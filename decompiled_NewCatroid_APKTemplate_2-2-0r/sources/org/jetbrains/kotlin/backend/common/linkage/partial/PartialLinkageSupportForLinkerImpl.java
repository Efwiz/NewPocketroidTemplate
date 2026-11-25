package org.jetbrains.kotlin.backend.common.linkage.partial;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import org.jetbrains.kotlin.backend.common.overrides.FakeOverrideBuilder;
import org.jetbrains.kotlin.backend.common.serialization.CompatibilityMode;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageLogger;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.util.SymbolTable;

/* JADX INFO: compiled from: PartialLinkageSupportForLinkerImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u000e\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012H\u0016J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\u0010\u0010\u0018\u001a\u00020\u00152\u0006\u0010\u0019\u001a\u00020\u001aH\u0016J$\u0010\u001b\u001a\u00020\u00152\u0006\u0010\u001c\u001a\u00020\u001d2\u0012\u0010\u001e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020!0 0\u001fH\u0016J\u0018\u0010\u001b\u001a\u00020\u00152\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\"\u001a\u00020#H\u0016J\u001e\u0010$\u001a\u00020\u00152\u0006\u0010\u001c\u001a\u00020\u001d2\f\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00150\u001fH\u0002J\u0010\u0010&\u001a\u00020\u00052\u0006\u0010'\u001a\u00020#H\u0016R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\u00020\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006("}, d2 = {"Lorg/jetbrains/kotlin/backend/common/linkage/partial/PartialLinkageSupportForLinkerImpl;", "Lorg/jetbrains/kotlin/backend/common/linkage/partial/PartialLinkageSupportForLinker;", "builtIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "allowErrorTypes", "", "logger", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageLogger;", "(Lorg/jetbrains/kotlin/ir/IrBuiltIns;ZLorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageLogger;)V", "classifierExplorer", "Lorg/jetbrains/kotlin/backend/common/linkage/partial/ClassifierExplorer;", "isEnabled", "()Z", "patcher", "Lorg/jetbrains/kotlin/backend/common/linkage/partial/PartiallyLinkedIrTreePatcher;", "stubGenerator", "Lorg/jetbrains/kotlin/backend/common/linkage/partial/MissingDeclarationStubGenerator;", "collectAllStubbedSymbols", "", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "exploreClassifiers", "", "fakeOverrideBuilder", "Lorg/jetbrains/kotlin/backend/common/overrides/FakeOverrideBuilder;", "exploreClassifiersInInlineLazyIrFunction", "function", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "generateStubsAndPatchUsages", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", PsiTreeChangeEvent.PROP_ROOTS, "Lkotlin/Function0;", "Lkotlin/sequences/Sequence;", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "root", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "generateStubsAndPatchUsagesInternal", "patchIrTree", "shouldBeSkipped", "declaration", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PartialLinkageSupportForLinkerImpl implements PartialLinkageSupportForLinker {
    private final ClassifierExplorer classifierExplorer;
    private final PartiallyLinkedIrTreePatcher patcher;
    private final MissingDeclarationStubGenerator stubGenerator;

    @Override // org.jetbrains.kotlin.backend.common.linkage.partial.PartialLinkageSupportForLinker
    public boolean isEnabled() {
        return true;
    }

    public PartialLinkageSupportForLinkerImpl(IrBuiltIns builtIns, boolean z, PartialLinkageLogger logger) {
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        Intrinsics.checkNotNullParameter(logger, "logger");
        MissingDeclarationStubGenerator missingDeclarationStubGenerator = new MissingDeclarationStubGenerator(builtIns);
        this.stubGenerator = missingDeclarationStubGenerator;
        ClassifierExplorer classifierExplorer = new ClassifierExplorer(builtIns, missingDeclarationStubGenerator, z);
        this.classifierExplorer = classifierExplorer;
        this.patcher = new PartiallyLinkedIrTreePatcher(builtIns, classifierExplorer, missingDeclarationStubGenerator, logger);
    }

    @Override // org.jetbrains.kotlin.backend.common.linkage.partial.PartialLinkageSupportForLinker
    public boolean shouldBeSkipped(IrDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return this.patcher.shouldBeSkipped(declaration);
    }

    @Override // org.jetbrains.kotlin.backend.common.linkage.partial.PartialLinkageSupportForLinker
    public void exploreClassifiers(FakeOverrideBuilder fakeOverrideBuilder) {
        Intrinsics.checkNotNullParameter(fakeOverrideBuilder, "fakeOverrideBuilder");
        Map<IrClass, CompatibilityMode> fakeOverrideCandidates = fakeOverrideBuilder.getFakeOverrideCandidates();
        if (fakeOverrideCandidates.isEmpty()) {
            return;
        }
        Set setCreateSetBuilder = SetsKt.createSetBuilder();
        for (IrClass irClass : fakeOverrideCandidates.keySet()) {
            if (this.classifierExplorer.exploreSymbol(irClass.getSymbol()) != null) {
                setCreateSetBuilder.add(irClass);
            }
        }
        CollectionsKt.removeAll((Collection) fakeOverrideCandidates.keySet(), (Iterable) SetsKt.build(setCreateSetBuilder));
    }

    @Override // org.jetbrains.kotlin.backend.common.linkage.partial.PartialLinkageSupportForLinker
    public void exploreClassifiersInInlineLazyIrFunction(IrFunction function) {
        Intrinsics.checkNotNullParameter(function, "function");
        this.classifierExplorer.exploreIrElement(function);
    }

    @Override // org.jetbrains.kotlin.backend.common.linkage.partial.PartialLinkageSupportForLinker
    public void generateStubsAndPatchUsages(SymbolTable symbolTable, final Function0<? extends Sequence<? extends IrModuleFragment>> roots) {
        Intrinsics.checkNotNullParameter(symbolTable, "symbolTable");
        Intrinsics.checkNotNullParameter(roots, "roots");
        generateStubsAndPatchUsagesInternal(symbolTable, new Function0<Unit>() { // from class: org.jetbrains.kotlin.backend.common.linkage.partial.PartialLinkageSupportForLinkerImpl.generateStubsAndPatchUsages.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                PartialLinkageSupportForLinkerImpl.this.patcher.patchModuleFragments(roots.invoke());
            }
        });
    }

    @Override // org.jetbrains.kotlin.backend.common.linkage.partial.PartialLinkageSupportForLinker
    public void generateStubsAndPatchUsages(SymbolTable symbolTable, final IrDeclaration root) {
        Intrinsics.checkNotNullParameter(symbolTable, "symbolTable");
        Intrinsics.checkNotNullParameter(root, "root");
        generateStubsAndPatchUsagesInternal(symbolTable, new Function0<Unit>() { // from class: org.jetbrains.kotlin.backend.common.linkage.partial.PartialLinkageSupportForLinkerImpl.generateStubsAndPatchUsages.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                PartialLinkageSupportForLinkerImpl.this.patcher.patchDeclarations(CollectionsKt.listOf(root));
            }
        });
    }

    @Override // org.jetbrains.kotlin.backend.common.linkage.partial.PartialLinkageSupportForLinker
    public Set<IrSymbol> collectAllStubbedSymbols() {
        return this.stubGenerator.getAllStubbedSymbols();
    }

    private final void generateStubsAndPatchUsagesInternal(SymbolTable symbolTable, Function0<Unit> patchIrTree) {
        Iterator<IrSymbol> it = symbolTable.getDescriptorExtension().getAllUnboundSymbols().iterator();
        while (it.hasNext()) {
            this.stubGenerator.getDeclaration(it.next());
        }
        patchIrTree.invoke();
        this.patcher.patchDeclarations(this.stubGenerator.grabDeclarationsToPatch());
    }
}
