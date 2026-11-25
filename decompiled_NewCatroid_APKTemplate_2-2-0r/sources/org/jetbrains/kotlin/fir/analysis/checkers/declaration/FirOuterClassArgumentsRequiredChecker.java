package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirTypeRefSource;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.FirErrorTypeRef;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirUserTypeRef;

/* JADX INFO: compiled from: FirOuterClassArgumentsRequiredChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J*\u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u000e2\b\u0010\u0007\u001a\u0004\u0018\u00010\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirOuterClassArgumentsRequiredChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirRegularClassChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkOuterClassArgumentsRequired", "typeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirOuterClassArgumentsRequiredChecker extends FirDeclarationChecker<FirRegularClass> {
    public static final FirOuterClassArgumentsRequiredChecker INSTANCE = new FirOuterClassArgumentsRequiredChecker();

    private FirOuterClassArgumentsRequiredChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirRegularClass declaration, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        Iterator<FirTypeRef> it = declaration.getSuperTypeRefs().iterator();
        while (it.hasNext()) {
            checkOuterClassArgumentsRequired(it.next(), declaration, context, reporter);
        }
    }

    private final void checkOuterClassArgumentsRequired(FirTypeRef typeRef, FirRegularClass declaration, CheckerContext context, DiagnosticReporter reporter) {
        if (!(typeRef instanceof FirResolvedTypeRef) || (typeRef instanceof FirErrorTypeRef)) {
            return;
        }
        FirResolvedTypeRef firResolvedTypeRef = (FirResolvedTypeRef) typeRef;
        ConeKotlinType type = firResolvedTypeRef.getType();
        FirTypeRef delegatedTypeRef = firResolvedTypeRef.getDelegatedTypeRef();
        if ((delegatedTypeRef instanceof FirUserTypeRef) && (type instanceof ConeClassLikeType)) {
            FirClassLikeSymbol<?> symbol = LookupTagUtilsKt.toSymbol(((ConeClassLikeType) type).getLookupTag(), context.getSession());
            if (symbol instanceof FirRegularClassSymbol) {
                ConeTypeProjection[] typeProjections = org.jetbrains.kotlin.fir.resolve.DeclarationUtilsKt.toTypeProjections(((FirUserTypeRef) delegatedTypeRef).getQualifier());
                List<FirTypeParameterSymbol> typeParameterSymbols = symbol.getTypeParameterSymbols();
                int size = typeParameterSymbols.size();
                for (int length = typeProjections.length; length < size; length++) {
                    FirTypeParameterSymbol firTypeParameterSymbol = typeParameterSymbols.get(length);
                    if (!org.jetbrains.kotlin.fir.resolve.DeclarationUtilsKt.isValidTypeParameterFromOuterDeclaration(firTypeParameterSymbol, declaration, context.getSession())) {
                        FirBasedSymbol<?> containingDeclarationSymbol = firTypeParameterSymbol.getContainingDeclarationSymbol();
                        FirRegularClassSymbol firRegularClassSymbol = containingDeclarationSymbol instanceof FirRegularClassSymbol ? (FirRegularClassSymbol) containingDeclarationSymbol : null;
                        if (firRegularClassSymbol == null) {
                            break;
                        }
                        KtDiagnosticReportHelpersKt.reportOn$default(reporter, typeRef.getSource(), FirErrors.INSTANCE.getOUTER_CLASS_ARGUMENTS_REQUIRED(), firRegularClassSymbol, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
                        break;
                    }
                }
            }
        }
        List<FirTypeRefSource> listExtractArgumentsTypeRefAndSource = FirHelpersKt.extractArgumentsTypeRefAndSource(typeRef);
        if (listExtractArgumentsTypeRefAndSource == null) {
            return;
        }
        Iterator<FirTypeRefSource> it = listExtractArgumentsTypeRefAndSource.iterator();
        while (it.hasNext()) {
            FirTypeRef typeRef2 = it.next().getTypeRef();
            if (typeRef2 != null) {
                INSTANCE.checkOuterClassArgumentsRequired(typeRef2, declaration, context, reporter);
            }
        }
    }
}
