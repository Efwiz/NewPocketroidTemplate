package org.jetbrains.kotlin.resolve.konan.platform;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.psi.KtCallableDeclaration;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.inline.ReasonableInlineRule;

/* JADX INFO: compiled from: NativePlatformConfigurator.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/konan/platform/NativeInliningRule;", "Lorg/jetbrains/kotlin/resolve/inline/ReasonableInlineRule;", "()V", "isInlineReasonable", "", "descriptor", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "declaration", "Lorg/jetbrains/kotlin/psi/KtCallableDeclaration;", "context", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "frontend.native"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NativeInliningRule implements ReasonableInlineRule {
    public static final NativeInliningRule INSTANCE = new NativeInliningRule();

    @Override // org.jetbrains.kotlin.resolve.inline.ReasonableInlineRule
    public boolean isInlineReasonable(CallableMemberDescriptor descriptor, KtCallableDeclaration declaration, BindingContext context) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        return true;
    }

    private NativeInliningRule() {
    }
}
