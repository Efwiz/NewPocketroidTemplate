package org.jetbrains.kotlin.descriptors.runtime.structure;

import java.lang.annotation.Annotation;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotationArgument;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: ReflectJavaAnnotationArguments.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000 \u00072\u00020\u0001:\u0001\u0007B\u0011\b\u0004\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004R\u0016\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u0082\u0001\u0005\b\t\n\u000b\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/runtime/structure/ReflectJavaAnnotationArgument;", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotationArgument;", "name", "Lorg/jetbrains/kotlin/name/Name;", "(Lorg/jetbrains/kotlin/name/Name;)V", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "Factory", "Lorg/jetbrains/kotlin/descriptors/runtime/structure/ReflectJavaAnnotationAsAnnotationArgument;", "Lorg/jetbrains/kotlin/descriptors/runtime/structure/ReflectJavaArrayAnnotationArgument;", "Lorg/jetbrains/kotlin/descriptors/runtime/structure/ReflectJavaClassObjectAnnotationArgument;", "Lorg/jetbrains/kotlin/descriptors/runtime/structure/ReflectJavaEnumValueAnnotationArgument;", "Lorg/jetbrains/kotlin/descriptors/runtime/structure/ReflectJavaLiteralAnnotationArgument;", "descriptors.runtime"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ReflectJavaAnnotationArgument implements JavaAnnotationArgument {

    /* JADX INFO: renamed from: Factory, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final Name name;

    public /* synthetic */ ReflectJavaAnnotationArgument(Name name2, DefaultConstructorMarker defaultConstructorMarker) {
        this(name2);
    }

    private ReflectJavaAnnotationArgument(Name name2) {
        this.name = name2;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationArgument
    public Name getName() {
        return this.name;
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.descriptors.runtime.structure.ReflectJavaAnnotationArgument$Factory, reason: from kotlin metadata */
    /* JADX INFO: compiled from: ReflectJavaAnnotationArguments.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/runtime/structure/ReflectJavaAnnotationArgument$Factory;", "", "()V", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/descriptors/runtime/structure/ReflectJavaAnnotationArgument;", "value", "name", "Lorg/jetbrains/kotlin/name/Name;", "descriptors.runtime"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final ReflectJavaAnnotationArgument create(Object value, Name name2) {
            Intrinsics.checkNotNullParameter(value, "value");
            if (ReflectClassUtilKt.isEnumClassOrSpecializedEnumEntryClass(value.getClass())) {
                return new ReflectJavaEnumValueAnnotationArgument(name2, (Enum) value);
            }
            if (value instanceof Annotation) {
                return new ReflectJavaAnnotationAsAnnotationArgument(name2, (Annotation) value);
            }
            if (value instanceof Object[]) {
                return new ReflectJavaArrayAnnotationArgument(name2, (Object[]) value);
            }
            return value instanceof Class ? new ReflectJavaClassObjectAnnotationArgument(name2, (Class) value) : new ReflectJavaLiteralAnnotationArgument(name2, value);
        }
    }
}
