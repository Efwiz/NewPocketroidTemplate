package org.jetbrains.kotlin.util.slicedMap;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;

/* JADX INFO: compiled from: SetSlice.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u0000 \u0013*\u0004\b\u0000\u0010\u00012\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u00020\u00030\u0002:\u0001\u0013B\u0019\b\u0007\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003¢\u0006\u0002\u0010\u0007J\u001f\u0010\b\u001a\u00020\u00032\u0006\u0010\t\u001a\u00028\u00002\b\u0010\n\u001a\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0002\u0010\u000bJ3\u0010\f\u001a\u0004\u0018\u00010\u00032\b\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\t\u001a\u00028\u00002\b\u0010\n\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u000f\u001a\u00020\u0003H\u0016¢\u0006\u0002\u0010\u0010J\u0016\u0010\u0011\u001a\u0010\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u0012H\u0016¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/util/slicedMap/SetSlice;", "K", "Lorg/jetbrains/kotlin/util/slicedMap/BasicWritableSlice;", "", "rewritePolicy", "Lorg/jetbrains/kotlin/util/slicedMap/RewritePolicy;", "isCollective", "(Lorg/jetbrains/kotlin/util/slicedMap/RewritePolicy;Z)V", "check", ASTExpr.DEFAULT_MAP_KEY_NAME, "value", "(Ljava/lang/Object;Ljava/lang/Boolean;)Z", "computeValue", "map", "Lorg/jetbrains/kotlin/util/slicedMap/SlicedMap;", "valueNotFound", "(Lorg/jetbrains/kotlin/util/slicedMap/SlicedMap;Ljava/lang/Object;Ljava/lang/Boolean;Z)Ljava/lang/Boolean;", "makeRawValueVersion", "Lorg/jetbrains/kotlin/util/slicedMap/ReadOnlySlice;", "Companion", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class SetSlice<K> extends BasicWritableSlice<K, Boolean> {
    public static final boolean DEFAULT = false;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public SetSlice(RewritePolicy rewritePolicy) {
        this(rewritePolicy, false, 2, null);
        Intrinsics.checkNotNullParameter(rewritePolicy, "rewritePolicy");
    }

    public /* synthetic */ SetSlice(RewritePolicy rewritePolicy, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(rewritePolicy, (i & 2) != 0 ? false : z);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SetSlice(RewritePolicy rewritePolicy, boolean z) {
        super(rewritePolicy, z);
        Intrinsics.checkNotNullParameter(rewritePolicy, "rewritePolicy");
    }

    @Override // org.jetbrains.kotlin.util.slicedMap.BasicWritableSlice, org.jetbrains.kotlin.util.slicedMap.WritableSlice
    public boolean check(K key, Boolean value) {
        return !Intrinsics.areEqual(value, Boolean.valueOf(DEFAULT));
    }

    @Override // org.jetbrains.kotlin.util.slicedMap.BasicWritableSlice, org.jetbrains.kotlin.util.slicedMap.ReadOnlySlice
    public Boolean computeValue(SlicedMap map, K key, Boolean value, boolean valueNotFound) {
        Boolean bool = (Boolean) super.computeValue(map, (Object) key, value, valueNotFound);
        return bool == null ? Boolean.valueOf(DEFAULT) : bool;
    }

    @Override // org.jetbrains.kotlin.util.slicedMap.BasicWritableSlice, org.jetbrains.kotlin.util.slicedMap.ReadOnlySlice
    public ReadOnlySlice<K, Boolean> makeRawValueVersion() {
        return new DelegatingSlice<K, Boolean>(this) { // from class: org.jetbrains.kotlin.util.slicedMap.SetSlice.makeRawValueVersion.1
            {
                super(this);
            }

            @Override // org.jetbrains.kotlin.util.slicedMap.DelegatingSlice, org.jetbrains.kotlin.util.slicedMap.ReadOnlySlice
            public Boolean computeValue(SlicedMap map, K key, Boolean value, boolean valueNotFound) {
                Intrinsics.checkNotNullParameter(map, "map");
                return valueNotFound ? Boolean.valueOf(SetSlice.DEFAULT) : value;
            }
        };
    }
}
