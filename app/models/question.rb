class Question < ActiveRecord::Base
  
  attr_accessible :options, :title, :question_type, :is_live
  serialize :options
  
  DEFAULT_OPTIONS = { answers: [] }
  TYPES = [[ 'Type in', 1 ], [ 'Multiple Choice', 2 ], [ 'Single Choice', 3 ] ]

  scope :lives, where( is_live: true)

  def prety_type
    Question::TYPES[question_type-1][0]
  end

  validates :title, presence: true

  before_create :set_defaults

private
  
  def set_defaults
    self.is_live = false
    true
  end
end
