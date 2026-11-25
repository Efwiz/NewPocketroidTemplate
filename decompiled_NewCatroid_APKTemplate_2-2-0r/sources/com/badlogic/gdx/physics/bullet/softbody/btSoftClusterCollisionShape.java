package com.badlogic.gdx.physics.bullet.softbody;

import com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape;

/* JADX INFO: loaded from: classes8.dex */
public class btSoftClusterCollisionShape extends btConvexInternalShape {
    private long swigCPtr;

    protected btSoftClusterCollisionShape(String str, long j, boolean z) {
        super(str, SoftbodyJNI.btSoftClusterCollisionShape_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btSoftClusterCollisionShape(long j, boolean z) {
        this("btSoftClusterCollisionShape", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(SoftbodyJNI.btSoftClusterCollisionShape_SWIGUpcast(j), z);
    }

    public static long getCPtr(btSoftClusterCollisionShape btsoftclustercollisionshape) {
        if (btsoftclustercollisionshape == null) {
            return 0L;
        }
        return btsoftclustercollisionshape.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                SoftbodyJNI.delete_btSoftClusterCollisionShape(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setCluster(btSoftBody.Cluster cluster) {
        SoftbodyJNI.btSoftClusterCollisionShape_cluster_set(this.swigCPtr, this, btSoftBody.Cluster.getCPtr(cluster), cluster);
    }

    public btSoftBody.Cluster getCluster() {
        long jBtSoftClusterCollisionShape_cluster_get = SoftbodyJNI.btSoftClusterCollisionShape_cluster_get(this.swigCPtr, this);
        if (jBtSoftClusterCollisionShape_cluster_get == 0) {
            return null;
        }
        return new btSoftBody.Cluster(jBtSoftClusterCollisionShape_cluster_get, false);
    }

    public btSoftClusterCollisionShape(btSoftBody.Cluster cluster) {
        this(SoftbodyJNI.new_btSoftClusterCollisionShape(btSoftBody.Cluster.getCPtr(cluster), cluster), true);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public int getShapeType() {
        return SoftbodyJNI.btSoftClusterCollisionShape_getShapeType(this.swigCPtr, this);
    }
}
