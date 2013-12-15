class CreateReplies < ActiveRecord::Migration
  def change
    create_table :replies do |t|
      t.string :value, null: false
      t.integer :question_id, null: false
      t.integer :device_id, null: false

      t.timestamps
    end

    add_foreign_key :replies, :questions
    add_foreign_key :replies, :devices

  end
end
