package org.catrobat.catroid.fast2d.components;

import com.artemis.PooledComponent;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/fast2d/components/TransformComponent.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class TransformComponent extends PooledComponent {
    public float x = 0.0f;
    public float y = 0.0f;
    public float z = 0.0f;
    public float scaleX = 1.0f;
    public float scaleY = 1.0f;
    public float rotation = 0.0f;

    @Override // com.artemis.PooledComponent
    protected void reset() {
        this.x = 0.0f;
        this.y = 0.0f;
        this.z = 0.0f;
        this.scaleX = 1.0f;
        this.scaleY = 1.0f;
        this.rotation = 0.0f;
    }
}
