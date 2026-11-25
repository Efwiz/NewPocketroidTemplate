package org.catrobat.catroid.ui;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import androidx.core.content.FileProvider;
import java.io.File;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.io.StorageOperations;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/ImportFromPocketPaintLauncher.class
  classes14.dex
 */
/* JADX INFO: compiled from: ImportLaunchers.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\bH\u0002J\u0006\u0010\t\u001a\u00020\nJ\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082D¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lorg/catrobat/catroid/ui/ImportFromPocketPaintLauncher;", "Lorg/catrobat/catroid/ui/ImportLauncher;", "activity", "Landroid/app/Activity;", "(Landroid/app/Activity;)V", "pocketPaintImageFileName", "", "createEmptyImageFile", "Ljava/io/File;", "getPocketPaintCacheUri", "Landroid/net/Uri;", "startActivityForResult", "", "requestCode", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ImportFromPocketPaintLauncher implements ImportLauncher {
    private final Activity activity;
    private final String pocketPaintImageFileName;

    public ImportFromPocketPaintLauncher(Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        this.activity = activity;
        this.pocketPaintImageFileName = "image.png";
    }

    public final Uri getPocketPaintCacheUri() {
        Activity activity = this.activity;
        Uri uriForFile = FileProvider.getUriForFile(activity, activity.getApplicationContext().getPackageName() + ".fileProvider", new File(Constants.POCKET_PAINT_CACHE_DIRECTORY, this.pocketPaintImageFileName));
        Intrinsics.checkNotNullExpressionValue(uriForFile, "getUriForFile(...)");
        return uriForFile;
    }

    private final File createEmptyImageFile() throws IOException {
        Constants.POCKET_PAINT_CACHE_DIRECTORY.mkdirs();
        if (!Constants.POCKET_PAINT_CACHE_DIRECTORY.isDirectory()) {
            throw new IOException("Cannot create " + Constants.POCKET_PAINT_CACHE_DIRECTORY.getAbsolutePath() + ".");
        }
        Project currentProject = ProjectManager.getInstance().getCurrentProject();
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(currentProject.getXmlHeader().getVirtualScreenWidth(), currentProject.getXmlHeader().getVirtualScreenHeight(), Bitmap.Config.ARGB_8888);
        Intrinsics.checkNotNullExpressionValue(bitmapCreateBitmap, "createBitmap(...)");
        File fileCompressBitmapToPng = StorageOperations.compressBitmapToPng(bitmapCreateBitmap, new File(Constants.POCKET_PAINT_CACHE_DIRECTORY, this.pocketPaintImageFileName));
        Intrinsics.checkNotNullExpressionValue(fileCompressBitmapToPng, "compressBitmapToPng(...)");
        return fileCompressBitmapToPng;
    }

    @Override // org.catrobat.catroid.ui.ImportLauncher
    public void startActivityForResult(int requestCode) {
        Intent component = new Intent("android.intent.action.MAIN").setComponent(new ComponentName(this.activity, Constants.POCKET_PAINT_INTENT_ACTIVITY_NAME));
        Intrinsics.checkNotNullExpressionValue(component, "setComponent(...)");
        Bundle bundle = new Bundle();
        bundle.putString("org.catrobat.extra.PAINTROID_PICTURE_PATH", createEmptyImageFile().getAbsolutePath());
        component.putExtras(bundle);
        component.addCategory("android.intent.category.LAUNCHER");
        this.activity.startActivityForResult(component, requestCode);
    }
}
