package org.jetbrains.kotlin.fir.analysis.checkers.expression;

import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceElementKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirSmartCastExpression;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;

/* JADX INFO: compiled from: FirEqualityCompatibilityChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000e\n\u0000\b\u0002\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\b\u0010\u001b\u001a\u00020\u001cH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u000e\u001a\u00020\u000f8F¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u001b\u0010\u0014\u001a\u00020\u00058FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0016\u0010\u0017\u001a\u0004\b\u0015\u0010\rR\u0011\u0010\u0018\u001a\u00020\u000f8F¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u0011R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\r¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/ArgumentInfo;", "", "argument", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "userType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "originalType", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;Lorg/jetbrains/kotlin/fir/FirSession;)V", "getArgument", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getOriginalType", "()Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "originalTypeInfo", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/TypeInfo;", "getOriginalTypeInfo", "()Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/TypeInfo;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "smartCastType", "getSmartCastType", "smartCastType$delegate", "Lkotlin/Lazy;", "smartCastTypeInfo", "getSmartCastTypeInfo", "getUserType", "toString", "", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class ArgumentInfo {
    private final FirExpression argument;
    private final ConeKotlinType originalType;
    private final FirSession session;

    /* JADX INFO: renamed from: smartCastType$delegate, reason: from kotlin metadata */
    private final Lazy smartCastType;
    private final ConeKotlinType userType;

    public ArgumentInfo(FirExpression argument, ConeKotlinType userType, ConeKotlinType originalType, FirSession session) {
        Intrinsics.checkNotNullParameter(argument, "argument");
        Intrinsics.checkNotNullParameter(userType, "userType");
        Intrinsics.checkNotNullParameter(originalType, "originalType");
        Intrinsics.checkNotNullParameter(session, "session");
        this.argument = argument;
        this.userType = userType;
        this.originalType = originalType;
        this.session = session;
        this.smartCastType = LazyKt.lazy(new Function0<ConeKotlinType>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.expression.ArgumentInfo$smartCastType$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final ConeKotlinType invoke() {
                return !(this.this$0.getArgument() instanceof FirSmartCastExpression) ? this.this$0.getOriginalType() : TypeExpansionUtilsKt.fullyExpandedType(this.this$0.getUserType(), this.this$0.getSession());
            }
        });
    }

    public final FirExpression getArgument() {
        return this.argument;
    }

    public final ConeKotlinType getUserType() {
        return this.userType;
    }

    public final ConeKotlinType getOriginalType() {
        return this.originalType;
    }

    public final FirSession getSession() {
        return this.session;
    }

    public final ConeKotlinType getSmartCastType() {
        return (ConeKotlinType) this.smartCastType.getValue();
    }

    public final TypeInfo getOriginalTypeInfo() {
        return FirEqualityCompatibilityCheckerKt.toTypeInfo(this.originalType, this.session);
    }

    public final TypeInfo getSmartCastTypeInfo() {
        return FirEqualityCompatibilityCheckerKt.toTypeInfo(getSmartCastType(), this.session);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        KtSourceElement source = this.argument.getSource();
        return sb.append((Object) (source != null ? KtSourceElementKt.getText(source) : null)).append(" :: ").append(this.userType).toString();
    }
}
