package org.jetbrains.kotlin.psi2ir.generators;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.NotFoundClasses;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.linkage.IrProvider;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: DeclarationStubGeneratorImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0012\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/generators/DeclarationStubGeneratorForNotFoundClasses;", "Lorg/jetbrains/kotlin/ir/linkage/IrProvider;", "stubGenerator", "Lorg/jetbrains/kotlin/psi2ir/generators/DeclarationStubGeneratorImpl;", "(Lorg/jetbrains/kotlin/psi2ir/generators/DeclarationStubGeneratorImpl;)V", "getDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DeclarationStubGeneratorForNotFoundClasses implements IrProvider {
    private final DeclarationStubGeneratorImpl stubGenerator;

    public DeclarationStubGeneratorForNotFoundClasses(DeclarationStubGeneratorImpl stubGenerator) {
        Intrinsics.checkNotNullParameter(stubGenerator, "stubGenerator");
        this.stubGenerator = stubGenerator;
    }

    @Override // org.jetbrains.kotlin.ir.linkage.IrProvider
    public IrDeclaration getDeclaration(IrSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        if (symbol.isBound()) {
            return null;
        }
        DeclarationDescriptor descriptor = symbol.getDescriptor();
        NotFoundClasses.MockClassDescriptor mockClassDescriptor = descriptor instanceof NotFoundClasses.MockClassDescriptor ? (NotFoundClasses.MockClassDescriptor) descriptor : null;
        if (mockClassDescriptor == null) {
            return null;
        }
        return this.stubGenerator.generateClassStub(mockClassDescriptor);
    }
}
