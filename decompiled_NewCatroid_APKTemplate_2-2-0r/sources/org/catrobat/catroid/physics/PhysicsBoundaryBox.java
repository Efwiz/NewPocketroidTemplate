package org.catrobat.catroid.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/physics/PhysicsBoundaryBox.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class PhysicsBoundaryBox {
    public static final int FRAME_SIZE = 5;
    private final World world;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/physics/PhysicsBoundaryBox$BoundaryBoxIdentifier.class
      classes14.dex
     */
    public enum BoundaryBoxIdentifier {
        BBI_HORIZONTAL,
        BBI_VERTICAL
    }

    public PhysicsBoundaryBox(World world) {
        this.world = world;
    }

    public void create(int width, int height) {
        float fConvertNormalToBox2dCoordinate = PhysicsWorldConverter.convertNormalToBox2dCoordinate(width);
        float fConvertNormalToBox2dCoordinate2 = PhysicsWorldConverter.convertNormalToBox2dCoordinate(height);
        float fConvertNormalToBox2dCoordinate3 = PhysicsWorldConverter.convertNormalToBox2dCoordinate(5.0f);
        float f = fConvertNormalToBox2dCoordinate3 / 2.0f;
        float f2 = fConvertNormalToBox2dCoordinate2 / 2.0f;
        createSide(new Vector2(0.0f, f2 + f), fConvertNormalToBox2dCoordinate, fConvertNormalToBox2dCoordinate3, BoundaryBoxIdentifier.BBI_HORIZONTAL);
        createSide(new Vector2(0.0f, (-f2) - f), fConvertNormalToBox2dCoordinate, fConvertNormalToBox2dCoordinate3, BoundaryBoxIdentifier.BBI_HORIZONTAL);
        float f3 = fConvertNormalToBox2dCoordinate / 2.0f;
        createSide(new Vector2((-f3) - f, 0.0f), fConvertNormalToBox2dCoordinate3, fConvertNormalToBox2dCoordinate2, BoundaryBoxIdentifier.BBI_VERTICAL);
        createSide(new Vector2(f3 + f, 0.0f), fConvertNormalToBox2dCoordinate3, fConvertNormalToBox2dCoordinate2, BoundaryBoxIdentifier.BBI_VERTICAL);
    }

    private void createSide(Vector2 center, float width, float height, BoundaryBoxIdentifier identifier) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.allowSleep = false;
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(width / 2.0f, height / 2.0f, center, 0.0f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.filter.maskBits = (short) 4;
        fixtureDef.filter.categoryBits = (short) 2;
        Body bodyCreateBody = this.world.createBody(bodyDef);
        bodyCreateBody.createFixture(fixtureDef);
        bodyCreateBody.setUserData(identifier);
    }
}
