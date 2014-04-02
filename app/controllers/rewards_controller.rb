class RewardsController < ApplicationController

  before_filter :detect_device!, :only => [:unlock]

  def unlock
    params[:id] = params[:id].tr!('۰۱١۲۳۴۵۶۷۸۹','01123456789') if params[:id].to_i == 0
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

end
