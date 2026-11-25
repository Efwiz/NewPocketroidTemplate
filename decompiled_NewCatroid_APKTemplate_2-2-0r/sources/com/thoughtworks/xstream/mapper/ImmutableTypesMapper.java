package com.thoughtworks.xstream.mapper;

import java.util.HashSet;
import java.util.Set;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/mapper/ImmutableTypesMapper.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class ImmutableTypesMapper extends MapperWrapper {
    private final Set immutableTypes;
    private final Set unreferenceableTypes;

    public ImmutableTypesMapper(Mapper mapper) {
        super(mapper);
        this.unreferenceableTypes = new HashSet();
        this.immutableTypes = new HashSet();
    }

    public void addImmutableType(Class cls) {
        addImmutableType(cls, true);
    }

    public void addImmutableType(Class cls, boolean z) {
        this.immutableTypes.add(cls);
        if (!z) {
            this.unreferenceableTypes.add(cls);
        } else {
            this.unreferenceableTypes.remove(cls);
        }
    }

    @Override // com.thoughtworks.xstream.mapper.MapperWrapper, com.thoughtworks.xstream.mapper.Mapper
    public boolean isImmutableValueType(Class cls) {
        if (this.immutableTypes.contains(cls)) {
            return true;
        }
        return super.isImmutableValueType(cls);
    }

    @Override // com.thoughtworks.xstream.mapper.MapperWrapper, com.thoughtworks.xstream.mapper.Mapper
    public boolean isReferenceable(Class cls) {
        if (this.unreferenceableTypes.contains(cls)) {
            return false;
        }
        return super.isReferenceable(cls);
    }
}
