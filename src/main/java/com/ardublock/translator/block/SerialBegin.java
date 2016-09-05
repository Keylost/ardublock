package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class SerialBegin extends TranslatorBlock
{
	public SerialBegin(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		TranslatorBlock speed = this.getRequiredTranslatorBlockAtSocket(0);
		
		translator.addSetupCommand("Serial.begin(" + speed.toCode() + ");");
	
		return codePrefix + codeSuffix;
	}
}
