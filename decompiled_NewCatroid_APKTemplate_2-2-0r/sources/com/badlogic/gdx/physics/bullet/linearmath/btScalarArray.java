package com.badlogic.gdx.physics.bullet.linearmath;

import com.badlogic.gdx.physics.bullet.BulletBase;
import org.jetbrains.kotlin.ir.BuiltInOperatorNames;

/* JADX INFO: loaded from: classes8.dex */
public class btScalarArray extends BulletBase {
    private long swigCPtr;

    protected btScalarArray(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btScalarArray(long j, boolean z) {
        this("btScalarArray", j, z);
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

    public static long getCPtr(btScalarArray btscalararray) {
        if (btscalararray == null) {
            return 0L;
        }
        return btscalararray.swigCPtr;
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
                LinearMathJNI.delete_btScalarArray(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btScalarArray operatorAssignment(btScalarArray btscalararray) {
        return new btScalarArray(LinearMathJNI.btScalarArray_operatorAssignment(this.swigCPtr, this, getCPtr(btscalararray), btscalararray), false);
    }

    public btScalarArray() {
        this(LinearMathJNI.new_btScalarArray__SWIG_0(), true);
    }

    public btScalarArray(btScalarArray btscalararray) {
        this(LinearMathJNI.new_btScalarArray__SWIG_1(getCPtr(btscalararray), btscalararray), true);
    }

    public int size() {
        return LinearMathJNI.btScalarArray_size(this.swigCPtr, this);
    }

    public float atConst(int i) {
        return LinearMathJNI.btScalarArray_atConst(this.swigCPtr, this, i);
    }

    public SWIGTYPE_p_float at(int i) {
        return new SWIGTYPE_p_float(LinearMathJNI.btScalarArray_at(this.swigCPtr, this, i), false);
    }

    public float operatorSubscriptConst(int i) {
        return LinearMathJNI.btScalarArray_operatorSubscriptConst(this.swigCPtr, this, i);
    }

    public SWIGTYPE_p_float operatorSubscript(int i) {
        return new SWIGTYPE_p_float(LinearMathJNI.btScalarArray_operatorSubscript(this.swigCPtr, this, i), false);
    }

    public void clear() {
        LinearMathJNI.btScalarArray_clear(this.swigCPtr, this);
    }

    public void pop_back() {
        LinearMathJNI.btScalarArray_pop_back(this.swigCPtr, this);
    }

    public void resizeNoInitialize(int i) {
        LinearMathJNI.btScalarArray_resizeNoInitialize(this.swigCPtr, this, i);
    }

    public void resize(int i, float f) {
        LinearMathJNI.btScalarArray_resize__SWIG_0(this.swigCPtr, this, i, f);
    }

    public void resize(int i) {
        LinearMathJNI.btScalarArray_resize__SWIG_1(this.swigCPtr, this, i);
    }

    public SWIGTYPE_p_float expandNonInitializing() {
        return new SWIGTYPE_p_float(LinearMathJNI.btScalarArray_expandNonInitializing(this.swigCPtr, this), false);
    }

    public SWIGTYPE_p_float expand(float f) {
        return new SWIGTYPE_p_float(LinearMathJNI.btScalarArray_expand__SWIG_0(this.swigCPtr, this, f), false);
    }

    public SWIGTYPE_p_float expand() {
        return new SWIGTYPE_p_float(LinearMathJNI.btScalarArray_expand__SWIG_1(this.swigCPtr, this), false);
    }

    public void push_back(float f) {
        LinearMathJNI.btScalarArray_push_back(this.swigCPtr, this, f);
    }

    public int capacity() {
        return LinearMathJNI.btScalarArray_capacity(this.swigCPtr, this);
    }

    public void reserve(int i) {
        LinearMathJNI.btScalarArray_reserve(this.swigCPtr, this, i);
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
                    LinearMathJNI.delete_btScalarArray_less(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public boolean operatorFunctionCall(float f, float f2) {
            return LinearMathJNI.btScalarArray_less_operatorFunctionCall(this.swigCPtr, f, f2);
        }

        public less() {
            this(LinearMathJNI.new_btScalarArray_less(), true);
        }
    }

    public void swap(int i, int i2) {
        LinearMathJNI.btScalarArray_swap(this.swigCPtr, this, i, i2);
    }

    public int findBinarySearch(float f) {
        return LinearMathJNI.btScalarArray_findBinarySearch(this.swigCPtr, this, f);
    }

    public int findLinearSearch(float f) {
        return LinearMathJNI.btScalarArray_findLinearSearch(this.swigCPtr, this, f);
    }

    public int findLinearSearch2(float f) {
        return LinearMathJNI.btScalarArray_findLinearSearch2(this.swigCPtr, this, f);
    }

    public void removeAtIndex(int i) {
        LinearMathJNI.btScalarArray_removeAtIndex(this.swigCPtr, this, i);
    }

    public void remove(float f) {
        LinearMathJNI.btScalarArray_remove(this.swigCPtr, this, f);
    }

    public void initializeFromBuffer(long j, int i, int i2) {
        LinearMathJNI.btScalarArray_initializeFromBuffer(this.swigCPtr, this, j, i, i2);
    }

    public void copyFromArray(btScalarArray btscalararray) {
        LinearMathJNI.btScalarArray_copyFromArray(this.swigCPtr, this, getCPtr(btscalararray), btscalararray);
    }
}
