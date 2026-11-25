package org.jetbrains.kotlin.javac.wrappers.symbols;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.javac.JavacWrapper;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotationArgument;
import org.jetbrains.kotlin.load.java.structure.JavaArrayAnnotationArgument;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: symbolBasedAnnotationArguments.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u00012\u00020\u0002B#\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u000e\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u0016R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/javac/wrappers/symbols/SymbolBasedArrayAnnotationArgument;", "Lorg/jetbrains/kotlin/javac/wrappers/symbols/SymbolBasedAnnotationArgument;", "Lorg/jetbrains/kotlin/load/java/structure/JavaArrayAnnotationArgument;", "args", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotationArgument;", "name", "Lorg/jetbrains/kotlin/name/Name;", "javac", "Lorg/jetbrains/kotlin/javac/JavacWrapper;", "(Ljava/util/List;Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/javac/JavacWrapper;)V", "getArgs", "()Ljava/util/List;", "getElements", "javac-wrapper"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SymbolBasedArrayAnnotationArgument extends SymbolBasedAnnotationArgument implements JavaArrayAnnotationArgument {
    private final List<JavaAnnotationArgument> args;

    public final List<JavaAnnotationArgument> getArgs() {
        return this.args;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public SymbolBasedArrayAnnotationArgument(List<? extends JavaAnnotationArgument> args, Name name2, JavacWrapper javac) {
        super(name2, javac, null);
        Intrinsics.checkNotNullParameter(args, "args");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(javac, "javac");
        this.args = args;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaArrayAnnotationArgument
    public List<JavaAnnotationArgument> getElements() {
        return this.args;
    }
}
