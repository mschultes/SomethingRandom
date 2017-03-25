package models;

import java.util.Random;

public class RandomGenerator {

    private final static Random random = new Random();

    //---------------
    // Static Methods

    public static Terrain generateRandomTerrainType() {
        final int randomIndex = random.nextInt(Terrain.values().length);
        final Terrain[] values = Terrain.values();

        return values[randomIndex];
    }

    public static Team generateRandomTeam() {
        return (random.nextInt() == 0) ? Team.ENEMY : Team.FRIENDLY;
    }

    public static int generateRandomLevel() {
        return random.nextInt(20);
    }

    public static int generateRandomTileId() {
        return random.nextInt(47) + 1;
    }

    public static Hexagon generateRandomHexagon() {

        final Hexagon hexagon = new Hexagon(generateRandomTeam(),
                generateRandomTerrainType(),
                generateRandomLevel(),
                generateRandomTileId());

        return hexagon;
    }

}
