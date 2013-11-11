class Device < ActiveRecord::Base
  attr_accessible :did, :gcmid, :last_check
end
