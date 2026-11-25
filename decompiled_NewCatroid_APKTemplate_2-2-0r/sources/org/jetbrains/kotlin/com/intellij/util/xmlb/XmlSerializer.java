package org.jetbrains.kotlin.com.intellij.util.xmlb;

import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;

/* JADX INFO: loaded from: classes3.dex */
public final class XmlSerializer {
    private static final SerializationFilter TRUE_FILTER = new SerializationFilter() { // from class: org.jetbrains.kotlin.com.intellij.util.xmlb.XmlSerializer.1
    };

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 6 || i == 11) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 6 || i == 11) ? 2 : 3];
        switch (i) {
            case 2:
            case 6:
            case 11:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/xmlb/XmlSerializer";
                break;
            case 3:
            default:
                objArr[0] = "object";
                break;
            case 4:
            case 9:
            case 13:
            case 15:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 5:
            case 10:
                objArr[0] = "aClass";
                break;
            case 7:
                objArr[0] = "url";
                break;
            case 8:
            case 12:
            case 14:
                objArr[0] = "bean";
                break;
        }
        if (i == 2) {
            objArr[1] = "serialize";
        } else if (i == 6) {
            objArr[1] = "deserialize";
        } else if (i != 11) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/xmlb/XmlSerializer";
        } else {
            objArr[1] = "getBeanBinding";
        }
        switch (i) {
            case 2:
            case 6:
            case 11:
                break;
            case 3:
                objArr[2] = "serializeIfNotDefault";
                break;
            case 4:
            case 5:
            case 7:
                objArr[2] = "deserialize";
                break;
            case 8:
            case 9:
                objArr[2] = "deserializeInto";
                break;
            case 10:
                objArr[2] = "getBeanBinding";
                break;
            case 12:
            case 13:
            case 14:
            case 15:
                objArr[2] = "serializeInto";
                break;
            default:
                objArr[2] = "serialize";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 6 && i != 11) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public static BeanBinding getBeanBinding(Class<?> cls) {
        if (cls == null) {
            $$$reportNull$$$0(10);
        }
        BeanBinding beanBinding = (BeanBinding) XmlSerializerImpl.serializer.getRootBinding(cls, cls);
        if (beanBinding == null) {
            $$$reportNull$$$0(11);
        }
        return beanBinding;
    }
}
