package org.jetbrains.kotlin.resolve.scopes;

import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.OverloadChecker;

/* JADX INFO: compiled from: LocalRedeclarationChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bH\u0014J\u0018\u0010\n\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u000b2\u0006\u0010\t\u001a\u00020\u000bH\u0014¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/scopes/ThrowingLocalRedeclarationChecker;", "Lorg/jetbrains/kotlin/resolve/scopes/AbstractLocalRedeclarationChecker;", "overloadChecker", "Lorg/jetbrains/kotlin/resolve/OverloadChecker;", "(Lorg/jetbrains/kotlin/resolve/OverloadChecker;)V", "handleConflictingOverloads", "", "first", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "second", "handleRedeclaration", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ThrowingLocalRedeclarationChecker extends AbstractLocalRedeclarationChecker {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ThrowingLocalRedeclarationChecker(OverloadChecker overloadChecker) {
        super(overloadChecker);
        Intrinsics.checkNotNullParameter(overloadChecker, "overloadChecker");
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.AbstractLocalRedeclarationChecker
    protected void handleRedeclaration(DeclarationDescriptor first, DeclarationDescriptor second) {
        Intrinsics.checkNotNullParameter(first, "first");
        Intrinsics.checkNotNullParameter(second, "second");
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String str = String.format("Redeclaration: %s (%s) and %s (%s) (no line info available)", Arrays.copyOf(new Object[]{DescriptorUtils.getFqName(first), first, DescriptorUtils.getFqName(second), second}, 4));
        Intrinsics.checkNotNullExpressionValue(str, "format(...)");
        throw new IllegalStateException(str);
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.AbstractLocalRedeclarationChecker
    protected void handleConflictingOverloads(CallableMemberDescriptor first, CallableMemberDescriptor second) {
        Intrinsics.checkNotNullParameter(first, "first");
        Intrinsics.checkNotNullParameter(second, "second");
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String str = String.format("Conflicting overloads: %s (%s) and %s (%s) (no line info available)", Arrays.copyOf(new Object[]{DescriptorUtils.getFqName(first), first, DescriptorUtils.getFqName(second), second}, 4));
        Intrinsics.checkNotNullExpressionValue(str, "format(...)");
        throw new IllegalStateException(str);
    }
}
