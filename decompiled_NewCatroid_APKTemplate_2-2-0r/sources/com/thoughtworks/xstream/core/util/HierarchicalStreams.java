package com.thoughtworks.xstream.core.util;

import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.mapper.Mapper;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/core/util/HierarchicalStreams.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class HierarchicalStreams {
    public static Class readClassType(HierarchicalStreamReader hierarchicalStreamReader, Mapper mapper) {
        String classAttribute = readClassAttribute(hierarchicalStreamReader, mapper);
        if (classAttribute == null) {
            return mapper.realClass(hierarchicalStreamReader.getNodeName());
        }
        return mapper.realClass(classAttribute);
    }

    public static String readClassAttribute(HierarchicalStreamReader hierarchicalStreamReader, Mapper mapper) {
        String strAliasForSystemAttribute;
        String strAliasForSystemAttribute2 = mapper.aliasForSystemAttribute("resolves-to");
        String attribute = strAliasForSystemAttribute2 == null ? null : hierarchicalStreamReader.getAttribute(strAliasForSystemAttribute2);
        return (attribute != null || (strAliasForSystemAttribute = mapper.aliasForSystemAttribute("class")) == null) ? attribute : hierarchicalStreamReader.getAttribute(strAliasForSystemAttribute);
    }
}
