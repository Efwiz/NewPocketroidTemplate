package org.jetbrains.kotlin.org.picocontainer.defaults;

import org.jetbrains.kotlin.org.picocontainer.PicoInitializationException;

/* JADX INFO: loaded from: classes4.dex */
public class PicoInvocationTargetInitializationException extends PicoInitializationException {
    public PicoInvocationTargetInitializationException(Throwable th) {
        super("InvocationTargetException: " + th.getClass().getName() + " " + th.getMessage(), th);
    }
}
