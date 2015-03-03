/**
 * 
 */
package user.pokeranch.cv.main;

import user.pokeranch.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;

/**
 * @author Setyo Lego
 *
 */
public class NewUser extends DialogFragment implements OnClickListener {
	
	private static String TAG = "NewUser";
	
	private OnNewUserListener mCallback;
	
	public static String message = "";
	private EditText username;

    public interface OnNewUserListener {
        public void onUserDefined(String dialog);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnNewUserListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnNewUserListener");
        }
    }
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		username = new EditText(getActivity());
		username.setInputType(InputType.TYPE_CLASS_TEXT);
		username.setHint(R.string.username);
		username.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
		username.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
		
	    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

	    // Inflate and set the layout for the dialog
	    // Pass null as the parent view because its going in the dialog layout
	    builder.setTitle(R.string.new_player);
	    if(message != "") builder.setMessage(message);
	    builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	            	   NewUser.this.getDialog().cancel();
	            	   message = "";
	               }
	           }).setPositiveButton(R.string.create, this).setView(username);
	    return builder.create();
	}
	
	@Override
	public void onClick(DialogInterface dialog, int position) {
		String getName;
		if(username != null) {
			getName = "";
			Editable aGetName = username.getText();
			if(aGetName.length() != 0) getName = aGetName.toString(); 
		} else 
 		   getName = "";
 	   Log.d(TAG,getName);
 	   
 	   mCallback.onUserDefined(getName);
	}

}
