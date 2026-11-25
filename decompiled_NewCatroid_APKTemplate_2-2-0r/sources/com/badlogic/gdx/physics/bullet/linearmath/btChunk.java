package com.badlogic.gdx.physics.bullet.linearmath;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btChunk extends BulletBase {
    private long swigCPtr;

    protected btChunk(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btChunk(long j, boolean z) {
        this("btChunk", j, z);
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

    public static long getCPtr(btChunk btchunk) {
        if (btchunk == null) {
            return 0L;
        }
        return btchunk.swigCPtr;
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
                LinearMathJNI.delete_btChunk(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setChunkCode(int i) {
        LinearMathJNI.btChunk_chunkCode_set(this.swigCPtr, this, i);
    }

    public int getChunkCode() {
        return LinearMathJNI.btChunk_chunkCode_get(this.swigCPtr, this);
    }

    public void setLength(int i) {
        LinearMathJNI.btChunk_length_set(this.swigCPtr, this, i);
    }

    public int getLength() {
        return LinearMathJNI.btChunk_length_get(this.swigCPtr, this);
    }

    public void setOldPtr(long j) {
        LinearMathJNI.btChunk_oldPtr_set(this.swigCPtr, this, j);
    }

    public long getOldPtr() {
        return LinearMathJNI.btChunk_oldPtr_get(this.swigCPtr, this);
    }

    public void setDna_nr(int i) {
        LinearMathJNI.btChunk_dna_nr_set(this.swigCPtr, this, i);
    }

    public int getDna_nr() {
        return LinearMathJNI.btChunk_dna_nr_get(this.swigCPtr, this);
    }

    public void setNumber(int i) {
        LinearMathJNI.btChunk_number_set(this.swigCPtr, this, i);
    }

    public int getNumber() {
        return LinearMathJNI.btChunk_number_get(this.swigCPtr, this);
    }

    public btChunk() {
        this(LinearMathJNI.new_btChunk(), true);
    }
}
