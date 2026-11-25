package com.badlogic.gdx.physics.bullet.softbody;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btVertexBufferDescriptor extends BulletBase {
    private long swigCPtr;

    public static final class BufferTypes {
        public static final int CPU_BUFFER = 0;
        public static final int DX11_BUFFER = 1;
        public static final int OPENGL_BUFFER = 2;
    }

    protected btVertexBufferDescriptor(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btVertexBufferDescriptor(long j, boolean z) {
        this("btVertexBufferDescriptor", j, z);
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

    public static long getCPtr(btVertexBufferDescriptor btvertexbufferdescriptor) {
        if (btvertexbufferdescriptor == null) {
            return 0L;
        }
        return btvertexbufferdescriptor.swigCPtr;
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
                SoftbodyJNI.delete_btVertexBufferDescriptor(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public boolean hasVertexPositions() {
        return SoftbodyJNI.btVertexBufferDescriptor_hasVertexPositions(this.swigCPtr, this);
    }

    public boolean hasNormals() {
        return SoftbodyJNI.btVertexBufferDescriptor_hasNormals(this.swigCPtr, this);
    }

    public int getBufferType() {
        return SoftbodyJNI.btVertexBufferDescriptor_getBufferType(this.swigCPtr, this);
    }

    public int getVertexOffset() {
        return SoftbodyJNI.btVertexBufferDescriptor_getVertexOffset(this.swigCPtr, this);
    }

    public int getVertexStride() {
        return SoftbodyJNI.btVertexBufferDescriptor_getVertexStride(this.swigCPtr, this);
    }

    public int getNormalOffset() {
        return SoftbodyJNI.btVertexBufferDescriptor_getNormalOffset(this.swigCPtr, this);
    }

    public int getNormalStride() {
        return SoftbodyJNI.btVertexBufferDescriptor_getNormalStride(this.swigCPtr, this);
    }
}
