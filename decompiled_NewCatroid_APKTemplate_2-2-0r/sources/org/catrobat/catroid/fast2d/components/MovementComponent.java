package org.catrobat.catroid.fast2d.components;

import com.artemis.PooledComponent;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/fast2d/components/MovementComponent.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class MovementComponent extends PooledComponent {
    public float velocityX = 0.0f;
    public float velocityY = 0.0f;
    public float angularVelocity = 0.0f;
    public float gravityY = 0.0f;

    @Override // com.artemis.PooledComponent
    protected void reset() {
        this.velocityX = 0.0f;
        this.velocityY = 0.0f;
        this.angularVelocity = 0.0f;
        this.gravityY = 0.0f;
    }
}
