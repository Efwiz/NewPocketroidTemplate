package org.jetbrains.kotlin.fir.resolve.calls;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirNamedArgumentExpression;
import org.jetbrains.kotlin.fir.expressions.FirVarargArgumentsExpression;
import org.jetbrains.kotlin.fir.expressions.FirWrappedArgumentExpression;
import org.jetbrains.kotlin.fir.resolve.BodyResolveComponents;
import org.jetbrains.kotlin.fir.resolve.FirDefaultParametersResolverKt;
import org.jetbrains.kotlin.fir.resolve.ResolveUtilsKt;
import org.jetbrains.kotlin.fir.scopes.FirScope;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.scopes.FirTypeScopeKt;
import org.jetbrains.kotlin.fir.scopes.ProcessorAction;
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirValueParameterSymbol;
import org.jetbrains.kotlin.fir.utils.exceptions.FirExceptionUtilsKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.ForbiddenNamedArgumentsTarget;
import org.jetbrains.kotlin.utils.exceptions.ExceptionAttachmentBuilder;
import org.jetbrains.kotlin.utils.exceptions.KotlinIllegalArgumentExceptionWithAttachments;

/* JADX INFO: compiled from: FirArgumentsToParametersMapper.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0088\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000f\b\u0002\u0018\u00002\u00020\u0001:\u0001CB/\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\u0010\u0010.\u001a\u00020/2\u0006\u00100\u001a\u00020\u0011H\u0002J\u0010\u00101\u001a\u00020/2\u0006\u00102\u001a\u00020+H\u0002J\b\u00103\u001a\u00020/H\u0002J\u0012\u00104\u001a\u0004\u0018\u00010\u001e2\u0006\u00102\u001a\u000205H\u0002J\u0012\u00106\u001a\u0004\u0018\u00010\u001e2\u0006\u00107\u001a\u00020\u001dH\u0002J\u0006\u00108\u001a\u00020/J\u0014\u00109\u001a\u00020/2\f\u0010:\u001a\b\u0012\u0004\u0012\u00020+0 J\u000e\u0010;\u001a\u00020/2\u0006\u0010<\u001a\u00020+J\u0010\u0010=\u001a\u00020/2\u0006\u00102\u001a\u000205H\u0002J\u0018\u0010>\u001a\u00020/2\u0006\u00102\u001a\u00020+2\u0006\u0010?\u001a\u00020\u000bH\u0002J\u0014\u0010@\u001a\u00020/2\f\u0010A\u001a\b\u0012\u0004\u0012\u00020+0 J\u0018\u0010B\u001a\u00020/2\u0006\u00102\u001a\u00020+2\u0006\u0010?\u001a\u00020\u000bH\u0002R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R.\u0010\u0012\u001a\n\u0012\u0004\u0012\u00020\u0011\u0018\u00010\u00102\u000e\u0010\u000f\u001a\n\u0012\u0004\u0012\u00020\u0011\u0018\u00010\u0010@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u001d\u0010\u0015\u001a\u0004\u0018\u00010\u00168FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0019\u0010\u001a\u001a\u0004\b\u0017\u0010\u0018R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u001b\u001a\u0010\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020\u001e\u0018\u00010\u001cX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u001e0 8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b!\u0010\u0014R-\u0010\"\u001a\u001e\u0012\u0004\u0012\u00020\u001e\u0012\u0004\u0012\u00020$0#j\u000e\u0012\u0004\u0012\u00020\u001e\u0012\u0004\u0012\u00020$`%¢\u0006\b\n\u0000\u001a\u0004\b&\u0010'R\u000e\u0010(\u001a\u00020)X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010*\u001a\n\u0012\u0004\u0012\u00020+\u0018\u00010\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u0018\u0010,\u001a\u00020\u000b*\u00020+8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b,\u0010-¨\u0006D"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/FirCallArgumentsProcessor;", "", "useSiteSession", "Lorg/jetbrains/kotlin/fir/FirSession;", "function", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "bodyResolveComponents", "Lorg/jetbrains/kotlin/fir/resolve/BodyResolveComponents;", "originScope", "Lorg/jetbrains/kotlin/fir/scopes/FirScope;", "isIndexedSetOperator", "", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/declarations/FirFunction;Lorg/jetbrains/kotlin/fir/resolve/BodyResolveComponents;Lorg/jetbrains/kotlin/fir/scopes/FirScope;Z)V", "currentPositionedParameterIndex", "", "<set-?>", "", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionDiagnostic;", "diagnostics", "getDiagnostics", "()Ljava/util/List;", "forbiddenNamedArgumentsTarget", "Lorg/jetbrains/kotlin/resolve/ForbiddenNamedArgumentsTarget;", "getForbiddenNamedArgumentsTarget", "()Lorg/jetbrains/kotlin/resolve/ForbiddenNamedArgumentsTarget;", "forbiddenNamedArgumentsTarget$delegate", "Lkotlin/Lazy;", "nameToParameter", "", "Lorg/jetbrains/kotlin/name/Name;", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "parameters", "", "getParameters", "result", "Ljava/util/LinkedHashMap;", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolvedCallArgument;", "Lkotlin/collections/LinkedHashMap;", "getResult", "()Ljava/util/LinkedHashMap;", "state", "Lorg/jetbrains/kotlin/fir/resolve/calls/FirCallArgumentsProcessor$State;", "varargArguments", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "isSpread", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;)Z", "addDiagnostic", "", "diagnostic", "addVarargArgument", "argument", "completeVarargPositionArguments", "findParameterByName", "Lorg/jetbrains/kotlin/fir/expressions/FirNamedArgumentExpression;", "getParameterByName", "name", "processDefaultsAndRunChecks", "processExcessLambdaArguments", "excessLambdaArguments", "processExternalArgument", "externalArgument", "processNamedArgument", "processNonLambdaArgument", "isLastArgument", "processNonLambdaArguments", "arguments", "processPositionArgument", "State", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class FirCallArgumentsProcessor {
    private final BodyResolveComponents bodyResolveComponents;
    private int currentPositionedParameterIndex;
    private List<ResolutionDiagnostic> diagnostics;

    /* JADX INFO: renamed from: forbiddenNamedArgumentsTarget$delegate, reason: from kotlin metadata */
    private final Lazy forbiddenNamedArgumentsTarget;
    private final FirFunction function;
    private final boolean isIndexedSetOperator;
    private Map<Name, ? extends FirValueParameter> nameToParameter;
    private final FirScope originScope;
    private final LinkedHashMap<FirValueParameter, ResolvedCallArgument> result;
    private State state;
    private final FirSession useSiteSession;
    private List<FirExpression> varargArguments;

    /* JADX INFO: compiled from: FirArgumentsToParametersMapper.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0082\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/FirCallArgumentsProcessor$State;", "", "(Ljava/lang/String;I)V", "POSITION_ARGUMENTS", "VARARG_POSITION", "NAMED_ONLY_ARGUMENTS", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private enum State {
        POSITION_ARGUMENTS,
        VARARG_POSITION,
        NAMED_ONLY_ARGUMENTS;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<State> getEntries() {
            return $ENTRIES;
        }
    }

    public FirCallArgumentsProcessor(FirSession useSiteSession, FirFunction function, BodyResolveComponents bodyResolveComponents, FirScope firScope, boolean z) {
        Intrinsics.checkNotNullParameter(useSiteSession, "useSiteSession");
        Intrinsics.checkNotNullParameter(function, "function");
        Intrinsics.checkNotNullParameter(bodyResolveComponents, "bodyResolveComponents");
        this.useSiteSession = useSiteSession;
        this.function = function;
        this.bodyResolveComponents = bodyResolveComponents;
        this.originScope = firScope;
        this.isIndexedSetOperator = z;
        this.state = State.POSITION_ARGUMENTS;
        this.result = new LinkedHashMap<>(function.getValueParameters().size());
        this.forbiddenNamedArgumentsTarget = LazyKt.lazy(new Function0<ForbiddenNamedArgumentsTarget>() { // from class: org.jetbrains.kotlin.fir.resolve.calls.FirCallArgumentsProcessor$forbiddenNamedArgumentsTarget$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final ForbiddenNamedArgumentsTarget invoke() {
                FirFunction firFunction = this.this$0.function;
                FirSession firSession = this.this$0.useSiteSession;
                FirScope firScope2 = this.this$0.originScope;
                return ResolveUtilsKt.getAsForbiddenNamedArgumentsTarget(firFunction, firSession, firScope2 instanceof FirTypeScope ? (FirTypeScope) firScope2 : null);
            }
        });
    }

    public final List<ResolutionDiagnostic> getDiagnostics() {
        return this.diagnostics;
    }

    public final LinkedHashMap<FirValueParameter, ResolvedCallArgument> getResult() {
        return this.result;
    }

    public final ForbiddenNamedArgumentsTarget getForbiddenNamedArgumentsTarget() {
        return (ForbiddenNamedArgumentsTarget) this.forbiddenNamedArgumentsTarget.getValue();
    }

    public final void processNonLambdaArguments(List<? extends FirExpression> arguments) {
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        int i = 0;
        for (FirExpression firExpression : arguments) {
            int i2 = i + 1;
            if (firExpression instanceof FirVarargArgumentsExpression) {
                List<FirExpression> arguments2 = ((FirVarargArgumentsExpression) firExpression).getArguments();
                Iterator<FirExpression> it = arguments2.iterator();
                int i3 = 0;
                while (it.hasNext()) {
                    int i4 = i3 + 1;
                    processNonLambdaArgument(it.next(), i == CollectionsKt.getLastIndex(arguments) && i3 == CollectionsKt.getLastIndex(arguments2));
                    i3 = i4;
                }
            } else {
                processNonLambdaArgument(firExpression, i == CollectionsKt.getLastIndex(arguments));
            }
            i = i2;
        }
        if (this.state == State.VARARG_POSITION) {
            completeVarargPositionArguments();
        }
    }

    private final void processNonLambdaArgument(FirExpression argument, boolean isLastArgument) {
        if (!(argument instanceof FirNamedArgumentExpression)) {
            if (this.state == State.VARARG_POSITION && this.isIndexedSetOperator && isLastArgument) {
                completeVarargPositionArguments();
            }
            processPositionArgument(argument, isLastArgument);
            return;
        }
        if (Intrinsics.areEqual(this.function.getOrigin(), FirDeclarationOrigin.DynamicScope.INSTANCE)) {
            processPositionArgument(((FirNamedArgumentExpression) argument).getExpression(), isLastArgument);
            return;
        }
        if (this.state == State.VARARG_POSITION) {
            completeVarargPositionArguments();
        }
        processNamedArgument((FirNamedArgumentExpression) argument);
    }

    private final void processPositionArgument(FirExpression argument, boolean isLastArgument) {
        int lastIndex;
        if (this.state == State.NAMED_ONLY_ARGUMENTS) {
            addDiagnostic(new MixingNamedAndPositionArguments(argument));
            return;
        }
        if (this.isIndexedSetOperator) {
            lastIndex = CollectionsKt.getLastIndex(getParameters());
            if (!isLastArgument) {
                int i = this.currentPositionedParameterIndex;
                lastIndex = i >= lastIndex ? -1 : i;
            }
        } else {
            lastIndex = this.currentPositionedParameterIndex;
        }
        FirValueParameter firValueParameter = (FirValueParameter) CollectionsKt.getOrNull(getParameters(), lastIndex);
        if (firValueParameter == null) {
            addDiagnostic(new TooManyArguments(argument, this.function));
            return;
        }
        if (!firValueParameter.getIsVararg()) {
            this.currentPositionedParameterIndex++;
            this.result.put(firValueParameter, new ResolvedCallArgument.SimpleArgument(argument));
            this.state = State.POSITION_ARGUMENTS;
        } else {
            addVarargArgument(argument);
            this.state = State.VARARG_POSITION;
        }
    }

    private final void processNamedArgument(FirNamedArgumentExpression argument) {
        ForbiddenNamedArgumentsTarget forbiddenNamedArgumentsTarget = getForbiddenNamedArgumentsTarget();
        if (forbiddenNamedArgumentsTarget != null) {
            addDiagnostic(new NamedArgumentNotAllowed(argument, this.function, forbiddenNamedArgumentsTarget));
        }
        boolean z = this.state != State.NAMED_ONLY_ARGUMENTS;
        this.state = State.NAMED_ONLY_ARGUMENTS;
        FirValueParameter firValueParameterFindParameterByName = findParameterByName(argument);
        if (firValueParameterFindParameterByName == null) {
            return;
        }
        if (this.result.get(firValueParameterFindParameterByName) != null) {
            addDiagnostic(new ArgumentPassedTwice(argument, firValueParameterFindParameterByName));
            return;
        }
        this.result.put(firValueParameterFindParameterByName, new ResolvedCallArgument.SimpleArgument(argument));
        if (z && Intrinsics.areEqual(CollectionsKt.getOrNull(getParameters(), this.currentPositionedParameterIndex), firValueParameterFindParameterByName)) {
            this.state = State.POSITION_ARGUMENTS;
            this.currentPositionedParameterIndex++;
        }
    }

    public final void processExternalArgument(FirExpression externalArgument) {
        Intrinsics.checkNotNullParameter(externalArgument, "externalArgument");
        FirValueParameter firValueParameter = (FirValueParameter) CollectionsKt.lastOrNull((List) getParameters());
        if (firValueParameter == null) {
            addDiagnostic(new TooManyArguments(externalArgument, this.function));
            return;
        }
        if (!Intrinsics.areEqual(this.function.getOrigin(), FirDeclarationOrigin.DynamicScope.INSTANCE)) {
            if (firValueParameter.getIsVararg()) {
                addDiagnostic(new VarargArgumentOutsideParentheses(externalArgument, firValueParameter));
                return;
            } else if (this.result.get(firValueParameter) != null) {
                addDiagnostic(new TooManyArguments(externalArgument, this.function));
                return;
            } else {
                this.result.put(firValueParameter, new ResolvedCallArgument.SimpleArgument(externalArgument));
                return;
            }
        }
        ResolvedCallArgument resolvedCallArgument = this.result.get(firValueParameter);
        if (resolvedCallArgument == null) {
            this.result.put(firValueParameter, new ResolvedCallArgument.SimpleArgument(externalArgument));
        } else {
            this.result.put(firValueParameter, new ResolvedCallArgument.VarargArgument(CollectionsKt.plus((Collection<? extends FirExpression>) resolvedCallArgument.getArguments(), externalArgument)));
        }
    }

    public final void processExcessLambdaArguments(List<? extends FirExpression> excessLambdaArguments) {
        Intrinsics.checkNotNullParameter(excessLambdaArguments, "excessLambdaArguments");
        Iterator<T> it = excessLambdaArguments.iterator();
        while (it.hasNext()) {
            addDiagnostic(new ManyLambdaExpressionArguments((FirExpression) it.next()));
        }
    }

    public final void processDefaultsAndRunChecks() {
        for (Map.Entry<FirValueParameter, ResolvedCallArgument> entry : this.result.entrySet()) {
            FirValueParameter key = entry.getKey();
            final ResolvedCallArgument value = entry.getValue();
            if (!key.getIsVararg()) {
                if (value instanceof ResolvedCallArgument.SimpleArgument) {
                    ResolvedCallArgument.SimpleArgument simpleArgument = (ResolvedCallArgument.SimpleArgument) value;
                    if (isSpread(simpleArgument.getCallArgument())) {
                        addDiagnostic(new NonVarargSpread(simpleArgument.getCallArgument()));
                    }
                } else {
                    KotlinIllegalArgumentExceptionWithAttachments kotlinIllegalArgumentExceptionWithAttachments = new KotlinIllegalArgumentExceptionWithAttachments("Incorrect resolved argument for parameter " + key.getClass() + ": " + value.getClass(), null);
                    ExceptionAttachmentBuilder exceptionAttachmentBuilder = new ExceptionAttachmentBuilder();
                    FirExceptionUtilsKt.withFirEntry(exceptionAttachmentBuilder, "parameter", key);
                    exceptionAttachmentBuilder.withEntryGroup("arguments", new Function1<ExceptionAttachmentBuilder, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.calls.FirCallArgumentsProcessor$processDefaultsAndRunChecks$1$1
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(ExceptionAttachmentBuilder exceptionAttachmentBuilder2) {
                            invoke2(exceptionAttachmentBuilder2);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(ExceptionAttachmentBuilder withEntryGroup) {
                            Intrinsics.checkNotNullParameter(withEntryGroup, "$this$withEntryGroup");
                            Iterator<FirExpression> it = value.getArguments().iterator();
                            int i = 0;
                            while (it.hasNext()) {
                                FirExceptionUtilsKt.withFirEntry(withEntryGroup, "argument" + i, it.next());
                                i++;
                            }
                        }
                    });
                    kotlinIllegalArgumentExceptionWithAttachments.withAttachment("info.txt", exceptionAttachmentBuilder.buildString());
                    throw kotlinIllegalArgumentExceptionWithAttachments;
                }
            }
        }
        Iterator<FirValueParameter> it = getParameters().iterator();
        int i = 0;
        while (true) {
            int i2 = i;
            if (!it.hasNext()) {
                return;
            }
            i = i2 + 1;
            FirValueParameter next = it.next();
            if (!this.result.containsKey(next)) {
                if (FirDefaultParametersResolverKt.getDefaultParameterResolver(this.bodyResolveComponents.getSession()).declaresDefaultValue(this.useSiteSession, this.bodyResolveComponents.getScopeSession(), this.function, this.originScope, i2)) {
                    this.result.put(next, ResolvedCallArgument.DefaultArgument.INSTANCE);
                } else if (next.getIsVararg()) {
                    this.result.put(next, new ResolvedCallArgument.VarargArgument(CollectionsKt.emptyList()));
                } else {
                    addDiagnostic(new NoValueForParameter(next, this.function));
                }
            }
        }
    }

    private final void completeVarargPositionArguments() {
        State state = State.VARARG_POSITION;
        FirValueParameter firValueParameter = getParameters().get(this.currentPositionedParameterIndex);
        LinkedHashMap<FirValueParameter, ResolvedCallArgument> linkedHashMap = this.result;
        List<FirExpression> list = this.varargArguments;
        Intrinsics.checkNotNull(list);
        linkedHashMap.put(firValueParameter, new ResolvedCallArgument.VarargArgument(list));
    }

    private final void addVarargArgument(FirExpression argument) {
        if (this.varargArguments == null) {
            this.varargArguments = new ArrayList();
        }
        List<FirExpression> list = this.varargArguments;
        Intrinsics.checkNotNull(list);
        list.add(argument);
    }

    private final FirValueParameter getParameterByName(Name name2) {
        if (this.nameToParameter == null) {
            List<FirValueParameter> parameters = getParameters();
            LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(parameters, 10)), 16));
            for (Object obj : parameters) {
                linkedHashMap.put(((FirValueParameter) obj).getName(), obj);
            }
            this.nameToParameter = linkedHashMap;
        }
        Map<Name, ? extends FirValueParameter> map = this.nameToParameter;
        Intrinsics.checkNotNull(map);
        return map.get(name2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [T, org.jetbrains.kotlin.fir.declarations.FirValueParameter] */
    private final FirValueParameter findParameterByName(final FirNamedArgumentExpression argument) {
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        objectRef.element = getParameterByName(argument.getName());
        FirFunctionSymbol<? extends FirFunction> symbol = this.function.getSymbol();
        FirNamedFunctionSymbol firNamedFunctionSymbol = symbol instanceof FirNamedFunctionSymbol ? (FirNamedFunctionSymbol) symbol : null;
        final Ref.IntRef intRef = new Ref.IntRef();
        intRef.element = -1;
        if (objectRef.element == 0) {
            if (firNamedFunctionSymbol != null && ClassMembersKt.isSubstitutionOrIntersectionOverride(this.function)) {
                final Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
                FirScope firScope = this.originScope;
                FirTypeScope firTypeScope = firScope instanceof FirTypeScope ? (FirTypeScope) firScope : null;
                if (firTypeScope != null) {
                    FirTypeScopeKt.processOverriddenFunctions(firTypeScope, firNamedFunctionSymbol, new Function1<FirNamedFunctionSymbol, ProcessorAction>() { // from class: org.jetbrains.kotlin.fir.resolve.calls.FirCallArgumentsProcessor.findParameterByName.1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        /* JADX WARN: Multi-variable type inference failed */
                        /* JADX WARN: Type inference failed for: r0v12, types: [T, java.lang.Object] */
                        /* JADX WARN: Type inference failed for: r9v1, types: [T, java.util.List] */
                        @Override // kotlin.jvm.functions.Function1
                        public final ProcessorAction invoke(FirNamedFunctionSymbol it) {
                            FirValueParameterSymbol firValueParameterSymbol;
                            Intrinsics.checkNotNullParameter(it, "it");
                            FirValueParameter firValueParameter = null;
                            if (ResolveUtilsKt.getAsForbiddenNamedArgumentsTarget$default((FirFunction) it.getFir(), FirCallArgumentsProcessor.this.useSiteSession, null, 2, null) != null) {
                                return ProcessorAction.NEXT;
                            }
                            ?? valueParameterSymbols = it.getValueParameterSymbols();
                            if (intRef.element != -1) {
                                return FirCallArgumentsProcessor.findParameterByName$findAndReportValueParameterWithDifferentName(valueParameterSymbols, intRef, argument, FirCallArgumentsProcessor.this);
                            }
                            Ref.IntRef intRef2 = intRef;
                            FirNamedArgumentExpression firNamedArgumentExpression = argument;
                            Iterator it2 = valueParameterSymbols.iterator();
                            int i = 0;
                            while (true) {
                                if (!it2.hasNext()) {
                                    i = -1;
                                    break;
                                }
                                if (Intrinsics.areEqual(((FirValueParameterSymbol) it2.next()).getName(), firNamedArgumentExpression.getName())) {
                                    break;
                                }
                                i++;
                            }
                            intRef2.element = i;
                            if (intRef.element != -1) {
                                objectRef.element = FirCallArgumentsProcessor.this.getParameters().get(intRef.element);
                                List<FirValueParameterSymbol> list = objectRef2.element;
                                if (list != null && (firValueParameterSymbol = (FirValueParameterSymbol) CollectionsKt.getOrNull(list, intRef.element)) != null) {
                                    firValueParameter = (FirValueParameter) firValueParameterSymbol.getFir();
                                }
                                if (firValueParameter != null) {
                                    FirCallArgumentsProcessor.this.addDiagnostic(new NameForAmbiguousParameter(argument));
                                    return ProcessorAction.STOP;
                                }
                                return ProcessorAction.NEXT;
                            }
                            objectRef2.element = valueParameterSymbols;
                            return ProcessorAction.NEXT;
                        }
                    });
                }
            }
            if (objectRef.element == 0) {
                addDiagnostic(new NameNotFound(argument, this.function));
            }
        } else if (firNamedFunctionSymbol != null && ClassMembersKt.isSubstitutionOrIntersectionOverride(this.function)) {
            Iterator<FirValueParameter> it = getParameters().iterator();
            int i = 0;
            while (true) {
                if (!it.hasNext()) {
                    i = -1;
                    break;
                }
                if (Intrinsics.areEqual(it.next().getName(), argument.getName())) {
                    break;
                }
                i++;
            }
            intRef.element = i;
            if (intRef.element != -1) {
                FirScope firScope2 = this.originScope;
                FirTypeScope firTypeScope2 = firScope2 instanceof FirTypeScope ? (FirTypeScope) firScope2 : null;
                if (firTypeScope2 != null) {
                    FirTypeScopeKt.processOverriddenFunctions(firTypeScope2, firNamedFunctionSymbol, new Function1<FirNamedFunctionSymbol, ProcessorAction>() { // from class: org.jetbrains.kotlin.fir.resolve.calls.FirCallArgumentsProcessor.findParameterByName.3
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        /* JADX WARN: Multi-variable type inference failed */
                        @Override // kotlin.jvm.functions.Function1
                        public final ProcessorAction invoke(FirNamedFunctionSymbol it2) {
                            Intrinsics.checkNotNullParameter(it2, "it");
                            if (ResolveUtilsKt.getAsForbiddenNamedArgumentsTarget$default((FirFunction) it2.getFir(), FirCallArgumentsProcessor.this.useSiteSession, null, 2, null) == null) {
                                return FirCallArgumentsProcessor.findParameterByName$findAndReportValueParameterWithDifferentName(it2.getValueParameterSymbols(), intRef, argument, FirCallArgumentsProcessor.this);
                            }
                            return ProcessorAction.NEXT;
                        }
                    });
                }
            }
        }
        return (FirValueParameter) objectRef.element;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static final ProcessorAction findParameterByName$findAndReportValueParameterWithDifferentName(List<FirValueParameterSymbol> list, Ref.IntRef intRef, FirNamedArgumentExpression firNamedArgumentExpression, FirCallArgumentsProcessor firCallArgumentsProcessor) {
        FirValueParameterSymbol firValueParameterSymbol = (FirValueParameterSymbol) CollectionsKt.getOrNull(list, intRef.element);
        FirValueParameter firValueParameter = firValueParameterSymbol != null ? (FirValueParameter) firValueParameterSymbol.getFir() : null;
        Name name2 = firValueParameter != null ? firValueParameter.getName() : null;
        if (name2 != null && !Intrinsics.areEqual(name2, firNamedArgumentExpression.getName())) {
            firCallArgumentsProcessor.addDiagnostic(new NameForAmbiguousParameter(firNamedArgumentExpression));
            return ProcessorAction.STOP;
        }
        return ProcessorAction.NEXT;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void addDiagnostic(ResolutionDiagnostic diagnostic) {
        if (this.diagnostics == null) {
            this.diagnostics = new ArrayList();
        }
        List<ResolutionDiagnostic> list = this.diagnostics;
        Intrinsics.checkNotNull(list);
        list.add(diagnostic);
    }

    private final boolean isSpread(FirExpression firExpression) {
        return (firExpression instanceof FirWrappedArgumentExpression) && ((FirWrappedArgumentExpression) firExpression).getIsSpread();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final List<FirValueParameter> getParameters() {
        return this.function.getValueParameters();
    }
}
