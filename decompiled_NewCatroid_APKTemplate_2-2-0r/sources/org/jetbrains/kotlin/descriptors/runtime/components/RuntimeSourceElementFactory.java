package org.jetbrains.kotlin.descriptors.runtime.components;

import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.SourceFile;
import org.jetbrains.kotlin.descriptors.runtime.structure.ReflectJavaElement;
import org.jetbrains.kotlin.load.java.sources.JavaSourceElement;
import org.jetbrains.kotlin.load.java.sources.JavaSourceElementFactory;
import org.jetbrains.kotlin.load.java.structure.JavaElement;

/* JADX INFO: compiled from: RuntimeSourceElementFactory.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001\u0007B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/runtime/components/RuntimeSourceElementFactory;", "Lorg/jetbrains/kotlin/load/java/sources/JavaSourceElementFactory;", "()V", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/load/java/sources/JavaSourceElement;", "javaElement", "Lorg/jetbrains/kotlin/load/java/structure/JavaElement;", "RuntimeSourceElement", "descriptors.runtime"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RuntimeSourceElementFactory implements JavaSourceElementFactory {
    public static final RuntimeSourceElementFactory INSTANCE = new RuntimeSourceElementFactory();

    /* JADX INFO: compiled from: RuntimeSourceElementFactory.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\t\u001a\u00020\nH\u0016R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/runtime/components/RuntimeSourceElementFactory$RuntimeSourceElement;", "Lorg/jetbrains/kotlin/load/java/sources/JavaSourceElement;", "javaElement", "Lorg/jetbrains/kotlin/descriptors/runtime/structure/ReflectJavaElement;", "(Lorg/jetbrains/kotlin/descriptors/runtime/structure/ReflectJavaElement;)V", "getJavaElement", "()Lorg/jetbrains/kotlin/descriptors/runtime/structure/ReflectJavaElement;", "getContainingFile", "Lorg/jetbrains/kotlin/descriptors/SourceFile;", "toString", "", "descriptors.runtime"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class RuntimeSourceElement implements JavaSourceElement {
        private final ReflectJavaElement javaElement;

        public RuntimeSourceElement(ReflectJavaElement javaElement) {
            Intrinsics.checkNotNullParameter(javaElement, "javaElement");
            this.javaElement = javaElement;
        }

        @Override // org.jetbrains.kotlin.load.java.sources.JavaSourceElement
        public ReflectJavaElement getJavaElement() {
            return this.javaElement;
        }

        public String toString() {
            return getClass().getName() + ": " + getJavaElement();
        }

        @Override // org.jetbrains.kotlin.descriptors.SourceElement
        public SourceFile getContainingFile() {
            SourceFile NO_SOURCE_FILE = SourceFile.NO_SOURCE_FILE;
            Intrinsics.checkNotNullExpressionValue(NO_SOURCE_FILE, "NO_SOURCE_FILE");
            return NO_SOURCE_FILE;
        }
    }

    private RuntimeSourceElementFactory() {
    }

    @Override // org.jetbrains.kotlin.load.java.sources.JavaSourceElementFactory
    public JavaSourceElement source(JavaElement javaElement) {
        Intrinsics.checkNotNullParameter(javaElement, "javaElement");
        return new RuntimeSourceElement((ReflectJavaElement) javaElement);
    }
}
