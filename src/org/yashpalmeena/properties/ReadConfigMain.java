package org.yashpalmeena.properties;

import java.io.IOException;

public class ReadConfigMain {

	
	public static void main(String[] args) throws IOException {
		String[] values = new String[] {"Yashpal", "Meena"};
		System.out.println(Config.get("greetings", values));
	}
}
