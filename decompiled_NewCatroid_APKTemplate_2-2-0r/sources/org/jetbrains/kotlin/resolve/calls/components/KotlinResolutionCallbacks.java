package org.jetbrains.kotlin.resolve.calls.components;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.resolve.calls.components.candidate.CallableReferenceResolutionCandidate;
import org.jetbrains.kotlin.resolve.calls.components.candidate.ResolutionCandidate;
import org.jetbrains.kotlin.resolve.calls.components.candidate.SimpleResolutionCandidate;
import org.jetbrains.kotlin.resolve.calls.inference.NewConstraintSystem;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintStorage;
import org.jetbrains.kotlin.resolve.calls.inference.model.NewTypeVariable;
import org.jetbrains.kotlin.resolve.calls.inference.model.TypeVariableTypeConstructor;
import org.jetbrains.kotlin.resolve.calls.model.CallableReferenceKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCall;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.LHSResult;
import org.jetbrains.kotlin.resolve.calls.model.LambdaKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCallAtom;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedLambdaAtom;
import org.jetbrains.kotlin.resolve.calls.tower.CandidateFactoryProviderForInvoke;
import org.jetbrains.kotlin.resolve.calls.tower.ImplicitScopeTower;
import org.jetbrains.kotlin.resolve.constants.IntegerValueTypeConstant;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.StubTypeForBuilderInference;
import org.jetbrains.kotlin.types.UnwrappedType;

/* JADX INFO: compiled from: ExternalComponents.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000º\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\"\n\u0002\u0010&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001Jd\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\r0\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\r0\u000f2\b\u0010\u0011\u001a\u0004\u0018\u00010\r2\u0006\u0010\u0012\u001a\u00020\u00132\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\u00170\u0015H&J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH&J\u0012\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\u0006\u0010\u001e\u001a\u00020\u001fH&J\b\u0010 \u001a\u00020!H&J\u0010\u0010\"\u001a\u00020\u00192\u0006\u0010#\u001a\u00020\u001bH&J\u001a\u0010$\u001a\u0004\u0018\u00010%2\u0006\u0010&\u001a\u00020!2\u0006\u0010'\u001a\u00020(H&J\u001e\u0010)\u001a\b\u0012\u0004\u0012\u00020+0*2\u0006\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020/H&J\u0012\u00100\u001a\u0004\u0018\u00010\r2\u0006\u0010#\u001a\u00020\u001bH&J\u0010\u00101\u001a\u0002022\u0006\u00103\u001a\u00020/H&J\u0018\u00104\u001a\u00020\u000b2\u0006\u0010#\u001a\u00020\u001b2\u0006\u00105\u001a\u00020\rH&J\"\u00106\u001a\u00020\u00192\u0018\u00107\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020:\u0012\u0004\u0012\u00020;0908H&J(\u0010<\u001a\b\u0012\u0004\u0012\u00020>0=2\u0006\u0010\u001e\u001a\u00020?2\b\u00105\u001a\u0004\u0018\u00010\r2\u0006\u0010@\u001a\u00020AH&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006BÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/components/KotlinResolutionCallbacks;", "", "inferenceSession", "Lorg/jetbrains/kotlin/resolve/calls/components/InferenceSession;", "getInferenceSession", "()Lorg/jetbrains/kotlin/resolve/calls/components/InferenceSession;", "analyzeAndGetLambdaReturnArguments", "Lorg/jetbrains/kotlin/resolve/calls/components/ReturnArgumentsAnalysisResult;", "lambdaArgument", "Lorg/jetbrains/kotlin/resolve/calls/model/LambdaKotlinCallArgument;", "isSuspend", "", "receiverType", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "contextReceiversTypes", "", "parameters", "expectedReturnType", "annotations", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "stubsForPostponedVariables", "", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/NewTypeVariable;", "Lorg/jetbrains/kotlin/types/StubTypeForBuilderInference;", "bindStubResolvedCallForCandidate", "", "candidate", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCallAtom;", "convertSignedConstantToUnsigned", "Lorg/jetbrains/kotlin/resolve/constants/IntegerValueTypeConstant;", "argument", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallArgument;", "createEmptyConstraintSystem", "Lorg/jetbrains/kotlin/resolve/calls/inference/NewConstraintSystem;", "disableContractsIfNecessary", "resolvedAtom", "findResultType", "Lorg/jetbrains/kotlin/types/KotlinType;", "constraintSystem", "typeVariable", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/TypeVariableTypeConstructor;", "getCandidateFactoryForInvoke", "Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateFactoryProviderForInvoke;", "Lorg/jetbrains/kotlin/resolve/calls/components/candidate/ResolutionCandidate;", "scopeTower", "Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;", "kotlinCall", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCall;", "getExpectedTypeFromAsExpressionAndRecordItInTrace", "getLhsResult", "Lorg/jetbrains/kotlin/resolve/calls/model/LHSResult;", "call", "isCompileTimeConstant", "expectedType", "recordInlinabilityOfLambda", "atom", "", "", "Lorg/jetbrains/kotlin/resolve/calls/components/candidate/SimpleResolutionCandidate;", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedLambdaAtom;", "resolveCallableReferenceArgument", "", "Lorg/jetbrains/kotlin/resolve/calls/components/candidate/CallableReferenceResolutionCandidate;", "Lorg/jetbrains/kotlin/resolve/calls/model/CallableReferenceKotlinCallArgument;", "baseSystem", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintStorage;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface KotlinResolutionCallbacks {
    ReturnArgumentsAnalysisResult analyzeAndGetLambdaReturnArguments(LambdaKotlinCallArgument lambdaArgument, boolean isSuspend, UnwrappedType receiverType, List<? extends UnwrappedType> contextReceiversTypes, List<? extends UnwrappedType> parameters, UnwrappedType expectedReturnType, Annotations annotations, Map<NewTypeVariable, StubTypeForBuilderInference> stubsForPostponedVariables);

    void bindStubResolvedCallForCandidate(ResolvedCallAtom candidate);

    IntegerValueTypeConstant convertSignedConstantToUnsigned(KotlinCallArgument argument);

    NewConstraintSystem createEmptyConstraintSystem();

    void disableContractsIfNecessary(ResolvedCallAtom resolvedAtom);

    KotlinType findResultType(NewConstraintSystem constraintSystem, TypeVariableTypeConstructor typeVariable);

    CandidateFactoryProviderForInvoke<ResolutionCandidate> getCandidateFactoryForInvoke(ImplicitScopeTower scopeTower, KotlinCall kotlinCall);

    UnwrappedType getExpectedTypeFromAsExpressionAndRecordItInTrace(ResolvedCallAtom resolvedAtom);

    InferenceSession getInferenceSession();

    LHSResult getLhsResult(KotlinCall call);

    boolean isCompileTimeConstant(ResolvedCallAtom resolvedAtom, UnwrappedType expectedType);

    void recordInlinabilityOfLambda(Set<? extends Map.Entry<? extends SimpleResolutionCandidate, ResolvedLambdaAtom>> atom);

    Collection<CallableReferenceResolutionCandidate> resolveCallableReferenceArgument(CallableReferenceKotlinCallArgument argument, UnwrappedType expectedType, ConstraintStorage baseSystem);
}
