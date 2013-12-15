class AddIsLiveToQuestions < ActiveRecord::Migration
  def change
    add_column :questions, :is_live, :boolean, default: 0
  end
end
