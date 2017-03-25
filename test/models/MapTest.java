package models;


import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;


public class MapTest {

    final private Random random = new Random();

    @Test
    public void getMapSizeTest() {
        final Map map = new Map();

        assertEquals(24 * 2 * 2 * 3 + 10, map.getMapSize());
    }

    @Test
    public void getMapTest() {
        final Map map = new Map();

        final Hexagon hexagon = RandomGenerator.generateRandomHexagon();

        map.addHexagon(map.getMiddleHexagonMapSpot(), hexagon);

        final Hexagon[][] hexagonArray = map.getHexagonArray();

        assertEquals(map.getMapSize(), hexagonArray.length);
        assertEquals(map.getMapSize(), hexagonArray[0].length);

        final Hexagon returnedHex = hexagonArray[map.getMapSize() / 2][map.getMapSize() / 2];

        assertEquals(hexagon.getLevel(), returnedHex.getLevel());
        assertEquals(hexagon.getNumberOfMeeples(), returnedHex.getNumberOfMeeples());
        assertEquals(hexagon.getTerrainType(), returnedHex.getTerrainType());
        assertEquals(hexagon.isHasTotoro(), returnedHex.isHasTotoro());
        assertEquals(hexagon.isEmpty(), returnedHex.isEmpty());
    }

    @Test
    public void addHexagonTest() {
        final Map map = new Map();

        final int x = random.nextInt(map.getMapSize());
        final MapSpot mapSpot = new MapSpot(x, (x % 2 == 0) ? 0 : 1);
        final Hexagon hexagon = RandomGenerator.generateRandomHexagon();

        map.addHexagon(mapSpot, hexagon);
        assertEquals(map.getHexagon(mapSpot), hexagon);
    }

    @Test
    public void getMiddleHexagonTest() {
        final Map map = new Map();
        final MapSpot mapSpot = map.getMiddleHexagonMapSpot();

        final Hexagon hexagon = RandomGenerator.generateRandomHexagon();

        map.addHexagon(mapSpot, hexagon);
        assertEquals(map.getMiddleHexagon(), hexagon);
    }

}
