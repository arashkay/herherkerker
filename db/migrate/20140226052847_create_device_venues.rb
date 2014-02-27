class CreateDeviceVenues < ActiveRecord::Migration
  def change
    create_table :device_venues do |t|
      t.integer :device_id
      t.integer :venue_id
      t.string :action
      t.string :uname

      t.timestamps
    end
    
    add_foreign_key :device_venues, :devices
    add_foreign_key :device_venues, :venues

  end
end
