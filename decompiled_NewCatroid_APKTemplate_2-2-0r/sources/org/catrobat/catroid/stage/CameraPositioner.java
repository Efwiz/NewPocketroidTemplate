package org.catrobat.catroid.stage;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.content.Look;
import org.catrobat.catroid.content.Sprite;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/stage/CameraPositioner.class
  classes14.dex
 */
/* JADX INFO: compiled from: CameraPositioner.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u0000 $2\u00020\u0001:\u0001$B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007J\u0018\u0010\u001e\u001a\u00020\u00052\u0006\u0010\u001f\u001a\u00020\u00052\u0006\u0010 \u001a\u00020\u0005H\u0002J\u0006\u0010!\u001a\u00020\"J\u0006\u0010#\u001a\u00020\"R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001a\u0010\f\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001c\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0017\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u000e\"\u0004\b\u0019\u0010\u0010R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u000e\"\u0004\b\u001b\u0010\u0010R\u001a\u0010\u0006\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u000e\"\u0004\b\u001d\u0010\u0010¨\u0006%"}, d2 = {"Lorg/catrobat/catroid/stage/CameraPositioner;", "", "camera", "Lcom/badlogic/gdx/graphics/OrthographicCamera;", "virtualHeightHalf", "", "virtualWidthHalf", "(Lcom/badlogic/gdx/graphics/OrthographicCamera;FF)V", "getCamera", "()Lcom/badlogic/gdx/graphics/OrthographicCamera;", "setCamera", "(Lcom/badlogic/gdx/graphics/OrthographicCamera;)V", "horizontalFlex", "getHorizontalFlex", "()F", "setHorizontalFlex", "(F)V", "spriteToFocusOn", "Lorg/catrobat/catroid/content/Sprite;", "getSpriteToFocusOn", "()Lorg/catrobat/catroid/content/Sprite;", "setSpriteToFocusOn", "(Lorg/catrobat/catroid/content/Sprite;)V", "verticalFlex", "getVerticalFlex", "setVerticalFlex", "getVirtualHeightHalf", "setVirtualHeightHalf", "getVirtualWidthHalf", "setVirtualWidthHalf", "convertLimitOnNegativeDifference", "difference", "limit", "reset", "", "updateCameraPositionForFocusedSprite", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CameraPositioner {
    private static final int CALCULATE_PERCENT = 100;
    private OrthographicCamera camera;
    private float horizontalFlex;
    private Sprite spriteToFocusOn;
    private float verticalFlex;
    private float virtualHeightHalf;
    private float virtualWidthHalf;

    private final float convertLimitOnNegativeDifference(float difference, float limit) {
        return difference < 0.0f ? limit * (-1) : limit;
    }

    public CameraPositioner(OrthographicCamera camera, float f, float f2) {
        Intrinsics.checkNotNullParameter(camera, "camera");
        this.camera = camera;
        this.virtualHeightHalf = f;
        this.virtualWidthHalf = f2;
    }

    public final OrthographicCamera getCamera() {
        return this.camera;
    }

    public final void setCamera(OrthographicCamera orthographicCamera) {
        Intrinsics.checkNotNullParameter(orthographicCamera, "<set-?>");
        this.camera = orthographicCamera;
    }

    public final float getVirtualHeightHalf() {
        return this.virtualHeightHalf;
    }

    public final void setVirtualHeightHalf(float f) {
        this.virtualHeightHalf = f;
    }

    public final float getVirtualWidthHalf() {
        return this.virtualWidthHalf;
    }

    public final void setVirtualWidthHalf(float f) {
        this.virtualWidthHalf = f;
    }

    public final Sprite getSpriteToFocusOn() {
        return this.spriteToFocusOn;
    }

    public final void setSpriteToFocusOn(Sprite sprite) {
        this.spriteToFocusOn = sprite;
    }

    public final float getHorizontalFlex() {
        return this.horizontalFlex;
    }

    public final void setHorizontalFlex(float f) {
        this.horizontalFlex = f;
    }

    public final float getVerticalFlex() {
        return this.verticalFlex;
    }

    public final void setVerticalFlex(float f) {
        this.verticalFlex = f;
    }

    public final void updateCameraPositionForFocusedSprite() {
        Look look;
        Look look2;
        Sprite sprite = this.spriteToFocusOn;
        if (sprite == null || (look = sprite.look) == null) {
            return;
        }
        float xInUserInterfaceDimensionUnit = look.getXInUserInterfaceDimensionUnit();
        Sprite sprite2 = this.spriteToFocusOn;
        if (sprite2 == null || (look2 = sprite2.look) == null) {
            return;
        }
        float yInUserInterfaceDimensionUnit = look2.getYInUserInterfaceDimensionUnit();
        float f = 100;
        float f2 = this.virtualWidthHalf * (this.horizontalFlex / f);
        float f3 = this.virtualHeightHalf * (this.verticalFlex / f);
        Vector3 position = this.camera.position;
        Intrinsics.checkNotNullExpressionValue(position, "position");
        float f4 = xInUserInterfaceDimensionUnit - position.x;
        float f5 = yInUserInterfaceDimensionUnit - position.y;
        float fConvertLimitOnNegativeDifference = this.camera.position.x;
        float fConvertLimitOnNegativeDifference2 = this.camera.position.y;
        if (Math.abs(f4) > f2) {
            fConvertLimitOnNegativeDifference = xInUserInterfaceDimensionUnit - convertLimitOnNegativeDifference(f4, f2);
        }
        if (Math.abs(f5) > f3) {
            fConvertLimitOnNegativeDifference2 = yInUserInterfaceDimensionUnit - convertLimitOnNegativeDifference(f5, f3);
        }
        if (fConvertLimitOnNegativeDifference == this.camera.position.x && fConvertLimitOnNegativeDifference2 == this.camera.position.y) {
            return;
        }
        this.camera.position.set(fConvertLimitOnNegativeDifference, fConvertLimitOnNegativeDifference2, 0.0f);
        this.camera.update();
    }

    public final void reset() {
        if (this.spriteToFocusOn == null) {
            return;
        }
        this.spriteToFocusOn = null;
        this.camera.position.set(0.0f, 0.0f, 0.0f);
        this.camera.update();
    }
}
