class DevicesController < ApplicationController
  
  before_filter :detect_device!, :only => [:register]

  def register
    @device.regid = params[:device][:regid]
    @device.last_check = Time.now
    render :json => @device.save
  end

end
