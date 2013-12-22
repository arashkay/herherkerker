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
      render json: @reward.collect(@device)
    else
      render json: question
    end
  end
  
end
