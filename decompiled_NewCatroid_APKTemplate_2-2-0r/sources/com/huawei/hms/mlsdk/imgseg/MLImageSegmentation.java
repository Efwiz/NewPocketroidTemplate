package com.huawei.hms.mlsdk.imgseg;

import android.graphics.Bitmap;

/* JADX INFO: loaded from: classes10.dex */
public class MLImageSegmentation {
    public static MLImageSegmentation instance;
    public Bitmap foreground;
    public Bitmap grayscale;
    public byte[] masks;
    public Bitmap original;

    public MLImageSegmentation(byte[] bArr, Bitmap bitmap, Bitmap bitmap2) {
        this(bArr, bitmap, bitmap2, null);
    }

    public static MLImageSegmentation getInstance() {
        MLImageSegmentation mLImageSegmentation = instance;
        return mLImageSegmentation == null ? new MLImageSegmentation(null, null, null) : mLImageSegmentation;
    }

    public Bitmap getForeground() {
        return this.foreground;
    }

    public Bitmap getGrayscale() {
        return this.grayscale;
    }

    public byte[] getMasks() {
        byte[] bArr = this.masks;
        if (bArr == null) {
            return null;
        }
        return (byte[]) bArr.clone();
    }

    public Bitmap getOriginal() {
        return this.original;
    }

    public MLImageSegmentation(byte[] bArr, Bitmap bitmap, Bitmap bitmap2, Bitmap bitmap3) {
        this.masks = bArr;
        this.foreground = bitmap;
        this.grayscale = bitmap2;
        this.original = bitmap3;
    }
}
