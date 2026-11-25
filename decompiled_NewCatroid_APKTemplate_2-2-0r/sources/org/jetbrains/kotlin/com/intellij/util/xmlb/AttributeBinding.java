package org.jetbrains.kotlin.com.intellij.util.xmlb;

import net.bytebuddy.implementation.Implementation;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.serialization.ClassUtil;
import org.jetbrains.kotlin.com.intellij.serialization.MutableAccessor;
import org.jetbrains.kotlin.com.intellij.util.XmlElement;
import org.jetbrains.kotlin.com.intellij.util.xmlb.annotations.Attribute;

/* JADX INFO: loaded from: classes3.dex */
final class AttributeBinding extends BasePrimitiveBinding {
    private final Class<?> valueClass;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 4 || i == 7) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 4 || i == 7) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "o";
                break;
            case 2:
            case 5:
                objArr[0] = "context";
                break;
            case 3:
            case 6:
            case 10:
            case 11:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 4:
            case 7:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/xmlb/AttributeBinding";
                break;
            case 8:
                objArr[0] = "host";
                break;
            case 9:
                objArr[0] = "value";
                break;
            default:
                objArr[0] = Implementation.Context.Default.ACCESSOR_METHOD_SUFFIX;
                break;
        }
        if (i == 4 || i == 7) {
            objArr[1] = "deserialize";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/xmlb/AttributeBinding";
        }
        switch (i) {
            case 1:
                objArr[2] = "serialize";
                break;
            case 2:
            case 3:
            case 5:
            case 6:
                objArr[2] = "deserialize";
                break;
            case 4:
            case 7:
                break;
            case 8:
            case 9:
                objArr[2] = FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX;
                break;
            case 10:
            case 11:
                objArr[2] = "isBoundTo";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 4 && i != 7) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.xmlb.Binding
    public boolean isBoundTo(XmlElement xmlElement) {
        if (xmlElement != null) {
            return false;
        }
        $$$reportNull$$$0(11);
        return false;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    AttributeBinding(MutableAccessor mutableAccessor, Attribute attribute) {
        super(mutableAccessor, attribute == null ? null : attribute.value(), attribute != null ? attribute.converter() : null);
        if (mutableAccessor == null) {
            $$$reportNull$$$0(0);
        }
        this.valueClass = ClassUtil.typeToClass(mutableAccessor.getGenericType());
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.xmlb.BasePrimitiveBinding
    public Object deserialize(Object obj, XmlElement xmlElement) {
        if (obj == null) {
            $$$reportNull$$$0(5);
        }
        if (xmlElement == null) {
            $$$reportNull$$$0(6);
        }
        if (obj == null) {
            $$$reportNull$$$0(7);
        }
        return obj;
    }

    void set(Object obj, String str) {
        if (obj == null) {
            $$$reportNull$$$0(8);
        }
        if (str == null) {
            $$$reportNull$$$0(9);
        }
        if (this.myConverter == null) {
            XmlSerializerImpl.doSet(obj, str, this.myAccessor, this.valueClass);
        } else {
            this.myAccessor.set(obj, this.myConverter.fromString(str));
        }
    }

    public String toString() {
        return "AttributeBinding[" + this.f726name + "]";
    }
}
