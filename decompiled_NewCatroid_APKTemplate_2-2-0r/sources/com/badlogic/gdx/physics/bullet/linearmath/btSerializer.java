package com.badlogic.gdx.physics.bullet.linearmath;

import com.badlogic.gdx.physics.bullet.BulletBase;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes8.dex */
public class btSerializer extends BulletBase {
    private long swigCPtr;

    protected btSerializer(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btSerializer(long j, boolean z) {
        this("btSerializer", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(j, z);
    }

    public static long getCPtr(btSerializer btserializer) {
        if (btserializer == null) {
            return 0L;
        }
        return btserializer.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                LinearMathJNI.delete_btSerializer(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public ByteBuffer getBufferPointer() {
        return LinearMathJNI.btSerializer_getBufferPointer(this.swigCPtr, this);
    }

    public int getCurrentBufferSize() {
        return LinearMathJNI.btSerializer_getCurrentBufferSize(this.swigCPtr, this);
    }

    public btChunk allocate(long j, int i) {
        long jBtSerializer_allocate = LinearMathJNI.btSerializer_allocate(this.swigCPtr, this, j, i);
        if (jBtSerializer_allocate == 0) {
            return null;
        }
        return new btChunk(jBtSerializer_allocate, false);
    }

    public void finalizeChunk(btChunk btchunk, String str, int i, long j) {
        LinearMathJNI.btSerializer_finalizeChunk(this.swigCPtr, this, btChunk.getCPtr(btchunk), btchunk, str, i, j);
    }

    public long findPointer(long j) {
        return LinearMathJNI.btSerializer_findPointer(this.swigCPtr, this, j);
    }

    public long getUniquePointer(long j) {
        return LinearMathJNI.btSerializer_getUniquePointer(this.swigCPtr, this, j);
    }

    public void startSerialization() {
        LinearMathJNI.btSerializer_startSerialization(this.swigCPtr, this);
    }

    public void finishSerialization() {
        LinearMathJNI.btSerializer_finishSerialization(this.swigCPtr, this);
    }

    public String findNameForPointer(long j) {
        return LinearMathJNI.btSerializer_findNameForPointer(this.swigCPtr, this, j);
    }

    public void registerNameForPointer(long j, String str) {
        LinearMathJNI.btSerializer_registerNameForPointer(this.swigCPtr, this, j, str);
    }

    public void serializeName(String str) {
        LinearMathJNI.btSerializer_serializeName(this.swigCPtr, this, str);
    }

    public int getSerializationFlags() {
        return LinearMathJNI.btSerializer_getSerializationFlags(this.swigCPtr, this);
    }

    public void setSerializationFlags(int i) {
        LinearMathJNI.btSerializer_setSerializationFlags(this.swigCPtr, this, i);
    }

    public int getNumChunks() {
        return LinearMathJNI.btSerializer_getNumChunks(this.swigCPtr, this);
    }

    public btChunk getChunk(int i) {
        long jBtSerializer_getChunk = LinearMathJNI.btSerializer_getChunk(this.swigCPtr, this, i);
        if (jBtSerializer_getChunk == 0) {
            return null;
        }
        return new btChunk(jBtSerializer_getChunk, false);
    }
}
