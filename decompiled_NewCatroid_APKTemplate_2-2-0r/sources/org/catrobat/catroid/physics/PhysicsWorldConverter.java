package org.catrobat.catroid.physics;

import com.badlogic.gdx.math.Vector2;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/physics/PhysicsWorldConverter.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class PhysicsWorldConverter {
    public static float convertBox2dToNormalCoordinate(float box2dCoordinate) {
        return box2dCoordinate * 10.0f;
    }

    public static float convertNormalToBox2dCoordinate(float catroidCoordinate) {
        return catroidCoordinate / 10.0f;
    }

    private PhysicsWorldConverter() {
    }

    public static float convertBox2dToNormalAngle(float box2dAngle) {
        return (float) Math.toDegrees(box2dAngle);
    }

    public static float convertNormalToBox2dAngle(float catroidAngle) {
        return (float) Math.toRadians(catroidAngle);
    }

    public static Vector2 convertBox2dToNormalVector(Vector2 box2DVector) {
        return new Vector2(convertBox2dToNormalCoordinate(box2DVector.x), convertBox2dToNormalCoordinate(box2DVector.y));
    }

    public static Vector2 convertCatroidToBox2dVector(Vector2 catroidVector) {
        return new Vector2(convertNormalToBox2dCoordinate(catroidVector.x), convertNormalToBox2dCoordinate(catroidVector.y));
    }
}
