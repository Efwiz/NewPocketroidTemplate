package org.jetbrains.kotlin.incremental;

import java.io.File;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.jvm.compiler.EnvironmentConfigFiles;
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment;
import org.jetbrains.kotlin.com.intellij.lang.java.JavaLanguage;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.com.intellij.openapi.util.Disposer;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiFileFactory;

/* JADX INFO: compiled from: IncrementalJvmCompilerRunner.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001f\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0010\u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\n\u001a\u00020\u000bR\u001b\u0010\u0002\u001a\u00020\u00038BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0006\u0010\u0007\u001a\u0004\b\u0004\u0010\u0005¨\u0006\f"}, d2 = {"org/jetbrains/kotlin/incremental/IncrementalJvmCompilerRunner$psiFileProvider$1", "", "psiFileFactory", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiFileFactory;", "getPsiFileFactory", "()Lcom/intellij/psi/PsiFileFactory;", "psiFileFactory$delegate", "Lkotlin/Lazy;", "javaFile", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiFile;", "file", "Ljava/io/File;", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IncrementalJvmCompilerRunner$psiFileProvider$1 {

    /* JADX INFO: renamed from: psiFileFactory$delegate, reason: from kotlin metadata */
    private final Lazy psiFileFactory;

    IncrementalJvmCompilerRunner$psiFileProvider$1(final IncrementalJvmCompilerRunner incrementalJvmCompilerRunner) {
        this.psiFileFactory = LazyKt.lazy(new Function0<PsiFileFactory>() { // from class: org.jetbrains.kotlin.incremental.IncrementalJvmCompilerRunner$psiFileProvider$1$psiFileFactory$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final PsiFileFactory invoke() {
                Disposable disposableNewDisposable = Disposer.newDisposable();
                Intrinsics.checkNotNullExpressionValue(disposableNewDisposable, "newDisposable(...)");
                return PsiFileFactory.getInstance(KotlinCoreEnvironment.INSTANCE.createForProduction(disposableNewDisposable, incrementalJvmCompilerRunner.getCompilerConfiguration(), EnvironmentConfigFiles.JVM_CONFIG_FILES).getProject());
            }
        });
    }

    public final PsiFile javaFile(File file) {
        Intrinsics.checkNotNullParameter(file, "file");
        return getPsiFileFactory().createFileFromText(FilesKt.getNameWithoutExtension(file), JavaLanguage.INSTANCE, FilesKt.readText$default(file, null, 1, null));
    }

    private final PsiFileFactory getPsiFileFactory() {
        Object value = this.psiFileFactory.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
        return (PsiFileFactory) value;
    }
}
