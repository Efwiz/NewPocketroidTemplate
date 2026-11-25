package org.jetbrains.kotlin.psi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.google.common.collect.Lists;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiComment;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiWhiteSpace;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.util.codeInsight.CommentUtilCore;
import org.jetbrains.kotlin.kdoc.psi.api.KDocElement;
import org.jetbrains.kotlin.lexer.KtToken;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.parsing.KotlinExpressionParsing;
import org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt;
import org.jetbrains.kotlin.resolve.StatementFilter;
import org.jetbrains.kotlin.resolve.StatementFilterKt;

/* JADX INFO: loaded from: classes6.dex */
public class KtPsiUtil {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    public interface KtExpressionWrapper {
        /* JADX INFO: renamed from: getBaseExpression */
        KtExpression getExpression();
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 4:
            case 5:
            case 7:
            case 9:
            case 10:
            case 11:
            case 13:
            case 14:
                str = "@NotNull method %s.%s must not return null";
                break;
            case 6:
            case 8:
            case 12:
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 4:
            case 5:
            case 7:
            case 9:
            case 10:
            case 11:
            case 13:
            case 14:
                i2 = 2;
                break;
            case 6:
            case 8:
            case 12:
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
                objArr[0] = "visitor";
                break;
            case 2:
            case 3:
            case 20:
            case 25:
            case 30:
            case 31:
            case 32:
            case 38:
            case 39:
            case 40:
            case 60:
            case 61:
                objArr[0] = "expression";
                break;
            case 4:
            case 5:
            case 7:
            case 9:
            case 10:
            case 11:
            case 13:
            case 14:
                objArr[0] = "org/jetbrains/kotlin/psi/KtPsiUtil";
                break;
            case 6:
                objArr[0] = "unreachableElements";
                break;
            case 8:
            case 12:
                objArr[0] = "quoted";
                break;
            case 15:
            case 54:
                objArr[0] = "owner";
                break;
            case 16:
            case 41:
                objArr[0] = "block";
                break;
            case 17:
                objArr[0] = "aClass";
                break;
            case 18:
                objArr[0] = "importedReference";
                break;
            case 19:
                objArr[0] = "nameExpression";
                break;
            case 21:
            case 22:
                objArr[0] = "namedDeclaration";
                break;
            case 23:
            case 26:
            case 56:
            case 58:
            case 59:
                objArr[0] = "declaration";
                break;
            case 24:
                objArr[0] = "parentTypes";
                break;
            case 27:
            case 28:
                objArr[0] = "classOrObject";
                break;
            case 29:
                objArr[0] = "ktParameter";
                break;
            case 33:
                objArr[0] = "innerExpression";
                break;
            case 34:
                objArr[0] = "currentInner";
                break;
            case 35:
                objArr[0] = "parentElement";
                break;
            case 36:
            case 37:
            case 44:
            case 45:
            case 47:
            case 52:
            case 55:
            case 64:
            default:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 42:
                objArr[0] = "varName";
                break;
            case 43:
                objArr[0] = "whenExpression";
                break;
            case 46:
                objArr[0] = "word";
                break;
            case 48:
                objArr[0] = "upperBound";
                break;
            case 49:
                objArr[0] = "root";
                break;
            case 50:
                objArr[0] = "elementTypes";
                break;
            case 51:
                objArr[0] = "predicate";
                break;
            case 53:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
            case 57:
                objArr[0] = "propertyOrFunction";
                break;
            case 62:
                objArr[0] = "functionLiteral";
                break;
            case 63:
                objArr[0] = "statementFilter";
                break;
            case 65:
            case 66:
            case 67:
                objArr[0] = "binaryExpression";
                break;
        }
        switch (i) {
            case 4:
                objArr[1] = "safeDeparenthesize";
                break;
            case 5:
                objArr[1] = "safeName";
                break;
            case 6:
            case 8:
            case 12:
            default:
                objArr[1] = "org/jetbrains/kotlin/psi/KtPsiUtil";
                break;
            case 7:
                objArr[1] = "findRootExpressions";
                break;
            case 9:
            case 10:
            case 11:
                objArr[1] = "unquoteIdentifier";
                break;
            case 13:
            case 14:
                objArr[1] = "unquoteIdentifierOrFieldReference";
                break;
        }
        switch (i) {
            case 2:
            case 3:
                objArr[2] = "safeDeparenthesize";
                break;
            case 4:
            case 5:
            case 7:
            case 9:
            case 10:
            case 11:
            case 13:
            case 14:
                break;
            case 6:
                objArr[2] = "findRootExpressions";
                break;
            case 8:
                objArr[2] = "unquoteIdentifier";
                break;
            case 12:
                objArr[2] = "unquoteIdentifierOrFieldReference";
                break;
            case 15:
                objArr[2] = "isDeprecated";
                break;
            case 16:
            case 17:
                objArr[2] = "getDirectParentOfTypeForBlock";
                break;
            case 18:
                objArr[2] = "getLastReference";
                break;
            case 19:
                objArr[2] = "isSelectorInQualified";
                break;
            case 20:
                objArr[2] = "isLHSOfDot";
                break;
            case 21:
                objArr[2] = "isScriptDeclaration";
                break;
            case 22:
                objArr[2] = "getScript";
                break;
            case 23:
                objArr[2] = "isRemovableVariableDeclaration";
                break;
            case 24:
                objArr[2] = "getTopmostParentOfTypes";
                break;
            case 25:
                objArr[2] = "isNullConstant";
                break;
            case 26:
                objArr[2] = "isAbstract";
                break;
            case 27:
                objArr[2] = "isTrait";
                break;
            case 28:
                objArr[2] = "getOutermostClassOrObject";
                break;
            case 29:
                objArr[2] = "getClassIfParameterIsProperty";
                break;
            case 30:
                objArr[2] = "getOperation";
                break;
            case 31:
                objArr[2] = "getPriority";
                break;
            case 32:
                objArr[2] = "areParenthesesUseless";
                break;
            case 33:
            case 34:
            case 35:
                objArr[2] = "areParenthesesNecessary";
                break;
            case 36:
                objArr[2] = "isAssignment";
                break;
            case 37:
                objArr[2] = "isOrdinaryAssignment";
                break;
            case 38:
                objArr[2] = "isCast";
                break;
            case 39:
                objArr[2] = "isSafeCast";
                break;
            case 40:
                objArr[2] = "isUnsafeCast";
                break;
            case 41:
            case 42:
                objArr[2] = "checkVariableDeclarationInBlock";
                break;
            case 43:
                objArr[2] = "checkWhenExpressionHasSingleElse";
                break;
            case 44:
                objArr[2] = "prevLeafIgnoringWhitespaceAndComments";
                break;
            case 45:
            case 46:
                objArr[2] = "getPreviousWord";
                break;
            case 47:
            case 48:
                objArr[2] = "getOutermostParent";
                break;
            case 49:
            case 50:
                objArr[2] = "getLastChildByType";
                break;
            case 51:
                objArr[2] = "getOutermostDescendantElement";
                break;
            case 52:
            case 53:
                objArr[2] = "findChildByType";
                break;
            case 54:
                objArr[2] = "replaceModifierList";
                break;
            case 55:
                objArr[2] = "getPackageName";
                break;
            case 56:
            case 58:
                objArr[2] = "getEnclosingElementForLocalDeclaration";
                break;
            case 57:
                objArr[2] = "isMemberOfObjectExpression";
                break;
            case 59:
                objArr[2] = "isLocal";
                break;
            case 60:
                objArr[2] = "getOperationToken";
                break;
            case 61:
                objArr[2] = "getParentCallIfPresent";
                break;
            case 62:
                objArr[2] = "isLabeledFunctionLiteral";
                break;
            case 63:
                objArr[2] = "getLastElementDeparenthesized";
                break;
            case 64:
                objArr[2] = "isStatement";
                break;
            case 65:
                objArr[2] = "isInOrNotInOperation";
                break;
            case 66:
                objArr[2] = "isNotInOperation";
                break;
            case 67:
                objArr[2] = "isInOperation";
                break;
            default:
                objArr[2] = "visitChildren";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 4:
            case 5:
            case 7:
            case 9:
            case 10:
            case 11:
            case 13:
            case 14:
                throw new IllegalStateException(str2);
            case 6:
            case 8:
            case 12:
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    private KtPsiUtil() {
    }

    public static <D> void visitChildren(KtElement ktElement, KtVisitor<Void, D> ktVisitor, D d) {
        if (ktElement == null) {
            $$$reportNull$$$0(0);
        }
        if (ktVisitor == null) {
            $$$reportNull$$$0(1);
        }
        for (PsiElement firstChild = ktElement.getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
            if (firstChild instanceof KtElement) {
                ((KtElement) firstChild).accept(ktVisitor, d);
            }
        }
    }

    public static KtExpression safeDeparenthesize(KtExpression ktExpression) {
        if (ktExpression == null) {
            $$$reportNull$$$0(2);
        }
        return safeDeparenthesize(ktExpression, false);
    }

    public static KtExpression safeDeparenthesize(KtExpression ktExpression, boolean z) {
        if (ktExpression == null) {
            $$$reportNull$$$0(3);
        }
        KtExpression ktExpressionDeparenthesize = deparenthesize(ktExpression, z);
        if (ktExpressionDeparenthesize != null) {
            ktExpression = ktExpressionDeparenthesize;
        }
        if (ktExpression == null) {
            $$$reportNull$$$0(4);
        }
        return ktExpression;
    }

    public static KtExpression deparenthesize(KtExpression ktExpression) {
        return deparenthesize(ktExpression, false);
    }

    public static KtExpression deparenthesize(KtExpression ktExpression, boolean z) {
        while (true) {
            KtExpression ktExpressionDeparenthesizeOnce = deparenthesizeOnce(ktExpression, z);
            if (ktExpressionDeparenthesizeOnce == ktExpression) {
                return ktExpressionDeparenthesizeOnce;
            }
            ktExpression = ktExpressionDeparenthesizeOnce;
        }
    }

    public static KtExpression deparenthesizeOnce(KtExpression ktExpression) {
        return deparenthesizeOnce(ktExpression, false);
    }

    public static KtExpression deparenthesizeOnce(KtExpression ktExpression, boolean z) {
        if ((ktExpression instanceof KtAnnotatedExpression) && !z) {
            return ((KtAnnotatedExpression) ktExpression).getBaseExpression();
        }
        if (ktExpression instanceof KtLabeledExpression) {
            return ((KtLabeledExpression) ktExpression).getBaseExpression();
        }
        if (ktExpression instanceof KtExpressionWrapper) {
            return ((KtExpressionWrapper) ktExpression).getExpression();
        }
        return ktExpression instanceof KtParenthesizedExpression ? ((KtParenthesizedExpression) ktExpression).getExpression() : ktExpression;
    }

    public static Name safeName(String str) {
        Name nameIdentifier = str == null ? SpecialNames.NO_NAME_PROVIDED : Name.identifier(str);
        if (nameIdentifier == null) {
            $$$reportNull$$$0(5);
        }
        return nameIdentifier;
    }

    public static Set<KtElement> findRootExpressions(Collection<KtElement> collection) {
        if (collection == null) {
            $$$reportNull$$$0(6);
        }
        HashSet hashSet = new HashSet();
        final HashSet hashSet2 = new HashSet();
        KtVisitorVoid ktVisitorVoid = new KtVisitorVoid() { // from class: org.jetbrains.kotlin.psi.KtPsiUtil.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "org/jetbrains/kotlin/psi/KtPsiUtil$1", "visitKtElement"));
            }

            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitKtElement(KtElement ktElement) {
                if (ktElement == null) {
                    $$$reportNull$$$0(0);
                }
                if (hashSet2.add(ktElement)) {
                    ktElement.acceptChildren(this);
                }
            }
        };
        for (KtElement ktElement : collection) {
            if (!hashSet2.contains(ktElement)) {
                ktElement.acceptChildren(ktVisitorVoid);
                hashSet.removeAll(hashSet2);
                hashSet.add(ktElement);
            }
        }
        return hashSet;
    }

    public static String unquoteIdentifier(String str) {
        if (str == null) {
            $$$reportNull$$$0(8);
        }
        if (str.indexOf(96) < 0) {
            if (str == null) {
                $$$reportNull$$$0(9);
            }
            return str;
        }
        if (!str.startsWith("`") || !str.endsWith("`") || str.length() < 2) {
            if (str == null) {
                $$$reportNull$$$0(11);
            }
            return str;
        }
        String strSubstring = str.substring(1, str.length() - 1);
        if (strSubstring == null) {
            $$$reportNull$$$0(10);
        }
        return strSubstring;
    }

    public static String unquoteIdentifierOrFieldReference(String str) {
        if (str == null) {
            $$$reportNull$$$0(12);
        }
        if (str.indexOf(96) < 0) {
            if (str == null) {
                $$$reportNull$$$0(13);
            }
            return str;
        }
        if (str.startsWith("$")) {
            String str2 = "$" + unquoteIdentifier(str.substring(1));
            if (str2 == null) {
                $$$reportNull$$$0(14);
            }
            return str2;
        }
        return unquoteIdentifier(str);
    }

    public static boolean isDeprecated(KtModifierListOwner ktModifierListOwner) {
        if (ktModifierListOwner == null) {
            $$$reportNull$$$0(15);
        }
        KtModifierList modifierList = ktModifierListOwner.getModifierList();
        if (modifierList == null) {
            return false;
        }
        Iterator<KtAnnotationEntry> it = modifierList.getAnnotationEntries().iterator();
        while (it.hasNext()) {
            if (StandardNames.FqNames.deprecated.shortName().equals(it.next().getShortName())) {
                return true;
            }
        }
        return false;
    }

    public static <T extends PsiElement> T getDirectParentOfTypeForBlock(KtBlockExpression ktBlockExpression, Class<T> cls) {
        if (ktBlockExpression == null) {
            $$$reportNull$$$0(16);
        }
        if (cls == null) {
            $$$reportNull$$$0(17);
        }
        T t = (T) PsiTreeUtil.getParentOfType(ktBlockExpression, cls);
        if (t instanceof KtIfExpression) {
            KtIfExpression ktIfExpression = (KtIfExpression) t;
            if (ktIfExpression.getElse() == ktBlockExpression || ktIfExpression.getThen() == ktBlockExpression) {
                return t;
            }
        }
        if (t instanceof KtWhenExpression) {
            Iterator<KtWhenEntry> it = ((KtWhenExpression) t).getEntries().iterator();
            while (it.hasNext()) {
                if (it.next().getExpression() == ktBlockExpression) {
                    return t;
                }
            }
        }
        if ((t instanceof KtFunctionLiteral) && ((KtFunctionLiteral) t).getBodyExpression() == ktBlockExpression) {
            return t;
        }
        if (!(t instanceof KtTryExpression)) {
            return null;
        }
        KtTryExpression ktTryExpression = (KtTryExpression) t;
        if (ktTryExpression.getTryBlock() == ktBlockExpression) {
            return t;
        }
        Iterator<KtCatchClause> it2 = ktTryExpression.getCatchClauses().iterator();
        while (it2.hasNext()) {
            if (it2.next().getCatchBody() == ktBlockExpression) {
                return t;
            }
        }
        return null;
    }

    public static KtSimpleNameExpression getLastReference(KtExpression ktExpression) {
        if (ktExpression == null) {
            $$$reportNull$$$0(18);
        }
        KtElement qualifiedElementSelector = KtPsiUtilKt.getQualifiedElementSelector(ktExpression);
        if (qualifiedElementSelector instanceof KtSimpleNameExpression) {
            return (KtSimpleNameExpression) qualifiedElementSelector;
        }
        return null;
    }

    public static boolean isSelectorInQualified(KtSimpleNameExpression ktSimpleNameExpression) {
        if (ktSimpleNameExpression == null) {
            $$$reportNull$$$0(19);
        }
        KtElement qualifiedElement = KtPsiUtilKt.getQualifiedElement(ktSimpleNameExpression);
        return (qualifiedElement instanceof KtQualifiedExpression) || ((qualifiedElement instanceof KtUserType) && ((KtUserType) qualifiedElement).getQualifier() != null);
    }

    public static boolean isLHSOfDot(KtExpression ktExpression) {
        if (ktExpression == null) {
            $$$reportNull$$$0(20);
        }
        PsiElement parent = ktExpression.getLightParent();
        if (!(parent instanceof KtQualifiedExpression)) {
            return false;
        }
        KtQualifiedExpression ktQualifiedExpression = (KtQualifiedExpression) parent;
        return ktQualifiedExpression.getReceiverExpression() == ktExpression || isLHSOfDot(ktQualifiedExpression);
    }

    public static boolean isScriptDeclaration(KtDeclaration ktDeclaration) {
        if (ktDeclaration == null) {
            $$$reportNull$$$0(21);
        }
        return getScript(ktDeclaration) != null;
    }

    public static KtScript getScript(KtDeclaration ktDeclaration) {
        if (ktDeclaration == null) {
            $$$reportNull$$$0(22);
        }
        PsiElement parent = ktDeclaration.getLightParent();
        if (parent == null || !(parent.getLightParent() instanceof KtScript)) {
            return null;
        }
        return (KtScript) parent.getLightParent();
    }

    public static boolean isRemovableVariableDeclaration(KtDeclaration ktDeclaration) {
        if (ktDeclaration == null) {
            $$$reportNull$$$0(23);
        }
        if (!(ktDeclaration instanceof KtVariableDeclaration)) {
            return false;
        }
        boolean z = ktDeclaration instanceof KtProperty;
        return true;
    }

    @SafeVarargs
    public static PsiElement getTopmostParentOfTypes(PsiElement psiElement, Class<? extends PsiElement>... clsArr) {
        if (clsArr == null) {
            $$$reportNull$$$0(24);
        }
        if (psiElement instanceof PsiFile) {
            return null;
        }
        PsiElement parentOfType = PsiTreeUtil.getParentOfType(psiElement, clsArr);
        if (parentOfType instanceof PsiFile) {
            return parentOfType;
        }
        while (true) {
            PsiElement parentOfType2 = PsiTreeUtil.getParentOfType(parentOfType, clsArr);
            if (parentOfType2 == null) {
                return parentOfType;
            }
            parentOfType = parentOfType2;
        }
    }

    public static boolean isNullConstant(KtExpression ktExpression) {
        if (ktExpression == null) {
            $$$reportNull$$$0(25);
        }
        KtExpression ktExpressionDeparenthesize = deparenthesize(ktExpression);
        return (ktExpressionDeparenthesize instanceof KtConstantExpression) && ktExpressionDeparenthesize.getNode().getElementType() == KtNodeTypes.NULL;
    }

    public static boolean isTrueConstant(KtExpression ktExpression) {
        return isBooleanConstant(ktExpression) && ktExpression.getNode().findChildByType(KtTokens.TRUE_KEYWORD) != null;
    }

    public static boolean isFalseConstant(KtExpression ktExpression) {
        return isBooleanConstant(ktExpression) && ktExpression.getNode().findChildByType(KtTokens.FALSE_KEYWORD) != null;
    }

    public static boolean isBooleanConstant(KtExpression ktExpression) {
        return ktExpression != null && ktExpression.getNode().getElementType() == KtNodeTypes.BOOLEAN_CONSTANT;
    }

    public static boolean isAbstract(KtDeclarationWithBody ktDeclarationWithBody) {
        if (ktDeclarationWithBody == null) {
            $$$reportNull$$$0(26);
        }
        return ktDeclarationWithBody.getBodyExpression() == null;
    }

    public static KtExpression getExpressionOrLastStatementInBlock(KtExpression ktExpression) {
        return ktExpression instanceof KtBlockExpression ? getLastStatementInABlock((KtBlockExpression) ktExpression) : ktExpression;
    }

    public static KtExpression getLastStatementInABlock(KtBlockExpression ktBlockExpression) {
        if (ktBlockExpression == null) {
            return null;
        }
        List<KtExpression> statements = ktBlockExpression.getStatements();
        if (statements.isEmpty()) {
            return null;
        }
        return statements.get(statements.size() - 1);
    }

    public static boolean isTrait(KtClassOrObject ktClassOrObject) {
        if (ktClassOrObject == null) {
            $$$reportNull$$$0(27);
        }
        return (ktClassOrObject instanceof KtClass) && ((KtClass) ktClassOrObject).isInterface();
    }

    public static KtClassOrObject getOutermostClassOrObject(KtClassOrObject ktClassOrObject) {
        if (ktClassOrObject == null) {
            $$$reportNull$$$0(28);
        }
        while (true) {
            PsiElement parent = ktClassOrObject.getLightParent();
            if ((parent instanceof PsiFile) || !(parent instanceof KtClassBody)) {
                return ktClassOrObject;
            }
            ktClassOrObject = (KtClassOrObject) parent.getLightParent();
        }
    }

    public static KtClassOrObject getClassIfParameterIsProperty(KtParameter ktParameter) {
        if (ktParameter == null) {
            $$$reportNull$$$0(29);
        }
        if (!ktParameter.hasValOrVar()) {
            return null;
        }
        PsiElement parent = ktParameter.getLightParent().getLightParent();
        if (parent instanceof KtPrimaryConstructor) {
            return ((KtPrimaryConstructor) parent).getContainingClassOrObject();
        }
        return null;
    }

    private static IElementType getOperation(KtExpression ktExpression) {
        if (ktExpression == null) {
            $$$reportNull$$$0(30);
        }
        if (ktExpression instanceof KtQualifiedExpression) {
            return ((KtQualifiedExpression) ktExpression).getOperationSign();
        }
        if (ktExpression instanceof KtOperationExpression) {
            return ((KtOperationExpression) ktExpression).getOperationReference().getReferencedNameElementType();
        }
        return null;
    }

    private static int getPriority(KtExpression ktExpression) {
        if (ktExpression == null) {
            $$$reportNull$$$0(31);
        }
        int length = KotlinExpressionParsing.Precedence.values().length;
        int i = length + 1;
        if ((ktExpression instanceof KtPostfixExpression) || (ktExpression instanceof KtQualifiedExpression) || (ktExpression instanceof KtCallExpression) || (ktExpression instanceof KtArrayAccessExpression) || (ktExpression instanceof KtDoubleColonExpression)) {
            return length;
        }
        if ((ktExpression instanceof KtPrefixExpression) || (ktExpression instanceof KtLabeledExpression)) {
            return length - 1;
        }
        if (ktExpression instanceof KtIfExpression) {
            return KotlinExpressionParsing.Precedence.ASSIGNMENT.ordinal();
        }
        if (ktExpression instanceof KtSuperExpression) {
            return i;
        }
        if ((ktExpression instanceof KtDeclaration) || (ktExpression instanceof KtStatementExpression)) {
            return 0;
        }
        IElementType operation = getOperation(ktExpression);
        for (KotlinExpressionParsing.Precedence precedence : KotlinExpressionParsing.Precedence.values()) {
            if (precedence != KotlinExpressionParsing.Precedence.PREFIX && precedence != KotlinExpressionParsing.Precedence.POSTFIX && precedence.getOperations().contains(operation)) {
                return (i - precedence.ordinal()) - 1;
            }
        }
        return i;
    }

    public static boolean areParenthesesUseless(KtParenthesizedExpression ktParenthesizedExpression) {
        if (ktParenthesizedExpression == null) {
            $$$reportNull$$$0(32);
        }
        KtExpression expression = ktParenthesizedExpression.getExpression();
        if (expression == null) {
            return true;
        }
        PsiElement parent = ktParenthesizedExpression.getLightParent();
        if (parent instanceof KtElement) {
            return !areParenthesesNecessary(expression, ktParenthesizedExpression, (KtElement) parent);
        }
        return true;
    }

    public static boolean areParenthesesNecessary(KtExpression ktExpression, KtExpression ktExpression2, KtElement ktElement) {
        KtExpression selectorExpression;
        if (ktExpression == null) {
            $$$reportNull$$$0(33);
        }
        if (ktExpression2 == null) {
            $$$reportNull$$$0(34);
        }
        if (ktElement == null) {
            $$$reportNull$$$0(35);
        }
        if (ktElement instanceof KtDelegatedSuperTypeEntry) {
            return true;
        }
        if ((ktElement instanceof KtParenthesizedExpression) || (ktExpression instanceof KtParenthesizedExpression) || (ktElement instanceof KtPackageDirective) || (ktElement instanceof KtWhenExpression) || (ktExpression instanceof KtWhenExpression) || (ktElement instanceof KtCollectionLiteralExpression)) {
            return false;
        }
        if (ktExpression instanceof KtIfExpression) {
            if (ktElement instanceof KtQualifiedExpression) {
                return true;
            }
            for (PsiElement parent = ktElement; !(parent instanceof KtBlockExpression) && !(parent instanceof KtDeclaration) && !(parent instanceof KtStatementExpression) && !(parent instanceof KtFile); parent = parent.getLightParent()) {
                if (parent.getTextRange().getEndOffset() != ktExpression2.getTextRange().getEndOffset()) {
                    return ((parent instanceof KtParenthesizedExpression) || (parent instanceof KtValueArgumentList)) ? false : true;
                }
            }
        }
        if ((ktExpression instanceof KtLambdaExpression) && endWithParenthesisOrCallExpression(PsiTreeUtil.skipWhitespacesAndCommentsBackward(ktExpression2))) {
            return true;
        }
        if (ktElement instanceof KtCallExpression) {
            KtCallExpression ktCallExpression = (KtCallExpression) ktElement;
            if (ktExpression2 == ktCallExpression.getCalleeExpression()) {
                if ((ktExpression instanceof KtDotQualifiedExpression) && (selectorExpression = ((KtDotQualifiedExpression) ktExpression).getSelectorExpression()) != null) {
                    ktExpression = selectorExpression;
                }
                if (ktExpression instanceof KtSimpleNameExpression) {
                    return false;
                }
                if (KtPsiUtilKt.getQualifiedExpressionForSelector(ktElement) != null) {
                    return true;
                }
                boolean z = ktExpression instanceof KtCallExpression;
                if (z && ktCallExpression.getValueArgumentList() == null) {
                    return true;
                }
                return ((ktExpression instanceof KtThisExpression) || (ktExpression instanceof KtArrayAccessExpression) || (ktExpression instanceof KtConstantExpression) || (ktExpression instanceof KtStringTemplateExpression) || z) ? false : true;
            }
        }
        boolean z2 = ktElement instanceof KtValueArgument;
        if (z2) {
            KtValueArgument ktValueArgument = (KtValueArgument) PsiTreeUtil.getNextSiblingOfType(ktElement, KtValueArgument.class);
            KtExpression argumentExpression = ktValueArgument != null ? ktValueArgument.getArgumentExpression() : null;
            if ((ktExpression instanceof KtBinaryExpression) && ((KtBinaryExpression) ktExpression).getOperationToken() == KtTokens.LT && (argumentExpression instanceof KtBinaryExpression) && ((KtBinaryExpression) argumentExpression).getOperationToken() == KtTokens.GT) {
                return true;
            }
        }
        IElementType operation = getOperation(ktExpression);
        boolean z3 = ktExpression instanceof KtBinaryExpression;
        if (z3) {
            if ((ktElement instanceof KtBinaryExpression) && (((KtBinaryExpression) ktExpression).getRight() instanceof KtReturnExpression)) {
                return true;
            }
            if (operation != KtTokens.ELVIS && !z2 && !(ktElement instanceof KtParameter) && !(ktElement instanceof KtBlockStringTemplateEntry) && ((!(ktElement instanceof KtContainerNode) || (ktElement instanceof KtContainerNodeForControlStructureBody)) && isKeepBinaryExpressionParenthesized((KtBinaryExpression) ktExpression))) {
                return true;
            }
        }
        if (!(ktElement instanceof KtExpression)) {
            return false;
        }
        KtExpression ktExpression3 = (KtExpression) ktElement;
        IElementType operation2 = getOperation(ktExpression3);
        if ((ktElement instanceof KtReturnExpression) && ((ktExpression instanceof KtLabeledExpression) || (ktExpression instanceof KtAnnotatedExpression))) {
            return true;
        }
        if ((ktExpression instanceof KtBinaryExpressionWithTypeRHS) && operation2 == KtTokens.LT) {
            return true;
        }
        if (ktElement instanceof KtLabeledExpression) {
            return false;
        }
        boolean z4 = ktElement instanceof KtBinaryExpression;
        if (z4 && operation2 == KtTokens.ELVIS && !z3 && ktExpression2 == ((KtBinaryExpression) ktElement).getRight()) {
            return false;
        }
        if (z4 && operation2 == KtTokens.EQ && (ktExpression instanceof KtNamedFunction) && ktExpression2 == ((KtBinaryExpression) ktElement).getRight()) {
            return false;
        }
        int priority = getPriority(ktExpression);
        int priority2 = getPriority(ktExpression3);
        if (priority != priority2) {
            return priority < priority2;
        }
        if (z4) {
            return (operation == KtTokens.ANDAND || operation == KtTokens.OROR || ((KtBinaryExpression) ktElement).getRight() != ktExpression2) ? false : true;
        }
        if ((ktElement instanceof KtPrefixExpression) && (ktExpression instanceof KtPrefixExpression)) {
            if (operation2 == KtTokens.PLUS) {
                return operation == KtTokens.PLUS || operation == KtTokens.PLUSPLUS;
            }
            if (operation2 == KtTokens.MINUS) {
                return operation == KtTokens.MINUS || operation == KtTokens.MINUSMINUS;
            }
        }
        return false;
    }

    private static boolean endWithParenthesisOrCallExpression(PsiElement psiElement) {
        if (psiElement == null) {
            return false;
        }
        if (psiElement.getText().endsWith(KtTokens.RPAR.getValue()) || (psiElement instanceof KtCallExpression)) {
            return true;
        }
        PsiElement[] children = psiElement.getChildren();
        int length = children.length;
        if (length == 0) {
            return false;
        }
        return endWithParenthesisOrCallExpression(children[length - 1]);
    }

    private static boolean isKeepBinaryExpressionParenthesized(KtBinaryExpression ktBinaryExpression) {
        for (PsiElement firstChild = ktBinaryExpression.getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
            if ((firstChild instanceof PsiWhiteSpace) && firstChild.textContains('\n')) {
                return true;
            }
            if (firstChild instanceof KtOperationReferenceExpression) {
                break;
            }
        }
        if ((ktBinaryExpression.getRight() instanceof KtBinaryExpression) && isKeepBinaryExpressionParenthesized((KtBinaryExpression) ktBinaryExpression.getRight())) {
            return true;
        }
        return (ktBinaryExpression.getLeft() instanceof KtBinaryExpression) && isKeepBinaryExpressionParenthesized((KtBinaryExpression) ktBinaryExpression.getLeft());
    }

    public static boolean isAssignment(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(36);
        }
        return (psiElement instanceof KtBinaryExpression) && KtTokens.ALL_ASSIGNMENTS.contains(((KtBinaryExpression) psiElement).getOperationToken());
    }

    public static boolean isOrdinaryAssignment(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(37);
        }
        return (psiElement instanceof KtBinaryExpression) && ((KtBinaryExpression) psiElement).getOperationToken().equals(KtTokens.EQ);
    }

    public static boolean isCast(KtBinaryExpressionWithTypeRHS ktBinaryExpressionWithTypeRHS) {
        if (ktBinaryExpressionWithTypeRHS == null) {
            $$$reportNull$$$0(38);
        }
        return isSafeCast(ktBinaryExpressionWithTypeRHS) || isUnsafeCast(ktBinaryExpressionWithTypeRHS);
    }

    public static boolean isSafeCast(KtBinaryExpressionWithTypeRHS ktBinaryExpressionWithTypeRHS) {
        if (ktBinaryExpressionWithTypeRHS == null) {
            $$$reportNull$$$0(39);
        }
        return ktBinaryExpressionWithTypeRHS.getOperationReference().getReferencedNameElementType() == KtTokens.AS_SAFE;
    }

    public static boolean isUnsafeCast(KtBinaryExpressionWithTypeRHS ktBinaryExpressionWithTypeRHS) {
        if (ktBinaryExpressionWithTypeRHS == null) {
            $$$reportNull$$$0(40);
        }
        return ktBinaryExpressionWithTypeRHS.getOperationReference().getReferencedNameElementType() == KtTokens.AS_KEYWORD;
    }

    public static boolean checkVariableDeclarationInBlock(KtBlockExpression ktBlockExpression, String str) {
        if (ktBlockExpression == null) {
            $$$reportNull$$$0(41);
        }
        if (str == null) {
            $$$reportNull$$$0(42);
        }
        for (KtExpression ktExpression : ktBlockExpression.getStatements()) {
            if ((ktExpression instanceof KtVariableDeclaration) && ((KtVariableDeclaration) ktExpression).getNameAsSafeName().asString().equals(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkWhenExpressionHasSingleElse(KtWhenExpression ktWhenExpression) {
        if (ktWhenExpression == null) {
            $$$reportNull$$$0(43);
        }
        Iterator<KtWhenEntry> it = ktWhenExpression.getEntries().iterator();
        int i = 0;
        while (it.hasNext()) {
            if (it.next().isElse()) {
                i++;
            }
        }
        return i == 1;
    }

    public static PsiElement skipTrailingWhitespacesAndComments(PsiElement psiElement) {
        return PsiTreeUtil.skipSiblingsForward(psiElement, PsiWhiteSpace.class, PsiComment.class);
    }

    public static PsiElement prevLeafIgnoringWhitespaceAndComments(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(44);
        }
        PsiElement psiElementPrevLeaf = PsiTreeUtil.prevLeaf(psiElement, true);
        while (psiElementPrevLeaf != null && KtTokens.WHITE_SPACE_OR_COMMENT_BIT_SET.contains(psiElementPrevLeaf.getNode().getElementType())) {
            psiElementPrevLeaf = PsiTreeUtil.prevLeaf(psiElementPrevLeaf, true);
        }
        return psiElementPrevLeaf;
    }

    public static PsiElement getPreviousWord(PsiElement psiElement, String str) {
        if (psiElement == null) {
            $$$reportNull$$$0(45);
        }
        if (str == null) {
            $$$reportNull$$$0(46);
        }
        PsiElement psiElementPrevLeafIgnoringWhitespaceAndComments = prevLeafIgnoringWhitespaceAndComments(psiElement);
        if (psiElementPrevLeafIgnoringWhitespaceAndComments != null && psiElementPrevLeafIgnoringWhitespaceAndComments.getNode().getElementType() == KtTokens.IDENTIFIER && str.equals(psiElementPrevLeafIgnoringWhitespaceAndComments.getText())) {
            return psiElementPrevLeafIgnoringWhitespaceAndComments;
        }
        return null;
    }

    public static String getText(PsiElement psiElement) {
        return psiElement != null ? psiElement.getText() : "";
    }

    public static String getNullableText(PsiElement psiElement) {
        if (psiElement != null) {
            return psiElement.getText();
        }
        return null;
    }

    public static boolean isInComment(PsiElement psiElement) {
        return CommentUtilCore.isComment(psiElement) || (psiElement instanceof KDocElement);
    }

    public static PsiElement getOutermostParent(PsiElement psiElement, PsiElement psiElement2, boolean z) {
        if (psiElement == null) {
            $$$reportNull$$$0(47);
        }
        if (psiElement2 == null) {
            $$$reportNull$$$0(48);
        }
        if (z) {
            psiElement = psiElement.getLightParent();
        }
        while (psiElement != null && psiElement.getLightParent() != psiElement2) {
            psiElement = psiElement.getLightParent();
        }
        return psiElement;
    }

    @SafeVarargs
    public static <T extends PsiElement> T getLastChildByType(PsiElement psiElement, Class<? extends T>... clsArr) {
        if (psiElement == null) {
            $$$reportNull$$$0(49);
        }
        if (clsArr == null) {
            $$$reportNull$$$0(50);
        }
        PsiElement[] children = psiElement.getChildren();
        for (int length = children.length - 1; length >= 0; length--) {
            if (PsiTreeUtil.instanceOf(children[length], clsArr)) {
                return (T) children[length];
            }
        }
        return null;
    }

    public static KtElement getOutermostDescendantElement(PsiElement psiElement, boolean z, final Predicate<KtElement> predicate) {
        if (predicate == null) {
            $$$reportNull$$$0(51);
        }
        if (!(psiElement instanceof KtElement)) {
            return null;
        }
        final ArrayList arrayListNewArrayList = Lists.newArrayList();
        psiElement.accept(new KtVisitorVoid() { // from class: org.jetbrains.kotlin.psi.KtPsiUtil.2
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "org/jetbrains/kotlin/psi/KtPsiUtil$2", "visitKtElement"));
            }

            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitKtElement(KtElement ktElement) {
                if (ktElement == null) {
                    $$$reportNull$$$0(0);
                }
                if (predicate.test(ktElement)) {
                    arrayListNewArrayList.add(ktElement);
                } else {
                    ktElement.acceptChildren(this);
                }
            }
        });
        if (arrayListNewArrayList.isEmpty()) {
            return null;
        }
        return (KtElement) arrayListNewArrayList.get(z ? 0 : arrayListNewArrayList.size() - 1);
    }

    public static PsiElement findChildByType(PsiElement psiElement, IElementType iElementType) {
        if (psiElement == null) {
            $$$reportNull$$$0(52);
        }
        if (iElementType == null) {
            $$$reportNull$$$0(53);
        }
        ASTNode aSTNodeFindChildByType = psiElement.getNode().findChildByType(iElementType);
        if (aSTNodeFindChildByType == null) {
            return null;
        }
        return aSTNodeFindChildByType.getPsi();
    }

    public static PsiElement skipSiblingsBackwardByPredicate(PsiElement psiElement, Predicate<PsiElement> predicate) {
        if (psiElement == null) {
            return null;
        }
        do {
            psiElement = psiElement.getPrevSibling();
            if (psiElement == null) {
                return null;
            }
        } while (predicate.test(psiElement));
        return psiElement;
    }

    public static PsiElement ascendIfPropertyAccessor(PsiElement psiElement) {
        return psiElement instanceof KtPropertyAccessor ? psiElement.getLightParent() : psiElement;
    }

    public static KtModifierList replaceModifierList(KtModifierListOwner ktModifierListOwner, KtModifierList ktModifierList) {
        if (ktModifierListOwner == null) {
            $$$reportNull$$$0(54);
        }
        KtModifierList modifierList = ktModifierListOwner.getModifierList();
        if (ktModifierList != null) {
            if (modifierList == null) {
                return (KtModifierList) ktModifierListOwner.addBefore(ktModifierList, ktModifierListOwner.getFirstChild());
            }
            return (KtModifierList) modifierList.replace(ktModifierList);
        }
        if (modifierList == null) {
            return null;
        }
        modifierList.delete();
        return null;
    }

    public static String getPackageName(KtElement ktElement) {
        if (ktElement == null) {
            $$$reportNull$$$0(55);
        }
        KtPackageDirective ktPackageDirective = (KtPackageDirective) PsiTreeUtil.findChildOfType(ktElement.getContainingKtFile(), KtPackageDirective.class);
        if (ktPackageDirective != null) {
            return ktPackageDirective.getQualifiedName();
        }
        return null;
    }

    public static KtElement getEnclosingElementForLocalDeclaration(KtDeclaration ktDeclaration) {
        if (ktDeclaration == null) {
            $$$reportNull$$$0(56);
        }
        return getEnclosingElementForLocalDeclaration(ktDeclaration, true);
    }

    private static boolean isMemberOfObjectExpression(KtCallableDeclaration ktCallableDeclaration) {
        if (ktCallableDeclaration == null) {
            $$$reportNull$$$0(57);
        }
        PsiElement stubOrPsiParent = PsiTreeUtil.getStubOrPsiParent(ktCallableDeclaration);
        if (!(stubOrPsiParent instanceof KtClassBody)) {
            return false;
        }
        PsiElement stubOrPsiParent2 = PsiTreeUtil.getStubOrPsiParent(stubOrPsiParent);
        if (stubOrPsiParent2 instanceof KtObjectDeclaration) {
            return PsiTreeUtil.getStubOrPsiParent(stubOrPsiParent2) instanceof KtObjectLiteralExpression;
        }
        return false;
    }

    private static boolean isNonLocalCallable(KtDeclaration ktDeclaration) {
        if (ktDeclaration instanceof KtProperty) {
            return !((KtProperty) ktDeclaration).isLocal();
        }
        if (ktDeclaration instanceof KtFunction) {
            return !((KtFunction) ktDeclaration).isLocal();
        }
        return false;
    }

    public static KtElement getEnclosingElementForLocalDeclaration(KtDeclaration ktDeclaration, boolean z) {
        if (ktDeclaration == null) {
            $$$reportNull$$$0(58);
        }
        if ((ktDeclaration instanceof KtTypeParameter) && z) {
            ktDeclaration = (KtDeclaration) PsiTreeUtil.getParentOfType(ktDeclaration, KtNamedDeclaration.class);
        } else if (ktDeclaration instanceof KtParameter) {
            KtFunctionType ktFunctionType = (KtFunctionType) PsiTreeUtil.getParentOfType(ktDeclaration, KtFunctionType.class);
            if (ktFunctionType != null) {
                return ktFunctionType;
            }
            PsiElement parent = ktDeclaration.getLightParent();
            if (((KtParameter) ktDeclaration).hasValOrVar() && parent != null && (parent.getLightParent() instanceof KtPrimaryConstructor)) {
                return getEnclosingElementForLocalDeclaration(((KtPrimaryConstructor) parent.getLightParent()).getContainingClassOrObject(), z);
            }
            if (z && parent != null && !(parent instanceof KtForExpression) && (parent.getLightParent() instanceof KtNamedFunction)) {
                ktDeclaration = (KtNamedFunction) parent.getLightParent();
            }
        }
        if (ktDeclaration instanceof PsiFile) {
            return ktDeclaration;
        }
        PsiElement stubOrPsiParent = PsiTreeUtil.getStubOrPsiParent(ktDeclaration);
        boolean zIsNonLocalCallable = isNonLocalCallable(ktDeclaration);
        while (stubOrPsiParent != null) {
            PsiElement stubOrPsiParent2 = PsiTreeUtil.getStubOrPsiParent(stubOrPsiParent);
            if (stubOrPsiParent2 instanceof KtScript) {
                return null;
            }
            if (stubOrPsiParent instanceof KtAnonymousInitializer) {
                return ((KtAnonymousInitializer) stubOrPsiParent).getBody();
            }
            if ((stubOrPsiParent instanceof KtProperty) || (stubOrPsiParent instanceof KtFunction)) {
                if (stubOrPsiParent2 instanceof KtFile) {
                    return (KtElement) stubOrPsiParent;
                }
                if ((stubOrPsiParent2 instanceof KtClassBody) && !isMemberOfObjectExpression((KtCallableDeclaration) stubOrPsiParent)) {
                    return (KtElement) stubOrPsiParent2;
                }
                if ((stubOrPsiParent2 instanceof KtBlockExpression) && (stubOrPsiParent2.getLightParent() instanceof KtScript)) {
                    return (KtElement) stubOrPsiParent2;
                }
            }
            if (stubOrPsiParent instanceof KtParameter) {
                return (KtElement) stubOrPsiParent;
            }
            if ((stubOrPsiParent instanceof KtValueArgument) && !zIsNonLocalCallable) {
                return (KtElement) stubOrPsiParent;
            }
            if ((stubOrPsiParent instanceof KtBlockExpression) && (!zIsNonLocalCallable || !(stubOrPsiParent.getLightParent() instanceof KtFunctionLiteral))) {
                return (KtElement) stubOrPsiParent;
            }
            if ((stubOrPsiParent instanceof KtDelegatedSuperTypeEntry) || (stubOrPsiParent instanceof KtSuperTypeCallEntry)) {
                PsiElement parent2 = stubOrPsiParent.getLightParent().getLightParent();
                if ((parent2 instanceof KtClassOrObject) && !(parent2.getLightParent() instanceof KtObjectLiteralExpression)) {
                    return (KtElement) parent2;
                }
            }
            stubOrPsiParent = stubOrPsiParent2;
        }
        return null;
    }

    public static boolean isLocal(KtDeclaration ktDeclaration) {
        if (ktDeclaration == null) {
            $$$reportNull$$$0(59);
        }
        return getEnclosingElementForLocalDeclaration(ktDeclaration) != null;
    }

    public static KtToken getOperationToken(KtOperationExpression ktOperationExpression) {
        if (ktOperationExpression == null) {
            $$$reportNull$$$0(60);
        }
        return (KtToken) ktOperationExpression.getOperationReference().getReferencedNameElementType();
    }

    public static boolean isLabelIdentifierExpression(PsiElement psiElement) {
        return psiElement instanceof KtLabelReferenceExpression;
    }

    public static KtExpression getParentCallIfPresent(KtExpression ktExpression) {
        if (ktExpression == null) {
            $$$reportNull$$$0(61);
        }
        PsiElement parent = ktExpression.getLightParent();
        while (parent != null) {
            if ((parent instanceof KtBinaryExpression) || (parent instanceof KtUnaryExpression) || (parent instanceof KtLabeledExpression) || (parent instanceof KtDotQualifiedExpression) || (parent instanceof KtCallExpression) || (parent instanceof KtArrayAccessExpression) || (parent instanceof KtDestructuringDeclaration)) {
                if (parent instanceof KtLabeledExpression) {
                    parent = parent.getLightParent();
                } else {
                    return (KtExpression) parent;
                }
            } else if ((parent instanceof KtParenthesizedExpression) || (parent instanceof KtBinaryExpressionWithTypeRHS)) {
                parent = parent.getLightParent();
            } else if ((parent instanceof KtValueArgument) || (parent instanceof KtValueArgumentList)) {
                parent = parent.getLightParent();
            } else {
                if (!(parent instanceof KtLambdaExpression) && !(parent instanceof KtAnnotatedExpression)) {
                    return null;
                }
                parent = parent.getLightParent();
            }
        }
        return null;
    }

    public static boolean isLabeledFunctionLiteral(KtFunctionLiteral ktFunctionLiteral) {
        if (ktFunctionLiteral == null) {
            $$$reportNull$$$0(62);
        }
        return ktFunctionLiteral.getLightParent().getLightParent() instanceof KtLabeledExpression;
    }

    public static KtExpression getLastElementDeparenthesized(KtExpression ktExpression, StatementFilter statementFilter) {
        KtExpression lastStatementInABlock;
        if (statementFilter == null) {
            $$$reportNull$$$0(63);
        }
        KtExpression ktExpressionDeparenthesize = deparenthesize(ktExpression);
        return (!(ktExpressionDeparenthesize instanceof KtBlockExpression) || (lastStatementInABlock = StatementFilterKt.getLastStatementInABlock(statementFilter, (KtBlockExpression) ktExpressionDeparenthesize)) == null) ? ktExpressionDeparenthesize : getLastElementDeparenthesized(lastStatementInABlock, statementFilter);
    }

    public static boolean isStatementContainer(PsiElement psiElement) {
        return (psiElement instanceof KtBlockExpression) || (psiElement instanceof KtContainerNodeForControlStructureBody) || (psiElement instanceof KtWhenEntry);
    }

    public static boolean isStatement(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(64);
        }
        return isStatementContainer(psiElement.getLightParent());
    }

    public static boolean isInOrNotInOperation(KtBinaryExpression ktBinaryExpression) {
        if (ktBinaryExpression == null) {
            $$$reportNull$$$0(65);
        }
        return isInOperation(ktBinaryExpression) || isNotInOperation(ktBinaryExpression);
    }

    public static boolean isNotInOperation(KtBinaryExpression ktBinaryExpression) {
        if (ktBinaryExpression == null) {
            $$$reportNull$$$0(66);
        }
        return ktBinaryExpression.getOperationToken() == KtTokens.NOT_IN;
    }

    private static boolean isInOperation(KtBinaryExpression ktBinaryExpression) {
        if (ktBinaryExpression == null) {
            $$$reportNull$$$0(67);
        }
        return ktBinaryExpression.getOperationToken() == KtTokens.IN_KEYWORD;
    }
}
