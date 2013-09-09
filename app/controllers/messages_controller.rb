class MessagesController < ApplicationController

  def index
    @messages = Message.approved
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

  def approve
    @message = Message.find params[:id]
    @message.is_approved = true
    if @message.save
      render :json => @message
    else
      render :json => @message.errors
    end
  end

end

