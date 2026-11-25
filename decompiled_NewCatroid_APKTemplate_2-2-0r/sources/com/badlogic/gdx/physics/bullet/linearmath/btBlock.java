package com.badlogic.gdx.physics.bullet.linearmath;

import com.badlogic.gdx.physics.bullet.BulletBase;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes8.dex */
public class btBlock extends BulletBase {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private long swigCPtr;

    protected btBlock(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btBlock(long j, boolean z) {
        this("btBlock", j, z);
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

    public static long getCPtr(btBlock btblock) {
        if (btblock == null) {
            return 0L;
        }
        return btblock.swigCPtr;
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
                LinearMathJNI.delete_btBlock(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setPrevious(btBlock btblock) {
        LinearMathJNI.btBlock_previous_set(this.swigCPtr, this, getCPtr(btblock), btblock);
    }

    public btBlock getPrevious() {
        long jBtBlock_previous_get = LinearMathJNI.btBlock_previous_get(this.swigCPtr, this);
        if (jBtBlock_previous_get == 0) {
            return null;
        }
        return new btBlock(jBtBlock_previous_get, false);
    }

    public void setAddress(ByteBuffer byteBuffer) {
        LinearMathJNI.btBlock_address_set(this.swigCPtr, this, byteBuffer);
    }

    public ByteBuffer getAddress() {
        return LinearMathJNI.btBlock_address_get(this.swigCPtr, this);
    }

    public btBlock() {
        this(LinearMathJNI.new_btBlock(), true);
    }
}
