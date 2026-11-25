package org.jetbrains.kotlin.codegen;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.auxiliary.TypeProxy;

/* JADX INFO: compiled from: builtinSpecialBridges.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\b\u0018\u0000*\n\b\u0000\u0010\u0001 \u0001*\u00020\u00022\u00020\u0002B)\u0012\u0006\u0010\u0003\u001a\u00028\u0000\u0012\u0006\u0010\u0004\u001a\u00028\u0000\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0006¢\u0006\u0002\u0010\bR\u0013\u0010\u0003\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\u000b\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0007\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\fR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\fR\u0013\u0010\u0004\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\u000b\u001a\u0004\b\r\u0010\n¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/codegen/BridgeForBuiltinSpecial;", "Signature", "", TypedValues.TransitionType.S_FROM, "to", "isSpecial", "", "isDelegateToSuper", "(Ljava/lang/Object;Ljava/lang/Object;ZZ)V", "getFrom", "()Ljava/lang/Object;", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, "()Z", "getTo", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BridgeForBuiltinSpecial<Signature> {
    private final Signature from;
    private final boolean isDelegateToSuper;
    private final boolean isSpecial;
    private final Signature to;

    public BridgeForBuiltinSpecial(Signature from, Signature to, boolean z, boolean z2) {
        Intrinsics.checkNotNullParameter(from, "from");
        Intrinsics.checkNotNullParameter(to, "to");
        this.from = from;
        this.to = to;
        this.isSpecial = z;
        this.isDelegateToSuper = z2;
    }

    public /* synthetic */ BridgeForBuiltinSpecial(Object obj, Object obj2, boolean z, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(obj, obj2, (i & 4) != 0 ? false : z, (i & 8) != 0 ? false : z2);
    }

    public final Signature getFrom() {
        return this.from;
    }

    public final Signature getTo() {
        return this.to;
    }

    /* JADX INFO: renamed from: isSpecial, reason: from getter */
    public final boolean getIsSpecial() {
        return this.isSpecial;
    }

    /* JADX INFO: renamed from: isDelegateToSuper, reason: from getter */
    public final boolean getIsDelegateToSuper() {
        return this.isDelegateToSuper;
    }
}
