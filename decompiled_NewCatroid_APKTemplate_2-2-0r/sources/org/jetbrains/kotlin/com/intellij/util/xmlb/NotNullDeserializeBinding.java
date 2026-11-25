package org.jetbrains.kotlin.com.intellij.util.xmlb;

import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.util.XmlElement;

/* JADX INFO: loaded from: classes3.dex */
public abstract class NotNullDeserializeBinding implements Binding {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "org/jetbrains/kotlin/com/intellij/util/xmlb/NotNullDeserializeBinding", "deserializeUnsafe"));
    }

    public abstract Object deserialize(Object obj, XmlElement xmlElement);

    @Override // org.jetbrains.kotlin.com.intellij.util.xmlb.Binding
    public final Object deserializeUnsafe(Object obj, XmlElement xmlElement) {
        if (xmlElement == null) {
            $$$reportNull$$$0(1);
        }
        return deserialize(obj, xmlElement);
    }
}
