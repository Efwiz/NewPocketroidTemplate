package com.thoughtworks.xstream.mapper;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/mapper/AttributeAliasingMapper.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class AttributeAliasingMapper extends AbstractAttributeAliasingMapper {
    public AttributeAliasingMapper(Mapper mapper) {
        super(mapper);
    }

    @Override // com.thoughtworks.xstream.mapper.MapperWrapper, com.thoughtworks.xstream.mapper.Mapper
    public String aliasForAttribute(String str) {
        String str2 = (String) this.nameToAlias.get(str);
        return str2 == null ? super.aliasForAttribute(str) : str2;
    }

    @Override // com.thoughtworks.xstream.mapper.MapperWrapper, com.thoughtworks.xstream.mapper.Mapper
    public String attributeForAlias(String str) {
        String str2 = (String) this.aliasToName.get(str);
        return str2 == null ? super.attributeForAlias(str) : str2;
    }
}
