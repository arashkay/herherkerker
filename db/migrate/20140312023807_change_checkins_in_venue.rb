class ChangeCheckinsInVenue < ActiveRecord::Migration
  def change
    rename_column :venues, :checkins, :checkins_count
  end
end
