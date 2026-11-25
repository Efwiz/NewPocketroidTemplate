package org.jetbrains.kotlin.fir.lightTree.converter;

import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Triple;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.text.StringsKt;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.ElementTypeUtils;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtLightSourceElement;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceElementKt;
import org.jetbrains.kotlin.cli.common.arguments.PreprocessCommandLineArgumentsKt;
import org.jetbrains.kotlin.com.google.common.collect.ImmutableSet;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.psi.TokenType;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.util.diff.FlyweightCapableTreeStructure;
import org.jetbrains.kotlin.descriptors.EffectiveVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.FirAnnotationContainer;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirExpressionRef;
import org.jetbrains.kotlin.fir.FirFunctionTarget;
import org.jetbrains.kotlin.fir.FirGenerationKt;
import org.jetbrains.kotlin.fir.FirLabel;
import org.jetbrains.kotlin.fir.FirLoopTarget;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.builder.AbstractRawFirBuilder;
import org.jetbrains.kotlin.fir.builder.CalleeAndReceiver;
import org.jetbrains.kotlin.fir.builder.Context;
import org.jetbrains.kotlin.fir.builder.ConversionUtilsKt;
import org.jetbrains.kotlin.fir.builder.FirLabelBuilder;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.FirVariable;
import org.jetbrains.kotlin.fir.declarations.builder.FirAnonymousFunctionBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirPropertyBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirValueParameterBuilder;
import org.jetbrains.kotlin.fir.declarations.impl.FirDeclarationStatusImpl;
import org.jetbrains.kotlin.fir.declarations.impl.FirResolvedDeclarationStatusImpl;
import org.jetbrains.kotlin.fir.diagnostics.ConeNotAnnotationContainer;
import org.jetbrains.kotlin.fir.diagnostics.ConeSimpleDiagnostic;
import org.jetbrains.kotlin.fir.diagnostics.ConeSyntaxDiagnostic;
import org.jetbrains.kotlin.fir.diagnostics.ConeUnderscoreUsageWithoutBackticks;
import org.jetbrains.kotlin.fir.diagnostics.ConeUnsupportedClassLiteralsWithEmptyLhs;
import org.jetbrains.kotlin.fir.diagnostics.DiagnosticKind;
import org.jetbrains.kotlin.fir.expressions.FirArgumentUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirBlock;
import org.jetbrains.kotlin.fir.expressions.FirCatch;
import org.jetbrains.kotlin.fir.expressions.FirErrorExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpressionUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCallOrigin;
import org.jetbrains.kotlin.fir.expressions.FirOperation;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.expressions.FirTypeOperatorCall;
import org.jetbrains.kotlin.fir.expressions.FirWhenBranch;
import org.jetbrains.kotlin.fir.expressions.FirWhenExpression;
import org.jetbrains.kotlin.fir.expressions.FirWhenSubjectExpression;
import org.jetbrains.kotlin.fir.expressions.builder.FirAbstractFunctionCallBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirAnonymousFunctionExpressionBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirArgumentListBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirArrayLiteralBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirBlockBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirBreakExpressionBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirCallableReferenceAccessBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirCatchBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirCheckNotNullCallBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirContinueExpressionBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirDoWhileLoopBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirElseIfTrueConditionBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirEmptyExpressionBlockBuilderKt;
import org.jetbrains.kotlin.fir.expressions.builder.FirEqualityOperatorCallBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirErrorExpressionBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirFunctionCallBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirGetClassCallBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirImplicitInvokeCallBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirLambdaArgumentExpressionBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirLoopJumpBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirNamedArgumentExpressionBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirPropertyAccessExpressionBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirQualifiedErrorAccessExpressionBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirReturnExpressionBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirSpreadArgumentExpressionBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirThisReceiverExpressionBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirThrowExpressionBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirTryExpressionBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirTypeOperatorCallBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirUnitExpressionBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirWhenBranchBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirWhenExpressionBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirWhenSubjectExpressionBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirWhileLoopBuilder;
import org.jetbrains.kotlin.fir.expressions.impl.FirContractCallBlock;
import org.jetbrains.kotlin.fir.expressions.impl.FirSingleExpressionBlock;
import org.jetbrains.kotlin.fir.lightTree.fir.DestructuringDeclaration;
import org.jetbrains.kotlin.fir.lightTree.fir.ValueParameter;
import org.jetbrains.kotlin.fir.lightTree.fir.WhenEntry;
import org.jetbrains.kotlin.fir.references.FirNamedReference;
import org.jetbrains.kotlin.fir.references.builder.FirErrorNamedReferenceBuilder;
import org.jetbrains.kotlin.fir.references.builder.FirExplicitSuperReferenceBuilder;
import org.jetbrains.kotlin.fir.references.builder.FirExplicitThisReferenceBuilder;
import org.jetbrains.kotlin.fir.references.builder.FirSimpleNamedReferenceBuilder;
import org.jetbrains.kotlin.fir.symbols.impl.FirAnonymousFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirValueParameterSymbol;
import org.jetbrains.kotlin.fir.types.FirErrorTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeProjection;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.builder.FirErrorTypeRefBuilder;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.lexer.KtSingleValueToken;
import org.jetbrains.kotlin.lexer.KtToken;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.psi.stubs.elements.KtConstantExpressionElementType;
import org.jetbrains.kotlin.psi.stubs.elements.KtNameReferenceExpressionElementType;
import org.jetbrains.kotlin.types.expressions.OperatorConventions;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: LightTreeRawFirExpressionBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000¶\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0010 \n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001:\u0002yzB3\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00060\n¢\u0006\u0002\u0010\u000bJ\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0006H\u0002J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0006H\u0002J\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0006H\u0002J)\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0018\u001a\u00020\u00062\u0017\u0010\u001b\u001a\u0013\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020\u001e0\u001c¢\u0006\u0002\b\u001fH\u0002J\u0010\u0010 \u001a\u00020\u00142\u0006\u0010!\u001a\u00020\u0006H\u0002J\u0010\u0010\"\u001a\u00020\u00142\u0006\u0010#\u001a\u00020\u0006H\u0002J&\u0010$\u001a\u0018\u0012\u0006\u0012\u0004\u0018\u00010&\u0012\u0004\u0012\u00020'\u0012\u0004\u0012\u00020(\u0018\u00010%2\u0006\u0010)\u001a\u00020\u0006H\u0002J\u0010\u0010*\u001a\u00020\u00142\u0006\u0010+\u001a\u00020\u0006H\u0002J\u0010\u0010,\u001a\u00020\u00142\u0006\u0010-\u001a\u00020\u0006H\u0002J\u0010\u0010.\u001a\u00020\u00142\u0006\u0010/\u001a\u00020\u0006H\u0002J\u0010\u00100\u001a\u00020\u00112\u0006\u00101\u001a\u00020\u0006H\u0002J\u0016\u00102\u001a\u00020\u00112\u0006\u0010-\u001a\u00020\u00062\u0006\u00103\u001a\u00020\u001dJ\u0010\u00104\u001a\u00020'2\u0006\u00105\u001a\u00020\u0006H\u0002J\u0010\u00106\u001a\u00020\u00112\u0006\u00107\u001a\u00020\u0006H\u0002J\u0010\u00108\u001a\u00020\u00142\u0006\u00109\u001a\u00020\u0006H\u0002J\u0016\u0010:\u001a\b\u0012\u0004\u0012\u00020\u00140;2\u0006\u0010<\u001a\u00020\u0006H\u0002J\u0010\u0010=\u001a\u00020\u00112\u0006\u0010>\u001a\u00020\u0006H\u0002J\u0010\u0010?\u001a\u00020\u00142\u0006\u0010@\u001a\u00020\u0006H\u0002J\u0012\u0010A\u001a\u00020'2\b\u0010B\u001a\u0004\u0018\u00010\u0006H\u0002J\u0010\u0010C\u001a\u00020\u00142\u0006\u0010D\u001a\u00020\u0006H\u0002J\u0014\u0010E\u001a\u0004\u0018\u00010'2\b\u0010B\u001a\u0004\u0018\u00010\u0006H\u0002J\u0010\u0010F\u001a\u00020\u00142\u0006\u0010G\u001a\u00020\u0006H\u0002J\u0010\u0010H\u001a\u00020\u00142\u0006\u0010I\u001a\u00020\u0006H\u0002J\u0010\u0010J\u001a\u00020K2\u0006\u0010L\u001a\u00020\u0006H\u0002J\u0010\u0010M\u001a\u00020\u00142\u0006\u0010N\u001a\u00020\u0006H\u0002J\u0010\u0010O\u001a\u00020K2\u0006\u0010P\u001a\u00020\u0006H\u0002J\u0010\u0010Q\u001a\u00020K2\u0006\u0010R\u001a\u00020\u0006H\u0002J\u0010\u0010S\u001a\u00020\u00142\u0006\u0010T\u001a\u00020\u0006H\u0002J\u0010\u0010U\u001a\u00020\u00142\u0006\u0010V\u001a\u00020\u0006H\u0002J\u0010\u0010W\u001a\u00020\u00142\u0006\u0010X\u001a\u00020\u0006H\u0002J\u0010\u0010Y\u001a\u00020\u00142\u0006\u0010Z\u001a\u00020\u0006H\u0002J\u0014\u0010[\u001a\b\u0012\u0004\u0012\u00020\u00140;2\u0006\u0010\\\u001a\u00020\u0006J \u0010]\u001a\u00020\u00142\u0006\u0010^\u001a\u00020\u00062\u000e\u0010_\u001a\n\u0012\u0004\u0012\u00020a\u0018\u00010`H\u0002J&\u0010b\u001a\u00020c2\u0006\u0010^\u001a\u00020\u00062\f\u0010_\u001a\b\u0012\u0004\u0012\u00020a0`2\u0006\u0010d\u001a\u00020\rH\u0002J&\u0010e\u001a\u00020c2\u0006\u0010^\u001a\u00020\u00062\f\u0010_\u001a\b\u0012\u0004\u0012\u00020a0`2\u0006\u0010d\u001a\u00020\rH\u0002J&\u0010f\u001a\u00020g2\u0006\u0010h\u001a\u00020\u00062\f\u0010_\u001a\b\u0012\u0004\u0012\u00020a0`2\u0006\u0010d\u001a\u00020\rH\u0002J\u0010\u0010i\u001a\u00020\u00142\u0006\u0010j\u001a\u00020\u0006H\u0002J\u0010\u0010k\u001a\u00020\u00112\u0006\u0010l\u001a\u00020\u0006H\u0002J\u0018\u0010m\u001a\u00020n2\u0006\u0010o\u001a\u00020p2\u0006\u0010L\u001a\u00020\u0006H\u0002J \u0010q\u001a\u00020c2\u0006\u0010d\u001a\u00020\r2\u0006\u0010r\u001a\u00020\u00142\u0006\u0010^\u001a\u00020\u0006H\u0002J.\u0010s\u001a\u0002Ht\"\n\b\u0000\u0010t\u0018\u0001*\u00020\u00112\b\u0010-\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u00103\u001a\u00020\u001dH\u0086\b¢\u0006\u0002\u0010uJ\u0010\u0010v\u001a\u00020w2\u0006\u00109\u001a\u00020\u0006H\u0002J\u0016\u0010x\u001a\u00020\u0014*\u0004\u0018\u00010\u00062\u0006\u00103\u001a\u00020\u001dH\u0002R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\f\u001a\u00020\r*\u00020\u00068BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000f¨\u0006{"}, d2 = {"Lorg/jetbrains/kotlin/fir/lightTree/converter/LightTreeRawFirExpressionBuilder;", "Lorg/jetbrains/kotlin/fir/lightTree/converter/AbstractLightTreeRawFirBuilder;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", Constants.TYPE_TREE, "Lorg/jetbrains/kotlin/com/intellij/util/diff/FlyweightCapableTreeStructure;", "Lorg/jetbrains/kotlin/com/intellij/lang/LighterASTNode;", "declarationBuilder", "Lorg/jetbrains/kotlin/fir/lightTree/converter/LightTreeRawFirDeclarationBuilder;", "context", "Lorg/jetbrains/kotlin/fir/builder/Context;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lcom/intellij/util/diff/FlyweightCapableTreeStructure;Lorg/jetbrains/kotlin/fir/lightTree/converter/LightTreeRawFirDeclarationBuilder;Lorg/jetbrains/kotlin/fir/builder/Context;)V", "usedAsExpression", "", "getUsedAsExpression", "(Lcom/intellij/lang/LighterASTNode;)Z", "convertAnnotatedExpression", "Lorg/jetbrains/kotlin/fir/FirElement;", "annotatedExpression", "convertArrayAccessExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "arrayAccess", "convertBinaryExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "binaryExpression", "convertBinaryWithTypeRHSExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirTypeOperatorCall;", "toFirOperation", "Lkotlin/Function1;", "", "Lorg/jetbrains/kotlin/fir/expressions/FirOperation;", "Lkotlin/ExtensionFunctionType;", "convertCallExpression", "callSuffix", "convertCallableReferenceExpression", "callableReferenceExpression", "convertCatchClause", "Lkotlin/Triple;", "Lorg/jetbrains/kotlin/fir/lightTree/fir/ValueParameter;", "Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "Lorg/jetbrains/kotlin/KtLightSourceElement;", "catchClause", "convertClassLiteralExpression", "classLiteralExpression", "convertCollectionLiteralExpression", "expression", "convertConstantExpression", "constantExpression", "convertDoWhile", "doWhileLoop", "convertExpression", HiAnalyticsConstant.HaKey.BI_KEY_ERRORREASON, "convertFinally", "finallyExpression", "convertFor", "forLoop", "convertIfExpression", "ifExpression", "convertIndices", "", "indices", "convertLabeledExpression", "labeledExpression", "convertLambdaExpression", "lambdaExpression", "convertLoopBody", "body", "convertLoopJump", "jump", "convertLoopOrIfBody", "convertQualifiedExpression", "dotQualifiedExpression", "convertReturn", "returnExpression", "convertSimpleNameExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;", "referenceExpression", "convertStringTemplate", "stringTemplate", "convertSuperExpression", "superExpression", "convertThisExpression", "thisExpression", "convertThrow", "throwExpression", "convertTryExpression", "tryExpression", "convertUnaryExpression", "unaryExpression", "convertValueArgument", "valueArgument", "convertValueArguments", "valueArguments", "convertWhenConditionExpression", "whenCondition", "whenRefWithSubject", "Lorg/jetbrains/kotlin/fir/FirExpressionRef;", "Lorg/jetbrains/kotlin/fir/expressions/FirWhenExpression;", "convertWhenConditionInRange", "Lorg/jetbrains/kotlin/fir/lightTree/converter/LightTreeRawFirExpressionBuilder$WhenConditionConvertedResults;", "hasSubject", "convertWhenConditionIsPattern", "convertWhenEntry", "Lorg/jetbrains/kotlin/fir/lightTree/fir/WhenEntry;", "whenEntry", "convertWhenExpression", "whenExpression", "convertWhile", "whileLoop", "createSimpleNamedReference", "Lorg/jetbrains/kotlin/fir/references/FirNamedReference;", "sourceElement", "Lorg/jetbrains/kotlin/KtSourceElement;", "createWhenConditionConvertedResults", "result", "getAsFirExpression", "R", "(Lcom/intellij/lang/LighterASTNode;Ljava/lang/String;)Lorg/jetbrains/kotlin/fir/FirElement;", "parseIfExpression", "Lorg/jetbrains/kotlin/fir/lightTree/converter/LightTreeRawFirExpressionBuilder$IfNodeComponents;", "convertShortOrLongStringTemplate", "IfNodeComponents", "WhenConditionConvertedResults", "light-tree2fir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LightTreeRawFirExpressionBuilder extends AbstractLightTreeRawFirBuilder {
    private final LightTreeRawFirDeclarationBuilder declarationBuilder;

    public /* synthetic */ LightTreeRawFirExpressionBuilder(FirSession firSession, FlyweightCapableTreeStructure flyweightCapableTreeStructure, LightTreeRawFirDeclarationBuilder lightTreeRawFirDeclarationBuilder, Context context, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(firSession, flyweightCapableTreeStructure, lightTreeRawFirDeclarationBuilder, (i & 8) != 0 ? new Context() : context);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LightTreeRawFirExpressionBuilder(FirSession session, FlyweightCapableTreeStructure<LighterASTNode> tree, LightTreeRawFirDeclarationBuilder declarationBuilder, Context<LighterASTNode> context) {
        super(session, tree, context);
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(tree, "tree");
        Intrinsics.checkNotNullParameter(declarationBuilder, "declarationBuilder");
        Intrinsics.checkNotNullParameter(context, "context");
        this.declarationBuilder = declarationBuilder;
    }

    public static /* synthetic */ FirElement getAsFirExpression$default(LightTreeRawFirExpressionBuilder lightTreeRawFirExpressionBuilder, LighterASTNode lighterASTNode, String errorReason, int i, Object obj) {
        ConeSimpleDiagnostic coneSimpleDiagnostic;
        if ((i & 2) != 0) {
            errorReason = "";
        }
        Intrinsics.checkNotNullParameter(errorReason, "errorReason");
        FirElement firElementConvertExpression = lighterASTNode != null ? lightTreeRawFirExpressionBuilder.convertExpression(lighterASTNode, errorReason) : null;
        Intrinsics.reifiedOperationMarker(2, "R");
        if (firElementConvertExpression != null) {
            return firElementConvertExpression;
        }
        KtLightSourceElement ktLightSourceElement = lighterASTNode != null ? (KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(lightTreeRawFirExpressionBuilder, lighterASTNode, null, 1, null) : null;
        if (lighterASTNode == null) {
            coneSimpleDiagnostic = new ConeSyntaxDiagnostic(errorReason);
        } else {
            coneSimpleDiagnostic = new ConeSimpleDiagnostic(errorReason, DiagnosticKind.ExpressionExpected);
        }
        FirErrorExpression firErrorExpressionBuildErrorExpression = FirExpressionUtilKt.buildErrorExpression(ktLightSourceElement, coneSimpleDiagnostic, firElementConvertExpression);
        Intrinsics.reifiedOperationMarker(1, "R");
        return firErrorExpressionBuildErrorExpression;
    }

    public final /* synthetic */ <R extends FirElement> R getAsFirExpression(LighterASTNode lighterASTNode, String errorReason) {
        ConeSimpleDiagnostic coneSimpleDiagnostic;
        Intrinsics.checkNotNullParameter(errorReason, "errorReason");
        R r = lighterASTNode != null ? (R) convertExpression(lighterASTNode, errorReason) : null;
        Intrinsics.reifiedOperationMarker(2, "R");
        if (r != null) {
            return r;
        }
        KtLightSourceElement ktLightSourceElement = lighterASTNode != null ? (KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(this, lighterASTNode, null, 1, null) : null;
        if (lighterASTNode == null) {
            coneSimpleDiagnostic = new ConeSyntaxDiagnostic(errorReason);
        } else {
            coneSimpleDiagnostic = new ConeSimpleDiagnostic(errorReason, DiagnosticKind.ExpressionExpected);
        }
        FirErrorExpression firErrorExpressionBuildErrorExpression = FirExpressionUtilKt.buildErrorExpression(ktLightSourceElement, coneSimpleDiagnostic, r);
        Intrinsics.reifiedOperationMarker(1, "R");
        return firErrorExpressionBuildErrorExpression;
    }

    public final FirElement convertExpression(LighterASTNode expression, String errorReason) {
        FirElement firElement;
        ConeSimpleDiagnostic coneSimpleDiagnostic;
        ConeSimpleDiagnostic coneSimpleDiagnostic2;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(errorReason, "errorReason");
        IElementType tokenType = expression.getTokenType();
        if (Intrinsics.areEqual(tokenType, KtNodeTypes.LAMBDA_EXPRESSION)) {
            return convertLambdaExpression(expression);
        }
        if (Intrinsics.areEqual(tokenType, KtNodeTypes.BINARY_EXPRESSION)) {
            return convertBinaryExpression(expression);
        }
        if (Intrinsics.areEqual(tokenType, KtNodeTypes.BINARY_WITH_TYPE)) {
            return convertBinaryWithTypeRHSExpression(expression, new Function1<String, FirOperation>() { // from class: org.jetbrains.kotlin.fir.lightTree.converter.LightTreeRawFirExpressionBuilder.convertExpression.1
                @Override // kotlin.jvm.functions.Function1
                public final FirOperation invoke(String convertBinaryWithTypeRHSExpression) {
                    Intrinsics.checkNotNullParameter(convertBinaryWithTypeRHSExpression, "$this$convertBinaryWithTypeRHSExpression");
                    return ConversionUtilsKt.toFirOperation(ElementTypeUtils.INSTANCE.getOperationSymbol(convertBinaryWithTypeRHSExpression));
                }
            });
        }
        if (Intrinsics.areEqual(tokenType, KtNodeTypes.IS_EXPRESSION)) {
            return convertBinaryWithTypeRHSExpression(expression, new Function1<String, FirOperation>() { // from class: org.jetbrains.kotlin.fir.lightTree.converter.LightTreeRawFirExpressionBuilder.convertExpression.2
                @Override // kotlin.jvm.functions.Function1
                public final FirOperation invoke(String convertBinaryWithTypeRHSExpression) {
                    Intrinsics.checkNotNullParameter(convertBinaryWithTypeRHSExpression, "$this$convertBinaryWithTypeRHSExpression");
                    return Intrinsics.areEqual(convertBinaryWithTypeRHSExpression, LanguageCodeUtil.IS) ? FirOperation.IS : FirOperation.NOT_IS;
                }
            });
        }
        if (Intrinsics.areEqual(tokenType, KtNodeTypes.LABELED_EXPRESSION)) {
            return convertLabeledExpression(expression);
        }
        if (Intrinsics.areEqual(tokenType, KtNodeTypes.PREFIX_EXPRESSION) ? true : Intrinsics.areEqual(tokenType, KtNodeTypes.POSTFIX_EXPRESSION)) {
            return convertUnaryExpression(expression);
        }
        if (Intrinsics.areEqual(tokenType, KtNodeTypes.ANNOTATED_EXPRESSION)) {
            return convertAnnotatedExpression(expression);
        }
        if (Intrinsics.areEqual(tokenType, KtNodeTypes.CLASS_LITERAL_EXPRESSION)) {
            return convertClassLiteralExpression(expression);
        }
        if (Intrinsics.areEqual(tokenType, KtNodeTypes.CALLABLE_REFERENCE_EXPRESSION)) {
            return convertCallableReferenceExpression(expression);
        }
        if (KtTokens.QUALIFIED_ACCESS.contains(tokenType)) {
            return convertQualifiedExpression(expression);
        }
        if (Intrinsics.areEqual(tokenType, KtNodeTypes.CALL_EXPRESSION)) {
            return convertCallExpression(expression);
        }
        if (Intrinsics.areEqual(tokenType, KtNodeTypes.WHEN)) {
            return convertWhenExpression(expression);
        }
        if (Intrinsics.areEqual(tokenType, KtNodeTypes.ARRAY_ACCESS_EXPRESSION)) {
            return convertArrayAccessExpression(expression);
        }
        if (Intrinsics.areEqual(tokenType, KtNodeTypes.COLLECTION_LITERAL_EXPRESSION)) {
            return convertCollectionLiteralExpression(expression);
        }
        if (Intrinsics.areEqual(tokenType, KtNodeTypes.STRING_TEMPLATE)) {
            return convertStringTemplate(expression);
        }
        if (tokenType instanceof KtConstantExpressionElementType) {
            return convertConstantExpression(expression);
        }
        if (Intrinsics.areEqual(tokenType, KtNodeTypes.REFERENCE_EXPRESSION)) {
            return convertSimpleNameExpression(expression);
        }
        if (Intrinsics.areEqual(tokenType, KtNodeTypes.DO_WHILE)) {
            return convertDoWhile(expression);
        }
        if (Intrinsics.areEqual(tokenType, KtNodeTypes.WHILE)) {
            return convertWhile(expression);
        }
        if (Intrinsics.areEqual(tokenType, KtNodeTypes.FOR)) {
            return convertFor(expression);
        }
        if (Intrinsics.areEqual(tokenType, KtNodeTypes.TRY)) {
            return convertTryExpression(expression);
        }
        if (Intrinsics.areEqual(tokenType, KtNodeTypes.IF)) {
            return convertIfExpression(expression);
        }
        if (Intrinsics.areEqual(tokenType, KtNodeTypes.BREAK) ? true : Intrinsics.areEqual(tokenType, KtNodeTypes.CONTINUE)) {
            return convertLoopJump(expression);
        }
        if (Intrinsics.areEqual(tokenType, KtNodeTypes.RETURN)) {
            return convertReturn(expression);
        }
        if (Intrinsics.areEqual(tokenType, KtNodeTypes.THROW)) {
            return convertThrow(expression);
        }
        if (Intrinsics.areEqual(tokenType, KtNodeTypes.PARENTHESIZED)) {
            LighterASTNode expressionInParentheses = getExpressionInParentheses(expression);
            getContext().forwardLabelUsagePermission(expression, expressionInParentheses);
            FirElement firElementConvertExpression = expressionInParentheses != null ? convertExpression(expressionInParentheses, "Empty parentheses") : null;
            firElement = !(firElementConvertExpression instanceof FirElement) ? null : firElementConvertExpression;
            if (firElement == null) {
                KtLightSourceElement ktLightSourceElement = expressionInParentheses != null ? (KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(this, expressionInParentheses, null, 1, null) : null;
                if (expressionInParentheses == null) {
                    coneSimpleDiagnostic2 = new ConeSyntaxDiagnostic("Empty parentheses");
                } else {
                    coneSimpleDiagnostic2 = new ConeSimpleDiagnostic("Empty parentheses", DiagnosticKind.ExpressionExpected);
                }
                FirErrorExpression firErrorExpressionBuildErrorExpression = FirExpressionUtilKt.buildErrorExpression(ktLightSourceElement, coneSimpleDiagnostic2, firElementConvertExpression);
                if (firErrorExpressionBuildErrorExpression != null) {
                    return firErrorExpressionBuildErrorExpression;
                }
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.FirElement");
            }
        } else {
            if (Intrinsics.areEqual(tokenType, KtNodeTypes.PROPERTY_DELEGATE) ? true : Intrinsics.areEqual(tokenType, KtNodeTypes.INDICES) ? true : Intrinsics.areEqual(tokenType, KtNodeTypes.CONDITION) ? true : Intrinsics.areEqual(tokenType, KtNodeTypes.LOOP_RANGE)) {
                LighterASTNode expressionInParentheses2 = getExpressionInParentheses(expression);
                FirElement firElementConvertExpression2 = expressionInParentheses2 != null ? convertExpression(expressionInParentheses2, errorReason) : null;
                firElement = !(firElementConvertExpression2 instanceof FirElement) ? null : firElementConvertExpression2;
                if (firElement == null) {
                    KtLightSourceElement ktLightSourceElement2 = expressionInParentheses2 != null ? (KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(this, expressionInParentheses2, null, 1, null) : null;
                    if (expressionInParentheses2 == null) {
                        coneSimpleDiagnostic = new ConeSyntaxDiagnostic(errorReason);
                    } else {
                        coneSimpleDiagnostic = new ConeSimpleDiagnostic(errorReason, DiagnosticKind.ExpressionExpected);
                    }
                    FirErrorExpression firErrorExpressionBuildErrorExpression2 = FirExpressionUtilKt.buildErrorExpression(ktLightSourceElement2, coneSimpleDiagnostic, firElementConvertExpression2);
                    if (firErrorExpressionBuildErrorExpression2 != null) {
                        return firErrorExpressionBuildErrorExpression2;
                    }
                    throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.FirElement");
                }
            } else {
                if (Intrinsics.areEqual(tokenType, KtNodeTypes.THIS_EXPRESSION)) {
                    return convertThisExpression(expression);
                }
                if (Intrinsics.areEqual(tokenType, KtNodeTypes.SUPER_EXPRESSION)) {
                    return convertSuperExpression(expression);
                }
                if (Intrinsics.areEqual(tokenType, KtNodeTypes.OBJECT_LITERAL)) {
                    return this.declarationBuilder.convertObjectLiteral(expression);
                }
                if (Intrinsics.areEqual(tokenType, KtNodeTypes.FUN)) {
                    return this.declarationBuilder.convertFunctionDeclaration(expression);
                }
                return Intrinsics.areEqual(tokenType, KtNodeTypes.DESTRUCTURING_DECLARATION) ? this.declarationBuilder.convertDestructingDeclaration$light_tree2fir(expression).toFirDestructingDeclaration(getBaseModuleData()) : FirExpressionUtilKt.buildErrorExpression$default(toFirSourceElement(expression, (KtFakeSourceElementKind) KtFakeSourceElementKind.ErrorTypeRef.INSTANCE), new ConeSimpleDiagnostic(errorReason, DiagnosticKind.ExpressionExpected), null, 4, null);
            }
        }
        return firElement;
    }

    private final FirExpression convertLambdaExpression(LighterASTNode lambdaExpression) throws Throwable {
        FirSingleExpressionBlock firSingleExpressionBlock;
        FirValueParameter firValueParameter;
        LighterASTNode lighterASTNode;
        ArrayList<ValueParameter> arrayList = new ArrayList();
        FirAnonymousFunctionSymbol firAnonymousFunctionSymbol = new FirAnonymousFunctionSymbol();
        IElementType FUNCTION_LITERAL = KtNodeTypes.FUNCTION_LITERAL;
        Intrinsics.checkNotNullExpressionValue(FUNCTION_LITERAL, "FUNCTION_LITERAL");
        KtToken[] ktTokenArr = new KtToken[0];
        LighterASTNode[] childrenAsArray = getChildrenAsArray((LighterASTNode) CollectionsKt.first((List) getChildNodesByType(lambdaExpression, FUNCTION_LITERAL)));
        int length = childrenAsArray.length;
        boolean z = false;
        LighterASTNode lighterASTNode2 = null;
        for (int i = 0; i < length && (lighterASTNode = childrenAsArray[i]) != null; i++) {
            IElementType tokenType = lighterASTNode.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr, tokenType) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT)) {
                IElementType tokenType2 = lighterASTNode.getTokenType();
                if (Intrinsics.areEqual(tokenType2, KtNodeTypes.VALUE_PARAMETER_LIST)) {
                    CollectionsKt.addAll(arrayList, LightTreeRawFirDeclarationBuilder.convertValueParameters$default(this.declarationBuilder, lighterASTNode, firAnonymousFunctionSymbol, AbstractRawFirBuilder.ValueParameterDeclaration.LAMBDA, null, 8, null));
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.BLOCK)) {
                    lighterASTNode2 = lighterASTNode;
                } else if (Intrinsics.areEqual(tokenType2, KtTokens.ARROW)) {
                    z = true;
                }
            }
        }
        KtLightSourceElement ktLightSourceElement = (KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(this, lambdaExpression, null, 1, null);
        FirAnonymousFunctionBuilder firAnonymousFunctionBuilder = new FirAnonymousFunctionBuilder();
        KtLightSourceElement ktLightSourceElement2 = ktLightSourceElement;
        firAnonymousFunctionBuilder.setSource(ktLightSourceElement2);
        firAnonymousFunctionBuilder.setModuleData(getBaseModuleData());
        firAnonymousFunctionBuilder.setOrigin(FirDeclarationOrigin.Source.INSTANCE);
        firAnonymousFunctionBuilder.setReturnTypeRef(getImplicitType());
        firAnonymousFunctionBuilder.setReceiverParameter(ConversionUtilsKt.asReceiverParameter(ktLightSourceElement2));
        firAnonymousFunctionBuilder.setSymbol(firAnonymousFunctionSymbol);
        firAnonymousFunctionBuilder.setLambda(true);
        firAnonymousFunctionBuilder.setHasExplicitParameterList(z);
        FirLabel lastLabel = getContext().getLastLabel(lambdaExpression);
        if (lastLabel == null) {
            Name name2 = (Name) CollectionsKt.lastOrNull((List) getContext().getCalleeNamesForLambda());
            if (name2 != null) {
                FirLabelBuilder firLabelBuilder = new FirLabelBuilder();
                firLabelBuilder.setSource(KtSourceElementKt.fakeElement$default(ktLightSourceElement2, KtFakeSourceElementKind.GeneratedLambdaLabel.INSTANCE, 0, 0, 6, null));
                String strAsString = name2.asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                firLabelBuilder.setName(strAsString);
                lastLabel = firLabelBuilder.build();
            } else {
                lastLabel = null;
            }
        }
        firAnonymousFunctionBuilder.setLabel(lastLabel);
        FirLabel label = firAnonymousFunctionBuilder.getLabel();
        FirFunctionTarget firFunctionTarget = new FirFunctionTarget(label != null ? label.getName() : null, true);
        getContext().getFirFunctionTargets().add(firFunctionTarget);
        ArrayList arrayList2 = new ArrayList();
        for (ValueParameter valueParameter : arrayList) {
            DestructuringDeclaration destructuringDeclaration = valueParameter.getDestructuringDeclaration();
            List<FirValueParameter> valueParameters = firAnonymousFunctionBuilder.getValueParameters();
            if (destructuringDeclaration != null) {
                Name name3 = SpecialNames.DESTRUCT;
                FirValueParameterBuilder firValueParameterBuilder = new FirValueParameterBuilder();
                firValueParameterBuilder.setSource(valueParameter.getFirValueParameter().getSource());
                firValueParameterBuilder.setContainingFunctionSymbol(firAnonymousFunctionSymbol);
                firValueParameterBuilder.setModuleData(getBaseModuleData());
                firValueParameterBuilder.setOrigin(FirDeclarationOrigin.Source.INSTANCE);
                firValueParameterBuilder.setReturnTypeRef(valueParameter.getFirValueParameter().getReturnTypeRef());
                firValueParameterBuilder.setName(name3);
                firValueParameterBuilder.setSymbol(new FirValueParameterSymbol(name3));
                firValueParameterBuilder.setDefaultValue(null);
                firValueParameterBuilder.setCrossinline(false);
                firValueParameterBuilder.setNoinline(false);
                firValueParameterBuilder.setVararg(false);
                firValueParameter = firValueParameterBuilder.mo6809build();
                CollectionsKt.addAll(arrayList2, ConverterUtilKt.generateDestructuringBlock(getBaseModuleData(), destructuringDeclaration, firValueParameter, false).getStatements());
            } else {
                firValueParameter = valueParameter.getFirValueParameter();
            }
            valueParameters.add(firValueParameter);
        }
        if (lighterASTNode2 != null) {
            FirBlockBuilder firBlockBuilderConvertBlockExpressionWithoutBuilding = this.declarationBuilder.convertBlockExpressionWithoutBuilding(lighterASTNode2);
            FirStatement firStatement = (FirStatement) CollectionsKt.firstOrNull((List) firBlockBuilderConvertBlockExpressionWithoutBuilding.getStatements());
            if (firStatement != null && ConversionUtilsKt.isContractBlockFirCheck(firStatement)) {
                FirFunctionCall firFunctionCall = (FirFunctionCall) firStatement;
                firAnonymousFunctionBuilder.setContractDescription(ConversionUtilsKt.toLegacyRawContractDescription(firFunctionCall));
                firBlockBuilderConvertBlockExpressionWithoutBuilding.getStatements().set(0, new FirContractCallBlock(firFunctionCall));
            }
            if (firBlockBuilderConvertBlockExpressionWithoutBuilding.getStatements().isEmpty()) {
                List<FirStatement> statements = firBlockBuilderConvertBlockExpressionWithoutBuilding.getStatements();
                FirReturnExpressionBuilder firReturnExpressionBuilder = new FirReturnExpressionBuilder();
                firReturnExpressionBuilder.setSource(KtSourceElementKt.fakeElement$default(ktLightSourceElement2, KtFakeSourceElementKind.ImplicitReturn.FromExpressionBody.INSTANCE, 0, 0, 6, null));
                firReturnExpressionBuilder.setTarget(firFunctionTarget);
                FirUnitExpressionBuilder firUnitExpressionBuilder = new FirUnitExpressionBuilder();
                firUnitExpressionBuilder.setSource(KtSourceElementKt.fakeElement$default(ktLightSourceElement2, KtFakeSourceElementKind.ImplicitUnit.LambdaCoercion.INSTANCE, 0, 0, 6, null));
                firReturnExpressionBuilder.setResult(firUnitExpressionBuilder.mo6809build());
                statements.add(firReturnExpressionBuilder.mo6809build());
            }
            firBlockBuilderConvertBlockExpressionWithoutBuilding.getStatements().addAll(0, arrayList2);
            firSingleExpressionBlock = firBlockBuilderConvertBlockExpressionWithoutBuilding.mo6809build();
        } else {
            firSingleExpressionBlock = new FirSingleExpressionBlock(FirExpressionUtilKt.buildErrorExpression$default(null, new ConeSyntaxDiagnostic("Lambda has no body"), null, 4, null));
        }
        firAnonymousFunctionBuilder.setBody(firSingleExpressionBlock);
        removeLast(getContext().getFirFunctionTargets());
        FirAnonymousFunction firAnonymousFunctionMo6809build = firAnonymousFunctionBuilder.mo6809build();
        firFunctionTarget.bind(firAnonymousFunctionMo6809build);
        FirAnonymousFunctionExpressionBuilder firAnonymousFunctionExpressionBuilder = new FirAnonymousFunctionExpressionBuilder();
        firAnonymousFunctionExpressionBuilder.setSource(ktLightSourceElement2);
        firAnonymousFunctionExpressionBuilder.setAnonymousFunction(firAnonymousFunctionMo6809build);
        return firAnonymousFunctionExpressionBuilder.mo6809build();
    }

    private final FirStatement convertBinaryExpression(LighterASTNode binaryExpression) {
        String str;
        FirExpression firExpressionBuildErrorExpression$default;
        ConeSimpleDiagnostic coneSimpleDiagnostic;
        KtToken[] ktTokenArr = new KtToken[0];
        boolean z = true;
        LighterASTNode lighterASTNode = null;
        LighterASTNode lighterASTNode2 = null;
        String str2 = null;
        KtSourceElement firSourceElement$default = null;
        for (LighterASTNode lighterASTNode3 : getChildrenAsArray(binaryExpression)) {
            if (lighterASTNode3 == null) {
                break;
            }
            IElementType tokenType = lighterASTNode3.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr, tokenType) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT)) {
                if (Intrinsics.areEqual(lighterASTNode3.getTokenType(), KtNodeTypes.OPERATION_REFERENCE)) {
                    String asText = getAsText(lighterASTNode3);
                    firSourceElement$default = AbstractRawFirBuilder.toFirSourceElement$default(this, lighterASTNode3, null, 1, null);
                    str2 = asText;
                    z = false;
                } else if (ElementTypeUtils.INSTANCE.isExpression(lighterASTNode3)) {
                    if (z) {
                        lighterASTNode = lighterASTNode3;
                    } else {
                        lighterASTNode2 = lighterASTNode3;
                    }
                }
            }
        }
        LightTreeRawFirExpressionBuilder lightTreeRawFirExpressionBuilder = this;
        KtLightSourceElement ktLightSourceElement = (KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(lightTreeRawFirExpressionBuilder, binaryExpression, null, 1, null);
        ElementTypeUtils elementTypeUtils = ElementTypeUtils.INSTANCE;
        if (str2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("operationTokenName");
            str = null;
        } else {
            str = str2;
        }
        IElementType operationSymbol = elementTypeUtils.getOperationSymbol(str);
        if (Intrinsics.areEqual(operationSymbol, KtTokens.IDENTIFIER)) {
            getContext().getCalleeNamesForLambda().add(ConverterUtilKt.nameAsSafeName$default(str2, null, 1, null));
        } else {
            getContext().getCalleeNamesForLambda().add(null);
        }
        if (lighterASTNode2 == null) {
            firExpressionBuildErrorExpression$default = FirExpressionUtilKt.buildErrorExpression$default(null, new ConeSyntaxDiagnostic("No right operand"), null, 4, null);
        } else {
            FirElement firElementConvertExpression = convertExpression(lighterASTNode2, "No right operand");
            FirAnnotationContainer firAnnotationContainer = (FirExpression) (!(firElementConvertExpression instanceof FirExpression) ? null : firElementConvertExpression);
            if (firAnnotationContainer == null) {
                FirAnnotationContainer firAnnotationContainerBuildErrorExpression = FirExpressionUtilKt.buildErrorExpression((KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(lightTreeRawFirExpressionBuilder, lighterASTNode2, null, 1, null), new ConeSimpleDiagnostic("No right operand", DiagnosticKind.ExpressionExpected), firElementConvertExpression);
                if (firAnnotationContainerBuildErrorExpression == null) {
                    throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirExpression");
                }
                firAnnotationContainer = (FirExpression) firAnnotationContainerBuildErrorExpression;
            }
            firExpressionBuildErrorExpression$default = (FirExpression) firAnnotationContainer;
        }
        FirElement firElementConvertExpression2 = lighterASTNode != null ? convertExpression(lighterASTNode, "No left operand") : null;
        FirAnnotationContainer firAnnotationContainer2 = (FirExpression) (!(firElementConvertExpression2 instanceof FirExpression) ? null : firElementConvertExpression2);
        if (firAnnotationContainer2 == null) {
            KtLightSourceElement ktLightSourceElement2 = lighterASTNode != null ? (KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(lightTreeRawFirExpressionBuilder, lighterASTNode, null, 1, null) : null;
            if (lighterASTNode == null) {
                coneSimpleDiagnostic = new ConeSyntaxDiagnostic("No left operand");
            } else {
                coneSimpleDiagnostic = new ConeSimpleDiagnostic("No left operand", DiagnosticKind.ExpressionExpected);
            }
            FirAnnotationContainer firAnnotationContainerBuildErrorExpression2 = FirExpressionUtilKt.buildErrorExpression(ktLightSourceElement2, coneSimpleDiagnostic, firElementConvertExpression2);
            if (firAnnotationContainerBuildErrorExpression2 == null) {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirExpression");
            }
            firAnnotationContainer2 = (FirExpression) firAnnotationContainerBuildErrorExpression2;
        }
        FirExpression firExpression = (FirExpression) firAnnotationContainer2;
        removeLast(getContext().getCalleeNamesForLambda());
        if (Intrinsics.areEqual(operationSymbol, KtTokens.ELVIS)) {
            return ConversionUtilsKt.generateNotNullOrOther(firExpression, firExpressionBuildErrorExpression$default, ktLightSourceElement);
        }
        if (Intrinsics.areEqual(operationSymbol, KtTokens.ANDAND) ? true : Intrinsics.areEqual(operationSymbol, KtTokens.OROR)) {
            return ConversionUtilsKt.generateLazyLogicalOperation(firExpression, firExpressionBuildErrorExpression$default, Intrinsics.areEqual(operationSymbol, KtTokens.ANDAND), ktLightSourceElement);
        }
        ImmutableSet<KtSingleValueToken> IN_OPERATIONS = OperatorConventions.IN_OPERATIONS;
        Intrinsics.checkNotNullExpressionValue(IN_OPERATIONS, "IN_OPERATIONS");
        if (CollectionsKt.contains(IN_OPERATIONS, operationSymbol)) {
            return ConversionUtilsKt.generateContainsOperation(firExpressionBuildErrorExpression$default, firExpression, Intrinsics.areEqual(operationSymbol, KtTokens.NOT_IN), ktLightSourceElement, firSourceElement$default);
        }
        ImmutableSet<KtSingleValueToken> COMPARISON_OPERATIONS = OperatorConventions.COMPARISON_OPERATIONS;
        Intrinsics.checkNotNullExpressionValue(COMPARISON_OPERATIONS, "COMPARISON_OPERATIONS");
        if (CollectionsKt.contains(COMPARISON_OPERATIONS, operationSymbol)) {
            return ConversionUtilsKt.generateComparisonExpression(firExpression, firExpressionBuildErrorExpression$default, operationSymbol, ktLightSourceElement, firSourceElement$default);
        }
        Name binaryName = ConversionUtilsKt.toBinaryName(operationSymbol);
        if (binaryName == null && !Intrinsics.areEqual(operationSymbol, KtTokens.IDENTIFIER)) {
            FirOperation firOperation = ConversionUtilsKt.toFirOperation(operationSymbol);
            if (FirOperation.INSTANCE.getASSIGNMENTS().contains(firOperation)) {
                return generateAssignment(lighterASTNode, AbstractRawFirBuilder.toFirSourceElement$default(lightTreeRawFirExpressionBuilder, binaryExpression, null, 1, null), lighterASTNode != null ? (KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(lightTreeRawFirExpressionBuilder, lighterASTNode, null, 1, null) : null, firExpressionBuildErrorExpression$default, firOperation, firExpression.getAnnotations(), lighterASTNode2, new Function1<LighterASTNode, FirExpression>() { // from class: org.jetbrains.kotlin.fir.lightTree.converter.LightTreeRawFirExpressionBuilder.convertBinaryExpression.3
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final FirExpression invoke(LighterASTNode generateAssignment) {
                        Intrinsics.checkNotNullParameter(generateAssignment, "$this$generateAssignment");
                        LightTreeRawFirExpressionBuilder lightTreeRawFirExpressionBuilder2 = LightTreeRawFirExpressionBuilder.this;
                        FirElement firElementConvertExpression3 = lightTreeRawFirExpressionBuilder2.convertExpression(generateAssignment, "");
                        FirAnnotationContainer firAnnotationContainer3 = (FirExpression) (!(firElementConvertExpression3 instanceof FirExpression) ? null : firElementConvertExpression3);
                        if (firAnnotationContainer3 == null) {
                            FirAnnotationContainer firAnnotationContainerBuildErrorExpression3 = FirExpressionUtilKt.buildErrorExpression((KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(lightTreeRawFirExpressionBuilder2, generateAssignment, null, 1, null), new ConeSimpleDiagnostic("", DiagnosticKind.ExpressionExpected), firElementConvertExpression3);
                            if (firAnnotationContainerBuildErrorExpression3 == null) {
                                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirExpression");
                            }
                            firAnnotationContainer3 = (FirExpression) firAnnotationContainerBuildErrorExpression3;
                        }
                        return (FirExpression) firAnnotationContainer3;
                    }
                });
            }
            FirEqualityOperatorCallBuilder firEqualityOperatorCallBuilder = new FirEqualityOperatorCallBuilder();
            firEqualityOperatorCallBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(lightTreeRawFirExpressionBuilder, binaryExpression, null, 1, null));
            firEqualityOperatorCallBuilder.setOperation(firOperation);
            firEqualityOperatorCallBuilder.setArgumentList(FirArgumentUtilKt.buildBinaryArgumentList(firExpression, firExpressionBuildErrorExpression$default));
            return firEqualityOperatorCallBuilder.mo6809build();
        }
        FirFunctionCallBuilder firFunctionCallBuilder = new FirFunctionCallBuilder();
        firFunctionCallBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(lightTreeRawFirExpressionBuilder, binaryExpression, null, 1, null));
        FirSimpleNamedReferenceBuilder firSimpleNamedReferenceBuilder = new FirSimpleNamedReferenceBuilder();
        KtLightSourceElement ktLightSourceElement3 = (KtLightSourceElement) firSourceElement$default;
        firSimpleNamedReferenceBuilder.setSource(ktLightSourceElement3 != null ? ktLightSourceElement3 : firFunctionCallBuilder.getSource());
        firSimpleNamedReferenceBuilder.setName(binaryName == null ? ConverterUtilKt.nameAsSafeName$default(str2, null, 1, null) : binaryName);
        firFunctionCallBuilder.setCalleeReference(firSimpleNamedReferenceBuilder.build());
        firFunctionCallBuilder.setExplicitReceiver(firExpression);
        firFunctionCallBuilder.setArgumentList(FirArgumentUtilKt.buildUnaryArgumentList(firExpressionBuildErrorExpression$default));
        firFunctionCallBuilder.setOrigin(binaryName != null ? FirFunctionCallOrigin.Operator : FirFunctionCallOrigin.Infix);
        return firFunctionCallBuilder.mo6809build();
    }

    private final FirTypeOperatorCall convertBinaryWithTypeRHSExpression(LighterASTNode binaryExpression, Function1<? super String, ? extends FirOperation> toFirOperation) {
        LighterASTNode lighterASTNode;
        KtToken[] ktTokenArr = new KtToken[0];
        LighterASTNode[] childrenAsArray = getChildrenAsArray(binaryExpression);
        int length = childrenAsArray.length;
        String asText = null;
        FirTypeRef firTypeRefConvertType = null;
        Object obj = null;
        for (int i = 0; i < length && (lighterASTNode = childrenAsArray[i]) != null; i++) {
            IElementType tokenType = lighterASTNode.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr, tokenType) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT)) {
                IElementType tokenType2 = lighterASTNode.getTokenType();
                if (Intrinsics.areEqual(tokenType2, KtNodeTypes.OPERATION_REFERENCE)) {
                    asText = getAsText(lighterASTNode);
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.TYPE_REFERENCE)) {
                    firTypeRefConvertType = this.declarationBuilder.convertType(lighterASTNode);
                } else if (ElementTypeUtils.INSTANCE.isExpression(lighterASTNode)) {
                    FirElement firElementConvertExpression = convertExpression(lighterASTNode, "No left operand");
                    FirErrorExpression firErrorExpression = (FirExpression) (!(firElementConvertExpression instanceof FirExpression) ? null : firElementConvertExpression);
                    if (firErrorExpression == null) {
                        FirErrorExpression firErrorExpressionBuildErrorExpression = FirExpressionUtilKt.buildErrorExpression((KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(this, lighterASTNode, null, 1, null), new ConeSimpleDiagnostic("No left operand", DiagnosticKind.ExpressionExpected), firElementConvertExpression);
                        if (firErrorExpressionBuildErrorExpression == null) {
                            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirExpression");
                        }
                        firErrorExpression = firErrorExpressionBuildErrorExpression;
                    }
                    obj = firErrorExpression;
                } else {
                    continue;
                }
            }
        }
        FirTypeOperatorCallBuilder firTypeOperatorCallBuilder = new FirTypeOperatorCallBuilder();
        firTypeOperatorCallBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(this, binaryExpression, null, 1, null));
        if (asText == null) {
            Intrinsics.throwUninitializedPropertyAccessException("operationTokenName");
            asText = null;
        }
        firTypeOperatorCallBuilder.setOperation(toFirOperation.invoke(asText));
        if (firTypeRefConvertType == null) {
            Intrinsics.throwUninitializedPropertyAccessException("firType");
            firTypeRefConvertType = null;
        }
        firTypeOperatorCallBuilder.setConversionTypeRef(firTypeRefConvertType);
        FirErrorExpression firErrorExpressionBuildErrorExpression$default = (FirExpression) obj;
        if (firErrorExpressionBuildErrorExpression$default == null) {
            firErrorExpressionBuildErrorExpression$default = FirExpressionUtilKt.buildErrorExpression$default(null, new ConeSyntaxDiagnostic("No left operand"), null, 4, null);
        }
        firTypeOperatorCallBuilder.setArgumentList(FirArgumentUtilKt.buildUnaryArgumentList(firErrorExpressionBuildErrorExpression$default));
        return firTypeOperatorCallBuilder.mo6809build();
    }

    private final FirElement convertLabeledExpression(LighterASTNode labeledExpression) {
        LighterASTNode lighterASTNode;
        KtToken[] ktTokenArr = new KtToken[0];
        LighterASTNode[] childrenAsArray = getChildrenAsArray(labeledExpression);
        int length = childrenAsArray.length;
        FirElement firElementConvertPropertyDeclaration$default = null;
        KtSourceElement second = null;
        for (int i = 0; i < length && (lighterASTNode = childrenAsArray[i]) != null; i++) {
            IElementType tokenType = lighterASTNode.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr, tokenType) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT)) {
                getContext().setNewLabelUserNode(lighterASTNode);
                IElementType tokenType2 = lighterASTNode.getTokenType();
                if (Intrinsics.areEqual(tokenType2, KtNodeTypes.LABEL_QUALIFIER)) {
                    String string = lighterASTNode.toString();
                    String strSubstring = string.substring(0, string.length() - 1);
                    Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
                    IElementType LABEL = KtNodeTypes.LABEL;
                    Intrinsics.checkNotNullExpressionValue(LABEL, "LABEL");
                    Pair<FirLabel, KtSourceElement> pairBuildLabelAndErrorSource = buildLabelAndErrorSource(strSubstring, AbstractRawFirBuilder.toFirSourceElement$default(this, CollectionsKt.single((List) getChildNodesByType(lighterASTNode, LABEL)), null, 1, null));
                    getContext().addNewLabel(pairBuildLabelAndErrorSource.getFirst());
                    second = pairBuildLabelAndErrorSource.getSecond();
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.BLOCK)) {
                    firElementConvertPropertyDeclaration$default = this.declarationBuilder.convertBlock(lighterASTNode);
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.PROPERTY)) {
                    firElementConvertPropertyDeclaration$default = LightTreeRawFirDeclarationBuilder.convertPropertyDeclaration$default(this.declarationBuilder, lighterASTNode, null, 2, null);
                } else if (ElementTypeUtils.INSTANCE.isExpression(lighterASTNode)) {
                    FirElement firElementConvertExpression = convertExpression(lighterASTNode, "");
                    FirErrorExpression firErrorExpression = !(firElementConvertExpression instanceof FirElement) ? null : firElementConvertExpression;
                    if (firErrorExpression == null) {
                        FirErrorExpression firErrorExpressionBuildErrorExpression = FirExpressionUtilKt.buildErrorExpression((KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(this, lighterASTNode, null, 1, null), new ConeSimpleDiagnostic("", DiagnosticKind.ExpressionExpected), firElementConvertExpression);
                        if (firErrorExpressionBuildErrorExpression == null) {
                            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.FirElement");
                        }
                        firErrorExpression = firErrorExpressionBuildErrorExpression;
                    }
                    firElementConvertPropertyDeclaration$default = firErrorExpression;
                } else {
                    continue;
                }
            }
        }
        getContext().dropLastLabel();
        return buildExpressionWithErrorLabel(firElementConvertPropertyDeclaration$default, second, AbstractRawFirBuilder.toFirSourceElement$default(this, labeledExpression, null, 1, null));
    }

    private final FirExpression convertUnaryExpression(LighterASTNode unaryExpression) {
        KtLightSourceElement ktLightSourceElement;
        ConeSimpleDiagnostic coneSimpleDiagnostic;
        ConeSimpleDiagnostic coneSimpleDiagnostic2;
        KtToken[] ktTokenArr = new KtToken[0];
        String asText = null;
        LighterASTNode lighterASTNode = null;
        LighterASTNode lighterASTNode2 = null;
        for (LighterASTNode lighterASTNode3 : getChildrenAsArray(unaryExpression)) {
            if (lighterASTNode3 == null) {
                break;
            }
            IElementType tokenType = lighterASTNode3.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr, tokenType) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT)) {
                if (Intrinsics.areEqual(lighterASTNode3.getTokenType(), KtNodeTypes.OPERATION_REFERENCE)) {
                    asText = getAsText(lighterASTNode3);
                    lighterASTNode = lighterASTNode3;
                } else if (ElementTypeUtils.INSTANCE.isExpression(lighterASTNode3)) {
                    lighterASTNode2 = lighterASTNode3;
                }
            }
        }
        ElementTypeUtils elementTypeUtils = ElementTypeUtils.INSTANCE;
        if (asText == null) {
            Intrinsics.throwUninitializedPropertyAccessException("operationTokenName");
            asText = null;
        }
        IElementType operationSymbol = elementTypeUtils.getOperationSymbol(asText);
        Name unaryName = ConversionUtilsKt.toUnaryName(operationSymbol);
        if (Intrinsics.areEqual(operationSymbol, KtTokens.EXCLEXCL)) {
            FirCheckNotNullCallBuilder firCheckNotNullCallBuilder = new FirCheckNotNullCallBuilder();
            LightTreeRawFirExpressionBuilder lightTreeRawFirExpressionBuilder = this;
            firCheckNotNullCallBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(lightTreeRawFirExpressionBuilder, unaryExpression, null, 1, null));
            FirElement firElementConvertExpression = lighterASTNode2 != null ? convertExpression(lighterASTNode2, "No operand") : null;
            FirAnnotationContainer firAnnotationContainer = (FirExpression) (!(firElementConvertExpression instanceof FirExpression) ? null : firElementConvertExpression);
            if (firAnnotationContainer == null) {
                KtLightSourceElement ktLightSourceElement2 = lighterASTNode2 != null ? (KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(lightTreeRawFirExpressionBuilder, lighterASTNode2, null, 1, null) : null;
                if (lighterASTNode2 == null) {
                    coneSimpleDiagnostic2 = new ConeSyntaxDiagnostic("No operand");
                } else {
                    coneSimpleDiagnostic2 = new ConeSimpleDiagnostic("No operand", DiagnosticKind.ExpressionExpected);
                }
                FirAnnotationContainer firAnnotationContainerBuildErrorExpression = FirExpressionUtilKt.buildErrorExpression(ktLightSourceElement2, coneSimpleDiagnostic2, firElementConvertExpression);
                if (firAnnotationContainerBuildErrorExpression == null) {
                    throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirExpression");
                }
                firAnnotationContainer = (FirExpression) firAnnotationContainerBuildErrorExpression;
            }
            firCheckNotNullCallBuilder.setArgumentList(FirArgumentUtilKt.buildUnaryArgumentList((FirExpression) firAnnotationContainer));
            return firCheckNotNullCallBuilder.mo6809build();
        }
        if (unaryName != null) {
            ImmutableSet<KtSingleValueToken> INCREMENT_OPERATIONS = OperatorConventions.INCREMENT_OPERATIONS;
            Intrinsics.checkNotNullExpressionValue(INCREMENT_OPERATIONS, "INCREMENT_OPERATIONS");
            if (CollectionsKt.contains(INCREMENT_OPERATIONS, operationSymbol)) {
                return generateIncrementOrDecrementBlock(unaryExpression, lighterASTNode, lighterASTNode2, unaryName, Intrinsics.areEqual(unaryExpression.getTokenType(), KtNodeTypes.PREFIX_EXPRESSION), new Function1<LighterASTNode, FirExpression>() { // from class: org.jetbrains.kotlin.fir.lightTree.converter.LightTreeRawFirExpressionBuilder.convertUnaryExpression.3
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final FirExpression invoke(LighterASTNode generateIncrementOrDecrementBlock) {
                        Intrinsics.checkNotNullParameter(generateIncrementOrDecrementBlock, "$this$generateIncrementOrDecrementBlock");
                        LightTreeRawFirExpressionBuilder lightTreeRawFirExpressionBuilder2 = LightTreeRawFirExpressionBuilder.this;
                        FirElement firElementConvertExpression2 = lightTreeRawFirExpressionBuilder2.convertExpression(generateIncrementOrDecrementBlock, "");
                        FirAnnotationContainer firAnnotationContainer2 = (FirExpression) (!(firElementConvertExpression2 instanceof FirExpression) ? null : firElementConvertExpression2);
                        if (firAnnotationContainer2 == null) {
                            FirAnnotationContainer firAnnotationContainerBuildErrorExpression2 = FirExpressionUtilKt.buildErrorExpression((KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(lightTreeRawFirExpressionBuilder2, generateIncrementOrDecrementBlock, null, 1, null), new ConeSimpleDiagnostic("", DiagnosticKind.ExpressionExpected), firElementConvertExpression2);
                            if (firAnnotationContainerBuildErrorExpression2 == null) {
                                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirExpression");
                            }
                            firAnnotationContainer2 = (FirExpression) firAnnotationContainerBuildErrorExpression2;
                        }
                        return (FirExpression) firAnnotationContainer2;
                    }
                });
            }
            FirElement firElementConvertExpression2 = lighterASTNode2 != null ? convertExpression(lighterASTNode2, "No operand") : null;
            FirAnnotationContainer firAnnotationContainer2 = (FirExpression) (!(firElementConvertExpression2 instanceof FirExpression) ? null : firElementConvertExpression2);
            if (firAnnotationContainer2 == null) {
                KtLightSourceElement ktLightSourceElement3 = lighterASTNode2 != null ? (KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(this, lighterASTNode2, null, 1, null) : null;
                if (lighterASTNode2 == null) {
                    coneSimpleDiagnostic = new ConeSyntaxDiagnostic("No operand");
                } else {
                    coneSimpleDiagnostic = new ConeSimpleDiagnostic("No operand", DiagnosticKind.ExpressionExpected);
                }
                FirAnnotationContainer firAnnotationContainerBuildErrorExpression2 = FirExpressionUtilKt.buildErrorExpression(ktLightSourceElement3, coneSimpleDiagnostic, firElementConvertExpression2);
                if (firAnnotationContainerBuildErrorExpression2 == null) {
                    throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirExpression");
                }
                firAnnotationContainer2 = (FirExpression) firAnnotationContainerBuildErrorExpression2;
            }
            FirExpression firExpression = (FirExpression) firAnnotationContainer2;
            FirExpression firExpressionConvertUnaryPlusMinusCallOnIntegerLiteralIfNecessary = convertUnaryPlusMinusCallOnIntegerLiteralIfNecessary(unaryExpression, firExpression, operationSymbol);
            if (firExpressionConvertUnaryPlusMinusCallOnIntegerLiteralIfNecessary != null) {
                return firExpressionConvertUnaryPlusMinusCallOnIntegerLiteralIfNecessary;
            }
            FirFunctionCallBuilder firFunctionCallBuilder = new FirFunctionCallBuilder();
            LightTreeRawFirExpressionBuilder lightTreeRawFirExpressionBuilder2 = this;
            firFunctionCallBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(lightTreeRawFirExpressionBuilder2, unaryExpression, null, 1, null));
            FirSimpleNamedReferenceBuilder firSimpleNamedReferenceBuilder = new FirSimpleNamedReferenceBuilder();
            firSimpleNamedReferenceBuilder.setSource((lighterASTNode == null || (ktLightSourceElement = (KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(lightTreeRawFirExpressionBuilder2, lighterASTNode, null, 1, null)) == null) ? firFunctionCallBuilder.getSource() : ktLightSourceElement);
            firSimpleNamedReferenceBuilder.setName(unaryName);
            firFunctionCallBuilder.setCalleeReference(firSimpleNamedReferenceBuilder.build());
            firFunctionCallBuilder.setExplicitReceiver(firExpression);
            firFunctionCallBuilder.setOrigin(FirFunctionCallOrigin.Operator);
            return firFunctionCallBuilder.mo6809build();
        }
        throw new IllegalStateException("Unexpected expression: " + getAsText(unaryExpression));
    }

    private final FirElement convertAnnotatedExpression(LighterASTNode annotatedExpression) {
        ArrayList arrayList = new ArrayList();
        KtToken[] ktTokenArr = new KtToken[0];
        FirErrorExpression firErrorExpressionBuildErrorExpression$default = null;
        for (LighterASTNode lighterASTNode : getChildrenAsArray(annotatedExpression)) {
            if (lighterASTNode == null) {
                break;
            }
            IElementType tokenType = lighterASTNode.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr, tokenType) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT)) {
                IElementType tokenType2 = lighterASTNode.getTokenType();
                if (Intrinsics.areEqual(tokenType2, KtNodeTypes.ANNOTATION)) {
                    CollectionsKt.addAll(arrayList, this.declarationBuilder.convertAnnotation(lighterASTNode));
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.ANNOTATION_ENTRY)) {
                    arrayList.add(LightTreeRawFirDeclarationBuilder.convertAnnotationEntry$default(this.declarationBuilder, lighterASTNode, null, null, 6, null));
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.BLOCK)) {
                    firErrorExpressionBuildErrorExpression$default = this.declarationBuilder.convertBlockExpression(lighterASTNode);
                } else if (ElementTypeUtils.INSTANCE.isExpression(lighterASTNode)) {
                    getContext().forwardLabelUsagePermission(annotatedExpression, lighterASTNode);
                    FirElement firElementConvertExpression = convertExpression(lighterASTNode, "");
                    FirErrorExpression firErrorExpression = !(firElementConvertExpression instanceof FirElement) ? null : firElementConvertExpression;
                    if (firErrorExpression == null) {
                        FirErrorExpression firErrorExpressionBuildErrorExpression = FirExpressionUtilKt.buildErrorExpression((KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(this, lighterASTNode, null, 1, null), new ConeSimpleDiagnostic("", DiagnosticKind.ExpressionExpected), firElementConvertExpression);
                        if (firErrorExpressionBuildErrorExpression == null) {
                            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.FirElement");
                        }
                        firErrorExpression = firErrorExpressionBuildErrorExpression;
                    }
                    firErrorExpressionBuildErrorExpression$default = firErrorExpression;
                } else {
                    continue;
                }
            }
        }
        if (firErrorExpressionBuildErrorExpression$default == null) {
            firErrorExpressionBuildErrorExpression$default = FirExpressionUtilKt.buildErrorExpression$default(null, new ConeNotAnnotationContainer("???"), null, 4, null);
        }
        if (!(firErrorExpressionBuildErrorExpression$default instanceof FirAnnotationContainer)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        FirAnnotationContainer firAnnotationContainer = (FirAnnotationContainer) firErrorExpressionBuildErrorExpression$default;
        firAnnotationContainer.replaceAnnotations(UtilsKt.smartPlus(firAnnotationContainer.getAnnotations(), arrayList));
        return firErrorExpressionBuildErrorExpression$default;
    }

    private final FirExpression convertClassLiteralExpression(LighterASTNode classLiteralExpression) {
        LighterASTNode lighterASTNode;
        KtToken[] ktTokenArr = new KtToken[0];
        LighterASTNode[] childrenAsArray = getChildrenAsArray(classLiteralExpression);
        int length = childrenAsArray.length;
        Object obj = null;
        for (int i = 0; i < length && (lighterASTNode = childrenAsArray[i]) != null; i++) {
            IElementType tokenType = lighterASTNode.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr, tokenType) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT) && ElementTypeUtils.INSTANCE.isExpression(lighterASTNode)) {
                FirElement firElementConvertExpression = convertExpression(lighterASTNode, "No receiver in class literal");
                FirErrorExpression firErrorExpression = (FirExpression) (!(firElementConvertExpression instanceof FirExpression) ? null : firElementConvertExpression);
                if (firErrorExpression == null) {
                    FirErrorExpression firErrorExpressionBuildErrorExpression = FirExpressionUtilKt.buildErrorExpression((KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(this, lighterASTNode, null, 1, null), new ConeSimpleDiagnostic("No receiver in class literal", DiagnosticKind.ExpressionExpected), firElementConvertExpression);
                    if (firErrorExpressionBuildErrorExpression == null) {
                        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirExpression");
                    }
                    firErrorExpression = firErrorExpressionBuildErrorExpression;
                }
                obj = firErrorExpression;
            }
        }
        KtLightSourceElement ktLightSourceElement = (KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(this, classLiteralExpression, null, 1, null);
        FirGetClassCallBuilder firGetClassCallBuilder = new FirGetClassCallBuilder();
        KtLightSourceElement ktLightSourceElement2 = ktLightSourceElement;
        firGetClassCallBuilder.setSource(ktLightSourceElement2);
        FirErrorExpression firErrorExpressionBuildErrorExpression$default = (FirExpression) obj;
        if (firErrorExpressionBuildErrorExpression$default == null) {
            firErrorExpressionBuildErrorExpression$default = FirExpressionUtilKt.buildErrorExpression$default(ktLightSourceElement2, ConeUnsupportedClassLiteralsWithEmptyLhs.INSTANCE, null, 4, null);
        }
        firGetClassCallBuilder.setArgumentList(FirArgumentUtilKt.buildUnaryArgumentList(firErrorExpressionBuildErrorExpression$default));
        return firGetClassCallBuilder.mo6809build();
    }

    private final FirExpression convertCallableReferenceExpression(LighterASTNode callableReferenceExpression) {
        boolean z = false;
        KtToken[] ktTokenArr = new KtToken[0];
        LighterASTNode[] childrenAsArray = getChildrenAsArray(callableReferenceExpression);
        int length = childrenAsArray.length;
        FirNamedReference firNamedReference = null;
        int i = 0;
        boolean z2 = false;
        boolean z3 = true;
        Object obj = null;
        FirNamedReference firNamedReferenceCreateSimpleNamedReference = null;
        while (i < length) {
            LighterASTNode lighterASTNode = childrenAsArray[i];
            if (lighterASTNode == null) {
                break;
            }
            IElementType tokenType = lighterASTNode.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr, tokenType) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT)) {
                IElementType tokenType2 = lighterASTNode.getTokenType();
                if (Intrinsics.areEqual(tokenType2, KtTokens.COLONCOLON)) {
                    z3 = z;
                } else if (Intrinsics.areEqual(tokenType2, KtTokens.QUEST)) {
                    z2 = true;
                } else if (!ElementTypeUtils.INSTANCE.isExpression(lighterASTNode)) {
                    continue;
                } else if (!z3) {
                    firNamedReferenceCreateSimpleNamedReference = createSimpleNamedReference(AbstractRawFirBuilder.toFirSourceElement$default(this, lighterASTNode, null, 1, null), lighterASTNode);
                } else {
                    FirElement firElementConvertExpression = convertExpression(lighterASTNode, "Incorrect receiver expression");
                    FirErrorExpression firErrorExpression = (FirExpression) (!(firElementConvertExpression instanceof FirExpression) ? null : firElementConvertExpression);
                    if (firErrorExpression == null) {
                        FirErrorExpression firErrorExpressionBuildErrorExpression = FirExpressionUtilKt.buildErrorExpression((KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(this, lighterASTNode, null, 1, null), new ConeSimpleDiagnostic("Incorrect receiver expression", DiagnosticKind.ExpressionExpected), firElementConvertExpression);
                        if (firErrorExpressionBuildErrorExpression == null) {
                            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirExpression");
                        }
                        firErrorExpression = firErrorExpressionBuildErrorExpression;
                    }
                    obj = firErrorExpression;
                }
            }
            i++;
            z = false;
        }
        FirCallableReferenceAccessBuilder firCallableReferenceAccessBuilder = new FirCallableReferenceAccessBuilder();
        firCallableReferenceAccessBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(this, callableReferenceExpression, null, 1, null));
        if (firNamedReferenceCreateSimpleNamedReference == null) {
            Intrinsics.throwUninitializedPropertyAccessException("namedReference");
        } else {
            firNamedReference = firNamedReferenceCreateSimpleNamedReference;
        }
        firCallableReferenceAccessBuilder.setCalleeReference(firNamedReference);
        firCallableReferenceAccessBuilder.setExplicitReceiver((FirExpression) obj);
        firCallableReferenceAccessBuilder.setHasQuestionMarkAtLHS(z2);
        return firCallableReferenceAccessBuilder.mo6809build();
    }

    /* JADX WARN: Code duplicated, block: B:61:0x0106  */
    /* JADX WARN: Code duplicated, block: B:63:0x010d  */
    /* JADX WARN: Code duplicated, block: B:65:0x011a  */
    /* JADX WARN: Code duplicated, block: B:66:0x011d  */
    /* JADX WARN: Code duplicated, block: B:70:0x012d  */
    /* JADX WARN: Code duplicated, block: B:71:0x0130  */
    /* JADX WARN: Multi-variable type inference failed */
    private final FirExpression convertQualifiedExpression(LighterASTNode dotQualifiedExpression) {
        int i;
        KtFakeSourceElementKind ktFakeSourceElementKind;
        KtLightSourceElement firSourceElement$default;
        DiagnosticKind diagnosticKind;
        KtLightSourceElement ktLightSourceElement;
        Ref.ObjectRef objectRef = new Ref.ObjectRef();
        boolean z = false;
        KtToken[] ktTokenArr = new KtToken[0];
        LighterASTNode[] childrenAsArray = getChildrenAsArray(dotQualifiedExpression);
        int length = childrenAsArray.length;
        int i2 = 0;
        boolean z2 = false;
        boolean z3 = false;
        FirExpression firExpression = null;
        while (i2 < length) {
            LighterASTNode lighterASTNode = childrenAsArray[i2];
            if (lighterASTNode == null) {
                break;
            }
            IElementType tokenType = lighterASTNode.getTokenType();
            if (KtTokens.COMMENTS.contains(tokenType) || Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) || Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) || ArraysKt.contains(ktTokenArr, tokenType) || Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT)) {
                childrenAsArray = childrenAsArray;
                ktTokenArr = ktTokenArr;
            } else {
                IElementType tokenType2 = lighterASTNode.getTokenType();
                if (!Intrinsics.areEqual(tokenType2, KtTokens.DOT)) {
                    if (Intrinsics.areEqual(tokenType2, KtTokens.SAFE_ACCESS)) {
                        z2 = true;
                    } else {
                        boolean z4 = (!z3 || Intrinsics.areEqual(tokenType2, TokenType.ERROR_ELEMENT)) ? z : true;
                        String str = "Incorrect " + (z4 ? "selector" : "receiver") + " expression";
                        FirElement firElementConvertExpression = convertExpression(lighterASTNode, str);
                        FirAnnotationContainer firAnnotationContainer = (FirExpression) (!(firElementConvertExpression instanceof FirExpression) ? null : firElementConvertExpression);
                        if (firAnnotationContainer == null) {
                            FirAnnotationContainer firAnnotationContainerBuildErrorExpression = FirExpressionUtilKt.buildErrorExpression((KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(this, lighterASTNode, null, 1, null), new ConeSimpleDiagnostic(str, DiagnosticKind.ExpressionExpected), firElementConvertExpression);
                            if (firAnnotationContainerBuildErrorExpression == null) {
                                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirExpression");
                            }
                            firAnnotationContainer = (FirExpression) firAnnotationContainerBuildErrorExpression;
                        }
                        FirExpression firExpression2 = (FirExpression) firAnnotationContainer;
                        if (z4) {
                            LighterASTNode firstChildExpressionUnwrapped = Intrinsics.areEqual(tokenType2, KtNodeTypes.CALL_EXPRESSION) ? getFirstChildExpressionUnwrapped(lighterASTNode) : null;
                            T tMo6809build = firExpression2;
                            if (!(tokenType2 instanceof KtNameReferenceExpressionElementType)) {
                                if (Intrinsics.areEqual(tokenType2, KtNodeTypes.CALL_EXPRESSION)) {
                                    tMo6809build = firExpression2;
                                    if (Intrinsics.areEqual(firstChildExpressionUnwrapped != null ? firstChildExpressionUnwrapped.getTokenType() : null, KtNodeTypes.LAMBDA_EXPRESSION)) {
                                        FirErrorExpressionBuilder firErrorExpressionBuilder = new FirErrorExpressionBuilder();
                                        if (firstChildExpressionUnwrapped != null) {
                                            i = 1;
                                            ktFakeSourceElementKind = null;
                                            ktLightSourceElement = (KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(this, firstChildExpressionUnwrapped, null, 1, null);
                                            if (ktLightSourceElement != null) {
                                                firSourceElement$default = ktLightSourceElement;
                                            }
                                            firErrorExpressionBuilder.setSource(firSourceElement$default);
                                            if (firstChildExpressionUnwrapped == null) {
                                                diagnosticKind = DiagnosticKind.IllegalSelector;
                                            } else {
                                                diagnosticKind = DiagnosticKind.NoReceiverAllowed;
                                            }
                                            firErrorExpressionBuilder.setDiagnostic(new ConeSimpleDiagnostic("The expression cannot be a selector (occur after a dot)", diagnosticKind));
                                            firErrorExpressionBuilder.setExpression(firExpression2);
                                            tMo6809build = firErrorExpressionBuilder.mo6809build();
                                        } else {
                                            i = 1;
                                            ktFakeSourceElementKind = null;
                                        }
                                        firSourceElement$default = AbstractRawFirBuilder.toFirSourceElement$default(this, lighterASTNode, ktFakeSourceElementKind, i, ktFakeSourceElementKind);
                                        firErrorExpressionBuilder.setSource(firSourceElement$default);
                                        if (firstChildExpressionUnwrapped == null) {
                                            diagnosticKind = DiagnosticKind.IllegalSelector;
                                        } else {
                                            diagnosticKind = DiagnosticKind.NoReceiverAllowed;
                                        }
                                        firErrorExpressionBuilder.setDiagnostic(new ConeSimpleDiagnostic("The expression cannot be a selector (occur after a dot)", diagnosticKind));
                                        firErrorExpressionBuilder.setExpression(firExpression2);
                                        tMo6809build = firErrorExpressionBuilder.mo6809build();
                                    }
                                } else {
                                    FirErrorExpressionBuilder firErrorExpressionBuilder2 = new FirErrorExpressionBuilder();
                                    if (firstChildExpressionUnwrapped != null) {
                                        i = 1;
                                        ktFakeSourceElementKind = null;
                                        ktLightSourceElement = (KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(this, firstChildExpressionUnwrapped, null, 1, null);
                                        if (ktLightSourceElement != null) {
                                            firSourceElement$default = ktLightSourceElement;
                                        }
                                        firErrorExpressionBuilder2.setSource(firSourceElement$default);
                                        if (firstChildExpressionUnwrapped == null) {
                                            diagnosticKind = DiagnosticKind.IllegalSelector;
                                        } else {
                                            diagnosticKind = DiagnosticKind.NoReceiverAllowed;
                                        }
                                        firErrorExpressionBuilder2.setDiagnostic(new ConeSimpleDiagnostic("The expression cannot be a selector (occur after a dot)", diagnosticKind));
                                        firErrorExpressionBuilder2.setExpression(firExpression2);
                                        tMo6809build = firErrorExpressionBuilder2.mo6809build();
                                    } else {
                                        i = 1;
                                        ktFakeSourceElementKind = null;
                                    }
                                    firSourceElement$default = AbstractRawFirBuilder.toFirSourceElement$default(this, lighterASTNode, ktFakeSourceElementKind, i, ktFakeSourceElementKind);
                                    firErrorExpressionBuilder2.setSource(firSourceElement$default);
                                    if (firstChildExpressionUnwrapped == null) {
                                        diagnosticKind = DiagnosticKind.IllegalSelector;
                                    } else {
                                        diagnosticKind = DiagnosticKind.NoReceiverAllowed;
                                    }
                                    firErrorExpressionBuilder2.setDiagnostic(new ConeSimpleDiagnostic("The expression cannot be a selector (occur after a dot)", diagnosticKind));
                                    firErrorExpressionBuilder2.setExpression(firExpression2);
                                    tMo6809build = firErrorExpressionBuilder2.mo6809build();
                                }
                            }
                            objectRef.element = tMo6809build;
                        } else {
                            firExpression = firExpression2;
                        }
                    }
                }
                z3 = true;
            }
            i2++;
            childrenAsArray = childrenAsArray;
            ktTokenArr = ktTokenArr;
            z = false;
            firExpression = firExpression;
        }
        Object objConvertFirSelector = objectRef.element;
        T t = objectRef.element;
        FirQualifiedAccessExpression firQualifiedAccessExpression = t instanceof FirQualifiedAccessExpression ? (FirQualifiedAccessExpression) t : null;
        if (firQualifiedAccessExpression != null) {
            if (z2) {
                firQualifiedAccessExpression.replaceSource(toFirSourceElement(dotQualifiedExpression, (KtFakeSourceElementKind) KtFakeSourceElementKind.DesugaredSafeCallExpression.INSTANCE));
                Intrinsics.checkNotNull(firExpression);
                return ConversionUtilsKt.createSafeCall(firQualifiedAccessExpression, firExpression, AbstractRawFirBuilder.toFirSourceElement$default(this, dotQualifiedExpression, null, 1, null));
            }
            KtSourceElement firSourceElement$default2 = AbstractRawFirBuilder.toFirSourceElement$default(this, dotQualifiedExpression, null, 1, null);
            Intrinsics.checkNotNull(firExpression);
            objConvertFirSelector = convertFirSelector(firQualifiedAccessExpression, firSourceElement$default2, firExpression);
        }
        if (firExpression != null) {
            T t2 = objectRef.element;
            FirErrorExpression firErrorExpression = t2 instanceof FirErrorExpression ? (FirErrorExpression) t2 : null;
            if (firErrorExpression != null) {
                FirQualifiedErrorAccessExpressionBuilder firQualifiedErrorAccessExpressionBuilder = new FirQualifiedErrorAccessExpressionBuilder();
                firQualifiedErrorAccessExpressionBuilder.setReceiver(firExpression);
                firQualifiedErrorAccessExpressionBuilder.setSelector(firErrorExpression);
                firQualifiedErrorAccessExpressionBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(this, dotQualifiedExpression, null, 1, null));
                firQualifiedErrorAccessExpressionBuilder.setDiagnostic(new ConeSyntaxDiagnostic("Qualified expression with unexpected selector"));
                return firQualifiedErrorAccessExpressionBuilder.mo6809build();
            }
        }
        FirExpression firExpression3 = (FirExpression) objConvertFirSelector;
        if (firExpression3 != null) {
            return firExpression3;
        }
        FirErrorExpressionBuilder firErrorExpressionBuilder3 = new FirErrorExpressionBuilder();
        firErrorExpressionBuilder3.setSource(null);
        firErrorExpressionBuilder3.setDiagnostic(new ConeSyntaxDiagnostic("Qualified expression without selector"));
        firErrorExpressionBuilder3.setExpression(firExpression);
        return firErrorExpressionBuilder3.mo6809build();
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final FirExpression convertCallExpression(LighterASTNode callSuffix) {
        CalleeAndReceiver calleeAndReceiver;
        FirPropertyAccessExpressionBuilder firPropertyAccessExpressionBuilder;
        KtLightSourceElement ktLightSourceElement;
        Ref.ObjectRef objectRef = new Ref.ObjectRef();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
        Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        Ref.ObjectRef objectRef3 = new Ref.ObjectRef();
        KtToken[] ktTokenArr = new KtToken[0];
        LighterASTNode[] childrenAsArray = getChildrenAsArray(callSuffix);
        int length = childrenAsArray.length;
        int i = 0;
        while (i < length) {
            LighterASTNode lighterASTNode = childrenAsArray[i];
            if (lighterASTNode == null) {
                break;
            }
            IElementType tokenType = lighterASTNode.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr, tokenType) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT)) {
                convertCallExpression$lambda$37$process(objectRef, this, objectRef3, arrayList, booleanRef, arrayList2, objectRef2, lighterASTNode);
            }
            i++;
            length = length;
            childrenAsArray = childrenAsArray;
            ktTokenArr = ktTokenArr;
        }
        LightTreeRawFirExpressionBuilder lightTreeRawFirExpressionBuilder = this;
        KtLightSourceElement ktLightSourceElement2 = (KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(lightTreeRawFirExpressionBuilder, callSuffix, null, 1, null);
        if (objectRef.element == 0) {
            if (objectRef2.element == 0) {
                if (objectRef3.element != 0) {
                    FirErrorNamedReferenceBuilder firErrorNamedReferenceBuilder = new FirErrorNamedReferenceBuilder();
                    T t = objectRef3.element;
                    Intrinsics.checkNotNull(t);
                    firErrorNamedReferenceBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(lightTreeRawFirExpressionBuilder, (LighterASTNode) t, null, 1, null));
                    firErrorNamedReferenceBuilder.setDiagnostic(new ConeSimpleDiagnostic("Super cannot be a callee", DiagnosticKind.SuperNotAllowed));
                    calleeAndReceiver = new CalleeAndReceiver(firErrorNamedReferenceBuilder.build(), null, false, 6, null);
                } else {
                    FirErrorNamedReferenceBuilder firErrorNamedReferenceBuilder2 = new FirErrorNamedReferenceBuilder();
                    firErrorNamedReferenceBuilder2.setSource(ktLightSourceElement2);
                    firErrorNamedReferenceBuilder2.setDiagnostic(new ConeSyntaxDiagnostic("Call has no callee"));
                    calleeAndReceiver = new CalleeAndReceiver(firErrorNamedReferenceBuilder2.build(), null, false, 6, null);
                }
            } else {
                FirSimpleNamedReferenceBuilder firSimpleNamedReferenceBuilder = new FirSimpleNamedReferenceBuilder();
                firSimpleNamedReferenceBuilder.setSource(ktLightSourceElement2);
                firSimpleNamedReferenceBuilder.setName(OperatorNameConventions.INVOKE);
                FirNamedReference firNamedReferenceBuild = firSimpleNamedReferenceBuilder.build();
                T t2 = objectRef2.element;
                Intrinsics.checkNotNull(t2);
                calleeAndReceiver = new CalleeAndReceiver(firNamedReferenceBuild, (FirExpression) t2, true);
            }
        } else {
            FirSimpleNamedReferenceBuilder firSimpleNamedReferenceBuilder2 = new FirSimpleNamedReferenceBuilder();
            LighterASTNode firstChildExpressionUnwrapped = getFirstChildExpressionUnwrapped(callSuffix);
            firSimpleNamedReferenceBuilder2.setSource((firstChildExpressionUnwrapped == null || (ktLightSourceElement = (KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(lightTreeRawFirExpressionBuilder, firstChildExpressionUnwrapped, null, 1, null)) == null) ? ktLightSourceElement2 : ktLightSourceElement);
            firSimpleNamedReferenceBuilder2.setName(ConverterUtilKt.nameAsSafeName$default((String) objectRef.element, null, 1, null));
            calleeAndReceiver = new CalleeAndReceiver(firSimpleNamedReferenceBuilder2.build(), null, false, 6, null);
        }
        FirNamedReference reference = calleeAndReceiver.getReference();
        FirExpression receiverExpression = calleeAndReceiver.getReceiverExpression();
        boolean isImplicitInvoke = calleeAndReceiver.getIsImplicitInvoke();
        if (booleanRef.element) {
            FirAbstractFunctionCallBuilder firImplicitInvokeCallBuilder = isImplicitInvoke ? new FirImplicitInvokeCallBuilder() : new FirFunctionCallBuilder();
            firImplicitInvokeCallBuilder.setSource(ktLightSourceElement2);
            firImplicitInvokeCallBuilder.setCalleeReference(reference);
            getContext().getCalleeNamesForLambda().add(reference.getName());
            FirAbstractFunctionCallBuilder firAbstractFunctionCallBuilder = firImplicitInvokeCallBuilder;
            ArrayList arrayList3 = new ArrayList();
            Iterator it = arrayList2.iterator();
            while (it.hasNext()) {
                CollectionsKt.addAll(arrayList3, convertValueArguments((LighterASTNode) it.next()));
            }
            ConverterUtilKt.extractArgumentsFrom(firAbstractFunctionCallBuilder, arrayList3);
            removeLast(getContext().getCalleeNamesForLambda());
            firPropertyAccessExpressionBuilder = firImplicitInvokeCallBuilder;
        } else {
            FirPropertyAccessExpressionBuilder firPropertyAccessExpressionBuilder2 = new FirPropertyAccessExpressionBuilder();
            firPropertyAccessExpressionBuilder2.setSource(ktLightSourceElement2);
            firPropertyAccessExpressionBuilder2.setCalleeReference(reference);
            firPropertyAccessExpressionBuilder = firPropertyAccessExpressionBuilder2;
        }
        firPropertyAccessExpressionBuilder.setExplicitReceiver(receiverExpression);
        CollectionsKt.addAll(firPropertyAccessExpressionBuilder.getTypeArguments(), arrayList);
        return firPropertyAccessExpressionBuilder.build();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v27, types: [T, java.lang.String] */
    private static final void convertCallExpression$lambda$37$process(Ref.ObjectRef<String> objectRef, LightTreeRawFirExpressionBuilder lightTreeRawFirExpressionBuilder, Ref.ObjectRef<LighterASTNode> objectRef2, List<FirTypeProjection> list, Ref.BooleanRef booleanRef, List<LighterASTNode> list2, Ref.ObjectRef<FirExpression> objectRef3, LighterASTNode lighterASTNode) {
        ConeSimpleDiagnostic coneSimpleDiagnostic;
        Unit unit;
        ConeSimpleDiagnostic coneSimpleDiagnostic2;
        IElementType tokenType = lighterASTNode.getTokenType();
        if (Intrinsics.areEqual(tokenType, KtNodeTypes.REFERENCE_EXPRESSION)) {
            objectRef.element = lightTreeRawFirExpressionBuilder.getAsText(lighterASTNode);
            return;
        }
        if (Intrinsics.areEqual(tokenType, KtNodeTypes.SUPER_EXPRESSION)) {
            objectRef2.element = lighterASTNode;
            return;
        }
        if (Intrinsics.areEqual(tokenType, KtNodeTypes.PARENTHESIZED)) {
            LighterASTNode expressionInParentheses = lightTreeRawFirExpressionBuilder.getExpressionInParentheses(lighterASTNode);
            if (expressionInParentheses != null) {
                convertCallExpression$lambda$37$process(objectRef, lightTreeRawFirExpressionBuilder, objectRef2, list, booleanRef, list2, objectRef3, expressionInParentheses);
                unit = Unit.INSTANCE;
            } else {
                unit = null;
            }
            if (unit == null) {
                FirElement firElementConvertExpression = lighterASTNode != 0 ? lightTreeRawFirExpressionBuilder.convertExpression(lighterASTNode, "Incorrect invoke receiver") : null;
                FirExpression firExpression = (FirExpression) (!(firElementConvertExpression instanceof FirExpression) ? null : firElementConvertExpression);
                T t = firExpression;
                if (firExpression == null) {
                    KtLightSourceElement ktLightSourceElement = lighterASTNode != 0 ? (KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(lightTreeRawFirExpressionBuilder, lighterASTNode, null, 1, null) : null;
                    if (lighterASTNode == 0) {
                        coneSimpleDiagnostic2 = new ConeSyntaxDiagnostic("Incorrect invoke receiver");
                    } else {
                        coneSimpleDiagnostic2 = new ConeSimpleDiagnostic("Incorrect invoke receiver", DiagnosticKind.ExpressionExpected);
                    }
                    FirErrorExpression firErrorExpressionBuildErrorExpression = FirExpressionUtilKt.buildErrorExpression(ktLightSourceElement, coneSimpleDiagnostic2, firElementConvertExpression);
                    if (firErrorExpressionBuildErrorExpression == null) {
                        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirExpression");
                    }
                    t = firErrorExpressionBuildErrorExpression;
                }
                objectRef3.element = t;
                return;
            }
            return;
        }
        if (Intrinsics.areEqual(tokenType, KtNodeTypes.TYPE_ARGUMENT_LIST)) {
            CollectionsKt.addAll(list, lightTreeRawFirExpressionBuilder.declarationBuilder.convertTypeArguments(lighterASTNode, true));
            return;
        }
        if (Intrinsics.areEqual(tokenType, KtNodeTypes.VALUE_ARGUMENT_LIST) ? true : Intrinsics.areEqual(tokenType, KtNodeTypes.LAMBDA_ARGUMENT)) {
            booleanRef.element = true;
            list2.add(lighterASTNode);
            return;
        }
        if (Intrinsics.areEqual(lighterASTNode.getTokenType(), TokenType.ERROR_ELEMENT)) {
            return;
        }
        FirElement firElementConvertExpression2 = lighterASTNode != 0 ? lightTreeRawFirExpressionBuilder.convertExpression(lighterASTNode, "Incorrect invoke receiver") : null;
        FirExpression firExpression2 = (FirExpression) (!(firElementConvertExpression2 instanceof FirExpression) ? null : firElementConvertExpression2);
        T t2 = firExpression2;
        if (firExpression2 == null) {
            KtLightSourceElement ktLightSourceElement2 = lighterASTNode != 0 ? (KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(lightTreeRawFirExpressionBuilder, lighterASTNode, null, 1, null) : null;
            if (lighterASTNode == 0) {
                coneSimpleDiagnostic = new ConeSyntaxDiagnostic("Incorrect invoke receiver");
            } else {
                coneSimpleDiagnostic = new ConeSimpleDiagnostic("Incorrect invoke receiver", DiagnosticKind.ExpressionExpected);
            }
            FirErrorExpression firErrorExpressionBuildErrorExpression2 = FirExpressionUtilKt.buildErrorExpression(ktLightSourceElement2, coneSimpleDiagnostic, firElementConvertExpression2);
            if (firErrorExpressionBuildErrorExpression2 == null) {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirExpression");
            }
            t2 = firErrorExpressionBuildErrorExpression2;
        }
        objectRef3.element = t2;
    }

    private final FirExpression convertStringTemplate(LighterASTNode stringTemplate) {
        return AbstractRawFirBuilder.toInterpolatingCall$default(this, getChildrenAsArray(stringTemplate), stringTemplate, null, new Function2<LighterASTNode, String, FirExpression>() { // from class: org.jetbrains.kotlin.fir.lightTree.converter.LightTreeRawFirExpressionBuilder.convertStringTemplate.1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final FirExpression invoke(LighterASTNode lighterASTNode, String it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return LightTreeRawFirExpressionBuilder.this.convertShortOrLongStringTemplate(lighterASTNode, it);
            }
        }, 2, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final FirExpression convertShortOrLongStringTemplate(LighterASTNode lighterASTNode, String str) {
        FirErrorExpression firErrorExpression;
        if (lighterASTNode != null) {
            KtToken LONG_TEMPLATE_ENTRY_START = KtTokens.LONG_TEMPLATE_ENTRY_START;
            Intrinsics.checkNotNullExpressionValue(LONG_TEMPLATE_ENTRY_START, "LONG_TEMPLATE_ENTRY_START");
            KtToken LONG_TEMPLATE_ENTRY_END = KtTokens.LONG_TEMPLATE_ENTRY_END;
            Intrinsics.checkNotNullExpressionValue(LONG_TEMPLATE_ENTRY_END, "LONG_TEMPLATE_ENTRY_END");
            KtToken[] ktTokenArr = {LONG_TEMPLATE_ENTRY_START, LONG_TEMPLATE_ENTRY_END};
            firErrorExpression = null;
            for (LighterASTNode lighterASTNode2 : getChildrenAsArray(lighterASTNode)) {
                if (lighterASTNode2 == null) {
                    break;
                }
                IElementType tokenType = lighterASTNode2.getTokenType();
                if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr, tokenType) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT)) {
                    FirElement firElementConvertExpression = convertExpression(lighterASTNode2, str);
                    FirExpression firExpression = (FirExpression) (!(firElementConvertExpression instanceof FirExpression) ? null : firElementConvertExpression);
                    if (firExpression == null) {
                        FirErrorExpression firErrorExpressionBuildErrorExpression = FirExpressionUtilKt.buildErrorExpression((KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(this, lighterASTNode2, null, 1, null), new ConeSimpleDiagnostic(str, DiagnosticKind.ExpressionExpected), firElementConvertExpression);
                        if (firErrorExpressionBuildErrorExpression == null) {
                            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirExpression");
                        }
                        firErrorExpression = firErrorExpressionBuildErrorExpression;
                    } else {
                        firErrorExpression = firExpression;
                    }
                }
            }
        } else {
            firErrorExpression = null;
        }
        FirExpression firExpression2 = (FirExpression) firErrorExpression;
        return firExpression2 == null ? FirExpressionUtilKt.buildErrorExpression$default(null, new ConeSyntaxDiagnostic(str), null, 4, null) : firExpression2;
    }

    private final FirExpression convertConstantExpression(LighterASTNode constantExpression) {
        return generateConstantExpressionByLiteral(constantExpression);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r12v9, types: [T, org.jetbrains.kotlin.fir.declarations.FirProperty] */
    /* JADX WARN: Type inference failed for: r9v0 */
    /* JADX WARN: Type inference failed for: r9v1, types: [java.lang.Object, org.jetbrains.kotlin.KtFakeSourceElementKind, org.jetbrains.kotlin.fir.lightTree.fir.ClassWrapper] */
    /* JADX WARN: Type inference failed for: r9v13 */
    private final FirExpression convertWhenExpression(LighterASTNode whenExpression) {
        FirExpression initializer;
        FirWhenBranch firWhenBranchBuild;
        LighterASTNode lighterASTNode;
        FirErrorExpression firErrorExpression;
        Ref.ObjectRef objectRef = new Ref.ObjectRef();
        ArrayList arrayList = new ArrayList();
        ArrayList<WhenEntry> arrayList2 = new ArrayList();
        KtToken[] ktTokenArr = new KtToken[0];
        LighterASTNode[] childrenAsArray = getChildrenAsArray(whenExpression);
        int length = childrenAsArray.length;
        ?? r9 = 0;
        int i = 0;
        Object obj = null;
        while (i < length && (lighterASTNode = childrenAsArray[i]) != null) {
            IElementType tokenType = lighterASTNode.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr, tokenType) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT)) {
                IElementType tokenType2 = lighterASTNode.getTokenType();
                if (!Intrinsics.areEqual(tokenType2, KtNodeTypes.PROPERTY)) {
                    if (Intrinsics.areEqual(tokenType2, KtNodeTypes.DESTRUCTURING_DECLARATION)) {
                        String str = "Incorrect when subject expression: " + getAsText(whenExpression);
                        FirElement firElementConvertExpression = convertExpression(lighterASTNode, str);
                        firErrorExpression = (FirExpression) (!(firElementConvertExpression instanceof FirExpression) ? null : firElementConvertExpression);
                        if (firErrorExpression == null) {
                            FirErrorExpression firErrorExpressionBuildErrorExpression = FirExpressionUtilKt.buildErrorExpression((KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(this, lighterASTNode, null, 1, null), new ConeSimpleDiagnostic(str, DiagnosticKind.ExpressionExpected), firElementConvertExpression);
                            if (firErrorExpressionBuildErrorExpression == null) {
                                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirExpression");
                            }
                            firErrorExpression = firErrorExpressionBuildErrorExpression;
                        }
                    } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.WHEN_ENTRY)) {
                        arrayList.add(lighterASTNode);
                    } else if (ElementTypeUtils.INSTANCE.isExpression(lighterASTNode)) {
                        String str2 = "Incorrect when subject expression: " + getAsText(whenExpression);
                        FirElement firElementConvertExpression2 = convertExpression(lighterASTNode, str2);
                        firErrorExpression = (FirExpression) (!(firElementConvertExpression2 instanceof FirExpression) ? null : firElementConvertExpression2);
                        if (firErrorExpression == null) {
                            FirErrorExpression firErrorExpressionBuildErrorExpression2 = FirExpressionUtilKt.buildErrorExpression((KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(this, lighterASTNode, null, 1, null), new ConeSimpleDiagnostic(str2, DiagnosticKind.ExpressionExpected), firElementConvertExpression2);
                            if (firErrorExpressionBuildErrorExpression2 == null) {
                                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirExpression");
                            }
                            firErrorExpression = firErrorExpressionBuildErrorExpression2;
                        }
                    } else {
                        continue;
                    }
                    obj = firErrorExpression;
                } else {
                    FirDeclaration firDeclarationConvertPropertyDeclaration$default = LightTreeRawFirDeclarationBuilder.convertPropertyDeclaration$default(this.declarationBuilder, lighterASTNode, r9, 2, r9);
                    Intrinsics.checkNotNull(firDeclarationConvertPropertyDeclaration$default, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirVariable");
                    FirVariable firVariable = (FirVariable) firDeclarationConvertPropertyDeclaration$default;
                    FirPropertyBuilder firPropertyBuilder = new FirPropertyBuilder();
                    firPropertyBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(this, lighterASTNode, r9, 1, r9));
                    firPropertyBuilder.setOrigin(FirDeclarationOrigin.Source.INSTANCE);
                    firPropertyBuilder.setModuleData(getBaseModuleData());
                    firPropertyBuilder.setReturnTypeRef(firVariable.getReturnTypeRef());
                    firPropertyBuilder.setName(firVariable.getName());
                    firPropertyBuilder.setInitializer(firVariable.getInitializer());
                    firPropertyBuilder.setVar(false);
                    firPropertyBuilder.setSymbol(new FirPropertySymbol(firVariable.getName()));
                    firPropertyBuilder.setLocal(true);
                    firPropertyBuilder.setStatus(new FirDeclarationStatusImpl(Visibilities.Local.INSTANCE, Modality.FINAL));
                    objectRef.element = firPropertyBuilder.mo6809build();
                }
            }
            i++;
            r9 = 0;
        }
        FirVariable firVariable2 = (FirVariable) objectRef.element;
        if (firVariable2 == null || (initializer = firVariable2.getInitializer()) == null) {
            initializer = (FirExpression) obj;
        }
        boolean z = initializer != null;
        FirExpressionRef<FirWhenExpression> firExpressionRef = new FirExpressionRef<>();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            arrayList2.add(convertWhenEntry((LighterASTNode) it.next(), firExpressionRef, z));
        }
        FirWhenExpressionBuilder firWhenExpressionBuilder = new FirWhenExpressionBuilder();
        LightTreeRawFirExpressionBuilder lightTreeRawFirExpressionBuilder = this;
        firWhenExpressionBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(lightTreeRawFirExpressionBuilder, whenExpression, null, 1, null));
        firWhenExpressionBuilder.setSubject(initializer);
        firWhenExpressionBuilder.setSubjectVariable((FirVariable) objectRef.element);
        firWhenExpressionBuilder.setUsedAsExpression(getUsedAsExpression(whenExpression));
        boolean z2 = z;
        for (WhenEntry whenEntry : arrayList2) {
            z2 = z2 || whenEntry.getShouldBindSubject();
            FirBlock firBlock = whenEntry.getFirBlock();
            KtLightSourceElement ktLightSourceElement = (KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(lightTreeRawFirExpressionBuilder, whenEntry.getNode(), null, 1, null);
            List<FirWhenBranch> branches = firWhenExpressionBuilder.getBranches();
            if (whenEntry.isElse()) {
                FirWhenBranchBuilder firWhenBranchBuilder = new FirWhenBranchBuilder();
                firWhenBranchBuilder.setSource(ktLightSourceElement);
                FirElseIfTrueConditionBuilder firElseIfTrueConditionBuilder = new FirElseIfTrueConditionBuilder();
                Unit unit = Unit.INSTANCE;
                firWhenBranchBuilder.setCondition(firElseIfTrueConditionBuilder.mo6809build());
                firWhenBranchBuilder.setResult(firBlock);
                firWhenBranchBuild = firWhenBranchBuilder.build();
            } else if (z) {
                FirExpression firWhenCondition = whenEntry.toFirWhenCondition();
                FirWhenBranchBuilder firWhenBranchBuilder2 = new FirWhenBranchBuilder();
                firWhenBranchBuilder2.setSource(ktLightSourceElement);
                firWhenBranchBuilder2.setCondition(firWhenCondition);
                firWhenBranchBuilder2.setResult(firBlock);
                firWhenBranchBuild = firWhenBranchBuilder2.build();
            } else {
                FirExpression firWhenConditionWithoutSubject = whenEntry.toFirWhenConditionWithoutSubject();
                FirWhenBranchBuilder firWhenBranchBuilder3 = new FirWhenBranchBuilder();
                firWhenBranchBuilder3.setSource(ktLightSourceElement);
                firWhenBranchBuilder3.setCondition(firWhenConditionWithoutSubject);
                firWhenBranchBuilder3.setResult(firBlock);
                firWhenBranchBuild = firWhenBranchBuilder3.build();
            }
            branches.add(firWhenBranchBuild);
        }
        FirWhenExpression firWhenExpressionMo6809build = firWhenExpressionBuilder.mo6809build();
        if (z2) {
            firExpressionRef.bind(firWhenExpressionMo6809build);
        }
        return firWhenExpressionMo6809build;
    }

    /* JADX WARN: Code duplicated, block: B:30:0x0092  */
    private final WhenEntry convertWhenEntry(LighterASTNode whenEntry, FirExpressionRef<FirWhenExpression> whenRefWithSubject, boolean hasSubject) {
        FirBlock firBlockBuildEmptyExpressionBlock = FirEmptyExpressionBlockBuilderKt.buildEmptyExpressionBlock();
        ArrayList arrayList = new ArrayList();
        KtToken[] ktTokenArr = new KtToken[0];
        boolean z = false;
        boolean z2 = false;
        for (LighterASTNode lighterASTNode : getChildrenAsArray(whenEntry)) {
            if (lighterASTNode == null) {
                break;
            }
            IElementType tokenType = lighterASTNode.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr, tokenType) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT)) {
                IElementType tokenType2 = lighterASTNode.getTokenType();
                if (Intrinsics.areEqual(tokenType2, KtNodeTypes.WHEN_CONDITION_EXPRESSION)) {
                    arrayList.add(convertWhenConditionExpression(lighterASTNode, hasSubject ? whenRefWithSubject : null));
                } else {
                    boolean z3 = true;
                    if (Intrinsics.areEqual(tokenType2, KtNodeTypes.WHEN_CONDITION_IN_RANGE)) {
                        WhenConditionConvertedResults whenConditionConvertedResultsConvertWhenConditionInRange = convertWhenConditionInRange(lighterASTNode, whenRefWithSubject, hasSubject);
                        FirExpression expression = whenConditionConvertedResultsConvertWhenConditionInRange.getExpression();
                        boolean shouldBindSubject = whenConditionConvertedResultsConvertWhenConditionInRange.getShouldBindSubject();
                        arrayList.add(expression);
                        if (!z2 && !shouldBindSubject) {
                            z3 = false;
                        }
                        z2 = z3;
                    } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.WHEN_CONDITION_IS_PATTERN)) {
                        WhenConditionConvertedResults whenConditionConvertedResultsConvertWhenConditionIsPattern = convertWhenConditionIsPattern(lighterASTNode, whenRefWithSubject, hasSubject);
                        FirExpression expression2 = whenConditionConvertedResultsConvertWhenConditionIsPattern.getExpression();
                        boolean shouldBindSubject2 = whenConditionConvertedResultsConvertWhenConditionIsPattern.getShouldBindSubject();
                        arrayList.add(expression2);
                        if (!z2 && !shouldBindSubject2) {
                            z3 = false;
                        }
                        z2 = z3;
                    } else if (Intrinsics.areEqual(tokenType2, KtTokens.ELSE_KEYWORD)) {
                        z = true;
                    } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.BLOCK)) {
                        firBlockBuildEmptyExpressionBlock = this.declarationBuilder.convertBlock(lighterASTNode);
                    } else if (ElementTypeUtils.INSTANCE.isExpression(lighterASTNode)) {
                        firBlockBuildEmptyExpressionBlock = this.declarationBuilder.convertBlock(lighterASTNode);
                    }
                }
            }
        }
        return new WhenEntry(arrayList, firBlockBuildEmptyExpressionBlock, whenEntry, z, z2);
    }

    private final FirExpression convertWhenConditionExpression(LighterASTNode whenCondition, FirExpressionRef<FirWhenExpression> whenRefWithSubject) {
        LighterASTNode lighterASTNode;
        KtToken[] ktTokenArr = new KtToken[0];
        LighterASTNode[] childrenAsArray = getChildrenAsArray(whenCondition);
        int length = childrenAsArray.length;
        Object obj = null;
        for (int i = 0; i < length && (lighterASTNode = childrenAsArray[i]) != null; i++) {
            IElementType tokenType = lighterASTNode.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr, tokenType) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT)) {
                lighterASTNode.getTokenType();
                if (ElementTypeUtils.INSTANCE.isExpression(lighterASTNode)) {
                    FirElement firElementConvertExpression = convertExpression(lighterASTNode, "No expression in condition with expression");
                    FirErrorExpression firErrorExpression = (FirExpression) (!(firElementConvertExpression instanceof FirExpression) ? null : firElementConvertExpression);
                    if (firErrorExpression == null) {
                        FirErrorExpression firErrorExpressionBuildErrorExpression = FirExpressionUtilKt.buildErrorExpression((KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(this, lighterASTNode, null, 1, null), new ConeSimpleDiagnostic("No expression in condition with expression", DiagnosticKind.ExpressionExpected), firElementConvertExpression);
                        if (firErrorExpressionBuildErrorExpression == null) {
                            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirExpression");
                        }
                        firErrorExpression = firErrorExpressionBuildErrorExpression;
                    }
                    obj = firErrorExpression;
                } else {
                    continue;
                }
            }
        }
        FirErrorExpression firErrorExpressionBuildErrorExpression$default = (FirExpression) obj;
        if (firErrorExpressionBuildErrorExpression$default == null) {
            firErrorExpressionBuildErrorExpression$default = FirExpressionUtilKt.buildErrorExpression$default(null, new ConeSyntaxDiagnostic("No expression in condition with expression"), null, 4, null);
        }
        if (whenRefWithSubject == null) {
            return firErrorExpressionBuildErrorExpression$default;
        }
        FirEqualityOperatorCallBuilder firEqualityOperatorCallBuilder = new FirEqualityOperatorCallBuilder();
        firEqualityOperatorCallBuilder.setSource(toFirSourceElement(whenCondition, (KtFakeSourceElementKind) KtFakeSourceElementKind.WhenCondition.INSTANCE));
        firEqualityOperatorCallBuilder.setOperation(FirOperation.EQ);
        FirWhenSubjectExpressionBuilder firWhenSubjectExpressionBuilder = new FirWhenSubjectExpressionBuilder();
        firWhenSubjectExpressionBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(this, whenCondition, null, 1, null));
        firWhenSubjectExpressionBuilder.setWhenRef(whenRefWithSubject);
        firEqualityOperatorCallBuilder.setArgumentList(FirArgumentUtilKt.buildBinaryArgumentList(firWhenSubjectExpressionBuilder.mo6809build(), firErrorExpressionBuildErrorExpression$default));
        return firEqualityOperatorCallBuilder.mo6809build();
    }

    /* JADX INFO: compiled from: LightTreeRawFirExpressionBuilder.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u00052\b\u0010\u000f\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0010\u001a\u00020\u0011HÖ\u0001J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/fir/lightTree/converter/LightTreeRawFirExpressionBuilder$WhenConditionConvertedResults;", "", "expression", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "shouldBindSubject", "", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;Z)V", "getExpression", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getShouldBindSubject", "()Z", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "toString", "", "light-tree2fir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final /* data */ class WhenConditionConvertedResults {
        private final FirExpression expression;
        private final boolean shouldBindSubject;

        public static /* synthetic */ WhenConditionConvertedResults copy$default(WhenConditionConvertedResults whenConditionConvertedResults, FirExpression firExpression, boolean z, int i, Object obj) {
            if ((i & 1) != 0) {
                firExpression = whenConditionConvertedResults.expression;
            }
            if ((i & 2) != 0) {
                z = whenConditionConvertedResults.shouldBindSubject;
            }
            return whenConditionConvertedResults.copy(firExpression, z);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final FirExpression getExpression() {
            return this.expression;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final boolean getShouldBindSubject() {
            return this.shouldBindSubject;
        }

        public final WhenConditionConvertedResults copy(FirExpression expression, boolean shouldBindSubject) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return new WhenConditionConvertedResults(expression, shouldBindSubject);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof WhenConditionConvertedResults)) {
                return false;
            }
            WhenConditionConvertedResults whenConditionConvertedResults = (WhenConditionConvertedResults) other;
            return Intrinsics.areEqual(this.expression, whenConditionConvertedResults.expression) && this.shouldBindSubject == whenConditionConvertedResults.shouldBindSubject;
        }

        public int hashCode() {
            return (this.expression.hashCode() * 31) + Boolean.hashCode(this.shouldBindSubject);
        }

        public String toString() {
            return "WhenConditionConvertedResults(expression=" + this.expression + ", shouldBindSubject=" + this.shouldBindSubject + ')';
        }

        public WhenConditionConvertedResults(FirExpression expression, boolean z) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            this.expression = expression;
            this.shouldBindSubject = z;
        }

        public final FirExpression getExpression() {
            return this.expression;
        }

        public final boolean getShouldBindSubject() {
            return this.shouldBindSubject;
        }
    }

    private final WhenConditionConvertedResults convertWhenConditionInRange(LighterASTNode whenCondition, FirExpressionRef<FirWhenExpression> whenRefWithSubject, boolean hasSubject) {
        LighterASTNode lighterASTNode;
        KtToken[] ktTokenArr = new KtToken[0];
        LighterASTNode[] childrenAsArray = getChildrenAsArray(whenCondition);
        int length = childrenAsArray.length;
        boolean z = false;
        Object obj = null;
        KtSourceElement firSourceElement$default = null;
        for (int i = 0; i < length && (lighterASTNode = childrenAsArray[i]) != null; i++) {
            IElementType tokenType = lighterASTNode.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr, tokenType) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT)) {
                if (Intrinsics.areEqual(lighterASTNode.getTokenType(), KtNodeTypes.OPERATION_REFERENCE) && Intrinsics.areEqual(getAsText(lighterASTNode), KtTokens.NOT_IN.getValue())) {
                    firSourceElement$default = AbstractRawFirBuilder.toFirSourceElement$default(this, lighterASTNode, null, 1, null);
                    z = true;
                } else if (Intrinsics.areEqual(lighterASTNode.getTokenType(), KtNodeTypes.OPERATION_REFERENCE)) {
                    firSourceElement$default = AbstractRawFirBuilder.toFirSourceElement$default(this, lighterASTNode, null, 1, null);
                } else if (ElementTypeUtils.INSTANCE.isExpression(lighterASTNode)) {
                    FirElement firElementConvertExpression = convertExpression(lighterASTNode, "No range in condition with range");
                    FirErrorExpression firErrorExpression = (FirExpression) (!(firElementConvertExpression instanceof FirExpression) ? null : firElementConvertExpression);
                    if (firErrorExpression == null) {
                        FirErrorExpression firErrorExpressionBuildErrorExpression = FirExpressionUtilKt.buildErrorExpression((KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(this, lighterASTNode, null, 1, null), new ConeSimpleDiagnostic("No range in condition with range", DiagnosticKind.ExpressionExpected), firElementConvertExpression);
                        if (firErrorExpressionBuildErrorExpression == null) {
                            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirExpression");
                        }
                        firErrorExpression = firErrorExpressionBuildErrorExpression;
                    }
                    obj = firErrorExpression;
                } else {
                    continue;
                }
            }
        }
        FirWhenSubjectExpressionBuilder firWhenSubjectExpressionBuilder = new FirWhenSubjectExpressionBuilder();
        firWhenSubjectExpressionBuilder.setWhenRef(whenRefWithSubject);
        LightTreeRawFirExpressionBuilder lightTreeRawFirExpressionBuilder = this;
        firWhenSubjectExpressionBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(lightTreeRawFirExpressionBuilder, whenCondition, null, 1, null));
        FirWhenSubjectExpression firWhenSubjectExpressionMo6809build = firWhenSubjectExpressionBuilder.mo6809build();
        FirErrorExpression firErrorExpressionBuildErrorExpression$default = (FirExpression) obj;
        if (firErrorExpressionBuildErrorExpression$default == null) {
            firErrorExpressionBuildErrorExpression$default = FirExpressionUtilKt.buildErrorExpression$default(null, new ConeSyntaxDiagnostic("No range in condition with range"), null, 4, null);
        }
        return createWhenConditionConvertedResults(hasSubject, ConversionUtilsKt.generateContainsOperation(firErrorExpressionBuildErrorExpression$default, firWhenSubjectExpressionMo6809build, z, AbstractRawFirBuilder.toFirSourceElement$default(lightTreeRawFirExpressionBuilder, whenCondition, null, 1, null), firSourceElement$default), whenCondition);
    }

    private final WhenConditionConvertedResults convertWhenConditionIsPattern(LighterASTNode whenCondition, FirExpressionRef<FirWhenExpression> whenRefWithSubject, boolean hasSubject) {
        KtToken[] ktTokenArr = new KtToken[0];
        FirOperation firOperation = null;
        FirOperation firOperation2 = null;
        FirErrorTypeRef firErrorTypeRefMo6809build = null;
        for (LighterASTNode lighterASTNode : getChildrenAsArray(whenCondition)) {
            if (lighterASTNode == null) {
                break;
            }
            IElementType tokenType = lighterASTNode.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr, tokenType) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT)) {
                IElementType tokenType2 = lighterASTNode.getTokenType();
                if (Intrinsics.areEqual(tokenType2, KtNodeTypes.TYPE_REFERENCE)) {
                    firErrorTypeRefMo6809build = this.declarationBuilder.convertType(lighterASTNode);
                } else if (Intrinsics.areEqual(tokenType2, KtTokens.IS_KEYWORD)) {
                    firOperation2 = FirOperation.IS;
                } else if (Intrinsics.areEqual(tokenType2, KtTokens.NOT_IS)) {
                    firOperation2 = FirOperation.NOT_IS;
                }
            }
        }
        FirWhenSubjectExpressionBuilder firWhenSubjectExpressionBuilder = new FirWhenSubjectExpressionBuilder();
        LightTreeRawFirExpressionBuilder lightTreeRawFirExpressionBuilder = this;
        firWhenSubjectExpressionBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(lightTreeRawFirExpressionBuilder, whenCondition, null, 1, null));
        firWhenSubjectExpressionBuilder.setWhenRef(whenRefWithSubject);
        FirWhenSubjectExpression firWhenSubjectExpressionMo6809build = firWhenSubjectExpressionBuilder.mo6809build();
        FirTypeOperatorCallBuilder firTypeOperatorCallBuilder = new FirTypeOperatorCallBuilder();
        firTypeOperatorCallBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(lightTreeRawFirExpressionBuilder, whenCondition, null, 1, null));
        if (firOperation2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("firOperation");
        } else {
            firOperation = firOperation2;
        }
        firTypeOperatorCallBuilder.setOperation(firOperation);
        if (firErrorTypeRefMo6809build == null) {
            FirErrorTypeRefBuilder firErrorTypeRefBuilder = new FirErrorTypeRefBuilder();
            firErrorTypeRefBuilder.setDiagnostic(new ConeSyntaxDiagnostic("Incomplete code"));
            firErrorTypeRefMo6809build = firErrorTypeRefBuilder.mo6809build();
        }
        firTypeOperatorCallBuilder.setConversionTypeRef(firErrorTypeRefMo6809build);
        firTypeOperatorCallBuilder.setArgumentList(FirArgumentUtilKt.buildUnaryArgumentList(firWhenSubjectExpressionMo6809build));
        return createWhenConditionConvertedResults(hasSubject, firTypeOperatorCallBuilder.mo6809build(), whenCondition);
    }

    private final WhenConditionConvertedResults createWhenConditionConvertedResults(boolean hasSubject, FirExpression result, LighterASTNode whenCondition) {
        if (hasSubject) {
            return new WhenConditionConvertedResults(result, false);
        }
        FirErrorExpressionBuilder firErrorExpressionBuilder = new FirErrorExpressionBuilder();
        firErrorExpressionBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(this, whenCondition, null, 1, null));
        firErrorExpressionBuilder.setDiagnostic(new ConeSimpleDiagnostic("No expression in condition with expression", DiagnosticKind.ExpressionExpected));
        firErrorExpressionBuilder.setNonExpressionElement(result);
        return new WhenConditionConvertedResults(firErrorExpressionBuilder.mo6809build(), true);
    }

    private final FirExpression convertArrayAccessExpression(LighterASTNode arrayAccess) {
        LighterASTNode parent;
        LighterASTNode lighterASTNode;
        ArrayList arrayList = new ArrayList();
        KtToken[] ktTokenArr = new KtToken[0];
        LighterASTNode[] childrenAsArray = getChildrenAsArray(arrayAccess);
        int length = childrenAsArray.length;
        Object obj = null;
        for (int i = 0; i < length && (lighterASTNode = childrenAsArray[i]) != null; i++) {
            IElementType tokenType = lighterASTNode.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr, tokenType) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT)) {
                if (Intrinsics.areEqual(lighterASTNode.getTokenType(), KtNodeTypes.INDICES)) {
                    CollectionsKt.addAll(arrayList, convertIndices(lighterASTNode));
                } else if (ElementTypeUtils.INSTANCE.isExpression(lighterASTNode)) {
                    FirElement firElementConvertExpression = convertExpression(lighterASTNode, "No array expression");
                    FirErrorExpression firErrorExpression = (FirExpression) (!(firElementConvertExpression instanceof FirExpression) ? null : firElementConvertExpression);
                    if (firErrorExpression == null) {
                        FirErrorExpression firErrorExpressionBuildErrorExpression = FirExpressionUtilKt.buildErrorExpression((KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(this, lighterASTNode, null, 1, null), new ConeSimpleDiagnostic("No array expression", DiagnosticKind.ExpressionExpected), firElementConvertExpression);
                        if (firErrorExpressionBuildErrorExpression == null) {
                            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirExpression");
                        }
                        firErrorExpression = firErrorExpressionBuildErrorExpression;
                    }
                    obj = firErrorExpression;
                } else {
                    continue;
                }
            }
        }
        FirExpression firExpressionRemove = getContext().getArraySetArgument().remove(arrayAccess);
        FirFunctionCallBuilder firFunctionCallBuilder = new FirFunctionCallBuilder();
        boolean z = firExpressionRemove == null;
        LightTreeRawFirExpressionBuilder lightTreeRawFirExpressionBuilder = this;
        if (z) {
            parent = arrayAccess;
        } else {
            parent = getParent(arrayAccess);
            Intrinsics.checkNotNull(parent);
        }
        firFunctionCallBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(lightTreeRawFirExpressionBuilder, parent, null, 1, null));
        FirSimpleNamedReferenceBuilder firSimpleNamedReferenceBuilder = new FirSimpleNamedReferenceBuilder();
        firSimpleNamedReferenceBuilder.setSource(KtSourceElementKt.fakeElement$default(AbstractRawFirBuilder.toFirSourceElement$default(lightTreeRawFirExpressionBuilder, arrayAccess, null, 1, null), KtFakeSourceElementKind.ArrayAccessNameReference.INSTANCE, 0, 0, 6, null));
        firSimpleNamedReferenceBuilder.setName(z ? OperatorNameConventions.GET : OperatorNameConventions.SET);
        firFunctionCallBuilder.setCalleeReference(firSimpleNamedReferenceBuilder.build());
        FirErrorExpression firErrorExpressionBuildErrorExpression$default = (FirExpression) obj;
        if (firErrorExpressionBuildErrorExpression$default == null) {
            firErrorExpressionBuildErrorExpression$default = FirExpressionUtilKt.buildErrorExpression$default(null, new ConeSyntaxDiagnostic("No array expression"), null, 4, null);
        }
        firFunctionCallBuilder.setExplicitReceiver(firErrorExpressionBuildErrorExpression$default);
        FirArgumentListBuilder firArgumentListBuilder = new FirArgumentListBuilder();
        CollectionsKt.addAll(firArgumentListBuilder.getArguments(), arrayList);
        if (firExpressionRemove != null) {
            firArgumentListBuilder.getArguments().add(firExpressionRemove);
        }
        firFunctionCallBuilder.setArgumentList(firArgumentListBuilder.build());
        firFunctionCallBuilder.setOrigin(FirFunctionCallOrigin.Operator);
        return ConversionUtilsKt.pullUpSafeCallIfNecessary(firFunctionCallBuilder.mo6809build());
    }

    private final FirExpression convertCollectionLiteralExpression(LighterASTNode expression) {
        LighterASTNode lighterASTNode;
        ArrayList arrayList = new ArrayList();
        KtToken[] ktTokenArr = new KtToken[0];
        LighterASTNode[] childrenAsArray = getChildrenAsArray(expression);
        int length = childrenAsArray.length;
        for (int i = 0; i < length && (lighterASTNode = childrenAsArray[i]) != null; i++) {
            IElementType tokenType = lighterASTNode.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr, tokenType) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT) && ElementTypeUtils.INSTANCE.isExpression(lighterASTNode)) {
                ArrayList arrayList2 = arrayList;
                FirElement firElementConvertExpression = convertExpression(lighterASTNode, "Incorrect collection literal argument");
                FirAnnotationContainer firAnnotationContainer = (FirExpression) (!(firElementConvertExpression instanceof FirExpression) ? null : firElementConvertExpression);
                if (firAnnotationContainer == null) {
                    FirAnnotationContainer firAnnotationContainerBuildErrorExpression = FirExpressionUtilKt.buildErrorExpression((KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(this, lighterASTNode, null, 1, null), new ConeSimpleDiagnostic("Incorrect collection literal argument", DiagnosticKind.ExpressionExpected), firElementConvertExpression);
                    if (firAnnotationContainerBuildErrorExpression == null) {
                        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirExpression");
                    }
                    firAnnotationContainer = (FirExpression) firAnnotationContainerBuildErrorExpression;
                }
                arrayList2.add(firAnnotationContainer);
            }
        }
        FirArrayLiteralBuilder firArrayLiteralBuilder = new FirArrayLiteralBuilder();
        firArrayLiteralBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(this, expression, null, 1, null));
        FirArgumentListBuilder firArgumentListBuilder = new FirArgumentListBuilder();
        CollectionsKt.addAll(firArgumentListBuilder.getArguments(), arrayList);
        firArrayLiteralBuilder.setArgumentList(firArgumentListBuilder.build());
        return firArrayLiteralBuilder.mo6809build();
    }

    private final List<FirExpression> convertIndices(LighterASTNode indices) {
        ArrayList arrayList = new ArrayList();
        KtToken[] ktTokenArr = new KtToken[0];
        for (LighterASTNode lighterASTNode : getChildrenAsArray(indices)) {
            if (lighterASTNode == null) {
                break;
            }
            IElementType tokenType = lighterASTNode.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr, tokenType) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT) && ElementTypeUtils.INSTANCE.isExpression(lighterASTNode)) {
                ArrayList arrayList2 = arrayList;
                FirElement firElementConvertExpression = convertExpression(lighterASTNode, "Incorrect index expression");
                FirAnnotationContainer firAnnotationContainer = (FirExpression) (!(firElementConvertExpression instanceof FirExpression) ? null : firElementConvertExpression);
                if (firAnnotationContainer == null) {
                    FirAnnotationContainer firAnnotationContainerBuildErrorExpression = FirExpressionUtilKt.buildErrorExpression((KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(this, lighterASTNode, null, 1, null), new ConeSimpleDiagnostic("Incorrect index expression", DiagnosticKind.ExpressionExpected), firElementConvertExpression);
                    if (firAnnotationContainerBuildErrorExpression == null) {
                        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirExpression");
                    }
                    firAnnotationContainer = (FirExpression) firAnnotationContainerBuildErrorExpression;
                }
                arrayList2.add(firAnnotationContainer);
            }
        }
        return arrayList;
    }

    private final FirQualifiedAccessExpression convertSimpleNameExpression(LighterASTNode referenceExpression) {
        KtLightSourceElement ktLightSourceElementFakeElement$default;
        KtLightSourceElement ktLightSourceElement = (KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(this, referenceExpression, null, 1, null);
        if (ktLightSourceElement.getKind() instanceof KtFakeSourceElementKind) {
            ktLightSourceElementFakeElement$default = ktLightSourceElement;
        } else {
            ktLightSourceElementFakeElement$default = KtSourceElementKt.fakeElement$default(ktLightSourceElement, KtFakeSourceElementKind.ReferenceInAtomicQualifiedAccess.INSTANCE, 0, 0, 6, null);
        }
        FirPropertyAccessExpressionBuilder firPropertyAccessExpressionBuilder = new FirPropertyAccessExpressionBuilder();
        if (ConversionUtilsKt.isUnderscore(getAsText(referenceExpression))) {
            firPropertyAccessExpressionBuilder.getNonFatalDiagnostics().add(new ConeUnderscoreUsageWithoutBackticks(ktLightSourceElement));
        }
        firPropertyAccessExpressionBuilder.setSource(ktLightSourceElement);
        firPropertyAccessExpressionBuilder.setCalleeReference(createSimpleNamedReference(ktLightSourceElementFakeElement$default, referenceExpression));
        return firPropertyAccessExpressionBuilder.mo6809build();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r14v0, types: [T, java.lang.Object, org.jetbrains.kotlin.com.intellij.lang.LighterASTNode] */
    private final FirElement convertDoWhile(LighterASTNode doWhileLoop) {
        ?? r14;
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        FirDoWhileLoopBuilder firDoWhileLoopBuilder = new FirDoWhileLoopBuilder();
        LightTreeRawFirExpressionBuilder lightTreeRawFirExpressionBuilder = this;
        firDoWhileLoopBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(lightTreeRawFirExpressionBuilder, doWhileLoop, null, 1, null));
        FirDoWhileLoopBuilder firDoWhileLoopBuilder2 = firDoWhileLoopBuilder;
        FirLoopTarget firLoopTargetPrepareTarget = prepareTarget(firDoWhileLoopBuilder2, doWhileLoop);
        KtToken[] ktTokenArr = new KtToken[0];
        LighterASTNode[] childrenAsArray = getChildrenAsArray(doWhileLoop);
        int length = childrenAsArray.length;
        FirErrorExpression firErrorExpression = null;
        for (int i = 0; i < length && (r14 = childrenAsArray[i]) != 0; i++) {
            IElementType tokenType = r14.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr, tokenType) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT)) {
                IElementType tokenType2 = r14.getTokenType();
                if (Intrinsics.areEqual(tokenType2, KtNodeTypes.BODY)) {
                    objectRef.element = r14;
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.CONDITION)) {
                    FirElement firElementConvertExpression = convertExpression(r14, "No condition in do-while loop");
                    firErrorExpression = (FirExpression) (!(firElementConvertExpression instanceof FirExpression) ? null : firElementConvertExpression);
                    if (firErrorExpression != null) {
                        continue;
                    } else {
                        FirErrorExpression firErrorExpressionBuildErrorExpression = FirExpressionUtilKt.buildErrorExpression((KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(lightTreeRawFirExpressionBuilder, r14, null, 1, null), new ConeSimpleDiagnostic("No condition in do-while loop", DiagnosticKind.ExpressionExpected), firElementConvertExpression);
                        if (firErrorExpressionBuildErrorExpression == null) {
                            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirExpression");
                        }
                        firErrorExpression = firErrorExpressionBuildErrorExpression;
                    }
                } else {
                    continue;
                }
            }
        }
        FirErrorExpression firErrorExpressionBuildErrorExpression$default = (FirExpression) firErrorExpression;
        if (firErrorExpressionBuildErrorExpression$default == null) {
            firErrorExpressionBuildErrorExpression$default = FirExpressionUtilKt.buildErrorExpression$default(null, new ConeSyntaxDiagnostic("No condition in do-while loop"), null, 4, null);
        }
        firDoWhileLoopBuilder.setCondition(firErrorExpressionBuildErrorExpression$default);
        return configure(firDoWhileLoopBuilder2, firLoopTargetPrepareTarget, new Function0<FirBlock>() { // from class: org.jetbrains.kotlin.fir.lightTree.converter.LightTreeRawFirExpressionBuilder.convertDoWhile.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final FirBlock invoke() {
                return LightTreeRawFirExpressionBuilder.this.convertLoopBody(objectRef.element);
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r9v0, types: [T, java.lang.Object, org.jetbrains.kotlin.com.intellij.lang.LighterASTNode] */
    private final FirElement convertWhile(LighterASTNode whileLoop) {
        ?? r9;
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        KtToken[] ktTokenArr = new KtToken[0];
        LighterASTNode[] childrenAsArray = getChildrenAsArray(whileLoop);
        int length = childrenAsArray.length;
        Object obj = null;
        for (int i = 0; i < length && (r9 = childrenAsArray[i]) != 0; i++) {
            IElementType tokenType = r9.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr, tokenType) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT)) {
                IElementType tokenType2 = r9.getTokenType();
                if (Intrinsics.areEqual(tokenType2, KtNodeTypes.BODY)) {
                    objectRef.element = r9;
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.CONDITION)) {
                    FirElement firElementConvertExpression = convertExpression(r9, "No condition in while loop");
                    FirErrorExpression firErrorExpression = (FirExpression) (!(firElementConvertExpression instanceof FirExpression) ? null : firElementConvertExpression);
                    if (firErrorExpression == null) {
                        FirErrorExpression firErrorExpressionBuildErrorExpression = FirExpressionUtilKt.buildErrorExpression((KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(this, r9, null, 1, null), new ConeSimpleDiagnostic("No condition in while loop", DiagnosticKind.ExpressionExpected), firElementConvertExpression);
                        if (firErrorExpressionBuildErrorExpression == null) {
                            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirExpression");
                        }
                        firErrorExpression = firErrorExpressionBuildErrorExpression;
                    }
                    obj = firErrorExpression;
                } else {
                    continue;
                }
            }
        }
        FirWhileLoopBuilder firWhileLoopBuilder = new FirWhileLoopBuilder();
        firWhileLoopBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(this, whileLoop, null, 1, null));
        FirErrorExpression firErrorExpressionBuildErrorExpression$default = (FirExpression) obj;
        if (firErrorExpressionBuildErrorExpression$default == null) {
            firErrorExpressionBuildErrorExpression$default = FirExpressionUtilKt.buildErrorExpression$default(null, new ConeSyntaxDiagnostic("No condition in while loop"), null, 4, null);
        }
        firWhileLoopBuilder.setCondition(firErrorExpressionBuildErrorExpression$default);
        FirWhileLoopBuilder firWhileLoopBuilder2 = firWhileLoopBuilder;
        return configure(firWhileLoopBuilder2, prepareTarget(firWhileLoopBuilder2, whileLoop), new Function0<FirBlock>() { // from class: org.jetbrains.kotlin.fir.lightTree.converter.LightTreeRawFirExpressionBuilder.convertWhile.3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final FirBlock invoke() {
                return LightTreeRawFirExpressionBuilder.this.convertLoopBody(objectRef.element);
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v6, types: [T, org.jetbrains.kotlin.fir.lightTree.fir.ValueParameter] */
    /* JADX WARN: Type inference failed for: r12v4, types: [T, java.lang.Object, org.jetbrains.kotlin.com.intellij.lang.LighterASTNode] */
    private final FirElement convertFor(LighterASTNode forLoop) {
        ?? r12;
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        final Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
        KtToken[] ktTokenArr = new KtToken[0];
        LighterASTNode[] childrenAsArray = getChildrenAsArray(forLoop);
        int length = childrenAsArray.length;
        Object obj = null;
        for (int i = 0; i < length && (r12 = childrenAsArray[i]) != 0; i++) {
            IElementType tokenType = r12.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr, tokenType) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT)) {
                IElementType tokenType2 = r12.getTokenType();
                if (Intrinsics.areEqual(tokenType2, KtNodeTypes.VALUE_PARAMETER)) {
                    objectRef.element = LightTreeRawFirDeclarationBuilder.convertValueParameter$default(this.declarationBuilder, r12, null, AbstractRawFirBuilder.ValueParameterDeclaration.FOR_LOOP, null, 8, null);
                } else if (!Intrinsics.areEqual(tokenType2, KtNodeTypes.LOOP_RANGE)) {
                    if (Intrinsics.areEqual(tokenType2, KtNodeTypes.BODY)) {
                        objectRef2.element = r12;
                    }
                } else {
                    FirElement firElementConvertExpression = convertExpression(r12, "No range in for loop");
                    FirErrorExpression firErrorExpression = (FirExpression) (!(firElementConvertExpression instanceof FirExpression) ? null : firElementConvertExpression);
                    if (firErrorExpression == null) {
                        FirErrorExpression firErrorExpressionBuildErrorExpression = FirExpressionUtilKt.buildErrorExpression((KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(this, r12, null, 1, null), new ConeSimpleDiagnostic("No range in for loop", DiagnosticKind.ExpressionExpected), firElementConvertExpression);
                        if (firErrorExpressionBuildErrorExpression == null) {
                            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirExpression");
                        }
                        firErrorExpression = firErrorExpressionBuildErrorExpression;
                    }
                    obj = firErrorExpression;
                }
            }
        }
        FirErrorExpression firErrorExpressionBuildErrorExpression$default = (FirExpression) obj;
        if (firErrorExpressionBuildErrorExpression$default == null) {
            firErrorExpressionBuildErrorExpression$default = FirExpressionUtilKt.buildErrorExpression$default(null, new ConeSyntaxDiagnostic("No range in for loop"), null, 4, null);
        }
        final KtLightSourceElement firSourceElement = toFirSourceElement(forLoop, (KtFakeSourceElementKind) KtFakeSourceElementKind.DesugaredForLoop.INSTANCE);
        FirBlockBuilder firBlockBuilder = new FirBlockBuilder();
        KtLightSourceElement ktLightSourceElement = firSourceElement;
        firBlockBuilder.setSource(ktLightSourceElement);
        FirModuleData baseModuleData = getBaseModuleData();
        KtSourceElement source = firErrorExpressionBuildErrorExpression$default.getSource();
        KtSourceElement ktSourceElementFakeElement$default = source != null ? KtSourceElementKt.fakeElement$default(source, KtFakeSourceElementKind.DesugaredForLoop.INSTANCE, 0, 0, 6, null) : null;
        Name name2 = SpecialNames.ITERATOR;
        FirFunctionCallBuilder firFunctionCallBuilder = new FirFunctionCallBuilder();
        firFunctionCallBuilder.setSource(ktLightSourceElement);
        FirSimpleNamedReferenceBuilder firSimpleNamedReferenceBuilder = new FirSimpleNamedReferenceBuilder();
        firSimpleNamedReferenceBuilder.setSource(ktLightSourceElement);
        firSimpleNamedReferenceBuilder.setName(OperatorNameConventions.ITERATOR);
        firFunctionCallBuilder.setCalleeReference(firSimpleNamedReferenceBuilder.build());
        firFunctionCallBuilder.setExplicitReceiver(firErrorExpressionBuildErrorExpression$default);
        Unit unit = Unit.INSTANCE;
        final FirProperty firPropertyGenerateTemporaryVariable$default = FirGenerationKt.generateTemporaryVariable$default(baseModuleData, ktSourceElementFakeElement$default, name2, firFunctionCallBuilder.mo6809build(), null, null, 48, null);
        firBlockBuilder.getStatements().add(firPropertyGenerateTemporaryVariable$default);
        List<FirStatement> statements = firBlockBuilder.getStatements();
        FirWhileLoopBuilder firWhileLoopBuilder = new FirWhileLoopBuilder();
        firWhileLoopBuilder.setSource(ktLightSourceElement);
        FirFunctionCallBuilder firFunctionCallBuilder2 = new FirFunctionCallBuilder();
        firFunctionCallBuilder2.setSource(ktLightSourceElement);
        FirSimpleNamedReferenceBuilder firSimpleNamedReferenceBuilder2 = new FirSimpleNamedReferenceBuilder();
        firSimpleNamedReferenceBuilder2.setSource(ktLightSourceElement);
        firSimpleNamedReferenceBuilder2.setName(OperatorNameConventions.HAS_NEXT);
        firFunctionCallBuilder2.setCalleeReference(firSimpleNamedReferenceBuilder2.build());
        firFunctionCallBuilder2.setExplicitReceiver(ConversionUtilsKt.generateResolvedAccessExpression(ktLightSourceElement, firPropertyGenerateTemporaryVariable$default));
        firWhileLoopBuilder.setCondition(firFunctionCallBuilder2.mo6809build());
        FirWhileLoopBuilder firWhileLoopBuilder2 = firWhileLoopBuilder;
        statements.add(configure(firWhileLoopBuilder2, prepareTarget(firWhileLoopBuilder2, forLoop), new Function0<FirBlock>() { // from class: org.jetbrains.kotlin.fir.lightTree.converter.LightTreeRawFirExpressionBuilder$convertFor$2$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final FirBlock invoke() {
                Ref.ObjectRef<LighterASTNode> objectRef3 = objectRef2;
                LightTreeRawFirExpressionBuilder lightTreeRawFirExpressionBuilder = this;
                Ref.ObjectRef<ValueParameter> objectRef4 = objectRef;
                KtLightSourceElement ktLightSourceElement2 = firSourceElement;
                FirProperty firProperty = firPropertyGenerateTemporaryVariable$default;
                FirBlockBuilder firBlockBuilder2 = new FirBlockBuilder();
                LighterASTNode lighterASTNode = objectRef3.element;
                firBlockBuilder2.setSource(lighterASTNode != null ? (KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(lightTreeRawFirExpressionBuilder, lighterASTNode, null, 1, null) : null);
                ValueParameter valueParameter = objectRef4.element;
                if (valueParameter != null) {
                    DestructuringDeclaration destructuringDeclaration = valueParameter.getDestructuringDeclaration();
                    FirModuleData baseModuleData2 = lightTreeRawFirExpressionBuilder.getBaseModuleData();
                    KtSourceElement source2 = valueParameter.getSource();
                    Name name3 = destructuringDeclaration != null ? SpecialNames.DESTRUCT : valueParameter.getName();
                    FirFunctionCallBuilder firFunctionCallBuilder3 = new FirFunctionCallBuilder();
                    KtLightSourceElement ktLightSourceElement3 = ktLightSourceElement2;
                    firFunctionCallBuilder3.setSource(ktLightSourceElement3);
                    FirSimpleNamedReferenceBuilder firSimpleNamedReferenceBuilder3 = new FirSimpleNamedReferenceBuilder();
                    firSimpleNamedReferenceBuilder3.setSource(ktLightSourceElement3);
                    firSimpleNamedReferenceBuilder3.setName(OperatorNameConventions.NEXT);
                    firFunctionCallBuilder3.setCalleeReference(firSimpleNamedReferenceBuilder3.build());
                    firFunctionCallBuilder3.setExplicitReceiver(ConversionUtilsKt.generateResolvedAccessExpression(ktLightSourceElement3, firProperty));
                    Unit unit2 = Unit.INSTANCE;
                    FirProperty firPropertyGenerateTemporaryVariable$default2 = FirGenerationKt.generateTemporaryVariable$default(baseModuleData2, source2, name3, firFunctionCallBuilder3.mo6809build(), valueParameter.getReturnTypeRef(), null, 32, null);
                    if (destructuringDeclaration != null) {
                        firBlockBuilder2.getStatements().addAll(ConverterUtilKt.generateDestructuringBlock(lightTreeRawFirExpressionBuilder.getBaseModuleData(), destructuringDeclaration, firPropertyGenerateTemporaryVariable$default2, true).getStatements());
                    } else {
                        firBlockBuilder2.getStatements().add(firPropertyGenerateTemporaryVariable$default2);
                    }
                    firBlockBuilder2.getStatements().add(lightTreeRawFirExpressionBuilder.convertLoopBody(objectRef3.element));
                }
                return firBlockBuilder2.mo6809build();
            }
        }));
        return firBlockBuilder.mo6809build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final FirBlock convertLoopBody(LighterASTNode body) {
        FirBlock firBlockConvertLoopOrIfBody = convertLoopOrIfBody(body);
        return firBlockConvertLoopOrIfBody == null ? FirEmptyExpressionBlockBuilderKt.buildEmptyExpressionBlock() : firBlockConvertLoopOrIfBody;
    }

    private final FirBlock convertLoopOrIfBody(LighterASTNode body) {
        FirBlock firBlockConvertBlockExpression;
        FirErrorExpression firErrorExpression;
        Object obj = null;
        if (body != null) {
            KtToken[] ktTokenArr = new KtToken[0];
            firBlockConvertBlockExpression = null;
            Object obj2 = null;
            for (LighterASTNode lighterASTNode : getChildrenAsArray(body)) {
                if (lighterASTNode == null) {
                    break;
                }
                IElementType tokenType = lighterASTNode.getTokenType();
                if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr, tokenType) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT)) {
                    IElementType tokenType2 = lighterASTNode.getTokenType();
                    if (Intrinsics.areEqual(tokenType2, KtNodeTypes.BLOCK)) {
                        firBlockConvertBlockExpression = this.declarationBuilder.convertBlockExpression(lighterASTNode);
                    } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.ANNOTATED_EXPRESSION)) {
                        IElementType BLOCK = KtNodeTypes.BLOCK;
                        Intrinsics.checkNotNullExpressionValue(BLOCK, "BLOCK");
                        if (getChildNodeByType(lighterASTNode, BLOCK) != null) {
                            FirElement firElementConvertExpression = convertExpression(lighterASTNode, "");
                            FirBlock firBlock = (FirBlock) (!(firElementConvertExpression instanceof FirBlock) ? null : firElementConvertExpression);
                            if (firBlock == null) {
                                FirExpression firExpressionBuildErrorExpression = FirExpressionUtilKt.buildErrorExpression((KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(this, lighterASTNode, null, 1, null), new ConeSimpleDiagnostic("", DiagnosticKind.ExpressionExpected), firElementConvertExpression);
                                if (firExpressionBuildErrorExpression == null) {
                                    throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirBlock");
                                }
                                firBlock = (FirBlock) firExpressionBuildErrorExpression;
                            }
                            firBlockConvertBlockExpression = firBlock;
                        } else {
                            FirElement firElementConvertExpression2 = convertExpression(lighterASTNode, "");
                            firErrorExpression = (FirStatement) (!(firElementConvertExpression2 instanceof FirStatement) ? null : firElementConvertExpression2);
                            if (firErrorExpression == null) {
                                FirErrorExpression firErrorExpressionBuildErrorExpression = FirExpressionUtilKt.buildErrorExpression((KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(this, lighterASTNode, null, 1, null), new ConeSimpleDiagnostic("", DiagnosticKind.ExpressionExpected), firElementConvertExpression2);
                                if (firErrorExpressionBuildErrorExpression == null) {
                                    throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirStatement");
                                }
                                firErrorExpression = firErrorExpressionBuildErrorExpression;
                            }
                            obj2 = firErrorExpression;
                        }
                    } else if (ElementTypeUtils.INSTANCE.isExpression(lighterASTNode)) {
                        FirElement firElementConvertExpression3 = convertExpression(lighterASTNode, "");
                        firErrorExpression = (FirStatement) (!(firElementConvertExpression3 instanceof FirStatement) ? null : firElementConvertExpression3);
                        if (firErrorExpression == null) {
                            FirErrorExpression firErrorExpressionBuildErrorExpression2 = FirExpressionUtilKt.buildErrorExpression((KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(this, lighterASTNode, null, 1, null), new ConeSimpleDiagnostic("", DiagnosticKind.ExpressionExpected), firElementConvertExpression3);
                            if (firErrorExpressionBuildErrorExpression2 == null) {
                                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirStatement");
                            }
                            firErrorExpression = firErrorExpressionBuildErrorExpression2;
                        }
                        obj2 = firErrorExpression;
                    } else {
                        continue;
                    }
                }
            }
            obj = obj2;
        } else {
            firBlockConvertBlockExpression = null;
        }
        FirStatement firStatement = (FirStatement) obj;
        return firStatement != null ? new FirSingleExpressionBlock(firStatement) : firBlockConvertBlockExpression;
    }

    private final FirExpression convertTryExpression(LighterASTNode tryExpression) {
        ArrayList<Triple> arrayList = new ArrayList();
        KtToken[] ktTokenArr = new KtToken[0];
        FirBlock firBlockConvertBlock = null;
        FirBlock firBlockConvertFinally = null;
        for (LighterASTNode lighterASTNode : getChildrenAsArray(tryExpression)) {
            if (lighterASTNode == null) {
                break;
            }
            IElementType tokenType = lighterASTNode.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr, tokenType) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT)) {
                IElementType tokenType2 = lighterASTNode.getTokenType();
                if (Intrinsics.areEqual(tokenType2, KtNodeTypes.BLOCK)) {
                    firBlockConvertBlock = this.declarationBuilder.convertBlock(lighterASTNode);
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.CATCH)) {
                    Triple<ValueParameter, FirBlock, KtLightSourceElement> tripleConvertCatchClause = convertCatchClause(lighterASTNode);
                    if (tripleConvertCatchClause != null) {
                        arrayList.add(tripleConvertCatchClause);
                    }
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.FINALLY)) {
                    firBlockConvertFinally = convertFinally(lighterASTNode);
                }
            }
        }
        FirTryExpressionBuilder firTryExpressionBuilder = new FirTryExpressionBuilder();
        firTryExpressionBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(this, tryExpression, null, 1, null));
        if (firBlockConvertBlock == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tryBlock");
            firBlockConvertBlock = null;
        }
        firTryExpressionBuilder.setTryBlock(firBlockConvertBlock);
        firTryExpressionBuilder.setFinallyBlock(firBlockConvertFinally);
        for (Triple triple : arrayList) {
            ValueParameter valueParameter = (ValueParameter) triple.component1();
            FirBlock firBlock = (FirBlock) triple.component2();
            KtLightSourceElement ktLightSourceElement = (KtLightSourceElement) triple.component3();
            if (valueParameter != null) {
                List<FirCatch> catches = firTryExpressionBuilder.getCatches();
                FirCatchBuilder firCatchBuilder = new FirCatchBuilder();
                FirPropertyBuilder firPropertyBuilder = new FirPropertyBuilder();
                firPropertyBuilder.setSource(valueParameter.getSource());
                firPropertyBuilder.setModuleData(getBaseModuleData());
                firPropertyBuilder.setOrigin(FirDeclarationOrigin.Source.INSTANCE);
                firPropertyBuilder.setReturnTypeRef(valueParameter.getReturnTypeRef());
                firPropertyBuilder.setVar(false);
                firPropertyBuilder.setStatus(new FirResolvedDeclarationStatusImpl(Visibilities.Local.INSTANCE, Modality.FINAL, EffectiveVisibility.Local.INSTANCE));
                firPropertyBuilder.setLocal(true);
                firPropertyBuilder.setName(valueParameter.getName());
                firPropertyBuilder.setSymbol(new FirPropertySymbol(new CallableId(firPropertyBuilder.getName(), (FqName) null, 2, (DefaultConstructorMarker) null)));
                CollectionsKt.addAll(firPropertyBuilder.getAnnotations(), valueParameter.getAnnotations());
                FirProperty firPropertyMo6809build = firPropertyBuilder.mo6809build();
                ClassMembersKt.setCatchParameter(firPropertyMo6809build, true);
                firCatchBuilder.setParameter(firPropertyMo6809build);
                firCatchBuilder.setBlock(firBlock);
                firCatchBuilder.setSource(ktLightSourceElement);
                catches.add(firCatchBuilder.build());
            }
        }
        return firTryExpressionBuilder.mo6809build();
    }

    private final Triple<ValueParameter, FirBlock, KtLightSourceElement> convertCatchClause(LighterASTNode catchClause) {
        KtToken[] ktTokenArr = new KtToken[0];
        ValueParameter valueParameter = null;
        LighterASTNode lighterASTNode = null;
        for (LighterASTNode lighterASTNode2 : getChildrenAsArray(catchClause)) {
            if (lighterASTNode2 == null) {
                break;
            }
            IElementType tokenType = lighterASTNode2.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr, tokenType) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT)) {
                IElementType tokenType2 = lighterASTNode2.getTokenType();
                if (Intrinsics.areEqual(tokenType2, KtNodeTypes.VALUE_PARAMETER_LIST)) {
                    valueParameter = (ValueParameter) CollectionsKt.firstOrNull(LightTreeRawFirDeclarationBuilder.convertValueParameters$default(this.declarationBuilder, lighterASTNode2, new FirAnonymousFunctionSymbol(), AbstractRawFirBuilder.ValueParameterDeclaration.CATCH, null, 8, null));
                    if (valueParameter == null) {
                        return null;
                    }
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.BLOCK)) {
                    lighterASTNode = lighterASTNode2;
                }
            }
        }
        return new Triple<>(valueParameter, this.declarationBuilder.convertBlock(lighterASTNode), AbstractRawFirBuilder.toFirSourceElement$default(this, catchClause, null, 1, null));
    }

    private final FirBlock convertFinally(LighterASTNode finallyExpression) {
        KtToken[] ktTokenArr = new KtToken[0];
        LighterASTNode lighterASTNode = null;
        for (LighterASTNode lighterASTNode2 : getChildrenAsArray(finallyExpression)) {
            if (lighterASTNode2 == null) {
                break;
            }
            IElementType tokenType = lighterASTNode2.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr, tokenType) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT) && Intrinsics.areEqual(lighterASTNode2.getTokenType(), KtNodeTypes.BLOCK)) {
                lighterASTNode = lighterASTNode2;
            }
        }
        return this.declarationBuilder.convertBlock(lighterASTNode);
    }

    private final FirExpression convertIfExpression(LighterASTNode ifExpression) {
        int i;
        IfNodeComponents ifExpression2 = parseIfExpression(ifExpression);
        FirWhenExpressionBuilder firWhenExpressionBuilder = new FirWhenExpressionBuilder();
        LightTreeRawFirExpressionBuilder lightTreeRawFirExpressionBuilder = this;
        firWhenExpressionBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(lightTreeRawFirExpressionBuilder, ifExpression, null, 1, null));
        do {
            FirBlock firBlockConvertLoopBody = convertLoopBody(ifExpression2.getThenBlock());
            List<FirWhenBranch> branches = firWhenExpressionBuilder.getBranches();
            FirWhenBranchBuilder firWhenBranchBuilder = new FirWhenBranchBuilder();
            LighterASTNode thenBlock = ifExpression2.getThenBlock();
            firWhenBranchBuilder.setSource(thenBlock != null ? (KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(lightTreeRawFirExpressionBuilder, thenBlock, null, 1, null) : null);
            FirErrorExpression firCondition = ifExpression2.getFirCondition();
            if (firCondition == null) {
                firCondition = FirExpressionUtilKt.buildErrorExpression$default(null, new ConeSyntaxDiagnostic("If statement should have condition"), null, 4, null);
            }
            firWhenBranchBuilder.setCondition(firCondition);
            firWhenBranchBuilder.setResult(firBlockConvertLoopBody);
            branches.add(firWhenBranchBuilder.build());
            if (ifExpression2.getElseBlock() != null) {
                LighterASTNode elseBlock = ifExpression2.getElseBlock();
                i = 0;
                if (elseBlock != null) {
                    KtToken[] ktTokenArr = new KtToken[0];
                    LighterASTNode[] childrenAsArray = getChildrenAsArray(elseBlock);
                    int length = childrenAsArray.length;
                    int i2 = 0;
                    while (i < length) {
                        LighterASTNode lighterASTNode = childrenAsArray[i];
                        if (lighterASTNode == null) {
                            break;
                        }
                        IElementType tokenType = lighterASTNode.getTokenType();
                        if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr, tokenType) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT) && Intrinsics.areEqual(lighterASTNode.getTokenType(), KtNodeTypes.IF)) {
                            ifExpression2 = parseIfExpression(lighterASTNode);
                            i2 = 1;
                        }
                        i++;
                    }
                    i = i2;
                }
            }
            firWhenExpressionBuilder.setUsedAsExpression(getUsedAsExpression(ifExpression));
            return firWhenExpressionBuilder.mo6809build();
        } while (i != 0);
        FirBlock firBlockConvertLoopOrIfBody = convertLoopOrIfBody(ifExpression2.getElseBlock());
        if (firBlockConvertLoopOrIfBody != null) {
            List<FirWhenBranch> branches2 = firWhenExpressionBuilder.getBranches();
            FirWhenBranchBuilder firWhenBranchBuilder2 = new FirWhenBranchBuilder();
            LighterASTNode elseBlock2 = ifExpression2.getElseBlock();
            firWhenBranchBuilder2.setSource(elseBlock2 != null ? (KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(lightTreeRawFirExpressionBuilder, elseBlock2, null, 1, null) : null);
            FirElseIfTrueConditionBuilder firElseIfTrueConditionBuilder = new FirElseIfTrueConditionBuilder();
            Unit unit = Unit.INSTANCE;
            firWhenBranchBuilder2.setCondition(firElseIfTrueConditionBuilder.mo6809build());
            firWhenBranchBuilder2.setResult(firBlockConvertLoopOrIfBody);
            branches2.add(firWhenBranchBuilder2.build());
        }
        firWhenExpressionBuilder.setUsedAsExpression(getUsedAsExpression(ifExpression));
        return firWhenExpressionBuilder.mo6809build();
    }

    /* JADX INFO: compiled from: LightTreeRawFirExpressionBuilder.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\b\u0002\u0018\u00002\u00020\u0001B#\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0007R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/fir/lightTree/converter/LightTreeRawFirExpressionBuilder$IfNodeComponents;", "", "firCondition", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "thenBlock", "Lorg/jetbrains/kotlin/com/intellij/lang/LighterASTNode;", "elseBlock", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;Lcom/intellij/lang/LighterASTNode;Lcom/intellij/lang/LighterASTNode;)V", "getElseBlock", "()Lcom/intellij/lang/LighterASTNode;", "getFirCondition", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getThenBlock", "light-tree2fir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class IfNodeComponents {
        private final LighterASTNode elseBlock;
        private final FirExpression firCondition;
        private final LighterASTNode thenBlock;

        public IfNodeComponents(FirExpression firExpression, LighterASTNode lighterASTNode, LighterASTNode lighterASTNode2) {
            this.firCondition = firExpression;
            this.thenBlock = lighterASTNode;
            this.elseBlock = lighterASTNode2;
        }

        public final LighterASTNode getElseBlock() {
            return this.elseBlock;
        }

        public final FirExpression getFirCondition() {
            return this.firCondition;
        }

        public final LighterASTNode getThenBlock() {
            return this.thenBlock;
        }
    }

    private final IfNodeComponents parseIfExpression(LighterASTNode ifExpression) {
        KtToken[] ktTokenArr = new KtToken[0];
        Object obj = null;
        LighterASTNode lighterASTNode = null;
        LighterASTNode lighterASTNode2 = null;
        for (LighterASTNode lighterASTNode3 : getChildrenAsArray(ifExpression)) {
            if (lighterASTNode3 == null) {
                break;
            }
            IElementType tokenType = lighterASTNode3.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr, tokenType) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT)) {
                IElementType tokenType2 = lighterASTNode3.getTokenType();
                if (!Intrinsics.areEqual(tokenType2, KtNodeTypes.CONDITION)) {
                    if (Intrinsics.areEqual(tokenType2, KtNodeTypes.THEN)) {
                        lighterASTNode = lighterASTNode3;
                    } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.ELSE)) {
                        lighterASTNode2 = lighterASTNode3;
                    }
                } else {
                    FirElement firElementConvertExpression = convertExpression(lighterASTNode3, "If statement should have condition");
                    FirErrorExpression firErrorExpression = (FirExpression) (!(firElementConvertExpression instanceof FirExpression) ? null : firElementConvertExpression);
                    if (firErrorExpression == null) {
                        FirErrorExpression firErrorExpressionBuildErrorExpression = FirExpressionUtilKt.buildErrorExpression((KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(this, lighterASTNode3, null, 1, null), new ConeSimpleDiagnostic("If statement should have condition", DiagnosticKind.ExpressionExpected), firElementConvertExpression);
                        if (firErrorExpressionBuildErrorExpression == null) {
                            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirExpression");
                        }
                        firErrorExpression = firErrorExpressionBuildErrorExpression;
                    }
                    obj = firErrorExpression;
                }
            }
        }
        return new IfNodeComponents((FirExpression) obj, lighterASTNode, lighterASTNode2);
    }

    private final boolean getUsedAsExpression(LighterASTNode lighterASTNode) {
        LighterASTNode parent = getParent(lighterASTNode);
        if (parent == null) {
            return true;
        }
        do {
            if (Intrinsics.areEqual(getElementType(parent), KtNodeTypes.ANNOTATED_EXPRESSION) || Intrinsics.areEqual(getElementType(parent), KtNodeTypes.LABELED_EXPRESSION)) {
                parent = getParent(parent);
            } else {
                IElementType tokenType = parent.getTokenType();
                if (Intrinsics.areEqual(tokenType, KtNodeTypes.BLOCK)) {
                    return false;
                }
                if (Intrinsics.areEqual(tokenType, KtNodeTypes.THEN) || Intrinsics.areEqual(tokenType, KtNodeTypes.ELSE) || Intrinsics.areEqual(tokenType, KtNodeTypes.WHEN_ENTRY)) {
                    LighterASTNode parent2 = getParent(parent);
                    if (parent2 != null) {
                        return getUsedAsExpression(parent2);
                    }
                    return true;
                }
                if (!Intrinsics.areEqual(tokenType, KtNodeTypes.BODY)) {
                    return true;
                }
                LighterASTNode parent3 = getParent(parent);
                IElementType tokenType2 = parent3 != null ? parent3.getTokenType() : null;
                if (tokenType2 == null) {
                    return true;
                }
                return (Intrinsics.areEqual(tokenType2, KtNodeTypes.FOR) || Intrinsics.areEqual(tokenType2, KtNodeTypes.WHILE) || Intrinsics.areEqual(tokenType2, KtNodeTypes.DO_WHILE)) ? false : true;
            }
        } while (parent != null);
        return true;
    }

    private final FirExpression convertLoopJump(LighterASTNode jump) {
        KtToken[] ktTokenArr = new KtToken[0];
        boolean z = true;
        for (LighterASTNode lighterASTNode : getChildrenAsArray(jump)) {
            if (lighterASTNode == null) {
                break;
            }
            IElementType tokenType = lighterASTNode.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr, tokenType) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT) && Intrinsics.areEqual(lighterASTNode.getTokenType(), KtTokens.CONTINUE_KEYWORD)) {
                z = false;
            }
        }
        FirLoopJumpBuilder firBreakExpressionBuilder = z ? new FirBreakExpressionBuilder() : new FirContinueExpressionBuilder();
        firBreakExpressionBuilder.setSource((KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(this, jump, null, 1, null));
        return bindLabel(firBreakExpressionBuilder, jump).build();
    }

    private final FirExpression convertReturn(LighterASTNode returnExpression) {
        LighterASTNode lighterASTNode;
        KtToken[] ktTokenArr = new KtToken[0];
        LighterASTNode[] childrenAsArray = getChildrenAsArray(returnExpression);
        int length = childrenAsArray.length;
        String strReplace$default = null;
        Object obj = null;
        for (int i = 0; i < length && (lighterASTNode = childrenAsArray[i]) != null; i++) {
            IElementType tokenType = lighterASTNode.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr, tokenType) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT)) {
                if (Intrinsics.areEqual(lighterASTNode.getTokenType(), KtNodeTypes.LABEL_QUALIFIER)) {
                    strReplace$default = StringsKt.replace$default(ConverterUtilKt.getAsStringWithoutBacktick(lighterASTNode), PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT, "", false, 4, (Object) null);
                } else if (ElementTypeUtils.INSTANCE.isExpression(lighterASTNode)) {
                    FirElement firElementConvertExpression = convertExpression(lighterASTNode, "Incorrect return expression");
                    FirErrorExpression firErrorExpression = (FirExpression) (!(firElementConvertExpression instanceof FirExpression) ? null : firElementConvertExpression);
                    if (firErrorExpression == null) {
                        FirErrorExpression firErrorExpressionBuildErrorExpression = FirExpressionUtilKt.buildErrorExpression((KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(this, lighterASTNode, null, 1, null), new ConeSimpleDiagnostic("Incorrect return expression", DiagnosticKind.ExpressionExpected), firElementConvertExpression);
                        if (firErrorExpressionBuildErrorExpression == null) {
                            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirExpression");
                        }
                        firErrorExpression = firErrorExpressionBuildErrorExpression;
                    }
                    obj = firErrorExpression;
                } else {
                    continue;
                }
            }
        }
        FirExpression firExpressionMo6809build = (FirExpression) obj;
        if (firExpressionMo6809build == null) {
            FirUnitExpressionBuilder firUnitExpressionBuilder = new FirUnitExpressionBuilder();
            firUnitExpressionBuilder.setSource(toFirSourceElement(returnExpression, (KtFakeSourceElementKind) KtFakeSourceElementKind.ImplicitUnit.Return.INSTANCE));
            firExpressionMo6809build = firUnitExpressionBuilder.mo6809build();
        }
        return toReturn(firExpressionMo6809build, AbstractRawFirBuilder.toFirSourceElement$default(this, returnExpression, null, 1, null), strReplace$default, true);
    }

    private final FirExpression convertThrow(LighterASTNode throwExpression) {
        LighterASTNode lighterASTNode;
        KtToken[] ktTokenArr = new KtToken[0];
        LighterASTNode[] childrenAsArray = getChildrenAsArray(throwExpression);
        int length = childrenAsArray.length;
        Object obj = null;
        for (int i = 0; i < length && (lighterASTNode = childrenAsArray[i]) != null; i++) {
            IElementType tokenType = lighterASTNode.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr, tokenType) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT) && ElementTypeUtils.INSTANCE.isExpression(lighterASTNode)) {
                FirElement firElementConvertExpression = convertExpression(lighterASTNode, "Nothing to throw");
                FirErrorExpression firErrorExpression = (FirExpression) (!(firElementConvertExpression instanceof FirExpression) ? null : firElementConvertExpression);
                if (firErrorExpression == null) {
                    FirErrorExpression firErrorExpressionBuildErrorExpression = FirExpressionUtilKt.buildErrorExpression((KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(this, lighterASTNode, null, 1, null), new ConeSimpleDiagnostic("Nothing to throw", DiagnosticKind.ExpressionExpected), firElementConvertExpression);
                    if (firErrorExpressionBuildErrorExpression == null) {
                        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirExpression");
                    }
                    firErrorExpression = firErrorExpressionBuildErrorExpression;
                }
                obj = firErrorExpression;
            }
        }
        FirThrowExpressionBuilder firThrowExpressionBuilder = new FirThrowExpressionBuilder();
        firThrowExpressionBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(this, throwExpression, null, 1, null));
        FirErrorExpression firErrorExpressionBuildErrorExpression$default = (FirExpression) obj;
        if (firErrorExpressionBuildErrorExpression$default == null) {
            firErrorExpressionBuildErrorExpression$default = FirExpressionUtilKt.buildErrorExpression$default(null, new ConeSyntaxDiagnostic("Nothing to throw"), null, 4, null);
        }
        firThrowExpressionBuilder.setException(firErrorExpressionBuildErrorExpression$default);
        return firThrowExpressionBuilder.mo6809build();
    }

    private final FirQualifiedAccessExpression convertThisExpression(LighterASTNode thisExpression) {
        String labelName = getLabelName(thisExpression);
        FirThisReceiverExpressionBuilder firThisReceiverExpressionBuilder = new FirThisReceiverExpressionBuilder();
        KtLightSourceElement ktLightSourceElement = (KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(this, thisExpression, null, 1, null);
        firThisReceiverExpressionBuilder.setSource(ktLightSourceElement);
        FirExplicitThisReferenceBuilder firExplicitThisReferenceBuilder = new FirExplicitThisReferenceBuilder();
        firExplicitThisReferenceBuilder.setLabelName(labelName);
        firExplicitThisReferenceBuilder.setSource(KtSourceElementKt.fakeElement$default(ktLightSourceElement, KtFakeSourceElementKind.ReferenceInAtomicQualifiedAccess.INSTANCE, 0, 0, 6, null));
        firThisReceiverExpressionBuilder.setCalleeReference(firExplicitThisReferenceBuilder.build());
        return firThisReceiverExpressionBuilder.mo6809build();
    }

    private final FirQualifiedAccessExpression convertSuperExpression(LighterASTNode superExpression) {
        String labelName = getLabelName(superExpression);
        FirTypeRef implicitType = getImplicitType();
        KtToken[] ktTokenArr = new KtToken[0];
        for (LighterASTNode lighterASTNode : getChildrenAsArray(superExpression)) {
            if (lighterASTNode == null) {
                break;
            }
            IElementType tokenType = lighterASTNode.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr, tokenType) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT) && Intrinsics.areEqual(lighterASTNode.getTokenType(), KtNodeTypes.TYPE_REFERENCE)) {
                implicitType = this.declarationBuilder.convertType(lighterASTNode);
            }
        }
        FirPropertyAccessExpressionBuilder firPropertyAccessExpressionBuilder = new FirPropertyAccessExpressionBuilder();
        KtLightSourceElement ktLightSourceElement = (KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(this, superExpression, null, 1, null);
        firPropertyAccessExpressionBuilder.setSource(ktLightSourceElement);
        FirExplicitSuperReferenceBuilder firExplicitSuperReferenceBuilder = new FirExplicitSuperReferenceBuilder();
        firExplicitSuperReferenceBuilder.setLabelName(labelName);
        firExplicitSuperReferenceBuilder.setSuperTypeRef(implicitType);
        firExplicitSuperReferenceBuilder.setSource(KtSourceElementKt.fakeElement$default(ktLightSourceElement, KtFakeSourceElementKind.ReferenceInAtomicQualifiedAccess.INSTANCE, 0, 0, 6, null));
        firPropertyAccessExpressionBuilder.setCalleeReference(firExplicitSuperReferenceBuilder.build());
        return firPropertyAccessExpressionBuilder.mo6809build();
    }

    public final List<FirExpression> convertValueArguments(LighterASTNode valueArguments) {
        Intrinsics.checkNotNullParameter(valueArguments, "valueArguments");
        LighterASTNode[] childrenAsArray = getChildrenAsArray(valueArguments);
        ArrayList arrayList = new ArrayList();
        for (LighterASTNode lighterASTNode : childrenAsArray) {
            if (lighterASTNode == null) {
                break;
            }
            IElementType tokenType = lighterASTNode.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT)) {
                IElementType tokenType2 = lighterASTNode.getTokenType();
                if (Intrinsics.areEqual(tokenType2, KtNodeTypes.VALUE_ARGUMENT)) {
                    arrayList.add(convertValueArgument(lighterASTNode));
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.LAMBDA_EXPRESSION) ? true : Intrinsics.areEqual(tokenType2, KtNodeTypes.LABELED_EXPRESSION) ? true : Intrinsics.areEqual(tokenType2, KtNodeTypes.ANNOTATED_EXPRESSION)) {
                    ArrayList arrayList2 = arrayList;
                    FirLambdaArgumentExpressionBuilder firLambdaArgumentExpressionBuilder = new FirLambdaArgumentExpressionBuilder();
                    LightTreeRawFirExpressionBuilder lightTreeRawFirExpressionBuilder = this;
                    firLambdaArgumentExpressionBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(lightTreeRawFirExpressionBuilder, valueArguments, null, 1, null));
                    FirElement firElementConvertExpression = convertExpression(lighterASTNode, "");
                    FirAnnotationContainer firAnnotationContainer = (FirExpression) (!(firElementConvertExpression instanceof FirExpression) ? null : firElementConvertExpression);
                    if (firAnnotationContainer == null) {
                        FirAnnotationContainer firAnnotationContainerBuildErrorExpression = FirExpressionUtilKt.buildErrorExpression((KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(lightTreeRawFirExpressionBuilder, lighterASTNode, null, 1, null), new ConeSimpleDiagnostic("", DiagnosticKind.ExpressionExpected), firElementConvertExpression);
                        if (firAnnotationContainerBuildErrorExpression == null) {
                            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirExpression");
                        }
                        firAnnotationContainer = (FirExpression) firAnnotationContainerBuildErrorExpression;
                    }
                    firLambdaArgumentExpressionBuilder.setExpression((FirExpression) firAnnotationContainer);
                    arrayList2.add(firLambdaArgumentExpressionBuilder.mo6809build());
                } else {
                    continue;
                }
            }
        }
        return arrayList;
    }

    private final FirExpression convertValueArgument(LighterASTNode valueArgument) {
        LighterASTNode lighterASTNode;
        KtToken[] ktTokenArr = new KtToken[0];
        LighterASTNode[] childrenAsArray = getChildrenAsArray(valueArgument);
        int length = childrenAsArray.length;
        boolean z = false;
        String asText = null;
        FirExpression firExpressionConvertConstantExpression = null;
        for (int i = 0; i < length && (lighterASTNode = childrenAsArray[i]) != null; i++) {
            IElementType tokenType = lighterASTNode.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr, tokenType) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT)) {
                IElementType tokenType2 = lighterASTNode.getTokenType();
                if (Intrinsics.areEqual(tokenType2, KtNodeTypes.VALUE_ARGUMENT_NAME)) {
                    asText = getAsText(lighterASTNode);
                } else if (Intrinsics.areEqual(tokenType2, KtTokens.MUL)) {
                    z = true;
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.STRING_TEMPLATE)) {
                    firExpressionConvertConstantExpression = convertStringTemplate(lighterASTNode);
                } else if (tokenType2 instanceof KtConstantExpressionElementType) {
                    firExpressionConvertConstantExpression = convertConstantExpression(lighterASTNode);
                } else if (ElementTypeUtils.INSTANCE.isExpression(lighterASTNode)) {
                    FirElement firElementConvertExpression = convertExpression(lighterASTNode, "Argument is absent");
                    FirErrorExpression firErrorExpression = (FirExpression) (!(firElementConvertExpression instanceof FirExpression) ? null : firElementConvertExpression);
                    if (firErrorExpression == null) {
                        FirErrorExpression firErrorExpressionBuildErrorExpression = FirExpressionUtilKt.buildErrorExpression((KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(this, lighterASTNode, null, 1, null), new ConeSimpleDiagnostic("Argument is absent", DiagnosticKind.ExpressionExpected), firElementConvertExpression);
                        if (firErrorExpressionBuildErrorExpression == null) {
                            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirExpression");
                        }
                        firErrorExpression = firErrorExpressionBuildErrorExpression;
                    }
                    firExpressionConvertConstantExpression = firErrorExpression;
                } else {
                    continue;
                }
            }
        }
        FirErrorExpression firErrorExpressionBuildErrorExpression$default = firExpressionConvertConstantExpression;
        if (firErrorExpressionBuildErrorExpression$default == null) {
            firErrorExpressionBuildErrorExpression$default = FirExpressionUtilKt.buildErrorExpression$default(null, new ConeSyntaxDiagnostic("Argument is absent"), null, 4, null);
        }
        if (asText != null) {
            FirNamedArgumentExpressionBuilder firNamedArgumentExpressionBuilder = new FirNamedArgumentExpressionBuilder();
            firNamedArgumentExpressionBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(this, valueArgument, null, 1, null));
            firNamedArgumentExpressionBuilder.setExpression(firErrorExpressionBuildErrorExpression$default);
            firNamedArgumentExpressionBuilder.setSpread(z);
            firNamedArgumentExpressionBuilder.setName(ConverterUtilKt.nameAsSafeName$default(asText, null, 1, null));
            return firNamedArgumentExpressionBuilder.mo6809build();
        }
        if (!z) {
            return firErrorExpressionBuildErrorExpression$default;
        }
        FirSpreadArgumentExpressionBuilder firSpreadArgumentExpressionBuilder = new FirSpreadArgumentExpressionBuilder();
        firSpreadArgumentExpressionBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(this, valueArgument, null, 1, null));
        firSpreadArgumentExpressionBuilder.setExpression(firErrorExpressionBuildErrorExpression$default);
        return firSpreadArgumentExpressionBuilder.mo6809build();
    }

    private final FirNamedReference createSimpleNamedReference(KtSourceElement sourceElement, LighterASTNode referenceExpression) {
        FirSimpleNamedReferenceBuilder firSimpleNamedReferenceBuilder = new FirSimpleNamedReferenceBuilder();
        firSimpleNamedReferenceBuilder.setSource(sourceElement);
        firSimpleNamedReferenceBuilder.setName(ConverterUtilKt.nameAsSafeName$default(getAsText(referenceExpression), null, 1, null));
        return firSimpleNamedReferenceBuilder.build();
    }
}
