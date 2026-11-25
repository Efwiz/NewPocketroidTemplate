package org.catrobat.catroid.sensing;

import android.os.Process;
import android.util.Log;
import ar.com.hjg.pngj.PngjInputException;
import org.catrobat.catroid.common.LookData;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/sensing/CollisionPolygonCreationTask.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class CollisionPolygonCreationTask implements Runnable {
    private static final String TAG = "CollisionPolygonCreationTask";
    private LookData lookdata;

    public CollisionPolygonCreationTask(LookData lookdata) {
        this.lookdata = lookdata;
    }

    @Override // java.lang.Runnable
    public void run() {
        Process.setThreadPriority(10);
        try {
            this.lookdata.getCollisionInformation().loadCollisionPolygon();
        } catch (PngjInputException unused) {
            Log.e(TAG, "File not found");
        } catch (NullPointerException unused2) {
            Log.e(TAG, "Image format not supported ");
        }
    }
}
