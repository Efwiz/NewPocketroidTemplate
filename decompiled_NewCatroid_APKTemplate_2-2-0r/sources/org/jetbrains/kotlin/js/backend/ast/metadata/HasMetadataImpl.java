package org.jetbrains.kotlin.js.backend.ast.metadata;

import androidx.exifinterface.media.ExifInterface;
import java.util.HashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;

/* JADX INFO: compiled from: HasMetadata.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010$\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0001J\u0019\u0010\r\u001a\u0002H\u000e\"\u0004\b\u0000\u0010\u000e2\u0006\u0010\u000f\u001a\u00020\u0005¢\u0006\u0002\u0010\u0010J\u0014\u0010\u0011\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0012J\u000e\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u000f\u001a\u00020\u0005J\u000e\u0010\u0015\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u0005J!\u0010\u0016\u001a\u00020\u000b\"\u0004\b\u0000\u0010\u000e2\u0006\u0010\u000f\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u0002H\u000e¢\u0006\u0002\u0010\u0018R\"\u0010\u0003\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u00048BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u001e\u0010\t\u001a\u0012\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u0006\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/js/backend/ast/metadata/HasMetadataImpl;", "Lorg/jetbrains/kotlin/js/backend/ast/metadata/HasMetadata;", "()V", "metadata", "", "", "", "getMetadata", "()Ljava/util/Map;", "metadataImpl", "copyMetadataFrom", "", "other", "getData", ExifInterface.GPS_DIRECTION_TRUE, ASTExpr.DEFAULT_MAP_KEY_NAME, "(Ljava/lang/String;)Ljava/lang/Object;", "getRawMetadata", "", "hasData", "", "removeData", "setData", "value", "(Ljava/lang/String;Ljava/lang/Object;)V", "js.ast"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class HasMetadataImpl implements HasMetadata {
    private Map<String, Object> metadataImpl;

    private final Map<String, Object> getMetadata() {
        Map<String, Object> map = this.metadataImpl;
        if (map != null) {
            return map;
        }
        HashMap map2 = new HashMap();
        this.metadataImpl = map2;
        return map2;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.metadata.HasMetadata
    public final <T> T getData(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return (T) getMetadata().get(key);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.metadata.HasMetadata
    public final <T> void setData(String key, T value) {
        Intrinsics.checkNotNullParameter(key, "key");
        getMetadata().put(key, value);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.metadata.HasMetadata
    public final boolean hasData(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        Map<String, Object> map = this.metadataImpl;
        return map != null && map.containsKey(key);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.metadata.HasMetadata
    public final void removeData(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        Map<String, Object> map = this.metadataImpl;
        if (map != null) {
            map.remove(key);
        }
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.metadata.HasMetadata
    public final void copyMetadataFrom(HasMetadata other) {
        Intrinsics.checkNotNullParameter(other, "other");
        Map<String, Object> rawMetadata = other.getRawMetadata();
        if (rawMetadata.isEmpty()) {
            return;
        }
        getMetadata().putAll(rawMetadata);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.metadata.HasMetadata
    public final Map<String, Object> getRawMetadata() {
        Map<String, Object> map = this.metadataImpl;
        return map == null ? MapsKt.emptyMap() : map;
    }
}
