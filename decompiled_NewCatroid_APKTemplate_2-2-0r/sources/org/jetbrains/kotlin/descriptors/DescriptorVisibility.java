package org.jetbrains.kotlin.descriptors;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;

/* JADX INFO: compiled from: DescriptorVisibility.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b&\u0018\u00002\u00020\u0001B\u0007\b\u0004¢\u0006\u0002\u0010\u0002J\u0015\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0014\u001a\u00020\u0000¢\u0006\u0002\u0010\u0015J\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017J*\u0010\u0018\u001a\u00020\u000e2\b\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u000eH&J\b\u0010 \u001a\u00020\u000eH&J\b\u0010!\u001a\u00020\u0000H&J\u0006\u0010\"\u001a\u00020\bR\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0012\u0010\u0007\u001a\u00020\bX¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0012\u0010\u000b\u001a\u00020\bX¦\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\nR\u0011\u0010\r\u001a\u00020\u000e8F¢\u0006\u0006\u001a\u0004\b\r\u0010\u000fR\u0011\u0010\u0010\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b\u0011\u0010\n¨\u0006#"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "", "()V", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "Lorg/jetbrains/kotlin/descriptors/Visibility;", "getDelegate", "()Lorg/jetbrains/kotlin/descriptors/Visibility;", "externalDisplayName", "", "getExternalDisplayName", "()Ljava/lang/String;", "internalDisplayName", "getInternalDisplayName", "isPublicAPI", "", "()Z", "name", "getName", "compareTo", "", "visibility", "(Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;)Ljava/lang/Integer;", "customEffectiveVisibility", "Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility;", "isVisible", "receiver", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "what", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorWithVisibility;", TypedValues.TransitionType.S_FROM, "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "useSpecialRulesForPrivateSealedConstructors", "mustCheckInImports", "normalize", "toString", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class DescriptorVisibility {
    public abstract Visibility getDelegate();

    public abstract String getExternalDisplayName();

    public abstract String getInternalDisplayName();

    public abstract boolean isVisible(ReceiverValue receiver, DeclarationDescriptorWithVisibility what, DeclarationDescriptor from, boolean useSpecialRulesForPrivateSealedConstructors);

    /* JADX INFO: renamed from: mustCheckInImports */
    public abstract boolean mo7471mustCheckInImports();

    public abstract DescriptorVisibility normalize();

    protected DescriptorVisibility() {
    }

    public final String getName() {
        return getDelegate().getName();
    }

    public final boolean isPublicAPI() {
        return getDelegate().getIsPublicAPI();
    }

    public final Integer compareTo(DescriptorVisibility visibility) {
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        return getDelegate().compareTo(visibility.getDelegate());
    }

    public final String toString() {
        return getDelegate().toString();
    }

    public final EffectiveVisibility customEffectiveVisibility() {
        return getDelegate().customEffectiveVisibility();
    }
}
