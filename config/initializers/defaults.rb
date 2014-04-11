module HHKK
  
  yaml = File.join(Rails.root, 'config', 'defaults.yml')
  if File.exist?(yaml) then
    hash = YAML.load_file(yaml)
    configs = (hash.has_key?('general') && !hash['general'].blank?) ? hash['general'] : {}
    configs = configs.merge(hash[Rails.env]) unless hash[Rails.env].blank?
  else
    puts "Please create #{yaml}"
    raise "Missing file"
  end

  CONFIGS = configs

  ACTIONS = HHKK::CONFIGS['actions']
  
  module REPORT
    
    DATE = HHKK::CONFIGS['report']['date']

    module USERS
      categories =  HHKK::CONFIGS['report']['categories']

      TOTAL       = categories['users']['total']
      DAILY       = categories['users']['daily']
      INACTIVE    = categories['users']['inactive']
    end

    module SESSIONS
      categories =  HHKK::CONFIGS['report']['categories']

      TOTAL    = categories['sessions']['total']
      DAILY    = categories['sessions']['daily']
    end

    module REWARDS
      categories =  HHKK::CONFIGS['report']['categories']

      IMPRESSIONS    = categories['rewards']['impressions']
    end
  end

  module MOBILE
    LIMIT     = HHKK::CONFIGS['mobile']['jokes_limit']
    INTERVAL  = HHKK::CONFIGS['mobile']['ads_interval']
    STARTING_JOKE = HHKK::CONFIGS['mobile']['starting_joke']
  end

  module PROFILING
    NAME    = HHKK::CONFIGS['profiling']['questions']['name']
    GENDER  = HHKK::CONFIGS['profiling']['questions']['gender']
    EMAIL   = HHKK::CONFIGS['profiling']['questions']['email']
    PHONE   = HHKK::CONFIGS['profiling']['questions']['phone']
    LIKES   = HHKK::CONFIGS['profiling']['questions']['likes']
    AGE   = HHKK::CONFIGS['profiling']['questions']['age']
  end
  
end

# JSON
Rabl.configure do |config|
  config.include_json_root = false
  config.include_child_root = false
end
