package org.jf.dexlib2.writer.builder;

import java.util.List;
import org.jf.dexlib2.base.reference.BaseCallSiteReference;
import org.jf.dexlib2.iface.value.StringEncodedValue;
import org.jf.util.collection.EmptyList;

/* JADX INFO: loaded from: classes6.dex */
public class BuilderCallSiteReference extends BaseCallSiteReference implements BuilderReference {
    final BuilderEncodedValues.BuilderArrayEncodedValue encodedCallSite;
    int index = -1;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    final String f1034name;

    public BuilderCallSiteReference(String str, BuilderEncodedValues.BuilderArrayEncodedValue builderArrayEncodedValue) {
        this.f1034name = str;
        this.encodedCallSite = builderArrayEncodedValue;
    }

    @Override // org.jf.dexlib2.iface.reference.CallSiteReference
    public String getName() {
        return this.f1034name;
    }

    @Override // org.jf.dexlib2.iface.reference.CallSiteReference
    public BuilderMethodHandleReference getMethodHandle() {
        return ((BuilderEncodedValues.BuilderMethodHandleEncodedValue) this.encodedCallSite.elements.get(0)).getValue();
    }

    @Override // org.jf.dexlib2.iface.reference.CallSiteReference
    public String getMethodName() {
        return ((StringEncodedValue) this.encodedCallSite.elements.get(1)).getValue();
    }

    @Override // org.jf.dexlib2.iface.reference.CallSiteReference
    public BuilderMethodProtoReference getMethodProto() {
        return ((BuilderEncodedValues.BuilderMethodTypeEncodedValue) this.encodedCallSite.elements.get(2)).getValue();
    }

    @Override // org.jf.dexlib2.iface.reference.CallSiteReference
    public List<? extends BuilderEncodedValues.BuilderEncodedValue> getExtraArguments() {
        if (this.encodedCallSite.elements.size() <= 3) {
            return EmptyList.of();
        }
        return this.encodedCallSite.elements.subList(3, this.encodedCallSite.elements.size());
    }

    @Override // org.jf.dexlib2.writer.builder.BuilderReference
    public int getIndex() {
        return this.index;
    }

    @Override // org.jf.dexlib2.writer.builder.BuilderReference
    public void setIndex(int i) {
        this.index = i;
    }
}
