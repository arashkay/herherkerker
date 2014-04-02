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

ActiveRecord::Schema.define(:version => 20140319093249) do

  create_table "businesses", :force => true do |t|
    t.string   "name"
    t.string   "address"
    t.string   "phone"
    t.datetime "created_at",                             :null => false
    t.datetime "updated_at",                             :null => false
    t.string   "email",                  :default => "", :null => false
    t.string   "encrypted_password",     :default => "", :null => false
    t.string   "reset_password_token"
    t.datetime "reset_password_sent_at"
    t.datetime "remember_created_at"
    t.integer  "sign_in_count",          :default => 0
    t.datetime "current_sign_in_at"
    t.datetime "last_sign_in_at"
    t.string   "current_sign_in_ip"
    t.string   "last_sign_in_ip"
    t.string   "contact_name"
    t.string   "contact_mobile"
    t.integer  "credit",                 :default => 0
  end

  add_index "businesses", ["email"], :name => "index_businesses_on_email", :unique => true
  add_index "businesses", ["reset_password_token"], :name => "index_businesses_on_reset_password_token", :unique => true

  create_table "device_rewards", :force => true do |t|
    t.integer  "reward_id",  :null => false
    t.integer  "device_id",  :null => false
    t.string   "state"
    t.datetime "created_at", :null => false
    t.datetime "updated_at", :null => false
  end

  add_index "device_rewards", ["device_id"], :name => "device_rewards_device_id_fk"
  add_index "device_rewards", ["reward_id"], :name => "device_rewards_reward_id_fk"

  create_table "device_venues", :force => true do |t|
    t.integer  "device_id"
    t.integer  "venue_id"
    t.string   "action"
    t.string   "uname"
    t.datetime "created_at", :null => false
    t.datetime "updated_at", :null => false
  end

  add_index "device_venues", ["device_id"], :name => "device_venues_device_id_fk"
  add_index "device_venues", ["venue_id"], :name => "device_venues_venue_id_fk"

  create_table "devices", :force => true do |t|
    t.string   "did"
    t.string   "regid"
    t.datetime "last_check"
    t.datetime "created_at",                                                       :null => false
    t.datetime "updated_at",                                                       :null => false
    t.datetime "notified_at"
    t.integer  "like_count",                                        :default => 0
    t.text     "badges"
    t.integer  "messages_count",                                    :default => 0
    t.integer  "shares_count",                                      :default => 0
    t.integer  "logins_count",                                      :default => 1
    t.datetime "last_date"
    t.decimal  "lat",                :precision => 10, :scale => 6
    t.decimal  "lng",                :precision => 10, :scale => 6
    t.string   "city"
    t.string   "name"
    t.string   "version"
    t.integer  "last_joke",                                         :default => 0
    t.string   "email"
    t.boolean  "gender"
    t.string   "country"
    t.string   "suburb"
    t.string   "image_file_name"
    t.string   "image_content_type"
    t.integer  "image_file_size"
    t.datetime "image_updated_at"
    t.string   "first_name"
    t.string   "last_name"
    t.string   "phone"
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

  create_table "reports", :force => true do |t|
    t.string   "category"
    t.string   "label"
    t.integer  "value"
    t.datetime "created_at", :null => false
    t.datetime "updated_at", :null => false
  end

  create_table "rewards", :force => true do |t|
    t.text     "instruction",                       :null => false
    t.datetime "expires_at",                        :null => false
    t.datetime "created_at",                        :null => false
    t.datetime "updated_at",                        :null => false
    t.string   "image_file_name"
    t.string   "image_content_type"
    t.integer  "image_file_size"
    t.datetime "image_updated_at"
    t.string   "state"
    t.integer  "total",              :default => 0
    t.integer  "collected",          :default => 0
    t.integer  "total_winners",      :default => 0
    t.integer  "ordering",           :default => 0
    t.integer  "business_id"
  end

  add_index "rewards", ["business_id"], :name => "rewards_business_id_fk"

  create_table "venues", :force => true do |t|
    t.string   "name"
    t.string   "address"
    t.string   "phone"
    t.float    "latitude"
    t.float    "longitude"
    t.string   "state"
    t.datetime "created_at",                        :null => false
    t.datetime "updated_at",                        :null => false
    t.integer  "likes",              :default => 0
    t.string   "image_file_name"
    t.string   "image_content_type"
    t.integer  "image_file_size"
    t.datetime "image_updated_at"
    t.integer  "checkins_count",     :default => 0
    t.integer  "business_id"
  end

  add_foreign_key "device_rewards", "devices", :name => "device_rewards_device_id_fk"
  add_foreign_key "device_rewards", "rewards", :name => "device_rewards_reward_id_fk"

  add_foreign_key "device_venues", "devices", :name => "device_venues_device_id_fk"
  add_foreign_key "device_venues", "venues", :name => "device_venues_venue_id_fk"

  add_foreign_key "replies", "devices", :name => "replies_device_id_fk"
  add_foreign_key "replies", "questions", :name => "replies_question_id_fk"

  add_foreign_key "rewards", "businesses", :name => "rewards_business_id_fk"

end
