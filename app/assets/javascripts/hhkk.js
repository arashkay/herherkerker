var hhkk = {};

$(function(){

$.extend(hhkk, {
  offset: 1,
  loading: false,
  ended: false,
  likes: [],
  init: function(){
    $.cookie.json = true;
    var likes = $.cookie('likes');
    if(likes==null||likes=='') likes = [];
    hhkk.likes = likes;
    hhkk.liking();
    $('.fn-send').click(hhkk.send)
    $(window).scroll(hhkk.more);
  },
  send: function(){
    $('.fn-submitted').hide();
  },
  saved: function(data){
    $('[name="message[body]"]').val('');
    $('.fn-submitted').fadeIn();
  },
  approved: function(){
    $(this).parents('.fn-post').fadeOut();
  },
  more: function(){
    if($(window).scrollTop() + $(window).height() < $(document).height() - 10||hhkk.loading||hhkk.ended) return;
    hhkk.loading = true;
    $('.fn-more').show();
    $.post('/messages/more', { offset: hhkk.offset++}, hhkk.list);
  },
  list: function(data){
    hhkk.loading = false;
    $('.fn-more').hide();
    if(data.length==0){
      $('.fn-end').show();
      hhkk.ended = true;
    }else{
      $('body').scrollTop($(window).scrollTop() + $(window).height());
      $('.fn-post:hidden').template( data, { appendTo: '.fn-list' } );
    }
    hhkk.liking();
  },
  liking: function(){
    var posts = $('.fn-post');
    $.each(hhkk.likes, function(i,v){
      posts.filter('[data-dbid='+v+']').find('.fn-like').addClass('liked');
    });
  },
  liked: function(data){
    var scope = this.parents('.fn-post');
    var $t = $('.fn-count', scope);
    var cnt = parseInt($t.text());
    if(data){
      $t.text(cnt+1);
      hhkk.like(scope.data('dbid'));
      $('.fn-like', scope).addClass('liked');
    }else{
      $t.text(cnt-1);
      hhkk.dislike(scope.data('dbid'));
      $('.fn-like', scope).removeClass('liked');
    }
  },
  like: function(id){
    var isnew = true;
    $.each(hhkk.likes,function(i,v){
      if(v!=id) return true;
      isnew = false;
      return false;
    });
    if(!isnew) return;
    hhkk.likes.push(id);
    $.cookie('likes', hhkk.likes);
  },
  dislike: function(id){
    hhkk.likes.splice( hhkk.likes.indexOf( id ), 1 )
    $.cookie('likes', hhkk.likes);
  }
});

});
