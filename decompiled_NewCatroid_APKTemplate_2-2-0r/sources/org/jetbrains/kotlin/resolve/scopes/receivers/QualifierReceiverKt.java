package org.jetbrains.kotlin.resolve.scopes.receivers;

import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.checker.NewCapturedTypeKt;

/* JADX INFO: compiled from: QualifierReceiver.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0001¨\u0006\u0002"}, d2 = {"prepareReceiverRegardingCaptureTypes", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;", "resolution"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class QualifierReceiverKt {
    public static final ReceiverValueWithSmartCastInfo prepareReceiverRegardingCaptureTypes(ReceiverValueWithSmartCastInfo receiverValueWithSmartCastInfo) {
        Intrinsics.checkNotNullParameter(receiverValueWithSmartCastInfo, "<this>");
        UnwrappedType unwrappedTypePrepareArgumentTypeRegardingCaptureTypes = NewCapturedTypeKt.prepareArgumentTypeRegardingCaptureTypes(receiverValueWithSmartCastInfo.getReceiverValue().getType().unwrap());
        if (unwrappedTypePrepareArgumentTypeRegardingCaptureTypes == null) {
            return receiverValueWithSmartCastInfo;
        }
        ReceiverValue receiverValueMo7466replaceType = receiverValueWithSmartCastInfo.getReceiverValue().mo7466replaceType(unwrappedTypePrepareArgumentTypeRegardingCaptureTypes);
        Intrinsics.checkNotNullExpressionValue(receiverValueMo7466replaceType, "replaceType(...)");
        Set<KotlinType> typesFromSmartCasts = receiverValueWithSmartCastInfo.getTypesFromSmartCasts();
        boolean isStable = receiverValueWithSmartCastInfo.getIsStable();
        KotlinType type = receiverValueWithSmartCastInfo.getReceiverValue().getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        return new ReceiverValueWithSmartCastInfo(receiverValueMo7466replaceType, typesFromSmartCasts, isStable, type);
    }
}
