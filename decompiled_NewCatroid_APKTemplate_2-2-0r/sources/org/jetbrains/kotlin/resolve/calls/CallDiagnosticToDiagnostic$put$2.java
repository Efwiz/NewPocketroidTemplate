package org.jetbrains.kotlin.resolve.calls;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory1;
import org.jetbrains.kotlin.diagnostics.ParametrizedDiagnostic;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallDiagnostic;

/* JADX INFO: Add missing generic type declarations: [C, E] */
/* JADX INFO: compiled from: DiagnosticReporterImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0000\u001a\n\u0012\u0004\u0012\u0002H\u0002\u0018\u00010\u0001\"\n\b\u0000\u0010\u0002\u0018\u0001*\u00020\u0003\"\b\b\u0001\u0010\u0004*\u00020\u0005\"\b\b\u0002\u0010\u0006*\u00020\u0007*\u0002H\u00062\u0006\u0010\b\u001a\u00020\u0003H\n¢\u0006\u0004\b\t\u0010\n"}, d2 = {SpecialNames.ANONYMOUS_STRING, "Lorg/jetbrains/kotlin/diagnostics/ParametrizedDiagnostic;", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "A", "", "C", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallDiagnostic;", "it", "invoke", "(Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallDiagnostic;Lcom/intellij/psi/PsiElement;)Lorg/jetbrains/kotlin/diagnostics/ParametrizedDiagnostic;"}, k = 3, mv = {1, 9, 0}, xi = 48)
public final class CallDiagnosticToDiagnostic$put$2<C, E> extends Lambda implements Function2<C, PsiElement, ParametrizedDiagnostic<E>> {
    final /* synthetic */ DiagnosticFactory1<E, A> $factory1;
    final /* synthetic */ Function1<C, A> $getA;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public CallDiagnosticToDiagnostic$put$2(DiagnosticFactory1<E, A> diagnosticFactory1, Function1<? super C, ? extends A> function1) {
        super(2);
        this.$factory1 = diagnosticFactory1;
        this.$getA = function1;
    }

    /* JADX WARN: Incorrect types in method signature: (TC;Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;)Lorg/jetbrains/kotlin/diagnostics/ParametrizedDiagnostic<TE;>; */
    /* JADX WARN: Type inference incomplete: some casts might be missing */
    @Override // kotlin.jvm.functions.Function2
    public final ParametrizedDiagnostic invoke(KotlinCallDiagnostic checkPut, PsiElement it) {
        Intrinsics.checkNotNullParameter(checkPut, "$this$checkPut");
        Intrinsics.checkNotNullParameter(it, "it");
        Intrinsics.reifiedOperationMarker(2, ExifInterface.LONGITUDE_EAST);
        if (it != null) {
            return this.$factory1.on(it, (A) this.$getA.invoke((C) checkPut));
        }
        return null;
    }
}
