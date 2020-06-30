// acordion
$(document).ready(function () {

  $('.acordion-wrapper').children('.acordion-title').last().addClass('active');

  $(function () {
    var $panel = $('div.acordion-wrapper > div.acordion-count').hide();
    $panel.last().show();

    $('.acordion-wrapper div.acordion-title > .title').on('click', function (event) {
      var $this = $(this);
      $panel.slideUp();
      $this.parent().next().slideDown();
      return false;
    })
  })

});

//active state
$(function () {
  $('.acordion-wrapper div.acordion-title > .title').click(function (e) {

    e.preventDefault();
    var $this = $(this);
    $('.acordion-wrapper').children('.acordion-title').removeClass('active');
    //$this.parent().addClass('active');

    if ($('.acordion-wrapper div.acordion-title').hasClass('active')) {
    } else {
      $this.parent().addClass('active');
    }
  });
});