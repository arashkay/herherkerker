class AddAddressToDevices < ActiveRecord::Migration
  def change
    add_column :devices, :country, :string
    add_column :devices, :suburb, :string
  end
end
