package org.jetbrains.kotlin.analysis.decompiled.light.classes.origin;

import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.analysis.decompiler.psi.file.KtClsFile;
import org.jetbrains.kotlin.asJava.builder.LightMemberOrigin;
import org.jetbrains.kotlin.asJava.classes.ImplUtilsKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.psi.KtDeclaration;

/* JADX INFO: compiled from: LightMemberOriginForCompiledElement.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u0013\u001a\u00020\u0002HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0005HÆ\u0003J\b\u0010\u0015\u001a\u00020\u0016H\u0016J\u001d\u0010\u0015\u001a\u00020\u00002\b\b\u0002\u0010\u0003\u001a\u00020\u00022\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aHÖ\u0003J\t\u0010\u001b\u001a\u00020\u001cHÖ\u0001J\u0012\u0010\u001d\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u0016H\u0016J\t\u0010\u001e\u001a\u00020\u001fHÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\u00028VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u001d\u0010\f\u001a\u0004\u0018\u00010\r8VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0003\u001a\u00020\u0002¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000b¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/analysis/decompiled/light/classes/origin/LightMemberOriginForCompiledMethod;", "Lorg/jetbrains/kotlin/analysis/decompiled/light/classes/origin/LightMemberOriginForCompiledElement;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiMethod;", "psiMethod", "file", "Lorg/jetbrains/kotlin/analysis/decompiler/psi/file/KtClsFile;", "(Lcom/intellij/psi/PsiMethod;Lorg/jetbrains/kotlin/analysis/decompiler/psi/file/KtClsFile;)V", "getFile", "()Lorg/jetbrains/kotlin/analysis/decompiler/psi/file/KtClsFile;", "member", "getMember", "()Lcom/intellij/psi/PsiMethod;", "originalElement", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "getOriginalElement", "()Lorg/jetbrains/kotlin/psi/KtDeclaration;", "originalElement$delegate", "Lkotlin/Lazy;", "getPsiMethod", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "Lorg/jetbrains/kotlin/asJava/builder/LightMemberOrigin;", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "isEquivalentTo", "toString", "", "light-classes-for-decompiled"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class LightMemberOriginForCompiledMethod implements LightMemberOriginForCompiledElement<PsiMethod> {
    private final KtClsFile file;

    /* JADX INFO: renamed from: originalElement$delegate, reason: from kotlin metadata */
    private final Lazy originalElement;
    private final PsiMethod psiMethod;

    public static /* synthetic */ LightMemberOriginForCompiledMethod copy$default(LightMemberOriginForCompiledMethod lightMemberOriginForCompiledMethod, PsiMethod psiMethod, KtClsFile ktClsFile, int i, Object obj) {
        if ((i & 1) != 0) {
            psiMethod = lightMemberOriginForCompiledMethod.psiMethod;
        }
        if ((i & 2) != 0) {
            ktClsFile = lightMemberOriginForCompiledMethod.file;
        }
        return lightMemberOriginForCompiledMethod.copy(psiMethod, ktClsFile);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final PsiMethod getPsiMethod() {
        return this.psiMethod;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final KtClsFile getFile() {
        return this.file;
    }

    public final LightMemberOriginForCompiledMethod copy(PsiMethod psiMethod, KtClsFile file) {
        Intrinsics.checkNotNullParameter(psiMethod, "psiMethod");
        Intrinsics.checkNotNullParameter(file, "file");
        return new LightMemberOriginForCompiledMethod(psiMethod, file);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof LightMemberOriginForCompiledMethod)) {
            return false;
        }
        LightMemberOriginForCompiledMethod lightMemberOriginForCompiledMethod = (LightMemberOriginForCompiledMethod) other;
        return Intrinsics.areEqual(this.psiMethod, lightMemberOriginForCompiledMethod.psiMethod) && Intrinsics.areEqual(this.file, lightMemberOriginForCompiledMethod.file);
    }

    public int hashCode() {
        return (this.psiMethod.hashCode() * 31) + this.file.hashCode();
    }

    public String toString() {
        return "LightMemberOriginForCompiledMethod(psiMethod=" + this.psiMethod + ", file=" + this.file + ')';
    }

    public LightMemberOriginForCompiledMethod(PsiMethod psiMethod, KtClsFile file) {
        Intrinsics.checkNotNullParameter(psiMethod, "psiMethod");
        Intrinsics.checkNotNullParameter(file, "file");
        this.psiMethod = psiMethod;
        this.file = file;
        this.originalElement = ImplUtilsKt.lazyPub(new Function0<KtDeclaration>() { // from class: org.jetbrains.kotlin.analysis.decompiled.light.classes.origin.LightMemberOriginForCompiledMethod$originalElement$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final KtDeclaration invoke() {
                return KotlinDeclarationInCompiledFileSearcher.Companion.getInstance().findDeclarationInCompiledFile(this.this$0.getFile(), this.this$0.getPsiMethod());
            }
        });
    }

    public final KtClsFile getFile() {
        return this.file;
    }

    public final PsiMethod getPsiMethod() {
        return this.psiMethod;
    }

    @Override // org.jetbrains.kotlin.analysis.decompiled.light.classes.origin.LightMemberOriginForCompiledElement
    public PsiMethod getMember() {
        return this.psiMethod;
    }

    @Override // org.jetbrains.kotlin.asJava.builder.LightMemberOrigin
    public boolean isEquivalentTo(LightMemberOrigin other) {
        if (other instanceof LightMemberOriginForCompiledMethod) {
            return this.psiMethod.isEquivalentTo(((LightMemberOriginForCompiledMethod) other).psiMethod);
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.asJava.builder.LightMemberOrigin
    public LightMemberOrigin copy() {
        PsiElement psiElementCopy = this.psiMethod.copy();
        Intrinsics.checkNotNull(psiElementCopy, "null cannot be cast to non-null type com.intellij.psi.PsiMethod");
        return new LightMemberOriginForCompiledMethod((PsiMethod) psiElementCopy, this.file);
    }

    @Override // org.jetbrains.kotlin.asJava.builder.LightElementOrigin
    public KtDeclaration getOriginalElement() {
        return (KtDeclaration) this.originalElement.getValue();
    }
}
