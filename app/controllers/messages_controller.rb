class MessagesController < ApplicationController

  def index
    @messages = Message.all
  end

  def show
    @message = Message.find params[:id]
    @messages = Message.all
  end

  def create
    @message = Message.new params[:message]
    if @message.save
      render :json => @message
    else
      render :json => @message.errors
    end
  end

end

