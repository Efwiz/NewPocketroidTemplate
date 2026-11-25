package org.jetbrains.kotlin.com.intellij.psi.impl;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTTokenNode;
import org.jetbrains.kotlin.com.intellij.lang.impl.PsiBuilderImpl;
import org.jetbrains.kotlin.com.intellij.openapi.application.Application;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.application.ex.ApplicationManagerEx;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.editor.Document;
import org.jetbrains.kotlin.com.intellij.openapi.util.Ref;
import org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.psi.FileViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.PsiComment;
import org.jetbrains.kotlin.com.intellij.psi.PsiDocumentManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiErrorElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiInvalidElementAccessException;
import org.jetbrains.kotlin.com.intellij.psi.PsiWhiteSpace;
import org.jetbrains.kotlin.com.intellij.psi.TokenType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.SourceTreeToPsiMap;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafPsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.RecursiveTreeElementWalkingVisitor;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.SharedImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer;
import org.jetbrains.kotlin.com.intellij.psi.stubs.Stub;
import org.jetbrains.kotlin.com.intellij.psi.templateLanguages.OuterLanguageElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtilCore;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.com.intellij.util.Consumer;
import org.jetbrains.kotlin.com.intellij.util.ExceptionUtil;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.PairConsumer;
import org.jetbrains.kotlin.com.intellij.util.ThrowableRunnable;
import org.jetbrains.kotlin.com.intellij.util.TimeoutUtil;
import org.jetbrains.kotlin.com.intellij.util.diff.FlyweightCapableTreeStructure;
import org.jetbrains.kotlin.com.intellij.util.graph.InboundSemiGraph;
import org.jetbrains.kotlin.com.intellij.util.graph.OutboundSemiGraph;

/* JADX INFO: loaded from: classes3.dex */
public final class DebugUtil {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static boolean CHECK;
    public static final boolean CHECK_INSIDE_ATOMIC_ACTION_ENABLED;
    public static final boolean DO_EXPENSIVE_CHECKS;
    private static final Logger LOG = Logger.getInstance((Class<?>) DebugUtil.class);
    private static final ThreadLocal<Integer> ourPsiModificationDepth;
    private static final ThreadLocal<Object> ourPsiModificationTrace;

    /* JADX WARN: Code duplicated, block: B:15:0x002d  */
    /* JADX WARN: Code duplicated, block: B:66:0x00da  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 2 && i != 4 && i != 6 && i != 10 && i != 13 && i != 22 && i != 24 && i != 27 && i != 43 && i != 45) {
            switch (i) {
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 2 && i != 4 && i != 6 && i != 10 && i != 13 && i != 22 && i != 24 && i != 27 && i != 43 && i != 45) {
            switch (i) {
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
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
            case 5:
            case 8:
            case 12:
            case 20:
            case 21:
            case 26:
                objArr[0] = "root";
                break;
            case 2:
            case 4:
            case 6:
            case 10:
            case 13:
            case 22:
            case 24:
            case 27:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 43:
            case 45:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/DebugUtil";
                break;
            case 7:
            case 15:
            case 25:
                objArr[0] = "buffer";
                break;
            case 9:
                objArr[0] = Constants.TYPE_TREE;
                break;
            case 11:
            case 14:
                objArr[0] = "node";
                break;
            case 16:
            case 19:
            case 23:
            case 33:
            case 34:
            default:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 17:
                objArr[0] = "element1";
                break;
            case 18:
                objArr[0] = "element2";
                break;
            case 28:
            case 29:
                objArr[0] = "runnable";
                break;
            case 30:
                objArr[0] = "treeElement";
                break;
            case 31:
                objArr[0] = "o";
                break;
            case 32:
                objArr[0] = org.catrobat.catroid.common.Constants.EXTRA_PROVIDER;
                break;
            case 35:
                objArr[0] = "document";
                break;
            case 42:
            case 44:
                objArr[0] = "graph";
                break;
        }
        if (i == 2) {
            objArr[1] = "treeToString";
        } else if (i == 4) {
            objArr[1] = "nodeTreeToString";
        } else if (i == 6) {
            objArr[1] = "treeToString";
        } else if (i == 10) {
            objArr[1] = "lightTreeToString";
        } else if (i == 13) {
            objArr[1] = "stubTreeToString";
        } else if (i == 22) {
            objArr[1] = "psiToString";
        } else if (i == 24) {
            objArr[1] = "psiToStringIgnoringNonCode";
        } else if (i == 27) {
            objArr[1] = "currentStackTrace";
        } else if (i != 43 && i != 45) {
            switch (i) {
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                    objArr[1] = "diagnosePsiDocumentInconsistency";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/DebugUtil";
                    break;
            }
        } else {
            objArr[1] = "graphToString";
        }
        switch (i) {
            case 1:
            case 5:
                objArr[2] = "treeToString";
                break;
            case 2:
            case 4:
            case 6:
            case 10:
            case 13:
            case 22:
            case 24:
            case 27:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 43:
            case 45:
                break;
            case 3:
                objArr[2] = "nodeTreeToString";
                break;
            case 7:
            case 8:
                objArr[2] = "treeToBuffer";
                break;
            case 9:
                objArr[2] = "lightTreeToString";
                break;
            case 11:
                objArr[2] = "lightTreeToBuffer";
                break;
            case 12:
                objArr[2] = "stubTreeToString";
                break;
            case 14:
            case 15:
                objArr[2] = "stubTreeToBuffer";
                break;
            case 16:
                objArr[2] = "checkParentChildConsistent";
                break;
            case 17:
            case 18:
                objArr[2] = "checkSameCharTabs";
                break;
            case 19:
            case 20:
            case 21:
                objArr[2] = "psiToString";
                break;
            case 23:
                objArr[2] = "psiToStringIgnoringNonCode";
                break;
            case 25:
            case 26:
                objArr[2] = "psiToBuffer";
                break;
            case 28:
            case 29:
                objArr[2] = "performPsiModification";
                break;
            case 30:
            case 31:
            case 32:
                objArr[2] = "onInvalidated";
                break;
            case 33:
                objArr[2] = "revalidateNode";
                break;
            case 34:
            case 35:
                objArr[2] = "diagnosePsiDocumentInconsistency";
                break;
            case 42:
            case 44:
                objArr[2] = "graphToString";
                break;
            default:
                objArr[2] = "psiTreeToString";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 4 && i != 6 && i != 10 && i != 13 && i != 22 && i != 24 && i != 27 && i != 43 && i != 45) {
            switch (i) {
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    static {
        Application application = ApplicationManager.getApplication();
        boolean z = application != null && application.isUnitTestMode();
        DO_EXPENSIVE_CHECKS = z;
        CHECK_INSIDE_ATOMIC_ACTION_ENABLED = z;
        ourPsiModificationTrace = new ThreadLocal<>();
        ourPsiModificationDepth = new ThreadLocal<>();
    }

    public static String psiTreeToString(PsiElement psiElement, boolean z) {
        if (psiElement == null) {
            $$$reportNull$$$0(0);
        }
        return treeToString(SourceTreeToPsiMap.psiElementToTree(psiElement), z);
    }

    public static String treeToString(ASTNode aSTNode, boolean z) {
        if (aSTNode == null) {
            $$$reportNull$$$0(1);
        }
        StringBuilder sb = new StringBuilder();
        treeToBuffer(sb, aSTNode, 0, z, false, false, true);
        String string = sb.toString();
        if (string == null) {
            $$$reportNull$$$0(2);
        }
        return string;
    }

    public static String nodeTreeToString(ASTNode aSTNode, boolean z) {
        if (aSTNode == null) {
            $$$reportNull$$$0(3);
        }
        StringBuilder sb = new StringBuilder();
        treeToBuffer(sb, aSTNode, 0, z, false, false, false);
        String string = sb.toString();
        if (string == null) {
            $$$reportNull$$$0(4);
        }
        return string;
    }

    public static String treeToString(ASTNode aSTNode, boolean z, boolean z2) {
        if (aSTNode == null) {
            $$$reportNull$$$0(5);
        }
        StringBuilder sb = new StringBuilder();
        treeToBuffer(sb, aSTNode, 0, z, z2, false, true);
        String string = sb.toString();
        if (string == null) {
            $$$reportNull$$$0(6);
        }
        return string;
    }

    public static void treeToBuffer(Appendable appendable, ASTNode aSTNode, int i, boolean z, boolean z2, boolean z3, boolean z4) {
        if (appendable == null) {
            $$$reportNull$$$0(7);
        }
        if (aSTNode == null) {
            $$$reportNull$$$0(8);
        }
        treeToBuffer(appendable, aSTNode, i, z, z2, z3, z4, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void treeToBuffer(Appendable appendable, ASTNode aSTNode, int i, boolean z, boolean z2, boolean z3, boolean z4, PairConsumer<? super PsiElement, ? super Consumer<? super PsiElement>> pairConsumer) {
        ((TreeElement) aSTNode).acceptTree(new TreeToBuffer(appendable, i, z, z2, z3, z4, pairConsumer));
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class TreeToBuffer extends RecursiveTreeElementWalkingVisitor {
        private final Appendable buffer;
        private final PairConsumer<? super PsiElement, ? super Consumer<? super PsiElement>> extra;
        private int indent;
        private final boolean showChildrenRanges;
        private final boolean showRanges;
        private final boolean showWhitespaces;
        private final boolean usePsi;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "e", "org/jetbrains/kotlin/com/intellij/psi/impl/DebugUtil$TreeToBuffer", "elementFinished"));
        }

        protected boolean showEmptyChildren() {
            return true;
        }

        private TreeToBuffer(Appendable appendable, int i, boolean z, boolean z2, boolean z3, boolean z4, PairConsumer<? super PsiElement, ? super Consumer<? super PsiElement>> pairConsumer) {
            this.buffer = appendable;
            this.showWhitespaces = z;
            this.showRanges = z2;
            this.showChildrenRanges = z3;
            this.usePsi = z4;
            this.extra = pairConsumer;
            this.indent = i;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.RecursiveTreeElementWalkingVisitor
        protected void visitNode(TreeElement treeElement) {
            if (!shouldShowNode(treeElement)) {
                this.indent += 2;
                return;
            }
            StringUtil.repeatSymbol(this.buffer, io.netty.util.internal.StringUtil.SPACE, this.indent);
            try {
                if (!(treeElement instanceof CompositeElement)) {
                    this.buffer.append(treeElement.toString()).append("('").append(DebugUtil.fixWhiteSpaces(treeElement.getText())).append("')");
                } else if (this.usePsi) {
                    PsiElement psi = treeElement.getPsi();
                    if (psi != null) {
                        this.buffer.append(psi.toString());
                    } else {
                        this.buffer.append(treeElement.getElementType().toString());
                    }
                } else {
                    this.buffer.append(treeElement.toString());
                }
                if (this.showRanges) {
                    this.buffer.append(treeElement.getTextRange().toString());
                }
                this.buffer.append("\n");
                this.indent += 2;
                if ((treeElement instanceof CompositeElement) && treeElement.getFirstChildNode() == null && showEmptyChildren()) {
                    StringUtil.repeatSymbol(this.buffer, io.netty.util.internal.StringUtil.SPACE, this.indent);
                    this.buffer.append("<empty list>\n");
                }
            } catch (IOException e) {
                DebugUtil.LOG.error((Throwable) e);
            }
            super.visitNode(treeElement);
        }

        protected boolean shouldShowNode(TreeElement treeElement) {
            return this.showWhitespaces || treeElement.getElementType() != TokenType.WHITE_SPACE;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.RecursiveTreeElementWalkingVisitor
        protected void elementFinished(ASTNode aSTNode) {
            if (aSTNode == null) {
                $$$reportNull$$$0(0);
            }
            PsiElement psi = (this.extra != null && this.usePsi && (aSTNode instanceof CompositeElement)) ? aSTNode.getPsi() : null;
            if (psi != null) {
                this.extra.consume(psi, new Consumer() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.DebugUtil$TreeToBuffer$$ExternalSyntheticLambda0
                    @Override // org.jetbrains.kotlin.com.intellij.util.Consumer
                    public final void consume(Object obj) {
                        this.f$0.m6542xbeb928a9((PsiElement) obj);
                    }
                });
            }
            this.indent -= 2;
        }

        /* JADX INFO: renamed from: lambda$elementFinished$0$org-jetbrains-kotlin-com-intellij-psi-impl-DebugUtil$TreeToBuffer, reason: not valid java name */
        /* synthetic */ void m6542xbeb928a9(PsiElement psiElement) {
            DebugUtil.treeToBuffer(this.buffer, psiElement.getNode(), this.indent, this.showWhitespaces, this.showRanges, this.showChildrenRanges, true, null);
        }
    }

    public static String lightTreeToString(FlyweightCapableTreeStructure<LighterASTNode> flyweightCapableTreeStructure, boolean z) {
        if (flyweightCapableTreeStructure == null) {
            $$$reportNull$$$0(9);
        }
        StringBuilder sb = new StringBuilder();
        lightTreeToBuffer(flyweightCapableTreeStructure, flyweightCapableTreeStructure.getRoot(), sb, 0, z);
        String string = sb.toString();
        if (string == null) {
            $$$reportNull$$$0(10);
        }
        return string;
    }

    private static void lightTreeToBuffer(FlyweightCapableTreeStructure<LighterASTNode> flyweightCapableTreeStructure, LighterASTNode lighterASTNode, Appendable appendable, int i, boolean z) {
        if (lighterASTNode == null) {
            $$$reportNull$$$0(11);
        }
        IElementType tokenType = lighterASTNode.getTokenType();
        if (z || tokenType != TokenType.WHITE_SPACE) {
            boolean z2 = lighterASTNode instanceof LighterASTTokenNode;
            StringUtil.repeatSymbol(appendable, io.netty.util.internal.StringUtil.SPACE, i);
            try {
                if (tokenType == TokenType.ERROR_ELEMENT) {
                    appendable.append("PsiErrorElement:").append(PsiBuilderImpl.getErrorMessage(lighterASTNode));
                } else if (tokenType == TokenType.WHITE_SPACE) {
                    appendable.append("PsiWhiteSpace");
                } else {
                    appendable.append(z2 ? "PsiElement" : "Element").append('(').append(tokenType.toString()).append(')');
                }
                if (z2) {
                    appendable.append("('").append(fixWhiteSpaces(((LighterASTTokenNode) lighterASTNode).getText().toString())).append("')");
                }
                appendable.append('\n');
                if (z2) {
                    return;
                }
                Ref<LighterASTNode[]> ref = new Ref<>();
                int children = flyweightCapableTreeStructure.getChildren(lighterASTNode, ref);
                if (children == 0) {
                    StringUtil.repeatSymbol(appendable, io.netty.util.internal.StringUtil.SPACE, i + 2);
                    appendable.append("<empty list>\n");
                } else {
                    for (int i2 = 0; i2 < children; i2++) {
                        lightTreeToBuffer(flyweightCapableTreeStructure, ref.get()[i2], appendable, i + 2, z);
                    }
                }
            } catch (IOException e) {
                LOG.error((Throwable) e);
            }
        }
    }

    public static String stubTreeToString(Stub stub) {
        if (stub == null) {
            $$$reportNull$$$0(12);
        }
        StringBuilder sb = new StringBuilder();
        stubTreeToBuffer(stub, sb, 0);
        String string = sb.toString();
        if (string == null) {
            $$$reportNull$$$0(13);
        }
        return string;
    }

    public static void stubTreeToBuffer(Stub stub, Appendable appendable, int i) {
        if (stub == null) {
            $$$reportNull$$$0(14);
        }
        if (appendable == null) {
            $$$reportNull$$$0(15);
        }
        StringUtil.repeatSymbol(appendable, io.netty.util.internal.StringUtil.SPACE, i);
        try {
            ObjectStubSerializer<?, ? extends Stub> stubType = stub.getStubType();
            if (stubType != null) {
                appendable.append(stubType.toString()).append(':');
            }
            appendable.append(stub.toString()).append('\n');
            Iterator<? extends Stub> it = stub.getChildrenStubs().iterator();
            while (it.hasNext()) {
                stubTreeToBuffer(it.next(), appendable, i + 2);
            }
        } catch (IOException e) {
            LOG.error((Throwable) e);
        }
    }

    private static void doCheckTreeStructure(ASTNode aSTNode) {
        if (aSTNode == null) {
            return;
        }
        while (aSTNode.getTreeParent() != null) {
            aSTNode = aSTNode.getTreeParent();
        }
        if (aSTNode instanceof CompositeElement) {
            checkSubtree((CompositeElement) aSTNode);
        }
    }

    private static void checkSubtree(CompositeElement compositeElement) {
        if (compositeElement.rawFirstChild() == null) {
            if (compositeElement.rawLastChild() != null) {
                throw new IncorrectTreeStructureException(compositeElement, "firstChild == null, but lastChild != null");
            }
            return;
        }
        for (ASTNode firstChildNode = compositeElement.getFirstChildNode(); firstChildNode != null; firstChildNode = firstChildNode.getTreeNext()) {
            if (firstChildNode instanceof CompositeElement) {
                checkSubtree((CompositeElement) firstChildNode);
            }
            if (firstChildNode.getTreeParent() != compositeElement) {
                throw new IncorrectTreeStructureException(firstChildNode, "child has wrong parent value");
            }
            if (firstChildNode == compositeElement.getFirstChildNode()) {
                if (firstChildNode.getTreePrev() != null) {
                    throw new IncorrectTreeStructureException(compositeElement, "firstChild.prev != null");
                }
            } else {
                if (firstChildNode.getTreePrev() == null) {
                    throw new IncorrectTreeStructureException(firstChildNode, "not first child has prev == null");
                }
                if (firstChildNode.getTreePrev().getTreeNext() != firstChildNode) {
                    throw new IncorrectTreeStructureException(firstChildNode, "element.prev.next != element");
                }
            }
            if (firstChildNode.getTreeNext() == null && compositeElement.getLastChildNode() != firstChildNode) {
                throw new IncorrectTreeStructureException(firstChildNode, "not last child has next == null");
            }
        }
    }

    public static void checkParentChildConsistent(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(16);
        }
        ASTNode treeParent = aSTNode.getTreeParent();
        if (treeParent != null && ArrayUtil.find(treeParent.getChildren(null), aSTNode) == -1) {
            throw new IncorrectTreeStructureException(aSTNode, "child cannot be found among parents children");
        }
    }

    public static void checkSameCharTabs(ASTNode aSTNode, ASTNode aSTNode2) {
        if (aSTNode == null) {
            $$$reportNull$$$0(17);
        }
        if (aSTNode2 == null) {
            $$$reportNull$$$0(18);
        }
        LOG.assertTrue(SharedImplUtil.findCharTableByTree(aSTNode) == SharedImplUtil.findCharTableByTree(aSTNode2));
    }

    public static String psiToString(PsiElement psiElement, boolean z) {
        if (psiElement == null) {
            $$$reportNull$$$0(19);
        }
        return psiToString(psiElement, z, false);
    }

    public static String psiToString(PsiElement psiElement, boolean z, boolean z2) {
        if (psiElement == null) {
            $$$reportNull$$$0(20);
        }
        return psiToString(psiElement, z, z2, null);
    }

    public static String psiToString(PsiElement psiElement, boolean z, boolean z2, PairConsumer<? super PsiElement, ? super Consumer<? super PsiElement>> pairConsumer) {
        if (psiElement == null) {
            $$$reportNull$$$0(21);
        }
        StringBuilder sb = new StringBuilder();
        psiToBuffer(sb, psiElement, z, z2, pairConsumer);
        String string = sb.toString();
        if (string == null) {
            $$$reportNull$$$0(22);
        }
        return string;
    }

    public static String psiToStringIgnoringNonCode(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(23);
        }
        StringBuilder sb = new StringBuilder();
        ((TreeElement) psiElement.getNode()).acceptTree(new TreeToBuffer(sb, 0, false, false, false, false, null) { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.DebugUtil.1
            @Override // org.jetbrains.kotlin.com.intellij.psi.impl.DebugUtil.TreeToBuffer
            protected boolean showEmptyChildren() {
                return false;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.impl.DebugUtil.TreeToBuffer
            protected boolean shouldShowNode(TreeElement treeElement) {
                return (!super.shouldShowNode(treeElement) || (treeElement instanceof PsiErrorElement) || (treeElement instanceof PsiComment) || ((treeElement instanceof LeafPsiElement) && StringUtil.isEmptyOrSpaces(treeElement.getText())) || (treeElement instanceof OuterLanguageElement)) ? false : true;
            }
        });
        String string = sb.toString();
        if (string == null) {
            $$$reportNull$$$0(24);
        }
        return string;
    }

    private static void psiToBuffer(Appendable appendable, PsiElement psiElement, boolean z, boolean z2, PairConsumer<? super PsiElement, ? super Consumer<? super PsiElement>> pairConsumer) {
        ASTNode node = psiElement.getNode();
        if (node == null) {
            psiToBuffer(appendable, psiElement, 0, z, z2, z2, pairConsumer);
        } else {
            treeToBuffer(appendable, node, 0, z, z2, z2, true, pairConsumer);
        }
    }

    public static void psiToBuffer(Appendable appendable, PsiElement psiElement, int i, boolean z, boolean z2, boolean z3) {
        if (appendable == null) {
            $$$reportNull$$$0(25);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(26);
        }
        psiToBuffer(appendable, psiElement, i, z, z2, z3, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void psiToBuffer(final Appendable appendable, PsiElement psiElement, final int i, final boolean z, boolean z2, final boolean z3, PairConsumer<? super PsiElement, ? super Consumer<? super PsiElement>> pairConsumer) {
        if (z || !(psiElement instanceof PsiWhiteSpace)) {
            StringUtil.repeatSymbol(appendable, io.netty.util.internal.StringUtil.SPACE, i);
            try {
                appendable.append(psiElement.toString());
                PsiElement firstChild = psiElement.getFirstChild();
                if (firstChild == null) {
                    appendable.append("('").append(fixWhiteSpaces(psiElement.getText())).append("')");
                }
                if (z2) {
                    appendable.append(psiElement.getTextRange().toString());
                }
                appendable.append("\n");
                while (firstChild != null) {
                    psiToBuffer(appendable, firstChild, i + 2, z, z3, z3, pairConsumer);
                    firstChild = firstChild.getNextSibling();
                }
                if (pairConsumer != null) {
                    pairConsumer.consume(psiElement, new Consumer() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.DebugUtil$$ExternalSyntheticLambda0
                        @Override // org.jetbrains.kotlin.com.intellij.util.Consumer
                        public final void consume(Object obj) {
                            Appendable appendable2 = appendable;
                            int i2 = i;
                            boolean z4 = z;
                            boolean z5 = z3;
                            DebugUtil.psiToBuffer(appendable2, (PsiElement) obj, i2 + 2, !z4, z5, z5, null);
                        }
                    });
                }
            } catch (IOException e) {
                LOG.error((Throwable) e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String fixWhiteSpaces(String str) {
        return str.replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t");
    }

    public static String currentStackTrace() {
        String strCurrentStackTrace = ExceptionUtil.currentStackTrace();
        if (strCurrentStackTrace == null) {
            $$$reportNull$$$0(27);
        }
        return strCurrentStackTrace;
    }

    public static class IncorrectTreeStructureException extends RuntimeException {
        private final ASTNode myElement;

        IncorrectTreeStructureException(ASTNode aSTNode, String str) {
            super(str);
            this.myElement = aSTNode;
        }

        public ASTNode getElement() {
            return this.myElement;
        }
    }

    private static void beginPsiModification(String str) {
        Object th;
        if (PsiInvalidElementAccessException.isTrackingInvalidation()) {
            ThreadLocal<Object> threadLocal = ourPsiModificationTrace;
            if (threadLocal.get() == null) {
                if (str == null) {
                    th = str;
                    if (!ApplicationManagerEx.isInStressTest()) {
                        th = new Throwable();
                    }
                }
                threadLocal.set(th);
            }
            th = str;
            ThreadLocal<Integer> threadLocal2 = ourPsiModificationDepth;
            Integer num = threadLocal2.get();
            if (num == null) {
                num = 0;
            }
            threadLocal2.set(Integer.valueOf(num.intValue() + 1));
        }
    }

    private static void endPsiModification() {
        Integer num;
        if (PsiInvalidElementAccessException.isTrackingInvalidation()) {
            ThreadLocal<Integer> threadLocal = ourPsiModificationDepth;
            Integer num2 = threadLocal.get();
            if (num2 == null) {
                LOG.warn("Unmatched PSI modification end", new Throwable());
                num = 0;
            } else {
                Integer numValueOf = Integer.valueOf(num2.intValue() - 1);
                threadLocal.set(numValueOf);
                num = numValueOf;
            }
            if (num.intValue() == 0) {
                ourPsiModificationTrace.set(null);
            }
        }
    }

    public static <T extends Throwable> void performPsiModification(String str, ThrowableRunnable<T> throwableRunnable) throws Throwable {
        if (throwableRunnable == null) {
            $$$reportNull$$$0(28);
        }
        beginPsiModification(str);
        try {
            throwableRunnable.run();
        } finally {
            endPsiModification();
        }
    }

    public static <T, E extends Throwable> T performPsiModification(String str, ThrowableComputable<T, E> throwableComputable) throws Throwable {
        if (throwableComputable == null) {
            $$$reportNull$$$0(29);
        }
        beginPsiModification(str);
        try {
            return throwableComputable.compute();
        } finally {
            endPsiModification();
        }
    }

    public static void onInvalidated(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(30);
        }
        Object objCalcInvalidationTrace = calcInvalidationTrace(aSTNode);
        if (objCalcInvalidationTrace != null) {
            PsiInvalidElementAccessException.setInvalidationTrace(aSTNode, objCalcInvalidationTrace);
        }
    }

    public static void onInvalidated(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(31);
        }
        if (PsiInvalidElementAccessException.getInvalidationTrace(psiElement) == null) {
            PsiInvalidElementAccessException.setInvalidationTrace(psiElement, currentInvalidationTrace());
        }
    }

    public static void onInvalidated(FileViewProvider fileViewProvider) {
        if (fileViewProvider == null) {
            $$$reportNull$$$0(32);
        }
        Object objCalcInvalidationTrace = calcInvalidationTrace(null);
        if (objCalcInvalidationTrace != null) {
            PsiInvalidElementAccessException.setInvalidationTrace(fileViewProvider, objCalcInvalidationTrace);
        }
    }

    private static Object calcInvalidationTrace(ASTNode aSTNode) {
        if (PsiInvalidElementAccessException.isTrackingInvalidation() && PsiInvalidElementAccessException.findInvalidationTrace(aSTNode) == null) {
            return currentInvalidationTrace();
        }
        return null;
    }

    private static Object currentInvalidationTrace() {
        Object obj = ourPsiModificationTrace.get();
        return (obj != null || ApplicationManagerEx.isInStressTest()) ? obj : handleUnspecifiedTrace();
    }

    private static Throwable handleUnspecifiedTrace() {
        Throwable th = new Throwable();
        if (ApplicationManager.getApplication().isUnitTestMode()) {
            LOG.error("PSI invalidated outside transaction", th);
        } else {
            LOG.info("PSI invalidated outside transaction", th);
        }
        return th;
    }

    public static void revalidateNode(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(33);
        }
        PsiInvalidElementAccessException.setInvalidationTrace(aSTNode, null);
    }

    public static void sleep(long j) {
        TimeoutUtil.sleep(j);
    }

    public static void checkTreeStructure(ASTNode aSTNode) {
        if (CHECK) {
            doCheckTreeStructure(aSTNode);
        }
    }

    public static String diagnosePsiDocumentInconsistency(PsiElement psiElement, Document document) {
        if (psiElement == null) {
            $$$reportNull$$$0(34);
        }
        if (document == null) {
            $$$reportNull$$$0(35);
        }
        PsiUtilCore.ensureValid(psiElement);
        PsiFile containingFile = psiElement.getContainingFile();
        if (containingFile == null) {
            String str = "no file for " + psiElement + " of " + psiElement.getClass();
            if (str == null) {
                $$$reportNull$$$0(36);
            }
            return str;
        }
        PsiUtilCore.ensureValid(containingFile);
        FileViewProvider viewProvider = containingFile.getViewProvider();
        PsiDocumentManager psiDocumentManager = PsiDocumentManager.getInstance(containingFile.getProject());
        Document document2 = viewProvider.getDocument();
        String str2 = "File[" + containingFile + " " + containingFile.getName() + ", " + containingFile.getLanguage() + ", " + viewProvider + "]";
        if (document2 != document) {
            String str3 = "wrong document for " + str2 + "; expected " + document + "; actual " + document2;
            if (str3 == null) {
                $$$reportNull$$$0(37);
            }
            return str3;
        }
        PsiFile cachedPsiFile = psiDocumentManager.getCachedPsiFile(document);
        FileViewProvider viewProvider2 = cachedPsiFile == null ? null : cachedPsiFile.getViewProvider();
        if (viewProvider2 != viewProvider) {
            String str4 = "wrong view provider for " + document + ", expected " + viewProvider + "; actual " + viewProvider2;
            if (str4 == null) {
                $$$reportNull$$$0(38);
            }
            return str4;
        }
        if (!psiDocumentManager.isCommitted(document)) {
            String str5 = "not committed document " + document + ", " + str2;
            if (str5 == null) {
                $$$reportNull$$$0(39);
            }
            return str5;
        }
        int textLength = containingFile.getTextLength();
        int textLength2 = document.getTextLength();
        if (textLength != textLength2) {
            String str6 = "file/doc text length different, " + str2 + " file.length=" + textLength + "; doc.length=" + textLength2;
            if (str6 == null) {
                $$$reportNull$$$0(40);
            }
            return str6;
        }
        String str7 = "unknown inconsistency in " + str2;
        if (str7 == null) {
            $$$reportNull$$$0(41);
        }
        return str7;
    }

    public static <T> String graphToString(final InboundSemiGraph<T> inboundSemiGraph) {
        if (inboundSemiGraph == null) {
            $$$reportNull$$$0(42);
        }
        StringBuilder sb = new StringBuilder();
        printNodes(inboundSemiGraph.getNodes().iterator(), new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.DebugUtil$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return inboundSemiGraph.getIn(obj);
            }
        }, 0, new HashSet(), sb);
        String string = sb.toString();
        if (string == null) {
            $$$reportNull$$$0(43);
        }
        return string;
    }

    public static <T> String graphToString(final OutboundSemiGraph<T> outboundSemiGraph) {
        if (outboundSemiGraph == null) {
            $$$reportNull$$$0(44);
        }
        StringBuilder sb = new StringBuilder();
        printNodes(outboundSemiGraph.getNodes().iterator(), new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.DebugUtil$$ExternalSyntheticLambda2
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return outboundSemiGraph.getOut(obj);
            }
        }, 0, new HashSet(), sb);
        String string = sb.toString();
        if (string == null) {
            $$$reportNull$$$0(45);
        }
        return string;
    }

    private static <T> void printNodes(Iterator<? extends T> it, Function<? super T, ? extends Iterator<? extends T>> function, int i, Set<? super T> set, StringBuilder sb) {
        while (it.hasNext()) {
            Object next = it.next();
            StringUtil.repeatSymbol(sb, io.netty.util.internal.StringUtil.SPACE, i);
            sb.append(next);
            if (set.add(next)) {
                sb.append('\n');
                printNodes(function.fun(next), function, i + 2, set, sb);
            } else {
                sb.append(" [...]\n");
            }
        }
    }

    @Deprecated
    public static void startPsiModification(String str) {
        beginPsiModification(str);
    }

    @Deprecated
    public static void finishPsiModification() {
        endPsiModification();
    }
}
