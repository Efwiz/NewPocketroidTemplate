package org.jetbrains.kotlin.fir.lightTree.converter;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.ElementTypeUtils;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtLightSourceElement;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.KtRealSourceElementKind;
import org.jetbrains.kotlin.KtSourceElementKind;
import org.jetbrains.kotlin.cli.common.arguments.PreprocessCommandLineArgumentsKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.util.Ref;
import org.jetbrains.kotlin.com.intellij.psi.TokenType;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.util.diff.FlyweightCapableTreeStructure;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.builder.AbstractRawFirBuilder;
import org.jetbrains.kotlin.fir.builder.Context;
import org.jetbrains.kotlin.fir.builder.ConversionUtilsKt;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitTypeRefImplWithoutSource;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.lexer.KtModifierKeywordToken;
import org.jetbrains.kotlin.lexer.KtToken;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: AbstractLightTreeRawFirBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0080\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B+\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00020\u0006\u0012\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00020\b¢\u0006\u0002\u0010\tJ=\u0010(\u001a\u00020)*\u00020\u00022\u0012\u0010*\u001a\n\u0012\u0006\b\u0001\u0012\u00020,0+\"\u00020,2\u0012\u0010-\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020)0.H\u0084\bø\u0001\u0000¢\u0006\u0002\u0010/J<\u00100\u001a\b\u0012\u0004\u0012\u0002H10\u001c\"\u0004\b\u0000\u00101*\u00020\u00022\u001e\u0010-\u001a\u001a\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u0002H103\u0012\u0004\u0012\u00020)02H\u0084\bø\u0001\u0000J\u000e\u00104\u001a\u0004\u0018\u00010\u0002*\u00020\u0002H\u0016J\u0016\u00105\u001a\u0004\u0018\u00010\u0002*\u00020\u00022\u0006\u00106\u001a\u00020\u0018H\u0016J\u001a\u00107\u001a\b\u0012\u0004\u0012\u00020\u00020\u001c*\u0004\u0018\u00010\u00022\u0006\u00106\u001a\u00020\u0018J\u001b\u00108\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00020+*\u0004\u0018\u00010\u0002¢\u0006\u0002\u00109J\u000e\u0010:\u001a\u0004\u0018\u00010\u0002*\u00020\u0002H\u0016J\u000e\u0010;\u001a\u0004\u0018\u00010\u0002*\u0004\u0018\u00010\u0002J\u000e\u0010<\u001a\u0004\u0018\u00010\u0002*\u00020\u0002H\u0002J\u000e\u0010=\u001a\u0004\u0018\u00010\u0002*\u00020\u0002H\u0004J\u000e\u0010>\u001a\u0004\u0018\u00010\u0014*\u00020\u0002H\u0016J\u000e\u0010?\u001a\u0004\u0018\u00010\u0002*\u00020\u0002H\u0016J\u000e\u0010@\u001a\u0004\u0018\u00010\u0002*\u00020\u0002H\u0002J\f\u0010A\u001a\u0004\u0018\u00010\u0002*\u00020\u0002J\f\u0010B\u001a\u00020C*\u00020\u0002H\u0016J\u0016\u0010D\u001a\u00020E*\u00020\u00022\b\u0010F\u001a\u0004\u0018\u00010GH\u0016R\u0014\u0010\n\u001a\u00020\u000bX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u001c\u0010\u0010\u001a\u0004\u0018\u00010\u0002*\u0004\u0018\u00010\u00028VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0018\u0010\u0013\u001a\u00020\u0014*\u00020\u00028VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u0018\u0010\u0017\u001a\u00020\u0018*\u00020\u00028VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001aR\"\u0010\u001b\u001a\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010\u001c*\u0004\u0018\u00010\u00028VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001eR\u0018\u0010\u001f\u001a\u00020 *\u00020\u00028VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010!R\u001c\u0010\"\u001a\u0004\u0018\u00010\u0002*\u0004\u0018\u00010\u00028VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b#\u0010\u0012R\u001c\u0010$\u001a\u0004\u0018\u00010\u0002*\u0004\u0018\u00010\u00028VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b%\u0010\u0012R\u0018\u0010&\u001a\u00020\u0014*\u00020\u00028VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b'\u0010\u0016\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006H"}, d2 = {"Lorg/jetbrains/kotlin/fir/lightTree/converter/AbstractLightTreeRawFirBuilder;", "Lorg/jetbrains/kotlin/fir/builder/AbstractRawFirBuilder;", "Lorg/jetbrains/kotlin/com/intellij/lang/LighterASTNode;", "baseSession", "Lorg/jetbrains/kotlin/fir/FirSession;", Constants.TYPE_TREE, "Lorg/jetbrains/kotlin/com/intellij/util/diff/FlyweightCapableTreeStructure;", "context", "Lorg/jetbrains/kotlin/fir/builder/Context;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lcom/intellij/util/diff/FlyweightCapableTreeStructure;Lorg/jetbrains/kotlin/fir/builder/Context;)V", "implicitType", "Lorg/jetbrains/kotlin/fir/types/impl/FirImplicitTypeRefImplWithoutSource;", "getImplicitType", "()Lorg/jetbrains/kotlin/fir/types/impl/FirImplicitTypeRefImplWithoutSource;", "getTree", "()Lcom/intellij/util/diff/FlyweightCapableTreeStructure;", "arrayExpression", "getArrayExpression", "(Lcom/intellij/lang/LighterASTNode;)Lcom/intellij/lang/LighterASTNode;", "asText", "", "getAsText", "(Lcom/intellij/lang/LighterASTNode;)Ljava/lang/String;", "elementType", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/IElementType;", "getElementType", "(Lcom/intellij/lang/LighterASTNode;)Lcom/intellij/psi/tree/IElementType;", "indexExpressions", "", "getIndexExpressions", "(Lcom/intellij/lang/LighterASTNode;)Ljava/util/List;", "isVararg", "", "(Lcom/intellij/lang/LighterASTNode;)Z", "receiverExpression", "getReceiverExpression", "selectorExpression", "getSelectorExpression", "unescapedValue", "getUnescapedValue", "forEachChildren", "", "skipTokens", "", "Lorg/jetbrains/kotlin/lexer/KtToken;", "f", "Lkotlin/Function1;", "(Lcom/intellij/lang/LighterASTNode;[Lorg/jetbrains/kotlin/lexer/KtToken;Lkotlin/jvm/functions/Function1;)V", "forEachChildrenReturnList", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlin/Function2;", "", "getAnnotatedExpression", "getChildNodeByType", ModuleXmlParser.TYPE, "getChildNodesByType", "getChildrenAsArray", "(Lcom/intellij/lang/LighterASTNode;)[Lcom/intellij/lang/LighterASTNode;", "getExpressionInParentheses", "getFirstChild", "getFirstChildExpression", "getFirstChildExpressionUnwrapped", "getLabelName", "getLabeledExpression", "getLastChildExpression", "getParent", "getReferencedNameAsName", "Lorg/jetbrains/kotlin/name/Name;", "toFirSourceElement", "Lorg/jetbrains/kotlin/KtLightSourceElement;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "light-tree2fir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractLightTreeRawFirBuilder extends AbstractRawFirBuilder<LighterASTNode> {
    private final FirImplicitTypeRefImplWithoutSource implicitType;
    private final FlyweightCapableTreeStructure<LighterASTNode> tree;

    public final FlyweightCapableTreeStructure<LighterASTNode> getTree() {
        return this.tree;
    }

    public /* synthetic */ AbstractLightTreeRawFirBuilder(FirSession firSession, FlyweightCapableTreeStructure flyweightCapableTreeStructure, Context context, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(firSession, flyweightCapableTreeStructure, (i & 4) != 0 ? new Context() : context);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AbstractLightTreeRawFirBuilder(FirSession baseSession, FlyweightCapableTreeStructure<LighterASTNode> tree, Context<LighterASTNode> context) {
        super(baseSession, context);
        Intrinsics.checkNotNullParameter(baseSession, "baseSession");
        Intrinsics.checkNotNullParameter(tree, "tree");
        Intrinsics.checkNotNullParameter(context, "context");
        this.tree = tree;
        this.implicitType = FirImplicitTypeRefImplWithoutSource.INSTANCE;
    }

    protected final FirImplicitTypeRefImplWithoutSource getImplicitType() {
        return this.implicitType;
    }

    @Override // org.jetbrains.kotlin.fir.builder.AbstractRawFirBuilder
    public KtLightSourceElement toFirSourceElement(LighterASTNode lighterASTNode, KtFakeSourceElementKind ktFakeSourceElementKind) {
        KtSourceElementKind forcedElementSourceKind;
        Intrinsics.checkNotNullParameter(lighterASTNode, "<this>");
        int startOffset = this.tree.getStartOffset(lighterASTNode);
        int endOffset = this.tree.getEndOffset(lighterASTNode);
        FlyweightCapableTreeStructure<LighterASTNode> flyweightCapableTreeStructure = this.tree;
        KtSourceElementKind ktSourceElementKind = ktFakeSourceElementKind;
        if (ktFakeSourceElementKind == null) {
            forcedElementSourceKind = getContext().getForcedElementSourceKind();
            if (forcedElementSourceKind == null) {
                ktSourceElementKind = KtRealSourceElementKind.INSTANCE;
                forcedElementSourceKind = ktSourceElementKind;
            }
        } else {
            forcedElementSourceKind = ktSourceElementKind;
        }
        return new KtLightSourceElement(lighterASTNode, startOffset, endOffset, flyweightCapableTreeStructure, forcedElementSourceKind);
    }

    @Override // org.jetbrains.kotlin.fir.builder.AbstractRawFirBuilder
    public IElementType getElementType(LighterASTNode lighterASTNode) {
        Intrinsics.checkNotNullParameter(lighterASTNode, "<this>");
        IElementType tokenType = lighterASTNode.getTokenType();
        Intrinsics.checkNotNullExpressionValue(tokenType, "getTokenType(...)");
        return tokenType;
    }

    @Override // org.jetbrains.kotlin.fir.builder.AbstractRawFirBuilder
    public String getAsText(LighterASTNode lighterASTNode) {
        Intrinsics.checkNotNullParameter(lighterASTNode, "<this>");
        return lighterASTNode.toString();
    }

    @Override // org.jetbrains.kotlin.fir.builder.AbstractRawFirBuilder
    public String getUnescapedValue(LighterASTNode lighterASTNode) {
        String string;
        Intrinsics.checkNotNullParameter(lighterASTNode, "<this>");
        String asText = getAsText(lighterASTNode);
        Character value = ConversionUtilsKt.escapedStringToCharacter(asText).getValue();
        return (value == null || (string = value.toString()) == null) ? StringsKt.replace$default(StringsKt.replace$default(asText, "\\", "", false, 4, (Object) null), "u", "\\u", false, 4, (Object) null) : string;
    }

    @Override // org.jetbrains.kotlin.fir.builder.AbstractRawFirBuilder
    public Name getReferencedNameAsName(LighterASTNode lighterASTNode) {
        Intrinsics.checkNotNullParameter(lighterASTNode, "<this>");
        return ConverterUtilKt.nameAsSafeName$default(getAsText(lighterASTNode), null, 1, null);
    }

    @Override // org.jetbrains.kotlin.fir.builder.AbstractRawFirBuilder
    public String getLabelName(LighterASTNode lighterASTNode) {
        Intrinsics.checkNotNullParameter(lighterASTNode, "<this>");
        if (Intrinsics.areEqual(lighterASTNode.getTokenType(), KtNodeTypes.FUN)) {
            LighterASTNode parent = getParent(lighterASTNode);
            if (parent != null) {
                return getLabelName(parent);
            }
            return null;
        }
        KtToken[] ktTokenArr = new KtToken[0];
        for (LighterASTNode lighterASTNode2 : getChildrenAsArray(lighterASTNode)) {
            if (lighterASTNode2 == null) {
                break;
            }
            IElementType tokenType = lighterASTNode2.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr, tokenType) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT) && Intrinsics.areEqual(lighterASTNode2.getTokenType(), KtNodeTypes.LABEL_QUALIFIER)) {
                return StringsKt.replaceFirst$default(getAsText(lighterASTNode2), PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT, "", false, 4, (Object) null);
            }
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.builder.AbstractRawFirBuilder
    public LighterASTNode getExpressionInParentheses(LighterASTNode lighterASTNode) {
        Intrinsics.checkNotNullParameter(lighterASTNode, "<this>");
        return getFirstChildExpression(lighterASTNode);
    }

    @Override // org.jetbrains.kotlin.fir.builder.AbstractRawFirBuilder
    public LighterASTNode getAnnotatedExpression(LighterASTNode lighterASTNode) {
        Intrinsics.checkNotNullParameter(lighterASTNode, "<this>");
        return getFirstChildExpression(lighterASTNode);
    }

    @Override // org.jetbrains.kotlin.fir.builder.AbstractRawFirBuilder
    public LighterASTNode getLabeledExpression(LighterASTNode lighterASTNode) {
        Intrinsics.checkNotNullParameter(lighterASTNode, "<this>");
        return getLastChildExpression(lighterASTNode);
    }

    private final LighterASTNode getFirstChildExpression(LighterASTNode lighterASTNode) {
        KtToken[] ktTokenArr = new KtToken[0];
        for (LighterASTNode lighterASTNode2 : getChildrenAsArray(lighterASTNode)) {
            if (lighterASTNode2 == null) {
                return null;
            }
            IElementType tokenType = lighterASTNode2.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr, tokenType) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT) && ElementTypeUtils.INSTANCE.isExpression(lighterASTNode2)) {
                return lighterASTNode2;
            }
        }
        return null;
    }

    protected final LighterASTNode getFirstChildExpressionUnwrapped(LighterASTNode lighterASTNode) {
        Intrinsics.checkNotNullParameter(lighterASTNode, "<this>");
        LighterASTNode firstChildExpression = getFirstChildExpression(lighterASTNode);
        if (firstChildExpression == null) {
            return null;
        }
        return Intrinsics.areEqual(firstChildExpression.getTokenType(), KtNodeTypes.PARENTHESIZED) ? getFirstChildExpressionUnwrapped(firstChildExpression) : firstChildExpression;
    }

    private final LighterASTNode getLastChildExpression(LighterASTNode lighterASTNode) {
        KtToken[] ktTokenArr = new KtToken[0];
        LighterASTNode lighterASTNode2 = null;
        for (LighterASTNode lighterASTNode3 : getChildrenAsArray(lighterASTNode)) {
            if (lighterASTNode3 == null) {
                break;
            }
            IElementType tokenType = lighterASTNode3.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr, tokenType) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT) && ElementTypeUtils.INSTANCE.isExpression(lighterASTNode3)) {
                lighterASTNode2 = lighterASTNode3;
            }
        }
        return lighterASTNode2;
    }

    @Override // org.jetbrains.kotlin.fir.builder.AbstractRawFirBuilder
    public LighterASTNode getChildNodeByType(LighterASTNode lighterASTNode, IElementType type) {
        Intrinsics.checkNotNullParameter(lighterASTNode, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        LighterASTNode[] childrenAsArray = getChildrenAsArray(lighterASTNode);
        int length = childrenAsArray.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                return null;
            }
            LighterASTNode lighterASTNode2 = childrenAsArray[i];
            if (Intrinsics.areEqual(lighterASTNode2 != null ? lighterASTNode2.getTokenType() : null, type)) {
                return lighterASTNode2;
            }
            i++;
        }
    }

    @Override // org.jetbrains.kotlin.fir.builder.AbstractRawFirBuilder
    public LighterASTNode getReceiverExpression(LighterASTNode lighterASTNode) {
        if (lighterASTNode != null) {
            KtToken[] ktTokenArr = new KtToken[0];
            LighterASTNode lighterASTNode2 = null;
            for (LighterASTNode lighterASTNode3 : getChildrenAsArray(lighterASTNode)) {
                if (lighterASTNode3 == null) {
                    break;
                }
                IElementType tokenType = lighterASTNode3.getTokenType();
                if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr, tokenType) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT)) {
                    IElementType tokenType2 = lighterASTNode3.getTokenType();
                    if (Intrinsics.areEqual(tokenType2, KtTokens.DOT) ? true : Intrinsics.areEqual(tokenType2, KtTokens.SAFE_ACCESS)) {
                        if (Intrinsics.areEqual(lighterASTNode2 != null ? getElementType(lighterASTNode2) : null, TokenType.ERROR_ELEMENT)) {
                            return null;
                        }
                        return lighterASTNode2;
                    }
                    lighterASTNode2 = lighterASTNode3;
                }
            }
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.builder.AbstractRawFirBuilder
    public LighterASTNode getSelectorExpression(LighterASTNode lighterASTNode) {
        if (lighterASTNode != null) {
            KtToken[] ktTokenArr = new KtToken[0];
            boolean z = false;
            for (LighterASTNode lighterASTNode2 : getChildrenAsArray(lighterASTNode)) {
                if (lighterASTNode2 == null) {
                    break;
                }
                IElementType tokenType = lighterASTNode2.getTokenType();
                if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr, tokenType) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT)) {
                    IElementType tokenType2 = lighterASTNode2.getTokenType();
                    if (Intrinsics.areEqual(tokenType2, KtTokens.DOT) ? true : Intrinsics.areEqual(tokenType2, KtTokens.SAFE_ACCESS)) {
                        z = true;
                    } else if (z) {
                        if (Intrinsics.areEqual(getElementType(lighterASTNode2), TokenType.ERROR_ELEMENT)) {
                            return null;
                        }
                        return lighterASTNode2;
                    }
                }
            }
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.builder.AbstractRawFirBuilder
    public LighterASTNode getArrayExpression(LighterASTNode lighterASTNode) {
        if (lighterASTNode != null) {
            return getFirstChildExpression(lighterASTNode);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.builder.AbstractRawFirBuilder
    public List<LighterASTNode> getIndexExpressions(LighterASTNode lighterASTNode) {
        LighterASTNode lastChildExpression;
        LighterASTNode[] childrenAsArray;
        List listFilterNotNull;
        if (lighterASTNode == null || (lastChildExpression = getLastChildExpression(lighterASTNode)) == null || (childrenAsArray = getChildrenAsArray(lastChildExpression)) == null || (listFilterNotNull = ArraysKt.filterNotNull(childrenAsArray)) == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (Object obj : listFilterNotNull) {
            if (ElementTypeUtils.INSTANCE.isExpression((LighterASTNode) obj)) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.fir.builder.AbstractRawFirBuilder
    public boolean isVararg(LighterASTNode lighterASTNode) {
        LighterASTNode childNodeByType;
        Intrinsics.checkNotNullParameter(lighterASTNode, "<this>");
        IElementType MODIFIER_LIST = KtNodeTypes.MODIFIER_LIST;
        Intrinsics.checkNotNullExpressionValue(MODIFIER_LIST, "MODIFIER_LIST");
        LighterASTNode childNodeByType2 = getChildNodeByType(lighterASTNode, MODIFIER_LIST);
        if (childNodeByType2 != null) {
            KtModifierKeywordToken VARARG_KEYWORD = KtTokens.VARARG_KEYWORD;
            Intrinsics.checkNotNullExpressionValue(VARARG_KEYWORD, "VARARG_KEYWORD");
            childNodeByType = getChildNodeByType(childNodeByType2, (IElementType) VARARG_KEYWORD);
        } else {
            childNodeByType = null;
        }
        return childNodeByType != null;
    }

    public final LighterASTNode getParent(LighterASTNode lighterASTNode) {
        Intrinsics.checkNotNullParameter(lighterASTNode, "<this>");
        return this.tree.getParent(lighterASTNode);
    }

    public final LighterASTNode[] getChildrenAsArray(LighterASTNode lighterASTNode) {
        if (lighterASTNode == null) {
            return new LighterASTNode[0];
        }
        Ref<LighterASTNode[]> ref = new Ref<>();
        this.tree.getChildren(lighterASTNode, ref);
        LighterASTNode[] lighterASTNodeArr = ref.get();
        Intrinsics.checkNotNullExpressionValue(lighterASTNodeArr, "get(...)");
        return lighterASTNodeArr;
    }

    public final LighterASTNode getFirstChild(LighterASTNode lighterASTNode) {
        return (LighterASTNode) ArraysKt.firstOrNull(getChildrenAsArray(lighterASTNode));
    }

    protected final void forEachChildren(LighterASTNode lighterASTNode, KtToken[] skipTokens, Function1<? super LighterASTNode, Unit> f) {
        Intrinsics.checkNotNullParameter(lighterASTNode, "<this>");
        Intrinsics.checkNotNullParameter(skipTokens, "skipTokens");
        Intrinsics.checkNotNullParameter(f, "f");
        for (LighterASTNode lighterASTNode2 : getChildrenAsArray(lighterASTNode)) {
            if (lighterASTNode2 == null) {
                return;
            }
            IElementType tokenType = lighterASTNode2.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !ArraysKt.contains(skipTokens, tokenType) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT)) {
                f.invoke(lighterASTNode2);
            }
        }
    }

    protected final <T> List<T> forEachChildrenReturnList(LighterASTNode lighterASTNode, Function2<? super LighterASTNode, ? super List<T>, Unit> f) {
        Intrinsics.checkNotNullParameter(lighterASTNode, "<this>");
        Intrinsics.checkNotNullParameter(f, "f");
        LighterASTNode[] childrenAsArray = getChildrenAsArray(lighterASTNode);
        ArrayList arrayList = new ArrayList();
        for (LighterASTNode lighterASTNode2 : childrenAsArray) {
            if (lighterASTNode2 == null) {
                break;
            }
            IElementType tokenType = lighterASTNode2.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT)) {
                f.invoke(lighterASTNode2, arrayList);
            }
        }
        return arrayList;
    }

    public final List<LighterASTNode> getChildNodesByType(LighterASTNode lighterASTNode, IElementType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        if (lighterASTNode == null) {
            return CollectionsKt.emptyList();
        }
        LighterASTNode[] childrenAsArray = getChildrenAsArray(lighterASTNode);
        ArrayList arrayList = new ArrayList();
        for (LighterASTNode lighterASTNode2 : childrenAsArray) {
            if (lighterASTNode2 == null) {
                return arrayList;
            }
            IElementType tokenType = lighterASTNode2.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT) && Intrinsics.areEqual(lighterASTNode2.getTokenType(), type)) {
                arrayList.add(lighterASTNode2);
            }
        }
        return arrayList;
    }
}
