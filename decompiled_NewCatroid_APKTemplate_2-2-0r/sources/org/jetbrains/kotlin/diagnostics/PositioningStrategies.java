package org.jetbrains.kotlin.diagnostics;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.PsiComment;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiErrorElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiNameIdentifierOwner;
import org.jetbrains.kotlin.com.intellij.psi.PsiRecursiveElementWalkingVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiWhiteSpace;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafPsiElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.lexer.KtModifierKeywordToken;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtAnnotationUseSiteTarget;
import org.jetbrains.kotlin.psi.KtAnonymousInitializer;
import org.jetbrains.kotlin.psi.KtArrayAccessExpression;
import org.jetbrains.kotlin.psi.KtBackingField;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.psi.KtBinaryExpressionWithTypeRHS;
import org.jetbrains.kotlin.psi.KtBlockExpression;
import org.jetbrains.kotlin.psi.KtCallElement;
import org.jetbrains.kotlin.psi.KtCallExpression;
import org.jetbrains.kotlin.psi.KtCallableDeclaration;
import org.jetbrains.kotlin.psi.KtCallableReferenceExpression;
import org.jetbrains.kotlin.psi.KtClass;
import org.jetbrains.kotlin.psi.KtClassInitializer;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtConstantExpression;
import org.jetbrains.kotlin.psi.KtConstructor;
import org.jetbrains.kotlin.psi.KtConstructorCalleeExpression;
import org.jetbrains.kotlin.psi.KtConstructorDelegationCall;
import org.jetbrains.kotlin.psi.KtConstructorDelegationReferenceExpression;
import org.jetbrains.kotlin.psi.KtContainerNode;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtDeclarationWithBody;
import org.jetbrains.kotlin.psi.KtDelegatedSuperTypeEntry;
import org.jetbrains.kotlin.psi.KtDestructuringDeclaration;
import org.jetbrains.kotlin.psi.KtDotQualifiedExpression;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtElementImpl;
import org.jetbrains.kotlin.psi.KtElementImplStub;
import org.jetbrains.kotlin.psi.KtEnumEntry;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtExpressionWithLabel;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtFunction;
import org.jetbrains.kotlin.psi.KtFunctionLiteral;
import org.jetbrains.kotlin.psi.KtIfExpression;
import org.jetbrains.kotlin.psi.KtImportAlias;
import org.jetbrains.kotlin.psi.KtImportDirective;
import org.jetbrains.kotlin.psi.KtLabelReferenceExpression;
import org.jetbrains.kotlin.psi.KtModifierList;
import org.jetbrains.kotlin.psi.KtModifierListOwner;
import org.jetbrains.kotlin.psi.KtNameReferenceExpression;
import org.jetbrains.kotlin.psi.KtNamedDeclaration;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtNullableType;
import org.jetbrains.kotlin.psi.KtObjectDeclaration;
import org.jetbrains.kotlin.psi.KtObjectLiteralExpression;
import org.jetbrains.kotlin.psi.KtOperationExpression;
import org.jetbrains.kotlin.psi.KtOperationReferenceExpression;
import org.jetbrains.kotlin.psi.KtPackageDirective;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtParameterList;
import org.jetbrains.kotlin.psi.KtParenthesizedExpression;
import org.jetbrains.kotlin.psi.KtProjectionKind;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtPropertyAccessor;
import org.jetbrains.kotlin.psi.KtPropertyDelegate;
import org.jetbrains.kotlin.psi.KtQualifiedExpression;
import org.jetbrains.kotlin.psi.KtReferenceExpression;
import org.jetbrains.kotlin.psi.KtReturnExpression;
import org.jetbrains.kotlin.psi.KtSecondaryConstructor;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.KtSuperTypeCallEntry;
import org.jetbrains.kotlin.psi.KtSuperTypeList;
import org.jetbrains.kotlin.psi.KtSuperTypeListEntry;
import org.jetbrains.kotlin.psi.KtTypeAlias;
import org.jetbrains.kotlin.psi.KtTypeArgumentList;
import org.jetbrains.kotlin.psi.KtTypeElement;
import org.jetbrains.kotlin.psi.KtTypeParameterList;
import org.jetbrains.kotlin.psi.KtTypeParameterListOwner;
import org.jetbrains.kotlin.psi.KtTypeProjection;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.psi.KtUnaryExpression;
import org.jetbrains.kotlin.psi.KtUserType;
import org.jetbrains.kotlin.psi.KtValueArgument;
import org.jetbrains.kotlin.psi.KtValueArgumentList;
import org.jetbrains.kotlin.psi.KtValueArgumentName;
import org.jetbrains.kotlin.psi.KtWhenConditionInRange;
import org.jetbrains.kotlin.psi.KtWhenEntry;
import org.jetbrains.kotlin.psi.KtWhenExpression;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;

/* JADX INFO: compiled from: PositioningStrategies.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0098\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001:\b\u009e\u0001\u009f\u0001 \u0001¡\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J-\u0010\u0096\u0001\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0016\u0010\u0097\u0001\u001a\f\u0012\u0007\b\u0001\u0012\u00030\u0099\u00010\u0098\u0001\"\u00030\u0099\u0001H\u0007¢\u0006\u0003\u0010\u009a\u0001J\u000f\u0010\u009b\u0001\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u0007J\u0010\u0010\u009c\u0001\u001a\u0004\u0018\u00010\u0019*\u00030\u009d\u0001H\u0002R\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0016\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u001d\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u0014\u0010\u0002\u001a\u0004\b\u0015\u0010\tR\u0016\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001c0\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u001f0\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010 \u001a\b\u0012\u0004\u0012\u00020\u001f0\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010!\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u001f0\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010#\u001a\b\u0012\u0004\u0012\u00020$0\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010%\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0017\u0010&\u001a\b\u0012\u0004\u0012\u00020'0\u0004¢\u0006\b\n\u0000\u001a\u0004\b(\u0010\tR\u0016\u0010)\u001a\b\u0012\u0004\u0012\u00020*0\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0017\u0010+\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0004¢\u0006\b\n\u0000\u001a\u0004\b,\u0010\tR\u0016\u0010-\u001a\b\u0012\u0004\u0012\u00020.0\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010/\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u00100\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u00101\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u00102\u001a\b\u0012\u0004\u0012\u0002030\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u00104\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u00105\u001a\b\u0012\u0004\u0012\u0002060\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u00107\u001a\b\u0012\u0004\u0012\u0002080\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u00109\u001a\b\u0012\u0004\u0012\u00020\u001f0\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010:\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010;\u001a\b\u0012\u0004\u0012\u00020<0\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010=\u001a\b\u0012\u0004\u0012\u00020>0\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0017\u0010?\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0004¢\u0006\b\n\u0000\u001a\u0004\b@\u0010\tR\u0016\u0010A\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010B\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010C\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010D\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0017\u0010E\u001a\b\u0012\u0004\u0012\u00020\u00190\u0004¢\u0006\b\n\u0000\u001a\u0004\bF\u0010\tR\u0016\u0010G\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010H\u001a\b\u0012\u0004\u0012\u00020\u00190\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010I\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0017\u0010J\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0004¢\u0006\b\n\u0000\u001a\u0004\bK\u0010\tR\u0016\u0010L\u001a\b\u0012\u0004\u0012\u00020M0\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0017\u0010N\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\bO\u0010\tR\u0016\u0010P\u001a\b\u0012\u0004\u0012\u00020\u001f0\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010Q\u001a\b\u0012\u0004\u0012\u00020R0\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010S\u001a\b\u0012\u0004\u0012\u00020T0\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010U\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0017\u0010V\u001a\b\u0012\u0004\u0012\u00020W0\u0004¢\u0006\b\n\u0000\u001a\u0004\bX\u0010\tR\u0016\u0010Y\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010Z\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010[\u001a\b\u0012\u0004\u0012\u0002080\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\\\u001a\b\u0012\u0004\u0012\u00020]0\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010^\u001a\b\u0012\u0004\u0012\u00020]0\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010_\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010`\u001a\b\u0012\u0004\u0012\u00020a0\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0017\u0010b\u001a\b\u0012\u0004\u0012\u00020\u001c0\u0004¢\u0006\b\n\u0000\u001a\u0004\bc\u0010\tR\u0016\u0010d\u001a\b\u0012\u0004\u0012\u00020'0\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010e\u001a\b\u0012\u0004\u0012\u00020f0\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010g\u001a\b\u0012\u0004\u0012\u00020'0\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0017\u0010h\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0004¢\u0006\b\n\u0000\u001a\u0004\bi\u0010\tR\u0017\u0010j\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0004¢\u0006\b\n\u0000\u001a\u0004\bk\u0010\tR\u0017\u0010l\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\bm\u0010\tR\u0016\u0010n\u001a\b\u0012\u0004\u0012\u00020o0\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010p\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010q\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0017\u0010r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0004¢\u0006\b\n\u0000\u001a\u0004\bs\u0010\tR\u0016\u0010t\u001a\b\u0012\u0004\u0012\u00020W0\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0017\u0010u\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0004¢\u0006\b\n\u0000\u001a\u0004\bv\u0010\tR\u0016\u0010w\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010x\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010y\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010z\u001a\b\u0012\u0004\u0012\u00020{0\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0017\u0010|\u001a\b\u0012\u0004\u0012\u00020\u001f0\u0004¢\u0006\b\n\u0000\u001a\u0004\b}\u0010\tR\u0016\u0010~\u001a\b\u0012\u0004\u0012\u00020\u001f0\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u007f\u001a\t\u0012\u0005\u0012\u00030\u0080\u00010\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\u0081\u0001\u001a\t\u0012\u0005\u0012\u00030\u0080\u00010\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u0082\u0001\u001a\b\u0012\u0004\u0012\u00020\u00190\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u0083\u0001\u001a\b\u0012\u0004\u0012\u00020\u00190\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u0084\u0001\u001a\b\u0012\u0004\u0012\u00020\u001f0\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\u0085\u0001\u001a\t\u0012\u0005\u0012\u00030\u0086\u00010\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u0087\u0001\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u0088\u0001\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\u0089\u0001\u001a\t\u0012\u0005\u0012\u00030\u008a\u00010\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\u008b\u0001\u001a\t\u0012\u0005\u0012\u00030\u008c\u00010\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0019\u0010\u008d\u0001\u001a\b\u0012\u0004\u0012\u00020\u00190\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u008e\u0001\u0010\tR\u001b\u0010\u008f\u0001\u001a\t0\u0090\u0001¢\u0006\u0003\b\u0091\u0001¢\u0006\n\n\u0000\u001a\u0006\b\u0092\u0001\u0010\u0093\u0001R\u001b\u0010\u0094\u0001\u001a\t0\u0090\u0001¢\u0006\u0003\b\u0091\u0001¢\u0006\n\n\u0000\u001a\u0006\b\u0095\u0001\u0010\u0093\u0001¨\u0006¢\u0001"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/PositioningStrategies;", "", "()V", "ABSTRACT_MODIFIER", "Lorg/jetbrains/kotlin/diagnostics/PositioningStrategy;", "Lorg/jetbrains/kotlin/psi/KtModifierListOwner;", "ANNOTATION_USE_SITE", "Lorg/jetbrains/kotlin/psi/KtAnnotationEntry;", "getANNOTATION_USE_SITE", "()Lorg/jetbrains/kotlin/diagnostics/PositioningStrategy;", "ARRAY_ACCESS", "Lorg/jetbrains/kotlin/psi/KtArrayAccessExpression;", "AS_TYPE", "Lorg/jetbrains/kotlin/psi/KtBinaryExpressionWithTypeRHS;", "CALL_ELEMENT", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "CALL_ELEMENT_WITH_DOT", "Lorg/jetbrains/kotlin/psi/KtQualifiedExpression;", "CALL_EXPRESSION", "COMMAS", "getCOMMAS$annotations", "getCOMMAS", "COMPANION_OBJECT", "CONST_MODIFIER", "CUT_CHAR_QUOTES", "Lorg/jetbrains/kotlin/psi/KtElement;", "DATA_MODIFIER", "DECLARATION_NAME", "Lorg/jetbrains/kotlin/psi/KtNamedDeclaration;", "DECLARATION_NAME_ONLY", "DECLARATION_RETURN_TYPE", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "DECLARATION_SIGNATURE", "DECLARATION_SIGNATURE_OR_DEFAULT", "DECLARATION_START_TO_NAME", "DECLARATION_WITH_BODY", "Lorg/jetbrains/kotlin/psi/KtDeclarationWithBody;", "DEFAULT", "DELEGATED_SUPERTYPE_BY_KEYWORD", "Lorg/jetbrains/kotlin/psi/KtTypeReference;", "getDELEGATED_SUPERTYPE_BY_KEYWORD", "DELEGATOR_SUPER_CALL", "Lorg/jetbrains/kotlin/psi/KtEnumEntry;", "DOT_BY_QUALIFIED", "getDOT_BY_QUALIFIED", "ELSE_ENTRY", "Lorg/jetbrains/kotlin/psi/KtWhenEntry;", "ENUM_MODIFIER", "EXPECT_ACTUAL_MODIFIER", "EXTERNAL_MODIFIER", "FIELD_KEYWORD", "Lorg/jetbrains/kotlin/psi/KtBackingField;", "FOR_REDECLARATION", "FOR_UNRESOLVED_REFERENCE", "Lorg/jetbrains/kotlin/psi/KtReferenceExpression;", "FUNCTION_PARAMETERS", "Lorg/jetbrains/kotlin/psi/KtFunction;", "FUN_INTERFACE", "FUN_MODIFIER", "IF_EXPRESSION", "Lorg/jetbrains/kotlin/psi/KtIfExpression;", "IMPORT_ALIAS", "Lorg/jetbrains/kotlin/psi/KtImportDirective;", "IMPORT_LAST_NAME", "getIMPORT_LAST_NAME", "INLINE_FUN_MODIFIER", "INLINE_OR_VALUE_MODIFIER", "INLINE_PARAMETER_MODIFIER", "INNER_MODIFIER", "LABEL", "getLABEL", "LATEINIT_MODIFIER", "LONG_LITERAL_SUFFIX", "MODALITY_MODIFIER", "NAME_IDENTIFIER", "getNAME_IDENTIFIER", "NAME_OF_NAMED_ARGUMENT", "Lorg/jetbrains/kotlin/psi/KtValueArgument;", "NON_FINAL_MODIFIER_OR_NAME", "getNON_FINAL_MODIFIER_OR_NAME", "NOT_SUPPORTED_IN_INLINE_MOST_RELEVANT", "NULLABLE_TYPE", "Lorg/jetbrains/kotlin/psi/KtNullableType;", "OBJECT_KEYWORD", "Lorg/jetbrains/kotlin/psi/KtObjectDeclaration;", "OPEN_MODIFIER", "OPERATOR", "Lorg/jetbrains/kotlin/psi/KtExpression;", "getOPERATOR", "OPERATOR_MODIFIER", "OVERRIDE_MODIFIER", "PARAMETERS_WITH_DEFAULT_VALUE", "PARAMETER_DEFAULT_VALUE", "Lorg/jetbrains/kotlin/psi/KtParameter;", "PARAMETER_VARARG_MODIFIER", "PRIVATE_MODIFIER", "PROPERTY_DELEGATE", "Lorg/jetbrains/kotlin/psi/KtProperty;", "PROPERTY_INITIALIZER", "getPROPERTY_INITIALIZER", "QUESTION_MARK_BY_TYPE", "RECEIVER", "Lorg/jetbrains/kotlin/psi/KtCallableDeclaration;", "REDUNDANT_NULLABLE", "REFERENCED_NAME_BY_QUALIFIED", "getREFERENCED_NAME_BY_QUALIFIED", "REFERENCE_BY_QUALIFIED", "getREFERENCE_BY_QUALIFIED", "REIFIED_MODIFIER", "getREIFIED_MODIFIER", "RETURN_WITH_LABEL", "Lorg/jetbrains/kotlin/psi/KtReturnExpression;", "SAFE_ACCESS", "SECONDARY_CONSTRUCTOR_DELEGATION_CALL", "SELECTOR_BY_QUALIFIED", "getSELECTOR_BY_QUALIFIED", "SPECIAL_CONSTRUCT_TOKEN", "SPREAD_OPERATOR", "getSPREAD_OPERATOR", "SUPERTYPES_LIST", "SUSPEND_MODIFIER", "TAILREC_MODIFIER", "TYPEALIAS_TYPE_REFERENCE", "Lorg/jetbrains/kotlin/psi/KtTypeAlias;", "TYPE_PARAMETERS_LIST", "getTYPE_PARAMETERS_LIST", "TYPE_PARAMETERS_OR_DECLARATION_SIGNATURE", "UNUSED_VALUE", "Lorg/jetbrains/kotlin/psi/KtBinaryExpression;", "USELESS_ELVIS", "VALUE_ARGUMENTS", "VALUE_ARGUMENTS_LIST", "VAL_OR_VAR_NODE", "VARIANCE_IN_PROJECTION", "Lorg/jetbrains/kotlin/psi/KtTypeProjection;", "VARIANCE_MODIFIER", "VISIBILITY_MODIFIER", "WHEN_CONDITION_IN_RANGE", "Lorg/jetbrains/kotlin/psi/KtWhenConditionInRange;", "WHEN_EXPRESSION", "Lorg/jetbrains/kotlin/psi/KtWhenExpression;", "WHOLE_ELEMENT", "getWHOLE_ELEMENT", "classKindTokens", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/TokenSet;", "Lorg/jetbrains/annotations/NotNull;", "getClassKindTokens", "()Lcom/intellij/psi/tree/TokenSet;", "propertyKindTokens", "getPropertyKindTokens", "modifierSetPosition", "tokens", "", "Lorg/jetbrains/kotlin/lexer/KtModifierKeywordToken;", "([Lorg/jetbrains/kotlin/lexer/KtModifierKeywordToken;)Lorg/jetbrains/kotlin/diagnostics/PositioningStrategy;", "projectionPosition", "getReferencedTypeExpression", "Lorg/jetbrains/kotlin/psi/KtTypeElement;", "DeclarationHeader", "FindReferencePositioningStrategy", "InlineFunPositioningStrategy", "ModifierSetBasedPositioningStrategy", "frontend.common-psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PositioningStrategies {
    public static final PositioningStrategy<KtModifierListOwner> ABSTRACT_MODIFIER;
    private static final PositioningStrategy<KtAnnotationEntry> ANNOTATION_USE_SITE;
    public static final PositioningStrategy<KtArrayAccessExpression> ARRAY_ACCESS;
    public static final PositioningStrategy<KtBinaryExpressionWithTypeRHS> AS_TYPE;
    public static final PositioningStrategy<PsiElement> CALL_ELEMENT;
    public static final PositioningStrategy<KtQualifiedExpression> CALL_ELEMENT_WITH_DOT;
    public static final PositioningStrategy<PsiElement> CALL_EXPRESSION;
    private static final PositioningStrategy<PsiElement> COMMAS;
    public static final PositioningStrategy<KtModifierListOwner> COMPANION_OBJECT;
    public static final PositioningStrategy<KtModifierListOwner> CONST_MODIFIER;
    public static final PositioningStrategy<KtElement> CUT_CHAR_QUOTES;
    public static final PositioningStrategy<KtModifierListOwner> DATA_MODIFIER;
    public static final PositioningStrategy<KtNamedDeclaration> DECLARATION_NAME;
    public static final PositioningStrategy<KtNamedDeclaration> DECLARATION_NAME_ONLY;
    public static final PositioningStrategy<KtDeclaration> DECLARATION_SIGNATURE;
    public static final PositioningStrategy<PsiElement> DECLARATION_SIGNATURE_OR_DEFAULT;
    public static final PositioningStrategy<KtDeclaration> DECLARATION_START_TO_NAME;
    public static final PositioningStrategy<KtDeclarationWithBody> DECLARATION_WITH_BODY;
    private static final PositioningStrategy<KtTypeReference> DELEGATED_SUPERTYPE_BY_KEYWORD;
    public static final PositioningStrategy<KtEnumEntry> DELEGATOR_SUPER_CALL;
    private static final PositioningStrategy<PsiElement> DOT_BY_QUALIFIED;
    public static final PositioningStrategy<KtWhenEntry> ELSE_ENTRY;
    public static final PositioningStrategy<KtModifierListOwner> ENUM_MODIFIER;
    public static final PositioningStrategy<KtModifierListOwner> EXPECT_ACTUAL_MODIFIER;
    public static final PositioningStrategy<KtModifierListOwner> EXTERNAL_MODIFIER;
    public static final PositioningStrategy<KtBackingField> FIELD_KEYWORD;
    public static final PositioningStrategy<PsiElement> FOR_REDECLARATION;
    public static final PositioningStrategy<KtReferenceExpression> FOR_UNRESOLVED_REFERENCE;
    public static final PositioningStrategy<KtFunction> FUNCTION_PARAMETERS;
    public static final PositioningStrategy<KtDeclaration> FUN_INTERFACE;
    public static final PositioningStrategy<KtModifierListOwner> FUN_MODIFIER;
    public static final PositioningStrategy<KtIfExpression> IF_EXPRESSION;
    public static final PositioningStrategy<KtImportDirective> IMPORT_ALIAS;
    private static final PositioningStrategy<PsiElement> IMPORT_LAST_NAME;
    public static final PositioningStrategy<KtModifierListOwner> INLINE_FUN_MODIFIER;
    public static final PositioningStrategy<KtModifierListOwner> INLINE_OR_VALUE_MODIFIER;
    public static final PositioningStrategy<KtModifierListOwner> INLINE_PARAMETER_MODIFIER;
    public static final PositioningStrategy<KtModifierListOwner> INNER_MODIFIER;
    private static final PositioningStrategy<KtElement> LABEL;
    public static final PositioningStrategy<KtModifierListOwner> LATEINIT_MODIFIER;
    public static final PositioningStrategy<KtElement> LONG_LITERAL_SUFFIX;
    public static final PositioningStrategy<KtModifierListOwner> MODALITY_MODIFIER;
    private static final PositioningStrategy<PsiElement> NAME_IDENTIFIER;
    public static final PositioningStrategy<KtValueArgument> NAME_OF_NAMED_ARGUMENT;
    private static final PositioningStrategy<KtModifierListOwner> NON_FINAL_MODIFIER_OR_NAME;
    public static final PositioningStrategy<KtDeclaration> NOT_SUPPORTED_IN_INLINE_MOST_RELEVANT;
    public static final PositioningStrategy<KtNullableType> NULLABLE_TYPE;
    public static final PositioningStrategy<KtObjectDeclaration> OBJECT_KEYWORD;
    public static final PositioningStrategy<KtModifierListOwner> OPEN_MODIFIER;
    private static final PositioningStrategy<KtExpression> OPERATOR;
    public static final PositioningStrategy<KtModifierListOwner> OPERATOR_MODIFIER;
    public static final PositioningStrategy<KtModifierListOwner> OVERRIDE_MODIFIER;
    public static final PositioningStrategy<KtFunction> PARAMETERS_WITH_DEFAULT_VALUE;
    public static final PositioningStrategy<KtParameter> PARAMETER_DEFAULT_VALUE;
    public static final PositioningStrategy<KtParameter> PARAMETER_VARARG_MODIFIER;
    public static final PositioningStrategy<KtModifierListOwner> PRIVATE_MODIFIER;
    public static final PositioningStrategy<KtProperty> PROPERTY_DELEGATE;
    private static final PositioningStrategy<KtNamedDeclaration> PROPERTY_INITIALIZER;
    public static final PositioningStrategy<KtTypeReference> QUESTION_MARK_BY_TYPE;
    public static final PositioningStrategy<KtCallableDeclaration> RECEIVER;
    public static final PositioningStrategy<KtTypeReference> REDUNDANT_NULLABLE;
    private static final PositioningStrategy<PsiElement> REFERENCED_NAME_BY_QUALIFIED;
    private static final PositioningStrategy<PsiElement> REFERENCE_BY_QUALIFIED;
    private static final PositioningStrategy<KtModifierListOwner> REIFIED_MODIFIER;
    public static final PositioningStrategy<KtReturnExpression> RETURN_WITH_LABEL;
    public static final PositioningStrategy<PsiElement> SAFE_ACCESS;
    public static final PositioningStrategy<PsiElement> SECONDARY_CONSTRUCTOR_DELEGATION_CALL;
    private static final PositioningStrategy<PsiElement> SELECTOR_BY_QUALIFIED;
    public static final PositioningStrategy<KtExpression> SPECIAL_CONSTRUCT_TOKEN;
    private static final PositioningStrategy<PsiElement> SPREAD_OPERATOR;
    public static final PositioningStrategy<KtModifierListOwner> SUSPEND_MODIFIER;
    public static final PositioningStrategy<KtModifierListOwner> TAILREC_MODIFIER;
    public static final PositioningStrategy<KtTypeAlias> TYPEALIAS_TYPE_REFERENCE;
    private static final PositioningStrategy<KtDeclaration> TYPE_PARAMETERS_LIST;
    public static final PositioningStrategy<KtDeclaration> TYPE_PARAMETERS_OR_DECLARATION_SIGNATURE;
    public static final PositioningStrategy<KtBinaryExpression> UNUSED_VALUE;
    public static final PositioningStrategy<KtBinaryExpression> USELESS_ELVIS;
    public static final PositioningStrategy<KtElement> VALUE_ARGUMENTS;
    public static final PositioningStrategy<KtElement> VALUE_ARGUMENTS_LIST;
    public static final PositioningStrategy<KtDeclaration> VAL_OR_VAR_NODE;
    public static final PositioningStrategy<KtTypeProjection> VARIANCE_IN_PROJECTION;
    public static final PositioningStrategy<KtModifierListOwner> VARIANCE_MODIFIER;
    public static final PositioningStrategy<KtModifierListOwner> VISIBILITY_MODIFIER;
    public static final PositioningStrategy<KtWhenConditionInRange> WHEN_CONDITION_IN_RANGE;
    public static final PositioningStrategy<KtWhenExpression> WHEN_EXPRESSION;
    private static final PositioningStrategy<KtElement> WHOLE_ELEMENT;
    private static final TokenSet classKindTokens;
    private static final TokenSet propertyKindTokens;
    public static final PositioningStrategies INSTANCE = new PositioningStrategies();
    public static final PositioningStrategy<PsiElement> DEFAULT = new PositioningStrategy<PsiElement>() { // from class: org.jetbrains.kotlin.diagnostics.PositioningStrategies$DEFAULT$1
        @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
        public List<TextRange> mark(PsiElement element) {
            Intrinsics.checkNotNullParameter(element, "element");
            if (element instanceof KtObjectLiteralExpression) {
                KtObjectDeclaration objectDeclaration = ((KtObjectLiteralExpression) element).getObjectDeclaration();
                Intrinsics.checkNotNullExpressionValue(objectDeclaration, "getObjectDeclaration(...)");
                PsiElement objectKeyword = objectDeclaration.getObjectKeyword();
                Intrinsics.checkNotNull(objectKeyword);
                KtSuperTypeList superTypeList = objectDeclaration.getSuperTypeList();
                return superTypeList == null ? PositioningStrategyKt.markElement(objectKeyword) : PositioningStrategyKt.markRange(objectKeyword, superTypeList);
            }
            if (element instanceof KtObjectDeclaration) {
                KtObjectDeclaration ktObjectDeclaration = (KtObjectDeclaration) element;
                PsiElement objectKeyword2 = ktObjectDeclaration.getObjectKeyword();
                Intrinsics.checkNotNull(objectKeyword2);
                PsiElement nameIdentifier = ktObjectDeclaration.mo7392getNameIdentifier();
                if (nameIdentifier == null) {
                    nameIdentifier = ktObjectDeclaration.getObjectKeyword();
                    Intrinsics.checkNotNull(nameIdentifier);
                }
                Intrinsics.checkNotNull(nameIdentifier);
                return PositioningStrategyKt.markRange(objectKeyword2, nameIdentifier);
            }
            if (element instanceof KtConstructorDelegationCall) {
                return PositioningStrategies.SECONDARY_CONSTRUCTOR_DELEGATION_CALL.mark(element);
            }
            return super.mark(element);
        }
    };
    public static final PositioningStrategy<PsiElement> SUPERTYPES_LIST = new PositioningStrategy<PsiElement>() { // from class: org.jetbrains.kotlin.diagnostics.PositioningStrategies$SUPERTYPES_LIST$1
        @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
        public List<TextRange> mark(PsiElement element) {
            Intrinsics.checkNotNullParameter(element, "element");
            KtClass ktClass = element instanceof KtClass ? (KtClass) element : null;
            if (ktClass == null) {
                return PositioningStrategyKt.markElement(element);
            }
            List<KtSuperTypeListEntry> superTypeListEntries = ktClass.getSuperTypeListEntries();
            if (superTypeListEntries.isEmpty()) {
                return PositioningStrategyKt.markElement(element);
            }
            return PositioningStrategyKt.markRange(superTypeListEntries.get(0), (PsiElement) CollectionsKt.last((List) superTypeListEntries));
        }
    };
    public static final PositioningStrategy<KtDeclaration> DECLARATION_RETURN_TYPE = new PositioningStrategy<KtDeclaration>() { // from class: org.jetbrains.kotlin.diagnostics.PositioningStrategies$DECLARATION_RETURN_TYPE$1
        @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
        public List<TextRange> mark(KtDeclaration element) {
            Intrinsics.checkNotNullParameter(element, "element");
            return PositioningStrategyKt.markElement(getElementToMark(element));
        }

        @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
        public boolean isValid(KtDeclaration element) {
            Intrinsics.checkNotNullParameter(element, "element");
            return !PositioningStrategyKt.hasSyntaxErrors(getElementToMark(element));
        }

        private final PsiElement getElementToMark(KtDeclaration declaration) {
            Pair pair;
            if (declaration instanceof KtCallableDeclaration) {
                KtCallableDeclaration ktCallableDeclaration = (KtCallableDeclaration) declaration;
                pair = new Pair(ktCallableDeclaration.mo7396getTypeReference(), ktCallableDeclaration.mo7392getNameIdentifier());
            } else if (declaration instanceof KtPropertyAccessor) {
                KtPropertyAccessor ktPropertyAccessor = (KtPropertyAccessor) declaration;
                pair = new Pair(ktPropertyAccessor.getReturnTypeReference(), ktPropertyAccessor.getNamePlaceholder());
            } else {
                pair = new Pair(null, null);
            }
            KtTypeReference ktTypeReference = (KtTypeReference) pair.component1();
            PsiElement psiElement = (PsiElement) pair.component2();
            if (ktTypeReference != null) {
                return ktTypeReference;
            }
            return psiElement != null ? psiElement : declaration;
        }
    };

    public static /* synthetic */ void getCOMMAS$annotations() {
    }

    private PositioningStrategies() {
    }

    /* JADX INFO: compiled from: PositioningStrategies.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0016\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u0005¢\u0006\u0002\u0010\u0004J\u0015\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/PositioningStrategies$DeclarationHeader;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "Lorg/jetbrains/kotlin/diagnostics/PositioningStrategy;", "()V", "isValid", "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "(Lorg/jetbrains/kotlin/psi/KtDeclaration;)Z", "frontend.common-psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static class DeclarationHeader<T extends KtDeclaration> extends PositioningStrategy<T> {
        @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
        public boolean isValid(T element) {
            Intrinsics.checkNotNullParameter(element, "element");
            if (!(element instanceof KtNamedDeclaration) || (element instanceof KtObjectDeclaration) || (element instanceof KtSecondaryConstructor) || (element instanceof KtFunction) || ((KtNamedDeclaration) element).mo7392getNameIdentifier() != null) {
                return super.isValid(element);
            }
            return false;
        }
    }

    static {
        TokenSet tokenSetCreate = TokenSet.create(KtTokens.VAL_KEYWORD, KtTokens.VAR_KEYWORD);
        Intrinsics.checkNotNullExpressionValue(tokenSetCreate, "create(...)");
        propertyKindTokens = tokenSetCreate;
        TokenSet tokenSetCreate2 = TokenSet.create(KtTokens.CLASS_KEYWORD, KtTokens.OBJECT_KEYWORD, KtTokens.INTERFACE_KEYWORD);
        Intrinsics.checkNotNullExpressionValue(tokenSetCreate2, "create(...)");
        classKindTokens = tokenSetCreate2;
        DECLARATION_START_TO_NAME = new DeclarationHeader<KtDeclaration>() { // from class: org.jetbrains.kotlin.diagnostics.PositioningStrategies$DECLARATION_START_TO_NAME$1
            private final PsiElement firstNonCommentNonAnnotationLeaf(PsiElement psiElement) {
                PsiElement firstChild = psiElement.getFirstChild();
                if (firstChild == null) {
                    return psiElement;
                }
                while (true) {
                    if ((firstChild instanceof PsiComment) || (firstChild instanceof PsiWhiteSpace) || (firstChild instanceof KtAnnotationEntry)) {
                        firstChild = firstChild.getNextSibling();
                    } else {
                        if (firstChild == null) {
                            return null;
                        }
                        PsiElement psiElementFirstNonCommentNonAnnotationLeaf = firstNonCommentNonAnnotationLeaf(firstChild);
                        if (psiElementFirstNonCommentNonAnnotationLeaf != null) {
                            return psiElementFirstNonCommentNonAnnotationLeaf;
                        }
                        firstChild = firstChild.getNextSibling();
                    }
                }
            }

            @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
            public List<TextRange> mark(KtDeclaration element) {
                Intrinsics.checkNotNullParameter(element, "element");
                KtDeclaration ktDeclaration = element;
                PsiElement psiElementFirstNonCommentNonAnnotationLeaf = firstNonCommentNonAnnotationLeaf(ktDeclaration);
                if (psiElementFirstNonCommentNonAnnotationLeaf == null) {
                    psiElementFirstNonCommentNonAnnotationLeaf = ktDeclaration;
                }
                KtNamedDeclaration ktNamedDeclaration = element instanceof KtNamedDeclaration ? (KtNamedDeclaration) element : null;
                PsiElement nameIdentifier = ktNamedDeclaration != null ? ktNamedDeclaration.mo7392getNameIdentifier() : null;
                if (nameIdentifier != null) {
                    return PositioningStrategyKt.markRange(psiElementFirstNonCommentNonAnnotationLeaf, nameIdentifier);
                }
                if (element instanceof KtConstructor) {
                    PsiElement constructorKeyword = ((KtConstructor) element).getConstructorKeyword();
                    if (constructorKeyword != null) {
                        ktDeclaration = constructorKeyword;
                    }
                    return PositioningStrategyKt.markRange(psiElementFirstNonCommentNonAnnotationLeaf, ktDeclaration);
                }
                if (element instanceof KtObjectDeclaration) {
                    PsiElement objectKeyword = ((KtObjectDeclaration) element).getObjectKeyword();
                    if (objectKeyword != null) {
                        ktDeclaration = objectKeyword;
                    }
                    return PositioningStrategyKt.markRange(psiElementFirstNonCommentNonAnnotationLeaf, ktDeclaration);
                }
                return PositioningStrategies.DEFAULT.mark(ktDeclaration);
            }
        };
        DECLARATION_NAME = new DeclarationHeader<KtNamedDeclaration>() { // from class: org.jetbrains.kotlin.diagnostics.PositioningStrategies$DECLARATION_NAME$1
            @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
            public List<TextRange> mark(KtNamedDeclaration element) {
                KtNamedDeclaration psi;
                Intrinsics.checkNotNullParameter(element, "element");
                PsiElement nameIdentifier = element.mo7392getNameIdentifier();
                if (nameIdentifier != null) {
                    if (element instanceof KtClassOrObject) {
                        KtModifierList modifierList = element.getModifierList();
                        if (modifierList == null || (psi = modifierList.getModifier(KtTokens.ENUM_KEYWORD)) == null) {
                            ASTNode aSTNodeFindChildByType = ((KtClassOrObject) element).getNode().findChildByType(TokenSet.create(KtTokens.CLASS_KEYWORD, KtTokens.OBJECT_KEYWORD));
                            psi = aSTNodeFindChildByType != null ? aSTNodeFindChildByType.getPsi() : null;
                            if (psi == null) {
                                psi = element;
                            }
                        }
                        return PositioningStrategyKt.markRange(psi, nameIdentifier);
                    }
                    return PositioningStrategyKt.markElement(nameIdentifier);
                }
                if (element instanceof KtNamedFunction) {
                    return PositioningStrategies.DECLARATION_SIGNATURE.mark(element);
                }
                return PositioningStrategies.DEFAULT.mark(element);
            }
        };
        DECLARATION_NAME_ONLY = new DeclarationHeader<KtNamedDeclaration>() { // from class: org.jetbrains.kotlin.diagnostics.PositioningStrategies$DECLARATION_NAME_ONLY$1
            @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
            public List<TextRange> mark(KtNamedDeclaration element) {
                Intrinsics.checkNotNullParameter(element, "element");
                PsiElement nameIdentifier = element.mo7392getNameIdentifier();
                if (nameIdentifier != null) {
                    return PositioningStrategyKt.markElement(nameIdentifier);
                }
                if (element instanceof KtNamedFunction) {
                    return PositioningStrategies.DECLARATION_SIGNATURE.mark(element);
                }
                return PositioningStrategies.DEFAULT.mark(element);
            }
        };
        DECLARATION_SIGNATURE = new DeclarationHeader<KtDeclaration>() { // from class: org.jetbrains.kotlin.diagnostics.PositioningStrategies$DECLARATION_SIGNATURE$1
            @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
            public List<TextRange> mark(KtDeclaration element) {
                KtTypeReference rightParenthesis;
                KtDeclaration nameIdentifier;
                KtDeclaration nameIdentifier2;
                KtDeclaration ktDeclaration;
                KtDeclaration ktDeclaration2;
                KtParameterList constructorKeyword;
                Intrinsics.checkNotNullParameter(element, "element");
                if (element instanceof KtConstructor) {
                    KtConstructor ktConstructor = (KtConstructor) element;
                    KtParameterList constructorKeyword2 = ktConstructor.getConstructorKeyword();
                    if (constructorKeyword2 == null) {
                        KtParameterList valueParameterList = ktConstructor.getValueParameterList();
                        if (valueParameterList == null) {
                            return PositioningStrategyKt.markElement(element);
                        }
                        constructorKeyword2 = valueParameterList;
                    }
                    KtParameterList valueParameterList2 = ktConstructor.getValueParameterList();
                    if (valueParameterList2 != null) {
                        constructorKeyword = valueParameterList2;
                    } else {
                        constructorKeyword = ktConstructor.getConstructorKeyword();
                        if (constructorKeyword == null) {
                            return PositioningStrategyKt.markElement(element);
                        }
                    }
                    return PositioningStrategyKt.markRange(constructorKeyword2, constructorKeyword);
                }
                if (element instanceof KtFunction) {
                    KtFunction ktFunction = (KtFunction) element;
                    PsiElement typeReference = ktFunction.mo7396getTypeReference();
                    if (typeReference == null && (typeReference = ktFunction.getValueParameterList()) == null) {
                        nameIdentifier2 = ktFunction.mo7392getNameIdentifier();
                        if (nameIdentifier2 == null) {
                            nameIdentifier2 = element;
                        }
                    } else {
                        nameIdentifier2 = typeReference;
                    }
                    if (element instanceof KtFunctionLiteral) {
                        KtTypeReference receiverTypeReference = ktFunction.mo7393getReceiverTypeReference();
                        if (receiverTypeReference != null) {
                            ktDeclaration2 = receiverTypeReference;
                        } else {
                            KtParameterList valueParameterList3 = ktFunction.getValueParameterList();
                            if (valueParameterList3 != null) {
                                ktDeclaration2 = valueParameterList3;
                            } else {
                                ktDeclaration2 = element;
                            }
                        }
                        ktDeclaration = (KtElement) ktDeclaration2;
                    } else {
                        ktDeclaration = element;
                    }
                    return PositioningStrategyKt.markRange(ktDeclaration, nameIdentifier2);
                }
                if (element instanceof KtProperty) {
                    KtProperty ktProperty = (KtProperty) element;
                    KtTypeReference ktTypeReferenceMo7396getTypeReference = ktProperty.mo7396getTypeReference();
                    if (ktTypeReferenceMo7396getTypeReference != null) {
                        nameIdentifier = ktTypeReferenceMo7396getTypeReference;
                    } else {
                        nameIdentifier = ktProperty.mo7392getNameIdentifier();
                        if (nameIdentifier == null) {
                            nameIdentifier = element;
                        }
                    }
                    return PositioningStrategyKt.markRange(element, nameIdentifier);
                }
                if (element instanceof KtPropertyAccessor) {
                    KtPropertyAccessor ktPropertyAccessor = (KtPropertyAccessor) element;
                    KtTypeReference returnTypeReference = ktPropertyAccessor.getReturnTypeReference();
                    if (returnTypeReference != null) {
                        rightParenthesis = returnTypeReference;
                    } else {
                        rightParenthesis = ktPropertyAccessor.getRightParenthesis();
                        if (rightParenthesis == null) {
                            rightParenthesis = ktPropertyAccessor.getNamePlaceholder();
                        }
                    }
                    Intrinsics.checkNotNull(rightParenthesis);
                    return PositioningStrategyKt.markRange(element, rightParenthesis);
                }
                if (element instanceof KtClass) {
                    KtClass ktClass = (KtClass) element;
                    PsiElement nameIdentifier3 = ktClass.mo7392getNameIdentifier();
                    if (nameIdentifier3 == null) {
                        return PositioningStrategyKt.markElement(element);
                    }
                    KtParameterList primaryConstructorParameterList = ktClass.getPrimaryConstructorParameterList();
                    return primaryConstructorParameterList == null ? PositioningStrategyKt.markElement(nameIdentifier3) : PositioningStrategyKt.markRange(nameIdentifier3, primaryConstructorParameterList);
                }
                if (element instanceof KtObjectDeclaration) {
                    return PositioningStrategies.DECLARATION_NAME.mark(element);
                }
                if (element instanceof KtClassInitializer) {
                    TextRange textRange = ((KtClassInitializer) element).getInitKeyword().getTextRange();
                    Intrinsics.checkNotNullExpressionValue(textRange, "getTextRange(...)");
                    return PositioningStrategyKt.markRange(textRange);
                }
                return super.mark(element);
            }
        };
        DECLARATION_SIGNATURE_OR_DEFAULT = new PositioningStrategy<PsiElement>() { // from class: org.jetbrains.kotlin.diagnostics.PositioningStrategies$DECLARATION_SIGNATURE_OR_DEFAULT$1
            @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
            public List<TextRange> mark(PsiElement element) {
                Intrinsics.checkNotNullParameter(element, "element");
                if (element instanceof KtDeclaration) {
                    return PositioningStrategies.DECLARATION_SIGNATURE.mark(element);
                }
                return PositioningStrategies.DEFAULT.mark(element);
            }

            @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
            public boolean isValid(PsiElement element) {
                Intrinsics.checkNotNullParameter(element, "element");
                if (element instanceof KtDeclaration) {
                    return PositioningStrategies.DECLARATION_SIGNATURE.isValid(element);
                }
                return PositioningStrategies.DEFAULT.isValid(element);
            }
        };
        NOT_SUPPORTED_IN_INLINE_MOST_RELEVANT = new PositioningStrategy<KtDeclaration>() { // from class: org.jetbrains.kotlin.diagnostics.PositioningStrategies$NOT_SUPPORTED_IN_INLINE_MOST_RELEVANT$1
            @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
            public List<TextRange> mark(KtDeclaration element) {
                KtDeclaration funKeyword;
                Intrinsics.checkNotNullParameter(element, "element");
                if (element instanceof KtClassOrObject) {
                    KtClassOrObject ktClassOrObject = (KtClassOrObject) element;
                    funKeyword = ktClassOrObject.getDeclarationKeyword();
                    if (funKeyword == null && (funKeyword = ktClassOrObject.mo7392getNameIdentifier()) == null) {
                        funKeyword = element;
                    }
                } else if (element instanceof KtNamedFunction) {
                    KtNamedFunction ktNamedFunction = (KtNamedFunction) element;
                    KtModifierList modifierList = ktNamedFunction.getModifierList();
                    if ((modifierList == null || (funKeyword = modifierList.getModifier(KtTokens.INLINE_KEYWORD)) == null) && (funKeyword = ktNamedFunction.getFunKeyword()) == null) {
                        funKeyword = element;
                    }
                } else {
                    funKeyword = element;
                }
                return PositioningStrategyKt.markElement(funKeyword);
            }
        };
        TYPE_PARAMETERS_OR_DECLARATION_SIGNATURE = new PositioningStrategy<KtDeclaration>() { // from class: org.jetbrains.kotlin.diagnostics.PositioningStrategies$TYPE_PARAMETERS_OR_DECLARATION_SIGNATURE$1
            @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
            public List<TextRange> mark(KtDeclaration element) {
                KtTypeParameterList ktTypeParameterListMo7395getTypeParameterList;
                Intrinsics.checkNotNullParameter(element, "element");
                if ((element instanceof KtTypeParameterListOwner) && (ktTypeParameterListMo7395getTypeParameterList = ((KtTypeParameterListOwner) element).mo7395getTypeParameterList()) != null) {
                    return PositioningStrategyKt.markElement(ktTypeParameterListMo7395getTypeParameterList);
                }
                return PositioningStrategies.DECLARATION_SIGNATURE.mark(element);
            }
        };
        KtModifierKeywordToken ABSTRACT_KEYWORD = KtTokens.ABSTRACT_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(ABSTRACT_KEYWORD, "ABSTRACT_KEYWORD");
        ABSTRACT_MODIFIER = new ModifierSetBasedPositioningStrategy(ABSTRACT_KEYWORD);
        KtModifierKeywordToken OPEN_KEYWORD = KtTokens.OPEN_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(OPEN_KEYWORD, "OPEN_KEYWORD");
        OPEN_MODIFIER = new ModifierSetBasedPositioningStrategy(OPEN_KEYWORD);
        KtModifierKeywordToken OVERRIDE_KEYWORD = KtTokens.OVERRIDE_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(OVERRIDE_KEYWORD, "OVERRIDE_KEYWORD");
        OVERRIDE_MODIFIER = new ModifierSetBasedPositioningStrategy(OVERRIDE_KEYWORD);
        KtModifierKeywordToken PRIVATE_KEYWORD = KtTokens.PRIVATE_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(PRIVATE_KEYWORD, "PRIVATE_KEYWORD");
        PRIVATE_MODIFIER = new ModifierSetBasedPositioningStrategy(PRIVATE_KEYWORD);
        KtModifierKeywordToken LATEINIT_KEYWORD = KtTokens.LATEINIT_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(LATEINIT_KEYWORD, "LATEINIT_KEYWORD");
        LATEINIT_MODIFIER = new ModifierSetBasedPositioningStrategy(LATEINIT_KEYWORD);
        VARIANCE_MODIFIER = projectionPosition();
        KtModifierKeywordToken CONST_KEYWORD = KtTokens.CONST_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(CONST_KEYWORD, "CONST_KEYWORD");
        CONST_MODIFIER = new ModifierSetBasedPositioningStrategy(CONST_KEYWORD);
        KtModifierKeywordToken FUN_KEYWORD = KtTokens.FUN_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(FUN_KEYWORD, "FUN_KEYWORD");
        FUN_MODIFIER = new ModifierSetBasedPositioningStrategy(FUN_KEYWORD);
        KtModifierKeywordToken SUSPEND_KEYWORD = KtTokens.SUSPEND_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(SUSPEND_KEYWORD, "SUSPEND_KEYWORD");
        SUSPEND_MODIFIER = new ModifierSetBasedPositioningStrategy(SUSPEND_KEYWORD);
        KtModifierKeywordToken DATA_KEYWORD = KtTokens.DATA_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(DATA_KEYWORD, "DATA_KEYWORD");
        DATA_MODIFIER = new ModifierSetBasedPositioningStrategy(DATA_KEYWORD);
        KtModifierKeywordToken OPERATOR_KEYWORD = KtTokens.OPERATOR_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(OPERATOR_KEYWORD, "OPERATOR_KEYWORD");
        OPERATOR_MODIFIER = new ModifierSetBasedPositioningStrategy(OPERATOR_KEYWORD);
        KtModifierKeywordToken ENUM_KEYWORD = KtTokens.ENUM_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(ENUM_KEYWORD, "ENUM_KEYWORD");
        ENUM_MODIFIER = new ModifierSetBasedPositioningStrategy(ENUM_KEYWORD);
        KtModifierKeywordToken TAILREC_KEYWORD = KtTokens.TAILREC_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(TAILREC_KEYWORD, "TAILREC_KEYWORD");
        TAILREC_MODIFIER = new ModifierSetBasedPositioningStrategy(TAILREC_KEYWORD);
        KtModifierKeywordToken EXTERNAL_KEYWORD = KtTokens.EXTERNAL_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(EXTERNAL_KEYWORD, "EXTERNAL_KEYWORD");
        EXTERNAL_MODIFIER = new ModifierSetBasedPositioningStrategy(EXTERNAL_KEYWORD);
        KtModifierKeywordToken EXPECT_KEYWORD = KtTokens.EXPECT_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(EXPECT_KEYWORD, "EXPECT_KEYWORD");
        KtModifierKeywordToken ACTUAL_KEYWORD = KtTokens.ACTUAL_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(ACTUAL_KEYWORD, "ACTUAL_KEYWORD");
        EXPECT_ACTUAL_MODIFIER = new ModifierSetBasedPositioningStrategy(EXPECT_KEYWORD, ACTUAL_KEYWORD);
        OBJECT_KEYWORD = new PositioningStrategy<KtObjectDeclaration>() { // from class: org.jetbrains.kotlin.diagnostics.PositioningStrategies$OBJECT_KEYWORD$1
            @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
            public List<TextRange> mark(KtObjectDeclaration element) {
                Intrinsics.checkNotNullParameter(element, "element");
                KtObjectDeclaration objectKeyword = element.getObjectKeyword();
                if (objectKeyword == null) {
                    objectKeyword = element;
                }
                return PositioningStrategyKt.markElement(objectKeyword);
            }
        };
        FIELD_KEYWORD = new DeclarationHeader<KtBackingField>() { // from class: org.jetbrains.kotlin.diagnostics.PositioningStrategies$FIELD_KEYWORD$1
            @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
            public List<TextRange> mark(KtBackingField element) {
                Intrinsics.checkNotNullParameter(element, "element");
                PsiElement fieldKeyword = element.getFieldKeyword();
                Intrinsics.checkNotNullExpressionValue(fieldKeyword, "getFieldKeyword(...)");
                return PositioningStrategyKt.markElement(fieldKeyword);
            }
        };
        PROPERTY_DELEGATE = new DeclarationHeader<KtProperty>() { // from class: org.jetbrains.kotlin.diagnostics.PositioningStrategies$PROPERTY_DELEGATE$1
            @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
            public List<TextRange> mark(KtProperty element) {
                Intrinsics.checkNotNullParameter(element, "element");
                KtPropertyDelegate delegate = element.getDelegate();
                if (delegate != null) {
                    return PositioningStrategyKt.markElement(delegate);
                }
                return PositioningStrategies.DEFAULT.mark(element);
            }
        };
        FOR_REDECLARATION = new PositioningStrategy<PsiElement>() { // from class: org.jetbrains.kotlin.diagnostics.PositioningStrategies$FOR_REDECLARATION$1
            @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
            public List<TextRange> mark(PsiElement element) {
                PsiElement nameIdentifier;
                Intrinsics.checkNotNullParameter(element, "element");
                if (element instanceof KtNamedDeclaration) {
                    nameIdentifier = ((KtNamedDeclaration) element).mo7392getNameIdentifier();
                } else if (element instanceof KtFile) {
                    KtPackageDirective packageDirective = ((KtFile) element).getPackageDirective();
                    Intrinsics.checkNotNull(packageDirective);
                    nameIdentifier = packageDirective.getNameIdentifier();
                } else {
                    nameIdentifier = null;
                }
                if (nameIdentifier == null && (element instanceof KtObjectDeclaration)) {
                    return PositioningStrategies.DEFAULT.mark(element);
                }
                if (nameIdentifier != null) {
                    element = nameIdentifier;
                }
                return PositioningStrategyKt.markElement(element);
            }
        };
        FOR_UNRESOLVED_REFERENCE = new PositioningStrategy<KtReferenceExpression>() { // from class: org.jetbrains.kotlin.diagnostics.PositioningStrategies$FOR_UNRESOLVED_REFERENCE$1
            @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
            public List<TextRange> mark(KtReferenceExpression element) {
                Intrinsics.checkNotNullParameter(element, "element");
                if (element instanceof KtArrayAccessExpression) {
                    List<TextRange> bracketRanges = ((KtArrayAccessExpression) element).getBracketRanges();
                    Intrinsics.checkNotNullExpressionValue(bracketRanges, "getBracketRanges(...)");
                    if (!bracketRanges.isEmpty()) {
                        return bracketRanges;
                    }
                }
                return CollectionsKt.listOf(element.getTextRange());
            }
        };
        ARRAY_ACCESS = new PositioningStrategy<KtArrayAccessExpression>() { // from class: org.jetbrains.kotlin.diagnostics.PositioningStrategies$ARRAY_ACCESS$1
            @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
            public List<TextRange> mark(KtArrayAccessExpression element) {
                Intrinsics.checkNotNullParameter(element, "element");
                KtContainerNode indicesNode = element.getIndicesNode();
                Intrinsics.checkNotNullExpressionValue(indicesNode, "getIndicesNode(...)");
                return PositioningStrategyKt.markElement(indicesNode);
            }
        };
        SAFE_ACCESS = new PositioningStrategy<PsiElement>() { // from class: org.jetbrains.kotlin.diagnostics.PositioningStrategies$SAFE_ACCESS$1
            @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
            public List<TextRange> mark(PsiElement element) {
                Intrinsics.checkNotNullParameter(element, "element");
                ASTNode aSTNodeFindChildByType = element.getNode().findChildByType(KtTokens.SAFE_ACCESS);
                PsiElement psi = aSTNodeFindChildByType != null ? aSTNodeFindChildByType.getPsi() : null;
                if (psi != null) {
                    element = psi;
                }
                return PositioningStrategyKt.markElement(element);
            }
        };
        TokenSet VISIBILITY_MODIFIERS = KtTokens.VISIBILITY_MODIFIERS;
        Intrinsics.checkNotNullExpressionValue(VISIBILITY_MODIFIERS, "VISIBILITY_MODIFIERS");
        VISIBILITY_MODIFIER = new ModifierSetBasedPositioningStrategy(VISIBILITY_MODIFIERS);
        TokenSet MODALITY_MODIFIERS = KtTokens.MODALITY_MODIFIERS;
        Intrinsics.checkNotNullExpressionValue(MODALITY_MODIFIERS, "MODALITY_MODIFIERS");
        MODALITY_MODIFIER = new ModifierSetBasedPositioningStrategy(MODALITY_MODIFIERS);
        KtModifierKeywordToken INLINE_KEYWORD = KtTokens.INLINE_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(INLINE_KEYWORD, "INLINE_KEYWORD");
        KtModifierKeywordToken VALUE_KEYWORD = KtTokens.VALUE_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(VALUE_KEYWORD, "VALUE_KEYWORD");
        INLINE_OR_VALUE_MODIFIER = new ModifierSetBasedPositioningStrategy(INLINE_KEYWORD, VALUE_KEYWORD);
        KtModifierKeywordToken INNER_KEYWORD = KtTokens.INNER_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(INNER_KEYWORD, "INNER_KEYWORD");
        INNER_MODIFIER = new ModifierSetBasedPositioningStrategy(INNER_KEYWORD);
        KtModifierKeywordToken NOINLINE_KEYWORD = KtTokens.NOINLINE_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(NOINLINE_KEYWORD, "NOINLINE_KEYWORD");
        KtModifierKeywordToken CROSSINLINE_KEYWORD = KtTokens.CROSSINLINE_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(CROSSINLINE_KEYWORD, "CROSSINLINE_KEYWORD");
        INLINE_PARAMETER_MODIFIER = new ModifierSetBasedPositioningStrategy(NOINLINE_KEYWORD, CROSSINLINE_KEYWORD);
        INLINE_FUN_MODIFIER = new InlineFunPositioningStrategy();
        VARIANCE_IN_PROJECTION = new PositioningStrategy<KtTypeProjection>() { // from class: org.jetbrains.kotlin.diagnostics.PositioningStrategies$VARIANCE_IN_PROJECTION$1
            @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
            public List<TextRange> mark(KtTypeProjection element) {
                Intrinsics.checkNotNullParameter(element, "element");
                PsiElement projectionToken = element.getProjectionToken();
                Intrinsics.checkNotNull(projectionToken);
                return PositioningStrategyKt.markElement(projectionToken);
            }
        };
        PARAMETER_DEFAULT_VALUE = new PositioningStrategy<KtParameter>() { // from class: org.jetbrains.kotlin.diagnostics.PositioningStrategies$PARAMETER_DEFAULT_VALUE$1
            @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
            public List<TextRange> mark(KtParameter element) {
                Intrinsics.checkNotNullParameter(element, "element");
                KtExpression defaultValue = element.getDefaultValue();
                Intrinsics.checkNotNull(defaultValue);
                ASTNode node = defaultValue.getNode();
                Intrinsics.checkNotNullExpressionValue(node, "getNode(...)");
                return PositioningStrategyKt.markNode(node);
            }
        };
        PARAMETERS_WITH_DEFAULT_VALUE = new PositioningStrategy<KtFunction>() { // from class: org.jetbrains.kotlin.diagnostics.PositioningStrategies$PARAMETERS_WITH_DEFAULT_VALUE$1
            @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
            public List<TextRange> mark(KtFunction element) {
                Intrinsics.checkNotNullParameter(element, "element");
                List<KtParameter> valueParameters = element.getValueParameters();
                Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
                ArrayList arrayList = new ArrayList();
                for (Object obj : valueParameters) {
                    if (((KtParameter) obj).hasDefaultValue()) {
                        arrayList.add(obj);
                    }
                }
                ArrayList arrayList2 = arrayList;
                List<TextRange> listMarkNode = null;
                if (arrayList2.isEmpty()) {
                    arrayList2 = null;
                }
                if (arrayList2 != null) {
                    ArrayList arrayList3 = new ArrayList();
                    Iterator it = arrayList2.iterator();
                    while (it.hasNext()) {
                        ASTNode node = ((KtParameter) it.next()).getNode();
                        Intrinsics.checkNotNullExpressionValue(node, "getNode(...)");
                        CollectionsKt.addAll(arrayList3, PositioningStrategyKt.markNode(node));
                    }
                    return arrayList3;
                }
                KtParameterList valueParameterList = element.getValueParameterList();
                if (valueParameterList != null) {
                    ASTNode node2 = valueParameterList.getNode();
                    Intrinsics.checkNotNullExpressionValue(node2, "getNode(...)");
                    return PositioningStrategyKt.markNode(node2);
                }
                PsiElement nameIdentifier = element.mo7392getNameIdentifier();
                if (nameIdentifier != null) {
                    ASTNode node3 = nameIdentifier.getNode();
                    Intrinsics.checkNotNullExpressionValue(node3, "getNode(...)");
                    listMarkNode = PositioningStrategyKt.markNode(node3);
                }
                if (listMarkNode != null) {
                    return listMarkNode;
                }
                ASTNode node4 = element.getNode();
                Intrinsics.checkNotNullExpressionValue(node4, "getNode(...)");
                return PositioningStrategyKt.markNode(node4);
            }
        };
        PARAMETER_VARARG_MODIFIER = new PositioningStrategy<KtParameter>() { // from class: org.jetbrains.kotlin.diagnostics.PositioningStrategies$PARAMETER_VARARG_MODIFIER$1
            @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
            public List<TextRange> mark(KtParameter element) {
                Intrinsics.checkNotNullParameter(element, "element");
                KtModifierList modifierList = element.getModifierList();
                Intrinsics.checkNotNull(modifierList);
                PsiElement modifier = modifierList.getModifier(KtTokens.VARARG_KEYWORD);
                Intrinsics.checkNotNull(modifier);
                ASTNode node = modifier.getNode();
                Intrinsics.checkNotNullExpressionValue(node, "getNode(...)");
                return PositioningStrategyKt.markNode(node);
            }
        };
        NAME_OF_NAMED_ARGUMENT = new PositioningStrategy<KtValueArgument>() { // from class: org.jetbrains.kotlin.diagnostics.PositioningStrategies$NAME_OF_NAMED_ARGUMENT$1
            @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
            public List<TextRange> mark(KtValueArgument element) {
                Intrinsics.checkNotNullParameter(element, "element");
                KtValueArgumentName argumentName = element.getArgumentName();
                return PositioningStrategyKt.markElement(argumentName == null ? element : argumentName);
            }
        };
        CALL_ELEMENT = new PositioningStrategy<PsiElement>() { // from class: org.jetbrains.kotlin.diagnostics.PositioningStrategies$CALL_ELEMENT$1
            @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
            public List<TextRange> mark(PsiElement element) {
                Intrinsics.checkNotNullParameter(element, "element");
                KtCallElement ktCallElement = element instanceof KtCallElement ? (KtCallElement) element : null;
                KtExpression calleeExpression = ktCallElement != null ? ktCallElement.getCalleeExpression() : null;
                if (calleeExpression != null) {
                    element = calleeExpression;
                }
                return PositioningStrategyKt.markElement(element);
            }
        };
        CALL_ELEMENT_WITH_DOT = new PositioningStrategy<KtQualifiedExpression>() { // from class: org.jetbrains.kotlin.diagnostics.PositioningStrategies$CALL_ELEMENT_WITH_DOT$1
            @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
            public List<TextRange> mark(KtQualifiedExpression element) {
                Intrinsics.checkNotNullParameter(element, "element");
                KtQualifiedExpression ktQualifiedExpression = element;
                List<TextRange> listMark = PositioningStrategies.INSTANCE.getSELECTOR_BY_QUALIFIED().mark(ktQualifiedExpression);
                if (listMark.size() != 1) {
                    return listMark;
                }
                TextRange textRange = (TextRange) CollectionsKt.first((List) listMark);
                List<TextRange> listMark2 = PositioningStrategies.SAFE_ACCESS.mark(ktQualifiedExpression);
                return listMark2.size() == 1 ? CollectionsKt.listOf(new TextRange(((TextRange) CollectionsKt.first((List) listMark2)).getStartOffset(), textRange.getEndOffset())) : listMark2;
            }
        };
        DECLARATION_WITH_BODY = new PositioningStrategy<KtDeclarationWithBody>() { // from class: org.jetbrains.kotlin.diagnostics.PositioningStrategies$DECLARATION_WITH_BODY$1
            @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
            public List<TextRange> mark(KtDeclarationWithBody element) {
                Intrinsics.checkNotNullParameter(element, "element");
                KtBlockExpression bodyBlockExpression = element.getBodyBlockExpression();
                TextRange lastBracketRange = bodyBlockExpression != null ? bodyBlockExpression.getLastBracketRange() : null;
                if (lastBracketRange != null) {
                    return PositioningStrategyKt.markRange(lastBracketRange);
                }
                return PositioningStrategyKt.markElement(element);
            }

            @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
            public boolean isValid(KtDeclarationWithBody element) {
                Intrinsics.checkNotNullParameter(element, "element");
                if (super.isValid(element)) {
                    KtBlockExpression bodyBlockExpression = element.getBodyBlockExpression();
                    if ((bodyBlockExpression != null ? bodyBlockExpression.getLastBracketRange() : null) != null) {
                        return true;
                    }
                }
                return false;
            }
        };
        VAL_OR_VAR_NODE = new PositioningStrategy<KtDeclaration>() { // from class: org.jetbrains.kotlin.diagnostics.PositioningStrategies$VAL_OR_VAR_NODE$1
            @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
            public List<TextRange> mark(KtDeclaration element) {
                Intrinsics.checkNotNullParameter(element, "element");
                if (element instanceof KtParameter) {
                    KtDeclaration valOrVarKeyword = ((KtParameter) element).getValOrVarKeyword();
                    if (valOrVarKeyword == null) {
                        valOrVarKeyword = element;
                    }
                    return PositioningStrategyKt.markElement(valOrVarKeyword);
                }
                if (element instanceof KtProperty) {
                    PsiElement valOrVarKeyword2 = ((KtProperty) element).getValOrVarKeyword();
                    Intrinsics.checkNotNullExpressionValue(valOrVarKeyword2, "getValOrVarKeyword(...)");
                    return PositioningStrategyKt.markElement(valOrVarKeyword2);
                }
                if (!(element instanceof KtDestructuringDeclaration)) {
                    throw new IllegalStateException(("Declaration is neither a parameter nor a property: " + PsiUtilsKt.getElementTextWithContext(element)).toString());
                }
                KtDeclaration valOrVarKeyword3 = ((KtDestructuringDeclaration) element).getValOrVarKeyword();
                if (valOrVarKeyword3 == null) {
                    valOrVarKeyword3 = element;
                }
                return PositioningStrategyKt.markElement(valOrVarKeyword3);
            }
        };
        ELSE_ENTRY = new PositioningStrategy<KtWhenEntry>() { // from class: org.jetbrains.kotlin.diagnostics.PositioningStrategies$ELSE_ENTRY$1
            @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
            public List<TextRange> mark(KtWhenEntry element) {
                Intrinsics.checkNotNullParameter(element, "element");
                PsiElement elseKeyword = element.getElseKeyword();
                Intrinsics.checkNotNull(elseKeyword);
                return PositioningStrategyKt.markElement(elseKeyword);
            }
        };
        WHEN_EXPRESSION = new PositioningStrategy<KtWhenExpression>() { // from class: org.jetbrains.kotlin.diagnostics.PositioningStrategies$WHEN_EXPRESSION$1
            @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
            public List<TextRange> mark(KtWhenExpression element) {
                Intrinsics.checkNotNullParameter(element, "element");
                PsiElement whenKeyword = element.getWhenKeyword();
                Intrinsics.checkNotNullExpressionValue(whenKeyword, "getWhenKeyword(...)");
                return PositioningStrategyKt.markElement(whenKeyword);
            }
        };
        IF_EXPRESSION = new PositioningStrategy<KtIfExpression>() { // from class: org.jetbrains.kotlin.diagnostics.PositioningStrategies$IF_EXPRESSION$1
            @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
            public List<TextRange> mark(KtIfExpression element) {
                Intrinsics.checkNotNullParameter(element, "element");
                PsiElement ifKeyword = element.getIfKeyword();
                Intrinsics.checkNotNullExpressionValue(ifKeyword, "getIfKeyword(...)");
                return PositioningStrategyKt.markElement(ifKeyword);
            }
        };
        WHEN_CONDITION_IN_RANGE = new PositioningStrategy<KtWhenConditionInRange>() { // from class: org.jetbrains.kotlin.diagnostics.PositioningStrategies$WHEN_CONDITION_IN_RANGE$1
            @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
            public List<TextRange> mark(KtWhenConditionInRange element) {
                Intrinsics.checkNotNullParameter(element, "element");
                KtOperationReferenceExpression operationReference = element.getOperationReference();
                Intrinsics.checkNotNullExpressionValue(operationReference, "getOperationReference(...)");
                return PositioningStrategyKt.markElement(operationReference);
            }
        };
        SPECIAL_CONSTRUCT_TOKEN = new PositioningStrategy<KtExpression>() { // from class: org.jetbrains.kotlin.diagnostics.PositioningStrategies$SPECIAL_CONSTRUCT_TOKEN$1
            @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
            public List<TextRange> mark(KtExpression element) {
                Intrinsics.checkNotNullParameter(element, "element");
                if (element instanceof KtWhenExpression) {
                    PsiElement whenKeyword = ((KtWhenExpression) element).getWhenKeyword();
                    Intrinsics.checkNotNullExpressionValue(whenKeyword, "getWhenKeyword(...)");
                    return PositioningStrategyKt.markElement(whenKeyword);
                }
                if (element instanceof KtIfExpression) {
                    PsiElement ifKeyword = ((KtIfExpression) element).getIfKeyword();
                    Intrinsics.checkNotNullExpressionValue(ifKeyword, "getIfKeyword(...)");
                    return PositioningStrategyKt.markElement(ifKeyword);
                }
                if (!(element instanceof KtOperationExpression)) {
                    throw new IllegalStateException(("Expression is not an if, when or operation expression: " + PsiUtilsKt.getElementTextWithContext(element)).toString());
                }
                KtSimpleNameExpression operationReference = ((KtOperationExpression) element).getOperationReference();
                Intrinsics.checkNotNullExpressionValue(operationReference, "getOperationReference(...)");
                return PositioningStrategyKt.markElement(operationReference);
            }
        };
        REDUNDANT_NULLABLE = new PositioningStrategy<KtTypeReference>() { // from class: org.jetbrains.kotlin.diagnostics.PositioningStrategies$REDUNDANT_NULLABLE$1
            @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
            public List<TextRange> mark(KtTypeReference element) {
                Intrinsics.checkNotNullParameter(element, "element");
                KtTypeElement typeElement = element.getTypeElement();
                ASTNode aSTNode = null;
                ASTNode aSTNode2 = null;
                ASTNode aSTNode3 = null;
                while (typeElement instanceof KtNullableType) {
                    KtNullableType ktNullableType = (KtNullableType) typeElement;
                    ASTNode questionMarkNode = ktNullableType.getQuestionMarkNode();
                    if (aSTNode == null) {
                        aSTNode = questionMarkNode;
                    }
                    typeElement = ktNullableType.getInnerType();
                    aSTNode3 = aSTNode2;
                    aSTNode2 = questionMarkNode;
                }
                if (aSTNode != null) {
                    if (aSTNode3 == null) {
                        aSTNode3 = aSTNode;
                    }
                    PsiElement psi = aSTNode3.getPsi();
                    Intrinsics.checkNotNullExpressionValue(psi, "getPsi(...)");
                    PsiElement psi2 = aSTNode.getPsi();
                    Intrinsics.checkNotNullExpressionValue(psi2, "getPsi(...)");
                    return PositioningStrategyKt.markRange(psi, psi2);
                }
                return super.mark(element);
            }
        };
        NULLABLE_TYPE = new PositioningStrategy<KtNullableType>() { // from class: org.jetbrains.kotlin.diagnostics.PositioningStrategies$NULLABLE_TYPE$1
            @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
            public List<TextRange> mark(KtNullableType element) {
                Intrinsics.checkNotNullParameter(element, "element");
                ASTNode questionMarkNode = element.getQuestionMarkNode();
                Intrinsics.checkNotNullExpressionValue(questionMarkNode, "getQuestionMarkNode(...)");
                return PositioningStrategyKt.markNode(questionMarkNode);
            }
        };
        QUESTION_MARK_BY_TYPE = new PositioningStrategy<KtTypeReference>() { // from class: org.jetbrains.kotlin.diagnostics.PositioningStrategies$QUESTION_MARK_BY_TYPE$1
            @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
            public List<TextRange> mark(KtTypeReference element) {
                Intrinsics.checkNotNullParameter(element, "element");
                KtTypeElement typeElement = element.getTypeElement();
                if (typeElement instanceof KtNullableType) {
                    ASTNode questionMarkNode = ((KtNullableType) typeElement).getQuestionMarkNode();
                    Intrinsics.checkNotNullExpressionValue(questionMarkNode, "getQuestionMarkNode(...)");
                    return PositioningStrategyKt.markNode(questionMarkNode);
                }
                return super.mark(element);
            }
        };
        CALL_EXPRESSION = new PositioningStrategy<PsiElement>() { // from class: org.jetbrains.kotlin.diagnostics.PositioningStrategies$CALL_EXPRESSION$1
            @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
            public List<TextRange> mark(PsiElement element) {
                KtExpression ktExpression;
                Intrinsics.checkNotNullParameter(element, "element");
                if (element instanceof KtCallExpression) {
                    KtCallExpression ktCallExpression = (KtCallExpression) element;
                    KtTypeArgumentList typeArgumentList = ktCallExpression.getTypeArgumentList();
                    if (typeArgumentList != null) {
                        ktExpression = typeArgumentList;
                    } else {
                        KtExpression calleeExpression = ktCallExpression.getCalleeExpression();
                        ktExpression = calleeExpression != null ? calleeExpression : (KtElement) element;
                    }
                    return PositioningStrategyKt.markRange(element, ktExpression);
                }
                return PositioningStrategyKt.markElement(element);
            }
        };
        VALUE_ARGUMENTS = new PositioningStrategy<KtElement>() { // from class: org.jetbrains.kotlin.diagnostics.PositioningStrategies$VALUE_ARGUMENTS$1
            @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
            public List<TextRange> mark(KtElement element) {
                PsiElement psiElementUnwrapParenthesesLabelsAndAnnotations;
                Intrinsics.checkNotNullParameter(element, "element");
                if (element instanceof KtBinaryExpression) {
                    KtBinaryExpression ktBinaryExpression = (KtBinaryExpression) element;
                    if (KtTokens.ALL_ASSIGNMENTS.contains(ktBinaryExpression.getOperationToken()) && (psiElementUnwrapParenthesesLabelsAndAnnotations = PsiUtilsKt.unwrapParenthesesLabelsAndAnnotations(ktBinaryExpression.getLeft())) != null) {
                        return PositioningStrategyKt.markElement(psiElementUnwrapParenthesesLabelsAndAnnotations);
                    }
                }
                if (element instanceof KtQualifiedExpression) {
                    KtExpression selectorExpression = ((KtQualifiedExpression) element).getSelectorExpression();
                    if (selectorExpression == null) {
                        selectorExpression = (KtExpression) element;
                    }
                    element = selectorExpression;
                } else if (element instanceof KtClassOrObject) {
                    KtSuperTypeList superTypeList = ((KtClassOrObject) element).getSuperTypeList();
                    element = superTypeList != null ? superTypeList : (KtElementImplStub) element;
                }
                PsiElement psiElement = null;
                KtValueArgumentList ktValueArgumentList = element instanceof KtValueArgumentList ? (KtValueArgumentList) element : null;
                if (ktValueArgumentList == null) {
                    ktValueArgumentList = (KtValueArgumentList) PsiTreeUtil.getChildOfType(element, KtValueArgumentList.class);
                }
                if (ktValueArgumentList != null) {
                    PsiElement rightParenthesis = ktValueArgumentList.getRightParenthesis();
                    if (rightParenthesis == null) {
                        return PositioningStrategyKt.markElement(element);
                    }
                    PsiElement[] children = ktValueArgumentList.getChildren();
                    Intrinsics.checkNotNullExpressionValue(children, "getChildren(...)");
                    PsiElement[] psiElementArr = children;
                    int length = psiElementArr.length - 1;
                    if (length >= 0) {
                        while (true) {
                            int i = length - 1;
                            PsiElement psiElement2 = psiElementArr[length];
                            if (psiElement2 instanceof KtValueArgument) {
                                psiElement = psiElement2;
                                break;
                            }
                            if (i < 0) {
                                break;
                            }
                            length = i;
                        }
                    }
                    PsiElement psiElement3 = psiElement;
                    if (psiElement3 != null) {
                        return PositioningStrategyKt.markRange(psiElement3, rightParenthesis);
                    }
                    KtElement leftParenthesis = ktValueArgumentList.getLeftParenthesis();
                    if (leftParenthesis == null) {
                        leftParenthesis = element;
                    }
                    return PositioningStrategyKt.markRange(leftParenthesis, rightParenthesis);
                }
                if (element instanceof KtCallExpression) {
                    KtNameReferenceExpression ktNameReferenceExpression = (KtNameReferenceExpression) PsiTreeUtil.getChildOfType(element, KtNameReferenceExpression.class);
                    return PositioningStrategyKt.markElement(ktNameReferenceExpression != null ? ktNameReferenceExpression : element);
                }
                return PositioningStrategyKt.markElement(element);
            }
        };
        VALUE_ARGUMENTS_LIST = new PositioningStrategy<KtElement>() { // from class: org.jetbrains.kotlin.diagnostics.PositioningStrategies$VALUE_ARGUMENTS_LIST$1
            @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
            public List<TextRange> mark(KtElement element) {
                Intrinsics.checkNotNullParameter(element, "element");
                KtValueArgumentList ktValueArgumentList = (KtValueArgumentList) PsiTreeUtil.getChildOfType(element, KtValueArgumentList.class);
                if (ktValueArgumentList != null) {
                    element = ktValueArgumentList;
                }
                return PositioningStrategyKt.markElement(element);
            }
        };
        FUNCTION_PARAMETERS = new PositioningStrategy<KtFunction>() { // from class: org.jetbrains.kotlin.diagnostics.PositioningStrategies$FUNCTION_PARAMETERS$1
            @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
            public List<TextRange> mark(KtFunction element) {
                Intrinsics.checkNotNullParameter(element, "element");
                KtParameterList valueParameterList = element.getValueParameterList();
                if (valueParameterList != null) {
                    return PositioningStrategyKt.markElement(valueParameterList);
                }
                if (element instanceof KtFunctionLiteral) {
                    ASTNode node = ((KtFunctionLiteral) element).getLBrace().getNode();
                    Intrinsics.checkNotNullExpressionValue(node, "getNode(...)");
                    return PositioningStrategyKt.markNode(node);
                }
                return PositioningStrategies.DECLARATION_SIGNATURE_OR_DEFAULT.mark(element);
            }
        };
        CUT_CHAR_QUOTES = new PositioningStrategy<KtElement>() { // from class: org.jetbrains.kotlin.diagnostics.PositioningStrategies$CUT_CHAR_QUOTES$1
            @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
            public List<TextRange> mark(KtElement element) {
                Intrinsics.checkNotNullParameter(element, "element");
                if ((element instanceof KtConstantExpression) && Intrinsics.areEqual(((KtConstantExpression) element).getNode().getElementType(), KtNodeTypes.CHARACTER_CONSTANT)) {
                    TextRange textRange = element.getTextRange();
                    TextRange textRangeCreate = TextRange.create(textRange.getStartOffset() + 1, textRange.getEndOffset() - 1);
                    Intrinsics.checkNotNullExpressionValue(textRangeCreate, "create(...)");
                    return CollectionsKt.listOf(textRangeCreate);
                }
                return PositioningStrategyKt.markElement(element);
            }
        };
        LONG_LITERAL_SUFFIX = new PositioningStrategy<KtElement>() { // from class: org.jetbrains.kotlin.diagnostics.PositioningStrategies$LONG_LITERAL_SUFFIX$1
            @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
            public List<TextRange> mark(KtElement element) {
                Intrinsics.checkNotNullParameter(element, "element");
                if ((element instanceof KtConstantExpression) && Intrinsics.areEqual(((KtConstantExpression) element).getNode().getElementType(), KtNodeTypes.INTEGER_CONSTANT)) {
                    int endOffset = PsiUtilsKt.getEndOffset(element);
                    TextRange textRangeCreate = TextRange.create(endOffset - 1, endOffset);
                    Intrinsics.checkNotNullExpressionValue(textRangeCreate, "create(...)");
                    return CollectionsKt.listOf(textRangeCreate);
                }
                return PositioningStrategyKt.markElement(element);
            }
        };
        AS_TYPE = new PositioningStrategy<KtBinaryExpressionWithTypeRHS>() { // from class: org.jetbrains.kotlin.diagnostics.PositioningStrategies$AS_TYPE$1
            @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
            public List<TextRange> mark(KtBinaryExpressionWithTypeRHS element) {
                Intrinsics.checkNotNullParameter(element, "element");
                KtSimpleNameExpression operationReference = element.getOperationReference();
                Intrinsics.checkNotNullExpressionValue(operationReference, "getOperationReference(...)");
                return PositioningStrategyKt.markRange(operationReference, element);
            }
        };
        KtModifierKeywordToken COMPANION_KEYWORD = KtTokens.COMPANION_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(COMPANION_KEYWORD, "COMPANION_KEYWORD");
        COMPANION_OBJECT = new ModifierSetBasedPositioningStrategy(COMPANION_KEYWORD);
        SECONDARY_CONSTRUCTOR_DELEGATION_CALL = new PositioningStrategy<PsiElement>() { // from class: org.jetbrains.kotlin.diagnostics.PositioningStrategies$SECONDARY_CONSTRUCTOR_DELEGATION_CALL$1
            @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
            public List<TextRange> mark(PsiElement element) {
                Intrinsics.checkNotNullParameter(element, "element");
                if (element instanceof KtSecondaryConstructor) {
                    KtSecondaryConstructor ktSecondaryConstructor = (KtSecondaryConstructor) element;
                    KtParameterList valueParameterList = ktSecondaryConstructor.getValueParameterList();
                    if (valueParameterList == null) {
                        return PositioningStrategyKt.markElement(element);
                    }
                    PsiElement constructorKeyword = ktSecondaryConstructor.getConstructorKeyword();
                    PsiElement lastChild = valueParameterList.getLastChild();
                    Intrinsics.checkNotNullExpressionValue(lastChild, "getLastChild(...)");
                    return PositioningStrategyKt.markRange(constructorKeyword, lastChild);
                }
                if (element instanceof KtConstructorDelegationCall) {
                    KtConstructorDelegationCall ktConstructorDelegationCall = (KtConstructorDelegationCall) element;
                    if (!ktConstructorDelegationCall.isImplicit()) {
                        KtConstructorDelegationReferenceExpression calleeExpression = ktConstructorDelegationCall.getCalleeExpression();
                        return PositioningStrategyKt.markElement(calleeExpression == null ? (KtElementImpl) element : calleeExpression);
                    }
                    PsiElement parentOfType = PsiTreeUtil.getParentOfType(element, KtSecondaryConstructor.class, true);
                    Intrinsics.checkNotNull(parentOfType);
                    KtSecondaryConstructor ktSecondaryConstructor2 = (KtSecondaryConstructor) parentOfType;
                    KtParameterList valueParameterList2 = ktSecondaryConstructor2.getValueParameterList();
                    if (valueParameterList2 == null) {
                        return PositioningStrategyKt.markElement(ktSecondaryConstructor2);
                    }
                    PsiElement constructorKeyword2 = ktSecondaryConstructor2.getConstructorKeyword();
                    PsiElement lastChild2 = valueParameterList2.getLastChild();
                    Intrinsics.checkNotNullExpressionValue(lastChild2, "getLastChild(...)");
                    return PositioningStrategyKt.markRange(constructorKeyword2, lastChild2);
                }
                return PositioningStrategyKt.markElement(element);
            }
        };
        DELEGATOR_SUPER_CALL = new PositioningStrategy<KtEnumEntry>() { // from class: org.jetbrains.kotlin.diagnostics.PositioningStrategies$DELEGATOR_SUPER_CALL$1
            @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
            public List<TextRange> mark(KtEnumEntry element) {
                Intrinsics.checkNotNullParameter(element, "element");
                List<KtSuperTypeListEntry> superTypeListEntries = element.getSuperTypeListEntries();
                Intrinsics.checkNotNullExpressionValue(superTypeListEntries, "getSuperTypeListEntries(...)");
                KtEnumEntry ktEnumEntry = element;
                if (!superTypeListEntries.isEmpty()) {
                    KtSuperTypeListEntry ktSuperTypeListEntry = superTypeListEntries.get(0);
                    Intrinsics.checkNotNullExpressionValue(ktSuperTypeListEntry, "get(...)");
                    ktEnumEntry = ktSuperTypeListEntry;
                }
                return PositioningStrategyKt.markElement(ktEnumEntry);
            }
        };
        UNUSED_VALUE = new PositioningStrategy<KtBinaryExpression>() { // from class: org.jetbrains.kotlin.diagnostics.PositioningStrategies$UNUSED_VALUE$1
            @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
            public List<TextRange> mark(KtBinaryExpression element) {
                Intrinsics.checkNotNullParameter(element, "element");
                KtExpression left = element.getLeft();
                Intrinsics.checkNotNull(left);
                int startOffset = PsiUtilsKt.getStartOffset(left);
                KtOperationReferenceExpression operationReference = element.getOperationReference();
                Intrinsics.checkNotNullExpressionValue(operationReference, "getOperationReference(...)");
                return CollectionsKt.listOf(new TextRange(startOffset, PsiUtilsKt.getEndOffset(operationReference)));
            }
        };
        USELESS_ELVIS = new PositioningStrategy<KtBinaryExpression>() { // from class: org.jetbrains.kotlin.diagnostics.PositioningStrategies$USELESS_ELVIS$1
            @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
            public List<TextRange> mark(KtBinaryExpression element) {
                Intrinsics.checkNotNullParameter(element, "element");
                KtOperationReferenceExpression operationReference = element.getOperationReference();
                Intrinsics.checkNotNullExpressionValue(operationReference, "getOperationReference(...)");
                return CollectionsKt.listOf(new TextRange(PsiUtilsKt.getStartOffset(operationReference), PsiUtilsKt.getEndOffset(element)));
            }
        };
        IMPORT_ALIAS = new PositioningStrategy<KtImportDirective>() { // from class: org.jetbrains.kotlin.diagnostics.PositioningStrategies$IMPORT_ALIAS$1
            @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
            public List<TextRange> mark(KtImportDirective element) {
                KtExpression selectorExpression;
                PsiElement psiElementMo7392getNameIdentifier;
                Intrinsics.checkNotNullParameter(element, "element");
                KtImportAlias alias = element.getAlias();
                if (alias != null && (psiElementMo7392getNameIdentifier = alias.mo7392getNameIdentifier()) != null) {
                    return PositioningStrategyKt.markElement(psiElementMo7392getNameIdentifier);
                }
                KtExpression importedReference = element.getImportedReference();
                if (importedReference != null) {
                    return (!(importedReference instanceof KtQualifiedExpression) || (selectorExpression = ((KtQualifiedExpression) importedReference).getSelectorExpression()) == null) ? PositioningStrategyKt.markElement(importedReference) : PositioningStrategyKt.markElement(selectorExpression);
                }
                return PositioningStrategyKt.markElement(element);
            }
        };
        RETURN_WITH_LABEL = new PositioningStrategy<KtReturnExpression>() { // from class: org.jetbrains.kotlin.diagnostics.PositioningStrategies$RETURN_WITH_LABEL$1
            @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
            public List<TextRange> mark(KtReturnExpression element) {
                Intrinsics.checkNotNullParameter(element, "element");
                PsiElement labeledExpression = element.getLabeledExpression();
                if (labeledExpression != null) {
                    return PositioningStrategyKt.markRange(element, labeledExpression);
                }
                PsiElement returnKeyword = element.getReturnKeyword();
                Intrinsics.checkNotNullExpressionValue(returnKeyword, "getReturnKeyword(...)");
                return PositioningStrategyKt.markElement(returnKeyword);
            }
        };
        RECEIVER = new DeclarationHeader<KtCallableDeclaration>() { // from class: org.jetbrains.kotlin.diagnostics.PositioningStrategies$RECEIVER$1
            @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
            public List<TextRange> mark(KtCallableDeclaration element) {
                Intrinsics.checkNotNullParameter(element, "element");
                KtTypeReference ktTypeReferenceMo7393getReceiverTypeReference = element.mo7393getReceiverTypeReference();
                if (ktTypeReferenceMo7393getReceiverTypeReference == null) {
                    return PositioningStrategies.DEFAULT.mark(element);
                }
                return PositioningStrategyKt.markElement(ktTypeReferenceMo7393getReceiverTypeReference);
            }
        };
        OPERATOR = new PositioningStrategy<KtExpression>() { // from class: org.jetbrains.kotlin.diagnostics.PositioningStrategies$OPERATOR$1
            @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
            public List<TextRange> mark(KtExpression element) {
                Intrinsics.checkNotNullParameter(element, "element");
                if (element instanceof KtBinaryExpression) {
                    KtOperationReferenceExpression operationReference = ((KtBinaryExpression) element).getOperationReference();
                    Intrinsics.checkNotNullExpressionValue(operationReference, "getOperationReference(...)");
                    return mark((KtExpression) operationReference);
                }
                if (element instanceof KtBinaryExpressionWithTypeRHS) {
                    KtSimpleNameExpression operationReference2 = ((KtBinaryExpressionWithTypeRHS) element).getOperationReference();
                    Intrinsics.checkNotNullExpressionValue(operationReference2, "getOperationReference(...)");
                    return mark((KtExpression) operationReference2);
                }
                if (!(element instanceof KtUnaryExpression)) {
                    return super.mark(element);
                }
                KtSimpleNameExpression operationReference3 = ((KtUnaryExpression) element).getOperationReference();
                Intrinsics.checkNotNullExpressionValue(operationReference3, "getOperationReference(...)");
                return mark((KtExpression) operationReference3);
            }
        };
        DOT_BY_QUALIFIED = new PositioningStrategy<PsiElement>() { // from class: org.jetbrains.kotlin.diagnostics.PositioningStrategies$DOT_BY_QUALIFIED$1
            @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
            public List<TextRange> mark(PsiElement element) {
                KtExpression left;
                Intrinsics.checkNotNullParameter(element, "element");
                if (element instanceof KtBinaryExpression) {
                    KtBinaryExpression ktBinaryExpression = (KtBinaryExpression) element;
                    if (KtTokens.ALL_ASSIGNMENTS.contains(ktBinaryExpression.getOperationToken()) && (left = ktBinaryExpression.getLeft()) != null) {
                        KtExpression ktExpression = left;
                        final PositioningStrategies$DOT_BY_QUALIFIED$1$mark$lambda$1$$inlined$findDescendantOfType$default$1 positioningStrategies$DOT_BY_QUALIFIED$1$mark$lambda$1$$inlined$findDescendantOfType$default$1 = new Function1<KtDotQualifiedExpression, Boolean>() { // from class: org.jetbrains.kotlin.diagnostics.PositioningStrategies$DOT_BY_QUALIFIED$1$mark$lambda$1$$inlined$findDescendantOfType$default$1
                            @Override // kotlin.jvm.functions.Function1
                            public final Boolean invoke(KtDotQualifiedExpression it) {
                                Intrinsics.checkNotNullParameter(it, "it");
                                return true;
                            }
                        };
                        PsiUtilsKt.checkDecompiledText(ktExpression);
                        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
                        ktExpression.accept(new PsiRecursiveElementWalkingVisitor() { // from class: org.jetbrains.kotlin.diagnostics.PositioningStrategies$DOT_BY_QUALIFIED$1$mark$lambda$1$$inlined$findDescendantOfType$default$2
                            /* JADX WARN: Multi-variable type inference failed */
                            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiRecursiveElementWalkingVisitor, org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor
                            public void visitElement(PsiElement element2) {
                                Intrinsics.checkNotNullParameter(element2, "element");
                                if ((element2 instanceof KtDotQualifiedExpression) && ((Boolean) positioningStrategies$DOT_BY_QUALIFIED$1$mark$lambda$1$$inlined$findDescendantOfType$default$1.invoke(element2)).booleanValue()) {
                                    objectRef.element = element2;
                                    stopWalking();
                                } else {
                                    super.visitElement(element2);
                                }
                            }
                        });
                        KtDotQualifiedExpression ktDotQualifiedExpression = (KtDotQualifiedExpression) ((PsiElement) objectRef.element);
                        if (ktDotQualifiedExpression != null) {
                            return mark(ktDotQualifiedExpression);
                        }
                    }
                }
                if (element instanceof KtDotQualifiedExpression) {
                    PsiElement psi = ((KtDotQualifiedExpression) element).getOperationTokenNode().getPsi();
                    Intrinsics.checkNotNullExpressionValue(psi, "getPsi(...)");
                    return mark(psi);
                }
                return PositioningStrategies.INSTANCE.getREFERENCE_BY_QUALIFIED().mark(element);
            }
        };
        SELECTOR_BY_QUALIFIED = new PositioningStrategy<PsiElement>() { // from class: org.jetbrains.kotlin.diagnostics.PositioningStrategies$SELECTOR_BY_QUALIFIED$1
            @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
            public List<TextRange> mark(PsiElement element) {
                KtTypeElement typeElement;
                KtElement referencedTypeExpression;
                PsiElement psiElementMo7392getNameIdentifier;
                KtExpression left;
                Intrinsics.checkNotNullParameter(element, "element");
                if (element instanceof KtBinaryExpression) {
                    KtBinaryExpression ktBinaryExpression = (KtBinaryExpression) element;
                    if (KtTokens.ALL_ASSIGNMENTS.contains(ktBinaryExpression.getOperationToken()) && (left = ktBinaryExpression.getLeft()) != null) {
                        return mark(left);
                    }
                }
                if (element instanceof KtQualifiedExpression) {
                    KtExpression selectorExpression = ((KtQualifiedExpression) element).getSelectorExpression();
                    if (selectorExpression instanceof KtElement) {
                        return mark(selectorExpression);
                    }
                }
                if (element instanceof KtImportDirective) {
                    KtImportDirective ktImportDirective = (KtImportDirective) element;
                    KtImportAlias alias = ktImportDirective.getAlias();
                    if (alias != null && (psiElementMo7392getNameIdentifier = alias.mo7392getNameIdentifier()) != null) {
                        return mark(psiElementMo7392getNameIdentifier);
                    }
                    KtExpression importedReference = ktImportDirective.getImportedReference();
                    if (importedReference != null) {
                        return mark(importedReference);
                    }
                }
                return (!(element instanceof KtTypeReference) || (typeElement = ((KtTypeReference) element).getTypeElement()) == null || (referencedTypeExpression = PositioningStrategies.INSTANCE.getReferencedTypeExpression(typeElement)) == null) ? super.mark(element) : mark(referencedTypeExpression);
            }
        };
        NAME_IDENTIFIER = new PositioningStrategy<PsiElement>() { // from class: org.jetbrains.kotlin.diagnostics.PositioningStrategies$NAME_IDENTIFIER$1
            @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
            public List<TextRange> mark(PsiElement element) {
                PsiElement nameIdentifier;
                Intrinsics.checkNotNullParameter(element, "element");
                if (element instanceof PsiNameIdentifierOwner) {
                    PsiElement psiElementMo7392getNameIdentifier = ((PsiNameIdentifierOwner) element).mo7392getNameIdentifier();
                    if (psiElementMo7392getNameIdentifier != null) {
                        return super.mark(psiElementMo7392getNameIdentifier);
                    }
                } else {
                    if (element instanceof KtLabelReferenceExpression) {
                        return super.mark(((KtLabelReferenceExpression) element).getReferencedNameElement());
                    }
                    if ((element instanceof KtPackageDirective) && (nameIdentifier = ((KtPackageDirective) element).getNameIdentifier()) != null) {
                        return super.mark(nameIdentifier);
                    }
                }
                return PositioningStrategies.DEFAULT.mark(element);
            }
        };
        SPREAD_OPERATOR = new PositioningStrategy<PsiElement>() { // from class: org.jetbrains.kotlin.diagnostics.PositioningStrategies$SPREAD_OPERATOR$1
            @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
            public List<TextRange> mark(PsiElement element) {
                LeafPsiElement spreadElement;
                ASTNode node;
                Intrinsics.checkNotNullParameter(element, "element");
                PsiElement psi = null;
                KtValueArgument ktValueArgument = element instanceof KtValueArgument ? (KtValueArgument) element : null;
                if (ktValueArgument != null && (spreadElement = ktValueArgument.getSpreadElement()) != null && (node = spreadElement.getNode()) != null) {
                    psi = node.getPsi();
                }
                if (psi != null) {
                    element = psi;
                }
                return super.mark(element);
            }
        };
        FUN_INTERFACE = new PositioningStrategy<KtDeclaration>() { // from class: org.jetbrains.kotlin.diagnostics.PositioningStrategies$FUN_INTERFACE$1
            @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
            public List<TextRange> mark(KtDeclaration element) {
                Intrinsics.checkNotNullParameter(element, "element");
                if (element instanceof KtClass) {
                    return PositioningStrategies.FUN_MODIFIER.mark(element);
                }
                if (element instanceof KtProperty) {
                    PsiElement valOrVarKeyword = ((KtProperty) element).getValOrVarKeyword();
                    Intrinsics.checkNotNullExpressionValue(valOrVarKeyword, "getValOrVarKeyword(...)");
                    return PositioningStrategyKt.markElement(valOrVarKeyword);
                }
                if (element instanceof KtNamedFunction) {
                    KtNamedFunction ktNamedFunction = (KtNamedFunction) element;
                    KtTypeParameterList typeParameterList = ktNamedFunction.mo7395getTypeParameterList();
                    if (typeParameterList != null) {
                        return PositioningStrategyKt.markElement(typeParameterList);
                    }
                    if (element.hasModifier(KtTokens.SUSPEND_KEYWORD)) {
                        return PositioningStrategies.SUSPEND_MODIFIER.mark(element);
                    }
                    KtDeclaration funKeyword = ktNamedFunction.getFunKeyword();
                    if (funKeyword == null) {
                        funKeyword = element;
                    }
                    return PositioningStrategyKt.markElement(funKeyword);
                }
                return PositioningStrategyKt.markElement(element);
            }
        };
        REFERENCE_BY_QUALIFIED = new FindReferencePositioningStrategy(false);
        REFERENCED_NAME_BY_QUALIFIED = new FindReferencePositioningStrategy(true);
        KtModifierKeywordToken REIFIED_KEYWORD = KtTokens.REIFIED_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(REIFIED_KEYWORD, "REIFIED_KEYWORD");
        REIFIED_MODIFIER = new ModifierSetBasedPositioningStrategy(REIFIED_KEYWORD);
        PROPERTY_INITIALIZER = new PositioningStrategy<KtNamedDeclaration>() { // from class: org.jetbrains.kotlin.diagnostics.PositioningStrategies$PROPERTY_INITIALIZER$1
            @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
            public List<TextRange> mark(KtNamedDeclaration element) {
                KtNamedDeclaration ktNamedDeclaration;
                Intrinsics.checkNotNullParameter(element, "element");
                if (element instanceof KtProperty) {
                    KtNamedDeclaration initializer = ((KtProperty) element).getInitializer();
                    if (initializer == null) {
                        initializer = element;
                    }
                    ktNamedDeclaration = initializer;
                } else if (element instanceof KtParameter) {
                    KtParameter ktParameter = (KtParameter) element;
                    KtExpression defaultValue = ktParameter.getDefaultValue();
                    if (defaultValue != null) {
                        ktNamedDeclaration = defaultValue;
                    } else {
                        KtTypeReference ktTypeReferenceMo7396getTypeReference = ktParameter.mo7396getTypeReference();
                        ktNamedDeclaration = ktTypeReferenceMo7396getTypeReference != null ? ktTypeReferenceMo7396getTypeReference : element;
                    }
                } else {
                    ktNamedDeclaration = element;
                }
                return PositioningStrategyKt.markElement(ktNamedDeclaration);
            }
        };
        WHOLE_ELEMENT = new PositioningStrategy<KtElement>() { // from class: org.jetbrains.kotlin.diagnostics.PositioningStrategies$WHOLE_ELEMENT$1
        };
        TYPE_PARAMETERS_LIST = new PositioningStrategy<KtDeclaration>() { // from class: org.jetbrains.kotlin.diagnostics.PositioningStrategies$TYPE_PARAMETERS_LIST$1
            @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
            public List<TextRange> mark(KtDeclaration element) {
                Intrinsics.checkNotNullParameter(element, "element");
                if (element instanceof KtTypeParameterListOwner) {
                    KtTypeParameterList ktTypeParameterListMo7395getTypeParameterList = ((KtTypeParameterListOwner) element).mo7395getTypeParameterList();
                    return PositioningStrategyKt.markElement(ktTypeParameterListMo7395getTypeParameterList == null ? element : ktTypeParameterListMo7395getTypeParameterList);
                }
                return PositioningStrategyKt.markElement(element);
            }
        };
        ANNOTATION_USE_SITE = new PositioningStrategy<KtAnnotationEntry>() { // from class: org.jetbrains.kotlin.diagnostics.PositioningStrategies$ANNOTATION_USE_SITE$1
            @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
            public List<TextRange> mark(KtAnnotationEntry element) {
                Intrinsics.checkNotNullParameter(element, "element");
                KtAnnotationUseSiteTarget useSiteTarget = element.getUseSiteTarget();
                return PositioningStrategyKt.markElement(useSiteTarget == null ? element : useSiteTarget);
            }
        };
        IMPORT_LAST_NAME = new PositioningStrategy<PsiElement>() { // from class: org.jetbrains.kotlin.diagnostics.PositioningStrategies$IMPORT_LAST_NAME$1
            @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
            public boolean isValid(PsiElement element) {
                Intrinsics.checkNotNullParameter(element, "element");
                boolean z = false;
                if (element instanceof PsiErrorElement) {
                    return false;
                }
                PsiElement[] children = element.getChildren();
                Intrinsics.checkNotNullExpressionValue(children, "getChildren(...)");
                for (PsiElement psiElement : children) {
                    Intrinsics.checkNotNull(psiElement);
                    if (!isValid(psiElement)) {
                        z = true;
                        break;
                    }
                }
                return !z;
            }

            @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
            public List<TextRange> mark(PsiElement element) {
                KtExpression selectorExpression;
                Intrinsics.checkNotNullParameter(element, "element");
                if (element instanceof KtImportDirective) {
                    KtImportDirective ktImportDirective = (KtImportDirective) element;
                    KtExpression importedReference = ktImportDirective.getImportedReference();
                    if ((importedReference instanceof KtDotQualifiedExpression) && (selectorExpression = ((KtDotQualifiedExpression) importedReference).getSelectorExpression()) != null) {
                        return super.mark(selectorExpression);
                    }
                    KtExpression importedReference2 = ktImportDirective.getImportedReference();
                    return super.mark(importedReference2 == null ? (KtElement) element : importedReference2);
                }
                return super.mark(element);
            }
        };
        LABEL = new PositioningStrategy<KtElement>() { // from class: org.jetbrains.kotlin.diagnostics.PositioningStrategies$LABEL$1
            @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
            public List<TextRange> mark(KtElement element) {
                KtContainerNode labelQualifier;
                Intrinsics.checkNotNullParameter(element, "element");
                KtExpressionWithLabel ktExpressionWithLabel = element instanceof KtExpressionWithLabel ? (KtExpressionWithLabel) element : null;
                if (ktExpressionWithLabel != null && (labelQualifier = ktExpressionWithLabel.getLabelQualifier()) != null) {
                    element = labelQualifier;
                }
                return super.mark(element);
            }
        };
        COMMAS = new PositioningStrategy<PsiElement>() { // from class: org.jetbrains.kotlin.diagnostics.PositioningStrategies$COMMAS$1
            @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
            public boolean isValid(PsiElement element) {
                Intrinsics.checkNotNullParameter(element, "element");
                return true;
            }

            @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
            public List<TextRange> mark(PsiElement element) {
                Intrinsics.checkNotNullParameter(element, "element");
                List listCreateListBuilder = CollectionsKt.createListBuilder();
                for (PsiElement psiElement : PsiUtilsKt.getAllChildren(element)) {
                    if (Intrinsics.areEqual(psiElement.getNode().getElementType(), KtTokens.COMMA)) {
                        listCreateListBuilder.add(PositioningStrategyKt.markSingleElement(psiElement));
                    }
                }
                return CollectionsKt.build(listCreateListBuilder);
            }
        };
        KtModifierKeywordToken ABSTRACT_KEYWORD2 = KtTokens.ABSTRACT_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(ABSTRACT_KEYWORD2, "ABSTRACT_KEYWORD");
        KtModifierKeywordToken OPEN_KEYWORD2 = KtTokens.OPEN_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(OPEN_KEYWORD2, "OPEN_KEYWORD");
        KtModifierKeywordToken SEALED_KEYWORD = KtTokens.SEALED_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(SEALED_KEYWORD, "SEALED_KEYWORD");
        NON_FINAL_MODIFIER_OR_NAME = new ModifierSetBasedPositioningStrategy(ABSTRACT_KEYWORD2, OPEN_KEYWORD2, SEALED_KEYWORD);
        DELEGATED_SUPERTYPE_BY_KEYWORD = new PositioningStrategy<KtTypeReference>() { // from class: org.jetbrains.kotlin.diagnostics.PositioningStrategies$DELEGATED_SUPERTYPE_BY_KEYWORD$1
            @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
            public List<TextRange> mark(KtTypeReference element) {
                Intrinsics.checkNotNullParameter(element, "element");
                PsiElement parent = element.getLightOwner();
                KtDelegatedSuperTypeEntry ktDelegatedSuperTypeEntry = parent instanceof KtDelegatedSuperTypeEntry ? (KtDelegatedSuperTypeEntry) parent : null;
                if (ktDelegatedSuperTypeEntry == null) {
                    return super.mark(element);
                }
                KtTypeReference psi = ktDelegatedSuperTypeEntry.getByKeywordNode().getPsi();
                if (psi == null) {
                    psi = element;
                }
                return PositioningStrategyKt.markElement(psi);
            }
        };
        TYPEALIAS_TYPE_REFERENCE = new PositioningStrategy<KtTypeAlias>() { // from class: org.jetbrains.kotlin.diagnostics.PositioningStrategies$TYPEALIAS_TYPE_REFERENCE$1
            @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
            public List<TextRange> mark(KtTypeAlias element) {
                Intrinsics.checkNotNullParameter(element, "element");
                KtTypeReference typeReference = element.getTypeReference();
                return PositioningStrategyKt.markElement(typeReference != null ? typeReference : element);
            }
        };
    }

    public final TokenSet getPropertyKindTokens() {
        return propertyKindTokens;
    }

    public final TokenSet getClassKindTokens() {
        return classKindTokens;
    }

    @JvmStatic
    public static final PositioningStrategy<KtModifierListOwner> modifierSetPosition(final KtModifierKeywordToken... tokens) {
        Intrinsics.checkNotNullParameter(tokens, "tokens");
        return new PositioningStrategy<KtModifierListOwner>() { // from class: org.jetbrains.kotlin.diagnostics.PositioningStrategies.modifierSetPosition.1
            @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
            public List<TextRange> mark(KtModifierListOwner element) {
                Intrinsics.checkNotNullParameter(element, "element");
                KtModifierList modifierList = element.getModifierList();
                if (modifierList == null) {
                    return PositioningStrategies.DEFAULT.mark(element);
                }
                for (KtModifierKeywordToken ktModifierKeywordToken : tokens) {
                    PsiElement modifier = modifierList.getModifier(ktModifierKeywordToken);
                    if (modifier != null) {
                        return PositioningStrategyKt.markElement(modifier);
                    }
                }
                return PositioningStrategies.DEFAULT.mark(element);
            }
        };
    }

    @JvmStatic
    public static final PositioningStrategy<KtModifierListOwner> projectionPosition() {
        return new PositioningStrategy<KtModifierListOwner>() { // from class: org.jetbrains.kotlin.diagnostics.PositioningStrategies.projectionPosition.1
            @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
            public List<TextRange> mark(KtModifierListOwner element) {
                Intrinsics.checkNotNullParameter(element, "element");
                if ((element instanceof KtTypeProjection) && ((KtTypeProjection) element).getProjectionKind() == KtProjectionKind.STAR) {
                    return PositioningStrategyKt.markElement(element);
                }
                KtModifierList modifierList = element.getModifierList();
                if (modifierList != null) {
                    Intrinsics.checkNotNullExpressionValue(modifierList, "sure(...)");
                    PsiElement modifier = modifierList.getModifier(KtTokens.IN_KEYWORD);
                    if (modifier != null) {
                        return PositioningStrategyKt.markElement(modifier);
                    }
                    PsiElement modifier2 = modifierList.getModifier(KtTokens.OUT_KEYWORD);
                    if (modifier2 != null) {
                        return PositioningStrategyKt.markElement(modifier2);
                    }
                    throw new IllegalStateException("None of the modifiers is found: in, out");
                }
                throw new AssertionError("No modifier list, but modifier has been found by the analyzer");
            }
        };
    }

    /* JADX INFO: compiled from: PositioningStrategies.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0012\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u001b\b\u0016\u0012\u0012\u0010\u0003\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00050\u0004\"\u00020\u0005¢\u0006\u0002\u0010\u0006B\r\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u0016\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\u0006\u0010\r\u001a\u00020\u0002H\u0016J\u001a\u0010\u000e\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u000b2\b\u0010\r\u001a\u0004\u0018\u00010\u0002H\u0004R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/PositioningStrategies$ModifierSetBasedPositioningStrategy;", "Lorg/jetbrains/kotlin/diagnostics/PositioningStrategy;", "Lorg/jetbrains/kotlin/psi/KtModifierListOwner;", "tokens", "", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/IElementType;", "([Lcom/intellij/psi/tree/IElementType;)V", "modifierSet", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/TokenSet;", "(Lcom/intellij/psi/tree/TokenSet;)V", "mark", "", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/TextRange;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "markModifier", "frontend.common-psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static class ModifierSetBasedPositioningStrategy extends PositioningStrategy<KtModifierListOwner> {
        private final TokenSet modifierSet;

        public ModifierSetBasedPositioningStrategy(TokenSet modifierSet) {
            Intrinsics.checkNotNullParameter(modifierSet, "modifierSet");
            this.modifierSet = modifierSet;
        }

        /* JADX WARN: Illegal instructions before constructor call */
        public ModifierSetBasedPositioningStrategy(IElementType... tokens) {
            Intrinsics.checkNotNullParameter(tokens, "tokens");
            TokenSet tokenSetCreate = TokenSet.create((IElementType[]) Arrays.copyOf(tokens, tokens.length));
            Intrinsics.checkNotNullExpressionValue(tokenSetCreate, "create(...)");
            this(tokenSetCreate);
        }

        protected final List<TextRange> markModifier(KtModifierListOwner element) {
            KtModifierList modifierList;
            IElementType[] types = this.modifierSet.getTypes();
            Intrinsics.checkNotNullExpressionValue(types, "getTypes(...)");
            IElementType[] iElementTypeArr = types;
            ArrayList arrayList = new ArrayList();
            int length = iElementTypeArr.length;
            int i = 0;
            while (true) {
                TextRange textRange = null;
                if (i >= length) {
                    break;
                }
                IElementType iElementType = iElementTypeArr[i];
                if (element != null && (modifierList = element.getModifierList()) != null) {
                    Intrinsics.checkNotNull(iElementType, "null cannot be cast to non-null type org.jetbrains.kotlin.lexer.KtModifierKeywordToken");
                    PsiElement modifier = modifierList.getModifier((KtModifierKeywordToken) iElementType);
                    if (modifier != null) {
                        textRange = modifier.getTextRange();
                    }
                }
                if (textRange != null) {
                    arrayList.add(textRange);
                }
                i++;
            }
            ArrayList arrayList2 = arrayList;
            if (arrayList2.isEmpty()) {
                return null;
            }
            return arrayList2;
        }

        @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
        public List<TextRange> mark(KtModifierListOwner element) {
            KtModifierListOwner namePlaceholder;
            PsiElement psiElementMo7392getNameIdentifier;
            Intrinsics.checkNotNullParameter(element, "element");
            List<TextRange> listMarkModifier = markModifier(element);
            if (listMarkModifier != null) {
                return listMarkModifier;
            }
            if ((element instanceof PsiNameIdentifierOwner) && (psiElementMo7392getNameIdentifier = ((PsiNameIdentifierOwner) element).mo7392getNameIdentifier()) != null) {
                return PositioningStrategyKt.markElement(psiElementMo7392getNameIdentifier);
            }
            if (element instanceof KtObjectDeclaration) {
                namePlaceholder = ((KtObjectDeclaration) element).getObjectKeyword();
                Intrinsics.checkNotNull(namePlaceholder);
            } else if (element instanceof KtPropertyAccessor) {
                namePlaceholder = ((KtPropertyAccessor) element).getNamePlaceholder();
                Intrinsics.checkNotNullExpressionValue(namePlaceholder, "getNamePlaceholder(...)");
            } else {
                if (!(element instanceof KtAnonymousInitializer)) {
                    throw new IllegalArgumentException("Can't find text range for element '" + element.getClass().getCanonicalName() + "' with the text '" + element.getText() + '\'');
                }
                namePlaceholder = element;
            }
            return PositioningStrategyKt.markElement(namePlaceholder);
        }
    }

    /* JADX INFO: compiled from: PositioningStrategies.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0016¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/PositioningStrategies$InlineFunPositioningStrategy;", "Lorg/jetbrains/kotlin/diagnostics/PositioningStrategies$ModifierSetBasedPositioningStrategy;", "()V", "mark", "", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/TextRange;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/psi/KtModifierListOwner;", "frontend.common-psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class InlineFunPositioningStrategy extends ModifierSetBasedPositioningStrategy {
        /* JADX WARN: Illegal instructions before constructor call */
        public InlineFunPositioningStrategy() {
            KtModifierKeywordToken INLINE_KEYWORD = KtTokens.INLINE_KEYWORD;
            Intrinsics.checkNotNullExpressionValue(INLINE_KEYWORD, "INLINE_KEYWORD");
            super(INLINE_KEYWORD);
        }

        @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategies.ModifierSetBasedPositioningStrategy, org.jetbrains.kotlin.diagnostics.PositioningStrategy
        public List<TextRange> mark(KtModifierListOwner element) {
            Intrinsics.checkNotNullParameter(element, "element");
            if (element instanceof KtProperty) {
                KtProperty ktProperty = (KtProperty) element;
                List<TextRange> listMarkModifier = markModifier(ktProperty.getGetter());
                if (listMarkModifier != null) {
                    return listMarkModifier;
                }
                List<TextRange> listMarkModifier2 = markModifier(ktProperty.getSetter());
                return listMarkModifier2 == null ? super.mark(element) : listMarkModifier2;
            }
            return super.mark(element);
        }
    }

    public final PositioningStrategy<KtExpression> getOPERATOR() {
        return OPERATOR;
    }

    public final PositioningStrategy<PsiElement> getDOT_BY_QUALIFIED() {
        return DOT_BY_QUALIFIED;
    }

    public final PositioningStrategy<PsiElement> getSELECTOR_BY_QUALIFIED() {
        return SELECTOR_BY_QUALIFIED;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final KtElement getReferencedTypeExpression(KtTypeElement ktTypeElement) {
        KtTypeElement innerType;
        if (ktTypeElement instanceof KtUserType) {
            return ((KtUserType) ktTypeElement).getReferenceExpression();
        }
        if (!(ktTypeElement instanceof KtNullableType) || (innerType = ((KtNullableType) ktTypeElement).getInnerType()) == null) {
            return null;
        }
        return getReferencedTypeExpression(innerType);
    }

    public final PositioningStrategy<PsiElement> getNAME_IDENTIFIER() {
        return NAME_IDENTIFIER;
    }

    public final PositioningStrategy<PsiElement> getSPREAD_OPERATOR() {
        return SPREAD_OPERATOR;
    }

    public final PositioningStrategy<PsiElement> getREFERENCE_BY_QUALIFIED() {
        return REFERENCE_BY_QUALIFIED;
    }

    public final PositioningStrategy<PsiElement> getREFERENCED_NAME_BY_QUALIFIED() {
        return REFERENCED_NAME_BY_QUALIFIED;
    }

    public final PositioningStrategy<KtModifierListOwner> getREIFIED_MODIFIER() {
        return REIFIED_MODIFIER;
    }

    public final PositioningStrategy<KtNamedDeclaration> getPROPERTY_INITIALIZER() {
        return PROPERTY_INITIALIZER;
    }

    public final PositioningStrategy<KtElement> getWHOLE_ELEMENT() {
        return WHOLE_ELEMENT;
    }

    public final PositioningStrategy<KtDeclaration> getTYPE_PARAMETERS_LIST() {
        return TYPE_PARAMETERS_LIST;
    }

    public final PositioningStrategy<KtAnnotationEntry> getANNOTATION_USE_SITE() {
        return ANNOTATION_USE_SITE;
    }

    public final PositioningStrategy<PsiElement> getIMPORT_LAST_NAME() {
        return IMPORT_LAST_NAME;
    }

    public final PositioningStrategy<KtElement> getLABEL() {
        return LABEL;
    }

    public final PositioningStrategy<PsiElement> getCOMMAS() {
        return COMMAS;
    }

    public final PositioningStrategy<KtModifierListOwner> getNON_FINAL_MODIFIER_OR_NAME() {
        return NON_FINAL_MODIFIER_OR_NAME;
    }

    public final PositioningStrategy<KtTypeReference> getDELEGATED_SUPERTYPE_BY_KEYWORD() {
        return DELEGATED_SUPERTYPE_BY_KEYWORD;
    }

    /* JADX INFO: compiled from: PositioningStrategies.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0016\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t2\u0006\u0010\u000b\u001a\u00020\u0002H\u0016R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/PositioningStrategies$FindReferencePositioningStrategy;", "Lorg/jetbrains/kotlin/diagnostics/PositioningStrategy;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "locateReferencedName", "", "(Z)V", "getLocateReferencedName", "()Z", "mark", "", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/TextRange;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "frontend.common-psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class FindReferencePositioningStrategy extends PositioningStrategy<PsiElement> {
        private final boolean locateReferencedName;

        public FindReferencePositioningStrategy(boolean z) {
            this.locateReferencedName = z;
        }

        public final boolean getLocateReferencedName() {
            return this.locateReferencedName;
        }

        @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
        public List<TextRange> mark(PsiElement element) {
            KtExpression expression;
            KtExpression left;
            Intrinsics.checkNotNullParameter(element, "element");
            if (element instanceof KtBinaryExpression) {
                KtBinaryExpression ktBinaryExpression = (KtBinaryExpression) element;
                if (Intrinsics.areEqual(ktBinaryExpression.getOperationToken(), KtTokens.EQ) && (left = ktBinaryExpression.getLeft()) != null) {
                    return mark(left);
                }
            }
            if (element instanceof KtQualifiedExpression) {
                KtExpression selectorExpression = ((KtQualifiedExpression) element).getSelectorExpression();
                if (selectorExpression instanceof KtCallExpression) {
                    KtExpression calleeExpression = ((KtCallExpression) selectorExpression).getCalleeExpression();
                    if (calleeExpression != null) {
                        selectorExpression = calleeExpression;
                    }
                } else if (!(selectorExpression instanceof KtReferenceExpression)) {
                    selectorExpression = (KtExpression) element;
                }
                element = selectorExpression;
            } else if (element instanceof KtCallableReferenceExpression) {
                KtSimpleNameExpression callableReference = ((KtCallableReferenceExpression) element).getCallableReference();
                Intrinsics.checkNotNullExpressionValue(callableReference, "getCallableReference(...)");
                element = callableReference;
            } else if (element instanceof KtCallExpression) {
                KtExpression calleeExpression2 = ((KtCallExpression) element).getCalleeExpression();
                if (calleeExpression2 == null) {
                    calleeExpression2 = (KtExpression) element;
                }
                element = calleeExpression2;
            } else if (element instanceof KtConstructorDelegationCall) {
                KtConstructorDelegationReferenceExpression calleeExpression3 = ((KtConstructorDelegationCall) element).getCalleeExpression();
                element = calleeExpression3 == null ? (KtElementImpl) element : calleeExpression3;
            } else if (element instanceof KtSuperTypeCallEntry) {
                KtConstructorCalleeExpression calleeExpression4 = ((KtSuperTypeCallEntry) element).getCalleeExpression();
                Intrinsics.checkNotNullExpressionValue(calleeExpression4, "getCalleeExpression(...)");
                element = calleeExpression4;
            } else if (element instanceof KtOperationExpression) {
                KtSimpleNameExpression operationReference = ((KtOperationExpression) element).getOperationReference();
                Intrinsics.checkNotNullExpressionValue(operationReference, "getOperationReference(...)");
                element = operationReference;
            } else if (element instanceof KtWhenConditionInRange) {
                KtOperationReferenceExpression operationReference2 = ((KtWhenConditionInRange) element).getOperationReference();
                Intrinsics.checkNotNullExpressionValue(operationReference2, "getOperationReference(...)");
                element = operationReference2;
            } else if (element instanceof KtAnnotationEntry) {
                KtConstructorCalleeExpression calleeExpression5 = ((KtAnnotationEntry) element).getCalleeExpression();
                element = calleeExpression5 == null ? (KtElementImplStub) element : calleeExpression5;
            } else if (element instanceof KtTypeReference) {
                KtTypeElement typeElement = ((KtTypeReference) element).getTypeElement();
                KtNullableType ktNullableType = typeElement instanceof KtNullableType ? (KtNullableType) typeElement : null;
                KtTypeElement innerType = ktNullableType != null ? ktNullableType.getInnerType() : null;
                element = innerType == null ? (KtElement) element : innerType;
            } else if (element instanceof KtImportDirective) {
                KtExpression importedReference = ((KtImportDirective) element).getImportedReference();
                element = importedReference == null ? (KtElement) element : importedReference;
            }
            while (this.locateReferencedName && (element instanceof KtParenthesizedExpression) && (expression = ((KtParenthesizedExpression) element).getExpression()) != null) {
                element = expression;
            }
            return super.mark(element);
        }
    }
}
