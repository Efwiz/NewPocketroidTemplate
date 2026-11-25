package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree;

import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.PsiBuilder;
import org.jetbrains.kotlin.com.intellij.lang.java.parser.JavaParser;
import org.jetbrains.kotlin.com.intellij.lang.java.parser.JavaParserUtil;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnonymousClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayType;
import org.jetbrains.kotlin.com.intellij.psi.PsiCapturedWildcardType;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiCompiledElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiIntersectionType;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaToken;
import org.jetbrains.kotlin.com.intellij.psi.PsiLambdaExpressionType;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiMember;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethodReferenceType;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiPrimitiveType;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiWildcardType;
import org.jetbrains.kotlin.com.intellij.psi.impl.GeneratedMarkerVisitor;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.DummyHolderFactory;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.JavaDummyElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiJavaCodeReferenceElementImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.codeStyle.CodeEditUtil;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.util.CharTable;

/* JADX INFO: loaded from: classes3.dex */
public class JavaTreeGenerator implements TreeGenerator {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Logger LOG = Logger.getInstance((Class<?>) JavaTreeGenerator.class);
    private static final JavaParserUtil.ParserWrapper MOD_LIST = new JavaParserUtil.ParserWrapper() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaTreeGenerator$$ExternalSyntheticLambda0
        @Override // org.jetbrains.kotlin.com.intellij.lang.java.parser.JavaParserUtil.ParserWrapper
        public final void parse(PsiBuilder psiBuilder) {
            JavaParser.INSTANCE.getDeclarationParser().parseModifierList(psiBuilder);
        }
    };
    static final Key<PsiClass> REFERENCED_CLASS_KEY = Key.create("REFERENCED_CLASS_KEY");
    static final Key<PsiMember> REFERENCED_MEMBER_KEY = Key.create("REFERENCED_MEMBER_KEY");

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "table";
        } else if (i == 2) {
            objArr[0] = "manager";
        } else if (i == 4) {
            objArr[0] = ConfigConstants.CONFIG_RENAMELIMIT_COPY;
        } else if (i != 5) {
            objArr[0] = "original";
        } else {
            objArr[0] = "reference";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/JavaTreeGenerator";
        if (i == 3 || i == 4) {
            objArr[2] = "markGeneratedIfNeeded";
        } else if (i != 5) {
            objArr[2] = "generateTreeFor";
        } else {
            objArr[2] = "encodeClassTypeInfoInReference";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    /* JADX WARN: Code duplicated, block: B:55:0x00ec  */
    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeGenerator
    public TreeElement generateTreeFor(PsiElement psiElement, CharTable charTable, PsiManager psiManager) {
        PsiElement psiElement2;
        String text;
        if (psiElement == null) {
            $$$reportNull$$$0(0);
        }
        if (charTable == null) {
            $$$reportNull$$$0(1);
        }
        if (psiManager == null) {
            $$$reportNull$$$0(2);
        }
        if (psiElement instanceof PsiJavaToken) {
            return createLeafFromText(psiElement.getText(), charTable, psiManager, psiElement, ((PsiJavaToken) psiElement).getTokenType());
        }
        if (psiElement instanceof PsiModifierList) {
            String text2 = psiElement.getText();
            TreeElement firstChildNode = DummyHolderFactory.createHolder(psiElement.getManager(), new JavaDummyElement(text2, MOD_LIST, PsiUtil.getLanguageLevel(psiElement)), (PsiElement) null).getTreeElement().getFirstChildNode();
            if (firstChildNode == null) {
                throw new AssertionError("No modifier list for \"" + text2 + '\"');
            }
            return markGeneratedIfNeeded(psiElement, firstChildNode);
        }
        if (psiElement instanceof PsiReferenceExpression) {
            TreeElement treeElementCreateReferenceExpression = createReferenceExpression(psiElement.getProject(), psiElement.getText(), psiElement);
            PsiElement psiElementResolve = ((PsiJavaCodeReferenceElement) psiElement).resolve();
            if (psiElementResolve instanceof PsiClass) {
                treeElementCreateReferenceExpression.putCopyableUserData(REFERENCED_CLASS_KEY, (PsiClass) psiElementResolve);
            }
            return treeElementCreateReferenceExpression;
        }
        if (psiElement instanceof PsiJavaCodeReferenceElement) {
            PsiElement psiElementResolve2 = ((PsiJavaCodeReferenceElement) psiElement).resolve();
            boolean z = psiElementResolve2 != null && CodeEditUtil.isNodeGenerated(psiElementResolve2.getNode());
            if (psiElementResolve2 instanceof PsiClass) {
                if (psiElementResolve2 instanceof PsiAnonymousClass) {
                    psiElement2 = psiElement;
                    PsiJavaCodeReferenceElement baseClassReference = ((PsiAnonymousClass) psiElementResolve2).getBaseClassReference();
                    psiElementResolve2 = baseClassReference.resolve();
                    psiElement2 = baseClassReference;
                }
                psiElement2 = psiElement;
                if (psiElement2 instanceof PsiJavaCodeReferenceElementImpl) {
                    int i = AnonymousClass1.$SwitchMap$com$intellij$psi$impl$source$PsiJavaCodeReferenceElementImpl$Kind[((PsiJavaCodeReferenceElementImpl) psiElement2).getKindEnum(psiElement2.getContainingFile()).ordinal()];
                    if (i == 1 || i == 2 || i == 3) {
                        text = psiElement2.getText();
                    } else if (i != 4 && i != 5) {
                        LOG.assertTrue(false);
                        text = psiElement2.getText();
                    } else {
                        text = ((PsiClass) psiElementResolve2).getQualifiedName();
                    }
                } else {
                    text = psiElement2.getText();
                }
                TreeElement treeElementCreateReference = createReference(psiElement2.getProject(), text, z);
                treeElementCreateReference.putCopyableUserData(REFERENCED_CLASS_KEY, (PsiClass) psiElementResolve2);
                return treeElementCreateReference;
            }
            return createReference(psiElement.getProject(), psiElement.getText(), z);
        }
        if (psiElement instanceof PsiCompiledElement) {
            PsiElement navigationElement = psiElement.getNavigationElement();
            if (navigationElement != psiElement) {
                return ChangeUtil.generateTreeElement(navigationElement, charTable, psiManager);
            }
            return ChangeUtil.generateTreeElement(((PsiCompiledElement) psiElement).get$mirrorFile(), charTable, psiManager);
        }
        if (!(psiElement instanceof PsiTypeElement)) {
            return null;
        }
        PsiType type = ((PsiTypeElement) psiElement).getType();
        if (type instanceof PsiIntersectionType) {
            type = ((PsiIntersectionType) type).getRepresentative();
        } else if ((type instanceof PsiMethodReferenceType) || (type instanceof PsiLambdaExpressionType)) {
            type = PsiType.getJavaLangObject(psiManager, GlobalSearchScope.projectScope(psiManager.getProject()));
        }
        TreeElement treeElement = (TreeElement) JavaPsiFacade.getInstance(psiElement.getProject()).getParserFacade().createTypeElementFromText(type.getCanonicalText(true), psiElement).getNode();
        markGeneratedIfNeeded(psiElement, treeElement);
        encodeInfoInTypeElement(treeElement, type);
        return treeElement;
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaTreeGenerator$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$intellij$psi$impl$source$PsiJavaCodeReferenceElementImpl$Kind;

        static {
            int[] iArr = new int[PsiJavaCodeReferenceElementImpl.Kind.values().length];
            $SwitchMap$com$intellij$psi$impl$source$PsiJavaCodeReferenceElementImpl$Kind = iArr;
            try {
                iArr[PsiJavaCodeReferenceElementImpl.Kind.CLASS_OR_PACKAGE_NAME_KIND.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$intellij$psi$impl$source$PsiJavaCodeReferenceElementImpl$Kind[PsiJavaCodeReferenceElementImpl.Kind.CLASS_NAME_KIND.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$intellij$psi$impl$source$PsiJavaCodeReferenceElementImpl$Kind[PsiJavaCodeReferenceElementImpl.Kind.CLASS_IN_QUALIFIED_NEW_KIND.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$intellij$psi$impl$source$PsiJavaCodeReferenceElementImpl$Kind[PsiJavaCodeReferenceElementImpl.Kind.CLASS_FQ_NAME_KIND.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$intellij$psi$impl$source$PsiJavaCodeReferenceElementImpl$Kind[PsiJavaCodeReferenceElementImpl.Kind.CLASS_FQ_OR_PACKAGE_NAME_KIND.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    private static LeafElement createLeafFromText(String str, CharTable charTable, PsiManager psiManager, PsiElement psiElement, IElementType iElementType) {
        return Factory.createSingleLeafElement(iElementType, str, 0, str.length(), charTable, psiManager, CodeEditUtil.isNodeGenerated(psiElement.getNode()));
    }

    private static TreeElement markGeneratedIfNeeded(PsiElement psiElement, TreeElement treeElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(3);
        }
        if (treeElement == null) {
            $$$reportNull$$$0(4);
        }
        if (CodeEditUtil.isNodeGenerated(psiElement.getNode())) {
            treeElement.acceptTree(new GeneratedMarkerVisitor());
        }
        return treeElement;
    }

    private static TreeElement createReference(Project project, String str, boolean z) {
        TreeElement treeElement = (TreeElement) JavaPsiFacade.getInstance(project).getParserFacade().createReferenceFromText(str, null).getNode();
        if (z) {
            treeElement.acceptTree(new GeneratedMarkerVisitor());
        }
        return treeElement;
    }

    private static TreeElement createReferenceExpression(Project project, String str, PsiElement psiElement) {
        return (TreeElement) JavaPsiFacade.getInstance(project).getParserFacade().createExpressionFromText(str, psiElement).getNode();
    }

    private static void encodeInfoInTypeElement(ASTNode aSTNode, PsiType psiType) {
        PsiClassType.ClassResolveResult classResolveResultResolveGenerics;
        PsiClass element;
        if (psiType instanceof PsiPrimitiveType) {
            return;
        }
        Logger logger = LOG;
        logger.assertTrue(aSTNode.getElementType() == JavaElementType.TYPE);
        if (psiType instanceof PsiArrayType) {
            ASTNode firstChildNode = aSTNode.getFirstChildNode();
            logger.assertTrue(firstChildNode.getElementType() == JavaElementType.TYPE);
            encodeInfoInTypeElement(firstChildNode, psiType.getDeepComponentType());
            return;
        }
        if (psiType instanceof PsiWildcardType) {
            PsiType bound = ((PsiWildcardType) psiType).getBound();
            if (bound == null) {
                return;
            }
            ASTNode lastChildNode = aSTNode.getLastChildNode();
            if (lastChildNode.getElementType() != JavaElementType.TYPE) {
                return;
            }
            encodeInfoInTypeElement(lastChildNode, bound);
            return;
        }
        if (psiType instanceof PsiCapturedWildcardType) {
            PsiType bound2 = ((PsiCapturedWildcardType) psiType).getWildcard().getBound();
            if (bound2 == null) {
                return;
            }
            ASTNode lastChildNode2 = aSTNode.getLastChildNode();
            if (lastChildNode2.getElementType() != JavaElementType.TYPE) {
                return;
            }
            encodeInfoInTypeElement(lastChildNode2, bound2);
            return;
        }
        if (psiType instanceof PsiIntersectionType) {
            encodeInfoInTypeElement(aSTNode, ((PsiIntersectionType) psiType).getRepresentative());
            return;
        }
        if (!(psiType instanceof PsiClassType) || (element = (classResolveResultResolveGenerics = ((PsiClassType) psiType).resolveGenerics()).getElement()) == null) {
            return;
        }
        if (element instanceof PsiAnonymousClass) {
            encodeInfoInTypeElement(aSTNode, ((PsiAnonymousClass) element).getBaseClassType());
            return;
        }
        ASTNode aSTNodeFindChildByType = aSTNode.findChildByType(JavaElementType.JAVA_CODE_REFERENCE);
        if (aSTNodeFindChildByType instanceof CompositeElement) {
            encodeClassTypeInfoInReference((CompositeElement) aSTNodeFindChildByType, classResolveResultResolveGenerics.getElement(), classResolveResultResolveGenerics.getSubstitutor());
        }
    }

    private static void encodeClassTypeInfoInReference(CompositeElement compositeElement, PsiClass psiClass, PsiSubstitutor psiSubstitutor) {
        PsiClass containingClass;
        if (compositeElement == null) {
            $$$reportNull$$$0(5);
        }
        compositeElement.putCopyableUserData(REFERENCED_CLASS_KEY, psiClass);
        PsiTypeParameter[] typeParameters = psiClass.getTypeParameters();
        if (typeParameters.length == 0) {
            return;
        }
        int i = 0;
        for (ASTNode firstChildNode = compositeElement.findChildByRole(246).getFirstChildNode(); firstChildNode != null && i < typeParameters.length; firstChildNode = firstChildNode.getTreeNext()) {
            if (firstChildNode.getElementType() == JavaElementType.TYPE) {
                PsiType psiTypeSubstitute = psiSubstitutor.substitute(typeParameters[i]);
                if (psiTypeSubstitute != null) {
                    encodeInfoInTypeElement(firstChildNode, psiTypeSubstitute);
                }
                i++;
            }
        }
        ASTNode aSTNodeFindChildByRole = compositeElement.findChildByRole(54);
        if (aSTNodeFindChildByRole == null || psiClass.hasModifierProperty("static") || (containingClass = psiClass.getContainingClass()) == null) {
            return;
        }
        encodeClassTypeInfoInReference((CompositeElement) aSTNodeFindChildByRole, containingClass, psiSubstitutor);
    }
}
