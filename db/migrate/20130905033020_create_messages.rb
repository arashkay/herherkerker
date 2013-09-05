class CreateMessages < ActiveRecord::Migration
  def change
    create_table :messages do |t|
      t.text :body
      t.boolean :is_approved, :default => false
      t.integer :rate, :default => 0

      t.timestamps
    end
  end
end
