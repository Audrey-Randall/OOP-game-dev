package game.component.behavior;

import game.Entity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


public class CosmeticBehavior extends Behavior {
	private String sprite;
	
	public CosmeticBehavior(String spr){
		super();
		sprite = spr;
	}
	
	public String getSpriteName(){
		return sprite;
	}
	
	public static String chooseSpriteFile(){
		try {
			BufferedReader file = new BufferedReader(new FileReader(new File("src/res/textures/index.txt")));
			ArrayList<String> toPickFrom = new ArrayList<String>();
			boolean continueOn = true;
			while (continueOn){
				try {
					String[] line = file.readLine().split("\\s");
					if (line.length > 0){
						if (line[line.length-1].contains("hat") && line[line.length-1].contains("entity_")){
							toPickFrom.add(line[line.length-1]);
						}
					}
				} catch (Exception e) { // I may try to find another way to do this. (Chance)
					continueOn = false;
				}
			}
			if (toPickFrom.size() > 0){
				System.out.println(toPickFrom.size());
				return toPickFrom.get(new Random().nextInt(toPickFrom.size()));
			}
			
			return "";
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}
	
    @Override
    public void act() {}
    @Override
    public void onCollide(Entity e) {
    	if (e.getBehavior().getClass().getSimpleName().equals("PlayerBehavior")){
    		entity.kill();
    	}
    }
}
