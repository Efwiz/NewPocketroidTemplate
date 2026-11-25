package org.jetbrains.kotlin.codegen;

import com.reandroid.dex.sections.Marker;
import java.util.Iterator;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner;
import org.jetbrains.kotlin.codegen.optimization.common.InsnSequence;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.config.JVMAssertionsMode;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorUtilKt;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.incremental.IncrementalJvmCacheKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTraceContext;
import org.jetbrains.kotlin.resolve.DelegatingBindingTrace;
import org.jetbrains.kotlin.resolve.calls.model.MutableDataFlowInfoForArguments;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCallImpl;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedValueArgument;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;
import org.jetbrains.kotlin.resolve.calls.tasks.ExplicitReceiverKind;
import org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOrigin;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.MethodVisitor;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.FieldInsnNode;
import org.jetbrains.org.objectweb.asm.tree.InsnList;
import org.jetbrains.org.objectweb.asm.tree.LdcInsnNode;
import org.jetbrains.org.objectweb.asm.tree.MethodInsnNode;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;

/* JADX INFO: compiled from: AssertCodegenUtil.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000T\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u000e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t\u001a.\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\n\u0010\u000e\u001a\u0006\u0012\u0002\b\u00030\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\n\u0010\u0012\u001a\u0006\u0012\u0002\b\u00030\u0013\u001a\u001e\u0010\u0014\u001a\u00020\u000b2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u0001\u001a(\u0010\u001a\u001a\u00020\u000b2\n\u0010\u000e\u001a\u0006\u0012\u0002\b\u00030\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\n\u0010\u0012\u001a\u0006\u0012\u0002\b\u00030\u0013H\u0002\u001a\u001c\u0010\u001b\u001a\u00020\u000b2\n\u0010\u000e\u001a\u0006\u0012\u0002\b\u00030\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002\u001a\u0012\u0010\u001c\u001a\u00020\u001d2\n\u0010\u000e\u001a\u0006\u0012\u0002\b\u00030\u000f\u001a\u0016\u0010\u001e\u001a\u00020\u000b2\u0006\u0010\u001f\u001a\u00020\u00072\u0006\u0010\u0019\u001a\u00020\u0001\u001a\n\u0010 \u001a\u00020\u001d*\u00020\t\u001a\f\u0010!\u001a\u00020\u001d*\u00020\tH\u0002\u001a\f\u0010\"\u001a\u00020\u001d*\u00020\tH\u0002\u001a\n\u0010#\u001a\u00020\u001d*\u00020$\u001a\"\u0010%\u001a\b\u0012\u0004\u0012\u0002H&0\u000f\"\b\b\u0000\u0010&*\u00020\t*\b\u0012\u0004\u0012\u0002H&0\u000fH\u0002\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0003\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0004\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0005\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000¨\u0006'"}, d2 = {"ALWAYS_ENABLED_ASSERT_FUNCTION_NAME", "", "ASSERTIONS_DISABLED_FIELD_NAME", "ASSERTION_ERROR_INTERNAL_NAME", "LAMBDA_INTERNAL_NAME", "THROWABLE_INTERNAL_NAME", "createMethodNodeForAlwaysEnabledAssert", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "functionDescriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "generateAssert", "", "assertionsMode", "Lorg/jetbrains/kotlin/config/JVMAssertionsMode;", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "codegen", "Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;", "parentCodegen", "Lorg/jetbrains/kotlin/codegen/MemberCodegen;", "generateAssertionsDisabledFieldInitialization", "classBuilder", "Lorg/jetbrains/kotlin/codegen/ClassBuilder;", "clInitBuilder", "Lorg/jetbrains/org/objectweb/asm/MethodVisitor;", "className", "generateJvmAssert", "inlineAlwaysInlineAssert", "isAssertCall", "", "rewriteAssertionsDisabledFieldInitialization", "methodNode", "isBuiltinAlwaysEnabledAssert", "isBuiltinAlwaysEnabledAssertWithLambda", "isBuiltinAlwaysEnabledAssertWithoutLambda", "isCheckAssertionsStatus", "Lorg/jetbrains/org/objectweb/asm/tree/FieldInsnNode;", "replaceAssertWithAssertInner", "D", Marker.BACKEND}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class AssertCodegenUtilKt {
    public static final String ASSERTIONS_DISABLED_FIELD_NAME = "$assertionsDisabled";

    /* JADX INFO: compiled from: AssertCodegenUtil.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[JVMAssertionsMode.values().length];
            try {
                iArr[JVMAssertionsMode.ALWAYS_ENABLE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[JVMAssertionsMode.ALWAYS_DISABLE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[JVMAssertionsMode.JVM.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static final boolean isAssertCall(ResolvedCall<?> resolvedCall) {
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
        Intrinsics.checkNotNullExpressionValue(resultingDescriptor, "getResultingDescriptor(...)");
        return DescriptorUtilKt.isTopLevelInPackage(resultingDescriptor, PsiKeyword.ASSERT, IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME);
    }

    private static final boolean isBuiltinAlwaysEnabledAssertWithLambda(FunctionDescriptor functionDescriptor) {
        return DescriptorUtilKt.isTopLevelInPackage(functionDescriptor, "alwaysEnabledAssert", IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME) && functionDescriptor.getValueParameters().size() == 2;
    }

    private static final boolean isBuiltinAlwaysEnabledAssertWithoutLambda(FunctionDescriptor functionDescriptor) {
        return DescriptorUtilKt.isTopLevelInPackage(functionDescriptor, "alwaysEnabledAssert", IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME) && functionDescriptor.getValueParameters().size() == 1;
    }

    public static final boolean isBuiltinAlwaysEnabledAssert(FunctionDescriptor functionDescriptor) {
        Intrinsics.checkNotNullParameter(functionDescriptor, "<this>");
        return isBuiltinAlwaysEnabledAssertWithLambda(functionDescriptor) || isBuiltinAlwaysEnabledAssertWithoutLambda(functionDescriptor);
    }

    public static final boolean isCheckAssertionsStatus(FieldInsnNode fieldInsnNode) {
        Intrinsics.checkNotNullParameter(fieldInsnNode, "<this>");
        return fieldInsnNode.getOpcode() == 178 && Intrinsics.areEqual(fieldInsnNode.f1010name, ASSERTIONS_DISABLED_FIELD_NAME) && Intrinsics.areEqual(fieldInsnNode.desc, Type.BOOLEAN_TYPE.getDescriptor());
    }

    public static final MethodNode createMethodNodeForAlwaysEnabledAssert(FunctionDescriptor functionDescriptor) {
        String methodDescriptor;
        Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
        if (isBuiltinAlwaysEnabledAssertWithLambda(functionDescriptor)) {
            methodDescriptor = Type.getMethodDescriptor(Type.VOID_TYPE, Type.BOOLEAN_TYPE, AsmTypes.FUNCTION0);
        } else if (isBuiltinAlwaysEnabledAssertWithoutLambda(functionDescriptor)) {
            methodDescriptor = Type.getMethodDescriptor(Type.VOID_TYPE, Type.BOOLEAN_TYPE);
        } else {
            throw new IllegalStateException(("functionDescriptor must be kotlin.alwaysEnabledAssert, but got " + functionDescriptor).toString());
        }
        MethodNode methodNode = new MethodNode(589824, 8, "fake", methodDescriptor, null, null);
        InstructionAdapter instructionAdapter = new InstructionAdapter(methodNode);
        Label label = new Label();
        instructionAdapter.load(0, Type.BOOLEAN_TYPE);
        instructionAdapter.ifne(label);
        if (isBuiltinAlwaysEnabledAssertWithLambda(functionDescriptor)) {
            instructionAdapter.load(1, Type.getObjectType("kotlin/jvm/functions/Function0"));
            instructionAdapter.invokeinterface("kotlin/jvm/functions/Function0", "invoke", "()Ljava/lang/Object;");
            instructionAdapter.store(2, AsmTypes.OBJECT_TYPE);
            instructionAdapter.anew(Type.getObjectType("java/lang/AssertionError"));
            instructionAdapter.dup();
            instructionAdapter.load(2, AsmTypes.OBJECT_TYPE);
            instructionAdapter.invokespecial("java/lang/AssertionError", "<init>", ReifiedTypeInliner.pluginIntrinsicsMarkerSignature, false);
        } else {
            instructionAdapter.anew(Type.getObjectType("java/lang/AssertionError"));
            instructionAdapter.dup();
            instructionAdapter.visitLdcInsn("Assertion failed");
            instructionAdapter.invokespecial("java/lang/AssertionError", "<init>", ReifiedTypeInliner.pluginIntrinsicsMarkerSignature, false);
        }
        instructionAdapter.checkcast(Type.getObjectType("java/lang/Throwable"));
        instructionAdapter.athrow();
        instructionAdapter.mark(label);
        instructionAdapter.areturn(Type.VOID_TYPE);
        methodNode.visitMaxs(3, 3);
        return methodNode;
    }

    public static final void generateAssert(JVMAssertionsMode assertionsMode, ResolvedCall<?> resolvedCall, ExpressionCodegen codegen, MemberCodegen<?> parentCodegen) {
        Intrinsics.checkNotNullParameter(assertionsMode, "assertionsMode");
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(parentCodegen, "parentCodegen");
        isAssertCall(resolvedCall);
        int i = WhenMappings.$EnumSwitchMapping$0[assertionsMode.ordinal()];
        if (i == 1) {
            inlineAlwaysInlineAssert(resolvedCall, codegen);
        } else if (i != 2) {
            if (i == 3) {
                generateJvmAssert(resolvedCall, codegen, parentCodegen);
                return;
            }
            throw new IllegalStateException("legacy assertions mode shall be handled in ExpressionCodegen".toString());
        }
    }

    private static final void generateJvmAssert(ResolvedCall<?> resolvedCall, ExpressionCodegen expressionCodegen, MemberCodegen<?> memberCodegen) {
        memberCodegen.generateAssertField();
        Label label = new Label();
        InstructionAdapter instructionAdapter = expressionCodegen.v;
        instructionAdapter.getstatic(memberCodegen.v.getThisName(), ASSERTIONS_DISABLED_FIELD_NAME, "Z");
        instructionAdapter.ifne(label);
        inlineAlwaysInlineAssert(resolvedCall, expressionCodegen);
        instructionAdapter.mark(label);
    }

    private static final void inlineAlwaysInlineAssert(ResolvedCall<?> resolvedCall, ExpressionCodegen expressionCodegen) {
        Intrinsics.checkNotNull(resolvedCall, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.calls.model.ResolvedCall<org.jetbrains.kotlin.descriptors.FunctionDescriptor>");
        ResolvedCall<?> resolvedCallReplaceAssertWithAssertInner = replaceAssertWithAssertInner(resolvedCall);
        KotlinTypeMapper typeMapper = expressionCodegen.typeMapper;
        Intrinsics.checkNotNullExpressionValue(typeMapper, "typeMapper");
        CallableDescriptor resultingDescriptor = resolvedCallReplaceAssertWithAssertInner.getResultingDescriptor();
        Intrinsics.checkNotNullExpressionValue(resultingDescriptor, "getResultingDescriptor(...)");
        expressionCodegen.invokeMethodWithArguments(KotlinTypeMapper.mapToCallableMethod$default(typeMapper, (FunctionDescriptor) resultingDescriptor, false, null, null, 12, null), resolvedCallReplaceAssertWithAssertInner, StackValue.none());
    }

    public static final void generateAssertionsDisabledFieldInitialization(ClassBuilder classBuilder, MethodVisitor clInitBuilder, String className) {
        Intrinsics.checkNotNullParameter(classBuilder, "classBuilder");
        Intrinsics.checkNotNullParameter(clInitBuilder, "clInitBuilder");
        Intrinsics.checkNotNullParameter(className, "className");
        classBuilder.newField(JvmDeclarationOrigin.NO_ORIGIN, 4120, ASSERTIONS_DISABLED_FIELD_NAME, "Z", null, null);
        Label label = new Label();
        Label label2 = new Label();
        InstructionAdapter instructionAdapter = new InstructionAdapter(clInitBuilder);
        instructionAdapter.mark(new Label());
        instructionAdapter.aconst(Type.getObjectType(className));
        instructionAdapter.invokevirtual(TypeProxy.SilentConstruction.Appender.JAVA_LANG_CLASS_INTERNAL_NAME, "desiredAssertionStatus", "()Z", false);
        instructionAdapter.ifne(label);
        instructionAdapter.iconst(1);
        instructionAdapter.goTo(label2);
        instructionAdapter.mark(label);
        instructionAdapter.iconst(0);
        instructionAdapter.mark(label2);
        instructionAdapter.putstatic(classBuilder.getThisName(), ASSERTIONS_DISABLED_FIELD_NAME, "Z");
    }

    /* JADX WARN: Code duplicated, block: B:31:0x0088  */
    public static final void rewriteAssertionsDisabledFieldInitialization(MethodNode methodNode, String className) {
        AbstractInsnNode next;
        AbstractInsnNode previous;
        Intrinsics.checkNotNullParameter(methodNode, "methodNode");
        Intrinsics.checkNotNullParameter(className, "className");
        InsnList instructions = methodNode.instructions;
        Intrinsics.checkNotNullExpressionValue(instructions, "instructions");
        Iterator<AbstractInsnNode> it = new InsnSequence(instructions).iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            AbstractInsnNode abstractInsnNode = next;
            if (abstractInsnNode instanceof FieldInsnNode) {
                FieldInsnNode fieldInsnNode = (FieldInsnNode) abstractInsnNode;
                if (fieldInsnNode.getOpcode() == 179 && Intrinsics.areEqual(fieldInsnNode.f1010name, ASSERTIONS_DISABLED_FIELD_NAME)) {
                    break;
                }
            }
        }
        AbstractInsnNode abstractInsnNode2 = next;
        if (abstractInsnNode2 != null) {
            AbstractInsnNode previous2 = abstractInsnNode2.getPrevious();
            while (previous2 != null) {
                if (previous2 instanceof MethodInsnNode) {
                    MethodInsnNode methodInsnNode = (MethodInsnNode) previous2;
                    if (methodInsnNode.getOpcode() == 182 && Intrinsics.areEqual(methodInsnNode.owner, TypeProxy.SilentConstruction.Appender.JAVA_LANG_CLASS_INTERNAL_NAME) && Intrinsics.areEqual(methodInsnNode.f1015name, "desiredAssertionStatus") && Intrinsics.areEqual(methodInsnNode.desc, "()Z")) {
                        break;
                    }
                }
                previous2 = previous2.getPrevious();
            }
            if (previous2 != null) {
                previous = previous2.getPrevious();
            } else {
                previous = null;
            }
        } else {
            previous = null;
        }
        LdcInsnNode ldcInsnNode = previous instanceof LdcInsnNode ? (LdcInsnNode) previous : null;
        if (ldcInsnNode == null) {
            return;
        }
        ldcInsnNode.cst = Type.getObjectType(className);
    }

    private static final <D extends FunctionDescriptor> ResolvedCall<D> replaceAssertWithAssertInner(ResolvedCall<D> resolvedCall) {
        FunctionDescriptor functionDescriptorCreateCustomCopy = CoroutineCodegenUtilKt.createCustomCopy((FunctionDescriptor) resolvedCall.getResultingDescriptor(), new Function2<FunctionDescriptor.CopyBuilder<? extends FunctionDescriptor>, FunctionDescriptor, FunctionDescriptor.CopyBuilder<? extends FunctionDescriptor>>() { // from class: org.jetbrains.kotlin.codegen.AssertCodegenUtilKt$replaceAssertWithAssertInner$newCandidateDescriptor$1
            @Override // kotlin.jvm.functions.Function2
            public final FunctionDescriptor.CopyBuilder<? extends FunctionDescriptor> invoke(FunctionDescriptor.CopyBuilder<? extends FunctionDescriptor> createCustomCopy, FunctionDescriptor it) {
                Intrinsics.checkNotNullParameter(createCustomCopy, "$this$createCustomCopy");
                Intrinsics.checkNotNullParameter(it, "it");
                FunctionDescriptor.CopyBuilder name2 = createCustomCopy.setName(Name.identifier("alwaysEnabledAssert"));
                Intrinsics.checkNotNullExpressionValue(name2, "setName(...)");
                return name2;
            }
        });
        Intrinsics.checkNotNullExpressionValue(functionDescriptorCreateCustomCopy, "createCustomCopy(...)");
        ReceiverValue receiverValueMo7304getDispatchReceiver = resolvedCall.getDispatchReceiver();
        ReceiverValue receiverValueMo7305getExtensionReceiver = resolvedCall.getExtensionReceiver();
        ExplicitReceiverKind explicitReceiverKind = resolvedCall.getExplicitReceiverKind();
        BindingContext context = new BindingTraceContext().getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
        ResolvedCallImpl resolvedCallImpl = new ResolvedCallImpl(resolvedCall.getCall(), functionDescriptorCreateCustomCopy, receiverValueMo7304getDispatchReceiver, receiverValueMo7305getExtensionReceiver, explicitReceiverKind, null, new DelegatingBindingTrace(context, "Temporary trace for assertInner", false, null, false, null, 60, null), TracingStrategy.EMPTY, new MutableDataFlowInfoForArguments.WithoutArgumentsCheck(DataFlowInfo.INSTANCE.getEMPTY()));
        Map<ValueParameterDescriptor, ResolvedValueArgument> valueArguments = resolvedCall.getValueArguments();
        Intrinsics.checkNotNullExpressionValue(valueArguments, "getValueArguments(...)");
        for (Map.Entry<ValueParameterDescriptor, ResolvedValueArgument> entry : valueArguments.entrySet()) {
            resolvedCallImpl.recordValueArgument(functionDescriptorCreateCustomCopy.getValueParameters().get(entry.getKey().getIndex()), entry.getValue());
        }
        return resolvedCallImpl;
    }
}
