package com.thoughtworks.xstream.converters.extended;

import com.reandroid.apk.PathMap;
import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.ConverterLookup;
import com.thoughtworks.xstream.converters.ConverterMatcher;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.SingleValueConverter;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.reflection.AbstractReflectionConverter;
import com.thoughtworks.xstream.converters.reflection.ReflectionProvider;
import com.thoughtworks.xstream.core.JVM;
import com.thoughtworks.xstream.core.util.FastField;
import com.thoughtworks.xstream.core.util.HierarchicalStreams;
import com.thoughtworks.xstream.core.util.Primitives;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/extended/ToAttributedValueConverter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class ToAttributedValueConverter implements Converter {
    private static final String STRUCTURE_MARKER = "";
    private final Mapper enumMapper;
    private final ConverterLookup lookup;
    private final Mapper mapper;
    private final ReflectionProvider reflectionProvider;
    private final Class type;
    private final Field valueField;

    public ToAttributedValueConverter(Class cls, Mapper mapper, ReflectionProvider reflectionProvider, ConverterLookup converterLookup) {
        this(cls, mapper, reflectionProvider, converterLookup, null, null);
    }

    public ToAttributedValueConverter(Class cls, Mapper mapper, ReflectionProvider reflectionProvider, ConverterLookup converterLookup, String str) {
        this(cls, mapper, reflectionProvider, converterLookup, str, null);
    }

    public ToAttributedValueConverter(Class cls, Mapper mapper, ReflectionProvider reflectionProvider, ConverterLookup converterLookup, String str, Class cls2) {
        this.type = cls;
        this.mapper = mapper;
        this.reflectionProvider = reflectionProvider;
        this.lookup = converterLookup;
        if (str == null) {
            this.valueField = null;
        } else {
            try {
                Field declaredField = (cls2 != null ? cls2 : cls).getDeclaredField(str);
                if (!declaredField.isAccessible()) {
                    declaredField.setAccessible(true);
                }
                this.valueField = declaredField;
            } catch (NoSuchFieldException e) {
                throw new IllegalArgumentException(new StringBuffer().append(e.getMessage()).append(": ").append(str).toString());
            }
        }
        this.enumMapper = JVM.isVersion(5) ? UseAttributeForEnumMapper.createEnumMapper(mapper) : null;
    }

    @Override // com.thoughtworks.xstream.converters.ConverterMatcher
    public boolean canConvert(Class cls) {
        return this.type == cls;
    }

    @Override // com.thoughtworks.xstream.converters.Converter
    public void marshal(Object obj, final HierarchicalStreamWriter hierarchicalStreamWriter, MarshallingContext marshallingContext) {
        String strAliasForSystemAttribute;
        final Class<?> cls = obj.getClass();
        final HashMap map = new HashMap();
        final String[] strArr = new String[1];
        final Object[] objArr = new Object[1];
        final Class[] clsArr = new Class[1];
        final Class[] clsArr2 = new Class[1];
        this.reflectionProvider.visitSerializableFields(obj, new ReflectionProvider.Visitor() { // from class: com.thoughtworks.xstream.converters.extended.ToAttributedValueConverter.1
            @Override // com.thoughtworks.xstream.converters.reflection.ReflectionProvider.Visitor
            public void visit(String str, Class cls2, Class cls3, Object obj2) {
                if (ToAttributedValueConverter.this.mapper.shouldSerializeMember(cls3, str)) {
                    FastField fastField = new FastField(cls3, str);
                    String strSerializedMember = ToAttributedValueConverter.this.mapper.serializedMember(cls3, str);
                    if (map.containsKey(strSerializedMember)) {
                        if (!ToAttributedValueConverter.this.fieldIsEqual(fastField)) {
                            ConversionException conversionException = new ConversionException("Cannot write attribute twice for object");
                            conversionException.add(PathMap.NAME_alias, strSerializedMember);
                            conversionException.add(ModuleXmlParser.TYPE, cls.getName());
                            throw conversionException;
                        }
                    } else {
                        map.put(strSerializedMember, ToAttributedValueConverter.this.reflectionProvider.getField(cls, str));
                    }
                    ConverterMatcher converterFromItemType = UseAttributeForEnumMapper.isEnum(cls2) ? ToAttributedValueConverter.this.enumMapper.getConverterFromItemType(null, cls2, null) : ToAttributedValueConverter.this.mapper.getLocalConverter(cls3, str);
                    if (converterFromItemType == null) {
                        converterFromItemType = ToAttributedValueConverter.this.lookup.lookupConverterForType(cls2);
                    }
                    if (obj2 != null) {
                        boolean z = ToAttributedValueConverter.this.valueField != null && ToAttributedValueConverter.this.fieldIsEqual(fastField);
                        if (z) {
                            clsArr2[0] = cls3;
                            clsArr[0] = cls2;
                            objArr[0] = obj2;
                            strArr[0] = "";
                        }
                        if (!(converterFromItemType instanceof SingleValueConverter)) {
                            if (z) {
                                return;
                            }
                            ConversionException conversionException2 = new ConversionException("Cannot write element as attribute");
                            conversionException2.add(PathMap.NAME_alias, strSerializedMember);
                            conversionException2.add(ModuleXmlParser.TYPE, cls.getName());
                            throw conversionException2;
                        }
                        String string = ((SingleValueConverter) converterFromItemType).toString(obj2);
                        if (z) {
                            strArr[0] = string;
                        } else if (string != null) {
                            hierarchicalStreamWriter.addAttribute(strSerializedMember, string);
                        }
                    }
                }
            }
        });
        if (strArr[0] != null) {
            Class<?> cls2 = objArr[0].getClass();
            Class clsDefaultImplementationOf = this.mapper.defaultImplementationOf(clsArr[0]);
            if (!cls2.equals(clsDefaultImplementationOf)) {
                String strSerializedClass = this.mapper.serializedClass(cls2);
                if (!strSerializedClass.equals(this.mapper.serializedClass(clsDefaultImplementationOf)) && (strAliasForSystemAttribute = this.mapper.aliasForSystemAttribute("class")) != null) {
                    hierarchicalStreamWriter.addAttribute(strAliasForSystemAttribute, strSerializedClass);
                }
            }
            String str = strArr[0];
            if (str == "") {
                marshallingContext.convertAnother(objArr[0]);
            } else {
                hierarchicalStreamWriter.setValue(str);
            }
        }
    }

    @Override // com.thoughtworks.xstream.converters.Converter
    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext) {
        Class clsDefaultImplementationOf;
        Iterator it;
        ConverterMatcher localConverter;
        Object objNewInstance = this.reflectionProvider.newInstance(unmarshallingContext.getRequiredType());
        Class<?> cls = objNewInstance.getClass();
        HashSet hashSet = new HashSet();
        Iterator attributeNames = hierarchicalStreamReader.getAttributeNames();
        HashSet hashSet2 = new HashSet();
        hashSet2.add(this.mapper.aliasForSystemAttribute("class"));
        while (attributeNames.hasNext()) {
            String str = (String) attributeNames.next();
            if (!hashSet2.contains(str)) {
                String strRealMember = this.mapper.realMember(cls, str);
                Field fieldOrNull = this.reflectionProvider.getFieldOrNull(cls, strRealMember);
                if (fieldOrNull == null) {
                    cls = cls;
                    it = attributeNames;
                    hashSet2 = hashSet2;
                } else if (Modifier.isTransient(fieldOrNull.getModifiers())) {
                    continue;
                } else {
                    it = attributeNames;
                    Class<?> type = fieldOrNull.getType();
                    Class<?> declaringClass = fieldOrNull.getDeclaringClass();
                    if (UseAttributeForEnumMapper.isEnum(type)) {
                        localConverter = this.enumMapper.getConverterFromItemType(null, type, null);
                    } else {
                        localConverter = this.mapper.getLocalConverter(declaringClass, strRealMember);
                    }
                    if (localConverter == null) {
                        localConverter = this.lookup.lookupConverterForType(type);
                    }
                    if (!(localConverter instanceof SingleValueConverter)) {
                        ConversionException conversionException = new ConversionException("Cannot read field as a single value for object");
                        conversionException.add("field", strRealMember);
                        conversionException.add(ModuleXmlParser.TYPE, cls.getName());
                        throw conversionException;
                    }
                    if (localConverter != null) {
                        Object objFromString = ((SingleValueConverter) localConverter).fromString(hierarchicalStreamReader.getAttribute(str));
                        if (type.isPrimitive()) {
                            type = Primitives.box(type);
                        }
                        if (objFromString != null && !type.isAssignableFrom(objFromString.getClass())) {
                            ConversionException conversionException2 = new ConversionException("Cannot assign object to type");
                            conversionException2.add("object type", objFromString.getClass().getName());
                            conversionException2.add("target type", type.getName());
                            throw conversionException2;
                        }
                        this.reflectionProvider.writeField(objNewInstance, strRealMember, objFromString, declaringClass);
                        if (!hashSet.add(new FastField(declaringClass, strRealMember))) {
                            throw new AbstractReflectionConverter.DuplicateFieldException(new StringBuffer().append(strRealMember).append(" [").append(declaringClass.getName()).append("]").toString());
                        }
                    }
                }
                attributeNames = it;
                hashSet2 = hashSet2;
                cls = cls;
            }
        }
        Field field = this.valueField;
        if (field != null) {
            Class<?> declaringClass2 = field.getDeclaringClass();
            String name2 = this.valueField.getName();
            Field field2 = name2 == null ? null : this.reflectionProvider.getField(declaringClass2, name2);
            if (name2 == null || field2 == null) {
                ConversionException conversionException3 = new ConversionException("Cannot assign value to field of type");
                conversionException3.add(CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, hierarchicalStreamReader.getNodeName());
                conversionException3.add("field", name2);
                conversionException3.add("target type", unmarshallingContext.getRequiredType().getName());
                throw conversionException3;
            }
            String classAttribute = HierarchicalStreams.readClassAttribute(hierarchicalStreamReader, this.mapper);
            if (classAttribute != null) {
                clsDefaultImplementationOf = this.mapper.realClass(classAttribute);
            } else {
                clsDefaultImplementationOf = this.mapper.defaultImplementationOf(this.reflectionProvider.getFieldType(objNewInstance, name2, declaringClass2));
            }
            Object objConvertAnother = unmarshallingContext.convertAnother(objNewInstance, clsDefaultImplementationOf, this.mapper.getLocalConverter(field2.getDeclaringClass(), field2.getName()));
            Class fieldType = this.reflectionProvider.getFieldType(objNewInstance, name2, declaringClass2);
            if (!fieldType.isPrimitive()) {
                clsDefaultImplementationOf = fieldType;
            }
            if (objConvertAnother != null && !clsDefaultImplementationOf.isAssignableFrom(objConvertAnother.getClass())) {
                ConversionException conversionException4 = new ConversionException("Cannot assign object to type");
                conversionException4.add("object type", objConvertAnother.getClass().getName());
                conversionException4.add("target type", clsDefaultImplementationOf.getName());
                throw conversionException4;
            }
            this.reflectionProvider.writeField(objNewInstance, name2, objConvertAnother, declaringClass2);
            if (!hashSet.add(new FastField(declaringClass2, name2))) {
                throw new AbstractReflectionConverter.DuplicateFieldException(new StringBuffer().append(name2).append(" [").append(declaringClass2.getName()).append("]").toString());
            }
        }
        return objNewInstance;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean fieldIsEqual(FastField fastField) {
        return this.valueField.getName().equals(fastField.getName()) && this.valueField.getDeclaringClass().getName().equals(fastField.getDeclaringClass());
    }
}
