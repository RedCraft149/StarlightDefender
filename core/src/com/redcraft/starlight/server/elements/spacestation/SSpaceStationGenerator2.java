package com.redcraft.starlight.server.elements.spacestation;

import java.net.SocketAddress;
import java.util.*;

public class SSpaceStationGenerator2 {
    private static final int CONNECTOR = 0x10;
    private static final int MODULE = 0x20;
    private static final int SOLAR_PANEL = 0x40;
    private static final int RIGHT = 2; // 0001
    private static final int DOWN = 4;  // 0010
    private static final int LEFT = 8;  // 0100
    private static final int UP = 1;    // 1000
    private static final int[] DIRECTIONS = {UP,RIGHT,DOWN,LEFT}; // Right, Down, Left, Up
    private static final int GRID_SIZE = 10;
    private static final double EXPANSION_PROBABILITY = 0.6;
    private static final int MAX_TILES = 10;

    public static void main(String[] args) {
        int[][] spaceStation = generateSpaceStation();
        visualize(spaceStation);
    }

    public static void visualize(int[][] spaceStation) {
        for(int y = 0; y < GRID_SIZE; y++) {
            StringBuilder top = new StringBuilder();
            StringBuilder middle = new StringBuilder();
            StringBuilder bottom = new StringBuilder();
            for(int x = 0; x < GRID_SIZE; x++) {
                top.append(" ").append((spaceStation[x][y] & UP) != 0 ? "|" : " ").append(" ");
                middle.append((spaceStation[x][y] & LEFT) != 0 ? "-" : " ").append(type(spaceStation[x][y])).append((spaceStation[x][y] & RIGHT) != 0 ? "-" : " ");
                bottom.append(" ").append((spaceStation[x][y] & DOWN) != 0 ? "|" : " ").append(" ");
            }
            System.out.println(top.toString());
            System.out.println(middle.toString());
            System.out.println(bottom.toString());
        }
    }

    private static String type(int i) {
        if((i & 0x8000_0000) != 0) return " ";
        if((i & MODULE) != 0) return "M";
        if((i & SOLAR_PANEL) != 0) return "S";
        if((i & CONNECTOR) != 0) return "C";
        return " ";
    }

    public static int[][] generateSpaceStation() {
        int[][] grid = new int[GRID_SIZE][GRID_SIZE];
        for (int[] column : grid) Arrays.fill(column, 0);

        Queue<int[]> queue = new LinkedList<>();
        int centerX = GRID_SIZE / 2, centerY = GRID_SIZE / 2;
        grid[centerX][centerY] = CONNECTOR | (RIGHT | DOWN | LEFT | UP); // Fully connected Connector
        queue.add(new int[]{centerX, centerY});
        int totalTiles = 0;

        Random random = new Random();

        while (!queue.isEmpty() && totalTiles < MAX_TILES) {
            int[] cell = queue.poll();
            int x = cell[0], y = cell[1];
            int current = grid[x][y];
            for (int i = 0; i < DIRECTIONS.length; i++) {
                int dir = DIRECTIONS[i];
                int nx = x + (dir == RIGHT ? 1 : dir == LEFT ? -1 : 0);
                int ny = y + (dir == DOWN ? 1 : dir == UP ? -1 : 0);

                // Skip if out of bounds or already filled
                if (nx < 0 || nx >= GRID_SIZE || ny < 0 || ny >= GRID_SIZE || (grid[nx][ny] & (CONNECTOR | MODULE | SOLAR_PANEL)) != 0) continue;

                // Randomize whether to create a new part
                if (random.nextDouble() <= EXPANSION_PROBABILITY) {
                    int type = random.nextInt(3); // 0 = Connector, 1 = Module, 2 = Solar Panel
                    grid[x][y] |= dir;
                    if (type == 0) {
                        grid[nx][ny] = CONNECTOR | (1 << ((i + 2) % 4));
                    } else if (type == 1) {
                        grid[nx][ny] = MODULE | (1 << ((i + 2) % 4));
                    } else {
                        grid[nx][ny] = SOLAR_PANEL | (1 << ((i + 2) % 4));
                        continue;
                    }
                    queue.add(new int[]{nx, ny});
                    totalTiles++;
                } else {
                    // Mark the direction as open but do not create a part
                    grid[x][y] &= ~dir;
                }
            }
        }
        return grid;
    }

    public static List<SSpaceStationPart> convert(int[][] spaceStation, float dp, float cx, float cy) {
        List<SSpaceStationPart> parts = new LinkedList<>();
        for(int x = 0; x < GRID_SIZE; x++) {
            for(int y = 0; y < GRID_SIZE; y++) {
                SSpaceStationPart part = convert(spaceStation[x][y],x,y,dp,cx,cy);
                if(part == null) continue;
                parts.add(part);
            }
        }
        return parts;
    }

     public static SSpaceStationPart convert(int i, int x, int y, float dp, float cx, float cy) {
        if((i & CONNECTOR) != 0) {
            SSpaceStationConnector connector = new SSpaceStationConnector(UUID.randomUUID(),i & 0xF);
            connector.setPosition(x*dp+cx,y*dp+cy);
            return connector;
        }
        if((i & MODULE) != 0) {
            SSpaceStationModule module = new SSpaceStationModule(UUID.randomUUID(),i & 0xF);
            module.setPosition(x*dp+cx,y*dp+cy);
            return module;
        }
        if((i & SOLAR_PANEL) != 0) {
            int panelDirection = convertDirectionToSolarPanel(i & 0xF);
            if(panelDirection == -1) return null;
            SSpaceStationSolarPanel solarPanel = new SSpaceStationSolarPanel(UUID.randomUUID(),panelDirection);
            solarPanel.setPosition(x*dp+cx,y*dp+cy);
            return solarPanel;
        }
        return null;
    }

    private static int convertDirectionToSolarPanel(int dir) {
        switch (dir) {
            case UP: return 0;
            case RIGHT: return 1;
            case DOWN: return 2;
            case LEFT: return 3;
            default: return -1;
        }
    }
}
