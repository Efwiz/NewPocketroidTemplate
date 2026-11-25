package com.badlogic.gdx.physics.bullet.softbody;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btSoftBodySolverOutput extends BulletBase {
    private long swigCPtr;

    protected btSoftBodySolverOutput(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btSoftBodySolverOutput(long j, boolean z) {
        this("btSoftBodySolverOutput", j, z);
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

    public static long getCPtr(btSoftBodySolverOutput btsoftbodysolveroutput) {
        if (btsoftbodysolveroutput == null) {
            return 0L;
        }
        return btsoftbodysolveroutput.swigCPtr;
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
                SoftbodyJNI.delete_btSoftBodySolverOutput(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void copySoftBodyToVertexBuffer(btSoftBody btsoftbody, btVertexBufferDescriptor btvertexbufferdescriptor) {
        SoftbodyJNI.btSoftBodySolverOutput_copySoftBodyToVertexBuffer(this.swigCPtr, this, btSoftBody.getCPtr(btsoftbody), btsoftbody, btVertexBufferDescriptor.getCPtr(btvertexbufferdescriptor), btvertexbufferdescriptor);
    }
}
