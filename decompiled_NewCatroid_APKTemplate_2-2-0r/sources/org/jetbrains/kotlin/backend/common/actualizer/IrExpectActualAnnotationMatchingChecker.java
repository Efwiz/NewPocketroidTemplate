package org.jetbrains.kotlin.backend.common.actualizer;

import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.KtDiagnosticReporterWithImplicitIrBasedContext;
import org.jetbrains.kotlin.backend.common.lower.LocalDeclarationsLoweringKt;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeAliasSymbol;
import org.jetbrains.kotlin.ir.types.IrTypeSystemContext;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.IrFakeOverrideUtilsKt;
import org.jetbrains.kotlin.mpp.DeclarationSymbolMarker;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.resolve.calls.mpp.AbstractExpectActualAnnotationMatchChecker;
import org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext;

/* JADX INFO: compiled from: IrExpectActualAnnotationMatchingChecker.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000E\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0001\r\b\u0000\u0018\u00002\u00020\u0001B1\u0012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\u0006\u0010\u0012\u001a\u00020\u0013J\u0012\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0016\u001a\u00020\u0004H\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u000eR\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\u000f\u001a\u00020\u0010*\u00020\u00048BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0011¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/actualizer/IrExpectActualAnnotationMatchingChecker;", "", "matchedExpectToActual", "", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "classActualizationInfo", "Lorg/jetbrains/kotlin/backend/common/actualizer/ClassActualizationInfo;", "typeSystemContext", "Lorg/jetbrains/kotlin/ir/types/IrTypeSystemContext;", "diagnosticsReporter", "Lorg/jetbrains/kotlin/KtDiagnosticReporterWithImplicitIrBasedContext;", "(Ljava/util/Map;Lorg/jetbrains/kotlin/backend/common/actualizer/ClassActualizationInfo;Lorg/jetbrains/kotlin/ir/types/IrTypeSystemContext;Lorg/jetbrains/kotlin/KtDiagnosticReporterWithImplicitIrBasedContext;)V", "context", "org/jetbrains/kotlin/backend/common/actualizer/IrExpectActualAnnotationMatchingChecker$context$1", "Lorg/jetbrains/kotlin/backend/common/actualizer/IrExpectActualAnnotationMatchingChecker$context$1;", "isFakeOverride", "", "(Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;)Z", "check", "", "getTypealiasSymbolIfActualizedViaTypealias", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeAliasSymbol;", "expectSymbol", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrExpectActualAnnotationMatchingChecker {
    private final ClassActualizationInfo classActualizationInfo;
    private final IrExpectActualAnnotationMatchingChecker$context$1 context;
    private final KtDiagnosticReporterWithImplicitIrBasedContext diagnosticsReporter;
    private final Map<IrSymbol, IrSymbol> matchedExpectToActual;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v1, types: [org.jetbrains.kotlin.backend.common.actualizer.IrExpectActualAnnotationMatchingChecker$context$1] */
    public IrExpectActualAnnotationMatchingChecker(Map<IrSymbol, ? extends IrSymbol> matchedExpectToActual, ClassActualizationInfo classActualizationInfo, final IrTypeSystemContext typeSystemContext, KtDiagnosticReporterWithImplicitIrBasedContext diagnosticsReporter) {
        Intrinsics.checkNotNullParameter(matchedExpectToActual, "matchedExpectToActual");
        Intrinsics.checkNotNullParameter(classActualizationInfo, "classActualizationInfo");
        Intrinsics.checkNotNullParameter(typeSystemContext, "typeSystemContext");
        Intrinsics.checkNotNullParameter(diagnosticsReporter, "diagnosticsReporter");
        this.matchedExpectToActual = matchedExpectToActual;
        this.classActualizationInfo = classActualizationInfo;
        this.diagnosticsReporter = diagnosticsReporter;
        final Map<ClassId, IrClassSymbol> actualClasses = classActualizationInfo.getActualClasses();
        this.context = new IrExpectActualMatchingContext(typeSystemContext, actualClasses) { // from class: org.jetbrains.kotlin.backend.common.actualizer.IrExpectActualAnnotationMatchingChecker$context$1
            @Override // org.jetbrains.kotlin.backend.common.actualizer.IrExpectActualMatchingContext
            public void onMatchedClasses(IrClassSymbol expectClassSymbol, IrClassSymbol actualClassSymbol) {
                Intrinsics.checkNotNullParameter(expectClassSymbol, "expectClassSymbol");
                Intrinsics.checkNotNullParameter(actualClassSymbol, "actualClassSymbol");
                throw new IllegalStateException("Must not be called".toString());
            }

            @Override // org.jetbrains.kotlin.backend.common.actualizer.IrExpectActualMatchingContext
            public void onMatchedCallables(IrSymbol expectSymbol, IrSymbol actualSymbol) {
                Intrinsics.checkNotNullParameter(expectSymbol, "expectSymbol");
                Intrinsics.checkNotNullParameter(actualSymbol, "actualSymbol");
                throw new IllegalStateException("Must not be called".toString());
            }
        };
    }

    public final void check() {
        AbstractExpectActualAnnotationMatchChecker.Incompatibility incompatibilityAreAnnotationsCompatible;
        for (Map.Entry<IrSymbol, IrSymbol> entry : this.matchedExpectToActual.entrySet()) {
            IrSymbol key = entry.getKey();
            IrTypeAliasSymbol value = entry.getValue();
            if (!isFakeOverride(key) && !isFakeOverride(value) && (incompatibilityAreAnnotationsCompatible = AbstractExpectActualAnnotationMatchChecker.INSTANCE.areAnnotationsCompatible(key, value, this.context)) != null) {
                IrTypeAliasSymbol typealiasSymbolIfActualizedViaTypealias = getTypealiasSymbolIfActualizedViaTypealias(key);
                if (typealiasSymbolIfActualizedViaTypealias != null) {
                    value = typealiasSymbolIfActualizedViaTypealias;
                }
                KtDiagnosticReporterWithImplicitIrBasedContext ktDiagnosticReporterWithImplicitIrBasedContext = this.diagnosticsReporter;
                DeclarationSymbolMarker expectSymbol = incompatibilityAreAnnotationsCompatible.getExpectSymbol();
                Intrinsics.checkNotNull(expectSymbol, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrSymbol");
                DeclarationSymbolMarker actualSymbol = incompatibilityAreAnnotationsCompatible.getActualSymbol();
                Intrinsics.checkNotNull(actualSymbol, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrSymbol");
                IrActualizerUtilsKt.reportActualAnnotationsNotMatchExpect(ktDiagnosticReporterWithImplicitIrBasedContext, (IrSymbol) expectSymbol, (IrSymbol) actualSymbol, incompatibilityAreAnnotationsCompatible.getType().mapAnnotationType(new Function1<ExpectActualMatchingContext.AnnotationCallInfo, IrConstructorCall>() { // from class: org.jetbrains.kotlin.backend.common.actualizer.IrExpectActualAnnotationMatchingChecker.check.1
                    @Override // kotlin.jvm.functions.Function1
                    public final IrConstructorCall invoke(ExpectActualMatchingContext.AnnotationCallInfo it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        Object annotationSymbol = it.getAnnotationSymbol();
                        Intrinsics.checkNotNull(annotationSymbol, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrConstructorCall");
                        return (IrConstructorCall) annotationSymbol;
                    }
                }), value);
            }
        }
    }

    private final boolean isFakeOverride(IrSymbol irSymbol) {
        IrSymbolOwner owner = irSymbol.getOwner();
        Intrinsics.checkNotNull(owner, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrDeclaration");
        return IrFakeOverrideUtilsKt.isFakeOverride((IrDeclaration) owner);
    }

    private final IrTypeAliasSymbol getTypealiasSymbolIfActualizedViaTypealias(IrSymbol expectSymbol) {
        IrSymbolOwner owner = expectSymbol.getOwner();
        Intrinsics.checkNotNull(owner, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrDeclaration");
        Sequence sequenceFilter = SequencesKt.filter(LocalDeclarationsLoweringKt.getParentsWithSelf((IrDeclaration) owner), new Function1<Object, Boolean>() { // from class: org.jetbrains.kotlin.backend.common.actualizer.IrExpectActualAnnotationMatchingChecker$getTypealiasSymbolIfActualizedViaTypealias$$inlined$filterIsInstance$1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(Object obj) {
                return Boolean.valueOf(obj instanceof IrClass);
            }
        });
        Intrinsics.checkNotNull(sequenceFilter, "null cannot be cast to non-null type kotlin.sequences.Sequence<R of kotlin.sequences.SequencesKt___SequencesKt.filterIsInstance>");
        IrClass irClass = (IrClass) SequencesKt.lastOrNull(sequenceFilter);
        if (irClass == null) {
            return null;
        }
        return this.classActualizationInfo.getActualTypeAliases().get(AdditionalIrUtilsKt.getClassIdOrFail(irClass));
    }
}
