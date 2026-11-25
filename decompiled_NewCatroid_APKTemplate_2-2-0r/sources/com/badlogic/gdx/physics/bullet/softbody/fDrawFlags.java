package com.badlogic.gdx.physics.bullet.softbody;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class fDrawFlags extends BulletBase {
    private long swigCPtr;

    public static final class EnumFlagType {
        public static final int Anchors = 64;
        public static final int ClusterTree = 2048;
        public static final int Clusters = 256;
        public static final int Contacts = 32;
        public static final int FaceTree = 1024;
        public static final int Faces = 4;
        public static final int Joints = 4096;
        public static final int Links = 2;
        public static final int NodeTree = 512;
        public static final int Nodes = 1;
        public static final int Normals = 16;
        public static final int Notes = 128;
        public static final int Std = 4302;
        public static final int StdTetra = 4306;
        public static final int Tetras = 8;
    }

    protected fDrawFlags(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public fDrawFlags(long j, boolean z) {
        this("fDrawFlags", j, z);
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

    public static long getCPtr(fDrawFlags fdrawflags) {
        if (fdrawflags == null) {
            return 0L;
        }
        return fdrawflags.swigCPtr;
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
                SoftbodyJNI.delete_fDrawFlags(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public fDrawFlags() {
        this(SoftbodyJNI.new_fDrawFlags(), true);
    }
}
