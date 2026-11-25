package org.jetbrains.kotlin.fir.analysis.js.checkers.declaration;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker;
import org.jetbrains.kotlin.fir.analysis.diagnostics.js.FirJsErrors;
import org.jetbrains.kotlin.fir.analysis.js.checkers.FirJsHelpersKt;
import org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.utils.FirDeclarationUtilKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.name.JsStandardClassIds;
import org.jetbrains.kotlin.utils.addToStdlib.AddToStdlibKt;

/* JADX INFO: compiled from: FirJsExternalInheritorOnlyChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J(\u0010\f\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00060\u000eH\u0002¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/js/checkers/declaration/FirJsExternalInheritorOnlyChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirClassChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "forEachParents", "f", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "checkers.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJsExternalInheritorOnlyChecker extends FirDeclarationChecker<FirClass> {
    public static final FirJsExternalInheritorOnlyChecker INSTANCE = new FirJsExternalInheritorOnlyChecker();

    private FirJsExternalInheritorOnlyChecker() {
    }

    private final void forEachParents(FirClass firClass, CheckerContext checkerContext, Function1<? super FirRegularClassSymbol, Unit> function1) {
        List mutableList = CollectionsKt.toMutableList((Collection) FirDeclarationUtilKt.getSuperConeTypes(firClass));
        HashSet hashSet = new HashSet();
        while (true) {
            List list = mutableList;
            if (list.isEmpty()) {
                return;
            }
            FirRegularClassSymbol regularClassSymbol = TypeUtilsKt.toRegularClassSymbol((ConeClassLikeType) AddToStdlibKt.popLast(mutableList), checkerContext.getSession());
            if (regularClassSymbol != null && hashSet.add(regularClassSymbol)) {
                function1.invoke(regularClassSymbol);
                Iterator<T> it = regularClassSymbol.getResolvedSuperTypeRefs().iterator();
                while (it.hasNext()) {
                    ConeKotlinType type = ((FirResolvedTypeRef) it.next()).getType();
                    ConeClassLikeType coneClassLikeType = type instanceof ConeClassLikeType ? (ConeClassLikeType) type : null;
                    if (coneClassLikeType != null) {
                        list.add(coneClassLikeType);
                    }
                }
            }
        }
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(final FirClass declaration, final CheckerContext context, final DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (FirJsHelpersKt.isEffectivelyExternal(declaration.getSymbol(), context)) {
            return;
        }
        forEachParents(declaration, context, new Function1<FirRegularClassSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.analysis.js.checkers.declaration.FirJsExternalInheritorOnlyChecker.check.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirRegularClassSymbol firRegularClassSymbol) {
                invoke2(firRegularClassSymbol);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirRegularClassSymbol parent) {
                Intrinsics.checkNotNullParameter(parent, "parent");
                if (FirAnnotationUtilsKt.hasAnnotation(parent, JsStandardClassIds.Annotations.JsExternalInheritorsOnly, context.getSession())) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirJsErrors.INSTANCE.getJS_EXTERNAL_INHERITORS_ONLY(), parent, declaration.getSymbol(), context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
                }
            }
        });
    }
}
