package main;

import mote4.util.FileIO;

public class Tilemap {

    public final int[][] TILES;
    public final int TILE_SIZE = 32;

    public Tilemap(String filename) {
		System.out.println("/res/files/"+filename+".txt");
        String file = FileIO.getString("/res/files/"+filename+".txt");
        String[] map = file.split("\n");

        int width = map[0].length();
        if (map.length != 15)
            throw new IllegalArgumentException("Map files MUST have 15 rows.");
        int[][] rawTiles = new int[width][15];
        for (int x = 0; x < rawTiles.length; x++)
            for (int y = 0; y < rawTiles[0].length; y++)
            {
                rawTiles[x][y] = (int)(map[y].charAt(x))-48;
            }
        TILES = makePretty(rawTiles);
    }

    /**
     * Convert tile index 1 (solid TILES) to different TILES based on surrounding TILES
     * @param oldTiles
     */
    private int[][] makePretty(int[][] oldTiles) {
        int[][] tiles = new int[oldTiles.length][oldTiles[0].length];
        for (int x = 0; x < oldTiles.length; x++)
            for (int y = 0; y < oldTiles[0].length; y++) {
                int tile = isSolid(oldTiles, x,y);
                if (tile == 1) {
                    int surrounding = 0;
                    surrounding += isSolid(oldTiles, x+1,y); // right
                    surrounding += isSolid(oldTiles, x-1,y) * 10; // left
                    surrounding += isSolid(oldTiles, x,y+1) * 100; // down
                    surrounding += isSolid(oldTiles, x,y-1) * 1000; //up

                    switch (surrounding)
                    {
                        case 0: // no surrounding
                            tiles[x][y] = 52;
                            break;

                        case 1: // only right
                            tiles[x][y] = 43;
                            break;
                        case 10: // only left
                            tiles[x][y] = 44;
                            break;
                        case 100: // only down
                            tiles[x][y] = 45;
                            break;
                        case 1000: // only up
                            tiles[x][y] = 53;
                            break;

                        case 11: // right, left
                            tiles[x][y] = 54;
                            break;
                        case 1100: // up, down
                            tiles[x][y] = 51;
                            break;

                        case 101: // right, down
                            tiles[x][y] = 19;
                            break;
                        case 110: // left, down
                            tiles[x][y] = 21;
                            break;
                        case 1001: // right, up
                            tiles[x][y] = 35;
                            break;
                        case 1010: // left, up
                            tiles[x][y] = 37;
                            break;

                        case 1101: // right, up, down
                            tiles[x][y] = 27;
                            break;
                        case 1110: // left, up, down
                            tiles[x][y] = 29;
                            break;
                        case 1011: // right, left, up
                            tiles[x][y] = 36;
                            break;
                        case 111: // right, left, down
                            tiles[x][y] = 20;
                            break;

                        case 1111: // surrounded
                            int corners = 0;
                            corners += isSolid(oldTiles, x+1,y+1); // bottom right
                            corners += isSolid(oldTiles, x-1,y+1) * 10; // bottom left
                            corners += isSolid(oldTiles, x+1,y-1) * 100; // upper right
                            corners += isSolid(oldTiles, x-1,y-1) * 1000; // upper left
                            switch (corners) {
                                case 1110:
                                    tiles[x][y] = 22;
                                    break;
                                case 1101:
                                    tiles[x][y] = 23;
                                    break;
                                case 1011:
                                    tiles[x][y] = 30;
                                    break;
                                case 111:
                                    tiles[x][y] = 31;
                                    break;
                                default:
                                    tiles[x][y] = 28;
                            }
                            break;

                        default:
                            throw new IllegalStateException("Unrecognized tile state: "+surrounding);
                    }
                } else
                    tiles[x][y] = oldTiles[x][y];
            }
        return tiles;
    }

    private int isSolid(int[][] tiles, int x, int y) {
        int tile = tiles[clamp(x,0,tiles.length-1)][clamp(y,0,tiles[0].length-1)];
        if (tile == 1)
            return 1;
        else
            return 0;
    }

    private int clamp(int x, int min, int max) {
        return Math.max(min, Math.min(max, x));
    }
}
