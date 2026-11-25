package org.jetbrains.kotlin.fir.analysis.p003native.checkers;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker;
import org.jetbrains.kotlin.fir.analysis.diagnostics.p002native.FirNativeErrors;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.FirVariable;
import org.jetbrains.kotlin.fir.declarations.impl.FirPrimaryConstructor;
import org.jetbrains.kotlin.fir.declarations.utils.DeclarationAttributesKt;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: FirNativeSharedImmutableChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/native/checkers/FirNativeSharedImmutableChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirBasicDeclarationChecker;", "()V", "sharedImmutableClassId", "Lorg/jetbrains/kotlin/name/ClassId;", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers.native"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirNativeSharedImmutableChecker extends FirDeclarationChecker<FirDeclaration> {
    public static final FirNativeSharedImmutableChecker INSTANCE = new FirNativeSharedImmutableChecker();
    private static final ClassId sharedImmutableClassId;

    private FirNativeSharedImmutableChecker() {
    }

    static {
        ClassId classId = ClassId.topLevel(new FqName("kotlin.native.concurrent.SharedImmutable"));
        Intrinsics.checkNotNullExpressionValue(classId, "topLevel(...)");
        sharedImmutableClassId = classId;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirDeclaration declaration, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (declaration instanceof FirVariable) {
            if (!(declaration instanceof FirValueParameter) || !(CollectionsKt.lastOrNull((List) context.getContainingDeclarations()) instanceof FirPrimaryConstructor)) {
                boolean z = (declaration instanceof FirProperty) && DeclarationAttributesKt.getHasBackingField((FirProperty) declaration);
                FirVariable firVariable = (FirVariable) declaration;
                if ((firVariable.getIsVar() || !z) && firVariable.getDelegate() == null) {
                    FirNativeHelpersKt.reportIfHasAnnotation(reporter, declaration, sharedImmutableClassId, FirNativeErrors.INSTANCE.getINAPPLICABLE_SHARED_IMMUTABLE_PROPERTY(), context);
                }
            }
            if (FirHelpersKt.isTopLevel(context)) {
                return;
            }
            FirNativeHelpersKt.reportIfHasAnnotation(reporter, declaration, sharedImmutableClassId, FirNativeErrors.INSTANCE.getINAPPLICABLE_SHARED_IMMUTABLE_TOP_LEVEL(), context);
            return;
        }
        KtSourceElement source = declaration.getSource();
        if ((source != null ? source.getKind() : null) instanceof KtFakeSourceElementKind) {
            return;
        }
        FirNativeHelpersKt.reportIfHasAnnotation(reporter, declaration, sharedImmutableClassId, FirNativeErrors.INSTANCE.getINAPPLICABLE_SHARED_IMMUTABLE_PROPERTY(), context);
    }
}
