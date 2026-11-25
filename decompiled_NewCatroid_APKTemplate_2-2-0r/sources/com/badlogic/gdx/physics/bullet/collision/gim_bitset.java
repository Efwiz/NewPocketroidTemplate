package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class gim_bitset extends BulletBase {
    private long swigCPtr;

    protected gim_bitset(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public gim_bitset(long j, boolean z) {
        this("gim_bitset", j, z);
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

    public static long getCPtr(gim_bitset gim_bitsetVar) {
        if (gim_bitsetVar == null) {
            return 0L;
        }
        return gim_bitsetVar.swigCPtr;
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
                CollisionJNI.delete_gim_bitset(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setContainer(SWIGTYPE_p_gim_arrayT_GUINT_t sWIGTYPE_p_gim_arrayT_GUINT_t) {
        CollisionJNI.gim_bitset_container_set(this.swigCPtr, this, SWIGTYPE_p_gim_arrayT_GUINT_t.getCPtr(sWIGTYPE_p_gim_arrayT_GUINT_t));
    }

    public SWIGTYPE_p_gim_arrayT_GUINT_t getContainer() {
        long jGim_bitset_container_get = CollisionJNI.gim_bitset_container_get(this.swigCPtr, this);
        if (jGim_bitset_container_get == 0) {
            return null;
        }
        return new SWIGTYPE_p_gim_arrayT_GUINT_t(jGim_bitset_container_get, false);
    }

    public gim_bitset() {
        this(CollisionJNI.new_gim_bitset__SWIG_0(), true);
    }

    public gim_bitset(SWIGTYPE_p_GUINT sWIGTYPE_p_GUINT) {
        this(CollisionJNI.new_gim_bitset__SWIG_1(SWIGTYPE_p_GUINT.getCPtr(sWIGTYPE_p_GUINT)), true);
    }

    public boolean resize(SWIGTYPE_p_GUINT sWIGTYPE_p_GUINT) {
        return CollisionJNI.gim_bitset_resize(this.swigCPtr, this, SWIGTYPE_p_GUINT.getCPtr(sWIGTYPE_p_GUINT));
    }

    public SWIGTYPE_p_GUINT size() {
        return new SWIGTYPE_p_GUINT(CollisionJNI.gim_bitset_size(this.swigCPtr, this), true);
    }

    public void set_all() {
        CollisionJNI.gim_bitset_set_all(this.swigCPtr, this);
    }

    public void clear_all() {
        CollisionJNI.gim_bitset_clear_all(this.swigCPtr, this);
    }

    public void set(SWIGTYPE_p_GUINT sWIGTYPE_p_GUINT) {
        CollisionJNI.gim_bitset_set(this.swigCPtr, this, SWIGTYPE_p_GUINT.getCPtr(sWIGTYPE_p_GUINT));
    }

    public char get(SWIGTYPE_p_GUINT sWIGTYPE_p_GUINT) {
        return CollisionJNI.gim_bitset_get(this.swigCPtr, this, SWIGTYPE_p_GUINT.getCPtr(sWIGTYPE_p_GUINT));
    }

    public void clear(SWIGTYPE_p_GUINT sWIGTYPE_p_GUINT) {
        CollisionJNI.gim_bitset_clear(this.swigCPtr, this, SWIGTYPE_p_GUINT.getCPtr(sWIGTYPE_p_GUINT));
    }
}
