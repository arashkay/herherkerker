class CreateReports < ActiveRecord::Migration
  def change
    create_table :reports do |t|
      t.string :category
      t.string :label
      t.integer :value

      t.timestamps
    end
  end
end
