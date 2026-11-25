package org.jetbrains.kotlin.com.intellij.util.xmlb;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Function;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.JDOMExternalizable;
import org.jetbrains.kotlin.com.intellij.serialization.MutableAccessor;
import org.jetbrains.kotlin.com.intellij.serialization.PropertyCollector;
import org.jetbrains.kotlin.com.intellij.util.ReflectionUtil;
import org.jetbrains.kotlin.com.intellij.util.ThreeState;
import org.jetbrains.kotlin.com.intellij.util.XmlElement;
import org.jetbrains.kotlin.com.intellij.util.xmlb.annotations.AbstractCollection;
import org.jetbrains.kotlin.com.intellij.util.xmlb.annotations.Attribute;
import org.jetbrains.kotlin.com.intellij.util.xmlb.annotations.CollectionBean;
import org.jetbrains.kotlin.com.intellij.util.xmlb.annotations.MapAnnotation;
import org.jetbrains.kotlin.com.intellij.util.xmlb.annotations.OptionTag;
import org.jetbrains.kotlin.com.intellij.util.xmlb.annotations.Property;
import org.jetbrains.kotlin.com.intellij.util.xmlb.annotations.Tag;
import org.jetbrains.kotlin.com.intellij.util.xmlb.annotations.Text;
import org.jetbrains.kotlin.com.intellij.util.xmlb.annotations.Transient;
import org.jetbrains.kotlin.com.intellij.util.xmlb.annotations.XCollection;
import org.jetbrains.kotlin.com.intellij.util.xmlb.annotations.XMap;

/* JADX INFO: loaded from: classes3.dex */
public class BeanBinding extends NotNullDeserializeBinding {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final XmlSerializerPropertyCollector PROPERTY_COLLECTOR = new XmlSerializerPropertyCollector(new MyPropertyCollectorConfiguration());
    protected NestedBinding[] bindings;
    ThreeState compareByFields;
    protected final Class<?> myBeanClass;
    private final String myTagName;

    /* JADX WARN: Code duplicated, block: B:11:0x0020  */
    /* JADX WARN: Code duplicated, block: B:55:0x00be  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 9 && i != 17 && i != 33 && i != 38 && i != 11 && i != 12) {
            switch (i) {
                case 28:
                case 29:
                case 30:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 9 && i != 17 && i != 33 && i != 38 && i != 11 && i != 12) {
            switch (i) {
                case 28:
                case 29:
                case 30:
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
                objArr[0] = "originalType";
                break;
            case 2:
            case 36:
                objArr[0] = "serializer";
                break;
            case 3:
            case 5:
            case 7:
                objArr[0] = "o";
                break;
            case 4:
                objArr[0] = "object";
                break;
            case 6:
                objArr[0] = "binding";
                break;
            case 8:
            case 10:
            case 20:
            case 22:
            case 24:
            case 25:
            case 26:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 9:
            case 11:
            case 12:
            case 17:
            case 28:
            case 29:
            case 30:
            case 33:
            case 38:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/xmlb/BeanBinding";
                break;
            case 13:
                objArr[0] = "currentValue";
                break;
            case 14:
                objArr[0] = "defaultValue";
                break;
            case 15:
                objArr[0] = "filter";
                break;
            case 16:
                objArr[0] = "accessorNameTracker";
                break;
            case 18:
                objArr[0] = "weights";
                break;
            case 19:
            case 21:
            case 23:
                objArr[0] = "result";
                break;
            case 27:
            case 31:
            case 32:
            case 34:
                objArr[0] = "aClass";
                break;
            case 35:
                objArr[0] = Implementation.Context.Default.ACCESSOR_METHOD_SUFFIX;
                break;
            case 37:
                objArr[0] = "propertyStyle";
                break;
            default:
                objArr[0] = "beanClass";
                break;
        }
        if (i == 9) {
            objArr[1] = "deserialize";
        } else if (i == 17) {
            objArr[1] = "computeBindingWeights";
        } else if (i == 33) {
            objArr[1] = "getAccessors";
        } else if (i == 38) {
            objArr[1] = "createBinding";
        } else if (i == 11) {
            objArr[1] = "deserialize";
        } else if (i != 12) {
            switch (i) {
                case 28:
                case 29:
                case 30:
                    objArr[1] = "getTagName";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/util/xmlb/BeanBinding";
                    break;
            }
        } else {
            objArr[1] = TypeProxy.SilentConstruction.Appender.NEW_INSTANCE_METHOD_NAME;
        }
        switch (i) {
            case 1:
            case 2:
                objArr[2] = ConfigConstants.CONFIG_INIT_SECTION;
                break;
            case 3:
            case 4:
                objArr[2] = "serialize";
                break;
            case 5:
                objArr[2] = "serializeInto";
                break;
            case 6:
            case 7:
                objArr[2] = "serializePropertyInto";
                break;
            case 8:
            case 10:
                objArr[2] = "deserialize";
                break;
            case 9:
            case 11:
            case 12:
            case 17:
            case 28:
            case 29:
            case 30:
            case 33:
            case 38:
                break;
            case 13:
            case 14:
            case 15:
                objArr[2] = "equalByFields";
                break;
            case 16:
                objArr[2] = "computeBindingWeights";
                break;
            case 18:
                objArr[2] = "sortBindings";
                break;
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
                objArr[2] = "deserializeInto";
                break;
            case 25:
            case 26:
                objArr[2] = "isBoundTo";
                break;
            case 27:
                objArr[2] = "getTagName";
                break;
            case 31:
                objArr[2] = "getTagNameFromAnnotation";
                break;
            case 32:
                objArr[2] = "getAccessors";
                break;
            case 34:
                objArr[2] = "isAssertBindings";
                break;
            case 35:
            case 36:
            case 37:
                objArr[2] = "createBinding";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 9 && i != 17 && i != 33 && i != 38 && i != 11 && i != 12) {
            switch (i) {
                case 28:
                case 29:
                case 30:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    public BeanBinding(Class<?> cls) {
        if (cls == null) {
            $$$reportNull$$$0(0);
        }
        this.compareByFields = ThreeState.UNSURE;
        this.myBeanClass = cls;
        this.myTagName = getTagName(cls);
    }

    private static final class XmlSerializerPropertyCollector extends PropertyCollector {
        private final ClassValue<List<MutableAccessor>> accessorCache;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 2 ? 3 : 2];
            if (i == 1) {
                objArr[0] = "aClass";
            } else if (i != 2) {
                objArr[0] = "configuration";
            } else {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/xmlb/BeanBinding$XmlSerializerPropertyCollector";
            }
            if (i != 2) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/util/xmlb/BeanBinding$XmlSerializerPropertyCollector";
            } else {
                objArr[1] = "collect";
            }
            if (i == 1) {
                objArr[2] = "collect";
            } else if (i != 2) {
                objArr[2] = "<init>";
            }
            String str2 = String.format(str, objArr);
            if (i == 2) {
                throw new IllegalStateException(str2);
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        XmlSerializerPropertyCollector(PropertyCollector.Configuration configuration) {
            super(configuration);
            if (configuration == null) {
                $$$reportNull$$$0(0);
            }
            this.accessorCache = new XmlSerializerPropertyCollectorListClassValue(configuration);
        }

        @Override // org.jetbrains.kotlin.com.intellij.serialization.PropertyCollector
        public List<MutableAccessor> collect(Class<?> cls) {
            if (cls == null) {
                $$$reportNull$$$0(1);
            }
            List<MutableAccessor> list = this.accessorCache.get(cls);
            if (list == null) {
                $$$reportNull$$$0(2);
            }
            return list;
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.xmlb.Binding
    public final synchronized void init(Type type, Serializer serializer) {
        if (type == null) {
            try {
                $$$reportNull$$$0(1);
            } catch (Throwable th) {
                throw th;
            }
        }
        if (serializer == null) {
            $$$reportNull$$$0(2);
        }
        Property property = (Property) this.myBeanClass.getAnnotation(Property.class);
        List<MutableAccessor> accessors = getAccessors(this.myBeanClass);
        this.bindings = new NestedBinding[accessors.size()];
        int size = accessors.size();
        for (int i = 0; i < size; i++) {
            NestedBinding nestedBindingCreateBinding = createBinding(accessors.get(i), serializer, property == null ? Property.Style.OPTION_TAG : property.style());
            nestedBindingCreateBinding.init(type, serializer);
            this.bindings[i] = nestedBindingCreateBinding;
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.xmlb.NotNullDeserializeBinding
    public final Object deserialize(Object obj, XmlElement xmlElement) {
        if (xmlElement == null) {
            $$$reportNull$$$0(10);
        }
        Object objNewInstance = newInstance();
        deserializeInto(objNewInstance, xmlElement);
        if (objNewInstance == null) {
            $$$reportNull$$$0(11);
        }
        return objNewInstance;
    }

    protected Object newInstance() {
        Object objNewInstance = ReflectionUtil.newInstance(this.myBeanClass, false);
        if (objNewInstance == null) {
            $$$reportNull$$$0(12);
        }
        return objNewInstance;
    }

    public final void deserializeInto(Object obj, XmlElement xmlElement) {
        if (obj == null) {
            $$$reportNull$$$0(23);
        }
        if (xmlElement == null) {
            $$$reportNull$$$0(24);
        }
        int i = 0;
        for (NestedBinding nestedBinding : this.bindings) {
            if (nestedBinding instanceof AttributeBinding) {
                i++;
                AttributeBinding attributeBinding = (AttributeBinding) nestedBinding;
                String str = xmlElement.attributes.get(attributeBinding.f726name);
                if (str != null) {
                    attributeBinding.set(obj, str);
                }
            } else if (xmlElement.content != null && (nestedBinding instanceof TextBinding)) {
                ((TextBinding) nestedBinding).set(obj, xmlElement.content);
            }
        }
        if (i == this.bindings.length) {
            return;
        }
        LinkedHashMap linkedHashMap = null;
        for (XmlElement xmlElement2 : xmlElement.children) {
            for (NestedBinding nestedBinding2 : this.bindings) {
                if (!(nestedBinding2 instanceof AttributeBinding) && !(nestedBinding2 instanceof TextBinding) && nestedBinding2.isBoundTo(xmlElement2)) {
                    if ((nestedBinding2 instanceof MultiNodeBinding) && ((MultiNodeBinding) nestedBinding2).isMulti()) {
                        if (linkedHashMap == null) {
                            linkedHashMap = new LinkedHashMap();
                        }
                        ((List) linkedHashMap.computeIfAbsent(nestedBinding2, new Function() { // from class: org.jetbrains.kotlin.com.intellij.util.xmlb.BeanBinding$$ExternalSyntheticLambda0
                            @Override // java.util.function.Function
                            public final Object apply(Object obj2) {
                                return BeanBinding.lambda$deserializeInto$2((NestedBinding) obj2);
                            }
                        })).add(xmlElement2);
                        break;
                    }
                    nestedBinding2.deserializeUnsafe(obj, xmlElement2);
                    break;
                }
            }
        }
        for (NestedBinding nestedBinding3 : this.bindings) {
            if ((nestedBinding3 instanceof AccessorBindingWrapper) && ((AccessorBindingWrapper) nestedBinding3).isFlat) {
                nestedBinding3.deserializeUnsafe(obj, xmlElement);
            }
        }
        if (linkedHashMap != null) {
            for (NestedBinding nestedBinding4 : linkedHashMap.keySet()) {
                ((MultiNodeBinding) nestedBinding4).deserializeList2(obj, (List) linkedHashMap.get(nestedBinding4));
            }
        }
    }

    static /* synthetic */ List lambda$deserializeInto$2(NestedBinding nestedBinding) {
        return new ArrayList();
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.xmlb.Binding
    public final boolean isBoundTo(XmlElement xmlElement) {
        if (xmlElement == null) {
            $$$reportNull$$$0(26);
        }
        return xmlElement.name.equals(this.myTagName);
    }

    private static String getTagName(Class<?> cls) {
        int i;
        if (cls == null) {
            $$$reportNull$$$0(27);
        }
        for (Class<?> superclass = cls; superclass != null; superclass = superclass.getSuperclass()) {
            String tagNameFromAnnotation = getTagNameFromAnnotation(superclass);
            if (tagNameFromAnnotation != null) {
                if (tagNameFromAnnotation == null) {
                    $$$reportNull$$$0(28);
                }
                return tagNameFromAnnotation;
            }
        }
        String simpleName = cls.getSimpleName();
        if (simpleName.isEmpty()) {
            simpleName = cls.getSuperclass().getSimpleName();
        }
        int iLastIndexOf = simpleName.lastIndexOf(36);
        if (iLastIndexOf <= 0 || simpleName.length() <= (i = iLastIndexOf + 1)) {
            if (simpleName == null) {
                $$$reportNull$$$0(30);
            }
            return simpleName;
        }
        String strSubstring = simpleName.substring(i);
        if (strSubstring == null) {
            $$$reportNull$$$0(29);
        }
        return strSubstring;
    }

    private static String getTagNameFromAnnotation(Class<?> cls) {
        if (cls == null) {
            $$$reportNull$$$0(31);
        }
        Tag tag = (Tag) cls.getAnnotation(Tag.class);
        if (tag == null || tag.value().isEmpty()) {
            return null;
        }
        return tag.value();
    }

    public static List<MutableAccessor> getAccessors(Class<?> cls) {
        if (cls == null) {
            $$$reportNull$$$0(32);
        }
        List<MutableAccessor> listCollect = PROPERTY_COLLECTOR.collect(cls);
        if (listCollect == null) {
            $$$reportNull$$$0(33);
        }
        return listCollect;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isAssertBindings(Class<?> cls) {
        if (cls == null) {
            $$$reportNull$$$0(34);
        }
        do {
            Property property = (Property) cls.getAnnotation(Property.class);
            if (property != null && !property.assertIfNoBindings()) {
                return true;
            }
            cls = cls.getSuperclass();
        } while (cls != null);
        return false;
    }

    private static final class XmlSerializerPropertyCollectorListClassValue extends ClassValue<List<MutableAccessor>> {
        private final PropertyCollector.Configuration configuration;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "configuration", "org/jetbrains/kotlin/com/intellij/util/xmlb/BeanBinding$XmlSerializerPropertyCollectorListClassValue", "<init>"));
        }

        @Override // java.lang.ClassValue
        protected /* bridge */ /* synthetic */ List<MutableAccessor> computeValue(Class cls) {
            return computeValue2((Class<?>) cls);
        }

        private XmlSerializerPropertyCollectorListClassValue(PropertyCollector.Configuration configuration) {
            if (configuration == null) {
                $$$reportNull$$$0(0);
            }
            this.configuration = configuration;
        }

        @Override // java.lang.ClassValue
        /* JADX INFO: renamed from: computeValue, reason: avoid collision after fix types in other method */
        protected List<MutableAccessor> computeValue2(Class<?> cls) {
            List<MutableAccessor> listDoCollect = PropertyCollector.doCollect(cls, this.configuration, null);
            if (listDoCollect.isEmpty() && !BeanBinding.isAssertBindings(cls)) {
                if (JDOMExternalizable.class.isAssignableFrom(cls)) {
                    Binding.LOG.error("Do not compute bindings for JDOMExternalizable: " + cls.getName());
                } else if (cls.isEnum()) {
                    Binding.LOG.error("Do not compute bindings for enum: " + cls.getName());
                } else if (cls == String.class) {
                    Binding.LOG.error("Do not compute bindings for String");
                }
                Binding.LOG.warn("no accessors for " + cls.getName());
            }
            return listDoCollect;
        }
    }

    public String toString() {
        return "BeanBinding[" + this.myBeanClass.getName() + ", tagName=" + this.myTagName + "]";
    }

    private static NestedBinding createBinding(MutableAccessor mutableAccessor, Serializer serializer, Property.Style style) {
        boolean zFlat;
        boolean zSurroundWithTag;
        XMap xMap;
        if (mutableAccessor == null) {
            $$$reportNull$$$0(35);
        }
        if (serializer == null) {
            $$$reportNull$$$0(36);
        }
        if (style == null) {
            $$$reportNull$$$0(37);
        }
        Attribute attribute = (Attribute) mutableAccessor.getAnnotation(Attribute.class);
        if (attribute != null) {
            return new AttributeBinding(mutableAccessor, attribute);
        }
        if (((Text) mutableAccessor.getAnnotation(Text.class)) != null) {
            return new TextBinding(mutableAccessor);
        }
        OptionTag optionTag = (OptionTag) mutableAccessor.getAnnotation(OptionTag.class);
        if (optionTag != null && optionTag.converter() != Converter.class) {
            return new OptionTagBinding(mutableAccessor, optionTag);
        }
        Binding binding = serializer.getBinding(mutableAccessor);
        if (binding instanceof JDOMElementBinding) {
            JDOMElementBinding jDOMElementBinding = (JDOMElementBinding) binding;
            if (jDOMElementBinding == null) {
                $$$reportNull$$$0(38);
            }
            return jDOMElementBinding;
        }
        Tag tag = (Tag) mutableAccessor.getAnnotation(Tag.class);
        if (tag != null) {
            return new TagBinding(mutableAccessor, tag);
        }
        if (binding instanceof CompactCollectionBinding) {
            return new AccessorBindingWrapper(mutableAccessor, binding, false, Property.Style.OPTION_TAG);
        }
        Property property = (Property) mutableAccessor.getAnnotation(Property.class);
        if (property != null) {
            zSurroundWithTag = property.surroundWithTag();
            zFlat = property.flat();
        } else {
            zFlat = false;
            zSurroundWithTag = true;
        }
        if (!zSurroundWithTag || zFlat) {
            if (zFlat && !(binding instanceof BeanBinding)) {
                throw new XmlSerializationException("inline supported only for BeanBinding: " + mutableAccessor);
            }
            if (binding == null || (binding instanceof TextBinding)) {
                throw new XmlSerializationException("Text-serializable properties can't be serialized without surrounding tags: " + mutableAccessor);
            }
            return new AccessorBindingWrapper(mutableAccessor, binding, zFlat, property.style());
        }
        XCollection xCollection = (XCollection) mutableAccessor.getAnnotation(XCollection.class);
        if (xCollection != null && (!xCollection.propertyElementName().isEmpty() || xCollection.style() == XCollection.Style.v2)) {
            return new TagBinding(mutableAccessor, xCollection.propertyElementName());
        }
        if (optionTag == null && (xMap = (XMap) mutableAccessor.getAnnotation(XMap.class)) != null) {
            return new TagBinding(mutableAccessor, xMap.propertyElementName());
        }
        if (style == Property.Style.ATTRIBUTE) {
            return new AttributeBinding(mutableAccessor, null);
        }
        return new OptionTagBinding(mutableAccessor, optionTag);
    }

    private static final class MyPropertyCollectorConfiguration extends PropertyCollector.Configuration {
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/xmlb/BeanBinding$MyPropertyCollectorConfiguration";
            if (i != 1) {
                objArr[2] = "isAnnotatedAsTransient";
            } else {
                objArr[2] = "hasStoreAnnotations";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        private MyPropertyCollectorConfiguration() {
            super(true, false, false);
        }

        @Override // org.jetbrains.kotlin.com.intellij.serialization.PropertyCollector.Configuration
        public boolean isAnnotatedAsTransient(AnnotatedElement annotatedElement) {
            if (annotatedElement == null) {
                $$$reportNull$$$0(0);
            }
            return annotatedElement.isAnnotationPresent(Transient.class);
        }

        @Override // org.jetbrains.kotlin.com.intellij.serialization.PropertyCollector.Configuration
        public boolean hasStoreAnnotations(AccessibleObject accessibleObject) {
            if (accessibleObject == null) {
                $$$reportNull$$$0(1);
            }
            return accessibleObject.isAnnotationPresent(OptionTag.class) || accessibleObject.isAnnotationPresent(Tag.class) || accessibleObject.isAnnotationPresent(Attribute.class) || accessibleObject.isAnnotationPresent(Property.class) || accessibleObject.isAnnotationPresent(Text.class) || accessibleObject.isAnnotationPresent(CollectionBean.class) || accessibleObject.isAnnotationPresent(MapAnnotation.class) || accessibleObject.isAnnotationPresent(XMap.class) || accessibleObject.isAnnotationPresent(XCollection.class) || accessibleObject.isAnnotationPresent(AbstractCollection.class);
        }
    }
}
