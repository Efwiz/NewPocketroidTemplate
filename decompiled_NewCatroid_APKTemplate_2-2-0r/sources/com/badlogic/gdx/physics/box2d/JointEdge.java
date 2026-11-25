package com.badlogic.gdx.physics.box2d;

/* JADX INFO: loaded from: classes8.dex */
public class JointEdge {
    public final Joint joint;
    public final Body other;

    protected JointEdge(Body body, Joint joint) {
        this.other = body;
        this.joint = joint;
    }
}
