package org.jetbrains.kotlin.analysis.decompiled.light.classes;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.analysis.decompiler.psi.file.KtClsFile;
import org.jetbrains.kotlin.asJava.classes.KtLightClass;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiEnumConstant;
import org.jetbrains.kotlin.com.intellij.psi.PsiEnumConstantInitializer;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpressionList;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.psi.KtClassOrObject;

/* JADX INFO: compiled from: KtLightEnumClassForDecompiledDeclaration.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u00012\u00020\u0002B/\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u000b¢\u0006\u0002\u0010\fJ\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0096\u0002J\n\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0016J\b\u0010\u0017\u001a\u00020\u0018H\u0016J\b\u0010\u0019\u001a\u00020\u001aH\u0016J\b\u0010\u001b\u001a\u00020\u001cH\u0016J\b\u0010\u001d\u001a\u00020\u001eH\u0016J\b\u0010\u001f\u001a\u00020\u0012H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0002X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/analysis/decompiled/light/classes/KtLightEnumClassForDecompiledDeclaration;", "Lorg/jetbrains/kotlin/analysis/decompiled/light/classes/KtLightClassForDecompiledDeclaration;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiEnumConstantInitializer;", "psiConstantInitializer", "enumConstant", "Lorg/jetbrains/kotlin/analysis/decompiled/light/classes/KtLightEnumEntryForDecompiledDeclaration;", "clsParent", "Lorg/jetbrains/kotlin/asJava/classes/KtLightClass;", "file", "Lorg/jetbrains/kotlin/analysis/decompiler/psi/file/KtClsFile;", "kotlinOrigin", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "(Lcom/intellij/psi/PsiEnumConstantInitializer;Lorg/jetbrains/kotlin/analysis/decompiled/light/classes/KtLightEnumEntryForDecompiledDeclaration;Lorg/jetbrains/kotlin/asJava/classes/KtLightClass;Lorg/jetbrains/kotlin/analysis/decompiler/psi/file/KtClsFile;Lorg/jetbrains/kotlin/psi/KtClassOrObject;)V", "accept", "", "visitor", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElementVisitor;", Namer.EQUALS_METHOD_NAME, "", "other", "", "getArgumentList", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiExpressionList;", "getBaseClassReference", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiJavaCodeReferenceElement;", "getBaseClassType", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiClassType;", "getEnumConstant", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiEnumConstant;", "hashCode", "", "isInQualifiedNew", "light-classes-for-decompiled"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtLightEnumClassForDecompiledDeclaration extends KtLightClassForDecompiledDeclaration implements PsiEnumConstantInitializer {
    private final KtLightEnumEntryForDecompiledDeclaration enumConstant;
    private final PsiEnumConstantInitializer psiConstantInitializer;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtLightEnumClassForDecompiledDeclaration(PsiEnumConstantInitializer psiConstantInitializer, KtLightEnumEntryForDecompiledDeclaration enumConstant, KtLightClass clsParent, KtClsFile file, KtClassOrObject ktClassOrObject) {
        super(psiConstantInitializer, clsParent, file, ktClassOrObject);
        Intrinsics.checkNotNullParameter(psiConstantInitializer, "psiConstantInitializer");
        Intrinsics.checkNotNullParameter(enumConstant, "enumConstant");
        Intrinsics.checkNotNullParameter(clsParent, "clsParent");
        Intrinsics.checkNotNullParameter(file, "file");
        this.psiConstantInitializer = psiConstantInitializer;
        this.enumConstant = enumConstant;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnonymousClass
    public PsiClassType getBaseClassType() {
        PsiClassType baseClassType = this.psiConstantInitializer.getBaseClassType();
        Intrinsics.checkNotNullExpressionValue(baseClassType, "getBaseClassType(...)");
        return baseClassType;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnonymousClass
    public PsiExpressionList getArgumentList() {
        return this.psiConstantInitializer.getArgumentList();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiEnumConstantInitializer
    public PsiEnumConstant getEnumConstant() {
        return this.enumConstant;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnonymousClass
    public PsiJavaCodeReferenceElement getBaseClassReference() {
        PsiJavaCodeReferenceElement baseClassReference = this.psiConstantInitializer.getBaseClassReference();
        Intrinsics.checkNotNullExpressionValue(baseClassReference, "getBaseClassReference(...)");
        return baseClassReference;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnonymousClass
    public boolean isInQualifiedNew() {
        return this.psiConstantInitializer.isInQualifiedNew();
    }

    @Override // org.jetbrains.kotlin.analysis.decompiled.light.classes.KtLightClassForDecompiledDeclaration
    public boolean equals(Object other) {
        return this == other || ((other instanceof KtLightEnumClassForDecompiledDeclaration) && super.equals(other));
    }

    @Override // org.jetbrains.kotlin.analysis.decompiled.light.classes.KtLightClassForDecompiledDeclaration
    public int hashCode() {
        return super.hashCode();
    }

    @Override // org.jetbrains.kotlin.analysis.decompiled.light.classes.KtLightClassForDecompiledDeclarationBase, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor visitor) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        if (visitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) visitor).visitEnumConstantInitializer(this);
        } else {
            visitor.visitElement(this);
        }
    }
}
