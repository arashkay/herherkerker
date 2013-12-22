class DeviceLocation

  @queue = :hhkk_device_location

  def self.perform(id)
    @device = Device.find id
    @device.locate!
  end

end
