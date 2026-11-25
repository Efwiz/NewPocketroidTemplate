package org.jetbrains.kotlin.resolve.calls.tower;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.koushikdutta.async.http.cache.ResponseCacheMiddleware;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.extensions.internal.CandidateInterceptor;
import org.jetbrains.kotlin.incremental.components.LookupLocation;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtReferenceExpression;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.DelegatingBindingTrace;
import org.jetbrains.kotlin.resolve.TemporaryBindingTrace;
import org.jetbrains.kotlin.resolve.calls.CallResolver;
import org.jetbrains.kotlin.resolve.calls.CallTransformer;
import org.jetbrains.kotlin.resolve.calls.CandidateResolver;
import org.jetbrains.kotlin.resolve.calls.context.BasicCallResolutionContext;
import org.jetbrains.kotlin.resolve.calls.context.CallCandidateResolutionContext;
import org.jetbrains.kotlin.resolve.calls.context.CandidateResolveMode;
import org.jetbrains.kotlin.resolve.calls.context.CheckArgumentTypesMode;
import org.jetbrains.kotlin.resolve.calls.context.ContextDependency;
import org.jetbrains.kotlin.resolve.calls.inference.BuilderInferenceSupport;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallDiagnostic;
import org.jetbrains.kotlin.resolve.calls.model.MutableResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCallImpl;
import org.jetbrains.kotlin.resolve.calls.model.VariableAsFunctionResolvedCallImpl;
import org.jetbrains.kotlin.resolve.calls.results.OverloadResolutionResultsImpl;
import org.jetbrains.kotlin.resolve.calls.results.ResolutionResultsHandler;
import org.jetbrains.kotlin.resolve.calls.results.ResolutionStatus;
import org.jetbrains.kotlin.resolve.calls.tasks.DynamicCallableDescriptors;
import org.jetbrains.kotlin.resolve.calls.tasks.ExplicitReceiverKind;
import org.jetbrains.kotlin.resolve.calls.tasks.OldResolutionCandidate;
import org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy;
import org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategyForInvoke;
import org.jetbrains.kotlin.resolve.calls.util.CallResolverUtilKt;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.deprecation.DeprecationResolver;
import org.jetbrains.kotlin.resolve.descriptorUtil.AnnotationsForResolveUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl;
import org.jetbrains.kotlin.resolve.scopes.ResolutionScope;
import org.jetbrains.kotlin.resolve.scopes.SyntheticScopes;
import org.jetbrains.kotlin.resolve.scopes.receivers.DetailedReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ExpressionReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.QualifierReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.Receiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValueWithSmartCastInfo;
import org.jetbrains.kotlin.types.DeferredType;
import org.jetbrains.kotlin.types.DynamicTypesKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeApproximator;
import org.jetbrains.kotlin.types.error.ErrorUtils;
import org.jetbrains.kotlin.types.expressions.OperatorConventions;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: NewResolutionOldInference.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000¤\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001:\u0005789:;Be\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u0012\u0006\u0010\u0012\u001a\u00020\u0013\u0012\u0006\u0010\u0014\u001a\u00020\u0015\u0012\u0006\u0010\u0016\u001a\u00020\u0017\u0012\u0006\u0010\u0018\u001a\u00020\u0019¢\u0006\u0002\u0010\u001aJD\u0010\u001b\u001a&\u0012\f\u0012\n \u001e*\u0004\u0018\u0001H\u001dH\u001d \u001e*\u0012\u0012\f\u0012\n \u001e*\u0004\u0018\u0001H\u001dH\u001d\u0018\u00010\u001c0\u001c\"\b\b\u0000\u0010\u001d*\u00020\u001f2\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\"0!H\u0002J6\u0010#\u001a\b\u0012\u0004\u0012\u0002H\u001d0\u001c\"\b\b\u0000\u0010\u001d*\u00020\u001f2\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\"0!2\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020(H\u0002J2\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020(2\u0006\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020/2\u0006\u00100\u001a\u0002012\b\u00102\u001a\u0004\u0018\u000103H\u0002J6\u00104\u001a\b\u0012\u0004\u0012\u0002H\u001d0\u001c\"\b\b\u0000\u0010\u001d*\u00020\u001f2\u0006\u0010+\u001a\u00020(2\u0006\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020/2\u0006\u0010%\u001a\u00020&J:\u00105\u001a\b\u0012\u0004\u0012\u0002H\u001d0\u001c\"\b\b\u0000\u0010\u001d*\u00020\u001f2\u0006\u0010'\u001a\u00020(2\u0006\u0010%\u001a\u00020&2\u0012\u0010$\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u001d060!R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006<"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/NewResolutionOldInference;", "", "candidateResolver", "Lorg/jetbrains/kotlin/resolve/calls/CandidateResolver;", "towerResolver", "Lorg/jetbrains/kotlin/resolve/calls/tower/TowerResolver;", "resolutionResultsHandler", "Lorg/jetbrains/kotlin/resolve/calls/results/ResolutionResultsHandler;", "dynamicCallableDescriptors", "Lorg/jetbrains/kotlin/resolve/calls/tasks/DynamicCallableDescriptors;", "syntheticScopes", "Lorg/jetbrains/kotlin/resolve/scopes/SyntheticScopes;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "builderInferenceSupport", "Lorg/jetbrains/kotlin/resolve/calls/inference/BuilderInferenceSupport;", "deprecationResolver", "Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationResolver;", "typeApproximator", "Lorg/jetbrains/kotlin/types/TypeApproximator;", "implicitsResolutionFilter", "Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitsExtensionsResolutionFilter;", "callResolver", "Lorg/jetbrains/kotlin/resolve/calls/CallResolver;", "candidateInterceptor", "Lorg/jetbrains/kotlin/extensions/internal/CandidateInterceptor;", "(Lorg/jetbrains/kotlin/resolve/calls/CandidateResolver;Lorg/jetbrains/kotlin/resolve/calls/tower/TowerResolver;Lorg/jetbrains/kotlin/resolve/calls/results/ResolutionResultsHandler;Lorg/jetbrains/kotlin/resolve/calls/tasks/DynamicCallableDescriptors;Lorg/jetbrains/kotlin/resolve/scopes/SyntheticScopes;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Lorg/jetbrains/kotlin/resolve/calls/inference/BuilderInferenceSupport;Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationResolver;Lorg/jetbrains/kotlin/types/TypeApproximator;Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitsExtensionsResolutionFilter;Lorg/jetbrains/kotlin/resolve/calls/CallResolver;Lorg/jetbrains/kotlin/extensions/internal/CandidateInterceptor;)V", "allCandidatesResult", "Lorg/jetbrains/kotlin/resolve/calls/results/OverloadResolutionResultsImpl;", "D", JvmProtoBufUtil.PLATFORM_TYPE_ID, "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "allCandidates", "", "Lorg/jetbrains/kotlin/resolve/calls/tower/NewResolutionOldInference$MyCandidate;", "convertToOverloadResults", "candidates", "tracing", "Lorg/jetbrains/kotlin/resolve/calls/tasks/TracingStrategy;", "basicCallContext", "Lorg/jetbrains/kotlin/resolve/calls/context/BasicCallResolutionContext;", "reportAdditionalDiagnosticIfNoCandidates", "", "context", "name", "Lorg/jetbrains/kotlin/name/Name;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/resolve/calls/tower/NewResolutionOldInference$ResolutionKind;", "scopeTower", "Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;", "detailedReceiver", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/DetailedReceiver;", "runResolution", "runResolutionForGivenCandidates", "Lorg/jetbrains/kotlin/resolve/calls/tasks/OldResolutionCandidate;", "CandidateFactoryImpl", "CandidateFactoryProviderForInvokeImpl", "ImplicitScopeTowerImpl", "MyCandidate", "ResolutionKind", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NewResolutionOldInference {
    private final BuilderInferenceSupport builderInferenceSupport;
    private final CallResolver callResolver;
    private final CandidateInterceptor candidateInterceptor;
    private final CandidateResolver candidateResolver;
    private final DeprecationResolver deprecationResolver;
    private final DynamicCallableDescriptors dynamicCallableDescriptors;
    private final ImplicitsExtensionsResolutionFilter implicitsResolutionFilter;
    private final LanguageVersionSettings languageVersionSettings;
    private final ResolutionResultsHandler resolutionResultsHandler;
    private final SyntheticScopes syntheticScopes;
    private final TowerResolver towerResolver;
    private final TypeApproximator typeApproximator;

    public NewResolutionOldInference(CandidateResolver candidateResolver, TowerResolver towerResolver, ResolutionResultsHandler resolutionResultsHandler, DynamicCallableDescriptors dynamicCallableDescriptors, SyntheticScopes syntheticScopes, LanguageVersionSettings languageVersionSettings, BuilderInferenceSupport builderInferenceSupport, DeprecationResolver deprecationResolver, TypeApproximator typeApproximator, ImplicitsExtensionsResolutionFilter implicitsResolutionFilter, CallResolver callResolver, CandidateInterceptor candidateInterceptor) {
        Intrinsics.checkNotNullParameter(candidateResolver, "candidateResolver");
        Intrinsics.checkNotNullParameter(towerResolver, "towerResolver");
        Intrinsics.checkNotNullParameter(resolutionResultsHandler, "resolutionResultsHandler");
        Intrinsics.checkNotNullParameter(dynamicCallableDescriptors, "dynamicCallableDescriptors");
        Intrinsics.checkNotNullParameter(syntheticScopes, "syntheticScopes");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(builderInferenceSupport, "builderInferenceSupport");
        Intrinsics.checkNotNullParameter(deprecationResolver, "deprecationResolver");
        Intrinsics.checkNotNullParameter(typeApproximator, "typeApproximator");
        Intrinsics.checkNotNullParameter(implicitsResolutionFilter, "implicitsResolutionFilter");
        Intrinsics.checkNotNullParameter(callResolver, "callResolver");
        Intrinsics.checkNotNullParameter(candidateInterceptor, "candidateInterceptor");
        this.candidateResolver = candidateResolver;
        this.towerResolver = towerResolver;
        this.resolutionResultsHandler = resolutionResultsHandler;
        this.dynamicCallableDescriptors = dynamicCallableDescriptors;
        this.syntheticScopes = syntheticScopes;
        this.languageVersionSettings = languageVersionSettings;
        this.builderInferenceSupport = builderInferenceSupport;
        this.deprecationResolver = deprecationResolver;
        this.typeApproximator = typeApproximator;
        this.implicitsResolutionFilter = implicitsResolutionFilter;
        this.callResolver = callResolver;
        this.candidateInterceptor = candidateInterceptor;
    }

    /* JADX INFO: compiled from: NewResolutionOldInference.kt */
    @Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0005\u0013\u0014\u0015\u0016\u0017B\u0007\b\u0004¢\u0006\u0002\u0010\u0002JE\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H ¢\u0006\u0002\b\u0012\u0082\u0001\u0005\u0018\u0019\u001a\u001b\u001c¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/NewResolutionOldInference$ResolutionKind;", "", "()V", "createTowerProcessor", "Lorg/jetbrains/kotlin/resolve/calls/tower/ScopeTowerProcessor;", "Lorg/jetbrains/kotlin/resolve/calls/tower/NewResolutionOldInference$MyCandidate;", "outer", "Lorg/jetbrains/kotlin/resolve/calls/tower/NewResolutionOldInference;", "name", "Lorg/jetbrains/kotlin/name/Name;", "tracing", "Lorg/jetbrains/kotlin/resolve/calls/tasks/TracingStrategy;", "scopeTower", "Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;", "explicitReceiver", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/DetailedReceiver;", "context", "Lorg/jetbrains/kotlin/resolve/calls/context/BasicCallResolutionContext;", "createTowerProcessor$frontend", "CallableReference", "Function", "GivenCandidates", "Invoke", "Variable", "Lorg/jetbrains/kotlin/resolve/calls/tower/NewResolutionOldInference$ResolutionKind$CallableReference;", "Lorg/jetbrains/kotlin/resolve/calls/tower/NewResolutionOldInference$ResolutionKind$Function;", "Lorg/jetbrains/kotlin/resolve/calls/tower/NewResolutionOldInference$ResolutionKind$GivenCandidates;", "Lorg/jetbrains/kotlin/resolve/calls/tower/NewResolutionOldInference$ResolutionKind$Invoke;", "Lorg/jetbrains/kotlin/resolve/calls/tower/NewResolutionOldInference$ResolutionKind$Variable;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static abstract class ResolutionKind {
        public /* synthetic */ ResolutionKind(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public abstract ScopeTowerProcessor<MyCandidate> createTowerProcessor$frontend(NewResolutionOldInference outer, Name name2, TracingStrategy tracing, ImplicitScopeTower scopeTower, DetailedReceiver explicitReceiver, BasicCallResolutionContext context);

        private ResolutionKind() {
        }

        /* JADX INFO: compiled from: NewResolutionOldInference.kt */
        @Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002JE\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0010¢\u0006\u0002\b\u0012¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/NewResolutionOldInference$ResolutionKind$Function;", "Lorg/jetbrains/kotlin/resolve/calls/tower/NewResolutionOldInference$ResolutionKind;", "()V", "createTowerProcessor", "Lorg/jetbrains/kotlin/resolve/calls/tower/ScopeTowerProcessor;", "Lorg/jetbrains/kotlin/resolve/calls/tower/NewResolutionOldInference$MyCandidate;", "outer", "Lorg/jetbrains/kotlin/resolve/calls/tower/NewResolutionOldInference;", "name", "Lorg/jetbrains/kotlin/name/Name;", "tracing", "Lorg/jetbrains/kotlin/resolve/calls/tasks/TracingStrategy;", "scopeTower", "Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;", "explicitReceiver", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/DetailedReceiver;", "context", "Lorg/jetbrains/kotlin/resolve/calls/context/BasicCallResolutionContext;", "createTowerProcessor$frontend", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Function extends ResolutionKind {
            public static final Function INSTANCE = new Function();

            private Function() {
                super(null);
            }

            @Override // org.jetbrains.kotlin.resolve.calls.tower.NewResolutionOldInference.ResolutionKind
            public ScopeTowerProcessor<MyCandidate> createTowerProcessor$frontend(NewResolutionOldInference outer, Name name2, TracingStrategy tracing, ImplicitScopeTower scopeTower, DetailedReceiver explicitReceiver, BasicCallResolutionContext context) {
                Intrinsics.checkNotNullParameter(outer, "outer");
                Intrinsics.checkNotNullParameter(name2, "name");
                Intrinsics.checkNotNullParameter(tracing, "tracing");
                Intrinsics.checkNotNullParameter(scopeTower, "scopeTower");
                Intrinsics.checkNotNullParameter(context, "context");
                CandidateFactoryImpl candidateFactoryImpl = new CandidateFactoryImpl(outer, name2, context, tracing);
                return ScopeTowerProcessorsKt.createFunctionProcessor(scopeTower, name2, candidateFactoryImpl, new CandidateFactoryProviderForInvokeImpl(outer, candidateFactoryImpl), explicitReceiver);
            }
        }

        /* JADX INFO: compiled from: NewResolutionOldInference.kt */
        @Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002JE\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0010¢\u0006\u0002\b\u0012¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/NewResolutionOldInference$ResolutionKind$Variable;", "Lorg/jetbrains/kotlin/resolve/calls/tower/NewResolutionOldInference$ResolutionKind;", "()V", "createTowerProcessor", "Lorg/jetbrains/kotlin/resolve/calls/tower/ScopeTowerProcessor;", "Lorg/jetbrains/kotlin/resolve/calls/tower/NewResolutionOldInference$MyCandidate;", "outer", "Lorg/jetbrains/kotlin/resolve/calls/tower/NewResolutionOldInference;", "name", "Lorg/jetbrains/kotlin/name/Name;", "tracing", "Lorg/jetbrains/kotlin/resolve/calls/tasks/TracingStrategy;", "scopeTower", "Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;", "explicitReceiver", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/DetailedReceiver;", "context", "Lorg/jetbrains/kotlin/resolve/calls/context/BasicCallResolutionContext;", "createTowerProcessor$frontend", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Variable extends ResolutionKind {
            public static final Variable INSTANCE = new Variable();

            private Variable() {
                super(null);
            }

            @Override // org.jetbrains.kotlin.resolve.calls.tower.NewResolutionOldInference.ResolutionKind
            public ScopeTowerProcessor<MyCandidate> createTowerProcessor$frontend(NewResolutionOldInference outer, Name name2, TracingStrategy tracing, ImplicitScopeTower scopeTower, DetailedReceiver explicitReceiver, BasicCallResolutionContext context) {
                Intrinsics.checkNotNullParameter(outer, "outer");
                Intrinsics.checkNotNullParameter(name2, "name");
                Intrinsics.checkNotNullParameter(tracing, "tracing");
                Intrinsics.checkNotNullParameter(scopeTower, "scopeTower");
                Intrinsics.checkNotNullParameter(context, "context");
                return ScopeTowerProcessorsKt.createVariableAndObjectProcessor$default(scopeTower, name2, new CandidateFactoryImpl(outer, name2, context, tracing), explicitReceiver, false, 16, null);
            }
        }

        /* JADX INFO: compiled from: NewResolutionOldInference.kt */
        @Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002JE\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0010¢\u0006\u0002\b\u0012¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/NewResolutionOldInference$ResolutionKind$CallableReference;", "Lorg/jetbrains/kotlin/resolve/calls/tower/NewResolutionOldInference$ResolutionKind;", "()V", "createTowerProcessor", "Lorg/jetbrains/kotlin/resolve/calls/tower/ScopeTowerProcessor;", "Lorg/jetbrains/kotlin/resolve/calls/tower/NewResolutionOldInference$MyCandidate;", "outer", "Lorg/jetbrains/kotlin/resolve/calls/tower/NewResolutionOldInference;", "name", "Lorg/jetbrains/kotlin/name/Name;", "tracing", "Lorg/jetbrains/kotlin/resolve/calls/tasks/TracingStrategy;", "scopeTower", "Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;", "explicitReceiver", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/DetailedReceiver;", "context", "Lorg/jetbrains/kotlin/resolve/calls/context/BasicCallResolutionContext;", "createTowerProcessor$frontend", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class CallableReference extends ResolutionKind {
            public static final CallableReference INSTANCE = new CallableReference();

            private CallableReference() {
                super(null);
            }

            @Override // org.jetbrains.kotlin.resolve.calls.tower.NewResolutionOldInference.ResolutionKind
            public ScopeTowerProcessor<MyCandidate> createTowerProcessor$frontend(NewResolutionOldInference outer, Name name2, TracingStrategy tracing, ImplicitScopeTower scopeTower, DetailedReceiver explicitReceiver, BasicCallResolutionContext context) {
                Intrinsics.checkNotNullParameter(outer, "outer");
                Intrinsics.checkNotNullParameter(name2, "name");
                Intrinsics.checkNotNullParameter(tracing, "tracing");
                Intrinsics.checkNotNullParameter(scopeTower, "scopeTower");
                Intrinsics.checkNotNullParameter(context, "context");
                return new PrioritizedCompositeScopeTowerProcessor(ScopeTowerProcessorsKt.createSimpleFunctionProcessor(scopeTower, name2, new CandidateFactoryImpl(outer, name2, context, tracing), explicitReceiver, false), ScopeTowerProcessorsKt.createVariableProcessor(scopeTower, name2, new CandidateFactoryImpl(outer, name2, context, tracing), explicitReceiver, false));
            }
        }

        /* JADX INFO: compiled from: NewResolutionOldInference.kt */
        @Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002JE\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0010¢\u0006\u0002\b\u0012¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/NewResolutionOldInference$ResolutionKind$Invoke;", "Lorg/jetbrains/kotlin/resolve/calls/tower/NewResolutionOldInference$ResolutionKind;", "()V", "createTowerProcessor", "Lorg/jetbrains/kotlin/resolve/calls/tower/ScopeTowerProcessor;", "Lorg/jetbrains/kotlin/resolve/calls/tower/NewResolutionOldInference$MyCandidate;", "outer", "Lorg/jetbrains/kotlin/resolve/calls/tower/NewResolutionOldInference;", "name", "Lorg/jetbrains/kotlin/name/Name;", "tracing", "Lorg/jetbrains/kotlin/resolve/calls/tasks/TracingStrategy;", "scopeTower", "Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;", "explicitReceiver", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/DetailedReceiver;", "context", "Lorg/jetbrains/kotlin/resolve/calls/context/BasicCallResolutionContext;", "createTowerProcessor$frontend", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Invoke extends ResolutionKind {
            public static final Invoke INSTANCE = new Invoke();

            private Invoke() {
                super(null);
            }

            @Override // org.jetbrains.kotlin.resolve.calls.tower.NewResolutionOldInference.ResolutionKind
            public ScopeTowerProcessor<MyCandidate> createTowerProcessor$frontend(NewResolutionOldInference outer, Name name2, TracingStrategy tracing, final ImplicitScopeTower scopeTower, DetailedReceiver explicitReceiver, final BasicCallResolutionContext context) {
                Intrinsics.checkNotNullParameter(outer, "outer");
                Intrinsics.checkNotNullParameter(name2, "name");
                Intrinsics.checkNotNullParameter(tracing, "tracing");
                Intrinsics.checkNotNullParameter(scopeTower, "scopeTower");
                Intrinsics.checkNotNullParameter(context, "context");
                final CandidateFactoryImpl candidateFactoryImpl = new CandidateFactoryImpl(outer, name2, context, tracing);
                Call call = context.call;
                final CallTransformer.CallForImplicitInvoke callForImplicitInvoke = call instanceof CallTransformer.CallForImplicitInvoke ? (CallTransformer.CallForImplicitInvoke) call : null;
                if (callForImplicitInvoke != null) {
                    return ScopeTowerProcessorsKt.createProcessorWithReceiverValueOrEmpty(explicitReceiver, new Function1<ReceiverValueWithSmartCastInfo, ScopeTowerProcessor<? extends MyCandidate>>() { // from class: org.jetbrains.kotlin.resolve.calls.tower.NewResolutionOldInference$ResolutionKind$Invoke$createTowerProcessor$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final ScopeTowerProcessor<NewResolutionOldInference.MyCandidate> invoke(ReceiverValueWithSmartCastInfo receiverValueWithSmartCastInfo) {
                            ImplicitScopeTower implicitScopeTower = scopeTower;
                            NewResolutionOldInference.CandidateFactoryImpl candidateFactoryImpl2 = candidateFactoryImpl;
                            BasicCallResolutionContext basicCallResolutionContext = context;
                            ExpressionReceiver dispatchReceiver = callForImplicitInvoke.getDispatchReceiver();
                            Intrinsics.checkNotNullExpressionValue(dispatchReceiver, "getDispatchReceiver(...)");
                            return InvokeProcessorsKt.createCallTowerProcessorForExplicitInvoke(implicitScopeTower, candidateFactoryImpl2, NewResolutionOldInferenceKt.transformToReceiverWithSmartCastInfo(basicCallResolutionContext, dispatchReceiver), receiverValueWithSmartCastInfo);
                        }
                    });
                }
                throw new AssertionError("Call should be CallForImplicitInvoke, but it is: " + context.call);
            }
        }

        /* JADX INFO: compiled from: NewResolutionOldInference.kt */
        @Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002JE\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0010¢\u0006\u0002\b\u0012¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/NewResolutionOldInference$ResolutionKind$GivenCandidates;", "Lorg/jetbrains/kotlin/resolve/calls/tower/NewResolutionOldInference$ResolutionKind;", "()V", "createTowerProcessor", "Lorg/jetbrains/kotlin/resolve/calls/tower/ScopeTowerProcessor;", "Lorg/jetbrains/kotlin/resolve/calls/tower/NewResolutionOldInference$MyCandidate;", "outer", "Lorg/jetbrains/kotlin/resolve/calls/tower/NewResolutionOldInference;", "name", "Lorg/jetbrains/kotlin/name/Name;", "tracing", "Lorg/jetbrains/kotlin/resolve/calls/tasks/TracingStrategy;", "scopeTower", "Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;", "explicitReceiver", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/DetailedReceiver;", "context", "Lorg/jetbrains/kotlin/resolve/calls/context/BasicCallResolutionContext;", "createTowerProcessor$frontend", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class GivenCandidates extends ResolutionKind {
            public GivenCandidates() {
                super(null);
            }

            @Override // org.jetbrains.kotlin.resolve.calls.tower.NewResolutionOldInference.ResolutionKind
            public ScopeTowerProcessor<MyCandidate> createTowerProcessor$frontend(NewResolutionOldInference outer, Name name2, TracingStrategy tracing, ImplicitScopeTower scopeTower, DetailedReceiver explicitReceiver, BasicCallResolutionContext context) {
                Intrinsics.checkNotNullParameter(outer, "outer");
                Intrinsics.checkNotNullParameter(name2, "name");
                Intrinsics.checkNotNullParameter(tracing, "tracing");
                Intrinsics.checkNotNullParameter(scopeTower, "scopeTower");
                Intrinsics.checkNotNullParameter(context, "context");
                throw new IllegalStateException("Should be not called");
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:32:0x0110  */
    public final <D extends CallableDescriptor> OverloadResolutionResultsImpl<D> runResolution(BasicCallResolutionContext context, Name name2, ResolutionKind kind, TracingStrategy tracing) {
        ReceiverValueWithSmartCastInfo receiverValueWithSmartCastInfoTransformToReceiverWithSmartCastInfo;
        Name name3;
        boolean z;
        boolean z2;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(kind, "kind");
        Intrinsics.checkNotNullParameter(tracing, "tracing");
        Receiver explicitReceiver = context.call.getExplicitReceiver();
        if (explicitReceiver == null ? true : explicitReceiver instanceof QualifierReceiver) {
            receiverValueWithSmartCastInfoTransformToReceiverWithSmartCastInfo = (DetailedReceiver) explicitReceiver;
        } else {
            Intrinsics.checkNotNull(explicitReceiver, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue");
            receiverValueWithSmartCastInfoTransformToReceiverWithSmartCastInfo = NewResolutionOldInferenceKt.transformToReceiverWithSmartCastInfo(context, (ReceiverValue) explicitReceiver);
        }
        DetailedReceiver detailedReceiver = receiverValueWithSmartCastInfoTransformToReceiverWithSmartCastInfo;
        DynamicCallableDescriptors dynamicCallableDescriptors = this.dynamicCallableDescriptors;
        Call call = context.call;
        Intrinsics.checkNotNullExpressionValue(call, "call");
        MemberScopeImpl memberScopeImplCreateDynamicDescriptorScope = dynamicCallableDescriptors.createDynamicDescriptorScope(call, context.scope.getOwnerDescriptor());
        SyntheticScopes syntheticScopes = this.syntheticScopes;
        Call call2 = context.call;
        Intrinsics.checkNotNullExpressionValue(call2, "call");
        ImplicitScopeTowerImpl implicitScopeTowerImpl = new ImplicitScopeTowerImpl(context, memberScopeImplCreateDynamicDescriptorScope, syntheticScopes, CallUtilKt.createLookupLocation(call2), this.typeApproximator, this.implicitsResolutionFilter, this.callResolver, this.candidateInterceptor);
        boolean zSupportsFeature = this.languageVersionSettings.supportsFeature(LanguageFeature.OperatorRem);
        Call call3 = context.call;
        Intrinsics.checkNotNullExpressionValue(call3, "call");
        boolean zIsBinaryRemOperator = CallResolverUtilKt.isBinaryRemOperator(call3);
        if (!zIsBinaryRemOperator || zSupportsFeature) {
            name3 = name2;
        } else {
            Name name4 = OperatorConventions.REM_TO_MOD_OPERATION_NAMES.get(name2);
            Intrinsics.checkNotNull(name4);
            name3 = name4;
        }
        Intrinsics.checkNotNull(name3);
        ImplicitScopeTowerImpl implicitScopeTowerImpl2 = implicitScopeTowerImpl;
        ScopeTowerProcessor<MyCandidate> scopeTowerProcessorCreateTowerProcessor$frontend = kind.createTowerProcessor$frontend(this, name3, tracing, implicitScopeTowerImpl2, detailedReceiver, context);
        if (context.collectAllCandidates) {
            OverloadResolutionResultsImpl<D> overloadResolutionResultsImplAllCandidatesResult = allCandidatesResult(this.towerResolver.collectAllCandidates(implicitScopeTowerImpl2, scopeTowerProcessorCreateTowerProcessor$frontend, name3));
            Intrinsics.checkNotNullExpressionValue(overloadResolutionResultsImplAllCandidatesResult, "allCandidatesResult(...)");
            return overloadResolutionResultsImplAllCandidatesResult;
        }
        Collection<MyCandidate> collectionRunResolve = this.towerResolver.runResolve(implicitScopeTowerImpl2, scopeTowerProcessorCreateTowerProcessor$frontend, !Intrinsics.areEqual(kind, ResolutionKind.CallableReference.INSTANCE), name3);
        if (!collectionRunResolve.isEmpty()) {
            Collection<MyCandidate> collection = collectionRunResolve;
            if (!(collection instanceof Collection) || !collection.isEmpty()) {
                Iterator<T> it = collection.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        z2 = true;
                        break;
                    }
                    if (!TowerUtilsKt.isInapplicable(((MyCandidate) it.next()).getResultingApplicability())) {
                        z2 = false;
                        break;
                    }
                }
            } else {
                z2 = true;
                break;
            }
            z = z2;
        }
        if (zIsBinaryRemOperator && zSupportsFeature && z) {
            Name name5 = OperatorConventions.REM_TO_MOD_OPERATION_NAMES.get(name2);
            Intrinsics.checkNotNull(name5);
            collectionRunResolve = this.towerResolver.runResolve(implicitScopeTowerImpl2, kind.createTowerProcessor$frontend(this, name5, tracing, implicitScopeTowerImpl2, detailedReceiver, context), !Intrinsics.areEqual(kind, ResolutionKind.CallableReference.INSTANCE), name5);
        }
        Name name6 = name3;
        Collection<MyCandidate> collectionInterceptResolvedCandidates = this.candidateInterceptor.interceptResolvedCandidates(collectionRunResolve, context, this.candidateResolver, this.callResolver, name2, kind, tracing);
        if (collectionInterceptResolvedCandidates.isEmpty() && reportAdditionalDiagnosticIfNoCandidates(context, name6, kind, implicitScopeTowerImpl2, detailedReceiver)) {
            OverloadResolutionResultsImpl<D> overloadResolutionResultsImplNameNotFound = OverloadResolutionResultsImpl.nameNotFound();
            Intrinsics.checkNotNullExpressionValue(overloadResolutionResultsImplNameNotFound, "nameNotFound(...)");
            return overloadResolutionResultsImplNameNotFound;
        }
        OverloadResolutionResultsImpl<D> overloadResolutionResultsImplConvertToOverloadResults = convertToOverloadResults(collectionInterceptResolvedCandidates, r5, context);
        this.builderInferenceSupport.checkBuilderInferenceCalls(context, tracing, overloadResolutionResultsImplConvertToOverloadResults);
        return overloadResolutionResultsImplConvertToOverloadResults;
    }

    public final <D extends CallableDescriptor> OverloadResolutionResultsImpl<D> runResolutionForGivenCandidates(BasicCallResolutionContext basicCallContext, TracingStrategy tracing, Collection<? extends OldResolutionCandidate<D>> candidates) {
        MyCandidate myCandidate;
        Intrinsics.checkNotNullParameter(basicCallContext, "basicCallContext");
        Intrinsics.checkNotNullParameter(tracing, "tracing");
        Intrinsics.checkNotNullParameter(candidates, "candidates");
        Collection<? extends OldResolutionCandidate<D>> collection = candidates;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            OldResolutionCandidate oldResolutionCandidate = (OldResolutionCandidate) it.next();
            TemporaryBindingTrace temporaryBindingTraceCreate = TemporaryBindingTrace.create(basicCallContext.trace, "Context for resolve candidate");
            Intrinsics.checkNotNullExpressionValue(temporaryBindingTraceCreate, "create(...)");
            final ResolvedCallImpl resolvedCallImplCreate = ResolvedCallImpl.create(oldResolutionCandidate, temporaryBindingTraceCreate, tracing, basicCallContext.dataFlowInfoForArguments);
            Intrinsics.checkNotNullExpressionValue(resolvedCallImplCreate, "create(...)");
            DeprecationResolver deprecationResolver = this.deprecationResolver;
            CallableDescriptor descriptor = oldResolutionCandidate.getDescriptor();
            Intrinsics.checkNotNullExpressionValue(descriptor, "getDescriptor(...)");
            if (DeprecationResolver.isHiddenInResolution$default(deprecationResolver, descriptor, basicCallContext.call, basicCallContext.trace.getContext(), NewResolutionOldInferenceKt.isSuperCall(basicCallContext), false, 16, null)) {
                myCandidate = new MyCandidate(CollectionsKt.listOf(HiddenDescriptor.INSTANCE), resolvedCallImplCreate, null, 4, null);
            } else {
                ResolvedCallImpl resolvedCallImpl = resolvedCallImplCreate;
                CallCandidateResolutionContext<D> callCandidateResolutionContextCreate = CallCandidateResolutionContext.create(resolvedCallImpl, basicCallContext, temporaryBindingTraceCreate, tracing, basicCallContext.call, CandidateResolveMode.EXIT_ON_FIRST_ERROR);
                CandidateResolver candidateResolver = this.candidateResolver;
                Intrinsics.checkNotNull(callCandidateResolutionContextCreate);
                CheckArgumentTypesMode checkArguments = basicCallContext.checkArguments;
                Intrinsics.checkNotNullExpressionValue(checkArguments, "checkArguments");
                candidateResolver.performResolutionForCandidateCall(callCandidateResolutionContextCreate, checkArguments);
                ResolutionStatus status = resolvedCallImplCreate.getStatus();
                Intrinsics.checkNotNullExpressionValue(status, "getStatus(...)");
                myCandidate = new MyCandidate(CollectionsKt.listOfNotNull(NewResolutionOldInferenceKt.createPreviousResolveError(status)), resolvedCallImpl, new Function0<List<? extends KotlinCallDiagnostic>>() { // from class: org.jetbrains.kotlin.resolve.calls.tower.NewResolutionOldInference$runResolutionForGivenCandidates$resolvedCandidates$1$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final List<? extends KotlinCallDiagnostic> invoke() {
                        resolvedCallImplCreate.performRemainingTasks();
                        ResolutionStatus status2 = resolvedCallImplCreate.getStatus();
                        Intrinsics.checkNotNullExpressionValue(status2, "getStatus(...)");
                        return CollectionsKt.listOfNotNull(NewResolutionOldInferenceKt.createPreviousResolveError(status2));
                    }
                });
            }
            arrayList.add(myCandidate);
        }
        ArrayList arrayList2 = arrayList;
        if (basicCallContext.collectAllCandidates) {
            OverloadResolutionResultsImpl<D> overloadResolutionResultsImplAllCandidatesResult = allCandidatesResult(this.towerResolver.runWithEmptyTowerData(new KnownResultProcessor(arrayList2), new TowerResolver.AllCandidatesCollector(), false));
            Intrinsics.checkNotNullExpressionValue(overloadResolutionResultsImplAllCandidatesResult, "allCandidatesResult(...)");
            return overloadResolutionResultsImplAllCandidatesResult;
        }
        return convertToOverloadResults(this.towerResolver.runWithEmptyTowerData(new KnownResultProcessor(arrayList2), new TowerResolver.SuccessfulResultCollector(), true), tracing, basicCallContext);
    }

    private final <D extends CallableDescriptor> OverloadResolutionResultsImpl<D> allCandidatesResult(Collection<MyCandidate> allCandidates) {
        OverloadResolutionResultsImpl<D> overloadResolutionResultsImplNameNotFound = OverloadResolutionResultsImpl.nameNotFound();
        Collection<MyCandidate> collection = allCandidates;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            MutableResolvedCall<?> resolvedCall = ((MyCandidate) it.next()).getResolvedCall();
            Intrinsics.checkNotNull(resolvedCall, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.calls.model.MutableResolvedCall<D of org.jetbrains.kotlin.resolve.calls.tower.NewResolutionOldInference.allCandidatesResult$lambda$3$lambda$2>");
            arrayList.add(resolvedCall);
        }
        overloadResolutionResultsImplNameNotFound.setAllCandidates(arrayList);
        return overloadResolutionResultsImplNameNotFound;
    }

    /* JADX WARN: Type inference failed for: r6v4, types: [java.lang.Object, org.jetbrains.kotlin.descriptors.CallableDescriptor] */
    /* JADX WARN: Type inference failed for: r7v4, types: [java.lang.Object, org.jetbrains.kotlin.descriptors.CallableDescriptor] */
    private final <D extends CallableDescriptor> OverloadResolutionResultsImpl<D> convertToOverloadResults(Collection<MyCandidate> candidates, TracingStrategy tracing, BasicCallResolutionContext basicCallContext) {
        Collection<MyCandidate> collection = candidates;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
        for (MyCandidate myCandidate : collection) {
            List<KotlinCallDiagnostic> listComponent1 = myCandidate.component1();
            MutableResolvedCall<?> mutableResolvedCallComponent2 = myCandidate.component2();
            if (mutableResolvedCallComponent2 instanceof VariableAsFunctionResolvedCallImpl) {
                VariableAsFunctionResolvedCallImpl variableAsFunctionResolvedCallImpl = (VariableAsFunctionResolvedCallImpl) mutableResolvedCallComponent2;
                tracing.bindReference(variableAsFunctionResolvedCallImpl.getVariableCall().getTrace(), variableAsFunctionResolvedCallImpl.getVariableCall());
                tracing.bindResolvedCall(variableAsFunctionResolvedCallImpl.getVariableCall().getTrace(), mutableResolvedCallComponent2);
                DelegatingBindingTrace trace = variableAsFunctionResolvedCallImpl.getVariableCall().getTrace();
                Intrinsics.checkNotNullExpressionValue(trace, "getTrace(...)");
                DelegatingBindingTrace trace2 = variableAsFunctionResolvedCallImpl.getFunctionCall().getTrace();
                Intrinsics.checkNotNullExpressionValue(trace2, "getTrace(...)");
                DelegatingBindingTrace.addOwnDataTo$default(trace, trace2, null, false, 6, null);
                variableAsFunctionResolvedCallImpl.getFunctionCall().getTracingStrategy().bindReference(variableAsFunctionResolvedCallImpl.getFunctionCall().getTrace(), variableAsFunctionResolvedCallImpl.getFunctionCall());
            } else {
                MutableResolvedCall<?> mutableResolvedCall = mutableResolvedCallComponent2;
                tracing.bindReference(mutableResolvedCallComponent2.getTrace(), mutableResolvedCall);
                tracing.bindResolvedCall(mutableResolvedCallComponent2.getTrace(), mutableResolvedCall);
            }
            if (mutableResolvedCallComponent2.getStatus().possibleTransformToSuccess()) {
                for (KotlinCallDiagnostic kotlinCallDiagnostic : listComponent1) {
                    if (kotlinCallDiagnostic instanceof UnsupportedInnerClassCall) {
                        DelegatingBindingTrace trace3 = mutableResolvedCallComponent2.getTrace();
                        Diagnostic diagnosticOn = Errors.UNSUPPORTED.on(mutableResolvedCallComponent2.getCall().getCallElement(), ((UnsupportedInnerClassCall) kotlinCallDiagnostic).getMessage());
                        Intrinsics.checkNotNullExpressionValue(diagnosticOn, "on(...)");
                        trace3.report(diagnosticOn);
                    } else if (kotlinCallDiagnostic instanceof NestedClassViaInstanceReference) {
                        tracing.nestedClassAccessViaInstanceReference(mutableResolvedCallComponent2.getTrace(), ((NestedClassViaInstanceReference) kotlinCallDiagnostic).getClassDescriptor(), mutableResolvedCallComponent2.getExplicitReceiverKind());
                    } else if (!(kotlinCallDiagnostic instanceof ErrorDescriptorDiagnostic) && (kotlinCallDiagnostic instanceof ResolvedUsingDeprecatedVisibility)) {
                        Call call = mutableResolvedCallComponent2.getCall();
                        Intrinsics.checkNotNullExpressionValue(call, "getCall(...)");
                        ?? candidateDescriptor = mutableResolvedCallComponent2.get$suspendPropertyDescriptor();
                        Intrinsics.checkNotNullExpressionValue(candidateDescriptor, "getCandidateDescriptor(...)");
                        ?? resultingDescriptor = mutableResolvedCallComponent2.getResultingDescriptor();
                        Intrinsics.checkNotNullExpressionValue(resultingDescriptor, "getResultingDescriptor(...)");
                        DelegatingBindingTrace trace4 = mutableResolvedCallComponent2.getTrace();
                        Intrinsics.checkNotNullExpressionValue(trace4, "getTrace(...)");
                        NewResolutionOldInferenceKt.reportResolvedUsingDeprecatedVisibility(call, candidateDescriptor, resultingDescriptor, (ResolvedUsingDeprecatedVisibility) kotlinCallDiagnostic, trace4);
                    }
                }
            }
            Intrinsics.checkNotNull(mutableResolvedCallComponent2, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.calls.model.MutableResolvedCall<D of org.jetbrains.kotlin.resolve.calls.tower.NewResolutionOldInference.convertToOverloadResults$lambda$4>");
            arrayList.add(mutableResolvedCallComponent2);
        }
        OverloadResolutionResultsImpl<D> overloadResolutionResultsImplComputeResultAndReportErrors = this.resolutionResultsHandler.computeResultAndReportErrors(basicCallContext, tracing, arrayList, this.languageVersionSettings);
        Intrinsics.checkNotNullExpressionValue(overloadResolutionResultsImplComputeResultAndReportErrors, "computeResultAndReportErrors(...)");
        return overloadResolutionResultsImplComputeResultAndReportErrors;
    }

    private final boolean reportAdditionalDiagnosticIfNoCandidates(BasicCallResolutionContext context, Name name2, ResolutionKind kind, ImplicitScopeTower scopeTower, DetailedReceiver detailedReceiver) {
        List listCollectErrorCandidatesForVariable;
        KtExpression calleeExpression = context.call.getCalleeExpression();
        KtReferenceExpression ktReferenceExpression = calleeExpression instanceof KtReferenceExpression ? (KtReferenceExpression) calleeExpression : null;
        if (ktReferenceExpression == null) {
            return false;
        }
        if (Intrinsics.areEqual(kind, ResolutionKind.Function.INSTANCE)) {
            listCollectErrorCandidatesForVariable = ErrorCandidateFactoryKt.collectErrorCandidatesForFunction(scopeTower, name2, detailedReceiver);
        } else {
            listCollectErrorCandidatesForVariable = Intrinsics.areEqual(kind, ResolutionKind.Variable.INSTANCE) ? ErrorCandidateFactoryKt.collectErrorCandidatesForVariable(scopeTower, name2, detailedReceiver) : CollectionsKt.emptyList();
        }
        Object objFirstOrNull = CollectionsKt.firstOrNull((Iterable<? extends Object>) listCollectErrorCandidatesForVariable);
        ErrorCandidate.Classifier classifier = objFirstOrNull instanceof ErrorCandidate.Classifier ? (ErrorCandidate.Classifier) objFirstOrNull : null;
        if (classifier == null) {
            return false;
        }
        context.trace.record(BindingContext.REFERENCE_TARGET, ktReferenceExpression, classifier.getDescriptor());
        context.trace.report(Errors.RESOLUTION_TO_CLASSIFIER.on(ktReferenceExpression, classifier.getDescriptor(), classifier.getKind(), classifier.getErrorMessage()));
        return true;
    }

    /* JADX INFO: compiled from: NewResolutionOldInference.kt */
    @Metadata(d1 = {"\u0000\u0090\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001BE\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011¢\u0006\u0002\u0010\u0012J\u0016\u00106\u001a\b\u0012\u0004\u0012\u00020\u001a072\u0006\u00108\u001a\u00020+H\u0016J\u0012\u00109\u001a\u0004\u0018\u00010\u001a2\u0006\u00108\u001a\u00020+H\u0016J\u0012\u0010:\u001a\u0004\u0018\u00010;2\u0006\u0010<\u001a\u00020;H\u0016JH\u0010=\u001a\b\u0012\u0004\u0012\u00020?0>2\u0006\u0010@\u001a\u00020A2\u0006\u0010<\u001a\u00020;2\f\u0010B\u001a\b\u0012\u0004\u0012\u00020?0>2\u0006\u0010\b\u001a\u00020\t2\b\u0010C\u001a\u0004\u0018\u00010\u001a2\b\u0010D\u001a\u0004\u0018\u00010\u001aH\u0016JH\u0010E\u001a\b\u0012\u0004\u0012\u00020F0>2\u0006\u0010@\u001a\u00020A2\u0006\u0010<\u001a\u00020;2\f\u0010B\u001a\b\u0012\u0004\u0012\u00020F0>2\u0006\u0010\b\u001a\u00020\t2\b\u0010C\u001a\u0004\u0018\u00010\u001a2\b\u0010D\u001a\u0004\u0018\u00010\u001aH\u0016R\u0014\u0010\u0013\u001a\u00020\u00148VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R*\u0010\u0017\u001a\u001e\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\u001a0\u0018j\u000e\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\u001a`\u001bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u000e\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0011\u0010\u0010\u001a\u00020\u0011¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u0014\u0010\f\u001a\u00020\rX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u0014\u0010$\u001a\u00020\u00148VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b$\u0010\u0016R\u0014\u0010%\u001a\u00020\u00148VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b%\u0010\u0016R\u0014\u0010&\u001a\u00020'8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b(\u0010)R\u0014\u0010*\u001a\u00020+8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b,\u0010-R\u0014\u0010\b\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b.\u0010/R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b0\u00101R\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b2\u00103R\u0014\u0010\n\u001a\u00020\u000bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b4\u00105¨\u0006G"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/NewResolutionOldInference$ImplicitScopeTowerImpl;", "Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;", "resolutionContext", "Lorg/jetbrains/kotlin/resolve/calls/context/BasicCallResolutionContext;", "dynamicScope", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "syntheticScopes", "Lorg/jetbrains/kotlin/resolve/scopes/SyntheticScopes;", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/incremental/components/LookupLocation;", "typeApproximator", "Lorg/jetbrains/kotlin/types/TypeApproximator;", "implicitsResolutionFilter", "Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitsExtensionsResolutionFilter;", "callResolver", "Lorg/jetbrains/kotlin/resolve/calls/CallResolver;", "candidateInterceptor", "Lorg/jetbrains/kotlin/extensions/internal/CandidateInterceptor;", "(Lorg/jetbrains/kotlin/resolve/calls/context/BasicCallResolutionContext;Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;Lorg/jetbrains/kotlin/resolve/scopes/SyntheticScopes;Lorg/jetbrains/kotlin/incremental/components/LookupLocation;Lorg/jetbrains/kotlin/types/TypeApproximator;Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitsExtensionsResolutionFilter;Lorg/jetbrains/kotlin/resolve/calls/CallResolver;Lorg/jetbrains/kotlin/extensions/internal/CandidateInterceptor;)V", "areContextReceiversEnabled", "", "getAreContextReceiversEnabled", "()Z", ResponseCacheMiddleware.CACHE, "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;", "Lkotlin/collections/HashMap;", "getCallResolver", "()Lorg/jetbrains/kotlin/resolve/calls/CallResolver;", "getCandidateInterceptor", "()Lorg/jetbrains/kotlin/extensions/internal/CandidateInterceptor;", "getDynamicScope", "()Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "getImplicitsResolutionFilter", "()Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitsExtensionsResolutionFilter;", "isDebuggerContext", "isNewInferenceEnabled", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "getLanguageVersionSettings", "()Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "lexicalScope", "Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;", "getLexicalScope", "()Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;", "getLocation", "()Lorg/jetbrains/kotlin/incremental/components/LookupLocation;", "getResolutionContext", "()Lorg/jetbrains/kotlin/resolve/calls/context/BasicCallResolutionContext;", "getSyntheticScopes", "()Lorg/jetbrains/kotlin/resolve/scopes/SyntheticScopes;", "getTypeApproximator", "()Lorg/jetbrains/kotlin/types/TypeApproximator;", "getContextReceivers", "", "scope", "getImplicitReceiver", "getNameForGivenImportAlias", "Lorg/jetbrains/kotlin/name/Name;", "name", "interceptFunctionCandidates", "", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "resolutionScope", "Lorg/jetbrains/kotlin/resolve/scopes/ResolutionScope;", "initialResults", "dispatchReceiver", "extensionReceiver", "interceptVariableCandidates", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ImplicitScopeTowerImpl implements ImplicitScopeTower {
        private final HashMap<ReceiverValue, ReceiverValueWithSmartCastInfo> cache;
        private final CallResolver callResolver;
        private final CandidateInterceptor candidateInterceptor;
        private final MemberScope dynamicScope;
        private final ImplicitsExtensionsResolutionFilter implicitsResolutionFilter;
        private final LookupLocation location;
        private final BasicCallResolutionContext resolutionContext;
        private final SyntheticScopes syntheticScopes;
        private final TypeApproximator typeApproximator;

        public ImplicitScopeTowerImpl(BasicCallResolutionContext resolutionContext, MemberScope dynamicScope, SyntheticScopes syntheticScopes, LookupLocation location, TypeApproximator typeApproximator, ImplicitsExtensionsResolutionFilter implicitsResolutionFilter, CallResolver callResolver, CandidateInterceptor candidateInterceptor) {
            Intrinsics.checkNotNullParameter(resolutionContext, "resolutionContext");
            Intrinsics.checkNotNullParameter(dynamicScope, "dynamicScope");
            Intrinsics.checkNotNullParameter(syntheticScopes, "syntheticScopes");
            Intrinsics.checkNotNullParameter(location, "location");
            Intrinsics.checkNotNullParameter(typeApproximator, "typeApproximator");
            Intrinsics.checkNotNullParameter(implicitsResolutionFilter, "implicitsResolutionFilter");
            Intrinsics.checkNotNullParameter(callResolver, "callResolver");
            Intrinsics.checkNotNullParameter(candidateInterceptor, "candidateInterceptor");
            this.resolutionContext = resolutionContext;
            this.dynamicScope = dynamicScope;
            this.syntheticScopes = syntheticScopes;
            this.location = location;
            this.typeApproximator = typeApproximator;
            this.implicitsResolutionFilter = implicitsResolutionFilter;
            this.callResolver = callResolver;
            this.candidateInterceptor = candidateInterceptor;
            this.cache = new HashMap<>();
        }

        public final BasicCallResolutionContext getResolutionContext() {
            return this.resolutionContext;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tower.ImplicitScopeTower
        public MemberScope getDynamicScope() {
            return this.dynamicScope;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tower.ImplicitScopeTower
        public SyntheticScopes getSyntheticScopes() {
            return this.syntheticScopes;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tower.ImplicitScopeTower
        public LookupLocation getLocation() {
            return this.location;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tower.ImplicitScopeTower
        public TypeApproximator getTypeApproximator() {
            return this.typeApproximator;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tower.ImplicitScopeTower
        public ImplicitsExtensionsResolutionFilter getImplicitsResolutionFilter() {
            return this.implicitsResolutionFilter;
        }

        public final CallResolver getCallResolver() {
            return this.callResolver;
        }

        public final CandidateInterceptor getCandidateInterceptor() {
            return this.candidateInterceptor;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tower.ImplicitScopeTower
        public ReceiverValueWithSmartCastInfo getImplicitReceiver(LexicalScope scope) {
            ReceiverValue value;
            Intrinsics.checkNotNullParameter(scope, "scope");
            ReceiverParameterDescriptor implicitReceiver = scope.getImplicitReceiver();
            if (implicitReceiver == null || (value = implicitReceiver.getValue()) == null) {
                return null;
            }
            HashMap<ReceiverValue, ReceiverValueWithSmartCastInfo> map = this.cache;
            ReceiverValueWithSmartCastInfo receiverValueWithSmartCastInfoTransformToReceiverWithSmartCastInfo = map.get(value);
            if (receiverValueWithSmartCastInfoTransformToReceiverWithSmartCastInfo == null) {
                receiverValueWithSmartCastInfoTransformToReceiverWithSmartCastInfo = NewResolutionOldInferenceKt.transformToReceiverWithSmartCastInfo(this.resolutionContext, value);
                map.put(value, receiverValueWithSmartCastInfoTransformToReceiverWithSmartCastInfo);
            }
            return receiverValueWithSmartCastInfoTransformToReceiverWithSmartCastInfo;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tower.ImplicitScopeTower
        public List<ReceiverValueWithSmartCastInfo> getContextReceivers(LexicalScope scope) {
            Intrinsics.checkNotNullParameter(scope, "scope");
            List<ReceiverParameterDescriptor> contextReceiversGroup = scope.getContextReceiversGroup();
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(contextReceiversGroup, 10));
            for (ReceiverParameterDescriptor receiverParameterDescriptor : contextReceiversGroup) {
                HashMap<ReceiverValue, ReceiverValueWithSmartCastInfo> map = this.cache;
                ReceiverValue value = receiverParameterDescriptor.getValue();
                Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
                ReceiverValueWithSmartCastInfo receiverValueWithSmartCastInfoTransformToReceiverWithSmartCastInfo = map.get(value);
                if (receiverValueWithSmartCastInfoTransformToReceiverWithSmartCastInfo == null) {
                    BasicCallResolutionContext basicCallResolutionContext = this.resolutionContext;
                    ReceiverValue value2 = receiverParameterDescriptor.getValue();
                    Intrinsics.checkNotNullExpressionValue(value2, "getValue(...)");
                    receiverValueWithSmartCastInfoTransformToReceiverWithSmartCastInfo = NewResolutionOldInferenceKt.transformToReceiverWithSmartCastInfo(basicCallResolutionContext, value2);
                    map.put(value, receiverValueWithSmartCastInfoTransformToReceiverWithSmartCastInfo);
                }
                arrayList.add(receiverValueWithSmartCastInfoTransformToReceiverWithSmartCastInfo);
            }
            return arrayList;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tower.ImplicitScopeTower
        public Name getNameForGivenImportAlias(Name name2) {
            Intrinsics.checkNotNullParameter(name2, "name");
            PsiFile containingFile = this.resolutionContext.call.getCallElement().getContainingFile();
            KtFile ktFile = containingFile instanceof KtFile ? (KtFile) containingFile : null;
            if (ktFile != null) {
                return ktFile.getNameForGivenImportAlias(name2);
            }
            return null;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tower.ImplicitScopeTower
        public LexicalScope getLexicalScope() {
            LexicalScope scope = this.resolutionContext.scope;
            Intrinsics.checkNotNullExpressionValue(scope, "scope");
            return scope;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tower.ImplicitScopeTower
        public boolean isDebuggerContext() {
            return this.resolutionContext.isDebuggerContext;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tower.ImplicitScopeTower
        public boolean isNewInferenceEnabled() {
            return this.resolutionContext.languageVersionSettings.supportsFeature(LanguageFeature.NewInference);
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tower.ImplicitScopeTower
        public boolean getAreContextReceiversEnabled() {
            return this.resolutionContext.languageVersionSettings.supportsFeature(LanguageFeature.ContextReceivers);
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tower.ImplicitScopeTower
        public LanguageVersionSettings getLanguageVersionSettings() {
            LanguageVersionSettings languageVersionSettings = this.resolutionContext.languageVersionSettings;
            Intrinsics.checkNotNullExpressionValue(languageVersionSettings, "languageVersionSettings");
            return languageVersionSettings;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tower.ImplicitScopeTower
        public Collection<FunctionDescriptor> interceptFunctionCandidates(ResolutionScope resolutionScope, Name name2, Collection<? extends FunctionDescriptor> initialResults, LookupLocation location, ReceiverValueWithSmartCastInfo dispatchReceiver, ReceiverValueWithSmartCastInfo extensionReceiver) {
            Intrinsics.checkNotNullParameter(resolutionScope, "resolutionScope");
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(initialResults, "initialResults");
            Intrinsics.checkNotNullParameter(location, "location");
            return this.candidateInterceptor.interceptFunctionCandidates(initialResults, this, this.resolutionContext, resolutionScope, this.callResolver, name2, location);
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tower.ImplicitScopeTower
        public Collection<VariableDescriptor> interceptVariableCandidates(ResolutionScope resolutionScope, Name name2, Collection<? extends VariableDescriptor> initialResults, LookupLocation location, ReceiverValueWithSmartCastInfo dispatchReceiver, ReceiverValueWithSmartCastInfo extensionReceiver) {
            Intrinsics.checkNotNullParameter(resolutionScope, "resolutionScope");
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(initialResults, "initialResults");
            Intrinsics.checkNotNullParameter(location, "location");
            return this.candidateInterceptor.interceptVariableCandidates(initialResults, this, this.resolutionContext, resolutionScope, this.callResolver, name2, location);
        }
    }

    /* JADX INFO: compiled from: NewResolutionOldInference.kt */
    @Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B7\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u0006\u0012\u0016\b\u0002\u0010\u0007\u001a\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003\u0018\u00010\b¢\u0006\u0002\u0010\tJ\u0010\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u0001H\u0016J\u000f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u0086\u0002J\r\u0010\u001e\u001a\u0006\u0012\u0002\b\u00030\u0006H\u0086\u0002R!\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00040\u00038FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000b\u0010\fR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\fR\u0014\u0010\u0010\u001a\u00020\u0011X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0012R\u0015\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u001b\u0010\u0015\u001a\u00020\u00168VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u0019\u0010\u000e\u001a\u0004\b\u0017\u0010\u0018¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/NewResolutionOldInference$MyCandidate;", "Lorg/jetbrains/kotlin/resolve/calls/tower/Candidate;", "eagerDiagnostics", "", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallDiagnostic;", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/MutableResolvedCall;", "finalDiagnosticsComputation", "Lkotlin/Function0;", "(Ljava/util/List;Lorg/jetbrains/kotlin/resolve/calls/model/MutableResolvedCall;Lkotlin/jvm/functions/Function0;)V", "diagnostics", "getDiagnostics", "()Ljava/util/List;", "diagnostics$delegate", "Lkotlin/Lazy;", "getEagerDiagnostics", "isSuccessful", "", "()Z", "getResolvedCall", "()Lorg/jetbrains/kotlin/resolve/calls/model/MutableResolvedCall;", "resultingApplicability", "Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateApplicability;", "getResultingApplicability", "()Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateApplicability;", "resultingApplicability$delegate", "addCompatibilityWarning", "", "other", "component1", "component2", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class MyCandidate implements Candidate {

        /* JADX INFO: renamed from: diagnostics$delegate, reason: from kotlin metadata */
        private final Lazy diagnostics;
        private final List<KotlinCallDiagnostic> eagerDiagnostics;
        private final boolean isSuccessful;
        private final MutableResolvedCall<?> resolvedCall;

        /* JADX INFO: renamed from: resultingApplicability$delegate, reason: from kotlin metadata */
        private final Lazy resultingApplicability;

        @Override // org.jetbrains.kotlin.resolve.calls.tower.Candidate
        public void addCompatibilityWarning(Candidate other) {
            Intrinsics.checkNotNullParameter(other, "other");
        }

        /* JADX WARN: Multi-variable type inference failed */
        public MyCandidate(List<? extends KotlinCallDiagnostic> eagerDiagnostics, MutableResolvedCall<?> resolvedCall, final Function0<? extends List<? extends KotlinCallDiagnostic>> function0) {
            Intrinsics.checkNotNullParameter(eagerDiagnostics, "eagerDiagnostics");
            Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
            this.eagerDiagnostics = eagerDiagnostics;
            this.resolvedCall = resolvedCall;
            this.diagnostics = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<List<? extends KotlinCallDiagnostic>>() { // from class: org.jetbrains.kotlin.resolve.calls.tower.NewResolutionOldInference$MyCandidate$diagnostics$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final List<? extends KotlinCallDiagnostic> invoke() {
                    List<KotlinCallDiagnostic> listInvoke;
                    Function0<List<KotlinCallDiagnostic>> function1 = function0;
                    return (function1 == null || (listInvoke = function1.invoke()) == null) ? this.getEagerDiagnostics() : listInvoke;
                }
            });
            this.resultingApplicability = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<CandidateApplicability>() { // from class: org.jetbrains.kotlin.resolve.calls.tower.NewResolutionOldInference$MyCandidate$resultingApplicability$2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final CandidateApplicability invoke() {
                    return ImplicitScopeTowerKt.getResultApplicabilityForCallDiagnostics(this.this$0.getDiagnostics());
                }
            });
            this.isSuccessful = CandidateApplicabilityKt.isSuccess(ImplicitScopeTowerKt.getResultApplicabilityForCallDiagnostics(eagerDiagnostics));
        }

        public /* synthetic */ MyCandidate(List list, MutableResolvedCall mutableResolvedCall, Function0 function0, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(list, mutableResolvedCall, (i & 4) != 0 ? null : function0);
        }

        public final List<KotlinCallDiagnostic> getEagerDiagnostics() {
            return this.eagerDiagnostics;
        }

        public final MutableResolvedCall<?> getResolvedCall() {
            return this.resolvedCall;
        }

        public final List<KotlinCallDiagnostic> getDiagnostics() {
            return (List) this.diagnostics.getValue();
        }

        public final List<KotlinCallDiagnostic> component1() {
            return getDiagnostics();
        }

        public final MutableResolvedCall<?> component2() {
            return this.resolvedCall;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tower.Candidate
        public CandidateApplicability getResultingApplicability() {
            return (CandidateApplicability) this.resultingApplicability.getValue();
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tower.Candidate
        /* JADX INFO: renamed from: isSuccessful, reason: from getter */
        public boolean getIsSuccessful() {
            return this.isSuccessful;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: NewResolutionOldInference.kt */
    @Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0082\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u001d\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u001e\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u00112\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J&\u0010\u0017\u001a\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001d0\u0011H\u0016J\"\u0010\u0017\u001a\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001dH\u0016J$\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00120\u00112\u0006\u0010\u0018\u001a\u00020\u00192\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00160!H\u0002J\b\u0010\"\u001a\u00020\u0002H\u0016R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006#"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/NewResolutionOldInference$CandidateFactoryImpl;", "Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateFactory;", "Lorg/jetbrains/kotlin/resolve/calls/tower/NewResolutionOldInference$MyCandidate;", "name", "Lorg/jetbrains/kotlin/name/Name;", "basicCallContext", "Lorg/jetbrains/kotlin/resolve/calls/context/BasicCallResolutionContext;", "tracing", "Lorg/jetbrains/kotlin/resolve/calls/tasks/TracingStrategy;", "(Lorg/jetbrains/kotlin/resolve/calls/tower/NewResolutionOldInference;Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/resolve/calls/context/BasicCallResolutionContext;Lorg/jetbrains/kotlin/resolve/calls/tasks/TracingStrategy;)V", "getBasicCallContext", "()Lorg/jetbrains/kotlin/resolve/calls/context/BasicCallResolutionContext;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "getTracing", "()Lorg/jetbrains/kotlin/resolve/calls/tasks/TracingStrategy;", "checkInfixAndOperator", "", "Lorg/jetbrains/kotlin/resolve/calls/tower/ResolutionDiagnostic;", "call", "Lorg/jetbrains/kotlin/psi/Call;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "createCandidate", "towerCandidate", "Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateWithBoundDispatchReceiver;", "explicitReceiverKind", "Lorg/jetbrains/kotlin/resolve/calls/tasks/ExplicitReceiverKind;", "extensionReceiverCandidates", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;", "extensionReceiver", "createDiagnosticsForCandidate", "candidateCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCallImpl;", "createErrorCandidate", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    final class CandidateFactoryImpl implements CandidateFactory<MyCandidate> {
        private final BasicCallResolutionContext basicCallContext;
        private final Name name;
        final /* synthetic */ NewResolutionOldInference this$0;
        private final TracingStrategy tracing;

        public CandidateFactoryImpl(NewResolutionOldInference newResolutionOldInference, Name name2, BasicCallResolutionContext basicCallContext, TracingStrategy tracing) {
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(basicCallContext, "basicCallContext");
            Intrinsics.checkNotNullParameter(tracing, "tracing");
            this.this$0 = newResolutionOldInference;
            this.name = name2;
            this.basicCallContext = basicCallContext;
            this.tracing = tracing;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tower.CandidateFactory
        public /* bridge */ /* synthetic */ Candidate createCandidate(CandidateWithBoundDispatchReceiver candidateWithBoundDispatchReceiver, ExplicitReceiverKind explicitReceiverKind, List list) {
            return createCandidate(candidateWithBoundDispatchReceiver, explicitReceiverKind, (List<ReceiverValueWithSmartCastInfo>) list);
        }

        public final Name getName() {
            return this.name;
        }

        public final BasicCallResolutionContext getBasicCallContext() {
            return this.basicCallContext;
        }

        public final TracingStrategy getTracing() {
            return this.tracing;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tower.CandidateFactory
        public MyCandidate createCandidate(final CandidateWithBoundDispatchReceiver towerCandidate, ExplicitReceiverKind explicitReceiverKind, ReceiverValueWithSmartCastInfo extensionReceiver) {
            Intrinsics.checkNotNullParameter(towerCandidate, "towerCandidate");
            Intrinsics.checkNotNullParameter(explicitReceiverKind, "explicitReceiverKind");
            TemporaryBindingTrace temporaryBindingTraceCreate = TemporaryBindingTrace.create(this.basicCallContext.trace, "Context for resolve candidate");
            Intrinsics.checkNotNullExpressionValue(temporaryBindingTraceCreate, "create(...)");
            Call call = this.basicCallContext.call;
            CallableDescriptor descriptor = towerCandidate.getDescriptor();
            ReceiverValueWithSmartCastInfo dispatchReceiver = towerCandidate.getDispatchReceiver();
            final ResolvedCallImpl<CallableDescriptor> resolvedCallImpl = new ResolvedCallImpl<>(call, descriptor, dispatchReceiver != null ? dispatchReceiver.getReceiverValue() : null, extensionReceiver != null ? extensionReceiver.getReceiverValue() : null, explicitReceiverKind, null, temporaryBindingTraceCreate, this.tracing, this.basicCallContext.dataFlowInfoForArguments);
            if (extensionReceiver != null) {
                ReceiverParameterDescriptor extensionReceiverParameter = towerCandidate.getDescriptor().getExtensionReceiverParameter();
                Intrinsics.checkNotNull(extensionReceiverParameter);
                KotlinType type = extensionReceiverParameter.getValue().getType();
                Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                boolean zIsDynamic = DynamicTypesKt.isDynamic(type);
                KotlinType type2 = extensionReceiver.getReceiverValue().getType();
                Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
                if (zIsDynamic != DynamicTypesKt.isDynamic(type2) || (zIsDynamic && !AnnotationsForResolveUtilsKt.hasDynamicExtensionAnnotation(towerCandidate.getDescriptor()))) {
                    return new MyCandidate(CollectionsKt.listOf(HiddenExtensionRelatedToDynamicTypes.INSTANCE), resolvedCallImpl, null, 4, null);
                }
            }
            if (DeprecationResolver.isHiddenInResolution$default(this.this$0.deprecationResolver, towerCandidate.getDescriptor(), this.basicCallContext.call, this.basicCallContext.trace.getContext(), NewResolutionOldInferenceKt.isSuperCall(this.basicCallContext), false, 16, null)) {
                return new MyCandidate(CollectionsKt.listOf(HiddenDescriptor.INSTANCE), resolvedCallImpl, null, 4, null);
            }
            ResolvedCallImpl<CallableDescriptor> resolvedCallImpl2 = resolvedCallImpl;
            BasicCallResolutionContext basicCallResolutionContext = this.basicCallContext;
            CallCandidateResolutionContext callCandidateResolutionContextCreate = CallCandidateResolutionContext.create(resolvedCallImpl2, basicCallResolutionContext, temporaryBindingTraceCreate, this.tracing, basicCallResolutionContext.call, CandidateResolveMode.EXIT_ON_FIRST_ERROR);
            CandidateResolver candidateResolver = this.this$0.candidateResolver;
            Intrinsics.checkNotNull(callCandidateResolutionContextCreate);
            CheckArgumentTypesMode checkArguments = this.basicCallContext.checkArguments;
            Intrinsics.checkNotNullExpressionValue(checkArguments, "checkArguments");
            candidateResolver.performResolutionForCandidateCall(callCandidateResolutionContextCreate, checkArguments);
            return new MyCandidate(createDiagnosticsForCandidate(towerCandidate, resolvedCallImpl), resolvedCallImpl2, new Function0<List<? extends KotlinCallDiagnostic>>() { // from class: org.jetbrains.kotlin.resolve.calls.tower.NewResolutionOldInference$CandidateFactoryImpl$createCandidate$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final List<? extends KotlinCallDiagnostic> invoke() {
                    resolvedCallImpl.performRemainingTasks();
                    return this.createDiagnosticsForCandidate(towerCandidate, resolvedCallImpl);
                }
            });
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tower.CandidateFactory
        public MyCandidate createCandidate(CandidateWithBoundDispatchReceiver towerCandidate, ExplicitReceiverKind explicitReceiverKind, List<ReceiverValueWithSmartCastInfo> extensionReceiverCandidates) {
            Intrinsics.checkNotNullParameter(towerCandidate, "towerCandidate");
            Intrinsics.checkNotNullParameter(explicitReceiverKind, "explicitReceiverKind");
            Intrinsics.checkNotNullParameter(extensionReceiverCandidates, "extensionReceiverCandidates");
            throw new IllegalStateException((Reflection.getOrCreateKotlinClass(getClass()).getSimpleName() + " doesn't support candidates with multiple extension receiver candidates").toString());
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tower.CandidateFactory
        public MyCandidate createErrorCandidate() {
            throw new IllegalStateException("Not supported creating error candidate for the old type inference candidate factory");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final List<ResolutionDiagnostic> createDiagnosticsForCandidate(CandidateWithBoundDispatchReceiver towerCandidate, ResolvedCallImpl<CallableDescriptor> candidateCall) {
            ArrayList arrayList = new ArrayList();
            arrayList.addAll(towerCandidate.getDiagnostics());
            Call call = this.basicCallContext.call;
            Intrinsics.checkNotNullExpressionValue(call, "call");
            arrayList.addAll(checkInfixAndOperator(call, towerCandidate.getDescriptor()));
            ResolutionStatus status = candidateCall.getStatus();
            Intrinsics.checkNotNullExpressionValue(status, "getStatus(...)");
            org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(arrayList, NewResolutionOldInferenceKt.createPreviousResolveError(status));
            return arrayList;
        }

        private final List<ResolutionDiagnostic> checkInfixAndOperator(Call call, CallableDescriptor descriptor) {
            if (!(descriptor instanceof FunctionDescriptor) || ErrorUtils.isError(descriptor)) {
                return CollectionsKt.emptyList();
            }
            FunctionDescriptor functionDescriptor = (FunctionDescriptor) descriptor;
            if (!Intrinsics.areEqual(functionDescriptor.getShortName(), this.name) && (Intrinsics.areEqual(this.name, OperatorNameConventions.UNARY_PLUS) || Intrinsics.areEqual(this.name, OperatorNameConventions.UNARY_MINUS))) {
                return CollectionsKt.listOf(DeprecatedUnaryPlusAsPlus.INSTANCE);
            }
            InfixCallNoInfixModifier infixCallNoInfixModifier = null;
            InvokeConventionCallNoOperatorModifier invokeConventionCallNoOperatorModifier = (!CallResolverUtilKt.isConventionCall(call) || functionDescriptor.isOperator()) ? null : InvokeConventionCallNoOperatorModifier.INSTANCE;
            if (CallResolverUtilKt.isInfixCall(call) && !functionDescriptor.isInfix()) {
                infixCallNoInfixModifier = InfixCallNoInfixModifier.INSTANCE;
            }
            return CollectionsKt.listOfNotNull((Object[]) new ResolutionDiagnostic[]{invokeConventionCallNoOperatorModifier, infixCallNoInfixModifier});
        }
    }

    /* JADX INFO: compiled from: NewResolutionOldInference.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u0082\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0011\u0012\n\u0010\u0003\u001a\u00060\u0004R\u00020\u0005¢\u0006\u0002\u0010\u0006J,\u0010\t\u001a\u0016\u0012\u0004\u0012\u00020\u000b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00020\f\u0018\u00010\n2\u0006\u0010\r\u001a\u00020\u00022\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0016\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00020\f2\u0006\u0010\u0011\u001a\u00020\u000fH\u0016J\u0018\u0010\u0012\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u00022\u0006\u0010\u0013\u001a\u00020\u0002H\u0016R\u0015\u0010\u0003\u001a\u00060\u0004R\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/NewResolutionOldInference$CandidateFactoryProviderForInvokeImpl;", "Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateFactoryProviderForInvoke;", "Lorg/jetbrains/kotlin/resolve/calls/tower/NewResolutionOldInference$MyCandidate;", "functionContext", "Lorg/jetbrains/kotlin/resolve/calls/tower/NewResolutionOldInference$CandidateFactoryImpl;", "Lorg/jetbrains/kotlin/resolve/calls/tower/NewResolutionOldInference;", "(Lorg/jetbrains/kotlin/resolve/calls/tower/NewResolutionOldInference;Lorg/jetbrains/kotlin/resolve/calls/tower/NewResolutionOldInference$CandidateFactoryImpl;)V", "getFunctionContext", "()Lorg/jetbrains/kotlin/resolve/calls/tower/NewResolutionOldInference$CandidateFactoryImpl;", "factoryForInvoke", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;", "Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateFactory;", "variable", "useExplicitReceiver", "", "factoryForVariable", "stripExplicitReceiver", "transformCandidate", "invoke", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class CandidateFactoryProviderForInvokeImpl implements CandidateFactoryProviderForInvoke<MyCandidate> {
        private final CandidateFactoryImpl functionContext;
        final /* synthetic */ NewResolutionOldInference this$0;

        public CandidateFactoryProviderForInvokeImpl(NewResolutionOldInference newResolutionOldInference, CandidateFactoryImpl functionContext) {
            Intrinsics.checkNotNullParameter(functionContext, "functionContext");
            this.this$0 = newResolutionOldInference;
            this.functionContext = functionContext;
        }

        public final CandidateFactoryImpl getFunctionContext() {
            return this.functionContext;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tower.CandidateFactoryProviderForInvoke
        public MyCandidate transformCandidate(final MyCandidate variable, final MyCandidate invoke) {
            Intrinsics.checkNotNullParameter(variable, "variable");
            Intrinsics.checkNotNullParameter(invoke, "invoke");
            MutableResolvedCall<?> resolvedCall = invoke.getResolvedCall();
            Intrinsics.checkNotNull(resolvedCall, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.calls.model.MutableResolvedCall<org.jetbrains.kotlin.descriptors.FunctionDescriptor>");
            MutableResolvedCall<?> resolvedCall2 = variable.getResolvedCall();
            Intrinsics.checkNotNull(resolvedCall2, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.calls.model.MutableResolvedCall<org.jetbrains.kotlin.descriptors.VariableDescriptor>");
            VariableAsFunctionResolvedCallImpl variableAsFunctionResolvedCallImpl = new VariableAsFunctionResolvedCallImpl(resolvedCall, resolvedCall2);
            CandidateApplicabilityKt.isSuccess(variable.getResultingApplicability());
            return new MyCandidate(CollectionsKt.plus((Collection) variable.getEagerDiagnostics(), (Iterable) invoke.getEagerDiagnostics()), variableAsFunctionResolvedCallImpl, new Function0<List<? extends KotlinCallDiagnostic>>() { // from class: org.jetbrains.kotlin.resolve.calls.tower.NewResolutionOldInference$CandidateFactoryProviderForInvokeImpl$transformCandidate$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final List<? extends KotlinCallDiagnostic> invoke() {
                    return CollectionsKt.plus((Collection) variable.getDiagnostics(), (Iterable) invoke.getDiagnostics());
                }
            });
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tower.CandidateFactoryProviderForInvoke
        public CandidateFactory<MyCandidate> factoryForVariable(boolean stripExplicitReceiver) {
            Call callStripCallArguments = CallTransformer.stripCallArguments(this.functionContext.getBasicCallContext().call);
            if (stripExplicitReceiver) {
                callStripCallArguments = CallTransformer.stripReceiver(callStripCallArguments);
            }
            NewResolutionOldInference newResolutionOldInference = this.this$0;
            Name name2 = this.functionContext.getName();
            BasicCallResolutionContext basicCallResolutionContextReplaceCall = this.functionContext.getBasicCallContext().replaceCall(callStripCallArguments);
            Intrinsics.checkNotNullExpressionValue(basicCallResolutionContextReplaceCall, "replaceCall(...)");
            return new CandidateFactoryImpl(newResolutionOldInference, name2, basicCallResolutionContextReplaceCall, this.functionContext.getTracing());
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.jetbrains.kotlin.resolve.calls.tower.CandidateFactoryProviderForInvoke
        public Pair<ReceiverValueWithSmartCastInfo, CandidateFactory<MyCandidate>> factoryForInvoke(MyCandidate variable, boolean useExplicitReceiver) {
            Intrinsics.checkNotNullParameter(variable, "variable");
            variable.getResolvedCall().getStatus().possibleTransformToSuccess();
            KtExpression calleeExpression = variable.getResolvedCall().getCall().getCalleeExpression();
            Object resultingDescriptor = variable.getResolvedCall().getResultingDescriptor();
            Intrinsics.checkNotNull(resultingDescriptor, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.VariableDescriptor");
            variable.getResolvedCall().getStatus().possibleTransformToSuccess();
            KotlinType type = ((VariableDescriptor) resultingDescriptor).getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            Receiver receiver = null;
            if ((type instanceof DeferredType) && ((DeferredType) type).isComputing()) {
                return null;
            }
            BasicCallResolutionContext basicCallContext = this.functionContext.getBasicCallContext();
            ExpressionReceiver.Companion companion = ExpressionReceiver.INSTANCE;
            Intrinsics.checkNotNull(calleeExpression);
            BindingContext context = basicCallContext.trace.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
            ExpressionReceiver expressionReceiverCreate = companion.create(calleeExpression, type, context);
            this.functionContext.getTracing().bindReference(variable.getResolvedCall().getTrace(), variable.getResolvedCall());
            Receiver explicitReceiver = basicCallContext.call.getExplicitReceiver();
            if (explicitReceiver != null && useExplicitReceiver) {
                receiver = explicitReceiver;
            }
            CallTransformer.CallForImplicitInvoke callForImplicitInvoke = new CallTransformer.CallForImplicitInvoke(receiver, expressionReceiverCreate, basicCallContext.call, true);
            TracingStrategyForInvoke tracingStrategyForInvoke = new TracingStrategyForInvoke(calleeExpression, callForImplicitInvoke, expressionReceiverCreate.getType());
            Context contextReplaceContextDependency = ((BasicCallResolutionContext) basicCallContext.replaceBindingTrace(variable.getResolvedCall().getTrace())).replaceCall(callForImplicitInvoke).replaceContextDependency(ContextDependency.DEPENDENT);
            Intrinsics.checkNotNullExpressionValue(contextReplaceContextDependency, "replaceContextDependency(...)");
            BasicCallResolutionContext basicCallResolutionContext = (BasicCallResolutionContext) contextReplaceContextDependency;
            return TuplesKt.to(NewResolutionOldInferenceKt.transformToReceiverWithSmartCastInfo(basicCallResolutionContext, expressionReceiverCreate), new CandidateFactoryImpl(this.this$0, OperatorNameConventions.INVOKE, basicCallResolutionContext, tracingStrategyForInvoke));
        }
    }
}
