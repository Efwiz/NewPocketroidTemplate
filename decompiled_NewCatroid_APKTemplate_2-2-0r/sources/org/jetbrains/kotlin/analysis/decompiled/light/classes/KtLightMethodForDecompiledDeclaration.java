package org.jetbrains.kotlin.analysis.decompiled.light.classes;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.analysis.decompiled.light.classes.origin.LightMemberOriginForCompiledMethod;
import org.jetbrains.kotlin.asJava.LightClassUtilsKt;
import org.jetbrains.kotlin.asJava.classes.KtLightClass;
import org.jetbrains.kotlin.asJava.elements.KtLightElementBase;
import org.jetbrains.kotlin.asJava.elements.KtLightMember;
import org.jetbrains.kotlin.asJava.elements.KtLightMethod;
import org.jetbrains.kotlin.com.intellij.psi.HierarchicalMethodSignature;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationMemberValue;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiCodeBlock;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiIdentifier;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceList;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterList;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiSuperMethodImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocComment;
import org.jetbrains.kotlin.com.intellij.psi.util.MethodSignature;
import org.jetbrains.kotlin.com.intellij.psi.util.MethodSignatureBackedByPsiMethod;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.psi.KtDeclaration;

/* JADX INFO: compiled from: KtLightMethodForDecompiledDeclaration.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000Þ\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\t\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u00032\b\u0012\u0004\u0012\u00020\u00020\u0004B\u001d\u0012\u0006\u0010\u0005\u001a\u00020\u0002\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\b\u0010\u0018\u001a\u00020\u0019H\u0016J\b\u0010\u001a\u001a\u00020\u001bH\u0016J\u0013\u0010\u001c\u001a\u00020\f2\b\u0010\u001d\u001a\u0004\u0018\u00010\u0019H\u0096\u0002J\u000f\u0010\u001e\u001a\t\u0018\u00010\u0002¢\u0006\u0002\b\u001fH\u0016J\u0015\u0010 \u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020!H\u0016¢\u0006\u0002\u0010\"J\u0016\u0010#\u001a\b\u0012\u0004\u0012\u00020%0$2\u0006\u0010&\u001a\u00020\fH\u0016J\u0015\u0010'\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020!H\u0016¢\u0006\u0002\u0010\"J\u001f\u0010'\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020!2\b\u0010(\u001a\u0004\u0018\u00010)H\u0016¢\u0006\u0002\u0010*J\u001d\u0010'\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020!2\u0006\u0010&\u001a\u00020\fH\u0016¢\u0006\u0002\u0010+J\n\u0010,\u001a\u0004\u0018\u00010-H\u0016J\b\u0010.\u001a\u00020\u0007H\u0016J\n\u0010/\u001a\u0004\u0018\u000100H\u0016J\n\u00101\u001a\u0004\u0018\u000102H\u0016J\r\u00103\u001a\u000704¢\u0006\u0002\b5H\u0016J\b\u00106\u001a\u000207H\u0016J\b\u00108\u001a\u000209H\u0016J\n\u0010:\u001a\u0004\u0018\u00010;H\u0016J\b\u0010<\u001a\u00020\u0002H\u0016J\b\u0010=\u001a\u00020>H\u0016J\n\u0010?\u001a\u0004\u0018\u00010@H\u0016J\n\u0010A\u001a\u0004\u0018\u00010BH\u0016J\u0010\u0010C\u001a\u00020D2\u0006\u0010E\u001a\u00020FH\u0016J\b\u0010G\u001a\u00020HH\u0016J\n\u0010I\u001a\u0004\u0018\u00010JH\u0016J\u0013\u0010K\u001a\b\u0012\u0004\u0012\u00020L0!H\u0016¢\u0006\u0002\u0010MJ\u0010\u0010N\u001a\u00020\f2\u0006\u0010O\u001a\u000209H\u0016J\b\u0010P\u001a\u00020\fH\u0016J\b\u0010Q\u001a\u00020RH\u0016J\b\u0010S\u001a\u00020\fH\u0016J\b\u0010T\u001a\u00020\fH\u0016J\u0012\u0010U\u001a\u00020\f2\b\u0010V\u001a\u0004\u0018\u00010\u001bH\u0016J\b\u0010W\u001a\u00020\fH\u0016J\b\u0010X\u001a\u00020\fH\u0016J\u0010\u0010Y\u001a\u00020\u001b2\u0006\u0010O\u001a\u000209H\u0016J\b\u0010Z\u001a\u000209H\u0016R\u000e\u0010\u0005\u001a\u00020\u0002X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\u00020\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\rR\u0016\u0010\u000e\u001a\u0004\u0018\u00010\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\b\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013¨\u0006["}, d2 = {"Lorg/jetbrains/kotlin/analysis/decompiled/light/classes/KtLightMethodForDecompiledDeclaration;", "Lorg/jetbrains/kotlin/asJava/elements/KtLightElementBase;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiMethod;", "Lorg/jetbrains/kotlin/asJava/elements/KtLightMethod;", "Lorg/jetbrains/kotlin/asJava/elements/KtLightMember;", "funDelegate", "funParent", "Lorg/jetbrains/kotlin/asJava/classes/KtLightClass;", "lightMemberOrigin", "Lorg/jetbrains/kotlin/analysis/decompiled/light/classes/origin/LightMemberOriginForCompiledMethod;", "(Lcom/intellij/psi/PsiMethod;Lorg/jetbrains/kotlin/asJava/classes/KtLightClass;Lorg/jetbrains/kotlin/analysis/decompiled/light/classes/origin/LightMemberOriginForCompiledMethod;)V", "isMangled", "", "()Z", "kotlinOrigin", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "getKotlinOrigin", "()Lorg/jetbrains/kotlin/psi/KtDeclaration;", "getLightMemberOrigin", "()Lorg/jetbrains/kotlin/analysis/decompiled/light/classes/origin/LightMemberOriginForCompiledMethod;", "accept", "", "visitor", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElementVisitor;", "clone", "", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", Namer.EQUALS_METHOD_NAME, "other", "findDeepestSuperMethod", "Lorg/jetbrains/annotations/Nullable;", "findDeepestSuperMethods", "", "()[Lcom/intellij/psi/PsiMethod;", "findSuperMethodSignaturesIncludingStatic", "", "Lorg/jetbrains/kotlin/com/intellij/psi/util/MethodSignatureBackedByPsiMethod;", "checkAccess", "findSuperMethods", "parentClass", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiClass;", "(Lcom/intellij/psi/PsiClass;)[Lcom/intellij/psi/PsiMethod;", "(Z)[Lcom/intellij/psi/PsiMethod;", "getBody", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiCodeBlock;", "getContainingClass", "getDefaultValue", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiAnnotationMemberValue;", "getDocComment", "Lorg/jetbrains/kotlin/com/intellij/psi/javadoc/PsiDocComment;", "getHierarchicalMethodSignature", "Lorg/jetbrains/kotlin/com/intellij/psi/HierarchicalMethodSignature;", "Lorg/jetbrains/annotations/NotNull;", "getModifierList", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiModifierList;", "getName", "", "getNameIdentifier", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiIdentifier;", "getOriginalElement", "getParameterList", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiParameterList;", "getReturnType", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiType;", "getReturnTypeElement", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiTypeElement;", "getSignature", "Lorg/jetbrains/kotlin/com/intellij/psi/util/MethodSignature;", "substitutor", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiSubstitutor;", "getThrowsList", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiReferenceList;", "getTypeParameterList", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiTypeParameterList;", "getTypeParameters", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiTypeParameter;", "()[Lcom/intellij/psi/PsiTypeParameter;", "hasModifierProperty", "name", "hasTypeParameters", "hashCode", "", "isConstructor", "isDeprecated", "isEquivalentTo", "another", "isValid", "isVarArgs", "setName", "toString", "light-classes-for-decompiled"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtLightMethodForDecompiledDeclaration extends KtLightElementBase implements PsiMethod, KtLightMember<PsiMethod>, KtLightMethod {
    private final PsiMethod funDelegate;
    private final KtLightClass funParent;
    private final LightMemberOriginForCompiledMethod lightMemberOrigin;

    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolderBase
    public Object clone() {
        return this;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.psi.PsiParameterListOwner
    public PsiCodeBlock getBody() {
        return null;
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightMember
    public LightMemberOriginForCompiledMethod getLightMemberOrigin() {
        return this.lightMemberOrigin;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtLightMethodForDecompiledDeclaration(PsiMethod funDelegate, KtLightClass funParent, LightMemberOriginForCompiledMethod lightMemberOrigin) {
        super(funParent);
        Intrinsics.checkNotNullParameter(funDelegate, "funDelegate");
        Intrinsics.checkNotNullParameter(funParent, "funParent");
        Intrinsics.checkNotNullParameter(lightMemberOrigin, "lightMemberOrigin");
        this.funDelegate = funDelegate;
        this.funParent = funParent;
        this.lightMemberOrigin = lightMemberOrigin;
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightElementBase, org.jetbrains.kotlin.asJava.elements.KtLightElement
    /* JADX INFO: renamed from: getKotlinOrigin */
    public KtDeclaration getKtDeclaration() {
        return getLightMemberOrigin().getOriginalElement();
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightMethod
    public boolean isMangled() {
        return LightClassUtilsKt.checkIsMangled(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    public boolean hasModifierProperty(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return this.funDelegate.hasModifierProperty(name2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public PsiTypeElement getReturnTypeElement() {
        return this.funDelegate.getReturnTypeElement();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMember, org.jetbrains.kotlin.com.intellij.psi.PsiJvmMember
    public KtLightClass getContainingClass() {
        return this.funParent;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner
    public PsiTypeParameter[] getTypeParameters() {
        PsiTypeParameter[] typeParameters = this.funDelegate.getTypeParameters();
        Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
        return typeParameters;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public PsiReferenceList getThrowsList() {
        PsiReferenceList throwsList = this.funDelegate.getThrowsList();
        Intrinsics.checkNotNullExpressionValue(throwsList, "getThrowsList(...)");
        return throwsList;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public PsiType getReturnType() {
        return this.funDelegate.getReturnType();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner
    public boolean hasTypeParameters() {
        return this.funDelegate.hasTypeParameters();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner
    /* JADX INFO: renamed from: getTypeParameterList */
    public PsiTypeParameterList mo6093getTypeParameterList() {
        return this.funDelegate.mo6093getTypeParameterList();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public boolean isVarArgs() {
        return this.funDelegate.isVarArgs();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public boolean isConstructor() {
        return this.funDelegate.isConstructor();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.psi.PsiNameIdentifierOwner
    /* JADX INFO: renamed from: getNameIdentifier */
    public PsiIdentifier mo7392getNameIdentifier() {
        return this.funDelegate.mo7392getNameIdentifier();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    public String getName() {
        String name2 = this.funDelegate.getName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        return name2;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaDocumentedElement
    /* JADX INFO: renamed from: getDocComment */
    public PsiDocComment mo6091getDocComment() {
        return this.funDelegate.mo6091getDocComment();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    /* JADX INFO: renamed from: getModifierList */
    public PsiModifierList mo6095getModifierList() {
        PsiModifierList psiModifierListMo6095getModifierList = this.funDelegate.mo6095getModifierList();
        Intrinsics.checkNotNullExpressionValue(psiModifierListMo6095getModifierList, "getModifierList(...)");
        return psiModifierListMo6095getModifierList;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationMethod
    public PsiAnnotationMemberValue getDefaultValue() {
        PsiMethod psiMethod = this.funDelegate;
        PsiAnnotationMethod psiAnnotationMethod = psiMethod instanceof PsiAnnotationMethod ? (PsiAnnotationMethod) psiMethod : null;
        if (psiAnnotationMethod != null) {
            return psiAnnotationMethod.getDefaultValue();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDocCommentOwner
    public boolean isDeprecated() {
        return this.funDelegate.isDeprecated();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
    public PsiElement setName(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        PsiElement name3 = this.funDelegate.setName(name2);
        Intrinsics.checkNotNullExpressionValue(name3, "setName(...)");
        return name3;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.psi.PsiParameterListOwner
    public PsiParameterList getParameterList() {
        PsiParameterList parameterList = this.funDelegate.getParameterList();
        Intrinsics.checkNotNullExpressionValue(parameterList, "getParameterList(...)");
        return parameterList;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public HierarchicalMethodSignature getHierarchicalMethodSignature() {
        HierarchicalMethodSignature hierarchicalMethodSignature = PsiSuperMethodImplUtil.getHierarchicalMethodSignature(this);
        Intrinsics.checkNotNullExpressionValue(hierarchicalMethodSignature, "getHierarchicalMethodSignature(...)");
        return hierarchicalMethodSignature;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public List<MethodSignatureBackedByPsiMethod> findSuperMethodSignaturesIncludingStatic(boolean checkAccess) {
        List<MethodSignatureBackedByPsiMethod> listFindSuperMethodSignaturesIncludingStatic = PsiSuperMethodImplUtil.findSuperMethodSignaturesIncludingStatic(this, checkAccess);
        Intrinsics.checkNotNullExpressionValue(listFindSuperMethodSignaturesIncludingStatic, "findSuperMethodSignaturesIncludingStatic(...)");
        return listFindSuperMethodSignaturesIncludingStatic;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public PsiMethod findDeepestSuperMethod() {
        return PsiSuperMethodImplUtil.findDeepestSuperMethod(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public PsiMethod[] findDeepestSuperMethods() {
        PsiMethod[] psiMethodArrFindDeepestSuperMethods = PsiSuperMethodImplUtil.findDeepestSuperMethods(this);
        Intrinsics.checkNotNullExpressionValue(psiMethodArrFindDeepestSuperMethods, "findDeepestSuperMethods(...)");
        return psiMethodArrFindDeepestSuperMethods;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public PsiMethod[] findSuperMethods() {
        PsiMethod[] psiMethodArrFindSuperMethods = PsiSuperMethodImplUtil.findSuperMethods(this);
        Intrinsics.checkNotNullExpressionValue(psiMethodArrFindSuperMethods, "findSuperMethods(...)");
        return psiMethodArrFindSuperMethods;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public PsiMethod[] findSuperMethods(boolean z) {
        PsiMethod[] psiMethodArrFindSuperMethods = PsiSuperMethodImplUtil.findSuperMethods(this, z);
        Intrinsics.checkNotNullExpressionValue(psiMethodArrFindSuperMethods, "findSuperMethods(...)");
        return psiMethodArrFindSuperMethods;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public PsiMethod[] findSuperMethods(PsiClass psiClass) {
        PsiMethod[] psiMethodArrFindSuperMethods = PsiSuperMethodImplUtil.findSuperMethods(this, psiClass);
        Intrinsics.checkNotNullExpressionValue(psiMethodArrFindSuperMethods, "findSuperMethods(...)");
        return psiMethodArrFindSuperMethods;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public MethodSignature getSignature(PsiSubstitutor substitutor) {
        Intrinsics.checkNotNullParameter(substitutor, "substitutor");
        MethodSignatureBackedByPsiMethod methodSignatureBackedByPsiMethodCreate = MethodSignatureBackedByPsiMethod.create(this, substitutor);
        Intrinsics.checkNotNullExpressionValue(methodSignatureBackedByPsiMethodCreate, "create(...)");
        return methodSignatureBackedByPsiMethodCreate;
    }

    public boolean equals(Object other) {
        if (other != this) {
            if (other instanceof KtLightMethodForDecompiledDeclaration) {
                KtLightMethodForDecompiledDeclaration ktLightMethodForDecompiledDeclaration = (KtLightMethodForDecompiledDeclaration) other;
                if (!Intrinsics.areEqual(getName(), ktLightMethodForDecompiledDeclaration.getName()) || !Intrinsics.areEqual(this.funParent, ktLightMethodForDecompiledDeclaration.funParent) || !Intrinsics.areEqual(this.funDelegate, ktLightMethodForDecompiledDeclaration.funDelegate)) {
                }
            }
            return false;
        }
        return true;
    }

    public int hashCode() {
        return getName().hashCode();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement copy() {
        return this;
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightElementBase, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return getClass().getSimpleName() + " of " + this.funParent;
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightElementBase, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement, org.jetbrains.kotlin.com.intellij.pom.PomTarget
    public boolean isValid() {
        return getLightParent().isValid();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: getOriginalElement, reason: from getter */
    public PsiMethod getFunDelegate() {
        return this.funDelegate;
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightElementBase, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean isEquivalentTo(PsiElement another) {
        return Intrinsics.areEqual(this, another) || ((another instanceof KtLightMethodForDecompiledDeclaration) && this.funDelegate.isEquivalentTo(((KtLightMethodForDecompiledDeclaration) another).funDelegate)) || this.funDelegate.isEquivalentTo(another);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor visitor) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        if (visitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) visitor).visitMethod(this);
        } else {
            visitor.visitElement(this);
        }
    }
}
