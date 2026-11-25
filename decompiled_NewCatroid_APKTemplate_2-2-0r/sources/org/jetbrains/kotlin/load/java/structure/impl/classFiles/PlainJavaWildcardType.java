package org.jetbrains.kotlin.load.java.structure.impl.classFiles;

import kotlin.Metadata;
import org.jetbrains.kotlin.load.java.structure.JavaType;
import org.jetbrains.kotlin.load.java.structure.JavaWildcardType;

/* JADX INFO: compiled from: Types.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u0017\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007R\u0016\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/PlainJavaWildcardType;", "Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/JavaPlainType;", "Lorg/jetbrains/kotlin/load/java/structure/JavaWildcardType;", "bound", "Lorg/jetbrains/kotlin/load/java/structure/JavaType;", "isExtends", "", "(Lorg/jetbrains/kotlin/load/java/structure/JavaType;Z)V", "getBound", "()Lorg/jetbrains/kotlin/load/java/structure/JavaType;", "()Z", "resolution.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PlainJavaWildcardType extends JavaPlainType implements JavaWildcardType {
    private final JavaType bound;
    private final boolean isExtends;

    public PlainJavaWildcardType(JavaType javaType, boolean z) {
        this.bound = javaType;
        this.isExtends = z;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaWildcardType
    public JavaType getBound() {
        return this.bound;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaWildcardType
    /* JADX INFO: renamed from: isExtends, reason: from getter */
    public boolean getIsExtends() {
        return this.isExtends;
    }
}
