class MessagesController < ApplicationController

  before_filter :authenticate_admin!, :only => [:list]

  def index
    @messages = Message.approved.limit(10)
  end

  def more
    @messages = Message.approved.offset(10*params[:offset].to_i).limit(10)
    render :json => @messages
  end

  def show
    @message = Message.find params[:id]
    @messages = Message.approved
  end

  def create
    @message = Message.new params[:message]
    if @message.save
      render :json => @message
    else
      render :json => @message.errors
    end
  end

  def list
    @messages = Message.fresh.all
  end
  
  def last
    @message = Message.approved.first
    render :text => "hhkk.show(#{{ :joke => @message.body }.to_json})"
  end

  def approve
    @message = Message.find params[:id]
    @message.is_approved = true
    if @message.save
      render :json => @message
    else
      render :json => @message.errors
    end
  end

  def reject
    @message = Message.find params[:id]
    @message.destroy
    render :json => @message
  end

  def like
    Message.increment_counter :likes, params[:id]
    render :json => true
  end

  def dislike
    Message.decrement_counter :likes, params[:id]
    render :json => false
  end

end

