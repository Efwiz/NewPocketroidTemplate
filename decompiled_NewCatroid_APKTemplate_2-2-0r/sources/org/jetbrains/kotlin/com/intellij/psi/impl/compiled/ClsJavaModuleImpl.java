package org.jetbrains.kotlin.com.intellij.psi.impl.compiled;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentation;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentationProviders;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassOwner;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaModule;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaModuleReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiPackageAccessibilityStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiProvidesStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiRequiresStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiUsesStatement;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiJavaModuleStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.SourceTreeToPsiMap;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocComment;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.com.intellij.util.containers.JBIterable;

/* JADX INFO: loaded from: classes3.dex */
public class ClsJavaModuleImpl extends ClsRepositoryPsiElement<PsiJavaModuleStub> implements PsiJavaModule {
    private final PsiJavaModuleReferenceElement myReference;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 5 || i == 6 || i == 9 || i == 10 || i == 13) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[(i == 5 || i == 6 || i == 9 || i == 10 || i == 13) ? 3 : 2];
        if (i == 5) {
            objArr[0] = "buffer";
        } else if (i == 6) {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        } else if (i == 9 || i == 10) {
            objArr[0] = "name";
        } else if (i != 13) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsJavaModuleImpl";
        } else {
            objArr[0] = "visitor";
        }
        switch (i) {
            case 1:
                objArr[1] = "getExports";
                break;
            case 2:
                objArr[1] = "getOpens";
                break;
            case 3:
                objArr[1] = "getUses";
                break;
            case 4:
                objArr[1] = "getProvides";
                break;
            case 5:
            case 6:
            case 9:
            case 10:
            case 13:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsJavaModuleImpl";
                break;
            case 7:
                objArr[1] = "getNameIdentifier";
                break;
            case 8:
                objArr[1] = "getName";
                break;
            case 11:
            case 12:
                objArr[1] = "getNavigationElement";
                break;
            default:
                objArr[1] = "getRequires";
                break;
        }
        if (i == 5) {
            objArr[2] = "appendMirrorText";
        } else if (i == 6) {
            objArr[2] = "setMirror";
        } else if (i == 9) {
            objArr[2] = "setName";
        } else if (i == 10) {
            objArr[2] = "hasModifierProperty";
        } else if (i == 13) {
            objArr[2] = "accept";
        }
        String str2 = String.format(str, objArr);
        if (i != 5 && i != 6 && i != 9 && i != 10 && i != 13) {
            throw new IllegalStateException(str2);
        }
        throw new IllegalArgumentException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaDocumentedElement
    /* JADX INFO: renamed from: getDocComment */
    public PsiDocComment mo6091getDocComment() {
        return null;
    }

    public ClsJavaModuleImpl(PsiJavaModuleStub psiJavaModuleStub) {
        super(psiJavaModuleStub);
        this.myReference = new ClsJavaModuleReferenceElementImpl(this, psiJavaModuleStub.getName());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaModule
    public Iterable<PsiRequiresStatement> getRequires() {
        JBIterable jBIterableOf = JBIterable.of(getStub().getChildrenByType(JavaElementType.REQUIRES_STATEMENT, PsiRequiresStatement.EMPTY_ARRAY));
        if (jBIterableOf == null) {
            $$$reportNull$$$0(0);
        }
        return jBIterableOf;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaModule
    public Iterable<PsiPackageAccessibilityStatement> getExports() {
        JBIterable jBIterableOf = JBIterable.of(getStub().getChildrenByType(JavaElementType.EXPORTS_STATEMENT, PsiPackageAccessibilityStatement.EMPTY_ARRAY));
        if (jBIterableOf == null) {
            $$$reportNull$$$0(1);
        }
        return jBIterableOf;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaModule
    public Iterable<PsiPackageAccessibilityStatement> getOpens() {
        JBIterable jBIterableOf = JBIterable.of(getStub().getChildrenByType(JavaElementType.OPENS_STATEMENT, PsiPackageAccessibilityStatement.EMPTY_ARRAY));
        if (jBIterableOf == null) {
            $$$reportNull$$$0(2);
        }
        return jBIterableOf;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaModule
    public Iterable<PsiUsesStatement> getUses() {
        JBIterable jBIterableOf = JBIterable.of(getStub().getChildrenByType(JavaElementType.USES_STATEMENT, PsiUsesStatement.EMPTY_ARRAY));
        if (jBIterableOf == null) {
            $$$reportNull$$$0(3);
        }
        return jBIterableOf;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaModule
    public Iterable<PsiProvidesStatement> getProvides() {
        JBIterable jBIterableOf = JBIterable.of(getStub().getChildrenByType(JavaElementType.PROVIDES_STATEMENT, PsiProvidesStatement.EMPTY_ARRAY));
        if (jBIterableOf == null) {
            $$$reportNull$$$0(4);
        }
        return jBIterableOf;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl
    public void appendMirrorText(int i, StringBuilder sb) {
        if (sb == null) {
            $$$reportNull$$$0(5);
        }
        appendText(getModifierList(), i, sb);
        sb.append("module ").append(getName()).append(" {\n");
        int indentSize = getIndentSize() + i;
        int length = sb.length();
        appendChildren(getRequires(), sb, indentSize, length);
        appendChildren(getExports(), sb, indentSize, length);
        appendChildren(getOpens(), sb, indentSize, length);
        appendChildren(getUses(), sb, indentSize, length);
        appendChildren(getProvides(), sb, indentSize, length);
        StringUtil.repeatSymbol(sb, io.netty.util.internal.StringUtil.SPACE, i);
        sb.append('}');
    }

    private static void appendChildren(Iterable<? extends PsiElement> iterable, StringBuilder sb, int i, int i2) {
        ArrayList arrayListNewArrayList = ContainerUtil.newArrayList(iterable);
        if (arrayListNewArrayList.isEmpty()) {
            return;
        }
        if (sb.length() > i2) {
            sb.append('\n');
        }
        Iterator it = arrayListNewArrayList.iterator();
        while (it.hasNext()) {
            appendText((PsiElement) it.next(), i, sb);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl
    public void setMirror(TreeElement treeElement) throws ClsElementImpl.InvalidMirrorException {
        if (treeElement == null) {
            $$$reportNull$$$0(6);
        }
        setMirrorCheckingType(treeElement, JavaElementType.MODULE);
        PsiJavaModule psiJavaModule = (PsiJavaModule) SourceTreeToPsiMap.treeToPsiNotNull(treeElement);
        setMirror(mo7392getNameIdentifier(), psiJavaModule.mo7392getNameIdentifier());
        setMirror(getModifierList(), psiJavaModule.getModifierList());
        setMirrors(getRequires(), psiJavaModule.getRequires());
        setMirrors(getExports(), psiJavaModule.getExports());
        setMirrors(getOpens(), psiJavaModule.getOpens());
        setMirrors(getUses(), psiJavaModule.getUses());
        setMirrors(getProvides(), psiJavaModule.getProvides());
    }

    private static <T extends PsiElement> void setMirrors(Iterable<? extends T> iterable, Iterable<? extends T> iterable2) {
        setMirrors((List) ContainerUtil.newArrayList(iterable), (List) ContainerUtil.newArrayList(iterable2));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaModule, org.jetbrains.kotlin.com.intellij.psi.PsiNameIdentifierOwner
    /* JADX INFO: renamed from: getNameIdentifier */
    public PsiJavaModuleReferenceElement mo7392getNameIdentifier() {
        PsiJavaModuleReferenceElement psiJavaModuleReferenceElement = this.myReference;
        if (psiJavaModuleReferenceElement == null) {
            $$$reportNull$$$0(7);
        }
        return psiJavaModuleReferenceElement;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    public String getName() {
        String referenceText = this.myReference.getReferenceText();
        if (referenceText == null) {
            $$$reportNull$$$0(8);
        }
        return referenceText;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
    public PsiElement setName(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(9);
        }
        throw cannotModifyException(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    public PsiModifierList getModifierList() {
        StubElement stubElementFindChildStubByType = getStub().findChildStubByType(JavaStubElementTypes.MODIFIER_LIST);
        if (stubElementFindChildStubByType != null) {
            return (PsiModifierList) stubElementFindChildStubByType.getPsi();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    public boolean hasModifierProperty(String str) {
        if (str == null) {
            $$$reportNull$$$0(10);
        }
        PsiModifierList psiModifierListMo6095getModifierList = getModifierList();
        return psiModifierListMo6095getModifierList != null && psiModifierListMo6095getModifierList.hasModifierProperty(str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem
    public ItemPresentation getPresentation() {
        return ItemPresentationProviders.getItemPresentation(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getNavigationElement() {
        PsiElement navigationElement;
        PsiJavaModule moduleDeclaration;
        PsiElement parent = getLightParent();
        if (!(parent instanceof PsiClassOwner) || (navigationElement = parent.getNavigationElement()) == parent || !(navigationElement instanceof PsiJavaFile) || (moduleDeclaration = ((PsiJavaFile) navigationElement).getModuleDeclaration()) == null) {
            return this;
        }
        if (moduleDeclaration == null) {
            $$$reportNull$$$0(11);
        }
        return moduleDeclaration;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(13);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitModule(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiJavaModule:" + getName();
    }
}
