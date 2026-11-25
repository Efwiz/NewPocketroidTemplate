package org.jetbrains.kotlin.com.intellij.util.messages.impl;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtilRt;
import org.jetbrains.kotlin.com.intellij.util.messages.MessageBusConnection;
import org.jetbrains.kotlin.com.intellij.util.messages.MessageHandler;
import org.jetbrains.kotlin.com.intellij.util.messages.Topic;

/* JADX INFO: loaded from: classes3.dex */
final class MessageBusConnectionImpl extends BaseBusConnection implements MessageBusConnection {
    private MessageHandler defaultHandler;

    /* JADX WARN: Code duplicated, block: B:12:0x0026  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "topic";
        } else if (i == 2) {
            objArr[0] = CrashHianalyticsData.MESSAGE;
        } else if (i == 3) {
            objArr[0] = "topicAndHandlerPairs";
        } else if (i == 4) {
            objArr[0] = "topic";
        } else if (i != 5) {
            objArr[0] = "bus";
        } else {
            objArr[0] = "handler";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/util/messages/impl/MessageBusConnectionImpl";
        if (i == 1) {
            objArr[2] = "subscribe";
        } else if (i == 2 || i == 3) {
            objArr[2] = "nullizeHandlersFromMessage";
        } else if (i == 4 || i == 5) {
            objArr[2] = "isMyHandler";
        } else {
            objArr[2] = "<init>";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    MessageBusConnectionImpl(MessageBusImpl messageBusImpl) {
        super(messageBusImpl);
        if (messageBusImpl == null) {
            $$$reportNull$$$0(0);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.Disposable
    public void dispose() {
        MessageBusImpl messageBusImpl = this.bus;
        if (messageBusImpl == null) {
            return;
        }
        this.bus = null;
        this.defaultHandler = null;
        messageBusImpl.notifyConnectionTerminated(this.subscriptions.getAndSet(ArrayUtilRt.EMPTY_OBJECT_ARRAY));
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.messages.MessageBusConnection
    public void deliverImmediately() {
        this.bus.deliverImmediately(this);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean nullizeHandlersFromMessage(Message message, Object[] objArr) {
        if (message == null) {
            $$$reportNull$$$0(2);
        }
        if (objArr == null) {
            $$$reportNull$$$0(3);
        }
        int i = 0;
        for (int i2 = 0; i2 < message.handlers.length; i2++) {
            Object obj = message.handlers[i2];
            if (obj == null) {
                i++;
            }
            for (int i3 = 0; i3 < objArr.length; i3 += 2) {
                if (message.topic == objArr[i3] && obj == objArr[i3 + 1]) {
                    message.handlers[i2] = null;
                    i++;
                }
            }
        }
        return i == message.handlers.length;
    }

    boolean isMyHandler(Topic<?> topic, Object obj) {
        if (topic == null) {
            $$$reportNull$$$0(4);
        }
        if (obj == null) {
            $$$reportNull$$$0(5);
        }
        if (this.defaultHandler == obj) {
            return true;
        }
        Object[] objArr = this.subscriptions.get();
        int length = objArr.length;
        for (int i = 0; i < length; i += 2) {
            if (topic == objArr[i] && obj == objArr[i + 1]) {
                return true;
            }
        }
        return false;
    }
}
