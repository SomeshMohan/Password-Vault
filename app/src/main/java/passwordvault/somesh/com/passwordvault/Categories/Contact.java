package passwordvault.somesh.com.passwordvault.Categories;

import io.realm.RealmObject;

/**
 * Created by Somesh on 3/14/2016.
 */
public class Contact extends RealmObject
{
    private String holderName;
    private int pin;
    private long number;
}
