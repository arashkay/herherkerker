# encoding: utf-8

class Device < ActiveRecord::Base
  
  attr_accessible :did, :regid, :last_check, :notified_at
  has_many :messages
  
  NOTIFICATION_INTERVAL = 12.hours

  before_create :set_dates

  def self.notify
    condition = ['regid IS NOT NULL AND (last_check < ? AND notified_at < ?)', Time.now-7.hours, Time.now-NOTIFICATION_INTERVAL]
    begin
      destinations = Device.select(:regid).where( condition ).limit(999).map &:regid
      puts "device #{destinations.size}"
      return if destinations.empty?
      Device.update_all( { :notified_at => Time.now }, { :regid => destinations } )
      GCM.host = 'https://android.googleapis.com/gcm/send'
      GCM.format = :json
      GCM.key = 'AIzaSyDWiBvld-Iv9SLq4L0eWXCAHcyaDhkrND8'
      data = { :message => 'جوک جدید داری!', :title => 'وقت جوکه', :msgcnt => "5" }
      GCM.send_notification destinations, data
    end while !destinations.empty?
  end

private 
  def set_dates
    self.last_check = Time.now
    self.notified_at = Time.now
  end

end
