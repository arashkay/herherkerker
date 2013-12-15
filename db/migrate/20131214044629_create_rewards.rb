class CreateRewards < ActiveRecord::Migration

  def change
    create_table :rewards do |t|
      t.text :instruction, null: false
      t.timestamp :expires_at, null: false

      t.timestamps
    end
  end

end
