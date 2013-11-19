class DevicesController < ApplicationController
  
  before_filter :detect_device!

  def register
    @device.regid = params[:device][:regid]
    render :json => @device.save
  end

  def show
    render @device.likes
  end

end
