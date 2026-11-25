package org.jf.dexlib2.writer.builder;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.jf.dexlib2.iface.reference.CallSiteReference;
import org.jf.dexlib2.writer.CallSiteSection;
import org.jf.dexlib2.writer.util.CallSiteUtil;

/* JADX INFO: loaded from: classes6.dex */
public class BuilderCallSitePool extends BaseBuilderPool implements CallSiteSection<BuilderCallSiteReference, BuilderEncodedValues.BuilderArrayEncodedValue> {
    private final ConcurrentMap<CallSiteReference, BuilderCallSiteReference> internedItems;

    public BuilderCallSitePool(DexBuilder dexBuilder) {
        super(dexBuilder);
        this.internedItems = new ConcurrentHashMap();
    }

    public BuilderCallSiteReference internCallSite(CallSiteReference callSiteReference) {
        BuilderCallSiteReference builderCallSiteReference = this.internedItems.get(callSiteReference);
        if (builderCallSiteReference != null) {
            return builderCallSiteReference;
        }
        BuilderCallSiteReference builderCallSiteReference2 = new BuilderCallSiteReference(callSiteReference.getName(), ((BuilderEncodedArrayPool) this.dexBuilder.encodedArraySection).internArrayEncodedValue(CallSiteUtil.getEncodedCallSite(callSiteReference)));
        BuilderCallSiteReference builderCallSiteReferencePutIfAbsent = this.internedItems.putIfAbsent(builderCallSiteReference2, builderCallSiteReference2);
        return builderCallSiteReferencePutIfAbsent == null ? builderCallSiteReference2 : builderCallSiteReferencePutIfAbsent;
    }

    @Override // org.jf.dexlib2.writer.CallSiteSection
    public BuilderEncodedValues.BuilderArrayEncodedValue getEncodedCallSite(BuilderCallSiteReference builderCallSiteReference) {
        return builderCallSiteReference.encodedCallSite;
    }

    @Override // org.jf.dexlib2.writer.IndexSection
    public int getItemIndex(BuilderCallSiteReference builderCallSiteReference) {
        return builderCallSiteReference.index;
    }

    @Override // org.jf.dexlib2.writer.IndexSection
    public Collection<? extends Map.Entry<? extends BuilderCallSiteReference, Integer>> getItems() {
        return new BuilderMapEntryCollection<BuilderCallSiteReference>(this.internedItems.values()) { // from class: org.jf.dexlib2.writer.builder.BuilderCallSitePool.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // org.jf.dexlib2.writer.builder.BuilderMapEntryCollection
            public int getValue(BuilderCallSiteReference builderCallSiteReference) {
                return builderCallSiteReference.index;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // org.jf.dexlib2.writer.builder.BuilderMapEntryCollection
            public int setValue(BuilderCallSiteReference builderCallSiteReference, int i) {
                int i2 = builderCallSiteReference.index;
                builderCallSiteReference.index = i;
                return i2;
            }
        };
    }

    @Override // org.jf.dexlib2.writer.IndexSection
    public int getItemCount() {
        return this.internedItems.size();
    }
}
