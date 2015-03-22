package nairit;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by nairit on 8/3/15.
 */
public class L {
    public static void m(String message){
        Log.d("nairit",""+message);

    }
    public static void t(Context context,String message){
        Toast.makeText(context,message+"",Toast.LENGTH_LONG).show();
    }
}
