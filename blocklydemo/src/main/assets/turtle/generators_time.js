'use strict';
Blockly.JavaScript['last_time'] = function(block) {
  var value = block.getFieldValue('NUM');
  var doStr = Blockly.JavaScript.statementToCode(block,"DO");
  var statements1 = doStr.split('\n');
  var c = "last_time:"+value+":"+(statements1.length-1);
  return c+'\n'+doStr;
};
