package org.jetbrains.kotlin.com.intellij.lang.jvm.types;

import java.util.Collection;
import org.jetbrains.kotlin.com.intellij.lang.jvm.JvmTypeParameter;

/* JADX INFO: loaded from: classes3.dex */
public interface JvmSubstitutor {
    Collection<JvmTypeParameter> getTypeParameters();

    JvmType substitute(JvmTypeParameter jvmTypeParameter);
}
