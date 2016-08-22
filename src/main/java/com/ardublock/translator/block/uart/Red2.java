package com.ardublock.translator.block.uart;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;

public class Red2 extends TranslatorBlock
{
	public Red2(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode()
	{
		return codePrefix + "9" + codeSuffix;
	}

}

