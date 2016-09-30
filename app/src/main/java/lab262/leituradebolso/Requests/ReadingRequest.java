package lab262.leituradebolso.Requests;

import com.loopj.android.http.JsonHttpResponseHandler;

/**
 * Created by luisresende on 29/09/16.
 */

public class ReadingRequest {

    private static String urlGetAll = Requester.baseUrl + Requester.versionWS + "/readings";

    public static void getReadings(String token, JsonHttpResponseHandler jsonHttpResponseHandler){

        Requester.client.get(null,urlGetAll,Requester.getArrayHeaders(token),null,jsonHttpResponseHandler);

    }

}
