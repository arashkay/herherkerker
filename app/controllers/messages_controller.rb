class MessagesController < ApplicationController

  before_filter :authenticate_admin!, :only => [:list, :approve, :reject]
  before_filter :detect_device!, :only => [:today, :create]

  def index
    redirect_to '/'
  end

  def show
    redirect_to '/'
  end

  def create
    @message = Message.new params[:message]
    @message.device_id = @device.id unless @device.blank?
    if @message.save
      render :json => @message
    else
      render :json => @message.errors
    end
  end

  def list
    @total = Message.unscoped.fresh.count
    if params[:filter].blank?
      @messages = Message.unscoped.fresh.limit(50)
    else
      @messages = Message.unscoped.fresh.bad_jokes
    end
  end

  def likes
    @messages = Message.select( [ :id, :likes, :updated_at  ] ).where id: params[:ids]
    render :json => @messages
  end
  
  def last
    @message = Message.approved.first
    render :text => "hhkk.show(#{{ :joke => @message.body }.to_json})"
  end

  def today
    @extras = []
    @rewards = []
    if @device.last_date < Time.now-HHKK::MOBILE::INTERVAL.hours
      @rewards = @device.unlockable( params[:last_reward_id].to_i )
      if params[:version]!="1.0.8"
      end
    end
    if (@device.id == 3596 || @device.id == 2) && @rewards.blank?
      @rewards = [Reward.last]
    end
    @messages = Message.unscoped.approved.where( [ "id > ?", @device.last_joke ] ).limit(HHKK::MOBILE::LIMIT).reverse!
    if @version == 1
      render :json => { jokes: @messages, extra: @extras, rewards: @rewards }
    else
      render :json => @messages
    end
    @device.update_attributes( { last_check: Time.now, last_joke: @messages.first.id } ) unless @device.blank? || @messages.empty?
  end

  def approve
    @message = Message.find params[:id]
    @message.likes = params[:likes].to_i + rand(10)
    @message.is_approved = true
    if @message.save
      unless @message.device_id.blank?
        device = @message.device
        device.increment :like_count, @message.likes
        device.increment :messages_count
        device.save
      end
      render :json => @message
    else
      render :json => @message.errors
    end
  end

  def reject
    @message = Message.find params[:id]
    device = @message.device
    unless device.nil?
      device.increment :like_count, (params[:likes].to_i + rand(20))
      device.increment :messages_count
      device.save
      device.warn! if params[:warning]
    end
    @message.destroy
    render :json => @message
  end

  def bulk_reject
    Message.unscoped.fresh.bad_jokes.destroy_all
    render json: true
  end

  def like
    begin
      params[:id] = params[:id].tr!('۰۱١۲۳۴۵۶۷۸۹','01123456789') if params[:id].to_i == 0
      @message = Message.find  params[:id]
      @message.increment! :likes
      Device.increment_counter( :like_count, @message.device_id ) unless @message.device_id.blank?
    rescue
    end
    render :json => true
  end

  def dislike
    begin
      Message.decrement_counter :likes, params[:id]
    rescue
    end
    render :json => false
  end

end

