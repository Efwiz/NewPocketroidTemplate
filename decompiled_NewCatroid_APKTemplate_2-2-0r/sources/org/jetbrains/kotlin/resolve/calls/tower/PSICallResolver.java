package org.jetbrains.kotlin.resolve.calls.tower;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.koushikdutta.async.http.cache.ResponseCacheMiddleware;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.com.intellij.openapi.util.Ref;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.contracts.EffectSystem;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.extensions.internal.CandidateInterceptor;
import org.jetbrains.kotlin.incremental.KotlinLookupLocation;
import org.jetbrains.kotlin.incremental.components.LookupLocation;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtBlockExpression;
import org.jetbrains.kotlin.psi.KtCallableReferenceExpression;
import org.jetbrains.kotlin.psi.KtCollectionLiteralExpression;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtLambdaExpression;
import org.jetbrains.kotlin.psi.KtProjectionKind;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.psi.KtReferenceExpression;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.KtTypeProjection;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.psi.LambdaArgument;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.psi.ValueArgumentName;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.MissingSupertypesResolver;
import org.jetbrains.kotlin.resolve.ModifierCheckerCore;
import org.jetbrains.kotlin.resolve.TemporaryBindingTrace;
import org.jetbrains.kotlin.resolve.TypeResolver;
import org.jetbrains.kotlin.resolve.calls.ArgumentTypeResolver;
import org.jetbrains.kotlin.resolve.calls.CallTransformer;
import org.jetbrains.kotlin.resolve.calls.GenericCandidateResolverKt;
import org.jetbrains.kotlin.resolve.calls.KotlinCallResolver;
import org.jetbrains.kotlin.resolve.calls.checkers.CallCheckerWithAdditionalResolve;
import org.jetbrains.kotlin.resolve.calls.components.InferenceSession;
import org.jetbrains.kotlin.resolve.calls.components.KotlinResolutionCallbacks;
import org.jetbrains.kotlin.resolve.calls.components.PostponedArgumentsAnalyzer;
import org.jetbrains.kotlin.resolve.calls.components.candidate.ResolutionCandidate;
import org.jetbrains.kotlin.resolve.calls.context.BasicCallResolutionContext;
import org.jetbrains.kotlin.resolve.calls.context.CallPosition;
import org.jetbrains.kotlin.resolve.calls.context.ContextDependency;
import org.jetbrains.kotlin.resolve.calls.inference.DescriptorRelatedInferenceUtilsKt;
import org.jetbrains.kotlin.resolve.calls.inference.NewConstraintSystem;
import org.jetbrains.kotlin.resolve.calls.inference.components.KotlinConstraintSystemCompleter;
import org.jetbrains.kotlin.resolve.calls.inference.components.ResultTypeResolver;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintStorage;
import org.jetbrains.kotlin.resolve.calls.model.AllCandidatesResolutionResult;
import org.jetbrains.kotlin.resolve.calls.model.CallResolutionResult;
import org.jetbrains.kotlin.resolve.calls.model.CandidateWithDiagnostics;
import org.jetbrains.kotlin.resolve.calls.model.GivenCandidate;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCall;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallComponents;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallDiagnostic;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallDiagnosticsKt;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallKind;
import org.jetbrains.kotlin.resolve.calls.model.LHSResult;
import org.jetbrains.kotlin.resolve.calls.model.ManyCandidatesCallDiagnostic;
import org.jetbrains.kotlin.resolve.calls.model.MutableDataFlowInfoForArguments;
import org.jetbrains.kotlin.resolve.calls.model.NoneCandidatesCallDiagnostic;
import org.jetbrains.kotlin.resolve.calls.model.PartialCallContainer;
import org.jetbrains.kotlin.resolve.calls.model.PartialCallResolutionResult;
import org.jetbrains.kotlin.resolve.calls.model.QualifierReceiverKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.ReceiverExpressionKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.ReceiverKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.ResolutionAtomsKt;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCallAtom;
import org.jetbrains.kotlin.resolve.calls.model.SimpleCandidateFactory;
import org.jetbrains.kotlin.resolve.calls.model.SimpleKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.TypeArgument;
import org.jetbrains.kotlin.resolve.calls.model.TypeArgumentPlaceholder;
import org.jetbrains.kotlin.resolve.calls.results.AllCandidates;
import org.jetbrains.kotlin.resolve.calls.results.ManyCandidates;
import org.jetbrains.kotlin.resolve.calls.results.OverloadResolutionResults;
import org.jetbrains.kotlin.resolve.calls.results.OverloadResolutionResultsImpl;
import org.jetbrains.kotlin.resolve.calls.results.ResolutionStatus;
import org.jetbrains.kotlin.resolve.calls.results.SingleOverloadResolutionResult;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValue;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValueFactory;
import org.jetbrains.kotlin.resolve.calls.tasks.DynamicCallableDescriptors;
import org.jetbrains.kotlin.resolve.calls.tasks.OldResolutionCandidate;
import org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy;
import org.jetbrains.kotlin.resolve.calls.util.CallMaker;
import org.jetbrains.kotlin.resolve.calls.util.CallResolverUtilKt;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.calls.util.PsiUtilsKt;
import org.jetbrains.kotlin.resolve.checkers.PassingProgressionAsCollectionCallChecker;
import org.jetbrains.kotlin.resolve.checkers.ResolutionWithStubTypesChecker;
import org.jetbrains.kotlin.resolve.constants.evaluate.ConstantExpressionEvaluator;
import org.jetbrains.kotlin.resolve.deprecation.DeprecationResolver;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.lazy.ForceResolveUtil;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.resolve.scopes.LexicalScopeKind;
import org.jetbrains.kotlin.resolve.scopes.LexicalWritableScope;
import org.jetbrains.kotlin.resolve.scopes.LocalRedeclarationChecker;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.resolve.scopes.ResolutionScope;
import org.jetbrains.kotlin.resolve.scopes.SyntheticScopes;
import org.jetbrains.kotlin.resolve.scopes.receivers.ClassQualifier;
import org.jetbrains.kotlin.resolve.scopes.receivers.ExpressionReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.Qualifier;
import org.jetbrains.kotlin.resolve.scopes.receivers.QualifierReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.QualifierReceiverKt;
import org.jetbrains.kotlin.resolve.scopes.receivers.Receiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValueWithSmartCastInfo;
import org.jetbrains.kotlin.resolve.source.PsiSourceElementKt;
import org.jetbrains.kotlin.types.DeferredType;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeKt;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeApproximator;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.expressions.DoubleColonExpressionResolver;
import org.jetbrains.kotlin.types.expressions.DoubleColonLHS;
import org.jetbrains.kotlin.types.expressions.ExpressionTypingContext;
import org.jetbrains.kotlin.types.expressions.ExpressionTypingServices;
import org.jetbrains.kotlin.types.expressions.KotlinTypeInfo;
import org.jetbrains.kotlin.types.expressions.OperatorConventions;
import org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext;
import org.jetbrains.kotlin.util.slicedMap.WritableSlice;
import org.jetbrains.kotlin.utils.KotlinExceptionWithAttachments;
import org.jetbrains.kotlin.utils.addToStdlib.AddToStdlibKt;

/* JADX INFO: compiled from: PSICallResolver.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0094\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u00002\u00020\u0001:\u0004\u009e\u0001\u009f\u0001Bµ\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u0012\u0006\u0010\u0012\u001a\u00020\u0013\u0012\u0006\u0010\u0014\u001a\u00020\u0015\u0012\u0006\u0010\u0016\u001a\u00020\u0017\u0012\u0006\u0010\u0018\u001a\u00020\u0019\u0012\u0006\u0010\u001a\u001a\u00020\u001b\u0012\u0006\u0010\u001c\u001a\u00020\u001d\u0012\u0006\u0010\u001e\u001a\u00020\u001f\u0012\u0006\u0010 \u001a\u00020!\u0012\u0006\u0010\"\u001a\u00020#\u0012\u0006\u0010$\u001a\u00020%\u0012\u0006\u0010&\u001a\u00020'\u0012\u0006\u0010(\u001a\u00020)\u0012\u0006\u0010*\u001a\u00020+\u0012\u0006\u0010,\u001a\u00020-¢\u0006\u0002\u0010.J\u0012\u0010?\u001a\u0004\u0018\u00010@2\u0006\u0010A\u001a\u00020BH\u0002JB\u0010C\u001a\u00020D\"\b\b\u0000\u0010E*\u00020F2\f\u0010G\u001a\b\u0012\u0004\u0012\u0002HE0H2\u0006\u0010I\u001a\u00020J2\u0006\u0010K\u001a\u00020L2\b\u0010M\u001a\u0004\u0018\u00010@2\u0006\u0010A\u001a\u00020BH\u0002J\b\u0010N\u001a\u00020DH\u0002J.\u0010O\u001a\b\u0012\u0004\u0012\u0002HE0H\"\b\b\u0000\u0010E*\u00020F2\u0006\u0010A\u001a\u00020B2\u0006\u0010P\u001a\u00020Q2\u0006\u0010R\u001a\u00020SJ@\u0010T\u001a\u00020U2\u0006\u0010A\u001a\u00020B2\u0006\u0010V\u001a\u00020W2\u0006\u0010X\u001a\u00020Y2\u0006\u0010Z\u001a\u00020[2\b\u0010\\\u001a\u0004\u0018\u00010:2\u0006\u0010]\u001a\u00020B2\u0006\u0010R\u001a\u00020SJ\u001e\u0010^\u001a\u00020_2\u0006\u0010`\u001a\u00020a2\u0006\u0010b\u001a\u00020c2\u0006\u0010A\u001a\u00020BJ\u0010\u0010^\u001a\u00020_2\u0006\u0010A\u001a\u00020BH\u0002J$\u0010d\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010f\u0012\u0004\u0012\u00020g0e2\u0006\u0010A\u001a\u00020B2\u0006\u0010V\u001a\u00020WJ:\u0010h\u001a\n\u0012\u0004\u0012\u0002HE\u0018\u00010H\"\b\b\u0000\u0010E*\u00020F2\u0006\u0010A\u001a\u00020B2\u0006\u0010`\u001a\u00020a2\u0006\u0010P\u001a\u00020Q2\u0006\u0010R\u001a\u00020SH\u0002J\u0018\u0010i\u001a\u00020:2\u0006\u0010j\u001a\u0002002\u0006\u0010k\u001a\u00020:H\u0002J(\u0010l\u001a\u0002002\u0006\u0010A\u001a\u00020B2\u0006\u0010I\u001a\u00020J2\u0006\u0010m\u001a\u00020n2\u0006\u0010o\u001a\u00020pH\u0002J4\u0010q\u001a\b\u0012\u0004\u0012\u00020r022\u0006\u0010A\u001a\u00020B2\f\u0010s\u001a\b\u0012\u0004\u0012\u00020[022\u0006\u0010t\u001a\u0002002\u0006\u0010R\u001a\u00020SH\u0002J\"\u0010u\u001a\u0004\u0018\u00010v2\u0006\u0010A\u001a\u00020B2\u0006\u0010w\u001a\u00020n2\u0006\u0010x\u001a\u00020yH\u0002J,\u0010z\u001a\u0004\u0018\u00010v2\u0006\u0010A\u001a\u00020B2\b\u0010{\u001a\u0004\u0018\u00010|2\u0006\u0010}\u001a\u0002002\u0006\u0010~\u001a\u000200H\u0002JC\u0010\u007f\u001a\u00020Q2\u0007\u0010\u0080\u0001\u001a\u00020:2\u0006\u0010A\u001a\u00020B2\u0006\u0010w\u001a\u00020n2\u0006\u0010R\u001a\u00020S2\u0006\u0010I\u001a\u00020J2\u0006\u0010K\u001a\u00020_2\b\u0010M\u001a\u0004\u0018\u00010@H\u0002J(\u0010\u0081\u0001\u001a\t\u0012\u0005\u0012\u00030\u0082\u0001022\u0006\u0010A\u001a\u00020B2\u000e\u0010\u0083\u0001\u001a\t\u0012\u0005\u0012\u00030\u0084\u000102H\u0002J2\u0010\u0085\u0001\u001a\u00030\u0086\u00012\u0006\u0010]\u001a\u00020B2\u0006\u0010X\u001a\u00020Y2\u0006\u0010Z\u001a\u00020[2\u0006\u0010t\u001a\u0002002\u0006\u0010R\u001a\u00020SH\u0002J8\u0010\u0087\u0001\u001a\b\u0012\u0004\u0012\u0002HE0H\"\b\b\u0000\u0010E*\u00020F2\u0006\u0010A\u001a\u00020B2\u0006\u0010k\u001a\u00020:2\u0007\u0010\u0088\u0001\u001a\u0002062\u0006\u0010R\u001a\u00020SJ>\u0010\u0089\u0001\u001a\b\u0012\u0004\u0012\u0002HE0H\"\b\b\u0000\u0010E*\u00020F2\u0006\u0010A\u001a\u00020B2\u0015\u0010\u008a\u0001\u001a\u0010\u0012\u000b\u0012\t\u0012\u0004\u0012\u0002HE0\u008c\u00010\u008b\u00012\u0006\u0010R\u001a\u00020SJG\u0010\u008d\u0001\u001a\u00030\u008e\u00012\u0006\u0010A\u001a\u00020B2\u0006\u0010w\u001a\u00020n2\u0006\u0010x\u001a\u00020y2\u0006\u0010k\u001a\u00020:2\u0006\u0010R\u001a\u00020S2\u0006\u0010t\u001a\u0002002\u000b\b\u0002\u0010\u008f\u0001\u001a\u0004\u0018\u00010|H\u0002J<\u0010\u0090\u0001\u001a\t\u0012\u0004\u0012\u0002HE0\u0091\u0001\"\b\b\u0000\u0010E*\u00020F2\b\u0010\u0092\u0001\u001a\u00030\u0093\u00012\u0006\u0010R\u001a\u00020S2\u0006\u0010`\u001a\u00020a2\u0006\u0010A\u001a\u00020BH\u0002J\u0015\u0010\u0094\u0001\u001a\u000200*\n\u0012\u0005\u0012\u00030\u0095\u00010\u008b\u0001H\u0002J\u0015\u0010\u0096\u0001\u001a\u000200*\n\u0012\u0005\u0012\u00030\u0095\u00010\u008b\u0001H\u0002J\r\u0010\u0097\u0001\u001a\u000200*\u00020QH\u0002J\u0015\u0010\u0098\u0001\u001a\u00020B*\u00020B2\u0006\u0010V\u001a\u00020\u0001H\u0002J\r\u0010\u0099\u0001\u001a\u000200*\u00020QH\u0002J\u0019\u0010\u009a\u0001\u001a\u00020D*\u0006\u0012\u0002\b\u00030<2\u0006\u0010`\u001a\u00020aH\u0002J!\u0010\u009b\u0001\u001a\u00020D*\u0006\u0012\u0002\b\u00030<2\u0006\u0010`\u001a\u00020a2\u0006\u0010&\u001a\u00020'H\u0002J!\u0010\u009c\u0001\u001a\u00020D*\u0006\u0012\u0002\b\u00030<2\u0006\u0010`\u001a\u00020a2\u0006\u0010&\u001a\u00020'H\u0002J\r\u0010\u009d\u0001\u001a\u00020n*\u000206H\u0002R\u000e\u0010/\u001a\u000200X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u00101\u001a\b\u0012\u0004\u0012\u00020302X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020)X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001dX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u00104\u001a\b\u0012\u0004\u0012\u00020605¢\u0006\b\n\u0000\u001a\u0004\b7\u00108R\u000e\u0010$\u001a\u00020%X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00109\u001a\u00020:X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020#X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010*\u001a\u00020+X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020'X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020!X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010,\u001a\u00020-X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\"\u0010;\u001a\u000200*\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030<028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b=\u0010>¨\u0006 \u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/PSICallResolver;", "", "typeResolver", "Lorg/jetbrains/kotlin/resolve/TypeResolver;", "expressionTypingServices", "Lorg/jetbrains/kotlin/types/expressions/ExpressionTypingServices;", "doubleColonExpressionResolver", "Lorg/jetbrains/kotlin/types/expressions/DoubleColonExpressionResolver;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "dynamicCallableDescriptors", "Lorg/jetbrains/kotlin/resolve/calls/tasks/DynamicCallableDescriptors;", "syntheticScopes", "Lorg/jetbrains/kotlin/resolve/scopes/SyntheticScopes;", "callComponents", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallComponents;", "kotlinToResolvedCallTransformer", "Lorg/jetbrains/kotlin/resolve/calls/tower/KotlinToResolvedCallTransformer;", "kotlinCallResolver", "Lorg/jetbrains/kotlin/resolve/calls/KotlinCallResolver;", "typeApproximator", "Lorg/jetbrains/kotlin/types/TypeApproximator;", "implicitsResolutionFilter", "Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitsExtensionsResolutionFilter;", "argumentTypeResolver", "Lorg/jetbrains/kotlin/resolve/calls/ArgumentTypeResolver;", "effectSystem", "Lorg/jetbrains/kotlin/contracts/EffectSystem;", "constantExpressionEvaluator", "Lorg/jetbrains/kotlin/resolve/constants/evaluate/ConstantExpressionEvaluator;", "dataFlowValueFactory", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValueFactory;", "postponedArgumentsAnalyzer", "Lorg/jetbrains/kotlin/resolve/calls/components/PostponedArgumentsAnalyzer;", "kotlinConstraintSystemCompleter", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/KotlinConstraintSystemCompleter;", "deprecationResolver", "Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationResolver;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "candidateInterceptor", "Lorg/jetbrains/kotlin/extensions/internal/CandidateInterceptor;", "missingSupertypesResolver", "Lorg/jetbrains/kotlin/resolve/MissingSupertypesResolver;", "resultTypeResolver", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/ResultTypeResolver;", "(Lorg/jetbrains/kotlin/resolve/TypeResolver;Lorg/jetbrains/kotlin/types/expressions/ExpressionTypingServices;Lorg/jetbrains/kotlin/types/expressions/DoubleColonExpressionResolver;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Lorg/jetbrains/kotlin/resolve/calls/tasks/DynamicCallableDescriptors;Lorg/jetbrains/kotlin/resolve/scopes/SyntheticScopes;Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallComponents;Lorg/jetbrains/kotlin/resolve/calls/tower/KotlinToResolvedCallTransformer;Lorg/jetbrains/kotlin/resolve/calls/KotlinCallResolver;Lorg/jetbrains/kotlin/types/TypeApproximator;Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitsExtensionsResolutionFilter;Lorg/jetbrains/kotlin/resolve/calls/ArgumentTypeResolver;Lorg/jetbrains/kotlin/contracts/EffectSystem;Lorg/jetbrains/kotlin/resolve/constants/evaluate/ConstantExpressionEvaluator;Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValueFactory;Lorg/jetbrains/kotlin/resolve/calls/components/PostponedArgumentsAnalyzer;Lorg/jetbrains/kotlin/resolve/calls/inference/components/KotlinConstraintSystemCompleter;Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationResolver;Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/extensions/internal/CandidateInterceptor;Lorg/jetbrains/kotlin/resolve/MissingSupertypesResolver;Lorg/jetbrains/kotlin/resolve/calls/inference/components/ResultTypeResolver;)V", "arePartiallySpecifiedTypeArgumentsEnabled", "", "callCheckersWithAdditionalResolve", "", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerWithAdditionalResolve;", "defaultResolutionKinds", "", "Lorg/jetbrains/kotlin/resolve/calls/tower/NewResolutionOldInference$ResolutionKind;", "getDefaultResolutionKinds", "()Ljava/util/Set;", "givenCandidatesName", "Lorg/jetbrains/kotlin/name/Name;", "allIncomplete", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "getAllIncomplete", "(Ljava/util/List;)Z", "calculateExpectedType", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "context", "Lorg/jetbrains/kotlin/resolve/calls/context/BasicCallResolutionContext;", "checkCallWithAdditionalResolve", "", "D", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "overloadResolutionResults", "Lorg/jetbrains/kotlin/resolve/calls/results/OverloadResolutionResults;", "scopeTower", "Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;", "resolutionCallbacks", "Lorg/jetbrains/kotlin/resolve/calls/components/KotlinResolutionCallbacks;", "expectedType", "clearCacheForApproximationResults", "convertToOverloadResolutionResults", "result", "Lorg/jetbrains/kotlin/resolve/calls/model/CallResolutionResult;", "tracingStrategy", "Lorg/jetbrains/kotlin/resolve/calls/tasks/TracingStrategy;", "createCallableReferenceKotlinCallArgument", "Lorg/jetbrains/kotlin/resolve/calls/tower/CallableReferenceKotlinCallArgumentImpl;", "ktExpression", "Lorg/jetbrains/kotlin/psi/KtCallableReferenceExpression;", "startDataFlowInfo", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;", "valueArgument", "Lorg/jetbrains/kotlin/psi/ValueArgument;", "argumentName", "outerCallContext", "createResolutionCallbacks", "Lorg/jetbrains/kotlin/resolve/calls/tower/KotlinResolutionCallbacksImpl;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "inferenceSession", "Lorg/jetbrains/kotlin/resolve/calls/components/InferenceSession;", "getLhsResult", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/types/expressions/DoubleColonLHS;", "Lorg/jetbrains/kotlin/resolve/calls/model/LHSResult;", "handleErrorResolutionResult", "refineNameForRemOperator", "isBinaryRemOperator", "name", "reportAdditionalDiagnosticIfNoCandidates", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallKind;", "kotlinCall", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCall;", "resolveArgumentsInParenthesis", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallArgument;", "arguments", "isSpecialFunction", "resolveDispatchReceiverForInvoke", "Lorg/jetbrains/kotlin/resolve/calls/model/ReceiverKotlinCallArgument;", "kotlinCallKind", "oldCall", "Lorg/jetbrains/kotlin/psi/Call;", "resolveReceiver", "oldReceiver", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/Receiver;", "isSafeCall", "isForImplicitInvoke", "resolveToDeprecatedMod", "remOperatorName", "resolveTypeArguments", "Lorg/jetbrains/kotlin/resolve/calls/model/TypeArgument;", "typeArguments", "Lorg/jetbrains/kotlin/psi/KtTypeProjection;", "resolveValueArgument", "Lorg/jetbrains/kotlin/resolve/calls/tower/PSIKotlinCallArgument;", "runResolutionAndInference", "resolutionKind", "runResolutionAndInferenceForGivenCandidates", "resolutionCandidates", "", "Lorg/jetbrains/kotlin/resolve/calls/tasks/OldResolutionCandidate;", "toKotlinCall", "Lorg/jetbrains/kotlin/resolve/calls/tower/PSIKotlinCallImpl;", "forcedExplicitReceiver", "transformManyCandidatesAndRecordTrace", "Lorg/jetbrains/kotlin/resolve/calls/results/ManyCandidates;", "diagnostic", "Lorg/jetbrains/kotlin/resolve/calls/model/ManyCandidatesCallDiagnostic;", "areAllFailed", "Lorg/jetbrains/kotlin/resolve/calls/components/candidate/ResolutionCandidate;", "areAllFailedWithInapplicableWrongReceiver", "areAllInapplicable", "expandContextForCatchClause", "isEmpty", "recordEffects", "recordLambdasInvocations", "recordResultInfo", "toKotlinCallKind", "ASTScopeTower", "FactoryProviderForInvoke", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PSICallResolver {
    private final boolean arePartiallySpecifiedTypeArgumentsEnabled;
    private final ArgumentTypeResolver argumentTypeResolver;
    private final List<CallCheckerWithAdditionalResolve> callCheckersWithAdditionalResolve;
    private final KotlinCallComponents callComponents;
    private final CandidateInterceptor candidateInterceptor;
    private final ConstantExpressionEvaluator constantExpressionEvaluator;
    private final DataFlowValueFactory dataFlowValueFactory;
    private final Set<NewResolutionOldInference.ResolutionKind> defaultResolutionKinds;
    private final DeprecationResolver deprecationResolver;
    private final DoubleColonExpressionResolver doubleColonExpressionResolver;
    private final DynamicCallableDescriptors dynamicCallableDescriptors;
    private final EffectSystem effectSystem;
    private final ExpressionTypingServices expressionTypingServices;
    private final Name givenCandidatesName;
    private final ImplicitsExtensionsResolutionFilter implicitsResolutionFilter;
    private final KotlinCallResolver kotlinCallResolver;
    private final KotlinConstraintSystemCompleter kotlinConstraintSystemCompleter;
    private final KotlinToResolvedCallTransformer kotlinToResolvedCallTransformer;
    private final LanguageVersionSettings languageVersionSettings;
    private final MissingSupertypesResolver missingSupertypesResolver;
    private final ModuleDescriptor moduleDescriptor;
    private final PostponedArgumentsAnalyzer postponedArgumentsAnalyzer;
    private final ResultTypeResolver resultTypeResolver;
    private final SyntheticScopes syntheticScopes;
    private final TypeApproximator typeApproximator;
    private final TypeResolver typeResolver;

    /* JADX INFO: compiled from: PSICallResolver.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[KotlinCallKind.values().length];
            try {
                iArr[KotlinCallKind.FUNCTION.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[KotlinCallKind.VARIABLE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public PSICallResolver(TypeResolver typeResolver, ExpressionTypingServices expressionTypingServices, DoubleColonExpressionResolver doubleColonExpressionResolver, LanguageVersionSettings languageVersionSettings, DynamicCallableDescriptors dynamicCallableDescriptors, SyntheticScopes syntheticScopes, KotlinCallComponents callComponents, KotlinToResolvedCallTransformer kotlinToResolvedCallTransformer, KotlinCallResolver kotlinCallResolver, TypeApproximator typeApproximator, ImplicitsExtensionsResolutionFilter implicitsResolutionFilter, ArgumentTypeResolver argumentTypeResolver, EffectSystem effectSystem, ConstantExpressionEvaluator constantExpressionEvaluator, DataFlowValueFactory dataFlowValueFactory, PostponedArgumentsAnalyzer postponedArgumentsAnalyzer, KotlinConstraintSystemCompleter kotlinConstraintSystemCompleter, DeprecationResolver deprecationResolver, ModuleDescriptor moduleDescriptor, CandidateInterceptor candidateInterceptor, MissingSupertypesResolver missingSupertypesResolver, ResultTypeResolver resultTypeResolver) {
        Intrinsics.checkNotNullParameter(typeResolver, "typeResolver");
        Intrinsics.checkNotNullParameter(expressionTypingServices, "expressionTypingServices");
        Intrinsics.checkNotNullParameter(doubleColonExpressionResolver, "doubleColonExpressionResolver");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(dynamicCallableDescriptors, "dynamicCallableDescriptors");
        Intrinsics.checkNotNullParameter(syntheticScopes, "syntheticScopes");
        Intrinsics.checkNotNullParameter(callComponents, "callComponents");
        Intrinsics.checkNotNullParameter(kotlinToResolvedCallTransformer, "kotlinToResolvedCallTransformer");
        Intrinsics.checkNotNullParameter(kotlinCallResolver, "kotlinCallResolver");
        Intrinsics.checkNotNullParameter(typeApproximator, "typeApproximator");
        Intrinsics.checkNotNullParameter(implicitsResolutionFilter, "implicitsResolutionFilter");
        Intrinsics.checkNotNullParameter(argumentTypeResolver, "argumentTypeResolver");
        Intrinsics.checkNotNullParameter(effectSystem, "effectSystem");
        Intrinsics.checkNotNullParameter(constantExpressionEvaluator, "constantExpressionEvaluator");
        Intrinsics.checkNotNullParameter(dataFlowValueFactory, "dataFlowValueFactory");
        Intrinsics.checkNotNullParameter(postponedArgumentsAnalyzer, "postponedArgumentsAnalyzer");
        Intrinsics.checkNotNullParameter(kotlinConstraintSystemCompleter, "kotlinConstraintSystemCompleter");
        Intrinsics.checkNotNullParameter(deprecationResolver, "deprecationResolver");
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        Intrinsics.checkNotNullParameter(candidateInterceptor, "candidateInterceptor");
        Intrinsics.checkNotNullParameter(missingSupertypesResolver, "missingSupertypesResolver");
        Intrinsics.checkNotNullParameter(resultTypeResolver, "resultTypeResolver");
        this.typeResolver = typeResolver;
        this.expressionTypingServices = expressionTypingServices;
        this.doubleColonExpressionResolver = doubleColonExpressionResolver;
        this.languageVersionSettings = languageVersionSettings;
        this.dynamicCallableDescriptors = dynamicCallableDescriptors;
        this.syntheticScopes = syntheticScopes;
        this.callComponents = callComponents;
        this.kotlinToResolvedCallTransformer = kotlinToResolvedCallTransformer;
        this.kotlinCallResolver = kotlinCallResolver;
        this.typeApproximator = typeApproximator;
        this.implicitsResolutionFilter = implicitsResolutionFilter;
        this.argumentTypeResolver = argumentTypeResolver;
        this.effectSystem = effectSystem;
        this.constantExpressionEvaluator = constantExpressionEvaluator;
        this.dataFlowValueFactory = dataFlowValueFactory;
        this.postponedArgumentsAnalyzer = postponedArgumentsAnalyzer;
        this.kotlinConstraintSystemCompleter = kotlinConstraintSystemCompleter;
        this.deprecationResolver = deprecationResolver;
        this.moduleDescriptor = moduleDescriptor;
        this.candidateInterceptor = candidateInterceptor;
        this.missingSupertypesResolver = missingSupertypesResolver;
        this.resultTypeResolver = resultTypeResolver;
        this.callCheckersWithAdditionalResolve = CollectionsKt.listOf((Object[]) new CallCheckerWithAdditionalResolve[]{new PassingProgressionAsCollectionCallChecker(kotlinCallResolver), new ResolutionWithStubTypesChecker(kotlinCallResolver)});
        Name nameSpecial = Name.special("<given candidates>");
        Intrinsics.checkNotNullExpressionValue(nameSpecial, "special(...)");
        this.givenCandidatesName = nameSpecial;
        this.arePartiallySpecifiedTypeArgumentsEnabled = languageVersionSettings.supportsFeature(LanguageFeature.PartiallySpecifiedTypeArguments);
        this.defaultResolutionKinds = SetsKt.setOf((Object[]) new NewResolutionOldInference.ResolutionKind[]{NewResolutionOldInference.ResolutionKind.Function.INSTANCE, NewResolutionOldInference.ResolutionKind.Variable.INSTANCE, NewResolutionOldInference.ResolutionKind.Invoke.INSTANCE, NewResolutionOldInference.ResolutionKind.CallableReference.INSTANCE});
    }

    public final Set<NewResolutionOldInference.ResolutionKind> getDefaultResolutionKinds() {
        return this.defaultResolutionKinds;
    }

    public final <D extends CallableDescriptor> OverloadResolutionResults<D> runResolutionAndInference(BasicCallResolutionContext context, Name name2, NewResolutionOldInference.ResolutionKind resolutionKind, TracingStrategy tracingStrategy) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(resolutionKind, "resolutionKind");
        Intrinsics.checkNotNullParameter(tracingStrategy, "tracingStrategy");
        Call call = context.call;
        Intrinsics.checkNotNullExpressionValue(call, "call");
        boolean zIsBinaryRemOperator = CallResolverUtilKt.isBinaryRemOperator(call);
        Name nameRefineNameForRemOperator = refineNameForRemOperator(zIsBinaryRemOperator, name2);
        KotlinCallKind kotlinCallKind = toKotlinCallKind(resolutionKind);
        Call call2 = context.call;
        Intrinsics.checkNotNullExpressionValue(call2, "call");
        PSIKotlinCallImpl kotlinCall$default = toKotlinCall$default(this, context, kotlinCallKind, call2, nameRefineNameForRemOperator, tracingStrategy, false, null, 64, null);
        ASTScopeTower aSTScopeTower = new ASTScopeTower(this, context, null, 2, null);
        KotlinResolutionCallbacksImpl kotlinResolutionCallbacksImplCreateResolutionCallbacks = createResolutionCallbacks(context);
        UnwrappedType unwrappedTypeCalculateExpectedType = calculateExpectedType(context);
        ASTScopeTower aSTScopeTower2 = aSTScopeTower;
        KotlinResolutionCallbacksImpl kotlinResolutionCallbacksImpl = kotlinResolutionCallbacksImplCreateResolutionCallbacks;
        PSIKotlinCallImpl pSIKotlinCallImpl = kotlinCall$default;
        CallResolutionResult callResolutionResultResolveAndCompleteCall = this.kotlinCallResolver.resolveAndCompleteCall(aSTScopeTower2, kotlinResolutionCallbacksImpl, pSIKotlinCallImpl, unwrappedTypeCalculateExpectedType, context.collectAllCandidates);
        boolean zSupportsFeature = this.languageVersionSettings.supportsFeature(LanguageFeature.OperatorRem);
        if (zIsBinaryRemOperator && zSupportsFeature && (isEmpty(callResolutionResultResolveAndCompleteCall) || areAllInapplicable(callResolutionResultResolveAndCompleteCall))) {
            callResolutionResultResolveAndCompleteCall = resolveToDeprecatedMod(name2, context, kotlinCallKind, tracingStrategy, aSTScopeTower2, kotlinResolutionCallbacksImplCreateResolutionCallbacks, unwrappedTypeCalculateExpectedType);
        }
        if (isEmpty(callResolutionResultResolveAndCompleteCall) && reportAdditionalDiagnosticIfNoCandidates(context, aSTScopeTower2, kotlinCallKind, pSIKotlinCallImpl)) {
            OverloadResolutionResultsImpl overloadResolutionResultsImplNameNotFound = OverloadResolutionResultsImpl.nameNotFound();
            Intrinsics.checkNotNullExpressionValue(overloadResolutionResultsImplNameNotFound, "nameNotFound(...)");
            return overloadResolutionResultsImplNameNotFound;
        }
        OverloadResolutionResults<D> overloadResolutionResultsConvertToOverloadResolutionResults = convertToOverloadResolutionResults(context, callResolutionResultResolveAndCompleteCall, tracingStrategy);
        clearCacheForApproximationResults();
        checkCallWithAdditionalResolve(overloadResolutionResultsConvertToOverloadResolutionResults, aSTScopeTower2, kotlinResolutionCallbacksImpl, unwrappedTypeCalculateExpectedType, context);
        return overloadResolutionResultsConvertToOverloadResolutionResults;
    }

    private final <D extends CallableDescriptor> void checkCallWithAdditionalResolve(OverloadResolutionResults<D> overloadResolutionResults, ImplicitScopeTower scopeTower, KotlinResolutionCallbacks resolutionCallbacks, UnwrappedType expectedType, BasicCallResolutionContext context) {
        Iterator<CallCheckerWithAdditionalResolve> it = this.callCheckersWithAdditionalResolve.iterator();
        while (it.hasNext()) {
            it.next().check(overloadResolutionResults, scopeTower, resolutionCallbacks, expectedType, context);
        }
    }

    public final <D extends CallableDescriptor> OverloadResolutionResults<D> runResolutionAndInferenceForGivenCandidates(BasicCallResolutionContext context, Collection<? extends OldResolutionCandidate<D>> resolutionCandidates, TracingStrategy tracingStrategy) {
        ReceiverValue dispatchReceiver;
        ReceiverValueWithSmartCastInfo receiverValueWithSmartCastInfoTransformToReceiverWithSmartCastInfo;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(resolutionCandidates, "resolutionCandidates");
        Intrinsics.checkNotNullParameter(tracingStrategy, "tracingStrategy");
        Collection<? extends OldResolutionCandidate<D>> collection = resolutionCandidates;
        Iterator<T> it = collection.iterator();
        do {
            if (!it.hasNext()) {
                dispatchReceiver = null;
                break;
            }
            dispatchReceiver = ((OldResolutionCandidate) it.next()).getDispatchReceiver();
        } while (dispatchReceiver == null);
        boolean z = false;
        if (!collection.isEmpty()) {
            Iterator<T> it2 = collection.iterator();
            while (it2.hasNext()) {
                if (GenericCandidateResolverKt.getSPECIAL_FUNCTION_NAMES().contains(((OldResolutionCandidate) it2.next()).getDescriptor().getShortName())) {
                    z = true;
                    break;
                }
            }
        }
        boolean z2 = z;
        KotlinCallKind kotlinCallKind = KotlinCallKind.FUNCTION;
        Call call = context.call;
        Intrinsics.checkNotNullExpressionValue(call, "call");
        PSIKotlinCallImpl kotlinCall = toKotlinCall(context, kotlinCallKind, call, this.givenCandidatesName, tracingStrategy, z2, dispatchReceiver);
        ASTScopeTower aSTScopeTower = new ASTScopeTower(this, context, null, 2, null);
        KotlinResolutionCallbacksImpl kotlinResolutionCallbacksImplCreateResolutionCallbacks = createResolutionCallbacks(context);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
        Iterator<T> it3 = collection.iterator();
        while (it3.hasNext()) {
            OldResolutionCandidate oldResolutionCandidate = (OldResolutionCandidate) it3.next();
            CallableDescriptor descriptor = oldResolutionCandidate.getDescriptor();
            Intrinsics.checkNotNull(descriptor, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.FunctionDescriptor");
            FunctionDescriptor functionDescriptor = (FunctionDescriptor) descriptor;
            ReceiverValue dispatchReceiver2 = oldResolutionCandidate.getDispatchReceiver();
            if (dispatchReceiver2 != null) {
                Intrinsics.checkNotNull(dispatchReceiver2);
                receiverValueWithSmartCastInfoTransformToReceiverWithSmartCastInfo = NewResolutionOldInferenceKt.transformToReceiverWithSmartCastInfo(context, dispatchReceiver2);
            } else {
                receiverValueWithSmartCastInfoTransformToReceiverWithSmartCastInfo = null;
            }
            arrayList.add(new GivenCandidate(functionDescriptor, receiverValueWithSmartCastInfoTransformToReceiverWithSmartCastInfo, oldResolutionCandidate.getKnownTypeParametersResultingSubstitutor()));
        }
        OverloadResolutionResults<D> overloadResolutionResultsConvertToOverloadResolutionResults = convertToOverloadResolutionResults(context, this.kotlinCallResolver.resolveAndCompleteGivenCandidates(aSTScopeTower, kotlinResolutionCallbacksImplCreateResolutionCallbacks, kotlinCall, calculateExpectedType(context), arrayList, context.collectAllCandidates), tracingStrategy);
        clearCacheForApproximationResults();
        return overloadResolutionResultsConvertToOverloadResolutionResults;
    }

    private final void clearCacheForApproximationResults() {
        this.typeApproximator.clearCache();
    }

    private final CallResolutionResult resolveToDeprecatedMod(Name remOperatorName, BasicCallResolutionContext context, KotlinCallKind kotlinCallKind, TracingStrategy tracingStrategy, ImplicitScopeTower scopeTower, KotlinResolutionCallbacksImpl resolutionCallbacks, UnwrappedType expectedType) {
        Name name2 = OperatorConventions.REM_TO_MOD_OPERATION_NAMES.get(remOperatorName);
        Intrinsics.checkNotNull(name2);
        Call call = context.call;
        Intrinsics.checkNotNullExpressionValue(call, "call");
        return this.kotlinCallResolver.resolveAndCompleteCall(scopeTower, resolutionCallbacks, toKotlinCall$default(this, context, kotlinCallKind, call, name2, tracingStrategy, false, null, 64, null), expectedType, context.collectAllCandidates);
    }

    private final Name refineNameForRemOperator(boolean isBinaryRemOperator, Name name2) {
        boolean zSupportsFeature = this.languageVersionSettings.supportsFeature(LanguageFeature.OperatorRem);
        if (!isBinaryRemOperator || zSupportsFeature) {
            return name2;
        }
        Name name3 = OperatorConventions.REM_TO_MOD_OPERATION_NAMES.get(name2);
        Intrinsics.checkNotNull(name3);
        return name3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final KotlinResolutionCallbacksImpl createResolutionCallbacks(BasicCallResolutionContext context) {
        BindingTrace trace = context.trace;
        Intrinsics.checkNotNullExpressionValue(trace, "trace");
        InferenceSession inferenceSession = context.inferenceSession;
        Intrinsics.checkNotNullExpressionValue(inferenceSession, "inferenceSession");
        return createResolutionCallbacks(trace, inferenceSession, context);
    }

    public final KotlinResolutionCallbacksImpl createResolutionCallbacks(BindingTrace trace, InferenceSession inferenceSession, BasicCallResolutionContext context) {
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(inferenceSession, "inferenceSession");
        Intrinsics.checkNotNullParameter(context, "context");
        return new KotlinResolutionCallbacksImpl(trace, this.expressionTypingServices, this.typeApproximator, this.argumentTypeResolver, this.languageVersionSettings, this.kotlinToResolvedCallTransformer, this.dataFlowValueFactory, inferenceSession, this.constantExpressionEvaluator, this.typeResolver, this, this.postponedArgumentsAnalyzer, this.kotlinConstraintSystemCompleter, this.callComponents, this.doubleColonExpressionResolver, this.deprecationResolver, this.moduleDescriptor, context, this.missingSupertypesResolver, this.kotlinCallResolver, this.resultTypeResolver);
    }

    private final UnwrappedType calculateExpectedType(BasicCallResolutionContext context) {
        UnwrappedType unwrappedTypeUnwrap = context.expectedType.unwrap();
        if (context.contextDependency == ContextDependency.DEPENDENT) {
            if (TypeUtils.noExpectedType(unwrappedTypeUnwrap)) {
                return null;
            }
            return unwrappedTypeUnwrap;
        }
        if (!KotlinTypeKt.isError(unwrappedTypeUnwrap)) {
            return unwrappedTypeUnwrap;
        }
        SimpleType NO_EXPECTED_TYPE = TypeUtils.NO_EXPECTED_TYPE;
        Intrinsics.checkNotNullExpressionValue(NO_EXPECTED_TYPE, "NO_EXPECTED_TYPE");
        return NO_EXPECTED_TYPE;
    }

    public final <D extends CallableDescriptor> OverloadResolutionResults<D> convertToOverloadResolutionResults(BasicCallResolutionContext context, CallResolutionResult result, TracingStrategy tracingStrategy) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(result, "result");
        Intrinsics.checkNotNullParameter(tracingStrategy, "tracingStrategy");
        if (result instanceof AllCandidatesResolutionResult) {
            Collection<CandidateWithDiagnostics> allCandidates = ((AllCandidatesResolutionResult) result).getAllCandidates();
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(allCandidates, 10));
            for (CandidateWithDiagnostics candidateWithDiagnostics : allCandidates) {
                ResolutionCandidate candidate = candidateWithDiagnostics.getCandidate();
                List<KotlinCallDiagnostic> listComponent2 = candidateWithDiagnostics.component2();
                NewConstraintSystem system = candidate.getSystem();
                ConstraintStorage constraintStorageAsReadOnlyStorage = system.asReadOnlyStorage();
                Intrinsics.checkNotNull(system, "null cannot be cast to non-null type org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext");
                arrayList.add(this.kotlinToResolvedCallTransformer.transformToResolvedCall(candidate.getResolvedCall(), null, DescriptorRelatedInferenceUtilsKt.buildResultingSubstitutor$default(constraintStorageAsReadOnlyStorage, (TypeSystemInferenceExtensionContext) system, false, 2, null), listComponent2));
            }
            return new AllCandidates(arrayList);
        }
        BindingTrace trace = context.trace;
        Intrinsics.checkNotNullExpressionValue(trace, "trace");
        OverloadResolutionResults<D> overloadResolutionResultsHandleErrorResolutionResult = handleErrorResolutionResult(context, trace, result, tracingStrategy);
        if (overloadResolutionResultsHandleErrorResolutionResult != null) {
            context.inferenceSession.addErrorCallInfo(new PSIErrorCallInfo(result, overloadResolutionResultsHandleErrorResolutionResult));
            return overloadResolutionResultsHandleErrorResolutionResult;
        }
        NewAbstractResolvedCall<D> newAbstractResolvedCallTransformAndReport = this.kotlinToResolvedCallTransformer.transformAndReport(result, context, tracingStrategy);
        recordEffects(newAbstractResolvedCallTransformAndReport, trace);
        return new SingleOverloadResolutionResult(newAbstractResolvedCallTransformAndReport);
    }

    private final <D extends CallableDescriptor> OverloadResolutionResults<D> handleErrorResolutionResult(BasicCallResolutionContext context, BindingTrace trace, CallResolutionResult result, TracingStrategy tracingStrategy) {
        Object next;
        Object next2;
        List<KotlinCallDiagnostic> diagnostics = result.getDiagnostics();
        List<KotlinCallDiagnostic> list = diagnostics;
        Iterator<T> it = list.iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (!(next instanceof NoneCandidatesCallDiagnostic));
        if (((NoneCandidatesCallDiagnostic) next) != null) {
            this.kotlinToResolvedCallTransformer.transformAndReport(result, context, tracingStrategy);
            tracingStrategy.unresolvedReference(trace);
            return OverloadResolutionResultsImpl.nameNotFound();
        }
        Iterator<T> it2 = list.iterator();
        do {
            if (!it2.hasNext()) {
                next2 = null;
                break;
            }
            next2 = it2.next();
        } while (!(next2 instanceof ManyCandidatesCallDiagnostic));
        ManyCandidatesCallDiagnostic manyCandidatesCallDiagnostic = (ManyCandidatesCallDiagnostic) next2;
        if (manyCandidatesCallDiagnostic != null) {
            this.kotlinToResolvedCallTransformer.transformAndReport(result, context, tracingStrategy);
            return transformManyCandidatesAndRecordTrace(manyCandidatesCallDiagnostic, tracingStrategy, trace, context);
        }
        if (ImplicitScopeTowerKt.getResultApplicabilityForCallDiagnostics(KotlinCallDiagnosticsKt.filterErrorDiagnostics(diagnostics)) != CandidateApplicability.INAPPLICABLE_WRONG_RECEIVER) {
            return null;
        }
        ResolvedCallAtom resolvedCallAtomResultCallAtom = ResolutionAtomsKt.resultCallAtom(result);
        if (resolvedCallAtomResultCallAtom == null) {
            throw new IllegalStateException(("Should be not null for result: " + result).toString());
        }
        NewAbstractResolvedCall<D> newAbstractResolvedCallOnlyTransform = this.kotlinToResolvedCallTransformer.onlyTransform(resolvedCallAtomResultCallAtom, diagnostics);
        tracingStrategy.unresolvedReferenceWrongReceiver(trace, CollectionsKt.listOf(newAbstractResolvedCallOnlyTransform));
        return new SingleOverloadResolutionResult(newAbstractResolvedCallOnlyTransform);
    }

    private final <D extends CallableDescriptor> ManyCandidates<D> transformManyCandidatesAndRecordTrace(ManyCandidatesCallDiagnostic diagnostic, TracingStrategy tracingStrategy, BindingTrace trace, BasicCallResolutionContext context) {
        Collection<ResolutionCandidate> candidates = diagnostic.getCandidates();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(candidates, 10));
        for (ResolutionCandidate resolutionCandidate : candidates) {
            arrayList.add(this.kotlinToResolvedCallTransformer.onlyTransform(resolutionCandidate.getResolvedCall(), CollectionsKt.plus((Collection) resolutionCandidate.getDiagnostics(), (Iterable) KotlinCallDiagnosticsKt.asDiagnostics(resolutionCandidate.getSystem().getErrors()))));
        }
        ArrayList arrayList2 = arrayList;
        if (areAllFailed(diagnostic.getCandidates())) {
            if (areAllFailedWithInapplicableWrongReceiver(diagnostic.getCandidates())) {
                tracingStrategy.unresolvedReferenceWrongReceiver(trace, arrayList2);
            } else {
                ArrayList arrayList3 = arrayList2;
                tracingStrategy.noneApplicable(trace, arrayList3);
                tracingStrategy.recordAmbiguity(trace, arrayList3);
            }
        } else {
            ArrayList arrayList4 = arrayList2;
            tracingStrategy.recordAmbiguity(trace, arrayList4);
            Call call = context.call;
            Intrinsics.checkNotNullExpressionValue(call, "call");
            if (!CallUtilKt.hasUnresolvedArguments(call, context)) {
                if (getAllIncomplete(arrayList2)) {
                    tracingStrategy.cannotCompleteResolve(trace, arrayList4);
                } else {
                    tracingStrategy.ambiguity(trace, arrayList4);
                }
            }
        }
        return new ManyCandidates<>(arrayList2);
    }

    private final boolean getAllIncomplete(List<? extends ResolvedCall<?>> list) {
        List<? extends ResolvedCall<?>> list2 = list;
        if ((list2 instanceof Collection) && list2.isEmpty()) {
            return true;
        }
        Iterator<T> it = list2.iterator();
        while (it.hasNext()) {
            if (((ResolvedCall) it.next()).getStatus() != ResolutionStatus.INCOMPLETE_TYPE_INFERENCE) {
                return false;
            }
        }
        return true;
    }

    private final void recordEffects(ResolvedCall<?> resolvedCall, BindingTrace bindingTrace) {
        DeclarationDescriptor containingDeclaration;
        CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
        if (resultingDescriptor == null || (containingDeclaration = resultingDescriptor.getContainingModule()) == null) {
            return;
        }
        ModuleDescriptor containingModule = DescriptorUtils.getContainingModule(containingDeclaration);
        Intrinsics.checkNotNullExpressionValue(containingModule, "getContainingModule(...)");
        recordLambdasInvocations(resolvedCall, bindingTrace, containingModule);
        recordResultInfo(resolvedCall, bindingTrace, containingModule);
    }

    private final void recordResultInfo(ResolvedCall<?> resolvedCall, BindingTrace bindingTrace, ModuleDescriptor moduleDescriptor) {
        if (resolvedCall instanceof NewResolvedCallImpl) {
            ((NewResolvedCallImpl) resolvedCall).updateResultingDataFlowInfo$frontend(this.effectSystem.getDataFlowInfoForFinishedCall(resolvedCall, bindingTrace, moduleDescriptor));
        }
    }

    private final void recordLambdasInvocations(ResolvedCall<?> resolvedCall, BindingTrace bindingTrace, ModuleDescriptor moduleDescriptor) {
        this.effectSystem.recordDefiniteInvocations(resolvedCall, bindingTrace, moduleDescriptor);
    }

    /* JADX WARN: Code duplicated, block: B:10:0x001c A[RETURN, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:11:0x001e A[ORIG_RETURN, RETURN] */
    private final boolean isEmpty(CallResolutionResult callResolutionResult) {
        for (Object obj : callResolutionResult.getDiagnostics()) {
            if (obj instanceof NoneCandidatesCallDiagnostic) {
                if (obj != null) {
                    return true;
                }
                return false;
            }
        }
        obj = null;
        if (obj != null) {
            return true;
        }
        return false;
    }

    private final boolean areAllFailed(Collection<? extends ResolutionCandidate> collection) {
        Collection<? extends ResolutionCandidate> collection2 = collection;
        if ((collection2 instanceof Collection) && collection2.isEmpty()) {
            return true;
        }
        Iterator<T> it = collection2.iterator();
        while (it.hasNext()) {
            if (CandidateApplicabilityKt.isSuccess(((ResolutionCandidate) it.next()).getResultingApplicability())) {
                return false;
            }
        }
        return true;
    }

    private final boolean areAllFailedWithInapplicableWrongReceiver(Collection<? extends ResolutionCandidate> collection) {
        Collection<? extends ResolutionCandidate> collection2 = collection;
        if ((collection2 instanceof Collection) && collection2.isEmpty()) {
            return true;
        }
        Iterator<T> it = collection2.iterator();
        while (it.hasNext()) {
            if (((ResolutionCandidate) it.next()).getResultingApplicability() != CandidateApplicability.INAPPLICABLE_WRONG_RECEIVER) {
                return false;
            }
        }
        return true;
    }

    private final boolean areAllInapplicable(CallResolutionResult callResolutionResult) {
        Object next;
        Iterator<T> it = callResolutionResult.getDiagnostics().iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (!(next instanceof ManyCandidatesCallDiagnostic));
        ManyCandidatesCallDiagnostic manyCandidatesCallDiagnostic = (ManyCandidatesCallDiagnostic) next;
        Collection<ResolutionCandidate> candidates = manyCandidatesCallDiagnostic != null ? manyCandidatesCallDiagnostic.getCandidates() : null;
        if (candidates != null) {
            return areAllFailed(candidates);
        }
        CandidateApplicability resultApplicabilityForCallDiagnostics = ImplicitScopeTowerKt.getResultApplicabilityForCallDiagnostics(callResolutionResult.getDiagnostics());
        return resultApplicabilityForCallDiagnostics == CandidateApplicability.INAPPLICABLE || resultApplicabilityForCallDiagnostics == CandidateApplicability.INAPPLICABLE_WRONG_RECEIVER || resultApplicabilityForCallDiagnostics == CandidateApplicability.HIDDEN;
    }

    private final boolean reportAdditionalDiagnosticIfNoCandidates(BasicCallResolutionContext context, ImplicitScopeTower scopeTower, KotlinCallKind kind, KotlinCall kotlinCall) {
        List listCollectErrorCandidatesForFunction;
        KtExpression calleeExpression = context.call.getCalleeExpression();
        KtReferenceExpression ktReferenceExpression = calleeExpression instanceof KtReferenceExpression ? (KtReferenceExpression) calleeExpression : null;
        if (ktReferenceExpression == null) {
            return false;
        }
        int i = WhenMappings.$EnumSwitchMapping$0[kind.ordinal()];
        if (i == 1) {
            Name name2 = kotlinCall.getName();
            ReceiverKotlinCallArgument explicitReceiver = kotlinCall.getExplicitReceiver();
            listCollectErrorCandidatesForFunction = ErrorCandidateFactoryKt.collectErrorCandidatesForFunction(scopeTower, name2, explicitReceiver != null ? explicitReceiver.getReceiver() : null);
        } else if (i == 2) {
            Name name3 = kotlinCall.getName();
            ReceiverKotlinCallArgument explicitReceiver2 = kotlinCall.getExplicitReceiver();
            listCollectErrorCandidatesForFunction = ErrorCandidateFactoryKt.collectErrorCandidatesForVariable(scopeTower, name3, explicitReceiver2 != null ? explicitReceiver2.getReceiver() : null);
        } else {
            listCollectErrorCandidatesForFunction = CollectionsKt.emptyList();
        }
        for (ErrorCandidate<?> errorCandidate : listCollectErrorCandidatesForFunction) {
            if (errorCandidate instanceof ErrorCandidate.Classifier) {
                ErrorCandidate.Classifier classifier = (ErrorCandidate.Classifier) errorCandidate;
                context.trace.record(BindingContext.REFERENCE_TARGET, ktReferenceExpression, classifier.getDescriptor());
                context.trace.report(Errors.RESOLUTION_TO_CLASSIFIER.on(ktReferenceExpression, classifier.getDescriptor(), classifier.getKind(), classifier.getErrorMessage()));
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: compiled from: PSICallResolver.kt */
    @Metadata(d1 = {"\u0000\u0094\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\u0016\u00100\u001a\b\u0012\u0004\u0012\u00020\u000e012\u0006\u00102\u001a\u00020!H\u0016J\u0012\u00103\u001a\u0004\u0018\u00010\u000e2\u0006\u00102\u001a\u00020!H\u0016J\u0012\u00104\u001a\u0004\u0018\u0001052\u0006\u00106\u001a\u000205H\u0016JH\u00107\u001a\b\u0012\u0004\u0012\u000209082\u0006\u0010:\u001a\u00020;2\u0006\u00106\u001a\u0002052\f\u0010<\u001a\b\u0012\u0004\u0012\u000209082\u0006\u0010$\u001a\u00020%2\b\u0010=\u001a\u0004\u0018\u00010\u000e2\b\u0010>\u001a\u0004\u0018\u00010\u000eH\u0016JH\u0010?\u001a\b\u0012\u0004\u0012\u00020@082\u0006\u0010:\u001a\u00020;2\u0006\u00106\u001a\u0002052\f\u0010<\u001a\b\u0012\u0004\u0012\u00020@082\u0006\u0010$\u001a\u00020%2\b\u0010=\u001a\u0004\u0018\u00010\u000e2\b\u0010>\u001a\u0004\u0018\u00010\u000eH\u0016R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR*\u0010\u000b\u001a\u001e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e0\fj\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e`\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\u00020\u0013X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0014\u0010\u0016\u001a\u00020\u00178VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019R\u0014\u0010\u001a\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\nR\u0014\u0010\u001b\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\nR\u0014\u0010\u001c\u001a\u00020\u001d8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u001fR\u0014\u0010 \u001a\u00020!8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\"\u0010#R\u0014\u0010$\u001a\u00020%X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b&\u0010'R\u0014\u0010(\u001a\u00020)8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b*\u0010+R\u0014\u0010,\u001a\u00020-8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b.\u0010/¨\u0006A"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/PSICallResolver$ASTScopeTower;", "Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;", "context", "Lorg/jetbrains/kotlin/resolve/calls/context/BasicCallResolutionContext;", "ktExpression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "(Lorg/jetbrains/kotlin/resolve/calls/tower/PSICallResolver;Lorg/jetbrains/kotlin/resolve/calls/context/BasicCallResolutionContext;Lorg/jetbrains/kotlin/psi/KtExpression;)V", "areContextReceiversEnabled", "", "getAreContextReceiversEnabled", "()Z", ResponseCacheMiddleware.CACHE, "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;", "Lkotlin/collections/HashMap;", "getContext", "()Lorg/jetbrains/kotlin/resolve/calls/context/BasicCallResolutionContext;", "dynamicScope", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "getDynamicScope", "()Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "implicitsResolutionFilter", "Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitsExtensionsResolutionFilter;", "getImplicitsResolutionFilter", "()Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitsExtensionsResolutionFilter;", "isDebuggerContext", "isNewInferenceEnabled", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "getLanguageVersionSettings", "()Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "lexicalScope", "Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;", "getLexicalScope", "()Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/incremental/components/LookupLocation;", "getLocation", "()Lorg/jetbrains/kotlin/incremental/components/LookupLocation;", "syntheticScopes", "Lorg/jetbrains/kotlin/resolve/scopes/SyntheticScopes;", "getSyntheticScopes", "()Lorg/jetbrains/kotlin/resolve/scopes/SyntheticScopes;", "typeApproximator", "Lorg/jetbrains/kotlin/types/TypeApproximator;", "getTypeApproximator", "()Lorg/jetbrains/kotlin/types/TypeApproximator;", "getContextReceivers", "", "scope", "getImplicitReceiver", "getNameForGivenImportAlias", "Lorg/jetbrains/kotlin/name/Name;", "name", "interceptFunctionCandidates", "", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "resolutionScope", "Lorg/jetbrains/kotlin/resolve/scopes/ResolutionScope;", "initialResults", "dispatchReceiver", "extensionReceiver", "interceptVariableCandidates", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class ASTScopeTower implements ImplicitScopeTower {
        private final HashMap<ReceiverParameterDescriptor, ReceiverValueWithSmartCastInfo> cache;
        private final BasicCallResolutionContext context;
        private final MemberScope dynamicScope;
        private final LookupLocation location;
        final /* synthetic */ PSICallResolver this$0;

        public ASTScopeTower(PSICallResolver pSICallResolver, BasicCallResolutionContext context, KtExpression ktExpression) {
            KotlinLookupLocation kotlinLookupLocationCreateLookupLocation;
            Intrinsics.checkNotNullParameter(context, "context");
            this.this$0 = pSICallResolver;
            this.context = context;
            DynamicCallableDescriptors dynamicCallableDescriptors = pSICallResolver.dynamicCallableDescriptors;
            Call call = context.call;
            Intrinsics.checkNotNullExpressionValue(call, "call");
            this.dynamicScope = dynamicCallableDescriptors.createDynamicDescriptorScope(call, context.scope.getOwnerDescriptor());
            if (ktExpression == null || (kotlinLookupLocationCreateLookupLocation = CallUtilKt.createLookupLocation(ktExpression)) == null) {
                Call call2 = context.call;
                Intrinsics.checkNotNullExpressionValue(call2, "call");
                kotlinLookupLocationCreateLookupLocation = CallUtilKt.createLookupLocation(call2);
            }
            this.location = kotlinLookupLocationCreateLookupLocation;
            this.cache = new HashMap<>();
        }

        public /* synthetic */ ASTScopeTower(PSICallResolver pSICallResolver, BasicCallResolutionContext basicCallResolutionContext, KtExpression ktExpression, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(pSICallResolver, basicCallResolutionContext, (i & 2) != 0 ? null : ktExpression);
        }

        public final BasicCallResolutionContext getContext() {
            return this.context;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tower.ImplicitScopeTower
        public MemberScope getDynamicScope() {
            return this.dynamicScope;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tower.ImplicitScopeTower
        public LookupLocation getLocation() {
            return this.location;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tower.ImplicitScopeTower
        public SyntheticScopes getSyntheticScopes() {
            return this.this$0.syntheticScopes;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tower.ImplicitScopeTower
        public boolean isDebuggerContext() {
            return this.context.isDebuggerContext;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tower.ImplicitScopeTower
        public boolean isNewInferenceEnabled() {
            return this.context.languageVersionSettings.supportsFeature(LanguageFeature.NewInference);
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tower.ImplicitScopeTower
        public boolean getAreContextReceiversEnabled() {
            return this.context.languageVersionSettings.supportsFeature(LanguageFeature.ContextReceivers);
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tower.ImplicitScopeTower
        public LanguageVersionSettings getLanguageVersionSettings() {
            LanguageVersionSettings languageVersionSettings = this.context.languageVersionSettings;
            Intrinsics.checkNotNullExpressionValue(languageVersionSettings, "languageVersionSettings");
            return languageVersionSettings;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tower.ImplicitScopeTower
        public LexicalScope getLexicalScope() {
            LexicalScope scope = this.context.scope;
            Intrinsics.checkNotNullExpressionValue(scope, "scope");
            return scope;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tower.ImplicitScopeTower
        public TypeApproximator getTypeApproximator() {
            return this.this$0.typeApproximator;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tower.ImplicitScopeTower
        public ImplicitsExtensionsResolutionFilter getImplicitsResolutionFilter() {
            return this.this$0.implicitsResolutionFilter;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tower.ImplicitScopeTower
        public ReceiverValueWithSmartCastInfo getImplicitReceiver(LexicalScope scope) {
            Intrinsics.checkNotNullParameter(scope, "scope");
            ReceiverParameterDescriptor implicitReceiver = scope.getImplicitReceiver();
            if (implicitReceiver == null) {
                return null;
            }
            HashMap<ReceiverParameterDescriptor, ReceiverValueWithSmartCastInfo> map = this.cache;
            ReceiverValueWithSmartCastInfo receiverValueWithSmartCastInfoTransformToReceiverWithSmartCastInfo = map.get(implicitReceiver);
            if (receiverValueWithSmartCastInfoTransformToReceiverWithSmartCastInfo == null) {
                BasicCallResolutionContext basicCallResolutionContext = this.context;
                ReceiverValue value = implicitReceiver.getValue();
                Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
                receiverValueWithSmartCastInfoTransformToReceiverWithSmartCastInfo = NewResolutionOldInferenceKt.transformToReceiverWithSmartCastInfo(basicCallResolutionContext, value);
                map.put(implicitReceiver, receiverValueWithSmartCastInfoTransformToReceiverWithSmartCastInfo);
            }
            return receiverValueWithSmartCastInfoTransformToReceiverWithSmartCastInfo;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tower.ImplicitScopeTower
        public List<ReceiverValueWithSmartCastInfo> getContextReceivers(LexicalScope scope) {
            Intrinsics.checkNotNullParameter(scope, "scope");
            List<ReceiverParameterDescriptor> contextReceiversGroup = scope.getContextReceiversGroup();
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(contextReceiversGroup, 10));
            for (ReceiverParameterDescriptor receiverParameterDescriptor : contextReceiversGroup) {
                HashMap<ReceiverParameterDescriptor, ReceiverValueWithSmartCastInfo> map = this.cache;
                ReceiverValueWithSmartCastInfo receiverValueWithSmartCastInfoTransformToReceiverWithSmartCastInfo = map.get(receiverParameterDescriptor);
                if (receiverValueWithSmartCastInfoTransformToReceiverWithSmartCastInfo == null) {
                    BasicCallResolutionContext basicCallResolutionContext = this.context;
                    ReceiverValue value = receiverParameterDescriptor.getValue();
                    Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
                    receiverValueWithSmartCastInfoTransformToReceiverWithSmartCastInfo = NewResolutionOldInferenceKt.transformToReceiverWithSmartCastInfo(basicCallResolutionContext, value);
                    map.put(receiverParameterDescriptor, receiverValueWithSmartCastInfoTransformToReceiverWithSmartCastInfo);
                }
                arrayList.add(receiverValueWithSmartCastInfoTransformToReceiverWithSmartCastInfo);
            }
            return arrayList;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tower.ImplicitScopeTower
        public Name getNameForGivenImportAlias(Name name2) {
            Intrinsics.checkNotNullParameter(name2, "name");
            PsiFile containingFile = this.context.call.getCallElement().getContainingFile();
            KtFile ktFile = containingFile instanceof KtFile ? (KtFile) containingFile : null;
            if (ktFile != null) {
                return ktFile.getNameForGivenImportAlias(name2);
            }
            return null;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tower.ImplicitScopeTower
        public Collection<FunctionDescriptor> interceptFunctionCandidates(ResolutionScope resolutionScope, Name name2, Collection<? extends FunctionDescriptor> initialResults, LookupLocation location, ReceiverValueWithSmartCastInfo dispatchReceiver, ReceiverValueWithSmartCastInfo extensionReceiver) {
            Intrinsics.checkNotNullParameter(resolutionScope, "resolutionScope");
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(initialResults, "initialResults");
            Intrinsics.checkNotNullParameter(location, "location");
            return this.this$0.candidateInterceptor.interceptFunctionCandidates(initialResults, this, this.context, resolutionScope, this.this$0, name2, location, dispatchReceiver, extensionReceiver);
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tower.ImplicitScopeTower
        public Collection<VariableDescriptor> interceptVariableCandidates(ResolutionScope resolutionScope, Name name2, Collection<? extends VariableDescriptor> initialResults, LookupLocation location, ReceiverValueWithSmartCastInfo dispatchReceiver, ReceiverValueWithSmartCastInfo extensionReceiver) {
            Intrinsics.checkNotNullParameter(resolutionScope, "resolutionScope");
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(initialResults, "initialResults");
            Intrinsics.checkNotNullParameter(location, "location");
            return this.this$0.candidateInterceptor.interceptVariableCandidates(initialResults, this, this.context, resolutionScope, this.this$0, name2, location, dispatchReceiver, extensionReceiver);
        }
    }

    /* JADX INFO: compiled from: PSICallResolver.kt */
    @Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\b\u0086\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u001d\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0002H\u0002J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0012\u001a\u00020\u0002H\u0002J,\u0010\u0015\u001a\u0016\u0012\u0004\u0012\u00020\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00020\u0017\u0018\u00010\u00162\u0006\u0010\u0012\u001a\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u0019H\u0016J\u0016\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00020\u00172\u0006\u0010\u001b\u001a\u00020\u0019H\u0016J\u0010\u0010\u001c\u001a\u00020\u00192\u0006\u0010\u0012\u001a\u00020\u0002H\u0002J\u0018\u0010\u001d\u001a\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u00022\u0006\u0010\u001e\u001a\u00020\u0002H\u0016R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/PSICallResolver$FactoryProviderForInvoke;", "Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateFactoryProviderForInvoke;", "Lorg/jetbrains/kotlin/resolve/calls/components/candidate/ResolutionCandidate;", "context", "Lorg/jetbrains/kotlin/resolve/calls/context/BasicCallResolutionContext;", "scopeTower", "Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;", "kotlinCall", "Lorg/jetbrains/kotlin/resolve/calls/tower/PSIKotlinCallImpl;", "(Lorg/jetbrains/kotlin/resolve/calls/tower/PSICallResolver;Lorg/jetbrains/kotlin/resolve/calls/context/BasicCallResolutionContext;Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;Lorg/jetbrains/kotlin/resolve/calls/tower/PSIKotlinCallImpl;)V", "getContext", "()Lorg/jetbrains/kotlin/resolve/calls/context/BasicCallResolutionContext;", "getKotlinCall", "()Lorg/jetbrains/kotlin/resolve/calls/tower/PSIKotlinCallImpl;", "getScopeTower", "()Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;", "createReceiverCallArgument", "Lorg/jetbrains/kotlin/resolve/calls/model/SimpleKotlinCallArgument;", "variable", "createReceiverValueWithSmartCastInfo", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;", "factoryForInvoke", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateFactory;", "useExplicitReceiver", "", "factoryForVariable", "stripExplicitReceiver", "isRecursiveVariableResolution", "transformCandidate", "invoke", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public final class FactoryProviderForInvoke implements CandidateFactoryProviderForInvoke<ResolutionCandidate> {
        private final BasicCallResolutionContext context;
        private final PSIKotlinCallImpl kotlinCall;
        private final ImplicitScopeTower scopeTower;
        final /* synthetic */ PSICallResolver this$0;

        @Override // org.jetbrains.kotlin.resolve.calls.tower.CandidateFactoryProviderForInvoke
        public ResolutionCandidate transformCandidate(ResolutionCandidate variable, ResolutionCandidate invoke) {
            Intrinsics.checkNotNullParameter(variable, "variable");
            Intrinsics.checkNotNullParameter(invoke, "invoke");
            return invoke;
        }

        public FactoryProviderForInvoke(PSICallResolver pSICallResolver, BasicCallResolutionContext context, ImplicitScopeTower scopeTower, PSIKotlinCallImpl kotlinCall) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(scopeTower, "scopeTower");
            Intrinsics.checkNotNullParameter(kotlinCall, "kotlinCall");
            this.this$0 = pSICallResolver;
            this.context = context;
            this.scopeTower = scopeTower;
            this.kotlinCall = kotlinCall;
            kotlinCall.getDispatchReceiverForInvokeExtension();
        }

        public final BasicCallResolutionContext getContext() {
            return this.context;
        }

        public final ImplicitScopeTower getScopeTower() {
            return this.scopeTower;
        }

        public final PSIKotlinCallImpl getKotlinCall() {
            return this.kotlinCall;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tower.CandidateFactoryProviderForInvoke
        public CandidateFactory<ResolutionCandidate> factoryForVariable(boolean stripExplicitReceiver) {
            ReceiverKotlinCallArgument explicitReceiver = stripExplicitReceiver ? null : this.kotlinCall.getExplicitReceiver();
            PSIKotlinCallImpl pSIKotlinCallImpl = this.kotlinCall;
            return new SimpleCandidateFactory(this.this$0.callComponents, this.scopeTower, new PSIKotlinCallForVariable(pSIKotlinCallImpl, explicitReceiver, pSIKotlinCallImpl.getName()), this.this$0.createResolutionCallbacks(this.context));
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tower.CandidateFactoryProviderForInvoke
        public Pair<ReceiverValueWithSmartCastInfo, CandidateFactory<ResolutionCandidate>> factoryForInvoke(ResolutionCandidate variable, boolean useExplicitReceiver) {
            PSIKotlinCallForInvoke pSIKotlinCallForInvoke;
            Intrinsics.checkNotNullParameter(variable, "variable");
            if (isRecursiveVariableResolution(variable)) {
                return null;
            }
            variable.getIsSuccessful();
            SimpleKotlinCallArgument simpleKotlinCallArgumentCreateReceiverCallArgument = createReceiverCallArgument(variable);
            ReceiverKotlinCallArgument explicitReceiver = this.kotlinCall.getExplicitReceiver();
            if (useExplicitReceiver && explicitReceiver != null) {
                pSIKotlinCallForInvoke = new PSIKotlinCallForInvoke(this.kotlinCall, variable, explicitReceiver, simpleKotlinCallArgumentCreateReceiverCallArgument);
            } else {
                pSIKotlinCallForInvoke = new PSIKotlinCallForInvoke(this.kotlinCall, variable, simpleKotlinCallArgumentCreateReceiverCallArgument, null);
            }
            return TuplesKt.to(simpleKotlinCallArgumentCreateReceiverCallArgument.getReceiver(), new SimpleCandidateFactory(this.this$0.callComponents, this.scopeTower, pSIKotlinCallForInvoke, this.this$0.createResolutionCallbacks(this.context)));
        }

        private final boolean isRecursiveVariableResolution(ResolutionCandidate variable) {
            KotlinType returnType = variable.getResolvedCall().get_candidateDescriptor().getReturnType();
            return (returnType instanceof DeferredType) && ((DeferredType) returnType).isComputing();
        }

        private final SimpleKotlinCallArgument createReceiverCallArgument(ResolutionCandidate variable) {
            TowerUtilsKt.forceResolution(variable);
            ReceiverValueWithSmartCastInfo receiverValueWithSmartCastInfoCreateReceiverValueWithSmartCastInfo = createReceiverValueWithSmartCastInfo(variable);
            if (receiverValueWithSmartCastInfoCreateReceiverValueWithSmartCastInfo.hasTypesFromSmartCasts()) {
                return ReceiverExpressionKotlinCallArgument.Companion.invoke$default(ReceiverExpressionKotlinCallArgument.INSTANCE, createReceiverValueWithSmartCastInfo(variable), false, true, 2, null);
            }
            PSIKotlinCall psiKotlinCall = PSIKotlinCallsKt.getPsiKotlinCall(variable.getResolvedCall().getAtom());
            PartialCallResolutionResult partialCallResolutionResult = new PartialCallResolutionResult(variable.getResolvedCall(), CollectionsKt.emptyList(), variable.getSystem(), false, 8, null);
            ReceiverValue receiverValue = receiverValueWithSmartCastInfoCreateReceiverValueWithSmartCastInfo.getReceiverValue();
            Intrinsics.checkNotNull(receiverValue, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.scopes.receivers.ExpressionReceiver");
            ValueArgument valueArgumentMakeExternalValueArgument = CallMaker.makeExternalValueArgument(((ExpressionReceiver) receiverValue).getExpression());
            Intrinsics.checkNotNullExpressionValue(valueArgumentMakeExternalValueArgument, "makeExternalValueArgument(...)");
            return new SubKotlinCallArgumentImpl(valueArgumentMakeExternalValueArgument, psiKotlinCall.getResultDataFlowInfo(), psiKotlinCall.getResultDataFlowInfo(), receiverValueWithSmartCastInfoCreateReceiverValueWithSmartCastInfo, partialCallResolutionResult);
        }

        private final ReceiverValueWithSmartCastInfo createReceiverValueWithSmartCastInfo(ResolutionCandidate variable) {
            KotlinCall atom = variable.getResolvedCall().getAtom();
            Intrinsics.checkNotNull(atom, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.calls.tower.PSIKotlinCallForVariable");
            PSIKotlinCallForVariable pSIKotlinCallForVariable = (PSIKotlinCallForVariable) atom;
            KtExpression calleeExpression = pSIKotlinCallForVariable.getBaseCall().getPsiCall().getCalleeExpression();
            KtReferenceExpression ktReferenceExpression = calleeExpression instanceof KtReferenceExpression ? (KtReferenceExpression) calleeExpression : null;
            if (ktReferenceExpression == null) {
                throw new IllegalStateException(("Unexpected call : " + pSIKotlinCallForVariable.getBaseCall().getPsiCall()).toString());
            }
            TemporaryBindingTrace temporaryBindingTraceCreate = TemporaryBindingTrace.create(this.context.trace, "Context for resolve candidate");
            Intrinsics.checkNotNullExpressionValue(temporaryBindingTraceCreate, "create(...)");
            UnwrappedType freshReturnType = ResolutionAtomsKt.getFreshReturnType(variable.getResolvedCall());
            Intrinsics.checkNotNull(freshReturnType);
            ExpressionReceiver expressionReceiverCreate = ExpressionReceiver.INSTANCE.create(ktReferenceExpression, freshReturnType, temporaryBindingTraceCreate.getContext());
            WritableSlice<KtReferenceExpression, DeclarationDescriptor> REFERENCE_TARGET = BindingContext.REFERENCE_TARGET;
            Intrinsics.checkNotNullExpressionValue(REFERENCE_TARGET, "REFERENCE_TARGET");
            temporaryBindingTraceCreate.record(REFERENCE_TARGET, ktReferenceExpression, variable.getResolvedCall().get_candidateDescriptor());
            ExpressionReceiver expressionReceiver = expressionReceiverCreate;
            DataFlowValue dataFlowValueCreateDataFlowValue = this.this$0.dataFlowValueFactory.createDataFlowValue(expressionReceiver, temporaryBindingTraceCreate.getContext(), this.context.scope.getOwnerDescriptor());
            DataFlowInfo dataFlowInfo = this.context.dataFlowInfo;
            LanguageVersionSettings languageVersionSettings = this.context.languageVersionSettings;
            Intrinsics.checkNotNullExpressionValue(languageVersionSettings, "languageVersionSettings");
            return QualifierReceiverKt.prepareReceiverRegardingCaptureTypes(new ReceiverValueWithSmartCastInfo(expressionReceiver, AddToStdlibKt.compactIfPossible(dataFlowInfo.getCollectedTypes(dataFlowValueCreateDataFlowValue, languageVersionSettings)), dataFlowValueCreateDataFlowValue.getIsStable(), null, 8, null));
        }
    }

    private final KotlinCallKind toKotlinCallKind(NewResolutionOldInference.ResolutionKind resolutionKind) {
        if (resolutionKind instanceof NewResolutionOldInference.ResolutionKind.Function) {
            return KotlinCallKind.FUNCTION;
        }
        if (resolutionKind instanceof NewResolutionOldInference.ResolutionKind.Variable) {
            return KotlinCallKind.VARIABLE;
        }
        if (resolutionKind instanceof NewResolutionOldInference.ResolutionKind.Invoke) {
            return KotlinCallKind.INVOKE;
        }
        if (resolutionKind instanceof NewResolutionOldInference.ResolutionKind.CallableReference) {
            return KotlinCallKind.CALLABLE_REFERENCE;
        }
        if (resolutionKind instanceof NewResolutionOldInference.ResolutionKind.GivenCandidates) {
            return KotlinCallKind.UNSUPPORTED;
        }
        throw new NoWhenBranchMatchedException();
    }

    static /* synthetic */ PSIKotlinCallImpl toKotlinCall$default(PSICallResolver pSICallResolver, BasicCallResolutionContext basicCallResolutionContext, KotlinCallKind kotlinCallKind, Call call, Name name2, TracingStrategy tracingStrategy, boolean z, Receiver receiver, int i, Object obj) {
        return pSICallResolver.toKotlinCall(basicCallResolutionContext, kotlinCallKind, call, name2, tracingStrategy, z, (i & 64) != 0 ? null : receiver);
    }

    private final PSIKotlinCallImpl toKotlinCall(BasicCallResolutionContext context, KotlinCallKind kotlinCallKind, Call oldCall, Name name2, TracingStrategy tracingStrategy, boolean isSpecialFunction, Receiver forcedExplicitReceiver) {
        ValueArgument valueArgument;
        KtLambdaExpression lambdaExpression;
        DataFlowInfo resultInfo;
        DataFlowInfo dataFlowInfoAfterThisArgument;
        ReceiverKotlinCallArgument receiverKotlinCallArgumentResolveReceiver = resolveReceiver(context, forcedExplicitReceiver == null ? oldCall.getExplicitReceiver() : forcedExplicitReceiver, CallUtilKt.isSafeCall(oldCall), false);
        ReceiverKotlinCallArgument receiverKotlinCallArgumentResolveDispatchReceiverForInvoke = resolveDispatchReceiverForInvoke(context, kotlinCallKind, oldCall);
        List<KtTypeProjection> typeArguments = oldCall.getTypeArguments();
        Intrinsics.checkNotNullExpressionValue(typeArguments, "getTypeArguments(...)");
        List<TypeArgument> listResolveTypeArguments = resolveTypeArguments(context, typeArguments);
        int size = oldCall.getFunctionLiteralArguments().size();
        if (oldCall.getCallType() == Call.CallType.ARRAY_SET_METHOD) {
            size = 1;
        }
        List<? extends ValueArgument> valueArguments = oldCall.getValueArguments();
        Intrinsics.checkNotNullExpressionValue(valueArguments, "getValueArguments(...)");
        List<? extends ValueArgument> listDropLast = size == 0 ? valueArguments : CollectionsKt.dropLast(valueArguments, size);
        List<? extends LambdaArgument> functionLiteralArguments = oldCall.getFunctionLiteralArguments();
        Intrinsics.checkNotNullExpressionValue(functionLiteralArguments, "getFunctionLiteralArguments(...)");
        List<KotlinCallArgument> listResolveArgumentsInParenthesis = resolveArgumentsInParenthesis(context, listDropLast, isSpecialFunction, tracingStrategy);
        if (oldCall.getCallType() == Call.CallType.ARRAY_SET_METHOD) {
            functionLiteralArguments.isEmpty();
            if (valueArguments.isEmpty()) {
                KotlinExceptionWithAttachments kotlinExceptionWithAttachmentsWithPsiAttachment = new KotlinExceptionWithAttachments("Can not find an external argument for 'set' method").withPsiAttachment("callElement.kt", oldCall.getCallElement());
                KtElement callElement = oldCall.getCallElement();
                if (!callElement.isValid()) {
                    callElement = null;
                }
                throw kotlinExceptionWithAttachmentsWithPsiAttachment.withPsiAttachment("file.kt", callElement != null ? callElement.getContainingFile() : null);
            }
            valueArgument = (ValueArgument) CollectionsKt.last((List) valueArguments);
        } else {
            if (functionLiteralArguments.size() > 1) {
                int size2 = functionLiteralArguments.size();
                for (int i = 0; i < size2; i++) {
                    if (i != 0 && (lambdaExpression = functionLiteralArguments.get(i).getLambdaExpression()) != null) {
                        if (CallUtilKt.isTrailingLambdaOnNewLIne(lambdaExpression)) {
                            context.trace.report(Errors.UNEXPECTED_TRAILING_LAMBDA_ON_A_NEW_LINE.on(lambdaExpression));
                        }
                        context.trace.report(Errors.MANY_LAMBDA_EXPRESSION_ARGUMENTS.on(lambdaExpression));
                    }
                }
            }
            valueArgument = (ValueArgument) CollectionsKt.firstOrNull((List) functionLiteralArguments);
        }
        ValueArgument valueArgument2 = valueArgument;
        if (valueArgument2 != null && !listResolveArgumentsInParenthesis.isEmpty()) {
            resultInfo = NewCallArgumentsKt.getPsiCallArgument((KotlinCallArgument) CollectionsKt.last((List) listResolveArgumentsInParenthesis)).getDataFlowInfoAfterThisArgument();
        } else {
            resultInfo = context.dataFlowInfoForArguments.getResultInfo();
            Intrinsics.checkNotNullExpressionValue(resultInfo, "getResultInfo(...)");
        }
        DataFlowInfo dataFlowInfo = resultInfo;
        PSIKotlinCallArgument pSIKotlinCallArgumentResolveValueArgument = valueArgument2 != null ? resolveValueArgument(context, dataFlowInfo, valueArgument2, isSpecialFunction, tracingStrategy) : null;
        if (pSIKotlinCallArgumentResolveValueArgument == null || (dataFlowInfoAfterThisArgument = pSIKotlinCallArgumentResolveValueArgument.getDataFlowInfoAfterThisArgument()) == null) {
            dataFlowInfoAfterThisArgument = dataFlowInfo;
        }
        Iterator<T> it = listResolveArgumentsInParenthesis.iterator();
        while (it.hasNext()) {
            NewCallArgumentsKt.setResultDataFlowInfoIfRelevant((KotlinCallArgument) it.next(), dataFlowInfoAfterThisArgument);
        }
        if (pSIKotlinCallArgumentResolveValueArgument != null) {
            NewCallArgumentsKt.setResultDataFlowInfoIfRelevant(pSIKotlinCallArgumentResolveValueArgument, dataFlowInfoAfterThisArgument);
        }
        PSIKotlinCallArgument pSIKotlinCallArgument = pSIKotlinCallArgumentResolveValueArgument;
        DataFlowInfo dataFlowInfo2 = context.dataFlowInfo;
        Intrinsics.checkNotNullExpressionValue(dataFlowInfo2, "dataFlowInfo");
        MutableDataFlowInfoForArguments dataFlowInfoForArguments = context.dataFlowInfoForArguments;
        Intrinsics.checkNotNullExpressionValue(dataFlowInfoForArguments, "dataFlowInfoForArguments");
        return new PSIKotlinCallImpl(kotlinCallKind, oldCall, tracingStrategy, receiverKotlinCallArgumentResolveReceiver, receiverKotlinCallArgumentResolveDispatchReceiverForInvoke, name2, listResolveTypeArguments, listResolveArgumentsInParenthesis, pSIKotlinCallArgument, dataFlowInfo2, dataFlowInfoAfterThisArgument, dataFlowInfoForArguments, oldCall instanceof CallTransformer.CallForImplicitInvoke);
    }

    private final ReceiverKotlinCallArgument resolveDispatchReceiverForInvoke(BasicCallResolutionContext context, KotlinCallKind kotlinCallKind, Call oldCall) {
        if (kotlinCallKind != KotlinCallKind.INVOKE) {
            return null;
        }
        if (!(oldCall instanceof CallTransformer.CallForImplicitInvoke)) {
            throw new IllegalArgumentException(("Call should be CallForImplicitInvoke, but it is: " + oldCall).toString());
        }
        return resolveReceiver(context, ((CallTransformer.CallForImplicitInvoke) oldCall).getDispatchReceiver(), false, true);
    }

    private final ReceiverKotlinCallArgument resolveReceiver(BasicCallResolutionContext context, Receiver oldReceiver, boolean isSafeCall, boolean isForImplicitInvoke) {
        PartialCallContainer partialCallContainer;
        PartialCallResolutionResult result = null;
        if (oldReceiver == null) {
            return null;
        }
        if (oldReceiver instanceof QualifierReceiver) {
            return new QualifierReceiverKotlinCallArgument((QualifierReceiver) oldReceiver);
        }
        if (oldReceiver instanceof ReceiverValue) {
            if (oldReceiver instanceof ExpressionReceiver) {
                ExpressionReceiver expressionReceiver = (ExpressionReceiver) oldReceiver;
                KtExpression lastElementDeparenthesized = KtPsiUtil.getLastElementDeparenthesized(expressionReceiver.getExpression(), context.statementFilter);
                BindingContext context2 = context.trace.getContext();
                Intrinsics.checkNotNullExpressionValue(context2, "getBindingContext(...)");
                Call call = (Call) context2.get(BindingContext.DELEGATE_EXPRESSION_TO_PROVIDE_DELEGATE_CALL, lastElementDeparenthesized);
                if (call == null) {
                    call = lastElementDeparenthesized != null ? CallUtilKt.getCall(lastElementDeparenthesized, context2) : null;
                }
                if (call != null && (partialCallContainer = (PartialCallContainer) context2.get(BindingContext.ONLY_RESOLVED_CALL, call)) != null) {
                    result = partialCallContainer.getResult();
                }
                PartialCallResolutionResult partialCallResolutionResult = result;
                if (partialCallResolutionResult != null) {
                    ReceiverValueWithSmartCastInfo receiverValueWithSmartCastInfo = new ReceiverValueWithSmartCastInfo((ReceiverValue) oldReceiver, SetsKt.emptySet(), true, null, 8, null);
                    ValueArgument valueArgumentMakeExternalValueArgument = CallMaker.makeExternalValueArgument(expressionReceiver.getExpression());
                    Intrinsics.checkNotNullExpressionValue(valueArgumentMakeExternalValueArgument, "makeExternalValueArgument(...)");
                    DataFlowInfo dataFlowInfo = context.dataFlowInfo;
                    Intrinsics.checkNotNullExpressionValue(dataFlowInfo, "dataFlowInfo");
                    DataFlowInfo dataFlowInfo2 = context.dataFlowInfo;
                    Intrinsics.checkNotNullExpressionValue(dataFlowInfo2, "dataFlowInfo");
                    return new SubKotlinCallArgumentImpl(valueArgumentMakeExternalValueArgument, dataFlowInfo, dataFlowInfo2, receiverValueWithSmartCastInfo, partialCallResolutionResult);
                }
            }
            return ReceiverExpressionKotlinCallArgument.INSTANCE.invoke(NewResolutionOldInferenceKt.transformToReceiverWithSmartCastInfo(context, (ReceiverValue) oldReceiver), isSafeCall, isForImplicitInvoke);
        }
        throw new IllegalStateException(("Incorrect receiver: " + oldReceiver).toString());
    }

    private final List<TypeArgument> resolveTypeArguments(BasicCallResolutionContext context, List<? extends KtTypeProjection> typeArguments) {
        TypeArgument simpleTypeArgumentImpl;
        List<? extends KtTypeProjection> list = typeArguments;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (KtTypeProjection ktTypeProjection : list) {
            if (ktTypeProjection.getProjectionKind() != KtProjectionKind.NONE) {
                context.trace.report(Errors.PROJECTION_ON_NON_CLASS_TYPE_ARGUMENT.on(ktTypeProjection));
            }
            BindingTrace trace = context.trace;
            Intrinsics.checkNotNullExpressionValue(trace, "trace");
            ModifierCheckerCore.INSTANCE.check(ktTypeProjection, trace, null, this.languageVersionSettings);
            KtTypeReference typeReference = ktTypeProjection.getTypeReference();
            if (typeReference == null) {
                simpleTypeArgumentImpl = TypeArgumentPlaceholder.INSTANCE;
            } else {
                Intrinsics.checkNotNull(typeReference);
                if (typeReference.isPlaceholder()) {
                    TypeResolver typeResolver = this.typeResolver;
                    BindingTrace trace2 = context.trace;
                    Intrinsics.checkNotNullExpressionValue(trace2, "trace");
                    LexicalScope scope = context.scope;
                    Intrinsics.checkNotNullExpressionValue(scope, "scope");
                    Annotations annotationsResolveTypeAnnotations = typeResolver.resolveTypeAnnotations(trace2, scope, typeReference);
                    ForceResolveUtil.forceResolveAllContents(annotationsResolveTypeAnnotations);
                    Intrinsics.checkNotNullExpressionValue(annotationsResolveTypeAnnotations, "apply(...)");
                    Iterator<AnnotationDescriptor> it = annotationsResolveTypeAnnotations.iterator();
                    while (it.hasNext()) {
                        PsiElement psi = PsiSourceElementKt.getPsi(it.next().getSource());
                        if (psi != null) {
                            context.trace.report(Errors.UNSUPPORTED.on(psi, "annotations on an underscored type argument"));
                        }
                    }
                    if (!this.arePartiallySpecifiedTypeArgumentsEnabled) {
                        context.trace.report(Errors.UNSUPPORTED.on(typeReference, "underscored type argument"));
                    }
                    simpleTypeArgumentImpl = TypeArgumentPlaceholder.INSTANCE;
                } else {
                    simpleTypeArgumentImpl = new SimpleTypeArgumentImpl(ktTypeProjection, NewCallArgumentsKt.resolveTypeWithGivenTypeReference(context, typeReference, this.typeResolver));
                }
            }
            arrayList.add(simpleTypeArgumentImpl);
        }
        return arrayList;
    }

    private final List<KotlinCallArgument> resolveArgumentsInParenthesis(BasicCallResolutionContext context, List<? extends ValueArgument> arguments, boolean isSpecialFunction, TracingStrategy tracingStrategy) {
        MutableDataFlowInfoForArguments dataFlowInfoForArguments = context.dataFlowInfoForArguments;
        Intrinsics.checkNotNullExpressionValue(dataFlowInfoForArguments, "dataFlowInfoForArguments");
        List<? extends ValueArgument> list = arguments;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (ValueArgument valueArgument : list) {
            DataFlowInfo info = dataFlowInfoForArguments.getInfo(valueArgument);
            Intrinsics.checkNotNullExpressionValue(info, "getInfo(...)");
            PSIKotlinCallArgument pSIKotlinCallArgumentResolveValueArgument = resolveValueArgument(context, info, valueArgument, isSpecialFunction, tracingStrategy);
            dataFlowInfoForArguments.updateInfo(valueArgument, pSIKotlinCallArgumentResolveValueArgument.getDataFlowInfoAfterThisArgument());
            arrayList.add(pSIKotlinCallArgumentResolveValueArgument);
        }
        return arrayList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final PSIKotlinCallArgument resolveValueArgument(BasicCallResolutionContext outerCallContext, DataFlowInfo startDataFlowInfo, ValueArgument valueArgument, boolean isSpecialFunction, TracingStrategy tracingStrategy) {
        KotlinBuiltIns builtIns = DescriptorUtilsKt.getBuiltIns(outerCallContext.scope.getOwnerDescriptor());
        KtExpression argumentExpression = valueArgument.getArgumentExpression();
        if (argumentExpression == null) {
            return resolveValueArgument$createParseErrorElement(valueArgument, startDataFlowInfo);
        }
        Object objDeparenthesize = KtPsiUtil.deparenthesize(argumentExpression);
        if (objDeparenthesize == null) {
            objDeparenthesize = resolveValueArgument$createParseErrorElement(valueArgument, startDataFlowInfo);
        }
        Object obj = objDeparenthesize;
        ValueArgumentName argumentName = valueArgument.getArgumentName();
        Name asName = argumentName != null ? argumentName.getAsName() : null;
        BasicCallResolutionContext basicCallResolutionContextExpandContextForCatchClause = expandContextForCatchClause(outerCallContext, obj);
        PSIKotlinCallArgument pSIKotlinCallArgumentProcessFunctionalExpression = NewCallArgumentsKt.processFunctionalExpression(basicCallResolutionContextExpandContextForCatchClause, argumentExpression, startDataFlowInfo, valueArgument, asName, builtIns, this.typeResolver);
        if (pSIKotlinCallArgumentProcessFunctionalExpression != null) {
            return pSIKotlinCallArgumentProcessFunctionalExpression;
        }
        if (obj instanceof KtCollectionLiteralExpression) {
            return new CollectionLiteralKotlinCallArgumentImpl(valueArgument, asName, startDataFlowInfo, startDataFlowInfo, (KtCollectionLiteralExpression) obj, basicCallResolutionContextExpandContextForCatchClause);
        }
        BasicCallResolutionContext basicCallResolutionContext = (BasicCallResolutionContext) ((BasicCallResolutionContext) basicCallResolutionContextExpandContextForCatchClause.replaceContextDependency(ContextDependency.DEPENDENT)).replaceDataFlowInfo(startDataFlowInfo);
        if (!isSpecialFunction || !(argumentExpression instanceof KtBlockExpression) || ArgumentTypeResolver.getCallableReferenceExpressionIfAny(argumentExpression, basicCallResolutionContext) == null) {
            basicCallResolutionContext = (BasicCallResolutionContext) basicCallResolutionContext.replaceExpectedType(TypeUtils.NO_EXPECTED_TYPE);
        }
        BasicCallResolutionContext basicCallResolutionContext2 = basicCallResolutionContext;
        Intrinsics.checkNotNullExpressionValue(basicCallResolutionContext2, "let(...)");
        if (obj instanceof KtCallableReferenceExpression) {
            return createCallableReferenceKotlinCallArgument(basicCallResolutionContext2, (KtCallableReferenceExpression) obj, startDataFlowInfo, valueArgument, asName, basicCallResolutionContextExpandContextForCatchClause, tracingStrategy);
        }
        KotlinTypeInfo typeInfo = this.expressionTypingServices.getTypeInfo(argumentExpression, basicCallResolutionContext2);
        Intrinsics.checkNotNullExpressionValue(typeInfo, "getTypeInfo(...)");
        PSIKotlinCallArgument pSIKotlinCallArgumentCreateSimplePSICallArgument = NewCallArgumentsKt.createSimplePSICallArgument(basicCallResolutionContext2, valueArgument, typeInfo);
        if (pSIKotlinCallArgumentCreateSimplePSICallArgument == null) {
            pSIKotlinCallArgumentCreateSimplePSICallArgument = resolveValueArgument$createParseErrorElement(valueArgument, startDataFlowInfo);
        }
        return pSIKotlinCallArgumentCreateSimplePSICallArgument;
    }

    private static final ParseErrorKotlinCallArgument resolveValueArgument$createParseErrorElement(ValueArgument valueArgument, DataFlowInfo dataFlowInfo) {
        return new ParseErrorKotlinCallArgument(valueArgument, dataFlowInfo);
    }

    public final Pair<DoubleColonLHS, LHSResult> getLhsResult(BasicCallResolutionContext context, KtCallableReferenceExpression ktExpression) {
        DoubleColonLHS doubleColonLHSResolveDoubleColonLHS$frontend;
        LHSResult.Type type;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(ktExpression, "ktExpression");
        ExpressionTypingContext expressionTypingContextNewContext = ExpressionTypingContext.newContext(context);
        Intrinsics.checkNotNullExpressionValue(expressionTypingContextNewContext, "newContext(...)");
        if (ktExpression.isEmptyLHS()) {
            return TuplesKt.to(null, LHSResult.Empty.INSTANCE);
        }
        CallPosition callPosition = context.callPosition;
        CallPosition.CallableReferenceRhs callableReferenceRhs = callPosition instanceof CallPosition.CallableReferenceRhs ? (CallPosition.CallableReferenceRhs) callPosition : null;
        if ((callableReferenceRhs == null || (doubleColonLHSResolveDoubleColonLHS$frontend = callableReferenceRhs.getLhs()) == null) && (doubleColonLHSResolveDoubleColonLHS$frontend = this.doubleColonExpressionResolver.resolveDoubleColonLHS$frontend(ktExpression, expressionTypingContextNewContext)) == null) {
            return TuplesKt.to(null, LHSResult.Empty.INSTANCE);
        }
        if (doubleColonLHSResolveDoubleColonLHS$frontend instanceof DoubleColonLHS.Expression) {
            DoubleColonLHS.Expression expression = (DoubleColonLHS.Expression) doubleColonLHSResolveDoubleColonLHS$frontend;
            if (expression.getIsObjectQualifier()) {
                ClassifierDescriptor this$0 = doubleColonLHSResolveDoubleColonLHS$frontend.getType().getConstructor().getThis$0();
                KtExpression receiverExpression = ktExpression.getReceiverExpression();
                KtExpression calleeExpressionIfAny = receiverExpression != null ? PsiUtilsKt.getCalleeExpressionIfAny(receiverExpression) : null;
                if ((calleeExpressionIfAny instanceof KtSimpleNameExpression) && (this$0 instanceof ClassDescriptor)) {
                    type = new LHSResult.Object(new ClassQualifier((KtSimpleNameExpression) calleeExpressionIfAny, (ClassDescriptor) this$0));
                } else {
                    type = LHSResult.Error.INSTANCE;
                }
            } else {
                SimplePSIKotlinCallArgument simplePSIKotlinCallArgumentCreateSimplePSICallArgument = NewCallArgumentsKt.createSimplePSICallArgument(context, new FakeValueArgumentForLeftCallableReference(ktExpression), expression.getTypeInfo());
                type = simplePSIKotlinCallArgumentCreateSimplePSICallArgument != null ? new LHSResult.Expression(simplePSIKotlinCallArgumentCreateSimplePSICallArgument) : LHSResult.Error.INSTANCE;
            }
        } else if (doubleColonLHSResolveDoubleColonLHS$frontend instanceof DoubleColonLHS.Type) {
            KtExpression receiverExpression2 = ktExpression.getReceiverExpression();
            Intrinsics.checkNotNull(receiverExpression2);
            Qualifier qualifier = (Qualifier) expressionTypingContextNewContext.trace.get(BindingContext.QUALIFIER, receiverExpression2);
            if (!(doubleColonLHSResolveDoubleColonLHS$frontend.getType().getConstructor().getThis$0() instanceof ClassDescriptor)) {
                expressionTypingContextNewContext.trace.report(Errors.CALLABLE_REFERENCE_LHS_NOT_A_CLASS.on(ktExpression));
                type = LHSResult.Error.INSTANCE;
            } else {
                type = new LHSResult.Type(qualifier, doubleColonLHSResolveDoubleColonLHS$frontend.getType().unwrap());
            }
        } else {
            throw new NoWhenBranchMatchedException();
        }
        return TuplesKt.to(doubleColonLHSResolveDoubleColonLHS$frontend, type);
    }

    public final CallableReferenceKotlinCallArgumentImpl createCallableReferenceKotlinCallArgument(BasicCallResolutionContext context, KtCallableReferenceExpression ktExpression, DataFlowInfo startDataFlowInfo, ValueArgument valueArgument, Name argumentName, BasicCallResolutionContext outerCallContext, TracingStrategy tracingStrategy) {
        DataFlowInfo dataFlowInfo;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(ktExpression, "ktExpression");
        Intrinsics.checkNotNullParameter(startDataFlowInfo, "startDataFlowInfo");
        Intrinsics.checkNotNullParameter(valueArgument, "valueArgument");
        Intrinsics.checkNotNullParameter(outerCallContext, "outerCallContext");
        Intrinsics.checkNotNullParameter(tracingStrategy, "tracingStrategy");
        NewCallArgumentsKt.checkNoSpread(outerCallContext, valueArgument);
        Pair<DoubleColonLHS, LHSResult> lhsResult = getLhsResult(context, ktExpression);
        DoubleColonLHS doubleColonLHSComponent1 = lhsResult.component1();
        LHSResult lHSResultComponent2 = lhsResult.component2();
        DoubleColonLHS.Expression expression = doubleColonLHSComponent1 instanceof DoubleColonLHS.Expression ? (DoubleColonLHS.Expression) doubleColonLHSComponent1 : null;
        DataFlowInfo dataFlowInfo2 = (expression == null || (dataFlowInfo = expression.getDataFlowInfo()) == null) ? startDataFlowInfo : dataFlowInfo;
        KtSimpleNameExpression callableReference = ktExpression.getCallableReference();
        Intrinsics.checkNotNullExpressionValue(callableReference, "getCallableReference(...)");
        Name referencedNameAsName = callableReference.getReferencedNameAsName();
        Call callMakeCall = (Call) outerCallContext.trace.get(BindingContext.CALL, callableReference);
        if (callMakeCall == null) {
            callMakeCall = CallMaker.makeCall(callableReference, null, null, callableReference, CollectionsKt.emptyList());
        }
        Call call = callMakeCall;
        Intrinsics.checkNotNull(call);
        return new CallableReferenceKotlinCallArgumentImpl(new ASTScopeTower(this, context, callableReference), valueArgument, startDataFlowInfo, dataFlowInfo2, ktExpression, argumentName, lHSResultComponent2, referencedNameAsName, toKotlinCall$default(this, context, KotlinCallKind.CALLABLE_REFERENCE, call, referencedNameAsName, tracingStrategy, false, null, 64, null));
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final BasicCallResolutionContext expandContextForCatchClause(BasicCallResolutionContext basicCallResolutionContext, Object obj) {
        Ref ref;
        VariableDescriptor variableDescriptor;
        if (!(obj instanceof KtExpression) || (ref = (Ref) basicCallResolutionContext.trace.getContext().get(BindingContext.NEW_INFERENCE_CATCH_EXCEPTION_PARAMETER, obj)) == null || (variableDescriptor = (VariableDescriptor) ref.get()) == null) {
            return basicCallResolutionContext;
        }
        ref.set(null);
        LocalRedeclarationChecker localRedeclarationCheckerCreateLocalRedeclarationChecker = this.expressionTypingServices.createLocalRedeclarationChecker(basicCallResolutionContext.trace);
        LexicalScope lexicalScope = basicCallResolutionContext.scope;
        Intrinsics.checkNotNull(lexicalScope);
        DeclarationDescriptor ownerDescriptor = lexicalScope.getOwnerDescriptor();
        Intrinsics.checkNotNull(localRedeclarationCheckerCreateLocalRedeclarationChecker);
        LexicalWritableScope lexicalWritableScope = new LexicalWritableScope(lexicalScope, ownerDescriptor, false, localRedeclarationCheckerCreateLocalRedeclarationChecker, LexicalScopeKind.CATCH);
        boolean z = basicCallResolutionContext.languageVersionSettings.getFeatureSupport(LanguageFeature.ForbidReferencingToUnderscoreNamedParameterOfCatchBlock) == LanguageFeature.State.ENABLED;
        if (!DescriptorUtilsKt.isUnderscoreNamed(variableDescriptor) || !z) {
            lexicalWritableScope.addVariableDescriptor(variableDescriptor);
        }
        Context contextReplaceScope = basicCallResolutionContext.replaceScope(lexicalWritableScope);
        Intrinsics.checkNotNullExpressionValue(contextReplaceScope, "replaceScope(...)");
        return (BasicCallResolutionContext) contextReplaceScope;
    }
}
