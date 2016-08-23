package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class str_to_int extends TranslatorBlock
{
	public str_to_int(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode()  throws SocketNullException, SubroutineNotDeclaredException
	{
		String str;
		
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		str = translatorBlock.toCode();
		String internalVariableName = translator.getNumberVariable(label);
		if (internalVariableName == null)
		{
			internalVariableName = translator.buildVariableName(label);
			translator.addNumberVariable(label, internalVariableName);
			translator.addDefinitionCommand("String " + internalVariableName + " = " + str + " ;");
//			translator.addSetupCommand(internalVariableName + " = \"\";");
		}
		String ret = internalVariableName + ".toInt()";
		return codePrefix + ret + codeSuffix;
	}

}
