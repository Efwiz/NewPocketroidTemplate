package org.jetbrains.kotlin.codegen;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.mediarouter.media.GlobalMediaRouter;
import com.android.dx.rop.code.RegisterSpec;
import com.beust.jcommander.Parameters;
import com.gaurav.avnc.vnc.XKeySym;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.android.gms.cast.MediaError;
import com.reandroid.archive.PathTree;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.text.StringsKt;
import org.apache.commons.compress.archivers.tar.TarConstants;
import org.eclipse.jdt.internal.compiler.parser.ParserBasicInformation;
import org.jetbrains.kotlin.backend.common.CodegenUtil;
import org.jetbrains.kotlin.backend.common.SamType;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.binding.CalculatedClosure;
import org.jetbrains.kotlin.codegen.binding.CodegenBinding;
import org.jetbrains.kotlin.codegen.binding.MutableClosure;
import org.jetbrains.kotlin.codegen.context.ClassContext;
import org.jetbrains.kotlin.codegen.context.ClosureContext;
import org.jetbrains.kotlin.codegen.context.CodegenContext;
import org.jetbrains.kotlin.codegen.context.ConstructorContext;
import org.jetbrains.kotlin.codegen.context.EnclosedValueDescriptor;
import org.jetbrains.kotlin.codegen.context.FieldOwnerContext;
import org.jetbrains.kotlin.codegen.context.InlineLambdaContext;
import org.jetbrains.kotlin.codegen.context.LocalLookup;
import org.jetbrains.kotlin.codegen.context.MethodContext;
import org.jetbrains.kotlin.codegen.context.ScriptContext;
import org.jetbrains.kotlin.codegen.context.ScriptLikeContext;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenForLambda;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.ResolvedCallWithRealDescriptor;
import org.jetbrains.kotlin.codegen.coroutines.SuspensionPointKind;
import org.jetbrains.kotlin.codegen.extensions.ExpressionCodegenExtension;
import org.jetbrains.kotlin.codegen.inline.FictitiousArrayConstructor;
import org.jetbrains.kotlin.codegen.inline.InlineCodegenForDefaultBody;
import org.jetbrains.kotlin.codegen.inline.InlineCodegenUtilsKt;
import org.jetbrains.kotlin.codegen.inline.NameGenerator;
import org.jetbrains.kotlin.codegen.inline.PsiExpressionLambda;
import org.jetbrains.kotlin.codegen.inline.PsiInlineCodegen;
import org.jetbrains.kotlin.codegen.inline.PsiSourceCompilerForInline;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeParametersUsages;
import org.jetbrains.kotlin.codegen.inline.TypeParameterMappings;
import org.jetbrains.kotlin.codegen.intrinsics.IntrinsicMethod;
import org.jetbrains.kotlin.codegen.intrinsics.IntrinsicPropertyGetter;
import org.jetbrains.kotlin.codegen.intrinsics.IntrinsicWithSpecialReceiver;
import org.jetbrains.kotlin.codegen.intrinsics.JavaClassProperty;
import org.jetbrains.kotlin.codegen.intrinsics.KCallableNameProperty;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.codegen.pseudoInsns.PseudoInsnsKt;
import org.jetbrains.kotlin.codegen.range.RangeValuesKt;
import org.jetbrains.kotlin.codegen.range.forLoop.ForLoopGenerator;
import org.jetbrains.kotlin.codegen.signature.BothSignatureWriter;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper;
import org.jetbrains.kotlin.codegen.when.SwitchCodegen;
import org.jetbrains.kotlin.codegen.when.SwitchCodegenProvider;
import org.jetbrains.kotlin.com.google.common.collect.Lists;
import org.jetbrains.kotlin.com.google.common.collect.Maps;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProcessCanceledException;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.containers.Stack;
import org.jetbrains.kotlin.config.ApiVersion;
import org.jetbrains.kotlin.config.JVMAssertionsMode;
import org.jetbrains.kotlin.config.JVMConfigurationKeys;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassOrPackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertySetterDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ScriptDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptorWithAccessors;
import org.jetbrains.kotlin.descriptors.impl.AnonymousFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.impl.LocalVariableDescriptor;
import org.jetbrains.kotlin.descriptors.impl.SyntheticFieldDescriptor;
import org.jetbrains.kotlin.descriptors.impl.TypeAliasConstructorDescriptor;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.load.java.DescriptorsJvmAbiUtil;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.load.java.descriptors.JavaPropertyDescriptor;
import org.jetbrains.kotlin.load.kotlin.DescriptorBasedTypeSignatureMappingKt;
import org.jetbrains.kotlin.load.kotlin.MethodSignatureMappingKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtAnnotatedExpression;
import org.jetbrains.kotlin.psi.KtArrayAccessExpression;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.psi.KtBinaryExpressionWithTypeRHS;
import org.jetbrains.kotlin.psi.KtBlockExpression;
import org.jetbrains.kotlin.psi.KtBreakExpression;
import org.jetbrains.kotlin.psi.KtCallExpression;
import org.jetbrains.kotlin.psi.KtCallableReferenceExpression;
import org.jetbrains.kotlin.psi.KtCatchClause;
import org.jetbrains.kotlin.psi.KtClass;
import org.jetbrains.kotlin.psi.KtClassLiteralExpression;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtCollectionLiteralExpression;
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
import org.jetbrains.kotlin.psi.KtExpressionWithLabel;
import org.jetbrains.kotlin.psi.KtFinallySection;
import org.jetbrains.kotlin.psi.KtForExpression;
import org.jetbrains.kotlin.psi.KtFunctionLiteral;
import org.jetbrains.kotlin.psi.KtIfExpression;
import org.jetbrains.kotlin.psi.KtIsExpression;
import org.jetbrains.kotlin.psi.KtLabeledExpression;
import org.jetbrains.kotlin.psi.KtLambdaExpression;
import org.jetbrains.kotlin.psi.KtLiteralStringTemplateEntry;
import org.jetbrains.kotlin.psi.KtNamedDeclaration;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtObjectDeclaration;
import org.jetbrains.kotlin.psi.KtObjectLiteralExpression;
import org.jetbrains.kotlin.psi.KtOperationReferenceExpression;
import org.jetbrains.kotlin.psi.KtParenthesizedExpression;
import org.jetbrains.kotlin.psi.KtPostfixExpression;
import org.jetbrains.kotlin.psi.KtPrefixExpression;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtPsiFactory;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.psi.KtReferenceExpression;
import org.jetbrains.kotlin.psi.KtReturnExpression;
import org.jetbrains.kotlin.psi.KtSafeQualifiedExpression;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.KtStringTemplateEntry;
import org.jetbrains.kotlin.psi.KtStringTemplateEntryWithExpression;
import org.jetbrains.kotlin.psi.KtStringTemplateExpression;
import org.jetbrains.kotlin.psi.KtSuperExpression;
import org.jetbrains.kotlin.psi.KtThisExpression;
import org.jetbrains.kotlin.psi.KtThrowExpression;
import org.jetbrains.kotlin.psi.KtTryExpression;
import org.jetbrains.kotlin.psi.KtTypeAlias;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.psi.KtValueArgument;
import org.jetbrains.kotlin.psi.KtVariableDeclaration;
import org.jetbrains.kotlin.psi.KtVisitor;
import org.jetbrains.kotlin.psi.KtWhenCondition;
import org.jetbrains.kotlin.psi.KtWhenConditionInRange;
import org.jetbrains.kotlin.psi.KtWhenConditionIsPattern;
import org.jetbrains.kotlin.psi.KtWhenConditionWithExpression;
import org.jetbrains.kotlin.psi.KtWhenEntry;
import org.jetbrains.kotlin.psi.KtWhenExpression;
import org.jetbrains.kotlin.psi.KtWhileExpression;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingContextUtils;
import org.jetbrains.kotlin.resolve.CompileTimeConstantUtils;
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.InlineClassDescriptorResolver;
import org.jetbrains.kotlin.resolve.InlineClassesUtilsKt;
import org.jetbrains.kotlin.resolve.PropertyImportedFromObject;
import org.jetbrains.kotlin.resolve.calls.inference.CapturedTypeConstructorKt;
import org.jetbrains.kotlin.resolve.calls.model.DefaultValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.VarargValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.VariableAsFunctionResolvedCall;
import org.jetbrains.kotlin.resolve.calls.util.CallMaker;
import org.jetbrains.kotlin.resolve.calls.util.CallResolverUtilKt;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.calls.util.FakeCallableDescriptorForObject;
import org.jetbrains.kotlin.resolve.calls.util.FakeCallableDescriptorForTypeAliasObject;
import org.jetbrains.kotlin.resolve.calls.util.UnderscoreUtilKt;
import org.jetbrains.kotlin.resolve.checkers.PrimitiveNumericComparisonInfo;
import org.jetbrains.kotlin.resolve.constants.BooleanValue;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.constants.DoubleValue;
import org.jetbrains.kotlin.resolve.constants.FloatValue;
import org.jetbrains.kotlin.resolve.constants.IntegerValueConstant;
import org.jetbrains.kotlin.resolve.constants.NullValue;
import org.jetbrains.kotlin.resolve.constants.StringValue;
import org.jetbrains.kotlin.resolve.constants.evaluate.ConstantExpressionEvaluatorKt;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.inline.InlineUtil;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.kotlin.resolve.jvm.JvmBindingContextSlices;
import org.jetbrains.kotlin.resolve.jvm.JvmConstantsKt;
import org.jetbrains.kotlin.resolve.jvm.RuntimeAssertionInfo;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOriginKt;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodGenericSignature;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodParameterKind;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodParameterSignature;
import org.jetbrains.kotlin.resolve.sam.SamConstructorDescriptor;
import org.jetbrains.kotlin.resolve.scopes.receivers.CastImplicitClassReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ExpressionReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ExtensionReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ImplicitClassReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.resolve.scopes.receivers.TransientReceiver;
import org.jetbrains.kotlin.synthetic.SyntheticJavaPropertyDescriptor;
import org.jetbrains.kotlin.types.FlexibleTypesKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.RangeUtilKt;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeApproximator;
import org.jetbrains.kotlin.types.TypeApproximatorConfiguration;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeSystemCommonBackendContext;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.checker.ClassicTypeSystemContextImpl;
import org.jetbrains.kotlin.types.expressions.DoubleColonLHS;
import org.jetbrains.kotlin.types.expressions.ExpressionTypingUtils;
import org.jetbrains.kotlin.types.model.TypeParameterMarker;
import org.jetbrains.kotlin.types.typesApproximation.CapturedTypeApproximationKt;
import org.jetbrains.kotlin.util.OperatorNameConventions;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.MethodVisitor;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: loaded from: classes3.dex */
public class ExpressionCodegen extends KtVisitor<StackValue, StackValue> implements BaseExpressionCodegen, LocalLookup {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final BindingContext bindingContext;
    private final Stack<BlockStackElement> blockStackElements;
    public final MethodContext context;
    public final CallGenerator defaultCallGenerator;
    private int finallyDepth;
    public final FrameMap myFrameMap;
    private int myLastLineNumber;
    private final MemberCodegen<?> parentCodegen;
    private final Type returnType;
    private boolean shouldMarkLineNumbers;
    private final GenerationState state;
    private final CodegenStatementVisitor statementVisitor;
    private final SwitchCodegenProvider switchCodegenProvider;
    private final TailRecursionCodegen tailRecursionCodegen;
    public final Map<KtElement, StackValue> tempVariables;
    final KotlinTypeMapper typeMapper;
    private final TypeSystemCommonBackendContext typeSystem;
    public final InstructionAdapter v;

    public static class BlockStackElement {
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 6:
            case 7:
            case 8:
            case 9:
            case 16:
            case 17:
            case 27:
            case 29:
            case 31:
            case 32:
            case 44:
            case 51:
            case 52:
            case 64:
            case 74:
            case 79:
            case 80:
            case 81:
            case 83:
            case 84:
            case 90:
            case 91:
            case 131:
            case 138:
            case 144:
            case 145:
            case 149:
            case 153:
            case 154:
            case 155:
            case 162:
            case 163:
            case 176:
            case 177:
            case 183:
            case 184:
            case 187:
            case 188:
            case 189:
            case 190:
            case 191:
            case 196:
            case 197:
            case 200:
            case 203:
            case 204:
            case 205:
            case 206:
            case 207:
            case 209:
            case 212:
            case 213:
            case 214:
            case 221:
            case 223:
            case 246:
            case GlobalMediaRouter.CallbackHandler.MSG_ROUTE_SELECTED /* 262 */:
            case 263:
            case ParserBasicInformation.SCOPE_SIZE /* 291 */:
            case 293:
            case 297:
            case 299:
            case 305:
            case 306:
            case 308:
            case 312:
            case 329:
            case 335:
            case 336:
            case 337:
            case 343:
            case 344:
            case TarConstants.XSTAR_PREFIX_OFFSET /* 345 */:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 6:
            case 7:
            case 8:
            case 9:
            case 16:
            case 17:
            case 27:
            case 29:
            case 31:
            case 32:
            case 44:
            case 51:
            case 52:
            case 64:
            case 74:
            case 79:
            case 80:
            case 81:
            case 83:
            case 84:
            case 90:
            case 91:
            case 131:
            case 138:
            case 144:
            case 145:
            case 149:
            case 153:
            case 154:
            case 155:
            case 162:
            case 163:
            case 176:
            case 177:
            case 183:
            case 184:
            case 187:
            case 188:
            case 189:
            case 190:
            case 191:
            case 196:
            case 197:
            case 200:
            case 203:
            case 204:
            case 205:
            case 206:
            case 207:
            case 209:
            case 212:
            case 213:
            case 214:
            case 221:
            case 223:
            case 246:
            case GlobalMediaRouter.CallbackHandler.MSG_ROUTE_SELECTED /* 262 */:
            case 263:
            case ParserBasicInformation.SCOPE_SIZE /* 291 */:
            case 293:
            case 297:
            case 299:
            case 305:
            case 306:
            case 308:
            case 312:
            case 329:
            case 335:
            case 336:
            case 337:
            case 343:
            case 344:
            case TarConstants.XSTAR_PREFIX_OFFSET /* 345 */:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
                objArr[0] = "frameMap";
                break;
            case 2:
            case 110:
            case 117:
                objArr[0] = "returnType";
                break;
            case 3:
            case 25:
            case 78:
                objArr[0] = "context";
                break;
            case 4:
            case 106:
                objArr[0] = "state";
                break;
            case 5:
                objArr[0] = "parentCodegen";
                break;
            case 6:
            case 7:
            case 8:
            case 9:
            case 16:
            case 17:
            case 27:
            case 29:
            case 31:
            case 32:
            case 44:
            case 51:
            case 52:
            case 64:
            case 74:
            case 79:
            case 80:
            case 81:
            case 83:
            case 84:
            case 90:
            case 91:
            case 131:
            case 138:
            case 144:
            case 145:
            case 149:
            case 153:
            case 154:
            case 155:
            case 162:
            case 163:
            case 176:
            case 177:
            case 183:
            case 184:
            case 187:
            case 188:
            case 189:
            case 190:
            case 191:
            case 196:
            case 197:
            case 200:
            case 203:
            case 204:
            case 205:
            case 206:
            case 207:
            case 209:
            case 212:
            case 213:
            case 214:
            case 221:
            case 223:
            case 246:
            case GlobalMediaRouter.CallbackHandler.MSG_ROUTE_SELECTED /* 262 */:
            case 263:
            case ParserBasicInformation.SCOPE_SIZE /* 291 */:
            case 293:
            case 297:
            case 299:
            case 305:
            case 306:
            case 308:
            case 312:
            case 329:
            case 335:
            case 336:
            case 337:
            case 343:
            case 344:
            case TarConstants.XSTAR_PREFIX_OFFSET /* 345 */:
                objArr[0] = "org/jetbrains/kotlin/codegen/ExpressionCodegen";
                break;
            case 10:
                objArr[0] = "literal";
                break;
            case 11:
            case 13:
                objArr[0] = "member";
                break;
            case 12:
            case 71:
            case 112:
            case 123:
            case 126:
            case 127:
            case 128:
            case 175:
            case 178:
            case 181:
            case 186:
            case 195:
                objArr[0] = "descriptor";
                break;
            case 14:
                objArr[0] = "provided";
                break;
            case 15:
                objArr[0] = "required";
                break;
            case 18:
            case 28:
            case 30:
            case 182:
            case XKeySym.XK_eacute /* 233 */:
            case XKeySym.XK_ediaeresis /* 235 */:
            case XKeySym.XK_iacute /* 237 */:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
            case 19:
                objArr[0] = "value";
                break;
            case 20:
                objArr[0] = "klass";
                break;
            case 21:
                objArr[0] = "typeAlias";
                break;
            case 22:
            case 70:
            case 298:
                objArr[0] = "declaration";
                break;
            case 23:
            case 24:
            case 26:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 46:
            case 47:
            case 48:
            case 53:
            case 54:
            case 55:
            case 56:
            case 58:
            case 60:
            case 62:
            case 63:
            case 66:
            case 69:
            case 75:
            case 82:
            case 109:
            case 113:
            case 119:
            case 121:
            case 139:
            case 140:
            case 215:
            case 216:
            case 224:
            case 225:
            case XKeySym.XK_atilde /* 227 */:
            case XKeySym.XK_aring /* 229 */:
            case XKeySym.XK_ae /* 230 */:
            case 272:
            case 273:
            case 277:
            case 278:
            case 280:
            case 303:
            case 313:
            case TypedValues.AttributesType.TYPE_EASING /* 317 */:
            case TypedValues.AttributesType.TYPE_PIVOT_TARGET /* 318 */:
            case MediaError.DetailedErrorCode.DASH_NO_INIT /* 322 */:
            case 323:
            case 324:
            case 330:
            case MediaError.DetailedErrorCode.SMOOTH_NETWORK /* 331 */:
            case 333:
                objArr[0] = "expression";
                break;
            case 41:
            case 42:
                objArr[0] = "forExpression";
                break;
            case 43:
            case 45:
            case 86:
            case 143:
            case 146:
            case 147:
            case 151:
            case 156:
            case 158:
            case 161:
            case 165:
            case 168:
            case 173:
            case 179:
            case 180:
            case 185:
            case 217:
            case 274:
            case 304:
            case 307:
            case 309:
            case 310:
            case 315:
                objArr[0] = "resolvedCall";
                break;
            case 49:
                objArr[0] = "afterBreakContinueLabel";
                break;
            case 50:
            case 104:
            case 105:
            case 107:
                objArr[0] = "nestedTryBlocksWithoutFinally";
                break;
            case 57:
            case 59:
            case 61:
                objArr[0] = "bindingContext";
                break;
            case 65:
                objArr[0] = "constant";
                break;
            case 67:
            case 68:
                objArr[0] = "function";
                break;
            case 72:
                objArr[0] = "strategy";
                break;
            case 73:
                objArr[0] = "closureCodegen";
                break;
            case 76:
                objArr[0] = "classDescriptor";
                break;
            case 77:
            case 170:
                objArr[0] = "callGenerator";
                break;
            case 85:
                objArr[0] = "statements";
                break;
            case 87:
                objArr[0] = "suspendFunction";
                break;
            case 88:
            case 89:
            case 219:
            case ParserBasicInformation.SCOPE_UBOUND /* 290 */:
            case 301:
            case 341:
                objArr[0] = "variableDescriptor";
                break;
            case 92:
            case 93:
            case 94:
            case 97:
            case 102:
                objArr[0] = "statement";
                break;
            case 95:
            case 328:
                objArr[0] = "blockEnd";
                break;
            case 96:
            case 98:
            case 100:
                objArr[0] = "leaveTasks";
                break;
            case 99:
                objArr[0] = "localFunction";
                break;
            case 101:
            case 218:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 103:
            case 111:
                objArr[0] = "afterReturnLabel";
                break;
            case 108:
                objArr[0] = CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME;
                break;
            case 114:
            case XKeySym.XK_egrave /* 232 */:
            case XKeySym.XK_ecircumflex /* 234 */:
            case XKeySym.XK_igrave /* 236 */:
                objArr[0] = "expr";
                break;
            case 115:
            case 116:
            case 118:
                objArr[0] = "bodyExpression";
                break;
            case 120:
            case 122:
            case 124:
            case 130:
            case 135:
            case 137:
            case 148:
            case 152:
            case 166:
            case 169:
            case 174:
            case 198:
            case XKeySym.XK_ccedilla /* 231 */:
            case 279:
            case 285:
            case 288:
            case 319:
                objArr[0] = "receiver";
                break;
            case 125:
            case 150:
                objArr[0] = "call";
                break;
            case 129:
            case 134:
            case 136:
                objArr[0] = "propertyDescriptor";
                break;
            case 132:
                objArr[0] = "accessorKind";
                break;
            case 133:
                objArr[0] = "containingDeclaration";
                break;
            case 141:
                objArr[0] = "probablyParenthesizedExpression";
                break;
            case 142:
                objArr[0] = "visitor";
                break;
            case 157:
                objArr[0] = "continuationExpression";
                break;
            case 159:
                objArr[0] = "enclosingSuspendFunctionJvmView";
                break;
            case 160:
                objArr[0] = "fd";
                break;
            case 164:
            case 167:
            case 172:
                objArr[0] = "callableMethod";
                break;
            case 171:
                objArr[0] = "argumentGenerator";
                break;
            case 192:
                objArr[0] = "receiverDescriptor";
                break;
            case 193:
                objArr[0] = "companionObjectDescriptor";
                break;
            case 194:
                objArr[0] = "contextBeforeInline";
                break;
            case 199:
                objArr[0] = "calleeContainingClass";
                break;
            case 201:
                objArr[0] = "singletonClassDescriptor";
                break;
            case 202:
            case 208:
                objArr[0] = "thisOrOuterClass";
                break;
            case 210:
                objArr[0] = "valueArgument";
                break;
            case 211:
                objArr[0] = "outType";
                break;
            case 220:
                objArr[0] = "target";
                break;
            case 222:
                objArr[0] = "lhs";
                break;
            case 226:
                objArr[0] = "ifnull";
                break;
            case XKeySym.XK_adiaeresis /* 228 */:
                objArr[0] = "ifNull";
                break;
            case XKeySym.XK_icircumflex /* 238 */:
            case XKeySym.XK_ntilde /* 241 */:
            case 251:
            case 255:
            case 258:
            case 265:
            case 269:
                objArr[0] = "opToken";
                break;
            case XKeySym.XK_idiaeresis /* 239 */:
            case 248:
            case 252:
            case 256:
                objArr[0] = "left";
                break;
            case 240:
            case 250:
            case 253:
            case 257:
                objArr[0] = "right";
                break;
            case 242:
                objArr[0] = "leftKotlinType";
                break;
            case 243:
                objArr[0] = "rightKotlinType";
                break;
            case 244:
            case 249:
            case 259:
                objArr[0] = "leftType";
                break;
            case 245:
            case 254:
            case 260:
                objArr[0] = "rightType";
                break;
            case 247:
                objArr[0] = "safeExpression";
                break;
            case GlobalMediaRouter.CallbackHandler.MSG_ROUTE_PRESENTATION_DISPLAY_CHANGED /* 261 */:
                objArr[0] = "ieee754Type";
                break;
            case GlobalMediaRouter.CallbackHandler.MSG_ROUTE_ANOTHER_SELECTED /* 264 */:
            case 268:
                objArr[0] = RegisterSpec.PREFIX;
                break;
            case 266:
            case 270:
                objArr[0] = "left754Type";
                break;
            case 267:
            case 271:
                objArr[0] = "right754Type";
                break;
            case 275:
            case MediaError.DetailedErrorCode.DASH_NETWORK /* 321 */:
                objArr[0] = "callable";
                break;
            case 276:
                objArr[0] = "jvmKotlinType";
                break;
            case 281:
                objArr[0] = "property";
                break;
            case 282:
            case 283:
                objArr[0] = "multiDeclaration";
                break;
            case 284:
            case 287:
                objArr[0] = "destructuringDeclaration";
                break;
            case 286:
            case 289:
                objArr[0] = "receiverStackValue";
                break;
            case 292:
                objArr[0] = "varValue";
                break;
            case 294:
            case 295:
                objArr[0] = "variableDeclaration";
                break;
            case 296:
                objArr[0] = "initializer";
                break;
            case 300:
                objArr[0] = "variable";
                break;
            case 302:
                objArr[0] = "metadataVar";
                break;
            case 311:
                objArr[0] = "objectType";
                break;
            case 314:
            case 316:
                objArr[0] = "arrayType";
                break;
            case 320:
                objArr[0] = "operationDescriptor";
                break;
            case 325:
                objArr[0] = "catchStart";
                break;
            case 326:
                objArr[0] = "catchedRegions";
                break;
            case 327:
                objArr[0] = "blockStart";
                break;
            case MediaError.DetailedErrorCode.SMOOTH_NO_MEDIA_DATA /* 332 */:
                objArr[0] = "usages";
                break;
            case 334:
                objArr[0] = "whenExpression";
                break;
            case 338:
                objArr[0] = PathTree.NAME_elements;
                break;
            case 339:
                objArr[0] = "delegateValue";
                break;
            case 340:
                objArr[0] = "metadataValue";
                break;
            case 342:
                objArr[0] = "typeMapper";
                break;
            case 346:
                objArr[0] = "typeParameter";
                break;
            default:
                objArr[0] = JvmAnnotationNames.METADATA_VERSION_FIELD_NAME;
                break;
        }
        switch (i) {
            case 6:
                objArr[1] = "getState";
                break;
            case 7:
                objArr[1] = "getBindingContext";
                break;
            case 8:
                objArr[1] = "getParentCodegen";
                break;
            case 9:
                objArr[1] = "getTypeMapper";
                break;
            case 16:
            case 17:
                objArr[1] = "castToRequiredTypeOfInterfaceIfNeeded";
                break;
            case 27:
                objArr[1] = "getSuperCallLabelTarget";
                break;
            case 29:
                objArr[1] = "asmType";
                break;
            case 31:
                objArr[1] = "mapTypeAsDeclaration";
                break;
            case 32:
                objArr[1] = "expressionType";
                break;
            case 44:
                objArr[1] = "getExpectedReceiverType";
                break;
            case 51:
            case 52:
                objArr[1] = "generateBreakOrContinueExpression";
                break;
            case 64:
                objArr[1] = "preprocessStringTemplate";
                break;
            case 74:
                objArr[1] = "putClosureInstanceOnStack";
                break;
            case 79:
            case 80:
            case 81:
                objArr[1] = "unwrapOriginalReceiverOwnerForSuspendLambda";
                break;
            case 83:
            case 84:
                objArr[1] = "lookupOuterValue";
                break;
            case 90:
            case 91:
                objArr[1] = "getVariableTypeNoSharing";
                break;
            case 131:
                objArr[1] = "intermediateValueForProperty";
                break;
            case 138:
                objArr[1] = "intermediateValueForSyntheticExtensionProperty";
                break;
            case 144:
            case 145:
                objArr[1] = "accessibleFunctionDescriptor";
                break;
            case 149:
            case 153:
            case 154:
            case 155:
                objArr[1] = "invokeFunction";
                break;
            case 162:
            case 163:
                objArr[1] = "resolveToCallable";
                break;
            case 176:
            case 177:
                objArr[1] = "getOrCreateCallGenerator";
                break;
            case 183:
            case 184:
                objArr[1] = "approximateCapturedType";
                break;
            case 187:
            case 188:
                objArr[1] = "getTypeArgumentsForResolvedCall";
                break;
            case 189:
            case 190:
            case 191:
                objArr[1] = "generateReceiverValue";
                break;
            case 196:
            case 197:
                objArr[1] = "generateExtensionReceiver";
                break;
            case 200:
            case 203:
            case 204:
            case 205:
            case 206:
            case 207:
                objArr[1] = "generateThisOrOuter";
                break;
            case 209:
                objArr[1] = "getNotNullParentContextForMethod";
                break;
            case 212:
            case 213:
            case 214:
                objArr[1] = "genVarargs";
                break;
            case 221:
                objArr[1] = "generatePropertyReference";
                break;
            case 223:
                objArr[1] = "generateClassLiteralReference";
                break;
            case 246:
                objArr[1] = "genEqualsForInlineClasses";
                break;
            case GlobalMediaRouter.CallbackHandler.MSG_ROUTE_SELECTED /* 262 */:
            case 263:
                objArr[1] = "boxIfNullable";
                break;
            case ParserBasicInformation.SCOPE_SIZE /* 291 */:
                objArr[1] = "getVariableMetadataValue";
                break;
            case 293:
                objArr[1] = "adjustVariableValue";
                break;
            case 297:
                objArr[1] = "generateProvideDelegateCallForLocalVariable";
                break;
            case 299:
                objArr[1] = "getVariableDescriptorNotNull";
                break;
            case 305:
            case 306:
                objArr[1] = "generateNewCall";
                break;
            case 308:
                objArr[1] = "getConstructorDescriptor";
                break;
            case 312:
                objArr[1] = "generateConstructorCall";
                break;
            case 329:
                objArr[1] = "getCurrentCatchIntervals";
                break;
            case 335:
                objArr[1] = "getFrameMap";
                break;
            case 336:
                objArr[1] = "getContext";
                break;
            case 337:
                objArr[1] = "getInlineNameGenerator";
                break;
            case 343:
                objArr[1] = "delegatedVariableValue";
                break;
            case 344:
                objArr[1] = "getVisitor";
                break;
            case TarConstants.XSTAR_PREFIX_OFFSET /* 345 */:
                objArr[1] = "getTypeSystem";
                break;
            default:
                objArr[1] = "org/jetbrains/kotlin/codegen/ExpressionCodegen";
                break;
        }
        switch (i) {
            case 6:
            case 7:
            case 8:
            case 9:
            case 16:
            case 17:
            case 27:
            case 29:
            case 31:
            case 32:
            case 44:
            case 51:
            case 52:
            case 64:
            case 74:
            case 79:
            case 80:
            case 81:
            case 83:
            case 84:
            case 90:
            case 91:
            case 131:
            case 138:
            case 144:
            case 145:
            case 149:
            case 153:
            case 154:
            case 155:
            case 162:
            case 163:
            case 176:
            case 177:
            case 183:
            case 184:
            case 187:
            case 188:
            case 189:
            case 190:
            case 191:
            case 196:
            case 197:
            case 200:
            case 203:
            case 204:
            case 205:
            case 206:
            case 207:
            case 209:
            case 212:
            case 213:
            case 214:
            case 221:
            case 223:
            case 246:
            case GlobalMediaRouter.CallbackHandler.MSG_ROUTE_SELECTED /* 262 */:
            case 263:
            case ParserBasicInformation.SCOPE_SIZE /* 291 */:
            case 293:
            case 297:
            case 299:
            case 305:
            case 306:
            case 308:
            case 312:
            case 329:
            case 335:
            case 336:
            case 337:
            case 343:
            case 344:
            case TarConstants.XSTAR_PREFIX_OFFSET /* 345 */:
                break;
            case 10:
                objArr[2] = "generateObjectLiteral";
                break;
            case 11:
            case 12:
                objArr[2] = "addReifiedParametersFromSignature";
                break;
            case 13:
                objArr[2] = "processTypeArguments";
                break;
            case 14:
            case 15:
                objArr[2] = "castToRequiredTypeOfInterfaceIfNeeded";
                break;
            case 18:
            case 19:
                objArr[2] = "putStackValue";
                break;
            case 20:
                objArr[2] = "visitClass";
                break;
            case 21:
                objArr[2] = "visitTypeAlias";
                break;
            case 22:
                objArr[2] = "visitObjectDeclaration";
                break;
            case 23:
                objArr[2] = "visitExpression";
                break;
            case 24:
                objArr[2] = "visitSuperExpression";
                break;
            case 25:
            case 26:
                objArr[2] = "getSuperCallLabelTarget";
                break;
            case 28:
                objArr[2] = "asmType";
                break;
            case 30:
                objArr[2] = "mapTypeAsDeclaration";
                break;
            case 33:
                objArr[2] = "visitParenthesizedExpression";
                break;
            case 34:
                objArr[2] = "visitAnnotatedExpression";
                break;
            case 35:
                objArr[2] = "visitIfExpression";
                break;
            case 36:
                objArr[2] = "generateIfExpression";
                break;
            case 37:
                objArr[2] = "visitWhileExpression";
                break;
            case 38:
                objArr[2] = "generateWhile";
                break;
            case 39:
                objArr[2] = "visitDoWhileExpression";
                break;
            case 40:
                objArr[2] = "generateDoWhile";
                break;
            case 41:
                objArr[2] = "visitForExpression";
                break;
            case 42:
                objArr[2] = "generateFor";
                break;
            case 43:
                objArr[2] = "getExpectedReceiverType";
                break;
            case 45:
                objArr[2] = "getSingleArgumentExpression";
                break;
            case 46:
                objArr[2] = "visitBreakExpression";
                break;
            case 47:
                objArr[2] = "visitContinueExpression";
                break;
            case 48:
            case 49:
            case 50:
                objArr[2] = "generateBreakOrContinueExpression";
                break;
            case 53:
                objArr[2] = "visitConstantExpression";
                break;
            case 54:
            case 58:
            case 59:
            case 60:
            case 61:
                objArr[2] = "getCompileTimeConstant";
                break;
            case 55:
            case 56:
            case 57:
                objArr[2] = "getPrimitiveOrStringCompileTimeConstant";
                break;
            case 62:
                objArr[2] = "visitStringTemplateExpression";
                break;
            case 63:
                objArr[2] = "preprocessStringTemplate";
                break;
            case 65:
                objArr[2] = "isConstantValueInlinableInStringTemplate";
                break;
            case 66:
                objArr[2] = "visitBlockExpression";
                break;
            case 67:
            case 68:
                objArr[2] = "visitNamedFunction";
                break;
            case 69:
                objArr[2] = "visitLambdaExpression";
                break;
            case 70:
            case 71:
            case 72:
                objArr[2] = "genClosure";
                break;
            case 73:
                objArr[2] = "putClosureInstanceOnStack";
                break;
            case 75:
                objArr[2] = "visitObjectLiteralExpression";
                break;
            case 76:
            case 77:
                objArr[2] = "pushClosureOnStack";
                break;
            case 78:
                objArr[2] = "unwrapOriginalReceiverOwnerForSuspendLambda";
                break;
            case 82:
            case 85:
                objArr[2] = "generateBlock";
                break;
            case 86:
                objArr[2] = "getCoroutineInstanceValueForSuspensionPoint";
                break;
            case 87:
                objArr[2] = "genCoroutineInstanceForSuspendLambda";
                break;
            case 88:
                objArr[2] = "getVariableType";
                break;
            case 89:
                objArr[2] = "getVariableTypeNoSharing";
                break;
            case 92:
                objArr[2] = "putDescriptorIntoFrameMap";
                break;
            case 93:
                objArr[2] = "putLocalVariableIntoFrameMap";
                break;
            case 94:
            case 95:
            case 96:
                objArr[2] = "addLeaveTaskToRemoveDescriptorFromFrameMap";
                break;
            case 97:
            case 98:
                objArr[2] = "addLeaveTaskToRemoveLocalVariableFromFrameMap";
                break;
            case 99:
            case 100:
                objArr[2] = "addLeaveTaskToRemoveNamedFunctionFromFrameMap";
                break;
            case 101:
                objArr[2] = "markStartLineNumber";
                break;
            case 102:
                objArr[2] = "markLineNumber";
                break;
            case 103:
            case 104:
                objArr[2] = "doFinallyOnReturn";
                break;
            case 105:
                objArr[2] = "genFinallyBlockOrGoto";
                break;
            case 106:
            case 107:
            case 108:
                objArr[2] = "addGapLabelsForNestedTryCatchWithoutFinally";
                break;
            case 109:
                objArr[2] = "visitReturnExpression";
                break;
            case 110:
            case 111:
                objArr[2] = "generateFinallyBlocksIfNeeded";
                break;
            case 112:
            case 113:
                objArr[2] = "getNonLocalReturnInfo";
                break;
            case 114:
                objArr[2] = "returnExpression";
                break;
            case 115:
                objArr[2] = "endsWithReturn";
                break;
            case 116:
            case 117:
                objArr[2] = "isLambdaVoidBody";
                break;
            case 118:
                objArr[2] = "isLambdaBody";
                break;
            case 119:
            case 120:
                objArr[2] = "visitSimpleNameExpression";
                break;
            case 121:
            case 122:
            case 123:
                objArr[2] = "generateNonIntrinsicSimpleNameExpression";
                break;
            case 124:
                objArr[2] = "applyIntrinsic";
                break;
            case 125:
                objArr[2] = "getSuperCallTarget";
                break;
            case 126:
                objArr[2] = "findLocalOrCapturedValue";
                break;
            case 127:
                objArr[2] = "findCapturedValue";
                break;
            case 128:
                objArr[2] = "lookupCapturedValueInConstructorParameters";
                break;
            case 129:
            case 130:
            case 134:
            case 135:
                objArr[2] = "intermediateValueForProperty";
                break;
            case 132:
            case 133:
                objArr[2] = "getBackingFieldContext";
                break;
            case 136:
            case 137:
                objArr[2] = "intermediateValueForSyntheticExtensionProperty";
                break;
            case 139:
                objArr[2] = "visitCallExpression";
                break;
            case 140:
                objArr[2] = "visitCollectionLiteralExpression";
                break;
            case 141:
            case 142:
                objArr[2] = "genSamInterfaceValue";
                break;
            case 143:
                objArr[2] = "accessibleFunctionDescriptor";
                break;
            case 146:
                objArr[2] = "createAccessorForHiddenConstructor";
                break;
            case 147:
            case 148:
            case 150:
            case 151:
            case 152:
                objArr[2] = "invokeFunction";
                break;
            case 156:
            case 157:
                objArr[2] = "prepareCoroutineArgumentForSuspendCall";
                break;
            case 158:
                objArr[2] = "getContinuationParameterFromEnclosingSuspendFunction";
                break;
            case 159:
                objArr[2] = "getContinuationParameterFromEnclosingSuspendFunctionDescriptor";
                break;
            case 160:
            case 161:
                objArr[2] = "resolveToCallable";
                break;
            case 164:
            case 165:
            case 166:
            case 167:
            case 168:
            case 169:
            case 170:
            case 171:
                objArr[2] = "invokeMethodWithArguments";
                break;
            case 172:
            case 173:
            case 174:
                objArr[2] = "putReceiverAndInlineMarkerIfNeeded";
                break;
            case 175:
            case 179:
            case 180:
            case 181:
                objArr[2] = "getOrCreateCallGenerator";
                break;
            case 178:
                objArr[2] = "getOrCreateCallGeneratorForDefaultImplBody";
                break;
            case 182:
                objArr[2] = "approximateCapturedType";
                break;
            case 185:
            case 186:
                objArr[2] = "getTypeArgumentsForResolvedCall";
                break;
            case 192:
                objArr[2] = "generateInstanceReceiver";
                break;
            case 193:
            case 194:
                objArr[2] = "getContextForCompanionObjectAccessorIfRequiredOrNull";
                break;
            case 195:
                objArr[2] = "generateExtensionReceiver";
                break;
            case 198:
                objArr[2] = "generateScriptReceiver";
                break;
            case 199:
            case 202:
                objArr[2] = "generateThisOrOuter";
                break;
            case 201:
                objArr[2] = "isInsideSingleton";
                break;
            case 208:
                objArr[2] = "generateThisOrOuterFromContext";
                break;
            case 210:
            case 211:
                objArr[2] = "genVarargs";
                break;
            case 215:
                objArr[2] = "visitClassLiteralExpression";
                break;
            case 216:
                objArr[2] = "visitCallableReferenceExpression";
                break;
            case 217:
                objArr[2] = "generateCallableReferenceReceiver";
                break;
            case 218:
            case 219:
            case 220:
                objArr[2] = "generatePropertyReference";
                break;
            case 222:
                objArr[2] = "generateClassLiteralReference";
                break;
            case 224:
                objArr[2] = "visitDotQualifiedExpression";
                break;
            case 225:
            case 226:
                objArr[2] = "generateExpressionWithNullFallback";
                break;
            case XKeySym.XK_atilde /* 227 */:
            case XKeySym.XK_adiaeresis /* 228 */:
                objArr[2] = "generateSafeQualifiedExpression";
                break;
            case XKeySym.XK_aring /* 229 */:
                objArr[2] = "visitSafeQualifiedExpression";
                break;
            case XKeySym.XK_ae /* 230 */:
            case XKeySym.XK_ccedilla /* 231 */:
                objArr[2] = "visitBinaryExpression";
                break;
            case XKeySym.XK_egrave /* 232 */:
            case XKeySym.XK_eacute /* 233 */:
            case XKeySym.XK_ecircumflex /* 234 */:
            case XKeySym.XK_ediaeresis /* 235 */:
                objArr[2] = "genLazyUnlessProvided";
                break;
            case XKeySym.XK_igrave /* 236 */:
            case XKeySym.XK_iacute /* 237 */:
                objArr[2] = "genUnlessProvided";
                break;
            case XKeySym.XK_icircumflex /* 238 */:
                objArr[2] = "generateEquals";
                break;
            case XKeySym.XK_idiaeresis /* 239 */:
            case 240:
            case XKeySym.XK_ntilde /* 241 */:
            case 242:
            case 243:
            case 244:
            case 245:
                objArr[2] = "genEqualsForInlineClasses";
                break;
            case 247:
                objArr[2] = "isSelectorPureNonNullType";
                break;
            case 248:
            case 249:
            case 250:
            case 251:
                objArr[2] = "genCmpPrimitiveToSafeCall";
                break;
            case 252:
            case 253:
            case 254:
            case 255:
                objArr[2] = "genCmpSafeCallToPrimitive";
                break;
            case 256:
            case 257:
            case 258:
            case 259:
            case 260:
                objArr[2] = "genEqualsForExpressionsPreferIeee754Arithmetic";
                break;
            case GlobalMediaRouter.CallbackHandler.MSG_ROUTE_PRESENTATION_DISPLAY_CHANGED /* 261 */:
                objArr[2] = "boxIfNullable";
                break;
            case GlobalMediaRouter.CallbackHandler.MSG_ROUTE_ANOTHER_SELECTED /* 264 */:
            case 265:
            case 266:
            case 267:
                objArr[2] = "generate754EqualsForNullableTypesViaIntrinsic";
                break;
            case 268:
            case 269:
            case 270:
            case 271:
                objArr[2] = "generate754EqualsForNullableTypes";
                break;
            case 272:
                objArr[2] = "generateElvis";
                break;
            case 273:
            case 274:
            case 275:
            case 276:
                objArr[2] = "putCallAugAssignMethod";
                break;
            case 277:
                objArr[2] = "visitLabeledExpression";
                break;
            case 278:
            case 279:
                objArr[2] = "visitPrefixExpression";
                break;
            case 280:
                objArr[2] = "visitPostfixExpression";
                break;
            case 281:
                objArr[2] = "visitProperty";
                break;
            case 282:
                objArr[2] = "visitDestructuringDeclaration";
                break;
            case 283:
                objArr[2] = "initializeDestructuringDeclaration";
                break;
            case 284:
            case 285:
            case 286:
            case 287:
            case 288:
            case 289:
                objArr[2] = "initializeDestructuringDeclarationVariables";
                break;
            case ParserBasicInformation.SCOPE_UBOUND /* 290 */:
                objArr[2] = "getVariableMetadataValue";
                break;
            case 292:
                objArr[2] = "adjustVariableValue";
                break;
            case 294:
                objArr[2] = "initializeLocalVariable";
                break;
            case 295:
                objArr[2] = "initializeLocalVariableWithFakeDefaultValue";
                break;
            case 296:
                objArr[2] = "generateProvideDelegateCallForLocalVariable";
                break;
            case 298:
                objArr[2] = "getVariableDescriptorNotNull";
                break;
            case 300:
            case 301:
            case 302:
                objArr[2] = "initializePropertyMetadata";
                break;
            case 303:
            case 304:
                objArr[2] = "generateNewCall";
                break;
            case 307:
                objArr[2] = "getConstructorDescriptor";
                break;
            case 309:
                objArr[2] = "getConstructorReceiver";
                break;
            case 310:
            case 311:
                objArr[2] = "generateConstructorCall";
                break;
            case 313:
            case 314:
            case 315:
                objArr[2] = "generateNewArray";
                break;
            case 316:
                objArr[2] = "newArrayInstruction";
                break;
            case TypedValues.AttributesType.TYPE_EASING /* 317 */:
                objArr[2] = "visitArrayAccessExpression";
                break;
            case TypedValues.AttributesType.TYPE_PIVOT_TARGET /* 318 */:
            case 319:
            case 320:
            case MediaError.DetailedErrorCode.DASH_NETWORK /* 321 */:
                objArr[2] = "createCollectionElementReceiver";
                break;
            case MediaError.DetailedErrorCode.DASH_NO_INIT /* 322 */:
                objArr[2] = "visitThrowExpression";
                break;
            case 323:
                objArr[2] = "visitThisExpression";
                break;
            case 324:
                objArr[2] = "visitTryExpression";
                break;
            case 325:
            case 326:
                objArr[2] = "generateExceptionTable";
                break;
            case 327:
            case 328:
                objArr[2] = "getCurrentCatchIntervals";
                break;
            case 330:
                objArr[2] = "visitBinaryWithTypeRHSExpression";
                break;
            case MediaError.DetailedErrorCode.SMOOTH_NETWORK /* 331 */:
                objArr[2] = "visitIsExpression";
                break;
            case MediaError.DetailedErrorCode.SMOOTH_NO_MEDIA_DATA /* 332 */:
                objArr[2] = "propagateChildReifiedTypeParametersUsages";
                break;
            case 333:
                objArr[2] = "visitWhenExpression";
                break;
            case 334:
                objArr[2] = "putUnitInstanceOntoStackForNonExhaustiveWhen";
                break;
            case 338:
                objArr[2] = "addBlockStackElementsForNonLocalReturns";
                break;
            case 339:
            case 340:
            case 341:
            case 342:
                objArr[2] = "delegatedVariableValue";
                break;
            case 346:
                objArr[2] = "consumeReifiedOperationMarker";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 6:
            case 7:
            case 8:
            case 9:
            case 16:
            case 17:
            case 27:
            case 29:
            case 31:
            case 32:
            case 44:
            case 51:
            case 52:
            case 64:
            case 74:
            case 79:
            case 80:
            case 81:
            case 83:
            case 84:
            case 90:
            case 91:
            case 131:
            case 138:
            case 144:
            case 145:
            case 149:
            case 153:
            case 154:
            case 155:
            case 162:
            case 163:
            case 176:
            case 177:
            case 183:
            case 184:
            case 187:
            case 188:
            case 189:
            case 190:
            case 191:
            case 196:
            case 197:
            case 200:
            case 203:
            case 204:
            case 205:
            case 206:
            case 207:
            case 209:
            case 212:
            case 213:
            case 214:
            case 221:
            case 223:
            case 246:
            case GlobalMediaRouter.CallbackHandler.MSG_ROUTE_SELECTED /* 262 */:
            case 263:
            case ParserBasicInformation.SCOPE_SIZE /* 291 */:
            case 293:
            case 297:
            case 299:
            case 305:
            case 306:
            case 308:
            case 312:
            case 329:
            case 335:
            case 336:
            case 337:
            case 343:
            case 344:
            case TarConstants.XSTAR_PREFIX_OFFSET /* 345 */:
                throw new IllegalStateException(str2);
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    public ExpressionCodegen(MethodVisitor methodVisitor, FrameMap frameMap, Type type, MethodContext methodContext, GenerationState generationState, MemberCodegen<?> memberCodegen) {
        if (methodVisitor == null) {
            $$$reportNull$$$0(0);
        }
        if (frameMap == null) {
            $$$reportNull$$$0(1);
        }
        if (type == null) {
            $$$reportNull$$$0(2);
        }
        if (methodContext == null) {
            $$$reportNull$$$0(3);
        }
        if (generationState == null) {
            $$$reportNull$$$0(4);
        }
        if (memberCodegen == null) {
            $$$reportNull$$$0(5);
        }
        this.statementVisitor = new CodegenStatementVisitor(this);
        this.defaultCallGenerator = new CallGenerator.DefaultCallGenerator(this);
        this.blockStackElements = new Stack<>();
        this.tempVariables = new HashMap();
        this.myLastLineNumber = -1;
        this.shouldMarkLineNumbers = true;
        this.finallyDepth = 0;
        this.state = generationState;
        this.typeMapper = generationState.getTypeMapper();
        this.bindingContext = generationState.getBindingContext();
        InstructionAdapter instructionAdapter = new InstructionAdapter(methodVisitor);
        this.v = instructionAdapter;
        this.myFrameMap = frameMap;
        this.context = methodContext;
        this.returnType = type;
        this.parentCodegen = memberCodegen;
        this.tailRecursionCodegen = new TailRecursionCodegen(methodContext, this, instructionAdapter, generationState);
        this.switchCodegenProvider = new SwitchCodegenProvider(this);
        this.typeSystem = new ClassicTypeSystemContextImpl(generationState.getModule().getBuiltIns());
    }

    private static FunctionDescriptor getOriginalSuspendLambdaDescriptorFromContext(MethodContext methodContext) {
        if ((methodContext.getParentContext() instanceof ClosureContext) && methodContext.getParentContext().closure != null && methodContext.getParentContext().closure.isSuspend()) {
            return ((ClosureContext) methodContext.getParentContext()).getOriginalSuspendLambdaDescriptor();
        }
        return null;
    }

    static class LoopBlockStackElement extends BlockStackElement {
        final Label breakLabel;
        final Label continueLabel;
        public final KtSimpleNameExpression targetLabel;

        LoopBlockStackElement(Label label, Label label2, KtSimpleNameExpression ktSimpleNameExpression) {
            this.breakLabel = label;
            this.continueLabel = label2;
            this.targetLabel = ktSimpleNameExpression;
        }
    }

    static class TryBlockStackElement extends BlockStackElement {
        List<Label> gaps = new ArrayList();

        TryBlockStackElement() {
        }

        void addGapLabel(Label label) {
            this.gaps.add(label);
        }
    }

    static class TryWithFinallyBlockStackElement extends TryBlockStackElement {
        final KtTryExpression expression;

        TryWithFinallyBlockStackElement(KtTryExpression ktTryExpression) {
            this.expression = ktTryExpression;
        }
    }

    public GenerationState getState() {
        GenerationState generationState = this.state;
        if (generationState == null) {
            $$$reportNull$$$0(6);
        }
        return generationState;
    }

    public BindingContext getBindingContext() {
        BindingContext bindingContext = this.bindingContext;
        if (bindingContext == null) {
            $$$reportNull$$$0(7);
        }
        return bindingContext;
    }

    public MemberCodegen<?> getParentCodegen() {
        MemberCodegen<?> memberCodegen = this.parentCodegen;
        if (memberCodegen == null) {
            $$$reportNull$$$0(8);
        }
        return memberCodegen;
    }

    public KotlinTypeMapper getTypeMapper() {
        KotlinTypeMapper kotlinTypeMapper = this.typeMapper;
        if (kotlinTypeMapper == null) {
            $$$reportNull$$$0(9);
        }
        return kotlinTypeMapper;
    }

    public ObjectLiteralResult generateObjectLiteral(KtObjectLiteralExpression ktObjectLiteralExpression) {
        if (ktObjectLiteralExpression == null) {
            $$$reportNull$$$0(10);
        }
        KtObjectDeclaration objectDeclaration = ktObjectLiteralExpression.getObjectDeclaration();
        ClassDescriptor classDescriptor = (ClassDescriptor) this.bindingContext.get(BindingContext.CLASS, objectDeclaration);
        ImplementationBodyCodegen implementationBodyCodegen = new ImplementationBodyCodegen(objectDeclaration, this.context.intoAnonymousClass(classDescriptor, this, OwnerKind.IMPLEMENTATION), this.state.getFactory().newVisitor(JvmDeclarationOriginKt.OtherOrigin(objectDeclaration, classDescriptor), CodegenBinding.asmTypeForAnonymousClass(this.bindingContext, objectDeclaration), ktObjectLiteralExpression.getContainingFile()), this.state, getParentCodegen(), true);
        implementationBodyCodegen.generate();
        addReifiedParametersFromSignature(implementationBodyCodegen, classDescriptor);
        propagateChildReifiedTypeParametersUsages(implementationBodyCodegen.getReifiedTypeParametersUsages());
        return new ObjectLiteralResult(implementationBodyCodegen.getReifiedTypeParametersUsages().wereUsedReifiedParameters(), classDescriptor);
    }

    private static void addReifiedParametersFromSignature(MemberCodegen<?> memberCodegen, ClassDescriptor classDescriptor) {
        if (memberCodegen == null) {
            $$$reportNull$$$0(11);
        }
        if (classDescriptor == null) {
            $$$reportNull$$$0(12);
        }
        Iterator<KotlinType> it = classDescriptor.getTypeConstructor().mo7438getSupertypes().iterator();
        while (it.hasNext()) {
            processTypeArguments(memberCodegen, it.next());
        }
    }

    private static void processTypeArguments(MemberCodegen<?> memberCodegen, KotlinType kotlinType) {
        if (memberCodegen == null) {
            $$$reportNull$$$0(13);
        }
        for (TypeProjection typeProjection : kotlinType.getArguments()) {
            if (!typeProjection.isStarProjection()) {
                TypeParameterDescriptor typeParameterDescriptorOrNull = TypeUtils.getTypeParameterDescriptorOrNull(typeProjection.getNullableAnyType());
                if (typeParameterDescriptorOrNull != null) {
                    if (typeParameterDescriptorOrNull.isReified()) {
                        memberCodegen.getReifiedTypeParametersUsages().addUsedReifiedParameter(typeParameterDescriptorOrNull.getShortName().asString());
                    }
                } else {
                    processTypeArguments(memberCodegen, typeProjection.getNullableAnyType());
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class ObjectLiteralResult {
        private final ClassDescriptor classDescriptor;
        private final boolean wereReifiedMarkers;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "classDescriptor", "org/jetbrains/kotlin/codegen/ExpressionCodegen$ObjectLiteralResult", "<init>"));
        }

        public ObjectLiteralResult(boolean z, ClassDescriptor classDescriptor) {
            if (classDescriptor == null) {
                $$$reportNull$$$0(0);
            }
            this.wereReifiedMarkers = z;
            this.classDescriptor = classDescriptor;
        }
    }

    private StackValue castToRequiredTypeOfInterfaceIfNeeded(StackValue stackValue, ClassDescriptor classDescriptor, ClassDescriptor classDescriptor2) {
        if (classDescriptor == null) {
            $$$reportNull$$$0(14);
        }
        if (classDescriptor2 == null) {
            $$$reportNull$$$0(15);
        }
        if (JvmCodegenUtil.isJvmInterface(classDescriptor) || !JvmCodegenUtil.isJvmInterface(classDescriptor2)) {
            if (stackValue == null) {
                $$$reportNull$$$0(17);
            }
            return stackValue;
        }
        SimpleType defaultType = classDescriptor2.getDefaultType();
        StackValue stackValueCoercion = StackValue.coercion(stackValue, asmType(defaultType), defaultType);
        if (stackValueCoercion == null) {
            $$$reportNull$$$0(16);
        }
        return stackValueCoercion;
    }

    public StackValue genQualified(StackValue stackValue, KtElement ktElement) {
        return genQualified(stackValue, ktElement, this);
    }

    private StackValue genQualified(StackValue stackValue, KtElement ktElement, KtVisitor<StackValue, StackValue> ktVisitor) {
        RuntimeAssertionInfo runtimeAssertionInfo;
        StackValue stackValueGenSamInterfaceValue;
        if (this.tempVariables.containsKey(ktElement)) {
            throw new IllegalStateException("Inconsistent state: expression saved to a temporary variable is a selector");
        }
        if (!(ktElement instanceof KtBlockExpression)) {
            markStartLineNumber(ktElement);
        }
        try {
            if ((ktElement instanceof KtExpression) && (stackValueGenSamInterfaceValue = genSamInterfaceValue((KtExpression) ktElement, ktVisitor)) != null) {
                return stackValueGenSamInterfaceValue;
            }
            StackValue stackValueCoerceAndBoxInlineClassIfNeeded = coerceAndBoxInlineClassIfNeeded(ktElement, (StackValue) ktElement.accept(ktVisitor, stackValue));
            if (ktElement instanceof KtExpression) {
                KtExpression ktExpression = (KtExpression) ktElement;
                runtimeAssertionInfo = (RuntimeAssertionInfo) this.bindingContext.get(JvmBindingContextSlices.RUNTIME_ASSERTION_INFO, ktExpression);
                if (runtimeAssertionInfo == null && this.state.getLanguageVersionSettings().supportsFeature(LanguageFeature.StrictJavaNullabilityAssertions)) {
                    runtimeAssertionInfo = (RuntimeAssertionInfo) this.bindingContext.get(JvmBindingContextSlices.BODY_RUNTIME_ASSERTION_INFO, ktExpression);
                }
            } else {
                runtimeAssertionInfo = null;
            }
            return BuiltinSpecialBridgesKt.isValueArgumentForCallToMethodWithTypeCheckBarrier(ktElement, this.bindingContext) ? stackValueCoerceAndBoxInlineClassIfNeeded : DescriptorAsmUtil.genNotNullAssertions(this.state, stackValueCoerceAndBoxInlineClassIfNeeded, runtimeAssertionInfo);
        } catch (CompilationException e) {
            throw e;
        } catch (ProcessCanceledException e2) {
            throw e2;
        } catch (Throwable th) {
            throw new CompilationException("Failed to generate expression: " + ktElement.getClass().getSimpleName(), th, ktElement);
        }
    }

    private StackValue coerceAndBoxInlineClassIfNeeded(KtElement ktElement, final StackValue stackValue) {
        ResolvedCall<? extends CallableDescriptor> resolvedCall = CallUtilKt.getResolvedCall(ktElement, this.bindingContext);
        if (resolvedCall == null) {
            return stackValue;
        }
        CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
        if (!(resultingDescriptor instanceof FunctionDescriptor)) {
            return stackValue;
        }
        FunctionDescriptor functionDescriptor = (FunctionDescriptor) resultingDescriptor;
        if (!functionDescriptor.isSuspend()) {
            return stackValue;
        }
        if ((resolvedCall instanceof VariableAsFunctionResolvedCall) && functionDescriptor.isOperator() && functionDescriptor.getShortName().getIdentifier().equals("invoke")) {
            return stackValue;
        }
        KotlinType kotlinTypeOriginalReturnTypeOfSuspendFunctionReturningUnboxedInlineClass = CoroutineCodegenUtilKt.originalReturnTypeOfSuspendFunctionReturningUnboxedInlineClass(functionDescriptor, this.typeMapper);
        final KotlinType kotlinType = kotlinTypeOriginalReturnTypeOfSuspendFunctionReturningUnboxedInlineClass != null ? kotlinTypeOriginalReturnTypeOfSuspendFunctionReturningUnboxedInlineClass : stackValue.kotlinType;
        final Type typeMapType = kotlinTypeOriginalReturnTypeOfSuspendFunctionReturningUnboxedInlineClass != null ? this.typeMapper.mapType(kotlinTypeOriginalReturnTypeOfSuspendFunctionReturningUnboxedInlineClass) : stackValue.type;
        return new StackValue(typeMapType, kotlinType) { // from class: org.jetbrains.kotlin.codegen.ExpressionCodegen.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[3];
                if (i != 1) {
                    objArr[0] = ModuleXmlParser.TYPE;
                } else {
                    objArr[0] = RegisterSpec.PREFIX;
                }
                objArr[1] = "org/jetbrains/kotlin/codegen/ExpressionCodegen$1";
                objArr[2] = "putSelector";
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
            }

            @Override // org.jetbrains.kotlin.codegen.StackValue
            public void putSelector(Type type, KotlinType kotlinType2, InstructionAdapter instructionAdapter) {
                if (type == null) {
                    $$$reportNull$$$0(0);
                }
                if (instructionAdapter == null) {
                    $$$reportNull$$$0(1);
                }
                KotlinType kotlinType3 = kotlinType;
                if ((kotlinType3 != null && InlineClassesUtilsKt.isInlineClassType(kotlinType3)) || (kotlinType2 != null && InlineClassesUtilsKt.isInlineClassType(kotlinType2))) {
                    stackValue.put(instructionAdapter);
                    StackValue.coerce(AsmTypes.OBJECT_TYPE, typeMapType, instructionAdapter);
                    StackValue.coerce(typeMapType, kotlinType, type, kotlinType2, instructionAdapter);
                    return;
                }
                stackValue.put(type, kotlinType2, instructionAdapter);
            }
        };
    }

    public StackValue gen(KtElement ktElement) {
        StackValue stackValue = this.tempVariables.get(ktElement);
        return stackValue != null ? stackValue : genQualified(StackValue.none(), ktElement);
    }

    public void gen(KtElement ktElement, Type type) {
        gen(ktElement, type, null);
    }

    public void gen(KtElement ktElement, Type type, KotlinType kotlinType) {
        putStackValue(ktElement, type, kotlinType, Type.VOID_TYPE.equals(type) ? genStatement(ktElement) : gen(ktElement));
    }

    private void putStackValue(KtElement ktElement, Type type, KotlinType kotlinType, StackValue stackValue) {
        ScriptContext scriptContext;
        FieldInfo resultFieldInfo;
        if (type == null) {
            $$$reportNull$$$0(18);
        }
        if (stackValue == null) {
            $$$reportNull$$$0(19);
        }
        if (stackValue.type != Type.VOID_TYPE && (scriptContext = getScriptContext()) != null && ktElement == scriptContext.getLastStatement() && (resultFieldInfo = scriptContext.getResultFieldInfo()) != null) {
            StackValue.field(resultFieldInfo, StackValue.LOCAL_0).store(stackValue, this.v);
            this.state.getScriptSpecific().setResultType(resultFieldInfo.getFieldKotlinType());
            this.state.getScriptSpecific().setResultFieldName(resultFieldInfo.getFieldName());
            return;
        }
        stackValue.put(type, kotlinType, this.v);
    }

    private ScriptContext getScriptContext() {
        CodegenContext context = getContext();
        while (context != null && !(context instanceof ScriptContext)) {
            context = context.getParentContext();
        }
        return (ScriptContext) context;
    }

    private StackValue genLazy(KtElement ktElement, Type type) {
        return StackValue.coercion(gen(ktElement), type, null);
    }

    private StackValue genLazy(KtElement ktElement, Type type, KotlinType kotlinType) {
        return StackValue.coercion(gen(ktElement), type, kotlinType);
    }

    private StackValue genStatement(KtElement ktElement) {
        return genQualified(StackValue.none(), ktElement, this.statementVisitor);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public StackValue visitClass(KtClass ktClass, StackValue stackValue) {
        if (ktClass == null) {
            $$$reportNull$$$0(20);
        }
        return visitClassOrObject(ktClass);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public StackValue visitTypeAlias(KtTypeAlias ktTypeAlias, StackValue stackValue) {
        if (ktTypeAlias == null) {
            $$$reportNull$$$0(21);
        }
        return StackValue.none();
    }

    private StackValue visitClassOrObject(KtClassOrObject ktClassOrObject) {
        ClassDescriptor classDescriptor = (ClassDescriptor) this.bindingContext.get(BindingContext.CLASS, ktClassOrObject);
        new ImplementationBodyCodegen(ktClassOrObject, this.context.intoAnonymousClass(classDescriptor, this, OwnerKind.IMPLEMENTATION), this.state.getFactory().newVisitor(JvmDeclarationOriginKt.OtherOrigin(ktClassOrObject, classDescriptor), CodegenBinding.asmTypeForAnonymousClass(this.bindingContext, ktClassOrObject), ktClassOrObject.getContainingFile()), this.state, getParentCodegen(), true).generate();
        return StackValue.none();
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public StackValue visitObjectDeclaration(KtObjectDeclaration ktObjectDeclaration, StackValue stackValue) {
        if (ktObjectDeclaration == null) {
            $$$reportNull$$$0(22);
        }
        return visitClassOrObject(ktObjectDeclaration);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public StackValue visitExpression(KtExpression ktExpression, StackValue stackValue) {
        if (ktExpression == null) {
            $$$reportNull$$$0(23);
        }
        throw new UnsupportedOperationException("Codegen for " + ktExpression + " is not yet implemented");
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public StackValue visitSuperExpression(KtSuperExpression ktSuperExpression, StackValue stackValue) {
        if (ktSuperExpression == null) {
            $$$reportNull$$$0(24);
        }
        return StackValue.thisOrOuter(this, getSuperCallLabelTarget(this.context, ktSuperExpression), true, false);
    }

    public static ClassDescriptor getSuperCallLabelTarget(CodegenContext<?> codegenContext, KtSuperExpression ktSuperExpression) {
        if (codegenContext == null) {
            $$$reportNull$$$0(25);
        }
        if (ktSuperExpression == null) {
            $$$reportNull$$$0(26);
        }
        ClassDescriptor classDescriptor = (ClassDescriptor) ((KotlinType) codegenContext.getState().getBindingContext().get(BindingContext.THIS_TYPE_FOR_SUPER_EXPRESSION, ktSuperExpression)).getConstructor().getThis$0();
        if (classDescriptor == null) {
            $$$reportNull$$$0(27);
        }
        return classDescriptor;
    }

    public Type asmType(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(28);
        }
        Type typeMapType = this.typeMapper.mapType(kotlinType);
        if (typeMapType == null) {
            $$$reportNull$$$0(29);
        }
        return typeMapType;
    }

    public Type mapTypeAsDeclaration(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(30);
        }
        Type typeMapTypeAsDeclaration = this.typeMapper.mapTypeAsDeclaration(kotlinType);
        if (typeMapTypeAsDeclaration == null) {
            $$$reportNull$$$0(31);
        }
        return typeMapTypeAsDeclaration;
    }

    public Type expressionType(KtExpression ktExpression) {
        Type typeAsmType = CodegenUtilKt.asmType(ktExpression, this.typeMapper, this.bindingContext);
        if (typeAsmType == null) {
            $$$reportNull$$$0(32);
        }
        return typeAsmType;
    }

    public KotlinType kotlinType(KtExpression ktExpression) {
        return CodegenUtilKt.kotlinType(ktExpression, this.bindingContext);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public StackValue visitParenthesizedExpression(KtParenthesizedExpression ktParenthesizedExpression, StackValue stackValue) {
        if (ktParenthesizedExpression == null) {
            $$$reportNull$$$0(33);
        }
        return genQualified(stackValue, ktParenthesizedExpression.getExpression());
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public StackValue visitAnnotatedExpression(KtAnnotatedExpression ktAnnotatedExpression, StackValue stackValue) {
        if (ktAnnotatedExpression == null) {
            $$$reportNull$$$0(34);
        }
        return genQualified(stackValue, ktAnnotatedExpression.getBaseExpression());
    }

    private static boolean isEmptyExpression(KtElement ktElement) {
        if (ktElement == null) {
            return true;
        }
        if (ktElement instanceof KtBlockExpression) {
            List<KtExpression> statements = ((KtBlockExpression) ktElement).getStatements();
            if (statements.size() == 0 || (statements.size() == 1 && isEmptyExpression(statements.get(0)))) {
                return true;
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public StackValue visitIfExpression(KtIfExpression ktIfExpression, StackValue stackValue) {
        if (ktIfExpression == null) {
            $$$reportNull$$$0(35);
        }
        return generateIfExpression(ktIfExpression, false);
    }

    StackValue generateIfExpression(final KtIfExpression ktIfExpression, final boolean z) {
        if (ktIfExpression == null) {
            $$$reportNull$$$0(36);
        }
        Type typeExpressionType = z ? Type.VOID_TYPE : expressionType(ktIfExpression);
        final KotlinType kotlinType = z ? null : kotlinType(ktIfExpression);
        final StackValue stackValueGen = gen(ktIfExpression.getCondition());
        final KtExpression then = ktIfExpression.getThen();
        final KtExpression ktExpression = ktIfExpression.getElse();
        if (isEmptyExpression(then)) {
            if (isEmptyExpression(ktExpression)) {
                return StackValue.coercion(stackValueGen, typeExpressionType, kotlinType);
            }
            return generateSingleBranchIf(stackValueGen, ktIfExpression, ktExpression, false, z);
        }
        if (isEmptyExpression(ktExpression)) {
            return generateSingleBranchIf(stackValueGen, ktIfExpression, then, true, z);
        }
        final Type type = typeExpressionType;
        return StackValue.operation(typeExpressionType, kotlinType, new Function1() { // from class: org.jetbrains.kotlin.codegen.ExpressionCodegen$$ExternalSyntheticLambda35
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return this.f$0.m6397x328c2a86(stackValueGen, then, type, kotlinType, ktExpression, ktIfExpression, z, (InstructionAdapter) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$generateIfExpression$0$org-jetbrains-kotlin-codegen-ExpressionCodegen, reason: not valid java name */
    /* synthetic */ Unit m6397x328c2a86(StackValue stackValue, KtExpression ktExpression, Type type, KotlinType kotlinType, KtExpression ktExpression2, KtIfExpression ktIfExpression, boolean z, InstructionAdapter instructionAdapter) {
        Label label = new Label();
        BranchedValue.INSTANCE.condJump(stackValue, label, true, instructionAdapter);
        Label label2 = new Label();
        gen(ktExpression, type, kotlinType);
        instructionAdapter.goTo(label2);
        instructionAdapter.mark(label);
        gen(ktExpression2, type, kotlinType);
        markLineNumber(ktIfExpression, z);
        instructionAdapter.mark(label2);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public StackValue visitWhileExpression(final KtWhileExpression ktWhileExpression, StackValue stackValue) {
        if (ktWhileExpression == null) {
            $$$reportNull$$$0(37);
        }
        return StackValue.operation(Type.VOID_TYPE, new Function1() { // from class: org.jetbrains.kotlin.codegen.ExpressionCodegen$$ExternalSyntheticLambda16
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return this.f$0.m6414xff612cb1(ktWhileExpression, (InstructionAdapter) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$visitWhileExpression$1$org-jetbrains-kotlin-codegen-ExpressionCodegen, reason: not valid java name */
    /* synthetic */ Unit m6414xff612cb1(KtWhileExpression ktWhileExpression, InstructionAdapter instructionAdapter) {
        generateWhile(ktWhileExpression);
        return Unit.INSTANCE;
    }

    private void generateWhile(KtWhileExpression ktWhileExpression) {
        if (ktWhileExpression == null) {
            $$$reportNull$$$0(38);
        }
        Label label = new Label();
        this.v.mark(label);
        Label label2 = new Label();
        this.blockStackElements.push(new LoopBlockStackElement(label2, label, targetLabel(ktWhileExpression)));
        BranchedValue.INSTANCE.loopJump(gen(ktWhileExpression.getCondition()), label2, true, this.v);
        generateLoopBody(ktWhileExpression.getBody());
        markStartLineNumber(ktWhileExpression);
        this.v.goTo(label);
        this.v.mark(label2);
        this.blockStackElements.pop();
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public StackValue visitDoWhileExpression(final KtDoWhileExpression ktDoWhileExpression, StackValue stackValue) {
        if (ktDoWhileExpression == null) {
            $$$reportNull$$$0(39);
        }
        return StackValue.operation(Type.VOID_TYPE, new Function1() { // from class: org.jetbrains.kotlin.codegen.ExpressionCodegen$$ExternalSyntheticLambda42
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return this.f$0.m6406x634c2bbb(ktDoWhileExpression, (InstructionAdapter) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$visitDoWhileExpression$2$org-jetbrains-kotlin-codegen-ExpressionCodegen, reason: not valid java name */
    /* synthetic */ Unit m6406x634c2bbb(KtDoWhileExpression ktDoWhileExpression, InstructionAdapter instructionAdapter) {
        generateDoWhile(ktDoWhileExpression);
        return Unit.INSTANCE;
    }

    private void generateDoWhile(KtDoWhileExpression ktDoWhileExpression) {
        StackValue stackValueGen;
        if (ktDoWhileExpression == null) {
            $$$reportNull$$$0(40);
        }
        Label label = new Label();
        this.v.mark(label);
        Label label2 = new Label();
        Label label3 = new Label();
        this.blockStackElements.push(new LoopBlockStackElement(label2, label3, targetLabel(ktDoWhileExpression)));
        PseudoInsnsKt.fakeAlwaysFalseIfeq(this.v, label3);
        PseudoInsnsKt.fakeAlwaysFalseIfeq(this.v, label2);
        KtElement body = ktDoWhileExpression.getBody();
        KtExpression condition = ktDoWhileExpression.getCondition();
        StackValueWithLeaveTask stackValueWithLeaveTaskGenerateBlock = null;
        if (body instanceof KtBlockExpression) {
            List<KtExpression> statements = ((KtBlockExpression) body).getStatements();
            List<KtExpression> arrayList = new ArrayList<>(statements.size() + 1);
            arrayList.addAll(statements);
            arrayList.add(condition);
            stackValueWithLeaveTaskGenerateBlock = generateBlock(arrayList, false, label3, null);
            stackValueGen = stackValueWithLeaveTaskGenerateBlock.getStackValue();
        } else {
            if (body != null) {
                gen(body, Type.VOID_TYPE);
            }
            this.v.mark(label3);
            stackValueGen = gen(condition);
        }
        BranchedValue.INSTANCE.loopJump(stackValueGen, label, false, this.v);
        if (stackValueWithLeaveTaskGenerateBlock != null) {
            stackValueWithLeaveTaskGenerateBlock.getLeaveTasks().invoke(stackValueGen);
        }
        this.v.mark(label2);
        this.blockStackElements.pop();
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public StackValue visitForExpression(final KtForExpression ktForExpression, StackValue stackValue) {
        if (ktForExpression == null) {
            $$$reportNull$$$0(41);
        }
        return StackValue.operation(Type.VOID_TYPE, new Function1() { // from class: org.jetbrains.kotlin.codegen.ExpressionCodegen$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return this.f$0.m6407xa15d0b57(ktForExpression, (InstructionAdapter) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$visitForExpression$3$org-jetbrains-kotlin-codegen-ExpressionCodegen, reason: not valid java name */
    /* synthetic */ Unit m6407xa15d0b57(KtForExpression ktForExpression, InstructionAdapter instructionAdapter) {
        generateFor(ktForExpression);
        return Unit.INSTANCE;
    }

    private void generateFor(KtForExpression ktForExpression) {
        if (ktForExpression == null) {
            $$$reportNull$$$0(42);
        }
        generateForLoop(RangeValuesKt.createRangeValueForExpression(this, ktForExpression.getLoopRange()).createForLoopGenerator(this, ktForExpression));
    }

    public static KotlinType getExpectedReceiverType(ResolvedCall<? extends CallableDescriptor> resolvedCall) {
        if (resolvedCall == null) {
            $$$reportNull$$$0(43);
        }
        KotlinType type = resolvedCall.getResultingDescriptor().getExtensionReceiverParameter().getType();
        if (type == null) {
            $$$reportNull$$$0(44);
        }
        return type;
    }

    public static KtExpression getSingleArgumentExpression(ResolvedCall<? extends CallableDescriptor> resolvedCall) {
        if (resolvedCall == null) {
            $$$reportNull$$$0(45);
        }
        List<ResolvedValueArgument> valueArgumentsByIndex = resolvedCall.getValueArgumentsByIndex();
        if (valueArgumentsByIndex == null || valueArgumentsByIndex.size() != 1) {
            return null;
        }
        List<ValueArgument> arguments = valueArgumentsByIndex.get(0).getArguments();
        if (arguments.size() != 1) {
            return null;
        }
        return arguments.get(0).getArgumentExpression();
    }

    private OwnerKind contextKind() {
        return this.context.getContextKind();
    }

    private void generateForLoop(ForLoopGenerator forLoopGenerator) {
        Label label = new Label();
        Label label2 = new Label();
        Label label3 = new Label();
        forLoopGenerator.beforeLoop();
        forLoopGenerator.checkEmptyLoop(label);
        this.v.mark(label2);
        resetLastLineNumber();
        markStartLineNumber(forLoopGenerator.getForExpression());
        this.v.nop();
        forLoopGenerator.checkPreCondition(label);
        PseudoInsnsKt.fakeAlwaysFalseIfeq(this.v, label3);
        markStartLineNumber(forLoopGenerator.getForExpression());
        this.v.nop();
        forLoopGenerator.beforeBody();
        this.blockStackElements.push(new LoopBlockStackElement(label, label3, targetLabel(forLoopGenerator.getForExpression())));
        forLoopGenerator.body();
        this.blockStackElements.pop();
        this.v.mark(label3);
        forLoopGenerator.afterBody(label);
        this.v.goTo(label2);
        this.v.mark(label);
        forLoopGenerator.afterLoop();
    }

    public void generateLoopBody(KtExpression ktExpression) {
        if (ktExpression != null) {
            gen(ktExpression, Type.VOID_TYPE);
        }
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public StackValue visitBreakExpression(KtBreakExpression ktBreakExpression, StackValue stackValue) {
        if (ktBreakExpression == null) {
            $$$reportNull$$$0(46);
        }
        return generateBreakOrContinueExpression(ktBreakExpression, true, new Label(), new ArrayList());
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public StackValue visitContinueExpression(KtContinueExpression ktContinueExpression, StackValue stackValue) {
        if (ktContinueExpression == null) {
            $$$reportNull$$$0(47);
        }
        return generateBreakOrContinueExpression(ktContinueExpression, false, new Label(), new ArrayList());
    }

    private StackValue generateBreakOrContinueExpression(KtExpressionWithLabel ktExpressionWithLabel, boolean z, final Label label, List<TryBlockStackElement> list) {
        if (ktExpressionWithLabel == null) {
            $$$reportNull$$$0(48);
        }
        if (label == null) {
            $$$reportNull$$$0(49);
        }
        if (list == null) {
            $$$reportNull$$$0(50);
        }
        if (this.blockStackElements.isEmpty()) {
            throw new UnsupportedOperationException("Target label for break/continue not found");
        }
        BlockStackElement blockStackElementPeek = this.blockStackElements.peek();
        if (blockStackElementPeek instanceof TryWithFinallyBlockStackElement) {
            genFinallyBlockOrGoto((TryWithFinallyBlockStackElement) blockStackElementPeek, null, label, list);
            list.clear();
        } else if (blockStackElementPeek instanceof TryBlockStackElement) {
            list.add((TryBlockStackElement) blockStackElementPeek);
        } else if (blockStackElementPeek instanceof LoopBlockStackElement) {
            LoopBlockStackElement loopBlockStackElement = (LoopBlockStackElement) blockStackElementPeek;
            KtSimpleNameExpression targetLabel = ktExpressionWithLabel.getTargetLabel();
            if (targetLabel == null || (loopBlockStackElement.targetLabel != null && targetLabel.getReferencedName().equals(loopBlockStackElement.targetLabel.getReferencedName()))) {
                final Label label2 = z ? loopBlockStackElement.breakLabel : loopBlockStackElement.continueLabel;
                StackValue stackValueOperation = StackValue.operation(Type.VOID_TYPE, getNothingType(), new Function1() { // from class: org.jetbrains.kotlin.codegen.ExpressionCodegen$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return this.f$0.m6393xb38bf17a(label2, label, (InstructionAdapter) obj);
                    }
                });
                if (stackValueOperation == null) {
                    $$$reportNull$$$0(51);
                }
                return stackValueOperation;
            }
        } else {
            throw new UnsupportedOperationException("Wrong BlockStackElement in processing stack");
        }
        this.blockStackElements.pop();
        StackValue stackValueGenerateBreakOrContinueExpression = generateBreakOrContinueExpression(ktExpressionWithLabel, z, label, list);
        this.blockStackElements.push(blockStackElementPeek);
        if (stackValueGenerateBreakOrContinueExpression == null) {
            $$$reportNull$$$0(52);
        }
        return stackValueGenerateBreakOrContinueExpression;
    }

    /* JADX INFO: renamed from: lambda$generateBreakOrContinueExpression$4$org-jetbrains-kotlin-codegen-ExpressionCodegen, reason: not valid java name */
    /* synthetic */ Unit m6393xb38bf17a(Label label, Label label2, InstructionAdapter instructionAdapter) {
        PseudoInsnsKt.fixStackAndJump(this.v, label);
        this.v.mark(label2);
        return Unit.INSTANCE;
    }

    private StackValue generateSingleBranchIf(final StackValue stackValue, final KtIfExpression ktIfExpression, final KtExpression ktExpression, final boolean z, final boolean z2) {
        final Type typeExpressionType = z2 ? Type.VOID_TYPE : expressionType(ktIfExpression);
        final KotlinType kotlinType = z2 ? null : kotlinType(ktIfExpression);
        return StackValue.operation(typeExpressionType, new Function1() { // from class: org.jetbrains.kotlin.codegen.ExpressionCodegen$$ExternalSyntheticLambda41
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return this.f$0.m6400x91910f6f(stackValue, z, z2, ktExpression, typeExpressionType, kotlinType, ktIfExpression, (InstructionAdapter) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$generateSingleBranchIf$5$org-jetbrains-kotlin-codegen-ExpressionCodegen, reason: not valid java name */
    /* synthetic */ Unit m6400x91910f6f(StackValue stackValue, boolean z, boolean z2, KtExpression ktExpression, Type type, KotlinType kotlinType, KtIfExpression ktIfExpression, InstructionAdapter instructionAdapter) {
        Label label = new Label();
        BranchedValue.INSTANCE.condJump(stackValue, label, z, instructionAdapter);
        if (z2) {
            gen(ktExpression, Type.VOID_TYPE);
            instructionAdapter.mark(label);
            return null;
        }
        gen(ktExpression, type, kotlinType);
        Label label2 = new Label();
        instructionAdapter.goTo(label2);
        instructionAdapter.mark(label);
        StackValue.putUnitInstance(instructionAdapter);
        markStartLineNumber(ktIfExpression);
        instructionAdapter.mark(label2);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public StackValue visitConstantExpression(KtConstantExpression ktConstantExpression, StackValue stackValue) {
        if (ktConstantExpression == null) {
            $$$reportNull$$$0(53);
        }
        return StackValue.constant(getPrimitiveOrStringCompileTimeConstant(ktConstantExpression).getValue(), expressionType(ktConstantExpression), kotlinType(ktConstantExpression));
    }

    public ConstantValue<?> getCompileTimeConstant(KtExpression ktExpression) {
        if (ktExpression == null) {
            $$$reportNull$$$0(54);
        }
        return getCompileTimeConstant(ktExpression, this.bindingContext, this.state.getConfig().getShouldInlineConstVals());
    }

    public ConstantValue<?> getPrimitiveOrStringCompileTimeConstant(KtExpression ktExpression) {
        if (ktExpression == null) {
            $$$reportNull$$$0(55);
        }
        return getPrimitiveOrStringCompileTimeConstant(ktExpression, this.bindingContext, this.state.getConfig().getShouldInlineConstVals());
    }

    public static ConstantValue<?> getPrimitiveOrStringCompileTimeConstant(KtExpression ktExpression, BindingContext bindingContext, boolean z) {
        if (ktExpression == null) {
            $$$reportNull$$$0(56);
        }
        if (bindingContext == null) {
            $$$reportNull$$$0(57);
        }
        ConstantValue<?> compileTimeConstant = getCompileTimeConstant(ktExpression, bindingContext, false, z);
        if (compileTimeConstant == null || ConstantExpressionEvaluatorKt.isStandaloneOnlyConstant(compileTimeConstant)) {
            return null;
        }
        return compileTimeConstant;
    }

    public static ConstantValue<?> getCompileTimeConstant(KtExpression ktExpression, BindingContext bindingContext, boolean z) {
        if (ktExpression == null) {
            $$$reportNull$$$0(58);
        }
        if (bindingContext == null) {
            $$$reportNull$$$0(59);
        }
        return getCompileTimeConstant(ktExpression, bindingContext, false, z);
    }

    public static ConstantValue<?> getCompileTimeConstant(KtExpression ktExpression, BindingContext bindingContext, boolean z, boolean z2) {
        if (ktExpression == null) {
            $$$reportNull$$$0(60);
        }
        if (bindingContext == null) {
            $$$reportNull$$$0(61);
        }
        return JvmConstantsKt.getCompileTimeConstant(ktExpression, bindingContext, z, z2);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public StackValue visitStringTemplateExpression(KtStringTemplateExpression ktStringTemplateExpression, StackValue stackValue) {
        if (ktStringTemplateExpression == null) {
            $$$reportNull$$$0(62);
        }
        final List<StringTemplateEntry> listPreprocessStringTemplate = preprocessStringTemplate(ktStringTemplateExpression);
        Type typeExpressionType = expressionType(ktStringTemplateExpression);
        if (listPreprocessStringTemplate.size() == 0) {
            return StackValue.constant("", typeExpressionType);
        }
        if (listPreprocessStringTemplate.size() == 1) {
            StringTemplateEntry stringTemplateEntry = listPreprocessStringTemplate.get(0);
            if (stringTemplateEntry instanceof StringTemplateEntry.Expression) {
                KtExpression ktExpression = ((StringTemplateEntry.Expression) stringTemplateEntry).expression;
                return DescriptorAsmUtil.genToString(gen(ktExpression), expressionType(ktExpression), kotlinType(ktExpression), this.typeMapper);
            }
            return StackValue.constant(((StringTemplateEntry.Constant) stringTemplateEntry).value, typeExpressionType);
        }
        return StackValue.operation(typeExpressionType, new Function1() { // from class: org.jetbrains.kotlin.codegen.ExpressionCodegen$$ExternalSyntheticLambda14
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return this.f$0.m6412xe4e6076(listPreprocessStringTemplate, (InstructionAdapter) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$visitStringTemplateExpression$6$org-jetbrains-kotlin-codegen-ExpressionCodegen, reason: not valid java name */
    /* synthetic */ Unit m6412xe4e6076(List list, InstructionAdapter instructionAdapter) {
        StringConcatGenerator stringConcatGeneratorCreate = StringConcatGenerator.INSTANCE.create(this.state, instructionAdapter);
        stringConcatGeneratorCreate.genStringBuilderConstructorIfNeded();
        invokeAppendForEntries(stringConcatGeneratorCreate, list);
        stringConcatGeneratorCreate.genToString();
        return Unit.INSTANCE;
    }

    private void invokeAppendForEntries(StringConcatGenerator stringConcatGenerator, List<StringTemplateEntry> list) {
        for (StringTemplateEntry stringTemplateEntry : list) {
            if (stringTemplateEntry instanceof StringTemplateEntry.Expression) {
                invokeAppend(stringConcatGenerator, ((StringTemplateEntry.Expression) stringTemplateEntry).expression);
            } else {
                String str = ((StringTemplateEntry.Constant) stringTemplateEntry).value;
                if (str.length() == 1) {
                    stringConcatGenerator.putValueOrProcessConstant(StackValue.constant(Character.valueOf(str.charAt(0)), Type.CHAR_TYPE, null));
                } else {
                    stringConcatGenerator.addStringConstant(str);
                }
            }
        }
    }

    private static abstract class StringTemplateEntry {
        private StringTemplateEntry() {
        }

        static class Constant extends StringTemplateEntry {
            final String value;

            Constant(String str) {
                super();
                this.value = str;
            }
        }

        static class Expression extends StringTemplateEntry {
            final KtExpression expression;

            Expression(KtExpression ktExpression) {
                super();
                this.expression = ktExpression;
            }
        }
    }

    private List<StringTemplateEntry> preprocessStringTemplate(KtStringTemplateExpression ktStringTemplateExpression) {
        if (ktStringTemplateExpression == null) {
            $$$reportNull$$$0(63);
        }
        KtStringTemplateEntry[] entries = ktStringTemplateExpression.getEntries();
        ArrayList arrayList = new ArrayList(entries.length);
        StringBuilder sb = new StringBuilder("");
        for (KtStringTemplateEntry ktStringTemplateEntry : entries) {
            if (ktStringTemplateEntry instanceof KtLiteralStringTemplateEntry) {
                sb.append(ktStringTemplateEntry.getText());
            } else if (ktStringTemplateEntry instanceof KtEscapeStringTemplateEntry) {
                sb.append(((KtEscapeStringTemplateEntry) ktStringTemplateEntry).getUnescapedValue());
            } else if (ktStringTemplateEntry instanceof KtStringTemplateEntryWithExpression) {
                KtExpression expression = ktStringTemplateEntry.getExpression();
                if (expression == null) {
                    throw new AssertionError("No expression in " + ktStringTemplateEntry);
                }
                ConstantValue<?> primitiveOrStringCompileTimeConstant = getPrimitiveOrStringCompileTimeConstant(expression);
                if (primitiveOrStringCompileTimeConstant != null && isConstantValueInlinableInStringTemplate(primitiveOrStringCompileTimeConstant)) {
                    sb.append(String.valueOf(primitiveOrStringCompileTimeConstant.getValue()));
                } else {
                    String string = sb.toString();
                    if (string.length() > 0) {
                        arrayList.add(new StringTemplateEntry.Constant(string));
                    }
                    sb.setLength(0);
                    arrayList.add(new StringTemplateEntry.Expression(expression));
                }
            } else {
                throw new AssertionError("Unexpected string template entry: " + ktStringTemplateEntry);
            }
        }
        String string2 = sb.toString();
        if (string2.length() > 0) {
            arrayList.add(new StringTemplateEntry.Constant(string2));
        }
        return arrayList;
    }

    private static boolean isConstantValueInlinableInStringTemplate(ConstantValue<?> constantValue) {
        if (constantValue == null) {
            $$$reportNull$$$0(65);
        }
        return (constantValue instanceof StringValue) || (constantValue instanceof BooleanValue) || (constantValue instanceof DoubleValue) || (constantValue instanceof FloatValue) || (constantValue instanceof IntegerValueConstant) || (constantValue instanceof NullValue);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public StackValue visitBlockExpression(KtBlockExpression ktBlockExpression, StackValue stackValue) {
        if (ktBlockExpression == null) {
            $$$reportNull$$$0(66);
        }
        return generateBlock(ktBlockExpression, false);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public StackValue visitNamedFunction(KtNamedFunction ktNamedFunction, StackValue stackValue) {
        if (ktNamedFunction == null) {
            $$$reportNull$$$0(67);
        }
        return visitNamedFunction(ktNamedFunction, stackValue, false);
    }

    public StackValue visitNamedFunction(KtNamedFunction ktNamedFunction, StackValue stackValue, boolean z) {
        if (ktNamedFunction == null) {
            $$$reportNull$$$0(68);
        }
        if (KtPsiUtil.isScriptDeclaration(ktNamedFunction)) {
            return StackValue.none();
        }
        StackValue stackValueGenClosure = genClosure(ktNamedFunction, null);
        if (!z) {
            return stackValueGenClosure;
        }
        int iLookupLocalIndex = lookupLocalIndex((DeclarationDescriptor) this.bindingContext.get(BindingContext.DECLARATION_TO_DESCRIPTOR, ktNamedFunction));
        stackValueGenClosure.put(AsmTypes.OBJECT_TYPE, null, this.v);
        this.v.store(iLookupLocalIndex, AsmTypes.OBJECT_TYPE);
        return StackValue.none();
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public StackValue visitLambdaExpression(KtLambdaExpression ktLambdaExpression, StackValue stackValue) {
        if (ktLambdaExpression == null) {
            $$$reportNull$$$0(69);
        }
        if (Boolean.TRUE.equals(this.bindingContext.get(BindingContext.BLOCK, ktLambdaExpression))) {
            return gen(ktLambdaExpression.getFunctionLiteral().getBodyExpression());
        }
        return genClosure(ktLambdaExpression.getFunctionLiteral(), null);
    }

    private StackValue genClosure(KtDeclarationWithBody ktDeclarationWithBody, SamType samType) {
        return genClosure(ktDeclarationWithBody, (FunctionDescriptor) this.bindingContext.get(BindingContext.FUNCTION, ktDeclarationWithBody), new ClosureGenerationStrategy(this.state, ktDeclarationWithBody), samType, null, null);
    }

    private StackValue genClosure(KtElement ktElement, FunctionDescriptor functionDescriptor, FunctionGenerationStrategy functionGenerationStrategy, SamType samType, ResolvedCall<FunctionDescriptor> resolvedCall, StackValue stackValue) {
        ClosureContext closureContextIntoClosure;
        if (ktElement == null) {
            $$$reportNull$$$0(70);
        }
        if (functionDescriptor == null) {
            $$$reportNull$$$0(71);
        }
        if (functionGenerationStrategy == null) {
            $$$reportNull$$$0(72);
        }
        ClassBuilder classBuilderNewVisitor = this.state.getFactory().newVisitor(JvmDeclarationOriginKt.OtherOrigin(ktElement, functionDescriptor), CodegenBinding.asmTypeForAnonymousClass(this.bindingContext, functionDescriptor), ktElement.getContainingFile());
        ClosureCodegen closureCodegenCreate = CoroutineCodegenForLambda.create(this, functionDescriptor, ktElement, classBuilderNewVisitor);
        if (functionDescriptor.isSuspend()) {
            closureContextIntoClosure = this.context.intoCoroutineClosure(CoroutineCodegenUtilKt.getOrCreateJvmSuspendFunctionView(functionDescriptor, this.state), functionDescriptor, this, this.state.getTypeMapper());
        } else {
            closureContextIntoClosure = this.context.intoClosure(functionDescriptor, this, this.typeMapper);
        }
        ClosureContext closureContext = closureContextIntoClosure;
        if (closureCodegenCreate == null) {
            closureCodegenCreate = new ClosureCodegen(this.state, ktElement, samType, closureContext, resolvedCall, functionGenerationStrategy, this.parentCodegen, classBuilderNewVisitor);
        }
        closureCodegenCreate.generate();
        return putClosureInstanceOnStack(closureCodegenCreate, stackValue);
    }

    private StackValue putClosureInstanceOnStack(ClosureCodegen closureCodegen, StackValue stackValue) {
        if (closureCodegen == null) {
            $$$reportNull$$$0(73);
        }
        if (closureCodegen.getReifiedTypeParametersUsages().wereUsedReifiedParameters()) {
            ReifiedTypeInliner.putNeedClassReificationMarker(this.v);
            propagateChildReifiedTypeParametersUsages(closureCodegen.getReifiedTypeParametersUsages());
        }
        StackValue stackValuePutInstanceOnStack = closureCodegen.putInstanceOnStack(this, stackValue);
        if (stackValuePutInstanceOnStack == null) {
            $$$reportNull$$$0(74);
        }
        return stackValuePutInstanceOnStack;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public StackValue visitObjectLiteralExpression(KtObjectLiteralExpression ktObjectLiteralExpression, StackValue stackValue) {
        if (ktObjectLiteralExpression == null) {
            $$$reportNull$$$0(75);
        }
        final ObjectLiteralResult objectLiteralResultGenerateObjectLiteral = generateObjectLiteral(ktObjectLiteralExpression);
        final ClassDescriptor classDescriptor = objectLiteralResultGenerateObjectLiteral.classDescriptor;
        final Type typeMapType = this.typeMapper.mapType(classDescriptor);
        return StackValue.operation(typeMapType, new Function1() { // from class: org.jetbrains.kotlin.codegen.ExpressionCodegen$$ExternalSyntheticLambda24
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return this.f$0.m6408x1ddbc14c(objectLiteralResultGenerateObjectLiteral, typeMapType, classDescriptor, (InstructionAdapter) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$visitObjectLiteralExpression$7$org-jetbrains-kotlin-codegen-ExpressionCodegen, reason: not valid java name */
    /* synthetic */ Unit m6408x1ddbc14c(ObjectLiteralResult objectLiteralResult, Type type, ClassDescriptor classDescriptor, InstructionAdapter instructionAdapter) {
        if (objectLiteralResult.wereReifiedMarkers) {
            ReifiedTypeInliner.putNeedClassReificationMarker(instructionAdapter);
        }
        instructionAdapter.anew(type);
        instructionAdapter.dup();
        pushClosureOnStack(classDescriptor, true, this.defaultCallGenerator, null);
        ResolvedCall<ConstructorDescriptor> delegationConstructorCall = BindingContextUtils.getDelegationConstructorCall(this.bindingContext, classDescriptor.mo6347getUnsubstitutedPrimaryConstructor());
        if (delegationConstructorCall != null) {
            ConstructorDescriptor constructorDescriptor = (ConstructorDescriptor) delegationConstructorCall.getResultingDescriptor();
            ConstructorDescriptor constructorDescriptor2 = (ConstructorDescriptor) SamCodegenUtil.resolveSamAdapter(constructorDescriptor);
            int size = constructorDescriptor.getValueParameters().size();
            List<Type> valueParameterTypes = this.typeMapper.mapToCallableMethod(constructorDescriptor2, false).getValueParameterTypes();
            ArrayList arrayList = new ArrayList(size);
            ArrayList arrayList2 = new ArrayList(size);
            ArrayList arrayList3 = new ArrayList(size);
            for (ValueParameterDescriptor valueParameterDescriptor : delegationConstructorCall.getValueArguments().keySet()) {
                ResolvedValueArgument resolvedValueArgument = delegationConstructorCall.getValueArguments().get(valueParameterDescriptor);
                if (!(resolvedValueArgument instanceof DefaultValueArgument)) {
                    arrayList.add(resolvedValueArgument);
                    arrayList2.add(valueParameterDescriptor);
                    arrayList3.add(valueParameterTypes.get(valueParameterDescriptor.getIndex()));
                }
            }
            new CallBasedArgumentGenerator(this, this.defaultCallGenerator, arrayList2, arrayList3).generate(arrayList, arrayList, null);
        }
        instructionAdapter.invokespecial(type.getInternalName(), "<init>", this.typeMapper.mapAsmMethod(SamCodegenUtil.resolveSamAdapter((ConstructorDescriptor) CollectionsKt.single(classDescriptor.getConstructors()))).getDescriptor(), false);
        return Unit.INSTANCE;
    }

    public void pushClosureOnStack(ClassDescriptor classDescriptor, boolean z, CallGenerator callGenerator, StackValue stackValue) {
        int i;
        CallableDescriptor enclosingCallableDescriptorWithReceiver;
        ClassDescriptor capturedOuterClassDescriptor;
        if (classDescriptor == null) {
            $$$reportNull$$$0(76);
        }
        if (callGenerator == null) {
            $$$reportNull$$$0(77);
        }
        CalculatedClosure calculatedClosure = (CalculatedClosure) this.bindingContext.get(CodegenBinding.CLOSURE, classDescriptor);
        if (calculatedClosure == null) {
            return;
        }
        boolean z2 = false;
        if (!z || (capturedOuterClassDescriptor = calculatedClosure.getCapturedOuterClassDescriptor()) == null) {
            i = 0;
        } else {
            StackValue stackValueGenerateThisOrOuter = generateThisOrOuter(capturedOuterClassDescriptor, false);
            callGenerator.putCapturedValueOnStack(stackValueGenerateThisOrOuter, stackValueGenerateThisOrOuter.type, 0);
            i = 1;
        }
        if (calculatedClosure.getCapturedReceiverFromOuterContext() != null) {
            if (stackValue == null) {
                CallableDescriptor callableDescriptorUnwrapOriginalReceiverOwnerForSuspendLambda = unwrapOriginalReceiverOwnerForSuspendLambda(this.context);
                if (callableDescriptorUnwrapOriginalReceiverOwnerForSuspendLambda.getExtensionReceiverParameter() != null) {
                    stackValue = generateExtensionReceiver(callableDescriptorUnwrapOriginalReceiverOwnerForSuspendLambda);
                }
            }
            if (stackValue == null && (enclosingCallableDescriptorWithReceiver = calculatedClosure.getEnclosingCallableDescriptorWithReceiver()) != null) {
                stackValue = generateExtensionReceiver(enclosingCallableDescriptorWithReceiver);
            }
            callGenerator.putCapturedValueOnStack(stackValue, stackValue.type, i);
            i++;
        }
        for (Map.Entry<DeclarationDescriptor, EnclosedValueDescriptor> entry : calculatedClosure.getCaptureVariables().entrySet()) {
            DeclarationDescriptor key = entry.getKey();
            EnclosedValueDescriptor value = entry.getValue();
            Type sharedVarType = this.typeMapper.getSharedVarType(key);
            boolean z3 = sharedVarType != null;
            if (sharedVarType == null) {
                sharedVarType = this.typeMapper.mapType((VariableDescriptor) key);
            }
            callGenerator.putCapturedValueOnStack(lookupOuterValue(value, z3), sharedVarType, i);
            i++;
        }
        ClassDescriptor superClassNotAny = DescriptorUtilsKt.getSuperClassNotAny(classDescriptor);
        if (superClassNotAny != null) {
            if (z && calculatedClosure.getCapturedOuterClassDescriptor() == null) {
                z2 = true;
            }
            pushClosureOnStack(superClassNotAny, z2, callGenerator, null);
        }
        if (calculatedClosure.isSuspend()) {
            if (calculatedClosure.isSuspendLambda()) {
                if ((callGenerator instanceof PsiInlineCodegen) && ((PsiExpressionLambda) Objects.requireNonNull(((PsiInlineCodegen) callGenerator).getActiveLambda(), "no active lambda found")).getIsCrossInline()) {
                    return;
                }
                this.v.aconst(null);
                return;
            }
            if ((classDescriptor instanceof SyntheticClassDescriptorForLambda) && ((SyntheticClassDescriptorForLambda) classDescriptor).isCallableReference()) {
                return;
            }
            StackValue stackValueFindLocalOrCapturedValue = findLocalOrCapturedValue((ValueParameterDescriptor) CollectionsKt.last((List) this.context.getFunctionDescriptor().getValueParameters()));
            callGenerator.putCapturedValueOnStack(stackValueFindLocalOrCapturedValue, stackValueFindLocalOrCapturedValue.type, i);
        }
    }

    private static CallableDescriptor unwrapOriginalReceiverOwnerForSuspendLambda(MethodContext methodContext) {
        if (methodContext == null) {
            $$$reportNull$$$0(78);
        }
        FunctionDescriptor functionDescriptor = (FunctionDescriptor) methodContext.getFunctionDescriptor().getUserData(CoroutineCodegenUtilKt.INITIAL_SUSPEND_DESCRIPTOR_FOR_INVOKE_SUSPEND);
        if (functionDescriptor != null) {
            if (functionDescriptor == null) {
                $$$reportNull$$$0(79);
            }
            return functionDescriptor;
        }
        if (methodContext.getFunctionDescriptor().isSuspend()) {
            CallableDescriptor callableDescriptorUnwrapInitialDescriptorForSuspendFunction = CoroutineCodegenUtilKt.unwrapInitialDescriptorForSuspendFunction(methodContext.getFunctionDescriptor());
            if (callableDescriptorUnwrapInitialDescriptorForSuspendFunction == null) {
                $$$reportNull$$$0(80);
            }
            return callableDescriptorUnwrapInitialDescriptorForSuspendFunction;
        }
        FunctionDescriptor functionDescriptor2 = methodContext.getFunctionDescriptor();
        if (functionDescriptor2 == null) {
            $$$reportNull$$$0(81);
        }
        return functionDescriptor2;
    }

    StackValue generateBlock(KtBlockExpression ktBlockExpression, boolean z) {
        if (ktBlockExpression == null) {
            $$$reportNull$$$0(82);
        }
        if (ktBlockExpression.getMyParent() instanceof KtNamedFunction) {
            return generateBlock(ktBlockExpression.getStatements(), z, null, this.context.getMethodEndLabel());
        }
        return generateBlock(ktBlockExpression.getStatements(), z, null, null);
    }

    private StackValue lookupOuterValue(EnclosedValueDescriptor enclosedValueDescriptor, boolean z) {
        DeclarationDescriptor descriptor = enclosedValueDescriptor.getDescriptor();
        for (LocalLookup.LocalLookupCase localLookupCase : LocalLookup.LocalLookupCase.values()) {
            if (localLookupCase.isCase(descriptor)) {
                StackValue stackValueOuterValue = localLookupCase.outerValue(enclosedValueDescriptor, this);
                if (!z || !(stackValueOuterValue instanceof StackValue.FieldForSharedVar)) {
                    if (stackValueOuterValue == null) {
                        $$$reportNull$$$0(84);
                    }
                    return stackValueOuterValue;
                }
                StackValue stackValue = ((StackValue.FieldForSharedVar) stackValueOuterValue).receiver;
                if (stackValue == null) {
                    $$$reportNull$$$0(83);
                }
                return stackValue;
            }
        }
        throw new IllegalStateException("Can't get outer value in " + this + " for " + enclosedValueDescriptor);
    }

    private StackValueWithLeaveTask generateBlock(List<KtExpression> list, boolean z, Label label, final Label label2) {
        if (list == null) {
            $$$reportNull$$$0(85);
        }
        final Label label3 = label2 != null ? label2 : new Label();
        final List<Function<StackValue, Void>> listNewArrayList = Lists.newArrayList();
        Iterator<KtExpression> it = list.iterator();
        StackValue stackValueNone = null;
        while (it.hasNext()) {
            KtExpression next = it.next();
            KtElement ktElementSafeDeparenthesize = KtPsiUtil.safeDeparenthesize(next);
            if (!(ktElementSafeDeparenthesize instanceof KtNamedDeclaration) || !KtPsiUtil.isScriptDeclaration((KtNamedDeclaration) ktElementSafeDeparenthesize)) {
                putDescriptorIntoFrameMap(ktElementSafeDeparenthesize);
                boolean z2 = (it.hasNext() || z) ? false : true;
                if (z2 && label != null) {
                    this.v.mark(label);
                }
                StackValue stackValueGen = z2 ? gen(next) : genStatement(next);
                if (it.hasNext()) {
                    stackValueGen.put(Type.VOID_TYPE, null, this.v);
                } else {
                    stackValueNone = stackValueGen;
                }
                addLeaveTaskToRemoveDescriptorFromFrameMap(ktElementSafeDeparenthesize, label3, listNewArrayList);
            }
        }
        if (list.isEmpty()) {
            stackValueNone = StackValue.none();
        }
        return new StackValueWithLeaveTask(stackValueNone, new Function1() { // from class: org.jetbrains.kotlin.codegen.ExpressionCodegen$$ExternalSyntheticLambda34
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return this.f$0.m6392x777d406a(label2, label3, listNewArrayList, (StackValue) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$generateBlock$8$org-jetbrains-kotlin-codegen-ExpressionCodegen, reason: not valid java name */
    /* synthetic */ Unit m6392x777d406a(Label label, Label label2, List list, StackValue stackValue) {
        if (label == null) {
            this.v.mark(label2);
        }
        Iterator it = Lists.reverse(list).iterator();
        while (it.hasNext()) {
            ((Function) it.next()).fun(stackValue);
        }
        return Unit.INSTANCE;
    }

    private StackValue getCoroutineInstanceValueForSuspensionPoint(ResolvedCall<?> resolvedCall) {
        if (resolvedCall == null) {
            $$$reportNull$$$0(86);
        }
        FunctionDescriptor functionDescriptor = (FunctionDescriptor) this.bindingContext.get(BindingContext.ENCLOSING_SUSPEND_FUNCTION_FOR_SUSPEND_FUNCTION_CALL, resolvedCall.getCall());
        if (functionDescriptor == null) {
            return null;
        }
        return genCoroutineInstanceForSuspendLambda(functionDescriptor);
    }

    public StackValue genCoroutineInstanceForSuspendLambda(FunctionDescriptor functionDescriptor) {
        if (functionDescriptor == null) {
            $$$reportNull$$$0(87);
        }
        if (CoroutineCodegenUtilKt.isSuspendLambdaOrLocalFunction(functionDescriptor)) {
            return StackValue.thisOrOuter(this, (ClassDescriptor) this.bindingContext.get(CodegenBinding.CLASS_FOR_CALLABLE, functionDescriptor), false, false);
        }
        return null;
    }

    private Type getVariableType(VariableDescriptor variableDescriptor) {
        if (variableDescriptor == null) {
            $$$reportNull$$$0(88);
        }
        Type sharedVarType = this.typeMapper.getSharedVarType(variableDescriptor);
        return sharedVarType != null ? sharedVarType : getVariableTypeNoSharing(variableDescriptor);
    }

    private Type getVariableTypeNoSharing(VariableDescriptor variableDescriptor) {
        KotlinType type;
        if (variableDescriptor == null) {
            $$$reportNull$$$0(89);
        }
        if (JvmCodegenUtil.isDelegatedLocalVariable(variableDescriptor)) {
            type = JvmCodegenUtil.getPropertyDelegateType((VariableDescriptorWithAccessors) variableDescriptor, this.bindingContext);
        } else {
            type = variableDescriptor.getType();
        }
        if ((variableDescriptor instanceof ValueParameterDescriptor) && MethodSignatureMappingKt.forceSingleValueParameterBoxing((CallableDescriptor) variableDescriptor.getContainingDeclaration())) {
            Type typeAsmType = asmType(TypeUtils.makeNullable(type));
            if (typeAsmType == null) {
                $$$reportNull$$$0(90);
            }
            return typeAsmType;
        }
        Type typeAsmType2 = asmType(type);
        if (typeAsmType2 == null) {
            $$$reportNull$$$0(91);
        }
        return typeAsmType2;
    }

    private void putDescriptorIntoFrameMap(KtElement ktElement) {
        if (ktElement == null) {
            $$$reportNull$$$0(92);
        }
        if (ktElement instanceof KtDestructuringDeclaration) {
            Iterator<KtDestructuringDeclarationEntry> it = ((KtDestructuringDeclaration) ktElement).getEntries().iterator();
            while (it.hasNext()) {
                putLocalVariableIntoFrameMap(it.next());
            }
        }
        if (ktElement instanceof KtVariableDeclaration) {
            putLocalVariableIntoFrameMap((KtVariableDeclaration) ktElement);
        }
        if (ktElement instanceof KtNamedFunction) {
            DeclarationDescriptor declarationDescriptor = (DeclarationDescriptor) this.bindingContext.get(BindingContext.DECLARATION_TO_DESCRIPTOR, ktElement);
            this.myFrameMap.enter(declarationDescriptor, CodegenBinding.asmTypeForAnonymousClass(this.bindingContext, (FunctionDescriptor) declarationDescriptor));
        }
    }

    private void putLocalVariableIntoFrameMap(KtVariableDeclaration ktVariableDeclaration) {
        if (ktVariableDeclaration == null) {
            $$$reportNull$$$0(93);
        }
        VariableDescriptor variableDescriptorNotNull = getVariableDescriptorNotNull(ktVariableDeclaration);
        if (variableDescriptorNotNull.getShortName().isSpecial()) {
            return;
        }
        Type variableType = getVariableType(variableDescriptorNotNull);
        int iEnter = this.myFrameMap.enter(variableDescriptorNotNull, variableType);
        if (JvmCodegenUtil.isDelegatedLocalVariable(variableDescriptorNotNull)) {
            this.myFrameMap.enter(CodegenBinding.getDelegatedLocalVariableMetadata(variableDescriptorNotNull, this.bindingContext), AsmTypes.K_PROPERTY0_TYPE);
        }
        if (AsmTypes.isSharedVarType(variableType)) {
            markLineNumber(ktVariableDeclaration, false);
            this.v.anew(variableType);
            this.v.dup();
            this.v.invokespecial(variableType.getInternalName(), "<init>", "()V", false);
            this.v.store(iEnter, AsmTypes.OBJECT_TYPE);
        }
    }

    private void addLeaveTaskToRemoveDescriptorFromFrameMap(KtElement ktElement, Label label, List<Function<StackValue, Void>> list) {
        if (ktElement == null) {
            $$$reportNull$$$0(94);
        }
        if (label == null) {
            $$$reportNull$$$0(95);
        }
        if (list == null) {
            $$$reportNull$$$0(96);
        }
        if (ktElement instanceof KtDestructuringDeclaration) {
            Iterator<KtDestructuringDeclarationEntry> it = ((KtDestructuringDeclaration) ktElement).getEntries().iterator();
            while (it.hasNext()) {
                addLeaveTaskToRemoveLocalVariableFromFrameMap(it.next(), label, list);
            }
        }
        if (ktElement instanceof KtVariableDeclaration) {
            addLeaveTaskToRemoveLocalVariableFromFrameMap((KtVariableDeclaration) ktElement, label, list);
        }
        if (ktElement instanceof KtNamedFunction) {
            addLeaveTaskToRemoveNamedFunctionFromFrameMap((KtNamedFunction) ktElement, label, list);
        }
    }

    private void addLeaveTaskToRemoveLocalVariableFromFrameMap(KtVariableDeclaration ktVariableDeclaration, final Label label, List<Function<StackValue, Void>> list) {
        if (ktVariableDeclaration == null) {
            $$$reportNull$$$0(97);
        }
        if (list == null) {
            $$$reportNull$$$0(98);
        }
        final VariableDescriptor variableDescriptorNotNull = getVariableDescriptorNotNull(ktVariableDeclaration);
        if (variableDescriptorNotNull.getShortName().isSpecial()) {
            return;
        }
        final Type variableType = getVariableType(variableDescriptorNotNull);
        final Label label2 = new Label();
        this.v.mark(label2);
        list.add(new Function() { // from class: org.jetbrains.kotlin.codegen.ExpressionCodegen$$ExternalSyntheticLambda28
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return this.f$0.m6381xd9c5411b(variableDescriptorNotNull, variableType, label2, label, (StackValue) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$addLeaveTaskToRemoveLocalVariableFromFrameMap$9$org-jetbrains-kotlin-codegen-ExpressionCodegen, reason: not valid java name */
    /* synthetic */ Void m6381xd9c5411b(VariableDescriptor variableDescriptor, Type type, Label label, Label label2, StackValue stackValue) {
        if (JvmCodegenUtil.isDelegatedLocalVariable(variableDescriptor)) {
            this.myFrameMap.leave(CodegenBinding.getDelegatedLocalVariableMetadata(variableDescriptor, this.bindingContext));
        }
        this.v.visitLocalVariable(variableDescriptor.getShortName().asString(), type.getDescriptor(), null, label, label2, this.myFrameMap.leave(variableDescriptor));
        return null;
    }

    private void addLeaveTaskToRemoveNamedFunctionFromFrameMap(KtNamedFunction ktNamedFunction, final Label label, List<Function<StackValue, Void>> list) {
        if (ktNamedFunction == null) {
            $$$reportNull$$$0(99);
        }
        if (list == null) {
            $$$reportNull$$$0(100);
        }
        final FunctionDescriptor functionDescriptor = (FunctionDescriptor) this.bindingContext.get(BindingContext.DECLARATION_TO_DESCRIPTOR, ktNamedFunction);
        final Type typeAsmTypeForAnonymousClass = CodegenBinding.asmTypeForAnonymousClass(this.bindingContext, functionDescriptor);
        final Label label2 = new Label();
        this.v.mark(label2);
        list.add(new Function() { // from class: org.jetbrains.kotlin.codegen.ExpressionCodegen$$ExternalSyntheticLambda19
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return this.f$0.m6382xa6006ec9(functionDescriptor, typeAsmTypeForAnonymousClass, label2, label, (StackValue) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$addLeaveTaskToRemoveNamedFunctionFromFrameMap$10$org-jetbrains-kotlin-codegen-ExpressionCodegen, reason: not valid java name */
    /* synthetic */ Void m6382xa6006ec9(FunctionDescriptor functionDescriptor, Type type, Label label, Label label2, StackValue stackValue) {
        this.v.visitLocalVariable(AsmUtil.LOCAL_FUNCTION_VARIABLE_PREFIX + CommonVariableAsmNameManglingUtils.mangleNameIfNeeded(functionDescriptor.getShortName().asString()) + "$" + StringsKt.substringAfterLast(type.getInternalName(), '$', ""), type.getDescriptor(), null, label, label2, this.myFrameMap.leave(functionDescriptor));
        return null;
    }

    public <T> T runWithShouldMarkLineNumbers(boolean z, Supplier<T> supplier) {
        boolean z2 = this.shouldMarkLineNumbers;
        this.shouldMarkLineNumbers = z;
        try {
            return supplier.get();
        } finally {
            this.shouldMarkLineNumbers = z2;
        }
    }

    public void markStartLineNumber(KtElement ktElement) {
        if (ktElement == null) {
            $$$reportNull$$$0(101);
        }
        markLineNumber(ktElement, false);
    }

    public void markLineNumber(PsiElement psiElement, boolean z) {
        Integer lineNumberForElement;
        if (psiElement == null) {
            $$$reportNull$$$0(102);
        }
        if (!this.shouldMarkLineNumbers || (lineNumberForElement = CodegenUtil.getLineNumberForElement(psiElement, z)) == null || lineNumberForElement.intValue() == this.myLastLineNumber) {
            return;
        }
        this.myLastLineNumber = lineNumberForElement.intValue();
        Label label = new Label();
        this.v.visitLabel(label);
        this.v.visitLineNumber(lineNumberForElement.intValue(), label);
    }

    @Override // org.jetbrains.kotlin.codegen.BaseExpressionCodegen
    public void markLineNumberAfterInlineIfNeeded(boolean z) {
        if (!this.shouldMarkLineNumbers || z) {
            if (this.myLastLineNumber > -1) {
                Label label = new Label();
                this.v.visitLabel(label);
                this.v.visitLineNumber(this.myLastLineNumber, label);
                return;
            }
            return;
        }
        resetLastLineNumber();
    }

    private void resetLastLineNumber() {
        this.myLastLineNumber = -1;
    }

    @Override // org.jetbrains.kotlin.codegen.BaseExpressionCodegen
    public int getLastLineNumber() {
        return this.myLastLineNumber;
    }

    private boolean doFinallyOnReturn(Label label, List<TryBlockStackElement> list) {
        if (label == null) {
            $$$reportNull$$$0(103);
        }
        if (list == null) {
            $$$reportNull$$$0(104);
        }
        if (this.blockStackElements.isEmpty()) {
            return false;
        }
        BlockStackElement blockStackElementPeek = this.blockStackElements.peek();
        if (blockStackElementPeek instanceof TryWithFinallyBlockStackElement) {
            genFinallyBlockOrGoto((TryWithFinallyBlockStackElement) blockStackElementPeek, null, label, list);
            list.clear();
        } else if (blockStackElementPeek instanceof TryBlockStackElement) {
            list.add((TryBlockStackElement) blockStackElementPeek);
        } else if (!(blockStackElementPeek instanceof LoopBlockStackElement)) {
            throw new UnsupportedOperationException("Wrong BlockStackElement in processing stack");
        }
        this.blockStackElements.pop();
        try {
            return doFinallyOnReturn(label, list);
        } finally {
            this.blockStackElements.push(blockStackElementPeek);
        }
    }

    public boolean hasFinallyBlocks() {
        Iterator<BlockStackElement> it = this.blockStackElements.iterator();
        while (it.hasNext()) {
            if (it.next() instanceof TryWithFinallyBlockStackElement) {
                return true;
            }
        }
        return false;
    }

    private void genFinallyBlockOrGoto(TryWithFinallyBlockStackElement tryWithFinallyBlockStackElement, Label label, Label label2, List<TryBlockStackElement> list) {
        if (list == null) {
            $$$reportNull$$$0(105);
        }
        if (tryWithFinallyBlockStackElement != null) {
            this.finallyDepth++;
            this.blockStackElements.pop();
            KtTryExpression ktTryExpression = tryWithFinallyBlockStackElement.expression;
            Label labelLinkedLabel = CodegenUtilKt.linkedLabel();
            this.v.mark(labelLinkedLabel);
            tryWithFinallyBlockStackElement.addGapLabel(labelLinkedLabel);
            addGapLabelsForNestedTryCatchWithoutFinally(this.state, list, labelLinkedLabel);
            if (InlineCodegenUtilsKt.isFinallyMarkerRequired(this.context)) {
                InlineCodegenUtilsKt.generateFinallyMarker(this.v, this.finallyDepth, true);
            }
            gen(ktTryExpression.getFinallyBlock().getFinalExpression(), Type.VOID_TYPE);
            if (InlineCodegenUtilsKt.isFinallyMarkerRequired(this.context)) {
                InlineCodegenUtilsKt.generateFinallyMarker(this.v, this.finallyDepth, false);
            }
        }
        if (label != null) {
            if (tryWithFinallyBlockStackElement != null) {
                markLineNumber(tryWithFinallyBlockStackElement.expression, true);
            }
            this.v.goTo(label);
        }
        if (tryWithFinallyBlockStackElement != null) {
            this.finallyDepth--;
            Label label3 = label2 != null ? label2 : new Label();
            if (label2 == null) {
                this.v.mark(label3);
            }
            tryWithFinallyBlockStackElement.addGapLabel(label3);
            addGapLabelsForNestedTryCatchWithoutFinally(this.state, list, label3);
            this.blockStackElements.push(tryWithFinallyBlockStackElement);
        }
    }

    private static void addGapLabelsForNestedTryCatchWithoutFinally(GenerationState generationState, List<TryBlockStackElement> list, Label label) {
        if (generationState == null) {
            $$$reportNull$$$0(106);
        }
        if (list == null) {
            $$$reportNull$$$0(107);
        }
        if (label == null) {
            $$$reportNull$$$0(108);
        }
        if (generationState.getLanguageVersionSettings().supportsFeature(LanguageFeature.ProperFinally)) {
            Iterator<TryBlockStackElement> it = list.iterator();
            while (it.hasNext()) {
                it.next().addGapLabel(label);
            }
        }
    }

    private KotlinType getNothingType() {
        return this.state.getModule().getBuiltIns().getNothingType();
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public StackValue visitReturnExpression(final KtReturnExpression ktReturnExpression, StackValue stackValue) {
        if (ktReturnExpression == null) {
            $$$reportNull$$$0(109);
        }
        return StackValue.operation(Type.VOID_TYPE, getNothingType(), new Function1() { // from class: org.jetbrains.kotlin.codegen.ExpressionCodegen$$ExternalSyntheticLambda23
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return this.f$0.m6411x5d8d2b0b(ktReturnExpression, (InstructionAdapter) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$visitReturnExpression$11$org-jetbrains-kotlin-codegen-ExpressionCodegen, reason: not valid java name */
    /* synthetic */ Unit m6411x5d8d2b0b(KtReturnExpression ktReturnExpression, InstructionAdapter instructionAdapter) {
        KotlinType kotlinTypeOriginalReturnTypeOfSuspendFunctionReturningUnboxedInlineClass;
        Type typeMapType;
        Type type;
        KotlinType kotlinType;
        KtExpression returnedExpression = ktReturnExpression.getReturnedExpression();
        NonLocalReturnInfo nonLocalReturnInfo = getNonLocalReturnInfo(getContext().getContextDescriptor(), ktReturnExpression);
        boolean z = nonLocalReturnInfo != null;
        if (z && this.state.getConfig().getIsInlineDisabled()) {
            this.state.getDiagnostics().report(Errors.NON_LOCAL_RETURN_IN_DISABLED_INLINE.on(ktReturnExpression));
            AsmUtil.genThrow(this.v, "java/lang/UnsupportedOperationException", "Non-local returns are not allowed with inlining disabled");
            return Unit.INSTANCE;
        }
        if (!z) {
            kotlinTypeOriginalReturnTypeOfSuspendFunctionReturningUnboxedInlineClass = CoroutineCodegenUtilKt.originalReturnTypeOfSuspendFunctionReturningUnboxedInlineClass(this.context.getFunctionDescriptor(), this.typeMapper);
            if (kotlinTypeOriginalReturnTypeOfSuspendFunctionReturningUnboxedInlineClass != null) {
                typeMapType = this.typeMapper.mapType(kotlinTypeOriginalReturnTypeOfSuspendFunctionReturningUnboxedInlineClass);
            } else {
                typeMapType = this.returnType;
                kotlinTypeOriginalReturnTypeOfSuspendFunctionReturningUnboxedInlineClass = this.context.getFunctionDescriptor().getReturnType();
            }
        } else {
            FunctionDescriptor functionDescriptor = nonLocalReturnInfo.descriptor instanceof FunctionDescriptor ? (FunctionDescriptor) nonLocalReturnInfo.descriptor : null;
            if (functionDescriptor != null && functionDescriptor.isSuspend()) {
                if (functionDescriptor instanceof AnonymousFunctionDescriptor) {
                    typeMapType = AsmTypes.OBJECT_TYPE;
                    kotlinTypeOriginalReturnTypeOfSuspendFunctionReturningUnboxedInlineClass = this.state.getModule().getBuiltIns().getNullableAnyType();
                } else {
                    kotlinTypeOriginalReturnTypeOfSuspendFunctionReturningUnboxedInlineClass = this.context.getFirstCrossInlineOrNonInlineContext() instanceof MethodContext ? CoroutineCodegenUtilKt.originalReturnTypeOfSuspendFunctionReturningUnboxedInlineClass(CoroutineCodegenUtilKt.getOrCreateJvmSuspendFunctionView(functionDescriptor, this.state), this.typeMapper) : null;
                    if (kotlinTypeOriginalReturnTypeOfSuspendFunctionReturningUnboxedInlineClass != null) {
                        typeMapType = this.typeMapper.mapType(kotlinTypeOriginalReturnTypeOfSuspendFunctionReturningUnboxedInlineClass);
                    } else {
                        JvmKotlinType jvmKotlinType = nonLocalReturnInfo.getJvmKotlinType(this.typeMapper);
                        type = jvmKotlinType.getType();
                        kotlinType = jvmKotlinType.getKotlinType();
                    }
                }
            } else {
                JvmKotlinType jvmKotlinType2 = nonLocalReturnInfo.getJvmKotlinType(this.typeMapper);
                type = jvmKotlinType2.getType();
                kotlinType = jvmKotlinType2.getKotlinType();
            }
            Type type2 = type;
            kotlinTypeOriginalReturnTypeOfSuspendFunctionReturningUnboxedInlineClass = kotlinType;
            typeMapType = type2;
        }
        putStackValue(returnedExpression, typeMapType, kotlinTypeOriginalReturnTypeOfSuspendFunctionReturningUnboxedInlineClass, returnedExpression != null ? gen(returnedExpression) : StackValue.none());
        Label label = new Label();
        generateFinallyBlocksIfNeeded(typeMapType, kotlinTypeOriginalReturnTypeOfSuspendFunctionReturningUnboxedInlineClass, label);
        markLineNumber(ktReturnExpression, false);
        if (!z) {
            this.v.areturn(this.returnType);
        } else {
            InlineCodegenUtilsKt.generateGlobalReturnFlag(this.v, nonLocalReturnInfo.labelName);
            this.v.visitInsn(typeMapType.getOpcode(172));
        }
        this.v.mark(label);
        return Unit.INSTANCE;
    }

    public void generateFinallyBlocksIfNeeded(Type type, KotlinType kotlinType, Label label) {
        if (type == null) {
            $$$reportNull$$$0(110);
        }
        if (label == null) {
            $$$reportNull$$$0(111);
        }
        if (hasFinallyBlocks()) {
            if (!Type.VOID_TYPE.equals(type)) {
                StackValue.Local local = StackValue.local(this.myFrameMap.enterTemp(type), type, kotlinType);
                local.store(StackValue.onStack(type, kotlinType), this.v);
                doFinallyOnReturn(label, new ArrayList());
                local.put(type, null, this.v);
                this.myFrameMap.leaveTemp(type);
                return;
            }
            doFinallyOnReturn(label, new ArrayList());
        }
    }

    private NonLocalReturnInfo getNonLocalReturnInfo(CallableMemberDescriptor callableMemberDescriptor, KtReturnExpression ktReturnExpression) {
        if (callableMemberDescriptor == null) {
            $$$reportNull$$$0(112);
        }
        if (ktReturnExpression == null) {
            $$$reportNull$$$0(113);
        }
        if (ExpressionTypingUtils.isFunctionLiteral(callableMemberDescriptor) || ExpressionTypingUtils.isFunctionExpression(callableMemberDescriptor)) {
            if (ktReturnExpression.getLabelName() == null) {
                if (ExpressionTypingUtils.isFunctionLiteral(callableMemberDescriptor)) {
                    return new NonLocalReturnInfo(BindingContextUtils.getContainingFunctionSkipFunctionLiterals(callableMemberDescriptor, true).getFirst(), InlineCodegenUtilsKt.FIRST_FUN_LABEL);
                }
                return null;
            }
            PsiElement psiElement = (PsiElement) this.bindingContext.get(BindingContext.LABEL_TARGET, ktReturnExpression.getTargetLabel());
            if (psiElement != DescriptorToSourceUtils.getSourceFromDescriptor(this.context.getContextDescriptor())) {
                return new NonLocalReturnInfo((CallableDescriptor) ((DeclarationDescriptor) this.typeMapper.getBindingContext().get(BindingContext.DECLARATION_TO_DESCRIPTOR, psiElement)), ktReturnExpression.getLabelName());
            }
        }
        return null;
    }

    public void returnExpression(KtExpression ktExpression) {
        Type typeMapType;
        if (ktExpression == null) {
            $$$reportNull$$$0(114);
        }
        boolean z = false;
        boolean z2 = (ktExpression instanceof KtBlockExpression) && (ktExpression.getMyParent() instanceof KtNamedFunction);
        FunctionDescriptor originalSuspendLambdaDescriptorFromContext = getOriginalSuspendLambdaDescriptorFromContext(this.context);
        if (originalSuspendLambdaDescriptorFromContext != null && DescriptorBasedTypeSignatureMappingKt.hasVoidReturnType(originalSuspendLambdaDescriptorFromContext)) {
            z = true;
        }
        KotlinType kotlinTypeOriginalReturnTypeOfSuspendFunctionReturningUnboxedInlineClass = CoroutineCodegenUtilKt.originalReturnTypeOfSuspendFunctionReturningUnboxedInlineClass(this.context.getFunctionDescriptor(), this.typeMapper);
        if (z2 || z) {
            typeMapType = Type.VOID_TYPE;
        } else {
            typeMapType = kotlinTypeOriginalReturnTypeOfSuspendFunctionReturningUnboxedInlineClass != null ? this.typeMapper.mapType(kotlinTypeOriginalReturnTypeOfSuspendFunctionReturningUnboxedInlineClass) : this.returnType;
        }
        if (z2 || z) {
            kotlinTypeOriginalReturnTypeOfSuspendFunctionReturningUnboxedInlineClass = null;
        } else if (kotlinTypeOriginalReturnTypeOfSuspendFunctionReturningUnboxedInlineClass == null) {
            kotlinTypeOriginalReturnTypeOfSuspendFunctionReturningUnboxedInlineClass = this.context.getFunctionDescriptor().getReturnType();
        }
        gen(ktExpression, typeMapType, kotlinTypeOriginalReturnTypeOfSuspendFunctionReturningUnboxedInlineClass);
        if (endsWithReturn(ktExpression)) {
            return;
        }
        if (isLambdaVoidBody(ktExpression, typeMapType)) {
            markLineNumber((KtFunctionLiteral) ktExpression.getMyParent(), true);
        } else if (!isLambdaBody(ktExpression)) {
            markLineNumber(ktExpression, true);
        }
        if (typeMapType.getSort() == 0) {
            StackValue.none().put(this.returnType, null, this.v);
        }
        this.v.areturn(this.returnType);
    }

    private static boolean endsWithReturn(KtElement ktElement) {
        if (ktElement == null) {
            $$$reportNull$$$0(115);
        }
        if (ktElement instanceof KtBlockExpression) {
            List<KtExpression> statements = ((KtBlockExpression) ktElement).getStatements();
            return statements.size() > 0 && (statements.get(statements.size() - 1) instanceof KtReturnExpression);
        }
        return ktElement instanceof KtReturnExpression;
    }

    private static boolean isLambdaVoidBody(KtElement ktElement, Type type) {
        if (ktElement == null) {
            $$$reportNull$$$0(116);
        }
        if (type == null) {
            $$$reportNull$$$0(117);
        }
        return isLambdaBody(ktElement) && Type.VOID_TYPE.equals(type);
    }

    private static boolean isLambdaBody(KtElement ktElement) {
        if (ktElement == null) {
            $$$reportNull$$$0(118);
        }
        if (ktElement instanceof KtBlockExpression) {
            return ktElement.getMyParent() instanceof KtFunctionLiteral;
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public StackValue visitSimpleNameExpression(KtSimpleNameExpression ktSimpleNameExpression, StackValue stackValue) {
        DeclarationDescriptor resultingDescriptor;
        if (ktSimpleNameExpression == null) {
            $$$reportNull$$$0(119);
        }
        if (stackValue == null) {
            $$$reportNull$$$0(120);
        }
        ResolvedCall<?> resolvedCall = CallUtilKt.getResolvedCall(ktSimpleNameExpression, this.bindingContext);
        if (resolvedCall == null) {
            resultingDescriptor = (DeclarationDescriptor) this.bindingContext.get(BindingContext.REFERENCE_TARGET, ktSimpleNameExpression);
        } else {
            if (resolvedCall instanceof VariableAsFunctionResolvedCall) {
                resolvedCall = ((VariableAsFunctionResolvedCall) resolvedCall).getVariableCall();
            }
            resultingDescriptor = resolvedCall.getResultingDescriptor();
            StackValue stackValueApplyIntrinsic = applyIntrinsic(resultingDescriptor, KCallableNameProperty.class, resolvedCall, stackValue);
            if (stackValueApplyIntrinsic != null) {
                return stackValueApplyIntrinsic;
            }
            stackValue = StackValue.receiver(resolvedCall, stackValue, this, null);
            if (resultingDescriptor instanceof FakeCallableDescriptorForObject) {
                resultingDescriptor = ((FakeCallableDescriptorForObject) resultingDescriptor).getReferencedDescriptor();
            }
        }
        StackValue stackValue2 = stackValue;
        ResolvedCall<?> resolvedCall2 = resolvedCall;
        DeclarationDescriptor original = resultingDescriptor.getOriginal();
        boolean z = original instanceof SyntheticFieldDescriptor;
        if (z) {
            original = ((SyntheticFieldDescriptor) original).getPropertyDescriptor();
        }
        DeclarationDescriptor declarationDescriptor = original;
        StackValue stackValueApplyIntrinsic2 = applyIntrinsic(declarationDescriptor, IntrinsicPropertyGetter.class, resolvedCall2, stackValue2);
        return stackValueApplyIntrinsic2 != null ? stackValueApplyIntrinsic2 : generateNonIntrinsicSimpleNameExpression(ktSimpleNameExpression, stackValue2, declarationDescriptor, resolvedCall2, z);
    }

    public StackValue generateNonIntrinsicSimpleNameExpression(KtSimpleNameExpression ktSimpleNameExpression, StackValue stackValue, DeclarationDescriptor declarationDescriptor, ResolvedCall<?> resolvedCall, boolean z) {
        StackValue stackValueApplyProperty;
        if (ktSimpleNameExpression == null) {
            $$$reportNull$$$0(121);
        }
        if (stackValue == null) {
            $$$reportNull$$$0(122);
        }
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(123);
        }
        boolean z2 = false;
        if (declarationDescriptor instanceof PropertyDescriptor) {
            PropertyDescriptor propertyDescriptor = (PropertyDescriptor) declarationDescriptor;
            if (InlineClassesUtilsKt.isUnderlyingPropertyOfInlineClass(propertyDescriptor)) {
                KotlinType type = propertyDescriptor.getType();
                return StackValue.underlyingValueOfInlineClass(this.typeMapper.mapType(type), type, stackValue);
            }
            List<ExpressionCodegenExtension> instances = ExpressionCodegenExtension.INSTANCE.getInstances(this.state.getProject());
            if (!instances.isEmpty() && resolvedCall != null) {
                ExpressionCodegenExtension.Context context = new ExpressionCodegenExtension.Context(this, this.typeMapper, this.v);
                KotlinType returnType = propertyDescriptor.getReturnType();
                for (ExpressionCodegenExtension expressionCodegenExtension : instances) {
                    if (returnType != null && (stackValueApplyProperty = expressionCodegenExtension.applyProperty(stackValue, resolvedCall, context)) != null) {
                        return stackValueApplyProperty;
                    }
                }
            }
            if (z && contextKind() != OwnerKind.DEFAULT_IMPLS) {
                z2 = true;
            }
            boolean z3 = z2;
            ClassDescriptor superCallTarget = resolvedCall == null ? null : getSuperCallTarget(resolvedCall.getCall());
            if (z3) {
                stackValue = StackValue.receiverWithoutReceiverArgument(stackValue);
            }
            return intermediateValueForProperty(propertyDescriptor, z3, z3, superCallTarget, false, stackValue, resolvedCall, false);
        }
        if (declarationDescriptor instanceof FakeCallableDescriptorForTypeAliasObject) {
            declarationDescriptor = ((FakeCallableDescriptorForTypeAliasObject) declarationDescriptor).getTypeAliasDescriptor();
        }
        if (declarationDescriptor instanceof TypeAliasDescriptor) {
            declarationDescriptor = ((TypeAliasDescriptor) declarationDescriptor).getClassDescriptor();
        }
        if (declarationDescriptor instanceof ClassDescriptor) {
            ClassDescriptor classDescriptor = (ClassDescriptor) declarationDescriptor;
            if (shouldGenerateSingletonAsThisOrOuterFromContext(classDescriptor)) {
                return generateThisOrOuterFromContext(classDescriptor, false, false);
            }
            if (DescriptorUtils.isCompanionObject(classDescriptor)) {
                return generateCompanionObjectInstance(classDescriptor);
            }
            if (DescriptorUtils.isObject(classDescriptor)) {
                return StackValue.singleton(classDescriptor, this.typeMapper);
            }
            if (DescriptorUtils.isEnumEntry(classDescriptor)) {
                return StackValue.enumEntry(classDescriptor, this.typeMapper);
            }
            ClassDescriptor classDescriptorMo6346getCompanionObjectDescriptor = classDescriptor.mo6346getCompanionObjectDescriptor();
            if (classDescriptorMo6346getCompanionObjectDescriptor != null) {
                return generateCompanionObjectInstance(classDescriptorMo6346getCompanionObjectDescriptor);
            }
            return StackValue.none();
        }
        StackValue stackValueFindLocalOrCapturedValue = findLocalOrCapturedValue(declarationDescriptor);
        if (stackValueFindLocalOrCapturedValue != null) {
            if (!(declarationDescriptor instanceof ValueParameterDescriptor)) {
                return stackValueFindLocalOrCapturedValue;
            }
            KotlinType type2 = ((ValueParameterDescriptor) declarationDescriptor).getType();
            if (!InlineClassesCodegenUtilKt.isInlineClassWithUnderlyingTypeAnyOrAnyN(type2) || !InlineClassesCodegenUtilKt.isGenericParameter((CallableDescriptor) declarationDescriptor)) {
                return stackValueFindLocalOrCapturedValue;
            }
            MethodContext methodContext = this.context;
            if ((methodContext instanceof InlineLambdaContext) || CoroutineCodegenUtilKt.isInvokeSuspendOfLambda(methodContext.getFunctionDescriptor())) {
                return stackValueFindLocalOrCapturedValue;
            }
            KotlinType kotlinType = (KotlinType) DescriptorUtilsKt.getInlineClassRepresentation((ClassDescriptor) type2.getConstructor().getThis$0()).getUnderlyingType();
            return StackValue.underlyingValueOfInlineClass(this.typeMapper.mapType(kotlinType), kotlinType, stackValueFindLocalOrCapturedValue);
        }
        throw new UnsupportedOperationException("don't know how to generate reference " + declarationDescriptor);
    }

    private StackValue generateCompanionObjectInstance(final ClassDescriptor classDescriptor) {
        CodegenContext contextForCompanionObjectAccessorIfRequiredOrNull = getContextForCompanionObjectAccessorIfRequiredOrNull(classDescriptor, this.context);
        if (contextForCompanionObjectAccessorIfRequiredOrNull == null) {
            return StackValue.singleton(classDescriptor, this.typeMapper);
        }
        DeclarationDescriptor contextDescriptor = contextForCompanionObjectAccessorIfRequiredOrNull.getContextDescriptor();
        contextForCompanionObjectAccessorIfRequiredOrNull.getOrCreateAccessorForCompanionObject(classDescriptor);
        final Type typeMapClass = this.typeMapper.mapClass((ClassDescriptor) contextDescriptor);
        final Type typeMapClass2 = this.typeMapper.mapClass(classDescriptor);
        return StackValue.operation(typeMapClass2, classDescriptor.getDefaultType(), new Function1() { // from class: org.jetbrains.kotlin.codegen.ExpressionCodegen$$ExternalSyntheticLambda21
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ExpressionCodegen.lambda$generateCompanionObjectInstance$12(typeMapClass, classDescriptor, typeMapClass2, (InstructionAdapter) obj);
            }
        });
    }

    static /* synthetic */ Unit lambda$generateCompanionObjectInstance$12(Type type, ClassDescriptor classDescriptor, Type type2, InstructionAdapter instructionAdapter) {
        instructionAdapter.invokestatic(type.getInternalName(), JvmCodegenUtil.getCompanionObjectAccessorName(classDescriptor), Type.getMethodDescriptor(type2, new Type[0]), false);
        return null;
    }

    private boolean shouldGenerateSingletonAsThisOrOuterFromContext(ClassDescriptor classDescriptor) {
        if (!CodegenUtilKt.isPossiblyUninitializedSingleton(classDescriptor) || !isInsideSingleton(classDescriptor)) {
            return false;
        }
        for (CodegenContext parentContext = this.context; parentContext != null; parentContext = parentContext.getParentContext()) {
            if (parentContext instanceof ConstructorContext) {
                ConstructorContext constructorContext = (ConstructorContext) parentContext;
                if (constructorContext.getConstructorDescriptor().getConstructedClass() == classDescriptor) {
                    return constructorContext.isThisInitialized();
                }
            } else if ((parentContext instanceof ClassContext) && isInInnerClassesChainFor((ClassDescriptor) ((ClassContext) parentContext).getContextDescriptor(), classDescriptor)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isInInnerClassesChainFor(ClassDescriptor classDescriptor, ClassDescriptor classDescriptor2) {
        if (classDescriptor == classDescriptor2) {
            return true;
        }
        if (!classDescriptor.getIsInner()) {
            return false;
        }
        DeclarationDescriptor containingDeclaration = classDescriptor.getContainingDeclaration();
        if (containingDeclaration instanceof ClassDescriptor) {
            return isInInnerClassesChainFor((ClassDescriptor) containingDeclaration, classDescriptor2);
        }
        return false;
    }

    private StackValue applyIntrinsic(DeclarationDescriptor declarationDescriptor, Class<? extends IntrinsicPropertyGetter> cls, ResolvedCall<?> resolvedCall, StackValue stackValue) {
        if (stackValue == null) {
            $$$reportNull$$$0(124);
        }
        if (!(declarationDescriptor instanceof CallableMemberDescriptor)) {
            return null;
        }
        CallableMemberDescriptor callableMemberDescriptorUnwrapFakeOverride = DescriptorUtils.unwrapFakeOverride((CallableMemberDescriptor) declarationDescriptor);
        IntrinsicMethod intrinsic = this.state.getIntrinsics().getIntrinsic(callableMemberDescriptorUnwrapFakeOverride);
        if (!cls.isInstance(intrinsic)) {
            return null;
        }
        return ((IntrinsicPropertyGetter) intrinsic).generate(resolvedCall, this, this.typeMapper.mapType(callableMemberDescriptorUnwrapFakeOverride), stackValue);
    }

    public ClassDescriptor getSuperCallTarget(Call call) {
        if (call == null) {
            $$$reportNull$$$0(125);
        }
        KtSuperExpression superCallExpression = CallResolverUtilKt.getSuperCallExpression(call);
        if (superCallExpression == null) {
            return null;
        }
        return getSuperCallLabelTarget(this.context, superCallExpression);
    }

    public StackValue findLocalOrCapturedValue(DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(126);
        }
        int iLookupLocalIndex = lookupLocalIndex(declarationDescriptor);
        if (iLookupLocalIndex >= 0) {
            return stackValueForLocal(declarationDescriptor, iLookupLocalIndex);
        }
        return findCapturedValue(declarationDescriptor);
    }

    public StackValue findCapturedValue(DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(127);
        }
        MethodContext methodContext = this.context;
        if (methodContext instanceof ConstructorContext) {
            return lookupCapturedValueInConstructorParameters(declarationDescriptor);
        }
        StackValue stackValueLookupInContext = methodContext.lookupInContext(declarationDescriptor, StackValue.LOCAL_0, this.state, false);
        if (!JvmCodegenUtil.isDelegatedLocalVariable(declarationDescriptor) || stackValueLookupInContext == null) {
            return stackValueLookupInContext;
        }
        return delegatedVariableValue(stackValueLookupInContext, this.context.lookupInContext(CodegenBinding.getDelegatedLocalVariableMetadata((VariableDescriptor) declarationDescriptor, this.bindingContext), StackValue.LOCAL_0, this.state, false), (VariableDescriptorWithAccessors) declarationDescriptor, this.typeMapper);
    }

    private StackValue lookupCapturedValueInConstructorParameters(DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(128);
        }
        StackValue stackValueLookupInContext = this.context.lookupInContext(declarationDescriptor, StackValue.LOCAL_0, this.state, false);
        if (this.context.closure == null || stackValueLookupInContext == null) {
            return stackValueLookupInContext;
        }
        int capturedParameterOffsetInConstructor = this.context.closure.getCapturedParameterOffsetInConstructor(declarationDescriptor);
        if (capturedParameterOffsetInConstructor == -1) {
            return adjustVariableValue(stackValueLookupInContext, declarationDescriptor);
        }
        if (stackValueLookupInContext instanceof StackValue.FieldForSharedVar) {
            return StackValue.shared(capturedParameterOffsetInConstructor, stackValueLookupInContext.type);
        }
        return adjustVariableValue(StackValue.local(capturedParameterOffsetInConstructor, stackValueLookupInContext.type, stackValueLookupInContext.kotlinType), declarationDescriptor);
    }

    private StackValue stackValueForLocal(DeclarationDescriptor declarationDescriptor, int i) {
        if (declarationDescriptor instanceof VariableDescriptor) {
            VariableDescriptor variableDescriptor = (VariableDescriptor) declarationDescriptor;
            Type sharedVarType = this.typeMapper.getSharedVarType(declarationDescriptor);
            Type variableTypeNoSharing = getVariableTypeNoSharing(variableDescriptor);
            KotlinType propertyDelegateType = JvmCodegenUtil.isDelegatedLocalVariable(declarationDescriptor) ? JvmCodegenUtil.getPropertyDelegateType((VariableDescriptorWithAccessors) declarationDescriptor, this.bindingContext) : null;
            if (sharedVarType != null) {
                return StackValue.shared(i, variableTypeNoSharing, variableDescriptor, propertyDelegateType);
            }
            return adjustVariableValue(StackValue.local(i, variableTypeNoSharing, variableDescriptor, propertyDelegateType), variableDescriptor);
        }
        return StackValue.local(i, AsmTypes.OBJECT_TYPE);
    }

    @Override // org.jetbrains.kotlin.codegen.context.LocalLookup
    public boolean isLocal(DeclarationDescriptor declarationDescriptor) {
        LocalLookup enclosingLocalLookup;
        if (lookupLocalIndex(declarationDescriptor) != -1) {
            return true;
        }
        if (!this.context.isContextWithUninitializedThis() || (enclosingLocalLookup = this.context.getParentContext().getEnclosingLocalLookup()) == null) {
            return false;
        }
        return enclosingLocalLookup.isLocal(declarationDescriptor);
    }

    public int lookupLocalIndex(DeclarationDescriptor declarationDescriptor) {
        DeclarationDescriptor declarationDescriptor2;
        int index = this.myFrameMap.getIndex(declarationDescriptor);
        if (index != -1) {
            return index;
        }
        if ((declarationDescriptor instanceof ValueParameterDescriptor) && (declarationDescriptor2 = (DeclarationDescriptor) this.bindingContext.get(CodegenBinding.PARAMETER_SYNONYM, (ValueParameterDescriptor) declarationDescriptor)) != null) {
            return this.myFrameMap.getIndex(declarationDescriptor2);
        }
        return -1;
    }

    public StackValue.Property intermediateValueForProperty(PropertyDescriptor propertyDescriptor, boolean z, ClassDescriptor classDescriptor, StackValue stackValue) {
        if (propertyDescriptor == null) {
            $$$reportNull$$$0(129);
        }
        if (stackValue == null) {
            $$$reportNull$$$0(130);
        }
        StackValue.Property propertyIntermediateValueForProperty = intermediateValueForProperty(propertyDescriptor, z, false, classDescriptor, false, stackValue, null, false);
        if (propertyIntermediateValueForProperty == null) {
            $$$reportNull$$$0(131);
        }
        return propertyIntermediateValueForProperty;
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.codegen.ExpressionCodegen$3, reason: invalid class name */
    static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$org$jetbrains$kotlin$codegen$AccessorKind;

        static {
            int[] iArr = new int[AccessorKind.values().length];
            $SwitchMap$org$jetbrains$kotlin$codegen$AccessorKind = iArr;
            try {
                iArr[AccessorKind.NORMAL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$codegen$AccessorKind[AccessorKind.IN_CLASS_COMPANION.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$codegen$AccessorKind[AccessorKind.FIELD_FROM_LOCAL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$codegen$AccessorKind[AccessorKind.LATEINIT_INTRINSIC.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    private CodegenContext getBackingFieldContext(AccessorKind accessorKind, DeclarationDescriptor declarationDescriptor) {
        CodegenContext codegenContextFindParentContextWithDescriptor;
        if (accessorKind == null) {
            $$$reportNull$$$0(132);
        }
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(133);
        }
        int i = AnonymousClass3.$SwitchMap$org$jetbrains$kotlin$codegen$AccessorKind[accessorKind.ordinal()];
        if (i == 1) {
            return (!(declarationDescriptor instanceof ClassDescriptor) || (codegenContextFindParentContextWithDescriptor = this.context.findParentContextWithDescriptor(declarationDescriptor)) == null) ? this.context.getParentContext() : codegenContextFindParentContextWithDescriptor;
        }
        if (i == 2) {
            return this.context.findParentContextWithDescriptor(declarationDescriptor.getContainingDeclaration());
        }
        if (i == 3) {
            return this.context.findParentContextWithDescriptor(declarationDescriptor);
        }
        if (i == 4) {
            return this.context.findParentContextWithDescriptor(declarationDescriptor);
        }
        throw new IllegalStateException("Unknown field accessor kind: " + accessorKind);
    }

    private static boolean isDefaultAccessor(PropertyAccessorDescriptor propertyAccessorDescriptor) {
        return propertyAccessorDescriptor == null || propertyAccessorDescriptor.isDefault();
    }

    /* JADX WARN: Code duplicated, block: B:100:0x0168 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:116:0x01ae A[PHI: r0
      0x01ae: PHI (r0v5 org.jetbrains.kotlin.descriptors.PropertyDescriptor) = 
      (r0v4 org.jetbrains.kotlin.descriptors.PropertyDescriptor)
      (r0v14 org.jetbrains.kotlin.descriptors.PropertyDescriptor)
      (r0v4 org.jetbrains.kotlin.descriptors.PropertyDescriptor)
     binds: [B:99:0x0166, B:103:0x0182, B:101:0x016a] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:118:0x01b2  */
    /* JADX WARN: Code duplicated, block: B:121:0x01c0 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:123:0x01c4  */
    /* JADX WARN: Code duplicated, block: B:125:0x01ce  */
    /* JADX WARN: Code duplicated, block: B:126:0x01d5  */
    /* JADX WARN: Code duplicated, block: B:130:0x01e6  */
    /* JADX WARN: Code duplicated, block: B:135:0x01f4 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:139:0x01fd A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:141:0x0200  */
    public StackValue.Property intermediateValueForProperty(PropertyDescriptor propertyDescriptor, boolean z, boolean z2, ClassDescriptor classDescriptor, boolean z3, StackValue stackValue, ResolvedCall resolvedCall, boolean z4) {
        AccessorKind accessorKind;
        PropertyDescriptor propertyDescriptor2;
        CallableMethod callableMethod;
        CallableMethod callableMethodMapToCallableMethod;
        String strMapDefaultFieldName;
        String str;
        KotlinType type;
        PropertySetterDescriptor setter;
        PropertyGetterDescriptor getter;
        PropertyDescriptor callableFromObject = propertyDescriptor;
        if (callableFromObject == null) {
            $$$reportNull$$$0(134);
        }
        if (stackValue == null) {
            $$$reportNull$$$0(135);
        }
        if (callableFromObject instanceof SyntheticJavaPropertyDescriptor) {
            return intermediateValueForSyntheticExtensionProperty((SyntheticJavaPropertyDescriptor) callableFromObject, stackValue);
        }
        if (callableFromObject instanceof PropertyImportedFromObject) {
            callableFromObject = ((PropertyImportedFromObject) callableFromObject).getCallableFromObject();
        }
        DeclarationDescriptor containingDeclaration = callableFromObject.getContainingDeclaration();
        boolean zIsPropertyWithBackingFieldInOuterClass = DescriptorsJvmAbiUtil.isPropertyWithBackingFieldInOuterClass(callableFromObject);
        if (z4) {
            accessorKind = AccessorKind.LATEINIT_INTRINSIC;
        } else if (zIsPropertyWithBackingFieldInOuterClass && (z || (DescriptorVisibilities.isPrivate(callableFromObject.getVisibilityImpl()) && isDefaultAccessor(callableFromObject.getGetter()) && isDefaultAccessor(callableFromObject.getSetter())))) {
            accessorKind = JvmCodegenUtil.isDebuggerContext(this.context) ? AccessorKind.NORMAL : AccessorKind.IN_CLASS_COMPANION;
        } else if (z2 && this.context.getFirstCrossInlineOrNonInlineContext().getParentContext().getContextDescriptor() != containingDeclaration) {
            accessorKind = AccessorKind.FIELD_FROM_LOCAL;
        } else {
            accessorKind = AccessorKind.NORMAL;
        }
        boolean z5 = DescriptorUtils.isStaticDeclaration(callableFromObject) || DescriptorAsmUtil.isInstancePropertyWithStaticBackingField(callableFromObject);
        boolean z6 = classDescriptor != null;
        boolean z7 = callableFromObject.getExtensionReceiverParameter() != null;
        KotlinType propertyDelegateType = JvmCodegenUtil.getPropertyDelegateType(callableFromObject, this.bindingContext);
        boolean z8 = propertyDelegateType != null;
        CodegenContext<? extends ClassOrPackageFragmentDescriptor> backingFieldContext = getBackingFieldContext(accessorKind, containingDeclaration);
        boolean z9 = (accessorKind == AccessorKind.NORMAL || (DescriptorAsmUtil.getVisibilityForBackingField(callableFromObject, z8) & 2) == 0) ? false : true;
        PropertyDescriptor propertyDescriptor3 = (PropertyDescriptor) DescriptorUtils.unwrapFakeOverride(callableFromObject);
        PropertyDescriptor propertyDescriptor4 = containingDeclaration;
        boolean zCouldUseDirectAccessToProperty = JvmCodegenUtil.couldUseDirectAccessToProperty(callableFromObject, true, z8, this.context, this.state.getConfig().getShouldInlineConstVals());
        boolean z10 = false;
        boolean zCouldUseDirectAccessToProperty2 = JvmCodegenUtil.couldUseDirectAccessToProperty(callableFromObject, false, z8, this.context, this.state.getConfig().getShouldInlineConstVals());
        if (accessorKind == AccessorKind.LATEINIT_INTRINSIC) {
            if ((!z9 || this.context.getClassOrPackageParentContext() == backingFieldContext) && !zIsPropertyWithBackingFieldInOuterClass) {
                z10 = true;
            }
            if (!z10) {
                if (zIsPropertyWithBackingFieldInOuterClass && (this.context.getContextDescriptor() instanceof AccessorForPropertyBackingField)) {
                    callableFromObject = (PropertyDescriptor) backingFieldContext.getParentContext().getAccessor(callableFromObject, AccessorKind.IN_CLASS_COMPANION, propertyDelegateType, classDescriptor);
                } else {
                    callableFromObject = (PropertyDescriptor) backingFieldContext.getAccessor(callableFromObject, accessorKind, propertyDelegateType, classDescriptor);
                }
            }
        } else {
            if (accessorKind == AccessorKind.IN_CLASS_COMPANION || accessorKind == AccessorKind.FIELD_FROM_LOCAL) {
                if (z3 || (zCouldUseDirectAccessToProperty && (!callableFromObject.isVar() || zCouldUseDirectAccessToProperty2))) {
                    z10 = true;
                }
                if (!z10) {
                    callableFromObject = (PropertyDescriptor) backingFieldContext.getAccessor(callableFromObject, accessorKind, propertyDelegateType, classDescriptor);
                }
                if (!z10 || (zCouldUseDirectAccessToProperty && zCouldUseDirectAccessToProperty2)) {
                    callableMethod = null;
                    callableMethodMapToCallableMethod = null;
                } else {
                    callableFromObject = (PropertyDescriptor) this.context.accessibleDescriptor((PropertyDescriptor) this.context.getAccessorForSuperCallIfNeeded(callableFromObject, classDescriptor, this.state), classDescriptor);
                    if (JvmCodegenUtil.isConstOrHasJvmFieldAnnotation(callableFromObject)) {
                        callableMethod = null;
                        callableMethodMapToCallableMethod = null;
                    } else {
                        CallableMethod callableMethodMapToCallableMethod2 = (zCouldUseDirectAccessToProperty || (getter = callableFromObject.getGetter()) == null) ? null : this.typeMapper.mapToCallableMethod(getter, z6);
                        if (!callableFromObject.isVar() || zCouldUseDirectAccessToProperty2 || (setter = callableFromObject.getSetter()) == null) {
                            callableMethod = callableMethodMapToCallableMethod2;
                            callableMethodMapToCallableMethod = null;
                        } else {
                            callableMethod = callableMethodMapToCallableMethod2;
                            callableMethodMapToCallableMethod = this.typeMapper.mapToCallableMethod(setter, z6);
                        }
                    }
                }
                if (!z5) {
                    callableFromObject = (PropertyDescriptor) DescriptorUtils.unwrapFakeOverride(callableFromObject);
                }
                Type typeMapOwner = this.typeMapper.mapOwner(propertyDescriptor2);
                if (z7 || z8) {
                    if (propertyDescriptor3.getContainingDeclaration() == backingFieldContext.getContextDescriptor()) {
                        strMapDefaultFieldName = ((FieldOwnerContext) backingFieldContext).getFieldName(callableFromObject, z8);
                    } else {
                        strMapDefaultFieldName = KotlinTypeMapper.mapDefaultFieldName(callableFromObject, z8);
                    }
                    str = strMapDefaultFieldName;
                } else {
                    str = null;
                }
                type = callableFromObject.getOriginal().getType();
                if ((callableFromObject instanceof JavaPropertyDescriptor) && InlineClassesUtilsKt.isInlineClassType(type)) {
                    type = TypeUtils.makeNullable(type);
                }
                KotlinTypeMapper kotlinTypeMapper = this.typeMapper;
                if (z8 && z) {
                    type = propertyDelegateType;
                }
                Type typeMapType = kotlinTypeMapper.mapType(type);
                if (z8 || !z) {
                    propertyDelegateType = null;
                }
                return StackValue.property(callableFromObject, typeMapOwner, typeMapType, z5, str, callableMethod, callableMethodMapToCallableMethod, stackValue, this, resolvedCall, z4, propertyDelegateType);
            }
            boolean z11 = (JvmCodegenUtil.isDebuggerContext(this.context) && DescriptorVisibilities.isPrivate(callableFromObject.getVisibilityImpl()) && this.bindingContext.get(BindingContext.BACKING_FIELD_REQUIRED, callableFromObject) == Boolean.TRUE) ? true : z;
            if (!zIsPropertyWithBackingFieldInOuterClass) {
                propertyDescriptor4 = callableFromObject;
            }
            z10 = z11;
            propertyDescriptor2 = propertyDescriptor4;
            if (z10) {
                callableMethod = null;
                callableMethodMapToCallableMethod = null;
            } else {
                callableMethod = null;
                callableMethodMapToCallableMethod = null;
            }
            if (!z5) {
                callableFromObject = (PropertyDescriptor) DescriptorUtils.unwrapFakeOverride(callableFromObject);
            }
            Type typeMapOwner2 = this.typeMapper.mapOwner(propertyDescriptor2);
            if (z7) {
                if (propertyDescriptor3.getContainingDeclaration() == backingFieldContext.getContextDescriptor()) {
                    strMapDefaultFieldName = ((FieldOwnerContext) backingFieldContext).getFieldName(callableFromObject, z8);
                } else {
                    strMapDefaultFieldName = KotlinTypeMapper.mapDefaultFieldName(callableFromObject, z8);
                }
                str = strMapDefaultFieldName;
            } else {
                if (propertyDescriptor3.getContainingDeclaration() == backingFieldContext.getContextDescriptor()) {
                    strMapDefaultFieldName = ((FieldOwnerContext) backingFieldContext).getFieldName(callableFromObject, z8);
                } else {
                    strMapDefaultFieldName = KotlinTypeMapper.mapDefaultFieldName(callableFromObject, z8);
                }
                str = strMapDefaultFieldName;
            }
            type = callableFromObject.getOriginal().getType();
            if (callableFromObject instanceof JavaPropertyDescriptor) {
                type = TypeUtils.makeNullable(type);
            }
            KotlinTypeMapper kotlinTypeMapper2 = this.typeMapper;
            if (z8) {
                type = propertyDelegateType;
            }
            Type typeMapType2 = kotlinTypeMapper2.mapType(type);
            if (z8) {
                propertyDelegateType = null;
            } else {
                propertyDelegateType = null;
            }
            return StackValue.property(callableFromObject, typeMapOwner2, typeMapType2, z5, str, callableMethod, callableMethodMapToCallableMethod, stackValue, this, resolvedCall, z4, propertyDelegateType);
        }
        propertyDescriptor2 = callableFromObject;
        if (z10) {
            callableMethod = null;
            callableMethodMapToCallableMethod = null;
        } else {
            callableMethod = null;
            callableMethodMapToCallableMethod = null;
        }
        if (!z5) {
            callableFromObject = (PropertyDescriptor) DescriptorUtils.unwrapFakeOverride(callableFromObject);
        }
        Type typeMapOwner3 = this.typeMapper.mapOwner(propertyDescriptor2);
        if (z7) {
            if (propertyDescriptor3.getContainingDeclaration() == backingFieldContext.getContextDescriptor()) {
                strMapDefaultFieldName = ((FieldOwnerContext) backingFieldContext).getFieldName(callableFromObject, z8);
            } else {
                strMapDefaultFieldName = KotlinTypeMapper.mapDefaultFieldName(callableFromObject, z8);
            }
            str = strMapDefaultFieldName;
        } else {
            if (propertyDescriptor3.getContainingDeclaration() == backingFieldContext.getContextDescriptor()) {
                strMapDefaultFieldName = ((FieldOwnerContext) backingFieldContext).getFieldName(callableFromObject, z8);
            } else {
                strMapDefaultFieldName = KotlinTypeMapper.mapDefaultFieldName(callableFromObject, z8);
            }
            str = strMapDefaultFieldName;
        }
        type = callableFromObject.getOriginal().getType();
        if (callableFromObject instanceof JavaPropertyDescriptor) {
            type = TypeUtils.makeNullable(type);
        }
        KotlinTypeMapper kotlinTypeMapper3 = this.typeMapper;
        if (z8) {
            type = propertyDelegateType;
        }
        Type typeMapType3 = kotlinTypeMapper3.mapType(type);
        if (z8) {
            propertyDelegateType = null;
        } else {
            propertyDelegateType = null;
        }
        return StackValue.property(callableFromObject, typeMapOwner3, typeMapType3, z5, str, callableMethod, callableMethodMapToCallableMethod, stackValue, this, resolvedCall, z4, propertyDelegateType);
    }

    private StackValue.Property intermediateValueForSyntheticExtensionProperty(SyntheticJavaPropertyDescriptor syntheticJavaPropertyDescriptor, StackValue stackValue) {
        if (syntheticJavaPropertyDescriptor == null) {
            $$$reportNull$$$0(136);
        }
        if (stackValue == null) {
            $$$reportNull$$$0(137);
        }
        Type typeMapType = this.typeMapper.mapType(syntheticJavaPropertyDescriptor.getOriginal().getType());
        CallableMethod callableMethodMapToCallableMethod = this.typeMapper.mapToCallableMethod((FunctionDescriptor) this.context.accessibleDescriptor(syntheticJavaPropertyDescriptor.getGetMethod(), null), false);
        FunctionDescriptor setMethod = syntheticJavaPropertyDescriptor.getSetMethod();
        StackValue.Property property = StackValue.property(syntheticJavaPropertyDescriptor, null, typeMapType, false, null, callableMethodMapToCallableMethod, setMethod != null ? this.typeMapper.mapToCallableMethod((FunctionDescriptor) this.context.accessibleDescriptor(setMethod, null), false) : null, stackValue, this, null, false, null);
        if (property == null) {
            $$$reportNull$$$0(138);
        }
        return property;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public StackValue visitCallExpression(KtCallExpression ktCallExpression, StackValue stackValue) {
        if (ktCallExpression == null) {
            $$$reportNull$$$0(139);
        }
        ResolvedCall<? extends CallableDescriptor> resolvedCallWithAssert = CallUtilKt.getResolvedCallWithAssert(ktCallExpression, this.bindingContext);
        FunctionDescriptor functionDescriptorAccessibleFunctionDescriptor = accessibleFunctionDescriptor(resolvedCallWithAssert);
        if (functionDescriptorAccessibleFunctionDescriptor instanceof ConstructorDescriptor) {
            return generateNewCall(ktCallExpression, resolvedCallWithAssert);
        }
        if (functionDescriptorAccessibleFunctionDescriptor.getOriginal() instanceof SamConstructorDescriptor) {
            return genSamInterfaceValue((KtExpression) this.bindingContext.get(CodegenBinding.SAM_CONSTRUCTOR_TO_ARGUMENT, ktCallExpression), this);
        }
        return invokeFunction(resolvedCallWithAssert, stackValue);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public StackValue visitCollectionLiteralExpression(KtCollectionLiteralExpression ktCollectionLiteralExpression, StackValue stackValue) {
        if (ktCollectionLiteralExpression == null) {
            $$$reportNull$$$0(140);
        }
        return invokeFunction((ResolvedCall) this.bindingContext.get(BindingContext.COLLECTION_LITERAL_CALL, ktCollectionLiteralExpression), stackValue);
    }

    private StackValue genSamInterfaceValue(KtExpression ktExpression, final KtVisitor<StackValue, StackValue> ktVisitor) {
        if (ktExpression == null) {
            $$$reportNull$$$0(141);
        }
        if (ktVisitor == null) {
            $$$reportNull$$$0(142);
        }
        final KtExpression ktExpressionDeparenthesize = KtPsiUtil.deparenthesize(ktExpression);
        final SamType samType = (SamType) this.bindingContext.get(CodegenBinding.SAM_VALUE, ktExpression);
        if (samType == null || ktExpressionDeparenthesize == null) {
            return null;
        }
        if (ktExpressionDeparenthesize instanceof KtLambdaExpression) {
            return genClosure(((KtLambdaExpression) ktExpressionDeparenthesize).getFunctionLiteral(), samType);
        }
        if (ktExpressionDeparenthesize instanceof KtNamedFunction) {
            return genClosure((KtNamedFunction) ktExpressionDeparenthesize, samType);
        }
        final Type samWrapperClass = this.state.getSamWrapperClasses().getSamWrapperClass(samType, ktExpressionDeparenthesize.getContainingKtFile(), this, this.context.getContextDescriptor());
        return StackValue.operation(samWrapperClass, new Function1() { // from class: org.jetbrains.kotlin.codegen.ExpressionCodegen$$ExternalSyntheticLambda27
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return this.f$0.m6386x8b8235d7(samType, ktExpressionDeparenthesize, ktVisitor, samWrapperClass, (InstructionAdapter) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$genSamInterfaceValue$13$org-jetbrains-kotlin-codegen-ExpressionCodegen, reason: not valid java name */
    /* synthetic */ Unit m6386x8b8235d7(SamType samType, KtExpression ktExpression, KtVisitor ktVisitor, Type type, InstructionAdapter instructionAdapter) {
        Label label = new Label();
        Type typeMapType = this.typeMapper.mapType(samType.getKotlinFunctionType());
        ((StackValue) ktExpression.accept(ktVisitor, StackValue.none())).put(typeMapType, null, instructionAdapter);
        instructionAdapter.dup();
        instructionAdapter.ifnull(label);
        int iEnterTemp = this.myFrameMap.enterTemp(typeMapType);
        instructionAdapter.store(iEnterTemp, typeMapType);
        instructionAdapter.anew(type);
        instructionAdapter.dup();
        instructionAdapter.load(iEnterTemp, typeMapType);
        instructionAdapter.invokespecial(type.getInternalName(), "<init>", Type.getMethodDescriptor(Type.VOID_TYPE, typeMapType), false);
        this.myFrameMap.leaveTemp(typeMapType);
        instructionAdapter.mark(label);
        return null;
    }

    protected FunctionDescriptor accessibleFunctionDescriptor(ResolvedCall<?> resolvedCall) {
        if (resolvedCall == null) {
            $$$reportNull$$$0(143);
        }
        FunctionDescriptor underlyingConstructorDescriptor = (FunctionDescriptor) resolvedCall.getResultingDescriptor();
        if (underlyingConstructorDescriptor instanceof TypeAliasConstructorDescriptor) {
            underlyingConstructorDescriptor = ((TypeAliasConstructorDescriptor) underlyingConstructorDescriptor).getUnderlyingConstructorDescriptor();
        }
        FunctionDescriptor originalIfSamAdapter = SamCodegenUtil.getOriginalIfSamAdapter(underlyingConstructorDescriptor);
        if (originalIfSamAdapter != null) {
            underlyingConstructorDescriptor = originalIfSamAdapter;
        }
        FunctionDescriptor functionDescriptor = (FunctionDescriptor) CoroutineCodegenUtilKt.unwrapInitialDescriptorForSuspendFunction(underlyingConstructorDescriptor);
        if (CallUtilKt.usesDefaultArguments(resolvedCall)) {
            if (functionDescriptor == null) {
                $$$reportNull$$$0(144);
            }
            return functionDescriptor;
        }
        if (shouldForceAccessorForConstructor(functionDescriptor.getOriginal())) {
            return createAccessorForHiddenConstructor(resolvedCall, functionDescriptor);
        }
        FunctionDescriptor functionDescriptor2 = (FunctionDescriptor) this.context.accessibleDescriptor(functionDescriptor, getSuperCallTarget(resolvedCall.getCall()));
        if (functionDescriptor2 == null) {
            $$$reportNull$$$0(145);
        }
        return functionDescriptor2;
    }

    private boolean shouldForceAccessorForConstructor(FunctionDescriptor functionDescriptor) {
        return DescriptorAsmUtil.isHiddenConstructor(functionDescriptor) && functionDescriptor.getContainingDeclaration() != this.context.getContextDescriptor().getContainingDeclaration();
    }

    private AccessorForConstructorDescriptor createAccessorForHiddenConstructor(ResolvedCall<?> resolvedCall, FunctionDescriptor functionDescriptor) {
        if (resolvedCall == null) {
            $$$reportNull$$$0(146);
        }
        return new AccessorForConstructorDescriptor((ClassConstructorDescriptor) functionDescriptor.getOriginal(), functionDescriptor.getContainingDeclaration(), getSuperCallTarget(resolvedCall.getCall()), AccessorKind.NORMAL);
    }

    public StackValue invokeFunction(ResolvedCall<?> resolvedCall, StackValue stackValue) {
        if (resolvedCall == null) {
            $$$reportNull$$$0(147);
        }
        if (stackValue == null) {
            $$$reportNull$$$0(148);
        }
        StackValue stackValueInvokeFunction = invokeFunction(resolvedCall.getCall(), resolvedCall, stackValue);
        if (stackValueInvokeFunction == null) {
            $$$reportNull$$$0(149);
        }
        return stackValueInvokeFunction;
    }

    public StackValue invokeFunction(Call call, ResolvedCall<?> resolvedCall, StackValue stackValue) {
        if (call == null) {
            $$$reportNull$$$0(150);
        }
        if (resolvedCall == null) {
            $$$reportNull$$$0(151);
        }
        if (stackValue == null) {
            $$$reportNull$$$0(152);
        }
        ResolvedCallWithRealDescriptor resolvedCallWithRealDescriptorReplaceSuspensionFunctionWithRealDescriptor = CoroutineCodegenUtilKt.replaceSuspensionFunctionWithRealDescriptor(resolvedCall, this.state);
        if (resolvedCallWithRealDescriptorReplaceSuspensionFunctionWithRealDescriptor != null) {
            prepareCoroutineArgumentForSuspendCall(resolvedCall, resolvedCallWithRealDescriptorReplaceSuspensionFunctionWithRealDescriptor.getFakeContinuationExpression());
            StackValue stackValueInvokeFunction = invokeFunction(resolvedCallWithRealDescriptorReplaceSuspensionFunctionWithRealDescriptor.getResolvedCall(), stackValue);
            if (stackValueInvokeFunction == null) {
                $$$reportNull$$$0(153);
            }
            return stackValueInvokeFunction;
        }
        FunctionDescriptor functionDescriptorAccessibleFunctionDescriptor = accessibleFunctionDescriptor(resolvedCall);
        ClassDescriptor superCallTarget = getSuperCallTarget(call);
        FunctionDescriptor functionDescriptor = (FunctionDescriptor) this.context.getAccessorForSuperCallIfNeeded(functionDescriptorAccessibleFunctionDescriptor, superCallTarget, this.state);
        List<ExpressionCodegenExtension> instances = ExpressionCodegenExtension.INSTANCE.getInstances(this.state.getProject());
        if (!instances.isEmpty()) {
            ExpressionCodegenExtension.Context context = new ExpressionCodegenExtension.Context(this, this.typeMapper, this.v);
            Iterator<ExpressionCodegenExtension> it = instances.iterator();
            while (it.hasNext()) {
                StackValue stackValueApplyFunction = it.next().applyFunction(stackValue, resolvedCall, context);
                if (stackValueApplyFunction != null) {
                    if (stackValueApplyFunction == null) {
                        $$$reportNull$$$0(154);
                    }
                    return stackValueApplyFunction;
                }
            }
        }
        StackValue stackValueInvokeMethodWithArguments = resolveToCallable(functionDescriptor, superCallTarget != null, resolvedCall).invokeMethodWithArguments(resolvedCall, stackValue, this);
        if (stackValueInvokeMethodWithArguments == null) {
            $$$reportNull$$$0(155);
        }
        return stackValueInvokeMethodWithArguments;
    }

    private void prepareCoroutineArgumentForSuspendCall(ResolvedCall<?> resolvedCall, KtExpression ktExpression) {
        if (resolvedCall == null) {
            $$$reportNull$$$0(156);
        }
        if (ktExpression == null) {
            $$$reportNull$$$0(157);
        }
        StackValue coroutineInstanceValueForSuspensionPoint = getCoroutineInstanceValueForSuspensionPoint(resolvedCall);
        if (coroutineInstanceValueForSuspensionPoint == null) {
            coroutineInstanceValueForSuspensionPoint = getContinuationParameterFromEnclosingSuspendFunction(resolvedCall);
        }
        this.tempVariables.put(ktExpression, coroutineInstanceValueForSuspensionPoint);
    }

    private StackValue getContinuationParameterFromEnclosingSuspendFunction(ResolvedCall<?> resolvedCall) {
        if (resolvedCall == null) {
            $$$reportNull$$$0(158);
        }
        return getContinuationParameterFromEnclosingSuspendFunctionDescriptor((FunctionDescriptor) this.bindingContext.get(CodegenBinding.SUSPEND_FUNCTION_TO_JVM_VIEW, (FunctionDescriptor) this.bindingContext.get(BindingContext.ENCLOSING_SUSPEND_FUNCTION_FOR_SUSPEND_FUNCTION_CALL, resolvedCall.getCall())));
    }

    public StackValue getContinuationParameterFromEnclosingSuspendFunctionDescriptor(FunctionDescriptor functionDescriptor) {
        if (functionDescriptor == null) {
            $$$reportNull$$$0(159);
        }
        return findLocalOrCapturedValue(functionDescriptor.getValueParameters().get(functionDescriptor.getValueParameters().size() - 1));
    }

    public static CodegenContext getParentContextSubclassOf(ClassDescriptor classDescriptor, CodegenContext codegenContext) {
        while (codegenContext != null) {
            if ((codegenContext instanceof ClassContext) && DescriptorUtils.isSubclass(codegenContext.getThisDescriptor(), classDescriptor)) {
                return codegenContext;
            }
            codegenContext = codegenContext.getParentContext();
        }
        return null;
    }

    public static CodegenContext getCompanionObjectContextSubclassOf(ClassDescriptor classDescriptor, CodegenContext codegenContext) {
        while (codegenContext != null) {
            if (codegenContext instanceof ClassContext) {
                ClassContext classContext = (ClassContext) codegenContext;
                ClassDescriptor classDescriptorMo6346getCompanionObjectDescriptor = ((ClassDescriptor) classContext.getContextDescriptor()).mo6346getCompanionObjectDescriptor();
                if (classDescriptorMo6346getCompanionObjectDescriptor != null && DescriptorUtils.isSubclass(classDescriptorMo6346getCompanionObjectDescriptor, classDescriptor)) {
                    return classContext.getCompanionObjectContext();
                }
            }
            codegenContext = codegenContext.getParentContext();
        }
        return null;
    }

    Callable resolveToCallable(FunctionDescriptor functionDescriptor, boolean z, ResolvedCall<?> resolvedCall) {
        Boolean boolClassFileContainsMethod;
        if (functionDescriptor == null) {
            $$$reportNull$$$0(160);
        }
        if (resolvedCall == null) {
            $$$reportNull$$$0(161);
        }
        IntrinsicMethod intrinsic = this.state.getIntrinsics().getIntrinsic(functionDescriptor);
        if (intrinsic != null) {
            Callable callable = intrinsic.toCallable(functionDescriptor, z, resolvedCall, this);
            if (callable == null) {
                $$$reportNull$$$0(162);
            }
            return callable;
        }
        FunctionDescriptor functionDescriptorResolveSamAdapter = SamCodegenUtil.resolveSamAdapter(functionDescriptor);
        List<ResolvedValueArgument> valueArgumentsByIndex = resolvedCall.getValueArgumentsByIndex();
        if (valueArgumentsByIndex != null && valueArgumentsByIndex.stream().anyMatch(new Predicate() { // from class: org.jetbrains.kotlin.codegen.ExpressionCodegen$$ExternalSyntheticLambda12
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ExpressionCodegen.lambda$resolveToCallable$14((ResolvedValueArgument) obj);
            }
        })) {
            functionDescriptorResolveSamAdapter = ArgumentGeneratorKt.getFunctionWithDefaultArguments(functionDescriptorResolveSamAdapter);
        }
        CallableMethod callableMethodMapToCallableMethod = this.typeMapper.mapToCallableMethod(functionDescriptorResolveSamAdapter, z, null, resolvedCall);
        if (callableMethodMapToCallableMethod.getAsmMethod().getName().contains(Parameters.DEFAULT_OPTION_PREFIXES) && !this.state.getConfiguration().getBoolean(JVMConfigurationKeys.USE_OLD_INLINE_CLASSES_MANGLING_SCHEME) && (boolClassFileContainsMethod = InlineClassesCodegenUtilKt.classFileContainsMethod(functionDescriptorResolveSamAdapter, this.state, callableMethodMapToCallableMethod.getAsmMethod())) != null && !boolClassFileContainsMethod.booleanValue()) {
            this.typeMapper.setUseOldManglingRulesForFunctionAcceptingInlineClass(true);
            callableMethodMapToCallableMethod = this.typeMapper.mapToCallableMethod(functionDescriptorResolveSamAdapter, z, null, resolvedCall);
            this.typeMapper.setUseOldManglingRulesForFunctionAcceptingInlineClass(false);
        }
        if (callableMethodMapToCallableMethod == null) {
            $$$reportNull$$$0(163);
        }
        return callableMethodMapToCallableMethod;
    }

    static /* synthetic */ boolean lambda$resolveToCallable$14(ResolvedValueArgument resolvedValueArgument) {
        return resolvedValueArgument instanceof DefaultValueArgument;
    }

    public void invokeMethodWithArguments(Callable callable, ResolvedCall<?> resolvedCall, StackValue stackValue) {
        if (callable == null) {
            $$$reportNull$$$0(164);
        }
        if (resolvedCall == null) {
            $$$reportNull$$$0(165);
        }
        if (stackValue == null) {
            $$$reportNull$$$0(166);
        }
        CallGenerator orCreateCallGenerator = getOrCreateCallGenerator(resolvedCall);
        invokeMethodWithArguments(callable, resolvedCall, stackValue, orCreateCallGenerator, new CallBasedArgumentGenerator(this, orCreateCallGenerator, resolvedCall.getResultingDescriptor().getValueParameters(), callable.getValueParameterTypes()));
    }

    public void invokeMethodWithArguments(Callable callable, ResolvedCall<?> resolvedCall, StackValue stackValue, CallGenerator callGenerator, ArgumentGenerator argumentGenerator) {
        if (callable == null) {
            $$$reportNull$$$0(167);
        }
        if (resolvedCall == null) {
            $$$reportNull$$$0(168);
        }
        if (stackValue == null) {
            $$$reportNull$$$0(169);
        }
        if (callGenerator == null) {
            $$$reportNull$$$0(170);
        }
        if (argumentGenerator == null) {
            $$$reportNull$$$0(171);
        }
        if (AssertCodegenUtilKt.isAssertCall(resolvedCall) && !this.state.getConfig().getAssertionsMode().equals(JVMAssertionsMode.LEGACY)) {
            AssertCodegenUtilKt.generateAssert(this.state.getConfig().getAssertionsMode(), resolvedCall, this, this.parentCodegen);
            return;
        }
        SuspensionPointKind suspensionPointKindIsSuspensionPoint = CoroutineCodegenUtilKt.isSuspensionPoint(resolvedCall, this);
        boolean z = (suspensionPointKindIsSuspensionPoint == SuspensionPointKind.NEVER || insideCallableReference()) ? false : true;
        boolean z2 = resolvedCall.getResultingDescriptor() instanceof ConstructorDescriptor;
        if (!(callable instanceof IntrinsicWithSpecialReceiver)) {
            putReceiverAndInlineMarkerIfNeeded(callable, resolvedCall, stackValue, z, z2);
        }
        callGenerator.processHiddenParameters();
        callGenerator.putHiddenParamsIntoLocals();
        DefaultCallArgs defaultCallArgsGenerate = argumentGenerator.generate(resolvedCall.getValueArgumentsByIndex(), new ArrayList(resolvedCall.getValueArguments().values()), resolvedCall.getResultingDescriptor());
        if (this.tailRecursionCodegen.isTailRecursion(resolvedCall)) {
            this.tailRecursionCodegen.generateTailRecursion(resolvedCall);
            return;
        }
        boolean zGenerateOnStackIfNeeded = defaultCallArgsGenerate.generateOnStackIfNeeded(callGenerator, z2);
        if (callable instanceof CallableMethod) {
            for (JvmMethodParameterSignature jvmMethodParameterSignature : ((CallableMethod) callable).getValueParameters()) {
                if (jvmMethodParameterSignature.getKind() == JvmMethodParameterKind.CONSTRUCTOR_MARKER) {
                    callGenerator.putValueIfNeeded(new JvmKotlinType(jvmMethodParameterSignature.getAsmType(), null), StackValue.constant(null, jvmMethodParameterSignature.getAsmType()));
                }
            }
        }
        if (z) {
            InlineCodegenUtilsKt.addSuspendMarker(this.v, true, suspensionPointKindIsSuspensionPoint == SuspensionPointKind.NOT_INLINE);
        }
        callGenerator.genCall(callable, resolvedCall, zGenerateOnStackIfNeeded, this);
        if (z) {
            InlineCodegenUtilsKt.addReturnsUnitMarkerIfNecessary(this.v, resolvedCall);
            InlineCodegenUtilsKt.addSuspendMarker(this.v, false, suspensionPointKindIsSuspensionPoint == SuspensionPointKind.NOT_INLINE);
            InlineCodegenUtilsKt.addUnboxInlineClassMarkersIfNeeded(this.v, resolvedCall.getResultingDescriptor(), this.typeMapper);
            InlineCodegenUtilsKt.addInlineMarker(this.v, false);
        }
        if (insideCallableReference() && (resolvedCall.getResultingDescriptor() instanceof FunctionDescriptor) && CoroutineCodegenUtilKt.originalReturnTypeOfSuspendFunctionReturningUnboxedInlineClass((FunctionDescriptor) resolvedCall.getResultingDescriptor(), this.typeMapper) != null) {
            CoroutineCodegenUtilKt.generateCoroutineSuspendedCheck(this.v);
        }
        KotlinType returnType = resolvedCall.getResultingDescriptor().getReturnType();
        if (returnType == null || !KotlinBuiltIns.isNothing(returnType)) {
            return;
        }
        if (this.state.getConfig().getUseKotlinNothingValueException()) {
            this.v.anew(Type.getObjectType("kotlin/KotlinNothingValueException"));
            this.v.dup();
            this.v.invokespecial("kotlin/KotlinNothingValueException", "<init>", "()V", false);
        } else {
            this.v.aconst(null);
        }
        this.v.athrow();
    }

    private boolean insideCallableReference() {
        MemberCodegen<?> memberCodegen = this.parentCodegen;
        return (memberCodegen instanceof ClosureCodegen) && ((ClosureCodegen) memberCodegen).isCallableReference();
    }

    private void putReceiverAndInlineMarkerIfNeeded(Callable callable, ResolvedCall<?> resolvedCall, StackValue stackValue, boolean z, boolean z2) {
        int iEnterTemp;
        if (callable == null) {
            $$$reportNull$$$0(172);
        }
        if (resolvedCall == null) {
            $$$reportNull$$$0(173);
        }
        if (stackValue == null) {
            $$$reportNull$$$0(174);
        }
        boolean z3 = false;
        boolean z4 = (stackValue instanceof StackValue.SafeCall) || (stackValue instanceof StackValue.OnStack);
        if (z && !z4 && !this.tailRecursionCodegen.isTailRecursion(resolvedCall)) {
            InlineCodegenUtilsKt.addInlineMarker(this.v, true);
        }
        if (z2) {
            return;
        }
        StackValue stackValueReceiver = StackValue.receiver(resolvedCall, stackValue, this, callable);
        stackValueReceiver.put(stackValueReceiver.type, stackValueReceiver.kotlinType, this.v);
        if (z && z4) {
            if (stackValueReceiver instanceof CallReceiver) {
                CallReceiver callReceiver = (CallReceiver) stackValueReceiver;
                if (callReceiver.getDispatchReceiver().type.getSort() != 0 && callReceiver.getExtensionReceiver().type.getSort() != 0) {
                    z3 = true;
                }
            }
            Type type = z3 ? ((CallReceiver) stackValueReceiver).getDispatchReceiver().type : stackValueReceiver.type;
            Type type2 = z3 ? stackValueReceiver.type : null;
            int iEnterTemp2 = this.myFrameMap.enterTemp(type);
            if (type2 != null) {
                iEnterTemp = this.myFrameMap.enterTemp(type2);
                this.v.store(iEnterTemp, type2);
            } else {
                iEnterTemp = -1;
            }
            this.v.store(iEnterTemp2, type);
            InlineCodegenUtilsKt.addInlineMarker(this.v, true);
            this.v.load(iEnterTemp2, type);
            if (type2 != null) {
                this.v.load(iEnterTemp, type2);
                this.myFrameMap.leaveTemp(type2);
            }
            this.myFrameMap.leaveTemp(type);
        }
        callable.afterReceiverGeneration(this.v, this.myFrameMap, this.state);
    }

    private CallGenerator getOrCreateCallGenerator(CallableDescriptor callableDescriptor, KtElement ktElement, TypeParameterMappings<KotlinType> typeParameterMappings, boolean z) {
        Boolean boolClassFileContainsMethod;
        if (callableDescriptor == null) {
            $$$reportNull$$$0(175);
        }
        if (ktElement == null) {
            CallGenerator callGenerator = this.defaultCallGenerator;
            if (callGenerator == null) {
                $$$reportNull$$$0(176);
            }
            return callGenerator;
        }
        boolean z2 = (callableDescriptor instanceof CallableMemberDescriptor) && this.state.getIntrinsics().getIntrinsic((CallableMemberDescriptor) callableDescriptor) != null;
        if (((!InlineUtil.isInline(callableDescriptor) || z2) && !InlineUtil.isArrayConstructorWithLambda(callableDescriptor)) || (this.state.getConfig().getIsInlineDisabled() && !InlineUtil.containsReifiedTypeParameters(callableDescriptor))) {
            CallGenerator callGenerator2 = this.defaultCallGenerator;
            if (callGenerator2 == null) {
                $$$reportNull$$$0(177);
            }
            return callGenerator2;
        }
        FunctionDescriptor originalSuspendFunctionView = CoroutineCodegenUtilKt.getOriginalSuspendFunctionView(CodegenUtilKt.unwrapInitialSignatureDescriptor((FunctionDescriptor) DescriptorUtils.unwrapFakeOverride((FunctionDescriptor) callableDescriptor.getOriginal())), this.bindingContext);
        FunctionDescriptor functionDescriptorCreate = InlineUtil.isArrayConstructorWithLambda(originalSuspendFunctionView) ? FictitiousArrayConstructor.create((ConstructorDescriptor) originalSuspendFunctionView) : originalSuspendFunctionView.getOriginal();
        PsiSourceCompilerForInline psiSourceCompilerForInline = new PsiSourceCompilerForInline(this, ktElement, functionDescriptorCreate);
        JvmMethodGenericSignature jvmMethodGenericSignatureMapSignatureWithGeneric = this.typeMapper.mapSignatureWithGeneric(functionDescriptorCreate, psiSourceCompilerForInline.getContext().getContextKind());
        if (jvmMethodGenericSignatureMapSignatureWithGeneric.getAsmMethod().getName().contains(Parameters.DEFAULT_OPTION_PREFIXES) && !this.state.getConfiguration().getBoolean(JVMConfigurationKeys.USE_OLD_INLINE_CLASSES_MANGLING_SCHEME) && (boolClassFileContainsMethod = InlineClassesCodegenUtilKt.classFileContainsMethod(functionDescriptorCreate, this.state, jvmMethodGenericSignatureMapSignatureWithGeneric.getAsmMethod())) != null && !boolClassFileContainsMethod.booleanValue()) {
            this.typeMapper.setUseOldManglingRulesForFunctionAcceptingInlineClass(true);
            jvmMethodGenericSignatureMapSignatureWithGeneric = this.typeMapper.mapSignatureWithGeneric(functionDescriptorCreate, psiSourceCompilerForInline.getContext().getContextKind());
            this.typeMapper.setUseOldManglingRulesForFunctionAcceptingInlineClass(false);
        }
        JvmMethodGenericSignature jvmMethodGenericSignature = jvmMethodGenericSignatureMapSignatureWithGeneric;
        if (z) {
            return new InlineCodegenForDefaultBody(functionDescriptorCreate, this, this.state, jvmMethodGenericSignature, psiSourceCompilerForInline);
        }
        return new PsiInlineCodegen(this, this.state, functionDescriptorCreate, jvmMethodGenericSignature, typeParameterMappings, psiSourceCompilerForInline, this.typeMapper.mapImplementationOwner(functionDescriptorCreate), this.typeMapper.mapOwner(callableDescriptor), ktElement);
    }

    protected CallGenerator getOrCreateCallGeneratorForDefaultImplBody(FunctionDescriptor functionDescriptor, KtNamedFunction ktNamedFunction) {
        if (functionDescriptor == null) {
            $$$reportNull$$$0(178);
        }
        return getOrCreateCallGenerator(functionDescriptor, ktNamedFunction, null, true);
    }

    CallGenerator getOrCreateCallGenerator(ResolvedCall<?> resolvedCall) {
        if (resolvedCall == null) {
            $$$reportNull$$$0(179);
        }
        return getOrCreateCallGenerator(resolvedCall, resolvedCall.getResultingDescriptor());
    }

    CallGenerator getOrCreateCallGenerator(ResolvedCall<?> resolvedCall, CallableDescriptor callableDescriptor) {
        if (resolvedCall == null) {
            $$$reportNull$$$0(180);
        }
        if (callableDescriptor == null) {
            $$$reportNull$$$0(181);
        }
        return getOrCreateCallGenerator(callableDescriptor, resolvedCall.getCall().getCallElement(), new TypeParameterMappings<>(this.typeSystem, getTypeArgumentsForResolvedCall(resolvedCall, callableDescriptor), InlineUtil.isArrayConstructorWithLambda(resolvedCall.getResultingDescriptor()), new Function2() { // from class: org.jetbrains.kotlin.codegen.ExpressionCodegen$$ExternalSyntheticLambda31
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return this.f$0.m6403x23e660ae((KotlinType) obj, (BothSignatureWriter) obj2);
            }
        }), false);
    }

    /* JADX INFO: renamed from: lambda$getOrCreateCallGenerator$15$org-jetbrains-kotlin-codegen-ExpressionCodegen, reason: not valid java name */
    /* synthetic */ Type m6403x23e660ae(KotlinType kotlinType, BothSignatureWriter bothSignatureWriter) {
        return this.typeMapper.mapTypeArgument(approximateCapturedType(kotlinType), bothSignatureWriter);
    }

    private KotlinType approximateCapturedType(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(182);
        }
        if (this.state.getLanguageVersionSettings().supportsFeature(LanguageFeature.NewInference)) {
            KotlinType kotlinType2 = TypeUtils.contains(kotlinType, (Function1<UnwrappedType, Boolean>) new Function1() { // from class: org.jetbrains.kotlin.codegen.ExpressionCodegen$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return Boolean.valueOf(CapturedTypeConstructorKt.isCaptured((UnwrappedType) obj));
                }
            }) ? (KotlinType) new TypeApproximator(this.state.getModule().getBuiltIns(), this.state.getLanguageVersionSettings()).approximateToSuperType(kotlinType, TypeApproximatorConfiguration.InternalTypesApproximation.INSTANCE) : null;
            if (kotlinType2 != null) {
                kotlinType = kotlinType2;
            }
            if (kotlinType == null) {
                $$$reportNull$$$0(183);
            }
            return kotlinType;
        }
        KotlinType upper = CapturedTypeApproximationKt.approximateCapturedTypes(kotlinType).getUpper();
        if (upper == null) {
            $$$reportNull$$$0(184);
        }
        return upper;
    }

    private static Map<TypeParameterDescriptor, KotlinType> getTypeArgumentsForResolvedCall(ResolvedCall<?> resolvedCall, CallableDescriptor callableDescriptor) {
        if (resolvedCall == null) {
            $$$reportNull$$$0(185);
        }
        if (callableDescriptor == null) {
            $$$reportNull$$$0(186);
        }
        if (!(callableDescriptor instanceof TypeAliasConstructorDescriptor)) {
            Map<TypeParameterDescriptor, KotlinType> typeArguments = resolvedCall.getTypeArguments();
            if (typeArguments == null) {
                $$$reportNull$$$0(187);
            }
            return typeArguments;
        }
        TypeAliasConstructorDescriptor typeAliasConstructorDescriptor = (TypeAliasConstructorDescriptor) callableDescriptor;
        ClassConstructorDescriptor underlyingConstructorDescriptor = typeAliasConstructorDescriptor.getUnderlyingConstructorDescriptor();
        List<TypeProjection> arguments = typeAliasConstructorDescriptor.getReturnType().getArguments();
        List<TypeParameterDescriptor> typeParameters = underlyingConstructorDescriptor.getTypeParameters();
        HashMap mapNewHashMapWithExpectedSize = Maps.newHashMapWithExpectedSize(typeParameters.size());
        for (TypeParameterDescriptor typeParameterDescriptor : typeParameters) {
            mapNewHashMapWithExpectedSize.put(typeParameterDescriptor, arguments.get(typeParameterDescriptor.getIndex()).getNullableAnyType());
        }
        if (mapNewHashMapWithExpectedSize == null) {
            $$$reportNull$$$0(188);
        }
        return mapNewHashMapWithExpectedSize;
    }

    public StackValue generateReceiverValue(ReceiverValue receiverValue, boolean z) {
        if (receiverValue instanceof ImplicitClassReceiver) {
            ClassDescriptor declarationDescriptor = ((ImplicitClassReceiver) receiverValue).getDeclarationDescriptor();
            StackValue stackValueGenerateInstanceReceiver = generateInstanceReceiver(declarationDescriptor, z, (receiverValue instanceof CastImplicitClassReceiver) || DescriptorUtils.isEnumEntry(declarationDescriptor));
            if (stackValueGenerateInstanceReceiver == null) {
                $$$reportNull$$$0(189);
            }
            return stackValueGenerateInstanceReceiver;
        }
        if (receiverValue instanceof ExtensionReceiver) {
            StackValue stackValueGenerateExtensionReceiver = generateExtensionReceiver(((ExtensionReceiver) receiverValue).getDeclarationDescriptor());
            if (stackValueGenerateExtensionReceiver == null) {
                $$$reportNull$$$0(190);
            }
            return stackValueGenerateExtensionReceiver;
        }
        if (receiverValue instanceof ExpressionReceiver) {
            ExpressionReceiver expressionReceiver = (ExpressionReceiver) receiverValue;
            StackValue stackValueGen = gen(expressionReceiver.getExpression());
            if (!this.state.getConfig().getIsReceiverAssertionsDisabled()) {
                stackValueGen = DescriptorAsmUtil.genNotNullAssertions(this.state, stackValueGen, (RuntimeAssertionInfo) this.bindingContext.get(JvmBindingContextSlices.RECEIVER_RUNTIME_ASSERTION_INFO, expressionReceiver));
            }
            if (stackValueGen == null) {
                $$$reportNull$$$0(191);
            }
            return stackValueGen;
        }
        throw new UnsupportedOperationException("Unsupported receiver value: " + receiverValue);
    }

    private StackValue generateInstanceReceiver(ClassDescriptor classDescriptor, boolean z, boolean z2) {
        if (classDescriptor == null) {
            $$$reportNull$$$0(192);
        }
        if (DescriptorUtils.isCompanionObject(classDescriptor)) {
            CallableMemberDescriptor contextDescriptor = this.context.getContextDescriptor();
            if ((contextDescriptor instanceof FunctionDescriptor) && classDescriptor == contextDescriptor.getContainingDeclaration()) {
                return StackValue.LOCAL_0;
            }
            if (CodegenUtilKt.isPossiblyUninitializedSingleton(classDescriptor) && isInsideSingleton(classDescriptor)) {
                return generateThisOrOuterFromContext(classDescriptor, false, false);
            }
            return generateCompanionObjectInstance(classDescriptor);
        }
        if (classDescriptor instanceof ScriptDescriptor) {
            StackValue stackValueGenerateScriptReceiver = generateScriptReceiver((ScriptDescriptor) classDescriptor);
            return stackValueGenerateScriptReceiver != null ? stackValueGenerateScriptReceiver : StackValue.thisOrOuter(this, classDescriptor, z, z2);
        }
        return StackValue.thisOrOuter(this, classDescriptor, z, z2);
    }

    private CodegenContext getContextForCompanionObjectAccessorIfRequiredOrNull(ClassDescriptor classDescriptor, CodegenContext codegenContext) {
        if (classDescriptor == null) {
            $$$reportNull$$$0(193);
        }
        if (codegenContext == null) {
            $$$reportNull$$$0(194);
        }
        if (JvmCodegenUtil.isDebuggerContext(codegenContext) || !this.state.getLanguageVersionSettings().supportsFeature(LanguageFeature.ProperVisibilityForCompanionObjectInstanceField)) {
            return null;
        }
        int visibilityAccessFlag = DescriptorAsmUtil.getVisibilityAccessFlag(classDescriptor);
        CodegenContext firstCrossInlineOrNonInlineContext = codegenContext.getFirstCrossInlineOrNonInlineContext();
        boolean zIsInlineMethodContext = firstCrossInlineOrNonInlineContext.isInlineMethodContext();
        DeclarationDescriptor contextDescriptor = firstCrossInlineOrNonInlineContext.getContextDescriptor();
        ClassDescriptor containingClass = DescriptorUtils.getContainingClass(classDescriptor);
        if ((visibilityAccessFlag & 2) != 0) {
            if (zIsInlineMethodContext || contextDescriptor.getContainingDeclaration() != containingClass) {
                return firstCrossInlineOrNonInlineContext.findParentContextWithDescriptor(containingClass);
            }
            return null;
        }
        if ((visibilityAccessFlag & 4) == 0) {
            return null;
        }
        if (!zIsInlineMethodContext && canAccessProtectedMembers(contextDescriptor, containingClass)) {
            return null;
        }
        CodegenContext parentContextSubclassOf = getParentContextSubclassOf(containingClass, firstCrossInlineOrNonInlineContext);
        return parentContextSubclassOf == null ? getCompanionObjectContextSubclassOf(containingClass, firstCrossInlineOrNonInlineContext) : parentContextSubclassOf;
    }

    private static boolean canAccessProtectedMembers(DeclarationDescriptor declarationDescriptor, ClassDescriptor classDescriptor) {
        DeclarationDescriptor containingDeclaration = declarationDescriptor.getContainingDeclaration();
        return containingDeclaration == classDescriptor || JvmCodegenUtil.isInSamePackage(declarationDescriptor, classDescriptor) || ((containingDeclaration instanceof ClassDescriptor) && DescriptorUtils.isSubclass((ClassDescriptor) containingDeclaration, classDescriptor));
    }

    public StackValue generateExtensionReceiver(CallableDescriptor callableDescriptor) {
        if (callableDescriptor == null) {
            $$$reportNull$$$0(195);
        }
        ReceiverParameterDescriptor extensionReceiverParameter = callableDescriptor.getExtensionReceiverParameter();
        if (this.myFrameMap.getIndex(extensionReceiverParameter) != -1) {
            KotlinType returnType = extensionReceiverParameter.getReturnType();
            StackValue.Local local = StackValue.local(this.myFrameMap.getIndex(extensionReceiverParameter), this.typeMapper.mapType(returnType), returnType);
            if (local == null) {
                $$$reportNull$$$0(196);
            }
            return local;
        }
        StackValue stackValueGenerateReceiver = this.context.generateReceiver(callableDescriptor, this.state, false);
        if (stackValueGenerateReceiver == null) {
            throw new NullPointerException("Extension receiver was null for callable " + callableDescriptor);
        }
        if (stackValueGenerateReceiver == null) {
            $$$reportNull$$$0(197);
        }
        return stackValueGenerateReceiver;
    }

    private StackValue generateScriptReceiver(ScriptDescriptor scriptDescriptor) {
        if (scriptDescriptor == null) {
            $$$reportNull$$$0(198);
        }
        CodegenContext parentContext = this.context;
        boolean z = parentContext instanceof ConstructorContext;
        StackValue outerExpression = StackValue.LOCAL_0;
        while (parentContext != null) {
            if (!z) {
                parentContext = getNotNullParentContextForMethod(parentContext);
            }
            if (parentContext instanceof ScriptContext) {
                ScriptContext scriptContext = (ScriptContext) parentContext;
                if (scriptContext.getScriptDescriptor() == scriptDescriptor) {
                    return outerExpression;
                }
                Type typeMapType = this.typeMapper.mapType(scriptContext.getScriptDescriptor());
                SimpleType defaultType = scriptDescriptor.getDefaultType();
                return StackValue.field(this.typeMapper.mapType(defaultType), defaultType, typeMapType, scriptContext.getScriptFieldName(scriptDescriptor), false, outerExpression, scriptDescriptor);
            }
            outerExpression = parentContext.getOuterExpression(outerExpression, true);
            if (z) {
                parentContext = getNotNullParentContextForMethod(parentContext);
                z = false;
            }
            parentContext = parentContext.getParentContext();
        }
        return outerExpression;
    }

    public StackValue generateThisOrOuter(ClassDescriptor classDescriptor, boolean z) {
        if (classDescriptor == null) {
            $$$reportNull$$$0(199);
        }
        StackValue stackValueGenerateThisOrOuter = generateThisOrOuter(classDescriptor, z, false);
        if (stackValueGenerateThisOrOuter == null) {
            $$$reportNull$$$0(200);
        }
        return stackValueGenerateThisOrOuter;
    }

    private boolean isInsideSingleton(ClassDescriptor classDescriptor) {
        if (classDescriptor == null) {
            $$$reportNull$$$0(201);
        }
        for (DeclarationDescriptor contextDescriptor = this.context.getContextDescriptor(); contextDescriptor != null; contextDescriptor = contextDescriptor.getContainingDeclaration()) {
            if (contextDescriptor == classDescriptor) {
                return true;
            }
            if ((contextDescriptor instanceof ClassDescriptor) && !((ClassDescriptor) contextDescriptor).getIsInner() && !DescriptorUtils.isAnonymousObject(contextDescriptor)) {
                return false;
            }
        }
        return false;
    }

    public StackValue generateThisOrOuter(ClassDescriptor classDescriptor, boolean z, boolean z2) {
        if (classDescriptor == null) {
            $$$reportNull$$$0(202);
        }
        if (!classDescriptor.getKind().isSingleton()) {
            StackValue stackValueGenerateThisOrOuterFromContext = generateThisOrOuterFromContext(classDescriptor, z, z2);
            if (stackValueGenerateThisOrOuterFromContext == null) {
                $$$reportNull$$$0(203);
            }
            return stackValueGenerateThisOrOuterFromContext;
        }
        if (classDescriptor.equals(this.context.getThisDescriptor()) && !CodegenUtilKt.isJvmStaticInObjectOrClassOrInterface(this.context.getFunctionDescriptor())) {
            StackValue.Local local = StackValue.local(0, this.typeMapper.mapType(classDescriptor));
            if (local == null) {
                $$$reportNull$$$0(204);
            }
            return local;
        }
        if (shouldGenerateSingletonAsThisOrOuterFromContext(classDescriptor)) {
            StackValue stackValueGenerateThisOrOuterFromContext2 = generateThisOrOuterFromContext(classDescriptor, z, z2);
            if (stackValueGenerateThisOrOuterFromContext2 == null) {
                $$$reportNull$$$0(205);
            }
            return stackValueGenerateThisOrOuterFromContext2;
        }
        if (DescriptorUtils.isEnumEntry(classDescriptor)) {
            StackValue.Field fieldEnumEntry = StackValue.enumEntry(classDescriptor, this.typeMapper);
            if (fieldEnumEntry == null) {
                $$$reportNull$$$0(206);
            }
            return fieldEnumEntry;
        }
        StackValue.Field fieldSingleton = StackValue.singleton(classDescriptor, this.typeMapper);
        if (fieldSingleton == null) {
            $$$reportNull$$$0(207);
        }
        return fieldSingleton;
    }

    private StackValue generateThisOrOuterFromContext(ClassDescriptor classDescriptor, boolean z, boolean z2) {
        MutableClosure mutableClosure;
        StackValue stackValueCaptureVariable;
        if (classDescriptor == null) {
            $$$reportNull$$$0(208);
        }
        CodegenContext enclosingClassContext = this.context;
        SimpleType defaultType = enclosingClassContext.getThisDescriptor().getDefaultType();
        StackValue stackValueLocal = StackValue.local(0, asmType(defaultType), defaultType);
        boolean z3 = enclosingClassContext instanceof ConstructorContext;
        while (enclosingClassContext != null) {
            ClassDescriptor thisDescriptor = enclosingClassContext.getThisDescriptor();
            if (!z && thisDescriptor.getTypeConstructor().equals(classDescriptor.getTypeConstructor())) {
                return stackValueLocal;
            }
            if (!z2 && z && DescriptorUtils.isSubclass(thisDescriptor, classDescriptor)) {
                return castToRequiredTypeOfInterfaceIfNeeded(stackValueLocal, thisDescriptor, classDescriptor);
            }
            if (z3) {
                StackValue outerExpression = enclosingClassContext.getOuterExpression(stackValueLocal, false);
                enclosingClassContext = getNotNullParentContextForMethod(enclosingClassContext).getEnclosingClassContext();
                stackValueLocal = outerExpression;
                z3 = false;
            } else {
                CodegenContext notNullParentContextForMethod = getNotNullParentContextForMethod(enclosingClassContext);
                enclosingClassContext = notNullParentContextForMethod.getEnclosingClassContext();
                if (!(classDescriptor instanceof ScriptDescriptor)) {
                    if (notNullParentContextForMethod instanceof ScriptLikeContext) {
                        return ((ScriptLikeContext) notNullParentContextForMethod).getOuterReceiverExpression(stackValueLocal, classDescriptor);
                    }
                    if ((enclosingClassContext instanceof ScriptLikeContext) && (mutableClosure = notNullParentContextForMethod.closure) != null && (stackValueCaptureVariable = ((ScriptLikeContext) enclosingClassContext).captureVariable(mutableClosure, classDescriptor)) != null) {
                        return stackValueCaptureVariable;
                    }
                }
                stackValueLocal = notNullParentContextForMethod.getOuterExpression(stackValueLocal, false);
            }
            z2 = false;
        }
        throw new UnsupportedOperationException();
    }

    private static CodegenContext getNotNullParentContextForMethod(CodegenContext codegenContext) {
        if (codegenContext instanceof MethodContext) {
            codegenContext = codegenContext.getParentContext();
        }
        if (codegenContext == null) {
            $$$reportNull$$$0(209);
        }
        return codegenContext;
    }

    private boolean canSkipArrayCopyForSpreadArgument(KtExpression ktExpression) {
        ResolvedCall<? extends CallableDescriptor> resolvedCall = CallUtilKt.getResolvedCall(ktExpression, this.bindingContext);
        if (resolvedCall == null) {
            return false;
        }
        CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
        return (resultingDescriptor instanceof ConstructorDescriptor) || CompileTimeConstantUtils.isArrayFunctionCall(resolvedCall) || DescriptorUtils.getFqName(resultingDescriptor).asString().equals("kotlin.arrayOfNulls");
    }

    public StackValue genVarargs(VarargValueArgument varargValueArgument, final KotlinType kotlinType) {
        boolean zAnyMatch;
        String str;
        String str2;
        String str3;
        if (varargValueArgument == null) {
            $$$reportNull$$$0(210);
        }
        if (kotlinType == null) {
            $$$reportNull$$$0(211);
        }
        final Type typeAsmType = asmType(kotlinType);
        final Type typeCorrectElementType = AsmUtil.correctElementType(typeAsmType);
        final List<ValueArgument> arguments = varargValueArgument.getArguments();
        final int size = arguments.size();
        if (this.state.getLanguageVersionSettings().supportsFeature(LanguageFeature.AllowAssigningArrayElementsToVarargsInNamedFormForFunctions)) {
            zAnyMatch = arguments.stream().anyMatch(new Predicate() { // from class: org.jetbrains.kotlin.codegen.ExpressionCodegen$$ExternalSyntheticLambda33
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return ExpressionCodegen.lambda$genVarargs$17((ValueArgument) obj);
                }
            });
        } else {
            zAnyMatch = arguments.stream().anyMatch(new Predicate() { // from class: org.jetbrains.kotlin.codegen.ExpressionCodegen$$ExternalSyntheticLambda37
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return ExpressionCodegen.lambda$genVarargs$18((ValueArgument) obj);
                }
            });
        }
        if (zAnyMatch) {
            final boolean zIsArray = KotlinBuiltIns.isArray(kotlinType);
            if (size == 1) {
                if (zIsArray) {
                    typeCorrectElementType = AsmTypes.OBJECT_TYPE;
                }
                final Type arrayType = AsmUtil.getArrayType(typeCorrectElementType);
                StackValue stackValueOperation = StackValue.operation(typeAsmType, kotlinType, new Function1() { // from class: org.jetbrains.kotlin.codegen.ExpressionCodegen$$ExternalSyntheticLambda38
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return this.f$0.m6387xe190c71e(arguments, typeAsmType, kotlinType, arrayType, zIsArray, (InstructionAdapter) obj);
                    }
                });
                if (stackValueOperation == null) {
                    $$$reportNull$$$0(212);
                }
                return stackValueOperation;
            }
            if (zIsArray) {
                str = "kotlin/jvm/internal/SpreadBuilder";
                str2 = ReifiedTypeInliner.pluginIntrinsicsMarkerSignature;
                str3 = "([Ljava/lang/Object;)[Ljava/lang/Object;";
            } else {
                str = "kotlin/jvm/internal/" + (AsmUtil.asmPrimitiveTypeToLangPrimitiveType(typeCorrectElementType).getTypeName().getIdentifier() + "SpreadBuilder");
                str2 = "(" + typeCorrectElementType.getDescriptor() + ")V";
                str3 = "()" + typeAsmType.getDescriptor();
            }
            final String str4 = str2;
            final String str5 = str3;
            final String str6 = str;
            StackValue stackValueOperation2 = StackValue.operation(typeAsmType, new Function1() { // from class: org.jetbrains.kotlin.codegen.ExpressionCodegen$$ExternalSyntheticLambda39
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return this.f$0.m6388xd8fab048(str6, size, arguments, typeCorrectElementType, str4, zIsArray, kotlinType, str5, typeAsmType, (InstructionAdapter) obj);
                }
            });
            if (stackValueOperation2 == null) {
                $$$reportNull$$$0(213);
            }
            return stackValueOperation2;
        }
        StackValue stackValueOperation3 = StackValue.operation(typeAsmType, kotlinType, new Function1() { // from class: org.jetbrains.kotlin.codegen.ExpressionCodegen$$ExternalSyntheticLambda40
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return this.f$0.m6389x12c55227(arguments, kotlinType, size, typeCorrectElementType, typeAsmType, (InstructionAdapter) obj);
            }
        });
        if (stackValueOperation3 == null) {
            $$$reportNull$$$0(214);
        }
        return stackValueOperation3;
    }

    static /* synthetic */ boolean lambda$genVarargs$17(ValueArgument valueArgument) {
        return valueArgument.getSpreadElement() != null || valueArgument.isNamed();
    }

    static /* synthetic */ boolean lambda$genVarargs$18(ValueArgument valueArgument) {
        return valueArgument.getSpreadElement() != null;
    }

    /* JADX INFO: renamed from: lambda$genVarargs$19$org-jetbrains-kotlin-codegen-ExpressionCodegen, reason: not valid java name */
    /* synthetic */ Unit m6387xe190c71e(List list, Type type, KotlinType kotlinType, Type type2, boolean z, InstructionAdapter instructionAdapter) {
        KtExpression argumentExpression = ((ValueArgument) list.get(0)).getArgumentExpression();
        gen(argumentExpression, type, kotlinType);
        if (!canSkipArrayCopyForSpreadArgument(argumentExpression)) {
            this.v.dup();
            this.v.arraylength();
            this.v.invokestatic("java/util/Arrays", "copyOf", Type.getMethodDescriptor(type2, type2, Type.INT_TYPE), false);
        }
        if (z) {
            this.v.checkcast(type);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: lambda$genVarargs$20$org-jetbrains-kotlin-codegen-ExpressionCodegen, reason: not valid java name */
    /* synthetic */ Unit m6388xd8fab048(String str, int i, List list, Type type, String str2, boolean z, KotlinType kotlinType, String str3, Type type2, InstructionAdapter instructionAdapter) {
        this.v.anew(Type.getObjectType(str));
        this.v.dup();
        this.v.iconst(i);
        this.v.invokespecial(str, "<init>", "(I)V", false);
        for (int i2 = 0; i2 != i; i2++) {
            this.v.dup();
            ValueArgument valueArgument = (ValueArgument) list.get(i2);
            KtExpression argumentExpression = valueArgument.getArgumentExpression();
            KotlinType kotlinType2 = kotlinType(argumentExpression);
            if (valueArgument.getSpreadElement() != null) {
                gen(argumentExpression, AsmTypes.OBJECT_TYPE, kotlinType2);
                if (kotlinType2 != null && InlineClassesUtilsKt.isInlineClassType(kotlinType2)) {
                    StackValue.coerce(AsmTypes.OBJECT_TYPE, kotlinType2, asmType(kotlinType2), kotlinType2, this.v);
                }
                this.v.invokevirtual(str, "addSpread", ReifiedTypeInliner.pluginIntrinsicsMarkerSignature, false);
            } else {
                gen(argumentExpression, type, kotlinType2);
                this.v.invokevirtual(str, ProductAction.ACTION_ADD, str2, false);
            }
        }
        if (!z) {
            this.v.invokevirtual(str, "toArray", str3, false);
        } else {
            this.v.dup();
            this.v.invokevirtual(str, "size", "()I", false);
            newArrayInstruction(kotlinType);
            this.v.invokevirtual(str, "toArray", str3, false);
            this.v.checkcast(type2);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: lambda$genVarargs$21$org-jetbrains-kotlin-codegen-ExpressionCodegen, reason: not valid java name */
    /* synthetic */ Unit m6389x12c55227(List list, KotlinType kotlinType, int i, Type type, Type type2, InstructionAdapter instructionAdapter) {
        this.v.iconst(list.size());
        newArrayInstruction(kotlinType);
        KotlinType arrayElementType = kotlinType.getConstructor().getBuiltIns().getArrayElementType(kotlinType);
        for (int i2 = 0; i2 != i; i2++) {
            this.v.dup();
            StackValue.arrayElement(type, arrayElementType, StackValue.onStack(type2, kotlinType), StackValue.constant(i2)).store(gen(((ValueArgument) list.get(i2)).getArgumentExpression()), this.v);
        }
        return Unit.INSTANCE;
    }

    public int indexOfLocalNotDelegated(KtReferenceExpression ktReferenceExpression) {
        DeclarationDescriptor declarationDescriptor = (DeclarationDescriptor) this.bindingContext.get(BindingContext.REFERENCE_TARGET, ktReferenceExpression);
        if (BindingContextUtils.isBoxedLocalCapturedInClosure(this.bindingContext, declarationDescriptor)) {
            return -1;
        }
        if ((declarationDescriptor instanceof LocalVariableDescriptor) && ((LocalVariableDescriptor) declarationDescriptor).getIsDelegated()) {
            return -1;
        }
        return lookupLocalIndex(declarationDescriptor);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public StackValue visitClassLiteralExpression(KtClassLiteralExpression ktClassLiteralExpression, StackValue stackValue) {
        if (ktClassLiteralExpression == null) {
            $$$reportNull$$$0(215);
        }
        KtExpression receiverExpression = ktClassLiteralExpression.getReceiverExpression();
        return generateClassLiteralReference((DoubleColonLHS) this.bindingContext.get(BindingContext.DOUBLE_COLON_LHS, receiverExpression), receiverExpression, true);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public StackValue visitCallableReferenceExpression(KtCallableReferenceExpression ktCallableReferenceExpression, StackValue stackValue) {
        if (ktCallableReferenceExpression == null) {
            $$$reportNull$$$0(216);
        }
        ResolvedCall<? extends CallableDescriptor> resolvedCallWithAssert = CallUtilKt.getResolvedCallWithAssert(ktCallableReferenceExpression.getCallableReference(), this.bindingContext);
        StackValue stackValueGenerateCallableReferenceReceiver = generateCallableReferenceReceiver(resolvedCallWithAssert);
        FunctionDescriptor functionDescriptor = (FunctionDescriptor) this.bindingContext.get(BindingContext.FUNCTION, ktCallableReferenceExpression);
        if (functionDescriptor != null) {
            return genClosure(ktCallableReferenceExpression, functionDescriptor, new FunctionReferenceGenerationStrategy(this.state, functionDescriptor, resolvedCallWithAssert, stackValueGenerateCallableReferenceReceiver != null ? new JvmKotlinType(stackValueGenerateCallableReferenceReceiver.type, stackValueGenerateCallableReferenceReceiver.kotlinType) : null, null, false), null, resolvedCallWithAssert, stackValueGenerateCallableReferenceReceiver);
        }
        return generatePropertyReference(ktCallableReferenceExpression, getVariableDescriptorNotNull(ktCallableReferenceExpression), (VariableDescriptor) resolvedCallWithAssert.getResultingDescriptor(), stackValueGenerateCallableReferenceReceiver);
    }

    public StackValue generateCallableReferenceReceiver(ResolvedCall<?> resolvedCall) {
        if (resolvedCall == null) {
            $$$reportNull$$$0(217);
        }
        ReceiverValue boundCallableReferenceReceiver = JvmCodegenUtil.getBoundCallableReferenceReceiver(resolvedCall);
        if (boundCallableReferenceReceiver == null) {
            return null;
        }
        KotlinType type = boundCallableReferenceReceiver.getType();
        return StackValue.coercion(generateReceiverValue(boundCallableReferenceReceiver, false), asmType(type), type);
    }

    private StackValue generatePropertyReference(KtElement ktElement, VariableDescriptor variableDescriptor, VariableDescriptor variableDescriptor2, StackValue stackValue) {
        if (ktElement == null) {
            $$$reportNull$$$0(218);
        }
        if (variableDescriptor == null) {
            $$$reportNull$$$0(219);
        }
        if (variableDescriptor2 == null) {
            $$$reportNull$$$0(220);
        }
        ClassDescriptor classDescriptor = (ClassDescriptor) this.bindingContext.get(CodegenBinding.CLASS_FOR_CALLABLE, variableDescriptor);
        if (classDescriptor == null) {
            throw new IllegalStateException("Property reference class was not found: " + variableDescriptor + "\nTried to generate: " + ktElement.getText());
        }
        PropertyReferenceCodegen propertyReferenceCodegen = new PropertyReferenceCodegen(this.state, this.parentCodegen, this.context.intoAnonymousClass(classDescriptor, this, OwnerKind.IMPLEMENTATION), ktElement, this.state.getFactory().newVisitor(JvmDeclarationOriginKt.OtherOrigin(ktElement), this.typeMapper.mapClass(classDescriptor), ktElement.getContainingFile()), variableDescriptor, variableDescriptor2, stackValue != null ? new JvmKotlinType(stackValue.type, stackValue.kotlinType) : null);
        propertyReferenceCodegen.generate();
        StackValue stackValuePutInstanceOnStack = propertyReferenceCodegen.putInstanceOnStack(stackValue);
        if (stackValuePutInstanceOnStack == null) {
            $$$reportNull$$$0(221);
        }
        return stackValuePutInstanceOnStack;
    }

    public StackValue generateClassLiteralReference(final DoubleColonLHS doubleColonLHS, final KtExpression ktExpression, final boolean z) {
        if (doubleColonLHS == null) {
            $$$reportNull$$$0(222);
        }
        StackValue stackValueOperation = StackValue.operation(z ? AsmTypes.K_CLASS_TYPE : AsmTypes.JAVA_CLASS_TYPE, new Function1() { // from class: org.jetbrains.kotlin.codegen.ExpressionCodegen$$ExternalSyntheticLambda29
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return this.f$0.m6394xdf219c17(doubleColonLHS, ktExpression, z, (InstructionAdapter) obj);
            }
        });
        if (stackValueOperation == null) {
            $$$reportNull$$$0(223);
        }
        return stackValueOperation;
    }

    /* JADX INFO: renamed from: lambda$generateClassLiteralReference$22$org-jetbrains-kotlin-codegen-ExpressionCodegen, reason: not valid java name */
    /* synthetic */ Unit m6394xdf219c17(DoubleColonLHS doubleColonLHS, KtExpression ktExpression, boolean z, InstructionAdapter instructionAdapter) {
        KotlinType type = doubleColonLHS.getType();
        if ((doubleColonLHS instanceof DoubleColonLHS.Expression) && !((DoubleColonLHS.Expression) doubleColonLHS).getIsObjectQualifier()) {
            JavaClassProperty.INSTANCE.generateImpl(instructionAdapter, gen(ktExpression));
        } else {
            if (TypeUtils.isTypeParameter(type)) {
                BaseExpressionCodegenKt.putReifiedOperationMarkerIfTypeIsReifiedParameter(this, type, ReifiedTypeInliner.OperationKind.JAVA_CLASS);
            }
            DescriptorAsmUtil.putJavaLangClassInstance(instructionAdapter, this.typeMapper.mapType(type), type, this.typeMapper);
        }
        if (z) {
            AsmUtil.wrapJavaClassIntoKClass(instructionAdapter);
        }
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public StackValue visitDotQualifiedExpression(KtDotQualifiedExpression ktDotQualifiedExpression, StackValue stackValue) {
        if (ktDotQualifiedExpression == null) {
            $$$reportNull$$$0(224);
        }
        return genQualified(StackValue.none(), ktDotQualifiedExpression.getSelectorExpression());
    }

    private StackValue generateExpressionWithNullFallback(KtExpression ktExpression, Label label) {
        if (ktExpression == null) {
            $$$reportNull$$$0(225);
        }
        if (label == null) {
            $$$reportNull$$$0(226);
        }
        KtExpression ktExpressionDeparenthesize = KtPsiUtil.deparenthesize(ktExpression);
        Type typeExpressionType = expressionType(ktExpressionDeparenthesize);
        KotlinType kotlinType = kotlinType(ktExpressionDeparenthesize);
        if ((ktExpressionDeparenthesize instanceof KtSafeQualifiedExpression) && !AsmUtil.isPrimitive(typeExpressionType)) {
            return StackValue.coercion(generateSafeQualifiedExpression((KtSafeQualifiedExpression) ktExpressionDeparenthesize, label), typeExpressionType, kotlinType);
        }
        return genLazy(ktExpressionDeparenthesize, typeExpressionType, kotlinType);
    }

    private StackValue generateSafeQualifiedExpression(KtSafeQualifiedExpression ktSafeQualifiedExpression, Label label) {
        if (ktSafeQualifiedExpression == null) {
            $$$reportNull$$$0(XKeySym.XK_atilde);
        }
        if (label == null) {
            $$$reportNull$$$0(XKeySym.XK_adiaeresis);
        }
        KtExpression receiverExpression = ktSafeQualifiedExpression.getReceiverExpression();
        KtExpression selectorExpression = ktSafeQualifiedExpression.getSelectorExpression();
        Type typeExpressionType = expressionType(receiverExpression);
        KotlinType kotlinType = kotlinType(receiverExpression);
        StackValue stackValueGenerateExpressionWithNullFallback = generateExpressionWithNullFallback(receiverExpression, label);
        if (AsmUtil.isPrimitive(typeExpressionType)) {
            label = null;
        }
        return genQualified(new StackValue.SafeCall(typeExpressionType, kotlinType, stackValueGenerateExpressionWithNullFallback, label), selectorExpression);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public StackValue visitSafeQualifiedExpression(KtSafeQualifiedExpression ktSafeQualifiedExpression, StackValue stackValue) {
        if (ktSafeQualifiedExpression == null) {
            $$$reportNull$$$0(XKeySym.XK_aring);
        }
        Label label = new Label();
        Type typeBoxType = AsmUtil.boxType(expressionType(ktSafeQualifiedExpression));
        KotlinType kotlinType = kotlinType(ktSafeQualifiedExpression);
        StackValue stackValueCoercion = StackValue.coercion(generateSafeQualifiedExpression(ktSafeQualifiedExpression, label), typeBoxType, kotlinType);
        return !AsmUtil.isPrimitive(expressionType(ktSafeQualifiedExpression.getReceiverExpression())) ? new StackValue.SafeFallback(typeBoxType, kotlinType, label, stackValueCoercion) : stackValueCoercion;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public StackValue visitBinaryExpression(KtBinaryExpression ktBinaryExpression, StackValue stackValue) {
        if (ktBinaryExpression == null) {
            $$$reportNull$$$0(XKeySym.XK_ae);
        }
        if (stackValue == null) {
            $$$reportNull$$$0(XKeySym.XK_ccedilla);
        }
        KtOperationReferenceExpression operationReference = ktBinaryExpression.getOperationReference();
        IElementType referencedNameElementType = operationReference.getReferencedNameElementType();
        if (referencedNameElementType == KtTokens.EQ) {
            return generateAssignmentExpression(ktBinaryExpression);
        }
        if (KtTokens.AUGMENTED_ASSIGNMENTS.contains(referencedNameElementType)) {
            return generateAugmentedAssignment(ktBinaryExpression);
        }
        if (referencedNameElementType == KtTokens.ANDAND) {
            return generateBooleanAnd(ktBinaryExpression);
        }
        if (referencedNameElementType == KtTokens.OROR) {
            return generateBooleanOr(ktBinaryExpression);
        }
        if (referencedNameElementType == KtTokens.EQEQ || referencedNameElementType == KtTokens.EXCLEQ || referencedNameElementType == KtTokens.EQEQEQ || referencedNameElementType == KtTokens.EXCLEQEQEQ) {
            return generateEquals(ktBinaryExpression.getLeft(), ktBinaryExpression.getRight(), referencedNameElementType, null, null, (PrimitiveNumericComparisonInfo) this.bindingContext.get(BindingContext.PRIMITIVE_NUMERIC_COMPARISON_INFO, ktBinaryExpression));
        }
        if (referencedNameElementType == KtTokens.LT || referencedNameElementType == KtTokens.LTEQ || referencedNameElementType == KtTokens.GT || referencedNameElementType == KtTokens.GTEQ) {
            return generateComparison(ktBinaryExpression, stackValue);
        }
        if (referencedNameElementType == KtTokens.ELVIS) {
            return generateElvis(ktBinaryExpression);
        }
        if (referencedNameElementType == KtTokens.IN_KEYWORD || referencedNameElementType == KtTokens.NOT_IN) {
            return generateIn(StackValue.expression(expressionType(ktBinaryExpression.getLeft()), ktBinaryExpression.getLeft(), this), ktBinaryExpression.getRight(), operationReference);
        }
        ConstantValue<?> primitiveOrStringCompileTimeConstant = getPrimitiveOrStringCompileTimeConstant(ktBinaryExpression);
        if (primitiveOrStringCompileTimeConstant != null) {
            return StackValue.constant(primitiveOrStringCompileTimeConstant.getValue(), expressionType(ktBinaryExpression));
        }
        ResolvedCall<? extends CallableDescriptor> resolvedCallWithAssert = CallUtilKt.getResolvedCallWithAssert(ktBinaryExpression, this.bindingContext);
        if (((FunctionDescriptor) resolvedCallWithAssert.getResultingDescriptor()) instanceof ConstructorDescriptor) {
            return generateConstructorCall(resolvedCallWithAssert, expressionType(ktBinaryExpression));
        }
        return invokeFunction(resolvedCallWithAssert, stackValue);
    }

    private StackValue generateIn(StackValue stackValue, KtExpression ktExpression, KtSimpleNameExpression ktSimpleNameExpression) {
        return RangeValuesKt.createRangeValueForExpression(this, KtPsiUtil.deparenthesize(ktExpression)).createInExpressionGenerator(this, ktSimpleNameExpression).generate(stackValue);
    }

    private StackValue generateBooleanAnd(KtBinaryExpression ktBinaryExpression) {
        return StackValue.and(gen(ktBinaryExpression.getLeft()), gen(ktBinaryExpression.getRight()));
    }

    private StackValue generateBooleanOr(KtBinaryExpression ktBinaryExpression) {
        return StackValue.or(gen(ktBinaryExpression.getLeft()), gen(ktBinaryExpression.getRight()));
    }

    private StackValue genLazyUnlessProvided(StackValue stackValue, KtExpression ktExpression, Type type) {
        if (ktExpression == null) {
            $$$reportNull$$$0(XKeySym.XK_egrave);
        }
        if (type == null) {
            $$$reportNull$$$0(XKeySym.XK_eacute);
        }
        return genLazyUnlessProvided(stackValue, ktExpression, type, null);
    }

    private StackValue genLazyUnlessProvided(StackValue stackValue, KtExpression ktExpression, Type type, KotlinType kotlinType) {
        if (ktExpression == null) {
            $$$reportNull$$$0(XKeySym.XK_ecircumflex);
        }
        if (type == null) {
            $$$reportNull$$$0(XKeySym.XK_ediaeresis);
        }
        return stackValue != null ? StackValue.coercion(stackValue, type, kotlinType) : genLazy(ktExpression, type, kotlinType);
    }

    private StackValue genUnlessProvided(StackValue stackValue, KtExpression ktExpression, Type type) {
        if (ktExpression == null) {
            $$$reportNull$$$0(XKeySym.XK_igrave);
        }
        if (type == null) {
            $$$reportNull$$$0(XKeySym.XK_iacute);
        }
        if (stackValue != null) {
            stackValue.put(type, null, this.v);
        } else {
            gen(ktExpression, type);
        }
        return StackValue.onStack(type);
    }

    static /* synthetic */ Unit lambda$generateEquals$23(InstructionAdapter instructionAdapter) {
        instructionAdapter.aconst(null);
        instructionAdapter.athrow();
        return Unit.INSTANCE;
    }

    private StackValue generateEquals(KtExpression ktExpression, KtExpression ktExpression2, IElementType iElementType, StackValue stackValue, KotlinType kotlinType, PrimitiveNumericComparisonInfo primitiveNumericComparisonInfo) {
        Type typeBoxType;
        if (iElementType == null) {
            $$$reportNull$$$0(XKeySym.XK_icircumflex);
        }
        if (ktExpression == null || ktExpression2 == null) {
            return StackValue.operation(Type.VOID_TYPE, new Function1() { // from class: org.jetbrains.kotlin.codegen.ExpressionCodegen$$ExternalSyntheticLambda9
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return ExpressionCodegen.lambda$generateEquals$23((InstructionAdapter) obj);
                }
            });
        }
        KotlinType kotlinType2 = kotlinType != null ? kotlinType : kotlinType(ktExpression);
        KotlinType kotlinType3 = kotlinType(ktExpression2);
        boolean z = kotlinType2 != null && InlineClassesUtilsKt.isInlineClassType(kotlinType2);
        boolean z2 = kotlinType3 != null && InlineClassesUtilsKt.isInlineClassType(kotlinType3);
        Type typeExpressionType = stackValue != null ? stackValue.type : expressionType(ktExpression);
        Type typeExpressionType2 = expressionType(ktExpression2);
        boolean z3 = AsmUtil.isPrimitive(typeExpressionType) && (!z || CodegenUtilKt.isInlineClassTypeWithPrimitiveEquality(kotlinType2));
        boolean z4 = AsmUtil.isPrimitive(typeExpressionType2) && (!z2 || CodegenUtilKt.isInlineClassTypeWithPrimitiveEquality(kotlinType3));
        if (KtPsiUtil.isNullConstant(ktExpression)) {
            return genCmpWithNull(ktExpression2, iElementType, null);
        }
        if (KtPsiUtil.isNullConstant(ktExpression2)) {
            return genCmpWithNull(ktExpression, iElementType, stackValue);
        }
        if (z || z2) {
            boolean z5 = z && StackValue.isUnboxedInlineClass(kotlinType2, typeExpressionType);
            boolean z6 = z2 && StackValue.isUnboxedInlineClass(kotlinType3, typeExpressionType2);
            if (!z5 || !z6 || !z3 || !z4) {
                return genEqualsForInlineClasses(ktExpression, ktExpression2, iElementType, stackValue, kotlinType2, kotlinType3, typeExpressionType, typeExpressionType2, z, z5, z6);
            }
        }
        if (isIntZero(ktExpression, typeExpressionType) && z4 && AsmUtil.isIntPrimitive(typeExpressionType2)) {
            return genCmpWithZero(ktExpression2, iElementType, null);
        }
        if (isIntZero(ktExpression2, typeExpressionType2) && z3 && AsmUtil.isIntPrimitive(typeExpressionType)) {
            return genCmpWithZero(ktExpression, iElementType, stackValue);
        }
        if (stackValue == null && (ktExpression instanceof KtSafeQualifiedExpression)) {
            KtSafeQualifiedExpression ktSafeQualifiedExpression = (KtSafeQualifiedExpression) ktExpression;
            if (isSelectorPureNonNullType(ktSafeQualifiedExpression) && AsmUtil.isPrimitive(typeExpressionType2) && z4) {
                return genCmpSafeCallToPrimitive(ktSafeQualifiedExpression, ktExpression2, typeExpressionType2, iElementType);
            }
        }
        if (AsmUtil.isPrimitive(typeExpressionType) && z3 && (ktExpression2 instanceof KtSafeQualifiedExpression)) {
            KtSafeQualifiedExpression ktSafeQualifiedExpression2 = (KtSafeQualifiedExpression) ktExpression2;
            if (isSelectorPureNonNullType(ktSafeQualifiedExpression2)) {
                return genCmpPrimitiveToSafeCall(ktExpression, typeExpressionType, ktSafeQualifiedExpression2, iElementType, stackValue);
            }
        }
        if (BoxedToPrimitiveEquality.isApplicable(iElementType, typeExpressionType, typeExpressionType2)) {
            return BoxedToPrimitiveEquality.create(iElementType, genLazyUnlessProvided(stackValue, ktExpression, typeExpressionType), typeExpressionType, genLazy(ktExpression2, typeExpressionType2), typeExpressionType2, this.myFrameMap);
        }
        if (PrimitiveToBoxedEquality.isApplicable(iElementType, typeExpressionType, typeExpressionType2)) {
            return PrimitiveToBoxedEquality.create(iElementType, genLazyUnlessProvided(stackValue, ktExpression, typeExpressionType), typeExpressionType, genLazy(ktExpression2, typeExpressionType2), typeExpressionType2);
        }
        if (PrimitiveToObjectEquality.isApplicable(iElementType, typeExpressionType, typeExpressionType2)) {
            return PrimitiveToObjectEquality.create(iElementType, genLazyUnlessProvided(stackValue, ktExpression, typeExpressionType), typeExpressionType, genLazy(ktExpression2, typeExpressionType2), typeExpressionType2);
        }
        if (AsmUtil.isPrimitive(typeExpressionType) != AsmUtil.isPrimitive(typeExpressionType2)) {
            typeExpressionType = AsmUtil.boxType(typeExpressionType);
            typeBoxType = AsmUtil.boxType(typeExpressionType2);
        } else {
            typeBoxType = typeExpressionType2;
        }
        if (iElementType == KtTokens.EQEQEQ || iElementType == KtTokens.EXCLEQEQEQ) {
            return StackValue.cmp(iElementType, AsmUtil.isPrimitive(typeExpressionType) ? typeExpressionType : AsmTypes.OBJECT_TYPE, genLazyUnlessProvided(stackValue, ktExpression, typeExpressionType), genLazy(ktExpression2, typeBoxType));
        }
        if ((iElementType == KtTokens.EQEQ || iElementType == KtTokens.EXCLEQ) && (isEnumExpression(ktExpression) || isEnumExpression(ktExpression2))) {
            return StackValue.cmp(iElementType == KtTokens.EQEQ ? KtTokens.EQEQEQ : KtTokens.EXCLEQEQEQ, AsmTypes.OBJECT_TYPE, genLazyUnlessProvided(stackValue, ktExpression, typeExpressionType), genLazy(ktExpression2, typeBoxType));
        }
        return genEqualsForExpressionsPreferIeee754Arithmetic(ktExpression, ktExpression2, iElementType, typeExpressionType, typeBoxType, stackValue, primitiveNumericComparisonInfo);
    }

    private StackValue genEqualsForInlineClasses(KtExpression ktExpression, KtExpression ktExpression2, final IElementType iElementType, StackValue stackValue, final KotlinType kotlinType, final KotlinType kotlinType2, final Type type, final Type type2, final boolean z, final boolean z2, final boolean z3) {
        if (ktExpression == null) {
            $$$reportNull$$$0(XKeySym.XK_idiaeresis);
        }
        if (ktExpression2 == null) {
            $$$reportNull$$$0(240);
        }
        if (iElementType == null) {
            $$$reportNull$$$0(XKeySym.XK_ntilde);
        }
        if (kotlinType == null) {
            $$$reportNull$$$0(242);
        }
        if (kotlinType2 == null) {
            $$$reportNull$$$0(243);
        }
        if (type == null) {
            $$$reportNull$$$0(244);
        }
        if (type2 == null) {
            $$$reportNull$$$0(245);
        }
        final StackValue stackValueGenLazyUnlessProvided = genLazyUnlessProvided(stackValue, ktExpression, type, kotlinType);
        final StackValue stackValueGenLazy = genLazy(ktExpression2, type2, kotlinType2);
        StackValue stackValueOperation = StackValue.operation(Type.BOOLEAN_TYPE, new Function1() { // from class: org.jetbrains.kotlin.codegen.ExpressionCodegen$$ExternalSyntheticLambda32
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return this.f$0.m6385x82900c29(iElementType, z2, z, z3, kotlinType, stackValueGenLazyUnlessProvided, type, stackValueGenLazy, type2, kotlinType2, (InstructionAdapter) obj);
            }
        });
        if (stackValueOperation == null) {
            $$$reportNull$$$0(246);
        }
        return stackValueOperation;
    }

    /* JADX INFO: renamed from: lambda$genEqualsForInlineClasses$24$org-jetbrains-kotlin-codegen-ExpressionCodegen, reason: not valid java name */
    /* synthetic */ Unit m6385x82900c29(IElementType iElementType, boolean z, boolean z2, boolean z3, KotlinType kotlinType, StackValue stackValue, Type type, StackValue stackValue2, Type type2, KotlinType kotlinType2, InstructionAdapter instructionAdapter) {
        Type type3;
        Type type4;
        SimpleType nullableAnyType = this.state.getModule().getBuiltIns().getNullableAnyType();
        Label label = new Label();
        int i = (iElementType == KtTokens.EXCLEQ || iElementType == KtTokens.EXCLEQEQEQ) ? 1 : 0;
        boolean z4 = (z || (z2 && z3)) && JvmCodegenUtil.typeHasSpecializedInlineClassEquality(kotlinType, this.state);
        stackValue.put(type, kotlinType, instructionAdapter);
        if (z4) {
            type3 = type;
        } else {
            StackValue.coerce(type, kotlinType, AsmTypes.OBJECT_TYPE, nullableAnyType, instructionAdapter);
            type3 = AsmTypes.OBJECT_TYPE;
        }
        stackValue2.put(type2, kotlinType2, instructionAdapter);
        if (z4 && z3) {
            type4 = type2;
        } else {
            StackValue.coerce(type2, kotlinType2, AsmTypes.OBJECT_TYPE, nullableAnyType, instructionAdapter);
            type4 = AsmTypes.OBJECT_TYPE;
        }
        if (z4) {
            String internalName = this.typeMapper.mapTypeAsDeclaration(kotlinType).getInternalName();
            if (TypeUtils.isNullableType(kotlinType)) {
                AsmUtil.dupSecond(instructionAdapter, type4, type3);
                Label label2 = new Label();
                instructionAdapter.ifnonnull(label2);
                if (TypeUtils.isNullableType(kotlinType2)) {
                    Label label3 = new Label();
                    instructionAdapter.ifnonnull(label3);
                    AsmUtil.pop(instructionAdapter, type3);
                    instructionAdapter.iconst(i ^ 1);
                    instructionAdapter.goTo(label);
                    instructionAdapter.visitLabel(label3);
                    AsmUtil.pop(instructionAdapter, type3);
                } else {
                    AsmUtil.pop2(instructionAdapter, type4, type3);
                }
                instructionAdapter.iconst(i);
                instructionAdapter.goTo(label);
                instructionAdapter.visitLabel(label2);
            }
            if (z3) {
                if (TypeUtils.isNullableType(kotlinType2)) {
                    AsmUtil.dup(instructionAdapter, type4);
                    Label label4 = new Label();
                    instructionAdapter.ifnonnull(label4);
                    AsmUtil.pop2(instructionAdapter, type4, type3);
                    instructionAdapter.iconst(i);
                    instructionAdapter.goTo(label);
                    instructionAdapter.visitLabel(label4);
                }
                if (!z) {
                    AsmUtil.swap(instructionAdapter, type4, type3);
                    KotlinType kotlinTypeMakeNotNullable = TypeUtils.makeNotNullable(kotlinType);
                    Type typeMapType = this.typeMapper.mapType(kotlinTypeMakeNotNullable);
                    StackValue.coerce(type, kotlinType, typeMapType, kotlinTypeMakeNotNullable, instructionAdapter);
                    AsmUtil.swap(instructionAdapter, typeMapType, type4);
                    type3 = typeMapType;
                }
                instructionAdapter.invokestatic(internalName, InlineClassDescriptorResolver.SPECIALIZED_EQUALS_NAME.asString(), Type.getMethodDescriptor(Type.BOOLEAN_TYPE, type3, type3), false);
            } else {
                instructionAdapter.invokestatic(internalName, "equals-impl", Type.getMethodDescriptor(Type.BOOLEAN_TYPE, type3, AsmTypes.OBJECT_TYPE), false);
            }
        } else {
            DescriptorAsmUtil.genAreEqualCall(instructionAdapter);
        }
        if (i != 0) {
            AsmUtil.genInvertBoolean(instructionAdapter);
        }
        instructionAdapter.visitLabel(label);
        return Unit.INSTANCE;
    }

    private boolean isEnumExpression(KtExpression ktExpression) {
        KotlinType type = this.bindingContext.getType(ktExpression);
        if (type == null) {
            return false;
        }
        return DescriptorUtils.isEnumClass(type.getConstructor().getThis$0());
    }

    private boolean isSelectorPureNonNullType(KtSafeQualifiedExpression ktSafeQualifiedExpression) {
        ResolvedCall<? extends CallableDescriptor> resolvedCall;
        KotlinType returnType;
        if (ktSafeQualifiedExpression == null) {
            $$$reportNull$$$0(247);
        }
        KtExpression selectorExpression = ktSafeQualifiedExpression.getSelectorExpression();
        return (selectorExpression == null || (resolvedCall = CallUtilKt.getResolvedCall(selectorExpression, this.bindingContext)) == null || (returnType = resolvedCall.getResultingDescriptor().getReturnType()) == null || TypeUtils.isNullableType(returnType)) ? false : true;
    }

    private StackValue genCmpPrimitiveToSafeCall(KtExpression ktExpression, Type type, KtSafeQualifiedExpression ktSafeQualifiedExpression, IElementType iElementType, StackValue stackValue) {
        if (ktExpression == null) {
            $$$reportNull$$$0(248);
        }
        if (type == null) {
            $$$reportNull$$$0(249);
        }
        if (ktSafeQualifiedExpression == null) {
            $$$reportNull$$$0(250);
        }
        if (iElementType == null) {
            $$$reportNull$$$0(251);
        }
        Label label = new Label();
        return new PrimitiveToSafeCallEquality(iElementType, type, genLazyUnlessProvided(stackValue, ktExpression, type), generateSafeQualifiedExpression(ktSafeQualifiedExpression, label), expressionType(ktSafeQualifiedExpression.getReceiverExpression()), label);
    }

    private StackValue genCmpSafeCallToPrimitive(KtSafeQualifiedExpression ktSafeQualifiedExpression, KtExpression ktExpression, Type type, IElementType iElementType) {
        if (ktSafeQualifiedExpression == null) {
            $$$reportNull$$$0(252);
        }
        if (ktExpression == null) {
            $$$reportNull$$$0(253);
        }
        if (type == null) {
            $$$reportNull$$$0(254);
        }
        if (iElementType == null) {
            $$$reportNull$$$0(255);
        }
        Label label = new Label();
        return new SafeCallToPrimitiveEquality(iElementType, type, generateSafeQualifiedExpression(ktSafeQualifiedExpression, label), genLazy(ktExpression, type), expressionType(ktSafeQualifiedExpression.getReceiverExpression()), label);
    }

    private static KotlinType getLeftOperandType(PrimitiveNumericComparisonInfo primitiveNumericComparisonInfo) {
        if (primitiveNumericComparisonInfo == null) {
            return null;
        }
        return primitiveNumericComparisonInfo.getLeftPrimitiveType();
    }

    private static KotlinType getRightOperandType(PrimitiveNumericComparisonInfo primitiveNumericComparisonInfo) {
        if (primitiveNumericComparisonInfo == null) {
            return null;
        }
        return primitiveNumericComparisonInfo.getRightPrimitiveType();
    }

    private StackValue genEqualsForExpressionsPreferIeee754Arithmetic(final KtExpression ktExpression, final KtExpression ktExpression2, final IElementType iElementType, Type type, Type type2, final StackValue stackValue, PrimitiveNumericComparisonInfo primitiveNumericComparisonInfo) {
        Type type3;
        Type type4;
        Type typeComparisonOperandType;
        if (ktExpression == null) {
            $$$reportNull$$$0(256);
        }
        if (ktExpression2 == null) {
            $$$reportNull$$$0(257);
        }
        if (iElementType == null) {
            $$$reportNull$$$0(258);
        }
        if (type == null) {
            $$$reportNull$$$0(259);
        }
        if (type2 == null) {
            $$$reportNull$$$0(260);
        }
        final TypeAndNullability typeAndNullabilityCalcTypeForIeee754ArithmeticIfNeeded = calcTypeForIeee754ArithmeticIfNeeded(ktExpression, getLeftOperandType(primitiveNumericComparisonInfo));
        final TypeAndNullability typeAndNullabilityCalcTypeForIeee754ArithmeticIfNeeded2 = calcTypeForIeee754ArithmeticIfNeeded(ktExpression2, getRightOperandType(primitiveNumericComparisonInfo));
        if (typeAndNullabilityCalcTypeForIeee754ArithmeticIfNeeded == null || typeAndNullabilityCalcTypeForIeee754ArithmeticIfNeeded2 == null || !((typeComparisonOperandType = AsmUtil.comparisonOperandType(typeAndNullabilityCalcTypeForIeee754ArithmeticIfNeeded.type, typeAndNullabilityCalcTypeForIeee754ArithmeticIfNeeded2.type)) == Type.FLOAT_TYPE || typeComparisonOperandType == Type.DOUBLE_TYPE)) {
            type3 = type;
            type4 = type2;
        } else if (typeAndNullabilityCalcTypeForIeee754ArithmeticIfNeeded.type.equals(typeAndNullabilityCalcTypeForIeee754ArithmeticIfNeeded2.type)) {
            if (typeAndNullabilityCalcTypeForIeee754ArithmeticIfNeeded.isNullable || typeAndNullabilityCalcTypeForIeee754ArithmeticIfNeeded2.isNullable) {
                if (this.state.getLanguageVersionSettings().getApiVersion().compareTo(ApiVersion.KOTLIN_1_1) >= 0) {
                    return StackValue.operation(Type.BOOLEAN_TYPE, new Function1() { // from class: org.jetbrains.kotlin.codegen.ExpressionCodegen$$ExternalSyntheticLambda7
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj) {
                            return this.f$0.m6383xf14994a2(iElementType, stackValue, ktExpression, typeAndNullabilityCalcTypeForIeee754ArithmeticIfNeeded, ktExpression2, typeAndNullabilityCalcTypeForIeee754ArithmeticIfNeeded2, (InstructionAdapter) obj);
                        }
                    });
                }
                return StackValue.operation(Type.BOOLEAN_TYPE, new Function1() { // from class: org.jetbrains.kotlin.codegen.ExpressionCodegen$$ExternalSyntheticLambda8
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return this.f$0.m6384x2b143681(iElementType, stackValue, ktExpression, typeAndNullabilityCalcTypeForIeee754ArithmeticIfNeeded, ktExpression2, typeAndNullabilityCalcTypeForIeee754ArithmeticIfNeeded2, (InstructionAdapter) obj);
                    }
                });
            }
            type3 = typeAndNullabilityCalcTypeForIeee754ArithmeticIfNeeded.type;
            type4 = typeAndNullabilityCalcTypeForIeee754ArithmeticIfNeeded2.type;
        } else {
            if (shouldUseProperIeee754Comparisons()) {
                return Ieee754Equality.create(this.myFrameMap, genLazy(ktExpression, boxIfNullable(typeAndNullabilityCalcTypeForIeee754ArithmeticIfNeeded)), genLazy(ktExpression2, boxIfNullable(typeAndNullabilityCalcTypeForIeee754ArithmeticIfNeeded2)), typeComparisonOperandType, iElementType);
            }
            type3 = type;
            type4 = type2;
        }
        return DescriptorAsmUtil.genEqualsForExpressionsOnStack(iElementType, genLazyUnlessProvided(stackValue, ktExpression, type3, kotlinType(ktExpression)), genLazy(ktExpression2, type4, kotlinType(ktExpression2)));
    }

    /* JADX INFO: renamed from: lambda$genEqualsForExpressionsPreferIeee754Arithmetic$25$org-jetbrains-kotlin-codegen-ExpressionCodegen, reason: not valid java name */
    /* synthetic */ Unit m6383xf14994a2(IElementType iElementType, StackValue stackValue, KtExpression ktExpression, TypeAndNullability typeAndNullability, KtExpression ktExpression2, TypeAndNullability typeAndNullability2, InstructionAdapter instructionAdapter) {
        generate754EqualsForNullableTypesViaIntrinsic(instructionAdapter, iElementType, stackValue, ktExpression, typeAndNullability, ktExpression2, typeAndNullability2);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: lambda$genEqualsForExpressionsPreferIeee754Arithmetic$26$org-jetbrains-kotlin-codegen-ExpressionCodegen, reason: not valid java name */
    /* synthetic */ Unit m6384x2b143681(IElementType iElementType, StackValue stackValue, KtExpression ktExpression, TypeAndNullability typeAndNullability, KtExpression ktExpression2, TypeAndNullability typeAndNullability2, InstructionAdapter instructionAdapter) {
        generate754EqualsForNullableTypes(instructionAdapter, iElementType, stackValue, ktExpression, typeAndNullability, ktExpression2, typeAndNullability2);
        return Unit.INSTANCE;
    }

    private static Type boxIfNullable(TypeAndNullability typeAndNullability) {
        if (typeAndNullability == null) {
            $$$reportNull$$$0(GlobalMediaRouter.CallbackHandler.MSG_ROUTE_PRESENTATION_DISPLAY_CHANGED);
        }
        if (typeAndNullability.isNullable) {
            Type typeBoxType = AsmUtil.boxType(typeAndNullability.type);
            if (typeBoxType == null) {
                $$$reportNull$$$0(GlobalMediaRouter.CallbackHandler.MSG_ROUTE_SELECTED);
            }
            return typeBoxType;
        }
        Type type = typeAndNullability.type;
        if (type == null) {
            $$$reportNull$$$0(263);
        }
        return type;
    }

    private void generate754EqualsForNullableTypesViaIntrinsic(InstructionAdapter instructionAdapter, IElementType iElementType, StackValue stackValue, KtExpression ktExpression, TypeAndNullability typeAndNullability, KtExpression ktExpression2, TypeAndNullability typeAndNullability2) {
        if (instructionAdapter == null) {
            $$$reportNull$$$0(GlobalMediaRouter.CallbackHandler.MSG_ROUTE_ANOTHER_SELECTED);
        }
        if (iElementType == null) {
            $$$reportNull$$$0(265);
        }
        if (typeAndNullability == null) {
            $$$reportNull$$$0(266);
        }
        if (typeAndNullability2 == null) {
            $$$reportNull$$$0(267);
        }
        boolean z = typeAndNullability.isNullable;
        Type typeBoxType = typeAndNullability.type;
        if (z) {
            typeBoxType = AsmUtil.boxType(typeBoxType);
        }
        genUnlessProvided(stackValue, ktExpression, typeBoxType);
        Type typeBoxType2 = typeAndNullability2.isNullable ? AsmUtil.boxType(typeAndNullability2.type) : typeAndNullability2.type;
        gen(ktExpression2, typeBoxType2);
        DescriptorAsmUtil.genIEEE754EqualForNullableTypesCall(instructionAdapter, typeBoxType, typeBoxType2);
        if (iElementType == KtTokens.EXCLEQ) {
            AsmUtil.genInvertBoolean(instructionAdapter);
        }
    }

    private void generate754EqualsForNullableTypes(InstructionAdapter instructionAdapter, IElementType iElementType, StackValue stackValue, KtExpression ktExpression, TypeAndNullability typeAndNullability, KtExpression ktExpression2, TypeAndNullability typeAndNullability2) {
        if (instructionAdapter == null) {
            $$$reportNull$$$0(268);
        }
        if (iElementType == null) {
            $$$reportNull$$$0(269);
        }
        if (typeAndNullability == null) {
            $$$reportNull$$$0(270);
        }
        if (typeAndNullability2 == null) {
            $$$reportNull$$$0(271);
        }
        int i = iElementType == KtTokens.EQEQ ? 1 : 0;
        int i2 = iElementType == KtTokens.EQEQ ? 0 : 1;
        Label label = new Label();
        StackValue stackValueGen = stackValue != null ? stackValue : gen(ktExpression);
        stackValueGen.put(stackValueGen.type, stackValueGen.kotlinType, instructionAdapter);
        StackValue stackValueOnStack = StackValue.onStack(stackValueGen.type);
        Type type = typeAndNullability.type;
        Type type2 = typeAndNullability2.type;
        if (typeAndNullability.isNullable) {
            stackValueOnStack.dup(instructionAdapter, false);
            Label label2 = new Label();
            instructionAdapter.ifnull(label2);
            StackValue.coercion(stackValueOnStack, type, null).put(type, null, instructionAdapter);
            StackValue stackValueOnStack2 = StackValue.onStack(type);
            StackValue stackValueGen2 = gen(ktExpression2);
            stackValueGen2.put(stackValueGen2.type, stackValueGen2.kotlinType, instructionAdapter);
            StackValue stackValueOnStack3 = StackValue.onStack(stackValueGen2.type);
            if (typeAndNullability2.isNullable) {
                stackValueOnStack3.dup(instructionAdapter, false);
                Label label3 = new Label();
                instructionAdapter.ifnonnull(label3);
                AsmUtil.pop(instructionAdapter, stackValueOnStack3.type);
                AsmUtil.pop(instructionAdapter, stackValueOnStack2.type);
                instructionAdapter.iconst(i2);
                instructionAdapter.goTo(label);
                instructionAdapter.mark(label3);
            }
            StackValue.coercion(stackValueOnStack3, type2, null).put(type2, null, instructionAdapter);
            StackValue.cmp(iElementType, type, stackValueOnStack2, StackValue.onStack(type2)).put(Type.BOOLEAN_TYPE, null, instructionAdapter);
            instructionAdapter.goTo(label);
            instructionAdapter.mark(label2);
            AsmUtil.pop(instructionAdapter, stackValueOnStack.type);
            StackValue stackValueGen3 = gen(ktExpression2);
            stackValueGen3.put(stackValueGen3.type, stackValueGen3.kotlinType, instructionAdapter);
            StackValue stackValueOnStack4 = StackValue.onStack(stackValueGen3.type);
            if (typeAndNullability2.isNullable) {
                Label label4 = new Label();
                instructionAdapter.ifnonnull(label4);
                instructionAdapter.iconst(i);
                instructionAdapter.goTo(label);
                instructionAdapter.mark(label4);
                instructionAdapter.iconst(i2);
            } else {
                AsmUtil.pop(instructionAdapter, stackValueOnStack4.type);
                instructionAdapter.iconst(i2);
            }
            instructionAdapter.mark(label);
            return;
        }
        StackValue.coercion(stackValueOnStack, type, null).put(type, null, instructionAdapter);
        StackValue stackValueOnStack5 = StackValue.onStack(type);
        StackValue stackValueGen4 = gen(ktExpression2);
        stackValueGen4.put(stackValueGen4.type, stackValueGen4.kotlinType, instructionAdapter);
        StackValue stackValueOnStack6 = StackValue.onStack(stackValueGen4.type);
        stackValueOnStack6.dup(instructionAdapter, false);
        Label label5 = new Label();
        instructionAdapter.ifnonnull(label5);
        AsmUtil.pop(instructionAdapter, stackValueOnStack6.type);
        AsmUtil.pop(instructionAdapter, stackValueOnStack5.type);
        instructionAdapter.iconst(i2);
        instructionAdapter.goTo(label);
        instructionAdapter.mark(label5);
        StackValue.coercion(stackValueOnStack6, type2, null).put(type2, null, instructionAdapter);
        StackValue.cmp(iElementType, type, stackValueOnStack5, StackValue.onStack(type2)).put(Type.BOOLEAN_TYPE, null, instructionAdapter);
        instructionAdapter.mark(label);
    }

    private boolean isIntZero(KtExpression ktExpression, Type type) {
        ConstantValue<?> primitiveOrStringCompileTimeConstant = getPrimitiveOrStringCompileTimeConstant(ktExpression);
        if (!AsmUtil.isIntPrimitive(type) || primitiveOrStringCompileTimeConstant == null) {
            return false;
        }
        Integer num = 0;
        return num.equals(primitiveOrStringCompileTimeConstant.getValue());
    }

    private StackValue genCmpWithZero(KtExpression ktExpression, IElementType iElementType, StackValue stackValue) {
        if (stackValue == null) {
            KotlinType kotlinType = kotlinType(ktExpression);
            stackValue = genLazy(ktExpression, asmType(kotlinType), kotlinType);
        }
        return StackValue.compareIntWithZero(stackValue, (KtTokens.EQEQ == iElementType || KtTokens.EQEQEQ == iElementType) ? 154 : 153);
    }

    private StackValue genCmpWithNull(KtExpression ktExpression, final IElementType iElementType, final StackValue stackValue) {
        final KotlinType kotlinType = kotlinType(ktExpression);
        final Type typeExpressionType = expressionType(ktExpression);
        if (stackValue == null) {
            stackValue = gen(ktExpression);
        }
        if (kotlinType == null || TypeUtils.isNullableType(kotlinType) || !InlineClassesUtilsKt.isInlineClassType(kotlinType)) {
            return StackValue.compareWithNull(stackValue, (KtTokens.EQEQ == iElementType || KtTokens.EQEQEQ == iElementType) ? 199 : 198);
        }
        return StackValue.operation(Type.BOOLEAN_TYPE, new Function1() { // from class: org.jetbrains.kotlin.codegen.ExpressionCodegen$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ExpressionCodegen.lambda$genCmpWithNull$27(stackValue, typeExpressionType, kotlinType, iElementType, (InstructionAdapter) obj);
            }
        });
    }

    static /* synthetic */ Unit lambda$genCmpWithNull$27(StackValue stackValue, Type type, KotlinType kotlinType, IElementType iElementType, InstructionAdapter instructionAdapter) {
        stackValue.put(type, kotlinType, instructionAdapter);
        AsmUtil.pop(instructionAdapter, type);
        instructionAdapter.iconst((iElementType == KtTokens.EXCLEQ || iElementType == KtTokens.EXCLEQEQEQ) ? 1 : 0);
        return Unit.INSTANCE;
    }

    private StackValue generateElvis(final KtBinaryExpression ktBinaryExpression) {
        if (ktBinaryExpression == null) {
            $$$reportNull$$$0(272);
        }
        final KtExpression left = ktBinaryExpression.getLeft();
        final Type typeExpressionType = expressionType(ktBinaryExpression);
        final KotlinType kotlinType = kotlinType(ktBinaryExpression);
        final Type typeExpressionType2 = expressionType(left);
        final KotlinType kotlinType2 = kotlinType(left);
        final Label label = new Label();
        final StackValue stackValueGenerateExpressionWithNullFallback = generateExpressionWithNullFallback(left, label);
        return AsmUtil.isPrimitive(typeExpressionType2) ? stackValueGenerateExpressionWithNullFallback : StackValue.operation(typeExpressionType, kotlinType, new Function1() { // from class: org.jetbrains.kotlin.codegen.ExpressionCodegen$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return this.f$0.m6396xa837a536(stackValueGenerateExpressionWithNullFallback, label, typeExpressionType2, kotlinType2, typeExpressionType, kotlinType, left, ktBinaryExpression, (InstructionAdapter) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$generateElvis$28$org-jetbrains-kotlin-codegen-ExpressionCodegen, reason: not valid java name */
    /* synthetic */ Unit m6396xa837a536(StackValue stackValue, Label label, Type type, KotlinType kotlinType, Type type2, KotlinType kotlinType2, KtExpression ktExpression, KtBinaryExpression ktBinaryExpression, InstructionAdapter instructionAdapter) {
        stackValue.put(stackValue.type, stackValue.kotlinType, instructionAdapter);
        instructionAdapter.dup();
        instructionAdapter.ifnull(label);
        StackValue.onStack(type, kotlinType).put(type2, kotlinType2, instructionAdapter);
        Label label2 = new Label();
        instructionAdapter.goTo(label2);
        instructionAdapter.mark(label);
        Integer lineNumberForElement = CodegenUtil.getLineNumberForElement(ktExpression, false);
        Integer lineNumberForElement2 = CodegenUtil.getLineNumberForElement(ktBinaryExpression.getRight(), false);
        if (lineNumberForElement2 != null && lineNumberForElement2.equals(lineNumberForElement)) {
            instructionAdapter.visitLineNumber(lineNumberForElement2.intValue(), label);
        }
        instructionAdapter.pop();
        gen(ktBinaryExpression.getRight(), type2, kotlinType2);
        instructionAdapter.mark(label2);
        return null;
    }

    private StackValue generateComparison(KtBinaryExpression ktBinaryExpression, StackValue stackValue) {
        StackValue stackValueInvokeFunction;
        StackValue stackValueConstant;
        Type typeComparisonOperandType;
        ResolvedCall<? extends CallableDescriptor> resolvedCallWithAssert = CallUtilKt.getResolvedCallWithAssert(ktBinaryExpression, this.bindingContext);
        PrimitiveNumericComparisonInfo primitiveNumericComparisonInfo = (PrimitiveNumericComparisonInfo) this.bindingContext.get(BindingContext.PRIMITIVE_NUMERIC_COMPARISON_INFO, ktBinaryExpression);
        KtExpression left = ktBinaryExpression.getLeft();
        KtExpression right = ktBinaryExpression.getRight();
        Type typeExpressionType = expressionType(left);
        Type typeExpressionType2 = expressionType(right);
        TypeAndNullability typeAndNullabilityCalcTypeForIeee754ArithmeticIfNeeded = calcTypeForIeee754ArithmeticIfNeeded(left, getLeftOperandType(primitiveNumericComparisonInfo));
        TypeAndNullability typeAndNullabilityCalcTypeForIeee754ArithmeticIfNeeded2 = calcTypeForIeee754ArithmeticIfNeeded(right, getRightOperandType(primitiveNumericComparisonInfo));
        boolean z = (typeAndNullabilityCalcTypeForIeee754ArithmeticIfNeeded == null || typeAndNullabilityCalcTypeForIeee754ArithmeticIfNeeded2 == null || !typeAndNullabilityCalcTypeForIeee754ArithmeticIfNeeded.type.equals(typeAndNullabilityCalcTypeForIeee754ArithmeticIfNeeded2.type)) ? false : true;
        boolean zShouldUseProperIeee754Comparisons = shouldUseProperIeee754Comparisons();
        boolean z2 = primitiveNumericComparisonInfo != null;
        if (zShouldUseProperIeee754Comparisons && z2 && typeAndNullabilityCalcTypeForIeee754ArithmeticIfNeeded != null && typeAndNullabilityCalcTypeForIeee754ArithmeticIfNeeded2 != null) {
            typeComparisonOperandType = AsmUtil.comparisonOperandType(typeAndNullabilityCalcTypeForIeee754ArithmeticIfNeeded.type, typeAndNullabilityCalcTypeForIeee754ArithmeticIfNeeded2.type);
            stackValueInvokeFunction = gen(left);
            stackValueConstant = gen(right);
        } else if (!zShouldUseProperIeee754Comparisons && z2 && ((AsmUtil.isPrimitive(typeExpressionType) && AsmUtil.isPrimitive(typeExpressionType2)) || z)) {
            typeComparisonOperandType = z ? typeAndNullabilityCalcTypeForIeee754ArithmeticIfNeeded.type : AsmUtil.comparisonOperandType(typeExpressionType, typeExpressionType2);
            stackValueInvokeFunction = gen(left);
            stackValueConstant = gen(right);
        } else {
            Type type = Type.INT_TYPE;
            stackValueInvokeFunction = invokeFunction(resolvedCallWithAssert, stackValue);
            stackValueConstant = StackValue.constant(0, type);
            typeComparisonOperandType = type;
        }
        return StackValue.cmp(ktBinaryExpression.getOperationToken(), typeComparisonOperandType, stackValueInvokeFunction, stackValueConstant);
    }

    private TypeAndNullability calcTypeForIeee754ArithmeticIfNeeded(KtExpression ktExpression, KotlinType kotlinType) {
        if (ktExpression == null) {
            return null;
        }
        if (shouldUseProperIeee754Comparisons()) {
            return Ieee754Kt.calcProperTypeForIeee754ArithmeticIfNeeded(ktExpression, this.bindingContext, kotlinType, this.typeMapper);
        }
        return Ieee754Kt.legacyCalcTypeForIeee754ArithmeticIfNeeded(ktExpression, this.bindingContext, this.context.getFunctionDescriptor(), this.state.getLanguageVersionSettings());
    }

    private boolean shouldUseProperIeee754Comparisons() {
        return this.state.getLanguageVersionSettings().supportsFeature(LanguageFeature.ProperIeee754Comparisons);
    }

    private StackValue generateAssignmentExpression(final KtBinaryExpression ktBinaryExpression) {
        return StackValue.operation(Type.VOID_TYPE, new Function1() { // from class: org.jetbrains.kotlin.codegen.ExpressionCodegen$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return this.f$0.m6390x159ce9a5(ktBinaryExpression, (InstructionAdapter) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$generateAssignmentExpression$29$org-jetbrains-kotlin-codegen-ExpressionCodegen, reason: not valid java name */
    /* synthetic */ Unit m6390x159ce9a5(KtBinaryExpression ktBinaryExpression, InstructionAdapter instructionAdapter) {
        gen(ktBinaryExpression.getLeft()).store(gen(ktBinaryExpression.getRight()), this.v);
        return Unit.INSTANCE;
    }

    private StackValue generateAugmentedAssignment(final KtBinaryExpression ktBinaryExpression) {
        return StackValue.operation(Type.VOID_TYPE, new Function1() { // from class: org.jetbrains.kotlin.codegen.ExpressionCodegen$$ExternalSyntheticLambda22
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return this.f$0.m6391x748e3a83(ktBinaryExpression, (InstructionAdapter) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$generateAugmentedAssignment$30$org-jetbrains-kotlin-codegen-ExpressionCodegen, reason: not valid java name */
    /* synthetic */ Unit m6391x748e3a83(KtBinaryExpression ktBinaryExpression, InstructionAdapter instructionAdapter) {
        ResolvedCall<?> resolvedCallWithAssert = CallUtilKt.getResolvedCallWithAssert(ktBinaryExpression, this.bindingContext);
        ResolvedCallWithRealDescriptor resolvedCallWithRealDescriptorReplaceSuspensionFunctionWithRealDescriptor = CoroutineCodegenUtilKt.replaceSuspensionFunctionWithRealDescriptor(resolvedCallWithAssert, this.state);
        if (resolvedCallWithRealDescriptorReplaceSuspensionFunctionWithRealDescriptor != null) {
            prepareCoroutineArgumentForSuspendCall(resolvedCallWithAssert, resolvedCallWithRealDescriptorReplaceSuspensionFunctionWithRealDescriptor.getFakeContinuationExpression());
            resolvedCallWithAssert = resolvedCallWithRealDescriptorReplaceSuspensionFunctionWithRealDescriptor.getResolvedCall();
        }
        ResolvedCall<?> resolvedCall = resolvedCallWithAssert;
        FunctionDescriptor functionDescriptorAccessibleFunctionDescriptor = accessibleFunctionDescriptor(resolvedCall);
        Callable callableResolveToCallable = resolveToCallable(functionDescriptorAccessibleFunctionDescriptor, false, resolvedCall);
        KtExpression left = ktBinaryExpression.getLeft();
        putCallAugAssignMethod(ktBinaryExpression, resolvedCall, callableResolveToCallable, functionDescriptorAccessibleFunctionDescriptor.getReturnType(), new JvmKotlinType(expressionType(left), kotlinType(left)), Boolean.TRUE.equals(this.bindingContext.get(BindingContext.VARIABLE_REASSIGNMENT, ktBinaryExpression)) || !KotlinBuiltIns.isUnit(functionDescriptorAccessibleFunctionDescriptor.getReturnType()));
        return Unit.INSTANCE;
    }

    private void putCallAugAssignMethod(KtBinaryExpression ktBinaryExpression, ResolvedCall<?> resolvedCall, Callable callable, KotlinType kotlinType, JvmKotlinType jvmKotlinType, boolean z) {
        if (ktBinaryExpression == null) {
            $$$reportNull$$$0(273);
        }
        if (resolvedCall == null) {
            $$$reportNull$$$0(274);
        }
        if (callable == null) {
            $$$reportNull$$$0(275);
        }
        if (jvmKotlinType == null) {
            $$$reportNull$$$0(276);
        }
        StackValue stackValueGen = gen(ktBinaryExpression.getLeft());
        if (z) {
            stackValueGen = StackValue.complexWriteReadReceiver(stackValueGen);
        }
        stackValueGen.put(jvmKotlinType.getType(), jvmKotlinType.getKotlinType(), this.v);
        callable.invokeMethodWithArguments(resolvedCall, StackValue.onStack(jvmKotlinType.getType(), jvmKotlinType.getKotlinType()), this).put(callable.getReturnType(), null, this.v);
        if (z) {
            stackValueGen.store(StackValue.onStack(callable.getReturnType(), kotlinType), this.v, true);
        }
    }

    public void invokeAppend(StringConcatGenerator stringConcatGenerator, KtExpression ktExpression) {
        StackValue stackValueGen;
        KtExpression ktExpressionSafeDeparenthesize = KtPsiUtil.safeDeparenthesize(ktExpression);
        ConstantValue<?> primitiveOrStringCompileTimeConstant = getPrimitiveOrStringCompileTimeConstant(ktExpressionSafeDeparenthesize);
        if (primitiveOrStringCompileTimeConstant == null) {
            if (ktExpressionSafeDeparenthesize instanceof KtBinaryExpression) {
                KtBinaryExpression ktBinaryExpression = (KtBinaryExpression) ktExpressionSafeDeparenthesize;
                if (ktBinaryExpression.getOperationToken() == KtTokens.PLUS) {
                    KtExpression left = ktBinaryExpression.getLeft();
                    KtExpression right = ktBinaryExpression.getRight();
                    if (expressionType(left).equals(AsmTypes.JAVA_STRING_TYPE)) {
                        invokeAppend(stringConcatGenerator, left);
                        invokeAppend(stringConcatGenerator, right);
                        return;
                    }
                }
            } else if (ktExpressionSafeDeparenthesize instanceof KtStringTemplateExpression) {
                invokeAppendForEntries(stringConcatGenerator, preprocessStringTemplate((KtStringTemplateExpression) ktExpressionSafeDeparenthesize));
                return;
            }
        }
        Type typeExpressionType = expressionType(ktExpressionSafeDeparenthesize);
        KotlinType kotlinType = kotlinType(ktExpressionSafeDeparenthesize);
        if (kotlinType != null && InlineClassesUtilsKt.isInlineClassType(kotlinType) && FlexibleTypesKt.isNullabilityFlexible(kotlinType)) {
            kotlinType = TypeUtils.makeNullable(kotlinType);
        }
        if (primitiveOrStringCompileTimeConstant != null) {
            stackValueGen = StackValue.constant(primitiveOrStringCompileTimeConstant.getValue(), typeExpressionType, kotlinType);
        } else {
            stackValueGen = gen(ktExpressionSafeDeparenthesize);
        }
        DescriptorAsmUtil.genInvokeAppendMethod(stringConcatGenerator, typeExpressionType, kotlinType, this.typeMapper, stackValueGen);
    }

    private static KtSimpleNameExpression targetLabel(KtExpression ktExpression) {
        if (ktExpression.getMyParent() instanceof KtLabeledExpression) {
            return ((KtLabeledExpression) ktExpression.getMyParent()).getTargetLabel();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public StackValue visitLabeledExpression(KtLabeledExpression ktLabeledExpression, StackValue stackValue) {
        if (ktLabeledExpression == null) {
            $$$reportNull$$$0(277);
        }
        return genQualified(stackValue, ktLabeledExpression.getBaseExpression());
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public StackValue visitPrefixExpression(KtPrefixExpression ktPrefixExpression, StackValue stackValue) {
        if (ktPrefixExpression == null) {
            $$$reportNull$$$0(278);
        }
        if (stackValue == null) {
            $$$reportNull$$$0(279);
        }
        ConstantValue<?> primitiveOrStringCompileTimeConstant = getPrimitiveOrStringCompileTimeConstant(ktPrefixExpression);
        if (primitiveOrStringCompileTimeConstant != null) {
            return StackValue.constant(primitiveOrStringCompileTimeConstant.getValue(), expressionType(ktPrefixExpression));
        }
        DeclarationDescriptor declarationDescriptor = (DeclarationDescriptor) this.bindingContext.get(BindingContext.REFERENCE_TARGET, ktPrefixExpression.getOperationReference());
        ResolvedCall<? extends CallableDescriptor> resolvedCallWithAssert = CallUtilKt.getResolvedCallWithAssert(ktPrefixExpression, this.bindingContext);
        resolvedCallWithAssert.getResultingDescriptor();
        String strAsString = declarationDescriptor == null ? "" : declarationDescriptor.getShortName().asString();
        if (!strAsString.equals("inc") && !strAsString.equals("dec")) {
            return invokeFunction(resolvedCallWithAssert, stackValue);
        }
        return StackValue.preIncrement(expressionType(ktPrefixExpression.getBaseExpression()), gen(ktPrefixExpression.getBaseExpression()), strAsString.equals("inc") ? 1 : -1, resolvedCallWithAssert, this);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public StackValue visitPostfixExpression(final KtPostfixExpression ktPostfixExpression, StackValue stackValue) {
        int i;
        int iIndexOfLocalNotDelegated;
        if (ktPostfixExpression == null) {
            $$$reportNull$$$0(280);
        }
        if (ktPostfixExpression.getOperationReference().getReferencedNameElementType() == KtTokens.EXCLEXCL) {
            final StackValue stackValueGenQualified = genQualified(stackValue, ktPostfixExpression.getBaseExpression());
            return AsmUtil.isPrimitive(stackValueGenQualified.type) ? stackValueGenQualified : StackValue.operation(stackValueGenQualified.type, stackValueGenQualified.kotlinType, new Function1() { // from class: org.jetbrains.kotlin.codegen.ExpressionCodegen$$ExternalSyntheticLambda25
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return this.f$0.m6409x105aaff6(stackValueGenQualified, (InstructionAdapter) obj);
                }
            });
        }
        DeclarationDescriptor declarationDescriptor = (DeclarationDescriptor) this.bindingContext.get(BindingContext.REFERENCE_TARGET, ktPostfixExpression.getOperationReference());
        String strAsString = declarationDescriptor != null ? declarationDescriptor.getShortName().asString() : null;
        final ResolvedCall<? extends CallableDescriptor> resolvedCallWithAssert = CallUtilKt.getResolvedCallWithAssert(ktPostfixExpression, this.bindingContext);
        CallableDescriptor resultingDescriptor = resolvedCallWithAssert.getResultingDescriptor();
        if (!(resultingDescriptor instanceof FunctionDescriptor) || declarationDescriptor == null) {
            throw new UnsupportedOperationException("Don't know how to generate this postfix expression: " + strAsString + " " + resultingDescriptor);
        }
        Type typeExpressionType = expressionType(ktPostfixExpression);
        final Type typeExpressionType2 = expressionType(ktPostfixExpression.getBaseExpression());
        final KotlinType kotlinType = kotlinType(ktPostfixExpression.getBaseExpression());
        DeclarationDescriptor containingDeclaration = resultingDescriptor.getContainingDeclaration();
        if (strAsString.equals("inc")) {
            i = 1;
        } else {
            if (!strAsString.equals("dec")) {
                throw new UnsupportedOperationException("Unsupported postfix operation: " + strAsString + " " + resultingDescriptor);
            }
            i = -1;
        }
        final int i2 = i;
        final boolean zIsPrimitiveNumberClassDescriptor = RangeUtilKt.isPrimitiveNumberClassDescriptor(containingDeclaration);
        if (zIsPrimitiveNumberClassDescriptor && AsmUtil.isPrimitive(typeExpressionType2)) {
            KtExpression baseExpression = ktPostfixExpression.getBaseExpression();
            if ((baseExpression instanceof KtReferenceExpression) && typeExpressionType == Type.INT_TYPE && this.bindingContext.get(BindingContext.SMARTCAST, baseExpression) == null && (iIndexOfLocalNotDelegated = indexOfLocalNotDelegated((KtReferenceExpression) baseExpression)) >= 0) {
                return StackValue.postIncrement(iIndexOfLocalNotDelegated, i2);
            }
        }
        return StackValue.operation(typeExpressionType2, kotlinType, new Function1() { // from class: org.jetbrains.kotlin.codegen.ExpressionCodegen$$ExternalSyntheticLambda26
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return this.f$0.m6410x4a2551d5(ktPostfixExpression, typeExpressionType2, kotlinType, zIsPrimitiveNumberClassDescriptor, i2, resolvedCallWithAssert, (InstructionAdapter) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$visitPostfixExpression$31$org-jetbrains-kotlin-codegen-ExpressionCodegen, reason: not valid java name */
    /* synthetic */ Unit m6409x105aaff6(StackValue stackValue, InstructionAdapter instructionAdapter) {
        stackValue.put(stackValue.type, stackValue.kotlinType, instructionAdapter);
        instructionAdapter.dup();
        if (this.state.getConfig().getUnifiedNullChecks()) {
            instructionAdapter.invokestatic("kotlin/jvm/internal/Intrinsics", "checkNotNull", ReifiedTypeInliner.pluginIntrinsicsMarkerSignature, false);
            return null;
        }
        Label label = new Label();
        instructionAdapter.ifnonnull(label);
        instructionAdapter.invokestatic("kotlin/jvm/internal/Intrinsics", "throwNpe", "()V", false);
        instructionAdapter.mark(label);
        return null;
    }

    /* JADX INFO: renamed from: lambda$visitPostfixExpression$32$org-jetbrains-kotlin-codegen-ExpressionCodegen, reason: not valid java name */
    /* synthetic */ Unit m6410x4a2551d5(KtPostfixExpression ktPostfixExpression, Type type, KotlinType kotlinType, boolean z, int i, ResolvedCall resolvedCall, InstructionAdapter instructionAdapter) {
        Type type2;
        KotlinType kotlinType2;
        StackValue stackValueComplexWriteReadReceiver = StackValue.complexWriteReadReceiver(gen(ktPostfixExpression.getBaseExpression()));
        stackValueComplexWriteReadReceiver.put(type, kotlinType, instructionAdapter);
        AsmUtil.dup(instructionAdapter, type);
        StackValue.Local local = StackValue.local(this.myFrameMap.enterTemp(type), type, kotlinType);
        local.store(StackValue.onStack(type, kotlinType), instructionAdapter);
        if (z && AsmUtil.isPrimitive(type)) {
            DescriptorAsmUtil.genIncrement(type, i, instructionAdapter);
            type2 = type;
            kotlinType2 = kotlinType;
        } else {
            StackValue stackValueInvokeFunction = invokeFunction(resolvedCall, StackValue.onStack(type, kotlinType));
            stackValueInvokeFunction.put(stackValueInvokeFunction.type, stackValueInvokeFunction.kotlinType, instructionAdapter);
            type2 = stackValueInvokeFunction.type;
            kotlinType2 = stackValueInvokeFunction.kotlinType;
        }
        stackValueComplexWriteReadReceiver.store(StackValue.onStack(type2, kotlinType2), instructionAdapter, true);
        local.put(type, kotlinType, instructionAdapter);
        this.myFrameMap.leaveTemp(type);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public StackValue visitProperty(KtProperty ktProperty, StackValue stackValue) {
        if (ktProperty == null) {
            $$$reportNull$$$0(281);
        }
        KtExpression initializer = ktProperty.getInitializer();
        KtExpression delegateExpression = ktProperty.getDelegateExpression();
        if (initializer != null) {
            initializeLocalVariable(ktProperty, gen(initializer));
        } else if (delegateExpression != null) {
            initializeLocalVariable(ktProperty, gen(delegateExpression));
        } else if (ktProperty.hasModifier(KtTokens.LATEINIT_KEYWORD)) {
            initializeLocalVariable(ktProperty, null);
        } else {
            initializeLocalVariableWithFakeDefaultValue(ktProperty);
        }
        return StackValue.none();
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public StackValue visitDestructuringDeclaration(KtDestructuringDeclaration ktDestructuringDeclaration, StackValue stackValue) {
        if (ktDestructuringDeclaration == null) {
            $$$reportNull$$$0(282);
        }
        return initializeDestructuringDeclaration(ktDestructuringDeclaration, false);
    }

    public StackValue initializeDestructuringDeclaration(KtDestructuringDeclaration ktDestructuringDeclaration, boolean z) {
        if (ktDestructuringDeclaration == null) {
            $$$reportNull$$$0(283);
        }
        KtExpression initializer = ktDestructuringDeclaration.getInitializer();
        if (initializer == null) {
            return StackValue.none();
        }
        KotlinType type = this.bindingContext.getType(initializer);
        Type typeAsmType = asmType(type);
        TransientReceiver transientReceiver = new TransientReceiver(type);
        int iEnterTemp = this.myFrameMap.enterTemp(typeAsmType);
        gen(initializer, typeAsmType, type);
        this.v.store(iEnterTemp, typeAsmType);
        initializeDestructuringDeclarationVariables(ktDestructuringDeclaration, transientReceiver, StackValue.local(iEnterTemp, typeAsmType, type), z);
        this.myFrameMap.leaveTemp(typeAsmType);
        return StackValue.none();
    }

    public void initializeDestructuringDeclarationVariables(KtDestructuringDeclaration ktDestructuringDeclaration, ReceiverValue receiverValue, StackValue stackValue) {
        if (ktDestructuringDeclaration == null) {
            $$$reportNull$$$0(284);
        }
        if (receiverValue == null) {
            $$$reportNull$$$0(285);
        }
        if (stackValue == null) {
            $$$reportNull$$$0(286);
        }
        initializeDestructuringDeclarationVariables(ktDestructuringDeclaration, receiverValue, stackValue, false);
    }

    private void initializeDestructuringDeclarationVariables(KtDestructuringDeclaration ktDestructuringDeclaration, ReceiverValue receiverValue, StackValue stackValue, boolean z) {
        if (ktDestructuringDeclaration == null) {
            $$$reportNull$$$0(287);
        }
        if (receiverValue == null) {
            $$$reportNull$$$0(288);
        }
        if (stackValue == null) {
            $$$reportNull$$$0(289);
        }
        for (KtDestructuringDeclarationEntry ktDestructuringDeclarationEntry : ktDestructuringDeclaration.getEntries()) {
            ResolvedCall<?> resolvedCall = (ResolvedCall) this.bindingContext.get(BindingContext.COMPONENT_RESOLVED_CALL, ktDestructuringDeclarationEntry);
            Call callMakeFakeCall = makeFakeCall(receiverValue);
            VariableDescriptor variableDescriptorNotNull = getVariableDescriptorNotNull(ktDestructuringDeclarationEntry);
            if (!UnderscoreUtilKt.isSingleUnderscore(ktDestructuringDeclarationEntry)) {
                if (z && (variableDescriptorNotNull instanceof PropertyDescriptor)) {
                    intermediateValueForProperty((PropertyDescriptor) variableDescriptorNotNull, true, false, null, true, StackValue.LOCAL_0, null, false).store(invokeFunction(callMakeFakeCall, resolvedCall, stackValue), this.v);
                } else {
                    initializeLocalVariable(ktDestructuringDeclarationEntry, invokeFunction(callMakeFakeCall, resolvedCall, stackValue));
                }
            }
        }
    }

    private StackValue getVariableMetadataValue(VariableDescriptorWithAccessors variableDescriptorWithAccessors) {
        if (variableDescriptorWithAccessors == null) {
            $$$reportNull$$$0(ParserBasicInformation.SCOPE_UBOUND);
        }
        StackValue stackValueFindLocalOrCapturedValue = findLocalOrCapturedValue(CodegenBinding.getDelegatedLocalVariableMetadata(variableDescriptorWithAccessors, this.bindingContext));
        if (stackValueFindLocalOrCapturedValue == null) {
            $$$reportNull$$$0(ParserBasicInformation.SCOPE_SIZE);
        }
        return stackValueFindLocalOrCapturedValue;
    }

    private StackValue adjustVariableValue(StackValue stackValue, DeclarationDescriptor declarationDescriptor) {
        if (stackValue == null) {
            $$$reportNull$$$0(292);
        }
        if (JvmCodegenUtil.isDelegatedLocalVariable(declarationDescriptor)) {
            VariableDescriptorWithAccessors variableDescriptorWithAccessors = (VariableDescriptorWithAccessors) declarationDescriptor;
            return delegatedVariableValue(stackValue, getVariableMetadataValue(variableDescriptorWithAccessors), variableDescriptorWithAccessors, this.typeMapper);
        }
        if (stackValue == null) {
            $$$reportNull$$$0(293);
        }
        return stackValue;
    }

    private void initializeLocalVariable(KtVariableDeclaration ktVariableDeclaration, StackValue stackValue) {
        StackValue stackValueShared;
        if (ktVariableDeclaration == null) {
            $$$reportNull$$$0(294);
        }
        LocalVariableDescriptor localVariableDescriptor = (LocalVariableDescriptor) getVariableDescriptorNotNull(ktVariableDeclaration);
        if (KtPsiUtil.isScriptDeclaration(ktVariableDeclaration)) {
            return;
        }
        int iLookupLocalIndex = lookupLocalIndex(localVariableDescriptor);
        if (iLookupLocalIndex < 0) {
            throw new IllegalStateException("Local variable not found for " + localVariableDescriptor);
        }
        Type sharedVarType = this.typeMapper.getSharedVarType(localVariableDescriptor);
        Type variableTypeNoSharing = getVariableTypeNoSharing(localVariableDescriptor);
        KotlinType propertyDelegateType = JvmCodegenUtil.getPropertyDelegateType(localVariableDescriptor, this.bindingContext);
        if (sharedVarType == null) {
            stackValueShared = StackValue.local(iLookupLocalIndex, variableTypeNoSharing, localVariableDescriptor, propertyDelegateType);
        } else {
            stackValueShared = StackValue.shared(iLookupLocalIndex, variableTypeNoSharing, localVariableDescriptor, propertyDelegateType);
        }
        stackValueShared.putReceiver(this.v, false);
        if (localVariableDescriptor.isLateInit()) {
            this.v.aconst(null);
            stackValueShared.storeSelector(stackValueShared.type, stackValueShared.kotlinType, this.v);
            return;
        }
        stackValue.put(stackValue.type, stackValue.kotlinType, this.v);
        markLineNumber(ktVariableDeclaration, false);
        Type type = stackValue.type;
        KotlinType kotlinType = stackValue.kotlinType;
        if (JvmCodegenUtil.isDelegatedLocalVariable(localVariableDescriptor)) {
            StackValue variableMetadataValue = getVariableMetadataValue(localVariableDescriptor);
            initializePropertyMetadata((KtProperty) ktVariableDeclaration, localVariableDescriptor, variableMetadataValue);
            ResolvedCall<FunctionDescriptor> resolvedCall = (ResolvedCall) this.bindingContext.get(BindingContext.PROVIDE_DELEGATE_RESOLVED_CALL, localVariableDescriptor);
            if (resolvedCall != null) {
                StackValue stackValueGenerateProvideDelegateCallForLocalVariable = generateProvideDelegateCallForLocalVariable(stackValue, variableMetadataValue, resolvedCall);
                type = stackValueGenerateProvideDelegateCallForLocalVariable.type;
                kotlinType = stackValueGenerateProvideDelegateCallForLocalVariable.kotlinType;
            }
        }
        stackValueShared.storeSelector(type, kotlinType, this.v);
    }

    private void initializeLocalVariableWithFakeDefaultValue(KtProperty ktProperty) {
        if (ktProperty == null) {
            $$$reportNull$$$0(295);
        }
        LocalVariableDescriptor localVariableDescriptor = (LocalVariableDescriptor) getVariableDescriptorNotNull(ktProperty);
        if (BindingContextUtils.isBoxedLocalCapturedInClosure(this.bindingContext, localVariableDescriptor)) {
            return;
        }
        Type typeMapType = this.typeMapper.mapType(localVariableDescriptor.getType());
        if (typeMapType == Type.VOID_TYPE) {
            return;
        }
        int iLookupLocalIndex = lookupLocalIndex(localVariableDescriptor);
        switch (typeMapType.getSort()) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                this.v.iconst(0);
                break;
            case 6:
                this.v.fconst(0.0f);
                break;
            case 7:
                this.v.lconst(0L);
                break;
            case 8:
                this.v.dconst(0.0d);
                break;
            default:
                this.v.aconst(null);
                break;
        }
        this.v.store(iLookupLocalIndex, typeMapType);
    }

    private StackValue generateProvideDelegateCallForLocalVariable(StackValue stackValue, final StackValue stackValue2, ResolvedCall<FunctionDescriptor> resolvedCall) {
        if (stackValue == null) {
            $$$reportNull$$$0(296);
        }
        StackValue stackValueOnStack = StackValue.onStack(stackValue.type, stackValue.kotlinType);
        List<? extends ValueArgument> valueArguments = resolvedCall.getCall().getValueArguments();
        this.tempVariables.put(valueArguments.get(0).getCallElement(), StackValue.constant(null, AsmTypes.OBJECT_TYPE));
        this.tempVariables.put(valueArguments.get(1).getCallElement(), new StackValue(AsmTypes.K_PROPERTY_TYPE) { // from class: org.jetbrains.kotlin.codegen.ExpressionCodegen.2
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[3];
                if (i != 1) {
                    objArr[0] = ModuleXmlParser.TYPE;
                } else {
                    objArr[0] = RegisterSpec.PREFIX;
                }
                objArr[1] = "org/jetbrains/kotlin/codegen/ExpressionCodegen$2";
                objArr[2] = "putSelector";
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
            }

            @Override // org.jetbrains.kotlin.codegen.StackValue
            public void putSelector(Type type, KotlinType kotlinType, InstructionAdapter instructionAdapter) {
                if (type == null) {
                    $$$reportNull$$$0(0);
                }
                if (instructionAdapter == null) {
                    $$$reportNull$$$0(1);
                }
                stackValue2.put(type, kotlinType, instructionAdapter);
            }
        });
        StackValue stackValueInvokeFunction = invokeFunction(resolvedCall, stackValueOnStack);
        stackValueInvokeFunction.put(stackValueInvokeFunction.type, stackValueInvokeFunction.kotlinType, this.v);
        this.tempVariables.remove(valueArguments.get(0).getCallElement());
        this.tempVariables.remove(valueArguments.get(1).getCallElement());
        if (stackValueInvokeFunction == null) {
            $$$reportNull$$$0(297);
        }
        return stackValueInvokeFunction;
    }

    public VariableDescriptor getVariableDescriptorNotNull(KtElement ktElement) {
        if (ktElement == null) {
            $$$reportNull$$$0(298);
        }
        VariableDescriptor variableDescriptor = (VariableDescriptor) this.bindingContext.get(BindingContext.VARIABLE, ktElement);
        if (variableDescriptor == null) {
            $$$reportNull$$$0(299);
        }
        return variableDescriptor;
    }

    private void initializePropertyMetadata(KtProperty ktProperty, LocalVariableDescriptor localVariableDescriptor, StackValue stackValue) {
        StackValue delegatedPropertyMetadata;
        if (ktProperty == null) {
            $$$reportNull$$$0(300);
        }
        if (localVariableDescriptor == null) {
            $$$reportNull$$$0(301);
        }
        if (stackValue == null) {
            $$$reportNull$$$0(302);
        }
        if (PropertyCodegen.isDelegatedPropertyWithOptimizedMetadata(localVariableDescriptor, this.bindingContext)) {
            delegatedPropertyMetadata = PropertyCodegen.getOptimizedDelegatedPropertyMetadataValue();
        } else if (this.context.getFunctionDescriptor().isInline()) {
            delegatedPropertyMetadata = generatePropertyReference(ktProperty.getDelegate(), localVariableDescriptor, localVariableDescriptor, null);
        } else {
            delegatedPropertyMetadata = PropertyCodegen.getDelegatedPropertyMetadata(localVariableDescriptor, this.bindingContext);
        }
        delegatedPropertyMetadata.put(AsmTypes.K_PROPERTY_TYPE, null, this.v);
        stackValue.storeSelector(AsmTypes.K_PROPERTY_TYPE, null, this.v);
    }

    private StackValue generateNewCall(KtCallExpression ktCallExpression, ResolvedCall<?> resolvedCall) {
        if (ktCallExpression == null) {
            $$$reportNull$$$0(303);
        }
        if (resolvedCall == null) {
            $$$reportNull$$$0(304);
        }
        Type typeExpressionType = expressionType(ktCallExpression);
        if (typeExpressionType.getSort() == 9 && KotlinBuiltIns.isArrayOrPrimitiveArray(kotlinType(ktCallExpression))) {
            StackValue stackValueGenerateNewArray = generateNewArray(ktCallExpression, this.bindingContext.getType(ktCallExpression), resolvedCall);
            if (stackValueGenerateNewArray == null) {
                $$$reportNull$$$0(305);
            }
            return stackValueGenerateNewArray;
        }
        StackValue stackValueGenerateConstructorCall = generateConstructorCall(resolvedCall, typeExpressionType);
        if (stackValueGenerateConstructorCall == null) {
            $$$reportNull$$$0(306);
        }
        return stackValueGenerateConstructorCall;
    }

    public ClassConstructorDescriptor getConstructorDescriptor(ResolvedCall<?> resolvedCall) {
        if (resolvedCall == null) {
            $$$reportNull$$$0(307);
        }
        ClassConstructorDescriptor classConstructorDescriptor = (ClassConstructorDescriptor) accessibleFunctionDescriptor(resolvedCall);
        if (classConstructorDescriptor == null) {
            $$$reportNull$$$0(308);
        }
        return classConstructorDescriptor;
    }

    private static ReceiverValue getConstructorReceiver(ResolvedCall<?> resolvedCall) {
        if (resolvedCall == null) {
            $$$reportNull$$$0(309);
        }
        CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
        if (resultingDescriptor.getExtensionReceiverParameter() != null) {
            return resolvedCall.getExtensionReceiver();
        }
        if (resultingDescriptor.mo6753getDispatchReceiverParameter() != null) {
            return resolvedCall.getDispatchReceiver();
        }
        return null;
    }

    public StackValue generateConstructorCall(final ResolvedCall<?> resolvedCall, final Type type) {
        if (resolvedCall == null) {
            $$$reportNull$$$0(310);
        }
        if (type == null) {
            $$$reportNull$$$0(311);
        }
        StackValue stackValueFunctionCall = StackValue.functionCall(type, resolvedCall.getResultingDescriptor().getReturnType(), new Function1() { // from class: org.jetbrains.kotlin.codegen.ExpressionCodegen$$ExternalSyntheticLambda36
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return this.f$0.m6395xf13061fb(resolvedCall, type, (InstructionAdapter) obj);
            }
        });
        if (stackValueFunctionCall == null) {
            $$$reportNull$$$0(312);
        }
        return stackValueFunctionCall;
    }

    /* JADX INFO: renamed from: lambda$generateConstructorCall$33$org-jetbrains-kotlin-codegen-ExpressionCodegen, reason: not valid java name */
    /* synthetic */ Unit m6395xf13061fb(ResolvedCall resolvedCall, Type type, InstructionAdapter instructionAdapter) {
        ClassConstructorDescriptor constructorDescriptor = getConstructorDescriptor(resolvedCall);
        ReceiverParameterDescriptor dispatchReceiverParameter = constructorDescriptor.mo6753getDispatchReceiverParameter();
        ClassDescriptor containingDeclaration = constructorDescriptor.getContainingDeclaration();
        if (!InlineClassesUtilsKt.isInlineClass(containingDeclaration)) {
            instructionAdapter.anew(type);
            instructionAdapter.dup();
        }
        if (dispatchReceiverParameter != null) {
            KotlinType type2 = dispatchReceiverParameter.getType();
            Type typeMapType = this.typeMapper.mapType(type2);
            ReceiverValue constructorReceiver = getConstructorReceiver(resolvedCall);
            generateReceiverValue(constructorReceiver, containingDeclaration.getIsInner() && (constructorReceiver instanceof ImplicitClassReceiver)).put(typeMapType, type2, instructionAdapter);
        }
        pushClosureOnStack(containingDeclaration, dispatchReceiverParameter == null, this.defaultCallGenerator, null);
        ClassConstructorDescriptor classConstructorDescriptor = (ClassConstructorDescriptor) SamCodegenUtil.resolveSamAdapter(constructorDescriptor);
        invokeMethodWithArguments(this.typeMapper.mapToCallableMethod(classConstructorDescriptor, false, InlineClassesUtilsKt.isInlineClass(classConstructorDescriptor.getContainingDeclaration()) ? OwnerKind.ERASED_INLINE_CLASS : null), resolvedCall, StackValue.none());
        return Unit.INSTANCE;
    }

    public StackValue generateNewArray(KtCallExpression ktCallExpression, final KotlinType kotlinType, ResolvedCall<?> resolvedCall) {
        if (ktCallExpression == null) {
            $$$reportNull$$$0(313);
        }
        if (kotlinType == null) {
            $$$reportNull$$$0(314);
        }
        if (resolvedCall == null) {
            $$$reportNull$$$0(315);
        }
        List<KtValueArgument> valueArguments = ktCallExpression.getValueArguments();
        if (valueArguments.size() == 1) {
            final KtExpression argumentExpression = valueArguments.get(0).getArgumentExpression();
            return StackValue.operation(this.typeMapper.mapType(kotlinType), new Function1() { // from class: org.jetbrains.kotlin.codegen.ExpressionCodegen$$ExternalSyntheticLambda10
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return this.f$0.m6399xdacf0b9f(argumentExpression, kotlinType, (InstructionAdapter) obj);
                }
            });
        }
        return invokeFunction(resolvedCall, StackValue.none());
    }

    /* JADX INFO: renamed from: lambda$generateNewArray$34$org-jetbrains-kotlin-codegen-ExpressionCodegen, reason: not valid java name */
    /* synthetic */ Unit m6399xdacf0b9f(KtExpression ktExpression, KotlinType kotlinType, InstructionAdapter instructionAdapter) {
        gen(ktExpression, Type.INT_TYPE);
        newArrayInstruction(kotlinType);
        return Unit.INSTANCE;
    }

    public void newArrayInstruction(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(316);
        }
        if (KotlinBuiltIns.isArray(kotlinType)) {
            KotlinType type = kotlinType.getArguments().get(0).getNullableAnyType();
            BaseExpressionCodegenKt.putReifiedOperationMarkerIfTypeIsReifiedParameter(this, type, ReifiedTypeInliner.OperationKind.NEW_ARRAY);
            this.v.newarray(AsmUtil.boxType(this.typeMapper.mapTypeAsDeclaration(type)));
        } else {
            this.v.newarray(AsmUtil.correctElementType(this.typeMapper.mapType(kotlinType)));
        }
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public StackValue visitArrayAccessExpression(KtArrayAccessExpression ktArrayAccessExpression, StackValue stackValue) {
        KotlinType type;
        Type typeCorrectElementType;
        if (ktArrayAccessExpression == null) {
            $$$reportNull$$$0(TypedValues.AttributesType.TYPE_EASING);
        }
        KtExpression arrayExpression = ktArrayAccessExpression.getArrayExpression();
        KotlinType type2 = arrayExpression != null ? this.bindingContext.getType(arrayExpression) : null;
        Type typeExpressionType = expressionType(arrayExpression);
        List<KtExpression> indexExpressions = ktArrayAccessExpression.getIndexExpressions();
        FunctionDescriptor functionDescriptor = (FunctionDescriptor) this.bindingContext.get(BindingContext.REFERENCE_TARGET, ktArrayAccessExpression);
        boolean z = type2 != null && InlineClassesUtilsKt.isInlineClassType(type2);
        if (typeExpressionType.getSort() == 9 && indexExpressions.size() == 1 && KotlinBuiltIns.isInt(functionDescriptor.getValueParameters().get(0).getType()) && !z) {
            KotlinType arrayElementType = this.state.getModule().getBuiltIns().getArrayElementType(type2);
            if (KotlinBuiltIns.isArray(type2)) {
                typeCorrectElementType = DescriptorAsmUtil.boxType(asmType(arrayElementType), arrayElementType, this.typeMapper);
            } else {
                typeCorrectElementType = AsmUtil.correctElementType(typeExpressionType);
            }
            return StackValue.arrayElement(typeCorrectElementType, arrayElementType, genLazy(arrayExpression, typeExpressionType), genLazy(indexExpressions.get(0), Type.INT_TYPE));
        }
        ResolvedCall<?> resolvedCall = (ResolvedCall) this.bindingContext.get(BindingContext.INDEXED_LVALUE_SET, ktArrayAccessExpression);
        ResolvedCall<?> resolvedCall2 = (ResolvedCall) this.bindingContext.get(BindingContext.INDEXED_LVALUE_GET, ktArrayAccessExpression);
        boolean zEquals = OperatorNameConventions.GET.equals(functionDescriptor.getShortName());
        ResolvedCall<?> resolvedCall3 = zEquals ? resolvedCall2 : resolvedCall;
        Callable callableResolveToCallable = resolveToCallable(accessibleFunctionDescriptor(resolvedCall3), false, resolvedCall3);
        CallableMethod callableMethodMapToCallableMethod = this.typeMapper.mapToCallableMethod(SamCodegenUtil.resolveSamAdapter(functionDescriptor), false);
        Type[] parameterTypes = callableMethodMapToCallableMethod.getParameterTypes();
        StackValue.CollectionElementReceiver collectionElementReceiverCreateCollectionElementReceiver = createCollectionElementReceiver(ktArrayAccessExpression, stackValue, functionDescriptor, zEquals, resolvedCall2, resolvedCall, callableResolveToCallable);
        Type returnType = zEquals ? callableMethodMapToCallableMethod.getReturnType() : (Type) ArrayUtil.getLastElement(parameterTypes);
        if (zEquals) {
            type = functionDescriptor.getOriginal().getReturnType();
        } else {
            type = ((ValueParameterDescriptor) CollectionsKt.last((List) functionDescriptor.getOriginal().getValueParameters())).getType();
        }
        return StackValue.collectionElement(collectionElementReceiverCreateCollectionElementReceiver, returnType, type, resolvedCall2, resolvedCall, this);
    }

    private StackValue.CollectionElementReceiver createCollectionElementReceiver(KtArrayAccessExpression ktArrayAccessExpression, StackValue stackValue, FunctionDescriptor functionDescriptor, boolean z, ResolvedCall<FunctionDescriptor> resolvedCall, ResolvedCall<FunctionDescriptor> resolvedCall2, Callable callable) {
        if (ktArrayAccessExpression == null) {
            $$$reportNull$$$0(TypedValues.AttributesType.TYPE_PIVOT_TARGET);
        }
        if (stackValue == null) {
            $$$reportNull$$$0(319);
        }
        if (functionDescriptor == null) {
            $$$reportNull$$$0(320);
        }
        if (callable == null) {
            $$$reportNull$$$0(MediaError.DetailedErrorCode.DASH_NETWORK);
        }
        List<ResolvedValueArgument> valueArgumentsByIndex = (z ? resolvedCall : resolvedCall2).getValueArgumentsByIndex();
        if (!z) {
            valueArgumentsByIndex.remove(valueArgumentsByIndex.size() - 1);
        }
        return new StackValue.CollectionElementReceiver(callable, stackValue, resolvedCall, resolvedCall2, z, this, valueArgumentsByIndex);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public StackValue visitThrowExpression(final KtThrowExpression ktThrowExpression, StackValue stackValue) {
        if (ktThrowExpression == null) {
            $$$reportNull$$$0(MediaError.DetailedErrorCode.DASH_NO_INIT);
        }
        return StackValue.operation(Type.VOID_TYPE, getNothingType(), new Function1() { // from class: org.jetbrains.kotlin.codegen.ExpressionCodegen$$ExternalSyntheticLambda20
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return this.f$0.m6413x63879be3(ktThrowExpression, (InstructionAdapter) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$visitThrowExpression$35$org-jetbrains-kotlin-codegen-ExpressionCodegen, reason: not valid java name */
    /* synthetic */ Unit m6413x63879be3(KtThrowExpression ktThrowExpression, InstructionAdapter instructionAdapter) {
        gen(ktThrowExpression.getThrownExpression(), AsmTypes.JAVA_THROWABLE_TYPE);
        this.v.athrow();
        return Unit.INSTANCE;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public StackValue visitThisExpression(KtThisExpression ktThisExpression, StackValue stackValue) {
        if (ktThisExpression == null) {
            $$$reportNull$$$0(323);
        }
        DeclarationDescriptor declarationDescriptor = (DeclarationDescriptor) this.bindingContext.get(BindingContext.REFERENCE_TARGET, ktThisExpression.getInstanceReference());
        if (declarationDescriptor instanceof ClassDescriptor) {
            return generateInstanceReceiver((ClassDescriptor) declarationDescriptor, false, true);
        }
        if (declarationDescriptor instanceof CallableDescriptor) {
            return generateExtensionReceiver((CallableDescriptor) declarationDescriptor);
        }
        throw new UnsupportedOperationException("Neither this nor receiver: " + declarationDescriptor + ktThisExpression.getMyParent().getContainingFile().getText());
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public StackValue visitTryExpression(KtTryExpression ktTryExpression, StackValue stackValue) {
        if (ktTryExpression == null) {
            $$$reportNull$$$0(324);
        }
        return generateTryExpression(ktTryExpression, false);
    }

    public StackValue generateTryExpression(final KtTryExpression ktTryExpression, final boolean z) {
        Type typeExpressionType = z ? Type.VOID_TYPE : expressionType(ktTryExpression);
        final KotlinType kotlinType = z ? null : kotlinType(ktTryExpression);
        final Type type = typeExpressionType;
        return StackValue.operation(typeExpressionType, kotlinType, new Function1() { // from class: org.jetbrains.kotlin.codegen.ExpressionCodegen$$ExternalSyntheticLambda15
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return this.f$0.m6401x67916f93(ktTryExpression, type, kotlinType, z, (InstructionAdapter) obj);
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX INFO: renamed from: lambda$generateTryExpression$36$org-jetbrains-kotlin-codegen-ExpressionCodegen, reason: not valid java name */
    /* synthetic */ Unit m6401x67916f93(KtTryExpression ktTryExpression, Type type, KotlinType kotlinType, boolean z, InstructionAdapter instructionAdapter) {
        TryBlockStackElement tryBlockStackElement;
        TryWithFinallyBlockStackElement tryWithFinallyBlockStackElement;
        int iEnterTemp;
        Type type2 = type;
        KotlinType kotlinType2 = kotlinType;
        KtFinallySection finallyBlock = ktTryExpression.getFinallyBlock();
        if (finallyBlock != null) {
            tryBlockStackElement = new TryWithFinallyBlockStackElement(ktTryExpression);
            this.blockStackElements.push(tryBlockStackElement);
            tryWithFinallyBlockStackElement = tryBlockStackElement;
        } else {
            tryBlockStackElement = new TryBlockStackElement();
            this.blockStackElements.push(tryBlockStackElement);
            tryWithFinallyBlockStackElement = 0;
        }
        Label label = new Label();
        instructionAdapter.mark(label);
        instructionAdapter.nop();
        gen(ktTryExpression.getTryBlock(), type2, kotlinType2);
        if (z) {
            iEnterTemp = -1;
        } else {
            iEnterTemp = this.myFrameMap.enterTemp(type2);
            instructionAdapter.store(iEnterTemp, type2);
        }
        int i = iEnterTemp;
        Label label2 = new Label();
        instructionAdapter.mark(label2);
        List<Label> currentCatchIntervals = getCurrentCatchIntervals(tryBlockStackElement, label, label2);
        Label label3 = new Label();
        genFinallyBlockOrGoto(tryWithFinallyBlockStackElement, label3, null, new ArrayList<>());
        List<KtCatchClause> catchClauses = ktTryExpression.getCatchClauses();
        int size = catchClauses.size();
        boolean z2 = false;
        int i2 = 0;
        while (i2 < size) {
            KtCatchClause ktCatchClause = catchClauses.get(i2);
            Label label4 = label3;
            Label label5 = new Label();
            instructionAdapter.mark(label5);
            int i3 = size;
            KtElement catchBody = ktCatchClause.getCatchBody();
            if (catchBody != null) {
                markLineNumber(catchBody, z2);
            }
            List<KtCatchClause> list = catchClauses;
            VariableDescriptor variableDescriptor = (VariableDescriptor) this.bindingContext.get(BindingContext.VALUE_PARAMETER, ktCatchClause.getCatchParameter());
            Type typeAsmType = asmType(variableDescriptor.getType());
            this.myFrameMap.enter(variableDescriptor, typeAsmType);
            int iLookupLocalIndex = lookupLocalIndex(variableDescriptor);
            instructionAdapter.store(iLookupLocalIndex, typeAsmType);
            List<Label> list2 = currentCatchIntervals;
            Label label6 = new Label();
            instructionAdapter.mark(label6);
            gen(catchBody, type2, kotlinType2);
            if (!z) {
                instructionAdapter.store(i, type2);
            }
            this.myFrameMap.leave(variableDescriptor);
            Label label7 = new Label();
            instructionAdapter.mark(label7);
            int i4 = i;
            Label label8 = label;
            instructionAdapter.visitLocalVariable(variableDescriptor.getShortName().asString(), typeAsmType.getDescriptor(), null, label6, label7, iLookupLocalIndex);
            genFinallyBlockOrGoto(tryWithFinallyBlockStackElement, (i2 == i3 + (-1) && finallyBlock == null) ? null : label4, null, new ArrayList<>());
            generateExceptionTable(label5, list2, typeAsmType.getInternalName());
            i2++;
            type2 = type;
            label = label8;
            currentCatchIntervals = list2;
            label3 = label4;
            size = i3;
            z2 = false;
            catchClauses = list;
            i = i4;
            kotlinType2 = kotlinType;
        }
        Label label9 = label3;
        int i5 = i;
        Label label10 = label;
        if (finallyBlock != null) {
            Label label11 = new Label();
            instructionAdapter.mark(label11);
            int iEnterTemp2 = this.myFrameMap.enterTemp(AsmTypes.JAVA_THROWABLE_TYPE);
            instructionAdapter.store(iEnterTemp2, AsmTypes.JAVA_THROWABLE_TYPE);
            Label label12 = new Label();
            instructionAdapter.mark(label12);
            List<Label> currentCatchIntervals2 = getCurrentCatchIntervals(tryWithFinallyBlockStackElement, label10, label12);
            genFinallyBlockOrGoto(tryWithFinallyBlockStackElement, null, null, new ArrayList<>());
            instructionAdapter.load(iEnterTemp2, AsmTypes.JAVA_THROWABLE_TYPE);
            this.myFrameMap.leaveTemp(AsmTypes.JAVA_THROWABLE_TYPE);
            instructionAdapter.athrow();
            generateExceptionTable(label11, currentCatchIntervals2, null);
        }
        markLineNumber(ktTryExpression, z);
        instructionAdapter.mark(label9);
        if (!z) {
            instructionAdapter.load(i5, type);
            this.myFrameMap.leaveTemp(type);
        }
        this.blockStackElements.pop();
        return Unit.INSTANCE;
    }

    private void generateExceptionTable(Label label, List<Label> list, String str) {
        if (label == null) {
            $$$reportNull$$$0(325);
        }
        if (list == null) {
            $$$reportNull$$$0(326);
        }
        for (int i = 0; i < list.size(); i += 2) {
            Label label2 = list.get(i);
            Label label3 = list.get(i + 1);
            if (label2.info == null || label3.info == null || label.info == null) {
                throw new IllegalStateException("All labels should have nodes added to instruction list");
            }
            this.v.visitTryCatchBlock(label2, label3, label, str);
        }
    }

    private static List<Label> getCurrentCatchIntervals(TryBlockStackElement tryBlockStackElement, Label label, Label label2) {
        List listEmptyList;
        if (label == null) {
            $$$reportNull$$$0(327);
        }
        if (label2 == null) {
            $$$reportNull$$$0(328);
        }
        if (tryBlockStackElement != null) {
            listEmptyList = new ArrayList(tryBlockStackElement.gaps);
        } else {
            listEmptyList = Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList(listEmptyList.size() + 2);
        arrayList.add(label);
        arrayList.addAll(listEmptyList);
        arrayList.add(label2);
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public StackValue visitBinaryWithTypeRHSExpression(KtBinaryExpressionWithTypeRHS ktBinaryExpressionWithTypeRHS, StackValue stackValue) {
        Type typeBoxType;
        if (ktBinaryExpressionWithTypeRHS == null) {
            $$$reportNull$$$0(330);
        }
        KtExpression left = ktBinaryExpressionWithTypeRHS.getLeft();
        final IElementType referencedNameElementType = ktBinaryExpressionWithTypeRHS.getOperationReference().getReferencedNameElementType();
        final KotlinType kotlinType = (KotlinType) this.bindingContext.get(BindingContext.TYPE, ktBinaryExpressionWithTypeRHS.getRight());
        final StackValue stackValueGenQualified = genQualified(stackValue, left);
        KotlinType kotlinType2 = stackValueGenQualified.kotlinType;
        if (kotlinType2 != null && InlineClassesUtilsKt.isInlineClassType(kotlinType2)) {
            typeBoxType = this.typeMapper.mapTypeAsDeclaration(kotlinType2);
        } else {
            typeBoxType = AsmUtil.boxType(stackValueGenQualified.type);
        }
        final Type type = typeBoxType;
        final Type typeBoxType2 = AsmUtil.boxType(this.typeMapper.mapTypeAsDeclaration(kotlinType));
        return StackValue.operation(typeBoxType2, kotlinType, new Function1() { // from class: org.jetbrains.kotlin.codegen.ExpressionCodegen$$ExternalSyntheticLambda30
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return this.f$0.m6405x57a071b7(stackValueGenQualified, type, referencedNameElementType, kotlinType, typeBoxType2, (InstructionAdapter) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$visitBinaryWithTypeRHSExpression$37$org-jetbrains-kotlin-codegen-ExpressionCodegen, reason: not valid java name */
    /* synthetic */ Unit m6405x57a071b7(StackValue stackValue, Type type, IElementType iElementType, KotlinType kotlinType, Type type2, InstructionAdapter instructionAdapter) {
        stackValue.put(type, stackValue.kotlinType, instructionAdapter);
        if (stackValue.type == Type.VOID_TYPE) {
            StackValue.putUnitInstance(instructionAdapter);
        }
        boolean z = iElementType == KtTokens.AS_SAFE;
        if (TypeUtils.isReifiedTypeParameter(kotlinType)) {
            BaseExpressionCodegenKt.putReifiedOperationMarkerIfTypeIsReifiedParameter(this, kotlinType, z ? ReifiedTypeInliner.OperationKind.SAFE_AS : ReifiedTypeInliner.OperationKind.AS);
            instructionAdapter.checkcast(type2);
            return Unit.INSTANCE;
        }
        CodegenUtilKt.generateAsCast(instructionAdapter, kotlinType, type2, z, this.state.getConfig().getUnifiedNullChecks());
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public StackValue visitIsExpression(KtIsExpression ktIsExpression, StackValue stackValue) {
        if (ktIsExpression == null) {
            $$$reportNull$$$0(MediaError.DetailedErrorCode.SMOOTH_NETWORK);
        }
        return generateIsCheck(StackValue.expression(AsmTypes.OBJECT_TYPE, ktIsExpression.getLeftHandSide(), this), ktIsExpression.getTypeReference(), ktIsExpression.isNegated());
    }

    private StackValue generateExpressionMatch(StackValue stackValue, KtExpression ktExpression, KotlinType kotlinType, KtExpression ktExpression2) {
        if (stackValue != null) {
            return generateEquals(ktExpression, ktExpression2, KtTokens.EQEQ, stackValue, kotlinType, (PrimitiveNumericComparisonInfo) this.bindingContext.get(BindingContext.PRIMITIVE_NUMERIC_COMPARISON_INFO, ktExpression2));
        }
        return gen(ktExpression2);
    }

    private StackValue generateIsCheck(StackValue stackValue, KtTypeReference ktTypeReference, boolean z) {
        KotlinType kotlinType = (KotlinType) this.bindingContext.get(BindingContext.TYPE, ktTypeReference);
        markStartLineNumber(ktTypeReference);
        StackValue stackValueGenerateIsCheck = generateIsCheck(stackValue, kotlinType, false);
        return z ? StackValue.not(stackValueGenerateIsCheck) : stackValueGenerateIsCheck;
    }

    private StackValue generateIsCheck(final StackValue stackValue, final KotlinType kotlinType, final boolean z) {
        Type typeMapTypeAsDeclaration;
        KotlinType kotlinType2 = stackValue.kotlinType;
        if (kotlinType2 != null && InlineClassesUtilsKt.isInlineClassType(kotlinType2)) {
            typeMapTypeAsDeclaration = this.typeMapper.mapTypeAsDeclaration(kotlinType2);
        } else {
            typeMapTypeAsDeclaration = AsmTypes.OBJECT_TYPE;
        }
        final Type type = typeMapTypeAsDeclaration;
        return StackValue.operation(Type.BOOLEAN_TYPE, new Function1() { // from class: org.jetbrains.kotlin.codegen.ExpressionCodegen$$ExternalSyntheticLambda13
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return this.f$0.m6398x2429c45c(stackValue, type, z, kotlinType, (InstructionAdapter) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$generateIsCheck$38$org-jetbrains-kotlin-codegen-ExpressionCodegen, reason: not valid java name */
    /* synthetic */ Unit m6398x2429c45c(StackValue stackValue, Type type, boolean z, KotlinType kotlinType, InstructionAdapter instructionAdapter) {
        stackValue.put(type, stackValue.kotlinType, instructionAdapter);
        if (z) {
            instructionAdapter.dup();
        }
        Type typeBoxType = AsmUtil.boxType(this.typeMapper.mapTypeAsDeclaration(kotlinType));
        if (TypeUtils.isReifiedTypeParameter(kotlinType)) {
            BaseExpressionCodegenKt.putReifiedOperationMarkerIfTypeIsReifiedParameter(this, kotlinType, ReifiedTypeInliner.OperationKind.IS);
            instructionAdapter.instanceOf(typeBoxType);
            return null;
        }
        CodegenUtilKt.generateIsCheck(instructionAdapter, kotlinType, typeBoxType);
        return null;
    }

    @Override // org.jetbrains.kotlin.codegen.BaseExpressionCodegen
    public void propagateChildReifiedTypeParametersUsages(ReifiedTypeParametersUsages reifiedTypeParametersUsages) {
        if (reifiedTypeParametersUsages == null) {
            $$$reportNull$$$0(MediaError.DetailedErrorCode.SMOOTH_NO_MEDIA_DATA);
        }
        this.parentCodegen.getReifiedTypeParametersUsages().propagateChildUsagesWithinContext(reifiedTypeParametersUsages, new Function0() { // from class: org.jetbrains.kotlin.codegen.ExpressionCodegen$$ExternalSyntheticLambda11
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return this.f$0.m6404x4e133217();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$propagateChildReifiedTypeParametersUsages$40$org-jetbrains-kotlin-codegen-ExpressionCodegen, reason: not valid java name */
    /* synthetic */ Set m6404x4e133217() {
        return (Set) this.context.getContextDescriptor().getTypeParameters().stream().filter(new Predicate() { // from class: org.jetbrains.kotlin.codegen.ExpressionCodegen$$ExternalSyntheticLambda17
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((TypeParameterDescriptor) obj).isReified();
            }
        }).map(new java.util.function.Function() { // from class: org.jetbrains.kotlin.codegen.ExpressionCodegen$$ExternalSyntheticLambda18
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((TypeParameterDescriptor) obj).getShortName().asString();
            }
        }).collect(Collectors.toSet());
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public StackValue visitWhenExpression(KtWhenExpression ktWhenExpression, StackValue stackValue) {
        if (ktWhenExpression == null) {
            $$$reportNull$$$0(333);
        }
        return generateWhenExpression(ktWhenExpression, false);
    }

    public StackValue generateWhenExpression(final KtWhenExpression ktWhenExpression, final boolean z) {
        Type typeExpressionType = z ? Type.VOID_TYPE : expressionType(ktWhenExpression);
        final KotlinType kotlinType = z ? null : kotlinType(ktWhenExpression);
        final Type type = typeExpressionType;
        return StackValue.operation(typeExpressionType, kotlinType, new Function1() { // from class: org.jetbrains.kotlin.codegen.ExpressionCodegen$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return this.f$0.m6402x2d2eaf1c(ktWhenExpression, z, type, kotlinType, (InstructionAdapter) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$generateWhenExpression$41$org-jetbrains-kotlin-codegen-ExpressionCodegen, reason: not valid java name */
    /* synthetic */ Unit m6402x2d2eaf1c(KtWhenExpression ktWhenExpression, boolean z, Type type, KotlinType kotlinType, InstructionAdapter instructionAdapter) {
        int i;
        Type type2;
        VariableDescriptor variableDescriptor;
        KotlinType kotlinType2;
        Label label;
        KotlinType kotlinType3;
        Type type3;
        KtExpression ktExpression;
        Label label2;
        Label label3;
        KtProperty subjectVariable = ktWhenExpression.getSubjectVariable();
        KtExpression subjectExpression = ktWhenExpression.getSubjectExpression();
        if (subjectVariable == null && subjectExpression == null) {
            instructionAdapter.nop();
        }
        SwitchCodegen switchCodegenBuildAppropriateSwitchCodegenIfPossible = this.switchCodegenProvider.buildAppropriateSwitchCodegenIfPossible(ktWhenExpression, z, CodegenUtil.isExhaustive(this.bindingContext, ktWhenExpression, z));
        if (switchCodegenBuildAppropriateSwitchCodegenIfPossible != null) {
            switchCodegenBuildAppropriateSwitchCodegenIfPossible.generate();
            return null;
        }
        if (subjectVariable != null) {
            VariableDescriptor variableDescriptor2 = (VariableDescriptor) this.bindingContext.get(BindingContext.VARIABLE, subjectVariable);
            KotlinType type4 = variableDescriptor2.getType();
            Type typeAsmType = asmType(variableDescriptor2.getType());
            int iEnter = this.myFrameMap.enter(variableDescriptor2, typeAsmType);
            visitProperty(subjectVariable, (StackValue) null);
            this.tempVariables.put(subjectExpression, StackValue.local(iEnter, typeAsmType, type4));
            variableDescriptor = variableDescriptor2;
            kotlinType2 = type4;
            type2 = typeAsmType;
            i = iEnter;
        } else if (subjectExpression != null) {
            KotlinType kotlinType4 = kotlinType(subjectExpression);
            Type typeExpressionType = expressionType(subjectExpression);
            int iEnterTemp = this.myFrameMap.enterTemp(typeExpressionType);
            gen(subjectExpression, typeExpressionType, kotlinType4);
            this.tempVariables.put(subjectExpression, StackValue.local(iEnterTemp, typeExpressionType, kotlinType4));
            instructionAdapter.store(iEnterTemp, typeExpressionType);
            i = iEnterTemp;
            kotlinType2 = kotlinType4;
            type2 = typeExpressionType;
            variableDescriptor = null;
        } else {
            i = -1;
            type2 = Type.VOID_TYPE;
            variableDescriptor = null;
            kotlinType2 = null;
        }
        Label label4 = new Label();
        instructionAdapter.mark(label4);
        Label label5 = new Label();
        boolean zCheckWhenExpressionHasSingleElse = KtPsiUtil.checkWhenExpressionHasSingleElse(ktWhenExpression);
        Label label6 = null;
        for (KtWhenEntry ktWhenEntry : ktWhenExpression.getEntries()) {
            if (label6 != null) {
                instructionAdapter.mark(label6);
            }
            label6 = new Label();
            FrameMapBase<DeclarationDescriptor>.Mark mark = this.myFrameMap.mark();
            Label label7 = new Label();
            if (ktWhenEntry.isElse()) {
                label = label4;
                kotlinType3 = kotlinType2;
                type3 = type2;
                ktExpression = subjectExpression;
                label2 = label7;
                label3 = label5;
            } else {
                KtWhenCondition[] conditions = ktWhenEntry.getConditions();
                Label label8 = label6;
                int i2 = 0;
                while (i2 < conditions.length) {
                    KtWhenCondition ktWhenCondition = conditions[i2];
                    int i3 = i2;
                    Label label9 = label8;
                    KtExpression ktExpression2 = subjectExpression;
                    KtExpression ktExpression3 = subjectExpression;
                    KtWhenCondition[] ktWhenConditionArr = conditions;
                    Type type5 = type2;
                    Type type6 = type2;
                    Label label10 = label7;
                    KotlinType kotlinType5 = kotlinType2;
                    KotlinType kotlinType6 = kotlinType2;
                    Label label11 = label5;
                    Label label12 = label4;
                    BranchedValue.INSTANCE.condJump(generateWhenCondition(ktExpression2, type5, kotlinType5, i, ktWhenCondition), label9, true, instructionAdapter);
                    if (i3 < ktWhenConditionArr.length - 1) {
                        instructionAdapter.goTo(label10);
                        instructionAdapter.mark(label9);
                        label8 = new Label();
                    } else {
                        label8 = label9;
                    }
                    i2 = i3 + 1;
                    label5 = label11;
                    label7 = label10;
                    conditions = ktWhenConditionArr;
                    subjectExpression = ktExpression3;
                    type2 = type6;
                    kotlinType2 = kotlinType6;
                    label4 = label12;
                }
                label = label4;
                kotlinType3 = kotlinType2;
                type3 = type2;
                ktExpression = subjectExpression;
                label2 = label7;
                label3 = label5;
                label6 = label8;
            }
            instructionAdapter.visitLabel(label2);
            gen(ktWhenEntry.getExpression(), type, kotlinType);
            mark.dropTo();
            if (!ktWhenEntry.isElse()) {
                instructionAdapter.goTo(label3);
            }
            label5 = label3;
            subjectExpression = ktExpression;
            type2 = type3;
            kotlinType2 = kotlinType3;
            label4 = label;
        }
        Label label13 = label5;
        Label label14 = label4;
        Type type7 = type2;
        KtExpression ktExpression4 = subjectExpression;
        if (!zCheckWhenExpressionHasSingleElse && label6 != null) {
            instructionAdapter.mark(label6);
            putUnitInstanceOntoStackForNonExhaustiveWhen(ktWhenExpression, z);
        }
        markLineNumber(ktWhenExpression, z);
        instructionAdapter.mark(label13);
        if (variableDescriptor != null) {
            this.myFrameMap.leave(variableDescriptor);
            instructionAdapter.visitLocalVariable(variableDescriptor.getShortName().asString(), type7.getDescriptor(), null, label14, label13, i);
        } else {
            this.myFrameMap.leaveTemp(type7);
        }
        this.tempVariables.remove(ktExpression4);
        return null;
    }

    public void putUnitInstanceOntoStackForNonExhaustiveWhen(KtWhenExpression ktWhenExpression, boolean z) {
        if (ktWhenExpression == null) {
            $$$reportNull$$$0(334);
        }
        if (CodegenUtil.isExhaustive(this.bindingContext, ktWhenExpression, z)) {
            AsmUtil.genThrow(this.v, "kotlin/NoWhenBranchMatchedException", null);
        } else {
            if (z) {
                return;
            }
            StackValue.putUnitInstance(this.v);
        }
    }

    private StackValue generateWhenCondition(KtExpression ktExpression, Type type, KotlinType kotlinType, int i, KtWhenCondition ktWhenCondition) {
        if (ktWhenCondition instanceof KtWhenConditionInRange) {
            KtWhenConditionInRange ktWhenConditionInRange = (KtWhenConditionInRange) ktWhenCondition;
            return generateIn(StackValue.local(i, type, kotlinType), ktWhenConditionInRange.getRangeExpression(), ktWhenConditionInRange.getOperationReference());
        }
        StackValue.Local local = i == -1 ? null : StackValue.local(i, type, kotlinType);
        if (ktWhenCondition instanceof KtWhenConditionIsPattern) {
            KtWhenConditionIsPattern ktWhenConditionIsPattern = (KtWhenConditionIsPattern) ktWhenCondition;
            return generateIsCheck(local, ktWhenConditionIsPattern.getTypeReference(), ktWhenConditionIsPattern.isNegated());
        }
        if (ktWhenCondition instanceof KtWhenConditionWithExpression) {
            return generateExpressionMatch(local, ktExpression, kotlinType, ((KtWhenConditionWithExpression) ktWhenCondition).getExpression());
        }
        throw new UnsupportedOperationException("unsupported kind of when condition");
    }

    public Call makeFakeCall(ReceiverValue receiverValue) {
        return CallMaker.makeCall(new KtPsiFactory(this.state.getProject(), false).createSimpleName("fake"), receiverValue);
    }

    public String toString() {
        return this.context.getContextDescriptor().toString();
    }

    @Override // org.jetbrains.kotlin.codegen.BaseExpressionCodegen
    public FrameMap getFrameMap() {
        FrameMap frameMap = this.myFrameMap;
        if (frameMap == null) {
            $$$reportNull$$$0(335);
        }
        return frameMap;
    }

    public MethodContext getContext() {
        MethodContext methodContext = this.context;
        if (methodContext == null) {
            $$$reportNull$$$0(336);
        }
        return methodContext;
    }

    @Override // org.jetbrains.kotlin.codegen.BaseExpressionCodegen
    public NameGenerator getInlineNameGenerator() {
        NameGenerator inlineNameGenerator = getParentCodegen().getInlineNameGenerator();
        Name name2 = this.context.getContextDescriptor().getShortName();
        NameGenerator nameGeneratorSubGenerator = inlineNameGenerator.subGenerator((name2.isSpecial() ? InlineCodegenUtilsKt.SPECIAL_TRANSFORMATION_NAME : name2.asString()) + InlineCodegenUtilsKt.INLINE_CALL_TRANSFORMATION_SUFFIX);
        if (nameGeneratorSubGenerator == null) {
            $$$reportNull$$$0(337);
        }
        return nameGeneratorSubGenerator;
    }

    public Type getReturnType() {
        return this.returnType;
    }

    public Stack<BlockStackElement> getBlockStackElements() {
        return new Stack<>(this.blockStackElements);
    }

    public void addBlockStackElementsForNonLocalReturns(Stack<BlockStackElement> stack, int i) {
        if (stack == null) {
            $$$reportNull$$$0(338);
        }
        this.blockStackElements.addAll(stack);
        this.finallyDepth = i;
    }

    private static class NonLocalReturnInfo {
        private final CallableDescriptor descriptor;
        private final String labelName;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i == 1) {
                objArr[0] = "name";
            } else if (i != 2) {
                objArr[0] = "descriptor";
            } else {
                objArr[0] = "typeMapper";
            }
            objArr[1] = "org/jetbrains/kotlin/codegen/ExpressionCodegen$NonLocalReturnInfo";
            if (i != 2) {
                objArr[2] = "<init>";
            } else {
                objArr[2] = "getJvmKotlinType";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        private NonLocalReturnInfo(CallableDescriptor callableDescriptor, String str) {
            if (callableDescriptor == null) {
                $$$reportNull$$$0(0);
            }
            if (str == null) {
                $$$reportNull$$$0(1);
            }
            this.descriptor = callableDescriptor;
            this.labelName = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public JvmKotlinType getJvmKotlinType(KotlinTypeMapper kotlinTypeMapper) {
            if (kotlinTypeMapper == null) {
                $$$reportNull$$$0(2);
            }
            return new JvmKotlinType(kotlinTypeMapper.mapReturnType(this.descriptor), this.descriptor.getReturnType());
        }
    }

    private StackValue.Delegate delegatedVariableValue(StackValue stackValue, StackValue stackValue2, VariableDescriptorWithAccessors variableDescriptorWithAccessors, KotlinTypeMapper kotlinTypeMapper) {
        if (stackValue == null) {
            $$$reportNull$$$0(339);
        }
        if (stackValue2 == null) {
            $$$reportNull$$$0(340);
        }
        if (variableDescriptorWithAccessors == null) {
            $$$reportNull$$$0(341);
        }
        if (kotlinTypeMapper == null) {
            $$$reportNull$$$0(342);
        }
        StackValue.Delegate delegateLocalDelegate = StackValue.localDelegate(kotlinTypeMapper.mapType(variableDescriptorWithAccessors.getType()), stackValue, stackValue2, variableDescriptorWithAccessors, this);
        if (delegateLocalDelegate == null) {
            $$$reportNull$$$0(343);
        }
        return delegateLocalDelegate;
    }

    @Override // org.jetbrains.kotlin.codegen.BaseExpressionCodegen
    public InstructionAdapter getVisitor() {
        InstructionAdapter instructionAdapter = this.v;
        if (instructionAdapter == null) {
            $$$reportNull$$$0(344);
        }
        return instructionAdapter;
    }

    @Override // org.jetbrains.kotlin.codegen.BaseExpressionCodegen
    public TypeSystemCommonBackendContext getTypeSystem() {
        TypeSystemCommonBackendContext typeSystemCommonBackendContext = this.typeSystem;
        if (typeSystemCommonBackendContext == null) {
            $$$reportNull$$$0(TarConstants.XSTAR_PREFIX_OFFSET);
        }
        return typeSystemCommonBackendContext;
    }

    @Override // org.jetbrains.kotlin.codegen.BaseExpressionCodegen
    public void consumeReifiedOperationMarker(TypeParameterMarker typeParameterMarker) {
        if (typeParameterMarker == null) {
            $$$reportNull$$$0(346);
        }
        TypeParameterDescriptor typeParameterDescriptor = (TypeParameterDescriptor) typeParameterMarker;
        if (typeParameterDescriptor.getContainingDeclaration() != this.context.getContextDescriptor()) {
            this.parentCodegen.getReifiedTypeParametersUsages().addUsedReifiedParameter(typeParameterDescriptor.getShortName().asString());
        }
    }
}
