package org.catrobat.catroid.transfers;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.Locale;
import javax.net.ssl.HttpsURLConnection;
import org.catrobat.catroid.web.CatrobatServerCalls;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/transfers/GetSurveyTask.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class GetSurveyTask extends AsyncTask<String, Void, String> {
    private static final String SURVEY_URL_JSON_KEY = "url";
    private static final String TAG = "GetSurveyTask";
    private final WeakReference<Context> contextWeakReference;
    private SurveyResponseListener onSurveyResponseListener;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/transfers/GetSurveyTask$SurveyResponseListener.class
      classes14.dex
     */
    public interface SurveyResponseListener {
        void onSurveyReceived(Context context, String surveyUrl);
    }

    public GetSurveyTask(Context context) {
        this.contextWeakReference = new WeakReference<>(context);
    }

    public void setOnSurveyResponseListener(SurveyResponseListener listener) {
        this.onSurveyResponseListener = listener;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public String doInBackground(String... arg0) {
        String survey = new CatrobatServerCalls().getSurvey(Locale.getDefault().getLanguage());
        String str = null;
        if (survey.isEmpty()) {
            return null;
        }
        try {
            String survey2 = parseSurvey(survey);
            try {
                if (isUrlStatusCodeOk(survey2)) {
                    return survey2;
                }
                return null;
            } catch (Exception e) {
                e = e;
                str = survey2;
                Log.e(TAG, "Failed to get survey url", e);
                return str;
            }
        } catch (Exception e2) {
            e = e2;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public void onPostExecute(String response) {
        Context context = this.contextWeakReference.get();
        if (context == null || response == null) {
            return;
        }
        this.onSurveyResponseListener.onSurveyReceived(context, response);
    }

    public String parseSurvey(String response) throws JSONException {
        return new JSONObject(response).getString("url");
    }

    private boolean isUrlStatusCodeOk(String surveyUrl) throws IOException {
        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) new URL(surveyUrl).openConnection();
        httpsURLConnection.connect();
        int responseCode = httpsURLConnection.getResponseCode();
        httpsURLConnection.disconnect();
        return responseCode == 200;
    }
}
