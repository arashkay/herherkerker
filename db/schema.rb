# encoding: UTF-8
# This file is auto-generated from the current state of the database. Instead
# of editing this file, please use the migrations feature of Active Record to
# incrementally modify your database, and then regenerate this schema definition.
#
# Note that this schema.rb definition is the authoritative source for your
# database schema. If you need to create the application database on another
# system, you should be using db:schema:load, not running all the migrations
# from scratch. The latter is a flawed and unsustainable approach (the more migrations
# you'll amass, the slower it'll run and the greater likelihood for issues).
#
# It's strongly recommended to check this file into your version control system.

ActiveRecord::Schema.define(:version => 20131215032619) do

  create_table "device_rewards", :force => true do |t|
    t.integer  "reward_id",  :null => false
    t.integer  "device_id",  :null => false
    t.string   "state"
    t.datetime "created_at", :null => false
    t.datetime "updated_at", :null => false
  end

  add_index "device_rewards", ["device_id"], :name => "device_rewards_device_id_fk"
  add_index "device_rewards", ["reward_id"], :name => "device_rewards_reward_id_fk"

  create_table "devices", :force => true do |t|
    t.string   "did"
    t.string   "regid"
    t.datetime "last_check"
    t.datetime "created_at",                    :null => false
    t.datetime "updated_at",                    :null => false
    t.datetime "notified_at"
    t.integer  "like_count",     :default => 0
    t.text     "badges"
    t.integer  "messages_count", :default => 0
    t.integer  "shares_count",   :default => 0
    t.integer  "logins_count",   :default => 1
    t.datetime "last_date"
  end

  add_index "devices", ["did"], :name => "index_devices_on_did"

  create_table "messages", :force => true do |t|
    t.text     "body"
    t.boolean  "is_approved", :default => false
    t.integer  "rate",        :default => 0
    t.datetime "created_at",                     :null => false
    t.datetime "updated_at",                     :null => false
    t.integer  "likes",       :default => 0
    t.integer  "device_id"
  end

  create_table "questions", :force => true do |t|
    t.string   "title",                            :null => false
    t.integer  "question_type",                    :null => false
    t.text     "options"
    t.datetime "created_at",                       :null => false
    t.datetime "updated_at",                       :null => false
    t.boolean  "is_live",       :default => false
  end

  create_table "replies", :force => true do |t|
    t.string   "value",       :null => false
    t.integer  "question_id", :null => false
    t.integer  "device_id",   :null => false
    t.datetime "created_at",  :null => false
    t.datetime "updated_at",  :null => false
  end

  add_index "replies", ["device_id"], :name => "replies_device_id_fk"
  add_index "replies", ["question_id"], :name => "replies_question_id_fk"

  create_table "rewards", :force => true do |t|
    t.text     "instruction",        :null => false
    t.datetime "expires_at",         :null => false
    t.datetime "created_at",         :null => false
    t.datetime "updated_at",         :null => false
    t.string   "image_file_name"
    t.string   "image_content_type"
    t.integer  "image_file_size"
    t.datetime "image_updated_at"
    t.string   "state"
  end

  add_foreign_key "device_rewards", "devices", :name => "device_rewards_device_id_fk"
  add_foreign_key "device_rewards", "rewards", :name => "device_rewards_reward_id_fk"

  add_foreign_key "replies", "devices", :name => "replies_device_id_fk"
  add_foreign_key "replies", "questions", :name => "replies_question_id_fk"

end
