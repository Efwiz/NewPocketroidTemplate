package org.jetbrains.kotlin.load.java.descriptors;

import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.mpp.MppJavaImplicitActualizatorMarker;

/* JADX INFO: loaded from: classes5.dex */
public interface JavaClassDescriptor extends ClassDescriptor, MppJavaImplicitActualizatorMarker {
    boolean isRecord();
}
