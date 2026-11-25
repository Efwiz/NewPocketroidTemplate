package org.catrobat.catroid.fast2d.components;

import com.artemis.PooledComponent;
import com.badlogic.gdx.physics.box2d.Body;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/fast2d/components/PhysicsComponent.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class PhysicsComponent extends PooledComponent {
    public Body body = null;
    public boolean isDynamic = true;
    public String shapeType = "BOX";
    public float density = 1.0f;
    public float friction = 0.5f;
    public float bounce = 0.0f;

    @Override // com.artemis.PooledComponent
    protected void reset() {
        this.body = null;
        this.isDynamic = true;
        this.shapeType = "BOX";
        this.density = 1.0f;
        this.friction = 0.5f;
        this.bounce = 0.0f;
    }
}
