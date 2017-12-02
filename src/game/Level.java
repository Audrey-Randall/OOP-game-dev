package game;

import mote4.scenegraph.Window;
import mote4.util.FileIO;

import java.util.ArrayList;
import java.util.Hashtable;

public class Level {
	private Hashtable<String, EntityFactory.EntityType> legend = new Hashtable<>();
	private Hashtable<String, String> extra = new Hashtable<>();
	private ArrayList<Entity> entities;
	private Entity player;
	private int levelLength;
	private String levelName;
	
	public Level(String filename, EntityFactory factory) {
        levelName = filename;
        String lastChar = filename.substring(filename.length()-1);
        filename = lastChar.equals("\r") ? filename.substring(0, filename.length()-1) : filename;
        String[] entityFile;
        try {
            entityFile = FileIO.getString("/res/files/" + filename + ".txt").split("\n");
            entities = new ArrayList<>();
            int yOffset = findLegendAndMapGap(entityFile);
            createLegend(entityFile, yOffset);
            levelLength = entityFile[yOffset].length()*32;
            createEntities(entityFile, yOffset, factory);
        } catch (NullPointerException e) {
            System.err.println("Error reading level file: /res/files/" + filename + ".txt");
            Window.destroy();
        }
	}
	
	public int findLegendAndMapGap(String[] mapAndLegend){
		int i = 0;
		for (; i < mapAndLegend.length; i++){
			if (mapAndLegend[i].length() <=  2){
				break;
			}
		}
		return i+1;
	}
	
	public void createLegend(String[] mapAndLegend, int yOffset){
		int i = 0;
		for (; i < yOffset; i++){
			if (mapAndLegend[i].length() > 2 && mapAndLegend[i].charAt(1) == ' '){
				String key = mapAndLegend[i].substring(0, 1);
				String rest = mapAndLegend[i].substring(2);
				if (rest.indexOf(" ") == -1){
					legend.put(key, EntityFactory.EntityType.valueOf(rest));
					extra.put(key,  "");
				}
				else{
					legend.put(key, EntityFactory.EntityType.valueOf(rest.substring(0, rest.indexOf(" "))));
					extra.put(key, rest.substring(rest.indexOf(" ")+1));
				}
			}
		}
	}
	
	public void createEntities(String[] mapAndLegend, int yOffset, EntityFactory factory){
		entities.add(factory.getEntity(EntityFactory.EntityType.TILEMAP, levelName +"_map"));
		for (int i = yOffset; i < mapAndLegend.length; i++){
			for (int j = 0; j < mapAndLegend[i].length(); j++){
				EntityFactory.EntityType type = legend.get(mapAndLegend[i].substring(j,j+1));
				String extraValue = extra.get(mapAndLegend[i].substring(j, j+1));
				if (type != null){
					Entity e = factory.getEntity(type, extraValue);
					System.out.println(e.getBehavior().getClass().getSimpleName());
					if (e.getBehavior().getClass().getSimpleName().equals("PlayerBehavior")){
						player = e;
					}
					// Hard coded for now; Please fix.
					e.moveTo(j*32, (i-yOffset)*32);
					entities.add(e);
				}
			}
		}
	}
	
	public ArrayList<Entity> getEntities(){
		return entities;
	}
	
	public Entity getPlayer(){
		return player;
	}
	
	public int getLevelLength(){
		return levelLength;
	}

	public String getLevelName() { return levelName; }
}
