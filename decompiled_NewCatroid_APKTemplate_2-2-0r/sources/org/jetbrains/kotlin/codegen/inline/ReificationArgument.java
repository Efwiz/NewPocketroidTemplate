package org.jetbrains.kotlin.codegen.inline;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.bytebuddy.description.type.TypeDescription;

/* JADX INFO: compiled from: ReifiedTypeInliner.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u000b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0006\u0010\u000f\u001a\u00020\u0003J\u000e\u0010\u0010\u001a\u00020\u00002\u0006\u0010\u0011\u001a\u00020\u0000R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/ReificationArgument;", "", "parameterName", "", "nullable", "", "arrayDepth", "", "(Ljava/lang/String;ZI)V", "getArrayDepth", "()I", "getNullable", "()Z", "getParameterName", "()Ljava/lang/String;", "asString", "combine", "replacement", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ReificationArgument {
    private final int arrayDepth;
    private final boolean nullable;
    private final String parameterName;

    public ReificationArgument(String parameterName, boolean z, int i) {
        Intrinsics.checkNotNullParameter(parameterName, "parameterName");
        this.parameterName = parameterName;
        this.nullable = z;
        this.arrayDepth = i;
    }

    public final int getArrayDepth() {
        return this.arrayDepth;
    }

    public final boolean getNullable() {
        return this.nullable;
    }

    public final String getParameterName() {
        return this.parameterName;
    }

    public final String asString() {
        return StringsKt.repeat("[", this.arrayDepth) + this.parameterName + (this.nullable ? TypeDescription.Generic.OfWildcardType.SYMBOL : "");
    }

    public final ReificationArgument combine(ReificationArgument replacement) {
        Intrinsics.checkNotNullParameter(replacement, "replacement");
        return new ReificationArgument(replacement.parameterName, this.nullable || (replacement.nullable && this.arrayDepth == 0), this.arrayDepth + replacement.arrayDepth);
    }
}
