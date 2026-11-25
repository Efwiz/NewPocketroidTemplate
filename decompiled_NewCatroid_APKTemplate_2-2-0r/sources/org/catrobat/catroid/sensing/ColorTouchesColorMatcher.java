package org.catrobat.catroid.sensing;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/sensing/ColorTouchesColorMatcher.class
  classes14.dex
 */
/* JADX INFO: compiled from: ConditionMatcherRunner.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000bH\u0016R\u0016\u0010\u0002\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0004\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/catrobat/catroid/sensing/ColorTouchesColorMatcher;", "Lorg/catrobat/catroid/sensing/ConditionMatcher;", "spriteColor", "", "stageColor", "(Ljava/lang/String;Ljava/lang/String;)V", "Lcom/badlogic/gdx/graphics/Color;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "matches", "", "x", "", "y", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ColorTouchesColorMatcher extends ConditionMatcher {
    private final Color spriteColor;
    private final Color stageColor;

    public ColorTouchesColorMatcher(String spriteColor, String stageColor) {
        Intrinsics.checkNotNullParameter(spriteColor, "spriteColor");
        Intrinsics.checkNotNullParameter(stageColor, "stageColor");
        this.spriteColor = Color.valueOf(spriteColor);
        this.stageColor = Color.valueOf(stageColor);
    }

    @Override // org.catrobat.catroid.sensing.ConditionMatcher
    public boolean matches(int x, int y) {
        Pixmap stagePixmap = getStagePixmap();
        if (stagePixmap == null) {
            return false;
        }
        int pixel = stagePixmap.getPixel(x, y);
        Pixmap spritePixmap = getSpritePixmap();
        if (spritePixmap == null) {
            return false;
        }
        int pixel2 = spritePixmap.getPixel(x, y);
        Color color = new Color(pixel);
        Color stageColor = this.stageColor;
        Intrinsics.checkNotNullExpressionValue(stageColor, "stageColor");
        if (!ConditionMatcherRunnerKt.equalsColor(color, stageColor)) {
            return false;
        }
        Color color2 = new Color(pixel2);
        Color spriteColor = this.spriteColor;
        Intrinsics.checkNotNullExpressionValue(spriteColor, "spriteColor");
        return ConditionMatcherRunnerKt.equalsColor(color2, spriteColor);
    }
}
