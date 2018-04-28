'use strict';
Blockly.JavaScript['light_up'] = function(block) {
  return 'light_up\n';
};

Blockly.JavaScript['light_down'] = function(block) {
  // Generate JavaScript for moving forward or backwards.
  var value = block.getFieldValue('VALUE');
  return 'light_down\n';
};
