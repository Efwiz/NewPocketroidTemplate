package org.jetbrains.kotlin.com.intellij.util.xmlb;

import com.reandroid.archive.PathTree;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.bytebuddy.implementation.Implementation;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.serialization.ClassUtil;
import org.jetbrains.kotlin.com.intellij.serialization.MutableAccessor;
import org.jetbrains.kotlin.com.intellij.util.XmlElement;
import org.jetbrains.kotlin.com.intellij.util.xmlb.annotations.Tag;

/* JADX INFO: loaded from: classes3.dex */
final class TagBinding extends BasePrimitiveBinding implements MultiNodeBinding {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final String myTextIfEmpty;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 7 || i == 10 || i == 13 || i == 16) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 7 || i == 10 || i == 13 || i == 16) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "tagAnnotation";
                break;
            case 2:
            default:
                objArr[0] = Implementation.Context.Default.ACCESSOR_METHOD_SUFFIX;
                break;
            case 3:
                objArr[0] = "suggestedName";
                break;
            case 4:
                objArr[0] = "o";
                break;
            case 5:
            case 8:
            case 11:
            case 14:
            case 17:
            case 19:
                objArr[0] = "context";
                break;
            case 6:
            case 9:
                objArr[0] = PathTree.NAME_elements;
                break;
            case 7:
            case 10:
            case 13:
            case 16:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/xmlb/TagBinding";
                break;
            case 12:
            case 15:
            case 21:
            case 22:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 18:
            case 20:
                objArr[0] = "children";
                break;
        }
        if (i == 7) {
            objArr[1] = "deserializeList";
        } else if (i == 10) {
            objArr[1] = "deserializeList2";
        } else if (i == 13 || i == 16) {
            objArr[1] = "deserialize";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/xmlb/TagBinding";
        }
        switch (i) {
            case 4:
                objArr[2] = "serialize";
                break;
            case 5:
            case 6:
                objArr[2] = "deserializeList";
                break;
            case 7:
            case 10:
            case 13:
            case 16:
                break;
            case 8:
            case 9:
                objArr[2] = "deserializeList2";
                break;
            case 11:
            case 12:
            case 14:
            case 15:
            case 17:
            case 18:
                objArr[2] = "deserialize";
                break;
            case 19:
            case 20:
                objArr[2] = "deserialize2";
                break;
            case 21:
            case 22:
                objArr[2] = "isBoundTo";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 7 && i != 10 && i != 13 && i != 16) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    TagBinding(MutableAccessor mutableAccessor, Tag tag) {
        super(mutableAccessor, tag.value(), null);
        if (mutableAccessor == null) {
            $$$reportNull$$$0(0);
        }
        if (tag == null) {
            $$$reportNull$$$0(1);
        }
        this.myTextIfEmpty = tag.textIfEmpty();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    TagBinding(MutableAccessor mutableAccessor, String str) {
        super(mutableAccessor, str, null);
        if (mutableAccessor == null) {
            $$$reportNull$$$0(2);
        }
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        this.myTextIfEmpty = "";
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.xmlb.MultiNodeBinding
    public Object deserializeList2(Object obj, List<XmlElement> list) {
        List<XmlElement> list2;
        if (obj == null) {
            $$$reportNull$$$0(8);
        }
        if (list == null) {
            $$$reportNull$$$0(9);
        }
        if (list.size() == 1) {
            list2 = list.get(0).children;
        } else {
            String str = list.get(0).name;
            ArrayList arrayList = new ArrayList();
            Iterator<XmlElement> it = list.iterator();
            while (it.hasNext()) {
                arrayList.addAll(it.next().children);
            }
            list2 = arrayList;
        }
        deserialize2(obj, list2);
        if (obj == null) {
            $$$reportNull$$$0(10);
        }
        return obj;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.xmlb.MultiNodeBinding
    public boolean isMulti() {
        return (this.myBinding instanceof MultiNodeBinding) && ((MultiNodeBinding) this.myBinding).isMulti();
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.xmlb.BasePrimitiveBinding
    public Object deserialize(Object obj, XmlElement xmlElement) {
        if (obj == null) {
            $$$reportNull$$$0(14);
        }
        if (xmlElement == null) {
            $$$reportNull$$$0(15);
        }
        if (this.myBinding == null) {
            String str = xmlElement.content;
            if (str == null) {
                str = this.myTextIfEmpty;
            }
            XmlSerializerImpl.doSet(obj, str, this.myAccessor, ClassUtil.typeToClass(this.myAccessor.getGenericType()));
        } else {
            deserialize2(obj, xmlElement.children);
        }
        if (obj == null) {
            $$$reportNull$$$0(16);
        }
        return obj;
    }

    private void deserialize2(Object obj, List<XmlElement> list) {
        if (obj == null) {
            $$$reportNull$$$0(19);
        }
        if (list == null) {
            $$$reportNull$$$0(20);
        }
        if ((this.myBinding instanceof BeanBinding) && !this.myAccessor.isWritable()) {
            ((BeanBinding) this.myBinding).deserializeInto(obj, list.get(0));
        } else if (((this.myBinding instanceof CollectionBinding) || (this.myBinding instanceof MapBinding)) && !this.myAccessor.isWritable()) {
            Binding.deserializeList2(this.myBinding, this.myAccessor.read(obj), list);
        } else {
            this.myAccessor.set(obj, Binding.deserializeList2(this.myBinding, this.myAccessor.read(obj), list));
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.xmlb.Binding
    public boolean isBoundTo(XmlElement xmlElement) {
        if (xmlElement == null) {
            $$$reportNull$$$0(22);
        }
        return xmlElement.name.equals(this.f726name);
    }
}
