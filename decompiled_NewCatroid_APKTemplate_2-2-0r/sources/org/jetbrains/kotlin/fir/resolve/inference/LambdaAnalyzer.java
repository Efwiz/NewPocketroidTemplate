package org.jetbrains.kotlin.fir.resolve.inference;

import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import org.jetbrains.kotlin.fir.resolve.calls.Candidate;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.types.model.StubTypeMarker;
import org.jetbrains.kotlin.types.model.TypeVariableMarker;

/* JADX INFO: compiled from: PostponedArgumentsAnalyzer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\\\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00070\t2\b\u0010\u000b\u001a\u0004\u0018\u00010\u00072\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\r2\u0006\u0010\u0010\u001a\u00020\u0011H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0012À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/inference/LambdaAnalyzer;", "", "analyzeAndGetLambdaReturnArguments", "Lorg/jetbrains/kotlin/fir/resolve/inference/ReturnArgumentsAnalysisResult;", "lambdaAtom", "Lorg/jetbrains/kotlin/fir/resolve/inference/ResolvedLambdaAtom;", "receiverType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "contextReceivers", "", "parameters", "expectedReturnType", "stubsForPostponedVariables", "", "Lorg/jetbrains/kotlin/types/model/TypeVariableMarker;", "Lorg/jetbrains/kotlin/types/model/StubTypeMarker;", "candidate", "Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface LambdaAnalyzer {
    ReturnArgumentsAnalysisResult analyzeAndGetLambdaReturnArguments(ResolvedLambdaAtom lambdaAtom, ConeKotlinType receiverType, List<? extends ConeKotlinType> contextReceivers, List<? extends ConeKotlinType> parameters, ConeKotlinType expectedReturnType, Map<TypeVariableMarker, ? extends StubTypeMarker> stubsForPostponedVariables, Candidate candidate);
}
