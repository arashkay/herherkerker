class DevicesController < ApplicationController
  
  before_filter :detect_device!

  def destroy
    @reward = @device.device_rewards.find params[:id]
    render json: @reward.remove!
  end

  def use
    @reward = @device.device_rewards.find params[:id]
    render json: @reward.use!
  end

end
