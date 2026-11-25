package org.jetbrains.kotlin.com.intellij.psi.impl.source;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.jetbrains.kotlin.com.intellij.lang.LighterAST;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressManager;
import org.jetbrains.kotlin.com.intellij.openapi.util.Condition;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LightTreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.com.intellij.util.containers.JBIterable;

/* JADX INFO: loaded from: classes3.dex */
public class FileLocalResolver {
    private final LighterAST myTree;

    /* JADX WARN: Code duplicated, block: B:11:0x0018  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 1 && i != 3 && i != 4 && i != 5 && i != 6 && i != 7) {
            switch (i) {
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                case 17:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 1 && i != 3 && i != 4 && i != 5 && i != 6 && i != 7) {
            switch (i) {
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                case 17:
                    i2 = 2;
                    break;
                default:
                    i2 = 3;
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/FileLocalResolver";
                break;
            case 2:
            case 9:
            case 10:
                objArr[0] = "ref";
                break;
            case 8:
            default:
                objArr[0] = org.eclipse.jgit.lib.Constants.TYPE_TREE;
                break;
            case 18:
            case 19:
                objArr[0] = PsiKeyword.VAR;
                break;
        }
        if (i == 1) {
            objArr[1] = "getLightTree";
        } else if (i != 3 && i != 4 && i != 5 && i != 6 && i != 7) {
            switch (i) {
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                    objArr[1] = "getDeclarations";
                    break;
                case 16:
                    objArr[1] = "walkChildrenScopes";
                    break;
                case 17:
                    objArr[1] = "before";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/FileLocalResolver";
                    break;
            }
        } else {
            objArr[1] = "resolveLocally";
        }
        switch (i) {
            case 1:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
                break;
            case 2:
                objArr[2] = "resolveLocally";
                break;
            case 8:
            case 9:
                objArr[2] = "canResolveToLocalVariable";
                break;
            case 10:
                objArr[2] = "hasQualifier";
                break;
            case 18:
            case 19:
                objArr[2] = "getShortClassTypeName";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 3 && i != 4 && i != 5 && i != 6 && i != 7) {
            switch (i) {
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                case 17:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    public FileLocalResolver(LighterAST lighterAST) {
        if (lighterAST == null) {
            $$$reportNull$$$0(0);
        }
        this.myTree = lighterAST;
    }

    public LighterAST getLightTree() {
        LighterAST lighterAST = this.myTree;
        if (lighterAST == null) {
            $$$reportNull$$$0(1);
        }
        return lighterAST;
    }

    public LightResolveResult resolveLocally(LighterASTNode lighterASTNode) {
        if (lighterASTNode == null) {
            $$$reportNull$$$0(2);
        }
        String nameIdentifierText = JavaLightTreeUtil.getNameIdentifierText(this.myTree, lighterASTNode);
        if (nameIdentifierText == null) {
            LightResolveResult lightResolveResult = LightResolveResult.UNKNOWN;
            if (lightResolveResult == null) {
                $$$reportNull$$$0(3);
            }
            return lightResolveResult;
        }
        if (!canResolveToLocalVariable(this.myTree, lighterASTNode)) {
            LightResolveResult lightResolveResult2 = LightResolveResult.NON_LOCAL;
            if (lightResolveResult2 == null) {
                $$$reportNull$$$0(4);
            }
            return lightResolveResult2;
        }
        boolean z = false;
        while (true) {
            ProgressManager.checkCanceled();
            LighterASTNode parent = this.myTree.getParent(lighterASTNode);
            if (parent == null) {
                LightResolveResult lightResolveResult3 = LightResolveResult.NON_LOCAL;
                if (lightResolveResult3 == null) {
                    $$$reportNull$$$0(5);
                }
                return lightResolveResult3;
            }
            for (LighterASTNode lighterASTNode2 : getDeclarations(parent, lighterASTNode)) {
                if (nameIdentifierText.equals(JavaLightTreeUtil.getNameIdentifierText(this.myTree, lighterASTNode2))) {
                    if (z) {
                        IElementType tokenType = lighterASTNode2.getTokenType();
                        LightResolveResult lightResolveResult4 = (tokenType == JavaElementType.FIELD || tokenType == JavaElementType.RECORD_COMPONENT) ? LightResolveResult.NON_LOCAL : LightResolveResult.UNKNOWN;
                        if (lightResolveResult4 == null) {
                            $$$reportNull$$$0(6);
                        }
                        return lightResolveResult4;
                    }
                    LightResolveResult lightResolveResultResolved = LightResolveResult.resolved(lighterASTNode2);
                    if (lightResolveResultResolved == null) {
                        $$$reportNull$$$0(7);
                    }
                    return lightResolveResultResolved;
                }
            }
            z |= parent.getTokenType() == JavaElementType.CLASS || parent.getTokenType() == JavaElementType.ANONYMOUS_CLASS;
            lighterASTNode = parent;
        }
    }

    private boolean canResolveToLocalVariable(LighterAST lighterAST, LighterASTNode lighterASTNode) {
        if (lighterAST == null) {
            $$$reportNull$$$0(8);
        }
        if (lighterASTNode == null) {
            $$$reportNull$$$0(9);
        }
        LighterASTNode parent = lighterAST.getParent(lighterASTNode);
        return (parent == null || parent.getTokenType() == JavaElementType.METHOD_CALL_EXPRESSION || hasQualifier(lighterASTNode)) ? false : true;
    }

    private boolean hasQualifier(LighterASTNode lighterASTNode) {
        if (lighterASTNode == null) {
            $$$reportNull$$$0(10);
        }
        return LightTreeUtil.firstChildOfType(this.myTree, lighterASTNode, ElementType.EXPRESSION_BIT_SET) != null;
    }

    private Iterable<LighterASTNode> getDeclarations(LighterASTNode lighterASTNode, LighterASTNode lighterASTNode2) {
        IElementType tokenType = lighterASTNode.getTokenType();
        if (tokenType == JavaElementType.CODE_BLOCK) {
            return walkChildrenScopes(before(LightTreeUtil.getChildrenOfType(this.myTree, lighterASTNode, JavaElementType.DECLARATION_STATEMENT), lighterASTNode2));
        }
        if (tokenType == JavaElementType.DECLARATION_STATEMENT) {
            return before(LightTreeUtil.getChildrenOfType(this.myTree, lighterASTNode, JavaElementType.LOCAL_VARIABLE), lighterASTNode2);
        }
        if (tokenType == JavaElementType.FOR_STATEMENT) {
            return walkChildrenScopes(before(LightTreeUtil.getChildrenOfType(this.myTree, lighterASTNode, ElementType.JAVA_STATEMENT_BIT_SET), lighterASTNode2));
        }
        if (tokenType == JavaElementType.FOREACH_STATEMENT || tokenType == JavaElementType.CATCH_SECTION) {
            JBIterable jBIterableOf = JBIterable.of(LightTreeUtil.firstChildOfType(this.myTree, lighterASTNode, JavaElementType.PARAMETER));
            if (jBIterableOf == null) {
                $$$reportNull$$$0(11);
            }
            return jBIterableOf;
        }
        if (tokenType == JavaElementType.TRY_STATEMENT) {
            return walkChildrenScopes(before(LightTreeUtil.getChildrenOfType(this.myTree, lighterASTNode, JavaElementType.RESOURCE_LIST), lighterASTNode2));
        }
        if (tokenType == JavaElementType.RESOURCE_LIST) {
            return before(LightTreeUtil.getChildrenOfType(this.myTree, lighterASTNode, JavaElementType.RESOURCE_VARIABLE), lighterASTNode2);
        }
        if (tokenType == JavaElementType.CLASS) {
            List<LighterASTNode> childrenOfType = LightTreeUtil.getChildrenOfType(this.myTree, lighterASTNode, JavaElementType.FIELD);
            LighterASTNode lighterASTNodeFirstChildOfType = LightTreeUtil.firstChildOfType(this.myTree, lighterASTNode, JavaElementType.RECORD_HEADER);
            if (lighterASTNodeFirstChildOfType == null) {
                if (childrenOfType == null) {
                    $$$reportNull$$$0(12);
                }
                return childrenOfType;
            }
            JBIterable jBIterableAppend = JBIterable.from(childrenOfType).append((Iterable) LightTreeUtil.getChildrenOfType(this.myTree, lighterASTNodeFirstChildOfType, JavaElementType.RECORD_COMPONENT));
            if (jBIterableAppend == null) {
                $$$reportNull$$$0(13);
            }
            return jBIterableAppend;
        }
        if (tokenType == JavaElementType.LAMBDA_EXPRESSION || tokenType == JavaElementType.METHOD) {
            LighterASTNode lighterASTNodeFirstChildOfType2 = LightTreeUtil.firstChildOfType(this.myTree, lighterASTNode, JavaElementType.PARAMETER_LIST);
            List<LighterASTNode> listEmptyList = lighterASTNodeFirstChildOfType2 == null ? ContainerUtil.emptyList() : LightTreeUtil.getChildrenOfType(this.myTree, lighterASTNodeFirstChildOfType2, JavaElementType.PARAMETER);
            if (listEmptyList == null) {
                $$$reportNull$$$0(14);
            }
            return listEmptyList;
        }
        List listEmptyList2 = Collections.emptyList();
        if (listEmptyList2 == null) {
            $$$reportNull$$$0(15);
        }
        return listEmptyList2;
    }

    private JBIterable<LighterASTNode> walkChildrenScopes(JBIterable<LighterASTNode> jBIterable) {
        JBIterable jBIterableFlatMap = jBIterable.flatMap(new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.FileLocalResolver$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return this.f$0.m6643x89ace57((LighterASTNode) obj);
            }
        });
        if (jBIterableFlatMap == null) {
            $$$reportNull$$$0(16);
        }
        return jBIterableFlatMap;
    }

    /* JADX INFO: renamed from: lambda$walkChildrenScopes$0$org-jetbrains-kotlin-com-intellij-psi-impl-source-FileLocalResolver, reason: not valid java name */
    /* synthetic */ Iterable m6643x89ace57(LighterASTNode lighterASTNode) {
        return getDeclarations(lighterASTNode, null);
    }

    private static JBIterable<LighterASTNode> before(List<LighterASTNode> list, final LighterASTNode lighterASTNode) {
        JBIterable<LighterASTNode> jBIterableFilter = JBIterable.from(list).filter(new Condition() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.FileLocalResolver$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
            public final boolean value(Object obj) {
                return FileLocalResolver.lambda$before$1(lighterASTNode, (LighterASTNode) obj);
            }
        });
        if (jBIterableFilter == null) {
            $$$reportNull$$$0(17);
        }
        return jBIterableFilter;
    }

    static /* synthetic */ boolean lambda$before$1(LighterASTNode lighterASTNode, LighterASTNode lighterASTNode2) {
        return lighterASTNode == null || lighterASTNode2.getStartOffset() < lighterASTNode.getStartOffset();
    }

    public String getShortClassTypeName(LighterASTNode lighterASTNode) {
        if (lighterASTNode == null) {
            $$$reportNull$$$0(18);
        }
        return getShortClassTypeName(lighterASTNode, 0);
    }

    public String getShortClassTypeName(LighterASTNode lighterASTNode, int i) {
        if (lighterASTNode == null) {
            $$$reportNull$$$0(19);
        }
        LighterASTNode lighterASTNodeFirstChildOfType = LightTreeUtil.firstChildOfType(this.myTree, lighterASTNode, JavaElementType.TYPE);
        while (i > 0) {
            if (LightTreeUtil.firstChildOfType(this.myTree, lighterASTNodeFirstChildOfType, JavaTokenType.LBRACKET) == null) {
                return null;
            }
            lighterASTNodeFirstChildOfType = LightTreeUtil.firstChildOfType(this.myTree, lighterASTNodeFirstChildOfType, JavaElementType.TYPE);
            i--;
        }
        LighterASTNode lighterASTNodeFirstChildOfType2 = LightTreeUtil.firstChildOfType(this.myTree, lighterASTNodeFirstChildOfType, JavaElementType.JAVA_CODE_REFERENCE);
        String nameIdentifierText = JavaLightTreeUtil.getNameIdentifierText(this.myTree, lighterASTNodeFirstChildOfType2);
        if (nameIdentifierText == null) {
            return null;
        }
        if (LightTreeUtil.firstChildOfType(this.myTree, lighterASTNodeFirstChildOfType2, JavaElementType.JAVA_CODE_REFERENCE) == null && isTypeParameter(nameIdentifierText, lighterASTNode)) {
            return null;
        }
        return nameIdentifierText;
    }

    private boolean isTypeParameter(String str, LighterASTNode lighterASTNode) {
        while (lighterASTNode != null) {
            if (lighterASTNode.getTokenType() == JavaElementType.CLASS || lighterASTNode.getTokenType() == JavaElementType.METHOD) {
                if (hasOwnTypeParameter(str, lighterASTNode)) {
                    return true;
                }
                if (isStatic(lighterASTNode)) {
                    return false;
                }
            }
            lighterASTNode = this.myTree.getParent(lighterASTNode);
        }
        return false;
    }

    private boolean hasOwnTypeParameter(String str, LighterASTNode lighterASTNode) {
        LighterASTNode lighterASTNodeFirstChildOfType = LightTreeUtil.firstChildOfType(this.myTree, lighterASTNode, JavaElementType.TYPE_PARAMETER_LIST);
        if (lighterASTNodeFirstChildOfType == null) {
            return false;
        }
        Iterator<LighterASTNode> it = LightTreeUtil.getChildrenOfType(this.myTree, lighterASTNodeFirstChildOfType, JavaElementType.TYPE_PARAMETER).iterator();
        while (it.hasNext()) {
            if (str.equals(JavaLightTreeUtil.getNameIdentifierText(this.myTree, it.next()))) {
                return true;
            }
        }
        return false;
    }

    private boolean isStatic(LighterASTNode lighterASTNode) {
        LighterASTNode lighterASTNodeFirstChildOfType = LightTreeUtil.firstChildOfType(this.myTree, lighterASTNode, JavaElementType.MODIFIER_LIST);
        return (lighterASTNodeFirstChildOfType == null || LightTreeUtil.firstChildOfType(this.myTree, lighterASTNodeFirstChildOfType, JavaTokenType.STATIC_KEYWORD) == null) ? false : true;
    }

    public static class LightResolveResult {
        public static final LightResolveResult UNKNOWN = new LightResolveResult();
        public static final LightResolveResult NON_LOCAL = new LightResolveResult();

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "target", "org/jetbrains/kotlin/com/intellij/psi/impl/source/FileLocalResolver$LightResolveResult", "resolved"));
        }

        public LighterASTNode getTarget() {
            return null;
        }

        static LightResolveResult resolved(final LighterASTNode lighterASTNode) {
            if (lighterASTNode == null) {
                $$$reportNull$$$0(0);
            }
            return new LightResolveResult() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.FileLocalResolver.LightResolveResult.1
                @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.FileLocalResolver.LightResolveResult
                public LighterASTNode getTarget() {
                    return lighterASTNode;
                }
            };
        }
    }
}
