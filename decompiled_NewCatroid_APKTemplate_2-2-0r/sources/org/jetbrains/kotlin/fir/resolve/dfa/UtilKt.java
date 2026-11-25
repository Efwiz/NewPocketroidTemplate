package org.jetbrains.kotlin.fir.resolve.dfa;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirVariable;
import org.jetbrains.kotlin.fir.expressions.FirCall;
import org.jetbrains.kotlin.fir.expressions.FirCheckNotNullCall;
import org.jetbrains.kotlin.fir.expressions.FirCheckedSafeCallSubject;
import org.jetbrains.kotlin.fir.expressions.FirDesugaredAssignmentValueReferenceExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpressionUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirOperation;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirResolvable;
import org.jetbrains.kotlin.fir.expressions.FirSafeCallExpression;
import org.jetbrains.kotlin.fir.expressions.FirSmartCastExpression;
import org.jetbrains.kotlin.fir.expressions.FirThisReceiverExpression;
import org.jetbrains.kotlin.fir.expressions.FirVariableAssignment;
import org.jetbrains.kotlin.fir.expressions.FirWhenExpression;
import org.jetbrains.kotlin.fir.expressions.FirWhenSubjectExpression;
import org.jetbrains.kotlin.fir.references.FirNamedReferenceWithCandidateBase;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.references.FirResolvedNamedReference;
import org.jetbrains.kotlin.fir.references.FirThisReference;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirSyntheticPropertySymbol;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeContext;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: util.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000:\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a\f\u0010\u0011\u001a\u00020\u0012*\u00020\u0013H\u0007\u001a\u001c\u0010\u0014\u001a\u00020\u0001*\u0004\u0018\u00010\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0001\u001a\f\u0010\u0019\u001a\u00020\t*\u00020\tH\u0007\u001a\u0018\u0010\u001a\u001a\b\u0012\u0002\b\u0003\u0018\u00010\b*\b\u0012\u0002\b\u0003\u0018\u00010\bH\u0002\"\u001e\u0010\u0000\u001a\u00020\u0001*\u00020\u00028FX\u0087\u0004¢\u0006\f\u0012\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006\"$\u0010\u0007\u001a\b\u0012\u0002\b\u0003\u0018\u00010\b*\u00020\t8FX\u0087\u0004¢\u0006\f\u0012\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\r\"$\u0010\u0007\u001a\b\u0012\u0002\b\u0003\u0018\u00010\b*\u00020\u000e8@X\u0081\u0004¢\u0006\f\u0012\u0004\b\n\u0010\u000f\u001a\u0004\b\f\u0010\u0010¨\u0006\u001b"}, d2 = {"coneType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getConeType$annotations", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;)V", "getConeType", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;)Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "Lorg/jetbrains/kotlin/fir/FirElement;", "getSymbol$annotations", "(Lorg/jetbrains/kotlin/fir/FirElement;)V", "getSymbol", "(Lorg/jetbrains/kotlin/fir/FirElement;)Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "Lorg/jetbrains/kotlin/fir/expressions/FirResolvable;", "(Lorg/jetbrains/kotlin/fir/expressions/FirResolvable;)V", "(Lorg/jetbrains/kotlin/fir/expressions/FirResolvable;)Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "isEq", "", "Lorg/jetbrains/kotlin/fir/expressions/FirOperation;", "smartCastedType", "Lorg/jetbrains/kotlin/fir/resolve/dfa/TypeStatement;", "context", "Lorg/jetbrains/kotlin/fir/types/ConeTypeContext;", "originalType", "unwrapElement", "unwrapFakeOverridesIfNecessary", "semantics"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class UtilKt {

    /* JADX INFO: compiled from: util.kt */
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
            $EnumSwitchMapping$0 = iArr;
        }
    }

    @DfaInternals
    public static /* synthetic */ void getConeType$annotations(FirExpression firExpression) {
    }

    @DfaInternals
    public static /* synthetic */ void getSymbol$annotations(FirElement firElement) {
    }

    @DfaInternals
    public static /* synthetic */ void getSymbol$annotations(FirResolvable firResolvable) {
    }

    public static final ConeKotlinType smartCastedType(TypeStatement typeStatement, ConeTypeContext context, ConeKotlinType originalType) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(originalType, "originalType");
        if (typeStatement == null || typeStatement.getExactType().isEmpty()) {
            return originalType;
        }
        List<? extends KotlinTypeMarker> mutableList = CollectionsKt.toMutableList((Collection) typeStatement.getExactType());
        mutableList.add(originalType);
        return context.intersectTypes(mutableList);
    }

    @DfaInternals
    public static final boolean isEq(FirOperation firOperation) {
        Intrinsics.checkNotNullParameter(firOperation, "<this>");
        int i = WhenMappings.$EnumSwitchMapping$0[firOperation.ordinal()];
        if (i == 1 || i == 2) {
            return true;
        }
        if (i == 3 || i == 4) {
            return false;
        }
        throw new IllegalArgumentException(firOperation + " should not be there");
    }

    public static final ConeKotlinType getConeType(FirExpression firExpression) {
        Intrinsics.checkNotNullParameter(firExpression, "<this>");
        return FirTypeUtilsKt.getConeType(firExpression.getTypeRef());
    }

    /* JADX WARN: Code duplicated, block: B:10:0x002b  */
    public static final FirBasedSymbol<?> getSymbol(FirElement firElement) {
        FirBasedSymbol<?> symbol;
        Intrinsics.checkNotNullParameter(firElement, "<this>");
        if (firElement instanceof FirResolvable) {
            symbol = unwrapFakeOverridesIfNecessary(getSymbol((FirResolvable) firElement));
        } else if (firElement instanceof FirVariableAssignment) {
            FirQualifiedAccessExpression firQualifiedAccessExpressionUnwrapLValue = FirExpressionUtilKt.unwrapLValue((FirVariableAssignment) firElement);
            if (firQualifiedAccessExpressionUnwrapLValue != null) {
                symbol = getSymbol((FirResolvable) firQualifiedAccessExpressionUnwrapLValue);
            } else {
                symbol = null;
            }
        } else if (firElement instanceof FirDeclaration) {
            symbol = unwrapFakeOverridesIfNecessary(((FirDeclaration) firElement).getSymbol());
        } else if (firElement instanceof FirWhenSubjectExpression) {
            FirExpression subject = ((FirWhenExpression) ((FirWhenSubjectExpression) firElement).getWhenRef().getValue()).getSubject();
            if (subject != null) {
                symbol = getSymbol(subject);
            } else {
                symbol = null;
            }
        } else if (firElement instanceof FirSafeCallExpression) {
            symbol = getSymbol(((FirSafeCallExpression) firElement).getSelector());
        } else if (firElement instanceof FirSmartCastExpression) {
            symbol = getSymbol(((FirSmartCastExpression) firElement).getOriginalExpression());
        } else if (firElement instanceof FirDesugaredAssignmentValueReferenceExpression) {
            symbol = getSymbol(((FirDesugaredAssignmentValueReferenceExpression) firElement).getExpressionRef().getValue());
        } else {
            symbol = null;
        }
        if (symbol == null) {
            return null;
        }
        FirExpression firExpression = firElement instanceof FirExpression ? (FirExpression) firElement : null;
        if (((firExpression != null ? FirExpressionUtilKt.unwrapSmartcastExpression(firExpression) : null) instanceof FirThisReceiverExpression) || !((symbol instanceof FirFunctionSymbol) || (symbol instanceof FirSyntheticPropertySymbol))) {
            return symbol;
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static final FirBasedSymbol<?> unwrapFakeOverridesIfNecessary(FirBasedSymbol<?> firBasedSymbol) {
        if (!(firBasedSymbol instanceof FirCallableSymbol)) {
            return firBasedSymbol;
        }
        FirCallableSymbol firCallableSymbol = (FirCallableSymbol) firBasedSymbol;
        if (firCallableSymbol.getDispatchReceiverType() == null) {
            return firBasedSymbol;
        }
        FirCallableDeclaration firCallableDeclaration = (FirCallableDeclaration) firCallableSymbol.getFir();
        while (true) {
            FirCallableDeclaration originalForSubstitutionOverrideAttr = (ClassMembersKt.isSubstitutionOverride(firCallableDeclaration) || (firCallableDeclaration.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firCallableDeclaration) : null;
            if (originalForSubstitutionOverrideAttr == null) {
                originalForSubstitutionOverrideAttr = ClassMembersKt.isIntersectionOverride(firCallableDeclaration) ? ClassMembersKt.getOriginalForIntersectionOverrideAttr(firCallableDeclaration) : null;
            }
            if (originalForSubstitutionOverrideAttr == null) {
                break;
            }
            firCallableDeclaration = originalForSubstitutionOverrideAttr;
        }
        FirCallableSymbol<? extends FirCallableDeclaration> symbol = firCallableDeclaration.getSymbol();
        if (symbol != null) {
            return symbol;
        }
        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol<*>");
    }

    public static final FirBasedSymbol<?> getSymbol(FirResolvable firResolvable) {
        Intrinsics.checkNotNullParameter(firResolvable, "<this>");
        FirReference calleeReference = firResolvable.getCalleeReference();
        if (calleeReference instanceof FirThisReference) {
            return ((FirThisReference) calleeReference).getBoundSymbol();
        }
        if (calleeReference instanceof FirResolvedNamedReference) {
            return ((FirResolvedNamedReference) calleeReference).getResolvedSymbol();
        }
        if (calleeReference instanceof FirNamedReferenceWithCandidateBase) {
            return ((FirNamedReferenceWithCandidateBase) calleeReference).getCandidateSymbol();
        }
        return null;
    }

    @DfaInternals
    public static final FirElement unwrapElement(FirElement firElement) {
        FirElement firElementUnwrapElement;
        Intrinsics.checkNotNullParameter(firElement, "<this>");
        if (firElement instanceof FirWhenSubjectExpression) {
            FirWhenExpression firWhenExpression = (FirWhenExpression) ((FirWhenSubjectExpression) firElement).getWhenRef().getValue();
            FirVariable subjectVariable = firWhenExpression.getSubjectVariable();
            FirExpression subject = subjectVariable != null ? subjectVariable : firWhenExpression.getSubject();
            return (subject == null || (firElementUnwrapElement = unwrapElement(subject)) == null) ? firElement : firElementUnwrapElement;
        }
        if (firElement instanceof FirSmartCastExpression) {
            return unwrapElement(((FirSmartCastExpression) firElement).getOriginalExpression());
        }
        if (firElement instanceof FirSafeCallExpression) {
            return unwrapElement(((FirSafeCallExpression) firElement).getSelector());
        }
        if (firElement instanceof FirCheckedSafeCallSubject) {
            return unwrapElement(((FirCheckedSafeCallSubject) firElement).getOriginalReceiverRef().getValue());
        }
        return firElement instanceof FirCheckNotNullCall ? unwrapElement((FirExpression) CollectionsKt.first((List) ((FirCall) firElement).getArgumentList().getArguments())) : firElement;
    }
}
