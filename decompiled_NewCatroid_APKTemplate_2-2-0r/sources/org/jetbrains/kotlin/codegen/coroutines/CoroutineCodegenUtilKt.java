package org.jetbrains.kotlin.codegen.coroutines;

import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.backend.common.CommonCoroutineCodegenUtilKt;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.codegen.CodegenUtilKt;
import org.jetbrains.kotlin.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.codegen.StackValue;
import org.jetbrains.kotlin.codegen.SyntheticClassDescriptorForLambda;
import org.jetbrains.kotlin.codegen.binding.CodegenBinding;
import org.jetbrains.kotlin.codegen.inline.InlineCodegenUtilsKt;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.coroutines.CoroutineUtilKt;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorUtilKt;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.impl.AnonymousFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.impl.ValueParameterDescriptorImpl;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtPsiFactory;
import org.jetbrains.kotlin.psi.KtValueArgument;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTraceContext;
import org.jetbrains.kotlin.resolve.DelegatingBindingTrace;
import org.jetbrains.kotlin.resolve.FunctionImportedFromObject;
import org.jetbrains.kotlin.resolve.ImportedFromObjectKt;
import org.jetbrains.kotlin.resolve.InlineClassesUtilsKt;
import org.jetbrains.kotlin.resolve.calls.checkers.CoroutineCallCheckerKt;
import org.jetbrains.kotlin.resolve.calls.model.ExpressionValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.MutableDataFlowInfoForArguments;
import org.jetbrains.kotlin.resolve.calls.model.MutableResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCallImpl;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.VariableAsFunctionResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.VariableAsFunctionResolvedCallImpl;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;
import org.jetbrains.kotlin.resolve.calls.tasks.ExplicitReceiverKind;
import org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy;
import org.jetbrains.kotlin.resolve.calls.tower.NewResolvedCallImpl;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeFactory;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeAttributes;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeConstructorSubstitution;
import org.jetbrains.kotlin.types.error.ErrorTypeKind;
import org.jetbrains.kotlin.types.error.ErrorUtils;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;
import org.jetbrains.kotlin.util.OperatorNameConventions;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;
import org.jetbrains.org.objectweb.asm.commons.Method;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;

/* JADX INFO: compiled from: coroutineCodegenUtil.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000È\u0001\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a\u0006\u0010\u0016\u001a\u00020\u0001\u001a\u000e\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u0010\u001a\u0006\u0010\u001a\u001a\u00020\u0018\u001a%\u0010\u001b\u001a\u0002H\u001c\"\b\b\u0000\u0010\u001c*\u00020\u00102\u0006\u0010\u001d\u001a\u0002H\u001c2\u0006\u0010\u001e\u001a\u00020\u001f¢\u0006\u0002\u0010 \u001a+\u0010\u001b\u001a\u0002H\u001c\"\b\b\u0000\u0010\u001c*\u00020\u00102\u0006\u0010\u001d\u001a\u0002H\u001c2\n\b\u0002\u0010!\u001a\u0004\u0018\u00010\"H\u0007¢\u0006\u0002\u0010#\u001a \u0010$\u001a\b\u0012\u0004\u0012\u00020&0%*\b\u0012\u0004\u0012\u00020&0'2\u0006\u0010!\u001a\u00020\"H\u0002\u001a \u0010(\u001a\b\u0012\u0004\u0012\u00020&0)*\b\u0012\u0004\u0012\u00020&0*2\u0006\u0010!\u001a\u00020\"H\u0002\u001aL\u0010+\u001a\u0002H\u001c\"\b\b\u0000\u0010\u001c*\u00020\u0010*\u0002H\u001c21\u0010,\u001a-\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00100.\u0012\u0004\u0012\u00020\u0010\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00100.0-j\u0002`/¢\u0006\u0002\b0¢\u0006\u0002\u00101\u001a\n\u00102\u001a\u000203*\u000204\u001a\f\u00105\u001a\u000206*\u00020\u0010H\u0002\u001a\u0012\u00107\u001a\u000208*\u0002082\u0006\u00109\u001a\u00020\u0007\u001a\u0012\u0010:\u001a\u00020\u0010*\u00020\u00102\u0006\u0010!\u001a\u00020\"\u001a\u0012\u0010;\u001a\u000206*\u00020<2\u0006\u0010=\u001a\u00020>\u001a\u0014\u0010?\u001a\u00020\u0007*\u00020@2\u0006\u0010A\u001a\u00020\u0007H\u0002\u001a\f\u0010B\u001a\u000203*\u000204H\u0002\u001a\u0012\u0010C\u001a\u000203*\u0002042\u0006\u0010D\u001a\u00020\u0007\u001a\n\u0010E\u001a\u00020F*\u00020\u0010\u001a\n\u0010G\u001a\u00020F*\u00020\u0007\u001a\n\u0010H\u001a\u00020F*\u00020\u0010\u001a\n\u0010I\u001a\u00020F*\u00020\u0010\u001a\n\u0010J\u001a\u00020F*\u00020K\u001a\n\u0010L\u001a\u00020F*\u00020\u0010\u001a\u0016\u0010M\u001a\u00020N*\u0006\u0012\u0002\b\u00030*2\u0006\u0010O\u001a\u00020P\u001a\n\u0010Q\u001a\u000203*\u000204\u001a\u0014\u0010R\u001a\u0004\u0018\u00010>*\u00020\u00102\u0006\u0010S\u001a\u00020T\u001a \u0010U\u001a\u0004\u0018\u00010V*\u0006\u0012\u0002\b\u00030*2\u0006\u0010W\u001a\u00020X2\u0006\u0010!\u001a\u00020\"\u001a\u0018\u0010U\u001a\u0004\u0018\u00010V*\u0006\u0012\u0002\b\u00030*2\u0006\u0010\u001e\u001a\u00020\u001f\u001a\u001b\u0010Y\u001a\u0002H\u001c\"\n\b\u0000\u0010\u001c*\u0004\u0018\u00010K*\u0002H\u001c¢\u0006\u0002\u0010Z\"\u0010\u0010\u0000\u001a\u00020\u00018\u0006X\u0087\u0004¢\u0006\u0002\n\u0000\"\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0005\"\u000e\u0010\u0006\u001a\u00020\u0007X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\b\u001a\u00020\u0007X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\t\u001a\u00020\u0007X\u0086T¢\u0006\u0002\n\u0000\"\u0011\u0010\n\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u000e\u0010\r\u001a\u00020\u0007X\u0082T¢\u0006\u0002\n\u0000\"\u0016\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000\"\u0016\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0012\u001a\u00020\u0007X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0013\u001a\u00020\u0007X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0014\u001a\u00020\u0007X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0015\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000*X\u0010[\")\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00100.\u0012\u0004\u0012\u00020\u0010\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00100.0-¢\u0006\u0002\b02)\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00100.\u0012\u0004\u0012\u00020\u0010\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00100.0-¢\u0006\u0002\b0¨\u0006\\"}, d2 = {"CONTINUATION_ASM_TYPE", "Lorg/jetbrains/org/objectweb/asm/Type;", "CONTINUATION_PARAMETER_NAME", "Lorg/jetbrains/kotlin/name/Name;", "getCONTINUATION_PARAMETER_NAME", "()Lorg/jetbrains/kotlin/name/Name;", "CONTINUATION_RESULT_FIELD_NAME", "", "CONTINUATION_VARIABLE_NAME", "COROUTINE_LABEL_FIELD_NAME", "DEBUG_METADATA_ANNOTATION_ASM_TYPE", "getDEBUG_METADATA_ANNOTATION_ASM_TYPE", "()Lorg/jetbrains/org/objectweb/asm/Type;", "GET_CONTEXT_METHOD_NAME", "INITIAL_DESCRIPTOR_FOR_SUSPEND_FUNCTION", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor$UserDataKey;", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "INITIAL_SUSPEND_DESCRIPTOR_FOR_INVOKE_SUSPEND", "INVOKE_SUSPEND_METHOD_NAME", "SUSPEND_FUNCTION_CREATE_METHOD_NAME", "SUSPEND_IMPL_NAME_SUFFIX", "coroutinesIntrinsicsFileFacadeInternalName", "coroutineContextAsmType", "createMethodNodeForCoroutineContext", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "functionDescriptor", "createMethodNodeForSuspendCoroutineUninterceptedOrReturn", "getOrCreateJvmSuspendFunctionView", "D", "function", "state", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "(Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;Lorg/jetbrains/kotlin/codegen/state/GenerationState;)Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "(Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;Lorg/jetbrains/kotlin/resolve/BindingContext;)Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "asDummyOldResolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCallImpl;", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "Lorg/jetbrains/kotlin/resolve/calls/tower/NewResolvedCallImpl;", "asMutableResolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/MutableResolvedCall;", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "createCustomCopy", "copySettings", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor$CopyBuilder;", "Lorg/jetbrains/kotlin/codegen/coroutines/FunctionDescriptorCopyBuilderToFunctionDescriptorCopyBuilder;", "Lkotlin/ExtensionFunctionType;", "(Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;Lkotlin/jvm/functions/Function2;)Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "generateCoroutineSuspendedCheck", "", "Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", "getContinuationParameterTypeOfSuspendFunction", "Lorg/jetbrains/kotlin/types/SimpleType;", "getImplForOpenMethod", "Lorg/jetbrains/org/objectweb/asm/commons/Method;", "ownerInternalName", "getOriginalSuspendFunctionView", "getResult", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "kotlinType", "Lorg/jetbrains/kotlin/types/KotlinType;", "identifiedChild", "Lorg/jetbrains/kotlin/name/FqName;", "name", "invokeGetContext", "invokeInvokeSuspendWithUnit", "thisName", "isBuiltInSuspendCoroutineUninterceptedOrReturnInJvm", "", "isCoroutineSuperClass", "isInvokeSuspendOfLambda", "isLocalSuspendFunctionNotSuspendLambda", "isSuspendFunctionNotSuspensionView", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "isSuspendLambdaOrLocalFunction", "isSuspensionPoint", "Lorg/jetbrains/kotlin/codegen/coroutines/SuspensionPointKind;", "codegen", "Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;", "loadCoroutineSuspendedMarker", "originalReturnTypeOfSuspendFunctionReturningUnboxedInlineClass", "typeMapper", "Lorg/jetbrains/kotlin/codegen/state/KotlinTypeMapper;", "replaceSuspensionFunctionWithRealDescriptor", "Lorg/jetbrains/kotlin/codegen/coroutines/ResolvedCallWithRealDescriptor;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "unwrapInitialDescriptorForSuspendFunction", "(Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;)Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "FunctionDescriptorCopyBuilderToFunctionDescriptorCopyBuilder", Marker.BACKEND}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CoroutineCodegenUtilKt {
    public static final Type CONTINUATION_ASM_TYPE;
    private static final Name CONTINUATION_PARAMETER_NAME;
    public static final String CONTINUATION_RESULT_FIELD_NAME = "result";
    public static final String CONTINUATION_VARIABLE_NAME = "$continuation";
    public static final String COROUTINE_LABEL_FIELD_NAME = "label";
    private static final Type DEBUG_METADATA_ANNOTATION_ASM_TYPE;
    public static final CallableDescriptor.UserDataKey<FunctionDescriptor> INITIAL_DESCRIPTOR_FOR_SUSPEND_FUNCTION;
    public static final CallableDescriptor.UserDataKey<FunctionDescriptor> INITIAL_SUSPEND_DESCRIPTOR_FOR_INVOKE_SUSPEND;
    public static final String INVOKE_SUSPEND_METHOD_NAME = "invokeSuspend";
    public static final String SUSPEND_FUNCTION_CREATE_METHOD_NAME = "create";
    public static final String SUSPEND_IMPL_NAME_SUFFIX = "$suspendImpl";
    private static final Type coroutinesIntrinsicsFileFacadeInternalName;

    public static final <D extends FunctionDescriptor> D getOrCreateJvmSuspendFunctionView(D function) {
        Intrinsics.checkNotNullParameter(function, "function");
        return (D) getOrCreateJvmSuspendFunctionView$default(function, null, 2, null);
    }

    public static final Type getDEBUG_METADATA_ANNOTATION_ASM_TYPE() {
        return DEBUG_METADATA_ANNOTATION_ASM_TYPE;
    }

    static {
        FqName fqNameChild = StandardNames.COROUTINES_JVM_INTERNAL_PACKAGE_FQ_NAME.child(Name.identifier("DebugMetadata"));
        Intrinsics.checkNotNullExpressionValue(fqNameChild, "child(...)");
        DEBUG_METADATA_ANNOTATION_ASM_TYPE = CodegenUtilKt.topLevelClassAsmType(fqNameChild);
        FqName fqNameChild2 = StandardNames.COROUTINES_INTRINSICS_PACKAGE_FQ_NAME.child(Name.identifier("IntrinsicsKt"));
        Intrinsics.checkNotNullExpressionValue(fqNameChild2, "child(...)");
        coroutinesIntrinsicsFileFacadeInternalName = CodegenUtilKt.topLevelClassAsmType(fqNameChild2);
        INITIAL_DESCRIPTOR_FOR_SUSPEND_FUNCTION = new CallableDescriptor.UserDataKey<FunctionDescriptor>() { // from class: org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt$INITIAL_DESCRIPTOR_FOR_SUSPEND_FUNCTION$1
        };
        INITIAL_SUSPEND_DESCRIPTOR_FOR_INVOKE_SUSPEND = new CallableDescriptor.UserDataKey<FunctionDescriptor>() { // from class: org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt$INITIAL_SUSPEND_DESCRIPTOR_FOR_INVOKE_SUSPEND$1
        };
        Name nameIdentifier = Name.identifier("continuation");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        CONTINUATION_PARAMETER_NAME = nameIdentifier;
        CONTINUATION_ASM_TYPE = CodegenUtilKt.topLevelClassAsmType(StandardNames.CONTINUATION_INTERFACE_FQ_NAME);
    }

    public static final Type coroutineContextAsmType() {
        FqName fqNameChild = StandardNames.COROUTINES_PACKAGE_FQ_NAME.child(Name.identifier("CoroutineContext"));
        Intrinsics.checkNotNullExpressionValue(fqNameChild, "child(...)");
        return CodegenUtilKt.topLevelClassAsmType(fqNameChild);
    }

    public static final boolean isCoroutineSuperClass(String str) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        return Intrinsics.areEqual(identifiedChild(StandardNames.COROUTINES_JVM_INTERNAL_PACKAGE_FQ_NAME, "ContinuationImpl"), str) || Intrinsics.areEqual(identifiedChild(StandardNames.COROUTINES_JVM_INTERNAL_PACKAGE_FQ_NAME, "RestrictedContinuationImpl"), str) || Intrinsics.areEqual(identifiedChild(StandardNames.COROUTINES_JVM_INTERNAL_PACKAGE_FQ_NAME, "SuspendLambda"), str) || Intrinsics.areEqual(identifiedChild(StandardNames.COROUTINES_JVM_INTERNAL_PACKAGE_FQ_NAME, "RestrictedSuspendLambda"), str);
    }

    private static final String identifiedChild(FqName fqName, String str) {
        FqName fqNameChild = fqName.child(Name.identifier(str));
        Intrinsics.checkNotNullExpressionValue(fqNameChild, "child(...)");
        return CodegenUtilKt.topLevelClassInternalName(fqNameChild);
    }

    public static final Name getCONTINUATION_PARAMETER_NAME() {
        return CONTINUATION_PARAMETER_NAME;
    }

    public static final ResolvedCallWithRealDescriptor replaceSuspensionFunctionWithRealDescriptor(ResolvedCall<?> resolvedCall, Project project, BindingContext bindingContext) {
        FunctionImportedFromObject orCreateJvmSuspendFunctionView;
        Intrinsics.checkNotNullParameter(resolvedCall, "<this>");
        Intrinsics.checkNotNullParameter(project, "project");
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        if (resolvedCall instanceof VariableAsFunctionResolvedCall) {
            VariableAsFunctionResolvedCall variableAsFunctionResolvedCall = (VariableAsFunctionResolvedCall) resolvedCall;
            ResolvedCallWithRealDescriptor resolvedCallWithRealDescriptorReplaceSuspensionFunctionWithRealDescriptor = replaceSuspensionFunctionWithRealDescriptor(variableAsFunctionResolvedCall.getFunctionCall(), project, bindingContext);
            if (resolvedCallWithRealDescriptorReplaceSuspensionFunctionWithRealDescriptor == null) {
                return null;
            }
            ResolvedCall<?> resolvedCall2 = resolvedCallWithRealDescriptorReplaceSuspensionFunctionWithRealDescriptor.getResolvedCall();
            Intrinsics.checkNotNull(resolvedCall2, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.calls.model.MutableResolvedCall<org.jetbrains.kotlin.descriptors.FunctionDescriptor>");
            return ResolvedCallWithRealDescriptor.copy$default(resolvedCallWithRealDescriptorReplaceSuspensionFunctionWithRealDescriptor, new VariableAsFunctionResolvedCallImpl((MutableResolvedCall) resolvedCall2, asMutableResolvedCall(variableAsFunctionResolvedCall.getVariableCall(), bindingContext)), null, 2, null);
        }
        CallableDescriptor candidateDescriptor = resolvedCall.get$suspendPropertyDescriptor();
        FunctionDescriptor functionDescriptor = candidateDescriptor instanceof FunctionDescriptor ? (FunctionDescriptor) candidateDescriptor : null;
        if (functionDescriptor == null || !functionDescriptor.isSuspend() || functionDescriptor.getUserData(INITIAL_DESCRIPTOR_FOR_SUSPEND_FUNCTION) != null) {
            return null;
        }
        if (functionDescriptor instanceof FunctionImportedFromObject) {
            orCreateJvmSuspendFunctionView = ImportedFromObjectKt.asImportedFromObject$default(getOrCreateJvmSuspendFunctionView(((FunctionImportedFromObject) functionDescriptor).getCallableFromObject(), bindingContext), (FunctionImportedFromObject) null, 1, (Object) null);
        } else if (functionDescriptor instanceof SimpleFunctionDescriptor) {
            orCreateJvmSuspendFunctionView = getOrCreateJvmSuspendFunctionView(functionDescriptor, bindingContext);
        } else {
            throw new AssertionError("Unexpected suspend function descriptor: " + functionDescriptor);
        }
        ReceiverValue receiverValueMo7304getDispatchReceiver = resolvedCall.getDispatchReceiver();
        ReceiverValue receiverValueMo7305getExtensionReceiver = resolvedCall.getExtensionReceiver();
        ExplicitReceiverKind explicitReceiverKind = resolvedCall.getExplicitReceiverKind();
        BindingContext bindingContext2 = new BindingTraceContext().getContext();
        Intrinsics.checkNotNullExpressionValue(bindingContext2, "getBindingContext(...)");
        ResolvedCallImpl resolvedCallImpl = new ResolvedCallImpl(resolvedCall.getCall(), orCreateJvmSuspendFunctionView, receiverValueMo7304getDispatchReceiver, receiverValueMo7305getExtensionReceiver, explicitReceiverKind, null, new DelegatingBindingTrace(bindingContext2, "Temporary trace for unwrapped suspension function", false, null, false, null, 60, null), TracingStrategy.EMPTY, new MutableDataFlowInfoForArguments.WithoutArgumentsCheck(DataFlowInfo.INSTANCE.getEMPTY()));
        Map<ValueParameterDescriptor, ResolvedValueArgument> valueArguments = resolvedCall.getValueArguments();
        Intrinsics.checkNotNullExpressionValue(valueArguments, "getValueArguments(...)");
        for (Map.Entry<ValueParameterDescriptor, ResolvedValueArgument> entry : valueArguments.entrySet()) {
            resolvedCallImpl.recordValueArgument(orCreateJvmSuspendFunctionView.getValueParameters().get(entry.getKey().getIndex()), entry.getValue());
        }
        List<KtValueArgument> arguments = new KtPsiFactory(project, false).createCallArguments("(this)").getArguments();
        Intrinsics.checkNotNullExpressionValue(arguments, "getArguments(...)");
        KtValueArgument ktValueArgument = (KtValueArgument) CollectionsKt.single((List) arguments);
        KtExpression argumentExpression = ktValueArgument.getArgumentExpression();
        Intrinsics.checkNotNull(argumentExpression);
        List<ValueParameterDescriptor> valueParameters = orCreateJvmSuspendFunctionView.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        resolvedCallImpl.recordValueArgument((ValueParameterDescriptor) CollectionsKt.last((List) valueParameters), new ExpressionValueArgument(ktValueArgument));
        List<TypeParameterDescriptor> typeParameters = orCreateJvmSuspendFunctionView.getTypeParameters();
        Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
        List<TypeParameterDescriptor> list = typeParameters;
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(list, 10)), 16));
        for (Object obj : list) {
            KotlinType kotlinType = resolvedCall.getTypeArguments().get(resolvedCall.get$suspendPropertyDescriptor().getTypeParameters().get(((TypeParameterDescriptor) obj).getIndex()));
            Intrinsics.checkNotNull(kotlinType);
            linkedHashMap.put(obj, TypeUtilsKt.asTypeProjection(kotlinType));
        }
        resolvedCallImpl.setSubstitutor(TypeConstructorSubstitution.INSTANCE.createByParametersMap(linkedHashMap).buildSubstitutor());
        return new ResolvedCallWithRealDescriptor(resolvedCallImpl, argumentExpression);
    }

    public static final ResolvedCallWithRealDescriptor replaceSuspensionFunctionWithRealDescriptor(ResolvedCall<?> resolvedCall, GenerationState state) {
        Intrinsics.checkNotNullParameter(resolvedCall, "<this>");
        Intrinsics.checkNotNullParameter(state, "state");
        return replaceSuspensionFunctionWithRealDescriptor(resolvedCall, state.getProject(), state.getBindingContext());
    }

    private static final MutableResolvedCall<VariableDescriptor> asMutableResolvedCall(ResolvedCall<VariableDescriptor> resolvedCall, BindingContext bindingContext) {
        if (resolvedCall instanceof ResolvedCallImpl) {
            Intrinsics.checkNotNull(resolvedCall, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.calls.model.MutableResolvedCall<org.jetbrains.kotlin.descriptors.VariableDescriptor>");
            return (MutableResolvedCall) resolvedCall;
        }
        if (!(resolvedCall instanceof NewResolvedCallImpl)) {
            throw new IllegalStateException("No mutable resolved call for " + resolvedCall);
        }
        Intrinsics.checkNotNull(resolvedCall, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.calls.tower.NewResolvedCallImpl<org.jetbrains.kotlin.descriptors.VariableDescriptor>");
        return asDummyOldResolvedCall((NewResolvedCallImpl) resolvedCall, bindingContext);
    }

    private static final ResolvedCallImpl<VariableDescriptor> asDummyOldResolvedCall(NewResolvedCallImpl<VariableDescriptor> newResolvedCallImpl, BindingContext bindingContext) {
        return new ResolvedCallImpl<>(newResolvedCallImpl.getCall(), newResolvedCallImpl.get$suspendPropertyDescriptor(), newResolvedCallImpl.getDispatchReceiver(), newResolvedCallImpl.getExtensionReceiver(), newResolvedCallImpl.getExplicitReceiverKind(), null, new DelegatingBindingTrace(bindingContext, "Trace for old call", false, null, false, null, 60, null), TracingStrategy.EMPTY, new MutableDataFlowInfoForArguments.WithoutArgumentsCheck(DataFlowInfo.INSTANCE.getEMPTY()));
    }

    public static final SuspensionPointKind isSuspensionPoint(ResolvedCall<?> resolvedCall, ExpressionCodegen codegen) {
        ResolvedCall<VariableDescriptor> variableCall;
        Intrinsics.checkNotNullParameter(resolvedCall, "<this>");
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
        FunctionDescriptor functionDescriptor = resultingDescriptor instanceof FunctionDescriptor ? (FunctionDescriptor) resultingDescriptor : null;
        if (functionDescriptor != null && ((FunctionDescriptor) unwrapInitialDescriptorForSuspendFunction(functionDescriptor)).isSuspend()) {
            if (isBuiltInSuspendCoroutineUninterceptedOrReturnInJvm(functionDescriptor)) {
                return SuspensionPointKind.ALWAYS;
            }
            if (functionDescriptor.isInline()) {
                return SuspensionPointKind.NEVER;
            }
            VariableAsFunctionResolvedCall variableAsFunctionResolvedCall = resolvedCall instanceof VariableAsFunctionResolvedCall ? (VariableAsFunctionResolvedCall) resolvedCall : null;
            VariableDescriptor variableDescriptor = (variableAsFunctionResolvedCall == null || (variableCall = variableAsFunctionResolvedCall.getVariableCall()) == null) ? null : (VariableDescriptor) variableCall.getResultingDescriptor();
            ValueParameterDescriptor valueParameterDescriptor = variableDescriptor instanceof ValueParameterDescriptor ? (ValueParameterDescriptor) variableDescriptor : null;
            return (valueParameterDescriptor == null || (!valueParameterDescriptor.getIsCrossinline() && (valueParameterDescriptor.getIsNoinline() || !codegen.context.getFunctionDescriptor().isInline()))) ? SuspensionPointKind.ALWAYS : SuspensionPointKind.NOT_INLINE;
        }
        return SuspensionPointKind.NEVER;
    }

    public static final boolean isSuspendFunctionNotSuspensionView(CallableDescriptor callableDescriptor) {
        Intrinsics.checkNotNullParameter(callableDescriptor, "<this>");
        return (callableDescriptor instanceof FunctionDescriptor) && ((FunctionDescriptor) callableDescriptor).isSuspend() && callableDescriptor.getUserData(INITIAL_DESCRIPTOR_FOR_SUSPEND_FUNCTION) == null;
    }

    public static final <D extends FunctionDescriptor> D getOrCreateJvmSuspendFunctionView(D function, GenerationState state) {
        Intrinsics.checkNotNullParameter(function, "function");
        Intrinsics.checkNotNullParameter(state, "state");
        return (D) getOrCreateJvmSuspendFunctionView(function, state.getBindingContext());
    }

    public static /* synthetic */ FunctionDescriptor getOrCreateJvmSuspendFunctionView$default(FunctionDescriptor functionDescriptor, BindingContext bindingContext, int i, Object obj) {
        if ((i & 2) != 0) {
            bindingContext = null;
        }
        return getOrCreateJvmSuspendFunctionView(functionDescriptor, bindingContext);
    }

    public static final <D extends FunctionDescriptor> D getOrCreateJvmSuspendFunctionView(final D function, BindingContext bindingContext) {
        SimpleType continuationParameterTypeOfSuspendFunction;
        D d;
        Intrinsics.checkNotNullParameter(function, "function");
        function.isSuspend();
        if (bindingContext != null && (d = (D) bindingContext.get(CodegenBinding.SUSPEND_FUNCTION_TO_JVM_VIEW, function)) != null) {
            return d;
        }
        D d2 = function;
        int size = function.getValueParameters().size();
        Annotations empty = Annotations.INSTANCE.getEMPTY();
        Name name2 = CONTINUATION_PARAMETER_NAME;
        DeclarationDescriptor containingDeclaration = function.getContainingDeclaration();
        ClassDescriptor classDescriptor = containingDeclaration instanceof ClassDescriptor ? (ClassDescriptor) containingDeclaration : null;
        if (classDescriptor != null && FunctionTypesKt.isBuiltinFunctionalClassDescriptor(classDescriptor)) {
            continuationParameterTypeOfSuspendFunction = DescriptorUtilsKt.getBuiltIns(function).getNullableAnyType();
        } else {
            continuationParameterTypeOfSuspendFunction = getContinuationParameterTypeOfSuspendFunction(function);
        }
        Intrinsics.checkNotNull(continuationParameterTypeOfSuspendFunction);
        SourceElement NO_SOURCE = SourceElement.NO_SOURCE;
        Intrinsics.checkNotNullExpressionValue(NO_SOURCE, "NO_SOURCE");
        final ValueParameterDescriptorImpl valueParameterDescriptorImpl = new ValueParameterDescriptorImpl(d2, null, size, empty, name2, continuationParameterTypeOfSuspendFunction, false, false, false, null, NO_SOURCE);
        return (D) createCustomCopy(function, new Function2<FunctionDescriptor.CopyBuilder<? extends FunctionDescriptor>, FunctionDescriptor, FunctionDescriptor.CopyBuilder<? extends FunctionDescriptor>>() { // from class: org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt.getOrCreateJvmSuspendFunctionView.3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Incorrect types in method signature: (TD;Lorg/jetbrains/kotlin/descriptors/impl/ValueParameterDescriptorImpl;)V */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final FunctionDescriptor.CopyBuilder<? extends FunctionDescriptor> invoke(FunctionDescriptor.CopyBuilder<? extends FunctionDescriptor> createCustomCopy, FunctionDescriptor it) {
                Intrinsics.checkNotNullParameter(createCustomCopy, "$this$createCustomCopy");
                Intrinsics.checkNotNullParameter(it, "it");
                createCustomCopy.setDropOriginalInContainingParts();
                createCustomCopy.setPreserveSourceElement();
                createCustomCopy.setReturnType((KotlinType) DescriptorUtilsKt.getBuiltIns(function).getNullableAnyType());
                List<ValueParameterDescriptor> valueParameters = it.getValueParameters();
                Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
                createCustomCopy.setValueParameters(CollectionsKt.plus((Collection<? extends ValueParameterDescriptorImpl>) valueParameters, valueParameterDescriptorImpl));
                FunctionDescriptor.CopyBuilder copyBuilderPutUserData = createCustomCopy.putUserData(CoroutineCodegenUtilKt.INITIAL_DESCRIPTOR_FOR_SUSPEND_FUNCTION, it);
                Intrinsics.checkNotNullExpressionValue(copyBuilderPutUserData, "putUserData(...)");
                return copyBuilderPutUserData;
            }
        });
    }

    public static final <D extends FunctionDescriptor> D createCustomCopy(D d, Function2<? super FunctionDescriptor.CopyBuilder<? extends FunctionDescriptor>, ? super FunctionDescriptor, ? extends FunctionDescriptor.CopyBuilder<? extends FunctionDescriptor>> copySettings) {
        Intrinsics.checkNotNullParameter(d, "<this>");
        Intrinsics.checkNotNullParameter(copySettings, "copySettings");
        FunctionDescriptor functionDescriptorCreateCustomCopy = d.getOriginal() != d ? createCustomCopy(d.getOriginal(), copySettings) : null;
        FunctionDescriptor.CopyBuilder<? extends FunctionDescriptor> copyBuilderNewCopyBuilder = d.newCopyBuilder();
        Intrinsics.checkNotNullExpressionValue(copyBuilderNewCopyBuilder, "newCopyBuilder(...)");
        D d2 = (D) copySettings.invoke(copyBuilderNewCopyBuilder, d).setOriginal((CallableMemberDescriptor) functionDescriptorCreateCustomCopy).build();
        Intrinsics.checkNotNull(d2);
        Collection<? extends FunctionDescriptor> overriddenDescriptors = d.getOverriddenDescriptors();
        Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
        Collection<? extends FunctionDescriptor> collection = overriddenDescriptors;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            arrayList.add(createCustomCopy((FunctionDescriptor) it.next(), copySettings));
        }
        d2.mo7205setOverriddenDescriptors(arrayList);
        return d2;
    }

    private static final SimpleType getContinuationParameterTypeOfSuspendFunction(FunctionDescriptor functionDescriptor) {
        ModuleDescriptor module = DescriptorUtilsKt.getModule(functionDescriptor);
        KotlinType returnType = functionDescriptor.getReturnType();
        Intrinsics.checkNotNull(returnType);
        return DescriptorUtilKt.getContinuationOfTypeOrAny(module, returnType);
    }

    public static final SimpleType getResult(ModuleDescriptor moduleDescriptor, KotlinType kotlinType) {
        SimpleType defaultType;
        SimpleType simpleTypeSimpleType$default;
        Intrinsics.checkNotNullParameter(moduleDescriptor, "<this>");
        Intrinsics.checkNotNullParameter(kotlinType, "kotlinType");
        ClassDescriptor classDescriptorResolveTopLevelClass = DescriptorUtilsKt.resolveTopLevelClass(DescriptorUtilsKt.getModule(moduleDescriptor), StandardNames.RESULT_FQ_NAME, NoLookupLocation.FROM_BACKEND);
        return (classDescriptorResolveTopLevelClass == null || (defaultType = classDescriptorResolveTopLevelClass.getDefaultType()) == null || (simpleTypeSimpleType$default = KotlinTypeFactory.simpleType$default(defaultType, (TypeAttributes) null, (TypeConstructor) null, CollectionsKt.listOf(TypeUtilsKt.asTypeProjection(kotlinType)), false, 22, (Object) null)) == null) ? ErrorUtils.createErrorType(ErrorTypeKind.TYPE_FOR_RESULT, new String[0]) : simpleTypeSimpleType$default;
    }

    public static final boolean isBuiltInSuspendCoroutineUninterceptedOrReturnInJvm(FunctionDescriptor functionDescriptor) {
        Intrinsics.checkNotNullParameter(functionDescriptor, "<this>");
        FunctionDescriptor functionDescriptor2 = (FunctionDescriptor) functionDescriptor.getUserData(INITIAL_DESCRIPTOR_FOR_SUSPEND_FUNCTION);
        return functionDescriptor2 != null && CommonCoroutineCodegenUtilKt.isBuiltInSuspendCoroutineUninterceptedOrReturn(functionDescriptor2);
    }

    public static final MethodNode createMethodNodeForCoroutineContext(FunctionDescriptor functionDescriptor) {
        Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
        CoroutineCallCheckerKt.isBuiltInCoroutineContext(functionDescriptor);
        MethodNode methodNode = new MethodNode(589824, 8, "fake", Type.getMethodDescriptor(coroutineContextAsmType(), new Type[0]), null, null);
        InstructionAdapter instructionAdapter = new InstructionAdapter(methodNode);
        InlineCodegenUtilsKt.addFakeContinuationMarker(instructionAdapter);
        invokeGetContext(instructionAdapter);
        methodNode.visitMaxs(1, 1);
        return methodNode;
    }

    public static final MethodNode createMethodNodeForSuspendCoroutineUninterceptedOrReturn() {
        Type type = AsmTypes.OBJECT_TYPE;
        Type type2 = CONTINUATION_ASM_TYPE;
        MethodNode methodNode = new MethodNode(589824, 8, "fake", Type.getMethodDescriptor(type, AsmTypes.FUNCTION1, type2), null, null);
        InstructionAdapter instructionAdapter = new InstructionAdapter(methodNode);
        instructionAdapter.load(0, AsmTypes.OBJECT_TYPE);
        instructionAdapter.load(1, AsmTypes.OBJECT_TYPE);
        instructionAdapter.invokeinterface(AsmTypes.FUNCTION1.getInternalName(), OperatorNameConventions.INVOKE.getIdentifier(), "(" + AsmTypes.OBJECT_TYPE + ')' + AsmTypes.OBJECT_TYPE);
        Label label = new Label();
        instructionAdapter.dup();
        loadCoroutineSuspendedMarker(instructionAdapter);
        instructionAdapter.ifacmpne(label);
        instructionAdapter.load(1, AsmTypes.OBJECT_TYPE);
        instructionAdapter.checkcast(type2);
        FqName fqNameChild = StandardNames.COROUTINES_JVM_INTERNAL_PACKAGE_FQ_NAME.child(Name.identifier("DebugProbesKt"));
        Intrinsics.checkNotNullExpressionValue(fqNameChild, "child(...)");
        instructionAdapter.invokestatic(CodegenUtilKt.topLevelClassAsmType(fqNameChild).getInternalName(), "probeCoroutineSuspended", "(" + type2 + ")V", false);
        instructionAdapter.mark(label);
        methodNode.visitInsn(176);
        methodNode.visitMaxs(3, 2);
        return methodNode;
    }

    private static final void invokeGetContext(InstructionAdapter instructionAdapter) {
        instructionAdapter.invokeinterface(CONTINUATION_ASM_TYPE.getInternalName(), "getContext", Type.getMethodDescriptor(coroutineContextAsmType(), new Type[0]));
        instructionAdapter.areturn(coroutineContextAsmType());
    }

    public static final <D extends CallableDescriptor> D unwrapInitialDescriptorForSuspendFunction(D d) {
        SimpleFunctionDescriptor simpleFunctionDescriptor = d instanceof SimpleFunctionDescriptor ? (SimpleFunctionDescriptor) d : null;
        FunctionDescriptor functionDescriptor = simpleFunctionDescriptor != null ? (FunctionDescriptor) simpleFunctionDescriptor.getUserData(INITIAL_DESCRIPTOR_FOR_SUSPEND_FUNCTION) : null;
        return functionDescriptor == null ? d : functionDescriptor;
    }

    public static final FunctionDescriptor getOriginalSuspendFunctionView(FunctionDescriptor functionDescriptor, BindingContext bindingContext) {
        Intrinsics.checkNotNullParameter(functionDescriptor, "<this>");
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        if (!functionDescriptor.isSuspend()) {
            return functionDescriptor;
        }
        FunctionDescriptor orCreateJvmSuspendFunctionView = getOrCreateJvmSuspendFunctionView(((FunctionDescriptor) unwrapInitialDescriptorForSuspendFunction(functionDescriptor)).getOriginal(), bindingContext);
        Intrinsics.checkNotNullExpressionValue(orCreateJvmSuspendFunctionView, "getOrCreateJvmSuspendFunctionView(...)");
        return orCreateJvmSuspendFunctionView;
    }

    /* JADX WARN: Code duplicated, block: B:37:0x00a1  */
    /* JADX WARN: Code duplicated, block: B:42:0x00b6  */
    /* JADX WARN: Code duplicated, block: B:48:0x00c5 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:50:? A[LOOP:0: B:26:0x0070->B:50:?, LOOP_END, SYNTHETIC] */
    public static final KotlinType originalReturnTypeOfSuspendFunctionReturningUnboxedInlineClass(FunctionDescriptor functionDescriptor, KotlinTypeMapper typeMapper) {
        KotlinType returnType;
        ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor;
        TypeConstructor constructor;
        KotlinType returnType2;
        Intrinsics.checkNotNullParameter(functionDescriptor, "<this>");
        Intrinsics.checkNotNullParameter(typeMapper, "typeMapper");
        if (!functionDescriptor.isSuspend() || (functionDescriptor instanceof AnonymousFunctionDescriptor)) {
            return null;
        }
        FunctionDescriptor original = ((FunctionDescriptor) unwrapInitialDescriptorForSuspendFunction(functionDescriptor)).getOriginal();
        Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
        KotlinType returnType3 = original.getReturnType();
        if (returnType3 == null || !InlineClassesUtilsKt.isInlineClassType(returnType3) || AsmUtil.isPrimitive(KotlinTypeMapper.mapType$default(typeMapper, TypeUtilsKt.makeNotNullable(returnType3), null, null, 6, null))) {
            return null;
        }
        if (returnType3.getIsMarkedNullable() && InlineClassesUtilsKt.isNullableUnderlyingType(returnType3)) {
            return null;
        }
        Collection<? extends FunctionDescriptor> overriddenDescriptors = original.getOverriddenDescriptors();
        Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
        Collection<? extends FunctionDescriptor> collection = overriddenDescriptors;
        if (!collection.isEmpty()) {
            for (FunctionDescriptor functionDescriptor2 : collection) {
                KotlinType returnType4 = functionDescriptor2.getOriginal().getReturnType();
                if (returnType4 == null || !returnType4.getIsMarkedNullable() || (returnType2 = functionDescriptor2.getOriginal().getReturnType()) == null) {
                    returnType = functionDescriptor2.getOriginal().getReturnType();
                    if (returnType != null || (constructor = returnType.getConstructor()) == null) {
                        classifierDescriptorMo7434getDeclarationDescriptor = null;
                    } else {
                        classifierDescriptorMo7434getDeclarationDescriptor = constructor.getThis$0();
                    }
                    if (!Intrinsics.areEqual(classifierDescriptorMo7434getDeclarationDescriptor, returnType3.getConstructor().getThis$0())) {
                    }
                } else {
                    Intrinsics.checkNotNull(returnType2);
                    if (!InlineClassesUtilsKt.isNullableUnderlyingType(returnType2)) {
                        returnType = functionDescriptor2.getOriginal().getReturnType();
                        if (returnType != null) {
                            classifierDescriptorMo7434getDeclarationDescriptor = null;
                        } else {
                            classifierDescriptorMo7434getDeclarationDescriptor = null;
                        }
                        if (!Intrinsics.areEqual(classifierDescriptorMo7434getDeclarationDescriptor, returnType3.getConstructor().getThis$0())) {
                        }
                    }
                }
                return null;
            }
        }
        return returnType3;
    }

    public static final void loadCoroutineSuspendedMarker(InstructionAdapter instructionAdapter) {
        Intrinsics.checkNotNullParameter(instructionAdapter, "<this>");
        instructionAdapter.invokestatic(coroutinesIntrinsicsFileFacadeInternalName.getInternalName(), "get" + CommonCoroutineCodegenUtilKt.getCOROUTINE_SUSPENDED_NAME(), Type.getMethodDescriptor(AsmTypes.OBJECT_TYPE, new Type[0]), false);
    }

    public static final void generateCoroutineSuspendedCheck(InstructionAdapter instructionAdapter) {
        Intrinsics.checkNotNullParameter(instructionAdapter, "<this>");
        instructionAdapter.dup();
        loadCoroutineSuspendedMarker(instructionAdapter);
        Label label = new Label();
        instructionAdapter.ifacmpne(label);
        instructionAdapter.areturn(AsmTypes.OBJECT_TYPE);
        instructionAdapter.mark(label);
    }

    public static final void invokeInvokeSuspendWithUnit(InstructionAdapter instructionAdapter, String thisName) {
        Intrinsics.checkNotNullParameter(instructionAdapter, "<this>");
        Intrinsics.checkNotNullParameter(thisName, "thisName");
        StackValue.putUnitInstance(instructionAdapter);
        instructionAdapter.invokevirtual(thisName, INVOKE_SUSPEND_METHOD_NAME, Type.getMethodDescriptor(AsmTypes.OBJECT_TYPE, AsmTypes.OBJECT_TYPE), false);
    }

    public static final Method getImplForOpenMethod(Method method, String ownerInternalName) {
        Intrinsics.checkNotNullParameter(method, "<this>");
        Intrinsics.checkNotNullParameter(ownerInternalName, "ownerInternalName");
        String str = method.getName() + SUSPEND_IMPL_NAME_SUFFIX;
        Type returnType = method.getReturnType();
        Type[] typeArr = {Type.getObjectType(ownerInternalName)};
        Type[] argumentTypes = method.getArgumentTypes();
        Intrinsics.checkNotNullExpressionValue(argumentTypes, "getArgumentTypes(...)");
        return new Method(str, returnType, (Type[]) ArraysKt.plus((Object[]) typeArr, (Object[]) argumentTypes));
    }

    public static final boolean isSuspendLambdaOrLocalFunction(FunctionDescriptor functionDescriptor) {
        boolean zAreEqual;
        Intrinsics.checkNotNullParameter(functionDescriptor, "<this>");
        if (!functionDescriptor.isSuspend()) {
            return false;
        }
        if (functionDescriptor instanceof AnonymousFunctionDescriptor) {
            zAreEqual = CoroutineUtilKt.isSuspendLambda(functionDescriptor);
        } else {
            zAreEqual = functionDescriptor instanceof SimpleFunctionDescriptor ? Intrinsics.areEqual(((SimpleFunctionDescriptor) functionDescriptor).getVisibilityImpl(), DescriptorVisibilities.LOCAL) : false;
        }
        return zAreEqual;
    }

    public static final boolean isLocalSuspendFunctionNotSuspendLambda(FunctionDescriptor functionDescriptor) {
        Intrinsics.checkNotNullParameter(functionDescriptor, "<this>");
        return isSuspendLambdaOrLocalFunction(functionDescriptor) && !(functionDescriptor instanceof AnonymousFunctionDescriptor);
    }

    public static final boolean isInvokeSuspendOfLambda(FunctionDescriptor functionDescriptor) {
        Intrinsics.checkNotNullParameter(functionDescriptor, "<this>");
        if (!(functionDescriptor instanceof SimpleFunctionDescriptor)) {
            return false;
        }
        SimpleFunctionDescriptor simpleFunctionDescriptor = (SimpleFunctionDescriptor) functionDescriptor;
        if (simpleFunctionDescriptor.getValueParameters().size() == 1 && Intrinsics.areEqual(simpleFunctionDescriptor.getValueParameters().get(0).getShortName().asString(), CoroutineConstantsKt.SUSPEND_CALL_RESULT_NAME) && Intrinsics.areEqual(simpleFunctionDescriptor.getShortName().asString(), INVOKE_SUSPEND_METHOD_NAME)) {
            return simpleFunctionDescriptor.getContainingDeclaration() instanceof SyntheticClassDescriptorForLambda;
        }
        return false;
    }
}
