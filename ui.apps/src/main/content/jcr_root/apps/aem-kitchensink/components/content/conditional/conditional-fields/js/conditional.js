(function($, $document) {
  'use strict';

  var CONTAINER_CLASS = '.conditional-fields-container';
  var CHECKBOX_CLASS = '.conditioner-checkbox';

  $document.on('dialog-ready', function() {
    var $container = $(CONTAINER_CLASS);

    if ($container.length <= 0) {
      return;
    }

    var $checkbox = $(CHECKBOX_CLASS);

    $checkbox.change(function() {
      handleCheckboxAction($(this));
    });

    function handleCheckboxAction(checkbox) {
      if (checkbox.is(':checked')) {
        setTextFieldVisible(true);
      } else {
        setTextFieldVisible(false);
      }
    }

    function setTextFieldVisible(shouldMakeVisible) {
      var imageUrl = $container.find('.conditional-text-field').parents('.coral-Form-fieldwrapper');

      if (shouldMakeVisible) {
        imageUrl.show();
      } else {
        imageUrl.hide();
      }
    }
  });
})(jQuery, jQuery(document));
