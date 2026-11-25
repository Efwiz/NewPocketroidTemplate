package org.jetbrains.kotlin.resolve.calls.checkers;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorUtilKt;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.incremental.IncrementalJvmCacheKt;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedValueArgument;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.constants.IntegerLiteralTypeConstructor;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeConstructor;

/* JADX INFO: compiled from: SynchronizedByValueChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\f\u0010\u000b\u001a\u00020\f*\u00020\rH\u0002¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/checkers/SynchronizedByValueChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallChecker;", "()V", "check", "", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "reportOn", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "isValueOrPrimitive", "", "Lorg/jetbrains/kotlin/types/KotlinType;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SynchronizedByValueChecker implements CallChecker {
    private final boolean isValueOrPrimitive(KotlinType kotlinType) {
        if (!KotlinBuiltIns.isPrimitiveType(kotlinType)) {
            ClassifierDescriptor this$0 = kotlinType.getConstructor().getThis$0();
            if (!(this$0 instanceof ClassDescriptor) || !((ClassDescriptor) this$0).isValue()) {
                TypeConstructor constructor = kotlinType.getConstructor();
                if (constructor instanceof IntegerLiteralTypeConstructor) {
                    Set<KotlinType> possibleTypes = ((IntegerLiteralTypeConstructor) constructor).getPossibleTypes();
                    if (!(possibleTypes instanceof Collection) || !possibleTypes.isEmpty()) {
                        Iterator<T> it = possibleTypes.iterator();
                        while (it.hasNext()) {
                            if (isValueOrPrimitive((KotlinType) it.next())) {
                            }
                        }
                    }
                }
                return false;
            }
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.checkers.CallChecker
    public void check(ResolvedCall<?> resolvedCall, PsiElement reportOn, CallCheckerContext context) {
        List<ResolvedValueArgument> valueArgumentsByIndex;
        ResolvedValueArgument resolvedValueArgument;
        List<ValueArgument> arguments;
        ValueArgument valueArgument;
        KtExpression argumentExpression;
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(reportOn, "reportOn");
        Intrinsics.checkNotNullParameter(context, "context");
        CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
        if (resultingDescriptor == null || !DescriptorUtilKt.isTopLevelInPackage(resultingDescriptor, "synchronized", IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME) || (valueArgumentsByIndex = resolvedCall.getValueArgumentsByIndex()) == null || (resolvedValueArgument = valueArgumentsByIndex.get(0)) == null || (arguments = resolvedValueArgument.getArguments()) == null || (valueArgument = (ValueArgument) CollectionsKt.firstOrNull((List) arguments)) == null || (argumentExpression = valueArgument.getArgumentExpression()) == null) {
            return;
        }
        BindingContext context2 = context.getTrace().getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getBindingContext(...)");
        KotlinType type = CallUtilKt.getType(argumentExpression, context2);
        if (type != null && isValueOrPrimitive(type)) {
            context.getTrace().report(Errors.FORBIDDEN_SYNCHRONIZED_BY_VALUE_CLASSES_OR_PRIMITIVES.on(reportOn, type));
        }
    }
}
