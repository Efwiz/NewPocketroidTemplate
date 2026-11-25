package org.jetbrains.kotlin.ir.backend.js.dce;

import java.util.HashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;

/* JADX INFO: compiled from: DceDumpNameCache.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\t\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\bR\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/dce/DceDumpNameCache;", "", "()V", "indexCache", "", "", "", "nameCache", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "getOrPut", "declaration", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DceDumpNameCache {
    private final Map<IrDeclaration, String> nameCache = new HashMap();
    private final Map<String, Integer> indexCache = new HashMap();

    public final String getOrPut(IrDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Map<IrDeclaration, String> map = this.nameCache;
        String strFqNameForDceDump = map.get(declaration);
        if (strFqNameForDceDump == null) {
            strFqNameForDceDump = UtilsKt.fqNameForDceDump(declaration);
            int iIntValue = this.indexCache.getOrDefault(strFqNameForDceDump, 0).intValue();
            this.indexCache.put(strFqNameForDceDump, Integer.valueOf(iIntValue + 1));
            if (iIntValue != 0) {
                strFqNameForDceDump = strFqNameForDceDump + " (" + iIntValue + ')';
            }
            map.put(declaration, strFqNameForDceDump);
        }
        return strFqNameForDceDump;
    }
}
