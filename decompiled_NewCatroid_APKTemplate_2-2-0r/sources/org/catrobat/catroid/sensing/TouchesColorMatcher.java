package org.catrobat.catroid.sensing;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/sensing/TouchesColorMatcher.class
  classes14.dex
 */
/* JADX INFO: compiled from: ConditionMatcherRunner.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0016R\u0016\u0010\u0002\u001a\n \u0006*\u0004\u0018\u00010\u00050\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lorg/catrobat/catroid/sensing/TouchesColorMatcher;", "Lorg/catrobat/catroid/sensing/ConditionMatcher;", TypedValues.Custom.S_COLOR, "", "(Ljava/lang/String;)V", "Lcom/badlogic/gdx/graphics/Color;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "matches", "", "x", "", "y", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TouchesColorMatcher extends ConditionMatcher {
    private final Color color;

    public TouchesColorMatcher(String color) {
        Intrinsics.checkNotNullParameter(color, "color");
        this.color = Color.valueOf(color);
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
        Color color2 = this.color;
        Intrinsics.checkNotNullExpressionValue(color2, "color");
        return ConditionMatcherRunnerKt.equalsColor(color, color2) && !Intrinsics.areEqual(new Color(pixel2), ConditionMatcherRunnerKt.clearColor);
    }
}
