$(function(){

$.extend( hhkk, { 
  admin: {
    init: function(){
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
  }
});

hhkk.admin.init();

});
