$(function(){

$.extend( hhkk, { 
  admin: {
    init: function(){
      hhkk.questions.init();
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
    }
  }
});

hhkk.admin.init();

});
