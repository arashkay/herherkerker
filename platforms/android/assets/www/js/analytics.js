var analytics = {};

$.extend(analytics, {
  id: null,
  ga: null,
  init: function(api_key){
    analytics.id = api_key;
    analytics.ga = window.plugins.gaPlugin;
    analytics.ga.init(analytics.success, analytics.error, analytics.id, 10);
    $(window).on('unload', analytics.exit);
  },
  track: function(page){
    analytics.ga.trackPage(analytics.success, analytics.error, page);
  },
  action: function( category, action, label, value ){
    analytics.ga.trackEvent(analytics.success, analytics.error, category, action, label, value);
  },
  exit: function(){
    analytics.ga.exit(analytics.success, analytics.error);
  },
  success: function(){
  },
  error: function(){
  }
});

