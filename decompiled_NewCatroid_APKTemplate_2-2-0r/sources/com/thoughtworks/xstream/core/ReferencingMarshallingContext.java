package com.thoughtworks.xstream.core;

import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.io.path.Path;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/core/ReferencingMarshallingContext.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public interface ReferencingMarshallingContext extends MarshallingContext {
    Path currentPath();

    Object lookupReference(Object obj);

    void registerImplicit(Object obj);

    void replace(Object obj, Object obj2);
}
