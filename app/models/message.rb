class Message < ActiveRecord::Base
  attr_accessible :body

  validates :body, :presence => true
  
  default_scope order( 'updated_at DESC' )
  scope :fresh, where( :is_approved => false )
  scope :approved, where( :is_approved => true )

end
