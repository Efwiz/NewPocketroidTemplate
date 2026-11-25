package org.jetbrains.kotlin.com.intellij.util.xmlb;

import net.bytebuddy.implementation.Implementation;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.serialization.ClassUtil;
import org.jetbrains.kotlin.com.intellij.serialization.MutableAccessor;
import org.jetbrains.kotlin.com.intellij.util.XmlElement;

/* JADX INFO: loaded from: classes3.dex */
final class TextBinding implements NestedBinding {
    private final MutableAccessor accessor;
    private final Class<?> valueClass;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        switch (i) {
            case 1:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/xmlb/TextBinding";
                break;
            case 2:
                objArr[0] = "o";
                break;
            case 3:
            case 4:
            case 5:
            case 6:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 7:
                objArr[0] = "context";
                break;
            case 8:
                objArr[0] = "value";
                break;
            default:
                objArr[0] = Implementation.Context.Default.ACCESSOR_METHOD_SUFFIX;
                break;
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/xmlb/TextBinding";
        } else {
            objArr[1] = "getAccessor";
        }
        switch (i) {
            case 1:
                break;
            case 2:
                objArr[2] = "serialize";
                break;
            case 3:
            case 4:
                objArr[2] = "isBoundTo";
                break;
            case 5:
            case 6:
                objArr[2] = "deserializeUnsafe";
                break;
            case 7:
            case 8:
                objArr[2] = FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX;
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalStateException(str2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.xmlb.Binding
    public Object deserializeUnsafe(Object obj, XmlElement xmlElement) {
        if (xmlElement == null) {
            $$$reportNull$$$0(6);
        }
        return obj;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.xmlb.Binding
    public boolean isBoundTo(XmlElement xmlElement) {
        if (xmlElement != null) {
            return false;
        }
        $$$reportNull$$$0(4);
        return false;
    }

    TextBinding(MutableAccessor mutableAccessor) {
        if (mutableAccessor == null) {
            $$$reportNull$$$0(0);
        }
        this.accessor = mutableAccessor;
        this.valueClass = ClassUtil.typeToClass(mutableAccessor.getGenericType());
    }

    void set(Object obj, String str) {
        if (obj == null) {
            $$$reportNull$$$0(7);
        }
        if (str == null) {
            $$$reportNull$$$0(8);
        }
        XmlSerializerImpl.doSet(obj, str, this.accessor, this.valueClass);
    }
}
