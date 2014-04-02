class Venue < ActiveRecord::Base
  
  attr_accessible :address, :latitude, :longitude, :name, :phone, :state, :image, :checkins_count
  has_attached_file :image, :styles => { :medium => "600x200#", :small => "300x100#", :thumb => "120x40#" }, :default_url => "/assets/:class/images/:style/missing.png"
  ACTIONS = HHKK::ACTIONS
  reverse_geocoded_by :latitude, :longitude

  def image_thumb
    self.image.url(:thumb)
  end
  
  def image_small
    self.image.url(:small)
  end

  include AASM

  aasm :column => 'state' do
    state :new, :initial => true
    state :approved
    state :rejected

    event :approve do
      transitions :to => :approved
    end
    event :reject do
      transitions :to => :rejected
    end
  end

  def self.search( latitude, longitude, name=nil)
    criteria = name.blank? ? Venue : Venue.where( ["name LIKE ?", "'%#{name}%'"] )
    criteria.near([latitude, longitude], HHKK::CONFIGS['mobile']['venue_distance'], :units => :km)
  end
  
  def checkin!(device, action)
    action = HHKK::ACTIONS.values.flatten.compact.find { |i| i['uname'] == action }
    DeviceVenue.create( { device_id: device.id, action: action['text'], uname: action['uname'], venue_id: id } )
  end

  def as_json(options={})
    super( methods: [:image_thumb, :image_small] )
  end

end
