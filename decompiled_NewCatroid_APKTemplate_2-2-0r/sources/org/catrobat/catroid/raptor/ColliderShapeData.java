package org.catrobat.catroid.raptor;

import com.badlogic.gdx.math.Vector3;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/ColliderShapeData.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ColliderShapeData {
    public ShapeType type = ShapeType.BOX;
    public Vector3 centerOffset = new Vector3();
    public Vector3 size = new Vector3(1.0f, 1.0f, 1.0f);
    public float radius = 0.5f;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/ColliderShapeData$ShapeType.class
      classes14.dex
     */
    public enum ShapeType {
        BOX,
        SPHERE,
        CAPSULE
    }
}
