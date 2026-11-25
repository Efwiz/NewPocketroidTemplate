package org.jetbrains.kotlin.com.intellij.util.messages.impl;

import org.jetbrains.kotlin.com.intellij.util.messages.SimpleMessageBusConnection;

/* JADX INFO: loaded from: classes3.dex */
final class SimpleMessageBusConnectionImpl extends BaseBusConnection implements SimpleMessageBusConnection {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "bus", "org/jetbrains/kotlin/com/intellij/util/messages/impl/SimpleMessageBusConnectionImpl", "<init>"));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    SimpleMessageBusConnectionImpl(MessageBusImpl messageBusImpl) {
        super(messageBusImpl);
        if (messageBusImpl == null) {
            $$$reportNull$$$0(0);
        }
    }
}
