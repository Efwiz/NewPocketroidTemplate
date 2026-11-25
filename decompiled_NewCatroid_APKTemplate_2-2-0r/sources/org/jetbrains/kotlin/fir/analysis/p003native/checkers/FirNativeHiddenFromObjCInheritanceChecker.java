package org.jetbrains.kotlin.fir.analysis.p003native.checkers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker;
import org.jetbrains.kotlin.fir.analysis.diagnostics.p002native.FirNativeErrors;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.utils.FirDeclarationUtilKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.types.ConeBuiltinTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;

/* JADX INFO: compiled from: FirNativeHiddenFromObjCInheritanceChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/native/checkers/FirNativeHiddenFromObjCInheritanceChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirRegularClassChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers.native"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirNativeHiddenFromObjCInheritanceChecker extends FirDeclarationChecker<FirRegularClass> {
    public static final FirNativeHiddenFromObjCInheritanceChecker INSTANCE = new FirNativeHiddenFromObjCInheritanceChecker();

    private FirNativeHiddenFromObjCInheritanceChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirRegularClass declaration, CheckerContext context, DiagnosticReporter reporter) {
        Object next;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (declaration.getClassKind() != ClassKind.ENUM_ENTRY && declaration.getStatus().getVisibility().getIsPublicAPI()) {
            FirSession session = context.getSession();
            if (FirNativeHiddenFromObjCInheritanceCheckerKt.checkIsHiddenFromObjC(declaration.getSymbol(), session)) {
                return;
            }
            List<ConeClassLikeType> superConeTypes = FirDeclarationUtilKt.getSuperConeTypes(declaration);
            ArrayList arrayList = new ArrayList();
            for (Object obj : superConeTypes) {
                ConeClassLikeType coneClassLikeType = (ConeClassLikeType) obj;
                if (!ConeBuiltinTypeUtilsKt.isAny(coneClassLikeType) && !ConeBuiltinTypeUtilsKt.isNullableAny(coneClassLikeType)) {
                    arrayList.add(obj);
                }
            }
            ArrayList arrayList2 = new ArrayList();
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                FirClassLikeSymbol<?> symbol = TypeUtilsKt.toSymbol((ConeClassLikeType) it.next(), session);
                if (symbol != null) {
                    arrayList2.add(symbol);
                }
            }
            Iterator it2 = arrayList2.iterator();
            do {
                if (!it2.hasNext()) {
                    next = null;
                    break;
                }
                next = it2.next();
            } while (!FirNativeHiddenFromObjCInheritanceCheckerKt.checkIsHiddenFromObjC((FirClassLikeSymbol) next, session));
            if (((FirClassLikeSymbol) next) != null) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirNativeErrors.INSTANCE.getSUBTYPE_OF_HIDDEN_FROM_OBJC(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
        }
    }
}
