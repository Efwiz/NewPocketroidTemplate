package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;
import org.jetbrains.kotlin.ir.BuiltInOperatorNames;

/* JADX INFO: loaded from: classes8.dex */
public class btGimContactArray extends BulletBase {
    private long swigCPtr;

    protected btGimContactArray(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btGimContactArray(long j, boolean z) {
        this("btGimContactArray", j, z);
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

    public static long getCPtr(btGimContactArray btgimcontactarray) {
        if (btgimcontactarray == null) {
            return 0L;
        }
        return btgimcontactarray.swigCPtr;
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
                CollisionJNI.delete_btGimContactArray(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btGimContactArray operatorAssignment(btGimContactArray btgimcontactarray) {
        return new btGimContactArray(CollisionJNI.btGimContactArray_operatorAssignment(this.swigCPtr, this, getCPtr(btgimcontactarray), btgimcontactarray), false);
    }

    public btGimContactArray() {
        this(CollisionJNI.new_btGimContactArray__SWIG_0(), true);
    }

    public btGimContactArray(btGimContactArray btgimcontactarray) {
        this(CollisionJNI.new_btGimContactArray__SWIG_1(getCPtr(btgimcontactarray), btgimcontactarray), true);
    }

    public int size() {
        return CollisionJNI.btGimContactArray_size(this.swigCPtr, this);
    }

    public GIM_CONTACT atConst(int i) {
        return new GIM_CONTACT(CollisionJNI.btGimContactArray_atConst(this.swigCPtr, this, i), false);
    }

    public GIM_CONTACT at(int i) {
        return new GIM_CONTACT(CollisionJNI.btGimContactArray_at(this.swigCPtr, this, i), false);
    }

    public GIM_CONTACT operatorSubscriptConst(int i) {
        return new GIM_CONTACT(CollisionJNI.btGimContactArray_operatorSubscriptConst(this.swigCPtr, this, i), false);
    }

    public GIM_CONTACT operatorSubscript(int i) {
        return new GIM_CONTACT(CollisionJNI.btGimContactArray_operatorSubscript(this.swigCPtr, this, i), false);
    }

    public void clear() {
        CollisionJNI.btGimContactArray_clear(this.swigCPtr, this);
    }

    public void pop_back() {
        CollisionJNI.btGimContactArray_pop_back(this.swigCPtr, this);
    }

    public void resizeNoInitialize(int i) {
        CollisionJNI.btGimContactArray_resizeNoInitialize(this.swigCPtr, this, i);
    }

    public void resize(int i, GIM_CONTACT gim_contact) {
        CollisionJNI.btGimContactArray_resize__SWIG_0(this.swigCPtr, this, i, GIM_CONTACT.getCPtr(gim_contact), gim_contact);
    }

    public void resize(int i) {
        CollisionJNI.btGimContactArray_resize__SWIG_1(this.swigCPtr, this, i);
    }

    public GIM_CONTACT expandNonInitializing() {
        return new GIM_CONTACT(CollisionJNI.btGimContactArray_expandNonInitializing(this.swigCPtr, this), false);
    }

    public GIM_CONTACT expand(GIM_CONTACT gim_contact) {
        return new GIM_CONTACT(CollisionJNI.btGimContactArray_expand__SWIG_0(this.swigCPtr, this, GIM_CONTACT.getCPtr(gim_contact), gim_contact), false);
    }

    public GIM_CONTACT expand() {
        return new GIM_CONTACT(CollisionJNI.btGimContactArray_expand__SWIG_1(this.swigCPtr, this), false);
    }

    public void push_back(GIM_CONTACT gim_contact) {
        CollisionJNI.btGimContactArray_push_back(this.swigCPtr, this, GIM_CONTACT.getCPtr(gim_contact), gim_contact);
    }

    public int capacity() {
        return CollisionJNI.btGimContactArray_capacity(this.swigCPtr, this);
    }

    public void reserve(int i) {
        CollisionJNI.btGimContactArray_reserve(this.swigCPtr, this, i);
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
                    CollisionJNI.delete_btGimContactArray_less(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public less() {
            this(CollisionJNI.new_btGimContactArray_less(), true);
        }
    }

    public void swap(int i, int i2) {
        CollisionJNI.btGimContactArray_swap(this.swigCPtr, this, i, i2);
    }

    public void removeAtIndex(int i) {
        CollisionJNI.btGimContactArray_removeAtIndex(this.swigCPtr, this, i);
    }

    public void initializeFromBuffer(long j, int i, int i2) {
        CollisionJNI.btGimContactArray_initializeFromBuffer(this.swigCPtr, this, j, i, i2);
    }

    public void copyFromArray(btGimContactArray btgimcontactarray) {
        CollisionJNI.btGimContactArray_copyFromArray(this.swigCPtr, this, getCPtr(btgimcontactarray), btgimcontactarray);
    }
}
