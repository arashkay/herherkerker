module ApplicationHelper

  def persian_date(date)
    date ? JalaliDate.new(date).format("%A %d %b %Y") : ''
  end

end
