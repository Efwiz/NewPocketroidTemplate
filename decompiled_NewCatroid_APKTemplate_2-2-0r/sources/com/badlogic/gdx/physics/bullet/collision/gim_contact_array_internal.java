package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class gim_contact_array_internal extends BulletBase {
    private long swigCPtr;

    protected gim_contact_array_internal(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public gim_contact_array_internal(long j, boolean z) {
        this("gim_contact_array_internal", j, z);
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

    public static long getCPtr(gim_contact_array_internal gim_contact_array_internalVar) {
        if (gim_contact_array_internalVar == null) {
            return 0L;
        }
        return gim_contact_array_internalVar.swigCPtr;
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
                CollisionJNI.delete_gim_contact_array_internal(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setData(GIM_CONTACT gim_contact) {
        CollisionJNI.gim_contact_array_internal_data_set(this.swigCPtr, this, GIM_CONTACT.getCPtr(gim_contact), gim_contact);
    }

    public GIM_CONTACT getData() {
        long jGim_contact_array_internal_data_get = CollisionJNI.gim_contact_array_internal_data_get(this.swigCPtr, this);
        if (jGim_contact_array_internal_data_get == 0) {
            return null;
        }
        return new GIM_CONTACT(jGim_contact_array_internal_data_get, false);
    }

    public void setSize(SWIGTYPE_p_GUINT sWIGTYPE_p_GUINT) {
        CollisionJNI.gim_contact_array_internal_size_set(this.swigCPtr, this, SWIGTYPE_p_GUINT.getCPtr(sWIGTYPE_p_GUINT));
    }

    public SWIGTYPE_p_GUINT getSize() {
        return new SWIGTYPE_p_GUINT(CollisionJNI.gim_contact_array_internal_size_get(this.swigCPtr, this), true);
    }

    public void setAllocated_size(SWIGTYPE_p_GUINT sWIGTYPE_p_GUINT) {
        CollisionJNI.gim_contact_array_internal_allocated_size_set(this.swigCPtr, this, SWIGTYPE_p_GUINT.getCPtr(sWIGTYPE_p_GUINT));
    }

    public SWIGTYPE_p_GUINT getAllocated_size() {
        return new SWIGTYPE_p_GUINT(CollisionJNI.gim_contact_array_internal_allocated_size_get(this.swigCPtr, this), true);
    }

    public void destroyData() {
        CollisionJNI.gim_contact_array_internal_destroyData(this.swigCPtr, this);
    }

    public boolean resizeData(SWIGTYPE_p_GUINT sWIGTYPE_p_GUINT) {
        return CollisionJNI.gim_contact_array_internal_resizeData(this.swigCPtr, this, SWIGTYPE_p_GUINT.getCPtr(sWIGTYPE_p_GUINT));
    }

    public boolean growingCheck() {
        return CollisionJNI.gim_contact_array_internal_growingCheck(this.swigCPtr, this);
    }

    public boolean reserve(SWIGTYPE_p_GUINT sWIGTYPE_p_GUINT) {
        return CollisionJNI.gim_contact_array_internal_reserve(this.swigCPtr, this, SWIGTYPE_p_GUINT.getCPtr(sWIGTYPE_p_GUINT));
    }

    public void clear_range(SWIGTYPE_p_GUINT sWIGTYPE_p_GUINT) {
        CollisionJNI.gim_contact_array_internal_clear_range(this.swigCPtr, this, SWIGTYPE_p_GUINT.getCPtr(sWIGTYPE_p_GUINT));
    }

    public void clear() {
        CollisionJNI.gim_contact_array_internal_clear(this.swigCPtr, this);
    }

    public void clear_memory() {
        CollisionJNI.gim_contact_array_internal_clear_memory(this.swigCPtr, this);
    }

    public gim_contact_array_internal() {
        this(CollisionJNI.new_gim_array__SWIG_0(), true);
    }

    public gim_contact_array_internal(SWIGTYPE_p_GUINT sWIGTYPE_p_GUINT) {
        this(CollisionJNI.new_gim_array__SWIG_1(SWIGTYPE_p_GUINT.getCPtr(sWIGTYPE_p_GUINT)), true);
    }

    public SWIGTYPE_p_GUINT sizeVal() {
        return new SWIGTYPE_p_GUINT(CollisionJNI.gim_contact_array_internal_sizeVal(this.swigCPtr, this), true);
    }

    public SWIGTYPE_p_GUINT max_size() {
        return new SWIGTYPE_p_GUINT(CollisionJNI.gim_contact_array_internal_max_size(this.swigCPtr, this), true);
    }

    public GIM_CONTACT operatorSubscript(long j) {
        return new GIM_CONTACT(CollisionJNI.gim_contact_array_internal_operatorSubscript(this.swigCPtr, this, j), false);
    }

    public GIM_CONTACT operatorSubscriptConst(long j) {
        return new GIM_CONTACT(CollisionJNI.gim_contact_array_internal_operatorSubscriptConst(this.swigCPtr, this, j), false);
    }

    public GIM_CONTACT pointer() {
        long jGim_contact_array_internal_pointer = CollisionJNI.gim_contact_array_internal_pointer(this.swigCPtr, this);
        if (jGim_contact_array_internal_pointer == 0) {
            return null;
        }
        return new GIM_CONTACT(jGim_contact_array_internal_pointer, false);
    }

    public GIM_CONTACT pointer_const() {
        long jGim_contact_array_internal_pointer_const = CollisionJNI.gim_contact_array_internal_pointer_const(this.swigCPtr, this);
        if (jGim_contact_array_internal_pointer_const == 0) {
            return null;
        }
        return new GIM_CONTACT(jGim_contact_array_internal_pointer_const, false);
    }

    public GIM_CONTACT get_pointer_at(SWIGTYPE_p_GUINT sWIGTYPE_p_GUINT) {
        long jGim_contact_array_internal_get_pointer_at = CollisionJNI.gim_contact_array_internal_get_pointer_at(this.swigCPtr, this, SWIGTYPE_p_GUINT.getCPtr(sWIGTYPE_p_GUINT));
        if (jGim_contact_array_internal_get_pointer_at == 0) {
            return null;
        }
        return new GIM_CONTACT(jGim_contact_array_internal_get_pointer_at, false);
    }

    public GIM_CONTACT get_pointer_at_const(SWIGTYPE_p_GUINT sWIGTYPE_p_GUINT) {
        long jGim_contact_array_internal_get_pointer_at_const = CollisionJNI.gim_contact_array_internal_get_pointer_at_const(this.swigCPtr, this, SWIGTYPE_p_GUINT.getCPtr(sWIGTYPE_p_GUINT));
        if (jGim_contact_array_internal_get_pointer_at_const == 0) {
            return null;
        }
        return new GIM_CONTACT(jGim_contact_array_internal_get_pointer_at_const, false);
    }

    public GIM_CONTACT at(SWIGTYPE_p_GUINT sWIGTYPE_p_GUINT) {
        return new GIM_CONTACT(CollisionJNI.gim_contact_array_internal_at(this.swigCPtr, this, SWIGTYPE_p_GUINT.getCPtr(sWIGTYPE_p_GUINT)), false);
    }

    public GIM_CONTACT at_const(SWIGTYPE_p_GUINT sWIGTYPE_p_GUINT) {
        return new GIM_CONTACT(CollisionJNI.gim_contact_array_internal_at_const(this.swigCPtr, this, SWIGTYPE_p_GUINT.getCPtr(sWIGTYPE_p_GUINT)), false);
    }

    public GIM_CONTACT front() {
        return new GIM_CONTACT(CollisionJNI.gim_contact_array_internal_front(this.swigCPtr, this), false);
    }

    public GIM_CONTACT front_const() {
        return new GIM_CONTACT(CollisionJNI.gim_contact_array_internal_front_const(this.swigCPtr, this), false);
    }

    public GIM_CONTACT back() {
        return new GIM_CONTACT(CollisionJNI.gim_contact_array_internal_back(this.swigCPtr, this), false);
    }

    public GIM_CONTACT back_const() {
        return new GIM_CONTACT(CollisionJNI.gim_contact_array_internal_back_const(this.swigCPtr, this), false);
    }

    public void swap(SWIGTYPE_p_GUINT sWIGTYPE_p_GUINT, SWIGTYPE_p_GUINT sWIGTYPE_p_GUINT2) {
        CollisionJNI.gim_contact_array_internal_swap(this.swigCPtr, this, SWIGTYPE_p_GUINT.getCPtr(sWIGTYPE_p_GUINT), SWIGTYPE_p_GUINT.getCPtr(sWIGTYPE_p_GUINT2));
    }

    public void push_back(GIM_CONTACT gim_contact) {
        CollisionJNI.gim_contact_array_internal_push_back(this.swigCPtr, this, GIM_CONTACT.getCPtr(gim_contact), gim_contact);
    }

    public void push_back_mem() {
        CollisionJNI.gim_contact_array_internal_push_back_mem(this.swigCPtr, this);
    }

    public void push_back_memcpy(GIM_CONTACT gim_contact) {
        CollisionJNI.gim_contact_array_internal_push_back_memcpy(this.swigCPtr, this, GIM_CONTACT.getCPtr(gim_contact), gim_contact);
    }

    public void pop_back() {
        CollisionJNI.gim_contact_array_internal_pop_back(this.swigCPtr, this);
    }

    public void pop_back_mem() {
        CollisionJNI.gim_contact_array_internal_pop_back_mem(this.swigCPtr, this);
    }

    public void erase(SWIGTYPE_p_GUINT sWIGTYPE_p_GUINT) {
        CollisionJNI.gim_contact_array_internal_erase(this.swigCPtr, this, SWIGTYPE_p_GUINT.getCPtr(sWIGTYPE_p_GUINT));
    }

    public void erase_sorted_mem(SWIGTYPE_p_GUINT sWIGTYPE_p_GUINT) {
        CollisionJNI.gim_contact_array_internal_erase_sorted_mem(this.swigCPtr, this, SWIGTYPE_p_GUINT.getCPtr(sWIGTYPE_p_GUINT));
    }

    public void erase_sorted(SWIGTYPE_p_GUINT sWIGTYPE_p_GUINT) {
        CollisionJNI.gim_contact_array_internal_erase_sorted(this.swigCPtr, this, SWIGTYPE_p_GUINT.getCPtr(sWIGTYPE_p_GUINT));
    }

    public void insert_mem(SWIGTYPE_p_GUINT sWIGTYPE_p_GUINT) {
        CollisionJNI.gim_contact_array_internal_insert_mem(this.swigCPtr, this, SWIGTYPE_p_GUINT.getCPtr(sWIGTYPE_p_GUINT));
    }

    public void insert(GIM_CONTACT gim_contact, SWIGTYPE_p_GUINT sWIGTYPE_p_GUINT) {
        CollisionJNI.gim_contact_array_internal_insert(this.swigCPtr, this, GIM_CONTACT.getCPtr(gim_contact), gim_contact, SWIGTYPE_p_GUINT.getCPtr(sWIGTYPE_p_GUINT));
    }

    public void resize(SWIGTYPE_p_GUINT sWIGTYPE_p_GUINT, boolean z, GIM_CONTACT gim_contact) {
        CollisionJNI.gim_contact_array_internal_resize__SWIG_0(this.swigCPtr, this, SWIGTYPE_p_GUINT.getCPtr(sWIGTYPE_p_GUINT), z, GIM_CONTACT.getCPtr(gim_contact), gim_contact);
    }

    public void resize(SWIGTYPE_p_GUINT sWIGTYPE_p_GUINT, boolean z) {
        CollisionJNI.gim_contact_array_internal_resize__SWIG_1(this.swigCPtr, this, SWIGTYPE_p_GUINT.getCPtr(sWIGTYPE_p_GUINT), z);
    }

    public void resize(SWIGTYPE_p_GUINT sWIGTYPE_p_GUINT) {
        CollisionJNI.gim_contact_array_internal_resize__SWIG_2(this.swigCPtr, this, SWIGTYPE_p_GUINT.getCPtr(sWIGTYPE_p_GUINT));
    }

    public void refit() {
        CollisionJNI.gim_contact_array_internal_refit(this.swigCPtr, this);
    }
}
