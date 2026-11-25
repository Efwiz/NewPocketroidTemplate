package org.catrobat.catroid.io;

import android.content.res.Resources;
import java.io.File;
import java.io.IOException;
import org.catrobat.catroid.utils.ImageEditing;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/io/ResourceImporter.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class ResourceImporter {
    private ResourceImporter() {
        throw new AssertionError();
    }

    public static File createImageFileFromResourcesInDirectory(Resources resources, int resourceId, File dstDir, String fileName, double scaleFactor) throws IOException {
        if (scaleFactor <= 0.0d) {
            throw new IllegalArgumentException("scaleFactor was: " + scaleFactor + ", it has to be > 0.");
        }
        File fileCopyStreamToDir = StorageOperations.copyStreamToDir(resources.openRawResource(resourceId), dstDir, fileName);
        if (scaleFactor != 1.0d) {
            ImageEditing.scaleImageFile(fileCopyStreamToDir, scaleFactor);
        }
        return fileCopyStreamToDir;
    }

    public static File createSoundFileFromResourcesInDirectory(Resources resources, int resourceId, File dstDir, String fileName) throws IOException {
        return StorageOperations.copyStreamToDir(resources.openRawResource(resourceId), dstDir, fileName);
    }
}
