package Everything.game.action.MapUpdater;


import Everything.Server.MoveObjects.EnemyMove;
import Everything.game.action.scanners.settlemenet.expanding.ExpansionToSpecificTerrainScanner;
import Everything.game.action.scanners.settlemenet.expanding.SettlementFromMapSpotScanner;
import Everything.models.*;
import Everything.game.action.scanners.*;


import java.util.ArrayList;

import static Everything.models.Team.ENEMY;


public class Updater {

    private Map map;
    private Team team;

    public Updater(final Map map, final Team team){
        this.map = map;
        this.team = team;
    }

    public void updateMap(EnemyMove enemymove) throws NoValidActionException {
        if(enemymove.getBuildType() == 2){

            MapSpot mapspot = new MapSpot(enemymove.getTileX(),enemymove.getTileY(),enemymove.getTileZ());
            MapSpot mapspot2 = new MapSpot(enemymove.getBuildX(),enemymove.getBuildY(),enemymove.getBuildZ());

            EnemyMoveExpand(enemymove.getMovenumber(), enemymove.getTileTerrainA(),enemymove.getTileTerrainB(), mapspot,enemymove.getOrientation(), 2,mapspot2,enemymove.getBuildTerrain());
        }
        else{
            MapSpot mapspot = new MapSpot(enemymove.getTileX(),enemymove.getTileY(),enemymove.getTileZ());
            MapSpot mapspot2 = new MapSpot(enemymove.getBuildX(),enemymove.getBuildY(),enemymove.getBuildZ());

            EnemyMove(enemymove.getMovenumber(), enemymove.getTileTerrainA(),enemymove.getTileTerrainB(), mapspot,enemymove.getOrientation(), enemymove.getBuildType(),mapspot2);
        }
    }

    public void setFirstTile(){

        MapSpot mapspot = new MapSpot(0,0,0);
        Hexagon hex1 = new Hexagon(Terrain.VOLCANO,1,0);
        map.setHexagon(mapspot, hex1);

        hex1 = new Hexagon(Terrain.JUNGLE,1,0);
        map.setHexagon(mapspot.topLeft(),hex1);

        hex1 = new Hexagon(Terrain.LAKE,1,0);
        map.setHexagon(mapspot.topRight(),hex1);

        hex1 = new Hexagon(Terrain.ROCK,1,0);
        map.setHexagon(mapspot.bottomLeft(),hex1);

        hex1 = new Hexagon(Terrain.GRASS,1,0);
        map.setHexagon(mapspot.bottomRight(),hex1);
    }

    //Does Not cover expand will be a different method
    //Move Number 1=Found Settlement 2=Expand 3=build Totoro 4=build tiger
    //int MoveNumber, Terrain A, Terrain B, MapSpot TileSpot, int Orientation, int turnChoice, MapSpot expandLocation
    //Order above, if doing Expand action use other method below
    public void EnemyMove(int moveNumber, Terrain A, Terrain B, MapSpot TileSpot, int Orientation, int turnChoice, MapSpot expandLocation){
        int TileLevel = 0;

        MapSpot hexSpot2 = new MapSpot(0,0,0);
        MapSpot hexSpot3 = new MapSpot(0,0,0);


        switch(Orientation) {
            case 1: hexSpot2 = TileSpot.topLeft(); hexSpot3 = TileSpot.topRight();
            break;
            case 2: hexSpot2 = TileSpot.topRight(); hexSpot3 = TileSpot.right();
            break;
            case 3: hexSpot2 = TileSpot.right(); hexSpot3 = TileSpot.bottomRight();
            break;
            case 4: hexSpot2 = TileSpot.bottomRight(); hexSpot3 = TileSpot.bottomLeft();
            break;
            case 5: hexSpot2 = TileSpot.bottomLeft(); hexSpot3 = TileSpot.left();
            break;
            case 6: hexSpot2 = TileSpot.left(); hexSpot3 = TileSpot.topLeft();
            break;

        }
        if(this.map.getHexagon(TileSpot) == null){

            TileLevel = 1;
        }
        else{
            TileLevel = this.map.getHexagon(TileSpot).getLevel() +1;
        }

        //Places Tiles
        Hexagon hex1 = new Hexagon(Terrain.VOLCANO, TileLevel,moveNumber);
        this.map.setHexagon(TileSpot, hex1);
        Hexagon hex2 = new Hexagon(A,TileLevel,moveNumber);
        this.map.setHexagon(hexSpot2, hex2);
        Hexagon hex3 = new Hexagon(B,TileLevel,moveNumber);
        this.map.setHexagon(hexSpot3, hex3);

        switch(turnChoice){
            case 1:
                this.map.getHexagon(expandLocation).addMeeplesAccordingToLevel(team);
                break;
            case 3:
                this.map.getHexagon(expandLocation).addTotoro(team);
                break;
            case 4:
                this.map.getHexagon(expandLocation).addTiger(team);
                break;
        }
    }

    public void EnemyMoveExpand(int MoveNumber, Terrain A, Terrain B, MapSpot TileSpot, int Orientation, int TurnChoice, MapSpot ExpandLocation, Terrain ExpandTerrain) throws NoValidActionException {
        MapSpot hexSpot2 = new MapSpot(0,0,0);
        MapSpot hexSpot3 = new MapSpot(0,0,0);

        int TileLevel = 0;

        switch(Orientation) {
            case 1: hexSpot2 = TileSpot.topLeft(); hexSpot3 = TileSpot.topRight();
                break;
            case 2: hexSpot2 = TileSpot.topRight(); hexSpot3 = TileSpot.right();
                break;
            case 3: hexSpot2 = TileSpot.right(); hexSpot3 = TileSpot.bottomRight();
                break;
            case 4: hexSpot2 = TileSpot.bottomRight(); hexSpot3 = TileSpot.bottomLeft();
                break;
            case 5: hexSpot2 = TileSpot.bottomLeft(); hexSpot3 = TileSpot.left();
                break;
            case 6: hexSpot2 = TileSpot.left(); hexSpot3 = TileSpot.topLeft();
                break;
        }

        if(this.map.getHexagon(TileSpot) == null){
            TileLevel = 1;
        }
        else{
            TileLevel = this.map.getHexagon(TileSpot).getLevel() +1;
        }

        //Places Tiles
        Hexagon hex1 = new Hexagon(Terrain.VOLCANO, TileLevel,MoveNumber);
        this.map.setHexagon(TileSpot, hex1);
        Hexagon hex2 = new Hexagon(A,TileLevel,MoveNumber);
        this.map.setHexagon(hexSpot2, hex2);
        Hexagon hex3 = new Hexagon(B,TileLevel,MoveNumber);
        this.map.setHexagon(hexSpot3, hex3);
        //

//        Settlement EnemySettlements = new Settlement(Team.getTeam());
//
//        SettlementsFactory Settlementfinder = new SettlementsFactory();
//
//        ArrayList<Settlement> ListOfSettlements = Settlementfinder.generateSettlements(map, Team.getTeam());
//
//        ExpansionToSpecificTerrainScanner Scanner = new ExpansionToSpecificTerrainScanner();
//
//
//        for(int i =0; i < ListOfSettlements.get(0).getMapSpots().size(); i++){
//            if(ListOfSettlements.get(0).getMapSpots().get(i).isEqual(ExpandLocation)){
//                EnemySettlements = ListOfSettlements.get(i);
//            }
//        }
//
//        ArrayList<MapSpot> ExpansionSpots =  Scanner.scan(EnemySettlements, this.map, ExpandTerrain);
//
//        for(int i = 0; i < ExpansionSpots.size(); i++){
//            this.map.getHexagon(ExpansionSpots.get(i)).addMeeplesAccordingToLevel(Team.getTeam());
//        }

        SettlementFromMapSpotScanner settlementFromMapSpotScanner = new SettlementFromMapSpotScanner();
        Settlement settlementToBeExpanded = settlementFromMapSpotScanner.scan(map, ExpandLocation);

        ExpansionToSpecificTerrainScanner expansionToSpecificTerrainScanner = new ExpansionToSpecificTerrainScanner();
        ArrayList<MapSpot> spotsToBeExpandedTo = expansionToSpecificTerrainScanner.scan(settlementToBeExpanded, map, ExpandTerrain);

        for (MapSpot m : spotsToBeExpandedTo) {
            map.getHexagon(m).addMeeplesAccordingToLevel(team);
        }

//        SettlementFromMapSpotScanner expansionFromMapSpotScanner = new SettlementFromMapSpotScanner();
//        expansionFromMapSpotScanner.scan(map, ExpandLocation, ExpandTerrain)
    }
}
