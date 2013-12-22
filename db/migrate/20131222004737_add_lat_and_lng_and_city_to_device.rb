class AddLatAndLngAndCityToDevice < ActiveRecord::Migration
  def change
    add_column :devices, :lat, :decimal, :precision=>10, :scale=>6
    add_column :devices, :lng, :decimal, :precision=>10, :scale=>6
    add_column :devices, :city, :string
  end
end
