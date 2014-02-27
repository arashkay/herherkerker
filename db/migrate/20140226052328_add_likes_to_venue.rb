class AddLikesToVenue < ActiveRecord::Migration
  def change
    add_column :venues, :likes, :integer
  end
end
