package com.badlogic.gdx.utils;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/DataBuffer.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class DataBuffer extends DataOutput {
    private final StreamUtils.OptimizedByteArrayOutputStream outStream;

    public DataBuffer() {
        this(32);
    }

    public DataBuffer(int i) {
        super(new StreamUtils.OptimizedByteArrayOutputStream(i));
        this.outStream = (StreamUtils.OptimizedByteArrayOutputStream) this.out;
    }

    public byte[] getBuffer() {
        return this.outStream.getBuffer();
    }

    public byte[] toArray() {
        return this.outStream.toByteArray();
    }
}
