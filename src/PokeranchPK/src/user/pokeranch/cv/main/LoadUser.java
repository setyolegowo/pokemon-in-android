/**
 * 
 */
package user.pokeranch.cv.main;

import java.util.ArrayList;

import user.pokeranch.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * @author Setyo Lego
 *
 */
public class LoadUser extends DialogFragment {
	public static ArrayList<String> listPlayerReg;
	
	private LoadUserListener mCallback;
	
	public interface LoadUserListener {
		public void onLoadUserLitener(String name);
	}
	
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (LoadUserListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement LoadUserListener");
        }
    }
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    
	    builder.setTitle(R.string.load_player);
	    assert listPlayerReg.isEmpty();
	    CharSequence[] cc = listPlayerReg.toArray(new CharSequence[listPlayerReg.size()]);
	    builder.setItems(cc, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				mCallback.onLoadUserLitener(listPlayerReg.get(arg1));
			}
		});
	    return builder.create();
	}
}
