class DeviceVenue < ActiveRecord::Base
  
  attr_accessible :action, :device_id, :venue_id, :uname
  belongs_to :device
  belongs_to :venue, counter_cache: :checkins_count

  def as_json(options={})
    super( include: { device: { only: [], methods: [:image_thumb, :nickname] }, venue: { only: [:name, :likes, :checkins_count], methods: [:image_small] } } )
  end

end
