package org.jetbrains.kotlin.ir.util;

import java.util.ArrayList;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ParameterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.ScriptDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.ir.ObsoleteDescriptorBasedAPI;
import org.jetbrains.kotlin.ir.declarations.DescriptorMetadataSource;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.impl.IrUninitializedType;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.utils.CollectionsKt;

/* JADX INFO: compiled from: overrides.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000.\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0007\u001a,\u0010\u0006\u001a\u00020\u0003*\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0007¨\u0006\u000f"}, d2 = {"generateOverriddenFunctionSymbols", "", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/ReferenceSymbolTable;", "declareSimpleFunctionWithOverrides", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "startOffset", "", "endOffset", "origin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "ir.tree"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class OverridesKt {
    @ObsoleteDescriptorBasedAPI
    public static final IrSimpleFunction declareSimpleFunctionWithOverrides(final SymbolTable symbolTable, final int i, final int i2, final IrDeclarationOrigin origin, final FunctionDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(symbolTable, "<this>");
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        IrSimpleFunction irSimpleFunctionDeclareSimpleFunction = symbolTable.getDescriptorExtension().declareSimpleFunction(descriptor, new Function1<IrSimpleFunctionSymbol, IrSimpleFunction>() { // from class: org.jetbrains.kotlin.ir.util.OverridesKt.declareSimpleFunctionWithOverrides.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrSimpleFunction invoke(IrSimpleFunctionSymbol it) {
                Intrinsics.checkNotNullParameter(it, "it");
                FunctionDescriptor functionDescriptor = descriptor;
                SymbolTable symbolTable2 = symbolTable;
                int i3 = i;
                int i4 = i2;
                IrDeclarationOrigin irDeclarationOrigin = origin;
                IrFactory irFactory = symbolTable2.getIrFactory();
                Name nameNameForDeclaration = symbolTable2.getNameProvider().nameForDeclaration(functionDescriptor);
                DescriptorVisibility visibility = functionDescriptor.getVisibilityImpl();
                Intrinsics.checkNotNullExpressionValue(visibility, "getVisibility(...)");
                boolean zIsInline = functionDescriptor.isInline();
                boolean zIsExpect = functionDescriptor.get$isExpect();
                IrUninitializedType irUninitializedType = IrUninitializedType.INSTANCE;
                Modality modality = functionDescriptor.getModality();
                Intrinsics.checkNotNullExpressionValue(modality, "getModality(...)");
                IrSimpleFunction irSimpleFunctionCreateSimpleFunction$default = IrFactory.DefaultImpls.createSimpleFunction$default(irFactory, i3, i4, irDeclarationOrigin, nameNameForDeclaration, visibility, zIsInline, zIsExpect, irUninitializedType, modality, it, functionDescriptor.isTailrec(), functionDescriptor.isSuspend(), functionDescriptor.isOperator(), functionDescriptor.isInfix(), DescriptorUtilsKt.isEffectivelyExternal(functionDescriptor), null, functionDescriptor.getKind() == CallableMemberDescriptor.Kind.FAKE_OVERRIDE, 32768, null);
                irSimpleFunctionCreateSimpleFunction$default.setMetadata(new DescriptorMetadataSource.Function(functionDescriptor));
                return irSimpleFunctionCreateSimpleFunction$default;
            }
        });
        generateOverriddenFunctionSymbols(irSimpleFunctionDeclareSimpleFunction, symbolTable);
        return irSimpleFunctionDeclareSimpleFunction;
    }

    @ObsoleteDescriptorBasedAPI
    public static final void generateOverriddenFunctionSymbols(IrSimpleFunction declaration, ReferenceSymbolTable symbolTable) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(symbolTable, "symbolTable");
        Collection<? extends FunctionDescriptor> overriddenDescriptors = declaration.getDescriptor().getOverriddenDescriptors();
        Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
        ArrayList arrayList = new ArrayList(overriddenDescriptors.size());
        for (FunctionDescriptor functionDescriptor : overriddenDescriptors) {
            ReferenceSymbolTableExtension<ClassDescriptor, TypeAliasDescriptor, ScriptDescriptor, FunctionDescriptor, ClassConstructorDescriptor, PropertyDescriptor, ParameterDescriptor, TypeParameterDescriptor> descriptorExtension = symbolTable.getDescriptorExtension();
            FunctionDescriptor original = functionDescriptor.getOriginal();
            Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
            arrayList.add(descriptorExtension.referenceSimpleFunction(original));
        }
        declaration.setOverriddenSymbols(CollectionsKt.compactIfPossible(arrayList));
    }
}
