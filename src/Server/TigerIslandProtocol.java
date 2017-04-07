package Server;

import models.*;



public class TigerIslandProtocol {
    private int gameID;
    private int moveNumber;
    private String tile;
    private int timeLimit;


    public String authenticateTournament(String input){
        String output = null;
        if(input.equals("WELCOME TO ANOTHER EDITION OF THUNDERDOME!")){
            output = "ENTER THUNDERDOME " + "tournament password";             //FIXME: replace with actual tournament password
            }
            else if(input.equals("TWO SHALL ENTER, ONE SHALL LEAVE")) {
            output = "I AM " + "<username> " + "<password>";                  //FIXME: replace with actual user/pass
            }
            return output;
    }
    public String parseInput(String input) {
        String[] tokens = input.split(" ");
        gameID = Integer.parseInt(tokens[5]);
        moveNumber = Integer.parseInt(tokens[10]);
        tile = tokens[12];
        timeLimit = Integer.parseInt(tokens[7]);
        MapSpot place[] = makeMove(gameID, moveNumber, tile);
        int buildType = place.length;
        String move = "GAME " + gameID + " MOVE " + moveNumber + " AT " + place[0].getX() + " " + place[0].getY()
                + " " + place[0].getZ() + " " /*+ place[0].getOrientation()*/ + " ";      //FIXME: Need to get z coord and orientation  // FIXME: 4/7/2017 COMMENTED OUT  getOrientation to compile
        switch (buildType) {
            case (1):
                move.concat("FOUND SETTLEMENT AT " + place[buildType].getX() + " " + place[buildType].getY() + " " + place[buildType].getZ());
                break;
            case (2):
                move.concat("EXPAND SETTLEMENT AT " + place[buildType].getX() + " " + place[buildType].getY() + " " + place[buildType].getZ() + " " /*+ place[buildType].getTerrain()*/) ; // FIXME: 4/7/2017 Commented out getTerrain to compile
                break; //FIXME: Get terrain that we're expanding to?
            case (3):
                move.concat("BUILD TOTORO SANCTUARY AT " + place[buildType].getX() + " " + place[buildType].getY() + " " + place[buildType].getZ());
                break;
            case(4):
                move.concat("BUILD TIGER PLAYGROUND AT " + place[buildType].getX() + " " + place[buildType].getY() + " " + place[buildType].getZ());
                break;
            case(5):
                move.concat("UNABLE TO BUILD" + place[buildType].getX() + " " + place[buildType].getY() + " " + place[buildType].getZ());
                break;
        }
        return move;
    }

    public MapSpot[] makeMove(int gameid, int movenumber, String tile){
        //FIXME: Pass to something that figures out the two moves, MapSpot[0] is tile placement location, MapSpot[1] is build location
        //size of returned move indicates build type, found settlement = 1, expand 2, totoro 3, tiger 4, unable to build 5
        MapSpot [] newmove = null;
        return newmove;
    }
}
