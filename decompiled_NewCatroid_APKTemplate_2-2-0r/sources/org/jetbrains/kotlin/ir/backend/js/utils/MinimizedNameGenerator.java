package org.jetbrains.kotlin.ir.backend.js.utils;

import java.util.HashMap;
import java.util.HashSet;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.org.jline.reader.LineReader;

/* JADX INFO: compiled from: MinimizedNameGenerator.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\r\u001a\u00020\u000eJ\u0006\u0010\u000f\u001a\u00020\u0005J\u000e\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0005J\u000e\u0010\u0013\u001a\u00020\u00052\u0006\u0010\u0012\u001a\u00020\u0005J\u000e\u0010\u0014\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\u0005R*\u0010\u0003\u001a\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0004j\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005`\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u0010\t\u001a\u0012\u0012\u0004\u0012\u00020\u00050\nj\b\u0012\u0004\u0012\u00020\u0005`\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\f\u001a\u0012\u0012\u0004\u0012\u00020\u00050\nj\b\u0012\u0004\u0012\u00020\u0005`\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/utils/MinimizedNameGenerator;", "", "()V", "functionSignatureToName", "Ljava/util/HashMap;", "", "Lkotlin/collections/HashMap;", "index", "", "keptNames", "Ljava/util/HashSet;", "Lkotlin/collections/HashSet;", "reservedNames", LineReader.CLEAR, "", "generateNextName", "keepName", "", "signature", "nameBySignature", "reserveName", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MinimizedNameGenerator {
    private int index;
    private final HashMap<String, String> functionSignatureToName = new HashMap<>();
    private final HashSet<String> reservedNames = new HashSet<>();
    private final HashSet<String> keptNames = new HashSet<>();

    public final String generateNextName() {
        int i = this.index;
        this.index = i + 1;
        String jsIdentifier = NameTablesKt.toJsIdentifier(i);
        while (this.reservedNames.contains(jsIdentifier)) {
            int i2 = this.index;
            this.index = i2 + 1;
            jsIdentifier = NameTablesKt.toJsIdentifier(i2);
        }
        return jsIdentifier;
    }

    public final String nameBySignature(String signature) {
        Intrinsics.checkNotNullParameter(signature, "signature");
        if (this.keptNames.contains(signature)) {
            return signature;
        }
        HashMap<String, String> map = this.functionSignatureToName;
        String strGenerateNextName = map.get(signature);
        if (strGenerateNextName == null) {
            strGenerateNextName = generateNextName();
            map.put(signature, strGenerateNextName);
        }
        return strGenerateNextName;
    }

    public final boolean keepName(String signature) {
        Intrinsics.checkNotNullParameter(signature, "signature");
        return this.keptNames.add(signature);
    }

    public final void reserveName(String signature) {
        Intrinsics.checkNotNullParameter(signature, "signature");
        this.reservedNames.add(signature);
    }

    public final void clear() {
        this.index = 0;
        this.functionSignatureToName.clear();
        this.reservedNames.clear();
    }
}
