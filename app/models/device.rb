class Device < ActiveRecord::Base
  
  attr_accessible :did, :regid, :last_check

  def self.notify
    destinations = [];
    Device.where( 'last_check > ?', Time.now-7.hours ).each do |device|
      destinations << device.regid
    end
    GCM.host = 'https://android.googleapis.com/gcm/send'
    GCM.format = :json
    GCM.key = "API_KEY_GOES_HERE"
    data = {:message => "جوک جدید داری!", :title => 'وقت جوکه!', :msgcnt => "5"}
    GCM.send_notification( destinations, data)
  end

end
