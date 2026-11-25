package org.catrobat.catroid.fast2d.systems;

import com.artemis.Aspect;
import com.artemis.Component;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import org.catrobat.catroid.fast2d.components.PhysicsComponent;
import org.catrobat.catroid.fast2d.components.TransformComponent;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/fast2d/systems/Fast2DPhysicsSystem.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class Fast2DPhysicsSystem extends IteratingSystem {
    public static final float PPM = 100.0f;
    private static final float TIME_STEP = 0.016666668f;
    private float accumulator;
    protected ComponentMapper<PhysicsComponent> mPhysics;
    protected ComponentMapper<TransformComponent> mTransform;
    private final World physicsWorld;

    public Fast2DPhysicsSystem(World world) {
        super(Aspect.all((Class<? extends Component>[]) new Class[]{TransformComponent.class, PhysicsComponent.class}));
        this.accumulator = 0.0f;
        this.physicsWorld = world;
    }

    @Override // com.artemis.BaseEntitySystem
    protected void removed(int entityId) {
        PhysicsComponent physicsComponent = (PhysicsComponent) this.mPhysics.get(entityId);
        if (physicsComponent == null || physicsComponent.body == null) {
            return;
        }
        this.physicsWorld.destroyBody(physicsComponent.body);
        physicsComponent.body = null;
    }

    @Override // com.artemis.BaseSystem
    protected void begin() {
        float f;
        this.accumulator += this.world.getDelta();
        int i = 3;
        while (true) {
            f = this.accumulator;
            if (f < TIME_STEP || i <= 0) {
                break;
            }
            this.physicsWorld.step(TIME_STEP, 4, 1);
            this.accumulator -= TIME_STEP;
            i--;
        }
        if (f >= TIME_STEP) {
            this.accumulator = f % TIME_STEP;
        }
    }

    @Override // com.artemis.systems.IteratingSystem
    protected void process(int entityId) {
        PhysicsComponent physicsComponent = (PhysicsComponent) this.mPhysics.get(entityId);
        TransformComponent transformComponent = (TransformComponent) this.mTransform.get(entityId);
        if (physicsComponent.body == null || !physicsComponent.body.isAwake()) {
            return;
        }
        Vector2 position = physicsComponent.body.getPosition();
        transformComponent.x = position.x * 100.0f;
        transformComponent.y = position.y * 100.0f;
        transformComponent.rotation = physicsComponent.body.getAngle() * 57.295776f;
    }
}
