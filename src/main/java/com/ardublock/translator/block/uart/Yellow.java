package com.ardublock.translator.block.uart;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;

public class Yellow extends TranslatorBlock
{
	public Yellow(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode()
	{
		return codePrefix + "8" + codeSuffix;
	}

}

