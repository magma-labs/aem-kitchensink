(function() {
  // Register CUI.Colorpicker globally on editor.html
  function sortProperties(obj) {
    var sortable = [];
    for (var key in obj) {
      if (obj.hasOwnProperty(key)) {
        sortable.push([key, obj[key]]);
      }
    }

    sortable.sort(function(a, b) {
      var x = a[1].toLowerCase();
      var y = b[1].toLowerCase();
      var xHigher = x > y ? 1 : 0;
      return x < y ? -1 : xHigher;
    });

    return sortable;
  }

  CUI.Colorpicker = new Class({
    toString: 'Colorpicker',
    extend: CUI.Colorpicker,

    _readDataFromMarkup: function() {
      this.superClass._readDataFromMarkup.call(this);

      var el = this.$element;

      // extend otb CUI.Colorpicker to workaround the pickerModes bug
      // in granite/ui/components/foundation/form/colorpicker/render.jsp
      // colorpickerJson.put('modes', pickerModes); should have been
      // colorpickerJson.put('pickerModes', pickerModes);

      if (el.data('config') && el.data('config').modes) {
        this.options.config.displayModes = el.data('config').modes;
      }

      // Sort colors by value

      if (el.data('config') && el.data('config').colors) {
        var colorsObject = el.data('config').colors;

        var sortedColorsArray = sortProperties(colorsObject);
        var sortedColors = _.object(sortedColorsArray);

        this.options.config.colors = sortedColors;
      }
    },

    _renderPalette: function() {
      // override _renderPalette method to workaround the Classic Palette
      // mode bug. Colors were not being paginated correctly in this mode.
      this._renderPaletteHeader();

      var table = $('<table>');
      var html = '';

      for (var i = 0; i < this.palettePageSize; i++) {
        html += '<tr>';
        var opacity = 0;
        var rgb = '';
        var cssClass = '';
        var shade = '';
        for (var sh = 0; sh < this.colorShadeNo; sh++) {
          if (this.options.config.displayModes.classicPalette) {
            // display colors with shades
            if (this.colorNames.length - 1 < i + this.lowerLimit) {
              html += '<td><a></a></td>';
            } else {
              rgb = CUI.util.color.HexToRGB(this.options.config.colors[this.colorNames[i + this.lowerLimit]]);
              shade = 'rgba(' + rgb.r + ',' + rgb.g + ',' + rgb.b + ',' + (1 - opacity).toFixed(2) + ')';
              opacity += 0.16;
              if (CUI.util.color.isSameColor(shade, this.$hiddenInput.val())) {
                cssClass = 'is-selected';
                this._fillSelectedColor(this.colorNames[i + this.lowerLimit], CUI.util.color.RGBAToHex(shade));
              } else {
                cssClass = '';
              }
              html += '<td class="filled"><a style="background-color:' + shade + '" color="' + shade + '" colorName="' + this.colorNames[i + this.lowerLimit] + '" class="' + cssClass + '">' + '</a></td>';
            }
          } else if (this.colorNames.length - 1 < (i * this.colorShadeNo + sh) + this.lowerLimit) {
            html += '<td><a></a></td>';
          } else {
            rgb = CUI.util.color.HexToRGB(this.options.config.colors[this.colorNames[(i * this.colorShadeNo + sh) + this.lowerLimit]]);
            shade = 'rgba(' + rgb.r + ',' + rgb.g + ',' + rgb.b + ',' + 1 + ')';
            if (CUI.util.color.isSameColor(shade,
              this.$hiddenInput.val())) {
              cssClass = 'is-selected';
            } else {
              cssClass = '';
            }
            html += '<td class="filled"><a style="background-color:' + shade + '" color="' + shade + '" colorName="' + this.colorNames[(i * this.colorShadeNo + sh) + this.lowerLimit] + '" class="' + cssClass + '">' + '</a></td>';
          }
        }
        html += '</tr>';
      }
      table.append('<tbody>' + html + '</tbody>');
      // click on a color box
      table.find('a').off('click.a').on('click.a', function(event) {
        event.stopPropagation();
        event.preventDefault();

        if (CUI.util.color.isSameColor(this.$hiddenInput.val(), $(event.target).attr('color'))) {
          return;
        }

        var previousSelected =  this.$element.find('table').find('.is-selected');
        previousSelected.removeClass('is-selected');
        previousSelected.find('i').remove();

        var selected = $(event.target);
        selected.addClass('is-selected');
        selected.html('<i class="coral-Icon coral-Icon--check"></i>');
        $(event.target).addClass('is-selected');

        var colorName = $(event.target).attr('colorName') ? $(event.target).attr('colorName') : '';
        this._fillSelectedColor(colorName, CUI.util.color.RGBAToHex($(event.target).attr('color')));

        this._setColor($(event.target).attr('color'));
      }.bind(this));

      var $navigator = this.$element.find('.palette-navigator');
      $navigator.find('.is-active').removeClass('is-active');
      $navigator.find('i[page="' + this.currentPage + '"]').addClass('is-active');

      return table;
    }
  });

  CUI.Widget.registry.register('colorpicker', CUI.Colorpicker);
}).call(this);
