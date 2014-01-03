class DeviceReward < ActiveRecord::Base

  attr_accessible :device_id, :reward_id, :state
  belongs_to :reward
  belongs_to :device

  validates :device_id, presence: true
  validates :reward_id, presence: true

  include AASM

  aasm :column => 'state' do
    state :collected, :initial => true
    state :used
    state :expired
    state :deleted
    state :won

    event :use do
      transitions :from => [:collected], :to => :used
    end
    event :expire do
      transitions :to => :expired
    end
    event :remove do
      transitions :to => :deleted
    end
    event :win do
      transitions :to => :won
    end
  end

  scope :availables, where( state: ['collected', 'used'] ).includes( :reward )

  def as_json(options={})
    super( include: { reward: { methods: [:image_thumb, :image_small, :qrcode] } } )
  end

end
