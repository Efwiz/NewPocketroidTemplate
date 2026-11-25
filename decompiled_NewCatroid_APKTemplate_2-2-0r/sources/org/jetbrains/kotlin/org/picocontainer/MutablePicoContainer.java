package org.jetbrains.kotlin.org.picocontainer;

/* JADX INFO: loaded from: classes4.dex */
public interface MutablePicoContainer extends PicoContainer {
    @Deprecated
    ComponentAdapter registerComponentImplementation(Object obj, Class<?> cls);

    @Deprecated
    ComponentAdapter registerComponentInstance(Object obj, Object obj2);

    ComponentAdapter unregisterComponent(Object obj);
}
