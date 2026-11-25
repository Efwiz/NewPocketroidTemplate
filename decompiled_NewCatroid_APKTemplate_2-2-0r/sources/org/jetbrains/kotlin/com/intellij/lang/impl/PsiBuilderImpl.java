package org.jetbrains.kotlin.com.intellij.lang.impl;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import com.huawei.hms.mlplugin.asr.MLAsrCaptureConstants;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import name.antonsmirnov.firmata.FormatHelper;
import net.bytebuddy.implementation.MethodDelegation;
import org.antlr.stringtemplate.language.ASTExpr;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.lang.ASTFactory;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.ForeignLeafType;
import org.jetbrains.kotlin.com.intellij.lang.ITokenTypeRemapper;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTTokenNode;
import org.jetbrains.kotlin.com.intellij.lang.LighterLazyParseableNode;
import org.jetbrains.kotlin.com.intellij.lang.ParserDefinition;
import org.jetbrains.kotlin.com.intellij.lang.PsiBuilder;
import org.jetbrains.kotlin.com.intellij.lang.TokenWrapper;
import org.jetbrains.kotlin.com.intellij.lang.WhitespaceSkippedCallback;
import org.jetbrains.kotlin.com.intellij.lang.WhitespacesAndCommentsBinder;
import org.jetbrains.kotlin.com.intellij.lang.WhitespacesBinders;
import org.jetbrains.kotlin.com.intellij.lexer.Lexer;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.progress.EmptyProgressIndicator;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicatorProvider;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Comparing;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.openapi.util.Pair;
import org.jetbrains.kotlin.com.intellij.openapi.util.Ref;
import org.jetbrains.kotlin.com.intellij.openapi.util.UnprotectedUserDataHolder;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.psi.PsiErrorElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.TokenType;
import org.jetbrains.kotlin.com.intellij.psi.impl.BlockSupportImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.DiffLog;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiDocumentManagerBase;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.CharTableImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.FileContextUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.Factory;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.FileElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ForeignLeafPsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LazyParseableElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.PsiWhiteSpaceImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.SharedImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.text.BlockSupport;
import org.jetbrains.kotlin.com.intellij.psi.tree.CustomLanguageASTComparator;
import org.jetbrains.kotlin.com.intellij.psi.tree.ICustomParsingType;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.IFileElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.ILazyParseableElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.ILazyParseableElementTypeBase;
import org.jetbrains.kotlin.com.intellij.psi.tree.ILeafElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.ILightLazyParseableElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.com.intellij.util.CharTable;
import org.jetbrains.kotlin.com.intellij.util.ThreeState;
import org.jetbrains.kotlin.com.intellij.util.TripleFunction;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.com.intellij.util.containers.LimitedPool;
import org.jetbrains.kotlin.com.intellij.util.diff.DiffTreeChangeBuilder;
import org.jetbrains.kotlin.com.intellij.util.diff.FlyweightCapableTreeStructure;
import org.jetbrains.kotlin.com.intellij.util.diff.ShallowNodeComparator;
import org.jetbrains.kotlin.com.intellij.util.text.CharArrayUtil;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.mozilla.javascript.ES6Iterator;

/* JADX INFO: loaded from: classes3.dex */
public class PsiBuilderImpl extends UnprotectedUserDataHolder implements PsiBuilder {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private IElementType myCachedTokenType;
    private final Int2ObjectMap<LazyParseableToken> myChameleonCache;
    private CharTable myCharTable;
    private TokenSet myComments;
    private int myCurrentLexeme;
    private boolean myDebugMode;
    private PsiFile myFile;
    private final CharSequence myLastCommittedText;
    private final int[] myLexStarts;
    private final IElementType[] myLexTypes;
    private final int myLexemeCount;
    private final Lexer myLexer;
    private final int myOffset;
    private final MarkerOptionalData myOptionalData;
    private final ASTNode myOriginalTree;
    private final MyTreeStructure myParentLightTree;
    private final ParserDefinition myParserDefinition;
    private final MarkerPool myPool;
    private final MarkerProduction myProduction;
    private final Project myProject;
    private ITokenTypeRemapper myRemapper;
    private final CharSequence myText;
    private final char[] myTextArray;
    private boolean myTokenTypeChecked;
    private WhitespaceSkippedCallback myWhitespaceSkippedCallback;
    private final TokenSet myWhitespaces;
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiBuilderImpl.class);
    public static final Key<TripleFunction<ASTNode, LighterASTNode, FlyweightCapableTreeStructure<LighterASTNode>, ThreeState>> CUSTOM_COMPARATOR = Key.create("CUSTOM_COMPARATOR");
    private static final Key<TokenSequence> LAZY_PARSEABLE_TOKENS = Key.create("LAZY_PARSEABLE_TOKENS");
    private static TokenSet ourAnyLanguageWhitespaceTokens = TokenSet.EMPTY;

    private interface Node extends LighterASTNode {
        boolean tokenTextMatches(CharSequence charSequence);
    }

    /* JADX WARN: Code duplicated, block: B:13:0x0023 A[FALL_THROUGH] */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 27 && i != 29 && i != 48 && i != 45 && i != 46) {
            switch (i) {
                default:
                    switch (i) {
                        default:
                            switch (i) {
                                default:
                                    switch (i) {
                                        case 50:
                                        case 51:
                                        case 52:
                                            break;
                                        default:
                                            str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                                            break;
                                    }
                                case 41:
                                case 42:
                                case 43:
                                    str = "@NotNull method %s.%s must not return null";
                                    break;
                            }
                        case 33:
                        case 34:
                        case 35:
                            str = "@NotNull method %s.%s must not return null";
                            break;
                    }
                case 18:
                case 19:
                case 20:
                case 21:
                    str = "@NotNull method %s.%s must not return null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 27 && i != 29 && i != 48 && i != 45 && i != 46) {
            switch (i) {
                case 18:
                case 19:
                case 20:
                case 21:
                    i2 = 2;
                    break;
                default:
                    switch (i) {
                        case 33:
                        case 34:
                        case 35:
                            i2 = 2;
                            break;
                        default:
                            switch (i) {
                                case 41:
                                case 42:
                                case 43:
                                    i2 = 2;
                                    break;
                                default:
                                    switch (i) {
                                        case 50:
                                        case 51:
                                        case 52:
                                            i2 = 2;
                                            break;
                                        default:
                                            i2 = 3;
                                            break;
                                    }
                                    break;
                            }
                            break;
                    }
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 5:
            case 10:
            case 14:
                objArr[0] = "parserDefinition";
                break;
            case 2:
            case 6:
            case 11:
            case 15:
                objArr[0] = "lexer";
                break;
            case 3:
            case 8:
            case 13:
            case 16:
                objArr[0] = "text";
                break;
            case 4:
            case 9:
                objArr[0] = "project";
                break;
            case 7:
            case 12:
                objArr[0] = "chameleon";
                break;
            case 17:
                objArr[0] = "tokens";
                break;
            case 18:
            case 19:
            case 20:
            case 21:
            case 27:
            case 29:
            case 33:
            case 34:
            case 35:
            case 41:
            case 42:
            case 43:
            case 45:
            case 46:
            case 48:
            case 50:
            case 51:
            case 52:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/lang/impl/PsiBuilderImpl";
                break;
            case 22:
            case 23:
            case 24:
            case 25:
            case 40:
                objArr[0] = "marker";
                break;
            case 26:
                objArr[0] = "messageText";
                break;
            case 28:
            case 36:
                objArr[0] = "rootMarker";
                break;
            case 30:
                objArr[0] = "oldRoot";
                break;
            case 31:
                objArr[0] = "newRoot";
                break;
            case 32:
                objArr[0] = "lastCommittedText";
                break;
            case 37:
                objArr[0] = "rootNode";
                break;
            case 38:
                objArr[0] = "ast";
                break;
            case 39:
                objArr[0] = "startMarker";
                break;
            case 44:
            case 49:
            default:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
            case 47:
                objArr[0] = "node";
                break;
            case 53:
            case 54:
                objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
                break;
        }
        if (i == 27) {
            objArr[1] = "buildTree";
        } else if (i == 29) {
            objArr[1] = "createRootAST";
        } else if (i == 48) {
            objArr[1] = "getLexer";
        } else if (i != 45 && i != 46) {
            switch (i) {
                case 18:
                    objArr[1] = "precede";
                    break;
                case 19:
                    objArr[1] = "getOriginalText";
                    break;
                case 20:
                    objArr[1] = "mark";
                    break;
                case 21:
                    objArr[1] = "createMarker";
                    break;
                default:
                    switch (i) {
                        case 33:
                            objArr[1] = "merge";
                            break;
                        case 34:
                        case 35:
                            objArr[1] = "prepareLightTree";
                            break;
                        default:
                            switch (i) {
                                case 41:
                                case 42:
                                case 43:
                                    objArr[1] = "createComposite";
                                    break;
                                default:
                                    switch (i) {
                                        case 50:
                                        case 51:
                                        case 52:
                                            objArr[1] = "createLeaf";
                                            break;
                                        default:
                                            objArr[1] = "org/jetbrains/kotlin/com/intellij/lang/impl/PsiBuilderImpl";
                                            break;
                                    }
                                    break;
                            }
                            break;
                    }
                    break;
            }
        } else {
            objArr[1] = "createLazy";
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
                objArr[2] = "<init>";
                break;
            case 17:
                objArr[2] = "enforceCommentTokens";
                break;
            case 18:
            case 19:
            case 20:
            case 21:
            case 27:
            case 29:
            case 33:
            case 34:
            case 35:
            case 41:
            case 42:
            case 43:
            case 45:
            case 46:
            case 48:
            case 50:
            case 51:
            case 52:
                break;
            case 22:
                objArr[2] = "rollbackTo";
                break;
            case 23:
                objArr[2] = "hasErrorsAfter";
                break;
            case 24:
                objArr[2] = "processDone";
                break;
            case 25:
                objArr[2] = "doValidityChecks";
                break;
            case 26:
                objArr[2] = "error";
                break;
            case 28:
                objArr[2] = "createRootAST";
                break;
            case 30:
            case 31:
            case 32:
                objArr[2] = "merge";
                break;
            case 36:
            case 37:
                objArr[2] = "bind";
                break;
            case 38:
            case 39:
                objArr[2] = "collapseLeaves";
                break;
            case 40:
                objArr[2] = "createComposite";
                break;
            case 44:
                objArr[2] = "createLazy";
                break;
            case 47:
                objArr[2] = "getErrorMessage";
                break;
            case 49:
                objArr[2] = "createLeaf";
                break;
            case 53:
                objArr[2] = "getUserData";
                break;
            case 54:
                objArr[2] = "putUserData";
                break;
            default:
                objArr[2] = "registerWhitespaceToken";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 27 && i != 29 && i != 48 && i != 45 && i != 46) {
            switch (i) {
                case 18:
                case 19:
                case 20:
                case 21:
                    break;
                default:
                    switch (i) {
                        case 33:
                        case 34:
                        case 35:
                            break;
                        default:
                            switch (i) {
                                case 41:
                                case 42:
                                case 43:
                                    break;
                                default:
                                    switch (i) {
                                        case 50:
                                        case 51:
                                        case 52:
                                            break;
                                        default:
                                            throw new IllegalArgumentException(str2);
                                    }
                                    break;
                            }
                            break;
                    }
                    break;
            }
        }
        throw new IllegalStateException(str2);
    }

    private static boolean doLexingOptimizationCorrectionCheck() {
        return false;
    }

    public static void registerWhitespaceToken(IElementType iElementType) {
        if (iElementType == null) {
            $$$reportNull$$$0(0);
        }
        ourAnyLanguageWhitespaceTokens = TokenSet.orSet(ourAnyLanguageWhitespaceTokens, TokenSet.create(iElementType));
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public PsiBuilderImpl(Project project, PsiFile psiFile, ParserDefinition parserDefinition, Lexer lexer, CharTable charTable, CharSequence charSequence, ASTNode aSTNode, MyTreeStructure myTreeStructure) {
        this(project, psiFile, parserDefinition, lexer, charTable, charSequence, aSTNode, aSTNode == null ? null : aSTNode.getText(), myTreeStructure, null);
        if (parserDefinition == null) {
            $$$reportNull$$$0(1);
        }
        if (lexer == null) {
            $$$reportNull$$$0(2);
        }
        if (charSequence == null) {
            $$$reportNull$$$0(3);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public PsiBuilderImpl(Project project, ParserDefinition parserDefinition, Lexer lexer, ASTNode aSTNode, CharSequence charSequence) {
        this(project, SharedImplUtil.getContainingFile(aSTNode), parserDefinition, lexer, SharedImplUtil.findCharTableByTree(aSTNode), charSequence, (ASTNode) Pair.getFirst((Pair) aSTNode.getUserData(BlockSupport.TREE_TO_BE_REPARSED)), (CharSequence) Pair.getSecond((Pair) aSTNode.getUserData(BlockSupport.TREE_TO_BE_REPARSED)), null, aSTNode);
        if (project == null) {
            $$$reportNull$$$0(4);
        }
        if (parserDefinition == null) {
            $$$reportNull$$$0(5);
        }
        if (lexer == null) {
            $$$reportNull$$$0(6);
        }
        if (aSTNode == null) {
            $$$reportNull$$$0(7);
        }
        if (charSequence == null) {
            $$$reportNull$$$0(8);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public PsiBuilderImpl(Project project, ParserDefinition parserDefinition, Lexer lexer, LighterLazyParseableNode lighterLazyParseableNode, CharSequence charSequence) {
        this(project, lighterLazyParseableNode.getContainingFile(), parserDefinition, lexer, lighterLazyParseableNode.getCharTable(), charSequence, null, null, ((LazyParseableToken) lighterLazyParseableNode).myParentStructure, lighterLazyParseableNode);
        if (project == null) {
            $$$reportNull$$$0(9);
        }
        if (parserDefinition == null) {
            $$$reportNull$$$0(10);
        }
        if (lexer == null) {
            $$$reportNull$$$0(11);
        }
        if (lighterLazyParseableNode == null) {
            $$$reportNull$$$0(12);
        }
        if (charSequence == null) {
            $$$reportNull$$$0(13);
        }
    }

    private PsiBuilderImpl(Project project, PsiFile psiFile, ParserDefinition parserDefinition, Lexer lexer, CharTable charTable, CharSequence charSequence, ASTNode aSTNode, CharSequence charSequence2, MyTreeStructure myTreeStructure, Object obj) {
        if (parserDefinition == null) {
            $$$reportNull$$$0(14);
        }
        if (lexer == null) {
            $$$reportNull$$$0(15);
        }
        if (charSequence == null) {
            $$$reportNull$$$0(16);
        }
        this.myChameleonCache = new Int2ObjectOpenHashMap();
        MarkerPool markerPool = new MarkerPool(this);
        this.myPool = markerPool;
        MarkerOptionalData markerOptionalData = new MarkerOptionalData();
        this.myOptionalData = markerOptionalData;
        this.myProduction = new MarkerProduction(markerPool, markerOptionalData);
        this.myProject = project;
        this.myFile = psiFile;
        this.myParserDefinition = parserDefinition;
        this.myText = charSequence;
        this.myTextArray = CharArrayUtil.fromSequenceWithoutCopying(charSequence);
        this.myLexer = lexer;
        this.myWhitespaces = parserDefinition.getWhitespaceTokens();
        this.myComments = parserDefinition.getCommentTokens();
        this.myCharTable = charTable;
        this.myOriginalTree = aSTNode;
        this.myLastCommittedText = charSequence2;
        if ((aSTNode == null) != (charSequence2 == null)) {
            throw new IllegalArgumentException("originalTree and lastCommittedText must be null/notnull together but got: originalTree=" + aSTNode + "; lastCommittedText=" + (charSequence2 == null ? null : FormatHelper.QUOTE + ((Object) StringUtil.first(charSequence2, 80, true)) + FormatHelper.QUOTE));
        }
        this.myParentLightTree = myTreeStructure;
        this.myOffset = obj instanceof LazyParseableToken ? ((LazyParseableToken) obj).getStartOffset() : 0;
        TokenSequence tokenSequencePerformLexing = performLexing(obj);
        this.myLexStarts = tokenSequencePerformLexing.lexStarts;
        this.myLexTypes = tokenSequencePerformLexing.lexTypes;
        this.myLexemeCount = tokenSequencePerformLexing.lexemeCount;
    }

    /* JADX WARN: Code duplicated, block: B:10:0x002d  */
    /* JADX WARN: Code duplicated, block: B:7:0x0019  */
    /* JADX WARN: Code duplicated, block: B:9:0x001e  */
    private TokenSequence performLexing(Object obj) {
        TokenSequence parsedTokenSequence;
        if (obj instanceof LazyParseableToken) {
            LazyParseableToken lazyParseableToken = (LazyParseableToken) obj;
            if (!shouldReuseCollapsedTokens(lazyParseableToken.getTokenType())) {
                if (obj instanceof LazyParseableElement) {
                    LazyParseableElement lazyParseableElement = (LazyParseableElement) obj;
                    Key<TokenSequence> key = LAZY_PARSEABLE_TOKENS;
                    TokenSequence tokenSequence = (TokenSequence) lazyParseableElement.getUserData(key);
                    lazyParseableElement.putUserData(key, null);
                    parsedTokenSequence = tokenSequence;
                } else {
                    parsedTokenSequence = null;
                }
            } else {
                parsedTokenSequence = lazyParseableToken.getParsedTokenSequence();
                ProgressIndicatorProvider.checkCanceled();
            }
        } else if (obj instanceof LazyParseableElement) {
            LazyParseableElement lazyParseableElement2 = (LazyParseableElement) obj;
            Key<TokenSequence> key2 = LAZY_PARSEABLE_TOKENS;
            TokenSequence tokenSequence2 = (TokenSequence) lazyParseableElement2.getUserData(key2);
            lazyParseableElement2.putUserData(key2, null);
            parsedTokenSequence = tokenSequence2;
        } else {
            parsedTokenSequence = null;
        }
        if (parsedTokenSequence != null) {
            if (doLexingOptimizationCorrectionCheck()) {
                parsedTokenSequence.assertMatches(this.myText, this.myLexer);
            }
            return parsedTokenSequence;
        }
        return TokenSequence.performLexing(this.myText, this.myLexer);
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.PsiBuilder
    public Project getProject() {
        return this.myProject;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.SyntaxTreeBuilder
    public void enforceCommentTokens(TokenSet tokenSet) {
        if (tokenSet == null) {
            $$$reportNull$$$0(17);
        }
        this.myComments = tokenSet;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.SyntaxTreeBuilder
    public StartMarker getLatestDoneMarker() {
        for (int size = this.myProduction.size() - 1; size >= 0; size--) {
            StartMarker doneMarkerAt = this.myProduction.getDoneMarkerAt(size);
            if (doneMarkerAt != null) {
                return doneMarkerAt;
            }
        }
        return null;
    }

    public List<ProductionMarker> getProductions() {
        return new AbstractList<ProductionMarker>() { // from class: org.jetbrains.kotlin.com.intellij.lang.impl.PsiBuilderImpl.1
            @Override // java.util.AbstractList, java.util.List
            public ProductionMarker get(int i) {
                return PsiBuilderImpl.this.myProduction.getMarkerAt(i);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
            public int size() {
                return PsiBuilderImpl.this.myProduction.size();
            }
        };
    }

    public static abstract class ProductionMarker implements Node {
        final int markerId;
        protected final PsiBuilderImpl myBuilder;
        protected int myLexemeIndex;
        protected ProductionMarker myNext;
        protected ProductionMarker myParent;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i != 1) {
                objArr[0] = "builder";
            } else {
                objArr[0] = ModuleXmlParser.TYPE;
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/lang/impl/PsiBuilderImpl$ProductionMarker";
            if (i != 1) {
                objArr[2] = "<init>";
            } else {
                objArr[2] = "remapTokenType";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        abstract int getLexemeIndex(boolean z);

        abstract void setLexemeIndex(int i, boolean z);

        ProductionMarker(int i, PsiBuilderImpl psiBuilderImpl) {
            if (psiBuilderImpl == null) {
                $$$reportNull$$$0(0);
            }
            this.myLexemeIndex = -1;
            this.markerId = i;
            this.myBuilder = psiBuilderImpl;
        }

        void clean() {
            this.myLexemeIndex = -1;
            this.myNext = null;
            this.myParent = null;
        }

        @Override // org.jetbrains.kotlin.com.intellij.lang.LighterASTNode
        public int getStartOffset() {
            return this.myBuilder.myLexStarts[this.myLexemeIndex] + this.myBuilder.myOffset;
        }

        public void remapTokenType(IElementType iElementType) {
            if (iElementType == null) {
                $$$reportNull$$$0(1);
            }
            throw new UnsupportedOperationException("Shall not be called on this kind of markers");
        }

        public int getStartIndex() {
            return this.myLexemeIndex;
        }

        public int getEndIndex() {
            throw new UnsupportedOperationException("Shall not be called on this kind of markers");
        }
    }

    static class StartMarker extends ProductionMarker implements PsiBuilder.Marker {
        private int myDoneLexeme;
        private ProductionMarker myFirstChild;
        private ProductionMarker myLastChild;
        private IElementType myType;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 1 ? 3 : 2];
            switch (i) {
                case 1:
                    objArr[0] = "org/jetbrains/kotlin/com/intellij/lang/impl/PsiBuilderImpl$StartMarker";
                    break;
                case 2:
                case 3:
                case 4:
                case 6:
                case 12:
                    objArr[0] = ModuleXmlParser.TYPE;
                    break;
                case 5:
                case 7:
                case 11:
                    objArr[0] = "before";
                    break;
                case 8:
                    objArr[0] = MLAsrCaptureConstants.ASR_ERROR_MESSAGE;
                    break;
                case 9:
                case 10:
                    objArr[0] = CrashHianalyticsData.MESSAGE;
                    break;
                default:
                    objArr[0] = "node";
                    break;
            }
            if (i != 1) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/lang/impl/PsiBuilderImpl$StartMarker";
            } else {
                objArr[1] = "precede";
            }
            switch (i) {
                case 1:
                    break;
                case 2:
                    objArr[2] = ES6Iterator.DONE_PROPERTY;
                    break;
                case 3:
                    objArr[2] = SchemaSymbols.ATTVAL_COLLAPSE;
                    break;
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                    objArr[2] = "doneBefore";
                    break;
                case 9:
                    objArr[2] = "error";
                    break;
                case 10:
                case 11:
                    objArr[2] = "errorBefore";
                    break;
                case 12:
                    objArr[2] = "remapTokenType";
                    break;
                default:
                    objArr[2] = "addChild";
                    break;
            }
            String str2 = String.format(str, objArr);
            if (i == 1) {
                throw new IllegalStateException(str2);
            }
        }

        StartMarker(int i, PsiBuilderImpl psiBuilderImpl) {
            super(i, psiBuilderImpl);
            this.myDoneLexeme = -1;
        }

        @Override // org.jetbrains.kotlin.com.intellij.lang.impl.PsiBuilderImpl.ProductionMarker
        void clean() {
            super.clean();
            this.myBuilder.myOptionalData.clean(this.markerId);
            this.myType = null;
            this.myDoneLexeme = -1;
            this.myLastChild = null;
            this.myFirstChild = null;
        }

        @Override // org.jetbrains.kotlin.com.intellij.lang.impl.PsiBuilderImpl.Node
        public boolean tokenTextMatches(CharSequence charSequence) {
            if (this.myFirstChild == null) {
                return charSequence.length() == 0;
            }
            throw new IllegalStateException("textMatches shouldn't be called on non-empty composite nodes");
        }

        @Override // org.jetbrains.kotlin.com.intellij.lang.LighterASTNode
        public int getEndOffset() {
            return this.myBuilder.myLexStarts[getEndIndex()] + this.myBuilder.myOffset;
        }

        @Override // org.jetbrains.kotlin.com.intellij.lang.impl.PsiBuilderImpl.ProductionMarker
        public int getEndIndex() {
            return this.myDoneLexeme;
        }

        @Override // org.jetbrains.kotlin.com.intellij.lang.impl.PsiBuilderImpl.ProductionMarker
        void setLexemeIndex(int i, boolean z) {
            if (z) {
                this.myDoneLexeme = i;
            } else {
                this.myLexemeIndex = i;
            }
        }

        @Override // org.jetbrains.kotlin.com.intellij.lang.impl.PsiBuilderImpl.ProductionMarker
        int getLexemeIndex(boolean z) {
            return z ? this.myDoneLexeme : this.myLexemeIndex;
        }

        public void addChild(ProductionMarker productionMarker) {
            if (productionMarker == null) {
                $$$reportNull$$$0(0);
            }
            if (this.myFirstChild == null) {
                this.myFirstChild = productionMarker;
            } else {
                this.myLastChild.myNext = productionMarker;
            }
            this.myLastChild = productionMarker;
        }

        @Override // org.jetbrains.kotlin.com.intellij.lang.PsiBuilder.Marker
        /* JADX INFO: renamed from: precede, reason: merged with bridge method [inline-methods] */
        public PsiBuilder.Marker m6464precede() {
            PsiBuilder.Marker markerPrecede = this.myBuilder.precede(this);
            if (markerPrecede == null) {
                $$$reportNull$$$0(1);
            }
            return markerPrecede;
        }

        @Override // org.jetbrains.kotlin.com.intellij.lang.SyntaxTreeBuilder.Marker
        public void drop() {
            this.myBuilder.myProduction.dropMarker(this);
        }

        @Override // org.jetbrains.kotlin.com.intellij.lang.SyntaxTreeBuilder.Marker
        public void rollbackTo() {
            this.myBuilder.rollbackTo(this);
        }

        @Override // org.jetbrains.kotlin.com.intellij.lang.SyntaxTreeBuilder.Marker
        public void done(IElementType iElementType) {
            if (iElementType == null) {
                $$$reportNull$$$0(2);
            }
            if (iElementType == TokenType.ERROR_ELEMENT) {
                PsiBuilderImpl.LOG.warn("Error elements with empty message are discouraged. Please use builder.error() instead", new RuntimeException());
            }
            this.myType = iElementType;
            this.myBuilder.processDone(this, null, null);
        }

        @Override // org.jetbrains.kotlin.com.intellij.lang.SyntaxTreeBuilder.Marker
        public void collapse(IElementType iElementType) {
            if (iElementType == null) {
                $$$reportNull$$$0(3);
            }
            done(iElementType);
            this.myBuilder.myOptionalData.markCollapsed(this.markerId);
        }

        @Override // org.jetbrains.kotlin.com.intellij.lang.PsiBuilder.Marker
        public void doneBefore(IElementType iElementType, PsiBuilder.Marker marker) {
            if (iElementType == null) {
                $$$reportNull$$$0(4);
            }
            if (marker == null) {
                $$$reportNull$$$0(5);
            }
            if (iElementType == TokenType.ERROR_ELEMENT) {
                PsiBuilderImpl.LOG.warn("Error elements with empty message are discouraged. Please use builder.errorBefore() instead", new RuntimeException());
            }
            this.myType = iElementType;
            this.myBuilder.processDone(this, null, (StartMarker) marker);
        }

        public void doneBefore(IElementType iElementType, PsiBuilder.Marker marker, String str) {
            if (iElementType == null) {
                $$$reportNull$$$0(6);
            }
            if (marker == null) {
                $$$reportNull$$$0(7);
            }
            if (str == null) {
                $$$reportNull$$$0(8);
            }
            StartMarker startMarker = (StartMarker) marker;
            ErrorItem errorItemAllocateErrorItem = this.myBuilder.myPool.allocateErrorItem();
            errorItemAllocateErrorItem.myMessage = str;
            errorItemAllocateErrorItem.myLexemeIndex = startMarker.myLexemeIndex;
            this.myBuilder.myProduction.addBefore(errorItemAllocateErrorItem, startMarker);
            doneBefore(iElementType, marker);
        }

        @Override // org.jetbrains.kotlin.com.intellij.lang.SyntaxTreeBuilder.Marker
        public void error(String str) {
            if (str == null) {
                $$$reportNull$$$0(9);
            }
            this.myType = TokenType.ERROR_ELEMENT;
            this.myBuilder.processDone(this, str, null);
        }

        @Override // org.jetbrains.kotlin.com.intellij.lang.PsiBuilder.Marker
        public void errorBefore(String str, PsiBuilder.Marker marker) {
            if (str == null) {
                $$$reportNull$$$0(10);
            }
            if (marker == null) {
                $$$reportNull$$$0(11);
            }
            this.myType = TokenType.ERROR_ELEMENT;
            this.myBuilder.processDone(this, str, (StartMarker) marker);
        }

        @Override // org.jetbrains.kotlin.com.intellij.lang.LighterASTNode
        public IElementType getTokenType() {
            return this.myType;
        }

        @Override // org.jetbrains.kotlin.com.intellij.lang.impl.PsiBuilderImpl.ProductionMarker
        public void remapTokenType(IElementType iElementType) {
            if (iElementType == null) {
                $$$reportNull$$$0(12);
            }
            this.myType = iElementType;
        }

        @Override // org.jetbrains.kotlin.com.intellij.lang.SyntaxTreeBuilder.Marker
        public void setCustomEdgeTokenBinders(WhitespacesAndCommentsBinder whitespacesAndCommentsBinder, WhitespacesAndCommentsBinder whitespacesAndCommentsBinder2) {
            if (whitespacesAndCommentsBinder != null) {
                this.myBuilder.myOptionalData.assignBinder(this.markerId, whitespacesAndCommentsBinder, false);
            }
            if (whitespacesAndCommentsBinder2 != null) {
                this.myBuilder.myOptionalData.assignBinder(this.markerId, whitespacesAndCommentsBinder2, true);
            }
        }

        public String toString() {
            if (this.myLexemeIndex < 0) {
                return "<dropped>";
            }
            boolean zIsDone = isDone();
            CharSequence charSequenceSubSequence = this.myBuilder.getOriginalText().subSequence(getStartOffset() - this.myBuilder.myOffset, zIsDone ? getEndOffset() - this.myBuilder.myOffset : this.myBuilder.getCurrentOffset());
            return zIsDone ? charSequenceSubSequence.toString() : ((Object) charSequenceSubSequence) + StringUtil.ELLIPSIS;
        }

        boolean isDone() {
            return this.myDoneLexeme != -1;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public PsiBuilder.Marker precede(StartMarker startMarker) {
        if (this.myDebugMode) {
            this.myProduction.assertNoDoneMarkerAround(startMarker);
        }
        StartMarker startMarkerCreateMarker = createMarker(startMarker.myLexemeIndex);
        this.myProduction.addBefore(startMarkerCreateMarker, startMarker);
        if (startMarkerCreateMarker == null) {
            $$$reportNull$$$0(18);
        }
        return startMarkerCreateMarker;
    }

    private static abstract class Token implements Node {
        StartMarker myParentNode;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/lang/impl/PsiBuilderImpl$Token", "getText"));
        }

        abstract int getEndOffsetInBuilder();

        abstract int getStartOffsetInBuilder();

        private Token() {
        }

        @Override // org.jetbrains.kotlin.com.intellij.lang.impl.PsiBuilderImpl.Node
        public boolean tokenTextMatches(CharSequence charSequence) {
            int startOffsetInBuilder = getStartOffsetInBuilder();
            int endOffsetInBuilder = getEndOffsetInBuilder();
            if (endOffsetInBuilder - startOffsetInBuilder != charSequence.length()) {
                return false;
            }
            PsiBuilderImpl builder = getBuilder();
            return builder.myTextArray != null ? CharArrayUtil.regionMatches(builder.myTextArray, startOffsetInBuilder, endOffsetInBuilder, charSequence) : CharArrayUtil.regionMatches(builder.myText, startOffsetInBuilder, endOffsetInBuilder, charSequence);
        }

        @Override // org.jetbrains.kotlin.com.intellij.lang.LighterASTNode
        public final int getEndOffset() {
            return getEndOffsetInBuilder() + getBuilder().myOffset;
        }

        @Override // org.jetbrains.kotlin.com.intellij.lang.LighterASTNode
        public final int getStartOffset() {
            return getStartOffsetInBuilder() + getBuilder().myOffset;
        }

        public final CharSequence getText() {
            if (!(getTokenType() instanceof TokenWrapper)) {
                CharSequence charSequenceSubSequence = getBuilder().myText.subSequence(getStartOffsetInBuilder(), getEndOffsetInBuilder());
                if (charSequenceSubSequence == null) {
                    $$$reportNull$$$0(1);
                }
                return charSequenceSubSequence;
            }
            String value = ((TokenWrapper) getTokenType()).getValue();
            if (value == null) {
                $$$reportNull$$$0(0);
            }
            return value;
        }

        PsiBuilderImpl getBuilder() {
            return this.myParentNode.myBuilder;
        }

        void clean() {
            this.myParentNode = null;
        }

        public String toString() {
            return getText().toString();
        }
    }

    private static abstract class TokenRange extends Token {
        private int myTokenEnd;
        private int myTokenStart;
        private IElementType myTokenType;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", ModuleXmlParser.TYPE, "org/jetbrains/kotlin/com/intellij/lang/impl/PsiBuilderImpl$TokenRange", "initToken"));
        }

        private TokenRange() {
            super();
        }

        @Override // org.jetbrains.kotlin.com.intellij.lang.impl.PsiBuilderImpl.Token
        int getStartOffsetInBuilder() {
            return this.myTokenStart;
        }

        @Override // org.jetbrains.kotlin.com.intellij.lang.impl.PsiBuilderImpl.Token
        int getEndOffsetInBuilder() {
            return this.myTokenEnd;
        }

        @Override // org.jetbrains.kotlin.com.intellij.lang.LighterASTNode
        public IElementType getTokenType() {
            return this.myTokenType;
        }

        void initToken(IElementType iElementType, StartMarker startMarker, int i, int i2) {
            if (iElementType == null) {
                $$$reportNull$$$0(0);
            }
            this.myParentNode = startMarker;
            this.myTokenType = iElementType;
            this.myTokenStart = i;
            this.myTokenEnd = i2;
        }
    }

    private static class TokenRangeNode extends TokenRange implements LighterASTTokenNode {
        private TokenRangeNode() {
            super();
        }
    }

    private static class SingleLexemeNode extends Token implements LighterASTTokenNode {
        private int myLexemeIndex;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/lang/impl/PsiBuilderImpl$SingleLexemeNode", "getTokenType"));
        }

        private SingleLexemeNode() {
            super();
        }

        @Override // org.jetbrains.kotlin.com.intellij.lang.impl.PsiBuilderImpl.Token
        int getStartOffsetInBuilder() {
            return getBuilder().myLexStarts[this.myLexemeIndex];
        }

        @Override // org.jetbrains.kotlin.com.intellij.lang.impl.PsiBuilderImpl.Token
        int getEndOffsetInBuilder() {
            return getBuilder().myLexStarts[this.myLexemeIndex + 1];
        }

        @Override // org.jetbrains.kotlin.com.intellij.lang.LighterASTNode
        public IElementType getTokenType() {
            IElementType iElementType = getBuilder().myLexTypes[this.myLexemeIndex];
            if (iElementType == null) {
                $$$reportNull$$$0(0);
            }
            return iElementType;
        }
    }

    private static class LazyParseableToken extends TokenRange implements LighterLazyParseableNode {
        private final int myEndIndex;
        private final MyTreeStructure myParentStructure;
        private FlyweightCapableTreeStructure<LighterASTNode> myParsed;
        private final int myStartIndex;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "visitor", "org/jetbrains/kotlin/com/intellij/lang/impl/PsiBuilderImpl$LazyParseableToken", "accept"));
        }

        LazyParseableToken(MyTreeStructure myTreeStructure, int i, int i2) {
            super();
            this.myParentStructure = myTreeStructure;
            this.myStartIndex = i;
            this.myEndIndex = i2;
        }

        @Override // org.jetbrains.kotlin.com.intellij.lang.LighterLazyParseableNode
        public PsiFile getContainingFile() {
            return getBuilder().myFile;
        }

        @Override // org.jetbrains.kotlin.com.intellij.lang.LighterLazyParseableNode
        public CharTable getCharTable() {
            return getBuilder().myCharTable;
        }

        public FlyweightCapableTreeStructure<LighterASTNode> parseContents() {
            FlyweightCapableTreeStructure<LighterASTNode> flyweightCapableTreeStructure = this.myParsed;
            if (flyweightCapableTreeStructure != null) {
                return flyweightCapableTreeStructure;
            }
            FlyweightCapableTreeStructure<LighterASTNode> contents = ((ILightLazyParseableElementType) getTokenType()).parseContents(this);
            this.myParsed = contents;
            return contents;
        }

        @Override // org.jetbrains.kotlin.com.intellij.lang.LighterLazyParseableNode
        public boolean accept(LighterLazyParseableNode.Visitor visitor) {
            if (visitor == null) {
                $$$reportNull$$$0(0);
            }
            for (int i = this.myStartIndex; i < this.myEndIndex; i++) {
                if (!visitor.visit(getBuilder().myLexTypes[i])) {
                    return false;
                }
            }
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public TokenSequence getParsedTokenSequence() {
            int i = this.myEndIndex - this.myStartIndex;
            if (i == 1) {
                return null;
            }
            int i2 = i + 1;
            int[] iArr = new int[i2];
            System.arraycopy(getBuilder().myLexStarts, this.myStartIndex, iArr, 0, i);
            int i3 = getBuilder().myLexStarts[this.myStartIndex];
            for (int i4 = 0; i4 < i; i4++) {
                iArr[i4] = iArr[i4] - i3;
            }
            iArr[i] = getEndOffset() - getStartOffset();
            IElementType[] iElementTypeArr = new IElementType[i2];
            System.arraycopy(getBuilder().myLexTypes, this.myStartIndex, iElementTypeArr, 0, i);
            return new TokenSequence(iArr, iElementTypeArr, i, getText());
        }
    }

    static class ErrorItem extends ProductionMarker {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private String myMessage;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/lang/impl/PsiBuilderImpl$ErrorItem", "getTokenType"));
        }

        ErrorItem(int i, PsiBuilderImpl psiBuilderImpl) {
            super(i, psiBuilderImpl);
        }

        @Override // org.jetbrains.kotlin.com.intellij.lang.impl.PsiBuilderImpl.ProductionMarker
        void clean() {
            super.clean();
            this.myMessage = null;
        }

        @Override // org.jetbrains.kotlin.com.intellij.lang.impl.PsiBuilderImpl.ProductionMarker
        void setLexemeIndex(int i, boolean z) {
            this.myLexemeIndex = i;
        }

        @Override // org.jetbrains.kotlin.com.intellij.lang.impl.PsiBuilderImpl.ProductionMarker
        int getLexemeIndex(boolean z) {
            return this.myLexemeIndex;
        }

        @Override // org.jetbrains.kotlin.com.intellij.lang.impl.PsiBuilderImpl.Node
        public boolean tokenTextMatches(CharSequence charSequence) {
            return charSequence.length() == 0;
        }

        @Override // org.jetbrains.kotlin.com.intellij.lang.LighterASTNode
        public int getEndOffset() {
            return getStartOffset();
        }

        @Override // org.jetbrains.kotlin.com.intellij.lang.impl.PsiBuilderImpl.ProductionMarker
        public int getEndIndex() {
            return getStartIndex();
        }

        @Override // org.jetbrains.kotlin.com.intellij.lang.LighterASTNode
        public IElementType getTokenType() {
            IElementType iElementType = TokenType.ERROR_ELEMENT;
            if (iElementType == null) {
                $$$reportNull$$$0(0);
            }
            return iElementType;
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.SyntaxTreeBuilder
    public CharSequence getOriginalText() {
        CharSequence charSequence = this.myText;
        if (charSequence == null) {
            $$$reportNull$$$0(19);
        }
        return charSequence;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.SyntaxTreeBuilder
    public IElementType getTokenType() {
        IElementType iElementType = this.myCachedTokenType;
        if (iElementType != null) {
            return iElementType;
        }
        IElementType iElementTypeCalcTokenType = calcTokenType();
        this.myCachedTokenType = iElementTypeCalcTokenType;
        return iElementTypeCalcTokenType;
    }

    private void clearCachedTokenType() {
        this.myCachedTokenType = null;
    }

    private IElementType remapCurrentToken() {
        IElementType iElementType = this.myCachedTokenType;
        if (iElementType != null) {
            return iElementType;
        }
        ITokenTypeRemapper iTokenTypeRemapper = this.myRemapper;
        if (iTokenTypeRemapper != null) {
            IElementType[] iElementTypeArr = this.myLexTypes;
            int i = this.myCurrentLexeme;
            IElementType iElementType2 = iElementTypeArr[i];
            int[] iArr = this.myLexStarts;
            remapCurrentToken(iTokenTypeRemapper.filter(iElementType2, iArr[i], iArr[i + 1], this.myText));
        }
        return this.myLexTypes[this.myCurrentLexeme];
    }

    private IElementType calcTokenType() {
        if (eof()) {
            return null;
        }
        if (this.myRemapper != null) {
            skipWhitespace();
        }
        return this.myLexTypes[this.myCurrentLexeme];
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.SyntaxTreeBuilder
    public void setTokenTypeRemapper(ITokenTypeRemapper iTokenTypeRemapper) {
        this.myRemapper = iTokenTypeRemapper;
        this.myTokenTypeChecked = false;
        clearCachedTokenType();
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.SyntaxTreeBuilder
    public void remapCurrentToken(IElementType iElementType) {
        this.myLexTypes[this.myCurrentLexeme] = iElementType;
        clearCachedTokenType();
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.SyntaxTreeBuilder
    public IElementType lookAhead(int i) {
        int iShiftOverWhitespaceForward = shiftOverWhitespaceForward(this.myCurrentLexeme);
        while (i > 0) {
            iShiftOverWhitespaceForward = shiftOverWhitespaceForward(iShiftOverWhitespaceForward + 1);
            i--;
        }
        if (iShiftOverWhitespaceForward < this.myLexemeCount) {
            return this.myLexTypes[iShiftOverWhitespaceForward];
        }
        return null;
    }

    private int shiftOverWhitespaceForward(int i) {
        while (i < this.myLexemeCount && whitespaceOrComment(this.myLexTypes[i])) {
            i++;
        }
        return i;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.SyntaxTreeBuilder
    public IElementType rawLookup(int i) {
        int i2 = this.myCurrentLexeme + i;
        if (i2 >= this.myLexemeCount || i2 < 0) {
            return null;
        }
        return this.myLexTypes[i2];
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.SyntaxTreeBuilder
    public int rawTokenTypeStart(int i) {
        int i2 = this.myCurrentLexeme + i;
        if (i2 < 0) {
            return -1;
        }
        return i2 >= this.myLexemeCount ? getOriginalText().length() : this.myLexStarts[i2];
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.SyntaxTreeBuilder
    public int rawTokenIndex() {
        return this.myCurrentLexeme;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.SyntaxTreeBuilder
    public void setWhitespaceSkippedCallback(WhitespaceSkippedCallback whitespaceSkippedCallback) {
        this.myWhitespaceSkippedCallback = whitespaceSkippedCallback;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.SyntaxTreeBuilder
    public void advanceLexer() {
        ProgressIndicatorProvider.checkCanceled();
        if (eof()) {
            return;
        }
        this.myTokenTypeChecked = false;
        this.myCurrentLexeme++;
        clearCachedTokenType();
    }

    private void skipWhitespace() {
        while (this.myCurrentLexeme < this.myLexemeCount && whitespaceOrComment(remapCurrentToken())) {
            IElementType[] iElementTypeArr = this.myLexTypes;
            int i = this.myCurrentLexeme;
            IElementType iElementType = iElementTypeArr[i];
            int[] iArr = this.myLexStarts;
            onSkip(iElementType, iArr[i], i + 1 < this.myLexemeCount ? iArr[i + 1] : this.myText.length());
            this.myCurrentLexeme++;
            clearCachedTokenType();
        }
    }

    private void onSkip(IElementType iElementType, int i, int i2) {
        WhitespaceSkippedCallback whitespaceSkippedCallback = this.myWhitespaceSkippedCallback;
        if (whitespaceSkippedCallback != null) {
            whitespaceSkippedCallback.onSkip(iElementType, i, i2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.SyntaxTreeBuilder
    public int getCurrentOffset() {
        return eof() ? getOriginalText().length() : this.myLexStarts[this.myCurrentLexeme];
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.SyntaxTreeBuilder
    public String getTokenText() {
        if (eof()) {
            return null;
        }
        IElementType tokenType = getTokenType();
        if (tokenType instanceof TokenWrapper) {
            return ((TokenWrapper) tokenType).getValue();
        }
        CharSequence charSequence = this.myText;
        int[] iArr = this.myLexStarts;
        int i = this.myCurrentLexeme;
        return charSequence.subSequence(iArr[i], iArr[i + 1]).toString();
    }

    public boolean whitespaceOrComment(IElementType iElementType) {
        return this.myWhitespaces.contains(iElementType) || this.myComments.contains(iElementType);
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.PsiBuilder
    /* JADX INFO: renamed from: mark, reason: merged with bridge method [inline-methods] */
    public PsiBuilder.Marker m6463mark() {
        if (!this.myProduction.isEmpty()) {
            skipWhitespace();
        }
        StartMarker startMarkerCreateMarker = createMarker(this.myCurrentLexeme);
        this.myProduction.addMarker(startMarkerCreateMarker);
        if (startMarkerCreateMarker == null) {
            $$$reportNull$$$0(20);
        }
        return startMarkerCreateMarker;
    }

    private StartMarker createMarker(int i) {
        StartMarker startMarkerAllocateStartMarker = this.myPool.allocateStartMarker();
        startMarkerAllocateStartMarker.myLexemeIndex = i;
        if (this.myDebugMode) {
            this.myOptionalData.notifyAllocated(startMarkerAllocateStartMarker.markerId);
        }
        if (startMarkerAllocateStartMarker == null) {
            $$$reportNull$$$0(21);
        }
        return startMarkerAllocateStartMarker;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.SyntaxTreeBuilder
    public final boolean eof() {
        if (!this.myTokenTypeChecked) {
            this.myTokenTypeChecked = true;
            skipWhitespace();
        }
        return this.myCurrentLexeme >= this.myLexemeCount;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void rollbackTo(StartMarker startMarker) {
        if (startMarker == null) {
            $$$reportNull$$$0(22);
        }
        if (this.myDebugMode) {
            this.myProduction.assertNoDoneMarkerAround(startMarker);
        }
        this.myCurrentLexeme = startMarker.myLexemeIndex;
        this.myTokenTypeChecked = true;
        this.myProduction.rollbackTo(startMarker);
        clearCachedTokenType();
    }

    public boolean hasErrorsAfter(PsiBuilder.Marker marker) {
        if (marker == null) {
            $$$reportNull$$$0(23);
        }
        return this.myProduction.hasErrorsAfter((StartMarker) marker);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processDone(StartMarker startMarker, String str, StartMarker startMarker2) {
        if (startMarker == null) {
            $$$reportNull$$$0(24);
        }
        doValidityChecks(startMarker, startMarker2);
        if (str != null) {
            this.myOptionalData.setErrorMessage(startMarker.markerId, str);
        }
        int i = startMarker2 == null ? this.myCurrentLexeme : startMarker2.myLexemeIndex;
        if (startMarker.myType.isLeftBound() && isEmpty(startMarker.myLexemeIndex, i)) {
            startMarker.setCustomEdgeTokenBinders(WhitespacesBinders.DEFAULT_RIGHT_BINDER, null);
        }
        startMarker.myDoneLexeme = i;
        this.myProduction.addDone(startMarker, startMarker2);
    }

    private boolean isEmpty(int i, int i2) {
        while (i < i2) {
            if (!whitespaceOrComment(this.myLexTypes[i])) {
                return false;
            }
            i++;
        }
        return true;
    }

    private void doValidityChecks(StartMarker startMarker, StartMarker startMarker2) {
        if (startMarker == null) {
            $$$reportNull$$$0(25);
        }
        if (startMarker.isDone()) {
            LOG.error("Marker already done.");
        }
        if (this.myDebugMode) {
            this.myProduction.doHeavyChecksOnMarkerDone(startMarker, startMarker2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.SyntaxTreeBuilder
    public void error(String str) {
        if (str == null) {
            $$$reportNull$$$0(26);
        }
        MarkerProduction markerProduction = this.myProduction;
        ProductionMarker startMarkerAt = markerProduction.getStartMarkerAt(markerProduction.size() - 1);
        if ((startMarkerAt instanceof ErrorItem) && startMarkerAt.myLexemeIndex == this.myCurrentLexeme) {
            return;
        }
        ErrorItem errorItemAllocateErrorItem = this.myPool.allocateErrorItem();
        errorItemAllocateErrorItem.myMessage = str;
        errorItemAllocateErrorItem.myLexemeIndex = this.myCurrentLexeme;
        this.myProduction.addMarker(errorItemAllocateErrorItem);
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.PsiBuilder
    public ASTNode getTreeBuilt() {
        return buildTree();
    }

    private ASTNode buildTree() {
        TreeElement firstChildNode;
        StartMarker startMarkerPrepareLightTree = prepareLightTree();
        PsiFile psiFile = this.myFile;
        boolean z = psiFile != null && BlockSupport.isTooDeep(psiFile.getOriginalFile());
        ASTNode aSTNode = this.myOriginalTree;
        if (aSTNode != null && !z) {
            throw new BlockSupport.ReparsedSuccessfullyException(merge(aSTNode, startMarkerPrepareLightTree, this.myLastCommittedText));
        }
        TreeElement treeElementCreateRootAST = createRootAST(startMarkerPrepareLightTree);
        bind(startMarkerPrepareLightTree, (CompositeElement) treeElementCreateRootAST);
        if (z && !(treeElementCreateRootAST instanceof FileElement) && (firstChildNode = treeElementCreateRootAST.getFirstChildNode()) != null) {
            firstChildNode.putUserData(BlockSupport.TREE_DEPTH_LIMIT_EXCEEDED, Boolean.TRUE);
        }
        if (treeElementCreateRootAST.getTextLength() != this.myText.length()) {
            LOG.error("Inconsistent root node. ; node type: " + treeElementCreateRootAST.getElementType() + "; text length: " + this.myText.length() + "; node length: " + treeElementCreateRootAST.getTextLength() + "; partial text: " + StringUtil.shortenTextWithEllipsis(this.myText.toString(), 512, 256) + "; partial node text: " + StringUtil.shortenTextWithEllipsis(treeElementCreateRootAST.getText(), 512, 256));
        }
        if (treeElementCreateRootAST == null) {
            $$$reportNull$$$0(27);
        }
        return treeElementCreateRootAST;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.PsiBuilder
    public FlyweightCapableTreeStructure<LighterASTNode> getLightTree() {
        return new MyTreeStructure(prepareLightTree(), this.myParentLightTree);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public TreeElement createRootAST(StartMarker startMarker) {
        TreeElement treeElementCreateComposite;
        if (startMarker == null) {
            $$$reportNull$$$0(28);
        }
        IElementType tokenType = startMarker.getTokenType();
        if (tokenType instanceof ILazyParseableElementType) {
            treeElementCreateComposite = createLazy((ILazyParseableElementType) tokenType, null, getASTFactory());
        } else {
            treeElementCreateComposite = createComposite(startMarker, getASTFactory());
        }
        if (this.myCharTable == null) {
            this.myCharTable = treeElementCreateComposite instanceof FileElement ? ((FileElement) treeElementCreateComposite).getCharTable() : new CharTableImpl();
        }
        if (!(treeElementCreateComposite instanceof FileElement)) {
            treeElementCreateComposite.putUserData(CharTable.CHAR_TABLE_KEY, this.myCharTable);
        }
        if (treeElementCreateComposite == null) {
            $$$reportNull$$$0(29);
        }
        return treeElementCreateComposite;
    }

    private ASTFactory getASTFactory() {
        Object obj = this.myParserDefinition;
        if (obj instanceof ASTFactory) {
            return (ASTFactory) obj;
        }
        return null;
    }

    private static final class ConvertFromTokensToASTBuilder implements DiffTreeChangeBuilder<ASTNode, LighterASTNode> {
        private final ASTConverter myConverter;
        private final DiffTreeChangeBuilder<? super ASTNode, ? super ASTNode> myDelegate;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            switch (i) {
                case 1:
                    objArr[0] = MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX;
                    break;
                case 2:
                case 4:
                    objArr[0] = "oldParent";
                    break;
                case 3:
                    objArr[0] = "oldNode";
                    break;
                case 5:
                    objArr[0] = "newNode";
                    break;
                case 6:
                    objArr[0] = "oldChild";
                    break;
                case 7:
                    objArr[0] = "newChild";
                    break;
                default:
                    objArr[0] = "rootNode";
                    break;
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/lang/impl/PsiBuilderImpl$ConvertFromTokensToASTBuilder";
            switch (i) {
                case 2:
                case 3:
                    objArr[2] = "nodeDeleted";
                    break;
                case 4:
                case 5:
                    objArr[2] = "nodeInserted";
                    break;
                case 6:
                case 7:
                    objArr[2] = "nodeReplaced";
                    break;
                default:
                    objArr[2] = "<init>";
                    break;
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        private ConvertFromTokensToASTBuilder(StartMarker startMarker, DiffTreeChangeBuilder<? super ASTNode, ? super ASTNode> diffTreeChangeBuilder, ASTFactory aSTFactory) {
            if (startMarker == null) {
                $$$reportNull$$$0(0);
            }
            if (diffTreeChangeBuilder == null) {
                $$$reportNull$$$0(1);
            }
            this.myDelegate = diffTreeChangeBuilder;
            this.myConverter = new ASTConverter(startMarker, aSTFactory);
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.diff.DiffTreeChangeBuilder
        public void nodeDeleted(ASTNode aSTNode, ASTNode aSTNode2) {
            if (aSTNode == null) {
                $$$reportNull$$$0(2);
            }
            if (aSTNode2 == null) {
                $$$reportNull$$$0(3);
            }
            this.myDelegate.nodeDeleted(aSTNode, aSTNode2);
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.diff.DiffTreeChangeBuilder
        public void nodeInserted(ASTNode aSTNode, LighterASTNode lighterASTNode, int i) {
            if (aSTNode == null) {
                $$$reportNull$$$0(4);
            }
            if (lighterASTNode == null) {
                $$$reportNull$$$0(5);
            }
            this.myDelegate.nodeInserted(aSTNode, this.myConverter.apply((Node) lighterASTNode), i);
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.diff.DiffTreeChangeBuilder
        public void nodeReplaced(ASTNode aSTNode, LighterASTNode lighterASTNode) {
            if (aSTNode == null) {
                $$$reportNull$$$0(6);
            }
            if (lighterASTNode == null) {
                $$$reportNull$$$0(7);
            }
            this.myDelegate.nodeReplaced(aSTNode, this.myConverter.apply((Node) lighterASTNode));
        }
    }

    private DiffLog merge(ASTNode aSTNode, StartMarker startMarker, CharSequence charSequence) {
        if (aSTNode == null) {
            $$$reportNull$$$0(30);
        }
        if (startMarker == null) {
            $$$reportNull$$$0(31);
        }
        if (charSequence == null) {
            $$$reportNull$$$0(32);
        }
        DiffLog diffLog = new DiffLog();
        ConvertFromTokensToASTBuilder convertFromTokensToASTBuilder = new ConvertFromTokensToASTBuilder(startMarker, diffLog, getASTFactory());
        MyTreeStructure myTreeStructure = new MyTreeStructure(startMarker, null);
        MyComparator myComparator = new MyComparator((TripleFunction) getUserData(CUSTOM_COMPARATOR), CustomLanguageASTComparator.getMatchingComparators(this.myFile), myTreeStructure);
        ProgressIndicator globalProgressIndicator = ProgressIndicatorProvider.getGlobalProgressIndicator();
        if (globalProgressIndicator == null) {
            globalProgressIndicator = new EmptyProgressIndicator();
        }
        BlockSupportImpl.diffTrees(aSTNode, convertFromTokensToASTBuilder, myComparator, myTreeStructure, globalProgressIndicator, charSequence);
        return diffLog;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private StartMarker prepareLightTree() {
        if (this.myProduction.isEmpty()) {
            LOG.error("Parser produced no markers. Text:\n" + ((Object) this.myText));
        }
        StartMarker startMarker = (StartMarker) Objects.requireNonNull(this.myProduction.getStartMarkerAt(0));
        if (startMarker.myFirstChild != null) {
            if (startMarker == null) {
                $$$reportNull$$$0(34);
            }
            return startMarker;
        }
        this.myTokenTypeChecked = true;
        balanceWhiteSpaces();
        startMarker.myNext = null;
        startMarker.myParent = startMarker.myFirstChild = startMarker.myLastChild = null;
        ObjectArrayList objectArrayList = new ObjectArrayList();
        objectArrayList.push(startMarker);
        int[] iArrElements = this.myProduction.elements();
        ProductionMarker[] productionMarkerArrElements = this.myPool.elements();
        int size = this.myProduction.size();
        int i = -1;
        StartMarker startMarker2 = startMarker;
        int i2 = 0;
        int i3 = 0;
        boolean z = false;
        for (int i4 = 1; i4 < size; i4++) {
            int i5 = iArrElements[i4];
            ProductionMarker productionMarker = i5 > 0 ? productionMarkerArrElements[i5] : null;
            if (productionMarker instanceof StartMarker) {
                StartMarker startMarker3 = (StartMarker) productionMarker;
                startMarker3.myParent = startMarker2;
                startMarker3.myNext = null;
                startMarker3.myFirstChild = startMarker3.myLastChild = null;
                startMarker2.addChild(startMarker3);
                objectArrayList.push(startMarker2);
                i2++;
                if (i2 > i3) {
                    i3 = i2;
                }
                startMarker2 = startMarker3;
            } else if (productionMarker instanceof ErrorItem) {
                productionMarker.myParent = startMarker2;
                int i6 = productionMarker.myLexemeIndex;
                if (i6 != i) {
                    startMarker2.addChild(productionMarker);
                    i = i6;
                }
            } else {
                if (isCollapsedChameleon(startMarker2)) {
                    z = true;
                }
                assertMarkersBalanced(i5 < 0 && productionMarkerArrElements[-i5] == startMarker2, productionMarker);
                startMarker2 = (StartMarker) objectArrayList.pop();
                i2--;
            }
        }
        int i7 = this.myCurrentLexeme;
        int i8 = this.myLexemeCount;
        if (i7 < i8) {
            LOG.error("Tokens " + ContainerUtil.newArrayList(this.myLexTypes, i7, i8) + " were not inserted into the tree. " + (this.myFile != null ? this.myFile.getLanguage() + ", " : "") + "Text:\n" + ((Object) this.myText));
        }
        if (startMarker.getEndIndex() < this.myLexemeCount) {
            LOG.error("Tokens " + ContainerUtil.newArrayList(this.myLexTypes, startMarker.getEndIndex(), this.myLexemeCount) + " are outside of root element \"" + startMarker.myType + "\". Text:\n" + ((Object) this.myText));
        }
        assertMarkersBalanced(startMarker2 == startMarker, startMarker2);
        checkTreeDepth(i3, startMarker.getTokenType() instanceof IFileElementType, z);
        clearCachedTokenType();
        if (startMarker == null) {
            $$$reportNull$$$0(35);
        }
        return startMarker;
    }

    private static boolean isCollapsedChameleon(StartMarker startMarker) {
        return (startMarker.getTokenType() instanceof ILazyParseableElementTypeBase) && startMarker.myFirstChild == null && startMarker.getTextLength() > 0;
    }

    private void assertMarkersBalanced(boolean z, ProductionMarker productionMarker) {
        if (z) {
            return;
        }
        reportUnbalancedMarkers(productionMarker);
    }

    private void reportUnbalancedMarkers(ProductionMarker productionMarker) {
        int startIndex = productionMarker != null ? productionMarker.getStartIndex() + 1 : this.myLexStarts.length;
        int[] iArr = this.myLexStarts;
        LOG.error("Unbalanced tree. Most probably caused by unbalanced markers. Try calling setDebugMode(true) against PsiBuilder passed to identify exact location of the problem\nlanguage: " + (this.myFile != null ? this.myFile.getLanguage() + ", " : "") + "\ncontext: '" + ((Object) (startIndex < iArr.length ? this.myText.subSequence(Math.max(0, iArr[startIndex] - 1000), this.myLexStarts[startIndex]) : "<none>")) + FormatHelper.QUOTE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r12v0 */
    /* JADX WARN: Type inference failed for: r12v1, types: [org.jetbrains.kotlin.com.intellij.lang.impl.PsiBuilderImpl$ProductionMarker] */
    /* JADX WARN: Type inference failed for: r12v2, types: [org.jetbrains.kotlin.com.intellij.lang.impl.PsiBuilderImpl$ProductionMarker] */
    /* JADX WARN: Type inference failed for: r12v4, types: [org.jetbrains.kotlin.com.intellij.lang.impl.PsiBuilderImpl$ProductionMarker] */
    /* JADX WARN: Type inference failed for: r12v7 */
    /* JADX WARN: Type inference failed for: r13v4 */
    /* JADX WARN: Type inference failed for: r18v0, types: [org.jetbrains.kotlin.com.intellij.lang.impl.PsiBuilderImpl] */
    private void balanceWhiteSpaces() {
        WhitespacesAndCommentsBinder binder;
        int lexemeIndex;
        AnonymousClass1 anonymousClass1 = null;
        RelativeTokenTypesView relativeTokenTypesView = new RelativeTokenTypesView();
        RelativeTokenTextView relativeTokenTextView = new RelativeTokenTextView();
        int[] iArrElements = this.myProduction.elements();
        ProductionMarker[] productionMarkerArrElements = this.myPool.elements();
        boolean z = true;
        int size = this.myProduction.size() - 1;
        int i = 1;
        int i2 = 0;
        while (i < size) {
            int i3 = iArrElements[i];
            ?? r12 = i3 > 0 ? productionMarkerArrElements[i3] : anonymousClass1;
            if (r12 instanceof StartMarker) {
                assertMarkersBalanced(((StartMarker) r12).isDone(), r12);
            }
            boolean z2 = r12 == 0 ? z : false;
            if (r12 == 0) {
                r12 = productionMarkerArrElements[-i3];
            }
            if (r12 instanceof ErrorItem) {
                binder = WhitespacesBinders.DEFAULT_RIGHT_BINDER;
            } else {
                binder = this.myOptionalData.getBinder(r12.markerId, z2);
            }
            int lexemeIndex2 = r12.getLexemeIndex(z2);
            boolean z3 = binder instanceof WhitespacesAndCommentsBinder.RecursiveBinder;
            if (z3) {
                lexemeIndex = 0;
            } else {
                int i4 = iArrElements[i - 1];
                ProductionMarker productionMarker = productionMarkerArrElements[Math.abs(i4)];
                if (i4 >= 0) {
                    z = false;
                }
                lexemeIndex = productionMarker.getLexemeIndex(z);
            }
            int iMax = Math.max(lexemeIndex2, i2);
            while (iMax > lexemeIndex && whitespaceOrComment(this.myLexTypes[iMax - 1])) {
                iMax--;
            }
            int iShiftOverWhitespaceForward = shiftOverWhitespaceForward(lexemeIndex2);
            if (iMax != iShiftOverWhitespaceForward) {
                relativeTokenTypesView.configure(iMax, iShiftOverWhitespaceForward);
                relativeTokenTextView.configure(iMax);
                iMax += binder.getEdgePosition(relativeTokenTypesView, iMax == 0 || iShiftOverWhitespaceForward == this.myLexemeCount, relativeTokenTextView);
                r12.setLexemeIndex(iMax, z2);
                if (z3) {
                    this.myProduction.confineMarkersToMaxLexeme(i, iMax);
                }
            } else {
                if (lexemeIndex2 < iMax) {
                    r12.setLexemeIndex(iMax, z2);
                } else {
                    i2 = lexemeIndex2;
                }
                i++;
                anonymousClass1 = null;
                z = true;
            }
            i2 = iMax;
            i++;
            anonymousClass1 = null;
            z = true;
        }
    }

    private final class RelativeTokenTypesView extends AbstractList<IElementType> {
        private int mySize;
        private int myStart;

        private RelativeTokenTypesView() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void configure(int i, int i2) {
            this.myStart = i;
            this.mySize = i2 - i;
        }

        @Override // java.util.AbstractList, java.util.List
        public IElementType get(int i) {
            return PsiBuilderImpl.this.myLexTypes[this.myStart + i];
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return this.mySize;
        }
    }

    private final class RelativeTokenTextView implements WhitespacesAndCommentsBinder.TokenTextGetter {
        private int myStart;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/lang/impl/PsiBuilderImpl$RelativeTokenTextView", "get"));
        }

        private RelativeTokenTextView() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void configure(int i) {
            this.myStart = i;
        }

        @Override // org.jetbrains.kotlin.com.intellij.lang.WhitespacesAndCommentsBinder.TokenTextGetter
        public CharSequence get(int i) {
            CharSequence charSequenceSubSequence = PsiBuilderImpl.this.myText.subSequence(PsiBuilderImpl.this.myLexStarts[this.myStart + i], PsiBuilderImpl.this.myLexStarts[this.myStart + i + 1]);
            if (charSequenceSubSequence == null) {
                $$$reportNull$$$0(0);
            }
            return charSequenceSubSequence;
        }
    }

    private void checkTreeDepth(int i, boolean z, boolean z2) {
        PsiFile psiFile = this.myFile;
        if (psiFile == null) {
            return;
        }
        PsiFile originalFile = psiFile.getOriginalFile();
        Boolean bool = (Boolean) originalFile.getUserData(BlockSupport.TREE_DEPTH_LIMIT_EXCEEDED);
        if (i > BlockSupport.INCREMENTAL_REPARSE_DEPTH_LIMIT) {
            if (Boolean.TRUE.equals(bool)) {
                return;
            }
            originalFile.putUserData(BlockSupport.TREE_DEPTH_LIMIT_EXCEEDED, Boolean.TRUE);
        } else {
            if (!z || bool == null || z2) {
                return;
            }
            originalFile.putUserData(BlockSupport.TREE_DEPTH_LIMIT_EXCEEDED, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:42:0x0097 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:50:0x002c A[SYNTHETIC] */
    public void bind(StartMarker startMarker, CompositeElement compositeElement) {
        if (startMarker == null) {
            $$$reportNull$$$0(36);
        }
        if (compositeElement == null) {
            $$$reportNull$$$0(37);
        }
        ASTFactory aSTFactory = getASTFactory();
        int iInsertLeaves = startMarker.myLexemeIndex;
        ProductionMarker productionMarker = startMarker.myFirstChild != null ? startMarker.myFirstChild : startMarker;
        boolean z = startMarker.myFirstChild == null;
        StartMarker startMarker2 = startMarker;
        while (true) {
            iInsertLeaves = insertLeaves(iInsertLeaves, productionMarker.getLexemeIndex(z), compositeElement);
            if (productionMarker == startMarker && z) {
                return;
            }
            if (productionMarker instanceof StartMarker) {
                StartMarker startMarker3 = (StartMarker) productionMarker;
                if (z) {
                    startMarker2 = (StartMarker) startMarker3.myParent;
                    compositeElement = compositeElement.getTreeParent();
                    productionMarker = startMarker3.myNext;
                } else if (!this.myOptionalData.isCollapsed(startMarker3.markerId)) {
                    CompositeElement compositeElementCreateComposite = createComposite(startMarker3, aSTFactory);
                    compositeElement.rawAddChildrenWithoutNotifications(compositeElementCreateComposite);
                    ProductionMarker productionMarker2 = startMarker3.myFirstChild != null ? startMarker3.myFirstChild : startMarker3;
                    boolean z2 = startMarker3.myFirstChild == null;
                    productionMarker = productionMarker2;
                    compositeElement = compositeElementCreateComposite;
                    z = z2;
                    startMarker2 = startMarker3;
                } else {
                    iInsertLeaves = collapseLeaves(compositeElement, startMarker3);
                    productionMarker = startMarker3.myNext;
                }
            } else if (productionMarker instanceof ErrorItem) {
                compositeElement.rawAddChildrenWithoutNotifications(Factory.createErrorElement(((ErrorItem) productionMarker).myMessage));
                productionMarker = productionMarker.myNext;
            } else if (productionMarker == null) {
                z = true;
                productionMarker = startMarker2;
            }
            z = false;
            if (productionMarker == null) {
                z = true;
                productionMarker = startMarker2;
            }
        }
    }

    private int insertLeaves(int i, int i2, CompositeElement compositeElement) {
        int iMin = Math.min(i2, this.myLexemeCount);
        while (i < iMin) {
            ProgressIndicatorProvider.checkCanceled();
            int[] iArr = this.myLexStarts;
            int i3 = iArr[i];
            int i4 = i + 1;
            int i5 = iArr[i4];
            if (i3 < i5 || (this.myLexTypes[i] instanceof ILeafElementType)) {
                compositeElement.rawAddChildrenWithoutNotifications(createLeaf(this.myLexTypes[i], i3, i5));
            }
            i = i4;
        }
        return i;
    }

    private int collapseLeaves(CompositeElement compositeElement, StartMarker startMarker) {
        if (compositeElement == null) {
            $$$reportNull$$$0(38);
        }
        if (startMarker == null) {
            $$$reportNull$$$0(39);
        }
        int i = this.myLexStarts[startMarker.myLexemeIndex];
        int i2 = this.myLexStarts[startMarker.getEndIndex()];
        IElementType iElementType = startMarker.myType;
        TreeElement treeElementCreateLeaf = createLeaf(iElementType, i, i2);
        if (shouldReuseCollapsedTokens(iElementType) && startMarker.myLexemeIndex < startMarker.getEndIndex()) {
            int endIndex = startMarker.getEndIndex() - startMarker.myLexemeIndex;
            int i3 = endIndex + 1;
            int[] iArr = new int[i3];
            IElementType[] iElementTypeArr = new IElementType[i3];
            for (int i4 = startMarker.myLexemeIndex; i4 < startMarker.getEndIndex(); i4++) {
                iArr[i4 - startMarker.myLexemeIndex] = this.myLexStarts[i4] - i;
                iElementTypeArr[i4 - startMarker.myLexemeIndex] = this.myLexTypes[i4];
            }
            iArr[endIndex] = i2 - i;
            treeElementCreateLeaf.putUserData(LAZY_PARSEABLE_TOKENS, new TokenSequence(iArr, iElementTypeArr, endIndex, treeElementCreateLeaf.getChars()));
        }
        compositeElement.rawAddChildrenWithoutNotifications(treeElementCreateLeaf);
        return startMarker.getEndIndex();
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static boolean shouldReuseCollapsedTokens(IElementType iElementType) {
        return (iElementType instanceof ILazyParseableElementTypeBase) && ((ILazyParseableElementTypeBase) iElementType).reuseCollapsedTokens();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static CompositeElement createComposite(StartMarker startMarker, ASTFactory aSTFactory) {
        CompositeElement compositeElementCreateComposite;
        if (startMarker == null) {
            $$$reportNull$$$0(40);
        }
        IElementType iElementType = startMarker.myType;
        if (iElementType == TokenType.ERROR_ELEMENT) {
            String doneError = startMarker.myBuilder.myOptionalData.getDoneError(startMarker.markerId);
            Objects.requireNonNull(doneError);
            CompositeElement compositeElementCreateErrorElement = Factory.createErrorElement(doneError);
            if (compositeElementCreateErrorElement == null) {
                $$$reportNull$$$0(41);
            }
            return compositeElementCreateErrorElement;
        }
        if (iElementType == null) {
            throw new RuntimeException("Unbalanced tree. Most probably caused by unbalanced markers. Try calling setDebugMode(true) against PsiBuilder passed to identify exact location of the problem");
        }
        if (aSTFactory != null && (compositeElementCreateComposite = aSTFactory.createComposite(startMarker.getTokenType())) != null) {
            if (compositeElementCreateComposite == null) {
                $$$reportNull$$$0(42);
            }
            return compositeElementCreateComposite;
        }
        CompositeElement compositeElementComposite = ASTFactory.composite(iElementType);
        if (compositeElementComposite == null) {
            $$$reportNull$$$0(43);
        }
        return compositeElementComposite;
    }

    private static LazyParseableElement createLazy(ILazyParseableElementType iLazyParseableElementType, CharSequence charSequence, ASTFactory aSTFactory) {
        LazyParseableElement lazyParseableElementCreateLazy;
        if (iLazyParseableElementType == null) {
            $$$reportNull$$$0(44);
        }
        if (aSTFactory != null && (lazyParseableElementCreateLazy = aSTFactory.createLazy(iLazyParseableElementType, charSequence)) != null) {
            if (lazyParseableElementCreateLazy == null) {
                $$$reportNull$$$0(45);
            }
            return lazyParseableElementCreateLazy;
        }
        LazyParseableElement lazyParseableElementLazy = ASTFactory.lazy(iLazyParseableElementType, charSequence);
        if (lazyParseableElementLazy == null) {
            $$$reportNull$$$0(46);
        }
        return lazyParseableElementLazy;
    }

    public static String getErrorMessage(LighterASTNode lighterASTNode) {
        if (lighterASTNode == null) {
            $$$reportNull$$$0(47);
        }
        if (lighterASTNode instanceof ErrorItem) {
            return ((ErrorItem) lighterASTNode).myMessage;
        }
        if (!(lighterASTNode instanceof StartMarker)) {
            return null;
        }
        StartMarker startMarker = (StartMarker) lighterASTNode;
        if (startMarker.myType == TokenType.ERROR_ELEMENT) {
            return startMarker.myBuilder.myOptionalData.getDoneError(startMarker.markerId);
        }
        return null;
    }

    private static final class MyComparator implements ShallowNodeComparator<ASTNode, LighterASTNode> {
        private final TripleFunction<? super ASTNode, ? super LighterASTNode, ? super FlyweightCapableTreeStructure<LighterASTNode>, ThreeState> myCustom;
        private final List<? extends CustomLanguageASTComparator> myCustomLanguageASTComparators;
        private final MyTreeStructure myTreeStructure;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str;
            int i2;
            switch (i) {
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 16:
                case 17:
                case 18:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                case 14:
                case 15:
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
            switch (i) {
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 16:
                case 17:
                case 18:
                    i2 = 2;
                    break;
                case 14:
                case 15:
                default:
                    i2 = 3;
                    break;
            }
            Object[] objArr = new Object[i2];
            switch (i) {
                case 1:
                    objArr[0] = "treeStructure";
                    break;
                case 2:
                case 14:
                    objArr[0] = "oldNode";
                    break;
                case 3:
                case 15:
                    objArr[0] = "newNode";
                    break;
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 16:
                case 17:
                case 18:
                    objArr[0] = "org/jetbrains/kotlin/com/intellij/lang/impl/PsiBuilderImpl$MyComparator";
                    break;
                case 19:
                case 21:
                    objArr[0] = "n1";
                    break;
                case 20:
                case 22:
                    objArr[0] = "n2";
                    break;
                default:
                    objArr[0] = "customLanguageASTComparators";
                    break;
            }
            switch (i) {
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                    objArr[1] = "deepEqual";
                    break;
                case 14:
                case 15:
                default:
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/lang/impl/PsiBuilderImpl$MyComparator";
                    break;
                case 16:
                case 17:
                case 18:
                    objArr[1] = "customCompare";
                    break;
            }
            switch (i) {
                case 2:
                case 3:
                    objArr[2] = "deepEqual";
                    break;
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 16:
                case 17:
                case 18:
                    break;
                case 14:
                case 15:
                    objArr[2] = "customCompare";
                    break;
                case 19:
                case 20:
                    objArr[2] = "typesEqual";
                    break;
                case 21:
                case 22:
                    objArr[2] = "hashCodesEqual";
                    break;
                default:
                    objArr[2] = "<init>";
                    break;
            }
            String str2 = String.format(str, objArr);
            switch (i) {
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 16:
                case 17:
                case 18:
                    throw new IllegalStateException(str2);
                case 14:
                case 15:
                default:
                    throw new IllegalArgumentException(str2);
            }
        }

        private MyComparator(TripleFunction<? super ASTNode, ? super LighterASTNode, ? super FlyweightCapableTreeStructure<LighterASTNode>, ThreeState> tripleFunction, List<? extends CustomLanguageASTComparator> list, MyTreeStructure myTreeStructure) {
            if (list == null) {
                $$$reportNull$$$0(0);
            }
            if (myTreeStructure == null) {
                $$$reportNull$$$0(1);
            }
            this.myCustom = tripleFunction;
            this.myCustomLanguageASTComparators = list;
            this.myTreeStructure = myTreeStructure;
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.diff.ShallowNodeComparator
        public ThreeState deepEqual(ASTNode aSTNode, LighterASTNode lighterASTNode) {
            ThreeState threeState;
            ThreeState threeState2;
            ThreeState threeState3;
            ThreeState threeState4;
            if (aSTNode == null) {
                $$$reportNull$$$0(2);
            }
            if (lighterASTNode == null) {
                $$$reportNull$$$0(3);
            }
            ProgressIndicatorProvider.checkCanceled();
            boolean z = aSTNode instanceof PsiErrorElement;
            if (z != (lighterASTNode.getTokenType() == TokenType.ERROR_ELEMENT)) {
                ThreeState threeState5 = ThreeState.NO;
                if (threeState5 == null) {
                    $$$reportNull$$$0(4);
                }
                return threeState5;
            }
            if (z) {
                ThreeState threeState6 = Objects.equals(((PsiErrorElement) aSTNode).getErrorDescription(), PsiBuilderImpl.getErrorMessage(lighterASTNode)) ? ThreeState.UNSURE : ThreeState.NO;
                if (threeState6 == null) {
                    $$$reportNull$$$0(5);
                }
                return threeState6;
            }
            ThreeState threeStateCustomCompare = customCompare(aSTNode, lighterASTNode);
            if (threeStateCustomCompare != ThreeState.UNSURE) {
                if (threeStateCustomCompare == null) {
                    $$$reportNull$$$0(6);
                }
                return threeStateCustomCompare;
            }
            if (lighterASTNode instanceof Token) {
                IElementType tokenType = lighterASTNode.getTokenType();
                Token token = (Token) lighterASTNode;
                if (aSTNode instanceof ForeignLeafPsiElement) {
                    if ((tokenType instanceof ForeignLeafType) && ((ForeignLeafType) tokenType).getValue().equals(aSTNode.getText())) {
                        threeState4 = ThreeState.YES;
                    } else {
                        threeState4 = ThreeState.NO;
                    }
                    if (threeState4 == null) {
                        $$$reportNull$$$0(7);
                    }
                    return threeState4;
                }
                if (aSTNode instanceof LeafElement) {
                    if (tokenType instanceof ForeignLeafType) {
                        ThreeState threeState7 = ThreeState.NO;
                        if (threeState7 == null) {
                            $$$reportNull$$$0(8);
                        }
                        return threeState7;
                    }
                    if (((LeafElement) aSTNode).textMatches(token.getText())) {
                        threeState3 = ThreeState.YES;
                    } else {
                        threeState3 = ThreeState.NO;
                    }
                    if (threeState3 == null) {
                        $$$reportNull$$$0(9);
                    }
                    return threeState3;
                }
                if (tokenType instanceof ILightLazyParseableElementType) {
                    if (((TreeElement) aSTNode).textMatches(token.getText())) {
                        ThreeState threeState8 = PsiDocumentManagerBase.isFullReparseInProgress() ? ThreeState.UNSURE : ThreeState.YES;
                        if (threeState8 == null) {
                            $$$reportNull$$$0(10);
                        }
                        return threeState8;
                    }
                    if (TreeUtil.isCollapsedChameleon(aSTNode)) {
                        threeState2 = ThreeState.NO;
                    } else {
                        threeState2 = ThreeState.UNSURE;
                    }
                    if (threeState2 == null) {
                        $$$reportNull$$$0(11);
                    }
                    return threeState2;
                }
                if (((aSTNode.getElementType() instanceof ILazyParseableElementType) && (tokenType instanceof ILazyParseableElementType)) || ((aSTNode.getElementType() instanceof ICustomParsingType) && (tokenType instanceof ICustomParsingType))) {
                    if (((TreeElement) aSTNode).textMatches(token.getText())) {
                        threeState = ThreeState.YES;
                    } else {
                        threeState = ThreeState.NO;
                    }
                    if (threeState == null) {
                        $$$reportNull$$$0(12);
                    }
                    return threeState;
                }
            }
            ThreeState threeState9 = ThreeState.UNSURE;
            if (threeState9 == null) {
                $$$reportNull$$$0(13);
            }
            return threeState9;
        }

        private ThreeState customCompare(ASTNode aSTNode, LighterASTNode lighterASTNode) {
            ThreeState threeStateFun;
            if (aSTNode == null) {
                $$$reportNull$$$0(14);
            }
            if (lighterASTNode == null) {
                $$$reportNull$$$0(15);
            }
            Iterator<? extends CustomLanguageASTComparator> it = this.myCustomLanguageASTComparators.iterator();
            while (it.hasNext()) {
                ThreeState threeStateCompareAST = it.next().compareAST(aSTNode, lighterASTNode, this.myTreeStructure);
                if (threeStateCompareAST != ThreeState.UNSURE) {
                    if (threeStateCompareAST == null) {
                        $$$reportNull$$$0(16);
                    }
                    return threeStateCompareAST;
                }
            }
            TripleFunction<? super ASTNode, ? super LighterASTNode, ? super FlyweightCapableTreeStructure<LighterASTNode>, ThreeState> tripleFunction = this.myCustom;
            if (tripleFunction != null && (threeStateFun = tripleFunction.fun(aSTNode, lighterASTNode, this.myTreeStructure)) != ThreeState.UNSURE) {
                if (threeStateFun == null) {
                    $$$reportNull$$$0(17);
                }
                return threeStateFun;
            }
            ThreeState threeState = ThreeState.UNSURE;
            if (threeState == null) {
                $$$reportNull$$$0(18);
            }
            return threeState;
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.diff.ShallowNodeComparator
        public boolean typesEqual(ASTNode aSTNode, LighterASTNode lighterASTNode) {
            Object objDereferenceToken;
            IElementType iElementTypeDereferenceToken;
            if (aSTNode == null) {
                $$$reportNull$$$0(19);
            }
            if (lighterASTNode == null) {
                $$$reportNull$$$0(20);
            }
            if (aSTNode instanceof PsiWhiteSpaceImpl) {
                return PsiBuilderImpl.ourAnyLanguageWhitespaceTokens.contains(lighterASTNode.getTokenType()) || ((lighterASTNode instanceof Token) && ((Token) lighterASTNode).getBuilder().myWhitespaces.contains(lighterASTNode.getTokenType()));
            }
            if (aSTNode instanceof ForeignLeafPsiElement) {
                objDereferenceToken = ((ForeignLeafPsiElement) aSTNode).getForeignType();
                iElementTypeDereferenceToken = lighterASTNode.getTokenType();
            } else {
                objDereferenceToken = dereferenceToken(aSTNode.getElementType());
                iElementTypeDereferenceToken = dereferenceToken(lighterASTNode.getTokenType());
            }
            return Comparing.equal((IElementType) objDereferenceToken, iElementTypeDereferenceToken);
        }

        private static IElementType dereferenceToken(IElementType iElementType) {
            return iElementType instanceof TokenWrapper ? dereferenceToken(((TokenWrapper) iElementType).getDelegate()) : iElementType;
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.diff.ShallowNodeComparator
        public boolean hashCodesEqual(ASTNode aSTNode, LighterASTNode lighterASTNode) {
            if (aSTNode == null) {
                $$$reportNull$$$0(21);
            }
            if (lighterASTNode == null) {
                $$$reportNull$$$0(22);
            }
            if ((aSTNode instanceof LeafElement) && (lighterASTNode instanceof Token)) {
                boolean z = aSTNode instanceof ForeignLeafPsiElement;
                if (z != (lighterASTNode.getTokenType() instanceof ForeignLeafType)) {
                    return false;
                }
                if (z) {
                    return aSTNode.getText().equals(((ForeignLeafType) lighterASTNode.getTokenType()).getValue());
                }
                return ((LeafElement) aSTNode).textMatches(((Token) lighterASTNode).getText());
            }
            if ((aSTNode instanceof PsiErrorElement) && lighterASTNode.getTokenType() == TokenType.ERROR_ELEMENT && !Objects.equals(((PsiErrorElement) aSTNode).getErrorDescription(), PsiBuilderImpl.getErrorMessage(lighterASTNode))) {
                return false;
            }
            return ((Node) lighterASTNode).tokenTextMatches(aSTNode.getChars());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class MyTreeStructure implements FlyweightCapableTreeStructure<LighterASTNode> {
        private int count;
        private final LimitedPool<SingleLexemeNode> myLexemePool;
        private final LimitedPool<TokenRangeNode> myRangePool;
        private final StartMarker myRoot;
        private LighterASTNode[] nodes;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = (i == 1 || i == 8) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
            Object[] objArr = new Object[(i == 1 || i == 8) ? 2 : 3];
            switch (i) {
                case 1:
                case 8:
                    objArr[0] = "org/jetbrains/kotlin/com/intellij/lang/impl/PsiBuilderImpl$MyTreeStructure";
                    break;
                case 2:
                case 7:
                case 9:
                case 10:
                    objArr[0] = "node";
                    break;
                case 3:
                    objArr[0] = "item";
                    break;
                case 4:
                    objArr[0] = "into";
                    break;
                case 5:
                    objArr[0] = ModuleXmlParser.TYPE;
                    break;
                case 6:
                    objArr[0] = "builder";
                    break;
                default:
                    objArr[0] = "root";
                    break;
            }
            if (i == 1) {
                objArr[1] = "getRoot";
            } else if (i != 8) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/lang/impl/PsiBuilderImpl$MyTreeStructure";
            } else {
                objArr[1] = "toString";
            }
            switch (i) {
                case 1:
                case 8:
                    break;
                case 2:
                    objArr[2] = "getParent";
                    break;
                case 3:
                case 4:
                    objArr[2] = "getChildren";
                    break;
                case 5:
                case 6:
                    objArr[2] = "insertLeaf";
                    break;
                case 7:
                    objArr[2] = "toString";
                    break;
                case 9:
                    objArr[2] = "getStartOffset";
                    break;
                case 10:
                    objArr[2] = "getEndOffset";
                    break;
                default:
                    objArr[2] = "<init>";
                    break;
            }
            String str2 = String.format(str, objArr);
            if (i != 1 && i != 8) {
                throw new IllegalArgumentException(str2);
            }
            throw new IllegalStateException(str2);
        }

        MyTreeStructure(StartMarker startMarker, MyTreeStructure myTreeStructure) {
            if (startMarker == null) {
                $$$reportNull$$$0(0);
            }
            if (myTreeStructure == null) {
                this.myRangePool = new LimitedPool<>(1000, new LimitedPool.ObjectFactory<TokenRangeNode>() { // from class: org.jetbrains.kotlin.com.intellij.lang.impl.PsiBuilderImpl.MyTreeStructure.1
                    private static /* synthetic */ void $$$reportNull$$$0(int i) {
                        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "token", "org/jetbrains/kotlin/com/intellij/lang/impl/PsiBuilderImpl$MyTreeStructure$1", "cleanup"));
                    }

                    @Override // org.jetbrains.kotlin.com.intellij.util.containers.LimitedPool.ObjectFactory
                    public void cleanup(TokenRangeNode tokenRangeNode) {
                        if (tokenRangeNode == null) {
                            $$$reportNull$$$0(0);
                        }
                        tokenRangeNode.clean();
                    }

                    @Override // org.jetbrains.kotlin.com.intellij.util.containers.LimitedPool.ObjectFactory
                    public TokenRangeNode create() {
                        return new TokenRangeNode();
                    }
                });
                this.myLexemePool = new LimitedPool<>(1000, new LimitedPool.ObjectFactory<SingleLexemeNode>() { // from class: org.jetbrains.kotlin.com.intellij.lang.impl.PsiBuilderImpl.MyTreeStructure.2
                    private static /* synthetic */ void $$$reportNull$$$0(int i) {
                        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "node", "org/jetbrains/kotlin/com/intellij/lang/impl/PsiBuilderImpl$MyTreeStructure$2", "cleanup"));
                    }

                    @Override // org.jetbrains.kotlin.com.intellij.util.containers.LimitedPool.ObjectFactory
                    public SingleLexemeNode create() {
                        return new SingleLexemeNode();
                    }

                    @Override // org.jetbrains.kotlin.com.intellij.util.containers.LimitedPool.ObjectFactory
                    public void cleanup(SingleLexemeNode singleLexemeNode) {
                        if (singleLexemeNode == null) {
                            $$$reportNull$$$0(0);
                        }
                        singleLexemeNode.clean();
                    }
                });
            } else {
                this.myRangePool = myTreeStructure.myRangePool;
                this.myLexemePool = myTreeStructure.myLexemePool;
            }
            this.myRoot = startMarker;
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.diff.FlyweightCapableTreeStructure
        public LighterASTNode getRoot() {
            StartMarker startMarker = this.myRoot;
            if (startMarker == null) {
                $$$reportNull$$$0(1);
            }
            return startMarker;
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.diff.FlyweightCapableTreeStructure
        public LighterASTNode getParent(LighterASTNode lighterASTNode) {
            if (lighterASTNode == null) {
                $$$reportNull$$$0(2);
            }
            if (lighterASTNode instanceof ProductionMarker) {
                return ((ProductionMarker) lighterASTNode).myParent;
            }
            if (lighterASTNode instanceof Token) {
                return ((Token) lighterASTNode).myParentNode;
            }
            throw new UnsupportedOperationException("Unknown node type: " + lighterASTNode);
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.diff.FlyweightCapableTreeStructure
        public int getChildren(LighterASTNode lighterASTNode, Ref<LighterASTNode[]> ref) {
            if (lighterASTNode == null) {
                $$$reportNull$$$0(3);
            }
            if (ref == null) {
                $$$reportNull$$$0(4);
            }
            if (lighterASTNode instanceof LazyParseableToken) {
                FlyweightCapableTreeStructure<LighterASTNode> contents = ((LazyParseableToken) lighterASTNode).parseContents();
                LighterASTNode root = contents.getRoot();
                if (root instanceof ProductionMarker) {
                    ((ProductionMarker) root).myParent = ((Token) lighterASTNode).myParentNode;
                }
                return contents.getChildren(root, ref);
            }
            if ((lighterASTNode instanceof Token) || (lighterASTNode instanceof ErrorItem)) {
                return 0;
            }
            StartMarker startMarker = (StartMarker) lighterASTNode;
            this.count = 0;
            int iInsertLeaves = startMarker.myLexemeIndex;
            for (ProductionMarker productionMarker = startMarker.myFirstChild; productionMarker != null; productionMarker = productionMarker.myNext) {
                iInsertLeaves = insertLeaves(iInsertLeaves, productionMarker.myLexemeIndex, startMarker.myBuilder, startMarker);
                boolean z = productionMarker instanceof StartMarker;
                if (z && productionMarker.myBuilder.myOptionalData.isCollapsed(productionMarker.markerId)) {
                    insertLeaf(productionMarker.getTokenType(), startMarker.myBuilder, productionMarker.myLexemeIndex, productionMarker.getEndIndex(), true, startMarker);
                } else {
                    ensureCapacity();
                    LighterASTNode[] lighterASTNodeArr = this.nodes;
                    int i = this.count;
                    this.count = i + 1;
                    lighterASTNodeArr[i] = productionMarker;
                }
                if (z) {
                    iInsertLeaves = productionMarker.getEndIndex();
                }
            }
            insertLeaves(iInsertLeaves, startMarker.getEndIndex(), startMarker.myBuilder, startMarker);
            LighterASTNode[] lighterASTNodeArr2 = this.nodes;
            if (lighterASTNodeArr2 == null) {
                lighterASTNodeArr2 = LighterASTNode.EMPTY_ARRAY;
            }
            ref.set(lighterASTNodeArr2);
            this.nodes = null;
            return this.count;
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.diff.FlyweightCapableTreeStructure
        public void disposeChildren(LighterASTNode[] lighterASTNodeArr, int i) {
            if (lighterASTNodeArr == null) {
                return;
            }
            for (int i2 = 0; i2 < i; i2++) {
                LighterASTNode lighterASTNode = lighterASTNodeArr[i2];
                if (lighterASTNode instanceof TokenRangeNode) {
                    this.myRangePool.recycle((TokenRangeNode) lighterASTNode);
                } else if (lighterASTNode instanceof SingleLexemeNode) {
                    this.myLexemePool.recycle((SingleLexemeNode) lighterASTNode);
                }
            }
        }

        private void ensureCapacity() {
            LighterASTNode[] lighterASTNodeArr = this.nodes;
            if (lighterASTNodeArr == null) {
                this.nodes = new LighterASTNode[10];
                return;
            }
            int i = this.count;
            if (i >= lighterASTNodeArr.length) {
                this.nodes = (LighterASTNode[]) Arrays.copyOf(lighterASTNodeArr, (i * 3) / 2);
            }
        }

        private int insertLeaves(int i, int i2, PsiBuilderImpl psiBuilderImpl, StartMarker startMarker) {
            int iMin = Math.min(i2, psiBuilderImpl.myLexemeCount);
            while (true) {
                int i3 = i;
                if (i3 >= iMin) {
                    return i3;
                }
                i = i3 + 1;
                insertLeaf(psiBuilderImpl.myLexTypes[i3], psiBuilderImpl, i3, i, false, startMarker);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        private void insertLeaf(IElementType iElementType, PsiBuilderImpl psiBuilderImpl, int i, int i2, boolean z, StartMarker startMarker) {
            LighterASTNode lighterASTNode;
            LazyParseableToken lazyParseableToken;
            if (iElementType == null) {
                $$$reportNull$$$0(5);
            }
            if (psiBuilderImpl == null) {
                $$$reportNull$$$0(6);
            }
            int i3 = psiBuilderImpl.myLexStarts[i];
            int i4 = psiBuilderImpl.myLexStarts[i2];
            if (i3 <= i4) {
                if (z || i3 != i4 || (iElementType instanceof ILeafElementType)) {
                    if (iElementType instanceof ILightLazyParseableElementType) {
                        int i5 = psiBuilderImpl.myOffset + i3;
                        lazyParseableToken = (LazyParseableToken) psiBuilderImpl.myChameleonCache.get(i5);
                        if (lazyParseableToken == null) {
                            LazyParseableToken lazyParseableToken2 = new LazyParseableToken(this, i, i2);
                            lazyParseableToken2.initToken(iElementType, startMarker, i3, i4);
                            psiBuilderImpl.myChameleonCache.put(i5, lazyParseableToken2);
                            lighterASTNode = lazyParseableToken2;
                        } else if (lazyParseableToken.getBuilder() != psiBuilderImpl || lazyParseableToken.myStartIndex != i || lazyParseableToken.myEndIndex != i2) {
                            lighterASTNode = lazyParseableToken;
                            throw new AssertionError("Wrong chameleon cached");
                        }
                    } else if (i == i2 - 1 && iElementType == psiBuilderImpl.myLexTypes[i]) {
                        SingleLexemeNode singleLexemeNodeAlloc = this.myLexemePool.alloc();
                        singleLexemeNodeAlloc.myParentNode = startMarker;
                        singleLexemeNodeAlloc.myLexemeIndex = i;
                        lighterASTNode = singleLexemeNodeAlloc;
                    } else {
                        TokenRangeNode tokenRangeNodeAlloc = this.myRangePool.alloc();
                        tokenRangeNodeAlloc.initToken(iElementType, startMarker, i3, i4);
                        lighterASTNode = tokenRangeNodeAlloc;
                    }
                    lighterASTNode = lazyParseableToken;
                    ensureCapacity();
                    LighterASTNode[] lighterASTNodeArr = this.nodes;
                    int i6 = this.count;
                    this.count = i6 + 1;
                    lighterASTNodeArr[i6] = lighterASTNode;
                }
            }
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.diff.FlyweightCapableTreeStructure
        public CharSequence toString(LighterASTNode lighterASTNode) {
            if (lighterASTNode == null) {
                $$$reportNull$$$0(7);
            }
            CharSequence charSequenceSubSequence = this.myRoot.myBuilder.myText.subSequence(lighterASTNode.getStartOffset(), lighterASTNode.getEndOffset());
            if (charSequenceSubSequence == null) {
                $$$reportNull$$$0(8);
            }
            return charSequenceSubSequence;
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.diff.FlyweightCapableTreeStructure
        public int getStartOffset(LighterASTNode lighterASTNode) {
            if (lighterASTNode == null) {
                $$$reportNull$$$0(9);
            }
            return lighterASTNode.getStartOffset();
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.diff.FlyweightCapableTreeStructure
        public int getEndOffset(LighterASTNode lighterASTNode) {
            if (lighterASTNode == null) {
                $$$reportNull$$$0(10);
            }
            return lighterASTNode.getEndOffset();
        }
    }

    private static final class ASTConverter implements Function<Node, ASTNode> {
        private final ASTFactory myASTFactory;
        private final StartMarker myRoot;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "root", "org/jetbrains/kotlin/com/intellij/lang/impl/PsiBuilderImpl$ASTConverter", "<init>"));
        }

        private ASTConverter(StartMarker startMarker, ASTFactory aSTFactory) {
            if (startMarker == null) {
                $$$reportNull$$$0(0);
            }
            this.myRoot = startMarker;
            this.myASTFactory = aSTFactory;
        }

        @Override // java.util.function.Function
        public ASTNode apply(Node node) {
            if (node instanceof Token) {
                Token token = (Token) node;
                return token.getBuilder().createLeaf(token.getTokenType(), token.getStartOffsetInBuilder(), token.getEndOffsetInBuilder());
            }
            if (node instanceof ErrorItem) {
                return Factory.createErrorElement(((ErrorItem) node).myMessage);
            }
            StartMarker startMarker = (StartMarker) node;
            StartMarker startMarker2 = this.myRoot;
            CompositeElement compositeElementCreateComposite = node == startMarker2 ? (CompositeElement) startMarker2.myBuilder.createRootAST(this.myRoot) : PsiBuilderImpl.createComposite(startMarker, this.myASTFactory);
            startMarker.myBuilder.bind(startMarker, compositeElementCreateComposite);
            return compositeElementCreateComposite;
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.SyntaxTreeBuilder
    public void setDebugMode(boolean z) {
        this.myDebugMode = z;
    }

    public int getLexemeCount() {
        return this.myLexemeCount;
    }

    public Lexer getLexer() {
        Lexer lexer = this.myLexer;
        if (lexer == null) {
            $$$reportNull$$$0(48);
        }
        return lexer;
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected TreeElement createLeaf(IElementType iElementType, int i, int i2) {
        LeafElement leafElementCreateLeaf;
        if (iElementType == 0) {
            $$$reportNull$$$0(49);
        }
        CharSequence charSequenceIntern = this.myCharTable.intern(this.myText, i, i2);
        if (this.myWhitespaces.contains(iElementType)) {
            return new PsiWhiteSpaceImpl(charSequenceIntern);
        }
        if (iElementType instanceof ICustomParsingType) {
            TreeElement treeElement = (TreeElement) ((ICustomParsingType) iElementType).parse(charSequenceIntern, this.myCharTable);
            if (treeElement == null) {
                $$$reportNull$$$0(50);
            }
            return treeElement;
        }
        ASTFactory aSTFactory = getASTFactory();
        if (iElementType instanceof ILazyParseableElementType) {
            return createLazy((ILazyParseableElementType) iElementType, charSequenceIntern, aSTFactory);
        }
        if (aSTFactory != null && (leafElementCreateLeaf = aSTFactory.createLeaf(iElementType, charSequenceIntern)) != null) {
            if (leafElementCreateLeaf == null) {
                $$$reportNull$$$0(51);
            }
            return leafElementCreateLeaf;
        }
        LeafElement leafElementLeaf = ASTFactory.leaf(iElementType, charSequenceIntern);
        if (leafElementLeaf == null) {
            $$$reportNull$$$0(52);
        }
        return leafElementLeaf;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.UnprotectedUserDataHolder, org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolder
    public <T> T getUserData(Key<T> key) {
        if (key == null) {
            $$$reportNull$$$0(53);
        }
        return key == FileContextUtil.CONTAINING_FILE_KEY ? (T) this.myFile : (T) super.getUserData(key);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.UnprotectedUserDataHolder, org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolder
    public <T> void putUserData(Key<T> key, T t) {
        if (key == null) {
            $$$reportNull$$$0(54);
        }
        if (key == FileContextUtil.CONTAINING_FILE_KEY) {
            this.myFile = (PsiFile) t;
        } else {
            super.putUserData(key, t);
        }
    }
}
