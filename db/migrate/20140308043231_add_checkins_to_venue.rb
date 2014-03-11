class AddCheckinsToVenue < ActiveRecord::Migration
  def change
    add_column :venues, :checkins, :integer, default: 0
  end
end
