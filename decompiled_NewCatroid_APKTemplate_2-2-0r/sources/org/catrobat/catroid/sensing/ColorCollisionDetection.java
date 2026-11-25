package org.catrobat.catroid.sensing;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Polygon;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.content.Look;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.common.Conversions;
import org.catrobat.catroid.stage.StageListener;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/sensing/ColorCollisionDetection.class
  classes14.dex
 */
/* JADX INFO: compiled from: ColorCollisionDetection.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\u0006\u0010\u0010\u001a\u00020\u000fJ\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0010\u0010\u0015\u001a\n\u0012\u0004\u0012\u00020\u0017\u0018\u00010\u0016H\u0016J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0002J\u0012\u0010\u001c\u001a\u00020\u00192\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0017J\b\u0010\u001f\u001a\u00020 H\u0016J \u0010!\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020#H\u0002J\u001a\u0010%\u001a\u00020\u00192\b\u0010&\u001a\u0004\u0018\u00010\u001e2\b\u0010'\u001a\u0004\u0018\u00010\u001eJ\u0010\u0010(\u001a\u00020\u00192\b\u0010)\u001a\u0004\u0018\u00010\u001eR\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R6\u0010\t\u001a(\u0012\f\u0012\n \f*\u0004\u0018\u00010\u000b0\u000b \f*\u0014\u0012\u000e\b\u0001\u0012\n \f*\u0004\u0018\u00010\u000b0\u000b\u0018\u00010\n0\nX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\rR\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006*"}, d2 = {"Lorg/catrobat/catroid/sensing/ColorCollisionDetection;", "Lorg/catrobat/catroid/sensing/ColorDetection;", "scope", "Lorg/catrobat/catroid/content/Scope;", "stageListener", "Lorg/catrobat/catroid/stage/StageListener;", "(Lorg/catrobat/catroid/content/Scope;Lorg/catrobat/catroid/stage/StageListener;)V", "boundingRectangle", "Lorg/catrobat/catroid/sensing/BoundingRectangle;", "polygons", "", "Lcom/badlogic/gdx/math/Polygon;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "[Lcom/badlogic/gdx/math/Polygon;", "scale", "", "calculateBufferScale", "createProjectionMatrix", "Lcom/badlogic/gdx/math/Matrix4;", "project", "Lorg/catrobat/catroid/content/Project;", "getLooksOfRelevantSprites", "", "Lorg/catrobat/catroid/content/Look;", "interpretMatcherOnStage", "", "matcher", "Lorg/catrobat/catroid/sensing/ConditionMatcher;", "isParameterInvalid", "parameter", "", "setBufferParameters", "", "tryConditionMatcherRunnerMatch", "batch", "Lcom/badlogic/gdx/graphics/g2d/SpriteBatch;", "spriteBatch", "tryInterpretFunctionColorTouchesColor", "spriteColor", "stageColor", "tryInterpretFunctionTouchesColor", TypedValues.Custom.S_COLOR, "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ColorCollisionDetection extends ColorDetection {
    private final BoundingRectangle boundingRectangle;
    private final Polygon[] polygons;
    private final float scale;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ColorCollisionDetection(Scope scope, StageListener stageListener) {
        super(scope, stageListener);
        Intrinsics.checkNotNullParameter(scope, "scope");
        Polygon[] polygons = scope.getSprite().look.getCurrentCollisionPolygon();
        this.polygons = polygons;
        Intrinsics.checkNotNullExpressionValue(polygons, "polygons");
        this.boundingRectangle = ConditionMatcherRunnerKt.toBoundingRectangle(polygons);
        this.scale = calculateBufferScale();
    }

    public final boolean tryInterpretFunctionTouchesColor(Object color) {
        setBufferParameters();
        if (isParameterInvalid(color) || isLookInvalid() || getStageListener() == null) {
            return false;
        }
        Intrinsics.checkNotNull(color, "null cannot be cast to non-null type kotlin.String");
        try {
            return interpretMatcherOnStage(new TouchesColorMatcher((String) color));
        } catch (Exception unused) {
            return false;
        }
    }

    private final boolean interpretMatcherOnStage(ConditionMatcher matcher) {
        List<Look> looksOfRelevantSprites = getLooksOfRelevantSprites();
        if (looksOfRelevantSprites == null) {
            return false;
        }
        SpriteBatch spriteBatch = new SpriteBatch();
        SpriteBatch spriteBatch2 = new SpriteBatch();
        Project project = getScope().getProject();
        Matrix4 matrix4CreateProjectionMatrix = project != null ? createProjectionMatrix(project) : null;
        matcher.setStagePixmap(matrix4CreateProjectionMatrix != null ? createPicture(looksOfRelevantSprites, matrix4CreateProjectionMatrix, spriteBatch) : null);
        boolean zIsLookVisible = getLook().isLookVisible();
        getLook().setLookVisible(true);
        matcher.setSpritePixmap(matrix4CreateProjectionMatrix != null ? createPicture(CollectionsKt.listOf(getLook()), matrix4CreateProjectionMatrix, spriteBatch2) : null);
        getLook().setLookVisible(zIsLookVisible);
        return tryConditionMatcherRunnerMatch(matcher, spriteBatch, spriteBatch2);
    }

    private final boolean tryConditionMatcherRunnerMatch(ConditionMatcher matcher, SpriteBatch batch, SpriteBatch spriteBatch) {
        try {
            return new ConditionMatcherRunner(matcher).match(getBufferWidth(), getBufferHeight());
        } finally {
            Pixmap stagePixmap = matcher.getStagePixmap();
            if (stagePixmap != null) {
                stagePixmap.dispose();
            }
            Pixmap spritePixmap = matcher.getSpritePixmap();
            if (spritePixmap != null) {
                spritePixmap.dispose();
            }
            batch.dispose();
            spriteBatch.dispose();
        }
    }

    public final boolean tryInterpretFunctionColorTouchesColor(Object spriteColor, Object stageColor) {
        setBufferParameters();
        if (isParameterInvalid(spriteColor) || isParameterInvalid(stageColor) || isLookInvalid()) {
            return false;
        }
        Intrinsics.checkNotNull(spriteColor, "null cannot be cast to non-null type kotlin.String");
        Intrinsics.checkNotNull(stageColor, "null cannot be cast to non-null type kotlin.String");
        try {
            return interpretMatcherOnStage(new ColorTouchesColorMatcher((String) spriteColor, (String) stageColor));
        } catch (Exception unused) {
            return false;
        }
    }

    public final float calculateBufferScale() {
        float fSqrt = (float) Math.sqrt(10000.0f / (this.boundingRectangle.getWidth() * this.boundingRectangle.getHeight()));
        if (fSqrt < 1.0f) {
            return fSqrt;
        }
        return 1.0f;
    }

    @Override // org.catrobat.catroid.sensing.ColorDetection
    public void setBufferParameters() {
        setBufferWidth((int) (this.boundingRectangle.getWidth() * this.scale));
        setBufferHeight((int) (this.boundingRectangle.getHeight() * this.scale));
    }

    @Override // org.catrobat.catroid.sensing.ColorDetection
    public boolean isParameterInvalid(Object parameter) {
        return ((parameter instanceof String) && Conversions.INSTANCE.isValidHexColor((String) parameter)) ? false : true;
    }

    @Override // org.catrobat.catroid.sensing.ColorDetection
    public List<Look> getLooksOfRelevantSprites() {
        StageListener stageListener = getStageListener();
        if (stageListener == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList(stageListener.getSpritesFromStage());
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : arrayList) {
            Sprite sprite = (Sprite) obj;
            if (!Intrinsics.areEqual(sprite, getScope().getSprite()) || sprite.isClone) {
                if (sprite.look.isLookVisible()) {
                    arrayList2.add(obj);
                }
            }
        }
        ArrayList arrayList3 = arrayList2;
        ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList3, 10));
        Iterator it = arrayList3.iterator();
        while (it.hasNext()) {
            arrayList4.add(((Sprite) it.next()).look);
        }
        return CollectionsKt.toMutableList((Collection) arrayList4);
    }

    @Override // org.catrobat.catroid.sensing.ColorDetection
    public Matrix4 createProjectionMatrix(Project project) {
        Intrinsics.checkNotNullParameter(project, "project");
        float width = this.boundingRectangle.getWidth() * getLook().getScaleX();
        float height = this.boundingRectangle.getHeight() * getLook().getScaleY();
        OrthographicCamera orthographicCamera = new OrthographicCamera(width, height);
        createViewport(project, width, height, orthographicCamera).apply();
        float f = 2;
        orthographicCamera.position.set(getLook().getX() + (getLook().getWidth() / f), getLook().getY() + (getLook().getHeight() / f), 0.0f);
        orthographicCamera.rotate(-getLook().getRotation());
        orthographicCamera.update();
        Matrix4 combined = orthographicCamera.combined;
        Intrinsics.checkNotNullExpressionValue(combined, "combined");
        return combined;
    }
}
