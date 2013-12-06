class GeneralController < ApplicationController

  def charts
    @registrations = Device.select('DATE(created_at) as date, count(DATE(created_at)) as cnt').group('DATE(created_at)').where(["created_at>?",Time.now-15.days]).all
  end

end
