class AddCappingToRewards < ActiveRecord::Migration
  def change
    add_column :rewards, :total, :integer, default: 0
    add_column :rewards, :collected, :integer, default: 0
    add_column :rewards, :total_winners, :integer, default: 0
  end
end
