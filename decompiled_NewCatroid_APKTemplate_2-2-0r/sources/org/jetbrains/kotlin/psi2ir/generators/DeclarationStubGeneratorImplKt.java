package org.jetbrains.kotlin.psi2ir.generators;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.linkage.IrDeserializer;
import org.jetbrains.kotlin.ir.linkage.IrProvider;
import org.jetbrains.kotlin.ir.util.DescriptorByIdSignatureFinder;
import org.jetbrains.kotlin.ir.util.StubGeneratorExtensions;
import org.jetbrains.kotlin.ir.util.SymbolTable;

/* JADX INFO: compiled from: DeclarationStubGeneratorImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001aB\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f2\b\b\u0002\u0010\r\u001a\u00020\u000e¨\u0006\u000f"}, d2 = {"generateTypicalIrProviderList", "", "Lorg/jetbrains/kotlin/ir/linkage/IrProvider;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "irBuiltins", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "descriptorFinder", "Lorg/jetbrains/kotlin/ir/util/DescriptorByIdSignatureFinder;", "deserializer", "Lorg/jetbrains/kotlin/ir/linkage/IrDeserializer;", "extensions", "Lorg/jetbrains/kotlin/ir/util/StubGeneratorExtensions;", "ir.psi2ir"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DeclarationStubGeneratorImplKt {
    public static /* synthetic */ List generateTypicalIrProviderList$default(ModuleDescriptor moduleDescriptor, IrBuiltIns irBuiltIns, SymbolTable symbolTable, DescriptorByIdSignatureFinder descriptorByIdSignatureFinder, IrDeserializer irDeserializer, StubGeneratorExtensions stubGeneratorExtensions, int i, Object obj) {
        if ((i & 16) != 0) {
            irDeserializer = null;
        }
        IrDeserializer irDeserializer2 = irDeserializer;
        if ((i & 32) != 0) {
            stubGeneratorExtensions = StubGeneratorExtensions.EMPTY;
        }
        return generateTypicalIrProviderList(moduleDescriptor, irBuiltIns, symbolTable, descriptorByIdSignatureFinder, irDeserializer2, stubGeneratorExtensions);
    }

    public static final List<IrProvider> generateTypicalIrProviderList(ModuleDescriptor moduleDescriptor, IrBuiltIns irBuiltins, SymbolTable symbolTable, DescriptorByIdSignatureFinder descriptorFinder, IrDeserializer irDeserializer, StubGeneratorExtensions extensions) {
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        Intrinsics.checkNotNullParameter(irBuiltins, "irBuiltins");
        Intrinsics.checkNotNullParameter(symbolTable, "symbolTable");
        Intrinsics.checkNotNullParameter(descriptorFinder, "descriptorFinder");
        Intrinsics.checkNotNullParameter(extensions, "extensions");
        return CollectionsKt.listOfNotNull((Object[]) new IrProvider[]{irDeserializer, new DeclarationStubGeneratorImpl(moduleDescriptor, symbolTable, irBuiltins, descriptorFinder, extensions)});
    }
}
