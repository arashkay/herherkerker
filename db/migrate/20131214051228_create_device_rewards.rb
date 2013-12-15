class CreateDeviceRewards < ActiveRecord::Migration
  def change
    create_table :device_rewards do |t|
      t.integer :reward_id, null: false
      t.integer :device_id, null: false
      t.string :state

      t.timestamps
    end

    add_foreign_key :device_rewards, :rewards
    add_foreign_key :device_rewards, :devices

  end
end
