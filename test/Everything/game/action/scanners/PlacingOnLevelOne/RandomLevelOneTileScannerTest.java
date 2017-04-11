package Everything.game.action.scanners.PlacingOnLevelOne;

import Everything.game.action.scanners.MapGenerator;
import Everything.game.action.scanners.NoValidActionException;
import Everything.models.*;
import org.junit.Test;

public class RandomLevelOneTileScannerTest {

    @Test
    public void circleTest() throws NoValidActionException{

        final Map map = MapGenerator.generateCircleWithOutsideLine(Terrain.GRASSLAND,
                Terrain.GRASSLAND,
                Terrain.GRASSLAND,
                Team.FRIENDLY,
                Team.FRIENDLY,
                Team.FRIENDLY);

        RandomLevelOneTileScanner randomLevelOneTileScanner = new RandomLevelOneTileScanner();

//        System.out.println(randomLevelOneTileScanner.scan(map).getM1());
//        System.out.println(randomLevelOneTileScanner.scan(map).getM2());
//        System.out.println(randomLevelOneTileScanner.scan(map).getM3());
    }

    @Test
    public void dotTest() throws NoValidActionException {
        final Map map = new Map();

        map.setHexagon(map.getMiddleHexagonMapSpot(), new Hexagon(Terrain.VOLCANO, 1, 1));

        RandomLevelOneTileScanner randomLevelOneTileScanner = new RandomLevelOneTileScanner();

        final TileMapSpot tileMapSpot = randomLevelOneTileScanner.scan(map);

//        System.out.println(tileMapSpot.getM1());
//        System.out.println(tileMapSpot.getM2());
//        System.out.println(tileMapSpot.getM3());
    }
}