package org.jetbrains.kotlin.com.intellij.psi.impl.compiled;

import io.netty.util.internal.StringUtil;
import java.util.function.Supplier;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.NotNullLazyValue;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceList;
import org.jetbrains.kotlin.com.intellij.psi.impl.cache.TypeInfo;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiClassReferenceListStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.SourceTreeToPsiMap;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public final class ClsReferenceListImpl extends ClsRepositoryPsiElement<PsiClassReferenceListStub> implements PsiReferenceList {
    private final NotNullLazyValue<PsiJavaCodeReferenceElement[]> myRefs;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2 || i == 3) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 2 || i == 3) ? 2 : 3];
        switch (i) {
            case 1:
            case 2:
            case 3:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsReferenceListImpl";
                break;
            case 4:
                objArr[0] = "buffer";
                break;
            case 5:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 6:
                objArr[0] = "visitor";
                break;
            default:
                objArr[0] = "stub";
                break;
        }
        if (i == 1) {
            objArr[1] = "getReferenceElements";
        } else if (i == 2) {
            objArr[1] = "getChildren";
        } else if (i != 3) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsReferenceListImpl";
        } else {
            objArr[1] = "getReferencedTypes";
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
                break;
            case 4:
                objArr[2] = "appendMirrorText";
                break;
            case 5:
                objArr[2] = "setMirror";
                break;
            case 6:
                objArr[2] = "accept";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2 && i != 3) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ClsReferenceListImpl(PsiClassReferenceListStub psiClassReferenceListStub) {
        super(psiClassReferenceListStub);
        if (psiClassReferenceListStub == null) {
            $$$reportNull$$$0(0);
        }
        this.myRefs = NotNullLazyValue.atomicLazy(new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsReferenceListImpl$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.m6599x1fa83b87();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$new$1$org-jetbrains-kotlin-com-intellij-psi-impl-compiled-ClsReferenceListImpl, reason: not valid java name */
    /* synthetic */ PsiJavaCodeReferenceElement[] m6599x1fa83b87() {
        TypeInfo[] types = getStub().getTypes();
        if (types.length <= 0) {
            return PsiJavaCodeReferenceElement.EMPTY_ARRAY;
        }
        return (PsiJavaCodeReferenceElement[]) ContainerUtil.map2Array(types, PsiJavaCodeReferenceElement.class, new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsReferenceListImpl$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return this.f$0.m6598x5374268((TypeInfo) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$new$0$org-jetbrains-kotlin-com-intellij-psi-impl-compiled-ClsReferenceListImpl, reason: not valid java name */
    /* synthetic */ PsiJavaCodeReferenceElement m6598x5374268(TypeInfo typeInfo) {
        return new ClsJavaCodeReferenceElementImpl(this, typeInfo.text, typeInfo.getTypeAnnotations());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReferenceList
    public PsiJavaCodeReferenceElement[] getReferenceElements() {
        PsiJavaCodeReferenceElement[] value = this.myRefs.getValue();
        if (value == null) {
            $$$reportNull$$$0(1);
        }
        return value;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsRepositoryPsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement[] getChildren() {
        PsiJavaCodeReferenceElement[] referenceElements = getReferenceElements();
        if (referenceElements == null) {
            $$$reportNull$$$0(2);
        }
        return referenceElements;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReferenceList
    public PsiClassType[] getReferencedTypes() {
        PsiClassType[] referencedTypes = getStub().getReferencedTypes();
        if (referencedTypes == null) {
            $$$reportNull$$$0(3);
        }
        return referencedTypes;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReferenceList
    public PsiReferenceList.Role getRole() {
        return getStub().getRole();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl
    public void appendMirrorText(int i, StringBuilder sb) {
        if (sb == null) {
            $$$reportNull$$$0(4);
        }
        PsiClassType[] referencedTypes = getStub().getReferencedTypes();
        if (referencedTypes.length != 0) {
            PsiReferenceList.Role role = getRole();
            int i2 = AnonymousClass1.$SwitchMap$com$intellij$psi$PsiReferenceList$Role[role.ordinal()];
            if (i2 == 1) {
                sb.append(StringUtil.SPACE).append(PsiKeyword.EXTENDS).append(StringUtil.SPACE);
            } else if (i2 == 2) {
                sb.append(PsiKeyword.EXTENDS).append(StringUtil.SPACE);
            } else if (i2 == 3) {
                sb.append(PsiKeyword.IMPLEMENTS).append(StringUtil.SPACE);
            } else if (i2 == 4) {
                sb.append(PsiKeyword.THROWS).append(StringUtil.SPACE);
            } else if (i2 == 5) {
                sb.append("with").append(StringUtil.SPACE);
            }
            for (int i3 = 0; i3 < referencedTypes.length; i3++) {
                if (i3 > 0) {
                    sb.append(role == PsiReferenceList.Role.EXTENDS_BOUNDS_LIST ? " & " : ", ");
                }
                sb.append(referencedTypes[i3].getCanonicalText(true));
            }
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsReferenceListImpl$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$intellij$psi$PsiReferenceList$Role;

        static {
            int[] iArr = new int[PsiReferenceList.Role.values().length];
            $SwitchMap$com$intellij$psi$PsiReferenceList$Role = iArr;
            try {
                iArr[PsiReferenceList.Role.EXTENDS_BOUNDS_LIST.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$intellij$psi$PsiReferenceList$Role[PsiReferenceList.Role.EXTENDS_LIST.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$intellij$psi$PsiReferenceList$Role[PsiReferenceList.Role.IMPLEMENTS_LIST.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$intellij$psi$PsiReferenceList$Role[PsiReferenceList.Role.THROWS_LIST.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$intellij$psi$PsiReferenceList$Role[PsiReferenceList.Role.PROVIDES_WITH_LIST.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl
    public void setMirror(TreeElement treeElement) throws ClsElementImpl.InvalidMirrorException {
        if (treeElement == null) {
            $$$reportNull$$$0(5);
        }
        setMirrorCheckingType(treeElement, null);
        PsiJavaCodeReferenceElement[] referenceElements = ((PsiReferenceList) SourceTreeToPsiMap.treeToPsiNotNull(treeElement)).getReferenceElements();
        PsiJavaCodeReferenceElement[] referenceElements2 = getReferenceElements();
        if (referenceElements.length == 0 && referenceElements2.length == 1 && CommonClassNames.JAVA_LANG_OBJECT.equals(referenceElements2[0].getQualifiedName())) {
            return;
        }
        setMirrors(referenceElements2, referenceElements);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(6);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitReferenceList(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiReferenceList:" + getRole();
    }
}
