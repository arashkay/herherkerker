class VenuesController < ApplicationController
  
  before_filter :detect_device!
  before_filter :authenticate_admin!, except: [:suggest]

  def suggest
    @venue = Venue.new params[:venue]
    render json: @venue.save
  end

  def create
    @venue = Venue.new params[:venue]
    @venue.approve
    @venue.save
    render json: @venue
  end

  def update
    @venue = Venue.find params[:id]
    @venue.update_attributes( params[:venue] )
    render json: @venue
  end

  def attach
    @venue = Venue.find params[:id]
    @venue.image = params[:file]
    @venue.save
    render json: @venue
  end

  def approve
    @venue = Venue.find params[:id]
    @venue.approve
    render json: @venue.save
  end

  def reject
    @venue = Venue.find params[:id]
    @venue.rejected
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
