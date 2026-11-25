package com.badlogic.gdx.physics.bullet.collision;

/* JADX INFO: loaded from: classes8.dex */
public class btGImpactMeshShape extends btGImpactShapeInterface {
    private long swigCPtr;

    protected btGImpactMeshShape(String str, long j, boolean z) {
        super(str, CollisionJNI.btGImpactMeshShape_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btGImpactMeshShape(long j, boolean z) {
        this("btGImpactMeshShape", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btGImpactShapeInterface, com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btGImpactMeshShape_SWIGUpcast(j), z);
    }

    public static long getCPtr(btGImpactMeshShape btgimpactmeshshape) {
        if (btgimpactmeshshape == null) {
            return 0L;
        }
        return btgimpactmeshshape.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btGImpactShapeInterface, com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btGImpactShapeInterface, com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                CollisionJNI.delete_btGImpactMeshShape(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btGImpactMeshShape(btStridingMeshInterface btstridingmeshinterface) {
        this(CollisionJNI.new_btGImpactMeshShape(btStridingMeshInterface.getCPtr(btstridingmeshinterface), btstridingmeshinterface), true);
    }

    public btStridingMeshInterface getMeshInterface() {
        long jBtGImpactMeshShape_getMeshInterface = CollisionJNI.btGImpactMeshShape_getMeshInterface(this.swigCPtr, this);
        if (jBtGImpactMeshShape_getMeshInterface == 0) {
            return null;
        }
        return new btStridingMeshInterface(jBtGImpactMeshShape_getMeshInterface, false);
    }

    public btStridingMeshInterface getMeshInterfaceConst() {
        long jBtGImpactMeshShape_getMeshInterfaceConst = CollisionJNI.btGImpactMeshShape_getMeshInterfaceConst(this.swigCPtr, this);
        if (jBtGImpactMeshShape_getMeshInterfaceConst == 0) {
            return null;
        }
        return new btStridingMeshInterface(jBtGImpactMeshShape_getMeshInterfaceConst, false);
    }

    public int getMeshPartCount() {
        return CollisionJNI.btGImpactMeshShape_getMeshPartCount(this.swigCPtr, this);
    }

    public btGImpactMeshShapePart getMeshPart(int i) {
        long jBtGImpactMeshShape_getMeshPart = CollisionJNI.btGImpactMeshShape_getMeshPart(this.swigCPtr, this, i);
        if (jBtGImpactMeshShape_getMeshPart == 0) {
            return null;
        }
        return new btGImpactMeshShapePart(jBtGImpactMeshShape_getMeshPart, false);
    }

    public btGImpactMeshShapePart getMeshPartConst(int i) {
        long jBtGImpactMeshShape_getMeshPartConst = CollisionJNI.btGImpactMeshShape_getMeshPartConst(this.swigCPtr, this, i);
        if (jBtGImpactMeshShape_getMeshPartConst == 0) {
            return null;
        }
        return new btGImpactMeshShapePart(jBtGImpactMeshShape_getMeshPartConst, false);
    }
}
