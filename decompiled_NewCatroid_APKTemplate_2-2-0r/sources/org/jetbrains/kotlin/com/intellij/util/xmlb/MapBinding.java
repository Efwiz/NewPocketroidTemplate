package org.jetbrains.kotlin.com.intellij.util.xmlb;

import com.reandroid.archive.PathTree;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.antlr.stringtemplate.language.ASTExpr;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.serialization.ClassUtil;
import org.jetbrains.kotlin.com.intellij.serialization.MutableAccessor;
import org.jetbrains.kotlin.com.intellij.util.ReflectionUtil;
import org.jetbrains.kotlin.com.intellij.util.XmlElement;
import org.jetbrains.kotlin.com.intellij.util.xmlb.annotations.MapAnnotation;
import org.jetbrains.kotlin.com.intellij.util.xmlb.annotations.XMap;

/* JADX INFO: loaded from: classes3.dex */
final class MapBinding implements MultiNodeBinding, NestedBinding {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Comparator<Object> KEY_COMPARATOR = new Comparator() { // from class: org.jetbrains.kotlin.com.intellij.util.xmlb.MapBinding$$ExternalSyntheticLambda0
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return MapBinding.lambda$static$0(obj, obj2);
        }
    };
    private final MutableAccessor accessor;
    private final XMap annotation;
    private Binding keyBinding;
    private Class<?> keyClass;
    private final Class<? extends Map> mapClass;
    private final MapAnnotation oldAnnotation;
    private Binding valueBinding;
    private Class<?> valueClass;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 5 || i == 6) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 5 || i == 6) ? 2 : 3];
        switch (i) {
            case 1:
            case 5:
            case 6:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/xmlb/MapBinding";
                break;
            case 2:
                objArr[0] = "originalType";
                break;
            case 3:
                objArr[0] = "serializer";
                break;
            case 4:
                objArr[0] = "o";
                break;
            case 7:
            case 8:
                objArr[0] = PathTree.NAME_elements;
                break;
            case 9:
            case 10:
            case 11:
            case 23:
            case 24:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 12:
            case 13:
                objArr[0] = "childNodes";
                break;
            case 14:
            case 16:
            case 19:
                objArr[0] = "entry";
                break;
            case 15:
            case 17:
            case 20:
                objArr[0] = "attributeName";
                break;
            case 18:
            case 21:
                objArr[0] = "valueClass";
                break;
            case 22:
                objArr[0] = "elementName";
                break;
            default:
                objArr[0] = "mapClass";
                break;
        }
        if (i == 1) {
            objArr[1] = "getAccessor";
        } else if (i == 5 || i == 6) {
            objArr[1] = "getEntryElementName";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/xmlb/MapBinding";
        }
        switch (i) {
            case 1:
            case 5:
            case 6:
                break;
            case 2:
            case 3:
                objArr[2] = ConfigConstants.CONFIG_INIT_SECTION;
                break;
            case 4:
                objArr[2] = "serialize";
                break;
            case 7:
                objArr[2] = "deserializeList";
                break;
            case 8:
                objArr[2] = "deserializeList2";
                break;
            case 9:
            case 10:
                objArr[2] = "deserializeUnsafe";
                break;
            case 11:
            case 12:
                objArr[2] = "deserialize";
                break;
            case 13:
                objArr[2] = "deserialize2";
                break;
            case 14:
            case 15:
                objArr[2] = "serializeKeyOrValue";
                break;
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
                objArr[2] = "deserializeKeyOrValue";
                break;
            case 22:
                objArr[2] = "isBoundToWithoutProperty";
                break;
            case 23:
            case 24:
                objArr[2] = "isBoundTo";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 5 && i != 6) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.xmlb.Binding
    public Object deserializeUnsafe(Object obj, XmlElement xmlElement) {
        if (xmlElement != null) {
            return null;
        }
        $$$reportNull$$$0(10);
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.xmlb.MultiNodeBinding
    public boolean isMulti() {
        return true;
    }

    static /* synthetic */ int lambda$static$0(Object obj, Object obj2) {
        if ((obj instanceof Comparable) && (obj2 instanceof Comparable)) {
            return ((Comparable) obj).compareTo((Comparable) obj2);
        }
        return 0;
    }

    MapBinding(MutableAccessor mutableAccessor, Class<? extends Map<?, ?>> cls) {
        if (cls == null) {
            $$$reportNull$$$0(0);
        }
        this.accessor = mutableAccessor;
        this.oldAnnotation = mutableAccessor == null ? null : (MapAnnotation) mutableAccessor.getAnnotation(MapAnnotation.class);
        this.annotation = mutableAccessor != null ? (XMap) mutableAccessor.getAnnotation(XMap.class) : null;
        this.mapClass = cls;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.xmlb.Binding
    public void init(Type type, Serializer serializer) {
        Type type2;
        if (type == null) {
            $$$reportNull$$$0(2);
        }
        if (serializer == null) {
            $$$reportNull$$$0(3);
        }
        ParameterizedType parameterizedType = (ParameterizedType) type;
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        this.keyClass = ClassUtil.typeToClass(actualTypeArguments[0]);
        if (actualTypeArguments.length == 1) {
            String typeName = parameterizedType.getRawType().getTypeName();
            if (typeName.equals("org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2IntMap") || typeName.equals("org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap")) {
                this.valueClass = Integer.class;
                type2 = Integer.class;
            } else {
                throw new UnsupportedOperationException("Value class is unknown for " + parameterizedType.getTypeName());
            }
        } else {
            type2 = actualTypeArguments[1];
            this.valueClass = ClassUtil.typeToClass(type2);
        }
        this.keyBinding = serializer.getBinding(this.keyClass, actualTypeArguments[0]);
        this.valueBinding = serializer.getBinding(this.valueClass, type2);
    }

    private boolean isSurroundWithTag() {
        if (this.annotation != null) {
            return false;
        }
        MapAnnotation mapAnnotation = this.oldAnnotation;
        return mapAnnotation == null || mapAnnotation.surroundWithTag();
    }

    String getEntryElementName() {
        XMap xMap = this.annotation;
        if (xMap != null) {
            String strEntryTagName = xMap.entryTagName();
            if (strEntryTagName == null) {
                $$$reportNull$$$0(5);
            }
            return strEntryTagName;
        }
        MapAnnotation mapAnnotation = this.oldAnnotation;
        String strEntryTagName2 = mapAnnotation == null ? "entry" : mapAnnotation.entryTagName();
        if (strEntryTagName2 == null) {
            $$$reportNull$$$0(6);
        }
        return strEntryTagName2;
    }

    private String getKeyAttributeName() {
        XMap xMap = this.annotation;
        if (xMap != null) {
            return xMap.keyAttributeName();
        }
        MapAnnotation mapAnnotation = this.oldAnnotation;
        return mapAnnotation == null ? ASTExpr.DEFAULT_MAP_KEY_NAME : mapAnnotation.keyAttributeName();
    }

    private String getValueAttributeName() {
        XMap xMap = this.annotation;
        if (xMap != null) {
            return xMap.valueAttributeName();
        }
        MapAnnotation mapAnnotation = this.oldAnnotation;
        return mapAnnotation == null ? "value" : mapAnnotation.valueAttributeName();
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.xmlb.MultiNodeBinding
    public Object deserializeList2(Object obj, List<XmlElement> list) {
        if (list == null) {
            $$$reportNull$$$0(8);
        }
        if (isSurroundWithTag()) {
            list = list.get(0).children;
        }
        return deserialize2(obj, list);
    }

    private Map<?, ?> deserialize2(Object obj, List<XmlElement> list) {
        if (list == null) {
            $$$reportNull$$$0(13);
        }
        Map<?, ?> map = null;
        Map<?, ?> map2 = this.accessor == null ? null : (Map) obj;
        if (map2 == null) {
            map = map2;
        } else if (ClassUtil.isMutableMap(map2)) {
            map2.clear();
            map = map2;
        }
        for (XmlElement xmlElement : list) {
            if (!xmlElement.name.equals(getEntryElementName())) {
                LOG.warn("unexpected entry for serialized Map will be skipped: " + xmlElement);
            } else {
                if (map == null) {
                    Class<? extends Map> cls = this.mapClass;
                    if (cls == Map.class) {
                        map = new HashMap<>();
                    } else {
                        try {
                            map = (Map) ReflectionUtil.newInstance(cls);
                        } catch (Exception e) {
                            LOG.warn(e);
                            map = new HashMap<>();
                        }
                    }
                }
                map.put(deserializeKeyOrValue(xmlElement, getKeyAttributeName(), obj, this.keyBinding, this.keyClass), deserializeKeyOrValue(xmlElement, getValueAttributeName(), obj, this.valueBinding, this.valueClass));
            }
        }
        return map;
    }

    private Object deserializeKeyOrValue(XmlElement xmlElement, String str, Object obj, Binding binding, Class<?> cls) {
        if (xmlElement == null) {
            $$$reportNull$$$0(19);
        }
        if (str == null) {
            $$$reportNull$$$0(20);
        }
        if (cls == null) {
            $$$reportNull$$$0(21);
        }
        String str2 = xmlElement.attributes.get(str);
        if (str2 != null) {
            return XmlSerializerImpl.convert(str2, cls);
        }
        if (!isSurroundKey()) {
            for (XmlElement xmlElement2 : xmlElement.children) {
                if (binding.isBoundTo(xmlElement2)) {
                    return binding.deserializeUnsafe(obj, xmlElement2);
                }
            }
            return null;
        }
        XmlElement child = xmlElement.getChild(str);
        List<XmlElement> listEmptyList = child == null ? Collections.emptyList() : child.children;
        if (listEmptyList.isEmpty()) {
            return null;
        }
        return Binding.deserializeList2(binding, null, listEmptyList);
    }

    private boolean isSurroundKey() {
        if (this.annotation != null) {
            return false;
        }
        MapAnnotation mapAnnotation = this.oldAnnotation;
        return mapAnnotation == null || mapAnnotation.surroundKeyWithTag();
    }

    boolean isBoundToWithoutProperty(String str) {
        if (str == null) {
            $$$reportNull$$$0(22);
        }
        XMap xMap = this.annotation;
        if (xMap != null) {
            return str.equals(xMap.entryTagName());
        }
        MapAnnotation mapAnnotation = this.oldAnnotation;
        if (mapAnnotation != null && !mapAnnotation.surroundWithTag()) {
            return str.equals(this.oldAnnotation.entryTagName());
        }
        return str.equals("map");
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.xmlb.Binding
    public boolean isBoundTo(XmlElement xmlElement) {
        if (xmlElement == null) {
            $$$reportNull$$$0(24);
        }
        MapAnnotation mapAnnotation = this.oldAnnotation;
        if (mapAnnotation != null && !mapAnnotation.surroundWithTag()) {
            return this.oldAnnotation.entryTagName().equals(xmlElement.name);
        }
        XMap xMap = this.annotation;
        if (xMap != null) {
            return xMap.propertyElementName().equals(xmlElement.name);
        }
        return xmlElement.name.equals("map");
    }
}
