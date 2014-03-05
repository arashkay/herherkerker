var hhkk = {};

$(function(){

$.extend(hhkk, {
  init: function(){
  },
  logout: function(){
    location.href = '/';
  },
  approved: function(){
    $(this).parents('.fn-post').fadeOut();
  }
});

});
