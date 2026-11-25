package org.jetbrains.kotlin.serialization.js;

import androidx.exifinterface.media.ExifInterface;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: JsModuleDescriptor.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\f\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00012\u00020\u0002B+\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\b\u0012\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0002\u0010\nR\u0013\u0010\t\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\r\u001a\u0004\b\u000b\u0010\fR\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\b¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/serialization/js/JsModuleDescriptor;", ExifInterface.GPS_DIRECTION_TRUE, "", "name", "", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/serialization/js/ModuleKind;", "imported", "", "data", "(Ljava/lang/String;Lorg/jetbrains/kotlin/serialization/js/ModuleKind;Ljava/util/List;Ljava/lang/Object;)V", "getData", "()Ljava/lang/Object;", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, "getImported", "()Ljava/util/List;", "getKind", "()Lorg/jetbrains/kotlin/serialization/js/ModuleKind;", "getName", "()Ljava/lang/String;", "js.serializer"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsModuleDescriptor<T> {
    private final T data;
    private final List<String> imported;
    private final ModuleKind kind;
    private final String name;

    public JsModuleDescriptor(String name2, ModuleKind kind, List<String> imported, T t) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(kind, "kind");
        Intrinsics.checkNotNullParameter(imported, "imported");
        this.name = name2;
        this.kind = kind;
        this.imported = imported;
        this.data = t;
    }

    public final String getName() {
        return this.name;
    }

    public final ModuleKind getKind() {
        return this.kind;
    }

    public final List<String> getImported() {
        return this.imported;
    }

    public final T getData() {
        return this.data;
    }
}
