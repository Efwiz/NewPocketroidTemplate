package org.catrobat.catroid.physics;

import android.util.Log;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.content.Look;
import org.catrobat.catroid.content.Sprite;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/physics/PhysicsLook.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class PhysicsLook extends Look {
    private static final int FULL_CIRCLE_DEGREE = 360;
    private static final int HALF_CIRCLE_DEGREE = 180;
    public static final float SCALE_FACTOR_ACCURACY = 10000.0f;
    private boolean isFlippedByAction;
    private final PhysicsObject physicsObject;
    private final PhysicsObjectStateHandler physicsObjectStateHandler;

    public PhysicsLook(Sprite sprite, PhysicsWorld physicsWorld) {
        super(sprite);
        this.isFlippedByAction = false;
        PhysicsObject physicsObject = physicsWorld.getPhysicsObject(sprite);
        this.physicsObject = physicsObject;
        this.physicsObjectStateHandler = new PhysicsObjectStateHandler(this, physicsObject);
    }

    @Override // org.catrobat.catroid.content.Look
    public void destroy() {
        PhysicsWorld physicsWorld;
        try {
            if (ProjectManager.getInstance() != null && ProjectManager.getInstance().getCurrentlyPlayingScene() != null && (physicsWorld = ProjectManager.getInstance().getCurrentlyPlayingScene().getPhysicsWorld()) != null && this.sprite != null) {
                physicsWorld.destroyPhysicsObject(this.sprite);
            }
        } catch (Exception e) {
            Log.e("PhysicsLook", "Error destroying physics object", e);
        }
        super.destroy();
    }

    @Override // org.catrobat.catroid.content.Look
    public void copyTo(final Look destination) {
        super.copyTo(destination);
        if (destination instanceof PhysicsLook) {
            this.physicsObject.copyTo(((PhysicsLook) destination).physicsObject);
        }
    }

    @Override // org.catrobat.catroid.content.Look
    public void setTransparencyInUserInterfaceDimensionUnit(float percent) {
        super.setTransparencyInUserInterfaceDimensionUnit(percent);
        updatePhysicsObjectState(true);
    }

    @Override // org.catrobat.catroid.content.Look
    public void setLookData(LookData lookData) {
        super.setLookData(lookData);
        ProjectManager.getInstance().getCurrentlyPlayingScene().getPhysicsWorld().changeLook(this.physicsObject, this);
        updatePhysicsObjectState(true);
    }

    @Override // org.catrobat.catroid.content.Look
    public void setXInUserInterfaceDimensionUnit(float x) {
        setX(applyCenterOffset(x, true, false));
    }

    @Override // org.catrobat.catroid.content.Look, com.badlogic.gdx.scenes.scene2d.Actor
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        PhysicsObject physicsObject = this.physicsObject;
        if (physicsObject != null) {
            physicsObject.setX(applyCenterOffset(x, true, true));
            this.physicsObject.setY(applyCenterOffset(y, false, true));
        }
    }

    @Override // org.catrobat.catroid.content.Look, com.badlogic.gdx.scenes.scene2d.Actor
    public void setX(float x) {
        super.setX(x);
        PhysicsObject physicsObject = this.physicsObject;
        if (physicsObject != null) {
            physicsObject.setX(applyCenterOffset(x, true, true));
        }
    }

    @Override // org.catrobat.catroid.content.Look, com.badlogic.gdx.scenes.scene2d.Actor
    public void setY(float y) {
        super.setY(y);
        PhysicsObject physicsObject = this.physicsObject;
        if (physicsObject != null) {
            physicsObject.setY(applyCenterOffset(y, false, true));
        }
    }

    @Override // org.catrobat.catroid.content.Look
    public float getAngularVelocityInUserInterfaceDimensionUnit() {
        return this.physicsObject.getRotationSpeed();
    }

    @Override // org.catrobat.catroid.content.Look
    public float getXVelocityInUserInterfaceDimensionUnit() {
        if (this.sprite.isGliding()) {
            return this.sprite.getGlidingVelocityX();
        }
        return this.physicsObject.getVelocity().x;
    }

    @Override // org.catrobat.catroid.content.Look
    public float getYVelocityInUserInterfaceDimensionUnit() {
        if (this.sprite.isGliding()) {
            return this.sprite.getGlidingVelocityY();
        }
        return this.physicsObject.getVelocity().y;
    }

    @Override // org.catrobat.catroid.content.Look
    public float getMotionDirectionInUserInterfaceDimensionUnit() {
        PhysicsObject physicsObject = this.physicsObject;
        if (physicsObject == null || physicsObject.getVelocity() == null || !isLookMoving()) {
            return super.getMotionDirectionInUserInterfaceDimensionUnit();
        }
        return breakDownCatroidAngle(90.0f - ((float) Math.toDegrees(Math.atan2(this.physicsObject.getVelocity().y, this.physicsObject.getVelocity().x))));
    }

    @Override // org.catrobat.catroid.content.Look
    public float getLookDirectionInUserInterfaceDimensionUnit() {
        int rotationMode = getRotationMode();
        if (rotationMode == 0) {
            return isFlipped() ? -90.0f : 90.0f;
        }
        if (rotationMode != 1) {
            return rotationMode != 2 ? 0.0f : 90.0f;
        }
        return convertStageAngleToCatroidAngle(getRotation());
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Actor
    public float getX() {
        float fApplyCenterOffset = applyCenterOffset(this.physicsObject.getX(), true, false);
        super.setX(fApplyCenterOffset);
        return fApplyCenterOffset;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Actor
    public float getY() {
        float fApplyCenterOffset = applyCenterOffset(this.physicsObject.getY(), false, false);
        super.setY(fApplyCenterOffset);
        return fApplyCenterOffset;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Actor
    public float getRotation() {
        PhysicsObject physicsObject = this.physicsObject;
        if (physicsObject == null) {
            return super.getRotation();
        }
        super.setRotation(physicsObject.getDirection() % 360.0f);
        float rotation = super.getRotation();
        float direction = this.physicsObject.getDirection() % 360.0f;
        if (direction < 0.0f) {
            direction += 360.0f;
        }
        int rotationMode = super.getRotationMode();
        if (rotationMode == 0) {
            super.setRotation(0.0f);
            flipLookDataIfNeeded(direction);
        } else if (rotationMode == 1) {
            super.setRotation(rotation);
        } else if (rotationMode == 2) {
            super.setRotation(0.0f);
        }
        return super.getRotation();
    }

    @Override // org.catrobat.catroid.content.Look, com.badlogic.gdx.scenes.scene2d.Actor
    public void setRotation(float degrees) {
        super.setRotation(degrees);
        if (this.physicsObject == null || getRotationMode() != 1) {
            return;
        }
        this.physicsObject.setDirection(super.getRotation() % 360.0f);
    }

    @Override // org.catrobat.catroid.content.Look
    public void setMotionDirectionInUserInterfaceDimensionUnit(float degrees) {
        PhysicsObject physicsObject = this.physicsObject;
        if (physicsObject != null) {
            physicsObject.setDirection(((-degrees) + 90.0f) % 360.0f);
        }
        super.setMotionDirectionInUserInterfaceDimensionUnit(degrees);
    }

    private void flipLookDataIfNeeded(float realRotation) {
        float fBreakDownCatroidAngle = breakDownCatroidAngle(this.physicsObject.getDirection());
        boolean z = fBreakDownCatroidAngle >= 0.0f;
        boolean z2 = fBreakDownCatroidAngle < 0.0f;
        boolean zIsFlipped = isFlipped();
        if (this.isFlippedByAction) {
            zIsFlipped = !zIsFlipped;
        }
        if (this.lookData != null) {
            if (!(zIsFlipped && z) && (zIsFlipped || !z2)) {
                return;
            }
            this.lookData.getTextureRegion().flip(true, false);
        }
    }

    @Override // org.catrobat.catroid.content.Look
    public void setRotationMode(int mode) {
        super.setRotationMode(mode);
        updatePhysicsObjectState(true);
    }

    @Override // org.catrobat.catroid.content.Look, com.badlogic.gdx.scenes.scene2d.Actor
    public void setScale(float scaleX, float scaleY) {
        if (Float.isNaN(scaleX) || Float.isInfinite(scaleX) || scaleX < 1.0E-4f) {
            scaleX = 1.0f;
        }
        if (Float.isNaN(scaleY) || Float.isInfinite(scaleY) || scaleY < 1.0E-4f) {
            scaleY = 1.0f;
        }
        Vector2 vector2 = new Vector2(getScaleX(), getScaleY());
        int iRound = Math.round(scaleX * 10000.0f);
        int iRound2 = Math.round(scaleY * 10000.0f);
        int iRound3 = Math.round(vector2.x * 10000.0f);
        int iRound4 = Math.round(vector2.y * 10000.0f);
        if (iRound == iRound3 && iRound2 == iRound4) {
            return;
        }
        super.setScale(scaleX, scaleY);
        if (this.physicsObject != null) {
            ProjectManager.getInstance().getCurrentlyPlayingScene().getPhysicsWorld().changeLook(this.physicsObject, this);
            updatePhysicsObjectState(true);
        }
    }

    @Override // org.catrobat.catroid.content.Look
    public void setLookVisible(boolean visible) {
        super.setLookVisible(visible);
        this.physicsObjectStateHandler.update(true);
    }

    @Override // org.catrobat.catroid.content.Look, com.badlogic.gdx.scenes.scene2d.ui.Image, com.badlogic.gdx.scenes.scene2d.ui.Widget, com.badlogic.gdx.scenes.scene2d.Actor
    public void draw(Batch batch, float parentAlpha) {
        this.physicsObjectStateHandler.checkHangup(true);
        super.draw(batch, parentAlpha);
    }

    public void updatePhysicsObjectState(boolean record) {
        this.physicsObjectStateHandler.update(record);
    }

    public boolean isHangedUp() {
        return this.physicsObjectStateHandler.isHangedUp();
    }

    public void setNonColliding(boolean nonColliding) {
        this.physicsObjectStateHandler.setNonColliding(nonColliding);
    }

    public void startGlide() {
        this.physicsObjectStateHandler.activateGlideTo();
    }

    public void stopGlide() {
        this.physicsObjectStateHandler.deactivateGlideTo();
    }

    public void updateFlippedByAction() {
        this.isFlippedByAction = !this.isFlippedByAction;
    }

    public void setFlippedByDegree(float degree) {
        setFlippedByDirection((degree + getMotionDirectionInUserInterfaceDimensionUnit()) % 360.0f);
    }

    public void setFlippedByDirection(float newDirection) {
        float f = newDirection % 360.0f;
        if (f < 0.0f) {
            f += 360.0f;
        }
        float motionDirectionInUserInterfaceDimensionUnit = getMotionDirectionInUserInterfaceDimensionUnit() - 90.0f;
        if ((motionDirectionInUserInterfaceDimensionUnit >= 0.0f && motionDirectionInUserInterfaceDimensionUnit <= 180.0f) != (f >= 0.0f && f <= 180.0f)) {
            updateFlippedByAction();
        }
    }

    private boolean isLookMoving() {
        return (((double) this.physicsObject.getVelocity().y) == 0.0d && ((double) this.physicsObject.getVelocity().x) == 0.0d) ? false : true;
    }

    private float applyCenterOffset(float coordinate, boolean isXCoordinate, boolean add) {
        float width = isXCoordinate ? getWidth() : getHeight();
        return add ? coordinate + (width / 2.0f) : coordinate - (width / 2.0f);
    }
}
