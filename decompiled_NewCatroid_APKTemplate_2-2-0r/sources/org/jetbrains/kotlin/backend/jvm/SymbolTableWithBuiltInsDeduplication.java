package org.jetbrains.kotlin.backend.jvm;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorUtilKt;
import org.jetbrains.kotlin.ir.ObsoleteDescriptorBasedAPI;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.util.DescriptorSymbolTableExtension;
import org.jetbrains.kotlin.ir.util.IdSignatureComposer;
import org.jetbrains.kotlin.ir.util.SymbolTable;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi2ir.descriptors.IrBuiltInsOverDescriptors;

/* JADX INFO: compiled from: SymbolTableWithBuiltInsDeduplication.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0001\u0010B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\u0007\u001a\u00020\bJ\b\u0010\u000b\u001a\u00020\fH\u0014J\u0016\u0010\r\u001a\u0004\u0018\u00010\u000e*\u00020\b2\u0006\u0010\u000f\u001a\u00020\u000eH\u0002R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/SymbolTableWithBuiltInsDeduplication;", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "signaturer", "Lorg/jetbrains/kotlin/ir/util/IdSignatureComposer;", "irFactory", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "(Lorg/jetbrains/kotlin/ir/util/IdSignatureComposer;Lorg/jetbrains/kotlin/ir/declarations/IrFactory;)V", "irBuiltIns", "Lorg/jetbrains/kotlin/psi2ir/descriptors/IrBuiltInsOverDescriptors;", "bindIrBuiltIns", "", "createDescriptorExtension", "Lorg/jetbrains/kotlin/ir/util/DescriptorSymbolTableExtension;", "findBuiltInClassDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "descriptor", "Extension", "backend.jvm.entrypoint"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SymbolTableWithBuiltInsDeduplication extends SymbolTable {
    private IrBuiltInsOverDescriptors irBuiltIns;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SymbolTableWithBuiltInsDeduplication(IdSignatureComposer signaturer, IrFactory irFactory) {
        super(signaturer, irFactory, null, 4, null);
        Intrinsics.checkNotNullParameter(signaturer, "signaturer");
        Intrinsics.checkNotNullParameter(irFactory, "irFactory");
    }

    public final void bindIrBuiltIns(IrBuiltInsOverDescriptors irBuiltIns) {
        Intrinsics.checkNotNullParameter(irBuiltIns, "irBuiltIns");
        if (this.irBuiltIns == null) {
            this.irBuiltIns = irBuiltIns;
            return;
        }
        throw new IllegalStateException("`irBuiltIns` have already been bound.");
    }

    @Override // org.jetbrains.kotlin.ir.util.SymbolTable
    protected DescriptorSymbolTableExtension createDescriptorExtension() {
        return new Extension();
    }

    /* JADX INFO: compiled from: SymbolTableWithBuiltInsDeduplication.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0017¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/SymbolTableWithBuiltInsDeduplication$Extension;", "Lorg/jetbrains/kotlin/ir/util/DescriptorSymbolTableExtension;", "(Lorg/jetbrains/kotlin/backend/jvm/SymbolTableWithBuiltInsDeduplication;)V", "referenceClass", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "declaration", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "backend.jvm.entrypoint"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class Extension extends DescriptorSymbolTableExtension {
        public Extension() {
            super(SymbolTableWithBuiltInsDeduplication.this);
        }

        @Override // org.jetbrains.kotlin.ir.util.DescriptorSymbolTableExtension, org.jetbrains.kotlin.ir.util.SymbolTableExtension, org.jetbrains.kotlin.ir.util.ReferenceSymbolTableExtension
        @ObsoleteDescriptorBasedAPI
        public IrClassSymbol referenceClass(ClassDescriptor declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            IrBuiltInsOverDescriptors irBuiltInsOverDescriptors = SymbolTableWithBuiltInsDeduplication.this.irBuiltIns;
            if (irBuiltInsOverDescriptors == null) {
                return super.referenceClass(declaration);
            }
            ClassDescriptor classDescriptorFindBuiltInClassDescriptor = SymbolTableWithBuiltInsDeduplication.this.findBuiltInClassDescriptor(irBuiltInsOverDescriptors, declaration);
            if (classDescriptorFindBuiltInClassDescriptor != null) {
                return super.referenceClass(classDescriptorFindBuiltInClassDescriptor);
            }
            return super.referenceClass(declaration);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ClassDescriptor findBuiltInClassDescriptor(IrBuiltInsOverDescriptors irBuiltInsOverDescriptors, ClassDescriptor classDescriptor) {
        FqName fqNameContainingPackage = DescriptorUtilKt.containingPackage(classDescriptor);
        if (fqNameContainingPackage == null) {
            return null;
        }
        Name name2 = classDescriptor.getShortName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        return irBuiltInsOverDescriptors.findClassDescriptor(name2, fqNameContainingPackage);
    }
}
