package org.jetbrains.kotlin.resolve.calls.smartcasts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.calls.ArgumentTypeResolver;
import org.jetbrains.kotlin.resolve.calls.components.InferenceSession;
import org.jetbrains.kotlin.resolve.calls.context.ResolutionContext;
import org.jetbrains.kotlin.resolve.calls.inference.BuilderInferenceSession;
import org.jetbrains.kotlin.resolve.calls.inference.components.NewTypeSubstitutor;
import org.jetbrains.kotlin.resolve.scopes.receivers.ImplicitReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.checker.IntersectionTypeKt;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;
import org.jetbrains.kotlin.util.slicedMap.WritableSlice;

/* JADX INFO: compiled from: SmartCastManager.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000~\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0003\u0018\u0000 +2\u00020\u0001:\u0002+,B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004JX\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\f2\n\u0010\r\u001a\u0006\u0012\u0002\b\u00030\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0016\b\u0002\u0010\u0013\u001a\u0010\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u0012\u0018\u00010\u0014J$\u0010\u0015\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\n2\n\u0010\u001a\u001a\u0006\u0012\u0002\b\u00030\u000eJ&\u0010\u001b\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\n2\n\u0010\u001a\u001a\u0006\u0012\u0002\b\u00030\u000eH\u0002J<\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\n0\u001d2\u0006\u0010\u001e\u001a\u00020\u00182\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020(J>\u0010)\u001a\b\u0012\u0004\u0012\u00020\n0*2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$2\u0006\u0010\u001e\u001a\u00020\u00182\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020(H\u0002J \u0010)\u001a\b\u0012\u0004\u0012\u00020\n0*2\n\u0010\u001a\u001a\u0006\u0012\u0002\b\u00030\u000e2\u0006\u0010\u001e\u001a\u00020\u0018R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006-"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/smartcasts/SmartCastManager;", "", "argumentTypeResolver", "Lorg/jetbrains/kotlin/resolve/calls/ArgumentTypeResolver;", "(Lorg/jetbrains/kotlin/resolve/calls/ArgumentTypeResolver;)V", "checkAndRecordPossibleCast", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/SmartCastResult;", "dataFlowValue", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValue;", "expectedType", "Lorg/jetbrains/kotlin/types/KotlinType;", "expression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "c", "Lorg/jetbrains/kotlin/resolve/calls/context/ResolutionContext;", "call", "Lorg/jetbrains/kotlin/psi/Call;", "recordExpressionType", "", "additionalPredicate", "Lkotlin/Function1;", "getSmartCastReceiverResult", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/SmartCastManager$ReceiverSmartCastResult;", "receiverArgument", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "receiverParameterType", "context", "getSmartCastReceiverResultWithGivenNullability", "getSmartCastVariants", "", "receiverToCast", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "containingDeclarationOrModule", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "dataFlowInfo", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "dataFlowValueFactory", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValueFactory;", "getSmartCastVariantsExcludingReceiver", "", "Companion", "ReceiverSmartCastResult", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SmartCastManager {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final ArgumentTypeResolver argumentTypeResolver;

    /* JADX INFO: compiled from: SmartCastManager.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/smartcasts/SmartCastManager$ReceiverSmartCastResult;", "", "(Ljava/lang/String;I)V", "OK", "SMARTCAST_NEEDED_OR_NOT_NULL_EXPECTED", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum ReceiverSmartCastResult {
        OK,
        SMARTCAST_NEEDED_OR_NOT_NULL_EXPECTED;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<ReceiverSmartCastResult> getEntries() {
            return $ENTRIES;
        }
    }

    public SmartCastManager(ArgumentTypeResolver argumentTypeResolver) {
        Intrinsics.checkNotNullParameter(argumentTypeResolver, "argumentTypeResolver");
        this.argumentTypeResolver = argumentTypeResolver;
    }

    public final List<KotlinType> getSmartCastVariants(ReceiverValue receiverToCast, BindingContext bindingContext, DeclarationDescriptor containingDeclarationOrModule, DataFlowInfo dataFlowInfo, LanguageVersionSettings languageVersionSettings, DataFlowValueFactory dataFlowValueFactory) {
        Intrinsics.checkNotNullParameter(receiverToCast, "receiverToCast");
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        Intrinsics.checkNotNullParameter(containingDeclarationOrModule, "containingDeclarationOrModule");
        Intrinsics.checkNotNullParameter(dataFlowInfo, "dataFlowInfo");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(dataFlowValueFactory, "dataFlowValueFactory");
        Collection<KotlinType> smartCastVariantsExcludingReceiver = getSmartCastVariantsExcludingReceiver(bindingContext, containingDeclarationOrModule, dataFlowInfo, receiverToCast, languageVersionSettings, dataFlowValueFactory);
        ArrayList arrayList = new ArrayList(smartCastVariantsExcludingReceiver.size() + 1);
        arrayList.add(receiverToCast.getType());
        arrayList.addAll(smartCastVariantsExcludingReceiver);
        return arrayList;
    }

    public final Collection<KotlinType> getSmartCastVariantsExcludingReceiver(ResolutionContext<?> context, ReceiverValue receiverToCast) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(receiverToCast, "receiverToCast");
        BindingContext context2 = context.trace.getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getBindingContext(...)");
        DeclarationDescriptor ownerDescriptor = context.scope.getOwnerDescriptor();
        DataFlowInfo dataFlowInfo = context.dataFlowInfo;
        Intrinsics.checkNotNullExpressionValue(dataFlowInfo, "dataFlowInfo");
        LanguageVersionSettings languageVersionSettings = context.languageVersionSettings;
        Intrinsics.checkNotNullExpressionValue(languageVersionSettings, "languageVersionSettings");
        DataFlowValueFactory dataFlowValueFactory = context.dataFlowValueFactory;
        Intrinsics.checkNotNullExpressionValue(dataFlowValueFactory, "dataFlowValueFactory");
        return getSmartCastVariantsExcludingReceiver(context2, ownerDescriptor, dataFlowInfo, receiverToCast, languageVersionSettings, dataFlowValueFactory);
    }

    private final Collection<KotlinType> getSmartCastVariantsExcludingReceiver(BindingContext bindingContext, DeclarationDescriptor containingDeclarationOrModule, DataFlowInfo dataFlowInfo, ReceiverValue receiverToCast, LanguageVersionSettings languageVersionSettings, DataFlowValueFactory dataFlowValueFactory) {
        return dataFlowInfo.getCollectedTypes(dataFlowValueFactory.createDataFlowValue(receiverToCast, bindingContext, containingDeclarationOrModule), languageVersionSettings);
    }

    public final ReceiverSmartCastResult getSmartCastReceiverResult(ReceiverValue receiverArgument, KotlinType receiverParameterType, ResolutionContext<?> context) {
        Intrinsics.checkNotNullParameter(receiverArgument, "receiverArgument");
        Intrinsics.checkNotNullParameter(receiverParameterType, "receiverParameterType");
        Intrinsics.checkNotNullParameter(context, "context");
        ReceiverSmartCastResult smartCastReceiverResultWithGivenNullability = getSmartCastReceiverResultWithGivenNullability(receiverArgument, receiverParameterType, context);
        if (smartCastReceiverResultWithGivenNullability != null) {
            return smartCastReceiverResultWithGivenNullability;
        }
        KotlinType kotlinTypeMakeNullable = TypeUtils.makeNullable(receiverParameterType);
        Intrinsics.checkNotNullExpressionValue(kotlinTypeMakeNullable, "makeNullable(...)");
        if (getSmartCastReceiverResultWithGivenNullability(receiverArgument, kotlinTypeMakeNullable, context) == null) {
            return null;
        }
        return ReceiverSmartCastResult.SMARTCAST_NEEDED_OR_NOT_NULL_EXPECTED;
    }

    private final ReceiverSmartCastResult getSmartCastReceiverResultWithGivenNullability(ReceiverValue receiverArgument, KotlinType receiverParameterType, ResolutionContext<?> context) {
        if (this.argumentTypeResolver.isSubtypeOfForArgumentType(receiverArgument.getType(), receiverParameterType)) {
            return ReceiverSmartCastResult.OK;
        }
        Collection<KotlinType> smartCastVariantsExcludingReceiver = getSmartCastVariantsExcludingReceiver(context, receiverArgument);
        if (!(smartCastVariantsExcludingReceiver instanceof Collection) || !smartCastVariantsExcludingReceiver.isEmpty()) {
            Iterator<T> it = smartCastVariantsExcludingReceiver.iterator();
            while (it.hasNext()) {
                if (this.argumentTypeResolver.isSubtypeOfForArgumentType((KotlinType) it.next(), receiverParameterType)) {
                    return ReceiverSmartCastResult.SMARTCAST_NEEDED_OR_NOT_NULL_EXPECTED;
                }
            }
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ SmartCastResult checkAndRecordPossibleCast$default(SmartCastManager smartCastManager, DataFlowValue dataFlowValue, KotlinType kotlinType, KtExpression ktExpression, ResolutionContext resolutionContext, Call call, boolean z, Function1 function1, int i, Object obj) {
        return smartCastManager.checkAndRecordPossibleCast(dataFlowValue, kotlinType, ktExpression, resolutionContext, call, z, (i & 64) != 0 ? null : function1);
    }

    public final SmartCastResult checkAndRecordPossibleCast(DataFlowValue dataFlowValue, KotlinType expectedType, KtExpression expression, ResolutionContext<?> c, Call call, boolean recordExpressionType, Function1<? super KotlinType, Boolean> additionalPredicate) {
        List listListOf;
        Set<KotlinType> set;
        ImplicitSmartCasts implicitSmartCastsPlus;
        KotlinType kotlinType;
        Intrinsics.checkNotNullParameter(dataFlowValue, "dataFlowValue");
        Intrinsics.checkNotNullParameter(expectedType, "expectedType");
        Intrinsics.checkNotNullParameter(c, "c");
        KtExpression calleeExpression = call != null ? call.getCalleeExpression() : null;
        if (c.languageVersionSettings.supportsFeature(LanguageFeature.NewInference)) {
            listListOf = TypeUtilsKt.expandIntersectionTypeIfNecessary(expectedType);
        } else {
            listListOf = CollectionsKt.listOf(expectedType);
        }
        InferenceSession inferenceSession = c.inferenceSession;
        BuilderInferenceSession builderInferenceSession = inferenceSession instanceof BuilderInferenceSession ? (BuilderInferenceSession) inferenceSession : null;
        NewTypeSubstitutor notFixedToInferredTypesSubstitutor = builderInferenceSession != null ? builderInferenceSession.getNotFixedToInferredTypesSubstitutor() : null;
        DataFlowInfo dataFlowInfo = c.dataFlowInfo;
        LanguageVersionSettings languageVersionSettings = c.languageVersionSettings;
        Intrinsics.checkNotNullExpressionValue(languageVersionSettings, "languageVersionSettings");
        Set<KotlinType> collectedTypes = dataFlowInfo.getCollectedTypes(dataFlowValue, languageVersionSettings);
        if (notFixedToInferredTypesSubstitutor != null) {
            Set<KotlinType> set2 = collectedTypes;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(set2, 10));
            Iterator<T> it = set2.iterator();
            while (it.hasNext()) {
                arrayList.add(notFixedToInferredTypesSubstitutor.safeSubstitute(((KotlinType) it.next()).unwrap()));
            }
            set = arrayList;
        } else {
            set = collectedTypes;
        }
        List<KotlinType> mutableList = CollectionsKt.toMutableList(set);
        List list = mutableList;
        if (!list.isEmpty() && c.languageVersionSettings.supportsFeature(LanguageFeature.NewInference)) {
            org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(list, IntersectionTypeKt.intersectWrappedTypes(CollectionsKt.plus((Collection<? extends KotlinType>) list, dataFlowValue.getType())));
        }
        for (KotlinType kotlinType2 : mutableList) {
            Iterable iterable = listListOf;
            if (!(iterable instanceof Collection) || !((Collection) iterable).isEmpty()) {
                Iterator it2 = iterable.iterator();
                while (it2.hasNext()) {
                    if (this.argumentTypeResolver.isSubtypeOfForArgumentType(kotlinType2, (KotlinType) it2.next())) {
                        if (additionalPredicate != null && !additionalPredicate.invoke(kotlinType2).booleanValue()) {
                            break;
                        }
                        if (expression != null) {
                            Companion companion = INSTANCE;
                            BindingTrace trace = c.trace;
                            Intrinsics.checkNotNullExpressionValue(trace, "trace");
                            companion.recordCastOrError(expression, kotlinType2, trace, dataFlowValue, call, recordExpressionType);
                        } else if (calleeExpression != null && dataFlowValue.getIsStable()) {
                            IdentifierInfo identifierInfo = dataFlowValue.getIdentifierInfo();
                            IdentifierInfo.Receiver receiver = identifierInfo instanceof IdentifierInfo.Receiver ? (IdentifierInfo.Receiver) identifierInfo : null;
                            ReceiverValue value = receiver != null ? receiver.getValue() : null;
                            if (value instanceof ImplicitReceiver) {
                                ImplicitSmartCasts implicitSmartCasts = (ImplicitSmartCasts) c.trace.get(BindingContext.IMPLICIT_RECEIVER_SMARTCAST, calleeExpression);
                                ImplicitSmartCasts implicitSmartCasts2 = new ImplicitSmartCasts((ImplicitReceiver) value, kotlinType2);
                                if (implicitSmartCasts != null && (kotlinType = implicitSmartCasts.getReceiverTypes().get(value)) != null && !Intrinsics.areEqual(kotlinType, kotlinType2)) {
                                    throw new AssertionError("Rewriting key " + value + " for implicit smart cast on " + calleeExpression.getText() + ": was " + kotlinType + ", now " + kotlinType2);
                                }
                                BindingTrace bindingTrace = c.trace;
                                WritableSlice<KtExpression, ImplicitSmartCasts> writableSlice = BindingContext.IMPLICIT_RECEIVER_SMARTCAST;
                                if (implicitSmartCasts != null && (implicitSmartCastsPlus = implicitSmartCasts.plus(implicitSmartCasts2)) != null) {
                                    implicitSmartCasts2 = implicitSmartCastsPlus;
                                }
                                bindingTrace.record(writableSlice, calleeExpression, implicitSmartCasts2);
                            }
                        }
                        return new SmartCastResult(kotlinType2, dataFlowValue.getIsStable());
                    }
                }
            }
        }
        if (c.dataFlowInfo.getCollectedNullability(dataFlowValue).canBeNull() || expectedType.getIsMarkedNullable()) {
            return null;
        }
        boolean zCanBeNull = dataFlowValue.getImmanentNullability().canBeNull();
        KotlinType kotlinTypeMakeNullable = TypeUtils.makeNullable(expectedType);
        Intrinsics.checkNotNullExpressionValue(kotlinTypeMakeNullable, "makeNullable(...)");
        if (this.argumentTypeResolver.isSubtypeOfForArgumentType(dataFlowValue.getType(), kotlinTypeMakeNullable) && (additionalPredicate == null || additionalPredicate.invoke(dataFlowValue.getType()).booleanValue())) {
            if (zCanBeNull && expression != null) {
                Companion companion2 = INSTANCE;
                KotlinType type = dataFlowValue.getType();
                BindingTrace trace2 = c.trace;
                Intrinsics.checkNotNullExpressionValue(trace2, "trace");
                companion2.recordCastOrError(expression, type, trace2, dataFlowValue, call, recordExpressionType);
            }
            return new SmartCastResult(dataFlowValue.getType(), !zCanBeNull || dataFlowValue.getIsStable());
        }
        return checkAndRecordPossibleCast$default(this, dataFlowValue, kotlinTypeMakeNullable, expression, c, call, recordExpressionType, null, 64, null);
    }

    /* JADX INFO: compiled from: SmartCastManager.kt */
    @Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J:\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J@\u0010\u0011\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u0007\u001a\u00020\b2\u0014\u0010\u0012\u001a\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0014\u0018\u00010\u0013H\u0002¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/smartcasts/SmartCastManager$Companion;", "", "()V", "recordCastOrError", "", "expression", "Lorg/jetbrains/kotlin/psi/KtExpression;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "dataFlowValue", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValue;", "call", "Lorg/jetbrains/kotlin/psi/Call;", "recordExpressionType", "", "updateSmartCast", ASTExpr.DEFAULT_MAP_KEY_NAME, "Lorg/jetbrains/kotlin/util/slicedMap/WritableSlice;", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/ExplicitSmartCasts;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void recordCastOrError(KtExpression expression, KotlinType type, BindingTrace trace, DataFlowValue dataFlowValue, Call call, boolean recordExpressionType) {
            if (KotlinBuiltIns.isNullableNothing(type)) {
                return;
            }
            if (dataFlowValue.getIsStable()) {
                if (dataFlowValue.getKind() == DataFlowValue.Kind.LEGACY_ALIEN_BASE_PROPERTY || dataFlowValue.getKind() == DataFlowValue.Kind.LEGACY_ALIEN_BASE_PROPERTY_INHERITED_IN_INVISIBLE_CLASS || dataFlowValue.getKind() == DataFlowValue.Kind.LEGACY_STABLE_LOCAL_DELEGATED_PROPERTY) {
                    trace.report(Errors.DEPRECATED_SMARTCAST.on(expression, type, expression.getText(), dataFlowValue.getKind().getDescription()));
                }
                updateSmartCast(trace, expression, call, type, BindingContext.SMARTCAST);
                if (recordExpressionType) {
                    trace.recordType(expression, type);
                    return;
                }
                return;
            }
            updateSmartCast(trace, expression, call, type, BindingContext.UNSTABLE_SMARTCAST);
            trace.report(Errors.SMARTCAST_IMPOSSIBLE.on(expression, type, expression.getText(), dataFlowValue.getKind().getDescription()));
        }

        private final void updateSmartCast(BindingTrace trace, KtExpression expression, Call call, KotlinType type, WritableSlice<KtExpression, ExplicitSmartCasts> key) {
            SingleSmartCast singleSmartCastPlus;
            KotlinType kotlinTypeType;
            ExplicitSmartCasts explicitSmartCasts = (ExplicitSmartCasts) trace.get(key, expression);
            SingleSmartCast singleSmartCast = new SingleSmartCast(call, type);
            if (explicitSmartCasts != null && (kotlinTypeType = explicitSmartCasts.type(call)) != null && !Intrinsics.areEqual(kotlinTypeType, type)) {
                throw new AssertionError("Rewriting key " + call + " for smart cast on " + expression.getText());
            }
            if (explicitSmartCasts == null || (singleSmartCastPlus = explicitSmartCasts.plus(singleSmartCast)) == null) {
                singleSmartCastPlus = singleSmartCast;
            }
            trace.record(key, expression, singleSmartCastPlus);
        }
    }
}
