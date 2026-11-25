package org.jetbrains.kotlin.com.intellij.util.messages.impl;

import androidx.lifecycle.LifecycleKt$$ExternalSyntheticBackportWithForwarding0;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtilRt;
import org.jetbrains.kotlin.com.intellij.util.messages.Topic;

/* JADX INFO: loaded from: classes3.dex */
abstract class BaseBusConnection implements MessageBusImpl.MessageHandlerHolder {
    protected MessageBusImpl bus;
    protected final AtomicReference<Object[]> subscriptions;

    /* JADX WARN: Code duplicated, block: B:12:0x0026  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "topic";
        } else if (i == 2) {
            objArr[0] = "handler";
        } else if (i == 3) {
            objArr[0] = "topic";
        } else if (i == 4) {
            objArr[0] = "result";
        } else if (i != 5) {
            objArr[0] = "bus";
        } else {
            objArr[0] = "predicate";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/util/messages/impl/BaseBusConnection";
        if (i == 1 || i == 2) {
            objArr[2] = "subscribe";
        } else if (i == 3 || i == 4) {
            objArr[2] = "collectHandlers";
        } else if (i != 5) {
            objArr[2] = "<init>";
        } else {
            objArr[2] = "disconnectIfNeeded";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    BaseBusConnection(MessageBusImpl messageBusImpl) {
        if (messageBusImpl == null) {
            $$$reportNull$$$0(0);
        }
        this.subscriptions = new AtomicReference<>(ArrayUtilRt.EMPTY_OBJECT_ARRAY);
        this.bus = messageBusImpl;
    }

    public final <L> void subscribe(Topic<L> topic, L l) {
        Object[] objArr;
        Object[] objArr2;
        if (topic == null) {
            $$$reportNull$$$0(1);
        }
        if (l == null) {
            $$$reportNull$$$0(2);
        }
        do {
            objArr = this.subscriptions.get();
            if (objArr.length == 0) {
                objArr2 = new Object[]{topic, l};
            } else {
                int length = objArr.length;
                Object[] objArrCopyOf = Arrays.copyOf(objArr, length + 2);
                objArrCopyOf[length] = topic;
                objArrCopyOf[length + 1] = l;
                objArr2 = objArrCopyOf;
            }
        } while (!LifecycleKt$$ExternalSyntheticBackportWithForwarding0.m(this.subscriptions, objArr, objArr2));
        this.bus.notifyOnSubscription(topic);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.messages.impl.MessageBusImpl.MessageHandlerHolder
    public final <L> void collectHandlers(Topic<L> topic, List<? super L> list) {
        if (topic == null) {
            $$$reportNull$$$0(3);
        }
        if (list == null) {
            $$$reportNull$$$0(4);
        }
        Object[] objArr = this.subscriptions.get();
        int length = objArr.length;
        for (int i = 0; i < length; i += 2) {
            if (objArr[i] == topic) {
                list.add(objArr[i + 1]);
            }
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.messages.impl.MessageBusImpl.MessageHandlerHolder
    public final boolean isDisposed() {
        return this.bus == null;
    }

    public final String toString() {
        return Arrays.toString(this.subscriptions.get());
    }
}
