# encoding: utf-8

class Device < ActiveRecord::Base
  
  attr_accessible :did, :regid, :last_check, :notified_at
  has_many :messages
  
  NOTIFICATION_INTERVAL = 8.hours

  before_create :set_dates

  def self.notify
    condition = ['last_check < ? AND notified_at < ?', Time.now-7.hours, Time.now-NOTIFICATION_INTERVAL]
    destinations = Device.select(:regid).where( condition ).map &:regid
    puts "device #{destinations.size}"
    return if destinations.empty?
    Device.update_all( { :notified_at => Time.now }, condition )
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
