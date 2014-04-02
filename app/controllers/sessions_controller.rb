class SessionsController < Devise::SessionsController

  def create
    if params[:business][:email]=='admin@herherkerker.com' && Digest::SHA1.hexdigest(params[:business][:password])==HHKK::CONFIGS['admin']['password']
      session[:admin] = true
      redirect_to after_sign_in_path_for(resource)
    else
      super
    end
  end
  
  def after_sign_in_path_for(resource)
    if session[:admin]
      dashboard_path
    else
      panel_dashboard_path
    end 
  end

  def destroy
    session[:admin] = nil if session[:admin]
    super
  end

end
