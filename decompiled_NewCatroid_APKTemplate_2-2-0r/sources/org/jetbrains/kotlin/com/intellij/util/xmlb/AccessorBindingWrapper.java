package org.jetbrains.kotlin.com.intellij.util.xmlb;

import com.reandroid.archive.PathTree;
import java.awt.Rectangle;
import java.util.List;
import net.bytebuddy.implementation.Implementation;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtilRt;
import org.jetbrains.kotlin.com.intellij.serialization.MutableAccessor;
import org.jetbrains.kotlin.com.intellij.util.XmlElement;
import org.jetbrains.kotlin.com.intellij.util.xmlb.annotations.Property;

/* JADX INFO: loaded from: classes3.dex */
final class AccessorBindingWrapper implements MultiNodeBinding, NestedBinding {
    private final MutableAccessor accessor;
    private final Property.Style beanStyle;
    private final Binding binding;
    final boolean isFlat;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 8 || i == 11) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 8 || i == 11) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "binding";
                break;
            case 2:
            case 8:
            case 11:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/xmlb/AccessorBindingWrapper";
                break;
            case 3:
                objArr[0] = "o";
                break;
            case 4:
            case 5:
            case 12:
            case 13:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 6:
            case 9:
                objArr[0] = "context";
                break;
            case 7:
            case 10:
                objArr[0] = PathTree.NAME_elements;
                break;
            default:
                objArr[0] = Implementation.Context.Default.ACCESSOR_METHOD_SUFFIX;
                break;
        }
        if (i == 2) {
            objArr[1] = "getAccessor";
        } else if (i == 8) {
            objArr[1] = "deserializeList";
        } else if (i != 11) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/xmlb/AccessorBindingWrapper";
        } else {
            objArr[1] = "deserializeList2";
        }
        switch (i) {
            case 2:
            case 8:
            case 11:
                break;
            case 3:
                objArr[2] = "serialize";
                break;
            case 4:
            case 5:
                objArr[2] = "deserializeUnsafe";
                break;
            case 6:
            case 7:
                objArr[2] = "deserializeList";
                break;
            case 9:
            case 10:
                objArr[2] = "deserializeList2";
                break;
            case 12:
            case 13:
                objArr[2] = "isBoundTo";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 8 && i != 11) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    AccessorBindingWrapper(MutableAccessor mutableAccessor, Binding binding, boolean z, Property.Style style) {
        if (mutableAccessor == null) {
            $$$reportNull$$$0(0);
        }
        if (binding == null) {
            $$$reportNull$$$0(1);
        }
        this.accessor = mutableAccessor;
        this.binding = binding;
        this.isFlat = z;
        this.beanStyle = style;
    }

    /* JADX WARN: Code duplicated, block: B:22:0x006e  */
    @Override // org.jetbrains.kotlin.com.intellij.util.xmlb.Binding
    public Object deserializeUnsafe(Object obj, XmlElement xmlElement) {
        Object objDeserializeUnsafe;
        if (xmlElement == null) {
            $$$reportNull$$$0(5);
        }
        Object obj2 = this.accessor.read(obj);
        if ((this.binding instanceof BeanBinding) && !this.accessor.isWritable()) {
            ((BeanBinding) this.binding).deserializeInto(obj2, xmlElement);
        } else {
            if (this.beanStyle == Property.Style.ATTRIBUTE) {
                Binding binding = this.binding;
                if ((binding instanceof BeanBinding) && ((BeanBinding) binding).myBeanClass == Rectangle.class) {
                    String attributeValue = xmlElement.getAttributeValue("x");
                    String attributeValue2 = xmlElement.getAttributeValue("y");
                    String attributeValue3 = xmlElement.getAttributeValue("width");
                    String attributeValue4 = xmlElement.getAttributeValue("height");
                    if (attributeValue == null || attributeValue2 == null || attributeValue3 == null || attributeValue4 == null) {
                        return obj;
                    }
                    objDeserializeUnsafe = new Rectangle(StringUtilRt.parseInt(attributeValue, 0), StringUtilRt.parseInt(attributeValue2, 0), StringUtilRt.parseInt(attributeValue3, 0), StringUtilRt.parseInt(attributeValue4, 0));
                } else {
                    objDeserializeUnsafe = this.binding.deserializeUnsafe(obj2, xmlElement);
                }
            } else {
                objDeserializeUnsafe = this.binding.deserializeUnsafe(obj2, xmlElement);
            }
            if (obj2 != objDeserializeUnsafe) {
                this.accessor.set(obj, objDeserializeUnsafe);
            }
        }
        return obj;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.xmlb.MultiNodeBinding
    public Object deserializeList2(Object obj, List<XmlElement> list) {
        if (obj == null) {
            $$$reportNull$$$0(9);
        }
        if (list == null) {
            $$$reportNull$$$0(10);
        }
        Object obj2 = this.accessor.read(obj);
        if ((this.binding instanceof BeanBinding) && !this.accessor.isWritable()) {
            ((BeanBinding) this.binding).deserializeInto(obj2, list.get(0));
        } else {
            Object objDeserializeList2 = Binding.deserializeList2(this.binding, obj2, list);
            if (obj2 != objDeserializeList2) {
                this.accessor.set(obj, objDeserializeList2);
            }
        }
        if (obj == null) {
            $$$reportNull$$$0(11);
        }
        return obj;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.xmlb.MultiNodeBinding
    public boolean isMulti() {
        Binding binding = this.binding;
        return (binding instanceof MultiNodeBinding) && ((MultiNodeBinding) binding).isMulti();
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.xmlb.Binding
    public boolean isBoundTo(XmlElement xmlElement) {
        if (xmlElement == null) {
            $$$reportNull$$$0(13);
        }
        Binding binding = this.binding;
        if (binding instanceof MapBinding) {
            return ((MapBinding) binding).isBoundToWithoutProperty(xmlElement.name);
        }
        return binding.isBoundTo(xmlElement);
    }

    public String toString() {
        return this.binding.toString();
    }
}
