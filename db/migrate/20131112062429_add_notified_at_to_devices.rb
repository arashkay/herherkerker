class AddNotifiedAtToDevices < ActiveRecord::Migration
  def change
    add_column :devices, :notified_at, :timestamp
  end
end
