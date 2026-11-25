package com.thoughtworks.xstream.mapper;

import com.thoughtworks.xstream.InitializationException;
import com.thoughtworks.xstream.converters.reflection.ReflectionProvider;
import com.thoughtworks.xstream.core.util.Primitives;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/mapper/ImplicitCollectionMapper.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class ImplicitCollectionMapper extends MapperWrapper {
    static /* synthetic */ Class class$com$thoughtworks$xstream$mapper$Mapper$Null;
    static /* synthetic */ Class class$java$lang$Object;
    static /* synthetic */ Class class$java$util$Collection;
    static /* synthetic */ Class class$java$util$Map;
    static /* synthetic */ Class class$java$util$Map$Entry;
    private final Map classNameToMapper;
    private ReflectionProvider reflectionProvider;

    public ImplicitCollectionMapper(Mapper mapper, ReflectionProvider reflectionProvider) {
        super(mapper);
        this.classNameToMapper = new HashMap();
        this.reflectionProvider = reflectionProvider;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ImplicitCollectionMapperForClass getMapper(Class cls, String str) {
        Field fieldOrNull = str != null ? this.reflectionProvider.getFieldOrNull(cls, str) : null;
        Class<?> declaringClass = fieldOrNull != null ? fieldOrNull.getDeclaringClass() : null;
        while (cls != null) {
            ImplicitCollectionMapperForClass implicitCollectionMapperForClass = (ImplicitCollectionMapperForClass) this.classNameToMapper.get(cls);
            if (implicitCollectionMapperForClass != null) {
                return implicitCollectionMapperForClass;
            }
            if (cls == declaringClass) {
                break;
            }
            cls = cls.getSuperclass();
        }
        return null;
    }

    private ImplicitCollectionMapperForClass getOrCreateMapper(Class cls) {
        ImplicitCollectionMapperForClass implicitCollectionMapperForClass = (ImplicitCollectionMapperForClass) this.classNameToMapper.get(cls);
        if (implicitCollectionMapperForClass != null) {
            return implicitCollectionMapperForClass;
        }
        ImplicitCollectionMapperForClass implicitCollectionMapperForClass2 = new ImplicitCollectionMapperForClass(cls);
        this.classNameToMapper.put(cls, implicitCollectionMapperForClass2);
        return implicitCollectionMapperForClass2;
    }

    @Override // com.thoughtworks.xstream.mapper.MapperWrapper, com.thoughtworks.xstream.mapper.Mapper
    public String getFieldNameForItemTypeAndName(Class cls, Class cls2, String str) {
        ImplicitCollectionMapperForClass mapper = getMapper(cls, null);
        if (mapper != null) {
            return mapper.getFieldNameForItemTypeAndName(cls2, str);
        }
        return null;
    }

    @Override // com.thoughtworks.xstream.mapper.MapperWrapper, com.thoughtworks.xstream.mapper.Mapper
    public Class getItemTypeForItemFieldName(Class cls, String str) {
        ImplicitCollectionMapperForClass mapper = getMapper(cls, null);
        if (mapper != null) {
            return mapper.getItemTypeForItemFieldName(str);
        }
        return null;
    }

    @Override // com.thoughtworks.xstream.mapper.MapperWrapper, com.thoughtworks.xstream.mapper.Mapper
    public Mapper.ImplicitCollectionMapping getImplicitCollectionDefForFieldName(Class cls, String str) {
        ImplicitCollectionMapperForClass mapper = getMapper(cls, str);
        if (mapper != null) {
            return mapper.getImplicitCollectionDefForFieldName(str);
        }
        return null;
    }

    public void add(Class cls, String str, Class cls2) {
        add(cls, str, null, cls2);
    }

    public void add(Class cls, String str, String str2, Class cls2) {
        add(cls, str, str2, cls2, null);
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    public void add(Class cls, String str, String str2, Class cls2, String str3) {
        Field declaredField;
        Field field = null;
        if (cls != null) {
            Class superclass = cls;
            loop0: do {
                declaredField = null;
                while (true) {
                    Class clsClass$ = class$java$lang$Object;
                    if (clsClass$ == null) {
                        clsClass$ = class$(CommonClassNames.JAVA_LANG_OBJECT);
                        class$java$lang$Object = clsClass$;
                    }
                    if (superclass == clsClass$) {
                        break loop0;
                    }
                    try {
                        declaredField = superclass.getDeclaredField(str);
                    } catch (NoSuchFieldException unused) {
                        superclass = superclass.getSuperclass();
                    } catch (SecurityException e) {
                        throw new InitializationException("Access denied for field with implicit collection", e);
                    }
                }
                break;
            } while (Modifier.isStatic(declaredField.getModifiers()));
            field = declaredField;
        }
        if (field == null) {
            throw new InitializationException(new StringBuffer("No field \"").append(str).append("\" for implicit collection").toString());
        }
        Class clsClass$2 = class$java$util$Map;
        if (clsClass$2 == null) {
            clsClass$2 = class$(CommonClassNames.JAVA_UTIL_MAP);
            class$java$util$Map = clsClass$2;
        }
        if (!clsClass$2.isAssignableFrom(field.getType())) {
            Class clsClass$3 = class$java$util$Collection;
            if (clsClass$3 == null) {
                clsClass$3 = class$(CommonClassNames.JAVA_UTIL_COLLECTION);
                class$java$util$Collection = clsClass$3;
            }
            if (!clsClass$3.isAssignableFrom(field.getType())) {
                Class<?> type = field.getType();
                if (!type.isArray()) {
                    throw new InitializationException(new StringBuffer("Field \"").append(str).append("\" declares no collection or array").toString());
                }
                Class componentType = type.getComponentType();
                if (componentType.isPrimitive()) {
                    componentType = Primitives.box(componentType);
                }
                if (cls2 == null) {
                    cls2 = componentType;
                } else {
                    if (cls2.isPrimitive()) {
                        cls2 = Primitives.box(cls2);
                    }
                    if (!componentType.isAssignableFrom(cls2)) {
                        throw new InitializationException(new StringBuffer("Field \"").append(str).append("\" declares an array, but the array type is not compatible with ").append(cls2.getName()).toString());
                    }
                }
            }
        } else if (str2 == null && str3 == null && (cls2 = class$java$util$Map$Entry) == null) {
            cls2 = class$("java.util.Map$Entry");
            class$java$util$Map$Entry = cls2;
        }
        getOrCreateMapper(cls).add(new ImplicitCollectionMappingImpl(str, cls2, str2, str3));
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/mapper/ImplicitCollectionMapper$ImplicitCollectionMapperForClass.class
      classes11.dex
     */
    private class ImplicitCollectionMapperForClass {
        private Class definedIn;
        private Map namedItemTypeToDef = new HashMap();
        private Map itemFieldNameToDef = new HashMap();
        private Map fieldNameToDef = new HashMap();

        ImplicitCollectionMapperForClass(Class cls) {
            this.definedIn = cls;
        }

        public String getFieldNameForItemTypeAndName(Class cls, String str) throws Throwable {
            Class clsClass$;
            ImplicitCollectionMappingImpl implicitCollectionMappingImpl = null;
            for (NamedItemType namedItemType : this.namedItemTypeToDef.keySet()) {
                ImplicitCollectionMappingImpl implicitCollectionMappingImpl2 = (ImplicitCollectionMappingImpl) this.namedItemTypeToDef.get(namedItemType);
                if (ImplicitCollectionMapper.class$com$thoughtworks$xstream$mapper$Mapper$Null == null) {
                    clsClass$ = ImplicitCollectionMapper.class$("com.thoughtworks.xstream.mapper.Mapper$Null");
                    ImplicitCollectionMapper.class$com$thoughtworks$xstream$mapper$Mapper$Null = clsClass$;
                } else {
                    clsClass$ = ImplicitCollectionMapper.class$com$thoughtworks$xstream$mapper$Mapper$Null;
                }
                if (cls == clsClass$) {
                    implicitCollectionMappingImpl = implicitCollectionMappingImpl2;
                    break;
                }
                if (namedItemType.itemType.isAssignableFrom(cls)) {
                    if (implicitCollectionMappingImpl2.getItemFieldName() != null) {
                        if (implicitCollectionMappingImpl2.getItemFieldName().equals(str)) {
                            return implicitCollectionMappingImpl2.getFieldName();
                        }
                    } else if (implicitCollectionMappingImpl == null || implicitCollectionMappingImpl.getItemType() == null || (implicitCollectionMappingImpl2.getItemType() != null && implicitCollectionMappingImpl.getItemType().isAssignableFrom(implicitCollectionMappingImpl2.getItemType()))) {
                        implicitCollectionMappingImpl = implicitCollectionMappingImpl2;
                    }
                }
            }
            if (implicitCollectionMappingImpl == null) {
                ImplicitCollectionMapperForClass mapper = ImplicitCollectionMapper.this.getMapper(this.definedIn.getSuperclass(), null);
                if (mapper != null) {
                    return mapper.getFieldNameForItemTypeAndName(cls, str);
                }
                return null;
            }
            return implicitCollectionMappingImpl.getFieldName();
        }

        public Class getItemTypeForItemFieldName(String str) {
            ImplicitCollectionMappingImpl implicitCollectionDefByItemFieldName = getImplicitCollectionDefByItemFieldName(str);
            if (implicitCollectionDefByItemFieldName == null) {
                ImplicitCollectionMapperForClass mapper = ImplicitCollectionMapper.this.getMapper(this.definedIn.getSuperclass(), null);
                if (mapper != null) {
                    return mapper.getItemTypeForItemFieldName(str);
                }
                return null;
            }
            return implicitCollectionDefByItemFieldName.getItemType();
        }

        private ImplicitCollectionMappingImpl getImplicitCollectionDefByItemFieldName(String str) {
            if (str == null) {
                return null;
            }
            ImplicitCollectionMappingImpl implicitCollectionMappingImpl = (ImplicitCollectionMappingImpl) this.itemFieldNameToDef.get(str);
            if (implicitCollectionMappingImpl != null) {
                return implicitCollectionMappingImpl;
            }
            ImplicitCollectionMapperForClass mapper = ImplicitCollectionMapper.this.getMapper(this.definedIn.getSuperclass(), null);
            if (mapper != null) {
                return mapper.getImplicitCollectionDefByItemFieldName(str);
            }
            return null;
        }

        public Mapper.ImplicitCollectionMapping getImplicitCollectionDefForFieldName(String str) {
            Mapper.ImplicitCollectionMapping implicitCollectionMapping = (Mapper.ImplicitCollectionMapping) this.fieldNameToDef.get(str);
            if (implicitCollectionMapping != null) {
                return implicitCollectionMapping;
            }
            ImplicitCollectionMapperForClass mapper = ImplicitCollectionMapper.this.getMapper(this.definedIn.getSuperclass(), null);
            if (mapper != null) {
                return mapper.getImplicitCollectionDefForFieldName(str);
            }
            return null;
        }

        public void add(ImplicitCollectionMappingImpl implicitCollectionMappingImpl) {
            this.fieldNameToDef.put(implicitCollectionMappingImpl.getFieldName(), implicitCollectionMappingImpl);
            this.namedItemTypeToDef.put(implicitCollectionMappingImpl.createNamedItemType(), implicitCollectionMappingImpl);
            if (implicitCollectionMappingImpl.getItemFieldName() != null) {
                this.itemFieldNameToDef.put(implicitCollectionMappingImpl.getItemFieldName(), implicitCollectionMappingImpl);
            }
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/mapper/ImplicitCollectionMapper$ImplicitCollectionMappingImpl.class
      classes11.dex
     */
    private static class ImplicitCollectionMappingImpl implements Mapper.ImplicitCollectionMapping {
        private final String fieldName;
        private final String itemFieldName;
        private final Class itemType;
        private final String keyFieldName;

        ImplicitCollectionMappingImpl(String str, Class cls, String str2, String str3) {
            this.fieldName = str;
            this.itemFieldName = str2;
            this.itemType = cls;
            this.keyFieldName = str3;
        }

        public NamedItemType createNamedItemType() {
            return new NamedItemType(this.itemType, this.itemFieldName);
        }

        @Override // com.thoughtworks.xstream.mapper.Mapper.ImplicitCollectionMapping
        public String getFieldName() {
            return this.fieldName;
        }

        @Override // com.thoughtworks.xstream.mapper.Mapper.ImplicitCollectionMapping
        public String getItemFieldName() {
            return this.itemFieldName;
        }

        @Override // com.thoughtworks.xstream.mapper.Mapper.ImplicitCollectionMapping
        public Class getItemType() {
            return this.itemType;
        }

        @Override // com.thoughtworks.xstream.mapper.Mapper.ImplicitCollectionMapping
        public String getKeyFieldName() {
            return this.keyFieldName;
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/mapper/ImplicitCollectionMapper$NamedItemType.class
      classes11.dex
     */
    private static class NamedItemType {
        String itemFieldName;
        Class itemType;

        NamedItemType(Class cls, String str) throws Throwable {
            if (cls == null) {
                if (ImplicitCollectionMapper.class$java$lang$Object == null) {
                    cls = ImplicitCollectionMapper.class$(CommonClassNames.JAVA_LANG_OBJECT);
                    ImplicitCollectionMapper.class$java$lang$Object = cls;
                } else {
                    cls = ImplicitCollectionMapper.class$java$lang$Object;
                }
            }
            this.itemType = cls;
            this.itemFieldName = str;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof NamedItemType)) {
                return false;
            }
            NamedItemType namedItemType = (NamedItemType) obj;
            return this.itemType.equals(namedItemType.itemType) && isEquals(this.itemFieldName, namedItemType.itemFieldName);
        }

        private static boolean isEquals(Object obj, Object obj2) {
            if (obj == null) {
                return obj2 == null;
            }
            return obj.equals(obj2);
        }

        public int hashCode() {
            int iHashCode = this.itemType.hashCode() << 7;
            String str = this.itemFieldName;
            return str != null ? iHashCode + str.hashCode() : iHashCode;
        }
    }
}
