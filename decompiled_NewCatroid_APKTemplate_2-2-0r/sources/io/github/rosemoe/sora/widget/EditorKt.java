package io.github.rosemoe.sora.widget;

import androidx.exifinterface.media.ExifInterface;
import io.github.rosemoe.sora.event.Event;
import io.github.rosemoe.sora.event.EventReceiver;
import io.github.rosemoe.sora.event.SubscriptionReceipt;
import io.github.rosemoe.sora.widget.component.EditorBuiltinComponent;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Editor.kt */
/* JADX INFO: loaded from: classes11.dex */
@Metadata(d1 = {"\u0000(\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u001e\u0010\u0000\u001a\u0002H\u0001\"\n\b\u0000\u0010\u0001\u0018\u0001*\u00020\u0002*\u00020\u0003H\u0086\b¢\u0006\u0002\u0010\u0004\u001a&\u0010\u0005\u001a\u00020\u0006\"\n\b\u0000\u0010\u0001\u0018\u0001*\u00020\u0002*\u00020\u00032\u0006\u0010\u0007\u001a\u0002H\u0001H\u0086\b¢\u0006\u0002\u0010\b\u001a-\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00010\n\"\n\b\u0000\u0010\u0001\u0018\u0001*\u00020\u000b*\u00020\u00032\f\u0010\f\u001a\b\u0012\u0004\u0012\u0002H\u00010\rH\u0086\b¨\u0006\u000e"}, d2 = {"getComponent", ExifInterface.GPS_DIRECTION_TRUE, "Lio/github/rosemoe/sora/widget/component/EditorBuiltinComponent;", "Lio/github/rosemoe/sora/widget/CodeEditor;", "(Lio/github/rosemoe/sora/widget/CodeEditor;)Lio/github/rosemoe/sora/widget/component/EditorBuiltinComponent;", "replaceComponent", "", "component", "(Lio/github/rosemoe/sora/widget/CodeEditor;Lio/github/rosemoe/sora/widget/component/EditorBuiltinComponent;)V", "subscribeEvent", "Lio/github/rosemoe/sora/event/SubscriptionReceipt;", "Lio/github/rosemoe/sora/event/Event;", "receiver", "Lio/github/rosemoe/sora/event/EventReceiver;", "editor_release"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class EditorKt {
    public static final /* synthetic */ <T extends Event> SubscriptionReceipt<T> subscribeEvent(CodeEditor codeEditor, EventReceiver<T> receiver) {
        Intrinsics.checkNotNullParameter(codeEditor, "<this>");
        Intrinsics.checkNotNullParameter(receiver, "receiver");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        SubscriptionReceipt<T> subscriptionReceiptSubscribeEvent = codeEditor.subscribeEvent(Event.class, receiver);
        Intrinsics.checkNotNullExpressionValue(subscriptionReceiptSubscribeEvent, "subscribeEvent(...)");
        return subscriptionReceiptSubscribeEvent;
    }

    public static final /* synthetic */ <T extends EditorBuiltinComponent> T getComponent(CodeEditor codeEditor) {
        Intrinsics.checkNotNullParameter(codeEditor, "<this>");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        T t = (T) codeEditor.getComponent(EditorBuiltinComponent.class);
        Intrinsics.checkNotNullExpressionValue(t, "getComponent(...)");
        return t;
    }

    public static final /* synthetic */ <T extends EditorBuiltinComponent> void replaceComponent(CodeEditor codeEditor, T component) {
        Intrinsics.checkNotNullParameter(codeEditor, "<this>");
        Intrinsics.checkNotNullParameter(component, "component");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        codeEditor.replaceComponent(EditorBuiltinComponent.class, component);
    }
}
