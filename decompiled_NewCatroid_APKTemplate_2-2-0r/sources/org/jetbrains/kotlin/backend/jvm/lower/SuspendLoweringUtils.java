package org.jetbrains.kotlin.backend.jvm.lower;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBodyBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.impl.IrErrorExpressionImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrExpressionBodyImpl;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeArgument;
import org.jetbrains.kotlin.ir.types.IrTypeProjection;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;

/* JADX INFO: compiled from: SuspendLambdaLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b \u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\f\u0010\u0007\u001a\u00020\b*\u00020\tH\u0004J(\u0010\n\u001a\u00020\u000b*\u00020\f2\u0006\u0010\r\u001a\u00020\u000b2\b\b\u0002\u0010\u000e\u001a\u00020\u000f2\b\b\u0002\u0010\u0010\u001a\u00020\u000fH\u0004JG\u0010\n\u001a\u00020\u000b*\u00020\f2\u0006\u0010\r\u001a\u00020\u000b2\b\b\u0002\u0010\u000e\u001a\u00020\u000f2\b\b\u0002\u0010\u0010\u001a\u00020\u000f2\u001d\u0010\u0011\u001a\u0019\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00140\u0012¢\u0006\u0002\b\u0015H\u0004J\f\u0010\u0016\u001a\u00020\u0017*\u00020\tH\u0004J\f\u0010\u0018\u001a\u00020\u0014*\u00020\u000bH\u0004R\u0014\u0010\u0002\u001a\u00020\u0003X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/SuspendLoweringUtils;", "", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "addCompletionValueParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "addFunctionOverride", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "function", "startOffset", "", "endOffset", "makeBody", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/ir/builders/IrBlockBodyBuilder;", "", "Lkotlin/ExtensionFunctionType;", "continuationType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "generateErrorForInlineBody", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class SuspendLoweringUtils {
    private final JvmBackendContext context;

    public SuspendLoweringUtils(JvmBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    protected final JvmBackendContext getContext() {
        return this.context;
    }

    public static /* synthetic */ IrSimpleFunction addFunctionOverride$default(SuspendLoweringUtils suspendLoweringUtils, IrClass irClass, IrSimpleFunction irSimpleFunction, int i, int i2, int i3, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: addFunctionOverride");
        }
        if ((i3 & 2) != 0) {
            i = -1;
        }
        if ((i3 & 4) != 0) {
            i2 = -1;
        }
        return suspendLoweringUtils.addFunctionOverride(irClass, irSimpleFunction, i, i2);
    }

    protected final IrSimpleFunction addFunctionOverride(IrClass irClass, IrSimpleFunction function, int i, int i2) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        Intrinsics.checkNotNullParameter(function, "function");
        Object obj = null;
        boolean z = false;
        for (Object obj2 : irClass.getSuperTypes()) {
            if (Intrinsics.areEqual(IrTypesKt.getClassifierOrFail((IrType) obj2), IrUtilsKt.getParentAsClass(function).getSymbol())) {
                if (z) {
                    throw new IllegalArgumentException("Collection contains more than one matching element.");
                }
                z = true;
                obj = obj2;
            }
        }
        if (z) {
            IrType irType = (IrType) obj;
            IrSymbolOwner owner = IrTypesKt.getClassifierOrFail(irType).getOwner();
            Intrinsics.checkNotNull(owner, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrClass");
            List<IrTypeParameter> typeParameters = ((IrClass) owner).getTypeParameters();
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(typeParameters, 10));
            Iterator<T> it = typeParameters.iterator();
            while (it.hasNext()) {
                arrayList.add(((IrTypeParameter) it.next()).getSymbol());
            }
            ArrayList arrayList2 = arrayList;
            Intrinsics.checkNotNull(irType, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
            List<IrTypeArgument> arguments = ((IrSimpleType) irType).getArguments();
            ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arguments, 10));
            for (IrTypeArgument irTypeArgument : arguments) {
                Intrinsics.checkNotNull(irTypeArgument, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrTypeProjection");
                arrayList3.add(((IrTypeProjection) irTypeArgument).getType());
            }
            Map map = MapsKt.toMap(CollectionsKt.zip(arrayList2, arrayList3));
            String strAsString = function.getName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            IrSimpleFunction irSimpleFunctionAddFunction$default = DeclarationBuildersKt.addFunction$default(irClass, strAsString, IrTypeUtilsKt.substitute(function.getReturnType(), map), null, null, false, false, false, false, null, i, i2, 508, null);
            irSimpleFunctionAddFunction$default.setOverriddenSymbols(CollectionsKt.listOf(function.getSymbol()));
            List<IrValueParameter> valueParameters = function.getValueParameters();
            ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameters, 10));
            for (IrValueParameter irValueParameter : valueParameters) {
                arrayList4.add(IrUtilsKt.copyTo$default(irValueParameter, irSimpleFunctionAddFunction$default, null, 0, 0, 0, null, null, IrTypeUtilsKt.substitute(irValueParameter.getType(), map), null, null, false, false, false, 8062, null));
            }
            irSimpleFunctionAddFunction$default.setValueParameters(arrayList4);
            return irSimpleFunctionAddFunction$default;
        }
        throw new NoSuchElementException("Collection contains no element matching the predicate.");
    }

    public static /* synthetic */ IrSimpleFunction addFunctionOverride$default(SuspendLoweringUtils suspendLoweringUtils, IrClass irClass, IrSimpleFunction irSimpleFunction, int i, int i2, Function2 function2, int i3, Object obj) {
        if (obj == null) {
            return suspendLoweringUtils.addFunctionOverride(irClass, irSimpleFunction, (i3 & 2) != 0 ? -1 : i, (i3 & 4) != 0 ? -1 : i2, function2);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: addFunctionOverride");
    }

    protected final IrSimpleFunction addFunctionOverride(IrClass irClass, IrSimpleFunction function, int i, int i2, Function2<? super IrBlockBodyBuilder, ? super IrFunction, Unit> makeBody) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        Intrinsics.checkNotNullParameter(function, "function");
        Intrinsics.checkNotNullParameter(makeBody, "makeBody");
        IrSimpleFunction irSimpleFunctionAddFunctionOverride = addFunctionOverride(irClass, function, i, i2);
        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(this.context, irSimpleFunctionAddFunctionOverride.getSymbol(), 0, 0, 6, (Object) null);
        IrBlockBodyBuilder irBlockBodyBuilder = new IrBlockBodyBuilder(declarationIrBuilderCreateIrBuilder$default.getContext(), declarationIrBuilderCreateIrBuilder$default.getScope(), declarationIrBuilderCreateIrBuilder$default.getStartOffset(), declarationIrBuilderCreateIrBuilder$default.getEndOffset());
        makeBody.invoke(irBlockBodyBuilder, irSimpleFunctionAddFunctionOverride);
        irSimpleFunctionAddFunctionOverride.setBody(irBlockBodyBuilder.getIrBlockBody());
        return irSimpleFunctionAddFunctionOverride;
    }

    protected final void generateErrorForInlineBody(IrSimpleFunction irSimpleFunction) {
        Intrinsics.checkNotNullParameter(irSimpleFunction, "<this>");
        irSimpleFunction.setBody(new IrExpressionBodyImpl(irSimpleFunction.getStartOffset(), irSimpleFunction.getEndOffset(), new IrErrorExpressionImpl(irSimpleFunction.getStartOffset(), irSimpleFunction.getEndOffset(), irSimpleFunction.getReturnType(), "This is a stub representing a copy of a suspend method without the state machine (used by the inliner). Since the difference is at the bytecode level, the body is still on the original function. Use suspendForInlineToOriginal() to retrieve it.")));
    }

    protected final IrValueParameter addCompletionValueParameter(IrFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        return DeclarationBuildersKt.addValueParameter$default(irFunction, CoroutineConstantsKt.SUSPEND_FUNCTION_COMPLETION_PARAMETER_NAME, continuationType(irFunction), (IrDeclarationOrigin) null, 4, (Object) null);
    }

    protected final IrType continuationType(IrFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        return IrTypesKt.makeNullable(IrTypesKt.typeWith(this.context.getIr().getSymbols().getContinuationClass(), irFunction.getReturnType()));
    }
}
