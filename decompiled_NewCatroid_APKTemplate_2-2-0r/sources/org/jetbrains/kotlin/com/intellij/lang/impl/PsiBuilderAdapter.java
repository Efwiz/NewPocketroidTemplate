package org.jetbrains.kotlin.com.intellij.lang.impl;

import net.bytebuddy.implementation.MethodDelegation;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.ITokenTypeRemapper;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.lang.PsiBuilder;
import org.jetbrains.kotlin.com.intellij.lang.WhitespaceSkippedCallback;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.com.intellij.util.diff.FlyweightCapableTreeStructure;

/* JADX INFO: loaded from: classes3.dex */
public class PsiBuilderAdapter implements PsiBuilder {
    protected final PsiBuilder myDelegate;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2 || i == 3 || i == 5 || i == 6) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 2 || i == 3 || i == 5 || i == 6) ? 2 : 3];
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 5:
            case 6:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/lang/impl/PsiBuilderAdapter";
                break;
            case 4:
                objArr[0] = "messageText";
                break;
            case 7:
                objArr[0] = "tokens";
                break;
            case 8:
            case 9:
            case 10:
            case 11:
                objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
                break;
            default:
                objArr[0] = MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX;
                break;
        }
        if (i == 1) {
            objArr[1] = "getDelegate";
        } else if (i == 2) {
            objArr[1] = "getOriginalText";
        } else if (i == 3) {
            objArr[1] = "mark";
        } else if (i == 5) {
            objArr[1] = "getTreeBuilt";
        } else if (i != 6) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/lang/impl/PsiBuilderAdapter";
        } else {
            objArr[1] = "getLightTree";
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 5:
            case 6:
                break;
            case 4:
                objArr[2] = "error";
                break;
            case 7:
                objArr[2] = "enforceCommentTokens";
                break;
            case 8:
                objArr[2] = "getUserData";
                break;
            case 9:
                objArr[2] = "putUserData";
                break;
            case 10:
                objArr[2] = "getUserDataUnprotected";
                break;
            case 11:
                objArr[2] = "putUserDataUnprotected";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2 && i != 3 && i != 5 && i != 6) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public PsiBuilderAdapter(PsiBuilder psiBuilder) {
        if (psiBuilder == null) {
            $$$reportNull$$$0(0);
        }
        this.myDelegate = psiBuilder;
    }

    public PsiBuilder getDelegate() {
        PsiBuilder psiBuilder = this.myDelegate;
        if (psiBuilder == null) {
            $$$reportNull$$$0(1);
        }
        return psiBuilder;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.PsiBuilder
    public Project getProject() {
        return this.myDelegate.getProject();
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.SyntaxTreeBuilder
    public CharSequence getOriginalText() {
        CharSequence originalText = this.myDelegate.getOriginalText();
        if (originalText == null) {
            $$$reportNull$$$0(2);
        }
        return originalText;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.SyntaxTreeBuilder
    public void advanceLexer() {
        this.myDelegate.advanceLexer();
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.SyntaxTreeBuilder
    public IElementType getTokenType() {
        return this.myDelegate.getTokenType();
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.SyntaxTreeBuilder
    public void setTokenTypeRemapper(ITokenTypeRemapper iTokenTypeRemapper) {
        this.myDelegate.setTokenTypeRemapper(iTokenTypeRemapper);
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.SyntaxTreeBuilder
    public void setWhitespaceSkippedCallback(WhitespaceSkippedCallback whitespaceSkippedCallback) {
        this.myDelegate.setWhitespaceSkippedCallback(whitespaceSkippedCallback);
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.SyntaxTreeBuilder
    public void remapCurrentToken(IElementType iElementType) {
        this.myDelegate.remapCurrentToken(iElementType);
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.SyntaxTreeBuilder
    public IElementType lookAhead(int i) {
        return this.myDelegate.lookAhead(i);
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.SyntaxTreeBuilder
    public IElementType rawLookup(int i) {
        return this.myDelegate.rawLookup(i);
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.SyntaxTreeBuilder
    public int rawTokenTypeStart(int i) {
        return this.myDelegate.rawTokenTypeStart(i);
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.SyntaxTreeBuilder
    public int rawTokenIndex() {
        return this.myDelegate.rawTokenIndex();
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.SyntaxTreeBuilder
    public String getTokenText() {
        return this.myDelegate.getTokenText();
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.SyntaxTreeBuilder
    public int getCurrentOffset() {
        return this.myDelegate.getCurrentOffset();
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.PsiBuilder
    /* JADX INFO: renamed from: mark, reason: merged with bridge method [inline-methods] */
    public PsiBuilder.Marker m6462mark() {
        PsiBuilder.Marker markerM6462mark = this.myDelegate.m6462mark();
        if (markerM6462mark == null) {
            $$$reportNull$$$0(3);
        }
        return markerM6462mark;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.SyntaxTreeBuilder
    public void error(String str) {
        if (str == null) {
            $$$reportNull$$$0(4);
        }
        this.myDelegate.error(str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.SyntaxTreeBuilder
    public boolean eof() {
        return this.myDelegate.eof();
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.PsiBuilder
    public ASTNode getTreeBuilt() {
        ASTNode treeBuilt = this.myDelegate.getTreeBuilt();
        if (treeBuilt == null) {
            $$$reportNull$$$0(5);
        }
        return treeBuilt;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.PsiBuilder
    public FlyweightCapableTreeStructure<LighterASTNode> getLightTree() {
        FlyweightCapableTreeStructure<LighterASTNode> lightTree = this.myDelegate.getLightTree();
        if (lightTree == null) {
            $$$reportNull$$$0(6);
        }
        return lightTree;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.SyntaxTreeBuilder
    public void setDebugMode(boolean z) {
        this.myDelegate.setDebugMode(z);
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.SyntaxTreeBuilder
    public void enforceCommentTokens(TokenSet tokenSet) {
        if (tokenSet == null) {
            $$$reportNull$$$0(7);
        }
        this.myDelegate.enforceCommentTokens(tokenSet);
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.SyntaxTreeBuilder
    public LighterASTNode getLatestDoneMarker() {
        return this.myDelegate.getLatestDoneMarker();
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolder
    public <T> T getUserData(Key<T> key) {
        if (key == null) {
            $$$reportNull$$$0(8);
        }
        return (T) this.myDelegate.getUserData(key);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolder
    public <T> void putUserData(Key<T> key, T t) {
        if (key == null) {
            $$$reportNull$$$0(9);
        }
        this.myDelegate.putUserData(key, t);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolderUnprotected
    public <T> T getUserDataUnprotected(Key<T> key) {
        if (key == null) {
            $$$reportNull$$$0(10);
        }
        return (T) this.myDelegate.getUserDataUnprotected(key);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolderUnprotected
    public <T> void putUserDataUnprotected(Key<T> key, T t) {
        if (key == null) {
            $$$reportNull$$$0(11);
        }
        this.myDelegate.putUserDataUnprotected(key, t);
    }
}
