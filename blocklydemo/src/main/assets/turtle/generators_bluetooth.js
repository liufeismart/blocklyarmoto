'use strict';
Blockly.JavaScript['light_up'] = function(block) {
  // Generate JavaScript for moving forward or backwards.
  var value = block.getFieldValue('VALUE');
  return 'Light.lightUp:'+value+'\n';
};

Blockly.JavaScript['light_down'] = function(block) {
  // Generate JavaScript for moving forward or backwards.
  var value = block.getFieldValue('VALUE');
  return 'Light.lightdown:'+value+'\n';
};
Blockly.JavaScript['delay'] = function(block) {
  // Generate JavaScript for moving forward or backwards.
  var value = block.getFieldValue('NUM');
  return 'delay:' +value+'\n';
};