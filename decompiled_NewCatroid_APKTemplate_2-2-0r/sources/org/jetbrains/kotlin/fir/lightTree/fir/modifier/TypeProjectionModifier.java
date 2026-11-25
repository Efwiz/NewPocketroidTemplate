package org.jetbrains.kotlin.fir.lightTree.fir.modifier;

import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.kotlin.KtSourceElement;

/* JADX INFO: compiled from: TypeProjectionModifier.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u001b\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/fir/lightTree/fir/modifier/TypeProjectionModifier;", "Lorg/jetbrains/kotlin/fir/lightTree/fir/modifier/Modifier;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "varianceModifiers", "", "(Lorg/jetbrains/kotlin/KtSourceElement;J)V", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "light-tree2fir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TypeProjectionModifier extends Modifier {
    private final KtSourceElement source;

    public TypeProjectionModifier() {
        this(null, 0L, 3, null);
    }

    public /* synthetic */ TypeProjectionModifier(KtSourceElement ktSourceElement, long j, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : ktSourceElement, (i & 2) != 0 ? ModifierFlag.NONE.getValue() : j);
    }

    public final KtSourceElement getSource() {
        return this.source;
    }

    public TypeProjectionModifier(KtSourceElement ktSourceElement, long j) {
        super(j);
        this.source = ktSourceElement;
    }
}
