package com.ardublock.translator.block.uart;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class Encoder_init  extends TranslatorBlock {

	public Encoder_init (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	//@Override
		public String toCode() throws SocketNullException, SubroutineNotDeclaredException
		{
			String send_data = "";
			String doEncoder = "";
			String ret = "";
			TranslatorBlock diam = this.getRequiredTranslatorBlockAtSocket(0);
			
			send_data = "\n\n void serial_send_data()\n"+
						"{\n"+
						"	real_speed = encoder0Pos / (double)(millis() - last_speed_update) * factor / 10;\n"+
						"	encoder0Pos = 0;\n"+
						"	last_speed_update = millis();\n"+
						"	Serial1.print('F');\n"+
						"	Serial1.print(real_speed);\n"+
						"	Serial1.print('E');\n"+
						"}\n\n";
						
			doEncoder = "\n\n void doEncoder() \n { \n encoder0Pos++; \n } \n\n";
			
			translator.addDefinitionCommand("#define encoder0PinA  2 \n double factor; \n volatile unsigned int encoder0Pos = 0; " +
											"\n float real_speed = 0; \n unsigned long last_speed_update = 0; \n" +
											send_data + doEncoder);
			translator.addSetupCommand("int diameter = " + diam.toCode() + "; \n" +
										"factor = diameter * 0.130833333; \n" +
										"pinMode(encoder0PinA, INPUT); \n" +
										"attachInterrupt(1, doEncoder, CHANGE);\n");
			translator.addSetupCommand("Serial1.begin(115200);\n ");
				
			ret = "\n if (millis() - last_speed_update > 1000) serial_send_data(); \n";
			
			return codePrefix + ret + codeSuffix;
		}
}
