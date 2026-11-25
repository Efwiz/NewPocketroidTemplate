package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class ICollide extends BulletBase {
    private long swigCPtr;

    protected ICollide(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public ICollide(long j, boolean z) {
        this("ICollide", j, z);
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

    public static long getCPtr(ICollide iCollide) {
        if (iCollide == null) {
            return 0L;
        }
        return iCollide.swigCPtr;
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
                CollisionJNI.delete_ICollide(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    protected void swigDirectorDisconnect() {
        this.swigCMemOwn = false;
        delete();
    }

    public void swigReleaseOwnership() {
        this.swigCMemOwn = false;
        CollisionJNI.ICollide_change_ownership(this, this.swigCPtr, false);
    }

    public void swigTakeOwnership() {
        this.swigCMemOwn = true;
        CollisionJNI.ICollide_change_ownership(this, this.swigCPtr, true);
    }

    public void Process(btDbvtNode btdbvtnode, btDbvtNode btdbvtnode2) {
        if (getClass() == ICollide.class) {
            CollisionJNI.ICollide_Process__SWIG_0(this.swigCPtr, this, btDbvtNode.getCPtr(btdbvtnode), btdbvtnode, btDbvtNode.getCPtr(btdbvtnode2), btdbvtnode2);
        } else {
            CollisionJNI.ICollide_ProcessSwigExplicitICollide__SWIG_0(this.swigCPtr, this, btDbvtNode.getCPtr(btdbvtnode), btdbvtnode, btDbvtNode.getCPtr(btdbvtnode2), btdbvtnode2);
        }
    }

    public void Process(btDbvtNode btdbvtnode) {
        if (getClass() == ICollide.class) {
            CollisionJNI.ICollide_Process__SWIG_1(this.swigCPtr, this, btDbvtNode.getCPtr(btdbvtnode), btdbvtnode);
        } else {
            CollisionJNI.ICollide_ProcessSwigExplicitICollide__SWIG_1(this.swigCPtr, this, btDbvtNode.getCPtr(btdbvtnode), btdbvtnode);
        }
    }

    public void Process(btDbvtNode btdbvtnode, float f) {
        if (getClass() == ICollide.class) {
            CollisionJNI.ICollide_Process__SWIG_2(this.swigCPtr, this, btDbvtNode.getCPtr(btdbvtnode), btdbvtnode, f);
        } else {
            CollisionJNI.ICollide_ProcessSwigExplicitICollide__SWIG_2(this.swigCPtr, this, btDbvtNode.getCPtr(btdbvtnode), btdbvtnode, f);
        }
    }

    public boolean Descent(btDbvtNode btdbvtnode) {
        return getClass() == ICollide.class ? CollisionJNI.ICollide_Descent(this.swigCPtr, this, btDbvtNode.getCPtr(btdbvtnode), btdbvtnode) : CollisionJNI.ICollide_DescentSwigExplicitICollide(this.swigCPtr, this, btDbvtNode.getCPtr(btdbvtnode), btdbvtnode);
    }

    public boolean AllLeaves(btDbvtNode btdbvtnode) {
        return getClass() == ICollide.class ? CollisionJNI.ICollide_AllLeaves(this.swigCPtr, this, btDbvtNode.getCPtr(btdbvtnode), btdbvtnode) : CollisionJNI.ICollide_AllLeavesSwigExplicitICollide(this.swigCPtr, this, btDbvtNode.getCPtr(btdbvtnode), btdbvtnode);
    }

    public ICollide() {
        this(CollisionJNI.new_ICollide(), true);
        CollisionJNI.ICollide_director_connect(this, this.swigCPtr, this.swigCMemOwn, true);
    }
}
