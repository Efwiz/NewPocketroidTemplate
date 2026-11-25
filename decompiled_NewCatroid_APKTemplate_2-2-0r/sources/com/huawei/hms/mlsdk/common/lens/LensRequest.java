package com.huawei.hms.mlsdk.common.lens;

import com.huawei.hms.common.size.Size;

/* JADX INFO: loaded from: classes10.dex */
public class LensRequest {
    public Size displaySize;
    public String flashMode;
    public String focusMode;
    public int fps;
    public int lensType;

    public Size getDisplaySize() {
        return this.displaySize;
    }

    public String getFlashMode() {
        return this.flashMode;
    }

    public String getFocusMode() {
        return this.focusMode;
    }

    public int getFps() {
        return this.fps;
    }

    public int getLensType() {
        return this.lensType;
    }

    public void setDisplaySize(Size size) {
        this.displaySize = size;
    }

    public void setFlashMode(String str) {
        this.flashMode = str;
    }

    public void setFocusMode(String str) {
        this.focusMode = str;
    }

    public void setFps(int i) {
        this.fps = i;
    }

    public void setLensType(int i) {
        this.lensType = i;
    }
}
