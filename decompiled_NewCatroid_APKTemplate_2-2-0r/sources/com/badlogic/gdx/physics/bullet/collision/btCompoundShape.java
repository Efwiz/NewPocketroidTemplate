package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import java.nio.FloatBuffer;

/* JADX INFO: loaded from: classes8.dex */
public class btCompoundShape extends btCollisionShape {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    protected Array<btCollisionShape> children;
    private long swigCPtr;

    protected btCompoundShape(String str, long j, boolean z) {
        super(str, CollisionJNI.btCompoundShape_SWIGUpcast(j), z);
        this.children = new Array<>();
        this.swigCPtr = j;
    }

    public btCompoundShape(long j, boolean z) {
        this("btCompoundShape", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btCompoundShape_SWIGUpcast(j), z);
    }

    public static long getCPtr(btCompoundShape btcompoundshape) {
        if (btcompoundshape == null) {
            return 0L;
        }
        return btcompoundshape.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                CollisionJNI.delete_btCompoundShape(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void addChildShape(Matrix4 matrix4, btCollisionShape btcollisionshape) {
        internalAddChildShape(matrix4, btcollisionshape);
        this.children.add(btcollisionshape);
        btcollisionshape.obtain();
    }

    public void removeChildShape(btCollisionShape btcollisionshape) {
        internalRemoveChildShape(btcollisionshape);
        int iIndexOf = this.children.indexOf(btcollisionshape, false);
        if (iIndexOf >= 0) {
            this.children.removeIndex(iIndexOf).release();
        }
    }

    public void removeChildShapeByIndex(int i) {
        internalRemoveChildShapeByIndex(i);
        this.children.removeIndex(i).release();
    }

    public btCollisionShape getChildShape(int i) {
        return this.children.get(i);
    }

    @Override // com.badlogic.gdx.physics.bullet.BulletBase, com.badlogic.gdx.utils.Disposable
    public void dispose() {
        Array.ArrayIterator<btCollisionShape> it = this.children.iterator();
        while (it.hasNext()) {
            it.next().release();
        }
        this.children.clear();
        super.dispose();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNew(long j) {
        return CollisionJNI.btCompoundShape_operatorNew__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDelete(long j) {
        CollisionJNI.btCompoundShape_operatorDelete__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNew(long j, long j2) {
        return CollisionJNI.btCompoundShape_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDelete(long j, long j2) {
        CollisionJNI.btCompoundShape_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNewArray(long j) {
        return CollisionJNI.btCompoundShape_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDeleteArray(long j) {
        CollisionJNI.btCompoundShape_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNewArray(long j, long j2) {
        return CollisionJNI.btCompoundShape_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDeleteArray(long j, long j2) {
        CollisionJNI.btCompoundShape_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public btCompoundShape(boolean z, int i) {
        this(CollisionJNI.new_btCompoundShape__SWIG_0(z, i), true);
    }

    public btCompoundShape(boolean z) {
        this(CollisionJNI.new_btCompoundShape__SWIG_1(z), true);
    }

    public btCompoundShape() {
        this(CollisionJNI.new_btCompoundShape__SWIG_2(), true);
    }

    private void internalAddChildShape(Matrix4 matrix4, btCollisionShape btcollisionshape) {
        CollisionJNI.btCompoundShape_internalAddChildShape(this.swigCPtr, this, matrix4, btCollisionShape.getCPtr(btcollisionshape), btcollisionshape);
    }

    private void internalRemoveChildShape(btCollisionShape btcollisionshape) {
        CollisionJNI.btCompoundShape_internalRemoveChildShape(this.swigCPtr, this, btCollisionShape.getCPtr(btcollisionshape), btcollisionshape);
    }

    private void internalRemoveChildShapeByIndex(int i) {
        CollisionJNI.btCompoundShape_internalRemoveChildShapeByIndex(this.swigCPtr, this, i);
    }

    public int getNumChildShapes() {
        return CollisionJNI.btCompoundShape_getNumChildShapes(this.swigCPtr, this);
    }

    public Matrix4 getChildTransform(int i) {
        return CollisionJNI.btCompoundShape_getChildTransform(this.swigCPtr, this, i);
    }

    public Matrix4 getChildTransformConst(int i) {
        return CollisionJNI.btCompoundShape_getChildTransformConst(this.swigCPtr, this, i);
    }

    public void updateChildTransform(int i, Matrix4 matrix4, boolean z) {
        CollisionJNI.btCompoundShape_updateChildTransform__SWIG_0(this.swigCPtr, this, i, matrix4, z);
    }

    public void updateChildTransform(int i, Matrix4 matrix4) {
        CollisionJNI.btCompoundShape_updateChildTransform__SWIG_1(this.swigCPtr, this, i, matrix4);
    }

    public btCompoundShapeChild getChildList() {
        long jBtCompoundShape_getChildList = CollisionJNI.btCompoundShape_getChildList(this.swigCPtr, this);
        if (jBtCompoundShape_getChildList == 0) {
            return null;
        }
        return new btCompoundShapeChild(jBtCompoundShape_getChildList, false);
    }

    public void recalculateLocalAabb() {
        CollisionJNI.btCompoundShape_recalculateLocalAabb(this.swigCPtr, this);
    }

    public btDbvt getDynamicAabbTreeConst() {
        long jBtCompoundShape_getDynamicAabbTreeConst = CollisionJNI.btCompoundShape_getDynamicAabbTreeConst(this.swigCPtr, this);
        if (jBtCompoundShape_getDynamicAabbTreeConst == 0) {
            return null;
        }
        return new btDbvt(jBtCompoundShape_getDynamicAabbTreeConst, false);
    }

    public btDbvt getDynamicAabbTree() {
        long jBtCompoundShape_getDynamicAabbTree = CollisionJNI.btCompoundShape_getDynamicAabbTree(this.swigCPtr, this);
        if (jBtCompoundShape_getDynamicAabbTree == 0) {
            return null;
        }
        return new btDbvt(jBtCompoundShape_getDynamicAabbTree, false);
    }

    public void createAabbTreeFromChildren() {
        CollisionJNI.btCompoundShape_createAabbTreeFromChildren(this.swigCPtr, this);
    }

    public void calculatePrincipalAxisTransform(FloatBuffer floatBuffer, Matrix4 matrix4, Vector3 vector3) {
        CollisionJNI.btCompoundShape_calculatePrincipalAxisTransform(this.swigCPtr, this, floatBuffer, matrix4, vector3);
    }

    public int getUpdateRevision() {
        return CollisionJNI.btCompoundShape_getUpdateRevision(this.swigCPtr, this);
    }
}
