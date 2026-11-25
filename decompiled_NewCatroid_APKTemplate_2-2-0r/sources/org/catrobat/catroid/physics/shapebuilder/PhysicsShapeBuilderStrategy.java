package org.catrobat.catroid.physics.shapebuilder;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.physics.box2d.Shape;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/physics/shapebuilder/PhysicsShapeBuilderStrategy.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public interface PhysicsShapeBuilderStrategy {
    Shape[] build(Pixmap pixmap, float scale);
}
