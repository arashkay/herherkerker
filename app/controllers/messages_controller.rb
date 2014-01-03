class MessagesController < ApplicationController

  before_filter :authenticate_admin!, :only => [:list, :approve, :reject]
  before_filter :detect_device!, :only => [:today, :create]

  def index
    @messages = Message.approved.listing
  end

  def more
    @messages = Message.approved.offset(20*params[:offset].to_i).listing
    render :json => @messages
  end

  def show
    @message = Message.find params[:id]
    @messages = Message.approved.listing.reverse
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
    @messages = Message.unscoped.fresh.all
    render layout: 'admin'
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
    @device.update_attribute :last_check, Time.now unless @device.blank?
    limit = (params[:firstload] == 'true') ? 15 : 10
    @messages = Message.unscoped.approved.where( [ "id > ?", params[:top] ] ).limit(limit).reverse!
    if @version == 1
      #render :json => { jokes: @messages, extra: [ ] }
      render :json => { jokes: @messages, extra: [ ], rewards: @device.unlockable( params[:last_reward_id] ) }
      #render :json => { jokes: @messages, extra: [ { body: '<a href="http://www.google.com">click here</a>' } ] }
    else
      render :json => @messages
    end
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
    end
    @message.destroy
    render :json => @message
  end

  def like
    @message = Message.find  params[:id]
    @message.increment! :likes
    Device.increment_counter( :like_count, @message.device_id ) unless @message.device_id.blank?
    render :json => true
  end

  def dislike
    Message.decrement_counter :likes, params[:id]
    render :json => false
  end

end

