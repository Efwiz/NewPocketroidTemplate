package org.jetbrains.kotlin.org.picocontainer;

/* JADX INFO: loaded from: classes4.dex */
public interface ComponentAdapter {
    Class<?> getComponentImplementation();

    Object getComponentInstance(PicoContainer picoContainer);

    Object getComponentKey();
}
