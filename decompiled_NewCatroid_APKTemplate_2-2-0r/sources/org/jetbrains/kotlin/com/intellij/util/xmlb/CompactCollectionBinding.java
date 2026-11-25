package org.jetbrains.kotlin.com.intellij.util.xmlb;

import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;
import java.util.List;
import net.bytebuddy.implementation.Implementation;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.serialization.MutableAccessor;
import org.jetbrains.kotlin.com.intellij.util.XmlElement;

/* JADX INFO: loaded from: classes3.dex */
final class CompactCollectionBinding extends NotNullDeserializeBinding implements NestedBinding {
    private final MutableAccessor accessor;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private final String f727name;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 6 || i == 8 || i == 3 || i == 4) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 6 || i == 8 || i == 3 || i == 4) ? 2 : 3];
        switch (i) {
            case 1:
            case 3:
            case 4:
            case 6:
            case 8:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/xmlb/CompactCollectionBinding";
                break;
            case 2:
                objArr[0] = "o";
                break;
            case 5:
            case 7:
            case 9:
            case 10:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            default:
                objArr[0] = Implementation.Context.Default.ACCESSOR_METHOD_SUFFIX;
                break;
        }
        if (i == 1) {
            objArr[1] = "getAccessor";
        } else if (i == 6 || i == 8) {
            objArr[1] = "deserialize";
        } else if (i == 3 || i == 4) {
            objArr[1] = "serialize";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/xmlb/CompactCollectionBinding";
        }
        switch (i) {
            case 1:
            case 3:
            case 4:
            case 6:
            case 8:
                break;
            case 2:
                objArr[2] = "serialize";
                break;
            case 5:
            case 7:
                objArr[2] = "deserialize";
                break;
            case 9:
            case 10:
                objArr[2] = "isBoundTo";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 6 && i != 8 && i != 3 && i != 4) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    CompactCollectionBinding(MutableAccessor mutableAccessor) {
        if (mutableAccessor == null) {
            $$$reportNull$$$0(0);
        }
        this.accessor = mutableAccessor;
        this.f727name = mutableAccessor.getName();
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.xmlb.NotNullDeserializeBinding
    public Object deserialize(Object obj, XmlElement xmlElement) {
        String attributeValue;
        String str;
        if (xmlElement == null) {
            $$$reportNull$$$0(7);
        }
        List list = (List) obj;
        list.clear();
        if (xmlElement.name.equals(this.f727name)) {
            for (XmlElement xmlElement2 : xmlElement.children) {
                if (xmlElement2.name.equals("item") && (str = xmlElement2.attributes.get("value")) != null) {
                    list.add(str);
                }
            }
        } else {
            XmlElement child = xmlElement.getChild("value");
            if (child != null) {
                child = child.getChild(SchemaSymbols.ATTVAL_LIST);
            }
            if (child != null) {
                for (XmlElement xmlElement3 : child.children) {
                    if (xmlElement3.name.equals("item") && (attributeValue = xmlElement3.getAttributeValue("itemvalue")) != null) {
                        list.add(attributeValue);
                    }
                }
            }
        }
        if (list == null) {
            $$$reportNull$$$0(8);
        }
        return list;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.xmlb.Binding
    public boolean isBoundTo(XmlElement xmlElement) {
        if (xmlElement == null) {
            $$$reportNull$$$0(10);
        }
        String str = xmlElement.name;
        if (isNameEqual(str)) {
            return true;
        }
        if (str.equals("option")) {
            return isNameEqual(xmlElement.getAttributeValue("name"));
        }
        return false;
    }

    private boolean isNameEqual(String str) {
        if (str == null) {
            return false;
        }
        if (str.equals(this.f727name)) {
            return true;
        }
        if (str.length() != this.f727name.length() + 2 || !str.startsWith(LanguageCodeUtil.MY) || !Character.isUpperCase(str.charAt(2))) {
            return false;
        }
        String str2 = this.f727name;
        return str.regionMatches(true, 2, str2, 0, str2.length());
    }
}
