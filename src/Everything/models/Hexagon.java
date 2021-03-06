package Everything.models;

public class Hexagon {

    //-----------
    // Attributes

    private Team occupiedBy;
    private int tileId;
    private int level;
    private Terrain terrainType;
    private int numberOfMeeples;
    private boolean hasTotoro;
    private boolean hasTiger;

    //-------------
    // Constructors

    public Hexagon(final Terrain terrainType, final int level, final int tileId) {
        this.occupiedBy = Team.NONE;
        this.terrainType = terrainType;
        this.level = level;
        this.tileId = tileId;
        numberOfMeeples = 0;
        hasTotoro = false;
        hasTiger = false;
    }

    public Hexagon(final Terrain terrainType, final int tileId) {
        this.occupiedBy = Team.NONE;
        this.terrainType = terrainType;
        this.tileId = tileId;
        numberOfMeeples = 0;
        hasTotoro = false;
        hasTiger = false;
    }

    //--------
    // Methods

    public void addMeeplesAccordingToLevel(final Team team)
    {
        if (!isEmpty()) {
            System.out.println("\n\n\n");
            System.out.println("!!" +
                    "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!ERROR!!!!!!!!!!!!!!!!!-------------------------------------------------------------------------" +
                    "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!ERROR!!!!!!!!!!!!!!!!!-------------------------------------------------------------------------");
            System.out.println(this);
            System.out.println("\n\n\n");
//            throw new RuntimeException("Hexagon is not empty, can't add units"); // TODO: FIND THE BUG TRIGGERING THIS
        }

            this.occupiedBy = team;
            this.numberOfMeeples = this.level;
        }

    public void addTotoro(final Team team) {
        if(!isEmpty())
            throw new RuntimeException("Adding totoro when Everything.models.Hexagon is not empty");

        this.occupiedBy = team;
        hasTotoro = true;
    }

    public void addTiger(final Team team){
        if(!isEmpty())
            throw new RuntimeException("Adding tiger when Everything.models.Hexagon is not empty");

        this.occupiedBy = team;
        hasTiger = true;
    }

    public boolean isEmpty(){
        return (numberOfMeeples == 0) && !(hasTotoro) && !(hasTiger);
    }

    public boolean isHasTotoro() {
        return hasTotoro;
    }

    public boolean isHasTiger() {
        return hasTiger;
    }

    //----------
    // Getters

    public Team getOccupiedBy() {
        return occupiedBy;
    }

    public int getTileId() {
        return tileId;
    }

    public int getLevel() {
        return level;
    }

    public int getNumberOfMeeples() {
        return numberOfMeeples;
    }

    public Terrain getTerrainType() {
        return terrainType;
    }

    @Override
    public String toString() {
        return "Hexagon{" +
                "occupiedBy=" + occupiedBy +
                ", tileId=" + tileId +
                ", level=" + level +
                ", terrainType=" + terrainType +
                ", numberOfMeeples=" + numberOfMeeples +
                ", hasTotoro=" + hasTotoro +
                ", hasTiger=" + hasTiger +
                '}';
    }

    //----------
    // Setters

    public void setLevel(int level){
        this.level = level;
    }

    /**
     * For map display
     */
    char ConvertTerrainToCharacter(){
        char TerrainChar = ' ';
        switch (terrainType){
            case VOLCANO:    TerrainChar = 'V';
            break;
            case GRASS:  TerrainChar = 'G';
            break;
            case JUNGLE:     TerrainChar = 'J';
            break;
            case LAKE:       TerrainChar = 'L';
            break;
            case ROCK:      TerrainChar = 'R';
            break;
        }
       return(TerrainChar);
    }

    /**
     * For map display
     */
    protected char ConvertTeamToChar(){
        char TeamChar = ' ';
        switch (occupiedBy){
            case FRIENDLY:    TeamChar = 'F';
                break;
            case ENEMY:       TeamChar = 'E';
                break;
            case NONE:     TeamChar = 'U';
                break;

        }
        return(TeamChar);
    }

}
