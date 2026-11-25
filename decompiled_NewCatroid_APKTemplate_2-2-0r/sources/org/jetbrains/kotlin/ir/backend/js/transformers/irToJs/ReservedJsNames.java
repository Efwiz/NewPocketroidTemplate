package org.jetbrains.kotlin.ir.backend.js.transformers.irToJs;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;

/* JADX INFO: compiled from: ReservedJsNames.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/ReservedJsNames;", "", "()V", "Companion", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ReservedJsNames {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    /* JADX INFO: compiled from: ReservedJsNames.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u0006\u0010\u0007\u001a\u00020\u0006J\u0006\u0010\b\u001a\u00020\u0006¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/ReservedJsNames$Companion;", "", "()V", "makeCrossModuleNameRef", "Lorg/jetbrains/kotlin/js/backend/ast/JsNameRef;", "moduleName", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "makeInternalModuleName", "makeJsExporterName", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final JsName makeInternalModuleName() {
            return new JsName("_", false);
        }

        public final JsName makeJsExporterName() {
            return new JsName("$jsExportAll$", false);
        }

        public final JsNameRef makeCrossModuleNameRef(JsName moduleName) {
            Intrinsics.checkNotNullParameter(moduleName, "moduleName");
            return new JsNameRef("$_$", moduleName.makeRef());
        }
    }
}
