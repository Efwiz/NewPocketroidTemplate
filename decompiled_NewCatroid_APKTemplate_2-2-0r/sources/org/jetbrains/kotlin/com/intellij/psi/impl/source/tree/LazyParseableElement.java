package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.diagnostic.PluginException;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Attachment;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressManager;
import org.jetbrains.kotlin.com.intellij.psi.impl.DebugUtil;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.ILazyParseableElementTypeBase;
import org.jetbrains.kotlin.com.intellij.reference.SoftReference;
import org.jetbrains.kotlin.com.intellij.util.ObjectUtils;
import org.jetbrains.kotlin.com.intellij.util.ThrowableRunnable;
import org.jetbrains.kotlin.com.intellij.util.text.CharArrayUtil;
import org.jetbrains.kotlin.com.intellij.util.text.ImmutableCharSequence;

/* JADX INFO: loaded from: classes3.dex */
public class LazyParseableElement extends CompositeElement {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Logger LOG = Logger.getInstance((Class<?>) LazyParseableElement.class);
    private static final Supplier<CharSequence> NO_TEXT = new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LazyParseableElement$$ExternalSyntheticLambda2
        @Override // java.util.function.Supplier
        public final Object get() {
            return LazyParseableElement.lambda$static$0();
        }
    };
    private static boolean ourParsingAllowed = true;
    private final ReentrantLock myLock;
    private volatile boolean myParsed;
    private volatile Supplier<CharSequence> myText;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2 || i == 3) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 2 || i == 3) ? 2 : 3];
        switch (i) {
            case 1:
            case 2:
            case 3:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/LazyParseableElement";
                break;
            case 4:
                objArr[0] = "buffer";
                break;
            case 5:
                objArr[0] = "parsedNode";
                break;
            case 6:
                objArr[0] = "first";
                break;
            case 7:
                objArr[0] = "ml";
                break;
            default:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
        }
        if (i == 1 || i == 2) {
            objArr[1] = "getText";
        } else if (i != 3) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/LazyParseableElement";
        } else {
            objArr[1] = "getChars";
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
                break;
            case 4:
                objArr[2] = "textMatches";
                break;
            case 5:
                objArr[2] = "setChildren";
                break;
            case 6:
                objArr[2] = "rawAddChildrenWithoutNotifications";
                break;
            case 7:
                objArr[2] = "waitForLock";
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

    static /* synthetic */ CharSequence lambda$new$1(CharSequence charSequence) {
        return charSequence;
    }

    static /* synthetic */ CharSequence lambda$static$0() {
        return null;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LazyParseableElement(IElementType iElementType, CharSequence charSequence) {
        super(iElementType);
        if (iElementType == null) {
            $$$reportNull$$$0(0);
        }
        ReentrantLock reentrantLock = new ReentrantLock();
        this.myLock = reentrantLock;
        waitForLock(reentrantLock);
        try {
            if (charSequence == null) {
                this.myParsed = true;
                this.myText = NO_TEXT;
            } else {
                final CharSequence charSequenceAsImmutable = ImmutableCharSequence.asImmutable(charSequence);
                this.myText = new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LazyParseableElement$$ExternalSyntheticLambda0
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return LazyParseableElement.lambda$new$1(charSequenceAsImmutable);
                    }
                };
                setCachedLength(charSequence.length());
            }
            reentrantLock.unlock();
        } catch (Throwable th) {
            this.myLock.unlock();
            throw th;
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement
    public void clearCaches() {
        super.clearCaches();
        waitForLock(this.myLock);
        try {
            if (this.myParsed) {
                this.myText = NO_TEXT;
            } else {
                setCachedLength(this.myText.get().length());
            }
        } finally {
            this.myLock.unlock();
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement, org.jetbrains.kotlin.com.intellij.lang.ASTNode
    public String getText() {
        CharSequence charSequenceMyText = myText();
        if (charSequenceMyText != null) {
            String string = charSequenceMyText.toString();
            if (string == null) {
                $$$reportNull$$$0(1);
            }
            return string;
        }
        String text = super.getText();
        this.myText = new SoftReference(text);
        if (text == null) {
            $$$reportNull$$$0(2);
        }
        return text;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement, org.jetbrains.kotlin.com.intellij.lang.ASTNode
    public CharSequence getChars() {
        CharSequence charSequenceMyText = myText();
        if (charSequenceMyText == null) {
            charSequenceMyText = super.getText();
            this.myText = new SoftReference(charSequenceMyText);
        }
        if (charSequenceMyText == null) {
            $$$reportNull$$$0(3);
        }
        return charSequenceMyText;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement, org.jetbrains.kotlin.com.intellij.lang.ASTNode
    public int getTextLength() {
        CharSequence charSequenceMyText = myText();
        if (charSequenceMyText != null) {
            return charSequenceMyText.length();
        }
        return super.getTextLength();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement
    public int hc() {
        CharSequence charSequenceMyText = myText();
        return charSequenceMyText == null ? super.hc() : LeafElement.leafHC(charSequenceMyText);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement
    protected int textMatches(CharSequence charSequence, int i) {
        if (charSequence == null) {
            $$$reportNull$$$0(4);
        }
        CharSequence charSequenceMyText = myText();
        if (charSequenceMyText != null) {
            return LeafElement.leafTextMatches(charSequenceMyText, charSequence, i);
        }
        return super.textMatches(charSequence, i);
    }

    public boolean isParsed() {
        return this.myParsed;
    }

    private CharSequence myText() {
        return this.myText.get();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    final void setFirstChildNode(TreeElement treeElement) {
        if (!isParsed()) {
            LOG.error("Mutating collapsed chameleon");
        }
        super.setFirstChildNode(treeElement);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    final void setLastChildNode(TreeElement treeElement) {
        if (!isParsed()) {
            LOG.error("Mutating collapsed chameleon");
        }
        super.setLastChildNode(treeElement);
    }

    private void ensureParsed() {
        if (!ourParsingAllowed) {
            LOG.error("Parsing not allowed!!!");
        }
        if (this.myParsed) {
            return;
        }
        waitForLock(this.myLock);
        try {
            if (this.myParsed) {
                return;
            }
            final CharSequence charSequence = this.myText.get();
            FileElement fileElement = TreeUtil.getFileElement((TreeElement) this);
            if (fileElement == null) {
                LOG.error("Chameleons must not be parsed till they're in file tree: " + this);
            } else {
                fileElement.assertReadAccessAllowed();
            }
            if (rawFirstChild() != null) {
                LOG.error("Reentrant parsing?");
            }
            DebugUtil.performPsiModification("lazy-parsing", new ThrowableRunnable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LazyParseableElement$$ExternalSyntheticLambda3
                @Override // org.jetbrains.kotlin.com.intellij.util.ThrowableRunnable
                public final void run() {
                    this.f$0.m6675x6a3c833e(charSequence);
                }
            });
        } finally {
            this.myLock.unlock();
        }
    }

    /* JADX INFO: renamed from: lambda$ensureParsed$2$org-jetbrains-kotlin-com-intellij-psi-impl-source-tree-LazyParseableElement, reason: not valid java name */
    /* synthetic */ void m6675x6a3c833e(CharSequence charSequence) throws RuntimeException {
        TreeElement treeElement = (TreeElement) ((ILazyParseableElementTypeBase) getElementType()).parseContents(this);
        assertTextLengthIntact(charSequence, treeElement);
        if (treeElement != null) {
            setChildren(treeElement);
        }
        this.myParsed = true;
        this.myText = new SoftReference(charSequence);
    }

    private void assertTextLengthIntact(CharSequence charSequence, TreeElement treeElement) {
        int textLength = 0;
        while (treeElement != null) {
            textLength += treeElement.getTextLength();
            treeElement = treeElement.getTreeNext();
        }
        if (textLength != charSequence.length()) {
            LOG.error("Text mismatch in " + ObjectUtils.objectInfo(getElementType()), PluginException.createByClass("Text mismatch", null, getElementType().getClass()), new Attachment("code.txt", charSequence.toString()));
        }
    }

    private void setChildren(final TreeElement treeElement) {
        if (treeElement == null) {
            $$$reportNull$$$0(5);
        }
        ProgressManager.getInstance().executeNonCancelableSection(new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LazyParseableElement$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m6676x5fe09f37(treeElement);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$setChildren$3$org-jetbrains-kotlin-com-intellij-psi-impl-source-tree-LazyParseableElement, reason: not valid java name */
    /* synthetic */ void m6676x5fe09f37(TreeElement treeElement) {
        try {
            TreeElement treeElementRawSetParents = rawSetParents(treeElement, this);
            super.setFirstChildNode(treeElement);
            super.setLastChildNode(treeElementRawSetParents);
        } catch (Throwable th) {
            LOG.error("Chameleon expansion may not be interrupted by exceptions", th);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public void rawAddChildrenWithoutNotifications(TreeElement treeElement) {
        if (treeElement == null) {
            $$$reportNull$$$0(6);
        }
        if (!isParsed()) {
            LOG.error("Mutating collapsed chameleon " + getClass());
        }
        super.rawAddChildrenWithoutNotifications(treeElement);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, org.jetbrains.kotlin.com.intellij.lang.ASTNode
    public TreeElement getFirstChildNode() {
        ensureParsed();
        return super.getFirstChildNode();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, org.jetbrains.kotlin.com.intellij.lang.ASTNode
    public TreeElement getLastChildNode() {
        ensureParsed();
        return super.getLastChildNode();
    }

    public int copyTo(char[] cArr, int i) {
        CharSequence charSequenceMyText = myText();
        if (charSequenceMyText == null) {
            return -1;
        }
        if (cArr != null) {
            CharArrayUtil.getChars(charSequenceMyText, cArr, i);
        }
        return i + charSequenceMyText.length();
    }

    public static void setParsingAllowed(boolean z) {
        ourParsingAllowed = z;
    }

    private static void waitForLock(ReentrantLock reentrantLock) {
        if (reentrantLock == null) {
            $$$reportNull$$$0(7);
        }
        while (!reentrantLock.tryLock(10L, TimeUnit.MILLISECONDS)) {
            ProgressManager.checkCanceled();
        }
    }
}
