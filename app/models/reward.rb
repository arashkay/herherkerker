class Reward < ActiveRecord::Base
  
  attr_accessible :instruction, :image, :expires_at, :total, :total_winners
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
    state :capped

    event :run do
      transitions :from => [:ready, :paused], :to => :live, :guard => :is_valid?
    end
    event :pause do
      transitions :to => :paused
    end
    event :cap do
      transitions :to => :capped
    end
  end

  def is_valid?
    image.exists? && expires_at > Time.now
  end

  def collect(device)
    #uncomment it only if claimings for voucher after capped condition is low #return nil unless self.live?
    self.collected += 1
    self.cap if self.collected >= self.total
    self.save
    reward = device.device_rewards.new reward_id: self.id
    reward.save
    reward
  end

  def draw
    ids = DeviceReward.where( reward_id: self.id ).select(:id).map(&:id).sample( self.total_winners )
    DeviceReward.where( id: ids ).each{ |i| i.win! }
  end

  def as_json(options={})
    super( methods: [:image_thumb, :image_small, :qrcode] )
  end

end
