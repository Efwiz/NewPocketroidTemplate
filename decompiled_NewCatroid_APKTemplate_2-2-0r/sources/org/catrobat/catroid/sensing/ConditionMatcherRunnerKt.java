package org.catrobat.catroid.sensing;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Polygon;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/sensing/ConditionMatcherRunnerKt.class
  classes14.dex
 */
/* JADX INFO: compiled from: ConditionMatcherRunner.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000.\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0014\u0010\u0004\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0002\u001a\u0014\u0010\u0006\u001a\u00020\u0007*\u00020\u00032\u0006\u0010\b\u001a\u00020\u0003H\u0002\u001a\f\u0010\t\u001a\u00020\u0007*\u00020\nH\u0002\u001a\u0015\u0010\u000b\u001a\u00020\f*\b\u0012\u0004\u0012\u00020\u000e0\r¢\u0006\u0002\u0010\u000f\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"EPSILON", "", "clearColor", "Lcom/badlogic/gdx/graphics/Color;", "absDiff", "f", "equalsColor", "", TypedValues.Custom.S_COLOR, "isOdd", "", "toBoundingRectangle", "Lorg/catrobat/catroid/sensing/BoundingRectangle;", "", "Lcom/badlogic/gdx/math/Polygon;", "([Lcom/badlogic/gdx/math/Polygon;)Lorg/catrobat/catroid/sensing/BoundingRectangle;", "catroid_apktemplateTemplateRelease"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ConditionMatcherRunnerKt {
    private static final float EPSILON = 0.03125f;
    private static final Color clearColor = new Color(1.0f, 1.0f, 1.0f, 0.0f);

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isOdd(int i) {
        return i % 2 == 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean equalsColor(Color color, Color color2) {
        return absDiff(color.r, color2.r) < EPSILON && absDiff(color.g, color2.g) < EPSILON && absDiff(color.b, color2.b) < EPSILON && !Intrinsics.areEqual(color, clearColor);
    }

    private static final float absDiff(float f, float f2) {
        return Math.abs(f - f2);
    }

    public static final BoundingRectangle toBoundingRectangle(Polygon[] polygonArr) {
        Intrinsics.checkNotNullParameter(polygonArr, "<this>");
        ArrayList arrayList = new ArrayList();
        for (Polygon polygon : polygonArr) {
            float[] vertices = polygon.getVertices();
            Intrinsics.checkNotNullExpressionValue(vertices, "getVertices(...)");
            CollectionsKt.addAll(arrayList, ArraysKt.asIterable(vertices));
        }
        return new BoundingRectangle(arrayList);
    }
}
