package org.jetbrains.kotlin.com.intellij.util.xmlb;

import com.reandroid.archive.PathTree;
import java.util.List;
import net.bytebuddy.implementation.Implementation;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.serialization.MutableAccessor;
import org.jetbrains.kotlin.com.intellij.util.XmlElement;
import org.jetbrains.kotlin.com.intellij.util.xmlb.annotations.Tag;

/* JADX INFO: loaded from: classes3.dex */
final class JDOMElementBinding extends NotNullDeserializeBinding implements MultiNodeBinding, NestedBinding {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final MutableAccessor myAccessor;
    private final String myTagName;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 5 || i == 10) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 5 || i == 10) ? 2 : 3];
        switch (i) {
            case 1:
            case 5:
            case 10:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/xmlb/JDOMElementBinding";
                break;
            case 2:
                objArr[0] = "o";
                break;
            case 3:
            case 6:
            case 8:
            case 11:
                objArr[0] = "context";
                break;
            case 4:
            case 7:
                objArr[0] = PathTree.NAME_elements;
                break;
            case 9:
            case 12:
            case 13:
            case 14:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            default:
                objArr[0] = Implementation.Context.Default.ACCESSOR_METHOD_SUFFIX;
                break;
        }
        if (i == 1) {
            objArr[1] = "getAccessor";
        } else if (i == 5) {
            objArr[1] = "deserializeList";
        } else if (i != 10) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/xmlb/JDOMElementBinding";
        } else {
            objArr[1] = "deserialize";
        }
        switch (i) {
            case 1:
            case 5:
            case 10:
                break;
            case 2:
                objArr[2] = "serialize";
                break;
            case 3:
            case 4:
                objArr[2] = "deserializeList";
                break;
            case 6:
            case 7:
                objArr[2] = "deserializeList2";
                break;
            case 8:
            case 9:
            case 11:
            case 12:
                objArr[2] = "deserialize";
                break;
            case 13:
            case 14:
                objArr[2] = "isBoundTo";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 5 && i != 10) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.xmlb.MultiNodeBinding
    public boolean isMulti() {
        return true;
    }

    JDOMElementBinding(MutableAccessor mutableAccessor) {
        if (mutableAccessor == null) {
            $$$reportNull$$$0(0);
        }
        this.myAccessor = mutableAccessor;
        Tag tag = (Tag) mutableAccessor.getAnnotation(Tag.class);
        String strValue = tag == null ? null : tag.value();
        this.myTagName = (strValue == null || strValue.isEmpty()) ? mutableAccessor.getName() : strValue;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.xmlb.MultiNodeBinding
    public Object deserializeList2(Object obj, List<XmlElement> list) {
        if (obj == null) {
            $$$reportNull$$$0(6);
        }
        if (list == null) {
            $$$reportNull$$$0(7);
        }
        throw new UnsupportedOperationException("XmlElement is not supported by JDOMElementBinding");
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.xmlb.NotNullDeserializeBinding
    public Object deserialize(Object obj, XmlElement xmlElement) {
        if (obj == null) {
            $$$reportNull$$$0(11);
        }
        if (xmlElement == null) {
            $$$reportNull$$$0(12);
        }
        throw new UnsupportedOperationException("XmlElement is not supported by JDOMElementBinding");
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.xmlb.Binding
    public boolean isBoundTo(XmlElement xmlElement) {
        if (xmlElement == null) {
            $$$reportNull$$$0(14);
        }
        return xmlElement.name.equals(this.myTagName);
    }
}
