package org.jetbrains.kotlin.resolve.calls;

import androidx.core.app.NotificationCompat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.builtins.ReflectionTypes;
import org.jetbrains.kotlin.com.google.common.collect.Lists;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafPsiElement;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilityUtils;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.MemberDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.impl.TypeAliasConstructorDescriptor;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.progress.ProgressIndicatorAndCompilationCanceledStatus;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtCallableReferenceExpression;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.KtSuperExpression;
import org.jetbrains.kotlin.psi.KtTypeProjection;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.DescriptorResolver;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.FunctionDescriptorUtil;
import org.jetbrains.kotlin.resolve.UpperBoundChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.AdditionalTypeChecker;
import org.jetbrains.kotlin.resolve.calls.context.CallCandidateResolutionContext;
import org.jetbrains.kotlin.resolve.calls.context.CallPosition;
import org.jetbrains.kotlin.resolve.calls.context.CallResolutionContext;
import org.jetbrains.kotlin.resolve.calls.context.CandidateResolveMode;
import org.jetbrains.kotlin.resolve.calls.context.CheckArgumentTypesMode;
import org.jetbrains.kotlin.resolve.calls.context.ResolutionContext;
import org.jetbrains.kotlin.resolve.calls.inference.SubstitutionFilteringInternalResolveAnnotations;
import org.jetbrains.kotlin.resolve.calls.model.ArgumentMatchStatus;
import org.jetbrains.kotlin.resolve.calls.model.MutableDataFlowInfoForArguments;
import org.jetbrains.kotlin.resolve.calls.model.MutableResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedValueArgument;
import org.jetbrains.kotlin.resolve.calls.results.ResolutionStatus;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValue;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValueFactory;
import org.jetbrains.kotlin.resolve.calls.smartcasts.Nullability;
import org.jetbrains.kotlin.resolve.calls.smartcasts.SmartCastManager;
import org.jetbrains.kotlin.resolve.calls.smartcasts.SmartCastResult;
import org.jetbrains.kotlin.resolve.calls.smartcasts.SmartCastUtilKt;
import org.jetbrains.kotlin.resolve.calls.util.CallResolverUtilKt;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.calls.util.FakeCallableDescriptorForObject;
import org.jetbrains.kotlin.resolve.calls.util.ResolveArgumentsMode;
import org.jetbrains.kotlin.resolve.scopes.receivers.ExpressionReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.Receiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.types.FlexibleTypesKt;
import org.jetbrains.kotlin.types.FunctionPlaceholdersKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeKt;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeAliasExpansionReportStrategy;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeSubstitution;
import org.jetbrains.kotlin.types.TypeSubstitutor;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.checker.ErrorTypesAreEqualToAnything;
import org.jetbrains.kotlin.types.checker.KotlinTypeChecker;
import org.jetbrains.kotlin.types.error.ErrorType;
import org.jetbrains.kotlin.types.error.ErrorTypeKind;
import org.jetbrains.kotlin.types.error.ErrorUtils;
import org.jetbrains.kotlin.types.expressions.DoubleColonExpressionResolver;
import org.jetbrains.kotlin.types.expressions.DoubleColonLHS;
import org.jetbrains.kotlin.types.expressions.KotlinTypeInfo;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: CandidateResolver.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000ö\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0011\u0018\u00002\u00020\u0001:\u0002pqBC\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\u0006\u0010\u000f\u001a\u00020\u0010¢\u0006\u0002\u0010\u0011J&\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001a2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001dH\u0002J*\u0010\u001f\u001a\u00060 R\u00020\u0000\"\b\b\u0000\u0010!*\u00020\u00132\f\u0010\"\u001a\b\u0012\u0004\u0012\u0002H!0\u00142\u0006\u0010#\u001a\u00020$J\u0014\u0010%\u001a\u00020\u00182\n\u0010\"\u001a\u0006\u0012\u0002\b\u00030\u0014H\u0002J \u0010&\u001a\u00020'\"\b\b\u0000\u0010!*\u00020\u00132\f\u0010\"\u001a\b\u0012\u0004\u0012\u0002H!0\u0014H\u0002J(\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020\u001a2\u0006\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u00020.H\u0002JJ\u00100\u001a\u00060 R\u00020\u0000\"\b\b\u0000\u0010!*\u00020\u0013\"\u000e\b\u0001\u00101*\b\u0012\u0004\u0012\u0002H1022\f\u0010\"\u001a\b\u0012\u0004\u0012\u0002H1022\f\u00103\u001a\b\u0012\u0004\u0012\u0002H!042\u0006\u0010#\u001a\u00020$H\u0002J\u0012\u00105\u001a\u0004\u0018\u0001062\u0006\u00107\u001a\u00020\u0013H\u0002J\u0014\u00108\u001a\u0004\u0018\u0001092\b\u0010:\u001a\u0004\u0018\u00010;H\u0002J&\u0010<\u001a\u00020)\"\b\b\u0000\u0010!*\u00020\u00132\f\u0010\"\u001a\b\u0012\u0004\u0012\u0002H!0\u00142\u0006\u0010=\u001a\u00020>J.\u0010?\u001a\u0004\u0018\u00010\u001a2\u0006\u0010@\u001a\u00020A2\u0006\u0010B\u001a\u00020\u001a2\u0006\u0010C\u001a\u00020\u001a2\n\u0010\"\u001a\u0006\u0012\u0002\b\u00030DH\u0002J\u001a\u0010E\u001a\u0004\u0018\u00010'2\u0006\u0010F\u001a\u00020\u001a2\u0006\u0010G\u001a\u00020\u001aH\u0002J>\u0010H\u001a\u00020)\"\b\b\u0000\u0010!*\u00020\u0013*\b\u0012\u0004\u0012\u0002H!0\u00142\u001f\b\u0004\u0010I\u001a\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u0002H!0\u0014\u0012\u0004\u0012\u00020)0J¢\u0006\u0002\bKH\u0082\bJ\u0010\u0010L\u001a\u00020)*\u0006\u0012\u0002\b\u00030\u0014H\u0002J>\u0010M\u001a\u00020)\"\b\b\u0000\u0010!*\u00020\u0013*\b\u0012\u0004\u0012\u0002H!0\u00142\u001f\b\u0004\u0010I\u001a\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u0002H!0\u0014\u0012\u0004\u0012\u00020'0J¢\u0006\u0002\bKH\u0082\bJ\u0010\u0010N\u001a\u00020)*\u0006\u0012\u0002\b\u00030\u0014H\u0002J\u0010\u0010O\u001a\u00020)*\u0006\u0012\u0002\b\u00030\u0014H\u0002J\u001c\u0010P\u001a\u00020)\"\b\b\u0000\u0010!*\u00020\u0013*\b\u0012\u0004\u0012\u0002H!0\u0014H\u0002J\u0010\u0010Q\u001a\u00020)*\u0006\u0012\u0002\b\u00030\u0014H\u0002JD\u0010R\u001a\u00020)*\u0006\u0012\u0002\b\u00030\u00142\f\u0010S\u001a\b\u0012\u0004\u0012\u00020T0\u001d2\f\u0010U\u001a\b\u0012\u0004\u0012\u00020\u001a0\u001d2\u0006\u0010V\u001a\u00020\u00132\u0006\u0010W\u001a\u00020.2\u0006\u0010X\u001a\u00020YH\u0002J6\u0010Z\u001a\u00020)*\u0006\u0012\u0002\b\u00030\u00142\f\u0010S\u001a\b\u0012\u0004\u0012\u00020T0\u001d2\u0006\u0010[\u001a\u00020\\2\u0006\u0010-\u001a\u00020.2\u0006\u0010X\u001a\u00020YH\u0002JV\u0010]\u001a\u00020'\"\b\b\u0000\u0010!*\u00020\u0013*\b\u0012\u0004\u0012\u0002H!0\u00142\f\u00103\u001a\b\u0012\u0004\u0012\u0002H!042\b\u0010^\u001a\u0004\u0018\u00010_2\b\u0010`\u001a\u0004\u0018\u00010a2\u0006\u0010b\u001a\u00020\u00182\u0006\u0010c\u001a\u00020\u00182\u0006\u0010d\u001a\u00020\u0018H\u0002J\u0010\u0010e\u001a\u00020)*\u0006\u0012\u0002\b\u00030\u0014H\u0002J$\u0010e\u001a\u00020)*\u0006\u0012\u0002\b\u00030\u00142\b\u0010f\u001a\u0004\u0018\u00010_2\b\u0010`\u001a\u0004\u0018\u00010aH\u0002J\u0010\u0010g\u001a\u00020)*\u0006\u0012\u0002\b\u00030\u0014H\u0002J$\u0010h\u001a\u00020'*\u0006\u0012\u0002\b\u00030\u00142\b\u0010`\u001a\u0004\u0018\u00010a2\b\u0010i\u001a\u0004\u0018\u00010\u001aH\u0002J\u0010\u0010j\u001a\u00020)*\u0006\u0012\u0002\b\u00030\u0014H\u0002J$\u0010k\u001a\u00020\u0018*\u0006\u0012\u0002\b\u00030\u00142\b\u0010`\u001a\u0004\u0018\u00010a2\b\u0010i\u001a\u0004\u0018\u00010\u001aH\u0002J,\u0010l\u001a\u00020\u0018*\u0006\u0012\u0002\b\u00030\u00142\b\u0010`\u001a\u0004\u0018\u00010a2\b\u0010i\u001a\u0004\u0018\u00010\u001a2\u0006\u0010d\u001a\u00020\u0018H\u0002J\u001c\u0010m\u001a\u00020)\"\b\b\u0000\u0010!*\u00020\u0013*\b\u0012\u0004\u0012\u0002H!0\u0014H\u0002J\u0010\u0010n\u001a\u00020)*\u0006\u0012\u0002\b\u00030\u0014H\u0002J\u001c\u0010o\u001a\u00020\u0018\"\b\b\u0000\u0010!*\u00020\u0013*\b\u0012\u0004\u0012\u0002H!0\u0014H\u0002R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u0012\u001a\u00020\u0013*\u0006\u0012\u0002\b\u00030\u00148BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016¨\u0006r"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/CandidateResolver;", "", "argumentTypeResolver", "Lorg/jetbrains/kotlin/resolve/calls/ArgumentTypeResolver;", "genericCandidateResolver", "Lorg/jetbrains/kotlin/resolve/calls/GenericCandidateResolver;", "reflectionTypes", "Lorg/jetbrains/kotlin/builtins/ReflectionTypes;", "additionalTypeCheckers", "", "Lorg/jetbrains/kotlin/resolve/calls/checkers/AdditionalTypeChecker;", "smartCastManager", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/SmartCastManager;", "dataFlowValueFactory", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValueFactory;", "upperBoundChecker", "Lorg/jetbrains/kotlin/resolve/UpperBoundChecker;", "(Lorg/jetbrains/kotlin/resolve/calls/ArgumentTypeResolver;Lorg/jetbrains/kotlin/resolve/calls/GenericCandidateResolver;Lorg/jetbrains/kotlin/builtins/ReflectionTypes;Ljava/lang/Iterable;Lorg/jetbrains/kotlin/resolve/calls/smartcasts/SmartCastManager;Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValueFactory;Lorg/jetbrains/kotlin/resolve/UpperBoundChecker;)V", "candidateDescriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "Lorg/jetbrains/kotlin/resolve/calls/context/CallCandidateResolutionContext;", "getCandidateDescriptor", "(Lorg/jetbrains/kotlin/resolve/calls/context/CallCandidateResolutionContext;)Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "canBeSubtype", "", "subType", "Lorg/jetbrains/kotlin/types/KotlinType;", "superType", "candidateTypeParameters", "", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "checkAllValueArguments", "Lorg/jetbrains/kotlin/resolve/calls/CandidateResolver$ValueArgumentsCheckingResult;", "D", "context", "resolveFunctionArgumentBodies", "Lorg/jetbrains/kotlin/resolve/calls/util/ResolveArgumentsMode;", "checkOuterClassMemberIsAccessible", "checkReceivers", "Lorg/jetbrains/kotlin/resolve/calls/results/ResolutionStatus;", "checkTypeInTypeAliasSubstitutionRec", "", "reportStrategy", "Lorg/jetbrains/kotlin/types/TypeAliasExpansionReportStrategy;", "unsubstitutedType", "typeAliasParametersSubstitutor", "Lorg/jetbrains/kotlin/types/TypeSubstitutor;", "boundsSubstitutor", "checkValueArgumentTypes", "C", "Lorg/jetbrains/kotlin/resolve/calls/context/CallResolutionContext;", "candidateCall", "Lorg/jetbrains/kotlin/resolve/calls/model/MutableResolvedCall;", "getDeclaringClass", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "candidate", "getReceiverSuper", "Lorg/jetbrains/kotlin/psi/KtSuperExpression;", "receiver", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/Receiver;", "performResolutionForCandidateCall", "checkArguments", "Lorg/jetbrains/kotlin/resolve/calls/context/CheckArgumentTypesMode;", "smartCastValueArgumentTypeIfPossible", "expression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "expectedType", "actualType", "Lorg/jetbrains/kotlin/resolve/calls/context/ResolutionContext;", "tryNotNullableArgument", "argumentType", "parameterType", "check", "checker", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "checkAbstractAndSuper", "checkAndReport", "checkConstructedExpandedType", "checkDispatchReceiver", "checkExpectedCallableType", "checkExtensionReceiver", "checkGenericBoundsInAFunctionCall", "ktTypeArguments", "Lorg/jetbrains/kotlin/psi/KtTypeProjection;", "typeArguments", "functionDescriptor", "substitutor", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "checkGenericBoundsInTypeAliasConstructorCall", "typeAliasConstructorDescriptor", "Lorg/jetbrains/kotlin/descriptors/impl/TypeAliasConstructorDescriptor;", "checkReceiver", "receiverParameter", "Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "receiverArgument", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "isExplicitReceiver", "implicitInvokeCheck", "isDispatchReceiver", "checkReceiverTypeError", "receiverParameterDescriptor", "checkValueArguments", "checkVisibilityWithDispatchReceiver", "smartCastType", "checkVisibilityWithoutReceiver", "isCandidateVisible", "isCandidateVisibleOrExtensionReceiver", "mapArguments", "processTypeArguments", "shouldContinue", "TypeAliasSingleStepExpansionReportStrategy", "ValueArgumentsCheckingResult", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CandidateResolver {
    private final Iterable<AdditionalTypeChecker> additionalTypeCheckers;
    private final ArgumentTypeResolver argumentTypeResolver;
    private final DataFlowValueFactory dataFlowValueFactory;
    private final GenericCandidateResolver genericCandidateResolver;
    private final ReflectionTypes reflectionTypes;
    private final SmartCastManager smartCastManager;
    private final UpperBoundChecker upperBoundChecker;

    /* JADX INFO: compiled from: CandidateResolver.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[CheckArgumentTypesMode.values().length];
            try {
                iArr[CheckArgumentTypesMode.CHECK_VALUE_ARGUMENTS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[CheckArgumentTypesMode.CHECK_CALLABLE_TYPE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public CandidateResolver(ArgumentTypeResolver argumentTypeResolver, GenericCandidateResolver genericCandidateResolver, ReflectionTypes reflectionTypes, Iterable<? extends AdditionalTypeChecker> additionalTypeCheckers, SmartCastManager smartCastManager, DataFlowValueFactory dataFlowValueFactory, UpperBoundChecker upperBoundChecker) {
        Intrinsics.checkNotNullParameter(argumentTypeResolver, "argumentTypeResolver");
        Intrinsics.checkNotNullParameter(genericCandidateResolver, "genericCandidateResolver");
        Intrinsics.checkNotNullParameter(reflectionTypes, "reflectionTypes");
        Intrinsics.checkNotNullParameter(additionalTypeCheckers, "additionalTypeCheckers");
        Intrinsics.checkNotNullParameter(smartCastManager, "smartCastManager");
        Intrinsics.checkNotNullParameter(dataFlowValueFactory, "dataFlowValueFactory");
        Intrinsics.checkNotNullParameter(upperBoundChecker, "upperBoundChecker");
        this.argumentTypeResolver = argumentTypeResolver;
        this.genericCandidateResolver = genericCandidateResolver;
        this.reflectionTypes = reflectionTypes;
        this.additionalTypeCheckers = additionalTypeCheckers;
        this.smartCastManager = smartCastManager;
        this.dataFlowValueFactory = dataFlowValueFactory;
        this.upperBoundChecker = upperBoundChecker;
    }

    public final <D extends CallableDescriptor> void performResolutionForCandidateCall(CallCandidateResolutionContext<D> context, CheckArgumentTypesMode checkArguments) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(checkArguments, "checkArguments");
        ProgressIndicatorAndCompilationCanceledStatus.checkCanceled();
        if (ErrorUtils.isError(getCandidateDescriptor(context))) {
            context.candidateCall.addStatus(ResolutionStatus.SUCCESS);
            return;
        }
        if (!checkOuterClassMemberIsAccessible(context)) {
            context.candidateCall.addStatus(ResolutionStatus.OTHER_ERROR);
            return;
        }
        if (!context.isDebuggerContext) {
            checkVisibilityWithoutReceiver(context);
        }
        int i = WhenMappings.$EnumSwitchMapping$0[checkArguments.ordinal()];
        if (i == 1) {
            mapArguments(context);
        } else if (i == 2) {
            checkExpectedCallableType(context);
        }
        checkReceiverTypeError(context);
        checkExtensionReceiver(context);
        checkDispatchReceiver(context);
        processTypeArguments(context);
        checkValueArguments(context);
        checkAbstractAndSuper(context);
        checkConstructedExpandedType(context);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean canBeSubtype(KotlinType subType, KotlinType superType, List<? extends TypeParameterDescriptor> candidateTypeParameters) {
        SimpleType DONT_CARE = TypeUtils.DONT_CARE;
        Intrinsics.checkNotNullExpressionValue(DONT_CARE, "DONT_CARE");
        KotlinType kotlinTypeSubstitute = GenericCandidateResolverKt.makeConstantSubstitutor(candidateTypeParameters, DONT_CARE).substitute(subType, Variance.INVARIANT);
        if (kotlinTypeSubstitute == null) {
            return true;
        }
        return ErrorTypesAreEqualToAnything.INSTANCE.isSubtypeOf(kotlinTypeSubstitute, superType);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ResolutionStatus checkVisibilityWithDispatchReceiver(CallCandidateResolutionContext<?> callCandidateResolutionContext, ReceiverValue receiverValue, KotlinType kotlinType) {
        ReceiverValue receiverValueWithSmartCast = SmartCastUtilKt.getReceiverValueWithSmartCast(receiverValue, kotlinType);
        CallableDescriptor candidateDescriptor = getCandidateDescriptor(callCandidateResolutionContext);
        DeclarationDescriptor ownerDescriptor = callCandidateResolutionContext.scope.getOwnerDescriptor();
        LanguageVersionSettings languageVersionSettings = callCandidateResolutionContext.languageVersionSettings;
        Intrinsics.checkNotNullExpressionValue(languageVersionSettings, "languageVersionSettings");
        DeclarationDescriptorWithVisibility declarationDescriptorWithVisibilityFindInvisibleMember = DescriptorVisibilityUtils.findInvisibleMember(receiverValueWithSmartCast, candidateDescriptor, ownerDescriptor, languageVersionSettings);
        if (declarationDescriptorWithVisibilityFindInvisibleMember != null) {
            callCandidateResolutionContext.tracing.invisibleMember(callCandidateResolutionContext.trace, declarationDescriptorWithVisibilityFindInvisibleMember);
            return ResolutionStatus.INVISIBLE_MEMBER_ERROR;
        }
        return ResolutionStatus.SUCCESS;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isCandidateVisibleOrExtensionReceiver(CallCandidateResolutionContext<?> callCandidateResolutionContext, ReceiverValue receiverValue, KotlinType kotlinType, boolean z) {
        return !z || isCandidateVisible(callCandidateResolutionContext, receiverValue, kotlinType);
    }

    private final boolean isCandidateVisible(CallCandidateResolutionContext<?> callCandidateResolutionContext, ReceiverValue receiverValue, KotlinType kotlinType) {
        ReceiverValue receiverValueWithSmartCast = SmartCastUtilKt.getReceiverValueWithSmartCast(receiverValue, kotlinType);
        CallableDescriptor candidateDescriptor = getCandidateDescriptor(callCandidateResolutionContext);
        DeclarationDescriptor ownerDescriptor = callCandidateResolutionContext.scope.getOwnerDescriptor();
        LanguageVersionSettings languageVersionSettings = callCandidateResolutionContext.languageVersionSettings;
        Intrinsics.checkNotNullExpressionValue(languageVersionSettings, "languageVersionSettings");
        return DescriptorVisibilityUtils.findInvisibleMember(receiverValueWithSmartCast, candidateDescriptor, ownerDescriptor, languageVersionSettings) == null;
    }

    private static final boolean checkOuterClassMemberIsAccessible$insideScript(KtElement ktElement) {
        PsiFile containingFile = ktElement.getContainingFile();
        KtFile ktFile = containingFile instanceof KtFile ? (KtFile) containingFile : null;
        if (ktFile != null) {
            return ktFile.isScript();
        }
        return false;
    }

    private final boolean checkOuterClassMemberIsAccessible(CallCandidateResolutionContext<?> context) {
        KtElement callElement = context.call.getCallElement();
        Intrinsics.checkNotNullExpressionValue(callElement, "getCallElement(...)");
        if (checkOuterClassMemberIsAccessible$insideScript(callElement) && context.call.getExplicitReceiver() == null && context.call.getDispatchReceiver() == null) {
            CallableDescriptor candidateDescriptor = context.candidateCall.get$suspendPropertyDescriptor();
            Intrinsics.checkNotNullExpressionValue(candidateDescriptor, "getCandidateDescriptor(...)");
            ClassDescriptor declaringClass = getDeclaringClass(candidateDescriptor);
            if (declaringClass != null && !declaringClass.getClassKind().isSingleton()) {
                return DescriptorResolver.checkHasOuterClassInstance(context.scope, context.trace, context.call.getCallElement(), declaringClass);
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final KtSuperExpression getReceiverSuper(Receiver receiver) {
        if (!(receiver instanceof ExpressionReceiver)) {
            return null;
        }
        KtExpression expression = ((ExpressionReceiver) receiver).getExpression();
        if (expression instanceof KtSuperExpression) {
            return (KtSuperExpression) expression;
        }
        return null;
    }

    private final ClassDescriptor getDeclaringClass(CallableDescriptor candidate) {
        ReceiverParameterDescriptor receiverParameterDescriptorMo6753getDispatchReceiverParameter = candidate.mo6753getDispatchReceiverParameter();
        if (receiverParameterDescriptorMo6753getDispatchReceiverParameter == null) {
            return null;
        }
        DeclarationDescriptor containingDeclaration = receiverParameterDescriptorMo6753getDispatchReceiverParameter.getContainingModule();
        Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
        if (containingDeclaration instanceof ClassDescriptor) {
            return (ClassDescriptor) containingDeclaration;
        }
        return null;
    }

    public final <D extends CallableDescriptor> ValueArgumentsCheckingResult checkAllValueArguments(CallCandidateResolutionContext<D> context, ResolveArgumentsMode resolveFunctionArgumentBodies) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(resolveFunctionArgumentBodies, "resolveFunctionArgumentBodies");
        MutableResolvedCall<D> candidateCall = context.candidateCall;
        Intrinsics.checkNotNullExpressionValue(candidateCall, "candidateCall");
        ValueArgumentsCheckingResult valueArgumentsCheckingResultCheckValueArgumentTypes = checkValueArgumentTypes(context, candidateCall, resolveFunctionArgumentBodies);
        ResolutionStatus resolutionStatusCombine = valueArgumentsCheckingResultCheckValueArgumentTypes.getStatus().combine(checkReceivers(context));
        Intrinsics.checkNotNullExpressionValue(resolutionStatusCombine, "combine(...)");
        return new ValueArgumentsCheckingResult(this, resolutionStatusCombine, valueArgumentsCheckingResultCheckValueArgumentTypes.getArgumentTypes());
    }

    /* JADX WARN: Code duplicated, block: B:44:0x011e  */
    private final <D extends CallableDescriptor, C extends CallResolutionContext<C>> ValueArgumentsCheckingResult checkValueArgumentTypes(CallResolutionContext<C> context, MutableResolvedCall<D> candidateCall, ResolveArgumentsMode resolveFunctionArgumentBodies) {
        MutableDataFlowInfoForArguments mutableDataFlowInfoForArguments;
        ArgumentMatchStatus argumentMatchStatus;
        KotlinType kotlinType;
        LeafPsiElement spreadElement;
        SmartCastResult smartCastResultCheckAndRecordPossibleCast$default;
        ResolutionStatus resolutionStatusTryNotNullableArgument = ResolutionStatus.SUCCESS;
        ArrayList arrayListNewArrayList = Lists.newArrayList();
        MutableDataFlowInfoForArguments dataFlowInfoForArguments = candidateCall.getDataFlowInfoForArguments();
        Intrinsics.checkNotNullExpressionValue(dataFlowInfoForArguments, "getDataFlowInfoForArguments(...)");
        Map<ValueParameterDescriptor, ResolvedValueArgument> valueArguments = candidateCall.getValueArguments();
        Intrinsics.checkNotNullExpressionValue(valueArguments, "getValueArguments(...)");
        for (Map.Entry<ValueParameterDescriptor, ResolvedValueArgument> entry : valueArguments.entrySet()) {
            ValueParameterDescriptor key = entry.getKey();
            for (ValueArgument valueArgument : entry.getValue().getArguments()) {
                KtExpression argumentExpression = valueArgument.getArgumentExpression();
                if (argumentExpression != null) {
                    Intrinsics.checkNotNull(key);
                    Intrinsics.checkNotNull(valueArgument);
                    CallResolutionContext<C> callResolutionContext = context;
                    KotlinType effectiveExpectedType = CallResolverUtilKt.getEffectiveExpectedType(key, valueArgument, callResolutionContext);
                    ResolutionContext resolutionContextReplaceExpectedType = context.replaceDataFlowInfo(dataFlowInfoForArguments.getInfo(valueArgument)).replaceExpectedType(effectiveExpectedType);
                    Intrinsics.checkNotNullExpressionValue(resolutionContextReplaceExpectedType, "replaceExpectedType(...)");
                    CallResolutionContext<?> callResolutionContext2 = (CallResolutionContext) resolutionContextReplaceExpectedType;
                    KotlinTypeInfo argumentTypeInfo = this.argumentTypeResolver.getArgumentTypeInfo(argumentExpression, callResolutionContext2, resolveFunctionArgumentBodies, FunctionTypesKt.isSuspendFunctionType(effectiveExpectedType));
                    Intrinsics.checkNotNullExpressionValue(argumentTypeInfo, "getArgumentTypeInfo(...)");
                    KotlinType type = argumentTypeInfo.getType();
                    dataFlowInfoForArguments.updateInfo(valueArgument, argumentTypeInfo.getDataFlowInfo());
                    ArgumentMatchStatus argumentMatchStatus2 = ArgumentMatchStatus.SUCCESS;
                    if (type == null || (KotlinTypeKt.isError(type) && !FunctionPlaceholdersKt.isFunctionPlaceholder(type))) {
                        mutableDataFlowInfoForArguments = dataFlowInfoForArguments;
                        argumentMatchStatus2 = ArgumentMatchStatus.ARGUMENT_HAS_NO_TYPE;
                        resolutionStatusTryNotNullableArgument = resolutionStatusTryNotNullableArgument;
                    } else if (TypeUtils.noExpectedType(effectiveExpectedType)) {
                        mutableDataFlowInfoForArguments = dataFlowInfoForArguments;
                    } else {
                        ResolutionStatus resolutionStatus = resolutionStatusTryNotNullableArgument;
                        if (!this.argumentTypeResolver.isSubtypeOfForArgumentType(type, effectiveExpectedType)) {
                            KotlinType expectedType = callResolutionContext2.expectedType;
                            mutableDataFlowInfoForArguments = dataFlowInfoForArguments;
                            Intrinsics.checkNotNullExpressionValue(expectedType, "expectedType");
                            KotlinType kotlinTypeSmartCastValueArgumentTypeIfPossible = smartCastValueArgumentTypeIfPossible(argumentExpression, expectedType, type, callResolutionContext2);
                            if (kotlinTypeSmartCastValueArgumentTypeIfPossible == null) {
                                resolutionStatusTryNotNullableArgument = tryNotNullableArgument(type, effectiveExpectedType);
                                if (resolutionStatusTryNotNullableArgument == null) {
                                    resolutionStatusTryNotNullableArgument = ResolutionStatus.OTHER_ERROR;
                                }
                                argumentMatchStatus = ArgumentMatchStatus.TYPE_MISMATCH;
                                kotlinType = type;
                            } else {
                                kotlinType = kotlinTypeSmartCastValueArgumentTypeIfPossible;
                                argumentMatchStatus = argumentMatchStatus2;
                            }
                            spreadElement = valueArgument.getSpreadElement();
                            if (spreadElement != null && !FlexibleTypesKt.isFlexible(type) && type.getIsMarkedNullable()) {
                                smartCastResultCheckAndRecordPossibleCast$default = SmartCastManager.checkAndRecordPossibleCast$default(this.smartCastManager, this.dataFlowValueFactory.createDataFlowValue(argumentExpression, type, callResolutionContext), effectiveExpectedType, argumentExpression, callResolutionContext, null, false, null, 64, null);
                                if (smartCastResultCheckAndRecordPossibleCast$default != null || !smartCastResultCheckAndRecordPossibleCast$default.getIsCorrect()) {
                                    context.trace.report(Errors.SPREAD_OF_NULLABLE.on(spreadElement));
                                }
                            }
                            type = kotlinType;
                            argumentMatchStatus2 = argumentMatchStatus;
                        } else {
                            mutableDataFlowInfoForArguments = dataFlowInfoForArguments;
                            if (ErrorUtils.INSTANCE.containsUninferredTypeVariable(effectiveExpectedType)) {
                                argumentMatchStatus2 = ArgumentMatchStatus.MATCH_MODULO_UNINFERRED_TYPES;
                            }
                            argumentMatchStatus = argumentMatchStatus2;
                            kotlinType = type;
                        }
                        resolutionStatusTryNotNullableArgument = resolutionStatus;
                        spreadElement = valueArgument.getSpreadElement();
                        if (spreadElement != null) {
                            smartCastResultCheckAndRecordPossibleCast$default = SmartCastManager.checkAndRecordPossibleCast$default(this.smartCastManager, this.dataFlowValueFactory.createDataFlowValue(argumentExpression, type, callResolutionContext), effectiveExpectedType, argumentExpression, callResolutionContext, null, false, null, 64, null);
                            if (smartCastResultCheckAndRecordPossibleCast$default != null) {
                                context.trace.report(Errors.SPREAD_OF_NULLABLE.on(spreadElement));
                            } else {
                                context.trace.report(Errors.SPREAD_OF_NULLABLE.on(spreadElement));
                            }
                        }
                        type = kotlinType;
                        argumentMatchStatus2 = argumentMatchStatus;
                    }
                    arrayListNewArrayList.add(type);
                    candidateCall.recordArgumentMatchStatus(valueArgument, argumentMatchStatus2);
                    dataFlowInfoForArguments = mutableDataFlowInfoForArguments;
                }
            }
            dataFlowInfoForArguments = dataFlowInfoForArguments;
        }
        Intrinsics.checkNotNull(arrayListNewArrayList);
        return new ValueArgumentsCheckingResult(this, resolutionStatusTryNotNullableArgument, arrayListNewArrayList);
    }

    private final KotlinType smartCastValueArgumentTypeIfPossible(KtExpression expression, KotlinType expectedType, KotlinType actualType, ResolutionContext<?> context) {
        Object next;
        ExpressionReceiver.Companion companion = ExpressionReceiver.INSTANCE;
        KtExpression ktExpressionSafeDeparenthesize = KtPsiUtil.safeDeparenthesize(expression);
        Intrinsics.checkNotNullExpressionValue(ktExpressionSafeDeparenthesize, "safeDeparenthesize(...)");
        BindingContext context2 = context.trace.getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getBindingContext(...)");
        Iterator<T> it = this.smartCastManager.getSmartCastVariantsExcludingReceiver(context, companion.create(ktExpressionSafeDeparenthesize, actualType, context2)).iterator();
        while (it.hasNext()) {
            next = it.next();
            if (KotlinTypeChecker.DEFAULT.isSubtypeOf((KotlinType) next, expectedType)) {
                return (KotlinType) next;
            }
        }
        next = null;
        return (KotlinType) next;
    }

    private final ResolutionStatus tryNotNullableArgument(KotlinType argumentType, KotlinType parameterType) {
        if (!argumentType.getIsMarkedNullable() || parameterType.getIsMarkedNullable()) {
            return null;
        }
        if (this.argumentTypeResolver.isSubtypeOfForArgumentType(TypeUtilsKt.makeNotNullable(argumentType), parameterType)) {
            return ResolutionStatus.NULLABLE_ARGUMENT_TYPE_MISMATCH;
        }
        return null;
    }

    private final <D extends CallableDescriptor> ResolutionStatus checkReceivers(CallCandidateResolutionContext<D> context) {
        ResolutionStatus resolutionStatus = ResolutionStatus.SUCCESS;
        MutableResolvedCall<D> candidateCall = context.candidateCall;
        Intrinsics.checkNotNullExpressionValue(candidateCall, "candidateCall");
        ResolutionStatus resolutionStatusCombine = resolutionStatus.combine(checkReceiver(context, candidateCall, candidateCall.getResultingDescriptor().getExtensionReceiverParameter(), candidateCall.getExtensionReceiver(), candidateCall.getExplicitReceiverKind().isExtensionReceiver(), false, false));
        Intrinsics.checkNotNullExpressionValue(resolutionStatusCombine, "combine(...)");
        ResolutionStatus resolutionStatusCombine2 = resolutionStatusCombine.combine(checkReceiver(context, candidateCall, candidateCall.getResultingDescriptor().mo6753getDispatchReceiverParameter(), candidateCall.getDispatchReceiver(), candidateCall.getExplicitReceiverKind().isDispatchReceiver(), context.call instanceof CallTransformer.CallForImplicitInvoke, true));
        Intrinsics.checkNotNullExpressionValue(resolutionStatusCombine2, "combine(...)");
        if (context.isDebuggerContext || candidateCall.getDispatchReceiver() == null || !isCandidateVisible(context, DescriptorVisibilities.ALWAYS_SUITABLE_RECEIVER, null)) {
            return resolutionStatusCombine2;
        }
        ResolutionStatus resolutionStatusCombine3 = resolutionStatusCombine2.combine(checkVisibilityWithDispatchReceiver(context, candidateCall.getDispatchReceiver(), candidateCall.getSmartCastDispatchReceiverType()));
        Intrinsics.checkNotNullExpressionValue(resolutionStatusCombine3, "combine(...)");
        return resolutionStatusCombine3;
    }

    /* JADX WARN: Code duplicated, block: B:50:0x00dc  */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v6, types: [org.jetbrains.kotlin.resolve.calls.context.ResolutionContext] */
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
    private final <D extends CallableDescriptor> ResolutionStatus checkReceiver(final CallCandidateResolutionContext<D> callCandidateResolutionContext, MutableResolvedCall<D> mutableResolvedCall, ReceiverParameterDescriptor receiverParameterDescriptor, final ReceiverValue receiverValue, boolean z, boolean z2, final boolean z3) {
        boolean z4;
        boolean z5;
        if (receiverParameterDescriptor == null || receiverValue == null) {
            return ResolutionStatus.SUCCESS;
        }
        D candidateDescriptor = mutableResolvedCall.get$suspendPropertyDescriptor();
        Intrinsics.checkNotNullExpressionValue(candidateDescriptor, "getCandidateDescriptor(...)");
        if (TypeUtils.dependsOnTypeParameters(receiverParameterDescriptor.getType(), candidateDescriptor.getTypeParameters())) {
            return ResolutionStatus.SUCCESS;
        }
        Call call = mutableResolvedCall.getCall();
        Intrinsics.checkNotNullExpressionValue(call, "getCall(...)");
        boolean z6 = z && !z2 && call.isSemanticallyEquivalentToSafeCall();
        KotlinType type = receiverParameterDescriptor.getType();
        if (z6) {
            type = TypeUtils.makeNullable(type);
        }
        KotlinType kotlinType = type;
        Intrinsics.checkNotNull(kotlinType);
        CallCandidateResolutionContext<D> callCandidateResolutionContext2 = callCandidateResolutionContext;
        SmartCastManager.ReceiverSmartCastResult smartCastReceiverResult = this.smartCastManager.getSmartCastReceiverResult(receiverValue, kotlinType, callCandidateResolutionContext2);
        if (smartCastReceiverResult == null) {
            callCandidateResolutionContext.tracing.wrongReceiverType(callCandidateResolutionContext.trace, receiverParameterDescriptor, receiverValue, callCandidateResolutionContext.replaceCallPosition(new CallPosition.ExtensionReceiverPosition(mutableResolvedCall)));
            return ResolutionStatus.OTHER_ERROR;
        }
        boolean z7 = smartCastReceiverResult != SmartCastManager.ReceiverSmartCastResult.OK;
        boolean z8 = z7 || !isCandidateVisibleOrExtensionReceiver(callCandidateResolutionContext, receiverValue, null, z3);
        KotlinType type2 = receiverValue.getType();
        Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
        if (z2 && (call instanceof CallTransformer.CallForImplicitInvoke) && CallUtilKt.isSafeCall(call)) {
            CallTransformer.CallForImplicitInvoke callForImplicitInvoke = (CallTransformer.CallForImplicitInvoke) call;
            Receiver explicitReceiver = callForImplicitInvoke.getOuterCall().getExplicitReceiver();
            if (Intrinsics.areEqual(explicitReceiver, callForImplicitInvoke.getExplicitReceiver()) || !(explicitReceiver instanceof ReceiverValue)) {
                z4 = false;
            } else {
                if (!callCandidateResolutionContext.dataFlowInfo.getStableNullability(callCandidateResolutionContext.dataFlowValueFactory.createDataFlowValue((ReceiverValue) explicitReceiver, callCandidateResolutionContext2)).canBeNull() || TypeUtils.isNullableType(kotlinType)) {
                    z4 = false;
                } else {
                    type2 = TypeUtils.makeNullable(type2);
                    Intrinsics.checkNotNullExpressionValue(type2, "makeNullable(...)");
                    z4 = true;
                }
            }
        } else {
            z4 = false;
        }
        DataFlowValue dataFlowValueCreateDataFlowValue = callCandidateResolutionContext.dataFlowValueFactory.createDataFlowValue(receiverValue, callCandidateResolutionContext2);
        Nullability stableNullability = callCandidateResolutionContext.dataFlowInfo.getStableNullability(dataFlowValueCreateDataFlowValue);
        ExpressionReceiver expressionReceiver = receiverValue instanceof ExpressionReceiver ? (ExpressionReceiver) receiverValue : null;
        KtExpression expression = expressionReceiver != null ? expressionReceiver.getExpression() : null;
        if (stableNullability.canBeNull() && !stableNullability.canBeNonNull()) {
            z5 = !TypeUtils.isNullableType(kotlinType);
            if (dataFlowValueCreateDataFlowValue.getImmanentNullability().canBeNonNull() && expression != null) {
                callCandidateResolutionContext.trace.record(BindingContext.SMARTCAST_NULL, expression);
            }
        } else if (z4 || !z8) {
            z5 = false;
        } else {
            SmartCastResult smartCastResultCheckAndRecordPossibleCast = this.smartCastManager.checkAndRecordPossibleCast(dataFlowValueCreateDataFlowValue, kotlinType, expression, callCandidateResolutionContext2, mutableResolvedCall.getCall(), true, new Function1<KotlinType, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.CandidateResolver$checkReceiver$smartCastResult$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(KotlinType possibleSmartCast) {
                    Intrinsics.checkNotNullParameter(possibleSmartCast, "possibleSmartCast");
                    return Boolean.valueOf(this.this$0.isCandidateVisibleOrExtensionReceiver(callCandidateResolutionContext, receiverValue, possibleSmartCast, z3));
                }
            });
            if (smartCastResultCheckAndRecordPossibleCast != null) {
                if (z3) {
                    mutableResolvedCall.setSmartCastDispatchReceiverType(smartCastResultCheckAndRecordPossibleCast.getResultType());
                } else {
                    mutableResolvedCall.updateExtensionReceiverWithSmartCastIfNeeded(smartCastResultCheckAndRecordPossibleCast.getResultType());
                }
                if (!smartCastResultCheckAndRecordPossibleCast.getIsCorrect()) {
                    return ResolutionStatus.UNSTABLE_SMARTCAST_FOR_RECEIVER_ERROR;
                }
            } else if (z7) {
                z5 = true;
            }
            z5 = false;
        }
        if (z5 || z4) {
            callCandidateResolutionContext.tracing.unsafeCall(callCandidateResolutionContext.trace, type2, z2);
            return ResolutionStatus.UNSAFE_CALL_ERROR;
        }
        Iterator<AdditionalTypeChecker> it = this.additionalTypeCheckers.iterator();
        while (it.hasNext()) {
            it.next().checkReceiver(receiverParameterDescriptor, receiverValue, z6, callCandidateResolutionContext);
        }
        return ResolutionStatus.SUCCESS;
    }

    /* JADX INFO: compiled from: CandidateResolver.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0086\u0004\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/CandidateResolver$ValueArgumentsCheckingResult;", "", NotificationCompat.CATEGORY_STATUS, "Lorg/jetbrains/kotlin/resolve/calls/results/ResolutionStatus;", "argumentTypes", "", "Lorg/jetbrains/kotlin/types/KotlinType;", "(Lorg/jetbrains/kotlin/resolve/calls/CandidateResolver;Lorg/jetbrains/kotlin/resolve/calls/results/ResolutionStatus;Ljava/util/List;)V", "getArgumentTypes", "()Ljava/util/List;", "getStatus", "()Lorg/jetbrains/kotlin/resolve/calls/results/ResolutionStatus;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public final class ValueArgumentsCheckingResult {
        private final List<KotlinType> argumentTypes;
        private final ResolutionStatus status;
        final /* synthetic */ CandidateResolver this$0;

        /* JADX WARN: Multi-variable type inference failed */
        public ValueArgumentsCheckingResult(CandidateResolver candidateResolver, ResolutionStatus status, List<? extends KotlinType> argumentTypes) {
            Intrinsics.checkNotNullParameter(status, "status");
            Intrinsics.checkNotNullParameter(argumentTypes, "argumentTypes");
            this.this$0 = candidateResolver;
            this.status = status;
            this.argumentTypes = argumentTypes;
        }

        public final List<KotlinType> getArgumentTypes() {
            return this.argumentTypes;
        }

        public final ResolutionStatus getStatus() {
            return this.status;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void checkGenericBoundsInAFunctionCall(CallCandidateResolutionContext<?> callCandidateResolutionContext, List<? extends KtTypeProjection> list, List<? extends KotlinType> list2, CallableDescriptor callableDescriptor, TypeSubstitutor typeSubstitutor, BindingTrace bindingTrace) {
        if (callableDescriptor instanceof TypeAliasConstructorDescriptor) {
            checkGenericBoundsInTypeAliasConstructorCall(callCandidateResolutionContext, list, (TypeAliasConstructorDescriptor) callableDescriptor, typeSubstitutor, bindingTrace);
            return;
        }
        List<TypeParameterDescriptor> typeParameters = callableDescriptor.getTypeParameters();
        Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
        int iMin = Math.min(typeParameters.size(), list.size()) - 1;
        if (iMin < 0) {
            return;
        }
        int i = 0;
        while (true) {
            TypeParameterDescriptor typeParameterDescriptor = typeParameters.get(i);
            KotlinType kotlinType = list2.get(i);
            KtTypeReference typeReference = list.get(i).getTypeReference();
            if (typeReference != null) {
                UpperBoundChecker upperBoundChecker = this.upperBoundChecker;
                Intrinsics.checkNotNull(typeParameterDescriptor);
                UpperBoundChecker.checkBounds$default(upperBoundChecker, typeReference, kotlinType, typeParameterDescriptor, typeSubstitutor, bindingTrace, null, null, 96, null);
            }
            if (i == iMin) {
                return;
            } else {
                i++;
            }
        }
    }

    /* JADX INFO: compiled from: CandidateResolver.kt */
    @Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0002\u0018\u00002\u00020\u0001B3\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f¢\u0006\u0002\u0010\rJ(\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0019\u001a\u00020\u0010H\u0016J\"\u0010\u001a\u001a\u00020\u00132\u0006\u0010\u0004\u001a\u00020\u00052\b\u0010\u0019\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u001b\u001a\u00020\u0017H\u0016J\u0010\u0010\u001c\u001a\u00020\u00132\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0010\u0010\u001d\u001a\u00020\u00132\u0006\u0010\u001e\u001a\u00020\u001fH\u0016J\u0018\u0010 \u001a\u00020\u00132\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010!\u001a\u00020\"H\u0016R\"\u0010\u000e\u001a\u0016\u0012\f\u0012\n \u0011*\u0004\u0018\u00010\u00100\u0010\u0012\u0004\u0012\u00020\b0\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006#"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/CandidateResolver$TypeAliasSingleStepExpansionReportStrategy;", "Lorg/jetbrains/kotlin/types/TypeAliasExpansionReportStrategy;", "callElement", "Lorg/jetbrains/kotlin/psi/KtElement;", "typeAlias", "Lorg/jetbrains/kotlin/descriptors/TypeAliasDescriptor;", "ktTypeArguments", "", "Lorg/jetbrains/kotlin/psi/KtTypeProjection;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "upperBoundChecker", "Lorg/jetbrains/kotlin/resolve/UpperBoundChecker;", "(Lorg/jetbrains/kotlin/psi/KtElement;Lorg/jetbrains/kotlin/descriptors/TypeAliasDescriptor;Ljava/util/List;Lorg/jetbrains/kotlin/resolve/BindingTrace;Lorg/jetbrains/kotlin/resolve/UpperBoundChecker;)V", "argumentsMapping", "", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "boundsViolationInSubstitution", "", "substitutor", "Lorg/jetbrains/kotlin/types/TypeSubstitutor;", "unsubstitutedArgument", "Lorg/jetbrains/kotlin/types/KotlinType;", "argument", "typeParameter", "conflictingProjection", "substitutedArgument", "recursiveTypeAlias", "repeatedAnnotation", "annotation", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "wrongNumberOfTypeArguments", "numberOfParameters", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class TypeAliasSingleStepExpansionReportStrategy implements TypeAliasExpansionReportStrategy {
        private final Map<TypeParameterDescriptor, KtTypeProjection> argumentsMapping;
        private final KtElement callElement;
        private final BindingTrace trace;
        private final UpperBoundChecker upperBoundChecker;

        @Override // org.jetbrains.kotlin.types.TypeAliasExpansionReportStrategy
        public void conflictingProjection(TypeAliasDescriptor typeAlias, TypeParameterDescriptor typeParameter, KotlinType substitutedArgument) {
            Intrinsics.checkNotNullParameter(typeAlias, "typeAlias");
            Intrinsics.checkNotNullParameter(substitutedArgument, "substitutedArgument");
        }

        @Override // org.jetbrains.kotlin.types.TypeAliasExpansionReportStrategy
        public void recursiveTypeAlias(TypeAliasDescriptor typeAlias) {
            Intrinsics.checkNotNullParameter(typeAlias, "typeAlias");
        }

        @Override // org.jetbrains.kotlin.types.TypeAliasExpansionReportStrategy
        public void repeatedAnnotation(AnnotationDescriptor annotation) {
            Intrinsics.checkNotNullParameter(annotation, "annotation");
        }

        @Override // org.jetbrains.kotlin.types.TypeAliasExpansionReportStrategy
        public void wrongNumberOfTypeArguments(TypeAliasDescriptor typeAlias, int numberOfParameters) {
            Intrinsics.checkNotNullParameter(typeAlias, "typeAlias");
        }

        public TypeAliasSingleStepExpansionReportStrategy(KtElement callElement, TypeAliasDescriptor typeAlias, List<? extends KtTypeProjection> ktTypeArguments, BindingTrace trace, UpperBoundChecker upperBoundChecker) {
            Intrinsics.checkNotNullParameter(callElement, "callElement");
            Intrinsics.checkNotNullParameter(typeAlias, "typeAlias");
            Intrinsics.checkNotNullParameter(ktTypeArguments, "ktTypeArguments");
            Intrinsics.checkNotNullParameter(trace, "trace");
            Intrinsics.checkNotNullParameter(upperBoundChecker, "upperBoundChecker");
            this.callElement = callElement;
            this.trace = trace;
            this.upperBoundChecker = upperBoundChecker;
            KotlinTypeKt.isError(typeAlias.getExpandedType());
            List<TypeParameterDescriptor> declaredTypeParameters = typeAlias.getDeclaredTypeParameters();
            Intrinsics.checkNotNullExpressionValue(declaredTypeParameters, "getDeclaredTypeParameters(...)");
            this.argumentsMapping = MapsKt.toMap(CollectionsKt.zip(declaredTypeParameters, ktTypeArguments));
        }

        @Override // org.jetbrains.kotlin.types.TypeAliasExpansionReportStrategy
        public void boundsViolationInSubstitution(TypeSubstitutor substitutor, KotlinType unsubstitutedArgument, KotlinType argument, TypeParameterDescriptor typeParameter) {
            Intrinsics.checkNotNullParameter(substitutor, "substitutor");
            Intrinsics.checkNotNullParameter(unsubstitutedArgument, "unsubstitutedArgument");
            Intrinsics.checkNotNullParameter(argument, "argument");
            Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
            KtTypeProjection ktTypeProjection = this.argumentsMapping.get(unsubstitutedArgument.getConstructor().getThis$0());
            UpperBoundChecker.checkBounds$default(this.upperBoundChecker, ktTypeProjection != null ? ktTypeProjection.getTypeReference() : null, argument, typeParameter, substitutor, this.trace, this.callElement, null, 64, null);
        }
    }

    private final void checkGenericBoundsInTypeAliasConstructorCall(CallCandidateResolutionContext<?> callCandidateResolutionContext, List<? extends KtTypeProjection> list, TypeAliasConstructorDescriptor typeAliasConstructorDescriptor, TypeSubstitutor typeSubstitutor, BindingTrace bindingTrace) {
        KotlinType kotlinTypeSubstitute = typeSubstitutor.substitute(typeAliasConstructorDescriptor.getReturnType(), Variance.INVARIANT);
        Intrinsics.checkNotNull(kotlinTypeSubstitute);
        TypeSubstitutor typeSubstitutorCreate = TypeSubstitutor.create(kotlinTypeSubstitute);
        Intrinsics.checkNotNullExpressionValue(typeSubstitutorCreate, "create(...)");
        TypeAliasDescriptor containingModule = typeAliasConstructorDescriptor.getContainingModule();
        SimpleType expandedType = containingModule.getExpandedType();
        if (KotlinTypeKt.isError(expandedType)) {
            return;
        }
        KtElement callElement = callCandidateResolutionContext.call.getCallElement();
        Intrinsics.checkNotNullExpressionValue(callElement, "getCallElement(...)");
        checkTypeInTypeAliasSubstitutionRec(new TypeAliasSingleStepExpansionReportStrategy(callElement, containingModule, list, bindingTrace, this.upperBoundChecker), expandedType, typeSubstitutor, typeSubstitutorCreate);
    }

    private final void checkTypeInTypeAliasSubstitutionRec(TypeAliasExpansionReportStrategy reportStrategy, KotlinType unsubstitutedType, TypeSubstitutor typeAliasParametersSubstitutor, TypeSubstitutor boundsSubstitutor) {
        List<TypeParameterDescriptor> parameters = unsubstitutedType.getConstructor().getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
        List<TypeProjection> arguments = typeAliasParametersSubstitutor.safeSubstitute(unsubstitutedType, Variance.INVARIANT).getArguments();
        int iMin = Math.min(parameters.size(), arguments.size()) - 1;
        if (iMin < 0) {
            return;
        }
        int i = 0;
        while (true) {
            TypeProjection typeProjection = arguments.get(i);
            if (!typeProjection.isStarProjection()) {
                TypeParameterDescriptor typeParameterDescriptor = parameters.get(i);
                KotlinType nullableAnyType = typeProjection.getNullableAnyType();
                Intrinsics.checkNotNullExpressionValue(nullableAnyType, "getType(...)");
                KotlinType nullableAnyType2 = unsubstitutedType.getArguments().get(i).getNullableAnyType();
                Intrinsics.checkNotNullExpressionValue(nullableAnyType2, "getType(...)");
                Intrinsics.checkNotNull(typeParameterDescriptor);
                reportStrategy.boundsViolationInSubstitution(boundsSubstitutor, nullableAnyType2, nullableAnyType, typeParameterDescriptor);
                checkTypeInTypeAliasSubstitutionRec(reportStrategy, nullableAnyType2, typeAliasParametersSubstitutor, boundsSubstitutor);
            }
            if (i == iMin) {
                return;
            } else {
                i++;
            }
        }
    }

    private final <D extends CallableDescriptor> boolean shouldContinue(CallCandidateResolutionContext<D> callCandidateResolutionContext) {
        return callCandidateResolutionContext.candidateResolveMode == CandidateResolveMode.FULLY || callCandidateResolutionContext.candidateCall.getStatus().possibleTransformToSuccess();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final CallableDescriptor getCandidateDescriptor(CallCandidateResolutionContext<?> callCandidateResolutionContext) {
        CallableDescriptor candidateDescriptor = callCandidateResolutionContext.candidateCall.get$suspendPropertyDescriptor();
        Intrinsics.checkNotNullExpressionValue(candidateDescriptor, "getCandidateDescriptor(...)");
        return candidateDescriptor;
    }

    private final void checkValueArguments(final CallCandidateResolutionContext<?> callCandidateResolutionContext) {
        ResolutionStatus status;
        if (!shouldContinue(callCandidateResolutionContext)) {
            callCandidateResolutionContext.candidateCall.addRemainingTasks(new Function0<Unit>() { // from class: org.jetbrains.kotlin.resolve.calls.CandidateResolver$checkValueArguments$$inlined$checkAndReport$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    ResolutionStatus status2;
                    CallCandidateResolutionContext callCandidateResolutionContext2 = callCandidateResolutionContext;
                    MutableResolvedCall<D> mutableResolvedCall = callCandidateResolutionContext2.candidateCall;
                    if (callCandidateResolutionContext2.call.getTypeArguments().isEmpty() && !this.getCandidateDescriptor(callCandidateResolutionContext2).getTypeParameters().isEmpty() && callCandidateResolutionContext2.candidateCall.getKnownTypeParametersSubstitutor() == null) {
                        status2 = this.genericCandidateResolver.inferTypeArguments(callCandidateResolutionContext2);
                    } else {
                        status2 = this.checkAllValueArguments(callCandidateResolutionContext2, ResolveArgumentsMode.SHAPE_FUNCTION_ARGUMENTS).getStatus();
                    }
                    mutableResolvedCall.addStatus(status2);
                }
            });
            return;
        }
        MutableResolvedCall<D> mutableResolvedCall = callCandidateResolutionContext.candidateCall;
        if (callCandidateResolutionContext.call.getTypeArguments().isEmpty() && !getCandidateDescriptor(callCandidateResolutionContext).getTypeParameters().isEmpty() && callCandidateResolutionContext.candidateCall.getKnownTypeParametersSubstitutor() == null) {
            status = this.genericCandidateResolver.inferTypeArguments(callCandidateResolutionContext);
        } else {
            status = checkAllValueArguments(callCandidateResolutionContext, ResolveArgumentsMode.SHAPE_FUNCTION_ARGUMENTS).getStatus();
        }
        mutableResolvedCall.addStatus(status);
    }

    private final void processTypeArguments(final CallCandidateResolutionContext<?> callCandidateResolutionContext) {
        ErrorType errorTypeCreateErrorType;
        if (shouldContinue(callCandidateResolutionContext)) {
            List<KtTypeProjection> typeArguments = callCandidateResolutionContext.call.getTypeArguments();
            Intrinsics.checkNotNullExpressionValue(typeArguments, "getTypeArguments(...)");
            if (callCandidateResolutionContext.candidateCall.getKnownTypeParametersSubstitutor() != null) {
                MutableResolvedCall<D> mutableResolvedCall = callCandidateResolutionContext.candidateCall;
                TypeSubstitutor knownTypeParametersSubstitutor = callCandidateResolutionContext.candidateCall.getKnownTypeParametersSubstitutor();
                Intrinsics.checkNotNull(knownTypeParametersSubstitutor);
                mutableResolvedCall.setSubstitutor(knownTypeParametersSubstitutor);
                return;
            }
            if (typeArguments.isEmpty()) {
                return;
            }
            ArrayList arrayList = new ArrayList();
            Iterator<KtTypeProjection> it = typeArguments.iterator();
            while (it.hasNext()) {
                KtTypeReference typeReference = it.next().getTypeReference();
                if (typeReference == null || (errorTypeCreateErrorType = (KotlinType) callCandidateResolutionContext.trace.getContext().get(BindingContext.TYPE, typeReference)) == null) {
                    errorTypeCreateErrorType = ErrorUtils.createErrorType(ErrorTypeKind.STAR_PROJECTION_IN_CALL, new String[0]);
                }
                Intrinsics.checkNotNull(errorTypeCreateErrorType);
                arrayList.add(errorTypeCreateErrorType);
            }
            int size = getCandidateDescriptor(callCandidateResolutionContext).getTypeParameters().size();
            for (int size2 = typeArguments.size(); size2 < size; size2++) {
                ErrorTypeKind errorTypeKind = ErrorTypeKind.MISSED_TYPE_ARGUMENT_FOR_TYPE_PARAMETER;
                String string = getCandidateDescriptor(callCandidateResolutionContext).getTypeParameters().get(size2).getShortName().toString();
                Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                arrayList.add(ErrorUtils.createErrorType(errorTypeKind, string));
            }
            CallableDescriptor candidateDescriptor = getCandidateDescriptor(callCandidateResolutionContext);
            Intrinsics.checkNotNull(candidateDescriptor, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.FunctionDescriptor");
            ArrayList arrayList2 = arrayList;
            TypeSubstitution typeSubstitutionCreateSubstitution = FunctionDescriptorUtil.createSubstitution((FunctionDescriptor) candidateDescriptor, arrayList2);
            Intrinsics.checkNotNull(typeSubstitutionCreateSubstitution);
            TypeSubstitutor typeSubstitutorCreate = TypeSubstitutor.create(new SubstitutionFilteringInternalResolveAnnotations(typeSubstitutionCreateSubstitution));
            Intrinsics.checkNotNullExpressionValue(typeSubstitutorCreate, "create(...)");
            if (size == typeArguments.size()) {
                CallableDescriptor candidateDescriptor2 = getCandidateDescriptor(callCandidateResolutionContext);
                BindingTrace trace = callCandidateResolutionContext.trace;
                Intrinsics.checkNotNullExpressionValue(trace, "trace");
                checkGenericBoundsInAFunctionCall(callCandidateResolutionContext, typeArguments, arrayList2, candidateDescriptor2, typeSubstitutorCreate, trace);
            } else {
                callCandidateResolutionContext.candidateCall.addStatus(ResolutionStatus.WRONG_NUMBER_OF_TYPE_ARGUMENTS_ERROR);
                callCandidateResolutionContext.tracing.wrongNumberOfTypeArguments(callCandidateResolutionContext.trace, size, getCandidateDescriptor(callCandidateResolutionContext));
            }
            callCandidateResolutionContext.candidateCall.setSubstitutor(typeSubstitutorCreate);
            return;
        }
        callCandidateResolutionContext.candidateCall.addRemainingTasks(new Function0<Unit>() { // from class: org.jetbrains.kotlin.resolve.calls.CandidateResolver$processTypeArguments$$inlined$check$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                ErrorType errorTypeCreateErrorType2;
                CallCandidateResolutionContext callCandidateResolutionContext2 = callCandidateResolutionContext;
                List<KtTypeProjection> typeArguments2 = callCandidateResolutionContext2.call.getTypeArguments();
                Intrinsics.checkNotNullExpressionValue(typeArguments2, "getTypeArguments(...)");
                if (callCandidateResolutionContext2.candidateCall.getKnownTypeParametersSubstitutor() != null) {
                    MutableResolvedCall<D> mutableResolvedCall2 = callCandidateResolutionContext2.candidateCall;
                    TypeSubstitutor knownTypeParametersSubstitutor2 = callCandidateResolutionContext2.candidateCall.getKnownTypeParametersSubstitutor();
                    Intrinsics.checkNotNull(knownTypeParametersSubstitutor2);
                    mutableResolvedCall2.setSubstitutor(knownTypeParametersSubstitutor2);
                    return;
                }
                if (typeArguments2.isEmpty()) {
                    return;
                }
                ArrayList arrayList3 = new ArrayList();
                Iterator<KtTypeProjection> it2 = typeArguments2.iterator();
                while (it2.hasNext()) {
                    KtTypeReference typeReference2 = it2.next().getTypeReference();
                    if (typeReference2 == null || (errorTypeCreateErrorType2 = (KotlinType) callCandidateResolutionContext2.trace.getContext().get(BindingContext.TYPE, typeReference2)) == null) {
                        errorTypeCreateErrorType2 = ErrorUtils.createErrorType(ErrorTypeKind.STAR_PROJECTION_IN_CALL, new String[0]);
                    }
                    Intrinsics.checkNotNull(errorTypeCreateErrorType2);
                    arrayList3.add(errorTypeCreateErrorType2);
                }
                int size3 = this.getCandidateDescriptor(callCandidateResolutionContext2).getTypeParameters().size();
                for (int size4 = typeArguments2.size(); size4 < size3; size4++) {
                    ErrorTypeKind errorTypeKind2 = ErrorTypeKind.MISSED_TYPE_ARGUMENT_FOR_TYPE_PARAMETER;
                    String string2 = this.getCandidateDescriptor(callCandidateResolutionContext2).getTypeParameters().get(size4).getShortName().toString();
                    Intrinsics.checkNotNullExpressionValue(string2, "toString(...)");
                    arrayList3.add(ErrorUtils.createErrorType(errorTypeKind2, string2));
                }
                CallableDescriptor candidateDescriptor3 = this.getCandidateDescriptor(callCandidateResolutionContext2);
                Intrinsics.checkNotNull(candidateDescriptor3, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.FunctionDescriptor");
                ArrayList arrayList4 = arrayList3;
                TypeSubstitution typeSubstitutionCreateSubstitution2 = FunctionDescriptorUtil.createSubstitution((FunctionDescriptor) candidateDescriptor3, arrayList4);
                Intrinsics.checkNotNull(typeSubstitutionCreateSubstitution2);
                TypeSubstitutor typeSubstitutorCreate2 = TypeSubstitutor.create(new SubstitutionFilteringInternalResolveAnnotations(typeSubstitutionCreateSubstitution2));
                Intrinsics.checkNotNullExpressionValue(typeSubstitutorCreate2, "create(...)");
                if (size3 != typeArguments2.size()) {
                    callCandidateResolutionContext2.candidateCall.addStatus(ResolutionStatus.WRONG_NUMBER_OF_TYPE_ARGUMENTS_ERROR);
                    callCandidateResolutionContext2.tracing.wrongNumberOfTypeArguments(callCandidateResolutionContext2.trace, size3, this.getCandidateDescriptor(callCandidateResolutionContext2));
                } else {
                    CandidateResolver candidateResolver = this;
                    CallableDescriptor candidateDescriptor4 = candidateResolver.getCandidateDescriptor(callCandidateResolutionContext2);
                    BindingTrace trace2 = callCandidateResolutionContext2.trace;
                    Intrinsics.checkNotNullExpressionValue(trace2, "trace");
                    candidateResolver.checkGenericBoundsInAFunctionCall(callCandidateResolutionContext2, typeArguments2, arrayList4, candidateDescriptor4, typeSubstitutorCreate2, trace2);
                }
                callCandidateResolutionContext2.candidateCall.setSubstitutor(typeSubstitutorCreate2);
            }
        });
    }

    private final <D extends CallableDescriptor> void mapArguments(final CallCandidateResolutionContext<D> callCandidateResolutionContext) {
        if (shouldContinue(callCandidateResolutionContext)) {
            if (ValueArgumentsToParametersMapper.mapValueArgumentsToParameters(callCandidateResolutionContext.call, callCandidateResolutionContext.tracing, callCandidateResolutionContext.candidateCall, callCandidateResolutionContext.languageVersionSettings).isSuccess()) {
                return;
            }
            callCandidateResolutionContext.candidateCall.addStatus(ResolutionStatus.ARGUMENTS_MAPPING_ERROR);
            return;
        }
        callCandidateResolutionContext.candidateCall.addRemainingTasks(new Function0<Unit>() { // from class: org.jetbrains.kotlin.resolve.calls.CandidateResolver$mapArguments$$inlined$check$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                CallCandidateResolutionContext callCandidateResolutionContext2 = callCandidateResolutionContext;
                if (ValueArgumentsToParametersMapper.mapValueArgumentsToParameters(callCandidateResolutionContext2.call, callCandidateResolutionContext2.tracing, callCandidateResolutionContext2.candidateCall, callCandidateResolutionContext2.languageVersionSettings).isSuccess()) {
                    return;
                }
                callCandidateResolutionContext2.candidateCall.addStatus(ResolutionStatus.ARGUMENTS_MAPPING_ERROR);
            }
        });
    }

    private final <D extends CallableDescriptor> void checkExpectedCallableType(final CallCandidateResolutionContext<D> callCandidateResolutionContext) {
        KtExpression receiverExpression;
        if (shouldContinue(callCandidateResolutionContext)) {
            if (TypeUtils.noExpectedType(callCandidateResolutionContext.expectedType)) {
                return;
            }
            DoubleColonExpressionResolver.Companion companion = DoubleColonExpressionResolver.INSTANCE;
            D candidateDescriptor = callCandidateResolutionContext.candidateCall.get$suspendPropertyDescriptor();
            Intrinsics.checkNotNullExpressionValue(candidateDescriptor, "getCandidateDescriptor(...)");
            PsiElement parent = callCandidateResolutionContext.call.getCallElement().getLightParent();
            DoubleColonLHS doubleColonLHS = null;
            KtCallableReferenceExpression ktCallableReferenceExpression = parent instanceof KtCallableReferenceExpression ? (KtCallableReferenceExpression) parent : null;
            if (ktCallableReferenceExpression != null && (receiverExpression = ktCallableReferenceExpression.getReceiverExpression()) != null) {
                doubleColonLHS = (DoubleColonLHS) callCandidateResolutionContext.trace.getContext().get(BindingContext.DOUBLE_COLON_LHS, receiverExpression);
            }
            KotlinType kotlinTypeCreateKCallableTypeForReference = companion.createKCallableTypeForReference(candidateDescriptor, doubleColonLHS, this.reflectionTypes, callCandidateResolutionContext.scope.getOwnerDescriptor());
            if (kotlinTypeCreateKCallableTypeForReference != null) {
                KotlinType expectedType = callCandidateResolutionContext.expectedType;
                Intrinsics.checkNotNullExpressionValue(expectedType, "expectedType");
                List<TypeParameterDescriptor> typeParameters = callCandidateResolutionContext.candidateCall.get$suspendPropertyDescriptor().getTypeParameters();
                Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
                if (canBeSubtype(kotlinTypeCreateKCallableTypeForReference, expectedType, typeParameters)) {
                    return;
                }
            }
            callCandidateResolutionContext.candidateCall.addStatus(ResolutionStatus.OTHER_ERROR);
            return;
        }
        callCandidateResolutionContext.candidateCall.addRemainingTasks(new Function0<Unit>() { // from class: org.jetbrains.kotlin.resolve.calls.CandidateResolver$checkExpectedCallableType$$inlined$check$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                KtExpression receiverExpression2;
                CallCandidateResolutionContext callCandidateResolutionContext2 = callCandidateResolutionContext;
                if (TypeUtils.noExpectedType(callCandidateResolutionContext2.expectedType)) {
                    return;
                }
                DoubleColonExpressionResolver.Companion companion2 = DoubleColonExpressionResolver.INSTANCE;
                CallableDescriptor candidateDescriptor2 = callCandidateResolutionContext2.candidateCall.get$suspendPropertyDescriptor();
                Intrinsics.checkNotNullExpressionValue(candidateDescriptor2, "getCandidateDescriptor(...)");
                PsiElement parent2 = callCandidateResolutionContext2.call.getCallElement().getLightParent();
                DoubleColonLHS doubleColonLHS2 = null;
                KtCallableReferenceExpression ktCallableReferenceExpression2 = parent2 instanceof KtCallableReferenceExpression ? (KtCallableReferenceExpression) parent2 : null;
                if (ktCallableReferenceExpression2 != null && (receiverExpression2 = ktCallableReferenceExpression2.getReceiverExpression()) != null) {
                    doubleColonLHS2 = (DoubleColonLHS) callCandidateResolutionContext2.trace.getContext().get(BindingContext.DOUBLE_COLON_LHS, receiverExpression2);
                }
                KotlinType kotlinTypeCreateKCallableTypeForReference2 = companion2.createKCallableTypeForReference(candidateDescriptor2, doubleColonLHS2, this.reflectionTypes, callCandidateResolutionContext2.scope.getOwnerDescriptor());
                if (kotlinTypeCreateKCallableTypeForReference2 != null) {
                    CandidateResolver candidateResolver = this;
                    KotlinType expectedType2 = callCandidateResolutionContext2.expectedType;
                    Intrinsics.checkNotNullExpressionValue(expectedType2, "expectedType");
                    List<TypeParameterDescriptor> typeParameters2 = callCandidateResolutionContext2.candidateCall.get$suspendPropertyDescriptor().getTypeParameters();
                    Intrinsics.checkNotNullExpressionValue(typeParameters2, "getTypeParameters(...)");
                    if (candidateResolver.canBeSubtype(kotlinTypeCreateKCallableTypeForReference2, expectedType2, typeParameters2)) {
                        return;
                    }
                }
                callCandidateResolutionContext2.candidateCall.addStatus(ResolutionStatus.OTHER_ERROR);
            }
        });
    }

    private final void checkVisibilityWithoutReceiver(final CallCandidateResolutionContext<?> callCandidateResolutionContext) {
        if (!shouldContinue(callCandidateResolutionContext)) {
            callCandidateResolutionContext.candidateCall.addRemainingTasks(new Function0<Unit>() { // from class: org.jetbrains.kotlin.resolve.calls.CandidateResolver$checkVisibilityWithoutReceiver$$inlined$checkAndReport$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    CallCandidateResolutionContext callCandidateResolutionContext2 = callCandidateResolutionContext;
                    callCandidateResolutionContext2.candidateCall.addStatus(this.checkVisibilityWithDispatchReceiver(callCandidateResolutionContext2, DescriptorVisibilities.ALWAYS_SUITABLE_RECEIVER, null));
                }
            });
        } else {
            callCandidateResolutionContext.candidateCall.addStatus(checkVisibilityWithDispatchReceiver(callCandidateResolutionContext, DescriptorVisibilities.ALWAYS_SUITABLE_RECEIVER, null));
        }
    }

    private final void checkExtensionReceiver(final CallCandidateResolutionContext<?> callCandidateResolutionContext) {
        ResolutionStatus resolutionStatus;
        if (!shouldContinue(callCandidateResolutionContext)) {
            callCandidateResolutionContext.candidateCall.addRemainingTasks(new Function0<Unit>() { // from class: org.jetbrains.kotlin.resolve.calls.CandidateResolver$checkExtensionReceiver$$inlined$checkAndReport$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    ResolutionStatus resolutionStatus2;
                    CallCandidateResolutionContext callCandidateResolutionContext2 = callCandidateResolutionContext;
                    MutableResolvedCall<D> mutableResolvedCall = callCandidateResolutionContext2.candidateCall;
                    ReceiverParameterDescriptor extensionReceiverParameter = callCandidateResolutionContext2.candidateCall.get$suspendPropertyDescriptor().getExtensionReceiverParameter();
                    ReceiverValue extensionReceiver = callCandidateResolutionContext2.candidateCall.getExtensionReceiver();
                    if (extensionReceiverParameter != null && extensionReceiver == null) {
                        callCandidateResolutionContext2.tracing.missingReceiver(callCandidateResolutionContext2.candidateCall.getTrace(), extensionReceiverParameter);
                        resolutionStatus2 = ResolutionStatus.OTHER_ERROR;
                    } else if (extensionReceiverParameter == null && extensionReceiver != null) {
                        callCandidateResolutionContext2.tracing.noReceiverAllowed(callCandidateResolutionContext2.candidateCall.getTrace());
                        if (callCandidateResolutionContext2.call.getCalleeExpression() instanceof KtSimpleNameExpression) {
                            resolutionStatus2 = ResolutionStatus.RECEIVER_PRESENCE_ERROR;
                        } else {
                            resolutionStatus2 = ResolutionStatus.OTHER_ERROR;
                        }
                    } else {
                        resolutionStatus2 = ResolutionStatus.SUCCESS;
                    }
                    mutableResolvedCall.addStatus(resolutionStatus2);
                }
            });
            return;
        }
        MutableResolvedCall<D> mutableResolvedCall = callCandidateResolutionContext.candidateCall;
        ReceiverParameterDescriptor extensionReceiverParameter = callCandidateResolutionContext.candidateCall.get$suspendPropertyDescriptor().getExtensionReceiverParameter();
        ReceiverValue extensionReceiver = callCandidateResolutionContext.candidateCall.getExtensionReceiver();
        if (extensionReceiverParameter != null && extensionReceiver == null) {
            callCandidateResolutionContext.tracing.missingReceiver(callCandidateResolutionContext.candidateCall.getTrace(), extensionReceiverParameter);
            resolutionStatus = ResolutionStatus.OTHER_ERROR;
        } else if (extensionReceiverParameter == null && extensionReceiver != null) {
            callCandidateResolutionContext.tracing.noReceiverAllowed(callCandidateResolutionContext.candidateCall.getTrace());
            if (callCandidateResolutionContext.call.getCalleeExpression() instanceof KtSimpleNameExpression) {
                resolutionStatus = ResolutionStatus.RECEIVER_PRESENCE_ERROR;
            } else {
                resolutionStatus = ResolutionStatus.OTHER_ERROR;
            }
        } else {
            resolutionStatus = ResolutionStatus.SUCCESS;
        }
        mutableResolvedCall.addStatus(resolutionStatus);
    }

    /* JADX WARN: Code duplicated, block: B:11:0x002c  */
    /* JADX WARN: Code duplicated, block: B:13:0x0030  */
    /* JADX WARN: Code duplicated, block: B:14:0x0037  */
    /* JADX WARN: Code duplicated, block: B:17:0x004a  */
    private final void checkDispatchReceiver(final CallCandidateResolutionContext<?> callCandidateResolutionContext) {
        ResolutionStatus resolutionStatus;
        ClassDescriptor referencedObject;
        if (!shouldContinue(callCandidateResolutionContext)) {
            callCandidateResolutionContext.candidateCall.addRemainingTasks(new Function0<Unit>() { // from class: org.jetbrains.kotlin.resolve.calls.CandidateResolver$checkDispatchReceiver$$inlined$checkAndReport$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* JADX WARN: Code duplicated, block: B:11:0x002e  */
                /* JADX WARN: Code duplicated, block: B:12:0x0035  */
                /* JADX WARN: Code duplicated, block: B:15:0x0048  */
                /* JADX WARN: Code duplicated, block: B:9:0x002a  */
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    ResolutionStatus resolutionStatus2;
                    ClassDescriptor referencedObject2;
                    CallCandidateResolutionContext callCandidateResolutionContext2 = callCandidateResolutionContext;
                    MutableResolvedCall<D> mutableResolvedCall = callCandidateResolutionContext2.candidateCall;
                    CallableDescriptor candidateDescriptor = this.getCandidateDescriptor(callCandidateResolutionContext2);
                    if (callCandidateResolutionContext2.candidateCall.getDispatchReceiver() != null) {
                        if (candidateDescriptor instanceof ClassConstructorDescriptor) {
                            ClassConstructorDescriptor classConstructorDescriptor = (ClassConstructorDescriptor) candidateDescriptor;
                            if (DescriptorUtils.isStaticNestedClass(classConstructorDescriptor.getContainingModule())) {
                                referencedObject2 = classConstructorDescriptor.getContainingModule();
                            } else if (candidateDescriptor instanceof FakeCallableDescriptorForObject) {
                                referencedObject2 = ((FakeCallableDescriptorForObject) candidateDescriptor).getReferencedObject();
                            } else {
                                referencedObject2 = null;
                            }
                        } else if (candidateDescriptor instanceof FakeCallableDescriptorForObject) {
                            referencedObject2 = ((FakeCallableDescriptorForObject) candidateDescriptor).getReferencedObject();
                        } else {
                            referencedObject2 = null;
                        }
                        if (referencedObject2 != null) {
                            callCandidateResolutionContext2.tracing.nestedClassAccessViaInstanceReference(callCandidateResolutionContext2.trace, referencedObject2, callCandidateResolutionContext2.candidateCall.getExplicitReceiverKind());
                            resolutionStatus2 = ResolutionStatus.OTHER_ERROR;
                        } else {
                            callCandidateResolutionContext2.candidateCall.getResultingDescriptor().mo6753getDispatchReceiverParameter();
                            resolutionStatus2 = ResolutionStatus.SUCCESS;
                        }
                    } else {
                        callCandidateResolutionContext2.candidateCall.getResultingDescriptor().mo6753getDispatchReceiverParameter();
                        resolutionStatus2 = ResolutionStatus.SUCCESS;
                    }
                    mutableResolvedCall.addStatus(resolutionStatus2);
                }
            });
            return;
        }
        MutableResolvedCall<D> mutableResolvedCall = callCandidateResolutionContext.candidateCall;
        CallableDescriptor candidateDescriptor = getCandidateDescriptor(callCandidateResolutionContext);
        if (callCandidateResolutionContext.candidateCall.getDispatchReceiver() != null) {
            if (candidateDescriptor instanceof ClassConstructorDescriptor) {
                ClassConstructorDescriptor classConstructorDescriptor = (ClassConstructorDescriptor) candidateDescriptor;
                if (DescriptorUtils.isStaticNestedClass(classConstructorDescriptor.getContainingModule())) {
                    referencedObject = classConstructorDescriptor.getContainingModule();
                } else if (candidateDescriptor instanceof FakeCallableDescriptorForObject) {
                    referencedObject = ((FakeCallableDescriptorForObject) candidateDescriptor).getReferencedObject();
                } else {
                    referencedObject = null;
                }
            } else if (candidateDescriptor instanceof FakeCallableDescriptorForObject) {
                referencedObject = ((FakeCallableDescriptorForObject) candidateDescriptor).getReferencedObject();
            } else {
                referencedObject = null;
            }
            if (referencedObject != null) {
                callCandidateResolutionContext.tracing.nestedClassAccessViaInstanceReference(callCandidateResolutionContext.trace, referencedObject, callCandidateResolutionContext.candidateCall.getExplicitReceiverKind());
                resolutionStatus = ResolutionStatus.OTHER_ERROR;
            } else {
                callCandidateResolutionContext.candidateCall.getResultingDescriptor().mo6753getDispatchReceiverParameter();
                resolutionStatus = ResolutionStatus.SUCCESS;
            }
        } else {
            callCandidateResolutionContext.candidateCall.getResultingDescriptor().mo6753getDispatchReceiverParameter();
            resolutionStatus = ResolutionStatus.SUCCESS;
        }
        mutableResolvedCall.addStatus(resolutionStatus);
    }

    private final void checkAbstractAndSuper(final CallCandidateResolutionContext<?> callCandidateResolutionContext) {
        if (shouldContinue(callCandidateResolutionContext)) {
            CallableDescriptor candidateDescriptor = getCandidateDescriptor(callCandidateResolutionContext);
            if ((callCandidateResolutionContext.candidateCall.getCall().getCalleeExpression() instanceof KtSimpleNameExpression) && (candidateDescriptor instanceof ConstructorDescriptor)) {
                Modality modality = ((ConstructorDescriptor) candidateDescriptor).getConstructedClass().getModality();
                Intrinsics.checkNotNullExpressionValue(modality, "getModality(...)");
                if (modality == Modality.ABSTRACT) {
                    callCandidateResolutionContext.tracing.instantiationOfAbstractClass(callCandidateResolutionContext.trace);
                }
            }
            if (getReceiverSuper(callCandidateResolutionContext.candidateCall.getDispatchReceiver()) != null && (candidateDescriptor instanceof MemberDescriptor) && ((MemberDescriptor) candidateDescriptor).getModality() == Modality.ABSTRACT) {
                callCandidateResolutionContext.tracing.abstractSuperCall(callCandidateResolutionContext.trace);
                callCandidateResolutionContext.candidateCall.addStatus(ResolutionStatus.OTHER_ERROR);
            }
            KtSuperExpression receiverSuper = getReceiverSuper(callCandidateResolutionContext.candidateCall.getExtensionReceiver());
            if (receiverSuper != null) {
                callCandidateResolutionContext.trace.report(Errors.SUPER_CANT_BE_EXTENSION_RECEIVER.on(receiverSuper, receiverSuper.getText()));
                callCandidateResolutionContext.candidateCall.addStatus(ResolutionStatus.OTHER_ERROR);
                return;
            }
            return;
        }
        callCandidateResolutionContext.candidateCall.addRemainingTasks(new Function0<Unit>() { // from class: org.jetbrains.kotlin.resolve.calls.CandidateResolver$checkAbstractAndSuper$$inlined$check$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                CallCandidateResolutionContext callCandidateResolutionContext2 = callCandidateResolutionContext;
                CallableDescriptor candidateDescriptor2 = this.getCandidateDescriptor(callCandidateResolutionContext2);
                if ((callCandidateResolutionContext2.candidateCall.getCall().getCalleeExpression() instanceof KtSimpleNameExpression) && (candidateDescriptor2 instanceof ConstructorDescriptor)) {
                    Modality modality2 = ((ConstructorDescriptor) candidateDescriptor2).getConstructedClass().getModality();
                    Intrinsics.checkNotNullExpressionValue(modality2, "getModality(...)");
                    if (modality2 == Modality.ABSTRACT) {
                        callCandidateResolutionContext2.tracing.instantiationOfAbstractClass(callCandidateResolutionContext2.trace);
                    }
                }
                if (this.getReceiverSuper(callCandidateResolutionContext2.candidateCall.getDispatchReceiver()) != null && (candidateDescriptor2 instanceof MemberDescriptor) && ((MemberDescriptor) candidateDescriptor2).getModality() == Modality.ABSTRACT) {
                    callCandidateResolutionContext2.tracing.abstractSuperCall(callCandidateResolutionContext2.trace);
                    callCandidateResolutionContext2.candidateCall.addStatus(ResolutionStatus.OTHER_ERROR);
                }
                KtSuperExpression receiverSuper2 = this.getReceiverSuper(callCandidateResolutionContext2.candidateCall.getExtensionReceiver());
                if (receiverSuper2 != null) {
                    callCandidateResolutionContext2.trace.report(Errors.SUPER_CANT_BE_EXTENSION_RECEIVER.on(receiverSuper2, receiverSuper2.getText()));
                    callCandidateResolutionContext2.candidateCall.addStatus(ResolutionStatus.OTHER_ERROR);
                }
            }
        });
    }

    private final void checkConstructedExpandedType(final CallCandidateResolutionContext<?> callCandidateResolutionContext) {
        if (shouldContinue(callCandidateResolutionContext)) {
            CallableDescriptor candidateDescriptor = getCandidateDescriptor(callCandidateResolutionContext);
            if (candidateDescriptor instanceof TypeAliasConstructorDescriptor) {
                TypeAliasConstructorDescriptor typeAliasConstructorDescriptor = (TypeAliasConstructorDescriptor) candidateDescriptor;
                if (TypeUtilsKt.containsTypeProjectionsInTopLevelArguments(typeAliasConstructorDescriptor.getReturnType())) {
                    callCandidateResolutionContext.trace.report(Errors.EXPANDED_TYPE_CANNOT_BE_CONSTRUCTED.on(callCandidateResolutionContext.call.getCallElement(), typeAliasConstructorDescriptor.getReturnType()));
                    callCandidateResolutionContext.candidateCall.addStatus(ResolutionStatus.OTHER_ERROR);
                    return;
                }
                return;
            }
            return;
        }
        callCandidateResolutionContext.candidateCall.addRemainingTasks(new Function0<Unit>() { // from class: org.jetbrains.kotlin.resolve.calls.CandidateResolver$checkConstructedExpandedType$$inlined$check$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                CallCandidateResolutionContext callCandidateResolutionContext2 = callCandidateResolutionContext;
                CallableDescriptor candidateDescriptor2 = this.getCandidateDescriptor(callCandidateResolutionContext2);
                if (candidateDescriptor2 instanceof TypeAliasConstructorDescriptor) {
                    TypeAliasConstructorDescriptor typeAliasConstructorDescriptor2 = (TypeAliasConstructorDescriptor) candidateDescriptor2;
                    if (TypeUtilsKt.containsTypeProjectionsInTopLevelArguments(typeAliasConstructorDescriptor2.getReturnType())) {
                        callCandidateResolutionContext2.trace.report(Errors.EXPANDED_TYPE_CANNOT_BE_CONSTRUCTED.on(callCandidateResolutionContext2.call.getCallElement(), typeAliasConstructorDescriptor2.getReturnType()));
                        callCandidateResolutionContext2.candidateCall.addStatus(ResolutionStatus.OTHER_ERROR);
                    }
                }
            }
        });
    }

    private final void checkReceiverTypeError(final CallCandidateResolutionContext<?> callCandidateResolutionContext) {
        if (shouldContinue(callCandidateResolutionContext)) {
            ReceiverParameterDescriptor extensionReceiverParameter = getCandidateDescriptor(callCandidateResolutionContext).getExtensionReceiverParameter();
            ReceiverParameterDescriptor receiverParameterDescriptorMo6753getDispatchReceiverParameter = getCandidateDescriptor(callCandidateResolutionContext).mo6753getDispatchReceiverParameter();
            Call call = callCandidateResolutionContext.call;
            Intrinsics.checkNotNullExpressionValue(call, "call");
            if (!CallResolverUtilKt.isInvokeCallOnExpressionWithBothReceivers(call)) {
                checkReceiverTypeError(callCandidateResolutionContext, extensionReceiverParameter, callCandidateResolutionContext.candidateCall.getExtensionReceiver());
            }
            checkReceiverTypeError(callCandidateResolutionContext, receiverParameterDescriptorMo6753getDispatchReceiverParameter, callCandidateResolutionContext.candidateCall.getDispatchReceiver());
            return;
        }
        callCandidateResolutionContext.candidateCall.addRemainingTasks(new Function0<Unit>() { // from class: org.jetbrains.kotlin.resolve.calls.CandidateResolver$checkReceiverTypeError$$inlined$check$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                CallCandidateResolutionContext callCandidateResolutionContext2 = callCandidateResolutionContext;
                ReceiverParameterDescriptor extensionReceiverParameter2 = this.getCandidateDescriptor(callCandidateResolutionContext2).getExtensionReceiverParameter();
                ReceiverParameterDescriptor receiverParameterDescriptorMo6753getDispatchReceiverParameter2 = this.getCandidateDescriptor(callCandidateResolutionContext2).mo6753getDispatchReceiverParameter();
                Call call2 = callCandidateResolutionContext2.call;
                Intrinsics.checkNotNullExpressionValue(call2, "call");
                if (!CallResolverUtilKt.isInvokeCallOnExpressionWithBothReceivers(call2)) {
                    this.checkReceiverTypeError(callCandidateResolutionContext2, extensionReceiverParameter2, callCandidateResolutionContext2.candidateCall.getExtensionReceiver());
                }
                this.checkReceiverTypeError(callCandidateResolutionContext2, receiverParameterDescriptorMo6753getDispatchReceiverParameter2, callCandidateResolutionContext2.candidateCall.getDispatchReceiver());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void checkReceiverTypeError(final CallCandidateResolutionContext<?> callCandidateResolutionContext, final ReceiverParameterDescriptor receiverParameterDescriptor, final ReceiverValue receiverValue) {
        ResolutionStatus resolutionStatus;
        if (!shouldContinue(callCandidateResolutionContext)) {
            callCandidateResolutionContext.candidateCall.addRemainingTasks(new Function0<Unit>() { // from class: org.jetbrains.kotlin.resolve.calls.CandidateResolver$checkReceiverTypeError$$inlined$checkAndReport$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    ResolutionStatus resolutionStatus2;
                    CallCandidateResolutionContext callCandidateResolutionContext2 = callCandidateResolutionContext;
                    MutableResolvedCall<D> mutableResolvedCall = callCandidateResolutionContext2.candidateCall;
                    ReceiverParameterDescriptor receiverParameterDescriptor2 = receiverParameterDescriptor;
                    if (receiverParameterDescriptor2 != null && receiverValue != null) {
                        if (this.smartCastManager.getSmartCastReceiverResult(receiverValue, CallResolverUtilKt.getErasedReceiverType(receiverParameterDescriptor2, this.getCandidateDescriptor(callCandidateResolutionContext2)), callCandidateResolutionContext2) == null) {
                            resolutionStatus2 = ResolutionStatus.RECEIVER_TYPE_ERROR;
                        } else {
                            resolutionStatus2 = ResolutionStatus.SUCCESS;
                        }
                    } else {
                        resolutionStatus2 = ResolutionStatus.SUCCESS;
                    }
                    mutableResolvedCall.addStatus(resolutionStatus2);
                }
            });
            return;
        }
        MutableResolvedCall<D> mutableResolvedCall = callCandidateResolutionContext.candidateCall;
        if (receiverParameterDescriptor != null && receiverValue != null) {
            if (this.smartCastManager.getSmartCastReceiverResult(receiverValue, CallResolverUtilKt.getErasedReceiverType(receiverParameterDescriptor, getCandidateDescriptor(callCandidateResolutionContext)), callCandidateResolutionContext) == null) {
                resolutionStatus = ResolutionStatus.RECEIVER_TYPE_ERROR;
            } else {
                resolutionStatus = ResolutionStatus.SUCCESS;
            }
        } else {
            resolutionStatus = ResolutionStatus.SUCCESS;
        }
        mutableResolvedCall.addStatus(resolutionStatus);
    }
}
