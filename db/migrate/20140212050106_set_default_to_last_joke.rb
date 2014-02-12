class SetDefaultToLastJoke < ActiveRecord::Migration
  def up
    change_column :devices, :last_joke, :integer, :default => 0
  end

  def down
    change_column :devices, :last_joke, :integer, :default => nil
  end
end
