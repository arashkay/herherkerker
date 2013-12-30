class Reply < ActiveRecord::Base

  attr_accessible :device_id, :question_id, :value
  belongs_to :device
  belongs_to :question

  def self.submit( question_id, device, values )
    success = false
    Reply.transaction do
      values.each do |value|
        reply = device.replies.new( {value: value} )
        reply.question = Question.find question_id
        reply.save
      end
      success = true
    end
    success
  end

end
