package org.jetbrains.kotlin.fir.analysis.checkers;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import org.antlr.stringtemplate.language.ASTExpr;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.types.ConeAttribute;
import org.jetbrains.kotlin.fir.types.FirTypeRef;

/* JADX INFO: compiled from: FirUpperBoundViolatedHelpers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0012\u0010\u0011\u001a\u00020\u00002\b\u0010\u0012\u001a\u0004\u0018\u00010\u0000H\u0016J\u0012\u0010\u0013\u001a\u00020\u00002\b\u0010\u0012\u001a\u0004\u0018\u00010\u0000H\u0016J\u0012\u0010\u0014\u001a\u00020\u00152\b\u0010\u0012\u001a\u0004\u0018\u00010\u0000H\u0016J\b\u0010\u0016\u001a\u00020\u0017H\u0016J\u0012\u0010\u0018\u001a\u00020\u00002\b\u0010\u0012\u001a\u0004\u0018\u00010\u0000H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u0005\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00000\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\t\u001a\u0004\u0018\u00010\n8F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0013\u0010\r\u001a\u0004\u0018\u00010\u000e8F¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/SourceAttribute;", "Lorg/jetbrains/kotlin/fir/types/ConeAttribute;", "data", "Lorg/jetbrains/kotlin/fir/analysis/checkers/FirTypeRefSource;", "(Lorg/jetbrains/kotlin/fir/analysis/checkers/FirTypeRefSource;)V", ASTExpr.DEFAULT_MAP_KEY_NAME, "Lkotlin/reflect/KClass;", "getKey", "()Lkotlin/reflect/KClass;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "typeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", ProductAction.ACTION_ADD, "other", "intersect", "isSubtypeOf", "", "toString", "", SchemaSymbols.ATTVAL_UNION, "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class SourceAttribute extends ConeAttribute<SourceAttribute> {
    private final FirTypeRefSource data;

    @Override // org.jetbrains.kotlin.fir.types.ConeAttribute
    public SourceAttribute add(SourceAttribute other) {
        return other == null ? this : other;
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeAttribute
    public KClass<? extends SourceAttribute> getKey() {
        return Reflection.getOrCreateKotlinClass(SourceAttribute.class);
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeAttribute
    public SourceAttribute intersect(SourceAttribute other) {
        return other == null ? this : other;
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeAttribute
    public boolean isSubtypeOf(SourceAttribute other) {
        return true;
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeAttribute
    public SourceAttribute union(SourceAttribute other) {
        return other == null ? this : other;
    }

    public SourceAttribute(FirTypeRefSource data) {
        Intrinsics.checkNotNullParameter(data, "data");
        this.data = data;
    }

    public final KtSourceElement getSource() {
        return this.data.getSource();
    }

    public final FirTypeRef getTypeRef() {
        return this.data.getTypeRef();
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeAttribute
    public String toString() {
        return "SourceAttribute: " + this.data;
    }
}
