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
      $('.fn-reward .fn-change').click(hhkk.rewards.change);
    },
    change: function(){
      var $this = $(this);
      var item = $this.parents('.fn-reward');
      var modal = $('#new_reward').modal('show');
      $("[name='reward[instruction]']", modal).val($('[name=instruction]',item).val());
      $("[name='reward[expires_at]']", modal).val(item.data('db-date'));
      $("[name='reward[total]']", modal).val(item.data('db-total'));
      $("[name='reward[total_winners]']", modal).val(item.data('db-winners'));
      $(".fn-save", modal).data('remote', $this.data('url')).attr('data-method', 'PUT');
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
