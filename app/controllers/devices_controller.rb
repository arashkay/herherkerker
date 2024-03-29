class DevicesController < ApplicationController
  
  before_filter :detect_device!

  def register
    @device.regid = params[:device][:regid]
    render :json => @device.save
  end

  def geo
    @device.lat = params[:device][:lat]
    @device.lng = params[:device][:lng]
    Resque.enqueue( DeviceLocation, @device.id) if @device.city.blank?
    render :json => @device.save
  end

  def update
    @device.first_name = params[:device][:first_name] unless params[:device][:first_name].blank?
    @device.last_name  = params[:device][:last_name]  unless params[:device][:last_name].blank?
    @device.phone      = params[:device][:phone]      unless params[:device][:phone].blank?
    @device.gender     = (params[:device][:gender].to_i==1) unless params[:device][:gender].blank?
    render json: @device.save
  end

  def show
    @device.shares_count = params[:shares] if params[:shares].to_i > @device.shares_count
    @device.increment_login
    @device.calculate_badges
    @device.version = params[:version] || '2.0.0'
    @device.last_date = Time.now
    @device.last_joke = HHKK::MOBILE::STARTING_JOKE if @device.last_joke == 0
    @device.save
    render json: { likes: (@device.nil? ? 0 : @device.like_count), badges: @device.badges }
  end

  def rewards
    render json: @device.device_rewards.availables
  end

  def winners
    @won_rewards = DeviceReward.where( state: :won ).order( 'created_at DESC')
    render layout: 'mobile'
  end

end
