package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory1;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousObject;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.expressions.FirBlock;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirReturnExpression;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.expressions.impl.FirSingleExpressionBlock;
import org.jetbrains.kotlin.fir.symbols.impl.FirAnonymousObjectSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeProjectionKt;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;

/* JADX INFO: compiled from: FirAmbiguousAnonymousTypeChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J*\u0010\u0011\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0002R\u001a\u0010\u0005\u001a\u0004\u0018\u00010\u0006*\u00020\u00078BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirAmbiguousAnonymousTypeChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirBasicDeclarationChecker;", "()V", "singleExpressionType", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "getSingleExpressionType", "(Lorg/jetbrains/kotlin/fir/expressions/FirBlock;)Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkTypeAndArguments", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "reportOn", "Lorg/jetbrains/kotlin/KtSourceElement;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirAmbiguousAnonymousTypeChecker extends FirDeclarationChecker<FirDeclaration> {
    public static final FirAmbiguousAnonymousTypeChecker INSTANCE = new FirAmbiguousAnonymousTypeChecker();

    private FirAmbiguousAnonymousTypeChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirDeclaration declaration, CheckerContext context, DiagnosticReporter reporter) {
        FirBlock body;
        FirTypeRef typeRef;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if ((declaration instanceof FirFunction) || (declaration instanceof FirProperty)) {
            if (!(declaration instanceof FirCallableDeclaration)) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            FirCallableDeclaration firCallableDeclaration = (FirCallableDeclaration) declaration;
            if (DeclarationUtilsKt.getHasExplicitReturnType(firCallableDeclaration.getSymbol())) {
                return;
            }
            List<FirDeclaration> containingDeclarations = context.getContainingDeclarations();
            if (!(containingDeclarations instanceof Collection) || !containingDeclarations.isEmpty()) {
                for (FirDeclaration firDeclaration : containingDeclarations) {
                    if (DeclarationUtilsKt.isLocalMember(firDeclaration) || (firDeclaration instanceof FirAnonymousObject)) {
                        return;
                    }
                }
            }
            if (TypeUtilsKt.shouldApproximateAnonymousTypesOfNonLocalDeclaration(TypeUtilsKt.visibilityForApproximation(declaration, (FirDeclaration) CollectionsKt.lastOrNull((List) context.getContainingDeclarations())), ((FirMemberDeclaration) declaration).getStatus().isInline())) {
                FirTypeRef singleExpressionType = null;
                if (firCallableDeclaration instanceof FirProperty) {
                    FirProperty firProperty = (FirProperty) declaration;
                    FirExpression initializer = firProperty.getInitializer();
                    if (initializer == null || (typeRef = initializer.getTypeRef()) == null) {
                        FirPropertyAccessor getter = firProperty.getGetter();
                        if (getter != null && (body = getter.getBody()) != null) {
                            singleExpressionType = getSingleExpressionType(body);
                        }
                    } else {
                        singleExpressionType = typeRef;
                    }
                } else {
                    if (!(firCallableDeclaration instanceof FirFunction)) {
                        throw new IllegalStateException("Should not be there".toString());
                    }
                    FirBlock body2 = ((FirFunction) declaration).getBody();
                    if (body2 != null) {
                        singleExpressionType = getSingleExpressionType(body2);
                    }
                }
                if (singleExpressionType == null) {
                    return;
                }
                checkTypeAndArguments(FirTypeUtilsKt.getConeType(singleExpressionType), context, reporter, declaration.getSource());
            }
        }
    }

    private final void checkTypeAndArguments(ConeKotlinType type, CheckerContext context, DiagnosticReporter reporter, KtSourceElement reportOn) {
        FirClassifierSymbol<?> symbol = TypeUtilsKt.toSymbol(type, context.getSession());
        if (symbol instanceof FirAnonymousObjectSymbol) {
            FirAnonymousObjectSymbol firAnonymousObjectSymbol = (FirAnonymousObjectSymbol) symbol;
            if (firAnonymousObjectSymbol.getResolvedSuperTypeRefs().size() > 1) {
                KtSourceElement ktSourceElement = reportOn;
                KtDiagnosticFactory1<Collection<ConeKotlinType>> ambiguous_anonymous_type_inferred = FirErrors.INSTANCE.getAMBIGUOUS_ANONYMOUS_TYPE_INFERRED();
                List<FirResolvedTypeRef> resolvedSuperTypeRefs = firAnonymousObjectSymbol.getResolvedSuperTypeRefs();
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(resolvedSuperTypeRefs, 10));
                Iterator<T> it = resolvedSuperTypeRefs.iterator();
                while (it.hasNext()) {
                    arrayList.add(FirTypeUtilsKt.getConeType((FirResolvedTypeRef) it.next()));
                }
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, ktSourceElement, ambiguous_anonymous_type_inferred, arrayList, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
            }
        }
        for (ConeTypeProjection coneTypeProjection : type.getTypeArguments()) {
            ConeKotlinType type2 = ConeTypeProjectionKt.getType(coneTypeProjection);
            if (type2 != null) {
                checkTypeAndArguments(type2, context, reporter, reportOn);
            }
        }
    }

    private final FirTypeRef getSingleExpressionType(FirBlock firBlock) {
        FirExpression result;
        FirSingleExpressionBlock firSingleExpressionBlock = firBlock instanceof FirSingleExpressionBlock ? (FirSingleExpressionBlock) firBlock : null;
        FirStatement statement = firSingleExpressionBlock != null ? firSingleExpressionBlock.getStatement() : null;
        FirReturnExpression firReturnExpression = statement instanceof FirReturnExpression ? (FirReturnExpression) statement : null;
        if (firReturnExpression == null || (result = firReturnExpression.getResult()) == null) {
            return null;
        }
        return result.getTypeRef();
    }
}
