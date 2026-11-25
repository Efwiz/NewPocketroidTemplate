package org.jetbrains.kotlin.psi2ir.generators;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.declarations.lazy.LazyScopedTypeParametersResolver;
import org.jetbrains.kotlin.ir.util.DeclarationStubGenerator;
import org.jetbrains.kotlin.ir.util.DescriptorByIdSignatureFinder;
import org.jetbrains.kotlin.ir.util.StubGeneratorExtensions;
import org.jetbrains.kotlin.ir.util.SymbolTable;
import org.jetbrains.kotlin.ir.util.TypeParametersResolver;
import org.jetbrains.kotlin.ir.util.TypeTranslator;

/* JADX INFO: compiled from: DeclarationStubGeneratorImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B/\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fR\u0014\u0010\b\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\u0010X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/generators/DeclarationStubGeneratorImpl;", "Lorg/jetbrains/kotlin/ir/util/DeclarationStubGenerator;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "irBuiltins", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "descriptorFinder", "Lorg/jetbrains/kotlin/ir/util/DescriptorByIdSignatureFinder;", "extensions", "Lorg/jetbrains/kotlin/ir/util/StubGeneratorExtensions;", "(Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/ir/util/SymbolTable;Lorg/jetbrains/kotlin/ir/IrBuiltIns;Lorg/jetbrains/kotlin/ir/util/DescriptorByIdSignatureFinder;Lorg/jetbrains/kotlin/ir/util/StubGeneratorExtensions;)V", "getDescriptorFinder", "()Lorg/jetbrains/kotlin/ir/util/DescriptorByIdSignatureFinder;", "typeTranslator", "Lorg/jetbrains/kotlin/ir/util/TypeTranslator;", "getTypeTranslator", "()Lorg/jetbrains/kotlin/ir/util/TypeTranslator;", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DeclarationStubGeneratorImpl extends DeclarationStubGenerator {
    private final DescriptorByIdSignatureFinder descriptorFinder;
    private final TypeTranslator typeTranslator;

    @Override // org.jetbrains.kotlin.ir.util.DeclarationStubGenerator
    public DescriptorByIdSignatureFinder getDescriptorFinder() {
        return this.descriptorFinder;
    }

    public /* synthetic */ DeclarationStubGeneratorImpl(ModuleDescriptor moduleDescriptor, SymbolTable symbolTable, IrBuiltIns irBuiltIns, DescriptorByIdSignatureFinder descriptorByIdSignatureFinder, StubGeneratorExtensions stubGeneratorExtensions, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(moduleDescriptor, symbolTable, irBuiltIns, descriptorByIdSignatureFinder, (i & 16) != 0 ? StubGeneratorExtensions.EMPTY : stubGeneratorExtensions);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeclarationStubGeneratorImpl(ModuleDescriptor moduleDescriptor, SymbolTable symbolTable, IrBuiltIns irBuiltins, DescriptorByIdSignatureFinder descriptorFinder, StubGeneratorExtensions extensions) {
        super(moduleDescriptor, symbolTable, irBuiltins, extensions);
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        Intrinsics.checkNotNullParameter(symbolTable, "symbolTable");
        Intrinsics.checkNotNullParameter(irBuiltins, "irBuiltins");
        Intrinsics.checkNotNullParameter(descriptorFinder, "descriptorFinder");
        Intrinsics.checkNotNullParameter(extensions, "extensions");
        this.descriptorFinder = descriptorFinder;
        this.typeTranslator = new TypeTranslatorImpl(getLazyTable(), irBuiltins.getLanguageVersionSettings(), moduleDescriptor, new Function0<TypeParametersResolver>() { // from class: org.jetbrains.kotlin.psi2ir.generators.DeclarationStubGeneratorImpl$typeTranslator$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final TypeParametersResolver invoke() {
                return new LazyScopedTypeParametersResolver(this.this$0.getLazyTable());
            }
        }, true, extensions, null, false, 192, null);
    }

    @Override // org.jetbrains.kotlin.ir.util.DeclarationStubGenerator
    public TypeTranslator getTypeTranslator() {
        return this.typeTranslator;
    }
}
