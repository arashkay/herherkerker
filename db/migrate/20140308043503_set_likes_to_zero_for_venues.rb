class SetLikesToZeroForVenues < ActiveRecord::Migration
  def up
    change_column :venues, :likes, :integer, default: 0
    Venue.update_all( likes: 0 )
  end

  def down
  end
end
