var hhkk = {};

$(function(){

$.extend(hhkk, {
  init: function(){
    $('[data-toggle=tooltip]').tooltip();
    $('[data-toggle=popover]').popover();
    $('input').iCheck({
      checkboxClass: 'icheckbox_square-blue',
      radioClass: 'iradio_square-blue'
    });
    hhkk.steps();
  },
  steps: function(){
    var container = $('[data-step]');
    var steps = $('.step', container);
    var current = steps.filter('.active');
    steps.find('.body input').on( 'keyup', function(){
      var good = true;
      $(this).parents('.body:first').find('input').each(
        function(){
          var val = $(this).val();
          if(val!=''&&val.length>2) return;
          good = false;
          return false;
        }
      );
      if(good){
        var step = $(this).parents('.step:first').addClass('success');
        step.next().addClass('active');
      }else{
        var step = $(this).parents('.step:first').removeClass('success');
      }
    });
    $('label', steps).on( 'click', function(){
      var current = $(this).parents('.step:first').find('.body').slideDown();
      steps.find('.body').not(current).slideUp();
    });
    steps.find('.next').click(function(){
      var $this = $(this).parents('.step')
      var next = $this.next();
      $this.find('[type=checkbox]').iCheck('check');
      next.find('.title').click();
      next.find('[type=checkbox]').iCheck('enable');
      var current = next.find('.body').slideDown();
      steps.find('.body').not(current).slideUp();
    });
  },
  logout: function(){
    location.href = '/';
  },
  approved: function(){
    $(this).parents('.fn-post').fadeOut();
  }
});

});
