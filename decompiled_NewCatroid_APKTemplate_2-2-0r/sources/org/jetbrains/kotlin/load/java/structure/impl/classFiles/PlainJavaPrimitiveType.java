package org.jetbrains.kotlin.load.java.structure.impl.classFiles;

import kotlin.Metadata;
import org.jetbrains.kotlin.builtins.PrimitiveType;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.load.java.structure.JavaPrimitiveType;

/* JADX INFO: compiled from: Types.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u000f\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004¢\u0006\u0002\u0010\u0005R\u0016\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/PlainJavaPrimitiveType;", "Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/JavaPlainType;", "Lorg/jetbrains/kotlin/load/java/structure/JavaPrimitiveType;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/builtins/PrimitiveType;", "(Lorg/jetbrains/kotlin/builtins/PrimitiveType;)V", "getType", "()Lorg/jetbrains/kotlin/builtins/PrimitiveType;", "resolution.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PlainJavaPrimitiveType extends JavaPlainType implements JavaPrimitiveType {
    private final PrimitiveType type;

    public PlainJavaPrimitiveType(PrimitiveType primitiveType) {
        this.type = primitiveType;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaPrimitiveType
    public PrimitiveType getType() {
        return this.type;
    }
}
