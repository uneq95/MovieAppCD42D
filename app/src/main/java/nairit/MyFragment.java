package nairit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sauhardsharma.myapplication.R;


/**
 * Created by nairit on 20/3/15.
 */
public class MyFragment extends Fragment {
    private TextView textView;
    public static MyFragment getInstance(int position){
        MyFragment myFragment=new MyFragment();
        Bundle args=new Bundle();
        args.putInt("position",position);
        myFragment.setArguments(args);
        return myFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_my, container, false);
        textView = (TextView) layout.findViewById(R.id.position);
        Bundle bundle = getArguments();
        if (bundle != null) {
            textView.setText("the page selected is" + bundle.getInt("position"));
        }

        return layout;



    }
}
