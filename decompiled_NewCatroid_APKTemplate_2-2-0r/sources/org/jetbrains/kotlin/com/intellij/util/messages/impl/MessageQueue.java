package org.jetbrains.kotlin.com.intellij.util.messages.impl;

import java.util.ArrayDeque;
import java.util.Deque;

/* JADX INFO: compiled from: MessageBusImpl.java */
/* JADX INFO: loaded from: classes3.dex */
final class MessageQueue {
    Message current;
    final Deque<Message> queue = new ArrayDeque();

    MessageQueue() {
    }
}
