package p4u1.dd.phoarsquare;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class NameFactory {
	ArrayList<Name> names = new ArrayList<Name>();
	public NameFactory () {
	
		FileHandle file = Gdx.files.internal("data/yob1984.txt");
		BufferedReader reader = new BufferedReader(file.reader());
		String curr;
		
		try {
			while ((curr = reader.readLine()) != null) {
				String[] parts = curr.split(",");
				boolean tempGender;
				if (parts[1].contentEquals("M")) {
					tempGender = true;
				} else {
					tempGender = false;
				}
				
				names.add(new Name(parts[0], tempGender));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Name getRandomName() {
		Random rn = new Random();
		int i = rn.nextInt(names.size());
		
		return names.get(i);
	}
	
	public class Name {
		String name;
		boolean isMale;
		public Name() {
			name = "";
			isMale = false;
		}
		
		public Name(String name, boolean isMale) {
			this();
			this.name = name;
			this.isMale = isMale;
		}
	}
	
	
}
