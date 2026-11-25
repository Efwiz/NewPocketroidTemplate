package org.catrobat.catroid.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.FlavoredConstants;
import org.catrobat.catroid.common.ScreenValues;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/ProjectManagerExtensionsKt.class
  classes14.dex
 */
/* JADX INFO: compiled from: ProjectManagerExtensions.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002¨\u0006\u0003"}, d2 = {"getProjectBitmap", "Landroid/graphics/Bitmap;", "Lorg/catrobat/catroid/ProjectManager;", "catroid_apktemplateTemplateRelease"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ProjectManagerExtensionsKt {
    public static final Bitmap getProjectBitmap(ProjectManager projectManager) {
        String absolutePath;
        Intrinsics.checkNotNullParameter(projectManager, "<this>");
        File file = new File(new File(FlavoredConstants.DEFAULT_ROOT_DIRECTORY, projectManager.getCurrentProject().getName()), projectManager.getCurrentlyPlayingScene().getName());
        File file2 = new File(file, Constants.SCREENSHOT_AUTOMATIC_FILE_NAME);
        File file3 = new File(file, Constants.SCREENSHOT_MANUAL_FILE_NAME);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        try {
            if (file2.exists()) {
                absolutePath = file2.getPath();
                Intrinsics.checkNotNullExpressionValue(absolutePath, "getPath(...)");
            } else if (file3.exists()) {
                absolutePath = file3.getPath();
                Intrinsics.checkNotNullExpressionValue(absolutePath, "getPath(...)");
            } else {
                absolutePath = projectManager.getCurrentlyEditedScene().getBackgroundSprite().getLookList().get(0).getFile().getAbsolutePath();
                Intrinsics.checkNotNullExpressionValue(absolutePath, "getAbsolutePath(...)");
            }
            Bitmap bitmapDecodeFile = BitmapFactory.decodeFile(absolutePath, options);
            Intrinsics.checkNotNull(bitmapDecodeFile);
            return bitmapDecodeFile;
        } catch (IndexOutOfBoundsException e) {
            Log.w("getProjectBitmap", "backgroundSprite has no looks! " + e.getMessage());
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(ScreenValues.currentScreenResolution.getWidth(), ScreenValues.currentScreenResolution.getHeight(), Bitmap.Config.ARGB_8888);
            Intrinsics.checkNotNullExpressionValue(bitmapCreateBitmap, "createBitmap(...)");
            bitmapCreateBitmap.eraseColor(-1);
            return bitmapCreateBitmap;
        }
    }
}
