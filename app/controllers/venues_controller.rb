class VenuesController < ApplicationController
  
  before_filter :detect_device!

  def suggest
    @venue = Venue.new params[:venue]
    render json: @venue.save
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

  def checkin
    @venue = Venue.find params[:id]
    unless @venue.nil?
      render json: @venue.checkin! @device, params[:action] 
    else
      render nil
    end
  end

end
