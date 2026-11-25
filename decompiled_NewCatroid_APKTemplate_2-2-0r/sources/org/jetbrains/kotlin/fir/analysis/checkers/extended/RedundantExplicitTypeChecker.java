package org.jetbrains.kotlin.fir.analysis.checkers.extended;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.expressions.FirConstExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirGetClassCall;
import org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier;
import org.jetbrains.kotlin.fir.expressions.FirStringConcatenationCall;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeAliasSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirErrorTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.StandardClassIds;
import org.jetbrains.kotlin.types.ConstantValueKind;

/* JADX INFO: compiled from: RedundantExplicitTypeChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0014\u0010\f\u001a\u00020\r*\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\u0016\u0010\u0011\u001a\u00020\r*\u00020\u000e2\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0002¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/extended/RedundantExplicitTypeChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirPropertyChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "hasSameNameWithoutModifiers", "", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "name", "Lorg/jetbrains/kotlin/name/Name;", "isSame", "other", "Lorg/jetbrains/kotlin/name/ClassId;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RedundantExplicitTypeChecker extends FirDeclarationChecker<FirProperty> {
    public static final RedundantExplicitTypeChecker INSTANCE = new RedundantExplicitTypeChecker();

    private RedundantExplicitTypeChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirProperty declaration, CheckerContext context, DiagnosticReporter reporter) {
        FirExpression initializer;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (!declaration.getIsLocal() || declaration.getReturnTypeRef().getSource() == null || (initializer = declaration.getInitializer()) == null) {
            return;
        }
        FirTypeRef returnTypeRef = declaration.getReturnTypeRef();
        if (returnTypeRef instanceof FirErrorTypeRef) {
            returnTypeRef = null;
        }
        if (returnTypeRef == null) {
            return;
        }
        KtSourceElement source = returnTypeRef.getSource();
        if ((source != null ? source.getKind() : null) instanceof KtFakeSourceElementKind) {
            return;
        }
        ConeKotlinType coneType = FirTypeUtilsKt.getConeType(returnTypeRef);
        if (!(TypeUtilsKt.toSymbol(coneType, context.getSession()) instanceof FirTypeAliasSymbol) && returnTypeRef.getAnnotations().isEmpty()) {
            if (initializer instanceof FirConstExpression) {
                KtSourceElement source2 = initializer.getSource();
                IElementType elementType = source2 != null ? source2.getElementType() : null;
                if (Intrinsics.areEqual(elementType, KtNodeTypes.BOOLEAN_CONSTANT)) {
                    if (!isSame(coneType, StandardClassIds.INSTANCE.getBoolean())) {
                        return;
                    }
                } else if (Intrinsics.areEqual(elementType, KtNodeTypes.INTEGER_CONSTANT)) {
                    if (Intrinsics.areEqual(((FirConstExpression) initializer).getKind(), ConstantValueKind.Long.INSTANCE)) {
                        if (!isSame(coneType, StandardClassIds.INSTANCE.getLong())) {
                            return;
                        }
                    } else if (!isSame(coneType, StandardClassIds.INSTANCE.getInt())) {
                        return;
                    }
                } else if (Intrinsics.areEqual(elementType, KtNodeTypes.FLOAT_CONSTANT)) {
                    if (Intrinsics.areEqual(((FirConstExpression) initializer).getKind(), ConstantValueKind.Float.INSTANCE)) {
                        if (!isSame(coneType, StandardClassIds.INSTANCE.getFloat())) {
                            return;
                        }
                    } else if (!isSame(coneType, StandardClassIds.INSTANCE.getDouble())) {
                        return;
                    }
                } else if (Intrinsics.areEqual(elementType, KtNodeTypes.CHARACTER_CONSTANT)) {
                    if (!isSame(coneType, StandardClassIds.INSTANCE.getChar())) {
                        return;
                    }
                } else if (!Intrinsics.areEqual(elementType, KtNodeTypes.STRING_TEMPLATE) || !isSame(coneType, StandardClassIds.INSTANCE.getString())) {
                    return;
                }
            } else if (initializer instanceof FirFunctionCall) {
                if (!hasSameNameWithoutModifiers(coneType, ((FirFunctionCall) initializer).getCalleeReference().getName())) {
                    return;
                }
            } else {
                if (initializer instanceof FirGetClassCall) {
                    return;
                }
                if (initializer instanceof FirResolvedQualifier) {
                    if (!isSame(coneType, ((FirResolvedQualifier) initializer).getClassId())) {
                        return;
                    }
                } else if (!(initializer instanceof FirStringConcatenationCall) || !isSame(coneType, StandardClassIds.INSTANCE.getString())) {
                    return;
                }
            }
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getReturnTypeRef().getSource(), FirErrors.INSTANCE.getREDUNDANT_EXPLICIT_TYPE(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
    }

    private final boolean isSame(ConeKotlinType coneKotlinType, ClassId classId) {
        return !coneKotlinType.getNullability().isNullable() && Intrinsics.areEqual(ConeTypeUtilsKt.getClassId(coneKotlinType.getType()), classId);
    }

    private final boolean hasSameNameWithoutModifiers(ConeKotlinType coneKotlinType, Name name2) {
        return (coneKotlinType instanceof ConeClassLikeType) && Intrinsics.areEqual(((ConeClassLikeType) coneKotlinType).getLookupTag().getName(), name2) && coneKotlinType.getTypeArguments().length == 0 && !ConeTypeUtilsKt.isMarkedNullable(coneKotlinType);
    }
}
