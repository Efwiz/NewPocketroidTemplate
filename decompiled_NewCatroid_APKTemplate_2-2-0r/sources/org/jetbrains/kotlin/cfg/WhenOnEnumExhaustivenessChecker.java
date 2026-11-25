package org.jetbrains.kotlin.cfg;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.diagnostics.WhenMissingCase;
import org.jetbrains.kotlin.psi.KtWhenExpression;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: WhenChecker.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÂ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J0\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J\u0010\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/cfg/WhenOnEnumExhaustivenessChecker;", "Lorg/jetbrains/kotlin/cfg/WhenOnClassExhaustivenessChecker;", "()V", "getMissingCases", "", "Lorg/jetbrains/kotlin/diagnostics/WhenMissingCase;", "expression", "Lorg/jetbrains/kotlin/psi/KtWhenExpression;", "context", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "subjectDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "nullable", "", "isApplicable", "subjectType", "Lorg/jetbrains/kotlin/types/KotlinType;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class WhenOnEnumExhaustivenessChecker extends WhenOnClassExhaustivenessChecker {
    public static final WhenOnEnumExhaustivenessChecker INSTANCE = new WhenOnEnumExhaustivenessChecker();

    private WhenOnEnumExhaustivenessChecker() {
    }

    @Override // org.jetbrains.kotlin.cfg.WhenExhaustivenessChecker
    public List<WhenMissingCase> getMissingCases(KtWhenExpression expression, BindingContext context, ClassDescriptor subjectDescriptor, boolean nullable) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        DescriptorUtils.isEnumClass(subjectDescriptor);
        List listCreateListBuilder = CollectionsKt.createListBuilder();
        WhenOnEnumExhaustivenessChecker whenOnEnumExhaustivenessChecker = INSTANCE;
        Intrinsics.checkNotNull(subjectDescriptor);
        listCreateListBuilder.addAll(whenOnEnumExhaustivenessChecker.getMissingClassCases(expression, whenOnEnumExhaustivenessChecker.getEnumEntries(subjectDescriptor), context));
        listCreateListBuilder.addAll(WhenOnNullableExhaustivenessChecker.INSTANCE.getMissingCases(expression, context, nullable));
        org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(listCreateListBuilder, WhenOnExpectExhaustivenessChecker.INSTANCE.getMissingCase(subjectDescriptor));
        return CollectionsKt.build(listCreateListBuilder);
    }

    @Override // org.jetbrains.kotlin.cfg.WhenExhaustivenessChecker
    public boolean isApplicable(KotlinType subjectType) {
        Intrinsics.checkNotNullParameter(subjectType, "subjectType");
        return WhenChecker.getClassDescriptorOfTypeIfEnum(subjectType) != null;
    }
}
