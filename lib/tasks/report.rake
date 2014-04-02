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
    Rake::Task["report:rewards"].invoke
  end

  desc "Generate user report based on todays stats"
  task :users => :environment do
    today = Device.where('Date(last_date) = Date(NOW())').count
    Report.create({ category: HHKK::REPORT::USERS::DAILY, value: Device.where(['last_date > ?', date-1.day]).count })
    Device.select('DISTINCT(version)').order(:version).map(&:version).each do |version|
      Report.create({ category: HHKK::REPORT::USERS::TOTAL, label: version, value: Device.where(version: version).count })
    end
    Report.create({ category: HHKK::REPORT::USERS::INACTIVE, value: Device.where(['last_date < ?', date-10.days]).count })
  end

  desc "Generate user report for rewards"
  task :rewards => :environment do
    count = Device.where(['last_date > ?', date-1.day]).count
    ids = Reward.select([:id]).where(state: 'live').order(:id).map(&:id)
    ids.each_with_index do |id, i|
      total = (count*(i < ids.size/2.0 ? 0.7 : 0.5)).to_i
      Report.create({ category: HHKK::REPORT::REWARDS::IMPRESSIONS, label: id, value: total })
    end
  end

  desc "Generate session report based on todays stats"
  task :session => :environment do
  end

end


