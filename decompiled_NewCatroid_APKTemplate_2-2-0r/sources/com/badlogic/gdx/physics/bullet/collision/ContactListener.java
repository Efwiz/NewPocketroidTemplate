package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.utils.GdxRuntimeException;

/* JADX INFO: loaded from: classes8.dex */
public class ContactListener extends BulletBase {
    private long swigCPtr;

    protected ContactListener(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public ContactListener(long j, boolean z) {
        this("ContactListener", j, z);
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

    public static long getCPtr(ContactListener contactListener) {
        if (contactListener == null) {
            return 0L;
        }
        return contactListener.swigCPtr;
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
                CollisionJNI.delete_ContactListener(this.swigCPtr);
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
        CollisionJNI.ContactListener_change_ownership(this, this.swigCPtr, false);
    }

    public void swigTakeOwnership() {
        this.swigCMemOwn = true;
        CollisionJNI.ContactListener_change_ownership(this, this.swigCPtr, true);
    }

    public ContactListener() {
        this(false);
        if (!setEvents()) {
            throw new GdxRuntimeException("Only one method per callback event can be overridden.");
        }
        enable();
    }

    private ContactListener(boolean z) {
        this(CollisionJNI.new_ContactListener(z), true);
        CollisionJNI.ContactListener_director_connect(this, this.swigCPtr, this.swigCMemOwn, true);
    }

    public void enable() {
        CollisionJNI.ContactListener_enable(this.swigCPtr, this);
    }

    public void disable() {
        CollisionJNI.ContactListener_disable(this.swigCPtr, this);
    }

    public void enableOnAdded() {
        CollisionJNI.ContactListener_enableOnAdded(this.swigCPtr, this);
    }

    public void disableOnAdded() {
        CollisionJNI.ContactListener_disableOnAdded(this.swigCPtr, this);
    }

    public boolean isOnAddedEnabled() {
        return CollisionJNI.ContactListener_isOnAddedEnabled(this.swigCPtr, this);
    }

    public boolean onContactAdded(btManifoldPoint btmanifoldpoint, btCollisionObjectWrapper btcollisionobjectwrapper, int i, int i2, btCollisionObjectWrapper btcollisionobjectwrapper2, int i3, int i4) {
        return CollisionJNI.ContactListener_onContactAdded__SWIG_0(this.swigCPtr, this, btManifoldPoint.getCPtr(btmanifoldpoint), btmanifoldpoint, btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper), btcollisionobjectwrapper, i, i2, btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper2), btcollisionobjectwrapper2, i3, i4);
    }

    public boolean onContactAdded(btManifoldPoint btmanifoldpoint, btCollisionObject btcollisionobject, int i, int i2, btCollisionObject btcollisionobject2, int i3, int i4) {
        return CollisionJNI.ContactListener_onContactAdded__SWIG_1(this.swigCPtr, this, btManifoldPoint.getCPtr(btmanifoldpoint), btmanifoldpoint, btCollisionObject.getCPtr(btcollisionobject), btcollisionobject, i, i2, btCollisionObject.getCPtr(btcollisionobject2), btcollisionobject2, i3, i4);
    }

    public boolean onContactAdded(btManifoldPoint btmanifoldpoint, int i, int i2, int i3, int i4, int i5, int i6) {
        return CollisionJNI.ContactListener_onContactAdded__SWIG_2(this.swigCPtr, this, btManifoldPoint.getCPtr(btmanifoldpoint), btmanifoldpoint, i, i2, i3, i4, i5, i6);
    }

    public boolean onContactAdded(btManifoldPoint btmanifoldpoint, btCollisionObjectWrapper btcollisionobjectwrapper, int i, int i2, boolean z, btCollisionObjectWrapper btcollisionobjectwrapper2, int i3, int i4, boolean z2) {
        return CollisionJNI.ContactListener_onContactAdded__SWIG_3(this.swigCPtr, this, btManifoldPoint.getCPtr(btmanifoldpoint), btmanifoldpoint, btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper), btcollisionobjectwrapper, i, i2, z, btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper2), btcollisionobjectwrapper2, i3, i4, z2);
    }

    public boolean onContactAdded(btManifoldPoint btmanifoldpoint, btCollisionObject btcollisionobject, int i, int i2, boolean z, btCollisionObject btcollisionobject2, int i3, int i4, boolean z2) {
        return CollisionJNI.ContactListener_onContactAdded__SWIG_4(this.swigCPtr, this, btManifoldPoint.getCPtr(btmanifoldpoint), btmanifoldpoint, btCollisionObject.getCPtr(btcollisionobject), btcollisionobject, i, i2, z, btCollisionObject.getCPtr(btcollisionobject2), btcollisionobject2, i3, i4, z2);
    }

    public boolean onContactAdded(btManifoldPoint btmanifoldpoint, int i, int i2, int i3, boolean z, int i4, int i5, int i6, boolean z2) {
        return CollisionJNI.ContactListener_onContactAdded__SWIG_5(this.swigCPtr, this, btManifoldPoint.getCPtr(btmanifoldpoint), btmanifoldpoint, i, i2, i3, z, i4, i5, i6, z2);
    }

    public boolean onContactAdded(btCollisionObjectWrapper btcollisionobjectwrapper, int i, int i2, btCollisionObjectWrapper btcollisionobjectwrapper2, int i3, int i4) {
        return CollisionJNI.ContactListener_onContactAdded__SWIG_6(this.swigCPtr, this, btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper), btcollisionobjectwrapper, i, i2, btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper2), btcollisionobjectwrapper2, i3, i4);
    }

    public boolean onContactAdded(btCollisionObject btcollisionobject, int i, int i2, btCollisionObject btcollisionobject2, int i3, int i4) {
        return CollisionJNI.ContactListener_onContactAdded__SWIG_7(this.swigCPtr, this, btCollisionObject.getCPtr(btcollisionobject), btcollisionobject, i, i2, btCollisionObject.getCPtr(btcollisionobject2), btcollisionobject2, i3, i4);
    }

    public boolean onContactAdded(int i, int i2, int i3, int i4, int i5, int i6) {
        return CollisionJNI.ContactListener_onContactAdded__SWIG_8(this.swigCPtr, this, i, i2, i3, i4, i5, i6);
    }

    public boolean onContactAdded(btCollisionObjectWrapper btcollisionobjectwrapper, int i, int i2, boolean z, btCollisionObjectWrapper btcollisionobjectwrapper2, int i3, int i4, boolean z2) {
        return CollisionJNI.ContactListener_onContactAdded__SWIG_9(this.swigCPtr, this, btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper), btcollisionobjectwrapper, i, i2, z, btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper2), btcollisionobjectwrapper2, i3, i4, z2);
    }

    public boolean onContactAdded(btCollisionObject btcollisionobject, int i, int i2, boolean z, btCollisionObject btcollisionobject2, int i3, int i4, boolean z2) {
        return CollisionJNI.ContactListener_onContactAdded__SWIG_10(this.swigCPtr, this, btCollisionObject.getCPtr(btcollisionobject), btcollisionobject, i, i2, z, btCollisionObject.getCPtr(btcollisionobject2), btcollisionobject2, i3, i4, z2);
    }

    public boolean onContactAdded(int i, int i2, int i3, boolean z, int i4, int i5, int i6, boolean z2) {
        return CollisionJNI.ContactListener_onContactAdded__SWIG_11(this.swigCPtr, this, i, i2, i3, z, i4, i5, i6, z2);
    }

    public void enableOnProcessed() {
        CollisionJNI.ContactListener_enableOnProcessed(this.swigCPtr, this);
    }

    public void disableOnProcessed() {
        CollisionJNI.ContactListener_disableOnProcessed(this.swigCPtr, this);
    }

    public boolean isOnProcessedEnabled() {
        return CollisionJNI.ContactListener_isOnProcessedEnabled(this.swigCPtr, this);
    }

    public void onContactProcessed(btManifoldPoint btmanifoldpoint, btCollisionObject btcollisionobject, btCollisionObject btcollisionobject2) {
        CollisionJNI.ContactListener_onContactProcessed__SWIG_0(this.swigCPtr, this, btManifoldPoint.getCPtr(btmanifoldpoint), btmanifoldpoint, btCollisionObject.getCPtr(btcollisionobject), btcollisionobject, btCollisionObject.getCPtr(btcollisionobject2), btcollisionobject2);
    }

    public void onContactProcessed(btManifoldPoint btmanifoldpoint, int i, int i2) {
        CollisionJNI.ContactListener_onContactProcessed__SWIG_1(this.swigCPtr, this, btManifoldPoint.getCPtr(btmanifoldpoint), btmanifoldpoint, i, i2);
    }

    public void onContactProcessed(btManifoldPoint btmanifoldpoint, btCollisionObject btcollisionobject, boolean z, btCollisionObject btcollisionobject2, boolean z2) {
        CollisionJNI.ContactListener_onContactProcessed__SWIG_2(this.swigCPtr, this, btManifoldPoint.getCPtr(btmanifoldpoint), btmanifoldpoint, btCollisionObject.getCPtr(btcollisionobject), btcollisionobject, z, btCollisionObject.getCPtr(btcollisionobject2), btcollisionobject2, z2);
    }

    public void onContactProcessed(btManifoldPoint btmanifoldpoint, int i, boolean z, int i2, boolean z2) {
        CollisionJNI.ContactListener_onContactProcessed__SWIG_3(this.swigCPtr, this, btManifoldPoint.getCPtr(btmanifoldpoint), btmanifoldpoint, i, z, i2, z2);
    }

    public void onContactProcessed(btCollisionObject btcollisionobject, btCollisionObject btcollisionobject2) {
        CollisionJNI.ContactListener_onContactProcessed__SWIG_4(this.swigCPtr, this, btCollisionObject.getCPtr(btcollisionobject), btcollisionobject, btCollisionObject.getCPtr(btcollisionobject2), btcollisionobject2);
    }

    public void onContactProcessed(int i, int i2) {
        CollisionJNI.ContactListener_onContactProcessed__SWIG_5(this.swigCPtr, this, i, i2);
    }

    public void onContactProcessed(btCollisionObject btcollisionobject, boolean z, btCollisionObject btcollisionobject2, boolean z2) {
        CollisionJNI.ContactListener_onContactProcessed__SWIG_6(this.swigCPtr, this, btCollisionObject.getCPtr(btcollisionobject), btcollisionobject, z, btCollisionObject.getCPtr(btcollisionobject2), btcollisionobject2, z2);
    }

    public void onContactProcessed(int i, boolean z, int i2, boolean z2) {
        CollisionJNI.ContactListener_onContactProcessed__SWIG_7(this.swigCPtr, this, i, z, i2, z2);
    }

    public void enableOnDestroyed() {
        CollisionJNI.ContactListener_enableOnDestroyed(this.swigCPtr, this);
    }

    public void disableOnDestroyed() {
        CollisionJNI.ContactListener_disableOnDestroyed(this.swigCPtr, this);
    }

    public boolean isOnDestroyedEnabled() {
        return CollisionJNI.ContactListener_isOnDestroyedEnabled(this.swigCPtr, this);
    }

    public void onContactDestroyed(int i) {
        CollisionJNI.ContactListener_onContactDestroyed(this.swigCPtr, this, i);
    }

    public void enableOnStarted() {
        CollisionJNI.ContactListener_enableOnStarted(this.swigCPtr, this);
    }

    public void disableOnStarted() {
        CollisionJNI.ContactListener_disableOnStarted(this.swigCPtr, this);
    }

    public boolean isOnStartedEnabled() {
        return CollisionJNI.ContactListener_isOnStartedEnabled(this.swigCPtr, this);
    }

    public void onContactStarted(btPersistentManifold btpersistentmanifold) {
        CollisionJNI.ContactListener_onContactStarted__SWIG_0(this.swigCPtr, this, btPersistentManifold.getCPtr(btpersistentmanifold), btpersistentmanifold);
    }

    public void onContactStarted(btCollisionObject btcollisionobject, btCollisionObject btcollisionobject2) {
        CollisionJNI.ContactListener_onContactStarted__SWIG_1(this.swigCPtr, this, btCollisionObject.getCPtr(btcollisionobject), btcollisionobject, btCollisionObject.getCPtr(btcollisionobject2), btcollisionobject2);
    }

    public void onContactStarted(int i, int i2) {
        CollisionJNI.ContactListener_onContactStarted__SWIG_2(this.swigCPtr, this, i, i2);
    }

    public void onContactStarted(btPersistentManifold btpersistentmanifold, boolean z, boolean z2) {
        CollisionJNI.ContactListener_onContactStarted__SWIG_3(this.swigCPtr, this, btPersistentManifold.getCPtr(btpersistentmanifold), btpersistentmanifold, z, z2);
    }

    public void onContactStarted(btCollisionObject btcollisionobject, boolean z, btCollisionObject btcollisionobject2, boolean z2) {
        CollisionJNI.ContactListener_onContactStarted__SWIG_4(this.swigCPtr, this, btCollisionObject.getCPtr(btcollisionobject), btcollisionobject, z, btCollisionObject.getCPtr(btcollisionobject2), btcollisionobject2, z2);
    }

    public void onContactStarted(int i, boolean z, int i2, boolean z2) {
        CollisionJNI.ContactListener_onContactStarted__SWIG_5(this.swigCPtr, this, i, z, i2, z2);
    }

    public void enableOnEnded() {
        CollisionJNI.ContactListener_enableOnEnded(this.swigCPtr, this);
    }

    public void disableOnEnded() {
        CollisionJNI.ContactListener_disableOnEnded(this.swigCPtr, this);
    }

    public boolean isOnEndedEnabled() {
        return CollisionJNI.ContactListener_isOnEndedEnabled(this.swigCPtr, this);
    }

    public void onContactEnded(btPersistentManifold btpersistentmanifold) {
        CollisionJNI.ContactListener_onContactEnded__SWIG_0(this.swigCPtr, this, btPersistentManifold.getCPtr(btpersistentmanifold), btpersistentmanifold);
    }

    public void onContactEnded(btCollisionObject btcollisionobject, btCollisionObject btcollisionobject2) {
        CollisionJNI.ContactListener_onContactEnded__SWIG_1(this.swigCPtr, this, btCollisionObject.getCPtr(btcollisionobject), btcollisionobject, btCollisionObject.getCPtr(btcollisionobject2), btcollisionobject2);
    }

    public void onContactEnded(int i, int i2) {
        CollisionJNI.ContactListener_onContactEnded__SWIG_2(this.swigCPtr, this, i, i2);
    }

    public void onContactEnded(btPersistentManifold btpersistentmanifold, boolean z, boolean z2) {
        CollisionJNI.ContactListener_onContactEnded__SWIG_3(this.swigCPtr, this, btPersistentManifold.getCPtr(btpersistentmanifold), btpersistentmanifold, z, z2);
    }

    public void onContactEnded(btCollisionObject btcollisionobject, boolean z, btCollisionObject btcollisionobject2, boolean z2) {
        CollisionJNI.ContactListener_onContactEnded__SWIG_4(this.swigCPtr, this, btCollisionObject.getCPtr(btcollisionobject), btcollisionobject, z, btCollisionObject.getCPtr(btcollisionobject2), btcollisionobject2, z2);
    }

    public void onContactEnded(int i, boolean z, int i2, boolean z2) {
        CollisionJNI.ContactListener_onContactEnded__SWIG_5(this.swigCPtr, this, i, z, i2, z2);
    }

    private boolean setEvents() {
        return CollisionJNI.ContactListener_setEvents(this.swigCPtr, this);
    }
}
