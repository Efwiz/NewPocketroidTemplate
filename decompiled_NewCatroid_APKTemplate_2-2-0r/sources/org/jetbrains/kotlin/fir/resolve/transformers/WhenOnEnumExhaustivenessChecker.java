package org.jetbrains.kotlin.fir.resolve.transformers;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.diagnostics.WhenMissingCase;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirEnumEntry;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.expressions.FirEqualityOperatorCall;
import org.jetbrains.kotlin.fir.expressions.FirExpressionUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirOperation;
import org.jetbrains.kotlin.fir.expressions.FirWhenExpression;
import org.jetbrains.kotlin.fir.references.FirResolvedNamedReference;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;

/* JADX INFO: compiled from: FirWhenExhaustivenessTransformer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\bÂ\u0002\u0018\u00002\u00020\u0001:\u0001\u0010B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J.\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fH\u0016J\u0018\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/WhenOnEnumExhaustivenessChecker;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/WhenExhaustivenessChecker;", "()V", "computeMissingCases", "", "whenExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirWhenExpression;", "subjectType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", FirebaseAnalytics.Param.DESTINATION, "", "Lorg/jetbrains/kotlin/diagnostics/WhenMissingCase;", "isApplicable", "", "ConditionChecker", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class WhenOnEnumExhaustivenessChecker extends WhenExhaustivenessChecker {
    public static final WhenOnEnumExhaustivenessChecker INSTANCE = new WhenOnEnumExhaustivenessChecker();

    private WhenOnEnumExhaustivenessChecker() {
        super(null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.resolve.transformers.WhenExhaustivenessChecker
    public boolean isApplicable(ConeKotlinType subjectType, FirSession session) {
        Intrinsics.checkNotNullParameter(subjectType, "subjectType");
        Intrinsics.checkNotNullParameter(session, "session");
        FirClassifierSymbol<?> symbol = TypeUtilsKt.toSymbol(subjectType, session);
        FirRegularClassSymbol firRegularClassSymbol = symbol instanceof FirRegularClassSymbol ? (FirRegularClassSymbol) symbol : null;
        return firRegularClassSymbol != null && ((FirRegularClass) firRegularClassSymbol.getFir()).getClassKind() == ClassKind.ENUM_CLASS;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.resolve.transformers.WhenExhaustivenessChecker
    public void computeMissingCases(FirWhenExpression whenExpression, ConeKotlinType subjectType, FirSession session, Collection<WhenMissingCase> destination) {
        Intrinsics.checkNotNullParameter(whenExpression, "whenExpression");
        Intrinsics.checkNotNullParameter(subjectType, "subjectType");
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(destination, "destination");
        FirClassifierSymbol<?> symbol = TypeUtilsKt.toSymbol(subjectType, session);
        Intrinsics.checkNotNull(symbol, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol");
        List<FirDeclaration> declarations = ((FirRegularClass) ((FirRegularClassSymbol) symbol).getFir()).getDeclarations();
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (FirDeclaration firDeclaration : declarations) {
            FirEnumEntry firEnumEntry = firDeclaration instanceof FirEnumEntry ? (FirEnumEntry) firDeclaration : null;
            if (firEnumEntry != null) {
                linkedHashSet.add(firEnumEntry);
            }
        }
        LinkedHashSet linkedHashSet2 = new LinkedHashSet();
        whenExpression.accept(ConditionChecker.INSTANCE, linkedHashSet2);
        Iterator it = SetsKt.minus((Set) linkedHashSet, (Iterable) linkedHashSet2).iterator();
        while (it.hasNext()) {
            destination.add(new WhenMissingCase.EnumCheckIsMissing(((FirEnumEntry) it.next()).getSymbol().getCallableId()));
        }
    }

    /* JADX INFO: compiled from: FirWhenExhaustivenessTransformer.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÂ\u0002\u0018\u00002\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J\u001e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002H\u0016¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/WhenOnEnumExhaustivenessChecker$ConditionChecker;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/WhenExhaustivenessChecker$AbstractConditionChecker;", "", "Lorg/jetbrains/kotlin/fir/declarations/FirEnumEntry;", "()V", "visitEqualityOperatorCall", "", "equalityOperatorCall", "Lorg/jetbrains/kotlin/fir/expressions/FirEqualityOperatorCall;", "data", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class ConditionChecker extends WhenExhaustivenessChecker.AbstractConditionChecker<Set<FirEnumEntry>> {
        public static final ConditionChecker INSTANCE = new ConditionChecker();

        private ConditionChecker() {
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
        public /* bridge */ /* synthetic */ Unit visitEqualityOperatorCall(FirEqualityOperatorCall firEqualityOperatorCall, Object obj) {
            visitEqualityOperatorCall(firEqualityOperatorCall, (Set<FirEnumEntry>) obj);
            return Unit.INSTANCE;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void visitEqualityOperatorCall(FirEqualityOperatorCall equalityOperatorCall, Set<FirEnumEntry> data) {
            Intrinsics.checkNotNullParameter(equalityOperatorCall, "equalityOperatorCall");
            Intrinsics.checkNotNullParameter(data, "data");
            FirOperation operation = equalityOperatorCall.getOperation();
            if (operation == FirOperation.EQ || operation == FirOperation.IDENTITY) {
                FirResolvedNamedReference resolvedCallableReference = FirExpressionUtilKt.toResolvedCallableReference(equalityOperatorCall.getArgumentList().getArguments().get(1));
                FirBasedSymbol<?> resolvedSymbol = resolvedCallableReference != null ? resolvedCallableReference.getResolvedSymbol() : null;
                FirVariableSymbol firVariableSymbol = resolvedSymbol instanceof FirVariableSymbol ? (FirVariableSymbol) resolvedSymbol : null;
                if (firVariableSymbol == null) {
                    return;
                }
                D fir = firVariableSymbol.getFir();
                FirEnumEntry firEnumEntry = fir instanceof FirEnumEntry ? (FirEnumEntry) fir : null;
                if (firEnumEntry == null) {
                    return;
                }
                data.add(firEnumEntry);
            }
        }
    }
}
