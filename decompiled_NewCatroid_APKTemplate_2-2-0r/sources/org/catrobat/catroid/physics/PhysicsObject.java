package org.catrobat.catroid.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.Transform;
import com.badlogic.gdx.utils.Array;
import java.util.Arrays;
import org.catrobat.catroid.content.Look;
import org.catrobat.catroid.content.Sprite;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/physics/PhysicsObject.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class PhysicsObject {
    public static final float DEFAULT_BOUNCE_FACTOR = 0.8f;
    public static final float DEFAULT_DENSITY = 1.0f;
    public static final float DEFAULT_FRICTION = 0.2f;
    public static final float DEFAULT_MASS = 1.0f;
    public static final float MAX_FRICTION = 1.0f;
    public static final float MIN_BOUNCE_FACTOR = 0.0f;
    public static final float MIN_DENSITY = 0.0f;
    public static final float MIN_FRICTION = 0.0f;
    public static final float MIN_MASS = 1.0E-6f;
    final Body body;
    private Vector2 bodyAabbLowerLeft;
    private Vector2 bodyAabbUpperRight;
    private float circumference;
    private Vector2 fixtureAabbLowerLeft;
    private Vector2 fixtureAabbUpperRight;
    private final FixtureDef fixtureDef;
    private float gravityScale;
    private boolean ifOnEdgeBounce;
    private float mass;
    private float rotationSpeed;
    private Type savedType;
    private Shape[] shapes;
    private Vector2 tmpVertice;
    private Type type;
    private Vector2 velocity;
    private short collisionMaskRecord = 0;
    private short categoryMaskRecord = 4;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/physics/PhysicsObject$Type.class
      classes14.dex
     */
    public enum Type {
        DYNAMIC,
        FIXED,
        NONE
    }

    public PhysicsObject(Body b, Sprite sprite) {
        FixtureDef fixtureDef = new FixtureDef();
        this.fixtureDef = fixtureDef;
        this.ifOnEdgeBounce = false;
        this.velocity = new Vector2();
        this.rotationSpeed = 0.0f;
        this.gravityScale = 0.0f;
        this.savedType = Type.NONE;
        this.body = b;
        b.setUserData(sprite);
        this.mass = 1.0f;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.2f;
        fixtureDef.restitution = 0.8f;
        setType(Type.NONE);
        this.tmpVertice = new Vector2();
    }

    private float sanitize(float value) {
        if (Float.isNaN(value) || Float.isInfinite(value)) {
            return 0.0f;
        }
        return value;
    }

    public void copyTo(PhysicsObject destination) {
        destination.setType(getType());
        destination.setPosition(getPosition());
        destination.setDirection(getDirection());
        destination.setMass(getMass());
        destination.setRotationSpeed(getRotationSpeed());
        destination.setBounceFactor(getBounceFactor());
        destination.setFriction(getFriction());
        destination.setVelocity(getVelocity());
    }

    public void setShape(Shape[] shapes) {
        if (Arrays.equals(this.shapes, shapes)) {
            return;
        }
        if (shapes != null) {
            this.shapes = (Shape[]) Arrays.copyOf(shapes, shapes.length);
        } else {
            this.shapes = null;
        }
        while (this.body.getFixtureList().size > 0) {
            this.body.destroyFixture(this.body.getFixtureList().first());
        }
        if (shapes != null) {
            for (Shape shape : shapes) {
                this.fixtureDef.shape = shape;
                this.body.createFixture(this.fixtureDef);
            }
        }
        setMass(this.mass);
        calculateCircumference();
    }

    public void setLinearDamping(float damping) {
        if (damping < 0.0f) {
            damping = 0.0f;
        }
        this.body.setLinearDamping(damping);
    }

    public void setAngularDamping(float damping) {
        if (damping < 0.0f) {
            damping = 0.0f;
        }
        this.body.setAngularDamping(damping);
    }

    private void calculateCircumference() {
        if (this.body.getFixtureList().size == 0) {
            this.circumference = 0.0f;
        } else {
            this.circumference = PhysicsWorldConverter.convertNormalToBox2dCoordinate(getBoundaryBoxDimensions().len() / 2.0f);
        }
    }

    public Type getType() {
        return this.type;
    }

    public void setType(Type type) {
        if (this.type == type) {
            return;
        }
        this.type = type;
        int i = AnonymousClass1.$SwitchMap$org$catrobat$catroid$physics$PhysicsObject$Type[type.ordinal()];
        if (i == 1) {
            this.body.setType(BodyDef.BodyType.DynamicBody);
            this.body.setGravityScale(1.0f);
            this.body.setBullet(true);
            setMass(this.mass);
            this.collisionMaskRecord = (short) -3;
        } else if (i == 2) {
            this.body.setType(BodyDef.BodyType.KinematicBody);
            this.collisionMaskRecord = (short) -3;
        } else if (i == 3) {
            this.body.setType(BodyDef.BodyType.KinematicBody);
            this.collisionMaskRecord = (short) 0;
        }
        calculateCircumference();
        setCollisionBits(this.categoryMaskRecord, this.collisionMaskRecord);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/physics/PhysicsObject$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.physics.PhysicsObject$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$catrobat$catroid$physics$PhysicsObject$Type;

        static {
            int[] iArr = new int[Type.values().length];
            $SwitchMap$org$catrobat$catroid$physics$PhysicsObject$Type = iArr;
            try {
                iArr[Type.DYNAMIC.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$physics$PhysicsObject$Type[Type.FIXED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$physics$PhysicsObject$Type[Type.NONE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public float getDirection() {
        return PhysicsWorldConverter.convertBox2dToNormalAngle(this.body.getAngle());
    }

    public float getX() {
        return PhysicsWorldConverter.convertBox2dToNormalCoordinate(this.body.getPosition().x);
    }

    public float getY() {
        return PhysicsWorldConverter.convertBox2dToNormalCoordinate(this.body.getPosition().y);
    }

    public Vector2 getMassCenter() {
        return this.body.getWorldCenter();
    }

    public float getCircumference() {
        return PhysicsWorldConverter.convertBox2dToNormalCoordinate(this.circumference);
    }

    public Vector2 getPosition() {
        return PhysicsWorldConverter.convertBox2dToNormalVector(this.body.getPosition());
    }

    public void setX(float x) {
        this.body.setTransform(PhysicsWorldConverter.convertNormalToBox2dCoordinate(sanitize(x)), this.body.getPosition().y, this.body.getAngle());
    }

    public void setY(float y) {
        float fSanitize = sanitize(y);
        Body body = this.body;
        body.setTransform(body.getPosition().x, PhysicsWorldConverter.convertNormalToBox2dCoordinate(fSanitize), this.body.getAngle());
    }

    public void setPosition(float x, float y) {
        float fSanitize = sanitize(x);
        float fSanitize2 = sanitize(y);
        float fConvertNormalToBox2dCoordinate = PhysicsWorldConverter.convertNormalToBox2dCoordinate(fSanitize);
        float fConvertNormalToBox2dCoordinate2 = PhysicsWorldConverter.convertNormalToBox2dCoordinate(fSanitize2);
        Body body = this.body;
        body.setTransform(fConvertNormalToBox2dCoordinate, fConvertNormalToBox2dCoordinate2, body.getAngle());
    }

    public void setDirection(float degrees) {
        float fSanitize = sanitize(degrees);
        Body body = this.body;
        body.setTransform(body.getPosition(), PhysicsWorldConverter.convertNormalToBox2dAngle(fSanitize));
    }

    public void setVelocity(float x, float y) {
        this.body.setLinearVelocity(PhysicsWorldConverter.convertNormalToBox2dCoordinate(sanitize(x)), PhysicsWorldConverter.convertNormalToBox2dCoordinate(sanitize(y)));
    }

    public void setRotationSpeed(float degreesPerSecond) {
        this.body.setAngularVelocity((float) Math.toRadians(sanitize(degreesPerSecond)));
    }

    public void setPosition(Vector2 position) {
        setPosition(position.x, position.y);
    }

    public float getRotationSpeed() {
        return (float) Math.toDegrees(this.body.getAngularVelocity());
    }

    public Vector2 getVelocity() {
        return PhysicsWorldConverter.convertBox2dToNormalVector(this.body.getLinearVelocity());
    }

    public void setVelocity(Vector2 velocity) {
        setVelocity(velocity.x, velocity.y);
    }

    public float getMass() {
        return this.mass;
    }

    public float getBounceFactor() {
        return this.fixtureDef.restitution;
    }

    public void setMass(float mass) {
        this.mass = mass;
        if (mass < 0.0f) {
            this.mass = 1.0E-6f;
        }
        if (mass < 1.0E-6f) {
            mass = 1.0E-6f;
        }
        if (isStaticObject()) {
            return;
        }
        setDensity(mass / (this.body.getMass() / this.fixtureDef.density));
    }

    private boolean isStaticObject() {
        return this.body.getMass() == 0.0f;
    }

    public void setDensity(float density) {
        if (density < 0.0f) {
            density = 0.0f;
        }
        this.fixtureDef.density = density;
        Array.ArrayIterator<Fixture> it = this.body.getFixtureList().iterator();
        while (it.hasNext()) {
            it.next().setDensity(density);
        }
        this.body.resetMassData();
    }

    public float getFriction() {
        return this.fixtureDef.friction;
    }

    public void setFriction(float friction) {
        if (friction < 0.0f) {
            friction = 0.0f;
        }
        if (friction > 1.0f) {
            friction = 1.0f;
        }
        this.fixtureDef.friction = friction;
        Array.ArrayIterator<Fixture> it = this.body.getFixtureList().iterator();
        while (it.hasNext()) {
            it.next().setFriction(friction);
        }
    }

    public void setBounceFactor(float bounceFactor) {
        if (bounceFactor < 0.0f) {
            bounceFactor = 0.0f;
        }
        this.fixtureDef.restitution = bounceFactor;
        Array.ArrayIterator<Fixture> it = this.body.getFixtureList().iterator();
        while (it.hasNext()) {
            it.next().setRestitution(bounceFactor);
        }
    }

    public void setGravityScale(float scale) {
        this.body.setGravityScale(scale);
    }

    public float getGravityScale() {
        return this.body.getGravityScale();
    }

    public void setFixedRotation(boolean flag) {
        this.body.setFixedRotation(flag);
    }

    public void setIfOnEdgeBounce(boolean bounce, Sprite sprite) {
        short s;
        if (this.ifOnEdgeBounce == bounce) {
            return;
        }
        this.ifOnEdgeBounce = bounce;
        if (bounce) {
            this.body.setUserData(sprite);
            s = -1;
        } else {
            s = -3;
        }
        setCollisionBits(this.categoryMaskRecord, s);
    }

    protected void setCollisionBits(short categoryBits, short maskBits) {
        setCollisionBits(categoryBits, maskBits, true);
    }

    protected void setCollisionBits(short categoryBits, short maskBits, boolean updateState) {
        this.fixtureDef.filter.categoryBits = categoryBits;
        this.fixtureDef.filter.maskBits = maskBits;
        Array.ArrayIterator<Fixture> it = this.body.getFixtureList().iterator();
        while (it.hasNext()) {
            Fixture next = it.next();
            Filter filterData = next.getFilterData();
            filterData.categoryBits = categoryBits;
            filterData.maskBits = maskBits;
            next.setFilterData(filterData);
        }
        if (updateState) {
            updateNonCollidingState();
        }
    }

    private void updateNonCollidingState() {
        Look look;
        if (this.body.getUserData() == null || !(this.body.getUserData() instanceof Sprite) || (look = ((Sprite) this.body.getUserData()).look) == null || !(look instanceof PhysicsLook)) {
            return;
        }
        ((PhysicsLook) look).setNonColliding(isNonColliding());
    }

    public void getBoundaryBox(Vector2 lowerLeft, Vector2 upperRight) {
        calculateAabb();
        lowerLeft.x = PhysicsWorldConverter.convertBox2dToNormalVector(this.bodyAabbLowerLeft).x;
        lowerLeft.y = PhysicsWorldConverter.convertBox2dToNormalVector(this.bodyAabbLowerLeft).y;
        upperRight.x = PhysicsWorldConverter.convertBox2dToNormalVector(this.bodyAabbUpperRight).x;
        upperRight.y = PhysicsWorldConverter.convertBox2dToNormalVector(this.bodyAabbUpperRight).y;
    }

    public Vector2 getBoundaryBoxDimensions() {
        calculateAabb();
        return new Vector2(PhysicsWorldConverter.convertBox2dToNormalCoordinate(Math.abs(this.bodyAabbUpperRight.x - this.bodyAabbLowerLeft.x)) + 1.0f, PhysicsWorldConverter.convertBox2dToNormalCoordinate(Math.abs(this.bodyAabbUpperRight.y - this.bodyAabbLowerLeft.y)) + 1.0f);
    }

    public void activateHangup() {
        this.velocity = new Vector2(getVelocity());
        this.rotationSpeed = getRotationSpeed();
        this.gravityScale = getGravityScale();
        setGravityScale(0.0f);
        setVelocity(0.0f, 0.0f);
        setRotationSpeed(0.0f);
    }

    public void deactivateHangup(boolean record) {
        if (record) {
            setGravityScale(this.gravityScale);
            setVelocity(this.velocity.x, this.velocity.y);
            setRotationSpeed(this.rotationSpeed);
            return;
        }
        setGravityScale(1.0f);
    }

    public void activateNonColliding(boolean updateState) {
        setCollisionBits(this.categoryMaskRecord, (short) 0, updateState);
    }

    public void deactivateNonColliding(boolean record, boolean updateState) {
        if (record) {
            setCollisionBits(this.categoryMaskRecord, this.collisionMaskRecord, updateState);
        }
    }

    public void activateFixed() {
        this.savedType = getType();
        setType(Type.FIXED);
    }

    public void deactivateFixed(boolean record) {
        if (record) {
            setType(this.savedType);
        }
    }

    public boolean isNonColliding() {
        return this.collisionMaskRecord == 0;
    }

    private void calculateAabb() {
        this.bodyAabbLowerLeft = new Vector2(2.1474836E9f, 2.1474836E9f);
        this.bodyAabbUpperRight = new Vector2(-2.1474836E9f, -2.1474836E9f);
        Transform transform = this.body.getTransform();
        int i = this.body.getFixtureList().size;
        Array<Fixture> fixtureList = this.body.getFixtureList();
        if (fixtureList.size == 0) {
            this.bodyAabbLowerLeft.x = 0.0f;
            this.bodyAabbLowerLeft.y = 0.0f;
            this.bodyAabbUpperRight.x = 0.0f;
            this.bodyAabbUpperRight.y = 0.0f;
        }
        for (int i2 = 0; i2 < i; i2++) {
            calculateAabb(fixtureList.get(i2), transform);
        }
    }

    private void calculateAabb(Fixture fixture, Transform transform) {
        this.fixtureAabbLowerLeft = new Vector2(2.1474836E9f, 2.1474836E9f);
        this.fixtureAabbUpperRight = new Vector2(-2.1474836E9f, -2.1474836E9f);
        if (fixture.getType() == Shape.Type.Circle) {
            CircleShape circleShape = (CircleShape) fixture.getShape();
            float radius = circleShape.getRadius();
            this.tmpVertice.set(circleShape.getPosition());
            this.tmpVertice.rotate(transform.getRotation()).add(transform.getPosition());
            this.fixtureAabbLowerLeft.set(this.tmpVertice.x - radius, this.tmpVertice.y - radius);
            this.fixtureAabbUpperRight.set(this.tmpVertice.x + radius, this.tmpVertice.y + radius);
        } else if (fixture.getType() == Shape.Type.Polygon) {
            PolygonShape polygonShape = (PolygonShape) fixture.getShape();
            int vertexCount = polygonShape.getVertexCount();
            polygonShape.getVertex(0, this.tmpVertice);
            this.fixtureAabbLowerLeft.set(transform.mul(this.tmpVertice));
            this.fixtureAabbUpperRight.set(this.fixtureAabbLowerLeft);
            for (int i = 1; i < vertexCount; i++) {
                polygonShape.getVertex(i, this.tmpVertice);
                transform.mul(this.tmpVertice);
                Vector2 vector2 = this.fixtureAabbLowerLeft;
                vector2.x = Math.min(vector2.x, this.tmpVertice.x);
                Vector2 vector3 = this.fixtureAabbLowerLeft;
                vector3.y = Math.min(vector3.y, this.tmpVertice.y);
                Vector2 vector4 = this.fixtureAabbUpperRight;
                vector4.x = Math.max(vector4.x, this.tmpVertice.x);
                Vector2 vector5 = this.fixtureAabbUpperRight;
                vector5.y = Math.max(vector5.y, this.tmpVertice.y);
            }
        }
        this.bodyAabbLowerLeft.x = Math.min(this.fixtureAabbLowerLeft.x, this.bodyAabbLowerLeft.x);
        this.bodyAabbLowerLeft.y = Math.min(this.fixtureAabbLowerLeft.y, this.bodyAabbLowerLeft.y);
        this.bodyAabbUpperRight.x = Math.max(this.fixtureAabbUpperRight.x, this.bodyAabbUpperRight.x);
        this.bodyAabbUpperRight.y = Math.max(this.fixtureAabbUpperRight.y, this.bodyAabbUpperRight.y);
    }
}
