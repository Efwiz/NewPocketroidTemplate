package org.catrobat.catroid.sensing;

import android.os.AsyncTask;
import android.util.Log;
import java.util.Iterator;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.content.Sprite;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/sensing/GatherCollisionInformationTask.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class GatherCollisionInformationTask extends AsyncTask<Void, Void, Boolean> {
    private static final String TAG = "GatherCollisionInformationTask";
    private OnPolygonLoadedListener listener;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/sensing/GatherCollisionInformationTask$OnPolygonLoadedListener.class
      classes14.dex
     */
    public interface OnPolygonLoadedListener {
        void onFinished();
    }

    public GatherCollisionInformationTask(OnPolygonLoadedListener listener) {
        this.listener = listener;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public Boolean doInBackground(Void... arg0) {
        getCollisionInformation();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public void onPostExecute(Boolean success) {
        super.onPostExecute(success);
        Log.i(TAG, "Finished task");
        this.listener.onFinished();
    }

    private void getCollisionInformation() {
        Log.i(TAG, "Waiting for all calculation threads to finish...");
        Iterator<Sprite> it = ProjectManager.getInstance().getCurrentlyEditedScene().getSpriteList().iterator();
        while (it.hasNext()) {
            for (LookData lookData : it.next().getLookList()) {
                if (lookData.getCollisionInformation().collisionPolygonCalculationThread != null) {
                    try {
                        lookData.getCollisionInformation().collisionPolygonCalculationThread.join();
                    } catch (InterruptedException unused) {
                        Log.i(TAG, "Thread got interrupted");
                    }
                }
            }
        }
        for (Sprite sprite : ProjectManager.getInstance().getCurrentlyEditedScene().getSpriteList()) {
            if (sprite.hasCollision(ProjectManager.getInstance().getCurrentlyEditedScene())) {
                Iterator<LookData> it2 = sprite.getLookList().iterator();
                while (it2.hasNext()) {
                    it2.next().getCollisionInformation().loadCollisionPolygon();
                }
            }
        }
    }
}
