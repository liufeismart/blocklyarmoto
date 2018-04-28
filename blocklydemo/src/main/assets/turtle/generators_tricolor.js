'use strict';
Blockly.JavaScript['tricolor'] = function(block) {
  // Generate JavaScript for moving forward or backwards.
  var value1 = block.getFieldValue('NUM1');
  var value2 = block.getFieldValue('NUM2');
  var value3 = block.getFieldValue('NUM3');
  return 'tricolor:'+value1+":"+value2+":"+value3+'\n';
};