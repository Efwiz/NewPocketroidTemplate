package org.jetbrains.kotlin.codegen;

import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: compiled from: CallGenerator.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001:\u0001'J.\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J.\u0010\f\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH&J*\u0010\r\u001a\u00020\u00032\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H&J\b\u0010\u0016\u001a\u00020\u0003H&J \u0010\u0017\u001a\u00020\u00032\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u0015H&J\b\u0010\u001d\u001a\u00020\u0003H&J\u0018\u0010\u001e\u001a\u00020\u00032\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u001f\u001a\u00020\u0019H\u0016J,\u0010\u001e\u001a\u00020\u00032\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u001f\u001a\u00020\u00192\b\b\u0002\u0010 \u001a\u00020!2\b\b\u0002\u0010\u0014\u001a\u00020\u0015H&J$\u0010\"\u001a\u00020\u00032\f\u0010#\u001a\b\u0012\u0004\u0012\u00020%0$2\f\u0010&\u001a\b\u0012\u0004\u0012\u00020\u001b0$H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006(À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/codegen/CallGenerator;", "", "genCall", "", "callableMethod", "Lorg/jetbrains/kotlin/codegen/Callable;", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "callDefault", "", "codegen", "Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;", "genCallInner", "genValueAndPut", "valueParameterDescriptor", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "argumentExpression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "parameterType", "Lorg/jetbrains/kotlin/codegen/JvmKotlinType;", "parameterIndex", "", "processHiddenParameters", "putCapturedValueOnStack", "stackValue", "Lorg/jetbrains/kotlin/codegen/StackValue;", "valueType", "Lorg/jetbrains/org/objectweb/asm/Type;", "paramIndex", "putHiddenParamsIntoLocals", "putValueIfNeeded", "value", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/codegen/ValueKind;", "reorderArgumentsIfNeeded", "actualArgsWithDeclIndex", "", "Lorg/jetbrains/kotlin/codegen/ArgumentAndDeclIndex;", "valueParameterTypes", "DefaultCallGenerator", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface CallGenerator {
    void genCallInner(Callable callableMethod, ResolvedCall<?> resolvedCall, boolean callDefault, ExpressionCodegen codegen);

    void genValueAndPut(ValueParameterDescriptor valueParameterDescriptor, KtExpression argumentExpression, JvmKotlinType parameterType, int parameterIndex);

    void processHiddenParameters();

    void putCapturedValueOnStack(StackValue stackValue, Type valueType, int paramIndex);

    void putHiddenParamsIntoLocals();

    void putValueIfNeeded(JvmKotlinType parameterType, StackValue value, ValueKind kind, int parameterIndex);

    void reorderArgumentsIfNeeded(List<ArgumentAndDeclIndex> actualArgsWithDeclIndex, List<Type> valueParameterTypes);

    /* JADX INFO: compiled from: CallGenerator.kt */
    @Metadata(d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J.\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0002\b\u0003\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0002\u001a\u00020\u0003H\u0016J*\u0010\r\u001a\u00020\u00062\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\b\u0010\u0016\u001a\u00020\u0006H\u0016J \u0010\u0017\u001a\u00020\u00062\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u0015H\u0016J\b\u0010\u001d\u001a\u00020\u0006H\u0016J(\u0010\u001e\u001a\u00020\u00062\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u001f\u001a\u00020\u00192\u0006\u0010 \u001a\u00020!2\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J$\u0010\"\u001a\u00020\u00062\f\u0010#\u001a\b\u0012\u0004\u0012\u00020%0$2\f\u0010&\u001a\b\u0012\u0004\u0012\u00020\u001b0$H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006'"}, d2 = {"Lorg/jetbrains/kotlin/codegen/CallGenerator$DefaultCallGenerator;", "Lorg/jetbrains/kotlin/codegen/CallGenerator;", "codegen", "Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;", "(Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;)V", "genCallInner", "", "callableMethod", "Lorg/jetbrains/kotlin/codegen/Callable;", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "callDefault", "", "genValueAndPut", "valueParameterDescriptor", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "argumentExpression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "parameterType", "Lorg/jetbrains/kotlin/codegen/JvmKotlinType;", "parameterIndex", "", "processHiddenParameters", "putCapturedValueOnStack", "stackValue", "Lorg/jetbrains/kotlin/codegen/StackValue;", "valueType", "Lorg/jetbrains/org/objectweb/asm/Type;", "paramIndex", "putHiddenParamsIntoLocals", "putValueIfNeeded", "value", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/codegen/ValueKind;", "reorderArgumentsIfNeeded", "actualArgsWithDeclIndex", "", "Lorg/jetbrains/kotlin/codegen/ArgumentAndDeclIndex;", "valueParameterTypes", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class DefaultCallGenerator implements CallGenerator {
        private final ExpressionCodegen codegen;

        @Override // org.jetbrains.kotlin.codegen.CallGenerator
        public void processHiddenParameters() {
        }

        @Override // org.jetbrains.kotlin.codegen.CallGenerator
        public void putHiddenParamsIntoLocals() {
        }

        public DefaultCallGenerator(ExpressionCodegen codegen) {
            Intrinsics.checkNotNullParameter(codegen, "codegen");
            this.codegen = codegen;
        }

        @Override // org.jetbrains.kotlin.codegen.CallGenerator
        public void genCallInner(Callable callableMethod, ResolvedCall<?> resolvedCall, boolean callDefault, ExpressionCodegen codegen) {
            Intrinsics.checkNotNullParameter(callableMethod, "callableMethod");
            Intrinsics.checkNotNullParameter(codegen, "codegen");
            if (callDefault) {
                InstructionAdapter v = codegen.v;
                Intrinsics.checkNotNullExpressionValue(v, "v");
                ((CallableMethod) callableMethod).genInvokeDefaultInstruction(v);
            } else {
                InstructionAdapter v2 = codegen.v;
                Intrinsics.checkNotNullExpressionValue(v2, "v");
                callableMethod.genInvokeInstruction(v2);
            }
        }

        @Override // org.jetbrains.kotlin.codegen.CallGenerator
        public void genValueAndPut(ValueParameterDescriptor valueParameterDescriptor, KtExpression argumentExpression, JvmKotlinType parameterType, int parameterIndex) {
            Intrinsics.checkNotNullParameter(argumentExpression, "argumentExpression");
            Intrinsics.checkNotNullParameter(parameterType, "parameterType");
            CallableDescriptor packageFragment = valueParameterDescriptor != null ? valueParameterDescriptor.getContainingDeclaration() : null;
            boolean z = packageFragment != null && JvmCodegenUtil.isDeclarationOfBigArityFunctionInvoke(packageFragment);
            InstructionAdapter instructionAdapter = this.codegen.v;
            if (z) {
                if (parameterIndex == 0) {
                    Intrinsics.checkNotNull(packageFragment);
                    instructionAdapter.iconst(packageFragment.getValueParameters().size());
                    instructionAdapter.newarray(AsmTypes.OBJECT_TYPE);
                }
                instructionAdapter.dup();
                instructionAdapter.iconst(parameterIndex);
            }
            this.codegen.gen(argumentExpression).put(parameterType.getType(), parameterType.getKotlinType(), instructionAdapter);
            if (z) {
                instructionAdapter.astore(AsmTypes.OBJECT_TYPE);
            }
        }

        @Override // org.jetbrains.kotlin.codegen.CallGenerator
        public void putCapturedValueOnStack(StackValue stackValue, Type valueType, int paramIndex) {
            Intrinsics.checkNotNullParameter(stackValue, "stackValue");
            Intrinsics.checkNotNullParameter(valueType, "valueType");
            stackValue.put(stackValue.type, stackValue.kotlinType, this.codegen.v);
        }

        @Override // org.jetbrains.kotlin.codegen.CallGenerator
        public void putValueIfNeeded(JvmKotlinType parameterType, StackValue value, ValueKind kind, int parameterIndex) {
            Intrinsics.checkNotNullParameter(parameterType, "parameterType");
            Intrinsics.checkNotNullParameter(value, "value");
            Intrinsics.checkNotNullParameter(kind, "kind");
            value.put(value.type, value.kotlinType, this.codegen.v);
        }

        @Override // org.jetbrains.kotlin.codegen.CallGenerator
        public void reorderArgumentsIfNeeded(List<ArgumentAndDeclIndex> actualArgsWithDeclIndex, List<Type> valueParameterTypes) {
            Intrinsics.checkNotNullParameter(actualArgsWithDeclIndex, "actualArgsWithDeclIndex");
            Intrinsics.checkNotNullParameter(valueParameterTypes, "valueParameterTypes");
            FrameMapBase<DeclarationDescriptor>.Mark mark = this.codegen.myFrameMap.mark();
            Iterable iterableWithIndex = CollectionsKt.withIndex(actualArgsWithDeclIndex);
            ArrayList arrayList = new ArrayList();
            boolean z = false;
            for (Object obj : iterableWithIndex) {
                if (z) {
                    arrayList.add(obj);
                } else {
                    IndexedValue indexedValue = (IndexedValue) obj;
                    if (((ArgumentAndDeclIndex) indexedValue.getValue()).getDeclIndex() != indexedValue.getIndex()) {
                        arrayList.add(obj);
                        z = true;
                    }
                }
            }
            List listReversed = CollectionsKt.reversed(arrayList);
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(listReversed, 10));
            Iterator it = listReversed.iterator();
            while (it.hasNext()) {
                ArgumentAndDeclIndex argumentAndDeclIndex = (ArgumentAndDeclIndex) ((IndexedValue) it.next()).getValue();
                Type type = valueParameterTypes.get(argumentAndDeclIndex.getDeclIndex());
                StackValue.Local local = StackValue.local(this.codegen.getFrameMap().enterTemp(type), type);
                Intrinsics.checkNotNullExpressionValue(local, "local(...)");
                local.store(StackValue.onStack(type), this.codegen.v);
                arrayList2.add(new Pair(Integer.valueOf(argumentAndDeclIndex.getDeclIndex()), local));
            }
            for (Pair pair : CollectionsKt.sortedWith(arrayList2, new Comparator() { // from class: org.jetbrains.kotlin.codegen.CallGenerator$DefaultCallGenerator$reorderArgumentsIfNeeded$$inlined$sortedBy$1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // java.util.Comparator
                public final int compare(T t, T t2) {
                    return ComparisonsKt.compareValues((Integer) ((Pair) t).getFirst(), (Integer) ((Pair) t2).getFirst());
                }
            })) {
                ((StackValue.Local) pair.getSecond()).put(valueParameterTypes.get(((Number) pair.getFirst()).intValue()), this.codegen.v);
            }
            mark.dropTo();
        }
    }

    default void genCall(Callable callableMethod, ResolvedCall<?> resolvedCall, boolean callDefault, ExpressionCodegen codegen) {
        KtExpression calleeExpression;
        Intrinsics.checkNotNullParameter(callableMethod, "callableMethod");
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        if (resolvedCall != null && (calleeExpression = resolvedCall.getCall().getCalleeExpression()) != null) {
            codegen.markStartLineNumber(calleeExpression);
        }
        genCallInner(callableMethod, resolvedCall, callDefault, codegen);
    }

    default void putValueIfNeeded(JvmKotlinType parameterType, StackValue value) {
        Intrinsics.checkNotNullParameter(parameterType, "parameterType");
        Intrinsics.checkNotNullParameter(value, "value");
        putValueIfNeeded$default(this, parameterType, value, ValueKind.GENERAL, 0, 8, null);
    }

    static /* synthetic */ void putValueIfNeeded$default(CallGenerator callGenerator, JvmKotlinType jvmKotlinType, StackValue stackValue, ValueKind valueKind, int i, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: putValueIfNeeded");
        }
        if ((i2 & 4) != 0) {
            valueKind = ValueKind.GENERAL;
        }
        if ((i2 & 8) != 0) {
            i = -1;
        }
        callGenerator.putValueIfNeeded(jvmKotlinType, stackValue, valueKind, i);
    }
}
