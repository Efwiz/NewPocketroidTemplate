package org.jetbrains.kotlin.checkers.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.diagnostics.DiagnosticWithParameters1;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.konan.library.NativeLibraryConstantsKt;

/* JADX INFO: compiled from: ActualDiagnostic.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0011\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u0001H\u0096\u0002J\u0010\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0013\u0010\u001e\u001a\u00020\u00072\b\u0010\u001b\u001a\u0004\u0018\u00010\u001fH\u0096\u0002J\b\u0010 \u001a\u00020\u001aH\u0016J\b\u0010!\u001a\u00020\u0005H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\f8F¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\u0010X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0015\u001a\u00020\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u0016\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0017¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/checkers/diagnostics/ActualDiagnostic;", "Lorg/jetbrains/kotlin/checkers/diagnostics/AbstractTestDiagnostic;", "diagnostic", "Lorg/jetbrains/kotlin/diagnostics/Diagnostic;", NativeLibraryConstantsKt.KONAN_DISTRIBUTION_PLATFORM_LIBS_DIR, "", "withNewInference", "", "(Lorg/jetbrains/kotlin/diagnostics/Diagnostic;Ljava/lang/String;Z)V", "getDiagnostic", "()Lorg/jetbrains/kotlin/diagnostics/Diagnostic;", "file", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiFile;", "getFile", "()Lcom/intellij/psi/PsiFile;", "inferenceCompatibility", "Lorg/jetbrains/kotlin/checkers/diagnostics/TextDiagnostic$InferenceCompatibility;", "getInferenceCompatibility", "()Lorg/jetbrains/kotlin/checkers/diagnostics/TextDiagnostic$InferenceCompatibility;", "setInferenceCompatibility", "(Lorg/jetbrains/kotlin/checkers/diagnostics/TextDiagnostic$InferenceCompatibility;)V", "name", "getName", "()Ljava/lang/String;", "getPlatform", "compareTo", "", "other", "enhanceInferenceCompatibility", "", Namer.EQUALS_METHOD_NAME, "", "hashCode", "toString", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ActualDiagnostic implements AbstractTestDiagnostic {
    private final Diagnostic diagnostic;
    private TextDiagnostic.InferenceCompatibility inferenceCompatibility;
    private final String platform;

    public ActualDiagnostic(Diagnostic diagnostic, String str, boolean z) {
        TextDiagnostic.InferenceCompatibility inferenceCompatibility;
        Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
        this.diagnostic = diagnostic;
        this.platform = str;
        if (z) {
            inferenceCompatibility = TextDiagnostic.InferenceCompatibility.NEW;
        } else {
            inferenceCompatibility = TextDiagnostic.InferenceCompatibility.OLD;
        }
        this.inferenceCompatibility = inferenceCompatibility;
    }

    public final Diagnostic getDiagnostic() {
        return this.diagnostic;
    }

    @Override // org.jetbrains.kotlin.checkers.diagnostics.AbstractTestDiagnostic
    public String getPlatform() {
        return this.platform;
    }

    @Override // org.jetbrains.kotlin.checkers.diagnostics.AbstractTestDiagnostic
    public TextDiagnostic.InferenceCompatibility getInferenceCompatibility() {
        return this.inferenceCompatibility;
    }

    public void setInferenceCompatibility(TextDiagnostic.InferenceCompatibility inferenceCompatibility) {
        Intrinsics.checkNotNullParameter(inferenceCompatibility, "<set-?>");
        this.inferenceCompatibility = inferenceCompatibility;
    }

    @Override // org.jetbrains.kotlin.checkers.diagnostics.AbstractTestDiagnostic
    public String getName() {
        return this.diagnostic.getFactory().getName();
    }

    public final PsiFile getFile() {
        return this.diagnostic.getPsiFile();
    }

    @Override // java.lang.Comparable
    public int compareTo(AbstractTestDiagnostic other) {
        Intrinsics.checkNotNullParameter(other, "other");
        Diagnostic diagnostic = this.diagnostic;
        if ((diagnostic instanceof DiagnosticWithParameters1) && (other instanceof ActualDiagnostic)) {
            ActualDiagnostic actualDiagnostic = (ActualDiagnostic) other;
            if (actualDiagnostic.diagnostic instanceof DiagnosticWithParameters1) {
                return (getName() + ((DiagnosticWithParameters1) this.diagnostic).getA()).compareTo(other.getName() + ((DiagnosticWithParameters1) actualDiagnostic.diagnostic).getA());
            }
        }
        if (diagnostic instanceof DiagnosticWithParameters1) {
            return (getName() + ((DiagnosticWithParameters1) this.diagnostic).getA()).compareTo(other.getName());
        }
        if (other instanceof ActualDiagnostic) {
            ActualDiagnostic actualDiagnostic2 = (ActualDiagnostic) other;
            if (actualDiagnostic2.diagnostic instanceof DiagnosticWithParameters1) {
                return getName().compareTo(other.getName() + ((DiagnosticWithParameters1) actualDiagnostic2.diagnostic).getA());
            }
        }
        return getName().compareTo(other.getName());
    }

    @Override // org.jetbrains.kotlin.checkers.diagnostics.AbstractTestDiagnostic
    public void enhanceInferenceCompatibility(TextDiagnostic.InferenceCompatibility inferenceCompatibility) {
        Intrinsics.checkNotNullParameter(inferenceCompatibility, "inferenceCompatibility");
        setInferenceCompatibility(inferenceCompatibility);
    }

    public boolean equals(Object other) {
        boolean zAreEqual;
        if (!(other instanceof ActualDiagnostic)) {
            return false;
        }
        ActualDiagnostic actualDiagnostic = (ActualDiagnostic) other;
        if (actualDiagnostic.diagnostic != this.diagnostic) {
            return false;
        }
        if (actualDiagnostic.getPlatform() == null) {
            zAreEqual = getPlatform() == null;
        } else {
            zAreEqual = Intrinsics.areEqual(actualDiagnostic.getPlatform(), getPlatform());
        }
        return zAreEqual && actualDiagnostic.getInferenceCompatibility() == getInferenceCompatibility();
    }

    public int hashCode() {
        int iIdentityHashCode = System.identityHashCode(this.diagnostic) * 31;
        String platform = getPlatform();
        return ((iIdentityHashCode + (platform != null ? platform.hashCode() : 0)) * 31) + getInferenceCompatibility().hashCode();
    }

    public String toString() {
        String abbreviation$frontend = getInferenceCompatibility().getAbbreviation();
        return (abbreviation$frontend != null ? abbreviation$frontend + ';' : "") + (getPlatform() != null ? getPlatform() + ':' : "") + this.diagnostic;
    }
}
