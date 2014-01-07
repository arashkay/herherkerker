$(function(){

$.extend( hhkk, { 
  more: $.noop,
  admin: {
    init: function(){
      hhkk.questions.init();
      hhkk.rewards.init();
    },
    reload: function(){
      location.href = location.href;
    }
  },
  charts: {
    registrations: function(data){
      var labels = [];
      var no = [];
      var max = 0;
      $.each(data,function(){
        labels.push(this.date);
        no.push(this.cnt);
        if(this.cnt>max) max = this.cnt;
      });
      var width = 1;
      switch(true){
        case max>10000:
          width = 10000;
          break;
        case max>1000:
          width = 1000;
          break;
        case max>100:
          width = 100;
          break;
        case max>10:
          width = 10;
          break;
      }
      new Chart($('#registrations')[0].getContext("2d"))
        .Line({
          labels: labels, 
          datasets: [{
            fillColor : "rgba(255,197,1,0.5)",
            strokeColor : "rgba(175,147,48,1)",
            pointColor : "rgba(220,220,220,1)",
            pointStrokeColor : "#fff",
            data: no
          }],
        },
        {
          scaleOverride: true,
          scaleSteps: 10,
          scaleStepWidth: width,
          scaleStartValue: 0,
          pointDotRadius: 5
        });
    },
    versions: function(data){
      var labels = [];
      var no = [];
      var colors = [ "rgba(27,104,181,1)", "rgba(127,40,81,1)", "rgba(40,127,52,1)", "rgba(127,115,40,1)", "rgba(114,66,64,1)",
                     "rgba(122,119,94,1)", "rgba(106,122,94,1)", "rgba(94,118,122,1)", "rgba(110,94,122,1)", "rgba(221,48,181,1)"];
      var dataset = [];
      $.each(data,function(i, v){
        dataset.push({ value: v.cnt, color: colors[i] });
        var label = $('<div class="label"/>').css('background', colors[i]).text( v.version )
        $('.fn-version-legend').append(label)
      });
      new Chart($('#versions')[0].getContext("2d"))
        .Pie(dataset);
    },
    rewards: function(rewards, collections){
      var dates = [0,1,1,1,1,1,1,1,1,1,1,1,1,1,1];
      var date = new Date();
      $.each(dates, function(i,v){
        date.setDate(date.getDate() - v)
        var dd = date.getDate();
        var mm = date.getMonth()+1;
        var yyyy = date.getFullYear();
        if(dd<10){dd='0'+dd} if(mm<10){mm='0'+mm} dates[i] = yyyy+'-'+mm+'-'+dd;
      });
      dates = dates.reverse();
      var dataset = [];
      var colors = [ "rgba(27,104,181,1)", "rgba(127,40,81,1)", "rgba(40,127,52,1)", "rgba(127,115,40,1)", "rgba(114,66,64,1)",
                     "rgba(122,119,94,1)", "rgba(106,122,94,1)", "rgba(94,118,122,1)", "rgba(110,94,122,1)", "rgba(221,48,181,1)"];
      $.each( collections, function(i, item){
        var data = [];
        $.each(dates, function(j, date){
          var count = 0;
          $.each( item, function(k, v){
            if(v.date!=date) return true;
            count = v.cnt;
            return false;
          });
          data.push(count);
        });
        dataset.push({
          fillColor : "rgba(0,0,0,0.0)",
          strokeColor : colors[i],
          pointColor : "rgba(220,220,220,1)",
          pointStrokeColor : "#fff",
          data: data
        });
        var label = $('<div class="label"/>').css('background', colors[i]).append( $('<img/>').attr('src',rewards[i].image_thumb) )
        $('.fn-reward-legend').append(label)
      });
      new Chart($('#rewards')[0].getContext("2d"))
        .Line({
          labels: dates, 
          datasets: dataset
        });
    }
  },
  questions: {
    init: function(){
      $('.fn-answers').on( 'click', '.fn-remove', function(){
        $(this).parents('.fn-answer:first').remove();
      });
      $('.fn-add-answer').click(hhkk.questions.addAnswer);
    },
    addAnswer: function(){
      var i = $('.fn-answers .fn-answer').size();
      var row = $('.template .fn-answer').clone().appendTo('.fn-answers');
      row.find('[name="answers[][title]"]').attr('name', "answers["+i+"][title]");
      row.find('[name="answers[][value]"]').attr('name', "answers["+i+"][value]");
    },
    saved: function(data){
      if(data.id)
        hhkk.admin.reload();
    },
    enabled: function(){
      $(this).parents('.fn-question').addClass('live');
    },
    disabled: function(){
      $(this).parents('.fn-question').removeClass('live');
    }
  },
  rewards: {
    init: function(){
      $('.instruction span').click(hhkk.rewards.toggle);
    },
    toggle: function(){
      var box = $(this).hide().parents('.instruction');
      $('textarea', box).attr( 'name', 'reward[instruction]').show();
    },
    saved: function(data){
      if(data.id)
        hhkk.admin.reload();
    },
    enabled: function(data){
      if(data!=false)
        $(this).parents('.fn-reward').addClass('live');
    },
    disabled: function(){
      $(this).parents('.fn-reward').removeClass('live');
    },
    attached: function(data){
      $(this).parents('.fn-reward').find('img').attr('src', data.image_thumb);
    },
    changed: function(){
      hhkk.admin.reload();
    }
  }
});

hhkk.admin.init();

});
