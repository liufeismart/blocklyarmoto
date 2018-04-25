'use strict';
Blockly.JavaScript['variate_front_direction'] = function(block) {
  return 'variate_front_distance'+"\n";
};
Blockly.JavaScript['variate_left_direction'] = function(block) {
  return 'variate_left_distance'+"\n";
};
Blockly.JavaScript['variate_right_direction'] = function(block) {
  return 'variate_right_distance'+"\n";
};

Blockly.JavaScript['variate_set'] = function(block) {
    var value1 = Blockly.JavaScript.statementToCode(block,"A");
    var value2 = Blockly.JavaScript.statementToCode(block,"B");
    var statements1 = value1.split('\n');
    var statements2 = value2.split('\n');
    return "variate_set:"+(statements1.length-1)+":"+(statements2.length-1)+"\n"+value1+value2;
};