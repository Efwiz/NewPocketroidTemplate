package org.jetbrains.kotlin.fir.resolve.calls;

import java.util.LinkedHashMap;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.fir.FirLanguageSettingsComponentKt;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.resolve.inference.ConeTypeParameterBasedTypeVariable;
import org.jetbrains.kotlin.fir.resolve.inference.ConstraintSystemCompleterKt;
import org.jetbrains.kotlin.fir.resolve.inference.FirBuilderInferenceSessionKt;
import org.jetbrains.kotlin.fir.symbols.ConeTypeParameterLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeParameterType;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeTypeVariable;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FunctionalTypeUtilsKt;

/* JADX INFO: compiled from: ResolutionStages.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J.\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0096@¢\u0006\u0002\u0010\r¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/PostponedVariablesInitializerResolutionStage;", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionStage;", "()V", "check", "", "candidate", "Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;", "callInfo", "Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;", "sink", "Lorg/jetbrains/kotlin/fir/resolve/calls/CheckerSink;", "context", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;", "(Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;Lorg/jetbrains/kotlin/fir/resolve/calls/CheckerSink;Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PostponedVariablesInitializerResolutionStage extends ResolutionStage {
    public static final PostponedVariablesInitializerResolutionStage INSTANCE = new PostponedVariablesInitializerResolutionStage();

    private PostponedVariablesInitializerResolutionStage() {
    }

    @Override // org.jetbrains.kotlin.fir.resolve.calls.ResolutionStage
    public Object check(Candidate candidate, CallInfo callInfo, CheckerSink checkerSink, ResolutionContext resolutionContext, Continuation<? super Unit> continuation) {
        ConeKotlinType coneKotlinTypeReceiverType;
        LinkedHashMap<FirExpression, FirValueParameter> argumentMapping = candidate.getArgumentMapping();
        if (argumentMapping != null && !(candidate.getTypeArgumentMapping() instanceof TypeArgumentMapping.Mapped)) {
            for (FirValueParameter firValueParameter : argumentMapping.values()) {
                Intrinsics.checkNotNull(firValueParameter);
                if (FirBuilderInferenceSessionKt.hasBuilderInferenceAnnotation(firValueParameter, resolutionContext.getSession()) && (coneKotlinTypeReceiverType = FunctionalTypeUtilsKt.receiverType(FirTypeUtilsKt.getConeType(firValueParameter.getReturnTypeRef()), callInfo.getSession())) != null && !FirLanguageSettingsComponentKt.getLanguageVersionSettings(resolutionContext.getSession()).supportsFeature(LanguageFeature.UseBuilderInferenceOnlyIfNeeded)) {
                    for (ConeTypeVariable coneTypeVariable : candidate.getFreshVariables()) {
                        ConeTypeVariable coneTypeVariable2 = coneTypeVariable;
                        if (!ConstraintSystemCompleterKt.getCsBuilder(candidate).isPostponedTypeVariable(coneTypeVariable2) && (coneTypeVariable instanceof ConeTypeParameterBasedTypeVariable)) {
                            final FirTypeParameterSymbol typeParameterSymbol = ((ConeTypeParameterBasedTypeVariable) coneTypeVariable).getTypeParameterSymbol();
                            if (ConeTypeUtilsKt.contains(coneKotlinTypeReceiverType, new Function1<ConeKotlinType, Boolean>() { // from class: org.jetbrains.kotlin.fir.resolve.calls.PostponedVariablesInitializerResolutionStage$check$typeHasVariable$1
                                {
                                    super(1);
                                }

                                @Override // kotlin.jvm.functions.Function1
                                public final Boolean invoke(ConeKotlinType it) {
                                    ConeTypeParameterLookupTag lookupTag;
                                    Intrinsics.checkNotNullParameter(it, "it");
                                    FirTypeParameterSymbol typeParameterSymbol2 = null;
                                    ConeTypeParameterType coneTypeParameterType = it instanceof ConeTypeParameterType ? (ConeTypeParameterType) it : null;
                                    if (coneTypeParameterType != null && (lookupTag = coneTypeParameterType.getLookupTag()) != null) {
                                        typeParameterSymbol2 = lookupTag.getTypeParameterSymbol();
                                    }
                                    return Boolean.valueOf(Intrinsics.areEqual(typeParameterSymbol2, typeParameterSymbol));
                                }
                            })) {
                                ConstraintSystemCompleterKt.getCsBuilder(candidate).markPostponedVariable(coneTypeVariable2);
                            }
                        }
                    }
                }
            }
            return Unit.INSTANCE;
        }
        return Unit.INSTANCE;
    }
}
