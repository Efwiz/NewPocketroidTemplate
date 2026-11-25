package org.jetbrains.kotlin.backend.common;

import androidx.exifinterface.media.ExifInterface;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.AbstractKtSourceElement;
import org.jetbrains.kotlin.KtOffsetsOnlySourceElement;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.declarations.IrFile;

/* JADX INFO: compiled from: Utils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00006\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u001d\u0010\u0000\u001a\u0004\u0018\u0001H\u0001\"\u0004\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u0002¢\u0006\u0002\u0010\u0003\u001a\u001b\u0010\u0004\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u0002¢\u0006\u0002\u0010\u0003\u001a#\u0010\u0005\u001a\u00020\u0006\"\u0004\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00022\u0006\u0010\u0007\u001a\u0002H\u0001¢\u0006\u0002\u0010\b\u001a$\u0010\t\u001a\u00020\n*\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0010\u001a\u00020\u0011\u001a\f\u0010\u0012\u001a\u0004\u0018\u00010\u0013*\u00020\u0011¨\u0006\u0014"}, d2 = {"peek", ExifInterface.LONGITUDE_EAST, "", "(Ljava/util/List;)Ljava/lang/Object;", "pop", "push", "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "(Ljava/util/List;Ljava/lang/Object;)Z", "reportWarning", "", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", CrashHianalyticsData.MESSAGE, "", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "irElement", "Lorg/jetbrains/kotlin/ir/IrElement;", "sourceElement", "Lorg/jetbrains/kotlin/AbstractKtSourceElement;", "ir.backend.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class UtilsKt {
    public static final void reportWarning(CommonBackendContext commonBackendContext, String message, IrFile irFile, IrElement irElement) {
        Intrinsics.checkNotNullParameter(commonBackendContext, "<this>");
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(irElement, "irElement");
        commonBackendContext.report(irElement, irFile, message, false);
    }

    public static final <E> boolean push(List<E> list, E e) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        return list.add(e);
    }

    public static final <E> E pop(List<E> list) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        return list.remove(list.size() - 1);
    }

    public static final <E> E peek(List<E> list) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        if (list.size() == 0) {
            return null;
        }
        return list.get(list.size() - 1);
    }

    public static final AbstractKtSourceElement sourceElement(IrElement irElement) {
        Intrinsics.checkNotNullParameter(irElement, "<this>");
        if (irElement.getStartOffset() >= 0) {
            return new KtOffsetsOnlySourceElement(irElement.getStartOffset(), irElement.getEndOffset());
        }
        return null;
    }
}
