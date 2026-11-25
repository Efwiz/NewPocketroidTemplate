package org.jetbrains.kotlin.load.java.structure.impl;

import kotlin.Metadata;
import org.jetbrains.kotlin.load.java.structure.JavaLiteralAnnotationArgument;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: annotationArgumentsImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0019\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006R\u0016\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0016\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/load/java/structure/impl/JavaLiteralAnnotationArgumentImpl;", "Lorg/jetbrains/kotlin/load/java/structure/JavaLiteralAnnotationArgument;", "name", "Lorg/jetbrains/kotlin/name/Name;", "value", "", "(Lorg/jetbrains/kotlin/name/Name;Ljava/lang/Object;)V", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "getValue", "()Ljava/lang/Object;", "resolution.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavaLiteralAnnotationArgumentImpl implements JavaLiteralAnnotationArgument {
    private final Name name;
    private final Object value;

    public JavaLiteralAnnotationArgumentImpl(Name name2, Object obj) {
        this.name = name2;
        this.value = obj;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationArgument
    public Name getName() {
        return this.name;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaLiteralAnnotationArgument
    public Object getValue() {
        return this.value;
    }
}
