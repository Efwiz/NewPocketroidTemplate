package org.jetbrains.kotlin.resolve.constants.evaluate;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.builtins.UnsignedTypes;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.google.common.collect.ImmutableBiMap;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.TypeConversionUtil;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ConstUtilKt;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorUtilKt;
import org.jetbrains.kotlin.descriptors.FindClassInModuleKt;
import org.jetbrains.kotlin.descriptors.InlineClassRepresentation;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptorImpl;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory0;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.incremental.components.InlineConstTracker;
import org.jetbrains.kotlin.lexer.KtSingleValueToken;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.parsing.ParseUtilsKt;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.psi.KtBinaryExpressionWithTypeRHS;
import org.jetbrains.kotlin.psi.KtCallExpression;
import org.jetbrains.kotlin.psi.KtClassLiteralExpression;
import org.jetbrains.kotlin.psi.KtCollectionLiteralExpression;
import org.jetbrains.kotlin.psi.KtConstantExpression;
import org.jetbrains.kotlin.psi.KtContainerNode;
import org.jetbrains.kotlin.psi.KtDoWhileExpression;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtLabeledExpression;
import org.jetbrains.kotlin.psi.KtOperationReferenceExpression;
import org.jetbrains.kotlin.psi.KtParenthesizedExpression;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.psi.KtQualifiedExpression;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.KtStringTemplateEntry;
import org.jetbrains.kotlin.psi.KtStringTemplateExpression;
import org.jetbrains.kotlin.psi.KtUnaryExpression;
import org.jetbrains.kotlin.psi.KtVisitor;
import org.jetbrains.kotlin.psi.KtWhenConditionWithExpression;
import org.jetbrains.kotlin.psi.KtWhileExpression;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.CompileTimeConstantUtils;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.ImplicitIntegerCoercion;
import org.jetbrains.kotlin.resolve.InlineClassesUtilsKt;
import org.jetbrains.kotlin.resolve.SinceKotlinAccessibility;
import org.jetbrains.kotlin.resolve.SinceKotlinUtilKt;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedValueArgument;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.constants.AnnotationValue;
import org.jetbrains.kotlin.resolve.constants.ArrayValue;
import org.jetbrains.kotlin.resolve.constants.CompileTimeConstant;
import org.jetbrains.kotlin.resolve.constants.CompileTimeConstantChecker;
import org.jetbrains.kotlin.resolve.constants.CompileTimeConstantKt;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.constants.ConstantValueFactory;
import org.jetbrains.kotlin.resolve.constants.ConstantValueFactoryKt;
import org.jetbrains.kotlin.resolve.constants.EnumValue;
import org.jetbrains.kotlin.resolve.constants.ErrorValue;
import org.jetbrains.kotlin.resolve.constants.IntValue;
import org.jetbrains.kotlin.resolve.constants.IntegerValueTypeConstant;
import org.jetbrains.kotlin.resolve.constants.KClassValue;
import org.jetbrains.kotlin.resolve.constants.LongValue;
import org.jetbrains.kotlin.resolve.constants.NullValue;
import org.jetbrains.kotlin.resolve.constants.TypedCompileTimeConstant;
import org.jetbrains.kotlin.resolve.constants.UIntValue;
import org.jetbrains.kotlin.resolve.constants.ULongValue;
import org.jetbrains.kotlin.resolve.constants.UnsignedErrorValueTypeConstant;
import org.jetbrains.kotlin.resolve.constants.UnsignedValueConstant;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeKt;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.expressions.OperatorConventions;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: ConstantExpressionEvaluator.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0091\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t*\u0001\u0010\b\u0002\u0018\u00002\u0014\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u0002\u0012\u0004\u0012\u00020\u00030\u0001:\u0001uB\u0015\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\b\u0010\u0016\u001a\u00020\u0013H\u0002J(\u0010\u0017\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00022\b\u0010\u0018\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u0003H\u0002J*\u0010\u001d\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00022\b\u0010\u0018\u001a\u0004\u0018\u00010\u00192\b\u0010\u001c\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u001a\u001a\u00020\u001bH\u0002J&\u0010\u001e\u001a\u0014\u0012\u000e\u0012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030!0 \u0018\u00010\u001f2\n\u0010\"\u001a\u0006\u0012\u0002\b\u00030#H\u0002J0\u0010$\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u001f2\n\u0010\"\u001a\u0006\u0012\u0002\b\u00030#2\u0006\u0010%\u001a\u00020&2\f\u0010'\u001a\b\u0012\u0004\u0012\u00020)0(H\u0002J$\u0010*\u001a\u0006\u0012\u0002\b\u00030\u00022\u0006\u0010\u0018\u001a\u00020+2\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u0003H\u0002J\"\u0010,\u001a\u0004\u0018\u00010-2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010.\u001a\u00020\u00032\u0006\u0010/\u001a\u000200H\u0002J\u001a\u00101\u001a\u0004\u0018\u00010-2\u0006\u00102\u001a\u0002032\u0006\u00104\u001a\u000205H\u0002J\u001e\u00106\u001a\u0004\u0018\u00010-2\n\u00107\u001a\u0006\u0012\u0002\b\u00030#2\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u001e\u00108\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00022\u0006\u0010\u0014\u001a\u00020\u00152\b\u0010\u001c\u001a\u0004\u0018\u00010\u0003J*\u00109\u001a\u0004\u0018\u00010\u00192\u0006\u0010:\u001a\u00020-2\u0006\u00104\u001a\u00020-2\u0006\u0010;\u001a\u00020<2\u0006\u0010=\u001a\u00020\u0015H\u0002J(\u0010>\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00022\u0006\u0010=\u001a\u00020\u00152\u0006\u0010?\u001a\u00020\u00152\b\u0010\u001c\u001a\u0004\u0018\u00010\u0003H\u0002J\"\u0010@\u001a\u0004\u0018\u00010\u00192\u0006\u0010:\u001a\u00020-2\u0006\u0010;\u001a\u00020<2\u0006\u0010=\u001a\u00020\u0015H\u0002J\u0012\u0010A\u001a\u0004\u0018\u0001002\u0006\u0010B\u001a\u00020\u0003H\u0002J\u001a\u0010C\u001a\u00020\u00132\u0006\u0010;\u001a\u00020<2\b\u00104\u001a\u0004\u0018\u00010\u0019H\u0002J\u0010\u0010D\u001a\u00020\u00132\u0006\u0010E\u001a\u00020FH\u0002J\u0010\u0010G\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u0010\u0010H\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u0018\u0010I\u001a\u00020J2\u0006\u0010\u0014\u001a\u00020K2\u0006\u0010L\u001a\u00020FH\u0002J*\u0010M\u001a\u000e\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u00020 2\f\u0010N\u001a\b\u0012\u0004\u0012\u00020O0 2\u0006\u0010\u001c\u001a\u00020\u0003H\u0002J\u001c\u0010P\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\n\u0010Q\u001a\u0006\u0012\u0002\b\u00030\u0002H\u0002J\u0010\u0010R\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u0010\u0010S\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J \u0010T\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00022\u0006\u0010\u0014\u001a\u00020U2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0003H\u0016J \u0010V\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00022\u0006\u0010\u0014\u001a\u00020W2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0003H\u0016J \u0010X\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00022\u0006\u0010\u0014\u001a\u00020Y2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0003H\u0016J \u0010Z\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00022\u0006\u0010\u0014\u001a\u00020[2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0003H\u0016J \u0010\\\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00022\u0006\u0010\u0014\u001a\u00020]2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0003H\u0016J \u0010^\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00022\u0006\u0010\u0014\u001a\u00020_2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0003H\u0016J \u0010`\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00022\u0006\u0010a\u001a\u00020b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0003H\u0016J \u0010c\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00022\u0006\u0010\u0014\u001a\u00020d2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0003H\u0016J \u0010e\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00022\u0006\u0010\u0014\u001a\u00020f2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0003H\u0016J \u0010g\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00022\u0006\u0010\u0014\u001a\u00020h2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0003H\u0016J \u0010i\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00022\u0006\u0010\u0014\u001a\u00020K2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0003H\u0016J \u0010j\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00022\u0006\u0010\u0014\u001a\u00020k2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0003H\u0016J \u0010l\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00022\u0006\u0010\u0014\u001a\u00020m2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0003H\u0016J\u0018\u0010n\u001a\u0006\u0012\u0002\b\u00030\u001f*\u00020+2\u0006\u0010\u001a\u001a\u00020\u001bH\u0002Jd\u0010o\u001a\b\u0012\u0004\u0012\u0002Hp0\u001f\"\u0004\b\u0000\u0010p*\b\u0012\u0004\u0012\u0002Hp0!2\b\b\u0002\u0010\u0012\u001a\u00020\u00132\b\b\u0002\u0010q\u001a\u00020\u00132\b\b\u0002\u0010r\u001a\u00020\u00132\b\b\u0002\u0010s\u001a\u00020\u00132\b\b\u0002\u0010S\u001a\u00020\u00132\b\b\u0002\u0010R\u001a\u00020\u00132\b\b\u0002\u0010t\u001a\u00020\u0013H\u0002J&\u0010o\u001a\b\u0012\u0004\u0012\u0002Hp0\u001f\"\u0004\b\u0000\u0010p*\b\u0012\u0004\u0012\u0002Hp0!2\u0006\u0010\u001a\u001a\u00020\u001bH\u0002R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0011R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006v"}, d2 = {"Lorg/jetbrains/kotlin/resolve/constants/evaluate/ConstantExpressionEvaluatorVisitor;", "Lorg/jetbrains/kotlin/psi/KtVisitor;", "Lorg/jetbrains/kotlin/resolve/constants/CompileTimeConstant;", "Lorg/jetbrains/kotlin/types/KotlinType;", "constantExpressionEvaluator", "Lorg/jetbrains/kotlin/resolve/constants/evaluate/ConstantExpressionEvaluator;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "(Lorg/jetbrains/kotlin/resolve/constants/evaluate/ConstantExpressionEvaluator;Lorg/jetbrains/kotlin/resolve/BindingTrace;)V", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "inlineConstTracker", "Lorg/jetbrains/kotlin/incremental/components/InlineConstTracker;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "stringExpressionEvaluator", "org/jetbrains/kotlin/resolve/constants/evaluate/ConstantExpressionEvaluatorVisitor$stringExpressionEvaluator$1", "Lorg/jetbrains/kotlin/resolve/constants/evaluate/ConstantExpressionEvaluatorVisitor$stringExpressionEvaluator$1;", "canBeUsedInAnnotation", "", "expression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "checkAccessibilityOfUnsignedTypes", "createCompileTimeConstant", "value", "", "parameters", "Lorg/jetbrains/kotlin/resolve/constants/CompileTimeConstant$Parameters;", "expectedType", "createConstant", "createConstantValueForArrayFunctionCall", "Lorg/jetbrains/kotlin/resolve/constants/TypedCompileTimeConstant;", "", "Lorg/jetbrains/kotlin/resolve/constants/ConstantValue;", "call", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "createConstantValueForUnsignedTypeConstructor", "constructorDescriptor", "Lorg/jetbrains/kotlin/descriptors/ConstructorDescriptor;", "representation", "Lorg/jetbrains/kotlin/descriptors/InlineClassRepresentation;", "Lorg/jetbrains/kotlin/types/SimpleType;", "createIntegerCompileTimeConstant", "", "createOperationArgument", "Lorg/jetbrains/kotlin/resolve/constants/evaluate/ConstantExpressionEvaluatorVisitor$OperationArgument;", "parameterType", "compileTimeType", "Lorg/jetbrains/kotlin/resolve/constants/evaluate/CompileTimeType;", "createOperationArgumentForFirstParameter", "argument", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedValueArgument;", "parameter", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "createOperationArgumentForReceiver", "resolvedCall", "evaluate", "evaluateBinaryAndCheck", "receiver", "name", "", "callExpression", "evaluateCall", "receiverExpression", "evaluateUnaryAndCheck", "getCompileTimeType", "c", "isDivisionByZero", "isPropertyCompileTimeConstant", "descriptor", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "isPureConstant", "isStandaloneOnlyConstant", "reportInlineConst", "", "Lorg/jetbrains/kotlin/psi/KtSimpleNameExpression;", "variableDescriptor", "resolveArguments", "valueArguments", "Lorg/jetbrains/kotlin/psi/ValueArgument;", "shouldSkipComplexBooleanValue", "constant", "usesNonConstValAsConstant", "usesVariableAsConstant", "visitBinaryExpression", "Lorg/jetbrains/kotlin/psi/KtBinaryExpression;", "visitBinaryWithTypeRHSExpression", "Lorg/jetbrains/kotlin/psi/KtBinaryExpressionWithTypeRHS;", "visitCallExpression", "Lorg/jetbrains/kotlin/psi/KtCallExpression;", "visitClassLiteralExpression", "Lorg/jetbrains/kotlin/psi/KtClassLiteralExpression;", "visitCollectionLiteralExpression", "Lorg/jetbrains/kotlin/psi/KtCollectionLiteralExpression;", "visitConstantExpression", "Lorg/jetbrains/kotlin/psi/KtConstantExpression;", "visitKtElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/psi/KtElement;", "visitLabeledExpression", "Lorg/jetbrains/kotlin/psi/KtLabeledExpression;", "visitParenthesizedExpression", "Lorg/jetbrains/kotlin/psi/KtParenthesizedExpression;", "visitQualifiedExpression", "Lorg/jetbrains/kotlin/psi/KtQualifiedExpression;", "visitSimpleNameExpression", "visitStringTemplateExpression", "Lorg/jetbrains/kotlin/psi/KtStringTemplateExpression;", "visitUnaryExpression", "Lorg/jetbrains/kotlin/psi/KtUnaryExpression;", "createSimpleIntCompileTimeConst", "wrap", ExifInterface.GPS_DIRECTION_TRUE, "isPure", "isUnsigned", "isUnsignedLong", "isConvertableConstVal", "OperationArgument", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class ConstantExpressionEvaluatorVisitor extends KtVisitor<CompileTimeConstant<?>, KotlinType> {
    private final KotlinBuiltIns builtIns;
    private final ConstantExpressionEvaluator constantExpressionEvaluator;
    private final InlineConstTracker inlineConstTracker;
    private final LanguageVersionSettings languageVersionSettings;
    private final ConstantExpressionEvaluatorVisitor$stringExpressionEvaluator$1 stringExpressionEvaluator;
    private final BindingTrace trace;

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public CompileTimeConstant<?> visitKtElement(KtElement element, KotlinType expectedType) {
        Intrinsics.checkNotNullParameter(element, "element");
        return null;
    }

    public ConstantExpressionEvaluatorVisitor(ConstantExpressionEvaluator constantExpressionEvaluator, BindingTrace trace) {
        Intrinsics.checkNotNullParameter(constantExpressionEvaluator, "constantExpressionEvaluator");
        Intrinsics.checkNotNullParameter(trace, "trace");
        this.constantExpressionEvaluator = constantExpressionEvaluator;
        this.trace = trace;
        this.languageVersionSettings = constantExpressionEvaluator.getLanguageVersionSettings();
        this.builtIns = constantExpressionEvaluator.getModule().getBuiltIns();
        this.inlineConstTracker = constantExpressionEvaluator.getInlineConstTracker() instanceof InlineConstTracker.DoNothing ? null : constantExpressionEvaluator.getInlineConstTracker();
        this.stringExpressionEvaluator = new ConstantExpressionEvaluatorVisitor$stringExpressionEvaluator$1(this);
    }

    public final CompileTimeConstant<?> evaluate(KtExpression expression, KotlinType expectedType) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        ConstantExpressionEvaluator.Companion companion = ConstantExpressionEvaluator.INSTANCE;
        BindingContext context = this.trace.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
        CompileTimeConstant<?> possiblyErrorConstant = companion.getPossiblyErrorConstant(expression, context);
        if (possiblyErrorConstant != null) {
            return possiblyErrorConstant;
        }
        ConstantExpressionEvaluatorVisitor constantExpressionEvaluatorVisitor = this;
        if (expectedType == null) {
            SimpleType NO_EXPECTED_TYPE = TypeUtils.NO_EXPECTED_TYPE;
            Intrinsics.checkNotNullExpressionValue(NO_EXPECTED_TYPE, "NO_EXPECTED_TYPE");
            expectedType = NO_EXPECTED_TYPE;
        }
        CompileTimeConstant<?> compileTimeConstant = (CompileTimeConstant) expression.accept(constantExpressionEvaluatorVisitor, expectedType);
        if (compileTimeConstant == null || shouldSkipComplexBooleanValue(expression, compileTimeConstant)) {
            return null;
        }
        if ((compileTimeConstant instanceof TypedCompileTimeConstant) && TypeUtilsKt.isGenericArrayOfTypeParameter(((TypedCompileTimeConstant) compileTimeConstant).getType())) {
            return compileTimeConstant;
        }
        this.trace.record(BindingContext.COMPILE_TIME_VALUE, expression, compileTimeConstant);
        return compileTimeConstant;
    }

    /* JADX WARN: Code duplicated, block: B:21:0x003f  */
    private final boolean shouldSkipComplexBooleanValue(KtExpression expression, CompileTimeConstant<?> constant) {
        if (!ConstantExpressionEvaluator.INSTANCE.isComplexBooleanConstant(expression, constant)) {
            return false;
        }
        if (this.languageVersionSettings.supportsFeature(LanguageFeature.ProhibitSimplificationOfNonTrivialConstBooleanExpressions)) {
            return true;
        }
        PsiElement parent = expression.getLightParent();
        while (parent instanceof KtParenthesizedExpression) {
            parent = ((KtParenthesizedExpression) parent).getLightParent();
        }
        if (!(parent instanceof KtWhenConditionWithExpression)) {
            if (parent instanceof KtContainerNode) {
                KtContainerNode ktContainerNode = (KtContainerNode) parent;
                if ((ktContainerNode.getLightParent() instanceof KtWhileExpression) || (ktContainerNode.getLightParent() instanceof KtDoWhileExpression)) {
                    SimpleType booleanType = this.builtIns.getBooleanType();
                    Intrinsics.checkNotNullExpressionValue(booleanType, "getBooleanType(...)");
                    ConstantValue<?> constantValue = constant.toConstantValue(booleanType);
                    BindingTrace bindingTrace = this.trace;
                    Object value = constantValue.getValue();
                    Intrinsics.checkNotNull(value, "null cannot be cast to non-null type kotlin.Boolean");
                    bindingTrace.report(Errors.NON_TRIVIAL_BOOLEAN_CONSTANT.on(expression, (Boolean) value));
                }
            }
        } else {
            SimpleType booleanType2 = this.builtIns.getBooleanType();
            Intrinsics.checkNotNullExpressionValue(booleanType2, "getBooleanType(...)");
            ConstantValue<?> constantValue2 = constant.toConstantValue(booleanType2);
            BindingTrace bindingTrace2 = this.trace;
            Object value2 = constantValue2.getValue();
            Intrinsics.checkNotNull(value2, "null cannot be cast to non-null type kotlin.Boolean");
            bindingTrace2.report(Errors.NON_TRIVIAL_BOOLEAN_CONSTANT.on(expression, (Boolean) value2));
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public CompileTimeConstant<?> visitConstantExpression(KtConstantExpression expression, KotlinType expectedType) {
        Character chValueOf;
        Intrinsics.checkNotNullParameter(expression, "expression");
        String text = expression.getText();
        if (text == null) {
            return null;
        }
        IElementType elementType = expression.getNode().getElementType();
        Intrinsics.checkNotNullExpressionValue(elementType, "getElementType(...)");
        if (Intrinsics.areEqual(elementType, KtNodeTypes.NULL)) {
            return wrap$default(this, new NullValue(), false, false, false, false, false, false, false, 127, null);
        }
        if (Intrinsics.areEqual(elementType, KtNodeTypes.INTEGER_CONSTANT) ? true : Intrinsics.areEqual(elementType, KtNodeTypes.FLOAT_CONSTANT)) {
            chValueOf = ParseUtilsKt.parseNumericLiteral(text, elementType);
        } else if (Intrinsics.areEqual(elementType, KtNodeTypes.BOOLEAN_CONSTANT)) {
            chValueOf = Boolean.valueOf(ParseUtilsKt.parseBoolean(text));
        } else {
            if (!Intrinsics.areEqual(elementType, KtNodeTypes.CHARACTER_CONSTANT)) {
                throw new IllegalArgumentException("Unsupported constant: " + expression);
            }
            chValueOf = CompileTimeConstantChecker.parseChar(expression);
        }
        if (chValueOf == null) {
            return null;
        }
        if (chValueOf instanceof Double) {
            Number number = (Number) chValueOf;
            if (Double.isInfinite(number.doubleValue())) {
                this.trace.report(Errors.FLOAT_LITERAL_CONFORMS_INFINITY.on(expression));
            }
            if ((number.doubleValue() == 0.0d) && !TypeConversionUtil.isFPZero(text)) {
                this.trace.report(Errors.FLOAT_LITERAL_CONFORMS_ZERO.on(expression));
            }
        }
        if (chValueOf instanceof Float) {
            Number number2 = (Number) chValueOf;
            if (Float.isInfinite(number2.floatValue())) {
                this.trace.report(Errors.FLOAT_LITERAL_CONFORMS_INFINITY.on(expression));
            }
            if ((number2.floatValue() == 0.0f) && !TypeConversionUtil.isFPZero(text)) {
                this.trace.report(Errors.FLOAT_LITERAL_CONFORMS_ZERO.on(expression));
            }
        }
        boolean z = Intrinsics.areEqual(elementType, KtNodeTypes.INTEGER_CONSTANT) && ParseUtilsKt.hasUnsignedLongSuffix(text);
        boolean z2 = z || ParseUtilsKt.hasUnsignedSuffix(text);
        return createConstant(chValueOf, expectedType, new CompileTimeConstant.Parameters(true, !(z2 || ParseUtilsKt.hasLongSuffix(text)), z2, z, false, false, false));
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public CompileTimeConstant<?> visitParenthesizedExpression(KtParenthesizedExpression expression, KotlinType expectedType) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        KtExpression ktExpressionDeparenthesize = KtPsiUtil.deparenthesize(expression);
        if (ktExpressionDeparenthesize == null || Intrinsics.areEqual(ktExpressionDeparenthesize, expression)) {
            return null;
        }
        return evaluate(ktExpressionDeparenthesize, expectedType);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public CompileTimeConstant<?> visitLabeledExpression(KtLabeledExpression expression, KotlinType expectedType) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        KtExpression baseExpression = expression.getBaseExpression();
        if (baseExpression != null) {
            return evaluate(baseExpression, expectedType);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public CompileTimeConstant<?> visitStringTemplateExpression(KtStringTemplateExpression expression, KotlinType expectedType) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        StringBuilder sb = new StringBuilder();
        KtStringTemplateEntry[] entries = expression.getEntries();
        Intrinsics.checkNotNullExpressionValue(entries, "getEntries(...)");
        boolean z = true;
        boolean z2 = false;
        boolean z3 = false;
        for (KtStringTemplateEntry ktStringTemplateEntry : entries) {
            ConstantExpressionEvaluatorVisitor$stringExpressionEvaluator$1 constantExpressionEvaluatorVisitor$stringExpressionEvaluator$1 = this.stringExpressionEvaluator;
            Intrinsics.checkNotNull(ktStringTemplateEntry);
            TypedCompileTimeConstant<String> typedCompileTimeConstantEvaluate = constantExpressionEvaluatorVisitor$stringExpressionEvaluator$1.evaluate(ktStringTemplateEntry);
            if (typedCompileTimeConstantEvaluate == null) {
                return null;
            }
            if (!typedCompileTimeConstantEvaluate.getCanBeUsedInAnnotations()) {
                z = false;
            }
            if (typedCompileTimeConstantEvaluate.getUsesVariableAsConstant()) {
                z2 = true;
            }
            if (typedCompileTimeConstantEvaluate.getUsesNonConstValAsConstant()) {
                z3 = true;
            }
            sb.append(typedCompileTimeConstantEvaluate.getConstantValue().getValue());
        }
        return createConstant(sb.toString(), expectedType, new CompileTimeConstant.Parameters(z, false, false, false, z2, z3, false));
    }

    private final boolean isStandaloneOnlyConstant(KtExpression expression) {
        ConstantExpressionEvaluator.Companion companion = ConstantExpressionEvaluator.INSTANCE;
        BindingContext context = this.trace.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
        CompileTimeConstant<?> constant = companion.getConstant(expression, context);
        if (constant != null) {
            return ConstantExpressionEvaluatorKt.isStandaloneOnlyConstant(constant);
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public CompileTimeConstant<?> visitBinaryWithTypeRHSExpression(KtBinaryExpressionWithTypeRHS expression, KotlinType expectedType) {
        KotlinType type;
        Intrinsics.checkNotNullParameter(expression, "expression");
        KtExpression left = expression.getLeft();
        Intrinsics.checkNotNullExpressionValue(left, "getLeft(...)");
        CompileTimeConstant<?> compileTimeConstantEvaluate = evaluate(left, expectedType);
        if (compileTimeConstantEvaluate != null && expectedType != null && !TypeUtils.noExpectedType(expectedType)) {
            if (compileTimeConstantEvaluate instanceof TypedCompileTimeConstant) {
                type = ((TypedCompileTimeConstant) compileTimeConstantEvaluate).getType();
            } else if (compileTimeConstantEvaluate instanceof IntegerValueTypeConstant) {
                type = ((IntegerValueTypeConstant) compileTimeConstantEvaluate).getType(expectedType);
            } else {
                throw new IllegalStateException("Unexpected compileTimeConstant class: " + compileTimeConstantEvaluate.getClass().getCanonicalName());
            }
            if (!TypeUtilsKt.isSubtypeOf(type, expectedType)) {
                return null;
            }
        }
        return compileTimeConstantEvaluate;
    }

    /* JADX WARN: Code duplicated, block: B:28:0x007a  */
    /* JADX WARN: Code duplicated, block: B:35:0x0094  */
    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public CompileTimeConstant<?> visitBinaryExpression(KtBinaryExpression expression, KotlinType expectedType) {
        KtExpression right;
        CompileTimeConstant<?> compileTimeConstantEvaluate;
        boolean z;
        Intrinsics.checkNotNullParameter(expression, "expression");
        KtExpression left = expression.getLeft();
        if (left == null) {
            return null;
        }
        IElementType operationToken = expression.getOperationToken();
        Intrinsics.checkNotNullExpressionValue(operationToken, "getOperationToken(...)");
        ImmutableBiMap<KtSingleValueToken, Name> BOOLEAN_OPERATIONS = OperatorConventions.BOOLEAN_OPERATIONS;
        Intrinsics.checkNotNullExpressionValue(BOOLEAN_OPERATIONS, "BOOLEAN_OPERATIONS");
        if (BOOLEAN_OPERATIONS.containsKey(operationToken)) {
            SimpleType booleanType = this.builtIns.getBooleanType();
            Intrinsics.checkNotNullExpressionValue(booleanType, "getBooleanType(...)");
            SimpleType simpleType = booleanType;
            CompileTimeConstant<?> compileTimeConstantEvaluate2 = evaluate(left, simpleType);
            if (compileTimeConstantEvaluate2 == null || (right = expression.getRight()) == null || (compileTimeConstantEvaluate = evaluate(right, simpleType)) == null) {
                return null;
            }
            Object value = compileTimeConstantEvaluate2.getValue(simpleType);
            Object value2 = compileTimeConstantEvaluate.getValue(simpleType);
            if (!(value instanceof Boolean) || !(value2 instanceof Boolean)) {
                return null;
            }
            if (Intrinsics.areEqual(operationToken, KtTokens.ANDAND)) {
                if (((Boolean) value).booleanValue() && ((Boolean) value2).booleanValue()) {
                    z = true;
                } else {
                    z = false;
                }
            } else {
                if (!Intrinsics.areEqual(operationToken, KtTokens.OROR)) {
                    throw new IllegalArgumentException("Unknown boolean operation token " + operationToken);
                }
                if (((Boolean) value).booleanValue() || ((Boolean) value2).booleanValue()) {
                    z = true;
                } else {
                    z = false;
                }
            }
            return createConstant(Boolean.valueOf(z), expectedType, new CompileTimeConstant.Parameters(true, false, false, false, compileTimeConstantEvaluate2.getUsesVariableAsConstant() || compileTimeConstantEvaluate.getUsesVariableAsConstant(), compileTimeConstantEvaluate2.getUsesNonConstValAsConstant() || compileTimeConstantEvaluate.getUsesNonConstValAsConstant(), false));
        }
        KtOperationReferenceExpression operationReference = expression.getOperationReference();
        Intrinsics.checkNotNullExpressionValue(operationReference, "getOperationReference(...)");
        return evaluateCall(operationReference, left, expectedType);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public CompileTimeConstant<?> visitCollectionLiteralExpression(KtCollectionLiteralExpression expression, KotlinType expectedType) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        ResolvedCall<?> resolvedCall = (ResolvedCall) this.trace.getContext().get(BindingContext.COLLECTION_LITERAL_CALL, expression);
        if (resolvedCall == null) {
            return null;
        }
        return createConstantValueForArrayFunctionCall(resolvedCall);
    }

    private final CompileTimeConstant<?> evaluateCall(KtExpression callExpression, KtExpression receiverExpression, KotlinType expectedType) {
        BindingContext context = this.trace.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
        ResolvedCall<? extends CallableDescriptor> resolvedCall = CallUtilKt.getResolvedCall(callExpression, context);
        if (resolvedCall == null || !KotlinBuiltIns.isUnderKotlinPackage(resolvedCall.getResultingDescriptor())) {
            return null;
        }
        Name name2 = resolvedCall.getResultingDescriptor().getShortName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        OperationArgument operationArgumentCreateOperationArgumentForReceiver = createOperationArgumentForReceiver(resolvedCall, receiverExpression);
        if (operationArgumentCreateOperationArgumentForReceiver == null || isStandaloneOnlyConstant(operationArgumentCreateOperationArgumentForReceiver.getExpression())) {
            return null;
        }
        Set<Map.Entry<ValueParameterDescriptor, ResolvedValueArgument>> setEntrySet = resolvedCall.getValueArguments().entrySet();
        if (setEntrySet.isEmpty()) {
            String strAsString = name2.asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            Object objEvaluateUnaryAndCheck = evaluateUnaryAndCheck(operationArgumentCreateOperationArgumentForReceiver, strAsString, callExpression);
            if (objEvaluateUnaryAndCheck == null) {
                return null;
            }
            return createConstant(objEvaluateUnaryAndCheck, expectedType, new CompileTimeConstant.Parameters(canBeUsedInAnnotation(operationArgumentCreateOperationArgumentForReceiver.getExpression()), (OperatorConventions.NUMBER_CONVERSIONS.contains(name2) || (operationArgumentCreateOperationArgumentForReceiver.getCtcType() == CompileTimeType.CHAR && Intrinsics.areEqual(name2, StandardNames.CHAR_CODE)) || !isPureConstant(operationArgumentCreateOperationArgumentForReceiver.getExpression())) ? false : true, false, false, usesVariableAsConstant(operationArgumentCreateOperationArgumentForReceiver.getExpression()), usesNonConstValAsConstant(operationArgumentCreateOperationArgumentForReceiver.getExpression()), false));
        }
        if (setEntrySet.size() != 1) {
            return null;
        }
        Map.Entry entry = (Map.Entry) CollectionsKt.first(setEntrySet);
        ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) entry.getKey();
        ResolvedValueArgument resolvedValueArgument = (ResolvedValueArgument) entry.getValue();
        Intrinsics.checkNotNull(resolvedValueArgument);
        Intrinsics.checkNotNull(valueParameterDescriptor);
        OperationArgument operationArgumentCreateOperationArgumentForFirstParameter = createOperationArgumentForFirstParameter(resolvedValueArgument, valueParameterDescriptor);
        if (operationArgumentCreateOperationArgumentForFirstParameter == null || isStandaloneOnlyConstant(operationArgumentCreateOperationArgumentForFirstParameter.getExpression())) {
            return null;
        }
        String strAsString2 = name2.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString2, "asString(...)");
        if (isDivisionByZero(strAsString2, operationArgumentCreateOperationArgumentForFirstParameter.getValue())) {
            PsiElement parentOfType = PsiTreeUtil.getParentOfType(receiverExpression, (Class<PsiElement>) KtExpression.class);
            Intrinsics.checkNotNull(parentOfType);
            this.trace.report(Errors.DIVISION_BY_ZERO.on((KtExpression) parentOfType));
            if ((ConstantExpressionEvaluatorKt.isIntegerType(operationArgumentCreateOperationArgumentForReceiver.getValue()) && ConstantExpressionEvaluatorKt.isIntegerType(operationArgumentCreateOperationArgumentForFirstParameter.getValue())) || !this.languageVersionSettings.supportsFeature(LanguageFeature.DivisionByZeroInConstantExpressions)) {
                return wrap$default(this, ErrorValue.INSTANCE.create("Division by zero"), false, false, false, false, false, false, false, 127, null);
            }
        }
        String strAsString3 = name2.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString3, "asString(...)");
        Object objEvaluateBinaryAndCheck = evaluateBinaryAndCheck(operationArgumentCreateOperationArgumentForReceiver, operationArgumentCreateOperationArgumentForFirstParameter, strAsString3, callExpression);
        if (objEvaluateBinaryAndCheck == null) {
            return null;
        }
        CompileTimeConstant.Parameters parameters = new CompileTimeConstant.Parameters(canBeUsedInAnnotation(operationArgumentCreateOperationArgumentForReceiver.getExpression()) && canBeUsedInAnnotation(operationArgumentCreateOperationArgumentForFirstParameter.getExpression()), isPureConstant(operationArgumentCreateOperationArgumentForReceiver.getExpression()) && isPureConstant(operationArgumentCreateOperationArgumentForFirstParameter.getExpression()), false, false, usesVariableAsConstant(operationArgumentCreateOperationArgumentForReceiver.getExpression()) || usesVariableAsConstant(operationArgumentCreateOperationArgumentForFirstParameter.getExpression()), usesNonConstValAsConstant(operationArgumentCreateOperationArgumentForReceiver.getExpression()) || usesNonConstValAsConstant(operationArgumentCreateOperationArgumentForFirstParameter.getExpression()), false);
        if (Intrinsics.areEqual(name2, OperatorNameConventions.COMPARE_TO)) {
            ConstantValue constantValueCreateCompileTimeConstantForCompareTo = ConstantExpressionEvaluatorKt.createCompileTimeConstantForCompareTo(objEvaluateBinaryAndCheck, callExpression);
            return constantValueCreateCompileTimeConstantForCompareTo != null ? wrap(constantValueCreateCompileTimeConstantForCompareTo, parameters) : null;
        }
        if (Intrinsics.areEqual(name2, OperatorNameConventions.EQUALS)) {
            ConstantValue constantValueCreateCompileTimeConstantForEquals = ConstantExpressionEvaluatorKt.createCompileTimeConstantForEquals(objEvaluateBinaryAndCheck, callExpression);
            return constantValueCreateCompileTimeConstantForEquals != null ? wrap(constantValueCreateCompileTimeConstantForEquals, parameters) : null;
        }
        return createConstant(objEvaluateBinaryAndCheck, expectedType, parameters);
    }

    private final boolean usesVariableAsConstant(KtExpression expression) {
        ConstantExpressionEvaluator.Companion companion = ConstantExpressionEvaluator.INSTANCE;
        BindingContext context = this.trace.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
        CompileTimeConstant<?> constant = companion.getConstant(expression, context);
        if (constant != null) {
            return constant.getUsesVariableAsConstant();
        }
        return false;
    }

    private final boolean usesNonConstValAsConstant(KtExpression expression) {
        ConstantExpressionEvaluator.Companion companion = ConstantExpressionEvaluator.INSTANCE;
        BindingContext context = this.trace.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
        CompileTimeConstant<?> constant = companion.getConstant(expression, context);
        if (constant != null) {
            return constant.getUsesNonConstValAsConstant();
        }
        return false;
    }

    private final boolean canBeUsedInAnnotation(KtExpression expression) {
        ConstantExpressionEvaluator.Companion companion = ConstantExpressionEvaluator.INSTANCE;
        BindingContext context = this.trace.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
        CompileTimeConstant<?> constant = companion.getConstant(expression, context);
        if (constant != null) {
            return constant.getCanBeUsedInAnnotations();
        }
        return false;
    }

    private final boolean isPureConstant(KtExpression expression) {
        ConstantExpressionEvaluator.Companion companion = ConstantExpressionEvaluator.INSTANCE;
        BindingContext context = this.trace.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
        CompileTimeConstant<?> constant = companion.getConstant(expression, context);
        if (constant != null) {
            return constant.isPure();
        }
        return false;
    }

    private final Object evaluateUnaryAndCheck(OperationArgument receiver, String name2, final KtExpression callExpression) {
        return ConstantExpressionEvaluatorKt.evaluateUnaryAndCheck(name2, receiver.getCtcType(), receiver.getValue(), new Function0<Unit>() { // from class: org.jetbrains.kotlin.resolve.constants.evaluate.ConstantExpressionEvaluatorVisitor.evaluateUnaryAndCheck.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                BindingTrace bindingTrace = ConstantExpressionEvaluatorVisitor.this.trace;
                DiagnosticFactory0<KtExpression> diagnosticFactory0 = Errors.INTEGER_OVERFLOW;
                KtExpression ktExpression = (KtExpression) PsiTreeUtil.getParentOfType(callExpression, KtExpression.class, true);
                if (ktExpression == null) {
                    ktExpression = callExpression;
                }
                bindingTrace.report(diagnosticFactory0.on(ktExpression));
            }
        });
    }

    private final Object evaluateBinaryAndCheck(OperationArgument receiver, OperationArgument parameter, String name2, final KtExpression callExpression) {
        return ConstantExpressionEvaluatorKt.evaluateBinaryAndCheck(name2, receiver.getCtcType(), receiver.getValue(), parameter.getCtcType(), parameter.getValue(), new Function0<Unit>() { // from class: org.jetbrains.kotlin.resolve.constants.evaluate.ConstantExpressionEvaluatorVisitor.evaluateBinaryAndCheck.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                BindingTrace bindingTrace = ConstantExpressionEvaluatorVisitor.this.trace;
                DiagnosticFactory0<KtExpression> diagnosticFactory0 = Errors.INTEGER_OVERFLOW;
                KtExpression ktExpression = (KtExpression) PsiTreeUtil.getParentOfType(callExpression, KtExpression.class, true);
                if (ktExpression == null) {
                    ktExpression = callExpression;
                }
                bindingTrace.report(diagnosticFactory0.on(ktExpression));
            }
        });
    }

    private final boolean isDivisionByZero(String name2, Object parameter) {
        return ConstantExpressionEvaluatorKt.DIVISION_OPERATION_NAMES.contains(name2) && ConstantExpressionEvaluatorKt.isZero(parameter);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public CompileTimeConstant<?> visitUnaryExpression(KtUnaryExpression expression, KotlinType expectedType) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        KtExpression baseExpression = expression.getBaseExpression();
        if (baseExpression == null) {
            return null;
        }
        KtSimpleNameExpression operationReference = expression.getOperationReference();
        Intrinsics.checkNotNullExpressionValue(operationReference, "getOperationReference(...)");
        return evaluateCall(operationReference, baseExpression, expectedType);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public CompileTimeConstant<?> visitSimpleNameExpression(KtSimpleNameExpression expression, KotlinType expectedType) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        DeclarationDescriptor declarationDescriptor = (DeclarationDescriptor) this.trace.getContext().get(BindingContext.REFERENCE_TARGET, expression);
        if (declarationDescriptor != null && DescriptorUtils.isEnumEntry(declarationDescriptor)) {
            DeclarationDescriptor containingDeclaration = declarationDescriptor.getContainingModule();
            Intrinsics.checkNotNull(containingDeclaration, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
            ClassId classId = DescriptorUtilsKt.getClassId((ClassifierDescriptor) containingDeclaration);
            if (classId == null) {
                return null;
            }
            Name name2 = declarationDescriptor.getShortName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            return wrap$default(this, new EnumValue(classId, name2), false, false, false, false, false, false, false, 127, null);
        }
        VariableDescriptor variableDescriptor = declarationDescriptor instanceof VariableDescriptor ? (VariableDescriptor) declarationDescriptor : null;
        if (variableDescriptor != null && isPropertyCompileTimeConstant(variableDescriptor)) {
            DeclarationDescriptor containingDeclaration2 = variableDescriptor.getContainingModule();
            Intrinsics.checkNotNullExpressionValue(containingDeclaration2, "getContainingDeclaration(...)");
            if (!DescriptorUtilsKt.isCompanionObject(containingDeclaration2)) {
                reportInlineConst(expression, variableDescriptor);
            }
        }
        BindingContext context = this.trace.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
        ResolvedCall<? extends CallableDescriptor> resolvedCall = CallUtilKt.getResolvedCall(expression, context);
        if (resolvedCall == null) {
            return null;
        }
        CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
        if (!(resultingDescriptor instanceof VariableDescriptor)) {
            return null;
        }
        if ((resultingDescriptor instanceof PropertyDescriptor) && ((PropertyDescriptor) resultingDescriptor).getModality() != Modality.FINAL) {
            return null;
        }
        VariableDescriptor variableDescriptor2 = (VariableDescriptor) resultingDescriptor;
        boolean z = variableDescriptor2.isConst() && ImplicitIntegerCoercion.INSTANCE.isEnabledFor(resultingDescriptor, this.languageVersionSettings) && (variableDescriptor2.mo6754getCompileTimeInitializer() instanceof IntValue);
        ConstantValue<?> constantValueMo6754getCompileTimeInitializer = variableDescriptor2.mo6754getCompileTimeInitializer();
        return constantValueMo6754getCompileTimeInitializer != null ? wrap(constantValueMo6754getCompileTimeInitializer, new CompileTimeConstant.Parameters(isPropertyCompileTimeConstant(variableDescriptor2), false, false, false, true, !variableDescriptor2.isConst(), z)) : null;
    }

    private final void reportInlineConst(KtSimpleNameExpression expression, VariableDescriptor variableDescriptor) {
        VirtualFile virtualFile;
        String path;
        String string;
        String strReplace$default;
        if (this.inlineConstTracker == null || (virtualFile = expression.getContainingFile().getVirtualFile()) == null || (path = virtualFile.getPath()) == null) {
            return;
        }
        String referencedName = expression.getReferencedName();
        String string2 = variableDescriptor.getType().toString();
        FqName fqNameContainingPackage = DescriptorUtilKt.containingPackage(variableDescriptor);
        if (fqNameContainingPackage == null || (string = fqNameContainingPackage.toString()) == null) {
            return;
        }
        DeclarationDescriptor containingDeclaration = variableDescriptor.getContainingModule();
        Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
        String strAsString = DescriptorUtilsKt.getFqNameSafe(containingDeclaration).asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        if (StringsKt.startsWith$default(strAsString, string + '.', false, 2, (Object) null)) {
            StringBuilder sbAppend = new StringBuilder().append(string).append('.');
            String strSubstring = strAsString.substring(string.length() + 1);
            Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
            strReplace$default = sbAppend.append(StringsKt.replace$default(strSubstring, ".", "$", false, 4, (Object) null)).toString();
        } else {
            strReplace$default = StringsKt.replace$default(strAsString, ".", "$", false, 4, (Object) null);
        }
        this.inlineConstTracker.report(path, strReplace$default, referencedName, string2);
    }

    private final boolean isPropertyCompileTimeConstant(VariableDescriptor descriptor) {
        if (descriptor.isVar()) {
            return false;
        }
        if (!DescriptorUtils.isObject(descriptor.getContainingModule()) && !DescriptorUtils.isStaticDeclaration(descriptor)) {
            return false;
        }
        KotlinType type = descriptor.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        return ConstUtilKt.canBeUsedForConstVal(type);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public CompileTimeConstant<?> visitQualifiedExpression(KtQualifiedExpression expression, KotlinType expectedType) {
        CompileTimeConstant<?> compileTimeConstantEvaluateCall;
        Intrinsics.checkNotNullParameter(expression, "expression");
        KtExpression selectorExpression = expression.getSelectorExpression();
        if (selectorExpression instanceof KtCallExpression) {
            CompileTimeConstant<?> compileTimeConstantEvaluate = evaluate(selectorExpression, expectedType);
            if (compileTimeConstantEvaluate != null) {
                return compileTimeConstantEvaluate;
            }
            KtExpression calleeExpression = ((KtCallExpression) selectorExpression).getCalleeExpression();
            if (calleeExpression instanceof KtSimpleNameExpression) {
                return evaluateCall(calleeExpression, expression.getReceiverExpression(), expectedType);
            }
            return null;
        }
        if ((selectorExpression instanceof KtSimpleNameExpression) && (compileTimeConstantEvaluateCall = evaluateCall(selectorExpression, expression.getReceiverExpression(), expectedType)) != null) {
            return compileTimeConstantEvaluateCall;
        }
        if (selectorExpression != null) {
            return evaluate(selectorExpression, expectedType);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public CompileTimeConstant<?> visitCallExpression(KtCallExpression expression, KotlinType expectedType) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        BindingContext context = this.trace.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
        ResolvedCall<? extends CallableDescriptor> resolvedCall = CallUtilKt.getResolvedCall(expression, context);
        if (resolvedCall == null) {
            return null;
        }
        CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
        if (CompileTimeConstantUtils.isArrayFunctionCall(resolvedCall)) {
            return createConstantValueForArrayFunctionCall(resolvedCall);
        }
        if (!(resultingDescriptor instanceof ConstructorDescriptor)) {
            return null;
        }
        ConstructorDescriptor constructorDescriptor = (ConstructorDescriptor) resultingDescriptor;
        ClassDescriptor constructedClass = constructorDescriptor.getConstructedClass();
        Intrinsics.checkNotNullExpressionValue(constructedClass, "getConstructedClass(...)");
        ClassDescriptor classDescriptor = constructedClass;
        if (DescriptorUtils.isAnnotationClass(classDescriptor)) {
            return wrap$default(this, new AnnotationValue(new AnnotationDescriptorImpl(constructedClass.getDefaultType(), this.constantExpressionEvaluator.resolveAnnotationArguments$frontend(resolvedCall, this.trace), SourceElement.NO_SOURCE)), false, false, false, false, false, false, false, 127, null);
        }
        if (!InlineClassesUtilsKt.isInlineClass(classDescriptor) || !UnsignedTypes.INSTANCE.isUnsignedClass(classDescriptor)) {
            return null;
        }
        InlineClassRepresentation<SimpleType> inlineClassRepresentation = DescriptorUtilsKt.getInlineClassRepresentation(constructedClass);
        Intrinsics.checkNotNull(inlineClassRepresentation);
        return createConstantValueForUnsignedTypeConstructor(resolvedCall, constructorDescriptor, inlineClassRepresentation);
    }

    private final TypedCompileTimeConstant<?> createConstantValueForUnsignedTypeConstructor(ResolvedCall<?> call, ConstructorDescriptor constructorDescriptor, InlineClassRepresentation<SimpleType> representation) {
        ResolvedValueArgument resolvedValueArgument;
        List<ValueArgument> arguments;
        ValueArgument valueArgument;
        KtExpression argumentExpression;
        SimpleType simpleType;
        CompileTimeConstant<?> compileTimeConstantEvaluate;
        ConstantValue<?> constantValue;
        UnsignedValueConstant<?> unsignedValueConstantCreateUnsignedValue;
        if (!constructorDescriptor.isPrimary()) {
            return null;
        }
        Map<ValueParameterDescriptor, ResolvedValueArgument> valueArguments = call.getValueArguments();
        Intrinsics.checkNotNullExpressionValue(valueArguments, "getValueArguments(...)");
        if (valueArguments.size() > 1 || (resolvedValueArgument = (ResolvedValueArgument) CollectionsKt.singleOrNull(valueArguments.values())) == null || (arguments = resolvedValueArgument.getArguments()) == null || (valueArgument = (ValueArgument) CollectionsKt.singleOrNull((List) arguments)) == null || (argumentExpression = valueArgument.getArgumentExpression()) == null || (compileTimeConstantEvaluate = evaluate(argumentExpression, (simpleType = (SimpleType) representation.getUnderlyingType()))) == null || (constantValue = compileTimeConstantEvaluate.toConstantValue(simpleType)) == null || (unsignedValueConstantCreateUnsignedValue = ConstantValueFactory.INSTANCE.createUnsignedValue(constantValue)) == null) {
            return null;
        }
        return wrap(unsignedValueConstantCreateUnsignedValue, compileTimeConstantEvaluate.getParameters());
    }

    private final TypedCompileTimeConstant<List<ConstantValue<?>>> createConstantValueForArrayFunctionCall(ResolvedCall<?> call) {
        boolean z;
        boolean z2;
        KotlinType returnType = call.getResultingDescriptor().getReturnType();
        if (returnType == null) {
            return null;
        }
        KotlinType arrayElementType = this.builtIns.getArrayElementType(returnType);
        Intrinsics.checkNotNullExpressionValue(arrayElementType, "getArrayElementType(...)");
        Collection<ResolvedValueArgument> collectionValues = call.getValueArguments().values();
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = collectionValues.iterator();
        while (it.hasNext()) {
            List<ValueArgument> arguments = ((ResolvedValueArgument) it.next()).getArguments();
            Intrinsics.checkNotNullExpressionValue(arguments, "getArguments(...)");
            CollectionsKt.addAll(arrayList, resolveArguments(arguments, arrayElementType));
        }
        ArrayList<CompileTimeConstant> arrayList2 = arrayList;
        List listFilterNotNull = CollectionsKt.filterNotNull(arrayList2);
        ConstantValueFactory constantValueFactory = ConstantValueFactory.INSTANCE;
        List list = listFilterNotNull;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator it2 = list.iterator();
        while (it2.hasNext()) {
            arrayList3.add(((CompileTimeConstant) it2.next()).toConstantValue(arrayElementType));
        }
        ArrayValue arrayValueCreateArrayValue = constantValueFactory.createArrayValue(arrayList3, returnType);
        if (!(list instanceof Collection) || !list.isEmpty()) {
            Iterator it3 = list.iterator();
            while (true) {
                if (!it3.hasNext()) {
                    z = false;
                    break;
                }
                if (((CompileTimeConstant) it3.next()).getUsesVariableAsConstant()) {
                    z = true;
                    break;
                }
            }
        } else {
            z = false;
            break;
        }
        if ((arrayList2 instanceof Collection) && arrayList2.isEmpty()) {
            z2 = false;
        } else {
            for (CompileTimeConstant compileTimeConstant : arrayList2) {
                if (compileTimeConstant == null || compileTimeConstant.getUsesNonConstValAsConstant()) {
                    z2 = true;
                }
            }
            z2 = false;
        }
        return wrap$default(this, arrayValueCreateArrayValue, false, false, false, false, z, z2, false, 79, null);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public CompileTimeConstant<?> visitClassLiteralExpression(KtClassLiteralExpression expression, KotlinType expectedType) {
        TypeProjection typeProjection;
        KotlinType nullableAnyType;
        Intrinsics.checkNotNullParameter(expression, "expression");
        KotlinType type = this.trace.getType(expression);
        Intrinsics.checkNotNull(type);
        if (KotlinTypeKt.isError(type)) {
            return null;
        }
        ClassifierDescriptor this$0 = type.getConstructor().getThis$0();
        if (!(this$0 instanceof ClassDescriptor) || !KotlinBuiltIns.isKClass((ClassDescriptor) this$0) || (typeProjection = (TypeProjection) CollectionsKt.singleOrNull((List) type.getArguments())) == null || (nullableAnyType = typeProjection.getNullableAnyType()) == null) {
            return null;
        }
        if (this.languageVersionSettings.supportsFeature(LanguageFeature.ProhibitTypeParametersInClassLiteralsInAnnotationArguments) && ConstantExpressionEvaluator.INSTANCE.isTypeParameterOrArrayOfTypeParameter$frontend(nullableAnyType)) {
            return null;
        }
        ConstantValue<?> constantValueCreate = KClassValue.INSTANCE.create(nullableAnyType);
        return constantValueCreate != null ? wrap$default(this, constantValueCreate, false, false, false, false, false, false, false, 127, null) : null;
    }

    private final List<CompileTimeConstant<?>> resolveArguments(List<? extends ValueArgument> valueArguments, KotlinType expectedType) {
        ArrayList arrayList = new ArrayList();
        Iterator<? extends ValueArgument> it = valueArguments.iterator();
        while (it.hasNext()) {
            KtExpression argumentExpression = it.next().getArgumentExpression();
            if (argumentExpression != null) {
                arrayList.add(evaluate(argumentExpression, expectedType));
            }
        }
        return arrayList;
    }

    /* JADX INFO: compiled from: ConstantExpressionEvaluator.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\b\u0002\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/resolve/constants/evaluate/ConstantExpressionEvaluatorVisitor$OperationArgument;", "", "value", "ctcType", "Lorg/jetbrains/kotlin/resolve/constants/evaluate/CompileTimeType;", "expression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "(Ljava/lang/Object;Lorg/jetbrains/kotlin/resolve/constants/evaluate/CompileTimeType;Lorg/jetbrains/kotlin/psi/KtExpression;)V", "getCtcType", "()Lorg/jetbrains/kotlin/resolve/constants/evaluate/CompileTimeType;", "getExpression", "()Lorg/jetbrains/kotlin/psi/KtExpression;", "getValue", "()Ljava/lang/Object;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class OperationArgument {
        private final CompileTimeType ctcType;
        private final KtExpression expression;
        private final Object value;

        public OperationArgument(Object value, CompileTimeType ctcType, KtExpression expression) {
            Intrinsics.checkNotNullParameter(value, "value");
            Intrinsics.checkNotNullParameter(ctcType, "ctcType");
            Intrinsics.checkNotNullParameter(expression, "expression");
            this.value = value;
            this.ctcType = ctcType;
            this.expression = expression;
        }

        public final CompileTimeType getCtcType() {
            return this.ctcType;
        }

        public final KtExpression getExpression() {
            return this.expression;
        }

        public final Object getValue() {
            return this.value;
        }
    }

    private final OperationArgument createOperationArgumentForReceiver(ResolvedCall<?> resolvedCall, KtExpression expression) {
        CompileTimeType compileTimeType;
        KotlinType receiverExpressionType = ConstantExpressionEvaluatorKt.getReceiverExpressionType(resolvedCall);
        if (receiverExpressionType == null || (compileTimeType = getCompileTimeType(receiverExpressionType)) == null) {
            return null;
        }
        return createOperationArgument(expression, receiverExpressionType, compileTimeType);
    }

    private final OperationArgument createOperationArgumentForFirstParameter(ResolvedValueArgument argument, ValueParameterDescriptor parameter) {
        KtExpression argumentExpression;
        KotlinType type = parameter.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        CompileTimeType compileTimeType = getCompileTimeType(type);
        if (compileTimeType == null) {
            return null;
        }
        List<ValueArgument> arguments = argument.getArguments();
        Intrinsics.checkNotNullExpressionValue(arguments, "getArguments(...)");
        if (arguments.size() != 1 || (argumentExpression = ((ValueArgument) CollectionsKt.first((List) arguments)).getArgumentExpression()) == null) {
            return null;
        }
        KotlinType type2 = parameter.getType();
        Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
        return createOperationArgument(argumentExpression, type2, compileTimeType);
    }

    private final CompileTimeType getCompileTimeType(KotlinType c) {
        KotlinType kotlinTypeMakeNotNullable = TypeUtils.makeNotNullable(c);
        if (Intrinsics.areEqual(kotlinTypeMakeNotNullable, this.builtIns.getIntType())) {
            return CompileTimeType.INT;
        }
        if (Intrinsics.areEqual(kotlinTypeMakeNotNullable, this.builtIns.getByteType())) {
            return CompileTimeType.BYTE;
        }
        if (Intrinsics.areEqual(kotlinTypeMakeNotNullable, this.builtIns.getShortType())) {
            return CompileTimeType.SHORT;
        }
        if (Intrinsics.areEqual(kotlinTypeMakeNotNullable, this.builtIns.getLongType())) {
            return CompileTimeType.LONG;
        }
        if (Intrinsics.areEqual(kotlinTypeMakeNotNullable, this.builtIns.getDoubleType())) {
            return CompileTimeType.DOUBLE;
        }
        if (Intrinsics.areEqual(kotlinTypeMakeNotNullable, this.builtIns.getFloatType())) {
            return CompileTimeType.FLOAT;
        }
        if (Intrinsics.areEqual(kotlinTypeMakeNotNullable, this.builtIns.getCharType())) {
            return CompileTimeType.CHAR;
        }
        if (Intrinsics.areEqual(kotlinTypeMakeNotNullable, this.builtIns.getBooleanType())) {
            return CompileTimeType.BOOLEAN;
        }
        if (Intrinsics.areEqual(kotlinTypeMakeNotNullable, this.builtIns.getStringType())) {
            return CompileTimeType.STRING;
        }
        if (Intrinsics.areEqual(kotlinTypeMakeNotNullable, this.builtIns.getAnyType())) {
            return CompileTimeType.ANY;
        }
        return null;
    }

    private final OperationArgument createOperationArgument(KtExpression expression, KotlinType parameterType, CompileTimeType compileTimeType) {
        CompileTimeConstant<?> compileTimeConstantEvaluateExpression = this.constantExpressionEvaluator.evaluateExpression(expression, this.trace, parameterType);
        if (compileTimeConstantEvaluateExpression == null) {
            return null;
        }
        if ((compileTimeConstantEvaluateExpression instanceof TypedCompileTimeConstant) && !TypeUtilsKt.isSubtypeOf(((TypedCompileTimeConstant) compileTimeConstantEvaluateExpression).getType(), parameterType)) {
            return null;
        }
        ConstantValue<?> constantValue = compileTimeConstantEvaluateExpression.toConstantValue(parameterType);
        Object objBoxedValue = compileTimeType == CompileTimeType.ANY ? constantValue.boxedValue() : constantValue.getValue();
        if (objBoxedValue == null) {
            return null;
        }
        return new OperationArgument(objBoxedValue, compileTimeType, expression);
    }

    private final CompileTimeConstant<?> createConstant(Object value, KotlinType expectedType, CompileTimeConstant.Parameters parameters) {
        if (!parameters.isPure() && !parameters.isUnsignedNumberLiteral()) {
            ConstantValue constantValueCreateConstantValue$default = ConstantValueFactory.createConstantValue$default(ConstantValueFactory.INSTANCE, value, null, 2, null);
            return constantValueCreateConstantValue$default != null ? wrap(constantValueCreateConstantValue$default, parameters) : null;
        }
        if (expectedType == null) {
            SimpleType NO_EXPECTED_TYPE = TypeUtils.NO_EXPECTED_TYPE;
            Intrinsics.checkNotNullExpressionValue(NO_EXPECTED_TYPE, "NO_EXPECTED_TYPE");
            expectedType = NO_EXPECTED_TYPE;
        }
        return createCompileTimeConstant(value, parameters, expectedType);
    }

    private final CompileTimeConstant<?> createCompileTimeConstant(Object value, CompileTimeConstant.Parameters parameters, KotlinType expectedType) {
        if (value instanceof Byte ? true : value instanceof Short ? true : value instanceof Integer ? true : value instanceof Long) {
            Intrinsics.checkNotNull(value, "null cannot be cast to non-null type kotlin.Number");
            return createIntegerCompileTimeConstant(((Number) value).longValue(), parameters, expectedType);
        }
        ConstantValue constantValueCreateConstantValue$default = ConstantValueFactory.createConstantValue$default(ConstantValueFactory.INSTANCE, value, null, 2, null);
        return constantValueCreateConstantValue$default != null ? wrap(constantValueCreateConstantValue$default, parameters) : null;
    }

    private final CompileTimeConstant<?> createIntegerCompileTimeConstant(long value, CompileTimeConstant.Parameters parameters, KotlinType expectedType) {
        if (parameters.isUnsignedNumberLiteral() && !checkAccessibilityOfUnsignedTypes()) {
            return new UnsignedErrorValueTypeConstant(Long.valueOf(value), this.constantExpressionEvaluator.getModule(), parameters);
        }
        if (parameters.isUnsignedLongNumberLiteral()) {
            return wrap(new ULongValue(value), parameters);
        }
        if (TypeUtils.noExpectedType(expectedType) || KotlinTypeKt.isError(expectedType)) {
            return CompileTimeConstantKt.createIntegerValueTypeConstant(Long.valueOf(value), this.constantExpressionEvaluator.getModule(), parameters, this.languageVersionSettings.supportsFeature(LanguageFeature.NewInference));
        }
        ConstantValue<?> constantValueCreateIntegerConstantValue = ConstantValueFactory.INSTANCE.createIntegerConstantValue(value, expectedType, parameters.isUnsignedNumberLiteral());
        if (constantValueCreateIntegerConstantValue != null) {
            return wrap(constantValueCreateIntegerConstantValue, parameters);
        }
        return createSimpleIntCompileTimeConst(value, parameters);
    }

    private final TypedCompileTimeConstant<?> createSimpleIntCompileTimeConst(long j, CompileTimeConstant.Parameters parameters) {
        LongValue longValue;
        ConstantValue uIntValue;
        if (parameters.isUnsignedNumberLiteral()) {
            int i = (int) j;
            uIntValue = j == ConstantValueFactoryKt.fromUIntToLong(i) ? new UIntValue(i) : new ULongValue(j);
        } else {
            int i2 = (int) j;
            if (j == i2) {
                longValue = new IntValue(i2);
            } else {
                longValue = new LongValue(j);
            }
            uIntValue = longValue;
        }
        return wrap(uIntValue, parameters);
    }

    private final boolean checkAccessibilityOfUnsignedTypes() {
        ClassDescriptor classDescriptorFindClassAcrossModuleDependencies = FindClassInModuleKt.findClassAcrossModuleDependencies(this.constantExpressionEvaluator.getModule(), StandardNames.FqNames.uInt);
        if (classDescriptorFindClassAcrossModuleDependencies == null) {
            return false;
        }
        return !(SinceKotlinUtilKt.checkSinceKotlinVersionAccessibility(classDescriptorFindClassAcrossModuleDependencies, this.languageVersionSettings) instanceof SinceKotlinAccessibility.NotAccessible);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final <T> TypedCompileTimeConstant<T> wrap(ConstantValue<? extends T> constantValue, CompileTimeConstant.Parameters parameters) {
        return new TypedCompileTimeConstant<>(constantValue, this.constantExpressionEvaluator.getModule(), parameters);
    }

    static /* synthetic */ TypedCompileTimeConstant wrap$default(ConstantExpressionEvaluatorVisitor constantExpressionEvaluatorVisitor, ConstantValue constantValue, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, int i, Object obj) {
        return constantExpressionEvaluatorVisitor.wrap(constantValue, (i & 1) != 0 ? !(constantValue instanceof NullValue) : z, (i & 2) != 0 ? false : z2, (i & 4) != 0 ? false : z3, (i & 8) != 0 ? false : z4, (i & 16) != 0 ? false : z5, (i & 32) != 0 ? false : z6, (i & 64) == 0 ? z7 : false);
    }

    private final <T> TypedCompileTimeConstant<T> wrap(ConstantValue<? extends T> constantValue, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7) {
        return wrap(constantValue, new CompileTimeConstant.Parameters(z, z2, z3, z4, z5, z6, z7));
    }
}
