package org.catrobat.catroid.physics;

import android.util.Log;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.physics.box2d.joints.GearJointDef;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef;
import com.badlogic.gdx.physics.box2d.joints.PulleyJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.badlogic.gdx.utils.GdxNativesLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.catrobat.catroid.common.ScreenValues;
import org.catrobat.catroid.content.Look;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.XmlHeader;
import org.catrobat.catroid.physics.shapebuilder.PhysicsShapeBuilder;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/physics/PhysicsWorld.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class PhysicsWorld {
    public static float ACTIVE_AREA_HEIGHT_FACTOR = 0.0f;
    public static float ACTIVE_AREA_WIDTH_FACTOR = 0.0f;
    public static final short CATEGORY_BOUNDARYBOX = 2;
    public static final short CATEGORY_NO_COLLISION = 0;
    public static final short CATEGORY_PHYSICSOBJECT = 4;
    public static final Vector2 DEFAULT_GRAVITY;
    public static final boolean IGNORE_SLEEPING_OBJECTS = true;
    public static final short MASK_BOUNDARYBOX = 4;
    public static final short MASK_NO_COLLISION = 0;
    public static final short MASK_PHYSICSOBJECT = -3;
    public static final short MASK_TO_BOUNCE = -1;
    public static final int POSITION_ITERATIONS = 3;
    public static final float RATIO = 10.0f;
    public static final int STABILIZING_STEPS = 6;
    private static final String TAG;
    public static final int VELOCITY_ITERATIONS = 3;
    public static Vector2 activeArea;
    private final ArrayList<Sprite> activeHorizontalBounces;
    private final ArrayList<Sprite> activeVerticalBounces;
    private PhysicsBoundaryBox boundaryBox;
    private final ClosestRayCastCallback closestRayCastCallback;
    private final RayCastResult currentRayCastResult;
    private final Map<String, Joint> joints;
    private final Map<Sprite, PhysicsObject> physicsObjects;
    private PhysicsShapeBuilder physicsShapeBuilder;
    private final Map<String, RayCastResult> rayCastResults;
    private Box2DDebugRenderer renderer;
    private int stabilizingSteCounter;
    private final World world;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/physics/PhysicsWorld$RayCastResult.class
      classes14.dex
     */
    public static class RayCastResult {
        public boolean hasHit = false;
        public Sprite hitSprite = null;
        public Vector2 hitPoint = new Vector2();
        public Vector2 hitNormal = new Vector2();
        public float hitFraction = -1.0f;
    }

    static {
        GdxNativesLoader.load();
        TAG = "PhysicsWorld";
        ACTIVE_AREA_WIDTH_FACTOR = 3.0f;
        ACTIVE_AREA_HEIGHT_FACTOR = 2.0f;
        DEFAULT_GRAVITY = new Vector2(0.0f, -10.0f);
    }

    public PhysicsWorld(Project project) {
        this(ScreenValues.currentScreenResolution.getWidth(), ScreenValues.currentScreenResolution.getHeight(), project);
    }

    public PhysicsWorld(int width, int height, Project project) {
        World world = new World(DEFAULT_GRAVITY, true);
        this.world = world;
        this.physicsObjects = new HashMap();
        this.activeVerticalBounces = new ArrayList<>();
        this.activeHorizontalBounces = new ArrayList<>();
        this.joints = new HashMap();
        this.stabilizingSteCounter = 0;
        this.physicsShapeBuilder = PhysicsShapeBuilder.getInstance();
        this.rayCastResults = new HashMap();
        this.currentRayCastResult = new RayCastResult();
        this.closestRayCastCallback = new ClosestRayCastCallback();
        XmlHeader xmlHeader = project.getXmlHeader();
        ACTIVE_AREA_WIDTH_FACTOR = xmlHeader.getPhysicsWidthArea();
        ACTIVE_AREA_HEIGHT_FACTOR = xmlHeader.getPhysicsHeightArea();
        PhysicsBoundaryBox physicsBoundaryBox = new PhysicsBoundaryBox(world);
        this.boundaryBox = physicsBoundaryBox;
        physicsBoundaryBox.create(width, height);
        activeArea = new Vector2(width * ACTIVE_AREA_WIDTH_FACTOR, height * ACTIVE_AREA_HEIGHT_FACTOR);
        world.setContactListener(new PhysicsCollisionListener(this));
    }

    public PhysicsWorld(int width, int height) {
        World world = new World(DEFAULT_GRAVITY, true);
        this.world = world;
        this.physicsObjects = new HashMap();
        this.activeVerticalBounces = new ArrayList<>();
        this.activeHorizontalBounces = new ArrayList<>();
        this.joints = new HashMap();
        this.stabilizingSteCounter = 0;
        this.physicsShapeBuilder = PhysicsShapeBuilder.getInstance();
        this.rayCastResults = new HashMap();
        this.currentRayCastResult = new RayCastResult();
        this.closestRayCastCallback = new ClosestRayCastCallback();
        ACTIVE_AREA_WIDTH_FACTOR = 3.0f;
        ACTIVE_AREA_HEIGHT_FACTOR = 2.0f;
        PhysicsBoundaryBox physicsBoundaryBox = new PhysicsBoundaryBox(world);
        this.boundaryBox = physicsBoundaryBox;
        physicsBoundaryBox.create(width, height);
        activeArea = new Vector2(width * ACTIVE_AREA_WIDTH_FACTOR, height * ACTIVE_AREA_HEIGHT_FACTOR);
        world.setContactListener(new PhysicsCollisionListener(this));
    }

    public void setBounceOnce(Sprite sprite, PhysicsBoundaryBox.BoundaryBoxIdentifier boundaryBoxIdentifier) {
        if (this.physicsObjects.containsKey(sprite)) {
            this.physicsObjects.get(sprite).setIfOnEdgeBounce(true, sprite);
            int i = AnonymousClass2.$SwitchMap$org$catrobat$catroid$physics$PhysicsBoundaryBox$BoundaryBoxIdentifier[boundaryBoxIdentifier.ordinal()];
            if (i == 1) {
                this.activeHorizontalBounces.add(sprite);
            } else {
                if (i != 2) {
                    return;
                }
                this.activeVerticalBounces.add(sprite);
            }
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/physics/PhysicsWorld$2.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.physics.PhysicsWorld$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$org$catrobat$catroid$physics$PhysicsBoundaryBox$BoundaryBoxIdentifier;

        static {
            int[] iArr = new int[PhysicsBoundaryBox.BoundaryBoxIdentifier.values().length];
            $SwitchMap$org$catrobat$catroid$physics$PhysicsBoundaryBox$BoundaryBoxIdentifier = iArr;
            try {
                iArr[PhysicsBoundaryBox.BoundaryBoxIdentifier.BBI_HORIZONTAL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$physics$PhysicsBoundaryBox$BoundaryBoxIdentifier[PhysicsBoundaryBox.BoundaryBoxIdentifier.BBI_VERTICAL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public void step(float deltaTime) {
        int i = this.stabilizingSteCounter;
        if (i < 6) {
            this.stabilizingSteCounter = i + 1;
            return;
        }
        try {
            this.world.step(deltaTime, 3, 3);
        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
    }

    public void render(Matrix4 perspectiveMatrix) {
        if (this.renderer == null) {
            this.renderer = new Box2DDebugRenderer(false, false, false, false, false, false);
        }
        this.renderer.render(this.world, perspectiveMatrix.scl(10.0f));
    }

    public boolean createPrismaticJoint(String jointId, Sprite spriteA, Sprite spriteB, Vector2 worldAnchor, Vector2 worldAxis) {
        if (jointId == null || jointId.isEmpty() || this.joints.containsKey(jointId)) {
            return false;
        }
        PhysicsObject physicsObject = getPhysicsObject(spriteA);
        PhysicsObject physicsObject2 = getPhysicsObject(spriteB);
        PrismaticJointDef prismaticJointDef = new PrismaticJointDef();
        prismaticJointDef.initialize(physicsObject.body, physicsObject2.body, PhysicsWorldConverter.convertCatroidToBox2dVector(worldAnchor), PhysicsWorldConverter.convertCatroidToBox2dVector(worldAxis));
        prismaticJointDef.collideConnected = false;
        this.joints.put(jointId, this.world.createJoint(prismaticJointDef));
        return true;
    }

    public boolean createRevoluteJoint(String jointId, Sprite spriteA, Sprite spriteB, Vector2 worldAnchorPoint) {
        if (jointId != null && !jointId.isEmpty() && !this.joints.containsKey(jointId)) {
            PhysicsObject physicsObject = getPhysicsObject(spriteA);
            PhysicsObject physicsObject2 = getPhysicsObject(spriteB);
            if (physicsObject != null && physicsObject2 != null) {
                RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
                revoluteJointDef.initialize(physicsObject.body, physicsObject2.body, PhysicsWorldConverter.convertCatroidToBox2dVector(worldAnchorPoint));
                revoluteJointDef.collideConnected = false;
                this.joints.put(jointId, this.world.createJoint(revoluteJointDef));
                return true;
            }
        }
        return false;
    }

    public boolean createDistanceJoint(String jointId, Sprite spriteA, Sprite spriteB, Float length, Float frequency, Float damping) {
        if (jointId != null && !jointId.isEmpty() && !this.joints.containsKey(jointId)) {
            PhysicsObject physicsObject = getPhysicsObject(spriteA);
            PhysicsObject physicsObject2 = getPhysicsObject(spriteB);
            if (physicsObject != null && physicsObject2 != null) {
                DistanceJointDef distanceJointDef = new DistanceJointDef();
                distanceJointDef.initialize(physicsObject.body, physicsObject2.body, physicsObject.body.getWorldCenter(), physicsObject2.body.getWorldCenter());
                distanceJointDef.collideConnected = false;
                if (length != null) {
                    distanceJointDef.length = PhysicsWorldConverter.convertNormalToBox2dCoordinate(length.floatValue());
                }
                if (frequency != null) {
                    distanceJointDef.frequencyHz = frequency.floatValue();
                }
                if (damping != null) {
                    distanceJointDef.dampingRatio = damping.floatValue();
                }
                this.joints.put(jointId, this.world.createJoint(distanceJointDef));
                return true;
            }
        }
        return false;
    }

    public boolean createWeldJoint(String jointId, Sprite spriteA, Sprite spriteB, Vector2 worldAnchorPoint) {
        if (jointId != null && !jointId.isEmpty() && !this.joints.containsKey(jointId)) {
            PhysicsObject physicsObject = getPhysicsObject(spriteA);
            PhysicsObject physicsObject2 = getPhysicsObject(spriteB);
            if (physicsObject != null && physicsObject2 != null) {
                WeldJointDef weldJointDef = new WeldJointDef();
                weldJointDef.initialize(physicsObject.body, physicsObject2.body, PhysicsWorldConverter.convertCatroidToBox2dVector(worldAnchorPoint));
                weldJointDef.collideConnected = false;
                this.joints.put(jointId, this.world.createJoint(weldJointDef));
                return true;
            }
        }
        return false;
    }

    public boolean createPulleyJoint(String jointId, Sprite spriteA, Sprite spriteB, Vector2 groundAnchorA, Vector2 groundAnchorB, float ratio) {
        if (jointId != null && !jointId.isEmpty() && !this.joints.containsKey(jointId)) {
            PhysicsObject physicsObject = getPhysicsObject(spriteA);
            PhysicsObject physicsObject2 = getPhysicsObject(spriteB);
            if (physicsObject != null && physicsObject2 != null) {
                PulleyJointDef pulleyJointDef = new PulleyJointDef();
                pulleyJointDef.initialize(physicsObject.body, physicsObject2.body, PhysicsWorldConverter.convertCatroidToBox2dVector(groundAnchorA), PhysicsWorldConverter.convertCatroidToBox2dVector(groundAnchorB), physicsObject.body.getWorldCenter(), physicsObject2.body.getWorldCenter(), ratio);
                pulleyJointDef.collideConnected = false;
                this.joints.put(jointId, this.world.createJoint(pulleyJointDef));
                return true;
            }
        }
        return false;
    }

    public boolean createGearJoint(String jointId, String jointAId, String jointBId, float ratio) {
        if (jointId != null && !jointId.isEmpty() && !this.joints.containsKey(jointId)) {
            Joint joint = this.joints.get(jointAId);
            Joint joint2 = this.joints.get(jointBId);
            if (joint == null || joint2 == null) {
                Log.e(TAG, "Cannot create GearJoint: one of the child joints was not found.");
            } else {
                GearJointDef gearJointDef = new GearJointDef();
                gearJointDef.joint1 = joint;
                gearJointDef.joint2 = joint2;
                gearJointDef.bodyA = joint.getBodyA();
                gearJointDef.bodyB = joint2.getBodyB();
                gearJointDef.ratio = ratio;
                this.joints.put(jointId, this.world.createJoint(gearJointDef));
                return true;
            }
        }
        return false;
    }

    public void destroyJoint(String jointId) {
        Joint jointRemove;
        if (jointId == null || !this.joints.containsKey(jointId) || (jointRemove = this.joints.remove(jointId)) == null) {
            return;
        }
        this.world.destroyJoint(jointRemove);
    }

    public void applyForce(Sprite sprite, Vector2 force, Vector2 point) {
        PhysicsObject physicsObject = getPhysicsObject(sprite);
        physicsObject.body.applyForce(force, physicsObject.body.getWorldPoint(PhysicsWorldConverter.convertCatroidToBox2dVector(point)), true);
    }

    public void applyImpulse(Sprite sprite, Vector2 impulse, Vector2 point) {
        PhysicsObject physicsObject = getPhysicsObject(sprite);
        physicsObject.body.applyLinearImpulse(impulse, physicsObject.body.getWorldPoint(PhysicsWorldConverter.convertCatroidToBox2dVector(point)), true);
    }

    public void applyTorque(Sprite sprite, float torque) {
        getPhysicsObject(sprite).body.applyTorque(torque, true);
    }

    public void applyAngularImpulse(Sprite sprite, float impulse) {
        getPhysicsObject(sprite).body.applyAngularImpulse(impulse, true);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/physics/PhysicsWorld$ClosestRayCastCallback.class
      classes14.dex
     */
    private class ClosestRayCastCallback implements RayCastCallback {
        public ClosestRayCastCallback() {
        }

        @Override // com.badlogic.gdx.physics.box2d.RayCastCallback
        public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
            if (fraction < PhysicsWorld.this.currentRayCastResult.hitFraction || !PhysicsWorld.this.currentRayCastResult.hasHit) {
                PhysicsWorld.this.currentRayCastResult.hasHit = true;
                PhysicsWorld.this.currentRayCastResult.hitSprite = (Sprite) fixture.getBody().getUserData();
                PhysicsWorld.this.currentRayCastResult.hitPoint = PhysicsWorldConverter.convertBox2dToNormalVector(point);
                PhysicsWorld.this.currentRayCastResult.hitNormal = normal.cpy();
                PhysicsWorld.this.currentRayCastResult.hitFraction = fraction;
            }
            return fraction;
        }
    }

    public void performRayCast(String rayId, Vector2 start, Vector2 end) {
        this.currentRayCastResult.hasHit = false;
        this.currentRayCastResult.hitFraction = -1.0f;
        this.world.rayCast(this.closestRayCastCallback, PhysicsWorldConverter.convertCatroidToBox2dVector(start), PhysicsWorldConverter.convertCatroidToBox2dVector(end));
        RayCastResult rayCastResult = new RayCastResult();
        rayCastResult.hasHit = this.currentRayCastResult.hasHit;
        if (rayCastResult.hasHit) {
            rayCastResult.hitSprite = this.currentRayCastResult.hitSprite;
            rayCastResult.hitPoint = this.currentRayCastResult.hitPoint;
            rayCastResult.hitNormal = this.currentRayCastResult.hitNormal;
            rayCastResult.hitFraction = this.currentRayCastResult.hitFraction;
        }
        this.rayCastResults.put(rayId, rayCastResult);
    }

    public RayCastResult getRayCastResult(String rayId) {
        return this.rayCastResults.get(rayId);
    }

    public void setGravity(float x, float y) {
        this.world.setGravity(new Vector2(x, y));
    }

    public Vector2 getGravity() {
        return this.world.getGravity();
    }

    public void changeLook(PhysicsObject physicsObject, Look look) {
        physicsObject.setShape((look.getLookData() == null || look.getLookData().getFile() == null) ? null : this.physicsShapeBuilder.getScaledShapes(look.getLookData(), look.getSizeInUserInterfaceDimensionUnit() / 100.0f));
    }

    public PhysicsObject getPhysicsObject(Sprite sprite) {
        sprite.getClass();
        if (this.physicsObjects.containsKey(sprite)) {
            return this.physicsObjects.get(sprite);
        }
        PhysicsObject physicsObjectCreatePhysicsObject = createPhysicsObject(sprite);
        this.physicsObjects.put(sprite, physicsObjectCreatePhysicsObject);
        return physicsObjectCreatePhysicsObject;
    }

    private PhysicsObject createPhysicsObject(Sprite sprite) {
        return new PhysicsObject(this.world.createBody(new BodyDef()), sprite);
    }

    public void bouncedOnEdge(Sprite sprite, PhysicsBoundaryBox.BoundaryBoxIdentifier boundaryBoxIdentifier) {
        if (this.physicsObjects.containsKey(sprite)) {
            PhysicsObject physicsObject = this.physicsObjects.get(sprite);
            int i = AnonymousClass2.$SwitchMap$org$catrobat$catroid$physics$PhysicsBoundaryBox$BoundaryBoxIdentifier[boundaryBoxIdentifier.ordinal()];
            if (i == 1) {
                if (!this.activeHorizontalBounces.remove(sprite) || this.activeVerticalBounces.contains(sprite)) {
                    return;
                }
                physicsObject.setIfOnEdgeBounce(false, sprite);
                PhysicalCollision.fireBounceOffEvent(sprite, null);
                return;
            }
            if (i == 2 && this.activeVerticalBounces.remove(sprite) && !this.activeHorizontalBounces.contains(sprite)) {
                physicsObject.setIfOnEdgeBounce(false, sprite);
                PhysicalCollision.fireBounceOffEvent(sprite, null);
            }
        }
    }

    public void destroyPhysicsObject(Sprite sprite) {
        final PhysicsObject physicsObjectRemove;
        if (sprite == null || !this.physicsObjects.containsKey(sprite) || (physicsObjectRemove = this.physicsObjects.remove(sprite)) == null || physicsObjectRemove.body == null) {
            return;
        }
        Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.physics.PhysicsWorld.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    PhysicsWorld.this.world.destroyBody(physicsObjectRemove.body);
                } catch (Exception e) {
                    Log.e(PhysicsWorld.TAG, "Error destroying physics body asynchronously: " + e.getMessage());
                }
            }
        });
    }
}
