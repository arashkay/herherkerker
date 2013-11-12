namespace :notify do
  
  desc "Notify inactive users"
  task :inactives => :environment do
    Device.notify
  end

end

