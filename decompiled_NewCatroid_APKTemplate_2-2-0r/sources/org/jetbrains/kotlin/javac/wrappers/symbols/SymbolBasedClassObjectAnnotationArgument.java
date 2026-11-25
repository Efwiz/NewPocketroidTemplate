package org.jetbrains.kotlin.javac.wrappers.symbols;

import javax.lang.model.type.TypeMirror;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.javac.JavacWrapper;
import org.jetbrains.kotlin.load.java.structure.JavaClassObjectAnnotationArgument;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: symbolBasedAnnotationArguments.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u001d\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u000e\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00040\u000bH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/javac/wrappers/symbols/SymbolBasedClassObjectAnnotationArgument;", "Lorg/jetbrains/kotlin/javac/wrappers/symbols/SymbolBasedAnnotationArgument;", "Lorg/jetbrains/kotlin/load/java/structure/JavaClassObjectAnnotationArgument;", ModuleXmlParser.TYPE, "Ljavax/lang/model/type/TypeMirror;", "name", "Lorg/jetbrains/kotlin/name/Name;", "javac", "Lorg/jetbrains/kotlin/javac/JavacWrapper;", "(Ljavax/lang/model/type/TypeMirror;Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/javac/JavacWrapper;)V", "getReferencedType", "Lorg/jetbrains/kotlin/javac/wrappers/symbols/SymbolBasedType;", "javac-wrapper"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SymbolBasedClassObjectAnnotationArgument extends SymbolBasedAnnotationArgument implements JavaClassObjectAnnotationArgument {
    private final TypeMirror type;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SymbolBasedClassObjectAnnotationArgument(TypeMirror type, Name name2, JavacWrapper javac) {
        super(name2, javac, null);
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(javac, "javac");
        this.type = type;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClassObjectAnnotationArgument
    public SymbolBasedType<TypeMirror> getReferencedType() {
        return SymbolBasedType.INSTANCE.create(this.type, getJavac());
    }
}
