class AddBusinessIdToVenues < ActiveRecord::Migration
  def change
    add_column :venues, :business_id, :integer
  end
end
