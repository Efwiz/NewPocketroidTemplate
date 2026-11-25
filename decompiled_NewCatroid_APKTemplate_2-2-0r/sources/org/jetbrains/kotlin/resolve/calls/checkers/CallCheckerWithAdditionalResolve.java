package org.jetbrains.kotlin.resolve.calls.checkers;

import kotlin.Metadata;
import org.jetbrains.kotlin.resolve.calls.components.KotlinResolutionCallbacks;
import org.jetbrains.kotlin.resolve.calls.context.BasicCallResolutionContext;
import org.jetbrains.kotlin.resolve.calls.results.OverloadResolutionResults;
import org.jetbrains.kotlin.resolve.calls.tower.ImplicitScopeTower;
import org.jetbrains.kotlin.types.UnwrappedType;

/* JADX INFO: compiled from: CallCheckerWithAdditionalResolve.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J6\u0010\u0002\u001a\u00020\u00032\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\rH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u000eÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerWithAdditionalResolve;", "", "check", "", "overloadResolutionResults", "Lorg/jetbrains/kotlin/resolve/calls/results/OverloadResolutionResults;", "scopeTower", "Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;", "resolutionCallbacks", "Lorg/jetbrains/kotlin/resolve/calls/components/KotlinResolutionCallbacks;", "expectedType", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "context", "Lorg/jetbrains/kotlin/resolve/calls/context/BasicCallResolutionContext;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface CallCheckerWithAdditionalResolve {
    void check(OverloadResolutionResults<?> overloadResolutionResults, ImplicitScopeTower scopeTower, KotlinResolutionCallbacks resolutionCallbacks, UnwrappedType expectedType, BasicCallResolutionContext context);
}
