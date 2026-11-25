package com.badlogic.gdx.physics.bullet.linearmath;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btGEN_Link extends BulletBase {
    private long swigCPtr;

    protected btGEN_Link(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btGEN_Link(long j, boolean z) {
        this("btGEN_Link", j, z);
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

    public static long getCPtr(btGEN_Link btgen_link) {
        if (btgen_link == null) {
            return 0L;
        }
        return btgen_link.swigCPtr;
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
                LinearMathJNI.delete_btGEN_Link(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btGEN_Link() {
        this(LinearMathJNI.new_btGEN_Link__SWIG_0(), true);
    }

    public btGEN_Link(btGEN_Link btgen_link, btGEN_Link btgen_link2) {
        this(LinearMathJNI.new_btGEN_Link__SWIG_1(getCPtr(btgen_link), btgen_link, getCPtr(btgen_link2), btgen_link2), true);
    }

    public btGEN_Link getNext() {
        long jBtGEN_Link_getNext = LinearMathJNI.btGEN_Link_getNext(this.swigCPtr, this);
        if (jBtGEN_Link_getNext == 0) {
            return null;
        }
        return new btGEN_Link(jBtGEN_Link_getNext, false);
    }

    public btGEN_Link getPrev() {
        long jBtGEN_Link_getPrev = LinearMathJNI.btGEN_Link_getPrev(this.swigCPtr, this);
        if (jBtGEN_Link_getPrev == 0) {
            return null;
        }
        return new btGEN_Link(jBtGEN_Link_getPrev, false);
    }

    public boolean isHead() {
        return LinearMathJNI.btGEN_Link_isHead(this.swigCPtr, this);
    }

    public boolean isTail() {
        return LinearMathJNI.btGEN_Link_isTail(this.swigCPtr, this);
    }

    public void insertBefore(btGEN_Link btgen_link) {
        LinearMathJNI.btGEN_Link_insertBefore(this.swigCPtr, this, getCPtr(btgen_link), btgen_link);
    }

    public void insertAfter(btGEN_Link btgen_link) {
        LinearMathJNI.btGEN_Link_insertAfter(this.swigCPtr, this, getCPtr(btgen_link), btgen_link);
    }

    public void remove() {
        LinearMathJNI.btGEN_Link_remove(this.swigCPtr, this);
    }
}
