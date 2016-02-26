package Model;

/**
 * Created by thibaultgeoffroy on 25/02/2016.
 */
public class MapChangeRequest {

    private int case_row;
    private int case_col;
    private String sprite_change;

    public MapChangeRequest(int Y, int X, String file){
        this.case_row = Y;
        this.case_col = X;
        this.sprite_change = file;
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


}
