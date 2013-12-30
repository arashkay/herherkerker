class Reward < ActiveRecord::Base
  
  attr_accessible :instruction, :image, :expires_at
  has_attached_file :image, :styles => { :medium => "400x400", :small => "200x200", :thumb => "100x100" }, :default_url => "/assets/:class/images/:style/missing.png"

  validates :expires_at, presence: true
  validates :instruction, presence: true
  
  def image_thumb
    self.image.url(:thumb)
  end
  
  def image_small
    self.image.url(:small)
  end

  def qrcode
    self.image.url(:small)
  end

  scope :lives, where( state: :live)

  include AASM

  aasm :column => 'state' do
    state :ready, :initial => true
    state :live
    state :ended
    state :paused

    event :run do
      transitions :from => [:ready, :paused], :to => :live, :guard => :is_valid?
    end
    event :pause do
      transitions :to => :paused
    end
  end

  def is_valid?
    image.exists? && expires_at > Time.now
  end

  def collect(device)
    reward = device.device_rewards.new reward_id: self.id
    reward.save
    reward
  end

  def as_json(options={})
    super( methods: [:image_thumb, :image_small, :qrcode] )
  end

end
