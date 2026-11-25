package org.catrobat.catroid.ui.runtimepermissions;

import android.app.Activity;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import java.util.ArrayList;
import java.util.List;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/runtimepermissions/PermissionRequestActivityExtension.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class PermissionRequestActivityExtension {
    private final List<RequiresPermissionTask> waitingForResponsePermissionTaskList = new ArrayList();

    public void addToRequiresPermissionTaskList(RequiresPermissionTask task) {
        this.waitingForResponsePermissionTaskList.add(task);
    }

    public void onRequestPermissionsResult(Activity activity, int requestCode, String[] permissions, int[] grantResults) {
        RequiresPermissionTask requiresPermissionTaskPopAllWithSameIdRequiredPermissionTask = popAllWithSameIdRequiredPermissionTask(requestCode);
        if (permissions.length == 0) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < permissions.length; i++) {
            if (grantResults[i] == -1) {
                arrayList.add(permissions[i]);
            }
        }
        if (requiresPermissionTaskPopAllWithSameIdRequiredPermissionTask != null) {
            if (arrayList.isEmpty()) {
                requiresPermissionTaskPopAllWithSameIdRequiredPermissionTask.execute(activity);
            } else {
                requiresPermissionTaskPopAllWithSameIdRequiredPermissionTask.setPermissions(arrayList);
                showPermissionRationale(activity, requiresPermissionTaskPopAllWithSameIdRequiredPermissionTask);
            }
        }
    }

    private RequiresPermissionTask popAllWithSameIdRequiredPermissionTask(int requestCode) {
        RequiresPermissionTask requiresPermissionTask = null;
        for (RequiresPermissionTask requiresPermissionTask2 : new ArrayList(this.waitingForResponsePermissionTaskList)) {
            if (requiresPermissionTask2.getPermissionRequestId() == requestCode) {
                this.waitingForResponsePermissionTaskList.remove(requiresPermissionTask2);
                requiresPermissionTask = requiresPermissionTask2;
            }
        }
        return requiresPermissionTask;
    }

    private void showPermissionRationale(final Activity activity, final RequiresPermissionTask task) {
        if (activity.shouldShowRequestPermissionRationale(task.getPermissions().get(0))) {
            String string = activity.getResources().getString(task.getRationaleString());
            DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.runtimepermissions.PermissionRequestActivityExtension$$ExternalSyntheticLambda0
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    PermissionRequestActivityExtension.lambda$showPermissionRationale$0(activity, task, dialogInterface, i);
                }
            };
            if (activity instanceof PermissionAdaptingActivity) {
                showAlertOKIgnoreCancel(activity, string, onClickListener, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.runtimepermissions.PermissionRequestActivityExtension$$ExternalSyntheticLambda1
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        PermissionRequestActivityExtension.lambda$showPermissionRationale$1(activity, task, dialogInterface, i);
                    }
                });
            } else {
                showAlertOKCancel(activity, string, onClickListener);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ void lambda$showPermissionRationale$0(Activity activity, RequiresPermissionTask requiresPermissionTask, DialogInterface dialogInterface, int i) {
        ((PermissionHandlingActivity) activity).addToRequiresPermissionTaskList(requiresPermissionTask);
        activity.requestPermissions((String[]) requiresPermissionTask.getPermissions().toArray(new String[0]), requiresPermissionTask.getPermissionRequestId());
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ void lambda$showPermissionRationale$1(Activity activity, RequiresPermissionTask requiresPermissionTask, DialogInterface dialogInterface, int i) {
        ((PermissionAdaptingActivity) activity).adaptToDeniedPermissions(requiresPermissionTask.getPermissions());
        activity.recreate();
    }

    private void showAlertOKCancel(Activity activity, String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(activity).setMessage(message).setPositiveButton(R.string.ok, okListener).setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null).show();
    }

    private void showAlertOKIgnoreCancel(Activity activity, String message, DialogInterface.OnClickListener okListener, DialogInterface.OnClickListener ignoreListener) {
        new AlertDialog.Builder(activity).setMessage(message).setPositiveButton(R.string.ok, okListener).setNeutralButton(R.string.ignore, ignoreListener).setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null).show();
    }
}
