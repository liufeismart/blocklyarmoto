'use strict';
Blockly.JavaScript['ultrasonic_result'] = function(block) {
  var value = block.getFieldValue('NUM');
  return 'tracking_result:' +value+"\n";
};
Blockly.JavaScript['ultrasonic'] = function(block) {
  return 'tracking_right'+"\n";
};