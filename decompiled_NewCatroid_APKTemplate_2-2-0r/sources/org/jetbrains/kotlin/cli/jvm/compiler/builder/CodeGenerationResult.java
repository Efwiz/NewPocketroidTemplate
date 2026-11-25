package org.jetbrains.kotlin.cli.jvm.compiler.builder;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiJavaFileStub;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.diagnostics.Diagnostics;

/* JADX INFO: compiled from: LightClassBuilder.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0007HÆ\u0003J'\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001J\t\u0010\u0018\u001a\u00020\u0019HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/compiler/builder/CodeGenerationResult;", "", "stub", "Lorg/jetbrains/kotlin/com/intellij/psi/impl/java/stubs/PsiJavaFileStub;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "diagnostics", "Lorg/jetbrains/kotlin/resolve/diagnostics/Diagnostics;", "(Lcom/intellij/psi/impl/java/stubs/PsiJavaFileStub;Lorg/jetbrains/kotlin/resolve/BindingContext;Lorg/jetbrains/kotlin/resolve/diagnostics/Diagnostics;)V", "getBindingContext", "()Lorg/jetbrains/kotlin/resolve/BindingContext;", "getDiagnostics", "()Lorg/jetbrains/kotlin/resolve/diagnostics/Diagnostics;", "getStub", "()Lcom/intellij/psi/impl/java/stubs/PsiJavaFileStub;", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class CodeGenerationResult {
    private final BindingContext bindingContext;
    private final Diagnostics diagnostics;
    private final PsiJavaFileStub stub;

    public static /* synthetic */ CodeGenerationResult copy$default(CodeGenerationResult codeGenerationResult, PsiJavaFileStub psiJavaFileStub, BindingContext bindingContext, Diagnostics diagnostics, int i, Object obj) {
        if ((i & 1) != 0) {
            psiJavaFileStub = codeGenerationResult.stub;
        }
        if ((i & 2) != 0) {
            bindingContext = codeGenerationResult.bindingContext;
        }
        if ((i & 4) != 0) {
            diagnostics = codeGenerationResult.diagnostics;
        }
        return codeGenerationResult.copy(psiJavaFileStub, bindingContext, diagnostics);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final PsiJavaFileStub getStub() {
        return this.stub;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final BindingContext getBindingContext() {
        return this.bindingContext;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final Diagnostics getDiagnostics() {
        return this.diagnostics;
    }

    public final CodeGenerationResult copy(PsiJavaFileStub stub, BindingContext bindingContext, Diagnostics diagnostics) {
        Intrinsics.checkNotNullParameter(stub, "stub");
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        Intrinsics.checkNotNullParameter(diagnostics, "diagnostics");
        return new CodeGenerationResult(stub, bindingContext, diagnostics);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CodeGenerationResult)) {
            return false;
        }
        CodeGenerationResult codeGenerationResult = (CodeGenerationResult) other;
        return Intrinsics.areEqual(this.stub, codeGenerationResult.stub) && Intrinsics.areEqual(this.bindingContext, codeGenerationResult.bindingContext) && Intrinsics.areEqual(this.diagnostics, codeGenerationResult.diagnostics);
    }

    public int hashCode() {
        return (((this.stub.hashCode() * 31) + this.bindingContext.hashCode()) * 31) + this.diagnostics.hashCode();
    }

    public String toString() {
        return "CodeGenerationResult(stub=" + this.stub + ", bindingContext=" + this.bindingContext + ", diagnostics=" + this.diagnostics + ')';
    }

    public CodeGenerationResult(PsiJavaFileStub stub, BindingContext bindingContext, Diagnostics diagnostics) {
        Intrinsics.checkNotNullParameter(stub, "stub");
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        Intrinsics.checkNotNullParameter(diagnostics, "diagnostics");
        this.stub = stub;
        this.bindingContext = bindingContext;
        this.diagnostics = diagnostics;
    }

    public final BindingContext getBindingContext() {
        return this.bindingContext;
    }

    public final Diagnostics getDiagnostics() {
        return this.diagnostics;
    }

    public final PsiJavaFileStub getStub() {
        return this.stub;
    }
}
