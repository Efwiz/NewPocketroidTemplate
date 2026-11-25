package org.jetbrains.kotlin.javac.wrappers.symbols;

import com.reandroid.arsc.value.CompoundEntry;
import java.util.ArrayList;
import java.util.Collection;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.javac.JavacWrapper;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotationArgument;
import org.jetbrains.kotlin.load.java.structure.JavaArrayAnnotationArgument;
import org.jetbrains.kotlin.load.java.structure.JavaElement;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: symbolBasedAnnotationArguments.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000 \f2\u00020\u00012\u00020\u0002:\u0001\fB\u0017\b\u0004\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007R\u0014\u0010\u0005\u001a\u00020\u0006X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u0082\u0001\u0005\r\u000e\u000f\u0010\u0011¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/javac/wrappers/symbols/SymbolBasedAnnotationArgument;", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotationArgument;", "Lorg/jetbrains/kotlin/load/java/structure/JavaElement;", "name", "Lorg/jetbrains/kotlin/name/Name;", "javac", "Lorg/jetbrains/kotlin/javac/JavacWrapper;", "(Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/javac/JavacWrapper;)V", "getJavac", "()Lorg/jetbrains/kotlin/javac/JavacWrapper;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "Companion", "Lorg/jetbrains/kotlin/javac/wrappers/symbols/SymbolBasedAnnotationAsAnnotationArgument;", "Lorg/jetbrains/kotlin/javac/wrappers/symbols/SymbolBasedArrayAnnotationArgument;", "Lorg/jetbrains/kotlin/javac/wrappers/symbols/SymbolBasedClassObjectAnnotationArgument;", "Lorg/jetbrains/kotlin/javac/wrappers/symbols/SymbolBasedLiteralAnnotationArgument;", "Lorg/jetbrains/kotlin/javac/wrappers/symbols/SymbolBasedReferenceAnnotationArgument;", "javac-wrapper"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class SymbolBasedAnnotationArgument implements JavaAnnotationArgument, JavaElement {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final JavacWrapper javac;
    private final Name name;

    public /* synthetic */ SymbolBasedAnnotationArgument(Name name2, JavacWrapper javacWrapper, DefaultConstructorMarker defaultConstructorMarker) {
        this(name2, javacWrapper);
    }

    private SymbolBasedAnnotationArgument(Name name2, JavacWrapper javacWrapper) {
        this.name = name2;
        this.javac = javacWrapper;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationArgument
    public Name getName() {
        return this.name;
    }

    protected final JavacWrapper getJavac() {
        return this.javac;
    }

    /* JADX INFO: compiled from: symbolBasedAnnotationArguments.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u001e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/javac/wrappers/symbols/SymbolBasedAnnotationArgument$Companion;", "", "()V", "arrayAnnotationArguments", "Lorg/jetbrains/kotlin/load/java/structure/JavaArrayAnnotationArgument;", CompoundEntry.NAME_values, "", "name", "Lorg/jetbrains/kotlin/name/Name;", "javac", "Lorg/jetbrains/kotlin/javac/JavacWrapper;", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotationArgument;", "value", "javac-wrapper"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final JavaAnnotationArgument create(Object value, Name name2, JavacWrapper javac) {
            Intrinsics.checkNotNullParameter(value, "value");
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(javac, "javac");
            if (value instanceof AnnotationMirror) {
                return new SymbolBasedAnnotationAsAnnotationArgument((AnnotationMirror) value, name2, javac);
            }
            if (value instanceof VariableElement) {
                return new SymbolBasedReferenceAnnotationArgument((VariableElement) value, name2, javac);
            }
            if (value instanceof TypeMirror) {
                return new SymbolBasedClassObjectAnnotationArgument((TypeMirror) value, name2, javac);
            }
            if (value instanceof Collection) {
                return arrayAnnotationArguments((Collection) value, name2, javac);
            }
            if (!(value instanceof AnnotationValue)) {
                return new SymbolBasedLiteralAnnotationArgument(value, name2, javac);
            }
            Object value2 = ((AnnotationValue) value).getValue();
            Intrinsics.checkNotNullExpressionValue(value2, "getValue(...)");
            return create(value2, name2, javac);
        }

        private final JavaArrayAnnotationArgument arrayAnnotationArguments(Collection<?> values, Name name2, JavacWrapper javac) {
            Collection<?> collection = values;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
            for (Object obj : collection) {
                Companion companion = SymbolBasedAnnotationArgument.INSTANCE;
                Intrinsics.checkNotNull(obj);
                arrayList.add(companion.create(obj, name2, javac));
            }
            return new SymbolBasedArrayAnnotationArgument(arrayList, name2, javac);
        }
    }
}
