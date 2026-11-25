package org.jetbrains.kotlin.ir.util;

import androidx.exifinterface.media.ExifInterface;
import com.huawei.hms.framework.common.ContainerUtils;
import com.huawei.hms.ml.language.common.utils.Constant;
import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;
import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import name.antonsmirnov.firmata.FormatHelper;
import net.bytebuddy.description.type.TypeDescription;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.transport.RefSpec;
import org.jetbrains.kotlin.cli.common.arguments.PreprocessCommandLineArgumentsKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.declarations.IrAnnotationContainer;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationBase;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrErrorDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrExternalPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrLocalDelegatedProperty;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.declarations.IrOverridableMember;
import org.jetbrains.kotlin.ir.declarations.IrPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrScript;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.declarations.IrTypeAlias;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrTypeParametersContainer;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrBlock;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrBranch;
import org.jetbrains.kotlin.ir.expressions.IrBreak;
import org.jetbrains.kotlin.ir.expressions.IrBreakContinue;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrCallableReference;
import org.jetbrains.kotlin.ir.expressions.IrCatch;
import org.jetbrains.kotlin.ir.expressions.IrClassReference;
import org.jetbrains.kotlin.ir.expressions.IrComposite;
import org.jetbrains.kotlin.ir.expressions.IrConst;
import org.jetbrains.kotlin.ir.expressions.IrConstKind;
import org.jetbrains.kotlin.ir.expressions.IrConstantArray;
import org.jetbrains.kotlin.ir.expressions.IrConstantObject;
import org.jetbrains.kotlin.ir.expressions.IrConstantPrimitive;
import org.jetbrains.kotlin.ir.expressions.IrConstantValue;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrContainerExpression;
import org.jetbrains.kotlin.ir.expressions.IrContinue;
import org.jetbrains.kotlin.ir.expressions.IrDeclarationReference;
import org.jetbrains.kotlin.ir.expressions.IrDelegatingConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrDoWhileLoop;
import org.jetbrains.kotlin.ir.expressions.IrDynamicExpression;
import org.jetbrains.kotlin.ir.expressions.IrDynamicMemberExpression;
import org.jetbrains.kotlin.ir.expressions.IrDynamicOperator;
import org.jetbrains.kotlin.ir.expressions.IrDynamicOperatorExpression;
import org.jetbrains.kotlin.ir.expressions.IrElseBranch;
import org.jetbrains.kotlin.ir.expressions.IrEnumConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrErrorCallExpression;
import org.jetbrains.kotlin.ir.expressions.IrErrorExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.expressions.IrFieldAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionReference;
import org.jetbrains.kotlin.ir.expressions.IrGetClass;
import org.jetbrains.kotlin.ir.expressions.IrGetEnumValue;
import org.jetbrains.kotlin.ir.expressions.IrGetField;
import org.jetbrains.kotlin.ir.expressions.IrGetObjectValue;
import org.jetbrains.kotlin.ir.expressions.IrGetSingletonValue;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.expressions.IrInlinedFunctionBlock;
import org.jetbrains.kotlin.ir.expressions.IrInstanceInitializerCall;
import org.jetbrains.kotlin.ir.expressions.IrLocalDelegatedPropertyReference;
import org.jetbrains.kotlin.ir.expressions.IrLoop;
import org.jetbrains.kotlin.ir.expressions.IrMemberAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrPropertyReference;
import org.jetbrains.kotlin.ir.expressions.IrRawFunctionReference;
import org.jetbrains.kotlin.ir.expressions.IrReturn;
import org.jetbrains.kotlin.ir.expressions.IrReturnableBlock;
import org.jetbrains.kotlin.ir.expressions.IrSetField;
import org.jetbrains.kotlin.ir.expressions.IrSetValue;
import org.jetbrains.kotlin.ir.expressions.IrSpreadElement;
import org.jetbrains.kotlin.ir.expressions.IrStatementContainer;
import org.jetbrains.kotlin.ir.expressions.IrStringConcatenation;
import org.jetbrains.kotlin.ir.expressions.IrSuspendableExpression;
import org.jetbrains.kotlin.ir.expressions.IrSuspensionPoint;
import org.jetbrains.kotlin.ir.expressions.IrSyntheticBody;
import org.jetbrains.kotlin.ir.expressions.IrThrow;
import org.jetbrains.kotlin.ir.expressions.IrTry;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperator;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperatorCall;
import org.jetbrains.kotlin.ir.expressions.IrValueAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrVararg;
import org.jetbrains.kotlin.ir.expressions.IrVarargElement;
import org.jetbrains.kotlin.ir.expressions.IrWhen;
import org.jetbrains.kotlin.ir.expressions.IrWhileLoop;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.types.IrDynamicType;
import org.jetbrains.kotlin.ir.types.IrErrorType;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrStarProjection;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeArgument;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypeProjection;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.types.SimpleTypeNullability;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitor;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.utils.Printer;

/* JADX INFO: compiled from: dumpKotlinLike.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000Ð\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0002\u0018\u0000 þ\u00012\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u0001:\u0002þ\u0001B\u0015\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0010\u0010\u001e\u001a\u00020\u000e2\u0006\u0010\u001f\u001a\u00020\u000eH\u0002J\u0010\u0010 \u001a\u00020\u000e2\u0006\u0010\u001f\u001a\u00020\u000eH\u0002J$\u0010!\u001a\b\u0012\u0004\u0012\u00020\"0\u00192\f\u0010#\u001a\b\u0012\u0004\u0012\u00020\"0\u00192\u0006\u0010$\u001a\u00020%H\u0002JF\u0010\u0004\u001a\u00020\u0002\"\b\b\u0000\u0010&*\u00020'2\b\u0010(\u001a\u0004\u0018\u0001H&2\n\b\u0002\u0010)\u001a\u0004\u0018\u0001H&2\u0017\u0010*\u001a\u0013\u0012\u0004\u0012\u0002H&\u0012\u0004\u0012\u00020\u000e0+¢\u0006\u0002\b,H\u0002¢\u0006\u0002\u0010-J\u0018\u0010\u0004\u001a\u00020\u00022\u0006\u0010.\u001a\u00020/2\u0006\u00100\u001a\u00020\u000eH\u0002J7\u00101\u001a\u00020\u0002\"\b\b\u0000\u00102*\u0002032\u0006\u00104\u001a\u0002H22\b\u00105\u001a\u0004\u0018\u00010\u00032\f\u00106\u001a\b\u0012\u0004\u0012\u00020\u000207H\u0002¢\u0006\u0002\u00108J\u000e\u00109\u001a\u00020\u00022\u0006\u0010:\u001a\u00020;J\u0018\u0010<\u001a\u00020\u00022\u0006\u0010=\u001a\u00020\u00032\u0006\u0010>\u001a\u00020?H\u0002J0\u0010@\u001a\u00020\u00022\u0006\u0010A\u001a\u00020/2\u0006\u0010B\u001a\u00020/2\u0006\u0010C\u001a\u00020/2\u0006\u0010D\u001a\u00020/2\u0006\u0010E\u001a\u00020/H\u0002J\u000e\u0010F\u001a\u00020\u00022\u0006\u0010G\u001a\u00020HJ\u000e\u0010I\u001a\u00020\u00022\u0006\u0010J\u001a\u00020KJ \u0010L\u001a\u00020\u00022\u0006\u0010M\u001a\u00020/2\u0006\u0010N\u001a\u00020O2\u0006\u0010G\u001a\u00020HH\u0002J\u0010\u0010P\u001a\u00020\u00022\u0006\u0010Q\u001a\u00020RH\u0002J\u001a\u0010S\u001a\u00020\u00022\u0006\u0010=\u001a\u00020T2\b\u00105\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010U\u001a\u00020\u00022\u0006\u0010V\u001a\u00020W2\b\u00105\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010X\u001a\u00020\u00022\u0006\u00104\u001a\u00020Y2\b\u00105\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010Z\u001a\u00020\u00022\u0006\u0010[\u001a\u00020\\2\b\u00105\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010]\u001a\u00020\u00022\u0006\u0010^\u001a\u00020_2\b\u00105\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010`\u001a\u00020\u00022\u0006\u0010V\u001a\u00020a2\b\u00105\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010b\u001a\u00020\u00022\u0006\u0010c\u001a\u00020d2\b\u00105\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010e\u001a\u00020\u00022\u0006\u0010=\u001a\u00020\u00152\b\u00105\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010f\u001a\u00020\u00022\u0006\u0010V\u001a\u00020g2\b\u00105\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010h\u001a\u00020\u00022\u0006\u0010V\u001a\u00020i2\b\u00105\u001a\u0004\u0018\u00010\u0003H\u0016J\u001e\u0010j\u001a\u00020\u00022\n\u0010V\u001a\u0006\u0012\u0002\b\u00030k2\b\u00105\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010l\u001a\u00020\u00022\u0006\u0010=\u001a\u00020m2\b\u00105\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010n\u001a\u00020\u00022\u0006\u0010V\u001a\u00020\"2\b\u00105\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010o\u001a\u00020\u00022\u0006\u0010V\u001a\u00020p2\b\u00105\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010q\u001a\u00020\u00022\u0006\u0010r\u001a\u00020s2\b\u00105\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010t\u001a\u00020\u00022\u0006\u0010V\u001a\u00020u2\b\u00105\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010v\u001a\u00020\u00022\u0006\u0010V\u001a\u00020w2\b\u00105\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010x\u001a\u00020\u00022\u0006\u0010:\u001a\u00020;2\b\u00105\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010y\u001a\u00020\u00022\u0006\u0010[\u001a\u00020z2\b\u00105\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010{\u001a\u00020\u00022\u0006\u0010V\u001a\u00020|2\b\u00105\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010}\u001a\u00020\u00022\u0006\u0010=\u001a\u00020~2\b\u00105\u001a\u0004\u0018\u00010\u0003H\u0016J\u001b\u0010\u007f\u001a\u00020\u00022\u0007\u0010V\u001a\u00030\u0080\u00012\b\u00105\u001a\u0004\u0018\u00010\u0003H\u0016J\u001c\u0010\u0081\u0001\u001a\u00020\u00022\u0007\u0010=\u001a\u00030\u0082\u00012\b\u00105\u001a\u0004\u0018\u00010\u0003H\u0016J\u001c\u0010\u0083\u0001\u001a\u00020\u00022\u0007\u0010V\u001a\u00030\u0084\u00012\b\u00105\u001a\u0004\u0018\u00010\u0003H\u0016J\u001c\u0010\u0085\u0001\u001a\u00020\u00022\u0007\u00104\u001a\u00030\u0086\u00012\b\u00105\u001a\u0004\u0018\u00010\u0003H\u0016J\u001c\u0010\u0087\u0001\u001a\u00020\u00022\u0007\u0010=\u001a\u00030\u0088\u00012\b\u00105\u001a\u0004\u0018\u00010\u0003H\u0016J\u001c\u0010\u0089\u0001\u001a\u00020\u00022\u0007\u0010=\u001a\u00030\u008a\u00012\b\u00105\u001a\u0004\u0018\u00010\u0003H\u0016J\u001c\u0010\u008b\u0001\u001a\u00020\u00022\u0007\u0010V\u001a\u00030\u008c\u00012\b\u00105\u001a\u0004\u0018\u00010\u0003H\u0016J\u001c\u0010\u008d\u0001\u001a\u00020\u00022\u0007\u0010V\u001a\u00030\u008e\u00012\b\u00105\u001a\u0004\u0018\u00010\u0003H\u0016J\u001c\u0010\u008f\u0001\u001a\u00020\u00022\u0007\u0010V\u001a\u00030\u0090\u00012\b\u00105\u001a\u0004\u0018\u00010\u0003H\u0016J\u001c\u0010\u0091\u0001\u001a\u00020\u00022\u0007\u0010V\u001a\u00030\u0092\u00012\b\u00105\u001a\u0004\u0018\u00010\u0003H\u0016J\u001c\u0010\u0093\u0001\u001a\u00020\u00022\u0007\u0010V\u001a\u00030\u0094\u00012\b\u00105\u001a\u0004\u0018\u00010\u0003H\u0016J\u001c\u0010\u0095\u0001\u001a\u00020\u00022\u0007\u0010V\u001a\u00030\u0096\u00012\b\u00105\u001a\u0004\u0018\u00010\u0003H\u0016J\u001c\u0010\u0097\u0001\u001a\u00020\u00022\u0007\u0010V\u001a\u00030\u0098\u00012\b\u00105\u001a\u0004\u0018\u00010\u0003H\u0016J\u001c\u0010\u0099\u0001\u001a\u00020\u00022\u0007\u0010V\u001a\u00030\u009a\u00012\b\u00105\u001a\u0004\u0018\u00010\u0003H\u0016J\u001c\u0010\u009b\u0001\u001a\u00020\u00022\u0007\u0010=\u001a\u00030\u009c\u00012\b\u00105\u001a\u0004\u0018\u00010\u0003H\u0016J\u001c\u0010\u009d\u0001\u001a\u00020\u00022\u0007\u0010V\u001a\u00030\u009e\u00012\b\u00105\u001a\u0004\u0018\u00010\u0003H\u0016J\u001c\u0010\u009f\u0001\u001a\u00020\u00022\u0007\u0010=\u001a\u00030 \u00012\b\u00105\u001a\u0004\u0018\u00010\u0003H\u0016J\u001c\u0010¡\u0001\u001a\u00020\u00022\u0007\u0010=\u001a\u00030¢\u00012\b\u00105\u001a\u0004\u0018\u00010\u0003H\u0016J\u001c\u0010£\u0001\u001a\u00020\u00022\u0007\u0010V\u001a\u00030¤\u00012\b\u00105\u001a\u0004\u0018\u00010\u0003H\u0016J\u001c\u0010¥\u0001\u001a\u00020\u00022\u0007\u0010V\u001a\u00030¦\u00012\b\u00105\u001a\u0004\u0018\u00010\u0003H\u0016J\u001c\u0010§\u0001\u001a\u00020\u00022\u0007\u0010V\u001a\u00030¨\u00012\b\u00105\u001a\u0004\u0018\u00010\u0003H\u0016J\u001c\u0010©\u0001\u001a\u00020\u00022\u0007\u0010V\u001a\u00030ª\u00012\b\u00105\u001a\u0004\u0018\u00010\u0003H\u0016J\u001c\u0010«\u0001\u001a\u00020\u00022\u0007\u0010V\u001a\u00030¬\u00012\b\u00105\u001a\u0004\u0018\u00010\u0003H\u0016J\u001c\u0010\u00ad\u0001\u001a\u00020\u00022\u0007\u0010=\u001a\u00030®\u00012\b\u00105\u001a\u0004\u0018\u00010\u0003H\u0016J\u001d\u0010¯\u0001\u001a\u00020\u00022\b\u0010°\u0001\u001a\u00030±\u00012\b\u00105\u001a\u0004\u0018\u00010\u0003H\u0016J\u001c\u0010²\u0001\u001a\u00020\u00022\u0007\u0010V\u001a\u00030³\u00012\b\u00105\u001a\u0004\u0018\u00010\u0003H\u0016J\u001c\u0010´\u0001\u001a\u00020\u00022\u0007\u00104\u001a\u00030µ\u00012\b\u00105\u001a\u0004\u0018\u00010\u0003H\u0016J\u001c\u0010¶\u0001\u001a\u00020\u00022\u0007\u0010V\u001a\u00030·\u00012\b\u00105\u001a\u0004\u0018\u00010\u0003H\u0016J\u001d\u0010¸\u0001\u001a\u00020\u00022\b\u0010¹\u0001\u001a\u00030º\u00012\b\u00105\u001a\u0004\u0018\u00010\u0003H\u0016J\u001c\u0010»\u0001\u001a\u00020\u00022\u0007\u0010=\u001a\u00030¼\u00012\b\u00105\u001a\u0004\u0018\u00010\u0003H\u0016J\u001c\u0010½\u0001\u001a\u00020\u00022\u0007\u0010V\u001a\u00030¾\u00012\b\u00105\u001a\u0004\u0018\u00010\u0003H\u0016J\u001c\u0010¿\u0001\u001a\u00020\u00022\u0007\u0010=\u001a\u00030À\u00012\b\u00105\u001a\u0004\u0018\u00010\u0003H\u0016J\u001b\u0010Á\u0001\u001a\u00020\u00022\u0006\u0010=\u001a\u00020\u001a2\b\u00105\u001a\u0004\u0018\u00010\u0003H\u0016J\u001c\u0010Â\u0001\u001a\u00020\u00022\u0007\u0010V\u001a\u00030Ã\u00012\b\u00105\u001a\u0004\u0018\u00010\u0003H\u0016J\u001c\u0010Ä\u0001\u001a\u00020\u00022\u0007\u0010=\u001a\u00030Å\u00012\b\u00105\u001a\u0004\u0018\u00010\u0003H\u0016J\u001c\u0010Æ\u0001\u001a\u00020\u00022\u0007\u0010V\u001a\u00030Ç\u00012\b\u00105\u001a\u0004\u0018\u00010\u0003H\u0016J\u001c\u0010È\u0001\u001a\u00020\u00022\u0007\u0010r\u001a\u00030É\u00012\b\u00105\u001a\u0004\u0018\u00010\u0003H\u0016J+\u0010Ê\u0001\u001a\u00020\u00022\u0006\u0010:\u001a\u00020;2\b\u0010$\u001a\u0004\u0018\u00010\u00032\r\u0010Ë\u0001\u001a\b\u0012\u0004\u0012\u00020\u000207H\u0082\bJ\u001f\u0010Ì\u0001\u001a\b\u0012\u0004\u0012\u00020\u00030\u0019*\b\u0012\u0004\u0012\u00020\u00030\u0019H\u0003¢\u0006\u0003\bÍ\u0001J\u001f\u0010Ì\u0001\u001a\b\u0012\u0004\u0012\u00020H0\u0019*\b\u0012\u0004\u0012\u00020H0\u0019H\u0003¢\u0006\u0003\bÎ\u0001J\u000e\u0010Ï\u0001\u001a\u00020\u0002*\u00030À\u0001H\u0002J\u0017\u0010Ð\u0001\u001a\u00020\u0002*\u00020\u001a2\b\u00105\u001a\u0004\u0018\u00010\u0003H\u0002J\u001f\u0010Ñ\u0001\u001a\u00020\u0002*\u00030®\u00012\u0006\u00100\u001a\u00020\u000e2\u0007\u0010Ò\u0001\u001a\u00020\u0003H\u0002J\u0018\u0010Ó\u0001\u001a\u00020\u0002*\u00020\"2\t\b\u0002\u0010Ô\u0001\u001a\u00020\u000eH\u0002J\r\u0010Õ\u0001\u001a\u00020\u0002*\u00020%H\u0002J+\u0010Ö\u0001\u001a\u00020\u0002*\u0007\u0012\u0002\b\u00030×\u00012\r\u0010Ø\u0001\u001a\b\u0012\u0004\u0012\u00020\u001a0\u00192\b\u00105\u001a\u0004\u0018\u00010\u0003H\u0002J\u0018\u0010Ù\u0001\u001a\u00020\u0002*\u00030Ú\u00012\b\u00105\u001a\u0004\u0018\u00010\u0003H\u0002J\r\u0010Û\u0001\u001a\u00020\u0002*\u00020\u001aH\u0002J\u0018\u0010Ü\u0001\u001a\u00020\u0002*\u00030Ý\u00012\b\u00105\u001a\u0004\u0018\u00010\u0003H\u0002J\u000e\u0010Þ\u0001\u001a\u00020\u0002*\u00030ß\u0001H\u0002Ji\u0010à\u0001\u001a\u00020\u0002*\u0007\u0012\u0002\b\u00030á\u00012\u0006\u0010N\u001a\u00020\u000e2\r\u0010Ø\u0001\u001a\b\u0012\u0004\u0012\u00020\u001a0\u00192\n\u0010â\u0001\u001a\u0005\u0018\u00010ã\u00012\u0007\u0010ä\u0001\u001a\u00020/2\b\u00105\u001a\u0004\u0018\u00010\u00032\t\b\u0002\u0010å\u0001\u001a\u00020\u000e2\t\b\u0002\u0010æ\u0001\u001a\u00020/2\t\b\u0002\u0010ç\u0001\u001a\u00020/H\u0002J;\u0010è\u0001\u001a\u00020\u0002*\u00030®\u00012\b\u00105\u001a\u0004\u0018\u00010\u00032\u0007\u0010é\u0001\u001a\u00020\u000e2\u0006\u0010N\u001a\u00020\u000e2\u0007\u0010ê\u0001\u001a\u00020/2\u0007\u0010ë\u0001\u001a\u00020/H\u0002J5\u0010ì\u0001\u001a\u00020\u0002*\u00030í\u00012\u0007\u0010î\u0001\u001a\u00020\u000e2\u0007\u0010ï\u0001\u001a\u00020\u000e2\b\u00105\u001a\u0004\u0018\u00010\u00032\t\b\u0002\u0010ð\u0001\u001a\u00020/H\u0002J\r\u0010ñ\u0001\u001a\u00020\u0002*\u00020KH\u0002J\u0019\u0010ò\u0001\u001a\u00020\u0002*\u00030ó\u00012\t\b\u0002\u0010ô\u0001\u001a\u00020\u000eH\u0002J\r\u0010õ\u0001\u001a\u00020\u0002*\u00020HH\u0002J\u000e\u0010ö\u0001\u001a\u00020\u0002*\u00030÷\u0001H\u0002J\u000e\u0010ø\u0001\u001a\u00020\u0002*\u00030ù\u0001H\u0002J\u000e\u0010ú\u0001\u001a\u00020\u0002*\u00030ó\u0001H\u0002J\u0017\u0010û\u0001\u001a\u00020/*\u00030À\u00012\u0007\u0010ü\u0001\u001a\u00020/H\u0002J\u0018\u0010ý\u0001\u001a\u00020\u0002*\u00020%2\t\b\u0002\u0010Ô\u0001\u001a\u00020\u000eH\u0002R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0018\u0010\r\u001a\u00020\u000e*\u00020\u000f8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0018\u0010\u0012\u001a\u00020\u000e*\u00020\u000f8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0011R\u001a\u0010\u0014\u001a\u0004\u0018\u00010\u0015*\u00020\u000f8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u001e\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u001a0\u0019*\u00020\u001b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001d¨\u0006ÿ\u0001"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/KotlinLikeDumper;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "p", "Lorg/jetbrains/kotlin/utils/Printer;", "options", "Lorg/jetbrains/kotlin/ir/util/KotlinLikeDumpOptions;", "(Lorg/jetbrains/kotlin/utils/Printer;Lorg/jetbrains/kotlin/ir/util/KotlinLikeDumpOptions;)V", "getOptions", "()Lorg/jetbrains/kotlin/ir/util/KotlinLikeDumpOptions;", "getP", "()Lorg/jetbrains/kotlin/utils/Printer;", "safeName", "", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "getSafeName", "(Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;)Ljava/lang/String;", "safeParentClassName", "getSafeParentClassName", "safeParentClassOrNull", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getSafeParentClassOrNull", "(Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;)Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "safeValueParameters", "", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "getSafeValueParameters", "(Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;)Ljava/util/List;", "commentBlock", "text", "customModifier", "filterAnnotations", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "annotations", "container", "Lorg/jetbrains/kotlin/ir/declarations/IrAnnotationContainer;", ExifInterface.GPS_DIRECTION_TRUE, "", "value", "defaultValue", "getString", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "(Ljava/lang/Object;Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)V", "condition", "", "s", "printBody", "Body", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "body", "data", "actuallyPrint", "Lkotlin/Function0;", "(Lorg/jetbrains/kotlin/ir/expressions/IrBody;Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;Lkotlin/jvm/functions/Function0;)V", "printElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/ir/IrElement;", "printModifiersWithNoIndent", "declaration", "modifiers", "Lorg/jetbrains/kotlin/ir/util/CustomKotlinLikeDumpStrategy$Modifiers;", "printParameterModifiersWithNoIndent", "isVararg", "isCrossinline", "isNoinline", "isHidden", "isAssignable", "printType", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "printTypeArgument", "typeArg", "Lorg/jetbrains/kotlin/ir/types/IrTypeArgument;", "printVariable", "isVar", "name", "Lorg/jetbrains/kotlin/name/Name;", "printVisibility", "visibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "visitAnonymousInitializer", "Lorg/jetbrains/kotlin/ir/declarations/IrAnonymousInitializer;", "visitBlock", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrBlock;", "visitBlockBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBlockBody;", "visitBranch", ConfigConstants.CONFIG_BRANCH_SECTION, "Lorg/jetbrains/kotlin/ir/expressions/IrBranch;", "visitBreakContinue", "jump", "Lorg/jetbrains/kotlin/ir/expressions/IrBreakContinue;", "visitCall", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "visitCatch", "aCatch", "Lorg/jetbrains/kotlin/ir/expressions/IrCatch;", "visitClass", "visitClassReference", "Lorg/jetbrains/kotlin/ir/expressions/IrClassReference;", "visitComposite", "Lorg/jetbrains/kotlin/ir/expressions/IrComposite;", "visitConst", "Lorg/jetbrains/kotlin/ir/expressions/IrConst;", "visitConstructor", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "visitConstructorCall", "visitDelegatingConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrDelegatingConstructorCall;", "visitDoWhileLoop", "loop", "Lorg/jetbrains/kotlin/ir/expressions/IrDoWhileLoop;", "visitDynamicMemberExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrDynamicMemberExpression;", "visitDynamicOperatorExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrDynamicOperatorExpression;", "visitElement", "visitElseBranch", "Lorg/jetbrains/kotlin/ir/expressions/IrElseBranch;", "visitEnumConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrEnumConstructorCall;", "visitEnumEntry", "Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "visitErrorCallExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrErrorCallExpression;", "visitErrorDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrErrorDeclaration;", "visitErrorExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrErrorExpression;", "visitExpressionBody", "Lorg/jetbrains/kotlin/ir/expressions/IrExpressionBody;", "visitField", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "visitFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "visitFunctionExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionExpression;", "visitFunctionReference", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;", "visitGetClass", "Lorg/jetbrains/kotlin/ir/expressions/IrGetClass;", "visitGetEnumValue", "Lorg/jetbrains/kotlin/ir/expressions/IrGetEnumValue;", "visitGetField", "Lorg/jetbrains/kotlin/ir/expressions/IrGetField;", "visitGetObjectValue", "Lorg/jetbrains/kotlin/ir/expressions/IrGetObjectValue;", "visitGetValue", "Lorg/jetbrains/kotlin/ir/expressions/IrGetValue;", "visitInstanceInitializerCall", "Lorg/jetbrains/kotlin/ir/expressions/IrInstanceInitializerCall;", "visitLocalDelegatedProperty", "Lorg/jetbrains/kotlin/ir/declarations/IrLocalDelegatedProperty;", "visitLocalDelegatedPropertyReference", "Lorg/jetbrains/kotlin/ir/expressions/IrLocalDelegatedPropertyReference;", "visitModuleFragment", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "visitProperty", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "visitPropertyReference", "Lorg/jetbrains/kotlin/ir/expressions/IrPropertyReference;", "visitRawFunctionReference", "Lorg/jetbrains/kotlin/ir/expressions/IrRawFunctionReference;", "visitReturn", "Lorg/jetbrains/kotlin/ir/expressions/IrReturn;", "visitSetField", "Lorg/jetbrains/kotlin/ir/expressions/IrSetField;", "visitSetValue", "Lorg/jetbrains/kotlin/ir/expressions/IrSetValue;", "visitSimpleFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "visitSpreadElement", "spread", "Lorg/jetbrains/kotlin/ir/expressions/IrSpreadElement;", "visitStringConcatenation", "Lorg/jetbrains/kotlin/ir/expressions/IrStringConcatenation;", "visitSyntheticBody", "Lorg/jetbrains/kotlin/ir/expressions/IrSyntheticBody;", "visitThrow", "Lorg/jetbrains/kotlin/ir/expressions/IrThrow;", "visitTry", "aTry", "Lorg/jetbrains/kotlin/ir/expressions/IrTry;", "visitTypeAlias", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeAlias;", "visitTypeOperator", "Lorg/jetbrains/kotlin/ir/expressions/IrTypeOperatorCall;", "visitTypeParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "visitValueParameter", "visitVararg", "Lorg/jetbrains/kotlin/ir/expressions/IrVararg;", "visitVariable", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "visitWhen", "Lorg/jetbrains/kotlin/ir/expressions/IrWhen;", "visitWhileLoop", "Lorg/jetbrains/kotlin/ir/expressions/IrWhileLoop;", "wrap", "block", "ordered", "orderedDeclarations", "orderedTypes", "printATypeParameterWithNoIndent", "printAValueParameterWithNoIndent", "printAccessor", "property", "printAnAnnotationWithNoIndent", "prefix", "printAnnotationsWithNoIndent", "printCallableReferenceWithNoIndent", "Lorg/jetbrains/kotlin/ir/expressions/IrCallableReference;", "valueParameters", "printConstructorCallWithNoIndent", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "printExtensionReceiverParameter", "printFieldAccess", "Lorg/jetbrains/kotlin/ir/expressions/IrFieldAccessExpression;", "printLabel", "Lorg/jetbrains/kotlin/ir/expressions/IrLoop;", "printMemberAccessExpressionWithNoIndent", "Lorg/jetbrains/kotlin/ir/expressions/IrMemberAccessExpression;", "superQualifierSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "omitAllBracketsIfNoArguments", "accessOperator", "omitAccessOperatorIfNoReceivers", "wrapArguments", "printSimpleFunction", "keyword", "printTypeParametersAndExtensionReceiver", "printSignatureAndBody", "printStatementContainer", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementContainer;", "before", "after", "withIndentation", "printTypeArgumentWithNoIndent", "printTypeParametersWithNoIndent", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParametersContainer;", "postfix", "printTypeWithNoIndent", "printValueParametersWithNoIndent", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "printVarianceWithNoIndent", "Lorg/jetbrains/kotlin/types/Variance;", "printWhereClauseIfNeededWithNoIndent", "printWhereClauseTypesWithNoIndent", "first", "printlnAnnotations", "Companion", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class KotlinLikeDumper implements IrElementVisitor<Unit, IrDeclaration> {
    private static final Companion Companion = new Companion(null);
    private final KotlinLikeDumpOptions options;
    private final Printer p;

    /* JADX INFO: compiled from: dumpKotlinLike.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;
        public static final /* synthetic */ int[] $EnumSwitchMapping$2;

        static {
            int[] iArr = new int[BodyPrintingStrategy.values().length];
            try {
                iArr[BodyPrintingStrategy.NO_BODIES.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[BodyPrintingStrategy.PRINT_ONLY_LOCAL_CLASSES_AND_FUNCTIONS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[BodyPrintingStrategy.PRINT_BODIES.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[IrTypeOperator.values().length];
            try {
                iArr2[IrTypeOperator.CAST.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr2[IrTypeOperator.IMPLICIT_CAST.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr2[IrTypeOperator.IMPLICIT_NOTNULL.ordinal()] = 3;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr2[IrTypeOperator.IMPLICIT_COERCION_TO_UNIT.ordinal()] = 4;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr2[IrTypeOperator.SAFE_CAST.ordinal()] = 5;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr2[IrTypeOperator.INSTANCEOF.ordinal()] = 6;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr2[IrTypeOperator.NOT_INSTANCEOF.ordinal()] = 7;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                iArr2[IrTypeOperator.IMPLICIT_INTEGER_COERCION.ordinal()] = 8;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                iArr2[IrTypeOperator.SAM_CONVERSION.ordinal()] = 9;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                iArr2[IrTypeOperator.IMPLICIT_DYNAMIC_CAST.ordinal()] = 10;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                iArr2[IrTypeOperator.REINTERPRET_CAST.ordinal()] = 11;
            } catch (NoSuchFieldError unused14) {
            }
            $EnumSwitchMapping$1 = iArr2;
            int[] iArr3 = new int[IrDynamicOperator.values().length];
            try {
                iArr3[IrDynamicOperator.ARRAY_ACCESS.ordinal()] = 1;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                iArr3[IrDynamicOperator.INVOKE.ordinal()] = 2;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                iArr3[IrDynamicOperator.UNARY_PLUS.ordinal()] = 3;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                iArr3[IrDynamicOperator.UNARY_MINUS.ordinal()] = 4;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                iArr3[IrDynamicOperator.EXCL.ordinal()] = 5;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                iArr3[IrDynamicOperator.PREFIX_INCREMENT.ordinal()] = 6;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                iArr3[IrDynamicOperator.PREFIX_DECREMENT.ordinal()] = 7;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                iArr3[IrDynamicOperator.POSTFIX_INCREMENT.ordinal()] = 8;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                iArr3[IrDynamicOperator.POSTFIX_DECREMENT.ordinal()] = 9;
            } catch (NoSuchFieldError unused23) {
            }
            $EnumSwitchMapping$2 = iArr3;
        }
    }

    public KotlinLikeDumper(Printer p, KotlinLikeDumpOptions options) {
        Intrinsics.checkNotNullParameter(p, "p");
        Intrinsics.checkNotNullParameter(options, "options");
        this.p = p;
        this.options = options;
    }

    public final KotlinLikeDumpOptions getOptions() {
        return this.options;
    }

    public final Printer getP() {
        return this.p;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitAnonymousInitializer */
    public /* bridge */ /* synthetic */ Unit visitAnonymousInitializer2(IrAnonymousInitializer irAnonymousInitializer, IrDeclaration irDeclaration) {
        visitAnonymousInitializer2(irAnonymousInitializer, irDeclaration);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBlock */
    public /* bridge */ /* synthetic */ Unit visitBlock2(IrBlock irBlock, IrDeclaration irDeclaration) {
        visitBlock2(irBlock, irDeclaration);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBlockBody */
    public /* bridge */ /* synthetic */ Unit visitBlockBody2(IrBlockBody irBlockBody, IrDeclaration irDeclaration) {
        visitBlockBody2(irBlockBody, irDeclaration);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBody */
    public /* bridge */ /* synthetic */ Unit visitBody2(IrBody irBody, IrDeclaration irDeclaration) {
        visitBody2(irBody, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitBody, reason: avoid collision after fix types in other method */
    public void visitBody2(IrBody irBody, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitBody(this, irBody, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBranch */
    public /* bridge */ /* synthetic */ Unit visitBranch2(IrBranch irBranch, IrDeclaration irDeclaration) {
        visitBranch2(irBranch, irDeclaration);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBreak */
    public /* bridge */ /* synthetic */ Unit visitBreak2(IrBreak irBreak, IrDeclaration irDeclaration) {
        visitBreak2(irBreak, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitBreak, reason: avoid collision after fix types in other method */
    public void visitBreak2(IrBreak irBreak, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitBreak(this, irBreak, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBreakContinue */
    public /* bridge */ /* synthetic */ Unit visitBreakContinue2(IrBreakContinue irBreakContinue, IrDeclaration irDeclaration) {
        visitBreakContinue2(irBreakContinue, irDeclaration);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitCall */
    public /* bridge */ /* synthetic */ Unit visitCall2(IrCall irCall, IrDeclaration irDeclaration) {
        visitCall2(irCall, irDeclaration);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public /* bridge */ /* synthetic */ Unit visitCallableReference(IrCallableReference irCallableReference, IrDeclaration irDeclaration) {
        visitCallableReference2((IrCallableReference<?>) irCallableReference, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitCallableReference, reason: avoid collision after fix types in other method */
    public void visitCallableReference2(IrCallableReference<?> irCallableReference, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitCallableReference(this, irCallableReference, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitCatch */
    public /* bridge */ /* synthetic */ Unit visitCatch2(IrCatch irCatch, IrDeclaration irDeclaration) {
        visitCatch2(irCatch, irDeclaration);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitClass */
    public /* bridge */ /* synthetic */ Unit visitClass2(IrClass irClass, IrDeclaration irDeclaration) {
        visitClass2(irClass, irDeclaration);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitClassReference */
    public /* bridge */ /* synthetic */ Unit visitClassReference2(IrClassReference irClassReference, IrDeclaration irDeclaration) {
        visitClassReference2(irClassReference, irDeclaration);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitComposite */
    public /* bridge */ /* synthetic */ Unit visitComposite2(IrComposite irComposite, IrDeclaration irDeclaration) {
        visitComposite2(irComposite, irDeclaration);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public /* bridge */ /* synthetic */ Unit visitConst(IrConst irConst, IrDeclaration irDeclaration) {
        visitConst2((IrConst<?>) irConst, irDeclaration);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantArray */
    public /* bridge */ /* synthetic */ Unit visitConstantArray2(IrConstantArray irConstantArray, IrDeclaration irDeclaration) {
        visitConstantArray2(irConstantArray, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitConstantArray, reason: avoid collision after fix types in other method */
    public void visitConstantArray2(IrConstantArray irConstantArray, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitConstantArray(this, irConstantArray, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantObject */
    public /* bridge */ /* synthetic */ Unit visitConstantObject2(IrConstantObject irConstantObject, IrDeclaration irDeclaration) {
        visitConstantObject2(irConstantObject, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitConstantObject, reason: avoid collision after fix types in other method */
    public void visitConstantObject2(IrConstantObject irConstantObject, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitConstantObject(this, irConstantObject, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantPrimitive */
    public /* bridge */ /* synthetic */ Unit visitConstantPrimitive2(IrConstantPrimitive irConstantPrimitive, IrDeclaration irDeclaration) {
        visitConstantPrimitive2(irConstantPrimitive, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitConstantPrimitive, reason: avoid collision after fix types in other method */
    public void visitConstantPrimitive2(IrConstantPrimitive irConstantPrimitive, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitConstantPrimitive(this, irConstantPrimitive, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantValue */
    public /* bridge */ /* synthetic */ Unit visitConstantValue2(IrConstantValue irConstantValue, IrDeclaration irDeclaration) {
        visitConstantValue2(irConstantValue, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitConstantValue, reason: avoid collision after fix types in other method */
    public void visitConstantValue2(IrConstantValue irConstantValue, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitConstantValue(this, irConstantValue, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstructor */
    public /* bridge */ /* synthetic */ Unit visitConstructor2(IrConstructor irConstructor, IrDeclaration irDeclaration) {
        visitConstructor2(irConstructor, irDeclaration);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstructorCall */
    public /* bridge */ /* synthetic */ Unit visitConstructorCall2(IrConstructorCall irConstructorCall, IrDeclaration irDeclaration) {
        visitConstructorCall2(irConstructorCall, irDeclaration);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitContainerExpression */
    public /* bridge */ /* synthetic */ Unit visitContainerExpression2(IrContainerExpression irContainerExpression, IrDeclaration irDeclaration) {
        visitContainerExpression2(irContainerExpression, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitContainerExpression, reason: avoid collision after fix types in other method */
    public void visitContainerExpression2(IrContainerExpression irContainerExpression, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitContainerExpression(this, irContainerExpression, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitContinue */
    public /* bridge */ /* synthetic */ Unit visitContinue2(IrContinue irContinue, IrDeclaration irDeclaration) {
        visitContinue2(irContinue, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitContinue, reason: avoid collision after fix types in other method */
    public void visitContinue2(IrContinue irContinue, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitContinue(this, irContinue, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDeclaration */
    public /* bridge */ /* synthetic */ Unit visitDeclaration2(IrDeclarationBase irDeclarationBase, IrDeclaration irDeclaration) {
        visitDeclaration2(irDeclarationBase, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitDeclaration, reason: avoid collision after fix types in other method */
    public void visitDeclaration2(IrDeclarationBase irDeclarationBase, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitDeclaration(this, irDeclarationBase, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDeclarationReference */
    public /* bridge */ /* synthetic */ Unit visitDeclarationReference2(IrDeclarationReference irDeclarationReference, IrDeclaration irDeclaration) {
        visitDeclarationReference2(irDeclarationReference, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitDeclarationReference, reason: avoid collision after fix types in other method */
    public void visitDeclarationReference2(IrDeclarationReference irDeclarationReference, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitDeclarationReference(this, irDeclarationReference, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDelegatingConstructorCall */
    public /* bridge */ /* synthetic */ Unit visitDelegatingConstructorCall2(IrDelegatingConstructorCall irDelegatingConstructorCall, IrDeclaration irDeclaration) {
        visitDelegatingConstructorCall2(irDelegatingConstructorCall, irDeclaration);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDoWhileLoop */
    public /* bridge */ /* synthetic */ Unit visitDoWhileLoop2(IrDoWhileLoop irDoWhileLoop, IrDeclaration irDeclaration) {
        visitDoWhileLoop2(irDoWhileLoop, irDeclaration);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDynamicExpression */
    public /* bridge */ /* synthetic */ Unit visitDynamicExpression2(IrDynamicExpression irDynamicExpression, IrDeclaration irDeclaration) {
        visitDynamicExpression2(irDynamicExpression, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitDynamicExpression, reason: avoid collision after fix types in other method */
    public void visitDynamicExpression2(IrDynamicExpression irDynamicExpression, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitDynamicExpression(this, irDynamicExpression, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDynamicMemberExpression */
    public /* bridge */ /* synthetic */ Unit visitDynamicMemberExpression2(IrDynamicMemberExpression irDynamicMemberExpression, IrDeclaration irDeclaration) {
        visitDynamicMemberExpression2(irDynamicMemberExpression, irDeclaration);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDynamicOperatorExpression */
    public /* bridge */ /* synthetic */ Unit visitDynamicOperatorExpression2(IrDynamicOperatorExpression irDynamicOperatorExpression, IrDeclaration irDeclaration) {
        visitDynamicOperatorExpression2(irDynamicOperatorExpression, irDeclaration);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitElement */
    public /* bridge */ /* synthetic */ Unit visitElement2(IrElement irElement, IrDeclaration irDeclaration) {
        visitElement2(irElement, irDeclaration);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitElseBranch */
    public /* bridge */ /* synthetic */ Unit visitElseBranch2(IrElseBranch irElseBranch, IrDeclaration irDeclaration) {
        visitElseBranch2(irElseBranch, irDeclaration);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitEnumConstructorCall */
    public /* bridge */ /* synthetic */ Unit visitEnumConstructorCall2(IrEnumConstructorCall irEnumConstructorCall, IrDeclaration irDeclaration) {
        visitEnumConstructorCall2(irEnumConstructorCall, irDeclaration);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitEnumEntry */
    public /* bridge */ /* synthetic */ Unit visitEnumEntry2(IrEnumEntry irEnumEntry, IrDeclaration irDeclaration) {
        visitEnumEntry2(irEnumEntry, irDeclaration);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitErrorCallExpression */
    public /* bridge */ /* synthetic */ Unit visitErrorCallExpression2(IrErrorCallExpression irErrorCallExpression, IrDeclaration irDeclaration) {
        visitErrorCallExpression2(irErrorCallExpression, irDeclaration);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitErrorDeclaration */
    public /* bridge */ /* synthetic */ Unit visitErrorDeclaration2(IrErrorDeclaration irErrorDeclaration, IrDeclaration irDeclaration) {
        visitErrorDeclaration2(irErrorDeclaration, irDeclaration);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitErrorExpression */
    public /* bridge */ /* synthetic */ Unit visitErrorExpression2(IrErrorExpression irErrorExpression, IrDeclaration irDeclaration) {
        visitErrorExpression2(irErrorExpression, irDeclaration);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExpression */
    public /* bridge */ /* synthetic */ Unit visitExpression2(IrExpression irExpression, IrDeclaration irDeclaration) {
        visitExpression2(irExpression, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitExpression, reason: avoid collision after fix types in other method */
    public void visitExpression2(IrExpression irExpression, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitExpression(this, irExpression, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExpressionBody */
    public /* bridge */ /* synthetic */ Unit visitExpressionBody2(IrExpressionBody irExpressionBody, IrDeclaration irDeclaration) {
        visitExpressionBody2(irExpressionBody, irDeclaration);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExternalPackageFragment */
    public /* bridge */ /* synthetic */ Unit visitExternalPackageFragment2(IrExternalPackageFragment irExternalPackageFragment, IrDeclaration irDeclaration) {
        visitExternalPackageFragment2(irExternalPackageFragment, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitExternalPackageFragment, reason: avoid collision after fix types in other method */
    public void visitExternalPackageFragment2(IrExternalPackageFragment irExternalPackageFragment, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitExternalPackageFragment(this, irExternalPackageFragment, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitField */
    public /* bridge */ /* synthetic */ Unit visitField2(IrField irField, IrDeclaration irDeclaration) throws Throwable {
        visitField2(irField, irDeclaration);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFieldAccess */
    public /* bridge */ /* synthetic */ Unit visitFieldAccess2(IrFieldAccessExpression irFieldAccessExpression, IrDeclaration irDeclaration) {
        visitFieldAccess2(irFieldAccessExpression, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitFieldAccess, reason: avoid collision after fix types in other method */
    public void visitFieldAccess2(IrFieldAccessExpression irFieldAccessExpression, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitFieldAccess(this, irFieldAccessExpression, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFile */
    public /* bridge */ /* synthetic */ Unit visitFile2(IrFile irFile, IrDeclaration irDeclaration) {
        visitFile2(irFile, irDeclaration);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunction */
    public /* bridge */ /* synthetic */ Unit visitFunction2(IrFunction irFunction, IrDeclaration irDeclaration) {
        visitFunction2(irFunction, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitFunction, reason: avoid collision after fix types in other method */
    public void visitFunction2(IrFunction irFunction, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitFunction(this, irFunction, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunctionAccess */
    public /* bridge */ /* synthetic */ Unit visitFunctionAccess2(IrFunctionAccessExpression irFunctionAccessExpression, IrDeclaration irDeclaration) {
        visitFunctionAccess2(irFunctionAccessExpression, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitFunctionAccess, reason: avoid collision after fix types in other method */
    public void visitFunctionAccess2(IrFunctionAccessExpression irFunctionAccessExpression, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitFunctionAccess(this, irFunctionAccessExpression, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunctionExpression */
    public /* bridge */ /* synthetic */ Unit visitFunctionExpression2(IrFunctionExpression irFunctionExpression, IrDeclaration irDeclaration) {
        visitFunctionExpression2(irFunctionExpression, irDeclaration);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunctionReference */
    public /* bridge */ /* synthetic */ Unit visitFunctionReference2(IrFunctionReference irFunctionReference, IrDeclaration irDeclaration) {
        visitFunctionReference2(irFunctionReference, irDeclaration);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetClass */
    public /* bridge */ /* synthetic */ Unit visitGetClass2(IrGetClass irGetClass, IrDeclaration irDeclaration) {
        visitGetClass2(irGetClass, irDeclaration);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetEnumValue */
    public /* bridge */ /* synthetic */ Unit visitGetEnumValue2(IrGetEnumValue irGetEnumValue, IrDeclaration irDeclaration) {
        visitGetEnumValue2(irGetEnumValue, irDeclaration);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetField */
    public /* bridge */ /* synthetic */ Unit visitGetField2(IrGetField irGetField, IrDeclaration irDeclaration) {
        visitGetField2(irGetField, irDeclaration);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetObjectValue */
    public /* bridge */ /* synthetic */ Unit visitGetObjectValue2(IrGetObjectValue irGetObjectValue, IrDeclaration irDeclaration) {
        visitGetObjectValue2(irGetObjectValue, irDeclaration);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetValue */
    public /* bridge */ /* synthetic */ Unit visitGetValue2(IrGetValue irGetValue, IrDeclaration irDeclaration) {
        visitGetValue2(irGetValue, irDeclaration);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitInstanceInitializerCall */
    public /* bridge */ /* synthetic */ Unit visitInstanceInitializerCall2(IrInstanceInitializerCall irInstanceInitializerCall, IrDeclaration irDeclaration) {
        visitInstanceInitializerCall2(irInstanceInitializerCall, irDeclaration);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitLocalDelegatedProperty */
    public /* bridge */ /* synthetic */ Unit visitLocalDelegatedProperty2(IrLocalDelegatedProperty irLocalDelegatedProperty, IrDeclaration irDeclaration) {
        visitLocalDelegatedProperty2(irLocalDelegatedProperty, irDeclaration);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitLocalDelegatedPropertyReference */
    public /* bridge */ /* synthetic */ Unit visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, IrDeclaration irDeclaration) {
        visitLocalDelegatedPropertyReference2(irLocalDelegatedPropertyReference, irDeclaration);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitLoop */
    public /* bridge */ /* synthetic */ Unit visitLoop2(IrLoop irLoop, IrDeclaration irDeclaration) {
        visitLoop2(irLoop, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitLoop, reason: avoid collision after fix types in other method */
    public void visitLoop2(IrLoop irLoop, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitLoop(this, irLoop, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public /* bridge */ /* synthetic */ Unit visitMemberAccess(IrMemberAccessExpression irMemberAccessExpression, IrDeclaration irDeclaration) {
        visitMemberAccess2((IrMemberAccessExpression<?>) irMemberAccessExpression, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitMemberAccess, reason: avoid collision after fix types in other method */
    public void visitMemberAccess2(IrMemberAccessExpression<?> irMemberAccessExpression, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitMemberAccess(this, irMemberAccessExpression, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitModuleFragment */
    public /* bridge */ /* synthetic */ Unit visitModuleFragment2(IrModuleFragment irModuleFragment, IrDeclaration irDeclaration) {
        visitModuleFragment2(irModuleFragment, irDeclaration);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitPackageFragment */
    public /* bridge */ /* synthetic */ Unit visitPackageFragment2(IrPackageFragment irPackageFragment, IrDeclaration irDeclaration) {
        visitPackageFragment2(irPackageFragment, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitPackageFragment, reason: avoid collision after fix types in other method */
    public void visitPackageFragment2(IrPackageFragment irPackageFragment, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitPackageFragment(this, irPackageFragment, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitProperty */
    public /* bridge */ /* synthetic */ Unit visitProperty2(IrProperty irProperty, IrDeclaration irDeclaration) throws Throwable {
        visitProperty2(irProperty, irDeclaration);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitPropertyReference */
    public /* bridge */ /* synthetic */ Unit visitPropertyReference2(IrPropertyReference irPropertyReference, IrDeclaration irDeclaration) {
        visitPropertyReference2(irPropertyReference, irDeclaration);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitRawFunctionReference */
    public /* bridge */ /* synthetic */ Unit visitRawFunctionReference2(IrRawFunctionReference irRawFunctionReference, IrDeclaration irDeclaration) {
        visitRawFunctionReference2(irRawFunctionReference, irDeclaration);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitReturn */
    public /* bridge */ /* synthetic */ Unit visitReturn2(IrReturn irReturn, IrDeclaration irDeclaration) {
        visitReturn2(irReturn, irDeclaration);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitScript */
    public /* bridge */ /* synthetic */ Unit visitScript2(IrScript irScript, IrDeclaration irDeclaration) {
        visitScript2(irScript, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitScript, reason: avoid collision after fix types in other method */
    public void visitScript2(IrScript irScript, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitScript(this, irScript, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSetField */
    public /* bridge */ /* synthetic */ Unit visitSetField2(IrSetField irSetField, IrDeclaration irDeclaration) {
        visitSetField2(irSetField, irDeclaration);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSetValue */
    public /* bridge */ /* synthetic */ Unit visitSetValue2(IrSetValue irSetValue, IrDeclaration irDeclaration) {
        visitSetValue2(irSetValue, irDeclaration);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSimpleFunction */
    public /* bridge */ /* synthetic */ Unit visitSimpleFunction2(IrSimpleFunction irSimpleFunction, IrDeclaration irDeclaration) {
        visitSimpleFunction2(irSimpleFunction, irDeclaration);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSingletonReference */
    public /* bridge */ /* synthetic */ Unit visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, IrDeclaration irDeclaration) {
        visitSingletonReference2(irGetSingletonValue, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitSingletonReference, reason: avoid collision after fix types in other method */
    public void visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitSingletonReference(this, irGetSingletonValue, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSpreadElement */
    public /* bridge */ /* synthetic */ Unit visitSpreadElement2(IrSpreadElement irSpreadElement, IrDeclaration irDeclaration) {
        visitSpreadElement2(irSpreadElement, irDeclaration);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitStringConcatenation */
    public /* bridge */ /* synthetic */ Unit visitStringConcatenation2(IrStringConcatenation irStringConcatenation, IrDeclaration irDeclaration) {
        visitStringConcatenation2(irStringConcatenation, irDeclaration);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSuspendableExpression */
    public /* bridge */ /* synthetic */ Unit visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, IrDeclaration irDeclaration) {
        visitSuspendableExpression2(irSuspendableExpression, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitSuspendableExpression, reason: avoid collision after fix types in other method */
    public void visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitSuspendableExpression(this, irSuspendableExpression, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSuspensionPoint */
    public /* bridge */ /* synthetic */ Unit visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, IrDeclaration irDeclaration) {
        visitSuspensionPoint2(irSuspensionPoint, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitSuspensionPoint, reason: avoid collision after fix types in other method */
    public void visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitSuspensionPoint(this, irSuspensionPoint, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSyntheticBody */
    public /* bridge */ /* synthetic */ Unit visitSyntheticBody2(IrSyntheticBody irSyntheticBody, IrDeclaration irDeclaration) {
        visitSyntheticBody2(irSyntheticBody, irDeclaration);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitThrow */
    public /* bridge */ /* synthetic */ Unit visitThrow2(IrThrow irThrow, IrDeclaration irDeclaration) {
        visitThrow2(irThrow, irDeclaration);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTry */
    public /* bridge */ /* synthetic */ Unit visitTry2(IrTry irTry, IrDeclaration irDeclaration) {
        visitTry2(irTry, irDeclaration);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeAlias */
    public /* bridge */ /* synthetic */ Unit visitTypeAlias2(IrTypeAlias irTypeAlias, IrDeclaration irDeclaration) {
        visitTypeAlias2(irTypeAlias, irDeclaration);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeOperator */
    public /* bridge */ /* synthetic */ Unit visitTypeOperator2(IrTypeOperatorCall irTypeOperatorCall, IrDeclaration irDeclaration) {
        visitTypeOperator2(irTypeOperatorCall, irDeclaration);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeParameter */
    public /* bridge */ /* synthetic */ Unit visitTypeParameter2(IrTypeParameter irTypeParameter, IrDeclaration irDeclaration) {
        visitTypeParameter2(irTypeParameter, irDeclaration);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitValueAccess */
    public /* bridge */ /* synthetic */ Unit visitValueAccess2(IrValueAccessExpression irValueAccessExpression, IrDeclaration irDeclaration) {
        visitValueAccess2(irValueAccessExpression, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitValueAccess, reason: avoid collision after fix types in other method */
    public void visitValueAccess2(IrValueAccessExpression irValueAccessExpression, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitValueAccess(this, irValueAccessExpression, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitValueParameter */
    public /* bridge */ /* synthetic */ Unit visitValueParameter2(IrValueParameter irValueParameter, IrDeclaration irDeclaration) {
        visitValueParameter2(irValueParameter, irDeclaration);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitVararg */
    public /* bridge */ /* synthetic */ Unit visitVararg2(IrVararg irVararg, IrDeclaration irDeclaration) {
        visitVararg2(irVararg, irDeclaration);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitVariable */
    public /* bridge */ /* synthetic */ Unit visitVariable2(IrVariable irVariable, IrDeclaration irDeclaration) {
        visitVariable2(irVariable, irDeclaration);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitWhen */
    public /* bridge */ /* synthetic */ Unit visitWhen2(IrWhen irWhen, IrDeclaration irDeclaration) {
        visitWhen2(irWhen, irDeclaration);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitWhileLoop */
    public /* bridge */ /* synthetic */ Unit visitWhileLoop2(IrWhileLoop irWhileLoop, IrDeclaration irDeclaration) {
        visitWhileLoop2(irWhileLoop, irDeclaration);
        return Unit.INSTANCE;
    }

    private final String getSafeName(IrSymbol irSymbol) {
        Name name2;
        String string;
        if (!irSymbol.isBound()) {
            return "/* ERROR: unbound symbol " + irSymbol.getSignature() + " */";
        }
        IrSymbolOwner owner = irSymbol.getOwner();
        IrDeclarationWithName irDeclarationWithName = owner instanceof IrDeclarationWithName ? (IrDeclarationWithName) owner : null;
        String str = (irDeclarationWithName == null || (name2 = irDeclarationWithName.getName()) == null || (string = name2.toString()) == null) ? "/* ERROR: unnamed symbol " + irSymbol.getSignature() + " */" : string;
        Intrinsics.checkNotNull(str);
        return str;
    }

    private final List<IrValueParameter> getSafeValueParameters(IrFunctionSymbol irFunctionSymbol) {
        if (!irFunctionSymbol.isBound()) {
            return CollectionsKt.emptyList();
        }
        return irFunctionSymbol.getOwner().getValueParameters();
    }

    private final String getSafeParentClassName(IrSymbol irSymbol) {
        IrClass parentClassOrNull;
        Name name2;
        String string;
        if (!irSymbol.isBound()) {
            return "/* ERROR: unbound symbol " + irSymbol.getSignature() + " */";
        }
        IrSymbolOwner owner = irSymbol.getOwner();
        IrDeclaration irDeclaration = owner instanceof IrDeclaration ? (IrDeclaration) owner : null;
        String str = (irDeclaration == null || (parentClassOrNull = IrUtilsKt.getParentClassOrNull(irDeclaration)) == null || (name2 = parentClassOrNull.getName()) == null || (string = name2.toString()) == null) ? "/* ERROR: unexpected parent for " + getSafeName(irSymbol) + " */" : string;
        Intrinsics.checkNotNull(str);
        return str;
    }

    private final IrClass getSafeParentClassOrNull(IrSymbol irSymbol) {
        if (!irSymbol.isBound()) {
            return null;
        }
        IrSymbolOwner owner = irSymbol.getOwner();
        IrDeclaration irDeclaration = owner instanceof IrDeclaration ? (IrDeclaration) owner : null;
        if (irDeclaration != null) {
            return IrUtilsKt.getParentClassOrNull(irDeclaration);
        }
        return null;
    }

    public final void printElement(IrElement element) {
        Intrinsics.checkNotNullParameter(element, "element");
        element.accept(this, null);
    }

    public final void printType(IrType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        printTypeWithNoIndent(type);
    }

    public final void printTypeArgument(IrTypeArgument typeArg) {
        Intrinsics.checkNotNullParameter(typeArg, "typeArg");
        printTypeArgumentWithNoIndent(typeArg);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final List<IrDeclaration> orderedDeclarations(List<? extends IrDeclaration> list) {
        return this.options.getStableOrder() ? DumpIrTreeKt.stableOrdered(list) : list;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final List<IrType> orderedTypes(List<? extends IrType> list) {
        if (!this.options.getStableOrder()) {
            return list;
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : list) {
            if (ordered$isNonInterfaceType((IrType) obj)) {
                arrayList.add(obj);
            } else {
                arrayList2.add(obj);
            }
        }
        Pair pair = new Pair(arrayList, arrayList2);
        return CollectionsKt.plus((Collection) CollectionsKt.sortedWith((List) pair.component1(), new Comparator() { // from class: org.jetbrains.kotlin.ir.util.KotlinLikeDumper$ordered$$inlined$sortedBy$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                return ComparisonsKt.compareValues(RenderIrElementKt.render$default((IrType) t, (DumpIrTreeOptions) null, 1, (Object) null), RenderIrElementKt.render$default((IrType) t2, (DumpIrTreeOptions) null, 1, (Object) null));
            }
        }), (Iterable) CollectionsKt.sortedWith((List) pair.component2(), new Comparator() { // from class: org.jetbrains.kotlin.ir.util.KotlinLikeDumper$ordered$$inlined$sortedBy$2
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                return ComparisonsKt.compareValues(RenderIrElementKt.render$default((IrType) t, (DumpIrTreeOptions) null, 1, (Object) null), RenderIrElementKt.render$default((IrType) t2, (DumpIrTreeOptions) null, 1, (Object) null));
            }
        }));
    }

    private static final boolean ordered$isNonInterfaceType(IrType irType) {
        IrClassifierSymbol classifierOrNull = IrTypesKt.getClassifierOrNull(irType);
        return (classifierOrNull != null && (classifierOrNull instanceof IrClassSymbol) && IrUtilsKt.isInterface(((IrClassSymbol) classifierOrNull).getOwner())) ? false : true;
    }

    /* JADX INFO: renamed from: visitElement, reason: avoid collision after fix types in other method */
    public void visitElement2(IrElement element, IrDeclaration data) {
        Intrinsics.checkNotNullParameter(element, "element");
        String str = "/* ERROR: unsupported element type: " + element.getClass().getSimpleName() + " */";
        if (element instanceof IrExpression) {
            this.p.printlnWithNoIndent("error(\"\") " + str);
        } else {
            this.p.println(str);
        }
    }

    /* JADX WARN: Code duplicated, block: B:12:0x0024  */
    private final void printModifiersWithNoIndent(IrDeclaration declaration, CustomKotlinLikeDumpStrategy.Modifiers modifiers) {
        boolean z;
        Modality modality;
        CustomKotlinLikeDumpStrategy.Modifiers modifiersTransformModifiersForDeclaration = this.options.getCustomDumpStrategy().transformModifiersForDeclaration(declaration, modifiers);
        if (declaration instanceof IrOverridableMember) {
            IrDeclarationParent parent = declaration.getParent();
            IrClass irClass = parent instanceof IrClass ? (IrClass) parent : null;
            if (irClass != null) {
                z = IrUtilsKt.isInterface(irClass);
            }
        }
        printVisibility(modifiersTransformModifiersForDeclaration.getVisibility());
        p(modifiersTransformModifiersForDeclaration.isExpect(), "expect");
        if (z || ((modifiersTransformModifiersForDeclaration.isOverride() || modifiersTransformModifiersForDeclaration.isFakeOverride()) && modifiersTransformModifiersForDeclaration.getModality() == Modality.OPEN)) {
            modality = Modality.OPEN;
        } else if (modifiersTransformModifiersForDeclaration.getClassKind() == ClassKind.INTERFACE) {
            modality = Modality.ABSTRACT;
        } else {
            modality = Modality.FINAL;
        }
        p(modifiersTransformModifiersForDeclaration.getModality(), modality, new Function1<Modality, String>() { // from class: org.jetbrains.kotlin.ir.util.KotlinLikeDumper$printModifiersWithNoIndent$1$1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(Modality p) {
                Intrinsics.checkNotNullParameter(p, "$this$p");
                String lowerCase = p.name().toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
                return lowerCase;
            }
        });
        p(modifiersTransformModifiersForDeclaration.isExternal(), "external");
        p(modifiersTransformModifiersForDeclaration.isFakeOverride(), customModifier("fake"));
        p(modifiersTransformModifiersForDeclaration.isOverride(), "override");
        p(modifiersTransformModifiersForDeclaration.isLateinit(), "lateinit");
        p(modifiersTransformModifiersForDeclaration.isTailrec(), "tailrec");
        printParameterModifiersWithNoIndent(modifiersTransformModifiersForDeclaration.isVararg(), modifiersTransformModifiersForDeclaration.isCrossinline(), modifiersTransformModifiersForDeclaration.isNoinline(), modifiersTransformModifiersForDeclaration.isHidden(), modifiersTransformModifiersForDeclaration.isAssignable());
        p(modifiersTransformModifiersForDeclaration.isSuspend(), "suspend");
        p(modifiersTransformModifiersForDeclaration.isInner(), "inner");
        p(modifiersTransformModifiersForDeclaration.isInline(), "inline");
        p(modifiersTransformModifiersForDeclaration.isValue(), "value");
        p(modifiersTransformModifiersForDeclaration.isData(), "data");
        p(modifiersTransformModifiersForDeclaration.isCompanion(), "companion");
        p(modifiersTransformModifiersForDeclaration.isFunInterface(), "fun");
        p$default(this, modifiersTransformModifiersForDeclaration.getClassKind(), null, new Function1<ClassKind, String>() { // from class: org.jetbrains.kotlin.ir.util.KotlinLikeDumper$printModifiersWithNoIndent$1$2
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(ClassKind p) {
                Intrinsics.checkNotNullParameter(p, "$this$p");
                StringBuilder sb = new StringBuilder();
                String lowerCase = p.name().toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
                return sb.append(StringsKt.replace$default(lowerCase, '_', StringUtil.SPACE, false, 4, (Object) null)).append(p == ClassKind.ENUM_ENTRY ? " class" : "").toString();
            }
        }, 2, null);
        p(modifiersTransformModifiersForDeclaration.isInfix(), "infix");
        p(modifiersTransformModifiersForDeclaration.isOperator(), "operator");
    }

    private final void printVisibility(DescriptorVisibility visibility) {
        p(visibility, DescriptorVisibilities.DEFAULT_VISIBILITY, new Function1<DescriptorVisibility, String>() { // from class: org.jetbrains.kotlin.ir.util.KotlinLikeDumper.printVisibility.1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(DescriptorVisibility p) {
                Intrinsics.checkNotNullParameter(p, "$this$p");
                return p.getName();
            }
        });
    }

    private final void printParameterModifiersWithNoIndent(boolean isVararg, boolean isCrossinline, boolean isNoinline, boolean isHidden, boolean isAssignable) {
        p(isVararg, "vararg");
        p(isCrossinline, "crossinline");
        p(isNoinline, "noinline");
        p(isHidden, customModifier("hidden"));
        p(isAssignable, customModifier(PsiKeyword.VAR));
    }

    static /* synthetic */ void printTypeParametersWithNoIndent$default(KotlinLikeDumper kotlinLikeDumper, IrTypeParametersContainer irTypeParametersContainer, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            str = "";
        }
        kotlinLikeDumper.printTypeParametersWithNoIndent(irTypeParametersContainer, str);
    }

    private final void printTypeParametersWithNoIndent(IrTypeParametersContainer irTypeParametersContainer, String str) {
        if (irTypeParametersContainer.getTypeParameters().isEmpty()) {
            return;
        }
        this.p.printWithNoIndent("<");
        int i = 0;
        for (Object obj : irTypeParametersContainer.getTypeParameters()) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            IrTypeParameter irTypeParameter = (IrTypeParameter) obj;
            p(i > 0, ",");
            printATypeParameterWithNoIndent(irTypeParameter);
            i = i2;
        }
        this.p.printWithNoIndent(">");
        this.p.printWithNoIndent(str);
    }

    private final void printATypeParameterWithNoIndent(IrTypeParameter irTypeParameter) {
        printVarianceWithNoIndent(irTypeParameter.getVariance());
        p(irTypeParameter.getIsReified(), "reified");
        printAnnotationsWithNoIndent(irTypeParameter);
        this.p.printWithNoIndent(irTypeParameter.getName().asString());
        if (irTypeParameter.getSuperTypes().size() == 1) {
            this.p.printWithNoIndent(" : ");
            printTypeWithNoIndent((IrType) CollectionsKt.single((List) irTypeParameter.getSuperTypes()));
        }
    }

    private final void printVarianceWithNoIndent(Variance variance) {
        p(variance, Variance.INVARIANT, new Function1<Variance, String>() { // from class: org.jetbrains.kotlin.ir.util.KotlinLikeDumper.printVarianceWithNoIndent.1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(Variance p) {
                Intrinsics.checkNotNullParameter(p, "$this$p");
                return p.getLabel();
            }
        });
    }

    private final List<IrConstructorCall> filterAnnotations(List<? extends IrConstructorCall> annotations, IrAnnotationContainer container) {
        ArrayList arrayList = new ArrayList();
        for (Object obj : annotations) {
            if (this.options.getCustomDumpStrategy().shouldPrintAnnotation((IrConstructorCall) obj, container)) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    private final void printAnnotationsWithNoIndent(IrAnnotationContainer irAnnotationContainer) {
        Iterator<T> it = filterAnnotations(irAnnotationContainer.getAnnotations(), irAnnotationContainer).iterator();
        while (it.hasNext()) {
            printAnAnnotationWithNoIndent$default(this, (IrConstructorCall) it.next(), null, 1, null);
            this.p.printWithNoIndent(" ");
        }
    }

    static /* synthetic */ void printlnAnnotations$default(KotlinLikeDumper kotlinLikeDumper, IrAnnotationContainer irAnnotationContainer, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            str = "";
        }
        kotlinLikeDumper.printlnAnnotations(irAnnotationContainer, str);
    }

    private final void printlnAnnotations(IrAnnotationContainer irAnnotationContainer, String str) {
        for (IrConstructorCall irConstructorCall : filterAnnotations(irAnnotationContainer.getAnnotations(), irAnnotationContainer)) {
            this.p.printIndent();
            printAnAnnotationWithNoIndent(irConstructorCall, str);
            this.p.printlnWithNoIndent(new Object[0]);
        }
    }

    static /* synthetic */ void printAnAnnotationWithNoIndent$default(KotlinLikeDumper kotlinLikeDumper, IrConstructorCall irConstructorCall, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            str = "";
        }
        kotlinLikeDumper.printAnAnnotationWithNoIndent(irConstructorCall, str);
    }

    private final void printAnAnnotationWithNoIndent(IrConstructorCall irConstructorCall, String str) {
        this.p.printWithNoIndent(PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT + (str.length() == 0 ? "" : str + ':'));
        visitConstructorCall2(irConstructorCall, (IrDeclaration) null);
    }

    private final void printWhereClauseIfNeededWithNoIndent(IrTypeParametersContainer irTypeParametersContainer) {
        List<IrTypeParameter> typeParameters = irTypeParametersContainer.getTypeParameters();
        if ((typeParameters instanceof Collection) && typeParameters.isEmpty()) {
            return;
        }
        Iterator<T> it = typeParameters.iterator();
        while (it.hasNext()) {
            if (((IrTypeParameter) it.next()).getSuperTypes().size() > 1) {
                this.p.printWithNoIndent(" where ");
                boolean zPrintWhereClauseTypesWithNoIndent = true;
                for (IrTypeParameter irTypeParameter : irTypeParametersContainer.getTypeParameters()) {
                    if (irTypeParameter.getSuperTypes().size() > 1) {
                        zPrintWhereClauseTypesWithNoIndent = printWhereClauseTypesWithNoIndent(irTypeParameter, zPrintWhereClauseTypesWithNoIndent);
                    }
                }
                return;
            }
        }
    }

    private final boolean printWhereClauseTypesWithNoIndent(IrTypeParameter irTypeParameter, boolean z) {
        for (IrType irType : orderedTypes(irTypeParameter.getSuperTypes())) {
            if (z) {
                z = false;
            } else {
                this.p.printWithNoIndent(", ");
            }
            this.p.printWithNoIndent(irTypeParameter.getName().asString());
            this.p.printWithNoIndent(" : ");
            printTypeWithNoIndent(irType);
        }
        return z;
    }

    private final void printTypeWithNoIndent(IrType irType) {
        printAnnotationsWithNoIndent(irType);
        if (irType instanceof IrSimpleType) {
            IrSimpleType irSimpleType = (IrSimpleType) irType;
            boolean z = (irSimpleType.getClassifier() instanceof IrTypeParameterSymbol) && irSimpleType.getNullability() == SimpleTypeNullability.DEFINITELY_NOT_NULL;
            if (z) {
                this.p.printWithNoIndent("(");
            }
            this.p.printWithNoIndent(getSafeName(irSimpleType.getClassifier()));
            if (!irSimpleType.getArguments().isEmpty()) {
                this.p.printWithNoIndent("<");
                int i = 0;
                for (Object obj : irSimpleType.getArguments()) {
                    int i2 = i + 1;
                    if (i < 0) {
                        CollectionsKt.throwIndexOverflow();
                    }
                    IrTypeArgument irTypeArgument = (IrTypeArgument) obj;
                    p(i > 0, ",");
                    printTypeArgumentWithNoIndent(irTypeArgument);
                    i = i2;
                }
                this.p.printWithNoIndent(">");
            }
            if (z) {
                this.p.printWithNoIndent(" & Any)");
            }
            if (IrTypePredicatesKt.isMarkedNullable(irSimpleType)) {
                this.p.printWithNoIndent(TypeDescription.Generic.OfWildcardType.SYMBOL);
                return;
            }
            return;
        }
        if (irType instanceof IrDynamicType) {
            this.p.printWithNoIndent("dynamic");
        } else if (irType instanceof IrErrorType) {
            this.p.printWithNoIndent("ErrorType");
        } else {
            this.p.printWithNoIndent("??? /* ERROR: unknown type: " + irType.getClass().getSimpleName() + " */");
        }
    }

    private final void printTypeArgumentWithNoIndent(IrTypeArgument irTypeArgument) {
        if (irTypeArgument instanceof IrStarProjection) {
            this.p.printWithNoIndent("*");
        } else if (irTypeArgument instanceof IrTypeProjection) {
            IrTypeProjection irTypeProjection = (IrTypeProjection) irTypeArgument;
            printVarianceWithNoIndent(irTypeProjection.getVariance());
            printTypeWithNoIndent(irTypeProjection.getType());
        }
    }

    /* JADX INFO: renamed from: visitSimpleFunction, reason: avoid collision after fix types in other method */
    public void visitSimpleFunction2(IrSimpleFunction declaration, IrDeclaration data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        String strAsString = declaration.getName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        printSimpleFunction(declaration, data, "fun ", strAsString, true, true);
        this.p.printlnWithNoIndent(new Object[0]);
    }

    private final void printSimpleFunction(IrSimpleFunction irSimpleFunction, IrDeclaration irDeclaration, String str, String str2, boolean z, boolean z2) {
        IrValueParameter extensionReceiverParameter;
        if (this.options.getPrintFakeOverridesStrategy() == FakeOverridesStrategy.NONE && irSimpleFunction.getIsFakeOverride()) {
            return;
        }
        if (this.options.getPrintFakeOverridesStrategy() == FakeOverridesStrategy.ALL_EXCEPT_ANY && IrUtilsKt.isFakeOverriddenFromAny(irSimpleFunction)) {
            return;
        }
        IrSimpleFunction irSimpleFunction2 = irSimpleFunction;
        if (this.options.getCustomDumpStrategy().willPrintElement(irSimpleFunction2, irDeclaration, this.p)) {
            try {
                printlnAnnotations$default(this, irSimpleFunction, null, 1, null);
                this.p.print("");
                printModifiersWithNoIndent(irSimpleFunction, new CustomKotlinLikeDumpStrategy.Modifiers(irSimpleFunction.getVisibility(), irSimpleFunction.getIsExpect(), irSimpleFunction.getModality(), irSimpleFunction.getIsExternal(), !irSimpleFunction.getOverriddenSymbols().isEmpty(), irSimpleFunction.getIsFakeOverride(), irSimpleFunction.getIsTailrec(), false, irSimpleFunction.getIsSuspend(), false, irSimpleFunction.getIsInline(), false, false, false, false, null, irSimpleFunction.getIsInfix(), irSimpleFunction.getIsOperator(), false, false, false, false, false, 8190592, null));
                this.p.printWithNoIndent(str);
                if (z) {
                    printTypeParametersWithNoIndent(irSimpleFunction, " ");
                }
                if (z && (extensionReceiverParameter = irSimpleFunction.getExtensionReceiverParameter()) != null) {
                    printExtensionReceiverParameter(extensionReceiverParameter);
                }
                this.p.printWithNoIndent(str2);
                if (z2) {
                    printValueParametersWithNoIndent(irSimpleFunction);
                    if (this.options.getPrintUnitReturnType() || !IrTypePredicatesKt.isUnit(irSimpleFunction.getReturnType())) {
                        this.p.printWithNoIndent(": ");
                        printTypeWithNoIndent(irSimpleFunction.getReturnType());
                    }
                    printWhereClauseIfNeededWithNoIndent(irSimpleFunction);
                    IrBody body = irSimpleFunction.getBody();
                    if (body != null) {
                        if (this.options.getBodyPrintingStrategy() == BodyPrintingStrategy.PRINT_BODIES) {
                            this.p.printWithNoIndent(" ");
                        }
                        body.accept(this, null);
                    }
                }
                if (!z2 || irSimpleFunction.getBody() == null || this.options.getBodyPrintingStrategy() != BodyPrintingStrategy.PRINT_BODIES) {
                    this.p.printlnWithNoIndent(new Object[0]);
                }
            } finally {
                this.options.getCustomDumpStrategy().didPrintElement(irSimpleFunction2, irDeclaration, this.p);
            }
        }
    }

    private final void printExtensionReceiverParameter(IrValueParameter irValueParameter) {
        printTypeWithNoIndent(irValueParameter.getType());
        this.p.printWithNoIndent(".");
    }

    private final void printValueParametersWithNoIndent(IrFunction irFunction) {
        this.p.printWithNoIndent("(");
        int i = 0;
        for (Object obj : irFunction.getValueParameters()) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            IrValueParameter irValueParameter = (IrValueParameter) obj;
            p(i > 0, ",");
            printAValueParameterWithNoIndent(irValueParameter, irFunction);
            i = i2;
        }
        this.p.printWithNoIndent(Constant.AFTER_QUTO);
    }

    private final void printAValueParameterWithNoIndent(IrValueParameter irValueParameter, IrDeclaration irDeclaration) {
        printAnnotationsWithNoIndent(irValueParameter);
        printParameterModifiersWithNoIndent(irValueParameter.getVarargElementType() != null, irValueParameter.getIsCrossinline(), irValueParameter.getIsNoinline(), irValueParameter.getIsHidden(), irValueParameter.getIsAssignable());
        this.p.printWithNoIndent(irValueParameter.getName().asString());
        this.p.printWithNoIndent(": ");
        IrType varargElementType = irValueParameter.getVarargElementType();
        if (varargElementType == null) {
            varargElementType = irValueParameter.getType();
        }
        printTypeWithNoIndent(varargElementType);
        IrExpressionBody defaultValue = irValueParameter.getDefaultValue();
        if (defaultValue != null) {
            if (this.options.getBodyPrintingStrategy() == BodyPrintingStrategy.PRINT_BODIES) {
                this.p.printWithNoIndent(" = ");
            }
            defaultValue.accept(this, irDeclaration);
        }
    }

    private final void printAccessor(IrSimpleFunction irSimpleFunction, String str, IrDeclaration irDeclaration) {
        printSimpleFunction(irSimpleFunction, irDeclaration, "", str, false, !Intrinsics.areEqual(irSimpleFunction.getOrigin(), IrDeclarationOrigin.DEFAULT_PROPERTY_ACCESSOR.INSTANCE));
    }

    private final void printVariable(boolean isVar, Name name2, IrType type) {
        this.p.printWithNoIndent(isVar ? PsiKeyword.VAR : "val");
        this.p.printWithNoIndent(" ");
        this.p.printWithNoIndent(name2.asString());
        this.p.printWithNoIndent(": ");
        printTypeWithNoIndent(type);
    }

    /* JADX INFO: renamed from: visitExpressionBody, reason: avoid collision after fix types in other method */
    public void visitExpressionBody2(final IrExpressionBody body, final IrDeclaration data) {
        Intrinsics.checkNotNullParameter(body, "body");
        printBody(body, data, new Function0<Unit>() { // from class: org.jetbrains.kotlin.ir.util.KotlinLikeDumper.visitExpressionBody.1
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
                body.getExpression().accept(this, data);
            }
        });
    }

    /* JADX INFO: renamed from: visitBlockBody, reason: avoid collision after fix types in other method */
    public void visitBlockBody2(final IrBlockBody body, final IrDeclaration data) {
        Intrinsics.checkNotNullParameter(body, "body");
        printBody(body, data, new Function0<Unit>() { // from class: org.jetbrains.kotlin.ir.util.KotlinLikeDumper.visitBlockBody.1
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
                KotlinLikeDumper.printStatementContainer$default(KotlinLikeDumper.this, body, "{", "}", data, false, 8, null);
                KotlinLikeDumper.this.getP().printlnWithNoIndent(new Object[0]);
            }
        });
    }

    static /* synthetic */ void printStatementContainer$default(KotlinLikeDumper kotlinLikeDumper, IrStatementContainer irStatementContainer, String str, String str2, IrDeclaration irDeclaration, boolean z, int i, Object obj) {
        if ((i & 8) != 0) {
            z = true;
        }
        kotlinLikeDumper.printStatementContainer(irStatementContainer, str, str2, irDeclaration, z);
    }

    private final void printStatementContainer(IrStatementContainer irStatementContainer, String str, String str2, IrDeclaration irDeclaration, boolean z) {
        this.p.printlnWithNoIndent(str);
        if (z) {
            this.p.pushIndent();
        }
        for (IrStatement irStatement : irStatementContainer.getStatements()) {
            if (irStatement instanceof IrExpression) {
                this.p.printIndent();
            }
            irStatement.accept(this, irDeclaration);
            this.p.printlnWithNoIndent(new Object[0]);
        }
        if (z) {
            this.p.popIndent();
        }
        this.p.print(str2);
    }

    /* JADX INFO: renamed from: visitSyntheticBody, reason: avoid collision after fix types in other method */
    public void visitSyntheticBody2(final IrSyntheticBody body, IrDeclaration data) {
        Intrinsics.checkNotNullParameter(body, "body");
        printBody(body, data, new Function0<Unit>() { // from class: org.jetbrains.kotlin.ir.util.KotlinLikeDumper.visitSyntheticBody.1
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
                KotlinLikeDumper.this.getP().printlnWithNoIndent("/* Synthetic body for " + body.getKind() + " */");
            }
        });
    }

    static /* synthetic */ void printMemberAccessExpressionWithNoIndent$default(KotlinLikeDumper kotlinLikeDumper, IrMemberAccessExpression irMemberAccessExpression, String str, List list, IrClassSymbol irClassSymbol, boolean z, IrDeclaration irDeclaration, String str2, boolean z2, boolean z3, int i, Object obj) {
        kotlinLikeDumper.printMemberAccessExpressionWithNoIndent(irMemberAccessExpression, str, list, irClassSymbol, z, irDeclaration, (i & 32) != 0 ? "." : str2, (i & 64) != 0 ? true : z2, (i & 128) != 0 ? false : z3);
    }

    private final void printMemberAccessExpressionWithNoIndent(IrMemberAccessExpression<?> irMemberAccessExpression, String str, List<? extends IrValueParameter> list, IrClassSymbol irClassSymbol, boolean z, IrDeclaration irDeclaration, String str2, boolean z2, boolean z3) {
        boolean z4 = ((irMemberAccessExpression.getDispatchReceiver() == null && irClassSymbol == null) || irMemberAccessExpression.getExtensionReceiver() == null) ? false : true;
        if (z4) {
            this.p.printWithNoIndent("(");
        }
        if (irClassSymbol != null) {
            this.p.printWithNoIndent("super<" + getSafeName(irClassSymbol) + '>');
        }
        IrExpression dispatchReceiver = irMemberAccessExpression.getDispatchReceiver();
        if (dispatchReceiver != null && irClassSymbol == null) {
            dispatchReceiver.accept(this, irDeclaration);
        }
        p(z4, ",");
        IrExpression extensionReceiver = irMemberAccessExpression.getExtensionReceiver();
        if (extensionReceiver != null) {
            extensionReceiver.accept(this, irDeclaration);
        }
        if (z4) {
            this.p.printWithNoIndent(Constant.AFTER_QUTO);
        }
        if (!z2 || irMemberAccessExpression.getDispatchReceiver() != null || irMemberAccessExpression.getExtensionReceiver() != null || irClassSymbol != null) {
            this.p.printWithNoIndent(str2);
        }
        this.p.printWithNoIndent(str);
        if (z && irMemberAccessExpression.getTypeArgumentsCount() == 0 && (irMemberAccessExpression.getValueArgumentsCount() == 0 || printMemberAccessExpressionWithNoIndent$allValueArgumentsAreNull(irMemberAccessExpression))) {
            return;
        }
        if (z3) {
            this.p.printWithNoIndent(RefSpec.WILDCARD_SUFFIX);
        }
        if (irMemberAccessExpression.getTypeArgumentsCount() > 0) {
            this.p.printWithNoIndent("<");
            int typeArgumentsCount = irMemberAccessExpression.getTypeArgumentsCount();
            int i = 0;
            while (i < typeArgumentsCount) {
                p(i > 0, ",");
                IrType typeArgument = irMemberAccessExpression.getTypeArgument(i);
                if (typeArgument != null) {
                    printTypeWithNoIndent(typeArgument);
                    Unit unit = Unit.INSTANCE;
                } else {
                    Intrinsics.checkNotNullExpressionValue(this.p.printWithNoIndent(commentBlock("null")), "printWithNoIndent(...)");
                }
                i++;
            }
            this.p.printWithNoIndent(">");
        }
        this.p.printWithNoIndent("(");
        int valueArgumentsCount = irMemberAccessExpression.getValueArgumentsCount();
        int i2 = 0;
        while (i2 < valueArgumentsCount) {
            IrExpression valueArgument = irMemberAccessExpression.getValueArgument(i2);
            if (valueArgument != null) {
                p(i2 > 0, ",");
                if (i2 < list.size()) {
                    this.p.printWithNoIndent(list.get(i2).getName().asString() + " = ");
                }
                valueArgument.accept(this, irDeclaration);
            }
            i2++;
        }
        this.p.printWithNoIndent(Constant.AFTER_QUTO);
        if (z3) {
            this.p.printWithNoIndent("*/");
        }
    }

    private static final boolean printMemberAccessExpressionWithNoIndent$allValueArgumentsAreNull(IrMemberAccessExpression<?> irMemberAccessExpression) {
        int valueArgumentsCount = irMemberAccessExpression.getValueArgumentsCount();
        for (int i = 0; i < valueArgumentsCount; i++) {
            if (irMemberAccessExpression.getValueArgument(i) != null) {
                return false;
            }
        }
        return true;
    }

    private final void printConstructorCallWithNoIndent(IrFunctionAccessExpression irFunctionAccessExpression, IrDeclaration irDeclaration) {
        String str;
        IrClass safeParentClassOrNull = getSafeParentClassOrNull(irFunctionAccessExpression.getSymbol());
        IrDeclarationParent parent = irDeclaration != null ? irDeclaration.getParent() : null;
        IrClass irClass = parent instanceof IrClass ? (IrClass) parent : null;
        String safeParentClassName = getSafeParentClassName(irFunctionAccessExpression.getSymbol());
        if (irDeclaration instanceof IrConstructor) {
            if (irClass == null) {
                str = "delegating/*" + safeParentClassName + "*/";
            } else {
                str = Intrinsics.areEqual(irClass, safeParentClassOrNull) ? "this/*" + safeParentClassName + "*/" : "super/*" + safeParentClassName + "*/";
            }
            safeParentClassName = str;
        }
        printMemberAccessExpressionWithNoIndent$default(this, irFunctionAccessExpression, safeParentClassName, getSafeValueParameters(irFunctionAccessExpression.getSymbol()), null, false, irDeclaration, null, false, false, 224, null);
    }

    /* JADX INFO: renamed from: visitFunctionExpression, reason: avoid collision after fix types in other method */
    public void visitFunctionExpression2(IrFunctionExpression expression, IrDeclaration data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        this.p.withholdIndentOnce();
        IrSimpleFunction function = expression.getFunction();
        String strAsString = expression.getFunction().getName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        printSimpleFunction(function, data, "fun ", strAsString, true, true);
    }

    private final void printFieldAccess(IrFieldAccessExpression irFieldAccessExpression, IrDeclaration irDeclaration) {
        IrExpression receiver = irFieldAccessExpression.getReceiver();
        if (receiver != null) {
            receiver.accept(this, irDeclaration);
        }
        IrClassSymbol superQualifierSymbol = irFieldAccessExpression.getSuperQualifierSymbol();
        if (superQualifierSymbol != null) {
            if (irFieldAccessExpression.getReceiver() != null) {
                this.p.printWithNoIndent("(");
            }
            this.p.printWithNoIndent("super<" + getSafeName(superQualifierSymbol) + '>');
            if (irFieldAccessExpression.getReceiver() != null) {
                this.p.printWithNoIndent(Constant.AFTER_QUTO);
            }
        }
        if (irFieldAccessExpression.getReceiver() != null || irFieldAccessExpression.getSuperQualifierSymbol() != null) {
            this.p.printWithNoIndent(".");
        }
        this.p.printWithNoIndent("#" + getSafeName(irFieldAccessExpression.getSymbol()));
    }

    /* JADX INFO: renamed from: visitThrow, reason: avoid collision after fix types in other method */
    public void visitThrow2(IrThrow expression, IrDeclaration data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        this.p.printWithNoIndent("throw ");
        expression.getValue().accept(this, data);
    }

    private final void printLabel(IrLoop irLoop) {
        String label = irLoop.getLabel();
        if (label != null) {
            this.p.printWithNoIndent(label);
            this.p.printWithNoIndent("@ ");
        }
    }

    private final void printCallableReferenceWithNoIndent(IrCallableReference<?> irCallableReference, List<? extends IrValueParameter> list, IrDeclaration irDeclaration) {
        IrClass safeParentClassOrNull;
        if (irCallableReference.getDispatchReceiver() == null && irCallableReference.getExtensionReceiver() == null && (safeParentClassOrNull = getSafeParentClassOrNull(irCallableReference.getSymbol())) != null) {
            this.p.printWithNoIndent(safeParentClassOrNull.getName().asString());
        }
        IrCallableReference<?> irCallableReference2 = irCallableReference;
        IrSymbolOwner owner = irCallableReference.getSymbol().getOwner();
        Intrinsics.checkNotNull(owner, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName");
        String strAsString = ((IrDeclarationWithName) owner).getName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        printMemberAccessExpressionWithNoIndent(irCallableReference2, strAsString, list, null, true, irDeclaration, "::", false, true);
    }

    private final void p(boolean condition, String s) {
        if (condition) {
            this.p.printWithNoIndent(s + StringUtil.SPACE);
        }
    }

    static /* synthetic */ void p$default(KotlinLikeDumper kotlinLikeDumper, Object obj, Object obj2, Function1 function1, int i, Object obj3) {
        if ((i & 2) != 0) {
            obj2 = null;
        }
        kotlinLikeDumper.p(obj, obj2, function1);
    }

    private final <T> void p(T value, T defaultValue, Function1<? super T, String> getString) {
        if (value == null) {
            return;
        }
        p(!Intrinsics.areEqual(value, defaultValue), getString.invoke(value));
    }

    private final String commentBlock(String text) {
        return "/* " + text + " */";
    }

    private final String customModifier(String text) {
        return "/* " + text + " */";
    }

    /* JADX INFO: compiled from: dumpKotlinLike.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0082\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/KotlinLikeDumper$Companion;", "", "()V", "CUSTOM_MODIFIER_END", "", "CUSTOM_MODIFIER_START", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* JADX INFO: renamed from: visitModuleFragment, reason: avoid collision after fix types in other method */
    public void visitModuleFragment2(IrModuleFragment declaration, IrDeclaration data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrModuleFragment irModuleFragment = declaration;
        if (this.options.getCustomDumpStrategy().willPrintElement(irModuleFragment, data, this.p)) {
            try {
                this.p.println("// MODULE: " + declaration.getName().asString());
                declaration.acceptChildren(this, null);
            } finally {
                this.options.getCustomDumpStrategy().didPrintElement(irModuleFragment, data, this.p);
            }
        }
    }

    /* JADX INFO: renamed from: visitFile, reason: avoid collision after fix types in other method */
    public void visitFile2(IrFile declaration, IrDeclaration data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrFile irFile = declaration;
        if (this.options.getCustomDumpStrategy().willPrintElement(irFile, data, this.p)) {
            try {
                if (this.options.getPrintRegionsPerFile()) {
                    this.p.println("//region block: " + IrDeclarationsKt.getName(declaration));
                }
                if (this.options.getPrintFileName()) {
                    this.p.println("// FILE: " + IrDeclarationsKt.getName(declaration));
                }
                if (this.options.getPrintFilePath()) {
                    this.p.println("// path: " + IrDeclarationsKt.getPath(declaration));
                }
                printlnAnnotations(declaration, "file");
                FqName fqName = declaration.getPackageFragmentDescriptor().getFqName();
                if (!fqName.isRoot()) {
                    this.p.println("package " + fqName.asString());
                }
                if (!this.p.isEmpty()) {
                    this.p.printlnWithNoIndent(new Object[0]);
                }
                Iterator<T> it = orderedDeclarations(declaration.getDeclarations()).iterator();
                while (it.hasNext()) {
                    ((IrDeclaration) it.next()).accept(this, null);
                }
                if (this.options.getPrintRegionsPerFile()) {
                    this.p.println("//endregion");
                }
            } finally {
                this.options.getCustomDumpStrategy().didPrintElement(irFile, data, this.p);
            }
        }
    }

    /* JADX INFO: renamed from: visitClass, reason: avoid collision after fix types in other method */
    public void visitClass2(IrClass declaration, IrDeclaration data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrClass irClass = declaration;
        if (this.options.getCustomDumpStrategy().willPrintElement(irClass, data, this.p)) {
            try {
                printlnAnnotations$default(this, declaration, null, 1, null);
                this.p.printIndent();
                printModifiersWithNoIndent(declaration, new CustomKotlinLikeDumpStrategy.Modifiers(declaration.getVisibility(), declaration.getIsExpect(), declaration.getModality(), declaration.getIsExternal(), false, false, false, false, false, declaration.getIsInner(), false, declaration.getIsValue(), declaration.getIsData(), declaration.getIsCompanion(), declaration.getIsFun(), declaration.getKind(), false, false, false, false, false, false, false, 8324592, null));
                this.p.printWithNoIndent(declaration.getName().asString());
                boolean z = true;
                printTypeParametersWithNoIndent$default(this, declaration, null, 1, null);
                if (!declaration.getSuperTypes().isEmpty()) {
                    for (IrType irType : orderedTypes(declaration.getSuperTypes())) {
                        if (!IrTypePredicatesKt.isAny(irType)) {
                            if (!z) {
                                this.p.printWithNoIndent(", ");
                            } else {
                                this.p.printWithNoIndent(" : ");
                                z = false;
                            }
                            printTypeWithNoIndent(irType);
                        }
                    }
                }
                printWhereClauseIfNeededWithNoIndent(declaration);
                this.p.printlnWithNoIndent(" {");
                this.p.pushIndent();
                Iterator<T> it = orderedDeclarations(declaration.getDeclarations()).iterator();
                while (it.hasNext()) {
                    ((IrDeclaration) it.next()).accept(this, declaration);
                }
                this.p.popIndent();
                this.p.println("}");
                this.p.printlnWithNoIndent(new Object[0]);
            } finally {
                this.options.getCustomDumpStrategy().didPrintElement(irClass, data, this.p);
            }
        }
    }

    /* JADX INFO: renamed from: visitTypeAlias, reason: avoid collision after fix types in other method */
    public void visitTypeAlias2(IrTypeAlias declaration, IrDeclaration data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrTypeAlias irTypeAlias = declaration;
        if (this.options.getCustomDumpStrategy().willPrintElement(irTypeAlias, data, this.p)) {
            try {
                printlnAnnotations$default(this, declaration, null, 1, null);
                this.p.printIndent();
                printVisibility(declaration.getVisibility());
                p(declaration.getIsActual(), "actual");
                this.p.printWithNoIndent("typealias ");
                this.p.printWithNoIndent(declaration.getName().asString());
                printTypeParametersWithNoIndent$default(this, declaration, null, 1, null);
                this.p.printWithNoIndent(" = ");
                printTypeWithNoIndent(declaration.getExpandedType());
                this.p.printlnWithNoIndent(new Object[0]);
            } finally {
                this.options.getCustomDumpStrategy().didPrintElement(irTypeAlias, data, this.p);
            }
        }
    }

    /* JADX INFO: renamed from: visitEnumEntry, reason: avoid collision after fix types in other method */
    public void visitEnumEntry2(IrEnumEntry declaration, IrDeclaration data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrEnumEntry irEnumEntry = declaration;
        if (this.options.getCustomDumpStrategy().willPrintElement(irEnumEntry, data, this.p)) {
            try {
                if (declaration.getCorrespondingClass() != null) {
                    this.p.println(new Object[0]);
                }
                printlnAnnotations$default(this, declaration, null, 1, null);
                this.p.printIndent();
                this.p.printWithNoIndent(declaration.getName());
                IrExpressionBody initializerExpression = declaration.getInitializerExpression();
                if (initializerExpression != null) {
                    if (this.options.getBodyPrintingStrategy() == BodyPrintingStrategy.PRINT_BODIES) {
                        this.p.printWithNoIndent(" = ");
                    }
                    initializerExpression.accept(this, declaration);
                }
                this.p.println(new Object[0]);
                IrClass correspondingClass = declaration.getCorrespondingClass();
                if (correspondingClass != null) {
                    correspondingClass.accept(this, declaration);
                }
                this.p.println(new Object[0]);
            } finally {
                this.options.getCustomDumpStrategy().didPrintElement(irEnumEntry, data, this.p);
            }
        }
    }

    /* JADX INFO: renamed from: visitAnonymousInitializer, reason: avoid collision after fix types in other method */
    public void visitAnonymousInitializer2(IrAnonymousInitializer declaration, IrDeclaration data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrAnonymousInitializer irAnonymousInitializer = declaration;
        if (this.options.getCustomDumpStrategy().willPrintElement(irAnonymousInitializer, data, this.p)) {
            try {
                printlnAnnotations$default(this, declaration, null, 1, null);
                this.p.printIndent();
                p(declaration.getIsStatic(), customModifier("static"));
                this.p.printWithNoIndent(ConfigConstants.CONFIG_INIT_SECTION);
                if (this.options.getBodyPrintingStrategy() == BodyPrintingStrategy.PRINT_BODIES) {
                    this.p.printWithNoIndent(" ");
                }
                declaration.getBody().accept(this, declaration);
                this.p.printlnWithNoIndent(new Object[0]);
            } finally {
                this.options.getCustomDumpStrategy().didPrintElement(irAnonymousInitializer, data, this.p);
            }
        }
    }

    /* JADX INFO: renamed from: visitConstructor, reason: avoid collision after fix types in other method */
    public void visitConstructor2(IrConstructor declaration, IrDeclaration data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrConstructor irConstructor = declaration;
        if (this.options.getCustomDumpStrategy().willPrintElement(irConstructor, data, this.p)) {
            try {
                printlnAnnotations$default(this, declaration, null, 1, null);
                this.p.printIndent();
                printModifiersWithNoIndent(declaration, new CustomKotlinLikeDumpStrategy.Modifiers(declaration.getVisibility(), declaration.getIsExpect(), null, declaration.getIsExternal(), false, false, false, false, false, false, declaration.getIsInline(), false, false, false, false, null, false, false, false, false, false, false, false, 8387572, null));
                this.p.printWithNoIndent(JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME);
                printTypeParametersWithNoIndent$default(this, declaration, null, 1, null);
                printValueParametersWithNoIndent(declaration);
                printWhereClauseIfNeededWithNoIndent(declaration);
                if (declaration.getIsPrimary()) {
                    this.p.printWithNoIndent(" ", customModifier("primary"));
                }
                IrBody body = declaration.getBody();
                if (body != null) {
                    if (this.options.getBodyPrintingStrategy() == BodyPrintingStrategy.PRINT_BODIES) {
                        this.p.printWithNoIndent(" ");
                    }
                    body.accept(this, declaration);
                }
                this.p.printlnWithNoIndent(new Object[0]);
            } finally {
                this.options.getCustomDumpStrategy().didPrintElement(irConstructor, data, this.p);
            }
        }
    }

    /* JADX INFO: renamed from: visitTypeParameter, reason: avoid collision after fix types in other method */
    public void visitTypeParameter2(IrTypeParameter declaration, IrDeclaration data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrTypeParameter irTypeParameter = declaration;
        if (this.options.getCustomDumpStrategy().willPrintElement(irTypeParameter, data, this.p)) {
            try {
                printATypeParameterWithNoIndent(declaration);
                if (declaration.getSuperTypes().size() > 1) {
                    printWhereClauseTypesWithNoIndent(declaration, true);
                }
            } finally {
                this.options.getCustomDumpStrategy().didPrintElement(irTypeParameter, data, this.p);
            }
        }
    }

    /* JADX INFO: renamed from: visitValueParameter, reason: avoid collision after fix types in other method */
    public void visitValueParameter2(IrValueParameter declaration, IrDeclaration data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrValueParameter irValueParameter = declaration;
        if (this.options.getCustomDumpStrategy().willPrintElement(irValueParameter, data, this.p)) {
            try {
                printAValueParameterWithNoIndent(declaration, data);
            } finally {
                this.options.getCustomDumpStrategy().didPrintElement(irValueParameter, data, this.p);
            }
        }
    }

    /* JADX INFO: renamed from: visitProperty, reason: avoid collision after fix types in other method */
    public void visitProperty2(IrProperty declaration, IrDeclaration data) throws Throwable {
        IrDeclaration irDeclaration;
        IrType returnType;
        IrExpressionBody initializer;
        IrValueParameter extensionReceiverParameter;
        List<IrSimpleFunctionSymbol> overriddenSymbols;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrProperty irProperty = declaration;
        if (this.options.getCustomDumpStrategy().willPrintElement(irProperty, data, this.p)) {
            try {
                if (this.options.getPrintFakeOverridesStrategy() != FakeOverridesStrategy.NONE || !declaration.getIsFakeOverride()) {
                    printlnAnnotations$default(this, declaration, null, 1, null);
                    this.p.printIndent();
                    IrProperty irProperty2 = declaration;
                    DescriptorVisibility visibility = declaration.getVisibility();
                    boolean isExpect = declaration.getIsExpect();
                    Modality modality = declaration.getModality();
                    boolean zIsExternal = declaration.getIsExternal();
                    IrSimpleFunction getter = declaration.getGetter();
                    boolean z = (getter == null || (overriddenSymbols = getter.getOverriddenSymbols()) == null || !(overriddenSymbols.isEmpty() ^ true)) ? false : true;
                    boolean isFakeOverride = declaration.getIsFakeOverride();
                    boolean isLateinit = declaration.getIsLateinit();
                    IrSimpleFunction getter2 = declaration.getGetter();
                    try {
                        printModifiersWithNoIndent(irProperty2, new CustomKotlinLikeDumpStrategy.Modifiers(visibility, isExpect, modality, zIsExternal, z, isFakeOverride, isLateinit, false, getter2 != null && getter2.getIsSuspend(), false, false, false, false, false, false, null, false, false, false, false, false, false, false, 8387200, null));
                        p(declaration.getIsConst(), PsiKeyword.CONST);
                        this.p.printWithNoIndent(declaration.getIsVar() ? PsiKeyword.VAR : "val");
                        this.p.printWithNoIndent(" ");
                        IrSimpleFunction getter3 = declaration.getGetter();
                        if (getter3 != null) {
                            printTypeParametersWithNoIndent(getter3, " ");
                        }
                        IrSimpleFunction getter4 = declaration.getGetter();
                        if (getter4 != null && (extensionReceiverParameter = getter4.getExtensionReceiverParameter()) != null) {
                            printExtensionReceiverParameter(extensionReceiverParameter);
                        }
                        this.p.printWithNoIndent(declaration.getName().asString());
                        IrField backingField = declaration.getBackingField();
                        if (backingField != null && !declaration.getIsDelegated()) {
                            this.p.printWithNoIndent(": ");
                            printTypeWithNoIndent(backingField.getType());
                        } else {
                            IrSimpleFunction getter5 = declaration.getGetter();
                            if (getter5 != null && (returnType = getter5.getReturnType()) != null) {
                                this.p.printWithNoIndent(": ");
                                printTypeWithNoIndent(returnType);
                            }
                        }
                        if (declaration.getIsDelegated()) {
                            this.p.printWithNoIndent(" ", commentBlock("by"));
                        }
                        this.p.printlnWithNoIndent(new Object[0]);
                        this.p.pushIndent();
                        IrField backingField2 = declaration.getBackingField();
                        if (backingField2 != null && (initializer = backingField2.getInitializer()) != null) {
                            if (this.options.getBodyPrintingStrategy() != BodyPrintingStrategy.NO_BODIES) {
                                this.p.print("field");
                            }
                            if (this.options.getBodyPrintingStrategy() == BodyPrintingStrategy.PRINT_BODIES) {
                                this.p.printWithNoIndent(" = ");
                            }
                            initializer.accept(this, declaration);
                            if (this.options.getBodyPrintingStrategy() != BodyPrintingStrategy.NO_BODIES) {
                                this.p.printlnWithNoIndent(new Object[0]);
                            }
                        }
                        IrSimpleFunction getter6 = declaration.getGetter();
                        if (getter6 != null) {
                            printAccessor(getter6, "get", declaration);
                        }
                        IrSimpleFunction setter = declaration.getSetter();
                        if (setter != null) {
                            printAccessor(setter, FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX, declaration);
                        }
                        this.p.popIndent();
                        this.p.printlnWithNoIndent(new Object[0]);
                        this.options.getCustomDumpStrategy().didPrintElement(irProperty, data, this.p);
                        return;
                    } catch (Throwable th) {
                        th = th;
                        irDeclaration = data;
                    }
                } else {
                    this.options.getCustomDumpStrategy().didPrintElement(irProperty, data, this.p);
                    return;
                }
            } catch (Throwable th2) {
                th = th2;
                irDeclaration = data;
            }
            this.options.getCustomDumpStrategy().didPrintElement(irProperty, irDeclaration, this.p);
            throw th;
        }
    }

    /* JADX INFO: renamed from: visitField, reason: avoid collision after fix types in other method */
    public void visitField2(IrField declaration, IrDeclaration data) throws Throwable {
        IrDeclaration irDeclaration;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrField irField = declaration;
        if (!this.options.getCustomDumpStrategy().willPrintElement(irField, data, this.p)) {
            return;
        }
        try {
            printlnAnnotations$default(this, declaration, null, 1, null);
            this.p.printIndent();
            try {
                printModifiersWithNoIndent(declaration, new CustomKotlinLikeDumpStrategy.Modifiers(declaration.getVisibility(), false, null, declaration.getIsExternal(), false, false, false, false, false, false, false, false, false, false, false, null, false, false, false, false, false, false, false, 8388598, null));
                if (declaration.getIsStatic() || declaration.getIsFinal()) {
                    this.p.printWithNoIndent("/* ");
                    p(declaration.getIsStatic(), "static");
                    p(declaration.getIsFinal(), "final");
                    this.p.printWithNoIndent("field");
                    this.p.printWithNoIndent(" */");
                    this.p.printWithNoIndent(" ");
                }
                this.p.printWithNoIndent(declaration.getIsFinal() ? "val " : "var ");
                this.p.printWithNoIndent(declaration.getName().asString() + ": ");
                printTypeWithNoIndent(declaration.getType());
                IrExpressionBody initializer = declaration.getInitializer();
                if (initializer != null) {
                    if (this.options.getBodyPrintingStrategy() == BodyPrintingStrategy.PRINT_BODIES) {
                        this.p.printWithNoIndent(" = ");
                    }
                    initializer.accept(this, declaration);
                }
                this.p.printlnWithNoIndent(new Object[0]);
                this.options.getCustomDumpStrategy().didPrintElement(irField, data, this.p);
            } catch (Throwable th) {
                th = th;
                irDeclaration = data;
                this.options.getCustomDumpStrategy().didPrintElement(irField, irDeclaration, this.p);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            irDeclaration = data;
        }
    }

    /* JADX INFO: renamed from: visitVariable, reason: avoid collision after fix types in other method */
    public void visitVariable2(IrVariable declaration, IrDeclaration data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrVariable irVariable = declaration;
        if (this.options.getCustomDumpStrategy().willPrintElement(irVariable, data, this.p)) {
            try {
                printlnAnnotations$default(this, declaration, null, 1, null);
                this.p.printIndent();
                p(declaration.getIsLateinit(), "lateinit");
                p(declaration.getIsConst(), PsiKeyword.CONST);
                printVariable(declaration.getIsVar(), declaration.getName(), declaration.getType());
                IrExpression initializer = declaration.getInitializer();
                if (initializer != null) {
                    this.p.printWithNoIndent(" = ");
                    initializer.accept(this, declaration);
                }
            } finally {
                this.options.getCustomDumpStrategy().didPrintElement(irVariable, data, this.p);
            }
        }
    }

    /* JADX INFO: renamed from: visitLocalDelegatedProperty, reason: avoid collision after fix types in other method */
    public void visitLocalDelegatedProperty2(IrLocalDelegatedProperty declaration, IrDeclaration data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrLocalDelegatedProperty irLocalDelegatedProperty = declaration;
        if (this.options.getCustomDumpStrategy().willPrintElement(irLocalDelegatedProperty, data, this.p)) {
            try {
                printlnAnnotations$default(this, declaration, null, 1, null);
                this.p.printIndent();
                printVariable(declaration.getIsVar(), declaration.getName(), declaration.getType());
                this.p.printlnWithNoIndent(new Object[0]);
                this.p.pushIndent();
                declaration.getDelegate().accept(this, declaration);
                this.p.printlnWithNoIndent(new Object[0]);
                printAccessor(declaration.getGetter(), "get", declaration);
                IrSimpleFunction setter = declaration.getSetter();
                if (setter != null) {
                    printAccessor(setter, FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX, declaration);
                }
                this.p.popIndent();
                this.p.printlnWithNoIndent(new Object[0]);
            } finally {
                this.options.getCustomDumpStrategy().didPrintElement(irLocalDelegatedProperty, data, this.p);
            }
        }
    }

    private final <Body extends IrBody> void printBody(Body body, IrDeclaration data, Function0<Unit> actuallyPrint) {
        Body body2 = body;
        if (this.options.getCustomDumpStrategy().willPrintElement(body2, data, this.p)) {
            try {
                int i = WhenMappings.$EnumSwitchMapping$0[this.options.getBodyPrintingStrategy().ordinal()];
                if (i == 2) {
                    body.acceptChildren(new IrElementVisitor<Unit, IrDeclaration>() { // from class: org.jetbrains.kotlin.ir.util.KotlinLikeDumper$printBody$1$1
                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitAnonymousInitializer */
                        public /* bridge */ /* synthetic */ Unit visitAnonymousInitializer2(IrAnonymousInitializer irAnonymousInitializer, IrDeclaration irDeclaration) {
                            visitAnonymousInitializer2(irAnonymousInitializer, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitAnonymousInitializer, reason: avoid collision after fix types in other method */
                        public void visitAnonymousInitializer2(IrAnonymousInitializer irAnonymousInitializer, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitAnonymousInitializer(this, irAnonymousInitializer, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitBlock */
                        public /* bridge */ /* synthetic */ Unit visitBlock2(IrBlock irBlock, IrDeclaration irDeclaration) {
                            visitBlock2(irBlock, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitBlock, reason: avoid collision after fix types in other method */
                        public void visitBlock2(IrBlock irBlock, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitBlock(this, irBlock, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitBlockBody */
                        public /* bridge */ /* synthetic */ Unit visitBlockBody2(IrBlockBody irBlockBody, IrDeclaration irDeclaration) {
                            visitBlockBody2(irBlockBody, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitBlockBody, reason: avoid collision after fix types in other method */
                        public void visitBlockBody2(IrBlockBody irBlockBody, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitBlockBody(this, irBlockBody, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitBody */
                        public /* bridge */ /* synthetic */ Unit visitBody2(IrBody irBody, IrDeclaration irDeclaration) {
                            visitBody2(irBody, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitBody, reason: avoid collision after fix types in other method */
                        public void visitBody2(IrBody irBody, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitBody(this, irBody, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitBranch */
                        public /* bridge */ /* synthetic */ Unit visitBranch2(IrBranch irBranch, IrDeclaration irDeclaration) {
                            visitBranch2(irBranch, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitBranch, reason: avoid collision after fix types in other method */
                        public void visitBranch2(IrBranch irBranch, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitBranch(this, irBranch, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitBreak */
                        public /* bridge */ /* synthetic */ Unit visitBreak2(IrBreak irBreak, IrDeclaration irDeclaration) {
                            visitBreak2(irBreak, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitBreak, reason: avoid collision after fix types in other method */
                        public void visitBreak2(IrBreak irBreak, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitBreak(this, irBreak, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitBreakContinue */
                        public /* bridge */ /* synthetic */ Unit visitBreakContinue2(IrBreakContinue irBreakContinue, IrDeclaration irDeclaration) {
                            visitBreakContinue2(irBreakContinue, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitBreakContinue, reason: avoid collision after fix types in other method */
                        public void visitBreakContinue2(IrBreakContinue irBreakContinue, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitBreakContinue(this, irBreakContinue, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitCall */
                        public /* bridge */ /* synthetic */ Unit visitCall2(IrCall irCall, IrDeclaration irDeclaration) {
                            visitCall2(irCall, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitCall, reason: avoid collision after fix types in other method */
                        public void visitCall2(IrCall irCall, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitCall(this, irCall, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        public /* bridge */ /* synthetic */ Unit visitCallableReference(IrCallableReference irCallableReference, IrDeclaration irDeclaration) {
                            visitCallableReference2((IrCallableReference<?>) irCallableReference, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitCallableReference, reason: avoid collision after fix types in other method */
                        public void visitCallableReference2(IrCallableReference<?> irCallableReference, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitCallableReference(this, irCallableReference, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitCatch */
                        public /* bridge */ /* synthetic */ Unit visitCatch2(IrCatch irCatch, IrDeclaration irDeclaration) {
                            visitCatch2(irCatch, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitCatch, reason: avoid collision after fix types in other method */
                        public void visitCatch2(IrCatch irCatch, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitCatch(this, irCatch, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitClass */
                        public /* bridge */ /* synthetic */ Unit visitClass2(IrClass irClass, IrDeclaration irDeclaration) {
                            visitClass2(irClass, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitClass, reason: avoid collision after fix types in other method */
                        public void visitClass2(IrClass irClass, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitClass(this, irClass, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitClassReference */
                        public /* bridge */ /* synthetic */ Unit visitClassReference2(IrClassReference irClassReference, IrDeclaration irDeclaration) {
                            visitClassReference2(irClassReference, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitClassReference, reason: avoid collision after fix types in other method */
                        public void visitClassReference2(IrClassReference irClassReference, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitClassReference(this, irClassReference, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitComposite */
                        public /* bridge */ /* synthetic */ Unit visitComposite2(IrComposite irComposite, IrDeclaration irDeclaration) {
                            visitComposite2(irComposite, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitComposite, reason: avoid collision after fix types in other method */
                        public void visitComposite2(IrComposite irComposite, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitComposite(this, irComposite, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        public /* bridge */ /* synthetic */ Unit visitConst(IrConst irConst, IrDeclaration irDeclaration) {
                            visitConst2((IrConst<?>) irConst, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitConst, reason: avoid collision after fix types in other method */
                        public void visitConst2(IrConst<?> irConst, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitConst(this, irConst, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitConstantArray */
                        public /* bridge */ /* synthetic */ Unit visitConstantArray2(IrConstantArray irConstantArray, IrDeclaration irDeclaration) {
                            visitConstantArray2(irConstantArray, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitConstantArray, reason: avoid collision after fix types in other method */
                        public void visitConstantArray2(IrConstantArray irConstantArray, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitConstantArray(this, irConstantArray, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitConstantObject */
                        public /* bridge */ /* synthetic */ Unit visitConstantObject2(IrConstantObject irConstantObject, IrDeclaration irDeclaration) {
                            visitConstantObject2(irConstantObject, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitConstantObject, reason: avoid collision after fix types in other method */
                        public void visitConstantObject2(IrConstantObject irConstantObject, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitConstantObject(this, irConstantObject, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitConstantPrimitive */
                        public /* bridge */ /* synthetic */ Unit visitConstantPrimitive2(IrConstantPrimitive irConstantPrimitive, IrDeclaration irDeclaration) {
                            visitConstantPrimitive2(irConstantPrimitive, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitConstantPrimitive, reason: avoid collision after fix types in other method */
                        public void visitConstantPrimitive2(IrConstantPrimitive irConstantPrimitive, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitConstantPrimitive(this, irConstantPrimitive, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitConstantValue */
                        public /* bridge */ /* synthetic */ Unit visitConstantValue2(IrConstantValue irConstantValue, IrDeclaration irDeclaration) {
                            visitConstantValue2(irConstantValue, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitConstantValue, reason: avoid collision after fix types in other method */
                        public void visitConstantValue2(IrConstantValue irConstantValue, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitConstantValue(this, irConstantValue, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitConstructor */
                        public /* bridge */ /* synthetic */ Unit visitConstructor2(IrConstructor irConstructor, IrDeclaration irDeclaration) {
                            visitConstructor2(irConstructor, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitConstructor, reason: avoid collision after fix types in other method */
                        public void visitConstructor2(IrConstructor irConstructor, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitConstructor(this, irConstructor, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitConstructorCall */
                        public /* bridge */ /* synthetic */ Unit visitConstructorCall2(IrConstructorCall irConstructorCall, IrDeclaration irDeclaration) {
                            visitConstructorCall2(irConstructorCall, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitConstructorCall, reason: avoid collision after fix types in other method */
                        public void visitConstructorCall2(IrConstructorCall irConstructorCall, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitConstructorCall(this, irConstructorCall, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitContainerExpression */
                        public /* bridge */ /* synthetic */ Unit visitContainerExpression2(IrContainerExpression irContainerExpression, IrDeclaration irDeclaration) {
                            visitContainerExpression2(irContainerExpression, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitContainerExpression, reason: avoid collision after fix types in other method */
                        public void visitContainerExpression2(IrContainerExpression irContainerExpression, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitContainerExpression(this, irContainerExpression, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitContinue */
                        public /* bridge */ /* synthetic */ Unit visitContinue2(IrContinue irContinue, IrDeclaration irDeclaration) {
                            visitContinue2(irContinue, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitContinue, reason: avoid collision after fix types in other method */
                        public void visitContinue2(IrContinue irContinue, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitContinue(this, irContinue, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitDeclaration */
                        public /* bridge */ /* synthetic */ Unit visitDeclaration2(IrDeclarationBase irDeclarationBase, IrDeclaration irDeclaration) {
                            visitDeclaration2(irDeclarationBase, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitDeclarationReference */
                        public /* bridge */ /* synthetic */ Unit visitDeclarationReference2(IrDeclarationReference irDeclarationReference, IrDeclaration irDeclaration) {
                            visitDeclarationReference2(irDeclarationReference, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitDeclarationReference, reason: avoid collision after fix types in other method */
                        public void visitDeclarationReference2(IrDeclarationReference irDeclarationReference, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitDeclarationReference(this, irDeclarationReference, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitDelegatingConstructorCall */
                        public /* bridge */ /* synthetic */ Unit visitDelegatingConstructorCall2(IrDelegatingConstructorCall irDelegatingConstructorCall, IrDeclaration irDeclaration) {
                            visitDelegatingConstructorCall2(irDelegatingConstructorCall, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitDelegatingConstructorCall, reason: avoid collision after fix types in other method */
                        public void visitDelegatingConstructorCall2(IrDelegatingConstructorCall irDelegatingConstructorCall, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitDelegatingConstructorCall(this, irDelegatingConstructorCall, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitDoWhileLoop */
                        public /* bridge */ /* synthetic */ Unit visitDoWhileLoop2(IrDoWhileLoop irDoWhileLoop, IrDeclaration irDeclaration) {
                            visitDoWhileLoop2(irDoWhileLoop, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitDoWhileLoop, reason: avoid collision after fix types in other method */
                        public void visitDoWhileLoop2(IrDoWhileLoop irDoWhileLoop, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitDoWhileLoop(this, irDoWhileLoop, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitDynamicExpression */
                        public /* bridge */ /* synthetic */ Unit visitDynamicExpression2(IrDynamicExpression irDynamicExpression, IrDeclaration irDeclaration) {
                            visitDynamicExpression2(irDynamicExpression, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitDynamicExpression, reason: avoid collision after fix types in other method */
                        public void visitDynamicExpression2(IrDynamicExpression irDynamicExpression, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitDynamicExpression(this, irDynamicExpression, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitDynamicMemberExpression */
                        public /* bridge */ /* synthetic */ Unit visitDynamicMemberExpression2(IrDynamicMemberExpression irDynamicMemberExpression, IrDeclaration irDeclaration) {
                            visitDynamicMemberExpression2(irDynamicMemberExpression, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitDynamicMemberExpression, reason: avoid collision after fix types in other method */
                        public void visitDynamicMemberExpression2(IrDynamicMemberExpression irDynamicMemberExpression, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitDynamicMemberExpression(this, irDynamicMemberExpression, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitDynamicOperatorExpression */
                        public /* bridge */ /* synthetic */ Unit visitDynamicOperatorExpression2(IrDynamicOperatorExpression irDynamicOperatorExpression, IrDeclaration irDeclaration) {
                            visitDynamicOperatorExpression2(irDynamicOperatorExpression, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitDynamicOperatorExpression, reason: avoid collision after fix types in other method */
                        public void visitDynamicOperatorExpression2(IrDynamicOperatorExpression irDynamicOperatorExpression, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitDynamicOperatorExpression(this, irDynamicOperatorExpression, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitElement */
                        public /* bridge */ /* synthetic */ Unit visitElement2(IrElement irElement, IrDeclaration irDeclaration) {
                            visitElement2(irElement, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitElseBranch */
                        public /* bridge */ /* synthetic */ Unit visitElseBranch2(IrElseBranch irElseBranch, IrDeclaration irDeclaration) {
                            visitElseBranch2(irElseBranch, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitElseBranch, reason: avoid collision after fix types in other method */
                        public void visitElseBranch2(IrElseBranch irElseBranch, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitElseBranch(this, irElseBranch, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitEnumConstructorCall */
                        public /* bridge */ /* synthetic */ Unit visitEnumConstructorCall2(IrEnumConstructorCall irEnumConstructorCall, IrDeclaration irDeclaration) {
                            visitEnumConstructorCall2(irEnumConstructorCall, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitEnumConstructorCall, reason: avoid collision after fix types in other method */
                        public void visitEnumConstructorCall2(IrEnumConstructorCall irEnumConstructorCall, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitEnumConstructorCall(this, irEnumConstructorCall, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitEnumEntry */
                        public /* bridge */ /* synthetic */ Unit visitEnumEntry2(IrEnumEntry irEnumEntry, IrDeclaration irDeclaration) {
                            visitEnumEntry2(irEnumEntry, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitEnumEntry, reason: avoid collision after fix types in other method */
                        public void visitEnumEntry2(IrEnumEntry irEnumEntry, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitEnumEntry(this, irEnumEntry, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitErrorCallExpression */
                        public /* bridge */ /* synthetic */ Unit visitErrorCallExpression2(IrErrorCallExpression irErrorCallExpression, IrDeclaration irDeclaration) {
                            visitErrorCallExpression2(irErrorCallExpression, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitErrorCallExpression, reason: avoid collision after fix types in other method */
                        public void visitErrorCallExpression2(IrErrorCallExpression irErrorCallExpression, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitErrorCallExpression(this, irErrorCallExpression, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitErrorDeclaration */
                        public /* bridge */ /* synthetic */ Unit visitErrorDeclaration2(IrErrorDeclaration irErrorDeclaration, IrDeclaration irDeclaration) {
                            visitErrorDeclaration2(irErrorDeclaration, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitErrorDeclaration, reason: avoid collision after fix types in other method */
                        public void visitErrorDeclaration2(IrErrorDeclaration irErrorDeclaration, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitErrorDeclaration(this, irErrorDeclaration, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitErrorExpression */
                        public /* bridge */ /* synthetic */ Unit visitErrorExpression2(IrErrorExpression irErrorExpression, IrDeclaration irDeclaration) {
                            visitErrorExpression2(irErrorExpression, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitErrorExpression, reason: avoid collision after fix types in other method */
                        public void visitErrorExpression2(IrErrorExpression irErrorExpression, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitErrorExpression(this, irErrorExpression, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitExpression */
                        public /* bridge */ /* synthetic */ Unit visitExpression2(IrExpression irExpression, IrDeclaration irDeclaration) {
                            visitExpression2(irExpression, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitExpression, reason: avoid collision after fix types in other method */
                        public void visitExpression2(IrExpression irExpression, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitExpression(this, irExpression, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitExpressionBody */
                        public /* bridge */ /* synthetic */ Unit visitExpressionBody2(IrExpressionBody irExpressionBody, IrDeclaration irDeclaration) {
                            visitExpressionBody2(irExpressionBody, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitExpressionBody, reason: avoid collision after fix types in other method */
                        public void visitExpressionBody2(IrExpressionBody irExpressionBody, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitExpressionBody(this, irExpressionBody, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitExternalPackageFragment */
                        public /* bridge */ /* synthetic */ Unit visitExternalPackageFragment2(IrExternalPackageFragment irExternalPackageFragment, IrDeclaration irDeclaration) {
                            visitExternalPackageFragment2(irExternalPackageFragment, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitExternalPackageFragment, reason: avoid collision after fix types in other method */
                        public void visitExternalPackageFragment2(IrExternalPackageFragment irExternalPackageFragment, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitExternalPackageFragment(this, irExternalPackageFragment, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitField */
                        public /* bridge */ /* synthetic */ Unit visitField2(IrField irField, IrDeclaration irDeclaration) {
                            visitField2(irField, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitField, reason: avoid collision after fix types in other method */
                        public void visitField2(IrField irField, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitField(this, irField, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitFieldAccess */
                        public /* bridge */ /* synthetic */ Unit visitFieldAccess2(IrFieldAccessExpression irFieldAccessExpression, IrDeclaration irDeclaration) {
                            visitFieldAccess2(irFieldAccessExpression, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitFieldAccess, reason: avoid collision after fix types in other method */
                        public void visitFieldAccess2(IrFieldAccessExpression irFieldAccessExpression, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitFieldAccess(this, irFieldAccessExpression, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitFile */
                        public /* bridge */ /* synthetic */ Unit visitFile2(IrFile irFile, IrDeclaration irDeclaration) {
                            visitFile2(irFile, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitFile, reason: avoid collision after fix types in other method */
                        public void visitFile2(IrFile irFile, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitFile(this, irFile, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitFunction */
                        public /* bridge */ /* synthetic */ Unit visitFunction2(IrFunction irFunction, IrDeclaration irDeclaration) {
                            visitFunction2(irFunction, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitFunction, reason: avoid collision after fix types in other method */
                        public void visitFunction2(IrFunction irFunction, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitFunction(this, irFunction, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitFunctionAccess */
                        public /* bridge */ /* synthetic */ Unit visitFunctionAccess2(IrFunctionAccessExpression irFunctionAccessExpression, IrDeclaration irDeclaration) {
                            visitFunctionAccess2(irFunctionAccessExpression, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitFunctionAccess, reason: avoid collision after fix types in other method */
                        public void visitFunctionAccess2(IrFunctionAccessExpression irFunctionAccessExpression, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitFunctionAccess(this, irFunctionAccessExpression, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitFunctionExpression */
                        public /* bridge */ /* synthetic */ Unit visitFunctionExpression2(IrFunctionExpression irFunctionExpression, IrDeclaration irDeclaration) {
                            visitFunctionExpression2(irFunctionExpression, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitFunctionExpression, reason: avoid collision after fix types in other method */
                        public void visitFunctionExpression2(IrFunctionExpression irFunctionExpression, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitFunctionExpression(this, irFunctionExpression, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitFunctionReference */
                        public /* bridge */ /* synthetic */ Unit visitFunctionReference2(IrFunctionReference irFunctionReference, IrDeclaration irDeclaration) {
                            visitFunctionReference2(irFunctionReference, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitFunctionReference, reason: avoid collision after fix types in other method */
                        public void visitFunctionReference2(IrFunctionReference irFunctionReference, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitFunctionReference(this, irFunctionReference, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitGetClass */
                        public /* bridge */ /* synthetic */ Unit visitGetClass2(IrGetClass irGetClass, IrDeclaration irDeclaration) {
                            visitGetClass2(irGetClass, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitGetClass, reason: avoid collision after fix types in other method */
                        public void visitGetClass2(IrGetClass irGetClass, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitGetClass(this, irGetClass, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitGetEnumValue */
                        public /* bridge */ /* synthetic */ Unit visitGetEnumValue2(IrGetEnumValue irGetEnumValue, IrDeclaration irDeclaration) {
                            visitGetEnumValue2(irGetEnumValue, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitGetEnumValue, reason: avoid collision after fix types in other method */
                        public void visitGetEnumValue2(IrGetEnumValue irGetEnumValue, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitGetEnumValue(this, irGetEnumValue, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitGetField */
                        public /* bridge */ /* synthetic */ Unit visitGetField2(IrGetField irGetField, IrDeclaration irDeclaration) {
                            visitGetField2(irGetField, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitGetField, reason: avoid collision after fix types in other method */
                        public void visitGetField2(IrGetField irGetField, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitGetField(this, irGetField, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitGetObjectValue */
                        public /* bridge */ /* synthetic */ Unit visitGetObjectValue2(IrGetObjectValue irGetObjectValue, IrDeclaration irDeclaration) {
                            visitGetObjectValue2(irGetObjectValue, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitGetObjectValue, reason: avoid collision after fix types in other method */
                        public void visitGetObjectValue2(IrGetObjectValue irGetObjectValue, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitGetObjectValue(this, irGetObjectValue, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitGetValue */
                        public /* bridge */ /* synthetic */ Unit visitGetValue2(IrGetValue irGetValue, IrDeclaration irDeclaration) {
                            visitGetValue2(irGetValue, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitGetValue, reason: avoid collision after fix types in other method */
                        public void visitGetValue2(IrGetValue irGetValue, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitGetValue(this, irGetValue, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitInstanceInitializerCall */
                        public /* bridge */ /* synthetic */ Unit visitInstanceInitializerCall2(IrInstanceInitializerCall irInstanceInitializerCall, IrDeclaration irDeclaration) {
                            visitInstanceInitializerCall2(irInstanceInitializerCall, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitInstanceInitializerCall, reason: avoid collision after fix types in other method */
                        public void visitInstanceInitializerCall2(IrInstanceInitializerCall irInstanceInitializerCall, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitInstanceInitializerCall(this, irInstanceInitializerCall, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitLocalDelegatedProperty */
                        public /* bridge */ /* synthetic */ Unit visitLocalDelegatedProperty2(IrLocalDelegatedProperty irLocalDelegatedProperty, IrDeclaration irDeclaration) {
                            visitLocalDelegatedProperty2(irLocalDelegatedProperty, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitLocalDelegatedPropertyReference */
                        public /* bridge */ /* synthetic */ Unit visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, IrDeclaration irDeclaration) {
                            visitLocalDelegatedPropertyReference2(irLocalDelegatedPropertyReference, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitLocalDelegatedPropertyReference, reason: avoid collision after fix types in other method */
                        public void visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitLocalDelegatedPropertyReference(this, irLocalDelegatedPropertyReference, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitLoop */
                        public /* bridge */ /* synthetic */ Unit visitLoop2(IrLoop irLoop, IrDeclaration irDeclaration) {
                            visitLoop2(irLoop, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitLoop, reason: avoid collision after fix types in other method */
                        public void visitLoop2(IrLoop irLoop, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitLoop(this, irLoop, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        public /* bridge */ /* synthetic */ Unit visitMemberAccess(IrMemberAccessExpression irMemberAccessExpression, IrDeclaration irDeclaration) {
                            visitMemberAccess2((IrMemberAccessExpression<?>) irMemberAccessExpression, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitMemberAccess, reason: avoid collision after fix types in other method */
                        public void visitMemberAccess2(IrMemberAccessExpression<?> irMemberAccessExpression, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitMemberAccess(this, irMemberAccessExpression, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitModuleFragment */
                        public /* bridge */ /* synthetic */ Unit visitModuleFragment2(IrModuleFragment irModuleFragment, IrDeclaration irDeclaration) {
                            visitModuleFragment2(irModuleFragment, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitModuleFragment, reason: avoid collision after fix types in other method */
                        public void visitModuleFragment2(IrModuleFragment irModuleFragment, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitModuleFragment(this, irModuleFragment, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitPackageFragment */
                        public /* bridge */ /* synthetic */ Unit visitPackageFragment2(IrPackageFragment irPackageFragment, IrDeclaration irDeclaration) {
                            visitPackageFragment2(irPackageFragment, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitPackageFragment, reason: avoid collision after fix types in other method */
                        public void visitPackageFragment2(IrPackageFragment irPackageFragment, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitPackageFragment(this, irPackageFragment, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitProperty */
                        public /* bridge */ /* synthetic */ Unit visitProperty2(IrProperty irProperty, IrDeclaration irDeclaration) {
                            visitProperty2(irProperty, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitProperty, reason: avoid collision after fix types in other method */
                        public void visitProperty2(IrProperty irProperty, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitProperty(this, irProperty, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitPropertyReference */
                        public /* bridge */ /* synthetic */ Unit visitPropertyReference2(IrPropertyReference irPropertyReference, IrDeclaration irDeclaration) {
                            visitPropertyReference2(irPropertyReference, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitPropertyReference, reason: avoid collision after fix types in other method */
                        public void visitPropertyReference2(IrPropertyReference irPropertyReference, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitPropertyReference(this, irPropertyReference, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitRawFunctionReference */
                        public /* bridge */ /* synthetic */ Unit visitRawFunctionReference2(IrRawFunctionReference irRawFunctionReference, IrDeclaration irDeclaration) {
                            visitRawFunctionReference2(irRawFunctionReference, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitRawFunctionReference, reason: avoid collision after fix types in other method */
                        public void visitRawFunctionReference2(IrRawFunctionReference irRawFunctionReference, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitRawFunctionReference(this, irRawFunctionReference, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitReturn */
                        public /* bridge */ /* synthetic */ Unit visitReturn2(IrReturn irReturn, IrDeclaration irDeclaration) {
                            visitReturn2(irReturn, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitReturn, reason: avoid collision after fix types in other method */
                        public void visitReturn2(IrReturn irReturn, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitReturn(this, irReturn, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitScript */
                        public /* bridge */ /* synthetic */ Unit visitScript2(IrScript irScript, IrDeclaration irDeclaration) {
                            visitScript2(irScript, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitScript, reason: avoid collision after fix types in other method */
                        public void visitScript2(IrScript irScript, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitScript(this, irScript, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitSetField */
                        public /* bridge */ /* synthetic */ Unit visitSetField2(IrSetField irSetField, IrDeclaration irDeclaration) {
                            visitSetField2(irSetField, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitSetField, reason: avoid collision after fix types in other method */
                        public void visitSetField2(IrSetField irSetField, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitSetField(this, irSetField, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitSetValue */
                        public /* bridge */ /* synthetic */ Unit visitSetValue2(IrSetValue irSetValue, IrDeclaration irDeclaration) {
                            visitSetValue2(irSetValue, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitSetValue, reason: avoid collision after fix types in other method */
                        public void visitSetValue2(IrSetValue irSetValue, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitSetValue(this, irSetValue, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitSimpleFunction */
                        public /* bridge */ /* synthetic */ Unit visitSimpleFunction2(IrSimpleFunction irSimpleFunction, IrDeclaration irDeclaration) {
                            visitSimpleFunction2(irSimpleFunction, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitSimpleFunction, reason: avoid collision after fix types in other method */
                        public void visitSimpleFunction2(IrSimpleFunction irSimpleFunction, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitSimpleFunction(this, irSimpleFunction, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitSingletonReference */
                        public /* bridge */ /* synthetic */ Unit visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, IrDeclaration irDeclaration) {
                            visitSingletonReference2(irGetSingletonValue, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitSingletonReference, reason: avoid collision after fix types in other method */
                        public void visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitSingletonReference(this, irGetSingletonValue, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitSpreadElement */
                        public /* bridge */ /* synthetic */ Unit visitSpreadElement2(IrSpreadElement irSpreadElement, IrDeclaration irDeclaration) {
                            visitSpreadElement2(irSpreadElement, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitSpreadElement, reason: avoid collision after fix types in other method */
                        public void visitSpreadElement2(IrSpreadElement irSpreadElement, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitSpreadElement(this, irSpreadElement, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitStringConcatenation */
                        public /* bridge */ /* synthetic */ Unit visitStringConcatenation2(IrStringConcatenation irStringConcatenation, IrDeclaration irDeclaration) {
                            visitStringConcatenation2(irStringConcatenation, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitStringConcatenation, reason: avoid collision after fix types in other method */
                        public void visitStringConcatenation2(IrStringConcatenation irStringConcatenation, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitStringConcatenation(this, irStringConcatenation, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitSuspendableExpression */
                        public /* bridge */ /* synthetic */ Unit visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, IrDeclaration irDeclaration) {
                            visitSuspendableExpression2(irSuspendableExpression, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitSuspendableExpression, reason: avoid collision after fix types in other method */
                        public void visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitSuspendableExpression(this, irSuspendableExpression, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitSuspensionPoint */
                        public /* bridge */ /* synthetic */ Unit visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, IrDeclaration irDeclaration) {
                            visitSuspensionPoint2(irSuspensionPoint, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitSuspensionPoint, reason: avoid collision after fix types in other method */
                        public void visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitSuspensionPoint(this, irSuspensionPoint, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitSyntheticBody */
                        public /* bridge */ /* synthetic */ Unit visitSyntheticBody2(IrSyntheticBody irSyntheticBody, IrDeclaration irDeclaration) {
                            visitSyntheticBody2(irSyntheticBody, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitSyntheticBody, reason: avoid collision after fix types in other method */
                        public void visitSyntheticBody2(IrSyntheticBody irSyntheticBody, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitSyntheticBody(this, irSyntheticBody, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitThrow */
                        public /* bridge */ /* synthetic */ Unit visitThrow2(IrThrow irThrow, IrDeclaration irDeclaration) {
                            visitThrow2(irThrow, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitThrow, reason: avoid collision after fix types in other method */
                        public void visitThrow2(IrThrow irThrow, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitThrow(this, irThrow, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitTry */
                        public /* bridge */ /* synthetic */ Unit visitTry2(IrTry irTry, IrDeclaration irDeclaration) {
                            visitTry2(irTry, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitTry, reason: avoid collision after fix types in other method */
                        public void visitTry2(IrTry irTry, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitTry(this, irTry, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitTypeAlias */
                        public /* bridge */ /* synthetic */ Unit visitTypeAlias2(IrTypeAlias irTypeAlias, IrDeclaration irDeclaration) {
                            visitTypeAlias2(irTypeAlias, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitTypeAlias, reason: avoid collision after fix types in other method */
                        public void visitTypeAlias2(IrTypeAlias irTypeAlias, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitTypeAlias(this, irTypeAlias, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitTypeOperator */
                        public /* bridge */ /* synthetic */ Unit visitTypeOperator2(IrTypeOperatorCall irTypeOperatorCall, IrDeclaration irDeclaration) {
                            visitTypeOperator2(irTypeOperatorCall, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitTypeOperator, reason: avoid collision after fix types in other method */
                        public void visitTypeOperator2(IrTypeOperatorCall irTypeOperatorCall, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitTypeOperator(this, irTypeOperatorCall, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitTypeParameter */
                        public /* bridge */ /* synthetic */ Unit visitTypeParameter2(IrTypeParameter irTypeParameter, IrDeclaration irDeclaration) {
                            visitTypeParameter2(irTypeParameter, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitTypeParameter, reason: avoid collision after fix types in other method */
                        public void visitTypeParameter2(IrTypeParameter irTypeParameter, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitTypeParameter(this, irTypeParameter, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitValueAccess */
                        public /* bridge */ /* synthetic */ Unit visitValueAccess2(IrValueAccessExpression irValueAccessExpression, IrDeclaration irDeclaration) {
                            visitValueAccess2(irValueAccessExpression, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitValueAccess, reason: avoid collision after fix types in other method */
                        public void visitValueAccess2(IrValueAccessExpression irValueAccessExpression, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitValueAccess(this, irValueAccessExpression, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitValueParameter */
                        public /* bridge */ /* synthetic */ Unit visitValueParameter2(IrValueParameter irValueParameter, IrDeclaration irDeclaration) {
                            visitValueParameter2(irValueParameter, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitValueParameter, reason: avoid collision after fix types in other method */
                        public void visitValueParameter2(IrValueParameter irValueParameter, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitValueParameter(this, irValueParameter, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitVararg */
                        public /* bridge */ /* synthetic */ Unit visitVararg2(IrVararg irVararg, IrDeclaration irDeclaration) {
                            visitVararg2(irVararg, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitVararg, reason: avoid collision after fix types in other method */
                        public void visitVararg2(IrVararg irVararg, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitVararg(this, irVararg, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitVariable */
                        public /* bridge */ /* synthetic */ Unit visitVariable2(IrVariable irVariable, IrDeclaration irDeclaration) {
                            visitVariable2(irVariable, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitWhen */
                        public /* bridge */ /* synthetic */ Unit visitWhen2(IrWhen irWhen, IrDeclaration irDeclaration) {
                            visitWhen2(irWhen, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitWhen, reason: avoid collision after fix types in other method */
                        public void visitWhen2(IrWhen irWhen, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitWhen(this, irWhen, irDeclaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                        /* JADX INFO: renamed from: visitWhileLoop */
                        public /* bridge */ /* synthetic */ Unit visitWhileLoop2(IrWhileLoop irWhileLoop, IrDeclaration irDeclaration) {
                            visitWhileLoop2(irWhileLoop, irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: visitWhileLoop, reason: avoid collision after fix types in other method */
                        public void visitWhileLoop2(IrWhileLoop irWhileLoop, IrDeclaration irDeclaration) {
                            IrElementVisitor.DefaultImpls.visitWhileLoop(this, irWhileLoop, irDeclaration);
                        }

                        /* JADX INFO: renamed from: visitElement, reason: avoid collision after fix types in other method */
                        public void visitElement2(IrElement element, IrDeclaration data2) {
                            Intrinsics.checkNotNullParameter(element, "element");
                            element.acceptChildren(this, data2);
                        }

                        /* JADX INFO: renamed from: visitDeclaration, reason: avoid collision after fix types in other method */
                        public void visitDeclaration2(IrDeclarationBase declaration, IrDeclaration data2) {
                            Intrinsics.checkNotNullParameter(declaration, "declaration");
                            this.this$0.getP().println(new Object[0]);
                            this.this$0.getP().pushIndent();
                            declaration.accept(this.this$0, data2);
                            this.this$0.getP().popIndent();
                        }

                        /* JADX INFO: renamed from: visitVariable, reason: avoid collision after fix types in other method */
                        public void visitVariable2(IrVariable declaration, IrDeclaration data2) {
                            Intrinsics.checkNotNullParameter(declaration, "declaration");
                            declaration.acceptChildren(this, data2);
                        }

                        /* JADX INFO: renamed from: visitLocalDelegatedProperty, reason: avoid collision after fix types in other method */
                        public void visitLocalDelegatedProperty2(IrLocalDelegatedProperty declaration, IrDeclaration data2) {
                            Intrinsics.checkNotNullParameter(declaration, "declaration");
                            declaration.acceptChildren(this, data2);
                        }
                    }, data);
                } else if (i == 3) {
                    actuallyPrint.invoke();
                }
            } finally {
                this.options.getCustomDumpStrategy().didPrintElement(body2, data, this.p);
            }
        }
    }

    /* JADX INFO: renamed from: visitComposite, reason: avoid collision after fix types in other method */
    public void visitComposite2(IrComposite expression, IrDeclaration data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrComposite irComposite = expression;
        if (this.options.getCustomDumpStrategy().willPrintElement(irComposite, data, this.p)) {
            try {
                printStatementContainer(expression, "// COMPOSITE {", "// }", data, false);
            } finally {
                this.options.getCustomDumpStrategy().didPrintElement(irComposite, data, this.p);
            }
        }
    }

    /* JADX INFO: renamed from: visitBlock, reason: avoid collision after fix types in other method */
    public void visitBlock2(IrBlock expression, IrDeclaration data) {
        String str;
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrBlock irBlock = expression;
        if (this.options.getCustomDumpStrategy().willPrintElement(irBlock, data, this.p)) {
            try {
                if (expression instanceof IrReturnableBlock) {
                    str = "RETURNABLE BLOCK";
                } else {
                    str = expression instanceof IrInlinedFunctionBlock ? "INLINED FUNCTION BLOCK" : "BLOCK";
                }
                printStatementContainer$default(this, expression, "{ // ".concat(str), "}", data, false, 8, null);
            } finally {
                this.options.getCustomDumpStrategy().didPrintElement(irBlock, data, this.p);
            }
        }
    }

    /* JADX INFO: renamed from: visitCall, reason: avoid collision after fix types in other method */
    public void visitCall2(IrCall expression, IrDeclaration data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrCall irCall = expression;
        if (this.options.getCustomDumpStrategy().willPrintElement(irCall, data, this.p)) {
            try {
                printMemberAccessExpressionWithNoIndent$default(this, expression, getSafeName(expression.getSymbol()), getSafeValueParameters(expression.getSymbol()), expression.getSuperQualifierSymbol(), false, data, null, false, false, 224, null);
            } finally {
                this.options.getCustomDumpStrategy().didPrintElement(irCall, data, this.p);
            }
        }
    }

    /* JADX INFO: renamed from: visitConstructorCall, reason: avoid collision after fix types in other method */
    public void visitConstructorCall2(IrConstructorCall expression, IrDeclaration data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrConstructorCall irConstructorCall = expression;
        if (this.options.getCustomDumpStrategy().willPrintElement(irConstructorCall, data, this.p)) {
            try {
                IrConstructorCall irConstructorCall2 = expression;
                String safeParentClassName = getSafeParentClassName(expression.getSymbol());
                List<IrValueParameter> safeValueParameters = getSafeValueParameters(expression.getSymbol());
                IrClass safeParentClassOrNull = getSafeParentClassOrNull(expression.getSymbol());
                printMemberAccessExpressionWithNoIndent$default(this, irConstructorCall2, safeParentClassName, safeValueParameters, null, safeParentClassOrNull != null && IrUtilsKt.isAnnotationClass(safeParentClassOrNull), data, null, false, false, 224, null);
            } finally {
                this.options.getCustomDumpStrategy().didPrintElement(irConstructorCall, data, this.p);
            }
        }
    }

    /* JADX INFO: renamed from: visitDelegatingConstructorCall, reason: avoid collision after fix types in other method */
    public void visitDelegatingConstructorCall2(IrDelegatingConstructorCall expression, IrDeclaration data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrDelegatingConstructorCall irDelegatingConstructorCall = expression;
        if (this.options.getCustomDumpStrategy().willPrintElement(irDelegatingConstructorCall, data, this.p)) {
            try {
                printConstructorCallWithNoIndent(expression, data);
            } finally {
                this.options.getCustomDumpStrategy().didPrintElement(irDelegatingConstructorCall, data, this.p);
            }
        }
    }

    /* JADX INFO: renamed from: visitEnumConstructorCall, reason: avoid collision after fix types in other method */
    public void visitEnumConstructorCall2(IrEnumConstructorCall expression, IrDeclaration data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrEnumConstructorCall irEnumConstructorCall = expression;
        if (this.options.getCustomDumpStrategy().willPrintElement(irEnumConstructorCall, data, this.p)) {
            try {
                printConstructorCallWithNoIndent(expression, data);
            } finally {
                this.options.getCustomDumpStrategy().didPrintElement(irEnumConstructorCall, data, this.p);
            }
        }
    }

    /* JADX INFO: renamed from: visitInstanceInitializerCall, reason: avoid collision after fix types in other method */
    public void visitInstanceInitializerCall2(IrInstanceInitializerCall expression, IrDeclaration data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrInstanceInitializerCall irInstanceInitializerCall = expression;
        if (this.options.getCustomDumpStrategy().willPrintElement(irInstanceInitializerCall, data, this.p)) {
            try {
                this.p.printlnWithNoIndent("/* <init>() */");
            } finally {
                this.options.getCustomDumpStrategy().didPrintElement(irInstanceInitializerCall, data, this.p);
            }
        }
    }

    /* JADX INFO: renamed from: visitGetField, reason: avoid collision after fix types in other method */
    public void visitGetField2(IrGetField expression, IrDeclaration data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrGetField irGetField = expression;
        if (this.options.getCustomDumpStrategy().willPrintElement(irGetField, data, this.p)) {
            try {
                printFieldAccess(expression, data);
            } finally {
                this.options.getCustomDumpStrategy().didPrintElement(irGetField, data, this.p);
            }
        }
    }

    /* JADX INFO: renamed from: visitSetField, reason: avoid collision after fix types in other method */
    public void visitSetField2(IrSetField expression, IrDeclaration data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrSetField irSetField = expression;
        if (this.options.getCustomDumpStrategy().willPrintElement(irSetField, data, this.p)) {
            try {
                printFieldAccess(expression, data);
                this.p.printWithNoIndent(" = ");
                expression.getValue().accept(this, data);
            } finally {
                this.options.getCustomDumpStrategy().didPrintElement(irSetField, data, this.p);
            }
        }
    }

    /* JADX INFO: renamed from: visitGetValue, reason: avoid collision after fix types in other method */
    public void visitGetValue2(IrGetValue expression, IrDeclaration data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrGetValue irGetValue = expression;
        if (this.options.getCustomDumpStrategy().willPrintElement(irGetValue, data, this.p)) {
            try {
                this.p.printWithNoIndent(getSafeName(expression.getSymbol()));
            } finally {
                this.options.getCustomDumpStrategy().didPrintElement(irGetValue, data, this.p);
            }
        }
    }

    /* JADX INFO: renamed from: visitSetValue, reason: avoid collision after fix types in other method */
    public void visitSetValue2(IrSetValue expression, IrDeclaration data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrSetValue irSetValue = expression;
        if (this.options.getCustomDumpStrategy().willPrintElement(irSetValue, data, this.p)) {
            try {
                this.p.printWithNoIndent(getSafeName(expression.getSymbol()) + " = ");
                expression.getValue().accept(this, data);
            } finally {
                this.options.getCustomDumpStrategy().didPrintElement(irSetValue, data, this.p);
            }
        }
    }

    /* JADX INFO: renamed from: visitGetObjectValue, reason: avoid collision after fix types in other method */
    public void visitGetObjectValue2(IrGetObjectValue expression, IrDeclaration data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrGetObjectValue irGetObjectValue = expression;
        if (this.options.getCustomDumpStrategy().willPrintElement(irGetObjectValue, data, this.p)) {
            try {
                printTypeWithNoIndent(IrTypesKt.getDefaultType(expression.getSymbol()));
            } finally {
                this.options.getCustomDumpStrategy().didPrintElement(irGetObjectValue, data, this.p);
            }
        }
    }

    /* JADX INFO: renamed from: visitGetEnumValue, reason: avoid collision after fix types in other method */
    public void visitGetEnumValue2(IrGetEnumValue expression, IrDeclaration data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrGetEnumValue irGetEnumValue = expression;
        if (this.options.getCustomDumpStrategy().willPrintElement(irGetEnumValue, data, this.p)) {
            try {
                this.p.printWithNoIndent(getSafeParentClassName(expression.getSymbol()));
                this.p.printWithNoIndent(".");
                this.p.printWithNoIndent(getSafeName(expression.getSymbol()));
            } finally {
                this.options.getCustomDumpStrategy().didPrintElement(irGetEnumValue, data, this.p);
            }
        }
    }

    /* JADX INFO: renamed from: visitRawFunctionReference, reason: avoid collision after fix types in other method */
    public void visitRawFunctionReference2(IrRawFunctionReference expression, IrDeclaration data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrRawFunctionReference irRawFunctionReference = expression;
        if (this.options.getCustomDumpStrategy().willPrintElement(irRawFunctionReference, data, this.p)) {
            try {
                this.p.printWithNoIndent(ContainerUtils.FIELD_DELIMITER);
                IrElementVisitor.DefaultImpls.visitRawFunctionReference(this, expression, data);
            } finally {
                this.options.getCustomDumpStrategy().didPrintElement(irRawFunctionReference, data, this.p);
            }
        }
    }

    /* JADX INFO: renamed from: visitReturn, reason: avoid collision after fix types in other method */
    public void visitReturn2(IrReturn expression, IrDeclaration data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrReturn irReturn = expression;
        if (this.options.getCustomDumpStrategy().willPrintElement(irReturn, data, this.p)) {
            try {
                this.p.printWithNoIndent("return ");
                expression.getValue().accept(this, data);
            } finally {
                this.options.getCustomDumpStrategy().didPrintElement(irReturn, data, this.p);
            }
        }
    }

    /* JADX INFO: renamed from: visitStringConcatenation, reason: avoid collision after fix types in other method */
    public void visitStringConcatenation2(IrStringConcatenation expression, IrDeclaration data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrStringConcatenation irStringConcatenation = expression;
        if (this.options.getCustomDumpStrategy().willPrintElement(irStringConcatenation, data, this.p)) {
            try {
                int i = 0;
                for (Object obj : expression.getArguments()) {
                    int i2 = i + 1;
                    if (i < 0) {
                        CollectionsKt.throwIndexOverflow();
                    }
                    IrExpression irExpression = (IrExpression) obj;
                    p(i > 0, " +");
                    irExpression.accept(this, data);
                    i = i2;
                }
            } finally {
                this.options.getCustomDumpStrategy().didPrintElement(irStringConcatenation, data, this.p);
            }
        }
    }

    /* JADX INFO: renamed from: visitConst, reason: avoid collision after fix types in other method */
    public void visitConst2(IrConst<?> expression, IrDeclaration data) {
        Pair pair;
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrConst<?> irConst = expression;
        if (this.options.getCustomDumpStrategy().willPrintElement(irConst, data, this.p)) {
            try {
                IrConstKind<?> kind = expression.getKind();
                if ((kind instanceof IrConstKind.Null) || (kind instanceof IrConstKind.Boolean)) {
                    pair = TuplesKt.to("", "");
                } else if (kind instanceof IrConstKind.Char) {
                    pair = TuplesKt.to(FormatHelper.QUOTE, FormatHelper.QUOTE);
                } else if (kind instanceof IrConstKind.Byte) {
                    pair = TuplesKt.to("", "B");
                } else if (kind instanceof IrConstKind.Short) {
                    pair = TuplesKt.to("", ExifInterface.LATITUDE_SOUTH);
                } else if (kind instanceof IrConstKind.Int) {
                    pair = TuplesKt.to("", "");
                } else if (kind instanceof IrConstKind.Long) {
                    pair = TuplesKt.to("", "L");
                } else if (kind instanceof IrConstKind.String) {
                    pair = TuplesKt.to("\"", "\"");
                } else if (kind instanceof IrConstKind.Float) {
                    pair = TuplesKt.to("", "F");
                } else {
                    if (!(kind instanceof IrConstKind.Double)) {
                        throw new NoWhenBranchMatchedException();
                    }
                    pair = TuplesKt.to("", "");
                }
                String str = (String) pair.component1();
                String str2 = (String) pair.component2();
                String strValueOf = String.valueOf(expression.getValue());
                if (kind instanceof IrConstKind.Char) {
                    strValueOf = org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil.escapeCharCharacters(strValueOf);
                } else if (kind instanceof IrConstKind.String) {
                    strValueOf = org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil.escapeStringCharacters(strValueOf);
                }
                Intrinsics.checkNotNull(strValueOf);
                this.p.printWithNoIndent(str, strValueOf, str2);
                this.options.getCustomDumpStrategy().didPrintElement(irConst, data, this.p);
            } catch (Throwable th) {
                this.options.getCustomDumpStrategy().didPrintElement(irConst, data, this.p);
                throw th;
            }
        }
    }

    /* JADX INFO: renamed from: visitVararg, reason: avoid collision after fix types in other method */
    public void visitVararg2(IrVararg expression, IrDeclaration data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrVararg irVararg = expression;
        if (this.options.getCustomDumpStrategy().willPrintElement(irVararg, data, this.p)) {
            try {
                this.p.printWithNoIndent("[");
                int i = 0;
                for (Object obj : expression.getElements()) {
                    int i2 = i + 1;
                    if (i < 0) {
                        CollectionsKt.throwIndexOverflow();
                    }
                    IrVarargElement irVarargElement = (IrVarargElement) obj;
                    p(i > 0, ",");
                    irVarargElement.accept(this, data);
                    i = i2;
                }
                this.p.printWithNoIndent("]");
            } finally {
                this.options.getCustomDumpStrategy().didPrintElement(irVararg, data, this.p);
            }
        }
    }

    /* JADX INFO: renamed from: visitSpreadElement, reason: avoid collision after fix types in other method */
    public void visitSpreadElement2(IrSpreadElement spread, IrDeclaration data) {
        Intrinsics.checkNotNullParameter(spread, "spread");
        IrSpreadElement irSpreadElement = spread;
        if (this.options.getCustomDumpStrategy().willPrintElement(irSpreadElement, data, this.p)) {
            try {
                this.p.printWithNoIndent("*");
                spread.getExpression().accept(this, data);
            } finally {
                this.options.getCustomDumpStrategy().didPrintElement(irSpreadElement, data, this.p);
            }
        }
    }

    /* JADX INFO: renamed from: visitTypeOperator, reason: avoid collision after fix types in other method */
    public void visitTypeOperator2(IrTypeOperatorCall expression, IrDeclaration data) {
        Pair pair;
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrTypeOperatorCall irTypeOperatorCall = expression;
        if (this.options.getCustomDumpStrategy().willPrintElement(irTypeOperatorCall, data, this.p)) {
            try {
                switch (WhenMappings.$EnumSwitchMapping$1[expression.getOperator().ordinal()]) {
                    case 1:
                        pair = TuplesKt.to("as", "");
                        break;
                    case 2:
                        pair = TuplesKt.to("/*as", " */");
                        break;
                    case 3:
                        pair = TuplesKt.to("/*!!", " */");
                        break;
                    case 4:
                        pair = TuplesKt.to("/*~>", " */");
                        break;
                    case 5:
                        pair = TuplesKt.to("as?", "");
                        break;
                    case 6:
                        pair = TuplesKt.to(LanguageCodeUtil.IS, "");
                        break;
                    case 7:
                        pair = TuplesKt.to("!is", "");
                        break;
                    case 8:
                        pair = TuplesKt.to("/*~>", " */");
                        break;
                    case 9:
                        pair = TuplesKt.to("/*->", " */");
                        break;
                    case 10:
                        pair = TuplesKt.to("/*~>", " */");
                        break;
                    case 11:
                        pair = TuplesKt.to("/*=>", " */");
                        break;
                    default:
                        throw new NoWhenBranchMatchedException();
                }
                String str = (String) pair.component1();
                String str2 = (String) pair.component2();
                expression.getArgument().accept(this, data);
                this.p.printWithNoIndent(" " + str + StringUtil.SPACE);
                printTypeWithNoIndent(expression.getTypeOperand());
                this.p.printWithNoIndent(str2);
                this.options.getCustomDumpStrategy().didPrintElement(irTypeOperatorCall, data, this.p);
            } catch (Throwable th) {
                this.options.getCustomDumpStrategy().didPrintElement(irTypeOperatorCall, data, this.p);
                throw th;
            }
        }
    }

    /* JADX INFO: renamed from: visitWhen, reason: avoid collision after fix types in other method */
    public void visitWhen2(IrWhen expression, IrDeclaration data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrWhen irWhen = expression;
        if (this.options.getCustomDumpStrategy().willPrintElement(irWhen, data, this.p)) {
            try {
                this.p.printlnWithNoIndent("when {");
                this.p.pushIndent();
                Iterator<T> it = expression.getBranches().iterator();
                while (it.hasNext()) {
                    ((IrBranch) it.next()).accept(this, data);
                }
                this.p.popIndent();
                this.p.print("}");
            } finally {
                this.options.getCustomDumpStrategy().didPrintElement(irWhen, data, this.p);
            }
        }
    }

    /* JADX INFO: renamed from: visitBranch, reason: avoid collision after fix types in other method */
    public void visitBranch2(IrBranch branch, IrDeclaration data) {
        Intrinsics.checkNotNullParameter(branch, "branch");
        IrBranch irBranch = branch;
        if (this.options.getCustomDumpStrategy().willPrintElement(irBranch, data, this.p)) {
            try {
                this.p.printIndent();
                branch.getCondition().accept(this, data);
                this.p.printWithNoIndent(" -> ");
                branch.getResult().accept(this, data);
                this.p.println(new Object[0]);
            } finally {
                this.options.getCustomDumpStrategy().didPrintElement(irBranch, data, this.p);
            }
        }
    }

    /* JADX INFO: renamed from: visitElseBranch, reason: avoid collision after fix types in other method */
    public void visitElseBranch2(IrElseBranch branch, IrDeclaration data) {
        Intrinsics.checkNotNullParameter(branch, "branch");
        IrElseBranch irElseBranch = branch;
        if (this.options.getCustomDumpStrategy().willPrintElement(irElseBranch, data, this.p)) {
            try {
                this.p.printIndent();
                IrExpression condition = branch.getCondition();
                IrConst irConst = condition instanceof IrConst ? (IrConst) condition : null;
                if (irConst != null ? Intrinsics.areEqual(irConst.getValue(), (Object) true) : false) {
                    this.p.printWithNoIndent(this.options.getPrintElseAsTrue() ? "true" : PsiKeyword.ELSE);
                } else {
                    this.p.printWithNoIndent("/* else */ ");
                    branch.getCondition().accept(this, data);
                }
                this.p.printWithNoIndent(" -> ");
                branch.getResult().accept(this, data);
                this.p.println(new Object[0]);
            } finally {
                this.options.getCustomDumpStrategy().didPrintElement(irElseBranch, data, this.p);
            }
        }
    }

    /* JADX INFO: renamed from: visitWhileLoop, reason: avoid collision after fix types in other method */
    public void visitWhileLoop2(IrWhileLoop loop, IrDeclaration data) {
        Intrinsics.checkNotNullParameter(loop, "loop");
        IrWhileLoop irWhileLoop = loop;
        if (this.options.getCustomDumpStrategy().willPrintElement(irWhileLoop, data, this.p)) {
            try {
                printLabel(loop);
                this.p.printWithNoIndent("while (");
                loop.getCondition().accept(this, data);
                this.p.printWithNoIndent(") ");
                IrExpression body = loop.getBody();
                if (body != null) {
                    body.accept(this, data);
                }
            } finally {
                this.options.getCustomDumpStrategy().didPrintElement(irWhileLoop, data, this.p);
            }
        }
    }

    /* JADX INFO: renamed from: visitDoWhileLoop, reason: avoid collision after fix types in other method */
    public void visitDoWhileLoop2(IrDoWhileLoop loop, IrDeclaration data) {
        Intrinsics.checkNotNullParameter(loop, "loop");
        IrDoWhileLoop irDoWhileLoop = loop;
        if (this.options.getCustomDumpStrategy().willPrintElement(irDoWhileLoop, data, this.p)) {
            try {
                printLabel(loop);
                this.p.printWithNoIndent(PsiKeyword.DO);
                IrExpression body = loop.getBody();
                if (body != null) {
                    body.accept(this, data);
                }
                this.p.print("while (");
                loop.getCondition().accept(this, data);
                this.p.printWithNoIndent(Constant.AFTER_QUTO);
            } finally {
                this.options.getCustomDumpStrategy().didPrintElement(irDoWhileLoop, data, this.p);
            }
        }
    }

    /* JADX INFO: renamed from: visitBreakContinue, reason: avoid collision after fix types in other method */
    public void visitBreakContinue2(IrBreakContinue jump, IrDeclaration data) {
        Intrinsics.checkNotNullParameter(jump, "jump");
        IrBreakContinue irBreakContinue = jump;
        if (this.options.getCustomDumpStrategy().willPrintElement(irBreakContinue, data, this.p)) {
            try {
                this.p.printWithNoIndent(jump instanceof IrContinue ? PsiKeyword.CONTINUE : PsiKeyword.BREAK);
                String label = jump.getLabel();
                if (label != null) {
                    this.p.printWithNoIndent(PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT);
                    this.p.printWithNoIndent(label);
                }
            } finally {
                this.options.getCustomDumpStrategy().didPrintElement(irBreakContinue, data, this.p);
            }
        }
    }

    /* JADX INFO: renamed from: visitTry, reason: avoid collision after fix types in other method */
    public void visitTry2(IrTry aTry, IrDeclaration data) {
        Intrinsics.checkNotNullParameter(aTry, "aTry");
        IrTry irTry = aTry;
        if (this.options.getCustomDumpStrategy().willPrintElement(irTry, data, this.p)) {
            try {
                this.p.printWithNoIndent("try ");
                aTry.getTryResult().accept(this, data);
                this.p.printlnWithNoIndent(new Object[0]);
                Iterator<T> it = aTry.getCatches().iterator();
                while (it.hasNext()) {
                    ((IrCatch) it.next()).accept(this, data);
                }
                IrExpression finallyExpression = aTry.getFinallyExpression();
                if (finallyExpression != null) {
                    this.p.print("finally ");
                    finallyExpression.accept(this, data);
                }
            } finally {
                this.options.getCustomDumpStrategy().didPrintElement(irTry, data, this.p);
            }
        }
    }

    /* JADX INFO: renamed from: visitCatch, reason: avoid collision after fix types in other method */
    public void visitCatch2(IrCatch aCatch, IrDeclaration data) {
        Intrinsics.checkNotNullParameter(aCatch, "aCatch");
        IrCatch irCatch = aCatch;
        if (this.options.getCustomDumpStrategy().willPrintElement(irCatch, data, this.p)) {
            try {
                this.p.print("catch (");
                IrVariable catchParameter = aCatch.getCatchParameter();
                this.p.printWithNoIndent(catchParameter.getName().asString());
                this.p.printWithNoIndent(": ");
                printTypeWithNoIndent(catchParameter.getType());
                this.p.printWithNoIndent(Constant.AFTER_QUTO);
                aCatch.getResult().accept(this, data);
                this.p.printlnWithNoIndent(new Object[0]);
            } finally {
                this.options.getCustomDumpStrategy().didPrintElement(irCatch, data, this.p);
            }
        }
    }

    /* JADX INFO: renamed from: visitGetClass, reason: avoid collision after fix types in other method */
    public void visitGetClass2(IrGetClass expression, IrDeclaration data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrGetClass irGetClass = expression;
        if (this.options.getCustomDumpStrategy().willPrintElement(irGetClass, data, this.p)) {
            try {
                expression.getArgument().accept(this, data);
                this.p.printWithNoIndent("::class");
            } finally {
                this.options.getCustomDumpStrategy().didPrintElement(irGetClass, data, this.p);
            }
        }
    }

    /* JADX INFO: renamed from: visitClassReference, reason: avoid collision after fix types in other method */
    public void visitClassReference2(IrClassReference expression, IrDeclaration data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrClassReference irClassReference = expression;
        if (this.options.getCustomDumpStrategy().willPrintElement(irClassReference, data, this.p)) {
            try {
                this.p.printWithNoIndent(getSafeName(expression.getSymbol()));
                this.p.printWithNoIndent("::class");
            } finally {
                this.options.getCustomDumpStrategy().didPrintElement(irClassReference, data, this.p);
            }
        }
    }

    /* JADX INFO: renamed from: visitFunctionReference, reason: avoid collision after fix types in other method */
    public void visitFunctionReference2(IrFunctionReference expression, IrDeclaration data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrFunctionReference irFunctionReference = expression;
        if (this.options.getCustomDumpStrategy().willPrintElement(irFunctionReference, data, this.p)) {
            try {
                printCallableReferenceWithNoIndent(expression, getSafeValueParameters(expression.getSymbol()), data);
            } finally {
                this.options.getCustomDumpStrategy().didPrintElement(irFunctionReference, data, this.p);
            }
        }
    }

    /* JADX INFO: renamed from: visitPropertyReference, reason: avoid collision after fix types in other method */
    public void visitPropertyReference2(IrPropertyReference expression, IrDeclaration data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrPropertyReference irPropertyReference = expression;
        if (this.options.getCustomDumpStrategy().willPrintElement(irPropertyReference, data, this.p)) {
            try {
                printCallableReferenceWithNoIndent(expression, CollectionsKt.emptyList(), data);
            } finally {
                this.options.getCustomDumpStrategy().didPrintElement(irPropertyReference, data, this.p);
            }
        }
    }

    /* JADX INFO: renamed from: visitLocalDelegatedPropertyReference, reason: avoid collision after fix types in other method */
    public void visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference expression, IrDeclaration data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference = expression;
        if (this.options.getCustomDumpStrategy().willPrintElement(irLocalDelegatedPropertyReference, data, this.p)) {
            try {
                printCallableReferenceWithNoIndent(expression, CollectionsKt.emptyList(), data);
            } finally {
                this.options.getCustomDumpStrategy().didPrintElement(irLocalDelegatedPropertyReference, data, this.p);
            }
        }
    }

    /* JADX INFO: renamed from: visitDynamicOperatorExpression, reason: avoid collision after fix types in other method */
    public void visitDynamicOperatorExpression2(IrDynamicOperatorExpression expression, IrDeclaration data) {
        Pair pair;
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrDynamicOperatorExpression irDynamicOperatorExpression = expression;
        if (this.options.getCustomDumpStrategy().willPrintElement(irDynamicOperatorExpression, data, this.p)) {
            try {
                IrDynamicOperator operator = expression.getOperator();
                switch (WhenMappings.$EnumSwitchMapping$2[operator.ordinal()]) {
                    case 1:
                        pair = TuplesKt.to("[", "]");
                        break;
                    case 2:
                        pair = TuplesKt.to("(", Constant.AFTER_QUTO);
                        break;
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                        this.p.printWithNoIndent(operator.getImage());
                        pair = TuplesKt.to("", "");
                        break;
                    case 8:
                    case 9:
                        pair = TuplesKt.to(operator.getImage(), "");
                        break;
                    default:
                        pair = TuplesKt.to(" " + operator.getImage() + StringUtil.SPACE, "");
                        break;
                }
                expression.getReceiver().accept(this, data);
                this.p.printWithNoIndent(pair.getFirst());
                int i = 0;
                for (Object obj : expression.getArguments()) {
                    int i2 = i + 1;
                    if (i < 0) {
                        CollectionsKt.throwIndexOverflow();
                    }
                    IrExpression irExpression = (IrExpression) obj;
                    p(i > 0, ",");
                    irExpression.accept(this, data);
                    i = i2;
                }
                this.p.printWithNoIndent(pair.getSecond());
            } finally {
                this.options.getCustomDumpStrategy().didPrintElement(irDynamicOperatorExpression, data, this.p);
            }
        }
    }

    /* JADX INFO: renamed from: visitDynamicMemberExpression, reason: avoid collision after fix types in other method */
    public void visitDynamicMemberExpression2(IrDynamicMemberExpression expression, IrDeclaration data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrDynamicMemberExpression irDynamicMemberExpression = expression;
        if (this.options.getCustomDumpStrategy().willPrintElement(irDynamicMemberExpression, data, this.p)) {
            try {
                expression.getReceiver().accept(this, data);
                this.p.printWithNoIndent(".");
                this.p.printWithNoIndent(expression.getMemberName());
            } finally {
                this.options.getCustomDumpStrategy().didPrintElement(irDynamicMemberExpression, data, this.p);
            }
        }
    }

    /* JADX INFO: renamed from: visitErrorDeclaration, reason: avoid collision after fix types in other method */
    public void visitErrorDeclaration2(IrErrorDeclaration declaration, IrDeclaration data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrErrorDeclaration irErrorDeclaration = declaration;
        if (this.options.getCustomDumpStrategy().willPrintElement(irErrorDeclaration, data, this.p)) {
            try {
                this.p.println("/* ErrorDeclaration */");
            } finally {
                this.options.getCustomDumpStrategy().didPrintElement(irErrorDeclaration, data, this.p);
            }
        }
    }

    /* JADX INFO: renamed from: visitErrorExpression, reason: avoid collision after fix types in other method */
    public void visitErrorExpression2(IrErrorExpression expression, IrDeclaration data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrErrorExpression irErrorExpression = expression;
        if (this.options.getCustomDumpStrategy().willPrintElement(irErrorExpression, data, this.p)) {
            try {
                this.p.printWithNoIndent("error(\"\") /* ErrorExpression */");
            } finally {
                this.options.getCustomDumpStrategy().didPrintElement(irErrorExpression, data, this.p);
            }
        }
    }

    /* JADX INFO: renamed from: visitErrorCallExpression, reason: avoid collision after fix types in other method */
    public void visitErrorCallExpression2(IrErrorCallExpression expression, IrDeclaration data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrErrorCallExpression irErrorCallExpression = expression;
        if (this.options.getCustomDumpStrategy().willPrintElement(irErrorCallExpression, data, this.p)) {
            try {
                this.p.printWithNoIndent("error(\"\") /* ErrorCallExpression */");
                IrExpression explicitReceiver = expression.getExplicitReceiver();
                if (explicitReceiver != null) {
                    explicitReceiver.accept(this, data);
                    this.p.printWithNoIndent("; ");
                }
                Iterator<T> it = expression.getArguments().iterator();
                while (it.hasNext()) {
                    ((IrExpression) it.next()).accept(this, data);
                    this.p.printWithNoIndent("; ");
                }
            } finally {
                this.options.getCustomDumpStrategy().didPrintElement(irErrorCallExpression, data, this.p);
            }
        }
    }
}
