package com.badlogic.gdx.physics.bullet.extras;

import com.badlogic.gdx.physics.bullet.BulletBase;
import org.jetbrains.kotlin.ir.BuiltInOperatorNames;

/* JADX INFO: loaded from: classes8.dex */
public class btStringArray extends BulletBase {
    private long swigCPtr;

    protected btStringArray(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btStringArray(long j, boolean z) {
        this("btStringArray", j, z);
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

    public static long getCPtr(btStringArray btstringarray) {
        if (btstringarray == null) {
            return 0L;
        }
        return btstringarray.swigCPtr;
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
                ExtrasJNI.delete_btStringArray(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btStringArray operatorAssignment(btStringArray btstringarray) {
        return new btStringArray(ExtrasJNI.btStringArray_operatorAssignment(this.swigCPtr, this, getCPtr(btstringarray), btstringarray), false);
    }

    public btStringArray() {
        this(ExtrasJNI.new_btStringArray__SWIG_0(), true);
    }

    public btStringArray(btStringArray btstringarray) {
        this(ExtrasJNI.new_btStringArray__SWIG_1(getCPtr(btstringarray), btstringarray), true);
    }

    public int size() {
        return ExtrasJNI.btStringArray_size(this.swigCPtr, this);
    }

    public String at(int i) {
        return ExtrasJNI.btStringArray_at__SWIG_0(this.swigCPtr, this, i);
    }

    public String operatorSubscript(int i) {
        return ExtrasJNI.btStringArray_operatorSubscript__SWIG_0(this.swigCPtr, this, i);
    }

    public void clear() {
        ExtrasJNI.btStringArray_clear(this.swigCPtr, this);
    }

    public void pop_back() {
        ExtrasJNI.btStringArray_pop_back(this.swigCPtr, this);
    }

    public void resizeNoInitialize(int i) {
        ExtrasJNI.btStringArray_resizeNoInitialize(this.swigCPtr, this, i);
    }

    public void resize(int i, String str) {
        ExtrasJNI.btStringArray_resize__SWIG_0(this.swigCPtr, this, i, str);
    }

    public void resize(int i) {
        ExtrasJNI.btStringArray_resize__SWIG_1(this.swigCPtr, this, i);
    }

    public String expandNonInitializing() {
        return ExtrasJNI.btStringArray_expandNonInitializing(this.swigCPtr, this);
    }

    public String expand(String str) {
        return ExtrasJNI.btStringArray_expand__SWIG_0(this.swigCPtr, this, str);
    }

    public String expand() {
        return ExtrasJNI.btStringArray_expand__SWIG_1(this.swigCPtr, this);
    }

    public void push_back(String str) {
        ExtrasJNI.btStringArray_push_back(this.swigCPtr, this, str);
    }

    public int capacity() {
        return ExtrasJNI.btStringArray_capacity(this.swigCPtr, this);
    }

    public void reserve(int i) {
        ExtrasJNI.btStringArray_reserve(this.swigCPtr, this, i);
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
                    ExtrasJNI.delete_btStringArray_less(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public boolean operatorFunctionCall(String str, String str2) {
            return ExtrasJNI.btStringArray_less_operatorFunctionCall(this.swigCPtr, this, str, str2);
        }

        public less() {
            this(ExtrasJNI.new_btStringArray_less(), true);
        }
    }

    public void swap(int i, int i2) {
        ExtrasJNI.btStringArray_swap(this.swigCPtr, this, i, i2);
    }

    public int findBinarySearch(String str) {
        return ExtrasJNI.btStringArray_findBinarySearch(this.swigCPtr, this, str);
    }

    public int findLinearSearch(String str) {
        return ExtrasJNI.btStringArray_findLinearSearch(this.swigCPtr, this, str);
    }

    public int findLinearSearch2(String str) {
        return ExtrasJNI.btStringArray_findLinearSearch2(this.swigCPtr, this, str);
    }

    public void removeAtIndex(int i) {
        ExtrasJNI.btStringArray_removeAtIndex(this.swigCPtr, this, i);
    }

    public void remove(String str) {
        ExtrasJNI.btStringArray_remove(this.swigCPtr, this, str);
    }

    public void initializeFromBuffer(long j, int i, int i2) {
        ExtrasJNI.btStringArray_initializeFromBuffer(this.swigCPtr, this, j, i, i2);
    }

    public void copyFromArray(btStringArray btstringarray) {
        ExtrasJNI.btStringArray_copyFromArray(this.swigCPtr, this, getCPtr(btstringarray), btstringarray);
    }
}
