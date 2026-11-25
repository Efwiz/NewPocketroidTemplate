package org.jetbrains.kotlin.resolve.calls.checkers;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValueFactory;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.checker.NewKotlinTypeChecker;

/* JADX INFO: compiled from: assignmentCheckUtils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a&\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\b¨\u0006\t"}, d2 = {"isAssignmentCorrectWithDataFlowInfo", "", "leftType", "Lorg/jetbrains/kotlin/types/KotlinType;", "rightExpression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "rightType", "context", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class AssignmentCheckUtilsKt {
    public static final boolean isAssignmentCorrectWithDataFlowInfo(KotlinType leftType, KtExpression rightExpression, KotlinType rightType, CallCheckerContext context) {
        Intrinsics.checkNotNullParameter(leftType, "leftType");
        Intrinsics.checkNotNullParameter(rightExpression, "rightExpression");
        Intrinsics.checkNotNullParameter(rightType, "rightType");
        Intrinsics.checkNotNullParameter(context, "context");
        NewKotlinTypeChecker kotlinTypeChecker = context.getCallComponents().getKotlinTypeChecker();
        if (kotlinTypeChecker.isSubtypeOf(rightType, leftType)) {
            return true;
        }
        DataFlowInfo dataFlowInfo = context.getDataFlowInfo();
        DataFlowValueFactory dataFlowValueFactory = context.getDataFlowValueFactory();
        BindingContext context2 = context.getTrace().getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getBindingContext(...)");
        Set<KotlinType> stableTypes = dataFlowInfo.getStableTypes(dataFlowValueFactory.createDataFlowValue(rightExpression, rightType, context2, context.getModuleDescriptor()), context.getLanguageVersionSettings());
        if (!(stableTypes instanceof Collection) || !stableTypes.isEmpty()) {
            Iterator<T> it = stableTypes.iterator();
            while (it.hasNext()) {
                if (kotlinTypeChecker.isSubtypeOf((KotlinType) it.next(), leftType)) {
                    return true;
                }
            }
        }
        return false;
    }
}
