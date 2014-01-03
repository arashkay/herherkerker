# encoding: utf-8

class Device < ActiveRecord::Base
  
  attr_accessible :did, :regid, :last_check, :notified_at, :lat, :lng
  has_many :messages
  has_many :replies
  has_many :device_rewards
  has_many :rewards, through: :device_rewards

  serialize :badges
  
  NOTIFICATION_INTERVAL = 12.hours
  LEVELS = [ 'day5', 'shared10', 'liked100', 'sent25', 'shared30', 'liked500', 'liked1200', 'shared100', 'sent50' ]

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
  
  def increment_login
    self.last_date = Time.now if last_date.blank?
    if last_date < Time.now-1.day
      increment :logins_count
      self.last_date = Time.now
    end
  end

  def calculate_badges
    self.badges = gain_like_badges + gain_activity_badges + gain_share_badges
  end

  def gain_like_badges
    if like_count >= 1200
      [ Device::LEVELS[2], Device::LEVELS[5], Device::LEVELS[6] ]
    elsif like_count >= 500
      [ Device::LEVELS[2], Device::LEVELS[5] ]
    elsif like_count >= 100
      [ Device::LEVELS[2] ]
    else
      []
    end
  end

  def gain_activity_badges
    activities = []
    activities << Device::LEVELS[0] if logins_count >= 5
    if messages_count >= 50
      activities << Device::LEVELS[3]
      activities << Device::LEVELS[8]
    elsif messages_count >= 25
      activities << Device::LEVELS[3]
    end
    activities
  end

  def gain_share_badges
    if shares_count >= 100
      [ Device::LEVELS[1], Device::LEVELS[4], Device::LEVELS[7] ]
    elsif shares_count >= 30
      [ Device::LEVELS[1], Device::LEVELS[4] ]
    elsif shares_count >= 10
      [ Device::LEVELS[1] ]
    else
      []
    end
  end

  def unlockable(reward_id)
    reward_id = 0 if reward_id.blank?
    last_taken = self.rewards.last
    reward_id = last_taken.id if !last_taken.blank? && last_taken.id > reward_id
    Reward.lives.where(['id > ?', reward_id]).limit(1)
  end

  def unanswered_question
    answered_questions = Reply.where( device_id: self.id ).select(:question_id).map(&:question_id)
    if answered_questions.empty?
      Question.lives.limit(1)
    else
      Question.lives.where(['id NOT IN (?)', answered_questions]).limit(1)
    end
  end

  def locate
    location = Geocoder.search [self.lat, self.lng]
    self.city = location[0].city
  end

  def locate!
    locate
    save
  end

private 
  def set_dates
    self.last_check = Time.now
    self.notified_at = Time.now
  end

end
