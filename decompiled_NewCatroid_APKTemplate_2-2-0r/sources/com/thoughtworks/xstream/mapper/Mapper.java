package com.thoughtworks.xstream.mapper;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.SingleValueConverter;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/mapper/Mapper.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public interface Mapper {

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/mapper/Mapper$ImplicitCollectionMapping.class
      classes11.dex
     */
    public interface ImplicitCollectionMapping {
        String getFieldName();

        String getItemFieldName();

        Class getItemType();

        String getKeyFieldName();
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/mapper/Mapper$Null.class
      classes11.dex
     */
    public static class Null {
    }

    String aliasForAttribute(Class cls, String str);

    String aliasForAttribute(String str);

    String aliasForSystemAttribute(String str);

    String attributeForAlias(Class cls, String str);

    String attributeForAlias(String str);

    Class defaultImplementationOf(Class cls);

    SingleValueConverter getConverterFromAttribute(Class cls, String str);

    SingleValueConverter getConverterFromAttribute(Class cls, String str, Class cls2);

    SingleValueConverter getConverterFromAttribute(String str);

    SingleValueConverter getConverterFromItemType(Class cls);

    SingleValueConverter getConverterFromItemType(String str, Class cls);

    SingleValueConverter getConverterFromItemType(String str, Class cls, Class cls2);

    String getFieldNameForItemTypeAndName(Class cls, Class cls2, String str);

    ImplicitCollectionMapping getImplicitCollectionDefForFieldName(Class cls, String str);

    Class getItemTypeForItemFieldName(Class cls, String str);

    Converter getLocalConverter(Class cls, String str);

    boolean isIgnoredElement(String str);

    boolean isImmutableValueType(Class cls);

    boolean isReferenceable(Class cls);

    Mapper lookupMapperOfType(Class cls);

    Class realClass(String str);

    String realMember(Class cls, String str);

    String serializedClass(Class cls);

    String serializedMember(Class cls, String str);

    boolean shouldSerializeMember(Class cls, String str);
}
