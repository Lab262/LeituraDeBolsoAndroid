package lab262.leituradebolso.Extensions;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by luisresende on 05/09/16.
 */
public class ActivityManager {

    public static void changeActivity(Context sourceActivity, Class<?> destinyActivity){
        Intent intent = new Intent(sourceActivity, destinyActivity);
        sourceActivity.startActivity(intent);
    }

    public static void changeActivityAndRemoveParentActivity(Context sourceActivity, Class<?> destinyActivity){
        Intent intent = new Intent(sourceActivity, destinyActivity);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        sourceActivity.startActivity(intent);
    }

    public static void changeActivity(Context sourceActivity, Class<?> destinyActivity, Bundle bundleExtras){
        Intent intent = new Intent(sourceActivity, destinyActivity);
        intent.putExtras(bundleExtras);
        sourceActivity.startActivity(intent);
    }
}
