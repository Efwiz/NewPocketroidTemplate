package org.jetbrains.kotlin.resolve.calls.model;

import java.util.List;
import kotlin.Metadata;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: KotlinCall.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001R\u0018\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0012\u0010\u0007\u001a\u00020\bX¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0016\u0010\u000b\u001a\u0004\u0018\u00010\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u0004\u0018\u00010\fX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u000eR\u0014\u0010\u0011\u001a\u0004\u0018\u00010\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u0012\u0010\u0014\u001a\u00020\u0015X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0016R\u0012\u0010\u0017\u001a\u00020\u0018X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001aR\u0018\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u0006ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u001eÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCall;", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolutionAtom;", "argumentsInParenthesis", "", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallArgument;", "getArgumentsInParenthesis", "()Ljava/util/List;", "callKind", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallKind;", "getCallKind", "()Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallKind;", "dispatchReceiverForInvokeExtension", "Lorg/jetbrains/kotlin/resolve/calls/model/ReceiverKotlinCallArgument;", "getDispatchReceiverForInvokeExtension", "()Lorg/jetbrains/kotlin/resolve/calls/model/ReceiverKotlinCallArgument;", "explicitReceiver", "getExplicitReceiver", "externalArgument", "getExternalArgument", "()Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallArgument;", "isForImplicitInvoke", "", "()Z", "name", "Lorg/jetbrains/kotlin/name/Name;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "typeArguments", "Lorg/jetbrains/kotlin/resolve/calls/model/TypeArgument;", "getTypeArguments", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface KotlinCall extends ResolutionAtom {
    List<KotlinCallArgument> getArgumentsInParenthesis();

    KotlinCallKind getCallKind();

    default ReceiverKotlinCallArgument getDispatchReceiverForInvokeExtension() {
        return null;
    }

    ReceiverKotlinCallArgument getExplicitReceiver();

    KotlinCallArgument getExternalArgument();

    Name getName();

    List<TypeArgument> getTypeArguments();

    boolean isForImplicitInvoke();
}
