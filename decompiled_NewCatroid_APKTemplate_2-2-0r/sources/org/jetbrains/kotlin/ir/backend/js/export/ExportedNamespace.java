package org.jetbrains.kotlin.ir.backend.js.export;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ExportModel.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005¢\u0006\u0002\u0010\u0006R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedNamespace;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedDeclaration;", "name", "", "declarations", "", "(Ljava/lang/String;Ljava/util/List;)V", "getDeclarations", "()Ljava/util/List;", "getName", "()Ljava/lang/String;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ExportedNamespace extends ExportedDeclaration {
    private final List<ExportedDeclaration> declarations;
    private final String name;

    public final String getName() {
        return this.name;
    }

    public final List<ExportedDeclaration> getDeclarations() {
        return this.declarations;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public ExportedNamespace(String name2, List<? extends ExportedDeclaration> declarations) {
        super(null);
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(declarations, "declarations");
        this.name = name2;
        this.declarations = declarations;
    }
}
