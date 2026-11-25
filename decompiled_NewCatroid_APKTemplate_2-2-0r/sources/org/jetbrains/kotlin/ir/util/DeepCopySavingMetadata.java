package org.jetbrains.kotlin.ir.util;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrLocalDelegatedProperty;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;

/* JADX INFO: compiled from: DeepCopySavingMetadata.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0016J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000b\u001a\u00020\rH\u0016J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u000b\u001a\u00020\u000fH\u0016J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u000b\u001a\u00020\u0011H\u0016J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u000b\u001a\u00020\u0013H\u0016J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u000b\u001a\u00020\u0015H\u0016J\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u000b\u001a\u00020\u0017H\u0016¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/DeepCopySavingMetadata;", "Lorg/jetbrains/kotlin/ir/util/DeepCopyIrTreeWithSymbols;", "symbolRemapper", "Lorg/jetbrains/kotlin/ir/util/SymbolRemapper;", "typeRemapper", "Lorg/jetbrains/kotlin/ir/util/TypeRemapper;", "symbolRenamer", "Lorg/jetbrains/kotlin/ir/util/SymbolRenamer;", "(Lorg/jetbrains/kotlin/ir/util/SymbolRemapper;Lorg/jetbrains/kotlin/ir/util/TypeRemapper;Lorg/jetbrains/kotlin/ir/util/SymbolRenamer;)V", "visitClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "declaration", "visitConstructor", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "visitField", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "visitFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "visitLocalDelegatedProperty", "Lorg/jetbrains/kotlin/ir/declarations/IrLocalDelegatedProperty;", "visitProperty", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "visitSimpleFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class DeepCopySavingMetadata extends DeepCopyIrTreeWithSymbols {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeepCopySavingMetadata(SymbolRemapper symbolRemapper, TypeRemapper typeRemapper, SymbolRenamer symbolRenamer) {
        super(symbolRemapper, typeRemapper, symbolRenamer);
        Intrinsics.checkNotNullParameter(symbolRemapper, "symbolRemapper");
        Intrinsics.checkNotNullParameter(typeRemapper, "typeRemapper");
        Intrinsics.checkNotNullParameter(symbolRenamer, "symbolRenamer");
    }

    @Override // org.jetbrains.kotlin.ir.util.DeepCopyIrTreeWithSymbols, org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrFile visitFile(IrFile declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrFile irFileVisitFile = super.visitFile(declaration);
        irFileVisitFile.setMetadata(declaration.getMetadata());
        return irFileVisitFile;
    }

    @Override // org.jetbrains.kotlin.ir.util.DeepCopyIrTreeWithSymbols, org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrClass visitClass(IrClass declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrClass irClassVisitClass = super.visitClass(declaration);
        irClassVisitClass.setMetadata(declaration.getMetadata());
        return irClassVisitClass;
    }

    @Override // org.jetbrains.kotlin.ir.util.DeepCopyIrTreeWithSymbols, org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrConstructor visitConstructor(IrConstructor declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrConstructor irConstructorVisitConstructor = super.visitConstructor(declaration);
        irConstructorVisitConstructor.setMetadata(declaration.getMetadata());
        return irConstructorVisitConstructor;
    }

    @Override // org.jetbrains.kotlin.ir.util.DeepCopyIrTreeWithSymbols, org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrSimpleFunction visitSimpleFunction(IrSimpleFunction declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrSimpleFunction irSimpleFunctionVisitSimpleFunction = super.visitSimpleFunction(declaration);
        irSimpleFunctionVisitSimpleFunction.setMetadata(declaration.getMetadata());
        return irSimpleFunctionVisitSimpleFunction;
    }

    @Override // org.jetbrains.kotlin.ir.util.DeepCopyIrTreeWithSymbols, org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrProperty visitProperty(IrProperty declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrProperty irPropertyVisitProperty = super.visitProperty(declaration);
        irPropertyVisitProperty.setMetadata(declaration.getMetadata());
        return irPropertyVisitProperty;
    }

    @Override // org.jetbrains.kotlin.ir.util.DeepCopyIrTreeWithSymbols, org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrField visitField(IrField declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrField irFieldVisitField = super.visitField(declaration);
        irFieldVisitField.setMetadata(declaration.getMetadata());
        return irFieldVisitField;
    }

    @Override // org.jetbrains.kotlin.ir.util.DeepCopyIrTreeWithSymbols, org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrLocalDelegatedProperty visitLocalDelegatedProperty(IrLocalDelegatedProperty declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrLocalDelegatedProperty irLocalDelegatedPropertyVisitLocalDelegatedProperty = super.visitLocalDelegatedProperty(declaration);
        irLocalDelegatedPropertyVisitLocalDelegatedProperty.setMetadata(declaration.getMetadata());
        return irLocalDelegatedPropertyVisitLocalDelegatedProperty;
    }
}
