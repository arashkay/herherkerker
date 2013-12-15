class AddPaperclipToRewards < ActiveRecord::Migration
  def self.up
    add_attachment :rewards, :image
  end

  def self.down
    remove_attachment :rewards, :image
  end
end
