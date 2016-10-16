package com.ardublock.translator.block.uart;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class Connect_Firefly  extends TranslatorBlock {

	public Connect_Firefly (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	//@Override
		public String toCode() throws SocketNullException, SubroutineNotDeclaredException
		{
			String ret = "";
			translator.addDefinitionCommand("int i, n, cur;\n int line = -110;\n boolean stop_line = false;\n " +
					"boolean obstacle = false;\n boolean stop_sign = false;\n boolean zebra = false;\n" + 
					"boolean give_way = false;\n boolean main_road = false;\n boolean pacBegin = true;\n" +
					"int light2 = 0;\n int light3 = 0;\n signed char buffer[20]; int time;");
			translator.addSetupCommand("Serial1.begin(115200);\n ");
			ret = 	"if (Serial1.available() > 4 && pacBegin) {\n" + 
					"time = millis();\n"+
					"cur = (signed char)Serial1.read();\n"+
					"if (cur == -126) {\n"+
					"buffer[0] = (signed char)Serial1.read();\n"+
					"buffer[1] = (signed char)Serial1.read();\n"+
					"buffer[2] = (signed char)Serial1.read();\n"+
					"buffer[3] = (signed char)Serial1.read();\n"+
					"n = buffer[3];\n"+
					"line = buffer[0];\n"+
					"pacBegin = false;\n\n"+						
					"if (buffer[1] == 1) stop_line = true;\n"+
					"else stop_line = false; \n"+
					"if (buffer[2] == 1) obstacle = true;\n"+
					"else obstacle = false;\n\n"+      
					"}\n"+
					"}\n\n"+  
					"if (Serial1.available() > n && !pacBegin) {\n"+
					"light2 = light3 = 0;\n"+
					"stop_sign = zebra = main_road = give_way = false;\n\n"+
					"for (int i = 0; i < n; i++) {\n"+
					"buffer[4 + i] = (signed char)Serial1.read();\n"+
					"switch(buffer[4 + i]) {\n"+
					"case 1:\n"+
					"stop_sign = true;\n"+
					"break;\n"+
					"case 2:\n"+
					"zebra = true;\n"+
					"break;\n"+
					"case 3:\n"+
					"main_road = true;\n"+
					"break;\n"+
					"case 4:\n"+
					"give_way = true;\n"+
					"break;\n"+
					"case 5:\n"+
					"light2 = 7; //Its green\n"+
					"break;\n"+
					"case 6:\n"+
					"light2 = 9; //Its red\n"+
					"break;\n"+
					"case 7:\n"+
					"light3 = 7; //Its green\n"+
					"break;\n"+
					"case 8:\n"+
					"light3 = 8; //Its yellow\n"+
					"break;\n"+
					"case 9:\n"+
					"light3 = 9; //Its red\n"+
					"break;\n"+
					"}\n"+
					"}\n"+
      
					"pacBegin = true;\n"+
					"if (millis() - time > 500) line = -110;\n"+
					"}\n";
			
			return ret;
		}
}
