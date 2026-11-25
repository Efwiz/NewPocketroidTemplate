package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.lang.ref.Reference;
import java.util.Objects;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.util.Condition;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationMemberValue;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiIdentifier;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiLiteralExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiNameValuePair;
import org.jetbrains.kotlin.com.intellij.psi.PsiReference;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.SyntaxTraverser;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiNameValuePairStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.JavaStubPsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.SourceTreeToPsiMap;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ElementType;
import org.jetbrains.kotlin.com.intellij.psi.util.MethodSignatureUtil;
import org.jetbrains.kotlin.com.intellij.reference.SoftReference;
import org.jetbrains.kotlin.com.intellij.testFramework.LightVirtualFile;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public class PsiNameValuePairImpl extends JavaStubPsiElement<PsiNameValuePairStub> implements PsiNameValuePair {
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiNameValuePairImpl.class);
    private volatile Reference<PsiAnnotationMemberValue> myDetachedValue;

    /* JADX WARN: Code duplicated, block: B:21:0x0033  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 4) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 4) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "node";
        } else if (i == 2) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiNameValuePairImpl";
        } else if (i == 3) {
            objArr[0] = "newValue";
        } else if (i == 4) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiNameValuePairImpl";
        } else if (i != 5) {
            objArr[0] = "stub";
        } else {
            objArr[0] = "visitor";
        }
        if (i == 2) {
            objArr[1] = "getNode";
        } else if (i != 4) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiNameValuePairImpl";
        } else {
            objArr[1] = "setValue";
        }
        if (i != 2) {
            if (i == 3) {
                objArr[2] = "setValue";
            } else if (i != 4) {
                if (i != 5) {
                    objArr[2] = "<init>";
                } else {
                    objArr[2] = "accept";
                }
            }
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 4) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiNameValuePairImpl(PsiNameValuePairStub psiNameValuePairStub) {
        super(psiNameValuePairStub, JavaStubElementTypes.NAME_VALUE_PAIR);
        if (psiNameValuePairStub == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiNameValuePairImpl(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(1);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.extapi.psi.StubBasedPsiElementBase, org.jetbrains.kotlin.com.intellij.extapi.psi.ASTDelegatePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public NameValuePairElement getNode() {
        ASTNode node = super.getNode();
        if (!(node instanceof NameValuePairElement)) {
            throw new IllegalStateException("Node is not NameValuePairElement; node class = " + node.getClass() + "; parents = " + String.join("; ", SyntaxTraverser.psiApi().parents(this).takeWhile(new Condition() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiNameValuePairImpl$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
                public final boolean value(Object obj) {
                    return Objects.nonNull((PsiElement) obj);
                }
            }).map(new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiNameValuePairImpl$$ExternalSyntheticLambda1
                @Override // org.jetbrains.kotlin.com.intellij.util.Function
                public final Object fun(Object obj) {
                    return ((PsiElement) obj).getClass().getName();
                }
            }).toList()));
        }
        NameValuePairElement nameValuePairElement = (NameValuePairElement) node;
        if (nameValuePairElement == null) {
            $$$reportNull$$$0(2);
        }
        return nameValuePairElement;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    /* JADX INFO: renamed from: getName */
    public String get$name() {
        PsiNameValuePairStub psiNameValuePairStub = (PsiNameValuePairStub) getStub();
        if (psiNameValuePairStub == null) {
            PsiIdentifier nameIdentifier = getNameIdentifier();
            if (nameIdentifier == null) {
                return null;
            }
            return nameIdentifier.getText();
        }
        return psiNameValuePairStub.getName();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiNameValuePair
    public String getLiteralValue() {
        PsiAnnotationMemberValue value = getValue();
        if (value instanceof PsiLiteralExpression) {
            return StringUtil.unquoteString(value.getText());
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiNameValuePair
    public PsiIdentifier getNameIdentifier() {
        ASTNode aSTNodeFindChildByRole = getNode().findChildByRole(9);
        if (aSTNodeFindChildByRole == null) {
            return null;
        }
        return (PsiIdentifier) aSTNodeFindChildByRole.getPsi();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiNameValuePair
    public PsiAnnotationMemberValue getValue() {
        PsiLiteralExpression psiLiteralExpression = (PsiLiteralExpression) getStubOrPsiChild(JavaStubElementTypes.LITERAL_EXPRESSION);
        if (psiLiteralExpression != null) {
            return psiLiteralExpression;
        }
        ASTNode aSTNodeFindChildByRole = getNode().findChildByRole(249);
        if (aSTNodeFindChildByRole == null) {
            return null;
        }
        return (PsiAnnotationMemberValue) aSTNodeFindChildByRole.getPsi();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiNameValuePair
    /* JADX INFO: renamed from: setValue */
    public PsiAnnotationMemberValue mo6100setValue(PsiAnnotationMemberValue psiAnnotationMemberValue) {
        if (psiAnnotationMemberValue == null) {
            $$$reportNull$$$0(3);
        }
        getValue().replace(psiAnnotationMemberValue);
        PsiAnnotationMemberValue value = getValue();
        if (value == null) {
            $$$reportNull$$$0(4);
        }
        return value;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiNameValuePair
    public PsiAnnotationMemberValue getDetachedValue() {
        PsiNameValuePairStub psiNameValuePairStub = (PsiNameValuePairStub) getStub();
        if (psiNameValuePairStub != null) {
            String value = psiNameValuePairStub.getValue();
            PsiAnnotationMemberValue psiAnnotationMemberValue = (PsiAnnotationMemberValue) SoftReference.dereference(this.myDetachedValue);
            if (psiAnnotationMemberValue != null) {
                return psiAnnotationMemberValue;
            }
            PsiAnnotation psiAnnotationCreateAnnotationFromText = JavaPsiFacade.getElementFactory(getProject()).createAnnotationFromText("@F(" + value + Constant.AFTER_QUTO, this);
            ((LightVirtualFile) psiAnnotationCreateAnnotationFromText.getContainingFile().getViewProvider().getVirtualFile()).setWritable(false);
            PsiAnnotationMemberValue psiAnnotationMemberValueFindAttributeValue = psiAnnotationCreateAnnotationFromText.findAttributeValue(null);
            this.myDetachedValue = new SoftReference(psiAnnotationMemberValueFindAttributeValue);
            return psiAnnotationMemberValueFindAttributeValue;
        }
        return getValue();
    }

    @Override // org.jetbrains.kotlin.com.intellij.extapi.psi.ASTDelegatePsiElement
    public void subtreeChanged() {
        this.myDetachedValue = null;
        super.subtreeChanged();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiReference getReference() {
        return new PsiReference() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiNameValuePairImpl.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                String str = (i == 2 || i == 3 || i == 4) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
                Object[] objArr = new Object[(i == 2 || i == 3 || i == 4) ? 3 : 2];
                if (i == 2) {
                    objArr[0] = "newElementName";
                } else if (i == 3 || i == 4) {
                    objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                } else {
                    objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiNameValuePairImpl$1";
                }
                if (i == 1) {
                    objArr[1] = "getRangeInElement";
                } else if (i == 2 || i == 3 || i == 4) {
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiNameValuePairImpl$1";
                } else {
                    objArr[1] = "getElement";
                }
                if (i == 2) {
                    objArr[2] = "handleElementRename";
                } else if (i == 3) {
                    objArr[2] = "bindToElement";
                } else if (i == 4) {
                    objArr[2] = "isReferenceTo";
                }
                String str2 = String.format(str, objArr);
                if (i != 2 && i != 3 && i != 4) {
                    throw new IllegalStateException(str2);
                }
                throw new IllegalArgumentException(str2);
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
            public boolean isSoft() {
                return false;
            }

            private PsiClass getReferencedClass() {
                PsiNameValuePairImpl.LOG.assertTrue((PsiNameValuePairImpl.this.getLightParent() instanceof PsiAnnotationParameterList) && (PsiNameValuePairImpl.this.getLightParent().getLightParent() instanceof PsiAnnotation));
                PsiJavaCodeReferenceElement nameReferenceElement = ((PsiAnnotation) PsiNameValuePairImpl.this.getLightParent().getLightParent()).getNameReferenceElement();
                if (nameReferenceElement == null) {
                    return null;
                }
                PsiElement psiElementResolve = nameReferenceElement.resolve();
                if (psiElementResolve instanceof PsiClass) {
                    return (PsiClass) psiElementResolve;
                }
                return null;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
            /* JADX INFO: renamed from: getElement */
            public PsiElement getPsiElement() {
                PsiNameValuePairImpl psiNameValuePairImpl = PsiNameValuePairImpl.this;
                if (psiNameValuePairImpl == null) {
                    $$$reportNull$$$0(0);
                }
                return psiNameValuePairImpl;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
            public TextRange getRangeInElement() {
                PsiIdentifier nameIdentifier = PsiNameValuePairImpl.this.getNameIdentifier();
                if (nameIdentifier != null) {
                    return new TextRange(0, nameIdentifier.getTextLength());
                }
                TextRange textRange = TextRange.EMPTY_RANGE;
                if (textRange == null) {
                    $$$reportNull$$$0(1);
                }
                return textRange;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
            public PsiElement resolve() {
                PsiClass referencedClass = getReferencedClass();
                if (referencedClass == null) {
                    return null;
                }
                String name2 = PsiNameValuePairImpl.this.get$name();
                if (name2 == null) {
                    name2 = "value";
                }
                return MethodSignatureUtil.findMethodBySignature(referencedClass, MethodSignatureUtil.createMethodSignature(name2, PsiType.EMPTY_ARRAY, PsiTypeParameter.EMPTY_ARRAY, PsiSubstitutor.EMPTY), false);
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
            public String getCanonicalText() {
                String name2 = PsiNameValuePairImpl.this.get$name();
                return name2 != null ? name2 : "value";
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
            public PsiElement handleElementRename(String str) throws IncorrectOperationException {
                if (str == null) {
                    $$$reportNull$$$0(2);
                }
                PsiIdentifier nameIdentifier = PsiNameValuePairImpl.this.getNameIdentifier();
                if (nameIdentifier != null) {
                    PsiImplUtil.setName(nameIdentifier, str);
                } else if (ElementType.ANNOTATION_MEMBER_VALUE_BIT_SET.contains(PsiNameValuePairImpl.this.getNode().getFirstChildNode().getElementType())) {
                    PsiIdentifier psiIdentifierCreateIdentifier = JavaPsiFacade.getElementFactory(PsiNameValuePairImpl.this.getProject()).createIdentifier(str);
                    PsiNameValuePairImpl psiNameValuePairImpl = PsiNameValuePairImpl.this;
                    psiNameValuePairImpl.addBefore(psiIdentifierCreateIdentifier, SourceTreeToPsiMap.treeElementToPsi(psiNameValuePairImpl.getNode().getFirstChildNode()));
                }
                return PsiNameValuePairImpl.this;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
            public PsiElement bindToElement(PsiElement psiElement) throws IncorrectOperationException {
                if (psiElement == null) {
                    $$$reportNull$$$0(3);
                }
                throw new IncorrectOperationException("Not implemented");
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
            public boolean isReferenceTo(PsiElement psiElement) {
                if (psiElement == null) {
                    $$$reportNull$$$0(4);
                }
                return (psiElement instanceof PsiMethod) && psiElement.equals(resolve());
            }
        };
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public final void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(5);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitNameValuePair(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiNameValuePair";
    }
}
