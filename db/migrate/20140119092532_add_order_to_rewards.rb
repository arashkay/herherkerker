class AddOrderToRewards < ActiveRecord::Migration
  def change
    add_column :rewards, :ordering, :integer, :default => 0
  end
end
