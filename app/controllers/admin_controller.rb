class AdminController < ApplicationController

  before_filter :authenticate_admin!

  def charts
    @registrations = Device.select('DATE(created_at) as date, count(DATE(created_at)) as cnt').group('DATE(created_at)').where(["created_at>?",Time.now-15.days]).all
    @rewards = Reward.order('id DESC').limit(10).reverse
    @collected = []
    @rewards.each do |reward|
      @collected << DeviceReward.select('DATE(created_at) as date, count(DATE(created_at)) as cnt').group('DATE(created_at)').where(["created_at>? AND reward_id=?",Time.now-15.days, reward.id]).all
    end
    @total_rewards = DeviceReward.group(:reward_id).select('COUNT(reward_id) as cnt, reward_id').where( reward_id: @rewards.map(&:id) ).order('reward_id ASC')
    @versions = Device.select('version, count(version) as cnt').where('version IS NOT NULL').group('version')
    @daily_users = Report.where( category: HHKK::REPORT::USERS::DAILY ).order('created_at DESC').limit(15).reverse
    @total_users = Report.where( category: HHKK::REPORT::USERS::TOTAL ).order('created_at DESC').limit(15).reverse
    @inactive_users = Report.where( category: HHKK::REPORT::USERS::INACTIVE ).order('created_at DESC').limit(15).reverse
    @preferences = Reply.select("count(value) as cnt, value").where(question_id: HHKK::PROFILING::LIKES).group(:value).order(:value)
    @preferences_label = Question.find(HHKK::PROFILING::LIKES).options[:answers].sort_by{ |i| i[:value] }.map { |i| i[:title] }
    @age = Reply.select("count(value) as cnt, value").where(question_id: HHKK::PROFILING::AGE).group(:value).order(:value)
    @age_label = Question.find(HHKK::PROFILING::AGE).options[:answers].sort_by{ |i| i[:value] }.map { |i| i[:title] }
  end

  def dashboard
    @rewards = Reward.order('ordering DESC').all
    @questions = Question.all
    @venues = Venue.order('id DESC').limit(20).all
  end

end

