package org.jetbrains.kotlin.ir.backend.js.lower;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.lower.InventNamesForLocalClasses;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.backend.js.utils.NameTablesKt;
import org.jetbrains.kotlin.ir.declarations.IrAttributeContainer;
import org.jetbrains.kotlin.ir.declarations.IrClass;

/* JADX INFO: compiled from: JsInventNamesForLocalClasses.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0014J\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0006H\u0014J\u0010\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u0006H\u0014R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/JsInventNamesForLocalClasses;", "Lorg/jetbrains/kotlin/backend/common/lower/InventNamesForLocalClasses;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;)V", "computeTopLevelClassName", "", "clazz", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "putLocalClassName", "", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrAttributeContainer;", "localClassName", "sanitizeNameIfNeeded", "name", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsInventNamesForLocalClasses extends InventNamesForLocalClasses {
    private final JsIrBackendContext context;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JsInventNamesForLocalClasses(JsIrBackendContext context) {
        super(true, false, 2, null);
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.InventNamesForLocalClasses
    protected String computeTopLevelClassName(IrClass clazz) {
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        String string = clazz.getName().toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.InventNamesForLocalClasses
    protected String sanitizeNameIfNeeded(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return NameTablesKt.sanitizeName(name2, false);
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.InventNamesForLocalClasses
    protected void putLocalClassName(IrAttributeContainer declaration, String localClassName) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(localClassName, "localClassName");
        if (declaration instanceof IrClass) {
            this.context.getLocalClassNames().put(declaration, localClassName);
        }
    }
}
