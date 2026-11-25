package org.jetbrains.kotlin.ir.backend.js.lower;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.MappingsKt;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.backend.js.ir.JsIrBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrTypeAbbreviation;
import org.jetbrains.kotlin.ir.types.impl.IrSimpleTypeImpl;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.utils.MemoryOptimizedCollectionUtilKt;

/* JADX INFO: compiled from: SecondaryCtorLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000$\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u001c\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002\u001a\u001c\u0010\u0007\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002\u001a\u001c\u0010\b\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\u0006H\u0002\u001a\u001c\u0010\n\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\u0006H\u0002\u001a\f\u0010\u000b\u001a\u00020\f*\u00020\rH\u0002¨\u0006\u000e"}, d2 = {"buildConstructorDelegate", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "klass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "buildConstructorFactory", "buildFactoryDeclaration", "irClass", "buildInitDeclaration", "toIrType", "Lorg/jetbrains/kotlin/ir/types/impl/IrSimpleTypeImpl;", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "backend.js"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class SecondaryCtorLoweringKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final IrSimpleTypeImpl toIrType(IrTypeParameter irTypeParameter) {
        return new IrSimpleTypeImpl((IrClassifierSymbol) irTypeParameter.getSymbol(), true, CollectionsKt.emptyList(), CollectionsKt.emptyList(), (IrTypeAbbreviation) null, 16, (DefaultConstructorMarker) null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final IrSimpleFunction buildInitDeclaration(JsIrBackendContext jsIrBackendContext, IrConstructor irConstructor, IrClass irClass) {
        IrSimpleType defaultType = IrUtilsKt.getDefaultType(irClass);
        String str = (irClass.getName() + "_init") + "_$Init$";
        IrFactory irFactory = jsIrBackendContext.getIrFactory();
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        irFunctionBuilder.setStartOffset(irConstructor.getStartOffset());
        irFunctionBuilder.setEndOffset(irConstructor.getEndOffset());
        Name nameIdentifier = Name.identifier(str);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        irFunctionBuilder.setName(nameIdentifier);
        IrSimpleType irSimpleType = defaultType;
        irFunctionBuilder.setReturnType(irSimpleType);
        DescriptorVisibility INTERNAL = DescriptorVisibilities.INTERNAL;
        Intrinsics.checkNotNullExpressionValue(INTERNAL, "INTERNAL");
        irFunctionBuilder.setVisibility(INTERNAL);
        irFunctionBuilder.setModality(Modality.FINAL);
        irFunctionBuilder.setInline(irConstructor.getIsInline());
        irFunctionBuilder.setExternal(irConstructor.getIsExternal());
        irFunctionBuilder.setOrigin(JsIrBuilder.SYNTHESIZED_DECLARATION.INSTANCE);
        IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(irFactory, irFunctionBuilder);
        irSimpleFunctionBuildFunction.setParent(irConstructor.getParent());
        IrUtilsKt.copyTypeParametersFrom$default(irSimpleFunctionBuildFunction, IrUtilsKt.getParentAsClass(irConstructor), null, null, 6, null);
        List<IrValueParameter> valueParameters = irConstructor.getValueParameters();
        ArrayList arrayList = new ArrayList(valueParameters.size());
        Iterator<T> it = valueParameters.iterator();
        while (it.hasNext()) {
            arrayList.add(IrUtilsKt.copyTo$default((IrValueParameter) it.next(), irSimpleFunctionBuildFunction, null, 0, 0, 0, null, null, null, null, null, false, false, false, 8190, null));
        }
        irSimpleFunctionBuildFunction.setValueParameters(org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList));
        irSimpleFunctionBuildFunction.setValueParameters(MemoryOptimizedCollectionUtilKt.memoryOptimizedPlus(irSimpleFunctionBuildFunction.getValueParameters(), JsIrBuilder.buildValueParameter$default(JsIrBuilder.INSTANCE, irSimpleFunctionBuildFunction, "$this", irConstructor.getValueParameters().size(), irSimpleType, false, null, 48, null)));
        return irSimpleFunctionBuildFunction;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final IrSimpleFunction buildFactoryDeclaration(JsIrBackendContext jsIrBackendContext, IrConstructor irConstructor, IrClass irClass) {
        IrSimpleType defaultType = IrUtilsKt.getDefaultType(irClass);
        String str = (irClass.getName() + "_init") + "_$Create$";
        IrFactory irFactory = jsIrBackendContext.getIrFactory();
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        irFunctionBuilder.setStartOffset(irConstructor.getStartOffset());
        irFunctionBuilder.setEndOffset(irConstructor.getEndOffset());
        Name nameIdentifier = Name.identifier(str);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        irFunctionBuilder.setName(nameIdentifier);
        irFunctionBuilder.setReturnType(defaultType);
        irFunctionBuilder.setVisibility(irConstructor.getVisibility());
        irFunctionBuilder.setModality(Modality.FINAL);
        irFunctionBuilder.setInline(irConstructor.getIsInline());
        irFunctionBuilder.setExternal(irConstructor.getIsExternal());
        IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(irFactory, irFunctionBuilder);
        irSimpleFunctionBuildFunction.setParent(irConstructor.getParent());
        IrUtilsKt.copyTypeParametersFrom$default(irSimpleFunctionBuildFunction, IrUtilsKt.getParentAsClass(irConstructor), null, null, 6, null);
        List<IrValueParameter> valueParameters = irSimpleFunctionBuildFunction.getValueParameters();
        List<IrValueParameter> valueParameters2 = irConstructor.getValueParameters();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameters2, 10));
        Iterator<T> it = valueParameters2.iterator();
        while (it.hasNext()) {
            arrayList.add(IrUtilsKt.copyTo$default((IrValueParameter) it.next(), irSimpleFunctionBuildFunction, null, 0, 0, 0, null, null, null, null, null, false, false, false, 8190, null));
        }
        irSimpleFunctionBuildFunction.setValueParameters(MemoryOptimizedCollectionUtilKt.memoryOptimizedPlus((List) valueParameters, (List) arrayList));
        irSimpleFunctionBuildFunction.setAnnotations(irConstructor.getAnnotations());
        return irSimpleFunctionBuildFunction;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final IrSimpleFunction buildConstructorDelegate(final JsIrBackendContext jsIrBackendContext, final IrConstructor irConstructor, final IrClass irClass) {
        return (IrSimpleFunction) MappingsKt.getOrPut(jsIrBackendContext.getMapping().getSecondaryConstructorToDelegate(), irConstructor, new Function0<IrSimpleFunction>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.SecondaryCtorLoweringKt.buildConstructorDelegate.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrSimpleFunction invoke() {
                return SecondaryCtorLoweringKt.buildInitDeclaration(jsIrBackendContext, irConstructor, irClass);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final IrSimpleFunction buildConstructorFactory(final JsIrBackendContext jsIrBackendContext, final IrConstructor irConstructor, final IrClass irClass) {
        return (IrSimpleFunction) MappingsKt.getOrPut(jsIrBackendContext.getMapping().getSecondaryConstructorToFactory(), irConstructor, new Function0<IrSimpleFunction>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.SecondaryCtorLoweringKt.buildConstructorFactory.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrSimpleFunction invoke() {
                return SecondaryCtorLoweringKt.buildFactoryDeclaration(jsIrBackendContext, irConstructor, irClass);
            }
        });
    }
}
