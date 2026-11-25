package org.catrobat.paintroid;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.core.content.FileProvider;
import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import id.zelory.compressor.Compressor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.catrobat.catroid.common.Constants;
import org.catrobat.paintroid.command.serialization.CommandSerializer;
import org.catrobat.paintroid.common.ConstantsKt;
import org.catrobat.paintroid.contract.MainActivityContracts;
import org.catrobat.paintroid.iotasks.BitmapReturnValue;
import org.catrobat.paintroid.iotasks.WorkspaceReturnValue;
import org.catrobat.paintroid.presenter.MainActivityPresenter;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;

/* JADX INFO: compiled from: FileIO.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000¸\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0010\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001tB\u0007\b\u0002¢\u0006\u0002\u0010\u0002J(\u0010%\u001a\u00020\u000e2\u0006\u0010&\u001a\u00020\u000e2\u0006\u0010'\u001a\u00020\u000e2\u0006\u0010(\u001a\u00020\u000e2\u0006\u0010)\u001a\u00020\u000eH\u0002J\u001e\u0010*\u001a\u00020\u00062\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00042\u0006\u0010+\u001a\u00020,J\u0018\u0010-\u001a\u00020\u00062\u0006\u0010\u0018\u001a\u00020\u00042\u0006\u0010+\u001a\u00020,H\u0002J\u0018\u0010.\u001a\u00020\u00062\u0006\u0010\u0018\u001a\u00020\u00042\u0006\u0010+\u001a\u00020,H\u0002J\u000e\u0010/\u001a\u00020\u00062\u0006\u00100\u001a\u000201J \u00102\u001a\u00020\u00062\u0006\u00103\u001a\u0002042\b\u00105\u001a\u0004\u0018\u0001012\u0006\u00106\u001a\u00020 J\u0018\u00107\u001a\u0002082\u0006\u00109\u001a\u00020:2\u0006\u0010;\u001a\u00020<H\u0002J\u001c\u0010=\u001a\u0002012\b\u0010\u0018\u001a\u0004\u0018\u00010\u00042\b\u0010>\u001a\u0004\u0018\u00010?H\u0007J*\u0010@\u001a\u0004\u0018\u00010A2\u0006\u0010+\u001a\u00020,2\u0006\u0010B\u001a\u00020 2\u0006\u0010C\u001a\u00020D2\b\u0010E\u001a\u0004\u0018\u00010FJ\u000e\u0010G\u001a\u00020H2\u0006\u00100\u001a\u000201J\u0014\u0010I\u001a\u0004\u0018\u00010A2\b\u0010J\u001a\u0004\u0018\u00010AH\u0007J\u0012\u0010K\u001a\u0004\u0018\u00010A2\b\u0010L\u001a\u0004\u0018\u000101J\"\u0010M\u001a\u0004\u0018\u00010A2\u0006\u0010+\u001a\u00020,2\u0006\u0010N\u001a\u00020 2\u0006\u0010E\u001a\u00020FH\u0007J\u0012\u0010O\u001a\u00020P2\b\u0010Q\u001a\u0004\u0018\u00010RH\u0007J\u0018\u0010S\u001a\u00020P2\u0006\u0010+\u001a\u00020,2\u0006\u0010B\u001a\u00020 H\u0003J\u001a\u0010T\u001a\u00020P2\u0006\u0010B\u001a\u00020 2\b\u0010E\u001a\u0004\u0018\u00010FH\u0002J \u0010U\u001a\u00020V2\u0006\u0010+\u001a\u00020,2\u0006\u0010N\u001a\u00020 2\b\u0010E\u001a\u0004\u0018\u00010FJ\u0012\u0010W\u001a\u00020X2\b\u0010E\u001a\u0004\u0018\u00010FH\u0002J\u001c\u0010Y\u001a\u0004\u0018\u00010A2\b\u0010J\u001a\u0004\u0018\u00010A2\u0006\u0010Z\u001a\u00020PH\u0007J\"\u0010[\u001a\u00020\u000e2\u0006\u0010+\u001a\u00020,2\u0006\u0010N\u001a\u00020 2\b\u0010E\u001a\u0004\u0018\u00010FH\u0002J \u0010\\\u001a\u00020V2\u0006\u0010+\u001a\u00020,2\u0006\u0010N\u001a\u00020 2\b\u0010E\u001a\u0004\u0018\u00010FJ\"\u0010]\u001a\u0004\u0018\u00010 2\u0006\u0010^\u001a\u00020 2\u0006\u0010\u0018\u001a\u00020\u00042\u0006\u0010+\u001a\u00020,H\u0002J\u0018\u0010_\u001a\u0004\u0018\u00010 2\u0006\u0010\u0018\u001a\u00020\u00042\u0006\u0010+\u001a\u00020,J\u0018\u0010`\u001a\u0004\u0018\u00010 2\u0006\u0010\u0018\u001a\u00020\u00042\u0006\u0010+\u001a\u00020,J\"\u0010a\u001a\u00020\u00062\u0006\u0010+\u001a\u00020,2\u0006\u0010N\u001a\u00020 2\b\u0010E\u001a\u0004\u0018\u00010FH\u0002J\u0010\u0010b\u001a\u0004\u0018\u00010c2\u0006\u0010d\u001a\u00020eJ\u0016\u0010f\u001a\u00020H2\u0006\u0010B\u001a\u00020 2\u0006\u0010+\u001a\u00020,J \u0010g\u001a\u00020H2\u0006\u0010h\u001a\u0002012\u0006\u0010i\u001a\u0002012\u0006\u00100\u001a\u000201H\u0002J\"\u0010j\u001a\u0004\u0018\u00010 2\b\u0010J\u001a\u0004\u0018\u00010A2\u0006\u00103\u001a\u0002042\u0006\u0010k\u001a\u00020\u0004J*\u0010l\u001a\u00020 2\u0006\u0010k\u001a\u00020\u00042\b\u0010J\u001a\u0004\u0018\u00010A2\b\u0010+\u001a\u0004\u0018\u00010,2\u0006\u0010E\u001a\u00020FJ\u001c\u0010m\u001a\u00020H2\b\u0010n\u001a\u0004\u0018\u00010<2\b\u0010J\u001a\u0004\u0018\u00010AH\u0002J \u0010o\u001a\u00020 2\u0006\u0010B\u001a\u00020 2\b\u0010J\u001a\u0004\u0018\u00010A2\u0006\u0010E\u001a\u00020FJ \u0010p\u001a\u00020H2\u0006\u0010B\u001a\u00020 2\u0006\u0010q\u001a\u0002012\u0006\u0010E\u001a\u00020FH\u0007J\u0016\u0010r\u001a\u00020H2\u0006\u00100\u001a\u0002012\u0006\u0010d\u001a\u00020eJ\u0010\u0010s\u001a\u00020H2\u0006\u00100\u001a\u000201H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u0012\u0010\u000b\u001a\u00020\f8\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\r\u001a\u00020\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0013\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u0012\u0010\u0016\u001a\u00020\u00178\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0018\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0019\u001a\u0004\u0018\u00010\u001aX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001eR\u0014\u0010\u001f\u001a\u0004\u0018\u00010 8\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u001c\u0010!\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u0015\"\u0004\b#\u0010$¨\u0006u"}, d2 = {"Lorg/catrobat/paintroid/FileIO;", "", "()V", "cacheChildFolder", "", "catroidFlag", "", "getCatroidFlag", "()Z", "setCatroidFlag", "(Z)V", "compressFormat", "Landroid/graphics/Bitmap$CompressFormat;", "compressQuality", "", "getCompressQuality", "()I", "setCompressQuality", "(I)V", "defaultFileName", "getDefaultFileName", "()Ljava/lang/String;", "fileType", "Lorg/catrobat/paintroid/FileIO$FileType;", "filename", "navigator", "Lorg/catrobat/paintroid/contract/MainActivityContracts$Navigator;", "getNavigator", "()Lorg/catrobat/paintroid/contract/MainActivityContracts$Navigator;", "setNavigator", "(Lorg/catrobat/paintroid/contract/MainActivityContracts$Navigator;)V", "storeImageUri", "Landroid/net/Uri;", "temporaryFilePath", "getTemporaryFilePath", "setTemporaryFilePath", "(Ljava/lang/String;)V", "calculateSampleSize", "width", "height", "maxWidth", "maxHeight", "checkFileExists", "resolver", "Landroid/content/ContentResolver;", "checkFileExistsInDownloadsFolder", "checkFileExistsInPicturesFolder", "checkForTemporaryFile", "internalMemoryPath", "Ljava/io/File;", "compress", "mainActivity", "Lorg/catrobat/paintroid/MainActivity;", "fileToCompress", FirebaseAnalytics.Param.DESTINATION, "copyStreams", "", TypedValues.TransitionType.S_FROM, "Ljava/io/InputStream;", "to", "Ljava/io/OutputStream;", "createNewEmptyPictureFile", "activity", "Landroid/app/Activity;", "decodeBitmapFromUri", "Landroid/graphics/Bitmap;", "uri", "options", "Landroid/graphics/BitmapFactory$Options;", "context", "Landroid/content/Context;", "deleteTempFile", "", "enableAlpha", "bitmap", "getBitmapFromFile", "bitmapFile", "getBitmapFromUri", "bitmapUri", "getBitmapOrientation", "", "exifInterface", "Landroidx/exifinterface/media/ExifInterface;", "getBitmapOrientationFromInputStream", "getBitmapOrientationFromUri", "getBitmapReturnValueFromUri", "Lorg/catrobat/paintroid/iotasks/BitmapReturnValue;", "getMemoryInfo", "Landroid/app/ActivityManager$MemoryInfo;", "getOrientedBitmap", "angle", "getScaleFactor", "getScaledBitmapFromUri", "getUriForFilename", "contentLocationUri", "getUriForFilenameInDownloadsFolder", "getUriForFilenameInPicturesFolder", "hasEnoughMemory", "openTemporaryPictureFile", "Lorg/catrobat/paintroid/iotasks/WorkspaceReturnValue;", "commandSerializer", "Lorg/catrobat/paintroid/command/serialization/CommandSerializer;", "parseFileName", "reorganizeTempFiles", "file1", "file2", "saveBitmapToCache", PsiTreeChangeEvent.PROP_FILE_NAME, "saveBitmapToFile", "saveBitmapToStream", "outputStream", "saveBitmapToUri", "saveFileFromUri", "destFile", "saveTemporaryPictureFile", "tryDeleteFile", "FileType", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class FileIO {
    private static boolean catroidFlag;
    private static MainActivityContracts.Navigator navigator;
    public static Uri storeImageUri;
    private static String temporaryFilePath;
    public static final FileIO INSTANCE = new FileIO();
    public static String filename = Constants.TMP_IMAGE_FILE_NAME;
    public static FileType fileType = FileType.PNG;
    private static int compressQuality = 100;
    public static Bitmap.CompressFormat compressFormat = Bitmap.CompressFormat.PNG;
    private static final String cacheChildFolder = Constants.IMAGE_DIRECTORY_NAME;

    /* JADX INFO: compiled from: FileIO.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[FileType.values().length];
            iArr[FileType.JPG.ordinal()] = 1;
            iArr[FileType.PNG.ordinal()] = 2;
            iArr[FileType.ORA.ordinal()] = 3;
            iArr[FileType.CATROBAT.ordinal()] = 4;
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private FileIO() {
    }

    public final int getCompressQuality() {
        return compressQuality;
    }

    public final void setCompressQuality(int i) {
        compressQuality = i;
    }

    public final boolean getCatroidFlag() {
        return catroidFlag;
    }

    public final void setCatroidFlag(boolean z) {
        catroidFlag = z;
    }

    public final MainActivityContracts.Navigator getNavigator() {
        return navigator;
    }

    public final void setNavigator(MainActivityContracts.Navigator navigator2) {
        navigator = navigator2;
    }

    public final String getTemporaryFilePath() {
        return temporaryFilePath;
    }

    public final void setTemporaryFilePath(String str) {
        temporaryFilePath = str;
    }

    public final String getDefaultFileName() {
        return Intrinsics.stringPlus(filename, fileType.toExtension());
    }

    /* JADX INFO: compiled from: FileIO.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\u0007\u001a\u00020\u0003R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000b¨\u0006\f"}, d2 = {"Lorg/catrobat/paintroid/FileIO$FileType;", "", "value", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getValue", "()Ljava/lang/String;", "toExtension", "PNG", "JPG", "ORA", CommandSerializer.MAGIC_VALUE, "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public enum FileType {
        PNG("png"),
        JPG("jpg"),
        ORA("ora"),
        CATROBAT(ConstantsKt.CATROBAT_IMAGE_ENDING);

        private final String value;

        FileType(String str) {
            this.value = str;
        }

        public final String getValue() {
            return this.value;
        }

        public final String toExtension() {
            return Intrinsics.stringPlus(".", this.value);
        }
    }

    private final void saveBitmapToStream(OutputStream outputStream, Bitmap bitmap) throws IOException {
        if (!((bitmap == null || bitmap.isRecycled()) ? false : true)) {
            throw new IllegalArgumentException("Bitmap is invalid".toString());
        }
        if (compressFormat == Bitmap.CompressFormat.JPEG) {
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
            Canvas canvas = new Canvas(bitmapCreateBitmap);
            canvas.drawColor(-1);
            canvas.drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
            bitmap = bitmapCreateBitmap;
        }
        if (bitmap != null && !bitmap.compress(compressFormat, compressQuality, outputStream)) {
            throw new IOException("Can not write png to stream.");
        }
    }

    public final Uri saveBitmapToUri(Uri uri, Bitmap bitmap, Context context) throws IOException {
        Intrinsics.checkNotNullParameter(uri, "uri");
        Intrinsics.checkNotNullParameter(context, "context");
        String string = UUID.randomUUID().toString();
        Intrinsics.checkNotNullExpressionValue(string, "uid.toString()");
        Uri uriSaveBitmapToCache = saveBitmapToCache(bitmap, (MainActivity) context, string);
        File file = uriSaveBitmapToCache == null ? null : new File(MainActivityPresenter.INSTANCE.getPathFromUri(context, uriSaveBitmapToCache));
        if (file != null) {
            try {
                if (compress((MainActivity) context, file, uri)) {
                    if (file.exists()) {
                        file.delete();
                    }
                    return uri;
                }
            } catch (Throwable th) {
                if (file != null && file.exists()) {
                    file.delete();
                }
                throw th;
            }
        }
        throw new IOException("Can not open URI.");
    }

    public final boolean compress(MainActivity mainActivity, File fileToCompress, Uri destination) {
        File parentFile;
        Intrinsics.checkNotNullParameter(mainActivity, "mainActivity");
        Intrinsics.checkNotNullParameter(destination, "destination");
        if (fileToCompress == null) {
            return false;
        }
        Compressor compressor = new Compressor(mainActivity);
        compressor.setQuality(compressQuality);
        compressor.setCompressFormat(compressFormat);
        if (Build.VERSION.SDK_INT >= 29) {
            File fileCompressToFile = null;
            try {
                try {
                    File file = new File(mainActivity.getCacheDir(), cacheChildFolder);
                    file.mkdirs();
                    compressor.setDestinationDirectoryPath(file.getPath());
                    fileCompressToFile = compressor.compressToFile(fileToCompress, Intrinsics.stringPlus(ConstantsKt.TEMP_PICTURE_NAME, fileType.toExtension()));
                    OutputStream outputStreamOpenOutputStream = mainActivity.getContentResolver().openOutputStream(destination);
                    if (outputStreamOpenOutputStream != null) {
                        INSTANCE.copyStreams(new FileInputStream(fileCompressToFile), outputStreamOpenOutputStream);
                    }
                } catch (IOException e) {
                    Log.e("Compression", "Can not compress image file.", e);
                    return false;
                }
            } finally {
                if (fileCompressToFile != null && fileCompressToFile.exists()) {
                    fileCompressToFile.delete();
                }
            }
        } else {
            try {
                String path = destination.getPath();
                if (path != null && (parentFile = new File(path).getParentFile()) != null) {
                    compressor.setDestinationDirectoryPath(parentFile.getPath());
                }
                compressor.compressToFile(fileToCompress, destination.getLastPathSegment());
            } catch (IOException e2) {
                Log.e("Compression", "Can not compress image file", e2);
                return false;
            }
        }
        return true;
    }

    public final Uri saveBitmapToFile(String fileName, Bitmap bitmap, ContentResolver resolver, Context context) throws IOException {
        Uri uriFromFile;
        Intrinsics.checkNotNullParameter(fileName, "fileName");
        Intrinsics.checkNotNullParameter(context, "context");
        if (Build.VERSION.SDK_INT >= 29) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("_display_name", fileName);
            contentValues.put("mime_type", "image/*");
            contentValues.put("relative_path", Environment.DIRECTORY_PICTURES);
            uriFromFile = resolver == null ? null : resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        } else {
            if (!org.catrobat.paintroid.common.Constants.PICTURES_DIRECTORY.exists() && !org.catrobat.paintroid.common.Constants.PICTURES_DIRECTORY.mkdirs()) {
                throw new IOException("Can not create media directory.");
            }
            uriFromFile = Uri.fromFile(new File(org.catrobat.paintroid.common.Constants.PICTURES_DIRECTORY, fileName));
        }
        String string = UUID.randomUUID().toString();
        Intrinsics.checkNotNullExpressionValue(string, "randomUUID().toString()");
        Uri uriSaveBitmapToCache = saveBitmapToCache(bitmap, (MainActivity) context, string);
        File file = uriSaveBitmapToCache != null ? new File(MainActivityPresenter.INSTANCE.getPathFromUri(context, uriSaveBitmapToCache)) : null;
        if (uriFromFile != null && file != null) {
            try {
                if (compress((MainActivity) context, file, uriFromFile)) {
                    if (file.exists()) {
                        file.delete();
                    }
                    return uriFromFile;
                }
            } catch (Throwable th) {
                if (file != null && file.exists()) {
                    file.delete();
                }
                throw th;
            }
        }
        throw new IOException("Can not compress image file.");
    }

    public final Uri saveBitmapToCache(Bitmap bitmap, MainActivity mainActivity, String fileName) {
        Intrinsics.checkNotNullParameter(mainActivity, "mainActivity");
        Intrinsics.checkNotNullParameter(fileName, "fileName");
        try {
            File cacheDir = mainActivity.getCacheDir();
            String str = cacheChildFolder;
            File file = new File(cacheDir, str);
            file.mkdirs();
            FileOutputStream fileOutputStream = new FileOutputStream(file + '/' + fileName + fileType.toExtension());
            saveBitmapToStream(fileOutputStream, bitmap);
            fileOutputStream.close();
            return FileProvider.getUriForFile(mainActivity.getApplicationContext(), Intrinsics.stringPlus(mainActivity.getApplicationContext().getPackageName(), ".fileprovider"), new File(new File(mainActivity.getCacheDir(), str), Intrinsics.stringPlus(fileName, fileType.toExtension())));
        } catch (IOException e) {
            Log.e("Can not write", "Can not write png to stream.", e);
            return null;
        }
    }

    @JvmStatic
    public static final File createNewEmptyPictureFile(String filename2, Activity activity) throws NullPointerException {
        if (filename2 == null) {
            filename2 = INSTANCE.getDefaultFileName();
        }
        Locale US = Locale.US;
        Intrinsics.checkNotNullExpressionValue(US, "US");
        if (filename2 == null) {
            throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
        }
        String lowerCase = filename2.toLowerCase(US);
        Intrinsics.checkNotNullExpressionValue(lowerCase, "(this as java.lang.String).toLowerCase(locale)");
        if (!StringsKt.endsWith$default(lowerCase, fileType.toExtension(), false, 2, (Object) null)) {
            filename2 = Intrinsics.stringPlus(filename2, fileType.toExtension());
        }
        File externalFilesDir = activity != null ? activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES) : null;
        if (externalFilesDir == null || (!externalFilesDir.exists() && !externalFilesDir.mkdir())) {
            throw new NullPointerException("Can not create media directory.");
        }
        return new File(externalFilesDir, filename2);
    }

    public final Bitmap decodeBitmapFromUri(ContentResolver resolver, Uri uri, BitmapFactory.Options options, Context context) throws IOException {
        Intrinsics.checkNotNullParameter(resolver, "resolver");
        Intrinsics.checkNotNullParameter(uri, "uri");
        Intrinsics.checkNotNullParameter(options, "options");
        InputStream inputStreamOpenInputStream = resolver.openInputStream(uri);
        if (inputStreamOpenInputStream == null) {
            throw new IOException("Can't open input stream");
        }
        InputStream inputStream = inputStreamOpenInputStream;
        try {
            Bitmap bitmapDecodeStream = BitmapFactory.decodeStream(inputStream, null, options);
            if (options.inJustDecodeBounds) {
                CloseableKt.closeFinally(inputStream, null);
                return bitmapDecodeStream;
            }
            Bitmap orientedBitmap = getOrientedBitmap(bitmapDecodeStream, INSTANCE.getBitmapOrientationFromInputStream(resolver, uri));
            CloseableKt.closeFinally(inputStream, null);
            return orientedBitmap;
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                CloseableKt.closeFinally(inputStream, th);
                throw th2;
            }
        }
    }

    private final float getBitmapOrientationFromInputStream(ContentResolver resolver, Uri uri) throws IOException {
        InputStream inputStreamOpenInputStream = resolver.openInputStream(uri);
        if (inputStreamOpenInputStream == null) {
            return 0.0f;
        }
        InputStream inputStream = inputStreamOpenInputStream;
        try {
            float bitmapOrientation = getBitmapOrientation(new ExifInterface(inputStream));
            CloseableKt.closeFinally(inputStream, null);
            return bitmapOrientation;
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                CloseableKt.closeFinally(inputStream, th);
                throw th2;
            }
        }
    }

    private final float getBitmapOrientationFromUri(Uri uri, Context context) throws IOException {
        return getBitmapOrientation(context == null ? null : new ExifInterface(MainActivityPresenter.INSTANCE.getPathFromUri(context, uri)));
    }

    @JvmStatic
    public static final Bitmap getOrientedBitmap(Bitmap bitmap, float angle) {
        if (bitmap == null) {
            return null;
        }
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        bitmap.recycle();
        return bitmapCreateBitmap;
    }

    @JvmStatic
    public static final float getBitmapOrientation(ExifInterface exifInterface) {
        if (exifInterface == null) {
            return 0.0f;
        }
        int attributeInt = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
        if (attributeInt == 3) {
            return 180.0f;
        }
        if (attributeInt != 6) {
            return attributeInt != 8 ? 0.0f : 270.0f;
        }
        return 90.0f;
    }

    public final void parseFileName(Uri uri, ContentResolver resolver) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        Intrinsics.checkNotNullParameter(resolver, "resolver");
        Cursor cursorQuery = resolver.query(uri, new String[]{"_display_name"}, null, null, null);
        String str = Constants.TMP_IMAGE_FILE_NAME;
        if (cursorQuery != null) {
            Cursor cursor = cursorQuery;
            try {
                if (cursorQuery.moveToFirst()) {
                    String string = cursorQuery.getString(cursorQuery.getColumnIndexOrThrow("_display_name"));
                    Intrinsics.checkNotNullExpressionValue(string, "cursor.getString(cursor.…ageColumns.DISPLAY_NAME))");
                    str = string;
                }
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(cursor, null);
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    CloseableKt.closeFinally(cursor, th);
                    throw th2;
                }
            }
        }
        if (StringsKt.endsWith$default(str, FileType.JPG.toExtension(), false, 2, (Object) null) || StringsKt.endsWith$default(str, ".jpeg", false, 2, (Object) null)) {
            fileType = FileType.JPG;
            compressFormat = Bitmap.CompressFormat.JPEG;
            String strSubstring = str.substring(0, str.length() - fileType.toExtension().length());
            Intrinsics.checkNotNullExpressionValue(strSubstring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            filename = strSubstring;
            return;
        }
        if (StringsKt.endsWith$default(str, Constants.DEFAULT_IMAGE_EXTENSION, false, 2, (Object) null)) {
            fileType = FileType.PNG;
            compressFormat = Bitmap.CompressFormat.PNG;
            String strSubstring2 = str.substring(0, str.length() - fileType.toExtension().length());
            Intrinsics.checkNotNullExpressionValue(strSubstring2, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            filename = strSubstring2;
        }
    }

    @JvmStatic
    public static final void saveFileFromUri(Uri uri, File destFile, Context context) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        Intrinsics.checkNotNullParameter(destFile, "destFile");
        Intrinsics.checkNotNullParameter(context, "context");
        try {
            InputStream inputStreamOpenInputStream = context.getContentResolver().openInputStream(uri);
            if (inputStreamOpenInputStream == null) {
                return;
            }
            InputStream inputStream = inputStreamOpenInputStream;
            try {
                InputStream inputStream2 = inputStream;
                FileOutputStream fileOutputStream = new FileOutputStream(destFile);
                try {
                    long jCopyStreams = INSTANCE.copyStreams(inputStream2, fileOutputStream);
                    CloseableKt.closeFinally(fileOutputStream, null);
                    Long.valueOf(jCopyStreams);
                    CloseableKt.closeFinally(inputStream, null);
                } catch (Throwable th) {
                    try {
                        throw th;
                    } catch (Throwable th2) {
                        CloseableKt.closeFinally(fileOutputStream, th);
                        throw th2;
                    }
                }
            } catch (Throwable th3) {
                try {
                    throw th3;
                } catch (Throwable th4) {
                    CloseableKt.closeFinally(inputStream, th3);
                    throw th4;
                }
            }
        } catch (IOException e) {
            Log.e("FileIO", "Can not copy streams.", e);
        }
    }

    private final long copyStreams(InputStream from, OutputStream to) throws IOException {
        byte[] bArr = new byte[4096];
        long j = 0;
        while (true) {
            int i = from.read(bArr);
            if (i == -1) {
                return j;
            }
            to.write(bArr, 0, i);
            j += (long) i;
        }
    }

    private final Uri getUriForFilename(Uri contentLocationUri, String filename2, ContentResolver resolver) {
        Cursor cursorQuery = resolver.query(contentLocationUri, null, "_display_name=?", new String[]{filename2}, null);
        if (cursorQuery == null) {
            return null;
        }
        while (cursorQuery.moveToNext()) {
            if (Intrinsics.areEqual(cursorQuery.getString(cursorQuery.getColumnIndexOrThrow("_display_name")), filename2)) {
                long j = cursorQuery.getLong(cursorQuery.getColumnIndexOrThrow("_id"));
                cursorQuery.close();
                return ContentUris.withAppendedId(contentLocationUri, j);
            }
        }
        cursorQuery.close();
        return null;
    }

    public final Uri getUriForFilenameInPicturesFolder(String filename2, ContentResolver resolver) {
        Intrinsics.checkNotNullParameter(filename2, "filename");
        Intrinsics.checkNotNullParameter(resolver, "resolver");
        if (Build.VERSION.SDK_INT >= 29) {
            Uri contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            Intrinsics.checkNotNullExpressionValue(contentUri, "contentUri");
            return getUriForFilename(contentUri, filename2, resolver);
        }
        File file = new File(org.catrobat.paintroid.common.Constants.PICTURES_DIRECTORY, filename2);
        if (file.exists()) {
            return Uri.fromFile(file);
        }
        return null;
    }

    public final Uri getUriForFilenameInDownloadsFolder(String filename2, ContentResolver resolver) {
        Intrinsics.checkNotNullParameter(filename2, "filename");
        Intrinsics.checkNotNullParameter(resolver, "resolver");
        if (Build.VERSION.SDK_INT >= 29) {
            Uri EXTERNAL_CONTENT_URI = MediaStore.Downloads.EXTERNAL_CONTENT_URI;
            Intrinsics.checkNotNullExpressionValue(EXTERNAL_CONTENT_URI, "EXTERNAL_CONTENT_URI");
            return getUriForFilename(EXTERNAL_CONTENT_URI, filename2, resolver);
        }
        File file = new File(org.catrobat.paintroid.common.Constants.DOWNLOADS_DIRECTORY, filename2);
        if (file.exists()) {
            return Uri.fromFile(file);
        }
        return null;
    }

    public final boolean checkFileExists(FileType fileType2, String filename2, ContentResolver resolver) {
        Intrinsics.checkNotNullParameter(fileType2, "fileType");
        Intrinsics.checkNotNullParameter(filename2, "filename");
        Intrinsics.checkNotNullParameter(resolver, "resolver");
        int i = WhenMappings.$EnumSwitchMapping$0[fileType2.ordinal()];
        if (i == 1 || i == 2) {
            return checkFileExistsInPicturesFolder(filename2, resolver);
        }
        if (i == 3 || i == 4) {
            return checkFileExistsInDownloadsFolder(filename2, resolver);
        }
        throw new NoWhenBranchMatchedException();
    }

    private final boolean checkFileExistsInPicturesFolder(String filename2, ContentResolver resolver) {
        return getUriForFilenameInPicturesFolder(filename2, resolver) != null;
    }

    private final boolean checkFileExistsInDownloadsFolder(String filename2, ContentResolver resolver) {
        return getUriForFilenameInDownloadsFolder(filename2, resolver) != null;
    }

    private final int calculateSampleSize(int width, int height, int maxWidth, int maxHeight) {
        int i = 1;
        while (true) {
            if (width <= maxWidth && height <= maxHeight) {
                return i;
            }
            width /= 2;
            height /= 2;
            i *= 2;
        }
    }

    @JvmStatic
    public static final Bitmap getBitmapFromUri(ContentResolver resolver, Uri bitmapUri, Context context) throws IOException {
        Intrinsics.checkNotNullParameter(resolver, "resolver");
        Intrinsics.checkNotNullParameter(bitmapUri, "bitmapUri");
        Intrinsics.checkNotNullParameter(context, "context");
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;
        return enableAlpha(INSTANCE.decodeBitmapFromUri(resolver, bitmapUri, options, context));
    }

    private final ActivityManager.MemoryInfo getMemoryInfo(Context context) {
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) (context == null ? null : context.getSystemService("activity"));
        if (activityManager != null) {
            activityManager.getMemoryInfo(memoryInfo);
        }
        return memoryInfo;
    }

    private final boolean hasEnoughMemory(ContentResolver resolver, Uri bitmapUri, Context context) throws IOException {
        ActivityManager.MemoryInfo memoryInfo = getMemoryInfo(context);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        decodeBitmapFromUri(resolver, bitmapUri, options, context);
        if (options.outHeight < 0 || options.outWidth < 0) {
            throw new IOException("Can't load bitmap from uri");
        }
        return ((long) (options.outWidth * options.outHeight)) * 4 > Math.min((long) (((float) (memoryInfo.availMem - memoryInfo.threshold)) * 0.9f), 100000000L);
    }

    private final int getScaleFactor(ContentResolver resolver, Uri bitmapUri, Context context) throws IOException {
        getMemoryInfo(context);
        BitmapFactory.Options options = new BitmapFactory.Options();
        decodeBitmapFromUri(resolver, bitmapUri, options, context);
        if (options.outHeight <= 0 || options.outWidth <= 0) {
            throw new IOException("Can't load bitmap from uri");
        }
        Runtime runtime = Runtime.getRuntime();
        float fMaxMemory = (((((runtime.maxMemory() - runtime.totalMemory()) + runtime.freeMemory()) * 0.9f) / 100.0f) * 0.9f) / 4;
        float fSqrt = (float) Math.sqrt(fMaxMemory / ((options.outWidth / options.outHeight) * 1.0f));
        return calculateSampleSize(options.outWidth, options.outHeight, (int) (fMaxMemory / fSqrt), (int) fSqrt);
    }

    public final BitmapReturnValue getBitmapReturnValueFromUri(ContentResolver resolver, Uri bitmapUri, Context context) throws IOException {
        Intrinsics.checkNotNullParameter(resolver, "resolver");
        Intrinsics.checkNotNullParameter(bitmapUri, "bitmapUri");
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;
        options.inJustDecodeBounds = false;
        return new BitmapReturnValue(null, enableAlpha(decodeBitmapFromUri(resolver, bitmapUri, options, context)), hasEnoughMemory(resolver, bitmapUri, context));
    }

    public final BitmapReturnValue getScaledBitmapFromUri(ContentResolver resolver, Uri bitmapUri, Context context) throws IOException {
        Intrinsics.checkNotNullParameter(resolver, "resolver");
        Intrinsics.checkNotNullParameter(bitmapUri, "bitmapUri");
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;
        options.inJustDecodeBounds = false;
        options.inSampleSize = INSTANCE.getScaleFactor(resolver, bitmapUri, context);
        return new BitmapReturnValue(null, enableAlpha(decodeBitmapFromUri(resolver, bitmapUri, options, context)), false);
    }

    public final Bitmap getBitmapFromFile(File bitmapFile) {
        if (bitmapFile == null) {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;
        return enableAlpha(BitmapFactory.decodeFile(bitmapFile.getAbsolutePath(), options));
    }

    @JvmStatic
    public static final Bitmap enableAlpha(Bitmap bitmap) {
        if (bitmap != null) {
            bitmap.setHasAlpha(true);
        }
        return bitmap;
    }

    public final void saveTemporaryPictureFile(File internalMemoryPath, CommandSerializer commandSerializer) {
        Intrinsics.checkNotNullParameter(internalMemoryPath, "internalMemoryPath");
        Intrinsics.checkNotNullParameter(commandSerializer, "commandSerializer");
        File file = new File(internalMemoryPath, ConstantsKt.TEMP_IMAGE_DIRECTORY_NAME);
        try {
            file.mkdirs();
            commandSerializer.writeToInternalMemory(new FileOutputStream(file + "/temporaryImage1.catrobat-image"));
            temporaryFilePath = ConstantsKt.TEMP_IMAGE_TEMP_PATH;
        } catch (IOException e) {
            Log.e("Cannot write", "Can't write to stream", e);
        }
        File file2 = new File(internalMemoryPath, ConstantsKt.TEMP_IMAGE_PATH);
        if (file2.exists()) {
            file2.delete();
        }
        File file3 = new File(internalMemoryPath, ConstantsKt.TEMP_IMAGE_TEMP_PATH);
        if (file3.exists()) {
            file3.renameTo(new File(internalMemoryPath, ConstantsKt.TEMP_IMAGE_PATH));
            temporaryFilePath = ConstantsKt.TEMP_IMAGE_PATH;
        }
    }

    public final boolean checkForTemporaryFile(File internalMemoryPath) {
        File[] fileArrListFiles;
        Intrinsics.checkNotNullParameter(internalMemoryPath, "internalMemoryPath");
        File file = new File(internalMemoryPath, ConstantsKt.TEMP_IMAGE_DIRECTORY_NAME);
        if (file.exists() && (fileArrListFiles = file.listFiles()) != null) {
            if (!(fileArrListFiles.length == 0)) {
                if (fileArrListFiles.length != 2) {
                    temporaryFilePath = fileArrListFiles[0].getPath();
                } else if (fileArrListFiles[1].lastModified() > fileArrListFiles[0].lastModified()) {
                    File file2 = fileArrListFiles[1];
                    Intrinsics.checkNotNullExpressionValue(file2, "fileList[1]");
                    File file3 = fileArrListFiles[0];
                    Intrinsics.checkNotNullExpressionValue(file3, "fileList[0]");
                    reorganizeTempFiles(file2, file3, internalMemoryPath);
                } else {
                    File file4 = fileArrListFiles[0];
                    Intrinsics.checkNotNullExpressionValue(file4, "fileList[0]");
                    File file5 = fileArrListFiles[1];
                    Intrinsics.checkNotNullExpressionValue(file5, "fileList[1]");
                    reorganizeTempFiles(file4, file5, internalMemoryPath);
                }
                return true;
            }
        }
        return false;
    }

    private final void reorganizeTempFiles(File file1, File file2, File internalMemoryPath) {
        file2.delete();
        file1.renameTo(new File(internalMemoryPath, ConstantsKt.TEMP_IMAGE_PATH));
        temporaryFilePath = ConstantsKt.TEMP_IMAGE_PATH;
    }

    public final WorkspaceReturnValue openTemporaryPictureFile(CommandSerializer commandSerializer) {
        Intrinsics.checkNotNullParameter(commandSerializer, "commandSerializer");
        if (temporaryFilePath != null) {
            try {
                return commandSerializer.readFromInternalMemory(new FileInputStream(temporaryFilePath));
            } catch (IOException e) {
                Log.e("Cannot read", "Can't read from stream", e);
            }
        }
        return null;
    }

    public final void deleteTempFile(File internalMemoryPath) {
        Intrinsics.checkNotNullParameter(internalMemoryPath, "internalMemoryPath");
        tryDeleteFile(internalMemoryPath);
    }

    private final void tryDeleteFile(File internalMemoryPath) {
        if (temporaryFilePath != null) {
            String str = temporaryFilePath;
            if (str == null) {
                str = "";
            }
            File file = new File(internalMemoryPath, str);
            if (file.exists()) {
                file.delete();
            }
        }
    }
}
