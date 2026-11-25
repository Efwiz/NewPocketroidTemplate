package org.jetbrains.kotlin.fir.declarations;

import androidx.room.FtsOptions;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeBuiltinTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeStarProjection;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeConstructionUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.fir.types.impl.ConeClassLikeTypeImpl;
import org.jetbrains.kotlin.name.StandardClassIds;

/* JADX INFO: compiled from: OperatorFunctionChecks.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\bÂ\u0002\u0018\u00002\u00020\u0001:\u0002\u0019\u001aB\u0007\b\u0002¢\u0006\u0002\u0010\u0002J(\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u00122\u0018\u0010\u0013\u001a\u0014\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\u00170\u0014J(\u0010\u0018\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u00122\u0018\u0010\u0013\u001a\u0014\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00170\u0014R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0003\u0010\u0005R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\b\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0005R\u0011\u0010\n\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u0005R\u0011\u0010\f\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u0005R\u0011\u0010\u000e\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0005¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/Checks;", "", "()V", "isKProperty", "Lorg/jetbrains/kotlin/fir/declarations/Check;", "()Lorg/jetbrains/kotlin/fir/declarations/Check;", "kPropertyType", "Lorg/jetbrains/kotlin/fir/types/impl/ConeClassLikeTypeImpl;", "member", "getMember", "memberOrExtension", "getMemberOrExtension", "noDefaultAndVarargs", "getNoDefaultAndVarargs", "nonSuspend", "getNonSuspend", "full", CrashHianalyticsData.MESSAGE, "", "predicate", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/fir/FirSession;", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "", FtsOptions.TOKENIZER_SIMPLE, "Returns", "ValueParametersCount", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class Checks {
    public static final Checks INSTANCE;
    private static final Check isKProperty;
    private static final ConeClassLikeTypeImpl kPropertyType;
    private static final Check member;
    private static final Check memberOrExtension;
    private static final Check noDefaultAndVarargs;
    private static final Check nonSuspend;

    private Checks() {
    }

    public final Check simple(final String message, final Function2<? super FirSimpleFunction, ? super FirSession, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        return new Check() { // from class: org.jetbrains.kotlin.fir.declarations.Checks.simple.1
            @Override // org.jetbrains.kotlin.fir.declarations.Check
            public String check(FirSimpleFunction function, FirSession session, ScopeSession scopeSession) {
                Intrinsics.checkNotNullParameter(function, "function");
                Intrinsics.checkNotNullParameter(session, "session");
                String str = message;
                if (predicate.invoke(function, session).booleanValue()) {
                    return null;
                }
                return str;
            }
        };
    }

    public final Check full(final String message, final Function2<? super FirSession, ? super FirSimpleFunction, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        return new Check() { // from class: org.jetbrains.kotlin.fir.declarations.Checks.full.1
            @Override // org.jetbrains.kotlin.fir.declarations.Check
            public String check(FirSimpleFunction function, FirSession session, ScopeSession scopeSession) {
                Intrinsics.checkNotNullParameter(function, "function");
                Intrinsics.checkNotNullParameter(session, "session");
                String str = message;
                if (predicate.invoke(session, function).booleanValue()) {
                    return null;
                }
                return str;
            }
        };
    }

    static {
        Checks checks = new Checks();
        INSTANCE = checks;
        memberOrExtension = checks.simple("must be a member or an extension function", new Function2<FirSimpleFunction, FirSession, Boolean>() { // from class: org.jetbrains.kotlin.fir.declarations.Checks$memberOrExtension$1
            @Override // kotlin.jvm.functions.Function2
            public final Boolean invoke(FirSimpleFunction it, FirSession firSession) {
                Intrinsics.checkNotNullParameter(it, "it");
                Intrinsics.checkNotNullParameter(firSession, "<anonymous parameter 1>");
                return Boolean.valueOf((it.getDispatchReceiverType() == null && it.getReceiverParameter() == null) ? false : true);
            }
        });
        member = checks.simple("must be a member function", new Function2<FirSimpleFunction, FirSession, Boolean>() { // from class: org.jetbrains.kotlin.fir.declarations.Checks$member$1
            @Override // kotlin.jvm.functions.Function2
            public final Boolean invoke(FirSimpleFunction it, FirSession firSession) {
                Intrinsics.checkNotNullParameter(it, "it");
                Intrinsics.checkNotNullParameter(firSession, "<anonymous parameter 1>");
                return Boolean.valueOf(it.getDispatchReceiverType() != null);
            }
        });
        nonSuspend = checks.simple("must not be suspend", new Function2<FirSimpleFunction, FirSession, Boolean>() { // from class: org.jetbrains.kotlin.fir.declarations.Checks$nonSuspend$1
            @Override // kotlin.jvm.functions.Function2
            public final Boolean invoke(FirSimpleFunction it, FirSession firSession) {
                Intrinsics.checkNotNullParameter(it, "it");
                Intrinsics.checkNotNullParameter(firSession, "<anonymous parameter 1>");
                return Boolean.valueOf(!it.getStatus().isSuspend());
            }
        });
        noDefaultAndVarargs = checks.simple("should not have varargs or parameters with default values", new Function2<FirSimpleFunction, FirSession, Boolean>() { // from class: org.jetbrains.kotlin.fir.declarations.Checks$noDefaultAndVarargs$1
            @Override // kotlin.jvm.functions.Function2
            public final Boolean invoke(FirSimpleFunction it, FirSession firSession) {
                Intrinsics.checkNotNullParameter(it, "it");
                Intrinsics.checkNotNullParameter(firSession, "<anonymous parameter 1>");
                List<FirValueParameter> valueParameters = it.getValueParameters();
                boolean z = true;
                if (!(valueParameters instanceof Collection) || !valueParameters.isEmpty()) {
                    for (FirValueParameter firValueParameter : valueParameters) {
                        if (firValueParameter.getDefaultValue() != null || firValueParameter.getIsVararg()) {
                            z = false;
                            break;
                        }
                    }
                }
                return Boolean.valueOf(z);
            }
        });
        kPropertyType = new ConeClassLikeTypeImpl(TypeConstructionUtilsKt.toLookupTag(StandardClassIds.INSTANCE.getKProperty()), new ConeStarProjection[]{ConeStarProjection.INSTANCE}, false, null, 8, null);
        isKProperty = checks.full("second parameter must be of type KProperty<*> or its supertype", new Function2<FirSession, FirSimpleFunction, Boolean>() { // from class: org.jetbrains.kotlin.fir.declarations.Checks.isKProperty.1
            @Override // kotlin.jvm.functions.Function2
            public final Boolean invoke(FirSession session, FirSimpleFunction function) {
                FirTypeRef returnTypeRef;
                ConeKotlinType coneType;
                Intrinsics.checkNotNullParameter(session, "session");
                Intrinsics.checkNotNullParameter(function, "function");
                FirValueParameter firValueParameter = (FirValueParameter) CollectionsKt.getOrNull(function.getValueParameters(), 1);
                if (firValueParameter != null && (returnTypeRef = firValueParameter.getReturnTypeRef()) != null && (coneType = FirTypeUtilsKt.getConeType(returnTypeRef)) != null) {
                    return Boolean.valueOf(TypeUtilsKt.isSubtypeOf(Checks.kPropertyType, coneType, session, true));
                }
                return false;
            }
        });
    }

    public final Check getMemberOrExtension() {
        return memberOrExtension;
    }

    public final Check getMember() {
        return member;
    }

    public final Check getNonSuspend() {
        return nonSuspend;
    }

    /* JADX INFO: compiled from: OperatorFunctionChecks.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000bJ\u000e\u0010\f\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000bR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/Checks$ValueParametersCount;", "", "()V", "none", "Lorg/jetbrains/kotlin/fir/declarations/Check;", "getNone", "()Lorg/jetbrains/kotlin/fir/declarations/Check;", "single", "getSingle", "atLeast", "n", "", "exactly", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ValueParametersCount {
        public static final ValueParametersCount INSTANCE = new ValueParametersCount();
        private static final Check single = Checks.INSTANCE.simple("must have a single value parameter", new Function2<FirSimpleFunction, FirSession, Boolean>() { // from class: org.jetbrains.kotlin.fir.declarations.Checks$ValueParametersCount$single$1
            @Override // kotlin.jvm.functions.Function2
            public final Boolean invoke(FirSimpleFunction it, FirSession firSession) {
                Intrinsics.checkNotNullParameter(it, "it");
                Intrinsics.checkNotNullParameter(firSession, "<anonymous parameter 1>");
                return Boolean.valueOf(it.getValueParameters().size() == 1);
            }
        });
        private static final Check none = Checks.INSTANCE.simple("must have no value parameters", new Function2<FirSimpleFunction, FirSession, Boolean>() { // from class: org.jetbrains.kotlin.fir.declarations.Checks$ValueParametersCount$none$1
            @Override // kotlin.jvm.functions.Function2
            public final Boolean invoke(FirSimpleFunction it, FirSession firSession) {
                Intrinsics.checkNotNullParameter(it, "it");
                Intrinsics.checkNotNullParameter(firSession, "<anonymous parameter 1>");
                return Boolean.valueOf(it.getValueParameters().isEmpty());
            }
        });

        private ValueParametersCount() {
        }

        public final Check atLeast(final int n) {
            return Checks.INSTANCE.simple("must have at least " + n + " value parameter" + (n > 1 ? "s" : ""), new Function2<FirSimpleFunction, FirSession, Boolean>() { // from class: org.jetbrains.kotlin.fir.declarations.Checks$ValueParametersCount$atLeast$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Boolean invoke(FirSimpleFunction it, FirSession firSession) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    Intrinsics.checkNotNullParameter(firSession, "<anonymous parameter 1>");
                    return Boolean.valueOf(it.getValueParameters().size() >= n);
                }
            });
        }

        public final Check exactly(final int n) {
            return Checks.INSTANCE.simple("must have exactly " + n + " value parameters", new Function2<FirSimpleFunction, FirSession, Boolean>() { // from class: org.jetbrains.kotlin.fir.declarations.Checks$ValueParametersCount$exactly$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Boolean invoke(FirSimpleFunction it, FirSession firSession) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    Intrinsics.checkNotNullParameter(firSession, "<anonymous parameter 1>");
                    return Boolean.valueOf(it.getValueParameters().size() == n);
                }
            });
        }

        public final Check getSingle() {
            return single;
        }

        public final Check getNone() {
            return none;
        }
    }

    /* JADX INFO: compiled from: OperatorFunctionChecks.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006R\u0011\u0010\t\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0006¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/Checks$Returns;", "", "()V", "boolean", "Lorg/jetbrains/kotlin/fir/declarations/Check;", "getBoolean", "()Lorg/jetbrains/kotlin/fir/declarations/Check;", "int", "getInt", "unit", "getUnit", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Returns {
        public static final Returns INSTANCE = new Returns();
        private static final Check boolean = Checks.INSTANCE.simple("must return Boolean", new Function2<FirSimpleFunction, FirSession, Boolean>() { // from class: org.jetbrains.kotlin.fir.declarations.Checks$Returns$boolean$1
            @Override // kotlin.jvm.functions.Function2
            public final Boolean invoke(FirSimpleFunction it, FirSession session) {
                Intrinsics.checkNotNullParameter(it, "it");
                Intrinsics.checkNotNullParameter(session, "session");
                return Boolean.valueOf(ConeBuiltinTypeUtilsKt.isBoolean(TypeExpansionUtilsKt.fullyExpandedType(FirTypeUtilsKt.getConeType(it.getReturnTypeRef()), session)));
            }
        });
        private static final Check int = Checks.INSTANCE.simple("must return Int", new Function2<FirSimpleFunction, FirSession, Boolean>() { // from class: org.jetbrains.kotlin.fir.declarations.Checks$Returns$int$1
            @Override // kotlin.jvm.functions.Function2
            public final Boolean invoke(FirSimpleFunction it, FirSession session) {
                Intrinsics.checkNotNullParameter(it, "it");
                Intrinsics.checkNotNullParameter(session, "session");
                return Boolean.valueOf(ConeBuiltinTypeUtilsKt.isInt(TypeExpansionUtilsKt.fullyExpandedType(FirTypeUtilsKt.getConeType(it.getReturnTypeRef()), session)));
            }
        });
        private static final Check unit = Checks.INSTANCE.simple("must return Unit", new Function2<FirSimpleFunction, FirSession, Boolean>() { // from class: org.jetbrains.kotlin.fir.declarations.Checks$Returns$unit$1
            @Override // kotlin.jvm.functions.Function2
            public final Boolean invoke(FirSimpleFunction it, FirSession session) {
                Intrinsics.checkNotNullParameter(it, "it");
                Intrinsics.checkNotNullParameter(session, "session");
                return Boolean.valueOf(ConeBuiltinTypeUtilsKt.isUnit(TypeExpansionUtilsKt.fullyExpandedType(FirTypeUtilsKt.getConeType(it.getReturnTypeRef()), session)));
            }
        });

        private Returns() {
        }

        public final Check getBoolean() {
            return boolean;
        }

        public final Check getInt() {
            return int;
        }

        public final Check getUnit() {
            return unit;
        }
    }

    public final Check getNoDefaultAndVarargs() {
        return noDefaultAndVarargs;
    }

    public final Check isKProperty() {
        return isKProperty;
    }
}
