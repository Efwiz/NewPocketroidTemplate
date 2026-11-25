package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;
import org.jetbrains.kotlin.ir.BuiltInOperatorNames;

/* JADX INFO: loaded from: classes8.dex */
public class btCollisionObjectConstArray extends BulletBase {
    private long swigCPtr;

    protected btCollisionObjectConstArray(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btCollisionObjectConstArray(long j, boolean z) {
        this("btCollisionObjectConstArray", j, z);
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

    public static long getCPtr(btCollisionObjectConstArray btcollisionobjectconstarray) {
        if (btcollisionobjectconstarray == null) {
            return 0L;
        }
        return btcollisionobjectconstarray.swigCPtr;
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
                CollisionJNI.delete_btCollisionObjectConstArray(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btCollisionObjectConstArray operatorAssignment(btCollisionObjectConstArray btcollisionobjectconstarray) {
        return new btCollisionObjectConstArray(CollisionJNI.btCollisionObjectConstArray_operatorAssignment(this.swigCPtr, this, getCPtr(btcollisionobjectconstarray), btcollisionobjectconstarray), false);
    }

    public btCollisionObjectConstArray() {
        this(CollisionJNI.new_btCollisionObjectConstArray__SWIG_0(), true);
    }

    public btCollisionObjectConstArray(btCollisionObjectConstArray btcollisionobjectconstarray) {
        this(CollisionJNI.new_btCollisionObjectConstArray__SWIG_1(getCPtr(btcollisionobjectconstarray), btcollisionobjectconstarray), true);
    }

    public int size() {
        return CollisionJNI.btCollisionObjectConstArray_size(this.swigCPtr, this);
    }

    public btCollisionObject atConst(int i) {
        return btCollisionObject.getInstance(CollisionJNI.btCollisionObjectConstArray_atConst(this.swigCPtr, this, i), false);
    }

    public SWIGTYPE_p_p_btCollisionObject at(int i) {
        return new SWIGTYPE_p_p_btCollisionObject(CollisionJNI.btCollisionObjectConstArray_at(this.swigCPtr, this, i), false);
    }

    public btCollisionObject operatorSubscriptConst(int i) {
        return btCollisionObject.getInstance(CollisionJNI.btCollisionObjectConstArray_operatorSubscriptConst(this.swigCPtr, this, i), false);
    }

    public SWIGTYPE_p_p_btCollisionObject operatorSubscript(int i) {
        return new SWIGTYPE_p_p_btCollisionObject(CollisionJNI.btCollisionObjectConstArray_operatorSubscript(this.swigCPtr, this, i), false);
    }

    public void clear() {
        CollisionJNI.btCollisionObjectConstArray_clear(this.swigCPtr, this);
    }

    public void pop_back() {
        CollisionJNI.btCollisionObjectConstArray_pop_back(this.swigCPtr, this);
    }

    public void resizeNoInitialize(int i) {
        CollisionJNI.btCollisionObjectConstArray_resizeNoInitialize(this.swigCPtr, this, i);
    }

    public void resize(int i, btCollisionObject btcollisionobject) {
        CollisionJNI.btCollisionObjectConstArray_resize__SWIG_0(this.swigCPtr, this, i, btCollisionObject.getCPtr(btcollisionobject), btcollisionobject);
    }

    public void resize(int i) {
        CollisionJNI.btCollisionObjectConstArray_resize__SWIG_1(this.swigCPtr, this, i);
    }

    public SWIGTYPE_p_p_btCollisionObject expandNonInitializing() {
        return new SWIGTYPE_p_p_btCollisionObject(CollisionJNI.btCollisionObjectConstArray_expandNonInitializing(this.swigCPtr, this), false);
    }

    public SWIGTYPE_p_p_btCollisionObject expand(btCollisionObject btcollisionobject) {
        return new SWIGTYPE_p_p_btCollisionObject(CollisionJNI.btCollisionObjectConstArray_expand__SWIG_0(this.swigCPtr, this, btCollisionObject.getCPtr(btcollisionobject), btcollisionobject), false);
    }

    public SWIGTYPE_p_p_btCollisionObject expand() {
        return new SWIGTYPE_p_p_btCollisionObject(CollisionJNI.btCollisionObjectConstArray_expand__SWIG_1(this.swigCPtr, this), false);
    }

    public void push_back(btCollisionObject btcollisionobject) {
        CollisionJNI.btCollisionObjectConstArray_push_back(this.swigCPtr, this, btCollisionObject.getCPtr(btcollisionobject), btcollisionobject);
    }

    public int capacity() {
        return CollisionJNI.btCollisionObjectConstArray_capacity(this.swigCPtr, this);
    }

    public void reserve(int i) {
        CollisionJNI.btCollisionObjectConstArray_reserve(this.swigCPtr, this, i);
    }

    public static class less extends BulletBase {
        private long swigCPtr;

        protected less(String str, long j, boolean z) {
            super(str, j, z);
            this.swigCPtr = j;
        }

        public less(long j, boolean z) {
            this(BuiltInOperatorNames.LESS, j, z);
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

        public static long getCPtr(less lessVar) {
            if (lessVar == null) {
                return 0L;
            }
            return lessVar.swigCPtr;
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
                    CollisionJNI.delete_btCollisionObjectConstArray_less(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public boolean operatorFunctionCall(btCollisionObject btcollisionobject, btCollisionObject btcollisionobject2) {
            return CollisionJNI.btCollisionObjectConstArray_less_operatorFunctionCall(this.swigCPtr, this, btCollisionObject.getCPtr(btcollisionobject), btcollisionobject, btCollisionObject.getCPtr(btcollisionobject2), btcollisionobject2);
        }

        public less() {
            this(CollisionJNI.new_btCollisionObjectConstArray_less(), true);
        }
    }

    public void swap(int i, int i2) {
        CollisionJNI.btCollisionObjectConstArray_swap(this.swigCPtr, this, i, i2);
    }

    public int findBinarySearch(btCollisionObject btcollisionobject) {
        return CollisionJNI.btCollisionObjectConstArray_findBinarySearch(this.swigCPtr, this, btCollisionObject.getCPtr(btcollisionobject), btcollisionobject);
    }

    public int findLinearSearch(btCollisionObject btcollisionobject) {
        return CollisionJNI.btCollisionObjectConstArray_findLinearSearch(this.swigCPtr, this, btCollisionObject.getCPtr(btcollisionobject), btcollisionobject);
    }

    public int findLinearSearch2(btCollisionObject btcollisionobject) {
        return CollisionJNI.btCollisionObjectConstArray_findLinearSearch2(this.swigCPtr, this, btCollisionObject.getCPtr(btcollisionobject), btcollisionobject);
    }

    public void removeAtIndex(int i) {
        CollisionJNI.btCollisionObjectConstArray_removeAtIndex(this.swigCPtr, this, i);
    }

    public void remove(btCollisionObject btcollisionobject) {
        CollisionJNI.btCollisionObjectConstArray_remove(this.swigCPtr, this, btCollisionObject.getCPtr(btcollisionobject), btcollisionobject);
    }

    public void initializeFromBuffer(long j, int i, int i2) {
        CollisionJNI.btCollisionObjectConstArray_initializeFromBuffer(this.swigCPtr, this, j, i, i2);
    }

    public void copyFromArray(btCollisionObjectConstArray btcollisionobjectconstarray) {
        CollisionJNI.btCollisionObjectConstArray_copyFromArray(this.swigCPtr, this, getCPtr(btcollisionobjectconstarray), btcollisionobjectconstarray);
    }
}
