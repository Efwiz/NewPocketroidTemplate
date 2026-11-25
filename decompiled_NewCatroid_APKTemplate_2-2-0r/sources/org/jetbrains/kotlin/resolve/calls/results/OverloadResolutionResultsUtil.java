package org.jetbrains.kotlin.resolve.calls.results;

import java.util.ArrayList;
import org.jetbrains.kotlin.com.google.common.collect.Lists;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.resolve.calls.context.ContextDependency;
import org.jetbrains.kotlin.resolve.calls.context.ResolutionContext;
import org.jetbrains.kotlin.resolve.calls.model.MutableResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.tower.NewAbstractResolvedCall;
import org.jetbrains.kotlin.resolve.calls.tower.NewVariableAsFunctionResolvedCallImpl;
import org.jetbrains.kotlin.resolve.calls.util.ResolvedCallUtilKt;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: loaded from: classes6.dex */
public class OverloadResolutionResultsUtil {
    /* JADX WARN: Code duplicated, block: B:21:0x002c  */
    /* JADX WARN: Code duplicated, block: B:22:0x0031  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2 || i == 3 || i == 4) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[(i == 1 || i == 2 || i == 3 || i == 4) ? 3 : 2];
        if (i == 1) {
            objArr[0] = "results";
        } else if (i == 2) {
            objArr[0] = "context";
        } else if (i == 3) {
            objArr[0] = "results";
        } else if (i != 4) {
            objArr[0] = "org/jetbrains/kotlin/resolve/calls/results/OverloadResolutionResultsUtil";
        } else {
            objArr[0] = "context";
        }
        if (i == 1 || i == 2 || i == 3 || i == 4) {
            objArr[1] = "org/jetbrains/kotlin/resolve/calls/results/OverloadResolutionResultsUtil";
        } else {
            objArr[1] = "ambiguity";
        }
        if (i == 1 || i == 2) {
            objArr[2] = "getResultingType";
        } else if (i == 3 || i == 4) {
            objArr[2] = "getResultingCall";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2 && i != 3 && i != 4) {
            throw new IllegalStateException(str2);
        }
        throw new IllegalArgumentException(str2);
    }

    public static <D extends CallableDescriptor> OverloadResolutionResults<D> ambiguity(OverloadResolutionResults<D> overloadResolutionResults, OverloadResolutionResults<D> overloadResolutionResults2) {
        ArrayList arrayListNewArrayList = Lists.newArrayList();
        arrayListNewArrayList.addAll(overloadResolutionResults.getResultingCalls());
        arrayListNewArrayList.addAll(overloadResolutionResults2.getResultingCalls());
        OverloadResolutionResultsImpl overloadResolutionResultsImplAmbiguity = OverloadResolutionResultsImpl.ambiguity(arrayListNewArrayList);
        if (overloadResolutionResultsImplAmbiguity == null) {
            $$$reportNull$$$0(0);
        }
        return overloadResolutionResultsImplAmbiguity;
    }

    public static <D extends CallableDescriptor> KotlinType getResultingType(OverloadResolutionResults<D> overloadResolutionResults, ResolutionContext<?> resolutionContext) {
        if (overloadResolutionResults == null) {
            $$$reportNull$$$0(1);
        }
        if (resolutionContext == null) {
            $$$reportNull$$$0(2);
        }
        ResolvedCall resultingCall = getResultingCall(overloadResolutionResults, resolutionContext);
        if (resultingCall != null) {
            return resultingCall.getResultingDescriptor().getReturnType();
        }
        return null;
    }

    public static <D extends CallableDescriptor> ResolvedCall<D> getResultingCall(OverloadResolutionResults<D> overloadResolutionResults, ResolutionContext<?> resolutionContext) {
        NewAbstractResolvedCall<FunctionDescriptor> functionCall;
        if (overloadResolutionResults == null) {
            $$$reportNull$$$0(3);
        }
        if (resolutionContext == null) {
            $$$reportNull$$$0(4);
        }
        if (overloadResolutionResults.isSingleResult() && resolutionContext.contextDependency == ContextDependency.INDEPENDENT) {
            ResolvedCall<D> resolvedCallMo7436getResultingCall = overloadResolutionResults.mo7436getResultingCall();
            if (resolvedCallMo7436getResultingCall instanceof NewVariableAsFunctionResolvedCallImpl) {
                functionCall = ((NewVariableAsFunctionResolvedCallImpl) resolvedCallMo7436getResultingCall).getFunctionCall();
            } else {
                functionCall = resolvedCallMo7436getResultingCall instanceof NewAbstractResolvedCall ? (NewAbstractResolvedCall) resolvedCallMo7436getResultingCall : null;
            }
            if (functionCall != null) {
                if (!ResolvedCallUtilKt.hasInferredReturnType(functionCall)) {
                    return null;
                }
            } else if (!((MutableResolvedCall) resolvedCallMo7436getResultingCall).hasInferredReturnType()) {
                return null;
            }
        }
        if (overloadResolutionResults.isSingleResult()) {
            return overloadResolutionResults.mo7436getResultingCall();
        }
        return null;
    }
}
