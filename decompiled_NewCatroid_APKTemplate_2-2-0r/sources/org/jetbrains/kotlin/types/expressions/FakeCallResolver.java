package org.jetbrains.kotlin.types.expressions;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.diagnostics.ParametrizedDiagnostic;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtPsiFactory;
import org.jetbrains.kotlin.psi.KtReferenceExpression;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.TemporaryBindingTrace;
import org.jetbrains.kotlin.resolve.calls.CallResolver;
import org.jetbrains.kotlin.resolve.calls.context.ResolutionContext;
import org.jetbrains.kotlin.resolve.calls.inference.InferenceErrorData;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.results.OverloadResolutionResults;
import org.jetbrains.kotlin.resolve.calls.tasks.AbstractTracingStrategy;
import org.jetbrains.kotlin.resolve.calls.tasks.ExplicitReceiverKind;
import org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy;
import org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategyImpl;
import org.jetbrains.kotlin.resolve.calls.util.CallMaker;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: FakeCallResolver.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001:\u0003 !\"B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006Jp\u0010\u0007\u001a\u0014\u0012\u0004\u0012\u00020\t\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\b2\b\u0010\f\u001a\u0004\u0018\u00010\r2\n\u0010\u000e\u001a\u0006\u0012\u0002\b\u00030\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u00112\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00122\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u00172\u0014\b\u0002\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020\u001b0\u0019H\u0007JP\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\n\u0010\u000e\u001a\u0006\u0012\u0002\b\u00030\u000f2\b\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00122\u0006\u0010\u001d\u001a\u00020\u00122\u0006\u0010\u001e\u001a\u00020\u001f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006#"}, d2 = {"Lorg/jetbrains/kotlin/types/expressions/FakeCallResolver;", "", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "callResolver", "Lorg/jetbrains/kotlin/resolve/calls/CallResolver;", "(Lcom/intellij/openapi/project/Project;Lorg/jetbrains/kotlin/resolve/calls/CallResolver;)V", "makeAndResolveFakeCallInContext", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/psi/Call;", "Lorg/jetbrains/kotlin/resolve/calls/results/OverloadResolutionResults;", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "receiver", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "context", "Lorg/jetbrains/kotlin/resolve/calls/context/ResolutionContext;", "valueArguments", "", "Lorg/jetbrains/kotlin/psi/KtExpression;", "name", "Lorg/jetbrains/kotlin/name/Name;", "callElement", "realExpression", "Lorg/jetbrains/kotlin/types/expressions/FakeCallResolver$RealExpression;", "onComplete", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/psi/KtSimpleNameExpression;", "", "resolveFakeCall", "reportErrorsOn", "callKind", "Lorg/jetbrains/kotlin/types/expressions/FakeCallKind;", "RealExpression", "TracingStrategyForComponentCall", "TracingStrategyForIteratorCall", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FakeCallResolver {
    private final CallResolver callResolver;
    private final Project project;

    /* JADX INFO: compiled from: FakeCallResolver.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[FakeCallKind.values().length];
            try {
                iArr[FakeCallKind.ITERATOR.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[FakeCallKind.COMPONENT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[FakeCallKind.OTHER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public final Pair<Call, OverloadResolutionResults<FunctionDescriptor>> makeAndResolveFakeCallInContext(ReceiverValue receiverValue, ResolutionContext<?> context, List<? extends KtExpression> valueArguments, Name name2, KtExpression callElement) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(valueArguments, "valueArguments");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(callElement, "callElement");
        return makeAndResolveFakeCallInContext$default(this, receiverValue, context, valueArguments, name2, callElement, null, null, 96, null);
    }

    public final Pair<Call, OverloadResolutionResults<FunctionDescriptor>> makeAndResolveFakeCallInContext(ReceiverValue receiverValue, ResolutionContext<?> context, List<? extends KtExpression> valueArguments, Name name2, KtExpression callElement, RealExpression realExpression) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(valueArguments, "valueArguments");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(callElement, "callElement");
        return makeAndResolveFakeCallInContext$default(this, receiverValue, context, valueArguments, name2, callElement, realExpression, null, 64, null);
    }

    public FakeCallResolver(Project project, CallResolver callResolver) {
        Intrinsics.checkNotNullParameter(project, "project");
        Intrinsics.checkNotNullParameter(callResolver, "callResolver");
        this.project = project;
        this.callResolver = callResolver;
    }

    public final OverloadResolutionResults<FunctionDescriptor> resolveFakeCall(ResolutionContext<?> context, ReceiverValue receiver, Name name2, KtExpression callElement, KtExpression reportErrorsOn, FakeCallKind callKind, List<? extends KtExpression> valueArguments) {
        ParametrizedDiagnostic parametrizedDiagnosticOn;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(callElement, "callElement");
        Intrinsics.checkNotNullParameter(reportErrorsOn, "reportErrorsOn");
        Intrinsics.checkNotNullParameter(callKind, "callKind");
        Intrinsics.checkNotNullParameter(valueArguments, "valueArguments");
        TemporaryBindingTrace temporaryBindingTraceCreate = TemporaryBindingTrace.create(context.trace, "trace to resolve fake call for", name2);
        Intrinsics.checkNotNullExpressionValue(temporaryBindingTraceCreate, "create(...)");
        ResolutionContext<?> resolutionContextReplaceBindingTrace = context.replaceBindingTrace(temporaryBindingTraceCreate);
        Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        RealExpression realExpression = new RealExpression(reportErrorsOn, callKind);
        Intrinsics.checkNotNull(resolutionContextReplaceBindingTrace);
        OverloadResolutionResults<FunctionDescriptor> second = makeAndResolveFakeCallInContext(receiver, resolutionContextReplaceBindingTrace, valueArguments, name2, callElement, realExpression, new FakeCallResolver$resolveFakeCall$result$1(booleanRef, temporaryBindingTraceCreate)).getSecond();
        if (booleanRef.element) {
            int i = WhenMappings.$EnumSwitchMapping$0[callKind.ordinal()];
            if (i != 1) {
                parametrizedDiagnosticOn = null;
                if (i != 2) {
                    if (i != 3) {
                        throw new NoWhenBranchMatchedException();
                    }
                } else if (receiver != null) {
                    parametrizedDiagnosticOn = Errors.COMPONENT_FUNCTION_MISSING.on(reportErrorsOn, name2, receiver.getType());
                }
            } else {
                parametrizedDiagnosticOn = Errors.ITERATOR_MISSING.on(reportErrorsOn);
            }
            if (parametrizedDiagnosticOn != null) {
                context.trace.report(parametrizedDiagnosticOn);
            }
        }
        return second;
    }

    /* JADX INFO: compiled from: FakeCallResolver.kt */
    @Metadata(d1 = {"\u0000¦\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u001f\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0013\u0010\u0011\u001a\u00020\u00122\b\b\u0001\u0010\u0013\u001a\u00020\u0014H\u0096\u0001J0\u0010\u0015\u001a\u00020\u0012\"\n\b\u0000\u0010\u0016*\u0004\u0018\u00010\u00172\u0006\u0010\u0013\u001a\u00020\u00142\u0012\u0010\u0018\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00160\u001a0\u0019H\u0016J\u001d\u0010\u001b\u001a\u00020\u00122\b\b\u0001\u0010\u0013\u001a\u00020\u00142\b\b\u0001\u0010\b\u001a\u00020\tH\u0096\u0001J=\u0010\u001c\u001a\u00020\u0012\"\u0010\b\u0000\u0010\u0016*\n \u001d*\u0004\u0018\u00010\u00170\u00172\b\b\u0001\u0010\u0013\u001a\u00020\u00142\u0016\b\u0001\u0010\u001e\u001a\u0010\u0012\f\u0012\n \u001d*\u0004\u0018\u0001H\u0016H\u00160\u001aH\u0096\u0001J=\u0010\u001f\u001a\u00020\u0012\"\u0010\b\u0000\u0010\u0016*\n \u001d*\u0004\u0018\u00010\u00170\u00172\b\b\u0001\u0010\u0013\u001a\u00020\u00142\u0016\b\u0001\u0010\u001e\u001a\u0010\u0012\f\u0012\n \u001d*\u0004\u0018\u0001H\u0016H\u00160\u001aH\u0096\u0001J\u008b\u0001\u0010 \u001a\u00020\u0012\"\u0010\b\u0000\u0010\u0016*\n \u001d*\u0004\u0018\u00010\u00170\u00172\b\b\u0001\u0010\u0013\u001a\u00020\u00142d\b\u0001\u0010!\u001a^\u0012*\b\u0001\u0012&\u0012\f\u0012\n \u001d*\u0004\u0018\u0001H\u0016H\u0016 \u001d*\u0012\u0012\f\u0012\n \u001d*\u0004\u0018\u0001H\u0016H\u0016\u0018\u00010\u001a0\u001a \u001d*,\u0012(\u0012&\u0012\f\u0012\n \u001d*\u0004\u0018\u0001H\u0016H\u0016 \u001d*\u0012\u0012\f\u0012\n \u001d*\u0004\u0018\u0001H\u0016H\u0016\u0018\u00010\u001a0\u001a0\u00190\"H\u0096\u0001J\u0013\u0010#\u001a\u00020\u00122\b\b\u0001\u0010\u0013\u001a\u00020\u0014H\u0096\u0001J\u001d\u0010$\u001a\u00020\u00122\b\b\u0001\u0010\u0013\u001a\u00020\u00142\b\b\u0001\u0010%\u001a\u00020&H\u0096\u0001J\u001d\u0010'\u001a\u00020\u00122\b\b\u0001\u0010\u0013\u001a\u00020\u00142\b\b\u0001\u0010(\u001a\u00020)H\u0096\u0001J'\u0010*\u001a\u00020\u00122\b\b\u0001\u0010\u0013\u001a\u00020\u00142\b\b\u0001\u0010+\u001a\u00020,2\b\b\u0001\u0010-\u001a\u00020.H\u0096\u0001J\u0013\u0010/\u001a\u00020\u00122\b\b\u0001\u0010\u0013\u001a\u00020\u0014H\u0096\u0001J\u001d\u00100\u001a\u00020\u00122\b\b\u0001\u0010\u0013\u001a\u00020\u00142\b\b\u0001\u00101\u001a\u000202H\u0096\u0001J\u008b\u0001\u00103\u001a\u00020\u0012\"\u0010\b\u0000\u0010\u0016*\n \u001d*\u0004\u0018\u00010\u00170\u00172\b\b\u0001\u0010\u0013\u001a\u00020\u00142d\b\u0001\u0010!\u001a^\u0012*\b\u0001\u0012&\u0012\f\u0012\n \u001d*\u0004\u0018\u0001H\u0016H\u0016 \u001d*\u0012\u0012\f\u0012\n \u001d*\u0004\u0018\u0001H\u0016H\u0016\u0018\u00010\u001a0\u001a \u001d*,\u0012(\u0012&\u0012\f\u0012\n \u001d*\u0004\u0018\u0001H\u0016H\u0016 \u001d*\u0012\u0012\f\u0012\n \u001d*\u0004\u0018\u0001H\u0016H\u0016\u0018\u00010\u001a0\u001a0\u00190\"H\u0096\u0001J\u008b\u0001\u00104\u001a\u00020\u0012\"\u0010\b\u0000\u0010\u0016*\n \u001d*\u0004\u0018\u00010\u00170\u00172\b\b\u0001\u0010\u0013\u001a\u00020\u00142d\b\u0001\u00105\u001a^\u0012*\b\u0001\u0012&\u0012\f\u0012\n \u001d*\u0004\u0018\u0001H\u0016H\u0016 \u001d*\u0012\u0012\f\u0012\n \u001d*\u0004\u0018\u0001H\u0016H\u0016\u0018\u00010\u001a0\u001a \u001d*,\u0012(\u0012&\u0012\f\u0012\n \u001d*\u0004\u0018\u0001H\u0016H\u0016 \u001d*\u0012\u0012\f\u0012\n \u001d*\u0004\u0018\u0001H\u0016H\u0016\u0018\u00010\u001a0\u001a0\u00190\"H\u0096\u0001J%\u00106\u001a\u00020\u00122\b\b\u0001\u0010\u0013\u001a\u00020\u00142\b\b\u0001\u00107\u001a\u0002082\u0006\u00109\u001a\u00020:H\u0096\u0001J\u001c\u0010;\u001a\u00020\u00122\n\u0010<\u001a\u0006\u0012\u0002\b\u00030=2\u0006\u0010>\u001a\u00020?H\u0016J\u0013\u0010@\u001a\u00020\u00122\b\b\u0001\u0010\u0013\u001a\u00020\u0014H\u0096\u0001J\u008b\u0001\u0010A\u001a\u00020\u0012\"\u0010\b\u0000\u0010\u0016*\n \u001d*\u0004\u0018\u00010\u00170\u00172\b\b\u0001\u0010\u0013\u001a\u00020\u00142d\b\u0001\u00105\u001a^\u0012*\b\u0001\u0012&\u0012\f\u0012\n \u001d*\u0004\u0018\u0001H\u0016H\u0016 \u001d*\u0012\u0012\f\u0012\n \u001d*\u0004\u0018\u0001H\u0016H\u0016\u0018\u00010\u001a0\u001a \u001d*,\u0012(\u0012&\u0012\f\u0012\n \u001d*\u0004\u0018\u0001H\u0016H\u0016 \u001d*\u0012\u0012\f\u0012\n \u001d*\u0004\u0018\u0001H\u0016H\u0016\u0018\u00010\u001a0\u001a0\u00190\"H\u0096\u0001J \u0010B\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010C\u001a\u00020D2\u0006\u0010E\u001a\u00020:H\u0016J%\u0010F\u001a\u00020\u00122\b\b\u0001\u0010\u0013\u001a\u00020\u00142\u0006\u0010G\u001a\u00020H2\b\b\u0001\u0010%\u001a\u00020\u0017H\u0096\u0001J5\u0010I\u001a\u00020\u00122\b\b\u0001\u0010\u0013\u001a\u00020\u00142\b\b\u0001\u0010J\u001a\u00020)2\b\b\u0001\u0010K\u001a\u00020L2\f\b\u0001\u0010M\u001a\u0006\u0012\u0002\b\u00030=H\u0096\u0001R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006N"}, d2 = {"Lorg/jetbrains/kotlin/types/expressions/FakeCallResolver$TracingStrategyForComponentCall;", "Lorg/jetbrains/kotlin/resolve/calls/tasks/TracingStrategy;", "fakeExpression", "Lorg/jetbrains/kotlin/psi/KtReferenceExpression;", "reportErrorsOn", "Lorg/jetbrains/kotlin/psi/KtExpression;", "name", "Lorg/jetbrains/kotlin/name/Name;", "call", "Lorg/jetbrains/kotlin/psi/Call;", "(Lorg/jetbrains/kotlin/psi/KtReferenceExpression;Lorg/jetbrains/kotlin/psi/KtExpression;Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/psi/Call;)V", "getCall", "()Lorg/jetbrains/kotlin/psi/Call;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "getReportErrorsOn", "()Lorg/jetbrains/kotlin/psi/KtExpression;", "abstractSuperCall", "", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "ambiguity", "D", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "resolvedCalls", "", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "bindCall", "bindReference", JvmProtoBufUtil.PLATFORM_TYPE_ID, "resolvedCall", "bindResolvedCall", "cannotCompleteResolve", "descriptors", "", "instantiationOfAbstractClass", "invisibleMember", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorWithVisibility;", "missingReceiver", "expectedReceiver", "Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "nestedClassAccessViaInstanceReference", "classDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "explicitReceiverKind", "Lorg/jetbrains/kotlin/resolve/calls/tasks/ExplicitReceiverKind;", "noReceiverAllowed", "noValueForParameter", "valueParameter", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "noneApplicable", "recordAmbiguity", "candidates", "recursiveType", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "insideAugmentedAssignment", "", "typeInferenceFailed", "context", "Lorg/jetbrains/kotlin/resolve/calls/context/ResolutionContext;", "inferenceErrorData", "Lorg/jetbrains/kotlin/resolve/calls/inference/InferenceErrorData;", "unresolvedReference", "unresolvedReferenceWrongReceiver", "unsafeCall", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "isCallForImplicitInvoke", "wrongNumberOfTypeArguments", "expectedTypeArgumentCount", "", "wrongReceiverType", "receiverParameter", "receiverArgument", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "c", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class TracingStrategyForComponentCall implements TracingStrategy {
        private final /* synthetic */ TracingStrategy $$delegate_0;
        private final Call call;
        private final Name name;
        private final KtExpression reportErrorsOn;

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public void abstractSuperCall(BindingTrace trace) {
            Intrinsics.checkNotNullParameter(trace, "trace");
            this.$$delegate_0.abstractSuperCall(trace);
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public void bindCall(BindingTrace trace, Call call) {
            Intrinsics.checkNotNullParameter(trace, "trace");
            Intrinsics.checkNotNullParameter(call, "call");
            this.$$delegate_0.bindCall(trace, call);
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public <D extends CallableDescriptor> void bindReference(BindingTrace trace, ResolvedCall<D> resolvedCall) {
            Intrinsics.checkNotNullParameter(trace, "trace");
            Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
            this.$$delegate_0.bindReference(trace, resolvedCall);
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public <D extends CallableDescriptor> void bindResolvedCall(BindingTrace trace, ResolvedCall<D> resolvedCall) {
            Intrinsics.checkNotNullParameter(trace, "trace");
            Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
            this.$$delegate_0.bindResolvedCall(trace, resolvedCall);
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public <D extends CallableDescriptor> void cannotCompleteResolve(BindingTrace trace, Collection<? extends ResolvedCall<D>> descriptors) {
            Intrinsics.checkNotNullParameter(trace, "trace");
            Intrinsics.checkNotNullParameter(descriptors, "descriptors");
            this.$$delegate_0.cannotCompleteResolve(trace, descriptors);
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public void instantiationOfAbstractClass(BindingTrace trace) {
            Intrinsics.checkNotNullParameter(trace, "trace");
            this.$$delegate_0.instantiationOfAbstractClass(trace);
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public void invisibleMember(BindingTrace trace, DeclarationDescriptorWithVisibility descriptor) {
            Intrinsics.checkNotNullParameter(trace, "trace");
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            this.$$delegate_0.invisibleMember(trace, descriptor);
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public void missingReceiver(BindingTrace trace, ReceiverParameterDescriptor expectedReceiver) {
            Intrinsics.checkNotNullParameter(trace, "trace");
            Intrinsics.checkNotNullParameter(expectedReceiver, "expectedReceiver");
            this.$$delegate_0.missingReceiver(trace, expectedReceiver);
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public void nestedClassAccessViaInstanceReference(BindingTrace trace, ClassDescriptor classDescriptor, ExplicitReceiverKind explicitReceiverKind) {
            Intrinsics.checkNotNullParameter(trace, "trace");
            Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
            Intrinsics.checkNotNullParameter(explicitReceiverKind, "explicitReceiverKind");
            this.$$delegate_0.nestedClassAccessViaInstanceReference(trace, classDescriptor, explicitReceiverKind);
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public void noReceiverAllowed(BindingTrace trace) {
            Intrinsics.checkNotNullParameter(trace, "trace");
            this.$$delegate_0.noReceiverAllowed(trace);
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public void noValueForParameter(BindingTrace trace, ValueParameterDescriptor valueParameter) {
            Intrinsics.checkNotNullParameter(trace, "trace");
            Intrinsics.checkNotNullParameter(valueParameter, "valueParameter");
            this.$$delegate_0.noValueForParameter(trace, valueParameter);
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public <D extends CallableDescriptor> void noneApplicable(BindingTrace trace, Collection<? extends ResolvedCall<D>> descriptors) {
            Intrinsics.checkNotNullParameter(trace, "trace");
            Intrinsics.checkNotNullParameter(descriptors, "descriptors");
            this.$$delegate_0.noneApplicable(trace, descriptors);
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public <D extends CallableDescriptor> void recordAmbiguity(BindingTrace trace, Collection<? extends ResolvedCall<D>> candidates) {
            Intrinsics.checkNotNullParameter(trace, "trace");
            Intrinsics.checkNotNullParameter(candidates, "candidates");
            this.$$delegate_0.recordAmbiguity(trace, candidates);
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public void recursiveType(BindingTrace trace, LanguageVersionSettings languageVersionSettings, boolean insideAugmentedAssignment) {
            Intrinsics.checkNotNullParameter(trace, "trace");
            Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
            this.$$delegate_0.recursiveType(trace, languageVersionSettings, insideAugmentedAssignment);
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public void unresolvedReference(BindingTrace trace) {
            Intrinsics.checkNotNullParameter(trace, "trace");
            this.$$delegate_0.unresolvedReference(trace);
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public <D extends CallableDescriptor> void unresolvedReferenceWrongReceiver(BindingTrace trace, Collection<? extends ResolvedCall<D>> candidates) {
            Intrinsics.checkNotNullParameter(trace, "trace");
            Intrinsics.checkNotNullParameter(candidates, "candidates");
            this.$$delegate_0.unresolvedReferenceWrongReceiver(trace, candidates);
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public void wrongNumberOfTypeArguments(BindingTrace trace, int expectedTypeArgumentCount, CallableDescriptor descriptor) {
            Intrinsics.checkNotNullParameter(trace, "trace");
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            this.$$delegate_0.wrongNumberOfTypeArguments(trace, expectedTypeArgumentCount, descriptor);
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public void wrongReceiverType(BindingTrace trace, ReceiverParameterDescriptor receiverParameter, ReceiverValue receiverArgument, ResolutionContext<?> c) {
            Intrinsics.checkNotNullParameter(trace, "trace");
            Intrinsics.checkNotNullParameter(receiverParameter, "receiverParameter");
            Intrinsics.checkNotNullParameter(receiverArgument, "receiverArgument");
            Intrinsics.checkNotNullParameter(c, "c");
            this.$$delegate_0.wrongReceiverType(trace, receiverParameter, receiverArgument, c);
        }

        public TracingStrategyForComponentCall(KtReferenceExpression fakeExpression, KtExpression reportErrorsOn, Name name2, Call call) {
            Intrinsics.checkNotNullParameter(fakeExpression, "fakeExpression");
            Intrinsics.checkNotNullParameter(reportErrorsOn, "reportErrorsOn");
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(call, "call");
            this.reportErrorsOn = reportErrorsOn;
            this.name = name2;
            this.call = call;
            this.$$delegate_0 = TracingStrategyImpl.create(fakeExpression, call);
        }

        public final KtExpression getReportErrorsOn() {
            return this.reportErrorsOn;
        }

        public final Name getName() {
            return this.name;
        }

        public final Call getCall() {
            return this.call;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public <D extends CallableDescriptor> void ambiguity(BindingTrace trace, Collection<? extends ResolvedCall<D>> resolvedCalls) {
            Intrinsics.checkNotNullParameter(trace, "trace");
            Intrinsics.checkNotNullParameter(resolvedCalls, "resolvedCalls");
            trace.report(Errors.COMPONENT_FUNCTION_AMBIGUITY.on(this.reportErrorsOn, this.name, resolvedCalls));
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public void unsafeCall(BindingTrace trace, KotlinType type, boolean isCallForImplicitInvoke) {
            Intrinsics.checkNotNullParameter(trace, "trace");
            Intrinsics.checkNotNullParameter(type, "type");
            trace.report(Errors.COMPONENT_FUNCTION_ON_NULLABLE.on(this.reportErrorsOn, this.name));
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public void typeInferenceFailed(ResolutionContext<?> context, InferenceErrorData inferenceErrorData) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(inferenceErrorData, "inferenceErrorData");
            Diagnostic diagnosticTypeInferenceFailedDiagnostic = AbstractTracingStrategy.typeInferenceFailedDiagnostic(context, inferenceErrorData, this.reportErrorsOn, this.call);
            if (diagnosticTypeInferenceFailedDiagnostic != null) {
                context.trace.report(diagnosticTypeInferenceFailedDiagnostic);
            }
        }
    }

    /* JADX INFO: compiled from: FakeCallResolver.kt */
    @Metadata(d1 = {"\u0000 \u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u001f\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0013\u0010\r\u001a\u00020\u000e2\b\b\u0001\u0010\u000f\u001a\u00020\u0010H\u0096\u0001J0\u0010\u0011\u001a\u00020\u000e\"\n\b\u0000\u0010\u0012*\u0004\u0018\u00010\u00132\u0006\u0010\u000f\u001a\u00020\u00102\u0012\u0010\u0014\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00120\u00160\u0015H\u0016J\u001d\u0010\u0017\u001a\u00020\u000e2\b\b\u0001\u0010\u000f\u001a\u00020\u00102\b\b\u0001\u0010\u0006\u001a\u00020\u0007H\u0096\u0001J=\u0010\u0018\u001a\u00020\u000e\"\u0010\b\u0000\u0010\u0012*\n \u0019*\u0004\u0018\u00010\u00130\u00132\b\b\u0001\u0010\u000f\u001a\u00020\u00102\u0016\b\u0001\u0010\u001a\u001a\u0010\u0012\f\u0012\n \u0019*\u0004\u0018\u0001H\u0012H\u00120\u0016H\u0096\u0001J=\u0010\u001b\u001a\u00020\u000e\"\u0010\b\u0000\u0010\u0012*\n \u0019*\u0004\u0018\u00010\u00130\u00132\b\b\u0001\u0010\u000f\u001a\u00020\u00102\u0016\b\u0001\u0010\u001a\u001a\u0010\u0012\f\u0012\n \u0019*\u0004\u0018\u0001H\u0012H\u00120\u0016H\u0096\u0001J\u008b\u0001\u0010\u001c\u001a\u00020\u000e\"\u0010\b\u0000\u0010\u0012*\n \u0019*\u0004\u0018\u00010\u00130\u00132\b\b\u0001\u0010\u000f\u001a\u00020\u00102d\b\u0001\u0010\u001d\u001a^\u0012*\b\u0001\u0012&\u0012\f\u0012\n \u0019*\u0004\u0018\u0001H\u0012H\u0012 \u0019*\u0012\u0012\f\u0012\n \u0019*\u0004\u0018\u0001H\u0012H\u0012\u0018\u00010\u00160\u0016 \u0019*,\u0012(\u0012&\u0012\f\u0012\n \u0019*\u0004\u0018\u0001H\u0012H\u0012 \u0019*\u0012\u0012\f\u0012\n \u0019*\u0004\u0018\u0001H\u0012H\u0012\u0018\u00010\u00160\u00160\u00150\u001eH\u0096\u0001J\u0013\u0010\u001f\u001a\u00020\u000e2\b\b\u0001\u0010\u000f\u001a\u00020\u0010H\u0096\u0001J\u001d\u0010 \u001a\u00020\u000e2\b\b\u0001\u0010\u000f\u001a\u00020\u00102\b\b\u0001\u0010!\u001a\u00020\"H\u0096\u0001J\u001d\u0010#\u001a\u00020\u000e2\b\b\u0001\u0010\u000f\u001a\u00020\u00102\b\b\u0001\u0010$\u001a\u00020%H\u0096\u0001J'\u0010&\u001a\u00020\u000e2\b\b\u0001\u0010\u000f\u001a\u00020\u00102\b\b\u0001\u0010'\u001a\u00020(2\b\b\u0001\u0010)\u001a\u00020*H\u0096\u0001J\u0013\u0010+\u001a\u00020\u000e2\b\b\u0001\u0010\u000f\u001a\u00020\u0010H\u0096\u0001J\u001d\u0010,\u001a\u00020\u000e2\b\b\u0001\u0010\u000f\u001a\u00020\u00102\b\b\u0001\u0010-\u001a\u00020.H\u0096\u0001J\u008b\u0001\u0010/\u001a\u00020\u000e\"\u0010\b\u0000\u0010\u0012*\n \u0019*\u0004\u0018\u00010\u00130\u00132\b\b\u0001\u0010\u000f\u001a\u00020\u00102d\b\u0001\u0010\u001d\u001a^\u0012*\b\u0001\u0012&\u0012\f\u0012\n \u0019*\u0004\u0018\u0001H\u0012H\u0012 \u0019*\u0012\u0012\f\u0012\n \u0019*\u0004\u0018\u0001H\u0012H\u0012\u0018\u00010\u00160\u0016 \u0019*,\u0012(\u0012&\u0012\f\u0012\n \u0019*\u0004\u0018\u0001H\u0012H\u0012 \u0019*\u0012\u0012\f\u0012\n \u0019*\u0004\u0018\u0001H\u0012H\u0012\u0018\u00010\u00160\u00160\u00150\u001eH\u0096\u0001J\u008b\u0001\u00100\u001a\u00020\u000e\"\u0010\b\u0000\u0010\u0012*\n \u0019*\u0004\u0018\u00010\u00130\u00132\b\b\u0001\u0010\u000f\u001a\u00020\u00102d\b\u0001\u00101\u001a^\u0012*\b\u0001\u0012&\u0012\f\u0012\n \u0019*\u0004\u0018\u0001H\u0012H\u0012 \u0019*\u0012\u0012\f\u0012\n \u0019*\u0004\u0018\u0001H\u0012H\u0012\u0018\u00010\u00160\u0016 \u0019*,\u0012(\u0012&\u0012\f\u0012\n \u0019*\u0004\u0018\u0001H\u0012H\u0012 \u0019*\u0012\u0012\f\u0012\n \u0019*\u0004\u0018\u0001H\u0012H\u0012\u0018\u00010\u00160\u00160\u00150\u001eH\u0096\u0001J%\u00102\u001a\u00020\u000e2\b\b\u0001\u0010\u000f\u001a\u00020\u00102\b\b\u0001\u00103\u001a\u0002042\u0006\u00105\u001a\u000206H\u0096\u0001J\u001c\u00107\u001a\u00020\u000e2\n\u00108\u001a\u0006\u0012\u0002\b\u0003092\u0006\u0010:\u001a\u00020;H\u0016J\u0013\u0010<\u001a\u00020\u000e2\b\b\u0001\u0010\u000f\u001a\u00020\u0010H\u0096\u0001J\u008b\u0001\u0010=\u001a\u00020\u000e\"\u0010\b\u0000\u0010\u0012*\n \u0019*\u0004\u0018\u00010\u00130\u00132\b\b\u0001\u0010\u000f\u001a\u00020\u00102d\b\u0001\u00101\u001a^\u0012*\b\u0001\u0012&\u0012\f\u0012\n \u0019*\u0004\u0018\u0001H\u0012H\u0012 \u0019*\u0012\u0012\f\u0012\n \u0019*\u0004\u0018\u0001H\u0012H\u0012\u0018\u00010\u00160\u0016 \u0019*,\u0012(\u0012&\u0012\f\u0012\n \u0019*\u0004\u0018\u0001H\u0012H\u0012 \u0019*\u0012\u0012\f\u0012\n \u0019*\u0004\u0018\u0001H\u0012H\u0012\u0018\u00010\u00160\u00160\u00150\u001eH\u0096\u0001J \u0010>\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010?\u001a\u00020@2\u0006\u0010A\u001a\u000206H\u0016J%\u0010B\u001a\u00020\u000e2\b\b\u0001\u0010\u000f\u001a\u00020\u00102\u0006\u0010C\u001a\u00020D2\b\b\u0001\u0010!\u001a\u00020\u0013H\u0096\u0001J5\u0010E\u001a\u00020\u000e2\b\b\u0001\u0010\u000f\u001a\u00020\u00102\b\b\u0001\u0010F\u001a\u00020%2\b\b\u0001\u0010G\u001a\u00020H2\f\b\u0001\u0010I\u001a\u0006\u0012\u0002\b\u000309H\u0096\u0001R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006J"}, d2 = {"Lorg/jetbrains/kotlin/types/expressions/FakeCallResolver$TracingStrategyForIteratorCall;", "Lorg/jetbrains/kotlin/resolve/calls/tasks/TracingStrategy;", "fakeExpression", "Lorg/jetbrains/kotlin/psi/KtReferenceExpression;", "reportErrorsOn", "Lorg/jetbrains/kotlin/psi/KtExpression;", "call", "Lorg/jetbrains/kotlin/psi/Call;", "(Lorg/jetbrains/kotlin/psi/KtReferenceExpression;Lorg/jetbrains/kotlin/psi/KtExpression;Lorg/jetbrains/kotlin/psi/Call;)V", "getCall", "()Lorg/jetbrains/kotlin/psi/Call;", "getReportErrorsOn", "()Lorg/jetbrains/kotlin/psi/KtExpression;", "abstractSuperCall", "", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "ambiguity", "D", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "resolvedCalls", "", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "bindCall", "bindReference", JvmProtoBufUtil.PLATFORM_TYPE_ID, "resolvedCall", "bindResolvedCall", "cannotCompleteResolve", "descriptors", "", "instantiationOfAbstractClass", "invisibleMember", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorWithVisibility;", "missingReceiver", "expectedReceiver", "Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "nestedClassAccessViaInstanceReference", "classDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "explicitReceiverKind", "Lorg/jetbrains/kotlin/resolve/calls/tasks/ExplicitReceiverKind;", "noReceiverAllowed", "noValueForParameter", "valueParameter", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "noneApplicable", "recordAmbiguity", "candidates", "recursiveType", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "insideAugmentedAssignment", "", "typeInferenceFailed", "context", "Lorg/jetbrains/kotlin/resolve/calls/context/ResolutionContext;", "inferenceErrorData", "Lorg/jetbrains/kotlin/resolve/calls/inference/InferenceErrorData;", "unresolvedReference", "unresolvedReferenceWrongReceiver", "unsafeCall", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "isCallForImplicitInvoke", "wrongNumberOfTypeArguments", "expectedTypeArgumentCount", "", "wrongReceiverType", "receiverParameter", "receiverArgument", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "c", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class TracingStrategyForIteratorCall implements TracingStrategy {
        private final /* synthetic */ TracingStrategy $$delegate_0;
        private final Call call;
        private final KtExpression reportErrorsOn;

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public void abstractSuperCall(BindingTrace trace) {
            Intrinsics.checkNotNullParameter(trace, "trace");
            this.$$delegate_0.abstractSuperCall(trace);
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public void bindCall(BindingTrace trace, Call call) {
            Intrinsics.checkNotNullParameter(trace, "trace");
            Intrinsics.checkNotNullParameter(call, "call");
            this.$$delegate_0.bindCall(trace, call);
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public <D extends CallableDescriptor> void bindReference(BindingTrace trace, ResolvedCall<D> resolvedCall) {
            Intrinsics.checkNotNullParameter(trace, "trace");
            Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
            this.$$delegate_0.bindReference(trace, resolvedCall);
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public <D extends CallableDescriptor> void bindResolvedCall(BindingTrace trace, ResolvedCall<D> resolvedCall) {
            Intrinsics.checkNotNullParameter(trace, "trace");
            Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
            this.$$delegate_0.bindResolvedCall(trace, resolvedCall);
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public <D extends CallableDescriptor> void cannotCompleteResolve(BindingTrace trace, Collection<? extends ResolvedCall<D>> descriptors) {
            Intrinsics.checkNotNullParameter(trace, "trace");
            Intrinsics.checkNotNullParameter(descriptors, "descriptors");
            this.$$delegate_0.cannotCompleteResolve(trace, descriptors);
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public void instantiationOfAbstractClass(BindingTrace trace) {
            Intrinsics.checkNotNullParameter(trace, "trace");
            this.$$delegate_0.instantiationOfAbstractClass(trace);
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public void invisibleMember(BindingTrace trace, DeclarationDescriptorWithVisibility descriptor) {
            Intrinsics.checkNotNullParameter(trace, "trace");
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            this.$$delegate_0.invisibleMember(trace, descriptor);
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public void missingReceiver(BindingTrace trace, ReceiverParameterDescriptor expectedReceiver) {
            Intrinsics.checkNotNullParameter(trace, "trace");
            Intrinsics.checkNotNullParameter(expectedReceiver, "expectedReceiver");
            this.$$delegate_0.missingReceiver(trace, expectedReceiver);
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public void nestedClassAccessViaInstanceReference(BindingTrace trace, ClassDescriptor classDescriptor, ExplicitReceiverKind explicitReceiverKind) {
            Intrinsics.checkNotNullParameter(trace, "trace");
            Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
            Intrinsics.checkNotNullParameter(explicitReceiverKind, "explicitReceiverKind");
            this.$$delegate_0.nestedClassAccessViaInstanceReference(trace, classDescriptor, explicitReceiverKind);
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public void noReceiverAllowed(BindingTrace trace) {
            Intrinsics.checkNotNullParameter(trace, "trace");
            this.$$delegate_0.noReceiverAllowed(trace);
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public void noValueForParameter(BindingTrace trace, ValueParameterDescriptor valueParameter) {
            Intrinsics.checkNotNullParameter(trace, "trace");
            Intrinsics.checkNotNullParameter(valueParameter, "valueParameter");
            this.$$delegate_0.noValueForParameter(trace, valueParameter);
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public <D extends CallableDescriptor> void noneApplicable(BindingTrace trace, Collection<? extends ResolvedCall<D>> descriptors) {
            Intrinsics.checkNotNullParameter(trace, "trace");
            Intrinsics.checkNotNullParameter(descriptors, "descriptors");
            this.$$delegate_0.noneApplicable(trace, descriptors);
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public <D extends CallableDescriptor> void recordAmbiguity(BindingTrace trace, Collection<? extends ResolvedCall<D>> candidates) {
            Intrinsics.checkNotNullParameter(trace, "trace");
            Intrinsics.checkNotNullParameter(candidates, "candidates");
            this.$$delegate_0.recordAmbiguity(trace, candidates);
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public void recursiveType(BindingTrace trace, LanguageVersionSettings languageVersionSettings, boolean insideAugmentedAssignment) {
            Intrinsics.checkNotNullParameter(trace, "trace");
            Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
            this.$$delegate_0.recursiveType(trace, languageVersionSettings, insideAugmentedAssignment);
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public void unresolvedReference(BindingTrace trace) {
            Intrinsics.checkNotNullParameter(trace, "trace");
            this.$$delegate_0.unresolvedReference(trace);
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public <D extends CallableDescriptor> void unresolvedReferenceWrongReceiver(BindingTrace trace, Collection<? extends ResolvedCall<D>> candidates) {
            Intrinsics.checkNotNullParameter(trace, "trace");
            Intrinsics.checkNotNullParameter(candidates, "candidates");
            this.$$delegate_0.unresolvedReferenceWrongReceiver(trace, candidates);
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public void wrongNumberOfTypeArguments(BindingTrace trace, int expectedTypeArgumentCount, CallableDescriptor descriptor) {
            Intrinsics.checkNotNullParameter(trace, "trace");
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            this.$$delegate_0.wrongNumberOfTypeArguments(trace, expectedTypeArgumentCount, descriptor);
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public void wrongReceiverType(BindingTrace trace, ReceiverParameterDescriptor receiverParameter, ReceiverValue receiverArgument, ResolutionContext<?> c) {
            Intrinsics.checkNotNullParameter(trace, "trace");
            Intrinsics.checkNotNullParameter(receiverParameter, "receiverParameter");
            Intrinsics.checkNotNullParameter(receiverArgument, "receiverArgument");
            Intrinsics.checkNotNullParameter(c, "c");
            this.$$delegate_0.wrongReceiverType(trace, receiverParameter, receiverArgument, c);
        }

        public TracingStrategyForIteratorCall(KtReferenceExpression fakeExpression, KtExpression reportErrorsOn, Call call) {
            Intrinsics.checkNotNullParameter(fakeExpression, "fakeExpression");
            Intrinsics.checkNotNullParameter(reportErrorsOn, "reportErrorsOn");
            Intrinsics.checkNotNullParameter(call, "call");
            this.reportErrorsOn = reportErrorsOn;
            this.call = call;
            this.$$delegate_0 = TracingStrategyImpl.create(fakeExpression, call);
        }

        public final KtExpression getReportErrorsOn() {
            return this.reportErrorsOn;
        }

        public final Call getCall() {
            return this.call;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public <D extends CallableDescriptor> void ambiguity(BindingTrace trace, Collection<? extends ResolvedCall<D>> resolvedCalls) {
            Intrinsics.checkNotNullParameter(trace, "trace");
            Intrinsics.checkNotNullParameter(resolvedCalls, "resolvedCalls");
            trace.report(Errors.ITERATOR_AMBIGUITY.on(this.reportErrorsOn, resolvedCalls));
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public void unsafeCall(BindingTrace trace, KotlinType type, boolean isCallForImplicitInvoke) {
            Intrinsics.checkNotNullParameter(trace, "trace");
            Intrinsics.checkNotNullParameter(type, "type");
            trace.report(Errors.ITERATOR_ON_NULLABLE.on(this.reportErrorsOn));
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public void typeInferenceFailed(ResolutionContext<?> context, InferenceErrorData inferenceErrorData) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(inferenceErrorData, "inferenceErrorData");
            Diagnostic diagnosticTypeInferenceFailedDiagnostic = AbstractTracingStrategy.typeInferenceFailedDiagnostic(context, inferenceErrorData, this.reportErrorsOn, this.call);
            if (diagnosticTypeInferenceFailedDiagnostic != null) {
                context.trace.report(diagnosticTypeInferenceFailedDiagnostic);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ Pair makeAndResolveFakeCallInContext$default(FakeCallResolver fakeCallResolver, ReceiverValue receiverValue, ResolutionContext resolutionContext, List list, Name name2, KtExpression ktExpression, RealExpression realExpression, Function1 function1, int i, Object obj) {
        return fakeCallResolver.makeAndResolveFakeCallInContext(receiverValue, resolutionContext, list, name2, ktExpression, (i & 32) != 0 ? null : realExpression, (i & 64) != 0 ? new Function1<KtSimpleNameExpression, Unit>() { // from class: org.jetbrains.kotlin.types.expressions.FakeCallResolver.makeAndResolveFakeCallInContext.1
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(KtSimpleNameExpression ktSimpleNameExpression) {
                Intrinsics.checkNotNullParameter(ktSimpleNameExpression, "<anonymous parameter 0>");
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(KtSimpleNameExpression ktSimpleNameExpression) {
                invoke2(ktSimpleNameExpression);
                return Unit.INSTANCE;
            }
        } : function1);
    }

    public final Pair<Call, OverloadResolutionResults<FunctionDescriptor>> makeAndResolveFakeCallInContext(ReceiverValue receiver, ResolutionContext<?> context, List<? extends KtExpression> valueArguments, Name name2, KtExpression callElement, RealExpression realExpression, Function1<? super KtSimpleNameExpression, Unit> onComplete) {
        OverloadResolutionResults<FunctionDescriptor> overloadResolutionResultsResolveCallWithGivenName;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(valueArguments, "valueArguments");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(callElement, "callElement");
        Intrinsics.checkNotNullParameter(onComplete, "onComplete");
        KtPsiFactory ktPsiFactory = new KtPsiFactory(this.project, false);
        String strAsString = name2.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        KtSimpleNameExpression ktSimpleNameExpressionCreateSimpleName = ktPsiFactory.createSimpleName(strAsString);
        TracingStrategyForIteratorCall tracingStrategyForIteratorCall = null;
        Call callMakeCallWithExpressions = CallMaker.makeCallWithExpressions(callElement, receiver, null, ktSimpleNameExpressionCreateSimpleName, valueArguments);
        Intrinsics.checkNotNullExpressionValue(callMakeCallWithExpressions, "makeCallWithExpressions(...)");
        FakeCallKind callKind = realExpression != null ? realExpression.getCallKind() : null;
        int i = callKind == null ? -1 : WhenMappings.$EnumSwitchMapping$0[callKind.ordinal()];
        if (i == 1) {
            tracingStrategyForIteratorCall = new TracingStrategyForIteratorCall(ktSimpleNameExpressionCreateSimpleName, realExpression.getExpressionToReportErrorsOn(), callMakeCallWithExpressions);
        } else if (i == 2) {
            tracingStrategyForIteratorCall = new TracingStrategyForComponentCall(ktSimpleNameExpressionCreateSimpleName, realExpression.getExpressionToReportErrorsOn(), name2, callMakeCallWithExpressions);
        }
        if (tracingStrategyForIteratorCall != null) {
            overloadResolutionResultsResolveCallWithGivenName = this.callResolver.resolveCallWithGivenName(context, callMakeCallWithExpressions, name2, tracingStrategyForIteratorCall);
        } else {
            overloadResolutionResultsResolveCallWithGivenName = this.callResolver.resolveCallWithGivenName(context, callMakeCallWithExpressions, ktSimpleNameExpressionCreateSimpleName, name2);
        }
        Intrinsics.checkNotNull(overloadResolutionResultsResolveCallWithGivenName);
        onComplete.invoke(ktSimpleNameExpressionCreateSimpleName);
        return new Pair<>(callMakeCallWithExpressions, overloadResolutionResultsResolveCallWithGivenName);
    }

    /* JADX INFO: compiled from: FakeCallResolver.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/types/expressions/FakeCallResolver$RealExpression;", "", "expressionToReportErrorsOn", "Lorg/jetbrains/kotlin/psi/KtExpression;", "callKind", "Lorg/jetbrains/kotlin/types/expressions/FakeCallKind;", "(Lorg/jetbrains/kotlin/psi/KtExpression;Lorg/jetbrains/kotlin/types/expressions/FakeCallKind;)V", "getCallKind", "()Lorg/jetbrains/kotlin/types/expressions/FakeCallKind;", "getExpressionToReportErrorsOn", "()Lorg/jetbrains/kotlin/psi/KtExpression;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class RealExpression {
        private final FakeCallKind callKind;
        private final KtExpression expressionToReportErrorsOn;

        public RealExpression(KtExpression expressionToReportErrorsOn, FakeCallKind callKind) {
            Intrinsics.checkNotNullParameter(expressionToReportErrorsOn, "expressionToReportErrorsOn");
            Intrinsics.checkNotNullParameter(callKind, "callKind");
            this.expressionToReportErrorsOn = expressionToReportErrorsOn;
            this.callKind = callKind;
        }

        public final FakeCallKind getCallKind() {
            return this.callKind;
        }

        public final KtExpression getExpressionToReportErrorsOn() {
            return this.expressionToReportErrorsOn;
        }
    }
}
