package org.jetbrains.kotlin.ir.backend.js.transformers.irToJs;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.serialization.js.ModuleKind;

/* JADX INFO: compiled from: CompilationOutputs.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u001a\f\u0010\u0005\u001a\u00020\u0001*\u00020\u0006H\u0002\u001a\u0014\u0010\u0007\u001a\u00020\b*\u00020\u00062\u0006\u0010\t\u001a\u00020\u0006H\u0002\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\n"}, d2 = {SchemaSymbols.ATTVAL_EXTENSION, "", "Lorg/jetbrains/kotlin/serialization/js/ModuleKind;", "getExtension", "(Lorg/jetbrains/kotlin/serialization/js/ModuleKind;)Ljava/lang/String;", "asSourceMappingUrl", "Ljava/io/File;", "copyModificationTimeFrom", "", TypedValues.TransitionType.S_FROM, "backend.js"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CompilationOutputsKt {

    /* JADX INFO: compiled from: CompilationOutputs.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ModuleKind.values().length];
            try {
                iArr[ModuleKind.ES.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static final String getExtension(ModuleKind moduleKind) {
        Intrinsics.checkNotNullParameter(moduleKind, "<this>");
        if (WhenMappings.$EnumSwitchMapping$0[moduleKind.ordinal()] == 1) {
            return ".mjs";
        }
        return ".js";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void copyModificationTimeFrom(File file, File file2) {
        long jLastModified = file2.lastModified();
        if (jLastModified > 0) {
            file.setLastModified(jLastModified);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String asSourceMappingUrl(File file) {
        return "\n//# sourceMappingURL=" + file.getName() + '\n';
    }
}
