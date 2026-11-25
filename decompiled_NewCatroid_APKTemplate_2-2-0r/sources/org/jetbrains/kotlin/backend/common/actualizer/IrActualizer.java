package org.jetbrains.kotlin.backend.common.actualizer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtDiagnosticReporterWithImplicitIrBasedContext;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.incremental.components.ExpectActualTracker;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.types.IrTypeSystemContext;
import org.jetbrains.kotlin.ir.util.DeepCopyTypeRemapper;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;

/* JADX INFO: compiled from: IrActualizer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J>\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010J\u001e\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\bH\u0002J0\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\b2\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\b2\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u00170\u0016H\u0002J$\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u00142\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u00170\u0016H\u0002¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/actualizer/IrActualizer;", "", "()V", "actualize", "Lorg/jetbrains/kotlin/backend/common/actualizer/IrActualizedResult;", "mainFragment", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "dependentFragments", "", "diagnosticReporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "typeSystemContext", "Lorg/jetbrains/kotlin/ir/types/IrTypeSystemContext;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "expectActualTracker", "Lorg/jetbrains/kotlin/incremental/components/ExpectActualTracker;", "mergeIrFragments", "", "removeExpectDeclarations", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "expectActualMap", "", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "shouldRemoveExpectDeclaration", "", "irDeclaration", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrActualizer {
    public static final IrActualizer INSTANCE = new IrActualizer();

    private IrActualizer() {
    }

    public final IrActualizedResult actualize(IrModuleFragment mainFragment, List<? extends IrModuleFragment> dependentFragments, DiagnosticReporter diagnosticReporter, IrTypeSystemContext typeSystemContext, LanguageVersionSettings languageVersionSettings, ExpectActualTracker expectActualTracker) {
        Intrinsics.checkNotNullParameter(mainFragment, "mainFragment");
        Intrinsics.checkNotNullParameter(dependentFragments, "dependentFragments");
        Intrinsics.checkNotNullParameter(diagnosticReporter, "diagnosticReporter");
        Intrinsics.checkNotNullParameter(typeSystemContext, "typeSystemContext");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        KtDiagnosticReporterWithImplicitIrBasedContext ktDiagnosticReporterWithImplicitIrBasedContext = new KtDiagnosticReporterWithImplicitIrBasedContext(diagnosticReporter, languageVersionSettings);
        ExpectActualCollector.Result resultCollect = new ExpectActualCollector(mainFragment, dependentFragments, typeSystemContext, ktDiagnosticReporterWithImplicitIrBasedContext, expectActualTracker).collect();
        Map<IrSymbol, IrSymbol> mapComponent1 = resultCollect.component1();
        ClassActualizationInfo classActualizationInfo = resultCollect.getClassActualizationInfo();
        if (languageVersionSettings.supportsFeature(LanguageFeature.MultiplatformRestrictions)) {
            new IrExpectActualAnnotationMatchingChecker(mapComponent1, classActualizationInfo, typeSystemContext, ktDiagnosticReporterWithImplicitIrBasedContext).check();
        }
        List<IrDeclaration> listRemoveExpectDeclarations = removeExpectDeclarations(dependentFragments, mapComponent1);
        FakeOverridesActualizer fakeOverridesActualizer = new FakeOverridesActualizer(mapComponent1);
        List<? extends IrModuleFragment> list = dependentFragments;
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            fakeOverridesActualizer.visitModuleFragment((IrModuleFragment) it.next());
        }
        ActualFakeOverridesAdder actualFakeOverridesAdder = new ActualFakeOverridesAdder(mapComponent1, classActualizationInfo.getActualClasses(), ktDiagnosticReporterWithImplicitIrBasedContext, typeSystemContext);
        Iterator<T> it2 = list.iterator();
        while (it2.hasNext()) {
            actualFakeOverridesAdder.visitModuleFragment((IrModuleFragment) it2.next());
        }
        ActualizerSymbolRemapper actualizerSymbolRemapper = new ActualizerSymbolRemapper(mapComponent1);
        ActualizerSymbolRemapper actualizerSymbolRemapper2 = actualizerSymbolRemapper;
        DeepCopyTypeRemapper deepCopyTypeRemapper = new DeepCopyTypeRemapper(actualizerSymbolRemapper2);
        new FunctionDefaultParametersActualizer(actualizerSymbolRemapper, deepCopyTypeRemapper, mapComponent1).actualize();
        ActualizerVisitor actualizerVisitor = new ActualizerVisitor(actualizerSymbolRemapper2, deepCopyTypeRemapper);
        Iterator<T> it3 = list.iterator();
        while (it3.hasNext()) {
            ((IrModuleFragment) it3.next()).transform((IrElementTransformer<? super Object>) actualizerVisitor, (Object) null);
        }
        mergeIrFragments(mainFragment, dependentFragments);
        return new IrActualizedResult(listRemoveExpectDeclarations);
    }

    private final List<IrDeclaration> removeExpectDeclarations(List<? extends IrModuleFragment> dependentFragments, final Map<IrSymbol, ? extends IrSymbol> expectActualMap) {
        final ArrayList arrayList = new ArrayList();
        Iterator<? extends IrModuleFragment> it = dependentFragments.iterator();
        while (it.hasNext()) {
            Iterator<IrFile> it2 = it.next().getFiles().iterator();
            while (it2.hasNext()) {
                List<IrDeclaration> declarations = it2.next().getDeclarations();
                final Function1<IrDeclaration, Boolean> function1 = new Function1<IrDeclaration, Boolean>() { // from class: org.jetbrains.kotlin.backend.common.actualizer.IrActualizer.removeExpectDeclarations.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    /* JADX WARN: Multi-variable type inference failed */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Boolean invoke(IrDeclaration it3) {
                        boolean z;
                        Intrinsics.checkNotNullParameter(it3, "it");
                        if (IrActualizer.INSTANCE.shouldRemoveExpectDeclaration(it3, expectActualMap)) {
                            arrayList.add(it3);
                            z = true;
                        } else {
                            z = false;
                        }
                        return Boolean.valueOf(z);
                    }
                };
                declarations.removeIf(new Predicate() { // from class: org.jetbrains.kotlin.backend.common.actualizer.IrActualizer$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return IrActualizer.removeExpectDeclarations$lambda$5(function1, obj);
                    }
                });
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean removeExpectDeclarations$lambda$5(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return ((Boolean) tmp0.invoke(obj)).booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean shouldRemoveExpectDeclaration(IrDeclaration irDeclaration, Map<IrSymbol, ? extends IrSymbol> expectActualMap) {
        if (irDeclaration instanceof IrClass) {
            IrClass irClass = (IrClass) irDeclaration;
            if (irClass.getIsExpect()) {
                return !IrActualizerUtilsKt.containsOptionalExpectation(irDeclaration) || expectActualMap.containsKey(irClass.getSymbol());
            }
            return false;
        }
        if (irDeclaration instanceof IrProperty) {
            return ((IrProperty) irDeclaration).getIsExpect();
        }
        if (irDeclaration instanceof IrFunction) {
            return ((IrFunction) irDeclaration).getIsExpect();
        }
        return false;
    }

    private final void mergeIrFragments(IrModuleFragment mainFragment, List<? extends IrModuleFragment> dependentFragments) {
        List<IrFile> files = mainFragment.getFiles();
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = dependentFragments.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, ((IrModuleFragment) it.next()).getFiles());
        }
        files.addAll(0, arrayList);
    }
}
