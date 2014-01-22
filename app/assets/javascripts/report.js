$(function(){

$.extend( hhkk, { 
  more: $.noop,
   charts: {
    registrations: function(data){
      var labels = [];
      var no = [];
      $.each(data,function(){
        labels.push(this.date);
        no.push(this.cnt);
      });
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
        });
    },
    daily_users: function(active_users, inactive_users){
      var labels = [];
      var active = [];
      var inactive = [];
      $.each(active_users,function(k, v){
        var date = new Date(v.created_at);
        var dd = date.getDate();
        var mm = date.getMonth()+1;
        if(dd<10){dd='0'+dd} if(mm<10){mm='0'+mm} date = mm+'-'+dd;
        labels.push(date);
        active.push(v.value);
        inactive.push(inactive_users[k].value);
      });
      new Chart($('#dailyusers')[0].getContext("2d"))
        .Line({
          labels: labels, 
          datasets: [{
            fillColor : "rgba(40,127,52,0.5)",
            strokeColor : "rgba(40,127,52,1)",
            pointColor : "rgba(40,127,52,1)",
            pointStrokeColor : "#fff",
            data: active
          },
          {
            fillColor : "rgba(255,197,1,0.5)",
            strokeColor : "rgba(175,147,48,1)",
            pointColor : "rgba(220,220,220,1)",
            pointStrokeColor : "#fff",
            data: inactive
          }],
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
        var label = $('<div class="label"/>').css('background', colors[i]).text( v.version + '(' + v.cnt + ')'  );
        $('.fn-version-legend').append(label);
      });
      new Chart($('#versions')[0].getContext("2d"))
        .Pie(dataset);
    },
    age: function(data, labels){
      var no = [];
      var colors = [ "rgba(27,104,181,1)", "rgba(127,40,81,1)", "rgba(40,127,52,1)", "rgba(127,115,40,1)", "rgba(114,66,64,1)",
                     "rgba(122,119,94,1)", "rgba(106,122,94,1)", "rgba(94,118,122,1)", "rgba(110,94,122,1)", "rgba(221,48,181,1)"];
      var dataset = [];
      $.each(data,function(i, v){
        dataset.push({ value: v.cnt, color: colors[i] });
        var label = $('<div class="label"/>').css('background', colors[i]).text( labels[i] + '(' + v.cnt + ')'  );
        $('.fn-age-legend').append(label);
      });
      new Chart($('#age')[0].getContext("2d"))
        .Pie(dataset);
    },
    preferences: function(data, labels){
      var no = [];
      var colors = [ "rgba(27,104,181,1)", "rgba(127,40,81,1)", "rgba(40,127,52,1)", "rgba(127,115,40,1)", "rgba(114,66,64,1)",
                     "rgba(122,119,94,1)", "rgba(106,122,94,1)", "rgba(94,118,122,1)", "rgba(110,94,122,1)", "rgba(221,48,181,1)"];
      var dataset = [];
      $.each(data,function(i, v){
        dataset.push({ value: v.cnt, color: colors[i] });
        var label = $('<div class="label"/>').css('background', colors[i]).text( labels[i] + '(' + v.cnt + ')'  );
        $('.fn-preferences-legend').append(label);
      });
      new Chart($('#preferences')[0].getContext("2d"))
        .Pie(dataset);
    },
    rewards: function(rewards, collections, total){
      var dates = [0,1,1,1,1,1,1,1,1,1,1,1,1,1,1];
      var date = new Date();
      $.each(dates, function(i,v){
        date.setDate(date.getDate() - v)
        var dd = date.getDate();
        var mm = date.getMonth()+1;
        var yyyy = date.getFullYear();
        if(dd<10){dd='0'+dd} if(mm<10){mm='0'+mm} dates[i] = mm+'-'+dd;
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
            if(v.date.substr(5)!=date) return true;
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
        var cnt = (total[i]==undefined)? 0 : total[i].cnt;
        var label = $('<div class="label"/>').css('background', colors[i]).append( $('<img/>').attr('src',rewards[i].image_thumb) ).append( '<br/><span>'+cnt+'</span>')
        $('.fn-reward-legend').append(label)
      });
      new Chart($('#rewards')[0].getContext("2d"))
        .Line({
          labels: dates, 
          datasets: dataset
        });
    },
    total_users: function(total){
      var colors = [ "rgba(27,104,181,1)", "rgba(127,40,81,1)", "rgba(40,127,52,1)", "rgba(127,115,40,1)", "rgba(114,66,64,1)",
                     "rgba(122,119,94,1)", "rgba(106,122,94,1)", "rgba(94,118,122,1)", "rgba(110,94,122,1)", "rgba(221,48,181,1)"];
      var types = [];
      var index = 0;
      var data = [];
      $.each(total, function(i,v){
        if($.inArray(v.label, types)==-1){
          types.push(v.label);
          var label = $('<div class="label"/>').css('background', colors[i]).text(v.label);
          $('.fn-total-users-legend').append(label);
          data[index++] = [];
        }
      });
      var labels = [];
      var last_date = null;
      for( i=0; i<total.length; i++){
        var v = total[i];
        var date = new Date(v.created_at);
        var dd = date.getDate();
        var mm = date.getMonth()+1;
        if(dd<10){dd='0'+dd} if(mm<10){mm='0'+mm} date = mm+'-'+dd;
        if(date!=last_date){
          labels.push(date);
          $.each(types,function(){
            if(!$.inArray(this.value, read)){
              data[k].push(0);
            }
          });
          var read = [];
        }
        last_date = date;
        $.each(types, function(k, item){
          if(item==v.label){
            data[k].push(v.value);
            read.push(item)
            return false;
          }
        });
      }
      var dataset = [];
      $.each(data, function(i, v){
        dataset.push({
          fillColor : "rgba(0,0,0,0.0)",
          strokeColor : colors[i],
          pointColor : "rgba(220,220,220,1)",
          pointStrokeColor : "#fff",
          data: v
        });
      });
      new Chart($('#totalusers')[0].getContext("2d"))
        .Line({
          labels: labels, 
          datasets: dataset
        });
    }    
  }
});

hhkk.admin.init();

});
