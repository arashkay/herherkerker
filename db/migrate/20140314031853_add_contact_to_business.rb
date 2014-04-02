class AddContactToBusiness < ActiveRecord::Migration
  def change
    add_column :businesses, :contact_name, :string
    add_column :businesses, :contact_mobile, :string
  end
end
