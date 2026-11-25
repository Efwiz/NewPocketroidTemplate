package org.jetbrains.kotlin.fir.resolve.transformers;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.reandroid.arsc.chunk.OverlayablePolicy;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.diagnostics.WhenMissingCase;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.DeclarationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirEnumEntry;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.SealedClassInheritorsKt;
import org.jetbrains.kotlin.fir.expressions.FirEqualityOperatorCall;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpressionUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirOperation;
import org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier;
import org.jetbrains.kotlin.fir.expressions.FirTypeOperatorCall;
import org.jetbrains.kotlin.fir.expressions.FirWhenExpression;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProviderKt;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: FirWhenExhaustivenessTransformer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0002\b\u0003\bÂ\u0002\u0018\u00002\u00020\u0001:\u0002\u0015\u0016B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J.\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fH\u0016J\u0018\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\"\u0010\u0010\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00120\u0011*\u0006\u0012\u0002\b\u00030\u00122\u0006\u0010\t\u001a\u00020\nH\u0002J*\u0010\u0013\u001a\u00020\u0004*\u0006\u0012\u0002\b\u00030\u00122\u0010\u0010\u000b\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00120\u00142\u0006\u0010\t\u001a\u00020\nH\u0002¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/WhenOnSealedClassExhaustivenessChecker;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/WhenExhaustivenessChecker;", "()V", "computeMissingCases", "", "whenExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirWhenExpression;", "subjectType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", FirebaseAnalytics.Param.DESTINATION, "", "Lorg/jetbrains/kotlin/diagnostics/WhenMissingCase;", "isApplicable", "", "collectAllSubclasses", "", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "collectAllSubclassesTo", "", "ConditionChecker", "Flags", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class WhenOnSealedClassExhaustivenessChecker extends WhenExhaustivenessChecker {
    public static final WhenOnSealedClassExhaustivenessChecker INSTANCE = new WhenOnSealedClassExhaustivenessChecker();

    private WhenOnSealedClassExhaustivenessChecker() {
        super(null);
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.WhenExhaustivenessChecker
    public boolean isApplicable(ConeKotlinType subjectType, FirSession session) {
        Intrinsics.checkNotNullParameter(subjectType, "subjectType");
        Intrinsics.checkNotNullParameter(session, "session");
        FirClassifierSymbol<?> symbol = TypeUtilsKt.toSymbol(subjectType, session);
        Object fir = symbol != null ? symbol.getFir() : null;
        FirRegularClass firRegularClass = fir instanceof FirRegularClass ? (FirRegularClass) fir : null;
        return (firRegularClass != null ? firRegularClass.getStatus().getModality() : null) == Modality.SEALED;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.resolve.transformers.WhenExhaustivenessChecker
    public void computeMissingCases(FirWhenExpression whenExpression, ConeKotlinType subjectType, FirSession session, Collection<WhenMissingCase> destination) {
        Set<FirBasedSymbol<?>> setCollectAllSubclasses;
        WhenMissingCase.EnumCheckIsMissing enumCheckIsMissing;
        Intrinsics.checkNotNullParameter(whenExpression, "whenExpression");
        Intrinsics.checkNotNullParameter(subjectType, "subjectType");
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(destination, "destination");
        FirClassifierSymbol<?> symbol = TypeUtilsKt.toSymbol(subjectType, session);
        if (symbol == null || (setCollectAllSubclasses = collectAllSubclasses(symbol, session)) == null) {
            return;
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        whenExpression.accept(ConditionChecker.INSTANCE, new Flags(setCollectAllSubclasses, linkedHashSet, session));
        for (FirBasedSymbol firBasedSymbol : SetsKt.minus((Set) setCollectAllSubclasses, (Iterable) linkedHashSet)) {
            if (firBasedSymbol instanceof FirClassSymbol) {
                FirClassSymbol firClassSymbol = (FirClassSymbol) firBasedSymbol;
                enumCheckIsMissing = new WhenMissingCase.IsTypeCheckIsMissing(firClassSymbol.getClassId(), ((FirClass) firClassSymbol.getFir()).getClassKind().isSingleton());
            } else {
                enumCheckIsMissing = firBasedSymbol instanceof FirVariableSymbol ? new WhenMissingCase.EnumCheckIsMissing(((FirVariableSymbol) firBasedSymbol).getCallableId()) : null;
            }
            if (enumCheckIsMissing != null) {
                destination.add(enumCheckIsMissing);
            }
        }
    }

    /* JADX INFO: compiled from: FirWhenExhaustivenessTransformer.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0002\u0018\u00002\u00020\u0001B1\u0012\u0010\u0010\u0002\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00040\u0003\u0012\u0010\u0010\u0005\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00040\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tR\u001b\u0010\u0002\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u001b\u0010\u0005\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00040\u0006¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000bR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/WhenOnSealedClassExhaustivenessChecker$Flags;", "", "allSubclasses", "", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "checkedSubclasses", "", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Ljava/util/Set;Ljava/util/Set;Lorg/jetbrains/kotlin/fir/FirSession;)V", "getAllSubclasses", "()Ljava/util/Set;", "getCheckedSubclasses", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class Flags {
        private final Set<FirBasedSymbol<?>> allSubclasses;
        private final Set<FirBasedSymbol<?>> checkedSubclasses;
        private final FirSession session;

        /* JADX WARN: Multi-variable type inference failed */
        public Flags(Set<? extends FirBasedSymbol<?>> allSubclasses, Set<FirBasedSymbol<?>> checkedSubclasses, FirSession session) {
            Intrinsics.checkNotNullParameter(allSubclasses, "allSubclasses");
            Intrinsics.checkNotNullParameter(checkedSubclasses, "checkedSubclasses");
            Intrinsics.checkNotNullParameter(session, "session");
            this.allSubclasses = allSubclasses;
            this.checkedSubclasses = checkedSubclasses;
            this.session = session;
        }

        public final Set<FirBasedSymbol<?>> getAllSubclasses() {
            return this.allSubclasses;
        }

        public final Set<FirBasedSymbol<?>> getCheckedSubclasses() {
            return this.checkedSubclasses;
        }

        public final FirSession getSession() {
            return this.session;
        }
    }

    /* JADX INFO: compiled from: FirWhenExhaustivenessTransformer.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\bÂ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J$\u0010\u0004\u001a\u00020\u00052\n\u0010\u0006\u001a\u0006\u0012\u0002\b\u00030\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0002H\u0002J\u0018\u0010\u000b\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0002H\u0016J\u0018\u0010\u000f\u001a\u00020\u00052\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u000e\u001a\u00020\u0002H\u0016¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/WhenOnSealedClassExhaustivenessChecker$ConditionChecker;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/WhenExhaustivenessChecker$AbstractConditionChecker;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/WhenOnSealedClassExhaustivenessChecker$Flags;", "()V", "processBranch", "", "symbolToCheck", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "isNegated", "", OverlayablePolicy.NAME_flags, "visitEqualityOperatorCall", "equalityOperatorCall", "Lorg/jetbrains/kotlin/fir/expressions/FirEqualityOperatorCall;", "data", "visitTypeOperatorCall", "typeOperatorCall", "Lorg/jetbrains/kotlin/fir/expressions/FirTypeOperatorCall;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class ConditionChecker extends WhenExhaustivenessChecker.AbstractConditionChecker<Flags> {
        public static final ConditionChecker INSTANCE = new ConditionChecker();

        /* JADX INFO: compiled from: FirWhenExhaustivenessTransformer.kt */
        @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
        public /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[FirOperation.values().length];
                try {
                    iArr[FirOperation.EQ.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[FirOperation.IDENTITY.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                try {
                    iArr[FirOperation.NOT_EQ.ordinal()] = 3;
                } catch (NoSuchFieldError unused3) {
                }
                try {
                    iArr[FirOperation.NOT_IDENTITY.ordinal()] = 4;
                } catch (NoSuchFieldError unused4) {
                }
                try {
                    iArr[FirOperation.IS.ordinal()] = 5;
                } catch (NoSuchFieldError unused5) {
                }
                try {
                    iArr[FirOperation.NOT_IS.ordinal()] = 6;
                } catch (NoSuchFieldError unused6) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        private ConditionChecker() {
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
        public /* bridge */ /* synthetic */ Unit visitEqualityOperatorCall(FirEqualityOperatorCall firEqualityOperatorCall, Object obj) {
            visitEqualityOperatorCall(firEqualityOperatorCall, (Flags) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
        public /* bridge */ /* synthetic */ Unit visitTypeOperatorCall(FirTypeOperatorCall firTypeOperatorCall, Object obj) {
            visitTypeOperatorCall(firTypeOperatorCall, (Flags) obj);
            return Unit.INSTANCE;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void visitEqualityOperatorCall(FirEqualityOperatorCall equalityOperatorCall, Flags data) {
            boolean z;
            FirBasedSymbol<?> firBasedSymbol;
            Intrinsics.checkNotNullParameter(equalityOperatorCall, "equalityOperatorCall");
            Intrinsics.checkNotNullParameter(data, "data");
            int i = WhenMappings.$EnumSwitchMapping$0[equalityOperatorCall.getOperation().ordinal()];
            if (i == 1 || i == 2) {
                z = false;
            } else if (i != 3 && i != 4) {
                return;
            } else {
                z = true;
            }
            FirExpression firExpression = equalityOperatorCall.getArgumentList().getArguments().get(1);
            FirBasedSymbol<?> companionObjectSymbol = null;
            if (firExpression instanceof FirResolvedQualifier) {
                FirClassLikeSymbol<?> symbol = ((FirResolvedQualifier) firExpression).getSymbol();
                FirRegularClassSymbol firRegularClassSymbol = symbol instanceof FirRegularClassSymbol ? (FirRegularClassSymbol) symbol : null;
                FirRegularClass firRegularClass = firRegularClassSymbol != null ? (FirRegularClass) firRegularClassSymbol.getFir() : null;
                if ((firRegularClass != null ? firRegularClass.getClassKind() : null) == ClassKind.OBJECT) {
                    companionObjectSymbol = firRegularClass.getSymbol();
                } else if (firRegularClass != null) {
                    companionObjectSymbol = firRegularClass.getCompanionObjectSymbol();
                }
                firBasedSymbol = companionObjectSymbol;
            } else {
                FirCallableSymbol<?> resolvedCallableSymbol = FirExpressionUtilKt.toResolvedCallableSymbol(firExpression);
                if (resolvedCallableSymbol != null && (resolvedCallableSymbol.getFir() instanceof FirEnumEntry)) {
                    companionObjectSymbol = resolvedCallableSymbol;
                }
                firBasedSymbol = companionObjectSymbol;
            }
            if (firBasedSymbol == null) {
                return;
            }
            processBranch(firBasedSymbol, z, data);
        }

        public void visitTypeOperatorCall(FirTypeOperatorCall typeOperatorCall, Flags data) {
            boolean z;
            Intrinsics.checkNotNullParameter(typeOperatorCall, "typeOperatorCall");
            Intrinsics.checkNotNullParameter(data, "data");
            int i = WhenMappings.$EnumSwitchMapping$0[typeOperatorCall.getOperation().ordinal()];
            if (i == 5) {
                z = false;
            } else if (i != 6) {
                return;
            } else {
                z = true;
            }
            FirClassifierSymbol<?> symbol = TypeUtilsKt.toSymbol(TypeExpansionUtilsKt.fullyExpandedType(FirTypeUtilsKt.getConeType(typeOperatorCall.getConversionTypeRef()), data.getSession()), data.getSession());
            if (symbol == null) {
                return;
            }
            processBranch(symbol, z, data);
        }

        private final void processBranch(FirBasedSymbol<?> symbolToCheck, boolean isNegated, Flags flags) {
            Set setCollectAllSubclasses = WhenOnSealedClassExhaustivenessChecker.INSTANCE.collectAllSubclasses(symbolToCheck, flags.getSession());
            Set set = setCollectAllSubclasses;
            if ((set instanceof Collection) && set.isEmpty()) {
                return;
            }
            Iterator it = set.iterator();
            while (it.hasNext()) {
                if (flags.getAllSubclasses().contains((FirBasedSymbol) it.next())) {
                    if (isNegated) {
                        setCollectAllSubclasses = SetsKt.minus((Set) flags.getAllSubclasses(), (Iterable) set);
                    }
                    flags.getCheckedSubclasses().addAll(setCollectAllSubclasses);
                    return;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Set<FirBasedSymbol<?>> collectAllSubclasses(FirBasedSymbol<?> firBasedSymbol, FirSession firSession) {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        INSTANCE.collectAllSubclassesTo(firBasedSymbol, linkedHashSet, firSession);
        return linkedHashSet;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void collectAllSubclassesTo(FirBasedSymbol<?> firBasedSymbol, Set<FirBasedSymbol<?>> set, FirSession firSession) {
        if (!(firBasedSymbol instanceof FirRegularClassSymbol)) {
            set.add(firBasedSymbol);
            return;
        }
        FirRegularClassSymbol firRegularClassSymbol = (FirRegularClassSymbol) firBasedSymbol;
        if (((FirMemberDeclaration) firRegularClassSymbol.getFir()).getStatus().getModality() != Modality.SEALED) {
            if (((FirRegularClass) firRegularClassSymbol.getFir()).getClassKind() != ClassKind.ENUM_CLASS) {
                set.add(firBasedSymbol);
                return;
            }
            Iterator<T> it = DeclarationUtilsKt.collectEnumEntries((FirClass) firRegularClassSymbol.getFir()).iterator();
            while (it.hasNext()) {
                set.add(((FirEnumEntry) it.next()).getSymbol());
            }
            return;
        }
        Iterator<T> it2 = SealedClassInheritorsKt.getSealedClassInheritors((FirRegularClass) firRegularClassSymbol.getFir(), firSession).iterator();
        while (it2.hasNext()) {
            FirClassLikeSymbol<?> classLikeSymbolByClassId = FirSymbolProviderKt.getSymbolProvider(firSession).getClassLikeSymbolByClassId((ClassId) it2.next());
            FirRegularClassSymbol firRegularClassSymbol2 = classLikeSymbolByClassId instanceof FirRegularClassSymbol ? (FirRegularClassSymbol) classLikeSymbolByClassId : null;
            if (firRegularClassSymbol2 != null) {
                INSTANCE.collectAllSubclassesTo(firRegularClassSymbol2, set, firSession);
            }
        }
    }
}
