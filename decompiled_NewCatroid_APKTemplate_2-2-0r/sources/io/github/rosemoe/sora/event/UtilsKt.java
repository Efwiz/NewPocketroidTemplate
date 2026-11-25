package io.github.rosemoe.sora.event;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX INFO: compiled from: Utils.kt */
/* JADX INFO: loaded from: classes11.dex */
@Metadata(d1 = {"\u0000*\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0010\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00010\u0002\u001a5\u0010\u0003\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u0001H\u0005H\u00050\u0004\"\n\b\u0000\u0010\u0005\u0018\u0001*\u00020\u0007*\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00050\nH\u0086\b\u001a5\u0010\u000b\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u0001H\u0005H\u00050\u0004\"\n\b\u0000\u0010\u0005\u0018\u0001*\u00020\u0007*\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00050\fH\u0086\b¨\u0006\r"}, d2 = {"getResultBoolean", "", "Lio/github/rosemoe/sora/event/ResultedEvent;", "subscribeAlways", "Lio/github/rosemoe/sora/event/SubscriptionReceipt;", ExifInterface.GPS_DIRECTION_TRUE, JvmProtoBufUtil.PLATFORM_TYPE_ID, "Lio/github/rosemoe/sora/event/Event;", "Lio/github/rosemoe/sora/event/EventManager;", "receiver", "Lio/github/rosemoe/sora/event/EventManager$NoUnsubscribeReceiver;", "subscribeEvent", "Lio/github/rosemoe/sora/event/EventReceiver;", "editor_release"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class UtilsKt {
    public static final boolean getResultBoolean(ResultedEvent<Boolean> resultedEvent) {
        Intrinsics.checkNotNullParameter(resultedEvent, "<this>");
        if (!resultedEvent.isResultSet()) {
            return false;
        }
        Boolean result = resultedEvent.getResult();
        Intrinsics.checkNotNull(result);
        Intrinsics.checkNotNull(result);
        return result.booleanValue();
    }

    public static final /* synthetic */ <T extends Event> SubscriptionReceipt<T> subscribeEvent(EventManager eventManager, EventReceiver<T> receiver) {
        Intrinsics.checkNotNullParameter(eventManager, "<this>");
        Intrinsics.checkNotNullParameter(receiver, "receiver");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        SubscriptionReceipt<T> subscriptionReceiptSubscribeEvent = eventManager.subscribeEvent(Event.class, receiver);
        Intrinsics.checkNotNullExpressionValue(subscriptionReceiptSubscribeEvent, "subscribeEvent(...)");
        return subscriptionReceiptSubscribeEvent;
    }

    public static final /* synthetic */ <T extends Event> SubscriptionReceipt<T> subscribeAlways(EventManager eventManager, EventManager.NoUnsubscribeReceiver<T> receiver) {
        Intrinsics.checkNotNullParameter(eventManager, "<this>");
        Intrinsics.checkNotNullParameter(receiver, "receiver");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        SubscriptionReceipt<T> subscriptionReceiptSubscribeAlways = eventManager.subscribeAlways(Event.class, receiver);
        Intrinsics.checkNotNullExpressionValue(subscriptionReceiptSubscribeAlways, "subscribeAlways(...)");
        return subscriptionReceiptSubscribeAlways;
    }
}
