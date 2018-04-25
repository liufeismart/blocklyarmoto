'use strict';
Blockly.JavaScript['avoidance_result'] = function(block) {
  var value = block.getFieldValue('NUM');
  return 'avoidance_result:' +value+"\n";
};


Blockly.JavaScript['avoidance_right'] = function(block) {
  return 'avoidance_right'+"\n";
};
Blockly.JavaScript['avoidance_left'] = function(block) {
  return 'avoidance_left'+"\n";
};

