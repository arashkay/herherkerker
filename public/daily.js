$(function(){
 
hhkk = {
  init: function(){
    $.ajax({
      url: 'http://herherkerker.com/last', 
      type: 'GET',
      //crossDomain: true,
      dataType: "jsonp",
      jsonpCallback: 'hhkk.show'
    });
  },
  show: function(json){
    $('[data-hhkk]').text(json.joke);
  }
}

hhkk.init();

})
