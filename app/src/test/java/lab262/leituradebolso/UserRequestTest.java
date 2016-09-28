package lab262.leituradebolso;

import android.content.Context;
import android.os.Looper;

import com.loopj.android.http.JsonHttpResponseHandler;

import junit.framework.TestCase;

import org.json.JSONObject;
import org.junit.Test;
import org.mockito.Mock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import cz.msebera.android.httpclient.Header;
import lab262.leituradebolso.Model.UserModel;
import lab262.leituradebolso.Requests.UserRequest;

/**
 * Created by luisresende on 27/09/16.
 */
public class UserRequestTest extends TestCase{

    private UserModel user;

    @Mock
    CountDownLatch latch;


    @Override
    protected void setUp() throws Exception {
        user = new UserModel("teste@teste.com");
    }

    @Test
    public void testCreateAccountUser() throws InterruptedException {

        final CountDownLatch latch = new CountDownLatch(1);
        final Boolean[] isSuccess = new Boolean[1];

        UserRequest.createAccountUser(user,"1234",new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                isSuccess[0] = true;
                latch.countDown();
                super.onSuccess(statusCode, headers, response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                isSuccess[0] = false;
                latch.countDown();
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });

        latch.await(500, TimeUnit.SECONDS);
        assertTrue(isSuccess[0]);
    }
}
