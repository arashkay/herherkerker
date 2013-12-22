class DevicesController < ApplicationController
  
  before_filter :detect_device!

  def register
    @device.regid = params[:device][:regid]
    render :json => @device.save
  end

  def geo
    @device.lat = params[:device][:lat]
    @device.long = params[:device][:lng]
    Resque.enqueue DeviceLocation, @device.id
    render :json => @device.save
  end

  def show
    @device.shares_count = params[:shares] if params[:shares].to_i > @device.shares_count
    @device.increment_login
    @device.calculate_badges
    @device.save
    render json: { likes: (@device.nil? ? 0 : @device.like_count), badges: @device.badges }
  end

end
