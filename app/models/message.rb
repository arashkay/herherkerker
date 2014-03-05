class Message < ActiveRecord::Base

  belongs_to :device
  attr_accessible :body

  validates :body, :presence => true
  
  default_scope order( 'id DESC' )
  scope :fresh, where( :is_approved => false )
  scope :approved, where( :is_approved => true )
  scope :listing, limit(20)

  def post_date
    ActionController::Base.helpers.distance_of_time_in_words_to_now updated_at
  end

  def as_json(options={})
    super( :only => [:id, :body, :likes, :device_id],:methods => :post_date)
  end

  def self.bad_jokes(matches=HHKK::CONFIGS['filtering']['matches'], min=HHKK::CONFIGS['filtering']['minimum'], max=HHKK::CONFIGS['filtering']['maximum'])
    query = matches.split('-').map{ |i| "body LIKE '% #{i.strip} %' OR body LIKE '#{i.strip} %' " }.join(' OR ')
    fresh.where(["#{query} OR length(body) < ? OR length(body) > ?", min, max])
  end

end
