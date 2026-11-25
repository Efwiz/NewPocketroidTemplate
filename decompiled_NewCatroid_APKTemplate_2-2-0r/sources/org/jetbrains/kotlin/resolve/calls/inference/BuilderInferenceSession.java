package org.jetbrains.kotlin.resolve.calls.inference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.Reflection;
import kotlin.ranges.RangesKt;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.KFunction;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiRecursiveElementWalkingVisitor;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.impl.AnonymousFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.impl.LocalVariableDescriptor;
import org.jetbrains.kotlin.descriptors.impl.SimpleFunctionDescriptorImpl;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtCallableReferenceExpression;
import org.jetbrains.kotlin.psi.KtClassLiteralExpression;
import org.jetbrains.kotlin.psi.KtDotQualifiedExpression;
import org.jetbrains.kotlin.psi.KtDoubleColonExpression;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtLambdaExpression;
import org.jetbrains.kotlin.psi.KtReferenceExpression;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.KtVariableDeclaration;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.DelegateInferenceSession;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.MissingSupertypesResolver;
import org.jetbrains.kotlin.resolve.TemporaryBindingTrace;
import org.jetbrains.kotlin.resolve.calls.ArgumentTypeResolver;
import org.jetbrains.kotlin.resolve.calls.components.ArgumentsUtilsKt;
import org.jetbrains.kotlin.resolve.calls.components.ClassicTypeSystemContextForCSKt;
import org.jetbrains.kotlin.resolve.calls.components.CompletedCallInfo;
import org.jetbrains.kotlin.resolve.calls.components.InferenceSession;
import org.jetbrains.kotlin.resolve.calls.components.PostponedArgumentsAnalyzer;
import org.jetbrains.kotlin.resolve.calls.components.candidate.ResolutionCandidate;
import org.jetbrains.kotlin.resolve.calls.context.BasicCallResolutionContext;
import org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintInjector;
import org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemCompletionMode;
import org.jetbrains.kotlin.resolve.calls.inference.components.EmptySubstitutor;
import org.jetbrains.kotlin.resolve.calls.inference.components.KotlinConstraintSystemCompleter;
import org.jetbrains.kotlin.resolve.calls.inference.components.NewTypeSubstitutor;
import org.jetbrains.kotlin.resolve.calls.inference.components.NewTypeSubstitutorByConstructorMap;
import org.jetbrains.kotlin.resolve.calls.inference.model.BuilderInferencePosition;
import org.jetbrains.kotlin.resolve.calls.inference.model.BuilderInferenceSubstitutionConstraintPositionImpl;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintKind;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintStorage;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintSystemError;
import org.jetbrains.kotlin.resolve.calls.inference.model.DeclaredUpperBoundConstraintPosition;
import org.jetbrains.kotlin.resolve.calls.inference.model.InitialConstraint;
import org.jetbrains.kotlin.resolve.calls.inference.model.InjectedAnotherStubTypeConstraintPositionImpl;
import org.jetbrains.kotlin.resolve.calls.inference.model.MutableVariableWithConstraints;
import org.jetbrains.kotlin.resolve.calls.inference.model.NewConstraintSystemImpl;
import org.jetbrains.kotlin.resolve.calls.inference.model.NewTypeVariable;
import org.jetbrains.kotlin.resolve.calls.inference.model.VariableWithConstraints;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCall;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallComponents;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallDiagnosticsKt;
import org.jetbrains.kotlin.resolve.calls.model.KotlinDiagnosticsHolder;
import org.jetbrains.kotlin.resolve.calls.model.LambdaKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.PostponedResolvedAtom;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedAtom;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCallAtom;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCallImpl;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedLambdaAtom;
import org.jetbrains.kotlin.resolve.calls.model.SimpleKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.SingleCallResolutionResult;
import org.jetbrains.kotlin.resolve.calls.model.SubKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValueFactory;
import org.jetbrains.kotlin.resolve.calls.tower.CallInfo;
import org.jetbrains.kotlin.resolve.calls.tower.KotlinToResolvedCallTransformer;
import org.jetbrains.kotlin.resolve.calls.tower.NewAbstractResolvedCall;
import org.jetbrains.kotlin.resolve.calls.tower.PSICallResolver;
import org.jetbrains.kotlin.resolve.calls.tower.PSICompletedCallInfo;
import org.jetbrains.kotlin.resolve.calls.tower.PSIKotlinCallImpl;
import org.jetbrains.kotlin.resolve.calls.tower.PSIKotlinCallsKt;
import org.jetbrains.kotlin.resolve.calls.tower.PSIPartialCallInfo;
import org.jetbrains.kotlin.resolve.calls.tower.ResolvedAtomCompleter;
import org.jetbrains.kotlin.resolve.calls.tower.StubTypesBasedInferenceSession;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.calls.util.FakeCallableDescriptorForObject;
import org.jetbrains.kotlin.resolve.deprecation.DeprecationResolver;
import org.jetbrains.kotlin.resolve.descriptorUtil.AnnotationsForResolveUtilsKt;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValueWithSmartCastInfo;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.StubTypeForBuilderInference;
import org.jetbrains.kotlin.types.TypeApproximator;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeConstructorSubstitution;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeSubstitutor;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;
import org.jetbrains.kotlin.types.checker.NewCapturedType;
import org.jetbrains.kotlin.types.checker.NewCapturedTypeConstructor;
import org.jetbrains.kotlin.types.expressions.DoubleColonExpressionResolver;
import org.jetbrains.kotlin.types.expressions.ExpressionTypingServices;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.MarkerExtensionsKt;
import org.jetbrains.kotlin.types.model.TypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeSubstitutorMarker;
import org.jetbrains.kotlin.types.model.TypeVariableMarker;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: BuilderInferenceSession.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000è\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0007\n\u0002\u0010\"\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\u0018\u0000 \u0097\u00012\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0002\u0097\u0001B\u0099\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00120\u0010\u0012\u0006\u0010\u0013\u001a\u00020\u0014\u0012\u0006\u0010\u0015\u001a\u00020\u0016\u0012\u0006\u0010\u0017\u001a\u00020\u0018\u0012\u0006\u0010\u0019\u001a\u00020\u001a\u0012\u0006\u0010\u001b\u001a\u00020\u001c\u0012\u0006\u0010\u001d\u001a\u00020\u001e\u0012\u0006\u0010\u001f\u001a\u00020 \u0012\u0006\u0010!\u001a\u00020\"\u0012\u0006\u0010#\u001a\u00020$\u0012\u0006\u0010%\u001a\u00020&¢\u0006\u0002\u0010'J\u0010\u00108\u001a\u0002092\u0006\u0010:\u001a\u00020;H\u0016J\u001e\u0010<\u001a\u0002092\u0006\u0010=\u001a\u00020-2\u0006\u0010>\u001a\u00020?2\u0006\u0010@\u001a\u00020?J\u000e\u0010A\u001a\u0002092\u0006\u0010B\u001a\u00020-J\u0010\u0010C\u001a\u0002012\u0006\u0010D\u001a\u00020\u0002H\u0002J\b\u0010E\u001a\u000201H\u0002J\u000e\u0010F\u001a\u0002092\u0006\u0010G\u001a\u00020HJ\u001e\u0010I\u001a\b\u0012\u0002\b\u0003\u0018\u00010J2\u0006\u0010:\u001a\u00020K2\u0006\u0010L\u001a\u00020MH\u0002J\u0016\u0010N\u001a\u0002092\u0006\u0010B\u001a\u00020O2\u0006\u0010P\u001a\u00020QJ\u0014\u0010R\u001a\u000e\u0012\u0004\u0012\u00020S\u0012\u0004\u0012\u00020T0\u0010H\u0002J\b\u0010U\u001a\u00020VH\u0002J\u0018\u0010W\u001a\u00020M2\u0006\u0010X\u001a\u00020Q2\u0006\u0010Y\u001a\u00020\u000eH\u0002J\b\u0010Z\u001a\u00020[H\u0016J\u001e\u0010\\\u001a\u0002092\u0006\u0010]\u001a\u00020?2\f\u0010^\u001a\b\u0012\u0004\u0012\u00020`0_H\u0002J\u001e\u0010a\u001a\b\u0012\u0004\u0012\u00020`0b2\u0006\u0010>\u001a\u00020?2\u0006\u0010@\u001a\u00020?H\u0002J\u000e\u0010c\u001a\b\u0012\u0004\u0012\u00020\u00000bH\u0002J\n\u0010d\u001a\u0004\u0018\u00010\u0000H\u0002J\b\u0010e\u001a\u00020\u0014H\u0002J\u0006\u0010f\u001a\u00020QJ\u000e\u0010g\u001a\b\u0012\u0004\u0012\u00020\u00000bH\u0002J\u0006\u0010h\u001a\u00020QJ\f\u0010i\u001a\b\u0012\u0004\u0012\u00020\u00120jJ\u0006\u00100\u001a\u000201J6\u0010k\u001a\u0010\u0012\u0004\u0012\u00020S\u0012\u0004\u0012\u00020T\u0018\u00010\u00102\u0006\u00102\u001a\u0002032\u0006\u0010l\u001a\u00020m2\u0006\u0010n\u001a\u00020o2\u0006\u0010p\u001a\u00020qH\u0016J\u0010\u0010r\u001a\u0002012\u0006\u0010s\u001a\u00020tH\u0002J\u0010\u0010u\u001a\u0002092\u0006\u00102\u001a\u000203H\u0016J \u0010v\u001a\u0002092\u0006\u0010w\u001a\u00020t2\u0006\u0010x\u001a\u00020Q2\u0006\u0010y\u001a\u000201H\u0002J\u0010\u0010z\u001a\u0002012\u0006\u0010:\u001a\u00020{H\u0002J\b\u0010|\u001a\u000201H\u0002J,\u0010}\u001a\u0002092\u0006\u0010~\u001a\u00020K2\n\u0010\u007f\u001a\u0006\u0012\u0002\b\u00030J2\u000e\u0010\u0080\u0001\u001a\t\u0012\u0005\u0012\u00030\u0081\u00010bH\u0002J\u0013\u0010\u0082\u0001\u001a\u0002012\b\u0010\u0083\u0001\u001a\u00030\u0084\u0001H\u0016J\u0013\u0010\u0085\u0001\u001a\u0002012\b\u0010\u0086\u0001\u001a\u00030\u0087\u0001H\u0016J\u0011\u0010\u0088\u0001\u001a\u0002012\u0006\u0010:\u001a\u00020{H\u0002J0\u0010\u0089\u0001\u001a\u000f\u0012\u0004\u0012\u00020?\u0012\u0004\u0012\u00020?0\u008a\u00012\u0007\u0010\u008b\u0001\u001a\u00020?2\u0007\u0010\u008c\u0001\u001a\u00020?2\u0006\u0010x\u001a\u00020QH\u0002J\u0011\u0010\u008d\u0001\u001a\u0002092\u0006\u0010P\u001a\u00020QH\u0002J/\u0010\u008e\u0001\u001a\u0002092\u0006\u0010~\u001a\u00020*2\u0007\u0010\u008f\u0001\u001a\u00020Q2\u0013\u0010\u0090\u0001\u001a\u000e\u0012\u0004\u0012\u00020S\u0012\u0004\u0012\u00020T0\u0010H\u0002J\u0019\u0010\u0091\u0001\u001a\u0002092\u0006\u0010B\u001a\u00020-2\u0006\u0010P\u001a\u00020QH\u0002J\u0011\u0010\u0092\u0001\u001a\u0002012\u0006\u0010:\u001a\u00020{H\u0016J\r\u0010\u0093\u0001\u001a\u000201*\u00020?H\u0002J\u000e\u0010\u0094\u0001\u001a\u000201*\u00030\u0084\u0001H\u0002J\u0017\u0010\u0095\u0001\u001a\u00030\u0096\u0001*\u00030\u0096\u00012\u0006\u0010P\u001a\u00020QH\u0002R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010(\u001a\u0012\u0012\u0004\u0012\u00020*0)j\b\u0012\u0004\u0012\u00020*`+X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010,\u001a\u0012\u0012\u0004\u0012\u00020-0)j\b\u0012\u0004\u0012\u00020-`+X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010.\u001a\u00020/X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00100\u001a\u000201X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00102\u001a\u000203X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020&X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020$X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020 X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u00104\u001a\u000205X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b6\u00107R\u001a\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00120\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\"X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0098\u0001²\u0006\f\u0010\u0099\u0001\u001a\u00030\u009a\u0001X\u008a\u0084\u0002"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/BuilderInferenceSession;", "Lorg/jetbrains/kotlin/resolve/calls/tower/StubTypesBasedInferenceSession;", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "psiCallResolver", "Lorg/jetbrains/kotlin/resolve/calls/tower/PSICallResolver;", "postponedArgumentsAnalyzer", "Lorg/jetbrains/kotlin/resolve/calls/components/PostponedArgumentsAnalyzer;", "kotlinConstraintSystemCompleter", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/KotlinConstraintSystemCompleter;", "callComponents", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallComponents;", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "topLevelCallContext", "Lorg/jetbrains/kotlin/resolve/calls/context/BasicCallResolutionContext;", "stubsForPostponedVariables", "", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/NewTypeVariable;", "Lorg/jetbrains/kotlin/types/StubTypeForBuilderInference;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "kotlinToResolvedCallTransformer", "Lorg/jetbrains/kotlin/resolve/calls/tower/KotlinToResolvedCallTransformer;", "expressionTypingServices", "Lorg/jetbrains/kotlin/types/expressions/ExpressionTypingServices;", "argumentTypeResolver", "Lorg/jetbrains/kotlin/resolve/calls/ArgumentTypeResolver;", "doubleColonExpressionResolver", "Lorg/jetbrains/kotlin/types/expressions/DoubleColonExpressionResolver;", "deprecationResolver", "Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationResolver;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "typeApproximator", "Lorg/jetbrains/kotlin/types/TypeApproximator;", "missingSupertypesResolver", "Lorg/jetbrains/kotlin/resolve/MissingSupertypesResolver;", "lambdaArgument", "Lorg/jetbrains/kotlin/resolve/calls/model/LambdaKotlinCallArgument;", "(Lorg/jetbrains/kotlin/resolve/calls/tower/PSICallResolver;Lorg/jetbrains/kotlin/resolve/calls/components/PostponedArgumentsAnalyzer;Lorg/jetbrains/kotlin/resolve/calls/inference/components/KotlinConstraintSystemCompleter;Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallComponents;Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;Lorg/jetbrains/kotlin/resolve/calls/context/BasicCallResolutionContext;Ljava/util/Map;Lorg/jetbrains/kotlin/resolve/BindingTrace;Lorg/jetbrains/kotlin/resolve/calls/tower/KotlinToResolvedCallTransformer;Lorg/jetbrains/kotlin/types/expressions/ExpressionTypingServices;Lorg/jetbrains/kotlin/resolve/calls/ArgumentTypeResolver;Lorg/jetbrains/kotlin/types/expressions/DoubleColonExpressionResolver;Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationResolver;Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/types/TypeApproximator;Lorg/jetbrains/kotlin/resolve/MissingSupertypesResolver;Lorg/jetbrains/kotlin/resolve/calls/model/LambdaKotlinCallArgument;)V", "commonCalls", "Ljava/util/ArrayList;", "Lorg/jetbrains/kotlin/resolve/calls/tower/PSICompletedCallInfo;", "Lkotlin/collections/ArrayList;", "commonExpressions", "Lorg/jetbrains/kotlin/psi/KtExpression;", "commonSystem", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/NewConstraintSystemImpl;", "hasInapplicableCall", "", "lambda", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedLambdaAtom;", "parentSession", "Lorg/jetbrains/kotlin/resolve/calls/components/InferenceSession;", "getParentSession", "()Lorg/jetbrains/kotlin/resolve/calls/components/InferenceSession;", "addCompletedCallInfo", "", "callInfo", "Lorg/jetbrains/kotlin/resolve/calls/components/CompletedCallInfo;", "addExpectedTypeConstraint", "callExpression", "a", "Lorg/jetbrains/kotlin/types/KotlinType;", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "addExpression", "expression", "anyReceiverContainStubType", "descriptor", "arePostponedVariablesInferred", "clearCallsInfoByContainingElement", "containingElement", "Lorg/jetbrains/kotlin/psi/KtElement;", "completeCall", "Lorg/jetbrains/kotlin/resolve/calls/tower/NewAbstractResolvedCall;", "Lorg/jetbrains/kotlin/resolve/calls/tower/CallInfo;", "atomCompleter", "Lorg/jetbrains/kotlin/resolve/calls/tower/ResolvedAtomCompleter;", "completeDoubleColonExpression", "Lorg/jetbrains/kotlin/psi/KtDoubleColonExpression;", "substitutor", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/NewTypeSubstitutor;", "createNonFixedTypeToVariableMap", "Lorg/jetbrains/kotlin/types/TypeConstructor;", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "createNonFixedTypeToVariableSubstitutor", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/NewTypeSubstitutorByConstructorMap;", "createResolvedAtomCompleter", "resultSubstitutor", "context", "currentConstraintSystem", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintStorage$Empty;", "extractCapturedTypesTo", ModuleXmlParser.TYPE, "to", "", "Lorg/jetbrains/kotlin/types/checker/NewCapturedType;", "extractCommonCapturedTypes", "", "findAllParentBuildInferenceSessions", "findParentBuildInferenceSession", "findTopLevelTrace", "getCurrentSubstitutor", "getNestedBuilderInferenceSessions", "getNotFixedToInferredTypesSubstitutor", "getUsedStubTypes", "", "inferPostponedVariables", "constraintSystemBuilder", "Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystemBuilder;", "completionMode", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintSystemCompletionMode;", "diagnosticsHolder", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinDiagnosticsHolder;", "initializeCommonSystem", "initialStorage", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintStorage;", "initializeLambda", "integrateConstraints", "storage", "nonFixedToVariablesSubstitutor", "shouldIntegrateAllConstraints", "isInLHSOfDoubleColonExpression", "Lorg/jetbrains/kotlin/resolve/calls/model/SingleCallResolutionResult;", "isTopLevelBuilderInferenceCall", "reportErrors", "completedCall", "resolvedCall", "errors", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintSystemError;", "shouldCompleteResolvedSubAtomsOf", "resolvedCallAtom", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCallAtom;", "shouldRunCompletion", "candidate", "Lorg/jetbrains/kotlin/resolve/calls/components/candidate/ResolutionCandidate;", "skipCall", "substituteNotFixedVariables", "Lkotlin/Pair;", "lowerType", "upperType", "updateAllCalls", "updateCall", "nonFixedTypesToResultSubstitutor", "nonFixedTypesToResult", "updateExpressionDescriptorAndType", "writeOnlyStubs", "containsStubType", "isSuitableForBuilderInference", "substitute", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/InitialConstraint;", "Companion", "frontend", "resultingSubstitutor", "Lorg/jetbrains/kotlin/resolve/calls/inference/ComposedSubstitutor;"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BuilderInferenceSession extends StubTypesBasedInferenceSession<CallableDescriptor> {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final ArgumentTypeResolver argumentTypeResolver;
    private final ArrayList<PSICompletedCallInfo> commonCalls;
    private final ArrayList<KtExpression> commonExpressions;
    private final NewConstraintSystemImpl commonSystem;
    private final DeprecationResolver deprecationResolver;
    private final DoubleColonExpressionResolver doubleColonExpressionResolver;
    private final ExpressionTypingServices expressionTypingServices;
    private boolean hasInapplicableCall;
    private final KotlinToResolvedCallTransformer kotlinToResolvedCallTransformer;
    private ResolvedLambdaAtom lambda;
    private final LambdaKotlinCallArgument lambdaArgument;
    private final MissingSupertypesResolver missingSupertypesResolver;
    private final ModuleDescriptor moduleDescriptor;
    private final InferenceSession parentSession;
    private final Map<NewTypeVariable, StubTypeForBuilderInference> stubsForPostponedVariables;
    private final BasicCallResolutionContext topLevelCallContext;
    private final BindingTrace trace;
    private final TypeApproximator typeApproximator;

    /* JADX INFO: compiled from: BuilderInferenceSession.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ConstraintKind.values().length];
            try {
                iArr[ConstraintKind.LOWER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ConstraintKind.UPPER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ConstraintKind.EQUALITY.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.StubTypesBasedInferenceSession, org.jetbrains.kotlin.resolve.calls.components.InferenceSession
    public boolean shouldCompleteResolvedSubAtomsOf(ResolvedCallAtom resolvedCallAtom) {
        Intrinsics.checkNotNullParameter(resolvedCallAtom, "resolvedCallAtom");
        return true;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BuilderInferenceSession(PSICallResolver psiCallResolver, PostponedArgumentsAnalyzer postponedArgumentsAnalyzer, KotlinConstraintSystemCompleter kotlinConstraintSystemCompleter, KotlinCallComponents callComponents, KotlinBuiltIns builtIns, BasicCallResolutionContext topLevelCallContext, Map<NewTypeVariable, StubTypeForBuilderInference> stubsForPostponedVariables, BindingTrace trace, KotlinToResolvedCallTransformer kotlinToResolvedCallTransformer, ExpressionTypingServices expressionTypingServices, ArgumentTypeResolver argumentTypeResolver, DoubleColonExpressionResolver doubleColonExpressionResolver, DeprecationResolver deprecationResolver, ModuleDescriptor moduleDescriptor, TypeApproximator typeApproximator, MissingSupertypesResolver missingSupertypesResolver, LambdaKotlinCallArgument lambdaArgument) {
        super(psiCallResolver, postponedArgumentsAnalyzer, kotlinConstraintSystemCompleter, callComponents, builtIns);
        Intrinsics.checkNotNullParameter(psiCallResolver, "psiCallResolver");
        Intrinsics.checkNotNullParameter(postponedArgumentsAnalyzer, "postponedArgumentsAnalyzer");
        Intrinsics.checkNotNullParameter(kotlinConstraintSystemCompleter, "kotlinConstraintSystemCompleter");
        Intrinsics.checkNotNullParameter(callComponents, "callComponents");
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        Intrinsics.checkNotNullParameter(topLevelCallContext, "topLevelCallContext");
        Intrinsics.checkNotNullParameter(stubsForPostponedVariables, "stubsForPostponedVariables");
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(kotlinToResolvedCallTransformer, "kotlinToResolvedCallTransformer");
        Intrinsics.checkNotNullParameter(expressionTypingServices, "expressionTypingServices");
        Intrinsics.checkNotNullParameter(argumentTypeResolver, "argumentTypeResolver");
        Intrinsics.checkNotNullParameter(doubleColonExpressionResolver, "doubleColonExpressionResolver");
        Intrinsics.checkNotNullParameter(deprecationResolver, "deprecationResolver");
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        Intrinsics.checkNotNullParameter(typeApproximator, "typeApproximator");
        Intrinsics.checkNotNullParameter(missingSupertypesResolver, "missingSupertypesResolver");
        Intrinsics.checkNotNullParameter(lambdaArgument, "lambdaArgument");
        this.topLevelCallContext = topLevelCallContext;
        this.stubsForPostponedVariables = stubsForPostponedVariables;
        this.trace = trace;
        this.kotlinToResolvedCallTransformer = kotlinToResolvedCallTransformer;
        this.expressionTypingServices = expressionTypingServices;
        this.argumentTypeResolver = argumentTypeResolver;
        this.doubleColonExpressionResolver = doubleColonExpressionResolver;
        this.deprecationResolver = deprecationResolver;
        this.moduleDescriptor = moduleDescriptor;
        this.typeApproximator = typeApproximator;
        this.missingSupertypesResolver = missingSupertypesResolver;
        this.lambdaArgument = lambdaArgument;
        ConstraintInjector constraintInjector = callComponents.getConstraintInjector();
        KotlinTypeRefiner kotlinTypeRefiner = callComponents.getKotlinTypeRefiner();
        LanguageVersionSettings languageVersionSettings = topLevelCallContext.languageVersionSettings;
        Intrinsics.checkNotNullExpressionValue(languageVersionSettings, "languageVersionSettings");
        NewConstraintSystemImpl NewConstraintSystemImpl = ClassicTypeSystemContextForCSKt.NewConstraintSystemImpl(constraintInjector, builtIns, kotlinTypeRefiner, languageVersionSettings);
        this.commonSystem = NewConstraintSystemImpl;
        if (topLevelCallContext.inferenceSession instanceof StubTypesBasedInferenceSession) {
            ((StubTypesBasedInferenceSession) topLevelCallContext.inferenceSession).addNestedInferenceSession(this);
        }
        Iterator<T> it = stubsForPostponedVariables.keySet().iterator();
        while (it.hasNext()) {
            NewConstraintSystemImpl.registerVariable((TypeVariableMarker) it.next());
        }
        this.commonCalls = new ArrayList<>();
        this.commonExpressions = new ArrayList<>();
        InferenceSession inferenceSession = this.topLevelCallContext.inferenceSession;
        Intrinsics.checkNotNullExpressionValue(inferenceSession, "inferenceSession");
        this.parentSession = inferenceSession;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.InferenceSession
    public InferenceSession getParentSession() {
        return this.parentSession;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.StubTypesBasedInferenceSession, org.jetbrains.kotlin.resolve.calls.components.InferenceSession
    public boolean shouldRunCompletion(ResolutionCandidate candidate) {
        Intrinsics.checkNotNullParameter(candidate, "candidate");
        NewConstraintSystem system = candidate.getSystem();
        Intrinsics.checkNotNull(system, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.calls.inference.model.NewConstraintSystemImpl");
        NewConstraintSystemImpl newConstraintSystemImpl = (NewConstraintSystemImpl) system;
        if (newConstraintSystemImpl.getHasContradiction()) {
            return true;
        }
        ConstraintStorage constraintStorageCurrentStorage = newConstraintSystemImpl.getBuilder().currentStorage();
        if (!isSuitableForBuilderInference(candidate.getResolvedCall())) {
            return true;
        }
        Set<TypeConstructorMarker> setKeySet = constraintStorageCurrentStorage.getNotFixedTypeVariables().keySet();
        if ((setKeySet instanceof Collection) && setKeySet.isEmpty()) {
            return true;
        }
        for (TypeConstructorMarker typeConstructorMarker : setKeySet) {
            TypeVariableMarker typeVariableMarker = constraintStorageCurrentStorage.getAllTypeVariables().get(typeConstructorMarker);
            if (typeVariableMarker == null || !constraintStorageCurrentStorage.getPostponedTypeVariables().contains(typeVariableMarker)) {
                if (!getKotlinConstraintSystemCompleter().getVariableFixationFinder().isTypeVariableHasProperConstraint(newConstraintSystemImpl, typeConstructorMarker)) {
                    List<ResolvedAtom> subResolvedAtoms = candidate.getSubResolvedAtoms();
                    if (!(subResolvedAtoms instanceof Collection) || !subResolvedAtoms.isEmpty()) {
                        Iterator<T> it = subResolvedAtoms.iterator();
                        while (it.hasNext()) {
                            if (shouldRunCompletion$hasPostponed((ResolvedAtom) it.next())) {
                                return true;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private static final boolean shouldRunCompletion$hasPostponed(ResolvedAtom resolvedAtom) {
        if ((resolvedAtom instanceof PostponedResolvedAtom) && !resolvedAtom.getAnalyzed()) {
            return true;
        }
        List<ResolvedAtom> subResolvedAtoms = resolvedAtom.getSubResolvedAtoms();
        if (subResolvedAtoms != null) {
            List<ResolvedAtom> list = subResolvedAtoms;
            if (!(list instanceof Collection) || !list.isEmpty()) {
                Iterator<T> it = list.iterator();
                while (it.hasNext()) {
                    if (shouldRunCompletion$hasPostponed((ResolvedAtom) it.next())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private final boolean isSuitableForBuilderInference(ResolvedCallAtom resolvedCallAtom) {
        ReceiverValueWithSmartCastInfo receiver;
        UnwrappedType stableType;
        ReceiverValueWithSmartCastInfo receiver2;
        UnwrappedType stableType2;
        SimpleKotlinCallArgument extensionReceiverArgument = resolvedCallAtom.getExtensionReceiverArgument();
        SimpleKotlinCallArgument dispatchReceiverArgument = resolvedCallAtom.getDispatchReceiverArgument();
        List<ResolvedAtom> subResolvedAtoms = resolvedCallAtom.getSubResolvedAtoms();
        if (subResolvedAtoms != null) {
            List<ResolvedAtom> list = subResolvedAtoms;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(((ResolvedAtom) it.next()).getAtom());
            }
            ArrayList arrayList2 = new ArrayList();
            for (Object obj : arrayList) {
                if (obj instanceof SubKotlinCallArgument) {
                    arrayList2.add(obj);
                }
            }
            ArrayList arrayList3 = arrayList2;
            if (!(arrayList3 instanceof Collection) || !arrayList3.isEmpty()) {
                Iterator it2 = arrayList3.iterator();
                while (it2.hasNext()) {
                    if (isSuitableForBuilderInference(((SubKotlinCallArgument) it2.next()).getCallResult().getResultCallAtom())) {
                        return true;
                    }
                }
            }
        }
        if (extensionReceiverArgument != null || dispatchReceiverArgument != null) {
            if (dispatchReceiverArgument != null && (receiver2 = dispatchReceiverArgument.getReceiver()) != null && (stableType2 = ArgumentsUtilsKt.getStableType(receiver2)) != null && containsStubType(stableType2)) {
                return true;
            }
            if (extensionReceiverArgument != null && (receiver = extensionReceiverArgument.getReceiver()) != null && (stableType = ArgumentsUtilsKt.getStableType(receiver)) != null && containsStubType(stableType)) {
                return AnnotationsForResolveUtilsKt.hasBuilderInferenceAnnotation(resolvedCallAtom.get_candidateDescriptor());
            }
        }
        return false;
    }

    private final boolean containsStubType(KotlinType kotlinType) {
        return TypeUtilsKt.contains(kotlinType, new Function1<UnwrappedType, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.inference.BuilderInferenceSession.containsStubType.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(UnwrappedType it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(it instanceof StubTypeForBuilderInference);
            }
        });
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.StubTypesBasedInferenceSession, org.jetbrains.kotlin.resolve.calls.components.InferenceSession
    public void addCompletedCallInfo(CompletedCallInfo callInfo) {
        Intrinsics.checkNotNullParameter(callInfo, "callInfo");
        if (!(callInfo instanceof PSICompletedCallInfo)) {
            throw new IllegalArgumentException(("Wrong instance of callInfo: " + callInfo).toString());
        }
        if (skipCall(callInfo.getCallResolutionResult())) {
            return;
        }
        this.commonCalls.add(callInfo);
        CallableDescriptor resultingDescriptor = ((PSICompletedCallInfo) callInfo).getResolvedCall().getResultingDescriptor();
        if (((resultingDescriptor instanceof LocalVariableDescriptor) || anyReceiverContainStubType(resultingDescriptor)) && !getCallComponents().getStatelessCallbacks().isApplicableCallForBuilderInference(resultingDescriptor, getCallComponents().getLanguageVersionSettings())) {
            this.hasInapplicableCall = true;
        }
    }

    public final void addExpression(KtExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        this.commonExpressions.add(expression);
    }

    private final boolean anyReceiverContainStubType(CallableDescriptor descriptor) {
        KotlinType type;
        KotlinType type2;
        ReceiverParameterDescriptor receiverParameterDescriptorMo6753getDispatchReceiverParameter = descriptor.mo6753getDispatchReceiverParameter();
        if (receiverParameterDescriptorMo6753getDispatchReceiverParameter != null && (type2 = receiverParameterDescriptorMo6753getDispatchReceiverParameter.getType()) != null && TypeUtilsKt.contains(type2, new Function1<UnwrappedType, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.inference.BuilderInferenceSession.anyReceiverContainStubType.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(UnwrappedType it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(it instanceof StubTypeForBuilderInference);
            }
        })) {
            return true;
        }
        ReceiverParameterDescriptor extensionReceiverParameter = descriptor.getExtensionReceiverParameter();
        return (extensionReceiverParameter == null || (type = extensionReceiverParameter.getType()) == null || !TypeUtilsKt.contains(type, new Function1<UnwrappedType, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.inference.BuilderInferenceSession.anyReceiverContainStubType.2
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(UnwrappedType it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(it instanceof StubTypeForBuilderInference);
            }
        })) ? false : true;
    }

    private final boolean isTopLevelBuilderInferenceCall() {
        return findParentBuildInferenceSession() == null;
    }

    private final BuilderInferenceSession findParentBuildInferenceSession() {
        for (InferenceSession parentSession = getParentSession(); parentSession != null; parentSession = parentSession.getParentSession()) {
            if (parentSession instanceof BuilderInferenceSession) {
                return (BuilderInferenceSession) parentSession;
            }
        }
        return null;
    }

    private final List<BuilderInferenceSession> findAllParentBuildInferenceSessions() {
        List listCreateListBuilder = CollectionsKt.createListBuilder();
        for (BuilderInferenceSession builderInferenceSessionFindParentBuildInferenceSession = findParentBuildInferenceSession(); builderInferenceSessionFindParentBuildInferenceSession != null; builderInferenceSessionFindParentBuildInferenceSession = builderInferenceSessionFindParentBuildInferenceSession.findParentBuildInferenceSession()) {
            listCreateListBuilder.add(builderInferenceSessionFindParentBuildInferenceSession);
        }
        return CollectionsKt.build(listCreateListBuilder);
    }

    /* JADX INFO: renamed from: hasInapplicableCall, reason: from getter */
    public final boolean getHasInapplicableCall() {
        return this.hasInapplicableCall;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.InferenceSession
    public boolean writeOnlyStubs(SingleCallResolutionResult callInfo) {
        Intrinsics.checkNotNullParameter(callInfo, "callInfo");
        return (skipCall(callInfo) || arePostponedVariablesInferred()) ? false : true;
    }

    private final boolean skipCall(SingleCallResolutionResult callInfo) {
        CallableDescriptor callableDescriptor = callInfo.getResultCallAtom().get_candidateDescriptor();
        if (callableDescriptor instanceof FakeCallableDescriptorForObject) {
            return true;
        }
        return !DescriptorUtils.isObject(callableDescriptor) && isInLHSOfDoubleColonExpression(callInfo);
    }

    private final boolean isInLHSOfDoubleColonExpression(SingleCallResolutionResult callInfo) {
        KtElement callElement = PSIKotlinCallsKt.getPsiKotlinCall(callInfo.getResultCallAtom().getAtom()).getPsiCall().getCallElement();
        Intrinsics.checkNotNullExpressionValue(callElement, "getCallElement(...)");
        KtElement ktElement = callElement;
        KtDoubleColonExpression ktDoubleColonExpression = (KtDoubleColonExpression) PsiTreeUtil.getParentOfType(ktElement, KtDoubleColonExpression.class, false);
        PsiElement lhs = ktDoubleColonExpression != null ? ktDoubleColonExpression.getLhs() : null;
        if ((lhs instanceof KtReferenceExpression) || (lhs instanceof KtDotQualifiedExpression)) {
            return PsiUtilsKt.isAncestor$default(lhs, ktElement, false, 2, null);
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.StubTypesBasedInferenceSession, org.jetbrains.kotlin.resolve.calls.components.InferenceSession
    public ConstraintStorage.Empty currentConstraintSystem() {
        return ConstraintStorage.Empty.INSTANCE;
    }

    public final NewTypeSubstitutor getNotFixedToInferredTypesSubstitutor() {
        return new ComposedSubstitutor(getCurrentSubstitutor(), createNonFixedTypeToVariableSubstitutor());
    }

    public final Set<StubTypeForBuilderInference> getUsedStubTypes() {
        return CollectionsKt.toSet(this.stubsForPostponedVariables.values());
    }

    public final NewTypeSubstitutor getCurrentSubstitutor() {
        TypeSubstitutorMarker typeSubstitutorMarkerBuildCurrentSubstitutor = this.commonSystem.buildCurrentSubstitutor();
        Intrinsics.checkNotNull(typeSubstitutorMarkerBuildCurrentSubstitutor, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.calls.inference.components.NewTypeSubstitutor");
        NewTypeSubstitutor newTypeSubstitutor = (NewTypeSubstitutor) typeSubstitutorMarkerBuildCurrentSubstitutor;
        if (newTypeSubstitutor.isEmpty()) {
            newTypeSubstitutor = null;
        }
        return newTypeSubstitutor == null ? EmptySubstitutor.INSTANCE : newTypeSubstitutor;
    }

    private final boolean arePostponedVariablesInferred() {
        return this.commonSystem.getNotFixedTypeVariables().isEmpty();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.InferenceSession
    public void initializeLambda(ResolvedLambdaAtom lambda) {
        Intrinsics.checkNotNullParameter(lambda, "lambda");
        this.lambda = lambda;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.InferenceSession
    public Map<TypeConstructor, UnwrappedType> inferPostponedVariables(ResolvedLambdaAtom lambda, ConstraintSystemBuilder constraintSystemBuilder, ConstraintSystemCompletionMode completionMode, KotlinDiagnosticsHolder diagnosticsHolder) {
        Intrinsics.checkNotNullParameter(lambda, "lambda");
        Intrinsics.checkNotNullParameter(constraintSystemBuilder, "constraintSystemBuilder");
        Intrinsics.checkNotNullParameter(completionMode, "completionMode");
        Intrinsics.checkNotNullParameter(diagnosticsHolder, "diagnosticsHolder");
        initializeLambda(lambda);
        final ConstraintStorage constraintStorageCurrentStorage = constraintSystemBuilder.currentStorage();
        Lazy lazy = LazyKt.lazy(new Function0<ComposedSubstitutor>() { // from class: org.jetbrains.kotlin.resolve.calls.inference.BuilderInferenceSession$inferPostponedVariables$resultingSubstitutor$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final ComposedSubstitutor invoke() {
                NewTypeSubstitutor newTypeSubstitutorBuildResultingSubstitutor = DescriptorRelatedInferenceUtilsKt.buildResultingSubstitutor(constraintStorageCurrentStorage, this.commonSystem, false);
                TypeSubstitutorMarker typeSubstitutorMarkerBuildCurrentSubstitutor = this.commonSystem.buildCurrentSubstitutor();
                Intrinsics.checkNotNull(typeSubstitutorMarkerBuildCurrentSubstitutor, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.calls.inference.components.NewTypeSubstitutor");
                return new ComposedSubstitutor(newTypeSubstitutorBuildResultingSubstitutor, (NewTypeSubstitutor) typeSubstitutorMarkerBuildCurrentSubstitutor);
            }
        });
        if (initializeCommonSystem(constraintStorageCurrentStorage)) {
            if (!isTopLevelBuilderInferenceCall()) {
                return null;
            }
            updateAllCalls(inferPostponedVariables$lambda$8(lazy));
            return null;
        }
        KotlinConstraintSystemCompleter kotlinConstraintSystemCompleter = getKotlinConstraintSystemCompleter();
        NewConstraintSystemImpl newConstraintSystemImplAsConstraintSystemCompleterContext = this.commonSystem.asConstraintSystemCompleterContext();
        SimpleType unitType = getBuiltIns().getUnitType();
        Intrinsics.checkNotNullExpressionValue(unitType, "getUnitType(...)");
        SimpleType simpleType = unitType;
        ArrayList<PSIPartialCallInfo> commonPartiallyResolvedCalls = getCommonPartiallyResolvedCalls();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(commonPartiallyResolvedCalls, 10));
        Iterator<T> it = commonPartiallyResolvedCalls.iterator();
        while (it.hasNext()) {
            arrayList.add(((PSIPartialCallInfo) it.next()).getCallResolutionResult().getResultCallAtom());
        }
        kotlinConstraintSystemCompleter.completeConstraintSystem(newConstraintSystemImplAsConstraintSystemCompleterContext, simpleType, arrayList, completionMode, diagnosticsHolder);
        if (completionMode == ConstraintSystemCompletionMode.FULL) {
            constraintSystemBuilder.substituteFixedVariables(new ComposedSubstitutor(inferPostponedVariables$lambda$8(lazy), createNonFixedTypeToVariableSubstitutor()));
        }
        if (isTopLevelBuilderInferenceCall()) {
            updateAllCalls(inferPostponedVariables$lambda$8(lazy));
        }
        Map fixedTypeVariables = this.commonSystem.getFixedTypeVariables();
        Intrinsics.checkNotNull(fixedTypeVariables, "null cannot be cast to non-null type kotlin.collections.Map<org.jetbrains.kotlin.types.TypeConstructor, org.jetbrains.kotlin.types.UnwrappedType>");
        return fixedTypeVariables;
    }

    private static final ComposedSubstitutor inferPostponedVariables$lambda$8(Lazy<ComposedSubstitutor> lazy) {
        return lazy.getValue();
    }

    private final List<BuilderInferenceSession> getNestedBuilderInferenceSessions() {
        List listCreateListBuilder = CollectionsKt.createListBuilder();
        for (StubTypesBasedInferenceSession<?> stubTypesBasedInferenceSession : getNestedInferenceSessions()) {
            if (stubTypesBasedInferenceSession instanceof BuilderInferenceSession) {
                Intrinsics.checkNotNull(stubTypesBasedInferenceSession);
                listCreateListBuilder.add(stubTypesBasedInferenceSession);
            } else if (stubTypesBasedInferenceSession instanceof DelegateInferenceSession) {
                listCreateListBuilder.addAll(((DelegateInferenceSession) stubTypesBasedInferenceSession).getNestedBuilderInferenceSessions());
            }
        }
        return CollectionsKt.build(listCreateListBuilder);
    }

    private final void updateAllCalls(NewTypeSubstitutor substitutor) {
        Companion companion = INSTANCE;
        ResolvedLambdaAtom resolvedLambdaAtom = this.lambda;
        if (resolvedLambdaAtom == null) {
            Intrinsics.throwUninitializedPropertyAccessException("lambda");
            resolvedLambdaAtom = null;
        }
        companion.updateCalls(this, resolvedLambdaAtom, substitutor, this.commonSystem.getErrors());
        for (BuilderInferenceSession builderInferenceSession : getNestedBuilderInferenceSessions()) {
            TypeSubstitutorMarker typeSubstitutorMarkerBuildCurrentSubstitutor = builderInferenceSession.commonSystem.buildCurrentSubstitutor();
            Intrinsics.checkNotNull(typeSubstitutorMarkerBuildCurrentSubstitutor, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.calls.inference.components.NewTypeSubstitutor");
            builderInferenceSession.updateAllCalls(new ComposedSubstitutor((NewTypeSubstitutor) typeSubstitutorMarkerBuildCurrentSubstitutor, substitutor));
        }
    }

    private final Map<TypeConstructor, UnwrappedType> createNonFixedTypeToVariableMap() {
        HashMap map = new HashMap();
        for (Map.Entry<NewTypeVariable, StubTypeForBuilderInference> entry : this.stubsForPostponedVariables.entrySet()) {
            map.put(entry.getValue().getConstructor(), entry.getKey().getDefaultType());
        }
        BuilderInferenceSession builderInferenceSessionFindParentBuildInferenceSession = findParentBuildInferenceSession();
        if (builderInferenceSessionFindParentBuildInferenceSession != null) {
            map.putAll(builderInferenceSessionFindParentBuildInferenceSession.createNonFixedTypeToVariableMap());
        }
        return map;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final NewTypeSubstitutorByConstructorMap createNonFixedTypeToVariableSubstitutor() {
        return new NewTypeSubstitutorByConstructorMap(createNonFixedTypeToVariableMap());
    }

    private final void integrateConstraints(ConstraintStorage storage, NewTypeSubstitutor nonFixedToVariablesSubstitutor, boolean shouldIntegrateAllConstraints) {
        Iterator<T> it = storage.getNotFixedTypeVariables().values().iterator();
        while (it.hasNext()) {
            InferenceUtilsKt.registerTypeVariableIfNotPresent(this.commonSystem, ((VariableWithConstraints) it.next()).getTypeVariable());
        }
        NewTypeSubstitutor newTypeSubstitutorBuildResultingSubstitutor = DescriptorRelatedInferenceUtilsKt.buildResultingSubstitutor(storage, this.commonSystem, false);
        for (InitialConstraint initialConstraint : storage.getInitialConstraints()) {
            if (!(initialConstraint.getPosition() instanceof BuilderInferencePosition)) {
                InitialConstraint initialConstraintSubstitute = substitute(initialConstraint, newTypeSubstitutorBuildResultingSubstitutor);
                KotlinTypeMarker a = initialConstraintSubstitute.getA();
                Intrinsics.checkNotNull(a, "null cannot be cast to non-null type org.jetbrains.kotlin.types.KotlinType");
                KotlinTypeMarker b = initialConstraintSubstitute.getB();
                Intrinsics.checkNotNull(b, "null cannot be cast to non-null type org.jetbrains.kotlin.types.KotlinType");
                Pair<KotlinType, KotlinType> pairSubstituteNotFixedVariables = substituteNotFixedVariables((KotlinType) a, (KotlinType) b, nonFixedToVariablesSubstitutor);
                KotlinType kotlinTypeComponent1 = pairSubstituteNotFixedVariables.component1();
                KotlinType kotlinTypeComponent2 = pairSubstituteNotFixedVariables.component2();
                KotlinType kotlinType = kotlinTypeComponent1;
                if (!this.commonSystem.isProperType(kotlinType) || !this.commonSystem.isProperType(kotlinTypeComponent2)) {
                    int i = WhenMappings.$EnumSwitchMapping$0[initialConstraint.getConstraintKind().ordinal()];
                    if (i == 1) {
                        throw new IllegalStateException("LOWER constraint shouldn't be used, please use UPPER".toString());
                    }
                    if (i == 2) {
                        this.commonSystem.addSubtypeConstraint(kotlinType, kotlinTypeComponent2, initialConstraintSubstitute.getPosition());
                    } else if (i == 3) {
                        NewConstraintSystemImpl newConstraintSystemImpl = this.commonSystem;
                        KotlinType kotlinType2 = kotlinTypeComponent2;
                        newConstraintSystemImpl.addSubtypeConstraint(kotlinType, kotlinType2, initialConstraintSubstitute.getPosition());
                        newConstraintSystemImpl.addSubtypeConstraint(kotlinType2, kotlinType, initialConstraintSubstitute.getPosition());
                    }
                }
            }
        }
        if (shouldIntegrateAllConstraints) {
            for (Map.Entry<TypeConstructorMarker, KotlinTypeMarker> entry : storage.getFixedTypeVariables().entrySet()) {
                TypeConstructorMarker key = entry.getKey();
                KotlinTypeMarker value = entry.getValue();
                TypeVariableMarker typeVariableMarker = (TypeVariableMarker) MapsKt.getValue(storage.getAllTypeVariables(), key);
                InferenceUtilsKt.registerTypeVariableIfNotPresent(this.commonSystem, typeVariableMarker);
                NewConstraintSystemImpl newConstraintSystemImpl2 = this.commonSystem;
                Intrinsics.checkNotNull(typeVariableMarker, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.calls.inference.model.NewTypeVariable");
                newConstraintSystemImpl2.addEqualityConstraint(((NewTypeVariable) typeVariableMarker).getDefaultType(), value, BuilderInferencePosition.INSTANCE);
            }
        }
    }

    public final void addExpectedTypeConstraint(KtExpression callExpression, KotlinType a, KotlinType b) {
        Intrinsics.checkNotNullParameter(callExpression, "callExpression");
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        Pair<KotlinType, KotlinType> pairSubstituteNotFixedVariables = substituteNotFixedVariables(a, b, createNonFixedTypeToVariableSubstitutor());
        KotlinType kotlinTypeComponent1 = pairSubstituteNotFixedVariables.component1();
        KotlinType kotlinTypeComponent2 = pairSubstituteNotFixedVariables.component2();
        BuilderInferenceExpectedTypeConstraintPosition builderInferenceExpectedTypeConstraintPosition = new BuilderInferenceExpectedTypeConstraintPosition(callExpression);
        TypeSubstitutorMarker typeSubstitutorMarkerBuildCurrentSubstitutor = this.commonSystem.buildCurrentSubstitutor();
        NewConstraintSystemImpl newConstraintSystemImpl = this.commonSystem;
        newConstraintSystemImpl.addSubtypeConstraint(MarkerExtensionsKt.safeSubstitute(typeSubstitutorMarkerBuildCurrentSubstitutor, newConstraintSystemImpl.getTypeSystemContext(), kotlinTypeComponent1), MarkerExtensionsKt.safeSubstitute(typeSubstitutorMarkerBuildCurrentSubstitutor, this.commonSystem.getTypeSystemContext(), kotlinTypeComponent2), builderInferenceExpectedTypeConstraintPosition);
    }

    private final Pair<KotlinType, KotlinType> substituteNotFixedVariables(KotlinType lowerType, KotlinType upperType, NewTypeSubstitutor nonFixedToVariablesSubstitutor) {
        List<NewCapturedType> listExtractCommonCapturedTypes = extractCommonCapturedTypes(lowerType, upperType);
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(listExtractCommonCapturedTypes, 10)), 16));
        for (NewCapturedType newCapturedType : listExtractCommonCapturedTypes) {
            NewCapturedTypeConstructor constructor = newCapturedType.getConstructor();
            Intrinsics.checkNotNull(constructor, "null cannot be cast to non-null type org.jetbrains.kotlin.types.TypeConstructor");
            Pair pair = TuplesKt.to(constructor, TypeUtilsKt.asTypeProjection(nonFixedToVariablesSubstitutor.safeSubstitute(newCapturedType)));
            linkedHashMap.put(pair.getFirst(), pair.getSecond());
        }
        TypeSubstitutor typeSubstitutorBuildSubstitutor = TypeConstructorSubstitution.Companion.createByConstructorsMap$default(TypeConstructorSubstitution.INSTANCE, linkedHashMap, false, 2, null).buildSubstitutor();
        return TuplesKt.to(nonFixedToVariablesSubstitutor.safeSubstitute(DescriptorRelatedInferenceUtilsKt.substitute(typeSubstitutorBuildSubstitutor, lowerType.unwrap())), nonFixedToVariablesSubstitutor.safeSubstitute(DescriptorRelatedInferenceUtilsKt.substitute(typeSubstitutorBuildSubstitutor, upperType.unwrap())));
    }

    private final List<NewCapturedType> extractCommonCapturedTypes(KotlinType a, KotlinType b) {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        extractCapturedTypesTo(a, linkedHashSet);
        ArrayList arrayList = new ArrayList();
        for (Object obj : linkedHashSet) {
            final NewCapturedType newCapturedType = (NewCapturedType) obj;
            if (TypeUtilsKt.contains(b, new Function1<UnwrappedType, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.inference.BuilderInferenceSession$extractCommonCapturedTypes$1$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(UnwrappedType it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return Boolean.valueOf(it.getConstructor() == newCapturedType.getConstructor());
                }
            })) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    private final void extractCapturedTypesTo(KotlinType type, Set<NewCapturedType> to) {
        if (type instanceof NewCapturedType) {
            to.add(type);
        }
        for (TypeProjection typeProjection : type.getArguments()) {
            if (!typeProjection.isStarProjection()) {
                KotlinType nullableAnyType = typeProjection.getNullableAnyType();
                Intrinsics.checkNotNullExpressionValue(nullableAnyType, "getType(...)");
                extractCapturedTypesTo(nullableAnyType, to);
            }
        }
    }

    private final boolean initializeCommonSystem(ConstraintStorage initialStorage) {
        NewTypeSubstitutorByConstructorMap newTypeSubstitutorByConstructorMapCreateNonFixedTypeToVariableSubstitutor = createNonFixedTypeToVariableSubstitutor();
        Iterator<BuilderInferenceSession> it = findAllParentBuildInferenceSessions().iterator();
        while (it.hasNext()) {
            for (Map.Entry<NewTypeVariable, StubTypeForBuilderInference> entry : it.next().stubsForPostponedVariables.entrySet()) {
                NewTypeVariable key = entry.getKey();
                StubTypeForBuilderInference value = entry.getValue();
                InferenceUtilsKt.registerTypeVariableIfNotPresent(this.commonSystem, key);
                this.commonSystem.addSubtypeConstraint(key.getDefaultType(), value, new InjectedAnotherStubTypeConstraintPositionImpl(this.lambdaArgument));
            }
        }
        NewTypeSubstitutorByConstructorMap newTypeSubstitutorByConstructorMap = newTypeSubstitutorByConstructorMapCreateNonFixedTypeToVariableSubstitutor;
        integrateConstraints(initialStorage, newTypeSubstitutorByConstructorMap, false);
        for (CallInfo callInfo : CollectionsKt.plus((Collection) this.commonCalls, (Iterable) getCommonPartiallyResolvedCalls())) {
            integrateConstraints(callInfo.getCallResolutionResult().getConstraintSystem().getBuilder().currentStorage(), newTypeSubstitutorByConstructorMap, callInfo instanceof PSIPartialCallInfo);
        }
        Map<TypeConstructorMarker, MutableVariableWithConstraints> notFixedTypeVariables = this.commonSystem.getNotFixedTypeVariables();
        if (!notFixedTypeVariables.isEmpty()) {
            Iterator<Map.Entry<TypeConstructorMarker, MutableVariableWithConstraints>> it2 = notFixedTypeVariables.entrySet().iterator();
            while (it2.hasNext()) {
                if (!it2.next().getValue().getConstraints().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v0, types: [org.jetbrains.kotlin.descriptors.CallableDescriptor] */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    public final void reportErrors(CallInfo completedCall, NewAbstractResolvedCall<?> resolvedCall, List<? extends ConstraintSystemError> errors) {
        this.kotlinToResolvedCallTransformer.reportCallDiagnostic(completedCall.getContext(), this.trace, resolvedCall, resolvedCall.getResultingDescriptor(), KotlinCallDiagnosticsKt.asDiagnostics(errors));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateExpressionDescriptorAndType(KtExpression expression, NewTypeSubstitutor substitutor) {
        Pair pair;
        KotlinType returnType;
        KotlinType type = this.trace.getType(expression);
        if (type != null) {
            this.trace.recordType(expression, substitutor.safeSubstitute(type.unwrap()));
        }
        if (expression instanceof KtLambdaExpression) {
            Object obj = this.trace.get(BindingContext.FUNCTION, ((KtLambdaExpression) expression).getFunctionLiteral());
            AnonymousFunctionDescriptor anonymousFunctionDescriptor = obj instanceof AnonymousFunctionDescriptor ? (AnonymousFunctionDescriptor) obj : null;
            if (anonymousFunctionDescriptor == null || (returnType = anonymousFunctionDescriptor.getReturnType()) == null) {
                return;
            } else {
                pair = TuplesKt.to(returnType, new C49711(anonymousFunctionDescriptor));
            }
        } else if (expression instanceof KtVariableDeclaration) {
            Object obj2 = this.trace.get(BindingContext.VARIABLE, expression);
            LocalVariableDescriptor localVariableDescriptor = obj2 instanceof LocalVariableDescriptor ? (LocalVariableDescriptor) obj2 : null;
            if (localVariableDescriptor == null) {
                return;
            } else {
                pair = TuplesKt.to(localVariableDescriptor.getType(), new C49722(localVariableDescriptor));
            }
        } else {
            if (expression instanceof KtDoubleColonExpression) {
                completeDoubleColonExpression((KtDoubleColonExpression) expression, substitutor);
                return;
            }
            return;
        }
        KotlinType kotlinType = (KotlinType) pair.component1();
        KFunction kFunction = (KFunction) pair.component2();
        if (TypeUtilsKt.shouldBeUpdated(kotlinType)) {
            ((Function1) kFunction).invoke(substitutor.safeSubstitute(kotlinType.unwrap()));
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.resolve.calls.inference.BuilderInferenceSession$updateExpressionDescriptorAndType$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: BuilderInferenceSession.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class C49711 extends FunctionReference implements Function1<KotlinType, Unit> {
        C49711(Object obj) {
            super(1, obj);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "setReturnType(Lorg/jetbrains/kotlin/types/KotlinType;)V";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "setReturnType";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(AnonymousFunctionDescriptor.class);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(KotlinType kotlinType) {
            invoke2(kotlinType);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(KotlinType p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            ((AnonymousFunctionDescriptor) this.receiver).setReturnType(p0);
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.resolve.calls.inference.BuilderInferenceSession$updateExpressionDescriptorAndType$2, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: BuilderInferenceSession.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class C49722 extends FunctionReference implements Function1<KotlinType, Unit> {
        C49722(Object obj) {
            super(1, obj);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "setOutType(Lorg/jetbrains/kotlin/types/KotlinType;)V";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "setOutType";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(LocalVariableDescriptor.class);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(KotlinType kotlinType) {
            invoke2(kotlinType);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(KotlinType kotlinType) {
            ((LocalVariableDescriptor) this.receiver).setOutType(kotlinType);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final void updateCall(PSICompletedCallInfo completedCall, NewTypeSubstitutor nonFixedTypesToResultSubstitutor, Map<TypeConstructor, ? extends UnwrappedType> nonFixedTypesToResult) {
        Set<Map.Entry<TypeConstructorMarker, KotlinTypeMarker>> setEntrySet = completedCall.getCallResolutionResult().getConstraintSystem().getBuilder().currentStorage().getFixedTypeVariables().entrySet();
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(setEntrySet, 10)), 16));
        Iterator<T> it = setEntrySet.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Object key = entry.getKey();
            Object value = entry.getValue();
            Intrinsics.checkNotNull(value, "null cannot be cast to non-null type org.jetbrains.kotlin.types.UnwrappedType");
            Pair pair = TuplesKt.to(key, nonFixedTypesToResultSubstitutor.safeSubstitute((UnwrappedType) value));
            linkedHashMap.put(pair.getFirst(), pair.getSecond());
        }
        Map mapPlus = MapsKt.plus(linkedHashMap, nonFixedTypesToResult);
        Intrinsics.checkNotNull(mapPlus, "null cannot be cast to non-null type kotlin.collections.Map<org.jetbrains.kotlin.types.TypeConstructor, org.jetbrains.kotlin.types.UnwrappedType>");
        NewTypeSubstitutorByConstructorMap newTypeSubstitutorByConstructorMap = new NewTypeSubstitutorByConstructorMap(mapPlus);
        Context contextReplaceInferenceSession = ((BasicCallResolutionContext) completedCall.getContext().replaceBindingTrace(findTopLevelTrace())).replaceInferenceSession(this);
        Intrinsics.checkNotNullExpressionValue(contextReplaceInferenceSession, "replaceInferenceSession(...)");
        completeCall(completedCall, createResolvedAtomCompleter(newTypeSubstitutorByConstructorMap, (BasicCallResolutionContext) contextReplaceInferenceSession));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final BindingTrace findTopLevelTrace() {
        BuilderInferenceSession builderInferenceSession = this;
        while (true) {
            BuilderInferenceSession builderInferenceSessionFindParentBuildInferenceSession = builderInferenceSession.findParentBuildInferenceSession();
            if (builderInferenceSessionFindParentBuildInferenceSession == null) {
                BindingTrace trace = builderInferenceSession.topLevelCallContext.trace;
                Intrinsics.checkNotNullExpressionValue(trace, "trace");
                return trace;
            }
            builderInferenceSession = builderInferenceSessionFindParentBuildInferenceSession;
        }
    }

    public final void completeDoubleColonExpression(KtDoubleColonExpression expression, NewTypeSubstitutor substitutor) {
        KtSimpleNameExpression receiverExpression;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(substitutor, "substitutor");
        ResolvedAtomCompleter resolvedAtomCompleterCreateResolvedAtomCompleter = createResolvedAtomCompleter(substitutor, this.topLevelCallContext);
        DeclarationDescriptor declarationDescriptor = (DeclarationDescriptor) this.trace.get(BindingContext.DECLARATION_TO_DESCRIPTOR, expression);
        if (declarationDescriptor instanceof SimpleFunctionDescriptorImpl) {
            resolvedAtomCompleterCreateResolvedAtomCompleter.substituteFunctionLiteralDescriptor(null, (SimpleFunctionDescriptorImpl) declarationDescriptor, substitutor);
        }
        if (expression instanceof KtCallableReferenceExpression) {
            receiverExpression = ((KtCallableReferenceExpression) expression).getCallableReference();
        } else {
            if (!(expression instanceof KtClassLiteralExpression)) {
                throw new IllegalStateException("Unsupported double colon expression");
            }
            receiverExpression = expression.getReceiverExpression();
        }
        Call call = (Call) this.trace.get(BindingContext.CALL, receiverExpression);
        if (call == null) {
            return;
        }
        ResolvedCall resolvedCall = (ResolvedCall) this.trace.get(BindingContext.RESOLVED_CALL, call);
        if (resolvedCall instanceof ResolvedCallImpl) {
            TypeSubstitutor typeSubstitutorBuildSubstitutor = CallUtilKt.toOldSubstitution(substitutor).buildSubstitutor();
            ResolvedCallImpl resolvedCallImpl = (ResolvedCallImpl) resolvedCall;
            if (DescriptorUtilsKt.shouldBeSubstituteWithStubTypes(resolvedCallImpl.getResultingDescriptor())) {
                resolvedCallImpl.setResultingSubstitutor(typeSubstitutorBuildSubstitutor);
            }
            if (CallUtilKt.shouldBeSubstituteWithStubTypes(resolvedCallImpl)) {
                resolvedCallImpl.setResolvedCallSubstitutor(typeSubstitutorBuildSubstitutor);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final NewAbstractResolvedCall<?> completeCall(CallInfo callInfo, ResolvedAtomCompleter atomCompleter) {
        ResolvedCallAtom resultCallAtom = callInfo.getCallResolutionResult().getResultCallAtom();
        List<ResolvedAtom> subResolvedAtoms = resultCallAtom.getSubResolvedAtoms();
        if (subResolvedAtoms != null) {
            Iterator<T> it = subResolvedAtoms.iterator();
            while (it.hasNext()) {
                atomCompleter.completeAll((ResolvedAtom) it.next());
            }
        }
        NewAbstractResolvedCall<?> newAbstractResolvedCallCompleteResolvedCall = atomCompleter.completeResolvedCall(resultCallAtom, callInfo.getCallResolutionResult().getDiagnostics());
        BindingTrace trace = callInfo.getContext().trace;
        Intrinsics.checkNotNullExpressionValue(trace, "trace");
        if (trace instanceof TemporaryBindingTrace) {
            ((TemporaryBindingTrace) trace).commit();
        }
        return newAbstractResolvedCallCompleteResolvedCall;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ResolvedAtomCompleter createResolvedAtomCompleter(NewTypeSubstitutor resultSubstitutor, BasicCallResolutionContext context) {
        KotlinToResolvedCallTransformer kotlinToResolvedCallTransformer = this.kotlinToResolvedCallTransformer;
        ExpressionTypingServices expressionTypingServices = this.expressionTypingServices;
        ArgumentTypeResolver argumentTypeResolver = this.argumentTypeResolver;
        DoubleColonExpressionResolver doubleColonExpressionResolver = this.doubleColonExpressionResolver;
        KotlinBuiltIns builtIns = getBuiltIns();
        DeprecationResolver deprecationResolver = this.deprecationResolver;
        ModuleDescriptor moduleDescriptor = this.moduleDescriptor;
        DataFlowValueFactory dataFlowValueFactory = context.dataFlowValueFactory;
        Intrinsics.checkNotNullExpressionValue(dataFlowValueFactory, "dataFlowValueFactory");
        return new ResolvedAtomCompleter(resultSubstitutor, context, kotlinToResolvedCallTransformer, expressionTypingServices, argumentTypeResolver, doubleColonExpressionResolver, builtIns, deprecationResolver, moduleDescriptor, dataFlowValueFactory, this.typeApproximator, this.missingSupertypesResolver, getCallComponents());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean clearCallsInfoByContainingElement$lambda$19(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return ((Boolean) tmp0.invoke(obj)).booleanValue();
    }

    public final void clearCallsInfoByContainingElement(final KtElement containingElement) {
        Intrinsics.checkNotNullParameter(containingElement, "containingElement");
        ArrayList<PSICompletedCallInfo> arrayList = this.commonCalls;
        final Function1<PSICompletedCallInfo, Boolean> function1 = new Function1<PSICompletedCallInfo, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.inference.BuilderInferenceSession.clearCallsInfoByContainingElement.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(PSICompletedCallInfo callInfo) {
                Intrinsics.checkNotNullParameter(callInfo, "callInfo");
                final KotlinCall atom = callInfo.getCallResolutionResult().getResultCallAtom().getAtom();
                if (!(atom instanceof PSIKotlinCallImpl)) {
                    return false;
                }
                KtElement ktElement = containingElement;
                final Function1<KtElement, Boolean> function2 = new Function1<KtElement, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.inference.BuilderInferenceSession.clearCallsInfoByContainingElement.1.1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Boolean invoke(KtElement it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        return Boolean.valueOf(Intrinsics.areEqual(it, ((PSIKotlinCallImpl) atom).getPsiCall().getCallElement()));
                    }
                };
                PsiUtilsKt.checkDecompiledText(ktElement);
                final Ref.ObjectRef objectRef = new Ref.ObjectRef();
                ktElement.accept(new PsiRecursiveElementWalkingVisitor() { // from class: org.jetbrains.kotlin.resolve.calls.inference.BuilderInferenceSession$clearCallsInfoByContainingElement$1$invoke$$inlined$anyDescendantOfType$1
                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiRecursiveElementWalkingVisitor, org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor
                    public void visitElement(PsiElement element) {
                        Intrinsics.checkNotNullParameter(element, "element");
                        if ((element instanceof KtElement) && ((Boolean) function2.invoke(element)).booleanValue()) {
                            objectRef.element = element;
                            stopWalking();
                        } else {
                            super.visitElement(element);
                        }
                    }
                });
                return Boolean.valueOf(((PsiElement) objectRef.element) != null);
            }
        };
        arrayList.removeIf(new Predicate() { // from class: org.jetbrains.kotlin.resolve.calls.inference.BuilderInferenceSession$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return BuilderInferenceSession.clearCallsInfoByContainingElement$lambda$19(function1, obj);
            }
        });
    }

    private final InitialConstraint substitute(InitialConstraint initialConstraint, NewTypeSubstitutor newTypeSubstitutor) {
        BuilderInferenceSubstitutionConstraintPositionImpl builderInferenceSubstitutionConstraintPositionImpl;
        KotlinTypeMarker a = initialConstraint.getA();
        Intrinsics.checkNotNull(a, "null cannot be cast to non-null type org.jetbrains.kotlin.types.UnwrappedType");
        UnwrappedType unwrappedTypeSafeSubstitute = newTypeSubstitutor.safeSubstitute((UnwrappedType) a);
        KotlinTypeMarker b = initialConstraint.getB();
        Intrinsics.checkNotNull(b, "null cannot be cast to non-null type org.jetbrains.kotlin.types.UnwrappedType");
        UnwrappedType unwrappedTypeSafeSubstitute2 = newTypeSubstitutor.safeSubstitute((UnwrappedType) b);
        if (Intrinsics.areEqual(unwrappedTypeSafeSubstitute, initialConstraint.getA()) && Intrinsics.areEqual(unwrappedTypeSafeSubstitute2, initialConstraint.getB())) {
            return initialConstraint;
        }
        boolean zSupportsFeature = this.expressionTypingServices.getLanguageVersionSettings().supportsFeature(LanguageFeature.ForbidInferringPostponedTypeVariableIntoDeclaredUpperBound);
        boolean z = Intrinsics.areEqual(unwrappedTypeSafeSubstitute2, initialConstraint.getB()) && (initialConstraint.getPosition() instanceof DeclaredUpperBoundConstraintPosition);
        if (z && zSupportsFeature) {
            builderInferenceSubstitutionConstraintPositionImpl = initialConstraint.getPosition();
        } else {
            builderInferenceSubstitutionConstraintPositionImpl = new BuilderInferenceSubstitutionConstraintPositionImpl(this.lambdaArgument, initialConstraint, z);
        }
        return new InitialConstraint(unwrappedTypeSafeSubstitute, unwrappedTypeSafeSubstitute2, initialConstraint.getConstraintKind(), builderInferenceSubstitutionConstraintPositionImpl);
    }

    /* JADX INFO: compiled from: BuilderInferenceSession.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J*\u0010\u0003\u001a\u00020\u0004*\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bH\u0002¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/BuilderInferenceSession$Companion;", "", "()V", "updateCalls", "", "Lorg/jetbrains/kotlin/resolve/calls/inference/BuilderInferenceSession;", "lambda", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedLambdaAtom;", "substitutor", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/NewTypeSubstitutor;", "errors", "", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintSystemError;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Multi-variable type inference failed */
        public final void updateCalls(BuilderInferenceSession builderInferenceSession, ResolvedLambdaAtom resolvedLambdaAtom, NewTypeSubstitutor newTypeSubstitutor, List<? extends ConstraintSystemError> list) {
            NewTypeSubstitutorByConstructorMap newTypeSubstitutorByConstructorMapCreateNonFixedTypeToVariableSubstitutor = builderInferenceSession.createNonFixedTypeToVariableSubstitutor();
            Map<TypeConstructor, UnwrappedType> map = newTypeSubstitutorByConstructorMapCreateNonFixedTypeToVariableSubstitutor.getMap();
            LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt.mapCapacity(map.size()));
            Iterator<T> it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                linkedHashMap.put(entry.getKey(), newTypeSubstitutor.safeSubstitute((UnwrappedType) entry.getValue()));
            }
            ComposedSubstitutor composedSubstitutor = new ComposedSubstitutor(newTypeSubstitutor, newTypeSubstitutorByConstructorMapCreateNonFixedTypeToVariableSubstitutor);
            Context contextReplaceInferenceSession = ((BasicCallResolutionContext) builderInferenceSession.topLevelCallContext.replaceBindingTrace(builderInferenceSession.findTopLevelTrace())).replaceInferenceSession(builderInferenceSession);
            Intrinsics.checkNotNullExpressionValue(contextReplaceInferenceSession, "replaceInferenceSession(...)");
            ResolvedAtomCompleter resolvedAtomCompleterCreateResolvedAtomCompleter = builderInferenceSession.createResolvedAtomCompleter(composedSubstitutor, (BasicCallResolutionContext) contextReplaceInferenceSession);
            for (KtExpression ktExpression : builderInferenceSession.commonExpressions) {
                Intrinsics.checkNotNull(ktExpression);
                builderInferenceSession.updateExpressionDescriptorAndType(ktExpression, composedSubstitutor);
            }
            for (PSICompletedCallInfo pSICompletedCallInfo : builderInferenceSession.commonCalls) {
                Intrinsics.checkNotNull(pSICompletedCallInfo);
                builderInferenceSession.updateCall(pSICompletedCallInfo, composedSubstitutor, linkedHashMap);
                builderInferenceSession.reportErrors(pSICompletedCallInfo, pSICompletedCallInfo.getResolvedCall(), list);
            }
            for (PSIPartialCallInfo pSIPartialCallInfo : builderInferenceSession.getCommonPartiallyResolvedCalls()) {
                Intrinsics.checkNotNull(pSIPartialCallInfo);
                PSIPartialCallInfo pSIPartialCallInfo2 = pSIPartialCallInfo;
                NewAbstractResolvedCall newAbstractResolvedCallCompleteCall = builderInferenceSession.completeCall(pSIPartialCallInfo2, resolvedAtomCompleterCreateResolvedAtomCompleter);
                if (newAbstractResolvedCallCompleteCall != null) {
                    builderInferenceSession.reportErrors(pSIPartialCallInfo2, newAbstractResolvedCallCompleteCall, list);
                }
            }
            resolvedAtomCompleterCreateResolvedAtomCompleter.completeAll(resolvedLambdaAtom);
        }
    }
}
