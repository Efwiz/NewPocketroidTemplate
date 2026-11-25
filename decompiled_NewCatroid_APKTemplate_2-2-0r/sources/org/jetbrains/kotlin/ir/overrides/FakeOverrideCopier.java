package org.jetbrains.kotlin.ir.overrides;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFunctionWithLateBinding;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrPropertyWithLateBinding;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.linkage.partial.IrUnimplementedOverridesStrategy;
import org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.DeepCopyIrTreeWithSymbols;
import org.jetbrains.kotlin.ir.util.SymbolRemapper;
import org.jetbrains.kotlin.ir.util.SymbolRenamer;
import org.jetbrains.kotlin.ir.util.TypeRemapper;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.utils.CollectionsKt;

/* JADX INFO: compiled from: FakeOverrideCopier.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r¢\u0006\u0002\u0010\u000eJ\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0010H\u0016J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0011\u001a\u00020\u0013H\u0016J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0011\u001a\u00020\u0015H\u0016R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/ir/overrides/FakeOverrideCopier;", "Lorg/jetbrains/kotlin/ir/util/DeepCopyIrTreeWithSymbols;", "symbolRemapper", "Lorg/jetbrains/kotlin/ir/util/SymbolRemapper;", "typeRemapper", "Lorg/jetbrains/kotlin/ir/util/TypeRemapper;", "symbolRenamer", "Lorg/jetbrains/kotlin/ir/util/SymbolRenamer;", "makeExternal", "", "parent", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "unimplementedOverridesStrategy", "Lorg/jetbrains/kotlin/ir/linkage/partial/IrUnimplementedOverridesStrategy;", "(Lorg/jetbrains/kotlin/ir/util/SymbolRemapper;Lorg/jetbrains/kotlin/ir/util/TypeRemapper;Lorg/jetbrains/kotlin/ir/util/SymbolRenamer;ZLorg/jetbrains/kotlin/ir/declarations/IrClass;Lorg/jetbrains/kotlin/ir/linkage/partial/IrUnimplementedOverridesStrategy;)V", "visitProperty", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "declaration", "visitSimpleFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "visitValueParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FakeOverrideCopier extends DeepCopyIrTreeWithSymbols {
    private final boolean makeExternal;
    private final IrClass parent;
    private final SymbolRemapper symbolRemapper;
    private final SymbolRenamer symbolRenamer;
    private final TypeRemapper typeRemapper;
    private final IrUnimplementedOverridesStrategy unimplementedOverridesStrategy;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FakeOverrideCopier(SymbolRemapper symbolRemapper, TypeRemapper typeRemapper, SymbolRenamer symbolRenamer, boolean z, IrClass parent, IrUnimplementedOverridesStrategy unimplementedOverridesStrategy) {
        super(symbolRemapper, typeRemapper, symbolRenamer);
        Intrinsics.checkNotNullParameter(symbolRemapper, "symbolRemapper");
        Intrinsics.checkNotNullParameter(typeRemapper, "typeRemapper");
        Intrinsics.checkNotNullParameter(symbolRenamer, "symbolRenamer");
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(unimplementedOverridesStrategy, "unimplementedOverridesStrategy");
        this.symbolRemapper = symbolRemapper;
        this.typeRemapper = typeRemapper;
        this.symbolRenamer = symbolRenamer;
        this.makeExternal = z;
        this.parent = parent;
        this.unimplementedOverridesStrategy = unimplementedOverridesStrategy;
    }

    @Override // org.jetbrains.kotlin.ir.util.DeepCopyIrTreeWithSymbols, org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrSimpleFunction visitSimpleFunction(IrSimpleFunction declaration) {
        IrValueParameter irValueParameter;
        IrValueParameter irValueParameter2;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrUnimplementedOverridesStrategy.Customization customizationComputeCustomization = this.unimplementedOverridesStrategy.computeCustomization(declaration, this.parent);
        IrFactory factory = declaration.getFactory();
        int startOffset = declaration.getStartOffset();
        int endOffset = declaration.getEndOffset();
        IrDeclarationOrigin.FAKE_OVERRIDE origin = customizationComputeCustomization.getOrigin();
        if (origin == null) {
            origin = IrDeclarationOrigin.FAKE_OVERRIDE.INSTANCE;
        }
        Name functionName = this.symbolRenamer.getFunctionName(declaration.getSymbol());
        DescriptorVisibility visibility = declaration.getVisibility();
        boolean zIsInline = declaration.getIsInline();
        boolean zIsExpect = declaration.getIsExpect();
        IrType returnType = declaration.getReturnType();
        Modality modality = customizationComputeCustomization.getModality();
        if (modality == null) {
            modality = declaration.getModality();
        }
        IrFunctionWithLateBinding irFunctionWithLateBindingCreateFunctionWithLateBinding$default = IrFactory.DefaultImpls.createFunctionWithLateBinding$default(factory, startOffset, endOffset, origin, functionName, visibility, zIsInline, zIsExpect, returnType, modality, declaration.getIsTailrec(), declaration.getIsSuspend(), declaration.getIsOperator(), declaration.getIsInfix(), this.makeExternal, false, 16384, null);
        transformAnnotations(irFunctionWithLateBindingCreateFunctionWithLateBinding$default, declaration);
        IrFunctionWithLateBinding irFunctionWithLateBinding = irFunctionWithLateBindingCreateFunctionWithLateBinding$default;
        copyTypeParametersFrom(irFunctionWithLateBinding, declaration);
        TypeRemapper typeRemapper = this.typeRemapper;
        typeRemapper.enterScope(irFunctionWithLateBinding);
        IrValueParameter dispatchReceiverParameter = declaration.getDispatchReceiverParameter();
        if (dispatchReceiverParameter != null) {
            IrElement irElementTransform = dispatchReceiverParameter.transform((IrElementTransformer<? super Object>) this, (Object) null);
            if (irElementTransform == null) {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrValueParameter");
            }
            irValueParameter = (IrValueParameter) irElementTransform;
        } else {
            irValueParameter = null;
        }
        irFunctionWithLateBindingCreateFunctionWithLateBinding$default.setDispatchReceiverParameter(irValueParameter);
        IrValueParameter extensionReceiverParameter = declaration.getExtensionReceiverParameter();
        if (extensionReceiverParameter != null) {
            IrElement irElementTransform2 = extensionReceiverParameter.transform((IrElementTransformer<? super Object>) this, (Object) null);
            if (irElementTransform2 == null) {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrValueParameter");
            }
            irValueParameter2 = (IrValueParameter) irElementTransform2;
        } else {
            irValueParameter2 = null;
        }
        irFunctionWithLateBindingCreateFunctionWithLateBinding$default.setExtensionReceiverParameter(irValueParameter2);
        irFunctionWithLateBindingCreateFunctionWithLateBinding$default.setReturnType(this.typeRemapper.remapType(declaration.getReturnType()));
        FakeOverrideCopier fakeOverrideCopier = this;
        List<IrValueParameter> valueParameters = declaration.getValueParameters();
        ArrayList arrayList = new ArrayList(valueParameters.size());
        Iterator<T> it = valueParameters.iterator();
        while (it.hasNext()) {
            IrElement irElementTransform3 = ((IrElement) it.next()).transform(fakeOverrideCopier, null);
            if (irElementTransform3 != null) {
                arrayList.add((IrValueParameter) irElementTransform3);
            } else {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrValueParameter");
            }
        }
        irFunctionWithLateBindingCreateFunctionWithLateBinding$default.setValueParameters(CollectionsKt.compactIfPossible(arrayList));
        Unit unit = Unit.INSTANCE;
        typeRemapper.leaveScope();
        return irFunctionWithLateBindingCreateFunctionWithLateBinding$default;
    }

    @Override // org.jetbrains.kotlin.ir.util.DeepCopyIrTreeWithSymbols, org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrProperty visitProperty(IrProperty declaration) {
        IrSimpleFunction irSimpleFunction;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrUnimplementedOverridesStrategy.Customization customizationComputeCustomization = this.unimplementedOverridesStrategy.computeCustomization(declaration, this.parent);
        IrFactory factory = declaration.getFactory();
        int startOffset = declaration.getStartOffset();
        int endOffset = declaration.getEndOffset();
        IrDeclarationOrigin.FAKE_OVERRIDE origin = customizationComputeCustomization.getOrigin();
        if (origin == null) {
            origin = IrDeclarationOrigin.FAKE_OVERRIDE.INSTANCE;
        }
        Name name2 = declaration.getName();
        DescriptorVisibility visibility = declaration.getVisibility();
        Modality modality = customizationComputeCustomization.getModality();
        if (modality == null) {
            modality = declaration.getModality();
        }
        IrPropertyWithLateBinding irPropertyWithLateBindingCreatePropertyWithLateBinding$default = IrFactory.DefaultImpls.createPropertyWithLateBinding$default(factory, startOffset, endOffset, origin, name2, visibility, modality, declaration.getIsVar(), declaration.getIsConst(), declaration.getIsLateinit(), declaration.getIsDelegated(), this.makeExternal, declaration.getIsExpect(), false, 4096, null);
        transformAnnotations(irPropertyWithLateBindingCreatePropertyWithLateBinding$default, declaration);
        IrSimpleFunction getter = declaration.getGetter();
        IrSimpleFunction irSimpleFunction2 = null;
        if (getter != null) {
            IrElement irElementTransform = getter.transform(this, null);
            if (irElementTransform == null) {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrSimpleFunction");
            }
            irSimpleFunction = (IrSimpleFunction) irElementTransform;
        } else {
            irSimpleFunction = null;
        }
        irPropertyWithLateBindingCreatePropertyWithLateBinding$default.setGetter(irSimpleFunction);
        IrSimpleFunction setter = declaration.getSetter();
        if (setter != null) {
            IrElement irElementTransform2 = setter.transform(this, null);
            if (irElementTransform2 == null) {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrSimpleFunction");
            }
            irSimpleFunction2 = (IrSimpleFunction) irElementTransform2;
        }
        irPropertyWithLateBindingCreatePropertyWithLateBinding$default.setSetter(irSimpleFunction2);
        return irPropertyWithLateBindingCreatePropertyWithLateBinding$default;
    }

    @Override // org.jetbrains.kotlin.ir.util.DeepCopyIrTreeWithSymbols, org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrValueParameter visitValueParameter(IrValueParameter declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrFactory factory = declaration.getFactory();
        int startOffset = declaration.getStartOffset();
        int endOffset = declaration.getEndOffset();
        IrDeclarationOrigin irDeclarationOriginMapDeclarationOrigin = mapDeclarationOrigin(declaration.getOrigin());
        Name valueParameterName = this.symbolRenamer.getValueParameterName(declaration.getSymbol());
        IrType irTypeRemapType = remapType(declaration.getType());
        boolean zIsAssignable = declaration.getIsAssignable();
        IrValueParameterSymbol declaredValueParameter = this.symbolRemapper.getDeclaredValueParameter(declaration.getSymbol());
        int index = declaration.getIndex();
        IrType varargElementType = declaration.getVarargElementType();
        IrValueParameter irValueParameterCreateValueParameter = factory.createValueParameter(startOffset, endOffset, irDeclarationOriginMapDeclarationOrigin, valueParameterName, irTypeRemapType, zIsAssignable, declaredValueParameter, index, varargElementType != null ? remapType(varargElementType) : null, declaration.getIsCrossinline(), declaration.getIsNoinline(), declaration.getIsHidden());
        transformAnnotations(irValueParameterCreateValueParameter, declaration);
        return irValueParameterCreateValueParameter;
    }
}
