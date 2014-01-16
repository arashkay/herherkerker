class AddGenderToDevices < ActiveRecord::Migration
  def change
    add_column :devices, :gender, :boolean
  end
end
