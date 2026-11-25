package org.jetbrains.kotlin.resolve.calls.inference.components;

import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.resolve.calls.components.PostponeArgumentsChecksKt;
import org.jetbrains.kotlin.resolve.calls.components.candidate.CallableReferenceResolutionCandidate;
import org.jetbrains.kotlin.resolve.calls.components.candidate.SimpleResolutionCandidate;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemBuilder;
import org.jetbrains.kotlin.resolve.calls.inference.model.ArgumentConstraintPositionImpl;
import org.jetbrains.kotlin.resolve.calls.inference.model.BuilderInferenceSubstitutionConstraintPosition;
import org.jetbrains.kotlin.resolve.calls.inference.model.Constraint;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintPosition;
import org.jetbrains.kotlin.resolve.calls.inference.model.FixVariableConstraintPositionImpl;
import org.jetbrains.kotlin.resolve.calls.inference.model.IncorporationConstraintPosition;
import org.jetbrains.kotlin.resolve.calls.inference.model.InferredIntoDeclaredUpperBounds;
import org.jetbrains.kotlin.resolve.calls.inference.model.NotEnoughInformationForTypeParameterImpl;
import org.jetbrains.kotlin.resolve.calls.inference.model.TypeVariableForLambdaParameterType;
import org.jetbrains.kotlin.resolve.calls.inference.model.TypeVariableForLambdaReturnType;
import org.jetbrains.kotlin.resolve.calls.inference.model.TypeVariableFromCallableDescriptor;
import org.jetbrains.kotlin.resolve.calls.inference.model.TypeVariableTypeConstructor;
import org.jetbrains.kotlin.resolve.calls.inference.model.VariableWithConstraints;
import org.jetbrains.kotlin.resolve.calls.model.CallableReferenceWithRevisedExpectedTypeAtom;
import org.jetbrains.kotlin.resolve.calls.model.KotlinConstraintSystemDiagnostic;
import org.jetbrains.kotlin.resolve.calls.model.KotlinDiagnosticsHolder;
import org.jetbrains.kotlin.resolve.calls.model.LambdaKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.LambdaWithTypeVariableAsExpectedTypeAtom;
import org.jetbrains.kotlin.resolve.calls.model.MultiLambdaBuilderInferenceRestriction;
import org.jetbrains.kotlin.resolve.calls.model.NotEnoughInformationForLambdaParameter;
import org.jetbrains.kotlin.resolve.calls.model.PostponedAtomWithRevisableExpectedType;
import org.jetbrains.kotlin.resolve.calls.model.PostponedCallableReferenceAtom;
import org.jetbrains.kotlin.resolve.calls.model.PostponedResolvedAtom;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedAtom;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCallAtom;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCallableReferenceArgumentAtom;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedLambdaAtom;
import org.jetbrains.kotlin.resolve.calls.model.StubResolvedAtom;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeFactory;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;
import org.jetbrains.kotlin.types.error.ErrorType;
import org.jetbrains.kotlin.types.error.ErrorTypeKind;
import org.jetbrains.kotlin.types.error.ErrorUtils;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.MarkerExtensionsKt;
import org.jetbrains.kotlin.types.model.TypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeSubstitutorMarker;
import org.jetbrains.kotlin.types.model.TypeVariableMarker;
import org.jetbrains.kotlin.types.model.TypeVariableTypeConstructorMarker;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: KotlinConstraintSystemCompleter.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0092\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 72\u00020\u0001:\u00017B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ4\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u00142\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019J.\u0010\u001a\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u001b\u001a\u00020\u001c2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u00142\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J6\u0010\u001a\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u00142\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\u001e\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020 2\u0006\u0010\"\u001a\u00020#2\u0006\u0010\u0018\u001a\u00020\u0019J \u0010$\u001a\u00020\u000e2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010%\u001a\u00020&H\u0002JH\u0010'\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u00172\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u00142\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0018\u001a\u00020\u00192\u0012\u0010(\u001a\u000e\u0012\u0004\u0012\u00020*\u0012\u0004\u0012\u00020\u000e0)J \u0010+\u001a\u00020,2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010-\u001a\u00020.2\u0006\u0010\u0018\u001a\u00020\u0019H\u0002JH\u0010/\u001a\u00020,*\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u00172\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u00142\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u00100\u001a\u00020,2\f\u00101\u001a\b\u0012\u0004\u0012\u00020*0\u00142\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J(\u00102\u001a\b\u0012\u0004\u0012\u0002030\u0014*\u00020\u00102\u0006\u00100\u001a\u00020,2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014H\u0002J*\u00104\u001a\u00020\u000e*\u00020\u00102\u0006\u0010\u001b\u001a\u00020\u001c2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u00142\u0006\u0010\u0018\u001a\u00020\u0019H\u0002JH\u00105\u001a\u00020\u000e*\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u00172\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u00142\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u00100\u001a\u00020,2\f\u00101\u001a\b\u0012\u0004\u0012\u00020*0\u00142\u0006\u0010\u0018\u001a\u00020\u0019H\u0002JN\u0010'\u001a\u00020\u000e*\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u00172\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u00142\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u00100\u001a\u00020,2\u0012\u0010(\u001a\u000e\u0012\u0004\u0012\u00020*\u0012\u0004\u0012\u00020\u000e0)H\u0002J\\\u00106\u001a\u00020,*\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u00172\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u00142\u0006\u0010\u0011\u001a\u00020\u00122\f\u00101\u001a\b\u0012\u0004\u0012\u00020*0\u00142\u0006\u00100\u001a\u00020,2\u0006\u0010\u0018\u001a\u00020\u00192\u0012\u0010(\u001a\u000e\u0012\u0004\u0012\u00020*\u0012\u0004\u0012\u00020\u000e0)H\u0002R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u00068"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/components/KotlinConstraintSystemCompleter;", "", "resultTypeResolver", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/ResultTypeResolver;", "variableFixationFinder", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/VariableFixationFinder;", "postponedArgumentsInputTypesResolver", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/PostponedArgumentInputTypesResolver;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "(Lorg/jetbrains/kotlin/resolve/calls/inference/components/ResultTypeResolver;Lorg/jetbrains/kotlin/resolve/calls/inference/components/VariableFixationFinder;Lorg/jetbrains/kotlin/resolve/calls/inference/components/PostponedArgumentInputTypesResolver;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;)V", "getVariableFixationFinder", "()Lorg/jetbrains/kotlin/resolve/calls/inference/components/VariableFixationFinder;", "completeConstraintSystem", "", "c", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintSystemCompletionContext;", "topLevelType", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "topLevelAtoms", "", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedAtom;", "completionMode", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintSystemCompletionMode;", "diagnosticsHolder", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinDiagnosticsHolder;", "fixVariable", "variableWithConstraints", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/VariableWithConstraints;", HiAnalyticsConstant.HaKey.BI_KEY_DIRECTION, "Lorg/jetbrains/kotlin/resolve/calls/inference/components/TypeVariableDirectionCalculator$ResolveDirection;", "prepareLambdaAtomForFactoryPattern", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedLambdaAtom;", "atom", "candidate", "Lorg/jetbrains/kotlin/resolve/calls/components/candidate/SimpleResolutionCandidate;", "reportWarningIfFixedIntoDeclaredUpperBounds", "resultType", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "runCompletion", "analyze", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/resolve/calls/model/PostponedResolvedAtom;", "transformToAtomWithNewFunctionalExpectedType", "", "argument", "Lorg/jetbrains/kotlin/resolve/calls/model/PostponedAtomWithRevisableExpectedType;", "fixNextReadyVariable", "collectVariablesFromContext", "postponedArguments", "getOrderedAllTypeVariables", "Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;", "processVariableWhenNotEnoughInformation", "reportNotEnoughTypeInformation", "tryToCompleteWithBuilderInference", "Companion", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinConstraintSystemCompleter {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final LanguageVersionSettings languageVersionSettings;
    private final PostponedArgumentInputTypesResolver postponedArgumentsInputTypesResolver;
    private final ResultTypeResolver resultTypeResolver;
    private final VariableFixationFinder variableFixationFinder;

    public KotlinConstraintSystemCompleter(ResultTypeResolver resultTypeResolver, VariableFixationFinder variableFixationFinder, PostponedArgumentInputTypesResolver postponedArgumentsInputTypesResolver, LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(resultTypeResolver, "resultTypeResolver");
        Intrinsics.checkNotNullParameter(variableFixationFinder, "variableFixationFinder");
        Intrinsics.checkNotNullParameter(postponedArgumentsInputTypesResolver, "postponedArgumentsInputTypesResolver");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        this.resultTypeResolver = resultTypeResolver;
        this.variableFixationFinder = variableFixationFinder;
        this.postponedArgumentsInputTypesResolver = postponedArgumentsInputTypesResolver;
        this.languageVersionSettings = languageVersionSettings;
    }

    public final VariableFixationFinder getVariableFixationFinder() {
        return this.variableFixationFinder;
    }

    public final void runCompletion(ConstraintSystemCompletionContext c, ConstraintSystemCompletionMode completionMode, List<? extends ResolvedAtom> topLevelAtoms, UnwrappedType topLevelType, KotlinDiagnosticsHolder diagnosticsHolder, Function1<? super PostponedResolvedAtom, Unit> analyze) {
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(completionMode, "completionMode");
        Intrinsics.checkNotNullParameter(topLevelAtoms, "topLevelAtoms");
        Intrinsics.checkNotNullParameter(topLevelType, "topLevelType");
        Intrinsics.checkNotNullParameter(diagnosticsHolder, "diagnosticsHolder");
        Intrinsics.checkNotNullParameter(analyze, "analyze");
        runCompletion(c, completionMode, topLevelAtoms, topLevelType, diagnosticsHolder, false, analyze);
    }

    public final void completeConstraintSystem(ConstraintSystemCompletionContext c, UnwrappedType topLevelType, List<? extends ResolvedAtom> topLevelAtoms, ConstraintSystemCompletionMode completionMode, KotlinDiagnosticsHolder diagnosticsHolder) {
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(topLevelType, "topLevelType");
        Intrinsics.checkNotNullParameter(topLevelAtoms, "topLevelAtoms");
        Intrinsics.checkNotNullParameter(completionMode, "completionMode");
        Intrinsics.checkNotNullParameter(diagnosticsHolder, "diagnosticsHolder");
        runCompletion(c, completionMode, topLevelAtoms, topLevelType, diagnosticsHolder, true, new Function1<PostponedResolvedAtom, Unit>() { // from class: org.jetbrains.kotlin.resolve.calls.inference.components.KotlinConstraintSystemCompleter.completeConstraintSystem.1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(PostponedResolvedAtom postponedResolvedAtom) {
                invoke2(postponedResolvedAtom);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(PostponedResolvedAtom it) {
                Intrinsics.checkNotNullParameter(it, "it");
                throw new IllegalStateException("Shouldn't be called in complete constraint system mode".toString());
            }
        });
    }

    private final void runCompletion(ConstraintSystemCompletionContext constraintSystemCompletionContext, ConstraintSystemCompletionMode constraintSystemCompletionMode, final List<? extends ResolvedAtom> list, UnwrappedType unwrappedType, KotlinDiagnosticsHolder kotlinDiagnosticsHolder, boolean z, Function1<? super PostponedResolvedAtom, Unit> function1) {
        UnwrappedType unwrappedType2 = unwrappedType;
        Set<TypeVariableTypeConstructorMarker> setExtractTypeVariables = constraintSystemCompletionContext.extractTypeVariables(unwrappedType2);
        while (true) {
            List<PostponedResolvedAtom> orderedNotAnalyzedPostponedArguments = INSTANCE.getOrderedNotAnalyzedPostponedArguments(list);
            if (constraintSystemCompletionMode == ConstraintSystemCompletionMode.UNTIL_FIRST_LAMBDA && constraintSystemCompletionContext.hasLambdaToAnalyze(this.languageVersionSettings, orderedNotAnalyzedPostponedArguments)) {
                return;
            }
            if (!constraintSystemCompletionContext.analyzeArgumentWithFixedParameterTypes(this.languageVersionSettings, orderedNotAnalyzedPostponedArguments, function1)) {
                boolean z2 = this.variableFixationFinder.findFirstVariableForFixation(constraintSystemCompletionContext, getOrderedAllTypeVariables(constraintSystemCompletionContext, z, list), orderedNotAnalyzedPostponedArguments, constraintSystemCompletionMode, unwrappedType2) != null;
                if (orderedNotAnalyzedPostponedArguments.isEmpty() && !z2) {
                    return;
                }
                ArrayList arrayList = new ArrayList();
                for (Object obj : orderedNotAnalyzedPostponedArguments) {
                    if (obj instanceof PostponedAtomWithRevisableExpectedType) {
                        arrayList.add(obj);
                    }
                }
                ArrayList arrayList2 = arrayList;
                TypeVariableDependencyInformationProvider typeVariableDependencyInformationProvider = new TypeVariableDependencyInformationProvider(constraintSystemCompletionContext.getNotFixedTypeVariables(), orderedNotAnalyzedPostponedArguments, unwrappedType2, constraintSystemCompletionContext);
                if (this.postponedArgumentsInputTypesResolver.collectParameterTypesAndBuildNewExpectedTypes(constraintSystemCompletionContext, arrayList2, constraintSystemCompletionMode, typeVariableDependencyInformationProvider, setExtractTypeVariables)) {
                    continue;
                } else {
                    if (constraintSystemCompletionMode == ConstraintSystemCompletionMode.FULL) {
                        Iterator<PostponedResolvedAtom> it = orderedNotAnalyzedPostponedArguments.iterator();
                        while (true) {
                            if (it.hasNext()) {
                                TypeVariableDependencyInformationProvider typeVariableDependencyInformationProvider2 = typeVariableDependencyInformationProvider;
                                if (!this.postponedArgumentsInputTypesResolver.fixNextReadyVariableForParameterTypeIfNeeded(constraintSystemCompletionContext, it.next(), orderedNotAnalyzedPostponedArguments, unwrappedType2, typeVariableDependencyInformationProvider, new Function1<TypeVariableMarker, Object>() { // from class: org.jetbrains.kotlin.resolve.calls.inference.components.KotlinConstraintSystemCompleter$runCompletion$variableWasFixed$1
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    /* JADX WARN: Multi-variable type inference failed */
                                    {
                                        super(1);
                                    }

                                    @Override // kotlin.jvm.functions.Function1
                                    public final Object invoke(TypeVariableMarker it2) {
                                        Intrinsics.checkNotNullParameter(it2, "it");
                                        ResolvedAtom resolvedAtomFindResolvedAtomBy = KotlinConstraintSystemCompleter.INSTANCE.findResolvedAtomBy(it2, list);
                                        return resolvedAtomFindResolvedAtomBy == null ? CollectionsKt.firstOrNull((List) list) : resolvedAtomFindResolvedAtomBy;
                                    }
                                })) {
                                    typeVariableDependencyInformationProvider = typeVariableDependencyInformationProvider2;
                                }
                            } else {
                                Iterator it2 = arrayList2.iterator();
                                while (true) {
                                    if (it2.hasNext()) {
                                        if (transformToAtomWithNewFunctionalExpectedType(constraintSystemCompletionContext, (PostponedAtomWithRevisableExpectedType) it2.next(), kotlinDiagnosticsHolder)) {
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (!constraintSystemCompletionContext.analyzeNextReadyPostponedArgument(this.languageVersionSettings, orderedNotAnalyzedPostponedArguments, constraintSystemCompletionMode, function1) && !fixNextReadyVariable(constraintSystemCompletionContext, constraintSystemCompletionMode, list, unwrappedType, z, orderedNotAnalyzedPostponedArguments, kotlinDiagnosticsHolder) && !tryToCompleteWithBuilderInference(constraintSystemCompletionContext, constraintSystemCompletionMode, list, unwrappedType, orderedNotAnalyzedPostponedArguments, z, kotlinDiagnosticsHolder, function1)) {
                        reportNotEnoughTypeInformation(constraintSystemCompletionContext, constraintSystemCompletionMode, list, unwrappedType, z, orderedNotAnalyzedPostponedArguments, kotlinDiagnosticsHolder);
                        if (constraintSystemCompletionMode != ConstraintSystemCompletionMode.FULL || !constraintSystemCompletionContext.analyzeRemainingNotAnalyzedPostponedArgument(orderedNotAnalyzedPostponedArguments, function1)) {
                            return;
                        }
                    }
                }
            }
        }
    }

    public final ResolvedLambdaAtom prepareLambdaAtomForFactoryPattern(ResolvedLambdaAtom atom, SimpleResolutionCandidate candidate, KotlinDiagnosticsHolder diagnosticsHolder) {
        Intrinsics.checkNotNullParameter(atom, "atom");
        Intrinsics.checkNotNullParameter(candidate, "candidate");
        Intrinsics.checkNotNullParameter(diagnosticsHolder, "diagnosticsHolder");
        TypeVariableForLambdaReturnType typeVariableForLambdaReturnType = new TypeVariableForLambdaReturnType(candidate.getCallComponents().getBuiltIns(), PostponedArgumentInputTypesResolver.TYPE_VARIABLE_NAME_FOR_LAMBDA_RETURN_TYPE);
        ConstraintSystemBuilder builder = candidate.getSystem().getBuilder();
        builder.registerVariable(typeVariableForLambdaReturnType);
        TypeSubstitutorMarker typeSubstitutorMarkerBuildCurrentSubstitutor = builder.buildCurrentSubstitutor();
        ConstraintSystemCompletionContext constraintSystemCompletionContextAsConstraintSystemCompleterContext = candidate.getSystem().asConstraintSystemCompleterContext();
        UnwrappedType expectedType = atom.getExpectedType();
        Intrinsics.checkNotNull(expectedType);
        KotlinTypeMarker kotlinTypeMarkerSafeSubstitute = MarkerExtensionsKt.safeSubstitute(typeSubstitutorMarkerBuildCurrentSubstitutor, constraintSystemCompletionContextAsConstraintSystemCompleterContext, expectedType);
        Intrinsics.checkNotNull(kotlinTypeMarkerSafeSubstitute, "null cannot be cast to non-null type org.jetbrains.kotlin.types.KotlinType");
        KotlinType kotlinType = (KotlinType) kotlinTypeMarkerSafeSubstitute;
        SimpleType simpleTypeSimpleType$default = KotlinTypeFactory.simpleType$default(kotlinType.getAttributes(), kotlinType.getConstructor(), CollectionsKt.plus((Collection<? extends TypeProjection>) CollectionsKt.dropLast(kotlinType.getArguments(), 1), TypeUtilsKt.asTypeProjection(typeVariableForLambdaReturnType.getDefaultType())), kotlinType.getIsMarkedNullable(), (KotlinTypeRefiner) null, 16, (Object) null);
        builder.addSubtypeConstraint(simpleTypeSimpleType$default, kotlinType, new ArgumentConstraintPositionImpl(atom.getAtom()));
        return PostponeArgumentsChecksKt.transformToResolvedLambda(atom, builder, diagnosticsHolder, simpleTypeSimpleType$default, typeVariableForLambdaReturnType);
    }

    /* JADX WARN: Code duplicated, block: B:37:0x009b  */
    private final boolean tryToCompleteWithBuilderInference(ConstraintSystemCompletionContext constraintSystemCompletionContext, ConstraintSystemCompletionMode constraintSystemCompletionMode, List<? extends ResolvedAtom> list, UnwrappedType unwrappedType, List<? extends PostponedResolvedAtom> list2, boolean z, KotlinDiagnosticsHolder kotlinDiagnosticsHolder, Function1<? super PostponedResolvedAtom, Unit> function1) {
        boolean z2;
        int i;
        if (constraintSystemCompletionMode != ConstraintSystemCompletionMode.FULL || !this.languageVersionSettings.supportsFeature(LanguageFeature.UseBuilderInferenceOnlyIfNeeded)) {
            return false;
        }
        ArrayList arrayList = new ArrayList();
        for (Object obj : list2) {
            if (obj instanceof ResolvedLambdaAtom) {
                arrayList.add(obj);
            }
        }
        ArrayList<ResolvedLambdaAtom> arrayList2 = arrayList;
        if (arrayList2.isEmpty()) {
            arrayList2 = null;
        }
        if (arrayList2 == null) {
            return false;
        }
        boolean zSupportsFeature = this.languageVersionSettings.supportsFeature(LanguageFeature.UseBuilderInferenceWithoutAnnotation);
        boolean zSupportsFeature2 = this.languageVersionSettings.supportsFeature(LanguageFeature.NoBuilderInferenceWithoutAnnotationRestriction);
        if (arrayList2.size() >= 2) {
            ArrayList arrayList3 = arrayList2;
            if ((arrayList3 instanceof Collection) && arrayList3.isEmpty()) {
                i = 0;
            } else {
                Iterator it = arrayList3.iterator();
                i = 0;
                while (it.hasNext()) {
                    if (!tryToCompleteWithBuilderInference$notFixedInputTypeVariables((ResolvedLambdaAtom) it.next(), constraintSystemCompletionContext).isEmpty() && (i = i + 1) < 0) {
                        CollectionsKt.throwCountOverflow();
                    }
                }
            }
            if (i >= 2) {
                z2 = true;
            } else {
                z2 = false;
            }
        } else {
            z2 = false;
        }
        ConstraintSystemBuilder builder = constraintSystemCompletionContext.getBuilder();
        for (ResolvedLambdaAtom resolvedLambdaAtom : arrayList2) {
            boolean hasBuilderInferenceAnnotation = resolvedLambdaAtom.getAtom().getHasBuilderInferenceAnnotation();
            if (hasBuilderInferenceAnnotation || zSupportsFeature) {
                if (!hasBuilderInferenceAnnotation) {
                    resolvedLambdaAtom.getAtom().setHasBuilderInferenceAnnotation(true);
                }
                List<TypeVariableTypeConstructorMarker> listTryToCompleteWithBuilderInference$notFixedInputTypeVariables = tryToCompleteWithBuilderInference$notFixedInputTypeVariables(resolvedLambdaAtom, constraintSystemCompletionContext);
                if (!listTryToCompleteWithBuilderInference$notFixedInputTypeVariables.isEmpty()) {
                    if (!zSupportsFeature2 && z2 && !hasBuilderInferenceAnnotation) {
                        Iterator<TypeVariableTypeConstructorMarker> it2 = listTryToCompleteWithBuilderInference$notFixedInputTypeVariables.iterator();
                        while (it2.hasNext()) {
                            kotlinDiagnosticsHolder.addDiagnostic(new MultiLambdaBuilderInferenceRestriction(resolvedLambdaAtom.getAtom(), constraintSystemCompletionContext.getTypeParameter(it2.next())));
                        }
                    }
                    Iterator<TypeVariableTypeConstructorMarker> it3 = listTryToCompleteWithBuilderInference$notFixedInputTypeVariables.iterator();
                    while (it3.hasNext()) {
                        builder.markPostponedVariable(((VariableWithConstraints) MapsKt.getValue(constraintSystemCompletionContext.getNotFixedTypeVariables(), it3.next())).getTypeVariable());
                    }
                    function1.invoke(resolvedLambdaAtom);
                }
            }
        }
        VariableFixationFinder.VariableForFixation variableForFixationFindFirstVariableForFixation = this.variableFixationFinder.findFirstVariableForFixation(constraintSystemCompletionContext, getOrderedAllTypeVariables(constraintSystemCompletionContext, z, list), list2, constraintSystemCompletionMode, unwrappedType);
        return variableForFixationFindFirstVariableForFixation != null && variableForFixationFindFirstVariableForFixation.getHasProperConstraint();
    }

    private static final List<TypeVariableTypeConstructorMarker> tryToCompleteWithBuilderInference$notFixedInputTypeVariables(ResolvedLambdaAtom resolvedLambdaAtom, ConstraintSystemCompletionContext constraintSystemCompletionContext) {
        Collection<UnwrappedType> inputTypes = resolvedLambdaAtom.getInputTypes();
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = inputTypes.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, constraintSystemCompletionContext.extractTypeVariables((UnwrappedType) it.next()));
        }
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : arrayList) {
            if (!constraintSystemCompletionContext.getFixedTypeVariables().containsKey((TypeVariableTypeConstructorMarker) obj)) {
                arrayList2.add(obj);
            }
        }
        return arrayList2;
    }

    private final boolean transformToAtomWithNewFunctionalExpectedType(ConstraintSystemCompletionContext c, PostponedAtomWithRevisableExpectedType argument, KotlinDiagnosticsHolder diagnosticsHolder) {
        KotlinTypeMarker revisedExpectedType = argument.getRevisedExpectedType();
        if (revisedExpectedType == null || !c.isFunctionOrKFunctionWithAnySuspendability(revisedExpectedType)) {
            revisedExpectedType = null;
        }
        UnwrappedType unwrappedType = (UnwrappedType) revisedExpectedType;
        if (unwrappedType == null) {
            return false;
        }
        if (argument instanceof PostponedCallableReferenceAtom) {
            PostponedCallableReferenceAtom postponedCallableReferenceAtom = (PostponedCallableReferenceAtom) argument;
            postponedCallableReferenceAtom.setAnalyzedResults(null, CollectionsKt.listOf(new CallableReferenceWithRevisedExpectedTypeAtom(postponedCallableReferenceAtom.getAtom(), unwrappedType)));
            return true;
        }
        if (argument instanceof LambdaWithTypeVariableAsExpectedTypeAtom) {
            PostponeArgumentsChecksKt.transformToResolvedLambda$default((LambdaWithTypeVariableAsExpectedTypeAtom) argument, c.getBuilder(), diagnosticsHolder, unwrappedType, (TypeVariableForLambdaReturnType) null, 8, (Object) null);
            return true;
        }
        throw new IllegalStateException("Unsupported postponed argument type of " + argument);
    }

    private final boolean fixNextReadyVariable(ConstraintSystemCompletionContext constraintSystemCompletionContext, ConstraintSystemCompletionMode constraintSystemCompletionMode, List<? extends ResolvedAtom> list, UnwrappedType unwrappedType, boolean z, List<? extends PostponedResolvedAtom> list2, KotlinDiagnosticsHolder kotlinDiagnosticsHolder) {
        VariableFixationFinder.VariableForFixation variableForFixationFindFirstVariableForFixation = this.variableFixationFinder.findFirstVariableForFixation(constraintSystemCompletionContext, getOrderedAllTypeVariables(constraintSystemCompletionContext, z, list), list2, constraintSystemCompletionMode, unwrappedType);
        if (variableForFixationFindFirstVariableForFixation == null || !variableForFixationFindFirstVariableForFixation.getHasProperConstraint()) {
            return false;
        }
        fixVariable(constraintSystemCompletionContext, (VariableWithConstraints) MapsKt.getValue(constraintSystemCompletionContext.getNotFixedTypeVariables(), variableForFixationFindFirstVariableForFixation.getVariable()), list, kotlinDiagnosticsHolder);
        return true;
    }

    private final void reportNotEnoughTypeInformation(ConstraintSystemCompletionContext constraintSystemCompletionContext, ConstraintSystemCompletionMode constraintSystemCompletionMode, List<? extends ResolvedAtom> list, UnwrappedType unwrappedType, boolean z, List<? extends PostponedResolvedAtom> list2, KotlinDiagnosticsHolder kotlinDiagnosticsHolder) {
        while (true) {
            VariableFixationFinder.VariableForFixation variableForFixationFindFirstVariableForFixation = this.variableFixationFinder.findFirstVariableForFixation(constraintSystemCompletionContext, getOrderedAllTypeVariables(constraintSystemCompletionContext, z, list), list2, constraintSystemCompletionMode, unwrappedType);
            if (variableForFixationFindFirstVariableForFixation == null) {
                return;
            }
            variableForFixationFindFirstVariableForFixation.getHasProperConstraint();
            if (constraintSystemCompletionMode == ConstraintSystemCompletionMode.PARTIAL) {
                return;
            } else {
                processVariableWhenNotEnoughInformation(constraintSystemCompletionContext, (VariableWithConstraints) MapsKt.getValue(constraintSystemCompletionContext.getNotFixedTypeVariables(), variableForFixationFindFirstVariableForFixation.getVariable()), list, kotlinDiagnosticsHolder);
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:15:0x0074  */
    private final void processVariableWhenNotEnoughInformation(ConstraintSystemCompletionContext constraintSystemCompletionContext, VariableWithConstraints variableWithConstraints, List<? extends ResolvedAtom> list, KotlinDiagnosticsHolder kotlinDiagnosticsHolder) {
        ErrorType errorTypeCreateErrorType;
        TypeVariableMarker typeVariable = variableWithConstraints.getTypeVariable();
        ResolvedAtom resolvedAtomFindResolvedAtomBy = INSTANCE.findResolvedAtomBy(typeVariable, list);
        if (resolvedAtomFindResolvedAtomBy == null) {
            resolvedAtomFindResolvedAtomBy = (ResolvedAtom) CollectionsKt.firstOrNull((List) list);
        }
        if (resolvedAtomFindResolvedAtomBy != null) {
            constraintSystemCompletionContext.addError(new NotEnoughInformationForTypeParameterImpl(typeVariable, resolvedAtomFindResolvedAtomBy, constraintSystemCompletionContext.getCouldBeResolvedWithUnrestrictedBuilderInference()));
        }
        if (typeVariable instanceof TypeVariableFromCallableDescriptor) {
            ErrorTypeKind errorTypeKind = ErrorTypeKind.UNINFERRED_TYPE_VARIABLE;
            String strAsString = ((TypeVariableFromCallableDescriptor) typeVariable).getOriginalTypeParameter().getShortName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            errorTypeCreateErrorType = ErrorUtils.createErrorType(errorTypeKind, strAsString);
        } else if (typeVariable instanceof TypeVariableForLambdaParameterType) {
            TypeVariableForLambdaParameterType typeVariableForLambdaParameterType = (TypeVariableForLambdaParameterType) typeVariable;
            if (typeVariableForLambdaParameterType.getAtom() instanceof LambdaKotlinCallArgument) {
                kotlinDiagnosticsHolder.addDiagnostic(new NotEnoughInformationForLambdaParameter((LambdaKotlinCallArgument) typeVariableForLambdaParameterType.getAtom(), typeVariableForLambdaParameterType.getIndex()));
                errorTypeCreateErrorType = ErrorUtils.createErrorType(ErrorTypeKind.UNINFERRED_LAMBDA_PARAMETER_TYPE, new String[0]);
            } else {
                errorTypeCreateErrorType = ErrorUtils.createErrorType(ErrorTypeKind.UNINFERRED_TYPE_VARIABLE, typeVariable.toString());
            }
        } else {
            errorTypeCreateErrorType = ErrorUtils.createErrorType(ErrorTypeKind.UNINFERRED_TYPE_VARIABLE, typeVariable.toString());
        }
        constraintSystemCompletionContext.fixVariable(typeVariable, errorTypeCreateErrorType, new FixVariableConstraintPositionImpl(typeVariable, resolvedAtomFindResolvedAtomBy));
    }

    private final List<TypeConstructorMarker> getOrderedAllTypeVariables(ConstraintSystemCompletionContext constraintSystemCompletionContext, boolean z, List<? extends ResolvedAtom> list) {
        if (z) {
            return CollectionsKt.toList(constraintSystemCompletionContext.getNotFixedTypeVariables().keySet());
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator<? extends ResolvedAtom> it = list.iterator();
        while (it.hasNext()) {
            getOrderedAllTypeVariables$collectAllTypeVariables(it.next(), linkedHashSet, constraintSystemCompletionContext);
        }
        if (linkedHashSet.size() != constraintSystemCompletionContext.getNotFixedTypeVariables().size()) {
            Set mutableSet = CollectionsKt.toMutableSet(constraintSystemCompletionContext.getNotFixedTypeVariables().keySet());
            mutableSet.removeAll(linkedHashSet);
            throw new IllegalArgumentException(("Not all type variables found: " + mutableSet).toString());
        }
        return CollectionsKt.toList(linkedHashSet);
    }

    private static final List<TypeVariableTypeConstructor> getOrderedAllTypeVariables$getVariablesFromRevisedExpectedType(KotlinType kotlinType) {
        List<TypeProjection> arguments;
        if (kotlinType == null || (arguments = kotlinType.getArguments()) == null) {
            return null;
        }
        List<TypeProjection> list = arguments;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((TypeProjection) it.next()).getNullableAnyType().getConstructor());
        }
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : arrayList) {
            if (obj instanceof TypeVariableTypeConstructor) {
                arrayList2.add(obj);
            }
        }
        return arrayList2;
    }

    private static final void getOrderedAllTypeVariables$collectAllTypeVariables(ResolvedAtom resolvedAtom, LinkedHashSet<TypeConstructor> linkedHashSet, ConstraintSystemCompletionContext constraintSystemCompletionContext) {
        ArrayList arrayListEmptyList;
        FreshVariableNewTypeSubstitutor freshVariablesSubstitutor;
        List<TypeVariableFromCallableDescriptor> freshVariables;
        ArrayList arrayListEmptyList2;
        FreshVariableNewTypeSubstitutor freshVariablesSubstitutor2;
        List<TypeVariableFromCallableDescriptor> freshVariables2;
        List<ResolvedAtom> subResolvedAtoms;
        if (resolvedAtom instanceof ResolvedLambdaAtom) {
            TypeVariableForLambdaReturnType typeVariableForLambdaReturnType = ((ResolvedLambdaAtom) resolvedAtom).getTypeVariableForLambdaReturnType();
            arrayListEmptyList = CollectionsKt.listOfNotNull(typeVariableForLambdaReturnType != null ? typeVariableForLambdaReturnType.getFreshTypeConstructor() : null);
        } else if (resolvedAtom instanceof LambdaWithTypeVariableAsExpectedTypeAtom) {
            arrayListEmptyList = getOrderedAllTypeVariables$getVariablesFromRevisedExpectedType(((LambdaWithTypeVariableAsExpectedTypeAtom) resolvedAtom).getRevisedExpectedType());
            if (arrayListEmptyList == null) {
                arrayListEmptyList = CollectionsKt.emptyList();
            }
        } else if (resolvedAtom instanceof PostponedCallableReferenceAtom) {
            PostponedCallableReferenceAtom postponedCallableReferenceAtom = (PostponedCallableReferenceAtom) resolvedAtom;
            List<TypeVariableTypeConstructor> orderedAllTypeVariables$getVariablesFromRevisedExpectedType = getOrderedAllTypeVariables$getVariablesFromRevisedExpectedType(postponedCallableReferenceAtom.getRevisedExpectedType());
            if (orderedAllTypeVariables$getVariablesFromRevisedExpectedType == null) {
                orderedAllTypeVariables$getVariablesFromRevisedExpectedType = CollectionsKt.emptyList();
            }
            List<TypeVariableTypeConstructor> list = orderedAllTypeVariables$getVariablesFromRevisedExpectedType;
            CallableReferenceResolutionCandidate candidate = postponedCallableReferenceAtom.getCandidate();
            if (candidate == null || (freshVariablesSubstitutor2 = candidate.getFreshVariablesSubstitutor()) == null || (freshVariables2 = freshVariablesSubstitutor2.getFreshVariables()) == null) {
                arrayListEmptyList2 = null;
            } else {
                List<TypeVariableFromCallableDescriptor> list2 = freshVariables2;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
                Iterator<T> it = list2.iterator();
                while (it.hasNext()) {
                    arrayList.add(((TypeVariableFromCallableDescriptor) it.next()).getFreshTypeConstructor());
                }
                arrayListEmptyList2 = arrayList;
            }
            if (arrayListEmptyList2 == null) {
                arrayListEmptyList2 = CollectionsKt.emptyList();
            }
            arrayListEmptyList = CollectionsKt.plus((Collection) list, (Iterable) arrayListEmptyList2);
        } else if (!(resolvedAtom instanceof ResolvedCallAtom)) {
            if (resolvedAtom instanceof ResolvedCallableReferenceArgumentAtom) {
                CallableReferenceResolutionCandidate candidate2 = ((ResolvedCallableReferenceArgumentAtom) resolvedAtom).getCandidate();
                if (candidate2 == null || (freshVariablesSubstitutor = candidate2.getFreshVariablesSubstitutor()) == null || (freshVariables = freshVariablesSubstitutor.getFreshVariables()) == null) {
                    arrayListEmptyList = null;
                } else {
                    List<TypeVariableFromCallableDescriptor> list3 = freshVariables;
                    ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list3, 10));
                    Iterator<T> it2 = list3.iterator();
                    while (it2.hasNext()) {
                        arrayList2.add(((TypeVariableFromCallableDescriptor) it2.next()).getFreshTypeConstructor());
                    }
                    arrayListEmptyList = arrayList2;
                }
                if (arrayListEmptyList == null) {
                    arrayListEmptyList = CollectionsKt.emptyList();
                }
            } else {
                arrayListEmptyList = CollectionsKt.emptyList();
            }
        } else {
            List<TypeVariableFromCallableDescriptor> freshVariables3 = ((ResolvedCallAtom) resolvedAtom).getFreshVariablesSubstitutor().getFreshVariables();
            ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(freshVariables3, 10));
            Iterator<T> it3 = freshVariables3.iterator();
            while (it3.hasNext()) {
                arrayList3.add(((TypeVariableFromCallableDescriptor) it3.next()).getFreshTypeConstructor());
            }
            arrayListEmptyList = arrayList3;
        }
        for (TypeVariableTypeConstructor typeVariableTypeConstructor : arrayListEmptyList) {
            if (!constraintSystemCompletionContext.getNotFixedTypeVariables().containsKey(typeVariableTypeConstructor)) {
                typeVariableTypeConstructor = null;
            }
            if (typeVariableTypeConstructor != null) {
                linkedHashSet.add(typeVariableTypeConstructor);
            }
        }
        LinkedHashSet<TypeConstructor> linkedHashSet2 = linkedHashSet;
        if (resolvedAtom instanceof StubResolvedAtom) {
            StubResolvedAtom stubResolvedAtom = (StubResolvedAtom) resolvedAtom;
            if (constraintSystemCompletionContext.getNotFixedTypeVariables().containsKey(stubResolvedAtom.getTypeVariable())) {
                linkedHashSet2.add(stubResolvedAtom.getTypeVariable());
            }
        }
        if (!resolvedAtom.getAnalyzed() || (subResolvedAtoms = resolvedAtom.getSubResolvedAtoms()) == null) {
            return;
        }
        Iterator<T> it4 = subResolvedAtoms.iterator();
        while (it4.hasNext()) {
            getOrderedAllTypeVariables$collectAllTypeVariables((ResolvedAtom) it4.next(), linkedHashSet, constraintSystemCompletionContext);
        }
    }

    private final void fixVariable(ConstraintSystemCompletionContext c, VariableWithConstraints variableWithConstraints, List<? extends ResolvedAtom> topLevelAtoms, KotlinDiagnosticsHolder diagnosticsHolder) {
        fixVariable(c, variableWithConstraints, TypeVariableDirectionCalculator.ResolveDirection.UNKNOWN, topLevelAtoms, diagnosticsHolder);
    }

    private final void reportWarningIfFixedIntoDeclaredUpperBounds(KotlinDiagnosticsHolder diagnosticsHolder, VariableWithConstraints variableWithConstraints, KotlinTypeMarker resultType) {
        boolean z;
        List<Constraint> constraints = variableWithConstraints.getConstraints();
        boolean z2 = false;
        KotlinTypeMarker type = null;
        if (!(constraints instanceof Collection) || !constraints.isEmpty()) {
            for (Constraint constraint : constraints) {
                ConstraintPosition from = constraint.getPosition().getFrom();
                if (from instanceof IncorporationConstraintPosition) {
                    from = ((IncorporationConstraintPosition) from).getFrom();
                }
                if ((from instanceof BuilderInferenceSubstitutionConstraintPosition) && ((BuilderInferenceSubstitutionConstraintPosition) from).getIsFromNotSubstitutedDeclaredUpperBound()) {
                    type = constraint.getType();
                    z = true;
                } else {
                    z = false;
                }
                if (z) {
                    z2 = true;
                    break;
                }
            }
        }
        if (z2 && Intrinsics.areEqual(type, resultType)) {
            diagnosticsHolder.addDiagnostic(new KotlinConstraintSystemDiagnostic(new InferredIntoDeclaredUpperBounds(variableWithConstraints.getTypeVariable())));
        }
    }

    private final void fixVariable(ConstraintSystemCompletionContext c, VariableWithConstraints variableWithConstraints, TypeVariableDirectionCalculator.ResolveDirection direction, List<? extends ResolvedAtom> topLevelAtoms, KotlinDiagnosticsHolder diagnosticsHolder) {
        KotlinTypeMarker kotlinTypeMarkerFindResultType = this.resultTypeResolver.findResultType(c, variableWithConstraints, direction);
        TypeVariableMarker typeVariable = variableWithConstraints.getTypeVariable();
        ResolvedAtom resolvedAtomFindResolvedAtomBy = INSTANCE.findResolvedAtomBy(typeVariable, topLevelAtoms);
        if (resolvedAtomFindResolvedAtomBy == null) {
            resolvedAtomFindResolvedAtomBy = (ResolvedAtom) CollectionsKt.firstOrNull((List) topLevelAtoms);
        }
        reportWarningIfFixedIntoDeclaredUpperBounds(diagnosticsHolder, variableWithConstraints, kotlinTypeMarkerFindResultType);
        c.fixVariable(typeVariable, kotlinTypeMarkerFindResultType, new FixVariableConstraintPositionImpl(typeVariable, resolvedAtomFindResolvedAtomBy));
    }

    /* JADX INFO: compiled from: KotlinConstraintSystemCompleter.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\bJ\u001a\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\b2\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\b¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/components/KotlinConstraintSystemCompleter$Companion;", "", "()V", "findResolvedAtomBy", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedAtom;", "typeVariable", "Lorg/jetbrains/kotlin/types/model/TypeVariableMarker;", "topLevelAtoms", "", "getOrderedNotAnalyzedPostponedArguments", "Lorg/jetbrains/kotlin/resolve/calls/model/PostponedResolvedAtom;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        private static final void getOrderedNotAnalyzedPostponedArguments$process(ResolvedAtom resolvedAtom, List<PostponedResolvedAtom> list) {
            List<ResolvedAtom> subResolvedAtoms;
            List<PostponedResolvedAtom> list2 = list;
            PostponedResolvedAtom postponedResolvedAtom = null;
            PostponedResolvedAtom postponedResolvedAtom2 = resolvedAtom instanceof PostponedResolvedAtom ? (PostponedResolvedAtom) resolvedAtom : null;
            if (postponedResolvedAtom2 != null && !postponedResolvedAtom2.getAnalyzed()) {
                postponedResolvedAtom = postponedResolvedAtom2;
            }
            org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(list2, postponedResolvedAtom);
            if (!resolvedAtom.getAnalyzed() || (subResolvedAtoms = resolvedAtom.getSubResolvedAtoms()) == null) {
                return;
            }
            Iterator<T> it = subResolvedAtoms.iterator();
            while (it.hasNext()) {
                getOrderedNotAnalyzedPostponedArguments$process((ResolvedAtom) it.next(), list);
            }
        }

        public final List<PostponedResolvedAtom> getOrderedNotAnalyzedPostponedArguments(List<? extends ResolvedAtom> topLevelAtoms) {
            Intrinsics.checkNotNullParameter(topLevelAtoms, "topLevelAtoms");
            ArrayList arrayList = new ArrayList();
            Iterator<? extends ResolvedAtom> it = topLevelAtoms.iterator();
            while (it.hasNext()) {
                getOrderedNotAnalyzedPostponedArguments$process(it.next(), arrayList);
            }
            return arrayList;
        }

        /* JADX WARN: Code duplicated, block: B:17:0x0047  */
        private static final ResolvedAtom findResolvedAtomBy$check(ResolvedAtom resolvedAtom, TypeVariableMarker typeVariableMarker) {
            boolean zAreEqual;
            FreshVariableNewTypeSubstitutor freshVariablesSubstitutor;
            List<TypeVariableFromCallableDescriptor> freshVariables;
            if (resolvedAtom instanceof ResolvedCallAtom) {
                zAreEqual = CollectionsKt.contains(((ResolvedCallAtom) resolvedAtom).getFreshVariablesSubstitutor().getFreshVariables(), typeVariableMarker);
            } else if (resolvedAtom instanceof ResolvedCallableReferenceArgumentAtom) {
                CallableReferenceResolutionCandidate candidate = ((ResolvedCallableReferenceArgumentAtom) resolvedAtom).getCandidate();
                if (candidate == null || (freshVariablesSubstitutor = candidate.getFreshVariablesSubstitutor()) == null || (freshVariables = freshVariablesSubstitutor.getFreshVariables()) == null) {
                    zAreEqual = false;
                } else {
                    zAreEqual = CollectionsKt.contains(freshVariables, typeVariableMarker);
                }
            } else if (resolvedAtom instanceof ResolvedLambdaAtom) {
                zAreEqual = Intrinsics.areEqual(typeVariableMarker, ((ResolvedLambdaAtom) resolvedAtom).getTypeVariableForLambdaReturnType());
            } else {
                zAreEqual = false;
            }
            if (zAreEqual) {
                return resolvedAtom;
            }
            List<ResolvedAtom> subResolvedAtoms = resolvedAtom.getSubResolvedAtoms();
            if (subResolvedAtoms == null) {
                return null;
            }
            Iterator<T> it = subResolvedAtoms.iterator();
            while (it.hasNext()) {
                ResolvedAtom resolvedAtomFindResolvedAtomBy$check = findResolvedAtomBy$check((ResolvedAtom) it.next(), typeVariableMarker);
                if (resolvedAtomFindResolvedAtomBy$check != null) {
                    return resolvedAtomFindResolvedAtomBy$check;
                }
            }
            return null;
        }

        public final ResolvedAtom findResolvedAtomBy(TypeVariableMarker typeVariable, List<? extends ResolvedAtom> topLevelAtoms) {
            Intrinsics.checkNotNullParameter(typeVariable, "typeVariable");
            Intrinsics.checkNotNullParameter(topLevelAtoms, "topLevelAtoms");
            Iterator<? extends ResolvedAtom> it = topLevelAtoms.iterator();
            while (it.hasNext()) {
                ResolvedAtom resolvedAtomFindResolvedAtomBy$check = findResolvedAtomBy$check(it.next(), typeVariable);
                if (resolvedAtomFindResolvedAtomBy$check != null) {
                    return resolvedAtomFindResolvedAtomBy$check;
                }
            }
            return null;
        }
    }
}
