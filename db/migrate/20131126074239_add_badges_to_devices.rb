class AddBadgesToDevices < ActiveRecord::Migration
  
  def change
    add_column :devices, :badges, :text
    add_column :devices, :messages_count, :integer, default: 0
    add_column :devices, :shares_count, :integer, default: 0
    add_column :devices, :logins_count, :integer, default: 1
    add_column :devices, :last_date, :timestamp
  end

end
