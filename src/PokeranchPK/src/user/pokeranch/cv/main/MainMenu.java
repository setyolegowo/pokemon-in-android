/**
 * 
 */
package user.pokeranch.cv.main;

import user.pokeranch.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author Setyo Lego
 *
 */
public class MainMenu extends Fragment {
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
		//DisplayMetrics displaymetrics = new DisplayMetrics();
		//getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        return inflater.inflate(R.layout.activity_main, container, false);
    }
}
