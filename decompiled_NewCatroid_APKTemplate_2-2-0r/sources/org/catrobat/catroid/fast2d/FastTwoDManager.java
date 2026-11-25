package org.catrobat.catroid.fast2d;

import com.artemis.ComponentMapper;
import com.artemis.WorldConfigurationBuilder;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.catrobat.catroid.fast2d.components.MovementComponent;
import org.catrobat.catroid.fast2d.components.PhysicsComponent;
import org.catrobat.catroid.fast2d.components.TextureComponent;
import org.catrobat.catroid.fast2d.components.TransformComponent;
import org.catrobat.catroid.fast2d.systems.Fast2DMovementSystem;
import org.catrobat.catroid.fast2d.systems.Fast2DPhysicsSystem;
import org.catrobat.catroid.fast2d.systems.Fast2DRenderSystem;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/fast2d/FastTwoDManager.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class FastTwoDManager implements Disposable {
    private OrthographicCamera camera;
    private ComponentMapper<MovementComponent> mMovement;
    private ComponentMapper<PhysicsComponent> mPhysics;
    private ComponentMapper<TextureComponent> mTexture;
    private ComponentMapper<TransformComponent> mTransform;
    private Fast2DMovementSystem movementSystem;
    private World physicsWorld;
    private Fast2DRenderSystem renderSystem;
    private com.artemis.World world;
    private final Map<String, Integer> entities = new HashMap();
    private final Map<String, Texture> textureCache = new HashMap();
    private final Vector3 tempVec = new Vector3();

    public void init(SpriteBatch batch) {
        this.physicsWorld = new World(new Vector2(0.0f, -9.8f), true);
        this.renderSystem = new Fast2DRenderSystem(batch);
        this.movementSystem = new Fast2DMovementSystem();
        com.artemis.World world = new com.artemis.World(new WorldConfigurationBuilder().with(this.movementSystem, new Fast2DPhysicsSystem(this.physicsWorld), this.renderSystem).build());
        this.world = world;
        this.mTransform = world.getMapper(TransformComponent.class);
        this.mTexture = this.world.getMapper(TextureComponent.class);
        this.mMovement = this.world.getMapper(MovementComponent.class);
        this.mPhysics = this.world.getMapper(PhysicsComponent.class);
        OrthographicCamera orthographicCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.camera = orthographicCamera;
        orthographicCamera.position.set(0.0f, 0.0f, 0.0f);
        this.camera.update();
    }

    public void resize(int width, int height) {
        this.camera.setToOrtho(false, width, height);
        this.camera.position.set(0.0f, 0.0f, 0.0f);
        this.camera.update();
    }

    public void updateAndRender(float delta) {
        this.renderSystem.setCamera(this.camera);
        this.world.setDelta(delta);
        this.world.process();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: getOrCreateEntity, reason: merged with bridge method [inline-methods] */
    public int m5357x3bef8dff(String entityId) {
        Integer num = this.entities.get(entityId);
        if (num == null) {
            int iCreate = this.world.create();
            this.mTransform.create(iCreate);
            this.entities.put(entityId, Integer.valueOf(iCreate));
            return iCreate;
        }
        return num.intValue();
    }

    private Texture getOrLoadTexture(String absolutePath) {
        if (this.textureCache.containsKey(absolutePath)) {
            return this.textureCache.get(absolutePath);
        }
        try {
            Texture texture = new Texture(Gdx.files.absolute(absolutePath), true);
            texture.setFilter(Texture.TextureFilter.MipMapLinearLinear, Texture.TextureFilter.Linear);
            this.textureCache.put(absolutePath, texture);
            return texture;
        } catch (Exception e) {
            Gdx.f82app.error("Fast2D", "Failed to load texture: " + absolutePath, e);
            return null;
        }
    }

    private void rebuildFixture(int i) {
        Shape shape;
        if (this.mPhysics.has(i)) {
            PhysicsComponent physicsComponent = (PhysicsComponent) this.mPhysics.get(i);
            if (physicsComponent.body == null) {
                return;
            }
            TransformComponent transformComponent = (TransformComponent) this.mTransform.get(i);
            TextureComponent textureComponent = this.mTexture.has(i) ? (TextureComponent) this.mTexture.get(i) : null;
            Array<Fixture> fixtureList = physicsComponent.body.getFixtureList();
            while (fixtureList.size > 0) {
                physicsComponent.body.destroyFixture(fixtureList.first());
            }
            float fAbs = 25.0f;
            float fAbs2 = (textureComponent == null || textureComponent.region == null) ? 25.0f : Math.abs(textureComponent.width * transformComponent.scaleX) / 2.0f;
            if (textureComponent != null && textureComponent.region != null) {
                fAbs = Math.abs(textureComponent.height * transformComponent.scaleY) / 2.0f;
            }
            if (fAbs2 < 0.1f) {
                fAbs2 = 0.1f;
            }
            if (fAbs < 0.1f) {
                fAbs = 0.1f;
            }
            if ("CIRCLE".equalsIgnoreCase(physicsComponent.shapeType)) {
                CircleShape circleShape = new CircleShape();
                circleShape.setRadius(Math.max(fAbs2, fAbs) / 100.0f);
                shape = circleShape;
            } else {
                PolygonShape polygonShape = new PolygonShape();
                polygonShape.setAsBox(fAbs2 / 100.0f, fAbs / 100.0f);
                shape = polygonShape;
            }
            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = shape;
            fixtureDef.density = physicsComponent.density;
            fixtureDef.friction = physicsComponent.friction;
            fixtureDef.restitution = physicsComponent.bounce;
            physicsComponent.body.createFixture(fixtureDef);
            shape.dispose();
            if (physicsComponent.isDynamic) {
                physicsComponent.body.resetMassData();
            }
            physicsComponent.body.setAwake(true);
        }
    }

    public void createEntity(final String id2) {
        Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.fast2d.FastTwoDManager$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5357x3bef8dff(id2);
            }
        });
    }

    public void destroyEntity(final String id2) {
        Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.fast2d.FastTwoDManager$$ExternalSyntheticLambda15
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5358xeafee24(id2);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$destroyEntity$1$org-catrobat-catroid-fast2d-FastTwoDManager, reason: not valid java name */
    /* synthetic */ void m5358xeafee24(String str) {
        Integer numRemove = this.entities.remove(str);
        if (numRemove != null) {
            this.world.delete(numRemove.intValue());
        }
    }

    public void setPosition(final String id2, final float x, final float y) {
        Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.fast2d.FastTwoDManager$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5366lambda$setPosition$2$orgcatrobatcatroidfast2dFastTwoDManager(id2, x, y);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$setPosition$2$org-catrobat-catroid-fast2d-FastTwoDManager, reason: not valid java name */
    /* synthetic */ void m5366lambda$setPosition$2$orgcatrobatcatroidfast2dFastTwoDManager(String str, float f, float f2) {
        int iM5357x3bef8dff = m5357x3bef8dff(str);
        TransformComponent transformComponent = (TransformComponent) this.mTransform.get(iM5357x3bef8dff);
        transformComponent.x = f;
        transformComponent.y = f2;
        if (!this.mPhysics.has(iM5357x3bef8dff) || ((PhysicsComponent) this.mPhysics.get(iM5357x3bef8dff)).body == null) {
            return;
        }
        ((PhysicsComponent) this.mPhysics.get(iM5357x3bef8dff)).body.setTransform(f / 100.0f, f2 / 100.0f, ((PhysicsComponent) this.mPhysics.get(iM5357x3bef8dff)).body.getAngle());
        ((PhysicsComponent) this.mPhysics.get(iM5357x3bef8dff)).body.setAwake(true);
    }

    public void setRotation(final String id2, final float angle) {
        Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.fast2d.FastTwoDManager$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5367lambda$setRotation$3$orgcatrobatcatroidfast2dFastTwoDManager(id2, angle);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$setRotation$3$org-catrobat-catroid-fast2d-FastTwoDManager, reason: not valid java name */
    /* synthetic */ void m5367lambda$setRotation$3$orgcatrobatcatroidfast2dFastTwoDManager(String str, float f) {
        int iM5357x3bef8dff = m5357x3bef8dff(str);
        ((TransformComponent) this.mTransform.get(iM5357x3bef8dff)).rotation = f;
        if (!this.mPhysics.has(iM5357x3bef8dff) || ((PhysicsComponent) this.mPhysics.get(iM5357x3bef8dff)).body == null) {
            return;
        }
        Body body = ((PhysicsComponent) this.mPhysics.get(iM5357x3bef8dff)).body;
        body.setTransform(body.getPosition(), f * 0.017453292f);
        body.setAwake(true);
    }

    public void setScale(final String id2, final float sx, final float sy) {
        Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.fast2d.FastTwoDManager$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5368lambda$setScale$4$orgcatrobatcatroidfast2dFastTwoDManager(id2, sx, sy);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$setScale$4$org-catrobat-catroid-fast2d-FastTwoDManager, reason: not valid java name */
    /* synthetic */ void m5368lambda$setScale$4$orgcatrobatcatroidfast2dFastTwoDManager(String str, float f, float f2) {
        int iM5357x3bef8dff = m5357x3bef8dff(str);
        ((TransformComponent) this.mTransform.get(iM5357x3bef8dff)).scaleX = f;
        ((TransformComponent) this.mTransform.get(iM5357x3bef8dff)).scaleY = f2;
        rebuildFixture(iM5357x3bef8dff);
    }

    public void setZIndex(final String id2, final float z) {
        Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.fast2d.FastTwoDManager$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5371lambda$setZIndex$5$orgcatrobatcatroidfast2dFastTwoDManager(id2, z);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$setZIndex$5$org-catrobat-catroid-fast2d-FastTwoDManager, reason: not valid java name */
    /* synthetic */ void m5371lambda$setZIndex$5$orgcatrobatcatroidfast2dFastTwoDManager(String str, float f) {
        ((TransformComponent) this.mTransform.get(m5357x3bef8dff(str))).z = f;
        this.renderSystem.requestSort();
    }

    public void setTexture(final String id2, final String absolutePath) {
        Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.fast2d.FastTwoDManager$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5369lambda$setTexture$6$orgcatrobatcatroidfast2dFastTwoDManager(id2, absolutePath);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$setTexture$6$org-catrobat-catroid-fast2d-FastTwoDManager, reason: not valid java name */
    /* synthetic */ void m5369lambda$setTexture$6$orgcatrobatcatroidfast2dFastTwoDManager(String str, String str2) {
        int iM5357x3bef8dff = m5357x3bef8dff(str);
        Texture orLoadTexture = getOrLoadTexture(str2);
        if (orLoadTexture == null) {
            return;
        }
        TextureComponent textureComponent = (TextureComponent) this.mTexture.create(iM5357x3bef8dff);
        textureComponent.region = new TextureRegion(orLoadTexture);
        int iLastIndexOf = str2.lastIndexOf(47);
        if (iLastIndexOf >= 0) {
            str2 = str2.substring(iLastIndexOf + 1);
        }
        textureComponent.textureName = str2;
        textureComponent.width = orLoadTexture.getWidth();
        textureComponent.height = orLoadTexture.getHeight();
        textureComponent.originX = textureComponent.width / 2.0f;
        textureComponent.originY = textureComponent.height / 2.0f;
        rebuildFixture(iM5357x3bef8dff);
    }

    public void setColor(final String id2, final float r, final float g, final float b, final float a) {
        Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.fast2d.FastTwoDManager$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5363lambda$setColor$7$orgcatrobatcatroidfast2dFastTwoDManager(id2, r, g, b, a);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$setColor$7$org-catrobat-catroid-fast2d-FastTwoDManager, reason: not valid java name */
    /* synthetic */ void m5363lambda$setColor$7$orgcatrobatcatroidfast2dFastTwoDManager(String str, float f, float f2, float f3, float f4) {
        ((TextureComponent) this.mTexture.create(m5357x3bef8dff(str))).color.set(f / 255.0f, f2 / 255.0f, f3 / 255.0f, f4 / 100.0f);
    }

    public void setVelocity(final String id2, final float vx, final float vy) {
        Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.fast2d.FastTwoDManager$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5370lambda$setVelocity$8$orgcatrobatcatroidfast2dFastTwoDManager(id2, vx, vy);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$setVelocity$8$org-catrobat-catroid-fast2d-FastTwoDManager, reason: not valid java name */
    /* synthetic */ void m5370lambda$setVelocity$8$orgcatrobatcatroidfast2dFastTwoDManager(String str, float f, float f2) {
        MovementComponent movementComponent = (MovementComponent) this.mMovement.create(m5357x3bef8dff(str));
        movementComponent.velocityX = f;
        movementComponent.velocityY = f2;
    }

    public void setAngularVelocity(final String id2, final float angVel) {
        Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.fast2d.FastTwoDManager$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5360xae090266(id2, angVel);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$setAngularVelocity$9$org-catrobat-catroid-fast2d-FastTwoDManager, reason: not valid java name */
    /* synthetic */ void m5360xae090266(String str, float f) {
        ((MovementComponent) this.mMovement.create(m5357x3bef8dff(str))).angularVelocity = f;
    }

    public void makePhysicsBody(final String id2, final boolean isDynamic, final String shape, final float density, final float friction, final float bounce) {
        Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.fast2d.FastTwoDManager$$ExternalSyntheticLambda16
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5359x9def80dc(id2, isDynamic, shape, density, friction, bounce);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$makePhysicsBody$10$org-catrobat-catroid-fast2d-FastTwoDManager, reason: not valid java name */
    /* synthetic */ void m5359x9def80dc(String str, boolean z, String str2, float f, float f2, float f3) {
        int iM5357x3bef8dff = m5357x3bef8dff(str);
        TransformComponent transformComponent = (TransformComponent) this.mTransform.get(iM5357x3bef8dff);
        if (this.mPhysics.has(iM5357x3bef8dff) && ((PhysicsComponent) this.mPhysics.get(iM5357x3bef8dff)).body != null) {
            this.physicsWorld.destroyBody(((PhysicsComponent) this.mPhysics.get(iM5357x3bef8dff)).body);
        }
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = z ? BodyDef.BodyType.DynamicBody : BodyDef.BodyType.StaticBody;
        bodyDef.position.set(transformComponent.x / 100.0f, transformComponent.y / 100.0f);
        bodyDef.angle = transformComponent.rotation * 0.017453292f;
        bodyDef.awake = true;
        bodyDef.allowSleep = true;
        Body bodyCreateBody = this.physicsWorld.createBody(bodyDef);
        PhysicsComponent physicsComponent = (PhysicsComponent) this.mPhysics.create(iM5357x3bef8dff);
        physicsComponent.body = bodyCreateBody;
        physicsComponent.isDynamic = z;
        physicsComponent.shapeType = str2;
        physicsComponent.density = f;
        physicsComponent.friction = f2;
        physicsComponent.bounce = f3;
        rebuildFixture(iM5357x3bef8dff);
    }

    public void applyForce(final String id2, final float forceX, final float forceY) {
        Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.fast2d.FastTwoDManager$$ExternalSyntheticLambda14
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5355lambda$applyForce$11$orgcatrobatcatroidfast2dFastTwoDManager(id2, forceX, forceY);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$applyForce$11$org-catrobat-catroid-fast2d-FastTwoDManager, reason: not valid java name */
    /* synthetic */ void m5355lambda$applyForce$11$orgcatrobatcatroidfast2dFastTwoDManager(String str, float f, float f2) {
        Integer num = this.entities.get(str);
        if (num == null || !this.mPhysics.has(num.intValue()) || ((PhysicsComponent) this.mPhysics.get(num.intValue())).body == null) {
            return;
        }
        ((PhysicsComponent) this.mPhysics.get(num.intValue())).body.applyForceToCenter(f, f2, true);
    }

    public void applyImpulse(final String id2, final float impX, final float impY) {
        Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.fast2d.FastTwoDManager$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5356xae506098(id2, impX, impY);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$applyImpulse$12$org-catrobat-catroid-fast2d-FastTwoDManager, reason: not valid java name */
    /* synthetic */ void m5356xae506098(String str, float f, float f2) {
        Integer num = this.entities.get(str);
        if (num == null || !this.mPhysics.has(num.intValue()) || ((PhysicsComponent) this.mPhysics.get(num.intValue())).body == null) {
            return;
        }
        Body body = ((PhysicsComponent) this.mPhysics.get(num.intValue())).body;
        Vector2 worldCenter = body.getWorldCenter();
        body.applyLinearImpulse(f, f2, worldCenter.x, worldCenter.y, true);
    }

    public void setPhysicsVelocity(final String id2, final float vx, final float vy) {
        Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.fast2d.FastTwoDManager$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5365x71121d9e(id2, vx, vy);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$setPhysicsVelocity$13$org-catrobat-catroid-fast2d-FastTwoDManager, reason: not valid java name */
    /* synthetic */ void m5365x71121d9e(String str, float f, float f2) {
        Integer num = this.entities.get(str);
        if (num == null || !this.mPhysics.has(num.intValue()) || ((PhysicsComponent) this.mPhysics.get(num.intValue())).body == null) {
            return;
        }
        ((PhysicsComponent) this.mPhysics.get(num.intValue())).body.setLinearVelocity(f, f2);
    }

    public void setCamera(final float x, final float y, final float zoom) {
        Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.fast2d.FastTwoDManager$$ExternalSyntheticLambda13
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5361lambda$setCamera$14$orgcatrobatcatroidfast2dFastTwoDManager(x, y, zoom);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$setCamera$14$org-catrobat-catroid-fast2d-FastTwoDManager, reason: not valid java name */
    /* synthetic */ void m5361lambda$setCamera$14$orgcatrobatcatroidfast2dFastTwoDManager(float f, float f2, float f3) {
        this.camera.position.set(f, f2, 0.0f);
        this.camera.zoom = f3;
        this.camera.update();
    }

    public void setGravity(final float x, final float y) {
        Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.fast2d.FastTwoDManager$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5364lambda$setGravity$15$orgcatrobatcatroidfast2dFastTwoDManager(x, y);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$setGravity$15$org-catrobat-catroid-fast2d-FastTwoDManager, reason: not valid java name */
    /* synthetic */ void m5364lambda$setGravity$15$orgcatrobatcatroidfast2dFastTwoDManager(float f, float f2) {
        World world = this.physicsWorld;
        if (world != null) {
            world.setGravity(new Vector2(f, f2));
        }
    }

    public void setCollisionFilter(final String id2, final boolean isSensor, final int groupIndex) {
        Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.fast2d.FastTwoDManager$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5362x7bf50f5b(id2, isSensor, groupIndex);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$setCollisionFilter$16$org-catrobat-catroid-fast2d-FastTwoDManager, reason: not valid java name */
    /* synthetic */ void m5362x7bf50f5b(String str, boolean z, int i) {
        try {
            int iM5357x3bef8dff = m5357x3bef8dff(str);
            if (!this.mPhysics.has(iM5357x3bef8dff) || ((PhysicsComponent) this.mPhysics.get(iM5357x3bef8dff)).body == null) {
                return;
            }
            Body body = ((PhysicsComponent) this.mPhysics.get(iM5357x3bef8dff)).body;
            Array<Fixture> fixtureList = body.getFixtureList();
            for (int i2 = 0; i2 < fixtureList.size; i2++) {
                Fixture fixture = fixtureList.get(i2);
                fixture.setSensor(z);
                Filter filterData = fixture.getFilterData();
                filterData.groupIndex = (short) i;
                fixture.setFilterData(filterData);
            }
            body.setAwake(true);
        } catch (Exception e) {
            Gdx.f82app.error("Fast2D_Physics", "Failed to set collision filter for: " + str, e);
        }
    }

    public boolean isEntityTouched(String id2, int pointer) {
        Integer num = this.entities.get(id2);
        if (num == null || !Gdx.input.isTouched(pointer)) {
            return false;
        }
        this.camera.update();
        this.tempVec.set(Gdx.input.getX(pointer), Gdx.input.getY(pointer), 0.0f);
        this.camera.unproject(this.tempVec);
        if (!this.mTransform.has(num.intValue()) || !this.mTexture.has(num.intValue())) {
            return false;
        }
        TransformComponent transformComponent = (TransformComponent) this.mTransform.get(num.intValue());
        TextureComponent textureComponent = (TextureComponent) this.mTexture.get(num.intValue());
        if (textureComponent.region == null) {
            return false;
        }
        float f = this.tempVec.x - transformComponent.x;
        float f2 = this.tempVec.y - transformComponent.y;
        if (transformComponent.rotation != 0.0f) {
            float fCosDeg = MathUtils.cosDeg(-transformComponent.rotation);
            float fSinDeg = MathUtils.sinDeg(-transformComponent.rotation);
            float f3 = (f * fCosDeg) - (f2 * fSinDeg);
            f2 = (f2 * fCosDeg) + (f * fSinDeg);
            f = f3;
        }
        float fAbs = Math.abs(textureComponent.width * transformComponent.scaleX) * 0.5f;
        float fAbs2 = Math.abs(textureComponent.height * transformComponent.scaleY) * 0.5f;
        return f >= (-fAbs) && f <= fAbs && f2 >= (-fAbs2) && f2 <= fAbs2;
    }

    public float getPositionX(String id2) {
        Integer num = this.entities.get(id2);
        if (num != null) {
            return ((TransformComponent) this.mTransform.get(num.intValue())).x;
        }
        return 0.0f;
    }

    public float getPositionY(String id2) {
        Integer num = this.entities.get(id2);
        if (num != null) {
            return ((TransformComponent) this.mTransform.get(num.intValue())).y;
        }
        return 0.0f;
    }

    public float getRotation(String id2) {
        Integer num = this.entities.get(id2);
        if (num != null) {
            return ((TransformComponent) this.mTransform.get(num.intValue())).rotation;
        }
        return 0.0f;
    }

    public float getScaleX(String id2) {
        Integer num = this.entities.get(id2);
        if (num != null) {
            return ((TransformComponent) this.mTransform.get(num.intValue())).scaleX;
        }
        return 1.0f;
    }

    public float getScaleY(String id2) {
        Integer num = this.entities.get(id2);
        if (num != null) {
            return ((TransformComponent) this.mTransform.get(num.intValue())).scaleY;
        }
        return 1.0f;
    }

    public String getTextureName(String id2) {
        Integer num = this.entities.get(id2);
        return (num == null || !this.mTexture.has(num.intValue())) ? "" : ((TextureComponent) this.mTexture.get(num.intValue())).textureName;
    }

    public float getColorR(String id2) {
        Integer num = this.entities.get(id2);
        if (num == null || !this.mTexture.has(num.intValue())) {
            return 255.0f;
        }
        return 255.0f * ((TextureComponent) this.mTexture.get(num.intValue())).color.r;
    }

    public float getColorG(String id2) {
        Integer num = this.entities.get(id2);
        if (num == null || !this.mTexture.has(num.intValue())) {
            return 255.0f;
        }
        return 255.0f * ((TextureComponent) this.mTexture.get(num.intValue())).color.g;
    }

    public float getColorB(String id2) {
        Integer num = this.entities.get(id2);
        if (num == null || !this.mTexture.has(num.intValue())) {
            return 255.0f;
        }
        return 255.0f * ((TextureComponent) this.mTexture.get(num.intValue())).color.b;
    }

    public float getAlpha(String id2) {
        Integer num = this.entities.get(id2);
        if (num == null || !this.mTexture.has(num.intValue())) {
            return 100.0f;
        }
        return 100.0f * ((TextureComponent) this.mTexture.get(num.intValue())).color.a;
    }

    public float getCamX() {
        return this.camera.position.x;
    }

    public float getCamY() {
        return this.camera.position.y;
    }

    public float getCamZoom() {
        return this.camera.zoom;
    }

    public void clearScene() {
        Iterator<Integer> it = this.entities.values().iterator();
        while (it.hasNext()) {
            this.world.delete(it.next().intValue());
        }
        this.entities.clear();
    }

    @Override // com.badlogic.gdx.utils.Disposable
    public void dispose() {
        clearScene();
        Iterator<Texture> it = this.textureCache.values().iterator();
        while (it.hasNext()) {
            it.next().dispose();
        }
        this.textureCache.clear();
        this.world.dispose();
        this.physicsWorld.dispose();
    }
}
