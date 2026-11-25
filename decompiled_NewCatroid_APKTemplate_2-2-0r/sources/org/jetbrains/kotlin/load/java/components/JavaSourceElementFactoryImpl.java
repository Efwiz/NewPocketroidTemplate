package org.jetbrains.kotlin.load.java.components;

import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.load.java.sources.JavaSourceElement;
import org.jetbrains.kotlin.load.java.sources.JavaSourceElementFactory;
import org.jetbrains.kotlin.load.java.structure.JavaElement;

/* JADX INFO: compiled from: JavaSourceElementFactoryImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/load/java/components/JavaSourceElementFactoryImpl;", "Lorg/jetbrains/kotlin/load/java/sources/JavaSourceElementFactory;", "()V", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/load/java/sources/JavaSourceElement;", "javaElement", "Lorg/jetbrains/kotlin/load/java/structure/JavaElement;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavaSourceElementFactoryImpl implements JavaSourceElementFactory {
    @Override // org.jetbrains.kotlin.load.java.sources.JavaSourceElementFactory
    public JavaSourceElement source(JavaElement javaElement) {
        Intrinsics.checkNotNullParameter(javaElement, "javaElement");
        return new JavaSourceElementImpl(javaElement);
    }
}
