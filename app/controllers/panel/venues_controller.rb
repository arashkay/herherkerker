class Panel::VenuesController < ApplicationController
  
  before_filter :authenticate_admin_or_business!, only: [:create, :new, :edit, :update, :attach] 
  before_filter :authenticate_admin!, except: [:create, :attach, :new, :edit, :update, :attach]
  
  def new
    @venue = Venue.new
  end

  def create
    @venue = Venue.new params[:venue]
    @venue.business_id = current_business.id if business_signed_in?
    @venue.approve if admin_signed_in?
    if @venue.save
      respond_to do |format| 
        format.json { render :venue }
        format.html { redirect_to panel_dashboard_path }
      end
    else
      respond_to do |format| 
        format.json { render json: @venue.errors }
        format.html { render :new }
      end
    end
  end

  def edit
    @venue = Venue.find params[:id]
    render :new
  end

  def update
    @venue = Venue.find params[:id]
    if @venue.update_attributes( params[:venue] )
      respond_to do |format| 
        format.json { render :venue }
        format.html { redirect_to panel_dashboard_path }
      end
    else
      respond_to do |format| 
        format.json { render json: @venue.errors }
        format.html { render :new }
      end
    end
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

end
