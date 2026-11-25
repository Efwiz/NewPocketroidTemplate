package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Vector3;

/* JADX INFO: loaded from: classes8.dex */
public class btContactArray extends btGimContactArray {
    private long swigCPtr;

    protected btContactArray(String str, long j, boolean z) {
        super(str, CollisionJNI.btContactArray_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btContactArray(long j, boolean z) {
        this("btContactArray", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btGimContactArray, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btContactArray_SWIGUpcast(j), z);
    }

    public static long getCPtr(btContactArray btcontactarray) {
        if (btcontactarray == null) {
            return 0L;
        }
        return btcontactarray.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btGimContactArray, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btGimContactArray, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                CollisionJNI.delete_btContactArray(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btContactArray() {
        this(CollisionJNI.new_btContactArray(), true);
    }

    public void push_contact(Vector3 vector3, Vector3 vector4, float f, int i, int i2) {
        CollisionJNI.btContactArray_push_contact(this.swigCPtr, this, vector3, vector4, f, i, i2);
    }

    public void push_triangle_contacts(GIM_TRIANGLE_CONTACT gim_triangle_contact, int i, int i2) {
        CollisionJNI.btContactArray_push_triangle_contacts(this.swigCPtr, this, GIM_TRIANGLE_CONTACT.getCPtr(gim_triangle_contact), gim_triangle_contact, i, i2);
    }

    public void merge_contacts(btContactArray btcontactarray, boolean z) {
        CollisionJNI.btContactArray_merge_contacts__SWIG_0(this.swigCPtr, this, getCPtr(btcontactarray), btcontactarray, z);
    }

    public void merge_contacts(btContactArray btcontactarray) {
        CollisionJNI.btContactArray_merge_contacts__SWIG_1(this.swigCPtr, this, getCPtr(btcontactarray), btcontactarray);
    }

    public void merge_contacts_unique(btContactArray btcontactarray) {
        CollisionJNI.btContactArray_merge_contacts_unique(this.swigCPtr, this, getCPtr(btcontactarray), btcontactarray);
    }
}
