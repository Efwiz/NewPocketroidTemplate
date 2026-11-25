package org.catrobat.catroid.ui;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import java.io.File;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.ui.runtimepermissions.RequiresPermissionTask;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/ImportFromCameraLauncher.class
  classes14.dex
 */
/* JADX INFO: compiled from: ImportLaunchers.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u0000 \u000e2\u00020\u0001:\u0001\u000eB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\u0007\u001a\u00020\bJ\u0006\u0010\t\u001a\u00020\nJ\u0010\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\rH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082D¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lorg/catrobat/catroid/ui/ImportFromCameraLauncher;", "Lorg/catrobat/catroid/ui/ImportLauncher;", "activity", "Landroidx/appcompat/app/AppCompatActivity;", "(Landroidx/appcompat/app/AppCompatActivity;)V", "cameraImageFileName", "", "createCameraCacheDir", "", "getCacheCameraUri", "Landroid/net/Uri;", "startActivityForResult", "requestCode", "", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ImportFromCameraLauncher implements ImportLauncher {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final int REQUEST_PERMISSIONS_CAMERA_LAUNCHER = 301;
    private final AppCompatActivity activity;
    private final String cameraImageFileName;

    public static final int getREQUEST_PERMISSIONS_CAMERA_LAUNCHER() {
        return INSTANCE.getREQUEST_PERMISSIONS_CAMERA_LAUNCHER();
    }

    public ImportFromCameraLauncher(AppCompatActivity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        this.activity = activity;
        this.cameraImageFileName = "image.jpg";
    }

    public final Uri getCacheCameraUri() {
        AppCompatActivity appCompatActivity = this.activity;
        Uri uriForFile = FileProvider.getUriForFile(appCompatActivity, appCompatActivity.getApplicationContext().getPackageName() + ".fileProvider", new File(Constants.CAMERA_CACHE_DIRECTORY, this.cameraImageFileName));
        Intrinsics.checkNotNullExpressionValue(uriForFile, "getUriForFile(...)");
        return uriForFile;
    }

    public final void createCameraCacheDir() throws IOException {
        Constants.CAMERA_CACHE_DIRECTORY.mkdirs();
        if (Constants.CAMERA_CACHE_DIRECTORY.isDirectory()) {
            return;
        }
        throw new IOException("Cannot create " + Constants.CAMERA_CACHE_DIRECTORY.getAbsolutePath() + ".");
    }

    @Override // org.catrobat.catroid.ui.ImportLauncher
    public void startActivityForResult(final int requestCode) {
        Log.d("PaintroidTest", "camera");
        new RequiresPermissionTask(INSTANCE.getREQUEST_PERMISSIONS_CAMERA_LAUNCHER(), CollectionsKt.listOf("android.permission.CAMERA")) { // from class: org.catrobat.catroid.ui.ImportFromCameraLauncher.startActivityForResult.1
            @Override // org.catrobat.catroid.ui.runtimepermissions.RequiresPermissionTask
            public void task() throws IOException {
                ImportFromCameraLauncher.this.createCameraCacheDir();
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.setFlags(2);
                intent.putExtra("output", ImportFromCameraLauncher.this.getCacheCameraUri());
                try {
                    ImportFromCameraLauncher.this.activity.startActivityForResult(Intent.createChooser(intent, ImportFromCameraLauncher.this.activity.getString(R.string.select_look_from_camera)), requestCode);
                } catch (ActivityNotFoundException unused) {
                    Log.e(RequiresPermissionTask.TAG, "Could not find camera.");
                }
            }
        }.execute(this.activity);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/ImportFromCameraLauncher$Companion.class
      classes14.dex
     */
    /* JADX INFO: compiled from: ImportLaunchers.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001c\u0010\u0003\u001a\u00020\u00048\u0006X\u0087D¢\u0006\u000e\n\u0000\u0012\u0004\b\u0005\u0010\u0002\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/catrobat/catroid/ui/ImportFromCameraLauncher$Companion;", "", "()V", "REQUEST_PERMISSIONS_CAMERA_LAUNCHER", "", "getREQUEST_PERMISSIONS_CAMERA_LAUNCHER$annotations", "getREQUEST_PERMISSIONS_CAMERA_LAUNCHER", "()I", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        public static /* synthetic */ void getREQUEST_PERMISSIONS_CAMERA_LAUNCHER$annotations() {
        }

        private Companion() {
        }

        public final int getREQUEST_PERMISSIONS_CAMERA_LAUNCHER() {
            return ImportFromCameraLauncher.REQUEST_PERMISSIONS_CAMERA_LAUNCHER;
        }
    }
}
