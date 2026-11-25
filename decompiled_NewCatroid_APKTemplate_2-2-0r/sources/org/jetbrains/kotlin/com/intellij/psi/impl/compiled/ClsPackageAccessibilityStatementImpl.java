package org.jetbrains.kotlin.com.intellij.psi.impl.compiled;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.AtomicNullableLazyValue;
import org.jetbrains.kotlin.com.intellij.openapi.util.NotNullLazyValue;
import org.jetbrains.kotlin.com.intellij.openapi.util.NullableLazyValue;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaModuleReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiPackageAccessibilityStatement;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaPackageAccessibilityStatementElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiPackageAccessibilityStatementStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public final class ClsPackageAccessibilityStatementImpl extends ClsRepositoryPsiElement<PsiPackageAccessibilityStatementStub> implements PsiPackageAccessibilityStatement {
    private final NotNullLazyValue<Iterable<PsiJavaModuleReferenceElement>> myModuleReferences;
    private final NullableLazyValue<PsiJavaCodeReferenceElement> myPackageReference;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 4) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[(i == 3 || i == 4) ? 3 : 2];
        if (i == 3) {
            objArr[0] = "buffer";
        } else if (i != 4) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsPackageAccessibilityStatementImpl";
        } else {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        }
        if (i == 1) {
            objArr[1] = "getModuleReferences";
        } else if (i == 2) {
            objArr[1] = "getModuleNames";
        } else if (i == 3 || i == 4) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsPackageAccessibilityStatementImpl";
        } else {
            objArr[1] = "getRole";
        }
        if (i == 3) {
            objArr[2] = "appendMirrorText";
        } else if (i == 4) {
            objArr[2] = "setMirror";
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 4) {
            throw new IllegalStateException(str2);
        }
        throw new IllegalArgumentException(str2);
    }

    public ClsPackageAccessibilityStatementImpl(PsiPackageAccessibilityStatementStub psiPackageAccessibilityStatementStub) {
        super(psiPackageAccessibilityStatementStub);
        this.myPackageReference = new AtomicNullableLazyValue<PsiJavaCodeReferenceElement>() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsPackageAccessibilityStatementImpl.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.NullableLazyValue
            public PsiJavaCodeReferenceElement compute() {
                String packageName = ClsPackageAccessibilityStatementImpl.this.getPackageName();
                if (packageName != null) {
                    return new ClsJavaCodeReferenceElementImpl(ClsPackageAccessibilityStatementImpl.this, packageName);
                }
                return null;
            }
        };
        this.myModuleReferences = NotNullLazyValue.atomicLazy(new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsPackageAccessibilityStatementImpl$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.m6594x732161a5();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$new$0$org-jetbrains-kotlin-com-intellij-psi-impl-compiled-ClsPackageAccessibilityStatementImpl, reason: not valid java name */
    /* synthetic */ PsiJavaModuleReferenceElement m6593x3b308686(String str) {
        return new ClsJavaModuleReferenceElementImpl(this, str);
    }

    /* JADX INFO: renamed from: lambda$new$1$org-jetbrains-kotlin-com-intellij-psi-impl-compiled-ClsPackageAccessibilityStatementImpl, reason: not valid java name */
    /* synthetic */ Iterable m6594x732161a5() {
        return ContainerUtil.map((Collection) getStub().getTargets(), new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsPackageAccessibilityStatementImpl$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return this.f$0.m6593x3b308686((String) obj);
            }
        });
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiPackageAccessibilityStatement
    public PsiPackageAccessibilityStatement.Role getRole() {
        PsiPackageAccessibilityStatement.Role roleTypeToRole = JavaPackageAccessibilityStatementElementType.typeToRole(getStub().getStubType());
        if (roleTypeToRole == null) {
            $$$reportNull$$$0(0);
        }
        return roleTypeToRole;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiPackageAccessibilityStatement
    public PsiJavaCodeReferenceElement getPackageReference() {
        return this.myPackageReference.getValue();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiPackageAccessibilityStatement
    public String getPackageName() {
        return StringUtil.nullize(getStub().getPackageName());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiPackageAccessibilityStatement
    public Iterable<PsiJavaModuleReferenceElement> getModuleReferences() {
        Iterable<PsiJavaModuleReferenceElement> value = this.myModuleReferences.getValue();
        if (value == null) {
            $$$reportNull$$$0(1);
        }
        return value;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiPackageAccessibilityStatement
    public List<String> getModuleNames() {
        List<String> targets = getStub().getTargets();
        if (targets == null) {
            $$$reportNull$$$0(2);
        }
        return targets;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl
    public void appendMirrorText(int i, StringBuilder sb) {
        if (sb == null) {
            $$$reportNull$$$0(3);
        }
        StringUtil.repeatSymbol(sb, io.netty.util.internal.StringUtil.SPACE, i);
        sb.append(StringUtil.toLowerCase(getRole().toString())).append(io.netty.util.internal.StringUtil.SPACE).append(getPackageName());
        List<String> targets = getStub().getTargets();
        if (!targets.isEmpty()) {
            sb.append(" to ");
            for (int i2 = 0; i2 < targets.size(); i2++) {
                if (i2 > 0) {
                    sb.append(", ");
                }
                sb.append(targets.get(i2));
            }
        }
        sb.append(";\n");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl
    public void setMirror(TreeElement treeElement) throws ClsElementImpl.InvalidMirrorException {
        if (treeElement == null) {
            $$$reportNull$$$0(4);
        }
        setMirrorCheckingType(treeElement, getStub().getStubType());
    }

    @Override // java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiPackageAccessibilityStatement[" + getRole() + "]";
    }
}
