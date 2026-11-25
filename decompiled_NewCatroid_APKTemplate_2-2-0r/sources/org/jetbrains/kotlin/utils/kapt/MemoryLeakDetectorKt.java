package org.jetbrains.kotlin.utils.kapt;

import java.lang.reflect.Field;
import java.util.Vector;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.AnnotatedConstruct;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.utils.ReflectionUtilKt;

/* JADX INFO: compiled from: MemoryLeakDetector.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\f\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0002\u001a\f\u0010\u0003\u001a\u00020\u0001*\u00020\u0002H\u0002\u001a\u0016\u0010\u0004\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00060\u0005*\u00020\u0007H\u0002¨\u0006\b"}, d2 = {"isJavacComponent", "", "", "isPrimitiveOrString", "loadedClasses", "Ljava/util/Vector;", "Ljava/lang/Class;", "Ljava/lang/ClassLoader;", "util"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class MemoryLeakDetectorKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isJavacComponent(Object obj) {
        if (obj instanceof Processor ? true : obj instanceof ProcessingEnvironment ? true : obj instanceof RoundEnvironment ? true : obj instanceof Filer ? true : obj instanceof Messager ? true : obj instanceof Elements ? true : obj instanceof Types) {
            return true;
        }
        return obj instanceof AnnotatedConstruct;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isPrimitiveOrString(Object obj) {
        if (obj instanceof Boolean ? true : obj instanceof Byte ? true : obj instanceof Short ? true : obj instanceof Integer ? true : obj instanceof Long ? true : obj instanceof Character ? true : obj instanceof Float ? true : obj instanceof Double ? true : obj instanceof Void) {
            return true;
        }
        return obj instanceof String;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Vector<Class<?>> loadedClasses(ClassLoader classLoader) {
        try {
            Field declaredField = ClassLoader.class.getDeclaredField("classes");
            Intrinsics.checkNotNull(declaredField);
            Object safe = ReflectionUtilKt.getSafe(declaredField, classLoader);
            Vector<Class<?>> vector = safe instanceof Vector ? (Vector) safe : null;
            return vector == null ? new Vector<>() : vector;
        } catch (Throwable unused) {
            return new Vector<>();
        }
    }
}
