namespace :profiling do
  
  desc "Build Profiles"
  task :build => :environment do
    Rake::Task["profiling:names"].invoke
    Rake::Task["profiling:genders"].invoke
  end

  desc "Detect Profiles' name"
  task :names => :environment do
    nonames = Device.select('devices.id').where('name IS NULL AND replies.id IS NOT NULL').joins("LEFT JOIN replies ON devices.id = replies.device_id AND replies.question_id = #{HHKK::PROFILING::NAME}")
    nonames.each do |device|
      reply = Reply.where( question_id: HHKK::PROFILING::NAME, device_id: device.id ).limit(1).first
      next if reply.blank?
      device.name = reply.value
      device.save
    end
  end

  desc "Detect Profiles' gender"
  task :genders => :environment do
    nogenders = Device.select('devices.id').where('gender IS NULL AND replies.id IS NOT NULL').joins("LEFT JOIN replies ON devices.id = replies.device_id AND replies.question_id = #{HHKK::PROFILING::GENDER}")
    question = Question.find HHKK::PROFILING::GENDER
    nogenders.each do |device|
      reply = Reply.where( question_id: HHKK::PROFILING::GENDER, device_id: device.id ).limit(1).first
      next if reply.blank?
      device.gender = (question.options[:answers][0][:value]==reply.value.to_i)
      device.save
    end
  end




end


