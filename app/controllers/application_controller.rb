class ApplicationController < ActionController::Base
  protect_from_forgery

  before_filter :detect_version!

protected
  
  def admin_signed_in?
    !session[:admin].nil?
  end
   
  def authenticate_admin!
    redirect_to root_path unless session[:admin]
  end

  def authenticate_admin_or_business!
    redirect_to root_path unless session[:admin] || business_signed_in?
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
