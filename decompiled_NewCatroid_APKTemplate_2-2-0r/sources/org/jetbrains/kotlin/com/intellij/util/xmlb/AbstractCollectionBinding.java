package org.jetbrains.kotlin.com.intellij.util.xmlb;

import com.reandroid.archive.PathTree;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.serialization.ClassUtil;
import org.jetbrains.kotlin.com.intellij.serialization.MutableAccessor;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.com.intellij.util.XmlElement;
import org.jetbrains.kotlin.com.intellij.util.xmlb.annotations.AbstractCollection;
import org.jetbrains.kotlin.com.intellij.util.xmlb.annotations.XCollection;

/* JADX INFO: loaded from: classes3.dex */
abstract class AbstractCollectionBinding extends NotNullDeserializeBinding implements MultiNodeBinding, NestedBinding {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final AbstractCollection annotation;
    private List<Binding> itemBindings;
    protected final Class<?> itemType;
    private final MutableAccessor myAccessor;
    protected final XCollection newAnnotation;
    private Serializer serializer;

    /* JADX WARN: Code duplicated, block: B:17:0x0034  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 1 && i != 7 && i != 4 && i != 5 && i != 11 && i != 12 && i != 14 && i != 15 && i != 17 && i != 18 && i != 20 && i != 21) {
            switch (i) {
                case 25:
                case 26:
                case 27:
                case 28:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 1 && i != 7 && i != 4 && i != 5 && i != 11 && i != 12 && i != 14 && i != 15 && i != 17 && i != 18 && i != 20 && i != 21) {
            switch (i) {
                case 25:
                case 26:
                case 27:
                case 28:
                    i2 = 2;
                    break;
                default:
                    i2 = 3;
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 4:
            case 5:
            case 7:
            case 11:
            case 12:
            case 14:
            case 15:
            case 17:
            case 18:
            case 20:
            case 21:
            case 25:
            case 26:
            case 27:
            case 28:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/xmlb/AbstractCollectionBinding";
                break;
            case 2:
                objArr[0] = "originalType";
                break;
            case 3:
                objArr[0] = "serializer";
                break;
            case 6:
                objArr[0] = "aClass";
                break;
            case 8:
            case 9:
            case 13:
            case 16:
            case 29:
            case 30:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 10:
                objArr[0] = "object";
                break;
            case 19:
            case 22:
                objArr[0] = PathTree.NAME_elements;
                break;
            case 23:
            case 24:
                objArr[0] = "node";
                break;
            default:
                objArr[0] = "elementType";
                break;
        }
        if (i == 1) {
            objArr[1] = "getAccessor";
        } else if (i == 7) {
            objArr[1] = "getItemBindings";
        } else if (i != 4 && i != 5) {
            if (i != 11 && i != 12) {
                if (i != 14 && i != 15 && i != 17 && i != 18) {
                    if (i != 20 && i != 21) {
                        switch (i) {
                            case 25:
                            case 26:
                                objArr[1] = "getElementName";
                                break;
                            case 27:
                            case 28:
                                objArr[1] = "getValueAttributeName";
                                break;
                            default:
                                objArr[1] = "org/jetbrains/kotlin/com/intellij/util/xmlb/AbstractCollectionBinding";
                                break;
                        }
                    } else {
                        objArr[1] = "deserializeList";
                    }
                } else {
                    objArr[1] = "deserialize";
                }
            } else {
                objArr[1] = "serialize";
            }
        } else {
            objArr[1] = "getElementTypes";
        }
        switch (i) {
            case 1:
            case 4:
            case 5:
            case 7:
            case 11:
            case 12:
            case 14:
            case 15:
            case 17:
            case 18:
            case 20:
            case 21:
            case 25:
            case 26:
            case 27:
            case 28:
                break;
            case 2:
            case 3:
                objArr[2] = ConfigConstants.CONFIG_INIT_SECTION;
                break;
            case 6:
                objArr[2] = "getItemBinding";
                break;
            case 8:
            case 9:
                objArr[2] = "getElementBinding";
                break;
            case 10:
                objArr[2] = "serialize";
                break;
            case 13:
            case 16:
                objArr[2] = "deserialize";
                break;
            case 19:
                objArr[2] = "deserializeList";
                break;
            case 22:
                objArr[2] = "deserializeList2";
                break;
            case 23:
            case 24:
                objArr[2] = "deserializeItem";
                break;
            case 29:
            case 30:
                objArr[2] = "isBoundTo";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 7 && i != 4 && i != 5 && i != 11 && i != 12 && i != 14 && i != 15 && i != 17 && i != 18 && i != 20 && i != 21) {
            switch (i) {
                case 25:
                case 26:
                case 27:
                case 28:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    protected abstract Object doDeserializeList2(Object obj, List<XmlElement> list);

    protected abstract String getCollectionTagName(Object obj);

    @Override // org.jetbrains.kotlin.com.intellij.util.xmlb.MultiNodeBinding
    public final boolean isMulti() {
        return true;
    }

    AbstractCollectionBinding(Class<?> cls, MutableAccessor mutableAccessor) {
        if (cls == null) {
            $$$reportNull$$$0(0);
        }
        this.myAccessor = mutableAccessor;
        this.itemType = cls;
        AbstractCollection abstractCollection = null;
        XCollection xCollection = mutableAccessor == null ? null : (XCollection) mutableAccessor.getAnnotation(XCollection.class);
        this.newAnnotation = xCollection;
        if (xCollection == null && mutableAccessor != null) {
            abstractCollection = (AbstractCollection) mutableAccessor.getAnnotation(AbstractCollection.class);
        }
        this.annotation = abstractCollection;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.xmlb.Binding
    public final void init(Type type, Serializer serializer) {
        if (type == null) {
            $$$reportNull$$$0(2);
        }
        if (serializer == null) {
            $$$reportNull$$$0(3);
        }
        this.serializer = serializer;
    }

    private boolean isSurroundWithTag() {
        AbstractCollection abstractCollection;
        return this.newAnnotation == null && ((abstractCollection = this.annotation) == null || abstractCollection.surroundWithTag());
    }

    private Class<?>[] getElementTypes() {
        XCollection xCollection = this.newAnnotation;
        if (xCollection != null) {
            Class<?>[] clsArrElementTypes = xCollection.elementTypes();
            if (clsArrElementTypes == null) {
                $$$reportNull$$$0(4);
            }
            return clsArrElementTypes;
        }
        AbstractCollection abstractCollection = this.annotation;
        Class<?>[] clsArrElementTypes2 = abstractCollection == null ? ArrayUtil.EMPTY_CLASS_ARRAY : abstractCollection.elementTypes();
        if (clsArrElementTypes2 == null) {
            $$$reportNull$$$0(5);
        }
        return clsArrElementTypes2;
    }

    private Binding getItemBinding(Class<?> cls) {
        if (cls == null) {
            $$$reportNull$$$0(6);
        }
        if (ClassUtil.isPrimitive(cls)) {
            return null;
        }
        return this.serializer.getRootBinding(cls, cls);
    }

    private synchronized List<Binding> getItemBindings() {
        List<Binding> list;
        if (this.itemBindings == null) {
            Binding itemBinding = getItemBinding(this.itemType);
            Class<?>[] elementTypes = getElementTypes();
            if (elementTypes.length == 0) {
                this.itemBindings = itemBinding == null ? Collections.emptyList() : Collections.singletonList(itemBinding);
            } else {
                SmartList smartList = new SmartList();
                this.itemBindings = smartList;
                if (itemBinding != null) {
                    smartList.add(itemBinding);
                }
                for (Class<?> cls : elementTypes) {
                    Binding itemBinding2 = getItemBinding(cls);
                    if (itemBinding2 != null && !this.itemBindings.contains(itemBinding2)) {
                        this.itemBindings.add(itemBinding2);
                    }
                }
                if (this.itemBindings.isEmpty()) {
                    this.itemBindings = Collections.emptyList();
                }
            }
        }
        list = this.itemBindings;
        if (list == null) {
            $$$reportNull$$$0(7);
        }
        return list;
    }

    private Binding getElementBinding(XmlElement xmlElement) {
        if (xmlElement == null) {
            $$$reportNull$$$0(9);
        }
        for (Binding binding : getItemBindings()) {
            if (binding.isBoundTo(xmlElement)) {
                return binding;
            }
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.xmlb.NotNullDeserializeBinding
    public final Object deserialize(Object obj, XmlElement xmlElement) {
        if (xmlElement == null) {
            $$$reportNull$$$0(16);
        }
        if (isSurroundWithTag()) {
            Object objDoDeserializeList2 = doDeserializeList2(obj, xmlElement.children);
            if (objDoDeserializeList2 == null) {
                $$$reportNull$$$0(17);
            }
            return objDoDeserializeList2;
        }
        Object objDoDeserializeList3 = doDeserializeList2(obj, Collections.singletonList(xmlElement));
        if (objDoDeserializeList3 == null) {
            $$$reportNull$$$0(18);
        }
        return objDoDeserializeList3;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.xmlb.MultiNodeBinding
    public Object deserializeList2(Object obj, List<XmlElement> list) {
        if (list == null) {
            $$$reportNull$$$0(22);
        }
        if (!isSurroundWithTag()) {
            return doDeserializeList2(obj, list);
        }
        XmlElement xmlElement = list.get(0);
        if (obj == null && xmlElement.name.equals(FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX)) {
            obj = new HashSet();
        }
        return doDeserializeList2(obj, xmlElement.children);
    }

    protected final Object deserializeItem(XmlElement xmlElement, Object obj) {
        String attributeValue;
        if (xmlElement == null) {
            $$$reportNull$$$0(24);
        }
        Binding elementBinding = getElementBinding(xmlElement);
        if (elementBinding == null) {
            String valueAttributeName = getValueAttributeName();
            if (valueAttributeName.isEmpty()) {
                attributeValue = xmlElement.content;
            } else {
                attributeValue = xmlElement.getAttributeValue(valueAttributeName);
            }
            return XmlSerializerImpl.convert(attributeValue, this.itemType);
        }
        return elementBinding.deserializeUnsafe(obj, xmlElement);
    }

    private String getElementName() {
        XCollection xCollection = this.newAnnotation;
        if (xCollection != null) {
            String strElementName = xCollection.elementName();
            if (strElementName == null) {
                $$$reportNull$$$0(25);
            }
            return strElementName;
        }
        AbstractCollection abstractCollection = this.annotation;
        String strElementTag = abstractCollection == null ? "option" : abstractCollection.elementTag();
        if (strElementTag == null) {
            $$$reportNull$$$0(26);
        }
        return strElementTag;
    }

    private String getValueAttributeName() {
        XCollection xCollection = this.newAnnotation;
        if (xCollection != null) {
            String strValueAttributeName = xCollection.valueAttributeName();
            if (strValueAttributeName == null) {
                $$$reportNull$$$0(27);
            }
            return strValueAttributeName;
        }
        AbstractCollection abstractCollection = this.annotation;
        String strElementValueAttribute = abstractCollection == null ? "value" : abstractCollection.elementValueAttribute();
        if (strElementValueAttribute == null) {
            $$$reportNull$$$0(28);
        }
        return strElementValueAttribute;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.xmlb.Binding
    public final boolean isBoundTo(XmlElement xmlElement) {
        if (xmlElement == null) {
            $$$reportNull$$$0(30);
        }
        if (isSurroundWithTag()) {
            return xmlElement.name.equals(getCollectionTagName(null));
        }
        if (getItemBindings().isEmpty()) {
            return xmlElement.name.equals(getElementName());
        }
        Iterator<Binding> it = getItemBindings().iterator();
        while (it.hasNext()) {
            if (it.next().isBoundTo(xmlElement)) {
                return true;
            }
        }
        return false;
    }
}
