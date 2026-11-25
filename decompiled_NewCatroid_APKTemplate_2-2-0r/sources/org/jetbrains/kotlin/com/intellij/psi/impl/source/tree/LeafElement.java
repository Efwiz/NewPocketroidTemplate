package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree;

import java.lang.ref.Reference;
import org.jetbrains.kotlin.cli.common.arguments.K2JsArgumentConstants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.lang.ASTFactory;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.com.intellij.reference.SoftReference;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.com.intellij.util.text.CharArrayUtil;

/* JADX INFO: loaded from: classes3.dex */
public abstract class LeafElement extends TreeElement {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final CharSequence myText;
    private static final Logger LOG = Logger.getInstance((Class<?>) LeafElement.class);
    private static final Key<SoftReference<String>> CACHED_TEXT = Key.create("CACHED_TEXT");

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2 || i == 3 || i == 4 || i == 5 || i == 10 || i == 12 || i == 19) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 2 || i == 3 || i == 4 || i == 5 || i == 10 || i == 12 || i == 19) ? 2 : 3];
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 10:
            case 12:
            case 19:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/LeafElement";
                break;
            case 6:
            case 8:
                objArr[0] = "buffer";
                break;
            case 7:
                objArr[0] = "text";
                break;
            case 9:
            case 11:
                objArr[0] = "newText";
                break;
            case 13:
                objArr[0] = "buf";
                break;
            case 14:
                objArr[0] = "visitor";
                break;
            case 15:
            case 16:
            default:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
            case 17:
            case 18:
                objArr[0] = "typesSet";
                break;
            case 20:
            case 23:
            case 24:
                objArr[0] = "child";
                break;
            case 21:
                objArr[0] = "leafType";
                break;
            case 22:
                objArr[0] = "leafText";
                break;
            case 25:
                objArr[0] = "oldChild";
                break;
            case 26:
                objArr[0] = "newChild";
                break;
            case 27:
                objArr[0] = "anotherParent";
                break;
            case 28:
                objArr[0] = "first";
                break;
            case 29:
                objArr[0] = "firstChild";
                break;
            case 30:
            case 31:
                objArr[0] = "clazz";
                break;
            case 32:
                objArr[0] = K2JsArgumentConstants.RUNTIME_DIAGNOSTIC_LOG;
                break;
        }
        if (i == 1) {
            objArr[1] = "clone";
        } else if (i == 2) {
            objArr[1] = "getChars";
        } else if (i == 3 || i == 4) {
            objArr[1] = "getText";
        } else if (i == 5) {
            objArr[1] = "textToCharArray";
        } else if (i == 10) {
            objArr[1] = "rawReplaceWithText";
        } else if (i == 12) {
            objArr[1] = "replaceWithText";
        } else if (i != 19) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/LeafElement";
        } else {
            objArr[1] = "getChildren";
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 10:
            case 12:
            case 19:
                break;
            case 6:
            case 13:
                objArr[2] = "textMatches";
                break;
            case 7:
            case 8:
                objArr[2] = "leafTextMatches";
                break;
            case 9:
                objArr[2] = "rawReplaceWithText";
                break;
            case 11:
                objArr[2] = "replaceWithText";
                break;
            case 14:
                objArr[2] = "acceptTree";
                break;
            case 15:
            case 16:
            case 17:
            case 18:
                objArr[2] = "findChildByType";
                break;
            case 20:
            case 23:
                objArr[2] = "addChild";
                break;
            case 21:
            case 22:
                objArr[2] = "addLeaf";
                break;
            case 24:
                objArr[2] = "removeChild";
                break;
            case 25:
            case 26:
                objArr[2] = "replaceChild";
                break;
            case 27:
                objArr[2] = "replaceAllChildrenToChildrenOf";
                break;
            case 28:
                objArr[2] = "removeRange";
                break;
            case 29:
                objArr[2] = "addChildren";
                break;
            case 30:
            case 31:
            case 32:
                objArr[2] = "getPsi";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2 && i != 3 && i != 4 && i != 5 && i != 10 && i != 12 && i != 19) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.ASTNode
    public ASTNode findChildByType(IElementType iElementType) {
        if (iElementType != null) {
            return null;
        }
        $$$reportNull$$$0(15);
        return null;
    }

    public ASTNode findChildByType(IElementType iElementType, ASTNode aSTNode) {
        if (iElementType != null) {
            return null;
        }
        $$$reportNull$$$0(16);
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.ASTNode
    public ASTNode findChildByType(TokenSet tokenSet) {
        if (tokenSet != null) {
            return null;
        }
        $$$reportNull$$$0(17);
        return null;
    }

    public ASTNode findChildByType(TokenSet tokenSet, ASTNode aSTNode) {
        if (tokenSet != null) {
            return null;
        }
        $$$reportNull$$$0(18);
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, org.jetbrains.kotlin.com.intellij.lang.ASTNode
    public LeafElement findLeafElementAt(int i) {
        return this;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, org.jetbrains.kotlin.com.intellij.lang.ASTNode
    public TreeElement getFirstChildNode() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, org.jetbrains.kotlin.com.intellij.lang.ASTNode
    public TreeElement getLastChildNode() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.ASTNode
    public PsiElement getPsi() {
        return null;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    protected LeafElement(IElementType iElementType, CharSequence charSequence) {
        super(iElementType);
        if (iElementType == null) {
            $$$reportNull$$$0(0);
        }
        this.myText = charSequence;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolderBase
    public LeafElement clone() {
        LeafElement leafElement = (LeafElement) super.clone();
        leafElement.clearCaches();
        if (leafElement == null) {
            $$$reportNull$$$0(1);
        }
        return leafElement;
    }

    public int getTextLength() {
        return this.myText.length();
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.ASTNode
    public CharSequence getChars() {
        CharSequence charSequence = this.myText;
        if (charSequence == null) {
            $$$reportNull$$$0(2);
        }
        return charSequence;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.ASTNode
    public String getText() {
        CharSequence charSequence = this.myText;
        if (charSequence.length() > 1000 && !(charSequence instanceof String)) {
            Key<SoftReference<String>> key = CACHED_TEXT;
            String string = (String) SoftReference.dereference((Reference) getUserData(key));
            if (string == null) {
                string = charSequence.toString();
                putUserData(key, new SoftReference(string));
            }
            if (string == null) {
                $$$reportNull$$$0(3);
            }
            return string;
        }
        String string2 = charSequence.toString();
        if (string2 == null) {
            $$$reportNull$$$0(4);
        }
        return string2;
    }

    public char charAt(int i) {
        return this.myText.charAt(i);
    }

    public int copyTo(char[] cArr, int i) {
        int length = this.myText.length();
        if (cArr != null) {
            CharArrayUtil.getChars(this.myText, cArr, i, length);
        }
        return i + length;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement
    public char[] textToCharArray() {
        char[] cArr = new char[this.myText.length()];
        CharArrayUtil.getChars(this.myText, cArr, 0);
        return cArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.ASTNode
    public boolean textContains(char c) {
        char[] cArrFromSequenceWithoutCopying;
        CharSequence charSequence = this.myText;
        int length = charSequence.length();
        if (length <= 5 || (cArrFromSequenceWithoutCopying = CharArrayUtil.fromSequenceWithoutCopying(charSequence)) == null) {
            for (int i = 0; i < length; i++) {
                if (c == charSequence.charAt(i)) {
                    return true;
                }
            }
            return false;
        }
        for (char c2 : cArrFromSequenceWithoutCopying) {
            if (c2 == c) {
                return true;
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement
    protected int textMatches(CharSequence charSequence, int i) {
        if (charSequence == null) {
            $$$reportNull$$$0(6);
        }
        return leafTextMatches(this.myText, charSequence, i);
    }

    static int leafTextMatches(CharSequence charSequence, CharSequence charSequence2, int i) {
        if (charSequence == null) {
            $$$reportNull$$$0(7);
        }
        if (charSequence2 == null) {
            $$$reportNull$$$0(8);
        }
        int length = charSequence.length();
        if (charSequence2.length() - i < length) {
            if (i == 0) {
                return Integer.MIN_VALUE;
            }
            return -i;
        }
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = i2 + i;
            if (charSequence.charAt(i2) != charSequence2.charAt(i3)) {
                if (i3 == 0) {
                    return Integer.MIN_VALUE;
                }
                return -i3;
            }
        }
        return i + length;
    }

    public LeafElement rawReplaceWithText(String str) {
        if (str == null) {
            $$$reportNull$$$0(9);
        }
        LeafElement leafElementLeaf = ASTFactory.leaf(getElementType(), str);
        copyUserDataTo(leafElementLeaf);
        rawReplaceWithList(leafElementLeaf);
        leafElementLeaf.clearCaches();
        if (leafElementLeaf == null) {
            $$$reportNull$$$0(10);
        }
        return leafElementLeaf;
    }

    public LeafElement replaceWithText(String str) {
        if (str == null) {
            $$$reportNull$$$0(11);
        }
        LeafElement leafElementCopyLeafWithText = ChangeUtil.copyLeafWithText(this, str);
        getTreeParent().replaceChild(this, leafElementCopyLeafWithText);
        if (leafElementCopyLeafWithText == null) {
            $$$reportNull$$$0(12);
        }
        return leafElementCopyLeafWithText;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement
    public boolean textMatches(CharSequence charSequence, int i, int i2) {
        if (charSequence == null) {
            $$$reportNull$$$0(13);
        }
        CharSequence chars = getChars();
        int length = chars.length();
        if (i2 - i != length) {
            return false;
        }
        if (charSequence == chars) {
            return true;
        }
        if (length > 5 && (chars instanceof String) && (charSequence instanceof String)) {
            return ((String) chars).regionMatches(0, (String) charSequence, i, length);
        }
        for (int i3 = 0; i3 < length; i3++) {
            if (chars.charAt(i3) != charSequence.charAt(i + i3)) {
                return false;
            }
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement
    public void acceptTree(TreeElementVisitor treeElementVisitor) {
        if (treeElementVisitor == null) {
            $$$reportNull$$$0(14);
        }
        treeElementVisitor.visitLeaf(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement
    public int hc() {
        return leafHC(getChars());
    }

    static int leafHC(CharSequence charSequence) {
        int length = charSequence.length();
        int iCharAt = 0;
        for (int i = 0; i < length; i++) {
            iCharAt += charSequence.charAt(i);
        }
        return iCharAt;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement
    public int getCachedLength() {
        return this.myText.length();
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.ASTNode
    public ASTNode[] getChildren(TokenSet tokenSet) {
        TreeElement[] treeElementArr = EMPTY_ARRAY;
        if (treeElementArr == null) {
            $$$reportNull$$$0(19);
        }
        return treeElementArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.ASTNode
    public void addChild(ASTNode aSTNode, ASTNode aSTNode2) {
        if (aSTNode == null) {
            $$$reportNull$$$0(20);
        }
        throw new IncorrectOperationException("Leaf elements cannot have children.");
    }

    public void addLeaf(IElementType iElementType, CharSequence charSequence, ASTNode aSTNode) {
        if (iElementType == null) {
            $$$reportNull$$$0(21);
        }
        if (charSequence == null) {
            $$$reportNull$$$0(22);
        }
        throw new IncorrectOperationException("Leaf elements cannot have children.");
    }

    public void addChild(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(23);
        }
        throw new IncorrectOperationException("Leaf elements cannot have children.");
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.ASTNode
    public void removeChild(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(24);
        }
        throw new IncorrectOperationException("Leaf elements cannot have children.");
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.ASTNode
    public void replaceChild(ASTNode aSTNode, ASTNode aSTNode2) {
        if (aSTNode == null) {
            $$$reportNull$$$0(25);
        }
        if (aSTNode2 == null) {
            $$$reportNull$$$0(26);
        }
        throw new IncorrectOperationException("Leaf elements cannot have children.");
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.ASTNode
    public void replaceAllChildrenToChildrenOf(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(27);
        }
        throw new IncorrectOperationException("Leaf elements cannot have children.");
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.ASTNode
    public void removeRange(ASTNode aSTNode, ASTNode aSTNode2) {
        if (aSTNode == null) {
            $$$reportNull$$$0(28);
        }
        throw new IncorrectOperationException("Leaf elements cannot have children.");
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.ASTNode
    public void addChildren(ASTNode aSTNode, ASTNode aSTNode2, ASTNode aSTNode3) {
        if (aSTNode == null) {
            $$$reportNull$$$0(29);
        }
        throw new IncorrectOperationException("Leaf elements cannot have children.");
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.ASTNode
    public <T extends PsiElement> T getPsi(Class<T> cls) {
        if (cls == null) {
            $$$reportNull$$$0(30);
        }
        return (T) getPsi(cls, getPsi(), LOG);
    }

    /* JADX WARN: Multi-variable type inference failed */
    static <T extends PsiElement> T getPsi(Class<T> cls, PsiElement psiElement, Logger logger) {
        if (cls == null) {
            $$$reportNull$$$0(31);
        }
        if (logger == null) {
            $$$reportNull$$$0(32);
        }
        logger.assertTrue(cls.isInstance(psiElement), "unexpected psi class. expected: " + cls + " got: " + (psiElement == 0 ? null : psiElement.getClass()));
        return psiElement;
    }
}
