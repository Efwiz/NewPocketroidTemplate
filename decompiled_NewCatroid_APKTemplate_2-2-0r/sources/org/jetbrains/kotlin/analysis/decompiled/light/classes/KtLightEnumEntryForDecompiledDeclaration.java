package org.jetbrains.kotlin.analysis.decompiled.light.classes;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.analysis.decompiled.light.classes.origin.LightMemberOriginForCompiledField;
import org.jetbrains.kotlin.analysis.decompiler.psi.file.KtClsFile;
import org.jetbrains.kotlin.analysis.providers.KotlinModificationTrackerFactoryKt;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiEnumConstant;
import org.jetbrains.kotlin.com.intellij.psi.PsiEnumConstantInitializer;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpressionList;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValuesManager;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: KtLightEnumEntryForDecompiledDeclaration.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u00012\u00020\u0002B%\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0096\u0002J\n\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0016J\n\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0016J\b\u0010\u0017\u001a\u00020\u0016H\u0016J\b\u0010\u0018\u001a\u00020\u0019H\u0016J\n\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0016J\n\u0010\u001c\u001a\u0004\u0018\u00010\u001bH\u0016J\b\u0010\u001d\u001a\u00020\u001eH\u0016R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0002X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/analysis/decompiled/light/classes/KtLightEnumEntryForDecompiledDeclaration;", "Lorg/jetbrains/kotlin/analysis/decompiled/light/classes/KtLightFieldForDecompiledDeclaration;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiEnumConstant;", "fldDelegate", "fldParent", "Lorg/jetbrains/kotlin/analysis/decompiled/light/classes/KtLightClassForDecompiledDeclaration;", "lightMemberOrigin", "Lorg/jetbrains/kotlin/analysis/decompiled/light/classes/origin/LightMemberOriginForCompiledField;", "file", "Lorg/jetbrains/kotlin/analysis/decompiler/psi/file/KtClsFile;", "(Lcom/intellij/psi/PsiEnumConstant;Lorg/jetbrains/kotlin/analysis/decompiled/light/classes/KtLightClassForDecompiledDeclaration;Lorg/jetbrains/kotlin/analysis/decompiled/light/classes/origin/LightMemberOriginForCompiledField;Lorg/jetbrains/kotlin/analysis/decompiler/psi/file/KtClsFile;)V", "accept", "", "visitor", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElementVisitor;", Namer.EQUALS_METHOD_NAME, "", "other", "", "getArgumentList", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiExpressionList;", "getInitializingClass", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiEnumConstantInitializer;", "getOrCreateInitializingClass", "hashCode", "", "resolveConstructor", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiMethod;", "resolveMethod", "resolveMethodGenerics", "Lorg/jetbrains/kotlin/com/intellij/psi/JavaResolveResult;", "light-classes-for-decompiled"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtLightEnumEntryForDecompiledDeclaration extends KtLightFieldForDecompiledDeclaration implements PsiEnumConstant {
    private final KtClsFile file;
    private final PsiEnumConstant fldDelegate;

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiEnumConstant
    public PsiEnumConstantInitializer getOrCreateInitializingClass() {
        PsiEnumConstantInitializer initializingClass = getInitializingClass();
        if (initializingClass != null) {
            return initializingClass;
        }
        throw new IllegalStateException("cannot create initializing class in light enum constant".toString());
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtLightEnumEntryForDecompiledDeclaration(PsiEnumConstant fldDelegate, KtLightClassForDecompiledDeclaration fldParent, LightMemberOriginForCompiledField lightMemberOrigin, KtClsFile file) {
        super(fldDelegate, fldParent, lightMemberOrigin);
        Intrinsics.checkNotNullParameter(fldDelegate, "fldDelegate");
        Intrinsics.checkNotNullParameter(fldParent, "fldParent");
        Intrinsics.checkNotNullParameter(lightMemberOrigin, "lightMemberOrigin");
        Intrinsics.checkNotNullParameter(file, "file");
        this.fldDelegate = fldDelegate;
        this.file = file;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiEnumConstant, org.jetbrains.kotlin.com.intellij.psi.PsiCall
    public PsiExpressionList getArgumentList() {
        return this.fldDelegate.getArgumentList();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiConstructorCall
    public PsiMethod resolveConstructor() {
        return this.fldDelegate.resolveConstructor();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiCall
    public PsiMethod resolveMethod() {
        return this.fldDelegate.resolveMethod();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiCall
    public JavaResolveResult resolveMethodGenerics() {
        JavaResolveResult javaResolveResultResolveMethodGenerics = this.fldDelegate.resolveMethodGenerics();
        Intrinsics.checkNotNullExpressionValue(javaResolveResultResolveMethodGenerics, "resolveMethodGenerics(...)");
        return javaResolveResultResolveMethodGenerics;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiEnumConstant
    public PsiEnumConstantInitializer getInitializingClass() {
        final KtLightEnumEntryForDecompiledDeclaration ktLightEnumEntryForDecompiledDeclaration = this;
        return (PsiEnumConstantInitializer) CachedValuesManager.getCachedValue((PsiElement) ktLightEnumEntryForDecompiledDeclaration, new CachedValueProvider() { // from class: org.jetbrains.kotlin.analysis.decompiled.light.classes.KtLightEnumEntryForDecompiledDeclaration$getInitializingClass$$inlined$cachedValueWithLibraryTracker$1
            @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider
            public final CachedValueProvider.Result<T> compute() {
                KtLightEnumClassForDecompiledDeclaration ktLightEnumClassForDecompiledDeclaration;
                PsiEnumConstantInitializer initializingClass = this.fldDelegate.getInitializingClass();
                if (initializingClass != null) {
                    Intrinsics.checkNotNull(initializingClass);
                    KtLightEnumEntryForDecompiledDeclaration ktLightEnumEntryForDecompiledDeclaration2 = this;
                    ktLightEnumClassForDecompiledDeclaration = new KtLightEnumClassForDecompiledDeclaration(initializingClass, ktLightEnumEntryForDecompiledDeclaration2, ktLightEnumEntryForDecompiledDeclaration2.mo6092getContainingClass(), this.file, null);
                } else {
                    ktLightEnumClassForDecompiledDeclaration = null;
                }
                Project project = ktLightEnumEntryForDecompiledDeclaration.getProject();
                Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
                return CachedValueProvider.Result.createSingleDependency(ktLightEnumClassForDecompiledDeclaration, KotlinModificationTrackerFactoryKt.createAllLibrariesModificationTracker(project));
            }
        });
    }

    @Override // org.jetbrains.kotlin.analysis.decompiled.light.classes.KtLightFieldForDecompiledDeclaration
    public boolean equals(Object other) {
        if (other != this) {
            if (other instanceof KtLightEnumEntryForDecompiledDeclaration) {
                KtLightEnumEntryForDecompiledDeclaration ktLightEnumEntryForDecompiledDeclaration = (KtLightEnumEntryForDecompiledDeclaration) other;
                if (!Intrinsics.areEqual(mo6092getContainingClass(), ktLightEnumEntryForDecompiledDeclaration.mo6092getContainingClass()) || !Intrinsics.areEqual(this.fldDelegate, ktLightEnumEntryForDecompiledDeclaration.fldDelegate)) {
                }
            }
            return false;
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.analysis.decompiled.light.classes.KtLightFieldForDecompiledDeclaration
    public int hashCode() {
        return super.hashCode();
    }

    @Override // org.jetbrains.kotlin.analysis.decompiled.light.classes.KtLightFieldForDecompiledDeclaration, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor visitor) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        if (visitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) visitor).visitEnumConstant(this);
        } else {
            visitor.visitElement(this);
        }
    }
}
