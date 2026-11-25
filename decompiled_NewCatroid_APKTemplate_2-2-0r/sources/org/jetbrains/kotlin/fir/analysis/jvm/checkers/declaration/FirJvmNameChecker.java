package org.jetbrains.kotlin.fir.analysis.jvm.checkers.declaration;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker;
import org.jetbrains.kotlin.fir.analysis.diagnostics.jvm.FirJvmErrors;
import org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirConstExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.resolve.ResolveUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.StandardClassIds;

/* JADX INFO: compiled from: FirJvmNameChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u000e\u0010\f\u001a\u0004\u0018\u00010\r*\u00020\u0002H\u0002J\u0014\u0010\u000e\u001a\u00020\u000f*\u00020\t2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\f\u0010\u0012\u001a\u00020\u000f*\u00020\u0013H\u0002¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/jvm/checkers/declaration/FirJvmNameChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirBasicDeclarationChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "findJvmNameAnnotation", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "isRenamableFunction", "", "function", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "isValueClassThatRequiresMangling", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "checkers.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJvmNameChecker extends FirDeclarationChecker<FirDeclaration> {
    public static final FirJvmNameChecker INSTANCE = new FirJvmNameChecker();

    private FirJvmNameChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirDeclaration declaration, CheckerContext context, DiagnosticReporter reporter) {
        FirExpression firExpressionFindArgumentByName;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        FirAnnotation firAnnotationFindJvmNameAnnotation = findJvmNameAnnotation(declaration);
        if (firAnnotationFindJvmNameAnnotation == null || (firExpressionFindArgumentByName = FirAnnotationUtilsKt.findArgumentByName(firAnnotationFindJvmNameAnnotation, StandardNames.NAME)) == null || !Intrinsics.areEqual(FirTypeUtilsKt.getConeType(firExpressionFindArgumentByName.getTypeRef()), context.getSession().getBuiltinTypes().getStringType().getType())) {
            return;
        }
        FirConstExpression firConstExpression = firExpressionFindArgumentByName instanceof FirConstExpression ? (FirConstExpression) firExpressionFindArgumentByName : null;
        Object value = firConstExpression != null ? firConstExpression.getValue() : null;
        String str = value instanceof String ? (String) value : null;
        if (str == null) {
            return;
        }
        if (!Name.isValidIdentifier(str)) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, firAnnotationFindJvmNameAnnotation.getSource(), FirJvmErrors.INSTANCE.getILLEGAL_JVM_NAME(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
        if ((declaration instanceof FirFunction) && !isRenamableFunction(context, (FirFunction) declaration)) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, firAnnotationFindJvmNameAnnotation.getSource(), FirJvmErrors.INSTANCE.getINAPPLICABLE_JVM_NAME(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            return;
        }
        if (declaration instanceof FirCallableDeclaration) {
            FirRegularClass containingClass = ResolveUtilsKt.getContainingClass((FirCallableDeclaration) declaration, context.getSession());
            FirMemberDeclaration firMemberDeclaration = (FirMemberDeclaration) declaration;
            if (firMemberDeclaration.getStatus().isOverride() || (!(firMemberDeclaration.getStatus().getModality() == Modality.FINAL || Intrinsics.areEqual(firMemberDeclaration.getStatus().getVisibility(), Visibilities.Private.INSTANCE)) || (containingClass != null && isValueClassThatRequiresMangling(containingClass)))) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, firAnnotationFindJvmNameAnnotation.getSource(), FirJvmErrors.INSTANCE.getINAPPLICABLE_JVM_NAME(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
        }
    }

    private final FirAnnotation findJvmNameAnnotation(FirDeclaration firDeclaration) {
        Object next;
        Iterator<T> it = firDeclaration.getAnnotations().iterator();
        while (it.hasNext()) {
            next = it.next();
            if (Intrinsics.areEqual(ConeTypeUtilsKt.getClassId(FirTypeUtilsKt.getConeType(((FirAnnotation) next).getAnnotationTypeRef())), StandardClassIds.Annotations.INSTANCE.getJvmName())) {
                return (FirAnnotation) next;
            }
        }
        next = null;
        return (FirAnnotation) next;
    }

    private final boolean isRenamableFunction(CheckerContext checkerContext, FirFunction firFunction) {
        return (FirHelpersKt.getContainingClassSymbol(firFunction, checkerContext.getSession()) == null && firFunction.getSymbol().getCallableId().isLocal()) ? false : true;
    }

    private final boolean isValueClassThatRequiresMangling(FirRegularClass firRegularClass) {
        return firRegularClass.getStatus().isInline() && !Intrinsics.areEqual(firRegularClass.getName(), StandardClassIds.INSTANCE.getResult().getShortClassName());
    }
}
