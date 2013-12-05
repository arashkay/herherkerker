var main = {
  current: null,
  defaults: {
    app: {
      api: 1,
      version: '1.0.3'
    },
    analytic: 'UA-11707985-12',
    notification: '80259899916',
    url: 'http://www.herherkerker.com',
    debug: false
  }
};
$.extend( main,{
  init: function(){
    $.ajaxSetup( { data: main.defaults.app } );
    if(!main.defaults.debug){
      analytics.init(main.defaults.analytic);
      notification.init(main.defaults.notification);
      analytics.track('home');
    }
    if(main.cache.val('points')!=null)
      $('.fn-points').text(main.cache.val('points'));
    main.popups.init();
    main.badges.init();
    $('.fn-options').click(main.menu.toggle);
    $('.fn-load-btn').click(main.posts.refresh);
    $('.fn-upload-btn').click(main.page.upload);
    $('.fn-about-btn').click(main.page.about);
    $('.fn-submit').click(main.posts.submit);
    $('.fn-close').click(main.page.close);
    $('[data-href]').click( main.open );
    $('.fn-posts').on( 'click','[data-href]', main.open );
    if(!main.defaults.debug)
      main.database.init();
    //main.contacts.init();
    if(main.cache.val('badges')!=null)
      main.badges.mark(main.cache.val('badges').split(','));
    $.get(main.defaults.url+'/devices/show.json', { device: { did: main.cache.did }, shares: main.cache.val('shares') }, main.me);
  },
  me: function(data){
    main.cache.val('points', data.likes);
    $('.fn-points').text(data.likes);
    main.badges.mark(data.badges);
    main.cache.val('badges', data.badges.join(','));
  },
  open: function(){
    $('.fn-blocker').fadeIn().delay(4000).fadeOut();
    var url = $(this).data('href');
    setTimeout( function(){window.open(url, '_system');}, 600);
  },
  loading: {
    popup: function(){
      $('.fn-blocker').fadeIn().delay(4000).fadeOut();
    },
    show: function(){
      $('.fn-blocker').fadeIn();
    },
    hide: function(){
      $('.fn-blocker').fadeOut();
    }
  },
  network: {
    exists: function(){
      return navigator.network.connection.type != Connection.NONE
    }
  },
  menu: {
    toggle: function(){
      $('.fn-menu').slideToggle();
    }
  },
  page: {
    close: function(){
      $(this).parents('.fn-page').slideUp('fast');
    },
    closeAll: function(){
      $('.fn-page').slideUp('fast');
    },
    about: function(){
      $('.fn-menu, .fn-upload').slideUp();
      $('.fn-about').slideDown();
    },
    upload: function(){
      $('.fn-menu, .fn-about').slideUp();
      $('.fn-upload').slideDown();
    }
  },
  badges: {
    init: function(){
      $('.fn-user').click(main.badges.toggle);
    },
    toggle: function(){
      $('body').toggleClass('badges');
    },
    mark: function(badges){
      $.each(badges, function(i,v){
        $('[data-badge="'+v+'"]').removeClass('locked');
      });
    }
  },
  popups: {
    init: function(){
      $('.fn-popup .fn-close').click(main.popups.close);
    },
    close: function(){
      $(this).parents('.fn-popup').hide();
    },
    closeAll: function(){
      $('.fn-popup').hide();
    }
  },
  register: function(regid){
    $.post(main.defaults.url+'/devices/register.json', { device: { did: main.cache.did, regid: regid } }, function(d){main.registered(regid)});
  },
  registered: function(regid){
    main.cache.val('regid', regid);
  },
  posts: {
    refresh: function(){
      $('.fn-menu').slideUp();
      $('body').removeClass('badges');
      main.page.closeAll();
      if(main.network.exists())
        main.posts.load(true);
    },
    early: function(){
      $('.fn-early').fadeIn().delay(4000).fadeOut();
    },
    submit: function(){
      var joke = $('.fn-joke').val();
      if(joke=='') return;
      analytics.track('posted');
      $.post(main.defaults.url+'/messages.json', { message: { body: joke }, device: { did: main.cache.did } }, main.posts.submitted);
      main.loading.show();
    },
    submitted: function(d){
      $('.fn-joke').val('');
      main.loading.hide();
      main.page.closeAll();
    },
    load: function(firstLoad){
      if (firstLoad==true) navigator.splashscreen.hide();
      if (!main.network.exists()) return;
      //if(Date.now()-main.cache.time()<21600000) return main.posts.early();
      $.post(main.defaults.url+'/messages/today.json', { top: main.cache.max(), firstload: (main.cache.time()==0), device: { did: main.cache.did } }, function(d){
        $('.fn-loader').hide(); 
        main.posts.list(d, true); 
      });
      $('.fn-loader').show();
    },
    list: function(data, save){
      if(save)
        main.database.write(data.jokes);
      var list = $('.template .fn-post').template(data.jokes);
      list.prependTo('.fn-posts');
      main.posts.actions(list);
      if(data.extra==undefined) return;
      $('.template .fn-ad').template(data.extra).prependTo('.fn-posts');
    },
    liked: function(tmp, data){
      if(data.liked){
        tmp = tmp.replace('post', 'post liked');
      }
      return tmp;
    },
    actions: function(list){
      main.drag.init(list);
      $('.fn-like', list).click( main.posts.like );
      $('.fn-dislike', list).click( main.posts.dislike );
      $('.fn-sms', list).click( main.posts.sms );
    },
    sms: function(e){
      e.stopPropagation();
      main.current = $(this).parents('.fn-post:first');
      var url = "sms:?body=" + $('p', main.current).text() + "--هرهرکرکر app" ;
      main.loading.popup();
      analytics.action( 'Sharing', 'SMS', 'SMS a Post', 1);
      main.current.addClass('smsed');
      setTimeout( function(){window.open(url, '_system');}, 600);
      if(main.cache.val('shares')==null)
        main.cache.val('shares', 1);
      else
        main.cache.val('shares', parseInt(main.cache.val('shares'))+1);
    },
    share: function(){
      main.current = $(this).parents('.fn-post:first');
      $('.fn-contact-popup').fadeIn();
      analytics.action( 'Sharing', 'SMS', 'SMS a Post', 1);
    },
    like: function(){
      var post = $(this).parents('.fn-post').addClass('liked');
      var cnt = $('.counts', post);
      cnt.text( parseInt(cnt.text())+1 );
      var id = post.data('dbid');
      $.post(main.defaults.url+'/messages/'+id+'/like.json');
      main.database.like(id, 1);
      analytics.action( 'Engagement', 'Like', 'Liking a Post', 1);
    },
    dislike: function(){
      var post = $(this).parents('.fn-post').removeClass('liked');
      var cnt = $('.counts', post);
      cnt.text( parseInt(cnt.text())-1 );
      var id = post.data('dbid');
      $.post(main.defaults.url+'/messages/'+id+'/dislike.json');
      main.database.like(id, 0);
      analytics.action( 'Engagement', 'Dislike', 'Disliking a Post', 1);
    },
    likes: function(){
      var ids = [];
      $('.fn-post:visible:lt(20)').each(function(){
        ids.push($(this).data('dbid'));
      });
      $.post(main.defaults.url+'/messages/likes.json', { ids: ids }, function(data){
        $.each( data, function(i,v){
          var cnt = $('[data-dbid='+v.id+'] .counts')
          if(cnt.text()==v.likes) return true;
          cnt.text(v.likes);
          main.database.likes(v.id, v.likes);
        })
      });
    },
    remove: function(target){
      target.animate({ left: -$(window).outerWidth() }, function(){ target.slideUp() } );
      main.database.remove( target.data('dbid') );
    }
  },
  database: {
    db: (main.defaults.debug)? null : window.openDatabase("Herherkerker", "1.0", "Herherkerker", 500000),
    init: function(){
      main.database.db.transaction(main.database.create, main.database.error, main.database.success);
    },
    reset: function(tx){
      tx.executeSql('DROP TABLE IF EXISTS posts');
      main.cache.max(0);
    },
    create: function(tx){
      //main.database.reset(tx);
      tx.executeSql('CREATE TABLE IF NOT EXISTS posts (id INTEGER UNIQUE, body TEXT, likes INTEGER, liked BOOLEAN)', [],function(){
        var id = main.cache.max();
        if(id==0)
          return main.posts.load(true);
        main.database.load( function(list){
          main.posts.list({jokes:list});
          main.posts.likes();
          main.posts.load(true);
        });
      });
    },
    error: function(err){
      //$.each( err, function(i,v){
      //  alert(i+':'+v);
      //});
      alert('db failed')
    },
    success: function(){
      //alert('yey!')
    },
    query: function(arg){
      var q = arg[0];
      var size = q.split('hhkkrpl').length-1;
      for(i=0;i<size;i++){
        var v = arg[i+1];
        v = ((typeof v == 'string')? v.replace(/\"/g,"'") : v);
        if(v==null) v = '';
        q = q.replace('hhkkrpl', v );
      }
      return q;
    },
    write: function(list){
      main.database.db.transaction( function(tx){
        $.each( list, function(i,v){
          tx.executeSql( main.database.query(['INSERT INTO posts (id, body, likes) VALUES (hhkkrpl,"hhkkrpl",hhkkrpl)', v.id, v.body, v.likes]));
        });
      }, main.database.error, function(){
        if(list.length>0){
          $.each(list, function(k,i){
            if(i.id>main.cache.max())
              main.cache.max(i.id);
          });
          main.cache.time(Date.now());
        }
      });
    },
    like: function(id, like){
      main.database.db.transaction( function(tx){
        tx.executeSql( 'UPDATE posts SET liked = '+like+', likes = likes+1 WHERE id = '+ id);
      });
    },
    likes: function(id, likes){
      main.database.db.transaction( function(tx){
        tx.executeSql( 'UPDATE posts SET likes = '+likes+' WHERE id = '+ id);
      });
    },
    load: function(callback){
      main.database.db.transaction( function(tx){
        tx.executeSql( 'SELECT * FROM posts ORDER BY id DESC', [],
        function(tx, re){
          var size = re.rows.length;
          var list = [];
          for(i=0;i<size;i++){
            var o = re.rows.item(i);
            list.push({ id: o.id, body: o.body, likes: o.likes, liked: o.liked });
          }
          callback(list);
        }, main.database.error);
      }, main.database.error, main.database.success);
    },
    max: function(callback){
      main.database.db.transaction( function(tx){
        tx.executeSql( 'SELECT * FROM posts ORDER BY id DESC LIMIT 1', [], 
          function(tx, re){
            if(re.rows.length==0)
              return callback(null);
            callback(re.rows.item(0).id);
          }, main.database.error);
      }, main.database.error, main.database.success);
    },
    remove: function(id){
      main.database.db.transaction( function(tx){
        tx.executeSql( 'DELETE FROM posts WHERE id = '+id )
      });
    }
  },
  cache: {
    storage: window.localStorage,
    max: function(id){
      if(id!=undefined)
        main.cache.storage.setItem('max', id);
      if(main.cache.storage.getItem('max')==null) return 0;
      return main.cache.storage.getItem('max');
    },
    time: function(time){
      if(time!=undefined)
        main.cache.storage.setItem('time', time);
      if(main.cache.storage.getItem('time')==null) return 0;
      return main.cache.storage.getItem('time');
    },
    val: function(key, val){
      if(val!=undefined)
        main.cache.storage.setItem(key, val);
      return main.cache.storage.getItem(key);
    },
    did: function(){
      if(main.cache.storage.getItem('did')==null)
        main.cache.storage.setItem('did', device.uuid);
      return main.cache.storage.getItem('did');
    }
  },
  drag: {
    init: function(target){
      $(target).on({
        touchstart: function(e){
          var touch = e.originalEvent.touches[0] || e.originalEvent.changedTouches[0];
          $(this).data({ touchx: touch.pageX }).on('touchmove', main.drag.move);
        },
        touchend: function(e){
          var touchx = $(this).data('touchx');
          var touch = e.originalEvent.touches[0] || e.originalEvent.changedTouches[0];
          var target = $(this);
          if(touchx-touch.pageX>target.width()/3)
            main.posts.remove(target);
          else
            target.css( { left: '' } );
          target.data('touchx', null).off('touchmove', main.drag.move);
        }
      })
    },
    move: function(e){
      var touch = e.originalEvent.touches[0] || e.originalEvent.changedTouches[0];
      var touchx = $(this).data('touchx');
    }
  }
});


if(main.defaults.debug)
  setTimeout(main.init, 1000);
