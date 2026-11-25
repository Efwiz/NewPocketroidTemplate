package org.catrobat.catroid.fast2d.systems;

import com.artemis.Aspect;
import com.artemis.Component;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import org.catrobat.catroid.fast2d.components.MovementComponent;
import org.catrobat.catroid.fast2d.components.TransformComponent;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/fast2d/systems/Fast2DMovementSystem.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class Fast2DMovementSystem extends IteratingSystem {
    protected ComponentMapper<MovementComponent> mMovement;
    protected ComponentMapper<TransformComponent> mTransform;

    public Fast2DMovementSystem() {
        super(Aspect.all((Class<? extends Component>[]) new Class[]{TransformComponent.class, MovementComponent.class}));
    }

    @Override // com.artemis.systems.IteratingSystem
    protected void process(int entityId) {
        TransformComponent transformComponent = (TransformComponent) this.mTransform.get(entityId);
        MovementComponent movementComponent = (MovementComponent) this.mMovement.get(entityId);
        float delta = this.world.getDelta();
        movementComponent.velocityY += movementComponent.gravityY * delta;
        transformComponent.x += movementComponent.velocityX * delta;
        transformComponent.y += movementComponent.velocityY * delta;
        transformComponent.rotation += movementComponent.angularVelocity * delta;
    }
}
