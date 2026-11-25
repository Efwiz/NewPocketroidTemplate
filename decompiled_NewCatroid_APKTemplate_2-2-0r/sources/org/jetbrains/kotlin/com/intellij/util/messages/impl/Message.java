package org.jetbrains.kotlin.com.intellij.util.messages.impl;

import java.lang.invoke.MethodHandle;
import java.util.Arrays;
import org.jetbrains.kotlin.com.intellij.codeWithMe.ClientId;
import org.jetbrains.kotlin.com.intellij.util.messages.Topic;

/* JADX INFO: loaded from: classes3.dex */
final class Message {
    final Object[] args;
    final ClientId clientId;
    int currentHandlerIndex;
    final Object[] handlers;
    final MethodHandle method;
    final String methodName;
    final Topic<?> topic;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "method";
        } else if (i == 2) {
            objArr[0] = "methodName";
        } else if (i != 3) {
            objArr[0] = "topic";
        } else {
            objArr[0] = "handlers";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/util/messages/impl/Message";
        objArr[2] = "<init>";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    Message(Topic<?> topic, MethodHandle methodHandle, String str, Object[] objArr, Object[] objArr2) {
        if (topic == null) {
            $$$reportNull$$$0(0);
        }
        if (methodHandle == null) {
            $$$reportNull$$$0(1);
        }
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        if (objArr2 == null) {
            $$$reportNull$$$0(3);
        }
        this.topic = topic;
        this.method = methodHandle;
        this.methodName = str;
        this.args = objArr;
        this.handlers = objArr2;
        this.clientId = ClientId.getCurrentOrNull();
    }

    public String toString() {
        return "Message(topic=" + this.topic + ", method=" + this.methodName + ", args=" + Arrays.toString(this.args) + ", handlers=" + Arrays.toString(this.handlers) + ')';
    }
}
