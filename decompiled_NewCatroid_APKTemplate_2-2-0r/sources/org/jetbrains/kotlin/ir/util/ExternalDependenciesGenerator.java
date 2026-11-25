package org.jetbrains.kotlin.ir.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.linkage.IrProvider;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;

/* JADX INFO: compiled from: ExternalDependenciesGenerator.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\u0006\u0010\b\u001a\u00020\tR\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/ExternalDependenciesGenerator;", "", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "irProviders", "", "Lorg/jetbrains/kotlin/ir/linkage/IrProvider;", "(Lorg/jetbrains/kotlin/ir/util/SymbolTable;Ljava/util/List;)V", "generateUnboundSymbolsAsDependencies", "", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ExternalDependenciesGenerator {
    private final List<IrProvider> irProviders;
    private final SymbolTable symbolTable;

    /* JADX WARN: Multi-variable type inference failed */
    public ExternalDependenciesGenerator(SymbolTable symbolTable, List<? extends IrProvider> irProviders) {
        Intrinsics.checkNotNullParameter(symbolTable, "symbolTable");
        Intrinsics.checkNotNullParameter(irProviders, "irProviders");
        this.symbolTable = symbolTable;
        this.irProviders = irProviders;
    }

    public final void generateUnboundSymbolsAsDependencies() {
        List<IrProvider> list = this.irProviders;
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            if (obj instanceof DeclarationStubGenerator) {
                arrayList.add(obj);
            }
        }
        DeclarationStubGenerator declarationStubGenerator = (DeclarationStubGenerator) CollectionsKt.singleOrNull((List) arrayList);
        if (declarationStubGenerator != null) {
            declarationStubGenerator.setUnboundSymbolGeneration(true);
        }
        Set<IrSymbol> setEmptySet = SetsKt.emptySet();
        while (true) {
            Set<IrSymbol> allUnboundSymbols = this.symbolTable.getDescriptorExtension().getAllUnboundSymbols();
            for (IrSymbol irSymbol : allUnboundSymbols) {
                if (!irSymbol.isBound()) {
                    Iterator<T> it = this.irProviders.iterator();
                    while (it.hasNext() && ((IrProvider) it.next()).getDeclaration(irSymbol) == null) {
                    }
                }
            }
            if (Intrinsics.areEqual(allUnboundSymbols, setEmptySet)) {
                return;
            } else {
                setEmptySet = allUnboundSymbols;
            }
        }
    }
}
