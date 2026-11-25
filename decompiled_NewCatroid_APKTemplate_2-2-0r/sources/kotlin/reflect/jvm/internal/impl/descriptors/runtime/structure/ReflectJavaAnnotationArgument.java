package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import java.lang.annotation.Annotation;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaAnnotationArgument;
import kotlin.reflect.jvm.internal.impl.name.Name;

/* JADX INFO: compiled from: ReflectJavaAnnotationArguments.kt */
/* JADX INFO: loaded from: classes12.dex */
public abstract class ReflectJavaAnnotationArgument implements JavaAnnotationArgument {
    public static final Factory Factory = new Factory(null);

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private final Name f304name;

    public /* synthetic */ ReflectJavaAnnotationArgument(Name name2, DefaultConstructorMarker defaultConstructorMarker) {
        this(name2);
    }

    private ReflectJavaAnnotationArgument(Name name2) {
        this.f304name = name2;
    }

    @Override // kotlin.reflect.jvm.internal.impl.load.java.structure.JavaAnnotationArgument
    public Name getName() {
        return this.f304name;
    }

    /* JADX INFO: compiled from: ReflectJavaAnnotationArguments.kt */
    public static final class Factory {
        public /* synthetic */ Factory(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Factory() {
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
