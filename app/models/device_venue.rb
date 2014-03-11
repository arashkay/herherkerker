class DeviceVenue < ActiveRecord::Base
  
  attr_accessible :action, :device_id, :venue_id, :uname
  belongs_to :device
  belongs_to :venue

  def as_json(options={})
    super( include: { device: { only: [], methods: [:image_thumb, :nickname] }, venue: { only: [:name, :likes, :checkins], methods: [:image_small] } } )
  end

end
