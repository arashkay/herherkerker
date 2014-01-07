class AddVersionAndLastJokeAndEmailToDevices < ActiveRecord::Migration
  def change
    add_column :devices, :version, :string
    add_column :devices, :last_joke, :integer
    add_column :devices, :email, :string
  end
end
