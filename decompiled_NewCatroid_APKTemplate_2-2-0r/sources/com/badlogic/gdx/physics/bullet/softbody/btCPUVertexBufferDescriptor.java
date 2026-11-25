package com.badlogic.gdx.physics.bullet.softbody;

import java.nio.FloatBuffer;

/* JADX INFO: loaded from: classes8.dex */
public class btCPUVertexBufferDescriptor extends btVertexBufferDescriptor {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private long swigCPtr;

    protected btCPUVertexBufferDescriptor(String str, long j, boolean z) {
        super(str, SoftbodyJNI.btCPUVertexBufferDescriptor_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btCPUVertexBufferDescriptor(long j, boolean z) {
        this("btCPUVertexBufferDescriptor", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.softbody.btVertexBufferDescriptor, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(SoftbodyJNI.btCPUVertexBufferDescriptor_SWIGUpcast(j), z);
    }

    public static long getCPtr(btCPUVertexBufferDescriptor btcpuvertexbufferdescriptor) {
        if (btcpuvertexbufferdescriptor == null) {
            return 0L;
        }
        return btcpuvertexbufferdescriptor.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.softbody.btVertexBufferDescriptor, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.softbody.btVertexBufferDescriptor, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                SoftbodyJNI.delete_btCPUVertexBufferDescriptor(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    private static long SwigConstructbtCPUVertexBufferDescriptor(FloatBuffer floatBuffer, int i, int i2) {
        return SoftbodyJNI.new_btCPUVertexBufferDescriptor__SWIG_0(floatBuffer, i, i2);
    }

    public btCPUVertexBufferDescriptor(FloatBuffer floatBuffer, int i, int i2) {
        this(SwigConstructbtCPUVertexBufferDescriptor(floatBuffer, i, i2), true);
    }

    private static long SwigConstructbtCPUVertexBufferDescriptor(FloatBuffer floatBuffer, int i, int i2, int i3, int i4) {
        return SoftbodyJNI.new_btCPUVertexBufferDescriptor__SWIG_1(floatBuffer, i, i2, i3, i4);
    }

    public btCPUVertexBufferDescriptor(FloatBuffer floatBuffer, int i, int i2, int i3, int i4) {
        this(SwigConstructbtCPUVertexBufferDescriptor(floatBuffer, i, i2, i3, i4), true);
    }

    public FloatBuffer getBasePointer() {
        return SoftbodyJNI.btCPUVertexBufferDescriptor_getBasePointer(this.swigCPtr, this);
    }
}
