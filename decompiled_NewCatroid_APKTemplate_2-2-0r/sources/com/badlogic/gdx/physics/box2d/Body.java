package com.badlogic.gdx.physics.box2d;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/* JADX INFO: loaded from: classes8.dex */
public class Body {
    protected long addr;
    private Object userData;
    private final World world;
    private final float[] tmp = new float[4];
    private Array<Fixture> fixtures = new Array<>(2);
    protected Array<JointEdge> joints = new Array<>(2);
    private final Transform transform = new Transform();
    private final Vector2 position = new Vector2();
    private final Vector2 worldCenter = new Vector2();
    private final Vector2 localCenter = new Vector2();
    private final Vector2 linearVelocity = new Vector2();
    private final MassData massData = new MassData();
    private final Vector2 localPoint = new Vector2();
    private final Vector2 worldVector = new Vector2();
    public final Vector2 localPoint2 = new Vector2();
    public final Vector2 localVector = new Vector2();
    public final Vector2 linVelWorld = new Vector2();
    public final Vector2 linVelLoc = new Vector2();

    private native void jniApplyAngularImpulse(long j, float f, boolean z);

    private native void jniApplyForce(long j, float f, float f2, float f3, float f4, boolean z);

    private native void jniApplyForceToCenter(long j, float f, float f2, boolean z);

    private native void jniApplyLinearImpulse(long j, float f, float f2, float f3, float f4, boolean z);

    private native void jniApplyTorque(long j, float f, boolean z);

    private native long jniCreateFixture(long j, long j2, float f);

    private native long jniCreateFixture(long j, long j2, float f, float f2, float f3, boolean z, short s, short s2, short s3);

    private native float jniGetAngle(long j);

    private native float jniGetAngularDamping(long j);

    private native float jniGetAngularVelocity(long j);

    private native float jniGetGravityScale(long j);

    private native float jniGetInertia(long j);

    private native float jniGetLinearDamping(long j);

    private native void jniGetLinearVelocity(long j, float[] fArr);

    private native void jniGetLinearVelocityFromLocalPoint(long j, float f, float f2, float[] fArr);

    private native void jniGetLinearVelocityFromWorldPoint(long j, float f, float f2, float[] fArr);

    private native void jniGetLocalCenter(long j, float[] fArr);

    private native void jniGetLocalPoint(long j, float f, float f2, float[] fArr);

    private native void jniGetLocalVector(long j, float f, float f2, float[] fArr);

    private native float jniGetMass(long j);

    private native void jniGetMassData(long j, float[] fArr);

    private native void jniGetPosition(long j, float[] fArr);

    private native void jniGetTransform(long j, float[] fArr);

    private native int jniGetType(long j);

    private native void jniGetWorldCenter(long j, float[] fArr);

    private native void jniGetWorldPoint(long j, float f, float f2, float[] fArr);

    private native void jniGetWorldVector(long j, float f, float f2, float[] fArr);

    private native boolean jniIsActive(long j);

    private native boolean jniIsAwake(long j);

    private native boolean jniIsBullet(long j);

    private native boolean jniIsFixedRotation(long j);

    private native boolean jniIsSleepingAllowed(long j);

    private native void jniResetMassData(long j);

    private native void jniSetActive(long j, boolean z);

    private native void jniSetAngularDamping(long j, float f);

    private native void jniSetAngularVelocity(long j, float f);

    private native void jniSetAwake(long j, boolean z);

    private native void jniSetBullet(long j, boolean z);

    private native void jniSetFixedRotation(long j, boolean z);

    private native void jniSetGravityScale(long j, float f);

    private native void jniSetLinearDamping(long j, float f);

    private native void jniSetLinearVelocity(long j, float f, float f2);

    private native void jniSetMassData(long j, float f, float f2, float f3, float f4);

    private native void jniSetSleepingAllowed(long j, boolean z);

    private native void jniSetTransform(long j, float f, float f2, float f3);

    private native void jniSetType(long j, int i);

    protected Body(World world, long j) {
        this.world = world;
        this.addr = j;
    }

    protected void reset(long j) {
        this.addr = j;
        this.userData = null;
        for (int i = 0; i < this.fixtures.size; i++) {
            this.world.freeFixtures.free(this.fixtures.get(i));
        }
        this.fixtures.clear();
        this.joints.clear();
    }

    public Fixture createFixture(FixtureDef fixtureDef) {
        long jJniCreateFixture = jniCreateFixture(this.addr, fixtureDef.shape.addr, fixtureDef.friction, fixtureDef.restitution, fixtureDef.density, fixtureDef.isSensor, fixtureDef.filter.categoryBits, fixtureDef.filter.maskBits, fixtureDef.filter.groupIndex);
        Fixture fixtureObtain = this.world.freeFixtures.obtain();
        fixtureObtain.reset(this, jJniCreateFixture);
        this.world.fixtures.put(fixtureObtain.addr, fixtureObtain);
        this.fixtures.add(fixtureObtain);
        return fixtureObtain;
    }

    public Fixture createFixture(Shape shape, float f) {
        long jJniCreateFixture = jniCreateFixture(this.addr, shape.addr, f);
        Fixture fixtureObtain = this.world.freeFixtures.obtain();
        fixtureObtain.reset(this, jJniCreateFixture);
        this.world.fixtures.put(fixtureObtain.addr, fixtureObtain);
        this.fixtures.add(fixtureObtain);
        return fixtureObtain;
    }

    public void destroyFixture(Fixture fixture) {
        this.world.destroyFixture(this, fixture);
        fixture.setUserData(null);
        this.world.fixtures.remove(fixture.addr);
        this.fixtures.removeValue(fixture, true);
        this.world.freeFixtures.free(fixture);
    }

    public void setTransform(Vector2 vector2, float f) {
        jniSetTransform(this.addr, vector2.x, vector2.y, f);
    }

    public void setTransform(float f, float f2, float f3) {
        jniSetTransform(this.addr, f, f2, f3);
    }

    public Transform getTransform() {
        jniGetTransform(this.addr, this.transform.vals);
        return this.transform;
    }

    public Vector2 getPosition() {
        jniGetPosition(this.addr, this.tmp);
        this.position.x = this.tmp[0];
        this.position.y = this.tmp[1];
        return this.position;
    }

    public float getAngle() {
        return jniGetAngle(this.addr);
    }

    public Vector2 getWorldCenter() {
        jniGetWorldCenter(this.addr, this.tmp);
        this.worldCenter.x = this.tmp[0];
        this.worldCenter.y = this.tmp[1];
        return this.worldCenter;
    }

    public Vector2 getLocalCenter() {
        jniGetLocalCenter(this.addr, this.tmp);
        this.localCenter.x = this.tmp[0];
        this.localCenter.y = this.tmp[1];
        return this.localCenter;
    }

    public void setLinearVelocity(Vector2 vector2) {
        jniSetLinearVelocity(this.addr, vector2.x, vector2.y);
    }

    public void setLinearVelocity(float f, float f2) {
        jniSetLinearVelocity(this.addr, f, f2);
    }

    public Vector2 getLinearVelocity() {
        jniGetLinearVelocity(this.addr, this.tmp);
        this.linearVelocity.x = this.tmp[0];
        this.linearVelocity.y = this.tmp[1];
        return this.linearVelocity;
    }

    public void setAngularVelocity(float f) {
        jniSetAngularVelocity(this.addr, f);
    }

    public float getAngularVelocity() {
        return jniGetAngularVelocity(this.addr);
    }

    public void applyForce(Vector2 vector2, Vector2 vector3, boolean z) {
        jniApplyForce(this.addr, vector2.x, vector2.y, vector3.x, vector3.y, z);
    }

    public void applyForce(float f, float f2, float f3, float f4, boolean z) {
        jniApplyForce(this.addr, f, f2, f3, f4, z);
    }

    public void applyForceToCenter(Vector2 vector2, boolean z) {
        jniApplyForceToCenter(this.addr, vector2.x, vector2.y, z);
    }

    public void applyForceToCenter(float f, float f2, boolean z) {
        jniApplyForceToCenter(this.addr, f, f2, z);
    }

    public void applyTorque(float f, boolean z) {
        jniApplyTorque(this.addr, f, z);
    }

    public void applyLinearImpulse(Vector2 vector2, Vector2 vector3, boolean z) {
        jniApplyLinearImpulse(this.addr, vector2.x, vector2.y, vector3.x, vector3.y, z);
    }

    public void applyLinearImpulse(float f, float f2, float f3, float f4, boolean z) {
        jniApplyLinearImpulse(this.addr, f, f2, f3, f4, z);
    }

    public void applyAngularImpulse(float f, boolean z) {
        jniApplyAngularImpulse(this.addr, f, z);
    }

    public float getMass() {
        return jniGetMass(this.addr);
    }

    public float getInertia() {
        return jniGetInertia(this.addr);
    }

    public MassData getMassData() {
        jniGetMassData(this.addr, this.tmp);
        this.massData.mass = this.tmp[0];
        this.massData.center.x = this.tmp[1];
        this.massData.center.y = this.tmp[2];
        this.massData.I = this.tmp[3];
        return this.massData;
    }

    public void setMassData(MassData massData) {
        jniSetMassData(this.addr, massData.mass, massData.center.x, massData.center.y, massData.I);
    }

    public void resetMassData() {
        jniResetMassData(this.addr);
    }

    public Vector2 getWorldPoint(Vector2 vector2) {
        jniGetWorldPoint(this.addr, vector2.x, vector2.y, this.tmp);
        this.localPoint.x = this.tmp[0];
        this.localPoint.y = this.tmp[1];
        return this.localPoint;
    }

    public Vector2 getWorldVector(Vector2 vector2) {
        jniGetWorldVector(this.addr, vector2.x, vector2.y, this.tmp);
        this.worldVector.x = this.tmp[0];
        this.worldVector.y = this.tmp[1];
        return this.worldVector;
    }

    public Vector2 getLocalPoint(Vector2 vector2) {
        jniGetLocalPoint(this.addr, vector2.x, vector2.y, this.tmp);
        this.localPoint2.x = this.tmp[0];
        this.localPoint2.y = this.tmp[1];
        return this.localPoint2;
    }

    public Vector2 getLocalVector(Vector2 vector2) {
        jniGetLocalVector(this.addr, vector2.x, vector2.y, this.tmp);
        this.localVector.x = this.tmp[0];
        this.localVector.y = this.tmp[1];
        return this.localVector;
    }

    public Vector2 getLinearVelocityFromWorldPoint(Vector2 vector2) {
        jniGetLinearVelocityFromWorldPoint(this.addr, vector2.x, vector2.y, this.tmp);
        this.linVelWorld.x = this.tmp[0];
        this.linVelWorld.y = this.tmp[1];
        return this.linVelWorld;
    }

    public Vector2 getLinearVelocityFromLocalPoint(Vector2 vector2) {
        jniGetLinearVelocityFromLocalPoint(this.addr, vector2.x, vector2.y, this.tmp);
        this.linVelLoc.x = this.tmp[0];
        this.linVelLoc.y = this.tmp[1];
        return this.linVelLoc;
    }

    public float getLinearDamping() {
        return jniGetLinearDamping(this.addr);
    }

    public void setLinearDamping(float f) {
        jniSetLinearDamping(this.addr, f);
    }

    public float getAngularDamping() {
        return jniGetAngularDamping(this.addr);
    }

    public void setAngularDamping(float f) {
        jniSetAngularDamping(this.addr, f);
    }

    public void setType(BodyDef.BodyType bodyType) {
        jniSetType(this.addr, bodyType.getValue());
    }

    public BodyDef.BodyType getType() {
        int iJniGetType = jniGetType(this.addr);
        if (iJniGetType == 0) {
            return BodyDef.BodyType.StaticBody;
        }
        if (iJniGetType == 1) {
            return BodyDef.BodyType.KinematicBody;
        }
        if (iJniGetType == 2) {
            return BodyDef.BodyType.DynamicBody;
        }
        return BodyDef.BodyType.StaticBody;
    }

    public void setBullet(boolean z) {
        jniSetBullet(this.addr, z);
    }

    public boolean isBullet() {
        return jniIsBullet(this.addr);
    }

    public void setSleepingAllowed(boolean z) {
        jniSetSleepingAllowed(this.addr, z);
    }

    public boolean isSleepingAllowed() {
        return jniIsSleepingAllowed(this.addr);
    }

    public void setAwake(boolean z) {
        jniSetAwake(this.addr, z);
    }

    public boolean isAwake() {
        return jniIsAwake(this.addr);
    }

    public void setActive(boolean z) {
        if (z) {
            jniSetActive(this.addr, z);
        } else {
            this.world.deactivateBody(this);
        }
    }

    public boolean isActive() {
        return jniIsActive(this.addr);
    }

    public void setFixedRotation(boolean z) {
        jniSetFixedRotation(this.addr, z);
    }

    public boolean isFixedRotation() {
        return jniIsFixedRotation(this.addr);
    }

    public Array<Fixture> getFixtureList() {
        return this.fixtures;
    }

    public Array<JointEdge> getJointList() {
        return this.joints;
    }

    public float getGravityScale() {
        return jniGetGravityScale(this.addr);
    }

    public void setGravityScale(float f) {
        jniSetGravityScale(this.addr, f);
    }

    public World getWorld() {
        return this.world;
    }

    public Object getUserData() {
        return this.userData;
    }

    public void setUserData(Object obj) {
        this.userData = obj;
    }
}
