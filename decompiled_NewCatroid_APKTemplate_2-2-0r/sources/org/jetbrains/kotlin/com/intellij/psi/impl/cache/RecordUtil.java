package org.jetbrains.kotlin.com.intellij.psi.impl.cache;

import java.util.Iterator;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.com.intellij.lang.LighterAST;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTTokenNode;
import org.jetbrains.kotlin.com.intellij.lang.java.lexer.JavaDocLexer;
import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;
import org.jetbrains.kotlin.com.intellij.psi.JavaDocTokenType;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiClassStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiFieldStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiModifierListStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LightTreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.util.CharTable;

/* JADX INFO: loaded from: classes3.dex */
public final class RecordUtil {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    /* JADX WARN: Code duplicated, block: B:20:0x0039  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 8 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 8 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "modList";
        } else if (i != 3) {
            switch (i) {
                case 5:
                    objArr[0] = "modList";
                    break;
                case 6:
                    objArr[0] = "table";
                    break;
                case 7:
                    objArr[0] = "node";
                    break;
                case 8:
                    objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/cache/RecordUtil";
                    break;
                case 9:
                    objArr[0] = "stub";
                    break;
                default:
                    objArr[0] = Constants.TYPE_TREE;
                    break;
            }
        } else {
            objArr[0] = "comment";
        }
        if (i != 8) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/cache/RecordUtil";
        } else {
            objArr[1] = "intern";
        }
        switch (i) {
            case 2:
            case 3:
                objArr[2] = "isDeprecatedByDocComment";
                break;
            case 4:
            case 5:
                objArr[2] = "packModifierList";
                break;
            case 6:
            case 7:
                objArr[2] = "intern";
                break;
            case 8:
                break;
            case 9:
                objArr[2] = "isStaticNonPrivateMember";
                break;
            default:
                objArr[2] = "isDeprecatedByAnnotation";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i == 8) {
            throw new IllegalStateException(str2);
        }
    }

    private RecordUtil() {
    }

    public static boolean isDeprecatedByAnnotation(LighterAST lighterAST, LighterASTNode lighterASTNode) {
        LighterASTNode lighterASTNodeFirstChildOfType;
        LighterASTNode lighterASTNodeFirstChildOfType2;
        if (lighterAST == null) {
            $$$reportNull$$$0(0);
        }
        if (lighterASTNode == null) {
            $$$reportNull$$$0(1);
        }
        for (LighterASTNode lighterASTNode2 : lighterAST.getChildren(lighterASTNode)) {
            if (lighterASTNode2.getTokenType() == JavaElementType.ANNOTATION && (lighterASTNodeFirstChildOfType = LightTreeUtil.firstChildOfType(lighterAST, lighterASTNode2, JavaElementType.JAVA_CODE_REFERENCE)) != null && (lighterASTNodeFirstChildOfType2 = LightTreeUtil.firstChildOfType(lighterAST, lighterASTNodeFirstChildOfType, JavaTokenType.IDENTIFIER)) != null && "Deprecated".equals(intern(lighterAST.getCharTable(), lighterASTNodeFirstChildOfType2))) {
                return true;
            }
        }
        return false;
    }

    public static boolean isDeprecatedByDocComment(LighterAST lighterAST, LighterASTNode lighterASTNode) {
        if (lighterAST == null) {
            $$$reportNull$$$0(2);
        }
        if (lighterASTNode == null) {
            $$$reportNull$$$0(3);
        }
        String filteredString = LightTreeUtil.toFilteredString(lighterAST, lighterASTNode, null);
        if (!filteredString.contains("@deprecated")) {
            return false;
        }
        JavaDocLexer javaDocLexer = new JavaDocLexer(LanguageLevel.HIGHEST);
        javaDocLexer.start(filteredString);
        while (true) {
            IElementType tokenType = javaDocLexer.getTokenType();
            if (tokenType == null) {
                return false;
            }
            if (tokenType == JavaDocTokenType.DOC_TAG_NAME && "@deprecated".equals(javaDocLexer.getTokenText())) {
                return true;
            }
            javaDocLexer.advance();
        }
    }

    public static int packModifierList(LighterAST lighterAST, LighterASTNode lighterASTNode) {
        if (lighterAST == null) {
            $$$reportNull$$$0(4);
        }
        if (lighterASTNode == null) {
            $$$reportNull$$$0(5);
        }
        Iterator<LighterASTNode> it = lighterAST.getChildren(lighterASTNode).iterator();
        int i = 0;
        while (it.hasNext()) {
            i |= ModifierFlags.KEYWORD_TO_MODIFIER_FLAG_MAP.getInt(it.next().getTokenType());
        }
        return i;
    }

    public static String intern(CharTable charTable, LighterASTNode lighterASTNode) {
        if (charTable == null) {
            $$$reportNull$$$0(6);
        }
        if (lighterASTNode == null) {
            $$$reportNull$$$0(7);
        }
        String string = charTable.intern(((LighterASTTokenNode) lighterASTNode).getText()).toString();
        if (string == null) {
            $$$reportNull$$$0(8);
        }
        return string;
    }

    public static boolean isStaticNonPrivateMember(StubElement<?> stubElement) {
        if (stubElement == null) {
            $$$reportNull$$$0(9);
        }
        PsiModifierListStub psiModifierListStub = (PsiModifierListStub) stubElement.findChildStubByType(JavaStubElementTypes.MODIFIER_LIST);
        if (psiModifierListStub == null) {
            return false;
        }
        int modifiersMask = psiModifierListStub.getModifiersMask();
        if (ModifierFlags.hasModifierProperty("private", modifiersMask)) {
            return false;
        }
        if (ModifierFlags.hasModifierProperty("static", modifiersMask)) {
            return true;
        }
        return ((stubElement instanceof PsiFieldStub) && stubElement.getStubType() == JavaElementType.ENUM_CONSTANT) || ((stubElement.getParentStub() instanceof PsiClassStub) && ((PsiClassStub) stubElement.getParentStub()).isInterface());
    }
}
