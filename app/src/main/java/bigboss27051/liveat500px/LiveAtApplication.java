package bigboss27051.liveat500px;

import android.app.Application;
import android.content.Context;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

/**
 * Created by a2g01 on 11/5/2559.
 */
public class LiveAtApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //Innitailize thing(s) here
        Contextor.getInstance().init(getApplicationContext());

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
