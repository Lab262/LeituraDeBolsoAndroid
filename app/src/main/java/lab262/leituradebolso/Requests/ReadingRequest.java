package lab262.leituradebolso.Requests;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;

/**
 * Created by luisresende on 29/09/16.
 */

public class ReadingRequest {

    private static String keyUrlParamsWhere = "$where";
    private static String keyUrlParamsWhereContent = "this._id==";

    private static String urlGetAll = Requester.baseUrl + Requester.versionWS + "/readings";

    public static void getReadings(String token, ArrayList<String> arrayIDs, JsonHttpResponseHandler jsonHttpResponseHandler){

        RequestParams requestParams = new RequestParams();
        requestParams.put(Requester.keyUrlParamsSkip,0);
        requestParams.put(Requester.keyUrlParamsLimit,0);

        String contentWhere = "";
        for (int i=0; i<arrayIDs.size(); i++){
            if (i!=0){
                contentWhere += " || ";
            }
            contentWhere += keyUrlParamsWhereContent + "'" + arrayIDs.get(i) +"'";
        }

        requestParams.put(keyUrlParamsWhere,contentWhere);

        Requester.client.get(null,urlGetAll,Requester.getArrayHeaders(token),requestParams,jsonHttpResponseHandler);

    }
}
