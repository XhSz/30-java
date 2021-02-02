package p21_file;

import java.io.File;
import java.io.IOException;

public class CreateFolder {

	public static void main(String[] args) {
		File f = new File("E:\\0001.json");
//		if(!f.exists())f.mkdirs();
		if(!f.exists())
			try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
