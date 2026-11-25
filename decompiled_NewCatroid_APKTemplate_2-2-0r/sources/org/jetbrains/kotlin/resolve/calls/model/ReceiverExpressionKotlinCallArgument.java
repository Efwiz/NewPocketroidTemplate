package org.jetbrains.kotlin.resolve.calls.model;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.description.type.TypeDescription;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.scopes.receivers.QualifierReceiverKt;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValueWithSmartCastInfo;

/* JADX INFO: compiled from: ArgumentsImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u0000 \u00122\u00020\u0001:\u0001\u0012B#\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007J\b\u0010\u0010\u001a\u00020\u0011H\u0016R\u0016\u0010\b\u001a\u0004\u0018\u00010\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\fR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\fR\u0014\u0010\r\u001a\u00020\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\fR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/ReceiverExpressionKotlinCallArgument;", "Lorg/jetbrains/kotlin/resolve/calls/model/ExpressionKotlinCallArgument;", "receiver", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;", "isSafeCall", "", "isForImplicitInvoke", "(Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;ZZ)V", "argumentName", "Lorg/jetbrains/kotlin/name/Name;", "getArgumentName", "()Lorg/jetbrains/kotlin/name/Name;", "()Z", "isSpread", "getReceiver", "()Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;", "toString", "", "Companion", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ReceiverExpressionKotlinCallArgument implements ExpressionKotlinCallArgument {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final boolean isForImplicitInvoke;
    private final boolean isSafeCall;
    private final ReceiverValueWithSmartCastInfo receiver;

    public /* synthetic */ ReceiverExpressionKotlinCallArgument(ReceiverValueWithSmartCastInfo receiverValueWithSmartCastInfo, boolean z, boolean z2, DefaultConstructorMarker defaultConstructorMarker) {
        this(receiverValueWithSmartCastInfo, z, z2);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.KotlinCallArgument
    /* JADX INFO: renamed from: getArgumentName */
    public Name getName() {
        return null;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.KotlinCallArgument
    public boolean isSpread() {
        return false;
    }

    private ReceiverExpressionKotlinCallArgument(ReceiverValueWithSmartCastInfo receiverValueWithSmartCastInfo, boolean z, boolean z2) {
        this.receiver = receiverValueWithSmartCastInfo;
        this.isSafeCall = z;
        this.isForImplicitInvoke = z2;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ReceiverKotlinCallArgument
    public ReceiverValueWithSmartCastInfo getReceiver() {
        return this.receiver;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ReceiverKotlinCallArgument
    /* JADX INFO: renamed from: isSafeCall, reason: from getter */
    public boolean getIsSafeCall() {
        return this.isSafeCall;
    }

    /* JADX INFO: renamed from: isForImplicitInvoke, reason: from getter */
    public final boolean getIsForImplicitInvoke() {
        return this.isForImplicitInvoke;
    }

    public String toString() {
        return getReceiver() + (getIsSafeCall() ? TypeDescription.Generic.OfWildcardType.SYMBOL : "");
    }

    /* JADX INFO: compiled from: ArgumentsImpl.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J%\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\bH\u0086\u0002¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/ReceiverExpressionKotlinCallArgument$Companion;", "", "()V", "invoke", "Lorg/jetbrains/kotlin/resolve/calls/model/ReceiverExpressionKotlinCallArgument;", "receiver", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;", "isSafeCall", "", "isForImplicitInvoke", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public static /* synthetic */ ReceiverExpressionKotlinCallArgument invoke$default(Companion companion, ReceiverValueWithSmartCastInfo receiverValueWithSmartCastInfo, boolean z, boolean z2, int i, Object obj) {
            if ((i & 2) != 0) {
                z = false;
            }
            if ((i & 4) != 0) {
                z2 = false;
            }
            return companion.invoke(receiverValueWithSmartCastInfo, z, z2);
        }

        public final ReceiverExpressionKotlinCallArgument invoke(ReceiverValueWithSmartCastInfo receiver, boolean isSafeCall, boolean isForImplicitInvoke) {
            Intrinsics.checkNotNullParameter(receiver, "receiver");
            return new ReceiverExpressionKotlinCallArgument(QualifierReceiverKt.prepareReceiverRegardingCaptureTypes(receiver), isSafeCall, isForImplicitInvoke, null);
        }
    }
}
