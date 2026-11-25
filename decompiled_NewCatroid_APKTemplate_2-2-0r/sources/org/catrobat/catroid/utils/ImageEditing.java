package org.catrobat.catroid.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.Log;
import ar.com.hjg.pngj.PngReader;
import ar.com.hjg.pngj.PngWriter;
import ar.com.hjg.pngj.PngjException;
import ar.com.hjg.pngj.PngjInputException;
import ar.com.hjg.pngj.chunks.ChunkHelper;
import ar.com.hjg.pngj.chunks.PngChunk;
import ar.com.hjg.pngj.chunks.PngChunkTextVar;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;
import org.catrobat.catroid.common.Constants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/ImageEditing.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class ImageEditing {
    private static final int JPG_COMPRESSION_SETTING = 95;
    private static final String TAG = "ImageEditing";

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/ImageEditing$ResizeType.class
      classes14.dex
     */
    public enum ResizeType {
        STRETCH_TO_RECTANGLE,
        STAY_IN_RECTANGLE_WITH_SAME_ASPECT_RATIO,
        FILL_RECTANGLE_WITH_SAME_ASPECT_RATIO
    }

    private ImageEditing() {
        throw new AssertionError();
    }

    private static Bitmap scaleBitmap(Bitmap bitmap, int xSize, int ySize) {
        if (bitmap == null || xSize <= 0 || ySize <= 0) {
            return bitmap;
        }
        Matrix matrix = new Matrix();
        matrix.postScale(xSize / bitmap.getWidth(), ySize / bitmap.getHeight());
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static Rect findVisibleBounds(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] iArr = new int[width * height];
        int i = width;
        bitmap.getPixels(iArr, 0, i, 0, 0, width, height);
        int i2 = -1;
        int i3 = -1;
        int i4 = height;
        for (int i5 = 0; i5 < height; i5++) {
            for (int i6 = 0; i6 < width; i6++) {
                if (!isPixelTransparent(iArr, width, i6, i5)) {
                    if (i6 < i) {
                        i = i6;
                    }
                    if (i6 > i2) {
                        i2 = i6;
                    }
                    if (i5 < i4) {
                        i4 = i5;
                    }
                    if (i5 > i3) {
                        i3 = i5;
                    }
                }
            }
        }
        if (i2 == -1) {
            return null;
        }
        return new Rect(i, i4, i2, i3);
    }

    public static Bitmap getScaledBitmapFromPath(String imagePath, int outputRectangleWidth, int outputRectangleHeight, ResizeType resizeType, boolean justScaleDown) {
        if (imagePath == null) {
            return null;
        }
        int[] imageDimensions = getImageDimensions(imagePath);
        int i = imageDimensions[0];
        int i2 = imageDimensions[1];
        int[] scaledImageDimensions = getScaledImageDimensions(i, i2, outputRectangleWidth, outputRectangleHeight, resizeType, justScaleDown);
        int i3 = scaledImageDimensions[0];
        int i4 = scaledImageDimensions[1];
        int iCalculateInSampleSize = calculateInSampleSize(i, i2, outputRectangleWidth, outputRectangleHeight);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = iCalculateInSampleSize;
        options.inJustDecodeBounds = false;
        return scaleBitmap(BitmapFactory.decodeFile(imagePath, options), i3, i4);
    }

    public static int[] getImageDimensions(String imagePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, options);
        return new int[]{options.outWidth, options.outHeight};
    }

    private static int[] getScaledImageDimensions(int originalWidth, int originalHeight, int outputRectangleWidth, int outputRectangleHeight, ResizeType resizeType, boolean justScaleDown) {
        double dFloor;
        double d = originalWidth;
        double d2 = d / ((double) outputRectangleWidth);
        double d3 = originalHeight;
        double d4 = d3 / ((double) outputRectangleHeight);
        double dMin = Math.min(d2, d4);
        double dMax = Math.max(d2, d4);
        if (!justScaleDown || originalHeight >= outputRectangleHeight || originalWidth >= outputRectangleWidth) {
            if (resizeType == ResizeType.STRETCH_TO_RECTANGLE) {
                originalWidth = outputRectangleWidth;
                originalHeight = outputRectangleHeight;
            } else {
                if (resizeType == ResizeType.STAY_IN_RECTANGLE_WITH_SAME_ASPECT_RATIO) {
                    originalWidth = (int) Math.floor(d / dMax);
                    dFloor = Math.floor(d3 / dMax);
                } else if (resizeType == ResizeType.FILL_RECTANGLE_WITH_SAME_ASPECT_RATIO) {
                    originalWidth = (int) Math.floor(d / dMin);
                    dFloor = Math.floor(d3 / dMin);
                }
                originalHeight = (int) dFloor;
            }
        }
        return new int[]{originalWidth, originalHeight};
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int rotationDegree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(rotationDegree);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static void scaleImageFile(File file, double scaleFactor) throws FileNotFoundException {
        int[] imageDimensions = getImageDimensions(file.getAbsolutePath());
        scaleImageFile(file, (int) (((double) imageDimensions[0]) * scaleFactor), (int) (((double) imageDimensions[1]) * scaleFactor));
    }

    public static void scaleImageFile(File file, int width, int height) throws FileNotFoundException {
        saveBitmapToImageFile(file, getScaledBitmapFromPath(file.getAbsolutePath(), width, height, ResizeType.FILL_RECTANGLE_WITH_SAME_ASPECT_RATIO, false));
    }

    public static double calculateScaleFactor(int originalWidth, int originalHeight, int newWidth, int newHeight) {
        if (originalHeight == 0 || originalWidth == 0 || newHeight == 0 || newWidth == 0) {
            throw new IllegalArgumentException("One or more values are 0");
        }
        return Math.max(((double) newWidth) / ((double) originalWidth), ((double) newHeight) / ((double) originalHeight));
    }

    private static int calculateInSampleSize(int origWidth, int origHeight, int reqWidth, int reqHeight) {
        int i = 1;
        if (origHeight > reqHeight || origWidth > reqWidth) {
            int i2 = origHeight / 2;
            int i3 = origWidth / 2;
            while (i2 / i > reqHeight && i3 / i > reqWidth) {
                i *= 2;
            }
        }
        return i;
    }

    public static void saveBitmapToImageFile(File outputFile, Bitmap bitmap) throws FileNotFoundException {
        FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
        try {
            try {
                try {
                    if (outputFile.getName().toLowerCase(Locale.US).endsWith(Constants.JPEG_IMAGE_EXTENSION) || outputFile.getName().toLowerCase(Locale.US).endsWith(".jpeg")) {
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 95, fileOutputStream);
                    } else {
                        bitmap.compress(Bitmap.CompressFormat.PNG, 0, fileOutputStream);
                    }
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (IOException e) {
                    Log.e(TAG, Log.getStackTraceString(e));
                    fileOutputStream.close();
                }
            } catch (Throwable th) {
                try {
                    fileOutputStream.close();
                } catch (IOException e2) {
                    Log.e(TAG, "Could not close OutputStream.", e2);
                }
                throw th;
            }
        } catch (IOException e3) {
            Log.e(TAG, "Could not close OutputStream.", e3);
        }
    }

    public static String readMetaDataStringFromPNG(String absolutePath, String key) throws PngjException {
        PngReader pngReader = new PngReader(new File(absolutePath));
        pngReader.readSkippingAllRows();
        for (PngChunk pngChunk : pngReader.getChunksList().getChunks()) {
            if (ChunkHelper.isText(pngChunk)) {
                PngChunkTextVar pngChunkTextVar = (PngChunkTextVar) pngChunk;
                String key2 = pngChunkTextVar.getKey();
                String val = pngChunkTextVar.getVal();
                if (key.equals(key2)) {
                    pngReader.close();
                    return val;
                }
            }
        }
        pngReader.close();
        return "";
    }

    public static synchronized void writeMetaDataStringToPNG(String absolutePath, String key, String value) {
        try {
            File file = new File(absolutePath);
            if (absolutePath.toLowerCase(Locale.US).endsWith(Constants.DEFAULT_IMAGE_EXTENSION)) {
                try {
                    File file2 = new File(absolutePath.substring(0, absolutePath.length() - 4) + "___temp.png");
                    PngReader pngReader = new PngReader(file);
                    PngWriter pngWriter = new PngWriter(file2, pngReader.imgInfo, true);
                    pngWriter.copyChunksFrom(pngReader.getChunksList(), 8);
                    pngWriter.getMetadata().setText(key, value);
                    for (int i = 0; i < pngReader.imgInfo.rows; i++) {
                        pngWriter.writeRow(pngReader.readRow());
                    }
                    pngReader.end();
                    pngWriter.end();
                    if (!file.delete()) {
                        Log.e(TAG, "writeMetaDataStringToPNG: Failed to delete old file");
                    }
                    if (!file2.renameTo(new File(absolutePath))) {
                        Log.e(TAG, "writeMetaDataStringToPNG: Failed to rename new file");
                    }
                } catch (PngjInputException unused) {
                    Log.w(TAG, "Tried to write metadata to a non-PNG or corrupted file: " + absolutePath);
                } catch (Exception e) {
                    Log.e(TAG, "Generic error while writing PNG metadata for: " + absolutePath, e);
                }
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public static boolean isPixelTransparent(int[] pixels, int width, int x, int y) {
        return pixels[x + (y * width)] == 0;
    }
}
