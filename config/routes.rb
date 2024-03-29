Herherkerker::Application.routes.draw do

  devise_for :businesses, controllers: { sessions: "sessions" } do
    get '/business' => 'sessions#new'
  end

  root to: 'general#index'

  resources :messages do
    collection do
      get :list
      match :today
      post :more
      post :likes
      post :bulk_reject
    end
    member do
      post :approve
      post :reject
      post :like
      post :dislike
    end
  end

  resources :devices, :only => [:show] do
    collection do
      put  :me, action: :update
      post :register
      post :geo
      post :rewards
    end
  end

  resources :rewards, only: [] do
    member do
      post :unlock
    end
    collection do
      post :unlock
    end
  end

  resources :venues, only: [:index] do
    collection do
      match :suggest
      get :actions
      get :checkins
      post :checkin
    end
    member do
      post :checkin
    end
  end

  resources :questions do
    member do
      post :enable
      post :disable
      post :reply
    end
  end

  resources :device_rewards, only: [:destroy, :update] do
    collection do
      post :sync
    end
    member do
      post :use
    end
  end
  
  get '/winners' => 'devices#winners'
  get '/last' => 'messages#last'

  get '/charts' => 'admin#charts'
  get '/dashboard' => 'admin#dashboard'

  namespace :panel do
    get "dashboard", controller: :businesses, action: :dashboard
    resources :businesses, only:[] do
    end
    resources :rewards do
      member do
        post :enable
        post :disable
        post :attach
        post :up
      end
    end
    resources :venues do
      member do
        post :attach
        post :approve
        post :reject
      end
    end
  end

  get '/:id' => 'messages#show', :as => :joke

  # The priority is based upon order of creation:
  # first created -> highest priority.

  # Sample of regular route:
  #   match 'products/:id' => 'catalog#view'
  # Keep in mind you can assign values other than :controller and :action

  # Sample of named route:
  #   match 'products/:id/purchase' => 'catalog#purchase', :as => :purchase
  # This route can be invoked with purchase_url(:id => product.id)

  # Sample resource route (maps HTTP verbs to controller actions automatically):
  #   resources :products

  # Sample resource route with options:
  #   resources :products do
  #     member do
  #       get 'short'
  #       post 'toggle'
  #     end
  #
  #     collection do
  #       get 'sold'
  #     end
  #   end

  # Sample resource route with sub-resources:
  #   resources :products do
  #     resources :comments, :sales
  #     resource :seller
  #   end

  # Sample resource route with more complex sub-resources
  #   resources :products do
  #     resources :comments
  #     resources :sales do
  #       get 'recent', :on => :collection
  #     end
  #   end

  # Sample resource route within a namespace:
  #   namespace :admin do
  #     # Directs /admin/products/* to Admin::ProductsController
  #     # (app/controllers/admin/products_controller.rb)
  #     resources :products
  #   end

  # You can have the root of your site routed with "root"
  # just remember to delete public/index.html.

  # See how all your routes lay out with "rake routes"

  # This is a legacy wild controller route that's not recommended for RESTful applications.
  # Note: This route will make all actions in every controller accessible via GET requests.
  # match ':controller(/:action(/:id))(.:format)'
end
