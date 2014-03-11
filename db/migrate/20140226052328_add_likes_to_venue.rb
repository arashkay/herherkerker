class AddLikesToVenue < ActiveRecord::Migration
  def change
    add_column :venues, :likes, :integer, default: 0
  end
end
