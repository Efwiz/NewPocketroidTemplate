package org.jetbrains.kotlin.incremental.classpathDiff;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ClasspathSnapshot.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\b\u0018\u00002\u00020\u0001B)\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\u0002\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000b¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/incremental/classpathDiff/JavaClassMemberLevelSnapshot;", "", "classAbiExcludingMembers", "Lorg/jetbrains/kotlin/incremental/classpathDiff/JavaElementSnapshot;", "fieldsAbi", "", "methodsAbi", "(Lorg/jetbrains/kotlin/incremental/classpathDiff/JavaElementSnapshot;Ljava/util/List;Ljava/util/List;)V", "getClassAbiExcludingMembers", "()Lorg/jetbrains/kotlin/incremental/classpathDiff/JavaElementSnapshot;", "getFieldsAbi", "()Ljava/util/List;", "getMethodsAbi", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavaClassMemberLevelSnapshot {
    private final JavaElementSnapshot classAbiExcludingMembers;
    private final List<JavaElementSnapshot> fieldsAbi;
    private final List<JavaElementSnapshot> methodsAbi;

    public JavaClassMemberLevelSnapshot(JavaElementSnapshot classAbiExcludingMembers, List<JavaElementSnapshot> fieldsAbi, List<JavaElementSnapshot> methodsAbi) {
        Intrinsics.checkNotNullParameter(classAbiExcludingMembers, "classAbiExcludingMembers");
        Intrinsics.checkNotNullParameter(fieldsAbi, "fieldsAbi");
        Intrinsics.checkNotNullParameter(methodsAbi, "methodsAbi");
        this.classAbiExcludingMembers = classAbiExcludingMembers;
        this.fieldsAbi = fieldsAbi;
        this.methodsAbi = methodsAbi;
    }

    public final JavaElementSnapshot getClassAbiExcludingMembers() {
        return this.classAbiExcludingMembers;
    }

    public final List<JavaElementSnapshot> getFieldsAbi() {
        return this.fieldsAbi;
    }

    public final List<JavaElementSnapshot> getMethodsAbi() {
        return this.methodsAbi;
    }
}
