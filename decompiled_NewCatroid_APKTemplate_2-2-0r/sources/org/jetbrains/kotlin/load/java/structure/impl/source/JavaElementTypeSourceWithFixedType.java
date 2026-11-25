package org.jetbrains.kotlin.load.java.structure.impl.source;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;

/* JADX INFO: compiled from: JavaElementTypeSource.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0000\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u0015\u0012\u0006\u0010\u0004\u001a\u00028\u0000\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\b\u0010\r\u001a\u00020\u000eH\u0016R\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0016\u0010\u0004\u001a\u00028\u0000X\u0096\u0004¢\u0006\n\n\u0002\u0010\f\u001a\u0004\b\n\u0010\u000b¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/load/java/structure/impl/source/JavaElementTypeSourceWithFixedType;", "TYPE", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiType;", "Lorg/jetbrains/kotlin/load/java/structure/impl/source/JavaElementTypeSource;", ModuleXmlParser.TYPE, "factory", "Lorg/jetbrains/kotlin/load/java/structure/impl/source/JavaElementSourceFactory;", "(Lcom/intellij/psi/PsiType;Lorg/jetbrains/kotlin/load/java/structure/impl/source/JavaElementSourceFactory;)V", "getFactory", "()Lorg/jetbrains/kotlin/load/java/structure/impl/source/JavaElementSourceFactory;", "getType", "()Lcom/intellij/psi/PsiType;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiType;", "toString", "", "resolution.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavaElementTypeSourceWithFixedType<TYPE extends PsiType> extends JavaElementTypeSource<TYPE> {
    private final JavaElementSourceFactory factory;
    private final TYPE type;

    @Override // org.jetbrains.kotlin.load.java.structure.impl.source.JavaElementTypeSource
    public TYPE getType() {
        return this.type;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.impl.source.JavaElementTypeSource
    public JavaElementSourceFactory getFactory() {
        return this.factory;
    }

    public JavaElementTypeSourceWithFixedType(TYPE type, JavaElementSourceFactory factory) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(factory, "factory");
        this.type = type;
        this.factory = factory;
    }

    public String toString() {
        String string = getType().toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }
}
