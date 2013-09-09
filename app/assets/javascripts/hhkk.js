var hhkk = {};

$(function(){

$.extend(hhkk, {
  init: function(){
  },
  saved: function(data){
    $('[name="message[body]"]').val('');
    $('.fn-submitted').fadeIn();
  },
  approved: function(){
    $(this).parents('.fn-post').fadeOut();
  }
});

});
