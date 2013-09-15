class Message < ActiveRecord::Base
  attr_accessible :body

  validates :body, :presence => true
  
  default_scope order( 'updated_at DESC' )
  scope :fresh, where( :is_approved => false )
  scope :approved, where( :is_approved => true )

  def post_date
    ActionController::Base.helpers.distance_of_time_in_words_to_now m.updated_at
  end

  def as_json(options={})
    super( :only => [:id, :body],:method => :post_date)
  end

end
