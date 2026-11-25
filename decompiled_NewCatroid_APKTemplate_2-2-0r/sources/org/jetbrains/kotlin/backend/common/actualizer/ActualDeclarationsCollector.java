package org.jetbrains.kotlin.backend.common.actualizer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationContainer;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrTypeAlias;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeAliasSymbol;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: ExpectActualCollector.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0002\u0018\u0000 \u001b2\u00020\u0001:\u0001\u001bB\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J\u0018\u0010\u0017\u001a\u00020\u00142\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u000bH\u0002R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u0007\u001a\u0010\u0012\u0004\u0012\u00020\b\u0012\u0006\u0012\u0004\u0018\u00010\t0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R \u0010\n\u001a\u0014\u0012\u0004\u0012\u00020\u000b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\f0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000e0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/actualizer/ActualDeclarationsCollector;", "", "()V", "actualClasses", "", "Lorg/jetbrains/kotlin/name/ClassId;", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "actualSymbolsToFile", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "actualTopLevels", "Lorg/jetbrains/kotlin/name/CallableId;", "", "actualTypeAliasesWithoutExpansion", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeAliasSymbol;", "currentFile", "visitedActualClasses", "", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "collect", "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/ir/IrElement;", "recordActualCallable", "callableDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationWithName;", "callableId", "Companion", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class ActualDeclarationsCollector {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private IrFile currentFile;
    private final Map<ClassId, IrClassSymbol> actualClasses = new LinkedHashMap();
    private final Map<ClassId, IrTypeAliasSymbol> actualTypeAliasesWithoutExpansion = new LinkedHashMap();
    private final Map<CallableId, List<IrSymbol>> actualTopLevels = new LinkedHashMap();
    private final Map<IrSymbol, IrFile> actualSymbolsToFile = new LinkedHashMap();
    private final Set<IrClass> visitedActualClasses = new LinkedHashSet();

    /* JADX INFO: compiled from: ExpectActualCollector.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0014\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/actualizer/ActualDeclarationsCollector$Companion;", "", "()V", "collectActualsFromFragments", "Lorg/jetbrains/kotlin/backend/common/actualizer/ClassActualizationInfo;", "fragments", "", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final ClassActualizationInfo collectActualsFromFragments(List<? extends IrModuleFragment> fragments) {
            Intrinsics.checkNotNullParameter(fragments, "fragments");
            ActualDeclarationsCollector actualDeclarationsCollector = new ActualDeclarationsCollector();
            Iterator<? extends IrModuleFragment> it = fragments.iterator();
            while (it.hasNext()) {
                actualDeclarationsCollector.collect(it.next());
            }
            return new ClassActualizationInfo(actualDeclarationsCollector.actualClasses, actualDeclarationsCollector.actualTypeAliasesWithoutExpansion, actualDeclarationsCollector.actualTopLevels, actualDeclarationsCollector.actualSymbolsToFile);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void collect(IrElement element) {
        if (element instanceof IrModuleFragment) {
            Iterator<IrFile> it = ((IrModuleFragment) element).getFiles().iterator();
            while (it.hasNext()) {
                collect(it.next());
            }
            return;
        }
        if (element instanceof IrFile) {
            IrFile irFile = (IrFile) element;
            this.currentFile = irFile;
            Iterator<IrDeclaration> it2 = irFile.getDeclarations().iterator();
            while (it2.hasNext()) {
                collect(it2.next());
            }
            return;
        }
        if (element instanceof IrTypeAlias) {
            IrTypeAlias irTypeAlias = (IrTypeAlias) element;
            if (irTypeAlias.getIsActual()) {
                ClassId classIdOrFail = AdditionalIrUtilsKt.getClassIdOrFail(irTypeAlias);
                IrClassifierSymbol classifierOrFail = IrTypesKt.getClassifierOrFail(irTypeAlias.getExpandedType());
                Intrinsics.checkNotNull(classifierOrFail, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrClassSymbol");
                IrClassSymbol irClassSymbol = (IrClassSymbol) classifierOrFail;
                this.actualClasses.put(classIdOrFail, irClassSymbol);
                this.actualTypeAliasesWithoutExpansion.put(classIdOrFail, irTypeAlias.getSymbol());
                this.actualSymbolsToFile.put(irClassSymbol, this.currentFile);
                this.actualSymbolsToFile.put(irTypeAlias.getSymbol(), this.currentFile);
                collect(irClassSymbol.getOwner());
                return;
            }
            return;
        }
        if (element instanceof IrClass) {
            IrClass irClass = (IrClass) element;
            if (irClass.getIsExpect() || !this.visitedActualClasses.add(element)) {
                return;
            }
            this.actualClasses.put(AdditionalIrUtilsKt.getClassIdOrFail(irClass), irClass.getSymbol());
            this.actualSymbolsToFile.put(irClass.getSymbol(), this.currentFile);
            Iterator<IrDeclaration> it3 = irClass.getDeclarations().iterator();
            while (it3.hasNext()) {
                collect(it3.next());
            }
            return;
        }
        if (element instanceof IrDeclarationContainer) {
            Iterator<IrDeclaration> it4 = ((IrDeclarationContainer) element).getDeclarations().iterator();
            while (it4.hasNext()) {
                collect(it4.next());
            }
            return;
        }
        if (element instanceof IrEnumEntry) {
            recordActualCallable((IrDeclarationWithName) element, AdditionalIrUtilsKt.getCallableId((IrEnumEntry) element));
            return;
        }
        if (element instanceof IrProperty) {
            IrProperty irProperty = (IrProperty) element;
            if (irProperty.getIsExpect()) {
                return;
            }
            recordActualCallable((IrDeclarationWithName) element, AdditionalIrUtilsKt.getCallableId(irProperty));
            return;
        }
        if (element instanceof IrFunction) {
            IrFunction irFunction = (IrFunction) element;
            if (irFunction.getIsExpect()) {
                return;
            }
            recordActualCallable((IrDeclarationWithName) element, AdditionalIrUtilsKt.getCallableId(irFunction));
        }
    }

    private final void recordActualCallable(IrDeclarationWithName callableDeclaration, CallableId callableId) {
        if (callableId.getClassId() == null) {
            Map<CallableId, List<IrSymbol>> map = this.actualTopLevels;
            ArrayList arrayList = map.get(callableId);
            if (arrayList == null) {
                arrayList = new ArrayList();
                map.put(callableId, arrayList);
            }
            arrayList.add(callableDeclaration.getSymbol());
            this.actualSymbolsToFile.put(callableDeclaration.getSymbol(), this.currentFile);
        }
    }
}
