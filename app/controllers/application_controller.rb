class ApplicationController < ActionController::Base
  protect_from_forgery

protected
  
  def authenticate_admin!
    redirect_to root_path if params[:arash].blank?
  end

end
