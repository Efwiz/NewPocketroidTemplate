package org.jetbrains.kotlin.com.intellij.util.xmlb;

import com.reandroid.archive.PathTree;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.serialization.ClassUtil;
import org.jetbrains.kotlin.com.intellij.serialization.MutableAccessor;
import org.jetbrains.kotlin.com.intellij.util.XmlElement;

/* JADX INFO: loaded from: classes3.dex */
final class CollectionBinding extends AbstractCollectionBinding {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 4 || i == 6 || i == 7 || i == 8) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 4 || i == 6 || i == 7 || i == 8) ? 2 : 3];
        switch (i) {
            case 1:
            case 3:
                objArr[0] = PathTree.NAME_elements;
                break;
            case 2:
            case 4:
            case 6:
            case 7:
            case 8:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/xmlb/CollectionBinding";
                break;
            case 5:
                objArr[0] = "o";
                break;
            default:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
        }
        if (i == 2) {
            objArr[1] = "doDeserializeList";
        } else if (i == 4) {
            objArr[1] = "doDeserializeList2";
        } else if (i == 6 || i == 7 || i == 8) {
            objArr[1] = "getIterable";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/xmlb/CollectionBinding";
        }
        switch (i) {
            case 1:
                objArr[2] = "doDeserializeList";
                break;
            case 2:
            case 4:
            case 6:
            case 7:
            case 8:
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
        if (i != 2 && i != 4 && i != 6 && i != 7 && i != 8) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    CollectionBinding(ParameterizedType parameterizedType, MutableAccessor mutableAccessor) {
        super(ClassUtil.typeToClass(parameterizedType.getActualTypeArguments()[0]), mutableAccessor);
        if (parameterizedType == null) {
            $$$reportNull$$$0(0);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.xmlb.AbstractCollectionBinding
    protected Object doDeserializeList2(Object obj, List<XmlElement> list) {
        Collection hashSet;
        if (list == null) {
            $$$reportNull$$$0(3);
        }
        if (obj != null && ClassUtil.isMutableCollection(obj)) {
            hashSet = (Collection) obj;
            hashSet.clear();
        } else {
            hashSet = obj instanceof Set ? new HashSet() : new ArrayList();
        }
        Iterator<XmlElement> it = list.iterator();
        while (it.hasNext()) {
            hashSet.add(deserializeItem(it.next(), obj));
        }
        if (hashSet == null) {
            $$$reportNull$$$0(4);
        }
        return hashSet;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.xmlb.AbstractCollectionBinding
    protected String getCollectionTagName(Object obj) {
        if (obj instanceof Set) {
            return FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX;
        }
        if (obj instanceof List) {
            return SchemaSymbols.ATTVAL_LIST;
        }
        return "collection";
    }
}
