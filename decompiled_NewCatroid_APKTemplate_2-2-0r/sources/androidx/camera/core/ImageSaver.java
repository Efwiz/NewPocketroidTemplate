package androidx.camera.core;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import androidx.camera.core.internal.utils.ImageUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;

/* JADX INFO: loaded from: classes.dex */
final class ImageSaver implements Runnable {
    private static final int COPY_BUFFER_SIZE = 1024;
    private static final int NOT_PENDING = 0;
    private static final int PENDING = 1;
    private static final String TAG = "ImageSaver";
    private static final String TEMP_FILE_PREFIX = "CameraX";
    private static final String TEMP_FILE_SUFFIX = ".tmp";
    final OnImageSavedCallback mCallback;
    private final Executor mExecutor;
    private final ImageProxy mImage;
    private final int mOrientation;
    private final ImageCapture.OutputFileOptions mOutputFileOptions;

    public interface OnImageSavedCallback {
        void onError(SaveError saveError, String str, Throwable th);

        void onImageSaved(ImageCapture.OutputFileResults outputFileResults);
    }

    public enum SaveError {
        FILE_IO_FAILED,
        ENCODE_FAILED,
        CROP_FAILED,
        UNKNOWN
    }

    ImageSaver(ImageProxy imageProxy, ImageCapture.OutputFileOptions outputFileOptions, int i, Executor executor, OnImageSavedCallback onImageSavedCallback) {
        this.mImage = imageProxy;
        this.mOutputFileOptions = outputFileOptions;
        this.mOrientation = i;
        this.mCallback = onImageSavedCallback;
        this.mExecutor = executor;
    }

    /* JADX WARN: Bottom block not found for handler: all -> 0x0115 */
    /* JADX WARN: Code duplicated, block: B:77:0x013c  */
    /* JADX WARN: Code duplicated, block: B:79:0x013f A[Catch: all -> 0x0129, TryCatch #3 {all -> 0x0129, blocks: (B:7:0x0017, B:45:0x00fb, B:75:0x012e, B:79:0x013f, B:80:0x0144, B:81:0x0149, B:90:0x015b, B:64:0x0122, B:63:0x011f), top: B:108:0x0017 }] */
    /* JADX WARN: Code duplicated, block: B:80:0x0144 A[Catch: all -> 0x0129, TryCatch #3 {all -> 0x0129, blocks: (B:7:0x0017, B:45:0x00fb, B:75:0x012e, B:79:0x013f, B:80:0x0144, B:81:0x0149, B:90:0x015b, B:64:0x0122, B:63:0x011f), top: B:108:0x0017 }] */
    /* JADX WARN: Code duplicated, block: B:81:0x0149 A[Catch: all -> 0x0129, TRY_LEAVE, TryCatch #3 {all -> 0x0129, blocks: (B:7:0x0017, B:45:0x00fb, B:75:0x012e, B:79:0x013f, B:80:0x0144, B:81:0x0149, B:90:0x015b, B:64:0x0122, B:63:0x011f), top: B:108:0x0017 }] */
    /* JADX WARN: Code duplicated, block: B:95:0x0168  */
    /* JADX WARN: Code duplicated, block: B:96:0x016c  */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void run() {
        /*
            Method dump skipped, instruction units count: 387
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.core.ImageSaver.run():void");
    }

    /* JADX INFO: renamed from: androidx.camera.core.ImageSaver$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$androidx$camera$core$internal$utils$ImageUtil$CodecFailedException$FailureType;

        static {
            int[] iArr = new int[ImageUtil.CodecFailedException.FailureType.values().length];
            $SwitchMap$androidx$camera$core$internal$utils$ImageUtil$CodecFailedException$FailureType = iArr;
            try {
                iArr[ImageUtil.CodecFailedException.FailureType.ENCODE_FAILED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$androidx$camera$core$internal$utils$ImageUtil$CodecFailedException$FailureType[ImageUtil.CodecFailedException.FailureType.DECODE_FAILED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$androidx$camera$core$internal$utils$ImageUtil$CodecFailedException$FailureType[ImageUtil.CodecFailedException.FailureType.UNKNOWN.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    private boolean isSaveToMediaStore() {
        return (this.mOutputFileOptions.getSaveCollection() == null || this.mOutputFileOptions.getContentResolver() == null || this.mOutputFileOptions.getContentValues() == null) ? false : true;
    }

    private boolean isSaveToFile() {
        return this.mOutputFileOptions.getFile() != null;
    }

    private boolean isSaveToOutputStream() {
        return this.mOutputFileOptions.getOutputStream() != null;
    }

    private void setUriNotPending(Uri uri) {
        if (Build.VERSION.SDK_INT >= 29) {
            ContentValues contentValues = new ContentValues();
            setContentValuePending(contentValues, 0);
            this.mOutputFileOptions.getContentResolver().update(uri, contentValues, null, null);
        }
    }

    private void setContentValuePending(ContentValues contentValues, int i) {
        if (Build.VERSION.SDK_INT >= 29) {
            contentValues.put("is_pending", Integer.valueOf(i));
        }
    }

    private boolean copyTempFileToUri(File file, Uri uri) throws IOException {
        OutputStream outputStreamOpenOutputStream = this.mOutputFileOptions.getContentResolver().openOutputStream(uri);
        if (outputStreamOpenOutputStream == null) {
            if (outputStreamOpenOutputStream == null) {
                return false;
            }
            outputStreamOpenOutputStream.close();
            return false;
        }
        try {
            copyTempFileToOutputStream(file, outputStreamOpenOutputStream);
            if (outputStreamOpenOutputStream == null) {
                return true;
            }
            outputStreamOpenOutputStream.close();
            return true;
        } catch (Throwable th) {
            if (outputStreamOpenOutputStream != null) {
                try {
                    outputStreamOpenOutputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    private void copyTempFileToOutputStream(File file, OutputStream outputStream) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        try {
            byte[] bArr = new byte[1024];
            while (true) {
                int i = fileInputStream.read(bArr);
                if (i > 0) {
                    outputStream.write(bArr, 0, i);
                } else {
                    fileInputStream.close();
                    return;
                }
            }
        } catch (Throwable th) {
            try {
                fileInputStream.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    private void postSuccess(final Uri uri) {
        try {
            this.mExecutor.execute(new Runnable() { // from class: androidx.camera.core.ImageSaver$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m87lambda$postSuccess$0$androidxcameracoreImageSaver(uri);
                }
            });
        } catch (RejectedExecutionException unused) {
            Log.e(TAG, "Application executor rejected executing OnImageSavedCallback.onImageSaved callback. Skipping.");
        }
    }

    /* JADX INFO: renamed from: lambda$postSuccess$0$androidx-camera-core-ImageSaver, reason: not valid java name */
    /* synthetic */ void m87lambda$postSuccess$0$androidxcameracoreImageSaver(Uri uri) {
        this.mCallback.onImageSaved(new ImageCapture.OutputFileResults(uri));
    }

    private void postError(final SaveError saveError, final String str, final Throwable th) {
        try {
            this.mExecutor.execute(new Runnable() { // from class: androidx.camera.core.ImageSaver$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m86lambda$postError$1$androidxcameracoreImageSaver(saveError, str, th);
                }
            });
        } catch (RejectedExecutionException unused) {
            Log.e(TAG, "Application executor rejected executing OnImageSavedCallback.onError callback. Skipping.");
        }
    }

    /* JADX INFO: renamed from: lambda$postError$1$androidx-camera-core-ImageSaver, reason: not valid java name */
    /* synthetic */ void m86lambda$postError$1$androidxcameracoreImageSaver(SaveError saveError, String str, Throwable th) {
        this.mCallback.onError(saveError, str, th);
    }
}
