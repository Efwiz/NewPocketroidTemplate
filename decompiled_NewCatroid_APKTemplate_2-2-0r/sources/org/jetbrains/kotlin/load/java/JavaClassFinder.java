package org.jetbrains.kotlin.load.java;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.JavaPackage;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: JavaClassFinder.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001:\u0001\u0015J\b\u0010\u0002\u001a\u00020\u0003H&J\u0012\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&J\u0012\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\b\u001a\u00020\tH\u0016J\u0016\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\u000b2\u0006\u0010\u0006\u001a\u00020\u0007H&J\u0016\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\u000b2\u0006\u0010\b\u001a\u00020\tH\u0016J\u001c\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\u000f2\b\b\u0002\u0010\u0010\u001a\u00020\u0003H&J\u0018\u0010\u0011\u001a\n\u0012\u0004\u0012\u00020\u0013\u0018\u00010\u00122\u0006\u0010\u0014\u001a\u00020\u000fH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0016À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/load/java/JavaClassFinder;", "", "canComputeKnownClassNamesInPackage", "", "findClass", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "request", "Lorg/jetbrains/kotlin/load/java/JavaClassFinder$Request;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "findClasses", "", "findPackage", "Lorg/jetbrains/kotlin/load/java/structure/JavaPackage;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "mayHaveAnnotations", "knownClassNamesInPackage", "", "", "packageFqName", "Request", "compiler.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface JavaClassFinder {
    boolean canComputeKnownClassNamesInPackage();

    JavaClass findClass(Request request);

    List<JavaClass> findClasses(Request request);

    JavaPackage findPackage(FqName fqName, boolean mayHaveAnnotations);

    Set<String> knownClassNamesInPackage(FqName packageFqName);

    /* JADX INFO: compiled from: JavaClassFinder.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u0010\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u0010\u0011\u001a\u0004\u0018\u00010\u0007HÆ\u0003J+\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001J\t\u0010\u0018\u001a\u00020\u0019HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/load/java/JavaClassFinder$Request;", "", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "previouslyFoundClassFileContent", "", "outerClass", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "(Lorg/jetbrains/kotlin/name/ClassId;[BLorg/jetbrains/kotlin/load/java/structure/JavaClass;)V", "getClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", "getOuterClass", "()Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "getPreviouslyFoundClassFileContent", "()[B", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "compiler.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class Request {
        private final ClassId classId;
        private final JavaClass outerClass;
        private final byte[] previouslyFoundClassFileContent;

        public static /* synthetic */ Request copy$default(Request request, ClassId classId, byte[] bArr, JavaClass javaClass, int i, Object obj) {
            if ((i & 1) != 0) {
                classId = request.classId;
            }
            if ((i & 2) != 0) {
                bArr = request.previouslyFoundClassFileContent;
            }
            if ((i & 4) != 0) {
                javaClass = request.outerClass;
            }
            return request.copy(classId, bArr, javaClass);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final ClassId getClassId() {
            return this.classId;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final byte[] getPreviouslyFoundClassFileContent() {
            return this.previouslyFoundClassFileContent;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final JavaClass getOuterClass() {
            return this.outerClass;
        }

        public final Request copy(ClassId classId, byte[] previouslyFoundClassFileContent, JavaClass outerClass) {
            Intrinsics.checkNotNullParameter(classId, "classId");
            return new Request(classId, previouslyFoundClassFileContent, outerClass);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Request)) {
                return false;
            }
            Request request = (Request) other;
            return Intrinsics.areEqual(this.classId, request.classId) && Intrinsics.areEqual(this.previouslyFoundClassFileContent, request.previouslyFoundClassFileContent) && Intrinsics.areEqual(this.outerClass, request.outerClass);
        }

        public int hashCode() {
            int iHashCode = this.classId.hashCode() * 31;
            byte[] bArr = this.previouslyFoundClassFileContent;
            int iHashCode2 = (iHashCode + (bArr == null ? 0 : Arrays.hashCode(bArr))) * 31;
            JavaClass javaClass = this.outerClass;
            return iHashCode2 + (javaClass != null ? javaClass.hashCode() : 0);
        }

        public String toString() {
            return "Request(classId=" + this.classId + ", previouslyFoundClassFileContent=" + Arrays.toString(this.previouslyFoundClassFileContent) + ", outerClass=" + this.outerClass + ')';
        }

        public Request(ClassId classId, byte[] bArr, JavaClass javaClass) {
            Intrinsics.checkNotNullParameter(classId, "classId");
            this.classId = classId;
            this.previouslyFoundClassFileContent = bArr;
            this.outerClass = javaClass;
        }

        public /* synthetic */ Request(ClassId classId, byte[] bArr, JavaClass javaClass, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(classId, (i & 2) != 0 ? null : bArr, (i & 4) != 0 ? null : javaClass);
        }

        public final ClassId getClassId() {
            return this.classId;
        }

        public final byte[] getPreviouslyFoundClassFileContent() {
            return this.previouslyFoundClassFileContent;
        }

        public final JavaClass getOuterClass() {
            return this.outerClass;
        }
    }

    default JavaClass findClass(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        return findClass(new Request(classId, null, null, 6, null));
    }

    default List<JavaClass> findClasses(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        return findClasses(new Request(classId, null, null, 6, null));
    }

    static /* synthetic */ JavaPackage findPackage$default(JavaClassFinder javaClassFinder, FqName fqName, boolean z, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: findPackage");
        }
        if ((i & 2) != 0) {
            z = true;
        }
        return javaClassFinder.findPackage(fqName, z);
    }
}
