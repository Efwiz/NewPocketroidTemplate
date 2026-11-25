package org.jetbrains.kotlin.analysis.decompiled.light.classes;

import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.analysis.decompiled.light.classes.origin.LightMemberOriginForCompiledField;
import org.jetbrains.kotlin.asJava.classes.KtLightClass;
import org.jetbrains.kotlin.asJava.elements.KtLightElementBase;
import org.jetbrains.kotlin.asJava.elements.KtLightFieldForSourceDeclarationSupport;
import org.jetbrains.kotlin.asJava.elements.KtLightMember;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.PsiIdentifier;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiVariable;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiVariableEx;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocComment;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.psi.KtDeclaration;

/* JADX INFO: compiled from: KtLightFieldForDecompiledDeclaration.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0092\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\n\b\u0016\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u00032\b\u0012\u0004\u0012\u00020\u00020\u00042\u00020\u0005B\u001d\u0012\u0006\u0010\u0006\u001a\u00020\u0002\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\b\u0010\u0016\u001a\u00020\u0017H\u0016J\n\u0010\u0018\u001a\u0004\u0018\u00010\u0017H\u0016J\u001a\u0010\u0018\u001a\u0004\u0018\u00010\u00172\u000e\u0010\u0019\u001a\n\u0012\u0004\u0012\u00020\u001b\u0018\u00010\u001aH\u0016J\b\u0010\u001c\u001a\u00020\u001dH\u0016J\u0013\u0010\u001e\u001a\u00020\u001f2\b\u0010 \u001a\u0004\u0018\u00010\u0017H\u0096\u0002J\b\u0010!\u001a\u00020\bH\u0016J\n\u0010\"\u001a\u0004\u0018\u00010#H\u0016J\n\u0010$\u001a\u0004\u0018\u00010%H\u0016J\n\u0010&\u001a\u0004\u0018\u00010'H\u0016J\b\u0010(\u001a\u00020)H\u0016J\b\u0010*\u001a\u00020+H\u0016J\b\u0010,\u001a\u00020-H\u0016J\n\u0010.\u001a\u0004\u0018\u00010/H\u0016J\b\u00100\u001a\u00020\u001fH\u0016J\u0010\u00101\u001a\u00020\u001f2\u0006\u00102\u001a\u00020)H\u0016J\b\u00103\u001a\u000204H\u0016J\b\u00105\u001a\u00020\u001fH\u0016J\u0012\u00106\u001a\u00020\u001f2\b\u00107\u001a\u0004\u0018\u00010\u001dH\u0016J\b\u00108\u001a\u00020\u001fH\u0016J\b\u00109\u001a\u00020\u0013H\u0016J\u0012\u0010:\u001a\u00020\u00132\b\u0010;\u001a\u0004\u0018\u00010%H\u0016J\u0010\u0010<\u001a\u00020\u001d2\u0006\u00102\u001a\u00020)H\u0016J\b\u0010=\u001a\u00020)H\u0016R\u000e\u0010\u0006\u001a\u00020\u0002X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\f\u001a\u0004\u0018\u00010\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\t\u001a\u00020\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011¨\u0006>"}, d2 = {"Lorg/jetbrains/kotlin/analysis/decompiled/light/classes/KtLightFieldForDecompiledDeclaration;", "Lorg/jetbrains/kotlin/asJava/elements/KtLightElementBase;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiField;", "Lorg/jetbrains/kotlin/asJava/elements/KtLightFieldForSourceDeclarationSupport;", "Lorg/jetbrains/kotlin/asJava/elements/KtLightMember;", "Lorg/jetbrains/kotlin/com/intellij/psi/impl/PsiVariableEx;", "fldDelegate", "fldParent", "Lorg/jetbrains/kotlin/asJava/classes/KtLightClass;", "lightMemberOrigin", "Lorg/jetbrains/kotlin/analysis/decompiled/light/classes/origin/LightMemberOriginForCompiledField;", "(Lcom/intellij/psi/PsiField;Lorg/jetbrains/kotlin/asJava/classes/KtLightClass;Lorg/jetbrains/kotlin/analysis/decompiled/light/classes/origin/LightMemberOriginForCompiledField;)V", "kotlinOrigin", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "getKotlinOrigin", "()Lorg/jetbrains/kotlin/psi/KtDeclaration;", "getLightMemberOrigin", "()Lorg/jetbrains/kotlin/analysis/decompiled/light/classes/origin/LightMemberOriginForCompiledField;", "accept", "", "visitor", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElementVisitor;", "clone", "", "computeConstantValue", "visitedVars", "", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiVariable;", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", Namer.EQUALS_METHOD_NAME, "", "other", "getContainingClass", "getDocComment", "Lorg/jetbrains/kotlin/com/intellij/psi/javadoc/PsiDocComment;", "getInitializer", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiExpression;", "getModifierList", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiModifierList;", "getName", "", "getNameIdentifier", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiIdentifier;", "getType", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiType;", "getTypeElement", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiTypeElement;", "hasInitializer", "hasModifierProperty", "name", "hashCode", "", "isDeprecated", "isEquivalentTo", "another", "isValid", "normalizeDeclaration", "setInitializer", "initializer", "setName", "toString", "light-classes-for-decompiled"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class KtLightFieldForDecompiledDeclaration extends KtLightElementBase implements PsiField, PsiVariableEx, KtLightFieldForSourceDeclarationSupport, KtLightMember<PsiField> {
    private final PsiField fldDelegate;
    private final KtLightClass fldParent;
    private final LightMemberOriginForCompiledField lightMemberOrigin;

    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolderBase
    public Object clone() {
        return this;
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightMember
    public LightMemberOriginForCompiledField getLightMemberOrigin() {
        return this.lightMemberOrigin;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtLightFieldForDecompiledDeclaration(PsiField fldDelegate, KtLightClass fldParent, LightMemberOriginForCompiledField lightMemberOrigin) {
        super(fldParent);
        Intrinsics.checkNotNullParameter(fldDelegate, "fldDelegate");
        Intrinsics.checkNotNullParameter(fldParent, "fldParent");
        Intrinsics.checkNotNullParameter(lightMemberOrigin, "lightMemberOrigin");
        this.fldDelegate = fldDelegate;
        this.fldParent = fldParent;
        this.lightMemberOrigin = lightMemberOrigin;
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightElementBase, org.jetbrains.kotlin.asJava.elements.KtLightElement
    public KtDeclaration getKotlinOrigin() {
        return getLightMemberOrigin().getOriginalElement();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    public boolean hasModifierProperty(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return this.fldDelegate.hasModifierProperty(name2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiField, org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    /* JADX INFO: renamed from: setInitializer */
    public void mo6096setInitializer(PsiExpression initializer) {
        this.fldDelegate.mo6096setInitializer(initializer);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMember, org.jetbrains.kotlin.com.intellij.psi.PsiJvmMember
    /* JADX INFO: renamed from: getContainingClass, reason: from getter and merged with bridge method [inline-methods] */
    public KtLightClass mo6092getContainingClass() {
        return this.fldParent;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public void normalizeDeclaration() {
        this.fldDelegate.normalizeDeclaration();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiField, org.jetbrains.kotlin.com.intellij.psi.PsiVariable, org.jetbrains.kotlin.com.intellij.psi.PsiNameIdentifierOwner
    /* JADX INFO: renamed from: getNameIdentifier */
    public PsiIdentifier mo7392getNameIdentifier() {
        PsiIdentifier psiIdentifierMo7392getNameIdentifier = this.fldDelegate.mo7392getNameIdentifier();
        Intrinsics.checkNotNullExpressionValue(psiIdentifierMo7392getNameIdentifier, "getNameIdentifier(...)");
        return psiIdentifierMo7392getNameIdentifier;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    /* JADX INFO: renamed from: getName */
    public String getMyName() {
        String myName = this.fldDelegate.getMyName();
        Intrinsics.checkNotNullExpressionValue(myName, "getName(...)");
        return myName;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public PsiExpression getInitializer() {
        return this.fldDelegate.getInitializer();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaDocumentedElement
    /* JADX INFO: renamed from: getDocComment */
    public PsiDocComment mo6091getDocComment() {
        return this.fldDelegate.mo6091getDocComment();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public PsiTypeElement getTypeElement() {
        return this.fldDelegate.getTypeElement();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    /* JADX INFO: renamed from: getModifierList */
    public PsiModifierList mo6095getModifierList() {
        return this.fldDelegate.mo6095getModifierList();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public boolean hasInitializer() {
        return this.fldDelegate.hasInitializer();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiField
    public PsiType getType() {
        PsiType type = this.fldDelegate.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        return type;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDocCommentOwner
    public boolean isDeprecated() {
        return this.fldDelegate.isDeprecated();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable, org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
    public PsiElement setName(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        PsiElement name3 = this.fldDelegate.setName(name2);
        Intrinsics.checkNotNullExpressionValue(name3, "setName(...)");
        return name3;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public Object computeConstantValue() {
        return this.fldDelegate.computeConstantValue();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiVariableEx
    public Object computeConstantValue(Set<PsiVariable> visitedVars) {
        PsiField psiField = this.fldDelegate;
        PsiVariableEx psiVariableEx = psiField instanceof PsiVariableEx ? (PsiVariableEx) psiField : null;
        if (psiVariableEx != null) {
            return psiVariableEx.computeConstantValue(visitedVars);
        }
        return null;
    }

    public boolean equals(Object other) {
        if (other != this) {
            if (other instanceof KtLightFieldForDecompiledDeclaration) {
                KtLightFieldForDecompiledDeclaration ktLightFieldForDecompiledDeclaration = (KtLightFieldForDecompiledDeclaration) other;
                if (!Intrinsics.areEqual(getMyName(), ktLightFieldForDecompiledDeclaration.getMyName()) || !Intrinsics.areEqual(this.fldParent, ktLightFieldForDecompiledDeclaration.fldParent) || !Intrinsics.areEqual(this.fldDelegate, ktLightFieldForDecompiledDeclaration.fldDelegate)) {
                }
            }
            return false;
        }
        return true;
    }

    public int hashCode() {
        return getMyName().hashCode();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement copy() {
        return this;
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightElementBase, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return getClass().getSimpleName() + " of " + this.fldParent;
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightElementBase, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement, org.jetbrains.kotlin.com.intellij.pom.PomTarget
    public boolean isValid() {
        return getMyParent().isValid();
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightElementBase, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean isEquivalentTo(PsiElement another) {
        return Intrinsics.areEqual(this, another) || ((another instanceof KtLightFieldForDecompiledDeclaration) && this.fldDelegate.isEquivalentTo(((KtLightFieldForDecompiledDeclaration) another).fldDelegate)) || this.fldDelegate.isEquivalentTo(another);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor visitor) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        if (visitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) visitor).visitField(this);
        } else {
            visitor.visitElement(this);
        }
    }
}
