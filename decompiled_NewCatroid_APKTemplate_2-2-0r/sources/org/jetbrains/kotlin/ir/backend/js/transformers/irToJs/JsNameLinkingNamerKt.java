package org.jetbrains.kotlin.ir.backend.js.transformers.irToJs;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.ir.backend.js.utils.NameTablesKt;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;

/* JADX INFO: compiled from: JsNameLinkingNamer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\u0002\u001a\f\u0010\u0004\u001a\u00020\u0005*\u00020\u0006H\u0002¨\u0006\u0007"}, d2 = {"makeRef", "Lorg/jetbrains/kotlin/js/backend/ast/JsNameRef;", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "safeName", "", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "backend.js"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JsNameLinkingNamerKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final String safeName(IrField irField) {
        String strAsString = irField.getName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        String strSanitizeName$default = NameTablesKt.sanitizeName$default(strAsString, false, 2, null);
        Character chLastOrNull = StringsKt.lastOrNull(strSanitizeName$default);
        Intrinsics.checkNotNull(chLastOrNull);
        return Character.isDigit(chLastOrNull.charValue()) ? strSanitizeName$default + '_' : strSanitizeName$default;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final JsNameRef makeRef(List<? extends JsName> list) {
        JsNameRef jsNameRefMakeRef = list.get(0).makeRef();
        Intrinsics.checkNotNullExpressionValue(jsNameRefMakeRef, "makeRef(...)");
        int size = list.size();
        int i = 1;
        while (i < size) {
            JsNameRef jsNameRef = new JsNameRef(list.get(i), jsNameRefMakeRef);
            i++;
            jsNameRefMakeRef = jsNameRef;
        }
        return jsNameRefMakeRef;
    }
}
