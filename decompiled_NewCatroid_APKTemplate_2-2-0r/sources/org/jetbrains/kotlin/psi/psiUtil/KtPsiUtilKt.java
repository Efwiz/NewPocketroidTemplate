package org.jetbrains.kotlin.psi.psiUtil;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.Regex;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.google.common.collect.ImmutableSet;
import org.jetbrains.kotlin.com.intellij.extapi.psi.StubBasedPsiElementBase;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiComment;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiWhiteSpace;
import org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.lexer.KotlinLexer;
import org.jetbrains.kotlin.lexer.KtModifierKeywordToken;
import org.jetbrains.kotlin.lexer.KtSingleValueToken;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.psi.KtAnnotatedExpression;
import org.jetbrains.kotlin.psi.KtAnnotation;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtAnnotationsContainer;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.psi.KtBinaryExpressionWithTypeRHS;
import org.jetbrains.kotlin.psi.KtBlockExpression;
import org.jetbrains.kotlin.psi.KtCallElement;
import org.jetbrains.kotlin.psi.KtCallExpression;
import org.jetbrains.kotlin.psi.KtCallableDeclaration;
import org.jetbrains.kotlin.psi.KtCallableReferenceExpression;
import org.jetbrains.kotlin.psi.KtClass;
import org.jetbrains.kotlin.psi.KtClassBody;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtConstructorCalleeExpression;
import org.jetbrains.kotlin.psi.KtContextReceiver;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtDotQualifiedExpression;
import org.jetbrains.kotlin.psi.KtDoubleColonExpression;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtElementImplStub;
import org.jetbrains.kotlin.psi.KtEscapeStringTemplateEntry;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtFunction;
import org.jetbrains.kotlin.psi.KtFunctionLiteral;
import org.jetbrains.kotlin.psi.KtImportDirective;
import org.jetbrains.kotlin.psi.KtLabeledExpression;
import org.jetbrains.kotlin.psi.KtLambdaArgument;
import org.jetbrains.kotlin.psi.KtLambdaExpression;
import org.jetbrains.kotlin.psi.KtLiteralStringTemplateEntry;
import org.jetbrains.kotlin.psi.KtModifierList;
import org.jetbrains.kotlin.psi.KtModifierListOwner;
import org.jetbrains.kotlin.psi.KtNameReferenceExpression;
import org.jetbrains.kotlin.psi.KtNamedDeclaration;
import org.jetbrains.kotlin.psi.KtNamedDeclarationUtil;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtNullableType;
import org.jetbrains.kotlin.psi.KtObjectDeclaration;
import org.jetbrains.kotlin.psi.KtPackageDirective;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtParameterList;
import org.jetbrains.kotlin.psi.KtParenthesizedExpression;
import org.jetbrains.kotlin.psi.KtPrimaryConstructor;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtPropertyAccessor;
import org.jetbrains.kotlin.psi.KtPsiFactory;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.psi.KtQualifiedExpression;
import org.jetbrains.kotlin.psi.KtReferenceExpression;
import org.jetbrains.kotlin.psi.KtScript;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.KtStringTemplateEntry;
import org.jetbrains.kotlin.psi.KtStringTemplateExpression;
import org.jetbrains.kotlin.psi.KtSuperTypeListEntry;
import org.jetbrains.kotlin.psi.KtTreeVisitorVoid;
import org.jetbrains.kotlin.psi.KtTypeArgumentList;
import org.jetbrains.kotlin.psi.KtTypeElement;
import org.jetbrains.kotlin.psi.KtTypeProjection;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.psi.KtUnaryExpression;
import org.jetbrains.kotlin.psi.KtUserType;
import org.jetbrains.kotlin.psi.KtValueArgument;
import org.jetbrains.kotlin.psi.KtValueArgumentList;
import org.jetbrains.kotlin.psi.KtVisitorVoid;
import org.jetbrains.kotlin.psi.UserDataProperty;
import org.jetbrains.kotlin.psi.stubs.KotlinClassOrObjectStub;
import org.jetbrains.kotlin.types.expressions.OperatorConventions;

/* JADX INFO: compiled from: ktPsiUtil.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000Ô\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0016\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a\u0010\u0010%\u001a\u00020\r2\b\u0010&\u001a\u0004\u0018\u00010\u0017\u001a\u0015\u0010'\u001a\u0004\u0018\u00010(2\b\u0010&\u001a\u0004\u0018\u00010\u0017H\u0086\u0010\u001aH\u0010)\u001a\u00020*\"\n\b\u0000\u0010+\u0018\u0001*\u00020\u0019\"\u0004\b\u0001\u0010,2\f\u0010-\u001a\b\u0012\u0004\u0012\u0002H,0.2\u001a\b\b\u0010/\u001a\u0014\u0012\u0004\u0012\u0002H+\u0012\n\u0012\b\u0012\u0004\u0012\u0002H,0100H\u0086\bø\u0001\u0000\u001a.\u00102\u001a\u00020*\"\n\b\u0000\u0010+\u0018\u0001*\u00020\u00192\u0014\b\b\u00103\u001a\u000e\u0012\u0004\u0012\u0002H+\u0012\u0004\u0012\u00020400H\u0086\bø\u0001\u0000\u001a\u0012\u00105\u001a\u0004\u0018\u00010\u00172\b\u00106\u001a\u0004\u0018\u00010\u0017\u001a\u0012\u00107\u001a\u0004\u0018\u00010\u00172\b\u00108\u001a\u0004\u0018\u00010\u0017\u001a\u000e\u00109\u001a\u00020\r2\u0006\u0010:\u001a\u00020;\u001a\u000e\u0010<\u001a\u00020\r2\u0006\u0010&\u001a\u00020\u0017\u001a\u000e\u0010=\u001a\u00020\r2\u0006\u0010>\u001a\u00020\u0017\u001a\u0012\u0010?\u001a\u000204*\u00020@2\u0006\u0010A\u001a\u00020B\u001a\f\u0010C\u001a\u0004\u0018\u00010(*\u00020;\u001a\u0012\u0010D\u001a\u000204*\u00020\u00172\u0006\u0010E\u001a\u00020\u0017\u001a\u0010\u0010F\u001a\b\u0012\u0004\u0012\u00020\u00190G*\u00020\u0019\u001a\u0012\u0010H\u001a\b\u0012\u0004\u0012\u00020J0I*\u00020KH\u0002\u001a\u0016\u0010L\u001a\b\u0012\u0004\u0012\u00020J0I*\u0006\u0012\u0002\b\u00030MH\u0002\u001a\u0010\u0010N\u001a\b\u0012\u0004\u0012\u00020J0I*\u00020K\u001a\f\u0010O\u001a\u0004\u0018\u00010P*\u00020\u0019\u001a\n\u0010Q\u001a\u00020R*\u00020S\u001a\u0010\u0010T\u001a\b\u0012\u0004\u0012\u00020\t0I*\u00020\b\u001a\u0014\u0010U\u001a\u0004\u0018\u00010\u000e*\u00020\b2\u0006\u0010V\u001a\u00020!\u001a\u001a\u0010W\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010Y\u0012\u0006\u0012\u0004\u0018\u00010@0X*\u00020Z\u001a\u0014\u0010[\u001a\u0004\u0018\u00010\u000e*\u00020\b2\u0006\u0010V\u001a\u00020!\u001a\u0010\u0010\\\u001a\b\u0012\u0004\u0012\u00020J0I*\u00020;\u001a\f\u0010]\u001a\u0004\u0018\u00010(*\u00020;\u001a\f\u0010^\u001a\u0004\u0018\u00010_*\u00020;\u001a\f\u0010`\u001a\u0004\u0018\u00010a*\u00020b\u001a\n\u0010c\u001a\u00020d*\u00020\"\u001a\u0018\u0010e\u001a\u0010\u0012\f\u0012\n f*\u0004\u0018\u00010\t0\t0I*\u00020g\u001a\u0014\u0010h\u001a\u0004\u0018\u00010i*\u00020;2\u0006\u0010j\u001a\u00020!\u001a\n\u0010k\u001a\u00020l*\u00020Z\u001a\n\u0010m\u001a\u00020n*\u00020@\u001a\n\u0010o\u001a\u00020;*\u00020;\u001a\f\u0010p\u001a\u0004\u0018\u00010@*\u00020;\u001a\n\u0010q\u001a\u00020\u0019*\u00020a\u001a\n\u0010r\u001a\u00020\u0019*\u00020a\u001a\f\u0010s\u001a\u0004\u0018\u00010\u0019*\u00020\u0019\u001a\f\u0010t\u001a\u0004\u0018\u00010u*\u00020;\u001a\n\u0010v\u001a\u00020;*\u00020;\u001a\f\u0010w\u001a\u0004\u0018\u00010u*\u00020\u0019\u001a\n\u0010x\u001a\u00020;*\u00020;\u001a\f\u0010y\u001a\u0004\u0018\u00010;*\u00020a\u001a \u0010z\u001a\b\u0012\u0004\u0012\u00020!0I*\u0012\u0012\u000e\b\u0001\u0012\n\u0012\u0006\b\u0001\u0012\u00020\b0|0{\u001a\f\u0010}\u001a\u0004\u0018\u00010u*\u00020a\u001a\f\u0010~\u001a\u0004\u0018\u00010l*\u00020\u000e\u001a\u0011\u0010\u007f\u001a\t\u0012\u0005\u0012\u00030\u0080\u00010I*\u00020\u000e\u001a\u000b\u0010\u0081\u0001\u001a\u00020\r*\u00020\t\u001a\u000b\u0010\u0082\u0001\u001a\u00020\r*\u00020P\u001a\u000b\u0010\u0083\u0001\u001a\u00020\r*\u00020a\u001a\u000b\u0010\u0084\u0001\u001a\u00020\r*\u00020\u0019\u001a\u000b\u0010\u0085\u0001\u001a\u00020\r*\u00020;\u001a\u000b\u0010\u0085\u0001\u001a\u00020\r*\u00020u\u001a\u0015\u0010\u0086\u0001\u001a\u00020\r*\u00030\u0087\u00012\u0007\u0010\u0088\u0001\u001a\u00020\r\u001a\u000b\u0010\u0089\u0001\u001a\u00020\r*\u00020;\u001a\u000b\u0010\u008a\u0001\u001a\u00020\r*\u00020;\u001a\u000b\u0010\u008b\u0001\u001a\u00020\r*\u00020\u0017\u001a\u000b\u0010\u008c\u0001\u001a\u00020\r*\u00020\u0019\u001a\u000b\u0010\u008d\u0001\u001a\u00020\r*\u00020\u0017\u001a\r\u0010\u008e\u0001\u001a\u00020\r*\u0004\u0018\u00010!\u001a\u000b\u0010\u008f\u0001\u001a\u00020\r*\u00020a\u001a\u000b\u0010\u0090\u0001\u001a\u00020\r*\u00020;\u001a\u000b\u0010\u0091\u0001\u001a\u00020\r*\u00020;\u001a\u000b\u0010\u0092\u0001\u001a\u00020\r*\u00020\b\u001a\u000b\u0010\u0093\u0001\u001a\u00020\r*\u00020a\u001a\u000b\u0010\u0094\u0001\u001a\u00020\r*\u00020\"\u001a\u000b\u0010\u0095\u0001\u001a\u00020\r*\u00020\"\u001a\u000b\u0010\u0096\u0001\u001a\u00020\r*\u00020\u0011\u001a\f\u0010\u0097\u0001\u001a\u00020\r*\u00030\u0080\u0001\u001a\u000b\u0010\u0098\u0001\u001a\u00020\r*\u00020\u0011\u001a\u000b\u0010\u0099\u0001\u001a\u00020\r*\u00020\"\u001a\u000b\u0010\u009a\u0001\u001a\u00020\r*\u00020\u0017\u001a\u000b\u0010\u009b\u0001\u001a\u00020;*\u00020;\u001a\r\u0010\u009c\u0001\u001a\u0004\u0018\u00010\u0017*\u00020\t\u001a\u000e\u0010\u009d\u0001\u001a\u0005\u0018\u00010\u009e\u0001*\u00020\t\u001a\u001a\u0010\u009f\u0001\u001a\u0004\u0018\u00010\u0017*\u00030 \u00012\b\u0010¡\u0001\u001a\u00030¢\u0001H\u0002\u001a\u0019\u0010\u009f\u0001\u001a\u0004\u0018\u00010\u0017*\u00020\u00112\b\u0010¡\u0001\u001a\u00030¢\u0001H\u0002\u001a\u0011\u0010£\u0001\u001a\b\u0012\u0004\u0012\u00020P0G*\u00020\u0019\u001a\u000b\u0010¤\u0001\u001a\u00020\u0003*\u00020\u0017\u001a\u000b\u0010¥\u0001\u001a\u00020!*\u00020!\u001a\u000e\u0010¦\u0001\u001a\u0005\u0018\u00010§\u0001*\u00020;\u001a\u000e\u0010¨\u0001\u001a\u0005\u0018\u00010©\u0001*\u00020\u000e\u001a\r\u0010ª\u0001\u001a\u00020Y*\u0004\u0018\u00010Y\u001a\u000b\u0010ª\u0001\u001a\u00020Y*\u00020\u000e\u001a\u000b\u0010«\u0001\u001a\u00020;*\u00020;\u001a\u0012\u0010¬\u0001\u001a\u0005\u0018\u00010\u00ad\u0001*\u00030\u00ad\u0001H\u0086\u0010\u001a\u000e\u0010®\u0001\u001a\u0004\u0018\u00010\u0017*\u00030 \u0001\u001a\r\u0010®\u0001\u001a\u0004\u0018\u00010\u0017*\u00020\u0011\u001a\u000f\u0010¯\u0001\u001a\u0005\u0018\u00010\u009e\u0001*\u00030 \u0001\u001a\u000e\u0010¯\u0001\u001a\u0005\u0018\u00010\u009e\u0001*\u00020\u0011\u001a\f\u0010°\u0001\u001a\u00030\u009e\u0001*\u00020\u0011\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u0015\u0010\u0002\u001a\u00020\u0003*\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006\"\u0017\u0010\u0007\u001a\u0004\u0018\u00010\b*\u00020\t8F¢\u0006\u0006\u001a\u0004\b\n\u0010\u000b\"\u0015\u0010\f\u001a\u00020\r*\u00020\u000e8F¢\u0006\u0006\u001a\u0004\b\f\u0010\u000f\"\u0015\u0010\u0010\u001a\u00020\r*\u00020\u00118F¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0012\"\u0015\u0010\u0013\u001a\u00020\r*\u00020\u00148F¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0015\"3\u0010\u0018\u001a\u0004\u0018\u00010\u0017*\u00020\u00192\b\u0010\u0016\u001a\u0004\u0018\u00010\u00178F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b\u001e\u0010\u001f\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001d\"\u0015\u0010 \u001a\u00020!*\u00020\"8F¢\u0006\u0006\u001a\u0004\b#\u0010$\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006±\u0001"}, d2 = {"BAD_NEIGHBOUR_FOR_SIMPLE_TEMPLATE_ENTRY_PATTERN", "Lkotlin/text/Regex;", "argumentIndex", "", "Lorg/jetbrains/kotlin/psi/KtValueArgument;", "getArgumentIndex", "(Lorg/jetbrains/kotlin/psi/KtValueArgument;)I", "containingClassOrObject", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "getContainingClassOrObject", "(Lorg/jetbrains/kotlin/psi/KtDeclaration;)Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "isPrivateNestedClassOrObject", "", "Lorg/jetbrains/kotlin/psi/KtNamedDeclaration;", "(Lorg/jetbrains/kotlin/psi/KtNamedDeclaration;)Z", "isPublic", "Lorg/jetbrains/kotlin/psi/KtModifierListOwner;", "(Lorg/jetbrains/kotlin/psi/KtModifierListOwner;)Z", "isUnderscoreInBackticks", "Lorg/jetbrains/kotlin/psi/KtNameReferenceExpression;", "(Lorg/jetbrains/kotlin/psi/KtNameReferenceExpression;)Z", "<set-?>", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "parentSubstitute", "Lorg/jetbrains/kotlin/psi/KtElement;", "getParentSubstitute", "(Lorg/jetbrains/kotlin/psi/KtElement;)Lcom/intellij/psi/PsiElement;", "setParentSubstitute", "(Lorg/jetbrains/kotlin/psi/KtElement;Lcom/intellij/psi/PsiElement;)V", "parentSubstitute$delegate", "Lorg/jetbrains/kotlin/psi/UserDataProperty;", "plainContent", "", "Lorg/jetbrains/kotlin/psi/KtStringTemplateExpression;", "getPlainContent", "(Lorg/jetbrains/kotlin/psi/KtStringTemplateExpression;)Ljava/lang/String;", "canPlaceAfterSimpleNameEntry", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "findAssignment", "Lorg/jetbrains/kotlin/psi/KtBinaryExpression;", "flatMapDescendantsOfTypeVisitor", "Lorg/jetbrains/kotlin/psi/KtVisitorVoid;", ExifInterface.GPS_DIRECTION_TRUE, "R", "accumulator", "", "map", "Lkotlin/Function1;", "", "forEachDescendantOfTypeVisitor", "block", "", "getTrailingCommaByClosingElement", "closingElement", "getTrailingCommaByElementsList", "elementList", "isDoubleColonReceiver", "expression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "isTopLevelInFileOrScript", "isTypeConstructorReference", "e", "addTypeArgument", "Lorg/jetbrains/kotlin/psi/KtCallExpression;", "typeArgument", "Lorg/jetbrains/kotlin/psi/KtTypeProjection;", "asAssignment", "astReplace", "newElement", "blockExpressionsOrSingle", "Lkotlin/sequences/Sequence;", "collectAnnotationEntriesFromPsi", "", "Lorg/jetbrains/kotlin/psi/KtAnnotationEntry;", "Lorg/jetbrains/kotlin/psi/KtAnnotationsContainer;", "collectAnnotationEntriesFromStubElement", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubElement;", "collectAnnotationEntriesFromStubOrPsi", "containingClass", "Lorg/jetbrains/kotlin/psi/KtClass;", "contentRange", "Lorg/jetbrains/kotlin/psi/psiUtil/PsiChildRange;", "Lorg/jetbrains/kotlin/psi/KtBlockExpression;", "effectiveDeclarations", "findFunctionByName", "name", "findLabelAndCall", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/name/Name;", "Lorg/jetbrains/kotlin/psi/KtFunctionLiteral;", "findPropertyByName", "getAnnotationEntries", "getAssignmentByLHS", "getBinaryWithTypeParent", "Lorg/jetbrains/kotlin/psi/KtBinaryExpressionWithTypeRHS;", "getCallNameExpression", "Lorg/jetbrains/kotlin/psi/KtSimpleNameExpression;", "Lorg/jetbrains/kotlin/psi/KtCallElement;", "getContentRange", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/TextRange;", "getFileOrScriptDeclarations", JvmProtoBufUtil.PLATFORM_TYPE_ID, "Lorg/jetbrains/kotlin/psi/KtFile;", "getLabeledParent", "Lorg/jetbrains/kotlin/psi/KtLabeledExpression;", "labelName", "getOrCreateParameterList", "Lorg/jetbrains/kotlin/psi/KtParameterList;", "getOrCreateValueArgumentList", "Lorg/jetbrains/kotlin/psi/KtValueArgumentList;", "getOutermostParenthesizerOrThis", "getPossiblyQualifiedCallExpression", "getQualifiedElement", "getQualifiedElementOrCallableRef", "getQualifiedElementSelector", "getQualifiedExpressionForReceiver", "Lorg/jetbrains/kotlin/psi/KtQualifiedExpression;", "getQualifiedExpressionForReceiverOrThis", "getQualifiedExpressionForSelector", "getQualifiedExpressionForSelectorOrThis", "getReceiverExpression", "getSuperNames", "Lorg/jetbrains/kotlin/com/intellij/extapi/psi/StubBasedPsiElementBase;", "Lorg/jetbrains/kotlin/psi/stubs/KotlinClassOrObjectStub;", "getTopmostParentQualifiedExpressionForSelector", "getValueParameterList", "getValueParameters", "Lorg/jetbrains/kotlin/psi/KtParameter;", "hasBody", "isAbstract", "isCallee", "isContextualDeclaration", "isContractDescriptionCallPsiCheck", "isContractPresentPsiCheck", "Lorg/jetbrains/kotlin/psi/KtNamedFunction;", "isAllowedOnMembers", "isDotReceiver", "isDotSelector", "isExtensionDeclaration", "isFirstStatement", "isFunctionalExpression", "isIdentifier", "isImportDirectiveExpression", "isInImportDirective", "isLambdaOutsideParentheses", "isObjectLiteral", "isPackageDirectiveExpression", "isPlain", "isPlainWithEscapes", "isPrivate", "isPropertyParameter", "isProtected", "isSingleQuoted", "isTopLevelKtOrJavaMember", "lastBlockStatementOrThis", "modalityModifier", "modalityModifierType", "Lorg/jetbrains/kotlin/lexer/KtModifierKeywordToken;", "modifierFromTokenSet", "Lorg/jetbrains/kotlin/psi/KtModifierList;", FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX, "Lorg/jetbrains/kotlin/com/intellij/psi/tree/TokenSet;", "nonStaticOuterClasses", "parameterIndex", "quoteIfNeeded", "referenceExpression", "Lorg/jetbrains/kotlin/psi/KtReferenceExpression;", "safeFqNameForLazyResolve", "Lorg/jetbrains/kotlin/name/FqName;", "safeNameForLazyResolve", "topParenthesizedParentOrMe", "unwrapNullability", "Lorg/jetbrains/kotlin/psi/KtTypeElement;", "visibilityModifier", "visibilityModifierType", "visibilityModifierTypeOrDefault", "psi"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class KtPsiUtilKt {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(KtPsiUtilKt.class, "psi"), "parentSubstitute", "getParentSubstitute(Lorg/jetbrains/kotlin/psi/KtElement;)Lcom/intellij/psi/PsiElement;"))};
    private static final Regex BAD_NEIGHBOUR_FOR_SIMPLE_TEMPLATE_ENTRY_PATTERN = new Regex("([a-zA-Z0-9_]|[^\\p{ASCII}]).*");
    private static final UserDataProperty parentSubstitute$delegate;

    public static final KtSimpleNameExpression getCallNameExpression(KtCallElement ktCallElement) {
        Intrinsics.checkNotNullParameter(ktCallElement, "<this>");
        KtExpression calleeExpression = ktCallElement.getCalleeExpression();
        if (calleeExpression == null) {
            return null;
        }
        if (calleeExpression instanceof KtSimpleNameExpression) {
            return (KtSimpleNameExpression) calleeExpression;
        }
        if (calleeExpression instanceof KtConstructorCalleeExpression) {
            return ((KtConstructorCalleeExpression) calleeExpression).getConstructorReferenceExpression();
        }
        return null;
    }

    public static final KtElement getQualifiedElement(KtSimpleNameExpression ktSimpleNameExpression) {
        KtExpression ktExpression;
        Intrinsics.checkNotNullParameter(ktSimpleNameExpression, "<this>");
        PsiElement parent = ktSimpleNameExpression.getLightOwner();
        KtCallExpression ktCallExpression = parent instanceof KtCallExpression ? (KtCallExpression) parent : null;
        KtSimpleNameExpression ktSimpleNameExpression2 = ktCallExpression != null ? ktCallExpression : ktSimpleNameExpression;
        PsiElement parent2 = ktSimpleNameExpression2.getLightOwner();
        if (parent2 instanceof KtQualifiedExpression) {
            if (Intrinsics.areEqual(((KtQualifiedExpression) parent2).getSelectorExpression(), ktSimpleNameExpression2)) {
                Intrinsics.checkNotNull(parent2);
                ktExpression = (KtExpression) parent2;
            } else {
                ktExpression = ktSimpleNameExpression2;
            }
            return ktExpression;
        }
        if (!(parent2 instanceof KtUserType)) {
            return ktSimpleNameExpression2;
        }
        if (!Intrinsics.areEqual(((KtUserType) parent2).getReferenceExpression(), ktSimpleNameExpression2)) {
            return ktSimpleNameExpression2;
        }
        Intrinsics.checkNotNull(parent2);
        return (KtElement) parent2;
    }

    public static final KtElement getQualifiedElementOrCallableRef(KtSimpleNameExpression ktSimpleNameExpression) {
        Intrinsics.checkNotNullParameter(ktSimpleNameExpression, "<this>");
        PsiElement parent = ktSimpleNameExpression.getLightOwner();
        return ((parent instanceof KtCallableReferenceExpression) && Intrinsics.areEqual(((KtCallableReferenceExpression) parent).getCallableReference(), ktSimpleNameExpression)) ? (KtElement) parent : getQualifiedElement(ktSimpleNameExpression);
    }

    public static final KtQualifiedExpression getTopmostParentQualifiedExpressionForSelector(KtSimpleNameExpression ktSimpleNameExpression) {
        Intrinsics.checkNotNullParameter(ktSimpleNameExpression, "<this>");
        Object objLast = SequencesKt.last(SequencesKt.generateSequence(ktSimpleNameExpression, new Function1<KtExpression, KtExpression>() { // from class: org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt.getTopmostParentQualifiedExpressionForSelector.1
            @Override // kotlin.jvm.functions.Function1
            public final KtExpression invoke(KtExpression it) {
                Intrinsics.checkNotNullParameter(it, "it");
                PsiElement parent = it.getLightOwner();
                KtQualifiedExpression ktQualifiedExpression = parent instanceof KtQualifiedExpression ? (KtQualifiedExpression) parent : null;
                if (Intrinsics.areEqual(ktQualifiedExpression != null ? ktQualifiedExpression.getSelectorExpression() : null, it)) {
                    return ktQualifiedExpression;
                }
                return null;
            }
        }));
        if (objLast instanceof KtQualifiedExpression) {
            return (KtQualifiedExpression) objLast;
        }
        return null;
    }

    public static final KtElement getQualifiedElementSelector(KtElement ktElement) {
        KtExpression calleeExpression;
        Intrinsics.checkNotNullParameter(ktElement, "<this>");
        if (ktElement instanceof KtSimpleNameExpression) {
            return ktElement;
        }
        if (ktElement instanceof KtCallExpression) {
            return ((KtCallExpression) ktElement).getCalleeExpression();
        }
        if (ktElement instanceof KtQualifiedExpression) {
            KtExpression selectorExpression = ((KtQualifiedExpression) ktElement).getSelectorExpression();
            KtCallExpression ktCallExpression = selectorExpression instanceof KtCallExpression ? (KtCallExpression) selectorExpression : null;
            if (ktCallExpression != null && (calleeExpression = ktCallExpression.getCalleeExpression()) != null) {
                selectorExpression = calleeExpression;
            }
            return selectorExpression;
        }
        if (ktElement instanceof KtUserType) {
            return ((KtUserType) ktElement).getReferenceExpression();
        }
        return null;
    }

    public static final KtExpression getReceiverExpression(KtSimpleNameExpression ktSimpleNameExpression) {
        KtUserType qualifier;
        Intrinsics.checkNotNullParameter(ktSimpleNameExpression, "<this>");
        PsiElement parent = ktSimpleNameExpression.getLightOwner();
        if (parent instanceof KtQualifiedExpression) {
            KtExpression receiverExpression = ((KtQualifiedExpression) parent).getReceiverExpression();
            if (Intrinsics.areEqual(receiverExpression, ktSimpleNameExpression)) {
                return null;
            }
            return receiverExpression;
        }
        if (parent instanceof KtCallExpression) {
            PsiElement parent2 = ((KtCallExpression) parent).getLightOwner();
            if (!(parent2 instanceof KtQualifiedExpression)) {
                return null;
            }
            KtExpression receiverExpression2 = ((KtQualifiedExpression) parent2).getReceiverExpression();
            if (Intrinsics.areEqual(receiverExpression2, parent)) {
                return null;
            }
            return receiverExpression2;
        }
        if (parent instanceof KtBinaryExpression) {
            KtBinaryExpression ktBinaryExpression = (KtBinaryExpression) parent;
            if (Intrinsics.areEqual(ktBinaryExpression.getOperationReference(), ktSimpleNameExpression)) {
                ImmutableSet<KtSingleValueToken> IN_OPERATIONS = OperatorConventions.IN_OPERATIONS;
                Intrinsics.checkNotNullExpressionValue(IN_OPERATIONS, "IN_OPERATIONS");
                return CollectionsKt.contains(IN_OPERATIONS, ktBinaryExpression.getOperationToken()) ? ktBinaryExpression.getRight() : ktBinaryExpression.getLeft();
            }
        }
        if (parent instanceof KtUnaryExpression) {
            KtUnaryExpression ktUnaryExpression = (KtUnaryExpression) parent;
            if (Intrinsics.areEqual(ktUnaryExpression.getOperationReference(), ktSimpleNameExpression)) {
                return ktUnaryExpression.getBaseExpression();
            }
        }
        if (!(parent instanceof KtUserType) || (qualifier = ((KtUserType) parent).getQualifier()) == null) {
            return null;
        }
        KtSimpleNameExpression referenceExpression = qualifier.getReferenceExpression();
        Intrinsics.checkNotNull(referenceExpression);
        return referenceExpression;
    }

    public static final KtQualifiedExpression getQualifiedExpressionForSelector(KtElement ktElement) {
        Intrinsics.checkNotNullParameter(ktElement, "<this>");
        PsiElement parent = ktElement.getLightOwner();
        if (parent instanceof KtQualifiedExpression) {
            KtQualifiedExpression ktQualifiedExpression = (KtQualifiedExpression) parent;
            if (Intrinsics.areEqual(ktQualifiedExpression.getSelectorExpression(), ktElement)) {
                return ktQualifiedExpression;
            }
        }
        return null;
    }

    public static final KtExpression getQualifiedExpressionForSelectorOrThis(KtExpression ktExpression) {
        Intrinsics.checkNotNullParameter(ktExpression, "<this>");
        KtQualifiedExpression qualifiedExpressionForSelector = getQualifiedExpressionForSelector(ktExpression);
        return qualifiedExpressionForSelector != null ? qualifiedExpressionForSelector : ktExpression;
    }

    public static final KtQualifiedExpression getQualifiedExpressionForReceiver(KtExpression ktExpression) {
        Intrinsics.checkNotNullParameter(ktExpression, "<this>");
        PsiElement parent = ktExpression.getLightOwner();
        if (parent instanceof KtQualifiedExpression) {
            KtQualifiedExpression ktQualifiedExpression = (KtQualifiedExpression) parent;
            if (Intrinsics.areEqual(ktQualifiedExpression.getReceiverExpression(), ktExpression)) {
                return ktQualifiedExpression;
            }
        }
        return null;
    }

    public static final KtExpression getQualifiedExpressionForReceiverOrThis(KtExpression ktExpression) {
        Intrinsics.checkNotNullParameter(ktExpression, "<this>");
        KtQualifiedExpression qualifiedExpressionForReceiver = getQualifiedExpressionForReceiver(ktExpression);
        return qualifiedExpressionForReceiver != null ? qualifiedExpressionForReceiver : ktExpression;
    }

    public static final boolean isDotReceiver(KtExpression ktExpression) {
        Intrinsics.checkNotNullParameter(ktExpression, "<this>");
        PsiElement parent = ktExpression.getLightOwner();
        KtDotQualifiedExpression ktDotQualifiedExpression = parent instanceof KtDotQualifiedExpression ? (KtDotQualifiedExpression) parent : null;
        return Intrinsics.areEqual(ktDotQualifiedExpression != null ? ktDotQualifiedExpression.getReceiverExpression() : null, ktExpression);
    }

    public static final boolean isDotSelector(KtExpression ktExpression) {
        Intrinsics.checkNotNullParameter(ktExpression, "<this>");
        PsiElement parent = ktExpression.getLightOwner();
        KtDotQualifiedExpression ktDotQualifiedExpression = parent instanceof KtDotQualifiedExpression ? (KtDotQualifiedExpression) parent : null;
        return Intrinsics.areEqual(ktDotQualifiedExpression != null ? ktDotQualifiedExpression.getSelectorExpression() : null, ktExpression);
    }

    public static final KtCallExpression getPossiblyQualifiedCallExpression(KtExpression ktExpression) {
        KtExpression selectorExpression;
        Intrinsics.checkNotNullParameter(ktExpression, "<this>");
        KtQualifiedExpression ktQualifiedExpression = ktExpression instanceof KtQualifiedExpression ? (KtQualifiedExpression) ktExpression : null;
        if (ktQualifiedExpression != null && (selectorExpression = ktQualifiedExpression.getSelectorExpression()) != null) {
            ktExpression = selectorExpression;
        }
        if (ktExpression instanceof KtCallExpression) {
            return (KtCallExpression) ktExpression;
        }
        return null;
    }

    public static final Sequence<KtElement> blockExpressionsOrSingle(KtElement ktElement) {
        Intrinsics.checkNotNullParameter(ktElement, "<this>");
        if (!(ktElement instanceof KtBlockExpression)) {
            return SequencesKt.sequenceOf(ktElement);
        }
        List<KtExpression> statements = ((KtBlockExpression) ktElement).getStatements();
        Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
        return CollectionsKt.asSequence(statements);
    }

    public static final KtExpression lastBlockStatementOrThis(KtExpression ktExpression) {
        List<KtExpression> statements;
        KtExpression ktExpression2;
        Intrinsics.checkNotNullParameter(ktExpression, "<this>");
        KtBlockExpression ktBlockExpression = ktExpression instanceof KtBlockExpression ? (KtBlockExpression) ktExpression : null;
        return (ktBlockExpression == null || (statements = ktBlockExpression.getStatements()) == null || (ktExpression2 = (KtExpression) CollectionsKt.lastOrNull((List) statements)) == null) ? ktExpression : ktExpression2;
    }

    public static final PsiChildRange contentRange(KtBlockExpression ktBlockExpression) {
        PsiElement rBrace;
        Intrinsics.checkNotNullParameter(ktBlockExpression, "<this>");
        PsiElement lBrace = ktBlockExpression.getLBrace();
        if (lBrace != null && (rBrace = ktBlockExpression.getRBrace()) != null) {
            Object obj = null;
            for (Object obj2 : PsiUtilsKt.siblings$default(lBrace, false, false, 1, null)) {
                if (!(((PsiElement) obj2) instanceof PsiWhiteSpace)) {
                    obj = obj2;
                    break;
                }
            }
            PsiElement psiElement = (PsiElement) obj;
            if (Intrinsics.areEqual(psiElement, rBrace)) {
                return PsiChildRange.INSTANCE.getEMPTY();
            }
            for (PsiElement psiElement2 : PsiUtilsKt.siblings(rBrace, false, false)) {
                if (!(psiElement2 instanceof PsiWhiteSpace)) {
                    return Intrinsics.areEqual(psiElement2, lBrace) ? PsiChildRange.INSTANCE.getEMPTY() : new PsiChildRange(psiElement, psiElement2);
                }
            }
            throw new NoSuchElementException("Sequence contains no element matching the predicate.");
        }
        return PsiChildRange.INSTANCE.getEMPTY();
    }

    public static final boolean isAbstract(KtClass ktClass) {
        Intrinsics.checkNotNullParameter(ktClass, "<this>");
        return ktClass.isInterface() || ktClass.hasModifier(KtTokens.ABSTRACT_KEYWORD);
    }

    private static final void getSuperNames$addSuperName(StubBasedPsiElementBase<? extends KotlinClassOrObjectStub<? extends KtClassOrObject>> stubBasedPsiElementBase, List<String> list, String str) {
        KtImportDirective ktImportDirectiveFindImportByAlias;
        list.add(str);
        PsiFile containingFile = stubBasedPsiElementBase.getContainingFile();
        Intrinsics.checkNotNullExpressionValue(containingFile, "getContainingFile(...)");
        if (!(containingFile instanceof KtFile) || (ktImportDirectiveFindImportByAlias = ((KtFile) containingFile).findImportByAlias(str)) == null) {
            return;
        }
        KtExpression importedReference = ktImportDirectiveFindImportByAlias.getImportedReference();
        while (importedReference instanceof KtDotQualifiedExpression) {
            importedReference = ((KtDotQualifiedExpression) importedReference).getSelectorExpression();
        }
        if (importedReference instanceof KtSimpleNameExpression) {
            list.add(((KtSimpleNameExpression) importedReference).getReferencedName());
        }
    }

    public static final List<String> getSuperNames(StubBasedPsiElementBase<? extends KotlinClassOrObjectStub<? extends KtClassOrObject>> stubBasedPsiElementBase) {
        String referencedName;
        Intrinsics.checkNotNullParameter(stubBasedPsiElementBase, "<this>");
        if (!(stubBasedPsiElementBase instanceof KtClassOrObject)) {
            throw new IllegalArgumentException(("it should be " + Reflection.getOrCreateKotlinClass(KtClassOrObject.class) + " but it is a " + stubBasedPsiElementBase.getClass().getName()).toString());
        }
        KtClassOrObject ktClassOrObject = (KtClassOrObject) stubBasedPsiElementBase;
        KotlinClassOrObjectStub kotlinClassOrObjectStub = (KotlinClassOrObjectStub) ktClassOrObject.getStub();
        if (kotlinClassOrObjectStub != null) {
            return kotlinClassOrObjectStub.getSuperNames();
        }
        List<KtSuperTypeListEntry> superTypeListEntries = ktClassOrObject.getSuperTypeListEntries();
        if (superTypeListEntries.isEmpty()) {
            List<String> listEmptyList = Collections.emptyList();
            Intrinsics.checkNotNullExpressionValue(listEmptyList, "emptyList(...)");
            return listEmptyList;
        }
        ArrayList arrayList = new ArrayList();
        Iterator<KtSuperTypeListEntry> it = superTypeListEntries.iterator();
        while (it.hasNext()) {
            KtUserType typeAsUserType = it.next().getTypeAsUserType();
            if (typeAsUserType != null && (referencedName = typeAsUserType.getReferencedName()) != null) {
                getSuperNames$addSuperName(stubBasedPsiElementBase, arrayList, referencedName);
            }
        }
        return arrayList;
    }

    public static final List<KtAnnotationEntry> getAnnotationEntries(KtExpression ktExpression) {
        Intrinsics.checkNotNullParameter(ktExpression, "<this>");
        PsiElement parent = ktExpression.getLightOwner();
        if (parent instanceof KtAnnotatedExpression) {
            List<KtAnnotationEntry> annotationEntries = ((KtAnnotatedExpression) parent).getAnnotationEntries();
            Intrinsics.checkNotNullExpressionValue(annotationEntries, "getAnnotationEntries(...)");
            return annotationEntries;
        }
        if (!(parent instanceof KtLabeledExpression)) {
            return CollectionsKt.emptyList();
        }
        Intrinsics.checkNotNull(parent);
        return getAnnotationEntries((KtExpression) parent);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final List<KtAnnotationEntry> collectAnnotationEntriesFromStubOrPsi(KtAnnotationsContainer ktAnnotationsContainer) {
        List<KtAnnotationEntry> listCollectAnnotationEntriesFromStubElement;
        Intrinsics.checkNotNullParameter(ktAnnotationsContainer, "<this>");
        if (!(ktAnnotationsContainer instanceof StubBasedPsiElementBase)) {
            return collectAnnotationEntriesFromPsi(ktAnnotationsContainer);
        }
        StubElement stub = ((StubBasedPsiElementBase) ktAnnotationsContainer).getStub();
        return (stub == null || (listCollectAnnotationEntriesFromStubElement = collectAnnotationEntriesFromStubElement(stub)) == null) ? collectAnnotationEntriesFromPsi(ktAnnotationsContainer) : listCollectAnnotationEntriesFromStubElement;
    }

    private static final List<KtAnnotationEntry> collectAnnotationEntriesFromStubElement(StubElement<?> stubElement) {
        List<KtAnnotationEntry> listEmptyList;
        List<StubElement> childrenStubs = stubElement.getChildrenStubs();
        Intrinsics.checkNotNullExpressionValue(childrenStubs, "getChildrenStubs(...)");
        ArrayList arrayList = new ArrayList();
        for (StubElement stubElement2 : childrenStubs) {
            IStubElementType stubType = stubElement2.getStubType();
            if (Intrinsics.areEqual(stubType, KtNodeTypes.ANNOTATION_ENTRY)) {
                PsiElement psi = stubElement2.getPsi();
                Intrinsics.checkNotNull(psi, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtAnnotationEntry");
                listEmptyList = CollectionsKt.listOf((KtAnnotationEntry) psi);
            } else if (Intrinsics.areEqual(stubType, KtNodeTypes.ANNOTATION)) {
                PsiElement psi2 = stubElement2.getPsi();
                Intrinsics.checkNotNull(psi2, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtAnnotation");
                listEmptyList = ((KtAnnotation) psi2).getEntries();
            } else {
                listEmptyList = CollectionsKt.emptyList();
            }
            Intrinsics.checkNotNull(listEmptyList);
            CollectionsKt.addAll(arrayList, listEmptyList);
        }
        return arrayList;
    }

    private static final List<KtAnnotationEntry> collectAnnotationEntriesFromPsi(KtAnnotationsContainer ktAnnotationsContainer) {
        List<KtAnnotationEntry> entries;
        PsiElement[] children = ktAnnotationsContainer.getChildren();
        Intrinsics.checkNotNullExpressionValue(children, "getChildren(...)");
        ArrayList arrayList = new ArrayList();
        for (PsiElement psiElement : children) {
            if (psiElement instanceof KtAnnotationEntry) {
                Intrinsics.checkNotNull(psiElement);
                entries = CollectionsKt.listOf(psiElement);
            } else {
                entries = psiElement instanceof KtAnnotation ? ((KtAnnotation) psiElement).getEntries() : CollectionsKt.emptyList();
            }
            Intrinsics.checkNotNull(entries);
            CollectionsKt.addAll(arrayList, entries);
        }
        return arrayList;
    }

    public static final /* synthetic */ <T extends KtElement> KtVisitorVoid forEachDescendantOfTypeVisitor(final Function1<? super T, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        Intrinsics.needClassReification();
        return new KtTreeVisitorVoid() { // from class: org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt.forEachDescendantOfTypeVisitor.1
            /* JADX WARN: Type inference incomplete: some casts might be missing */
            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitKtElement(KtElement element) {
                Intrinsics.checkNotNullParameter(element, "element");
                super.visitKtElement(element);
                Intrinsics.reifiedOperationMarker(3, ExifInterface.GPS_DIRECTION_TRUE);
                if (element instanceof KtElement) {
                    block.invoke((T) element);
                }
            }
        };
    }

    public static final /* synthetic */ <T extends KtElement, R> KtVisitorVoid flatMapDescendantsOfTypeVisitor(final Collection<R> accumulator, final Function1<? super T, ? extends Collection<? extends R>> map) {
        Intrinsics.checkNotNullParameter(accumulator, "accumulator");
        Intrinsics.checkNotNullParameter(map, "map");
        Intrinsics.needClassReification();
        final Function1 function1 = new Function1<T, Unit>() { // from class: org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt.flatMapDescendantsOfTypeVisitor.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Object obj) {
                invoke((KtElement) obj);
                return Unit.INSTANCE;
            }

            /* JADX WARN: Incorrect types in method signature: (TT;)V */
            /* JADX WARN: Type inference incomplete: some casts might be missing */
            public final void invoke(KtElement it) {
                Intrinsics.checkNotNullParameter(it, "it");
                accumulator.addAll((Collection<? extends R>) ((Collection) map.invoke((T) it)));
            }
        };
        Intrinsics.needClassReification();
        return new KtTreeVisitorVoid() { // from class: org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt$flatMapDescendantsOfTypeVisitor$$inlined$forEachDescendantOfTypeVisitor$1
            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitKtElement(KtElement element) {
                Intrinsics.checkNotNullParameter(element, "element");
                super.visitKtElement(element);
                Intrinsics.reifiedOperationMarker(3, ExifInterface.GPS_DIRECTION_TRUE);
                if (element instanceof KtElement) {
                    function1.invoke(element);
                }
            }
        };
    }

    public static final boolean isContractPresentPsiCheck(KtNamedFunction ktNamedFunction, boolean z) {
        KtBlockExpression bodyBlockExpression;
        List<KtExpression> statements;
        KtExpression ktExpression;
        Intrinsics.checkNotNullParameter(ktNamedFunction, "<this>");
        if ((z || ktNamedFunction.isTopLevel()) && ktNamedFunction.hasBlockBody() && !ktNamedFunction.hasModifier(KtTokens.OPERATOR_KEYWORD)) {
            KtNamedFunction ktNamedFunction2 = ktNamedFunction instanceof KtFunction ? ktNamedFunction : null;
            if (ktNamedFunction2 != null && (bodyBlockExpression = ktNamedFunction2.getBodyBlockExpression()) != null && (statements = bodyBlockExpression.getStatements()) != null && (ktExpression = (KtExpression) CollectionsKt.firstOrNull((List) statements)) != null) {
                return isContractDescriptionCallPsiCheck(ktExpression);
            }
        }
        return false;
    }

    /* JADX WARN: Code duplicated, block: B:10:0x0020  */
    public static final boolean isContractDescriptionCallPsiCheck(KtExpression ktExpression) {
        Intrinsics.checkNotNullParameter(ktExpression, "<this>");
        if (ktExpression instanceof KtCallExpression) {
            KtExpression calleeExpression = ((KtCallExpression) ktExpression).getCalleeExpression();
            if (!Intrinsics.areEqual(calleeExpression != null ? calleeExpression.getText() : null, "contract")) {
                if (ktExpression instanceof KtQualifiedExpression) {
                }
                return false;
            }
        } else if ((ktExpression instanceof KtQualifiedExpression) || !isContractDescriptionCallPsiCheck((KtQualifiedExpression) ktExpression)) {
            return false;
        }
        return true;
    }

    public static final boolean isContractDescriptionCallPsiCheck(KtQualifiedExpression ktQualifiedExpression) {
        Intrinsics.checkNotNullParameter(ktQualifiedExpression, "<this>");
        KtExpression selectorExpression = ktQualifiedExpression.getSelectorExpression();
        return selectorExpression != null && Intrinsics.areEqual(ktQualifiedExpression.getReceiverExpression().getText(), "kotlin.contracts") && isContractDescriptionCallPsiCheck(selectorExpression);
    }

    public static final boolean isFirstStatement(KtElement ktElement) {
        Intrinsics.checkNotNullParameter(ktElement, "<this>");
        PsiElement parent = ktElement.getLightOwner();
        if (parent instanceof KtDotQualifiedExpression) {
            ktElement = (KtElement) parent;
            parent = ((KtDotQualifiedExpression) parent).getLightOwner();
        }
        return (parent instanceof KtBlockExpression) && Intrinsics.areEqual(((KtBlockExpression) parent).getFirstStatement(), ktElement);
    }

    public static final List<KtDeclaration> effectiveDeclarations(KtClassOrObject ktClassOrObject) {
        Intrinsics.checkNotNullParameter(ktClassOrObject, "<this>");
        if (!(ktClassOrObject instanceof KtClass)) {
            return ktClassOrObject.getDeclarations();
        }
        List<KtDeclaration> declarations = ktClassOrObject.getDeclarations();
        List<KtParameter> primaryConstructorParameters = ktClassOrObject.getPrimaryConstructorParameters();
        ArrayList arrayList = new ArrayList();
        for (Object obj : primaryConstructorParameters) {
            if (((KtParameter) obj).hasValOrVar()) {
                arrayList.add(obj);
            }
        }
        return CollectionsKt.plus((Collection) declarations, (Iterable) arrayList);
    }

    public static final boolean isExtensionDeclaration(PsiElement psiElement) {
        KtCallableDeclaration ktCallableDeclaration;
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        if (psiElement instanceof KtNamedFunction ? true : psiElement instanceof KtProperty) {
            ktCallableDeclaration = (KtCallableDeclaration) psiElement;
        } else {
            ktCallableDeclaration = psiElement instanceof KtPropertyAccessor ? (KtCallableDeclaration) PsiTreeUtil.getParentOfType(psiElement, KtProperty.class, false) : null;
        }
        return (ktCallableDeclaration != null ? ktCallableDeclaration.mo7393getReceiverTypeReference() : null) != null;
    }

    public static final boolean isContextualDeclaration(KtElement ktElement) {
        List<KtContextReceiver> contextReceivers;
        Intrinsics.checkNotNullParameter(ktElement, "<this>");
        if (ktElement instanceof KtCallableDeclaration) {
            contextReceivers = ((KtCallableDeclaration) ktElement).getContextReceivers();
            Intrinsics.checkNotNullExpressionValue(contextReceivers, "getContextReceivers(...)");
        } else {
            contextReceivers = ktElement instanceof KtClassOrObject ? ((KtClassOrObject) ktElement).getContextReceivers() : CollectionsKt.emptyList();
        }
        return !contextReceivers.isEmpty();
    }

    public static final boolean isObjectLiteral(KtClassOrObject ktClassOrObject) {
        Intrinsics.checkNotNullParameter(ktClassOrObject, "<this>");
        return (ktClassOrObject instanceof KtObjectDeclaration) && ((KtObjectDeclaration) ktClassOrObject).isObjectLiteral();
    }

    public static final int parameterIndex(PsiElement psiElement) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        PsiElement parent = psiElement.getLightOwner();
        if ((psiElement instanceof KtParameter) && (parent instanceof KtParameterList)) {
            return ((KtParameterList) parent).getParameters().indexOf(psiElement);
        }
        if ((psiElement instanceof PsiParameter) && (parent instanceof PsiParameterList)) {
            return ((PsiParameterList) parent).getParameterIndex((PsiParameter) psiElement);
        }
        return -1;
    }

    public static final int getArgumentIndex(KtValueArgument ktValueArgument) {
        Intrinsics.checkNotNullParameter(ktValueArgument, "<this>");
        PsiElement parent = ktValueArgument.getLightOwner();
        Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtValueArgumentList");
        return ((KtValueArgumentList) parent).getArguments().indexOf(ktValueArgument);
    }

    public static final boolean isPrivate(KtModifierListOwner ktModifierListOwner) {
        Intrinsics.checkNotNullParameter(ktModifierListOwner, "<this>");
        return ktModifierListOwner.hasModifier(KtTokens.PRIVATE_KEYWORD);
    }

    public static final boolean isProtected(KtModifierListOwner ktModifierListOwner) {
        Intrinsics.checkNotNullParameter(ktModifierListOwner, "<this>");
        return ktModifierListOwner.hasModifier(KtTokens.PROTECTED_KEYWORD);
    }

    public static final boolean isImportDirectiveExpression(KtSimpleNameExpression ktSimpleNameExpression) {
        Intrinsics.checkNotNullParameter(ktSimpleNameExpression, "<this>");
        PsiElement parent = ktSimpleNameExpression.getLightOwner();
        return (parent instanceof KtImportDirective) || (parent.getLightOwner() instanceof KtImportDirective);
    }

    public static final boolean isPackageDirectiveExpression(KtSimpleNameExpression ktSimpleNameExpression) {
        Intrinsics.checkNotNullParameter(ktSimpleNameExpression, "<this>");
        PsiElement parent = ktSimpleNameExpression.getLightOwner();
        return (parent instanceof KtPackageDirective) || (parent.getLightOwner() instanceof KtPackageDirective);
    }

    public static final boolean isInImportDirective(KtExpression ktExpression) {
        Intrinsics.checkNotNullParameter(ktExpression, "<this>");
        Iterator it = SequencesKt.takeWhile(PsiUtilsKt.getParents(ktExpression), new Function1<PsiElement, Boolean>() { // from class: org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt.isInImportDirective.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(PsiElement it2) {
                Intrinsics.checkNotNullParameter(it2, "it");
                return Boolean.valueOf(((it2 instanceof KtDeclaration) || (it2 instanceof KtBlockExpression)) ? false : true);
            }
        }).iterator();
        while (it.hasNext()) {
            if (((PsiElement) it.next()) instanceof KtImportDirective) {
                return true;
            }
        }
        return false;
    }

    public static final boolean isLambdaOutsideParentheses(KtExpression ktExpression) {
        Intrinsics.checkNotNullParameter(ktExpression, "<this>");
        PsiElement parent = ktExpression.getLightOwner();
        if (parent instanceof KtLambdaArgument) {
            return true;
        }
        if (!(parent instanceof KtLabeledExpression)) {
            return false;
        }
        Intrinsics.checkNotNull(parent);
        return isLambdaOutsideParentheses((KtExpression) parent);
    }

    public static final KtBinaryExpression getAssignmentByLHS(KtExpression ktExpression) {
        Intrinsics.checkNotNullParameter(ktExpression, "<this>");
        PsiElement parent = ktExpression.getLightOwner();
        KtBinaryExpression ktBinaryExpression = parent instanceof KtBinaryExpression ? (KtBinaryExpression) parent : null;
        if (ktBinaryExpression != null && KtPsiUtil.isAssignment(ktBinaryExpression) && Intrinsics.areEqual(ktBinaryExpression.getLeft(), ktExpression)) {
            return ktBinaryExpression;
        }
        return null;
    }

    public static final KtBinaryExpression findAssignment(PsiElement psiElement) {
        while (true) {
            PsiElement parent = psiElement != null ? psiElement.getLightOwner() : null;
            if (parent instanceof KtBinaryExpression) {
                KtBinaryExpression ktBinaryExpression = (KtBinaryExpression) parent;
                if (Intrinsics.areEqual(ktBinaryExpression.getLeft(), psiElement) && Intrinsics.areEqual(ktBinaryExpression.getOperationToken(), KtTokens.EQ)) {
                    return ktBinaryExpression;
                }
                return null;
            }
            if (parent instanceof KtQualifiedExpression) {
                psiElement = psiElement.getLightOwner();
            } else {
                if (!(parent instanceof KtSimpleNameExpression)) {
                    return null;
                }
                psiElement = psiElement.getLightOwner();
            }
        }
    }

    public static final TextRange getContentRange(KtStringTemplateExpression ktStringTemplateExpression) {
        Intrinsics.checkNotNullParameter(ktStringTemplateExpression, "<this>");
        int textLength = ktStringTemplateExpression.getNode().getFirstChildNode().getTextLength();
        ASTNode lastChildNode = ktStringTemplateExpression.getNode().getLastChildNode();
        int textLength2 = ktStringTemplateExpression.getTextLength();
        if (Intrinsics.areEqual(lastChildNode.getElementType(), KtTokens.CLOSING_QUOTE)) {
            textLength2 -= lastChildNode.getTextLength();
        }
        return new TextRange(textLength, textLength2);
    }

    public static final boolean isCallee(KtSimpleNameExpression ktSimpleNameExpression) {
        KtTypeReference typeReference;
        KtTypeReference ktTypeReference;
        KtConstructorCalleeExpression ktConstructorCalleeExpression;
        Intrinsics.checkNotNullParameter(ktSimpleNameExpression, "<this>");
        PsiElement parent = ktSimpleNameExpression.getLightOwner();
        if (parent instanceof KtCallElement) {
            return Intrinsics.areEqual(((KtCallElement) parent).getCalleeExpression(), ktSimpleNameExpression);
        }
        if (parent instanceof KtBinaryExpression) {
            return Intrinsics.areEqual(((KtBinaryExpression) parent).getOperationReference(), ktSimpleNameExpression);
        }
        KtUserType ktUserType = (KtUserType) PsiTreeUtil.getParentOfType(ktSimpleNameExpression, KtUserType.class, true);
        KtCallElement ktCallElement = (ktUserType == null || (ktTypeReference = (KtTypeReference) PsiTreeUtil.getParentOfType(ktUserType, KtTypeReference.class, true)) == null || (ktConstructorCalleeExpression = (KtConstructorCalleeExpression) PsiTreeUtil.getParentOfType(ktTypeReference, KtConstructorCalleeExpression.class, true)) == null) ? null : (KtCallElement) PsiTreeUtil.getParentOfType(ktConstructorCalleeExpression, KtCallElement.class, true);
        if (ktCallElement == null) {
            return false;
        }
        KtExpression calleeExpression = ktCallElement.getCalleeExpression();
        KtConstructorCalleeExpression ktConstructorCalleeExpression2 = calleeExpression instanceof KtConstructorCalleeExpression ? (KtConstructorCalleeExpression) calleeExpression : null;
        KtTypeElement typeElement = (ktConstructorCalleeExpression2 == null || (typeReference = ktConstructorCalleeExpression2.getTypeReference()) == null) ? null : typeReference.getTypeElement();
        KtUserType ktUserType2 = typeElement instanceof KtUserType ? (KtUserType) typeElement : null;
        return Intrinsics.areEqual(ktUserType2 != null ? ktUserType2.getReferenceExpression() : null, ktSimpleNameExpression);
    }

    public static final String getPlainContent(KtStringTemplateExpression ktStringTemplateExpression) {
        Intrinsics.checkNotNullParameter(ktStringTemplateExpression, "<this>");
        String strSubstring = getContentRange(ktStringTemplateExpression).substring(ktStringTemplateExpression.getText());
        Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
        return strSubstring;
    }

    public static final boolean isSingleQuoted(KtStringTemplateExpression ktStringTemplateExpression) {
        Intrinsics.checkNotNullParameter(ktStringTemplateExpression, "<this>");
        return ktStringTemplateExpression.getNode().getFirstChildNode().getTextLength() == 1;
    }

    public static final boolean isPrivateNestedClassOrObject(KtNamedDeclaration ktNamedDeclaration) {
        Intrinsics.checkNotNullParameter(ktNamedDeclaration, "<this>");
        return (ktNamedDeclaration instanceof KtClassOrObject) && isPrivate(ktNamedDeclaration) && !((KtClassOrObject) ktNamedDeclaration).isTopLevel();
    }

    public static final List<KtParameter> getValueParameters(KtNamedDeclaration ktNamedDeclaration) {
        Intrinsics.checkNotNullParameter(ktNamedDeclaration, "<this>");
        KtParameterList valueParameterList = getValueParameterList(ktNamedDeclaration);
        List<KtParameter> parameters = valueParameterList != null ? valueParameterList.getParameters() : null;
        if (parameters != null) {
            return parameters;
        }
        List<KtParameter> listEmptyList = Collections.emptyList();
        Intrinsics.checkNotNullExpressionValue(listEmptyList, "emptyList(...)");
        return listEmptyList;
    }

    public static final KtParameterList getValueParameterList(KtNamedDeclaration ktNamedDeclaration) {
        Intrinsics.checkNotNullParameter(ktNamedDeclaration, "<this>");
        if (ktNamedDeclaration instanceof KtCallableDeclaration) {
            return ((KtCallableDeclaration) ktNamedDeclaration).getValueParameterList();
        }
        if (ktNamedDeclaration instanceof KtClass) {
            return ((KtClass) ktNamedDeclaration).getPrimaryConstructorParameterList();
        }
        return null;
    }

    public static final KtBinaryExpression asAssignment(KtExpression ktExpression) {
        Intrinsics.checkNotNullParameter(ktExpression, "<this>");
        if (KtPsiUtil.isAssignment(ktExpression)) {
            return (KtBinaryExpression) ktExpression;
        }
        return null;
    }

    private static final PsiElement modifierFromTokenSet(final KtModifierList ktModifierList, TokenSet tokenSet) {
        Object next;
        IElementType[] types = tokenSet.getTypes();
        Intrinsics.checkNotNullExpressionValue(types, "getTypes(...)");
        Iterator it = SequencesKt.map(ArraysKt.asSequence(types), new Function1<IElementType, PsiElement>() { // from class: org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt.modifierFromTokenSet.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final PsiElement invoke(IElementType iElementType) {
                KtModifierList ktModifierList2 = ktModifierList;
                Intrinsics.checkNotNull(iElementType, "null cannot be cast to non-null type org.jetbrains.kotlin.lexer.KtModifierKeywordToken");
                return ktModifierList2.getModifier((KtModifierKeywordToken) iElementType);
            }
        }).iterator();
        while (it.hasNext()) {
            next = it.next();
            if (((PsiElement) next) != null) {
                return (PsiElement) next;
            }
        }
        next = null;
        return (PsiElement) next;
    }

    private static final PsiElement modifierFromTokenSet(KtModifierListOwner ktModifierListOwner, TokenSet tokenSet) {
        KtModifierList modifierList = ktModifierListOwner.getModifierList();
        if (modifierList != null) {
            return modifierFromTokenSet(modifierList, tokenSet);
        }
        return null;
    }

    public static final PsiElement visibilityModifier(KtModifierList ktModifierList) {
        Intrinsics.checkNotNullParameter(ktModifierList, "<this>");
        TokenSet VISIBILITY_MODIFIERS = KtTokens.VISIBILITY_MODIFIERS;
        Intrinsics.checkNotNullExpressionValue(VISIBILITY_MODIFIERS, "VISIBILITY_MODIFIERS");
        return modifierFromTokenSet(ktModifierList, VISIBILITY_MODIFIERS);
    }

    public static final KtModifierKeywordToken visibilityModifierType(KtModifierList ktModifierList) {
        ASTNode node;
        Intrinsics.checkNotNullParameter(ktModifierList, "<this>");
        PsiElement psiElementVisibilityModifier = visibilityModifier(ktModifierList);
        return (KtModifierKeywordToken) ((psiElementVisibilityModifier == null || (node = psiElementVisibilityModifier.getNode()) == null) ? null : node.getElementType());
    }

    public static final PsiElement visibilityModifier(KtModifierListOwner ktModifierListOwner) {
        Intrinsics.checkNotNullParameter(ktModifierListOwner, "<this>");
        KtModifierList modifierList = ktModifierListOwner.getModifierList();
        if (modifierList == null) {
            return null;
        }
        TokenSet VISIBILITY_MODIFIERS = KtTokens.VISIBILITY_MODIFIERS;
        Intrinsics.checkNotNullExpressionValue(VISIBILITY_MODIFIERS, "VISIBILITY_MODIFIERS");
        return modifierFromTokenSet(modifierList, VISIBILITY_MODIFIERS);
    }

    public static final boolean isPublic(KtModifierListOwner ktModifierListOwner) {
        Intrinsics.checkNotNullParameter(ktModifierListOwner, "<this>");
        if ((ktModifierListOwner instanceof KtDeclaration) && KtPsiUtil.isLocal((KtDeclaration) ktModifierListOwner)) {
            return false;
        }
        KtModifierKeywordToken ktModifierKeywordTokenVisibilityModifierType = visibilityModifierType(ktModifierListOwner);
        return ktModifierKeywordTokenVisibilityModifierType == null || Intrinsics.areEqual(ktModifierKeywordTokenVisibilityModifierType, KtTokens.PUBLIC_KEYWORD);
    }

    public static final KtModifierKeywordToken visibilityModifierType(KtModifierListOwner ktModifierListOwner) {
        ASTNode node;
        Intrinsics.checkNotNullParameter(ktModifierListOwner, "<this>");
        PsiElement psiElementVisibilityModifier = visibilityModifier(ktModifierListOwner);
        return (KtModifierKeywordToken) ((psiElementVisibilityModifier == null || (node = psiElementVisibilityModifier.getNode()) == null) ? null : node.getElementType());
    }

    public static final KtModifierKeywordToken visibilityModifierTypeOrDefault(KtModifierListOwner ktModifierListOwner) {
        Intrinsics.checkNotNullParameter(ktModifierListOwner, "<this>");
        KtModifierKeywordToken ktModifierKeywordTokenVisibilityModifierType = visibilityModifierType(ktModifierListOwner);
        if (ktModifierKeywordTokenVisibilityModifierType != null) {
            return ktModifierKeywordTokenVisibilityModifierType;
        }
        KtModifierKeywordToken DEFAULT_VISIBILITY_KEYWORD = KtTokens.DEFAULT_VISIBILITY_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(DEFAULT_VISIBILITY_KEYWORD, "DEFAULT_VISIBILITY_KEYWORD");
        return DEFAULT_VISIBILITY_KEYWORD;
    }

    public static final PsiElement modalityModifier(KtDeclaration ktDeclaration) {
        Intrinsics.checkNotNullParameter(ktDeclaration, "<this>");
        TokenSet MODALITY_MODIFIERS = KtTokens.MODALITY_MODIFIERS;
        Intrinsics.checkNotNullExpressionValue(MODALITY_MODIFIERS, "MODALITY_MODIFIERS");
        return modifierFromTokenSet(ktDeclaration, MODALITY_MODIFIERS);
    }

    public static final KtModifierKeywordToken modalityModifierType(KtDeclaration ktDeclaration) {
        ASTNode node;
        Intrinsics.checkNotNullParameter(ktDeclaration, "<this>");
        PsiElement psiElementModalityModifier = modalityModifier(ktDeclaration);
        return (KtModifierKeywordToken) ((psiElementModalityModifier == null || (node = psiElementModalityModifier.getNode()) == null) ? null : node.getElementType());
    }

    public static final boolean isPlain(KtStringTemplateExpression ktStringTemplateExpression) {
        Intrinsics.checkNotNullParameter(ktStringTemplateExpression, "<this>");
        KtStringTemplateEntry[] entries = ktStringTemplateExpression.getEntries();
        Intrinsics.checkNotNullExpressionValue(entries, "getEntries(...)");
        for (KtStringTemplateEntry ktStringTemplateEntry : entries) {
            if (!(ktStringTemplateEntry instanceof KtLiteralStringTemplateEntry)) {
                return false;
            }
        }
        return true;
    }

    public static final boolean isPlainWithEscapes(KtStringTemplateExpression ktStringTemplateExpression) {
        Intrinsics.checkNotNullParameter(ktStringTemplateExpression, "<this>");
        KtStringTemplateEntry[] entries = ktStringTemplateExpression.getEntries();
        Intrinsics.checkNotNullExpressionValue(entries, "getEntries(...)");
        for (KtStringTemplateEntry ktStringTemplateEntry : entries) {
            if (!(ktStringTemplateEntry instanceof KtLiteralStringTemplateEntry) && !(ktStringTemplateEntry instanceof KtEscapeStringTemplateEntry)) {
                return false;
            }
        }
        return true;
    }

    public static final KtClassOrObject getContainingClassOrObject(KtDeclaration ktDeclaration) {
        Intrinsics.checkNotNullParameter(ktDeclaration, "<this>");
        PsiElement parent = ktDeclaration.getLightOwner();
        if (parent instanceof KtClassBody) {
            PsiElement parent2 = ((KtClassBody) parent).getLightOwner();
            if (parent2 instanceof KtClassOrObject) {
                return (KtClassOrObject) parent2;
            }
            return null;
        }
        if (parent instanceof KtClassOrObject) {
            return (KtClassOrObject) parent;
        }
        if (!(parent instanceof KtParameterList)) {
            return null;
        }
        PsiElement parent3 = ((KtParameterList) parent).getLightOwner();
        KtPrimaryConstructor ktPrimaryConstructor = parent3 instanceof KtPrimaryConstructor ? (KtPrimaryConstructor) parent3 : null;
        if (ktPrimaryConstructor != null) {
            return ktPrimaryConstructor.getContainingClassOrObject();
        }
        return null;
    }

    public static final KtExpression getOutermostParenthesizerOrThis(KtExpression ktExpression) {
        Object next;
        Intrinsics.checkNotNullParameter(ktExpression, "<this>");
        KtExpression ktExpression2 = ktExpression;
        Iterator it = SequencesKt.zip(PsiUtilsKt.getParentsWithSelf(ktExpression2), PsiUtilsKt.getParents(ktExpression2)).iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            Pair pair = (Pair) next;
            PsiElement psiElement = (PsiElement) pair.component1();
            PsiElement psiElement2 = (PsiElement) pair.component2();
            if (!(psiElement2 instanceof KtParenthesizedExpression)) {
                if (psiElement2 instanceof KtAnnotatedExpression) {
                    if (!Intrinsics.areEqual(((KtAnnotatedExpression) psiElement2).getBaseExpression(), psiElement)) {
                        break;
                    }
                } else if (!(psiElement2 instanceof KtLabeledExpression) || !Intrinsics.areEqual(((KtLabeledExpression) psiElement2).getBaseExpression(), psiElement)) {
                    break;
                }
            }
        }
        Pair pair2 = (Pair) next;
        KtExpression ktExpression3 = (KtExpression) (pair2 != null ? (PsiElement) pair2.getFirst() : null);
        return ktExpression3 == null ? ktExpression : ktExpression3;
    }

    public static final boolean isFunctionalExpression(PsiElement psiElement) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        return (psiElement instanceof KtNamedFunction) && ((KtNamedFunction) psiElement).mo7392getNameIdentifier() == null;
    }

    public static final boolean canPlaceAfterSimpleNameEntry(PsiElement psiElement) {
        String text = psiElement != null ? psiElement.getText() : null;
        if (text == null) {
            return true;
        }
        return !BAD_NEIGHBOUR_FOR_SIMPLE_TEMPLATE_ENTRY_PATTERN.matches(text);
    }

    public static final Sequence<KtClass> nonStaticOuterClasses(KtElement ktElement) {
        Intrinsics.checkNotNullParameter(ktElement, "<this>");
        return SequencesKt.generateSequence(containingClass(ktElement), new Function1<KtClass, KtClass>() { // from class: org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt.nonStaticOuterClasses.1
            @Override // kotlin.jvm.functions.Function1
            public final KtClass invoke(KtClass it) {
                Intrinsics.checkNotNullParameter(it, "it");
                if (it.isInner()) {
                    return KtPsiUtilKt.containingClass(it);
                }
                return null;
            }
        });
    }

    public static final KtClass containingClass(KtElement ktElement) {
        Intrinsics.checkNotNullParameter(ktElement, "<this>");
        return (KtClass) PsiTreeUtil.getParentOfType(ktElement, KtClass.class, true);
    }

    public static final KtNamedDeclaration findPropertyByName(KtClassOrObject ktClassOrObject, String name2) {
        Object obj;
        Object next;
        Intrinsics.checkNotNullParameter(ktClassOrObject, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        Iterator<T> it = ktClassOrObject.getDeclarations().iterator();
        while (true) {
            obj = null;
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            KtDeclaration ktDeclaration = (KtDeclaration) next;
            if ((ktDeclaration instanceof KtProperty) && Intrinsics.areEqual(((KtProperty) ktDeclaration).get_name(), name2)) {
                break;
            }
        }
        KtNamedDeclaration ktNamedDeclaration = (KtNamedDeclaration) next;
        if (ktNamedDeclaration != null) {
            return ktNamedDeclaration;
        }
        for (Object obj2 : ktClassOrObject.getPrimaryConstructorParameters()) {
            KtParameter ktParameter = (KtParameter) obj2;
            if (ktParameter.hasValOrVar() && Intrinsics.areEqual(ktParameter.get_name(), name2)) {
                obj = obj2;
                break;
            }
        }
        return (KtNamedDeclaration) obj;
    }

    public static final KtNamedDeclaration findFunctionByName(KtClassOrObject ktClassOrObject, String name2) {
        Object next;
        Intrinsics.checkNotNullParameter(ktClassOrObject, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        Iterator<T> it = ktClassOrObject.getDeclarations().iterator();
        while (it.hasNext()) {
            next = it.next();
            KtDeclaration ktDeclaration = (KtDeclaration) next;
            if ((ktDeclaration instanceof KtNamedFunction) && Intrinsics.areEqual(((KtNamedFunction) ktDeclaration).get_name(), name2)) {
                return (KtNamedDeclaration) next;
            }
        }
        next = null;
        return (KtNamedDeclaration) next;
    }

    public static final boolean isTypeConstructorReference(PsiElement e) {
        Intrinsics.checkNotNullParameter(e, "e");
        PsiElement parent = e.getLightOwner();
        return (parent instanceof KtUserType) && Intrinsics.areEqual(((KtUserType) parent).getReferenceExpression(), e);
    }

    public static final boolean isPropertyParameter(KtParameter ktParameter) {
        Intrinsics.checkNotNullParameter(ktParameter, "<this>");
        return (ktParameter.getOwnerFunction() instanceof KtPrimaryConstructor) && ktParameter.hasValOrVar();
    }

    public static final boolean isDoubleColonReceiver(KtExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        KtExpression ktExpression = expression;
        C48901 c48901 = new Function1<KtDoubleColonExpression, PsiElement>() { // from class: org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt.isDoubleColonReceiver.1
            @Override // kotlin.jvm.functions.Function1
            public final PsiElement invoke(KtDoubleColonExpression getParentOfTypeAndBranch) {
                Intrinsics.checkNotNullParameter(getParentOfTypeAndBranch, "$this$getParentOfTypeAndBranch");
                return getParentOfTypeAndBranch.getReceiverExpression();
            }
        };
        PsiElement parentOfType = PsiTreeUtil.getParentOfType(ktExpression, KtDoubleColonExpression.class, false);
        return (parentOfType != null ? PsiUtilsKt.getIfChildIsInBranch(parentOfType, ktExpression, c48901) : null) != null;
    }

    public static final KtParameterList getOrCreateParameterList(KtFunctionLiteral ktFunctionLiteral) {
        Intrinsics.checkNotNullParameter(ktFunctionLiteral, "<this>");
        KtParameterList valueParameterList = ktFunctionLiteral.getValueParameterList();
        if (valueParameterList != null) {
            return valueParameterList;
        }
        Project project = ktFunctionLiteral.getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        KtPsiFactory ktPsiFactory = new KtPsiFactory(project, false, 2, (DefaultConstructorMarker) null);
        PsiElement lBrace = ktFunctionLiteral.getLBrace();
        Intrinsics.checkNotNullExpressionValue(lBrace, "getLBrace(...)");
        PsiElement psiElementAddAfter = ktFunctionLiteral.addAfter(ktPsiFactory.createLambdaParameterList("x"), lBrace);
        Intrinsics.checkNotNull(psiElementAddAfter, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtParameterList");
        KtParameterList ktParameterList = (KtParameterList) psiElementAddAfter;
        ktParameterList.removeParameter(0);
        if (ktFunctionLiteral.getArrow() == null) {
            Pair<PsiElement, PsiElement> pairCreateWhitespaceAndArrow = ktPsiFactory.createWhitespaceAndArrow();
            ktFunctionLiteral.addRangeAfter(pairCreateWhitespaceAndArrow.getFirst(), pairCreateWhitespaceAndArrow.getSecond(), ktParameterList);
        }
        return ktParameterList;
    }

    public static final Pair<Name, KtCallExpression> findLabelAndCall(KtFunctionLiteral ktFunctionLiteral) {
        Intrinsics.checkNotNullParameter(ktFunctionLiteral, "<this>");
        PsiElement parent = ktFunctionLiteral.getLightOwner();
        Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtLambdaExpression");
        PsiElement parent2 = ((KtLambdaExpression) parent).getLightOwner();
        if (parent2 instanceof KtLabeledExpression) {
            KtLabeledExpression ktLabeledExpression = (KtLabeledExpression) parent2;
            PsiElement parent3 = ktLabeledExpression.getLightOwner();
            KtValueArgument ktValueArgument = parent3 instanceof KtValueArgument ? (KtValueArgument) parent3 : null;
            return new Pair<>(ktLabeledExpression.getLabelNameAsName(), ktValueArgument != null ? findLabelAndCall$callExpression(ktValueArgument) : null);
        }
        if (parent2 instanceof KtValueArgument) {
            Intrinsics.checkNotNull(parent2);
            KtCallExpression ktCallExpressionFindLabelAndCall$callExpression = findLabelAndCall$callExpression((KtValueArgument) parent2);
            KtExpression calleeExpression = ktCallExpressionFindLabelAndCall$callExpression != null ? ktCallExpressionFindLabelAndCall$callExpression.getCalleeExpression() : null;
            KtSimpleNameExpression ktSimpleNameExpression = calleeExpression instanceof KtSimpleNameExpression ? (KtSimpleNameExpression) calleeExpression : null;
            return new Pair<>(ktSimpleNameExpression != null ? ktSimpleNameExpression.getReferencedNameAsName() : null, ktCallExpressionFindLabelAndCall$callExpression);
        }
        return new Pair<>(null, null);
    }

    private static final KtCallExpression findLabelAndCall$callExpression(KtValueArgument ktValueArgument) {
        PsiElement parent = ktValueArgument.getLightOwner();
        PsiElement parent2 = (parent instanceof KtValueArgumentList ? (KtElementImplStub) parent : ktValueArgument).getLightOwner();
        if (parent2 instanceof KtCallExpression) {
            return (KtCallExpression) parent2;
        }
        return null;
    }

    public static final KtValueArgumentList getOrCreateValueArgumentList(KtCallExpression ktCallExpression) {
        Intrinsics.checkNotNullParameter(ktCallExpression, "<this>");
        KtValueArgumentList valueArgumentList = ktCallExpression.getValueArgumentList();
        if (valueArgumentList != null) {
            return valueArgumentList;
        }
        Project project = ktCallExpression.getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        KtValueArgumentList ktValueArgumentListCreateCallArguments = new KtPsiFactory(project, false, 2, (DefaultConstructorMarker) null).createCallArguments("()");
        KtElement typeArgumentList = ktCallExpression.getTypeArgumentList();
        if (typeArgumentList == null) {
            typeArgumentList = ktCallExpression.getCalleeExpression();
        }
        PsiElement psiElementAddAfter = ktCallExpression.addAfter(ktValueArgumentListCreateCallArguments, typeArgumentList);
        Intrinsics.checkNotNull(psiElementAddAfter, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtValueArgumentList");
        return (KtValueArgumentList) psiElementAddAfter;
    }

    public static final void addTypeArgument(KtCallExpression ktCallExpression, KtTypeProjection typeArgument) {
        Intrinsics.checkNotNullParameter(ktCallExpression, "<this>");
        Intrinsics.checkNotNullParameter(typeArgument, "typeArgument");
        if (ktCallExpression.getTypeArgumentList() != null) {
            KtTypeArgumentList typeArgumentList = ktCallExpression.getTypeArgumentList();
            if (typeArgumentList != null) {
                typeArgumentList.addArgument(typeArgument);
                return;
            }
            return;
        }
        Project project = ktCallExpression.getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        ktCallExpression.addAfter(new KtPsiFactory(project, false, 2, (DefaultConstructorMarker) null).createTypeArguments("<" + typeArgument.getText() + '>'), ktCallExpression.getCalleeExpression());
    }

    public static final boolean hasBody(KtDeclaration ktDeclaration) {
        Intrinsics.checkNotNullParameter(ktDeclaration, "<this>");
        if (ktDeclaration instanceof KtFunction) {
            return ((KtFunction) ktDeclaration).hasBody();
        }
        if (ktDeclaration instanceof KtProperty) {
            return ((KtProperty) ktDeclaration).hasBody();
        }
        return false;
    }

    public static final KtReferenceExpression referenceExpression(KtExpression ktExpression) {
        Intrinsics.checkNotNullParameter(ktExpression, "<this>");
        if (ktExpression instanceof KtCallExpression) {
            ktExpression = ((KtCallExpression) ktExpression).getCalleeExpression();
        }
        if (ktExpression instanceof KtReferenceExpression) {
            return (KtReferenceExpression) ktExpression;
        }
        return null;
    }

    public static final KtLabeledExpression getLabeledParent(KtExpression ktExpression, String labelName) {
        Intrinsics.checkNotNullParameter(ktExpression, "<this>");
        Intrinsics.checkNotNullParameter(labelName, "labelName");
        for (PsiElement psiElement : PsiUtilsKt.getParents(ktExpression)) {
            if (!(psiElement instanceof KtLabeledExpression)) {
                if (!(psiElement instanceof KtParenthesizedExpression ? true : psiElement instanceof KtAnnotatedExpression ? true : psiElement instanceof KtLambdaExpression)) {
                    break;
                }
            } else {
                KtLabeledExpression ktLabeledExpression = (KtLabeledExpression) psiElement;
                if (Intrinsics.areEqual(ktLabeledExpression.getLabelName(), labelName)) {
                    return ktLabeledExpression;
                }
            }
        }
        return null;
    }

    public static final void astReplace(PsiElement psiElement, PsiElement newElement) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        Intrinsics.checkNotNullParameter(newElement, "newElement");
        psiElement.getLightOwner().getNode().replaceChild(psiElement.getNode(), newElement.getNode());
    }

    static {
        Key keyCreate = Key.create("PARENT_SUBSTITUTE");
        Intrinsics.checkNotNullExpressionValue(keyCreate, "create(...)");
        parentSubstitute$delegate = new UserDataProperty(keyCreate);
    }

    public static final PsiElement getParentSubstitute(KtElement ktElement) {
        Intrinsics.checkNotNullParameter(ktElement, "<this>");
        return (PsiElement) parentSubstitute$delegate.getValue(ktElement, $$delegatedProperties[0]);
    }

    public static final void setParentSubstitute(KtElement ktElement, PsiElement psiElement) {
        Intrinsics.checkNotNullParameter(ktElement, "<this>");
        parentSubstitute$delegate.setValue(ktElement, $$delegatedProperties[0], psiElement);
    }

    public static final boolean isIdentifier(String str) {
        if (str == null) {
            return false;
        }
        String str2 = str;
        if (str2.length() == 0) {
            return false;
        }
        KotlinLexer kotlinLexer = new KotlinLexer();
        kotlinLexer.start(str2, 0, str.length());
        if (kotlinLexer.getTokenType() != KtTokens.IDENTIFIER) {
            return false;
        }
        kotlinLexer.advance();
        return kotlinLexer.getTokenType() == null;
    }

    public static final String quoteIfNeeded(String str) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        return isIdentifier(str) ? str : "`" + str + '`';
    }

    public static final boolean isTopLevelKtOrJavaMember(PsiElement psiElement) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        if (psiElement instanceof KtDeclaration) {
            return ((KtDeclaration) psiElement).getLightOwner() instanceof KtFile;
        }
        if (psiElement instanceof PsiClass) {
            PsiClass psiClass = (PsiClass) psiElement;
            if (psiClass.getContainingClass() == null && psiClass.getQualifiedName() != null) {
                return true;
            }
        }
        return false;
    }

    public static final Name safeNameForLazyResolve(KtNamedDeclaration ktNamedDeclaration) {
        Intrinsics.checkNotNullParameter(ktNamedDeclaration, "<this>");
        return safeNameForLazyResolve(ktNamedDeclaration.getNameAsName());
    }

    public static final Name safeNameForLazyResolve(Name name2) {
        return SpecialNames.safeIdentifier(name2);
    }

    public static final FqName safeFqNameForLazyResolve(KtNamedDeclaration ktNamedDeclaration) {
        Intrinsics.checkNotNullParameter(ktNamedDeclaration, "<this>");
        FqName parentFqName = KtNamedDeclarationUtil.getParentFqName(ktNamedDeclaration);
        if (parentFqName != null) {
            return parentFqName.child(safeNameForLazyResolve(ktNamedDeclaration));
        }
        return null;
    }

    public static final boolean isTopLevelInFileOrScript(PsiElement element) {
        Intrinsics.checkNotNullParameter(element, "element");
        PsiElement parent = element.getLightOwner();
        if (parent instanceof KtFile) {
            return true;
        }
        if (parent instanceof KtBlockExpression) {
            return ((KtBlockExpression) parent).getLightOwner() instanceof KtScript;
        }
        return false;
    }

    public static final List<KtDeclaration> getFileOrScriptDeclarations(KtFile ktFile) {
        Intrinsics.checkNotNullParameter(ktFile, "<this>");
        if (!ktFile.isScript()) {
            return ktFile.getDeclarations();
        }
        KtScript script = ktFile.getScript();
        Intrinsics.checkNotNull(script);
        List<KtDeclaration> declarations = script.getDeclarations();
        Intrinsics.checkNotNullExpressionValue(declarations, "getDeclarations(...)");
        return declarations;
    }

    public static final KtBinaryExpressionWithTypeRHS getBinaryWithTypeParent(KtExpression ktExpression) {
        KtCallExpression ktCallExpression;
        Intrinsics.checkNotNullParameter(ktExpression, "<this>");
        PsiElement parent = ktExpression.getLightOwner();
        KtCallExpression ktCallExpression2 = parent instanceof KtCallExpression ? (KtCallExpression) parent : null;
        if (ktCallExpression2 == null) {
            return null;
        }
        PsiElement parent2 = ktCallExpression2.getLightOwner();
        if (parent2 instanceof KtQualifiedExpression) {
            if (!Intrinsics.areEqual(((KtQualifiedExpression) parent2).getSelectorExpression(), ktCallExpression2)) {
                return null;
            }
            ktCallExpression = (KtExpression) parent2;
        } else {
            ktCallExpression = ktCallExpression2;
        }
        PsiElement parent3 = topParenthesizedParentOrMe(ktCallExpression).getLightOwner();
        if (parent3 instanceof KtBinaryExpressionWithTypeRHS) {
            return (KtBinaryExpressionWithTypeRHS) parent3;
        }
        return null;
    }

    public static final KtExpression topParenthesizedParentOrMe(KtExpression ktExpression) {
        Intrinsics.checkNotNullParameter(ktExpression, "<this>");
        while (true) {
            PsiElement parent = ktExpression.getLightOwner();
            if (!Intrinsics.areEqual(KtPsiUtil.deparenthesizeOnce(parent instanceof KtExpression ? (KtExpression) parent : null), ktExpression)) {
                break;
            }
            PsiElement parent2 = ktExpression.getLightOwner();
            KtExpression ktExpression2 = parent2 instanceof KtExpression ? (KtExpression) parent2 : null;
            if (ktExpression2 == null) {
                break;
            }
            ktExpression = ktExpression2;
        }
        return ktExpression;
    }

    public static final PsiElement getTrailingCommaByClosingElement(PsiElement psiElement) {
        PsiElement prevSiblingIgnoringWhitespaceAndComments$default;
        if (psiElement == null || (prevSiblingIgnoringWhitespaceAndComments$default = PsiUtilsKt.getPrevSiblingIgnoringWhitespaceAndComments$default(psiElement, false, 1, null)) == null || !Intrinsics.areEqual(prevSiblingIgnoringWhitespaceAndComments$default.getNode().getElementType(), KtTokens.COMMA)) {
            return null;
        }
        return prevSiblingIgnoringWhitespaceAndComments$default;
    }

    public static final PsiElement getTrailingCommaByElementsList(PsiElement psiElement) {
        PsiElement lastChild;
        if (psiElement == null || (lastChild = psiElement.getLastChild()) == null) {
            lastChild = null;
        } else if (lastChild instanceof PsiComment) {
            lastChild = PsiUtilsKt.getPrevSiblingIgnoringWhitespaceAndComments$default(lastChild, false, 1, null);
        }
        if (lastChild == null || !Intrinsics.areEqual(lastChild.getNode().getElementType(), KtTokens.COMMA)) {
            return null;
        }
        return lastChild;
    }

    public static final boolean isUnderscoreInBackticks(KtNameReferenceExpression ktNameReferenceExpression) {
        Intrinsics.checkNotNullParameter(ktNameReferenceExpression, "<this>");
        return Intrinsics.areEqual(ktNameReferenceExpression.getReferencedName(), "`_`");
    }

    public static final KtTypeElement unwrapNullability(KtTypeElement ktTypeElement) {
        Intrinsics.checkNotNullParameter(ktTypeElement, "<this>");
        while (ktTypeElement instanceof KtNullableType) {
            ktTypeElement = ((KtNullableType) ktTypeElement).getInnerType();
            if (ktTypeElement == null) {
                return null;
            }
        }
        return ktTypeElement;
    }
}
