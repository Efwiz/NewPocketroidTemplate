package org.jetbrains.kotlin.resolve.scopes.receivers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.ChainedMemberScope;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: Qualifier.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u0013\u001a\u00020\u0014H\u0016R\u0016\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\u00108VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/resolve/scopes/receivers/ClassQualifier;", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ClassifierQualifier;", "referenceExpression", "Lorg/jetbrains/kotlin/psi/KtSimpleNameExpression;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "(Lorg/jetbrains/kotlin/psi/KtSimpleNameExpression;Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;)V", "classValueReceiver", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ClassValueReceiver;", "getClassValueReceiver", "()Lorg/jetbrains/kotlin/resolve/scopes/receivers/ClassValueReceiver;", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "getReferenceExpression", "()Lorg/jetbrains/kotlin/psi/KtSimpleNameExpression;", "staticScope", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "getStaticScope", "()Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "toString", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ClassQualifier implements ClassifierQualifier {
    private final ClassValueReceiver classValueReceiver;
    private final ClassDescriptor descriptor;
    private final KtSimpleNameExpression referenceExpression;

    public ClassQualifier(KtSimpleNameExpression referenceExpression, ClassDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(referenceExpression, "referenceExpression");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        this.referenceExpression = referenceExpression;
        this.descriptor = descriptor;
        KotlinType classValueType = DescriptorUtilsKt.getClassValueType(getDescriptor());
        this.classValueReceiver = classValueType != null ? new ClassValueReceiver(this, classValueType, null, 4, null) : null;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.receivers.Qualifier
    public KtSimpleNameExpression getReferenceExpression() {
        return this.referenceExpression;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.receivers.QualifierReceiver
    public ClassDescriptor getDescriptor() {
        return this.descriptor;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.receivers.QualifierReceiver
    public ClassValueReceiver getClassValueReceiver() {
        return this.classValueReceiver;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.receivers.QualifierReceiver
    public MemberScope getStaticScope() {
        if (getDescriptor().getKind() == ClassKind.ENUM_ENTRY) {
            MemberScope staticScope = getDescriptor().getStaticScope();
            Intrinsics.checkNotNullExpressionValue(staticScope, "getStaticScope(...)");
            return staticScope;
        }
        ChainedMemberScope.Companion companion = ChainedMemberScope.INSTANCE;
        String str = "Static scope for " + getDescriptor().getShortName() + " as class or object";
        MemberScope staticScope2 = getDescriptor().getStaticScope();
        Intrinsics.checkNotNullExpressionValue(staticScope2, "getStaticScope(...)");
        MemberScope unsubstitutedInnerClassesScope = getDescriptor().getUnsubstitutedInnerClassesScope();
        Intrinsics.checkNotNullExpressionValue(unsubstitutedInnerClassesScope, "getUnsubstitutedInnerClassesScope(...)");
        return companion.create(str, staticScope2, unsubstitutedInnerClassesScope);
    }

    public String toString() {
        return "Class{" + getDescriptor() + '}';
    }
}
