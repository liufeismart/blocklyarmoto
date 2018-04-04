'use strict';
Blockly.JavaScript['electrical_machinery_1'] = function(block) {
  var value = block.getFieldValue('NUM');
  return 'electrical_machinery_1:'+value+'\n';
};
Blockly.JavaScript['electrical_machinery_2'] = function(block) {
  var value = block.getFieldValue('NUM');
  return 'electrical_machinery_2:'+value+'\n';
};
Blockly.JavaScript['delay'] = function(block) {
  var value = block.getFieldValue('NUM');
  return 'delay:' +value+'\n';
};


Blockly.JavaScript['turtle_repeat_internal'] = function(block) {
      var value1 = block.getFieldValue("TIMES");
      var value2 = Blockly.JavaScript.statementToCode(block,"DO");
      var statements = value2.split('\n');

      return "repeat:"+value1+":"+ (statements.length-1)+'\n'+value2+'\n';
//    var b=a.getField("TIMES")?String(Number(a.getFieldValue("TIMES"))):Blockly.JavaScript.valueToCode(a,"TIMES",Blockly.JavaScript.ORDER_ASSIGNMENT)||"0",c=Blockly.JavaScript.statementToCode(a,"DO");
//    var c=Blockly.JavaScript.addLoopTrap(c,a.id);
//    a="";
//    var d=Blockly.JavaScript.variableDB_.getDistinctName("count",Blockly.Variables.NAME_TYPE),e=b;
//    b.match(/^\w+$/)||Blockly.isNumber(b)||(e=Blockly.JavaScript.variableDB_.getDistinctName("repeat_end",Blockly.Variables.NAME_TYPE),
//a+="var "+e+" = "+b+";\n");
//    return a+("for (var "+d+" = 0; "+d+" < "+e+"; "+d+"++) {\n"+c+"}\n")}
};