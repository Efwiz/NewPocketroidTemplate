package org.catrobat.catroid.transfers;

import android.os.AsyncTask;
import android.util.Log;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import org.catrobat.catroid.web.CatrobatServerCalls;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/transfers/GetTagsTask.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class GetTagsTask extends AsyncTask<String, Void, String> {
    private static final String TAG = "GetTagsTask";
    private static final String TAGS_JSON_KEY = "constantTags";
    private TagResponseListener onTagsResponseListener;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/transfers/GetTagsTask$TagResponseListener.class
      classes14.dex
     */
    public interface TagResponseListener {
        void onTagsReceived(List<String> tags);
    }

    public void setOnTagsResponseListener(TagResponseListener listener) {
        this.onTagsResponseListener = listener;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public String doInBackground(String... arg0) {
        return new CatrobatServerCalls().getTags(Locale.getDefault().getLanguage());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public void onPostExecute(String response) {
        try {
            this.onTagsResponseListener.onTagsReceived(parseTags(response));
        } catch (JSONException e) {
            Log.e(TAG, "Failed to parse tags json", e);
        }
    }

    private List<String> parseTags(String response) throws JSONException {
        ArrayList arrayList = new ArrayList();
        JSONArray jSONArray = new JSONObject(response).getJSONArray(TAGS_JSON_KEY);
        for (int i = 0; i < jSONArray.length(); i++) {
            arrayList.add(jSONArray.getString(i));
        }
        return Collections.unmodifiableList(arrayList);
    }
}
