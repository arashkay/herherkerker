class Venue < ActiveRecord::Base
  
  attr_accessible :address, :latitude, :longitude, :name, :phone, :state

  include AASM

  aasm :column => 'state' do
    state :new, :initial => true
    state :approved
    state :rejected

    event :approve do
      transitions :to => :approved
    end
    event :reject do
      transitions :to => :rejected
    end
  end



end
