package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btSerializer;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.LongMap;

/* JADX INFO: loaded from: classes8.dex */
public class btCollisionObject extends BulletBase implements Disposable {
    public static final LongMap<btCollisionObject> instances = new LongMap<>();
    protected btCollisionShape collisionShape;
    protected int contactCallbackFilter;
    protected int contactCallbackFlag;
    protected GdxCollisionObjectBridge gdxBridge;
    private long swigCPtr;
    public Object userData;
    protected int userValue;

    public static final class AnisotropicFrictionFlags {
        public static final int CF_ANISOTROPIC_FRICTION = 1;
        public static final int CF_ANISOTROPIC_FRICTION_DISABLED = 0;
        public static final int CF_ANISOTROPIC_ROLLING_FRICTION = 2;
    }

    public static final class CollisionFlags {
        public static final int CF_CHARACTER_OBJECT = 16;
        public static final int CF_CUSTOM_MATERIAL_CALLBACK = 8;
        public static final int CF_DISABLE_SPU_COLLISION_PROCESSING = 64;
        public static final int CF_DISABLE_VISUALIZE_OBJECT = 32;
        public static final int CF_HAS_COLLISION_SOUND_TRIGGER = 1024;
        public static final int CF_HAS_CONTACT_STIFFNESS_DAMPING = 128;
        public static final int CF_HAS_CUSTOM_DEBUG_RENDERING_COLOR = 256;
        public static final int CF_HAS_FRICTION_ANCHOR = 512;
        public static final int CF_KINEMATIC_OBJECT = 2;
        public static final int CF_NO_CONTACT_RESPONSE = 4;
        public static final int CF_STATIC_OBJECT = 1;
    }

    public static final class CollisionObjectTypes {
        public static final int CO_COLLISION_OBJECT = 1;
        public static final int CO_FEATHERSTONE_LINK = 64;
        public static final int CO_GHOST_OBJECT = 4;
        public static final int CO_HF_FLUID = 16;
        public static final int CO_RIGID_BODY = 2;
        public static final int CO_SOFT_BODY = 8;
        public static final int CO_USER_TYPE = 32;
    }

    protected btCollisionObject(String str, long j, boolean z) {
        super(str, j, z);
        this.contactCallbackFlag = 1;
        this.swigCPtr = j;
    }

    public btCollisionObject(long j, boolean z) {
        this("btCollisionObject", j, z);
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

    public static long getCPtr(btCollisionObject btcollisionobject) {
        if (btcollisionobject == null) {
            return 0L;
        }
        return btcollisionobject.swigCPtr;
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
                CollisionJNI.delete_btCollisionObject(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public static btCollisionObject getInstance(long j) {
        if (j == 0) {
            return null;
        }
        return instances.get(j);
    }

    public static btCollisionObject getInstance(long j, boolean z) {
        if (j == 0) {
            return null;
        }
        btCollisionObject btcollisionobject = instances.get(j);
        return btcollisionobject == null ? new btCollisionObject(j, z) : btcollisionobject;
    }

    public static void addInstance(btCollisionObject btcollisionobject) {
        instances.put(getCPtr(btcollisionobject), btcollisionobject);
    }

    public static void removeInstance(btCollisionObject btcollisionobject) {
        instances.remove(getCPtr(btcollisionobject));
    }

    @Override // com.badlogic.gdx.physics.bullet.BulletBase
    protected void construct() {
        super.construct();
        GdxCollisionObjectBridge gdxCollisionObjectBridge = new GdxCollisionObjectBridge();
        this.gdxBridge = gdxCollisionObjectBridge;
        internalSetGdxBridge(gdxCollisionObjectBridge);
        addInstance(this);
    }

    @Override // com.badlogic.gdx.physics.bullet.BulletBase, com.badlogic.gdx.utils.Disposable
    public void dispose() {
        if (this.swigCPtr != 0) {
            removeInstance(this);
        }
        GdxCollisionObjectBridge gdxCollisionObjectBridge = this.gdxBridge;
        if (gdxCollisionObjectBridge != null) {
            gdxCollisionObjectBridge.dispose();
        }
        this.gdxBridge = null;
        btCollisionShape btcollisionshape = this.collisionShape;
        if (btcollisionshape != null) {
            btcollisionshape.release();
        }
        this.collisionShape = null;
        super.dispose();
    }

    public int getUserValue() {
        return this.userValue;
    }

    public void setUserValue(int i) {
        GdxCollisionObjectBridge gdxCollisionObjectBridge = this.gdxBridge;
        this.userValue = i;
        gdxCollisionObjectBridge.setUserValue(i);
    }

    public int getContactCallbackFlag() {
        return this.contactCallbackFlag;
    }

    public void setContactCallbackFlag(int i) {
        GdxCollisionObjectBridge gdxCollisionObjectBridge = this.gdxBridge;
        this.contactCallbackFlag = i;
        gdxCollisionObjectBridge.setContactCallbackFlag(i);
    }

    public int getContactCallbackFilter() {
        return this.contactCallbackFilter;
    }

    public void setContactCallbackFilter(int i) {
        GdxCollisionObjectBridge gdxCollisionObjectBridge = this.gdxBridge;
        this.contactCallbackFilter = i;
        gdxCollisionObjectBridge.setContactCallbackFilter(i);
    }

    public void setCollisionShape(btCollisionShape btcollisionshape) {
        refCollisionShape(btcollisionshape);
        internalSetCollisionShape(btcollisionshape);
    }

    protected void refCollisionShape(btCollisionShape btcollisionshape) {
        btCollisionShape btcollisionshape2 = this.collisionShape;
        if (btcollisionshape2 == btcollisionshape) {
            return;
        }
        if (btcollisionshape2 != null) {
            btcollisionshape2.release();
        }
        this.collisionShape = btcollisionshape;
        if (btcollisionshape != null) {
            btcollisionshape.obtain();
        }
    }

    public btCollisionShape getCollisionShape() {
        return this.collisionShape;
    }

    public long operatorNew(long j) {
        return CollisionJNI.btCollisionObject_operatorNew__SWIG_0(this.swigCPtr, this, j);
    }

    public void operatorDelete(long j) {
        CollisionJNI.btCollisionObject_operatorDelete__SWIG_0(this.swigCPtr, this, j);
    }

    public long operatorNew(long j, long j2) {
        return CollisionJNI.btCollisionObject_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void operatorDelete(long j, long j2) {
        CollisionJNI.btCollisionObject_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public long operatorNewArray(long j) {
        return CollisionJNI.btCollisionObject_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
    }

    public void operatorDeleteArray(long j) {
        CollisionJNI.btCollisionObject_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
    }

    public long operatorNewArray(long j, long j2) {
        return CollisionJNI.btCollisionObject_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void operatorDeleteArray(long j, long j2) {
        CollisionJNI.btCollisionObject_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public boolean mergesSimulationIslands() {
        return CollisionJNI.btCollisionObject_mergesSimulationIslands(this.swigCPtr, this);
    }

    public Vector3 getAnisotropicFriction() {
        return CollisionJNI.btCollisionObject_getAnisotropicFriction__SWIG_0(this.swigCPtr, this);
    }

    public void setAnisotropicFriction(Vector3 vector3, int i) {
        CollisionJNI.btCollisionObject_setAnisotropicFriction__SWIG_0(this.swigCPtr, this, vector3, i);
    }

    public void setAnisotropicFriction(Vector3 vector3) {
        CollisionJNI.btCollisionObject_setAnisotropicFriction__SWIG_1(this.swigCPtr, this, vector3);
    }

    public boolean hasAnisotropicFriction(int i) {
        return CollisionJNI.btCollisionObject_hasAnisotropicFriction__SWIG_0(this.swigCPtr, this, i);
    }

    public boolean hasAnisotropicFriction() {
        return CollisionJNI.btCollisionObject_hasAnisotropicFriction__SWIG_1(this.swigCPtr, this);
    }

    public void setContactProcessingThreshold(float f) {
        CollisionJNI.btCollisionObject_setContactProcessingThreshold(this.swigCPtr, this, f);
    }

    public float getContactProcessingThreshold() {
        return CollisionJNI.btCollisionObject_getContactProcessingThreshold(this.swigCPtr, this);
    }

    public boolean isStaticObject() {
        return CollisionJNI.btCollisionObject_isStaticObject(this.swigCPtr, this);
    }

    public boolean isKinematicObject() {
        return CollisionJNI.btCollisionObject_isKinematicObject(this.swigCPtr, this);
    }

    public boolean isStaticOrKinematicObject() {
        return CollisionJNI.btCollisionObject_isStaticOrKinematicObject(this.swigCPtr, this);
    }

    public boolean hasContactResponse() {
        return CollisionJNI.btCollisionObject_hasContactResponse(this.swigCPtr, this);
    }

    public btCollisionObject() {
        this(CollisionJNI.new_btCollisionObject(), true);
    }

    private void internalSetCollisionShape(btCollisionShape btcollisionshape) {
        CollisionJNI.btCollisionObject_internalSetCollisionShape(this.swigCPtr, this, btCollisionShape.getCPtr(btcollisionshape), btcollisionshape);
    }

    private btCollisionShape internalGetCollisionShape() {
        long jBtCollisionObject_internalGetCollisionShape__SWIG_0 = CollisionJNI.btCollisionObject_internalGetCollisionShape__SWIG_0(this.swigCPtr, this);
        if (jBtCollisionObject_internalGetCollisionShape__SWIG_0 == 0) {
            return null;
        }
        return btCollisionShape.newDerivedObject(jBtCollisionObject_internalGetCollisionShape__SWIG_0, false);
    }

    public void setIgnoreCollisionCheck(btCollisionObject btcollisionobject, boolean z) {
        CollisionJNI.btCollisionObject_setIgnoreCollisionCheck(this.swigCPtr, this, getCPtr(btcollisionobject), btcollisionobject, z);
    }

    public boolean checkCollideWithOverride(btCollisionObject btcollisionobject) {
        return CollisionJNI.btCollisionObject_checkCollideWithOverride(this.swigCPtr, this, getCPtr(btcollisionobject), btcollisionobject);
    }

    public long internalGetExtensionPointer() {
        return CollisionJNI.btCollisionObject_internalGetExtensionPointer(this.swigCPtr, this);
    }

    public void internalSetExtensionPointer(long j) {
        CollisionJNI.btCollisionObject_internalSetExtensionPointer(this.swigCPtr, this, j);
    }

    public int getActivationState() {
        return CollisionJNI.btCollisionObject_getActivationState(this.swigCPtr, this);
    }

    public void setActivationState(int i) {
        CollisionJNI.btCollisionObject_setActivationState(this.swigCPtr, this, i);
    }

    public void setDeactivationTime(float f) {
        CollisionJNI.btCollisionObject_setDeactivationTime(this.swigCPtr, this, f);
    }

    public float getDeactivationTime() {
        return CollisionJNI.btCollisionObject_getDeactivationTime(this.swigCPtr, this);
    }

    public void forceActivationState(int i) {
        CollisionJNI.btCollisionObject_forceActivationState(this.swigCPtr, this, i);
    }

    public void activate(boolean z) {
        CollisionJNI.btCollisionObject_activate__SWIG_0(this.swigCPtr, this, z);
    }

    public void activate() {
        CollisionJNI.btCollisionObject_activate__SWIG_1(this.swigCPtr, this);
    }

    public boolean isActive() {
        return CollisionJNI.btCollisionObject_isActive(this.swigCPtr, this);
    }

    public void setRestitution(float f) {
        CollisionJNI.btCollisionObject_setRestitution(this.swigCPtr, this, f);
    }

    public float getRestitution() {
        return CollisionJNI.btCollisionObject_getRestitution(this.swigCPtr, this);
    }

    public void setFriction(float f) {
        CollisionJNI.btCollisionObject_setFriction(this.swigCPtr, this, f);
    }

    public float getFriction() {
        return CollisionJNI.btCollisionObject_getFriction(this.swigCPtr, this);
    }

    public void setRollingFriction(float f) {
        CollisionJNI.btCollisionObject_setRollingFriction(this.swigCPtr, this, f);
    }

    public float getRollingFriction() {
        return CollisionJNI.btCollisionObject_getRollingFriction(this.swigCPtr, this);
    }

    public void setSpinningFriction(float f) {
        CollisionJNI.btCollisionObject_setSpinningFriction(this.swigCPtr, this, f);
    }

    public float getSpinningFriction() {
        return CollisionJNI.btCollisionObject_getSpinningFriction(this.swigCPtr, this);
    }

    public void setContactStiffnessAndDamping(float f, float f2) {
        CollisionJNI.btCollisionObject_setContactStiffnessAndDamping(this.swigCPtr, this, f, f2);
    }

    public float getContactStiffness() {
        return CollisionJNI.btCollisionObject_getContactStiffness(this.swigCPtr, this);
    }

    public float getContactDamping() {
        return CollisionJNI.btCollisionObject_getContactDamping(this.swigCPtr, this);
    }

    public int getInternalType() {
        return CollisionJNI.btCollisionObject_getInternalType(this.swigCPtr, this);
    }

    public Matrix4 getWorldTransform() {
        return CollisionJNI.btCollisionObject_getWorldTransform__SWIG_0(this.swigCPtr, this);
    }

    public void setWorldTransform(Matrix4 matrix4) {
        CollisionJNI.btCollisionObject_setWorldTransform(this.swigCPtr, this, matrix4);
    }

    public btBroadphaseProxy getBroadphaseHandle() {
        return btBroadphaseProxy.internalTemp(CollisionJNI.btCollisionObject_getBroadphaseHandle__SWIG_0(this.swigCPtr, this), false);
    }

    public void setBroadphaseHandle(btBroadphaseProxy btbroadphaseproxy) {
        CollisionJNI.btCollisionObject_setBroadphaseHandle(this.swigCPtr, this, btBroadphaseProxy.getCPtr(btbroadphaseproxy), btbroadphaseproxy);
    }

    public Matrix4 getInterpolationWorldTransform() {
        return CollisionJNI.btCollisionObject_getInterpolationWorldTransform__SWIG_0(this.swigCPtr, this);
    }

    public void setInterpolationWorldTransform(Matrix4 matrix4) {
        CollisionJNI.btCollisionObject_setInterpolationWorldTransform(this.swigCPtr, this, matrix4);
    }

    public void setInterpolationLinearVelocity(Vector3 vector3) {
        CollisionJNI.btCollisionObject_setInterpolationLinearVelocity(this.swigCPtr, this, vector3);
    }

    public void setInterpolationAngularVelocity(Vector3 vector3) {
        CollisionJNI.btCollisionObject_setInterpolationAngularVelocity(this.swigCPtr, this, vector3);
    }

    public Vector3 getInterpolationLinearVelocity() {
        return CollisionJNI.btCollisionObject_getInterpolationLinearVelocity__SWIG_0(this.swigCPtr, this);
    }

    public Vector3 getInterpolationAngularVelocity() {
        return CollisionJNI.btCollisionObject_getInterpolationAngularVelocity__SWIG_0(this.swigCPtr, this);
    }

    public int getIslandTag() {
        return CollisionJNI.btCollisionObject_getIslandTag(this.swigCPtr, this);
    }

    public void setIslandTag(int i) {
        CollisionJNI.btCollisionObject_setIslandTag(this.swigCPtr, this, i);
    }

    public int getCompanionId() {
        return CollisionJNI.btCollisionObject_getCompanionId(this.swigCPtr, this);
    }

    public void setCompanionId(int i) {
        CollisionJNI.btCollisionObject_setCompanionId(this.swigCPtr, this, i);
    }

    public int getWorldArrayIndex() {
        return CollisionJNI.btCollisionObject_getWorldArrayIndex(this.swigCPtr, this);
    }

    public void setWorldArrayIndex(int i) {
        CollisionJNI.btCollisionObject_setWorldArrayIndex(this.swigCPtr, this, i);
    }

    public float getHitFraction() {
        return CollisionJNI.btCollisionObject_getHitFraction(this.swigCPtr, this);
    }

    public void setHitFraction(float f) {
        CollisionJNI.btCollisionObject_setHitFraction(this.swigCPtr, this, f);
    }

    public int getCollisionFlags() {
        return CollisionJNI.btCollisionObject_getCollisionFlags(this.swigCPtr, this);
    }

    public void setCollisionFlags(int i) {
        CollisionJNI.btCollisionObject_setCollisionFlags(this.swigCPtr, this, i);
    }

    public float getCcdSweptSphereRadius() {
        return CollisionJNI.btCollisionObject_getCcdSweptSphereRadius(this.swigCPtr, this);
    }

    public void setCcdSweptSphereRadius(float f) {
        CollisionJNI.btCollisionObject_setCcdSweptSphereRadius(this.swigCPtr, this, f);
    }

    public float getCcdMotionThreshold() {
        return CollisionJNI.btCollisionObject_getCcdMotionThreshold(this.swigCPtr, this);
    }

    public float getCcdSquareMotionThreshold() {
        return CollisionJNI.btCollisionObject_getCcdSquareMotionThreshold(this.swigCPtr, this);
    }

    public void setCcdMotionThreshold(float f) {
        CollisionJNI.btCollisionObject_setCcdMotionThreshold(this.swigCPtr, this, f);
    }

    public long getUserPointer() {
        return CollisionJNI.btCollisionObject_getUserPointer(this.swigCPtr, this);
    }

    public int getUserIndex() {
        return CollisionJNI.btCollisionObject_getUserIndex(this.swigCPtr, this);
    }

    public int getUserIndex2() {
        return CollisionJNI.btCollisionObject_getUserIndex2(this.swigCPtr, this);
    }

    public void setUserPointer(long j) {
        CollisionJNI.btCollisionObject_setUserPointer(this.swigCPtr, this, j);
    }

    public void setUserIndex(int i) {
        CollisionJNI.btCollisionObject_setUserIndex(this.swigCPtr, this, i);
    }

    public void setUserIndex2(int i) {
        CollisionJNI.btCollisionObject_setUserIndex2(this.swigCPtr, this, i);
    }

    public int getUpdateRevisionInternal() {
        return CollisionJNI.btCollisionObject_getUpdateRevisionInternal(this.swigCPtr, this);
    }

    public void setCustomDebugColor(Vector3 vector3) {
        CollisionJNI.btCollisionObject_setCustomDebugColor(this.swigCPtr, this, vector3);
    }

    public void removeCustomDebugColor() {
        CollisionJNI.btCollisionObject_removeCustomDebugColor(this.swigCPtr, this);
    }

    public boolean getCustomDebugColor(Vector3 vector3) {
        return CollisionJNI.btCollisionObject_getCustomDebugColor(this.swigCPtr, this, vector3);
    }

    public boolean checkCollideWith(btCollisionObject btcollisionobject) {
        return CollisionJNI.btCollisionObject_checkCollideWith(this.swigCPtr, this, getCPtr(btcollisionobject), btcollisionobject);
    }

    public int calculateSerializeBufferSize() {
        return CollisionJNI.btCollisionObject_calculateSerializeBufferSize(this.swigCPtr, this);
    }

    public String serialize(long j, btSerializer btserializer) {
        return CollisionJNI.btCollisionObject_serialize(this.swigCPtr, this, j, btSerializer.getCPtr(btserializer), btserializer);
    }

    public void serializeSingleObject(btSerializer btserializer) {
        CollisionJNI.btCollisionObject_serializeSingleObject(this.swigCPtr, this, btSerializer.getCPtr(btserializer), btserializer);
    }

    private void internalSetGdxBridge(GdxCollisionObjectBridge gdxCollisionObjectBridge) {
        CollisionJNI.btCollisionObject_internalSetGdxBridge(this.swigCPtr, this, GdxCollisionObjectBridge.getCPtr(gdxCollisionObjectBridge), gdxCollisionObjectBridge);
    }

    private GdxCollisionObjectBridge internalGetGdxBridge() {
        long jBtCollisionObject_internalGetGdxBridge = CollisionJNI.btCollisionObject_internalGetGdxBridge(this.swigCPtr, this);
        if (jBtCollisionObject_internalGetGdxBridge == 0) {
            return null;
        }
        return new GdxCollisionObjectBridge(jBtCollisionObject_internalGetGdxBridge, false);
    }

    public void getAnisotropicFriction(Vector3 vector3) {
        CollisionJNI.btCollisionObject_getAnisotropicFriction__SWIG_1(this.swigCPtr, this, vector3);
    }

    public void getWorldTransform(Matrix4 matrix4) {
        CollisionJNI.btCollisionObject_getWorldTransform__SWIG_2(this.swigCPtr, this, matrix4);
    }

    public void getInterpolationWorldTransform(Matrix4 matrix4) {
        CollisionJNI.btCollisionObject_getInterpolationWorldTransform__SWIG_2(this.swigCPtr, this, matrix4);
    }

    public void getInterpolationLinearVelocity(Vector3 vector3) {
        CollisionJNI.btCollisionObject_getInterpolationLinearVelocity__SWIG_1(this.swigCPtr, this, vector3);
    }

    public void getInterpolationAngularVelocity(Vector3 vector3) {
        CollisionJNI.btCollisionObject_getInterpolationAngularVelocity__SWIG_1(this.swigCPtr, this, vector3);
    }
}
