package org.jetbrains.kotlin.backend.jvm.lower;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.backend.common.phaser.DumperVerifierKt;
import org.jetbrains.kotlin.backend.common.phaser.NamedCompilerPhase;
import org.jetbrains.kotlin.backend.common.phaser.PhaseBuildersKt;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.JvmLowerKt;
import org.jetbrains.kotlin.backend.jvm.JvmLoweredDeclarationOrigin;
import org.jetbrains.kotlin.backend.jvm.JvmLoweredStatementOrigin;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrCoroutineUtilsKt;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrUtilsKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrExpressionsKt;
import org.jetbrains.kotlin.ir.expressions.IrMemberAccessExpression;
import org.jetbrains.kotlin.ir.expressions.impl.IrCompositeImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: AddContinuationLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000F\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0014\u0010\u0007\u001a\u00020\b*\u00020\t2\u0006\u0010\n\u001a\u00020\u0002H\u0002\u001a\u0014\u0010\u000b\u001a\u00020\f*\u00020\f2\u0006\u0010\n\u001a\u00020\u0002H\u0002\u001aR\u0010\r\u001a\u0002H\u000e\"\u000e\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u00020\u00100\u000f*\u0002H\u000e2\u0006\u0010\n\u001a\u00020\u00022\b\u0010\u0011\u001a\u0004\u0018\u00010\t2\u001d\u0010\u0012\u001a\u0019\u0012\u0004\u0012\u0002H\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u0002H\u000e0\u0013¢\u0006\u0002\b\u0015H\u0002¢\u0006\u0002\u0010\u0016\u001a\u0014\u0010\u0017\u001a\u00020\f*\u00020\f2\u0006\u0010\n\u001a\u00020\u0002H\u0002\"0\u0010\u0000\u001a\u001e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001j\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003`\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0018"}, d2 = {"addContinuationPhase", "Lorg/jetbrains/kotlin/backend/common/phaser/NamedCompilerPhase;", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "Lorg/jetbrains/kotlin/backend/common/phaser/SameTypeNamedCompilerPhase;", "getAddContinuationPhase", "()Lorg/jetbrains/kotlin/backend/common/phaser/NamedCompilerPhase;", "continuationType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "context", "createSuspendFunctionStub", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "retargetToSuspendView", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/ir/expressions/IrMemberAccessExpression;", "Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "caller", "copyWithTargetSymbol", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "Lkotlin/ExtensionFunctionType;", "(Lorg/jetbrains/kotlin/ir/expressions/IrMemberAccessExpression;Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;Lorg/jetbrains/kotlin/ir/declarations/IrFunction;Lkotlin/jvm/functions/Function2;)Lorg/jetbrains/kotlin/ir/expressions/IrMemberAccessExpression;", "suspendFunctionViewOrStub", "backend.jvm.lower"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class AddContinuationLoweringKt {
    private static final NamedCompilerPhase<JvmBackendContext, IrFile> addContinuationPhase = PhaseBuildersKt.makeIrFilePhase(AddContinuationLoweringKt$addContinuationPhase$1.INSTANCE, "AddContinuation", "Add continuation classes and parameters to suspend functions", (248 & 8) != 0 ? SetsKt.emptySet() : SetsKt.setOf((Object[]) new NamedCompilerPhase[]{SuspendLambdaLoweringKt.getSuspendLambdaPhase(), JvmLowerKt.getLocalDeclarationsPhase(), TailCallOptimizationLoweringKt.getTailCallOptimizationPhase()}), (248 & 16) != 0 ? SetsKt.emptySet() : null, (248 & 32) != 0 ? SetsKt.emptySet() : null, (248 & 64) != 0 ? SetsKt.emptySet() : null, (248 & 128) != 0 ? SetsKt.setOf((Object[]) new Function3[]{DumperVerifierKt.getDefaultDumper(), DumperVerifierKt.getValidationAction()}) : null);

    public static final NamedCompilerPhase<JvmBackendContext, IrFile> getAddContinuationPhase() {
        return addContinuationPhase;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final IrSimpleFunction suspendFunctionViewOrStub(IrSimpleFunction irSimpleFunction, JvmBackendContext jvmBackendContext) {
        if (!irSimpleFunction.getIsSuspend()) {
            return irSimpleFunction;
        }
        if (Intrinsics.areEqual(irSimpleFunction.getOrigin(), JvmLoweredDeclarationOrigin.SUPER_INTERFACE_METHOD_BRIDGE.INSTANCE)) {
            IrValueParameter irValueParameter = (IrValueParameter) CollectionsKt.lastOrNull((List) irSimpleFunction.getValueParameters());
            if (Intrinsics.areEqual(irValueParameter != null ? irValueParameter.getOrigin() : null, JvmLoweredDeclarationOrigin.CONTINUATION_CLASS.INSTANCE)) {
                return irSimpleFunction;
            }
        }
        ConcurrentHashMap<IrSimpleFunction, IrSimpleFunction> suspendFunctionOriginalToView = jvmBackendContext.getSuspendFunctionOriginalToView();
        IrSimpleFunction irSimpleFunctionSuspendFunctionOriginal = JvmIrUtilsKt.suspendFunctionOriginal(irSimpleFunction);
        IrSimpleFunction irSimpleFunction2 = suspendFunctionOriginalToView.get(irSimpleFunctionSuspendFunctionOriginal);
        if (irSimpleFunction2 == null) {
            IrSimpleFunction irSimpleFunctionCreateSuspendFunctionStub = createSuspendFunctionStub(irSimpleFunction, jvmBackendContext);
            IrSimpleFunction irSimpleFunctionPutIfAbsent = suspendFunctionOriginalToView.putIfAbsent(irSimpleFunctionSuspendFunctionOriginal, irSimpleFunctionCreateSuspendFunctionStub);
            irSimpleFunction2 = irSimpleFunctionPutIfAbsent == null ? irSimpleFunctionCreateSuspendFunctionStub : irSimpleFunctionPutIfAbsent;
        }
        Intrinsics.checkNotNullExpressionValue(irSimpleFunction2, "getOrPut(...)");
        return irSimpleFunction2;
    }

    private static final IrSimpleFunction createSuspendFunctionStub(IrSimpleFunction irSimpleFunction, JvmBackendContext jvmBackendContext) {
        Object next;
        if (!irSimpleFunction.getIsSuspend()) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        IrFactory factory = irSimpleFunction.getFactory();
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        IrSimpleFunction irSimpleFunction2 = irSimpleFunction;
        irFunctionBuilder.updateFrom((IrFunction) irSimpleFunction2);
        irFunctionBuilder.setName(irSimpleFunction.getName());
        irFunctionBuilder.setOrigin(irSimpleFunction.getOrigin());
        irFunctionBuilder.setReturnType(jvmBackendContext.getIrBuiltIns().getAnyNType());
        IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(factory, irFunctionBuilder);
        irSimpleFunctionBuildFunction.setParent(irSimpleFunction.getParent());
        irSimpleFunctionBuildFunction.setAnnotations(CollectionsKt.plus((Collection) irSimpleFunctionBuildFunction.getAnnotations(), (Iterable) irSimpleFunction.getAnnotations()));
        irSimpleFunctionBuildFunction.setMetadata(irSimpleFunction.getMetadata());
        irSimpleFunctionBuildFunction.setContextReceiverParametersCount(irSimpleFunction.getContextReceiverParametersCount());
        IrDeclarationsKt.copyAttributes(irSimpleFunctionBuildFunction, irSimpleFunction);
        IrSimpleFunction irSimpleFunction3 = irSimpleFunctionBuildFunction;
        IrSimpleFunction irSimpleFunction4 = irSimpleFunction;
        IrUtilsKt.copyTypeParametersFrom$default(irSimpleFunction3, irSimpleFunction4, null, null, 6, null);
        Map<IrTypeParameterSymbol, IrType> mapMakeTypeParameterSubstitutionMap = IrUtilsKt.makeTypeParameterSubstitutionMap(irSimpleFunction4, irSimpleFunction3);
        IrSimpleFunction irSimpleFunction5 = irSimpleFunctionBuildFunction;
        IrUtilsKt.copyReceiverParametersFrom(irSimpleFunction5, irSimpleFunction2, mapMakeTypeParameterSubstitutionMap);
        List<IrSimpleFunctionSymbol> overriddenSymbols = irSimpleFunctionBuildFunction.getOverriddenSymbols();
        List<IrSimpleFunctionSymbol> overriddenSymbols2 = irSimpleFunction.getOverriddenSymbols();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(overriddenSymbols2, 10));
        Iterator<T> it = overriddenSymbols2.iterator();
        while (it.hasNext()) {
            arrayList.add(suspendFunctionViewOrStub(((IrSimpleFunctionSymbol) it.next()).getOwner(), jvmBackendContext).getSymbol());
        }
        irSimpleFunctionBuildFunction.setOverriddenSymbols(CollectionsKt.plus((Collection) overriddenSymbols, (Iterable) arrayList));
        Iterator<T> it2 = irSimpleFunction.getValueParameters().iterator();
        do {
            if (!it2.hasNext()) {
                next = null;
                break;
            }
            next = it2.next();
        } while (!Intrinsics.areEqual(((IrValueParameter) next).getOrigin(), IrDeclarationOrigin.MASK_FOR_DEFAULT_FUNCTION.INSTANCE));
        IrValueParameter irValueParameter = (IrValueParameter) next;
        int index = irValueParameter != null ? irValueParameter.getIndex() : irSimpleFunction.getValueParameters().size();
        List<IrValueParameter> valueParameters = irSimpleFunctionBuildFunction.getValueParameters();
        List<IrValueParameter> listTake = CollectionsKt.take(irSimpleFunction.getValueParameters(), index);
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(listTake, 10));
        for (IrValueParameter irValueParameter2 : listTake) {
            ArrayList arrayList3 = arrayList2;
            arrayList3.add(IrUtilsKt.copyTo$default(irValueParameter2, irSimpleFunction5, null, irValueParameter2.getIndex(), 0, 0, null, null, IrTypeUtilsKt.substitute(irValueParameter2.getType(), mapMakeTypeParameterSubstitutionMap), null, null, false, false, false, 8058, null));
            arrayList2 = arrayList3;
            valueParameters = valueParameters;
            index = index;
            irSimpleFunction5 = irSimpleFunction5;
        }
        int i = index;
        irSimpleFunctionBuildFunction.setValueParameters(CollectionsKt.plus((Collection) valueParameters, (Iterable) arrayList2));
        IrValueParameter irValueParameterAddValueParameter = DeclarationBuildersKt.addValueParameter(irSimpleFunction5, CoroutineConstantsKt.SUSPEND_FUNCTION_COMPLETION_PARAMETER_NAME, IrTypeUtilsKt.substitute(continuationType(irSimpleFunction2, jvmBackendContext), mapMakeTypeParameterSubstitutionMap), JvmLoweredDeclarationOrigin.CONTINUATION_CLASS.INSTANCE);
        List<IrValueParameter> valueParameters2 = irSimpleFunctionBuildFunction.getValueParameters();
        List<IrValueParameter> listDrop = CollectionsKt.drop(irSimpleFunction.getValueParameters(), i);
        ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(listDrop, 10));
        for (IrValueParameter irValueParameter3 : listDrop) {
            ArrayList arrayList5 = arrayList4;
            arrayList5.add(IrUtilsKt.copyTo$default(irValueParameter3, irSimpleFunction5, null, irValueParameter3.getIndex() + 1, 0, 0, null, null, IrTypeUtilsKt.substitute(irValueParameter3.getType(), mapMakeTypeParameterSubstitutionMap), null, null, false, false, false, 8058, null));
            arrayList4 = arrayList5;
            irSimpleFunction5 = irSimpleFunction5;
            valueParameters2 = valueParameters2;
            irValueParameterAddValueParameter = irValueParameterAddValueParameter;
        }
        IrValueParameter irValueParameter4 = irValueParameterAddValueParameter;
        IrSimpleFunction irSimpleFunction6 = irSimpleFunction5;
        irSimpleFunctionBuildFunction.setValueParameters(CollectionsKt.plus((Collection) valueParameters2, (Iterable) arrayList4));
        List<IrValueParameter> explicitParameters = IrUtilsKt.getExplicitParameters(irSimpleFunction2);
        List<IrValueParameter> explicitParameters2 = IrUtilsKt.getExplicitParameters(irSimpleFunction6);
        ArrayList arrayList6 = new ArrayList();
        for (Object obj : explicitParameters2) {
            IrValueParameter irValueParameter5 = irValueParameter4;
            if (!Intrinsics.areEqual((IrValueParameter) obj, irValueParameter5)) {
                arrayList6.add(obj);
            }
            irValueParameter4 = irValueParameter5;
        }
        jvmBackendContext.remapMultiFieldValueClassStructure(irSimpleFunction2, irSimpleFunction6, MapsKt.toMap(CollectionsKt.zip(explicitParameters, arrayList6)));
        return irSimpleFunctionBuildFunction;
    }

    private static final IrType continuationType(IrFunction irFunction, JvmBackendContext jvmBackendContext) {
        boolean z;
        FqName fqNameWhenAvailable;
        String strAsString;
        IrSimpleType defaultType;
        IrDeclarationParent parent = irFunction.getParent();
        IrClass irClass = parent instanceof IrClass ? (IrClass) parent : null;
        boolean z2 = (irClass == null || (defaultType = IrUtilsKt.getDefaultType(irClass)) == null || !IrTypeUtilsKt.isSuspendFunction(defaultType)) ? false : true;
        IrDeclarationParent parent2 = irFunction.getParent();
        IrClass irClass2 = parent2 instanceof IrClass ? (IrClass) parent2 : null;
        if (irClass2 != null && (fqNameWhenAvailable = IrUtilsKt.getFqNameWhenAvailable(irClass2)) != null && (strAsString = fqNameWhenAvailable.asString()) != null && StringsKt.startsWith$default(strAsString, "kotlin.jvm.functions.Function", false, 2, (Object) null)) {
            String strRemovePrefix = StringsKt.removePrefix(strAsString, (CharSequence) "kotlin.jvm.functions.Function");
            int i = 0;
            while (true) {
                if (i >= strRemovePrefix.length()) {
                    z = true;
                    break;
                }
                if (!Character.isDigit(strRemovePrefix.charAt(i))) {
                    z = false;
                    break;
                }
                i++;
            }
        } else {
            z = false;
            break;
        }
        return (z2 || z) ? jvmBackendContext.getIrBuiltIns().getAnyNType() : IrTypesKt.typeWith(jvmBackendContext.getIr().getSymbols().getContinuationClass(), irFunction.getReturnType());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final <T extends IrMemberAccessExpression<IrFunctionSymbol>> T retargetToSuspendView(T t, JvmBackendContext jvmBackendContext, IrFunction irFunction, Function2<? super T, ? super IrSimpleFunctionSymbol, ? extends T> function2) {
        IrValueParameterSymbol symbol;
        IrGetValueImpl irGetValueImpl;
        IrFunction owner = ((IrFunctionSymbol) t.getSymbol()).getOwner();
        if (owner instanceof IrSimpleFunction) {
            IrSimpleFunction irSimpleFunction = (IrSimpleFunction) owner;
            if (irSimpleFunction.getIsSuspend() && ((irFunction == null || !JvmIrCoroutineUtilsKt.isInvokeSuspendOfContinuation(irFunction)) && !Intrinsics.areEqual(owner.getOrigin(), JvmLoweredDeclarationOrigin.SUSPEND_IMPL_STATIC_FUNCTION.INSTANCE) && JvmIrCoroutineUtilsKt.continuationParameter(owner) == null)) {
                IrSimpleFunction irSimpleFunctionSuspendFunctionViewOrStub = suspendFunctionViewOrStub(irSimpleFunction, jvmBackendContext);
                if (Intrinsics.areEqual(irSimpleFunctionSuspendFunctionViewOrStub, owner)) {
                    return t;
                }
                T tInvoke = function2.invoke(t, irSimpleFunctionSuspendFunctionViewOrStub.getSymbol());
                IrDeclarationsKt.copyAttributes(tInvoke, t);
                IrExpressionsKt.copyTypeArgumentsFrom$default(tInvoke, t, 0, 2, null);
                tInvoke.setDispatchReceiver(t.getDispatchReceiver());
                tInvoke.setExtensionReceiver(t.getExtensionReceiver());
                IrValueParameter irValueParameterContinuationParameter = JvmIrCoroutineUtilsKt.continuationParameter(irSimpleFunctionSuspendFunctionViewOrStub);
                Intrinsics.checkNotNull(irValueParameterContinuationParameter);
                int valueArgumentsCount = t.getValueArgumentsCount();
                int i = 0;
                while (i < valueArgumentsCount) {
                    tInvoke.putValueArgument((i >= irValueParameterContinuationParameter.getIndex() ? 1 : 0) + i, t.getValueArgument(i));
                    i++;
                }
                if (irFunction != null) {
                    if (Intrinsics.areEqual(irFunction.getOrigin(), JvmLoweredDeclarationOrigin.INLINE_LAMBDA.INSTANCE)) {
                        irGetValueImpl = new IrCompositeImpl(-1, -1, irValueParameterContinuationParameter.getType(), JvmLoweredStatementOrigin.FAKE_CONTINUATION.INSTANCE);
                    } else {
                        IrValueParameter irValueParameterContinuationParameter2 = JvmIrCoroutineUtilsKt.continuationParameter(irFunction);
                        if (irValueParameterContinuationParameter2 != null && (symbol = irValueParameterContinuationParameter2.getSymbol()) != null) {
                            irGetValueImpl = new IrGetValueImpl(-1, -1, symbol, null, 8, null);
                        } else {
                            throw new AssertionError(RenderIrElementKt.render$default(irFunction, (DumpIrTreeOptions) null, 1, (Object) null) + " has no continuation; can't call " + RenderIrElementKt.render$default(owner, (DumpIrTreeOptions) null, 1, (Object) null));
                        }
                    }
                    tInvoke.putValueArgument(irValueParameterContinuationParameter.getIndex(), irGetValueImpl);
                }
                return tInvoke;
            }
        }
        return t;
    }
}
