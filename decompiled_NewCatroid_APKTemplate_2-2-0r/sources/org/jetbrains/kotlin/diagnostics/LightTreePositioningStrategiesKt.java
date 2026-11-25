package org.jetbrains.kotlin.diagnostics;

import com.reandroid.arsc.container.SpecTypePair;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.KtNodeType;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.util.Ref;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.com.intellij.util.diff.FlyweightCapableTreeStructure;
import org.jetbrains.kotlin.lexer.KtKeywordToken;
import org.jetbrains.kotlin.lexer.KtModifierKeywordToken;
import org.jetbrains.kotlin.lexer.KtSingleValueToken;
import org.jetbrains.kotlin.lexer.KtToken;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.stubs.elements.KtConstantExpressionElementType;
import org.jetbrains.kotlin.psi.stubs.elements.KtStringTemplateExpressionElementType;

/* JADX INFO: compiled from: LightTreePositioningStrategies.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000Z\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u001a\n\u0002\u0010\u0002\n\u0002\b\u000b\u001a/\u0010\u0004\u001a\u00020\u00052%\u0010\u0006\u001a!\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b\u0012\u0004\u0012\u00020\t\u0012\u0006\u0012\u0004\u0018\u00010\t0\u0007¢\u0006\u0002\b\nH\u0002\u001a\u001a\u0010\u000b\u001a\u00020\t*\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\f\u001a\u00020\tH\u0002\u001a \u0010\r\u001a\n\u0012\u0004\u0012\u00020\t\u0018\u00010\u0001*\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\f\u001a\u00020\t\u001a\u001c\u0010\u000e\u001a\u0004\u0018\u00010\t*\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\f\u001a\u00020\tH\u0002\u001a<\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\t0\u0001*\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\f\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\u00022\u0014\b\u0002\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00130\u0012\u001a\u001c\u0010\u0014\u001a\u0004\u0018\u00010\t*\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\f\u001a\u00020\tH\u0002\u001a\u001c\u0010\u0015\u001a\u0004\u0018\u00010\t*\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\f\u001a\u00020\tH\u0002\u001a\u001c\u0010\u0016\u001a\u0004\u0018\u00010\t*\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\f\u001a\u00020\tH\u0002\u001a\u001c\u0010\u0017\u001a\u0004\u0018\u00010\t*\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\f\u001a\u00020\tH\u0002\u001a\u001c\u0010\u0018\u001a\u0004\u0018\u00010\t*\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\f\u001a\u00020\tH\u0002\u001a\u001c\u0010\u0019\u001a\u0004\u0018\u00010\t*\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\f\u001a\u00020\tH\u0002\u001a\"\u0010\u001a\u001a\u0004\u0018\u00010\t*\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\f\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\u0002\u001a\"\u0010\u001a\u001a\u0004\u0018\u00010\t*\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\f\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\u001b\u001a&\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\t0\u0001*\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\f\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\u0002\u001a,\u0010\u001d\u001a\u0004\u0018\u00010\t*\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\f\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\u00022\b\b\u0002\u0010\u001e\u001a\u00020\u0013\u001a\"\u0010\u001f\u001a\u0004\u0018\u00010\t*\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\f\u001a\u00020\t2\u0006\u0010 \u001a\u00020\u001b\u001a\u001c\u0010!\u001a\u0004\u0018\u00010\t*\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\f\u001a\u00020\tH\u0002\u001a.\u0010\"\u001a\u0004\u0018\u00010\t*\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\f\u001a\u00020\t2\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00130\u0012\u001a\"\u0010#\u001a\u0004\u0018\u00010\t*\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\f\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\u0002\u001a.\u0010$\u001a\u0004\u0018\u00010\t*\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\f\u001a\u00020\t2\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00130\u0012\u001a.\u0010%\u001a\u0004\u0018\u00010\t*\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\f\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\u00022\b\b\u0002\u0010&\u001a\u00020\u0013H\u0002\u001a\u001c\u0010'\u001a\u0004\u0018\u00010\t*\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\f\u001a\u00020\tH\u0002\u001a\u001a\u0010(\u001a\u0004\u0018\u00010\t*\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\f\u001a\u00020\t\u001a\u001e\u0010)\u001a\b\u0012\u0004\u0012\u00020\t0**\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\f\u001a\u00020\t\u001a%\u0010+\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0,*\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\f\u001a\u00020\t¢\u0006\u0002\u0010-\u001a\u001c\u0010.\u001a\u0004\u0018\u00010\t*\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\f\u001a\u00020\tH\u0002\u001a\n\u0010/\u001a\u00020\u0013*\u000200\u001a\n\u00101\u001a\u00020\u0013*\u000200\u001a\n\u00102\u001a\u00020\u0013*\u000200\u001a\u001c\u00103\u001a\u0004\u0018\u00010\t*\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\f\u001a\u00020\tH\u0002\u001a\u001c\u00104\u001a\u0004\u0018\u00010\t*\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\f\u001a\u00020\tH\u0002\u001a\u001a\u00105\u001a\u0004\u0018\u00010\t*\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u00106\u001a\u00020\t\u001a\n\u00107\u001a\u00020\u0013*\u00020\t\u001a\u001c\u00108\u001a\u0004\u0018\u00010\t*\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\f\u001a\u00020\tH\u0002\u001a\u001a\u00109\u001a\u0004\u0018\u00010\t*\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\f\u001a\u00020\t\u001a\u001a\u0010:\u001a\u0004\u0018\u00010\t*\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u00106\u001a\u00020\t\u001a\u001c\u0010;\u001a\u0004\u0018\u00010\t*\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\f\u001a\u00020\tH\u0002\u001a\u001a\u0010<\u001a\u0004\u0018\u00010\t*\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\f\u001a\u00020\t\u001a\u001c\u0010=\u001a\u0004\u0018\u00010\t*\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\f\u001a\u00020\tH\u0002\u001a\u001c\u0010>\u001a\u0004\u0018\u00010\t*\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\f\u001a\u00020\tH\u0002\u001a\u001a\u0010?\u001a\u0004\u0018\u00010\t*\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u00106\u001a\u00020\t\u001a\u001c\u0010@\u001a\u0004\u0018\u00010\t*\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\f\u001a\u00020\tH\u0002\u001a\u001c\u0010A\u001a\u0004\u0018\u00010\t*\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\f\u001a\u00020\tH\u0002\u001a$\u0010B\u001a\u0004\u0018\u00010\t*\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\f\u001a\u00020\t2\u0006\u0010C\u001a\u00020\u0013H\u0002\u001a\u001c\u0010D\u001a\u0004\u0018\u00010\t*\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\f\u001a\u00020\tH\u0002\u001a\u001c\u0010E\u001a\u0004\u0018\u00010\t*\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\f\u001a\u00020\tH\u0002\u001a\u001c\u0010F\u001a\u0004\u0018\u00010\t*\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\f\u001a\u00020\tH\u0002\u001a\u001a\u0010G\u001a\u0004\u0018\u00010\t*\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\f\u001a\u00020\t\u001a\u001c\u0010H\u001a\u0004\u0018\u00010\t*\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\f\u001a\u00020\tH\u0002\u001a\u001c\u0010I\u001a\u0004\u0018\u00010\t*\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\f\u001a\u00020\tH\u0002\u001a,\u0010J\u001a\u00020K*\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\f\u001a\u00020\t2\u0012\u0010L\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00130\u0012\u001a\u001a\u0010M\u001a\u0004\u0018\u00010\t*\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u00106\u001a\u00020\t\u001a\u001c\u0010N\u001a\u0004\u0018\u00010\t*\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\f\u001a\u00020\tH\u0002\u001a\u0018\u0010O\u001a\u00020\t*\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\f\u001a\u00020\t\u001a\u001a\u0010P\u001a\u0004\u0018\u00010\t*\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\f\u001a\u00020\t\u001a\u001a\u0010Q\u001a\u0004\u0018\u00010\t*\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\f\u001a\u00020\t\u001a\u001c\u0010R\u001a\u0004\u0018\u00010\t*\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\f\u001a\u00020\tH\u0002\u001a \u0010S\u001a\b\u0012\u0004\u0012\u00020\t0\u0001*\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\f\u001a\u00020\tH\u0002\u001a\u001a\u0010T\u001a\u0004\u0018\u00010\t*\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u00106\u001a\u00020\t\u001a\u001c\u0010U\u001a\u0004\u0018\u00010\t*\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\f\u001a\u00020\tH\u0002\"\u001c\u0010\u0000\u001a\u0010\u0012\f\u0012\n \u0003*\u0004\u0018\u00010\u00020\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006V"}, d2 = {"EXPRESSIONS_SET", "", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/IElementType;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "keywordStrategy", "Lorg/jetbrains/kotlin/diagnostics/LightTreePositioningStrategy;", "keywordExtractor", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/com/intellij/util/diff/FlyweightCapableTreeStructure;", "Lorg/jetbrains/kotlin/com/intellij/lang/LighterASTNode;", "Lkotlin/ExtensionFunctionType;", "accessorNamePlaceholder", "node", "annotations", "byKeyword", "collectDescendantsOfType", ModuleXmlParser.TYPE, "predicate", "Lkotlin/Function1;", "", "companionKeyword", "constructorKeyword", "defaultValue", "dotOperator", "elseKeyword", "fieldKeyword", "findChildByType", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/TokenSet;", "findChildrenByType", "findDescendantByType", "followFunctions", "findDescendantByTypes", SpecTypePair.NAME_types, "findExpressionDeep", "findFirstDescendant", "findLastChildByType", "findLastDescendant", "findParentOfType", SchemaSymbols.ATTVAL_STRICT, "firstChild", "firstChildExpression", "getAncestors", "Lkotlin/sequences/Sequence;", "getChildrenArray", "", "(Lcom/intellij/util/diff/FlyweightCapableTreeStructure;Lcom/intellij/lang/LighterASTNode;)[Lcom/intellij/lang/LighterASTNode;", "getter", "hasPrimaryConstructor", "Lorg/jetbrains/kotlin/KtSourceElement;", "hasValOrVar", "hasVar", "ifKeyword", "initKeyword", "inlineModifier", "declaration", "isExpression", "lastChild", "lastChildExpression", "modalityModifier", "modifierList", "nameIdentifier", "objectKeyword", "operationReference", "overrideModifier", "primaryConstructor", "receiverTypeReference", "referenceExpression", "locateReferencedName", "returnKeyword", "rightParenthesis", "safeAccess", "selector", "setter", "supertypesList", "traverseDescendants", "", "acceptor", "typeParametersList", "typeReference", "unwrapParenthesesLabelsAndAnnotations", "userType", "valOrVarKeyword", "valueParameterList", "valueParameters", "visibilityModifier", "whenKeyword", "frontend.common-psi"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class LightTreePositioningStrategiesKt {
    private static final List<IElementType> EXPRESSIONS_SET = CollectionsKt.listOf((Object[]) new IElementType[]{KtNodeTypes.REFERENCE_EXPRESSION, KtNodeTypes.DOT_QUALIFIED_EXPRESSION, KtNodeTypes.LAMBDA_EXPRESSION, KtNodeTypes.FUN});

    public static final boolean hasValOrVar(KtSourceElement ktSourceElement) {
        Intrinsics.checkNotNullParameter(ktSourceElement, "<this>");
        return valOrVarKeyword(ktSourceElement.getTreeStructure(), ktSourceElement.getLighterASTNode()) != null;
    }

    public static final boolean hasVar(KtSourceElement ktSourceElement) {
        Intrinsics.checkNotNullParameter(ktSourceElement, "<this>");
        FlyweightCapableTreeStructure<LighterASTNode> treeStructure = ktSourceElement.getTreeStructure();
        LighterASTNode lighterASTNode = ktSourceElement.getLighterASTNode();
        KtKeywordToken VAR_KEYWORD = KtTokens.VAR_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(VAR_KEYWORD, "VAR_KEYWORD");
        return findChildByType(treeStructure, lighterASTNode, VAR_KEYWORD) != null;
    }

    public static final boolean hasPrimaryConstructor(KtSourceElement ktSourceElement) {
        Intrinsics.checkNotNullParameter(ktSourceElement, "<this>");
        return primaryConstructor(ktSourceElement.getTreeStructure(), ktSourceElement.getLighterASTNode()) != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final LighterASTNode companionKeyword(FlyweightCapableTreeStructure<LighterASTNode> flyweightCapableTreeStructure, LighterASTNode lighterASTNode) {
        LighterASTNode lighterASTNodeModifierList = modifierList(flyweightCapableTreeStructure, lighterASTNode);
        if (lighterASTNodeModifierList == null) {
            return null;
        }
        KtModifierKeywordToken COMPANION_KEYWORD = KtTokens.COMPANION_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(COMPANION_KEYWORD, "COMPANION_KEYWORD");
        return findChildByType(flyweightCapableTreeStructure, lighterASTNodeModifierList, COMPANION_KEYWORD);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final LighterASTNode constructorKeyword(FlyweightCapableTreeStructure<LighterASTNode> flyweightCapableTreeStructure, LighterASTNode lighterASTNode) {
        KtKeywordToken CONSTRUCTOR_KEYWORD = KtTokens.CONSTRUCTOR_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(CONSTRUCTOR_KEYWORD, "CONSTRUCTOR_KEYWORD");
        return findChildByType(flyweightCapableTreeStructure, lighterASTNode, CONSTRUCTOR_KEYWORD);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final LighterASTNode dotOperator(FlyweightCapableTreeStructure<LighterASTNode> flyweightCapableTreeStructure, LighterASTNode lighterASTNode) {
        KtSingleValueToken DOT = KtTokens.DOT;
        Intrinsics.checkNotNullExpressionValue(DOT, "DOT");
        return findChildByType(flyweightCapableTreeStructure, lighterASTNode, DOT);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final LighterASTNode safeAccess(FlyweightCapableTreeStructure<LighterASTNode> flyweightCapableTreeStructure, LighterASTNode lighterASTNode) {
        KtSingleValueToken SAFE_ACCESS = KtTokens.SAFE_ACCESS;
        Intrinsics.checkNotNullExpressionValue(SAFE_ACCESS, "SAFE_ACCESS");
        return findChildByType(flyweightCapableTreeStructure, lighterASTNode, SAFE_ACCESS);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final LighterASTNode initKeyword(FlyweightCapableTreeStructure<LighterASTNode> flyweightCapableTreeStructure, LighterASTNode lighterASTNode) {
        KtKeywordToken INIT_KEYWORD = KtTokens.INIT_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(INIT_KEYWORD, "INIT_KEYWORD");
        return findChildByType(flyweightCapableTreeStructure, lighterASTNode, INIT_KEYWORD);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final LighterASTNode whenKeyword(FlyweightCapableTreeStructure<LighterASTNode> flyweightCapableTreeStructure, LighterASTNode lighterASTNode) {
        KtKeywordToken WHEN_KEYWORD = KtTokens.WHEN_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(WHEN_KEYWORD, "WHEN_KEYWORD");
        return findChildByType(flyweightCapableTreeStructure, lighterASTNode, WHEN_KEYWORD);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final LighterASTNode ifKeyword(FlyweightCapableTreeStructure<LighterASTNode> flyweightCapableTreeStructure, LighterASTNode lighterASTNode) {
        KtKeywordToken IF_KEYWORD = KtTokens.IF_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(IF_KEYWORD, "IF_KEYWORD");
        return findChildByType(flyweightCapableTreeStructure, lighterASTNode, IF_KEYWORD);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final LighterASTNode elseKeyword(FlyweightCapableTreeStructure<LighterASTNode> flyweightCapableTreeStructure, LighterASTNode lighterASTNode) {
        KtKeywordToken ELSE_KEYWORD = KtTokens.ELSE_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(ELSE_KEYWORD, "ELSE_KEYWORD");
        return findChildByType(flyweightCapableTreeStructure, lighterASTNode, ELSE_KEYWORD);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final LighterASTNode returnKeyword(FlyweightCapableTreeStructure<LighterASTNode> flyweightCapableTreeStructure, LighterASTNode lighterASTNode) {
        KtKeywordToken RETURN_KEYWORD = KtTokens.RETURN_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(RETURN_KEYWORD, "RETURN_KEYWORD");
        return findChildByType(flyweightCapableTreeStructure, lighterASTNode, RETURN_KEYWORD);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final LighterASTNode fieldKeyword(FlyweightCapableTreeStructure<LighterASTNode> flyweightCapableTreeStructure, LighterASTNode lighterASTNode) {
        KtKeywordToken FIELD_KEYWORD = KtTokens.FIELD_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(FIELD_KEYWORD, "FIELD_KEYWORD");
        return findChildByType(flyweightCapableTreeStructure, lighterASTNode, FIELD_KEYWORD);
    }

    public static final LighterASTNode nameIdentifier(FlyweightCapableTreeStructure<LighterASTNode> flyweightCapableTreeStructure, LighterASTNode node) {
        Intrinsics.checkNotNullParameter(flyweightCapableTreeStructure, "<this>");
        Intrinsics.checkNotNullParameter(node, "node");
        KtToken IDENTIFIER = KtTokens.IDENTIFIER;
        Intrinsics.checkNotNullExpressionValue(IDENTIFIER, "IDENTIFIER");
        return findChildByType(flyweightCapableTreeStructure, node, IDENTIFIER);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final LighterASTNode operationReference(FlyweightCapableTreeStructure<LighterASTNode> flyweightCapableTreeStructure, LighterASTNode lighterASTNode) {
        IElementType OPERATION_REFERENCE = KtNodeTypes.OPERATION_REFERENCE;
        Intrinsics.checkNotNullExpressionValue(OPERATION_REFERENCE, "OPERATION_REFERENCE");
        return findChildByType(flyweightCapableTreeStructure, lighterASTNode, OPERATION_REFERENCE);
    }

    public static final boolean isExpression(LighterASTNode lighterASTNode) {
        Intrinsics.checkNotNullParameter(lighterASTNode, "<this>");
        IElementType tokenType = lighterASTNode.getTokenType();
        if (tokenType instanceof KtNodeType ? true : tokenType instanceof KtConstantExpressionElementType ? true : tokenType instanceof KtStringTemplateExpressionElementType) {
            return true;
        }
        return EXPRESSIONS_SET.contains(tokenType);
    }

    public static final LighterASTNode[] getChildrenArray(FlyweightCapableTreeStructure<LighterASTNode> flyweightCapableTreeStructure, LighterASTNode node) {
        Intrinsics.checkNotNullParameter(flyweightCapableTreeStructure, "<this>");
        Intrinsics.checkNotNullParameter(node, "node");
        Ref<LighterASTNode[]> ref = new Ref<>();
        flyweightCapableTreeStructure.getChildren(node, ref);
        LighterASTNode[] lighterASTNodeArr = ref.get();
        return lighterASTNodeArr == null ? new LighterASTNode[0] : lighterASTNodeArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final LighterASTNode referenceExpression(FlyweightCapableTreeStructure<LighterASTNode> flyweightCapableTreeStructure, LighterASTNode lighterASTNode, boolean z) {
        Ref<LighterASTNode[]> ref = new Ref<>();
        flyweightCapableTreeStructure.getChildren(lighterASTNode, ref);
        LighterASTNode[] lighterASTNodeArr = ref.get();
        LighterASTNode lighterASTNodeReferenceExpression = null;
        if (lighterASTNodeArr != null) {
            int length = lighterASTNodeArr.length;
            for (int i = 0; i < length; i++) {
                LighterASTNode lighterASTNode2 = lighterASTNodeArr[i];
                if (lighterASTNode2 == null || !isExpression(lighterASTNode2)) {
                    if (!Intrinsics.areEqual(lighterASTNode2 != null ? lighterASTNode2.getTokenType() : null, KtNodeTypes.PARENTHESIZED)) {
                    }
                }
                lighterASTNodeReferenceExpression = lighterASTNode2;
                break;
            }
        }
        while (z && lighterASTNodeReferenceExpression != null && Intrinsics.areEqual(lighterASTNodeReferenceExpression.getTokenType(), KtNodeTypes.PARENTHESIZED)) {
            lighterASTNodeReferenceExpression = referenceExpression(flyweightCapableTreeStructure, lighterASTNodeReferenceExpression, true);
        }
        return lighterASTNodeReferenceExpression;
    }

    public static final LighterASTNode unwrapParenthesesLabelsAndAnnotations(FlyweightCapableTreeStructure<LighterASTNode> flyweightCapableTreeStructure, LighterASTNode node) {
        LighterASTNode lighterASTNodeFirstChildExpression;
        Intrinsics.checkNotNullParameter(flyweightCapableTreeStructure, "<this>");
        Intrinsics.checkNotNullParameter(node, "node");
        while (true) {
            IElementType tokenType = node.getTokenType();
            if (Intrinsics.areEqual(tokenType, KtNodeTypes.PARENTHESIZED)) {
                lighterASTNodeFirstChildExpression = firstChildExpression(flyweightCapableTreeStructure, node);
                if (lighterASTNodeFirstChildExpression == null) {
                    return node;
                }
            } else if (Intrinsics.areEqual(tokenType, KtNodeTypes.LABELED_EXPRESSION)) {
                lighterASTNodeFirstChildExpression = lastChildExpression(flyweightCapableTreeStructure, node);
                if (lighterASTNodeFirstChildExpression == null) {
                    return node;
                }
            } else if (!Intrinsics.areEqual(tokenType, KtNodeTypes.ANNOTATED_EXPRESSION) || (lighterASTNodeFirstChildExpression = firstChildExpression(flyweightCapableTreeStructure, node)) == null) {
                return node;
            }
            node = lighterASTNodeFirstChildExpression;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final LighterASTNode findExpressionDeep(FlyweightCapableTreeStructure<LighterASTNode> flyweightCapableTreeStructure, LighterASTNode lighterASTNode) {
        return findFirstDescendant(flyweightCapableTreeStructure, lighterASTNode, new Function1<LighterASTNode, Boolean>() { // from class: org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategiesKt.findExpressionDeep.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(LighterASTNode it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(LightTreePositioningStrategiesKt.isExpression(it));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final LighterASTNode rightParenthesis(FlyweightCapableTreeStructure<LighterASTNode> flyweightCapableTreeStructure, LighterASTNode lighterASTNode) {
        KtSingleValueToken RPAR = KtTokens.RPAR;
        Intrinsics.checkNotNullExpressionValue(RPAR, "RPAR");
        return findChildByType(flyweightCapableTreeStructure, lighterASTNode, RPAR);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final LighterASTNode objectKeyword(FlyweightCapableTreeStructure<LighterASTNode> flyweightCapableTreeStructure, LighterASTNode lighterASTNode) {
        KtKeywordToken OBJECT_KEYWORD = KtTokens.OBJECT_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(OBJECT_KEYWORD, "OBJECT_KEYWORD");
        return findChildByType(flyweightCapableTreeStructure, lighterASTNode, OBJECT_KEYWORD);
    }

    public static final LighterASTNode valOrVarKeyword(FlyweightCapableTreeStructure<LighterASTNode> flyweightCapableTreeStructure, LighterASTNode node) {
        Intrinsics.checkNotNullParameter(flyweightCapableTreeStructure, "<this>");
        Intrinsics.checkNotNullParameter(node, "node");
        TokenSet VAL_VAR_TOKEN_SET = KtParameter.VAL_VAR_TOKEN_SET;
        Intrinsics.checkNotNullExpressionValue(VAL_VAR_TOKEN_SET, "VAL_VAR_TOKEN_SET");
        return findChildByType(flyweightCapableTreeStructure, node, VAL_VAR_TOKEN_SET);
    }

    public static final LighterASTNode visibilityModifier(FlyweightCapableTreeStructure<LighterASTNode> flyweightCapableTreeStructure, LighterASTNode declaration) {
        Intrinsics.checkNotNullParameter(flyweightCapableTreeStructure, "<this>");
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        LighterASTNode lighterASTNodeModifierList = modifierList(flyweightCapableTreeStructure, declaration);
        if (lighterASTNodeModifierList == null) {
            return null;
        }
        TokenSet VISIBILITY_MODIFIERS = KtTokens.VISIBILITY_MODIFIERS;
        Intrinsics.checkNotNullExpressionValue(VISIBILITY_MODIFIERS, "VISIBILITY_MODIFIERS");
        return findChildByType(flyweightCapableTreeStructure, lighterASTNodeModifierList, VISIBILITY_MODIFIERS);
    }

    public static final LighterASTNode modalityModifier(FlyweightCapableTreeStructure<LighterASTNode> flyweightCapableTreeStructure, LighterASTNode declaration) {
        Intrinsics.checkNotNullParameter(flyweightCapableTreeStructure, "<this>");
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        LighterASTNode lighterASTNodeModifierList = modifierList(flyweightCapableTreeStructure, declaration);
        if (lighterASTNodeModifierList == null) {
            return null;
        }
        TokenSet MODALITY_MODIFIERS = KtTokens.MODALITY_MODIFIERS;
        Intrinsics.checkNotNullExpressionValue(MODALITY_MODIFIERS, "MODALITY_MODIFIERS");
        return findChildByType(flyweightCapableTreeStructure, lighterASTNodeModifierList, MODALITY_MODIFIERS);
    }

    public static final LighterASTNode overrideModifier(FlyweightCapableTreeStructure<LighterASTNode> flyweightCapableTreeStructure, LighterASTNode declaration) {
        Intrinsics.checkNotNullParameter(flyweightCapableTreeStructure, "<this>");
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        LighterASTNode lighterASTNodeModifierList = modifierList(flyweightCapableTreeStructure, declaration);
        if (lighterASTNodeModifierList == null) {
            return null;
        }
        KtModifierKeywordToken OVERRIDE_KEYWORD = KtTokens.OVERRIDE_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(OVERRIDE_KEYWORD, "OVERRIDE_KEYWORD");
        return findChildByType(flyweightCapableTreeStructure, lighterASTNodeModifierList, OVERRIDE_KEYWORD);
    }

    public static final LighterASTNode inlineModifier(FlyweightCapableTreeStructure<LighterASTNode> flyweightCapableTreeStructure, LighterASTNode declaration) {
        Intrinsics.checkNotNullParameter(flyweightCapableTreeStructure, "<this>");
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        LighterASTNode lighterASTNodeModifierList = modifierList(flyweightCapableTreeStructure, declaration);
        if (lighterASTNodeModifierList == null) {
            return null;
        }
        KtModifierKeywordToken INLINE_KEYWORD = KtTokens.INLINE_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(INLINE_KEYWORD, "INLINE_KEYWORD");
        return findChildByType(flyweightCapableTreeStructure, lighterASTNodeModifierList, INLINE_KEYWORD);
    }

    public static final LighterASTNode typeParametersList(FlyweightCapableTreeStructure<LighterASTNode> flyweightCapableTreeStructure, LighterASTNode declaration) {
        Intrinsics.checkNotNullParameter(flyweightCapableTreeStructure, "<this>");
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IElementType TYPE_PARAMETER_LIST = KtNodeTypes.TYPE_PARAMETER_LIST;
        Intrinsics.checkNotNullExpressionValue(TYPE_PARAMETER_LIST, "TYPE_PARAMETER_LIST");
        return findChildByType(flyweightCapableTreeStructure, declaration, TYPE_PARAMETER_LIST);
    }

    public static final List<LighterASTNode> annotations(FlyweightCapableTreeStructure<LighterASTNode> flyweightCapableTreeStructure, LighterASTNode node) {
        LighterASTNode lighterASTNodeModifierList;
        Intrinsics.checkNotNullParameter(flyweightCapableTreeStructure, "<this>");
        Intrinsics.checkNotNullParameter(node, "node");
        IElementType TYPE_REFERENCE = KtNodeTypes.TYPE_REFERENCE;
        Intrinsics.checkNotNullExpressionValue(TYPE_REFERENCE, "TYPE_REFERENCE");
        LighterASTNode lighterASTNodeFindChildByType = findChildByType(flyweightCapableTreeStructure, node, TYPE_REFERENCE);
        if (lighterASTNodeFindChildByType == null || (lighterASTNodeModifierList = modifierList(flyweightCapableTreeStructure, lighterASTNodeFindChildByType)) == null) {
            return null;
        }
        IElementType ANNOTATION_ENTRY = KtNodeTypes.ANNOTATION_ENTRY;
        Intrinsics.checkNotNullExpressionValue(ANNOTATION_ENTRY, "ANNOTATION_ENTRY");
        return collectDescendantsOfType$default(flyweightCapableTreeStructure, lighterASTNodeModifierList, ANNOTATION_ENTRY, null, 4, null);
    }

    public static final LighterASTNode userType(FlyweightCapableTreeStructure<LighterASTNode> flyweightCapableTreeStructure, LighterASTNode node) {
        Intrinsics.checkNotNullParameter(flyweightCapableTreeStructure, "<this>");
        Intrinsics.checkNotNullParameter(node, "node");
        IElementType TYPE_REFERENCE = KtNodeTypes.TYPE_REFERENCE;
        Intrinsics.checkNotNullExpressionValue(TYPE_REFERENCE, "TYPE_REFERENCE");
        LighterASTNode lighterASTNodeFindChildByType = findChildByType(flyweightCapableTreeStructure, node, TYPE_REFERENCE);
        if (lighterASTNodeFindChildByType == null) {
            return null;
        }
        IElementType USER_TYPE = KtNodeTypes.USER_TYPE;
        Intrinsics.checkNotNullExpressionValue(USER_TYPE, "USER_TYPE");
        return findChildByType(flyweightCapableTreeStructure, lighterASTNodeFindChildByType, USER_TYPE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final LighterASTNode supertypesList(FlyweightCapableTreeStructure<LighterASTNode> flyweightCapableTreeStructure, LighterASTNode lighterASTNode) {
        IElementType SUPER_TYPE_LIST = KtNodeTypes.SUPER_TYPE_LIST;
        Intrinsics.checkNotNullExpressionValue(SUPER_TYPE_LIST, "SUPER_TYPE_LIST");
        return findChildByType(flyweightCapableTreeStructure, lighterASTNode, SUPER_TYPE_LIST);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final LighterASTNode getter(FlyweightCapableTreeStructure<LighterASTNode> flyweightCapableTreeStructure, LighterASTNode lighterASTNode) {
        Ref<LighterASTNode[]> ref = new Ref<>();
        flyweightCapableTreeStructure.getChildren(lighterASTNode, ref);
        LighterASTNode[] lighterASTNodeArr = ref.get();
        if (lighterASTNodeArr == null) {
            return null;
        }
        for (LighterASTNode lighterASTNode2 : lighterASTNodeArr) {
            if (lighterASTNode2 != null && Intrinsics.areEqual(lighterASTNode2.getTokenType(), KtNodeTypes.PROPERTY_ACCESSOR)) {
                KtKeywordToken GET_KEYWORD = KtTokens.GET_KEYWORD;
                Intrinsics.checkNotNullExpressionValue(GET_KEYWORD, "GET_KEYWORD");
                if (findChildByType(flyweightCapableTreeStructure, lighterASTNode2, GET_KEYWORD) != null) {
                    return lighterASTNode2;
                }
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final LighterASTNode setter(FlyweightCapableTreeStructure<LighterASTNode> flyweightCapableTreeStructure, LighterASTNode lighterASTNode) {
        Ref<LighterASTNode[]> ref = new Ref<>();
        flyweightCapableTreeStructure.getChildren(lighterASTNode, ref);
        LighterASTNode[] lighterASTNodeArr = ref.get();
        if (lighterASTNodeArr == null) {
            return null;
        }
        for (LighterASTNode lighterASTNode2 : lighterASTNodeArr) {
            if (lighterASTNode2 != null && Intrinsics.areEqual(lighterASTNode2.getTokenType(), KtNodeTypes.PROPERTY_ACCESSOR)) {
                KtKeywordToken SET_KEYWORD = KtTokens.SET_KEYWORD;
                Intrinsics.checkNotNullExpressionValue(SET_KEYWORD, "SET_KEYWORD");
                if (findChildByType(flyweightCapableTreeStructure, lighterASTNode2, SET_KEYWORD) != null) {
                    return lighterASTNode2;
                }
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final LighterASTNode accessorNamePlaceholder(FlyweightCapableTreeStructure<LighterASTNode> flyweightCapableTreeStructure, LighterASTNode lighterASTNode) {
        KtKeywordToken GET_KEYWORD = KtTokens.GET_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(GET_KEYWORD, "GET_KEYWORD");
        LighterASTNode lighterASTNodeFindChildByType = findChildByType(flyweightCapableTreeStructure, lighterASTNode, GET_KEYWORD);
        if (lighterASTNodeFindChildByType != null) {
            return lighterASTNodeFindChildByType;
        }
        KtKeywordToken SET_KEYWORD = KtTokens.SET_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(SET_KEYWORD, "SET_KEYWORD");
        LighterASTNode lighterASTNodeFindChildByType2 = findChildByType(flyweightCapableTreeStructure, lighterASTNode, SET_KEYWORD);
        Intrinsics.checkNotNull(lighterASTNodeFindChildByType2);
        return lighterASTNodeFindChildByType2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final LighterASTNode modifierList(FlyweightCapableTreeStructure<LighterASTNode> flyweightCapableTreeStructure, LighterASTNode lighterASTNode) {
        IElementType MODIFIER_LIST = KtNodeTypes.MODIFIER_LIST;
        Intrinsics.checkNotNullExpressionValue(MODIFIER_LIST, "MODIFIER_LIST");
        return findChildByType(flyweightCapableTreeStructure, lighterASTNode, MODIFIER_LIST);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final LighterASTNode primaryConstructor(FlyweightCapableTreeStructure<LighterASTNode> flyweightCapableTreeStructure, LighterASTNode lighterASTNode) {
        IElementType PRIMARY_CONSTRUCTOR = KtNodeTypes.PRIMARY_CONSTRUCTOR;
        Intrinsics.checkNotNullExpressionValue(PRIMARY_CONSTRUCTOR, "PRIMARY_CONSTRUCTOR");
        return findChildByType(flyweightCapableTreeStructure, lighterASTNode, PRIMARY_CONSTRUCTOR);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final LighterASTNode valueParameterList(FlyweightCapableTreeStructure<LighterASTNode> flyweightCapableTreeStructure, LighterASTNode lighterASTNode) {
        IElementType VALUE_PARAMETER_LIST = KtNodeTypes.VALUE_PARAMETER_LIST;
        Intrinsics.checkNotNullExpressionValue(VALUE_PARAMETER_LIST, "VALUE_PARAMETER_LIST");
        return findChildByType(flyweightCapableTreeStructure, lighterASTNode, VALUE_PARAMETER_LIST);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final List<LighterASTNode> valueParameters(FlyweightCapableTreeStructure<LighterASTNode> flyweightCapableTreeStructure, LighterASTNode lighterASTNode) {
        List<LighterASTNode> listFindChildrenByType;
        LighterASTNode lighterASTNodeValueParameterList = valueParameterList(flyweightCapableTreeStructure, lighterASTNode);
        if (lighterASTNodeValueParameterList != null) {
            IElementType VALUE_PARAMETER = KtNodeTypes.VALUE_PARAMETER;
            Intrinsics.checkNotNullExpressionValue(VALUE_PARAMETER, "VALUE_PARAMETER");
            listFindChildrenByType = findChildrenByType(flyweightCapableTreeStructure, lighterASTNodeValueParameterList, VALUE_PARAMETER);
        } else {
            listFindChildrenByType = null;
        }
        return listFindChildrenByType == null ? CollectionsKt.emptyList() : listFindChildrenByType;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final LighterASTNode typeReference(FlyweightCapableTreeStructure<LighterASTNode> flyweightCapableTreeStructure, LighterASTNode lighterASTNode) {
        List listFilterNotNull;
        Ref<LighterASTNode[]> ref = new Ref<>();
        flyweightCapableTreeStructure.getChildren(lighterASTNode, ref);
        LighterASTNode[] lighterASTNodeArr = ref.get();
        Object obj = null;
        if (lighterASTNodeArr == null || (listFilterNotNull = ArraysKt.filterNotNull(lighterASTNodeArr)) == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        boolean z = false;
        for (Object obj2 : listFilterNotNull) {
            if (!z) {
                if (Intrinsics.areEqual(((LighterASTNode) obj2).getTokenType(), KtTokens.COLON)) {
                    arrayList.add(obj2);
                    z = true;
                }
            } else {
                arrayList.add(obj2);
            }
        }
        for (Object obj3 : arrayList) {
            if (Intrinsics.areEqual(((LighterASTNode) obj3).getTokenType(), KtNodeTypes.TYPE_REFERENCE)) {
                obj = obj3;
                break;
            }
        }
        return (LighterASTNode) obj;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final LighterASTNode receiverTypeReference(FlyweightCapableTreeStructure<LighterASTNode> flyweightCapableTreeStructure, LighterASTNode lighterASTNode) {
        List listFilterNotNull;
        Ref<LighterASTNode[]> ref = new Ref<>();
        flyweightCapableTreeStructure.getChildren(lighterASTNode, ref);
        LighterASTNode[] lighterASTNodeArr = ref.get();
        Object obj = null;
        if (lighterASTNodeArr == null || (listFilterNotNull = ArraysKt.filterNotNull(lighterASTNodeArr)) == null) {
            return null;
        }
        for (Object obj2 : listFilterNotNull) {
            LighterASTNode lighterASTNode2 = (LighterASTNode) obj2;
            if (Intrinsics.areEqual(lighterASTNode2.getTokenType(), KtTokens.COLON) || Intrinsics.areEqual(lighterASTNode2.getTokenType(), KtTokens.LPAR)) {
                return null;
            }
            if (Intrinsics.areEqual(lighterASTNode2.getTokenType(), KtNodeTypes.TYPE_REFERENCE)) {
                obj = obj2;
                break;
            }
        }
        return (LighterASTNode) obj;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final LightTreePositioningStrategy keywordStrategy(final Function2<? super FlyweightCapableTreeStructure<LighterASTNode>, ? super LighterASTNode, ? extends LighterASTNode> function2) {
        return new LightTreePositioningStrategy() { // from class: org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategiesKt.keywordStrategy.1
            @Override // org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategy
            public List<TextRange> mark(LighterASTNode node, int startOffset, int endOffset, FlyweightCapableTreeStructure<LighterASTNode> tree) {
                Intrinsics.checkNotNullParameter(node, "node");
                Intrinsics.checkNotNullParameter(tree, "tree");
                LighterASTNode lighterASTNodeInvoke = function2.invoke(tree, node);
                if (lighterASTNodeInvoke != null) {
                    return LightTreePositioningStrategyKt.markElement(lighterASTNodeInvoke, startOffset, endOffset, tree, node);
                }
                return LightTreePositioningStrategies.INSTANCE.getDEFAULT().mark(node, startOffset, endOffset, tree);
            }

            @Override // org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategy
            public boolean isValid(LighterASTNode node, FlyweightCapableTreeStructure<LighterASTNode> tree) {
                Intrinsics.checkNotNullParameter(node, "node");
                Intrinsics.checkNotNullParameter(tree, "tree");
                return function2.invoke(tree, node) != null;
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final LighterASTNode defaultValue(FlyweightCapableTreeStructure<LighterASTNode> flyweightCapableTreeStructure, LighterASTNode lighterASTNode) {
        List<LighterASTNode> listReversed;
        Ref<LighterASTNode[]> ref = new Ref<>();
        flyweightCapableTreeStructure.getChildren(lighterASTNode, ref);
        LighterASTNode[] lighterASTNodeArr = ref.get();
        if (lighterASTNodeArr != null && (listReversed = ArraysKt.reversed(lighterASTNodeArr)) != null) {
            for (LighterASTNode lighterASTNode2 : listReversed) {
                if (lighterASTNode2 != null && !Intrinsics.areEqual(lighterASTNode2.getTokenType(), KtTokens.WHITE_SPACE)) {
                    if (Intrinsics.areEqual(lighterASTNode2.getTokenType(), KtNodeTypes.TYPE_REFERENCE) || Intrinsics.areEqual(lighterASTNode2.getTokenType(), KtTokens.COLON)) {
                        break;
                    }
                    return lighterASTNode2;
                }
            }
        }
        return null;
    }

    public static final LighterASTNode selector(FlyweightCapableTreeStructure<LighterASTNode> flyweightCapableTreeStructure, LighterASTNode node) {
        Intrinsics.checkNotNullParameter(flyweightCapableTreeStructure, "<this>");
        Intrinsics.checkNotNullParameter(node, "node");
        Ref<LighterASTNode[]> ref = new Ref<>();
        flyweightCapableTreeStructure.getChildren(node, ref);
        LighterASTNode[] lighterASTNodeArr = ref.get();
        if (lighterASTNodeArr == null) {
            return null;
        }
        boolean z = false;
        for (LighterASTNode lighterASTNode : lighterASTNodeArr) {
            if (lighterASTNode != null) {
                IElementType tokenType = lighterASTNode.getTokenType();
                if (Intrinsics.areEqual(tokenType, KtTokens.DOT) || Intrinsics.areEqual(tokenType, KtTokens.COLONCOLON) || Intrinsics.areEqual(tokenType, KtTokens.SAFE_ACCESS)) {
                    z = true;
                } else if (z && isExpression(lighterASTNode)) {
                    return lighterASTNode;
                }
            }
        }
        return null;
    }

    public static final LighterASTNode firstChildExpression(FlyweightCapableTreeStructure<LighterASTNode> flyweightCapableTreeStructure, LighterASTNode node) {
        Intrinsics.checkNotNullParameter(flyweightCapableTreeStructure, "<this>");
        Intrinsics.checkNotNullParameter(node, "node");
        Ref<LighterASTNode[]> ref = new Ref<>();
        flyweightCapableTreeStructure.getChildren(node, ref);
        LighterASTNode[] lighterASTNodeArr = ref.get();
        if (lighterASTNodeArr == null) {
            return null;
        }
        for (LighterASTNode lighterASTNode : lighterASTNodeArr) {
            if (lighterASTNode != null && isExpression(lighterASTNode)) {
                return lighterASTNode;
            }
        }
        return null;
    }

    public static final LighterASTNode lastChildExpression(FlyweightCapableTreeStructure<LighterASTNode> flyweightCapableTreeStructure, LighterASTNode node) {
        int length;
        Intrinsics.checkNotNullParameter(flyweightCapableTreeStructure, "<this>");
        Intrinsics.checkNotNullParameter(node, "node");
        Ref<LighterASTNode[]> ref = new Ref<>();
        flyweightCapableTreeStructure.getChildren(node, ref);
        LighterASTNode[] lighterASTNodeArr = ref.get();
        if (lighterASTNodeArr == null || (length = lighterASTNodeArr.length - 1) < 0) {
            return null;
        }
        while (true) {
            int i = length - 1;
            LighterASTNode lighterASTNode = lighterASTNodeArr[length];
            if (lighterASTNode != null && isExpression(lighterASTNode)) {
                return lighterASTNode;
            }
            if (i < 0) {
                return null;
            }
            length = i;
        }
    }

    public static final LighterASTNode findChildByType(FlyweightCapableTreeStructure<LighterASTNode> flyweightCapableTreeStructure, LighterASTNode node, IElementType type) {
        Intrinsics.checkNotNullParameter(flyweightCapableTreeStructure, "<this>");
        Intrinsics.checkNotNullParameter(node, "node");
        Intrinsics.checkNotNullParameter(type, "type");
        Ref<LighterASTNode[]> ref = new Ref<>();
        flyweightCapableTreeStructure.getChildren(node, ref);
        LighterASTNode[] lighterASTNodeArr = ref.get();
        if (lighterASTNodeArr == null) {
            return null;
        }
        int length = lighterASTNodeArr.length;
        for (int i = 0; i < length; i++) {
            LighterASTNode lighterASTNode = lighterASTNodeArr[i];
            if (Intrinsics.areEqual(lighterASTNode != null ? lighterASTNode.getTokenType() : null, type)) {
                return lighterASTNode;
            }
        }
        return null;
    }

    public static final List<LighterASTNode> findChildrenByType(FlyweightCapableTreeStructure<LighterASTNode> flyweightCapableTreeStructure, LighterASTNode node, IElementType type) {
        Intrinsics.checkNotNullParameter(flyweightCapableTreeStructure, "<this>");
        Intrinsics.checkNotNullParameter(node, "node");
        Intrinsics.checkNotNullParameter(type, "type");
        Ref<LighterASTNode[]> ref = new Ref<>();
        flyweightCapableTreeStructure.getChildren(node, ref);
        LighterASTNode[] lighterASTNodeArr = ref.get();
        List<LighterASTNode> listFilterNotNull = null;
        if (lighterASTNodeArr != null) {
            ArrayList arrayList = new ArrayList();
            int length = lighterASTNodeArr.length;
            for (int i = 0; i < length; i++) {
                LighterASTNode lighterASTNode = lighterASTNodeArr[i];
                if (Intrinsics.areEqual(lighterASTNode != null ? lighterASTNode.getTokenType() : null, type)) {
                    arrayList.add(lighterASTNode);
                }
            }
            listFilterNotNull = CollectionsKt.filterNotNull(arrayList);
        }
        return listFilterNotNull == null ? CollectionsKt.emptyList() : listFilterNotNull;
    }

    public static final LighterASTNode findLastChildByType(FlyweightCapableTreeStructure<LighterASTNode> flyweightCapableTreeStructure, LighterASTNode node, IElementType type) {
        int length;
        Intrinsics.checkNotNullParameter(flyweightCapableTreeStructure, "<this>");
        Intrinsics.checkNotNullParameter(node, "node");
        Intrinsics.checkNotNullParameter(type, "type");
        Ref<LighterASTNode[]> ref = new Ref<>();
        flyweightCapableTreeStructure.getChildren(node, ref);
        LighterASTNode[] lighterASTNodeArr = ref.get();
        if (lighterASTNodeArr == null || (length = lighterASTNodeArr.length - 1) < 0) {
            return null;
        }
        while (true) {
            int i = length - 1;
            LighterASTNode lighterASTNode = lighterASTNodeArr[length];
            if (Intrinsics.areEqual(lighterASTNode != null ? lighterASTNode.getTokenType() : null, type)) {
                return lighterASTNode;
            }
            if (i < 0) {
                return null;
            }
            length = i;
        }
    }

    public static /* synthetic */ LighterASTNode findDescendantByType$default(FlyweightCapableTreeStructure flyweightCapableTreeStructure, LighterASTNode lighterASTNode, IElementType iElementType, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = true;
        }
        return findDescendantByType(flyweightCapableTreeStructure, lighterASTNode, iElementType, z);
    }

    /* JADX WARN: Code duplicated, block: B:30:0x0061  */
    public static final LighterASTNode findDescendantByType(FlyweightCapableTreeStructure<LighterASTNode> flyweightCapableTreeStructure, LighterASTNode node, IElementType type, boolean z) {
        boolean z2;
        LighterASTNode lighterASTNode;
        Intrinsics.checkNotNullParameter(flyweightCapableTreeStructure, "<this>");
        Intrinsics.checkNotNullParameter(node, "node");
        Intrinsics.checkNotNullParameter(type, "type");
        Ref<LighterASTNode[]> ref = new Ref<>();
        flyweightCapableTreeStructure.getChildren(node, ref);
        LighterASTNode[] lighterASTNodeArr = ref.get();
        if (lighterASTNodeArr != null) {
            int length = lighterASTNodeArr.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    lighterASTNode = null;
                    break;
                }
                lighterASTNode = lighterASTNodeArr[i];
                if (Intrinsics.areEqual(lighterASTNode != null ? lighterASTNode.getTokenType() : null, type)) {
                    break;
                }
                i++;
            }
            if (lighterASTNode != null) {
                return lighterASTNode;
            }
        }
        LighterASTNode[] lighterASTNodeArr2 = ref.get();
        if (lighterASTNodeArr2 == null) {
            return null;
        }
        int length2 = lighterASTNodeArr2.length;
        for (int i2 = 0; i2 < length2; i2++) {
            LighterASTNode lighterASTNode2 = lighterASTNodeArr2[i2];
            if (z) {
                z2 = false;
            } else if (Intrinsics.areEqual(lighterASTNode2 != null ? lighterASTNode2.getTokenType() : null, KtNodeTypes.FUN)) {
                z2 = true;
            } else {
                z2 = false;
            }
            LighterASTNode lighterASTNodeFindDescendantByType = (z2 || lighterASTNode2 == null) ? null : findDescendantByType(flyweightCapableTreeStructure, lighterASTNode2, type, z);
            if (lighterASTNodeFindDescendantByType != null) {
                return lighterASTNodeFindDescendantByType;
            }
        }
        return null;
    }

    public static final LighterASTNode findDescendantByTypes(FlyweightCapableTreeStructure<LighterASTNode> flyweightCapableTreeStructure, LighterASTNode node, TokenSet types) {
        LighterASTNode lighterASTNode;
        Intrinsics.checkNotNullParameter(flyweightCapableTreeStructure, "<this>");
        Intrinsics.checkNotNullParameter(node, "node");
        Intrinsics.checkNotNullParameter(types, "types");
        Ref<LighterASTNode[]> ref = new Ref<>();
        flyweightCapableTreeStructure.getChildren(node, ref);
        LighterASTNode[] lighterASTNodeArr = ref.get();
        if (lighterASTNodeArr != null) {
            int length = lighterASTNodeArr.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    lighterASTNode = null;
                    break;
                }
                lighterASTNode = lighterASTNodeArr[i];
                if (types.contains(lighterASTNode != null ? lighterASTNode.getTokenType() : null)) {
                    break;
                }
                i++;
            }
            if (lighterASTNode != null) {
                return lighterASTNode;
            }
        }
        LighterASTNode[] lighterASTNodeArr2 = ref.get();
        if (lighterASTNodeArr2 == null) {
            return null;
        }
        int length2 = lighterASTNodeArr2.length;
        for (int i2 = 0; i2 < length2; i2++) {
            LighterASTNode lighterASTNode2 = lighterASTNodeArr2[i2];
            LighterASTNode lighterASTNodeFindDescendantByTypes = lighterASTNode2 != null ? findDescendantByTypes(flyweightCapableTreeStructure, lighterASTNode2, types) : null;
            if (lighterASTNodeFindDescendantByTypes != null) {
                return lighterASTNodeFindDescendantByTypes;
            }
        }
        return null;
    }

    public static final LighterASTNode findFirstDescendant(FlyweightCapableTreeStructure<LighterASTNode> flyweightCapableTreeStructure, LighterASTNode node, Function1<? super LighterASTNode, Boolean> predicate) {
        LighterASTNode lighterASTNode;
        Intrinsics.checkNotNullParameter(flyweightCapableTreeStructure, "<this>");
        Intrinsics.checkNotNullParameter(node, "node");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        Ref<LighterASTNode[]> ref = new Ref<>();
        flyweightCapableTreeStructure.getChildren(node, ref);
        LighterASTNode[] lighterASTNodeArr = ref.get();
        if (lighterASTNodeArr != null) {
            int length = lighterASTNodeArr.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    lighterASTNode = null;
                    break;
                }
                lighterASTNode = lighterASTNodeArr[i];
                if (lighterASTNode != null && predicate.invoke(lighterASTNode).booleanValue()) {
                    break;
                }
                i++;
            }
            if (lighterASTNode != null) {
                return lighterASTNode;
            }
        }
        if (lighterASTNodeArr == null) {
            return null;
        }
        int length2 = lighterASTNodeArr.length;
        for (int i2 = 0; i2 < length2; i2++) {
            LighterASTNode lighterASTNode2 = lighterASTNodeArr[i2];
            LighterASTNode lighterASTNodeFindFirstDescendant = lighterASTNode2 != null ? findFirstDescendant(flyweightCapableTreeStructure, lighterASTNode2, predicate) : null;
            if (lighterASTNodeFindFirstDescendant != null) {
                return lighterASTNodeFindFirstDescendant;
            }
        }
        return null;
    }

    public static final LighterASTNode findLastDescendant(FlyweightCapableTreeStructure<LighterASTNode> flyweightCapableTreeStructure, LighterASTNode node, Function1<? super LighterASTNode, Boolean> predicate) {
        LighterASTNode lighterASTNode;
        Intrinsics.checkNotNullParameter(flyweightCapableTreeStructure, "<this>");
        Intrinsics.checkNotNullParameter(node, "node");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        Ref<LighterASTNode[]> ref = new Ref<>();
        flyweightCapableTreeStructure.getChildren(node, ref);
        LighterASTNode[] lighterASTNodeArr = ref.get();
        if (lighterASTNodeArr != null) {
            int length = lighterASTNodeArr.length - 1;
            if (length < 0) {
                lighterASTNode = null;
                break;
            }
            while (true) {
                int i = length - 1;
                lighterASTNode = lighterASTNodeArr[length];
                if (lighterASTNode != null && predicate.invoke(lighterASTNode).booleanValue()) {
                    break;
                }
                if (i < 0) {
                    lighterASTNode = null;
                    break;
                }
                length = i;
            }
            if (lighterASTNode != null) {
                return lighterASTNode;
            }
        }
        Intrinsics.checkNotNull(lighterASTNodeArr);
        List listReversed = ArraysKt.reversed(lighterASTNodeArr);
        int size = listReversed.size();
        for (int i2 = 0; i2 < size; i2++) {
            LighterASTNode lighterASTNode2 = (LighterASTNode) listReversed.get(i2);
            LighterASTNode lighterASTNodeFindLastDescendant = lighterASTNode2 != null ? findLastDescendant(flyweightCapableTreeStructure, lighterASTNode2, predicate) : null;
            if (lighterASTNodeFindLastDescendant != null) {
                return lighterASTNodeFindLastDescendant;
            }
        }
        return null;
    }

    public static /* synthetic */ List collectDescendantsOfType$default(FlyweightCapableTreeStructure flyweightCapableTreeStructure, LighterASTNode lighterASTNode, IElementType iElementType, Function1 function1, int i, Object obj) {
        if ((i & 4) != 0) {
            function1 = new Function1<LighterASTNode, Boolean>() { // from class: org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategiesKt.collectDescendantsOfType.1
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(LighterASTNode it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return true;
                }
            };
        }
        return collectDescendantsOfType(flyweightCapableTreeStructure, lighterASTNode, iElementType, function1);
    }

    public static final List<LighterASTNode> collectDescendantsOfType(FlyweightCapableTreeStructure<LighterASTNode> flyweightCapableTreeStructure, LighterASTNode node, IElementType type, Function1<? super LighterASTNode, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(flyweightCapableTreeStructure, "<this>");
        Intrinsics.checkNotNullParameter(node, "node");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        ArrayList arrayList = new ArrayList();
        collectDescendantsOfType$collectDescendantByType(flyweightCapableTreeStructure, type, predicate, arrayList, node);
        return arrayList;
    }

    private static final void collectDescendantsOfType$collectDescendantByType(FlyweightCapableTreeStructure<LighterASTNode> flyweightCapableTreeStructure, IElementType iElementType, Function1<? super LighterASTNode, Boolean> function1, List<LighterASTNode> list, LighterASTNode lighterASTNode) {
        Ref<LighterASTNode[]> ref = new Ref<>();
        flyweightCapableTreeStructure.getChildren(lighterASTNode, ref);
        LighterASTNode[] lighterASTNodeArr = ref.get();
        if (lighterASTNodeArr != null) {
            int length = lighterASTNodeArr.length;
            for (int i = 0; i < length; i++) {
                LighterASTNode lighterASTNode2 = lighterASTNodeArr[i];
                if (Intrinsics.areEqual(lighterASTNode2 != null ? lighterASTNode2.getTokenType() : null, iElementType) && function1.invoke(lighterASTNode2).booleanValue()) {
                    list.add(lighterASTNode2);
                }
                if (lighterASTNode2 != null) {
                    collectDescendantsOfType$collectDescendantByType(flyweightCapableTreeStructure, iElementType, function1, list, lighterASTNode2);
                }
            }
        }
    }

    private static final void traverseDescendants$traverse(FlyweightCapableTreeStructure<LighterASTNode> flyweightCapableTreeStructure, Function1<? super LighterASTNode, Boolean> function1, LighterASTNode lighterASTNode) {
        Ref<LighterASTNode[]> ref = new Ref<>();
        flyweightCapableTreeStructure.getChildren(lighterASTNode, ref);
        LighterASTNode[] lighterASTNodeArr = ref.get();
        if (lighterASTNodeArr != null) {
            for (LighterASTNode lighterASTNode2 : lighterASTNodeArr) {
                if (lighterASTNode2 != null && function1.invoke(lighterASTNode2).booleanValue()) {
                    traverseDescendants$traverse(flyweightCapableTreeStructure, function1, lighterASTNode2);
                }
            }
        }
    }

    public static final void traverseDescendants(FlyweightCapableTreeStructure<LighterASTNode> flyweightCapableTreeStructure, LighterASTNode node, Function1<? super LighterASTNode, Boolean> acceptor) {
        Intrinsics.checkNotNullParameter(flyweightCapableTreeStructure, "<this>");
        Intrinsics.checkNotNullParameter(node, "node");
        Intrinsics.checkNotNullParameter(acceptor, "acceptor");
        traverseDescendants$traverse(flyweightCapableTreeStructure, acceptor, node);
    }

    public static final LighterASTNode findChildByType(FlyweightCapableTreeStructure<LighterASTNode> flyweightCapableTreeStructure, LighterASTNode node, TokenSet type) {
        Intrinsics.checkNotNullParameter(flyweightCapableTreeStructure, "<this>");
        Intrinsics.checkNotNullParameter(node, "node");
        Intrinsics.checkNotNullParameter(type, "type");
        Ref<LighterASTNode[]> ref = new Ref<>();
        flyweightCapableTreeStructure.getChildren(node, ref);
        LighterASTNode[] lighterASTNodeArr = ref.get();
        if (lighterASTNodeArr == null) {
            return null;
        }
        int length = lighterASTNodeArr.length;
        for (int i = 0; i < length; i++) {
            LighterASTNode lighterASTNode = lighterASTNodeArr[i];
            if (type.contains(lighterASTNode != null ? lighterASTNode.getTokenType() : null)) {
                return lighterASTNode;
            }
        }
        return null;
    }

    static /* synthetic */ LighterASTNode findParentOfType$default(FlyweightCapableTreeStructure flyweightCapableTreeStructure, LighterASTNode lighterASTNode, IElementType iElementType, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = true;
        }
        return findParentOfType(flyweightCapableTreeStructure, lighterASTNode, iElementType, z);
    }

    private static final LighterASTNode findParentOfType(FlyweightCapableTreeStructure<LighterASTNode> flyweightCapableTreeStructure, LighterASTNode lighterASTNode, IElementType iElementType, boolean z) {
        if (!z && Intrinsics.areEqual(lighterASTNode.getTokenType(), iElementType)) {
            return lighterASTNode;
        }
        LighterASTNode parent = flyweightCapableTreeStructure.getParent(lighterASTNode);
        while (parent != null) {
            if (Intrinsics.areEqual(parent.getTokenType(), iElementType)) {
                return parent;
            }
            parent = flyweightCapableTreeStructure.getParent(parent);
        }
        return null;
    }

    public static final Sequence<LighterASTNode> getAncestors(final FlyweightCapableTreeStructure<LighterASTNode> flyweightCapableTreeStructure, LighterASTNode node) {
        Intrinsics.checkNotNullParameter(flyweightCapableTreeStructure, "<this>");
        Intrinsics.checkNotNullParameter(node, "node");
        return SequencesKt.generateSequence(flyweightCapableTreeStructure.getParent(node), new Function1<LighterASTNode, LighterASTNode>() { // from class: org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategiesKt.getAncestors.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final LighterASTNode invoke(LighterASTNode it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return flyweightCapableTreeStructure.getParent(it);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final LighterASTNode firstChild(FlyweightCapableTreeStructure<LighterASTNode> flyweightCapableTreeStructure, LighterASTNode lighterASTNode) {
        Ref<LighterASTNode[]> ref = new Ref<>();
        flyweightCapableTreeStructure.getChildren(lighterASTNode, ref);
        LighterASTNode[] lighterASTNodeArr = ref.get();
        if (lighterASTNodeArr != null) {
            return (LighterASTNode) ArraysKt.firstOrNull(lighterASTNodeArr);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final LighterASTNode lastChild(FlyweightCapableTreeStructure<LighterASTNode> flyweightCapableTreeStructure, LighterASTNode lighterASTNode) {
        LighterASTNode lighterASTNode2;
        Ref<LighterASTNode[]> ref = new Ref<>();
        flyweightCapableTreeStructure.getChildren(lighterASTNode, ref);
        LighterASTNode[] lighterASTNodeArr = ref.get();
        Intrinsics.checkNotNullExpressionValue(lighterASTNodeArr, "get(...)");
        LighterASTNode[] lighterASTNodeArr2 = lighterASTNodeArr;
        int length = lighterASTNodeArr2.length - 1;
        if (length < 0) {
            lighterASTNode2 = null;
            break;
        }
        while (true) {
            int i = length - 1;
            lighterASTNode2 = lighterASTNodeArr2[length];
            if (lighterASTNode2 != null) {
                break;
            }
            if (i < 0) {
                lighterASTNode2 = null;
                break;
            }
            length = i;
        }
        return lighterASTNode2;
    }
}
