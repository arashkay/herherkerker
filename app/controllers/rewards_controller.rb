class RewardsController < ApplicationController

  before_filter :authenticate_admin!, :except => [:unlock]
  before_filter :detect_device!, :only => [:unlock]

  def create
    @reward = Reward.new params[:reward]
    if @reward.save
      render json: @reward
    else
      render json: @reward.errors
    end
  end

  def update
    @reward = Reward.find params[:id]
    @reward.update_attributes( params[:reward] )
    render json: @reward
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

  def unlock
    @reward = Reward.find params[:id]
    question = @device.unanswered_question
    if question.empty? 
      if params[:version]>="2.0.0"
        @reward.collect(@device)
        render json: []
      else
        render json: @reward.collect(@device)
      end
    else
      render json: question
    end
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
