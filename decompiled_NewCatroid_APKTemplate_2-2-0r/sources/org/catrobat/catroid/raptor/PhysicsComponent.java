package org.catrobat.catroid.raptor;

import java.util.ArrayList;
import java.util.List;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/PhysicsComponent.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class PhysicsComponent implements Component {
    public ThreeDManager.PhysicsState state = ThreeDManager.PhysicsState.NONE;

    @Deprecated
    public ThreeDManager.PhysicsShape shape = ThreeDManager.PhysicsShape.BOX;
    public float mass = 1.0f;
    public float friction = 0.5f;
    public float restitution = 0.1f;
    public List<ColliderShapeData> colliders = new ArrayList();
}
