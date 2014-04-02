class AddBusinessIdToRewards < ActiveRecord::Migration
  def change
    add_column :rewards, :business_id, :integer
    add_foreign_key :rewards, :businesses
  end

end
