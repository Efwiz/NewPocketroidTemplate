package org.jetbrains.kotlin.fir.resolve.inference;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirLanguageSettingsComponentKt;
import org.jetbrains.kotlin.fir.diagnostics.ConeCannotInferTypeParameterType;
import org.jetbrains.kotlin.fir.diagnostics.ConeSimpleDiagnostic;
import org.jetbrains.kotlin.fir.diagnostics.DiagnosticKind;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.resolve.BodyResolveComponents;
import org.jetbrains.kotlin.fir.resolve.calls.Candidate;
import org.jetbrains.kotlin.fir.resolve.calls.ResolutionContext;
import org.jetbrains.kotlin.fir.resolve.inference.model.ConeFixVariableConstraintPosition;
import org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.BodyResolveContext;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeErrorType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeVariable;
import org.jetbrains.kotlin.fir.types.ConeTypeVariableTypeConstructor;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemBuilder;
import org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemCompletionContext;
import org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemCompletionMode;
import org.jetbrains.kotlin.resolve.calls.inference.components.PostponedArgumentInputTypesResolver;
import org.jetbrains.kotlin.resolve.calls.inference.components.TypeVariableDependencyInformationProvider;
import org.jetbrains.kotlin.resolve.calls.inference.components.TypeVariableDirectionCalculator;
import org.jetbrains.kotlin.resolve.calls.inference.components.VariableFixationFinder;
import org.jetbrains.kotlin.resolve.calls.inference.model.NotEnoughInformationForTypeParameter;
import org.jetbrains.kotlin.resolve.calls.inference.model.VariableWithConstraints;
import org.jetbrains.kotlin.resolve.calls.model.PostponedAtomWithRevisableExpectedType;
import org.jetbrains.kotlin.resolve.calls.model.PostponedResolvedAtomMarker;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.SimpleTypeMarker;
import org.jetbrains.kotlin.types.model.TypeArgumentListMarker;
import org.jetbrains.kotlin.types.model.TypeArgumentMarker;
import org.jetbrains.kotlin.types.model.TypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeVariableMarker;
import org.jetbrains.kotlin.types.model.TypeVariableTypeConstructorMarker;

/* JADX INFO: compiled from: ConstraintSystemCompleter.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0082\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000 32\u00020\u0001:\u00013B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006JR\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u00182\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0004\u001a\u00020\u001c2\b\b\u0002\u0010\u001d\u001a\u00020\u001e2\u0012\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u00020!\u0012\u0004\u0012\u00020\u00120 J\u0018\u0010\"\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010#\u001a\u00020$H\u0002J \u0010%\u001a\u00020\u001e2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010&\u001a\u00020\u001c2\u0006\u0010'\u001a\u00020(H\u0002J@\u0010)\u001a\u00020\u001e*\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u00182\u0006\u0010*\u001a\u00020\u001b2\u0006\u0010\u001d\u001a\u00020\u001e2\f\u0010+\u001a\b\u0012\u0004\u0012\u00020!0\u0018H\u0002J(\u0010,\u001a\b\u0012\u0004\u0012\u00020-0\u0018*\u00020\u00142\u0006\u0010\u001d\u001a\u00020\u001e2\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u0018H\u0002J\"\u0010.\u001a\u00020\u0012*\u00020\u00142\u0006\u0010#\u001a\u00020$2\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u0018H\u0002JF\u0010/\u001a\u00020\u0012*\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u00182\u0006\u0010*\u001a\u00020\u001b2\f\u00100\u001a\b\u0012\u0004\u0012\u00020-0\u00182\f\u0010+\u001a\b\u0012\u0004\u0012\u00020!0\u0018H\u0002JP\u00101\u001a\u00020\u0012*\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u00182\u0006\u0010*\u001a\u00020\u001b2\u0006\u0010\u0004\u001a\u00020\u001c2\b\b\u0002\u0010\u001d\u001a\u00020\u001e2\u0012\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u00020!\u0012\u0004\u0012\u00020\u00120 H\u0002JL\u00102\u001a\u00020\u001e*\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010*\u001a\u00020\u001b2\f\u0010+\u001a\b\u0012\u0004\u0012\u00020!0\u00182\f\u00100\u001a\b\u0012\u0004\u0012\u00020-0\u00182\u0012\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u00020!\u0012\u0004\u0012\u00020\u00120 H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\r\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u00064"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/inference/ConstraintSystemCompleter;", "", "components", "Lorg/jetbrains/kotlin/fir/resolve/BodyResolveComponents;", "context", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/BodyResolveContext;", "(Lorg/jetbrains/kotlin/fir/resolve/BodyResolveComponents;Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/BodyResolveContext;)V", "inferenceComponents", "Lorg/jetbrains/kotlin/fir/resolve/inference/InferenceComponents;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "postponedArgumentsInputTypesResolver", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/PostponedArgumentInputTypesResolver;", "variableFixationFinder", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/VariableFixationFinder;", "getVariableFixationFinder", "()Lorg/jetbrains/kotlin/resolve/calls/inference/components/VariableFixationFinder;", "complete", "", "c", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintSystemCompletionContext;", "completionMode", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintSystemCompletionMode;", "topLevelAtoms", "", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "candidateReturnType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;", "collectVariablesFromContext", "", "analyze", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/resolve/inference/PostponedResolvedAtom;", "fixVariable", "variableWithConstraints", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/VariableWithConstraints;", "transformToAtomWithNewFunctionExpectedType", "resolutionContext", "argument", "Lorg/jetbrains/kotlin/resolve/calls/model/PostponedAtomWithRevisableExpectedType;", "fixNextReadyVariable", "topLevelType", "postponedArguments", "getOrderedAllTypeVariables", "Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;", "processVariableWhenNotEnoughInformation", "reportNotEnoughTypeInformation", "allTypeVariables", "runCompletion", "tryToCompleteWithBuilderInference", "Companion", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConstraintSystemCompleter {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final BodyResolveContext context;
    private final InferenceComponents inferenceComponents;
    private final LanguageVersionSettings languageVersionSettings;
    private final PostponedArgumentInputTypesResolver postponedArgumentsInputTypesResolver;
    private final VariableFixationFinder variableFixationFinder;

    public ConstraintSystemCompleter(BodyResolveComponents components, BodyResolveContext context) {
        Intrinsics.checkNotNullParameter(components, "components");
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        InferenceComponents inferenceComponents = InferenceComponentsKt.getInferenceComponents(components.getSession());
        this.inferenceComponents = inferenceComponents;
        this.variableFixationFinder = inferenceComponents.getVariableFixationFinder();
        this.postponedArgumentsInputTypesResolver = inferenceComponents.getPostponedArgumentInputTypesResolver();
        this.languageVersionSettings = FirLanguageSettingsComponentKt.getLanguageVersionSettings(components.getSession());
    }

    public final VariableFixationFinder getVariableFixationFinder() {
        return this.variableFixationFinder;
    }

    public final void complete(ConstraintSystemCompletionContext c, ConstraintSystemCompletionMode completionMode, List<? extends FirStatement> topLevelAtoms, ConeKotlinType candidateReturnType, ResolutionContext context, boolean collectVariablesFromContext, Function1<? super PostponedResolvedAtom, Unit> analyze) {
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(completionMode, "completionMode");
        Intrinsics.checkNotNullParameter(topLevelAtoms, "topLevelAtoms");
        Intrinsics.checkNotNullParameter(candidateReturnType, "candidateReturnType");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(analyze, "analyze");
        runCompletion(c, completionMode, topLevelAtoms, candidateReturnType, context, collectVariablesFromContext, analyze);
    }

    private final void runCompletion(ConstraintSystemCompletionContext constraintSystemCompletionContext, ConstraintSystemCompletionMode constraintSystemCompletionMode, List<? extends FirStatement> list, ConeKotlinType coneKotlinType, ResolutionContext resolutionContext, boolean z, Function1<? super PostponedResolvedAtom, Unit> function1) {
        ConeKotlinType coneKotlinType2 = coneKotlinType;
        Set<TypeVariableTypeConstructorMarker> setExtractTypeVariables = constraintSystemCompletionContext.extractTypeVariables(coneKotlinType2);
        if (constraintSystemCompletionMode == ConstraintSystemCompletionMode.FULL || constraintSystemCompletionMode == ConstraintSystemCompletionMode.UNTIL_FIRST_LAMBDA) {
            constraintSystemCompletionContext.resolveForkPointsConstraints();
        }
        while (true) {
            List<? extends PostponedResolvedAtom> orderedNotAnalyzedPostponedArguments = INSTANCE.getOrderedNotAnalyzedPostponedArguments(list);
            if (constraintSystemCompletionMode == ConstraintSystemCompletionMode.UNTIL_FIRST_LAMBDA && constraintSystemCompletionContext.hasLambdaToAnalyze(this.languageVersionSettings, orderedNotAnalyzedPostponedArguments)) {
                return;
            }
            if (!constraintSystemCompletionContext.analyzeArgumentWithFixedParameterTypes(this.languageVersionSettings, orderedNotAnalyzedPostponedArguments, function1)) {
                boolean z2 = this.variableFixationFinder.findFirstVariableForFixation(constraintSystemCompletionContext, getOrderedAllTypeVariables(constraintSystemCompletionContext, z, list), orderedNotAnalyzedPostponedArguments, constraintSystemCompletionMode, coneKotlinType2) != null;
                if (orderedNotAnalyzedPostponedArguments.isEmpty() && !z2) {
                    return;
                }
                ArrayList arrayList = new ArrayList();
                for (Object obj : orderedNotAnalyzedPostponedArguments) {
                    if ((obj instanceof PostponedAtomWithRevisableExpectedType) && ((PostponedAtomWithRevisableExpectedType) obj).getRevisedExpectedType() == null) {
                        arrayList.add(obj);
                    }
                }
                ArrayList arrayList2 = arrayList;
                TypeVariableDependencyInformationProvider typeVariableDependencyInformationProvider = new TypeVariableDependencyInformationProvider(constraintSystemCompletionContext.getNotFixedTypeVariables(), orderedNotAnalyzedPostponedArguments, coneKotlinType2, constraintSystemCompletionContext);
                if (this.postponedArgumentsInputTypesResolver.collectParameterTypesAndBuildNewExpectedTypes(constraintSystemCompletionContext, arrayList2, constraintSystemCompletionMode, typeVariableDependencyInformationProvider, setExtractTypeVariables)) {
                    continue;
                } else {
                    if (constraintSystemCompletionMode == ConstraintSystemCompletionMode.FULL) {
                        Iterator<? extends PostponedResolvedAtom> it = orderedNotAnalyzedPostponedArguments.iterator();
                        while (true) {
                            if (it.hasNext()) {
                                TypeVariableDependencyInformationProvider typeVariableDependencyInformationProvider2 = typeVariableDependencyInformationProvider;
                                if (!this.postponedArgumentsInputTypesResolver.fixNextReadyVariableForParameterTypeIfNeeded(constraintSystemCompletionContext, it.next(), orderedNotAnalyzedPostponedArguments, coneKotlinType2, typeVariableDependencyInformationProvider, new Function1<TypeVariableMarker, Object>() { // from class: org.jetbrains.kotlin.fir.resolve.inference.ConstraintSystemCompleter$runCompletion$variableWasFixed$1
                                    @Override // kotlin.jvm.functions.Function1
                                    public final Object invoke(TypeVariableMarker it2) {
                                        Intrinsics.checkNotNullParameter(it2, "it");
                                        return null;
                                    }
                                })) {
                                    typeVariableDependencyInformationProvider = typeVariableDependencyInformationProvider2;
                                }
                            } else {
                                Iterator it2 = arrayList2.iterator();
                                while (true) {
                                    if (it2.hasNext()) {
                                        if (transformToAtomWithNewFunctionExpectedType(constraintSystemCompletionContext, resolutionContext, (PostponedAtomWithRevisableExpectedType) it2.next())) {
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (!constraintSystemCompletionContext.analyzeNextReadyPostponedArgument(this.languageVersionSettings, orderedNotAnalyzedPostponedArguments, constraintSystemCompletionMode, function1) && !fixNextReadyVariable(constraintSystemCompletionContext, constraintSystemCompletionMode, list, coneKotlinType, z, orderedNotAnalyzedPostponedArguments)) {
                        List<TypeConstructorMarker> orderedAllTypeVariables = getOrderedAllTypeVariables(constraintSystemCompletionContext, z, list);
                        if (tryToCompleteWithBuilderInference(constraintSystemCompletionContext, constraintSystemCompletionMode, coneKotlinType, orderedNotAnalyzedPostponedArguments, orderedAllTypeVariables, function1)) {
                            continue;
                        } else {
                            reportNotEnoughTypeInformation(constraintSystemCompletionContext, constraintSystemCompletionMode, list, coneKotlinType, orderedAllTypeVariables, orderedNotAnalyzedPostponedArguments);
                            if (constraintSystemCompletionMode != ConstraintSystemCompletionMode.FULL || !constraintSystemCompletionContext.analyzeRemainingNotAnalyzedPostponedArgument(orderedNotAnalyzedPostponedArguments, function1)) {
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    private final boolean tryToCompleteWithBuilderInference(ConstraintSystemCompletionContext constraintSystemCompletionContext, ConstraintSystemCompletionMode constraintSystemCompletionMode, ConeKotlinType coneKotlinType, List<? extends PostponedResolvedAtom> list, List<? extends TypeConstructorMarker> list2, Function1<? super PostponedResolvedAtom, Unit> function1) {
        if (constraintSystemCompletionMode != ConstraintSystemCompletionMode.FULL || !this.languageVersionSettings.supportsFeature(LanguageFeature.UseBuilderInferenceOnlyIfNeeded)) {
            return false;
        }
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
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
        ConstraintSystemBuilder builder = constraintSystemCompletionContext.getBuilder();
        for (ResolvedLambdaAtom resolvedLambdaAtom : arrayList2) {
            Collection<ConeKotlinType> inputTypes = resolvedLambdaAtom.getInputTypes();
            ArrayList arrayList3 = new ArrayList();
            Iterator<T> it = inputTypes.iterator();
            while (it.hasNext()) {
                CollectionsKt.addAll(arrayList3, constraintSystemCompletionContext.extractTypeVariables((ConeKotlinType) it.next()));
            }
            ArrayList arrayList4 = new ArrayList();
            for (Object obj2 : arrayList3) {
                if (!constraintSystemCompletionContext.getFixedTypeVariables().containsKey((TypeVariableTypeConstructorMarker) obj2)) {
                    arrayList4.add(obj2);
                }
            }
            ArrayList arrayList5 = arrayList4;
            if (!arrayList5.isEmpty()) {
                Iterator it2 = arrayList5.iterator();
                while (it2.hasNext()) {
                    builder.markPostponedVariable(((VariableWithConstraints) MapsKt.getValue(constraintSystemCompletionContext.getNotFixedTypeVariables(), (TypeVariableTypeConstructorMarker) it2.next())).getTypeVariable());
                }
                function1.invoke(resolvedLambdaAtom);
            }
        }
        VariableFixationFinder.VariableForFixation variableForFixationFindFirstVariableForFixation = this.variableFixationFinder.findFirstVariableForFixation(constraintSystemCompletionContext, list2, list, constraintSystemCompletionMode, coneKotlinType);
        return variableForFixationFindFirstVariableForFixation != null && variableForFixationFindFirstVariableForFixation.getHasProperConstraint();
    }

    private final boolean transformToAtomWithNewFunctionExpectedType(ConstraintSystemCompletionContext c, ResolutionContext resolutionContext, PostponedAtomWithRevisableExpectedType argument) {
        KotlinTypeMarker revisedExpectedType = argument.getRevisedExpectedType();
        KotlinTypeMarker kotlinTypeMarker = null;
        if (revisedExpectedType != null) {
            if (!c.isFunctionOrKFunctionWithAnySuspendability(revisedExpectedType)) {
                revisedExpectedType = null;
            }
            kotlinTypeMarker = revisedExpectedType;
        }
        ConeKotlinType coneKotlinType = (ConeKotlinType) kotlinTypeMarker;
        if (coneKotlinType == null) {
            return false;
        }
        if (argument instanceof ResolvedCallableReferenceAtom) {
            argument.reviseExpectedType(coneKotlinType);
            return true;
        }
        if (argument instanceof LambdaWithTypeVariableAsExpectedTypeAtom) {
            PostponedArgumentsAnalyzerKt.transformToResolvedLambda$default((LambdaWithTypeVariableAsExpectedTypeAtom) argument, c.getBuilder(), resolutionContext, coneKotlinType, null, 8, null);
            return true;
        }
        throw new IllegalStateException("Unsupported postponed argument type of " + argument);
    }

    private final boolean fixNextReadyVariable(ConstraintSystemCompletionContext constraintSystemCompletionContext, ConstraintSystemCompletionMode constraintSystemCompletionMode, List<? extends FirStatement> list, ConeKotlinType coneKotlinType, boolean z, List<? extends PostponedResolvedAtom> list2) {
        VariableFixationFinder.VariableForFixation variableForFixationFindFirstVariableForFixation = this.variableFixationFinder.findFirstVariableForFixation(constraintSystemCompletionContext, getOrderedAllTypeVariables(constraintSystemCompletionContext, z, list), list2, constraintSystemCompletionMode, coneKotlinType);
        if (variableForFixationFindFirstVariableForFixation == null) {
            return false;
        }
        VariableWithConstraints variableWithConstraints = (VariableWithConstraints) MapsKt.getValue(constraintSystemCompletionContext.getNotFixedTypeVariables(), variableForFixationFindFirstVariableForFixation.getVariable());
        if (!variableForFixationFindFirstVariableForFixation.getHasProperConstraint()) {
            if (!this.context.getInferenceSession().isSyntheticTypeVariable(variableWithConstraints.getTypeVariable())) {
                return false;
            }
            FirInferenceSession inferenceSession = this.context.getInferenceSession();
            TypeVariableMarker typeVariable = variableWithConstraints.getTypeVariable();
            Intrinsics.checkNotNull(typeVariable, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.ConeTypeVariable");
            inferenceSession.fixSyntheticTypeVariableWithNotEnoughInformation((ConeTypeVariable) typeVariable, constraintSystemCompletionContext);
            return true;
        }
        fixVariable(constraintSystemCompletionContext, variableWithConstraints);
        return true;
    }

    private final void reportNotEnoughTypeInformation(ConstraintSystemCompletionContext constraintSystemCompletionContext, ConstraintSystemCompletionMode constraintSystemCompletionMode, List<? extends FirStatement> list, ConeKotlinType coneKotlinType, List<? extends TypeConstructorMarker> list2, List<? extends PostponedResolvedAtom> list3) {
        while (true) {
            VariableFixationFinder.VariableForFixation variableForFixationFindFirstVariableForFixation = this.variableFixationFinder.findFirstVariableForFixation(constraintSystemCompletionContext, list2, list3, constraintSystemCompletionMode, coneKotlinType);
            if (variableForFixationFindFirstVariableForFixation == null) {
                return;
            }
            variableForFixationFindFirstVariableForFixation.getHasProperConstraint();
            if (constraintSystemCompletionMode == ConstraintSystemCompletionMode.PARTIAL) {
                return;
            } else {
                processVariableWhenNotEnoughInformation(constraintSystemCompletionContext, (VariableWithConstraints) MapsKt.getValue(constraintSystemCompletionContext.getNotFixedTypeVariables(), variableForFixationFindFirstVariableForFixation.getVariable()), list);
            }
        }
    }

    private final void processVariableWhenNotEnoughInformation(ConstraintSystemCompletionContext constraintSystemCompletionContext, VariableWithConstraints variableWithConstraints, List<? extends FirStatement> list) {
        ConeErrorType coneErrorTypeCreateCannotInferErrorType$default;
        TypeVariableMarker typeVariable = variableWithConstraints.getTypeVariable();
        Companion companion = INSTANCE;
        FirStatement firStatementFindResolvedAtomBy = companion.findResolvedAtomBy(typeVariable, list);
        if (firStatementFindResolvedAtomBy == null) {
            firStatementFindResolvedAtomBy = (FirStatement) CollectionsKt.firstOrNull((List) list);
        }
        if (firStatementFindResolvedAtomBy != null) {
            constraintSystemCompletionContext.addError(new NotEnoughInformationForTypeParameter(typeVariable, firStatementFindResolvedAtomBy, constraintSystemCompletionContext.getCouldBeResolvedWithUnrestrictedBuilderInference()));
        }
        if (typeVariable instanceof ConeTypeParameterBasedTypeVariable) {
            ConeTypeParameterBasedTypeVariable coneTypeParameterBasedTypeVariable = (ConeTypeParameterBasedTypeVariable) typeVariable;
            coneErrorTypeCreateCannotInferErrorType$default = companion.createCannotInferErrorType(coneTypeParameterBasedTypeVariable.getTypeParameterSymbol(), "Cannot infer argument for type parameter " + coneTypeParameterBasedTypeVariable.getTypeParameterSymbol().getName(), true);
        } else {
            coneErrorTypeCreateCannotInferErrorType$default = typeVariable instanceof ConeTypeVariableForLambdaParameterType ? Companion.createCannotInferErrorType$default(companion, null, "Cannot infer lambda parameter type", false, 4, null) : Companion.createCannotInferErrorType$default(companion, null, "Cannot infer type variable " + typeVariable, false, 4, null);
        }
        constraintSystemCompletionContext.fixVariable(typeVariable, coneErrorTypeCreateCannotInferErrorType$default, new ConeFixVariableConstraintPosition(typeVariable));
    }

    private final List<TypeConstructorMarker> getOrderedAllTypeVariables(ConstraintSystemCompletionContext constraintSystemCompletionContext, boolean z, List<? extends FirStatement> list) {
        if (z) {
            return CollectionsKt.toList(constraintSystemCompletionContext.getNotFixedTypeVariables().keySet());
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet(constraintSystemCompletionContext.getNotFixedTypeVariables().size());
        Iterator<? extends FirStatement> it = list.iterator();
        while (it.hasNext()) {
            getOrderedAllTypeVariables$collectAllTypeVariables(it.next(), linkedHashSet, constraintSystemCompletionContext);
        }
        if (this.context.getInferenceSession().hasSyntheticTypeVariables()) {
            LinkedHashSet linkedHashSet2 = linkedHashSet;
            Map<TypeConstructorMarker, VariableWithConstraints> notFixedTypeVariables = constraintSystemCompletionContext.getNotFixedTypeVariables();
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (Map.Entry<TypeConstructorMarker, VariableWithConstraints> entry : notFixedTypeVariables.entrySet()) {
                if (this.context.getInferenceSession().isSyntheticTypeVariable(entry.getValue().getTypeVariable())) {
                    linkedHashMap.put(entry.getKey(), entry.getValue());
                }
            }
            CollectionsKt.addAll(linkedHashSet2, linkedHashMap.keySet());
        }
        if (linkedHashSet.size() != constraintSystemCompletionContext.getNotFixedTypeVariables().size()) {
            Set mutableSet = CollectionsKt.toMutableSet(constraintSystemCompletionContext.getNotFixedTypeVariables().keySet());
            mutableSet.removeAll(linkedHashSet);
            throw new IllegalArgumentException(("Not all type variables found: " + mutableSet).toString());
        }
        return CollectionsKt.toList(linkedHashSet);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final TypeConstructorMarker getOrderedAllTypeVariables$toTypeConstructor(ConeTypeVariable coneTypeVariable, ConstraintSystemCompletionContext constraintSystemCompletionContext) {
        ConeTypeVariableTypeConstructor typeConstructor;
        ConeTypeVariableTypeConstructor coneTypeVariableTypeConstructor = null;
        if (coneTypeVariable != null && (typeConstructor = coneTypeVariable.getTypeConstructor()) != null && constraintSystemCompletionContext.getNotFixedTypeVariables().keySet().contains(typeConstructor)) {
            coneTypeVariableTypeConstructor = typeConstructor;
        }
        return coneTypeVariableTypeConstructor;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void getOrderedAllTypeVariables$collectNotFixedVariables(PostponedAtomWithRevisableExpectedType postponedAtomWithRevisableExpectedType, ConstraintSystemCompletionContext constraintSystemCompletionContext, LinkedHashSet<TypeConstructorMarker> linkedHashSet) {
        SimpleTypeMarker simpleTypeMarkerLowerBoundIfFlexible;
        TypeArgumentListMarker typeArgumentListMarkerAsArgumentList;
        KotlinTypeMarker revisedExpectedType = postponedAtomWithRevisableExpectedType.getRevisedExpectedType();
        if (revisedExpectedType == null || (simpleTypeMarkerLowerBoundIfFlexible = constraintSystemCompletionContext.lowerBoundIfFlexible(revisedExpectedType)) == null || (typeArgumentListMarkerAsArgumentList = constraintSystemCompletionContext.asArgumentList(simpleTypeMarkerLowerBoundIfFlexible)) == null) {
            return;
        }
        Iterator<TypeArgumentMarker> it = constraintSystemCompletionContext.iterator(typeArgumentListMarkerAsArgumentList);
        while (it.hasNext()) {
            TypeConstructorMarker typeConstructorMarkerTypeConstructor = constraintSystemCompletionContext.typeConstructor(constraintSystemCompletionContext.getType(it.next()));
            if (constraintSystemCompletionContext.getNotFixedTypeVariables().containsKey(typeConstructorMarkerTypeConstructor)) {
                linkedHashSet.add(typeConstructorMarkerTypeConstructor);
            }
        }
    }

    private static final void getOrderedAllTypeVariables$collectAllTypeVariables(FirStatement firStatement, final LinkedHashSet<TypeConstructorMarker> linkedHashSet, final ConstraintSystemCompletionContext constraintSystemCompletionContext) {
        ConstraintSystemCompleterKt.processAllContainingCallCandidates(firStatement, true, new Function1<Candidate, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.inference.ConstraintSystemCompleter$getOrderedAllTypeVariables$collectAllTypeVariables$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Candidate candidate) {
                invoke2(candidate);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Candidate candidate) {
                Intrinsics.checkNotNullParameter(candidate, "candidate");
                List<ConeTypeVariable> freshVariables = candidate.getFreshVariables();
                LinkedHashSet<TypeConstructorMarker> linkedHashSet2 = linkedHashSet;
                ConstraintSystemCompletionContext constraintSystemCompletionContext2 = constraintSystemCompletionContext;
                Iterator<T> it = freshVariables.iterator();
                while (it.hasNext()) {
                    TypeConstructorMarker orderedAllTypeVariables$toTypeConstructor = ConstraintSystemCompleter.getOrderedAllTypeVariables$toTypeConstructor((ConeTypeVariable) it.next(), constraintSystemCompletionContext2);
                    if (orderedAllTypeVariables$toTypeConstructor != null) {
                        linkedHashSet2.add(orderedAllTypeVariables$toTypeConstructor);
                    }
                }
                for (PostponedResolvedAtomMarker postponedResolvedAtomMarker : candidate.getPostponedAtoms()) {
                    if (postponedResolvedAtomMarker instanceof ResolvedLambdaAtom) {
                        org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(linkedHashSet, ConstraintSystemCompleter.getOrderedAllTypeVariables$toTypeConstructor(((ResolvedLambdaAtom) postponedResolvedAtomMarker).getTypeVariableForLambdaReturnType(), constraintSystemCompletionContext));
                    } else if (postponedResolvedAtomMarker instanceof LambdaWithTypeVariableAsExpectedTypeAtom) {
                        ConstraintSystemCompleter.getOrderedAllTypeVariables$collectNotFixedVariables((PostponedAtomWithRevisableExpectedType) postponedResolvedAtomMarker, constraintSystemCompletionContext, linkedHashSet);
                    } else if ((postponedResolvedAtomMarker instanceof ResolvedCallableReferenceAtom) && ((ResolvedCallableReferenceAtom) postponedResolvedAtomMarker).getMightNeedAdditionalResolution()) {
                        ConstraintSystemCompleter.getOrderedAllTypeVariables$collectNotFixedVariables((PostponedAtomWithRevisableExpectedType) postponedResolvedAtomMarker, constraintSystemCompletionContext, linkedHashSet);
                    }
                }
            }
        });
    }

    private final void fixVariable(ConstraintSystemCompletionContext c, VariableWithConstraints variableWithConstraints) {
        KotlinTypeMarker kotlinTypeMarkerFindResultType = this.inferenceComponents.getResultTypeResolver().findResultType(c, variableWithConstraints, TypeVariableDirectionCalculator.ResolveDirection.UNKNOWN);
        TypeVariableMarker typeVariable = variableWithConstraints.getTypeVariable();
        c.fixVariable(typeVariable, kotlinTypeMarkerFindResultType, new ConeFixVariableConstraintPosition(typeVariable));
    }

    /* JADX INFO: compiled from: ConstraintSystemCompleter.kt */
    @Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\nH\u0002J \u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\f0\u0010H\u0002J\u001c\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u00102\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\f0\u0010H\u0002J\u001b\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u00102\u0006\u0010\u0013\u001a\u00020\u0014H\u0000¢\u0006\u0002\b\u0015¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/inference/ConstraintSystemCompleter$Companion;", "", "()V", "createCannotInferErrorType", "Lorg/jetbrains/kotlin/fir/types/ConeErrorType;", "typeParameterSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeParameterSymbol;", CrashHianalyticsData.MESSAGE, "", "isUninferredParameter", "", "findResolvedAtomBy", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "typeVariable", "Lorg/jetbrains/kotlin/types/model/TypeVariableMarker;", "topLevelAtoms", "", "getOrderedNotAnalyzedPostponedArguments", "Lorg/jetbrains/kotlin/fir/resolve/inference/PostponedResolvedAtom;", "candidate", "Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;", "getOrderedNotAnalyzedPostponedArguments$resolve", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final List<PostponedResolvedAtom> getOrderedNotAnalyzedPostponedArguments$resolve(Candidate candidate) {
            Intrinsics.checkNotNullParameter(candidate, "candidate");
            FirElement callSite = candidate.getCallInfo().getCallSite();
            FirStatement firStatement = callSite instanceof FirStatement ? (FirStatement) callSite : null;
            return firStatement == null ? CollectionsKt.emptyList() : getOrderedNotAnalyzedPostponedArguments(CollectionsKt.listOf(firStatement));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final List<PostponedResolvedAtom> getOrderedNotAnalyzedPostponedArguments(List<? extends FirStatement> topLevelAtoms) {
            final ArrayList arrayList = new ArrayList();
            Iterator<? extends FirStatement> it = topLevelAtoms.iterator();
            while (it.hasNext()) {
                ConstraintSystemCompleterKt.processAllContainingCallCandidates(it.next(), true, new Function1<Candidate, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.inference.ConstraintSystemCompleter$Companion$getOrderedNotAnalyzedPostponedArguments$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(Candidate candidate) {
                        invoke2(candidate);
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(Candidate candidate) {
                        Intrinsics.checkNotNullParameter(candidate, "candidate");
                        List<PostponedResolvedAtom> postponedAtoms = candidate.getPostponedAtoms();
                        ArrayList<PostponedResolvedAtom> arrayList2 = arrayList;
                        for (PostponedResolvedAtom postponedResolvedAtom : postponedAtoms) {
                            ArrayList<PostponedResolvedAtom> arrayList3 = arrayList2;
                            PostponedResolvedAtom postponedResolvedAtom2 = null;
                            if (!(postponedResolvedAtom instanceof PostponedResolvedAtom)) {
                                postponedResolvedAtom = null;
                            }
                            if (postponedResolvedAtom != null && !Boolean.valueOf(postponedResolvedAtom.getAnalyzed()).booleanValue()) {
                                postponedResolvedAtom2 = postponedResolvedAtom;
                            }
                            org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(arrayList3, postponedResolvedAtom2);
                        }
                    }
                });
            }
            return arrayList;
        }

        private static final FirStatement findResolvedAtomBy$findFirstAtomContainingVariable(FirStatement firStatement, final TypeVariableMarker typeVariableMarker) {
            final Ref.ObjectRef objectRef = new Ref.ObjectRef();
            ConstraintSystemCompleterKt.processAllContainingCallCandidates(firStatement, true, new Function1<Candidate, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.inference.ConstraintSystemCompleter$Companion$findResolvedAtomBy$findFirstAtomContainingVariable$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Candidate candidate) {
                    invoke2(candidate);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(Candidate candidate) {
                    Intrinsics.checkNotNullParameter(candidate, "candidate");
                    if (CollectionsKt.contains(candidate.getFreshVariables(), typeVariableMarker)) {
                        ConstraintSystemCompleter.Companion.findResolvedAtomBy$findFirstAtomContainingVariable$suggestElement(objectRef, candidate.getCallInfo().getCallSite());
                    }
                    for (PostponedResolvedAtom postponedResolvedAtom : candidate.getPostponedAtoms()) {
                        if (postponedResolvedAtom instanceof ResolvedLambdaAtom) {
                            ResolvedLambdaAtom resolvedLambdaAtom = (ResolvedLambdaAtom) postponedResolvedAtom;
                            if (Intrinsics.areEqual(resolvedLambdaAtom.getTypeVariableForLambdaReturnType(), typeVariableMarker)) {
                                ConstraintSystemCompleter.Companion.findResolvedAtomBy$findFirstAtomContainingVariable$suggestElement(objectRef, resolvedLambdaAtom.getReference());
                            }
                        }
                    }
                }
            });
            return (FirStatement) objectRef.element;
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Multi-variable type inference failed */
        public static final void findResolvedAtomBy$findFirstAtomContainingVariable$suggestElement(Ref.ObjectRef<FirStatement> objectRef, FirElement firElement) {
            if (objectRef.element == null && (firElement instanceof FirStatement)) {
                objectRef.element = firElement;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final FirStatement findResolvedAtomBy(TypeVariableMarker typeVariable, List<? extends FirStatement> topLevelAtoms) {
            Iterator<T> it = topLevelAtoms.iterator();
            while (it.hasNext()) {
                FirStatement firStatementFindResolvedAtomBy$findFirstAtomContainingVariable = findResolvedAtomBy$findFirstAtomContainingVariable((FirStatement) it.next(), typeVariable);
                if (firStatementFindResolvedAtomBy$findFirstAtomContainingVariable != null) {
                    return firStatementFindResolvedAtomBy$findFirstAtomContainingVariable;
                }
            }
            return null;
        }

        static /* synthetic */ ConeErrorType createCannotInferErrorType$default(Companion companion, FirTypeParameterSymbol firTypeParameterSymbol, String str, boolean z, int i, Object obj) {
            if ((i & 4) != 0) {
                z = false;
            }
            return companion.createCannotInferErrorType(firTypeParameterSymbol, str, z);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final ConeErrorType createCannotInferErrorType(FirTypeParameterSymbol typeParameterSymbol, String message, boolean isUninferredParameter) {
            ConeCannotInferTypeParameterType coneCannotInferTypeParameterType;
            if (typeParameterSymbol == null) {
                coneCannotInferTypeParameterType = new ConeSimpleDiagnostic(message, DiagnosticKind.CannotInferParameterType);
            } else {
                coneCannotInferTypeParameterType = new ConeCannotInferTypeParameterType(typeParameterSymbol, message);
            }
            return new ConeErrorType(coneCannotInferTypeParameterType, isUninferredParameter, null, null, 12, null);
        }
    }
}
