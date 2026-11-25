package org.jetbrains.kotlin.load.java.structure.impl.classFiles;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.load.java.structure.JavaClassObjectAnnotationArgument;
import org.jetbrains.kotlin.load.java.structure.JavaType;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: Annotations.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B'\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\b\u0010\f\u001a\u00020\rH\u0016R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/PlainJavaClassObjectAnnotationArgument;", "Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/PlainJavaAnnotationArgument;", "Lorg/jetbrains/kotlin/load/java/structure/JavaClassObjectAnnotationArgument;", "name", "", ModuleXmlParser.TYPE, "Lorg/jetbrains/org/objectweb/asm/Type;", "signatureParser", "Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/BinaryClassSignatureParser;", "context", "Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/ClassifierResolutionContext;", "(Ljava/lang/String;Lorg/jetbrains/org/objectweb/asm/Type;Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/BinaryClassSignatureParser;Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/ClassifierResolutionContext;)V", "getReferencedType", "Lorg/jetbrains/kotlin/load/java/structure/JavaType;", "resolution.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PlainJavaClassObjectAnnotationArgument extends PlainJavaAnnotationArgument implements JavaClassObjectAnnotationArgument {
    private final ClassifierResolutionContext context;
    private final BinaryClassSignatureParser signatureParser;
    private final Type type;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PlainJavaClassObjectAnnotationArgument(String str, Type type, BinaryClassSignatureParser signatureParser, ClassifierResolutionContext context) {
        super(str, null);
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(signatureParser, "signatureParser");
        Intrinsics.checkNotNullParameter(context, "context");
        this.type = type;
        this.signatureParser = signatureParser;
        this.context = context;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClassObjectAnnotationArgument
    public JavaType getReferencedType() {
        return this.signatureParser.mapAsmType(this.type, this.context);
    }
}
