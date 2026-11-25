package org.jetbrains.kotlin.backend.jvm.codegen;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrInlineUtilsKt;
import org.jetbrains.kotlin.backend.jvm.mapping.IrTypeMapper;
import org.jetbrains.kotlin.codegen.IrExpressionLambda;
import org.jetbrains.kotlin.codegen.inline.CapturedParamDesc;
import org.jetbrains.kotlin.codegen.inline.ExpressionLambda;
import org.jetbrains.kotlin.codegen.inline.LambdaInfo;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.descriptors.IrBasedDescriptorsKt;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionReference;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.DumpIrTreeKt;
import org.jetbrains.kotlin.ir.util.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.Method;

/* JADX INFO: compiled from: IrInlineCodegen.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u00012\u00020\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007R\u001a\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\u000e8F¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0011\u001a\u00020\u00128VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0015\u001a\u00020\u0016X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u001c\u0010\u0019\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u001a0\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\fR\u0014\u0010\u001c\u001a\u00020\u001aX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0014\u0010\u001f\u001a\u00020\u0012X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0014R\u0014\u0010 \u001a\u00020!X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b$\u0010%¨\u0006&"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/codegen/IrExpressionLambdaImpl;", "Lorg/jetbrains/kotlin/codegen/inline/ExpressionLambda;", "Lorg/jetbrains/kotlin/codegen/IrExpressionLambda;", "codegen", "Lorg/jetbrains/kotlin/backend/jvm/codegen/ExpressionCodegen;", "reference", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;", "(Lorg/jetbrains/kotlin/backend/jvm/codegen/ExpressionCodegen;Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;)V", "capturedVars", "", "Lorg/jetbrains/kotlin/codegen/inline/CapturedParamDesc;", "getCapturedVars", "()Ljava/util/List;", "function", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "getFunction", "()Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "hasDispatchReceiver", "", "getHasDispatchReceiver", "()Z", "invokeMethod", "Lorg/jetbrains/org/objectweb/asm/commons/Method;", "getInvokeMethod", "()Lorg/jetbrains/org/objectweb/asm/commons/Method;", "invokeMethodParameters", "Lorg/jetbrains/kotlin/types/KotlinType;", "getInvokeMethodParameters", "invokeMethodReturnType", "getInvokeMethodReturnType", "()Lorg/jetbrains/kotlin/types/KotlinType;", "isExtensionLambda", "lambdaClassType", "Lorg/jetbrains/org/objectweb/asm/Type;", "getLambdaClassType", "()Lorg/jetbrains/org/objectweb/asm/Type;", "getReference", "()Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrExpressionLambdaImpl extends ExpressionLambda implements IrExpressionLambda {
    private final List<CapturedParamDesc> capturedVars;
    private final Method invokeMethod;
    private final List<KotlinType> invokeMethodParameters;
    private final KotlinType invokeMethodReturnType;
    private final boolean isExtensionLambda;
    private final Type lambdaClassType;
    private final IrFunctionReference reference;

    @Override // org.jetbrains.kotlin.codegen.inline.LambdaInfo
    public boolean getHasDispatchReceiver() {
        return false;
    }

    public final IrFunctionReference getReference() {
        return this.reference;
    }

    public IrExpressionLambdaImpl(ExpressionCodegen codegen, IrFunctionReference reference) {
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(reference, "reference");
        this.reference = reference;
        this.isExtensionLambda = getFunction().getExtensionReceiverParameter() != null && reference.getExtensionReceiver() == null;
        Type localClassType = codegen.getContext().getLocalClassType(reference);
        if (localClassType == null) {
            throw new AssertionError("callable reference " + DumpIrTreeKt.dump$default(reference, null, 1, null) + " has no name in context");
        }
        this.lambdaClassType = localClassType;
        Method methodMapAsmMethod = codegen.getMethodSignatureMapper().mapAsmMethod(getFunction());
        List<Pair<IrValueParameter, IrExpression>> argumentsWithIr = IrUtilsKt.getArgumentsWithIr(reference);
        boolean isExtensionLambda = getIsExtensionLambda();
        int size = argumentsWithIr.size() + (isExtensionLambda ? 1 : 0);
        List<Pair<IrValueParameter, IrExpression>> list = argumentsWithIr;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        int i = 0;
        for (Object obj : list) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            IrValueParameter irValueParameter = (IrValueParameter) ((Pair) obj).component1();
            boolean z = JvmIrInlineUtilsKt.isInlineParameter(irValueParameter) && IrTypeUtilsKt.isSuspendFunction(irValueParameter.getType());
            String strAsString = irValueParameter.getName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            Type type = methodMapAsmMethod.getArgumentTypes()[i + (isExtensionLambda ? 1 : 0)];
            Intrinsics.checkNotNullExpressionValue(type, "get(...)");
            arrayList.add(LambdaInfo.INSTANCE.capturedParamDesc(this, strAsString, type, z));
            i = i2;
        }
        this.capturedVars = arrayList;
        List<IrValueParameter> explicitParameters = IrUtilsKt.getExplicitParameters(getFunction());
        List listPlus = CollectionsKt.plus((Collection) CollectionsKt.take(explicitParameters, isExtensionLambda ? 1 : 0), (Iterable) CollectionsKt.drop(explicitParameters, size));
        Type[] argumentTypes = methodMapAsmMethod.getArgumentTypes();
        Intrinsics.checkNotNull(argumentTypes);
        List listPlus2 = CollectionsKt.plus((Collection) ArraysKt.take(argumentTypes, isExtensionLambda ? 1 : 0), (Iterable) ArraysKt.drop(argumentTypes, size));
        IrType irTypeOriginalReturnTypeOfSuspendFunctionReturningUnboxedInlineClass = CoroutineCodegenKt.originalReturnTypeOfSuspendFunctionReturningUnboxedInlineClass(getFunction());
        Type typeMapType$default = irTypeOriginalReturnTypeOfSuspendFunctionReturningUnboxedInlineClass != null ? IrTypeMapper.mapType$default(codegen.getTypeMapper(), irTypeOriginalReturnTypeOfSuspendFunctionReturningUnboxedInlineClass, null, null, false, 14, null) : null;
        this.invokeMethod = new Method(methodMapAsmMethod.getName(), typeMapType$default == null ? methodMapAsmMethod.getReturnType() : typeMapType$default, (Type[]) listPlus2.toArray(new Type[0]));
        List list2 = listPlus;
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        Iterator it = list2.iterator();
        while (it.hasNext()) {
            arrayList2.add(IrBasedDescriptorsKt.toIrBasedKotlinType$default(((IrValueParameter) it.next()).getType(), null, 1, null));
        }
        this.invokeMethodParameters = arrayList2;
        this.invokeMethodReturnType = IrBasedDescriptorsKt.toIrBasedKotlinType$default(irTypeOriginalReturnTypeOfSuspendFunctionReturningUnboxedInlineClass == null ? getFunction().getReturnType() : irTypeOriginalReturnTypeOfSuspendFunctionReturningUnboxedInlineClass, null, 1, null);
    }

    @Override // org.jetbrains.kotlin.codegen.IrExpressionLambda
    /* JADX INFO: renamed from: isExtensionLambda, reason: from getter */
    public boolean getIsExtensionLambda() {
        return this.isExtensionLambda;
    }

    public final IrFunction getFunction() {
        return this.reference.getSymbol().getOwner();
    }

    @Override // org.jetbrains.kotlin.codegen.inline.LambdaInfo
    public Type getLambdaClassType() {
        return this.lambdaClassType;
    }

    @Override // org.jetbrains.kotlin.codegen.inline.LambdaInfo
    public List<CapturedParamDesc> getCapturedVars() {
        return this.capturedVars;
    }

    @Override // org.jetbrains.kotlin.codegen.inline.LambdaInfo
    public Method getInvokeMethod() {
        return this.invokeMethod;
    }

    @Override // org.jetbrains.kotlin.codegen.inline.LambdaInfo
    public List<KotlinType> getInvokeMethodParameters() {
        return this.invokeMethodParameters;
    }

    @Override // org.jetbrains.kotlin.codegen.inline.LambdaInfo
    public KotlinType getInvokeMethodReturnType() {
        return this.invokeMethodReturnType;
    }
}
