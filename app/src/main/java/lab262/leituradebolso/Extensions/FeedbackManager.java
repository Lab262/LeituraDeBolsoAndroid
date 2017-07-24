package lab262.leituradebolso.Extensions;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import lab262.leituradebolso.R;

/**
 * Created by luisresende on 14/10/16.
 */

public class FeedbackManager {

    static String keyErrors = "errors";
    static String keyMessageError = "detail";


    static public void createToast(Context context, String message, Boolean isShort){

        int duration = Toast.LENGTH_LONG;
        if (isShort){
            duration = Toast.LENGTH_SHORT;
        }

        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }

    static public ProgressDialog createProgressDialog (Context context, String message){
        if (!((Activity) context).isFinishing()){
            ProgressDialog dialog = new ProgressDialog(context);
            dialog.setMessage(message);
            dialog.show();
            return dialog;
        }else{
            return null;
        }

    }

    static public void feedbackErrorResponse(Context context, ProgressDialog progressDialog, int statusCode, JSONObject errorResponse){
        if (progressDialog!=null){
            progressDialog.dismiss();
        }
        if (statusCode!=0){
            try {
                JSONArray arrayErrors = errorResponse.getJSONArray(keyErrors);
                String message = arrayErrors.getJSONObject(0).getString(keyMessageError);
                FeedbackManager.createToast(context,message,false);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            FeedbackManager.createToast(context,context.getString(R.string.placeholder_error_connection),false);
        }
    }
}
