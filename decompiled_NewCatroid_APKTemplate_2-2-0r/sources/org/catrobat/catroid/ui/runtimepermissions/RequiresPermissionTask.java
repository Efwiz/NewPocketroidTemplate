package org.catrobat.catroid.ui.runtimepermissions;

import android.app.Activity;
import android.util.Log;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.util.Iterator;
import java.util.List;
import org.eclipse.jgit.lib.ReflogEntry;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/runtimepermissions/RequiresPermissionTask.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public abstract class RequiresPermissionTask {
    public static final String TAG = "RequiresPermissionTask";
    private final int permissionRequestId;
    private List<String> permissions;
    private final int rationaleString;

    public abstract void task();

    protected RequiresPermissionTask(int permissionRequestId, List<String> permissions, int rationaleString) {
        this.permissionRequestId = permissionRequestId;
        this.permissions = permissions;
        this.rationaleString = rationaleString;
        Log.d("PaintroidTest", ReflogEntry.PREFIX_CREATED);
    }

    public int getPermissionRequestId() {
        return this.permissionRequestId;
    }

    public int getRationaleString() {
        return this.rationaleString;
    }

    public List<String> getPermissions() {
        return this.permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void execute(Activity activity) {
        if (checkPermission(activity, this.permissions)) {
            task();
            return;
        }
        if (activity instanceof PermissionHandlingActivity) {
            ((PermissionHandlingActivity) activity).addToRequiresPermissionTaskList(this);
        } else {
            Log.d(TAG, "This has to be called from a PermissionHandlingActivity to have your task be executed on premissionResult");
        }
        ActivityCompat.requestPermissions(activity, (String[]) this.permissions.toArray(new String[0]), this.permissionRequestId);
    }

    public static boolean checkPermission(Activity activity, List<String> permissions) {
        Iterator<String> it = permissions.iterator();
        while (it.hasNext()) {
            if (ContextCompat.checkSelfPermission(activity, it.next()) != 0) {
                return false;
            }
        }
        return true;
    }
}
