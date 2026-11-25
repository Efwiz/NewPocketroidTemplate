package org.jetbrains.kotlin.com.intellij.util.xmlb;

import java.util.List;
import net.bytebuddy.implementation.Implementation;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.serialization.ClassUtil;
import org.jetbrains.kotlin.com.intellij.serialization.MutableAccessor;
import org.jetbrains.kotlin.com.intellij.util.XmlElement;
import org.jetbrains.kotlin.com.intellij.util.xmlb.annotations.OptionTag;

/* JADX INFO: loaded from: classes3.dex */
final class OptionTagBinding extends BasePrimitiveBinding {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final String myNameAttribute;
    private final String myTagName;
    private final String myValueAttribute;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3 || i == 6 || i == 9) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3 || i == 6 || i == 9) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "o";
                break;
            case 2:
            case 3:
            case 6:
            case 9:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/xmlb/OptionTagBinding";
                break;
            case 4:
            case 7:
                objArr[0] = "context";
                break;
            case 5:
            case 8:
            case 10:
            case 11:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            default:
                objArr[0] = Implementation.Context.Default.ACCESSOR_METHOD_SUFFIX;
                break;
        }
        if (i == 2 || i == 3) {
            objArr[1] = "serialize";
        } else if (i == 6 || i == 9) {
            objArr[1] = "deserialize";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/xmlb/OptionTagBinding";
        }
        switch (i) {
            case 1:
                objArr[2] = "serialize";
                break;
            case 2:
            case 3:
            case 6:
            case 9:
                break;
            case 4:
            case 5:
            case 7:
            case 8:
                objArr[2] = "deserialize";
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
        if (i != 2 && i != 3 && i != 6 && i != 9) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    OptionTagBinding(MutableAccessor mutableAccessor, OptionTag optionTag) {
        super(mutableAccessor, optionTag == null ? null : optionTag.value(), optionTag != null ? optionTag.converter() : null);
        if (mutableAccessor == null) {
            $$$reportNull$$$0(0);
        }
        if (optionTag == null) {
            this.myTagName = "option";
            this.myNameAttribute = "name";
            this.myValueAttribute = "value";
            return;
        }
        String strInternEmptyString = StringUtil.internEmptyString(optionTag.nameAttribute());
        this.myNameAttribute = strInternEmptyString;
        this.myValueAttribute = StringUtil.internEmptyString(optionTag.valueAttribute());
        String strTag = optionTag.tag();
        if (StringUtil.isEmpty(strInternEmptyString) && "option".equals(strTag)) {
            strTag = this.myAccessor.getName();
        }
        this.myTagName = strTag;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.xmlb.BasePrimitiveBinding
    public Object deserialize(Object obj, XmlElement xmlElement) {
        Object objDeserializeList2;
        if (obj == null) {
            $$$reportNull$$$0(7);
        }
        if (xmlElement == null) {
            $$$reportNull$$$0(8);
        }
        String attributeValue = xmlElement.getAttributeValue(this.myValueAttribute);
        if (attributeValue == null) {
            if (this.myValueAttribute.isEmpty()) {
                this.myAccessor.set(obj, this.myBinding.deserializeUnsafe(obj, xmlElement));
            } else {
                List<XmlElement> list = xmlElement.children;
                if (list.isEmpty()) {
                    if ((this.myBinding instanceof CollectionBinding) || (this.myBinding instanceof MapBinding)) {
                        Object obj2 = this.myAccessor.read(obj);
                        if (obj2 != null && obj2 != (objDeserializeList2 = ((MultiNodeBinding) this.myBinding).deserializeList2(obj2, list))) {
                            this.myAccessor.set(obj, objDeserializeList2);
                        }
                    } else {
                        this.myAccessor.set(obj, null);
                    }
                } else {
                    Object obj3 = this.myAccessor.read(obj);
                    Object objDeserializeList3 = Binding.deserializeList2(this.myBinding, obj3, list);
                    if (obj3 != objDeserializeList3) {
                        this.myAccessor.set(obj, objDeserializeList3);
                    }
                }
            }
        } else if (this.myConverter == null) {
            try {
                XmlSerializerImpl.doSet(obj, attributeValue, this.myAccessor, ClassUtil.typeToClass(this.myAccessor.getGenericType()));
            } catch (Exception e) {
                throw new RuntimeException("Cannot set value for field " + this.f726name, e);
            }
        } else {
            this.myAccessor.set(obj, this.myConverter.fromString(attributeValue));
        }
        if (obj == null) {
            $$$reportNull$$$0(9);
        }
        return obj;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.xmlb.Binding
    public boolean isBoundTo(XmlElement xmlElement) {
        if (xmlElement == null) {
            $$$reportNull$$$0(11);
        }
        if (!xmlElement.name.equals(this.myTagName)) {
            return false;
        }
        String attributeValue = xmlElement.getAttributeValue(this.myNameAttribute);
        if (StringUtil.isEmpty(this.myNameAttribute)) {
            return attributeValue == null || attributeValue.equals(this.f726name);
        }
        return attributeValue != null && attributeValue.equals(this.f726name);
    }

    public String toString() {
        return "OptionTagBinding[" + this.f726name + ", binding=" + this.myBinding + "]";
    }
}
