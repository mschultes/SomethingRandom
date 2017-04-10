//package Everything.game.action.ai;
//
//import Everything.Server.MoveObjects.MakeMoveInstruction;
//import Everything.Server.MoveObjects.WeJustDidThisMove;
//import Everything.game.action.handlers.FirstLevelTileAdditionHandler;
//import Everything.game.action.handlers.NukingAndStackingHandler;
//import Everything.game.action.handlers.SettlementExpansionHandler;
//import Everything.game.action.handlers.SettlementFoundingHandler;
//import Everything.game.action.scanners.NoValidActionException;
//import Everything.game.action.scanners.Nuking.SettlementAdjacentMapSpotsScanner;
//import Everything.game.action.scanners.Nuking.SettlementAdjacentVolcanoesScanner;
//import Everything.game.action.scanners.Nuking.SettlementLevelOneTwoSpotsNukingScanner;
//import Everything.game.action.scanners.PlacingOnLevelOne.SettlementLevelOneTilePlacementScanner;
//import Everything.game.action.scanners.SettlementFounding.FoundingNextToSettlementScanner;
//import Everything.game.action.scanners.SettlementFounding.RandomSettlementFoundingScanner;
//import Everything.game.action.scanners.SettlementsFactory;
//import Everything.game.action.scanners.settlemenet.expanding.ExpansionToSpecificTerrainScanner;
//import Everything.game.action.scanners.settlemenet.expanding.SettlementExpansionMeeplesCost;
//import Everything.game.action.scanners.settlemenet.expanding.TigerSpotScanner;
//import Everything.game.action.scanners.settlemenet.expanding.TotoroSpotScanner;
//import Everything.models.*;
//
//import java.util.ArrayList;
//
//// TODO: 4/9/2017 1. Place tile in random spot 2. Found villager in random spot 3. Optimal expansion logic
//
//public class AIBot {
//
//    //-----------
//    // attributes
//
//    private SettlementsFactory settlementsFactory;
//
//    // Handlers
//    private FirstLevelTileAdditionHandler firstLevelTileAdditionHandler;
//    private NukingAndStackingHandler nukingAndStackingHandler;
//    private SettlementExpansionHandler settlementExpansionHandler;
//    private SettlementFoundingHandler settlementFoundingHandler;
//
//    // Nuking
//    private SettlementAdjacentMapSpotsScanner settlementAdjacentMapSpotsScanner;
//    private SettlementAdjacentVolcanoesScanner settlementAdjacentVolcanoesScanner;
//    private SettlementLevelOneTwoSpotsNukingScanner settlementLevelOneTwoSpotsNukingScanner;
//
//    // Placing
//    private SettlementLevelOneTilePlacementScanner settlementLevelOneTilePlacementScanner;
//
//    //Settlement Expansion
//    private ExpansionToSpecificTerrainScanner expansionToSpecificTerrainScanner;
//    private SettlementExpansionMeeplesCost settlementExpansionMeeplesCost;
//    private TotoroSpotScanner totoroSpotScanner;
//    private TigerSpotScanner tigerSpotScanner;
//
//    //Settlement Founding
//    private FoundingNextToSettlementScanner foundingNextToSettlementScanner;
//    private RandomSettlementFoundingScanner randomSettlementFoundingScanner;
//
//    //-------------
//    // Constructors
//
//    public AIBot(final SettlementsFactory settlementsFactory,
//                 final FirstLevelTileAdditionHandler firstLevelTileAdditionHandler,
//                 final NukingAndStackingHandler nukingAndStackingHandler,
//                 final SettlementExpansionHandler settlementExpansionHandler,
//                 final SettlementFoundingHandler settlementFoundingHandler,
//                 final SettlementAdjacentMapSpotsScanner settlementAdjacentMapSpotsScanner,
//                 final SettlementAdjacentVolcanoesScanner settlementAdjacentVolcanoesScanner,
//                 final SettlementLevelOneTwoSpotsNukingScanner settlementLevelOneTwoSpotsNukingScanner,
//                 final SettlementLevelOneTilePlacementScanner settlementLevelOneTilePlacementScanner,
//                 final ExpansionToSpecificTerrainScanner expansionToSpecificTerrainScanner,
//                 final SettlementExpansionMeeplesCost settlementExpansionMeeplesCost,
//                 final TotoroSpotScanner totoroSpotScanner,
//                 final TigerSpotScanner tigerSpotScanner,
//                 final FoundingNextToSettlementScanner foundingNextToSettlementScanner,
//                 final RandomSettlementFoundingScanner randomSettlementFoundingScanner) {
//        this.settlementsFactory = settlementsFactory;
//        this.firstLevelTileAdditionHandler = firstLevelTileAdditionHandler;
//        this.nukingAndStackingHandler = nukingAndStackingHandler;
//        this.settlementExpansionHandler = settlementExpansionHandler;
//        this.settlementFoundingHandler = settlementFoundingHandler;
//        this.settlementAdjacentMapSpotsScanner = settlementAdjacentMapSpotsScanner;
//        this.settlementAdjacentVolcanoesScanner = settlementAdjacentVolcanoesScanner;
//        this.settlementLevelOneTwoSpotsNukingScanner = settlementLevelOneTwoSpotsNukingScanner;
//        this.settlementLevelOneTilePlacementScanner = settlementLevelOneTilePlacementScanner;
//        this.expansionToSpecificTerrainScanner = expansionToSpecificTerrainScanner;
//        this.settlementExpansionMeeplesCost = settlementExpansionMeeplesCost;
//        this.totoroSpotScanner = totoroSpotScanner;
//        this.tigerSpotScanner = tigerSpotScanner;
//        this.foundingNextToSettlementScanner = foundingNextToSettlementScanner;
//        this.randomSettlementFoundingScanner = randomSettlementFoundingScanner;
//    }
//
//    //--------
//    // Methods
//
//    public WeJustDidThisMove playTurn(final MakeMoveInstruction makeMoveInstruction, final Map map, final Player player) {
//        String[] tiles = makeMoveInstruction.getTile().split("\\+");
//
//        final Tile tile = new Tile(new Hexagon(Terrain.valueOf(tiles[0]), makeMoveInstruction.getMoveNumber()),
//                new Hexagon(Terrain.valueOf(tiles[1]), makeMoveInstruction.getMoveNumber()),
//                new Hexagon(Terrain.VOLCANO, makeMoveInstruction.getMoveNumber()));
//
//        doATilePlacementMove(map, player, tile);
//        doABuildMove(map, player);
//
//        final WeJustDidThisMove weJustDidThisMove = new WeJustDidThisMove();
//    }
//
//    private void doATilePlacementMove(final Map map, final Player player, final Tile tile) {
//        final ArrayList<Settlement> friendlySettlements
//                = settlementsFactory.generateSettlements(player.getTeam());
//
//        final ArrayList<Settlement> enemySettlements =
//                settlementsFactory.generateSettlements((player.getTeam() == Team.FRIENDLY) ? Team.ENEMY : Team.FRIENDLY);
//
//        if (player.getNumberOfTotorosLeft() > 0) {
//            if (friendlySettlements.size() == 0) {
//                // Place tile in any random spot (first turn only)
//                return;
//            }
//            else {
//                for (Settlement friendlySettlement : friendlySettlements) {
//                    if (friendlySettlement.size() >= 5 && friendlySettlement.getNumberOfTotoros() < 1) {
//                        try {
//
//                            TileMapSpot tileMapSpot
//                                    = settlementLevelOneTilePlacementScanner.findTileMapSpotToPlaceTileAroundSettlment(map, friendlySettlement);
//
//                            map.setHexagon(tileMapSpot.getM1(), tile.getH1());
//                            map.setHexagon(tileMapSpot.getM2(), tile.getH2());
//                            map.setHexagon(tileMapSpot.getM3(), tile.getH3());
//                            return;
//                        } catch (NoValidActionException e) {}
//                    }
//                    else if (friendlySettlement.size() == 4 && friendlySettlement.getNumberOfTotoros() < 1) {
//                        try {
//
//                            TileMapSpot tileMapSpot
//                                    = settlementLevelOneTilePlacementScanner.findTileMapSpotToPlaceTileAroundSettlment(map, friendlySettlement);
//
//                            map.setHexagon(tileMapSpot.getM1(), tile.getH1());
//                            map.setHexagon(tileMapSpot.getM2(), tile.getH2());
//                            map.setHexagon(tileMapSpot.getM3(), tile.getH3());
//                            return;
//                        } catch (NoValidActionException e) {}
//                    }
//                    else if (friendlySettlement.size() == 3 && friendlySettlement.getNumberOfTotoros() < 1) {
//                        try {
//
//                            TileMapSpot tileMapSpot
//                                    = settlementLevelOneTilePlacementScanner.findTileMapSpotToPlaceTileAroundSettlment(map, friendlySettlement);
//
//                            map.setHexagon(tileMapSpot.getM1(), tile.getH1());
//                            map.setHexagon(tileMapSpot.getM2(), tile.getH2());
//                            map.setHexagon(tileMapSpot.getM3(), tile.getH3());
//                            return;
//                        } catch (NoValidActionException e) {}
//                    }
//                    else if (friendlySettlement.size() == 2 && friendlySettlement.getNumberOfTotoros() < 1) {
//                        try {
//
//                            TileMapSpot tileMapSpot
//                                    = settlementLevelOneTilePlacementScanner.findTileMapSpotToPlaceTileAroundSettlment(map, friendlySettlement);
//
//                            map.setHexagon(tileMapSpot.getM1(), tile.getH1());
//                            map.setHexagon(tileMapSpot.getM2(), tile.getH2());
//                            map.setHexagon(tileMapSpot.getM3(), tile.getH3());
//                            return;
//                        } catch (NoValidActionException e) {}
//                    }
//                    else if (friendlySettlement.size() == 1 && friendlySettlement.getNumberOfTotoros() < 1) {
//                        try {
//
//                            TileMapSpot tileMapSpot
//                                    = settlementLevelOneTilePlacementScanner.findTileMapSpotToPlaceTileAroundSettlment(map, friendlySettlement);
//
//                            map.setHexagon(tileMapSpot.getM1(), tile.getH1());
//                            map.setHexagon(tileMapSpot.getM2(), tile.getH2());
//                            map.setHexagon(tileMapSpot.getM3(), tile.getH3());
//                            return;
//                        } catch (NoValidActionException e) {}
//                    }
//                    else {
//                        // Place tile in any random spot
//                        return;
//                    }
//                }
//            }
//
//        }
//        else if (player.getNumberOfMeeplesLeft() > 0) {
//            // Place tile in any random spot
//            return;
//        }
//        else {
//            return; // Game Over
//        }
//
//    }
//
//    private void doABuildMove(final Map map, final Player player) {
//        final ArrayList<Settlement> friendlySettlements
//                = settlementsFactory.generateSettlements(player.getTeam());
//
//        if (player.getNumberOfTotorosLeft() > 0) {
//            if (friendlySettlements.size() == 0) {
//                try{
//                    // Place villager on any hex
//                    final MapSpot foundingSpots;
//                        foundingSpots = randomSettlementFoundingScanner.scan(map);
//                    map.getHexagon(foundingSpots).addMeeplesAccordingToLevel(Team.FRIENDLY);
//                    return;
//                } catch (NoValidActionException e) {}
//                    return;
//            }
//
//            else {
//                for (Settlement friendlySettlement : friendlySettlements) {
//                    if (friendlySettlement.size() >= 5 && friendlySettlement.getNumberOfTotoros() < 1) {
//                        try {
//
//                            ArrayList<MapSpot> totoroSpots = totoroSpotScanner.scan(friendlySettlement, map);
//
//                            for (MapSpot totoroSpot : totoroSpots) {
//                                if (map.getHexagon(totoroSpot).getLevel() < 3) {
//                                    map.getHexagon(totoroSpot).addTotoro(Team.FRIENDLY);
//                                    return;
//                                }
//                            }
//
//                            map.getHexagon(totoroSpots.get(0)).addTotoro(Team.FRIENDLY);
//                            return;
//
//                        } catch (NoValidActionException e) {}
//                    }
//
//                    else if (friendlySettlement.size() == 4 && friendlySettlement.getNumberOfTotoros() < 1) {
//                        try {
//                            final ArrayList<MapSpot> foundingSpots = foundingNextToSettlementScanner.scan(friendlySettlement, map);
//                            map.getHexagon(foundingSpots.get(0)).addMeeplesAccordingToLevel(Team.FRIENDLY);
//                            return;
//                        } catch (NoValidActionException e) {}
//
//                    }
//
//                    else if (friendlySettlement.size() == 3 && friendlySettlement.getNumberOfTotoros() < 1) {
//                        try {
//                            final ArrayList<MapSpot> expandableMapSpots
//                                    = meeplesExpandableSpotsScanner.scan(friendlySettlement, map);
//
//                            if ((expandableMapSpots.size() + friendlySettlement.size()) == 5) {
//                                settlementExpansionHandler.expandWithMeeples(friendlySettlement.getMapSpots().get(0));
//                                return;
//                            }
//
//                            else {
//                                ArrayList<MapSpot> foundingSpots = foundingNextToSettlementScanner.scan(friendlySettlement, map);
//                                map.getHexagon(foundingSpots.get(0)).addMeeplesAccordingToLevel(Team.FRIENDLY);
//                                return;
//                            }
//                        } catch (NoValidActionException e) {}
//                    }
//
//                    else if (friendlySettlement.size() == 2 && friendlySettlement.getNumberOfTotoros() < 1) {
//                        try {
//                            final ArrayList<MapSpot> expandableMapSpots
//                                    = meeplesExpandableSpotsScanner.scan(friendlySettlement, map);
//
//                            if ((expandableMapSpots.size() + friendlySettlement.size()) == 5) {
//                                settlementExpansionHandler.expandWithMeeples(friendlySettlement.getMapSpots().get(0));
//                                return;
//                            }
//
//                            else if ((expandableMapSpots.size() + friendlySettlement.size()) == 4) {
//                                settlementExpansionHandler.expandWithMeeples(friendlySettlement.getMapSpots().get(0));
//                                return;
//                            }
//
//                            else {
//                                ArrayList<MapSpot> foundingSpots = foundingNextToSettlementScanner.scan(friendlySettlement, map);
//                                map.getHexagon(foundingSpots.get(0)).addMeeplesAccordingToLevel(Team.FRIENDLY);
//                                return;
//                            }
//                        } catch (NoValidActionException e) {}
//                    }
//
//                    else if (friendlySettlement.size() == 1 && friendlySettlement.getNumberOfTotoros() < 1) {
//                        try {
//                            final ArrayList<MapSpot> expandableMapSpots
//                                    = meeplesExpandableSpotsScanner.scan(friendlySettlement, map);
//
//                            if ((expandableMapSpots.size() + friendlySettlement.size()) == 5) {
//                                settlementExpansionHandler.expandWithMeeples(friendlySettlement.getMapSpots().get(0));
//                                return;
//                            }
//
//                            else if ((expandableMapSpots.size() + friendlySettlement.size()) == 4) {
//                                settlementExpansionHandler.expandWithMeeples(friendlySettlement.getMapSpots().get(0));
//                                return;
//                            }
//
//                            else if ((expandableMapSpots.size() + friendlySettlement.size()) == 3) {
//                                settlementExpansionHandler.expandWithMeeples(friendlySettlement.getMapSpots().get(0));
//                                return;
//                            }
//
//                            else {
//                                ArrayList<MapSpot> foundingSpots = foundingNextToSettlementScanner.scan(friendlySettlement, map);
//                                map.getHexagon(foundingSpots.get(0)).addMeeplesAccordingToLevel(Team.FRIENDLY);
//                                return;
//                            }
//                        } catch (NoValidActionException e) {}
//                    }
//
//                    else {
//                        try{
//                            // Place villager on any hex
//                            final MapSpot foundingSpots;
//                                foundingSpots = randomSettlementFoundingScanner.scan(map);
//                            map.getHexagon(foundingSpots).addMeeplesAccordingToLevel(Team.FRIENDLY);
//                            return;
//                        } catch (NoValidActionException e) {}
//                        return;
//                    }
//                }
//
//            }
//        }
//
//        else if (player.getNumberOfMeeplesLeft() > 0) {
//            // Iterate through expansion options to exhaust meeple count ....
//            //  a. check meeple count minus expansion cost
//            //  b. if difference == 0, then immediately expand
//            //  c. if difference > 0, then check if new max is found
//            //  d. if all costs for expansion are greater than meeple count, then found villager in any random spot
//
//            ArrayList<MapSpot> maxMapSpot = new ArrayList<MapSpot>();
//            Settlement maxSettlement = new Settlement(player.getTeam());
//
//            for (Settlement friendlySettlement : friendlySettlements) {
//                try {
//                    final ArrayList<MapSpot> expandableMapSpots
//                            = meeplesExpandableSpotsScanner.scan(friendlySettlement, map);
//
//                    if ((player.getNumberOfMeeplesLeft() - expandableMapSpots.size()) == 0) {
//                        settlementExpansionHandler.expandWithMeeples(friendlySettlement.getMapSpots().get(0));
//                        return;
//                    }
//
//                    if ((player.getNumberOfMeeplesLeft() - expandableMapSpots.size()) > 0) {
//                        if (expandableMapSpots.size() > maxMapSpot.size()) {
//                            maxMapSpot = expandableMapSpots;
//                            maxSettlement = friendlySettlement;
//                        }
//                    }
//
//                } catch (NoValidActionException e) {}
//
//            }
//
//            try {
//                if (maxMapSpot.size() > 1) {
//                    settlementExpansionHandler.expandWithMeeples(maxSettlement.getMapSpots().get(0));
//                    return;
//                }
//                else {
//                    // Place villager on any hex
//                    final MapSpot foundingSpots;
//                    foundingSpots = randomSettlementFoundingScanner.scan(map);
//                    map.getHexagon(foundingSpots).addMeeplesAccordingToLevel(Team.FRIENDLY);
//                    return;
//                }
//
//            } catch (NoValidActionException e) {}
//        }
//
//        else {
//            return;
//            // Game Over
//            // FIXME: 4/9/2017 No More Build Options
//        }
//
//    }
//}
