package org.catrobat.catroid.physics;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import java.util.HashMap;
import java.util.Map;
import org.catrobat.catroid.content.Sprite;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/physics/PhysicsCollisionListener.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class PhysicsCollisionListener implements ContactListener {
    public static final String COLLISION_MESSAGE_CONNECTOR = "<\t-\t>";
    public static final String COLLISION_MESSAGE_ESCAPE_CHAR = "\t";
    private Map<CollidingSprites, PhysicalCollision> collidingSpritesToCollisionMap = new HashMap();
    private PhysicsWorld physicsWorld;

    @Override // com.badlogic.gdx.physics.box2d.ContactListener
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }

    @Override // com.badlogic.gdx.physics.box2d.ContactListener
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    public PhysicsCollisionListener(PhysicsWorld physicsWorld) {
        this.physicsWorld = physicsWorld;
    }

    private void registerContact(Sprite sprite1, Sprite sprite2) {
        CollidingSprites collidingSprites = new CollidingSprites(sprite1, sprite2);
        if (!this.collidingSpritesToCollisionMap.containsKey(collidingSprites)) {
            this.collidingSpritesToCollisionMap.put(collidingSprites, new PhysicalCollision(collidingSprites));
        }
        this.collidingSpritesToCollisionMap.get(collidingSprites).increaseContactCounter();
    }

    private void unregisterContact(Sprite sprite1, Sprite sprite2) {
        CollidingSprites collidingSprites = new CollidingSprites(sprite1, sprite2);
        if (this.collidingSpritesToCollisionMap.containsKey(collidingSprites)) {
            PhysicalCollision physicalCollision = this.collidingSpritesToCollisionMap.get(collidingSprites);
            physicalCollision.decreaseContactCounter();
            if (physicalCollision.getContactCounter() == 0) {
                physicalCollision.sendBounceOffEvents();
                this.collidingSpritesToCollisionMap.remove(collidingSprites);
            }
        }
    }

    @Override // com.badlogic.gdx.physics.box2d.ContactListener
    public void beginContact(Contact contact) {
        Body body = contact.getFixtureA().getBody();
        Body body2 = contact.getFixtureB().getBody();
        if ((body.getUserData() instanceof Sprite) && (body2.getUserData() instanceof PhysicsBoundaryBox.BoundaryBoxIdentifier)) {
            this.physicsWorld.bouncedOnEdge((Sprite) body.getUserData(), (PhysicsBoundaryBox.BoundaryBoxIdentifier) body2.getUserData());
            return;
        }
        if ((body.getUserData() instanceof PhysicsBoundaryBox.BoundaryBoxIdentifier) && (body2.getUserData() instanceof Sprite)) {
            this.physicsWorld.bouncedOnEdge((Sprite) body2.getUserData(), (PhysicsBoundaryBox.BoundaryBoxIdentifier) body.getUserData());
        } else if ((body.getUserData() instanceof Sprite) && (body2.getUserData() instanceof Sprite)) {
            registerContact((Sprite) body.getUserData(), (Sprite) body2.getUserData());
        }
    }

    @Override // com.badlogic.gdx.physics.box2d.ContactListener
    public void endContact(Contact contact) {
        Body body = contact.getFixtureA().getBody();
        Body body2 = contact.getFixtureB().getBody();
        if ((body.getUserData() instanceof Sprite) && (body2.getUserData() instanceof Sprite)) {
            unregisterContact((Sprite) body.getUserData(), (Sprite) body2.getUserData());
        }
    }
}
