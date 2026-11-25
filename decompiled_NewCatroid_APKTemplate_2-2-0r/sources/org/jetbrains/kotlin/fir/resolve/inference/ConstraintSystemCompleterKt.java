package org.jetbrains.kotlin.fir.resolve.inference;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirAnnotationContainer;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.expressions.FirAnnotationCall;
import org.jetbrains.kotlin.fir.expressions.FirBlock;
import org.jetbrains.kotlin.fir.expressions.FirCall;
import org.jetbrains.kotlin.fir.expressions.FirCatch;
import org.jetbrains.kotlin.fir.expressions.FirCheckNotNullCall;
import org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall;
import org.jetbrains.kotlin.fir.expressions.FirElvisExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirResolvable;
import org.jetbrains.kotlin.fir.expressions.FirSafeCallExpression;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.expressions.FirTryExpression;
import org.jetbrains.kotlin.fir.expressions.FirVariableAssignment;
import org.jetbrains.kotlin.fir.expressions.FirWhenBranch;
import org.jetbrains.kotlin.fir.expressions.FirWhenExpression;
import org.jetbrains.kotlin.fir.expressions.FirWrappedArgumentExpression;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.resolve.calls.Candidate;
import org.jetbrains.kotlin.fir.resolve.calls.FirNamedReferenceWithCandidate;
import org.jetbrains.kotlin.resolve.calls.inference.model.NewConstraintSystemImpl;

/* JADX INFO: compiled from: ConstraintSystemCompleter.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000*\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a&\u0010\u0005\u001a\u00020\u0006*\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00060\u000b\u001a(\u0010\f\u001a\u00020\u0006*\u00020\r2\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00060\u000b2\u0006\u0010\b\u001a\u00020\tH\u0002\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u000e"}, d2 = {"csBuilder", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/NewConstraintSystemImpl;", "Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;", "getCsBuilder", "(Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;)Lorg/jetbrains/kotlin/resolve/calls/inference/model/NewConstraintSystemImpl;", "processAllContainingCallCandidates", "", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "processBlocks", "", "processor", "Lkotlin/Function1;", "processCandidateIfApplicable", "Lorg/jetbrains/kotlin/fir/expressions/FirResolvable;", "resolve"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ConstraintSystemCompleterKt {
    public static final void processAllContainingCallCandidates(FirStatement firStatement, boolean z, Function1<? super Candidate, Unit> processor) {
        Intrinsics.checkNotNullParameter(firStatement, "<this>");
        Intrinsics.checkNotNullParameter(processor, "processor");
        if (firStatement instanceof FirFunctionCall) {
            processCandidateIfApplicable((FirResolvable) firStatement, processor, z);
            Iterator<T> it = ((FirCall) firStatement).getArgumentList().getArguments().iterator();
            while (it.hasNext()) {
                processAllContainingCallCandidates((FirExpression) it.next(), z, processor);
            }
            return;
        }
        if (firStatement instanceof FirSafeCallExpression) {
            processAllContainingCallCandidates(((FirSafeCallExpression) firStatement).getSelector(), z, processor);
            return;
        }
        if (firStatement instanceof FirWhenExpression) {
            processCandidateIfApplicable((FirResolvable) firStatement, processor, z);
            Iterator<T> it2 = ((FirWhenExpression) firStatement).getBranches().iterator();
            while (it2.hasNext()) {
                processAllContainingCallCandidates(((FirWhenBranch) it2.next()).getResult(), z, processor);
            }
            return;
        }
        if (firStatement instanceof FirTryExpression) {
            processCandidateIfApplicable((FirResolvable) firStatement, processor, z);
            FirTryExpression firTryExpression = (FirTryExpression) firStatement;
            processAllContainingCallCandidates(firTryExpression.getTryBlock(), z, processor);
            Iterator<T> it3 = firTryExpression.getCatches().iterator();
            while (it3.hasNext()) {
                processAllContainingCallCandidates(((FirCatch) it3.next()).getBlock(), z, processor);
            }
            return;
        }
        if (firStatement instanceof FirCheckNotNullCall) {
            processCandidateIfApplicable((FirResolvable) firStatement, processor, z);
            Iterator<T> it4 = ((FirCall) firStatement).getArgumentList().getArguments().iterator();
            while (it4.hasNext()) {
                processAllContainingCallCandidates((FirExpression) it4.next(), z, processor);
            }
            return;
        }
        if (firStatement instanceof FirQualifiedAccessExpression) {
            processCandidateIfApplicable((FirResolvable) firStatement, processor, z);
            return;
        }
        if (firStatement instanceof FirVariableAssignment) {
            FirVariableAssignment firVariableAssignment = (FirVariableAssignment) firStatement;
            FirAnnotationContainer lValue = firVariableAssignment.getLValue();
            FirResolvable firResolvable = lValue instanceof FirResolvable ? (FirResolvable) lValue : null;
            if (firResolvable != null) {
                processCandidateIfApplicable(firResolvable, processor, z);
            }
            processAllContainingCallCandidates(firVariableAssignment.getRValue(), z, processor);
            return;
        }
        if (firStatement instanceof FirWrappedArgumentExpression) {
            processAllContainingCallCandidates(((FirWrappedArgumentExpression) firStatement).getExpression(), z, processor);
            return;
        }
        if (firStatement instanceof FirBlock) {
            if (z) {
                Iterator<T> it5 = UtilsKt.returnExpressions((FirBlock) firStatement).iterator();
                while (it5.hasNext()) {
                    processAllContainingCallCandidates((FirExpression) it5.next(), z, processor);
                }
                return;
            }
            return;
        }
        if (firStatement instanceof FirDelegatedConstructorCall) {
            processCandidateIfApplicable((FirResolvable) firStatement, processor, z);
            Iterator<T> it6 = ((FirCall) firStatement).getArgumentList().getArguments().iterator();
            while (it6.hasNext()) {
                processAllContainingCallCandidates((FirExpression) it6.next(), z, processor);
            }
            return;
        }
        if (firStatement instanceof FirElvisExpression) {
            processCandidateIfApplicable((FirResolvable) firStatement, processor, z);
            FirElvisExpression firElvisExpression = (FirElvisExpression) firStatement;
            processAllContainingCallCandidates(firElvisExpression.getLhs(), z, processor);
            processAllContainingCallCandidates(firElvisExpression.getRhs(), z, processor);
            return;
        }
        if (firStatement instanceof FirAnnotationCall) {
            processCandidateIfApplicable((FirResolvable) firStatement, processor, z);
            Iterator<T> it7 = ((FirCall) firStatement).getArgumentList().getArguments().iterator();
            while (it7.hasNext()) {
                processAllContainingCallCandidates((FirExpression) it7.next(), z, processor);
            }
        }
    }

    private static final void processCandidateIfApplicable(FirResolvable firResolvable, Function1<? super Candidate, Unit> function1, boolean z) {
        Candidate candidate;
        FirReference calleeReference = firResolvable.getCalleeReference();
        FirNamedReferenceWithCandidate firNamedReferenceWithCandidate = calleeReference instanceof FirNamedReferenceWithCandidate ? (FirNamedReferenceWithCandidate) calleeReference : null;
        if (firNamedReferenceWithCandidate == null || (candidate = firNamedReferenceWithCandidate.getCandidate()) == null) {
            return;
        }
        function1.invoke(candidate);
        for (PostponedResolvedAtom postponedResolvedAtom : candidate.getPostponedAtoms()) {
            if ((postponedResolvedAtom instanceof ResolvedLambdaAtom) && postponedResolvedAtom.getAnalyzed()) {
                Iterator<T> it = ((ResolvedLambdaAtom) postponedResolvedAtom).getReturnStatements().iterator();
                while (it.hasNext()) {
                    processAllContainingCallCandidates((FirExpression) it.next(), z, function1);
                }
            }
        }
    }

    public static final NewConstraintSystemImpl getCsBuilder(Candidate candidate) {
        Intrinsics.checkNotNullParameter(candidate, "<this>");
        return candidate.getSystem().getBuilder();
    }
}
