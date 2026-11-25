package org.jetbrains.kotlin.util.slicedMap;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/* JADX INFO: loaded from: classes6.dex */
public class BasicWritableSlice<K, V> extends AbstractWritableSlice<K, V> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private String debugName;
    private final boolean isCollective;
    private final RewritePolicy rewritePolicy;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "debugName", "org/jetbrains/kotlin/util/slicedMap/BasicWritableSlice", "setDebugName"));
    }

    @Override // org.jetbrains.kotlin.util.slicedMap.WritableSlice
    public void afterPut(MutableSlicedMap mutableSlicedMap, K k, V v) {
    }

    @Override // org.jetbrains.kotlin.util.slicedMap.WritableSlice
    public boolean check(K k, V v) {
        return true;
    }

    public V computeValue(SlicedMap slicedMap, K k, V v, boolean z) {
        return v;
    }

    public static Void initSliceDebugNames(Class<?> cls) {
        for (Field field : cls.getFields()) {
            if (Modifier.isStatic(field.getModifiers())) {
                try {
                    Object obj = field.get(null);
                    if (obj instanceof BasicWritableSlice) {
                        ((BasicWritableSlice) obj).debugName = field.getName();
                    } else {
                        continue;
                    }
                } catch (IllegalAccessException e) {
                    throw new IllegalStateException(e);
                }
            }
        }
        return null;
    }

    public BasicWritableSlice(RewritePolicy rewritePolicy) {
        this(rewritePolicy, false);
    }

    public BasicWritableSlice(RewritePolicy rewritePolicy, boolean z) {
        super("<BasicWritableSlice>");
        this.rewritePolicy = rewritePolicy;
        this.isCollective = z;
    }

    @Override // org.jetbrains.kotlin.util.slicedMap.WritableSlice
    public RewritePolicy getRewritePolicy() {
        return this.rewritePolicy;
    }

    @Override // org.jetbrains.kotlin.util.slicedMap.WritableSlice
    public boolean isCollective() {
        return this.isCollective;
    }

    public void setDebugName(String str) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        if (this.debugName != null) {
            throw new IllegalStateException("Debug name already set for " + this);
        }
        this.debugName = str;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Key
    public String toString() {
        return this.debugName;
    }

    @Override // org.jetbrains.kotlin.util.slicedMap.ReadOnlySlice
    public ReadOnlySlice<K, V> makeRawValueVersion() {
        return new DelegatingSlice<K, V>(this) { // from class: org.jetbrains.kotlin.util.slicedMap.BasicWritableSlice.1
            static final /* synthetic */ boolean $assertionsDisabled = false;

            @Override // org.jetbrains.kotlin.util.slicedMap.DelegatingSlice, org.jetbrains.kotlin.util.slicedMap.ReadOnlySlice
            public V computeValue(SlicedMap slicedMap, K k, V v, boolean z) {
                return v;
            }
        };
    }
}
