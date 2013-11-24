class ApplicationController < ActionController::Base
  protect_from_forgery

  before_filter :detect_version!

protected
  
  def authenticate_admin!
    redirect_to root_path if params[:arash].blank? && !session[:admin]
    session[:admin] = true
  end

  def detect_device!
    if params[:device].blank? || params[:device][:did].blank?
      @device = nil
    else
      @device = Device.find_or_create_by_did params[:device][:did]
    end
  end

  def detect_version!
    @version = params[:api].to_i unless params[:api].blank?
  end

end
