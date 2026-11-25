package org.jetbrains.kotlin.fir.analysis.p003native.checkers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker;
import org.jetbrains.kotlin.fir.analysis.diagnostics.p002native.FirNativeErrors;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirVariable;
import org.jetbrains.kotlin.fir.declarations.utils.DeclarationAttributesKt;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: FirNativeThreadLocalChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/native/checkers/FirNativeThreadLocalChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirBasicDeclarationChecker;", "()V", "threadLocalClassId", "Lorg/jetbrains/kotlin/name/ClassId;", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers.native"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirNativeThreadLocalChecker extends FirDeclarationChecker<FirDeclaration> {
    public static final FirNativeThreadLocalChecker INSTANCE = new FirNativeThreadLocalChecker();
    private static final ClassId threadLocalClassId;

    private FirNativeThreadLocalChecker() {
    }

    static {
        ClassId classId = ClassId.topLevel(new FqName("kotlin.native.concurrent.ThreadLocal"));
        Intrinsics.checkNotNullExpressionValue(classId, "topLevel(...)");
        threadLocalClassId = classId;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirDeclaration declaration, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        boolean z = (declaration instanceof FirClass) && ((FirClass) declaration).getClassKind() == ClassKind.OBJECT;
        if ((!(declaration instanceof FirVariable) || ((!(declaration instanceof FirProperty) || !DeclarationAttributesKt.getHasBackingField((FirProperty) declaration)) && ((FirVariable) declaration).getDelegate() == null)) && !z) {
            FirNativeHelpersKt.reportIfHasAnnotation(reporter, declaration, threadLocalClassId, FirNativeErrors.INSTANCE.getINAPPLICABLE_THREAD_LOCAL(), context);
        }
        KtSourceElement source = declaration.getSource();
        if (((source != null ? source.getKind() : null) instanceof KtFakeSourceElementKind) || FirHelpersKt.isTopLevel(context) || z) {
            return;
        }
        FirNativeHelpersKt.reportIfHasAnnotation(reporter, declaration, threadLocalClassId, FirNativeErrors.INSTANCE.getINAPPLICABLE_THREAD_LOCAL_TOP_LEVEL(), context);
    }
}
