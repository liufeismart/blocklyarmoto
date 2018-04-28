'use strict';
Blockly.JavaScript['buzz'] = function(block) {
  // Generate JavaScript for moving forward or backwards.
  var value = block.getFieldValue('NUM');
  return 'buzz:'+value+'\n';
};