package com.badlogic.gdx.physics.bullet.linearmath;

import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes8.dex */
public class btDefaultSerializer extends btSerializer {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private long swigCPtr;

    protected btDefaultSerializer(String str, long j, boolean z) {
        super(str, LinearMathJNI.btDefaultSerializer_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btDefaultSerializer(long j, boolean z) {
        this("btDefaultSerializer", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.linearmath.btSerializer, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(LinearMathJNI.btDefaultSerializer_SWIGUpcast(j), z);
    }

    public static long getCPtr(btDefaultSerializer btdefaultserializer) {
        if (btdefaultserializer == null) {
            return 0L;
        }
        return btdefaultserializer.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.linearmath.btSerializer, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.linearmath.btSerializer, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                LinearMathJNI.delete_btDefaultSerializer(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public SWIGTYPE_p_btHashMapT_btHashPtr_void_p_t getSkipPointers() {
        long jBtDefaultSerializer_skipPointers_get = LinearMathJNI.btDefaultSerializer_skipPointers_get(this.swigCPtr, this);
        if (jBtDefaultSerializer_skipPointers_get == 0) {
            return null;
        }
        return new SWIGTYPE_p_btHashMapT_btHashPtr_void_p_t(jBtDefaultSerializer_skipPointers_get, false);
    }

    private static long SwigConstructbtDefaultSerializer(int i, ByteBuffer byteBuffer) {
        return LinearMathJNI.new_btDefaultSerializer__SWIG_0(i, byteBuffer);
    }

    public btDefaultSerializer(int i, ByteBuffer byteBuffer) {
        this(SwigConstructbtDefaultSerializer(i, byteBuffer), true);
    }

    public btDefaultSerializer(int i) {
        this(LinearMathJNI.new_btDefaultSerializer__SWIG_1(i), true);
    }

    public btDefaultSerializer() {
        this(LinearMathJNI.new_btDefaultSerializer__SWIG_2(), true);
    }

    public static int getMemoryDnaSizeInBytes() {
        return LinearMathJNI.btDefaultSerializer_getMemoryDnaSizeInBytes();
    }

    public static String getMemoryDna() {
        return LinearMathJNI.btDefaultSerializer_getMemoryDna();
    }

    public void insertHeader() {
        LinearMathJNI.btDefaultSerializer_insertHeader(this.swigCPtr, this);
    }

    public void writeHeader(ByteBuffer byteBuffer) {
        LinearMathJNI.btDefaultSerializer_writeHeader(this.swigCPtr, this, byteBuffer);
    }

    public ByteBuffer internalAlloc(long j) {
        return LinearMathJNI.btDefaultSerializer_internalAlloc(this.swigCPtr, this, j);
    }
}
