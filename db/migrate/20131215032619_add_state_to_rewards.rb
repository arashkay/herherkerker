class AddStateToRewards < ActiveRecord::Migration
  def change
    add_column :rewards, :state, :string
  end
end
