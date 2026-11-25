package org.jetbrains.kotlin.org.picocontainer.defaults;

import java.lang.reflect.Constructor;
import java.util.Collection;
import org.jetbrains.kotlin.org.picocontainer.PicoIntrospectionException;

/* JADX INFO: loaded from: classes4.dex */
public final class TooManySatisfiableConstructorsException extends PicoIntrospectionException {
    private final Collection<Constructor<?>> constructors;

    public TooManySatisfiableConstructorsException(Collection<Constructor<?>> collection) {
        super("Too many satisfiable constructors:" + collection.toString());
        this.constructors = collection;
    }
}
