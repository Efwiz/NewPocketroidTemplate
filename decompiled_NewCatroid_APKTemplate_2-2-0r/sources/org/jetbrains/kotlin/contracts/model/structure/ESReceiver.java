package org.jetbrains.kotlin.contracts.model.structure;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.contracts.model.ESExpressionVisitor;
import org.jetbrains.kotlin.contracts.model.ESValue;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;

/* JADX INFO: compiled from: Values.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J!\u0010\u0006\u001a\u0002H\u0007\"\u0004\b\u0000\u0010\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u0002H\u00070\tH\u0016¢\u0006\u0002\u0010\nR\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u000bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/contracts/model/structure/ESReceiver;", "Lorg/jetbrains/kotlin/contracts/model/ESValue;", "receiverValue", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "getReceiverValue", "()Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "accept", ExifInterface.GPS_DIRECTION_TRUE, "visitor", "Lorg/jetbrains/kotlin/contracts/model/ESExpressionVisitor;", "(Lorg/jetbrains/kotlin/contracts/model/ESExpressionVisitor;)Ljava/lang/Object;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ESReceiver extends ESValue {
    ReceiverValue getReceiverValue();

    @Override // org.jetbrains.kotlin.contracts.model.ESExpression
    default <T> T accept(ESExpressionVisitor<? extends T> visitor) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitReceiver(this);
    }
}
