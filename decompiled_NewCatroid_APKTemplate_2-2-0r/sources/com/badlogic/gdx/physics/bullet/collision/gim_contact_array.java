package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Vector3;

/* JADX INFO: loaded from: classes8.dex */
public class gim_contact_array extends gim_contact_array_internal {
    private long swigCPtr;

    protected gim_contact_array(String str, long j, boolean z) {
        super(str, CollisionJNI.gim_contact_array_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public gim_contact_array(long j, boolean z) {
        this("gim_contact_array", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.gim_contact_array_internal, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.gim_contact_array_SWIGUpcast(j), z);
    }

    public static long getCPtr(gim_contact_array gim_contact_arrayVar) {
        if (gim_contact_arrayVar == null) {
            return 0L;
        }
        return gim_contact_arrayVar.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.gim_contact_array_internal, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.gim_contact_array_internal, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                CollisionJNI.delete_gim_contact_array(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public gim_contact_array() {
        this(CollisionJNI.new_gim_contact_array(), true);
    }

    public void push_contact(Vector3 vector3, Vector3 vector4, SWIGTYPE_p_GREAL sWIGTYPE_p_GREAL, SWIGTYPE_p_GUINT sWIGTYPE_p_GUINT, SWIGTYPE_p_GUINT sWIGTYPE_p_GUINT2) {
        CollisionJNI.gim_contact_array_push_contact(this.swigCPtr, this, vector3, vector4, SWIGTYPE_p_GREAL.getCPtr(sWIGTYPE_p_GREAL), SWIGTYPE_p_GUINT.getCPtr(sWIGTYPE_p_GUINT), SWIGTYPE_p_GUINT.getCPtr(sWIGTYPE_p_GUINT2));
    }

    public void push_triangle_contacts(GIM_TRIANGLE_CONTACT_DATA gim_triangle_contact_data, SWIGTYPE_p_GUINT sWIGTYPE_p_GUINT, SWIGTYPE_p_GUINT sWIGTYPE_p_GUINT2) {
        CollisionJNI.gim_contact_array_push_triangle_contacts(this.swigCPtr, this, GIM_TRIANGLE_CONTACT_DATA.getCPtr(gim_triangle_contact_data), gim_triangle_contact_data, SWIGTYPE_p_GUINT.getCPtr(sWIGTYPE_p_GUINT), SWIGTYPE_p_GUINT.getCPtr(sWIGTYPE_p_GUINT2));
    }

    public void merge_contacts(gim_contact_array gim_contact_arrayVar, boolean z) {
        CollisionJNI.gim_contact_array_merge_contacts__SWIG_0(this.swigCPtr, this, getCPtr(gim_contact_arrayVar), gim_contact_arrayVar, z);
    }

    public void merge_contacts(gim_contact_array gim_contact_arrayVar) {
        CollisionJNI.gim_contact_array_merge_contacts__SWIG_1(this.swigCPtr, this, getCPtr(gim_contact_arrayVar), gim_contact_arrayVar);
    }

    public void merge_contacts_unique(gim_contact_array gim_contact_arrayVar) {
        CollisionJNI.gim_contact_array_merge_contacts_unique(this.swigCPtr, this, getCPtr(gim_contact_arrayVar), gim_contact_arrayVar);
    }
}
