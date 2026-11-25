package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btUsageBitfield extends BulletBase {
    private long swigCPtr;

    protected btUsageBitfield(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btUsageBitfield(long j, boolean z) {
        this("btUsageBitfield", j, z);
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

    public static long getCPtr(btUsageBitfield btusagebitfield) {
        if (btusagebitfield == null) {
            return 0L;
        }
        return btusagebitfield.swigCPtr;
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
                CollisionJNI.delete_btUsageBitfield(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btUsageBitfield() {
        this(CollisionJNI.new_btUsageBitfield(), true);
    }

    public void reset() {
        CollisionJNI.btUsageBitfield_reset(this.swigCPtr, this);
    }

    public void setUsedVertexA(int i) {
        CollisionJNI.btUsageBitfield_usedVertexA_set(this.swigCPtr, this, i);
    }

    public int getUsedVertexA() {
        return CollisionJNI.btUsageBitfield_usedVertexA_get(this.swigCPtr, this);
    }

    public void setUsedVertexB(int i) {
        CollisionJNI.btUsageBitfield_usedVertexB_set(this.swigCPtr, this, i);
    }

    public int getUsedVertexB() {
        return CollisionJNI.btUsageBitfield_usedVertexB_get(this.swigCPtr, this);
    }

    public void setUsedVertexC(int i) {
        CollisionJNI.btUsageBitfield_usedVertexC_set(this.swigCPtr, this, i);
    }

    public int getUsedVertexC() {
        return CollisionJNI.btUsageBitfield_usedVertexC_get(this.swigCPtr, this);
    }

    public void setUsedVertexD(int i) {
        CollisionJNI.btUsageBitfield_usedVertexD_set(this.swigCPtr, this, i);
    }

    public int getUsedVertexD() {
        return CollisionJNI.btUsageBitfield_usedVertexD_get(this.swigCPtr, this);
    }

    public void setUnused1(int i) {
        CollisionJNI.btUsageBitfield_unused1_set(this.swigCPtr, this, i);
    }

    public int getUnused1() {
        return CollisionJNI.btUsageBitfield_unused1_get(this.swigCPtr, this);
    }

    public void setUnused2(int i) {
        CollisionJNI.btUsageBitfield_unused2_set(this.swigCPtr, this, i);
    }

    public int getUnused2() {
        return CollisionJNI.btUsageBitfield_unused2_get(this.swigCPtr, this);
    }

    public void setUnused3(int i) {
        CollisionJNI.btUsageBitfield_unused3_set(this.swigCPtr, this, i);
    }

    public int getUnused3() {
        return CollisionJNI.btUsageBitfield_unused3_get(this.swigCPtr, this);
    }

    public void setUnused4(int i) {
        CollisionJNI.btUsageBitfield_unused4_set(this.swigCPtr, this, i);
    }

    public int getUnused4() {
        return CollisionJNI.btUsageBitfield_unused4_get(this.swigCPtr, this);
    }
}
