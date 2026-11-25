package org.jetbrains.kotlin.descriptors.impl;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeSubstitution;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;

/* JADX INFO: compiled from: ModuleAwareClassDescriptor.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u0000 \r2\u00020\u0001:\u0001\rB\u0005¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\tH$J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00020\tH$J\u0010\u0010\f\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tH$¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/impl/ModuleAwareClassDescriptor;", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "()V", "getMemberScope", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "typeArguments", "", "Lorg/jetbrains/kotlin/types/TypeProjection;", "kotlinTypeRefiner", "Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;", "typeSubstitution", "Lorg/jetbrains/kotlin/types/TypeSubstitution;", "getUnsubstitutedMemberScope", "Companion", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ModuleAwareClassDescriptor implements ClassDescriptor {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    protected abstract MemberScope getMemberScope(List<? extends TypeProjection> typeArguments, KotlinTypeRefiner kotlinTypeRefiner);

    protected abstract MemberScope getMemberScope(TypeSubstitution typeSubstitution, KotlinTypeRefiner kotlinTypeRefiner);

    protected abstract MemberScope getUnsubstitutedMemberScope(KotlinTypeRefiner kotlinTypeRefiner);

    /* JADX INFO: compiled from: ModuleAwareClassDescriptor.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J!\u0010\u0003\u001a\u00020\u0004*\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0000¢\u0006\u0002\b\nJ\u0019\u0010\u000b\u001a\u00020\u0004*\u00020\u00052\u0006\u0010\b\u001a\u00020\tH\u0000¢\u0006\u0002\b\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/impl/ModuleAwareClassDescriptor$Companion;", "", "()V", "getRefinedMemberScopeIfPossible", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "typeSubstitution", "Lorg/jetbrains/kotlin/types/TypeSubstitution;", "kotlinTypeRefiner", "Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;", "getRefinedMemberScopeIfPossible$descriptors", "getRefinedUnsubstitutedMemberScopeIfPossible", "getRefinedUnsubstitutedMemberScopeIfPossible$descriptors", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final MemberScope getRefinedUnsubstitutedMemberScopeIfPossible$descriptors(ClassDescriptor classDescriptor, KotlinTypeRefiner kotlinTypeRefiner) {
            MemberScope unsubstitutedMemberScope;
            Intrinsics.checkNotNullParameter(classDescriptor, "<this>");
            Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
            ModuleAwareClassDescriptor moduleAwareClassDescriptor = classDescriptor instanceof ModuleAwareClassDescriptor ? (ModuleAwareClassDescriptor) classDescriptor : null;
            if (moduleAwareClassDescriptor != null && (unsubstitutedMemberScope = moduleAwareClassDescriptor.getUnsubstitutedMemberScope(kotlinTypeRefiner)) != null) {
                return unsubstitutedMemberScope;
            }
            MemberScope unsubstitutedMemberScope2 = classDescriptor.getUnsubstitutedMemberScope();
            Intrinsics.checkNotNullExpressionValue(unsubstitutedMemberScope2, "getUnsubstitutedMemberScope(...)");
            return unsubstitutedMemberScope2;
        }

        public final MemberScope getRefinedMemberScopeIfPossible$descriptors(ClassDescriptor classDescriptor, TypeSubstitution typeSubstitution, KotlinTypeRefiner kotlinTypeRefiner) {
            MemberScope memberScope;
            Intrinsics.checkNotNullParameter(classDescriptor, "<this>");
            Intrinsics.checkNotNullParameter(typeSubstitution, "typeSubstitution");
            Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
            ModuleAwareClassDescriptor moduleAwareClassDescriptor = classDescriptor instanceof ModuleAwareClassDescriptor ? (ModuleAwareClassDescriptor) classDescriptor : null;
            if (moduleAwareClassDescriptor != null && (memberScope = moduleAwareClassDescriptor.getMemberScope(typeSubstitution, kotlinTypeRefiner)) != null) {
                return memberScope;
            }
            MemberScope memberScope2 = classDescriptor.getMemberScope(typeSubstitution);
            Intrinsics.checkNotNullExpressionValue(memberScope2, "getMemberScope(...)");
            return memberScope2;
        }
    }
}
