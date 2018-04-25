Blockly.JavaScript['turtle_repeat_internal'] = function(block) {
      var value1 = block.getFieldValue("TIMES");
      var value2 = Blockly.JavaScript.statementToCode(block,"DO");
      var statements = value2.split('\n');
      return "repeat:"+value1+":"+ (statements.length-1)+'\n'+value2+'\n';
};