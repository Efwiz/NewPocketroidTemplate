package org.jetbrains.kotlin.com.intellij.util.xmlb;

import com.reandroid.archive.PathTree;
import java.util.List;
import org.jetbrains.kotlin.com.intellij.serialization.MutableAccessor;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.com.intellij.util.XmlElement;

/* JADX INFO: loaded from: classes3.dex */
final class ArrayBinding extends AbstractCollectionBinding {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 4 || i == 6) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 4 || i == 6) ? 2 : 3];
        switch (i) {
            case 1:
            case 3:
                objArr[0] = PathTree.NAME_elements;
                break;
            case 2:
            case 4:
            case 6:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/xmlb/ArrayBinding";
                break;
            case 5:
                objArr[0] = "o";
                break;
            default:
                objArr[0] = "valueClass";
                break;
        }
        if (i == 2) {
            objArr[1] = "doDeserializeList";
        } else if (i == 4) {
            objArr[1] = "doDeserializeList2";
        } else if (i != 6) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/xmlb/ArrayBinding";
        } else {
            objArr[1] = "getIterable";
        }
        switch (i) {
            case 1:
                objArr[2] = "doDeserializeList";
                break;
            case 2:
            case 4:
            case 6:
                break;
            case 3:
                objArr[2] = "doDeserializeList2";
                break;
            case 5:
                objArr[2] = "getIterable";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 4 && i != 6) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    ArrayBinding(Class<?> cls, MutableAccessor mutableAccessor) {
        super(cls.getComponentType(), mutableAccessor);
        if (cls == null) {
            $$$reportNull$$$0(0);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.xmlb.AbstractCollectionBinding
    protected String getCollectionTagName(Object obj) {
        return "array";
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.xmlb.AbstractCollectionBinding
    protected Object doDeserializeList2(Object obj, List<XmlElement> list) {
        if (list == null) {
            $$$reportNull$$$0(3);
        }
        int size = list.size();
        Object[] objArrNewArray = ArrayUtil.newArray(this.itemType, size);
        for (int i = 0; i < size; i++) {
            objArrNewArray[i] = deserializeItem(list.get(i), obj);
        }
        if (objArrNewArray == null) {
            $$$reportNull$$$0(4);
        }
        return objArrNewArray;
    }
}
