class AddCreditToBusinesses < ActiveRecord::Migration
  def change
    add_column :businesses, :credit, :integer, default: 0
  end
end
