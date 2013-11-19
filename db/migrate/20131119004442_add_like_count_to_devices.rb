class AddLikeCountToDevices < ActiveRecord::Migration
  def change
    add_column :devices, :like_count, :integer, :default => 0
  end
end
