package com.badlogic.gdx.physics.bullet.linearmath;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btGEN_List extends BulletBase {
    private long swigCPtr;

    protected btGEN_List(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btGEN_List(long j, boolean z) {
        this("btGEN_List", j, z);
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

    public static long getCPtr(btGEN_List btgen_list) {
        if (btgen_list == null) {
            return 0L;
        }
        return btgen_list.swigCPtr;
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
                LinearMathJNI.delete_btGEN_List(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btGEN_List() {
        this(LinearMathJNI.new_btGEN_List(), true);
    }

    public btGEN_Link getHead() {
        long jBtGEN_List_getHead = LinearMathJNI.btGEN_List_getHead(this.swigCPtr, this);
        if (jBtGEN_List_getHead == 0) {
            return null;
        }
        return new btGEN_Link(jBtGEN_List_getHead, false);
    }

    public btGEN_Link getTail() {
        long jBtGEN_List_getTail = LinearMathJNI.btGEN_List_getTail(this.swigCPtr, this);
        if (jBtGEN_List_getTail == 0) {
            return null;
        }
        return new btGEN_Link(jBtGEN_List_getTail, false);
    }

    public void addHead(btGEN_Link btgen_link) {
        LinearMathJNI.btGEN_List_addHead(this.swigCPtr, this, btGEN_Link.getCPtr(btgen_link), btgen_link);
    }

    public void addTail(btGEN_Link btgen_link) {
        LinearMathJNI.btGEN_List_addTail(this.swigCPtr, this, btGEN_Link.getCPtr(btgen_link), btgen_link);
    }
}
