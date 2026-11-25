package org.jetbrains.kotlin.javac.wrappers.symbols;

import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.PrimitiveType;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.javac.JavacWrapper;
import org.jetbrains.kotlin.load.java.structure.JavaPrimitiveType;
import org.jetbrains.kotlin.resolve.jvm.JvmPrimitiveType;

/* JADX INFO: compiled from: symbolBasedTypes.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\u0015\u0012\u0006\u0010\u0004\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007R\u0016\u0010\b\u001a\u0004\u0018\u00010\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/javac/wrappers/symbols/SymbolBasedPrimitiveType;", "Lorg/jetbrains/kotlin/javac/wrappers/symbols/SymbolBasedType;", "Ljavax/lang/model/type/TypeMirror;", "Lorg/jetbrains/kotlin/load/java/structure/JavaPrimitiveType;", "typeMirror", "javac", "Lorg/jetbrains/kotlin/javac/JavacWrapper;", "(Ljavax/lang/model/type/TypeMirror;Lorg/jetbrains/kotlin/javac/JavacWrapper;)V", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/builtins/PrimitiveType;", "getType", "()Lorg/jetbrains/kotlin/builtins/PrimitiveType;", "javac-wrapper"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SymbolBasedPrimitiveType extends SymbolBasedType<TypeMirror> implements JavaPrimitiveType {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SymbolBasedPrimitiveType(TypeMirror typeMirror, JavacWrapper javac) {
        super(typeMirror, javac, null);
        Intrinsics.checkNotNullParameter(typeMirror, "typeMirror");
        Intrinsics.checkNotNullParameter(javac, "javac");
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaPrimitiveType
    public PrimitiveType getType() {
        if (getTypeMirror().getKind() == TypeKind.VOID) {
            return null;
        }
        return JvmPrimitiveType.get(getTypeMirror().toString()).getPrimitiveType();
    }
}
