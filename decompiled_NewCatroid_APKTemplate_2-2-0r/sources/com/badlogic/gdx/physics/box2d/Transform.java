package com.badlogic.gdx.physics.box2d;

import com.badlogic.gdx.math.Vector2;

/* JADX INFO: loaded from: classes8.dex */
public class Transform {
    public static final int COS = 2;
    public static final int POS_X = 0;
    public static final int POS_Y = 1;
    public static final int SIN = 3;
    private Vector2 orientation;
    private Vector2 position;
    public float[] vals;

    public Transform() {
        this.vals = new float[4];
        this.position = new Vector2();
        this.orientation = new Vector2();
    }

    public Transform(Vector2 vector2, float f) {
        this.vals = new float[4];
        this.position = new Vector2();
        this.orientation = new Vector2();
        setPosition(vector2);
        setRotation(f);
    }

    public Transform(Vector2 vector2, Vector2 vector3) {
        this.vals = new float[4];
        this.position = new Vector2();
        this.orientation = new Vector2();
        setPosition(vector2);
        setOrientation(vector3);
    }

    public Vector2 mul(Vector2 vector2) {
        float[] fArr = this.vals;
        float f = fArr[0] + (fArr[2] * vector2.x) + ((-this.vals[3]) * vector2.y);
        float[] fArr2 = this.vals;
        float f2 = fArr2[1] + (fArr2[3] * vector2.x) + (this.vals[2] * vector2.y);
        vector2.x = f;
        vector2.y = f2;
        return vector2;
    }

    public Vector2 getPosition() {
        Vector2 vector2 = this.position;
        float[] fArr = this.vals;
        return vector2.set(fArr[0], fArr[1]);
    }

    public void setRotation(float f) {
        double d = f;
        float fCos = (float) Math.cos(d);
        float fSin = (float) Math.sin(d);
        float[] fArr = this.vals;
        fArr[2] = fCos;
        fArr[3] = fSin;
    }

    public float getRotation() {
        float[] fArr = this.vals;
        return (float) Math.atan2(fArr[3], fArr[2]);
    }

    public Vector2 getOrientation() {
        Vector2 vector2 = this.orientation;
        float[] fArr = this.vals;
        return vector2.set(fArr[2], fArr[3]);
    }

    public void setOrientation(Vector2 vector2) {
        this.vals[2] = vector2.x;
        this.vals[3] = vector2.y;
    }

    public void setPosition(Vector2 vector2) {
        this.vals[0] = vector2.x;
        this.vals[1] = vector2.y;
    }
}
