namespace :report do

  def date(force_new=false)
    return @date unless @date.nil?
    timestamp = Report.find_by_category(HHKK::REPORT::DATE)
    timestamp = Report.create( { category: HHKK::REPORT::DATE, value: Time.now.to_i } ) if timestamp.nil?
    timestamp.update_attribute( :value, Time.now.to_i ) if force_new
    @date = Time.at timestamp.value
  end

  desc "Generate report based on todays stats"
  task :all => :environment do
    date true
    Rake::Task["report:users"].invoke
  end

  desc "Generate user report based on todays stats"
  task :users => :environment do
    today = Device.where('Date(last_date) = Date(NOW())').count
    Report.create({ category: HHKK::REPORT::USERS::DAILY, value: Device.where(['last_date > ?', date]).count })
    Device.group(:version).select(:version).map(&:version).each do |version|
      Report.create({ category: HHKK::REPORT::USERS::TOTAL, label: version, value: Device.where(version: version).count })
    end
    Report.create({ category: HHKK::REPORT::USERS::INACTIVE, value: Device.where(['last_date < ?', date-10.days]).count })
  end

  desc "Generate session report based on todays stats"
  task :all => :environment do
  end

end


