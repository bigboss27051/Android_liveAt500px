package bigboss27051.liveat500px.datatype;

import android.os.Bundle;

/**
 * Created by a2g01 on 17/05/2559.
 */
public class MutableIntager {
    private int value;

    public MutableIntager(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Bundle onSaveInstanceState(){
        Bundle bundle = new Bundle();
        bundle.putInt("value",value);
        return bundle;
    }

    public void onRestoreInstanceState(Bundle savedInstanceState){
        value = savedInstanceState.getInt("value");
    }

}
