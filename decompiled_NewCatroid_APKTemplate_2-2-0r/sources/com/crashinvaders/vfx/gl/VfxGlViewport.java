package com.crashinvaders.vfx.gl;

/* JADX INFO: loaded from: classes8.dex */
public class VfxGlViewport {
    public int height;
    public int width;
    public int x;
    public int y;

    public VfxGlViewport set(int i, int i2, int i3, int i4) {
        this.x = i;
        this.y = i2;
        this.width = i3;
        this.height = i4;
        return this;
    }

    public VfxGlViewport set(VfxGlViewport vfxGlViewport) {
        this.x = vfxGlViewport.x;
        this.y = vfxGlViewport.y;
        this.width = vfxGlViewport.width;
        this.height = vfxGlViewport.height;
        return this;
    }

    public String toString() {
        return "x=" + this.x + ", y=" + this.y + ", width=" + this.width + ", height=" + this.height;
    }
}
