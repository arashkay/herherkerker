var notification = {};

$.extend(notification, {
  sender: null,
  note: null,
  init: function(api_key){
    notification.sender = api_key;
    notification.note = window.plugins.pushNotification;
    if( device.platform == 'android' || device.platform == 'Android' ){
      notification.note.register(
        notification.registered,
        notification.notregistered, {
          "senderID": notification.sender,
          "ecb": "notification.noted"
        }
      );
    }
  },
  registered: function(result){
    //alert(result)
  },
  notregistered: function(error){
    //alert('Internet Connection Needed!');
  },
  noted: function(e){
    switch( e.event ) {
      case 'registered':
        if( e.regid.length > 0 )
          if(main.cache.val('regid')!=e.regid)
            main.register(e.regid);
        break;
      case 'message':
        if( e.foreground ){
          // if the notification contains a soundname, play it.
          //var sound = new Media("/android_asset/www/"+e.soundname);
          //sound.play();
        }else{  
          if ( e.coldstart ){
          }else{
          }
        }
        //alert(e.payload.message);
        //alert(e.payload.msgcnt);
        break;
      case 'error':
        //alert('error:'+e.msg)
        break;
      default:
        //alert('unknown');
        break;
    }
  }
});
