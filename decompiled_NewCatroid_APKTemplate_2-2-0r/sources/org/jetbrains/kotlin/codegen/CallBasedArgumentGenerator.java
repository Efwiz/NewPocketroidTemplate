package org.jetbrains.kotlin.codegen;

import com.reandroid.dex.sections.Marker;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.DefaultValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.ExpressionValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.VarargValueArgument;
import org.jetbrains.kotlin.resolve.inline.InlineUtil;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.kotlin.types.FlexibleTypesKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: CallBasedArgumentGenerator.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B1\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0007¢\u0006\u0002\u0010\u000bJ\u0018\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0014J\u0018\u0010\u0019\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u001aH\u0014J\u0018\u0010\u001b\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u001cH\u0014J\u0010\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J\u0016\u0010\u001f\u001a\u00020\u00142\f\u0010 \u001a\b\u0012\u0004\u0012\u00020!0\u0007H\u0014R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\u000f\u001a\u00020\u0010*\u00020\b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/codegen/CallBasedArgumentGenerator;", "Lorg/jetbrains/kotlin/codegen/ArgumentGenerator;", "codegen", "Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;", "callGenerator", "Lorg/jetbrains/kotlin/codegen/CallGenerator;", "valueParameters", "", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "valueParameterTypes", "Lorg/jetbrains/org/objectweb/asm/Type;", "(Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;Lorg/jetbrains/kotlin/codegen/CallGenerator;Ljava/util/List;Ljava/util/List;)V", "isPolymorphicSignature", "", "isVarargInvoke", "unsubstitutedType", "Lorg/jetbrains/kotlin/types/KotlinType;", "getUnsubstitutedType", "(Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;)Lorg/jetbrains/kotlin/types/KotlinType;", "generateDefault", "", ASTExpr.DEFAULT_INDEX_VARIABLE_NAME, "", "argument", "Lorg/jetbrains/kotlin/resolve/calls/model/DefaultValueArgument;", "generateExpression", "Lorg/jetbrains/kotlin/resolve/calls/model/ExpressionValueArgument;", "generateVararg", "Lorg/jetbrains/kotlin/resolve/calls/model/VarargValueArgument;", "getJvmKotlinType", "Lorg/jetbrains/kotlin/codegen/JvmKotlinType;", "reorderArgumentsIfNeeded", "args", "Lorg/jetbrains/kotlin/codegen/ArgumentAndDeclIndex;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CallBasedArgumentGenerator extends ArgumentGenerator {
    private final CallGenerator callGenerator;
    private final ExpressionCodegen codegen;
    private final boolean isPolymorphicSignature;
    private final boolean isVarargInvoke;
    private final List<Type> valueParameterTypes;
    private final List<ValueParameterDescriptor> valueParameters;

    /* JADX WARN: Code duplicated, block: B:19:0x0065  */
    /* JADX WARN: Multi-variable type inference failed */
    public CallBasedArgumentGenerator(ExpressionCodegen codegen, CallGenerator callGenerator, List<? extends ValueParameterDescriptor> valueParameters, List<Type> valueParameterTypes) {
        boolean z;
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(callGenerator, "callGenerator");
        Intrinsics.checkNotNullParameter(valueParameters, "valueParameters");
        Intrinsics.checkNotNullParameter(valueParameterTypes, "valueParameterTypes");
        this.codegen = codegen;
        this.callGenerator = callGenerator;
        this.valueParameters = valueParameters;
        this.valueParameterTypes = valueParameterTypes;
        ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) CollectionsKt.firstOrNull((List) valueParameters);
        boolean zIsDeclarationOfBigArityFunctionInvoke = JvmCodegenUtil.isDeclarationOfBigArityFunctionInvoke(valueParameterDescriptor != null ? valueParameterDescriptor.getContainingModule() : null);
        this.isVarargInvoke = zIsDeclarationOfBigArityFunctionInvoke;
        if (codegen.getState().getLanguageVersionSettings().supportsFeature(LanguageFeature.PolymorphicSignature)) {
            ValueParameterDescriptor valueParameterDescriptor2 = (ValueParameterDescriptor) CollectionsKt.firstOrNull((List) valueParameters);
            CallableDescriptor packageFragment = valueParameterDescriptor2 != null ? valueParameterDescriptor2.getContainingModule() : null;
            FunctionDescriptor functionDescriptor = packageFragment instanceof FunctionDescriptor ? (FunctionDescriptor) packageFragment : null;
            if (functionDescriptor != null) {
                z = JvmCodegenUtil.isPolymorphicSignature(functionDescriptor);
            }
        }
        this.isPolymorphicSignature = z;
        if (zIsDeclarationOfBigArityFunctionInvoke || z) {
            return;
        }
        valueParameters.size();
        valueParameterTypes.size();
    }

    @Override // org.jetbrains.kotlin.codegen.ArgumentGenerator
    protected void generateExpression(int i, ExpressionValueArgument argument) {
        JvmKotlinType jvmKotlinType;
        Intrinsics.checkNotNullParameter(argument, "argument");
        CallGenerator callGenerator = this.callGenerator;
        ValueParameterDescriptor valueParameterDescriptor = this.valueParameters.get(i);
        ValueArgument valueArgument = argument.getValueArgument();
        Intrinsics.checkNotNull(valueArgument);
        KtExpression argumentExpression = valueArgument.getArgumentExpression();
        Intrinsics.checkNotNull(argumentExpression);
        if (this.isVarargInvoke) {
            Type OBJECT_TYPE = AsmTypes.OBJECT_TYPE;
            Intrinsics.checkNotNullExpressionValue(OBJECT_TYPE, "OBJECT_TYPE");
            jvmKotlinType = new JvmKotlinType(OBJECT_TYPE, null, 2, null);
        } else {
            jvmKotlinType = getJvmKotlinType(i);
        }
        callGenerator.genValueAndPut(valueParameterDescriptor, argumentExpression, jvmKotlinType, i);
    }

    @Override // org.jetbrains.kotlin.codegen.ArgumentGenerator
    protected void generateDefault(int i, DefaultValueArgument argument) {
        Intrinsics.checkNotNullParameter(argument, "argument");
        CallGenerator callGenerator = this.callGenerator;
        JvmKotlinType jvmKotlinType = getJvmKotlinType(i);
        StackValue stackValueCreateDefaultValue = StackValue.createDefaultValue(this.valueParameterTypes.get(i));
        Intrinsics.checkNotNullExpressionValue(stackValueCreateDefaultValue, "createDefaultValue(...)");
        callGenerator.putValueIfNeeded(jvmKotlinType, stackValueCreateDefaultValue, InlineUtil.isInlineParameter(this.valueParameters.get(i)) ? ValueKind.DEFAULT_INLINE_PARAMETER : ValueKind.DEFAULT_PARAMETER, i);
    }

    @Override // org.jetbrains.kotlin.codegen.ArgumentGenerator
    protected void generateVararg(int i, VarargValueArgument argument) {
        Intrinsics.checkNotNullParameter(argument, "argument");
        if (this.isPolymorphicSignature) {
            Iterator<ValueArgument> it = argument.getArguments().iterator();
            int i2 = 0;
            while (it.hasNext()) {
                KtExpression argumentExpression = it.next().getArgumentExpression();
                Intrinsics.checkNotNull(argumentExpression);
                this.callGenerator.genValueAndPut(null, argumentExpression, new JvmKotlinType(this.valueParameterTypes.get(i2), this.codegen.kotlinType(argumentExpression)), i2);
                i2++;
            }
            return;
        }
        ExpressionCodegen expressionCodegen = this.codegen;
        KotlinType type = this.valueParameters.get(i).getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        StackValue stackValueGenVarargs = expressionCodegen.genVarargs(argument, FlexibleTypesKt.upperIfFlexible(type));
        Intrinsics.checkNotNullExpressionValue(stackValueGenVarargs, "genVarargs(...)");
        this.callGenerator.putValueIfNeeded(getJvmKotlinType(i), stackValueGenVarargs, ValueKind.GENERAL_VARARG, i);
    }

    @Override // org.jetbrains.kotlin.codegen.ArgumentGenerator
    protected void reorderArgumentsIfNeeded(List<ArgumentAndDeclIndex> args) {
        Intrinsics.checkNotNullParameter(args, "args");
        this.callGenerator.reorderArgumentsIfNeeded(args, this.valueParameterTypes);
    }

    private final JvmKotlinType getJvmKotlinType(int i) {
        return new JvmKotlinType(this.valueParameterTypes.get(i), getUnsubstitutedType(this.valueParameters.get(i)));
    }

    private final KotlinType getUnsubstitutedType(ValueParameterDescriptor valueParameterDescriptor) {
        KotlinType type = valueParameterDescriptor.getContainingModule().getOriginal().getValueParameters().get(valueParameterDescriptor.getIndex()).getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        return type;
    }
}
