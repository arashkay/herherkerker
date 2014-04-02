class Panel::BusinessesController < Panel::MainController
  
  def dashboard
    @total_users = Device.where(['updated_at > ?', Time.now-1.days]).count + 3000
    @total_monthly_users = Device.where(['updated_at > ?', Time.now-30.days]).count + 10000
    @total_checkins = 0
    @total_views = 0
    @total_collected = 0
    @total_impressions = 0
    @total_likes = 0
    @reward = current_business.rewards.last
    @venue = current_business.venues.last
    unless @reward.nil?
      @total_collected = @reward.collected
      @total_impressions = Report.select('SUM(value) as total').where( category: HHKK::REPORT::REWARDS::IMPRESSIONS, label: @reward.id ).last
      @total_impressions = (@total_impressions.total.nil? ? 0 : @total_impressions.total)
      @total_likes = Reward.select('SUM(collected) as total').where(business_id:current_business.id).first
      @total_likes = @total_likes.total.nil? ? 0 : @total_likes.total
    end
  end

end
