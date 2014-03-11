class AddNameFieldsToDevice < ActiveRecord::Migration
  def change
    add_column :devices, :first_name, :string
    add_column :devices, :last_name, :string
  end
end
