package org.jetbrains.kotlin.js.backend.ast.metadata;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KProperty;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner;
import org.jetbrains.kotlin.js.backend.ast.metadata.HasMetadata;

/* JADX INFO: compiled from: MetadataProperty.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u0000*\n\b\u0000\u0010\u0001 \u0000*\u00020\u0002*\u0004\b\u0001\u0010\u00032\u00020\u0004B\r\u0012\u0006\u0010\u0005\u001a\u00028\u0001¢\u0006\u0002\u0010\u0006J\"\u0010\n\u001a\u00028\u00012\u0006\u0010\u000b\u001a\u00028\u00002\n\u0010\f\u001a\u0006\u0012\u0002\b\u00030\rH\u0086\u0002¢\u0006\u0002\u0010\u000eJ*\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u000b\u001a\u00028\u00002\n\u0010\f\u001a\u0006\u0012\u0002\b\u00030\r2\u0006\u0010\u0011\u001a\u00028\u0001H\u0086\u0002¢\u0006\u0002\u0010\u0012R\u0013\u0010\u0005\u001a\u00028\u0001¢\u0006\n\n\u0002\u0010\t\u001a\u0004\b\u0007\u0010\b¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/js/backend/ast/metadata/MetadataProperty;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/js/backend/ast/metadata/HasMetadata;", "R", "", "default", ReifiedTypeInliner.pluginIntrinsicsMarkerSignature, "getDefault", "()Ljava/lang/Object;", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, "getValue", "thisRef", "desc", "Lkotlin/reflect/KProperty;", "(Lorg/jetbrains/kotlin/js/backend/ast/metadata/HasMetadata;Lkotlin/reflect/KProperty;)Ljava/lang/Object;", "setValue", "", "value", "(Lorg/jetbrains/kotlin/js/backend/ast/metadata/HasMetadata;Lkotlin/reflect/KProperty;Ljava/lang/Object;)V", "js.ast"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MetadataProperty<T extends HasMetadata, R> {
    private final R default;

    public MetadataProperty(R r) {
        this.default = r;
    }

    public final R getDefault() {
        return this.default;
    }

    public final R getValue(T thisRef, KProperty<?> desc) {
        Intrinsics.checkNotNullParameter(thisRef, "thisRef");
        Intrinsics.checkNotNullParameter(desc, "desc");
        return !thisRef.hasData(desc.getName()) ? this.default : (R) thisRef.getData(desc.getName());
    }

    public final void setValue(T thisRef, KProperty<?> desc, R value) {
        Intrinsics.checkNotNullParameter(thisRef, "thisRef");
        Intrinsics.checkNotNullParameter(desc, "desc");
        if (Intrinsics.areEqual(value, this.default)) {
            thisRef.removeData(desc.getName());
        } else {
            thisRef.setData(desc.getName(), value);
        }
    }
}
