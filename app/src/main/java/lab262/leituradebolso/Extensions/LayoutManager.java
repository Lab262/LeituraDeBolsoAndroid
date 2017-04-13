package lab262.leituradebolso.Extensions;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by luisresende on 20/10/16.
 */

public class LayoutManager {

    private static String pathFonts = "fonts/";
    private static String fontQuicksandBold = pathFonts + "Quicksand-Bold.otf";
    private static String fontComfortaaRegular = pathFonts + "Comfortaa_Regular.ttf";
    private static String fontComfortaaThin = pathFonts + "Comfortaa_Thin.ttf";
    private static String fontComfortaaBold = pathFonts + "Comfortaa_Bold.ttf";
    private static String fontMerriweatherLight = pathFonts + "Merriweather Light.ttf";
    private static String fontMerriweatherBold = pathFonts + "Merriweather-Bold.ttf";

    private static LayoutManager ourInstance = new LayoutManager();

    private Context context;

    public Typeface typefaceQuicksandBold;
    public Typeface typefaceComfortaaRegular, typefaceComfortaaThin, typefaceComfortaaBold;
    public Typeface typefaceMerriweatherLight, typefaceMerriweatherBold;

    private LayoutManager(){
    }

    public static LayoutManager sharedInstance() {
        return ourInstance;
    }

    public LayoutManager(Context context){
        sharedInstance().context = context;
        sharedInstance().typefaceQuicksandBold = getTypeface(fontQuicksandBold);
        sharedInstance().typefaceComfortaaRegular = getTypeface(fontComfortaaRegular);
        sharedInstance().typefaceComfortaaThin = getTypeface(fontComfortaaThin);
        sharedInstance().typefaceComfortaaBold = getTypeface(fontComfortaaBold);
        sharedInstance().typefaceMerriweatherLight = getTypeface(fontMerriweatherLight);
        sharedInstance().typefaceMerriweatherBold = getTypeface(fontMerriweatherBold);
    }

    private Typeface getTypeface(String nameFont){
        return Typeface.createFromAsset(sharedInstance().context.getAssets(),nameFont);
    }
}
