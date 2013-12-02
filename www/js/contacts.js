$.extend( main, {
  contacts: {
    max: 50,
    filter: ["displayName", "phoneNumbers"],
    request: null,
    init: function(){
      $('.fn-contacts').on('click', '.fn-send-sms', function(){
        main.contacts.sms.send($(this).text(), $('p', main.current).text() + "--هرهرکرکر app" );
      });
      $('.fn-find-contact').keyup( function(){
        var $t = $(this);
        var text = $t.val();
        if(text.length>2){
          if(main.contacts.request) clearTimeout( main.contacts.request );
          main.contacts.request = setTimeout( function(){ main.contacts.find(text); $t.addClass('busy'); }, 2000);
        }
      });
    },
    sms: {
      send: function(number, text){
        sms.send(number, text, null, main.contacts.sms.success, main.contacts.sms.error);
      },
      success: function(e){
        main.current.addClass('smsed');
        main.popups.closeAll();
        if(main.cache.val('shares')==null)
          main.cache.val('shares', 1);
        else
          main.cache.val('shares', parseInt(main.cache.val('shares'))+1);
      },
      error: function(e){
        //alert('sms not sent!'+e);
      }
    },
    find: function(text){
      var options = new ContactFindOptions(text, true);
      navigator.contacts.find( main.contacts.filter, main.contacts.found, main.contacts.error, options);
    },
    found: function(contacts){
      $('.fn-find-contact').removeClass('busy');
      var list = $('.fn-contacts').empty();
      var max = main.contacts.max;
      $.each( contacts, function(i, v){
        if(v.displayName==null) return true;
        if(i>max) return false;
        var row = $('.template .fn-contact').clone();
        row.appendTo(list).find('.fn-text').text(v.displayName);
        if(v.phoneNumbers==null){
           max++;
           return row.remove();
        }
        $.each(v.phoneNumbers,function(j, no){
          if(/^(((\+|00)98)|0)?9\d{9}$/.test(no.value)||/^(04)/.test(no.value))
            $("<span class='block fn-send-sms'/>").text(no.value).appendTo(row);
        });
      });
    },
    error: function(e){
      //alert('error'+e);
    }
  }
});
