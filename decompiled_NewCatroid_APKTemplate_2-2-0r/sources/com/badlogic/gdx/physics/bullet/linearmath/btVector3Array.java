package com.badlogic.gdx.physics.bullet.linearmath;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.BulletBase;
import org.jetbrains.kotlin.ir.BuiltInOperatorNames;

/* JADX INFO: loaded from: classes8.dex */
public class btVector3Array extends BulletBase {
    private long swigCPtr;

    protected btVector3Array(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btVector3Array(long j, boolean z) {
        this("btVector3Array", j, z);
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

    public static long getCPtr(btVector3Array btvector3array) {
        if (btvector3array == null) {
            return 0L;
        }
        return btvector3array.swigCPtr;
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
                LinearMathJNI.delete_btVector3Array(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btVector3Array operatorAssignment(btVector3Array btvector3array) {
        return new btVector3Array(LinearMathJNI.btVector3Array_operatorAssignment(this.swigCPtr, this, getCPtr(btvector3array), btvector3array), false);
    }

    public btVector3Array() {
        this(LinearMathJNI.new_btVector3Array__SWIG_0(), true);
    }

    public btVector3Array(btVector3Array btvector3array) {
        this(LinearMathJNI.new_btVector3Array__SWIG_1(getCPtr(btvector3array), btvector3array), true);
    }

    public int size() {
        return LinearMathJNI.btVector3Array_size(this.swigCPtr, this);
    }

    public Vector3 atConst(int i) {
        return LinearMathJNI.btVector3Array_atConst(this.swigCPtr, this, i);
    }

    public Vector3 at(int i) {
        return LinearMathJNI.btVector3Array_at(this.swigCPtr, this, i);
    }

    public Vector3 operatorSubscriptConst(int i) {
        return LinearMathJNI.btVector3Array_operatorSubscriptConst(this.swigCPtr, this, i);
    }

    public Vector3 operatorSubscript(int i) {
        return LinearMathJNI.btVector3Array_operatorSubscript(this.swigCPtr, this, i);
    }

    public void clear() {
        LinearMathJNI.btVector3Array_clear(this.swigCPtr, this);
    }

    public void pop_back() {
        LinearMathJNI.btVector3Array_pop_back(this.swigCPtr, this);
    }

    public void resizeNoInitialize(int i) {
        LinearMathJNI.btVector3Array_resizeNoInitialize(this.swigCPtr, this, i);
    }

    public void resize(int i, Vector3 vector3) {
        LinearMathJNI.btVector3Array_resize__SWIG_0(this.swigCPtr, this, i, vector3);
    }

    public void resize(int i) {
        LinearMathJNI.btVector3Array_resize__SWIG_1(this.swigCPtr, this, i);
    }

    public Vector3 expandNonInitializing() {
        return LinearMathJNI.btVector3Array_expandNonInitializing(this.swigCPtr, this);
    }

    public Vector3 expand(Vector3 vector3) {
        return LinearMathJNI.btVector3Array_expand__SWIG_0(this.swigCPtr, this, vector3);
    }

    public Vector3 expand() {
        return LinearMathJNI.btVector3Array_expand__SWIG_1(this.swigCPtr, this);
    }

    public void push_back(Vector3 vector3) {
        LinearMathJNI.btVector3Array_push_back(this.swigCPtr, this, vector3);
    }

    public int capacity() {
        return LinearMathJNI.btVector3Array_capacity(this.swigCPtr, this);
    }

    public void reserve(int i) {
        LinearMathJNI.btVector3Array_reserve(this.swigCPtr, this, i);
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
                    LinearMathJNI.delete_btVector3Array_less(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public boolean operatorFunctionCall(Vector3 vector3, Vector3 vector4) {
            return LinearMathJNI.btVector3Array_less_operatorFunctionCall(this.swigCPtr, this, vector3, vector4);
        }

        public less() {
            this(LinearMathJNI.new_btVector3Array_less(), true);
        }
    }

    public void swap(int i, int i2) {
        LinearMathJNI.btVector3Array_swap(this.swigCPtr, this, i, i2);
    }

    public int findBinarySearch(Vector3 vector3) {
        return LinearMathJNI.btVector3Array_findBinarySearch(this.swigCPtr, this, vector3);
    }

    public int findLinearSearch(Vector3 vector3) {
        return LinearMathJNI.btVector3Array_findLinearSearch(this.swigCPtr, this, vector3);
    }

    public int findLinearSearch2(Vector3 vector3) {
        return LinearMathJNI.btVector3Array_findLinearSearch2(this.swigCPtr, this, vector3);
    }

    public void removeAtIndex(int i) {
        LinearMathJNI.btVector3Array_removeAtIndex(this.swigCPtr, this, i);
    }

    public void remove(Vector3 vector3) {
        LinearMathJNI.btVector3Array_remove(this.swigCPtr, this, vector3);
    }

    public void initializeFromBuffer(long j, int i, int i2) {
        LinearMathJNI.btVector3Array_initializeFromBuffer(this.swigCPtr, this, j, i, i2);
    }

    public void copyFromArray(btVector3Array btvector3array) {
        LinearMathJNI.btVector3Array_copyFromArray(this.swigCPtr, this, getCPtr(btvector3array), btvector3array);
    }
}
