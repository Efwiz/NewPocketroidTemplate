package org.jetbrains.kotlin.resolve;

import com.huawei.hms.ml.language.common.utils.Constant;
import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.google.common.collect.Lists;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptorWithAccessors;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory2;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactoryForDeprecation0;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtPropertyAccessor;
import org.jetbrains.kotlin.psi.KtPropertyDelegate;
import org.jetbrains.kotlin.psi.KtPsiFactory;
import org.jetbrains.kotlin.psi.KtReferenceExpression;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.renderer.DescriptorRenderer;
import org.jetbrains.kotlin.resolve.calls.checkers.OperatorCallChecker;
import org.jetbrains.kotlin.resolve.calls.components.InferenceSession;
import org.jetbrains.kotlin.resolve.calls.components.PostponedArgumentsAnalyzer;
import org.jetbrains.kotlin.resolve.calls.context.BasicCallResolutionContext;
import org.jetbrains.kotlin.resolve.calls.context.CheckArgumentTypesMode;
import org.jetbrains.kotlin.resolve.calls.context.ContextDependency;
import org.jetbrains.kotlin.resolve.calls.inference.CallHandle;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystem;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemCompleter;
import org.jetbrains.kotlin.resolve.calls.inference.TypeVariableKt;
import org.jetbrains.kotlin.resolve.calls.inference.components.EmptySubstitutor;
import org.jetbrains.kotlin.resolve.calls.inference.components.KotlinConstraintSystemCompleter;
import org.jetbrains.kotlin.resolve.calls.inference.components.NewTypeSubstitutorByConstructorMap;
import org.jetbrains.kotlin.resolve.calls.inference.constraintPosition.ConstraintPositionKind;
import org.jetbrains.kotlin.resolve.calls.inference.model.TypeVariableTypeConstructor;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCall;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallComponents;
import org.jetbrains.kotlin.resolve.calls.model.ResolutionAtomsKt;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCallAtom;
import org.jetbrains.kotlin.resolve.calls.results.OverloadResolutionResults;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValueFactory;
import org.jetbrains.kotlin.resolve.calls.tower.PSICallResolver;
import org.jetbrains.kotlin.resolve.calls.tower.ResolutionResultCallInfo;
import org.jetbrains.kotlin.resolve.calls.util.CallMaker;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.calls.util.PsiUtilsKt;
import org.jetbrains.kotlin.resolve.constants.IntegerLiteralTypeConstructor;
import org.jetbrains.kotlin.resolve.diagnostics.Diagnostics;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.resolve.scopes.ScopeUtils;
import org.jetbrains.kotlin.resolve.scopes.receivers.ExpressionReceiver;
import org.jetbrains.kotlin.types.DeferredType;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.StubTypeForProvideDelegateReceiver;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeSubstitutor;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.checker.KotlinTypeChecker;
import org.jetbrains.kotlin.types.checker.NewTypeVariableConstructor;
import org.jetbrains.kotlin.types.error.ErrorTypeKind;
import org.jetbrains.kotlin.types.error.ErrorUtils;
import org.jetbrains.kotlin.types.expressions.ExpressionTypingContext;
import org.jetbrains.kotlin.types.expressions.ExpressionTypingServices;
import org.jetbrains.kotlin.types.expressions.ExpressionTypingUtils;
import org.jetbrains.kotlin.types.expressions.FakeCallResolver;
import org.jetbrains.kotlin.types.expressions.KotlinTypeInfo;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;
import org.jetbrains.kotlin.util.OperatorNameConventions;
import org.jetbrains.kotlin.util.slicedMap.WritableSlice;

/* JADX INFO: compiled from: DelegatedPropertyResolver.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000Î\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0018\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0001hBM\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u0012\u0006\u0010\u0012\u001a\u00020\u0013¢\u0006\u0002\u0010\u0014J\u001e\u0010\u0018\u001a\u0010\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020\u001a\u0018\u00010\u00192\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J\u0018\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"H\u0002J\u0016\u0010#\u001a\u00020\u00162\f\u0010$\u001a\b\u0012\u0004\u0012\u00020&0%H\u0002J0\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020.2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010/\u001a\u000200H\u0002J(\u00101\u001a\u00020(2\u0006\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020.2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010/\u001a\u000200H\u0002J \u00102\u001a\u0002032\u0006\u00104\u001a\u0002052\u0006\u00106\u001a\u00020\u001c2\u0006\u0010\u001f\u001a\u00020 H\u0002J \u00107\u001a\u00020\u001c2\u0006\u00104\u001a\u0002052\u0006\u0010\u001f\u001a\u00020 2\u0006\u00106\u001a\u00020\u001cH\u0002Jf\u00108\u001a\b\u0012\u0004\u0012\u00020&0%2\u0006\u0010)\u001a\u00020*2\u0006\u00104\u001a\u0002052\u0006\u00106\u001a\u00020\u001c2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020.2\u0006\u00109\u001a\u00020\u00162\u0006\u0010:\u001a\u00020\u00162\n\b\u0002\u0010;\u001a\u0004\u0018\u0001032\n\b\u0002\u0010<\u001a\u0004\u0018\u00010(H\u0002J@\u0010=\u001a\u0004\u0018\u00010\u001c2\u0006\u0010>\u001a\u00020*2\u0006\u00104\u001a\u0002052\u0006\u0010?\u001a\u00020\u001c2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010@\u001a\u00020,2\u0006\u0010-\u001a\u00020.2\u0006\u0010A\u001a\u000200JH\u0010B\u001a\b\u0012\u0004\u0012\u00020&0%2\u0006\u0010)\u001a\u00020*2\u0006\u00104\u001a\u0002052\u0006\u0010C\u001a\u00020\u001c2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010@\u001a\u00020,2\u0006\u0010-\u001a\u00020.2\b\u0010A\u001a\u0004\u0018\u000100H\u0002J.\u0010B\u001a\b\u0012\u0004\u0012\u00020&0%2\u0006\u0010)\u001a\u00020*2\u0006\u00104\u001a\u0002052\u0006\u0010C\u001a\u00020\u001c2\u0006\u0010D\u001a\u00020(H\u0002J(\u0010E\u001a\u00020\u001c2\u0006\u0010>\u001a\u00020*2\u0006\u00104\u001a\u0002052\u0006\u0010?\u001a\u00020\u001c2\u0006\u0010\u001f\u001a\u00020 H\u0002JJ\u0010F\u001a\u00020\u001a2\u0006\u00104\u001a\u0002052\u0006\u0010>\u001a\u00020*2\u0006\u0010+\u001a\u00020,2\u0006\u0010\u001f\u001a\u00020\"2\u0006\u00106\u001a\u00020\u001c2\b\u0010G\u001a\u0004\u0018\u00010\u001c2\u0006\u0010H\u001a\u00020.2\u0006\u0010A\u001a\u000200H\u0002J*\u0010I\u001a\u0004\u0018\u00010\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\u0014\u0010J\u001a\u0010\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020\u001a\u0018\u00010\u0019H\u0002J.\u0010K\u001a\u00020\u001e2\f\u0010L\u001a\b\u0012\u0004\u0012\u00020&0%2\u0006\u0010)\u001a\u00020*2\u0006\u0010M\u001a\u00020N2\u0006\u0010\u001f\u001a\u00020 H\u0002J\u0018\u0010O\u001a\u00020P2\u0006\u0010Q\u001a\u00020R2\u0006\u0010D\u001a\u00020SH\u0002J.\u0010T\u001a\u00020\u00162\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010U\u001a\u00020R2\f\u0010V\u001a\b\u0012\u0004\u0012\u00020&0%2\u0006\u00104\u001a\u000205H\u0002J0\u0010W\u001a\u00020\u001e2\u0006\u0010U\u001a\u00020R2\u0006\u00104\u001a\u0002052\u0006\u00106\u001a\u00020\u001c2\u0006\u0010\u001f\u001a\u00020 2\u0006\u00109\u001a\u00020\u0016H\u0002J>\u0010X\u001a\u00020\u001e2\f\u0010L\u001a\b\u0012\u0004\u0012\u00020&0%2\u0006\u0010M\u001a\u00020N2\u0006\u00104\u001a\u0002052\u0006\u00106\u001a\u00020\u001c2\u0006\u0010\u001f\u001a\u00020 2\u0006\u00109\u001a\u00020\u0016H\u0002J>\u0010Y\u001a\u00020\u001c2\u0006\u00104\u001a\u0002052\u0006\u0010Z\u001a\u00020[2\u0006\u0010>\u001a\u00020*2\u0006\u0010+\u001a\u00020,2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010-\u001a\u00020.2\u0006\u0010A\u001a\u000200J@\u0010\\\u001a\u00020\u001e2\u0006\u0010)\u001a\u00020*2\u0006\u00104\u001a\u0002052\u0006\u00106\u001a\u00020\u001c2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010@\u001a\u00020,2\u0006\u0010-\u001a\u00020.2\u0006\u00109\u001a\u00020\u0016H\u0002J@\u0010]\u001a\u00020\u001e2\u0006\u0010>\u001a\u00020*2\u0006\u00104\u001a\u0002052\u0006\u00106\u001a\u00020\u001c2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010@\u001a\u00020,2\u0006\u0010-\u001a\u00020.2\u0006\u0010A\u001a\u000200H\u0002J>\u0010^\u001a\u00020\u001e2\u0006\u0010_\u001a\u00020.2\u0006\u0010Z\u001a\u00020[2\u0006\u0010>\u001a\u00020*2\u0006\u00104\u001a\u0002052\u0006\u0010`\u001a\u00020,2\u0006\u0010A\u001a\u0002002\u0006\u0010\u001f\u001a\u00020 J@\u0010a\u001a\u00020\u001e2\u0006\u0010)\u001a\u00020*2\u0006\u0010b\u001a\u0002052\u0006\u0010?\u001a\u00020\u001c2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010@\u001a\u00020,2\u0006\u0010-\u001a\u00020.2\u0006\u0010A\u001a\u000200H\u0002J8\u0010c\u001a\u00020\u001e2\u0006\u0010>\u001a\u00020*2\u0006\u00104\u001a\u0002052\u0006\u00106\u001a\u00020\u001c2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010@\u001a\u00020,2\u0006\u0010-\u001a\u00020.H\u0002J:\u0010d\u001a\u0004\u0018\u00010\u001c2\u0006\u00104\u001a\u0002052\u0006\u0010>\u001a\u00020*2\u0006\u0010+\u001a\u00020,2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010-\u001a\u00020.2\u0006\u0010A\u001a\u000200H\u0002J\f\u0010e\u001a\u000205*\u00020fH\u0002J\f\u0010g\u001a\u00020\u0016*\u00020\u001cH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0015\u001a\u00020\u00168BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0017R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006i"}, d2 = {"Lorg/jetbrains/kotlin/resolve/DelegatedPropertyResolver;", "", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "fakeCallResolver", "Lorg/jetbrains/kotlin/types/expressions/FakeCallResolver;", "expressionTypingServices", "Lorg/jetbrains/kotlin/types/expressions/ExpressionTypingServices;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "dataFlowValueFactory", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValueFactory;", "psiCallResolver", "Lorg/jetbrains/kotlin/resolve/calls/tower/PSICallResolver;", "postponedArgumentsAnalyzer", "Lorg/jetbrains/kotlin/resolve/calls/components/PostponedArgumentsAnalyzer;", "kotlinConstraintSystemCompleter", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/KotlinConstraintSystemCompleter;", "callComponents", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallComponents;", "(Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;Lorg/jetbrains/kotlin/types/expressions/FakeCallResolver;Lorg/jetbrains/kotlin/types/expressions/ExpressionTypingServices;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValueFactory;Lorg/jetbrains/kotlin/resolve/calls/tower/PSICallResolver;Lorg/jetbrains/kotlin/resolve/calls/components/PostponedArgumentsAnalyzer;Lorg/jetbrains/kotlin/resolve/calls/inference/components/KotlinConstraintSystemCompleter;Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallComponents;)V", "isOperatorProvideDelegateSupported", "", "()Z", "buildSubstitutionMapOfNonFixedVariables", "", "Lorg/jetbrains/kotlin/types/UnwrappedType;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "completeNotComputedDelegateType", "", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "traceToResolveDelegatedProperty", "Lorg/jetbrains/kotlin/resolve/TemporaryBindingTrace;", "conventionMethodFound", "results", "Lorg/jetbrains/kotlin/resolve/calls/results/OverloadResolutionResults;", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "createContextForGetSetValueMethods", "Lorg/jetbrains/kotlin/types/expressions/ExpressionTypingContext;", "propertyDescriptor", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptorWithAccessors;", "scopeForDelegate", "Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;", "dataFlowInfo", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;", "inferenceExtension", "Lorg/jetbrains/kotlin/resolve/calls/components/InferenceSession;", "createContextForProvideDelegateMethod", "createReceiverForGetSetValueMethods", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ExpressionReceiver;", "delegateExpression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "delegateType", "extractResolvedDelegateType", "getGetSetValueMethod", "isGet", "isComplete", "knownReceiver", "knownContext", "getGetValueMethodReturnType", "variableDescriptor", "byExpressionType", "initializerScope", "inferenceSession", "getProvideDelegateMethod", "delegateExpressionType", "context", "getResolvedDelegateType", "inferDelegateTypeFromGetSetValueMethods", "delegateTypeForProperType", "delegateDataFlow", "inverseSubstitution", "substitutionMap", "recordDelegateOperatorResults", "result", Implementation.Context.Default.ACCESSOR_METHOD_SUFFIX, "Lorg/jetbrains/kotlin/descriptors/VariableAccessorDescriptor;", "renderCall", "", "call", "Lorg/jetbrains/kotlin/psi/Call;", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "reportDelegateErrorIfCandidateExists", "delegateOperatorCall", "delegateOperatorResults", "reportDelegateFunctionMissing", "reportGetSetValueResolutionError", "resolveDelegateExpression", "property", "Lorg/jetbrains/kotlin/psi/KtProperty;", "resolveGetSetValueMethod", "resolveGetValueMethod", "resolvePropertyDelegate", "outerDataFlowInfo", "propertyHeaderScope", "resolveProvideDelegateMethod", "byExpression", "resolveSetValueMethod", "resolveWithNewInference", "createExpressionForProperty", "Lorg/jetbrains/kotlin/psi/KtPsiFactory;", "isProperType", "ConstraintSystemCompleterImpl", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DelegatedPropertyResolver {
    private final KotlinBuiltIns builtIns;
    private final KotlinCallComponents callComponents;
    private final DataFlowValueFactory dataFlowValueFactory;
    private final ExpressionTypingServices expressionTypingServices;
    private final FakeCallResolver fakeCallResolver;
    private final KotlinConstraintSystemCompleter kotlinConstraintSystemCompleter;
    private final LanguageVersionSettings languageVersionSettings;
    private final PostponedArgumentsAnalyzer postponedArgumentsAnalyzer;
    private final PSICallResolver psiCallResolver;

    public DelegatedPropertyResolver(KotlinBuiltIns builtIns, FakeCallResolver fakeCallResolver, ExpressionTypingServices expressionTypingServices, LanguageVersionSettings languageVersionSettings, DataFlowValueFactory dataFlowValueFactory, PSICallResolver psiCallResolver, PostponedArgumentsAnalyzer postponedArgumentsAnalyzer, KotlinConstraintSystemCompleter kotlinConstraintSystemCompleter, KotlinCallComponents callComponents) {
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        Intrinsics.checkNotNullParameter(fakeCallResolver, "fakeCallResolver");
        Intrinsics.checkNotNullParameter(expressionTypingServices, "expressionTypingServices");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(dataFlowValueFactory, "dataFlowValueFactory");
        Intrinsics.checkNotNullParameter(psiCallResolver, "psiCallResolver");
        Intrinsics.checkNotNullParameter(postponedArgumentsAnalyzer, "postponedArgumentsAnalyzer");
        Intrinsics.checkNotNullParameter(kotlinConstraintSystemCompleter, "kotlinConstraintSystemCompleter");
        Intrinsics.checkNotNullParameter(callComponents, "callComponents");
        this.builtIns = builtIns;
        this.fakeCallResolver = fakeCallResolver;
        this.expressionTypingServices = expressionTypingServices;
        this.languageVersionSettings = languageVersionSettings;
        this.dataFlowValueFactory = dataFlowValueFactory;
        this.psiCallResolver = psiCallResolver;
        this.postponedArgumentsAnalyzer = postponedArgumentsAnalyzer;
        this.kotlinConstraintSystemCompleter = kotlinConstraintSystemCompleter;
        this.callComponents = callComponents;
    }

    public final void resolvePropertyDelegate(DataFlowInfo outerDataFlowInfo, KtProperty property, VariableDescriptorWithAccessors variableDescriptor, KtExpression delegateExpression, LexicalScope propertyHeaderScope, InferenceSession inferenceSession, BindingTrace trace) {
        LexicalScope propertyHeaderScope2 = propertyHeaderScope;
        Intrinsics.checkNotNullParameter(outerDataFlowInfo, "outerDataFlowInfo");
        Intrinsics.checkNotNullParameter(property, "property");
        Intrinsics.checkNotNullParameter(variableDescriptor, "variableDescriptor");
        Intrinsics.checkNotNullParameter(delegateExpression, "delegateExpression");
        Intrinsics.checkNotNullParameter(propertyHeaderScope2, "propertyHeaderScope");
        Intrinsics.checkNotNullParameter(inferenceSession, "inferenceSession");
        Intrinsics.checkNotNullParameter(trace, "trace");
        KtPropertyAccessor getter = property.getGetter();
        if (getter != null && getter.hasBody()) {
            trace.report(Errors.ACCESSOR_FOR_DELEGATED_PROPERTY.on(getter));
        }
        KtPropertyAccessor setter = property.getSetter();
        if (setter != null && setter.hasBody()) {
            trace.report(Errors.ACCESSOR_FOR_DELEGATED_PROPERTY.on(setter));
        }
        if (variableDescriptor instanceof PropertyDescriptor) {
            propertyHeaderScope2 = ScopeUtils.makeScopeForPropertyInitializer(propertyHeaderScope2, (PropertyDescriptor) variableDescriptor);
            Intrinsics.checkNotNullExpressionValue(propertyHeaderScope2, "makeScopeForPropertyInitializer(...)");
        }
        LexicalScope lexicalScope = propertyHeaderScope2;
        KotlinType kotlinTypeResolveDelegateExpression = resolveDelegateExpression(delegateExpression, property, variableDescriptor, lexicalScope, trace, outerDataFlowInfo, inferenceSession);
        resolveProvideDelegateMethod(variableDescriptor, delegateExpression, kotlinTypeResolveDelegateExpression, trace, lexicalScope, outerDataFlowInfo, inferenceSession);
        KotlinType resolvedDelegateType = getResolvedDelegateType(variableDescriptor, delegateExpression, kotlinTypeResolveDelegateExpression, trace);
        resolveGetValueMethod(variableDescriptor, delegateExpression, resolvedDelegateType, trace, lexicalScope, outerDataFlowInfo, inferenceSession);
        if (property.isVar()) {
            resolveSetValueMethod(variableDescriptor, delegateExpression, resolvedDelegateType, trace, lexicalScope, outerDataFlowInfo);
        }
    }

    private final KotlinType getResolvedDelegateType(VariableDescriptorWithAccessors variableDescriptor, KtExpression delegateExpression, KotlinType byExpressionType, BindingTrace trace) {
        ResolvedCall resolvedCall = (ResolvedCall) trace.getContext().get(BindingContext.PROVIDE_DELEGATE_RESOLVED_CALL, variableDescriptor);
        if (resolvedCall == null) {
            return byExpressionType;
        }
        KotlinType returnType = ((FunctionDescriptor) resolvedCall.getResultingDescriptor()).getReturnType();
        if (returnType != null) {
            return returnType;
        }
        throw new AssertionError("No return type fore 'provideDelegate' of " + delegateExpression.getText());
    }

    public final KotlinType getGetValueMethodReturnType(VariableDescriptorWithAccessors variableDescriptor, KtExpression delegateExpression, KotlinType byExpressionType, BindingTrace trace, LexicalScope initializerScope, DataFlowInfo dataFlowInfo, InferenceSession inferenceSession) {
        FunctionDescriptor functionDescriptor;
        Intrinsics.checkNotNullParameter(variableDescriptor, "variableDescriptor");
        Intrinsics.checkNotNullParameter(delegateExpression, "delegateExpression");
        Intrinsics.checkNotNullParameter(byExpressionType, "byExpressionType");
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(initializerScope, "initializerScope");
        Intrinsics.checkNotNullParameter(dataFlowInfo, "dataFlowInfo");
        Intrinsics.checkNotNullParameter(inferenceSession, "inferenceSession");
        resolveProvideDelegateMethod(variableDescriptor, delegateExpression, byExpressionType, trace, initializerScope, dataFlowInfo, inferenceSession);
        resolveGetSetValueMethod(variableDescriptor, delegateExpression, getResolvedDelegateType(variableDescriptor, delegateExpression, byExpressionType, trace), trace, initializerScope, dataFlowInfo, true);
        ResolvedCall resolvedCall = (ResolvedCall) trace.getContext().get(BindingContext.DELEGATED_PROPERTY_RESOLVED_CALL, variableDescriptor.getGetter());
        if (resolvedCall == null || (functionDescriptor = (FunctionDescriptor) resolvedCall.getResultingDescriptor()) == null) {
            return null;
        }
        return functionDescriptor.getReturnType();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isOperatorProvideDelegateSupported() {
        return this.languageVersionSettings.supportsFeature(LanguageFeature.OperatorProvideDelegate);
    }

    private final void resolveGetValueMethod(VariableDescriptorWithAccessors variableDescriptor, KtExpression delegateExpression, KotlinType delegateType, BindingTrace trace, LexicalScope initializerScope, DataFlowInfo dataFlowInfo, InferenceSession inferenceSession) {
        KotlinType getValueMethodReturnType = getGetValueMethodReturnType(variableDescriptor, delegateExpression, delegateType, trace, initializerScope, dataFlowInfo, inferenceSession);
        KotlinType type = variableDescriptor.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        if ((type instanceof DeferredType) || getValueMethodReturnType == null || KotlinTypeChecker.DEFAULT.isSubtypeOf(getValueMethodReturnType, type)) {
            return;
        }
        Call call = (Call) trace.getContext().get(BindingContext.DELEGATED_PROPERTY_CALL, variableDescriptor.getGetter());
        if (call == null) {
            throw new AssertionError("Call should exists for " + variableDescriptor.getGetter());
        }
        BindingContext context = trace.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
        trace.report(Errors.DELEGATE_SPECIAL_FUNCTION_RETURN_TYPE_MISMATCH.on(delegateExpression, renderCall(call, context), variableDescriptor.getType(), getValueMethodReturnType));
    }

    private final void resolveSetValueMethod(VariableDescriptorWithAccessors variableDescriptor, KtExpression delegateExpression, KotlinType delegateType, BindingTrace trace, LexicalScope initializerScope, DataFlowInfo dataFlowInfo) {
        resolveGetSetValueMethod(variableDescriptor, delegateExpression, delegateType, trace, initializerScope, dataFlowInfo, false);
    }

    private final KtExpression createExpressionForProperty(KtPsiFactory ktPsiFactory) {
        return ktPsiFactory.createExpression("null as " + StandardNames.FqNames.kPropertyFqName.asString() + "<*>");
    }

    private final void resolveGetSetValueMethod(VariableDescriptorWithAccessors propertyDescriptor, KtExpression delegateExpression, KotlinType delegateType, BindingTrace trace, LexicalScope initializerScope, DataFlowInfo dataFlowInfo, boolean isGet) {
        VariableAccessorDescriptor getter = isGet ? propertyDescriptor.getGetter() : propertyDescriptor.getSetter();
        if (getter == null) {
            throw new AssertionError("Delegated property should have getter/setter " + propertyDescriptor + StringUtil.SPACE + delegateExpression.getText());
        }
        if (trace.getContext().get(BindingContext.DELEGATED_PROPERTY_CALL, getter) != null) {
            return;
        }
        OverloadResolutionResults<FunctionDescriptor> getSetValueMethod$default = getGetSetValueMethod$default(this, propertyDescriptor, delegateExpression, delegateType, trace, initializerScope, dataFlowInfo, isGet, true, null, null, 768, null);
        if (getSetValueMethod$default.isSuccess()) {
            recordDelegateOperatorResults(getSetValueMethod$default, propertyDescriptor, getter, trace);
        } else {
            reportGetSetValueResolutionError(getSetValueMethod$default, getter, delegateExpression, delegateType, trace, isGet);
        }
    }

    private final void recordDelegateOperatorResults(OverloadResolutionResults<FunctionDescriptor> result, VariableDescriptorWithAccessors propertyDescriptor, VariableAccessorDescriptor accessor, BindingTrace trace) {
        KtPropertyDelegate delegate;
        CallableDescriptor callableDescriptorMo7437getResultingDescriptor = result.mo7437getResultingDescriptor();
        Intrinsics.checkNotNullExpressionValue(callableDescriptorMo7437getResultingDescriptor, "getResultingDescriptor(...)");
        FunctionDescriptor functionDescriptor = (FunctionDescriptor) callableDescriptorMo7437getResultingDescriptor;
        Object objMo7436getResultingCall = result.mo7436getResultingCall();
        Intrinsics.checkNotNullExpressionValue(objMo7436getResultingCall, "getResultingCall(...)");
        if (!functionDescriptor.isOperator()) {
            PsiElement psiElementDescriptorToDeclaration = DescriptorToSourceUtils.descriptorToDeclaration(propertyDescriptor);
            if ((psiElementDescriptorToDeclaration instanceof KtProperty) && (delegate = ((KtProperty) psiElementDescriptorToDeclaration).getDelegate()) != null) {
                PsiElement psi = delegate.getByKeywordNode().getPsi();
                OperatorCallChecker.Companion companion = OperatorCallChecker.INSTANCE;
                Intrinsics.checkNotNull(psi);
                companion.report(psi, functionDescriptor, trace);
            }
        }
        trace.record(BindingContext.DELEGATED_PROPERTY_RESOLVED_CALL, accessor, objMo7436getResultingCall);
    }

    private final void reportGetSetValueResolutionError(OverloadResolutionResults<FunctionDescriptor> result, VariableAccessorDescriptor accessor, KtExpression delegateExpression, KotlinType delegateType, BindingTrace trace, boolean isGet) {
        Call call = (Call) trace.getContext().get(BindingContext.DELEGATED_PROPERTY_CALL, accessor);
        if (call == null) {
            throw new AssertionError("'getDelegatedPropertyConventionMethod' didn't record a call");
        }
        if (reportDelegateErrorIfCandidateExists(trace, call, result, delegateExpression)) {
            return;
        }
        reportDelegateFunctionMissing(call, delegateExpression, delegateType, trace, isGet);
    }

    private final void reportDelegateFunctionMissing(Call delegateOperatorCall, KtExpression delegateExpression, KotlinType delegateType, BindingTrace trace, boolean isGet) {
        BindingContext context = trace.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
        trace.report(Errors.DELEGATE_SPECIAL_FUNCTION_MISSING.on(delegateExpression, renderCall(delegateOperatorCall, context), delegateType, isGet ? MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX : "delegate for var (read-write property)"));
    }

    private final boolean reportDelegateErrorIfCandidateExists(BindingTrace trace, Call delegateOperatorCall, OverloadResolutionResults<FunctionDescriptor> delegateOperatorResults, KtExpression delegateExpression) {
        DiagnosticFactory2 diagnosticFactory2;
        if (delegateOperatorResults.isSingleResult() || delegateOperatorResults.isIncomplete() || delegateOperatorResults.getResultCode() == OverloadResolutionResults.Code.MANY_FAILED_CANDIDATES) {
            diagnosticFactory2 = Errors.DELEGATE_SPECIAL_FUNCTION_NONE_APPLICABLE;
        } else {
            diagnosticFactory2 = delegateOperatorResults.isAmbiguity() ? Errors.DELEGATE_SPECIAL_FUNCTION_AMBIGUITY : null;
        }
        if (diagnosticFactory2 != null) {
            BindingContext context = trace.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
            trace.report(diagnosticFactory2.on(delegateExpression, renderCall(delegateOperatorCall, context), delegateOperatorResults.getResultingCalls()));
        }
        return diagnosticFactory2 != null;
    }

    private final void resolveProvideDelegateMethod(VariableDescriptorWithAccessors propertyDescriptor, KtExpression byExpression, KotlinType byExpressionType, BindingTrace trace, LexicalScope initializerScope, DataFlowInfo dataFlowInfo, InferenceSession inferenceSession) {
        if (isOperatorProvideDelegateSupported() && trace.getContext().get(BindingContext.PROVIDE_DELEGATE_CALL, propertyDescriptor) == null) {
            TemporaryBindingTrace temporaryBindingTraceCreate = TemporaryBindingTrace.create(trace, "trace to resolve provideDelegate method");
            Intrinsics.checkNotNullExpressionValue(temporaryBindingTraceCreate, "create(...)");
            TemporaryBindingTrace temporaryBindingTrace = temporaryBindingTraceCreate;
            OverloadResolutionResults<FunctionDescriptor> provideDelegateMethod = getProvideDelegateMethod(propertyDescriptor, byExpression, byExpressionType, temporaryBindingTrace, initializerScope, dataFlowInfo, inferenceSession);
            if (!provideDelegateMethod.isSuccess()) {
                Call call = (Call) temporaryBindingTraceCreate.getContext().get(BindingContext.PROVIDE_DELEGATE_CALL, propertyDescriptor);
                if (call == null) {
                    throw new AssertionError("'getDelegatedPropertyConventionMethod' didn't record a call");
                }
                if (reportDelegateErrorIfCandidateExists(temporaryBindingTrace, call, provideDelegateMethod, byExpression)) {
                    temporaryBindingTraceCreate.commit();
                    return;
                }
                return;
            }
            temporaryBindingTraceCreate.commit();
            CallableDescriptor callableDescriptorMo7437getResultingDescriptor = provideDelegateMethod.mo7437getResultingDescriptor();
            Intrinsics.checkNotNullExpressionValue(callableDescriptorMo7437getResultingDescriptor, "getResultingDescriptor(...)");
            if (((FunctionDescriptor) callableDescriptorMo7437getResultingDescriptor).isOperator()) {
                Object objMo7436getResultingCall = provideDelegateMethod.mo7436getResultingCall();
                Intrinsics.checkNotNullExpressionValue(objMo7436getResultingCall, "getResultingCall(...)");
                trace.record(BindingContext.PROVIDE_DELEGATE_RESOLVED_CALL, propertyDescriptor, objMo7436getResultingCall);
            }
        }
    }

    static /* synthetic */ OverloadResolutionResults getGetSetValueMethod$default(DelegatedPropertyResolver delegatedPropertyResolver, VariableDescriptorWithAccessors variableDescriptorWithAccessors, KtExpression ktExpression, KotlinType kotlinType, BindingTrace bindingTrace, LexicalScope lexicalScope, DataFlowInfo dataFlowInfo, boolean z, boolean z2, ExpressionReceiver expressionReceiver, ExpressionTypingContext expressionTypingContext, int i, Object obj) {
        return delegatedPropertyResolver.getGetSetValueMethod(variableDescriptorWithAccessors, ktExpression, kotlinType, bindingTrace, lexicalScope, dataFlowInfo, z, z2, (i & 256) != 0 ? null : expressionReceiver, (i & 512) != 0 ? null : expressionTypingContext);
    }

    private final OverloadResolutionResults<FunctionDescriptor> getGetSetValueMethod(VariableDescriptorWithAccessors propertyDescriptor, KtExpression delegateExpression, KotlinType delegateType, BindingTrace trace, LexicalScope scopeForDelegate, DataFlowInfo dataFlowInfo, boolean isGet, boolean isComplete, ExpressionReceiver knownReceiver, ExpressionTypingContext knownContext) {
        SimpleType type;
        ExpressionTypingContext expressionTypingContextNewContext;
        ExpressionReceiver expressionReceiverCreate;
        LexicalScope lexicalScopeMakeScopeForDelegateConventionFunctions = ScopeUtils.makeScopeForDelegateConventionFunctions(scopeForDelegate, propertyDescriptor);
        Intrinsics.checkNotNullExpressionValue(lexicalScopeMakeScopeForDelegateConventionFunctions, "makeScopeForDelegateConventionFunctions(...)");
        VariableAccessorDescriptor getter = isGet ? propertyDescriptor.getGetter() : propertyDescriptor.getSetter();
        if (getter == null) {
            throw new AssertionError("Delegated property should have getter/setter " + propertyDescriptor + StringUtil.SPACE + delegateExpression.getText());
        }
        if (isComplete && isGet && !(propertyDescriptor.getType() instanceof DeferredType)) {
            type = propertyDescriptor.getType();
        } else {
            type = TypeUtils.NO_EXPECTED_TYPE;
        }
        KotlinType kotlinType = type;
        Intrinsics.checkNotNull(kotlinType);
        if (knownContext == null) {
            expressionTypingContextNewContext = ExpressionTypingContext.newContext(trace, lexicalScopeMakeScopeForDelegateConventionFunctions, dataFlowInfo, kotlinType, this.languageVersionSettings, this.dataFlowValueFactory);
            Intrinsics.checkNotNullExpressionValue(expressionTypingContextNewContext, "newContext(...)");
        } else {
            expressionTypingContextNewContext = knownContext;
        }
        boolean z = (propertyDescriptor.getExtensionReceiverParameter() == null && propertyDescriptor.mo6753getDispatchReceiverParameter() == null) ? false : true;
        ArrayList arrayListNewArrayList = Lists.newArrayList();
        Project project = delegateExpression.getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        KtPsiFactory ktPsiFactory = new KtPsiFactory(project, false);
        arrayListNewArrayList.add(ktPsiFactory.createExpression(z ? "this" : "null"));
        arrayListNewArrayList.add(createExpressionForProperty(ktPsiFactory));
        if (!isGet) {
            KtExpression ktExpressionCreateFakeExpressionOfType = ExpressionTypingUtils.createFakeExpressionOfType(delegateExpression.getProject(), trace, "fakeArgument" + arrayListNewArrayList.size(), propertyDescriptor.getType());
            Intrinsics.checkNotNull(ktExpressionCreateFakeExpressionOfType, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtReferenceExpression");
            KtReferenceExpression ktReferenceExpression = (KtReferenceExpression) ktExpressionCreateFakeExpressionOfType;
            arrayListNewArrayList.add(ktReferenceExpression);
            List<ValueParameterDescriptor> valueParameters = getter.getValueParameters();
            Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
            trace.record(BindingContext.REFERENCE_TARGET, ktReferenceExpression, valueParameters.get(0));
        }
        Name name2 = isGet ? OperatorNameConventions.GET_VALUE : OperatorNameConventions.SET_VALUE;
        if (knownReceiver == null) {
            ExpressionReceiver.Companion companion = ExpressionReceiver.INSTANCE;
            BindingContext context = trace.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
            expressionReceiverCreate = companion.create(delegateExpression, delegateType, context);
        } else {
            expressionReceiverCreate = knownReceiver;
        }
        Intrinsics.checkNotNull(arrayListNewArrayList);
        Pair pairMakeAndResolveFakeCallInContext$default = FakeCallResolver.makeAndResolveFakeCallInContext$default(this.fakeCallResolver, expressionReceiverCreate, expressionTypingContextNewContext, arrayListNewArrayList, name2, delegateExpression, null, null, 96, null);
        trace.record(BindingContext.DELEGATED_PROPERTY_CALL, getter, pairMakeAndResolveFakeCallInContext$default.getFirst());
        return (OverloadResolutionResults) pairMakeAndResolveFakeCallInContext$default.getSecond();
    }

    private final ExpressionReceiver createReceiverForGetSetValueMethods(KtExpression delegateExpression, KotlinType delegateType, BindingTrace trace) {
        ExpressionReceiver.Companion companion = ExpressionReceiver.INSTANCE;
        BindingContext context = trace.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
        return companion.create(delegateExpression, delegateType, context);
    }

    private final ExpressionTypingContext createContextForGetSetValueMethods(VariableDescriptorWithAccessors propertyDescriptor, LexicalScope scopeForDelegate, DataFlowInfo dataFlowInfo, BindingTrace trace, InferenceSession inferenceExtension) {
        LexicalScope lexicalScopeMakeScopeForDelegateConventionFunctions = ScopeUtils.makeScopeForDelegateConventionFunctions(scopeForDelegate, propertyDescriptor);
        Intrinsics.checkNotNullExpressionValue(lexicalScopeMakeScopeForDelegateConventionFunctions, "makeScopeForDelegateConventionFunctions(...)");
        ExpressionTypingContext expressionTypingContextNewContext = ExpressionTypingContext.newContext(trace, lexicalScopeMakeScopeForDelegateConventionFunctions, dataFlowInfo, TypeUtils.NO_EXPECTED_TYPE, this.languageVersionSettings, this.dataFlowValueFactory, inferenceExtension);
        Intrinsics.checkNotNullExpressionValue(expressionTypingContextNewContext, "newContext(...)");
        return expressionTypingContextNewContext;
    }

    private final ExpressionTypingContext createContextForProvideDelegateMethod(LexicalScope scopeForDelegate, DataFlowInfo dataFlowInfo, BindingTrace trace, InferenceSession inferenceExtension) {
        ExpressionTypingContext expressionTypingContextNewContext = ExpressionTypingContext.newContext(trace, scopeForDelegate, dataFlowInfo, TypeUtils.NO_EXPECTED_TYPE, ContextDependency.INDEPENDENT, StatementFilter.NONE, this.languageVersionSettings, this.dataFlowValueFactory, inferenceExtension);
        Intrinsics.checkNotNullExpressionValue(expressionTypingContextNewContext, "newContext(...)");
        return expressionTypingContextNewContext;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final OverloadResolutionResults<FunctionDescriptor> getProvideDelegateMethod(VariableDescriptorWithAccessors propertyDescriptor, KtExpression delegateExpression, KotlinType delegateExpressionType, BindingTrace trace, LexicalScope initializerScope, DataFlowInfo dataFlowInfo, InferenceSession inferenceSession) {
        ExpressionTypingContext expressionTypingContextNewContext = ExpressionTypingContext.newContext(trace, initializerScope, dataFlowInfo, TypeUtils.NO_EXPECTED_TYPE, this.languageVersionSettings, this.dataFlowValueFactory, inferenceSession);
        Intrinsics.checkNotNullExpressionValue(expressionTypingContextNewContext, "newContext(...)");
        return getProvideDelegateMethod(propertyDescriptor, delegateExpression, delegateExpressionType, expressionTypingContextNewContext);
    }

    private final OverloadResolutionResults<FunctionDescriptor> getProvideDelegateMethod(VariableDescriptorWithAccessors propertyDescriptor, KtExpression delegateExpression, KotlinType delegateExpressionType, ExpressionTypingContext context) {
        boolean z = propertyDescriptor.mo6753getDispatchReceiverParameter() != null;
        Project project = delegateExpression.getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        KtPsiFactory ktPsiFactory = new KtPsiFactory(project, false);
        KtExpression[] ktExpressionArr = new KtExpression[2];
        ktExpressionArr[0] = ktPsiFactory.createExpression(z ? "this" : "null");
        ktExpressionArr[1] = createExpressionForProperty(ktPsiFactory);
        List listListOf = CollectionsKt.listOf((Object[]) ktExpressionArr);
        Name name2 = OperatorNameConventions.PROVIDE_DELEGATE;
        ExpressionReceiver.Companion companion = ExpressionReceiver.INSTANCE;
        BindingContext context2 = context.trace.getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getBindingContext(...)");
        Pair pairMakeAndResolveFakeCallInContext$default = FakeCallResolver.makeAndResolveFakeCallInContext$default(this.fakeCallResolver, companion.create(delegateExpression, delegateExpressionType, context2), context, listListOf, name2, delegateExpression, null, null, 96, null);
        Call call = (Call) pairMakeAndResolveFakeCallInContext$default.component1();
        OverloadResolutionResults<FunctionDescriptor> overloadResolutionResults = (OverloadResolutionResults) pairMakeAndResolveFakeCallInContext$default.component2();
        if (overloadResolutionResults.isSingleResult()) {
            context.trace.record(BindingContext.DELEGATE_EXPRESSION_TO_PROVIDE_DELEGATE_CALL, delegateExpression, call);
        }
        context.trace.record(BindingContext.PROVIDE_DELEGATE_CALL, propertyDescriptor, call);
        return overloadResolutionResults;
    }

    private final String renderCall(Call call, final BindingContext context) {
        KtExpression calleeExpression = call.getCalleeExpression();
        if (calleeExpression == null) {
            throw new AssertionError("CalleeExpression should exists for fake call of convention method");
        }
        List<? extends ValueArgument> valueArguments = call.getValueArguments();
        String str = calleeExpression.getText() + '(';
        Intrinsics.checkNotNull(valueArguments);
        return CollectionsKt.joinToString$default(valueArguments, ", ", str, Constant.AFTER_QUTO, 0, null, new Function1<ValueArgument, CharSequence>() { // from class: org.jetbrains.kotlin.resolve.DelegatedPropertyResolver.renderCall.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(ValueArgument valueArgument) {
                BindingContext bindingContext = context;
                KtExpression argumentExpression = valueArgument.getArgumentExpression();
                Intrinsics.checkNotNull(argumentExpression);
                KotlinType type = bindingContext.getType(argumentExpression);
                Intrinsics.checkNotNull(type);
                return DescriptorRenderer.SHORT_NAMES_IN_TYPES.renderType(type);
            }
        }, 24, null);
    }

    public final KotlinType resolveDelegateExpression(KtExpression delegateExpression, KtProperty property, VariableDescriptorWithAccessors variableDescriptor, LexicalScope scopeForDelegate, BindingTrace trace, DataFlowInfo dataFlowInfo, InferenceSession inferenceSession) {
        Intrinsics.checkNotNullParameter(delegateExpression, "delegateExpression");
        Intrinsics.checkNotNullParameter(property, "property");
        Intrinsics.checkNotNullParameter(variableDescriptor, "variableDescriptor");
        Intrinsics.checkNotNullParameter(scopeForDelegate, "scopeForDelegate");
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(dataFlowInfo, "dataFlowInfo");
        Intrinsics.checkNotNullParameter(inferenceSession, "inferenceSession");
        KotlinType type = property.mo7396getTypeReference() != null ? variableDescriptor.getType() : TypeUtils.NO_EXPECTED_TYPE;
        Intrinsics.checkNotNull(type);
        KotlinType kotlinTypeResolveWithNewInference = resolveWithNewInference(delegateExpression, variableDescriptor, scopeForDelegate, trace, dataFlowInfo, inferenceSession);
        if (kotlinTypeResolveWithNewInference != null) {
            return kotlinTypeResolveWithNewInference;
        }
        TemporaryBindingTrace temporaryBindingTraceCreate = TemporaryBindingTrace.create(trace, "Trace to resolve delegated property");
        Intrinsics.checkNotNullExpressionValue(temporaryBindingTraceCreate, "create(...)");
        ConstraintSystemCompleterImpl constraintSystemCompleterImpl = new ConstraintSystemCompleterImpl(this, property, type, variableDescriptor, delegateExpression, scopeForDelegate, trace, dataFlowInfo);
        KtExpression calleeExpressionIfAny = PsiUtilsKt.getCalleeExpressionIfAny(delegateExpression);
        if (calleeExpressionIfAny != null) {
            WritableSlice<KtElement, ConstraintSystemCompleter> CONSTRAINT_SYSTEM_COMPLETER = BindingContext.CONSTRAINT_SYSTEM_COMPLETER;
            Intrinsics.checkNotNullExpressionValue(CONSTRAINT_SYSTEM_COMPLETER, "CONSTRAINT_SYSTEM_COMPLETER");
            temporaryBindingTraceCreate.record(CONSTRAINT_SYSTEM_COMPLETER, calleeExpressionIfAny, constraintSystemCompleterImpl);
        }
        KotlinType kotlinTypeSafeGetType = this.expressionTypingServices.safeGetType(scopeForDelegate, delegateExpression, TypeUtils.NO_EXPECTED_TYPE, dataFlowInfo, inferenceSession, temporaryBindingTraceCreate);
        Intrinsics.checkNotNullExpressionValue(kotlinTypeSafeGetType, "safeGetType(...)");
        temporaryBindingTraceCreate.commit(new TraceEntryFilter() { // from class: org.jetbrains.kotlin.resolve.DelegatedPropertyResolver$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.resolve.TraceEntryFilter
            public final boolean accept(WritableSlice writableSlice, Object obj) {
                return DelegatedPropertyResolver.resolveDelegateExpression$lambda$6(writableSlice, obj);
            }
        }, true);
        return kotlinTypeSafeGetType;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean resolveDelegateExpression$lambda$6(WritableSlice writableSlice, Object obj) {
        return writableSlice != BindingContext.CONSTRAINT_SYSTEM_COMPLETER;
    }

    private final void completeNotComputedDelegateType(BindingTrace trace, TemporaryBindingTrace traceToResolveDelegatedProperty) {
        Object next;
        Diagnostic diagnostic;
        Diagnostics diagnostics = traceToResolveDelegatedProperty.getContext().getDiagnostics();
        Intrinsics.checkNotNullExpressionValue(diagnostics, "getDiagnostics(...)");
        Iterator<T> it = diagnostics.iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            diagnostic = (Diagnostic) next;
            if (Intrinsics.areEqual(diagnostic.getFactory(), Errors.TYPECHECKER_HAS_RUN_INTO_RECURSIVE_PROBLEM.getErrorFactory())) {
                break;
            }
        } while (!Intrinsics.areEqual(diagnostic.getFactory(), Errors.TYPECHECKER_HAS_RUN_INTO_RECURSIVE_PROBLEM.getWarningFactory()));
        Diagnostic diagnostic2 = (Diagnostic) next;
        if (diagnostic2 != null) {
            DiagnosticFactoryForDeprecation0<KtExpression> diagnosticFactoryForDeprecation0 = Errors.TYPECHECKER_HAS_RUN_INTO_RECURSIVE_PROBLEM;
            LanguageVersionSettings languageVersionSettings = this.languageVersionSettings;
            PsiElement psiElement = diagnostic2.getPsiElement();
            Intrinsics.checkNotNull(psiElement, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtExpression");
            trace.report(diagnosticFactoryForDeprecation0.on(languageVersionSettings, (KtExpression) psiElement));
        }
    }

    private final KotlinType resolveWithNewInference(KtExpression delegateExpression, VariableDescriptorWithAccessors variableDescriptor, LexicalScope scopeForDelegate, BindingTrace trace, DataFlowInfo dataFlowInfo, InferenceSession inferenceSession) {
        EmptySubstitutor newTypeSubstitutorByConstructorMap;
        if (!this.languageVersionSettings.supportsFeature(LanguageFeature.NewInference)) {
            return null;
        }
        KotlinType type = trace.getType(delegateExpression);
        if (type != null) {
            return type;
        }
        TemporaryBindingTrace temporaryBindingTraceCreate = TemporaryBindingTrace.create(trace, "Trace to resolve delegated property");
        Intrinsics.checkNotNullExpressionValue(temporaryBindingTraceCreate, "create(...)");
        TemporaryBindingTrace temporaryBindingTrace = temporaryBindingTraceCreate;
        KotlinTypeInfo typeInfo = this.expressionTypingServices.getTypeInfo(scopeForDelegate, delegateExpression, TypeUtils.NO_EXPECTED_TYPE, dataFlowInfo, inferenceSession, temporaryBindingTrace, false, delegateExpression, ContextDependency.DEPENDENT);
        Intrinsics.checkNotNullExpressionValue(typeInfo, "getTypeInfo(...)");
        UnwrappedType type2 = typeInfo.getType();
        if (type2 == null) {
            completeNotComputedDelegateType(trace, temporaryBindingTraceCreate);
            return null;
        }
        DataFlowInfo dataFlowInfo2 = typeInfo.getDataFlowInfo();
        TypeConstructor constructor = type2.getConstructor();
        if (constructor instanceof IntegerLiteralTypeConstructor) {
            type2 = ((IntegerLiteralTypeConstructor) constructor).getApproximatedType();
        }
        KotlinType kotlinType = isProperType(type2) ? type2 : null;
        if (this.languageVersionSettings.supportsFeature(LanguageFeature.OperatorProvideDelegate)) {
            TemporaryBindingTrace temporaryBindingTraceCreate2 = TemporaryBindingTrace.create(temporaryBindingTrace, "Trace to resolve provide delegate");
            Intrinsics.checkNotNullExpressionValue(temporaryBindingTraceCreate2, "create(...)");
            Map<UnwrappedType, UnwrappedType> mapBuildSubstitutionMapOfNonFixedVariables = buildSubstitutionMapOfNonFixedVariables(type2);
            if (mapBuildSubstitutionMapOfNonFixedVariables == null) {
                newTypeSubstitutorByConstructorMap = EmptySubstitutor.INSTANCE;
            } else {
                LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt.mapCapacity(mapBuildSubstitutionMapOfNonFixedVariables.size()));
                Iterator<T> it = mapBuildSubstitutionMapOfNonFixedVariables.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry entry = (Map.Entry) it.next();
                    linkedHashMap.put(((UnwrappedType) entry.getKey()).getConstructor(), entry.getValue());
                }
                newTypeSubstitutorByConstructorMap = new NewTypeSubstitutorByConstructorMap(linkedHashMap);
            }
            OverloadResolutionResults<FunctionDescriptor> provideDelegateMethod = getProvideDelegateMethod(variableDescriptor, delegateExpression, newTypeSubstitutorByConstructorMap.safeSubstitute(type2.unwrap()), createContextForProvideDelegateMethod(scopeForDelegate, dataFlowInfo2, temporaryBindingTraceCreate2, new InferenceSessionForExistingCandidates(mapBuildSubstitutionMapOfNonFixedVariables != null, inferenceSession)));
            if (provideDelegateMethod.isSuccess()) {
                CallableDescriptor callableDescriptorMo7437getResultingDescriptor = provideDelegateMethod.mo7437getResultingDescriptor();
                Intrinsics.checkNotNullExpressionValue(callableDescriptorMo7437getResultingDescriptor, "getResultingDescriptor(...)");
                FunctionDescriptor functionDescriptor = (FunctionDescriptor) callableDescriptorMo7437getResultingDescriptor;
                if (functionDescriptor.isOperator()) {
                    UnwrappedType unwrappedTypeInverseSubstitution = inverseSubstitution(functionDescriptor.getReturnType(), mapBuildSubstitutionMapOfNonFixedVariables);
                    if (unwrappedTypeInverseSubstitution == null) {
                        return null;
                    }
                    type2 = unwrappedTypeInverseSubstitution;
                    dataFlowInfo2 = provideDelegateMethod.mo7436getResultingCall().getDataFlowInfoForArguments().getResultInfo();
                    Intrinsics.checkNotNullExpressionValue(dataFlowInfo2, "getResultInfo(...)");
                }
                if (mapBuildSubstitutionMapOfNonFixedVariables == null) {
                    WritableSlice<VariableDescriptorWithAccessors, ResolvedCall<FunctionDescriptor>> PROVIDE_DELEGATE_RESOLVED_CALL = BindingContext.PROVIDE_DELEGATE_RESOLVED_CALL;
                    Intrinsics.checkNotNullExpressionValue(PROVIDE_DELEGATE_RESOLVED_CALL, "PROVIDE_DELEGATE_RESOLVED_CALL");
                    temporaryBindingTraceCreate2.record(PROVIDE_DELEGATE_RESOLVED_CALL, variableDescriptor, provideDelegateMethod.mo7436getResultingCall());
                    temporaryBindingTraceCreate2.commit();
                }
            }
        }
        return inferDelegateTypeFromGetSetValueMethods(delegateExpression, variableDescriptor, scopeForDelegate, temporaryBindingTraceCreate, type2, kotlinType, dataFlowInfo2, inferenceSession);
    }

    private final UnwrappedType inverseSubstitution(KotlinType type, Map<UnwrappedType, ? extends UnwrappedType> substitutionMap) {
        if (type == null) {
            return null;
        }
        if (substitutionMap == null) {
            return type.unwrap();
        }
        HashMap map = new HashMap();
        for (Map.Entry<UnwrappedType, ? extends UnwrappedType> entry : substitutionMap.entrySet()) {
            map.put(entry.getValue().getConstructor(), entry.getKey());
        }
        return new NewTypeSubstitutorByConstructorMap(map).safeSubstitute(type.unwrap());
    }

    private final Map<UnwrappedType, UnwrappedType> buildSubstitutionMapOfNonFixedVariables(KotlinType type) {
        if (type.getConstructor() instanceof NewTypeVariableConstructor) {
            return null;
        }
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        TypeUtilsKt.contains(type, new Function1<UnwrappedType, Boolean>() { // from class: org.jetbrains.kotlin.resolve.DelegatedPropertyResolver.buildSubstitutionMapOfNonFixedVariables.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            /* JADX WARN: Type inference failed for: r2v1, types: [T, java.util.HashMap] */
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(UnwrappedType innerType) {
                Intrinsics.checkNotNullParameter(innerType, "innerType");
                TypeConstructor constructor = innerType.getConstructor();
                if (constructor instanceof NewTypeVariableConstructor) {
                    if (objectRef.element == null) {
                        objectRef.element = new HashMap();
                    }
                    Map<UnwrappedType, UnwrappedType> map = objectRef.element;
                    Intrinsics.checkNotNull(map);
                    if (!map.containsKey(innerType)) {
                        Map<UnwrappedType, UnwrappedType> map2 = objectRef.element;
                        Intrinsics.checkNotNull(map2);
                        map2.put(innerType, new StubTypeForProvideDelegateReceiver((NewTypeVariableConstructor) constructor, innerType.getIsMarkedNullable(), null, 4, null));
                    }
                }
                return false;
            }
        });
        return (Map) objectRef.element;
    }

    private final UnwrappedType inferDelegateTypeFromGetSetValueMethods(KtExpression delegateExpression, VariableDescriptorWithAccessors variableDescriptor, LexicalScope scopeForDelegate, TemporaryBindingTrace trace, KotlinType delegateType, KotlinType delegateTypeForProperType, DataFlowInfo delegateDataFlow, InferenceSession inferenceSession) {
        Object next;
        ResolvedCallAtom resolvedCallAtomResultCallAtom;
        KotlinCall atom;
        DelegateInferenceSession delegateInferenceSession = new DelegateInferenceSession(variableDescriptor, !(variableDescriptor.getType() instanceof DeferredType) ? variableDescriptor.getType().unwrap() : null, this.psiCallResolver, this.postponedArgumentsAnalyzer, this.kotlinConstraintSystemCompleter, this.callComponents, this.builtIns, inferenceSession);
        TemporaryBindingTrace temporaryBindingTrace = trace;
        ExpressionReceiver expressionReceiverCreateReceiverForGetSetValueMethods = createReceiverForGetSetValueMethods(delegateExpression, delegateType, temporaryBindingTrace);
        DelegateInferenceSession delegateInferenceSession2 = delegateInferenceSession;
        ExpressionTypingContext expressionTypingContextCreateContextForGetSetValueMethods = createContextForGetSetValueMethods(variableDescriptor, scopeForDelegate, delegateDataFlow, temporaryBindingTrace, delegateInferenceSession2);
        getGetSetValueMethod(variableDescriptor, delegateExpression, delegateType, temporaryBindingTrace, scopeForDelegate, delegateDataFlow, true, true, expressionReceiverCreateReceiverForGetSetValueMethods, expressionTypingContextCreateContextForGetSetValueMethods);
        if (variableDescriptor.isVar() && !(variableDescriptor.getReturnType() instanceof DeferredType)) {
            getGetSetValueMethod(variableDescriptor, delegateExpression, delegateType, temporaryBindingTrace, scopeForDelegate, delegateDataFlow, false, true, expressionReceiverCreateReceiverForGetSetValueMethods, expressionTypingContextCreateContextForGetSetValueMethods);
        }
        Call callMakeCall = CallMaker.makeCall(delegateExpression, expressionReceiverCreateReceiverForGetSetValueMethods);
        Intrinsics.checkNotNullExpressionValue(callMakeCall, "makeCall(...)");
        BasicCallResolutionContext basicCallResolutionContextCreate = BasicCallResolutionContext.create(expressionTypingContextCreateContextForGetSetValueMethods, callMakeCall, CheckArgumentTypesMode.CHECK_VALUE_ARGUMENTS);
        Intrinsics.checkNotNullExpressionValue(basicCallResolutionContextCreate, "create(...)");
        List<ResolutionResultCallInfo<FunctionDescriptor>> listResolveCandidates = delegateInferenceSession.resolveCandidates(this.psiCallResolver.createResolutionCallbacks(temporaryBindingTrace, delegateInferenceSession2, basicCallResolutionContextCreate));
        for (Pair pair : CollectionsKt.listOf((Object[]) new Pair[]{TuplesKt.to(OperatorNameConventions.GET_VALUE, true), TuplesKt.to(OperatorNameConventions.SET_VALUE, false)})) {
            Name name2 = (Name) pair.component1();
            boolean zBooleanValue = ((Boolean) pair.component2()).booleanValue();
            Iterator<T> it = listResolveCandidates.iterator();
            do {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
                resolvedCallAtomResultCallAtom = ResolutionAtomsKt.resultCallAtom(((ResolutionResultCallInfo) next).getResolutionResult());
            } while (!Intrinsics.areEqual((resolvedCallAtomResultCallAtom == null || (atom = resolvedCallAtomResultCallAtom.getAtom()) == null) ? null : atom.getName(), name2));
            ResolutionResultCallInfo resolutionResultCallInfo = (ResolutionResultCallInfo) next;
            if (resolutionResultCallInfo != null) {
                inferDelegateTypeFromGetSetValueMethods$recordResolvedDelegateOrReportError(variableDescriptor, this, trace, delegateExpression, delegateType, resolutionResultCallInfo.getOverloadResolutionResults(), zBooleanValue);
            }
        }
        KotlinType kotlinTypeExtractResolvedDelegateType = extractResolvedDelegateType(delegateExpression, temporaryBindingTrace, delegateType);
        trace.recordType(delegateExpression, delegateTypeForProperType == null ? kotlinTypeExtractResolvedDelegateType : delegateTypeForProperType);
        trace.commit();
        return kotlinTypeExtractResolvedDelegateType.unwrap();
    }

    private static final void inferDelegateTypeFromGetSetValueMethods$recordResolvedDelegateOrReportError(VariableDescriptorWithAccessors variableDescriptorWithAccessors, DelegatedPropertyResolver delegatedPropertyResolver, TemporaryBindingTrace temporaryBindingTrace, KtExpression ktExpression, KotlinType kotlinType, OverloadResolutionResults<FunctionDescriptor> overloadResolutionResults, boolean z) {
        VariableAccessorDescriptor setter;
        if (z) {
            setter = variableDescriptorWithAccessors.getGetter();
        } else if (!z) {
            setter = variableDescriptorWithAccessors.getSetter();
        } else {
            throw new NoWhenBranchMatchedException();
        }
        VariableAccessorDescriptor variableAccessorDescriptor = setter;
        if (variableAccessorDescriptor == null) {
            throw new IllegalArgumentException(("Delegated property should have getter/setter " + variableDescriptorWithAccessors + StringUtil.SPACE + ktExpression.getText()).toString());
        }
        if (overloadResolutionResults.isSuccess()) {
            delegatedPropertyResolver.recordDelegateOperatorResults(overloadResolutionResults, variableDescriptorWithAccessors, variableAccessorDescriptor, temporaryBindingTrace);
        } else {
            delegatedPropertyResolver.reportGetSetValueResolutionError(overloadResolutionResults, variableAccessorDescriptor, ktExpression, kotlinType, temporaryBindingTrace, z);
        }
    }

    private final KotlinType extractResolvedDelegateType(KtExpression delegateExpression, BindingTrace trace, KotlinType delegateType) {
        CallableDescriptor resultingDescriptor;
        BindingContext context = trace.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
        Call call = CallUtilKt.getCall(delegateExpression, context);
        BindingContext context2 = trace.getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getBindingContext(...)");
        ResolvedCall<? extends CallableDescriptor> resolvedCall = CallUtilKt.getResolvedCall(call, context2);
        KotlinType returnType = (resolvedCall == null || (resultingDescriptor = resolvedCall.getResultingDescriptor()) == null) ? null : resultingDescriptor.getReturnType();
        if (returnType != null) {
            if (!isProperType(returnType)) {
                returnType = null;
            }
            if (returnType != null) {
                return returnType;
            }
        }
        if (!isProperType(delegateType)) {
            delegateType = null;
        }
        if (delegateType != null) {
            return delegateType;
        }
        ErrorTypeKind errorTypeKind = ErrorTypeKind.TYPE_FOR_DELEGATION;
        String text = delegateExpression.getText();
        Intrinsics.checkNotNullExpressionValue(text, "getText(...)");
        return ErrorUtils.createErrorType(errorTypeKind, text);
    }

    private final boolean isProperType(KotlinType kotlinType) {
        return !TypeUtilsKt.contains(kotlinType, new Function1<UnwrappedType, Boolean>() { // from class: org.jetbrains.kotlin.resolve.DelegatedPropertyResolver.isProperType.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(UnwrappedType it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(it.getConstructor() instanceof TypeVariableTypeConstructor);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean conventionMethodFound(OverloadResolutionResults<FunctionDescriptor> results) {
        return results.isSuccess() || (results.isSingleResult() && results.getResultCode() == OverloadResolutionResults.Code.SINGLE_CANDIDATE_ARGUMENT_MISMATCH);
    }

    /* JADX INFO: compiled from: DelegatedPropertyResolver.kt */
    @Metadata(d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B=\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f¢\u0006\u0002\u0010\u0010J*\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$2\b\b\u0002\u0010%\u001a\u00020&H\u0002J\u001c\u0010'\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 2\n\u0010(\u001a\u0006\u0012\u0002\b\u00030)H\u0016J(\u0010*\u001a\u00020\u00052\u0006\u0010+\u001a\u00020\u00052\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"2\u0006\u0010,\u001a\u00020-H\u0002R\u0011\u0010\u000e\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\f\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001c¨\u0006."}, d2 = {"Lorg/jetbrains/kotlin/resolve/DelegatedPropertyResolver$ConstraintSystemCompleterImpl;", "Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystemCompleter;", "property", "Lorg/jetbrains/kotlin/psi/KtProperty;", "expectedType", "Lorg/jetbrains/kotlin/types/KotlinType;", "variableDescriptor", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptorWithAccessors;", "delegateExpression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "scopeForDelegate", "Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "dataFlowInfo", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;", "(Lorg/jetbrains/kotlin/resolve/DelegatedPropertyResolver;Lorg/jetbrains/kotlin/psi/KtProperty;Lorg/jetbrains/kotlin/types/KotlinType;Lorg/jetbrains/kotlin/descriptors/VariableDescriptorWithAccessors;Lorg/jetbrains/kotlin/psi/KtExpression;Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;Lorg/jetbrains/kotlin/resolve/BindingTrace;Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;)V", "getDataFlowInfo", "()Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;", "getDelegateExpression", "()Lorg/jetbrains/kotlin/psi/KtExpression;", "getExpectedType", "()Lorg/jetbrains/kotlin/types/KotlinType;", "getProperty", "()Lorg/jetbrains/kotlin/psi/KtProperty;", "getTrace", "()Lorg/jetbrains/kotlin/resolve/BindingTrace;", "getVariableDescriptor", "()Lorg/jetbrains/kotlin/descriptors/VariableDescriptorWithAccessors;", "addConstraintForThisValue", "", "constraintSystem", "Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystem$Builder;", "typeVariableSubstitutor", "Lorg/jetbrains/kotlin/types/TypeSubstitutor;", "resultingDescriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "dispatchReceiverOnly", "", "completeConstraintSystem", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "getDelegateType", "byExpressionType", "traceToResolveConventionMethods", "Lorg/jetbrains/kotlin/resolve/TemporaryBindingTrace;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public final class ConstraintSystemCompleterImpl implements ConstraintSystemCompleter {
        private final DataFlowInfo dataFlowInfo;
        private final KtExpression delegateExpression;
        private final KotlinType expectedType;
        private final KtProperty property;
        private final LexicalScope scopeForDelegate;
        final /* synthetic */ DelegatedPropertyResolver this$0;
        private final BindingTrace trace;
        private final VariableDescriptorWithAccessors variableDescriptor;

        public ConstraintSystemCompleterImpl(DelegatedPropertyResolver delegatedPropertyResolver, KtProperty property, KotlinType expectedType, VariableDescriptorWithAccessors variableDescriptor, KtExpression delegateExpression, LexicalScope scopeForDelegate, BindingTrace trace, DataFlowInfo dataFlowInfo) {
            Intrinsics.checkNotNullParameter(property, "property");
            Intrinsics.checkNotNullParameter(expectedType, "expectedType");
            Intrinsics.checkNotNullParameter(variableDescriptor, "variableDescriptor");
            Intrinsics.checkNotNullParameter(delegateExpression, "delegateExpression");
            Intrinsics.checkNotNullParameter(scopeForDelegate, "scopeForDelegate");
            Intrinsics.checkNotNullParameter(trace, "trace");
            Intrinsics.checkNotNullParameter(dataFlowInfo, "dataFlowInfo");
            this.this$0 = delegatedPropertyResolver;
            this.property = property;
            this.expectedType = expectedType;
            this.variableDescriptor = variableDescriptor;
            this.delegateExpression = delegateExpression;
            this.scopeForDelegate = scopeForDelegate;
            this.trace = trace;
            this.dataFlowInfo = dataFlowInfo;
        }

        public final KtProperty getProperty() {
            return this.property;
        }

        public final KotlinType getExpectedType() {
            return this.expectedType;
        }

        public final VariableDescriptorWithAccessors getVariableDescriptor() {
            return this.variableDescriptor;
        }

        public final KtExpression getDelegateExpression() {
            return this.delegateExpression;
        }

        public final BindingTrace getTrace() {
            return this.trace;
        }

        public final DataFlowInfo getDataFlowInfo() {
            return this.dataFlowInfo;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemCompleter
        public void completeConstraintSystem(ConstraintSystem.Builder constraintSystem, ResolvedCall<?> resolvedCall) {
            KotlinType kotlinTypeSubstitute;
            Intrinsics.checkNotNullParameter(constraintSystem, "constraintSystem");
            Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
            KotlinType returnType = resolvedCall.get$suspendPropertyDescriptor().getReturnType();
            if (returnType == null) {
                return;
            }
            Map<CallHandle, TypeSubstitutor> typeVariableSubstitutors = constraintSystem.getTypeVariableSubstitutors();
            Call call = resolvedCall.getCall();
            Intrinsics.checkNotNullExpressionValue(call, "getCall(...)");
            TypeSubstitutor typeSubstitutor = typeVariableSubstitutors.get(TypeVariableKt.toHandle(call));
            if (typeSubstitutor == null) {
                throw new AssertionError("No substitutor in the system for call: " + resolvedCall.getCall());
            }
            TemporaryBindingTrace temporaryBindingTraceCreate = TemporaryBindingTrace.create(this.trace, "Trace to resolve delegated property convention methods");
            Intrinsics.checkNotNullExpressionValue(temporaryBindingTraceCreate, "create(...)");
            KotlinType delegateType = getDelegateType(returnType, constraintSystem, typeSubstitutor, temporaryBindingTraceCreate);
            TemporaryBindingTrace temporaryBindingTrace = temporaryBindingTraceCreate;
            OverloadResolutionResults getSetValueMethod$default = DelegatedPropertyResolver.getGetSetValueMethod$default(this.this$0, this.variableDescriptor, this.delegateExpression, delegateType, temporaryBindingTrace, this.scopeForDelegate, this.dataFlowInfo, true, false, null, null, 768, null);
            if (this.this$0.conventionMethodFound(getSetValueMethod$default)) {
                CallableDescriptor callableDescriptorMo7437getResultingDescriptor = getSetValueMethod$default.mo7437getResultingDescriptor();
                Intrinsics.checkNotNullExpressionValue(callableDescriptorMo7437getResultingDescriptor, "getResultingDescriptor(...)");
                FunctionDescriptor functionDescriptor = (FunctionDescriptor) callableDescriptorMo7437getResultingDescriptor;
                KotlinType returnType2 = functionDescriptor.getReturnType();
                if (returnType2 != null && !TypeUtils.noExpectedType(this.expectedType) && (kotlinTypeSubstitute = typeSubstitutor.substitute(returnType2, Variance.INVARIANT)) != null) {
                    constraintSystem.addSubtypeConstraint(kotlinTypeSubstitute, this.expectedType, ConstraintPositionKind.FROM_COMPLETER.position());
                }
                addConstraintForThisValue$default(this, constraintSystem, typeSubstitutor, functionDescriptor, false, 8, null);
            }
            if (this.variableDescriptor.isVar() && !(this.variableDescriptor.getReturnType() instanceof DeferredType)) {
                OverloadResolutionResults getSetValueMethod$default2 = DelegatedPropertyResolver.getGetSetValueMethod$default(this.this$0, this.variableDescriptor, this.delegateExpression, delegateType, temporaryBindingTrace, this.scopeForDelegate, this.dataFlowInfo, false, false, null, null, 768, null);
                if (this.this$0.conventionMethodFound(getSetValueMethod$default2)) {
                    CallableDescriptor callableDescriptorMo7437getResultingDescriptor2 = getSetValueMethod$default2.mo7437getResultingDescriptor();
                    Intrinsics.checkNotNullExpressionValue(callableDescriptorMo7437getResultingDescriptor2, "getResultingDescriptor(...)");
                    FunctionDescriptor functionDescriptor2 = (FunctionDescriptor) callableDescriptorMo7437getResultingDescriptor2;
                    List<ValueParameterDescriptor> valueParameters = functionDescriptor2.getValueParameters();
                    Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
                    if (valueParameters.size() == 3) {
                        if (!TypeUtils.noExpectedType(this.expectedType)) {
                            KotlinType type = valueParameters.get(2).getType();
                            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                            constraintSystem.addSubtypeConstraint(this.expectedType, typeSubstitutor.substitute(type, Variance.INVARIANT), ConstraintPositionKind.FROM_COMPLETER.position());
                        }
                        addConstraintForThisValue$default(this, constraintSystem, typeSubstitutor, functionDescriptor2, false, 8, null);
                    }
                }
            }
        }

        private final KotlinType getDelegateType(KotlinType byExpressionType, ConstraintSystem.Builder constraintSystem, TypeSubstitutor typeVariableSubstitutor, TemporaryBindingTrace traceToResolveConventionMethods) {
            if (this.this$0.isOperatorProvideDelegateSupported()) {
                OverloadResolutionResults provideDelegateMethod = this.this$0.getProvideDelegateMethod(this.variableDescriptor, this.delegateExpression, byExpressionType, traceToResolveConventionMethods, this.scopeForDelegate, this.dataFlowInfo, null);
                if (this.this$0.conventionMethodFound(provideDelegateMethod)) {
                    CallableDescriptor callableDescriptorMo7437getResultingDescriptor = provideDelegateMethod.mo7437getResultingDescriptor();
                    Intrinsics.checkNotNullExpressionValue(callableDescriptorMo7437getResultingDescriptor, "getResultingDescriptor(...)");
                    FunctionDescriptor functionDescriptor = (FunctionDescriptor) callableDescriptorMo7437getResultingDescriptor;
                    KotlinType returnType = functionDescriptor.getReturnType();
                    if (functionDescriptor.isOperator()) {
                        addConstraintForThisValue(constraintSystem, typeVariableSubstitutor, functionDescriptor, true);
                        if (returnType != null) {
                            return returnType;
                        }
                        throw new AssertionError("No return type fore 'provideDelegate' of " + this.delegateExpression.getText());
                    }
                }
            }
            return byExpressionType;
        }

        static /* synthetic */ void addConstraintForThisValue$default(ConstraintSystemCompleterImpl constraintSystemCompleterImpl, ConstraintSystem.Builder builder, TypeSubstitutor typeSubstitutor, FunctionDescriptor functionDescriptor, boolean z, int i, Object obj) {
            if ((i & 8) != 0) {
                z = false;
            }
            constraintSystemCompleterImpl.addConstraintForThisValue(builder, typeSubstitutor, functionDescriptor, z);
        }

        private final void addConstraintForThisValue(ConstraintSystem.Builder constraintSystem, TypeSubstitutor typeVariableSubstitutor, FunctionDescriptor resultingDescriptor, boolean dispatchReceiverOnly) {
            KotlinType type;
            ReceiverParameterDescriptor extensionReceiverParameter = this.variableDescriptor.getExtensionReceiverParameter();
            ReceiverParameterDescriptor dispatchReceiverParameter = this.variableDescriptor.mo6753getDispatchReceiverParameter();
            SimpleType type2 = null;
            if (dispatchReceiverOnly) {
                if (dispatchReceiverParameter != null) {
                    type2 = dispatchReceiverParameter.getType();
                }
            } else if (extensionReceiverParameter != null && (type = extensionReceiverParameter.getType()) != null) {
                type2 = type;
            } else if (dispatchReceiverParameter != null) {
                type2 = dispatchReceiverParameter.getType();
            }
            if (type2 == null) {
                type2 = this.this$0.builtIns.getNullableNothingType();
            }
            Intrinsics.checkNotNull(type2);
            List<ValueParameterDescriptor> valueParameters = resultingDescriptor.getValueParameters();
            Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
            if (valueParameters.isEmpty()) {
                return;
            }
            constraintSystem.addSubtypeConstraint(type2, typeVariableSubstitutor.substitute(valueParameters.get(0).getType(), Variance.INVARIANT), ConstraintPositionKind.FROM_COMPLETER.position());
        }
    }
}
