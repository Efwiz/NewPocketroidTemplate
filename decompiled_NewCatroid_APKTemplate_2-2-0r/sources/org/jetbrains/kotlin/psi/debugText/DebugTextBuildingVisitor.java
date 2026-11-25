package org.jetbrains.kotlin.psi.debugText;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.implementation.Implementation;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.lexer.KtModifierKeywordToken;
import org.jetbrains.kotlin.lexer.KtSingleValueToken;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtAnnotation;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtAnonymousInitializer;
import org.jetbrains.kotlin.psi.KtClass;
import org.jetbrains.kotlin.psi.KtClassBody;
import org.jetbrains.kotlin.psi.KtConstructorCalleeExpression;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtDotQualifiedExpression;
import org.jetbrains.kotlin.psi.KtDynamicType;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtElementImplStub;
import org.jetbrains.kotlin.psi.KtEnumEntry;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtFunctionType;
import org.jetbrains.kotlin.psi.KtImportDirective;
import org.jetbrains.kotlin.psi.KtImportList;
import org.jetbrains.kotlin.psi.KtInitializerList;
import org.jetbrains.kotlin.psi.KtModifierList;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtNullableType;
import org.jetbrains.kotlin.psi.KtObjectDeclaration;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtParameterList;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtPropertyAccessor;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.KtStubbedPsiUtil;
import org.jetbrains.kotlin.psi.KtSuperTypeList;
import org.jetbrains.kotlin.psi.KtSuperTypeListEntry;
import org.jetbrains.kotlin.psi.KtTypeArgumentList;
import org.jetbrains.kotlin.psi.KtTypeConstraint;
import org.jetbrains.kotlin.psi.KtTypeConstraintList;
import org.jetbrains.kotlin.psi.KtTypeParameter;
import org.jetbrains.kotlin.psi.KtTypeParameterList;
import org.jetbrains.kotlin.psi.KtTypeProjection;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.psi.KtUserType;
import org.jetbrains.kotlin.psi.KtVisitor;
import org.jetbrains.kotlin.resolve.ImportPath;

/* JADX INFO: compiled from: DebugTextUtil.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000Î\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\bÂ\u0002\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J%\u0010\b\u001a\u0004\u0018\u00010\u00022\u001b\u0010\t\u001a\u0017\u0012\b\u0012\u00060\u000bj\u0002`\f\u0012\u0004\u0012\u00020\u00030\n¢\u0006\u0002\b\rJ1\u0010\u000e\u001a\u0004\u0018\u00010\u00022\n\u0010\u000f\u001a\u0006\u0012\u0002\b\u00030\u00102\u0016\u0010\u0011\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00130\u0012\"\u0004\u0018\u00010\u0013¢\u0006\u0002\u0010\u0014J0\u0010\u0015\u001a\u0004\u0018\u00010\u00022\n\u0010\u000f\u001a\u0006\u0012\u0002\b\u00030\u00102\u0006\u0010\u0016\u001a\u00020\u00022\b\b\u0002\u0010\u0017\u001a\u00020\u00022\b\b\u0002\u0010\u0018\u001a\u00020\u0002J!\u0010\u0019\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0002\u0010\u001dJ!\u0010\u001e\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u001f\u001a\u00020 2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0002\u0010!J!\u0010\"\u001a\u0004\u0018\u00010\u00022\u0006\u0010#\u001a\u00020$2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0002\u0010%J!\u0010&\u001a\u0004\u0018\u00010\u00022\u0006\u0010'\u001a\u00020(2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0002\u0010)J!\u0010*\u001a\u0004\u0018\u00010\u00022\u0006\u0010+\u001a\u00020,2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0002\u0010-J!\u0010.\u001a\u0004\u0018\u00010\u00022\u0006\u0010/\u001a\u0002002\b\u0010\u001c\u001a\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0002\u00101J!\u00102\u001a\u0004\u0018\u00010\u00022\u0006\u00103\u001a\u0002042\b\u0010\u001c\u001a\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0002\u00105J!\u00106\u001a\u0004\u0018\u00010\u00022\u0006\u00107\u001a\u0002082\b\u0010\u001c\u001a\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0002\u00109J!\u0010:\u001a\u0004\u0018\u00010\u00022\u0006\u0010;\u001a\u00020<2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0002\u0010=J!\u0010>\u001a\u0004\u0018\u00010\u00022\u0006\u0010?\u001a\u00020@2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0002\u0010AJ!\u0010B\u001a\u0004\u0018\u00010\u00022\u0006\u0010C\u001a\u00020D2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0002\u0010EJ!\u0010F\u001a\u0004\u0018\u00010\u00022\u0006\u0010G\u001a\u00020H2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0002\u0010IJ!\u0010J\u001a\u0004\u0018\u00010\u00022\u0006\u0010K\u001a\u00020L2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0002\u0010MJ!\u0010N\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u000f\u001a\u00020\u00132\b\u0010\u001c\u001a\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0002\u0010OJ!\u0010P\u001a\u0004\u0018\u00010\u00022\u0006\u0010Q\u001a\u00020R2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0002\u0010SJ!\u0010T\u001a\u0004\u0018\u00010\u00022\u0006\u0010K\u001a\u00020U2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0002\u0010VJ!\u0010W\u001a\u0004\u0018\u00010\u00022\u0006\u0010X\u001a\u00020Y2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0002\u0010ZJ!\u0010[\u001a\u0004\u0018\u00010\u00022\u0006\u0010\\\u001a\u00020]2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0002\u0010^J!\u0010_\u001a\u0004\u0018\u00010\u00022\u0006\u0010`\u001a\u00020a2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0002\u0010bJ!\u0010c\u001a\u0004\u0018\u00010\u00022\u0006\u0010d\u001a\u00020e2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0002\u0010fJ!\u0010g\u001a\u0004\u0018\u00010\u00022\u0006\u0010K\u001a\u00020h2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0002\u0010iJ!\u0010j\u001a\u0004\u0018\u00010\u00022\u0006\u0010k\u001a\u00020l2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0002\u0010mJ!\u0010n\u001a\u0004\u0018\u00010\u00022\u0006\u0010o\u001a\u00020p2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0002\u0010qJ!\u0010r\u001a\u0004\u0018\u00010\u00022\u0006\u00103\u001a\u00020s2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0002\u0010tJ!\u0010u\u001a\u0004\u0018\u00010\u00022\u0006\u0010K\u001a\u00020v2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0002\u0010wJ!\u0010x\u001a\u0004\u0018\u00010\u00022\u0006\u0010y\u001a\u00020z2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0002\u0010{J!\u0010|\u001a\u0004\u0018\u00010\u00022\u0006\u0010}\u001a\u00020~2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0002\u0010\u007fJ%\u0010\u0080\u0001\u001a\u0004\u0018\u00010\u00022\b\u0010\u0081\u0001\u001a\u00030\u0082\u00012\b\u0010\u001c\u001a\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0003\u0010\u0083\u0001J$\u0010\u0084\u0001\u001a\u0004\u0018\u00010\u00022\u0007\u0010K\u001a\u00030\u0085\u00012\b\u0010\u001c\u001a\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0003\u0010\u0086\u0001J$\u0010\u0087\u0001\u001a\u0004\u0018\u00010\u00022\u0007\u0010d\u001a\u00030\u0088\u00012\b\u0010\u001c\u001a\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0003\u0010\u0089\u0001J$\u0010\u008a\u0001\u001a\u0004\u0018\u00010\u00022\u0007\u0010K\u001a\u00030\u008b\u00012\b\u0010\u001c\u001a\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0003\u0010\u008c\u0001J%\u0010\u008d\u0001\u001a\u0004\u0018\u00010\u00022\b\u0010\u008e\u0001\u001a\u00030\u008f\u00012\b\u0010\u001c\u001a\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0003\u0010\u0090\u0001J%\u0010\u0091\u0001\u001a\u0004\u0018\u00010\u00022\b\u0010\u0092\u0001\u001a\u00030\u0093\u00012\b\u0010\u001c\u001a\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0003\u0010\u0094\u0001J%\u0010\u0095\u0001\u001a\u0004\u0018\u00010\u00022\b\u0010\u0096\u0001\u001a\u00030\u0097\u00012\b\u0010\u001c\u001a\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0003\u0010\u0098\u0001R\u0013\u0010\u0005\u001a\u00070\u0006¢\u0006\u0002\b\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0099\u0001"}, d2 = {"Lorg/jetbrains/kotlin/psi/debugText/DebugTextBuildingVisitor;", "Lorg/jetbrains/kotlin/psi/KtVisitor;", "", "", "()V", "LOG", "Lorg/jetbrains/kotlin/com/intellij/openapi/diagnostic/Logger;", "Lorg/jetbrains/annotations/NotNull;", "buildText", "body", "Lkotlin/Function1;", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "Lkotlin/ExtensionFunctionType;", "render", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/psi/KtElementImplStub;", "relevantChildren", "", "Lorg/jetbrains/kotlin/psi/KtElement;", "(Lorg/jetbrains/kotlin/psi/KtElementImplStub;[Lorg/jetbrains/kotlin/psi/KtElement;)Ljava/lang/String;", "renderChildren", "separator", "prefix", "postfix", "visitAnnotation", "annotation", "Lorg/jetbrains/kotlin/psi/KtAnnotation;", "data", "(Lorg/jetbrains/kotlin/psi/KtAnnotation;Lkotlin/Unit;)Ljava/lang/String;", "visitAnnotationEntry", "annotationEntry", "Lorg/jetbrains/kotlin/psi/KtAnnotationEntry;", "(Lorg/jetbrains/kotlin/psi/KtAnnotationEntry;Lkotlin/Unit;)Ljava/lang/String;", "visitAnonymousInitializer", "initializer", "Lorg/jetbrains/kotlin/psi/KtAnonymousInitializer;", "(Lorg/jetbrains/kotlin/psi/KtAnonymousInitializer;Lkotlin/Unit;)Ljava/lang/String;", "visitClass", "klass", "Lorg/jetbrains/kotlin/psi/KtClass;", "(Lorg/jetbrains/kotlin/psi/KtClass;Lkotlin/Unit;)Ljava/lang/String;", "visitClassBody", "classBody", "Lorg/jetbrains/kotlin/psi/KtClassBody;", "(Lorg/jetbrains/kotlin/psi/KtClassBody;Lkotlin/Unit;)Ljava/lang/String;", "visitConstructorCalleeExpression", "constructorCalleeExpression", "Lorg/jetbrains/kotlin/psi/KtConstructorCalleeExpression;", "(Lorg/jetbrains/kotlin/psi/KtConstructorCalleeExpression;Lkotlin/Unit;)Ljava/lang/String;", "visitDotQualifiedExpression", "expression", "Lorg/jetbrains/kotlin/psi/KtDotQualifiedExpression;", "(Lorg/jetbrains/kotlin/psi/KtDotQualifiedExpression;Lkotlin/Unit;)Ljava/lang/String;", "visitDynamicType", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/psi/KtDynamicType;", "(Lorg/jetbrains/kotlin/psi/KtDynamicType;Lkotlin/Unit;)Ljava/lang/String;", "visitEnumEntry", "enumEntry", "Lorg/jetbrains/kotlin/psi/KtEnumEntry;", "(Lorg/jetbrains/kotlin/psi/KtEnumEntry;Lkotlin/Unit;)Ljava/lang/String;", "visitFunctionType", "functionType", "Lorg/jetbrains/kotlin/psi/KtFunctionType;", "(Lorg/jetbrains/kotlin/psi/KtFunctionType;Lkotlin/Unit;)Ljava/lang/String;", "visitImportDirective", "importDirective", "Lorg/jetbrains/kotlin/psi/KtImportDirective;", "(Lorg/jetbrains/kotlin/psi/KtImportDirective;Lkotlin/Unit;)Ljava/lang/String;", "visitImportList", "importList", "Lorg/jetbrains/kotlin/psi/KtImportList;", "(Lorg/jetbrains/kotlin/psi/KtImportList;Lkotlin/Unit;)Ljava/lang/String;", "visitInitializerList", SchemaSymbols.ATTVAL_LIST, "Lorg/jetbrains/kotlin/psi/KtInitializerList;", "(Lorg/jetbrains/kotlin/psi/KtInitializerList;Lkotlin/Unit;)Ljava/lang/String;", "visitKtElement", "(Lorg/jetbrains/kotlin/psi/KtElement;Lkotlin/Unit;)Ljava/lang/String;", "visitKtFile", "file", "Lorg/jetbrains/kotlin/psi/KtFile;", "(Lorg/jetbrains/kotlin/psi/KtFile;Lkotlin/Unit;)Ljava/lang/String;", "visitModifierList", "Lorg/jetbrains/kotlin/psi/KtModifierList;", "(Lorg/jetbrains/kotlin/psi/KtModifierList;Lkotlin/Unit;)Ljava/lang/String;", "visitNamedFunction", "function", "Lorg/jetbrains/kotlin/psi/KtNamedFunction;", "(Lorg/jetbrains/kotlin/psi/KtNamedFunction;Lkotlin/Unit;)Ljava/lang/String;", "visitNullableType", "nullableType", "Lorg/jetbrains/kotlin/psi/KtNullableType;", "(Lorg/jetbrains/kotlin/psi/KtNullableType;Lkotlin/Unit;)Ljava/lang/String;", "visitObjectDeclaration", "declaration", "Lorg/jetbrains/kotlin/psi/KtObjectDeclaration;", "(Lorg/jetbrains/kotlin/psi/KtObjectDeclaration;Lkotlin/Unit;)Ljava/lang/String;", "visitParameter", "parameter", "Lorg/jetbrains/kotlin/psi/KtParameter;", "(Lorg/jetbrains/kotlin/psi/KtParameter;Lkotlin/Unit;)Ljava/lang/String;", "visitParameterList", "Lorg/jetbrains/kotlin/psi/KtParameterList;", "(Lorg/jetbrains/kotlin/psi/KtParameterList;Lkotlin/Unit;)Ljava/lang/String;", "visitProperty", "property", "Lorg/jetbrains/kotlin/psi/KtProperty;", "(Lorg/jetbrains/kotlin/psi/KtProperty;Lkotlin/Unit;)Ljava/lang/String;", "visitPropertyAccessor", Implementation.Context.Default.ACCESSOR_METHOD_SUFFIX, "Lorg/jetbrains/kotlin/psi/KtPropertyAccessor;", "(Lorg/jetbrains/kotlin/psi/KtPropertyAccessor;Lkotlin/Unit;)Ljava/lang/String;", "visitSimpleNameExpression", "Lorg/jetbrains/kotlin/psi/KtSimpleNameExpression;", "(Lorg/jetbrains/kotlin/psi/KtSimpleNameExpression;Lkotlin/Unit;)Ljava/lang/String;", "visitSuperTypeList", "Lorg/jetbrains/kotlin/psi/KtSuperTypeList;", "(Lorg/jetbrains/kotlin/psi/KtSuperTypeList;Lkotlin/Unit;)Ljava/lang/String;", "visitSuperTypeListEntry", "specifier", "Lorg/jetbrains/kotlin/psi/KtSuperTypeListEntry;", "(Lorg/jetbrains/kotlin/psi/KtSuperTypeListEntry;Lkotlin/Unit;)Ljava/lang/String;", "visitTypeArgumentList", "typeArgumentList", "Lorg/jetbrains/kotlin/psi/KtTypeArgumentList;", "(Lorg/jetbrains/kotlin/psi/KtTypeArgumentList;Lkotlin/Unit;)Ljava/lang/String;", "visitTypeConstraint", "constraint", "Lorg/jetbrains/kotlin/psi/KtTypeConstraint;", "(Lorg/jetbrains/kotlin/psi/KtTypeConstraint;Lkotlin/Unit;)Ljava/lang/String;", "visitTypeConstraintList", "Lorg/jetbrains/kotlin/psi/KtTypeConstraintList;", "(Lorg/jetbrains/kotlin/psi/KtTypeConstraintList;Lkotlin/Unit;)Ljava/lang/String;", "visitTypeParameter", "Lorg/jetbrains/kotlin/psi/KtTypeParameter;", "(Lorg/jetbrains/kotlin/psi/KtTypeParameter;Lkotlin/Unit;)Ljava/lang/String;", "visitTypeParameterList", "Lorg/jetbrains/kotlin/psi/KtTypeParameterList;", "(Lorg/jetbrains/kotlin/psi/KtTypeParameterList;Lkotlin/Unit;)Ljava/lang/String;", "visitTypeProjection", "typeProjection", "Lorg/jetbrains/kotlin/psi/KtTypeProjection;", "(Lorg/jetbrains/kotlin/psi/KtTypeProjection;Lkotlin/Unit;)Ljava/lang/String;", "visitTypeReference", "typeReference", "Lorg/jetbrains/kotlin/psi/KtTypeReference;", "(Lorg/jetbrains/kotlin/psi/KtTypeReference;Lkotlin/Unit;)Ljava/lang/String;", "visitUserType", "userType", "Lorg/jetbrains/kotlin/psi/KtUserType;", "(Lorg/jetbrains/kotlin/psi/KtUserType;Lkotlin/Unit;)Ljava/lang/String;", "frontend.common-psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class DebugTextBuildingVisitor extends KtVisitor<String, Unit> {
    public static final DebugTextBuildingVisitor INSTANCE;
    private static final Logger LOG;

    private DebugTextBuildingVisitor() {
    }

    static {
        DebugTextBuildingVisitor debugTextBuildingVisitor = new DebugTextBuildingVisitor();
        INSTANCE = debugTextBuildingVisitor;
        Logger logger = Logger.getInstance(debugTextBuildingVisitor.getClass());
        Intrinsics.checkNotNullExpressionValue(logger, "getInstance(...)");
        LOG = logger;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public String visitKtFile(KtFile file, Unit data) {
        Intrinsics.checkNotNullParameter(file, "file");
        return "STUB file: " + file.get$name();
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public String visitKtElement(KtElement element, Unit data) {
        Intrinsics.checkNotNullParameter(element, "element");
        if (element instanceof KtElementImplStub) {
            LOG.error("getDebugText() is not defined for " + element.getClass());
        }
        return element.getText();
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public String visitImportDirective(KtImportDirective importDirective, Unit data) {
        String string;
        Intrinsics.checkNotNullParameter(importDirective, "importDirective");
        ImportPath importPath = importDirective.getImportPath();
        if (importPath == null) {
            return "import <invalid>";
        }
        if (importPath.hasAlias()) {
            StringBuilder sb = new StringBuilder(" as ");
            Name alias = importPath.getAlias();
            Intrinsics.checkNotNull(alias);
            string = sb.append(alias.asString()).toString();
        } else {
            string = "";
        }
        return "import " + importPath.getPathStr() + string;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public String visitImportList(KtImportList importList, Unit data) {
        Intrinsics.checkNotNullParameter(importList, "importList");
        return renderChildren$default(this, importList, "\n", null, null, 12, null);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public String visitAnnotationEntry(KtAnnotationEntry annotationEntry, Unit data) {
        Intrinsics.checkNotNullParameter(annotationEntry, "annotationEntry");
        return render(annotationEntry, annotationEntry.getCalleeExpression(), annotationEntry.getTypeArgumentList());
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public String visitTypeReference(KtTypeReference typeReference, Unit data) {
        Intrinsics.checkNotNullParameter(typeReference, "typeReference");
        return renderChildren$default(this, typeReference, " ", null, null, 12, null);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public String visitTypeArgumentList(KtTypeArgumentList typeArgumentList, Unit data) {
        Intrinsics.checkNotNullParameter(typeArgumentList, "typeArgumentList");
        return renderChildren(typeArgumentList, ", ", "<", ">");
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public String visitTypeConstraintList(KtTypeConstraintList list, Unit data) {
        Intrinsics.checkNotNullParameter(list, "list");
        return renderChildren(list, ", ", "where ", "");
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public String visitUserType(KtUserType userType, Unit data) {
        Intrinsics.checkNotNullParameter(userType, "userType");
        return render(userType, userType.getQualifier(), userType.getReferenceExpression(), userType.getTypeArgumentList());
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public String visitDynamicType(KtDynamicType type, Unit data) {
        Intrinsics.checkNotNullParameter(type, "type");
        return "dynamic";
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public String visitAnnotation(KtAnnotation annotation, Unit data) {
        Intrinsics.checkNotNullParameter(annotation, "annotation");
        return renderChildren(annotation, " ", "[", "]");
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public String visitConstructorCalleeExpression(KtConstructorCalleeExpression constructorCalleeExpression, Unit data) {
        Intrinsics.checkNotNullParameter(constructorCalleeExpression, "constructorCalleeExpression");
        return render(constructorCalleeExpression, constructorCalleeExpression.getConstructorReferenceExpression());
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public String visitSuperTypeListEntry(KtSuperTypeListEntry specifier, Unit data) {
        Intrinsics.checkNotNullParameter(specifier, "specifier");
        return render(specifier, specifier.getTypeReference());
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public String visitSuperTypeList(KtSuperTypeList list, Unit data) {
        Intrinsics.checkNotNullParameter(list, "list");
        return renderChildren$default(this, list, ", ", null, null, 12, null);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public String visitTypeParameterList(KtTypeParameterList list, Unit data) {
        Intrinsics.checkNotNullParameter(list, "list");
        return renderChildren(list, ", ", "<", ">");
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public String visitDotQualifiedExpression(KtDotQualifiedExpression expression, Unit data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return renderChildren$default(this, expression, ".", null, null, 12, null);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public String visitInitializerList(KtInitializerList list, Unit data) {
        Intrinsics.checkNotNullParameter(list, "list");
        return renderChildren$default(this, list, ", ", null, null, 12, null);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public String visitParameterList(KtParameterList list, Unit data) {
        Intrinsics.checkNotNullParameter(list, "list");
        return renderChildren(list, ", ", "(", Constant.AFTER_QUTO);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public String visitEnumEntry(final KtEnumEntry enumEntry, Unit data) {
        Intrinsics.checkNotNullParameter(enumEntry, "enumEntry");
        return buildText(new Function1<StringBuilder, Unit>() { // from class: org.jetbrains.kotlin.psi.debugText.DebugTextBuildingVisitor.visitEnumEntry.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(StringBuilder sb) {
                invoke2(sb);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(StringBuilder buildText) {
                Intrinsics.checkNotNullParameter(buildText, "$this$buildText");
                buildText.append("STUB: ");
                DebugTextUtilKt.appendInn$default(buildText, enumEntry.getModifierList(), null, " ", 2, null);
                buildText.append("enum entry ");
                DebugTextUtilKt.appendInn$default(buildText, enumEntry.getNameAsName(), null, null, 6, null);
                DebugTextUtilKt.appendInn$default(buildText, enumEntry.getInitializerList(), " : ", null, 4, null);
            }
        });
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public String visitFunctionType(final KtFunctionType functionType, Unit data) {
        Intrinsics.checkNotNullParameter(functionType, "functionType");
        return buildText(new Function1<StringBuilder, Unit>() { // from class: org.jetbrains.kotlin.psi.debugText.DebugTextBuildingVisitor.visitFunctionType.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(StringBuilder sb) {
                invoke2(sb);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(StringBuilder buildText) {
                Intrinsics.checkNotNullParameter(buildText, "$this$buildText");
                DebugTextUtilKt.appendInn$default(buildText, functionType.getReceiverTypeReference(), null, ".", 2, null);
                DebugTextUtilKt.appendInn$default(buildText, functionType.getParameterList(), null, null, 6, null);
                DebugTextUtilKt.appendInn$default(buildText, functionType.getReturnTypeReference(), " -> ", null, 4, null);
            }
        });
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public String visitTypeParameter(final KtTypeParameter parameter, Unit data) {
        Intrinsics.checkNotNullParameter(parameter, "parameter");
        return buildText(new Function1<StringBuilder, Unit>() { // from class: org.jetbrains.kotlin.psi.debugText.DebugTextBuildingVisitor.visitTypeParameter.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(StringBuilder sb) {
                invoke2(sb);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(StringBuilder buildText) {
                Intrinsics.checkNotNullParameter(buildText, "$this$buildText");
                DebugTextUtilKt.appendInn$default(buildText, parameter.getModifierList(), null, " ", 2, null);
                DebugTextUtilKt.appendInn$default(buildText, parameter.getNameAsName(), null, null, 6, null);
                DebugTextUtilKt.appendInn$default(buildText, parameter.getExtendsBound(), " : ", null, 4, null);
            }
        });
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public String visitTypeProjection(final KtTypeProjection typeProjection, Unit data) {
        Intrinsics.checkNotNullParameter(typeProjection, "typeProjection");
        return buildText(new Function1<StringBuilder, Unit>() { // from class: org.jetbrains.kotlin.psi.debugText.DebugTextBuildingVisitor.visitTypeProjection.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(StringBuilder sb) {
                invoke2(sb);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(StringBuilder buildText) {
                Intrinsics.checkNotNullParameter(buildText, "$this$buildText");
                KtSingleValueToken token = typeProjection.getProjectionKind().getToken();
                DebugTextUtilKt.appendInn$default(buildText, token != null ? token.getValue() : null, null, null, 6, null);
                KtTypeReference typeReference = typeProjection.getTypeReference();
                if (token != null && typeReference != null) {
                    buildText.append(" ");
                }
                DebugTextUtilKt.appendInn$default(buildText, typeReference, null, null, 6, null);
            }
        });
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public String visitModifierList(final KtModifierList list, Unit data) {
        Intrinsics.checkNotNullParameter(list, "list");
        return buildText(new Function1<StringBuilder, Unit>() { // from class: org.jetbrains.kotlin.psi.debugText.DebugTextBuildingVisitor.visitModifierList.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(StringBuilder sb) {
                invoke2(sb);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(StringBuilder buildText) {
                Intrinsics.checkNotNullParameter(buildText, "$this$buildText");
                KtModifierKeywordToken[] MODIFIER_KEYWORDS_ARRAY = KtTokens.MODIFIER_KEYWORDS_ARRAY;
                Intrinsics.checkNotNullExpressionValue(MODIFIER_KEYWORDS_ARRAY, "MODIFIER_KEYWORDS_ARRAY");
                boolean z = true;
                for (KtModifierKeywordToken ktModifierKeywordToken : MODIFIER_KEYWORDS_ARRAY) {
                    if (list.hasModifier(ktModifierKeywordToken)) {
                        if (!z) {
                            buildText.append(" ");
                        }
                        buildText.append(ktModifierKeywordToken.getValue());
                        z = false;
                    }
                }
            }
        });
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public String visitSimpleNameExpression(KtSimpleNameExpression expression, Unit data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return expression.getReferencedName();
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public String visitNullableType(KtNullableType nullableType, Unit data) {
        Intrinsics.checkNotNullParameter(nullableType, "nullableType");
        return renderChildren(nullableType, "", "", TypeDescription.Generic.OfWildcardType.SYMBOL);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public String visitAnonymousInitializer(KtAnonymousInitializer initializer, Unit data) {
        String debugText;
        Intrinsics.checkNotNullParameter(initializer, "initializer");
        KtDeclaration containingDeclaration = KtStubbedPsiUtil.getContainingDeclaration(initializer);
        StringBuilder sb = new StringBuilder("initializer in ");
        if (containingDeclaration == null || (debugText = DebugTextUtilKt.getDebugText(containingDeclaration)) == null) {
            debugText = "...";
        }
        return sb.append(debugText).toString();
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public String visitClassBody(KtClassBody classBody, Unit data) {
        String debugText;
        Intrinsics.checkNotNullParameter(classBody, "classBody");
        KtDeclaration containingDeclaration = KtStubbedPsiUtil.getContainingDeclaration(classBody);
        StringBuilder sb = new StringBuilder("class body for ");
        if (containingDeclaration == null || (debugText = DebugTextUtilKt.getDebugText(containingDeclaration)) == null) {
            debugText = "...";
        }
        return sb.append(debugText).toString();
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public String visitPropertyAccessor(KtPropertyAccessor accessor, Unit data) {
        String debugText;
        Intrinsics.checkNotNullParameter(accessor, "accessor");
        KtProperty ktProperty = (KtProperty) KtStubbedPsiUtil.getContainingDeclaration(accessor, KtProperty.class);
        StringBuilder sbAppend = new StringBuilder().append(accessor.isGetter() ? "getter" : "setter").append(" for ");
        if (ktProperty == null || (debugText = DebugTextUtilKt.getDebugText(ktProperty)) == null) {
            debugText = "...";
        }
        return sbAppend.append(debugText).toString();
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public String visitClass(final KtClass klass, Unit data) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        return buildText(new Function1<StringBuilder, Unit>() { // from class: org.jetbrains.kotlin.psi.debugText.DebugTextBuildingVisitor.visitClass.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(StringBuilder sb) {
                invoke2(sb);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(StringBuilder buildText) {
                Intrinsics.checkNotNullParameter(buildText, "$this$buildText");
                buildText.append("STUB: ");
                DebugTextUtilKt.appendInn$default(buildText, klass.getModifierList(), null, " ", 2, null);
                buildText.append(ExternalAnnotationProvider.CLASS_PREFIX);
                DebugTextUtilKt.appendInn$default(buildText, klass.getNameAsName(), null, null, 6, null);
                DebugTextUtilKt.appendInn$default(buildText, klass.mo7395getTypeParameterList(), null, null, 6, null);
                DebugTextUtilKt.appendInn(buildText, klass.getPrimaryConstructorModifierList(), " ", " ");
                DebugTextUtilKt.appendInn$default(buildText, klass.getPrimaryConstructorParameterList(), null, null, 6, null);
                DebugTextUtilKt.appendInn$default(buildText, klass.getSuperTypeList(), " : ", null, 4, null);
            }
        });
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public String visitNamedFunction(final KtNamedFunction function, Unit data) {
        Intrinsics.checkNotNullParameter(function, "function");
        return buildText(new Function1<StringBuilder, Unit>() { // from class: org.jetbrains.kotlin.psi.debugText.DebugTextBuildingVisitor.visitNamedFunction.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(StringBuilder sb) {
                invoke2(sb);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(StringBuilder buildText) {
                Intrinsics.checkNotNullParameter(buildText, "$this$buildText");
                buildText.append("STUB: ");
                DebugTextUtilKt.appendInn$default(buildText, function.getModifierList(), null, " ", 2, null);
                buildText.append("fun ");
                KtTypeParameterList typeParameterList = function.mo7395getTypeParameterList();
                if (function.hasTypeParameterListBeforeFunctionName()) {
                    DebugTextUtilKt.appendInn$default(buildText, typeParameterList, null, " ", 2, null);
                }
                DebugTextUtilKt.appendInn$default(buildText, function.mo7393getReceiverTypeReference(), null, ".", 2, null);
                DebugTextUtilKt.appendInn$default(buildText, function.getNameAsName(), null, null, 6, null);
                if (!function.hasTypeParameterListBeforeFunctionName()) {
                    DebugTextUtilKt.appendInn$default(buildText, typeParameterList, null, null, 6, null);
                }
                DebugTextUtilKt.appendInn$default(buildText, function.getValueParameterList(), null, null, 6, null);
                DebugTextUtilKt.appendInn$default(buildText, function.mo7396getTypeReference(), ": ", null, 4, null);
                DebugTextUtilKt.appendInn$default(buildText, function.mo7394getTypeConstraintList(), " ", null, 4, null);
            }
        });
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public String visitObjectDeclaration(final KtObjectDeclaration declaration, Unit data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return buildText(new Function1<StringBuilder, Unit>() { // from class: org.jetbrains.kotlin.psi.debugText.DebugTextBuildingVisitor.visitObjectDeclaration.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(StringBuilder sb) {
                invoke2(sb);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(StringBuilder buildText) {
                Intrinsics.checkNotNullParameter(buildText, "$this$buildText");
                buildText.append("STUB: ");
                DebugTextUtilKt.appendInn$default(buildText, declaration.getModifierList(), null, " ", 2, null);
                buildText.append("object ");
                DebugTextUtilKt.appendInn$default(buildText, declaration.getNameAsName(), null, null, 6, null);
                DebugTextUtilKt.appendInn$default(buildText, declaration.getSuperTypeList(), " : ", null, 4, null);
            }
        });
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public String visitParameter(final KtParameter parameter, Unit data) {
        Intrinsics.checkNotNullParameter(parameter, "parameter");
        return buildText(new Function1<StringBuilder, Unit>() { // from class: org.jetbrains.kotlin.psi.debugText.DebugTextBuildingVisitor.visitParameter.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(StringBuilder sb) {
                invoke2(sb);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(StringBuilder buildText) {
                Intrinsics.checkNotNullParameter(buildText, "$this$buildText");
                if (parameter.hasValOrVar()) {
                    buildText.append(parameter.isMutable() ? "var " : "val ");
                }
                Name nameAsName = parameter.getNameAsName();
                DebugTextUtilKt.appendInn$default(buildText, nameAsName, null, null, 6, null);
                KtTypeReference ktTypeReferenceMo7396getTypeReference = parameter.mo7396getTypeReference();
                if (ktTypeReferenceMo7396getTypeReference != null && nameAsName != null) {
                    buildText.append(": ");
                }
                DebugTextUtilKt.appendInn$default(buildText, ktTypeReferenceMo7396getTypeReference, null, null, 6, null);
            }
        });
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public String visitProperty(final KtProperty property, Unit data) {
        Intrinsics.checkNotNullParameter(property, "property");
        return buildText(new Function1<StringBuilder, Unit>() { // from class: org.jetbrains.kotlin.psi.debugText.DebugTextBuildingVisitor.visitProperty.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(StringBuilder sb) {
                invoke2(sb);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(StringBuilder buildText) {
                Intrinsics.checkNotNullParameter(buildText, "$this$buildText");
                buildText.append("STUB: ");
                DebugTextUtilKt.appendInn$default(buildText, property.getModifierList(), null, " ", 2, null);
                buildText.append(property.isVar() ? "var " : "val ");
                DebugTextUtilKt.appendInn$default(buildText, property.getNameAsName(), null, null, 6, null);
                DebugTextUtilKt.appendInn$default(buildText, property.mo7396getTypeReference(), ": ", null, 4, null);
            }
        });
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public String visitTypeConstraint(final KtTypeConstraint constraint, Unit data) {
        Intrinsics.checkNotNullParameter(constraint, "constraint");
        return buildText(new Function1<StringBuilder, Unit>() { // from class: org.jetbrains.kotlin.psi.debugText.DebugTextBuildingVisitor.visitTypeConstraint.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(StringBuilder sb) {
                invoke2(sb);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(StringBuilder buildText) {
                Intrinsics.checkNotNullParameter(buildText, "$this$buildText");
                DebugTextUtilKt.appendInn$default(buildText, constraint.getSubjectTypeParameterName(), null, null, 6, null);
                DebugTextUtilKt.appendInn$default(buildText, constraint.getBoundTypeReference(), " : ", null, 4, null);
            }
        });
    }

    public final String buildText(Function1<? super StringBuilder, Unit> body) {
        Intrinsics.checkNotNullParameter(body, "body");
        StringBuilder sb = new StringBuilder();
        body.invoke(sb);
        return sb.toString();
    }

    public static /* synthetic */ String renderChildren$default(DebugTextBuildingVisitor debugTextBuildingVisitor, KtElementImplStub ktElementImplStub, String str, String str2, String str3, int i, Object obj) {
        if ((i & 4) != 0) {
            str2 = "";
        }
        if ((i & 8) != 0) {
            str3 = "";
        }
        return debugTextBuildingVisitor.renderChildren(ktElementImplStub, str, str2, str3);
    }

    /* JADX WARN: Type inference failed for: r0v4, types: [org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement] */
    public final String renderChildren(KtElementImplStub<?> element, String separator, String prefix, String postfix) {
        String strJoinToString$default;
        List<StubElement> childrenStubs;
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(separator, "separator");
        Intrinsics.checkNotNullParameter(prefix, "prefix");
        Intrinsics.checkNotNullParameter(postfix, "postfix");
        ?? stub = element.getStub();
        ArrayList arrayList = null;
        if (stub != 0 && (childrenStubs = stub.getChildrenStubs()) != null) {
            ArrayList arrayList2 = new ArrayList();
            for (StubElement stubElement : childrenStubs) {
                PsiElement psi = stubElement != null ? stubElement.getPsi() : null;
                KtElement ktElement = psi instanceof KtElement ? (KtElement) psi : null;
                String debugText = ktElement != null ? DebugTextUtilKt.getDebugText(ktElement) : null;
                if (debugText != null) {
                    arrayList2.add(debugText);
                }
            }
            arrayList = arrayList2;
        }
        return (arrayList == null || (strJoinToString$default = CollectionsKt.joinToString$default(arrayList, separator, prefix, postfix, 0, null, null, 56, null)) == null) ? element.getText() : strJoinToString$default;
    }

    public final String render(KtElementImplStub<?> element, KtElement... relevantChildren) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(relevantChildren, "relevantChildren");
        return element.getStub() == null ? element.getText() : CollectionsKt.joinToString$default(ArraysKt.filterNotNull(relevantChildren), "", "", "", 0, null, new Function1<KtElement, CharSequence>() { // from class: org.jetbrains.kotlin.psi.debugText.DebugTextBuildingVisitor.render.1
            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(KtElement it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return DebugTextUtilKt.getDebugText(it);
            }
        }, 24, null);
    }
}
