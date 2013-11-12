# encoding: utf-8

class Device < ActiveRecord::Base
  
  attr_accessible :did, :regid, :last_check, :notified_at

  before_create :set_dates

  def self.notify
    destinations = [];
    Device.where( ['last_check < ? AND notified_at < ?', Time.now-7.hours, Time.now-15.hours] ).each do |device|
      destinations << device.regid
    end
    return if destinations.empty?
    Device.update_all( { :notified_at => Time.now }, ['last_check < ? AND notified_at < ?', Time.now-7.hours, Time.now-15.hours] )
    GCM.host = 'https://android.googleapis.com/gcm/send'
    GCM.format = :json
    GCM.key = 'AIzaSyDWiBvld-Iv9SLq4L0eWXCAHcyaDhkrND8'
    data = { :message => 'جوک جدید داری!', :title => 'وقت جوکه', :msgcnt => "5" }
    GCM.send_notification destinations, data
  end

private 
  def set_dates
    self.last_check = Time.now
    self.notified_at = Time.now
  end

end
