package com.thoughtworks.xstream.converters.javabean;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.reflection.MissingFieldException;
import com.thoughtworks.xstream.core.util.FastField;
import com.thoughtworks.xstream.io.ExtendedHierarchicalStreamWriterHelper;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;
import java.util.HashSet;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/javabean/JavaBeanConverter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class JavaBeanConverter implements Converter {
    static /* synthetic */ Class class$com$thoughtworks$xstream$mapper$Mapper$Null;
    protected final JavaBeanProvider beanProvider;
    private String classAttributeIdentifier;
    protected final Mapper mapper;
    private final Class type;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public JavaBeanConverter(Mapper mapper) {
        this(mapper, (Class) null);
    }

    public JavaBeanConverter(Mapper mapper, Class cls) {
        this(mapper, new BeanProvider(), cls);
    }

    public JavaBeanConverter(Mapper mapper, JavaBeanProvider javaBeanProvider) {
        this(mapper, javaBeanProvider, null);
    }

    public JavaBeanConverter(Mapper mapper, JavaBeanProvider javaBeanProvider, Class cls) {
        this.mapper = mapper;
        this.beanProvider = javaBeanProvider;
        this.type = cls;
    }

    public JavaBeanConverter(Mapper mapper, String str) {
        this(mapper, new BeanProvider());
        this.classAttributeIdentifier = str;
    }

    @Override // com.thoughtworks.xstream.converters.ConverterMatcher
    public boolean canConvert(Class cls) {
        Class cls2 = this.type;
        return (cls2 == null || cls2 == cls) && this.beanProvider.canInstantiate(cls);
    }

    @Override // com.thoughtworks.xstream.converters.Converter
    public void marshal(final Object obj, final HierarchicalStreamWriter hierarchicalStreamWriter, final MarshallingContext marshallingContext) {
        final String strAliasForSystemAttribute = this.mapper.aliasForSystemAttribute("class");
        this.beanProvider.visitSerializableProperties(obj, new JavaBeanProvider.Visitor() { // from class: com.thoughtworks.xstream.converters.javabean.JavaBeanConverter.1
            @Override // com.thoughtworks.xstream.converters.javabean.JavaBeanProvider.Visitor
            public boolean shouldVisit(String str, Class cls) {
                return JavaBeanConverter.this.mapper.shouldSerializeMember(cls, str);
            }

            @Override // com.thoughtworks.xstream.converters.javabean.JavaBeanProvider.Visitor
            public void visit(String str, Class cls, Class cls2, Object obj2) throws Throwable {
                if (obj2 != null) {
                    writeField(str, cls, obj2);
                } else {
                    writeNullField(str);
                }
            }

            private void writeField(String str, Class cls, Object obj2) {
                String str2;
                Class<?> cls2 = obj2.getClass();
                Class clsDefaultImplementationOf = JavaBeanConverter.this.mapper.defaultImplementationOf(cls);
                ExtendedHierarchicalStreamWriterHelper.startNode(hierarchicalStreamWriter, JavaBeanConverter.this.mapper.serializedMember(obj.getClass(), str), cls2);
                if (!cls2.equals(clsDefaultImplementationOf) && (str2 = strAliasForSystemAttribute) != null) {
                    hierarchicalStreamWriter.addAttribute(str2, JavaBeanConverter.this.mapper.serializedClass(cls2));
                }
                marshallingContext.convertAnother(obj2);
                hierarchicalStreamWriter.endNode();
            }

            private void writeNullField(String str) throws Throwable {
                Class clsClass$;
                Class clsClass$2;
                String strSerializedMember = JavaBeanConverter.this.mapper.serializedMember(obj.getClass(), str);
                HierarchicalStreamWriter hierarchicalStreamWriter2 = hierarchicalStreamWriter;
                if (JavaBeanConverter.class$com$thoughtworks$xstream$mapper$Mapper$Null == null) {
                    clsClass$ = JavaBeanConverter.class$("com.thoughtworks.xstream.mapper.Mapper$Null");
                    JavaBeanConverter.class$com$thoughtworks$xstream$mapper$Mapper$Null = clsClass$;
                } else {
                    clsClass$ = JavaBeanConverter.class$com$thoughtworks$xstream$mapper$Mapper$Null;
                }
                ExtendedHierarchicalStreamWriterHelper.startNode(hierarchicalStreamWriter2, strSerializedMember, clsClass$);
                HierarchicalStreamWriter hierarchicalStreamWriter3 = hierarchicalStreamWriter;
                String str2 = strAliasForSystemAttribute;
                Mapper mapper = JavaBeanConverter.this.mapper;
                if (JavaBeanConverter.class$com$thoughtworks$xstream$mapper$Mapper$Null == null) {
                    clsClass$2 = JavaBeanConverter.class$("com.thoughtworks.xstream.mapper.Mapper$Null");
                    JavaBeanConverter.class$com$thoughtworks$xstream$mapper$Mapper$Null = clsClass$2;
                } else {
                    clsClass$2 = JavaBeanConverter.class$com$thoughtworks$xstream$mapper$Mapper$Null;
                }
                hierarchicalStreamWriter3.addAttribute(str2, mapper.serializedClass(clsClass$2));
                hierarchicalStreamWriter.endNode();
            }
        });
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    @Override // com.thoughtworks.xstream.converters.Converter
    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext) {
        Object objInstantiateNewInstance = instantiateNewInstance(unmarshallingContext);
        HashSet hashSet = new HashSet() { // from class: com.thoughtworks.xstream.converters.javabean.JavaBeanConverter.2
            @Override // java.util.HashSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean add(Object obj) {
                if (super.add(obj)) {
                    return true;
                }
                throw new DuplicatePropertyException(((FastField) obj).getName());
            }
        };
        Class<?> cls = objInstantiateNewInstance.getClass();
        while (hierarchicalStreamReader.hasMoreChildren()) {
            hierarchicalStreamReader.moveDown();
            String strRealMember = this.mapper.realMember(cls, hierarchicalStreamReader.getNodeName());
            if (this.mapper.shouldSerializeMember(cls, strRealMember)) {
                if (this.beanProvider.propertyDefinedInClass(strRealMember, cls)) {
                    this.beanProvider.writeProperty(objInstantiateNewInstance, strRealMember, unmarshallingContext.convertAnother(objInstantiateNewInstance, determineType(hierarchicalStreamReader, objInstantiateNewInstance, strRealMember)));
                    hashSet.add(new FastField(cls, strRealMember));
                } else if (!this.mapper.isIgnoredElement(strRealMember)) {
                    throw new MissingFieldException(cls.getName(), strRealMember);
                }
            }
            hierarchicalStreamReader.moveUp();
        }
        return objInstantiateNewInstance;
    }

    private Object instantiateNewInstance(UnmarshallingContext unmarshallingContext) {
        Object objCurrentObject = unmarshallingContext.currentObject();
        return objCurrentObject == null ? this.beanProvider.newInstance(unmarshallingContext.getRequiredType()) : objCurrentObject;
    }

    private Class determineType(HierarchicalStreamReader hierarchicalStreamReader, Object obj, String str) {
        String strAliasForSystemAttribute = this.classAttributeIdentifier;
        if (strAliasForSystemAttribute == null) {
            strAliasForSystemAttribute = this.mapper.aliasForSystemAttribute("class");
        }
        String attribute = strAliasForSystemAttribute == null ? null : hierarchicalStreamReader.getAttribute(strAliasForSystemAttribute);
        if (attribute != null) {
            return this.mapper.realClass(attribute);
        }
        return this.mapper.defaultImplementationOf(this.beanProvider.getPropertyType(obj, str));
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/javabean/JavaBeanConverter$DuplicateFieldException.class
      classes11.dex
     */
    public static class DuplicateFieldException extends ConversionException {
        public DuplicateFieldException(String str) {
            super(str);
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/javabean/JavaBeanConverter$DuplicatePropertyException.class
      classes11.dex
     */
    public static class DuplicatePropertyException extends ConversionException {
        public DuplicatePropertyException(String str) {
            super(new StringBuffer("Duplicate property ").append(str).toString());
            add("property", str);
        }
    }
}
