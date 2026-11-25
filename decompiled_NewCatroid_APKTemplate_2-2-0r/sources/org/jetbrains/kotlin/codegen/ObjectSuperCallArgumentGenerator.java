package org.jetbrains.kotlin.codegen;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.reandroid.dex.sections.Marker;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.resolve.calls.model.DefaultValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.ExpressionValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.VarargValueArgument;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodParameterSignature;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: compiled from: ObjectSuperCallArgumentGenerator.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000v\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0015\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001BG\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0003\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e¢\u0006\u0002\u0010\u0010J.\u0010\u0013\u001a\u00020\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\u00032\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00160\u00032\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0016J\u0018\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\f2\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J\u0018\u0010\u001f\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\f2\u0006\u0010\u001d\u001a\u00020 H\u0016J\u0010\u0010!\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\fH\u0002J\u0018\u0010\"\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\f2\u0006\u0010\u001d\u001a\u00020#H\u0016J\u0016\u0010$\u001a\u00020\u001b2\f\u0010%\u001a\b\u0012\u0004\u0012\u00020&0\u0003H\u0014R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006'"}, d2 = {"Lorg/jetbrains/kotlin/codegen/ObjectSuperCallArgumentGenerator;", "Lorg/jetbrains/kotlin/codegen/ArgumentGenerator;", "parameters", "", "Lorg/jetbrains/kotlin/resolve/jvm/jvmSignature/JvmMethodParameterSignature;", "iv", "Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", "superValueParameters", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "typeMapper", "Lorg/jetbrains/kotlin/codegen/state/KotlinTypeMapper;", TypedValues.CycleType.S_WAVE_OFFSET, "", "superConstructorCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "Lorg/jetbrains/kotlin/descriptors/ConstructorDescriptor;", "(Ljava/util/List;Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;Ljava/util/List;Lorg/jetbrains/kotlin/codegen/state/KotlinTypeMapper;ILorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;)V", "offsets", "", CompilerOptions.GENERATE, "Lorg/jetbrains/kotlin/codegen/DefaultCallArgs;", "valueArgumentsByIndex", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedValueArgument;", "actualArgs", "calleeDescriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "generateDefault", "", ASTExpr.DEFAULT_INDEX_VARIABLE_NAME, "argument", "Lorg/jetbrains/kotlin/resolve/calls/model/DefaultValueArgument;", "generateExpression", "Lorg/jetbrains/kotlin/resolve/calls/model/ExpressionValueArgument;", "generateSuperCallArgument", "generateVararg", "Lorg/jetbrains/kotlin/resolve/calls/model/VarargValueArgument;", "reorderArgumentsIfNeeded", "args", "Lorg/jetbrains/kotlin/codegen/ArgumentAndDeclIndex;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ObjectSuperCallArgumentGenerator extends ArgumentGenerator {
    private final InstructionAdapter iv;
    private final int[] offsets;
    private final List<JvmMethodParameterSignature> parameters;
    private final List<ValueParameterDescriptor> superValueParameters;
    private final KotlinTypeMapper typeMapper;

    @Override // org.jetbrains.kotlin.codegen.ArgumentGenerator
    protected void reorderArgumentsIfNeeded(List<ArgumentAndDeclIndex> args) {
        Intrinsics.checkNotNullParameter(args, "args");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ObjectSuperCallArgumentGenerator(List<JvmMethodParameterSignature> parameters, InstructionAdapter iv, List<? extends ValueParameterDescriptor> superValueParameters, KotlinTypeMapper typeMapper, int i, ResolvedCall<ConstructorDescriptor> superConstructorCall) {
        Intrinsics.checkNotNullParameter(parameters, "parameters");
        Intrinsics.checkNotNullParameter(iv, "iv");
        Intrinsics.checkNotNullParameter(superValueParameters, "superValueParameters");
        Intrinsics.checkNotNullParameter(typeMapper, "typeMapper");
        Intrinsics.checkNotNullParameter(superConstructorCall, "superConstructorCall");
        this.parameters = parameters;
        this.iv = iv;
        this.superValueParameters = superValueParameters;
        this.typeMapper = typeMapper;
        int size = parameters.size();
        int[] iArr = new int[size];
        for (int i2 = 0; i2 < size; i2++) {
            iArr[i2] = -1;
        }
        this.offsets = iArr;
        Map<ValueParameterDescriptor, ResolvedValueArgument> valueArguments = superConstructorCall.getValueArguments();
        Intrinsics.checkNotNullExpressionValue(valueArguments, "getValueArguments(...)");
        for (Map.Entry<ValueParameterDescriptor, ResolvedValueArgument> entry : valueArguments.entrySet()) {
            ValueParameterDescriptor key = entry.getKey();
            if (!(entry.getValue() instanceof DefaultValueArgument)) {
                int index = key.getIndex();
                this.offsets[index] = i;
                i += this.parameters.get(index).getAsmType().getSize();
            }
        }
    }

    @Override // org.jetbrains.kotlin.codegen.ArgumentGenerator
    public DefaultCallArgs generate(List<? extends ResolvedValueArgument> valueArgumentsByIndex, List<? extends ResolvedValueArgument> actualArgs, CallableDescriptor calleeDescriptor) {
        Intrinsics.checkNotNullParameter(valueArgumentsByIndex, "valueArgumentsByIndex");
        Intrinsics.checkNotNullParameter(actualArgs, "actualArgs");
        return super.generate(valueArgumentsByIndex, valueArgumentsByIndex, calleeDescriptor);
    }

    @Override // org.jetbrains.kotlin.codegen.ArgumentGenerator
    public void generateExpression(int i, ExpressionValueArgument argument) {
        Intrinsics.checkNotNullParameter(argument, "argument");
        generateSuperCallArgument(i);
    }

    @Override // org.jetbrains.kotlin.codegen.ArgumentGenerator
    public void generateDefault(int i, DefaultValueArgument argument) {
        Intrinsics.checkNotNullParameter(argument, "argument");
        Type asmType = this.parameters.get(i).getAsmType();
        Intrinsics.checkNotNullExpressionValue(asmType, "getAsmType(...)");
        AsmUtil.pushDefaultValueOnStack(asmType, this.iv);
    }

    @Override // org.jetbrains.kotlin.codegen.ArgumentGenerator
    public void generateVararg(int i, VarargValueArgument argument) {
        Intrinsics.checkNotNullParameter(argument, "argument");
        generateSuperCallArgument(i);
    }

    private final void generateSuperCallArgument(int i) {
        Type asmType = this.parameters.get(i).getAsmType();
        Intrinsics.checkNotNullExpressionValue(asmType, "getAsmType(...)");
        int i2 = this.offsets[i];
        if (i2 == -1) {
            throw new AssertionError("Unknown parameter value at index " + i + " with type " + asmType);
        }
        this.iv.load(i2, asmType);
    }
}
