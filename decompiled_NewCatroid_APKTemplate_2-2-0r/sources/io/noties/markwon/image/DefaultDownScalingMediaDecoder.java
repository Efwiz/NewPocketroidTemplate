package io.noties.markwon.image;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;

/* JADX INFO: loaded from: classes11.dex */
public class DefaultDownScalingMediaDecoder extends MediaDecoder {
    private final int maxHeight;
    private final int maxWidth;
    private final Resources resources;

    public static DefaultDownScalingMediaDecoder create(int i, int i2) {
        return create(Resources.getSystem(), i, i2);
    }

    public static DefaultDownScalingMediaDecoder create(Resources resources, int i, int i2) {
        return new DefaultDownScalingMediaDecoder(resources, i, i2);
    }

    private DefaultDownScalingMediaDecoder(Resources resources, int i, int i2) {
        this.resources = resources;
        this.maxWidth = i;
        this.maxHeight = i2;
    }

    @Override // io.noties.markwon.image.MediaDecoder
    public Drawable decode(String str, InputStream inputStream) {
        File fileWriteToTempFile = writeToTempFile(inputStream);
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            decode(fileWriteToTempFile, options);
            options.inSampleSize = calculateInSampleSize(options, this.maxWidth, this.maxHeight);
            options.inJustDecodeBounds = false;
            return new BitmapDrawable(this.resources, decode(fileWriteToTempFile, options));
        } finally {
            fileWriteToTempFile.delete();
        }
    }

    private static File writeToTempFile(InputStream inputStream) {
        try {
            File fileCreateTempFile = File.createTempFile("markwon", null);
            try {
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(fileCreateTempFile, false));
                byte[] bArr = new byte[8192];
                while (true) {
                    try {
                        try {
                            int i = inputStream.read(bArr);
                            if (i > 0) {
                                bufferedOutputStream.write(bArr, 0, i);
                            } else {
                                try {
                                    break;
                                } catch (IOException unused) {
                                }
                            }
                        } catch (IOException e) {
                            throw new IllegalStateException(e);
                        }
                    } catch (Throwable th) {
                        try {
                            bufferedOutputStream.close();
                        } catch (IOException unused2) {
                        }
                        throw th;
                    }
                }
                bufferedOutputStream.close();
                return fileCreateTempFile;
            } catch (FileNotFoundException e2) {
                throw new IllegalStateException(e2);
            }
        } catch (IOException e3) {
            throw new IllegalStateException(e3);
        }
    }

    private static Bitmap decode(File file, BitmapFactory.Options options) {
        InputStream file2 = readFile(file);
        try {
            return BitmapFactory.decodeStream(file2, null, options);
        } finally {
            try {
                file2.close();
            } catch (IOException unused) {
            }
        }
    }

    private static InputStream readFile(File file) {
        try {
            return new BufferedInputStream(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int i, int i2) {
        int i3 = options.outWidth;
        int i4 = options.outHeight;
        boolean z = i > 0;
        boolean z2 = i2 > 0;
        if (z && z2) {
            return Math.min(calculateInSampleSize(i3, i), calculateInSampleSize(i4, i2));
        }
        if (z) {
            return calculateInSampleSize(i3, i);
        }
        if (z2) {
            return calculateInSampleSize(i4, i2);
        }
        return 1;
    }

    private static int calculateInSampleSize(int i, int i2) {
        int i3 = 1;
        while ((i / 2) / i3 > i2) {
            i3 *= 2;
        }
        return i3;
    }

    @Override // io.noties.markwon.image.MediaDecoder
    public Collection<String> supportedTypes() {
        return Collections.emptySet();
    }
}
