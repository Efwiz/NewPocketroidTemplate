package org.jetbrains.kotlin.resolve.diagnostics;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.arguments.DevModeOverwritingStrategies;
import org.jetbrains.kotlin.diagnostics.GenericDiagnostics;
import org.jetbrains.kotlin.diagnostics.UnboundDiagnostic;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX INFO: compiled from: SimpleGenericDiagnostics.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0016\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u0013\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005¢\u0006\u0002\u0010\u0006J\u0016\u0010\t\u001a\u0010\u0012\f\u0012\n \b*\u0004\u0018\u00018\u00008\u00000\u0007H\u0016R\u001c\u0010\u0004\u001a\u0010\u0012\f\u0012\n \b*\u0004\u0018\u00018\u00008\u00000\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/resolve/diagnostics/SimpleGenericDiagnostics;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/diagnostics/UnboundDiagnostic;", "Lorg/jetbrains/kotlin/diagnostics/GenericDiagnostics;", "diagnostics", "", "(Ljava/util/Collection;)V", "Ljava/util/ArrayList;", JvmProtoBufUtil.PLATFORM_TYPE_ID, DevModeOverwritingStrategies.ALL, "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class SimpleGenericDiagnostics<T extends UnboundDiagnostic> implements GenericDiagnostics<T> {
    private final ArrayList<T> diagnostics;

    public SimpleGenericDiagnostics(Collection<? extends T> diagnostics) {
        Intrinsics.checkNotNullParameter(diagnostics, "diagnostics");
        this.diagnostics = new ArrayList<>(diagnostics);
    }

    @Override // org.jetbrains.kotlin.diagnostics.GenericDiagnostics
    public ArrayList<T> all() {
        return this.diagnostics;
    }
}
