package org.jetbrains.kotlin.codegen;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.description.method.ParameterDescription;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedValueArgument;

/* JADX INFO: compiled from: ArgumentGenerator.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/codegen/ArgumentAndDeclIndex;", "", ParameterDescription.NAME_PREFIX, "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedValueArgument;", "declIndex", "", "(Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedValueArgument;I)V", "getArg", "()Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedValueArgument;", "getDeclIndex", "()I", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ArgumentAndDeclIndex {
    private final ResolvedValueArgument arg;
    private final int declIndex;

    public ArgumentAndDeclIndex(ResolvedValueArgument arg, int i) {
        Intrinsics.checkNotNullParameter(arg, "arg");
        this.arg = arg;
        this.declIndex = i;
    }

    public final ResolvedValueArgument getArg() {
        return this.arg;
    }

    public final int getDeclIndex() {
        return this.declIndex;
    }
}
