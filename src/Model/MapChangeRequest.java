package Model;

public class MapChangeRequest {

    private final int case_row;
    private final int case_col;
    private final String sprite_change;
    private String type;

    /**
     * Constructeur de classe MapChangeRequest
     *
     * @param Y Coordonnée Y
     * @param X Coordonnée X
     * @param file Path vers un le sprite a intégré.
     * @param type String représentant le type de changement à effectuer.
     */
    public MapChangeRequest(int Y, int X, String file, String type) {
        this.case_row = Y;
        this.case_col = X;
        this.sprite_change = file;
        this.type = type;
    }

    /**
     * @return the case_X
     */
    public int getCase_row() {
        return case_row;
    }

    /**
     * @return the case_Y
     */
    public int getCase_col() {
        return case_col;
    }

    /**
     * @return the sprite_change
     */
    public String getSprite_change() {
        return sprite_change;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

}
