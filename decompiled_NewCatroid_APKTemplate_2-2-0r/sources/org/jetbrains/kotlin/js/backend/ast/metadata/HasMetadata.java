package org.jetbrains.kotlin.js.backend.ast.metadata;

import androidx.exifinterface.media.ExifInterface;
import java.util.Map;
import kotlin.Metadata;
import org.antlr.stringtemplate.language.ASTExpr;

/* JADX INFO: compiled from: HasMetadata.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010$\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0000H&J\u001b\u0010\u0005\u001a\u0002H\u0006\"\u0004\b\u0000\u0010\u00062\u0006\u0010\u0007\u001a\u00020\bH&¢\u0006\u0002\u0010\tJ\u0016\u0010\n\u001a\u0010\u0012\u0004\u0012\u00020\b\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u000bH&J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u0007\u001a\u00020\bH&J\u0010\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH&J#\u0010\u000f\u001a\u00020\u0003\"\u0004\b\u0000\u0010\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0010\u001a\u0002H\u0006H&¢\u0006\u0002\u0010\u0011ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0012À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/js/backend/ast/metadata/HasMetadata;", "", "copyMetadataFrom", "", "other", "getData", ExifInterface.GPS_DIRECTION_TRUE, ASTExpr.DEFAULT_MAP_KEY_NAME, "", "(Ljava/lang/String;)Ljava/lang/Object;", "getRawMetadata", "", "hasData", "", "removeData", "setData", "value", "(Ljava/lang/String;Ljava/lang/Object;)V", "js.ast"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface HasMetadata {
    void copyMetadataFrom(HasMetadata other);

    <T> T getData(String key);

    Map<String, Object> getRawMetadata();

    boolean hasData(String key);

    void removeData(String key);

    <T> void setData(String key, T value);
}
