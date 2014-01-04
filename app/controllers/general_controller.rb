class GeneralController < ApplicationController

  layout 'admin'
  before_filter :authenticate_admin!

  def charts
    @registrations = Device.select('DATE(created_at) as date, count(DATE(created_at)) as cnt').group('DATE(created_at)').where(["created_at>?",Time.now-15.days]).all
    @rewards = Reward.last(10)
    @collected = []
    @rewards.each do |reward|
      @collected << DeviceReward.select('DATE(created_at) as date, count(DATE(created_at)) as cnt').group('DATE(created_at)').where(["created_at>?",Time.now-15.days]).all
    end
  end

  def admin
    @rewards = Reward.all
    @questions = Question.all
  end

end
