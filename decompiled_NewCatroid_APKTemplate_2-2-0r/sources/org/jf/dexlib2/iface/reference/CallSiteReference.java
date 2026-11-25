package org.jf.dexlib2.iface.reference;

import java.util.List;
import org.jf.dexlib2.iface.value.EncodedValue;

/* JADX INFO: loaded from: classes6.dex */
public interface CallSiteReference extends Reference {
    boolean equals(Object obj);

    List<? extends EncodedValue> getExtraArguments();

    MethodHandleReference getMethodHandle();

    String getMethodName();

    MethodProtoReference getMethodProto();

    String getName();

    int hashCode();
}
