'use strict';
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