package org.jetbrains.kotlin.cli.jvm.compiler;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.core.JavaCoreProjectEnvironment;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.resolve.jvm.KotlinCliJavaFileManager;

/* JADX INFO: compiled from: KotlinCoreProjectEnvironment.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u0007\u001a\u00020\bH\u0014¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/compiler/KotlinCoreProjectEnvironment;", "Lorg/jetbrains/kotlin/com/intellij/core/JavaCoreProjectEnvironment;", "disposable", "Lorg/jetbrains/kotlin/com/intellij/openapi/Disposable;", "applicationEnvironment", "Lorg/jetbrains/kotlin/cli/jvm/compiler/KotlinCoreApplicationEnvironment;", "(Lcom/intellij/openapi/Disposable;Lorg/jetbrains/kotlin/cli/jvm/compiler/KotlinCoreApplicationEnvironment;)V", "createCoreFileManager", "Lorg/jetbrains/kotlin/resolve/jvm/KotlinCliJavaFileManager;", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class KotlinCoreProjectEnvironment extends JavaCoreProjectEnvironment {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KotlinCoreProjectEnvironment(Disposable disposable, KotlinCoreApplicationEnvironment applicationEnvironment) {
        super(disposable, applicationEnvironment);
        Intrinsics.checkNotNullParameter(disposable, "disposable");
        Intrinsics.checkNotNullParameter(applicationEnvironment, "applicationEnvironment");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.com.intellij.core.JavaCoreProjectEnvironment
    public KotlinCliJavaFileManager createCoreFileManager() {
        PsiManager psiManager = PsiManager.getInstance(getProject());
        Intrinsics.checkNotNullExpressionValue(psiManager, "getInstance(...)");
        return new KotlinCliJavaFileManagerImpl(psiManager);
    }
}
