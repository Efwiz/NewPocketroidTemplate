package org.jetbrains.kotlin.resolve.diagnostics;

import java.util.ArrayList;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.arguments.DevModeOverwritingStrategies;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX INFO: compiled from: SimpleDiagnostics.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u001f\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\u0013\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0016\u0010\r\u001a\u0010\u0012\f\u0012\n \b*\u0004\u0018\u00010\u00020\u00020\u0007H\u0016J\u0016\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\b\u0010\u0012\u001a\u00020\u0000H\u0016R\u001c\u0010\u0004\u001a\u0010\u0012\f\u0012\n \b*\u0004\u0018\u00010\u00020\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u000b\u0010\f¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/resolve/diagnostics/SimpleDiagnostics;", "Lorg/jetbrains/kotlin/resolve/diagnostics/SimpleGenericDiagnostics;", "Lorg/jetbrains/kotlin/diagnostics/Diagnostic;", "Lorg/jetbrains/kotlin/resolve/diagnostics/Diagnostics;", "diagnostics", "", "(Ljava/util/Collection;)V", "Ljava/util/ArrayList;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "elementsCache", "Lorg/jetbrains/kotlin/resolve/diagnostics/DiagnosticsElementsCache;", "getElementsCache$annotations", "()V", DevModeOverwritingStrategies.ALL, "forElement", "", "psiElement", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "noSuppression", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SimpleDiagnostics extends SimpleGenericDiagnostics<Diagnostic> implements Diagnostics {
    private final ArrayList<Diagnostic> diagnostics;
    private final DiagnosticsElementsCache elementsCache;

    @Override // org.jetbrains.kotlin.resolve.diagnostics.Diagnostics
    public SimpleDiagnostics noSuppression() {
        return this;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SimpleDiagnostics(Collection<? extends Diagnostic> diagnostics) {
        super(diagnostics);
        Intrinsics.checkNotNullParameter(diagnostics, "diagnostics");
        this.diagnostics = new ArrayList<>(diagnostics);
        this.elementsCache = new DiagnosticsElementsCache(this, new Function1<Diagnostic, Boolean>() { // from class: org.jetbrains.kotlin.resolve.diagnostics.SimpleDiagnostics$elementsCache$1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(Diagnostic diagnostic) {
                return true;
            }
        });
    }

    @Override // org.jetbrains.kotlin.resolve.diagnostics.SimpleGenericDiagnostics, org.jetbrains.kotlin.diagnostics.GenericDiagnostics
    public ArrayList<Diagnostic> all() {
        return this.diagnostics;
    }

    @Override // org.jetbrains.kotlin.resolve.diagnostics.Diagnostics
    public Collection<Diagnostic> forElement(PsiElement psiElement) {
        Intrinsics.checkNotNullParameter(psiElement, "psiElement");
        Collection<Diagnostic> diagnostics = this.elementsCache.getDiagnostics(psiElement);
        Intrinsics.checkNotNullExpressionValue(diagnostics, "getDiagnostics(...)");
        return diagnostics;
    }
}
