'use strict';
Blockly.JavaScript['electrical_machinery_1'] = function(block) {
  var value = block.getFieldValue('NUM');
  return 'electrical_machinery_1:'+value+'\n';
};
Blockly.JavaScript['electrical_machinery_2'] = function(block) {
  var value = block.getFieldValue('NUM');
  return 'electrical_machinery_2:'+value+'\n';
};


Blockly.JavaScript['controls_ifelse'] = function(a) {
    var b=0,c="";
    var d="";
    var f="";
    var d=Blockly.JavaScript.statementToCode(a, "IF"+b);
    var e=Blockly.JavaScript.statementToCode(a,"DO"+b);
    var statements1 = d.split('\n');
    var statements2 = e.split('\n');

    c = "if:"+(statements1.length-1)+":"+ (statements2.length-1);
    if(a.getInput("ELSE")) {
        f=Blockly.JavaScript.statementToCode(a,"ELSE");
        var statements3 = f.split('\n');
        c+=":"+ (statements3.length-1);
    }
    return c+"\n"+d+ e+f;
};
Blockly.JavaScript['controls_if']  = Blockly.JavaScript['controls_ifelse'];

Blockly.JavaScript['logic_compare'] = function(block) {
    var value1 = Blockly.JavaScript.statementToCode(block,"A");
    var value2 = block.getFieldValue("OP");
    var value3 = Blockly.JavaScript.statementToCode(block,"B");
    var statements1 = value1.split('\n');
    var statements2 = value3.split('\n');
    return "logic_compare:"+value2+":"+(statements1.length-1)+":"+(statements2.length-1)+"\n"+value1+value3;
};

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