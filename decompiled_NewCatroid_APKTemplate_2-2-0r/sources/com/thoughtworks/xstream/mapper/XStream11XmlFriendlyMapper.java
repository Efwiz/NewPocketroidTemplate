package com.thoughtworks.xstream.mapper;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/mapper/XStream11XmlFriendlyMapper.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class XStream11XmlFriendlyMapper extends AbstractXmlFriendlyMapper {
    public XStream11XmlFriendlyMapper(Mapper mapper) {
        super(mapper);
    }

    @Override // com.thoughtworks.xstream.mapper.MapperWrapper, com.thoughtworks.xstream.mapper.Mapper
    public Class realClass(String str) {
        return super.realClass(unescapeClassName(str));
    }

    @Override // com.thoughtworks.xstream.mapper.MapperWrapper, com.thoughtworks.xstream.mapper.Mapper
    public String realMember(Class cls, String str) {
        return unescapeFieldName(super.realMember(cls, str));
    }

    public String mapNameFromXML(String str) {
        return unescapeFieldName(str);
    }
}
