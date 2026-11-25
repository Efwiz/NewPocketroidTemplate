package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class ContactCache extends BulletBase {
    private long swigCPtr;

    protected ContactCache(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public ContactCache(long j, boolean z) {
        this("ContactCache", j, z);
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

    public static long getCPtr(ContactCache contactCache) {
        if (contactCache == null) {
            return 0L;
        }
        return contactCache.swigCPtr;
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
                CollisionJNI.delete_ContactCache(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    protected void swigDirectorDisconnect() {
        this.swigCMemOwn = false;
        delete();
    }

    public void swigReleaseOwnership() {
        this.swigCMemOwn = false;
        CollisionJNI.ContactCache_change_ownership(this, this.swigCPtr, false);
    }

    public void swigTakeOwnership() {
        this.swigCMemOwn = true;
        CollisionJNI.ContactCache_change_ownership(this, this.swigCPtr, true);
    }

    public ContactCache() {
        this(false);
        enable();
    }

    public void setCacheTime(float f) {
        CollisionJNI.ContactCache_cacheTime_set(this.swigCPtr, this, f);
    }

    public float getCacheTime() {
        return CollisionJNI.ContactCache_cacheTime_get(this.swigCPtr, this);
    }

    private ContactCache(boolean z) {
        this(CollisionJNI.new_ContactCache(z), true);
        CollisionJNI.ContactCache_director_connect(this, this.swigCPtr, this.swigCMemOwn, true);
    }

    public void enable() {
        CollisionJNI.ContactCache_enable(this.swigCPtr, this);
    }

    public void disable() {
        CollisionJNI.ContactCache_disable(this.swigCPtr, this);
    }

    public boolean isEnabled() {
        return CollisionJNI.ContactCache_isEnabled(this.swigCPtr, this);
    }

    public void onContactStarted(btPersistentManifold btpersistentmanifold, boolean z, boolean z2) {
        CollisionJNI.ContactCache_onContactStarted(this.swigCPtr, this, btPersistentManifold.getCPtr(btpersistentmanifold), btpersistentmanifold, z, z2);
    }

    public void onContactEnded(btCollisionObject btcollisionobject, boolean z, btCollisionObject btcollisionobject2, boolean z2) {
        CollisionJNI.ContactCache_onContactEnded(this.swigCPtr, this, btCollisionObject.getCPtr(btcollisionobject), btcollisionobject, z, btCollisionObject.getCPtr(btcollisionobject2), btcollisionobject2, z2);
    }

    public void clear() {
        CollisionJNI.ContactCache_clear(this.swigCPtr, this);
    }

    public void update(float f) {
        CollisionJNI.ContactCache_update(this.swigCPtr, this, f);
    }
}
