package org.catrobat.catroid.NN;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import java.io.File;
import java.io.FileOutputStream;
import kotlin.Metadata;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.content.TableManager;
import org.catrobat.catroid.utils.ErrorLog;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/NN/ImageProcessingManager.class
  classes7.dex
 */
/* JADX INFO: compiled from: ImageProcessingManager.kt */
/* JADX INFO: loaded from: classes7.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0006\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004J\u0012\u0010\u0006\u001a\u0004\u0018\u00010\u00042\b\u0010\u0007\u001a\u0004\u0018\u00010\bJ(\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\u00042\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\rJ+\u0010\u0010\u001a\u0004\u0018\u00010\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00042\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0012¢\u0006\u0002\u0010\u0014J\u001a\u0010\u0015\u001a\u0004\u0018\u00010\b2\b\u0010\u0016\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0017\u001a\u00020\u0004¨\u0006\u0018"}, d2 = {"Lorg/catrobat/catroid/NN/ImageProcessingManager;", "", "()V", "convertToGrayscale", "Landroid/graphics/Bitmap;", "originalBitmap", "loadBitmapFromFile", "imageFile", "Ljava/io/File;", "normalizeToTables", "", "bitmap", "rTableName", "", "gTableName", "bTableName", "resizeBitmap", "newWidth", "", "newHeight", "(Landroid/graphics/Bitmap;Ljava/lang/Integer;Ljava/lang/Integer;)Landroid/graphics/Bitmap;", "saveBitmapToProject", "file", "bitmapToSave", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ImageProcessingManager {
    public static final ImageProcessingManager INSTANCE = new ImageProcessingManager();

    private ImageProcessingManager() {
    }

    public final Bitmap loadBitmapFromFile(File imageFile) {
        if (imageFile != null && imageFile.exists()) {
            return BitmapFactory.decodeFile(imageFile.getAbsolutePath());
        }
        return null;
    }

    public final Bitmap resizeBitmap(Bitmap originalBitmap, Integer newWidth, Integer newHeight) {
        if (originalBitmap == null || newWidth == null || newHeight == null) {
            return null;
        }
        return Bitmap.createScaledBitmap(originalBitmap, newWidth.intValue(), newHeight.intValue(), true);
    }

    public final Bitmap convertToGrayscale(Bitmap originalBitmap) {
        if (originalBitmap == null) {
            return null;
        }
        int width = originalBitmap.getWidth();
        int height = originalBitmap.getHeight();
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Intrinsics.checkNotNullExpressionValue(bitmapCreateBitmap, "createBitmap(...)");
        for (int i = 0; i < width; i++) {
            for (int i2 = 0; i2 < height; i2++) {
                int pixel = originalBitmap.getPixel(i, i2);
                int i3 = (int) ((((double) ((pixel >> 16) & 255)) * 0.299d) + (((double) ((pixel >> 8) & 255)) * 0.587d) + (((double) (pixel & 255)) * 0.114d));
                bitmapCreateBitmap.setPixel(i, i2, Color.rgb(i3, i3, i3));
            }
        }
        return bitmapCreateBitmap;
    }

    public final void normalizeToTables(Bitmap bitmap, String rTableName, String gTableName, String bTableName) {
        Intrinsics.checkNotNullParameter(rTableName, "rTableName");
        Intrinsics.checkNotNullParameter(gTableName, "gTableName");
        Intrinsics.checkNotNullParameter(bTableName, "bTableName");
        if (bitmap == null) {
            return;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        TableManager.INSTANCE.createTable(rTableName, width, height);
        TableManager.INSTANCE.createTable(gTableName, width, height);
        TableManager.INSTANCE.createTable(bTableName, width, height);
        for (int i = 0; i < width; i++) {
            int i2 = 0;
            while (i2 < height) {
                int pixel = bitmap.getPixel(i, i2);
                int i3 = i + 1;
                i2++;
                TableManager.INSTANCE.insertElement(rTableName, Float.valueOf(((pixel >> 16) & 255) / 255.0f), i3, i2);
                TableManager.INSTANCE.insertElement(gTableName, Float.valueOf(((pixel >> 8) & 255) / 255.0f), i3, i2);
                TableManager.INSTANCE.insertElement(bTableName, Float.valueOf((pixel & 255) / 255.0f), i3, i2);
            }
        }
    }

    public final File saveBitmapToProject(File file, Bitmap bitmapToSave) {
        Intrinsics.checkNotNullParameter(bitmapToSave, "bitmapToSave");
        if (file == null) {
            return null;
        }
        Bitmap.CompressFormat compressFormat = Bitmap.CompressFormat.PNG;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            try {
                bitmapToSave.compress(compressFormat, 100, fileOutputStream);
                CloseableKt.closeFinally(fileOutputStream, null);
                return file;
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    CloseableKt.closeFinally(fileOutputStream, th);
                    throw th2;
                }
            }
        } catch (Exception e) {
            ErrorLog errorLog = ErrorLog.INSTANCE;
            String message = e.getMessage();
            if (message == null) {
                message = "**message not provided :(**";
            }
            errorLog.log(message);
            return null;
        }
    }
}
