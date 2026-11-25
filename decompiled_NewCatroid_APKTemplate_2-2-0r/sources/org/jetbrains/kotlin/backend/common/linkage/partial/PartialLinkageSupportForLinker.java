package org.jetbrains.kotlin.backend.common.linkage.partial;

import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import org.jetbrains.kotlin.backend.common.overrides.FakeOverrideBuilder;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.util.SymbolTable;

/* JADX INFO: compiled from: PartialLinkageSupportForLinker.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\bf\u0018\u0000 \u001a2\u00020\u0001:\u0001\u001aJ\u000e\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H&J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH&J\u0010\u0010\f\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u000eH&J$\u0010\u000f\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\u00112\u0012\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00150\u00140\u0013H&J\u0018\u0010\u000f\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0016\u001a\u00020\u0017H&J\u0010\u0010\u0018\u001a\u00020\u00032\u0006\u0010\u0019\u001a\u00020\u0017H&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0002\u0010\u0004ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u001bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/linkage/partial/PartialLinkageSupportForLinker;", "", "isEnabled", "", "()Z", "collectAllStubbedSymbols", "", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "exploreClassifiers", "", "fakeOverrideBuilder", "Lorg/jetbrains/kotlin/backend/common/overrides/FakeOverrideBuilder;", "exploreClassifiersInInlineLazyIrFunction", "function", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "generateStubsAndPatchUsages", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", PsiTreeChangeEvent.PROP_ROOTS, "Lkotlin/Function0;", "Lkotlin/sequences/Sequence;", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "root", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "shouldBeSkipped", "declaration", "Companion", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface PartialLinkageSupportForLinker {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    Set<IrSymbol> collectAllStubbedSymbols();

    void exploreClassifiers(FakeOverrideBuilder fakeOverrideBuilder);

    void exploreClassifiersInInlineLazyIrFunction(IrFunction function);

    void generateStubsAndPatchUsages(SymbolTable symbolTable, Function0<? extends Sequence<? extends IrModuleFragment>> roots);

    void generateStubsAndPatchUsages(SymbolTable symbolTable, IrDeclaration root);

    boolean isEnabled();

    boolean shouldBeSkipped(IrDeclaration declaration);

    /* JADX INFO: compiled from: PartialLinkageSupportForLinker.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/linkage/partial/PartialLinkageSupportForLinker$Companion;", "", "()V", "DISABLED", "Lorg/jetbrains/kotlin/backend/common/linkage/partial/PartialLinkageSupportForLinker;", "getDISABLED", "()Lorg/jetbrains/kotlin/backend/common/linkage/partial/PartialLinkageSupportForLinker;", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();
        private static final PartialLinkageSupportForLinker DISABLED = new PartialLinkageSupportForLinker() { // from class: org.jetbrains.kotlin.backend.common.linkage.partial.PartialLinkageSupportForLinker$Companion$DISABLED$1
            @Override // org.jetbrains.kotlin.backend.common.linkage.partial.PartialLinkageSupportForLinker
            public void exploreClassifiers(FakeOverrideBuilder fakeOverrideBuilder) {
                Intrinsics.checkNotNullParameter(fakeOverrideBuilder, "fakeOverrideBuilder");
            }

            @Override // org.jetbrains.kotlin.backend.common.linkage.partial.PartialLinkageSupportForLinker
            public void exploreClassifiersInInlineLazyIrFunction(IrFunction function) {
                Intrinsics.checkNotNullParameter(function, "function");
            }

            @Override // org.jetbrains.kotlin.backend.common.linkage.partial.PartialLinkageSupportForLinker
            public void generateStubsAndPatchUsages(SymbolTable symbolTable, Function0<? extends Sequence<? extends IrModuleFragment>> roots) {
                Intrinsics.checkNotNullParameter(symbolTable, "symbolTable");
                Intrinsics.checkNotNullParameter(roots, "roots");
            }

            @Override // org.jetbrains.kotlin.backend.common.linkage.partial.PartialLinkageSupportForLinker
            public void generateStubsAndPatchUsages(SymbolTable symbolTable, IrDeclaration root) {
                Intrinsics.checkNotNullParameter(symbolTable, "symbolTable");
                Intrinsics.checkNotNullParameter(root, "root");
            }

            @Override // org.jetbrains.kotlin.backend.common.linkage.partial.PartialLinkageSupportForLinker
            public boolean isEnabled() {
                return false;
            }

            @Override // org.jetbrains.kotlin.backend.common.linkage.partial.PartialLinkageSupportForLinker
            public boolean shouldBeSkipped(IrDeclaration declaration) {
                Intrinsics.checkNotNullParameter(declaration, "declaration");
                return true;
            }

            @Override // org.jetbrains.kotlin.backend.common.linkage.partial.PartialLinkageSupportForLinker
            public Set<IrSymbol> collectAllStubbedSymbols() {
                return SetsKt.emptySet();
            }
        };

        private Companion() {
        }

        public final PartialLinkageSupportForLinker getDISABLED() {
            return DISABLED;
        }
    }
}
