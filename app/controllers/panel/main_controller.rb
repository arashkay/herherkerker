class Panel::MainController < ApplicationController

  before_filter :authenticate_business!

end
