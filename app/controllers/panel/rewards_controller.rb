class Panel::RewardsController < ApplicationController
  
  before_filter :authenticate_admin_or_business!, only: [:create, :new, :edit, :update] 
  before_filter :authenticate_admin!, except: [:create, :new, :edit, :update]
  
  def new
    @reward = Reward.new
  end

  def create
    @reward = Reward.new params[:reward]
    @reward.business_id = current_business.id if business_signed_in?
    if @reward.save
      respond_to do |format| 
        format.json { render :reward }
        format.html { redirect_to panel_dashboard_path }
      end
    else
      respond_to do |format| 
        format.json { render json: @reward.errors }
        format.html { render :new }
      end
    end
  end

  def edit
    @reward = Reward.find params[:id]
    render :new
  end

  def update
    @reward = Reward.find params[:id]
    if @reward.update_attributes( params[:reward] )
      respond_to do |format| 
        format.json { render :reward }
        format.html { redirect_to panel_dashboard_path }
      end
    else
      respond_to do |format| 
        format.json { render json: @reward.errors }
        format.html { render :new }
      end
    end
  end

  def disable
    @reward = Reward.find params[:id]
    render json: @reward.pause!
  end
  
  def enable
    @reward = Reward.find params[:id]
    if @reward.may_run?
      render json: @reward.run!
    else
      render json: false
    end
  end

  def attach
    @reward = Reward.find params[:id]
    @reward.image = params[:file]
    @reward.save
    render json: @reward
  end
  
  def up
    ordering = Reward.find(params[:id]).ordering
    @rewards = Reward.where( ordering: [ordering, ordering-1] ).order('ordering DESC').limit(2)
    ordering = @rewards[0].ordering
    ordering = (ordering==@rewards[1].ordering ? ordering+1 : ordering)
    @rewards[0].ordering = @rewards[1].ordering
    @rewards[1].ordering = ordering
    @rewards[0].save
    @rewards[1].save
    render json: true
  end

end
