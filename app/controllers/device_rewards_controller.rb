class DeviceRewardsController < ApplicationController
  
  before_filter :detect_device!

  def destroy
    @reward = @device.device_rewards.find params[:id]
    render json: @reward.remove!
  end

  def sync
    rewards = params[:rewards].map{ |i| i[1] }
    @device.device_rewards.where( id: rewards.map{ |i| i[:id] } ).each do |reward|
      state = rewards.select{ |v| v[:id].to_i == reward.id }[0]
      case state[:state]
        when 'deleted'
          reward.remove!
        when 'used'
          reward.use!
      end
    end
    render json: true
  end

  def use
    @reward = @device.device_rewards.find params[:id]
    render json: @reward.use!
  end

end
