class QuestionsController < ApplicationController

  before_filter :authenticate_admin!, :except => [:reply]
  before_filter :detect_device!, :only => [:reply]

  def create
    @question = Question.new params[:question]
    @question.options = Question::DEFAULT_OPTIONS
    (params[:answers]||[]).each do |i, k|
      @question.options[:answers] << { title: k[:title], value: k[:value] }
    end
    if @question.save
      render json: @question
    else
      render json: @question.errors
    end
  end

  def disable
    @question = Question.find params[:id]
    render json: @question.update_attributes( is_live: false )
  end
  
  def enable
    @question = Question.find params[:id]
    render json: @question.update_attributes( is_live: true )
  end
  
  def reply
    if Reply.submit params[:id], @device, params[:reply][:value] 
      @reward = Reward.find params[:reward][:id]
      render json: @reward.collect(@device)
    else
      render json: false
    end
  end

end 
