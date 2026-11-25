package org.jetbrains.kotlin.build.report.metrics;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;

/* JADX INFO: compiled from: BuildAttributes.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010$\n\u0002\b\u0002\u0018\u0000 \u000f2\u00020\u0001:\u0001\u000fB\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00052\b\b\u0002\u0010\n\u001a\u00020\u0006J\u000e\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\u0000J\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u000eR\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/build/report/metrics/BuildAttributes;", "Ljava/io/Serializable;", "()V", "myAttributes", "Ljava/util/EnumMap;", "Lorg/jetbrains/kotlin/build/report/metrics/BuildAttribute;", "", ProductAction.ACTION_ADD, "", ASTExpr.DEFAULT_ATTRIBUTE_NAME_DEPRECATED, "count", "addAll", "other", "asMap", "", "Companion", "kotlin-build-statistics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BuildAttributes implements Serializable {
    public static final long serialVersionUID = 0;
    private final EnumMap<BuildAttribute, Integer> myAttributes = new EnumMap<>(BuildAttribute.class);

    public static /* synthetic */ void add$default(BuildAttributes buildAttributes, BuildAttribute buildAttribute, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = 1;
        }
        buildAttributes.add(buildAttribute, i);
    }

    public final void add(BuildAttribute attr, int count) {
        Intrinsics.checkNotNullParameter(attr, "attr");
        EnumMap<BuildAttribute, Integer> enumMap = this.myAttributes;
        enumMap.put(attr, Integer.valueOf(((Number) enumMap.getOrDefault(attr, 0)).intValue() + count));
    }

    public final void addAll(BuildAttributes other) {
        Intrinsics.checkNotNullParameter(other, "other");
        for (Map.Entry<BuildAttribute, Integer> entry : other.myAttributes.entrySet()) {
            BuildAttribute key = entry.getKey();
            Integer value = entry.getValue();
            Intrinsics.checkNotNull(key);
            Intrinsics.checkNotNull(value);
            add(key, value.intValue());
        }
    }

    public final Map<BuildAttribute, Integer> asMap() {
        return this.myAttributes;
    }
}
