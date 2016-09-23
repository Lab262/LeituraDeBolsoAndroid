package lab262.leituradebolso.Extensions;

import java.util.Date;

/**
 * Created by luisresende on 22/09/16.
 */

public class ApplicationState {

    private Boolean reciveNotification;
    private int textSize;
    private Boolean noturneMode;
    private Date hourNotification;

    private static ApplicationState ourInstance = new ApplicationState();

    /**
     * Returns an Singleton Instance
     *
     * @return  the static instance of this class
     */
    public static ApplicationState sharedState() {
        return ourInstance;
    }

    private ApplicationState(){

    }

    public ApplicationState (Boolean reciveNotification, Boolean noturneMode, int textSize, Date hourNotification){
        sharedState().setReciveNotification(reciveNotification);
        sharedState().setNoturneMode(noturneMode);
        sharedState().setTextSize(textSize);
        sharedState().setHourNotification(hourNotification);
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public Boolean getReciveNotification() {
        return reciveNotification;
    }

    public void setReciveNotification(Boolean reciveNotification) {
        this.reciveNotification = reciveNotification;
    }

    public Date getHourNotification() {
        return hourNotification;
    }

    public void setHourNotification(Date hourNotification) {
        this.hourNotification = hourNotification;
    }

    public Boolean getNoturneMode() {
        return noturneMode;
    }

    public void setNoturneMode(Boolean noturneMode) {
        this.noturneMode = noturneMode;
    }
}
