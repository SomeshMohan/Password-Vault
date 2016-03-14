package passwordvault.somesh.com.passwordvault.Categories;

import io.realm.RealmObject;

/**
 * Created by root on 14/3/16.
 */
public class PanCard extends RealmObject
{
    private String holderName;
    private String panNumber;
    private String notes;

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
