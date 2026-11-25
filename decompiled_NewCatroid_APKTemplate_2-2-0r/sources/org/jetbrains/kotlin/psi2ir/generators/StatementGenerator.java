package org.jetbrains.kotlin.psi2ir.generators;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.backend.common.BackendException;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptorWithAccessors;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.Scope;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrConst;
import org.jetbrains.kotlin.ir.expressions.IrConstKind;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrStatementContainer;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.impl.IrBlockImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrCompositeImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrErrorExpressionImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetObjectValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrReturnImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrStringConcatenationImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrThrowImpl;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.DescriptorSymbolTableExtension;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.ir.util.TypeTranslator;
import org.jetbrains.kotlin.psi.KtArrayAccessExpression;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.psi.KtBinaryExpressionWithTypeRHS;
import org.jetbrains.kotlin.psi.KtBlockExpression;
import org.jetbrains.kotlin.psi.KtBreakExpression;
import org.jetbrains.kotlin.psi.KtCallExpression;
import org.jetbrains.kotlin.psi.KtCallableReferenceExpression;
import org.jetbrains.kotlin.psi.KtClassLiteralExpression;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtConstantExpression;
import org.jetbrains.kotlin.psi.KtContinueExpression;
import org.jetbrains.kotlin.psi.KtDeclarationWithBody;
import org.jetbrains.kotlin.psi.KtDestructuringDeclaration;
import org.jetbrains.kotlin.psi.KtDestructuringDeclarationEntry;
import org.jetbrains.kotlin.psi.KtDoWhileExpression;
import org.jetbrains.kotlin.psi.KtDotQualifiedExpression;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtEscapeStringTemplateEntry;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtForExpression;
import org.jetbrains.kotlin.psi.KtFunctionLiteral;
import org.jetbrains.kotlin.psi.KtIfExpression;
import org.jetbrains.kotlin.psi.KtIsExpression;
import org.jetbrains.kotlin.psi.KtLambdaExpression;
import org.jetbrains.kotlin.psi.KtLiteralStringTemplateEntry;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtObjectLiteralExpression;
import org.jetbrains.kotlin.psi.KtPostfixExpression;
import org.jetbrains.kotlin.psi.KtPrefixExpression;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtPropertyDelegate;
import org.jetbrains.kotlin.psi.KtQualifiedExpression;
import org.jetbrains.kotlin.psi.KtReferenceExpression;
import org.jetbrains.kotlin.psi.KtReturnExpression;
import org.jetbrains.kotlin.psi.KtSafeQualifiedExpression;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.KtStringTemplateEntry;
import org.jetbrains.kotlin.psi.KtStringTemplateEntryWithExpression;
import org.jetbrains.kotlin.psi.KtStringTemplateExpression;
import org.jetbrains.kotlin.psi.KtThisExpression;
import org.jetbrains.kotlin.psi.KtThrowExpression;
import org.jetbrains.kotlin.psi.KtTryExpression;
import org.jetbrains.kotlin.psi.KtTypeAlias;
import org.jetbrains.kotlin.psi.KtVisitor;
import org.jetbrains.kotlin.psi.KtWhenExpression;
import org.jetbrains.kotlin.psi.KtWhileExpression;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;
import org.jetbrains.kotlin.psi2ir.KotlinUtilsKt;
import org.jetbrains.kotlin.psi2ir.intermediate.CallBuilder;
import org.jetbrains.kotlin.psi2ir.intermediate.CallBuilderKt;
import org.jetbrains.kotlin.psi2ir.intermediate.IntermediateValue;
import org.jetbrains.kotlin.psi2ir.intermediate.RematerializableValueKt;
import org.jetbrains.kotlin.psi2ir.intermediate.VariableLValue;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingContextUtils;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.VariableAsFunctionResolvedCall;
import org.jetbrains.kotlin.resolve.calls.smartcasts.ExplicitSmartCasts;
import org.jetbrains.kotlin.resolve.constants.CompileTimeConstant;
import org.jetbrains.kotlin.resolve.constants.evaluate.ConstantExpressionEvaluator;
import org.jetbrains.kotlin.resolve.scopes.receivers.ContextClassReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.expressions.ExpressionTypingUtils;
import org.jetbrains.kotlin.util.OperatorNameConventions;
import org.jetbrains.kotlin.util.slicedMap.ReadOnlySlice;
import org.jetbrains.kotlin.util.slicedMap.WritableSlice;

/* JADX INFO: compiled from: StatementGenerator.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000¤\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u00012\u00020\u0004B\u0015\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ&\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u001fJ\u001a\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$2\n\u0010%\u001a\u0006\u0012\u0002\b\u00030&J\u000e\u0010'\u001a\u00020\"2\u0006\u0010(\u001a\u00020)J&\u0010*\u001a\u00020\"2\u0006\u0010+\u001a\u00020\u00132\u0006\u0010#\u001a\u00020$2\f\u0010,\u001a\b\u0012\u0002\b\u0003\u0018\u00010-H\u0002J(\u0010.\u001a\u00020\u00022\u0006\u0010/\u001a\u0002002\u0006\u00101\u001a\u0002022\u0006\u00103\u001a\u0002042\u0006\u00105\u001a\u000206H\u0002J\u000e\u00107\u001a\u00020\u00022\u0006\u0010(\u001a\u00020)J\"\u00108\u001a\b\u0012\u0004\u0012\u00020\u0002092\f\u0010:\u001a\b\u0012\u0004\u0012\u00020$0;2\u0006\u0010<\u001a\u00020\u001dJ&\u0010=\u001a\u00020\"2\u0006\u0010>\u001a\u00020?2\u0006\u0010@\u001a\u00020?2\u0006\u0010A\u001a\u00020B2\u0006\u0010C\u001a\u00020DJ\u0010\u0010E\u001a\u00020F2\u0006\u0010#\u001a\u00020GH\u0002J\u0010\u0010H\u001a\u00020I2\u0006\u0010C\u001a\u00020DH\u0002J\b\u0010J\u001a\u00020FH\u0002J\u001a\u0010K\u001a\u00020\u00022\u0006\u0010#\u001a\u00020L2\b\u0010M\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010N\u001a\u00020\u00022\u0006\u0010#\u001a\u00020O2\b\u0010M\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010P\u001a\u00020\u00022\u0006\u0010#\u001a\u00020Q2\b\u0010M\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010R\u001a\u00020\u00022\u0006\u0010#\u001a\u00020S2\b\u0010M\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010T\u001a\u00020\u00022\u0006\u0010#\u001a\u00020U2\b\u0010M\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010V\u001a\u00020\u00022\u0006\u0010#\u001a\u00020W2\b\u0010M\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010X\u001a\u00020\u00022\u0006\u0010#\u001a\u00020Y2\b\u0010M\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010Z\u001a\u00020\u00022\u0006\u0010#\u001a\u00020[2\b\u0010M\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010\\\u001a\u00020\u00022\u0006\u0010]\u001a\u00020^2\b\u0010M\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010_\u001a\u00020\"2\u0006\u0010#\u001a\u00020`2\b\u0010M\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010a\u001a\u00020\u00022\u0006\u0010#\u001a\u00020b2\b\u0010M\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010c\u001a\u00020\u00022\u0006\u0010\u001a\u001a\u00020\u001b2\b\u0010M\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010d\u001a\u00020\u00022\u0006\u0010#\u001a\u00020e2\b\u0010M\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010f\u001a\u00020\u00022\u0006\u0010#\u001a\u00020g2\b\u0010M\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010h\u001a\u00020\u00022\u0006\u0010i\u001a\u00020j2\b\u0010M\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010k\u001a\u00020\u00022\u0006\u0010#\u001a\u00020$2\b\u0010M\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010l\u001a\u00020\u00022\u0006\u0010#\u001a\u00020m2\b\u0010M\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010n\u001a\u00020\u00022\u0006\u0010#\u001a\u00020o2\b\u0010M\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010p\u001a\u00020\u00022\u0006\u0010#\u001a\u00020q2\b\u0010M\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010r\u001a\u00020\u00022\u0006\u0010#\u001a\u00020s2\b\u0010M\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010t\u001a\u00020\u00022\u0006\u0010i\u001a\u00020u2\b\u0010M\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010v\u001a\u00020\u00022\u0006\u0010w\u001a\u00020x2\b\u0010M\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010y\u001a\u00020\u00022\u0006\u0010#\u001a\u00020z2\b\u0010M\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010{\u001a\u00020\u00022\u0006\u0010#\u001a\u00020|2\b\u0010M\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010}\u001a\u00020\u00022\u0006\u0010#\u001a\u00020~2\b\u0010M\u001a\u0004\u0018\u00010\u0003H\u0016J\u001b\u0010\u007f\u001a\u00020\u00022\u0007\u0010\u0080\u0001\u001a\u0002002\b\u0010M\u001a\u0004\u0018\u00010\u0003H\u0016J\u001b\u0010\u0081\u0001\u001a\u00020\u00022\u0006\u0010#\u001a\u00020G2\b\u0010M\u001a\u0004\u0018\u00010\u0003H\u0016J\u001c\u0010\u0082\u0001\u001a\u00020\u00022\u0007\u0010#\u001a\u00030\u0083\u00012\b\u0010M\u001a\u0004\u0018\u00010\u0003H\u0016J\u001c\u0010\u0084\u0001\u001a\u00020\"2\u0007\u0010#\u001a\u00030\u0085\u00012\b\u0010M\u001a\u0004\u0018\u00010\u0003H\u0016J\u001c\u0010\u0086\u0001\u001a\u00020\u00022\u0007\u0010i\u001a\u00030\u0087\u00012\b\u0010M\u001a\u0004\u0018\u00010\u0003H\u0016J\u001c\u0010\u0088\u0001\u001a\u00020\u00022\u0007\u0010#\u001a\u00030\u0089\u00012\b\u0010M\u001a\u0004\u0018\u00010\u0003H\u0016J\u001c\u0010\u008a\u0001\u001a\u00020\"2\u0007\u0010#\u001a\u00030\u008b\u00012\b\u0010M\u001a\u0004\u0018\u00010\u0003H\u0016J\u001c\u0010\u008c\u0001\u001a\u00020\u00022\u0007\u0010#\u001a\u00030\u008d\u00012\b\u0010M\u001a\u0004\u0018\u00010\u0003H\u0016J\u001c\u0010\u008e\u0001\u001a\u00020\u00022\u0007\u0010#\u001a\u00030\u008f\u00012\b\u0010M\u001a\u0004\u0018\u00010\u0003H\u0016J\u001d\u0010\u0090\u0001\u001a\u00020\u00022\b\u0010\u0091\u0001\u001a\u00030\u0092\u00012\b\u0010M\u001a\u0004\u0018\u00010\u0003H\u0016J\u001c\u0010\u0093\u0001\u001a\u00020\u00022\u0007\u0010#\u001a\u00030\u0094\u00012\b\u0010M\u001a\u0004\u0018\u00010\u0003H\u0016J\u001c\u0010\u0095\u0001\u001a\u00020\u00022\u0007\u0010#\u001a\u00030\u0096\u00012\b\u0010M\u001a\u0004\u0018\u00010\u0003H\u0016J\u0019\u0010\u0097\u0001\u001a\b\u0012\u0004\u0012\u00020\"0;*\b\u0012\u0004\u0012\u00020\"0;H\u0002J\f\u0010\u0098\u0001\u001a\u00030\u0099\u0001*\u00020BR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0012\u001a\u00020\u00138F¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u009a\u0001"}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/generators/StatementGenerator;", "Lorg/jetbrains/kotlin/psi/KtVisitor;", "Lorg/jetbrains/kotlin/ir/IrStatement;", "", "Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorWithScope;", "bodyGenerator", "Lorg/jetbrains/kotlin/psi2ir/generators/BodyGenerator;", "scope", "Lorg/jetbrains/kotlin/ir/builders/Scope;", "(Lorg/jetbrains/kotlin/psi2ir/generators/BodyGenerator;Lorg/jetbrains/kotlin/ir/builders/Scope;)V", "getBodyGenerator", "()Lorg/jetbrains/kotlin/psi2ir/generators/BodyGenerator;", "context", "Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorContext;", "getContext", "()Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorContext;", "getScope", "()Lorg/jetbrains/kotlin/ir/builders/Scope;", "scopeOwner", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "getScopeOwner", "()Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "typeTranslator", "Lorg/jetbrains/kotlin/ir/util/TypeTranslator;", "declareComponentVariablesInBlock", "", "multiDeclaration", "Lorg/jetbrains/kotlin/psi/KtDestructuringDeclaration;", "irBlock", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementContainer;", "firstContainerValue", "Lorg/jetbrains/kotlin/psi2ir/intermediate/IntermediateValue;", "restContainerValue", "generateConstantExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "expression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "constant", "Lorg/jetbrains/kotlin/resolve/constants/CompileTimeConstant;", "generateExpression", "ktElement", "Lorg/jetbrains/kotlin/psi/KtElement;", "generateExpressionForReferencedDescriptor", "descriptor", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "generateLocalDelegatedProperty", "ktProperty", "Lorg/jetbrains/kotlin/psi/KtProperty;", "ktDelegate", "Lorg/jetbrains/kotlin/psi/KtPropertyDelegate;", "variableDescriptor", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptorWithAccessors;", "scopeOwnerSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "generateStatement", "generateStatements", "", "ktStatements", "", "to", "generateThisReceiver", "startOffset", "", "endOffset", "kotlinType", "Lorg/jetbrains/kotlin/types/KotlinType;", "classDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "getReturnExpressionTarget", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "Lorg/jetbrains/kotlin/psi/KtReturnExpression;", "isThisForClassPhysicallyAvailable", "", "scopeOwnerAsCallable", "visitArrayAccessExpression", "Lorg/jetbrains/kotlin/psi/KtArrayAccessExpression;", "data", "visitBinaryExpression", "Lorg/jetbrains/kotlin/psi/KtBinaryExpression;", "visitBinaryWithTypeRHSExpression", "Lorg/jetbrains/kotlin/psi/KtBinaryExpressionWithTypeRHS;", "visitBlockExpression", "Lorg/jetbrains/kotlin/psi/KtBlockExpression;", "visitBreakExpression", "Lorg/jetbrains/kotlin/psi/KtBreakExpression;", "visitCallExpression", "Lorg/jetbrains/kotlin/psi/KtCallExpression;", "visitCallableReferenceExpression", "Lorg/jetbrains/kotlin/psi/KtCallableReferenceExpression;", "visitClassLiteralExpression", "Lorg/jetbrains/kotlin/psi/KtClassLiteralExpression;", "visitClassOrObject", "classOrObject", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "visitConstantExpression", "Lorg/jetbrains/kotlin/psi/KtConstantExpression;", "visitContinueExpression", "Lorg/jetbrains/kotlin/psi/KtContinueExpression;", "visitDestructuringDeclaration", "visitDoWhileExpression", "Lorg/jetbrains/kotlin/psi/KtDoWhileExpression;", "visitDotQualifiedExpression", "Lorg/jetbrains/kotlin/psi/KtDotQualifiedExpression;", "visitEscapeStringTemplateEntry", "entry", "Lorg/jetbrains/kotlin/psi/KtEscapeStringTemplateEntry;", "visitExpression", "visitForExpression", "Lorg/jetbrains/kotlin/psi/KtForExpression;", "visitIfExpression", "Lorg/jetbrains/kotlin/psi/KtIfExpression;", "visitIsExpression", "Lorg/jetbrains/kotlin/psi/KtIsExpression;", "visitLambdaExpression", "Lorg/jetbrains/kotlin/psi/KtLambdaExpression;", "visitLiteralStringTemplateEntry", "Lorg/jetbrains/kotlin/psi/KtLiteralStringTemplateEntry;", "visitNamedFunction", "function", "Lorg/jetbrains/kotlin/psi/KtNamedFunction;", "visitObjectLiteralExpression", "Lorg/jetbrains/kotlin/psi/KtObjectLiteralExpression;", "visitPostfixExpression", "Lorg/jetbrains/kotlin/psi/KtPostfixExpression;", "visitPrefixExpression", "Lorg/jetbrains/kotlin/psi/KtPrefixExpression;", "visitProperty", "property", "visitReturnExpression", "visitSafeQualifiedExpression", "Lorg/jetbrains/kotlin/psi/KtSafeQualifiedExpression;", "visitSimpleNameExpression", "Lorg/jetbrains/kotlin/psi/KtSimpleNameExpression;", "visitStringTemplateEntryWithExpression", "Lorg/jetbrains/kotlin/psi/KtStringTemplateEntryWithExpression;", "visitStringTemplateExpression", "Lorg/jetbrains/kotlin/psi/KtStringTemplateExpression;", "visitThisExpression", "Lorg/jetbrains/kotlin/psi/KtThisExpression;", "visitThrowExpression", "Lorg/jetbrains/kotlin/psi/KtThrowExpression;", "visitTryExpression", "Lorg/jetbrains/kotlin/psi/KtTryExpression;", "visitTypeAlias", "typeAlias", "Lorg/jetbrains/kotlin/psi/KtTypeAlias;", "visitWhenExpression", "Lorg/jetbrains/kotlin/psi/KtWhenExpression;", "visitWhileExpression", "Lorg/jetbrains/kotlin/psi/KtWhileExpression;", "postprocessStringTemplateEntries", "toIrType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class StatementGenerator extends KtVisitor implements GeneratorWithScope {
    private final BodyGenerator bodyGenerator;
    private final Scope scope;
    private final TypeTranslator typeTranslator;

    public final BodyGenerator getBodyGenerator() {
        return this.bodyGenerator;
    }

    @Override // org.jetbrains.kotlin.ir.builders.IrGeneratorWithScope
    public Scope getScope() {
        return this.scope;
    }

    public StatementGenerator(BodyGenerator bodyGenerator, Scope scope) {
        Intrinsics.checkNotNullParameter(bodyGenerator, "bodyGenerator");
        Intrinsics.checkNotNullParameter(scope, "scope");
        this.bodyGenerator = bodyGenerator;
        this.scope = scope;
        this.typeTranslator = getContext().getTypeTranslator();
    }

    @Override // org.jetbrains.kotlin.ir.builders.IrGenerator
    public GeneratorContext getContext() {
        return this.bodyGenerator.getContext();
    }

    public final DeclarationDescriptor getScopeOwner() {
        return this.bodyGenerator.getScopeOwner();
    }

    public final IrType toIrType(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        return this.typeTranslator.translateType(kotlinType);
    }

    public final IrStatement generateStatement(KtElement ktElement) {
        Intrinsics.checkNotNullParameter(ktElement, "ktElement");
        try {
            return (IrStatement) KotlinUtilsKt.deparenthesize(ktElement).accept(this, null);
        } catch (BackendException e) {
            throw e;
        } catch (ErrorExpressionException e2) {
            throw e2;
        } catch (Throwable th) {
            return new ErrorExpressionGenerator(this).generateErrorExpression(ktElement, th);
        }
    }

    public final List<IrStatement> generateStatements(List<? extends KtExpression> ktStatements, IrStatementContainer to) {
        Intrinsics.checkNotNullParameter(ktStatements, "ktStatements");
        Intrinsics.checkNotNullParameter(to, "to");
        List<IrStatement> statements = to.getStatements();
        Iterator<T> it = ktStatements.iterator();
        while (it.hasNext()) {
            statements.add(generateStatement((KtExpression) it.next()));
        }
        return statements;
    }

    public final IrExpression generateExpression(KtElement ktElement) {
        Intrinsics.checkNotNullParameter(ktElement, "ktElement");
        IrStatement irStatementGenerateStatement = generateStatement(ktElement);
        if (irStatementGenerateStatement instanceof IrExpression) {
            return (IrExpression) irStatementGenerateStatement;
        }
        if (irStatementGenerateStatement instanceof IrDeclaration) {
            return new IrBlockImpl(irStatementGenerateStatement.getStartOffset(), irStatementGenerateStatement.getEndOffset(), getContext().getIrBuiltIns().getUnitType(), null, CollectionsKt.listOf(irStatementGenerateStatement));
        }
        throw new AssertionError("Unexpected statement: " + RenderIrElementKt.render$default(irStatementGenerateStatement, (DumpIrTreeOptions) null, 1, (Object) null));
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public IrStatement visitExpression(KtExpression expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        KtExpression ktExpression = expression;
        int startOffsetSkippingComments = PsiUtilsKt.getStartOffsetSkippingComments(ktExpression);
        int endOffset = PsiUtilsKt.getEndOffset(ktExpression);
        IrType nothingType = getContext().getIrBuiltIns().getNothingType();
        String simpleName = expression.getClass().getSimpleName();
        Intrinsics.checkNotNullExpressionValue(simpleName, "getSimpleName(...)");
        return new IrErrorExpressionImpl(startOffsetSkippingComments, endOffset, nothingType, simpleName);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public IrStatement visitProperty(KtProperty property, Void data) {
        KtProperty nameIdentifier;
        Intrinsics.checkNotNullParameter(property, "property");
        WritableSlice<PsiElement, VariableDescriptor> VARIABLE = BindingContext.VARIABLE;
        Intrinsics.checkNotNullExpressionValue(VARIABLE, "VARIABLE");
        VariableDescriptor variableDescriptor = (VariableDescriptor) GeneratorKt.getOrFail(this, VARIABLE, property);
        KtPropertyDelegate delegate = property.getDelegate();
        if (delegate != null) {
            Intrinsics.checkNotNull(variableDescriptor, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.VariableDescriptorWithAccessors");
            return generateLocalDelegatedProperty(property, delegate, (VariableDescriptorWithAccessors) variableDescriptor, this.bodyGenerator.getScopeOwnerSymbol());
        }
        if (!getContext().getExtensions().getDebugInfoOnlyOnVariablesInDestructuringDeclarations() || (nameIdentifier = property.mo7392getNameIdentifier()) == null) {
            nameIdentifier = property;
        }
        DescriptorSymbolTableExtension descriptorExtension = getContext().getSymbolTable().getDescriptorExtension();
        int startOffsetSkippingComments = PsiUtilsKt.getStartOffsetSkippingComments(nameIdentifier);
        int endOffset = PsiUtilsKt.getEndOffset(nameIdentifier);
        IrDeclarationOrigin.DEFINED defined = IrDeclarationOrigin.DEFINED.INSTANCE;
        Intrinsics.checkNotNull(variableDescriptor);
        KotlinType type = variableDescriptor.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        IrType irType = toIrType(type);
        KtExpression initializer = property.getInitializer();
        return descriptorExtension.declareVariable(startOffsetSkippingComments, endOffset, defined, variableDescriptor, irType, initializer != null ? generateExpression(initializer) : null);
    }

    private final IrStatement generateLocalDelegatedProperty(KtProperty ktProperty, KtPropertyDelegate ktDelegate, VariableDescriptorWithAccessors variableDescriptor, IrSymbol scopeOwnerSymbol) {
        return new DelegatedPropertyGenerator(getContext()).generateLocalDelegatedProperty(ktProperty, ktDelegate, variableDescriptor, scopeOwnerSymbol);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public IrStatement visitDestructuringDeclaration(KtDestructuringDeclaration multiDeclaration, Void data) {
        Pair pair;
        Intrinsics.checkNotNullParameter(multiDeclaration, "multiDeclaration");
        if (getContext().getExtensions().getDebugInfoOnlyOnVariablesInDestructuringDeclarations()) {
            pair = TuplesKt.to(-2, -2);
        } else {
            KtDestructuringDeclaration ktDestructuringDeclaration = multiDeclaration;
            pair = TuplesKt.to(Integer.valueOf(PsiUtilsKt.getStartOffsetSkippingComments(ktDestructuringDeclaration)), Integer.valueOf(PsiUtilsKt.getEndOffset(ktDestructuringDeclaration)));
        }
        IrCompositeImpl irCompositeImpl = new IrCompositeImpl(((Number) pair.component1()).intValue(), ((Number) pair.component2()).intValue(), getContext().getIrBuiltIns().getUnitType(), IrStatementOrigin.DESTRUCTURING_DECLARATION.INSTANCE);
        KtExpression initializer = multiDeclaration.getInitializer();
        Intrinsics.checkNotNull(initializer);
        IrExpression irExpressionGenerateExpression = generateExpression(initializer);
        IrCompositeImpl irCompositeImpl2 = irCompositeImpl;
        IrVariable irVariableDeclareTemporaryVariableInBlock$default = RematerializableValueKt.declareTemporaryVariableInBlock$default(getScope(), irExpressionGenerateExpression, irCompositeImpl2, "container", 0, 0, 24, null);
        VariableLValue variableLValue = new VariableLValue(getContext(), irVariableDeclareTemporaryVariableInBlock$default, null, 0, 0, 28, null);
        VariableLValue variableLValue2 = variableLValue;
        if (getContext().getExtensions().getDebugInfoOnlyOnVariablesInDestructuringDeclarations()) {
            variableLValue = new VariableLValue(getContext(), irVariableDeclareTemporaryVariableInBlock$default, null, -2, -2, 4, null);
        }
        declareComponentVariablesInBlock(multiDeclaration, irCompositeImpl2, variableLValue2, variableLValue);
        return irCompositeImpl;
    }

    public final void declareComponentVariablesInBlock(KtDestructuringDeclaration multiDeclaration, IrStatementContainer irBlock, IntermediateValue firstContainerValue, IntermediateValue restContainerValue) {
        Pair pair;
        KtDestructuringDeclarationEntry ktDestructuringDeclarationEntryMo7392getNameIdentifier;
        Intrinsics.checkNotNullParameter(multiDeclaration, "multiDeclaration");
        Intrinsics.checkNotNullParameter(irBlock, "irBlock");
        IntermediateValue firstContainerValue2 = firstContainerValue;
        Intrinsics.checkNotNullParameter(firstContainerValue2, "firstContainerValue");
        Intrinsics.checkNotNullParameter(restContainerValue, "restContainerValue");
        CallGenerator callGenerator = new CallGenerator(this);
        int i = 0;
        for (KtDestructuringDeclarationEntry ktDestructuringDeclarationEntry : multiDeclaration.getEntries()) {
            i++;
            StatementGenerator statementGenerator = this;
            WritableSlice<PsiElement, VariableDescriptor> VARIABLE = BindingContext.VARIABLE;
            Intrinsics.checkNotNullExpressionValue(VARIABLE, "VARIABLE");
            VariableDescriptor variableDescriptor = (VariableDescriptor) GeneratorKt.getOrFail(statementGenerator, VARIABLE, ktDestructuringDeclarationEntry);
            if (!variableDescriptor.getShortName().isSpecial()) {
                WritableSlice<KtDestructuringDeclarationEntry, ResolvedCall<FunctionDescriptor>> COMPONENT_RESOLVED_CALL = BindingContext.COMPONENT_RESOLVED_CALL;
                Intrinsics.checkNotNullExpressionValue(COMPONENT_RESOLVED_CALL, "COMPONENT_RESOLVED_CALL");
                ResolvedCall resolvedCall = (ResolvedCall) GeneratorKt.getOrFail(statementGenerator, COMPONENT_RESOLVED_CALL, ktDestructuringDeclarationEntry);
                Intrinsics.checkNotNull(resolvedCall);
                CallBuilder callBuilderPregenerateCall = ArgumentsGenerationUtilsKt.pregenerateCall(this, resolvedCall);
                CallBuilderKt.setExplicitReceiverValue(callBuilderPregenerateCall, firstContainerValue2);
                if (getContext().getExtensions().getDebugInfoOnlyOnVariablesInDestructuringDeclarations()) {
                    pair = TuplesKt.to(-2, -2);
                } else {
                    Intrinsics.checkNotNull(ktDestructuringDeclarationEntry);
                    KtDestructuringDeclarationEntry ktDestructuringDeclarationEntry2 = ktDestructuringDeclarationEntry;
                    pair = TuplesKt.to(Integer.valueOf(PsiUtilsKt.getStartOffsetSkippingComments(ktDestructuringDeclarationEntry2)), Integer.valueOf(PsiUtilsKt.getEndOffset(ktDestructuringDeclarationEntry2)));
                }
                IrExpression irExpressionGenerateCall = callGenerator.generateCall(((Number) pair.component1()).intValue(), ((Number) pair.component2()).intValue(), callBuilderPregenerateCall, IrStatementOrigin.COMPONENT_N.INSTANCE.withIndex(i));
                if (getContext().getExtensions().getDebugInfoOnlyOnVariablesInDestructuringDeclarations()) {
                    ktDestructuringDeclarationEntryMo7392getNameIdentifier = ktDestructuringDeclarationEntry.mo7392getNameIdentifier();
                    if (ktDestructuringDeclarationEntryMo7392getNameIdentifier == null) {
                        ktDestructuringDeclarationEntryMo7392getNameIdentifier = ktDestructuringDeclarationEntry;
                    }
                    Intrinsics.checkNotNull(ktDestructuringDeclarationEntryMo7392getNameIdentifier);
                } else {
                    Intrinsics.checkNotNull(ktDestructuringDeclarationEntry);
                    ktDestructuringDeclarationEntryMo7392getNameIdentifier = ktDestructuringDeclarationEntry;
                }
                DescriptorSymbolTableExtension descriptorExtension = getContext().getSymbolTable().getDescriptorExtension();
                int startOffsetSkippingComments = PsiUtilsKt.getStartOffsetSkippingComments(ktDestructuringDeclarationEntryMo7392getNameIdentifier);
                int endOffset = PsiUtilsKt.getEndOffset(ktDestructuringDeclarationEntryMo7392getNameIdentifier);
                IrDeclarationOrigin.DEFINED defined = IrDeclarationOrigin.DEFINED.INSTANCE;
                Intrinsics.checkNotNull(variableDescriptor);
                KotlinType type = variableDescriptor.getType();
                Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                irBlock.getStatements().add(descriptorExtension.declareVariable(startOffsetSkippingComments, endOffset, defined, variableDescriptor, toIrType(type), irExpressionGenerateCall));
                firstContainerValue2 = restContainerValue;
            }
        }
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public IrStatement visitBlockExpression(KtBlockExpression expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        if ((expression.getLightParent() instanceof KtDeclarationWithBody) && !(expression.getLightParent() instanceof KtFunctionLiteral)) {
            throw new AssertionError("Use IrBlockBody and corresponding body generator to generate blocks as function bodies");
        }
        KtBlockExpression ktBlockExpression = expression;
        IrBlockImpl irBlockImpl = new IrBlockImpl(PsiUtilsKt.getStartOffsetSkippingComments(ktBlockExpression), PsiUtilsKt.getEndOffset(ktBlockExpression), toIrType(GeneratorKt.getExpressionTypeWithCoercionToUnitOrFail(this, expression)), null, 8, null);
        List<KtExpression> statements = expression.getStatements();
        Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
        for (KtExpression ktExpression : statements) {
            List<IrStatement> statements2 = irBlockImpl.getStatements();
            Intrinsics.checkNotNull(ktExpression);
            statements2.add(generateStatement(ktExpression));
        }
        return irBlockImpl;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public IrStatement visitReturnExpression(KtReturnExpression expression, Void data) {
        IrGetObjectValueImpl irGetObjectValueImpl;
        Intrinsics.checkNotNullParameter(expression, "expression");
        CallableDescriptor returnExpressionTarget = getReturnExpressionTarget(expression);
        KtExpression returnedExpression = expression.getReturnedExpression();
        if (returnedExpression == null || (irGetObjectValueImpl = generateExpression(returnedExpression)) == null) {
            KtReturnExpression ktReturnExpression = expression;
            irGetObjectValueImpl = new IrGetObjectValueImpl(PsiUtilsKt.getStartOffsetSkippingComments(ktReturnExpression), PsiUtilsKt.getEndOffset(ktReturnExpression), getContext().getIrBuiltIns().getUnitType(), getContext().getIrBuiltIns().getUnitClass());
        }
        KtReturnExpression ktReturnExpression2 = expression;
        return new IrReturnImpl(PsiUtilsKt.getStartOffsetSkippingComments(ktReturnExpression2), PsiUtilsKt.getEndOffset(ktReturnExpression2), getContext().getIrBuiltIns().getNothingType(), IrUtilsKt.referenceFunction(getContext().getSymbolTable(), returnExpressionTarget), irGetObjectValueImpl);
    }

    private final CallableDescriptor scopeOwnerAsCallable() {
        DeclarationDescriptor scopeOwner = getScopeOwner();
        CallableDescriptor callableDescriptor = scopeOwner instanceof CallableDescriptor ? (CallableDescriptor) scopeOwner : null;
        if (callableDescriptor != null) {
            return callableDescriptor;
        }
        throw new AssertionError("'return' in a non-callable: " + getScopeOwner());
    }

    private final CallableDescriptor getReturnExpressionTarget(KtReturnExpression expression) {
        FunctionDescriptor functionDescriptorScopeOwnerAsCallable;
        if (!ExpressionTypingUtils.isFunctionLiteral(getScopeOwner()) && !ExpressionTypingUtils.isFunctionExpression(getScopeOwner())) {
            return scopeOwnerAsCallable();
        }
        KtSimpleNameExpression targetLabel = expression.getTargetLabel();
        if (targetLabel != null) {
            StatementGenerator statementGenerator = this;
            WritableSlice<KtReferenceExpression, PsiElement> LABEL_TARGET = BindingContext.LABEL_TARGET;
            Intrinsics.checkNotNullExpressionValue(LABEL_TARGET, "LABEL_TARGET");
            PsiElement psiElement = (PsiElement) GeneratorKt.getOrFail(statementGenerator, LABEL_TARGET, targetLabel);
            ReadOnlySlice<PsiElement, DeclarationDescriptor> DECLARATION_TO_DESCRIPTOR = BindingContext.DECLARATION_TO_DESCRIPTOR;
            Intrinsics.checkNotNullExpressionValue(DECLARATION_TO_DESCRIPTOR, "DECLARATION_TO_DESCRIPTOR");
            DeclarationDescriptor declarationDescriptor = (DeclarationDescriptor) GeneratorKt.getOrFail(statementGenerator, DECLARATION_TO_DESCRIPTOR, psiElement);
            Intrinsics.checkNotNull(declarationDescriptor, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.CallableDescriptor");
            functionDescriptorScopeOwnerAsCallable = (CallableDescriptor) declarationDescriptor;
        } else if (ExpressionTypingUtils.isFunctionLiteral(getScopeOwner())) {
            functionDescriptorScopeOwnerAsCallable = BindingContextUtils.getContainingFunctionSkipFunctionLiterals(getScopeOwner(), true).first;
        } else {
            functionDescriptorScopeOwnerAsCallable = scopeOwnerAsCallable();
        }
        Intrinsics.checkNotNull(functionDescriptorScopeOwnerAsCallable);
        return functionDescriptorScopeOwnerAsCallable;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public IrStatement visitThrowExpression(KtThrowExpression expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        KtThrowExpression ktThrowExpression = expression;
        int startOffsetSkippingComments = PsiUtilsKt.getStartOffsetSkippingComments(ktThrowExpression);
        int endOffset = PsiUtilsKt.getEndOffset(ktThrowExpression);
        IrType nothingType = getContext().getIrBuiltIns().getNothingType();
        KtExpression thrownExpression = expression.getThrownExpression();
        Intrinsics.checkNotNull(thrownExpression);
        return new IrThrowImpl(startOffsetSkippingComments, endOffset, nothingType, generateExpression(thrownExpression));
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public IrExpression visitConstantExpression(KtConstantExpression expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        KtConstantExpression ktConstantExpression = expression;
        CompileTimeConstant<?> constant = ConstantExpressionEvaluator.INSTANCE.getConstant(ktConstantExpression, getContext().getBindingContext());
        if (constant != null) {
            return generateConstantExpression(ktConstantExpression, constant);
        }
        throw new IllegalStateException(("KtConstantExpression was not evaluated: " + expression.getText()).toString());
    }

    public final IrExpression generateConstantExpression(KtExpression expression, CompileTimeConstant<?> constant) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(constant, "constant");
        KtExpression ktExpression = expression;
        return getContext().getConstantValueGenerator().generateConstantValueAsExpression(PsiUtilsKt.getStartOffsetSkippingComments(ktExpression), PsiUtilsKt.getEndOffset(ktExpression), constant.toConstantValue(GeneratorKt.getTypeInferredByFrontendOrFail(this, expression)));
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public IrStatement visitStringTemplateExpression(KtStringTemplateExpression expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        KtStringTemplateExpression ktStringTemplateExpression = expression;
        int startOffsetSkippingComments = PsiUtilsKt.getStartOffsetSkippingComments(ktStringTemplateExpression);
        int endOffset = PsiUtilsKt.getEndOffset(ktStringTemplateExpression);
        IrType irType = toIrType(GeneratorKt.getTypeInferredByFrontendOrFail(this, expression));
        KtStringTemplateEntry[] entries = expression.getEntries();
        Intrinsics.checkNotNullExpressionValue(entries, "getEntries(...)");
        KtStringTemplateEntry[] ktStringTemplateEntryArr = entries;
        ArrayList arrayList = new ArrayList(ktStringTemplateEntryArr.length);
        for (KtStringTemplateEntry ktStringTemplateEntry : ktStringTemplateEntryArr) {
            Intrinsics.checkNotNull(ktStringTemplateEntry);
            arrayList.add(generateExpression(ktStringTemplateEntry));
        }
        List<IrExpression> listPostprocessStringTemplateEntries = postprocessStringTemplateEntries(arrayList);
        int size = listPostprocessStringTemplateEntries.size();
        if (size == 0) {
            return IrConstImpl.INSTANCE.string(startOffsetSkippingComments, endOffset, irType, "");
        }
        if (size == 1) {
            IrStringConcatenationImpl irStringConcatenationImpl = (IrExpression) CollectionsKt.first((List) listPostprocessStringTemplateEntries);
            if (!(irStringConcatenationImpl instanceof IrConst) || !Intrinsics.areEqual(((IrConst) irStringConcatenationImpl).getKind(), IrConstKind.String.INSTANCE)) {
                irStringConcatenationImpl = new IrStringConcatenationImpl(startOffsetSkippingComments, endOffset, irType, CollectionsKt.listOf(irStringConcatenationImpl));
            }
            return irStringConcatenationImpl;
        }
        return new IrStringConcatenationImpl(startOffsetSkippingComments, endOffset, irType, listPostprocessStringTemplateEntries);
    }

    private final List<IrExpression> postprocessStringTemplateEntries(List<? extends IrExpression> list) {
        ArrayList arrayList = new ArrayList(list.size());
        IrType stringType = getContext().getIrBuiltIns().getStringType();
        StringBuilder sb = new StringBuilder();
        int startOffset = 0;
        int endOffset = 0;
        for (IrExpression irExpression : list) {
            if (irExpression instanceof IrConst) {
                IrConst<?> irConst = (IrConst) irExpression;
                if (Intrinsics.areEqual(irConst.getKind(), IrConstKind.String.INSTANCE)) {
                    if (sb.length() == 0) {
                        startOffset = irExpression.getStartOffset();
                    }
                    sb.append(IrConstKind.String.INSTANCE.valueOf(irConst));
                    endOffset = irExpression.getEndOffset();
                }
            }
            if (sb.length() > 0) {
                IrConstImpl.Companion companion = IrConstImpl.INSTANCE;
                String string = sb.toString();
                Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                arrayList.add(companion.string(startOffset, endOffset, stringType, string));
                StringsKt.clear(sb);
            }
            arrayList.add(irExpression);
        }
        if (sb.length() > 0) {
            IrConstImpl.Companion companion2 = IrConstImpl.INSTANCE;
            String string2 = sb.toString();
            Intrinsics.checkNotNullExpressionValue(string2, "toString(...)");
            arrayList.add(companion2.string(startOffset, endOffset, stringType, string2));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public IrStatement visitLiteralStringTemplateEntry(KtLiteralStringTemplateEntry entry, Void data) {
        Intrinsics.checkNotNullParameter(entry, "entry");
        IrConstImpl.Companion companion = IrConstImpl.INSTANCE;
        KtLiteralStringTemplateEntry ktLiteralStringTemplateEntry = entry;
        int startOffsetSkippingComments = PsiUtilsKt.getStartOffsetSkippingComments(ktLiteralStringTemplateEntry);
        int endOffset = PsiUtilsKt.getEndOffset(ktLiteralStringTemplateEntry);
        IrType stringType = getContext().getIrBuiltIns().getStringType();
        String text = entry.getText();
        Intrinsics.checkNotNullExpressionValue(text, "getText(...)");
        return companion.string(startOffsetSkippingComments, endOffset, stringType, text);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public IrStatement visitEscapeStringTemplateEntry(KtEscapeStringTemplateEntry entry, Void data) {
        Intrinsics.checkNotNullParameter(entry, "entry");
        IrConstImpl.Companion companion = IrConstImpl.INSTANCE;
        KtEscapeStringTemplateEntry ktEscapeStringTemplateEntry = entry;
        int startOffsetSkippingComments = PsiUtilsKt.getStartOffsetSkippingComments(ktEscapeStringTemplateEntry);
        int endOffset = PsiUtilsKt.getEndOffset(ktEscapeStringTemplateEntry);
        IrType stringType = getContext().getIrBuiltIns().getStringType();
        String unescapedValue = entry.getUnescapedValue();
        Intrinsics.checkNotNullExpressionValue(unescapedValue, "getUnescapedValue(...)");
        return companion.string(startOffsetSkippingComments, endOffset, stringType, unescapedValue);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public IrStatement visitStringTemplateEntryWithExpression(KtStringTemplateEntryWithExpression entry, Void data) {
        Intrinsics.checkNotNullParameter(entry, "entry");
        KtExpression expression = entry.getExpression();
        Intrinsics.checkNotNull(expression);
        return generateExpression(expression);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public IrExpression visitSimpleNameExpression(KtSimpleNameExpression expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        StatementGenerator statementGenerator = this;
        KtSimpleNameExpression ktSimpleNameExpression = expression;
        ResolvedCall<? extends CallableDescriptor> resolvedCall = GeneratorKt.getResolvedCall(statementGenerator, ktSimpleNameExpression);
        if (resolvedCall != null) {
            if (resolvedCall instanceof VariableAsFunctionResolvedCall) {
                return CallGeneratorKt.generateCall$default(new CallGenerator(this), ktSimpleNameExpression, ArgumentsGenerationUtilsKt.pregenerateCall(this, ((VariableAsFunctionResolvedCall) resolvedCall).getVariableCall()), IrStatementOrigin.VARIABLE_AS_FUNCTION.INSTANCE, 0, 0, 24, null);
            }
            return generateExpressionForReferencedDescriptor(resolvedCall.getResultingDescriptor(), expression, resolvedCall);
        }
        WritableSlice<KtReferenceExpression, DeclarationDescriptor> REFERENCE_TARGET = BindingContext.REFERENCE_TARGET;
        Intrinsics.checkNotNullExpressionValue(REFERENCE_TARGET, "REFERENCE_TARGET");
        DeclarationDescriptor declarationDescriptor = (DeclarationDescriptor) GeneratorKt.get(statementGenerator, REFERENCE_TARGET, expression);
        if (declarationDescriptor != null) {
            return generateExpressionForReferencedDescriptor(declarationDescriptor, expression, null);
        }
        return new ErrorExpressionGenerator(this).generateErrorSimpleName(expression);
    }

    private final IrExpression generateExpressionForReferencedDescriptor(DeclarationDescriptor descriptor, KtExpression expression, ResolvedCall<?> resolvedCall) {
        KotlinType defaultType;
        CallGenerator callGenerator = new CallGenerator(this);
        KtExpression ktExpression = expression;
        int startOffsetSkippingComments = PsiUtilsKt.getStartOffsetSkippingComments(ktExpression);
        int endOffset = PsiUtilsKt.getEndOffset(ktExpression);
        ExplicitSmartCasts explicitSmartCasts = (ExplicitSmartCasts) getContext().getBindingContext().get(BindingContext.SMARTCAST, expression);
        return callGenerator.generateValueReference(startOffsetSkippingComments, endOffset, descriptor, resolvedCall, null, (explicitSmartCasts == null || (defaultType = explicitSmartCasts.getDefaultType()) == null) ? null : toIrType(defaultType));
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public IrStatement visitCallExpression(KtCallExpression expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        KtCallExpression ktCallExpression = expression;
        ResolvedCall<? extends CallableDescriptor> resolvedCall = GeneratorKt.getResolvedCall(this, ktCallExpression);
        if (resolvedCall == null) {
            return new ErrorExpressionGenerator(this).generateErrorCall(expression);
        }
        if (resolvedCall instanceof VariableAsFunctionResolvedCall) {
            return CallGeneratorKt.generateCall$default(new CallGenerator(this), ktCallExpression, ArgumentsGenerationUtilsKt.pregenerateCall(this, ((VariableAsFunctionResolvedCall) resolvedCall).getFunctionCall()), IrStatementOrigin.INVOKE.INSTANCE, 0, 0, 24, null);
        }
        KtExpression calleeExpression = expression.getCalleeExpression();
        KtCallExpression ktCallExpression2 = expression;
        return new CallGenerator(this).generateCall(PsiUtilsKt.getStartOffsetSkippingComments(ktCallExpression2), PsiUtilsKt.getEndOffset(ktCallExpression2), ArgumentsGenerationUtilsKt.pregenerateCall(this, resolvedCall), (!Intrinsics.areEqual(resolvedCall.getResultingDescriptor().getShortName(), OperatorNameConventions.INVOKE) || (calleeExpression instanceof KtSimpleNameExpression) || (calleeExpression instanceof KtQualifiedExpression)) ? null : IrStatementOrigin.INVOKE.INSTANCE);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public IrStatement visitArrayAccessExpression(KtArrayAccessExpression expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        WritableSlice<KtExpression, ResolvedCall<FunctionDescriptor>> INDEXED_LVALUE_GET = BindingContext.INDEXED_LVALUE_GET;
        Intrinsics.checkNotNullExpressionValue(INDEXED_LVALUE_GET, "INDEXED_LVALUE_GET");
        ResolvedCall resolvedCall = (ResolvedCall) GeneratorKt.getOrFail(this, INDEXED_LVALUE_GET, expression);
        CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
        Intrinsics.checkNotNullExpressionValue(resultingDescriptor, "getResultingDescriptor(...)");
        if (org.jetbrains.kotlin.resolve.calls.tasks.DynamicCallsKt.isDynamic(resultingDescriptor)) {
            return new OperatorExpressionGenerator(this).generateDynamicArrayAccess(expression);
        }
        CallGenerator callGenerator = new CallGenerator(this);
        KtArrayAccessExpression ktArrayAccessExpression = expression;
        int startOffsetSkippingComments = PsiUtilsKt.getStartOffsetSkippingComments(ktArrayAccessExpression);
        int endOffset = PsiUtilsKt.getEndOffset(ktArrayAccessExpression);
        Intrinsics.checkNotNull(resolvedCall);
        return callGenerator.generateCall(startOffsetSkippingComments, endOffset, ArgumentsGenerationUtilsKt.pregenerateCall(this, resolvedCall), IrStatementOrigin.GET_ARRAY_ELEMENT.INSTANCE);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public IrStatement visitDotQualifiedExpression(KtDotQualifiedExpression expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        KtExpression selectorExpression = expression.getSelectorExpression();
        Intrinsics.checkNotNull(selectorExpression);
        Object objAccept = selectorExpression.accept(this, data);
        Intrinsics.checkNotNullExpressionValue(objAccept, "accept(...)");
        return (IrStatement) objAccept;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public IrStatement visitSafeQualifiedExpression(KtSafeQualifiedExpression expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        KtExpression selectorExpression = expression.getSelectorExpression();
        Intrinsics.checkNotNull(selectorExpression);
        Object objAccept = selectorExpression.accept(this, data);
        Intrinsics.checkNotNullExpressionValue(objAccept, "accept(...)");
        return (IrStatement) objAccept;
    }

    private final boolean isThisForClassPhysicallyAvailable(ClassDescriptor classDescriptor) {
        for (DeclarationDescriptor scopeOwner = getScopeOwner(); scopeOwner != null; scopeOwner = scopeOwner.getContainingDeclaration()) {
            if (Intrinsics.areEqual(scopeOwner, classDescriptor)) {
                return true;
            }
            if ((scopeOwner instanceof ClassDescriptor) && !((ClassDescriptor) scopeOwner).getIsInner()) {
                return false;
            }
        }
        return false;
    }

    public final IrExpression generateThisReceiver(int startOffset, int endOffset, KotlinType kotlinType, ClassDescriptor classDescriptor) {
        Intrinsics.checkNotNullParameter(kotlinType, "kotlinType");
        Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
        ReceiverParameterDescriptor thisAsReceiverParameter = classDescriptor.getThisAsReceiverParameter();
        Intrinsics.checkNotNullExpressionValue(thisAsReceiverParameter, "getThisAsReceiverParameter(...)");
        IrType irType = toIrType(kotlinType);
        if (DescriptorUtils.isObject(classDescriptor) && !isThisForClassPhysicallyAvailable(classDescriptor)) {
            return new IrGetObjectValueImpl(startOffset, endOffset, irType, getContext().getSymbolTable().getDescriptorExtension().referenceClass(classDescriptor));
        }
        return new IrGetValueImpl(startOffset, endOffset, irType, getContext().getSymbolTable().getDescriptorExtension().referenceValueParameter((ParameterDescriptor) thisAsReceiverParameter), null, 16, null);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public IrExpression visitThisExpression(KtThisExpression expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        StatementGenerator statementGenerator = this;
        WritableSlice<KtReferenceExpression, DeclarationDescriptor> REFERENCE_TARGET = BindingContext.REFERENCE_TARGET;
        Intrinsics.checkNotNullExpressionValue(REFERENCE_TARGET, "REFERENCE_TARGET");
        Object obj = statementGenerator.getContext().getBindingContext().get(REFERENCE_TARGET, expression.getInstanceReference());
        if (obj != null) {
            Intrinsics.checkNotNull(obj);
            DeclarationDescriptor declarationDescriptor = (DeclarationDescriptor) obj;
            WritableSlice<KtReferenceExpression, ReceiverParameterDescriptor> THIS_REFERENCE_TARGET = BindingContext.THIS_REFERENCE_TARGET;
            Intrinsics.checkNotNullExpressionValue(THIS_REFERENCE_TARGET, "THIS_REFERENCE_TARGET");
            KtReferenceExpression instanceReference = expression.getInstanceReference();
            Intrinsics.checkNotNullExpressionValue(instanceReference, "getInstanceReference(...)");
            Object obj2 = statementGenerator.getContext().getBindingContext().get(THIS_REFERENCE_TARGET, instanceReference);
            if (obj2 != null) {
                Intrinsics.checkNotNull(obj2);
                ReceiverParameterDescriptor receiverParameterDescriptor = (ReceiverParameterDescriptor) obj2;
                KtThisExpression ktThisExpression = expression;
                int startOffsetSkippingComments = PsiUtilsKt.getStartOffsetSkippingComments(ktThisExpression);
                int endOffset = PsiUtilsKt.getEndOffset(ktThisExpression);
                if (declarationDescriptor instanceof ClassDescriptor) {
                    if (receiverParameterDescriptor.getValue() instanceof ContextClassReceiver) {
                        ReceiverValue value = receiverParameterDescriptor.getValue();
                        Intrinsics.checkNotNull(value, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.scopes.receivers.ContextClassReceiver");
                        return ArgumentsGenerationUtilsKt.loadContextReceiver(this, (ContextClassReceiver) value, startOffsetSkippingComments, endOffset);
                    }
                    ClassDescriptor classDescriptor = (ClassDescriptor) declarationDescriptor;
                    KotlinType type = classDescriptor.getThisAsReceiverParameter().getType();
                    Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                    return generateThisReceiver(startOffsetSkippingComments, endOffset, type, classDescriptor);
                }
                if (declarationDescriptor instanceof CallableDescriptor) {
                    KotlinType type2 = receiverParameterDescriptor.getType();
                    Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
                    return new IrGetValueImpl(startOffsetSkippingComments, endOffset, toIrType(type2), getContext().getSymbolTable().getDescriptorExtension().referenceValueParameter((ParameterDescriptor) receiverParameterDescriptor), null, 16, null);
                }
                throw new IllegalStateException(("Expected this or receiver: " + declarationDescriptor).toString());
            }
            throw new RuntimeException("No reference target for this");
        }
        throw new RuntimeException("No reference target for this");
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public IrStatement visitBinaryExpression(KtBinaryExpression expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return new OperatorExpressionGenerator(this).generateBinaryExpression(expression);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public IrStatement visitPrefixExpression(KtPrefixExpression expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return new OperatorExpressionGenerator(this).generatePrefixExpression(expression);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public IrStatement visitPostfixExpression(KtPostfixExpression expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return new OperatorExpressionGenerator(this).generatePostfixExpression(expression);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public IrStatement visitBinaryWithTypeRHSExpression(KtBinaryExpressionWithTypeRHS expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return new OperatorExpressionGenerator(this).generateCastExpression(expression);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public IrStatement visitIsExpression(KtIsExpression expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return new OperatorExpressionGenerator(this).generateInstanceOfExpression(expression);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public IrStatement visitIfExpression(KtIfExpression expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return new BranchingExpressionGenerator(this).generateIfExpression(expression);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public IrStatement visitWhenExpression(KtWhenExpression expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return new BranchingExpressionGenerator(this).generateWhenExpression(expression);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public IrStatement visitWhileExpression(KtWhileExpression expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return new LoopExpressionGenerator(this).generateWhileLoop(expression);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public IrStatement visitDoWhileExpression(KtDoWhileExpression expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return new LoopExpressionGenerator(this).generateDoWhileLoop(expression);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public IrStatement visitForExpression(KtForExpression expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return new LoopExpressionGenerator(this).generateForLoop(expression);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public IrStatement visitBreakExpression(KtBreakExpression expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return new LoopExpressionGenerator(this).generateBreak(expression);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public IrStatement visitContinueExpression(KtContinueExpression expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return new LoopExpressionGenerator(this).generateContinue(expression);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public IrStatement visitTryExpression(KtTryExpression expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return new TryCatchExpressionGenerator(this).generateTryCatch(expression);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public IrStatement visitLambdaExpression(KtLambdaExpression expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return new LocalFunctionGenerator(this).generateLambda(expression);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public IrStatement visitNamedFunction(KtNamedFunction function, Void data) {
        Intrinsics.checkNotNullParameter(function, "function");
        return new LocalFunctionGenerator(this).generateFunction(function);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public IrStatement visitObjectLiteralExpression(KtObjectLiteralExpression expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return new LocalClassGenerator(this).generateObjectLiteral(expression);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public IrStatement visitClassOrObject(KtClassOrObject classOrObject, Void data) {
        Intrinsics.checkNotNullParameter(classOrObject, "classOrObject");
        return new LocalClassGenerator(this).generateLocalClass(classOrObject);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public IrStatement visitTypeAlias(KtTypeAlias typeAlias, Void data) {
        Intrinsics.checkNotNullParameter(typeAlias, "typeAlias");
        KtTypeAlias ktTypeAlias = typeAlias;
        return new IrBlockImpl(PsiUtilsKt.getStartOffsetSkippingComments(ktTypeAlias), PsiUtilsKt.getEndOffset(ktTypeAlias), getContext().getIrBuiltIns().getUnitType(), null, 8, null);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public IrStatement visitClassLiteralExpression(KtClassLiteralExpression expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return new ReflectionReferencesGenerator(this).generateClassLiteral(expression);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public IrStatement visitCallableReferenceExpression(KtCallableReferenceExpression expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return new ReflectionReferencesGenerator(this).generateCallableReference(expression);
    }
}
