package org.catrobat.catroid.content;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.constraintlayout.motion.widget.Key;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.stage.StageActivity;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/SquareActor.class
  classes14.dex
 */
/* JADX INFO: compiled from: SquareActor.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000 \u001c2\u00020\u0001:\u0001\u001cBK\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\u0003\u0012\b\b\u0002\u0010\n\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0003¢\u0006\u0002\u0010\fJ\u001a\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0013\u001a\u00020\u0003H\u0016J8\u0010\u0014\u001a\u00020\u00102\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00032\u0006\u0010\u0018\u001a\u00020\u00032\u0006\u0010\u0019\u001a\u00020\u00032\u0006\u0010\u001a\u001a\u00020\u00032\u0006\u0010\u001b\u001a\u00020\u0003H\u0002R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001d"}, d2 = {"Lorg/catrobat/catroid/content/SquareActor;", "Lcom/badlogic/gdx/scenes/scene2d/Actor;", "x", "", "y", "width", "height", "colorHex", "", Key.ROTATION, "transparency", StageActivity.STYLE_CORNER_RADIUS, "(FFFFLjava/lang/String;FFF)V", TypedValues.Custom.S_COLOR, "Lcom/badlogic/gdx/graphics/Color;", "draw", "", "batch", "Lcom/badlogic/gdx/graphics/g2d/Batch;", "parentAlpha", "drawRoundedRectangle", "shapeRenderer", "Lcom/badlogic/gdx/graphics/glutils/ShapeRenderer;", "rx", "ry", "rWidth", "rHeight", "radius", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SquareActor extends Actor {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static ShapeRenderer sharedShapeRenderer;
    private final Color color;
    private final float cornerRadius;
    private final float height;
    private final float rotation;
    private final float width;
    private final float x;
    private final float y;

    @JvmStatic
    public static final void disposeShared() {
        INSTANCE.disposeShared();
    }

    public /* synthetic */ SquareActor(float f, float f2, float f3, float f4, String str, float f5, float f6, float f7, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(f, f2, f3, f4, str, (i & 32) != 0 ? 0.0f : f5, (i & 64) != 0 ? 1.0f : f6, (i & 128) != 0 ? 0.0f : f7);
    }

    public SquareActor(float f, float f2, float f3, float f4, String colorHex, float f5, float f6, float f7) {
        Intrinsics.checkNotNullParameter(colorHex, "colorHex");
        this.x = f;
        this.y = f2;
        this.width = f3;
        this.height = f4;
        this.rotation = f5;
        this.cornerRadius = f7;
        Color colorValueOf = Color.valueOf(colorHex);
        colorValueOf.a = f6;
        Intrinsics.checkNotNullExpressionValue(colorValueOf, "apply(...)");
        this.color = colorValueOf;
        setBounds(f, f2, f3, f4);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/SquareActor$Companion.class
      classes14.dex
     */
    /* JADX INFO: compiled from: SquareActor.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0005\u001a\u00020\u0006H\u0007J\u0006\u0010\u0007\u001a\u00020\u0004R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\b"}, d2 = {"Lorg/catrobat/catroid/content/SquareActor$Companion;", "", "()V", "sharedShapeRenderer", "Lcom/badlogic/gdx/graphics/glutils/ShapeRenderer;", "disposeShared", "", "getShapeRenderer", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final ShapeRenderer getShapeRenderer() {
            if (SquareActor.sharedShapeRenderer == null) {
                SquareActor.sharedShapeRenderer = new ShapeRenderer();
            }
            ShapeRenderer shapeRenderer = SquareActor.sharedShapeRenderer;
            Intrinsics.checkNotNull(shapeRenderer);
            return shapeRenderer;
        }

        @JvmStatic
        public final void disposeShared() {
            ShapeRenderer shapeRenderer = SquareActor.sharedShapeRenderer;
            if (shapeRenderer != null) {
                shapeRenderer.dispose();
            }
            SquareActor.sharedShapeRenderer = null;
        }
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Actor
    public void draw(Batch batch, float parentAlpha) {
        if (batch == null) {
            return;
        }
        batch.end();
        ShapeRenderer shapeRenderer = INSTANCE.getShapeRenderer();
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.setTransformMatrix(batch.getTransformMatrix());
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(new Color(this.color.r, this.color.g, this.color.b, this.color.a * parentAlpha));
        shapeRenderer.identity();
        float f = 2;
        shapeRenderer.translate(this.x + (this.width / f), this.y + (this.height / f), 0.0f);
        shapeRenderer.rotate(0.0f, 0.0f, 1.0f, this.rotation);
        float f2 = this.cornerRadius;
        if (f2 > 0.0f) {
            float f3 = this.width;
            float f4 = this.height;
            drawRoundedRectangle(shapeRenderer, (-f3) / f, (-f4) / f, f3, f4, f2);
        } else {
            float f5 = this.width;
            float f6 = this.height;
            shapeRenderer.rect((-f5) / f, (-f6) / f, f5, f6);
        }
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
        batch.begin();
    }

    private final void drawRoundedRectangle(ShapeRenderer shapeRenderer, float rx, float ry, float rWidth, float rHeight, float radius) {
        float f = rx + radius;
        float f2 = 2 * radius;
        shapeRenderer.rect(f, ry, rWidth - f2, rHeight);
        float f3 = ry + radius;
        shapeRenderer.rect(rx, f3, rWidth, rHeight - f2);
        shapeRenderer.arc(f, f3, radius, 180.0f, 90.0f);
        float f4 = (rx + rWidth) - radius;
        shapeRenderer.arc(f4, f3, radius, 270.0f, 90.0f);
        float f5 = (ry + rHeight) - radius;
        shapeRenderer.arc(f, f5, radius, 90.0f, 90.0f);
        shapeRenderer.arc(f4, f5, radius, 0.0f, 90.0f);
    }
}
