var hhkk = {};

$(function(){

$.extend(hhkk, {
  offset: 1,
  loading: false,
  ended: false,
  init: function(){
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
  }
});

});
