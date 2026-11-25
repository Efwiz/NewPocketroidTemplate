package org.jetbrains.kotlin.com.intellij.util.messages.impl;

import java.util.List;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.PluginDescriptor;
import org.jetbrains.kotlin.com.intellij.util.messages.Topic;

/* JADX INFO: loaded from: classes3.dex */
final class DescriptorBasedMessageBusConnection<L> implements MessageBusImpl.MessageHandlerHolder {
    final List<? extends L> handlers;
    final PluginDescriptor module;
    final Topic<L> topic;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
            case 3:
                objArr[0] = "topic";
                break;
            case 2:
            case 6:
                objArr[0] = "handlers";
                break;
            case 4:
                objArr[0] = "result";
                break;
            case 5:
                objArr[0] = "predicate";
                break;
            case 7:
                objArr[0] = "excludeClassNames";
                break;
            default:
                objArr[0] = "module";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/util/messages/impl/DescriptorBasedMessageBusConnection";
        if (i == 3 || i == 4) {
            objArr[2] = "collectHandlers";
        } else if (i == 5) {
            objArr[2] = "disconnectIfNeeded";
        } else if (i == 6 || i == 7) {
            objArr[2] = "computeNewHandlers";
        } else {
            objArr[2] = "<init>";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.messages.impl.MessageBusImpl.MessageHandlerHolder
    public boolean isDisposed() {
        return false;
    }

    DescriptorBasedMessageBusConnection(PluginDescriptor pluginDescriptor, Topic<L> topic, List<? extends L> list) {
        if (pluginDescriptor == null) {
            $$$reportNull$$$0(0);
        }
        if (topic == null) {
            $$$reportNull$$$0(1);
        }
        if (list == null) {
            $$$reportNull$$$0(2);
        }
        this.module = pluginDescriptor;
        this.topic = topic;
        this.handlers = list;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.messages.impl.MessageBusImpl.MessageHandlerHolder
    public <L1> void collectHandlers(Topic<L1> topic, List<? super L1> list) {
        if (topic == null) {
            $$$reportNull$$$0(3);
        }
        if (list == null) {
            $$$reportNull$$$0(4);
        }
        if (this.topic == topic) {
            list.addAll(this.handlers);
        }
    }

    public String toString() {
        return "DescriptorBasedMessageBusConnection(handlers=" + this.handlers + ')';
    }
}
