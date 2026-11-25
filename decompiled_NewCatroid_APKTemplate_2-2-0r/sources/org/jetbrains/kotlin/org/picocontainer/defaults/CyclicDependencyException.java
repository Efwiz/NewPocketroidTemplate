package org.jetbrains.kotlin.org.picocontainer.defaults;

import java.util.LinkedList;
import java.util.List;
import org.jetbrains.kotlin.org.picocontainer.PicoIntrospectionException;

/* JADX INFO: loaded from: classes4.dex */
public class CyclicDependencyException extends PicoIntrospectionException {
    private final List stack;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CyclicDependencyException(Class cls) {
        super((Throwable) null);
        this.stack = new LinkedList();
        push(cls);
    }

    public void push(Class cls) {
        this.stack.add(cls);
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        return "Cyclic dependency: " + this.stack.toString();
    }
}
