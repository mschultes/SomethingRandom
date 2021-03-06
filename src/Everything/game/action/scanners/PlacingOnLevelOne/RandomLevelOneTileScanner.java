package Everything.game.action.scanners.PlacingOnLevelOne;

import Everything.game.action.scanners.NoValidActionException;
import Everything.models.Map;
import Everything.models.MapSpot;
import Everything.models.TileMapSpot;

public class RandomLevelOneTileScanner {

    //---------------
    // Public Methods

    public TileMapSpot scan(final Map map) throws NoValidActionException{

        boolean isVisited[][][] = new boolean[Map.size()][Map.size()][Map.size()];
        result = null;

        visit(map.getMiddleHexagonMapSpot(), isVisited, map);

        if (result == null) {
            throw new NoValidActionException("No valid spots found");
        }

        return result;
    }

    //----------------
    // Private Methods

    private TileMapSpot result;
    private void visit(final MapSpot currentMapSpot,
                              final boolean isVisited[][][],
                              final Map map){


        if (result != null || isVisited[currentMapSpot.getX()][currentMapSpot.getY()][currentMapSpot.getZ()]) {
            return;
        }

        isVisited[currentMapSpot.getX()][currentMapSpot.getY()][currentMapSpot.getZ()] = true;

        for (MapSpot adjMapSpots : currentMapSpot.getAdjacentMapSpots()) {

            try
            {
                result = getTileIfValid(adjMapSpots, map);
            }catch (NoValidActionException e){}

            if (satisfiesVisitingRequirements(map, adjMapSpots, isVisited)) {
                visit(adjMapSpots, isVisited, map);
            }
        }
    }

    private boolean satisfiesVisitingRequirements(final Map map, final MapSpot mapSpot, final boolean isVisited[][][]) {
        return map.getHexagon(mapSpot) != null &&
                !isVisited[mapSpot.getX()][mapSpot.getY()][mapSpot.getZ()];
    }

    private TileMapSpot getTileIfValid(final MapSpot mapSpot, final Map map) throws NoValidActionException {
        if(map.getHexagon(mapSpot) != null)
            throw new NoValidActionException("No valid spot found");

        MapSpot[] orderedAdjacentMapSpots = new MapSpot[7];

        orderedAdjacentMapSpots[0] = mapSpot.left();
        orderedAdjacentMapSpots[1] = mapSpot.topLeft();
        orderedAdjacentMapSpots[2] = mapSpot.topRight();
        orderedAdjacentMapSpots[3] = mapSpot.right();
        orderedAdjacentMapSpots[4] = mapSpot.bottomRight();
        orderedAdjacentMapSpots[5] = mapSpot.bottomLeft();
        orderedAdjacentMapSpots[6] = mapSpot.left();

        for(int i = 0; i < 6; i++) {
            if (map.getHexagon(orderedAdjacentMapSpots[i]) == null &&
                    map.getHexagon(orderedAdjacentMapSpots[i + 1]) == null) {

                return new TileMapSpot(mapSpot, orderedAdjacentMapSpots[i], orderedAdjacentMapSpots[i + 1]);
            }
        }

        throw new NoValidActionException("No Valid Spot Found");
    }
}
