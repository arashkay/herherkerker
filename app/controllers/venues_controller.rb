class VenuesController < ApplicationController
  
  before_filter :detect_device!

  def suggest
    @venue = Venue.new params[:venue]
    render json: @venue.save
  end

  def index
    @venues = Venue.search params[:scope][:latitude], params[:scope][:longitude], params[:scope][:name]
    render json: @venues
  end

  def actions
    render json: Venue::ACTIONS
  end

  def checkin
    @venue = Venue.find params[:id]
    unless @venue.nil?
      render json: @venue.checkin!(@device, params[:action_name])
    else
      render nil
    end
  end

  def checkins
    @checkins = DeviceVenue.order('id DESC').limit(50)
    render json: @checkins
  end

end
