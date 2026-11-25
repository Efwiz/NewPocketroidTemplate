package org.jetbrains.kotlin.analysis.decompiled.light.classes;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.analysis.decompiler.psi.file.KtClsFile;
import org.jetbrains.kotlin.asJava.classes.KtLightClassForFacade;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.fileClasses.JvmFileClassUtilKt;
import org.jetbrains.kotlin.load.java.structure.LightClassOriginKind;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtFile;

/* JADX INFO: compiled from: KtLightClassForDecompiledFacade.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0000\u0018\u00002\u00020\u00012\u00020\u0002B5\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n\u0012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f¢\u0006\u0002\u0010\u000eJ\b\u0010\u001d\u001a\u00020\u001eH\u0016R\u0014\u0010\u000f\u001a\u00020\u00108VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u001a\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0015\u001a\u00020\u0016X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0014\u0010\u0019\u001a\u00020\u001a8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001c¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/analysis/decompiled/light/classes/KtLightClassForDecompiledFacade;", "Lorg/jetbrains/kotlin/analysis/decompiled/light/classes/KtLightClassForDecompiledDeclaration;", "Lorg/jetbrains/kotlin/asJava/classes/KtLightClassForFacade;", "clsDelegate", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiClass;", "clsParent", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "file", "Lorg/jetbrains/kotlin/analysis/decompiler/psi/file/KtClsFile;", "kotlinOrigin", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "files", "", "Lorg/jetbrains/kotlin/psi/KtFile;", "(Lcom/intellij/psi/PsiClass;Lcom/intellij/psi/PsiElement;Lorg/jetbrains/kotlin/analysis/decompiler/psi/file/KtClsFile;Lorg/jetbrains/kotlin/psi/KtClassOrObject;Ljava/util/Collection;)V", "facadeClassFqName", "Lorg/jetbrains/kotlin/name/FqName;", "getFacadeClassFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "getFiles", "()Ljava/util/Collection;", "multiFileClass", "", "getMultiFileClass", "()Z", "originKind", "Lorg/jetbrains/kotlin/load/java/structure/LightClassOriginKind;", "getOriginKind", "()Lorg/jetbrains/kotlin/load/java/structure/LightClassOriginKind;", "getName", "", "light-classes-for-decompiled"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtLightClassForDecompiledFacade extends KtLightClassForDecompiledDeclaration implements KtLightClassForFacade {
    private final Collection<KtFile> files;
    private final boolean multiFileClass;

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassForFacade
    public Collection<KtFile> getFiles() {
        return this.files;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public KtLightClassForDecompiledFacade(PsiClass clsDelegate, PsiElement clsParent, KtClsFile file, KtClassOrObject ktClassOrObject, Collection<? extends KtFile> files) {
        super(clsDelegate, clsParent, file, ktClassOrObject);
        Intrinsics.checkNotNullParameter(clsDelegate, "clsDelegate");
        Intrinsics.checkNotNullParameter(clsParent, "clsParent");
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(files, "files");
        this.files = files;
    }

    @Override // org.jetbrains.kotlin.analysis.decompiled.light.classes.KtLightClassForDecompiledDeclaration, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    /* JADX INFO: renamed from: getName */
    public String getMyName() {
        return super.getMyName();
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassForFacade
    public FqName getFacadeClassFqName() {
        return JvmFileClassUtilKt.getJavaFileFacadeFqName(getFile());
    }

    @Override // org.jetbrains.kotlin.analysis.decompiled.light.classes.KtLightClassForDecompiledDeclaration, org.jetbrains.kotlin.asJava.KtLightClassMarker
    public LightClassOriginKind getOriginKind() {
        return LightClassOriginKind.BINARY;
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassForFacade
    public boolean getMultiFileClass() {
        return this.multiFileClass;
    }
}
