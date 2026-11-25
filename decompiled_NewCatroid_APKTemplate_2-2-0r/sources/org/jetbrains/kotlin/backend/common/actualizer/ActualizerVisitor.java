package org.jetbrains.kotlin.backend.common.actualizer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrExternalPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrLocalDelegatedProperty;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrScript;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeAlias;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.DeepCopyIrTreeWithSymbols;
import org.jetbrains.kotlin.ir.util.SymbolRemapper;
import org.jetbrains.kotlin.ir.util.SymbolRenamer;
import org.jetbrains.kotlin.ir.util.TypeRemapper;

/* JADX INFO: compiled from: ExpectActualLinker.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0080\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0010\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bH\u0016J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\t\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\rH\u0016J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\t\u001a\u00020\u000fH\u0016J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0016J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\t\u001a\u00020\u0013H\u0016J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\t\u001a\u00020\u0015H\u0016J\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\t\u001a\u00020\u0017H\u0016J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\t\u001a\u00020\u0019H\u0016J\u0010\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\t\u001a\u00020\u001bH\u0016J\u0010\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\t\u001a\u00020\u001dH\u0016J\u0010\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\t\u001a\u00020\u001fH\u0016J\u0010\u0010 \u001a\u00020!2\u0006\u0010\t\u001a\u00020!H\u0016J\u0010\u0010\"\u001a\u00020#2\u0006\u0010\t\u001a\u00020#H\u0016J\u0010\u0010$\u001a\u00020%2\u0006\u0010\t\u001a\u00020%H\u0016J\u0010\u0010&\u001a\u00020'2\u0006\u0010\t\u001a\u00020'H\u0016J\u0010\u0010(\u001a\u00020)2\u0006\u0010\t\u001a\u00020)H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006*"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/actualizer/ActualizerVisitor;", "Lorg/jetbrains/kotlin/ir/util/DeepCopyIrTreeWithSymbols;", "symbolRemapper", "Lorg/jetbrains/kotlin/ir/util/SymbolRemapper;", "typeRemapper", "Lorg/jetbrains/kotlin/ir/util/TypeRemapper;", "(Lorg/jetbrains/kotlin/ir/util/SymbolRemapper;Lorg/jetbrains/kotlin/ir/util/TypeRemapper;)V", "visitAnonymousInitializer", "Lorg/jetbrains/kotlin/ir/declarations/IrAnonymousInitializer;", "declaration", "visitClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "visitConstructor", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "visitEnumEntry", "Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "visitExternalPackageFragment", "Lorg/jetbrains/kotlin/ir/declarations/IrExternalPackageFragment;", "visitField", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "visitFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "visitFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "visitLocalDelegatedProperty", "Lorg/jetbrains/kotlin/ir/declarations/IrLocalDelegatedProperty;", "visitModuleFragment", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "visitProperty", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "visitScript", "Lorg/jetbrains/kotlin/ir/declarations/IrScript;", "visitSimpleFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "visitTypeAlias", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeAlias;", "visitTypeParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "visitValueParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "visitVariable", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class ActualizerVisitor extends DeepCopyIrTreeWithSymbols {
    private final SymbolRemapper symbolRemapper;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ActualizerVisitor(SymbolRemapper symbolRemapper, TypeRemapper typeRemapper) {
        super(symbolRemapper, typeRemapper, SymbolRenamer.DEFAULT.INSTANCE);
        Intrinsics.checkNotNullParameter(symbolRemapper, "symbolRemapper");
        Intrinsics.checkNotNullParameter(typeRemapper, "typeRemapper");
        this.symbolRemapper = symbolRemapper;
    }

    @Override // org.jetbrains.kotlin.ir.util.DeepCopyIrTreeWithSymbols, org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrModuleFragment visitModuleFragment(IrModuleFragment declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        declaration.transformChildren(this, null);
        return declaration;
    }

    @Override // org.jetbrains.kotlin.ir.util.DeepCopyIrTreeWithSymbols, org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExternalPackageFragment visitExternalPackageFragment(IrExternalPackageFragment declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        declaration.transformChildren(this, null);
        return declaration;
    }

    @Override // org.jetbrains.kotlin.ir.util.DeepCopyIrTreeWithSymbols, org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrFile visitFile(IrFile declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        declaration.transformChildren(this, null);
        transformAnnotations(declaration, declaration);
        return declaration;
    }

    @Override // org.jetbrains.kotlin.ir.util.DeepCopyIrTreeWithSymbols, org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrScript visitScript(IrScript declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrType baseClass = declaration.getBaseClass();
        declaration.setBaseClass(baseClass != null ? remapType(baseClass) : null);
        declaration.transformChildren(this, null);
        return declaration;
    }

    @Override // org.jetbrains.kotlin.ir.util.DeepCopyIrTreeWithSymbols, org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrClass visitClass(IrClass declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        List<IrType> superTypes = declaration.getSuperTypes();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(superTypes, 10));
        Iterator<T> it = superTypes.iterator();
        while (it.hasNext()) {
            arrayList.add(remapType((IrType) it.next()));
        }
        declaration.setSuperTypes(arrayList);
        declaration.transformChildren(this, null);
        transformAnnotations(declaration, declaration);
        return declaration;
    }

    @Override // org.jetbrains.kotlin.ir.util.DeepCopyIrTreeWithSymbols, org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrSimpleFunction visitSimpleFunction(IrSimpleFunction declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrFunction irFunctionVisitFunction = visitFunction((IrFunction) declaration);
        Intrinsics.checkNotNull(irFunctionVisitFunction, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrSimpleFunction");
        IrSimpleFunction irSimpleFunction = (IrSimpleFunction) irFunctionVisitFunction;
        List<IrSimpleFunctionSymbol> overriddenSymbols = irSimpleFunction.getOverriddenSymbols();
        ArrayList arrayList = new ArrayList(overriddenSymbols.size());
        Iterator<T> it = overriddenSymbols.iterator();
        while (it.hasNext()) {
            IrFunctionSymbol referencedFunction = this.symbolRemapper.getReferencedFunction((IrSimpleFunctionSymbol) it.next());
            Intrinsics.checkNotNull(referencedFunction, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol");
            arrayList.add((IrSimpleFunctionSymbol) referencedFunction);
        }
        irSimpleFunction.setOverriddenSymbols(org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList));
        return irSimpleFunction;
    }

    @Override // org.jetbrains.kotlin.ir.util.DeepCopyIrTreeWithSymbols, org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrConstructor visitConstructor(IrConstructor declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrFunction irFunctionVisitFunction = visitFunction((IrFunction) declaration);
        Intrinsics.checkNotNull(irFunctionVisitFunction, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrConstructor");
        return (IrConstructor) irFunctionVisitFunction;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrFunction visitFunction(IrFunction declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        declaration.setReturnType(remapType(declaration.getReturnType()));
        declaration.transformChildren(this, null);
        transformAnnotations(declaration, declaration);
        return declaration;
    }

    @Override // org.jetbrains.kotlin.ir.util.DeepCopyIrTreeWithSymbols, org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrProperty visitProperty(IrProperty declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        declaration.transformChildren(this, null);
        List<IrPropertySymbol> overriddenSymbols = declaration.getOverriddenSymbols();
        ArrayList arrayList = new ArrayList(overriddenSymbols.size());
        Iterator<T> it = overriddenSymbols.iterator();
        while (it.hasNext()) {
            arrayList.add(this.symbolRemapper.getReferencedProperty((IrPropertySymbol) it.next()));
        }
        declaration.setOverriddenSymbols(org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList));
        transformAnnotations(declaration, declaration);
        return declaration;
    }

    @Override // org.jetbrains.kotlin.ir.util.DeepCopyIrTreeWithSymbols, org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrField visitField(IrField declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        declaration.setType(remapType(declaration.getType()));
        declaration.transformChildren(this, null);
        transformAnnotations(declaration, declaration);
        return declaration;
    }

    @Override // org.jetbrains.kotlin.ir.util.DeepCopyIrTreeWithSymbols, org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrLocalDelegatedProperty visitLocalDelegatedProperty(IrLocalDelegatedProperty declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        declaration.setType(remapType(declaration.getType()));
        declaration.transformChildren(this, null);
        return declaration;
    }

    @Override // org.jetbrains.kotlin.ir.util.DeepCopyIrTreeWithSymbols, org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrEnumEntry visitEnumEntry(IrEnumEntry declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        declaration.transformChildren(this, null);
        transformAnnotations(declaration, declaration);
        return declaration;
    }

    @Override // org.jetbrains.kotlin.ir.util.DeepCopyIrTreeWithSymbols, org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrTypeParameter visitTypeParameter(IrTypeParameter declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        List<IrType> superTypes = declaration.getSuperTypes();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(superTypes, 10));
        Iterator<T> it = superTypes.iterator();
        while (it.hasNext()) {
            arrayList.add(remapType((IrType) it.next()));
        }
        declaration.setSuperTypes(arrayList);
        declaration.transformChildren(this, null);
        transformAnnotations(declaration, declaration);
        return declaration;
    }

    @Override // org.jetbrains.kotlin.ir.util.DeepCopyIrTreeWithSymbols, org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrValueParameter visitValueParameter(IrValueParameter declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        declaration.setType(remapType(declaration.getType()));
        IrType varargElementType = declaration.getVarargElementType();
        declaration.setVarargElementType(varargElementType != null ? remapType(varargElementType) : null);
        declaration.transformChildren(this, null);
        transformAnnotations(declaration, declaration);
        return declaration;
    }

    @Override // org.jetbrains.kotlin.ir.util.DeepCopyIrTreeWithSymbols, org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrAnonymousInitializer visitAnonymousInitializer(IrAnonymousInitializer declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        declaration.transformChildren(this, null);
        return declaration;
    }

    @Override // org.jetbrains.kotlin.ir.util.DeepCopyIrTreeWithSymbols, org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrVariable visitVariable(IrVariable declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        declaration.setType(remapType(declaration.getType()));
        declaration.transformChildren(this, null);
        transformAnnotations(declaration, declaration);
        return declaration;
    }

    @Override // org.jetbrains.kotlin.ir.util.DeepCopyIrTreeWithSymbols, org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrTypeAlias visitTypeAlias(IrTypeAlias declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        declaration.setExpandedType(remapType(declaration.getExpandedType()));
        declaration.transformChildren(this, null);
        transformAnnotations(declaration, declaration);
        return declaration;
    }
}
