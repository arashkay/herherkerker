class UserVenue < ActiveRecord::Base
  attr_accessible :action, :user_id, :venue_id
end
