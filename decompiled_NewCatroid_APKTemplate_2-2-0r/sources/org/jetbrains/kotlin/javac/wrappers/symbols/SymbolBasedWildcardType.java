package org.jetbrains.kotlin.javac.wrappers.symbols;

import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.WildcardType;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.javac.JavacWrapper;
import org.jetbrains.kotlin.load.java.structure.JavaType;
import org.jetbrains.kotlin.load.java.structure.JavaWildcardType;

/* JADX INFO: compiled from: symbolBasedTypes.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\u0015\u0012\u0006\u0010\u0004\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007R\u0016\u0010\b\u001a\u0004\u0018\u00010\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/javac/wrappers/symbols/SymbolBasedWildcardType;", "Lorg/jetbrains/kotlin/javac/wrappers/symbols/SymbolBasedType;", "Ljavax/lang/model/type/WildcardType;", "Lorg/jetbrains/kotlin/load/java/structure/JavaWildcardType;", "typeMirror", "javac", "Lorg/jetbrains/kotlin/javac/JavacWrapper;", "(Ljavax/lang/model/type/WildcardType;Lorg/jetbrains/kotlin/javac/JavacWrapper;)V", "bound", "Lorg/jetbrains/kotlin/load/java/structure/JavaType;", "getBound", "()Lorg/jetbrains/kotlin/load/java/structure/JavaType;", "isExtends", "", "()Z", "javac-wrapper"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SymbolBasedWildcardType extends SymbolBasedType<WildcardType> implements JavaWildcardType {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SymbolBasedWildcardType(WildcardType typeMirror, JavacWrapper javac) {
        super((TypeMirror) typeMirror, javac, null);
        Intrinsics.checkNotNullParameter(typeMirror, "typeMirror");
        Intrinsics.checkNotNullParameter(javac, "javac");
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaWildcardType
    public JavaType getBound() {
        TypeMirror extendsBound = getTypeMirror().getExtendsBound();
        if (extendsBound == null) {
            extendsBound = getTypeMirror().getSuperBound();
        }
        return extendsBound != null ? SymbolBasedType.INSTANCE.create(extendsBound, getJavac()) : null;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaWildcardType
    /* JADX INFO: renamed from: isExtends */
    public boolean getIsExtends() {
        return getTypeMirror().getExtendsBound() != null;
    }
}
