package main;

import mote4.util.FileIO;

public class Tilemap {

    public final int[][] tiles;
    public final int TILE_SIZE = 32;

    public Tilemap() {
        String file = FileIO.getString("/res/files/level1.txt");
        String[] map = file.split("\n");

        int width = 60;
        tiles = new int[width][15];
        for (int x = 0; x < tiles.length; x++)
            for (int y = 0; y < tiles[0].length; y++)
            {
                tiles[x][y] = (int)(map[y].charAt(x))-48;
            }
    }
}
