package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btTransform;

/* JADX INFO: loaded from: classes8.dex */
public class btCompoundShapeChild extends BulletBase {
    private long swigCPtr;

    protected btCompoundShapeChild(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btCompoundShapeChild(long j, boolean z) {
        this("btCompoundShapeChild", j, z);
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

    public static long getCPtr(btCompoundShapeChild btcompoundshapechild) {
        if (btcompoundshapechild == null) {
            return 0L;
        }
        return btcompoundshapechild.swigCPtr;
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
                CollisionJNI.delete_btCompoundShapeChild(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public long operatorNew(long j) {
        return CollisionJNI.btCompoundShapeChild_operatorNew__SWIG_0(this.swigCPtr, this, j);
    }

    public void operatorDelete(long j) {
        CollisionJNI.btCompoundShapeChild_operatorDelete__SWIG_0(this.swigCPtr, this, j);
    }

    public long operatorNew(long j, long j2) {
        return CollisionJNI.btCompoundShapeChild_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void operatorDelete(long j, long j2) {
        CollisionJNI.btCompoundShapeChild_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public long operatorNewArray(long j) {
        return CollisionJNI.btCompoundShapeChild_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
    }

    public void operatorDeleteArray(long j) {
        CollisionJNI.btCompoundShapeChild_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
    }

    public long operatorNewArray(long j, long j2) {
        return CollisionJNI.btCompoundShapeChild_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void operatorDeleteArray(long j, long j2) {
        CollisionJNI.btCompoundShapeChild_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void setTransform(btTransform bttransform) {
        CollisionJNI.btCompoundShapeChild_transform_set(this.swigCPtr, this, btTransform.getCPtr(bttransform), bttransform);
    }

    public btTransform getTransform() {
        long jBtCompoundShapeChild_transform_get = CollisionJNI.btCompoundShapeChild_transform_get(this.swigCPtr, this);
        if (jBtCompoundShapeChild_transform_get == 0) {
            return null;
        }
        return new btTransform(jBtCompoundShapeChild_transform_get, false);
    }

    public void setChildShape(btCollisionShape btcollisionshape) {
        CollisionJNI.btCompoundShapeChild_childShape_set(this.swigCPtr, this, btCollisionShape.getCPtr(btcollisionshape), btcollisionshape);
    }

    public btCollisionShape getChildShape() {
        long jBtCompoundShapeChild_childShape_get = CollisionJNI.btCompoundShapeChild_childShape_get(this.swigCPtr, this);
        if (jBtCompoundShapeChild_childShape_get == 0) {
            return null;
        }
        return btCollisionShape.newDerivedObject(jBtCompoundShapeChild_childShape_get, false);
    }

    public void setChildShapeType(int i) {
        CollisionJNI.btCompoundShapeChild_childShapeType_set(this.swigCPtr, this, i);
    }

    public int getChildShapeType() {
        return CollisionJNI.btCompoundShapeChild_childShapeType_get(this.swigCPtr, this);
    }

    public void setChildMargin(float f) {
        CollisionJNI.btCompoundShapeChild_childMargin_set(this.swigCPtr, this, f);
    }

    public float getChildMargin() {
        return CollisionJNI.btCompoundShapeChild_childMargin_get(this.swigCPtr, this);
    }

    public void setNode(btDbvtNode btdbvtnode) {
        CollisionJNI.btCompoundShapeChild_node_set(this.swigCPtr, this, btDbvtNode.getCPtr(btdbvtnode), btdbvtnode);
    }

    public btDbvtNode getNode() {
        return btDbvtNode.internalTemp(CollisionJNI.btCompoundShapeChild_node_get(this.swigCPtr, this), false);
    }

    public btCompoundShapeChild() {
        this(CollisionJNI.new_btCompoundShapeChild(), true);
    }
}
