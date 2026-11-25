package org.jetbrains.kotlin.backend.jvm.codegen;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.org.objectweb.asm.Label;

/* JADX INFO: compiled from: ExpressionCodegen.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/codegen/Gap;", "", "start", "Lorg/jetbrains/org/objectweb/asm/Label;", "end", "(Lorg/jetbrains/org/objectweb/asm/Label;Lorg/jetbrains/org/objectweb/asm/Label;)V", "getEnd", "()Lorg/jetbrains/org/objectweb/asm/Label;", "getStart", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Gap {
    private final Label end;
    private final Label start;

    public Gap(Label start, Label end) {
        Intrinsics.checkNotNullParameter(start, "start");
        Intrinsics.checkNotNullParameter(end, "end");
        this.start = start;
        this.end = end;
    }

    public final Label getEnd() {
        return this.end;
    }

    public final Label getStart() {
        return this.start;
    }
}
