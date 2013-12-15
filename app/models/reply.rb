class Reply < ActiveRecord::Base

  attr_accessible :device_id, :question_id, :value
  belongs_to :device
  belongs_to :question

  def self.submit( question_id, device, value )
    reply = device.replies.new( {value: value} )
    reply.question = Question.find question_id
    reply.save
  end

end
