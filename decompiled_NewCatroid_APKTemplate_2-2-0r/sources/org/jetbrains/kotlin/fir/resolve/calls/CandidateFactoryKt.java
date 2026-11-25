package org.jetbrains.kotlin.fir.resolve.calls;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.expressions.FirBlock;
import org.jetbrains.kotlin.fir.expressions.FirCheckNotNullCall;
import org.jetbrains.kotlin.fir.expressions.FirElvisExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirResolvable;
import org.jetbrains.kotlin.fir.expressions.FirSafeCallExpression;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.expressions.FirTryExpression;
import org.jetbrains.kotlin.fir.expressions.FirWhenExpression;
import org.jetbrains.kotlin.fir.expressions.FirWrappedArgumentExpression;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.resolve.calls.components.PostponedArgumentsAnalyzerContext;

/* JADX INFO: compiled from: CandidateFactory.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u000e\u0010\u0005\u001a\u0004\u0018\u00010\u0006*\u00020\u0007H\u0000¨\u0006\b"}, d2 = {"addSubsystemFromExpression", "", "Lorg/jetbrains/kotlin/resolve/calls/components/PostponedArgumentsAnalyzerContext;", "statement", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "candidate", "Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;", "Lorg/jetbrains/kotlin/fir/expressions/FirResolvable;", "resolve"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CandidateFactoryKt {
    public static final boolean addSubsystemFromExpression(PostponedArgumentsAnalyzerContext postponedArgumentsAnalyzerContext, FirStatement statement) {
        Intrinsics.checkNotNullParameter(postponedArgumentsAnalyzerContext, "<this>");
        Intrinsics.checkNotNullParameter(statement, "statement");
        if (statement instanceof FirQualifiedAccessExpression ? true : statement instanceof FirWhenExpression ? true : statement instanceof FirTryExpression ? true : statement instanceof FirCheckNotNullCall ? true : statement instanceof FirElvisExpression) {
            Candidate candidate = candidate((FirResolvable) statement);
            if (candidate == null) {
                return false;
            }
            postponedArgumentsAnalyzerContext.addOtherSystem(candidate.getSystem().asReadOnlyStorage());
            return true;
        }
        if (statement instanceof FirSafeCallExpression) {
            return addSubsystemFromExpression(postponedArgumentsAnalyzerContext, ((FirSafeCallExpression) statement).getSelector());
        }
        if (statement instanceof FirWrappedArgumentExpression) {
            return addSubsystemFromExpression(postponedArgumentsAnalyzerContext, ((FirWrappedArgumentExpression) statement).getExpression());
        }
        if (statement instanceof FirBlock) {
            List<FirExpression> listReturnExpressions = UtilsKt.returnExpressions((FirBlock) statement);
            if (!(listReturnExpressions instanceof Collection) || !listReturnExpressions.isEmpty()) {
                Iterator<T> it = listReturnExpressions.iterator();
                while (it.hasNext()) {
                    if (addSubsystemFromExpression(postponedArgumentsAnalyzerContext, (FirExpression) it.next())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static final Candidate candidate(FirResolvable firResolvable) {
        Intrinsics.checkNotNullParameter(firResolvable, "<this>");
        FirReference calleeReference = firResolvable.getCalleeReference();
        if (calleeReference instanceof FirNamedReferenceWithCandidate) {
            return ((FirNamedReferenceWithCandidate) calleeReference).getCandidate();
        }
        return null;
    }
}
