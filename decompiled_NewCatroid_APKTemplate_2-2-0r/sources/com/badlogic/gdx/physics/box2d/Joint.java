package com.badlogic.gdx.physics.box2d;

import com.badlogic.gdx.math.Vector2;

/* JADX INFO: loaded from: classes8.dex */
public abstract class Joint {
    protected long addr;
    protected JointEdge jointEdgeA;
    protected JointEdge jointEdgeB;
    private Object userData;
    private final World world;
    private final float[] tmp = new float[2];
    private final Vector2 anchorA = new Vector2();
    private final Vector2 anchorB = new Vector2();
    private final Vector2 reactionForce = new Vector2();

    private native void jniGetAnchorA(long j, float[] fArr);

    private native void jniGetAnchorB(long j, float[] fArr);

    private native long jniGetBodyA(long j);

    private native long jniGetBodyB(long j);

    private native boolean jniGetCollideConnected(long j);

    private native void jniGetReactionForce(long j, float f, float[] fArr);

    private native float jniGetReactionTorque(long j, float f);

    private native int jniGetType(long j);

    private native boolean jniIsActive(long j);

    protected Joint(World world, long j) {
        this.world = world;
        this.addr = j;
    }

    public JointDef.JointType getType() {
        int iJniGetType = jniGetType(this.addr);
        if (iJniGetType > 0 && iJniGetType < JointDef.JointType.valueTypes.length) {
            return JointDef.JointType.valueTypes[iJniGetType];
        }
        return JointDef.JointType.Unknown;
    }

    public Body getBodyA() {
        return this.world.bodies.get(jniGetBodyA(this.addr));
    }

    public Body getBodyB() {
        return this.world.bodies.get(jniGetBodyB(this.addr));
    }

    public Vector2 getAnchorA() {
        jniGetAnchorA(this.addr, this.tmp);
        this.anchorA.x = this.tmp[0];
        this.anchorA.y = this.tmp[1];
        return this.anchorA;
    }

    public Vector2 getAnchorB() {
        jniGetAnchorB(this.addr, this.tmp);
        this.anchorB.x = this.tmp[0];
        this.anchorB.y = this.tmp[1];
        return this.anchorB;
    }

    public boolean getCollideConnected() {
        return jniGetCollideConnected(this.addr);
    }

    public Vector2 getReactionForce(float f) {
        jniGetReactionForce(this.addr, f, this.tmp);
        this.reactionForce.x = this.tmp[0];
        this.reactionForce.y = this.tmp[1];
        return this.reactionForce;
    }

    public float getReactionTorque(float f) {
        return jniGetReactionTorque(this.addr, f);
    }

    public Object getUserData() {
        return this.userData;
    }

    public void setUserData(Object obj) {
        this.userData = obj;
    }

    public boolean isActive() {
        return jniIsActive(this.addr);
    }
}
