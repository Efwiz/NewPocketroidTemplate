package org.jetbrains.kotlin.resolve.multiplatform;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner;

/* JADX INFO: compiled from: ExpectActualAnnotationsIncompatibilityType.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00012\u00020\u0002:\u0002\u000b\fB\u0007\b\u0004¢\u0006\u0002\u0010\u0003J&\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\b0\u0000\"\u0004\b\u0001\u0010\b2\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u0002H\b0\nR\u0012\u0010\u0004\u001a\u00028\u0000X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006\u0082\u0001\u0002\r\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualAnnotationsIncompatibilityType;", "A", "", "()V", "expectAnnotation", "getExpectAnnotation", "()Ljava/lang/Object;", "mapAnnotationType", "A2", "mapper", "Lkotlin/Function1;", "DifferentOnActual", "MissingOnActual", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualAnnotationsIncompatibilityType$DifferentOnActual;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualAnnotationsIncompatibilityType$MissingOnActual;", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ExpectActualAnnotationsIncompatibilityType<A> {
    public /* synthetic */ ExpectActualAnnotationsIncompatibilityType(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract A getExpectAnnotation();

    private ExpectActualAnnotationsIncompatibilityType() {
    }

    /* JADX INFO: compiled from: ExpectActualAnnotationsIncompatibilityType.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000*\u0006\b\u0001\u0010\u0001 \u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00028\u0001¢\u0006\u0002\u0010\u0004R\u0016\u0010\u0003\u001a\u00028\u0001X\u0096\u0004¢\u0006\n\n\u0002\u0010\u0007\u001a\u0004\b\u0005\u0010\u0006¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualAnnotationsIncompatibilityType$MissingOnActual;", "A", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualAnnotationsIncompatibilityType;", "expectAnnotation", ReifiedTypeInliner.pluginIntrinsicsMarkerSignature, "getExpectAnnotation", "()Ljava/lang/Object;", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class MissingOnActual<A> extends ExpectActualAnnotationsIncompatibilityType<A> {
        private final A expectAnnotation;

        @Override // org.jetbrains.kotlin.resolve.multiplatform.ExpectActualAnnotationsIncompatibilityType
        public A getExpectAnnotation() {
            return this.expectAnnotation;
        }

        public MissingOnActual(A a) {
            super(null);
            this.expectAnnotation = a;
        }
    }

    /* JADX INFO: compiled from: ExpectActualAnnotationsIncompatibilityType.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000*\u0006\b\u0001\u0010\u0001 \u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00028\u0001\u0012\u0006\u0010\u0004\u001a\u00028\u0001¢\u0006\u0002\u0010\u0005R\u0013\u0010\u0004\u001a\u00028\u0001¢\u0006\n\n\u0002\u0010\b\u001a\u0004\b\u0006\u0010\u0007R\u0016\u0010\u0003\u001a\u00028\u0001X\u0096\u0004¢\u0006\n\n\u0002\u0010\b\u001a\u0004\b\t\u0010\u0007¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualAnnotationsIncompatibilityType$DifferentOnActual;", "A", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualAnnotationsIncompatibilityType;", "expectAnnotation", "actualAnnotation", "(Ljava/lang/Object;Ljava/lang/Object;)V", "getActualAnnotation", "()Ljava/lang/Object;", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, "getExpectAnnotation", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class DifferentOnActual<A> extends ExpectActualAnnotationsIncompatibilityType<A> {
        private final A actualAnnotation;
        private final A expectAnnotation;

        @Override // org.jetbrains.kotlin.resolve.multiplatform.ExpectActualAnnotationsIncompatibilityType
        public A getExpectAnnotation() {
            return this.expectAnnotation;
        }

        public final A getActualAnnotation() {
            return this.actualAnnotation;
        }

        public DifferentOnActual(A a, A a2) {
            super(null);
            this.expectAnnotation = a;
            this.actualAnnotation = a2;
        }
    }

    public final <A2> ExpectActualAnnotationsIncompatibilityType<A2> mapAnnotationType(Function1<? super A, ? extends A2> mapper) {
        Intrinsics.checkNotNullParameter(mapper, "mapper");
        if (this instanceof MissingOnActual) {
            return new MissingOnActual(mapper.invoke(getExpectAnnotation()));
        }
        if (this instanceof DifferentOnActual) {
            return new DifferentOnActual(mapper.invoke(getExpectAnnotation()), mapper.invoke((Object) ((DifferentOnActual) this).getActualAnnotation()));
        }
        throw new NoWhenBranchMatchedException();
    }
}
