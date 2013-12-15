class CreateQuestions < ActiveRecord::Migration
  def change
    create_table :questions do |t|
      t.string :title, null: false
      t.integer :question_type, null: false
      t.text :options

      t.timestamps
    end
  end
end
