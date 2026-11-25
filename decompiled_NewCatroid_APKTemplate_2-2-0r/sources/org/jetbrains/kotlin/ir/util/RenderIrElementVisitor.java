package org.jetbrains.kotlin.ir.util;

import androidx.exifinterface.media.ExifInterface;
import com.huawei.hms.ml.language.common.utils.Constant;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import name.antonsmirnov.firmata.FormatHelper;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationBase;
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
import org.jetbrains.kotlin.ir.declarations.IrPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrScript;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
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
import org.jetbrains.kotlin.ir.expressions.IrStringConcatenation;
import org.jetbrains.kotlin.ir.expressions.IrSuspendableExpression;
import org.jetbrains.kotlin.ir.expressions.IrSuspensionPoint;
import org.jetbrains.kotlin.ir.expressions.IrSyntheticBody;
import org.jetbrains.kotlin.ir.expressions.IrThrow;
import org.jetbrains.kotlin.ir.expressions.IrTry;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperatorCall;
import org.jetbrains.kotlin.ir.expressions.IrValueAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrVararg;
import org.jetbrains.kotlin.ir.expressions.IrWhen;
import org.jetbrains.kotlin.ir.expressions.IrWhileLoop;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitor;
import org.jetbrains.kotlin.renderer.DescriptorRenderer;
import org.jetbrains.kotlin.util.capitalizeDecapitalize.CapitalizeDecapitalizeKt;
import org.jetbrains.kotlin.utils.CollectionsKt;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: RenderIrElement.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000ü\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u0001:\u0002³\u0001B\u000f\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u000e\u0010\u000b\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\rJ\u0012\u0010\u000e\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\u000e\u0010\u0011\u001a\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u0013J\u000e\u0010\u0014\u001a\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u0016J\u001a\u0010\u0017\u001a\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010\u001b\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u00020\u001c2\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010\u001d\u001a\u00020\u00022\u0006\u0010\u001e\u001a\u00020\u001f2\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010 \u001a\u00020\u00022\u0006\u0010!\u001a\u00020\"2\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010#\u001a\u00020\u00022\u0006\u0010$\u001a\u00020%2\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010&\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u00020'2\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010(\u001a\u00020\u00022\u0006\u0010)\u001a\u00020*2\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010+\u001a\u00020\u00022\u0006\u0010\u0018\u001a\u00020,2\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010-\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u00020.2\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010/\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u0002002\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u0016J\u001e\u00101\u001a\u00020\u00022\n\u0010\u000f\u001a\u0006\u0012\u0002\b\u0003022\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u00103\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u0002042\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u00105\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u0002062\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u00107\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u0002082\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u00109\u001a\u00020\u00022\u0006\u0010\u0018\u001a\u00020:2\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010;\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u00020\r2\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010<\u001a\u00020\u00022\u0006\u0010$\u001a\u00020=2\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010>\u001a\u00020\u00022\u0006\u0010\u0018\u001a\u00020?2\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010@\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u00020A2\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010B\u001a\u00020\u00022\u0006\u0010C\u001a\u00020D2\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010E\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u00020F2\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010G\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u00020H2\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010I\u001a\u00020\u00022\u0006\u0010J\u001a\u00020K2\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010L\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u00020M2\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010N\u001a\u00020\u00022\u0006\u0010\u0018\u001a\u00020O2\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010P\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u00020Q2\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010R\u001a\u00020\u00022\u0006\u0010\u0018\u001a\u00020S2\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010T\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u00020U2\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010V\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u00020W2\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010X\u001a\u00020\u00022\u0006\u0010\u001e\u001a\u00020Y2\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010Z\u001a\u00020\u00022\u0006\u0010\u0018\u001a\u00020[2\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010\\\u001a\u00020\u00022\u0006\u0010\u0018\u001a\u00020]2\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010^\u001a\u00020\u00022\u0006\u0010\u0018\u001a\u00020_2\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010`\u001a\u00020\u00022\u0006\u0010\u0018\u001a\u00020a2\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010b\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u00020c2\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010d\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u00020\u00102\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010e\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u00020f2\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010g\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u00020h2\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010i\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u00020j2\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010k\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u00020l2\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010m\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u00020n2\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010o\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u00020p2\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010q\u001a\u00020\u00022\u0006\u0010\u0018\u001a\u00020r2\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010s\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u00020t2\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010u\u001a\u00020\u00022\u0006\u0010\u0018\u001a\u00020v2\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010w\u001a\u00020\u00022\u0006\u0010\u0018\u001a\u00020x2\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010y\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u00020z2\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010{\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u00020|2\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010}\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u00020~2\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u0016J\u001b\u0010\u007f\u001a\u00020\u00022\u0007\u0010\u0018\u001a\u00030\u0080\u00012\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u0016J\u001c\u0010\u0081\u0001\u001a\u00020\u00022\u0007\u0010\u000f\u001a\u00030\u0082\u00012\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u0016J\u001c\u0010\u0083\u0001\u001a\u00020\u00022\u0007\u0010\u000f\u001a\u00030\u0084\u00012\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u0016J\u001c\u0010\u0085\u0001\u001a\u00020\u00022\u0007\u0010\u0018\u001a\u00030\u0086\u00012\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u0016J\u001d\u0010\u0087\u0001\u001a\u00020\u00022\b\u0010\u0088\u0001\u001a\u00030\u0089\u00012\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u0016J\u001c\u0010\u008a\u0001\u001a\u00020\u00022\u0007\u0010\u000f\u001a\u00030\u008b\u00012\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u0016J\u001c\u0010\u008c\u0001\u001a\u00020\u00022\u0007\u0010\u001e\u001a\u00030\u008d\u00012\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u0016J\u001c\u0010\u008e\u0001\u001a\u00020\u00022\u0007\u0010\u000f\u001a\u00030\u008f\u00012\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u0016J\u001d\u0010\u0090\u0001\u001a\u00020\u00022\b\u0010\u0091\u0001\u001a\u00030\u0092\u00012\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u0016J\u001c\u0010\u0093\u0001\u001a\u00020\u00022\u0007\u0010\u0018\u001a\u00030\u0094\u00012\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u0016J\u001c\u0010\u0095\u0001\u001a\u00020\u00022\u0007\u0010\u000f\u001a\u00030\u0096\u00012\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u0016J\u001c\u0010\u0097\u0001\u001a\u00020\u00022\u0007\u0010\u0018\u001a\u00030\u0098\u00012\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u0016J\u001c\u0010\u0099\u0001\u001a\u00020\u00022\u0007\u0010\u0018\u001a\u00030\u009a\u00012\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u0016J\u001c\u0010\u009b\u0001\u001a\u00020\u00022\u0007\u0010\u000f\u001a\u00030\u009c\u00012\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u0016J\u001c\u0010\u009d\u0001\u001a\u00020\u00022\u0007\u0010\u0018\u001a\u00030\u009e\u00012\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u0016J\u001c\u0010\u009f\u0001\u001a\u00020\u00022\u0007\u0010\u000f\u001a\u00030 \u00012\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u0016J\u001c\u0010¡\u0001\u001a\u00020\u00022\u0007\u0010C\u001a\u00030¢\u00012\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u0016JS\u0010£\u0001\u001a\u00030¤\u0001\"\n\b\u0000\u0010¥\u0001*\u00030¦\u0001*\b0§\u0001j\u0003`¨\u00012\u0007\u0010©\u0001\u001a\u00020\u00022\n\u0010ª\u0001\u001a\u0005\u0018\u0001H¥\u00012\u0015\u0010«\u0001\u001a\u0010\u0012\u0005\u0012\u0003H¥\u0001\u0012\u0004\u0012\u00020\u00020¬\u0001H\u0082\b¢\u0006\u0003\u0010\u00ad\u0001J\u000f\u0010®\u0001\u001a\u00030¦\u0001*\u00030¦\u0001H\u0002J\r\u0010¯\u0001\u001a\u00020\u0002*\u00020\u0016H\u0002J\r\u0010°\u0001\u001a\u00020\u0002*\u00020\u0013H\u0002J\r\u0010±\u0001\u001a\u00020\u0002*\u00020'H\u0002J\r\u0010²\u0001\u001a\u00020\u0002*\u00020aH\u0002R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006´\u0001"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/RenderIrElementVisitor;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;", "", "", "options", "Lorg/jetbrains/kotlin/ir/util/DumpIrTreeOptions;", "(Lorg/jetbrains/kotlin/ir/util/DumpIrTreeOptions;)V", "descriptorRendererForErrorDeclarations", "Lorg/jetbrains/kotlin/renderer/DescriptorRenderer;", "variableNameData", "Lorg/jetbrains/kotlin/ir/util/VariableNameData;", "renderAsAnnotation", "irAnnotation", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "renderReflectionTarget", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;", "renderSymbolReference", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "renderType", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "visitAnonymousInitializer", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrAnonymousInitializer;", "data", "visitBlock", "Lorg/jetbrains/kotlin/ir/expressions/IrBlock;", "visitBlockBody", "body", "Lorg/jetbrains/kotlin/ir/expressions/IrBlockBody;", "visitBranch", ConfigConstants.CONFIG_BRANCH_SECTION, "Lorg/jetbrains/kotlin/ir/expressions/IrBranch;", "visitBreak", "jump", "Lorg/jetbrains/kotlin/ir/expressions/IrBreak;", "visitCall", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "visitCatch", "aCatch", "Lorg/jetbrains/kotlin/ir/expressions/IrCatch;", "visitClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "visitClassReference", "Lorg/jetbrains/kotlin/ir/expressions/IrClassReference;", "visitComposite", "Lorg/jetbrains/kotlin/ir/expressions/IrComposite;", "visitConst", "Lorg/jetbrains/kotlin/ir/expressions/IrConst;", "visitConstantArray", "Lorg/jetbrains/kotlin/ir/expressions/IrConstantArray;", "visitConstantObject", "Lorg/jetbrains/kotlin/ir/expressions/IrConstantObject;", "visitConstantPrimitive", "Lorg/jetbrains/kotlin/ir/expressions/IrConstantPrimitive;", "visitConstructor", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "visitConstructorCall", "visitContinue", "Lorg/jetbrains/kotlin/ir/expressions/IrContinue;", "visitDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationBase;", "visitDelegatingConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrDelegatingConstructorCall;", "visitDoWhileLoop", "loop", "Lorg/jetbrains/kotlin/ir/expressions/IrDoWhileLoop;", "visitDynamicMemberExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrDynamicMemberExpression;", "visitDynamicOperatorExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrDynamicOperatorExpression;", "visitElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/ir/IrElement;", "visitEnumConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrEnumConstructorCall;", "visitEnumEntry", "Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "visitErrorCallExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrErrorCallExpression;", "visitErrorDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrErrorDeclaration;", "visitErrorExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrErrorExpression;", "visitExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "visitExpressionBody", "Lorg/jetbrains/kotlin/ir/expressions/IrExpressionBody;", "visitExternalPackageFragment", "Lorg/jetbrains/kotlin/ir/declarations/IrExternalPackageFragment;", "visitField", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "visitFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "visitFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "visitFunctionExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionExpression;", "visitFunctionReference", "visitGetClass", "Lorg/jetbrains/kotlin/ir/expressions/IrGetClass;", "visitGetEnumValue", "Lorg/jetbrains/kotlin/ir/expressions/IrGetEnumValue;", "visitGetField", "Lorg/jetbrains/kotlin/ir/expressions/IrGetField;", "visitGetObjectValue", "Lorg/jetbrains/kotlin/ir/expressions/IrGetObjectValue;", "visitGetValue", "Lorg/jetbrains/kotlin/ir/expressions/IrGetValue;", "visitInstanceInitializerCall", "Lorg/jetbrains/kotlin/ir/expressions/IrInstanceInitializerCall;", "visitLocalDelegatedProperty", "Lorg/jetbrains/kotlin/ir/declarations/IrLocalDelegatedProperty;", "visitLocalDelegatedPropertyReference", "Lorg/jetbrains/kotlin/ir/expressions/IrLocalDelegatedPropertyReference;", "visitModuleFragment", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "visitProperty", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "visitPropertyReference", "Lorg/jetbrains/kotlin/ir/expressions/IrPropertyReference;", "visitRawFunctionReference", "Lorg/jetbrains/kotlin/ir/expressions/IrRawFunctionReference;", "visitReturn", "Lorg/jetbrains/kotlin/ir/expressions/IrReturn;", "visitScript", "Lorg/jetbrains/kotlin/ir/declarations/IrScript;", "visitSetField", "Lorg/jetbrains/kotlin/ir/expressions/IrSetField;", "visitSetValue", "Lorg/jetbrains/kotlin/ir/expressions/IrSetValue;", "visitSimpleFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "visitSpreadElement", "spread", "Lorg/jetbrains/kotlin/ir/expressions/IrSpreadElement;", "visitStringConcatenation", "Lorg/jetbrains/kotlin/ir/expressions/IrStringConcatenation;", "visitSyntheticBody", "Lorg/jetbrains/kotlin/ir/expressions/IrSyntheticBody;", "visitThrow", "Lorg/jetbrains/kotlin/ir/expressions/IrThrow;", "visitTry", "aTry", "Lorg/jetbrains/kotlin/ir/expressions/IrTry;", "visitTypeAlias", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeAlias;", "visitTypeOperator", "Lorg/jetbrains/kotlin/ir/expressions/IrTypeOperatorCall;", "visitTypeParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "visitValueParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "visitVararg", "Lorg/jetbrains/kotlin/ir/expressions/IrVararg;", "visitVariable", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "visitWhen", "Lorg/jetbrains/kotlin/ir/expressions/IrWhen;", "visitWhileLoop", "Lorg/jetbrains/kotlin/ir/expressions/IrWhileLoop;", "appendNullableAttribute", "", ExifInterface.GPS_DIRECTION_TRUE, "", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "prefix", "value", "toString", "Lkotlin/Function1;", "(Ljava/lang/StringBuilder;Ljava/lang/String;Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)V", "escapeIfRequired", "render", "renderReference", "renderSuperQualifier", "renderValueParameterTypes", "BoundSymbolReferenceRenderer", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RenderIrElementVisitor implements IrElementVisitor {
    private final DescriptorRenderer descriptorRendererForErrorDeclarations;
    private final DumpIrTreeOptions options;
    private final VariableNameData variableNameData;

    /* JADX WARN: Multi-variable type inference failed */
    public RenderIrElementVisitor() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public RenderIrElementVisitor(DumpIrTreeOptions options) {
        Intrinsics.checkNotNullParameter(options, "options");
        this.options = options;
        this.variableNameData = new VariableNameData(options.getNormalizeNames());
        this.descriptorRendererForErrorDeclarations = DescriptorRenderer.ONLY_NAMES_WITH_SHORT_TYPES;
    }

    public /* synthetic */ RenderIrElementVisitor(DumpIrTreeOptions dumpIrTreeOptions, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? new DumpIrTreeOptions(false, false, false, false, false, false, 63, null) : dumpIrTreeOptions);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBody, reason: merged with bridge method [inline-methods] */
    public String visitBody2(IrBody irBody, Void r2) {
        return (String) IrElementVisitor.DefaultImpls.visitBody(this, irBody, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBreakContinue, reason: merged with bridge method [inline-methods] */
    public String visitBreakContinue2(IrBreakContinue irBreakContinue, Void r2) {
        return (String) IrElementVisitor.DefaultImpls.visitBreakContinue(this, irBreakContinue, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public /* bridge */ /* synthetic */ Object visitCallableReference(IrCallableReference irCallableReference, Object obj) {
        return visitCallableReference((IrCallableReference<?>) irCallableReference, (Void) obj);
    }

    public String visitCallableReference(IrCallableReference<?> irCallableReference, Void r2) {
        return (String) IrElementVisitor.DefaultImpls.visitCallableReference(this, irCallableReference, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public /* bridge */ /* synthetic */ Object visitConst(IrConst irConst, Object obj) {
        return visitConst((IrConst<?>) irConst, (Void) obj);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantValue, reason: merged with bridge method [inline-methods] */
    public String visitConstantValue2(IrConstantValue irConstantValue, Void r2) {
        return (String) IrElementVisitor.DefaultImpls.visitConstantValue(this, irConstantValue, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitContainerExpression, reason: merged with bridge method [inline-methods] */
    public String visitContainerExpression2(IrContainerExpression irContainerExpression, Void r2) {
        return (String) IrElementVisitor.DefaultImpls.visitContainerExpression(this, irContainerExpression, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDeclarationReference, reason: merged with bridge method [inline-methods] */
    public String visitDeclarationReference2(IrDeclarationReference irDeclarationReference, Void r2) {
        return (String) IrElementVisitor.DefaultImpls.visitDeclarationReference(this, irDeclarationReference, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDynamicExpression, reason: merged with bridge method [inline-methods] */
    public String visitDynamicExpression2(IrDynamicExpression irDynamicExpression, Void r2) {
        return (String) IrElementVisitor.DefaultImpls.visitDynamicExpression(this, irDynamicExpression, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitElseBranch, reason: merged with bridge method [inline-methods] */
    public String visitElseBranch2(IrElseBranch irElseBranch, Void r2) {
        return (String) IrElementVisitor.DefaultImpls.visitElseBranch(this, irElseBranch, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFieldAccess, reason: merged with bridge method [inline-methods] */
    public String visitFieldAccess2(IrFieldAccessExpression irFieldAccessExpression, Void r2) {
        return (String) IrElementVisitor.DefaultImpls.visitFieldAccess(this, irFieldAccessExpression, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunctionAccess, reason: merged with bridge method [inline-methods] */
    public String visitFunctionAccess2(IrFunctionAccessExpression irFunctionAccessExpression, Void r2) {
        return (String) IrElementVisitor.DefaultImpls.visitFunctionAccess(this, irFunctionAccessExpression, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitLoop, reason: merged with bridge method [inline-methods] */
    public String visitLoop2(IrLoop irLoop, Void r2) {
        return (String) IrElementVisitor.DefaultImpls.visitLoop(this, irLoop, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public /* bridge */ /* synthetic */ Object visitMemberAccess(IrMemberAccessExpression irMemberAccessExpression, Object obj) {
        return visitMemberAccess((IrMemberAccessExpression<?>) irMemberAccessExpression, (Void) obj);
    }

    public String visitMemberAccess(IrMemberAccessExpression<?> irMemberAccessExpression, Void r2) {
        return (String) IrElementVisitor.DefaultImpls.visitMemberAccess(this, irMemberAccessExpression, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitPackageFragment, reason: merged with bridge method [inline-methods] */
    public String visitPackageFragment2(IrPackageFragment irPackageFragment, Void r2) {
        return (String) IrElementVisitor.DefaultImpls.visitPackageFragment(this, irPackageFragment, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSingletonReference, reason: merged with bridge method [inline-methods] */
    public String visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, Void r2) {
        return (String) IrElementVisitor.DefaultImpls.visitSingletonReference(this, irGetSingletonValue, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSuspendableExpression, reason: merged with bridge method [inline-methods] */
    public String visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, Void r2) {
        return (String) IrElementVisitor.DefaultImpls.visitSuspendableExpression(this, irSuspendableExpression, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSuspensionPoint, reason: merged with bridge method [inline-methods] */
    public String visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, Void r2) {
        return (String) IrElementVisitor.DefaultImpls.visitSuspensionPoint(this, irSuspensionPoint, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitValueAccess, reason: merged with bridge method [inline-methods] */
    public String visitValueAccess2(IrValueAccessExpression irValueAccessExpression, Void r2) {
        return (String) IrElementVisitor.DefaultImpls.visitValueAccess(this, irValueAccessExpression, r2);
    }

    public final String renderType(IrType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        return RenderIrElementKt.renderTypeWithRenderer(type, this, this.options);
    }

    public final String renderSymbolReference(IrSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return renderReference(symbol);
    }

    public final String renderAsAnnotation(IrConstructorCall irAnnotation) throws IOException {
        Intrinsics.checkNotNullParameter(irAnnotation, "irAnnotation");
        StringBuilder sb = new StringBuilder();
        RenderIrElementKt.renderAsAnnotation(sb, irAnnotation, this, this.options);
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    private final String render(IrType irType) {
        return RenderIrElementKt.renderTypeWithRenderer(irType, this, this.options);
    }

    private final String renderReference(IrSymbol irSymbol) {
        if (irSymbol.isBound()) {
            return (String) irSymbol.getOwner().accept(new BoundSymbolReferenceRenderer(this.variableNameData, this.options), null);
        }
        return "UNBOUND " + irSymbol.getClass().getSimpleName();
    }

    /* JADX INFO: compiled from: RenderIrElement.kt */
    @Metadata(d1 = {"\u0000\u0080\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u0001B\u0015\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u001a\u0010\t\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010\r\u001a\u00020\u00022\u0006\u0010\u000e\u001a\u00020\u000f2\b\u0010\f\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010\u0010\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u00112\b\u0010\f\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010\u0012\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u00132\b\u0010\f\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010\u0014\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u00152\b\u0010\f\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010\u0016\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u00172\b\u0010\f\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010\u0018\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u00192\b\u0010\f\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010\u001a\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u001b2\b\u0010\f\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010\u001c\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u001d2\b\u0010\f\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010\u001e\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u001f2\b\u0010\f\u001a\u0004\u0018\u00010\u0003H\u0016J\u0018\u0010 \u001a\u00020!*\u00060\"j\u0002`#2\u0006\u0010$\u001a\u00020%H\u0002J\u0018\u0010&\u001a\u00020!*\u00060\"j\u0002`#2\u0006\u0010\u000e\u001a\u00020'H\u0002J\u0018\u0010(\u001a\u00020!*\u00060\"j\u0002`#2\u0006\u0010\n\u001a\u00020%H\u0002J\u0018\u0010)\u001a\u00020!*\u00060\"j\u0002`#2\u0006\u0010\n\u001a\u00020*H\u0002R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006+"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/RenderIrElementVisitor$BoundSymbolReferenceRenderer;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;", "", "", "variableNameData", "Lorg/jetbrains/kotlin/ir/util/VariableNameData;", "options", "Lorg/jetbrains/kotlin/ir/util/DumpIrTreeOptions;", "(Lorg/jetbrains/kotlin/ir/util/VariableNameData;Lorg/jetbrains/kotlin/ir/util/DumpIrTreeOptions;)V", "visitClass", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "data", "visitElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/ir/IrElement;", "visitEnumEntry", "Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "visitField", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "visitFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "visitLocalDelegatedProperty", "Lorg/jetbrains/kotlin/ir/declarations/IrLocalDelegatedProperty;", "visitProperty", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "visitTypeParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "visitValueParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "visitVariable", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "renderDeclaredIn", "", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "irDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "renderElementNameFallback", "", "renderParentOfReferencedDeclaration", "renderTypeParameters", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParametersContainer;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class BoundSymbolReferenceRenderer implements IrElementVisitor {
        private final DumpIrTreeOptions options;
        private final VariableNameData variableNameData;

        public BoundSymbolReferenceRenderer(VariableNameData variableNameData, DumpIrTreeOptions options) {
            Intrinsics.checkNotNullParameter(variableNameData, "variableNameData");
            Intrinsics.checkNotNullParameter(options, "options");
            this.variableNameData = variableNameData;
            this.options = options;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitAnonymousInitializer, reason: merged with bridge method [inline-methods] */
        public String visitAnonymousInitializer2(IrAnonymousInitializer irAnonymousInitializer, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitAnonymousInitializer(this, irAnonymousInitializer, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBlock, reason: merged with bridge method [inline-methods] */
        public String visitBlock2(IrBlock irBlock, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitBlock(this, irBlock, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBlockBody, reason: merged with bridge method [inline-methods] */
        public String visitBlockBody2(IrBlockBody irBlockBody, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitBlockBody(this, irBlockBody, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBody, reason: merged with bridge method [inline-methods] */
        public String visitBody2(IrBody irBody, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitBody(this, irBody, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBranch, reason: merged with bridge method [inline-methods] */
        public String visitBranch2(IrBranch irBranch, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitBranch(this, irBranch, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBreak, reason: merged with bridge method [inline-methods] */
        public String visitBreak2(IrBreak irBreak, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitBreak(this, irBreak, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBreakContinue, reason: merged with bridge method [inline-methods] */
        public String visitBreakContinue2(IrBreakContinue irBreakContinue, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitBreakContinue(this, irBreakContinue, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitCall, reason: merged with bridge method [inline-methods] */
        public String visitCall2(IrCall irCall, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitCall(this, irCall, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public /* bridge */ /* synthetic */ Object visitCallableReference(IrCallableReference irCallableReference, Object obj) {
            return visitCallableReference((IrCallableReference<?>) irCallableReference, (Void) obj);
        }

        public String visitCallableReference(IrCallableReference<?> irCallableReference, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitCallableReference(this, irCallableReference, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitCatch, reason: merged with bridge method [inline-methods] */
        public String visitCatch2(IrCatch irCatch, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitCatch(this, irCatch, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitClassReference, reason: merged with bridge method [inline-methods] */
        public String visitClassReference2(IrClassReference irClassReference, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitClassReference(this, irClassReference, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitComposite, reason: merged with bridge method [inline-methods] */
        public String visitComposite2(IrComposite irComposite, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitComposite(this, irComposite, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public /* bridge */ /* synthetic */ Object visitConst(IrConst irConst, Object obj) {
            return visitConst((IrConst<?>) irConst, (Void) obj);
        }

        public String visitConst(IrConst<?> irConst, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitConst(this, irConst, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstantArray, reason: merged with bridge method [inline-methods] */
        public String visitConstantArray2(IrConstantArray irConstantArray, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitConstantArray(this, irConstantArray, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstantObject, reason: merged with bridge method [inline-methods] */
        public String visitConstantObject2(IrConstantObject irConstantObject, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitConstantObject(this, irConstantObject, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstantPrimitive, reason: merged with bridge method [inline-methods] */
        public String visitConstantPrimitive2(IrConstantPrimitive irConstantPrimitive, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitConstantPrimitive(this, irConstantPrimitive, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstantValue, reason: merged with bridge method [inline-methods] */
        public String visitConstantValue2(IrConstantValue irConstantValue, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitConstantValue(this, irConstantValue, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstructor, reason: merged with bridge method [inline-methods] */
        public String visitConstructor2(IrConstructor irConstructor, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitConstructor(this, irConstructor, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstructorCall, reason: merged with bridge method [inline-methods] */
        public String visitConstructorCall2(IrConstructorCall irConstructorCall, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitConstructorCall(this, irConstructorCall, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitContainerExpression, reason: merged with bridge method [inline-methods] */
        public String visitContainerExpression2(IrContainerExpression irContainerExpression, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitContainerExpression(this, irContainerExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitContinue, reason: merged with bridge method [inline-methods] */
        public String visitContinue2(IrContinue irContinue, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitContinue(this, irContinue, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDeclaration, reason: merged with bridge method [inline-methods] */
        public String visitDeclaration2(IrDeclarationBase irDeclarationBase, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitDeclaration(this, irDeclarationBase, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDeclarationReference, reason: merged with bridge method [inline-methods] */
        public String visitDeclarationReference2(IrDeclarationReference irDeclarationReference, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitDeclarationReference(this, irDeclarationReference, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDelegatingConstructorCall, reason: merged with bridge method [inline-methods] */
        public String visitDelegatingConstructorCall2(IrDelegatingConstructorCall irDelegatingConstructorCall, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitDelegatingConstructorCall(this, irDelegatingConstructorCall, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDoWhileLoop, reason: merged with bridge method [inline-methods] */
        public String visitDoWhileLoop2(IrDoWhileLoop irDoWhileLoop, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitDoWhileLoop(this, irDoWhileLoop, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDynamicExpression, reason: merged with bridge method [inline-methods] */
        public String visitDynamicExpression2(IrDynamicExpression irDynamicExpression, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitDynamicExpression(this, irDynamicExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDynamicMemberExpression, reason: merged with bridge method [inline-methods] */
        public String visitDynamicMemberExpression2(IrDynamicMemberExpression irDynamicMemberExpression, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitDynamicMemberExpression(this, irDynamicMemberExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDynamicOperatorExpression, reason: merged with bridge method [inline-methods] */
        public String visitDynamicOperatorExpression2(IrDynamicOperatorExpression irDynamicOperatorExpression, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitDynamicOperatorExpression(this, irDynamicOperatorExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitElseBranch, reason: merged with bridge method [inline-methods] */
        public String visitElseBranch2(IrElseBranch irElseBranch, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitElseBranch(this, irElseBranch, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitEnumConstructorCall, reason: merged with bridge method [inline-methods] */
        public String visitEnumConstructorCall2(IrEnumConstructorCall irEnumConstructorCall, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitEnumConstructorCall(this, irEnumConstructorCall, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitErrorCallExpression, reason: merged with bridge method [inline-methods] */
        public String visitErrorCallExpression2(IrErrorCallExpression irErrorCallExpression, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitErrorCallExpression(this, irErrorCallExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitErrorDeclaration, reason: merged with bridge method [inline-methods] */
        public String visitErrorDeclaration2(IrErrorDeclaration irErrorDeclaration, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitErrorDeclaration(this, irErrorDeclaration, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitErrorExpression, reason: merged with bridge method [inline-methods] */
        public String visitErrorExpression2(IrErrorExpression irErrorExpression, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitErrorExpression(this, irErrorExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitExpression, reason: merged with bridge method [inline-methods] */
        public String visitExpression2(IrExpression irExpression, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitExpression(this, irExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitExpressionBody, reason: merged with bridge method [inline-methods] */
        public String visitExpressionBody2(IrExpressionBody irExpressionBody, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitExpressionBody(this, irExpressionBody, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitExternalPackageFragment, reason: merged with bridge method [inline-methods] */
        public String visitExternalPackageFragment2(IrExternalPackageFragment irExternalPackageFragment, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitExternalPackageFragment(this, irExternalPackageFragment, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFieldAccess, reason: merged with bridge method [inline-methods] */
        public String visitFieldAccess2(IrFieldAccessExpression irFieldAccessExpression, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitFieldAccess(this, irFieldAccessExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFile, reason: merged with bridge method [inline-methods] */
        public String visitFile2(IrFile irFile, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitFile(this, irFile, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFunctionAccess, reason: merged with bridge method [inline-methods] */
        public String visitFunctionAccess2(IrFunctionAccessExpression irFunctionAccessExpression, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitFunctionAccess(this, irFunctionAccessExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFunctionExpression, reason: merged with bridge method [inline-methods] */
        public String visitFunctionExpression2(IrFunctionExpression irFunctionExpression, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitFunctionExpression(this, irFunctionExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFunctionReference, reason: merged with bridge method [inline-methods] */
        public String visitFunctionReference2(IrFunctionReference irFunctionReference, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitFunctionReference(this, irFunctionReference, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitGetClass, reason: merged with bridge method [inline-methods] */
        public String visitGetClass2(IrGetClass irGetClass, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitGetClass(this, irGetClass, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitGetEnumValue, reason: merged with bridge method [inline-methods] */
        public String visitGetEnumValue2(IrGetEnumValue irGetEnumValue, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitGetEnumValue(this, irGetEnumValue, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitGetField, reason: merged with bridge method [inline-methods] */
        public String visitGetField2(IrGetField irGetField, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitGetField(this, irGetField, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitGetObjectValue, reason: merged with bridge method [inline-methods] */
        public String visitGetObjectValue2(IrGetObjectValue irGetObjectValue, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitGetObjectValue(this, irGetObjectValue, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitGetValue, reason: merged with bridge method [inline-methods] */
        public String visitGetValue2(IrGetValue irGetValue, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitGetValue(this, irGetValue, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitInstanceInitializerCall, reason: merged with bridge method [inline-methods] */
        public String visitInstanceInitializerCall2(IrInstanceInitializerCall irInstanceInitializerCall, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitInstanceInitializerCall(this, irInstanceInitializerCall, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitLocalDelegatedPropertyReference, reason: merged with bridge method [inline-methods] */
        public String visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitLocalDelegatedPropertyReference(this, irLocalDelegatedPropertyReference, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitLoop, reason: merged with bridge method [inline-methods] */
        public String visitLoop2(IrLoop irLoop, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitLoop(this, irLoop, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public /* bridge */ /* synthetic */ Object visitMemberAccess(IrMemberAccessExpression irMemberAccessExpression, Object obj) {
            return visitMemberAccess((IrMemberAccessExpression<?>) irMemberAccessExpression, (Void) obj);
        }

        public String visitMemberAccess(IrMemberAccessExpression<?> irMemberAccessExpression, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitMemberAccess(this, irMemberAccessExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitModuleFragment, reason: merged with bridge method [inline-methods] */
        public String visitModuleFragment2(IrModuleFragment irModuleFragment, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitModuleFragment(this, irModuleFragment, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitPackageFragment, reason: merged with bridge method [inline-methods] */
        public String visitPackageFragment2(IrPackageFragment irPackageFragment, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitPackageFragment(this, irPackageFragment, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitPropertyReference, reason: merged with bridge method [inline-methods] */
        public String visitPropertyReference2(IrPropertyReference irPropertyReference, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitPropertyReference(this, irPropertyReference, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitRawFunctionReference, reason: merged with bridge method [inline-methods] */
        public String visitRawFunctionReference2(IrRawFunctionReference irRawFunctionReference, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitRawFunctionReference(this, irRawFunctionReference, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitReturn, reason: merged with bridge method [inline-methods] */
        public String visitReturn2(IrReturn irReturn, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitReturn(this, irReturn, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitScript, reason: merged with bridge method [inline-methods] */
        public String visitScript2(IrScript irScript, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitScript(this, irScript, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSetField, reason: merged with bridge method [inline-methods] */
        public String visitSetField2(IrSetField irSetField, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitSetField(this, irSetField, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSetValue, reason: merged with bridge method [inline-methods] */
        public String visitSetValue2(IrSetValue irSetValue, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitSetValue(this, irSetValue, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSimpleFunction, reason: merged with bridge method [inline-methods] */
        public String visitSimpleFunction2(IrSimpleFunction irSimpleFunction, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitSimpleFunction(this, irSimpleFunction, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSingletonReference, reason: merged with bridge method [inline-methods] */
        public String visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitSingletonReference(this, irGetSingletonValue, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSpreadElement, reason: merged with bridge method [inline-methods] */
        public String visitSpreadElement2(IrSpreadElement irSpreadElement, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitSpreadElement(this, irSpreadElement, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitStringConcatenation, reason: merged with bridge method [inline-methods] */
        public String visitStringConcatenation2(IrStringConcatenation irStringConcatenation, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitStringConcatenation(this, irStringConcatenation, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSuspendableExpression, reason: merged with bridge method [inline-methods] */
        public String visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitSuspendableExpression(this, irSuspendableExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSuspensionPoint, reason: merged with bridge method [inline-methods] */
        public String visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitSuspensionPoint(this, irSuspensionPoint, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSyntheticBody, reason: merged with bridge method [inline-methods] */
        public String visitSyntheticBody2(IrSyntheticBody irSyntheticBody, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitSyntheticBody(this, irSyntheticBody, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitThrow, reason: merged with bridge method [inline-methods] */
        public String visitThrow2(IrThrow irThrow, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitThrow(this, irThrow, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitTry, reason: merged with bridge method [inline-methods] */
        public String visitTry2(IrTry irTry, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitTry(this, irTry, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitTypeAlias, reason: merged with bridge method [inline-methods] */
        public String visitTypeAlias2(IrTypeAlias irTypeAlias, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitTypeAlias(this, irTypeAlias, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitTypeOperator, reason: merged with bridge method [inline-methods] */
        public String visitTypeOperator2(IrTypeOperatorCall irTypeOperatorCall, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitTypeOperator(this, irTypeOperatorCall, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitValueAccess, reason: merged with bridge method [inline-methods] */
        public String visitValueAccess2(IrValueAccessExpression irValueAccessExpression, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitValueAccess(this, irValueAccessExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitVararg, reason: merged with bridge method [inline-methods] */
        public String visitVararg2(IrVararg irVararg, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitVararg(this, irVararg, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitWhen, reason: merged with bridge method [inline-methods] */
        public String visitWhen2(IrWhen irWhen, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitWhen(this, irWhen, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitWhileLoop, reason: merged with bridge method [inline-methods] */
        public String visitWhileLoop2(IrWhileLoop irWhileLoop, Void r2) {
            return (String) IrElementVisitor.DefaultImpls.visitWhileLoop(this, irWhileLoop, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitTypeParameter, reason: merged with bridge method [inline-methods] */
        public String visitTypeParameter2(IrTypeParameter declaration, Void data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return RenderIrElementKt.renderTypeParameter(declaration, null, this.options);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitClass, reason: merged with bridge method [inline-methods] */
        public String visitClass2(IrClass declaration, Void data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return RenderIrElementKt.renderClassWithRenderer(declaration, null, this.options);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitEnumEntry, reason: merged with bridge method [inline-methods] */
        public String visitEnumEntry2(IrEnumEntry declaration, Void data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return RenderIrElementKt.renderEnumEntry(declaration, this.options);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitField, reason: merged with bridge method [inline-methods] */
        public String visitField2(IrField declaration, Void data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return RenderIrElementKt.renderField(declaration, null, this.options);
        }

        private final void renderTypeParameters(StringBuilder sb, IrTypeParametersContainer irTypeParametersContainer) throws IOException {
            if (irTypeParametersContainer.getTypeParameters().isEmpty()) {
                return;
            }
            StringBuilder sb2 = sb;
            List<IrTypeParameter> typeParameters = irTypeParametersContainer.getTypeParameters();
            sb2.append((CharSequence) "<");
            boolean z = true;
            for (Object obj : typeParameters) {
                if (!z) {
                    sb2.append((CharSequence) ", ");
                }
                sb2.append(((IrTypeParameter) obj).getName().asString());
                z = false;
            }
            sb2.append((CharSequence) ">");
            sb.append(StringUtil.SPACE);
        }

        private final void renderDeclaredIn(StringBuilder sb, IrDeclaration irDeclaration) {
            sb.append("declared in ");
            renderParentOfReferencedDeclaration(sb, irDeclaration);
        }

        private final void renderParentOfReferencedDeclaration(StringBuilder sb, IrDeclaration irDeclaration) {
            try {
                IrDeclarationParent parent = irDeclaration.getParent();
                if (parent instanceof IrPackageFragment) {
                    String strAsString = ((IrPackageFragment) parent).getPackageFqName().asString();
                    Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                    String str = strAsString;
                    if (str.length() == 0) {
                        str = "<root>";
                    }
                    sb.append(str);
                    return;
                }
                if (parent instanceof IrDeclaration) {
                    IrDeclaration irDeclaration2 = (IrDeclaration) parent;
                    renderParentOfReferencedDeclaration(sb, irDeclaration2);
                    DumpIrTreeOptions dumpIrTreeOptions = this.options;
                    if (!IrUtilsKt.isFileClass(irDeclaration2) || dumpIrTreeOptions.getPrintFacadeClassInFqNames()) {
                        sb.append('.');
                        if (!(irDeclaration2 instanceof IrDeclarationWithName)) {
                            renderElementNameFallback(sb, parent);
                            return;
                        } else {
                            sb.append(((IrDeclarationWithName) irDeclaration2).getName());
                            return;
                        }
                    }
                    return;
                }
                renderElementNameFallback(sb, parent);
            } catch (Exception unused) {
                sb.append("<no parent>");
            }
        }

        private final void renderElementNameFallback(StringBuilder sb, Object obj) {
            sb.append('{');
            sb.append(obj.getClass().getSimpleName());
            sb.append('}');
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitElement, reason: merged with bridge method [inline-methods] */
        public String visitElement2(IrElement element, Void data) {
            Intrinsics.checkNotNullParameter(element, "element");
            StringBuilder sb = new StringBuilder();
            sb.append('{');
            sb.append(element.getClass().getSimpleName());
            sb.append('}');
            if (element instanceof IrDeclaration) {
                if (element instanceof IrDeclarationWithName) {
                    sb.append(((IrDeclarationWithName) element).getName());
                    sb.append(StringUtil.SPACE);
                }
                renderDeclaredIn(sb, (IrDeclaration) element);
            }
            String string = sb.toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            return StringsKt.trimEnd((CharSequence) string).toString();
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitVariable, reason: merged with bridge method [inline-methods] */
        public String visitVariable2(IrVariable declaration, Void data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            StringBuilder sb = new StringBuilder();
            sb.append(declaration.getIsVar() ? "var " : "val ");
            sb.append(RenderIrElementKt.normalizedName(declaration, this.variableNameData));
            sb.append(": ");
            sb.append(RenderIrElementKt.renderTypeWithRenderer(declaration.getType(), null, this.options));
            sb.append(StringUtil.SPACE);
            if (this.options.getPrintFlagsInDeclarationReferences()) {
                sb.append(RenderIrElementKt.renderVariableFlags(declaration));
            }
            renderDeclaredIn(sb, declaration);
            String string = sb.toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            return StringsKt.trimEnd((CharSequence) string).toString();
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitValueParameter, reason: merged with bridge method [inline-methods] */
        public String visitValueParameter2(IrValueParameter declaration, Void data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            StringBuilder sb = new StringBuilder();
            sb.append(declaration.getName().asString());
            sb.append(": ");
            sb.append(RenderIrElementKt.renderTypeWithRenderer(declaration.getType(), null, this.options));
            sb.append(StringUtil.SPACE);
            if (this.options.getPrintFlagsInDeclarationReferences()) {
                sb.append(RenderIrElementKt.renderValueParameterFlags(declaration));
            }
            renderDeclaredIn(sb, declaration);
            String string = sb.toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            return StringsKt.trimEnd((CharSequence) string).toString();
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFunction, reason: merged with bridge method [inline-methods] */
        public String visitFunction2(IrFunction declaration, Void data) throws IOException {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            StringBuilder sb = new StringBuilder();
            sb.append(declaration.getVisibility());
            sb.append(StringUtil.SPACE);
            boolean z = declaration instanceof IrSimpleFunction;
            if (z) {
                sb.append(CapitalizeDecapitalizeKt.toLowerCaseAsciiOnly(((IrSimpleFunction) declaration).getModality().toString()));
                sb.append(StringUtil.SPACE);
            }
            if (z) {
                sb.append("fun ");
            } else if (declaration instanceof IrConstructor) {
                sb.append("constructor ");
            } else {
                sb.append("{" + declaration.getClass().getSimpleName() + '}');
            }
            sb.append(declaration.getName().asString());
            sb.append(StringUtil.SPACE);
            renderTypeParameters(sb, declaration);
            StringBuilder sb2 = sb;
            List<IrValueParameter> valueParameters = declaration.getValueParameters();
            sb2.append((CharSequence) "(");
            boolean z2 = true;
            for (Object obj : valueParameters) {
                if (!z2) {
                    sb2.append((CharSequence) ", ");
                }
                IrValueParameter irValueParameter = (IrValueParameter) obj;
                StringBuilder sb3 = sb2;
                IrType varargElementType = irValueParameter.getVarargElementType();
                if (varargElementType != null) {
                    sb3.append("vararg ");
                    sb3.append(irValueParameter.getName().asString());
                    sb3.append(": ");
                    sb3.append(RenderIrElementKt.renderTypeWithRenderer(varargElementType, null, this.options));
                } else {
                    sb3.append(irValueParameter.getName().asString());
                    sb3.append(": ");
                    sb3.append(RenderIrElementKt.renderTypeWithRenderer(irValueParameter.getType(), null, this.options));
                }
                z2 = false;
            }
            sb2.append((CharSequence) Constant.AFTER_QUTO);
            if (z) {
                sb.append(": ");
                sb.append(RenderIrElementKt.renderReturnType(declaration, null, this.options));
            }
            sb.append(StringUtil.SPACE);
            if (this.options.getPrintFlagsInDeclarationReferences()) {
                if (z) {
                    sb.append(RenderIrElementKt.renderSimpleFunctionFlags((IrSimpleFunction) declaration));
                } else if (declaration instanceof IrConstructor) {
                    sb.append(RenderIrElementKt.renderConstructorFlags((IrConstructor) declaration));
                }
            }
            renderDeclaredIn(sb, declaration);
            String string = sb.toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            return StringsKt.trimEnd((CharSequence) string).toString();
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitProperty, reason: merged with bridge method [inline-methods] */
        public String visitProperty2(IrProperty declaration, Void data) {
            IrType type;
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            StringBuilder sb = new StringBuilder();
            sb.append(declaration.getVisibility());
            sb.append(StringUtil.SPACE);
            sb.append(CapitalizeDecapitalizeKt.toLowerCaseAsciiOnly(declaration.getModality().toString()));
            sb.append(StringUtil.SPACE);
            sb.append(declaration.getName().asString());
            IrSimpleFunction getter = declaration.getGetter();
            if (getter != null) {
                sb.append(": ");
                sb.append(RenderIrElementKt.renderReturnType(getter, null, this.options));
            } else {
                IrField backingField = declaration.getBackingField();
                if (backingField != null && (type = backingField.getType()) != null) {
                    sb.append(": ");
                    sb.append(RenderIrElementKt.renderTypeWithRenderer(type, null, this.options));
                }
            }
            if (this.options.getPrintFlagsInDeclarationReferences()) {
                sb.append(StringUtil.SPACE);
                sb.append(RenderIrElementKt.renderPropertyFlags(declaration));
            }
            String string = sb.toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            return StringsKt.trimEnd((CharSequence) string).toString();
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitLocalDelegatedProperty, reason: merged with bridge method [inline-methods] */
        public String visitLocalDelegatedProperty2(IrLocalDelegatedProperty declaration, Void data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            StringBuilder sb = new StringBuilder();
            sb.append(declaration.getIsVar() ? "var " : "val ");
            sb.append(declaration.getName().asString());
            sb.append(": ");
            sb.append(RenderIrElementKt.renderTypeWithRenderer(declaration.getType(), null, this.options));
            sb.append(" by (...)");
            String string = sb.toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            return StringsKt.trimEnd((CharSequence) string).toString();
        }
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitElement, reason: merged with bridge method [inline-methods] */
    public String visitElement2(IrElement element, Void data) {
        Intrinsics.checkNotNullParameter(element, "element");
        return "?ELEMENT? " + element.getClass().getSimpleName() + StringUtil.SPACE + element;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDeclaration, reason: merged with bridge method [inline-methods] */
    public String visitDeclaration2(IrDeclarationBase declaration, Void data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return "?DECLARATION? " + declaration.getClass().getSimpleName() + StringUtil.SPACE + declaration;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitModuleFragment, reason: merged with bridge method [inline-methods] */
    public String visitModuleFragment2(IrModuleFragment declaration, Void data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return "MODULE_FRAGMENT name:" + declaration.getName();
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExternalPackageFragment, reason: merged with bridge method [inline-methods] */
    public String visitExternalPackageFragment2(IrExternalPackageFragment declaration, Void data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return "EXTERNAL_PACKAGE_FRAGMENT fqName:" + declaration.getPackageFqName();
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFile, reason: merged with bridge method [inline-methods] */
    public String visitFile2(IrFile declaration, Void data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return "FILE fqName:" + declaration.getPackageFqName() + " fileName:" + IrDeclarationsKt.getPath(declaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunction, reason: merged with bridge method [inline-methods] */
    public String visitFunction2(IrFunction declaration, Void data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return StringsKt.trimEnd((CharSequence) ("FUN " + RenderIrElementKt.renderOriginIfNonTrivial(declaration))).toString();
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitScript, reason: merged with bridge method [inline-methods] */
    public String visitScript2(IrScript declaration, Void data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return "SCRIPT";
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSimpleFunction, reason: merged with bridge method [inline-methods] */
    public String visitSimpleFunction2(IrSimpleFunction declaration, Void data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrSimpleFunction irSimpleFunction = declaration;
        return StringsKt.trimEnd((CharSequence) ("FUN " + RenderIrElementKt.renderOriginIfNonTrivial(declaration) + "name:" + declaration.getName() + StringUtil.SPACE + RenderIrElementKt.renderSignatureIfEnabled(declaration, this.options.getPrintSignatures()) + "visibility:" + declaration.getVisibility() + " modality:" + declaration.getModality() + StringUtil.SPACE + RenderIrElementKt.renderTypeParameters(irSimpleFunction) + StringUtil.SPACE + renderValueParameterTypes(irSimpleFunction) + " returnType:" + RenderIrElementKt.renderReturnType(irSimpleFunction, this, this.options) + StringUtil.SPACE + RenderIrElementKt.renderSimpleFunctionFlags(declaration))).toString();
    }

    private final String renderValueParameterTypes(IrFunction irFunction) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = arrayList;
        IrValueParameter dispatchReceiverParameter = irFunction.getDispatchReceiverParameter();
        CollectionsKt.addIfNotNull(arrayList2, dispatchReceiverParameter != null ? "$this:" + render(dispatchReceiverParameter.getType()) : null);
        IrValueParameter extensionReceiverParameter = irFunction.getExtensionReceiverParameter();
        CollectionsKt.addIfNotNull(arrayList2, extensionReceiverParameter != null ? "$receiver:" + render(extensionReceiverParameter.getType()) : null);
        for (IrValueParameter irValueParameter : irFunction.getValueParameters()) {
            arrayList2.add(irValueParameter.getName() + ':' + render(irValueParameter.getType()));
        }
        return kotlin.collections.CollectionsKt.joinToString$default(arrayList, ", ", "(", Constant.AFTER_QUTO, 0, null, null, 56, null);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstructor, reason: merged with bridge method [inline-methods] */
    public String visitConstructor2(IrConstructor declaration, Void data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrConstructor irConstructor = declaration;
        return StringsKt.trimEnd((CharSequence) ("CONSTRUCTOR " + RenderIrElementKt.renderOriginIfNonTrivial(declaration) + RenderIrElementKt.renderSignatureIfEnabled(declaration, this.options.getPrintSignatures()) + "visibility:" + declaration.getVisibility() + StringUtil.SPACE + RenderIrElementKt.renderTypeParameters(irConstructor) + StringUtil.SPACE + renderValueParameterTypes(irConstructor) + " returnType:" + RenderIrElementKt.renderReturnType(irConstructor, this, this.options) + StringUtil.SPACE + RenderIrElementKt.renderConstructorFlags(declaration))).toString();
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitProperty, reason: merged with bridge method [inline-methods] */
    public String visitProperty2(IrProperty declaration, Void data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return StringsKt.trimEnd((CharSequence) ("PROPERTY " + RenderIrElementKt.renderOriginIfNonTrivial(declaration) + "name:" + declaration.getName() + StringUtil.SPACE + RenderIrElementKt.renderSignatureIfEnabled(declaration, this.options.getPrintSignatures()) + "visibility:" + declaration.getVisibility() + " modality:" + declaration.getModality() + StringUtil.SPACE + RenderIrElementKt.renderPropertyFlags(declaration))).toString();
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitField, reason: merged with bridge method [inline-methods] */
    public String visitField2(IrField declaration, Void data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return RenderIrElementKt.renderField(declaration, this, this.options);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitClass, reason: merged with bridge method [inline-methods] */
    public String visitClass2(IrClass declaration, Void data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return RenderIrElementKt.renderClassWithRenderer(declaration, this, this.options);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitVariable, reason: merged with bridge method [inline-methods] */
    public String visitVariable2(IrVariable declaration, Void data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return StringsKt.trimEnd((CharSequence) ("VAR " + RenderIrElementKt.renderOriginIfNonTrivial(declaration) + "name:" + RenderIrElementKt.normalizedName(declaration, this.variableNameData) + " type:" + render(declaration.getType()) + StringUtil.SPACE + RenderIrElementKt.renderVariableFlags(declaration))).toString();
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitEnumEntry, reason: merged with bridge method [inline-methods] */
    public String visitEnumEntry2(IrEnumEntry declaration, Void data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return RenderIrElementKt.renderEnumEntry(declaration, this.options);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitAnonymousInitializer, reason: merged with bridge method [inline-methods] */
    public String visitAnonymousInitializer2(IrAnonymousInitializer declaration, Void data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return "ANONYMOUS_INITIALIZER isStatic=" + declaration.getIsStatic();
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeParameter, reason: merged with bridge method [inline-methods] */
    public String visitTypeParameter2(IrTypeParameter declaration, Void data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return RenderIrElementKt.renderTypeParameter(declaration, this, this.options);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitValueParameter, reason: merged with bridge method [inline-methods] */
    public String visitValueParameter2(IrValueParameter declaration, Void data) {
        String str;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        String str2 = "";
        StringBuilder sbAppend = new StringBuilder("VALUE_PARAMETER ").append(RenderIrElementKt.renderOriginIfNonTrivial(declaration)).append("name:").append(declaration.getName()).append(StringUtil.SPACE).append(declaration.getIndex() >= 0 ? "index:" + declaration.getIndex() + StringUtil.SPACE : "").append("type:").append(render(declaration.getType())).append(StringUtil.SPACE);
        IrType varargElementType = declaration.getVarargElementType();
        if (varargElementType != null && (str = "varargElementType:" + render(varargElementType) + StringUtil.SPACE) != null) {
            str2 = str;
        }
        return StringsKt.trimEnd((CharSequence) sbAppend.append(str2).append(RenderIrElementKt.renderValueParameterFlags(declaration)).toString()).toString();
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitLocalDelegatedProperty, reason: merged with bridge method [inline-methods] */
    public String visitLocalDelegatedProperty2(IrLocalDelegatedProperty declaration, Void data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return StringsKt.trimEnd((CharSequence) ("LOCAL_DELEGATED_PROPERTY " + RenderIrElementKt.renderOriginIfNonTrivial(declaration) + "name:" + declaration.getName() + " type:" + render(declaration.getType()) + " flags:" + RenderIrElementKt.renderLocalDelegatedPropertyFlags(declaration))).toString();
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeAlias, reason: merged with bridge method [inline-methods] */
    public String visitTypeAlias2(IrTypeAlias declaration, Void data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return "TYPEALIAS " + RenderIrElementKt.renderOriginIfNonTrivial(declaration) + "name:" + declaration.getName() + StringUtil.SPACE + RenderIrElementKt.renderSignatureIfEnabled(declaration, this.options.getPrintSignatures()) + "visibility:" + declaration.getVisibility() + " expandedType:" + render(declaration.getExpandedType()) + RenderIrElementKt.renderTypeAliasFlags(declaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExpressionBody, reason: merged with bridge method [inline-methods] */
    public String visitExpressionBody2(IrExpressionBody body, Void data) {
        Intrinsics.checkNotNullParameter(body, "body");
        return "EXPRESSION_BODY";
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBlockBody, reason: merged with bridge method [inline-methods] */
    public String visitBlockBody2(IrBlockBody body, Void data) {
        Intrinsics.checkNotNullParameter(body, "body");
        return "BLOCK_BODY";
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSyntheticBody, reason: merged with bridge method [inline-methods] */
    public String visitSyntheticBody2(IrSyntheticBody body, Void data) {
        Intrinsics.checkNotNullParameter(body, "body");
        return "SYNTHETIC_BODY kind=" + body.getKind();
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExpression, reason: merged with bridge method [inline-methods] */
    public String visitExpression2(IrExpression expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return "? " + expression.getClass().getSimpleName() + " type=" + render(expression.getType());
    }

    public String visitConst(IrConst<?> expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        StringBuilder sbAppend = new StringBuilder("CONST ").append(expression.getKind()).append(" type=").append(render(expression.getType())).append(" value=");
        Object value = expression.getValue();
        return sbAppend.append(value != null ? escapeIfRequired(value) : null).toString();
    }

    private final Object escapeIfRequired(Object obj) {
        if (obj instanceof String) {
            return "\"" + org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil.escapeStringCharacters((String) obj) + '\"';
        }
        return obj instanceof Character ? FormatHelper.QUOTE + org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil.escapeStringCharacters(obj.toString()) + '\'' : obj;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitVararg, reason: merged with bridge method [inline-methods] */
    public String visitVararg2(IrVararg expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return "VARARG type=" + render(expression.getType()) + " varargElementType=" + render(expression.getVarargElementType());
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSpreadElement, reason: merged with bridge method [inline-methods] */
    public String visitSpreadElement2(IrSpreadElement spread, Void data) {
        Intrinsics.checkNotNullParameter(spread, "spread");
        return "SPREAD_ELEMENT";
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBlock, reason: merged with bridge method [inline-methods] */
    public String visitBlock2(IrBlock expression, Void data) {
        String str;
        Intrinsics.checkNotNullParameter(expression, "expression");
        if (expression instanceof IrReturnableBlock) {
            str = "RETURNABLE_";
        } else {
            str = expression instanceof IrInlinedFunctionBlock ? "INLINED_" : "";
        }
        return str + "BLOCK type=" + render(expression.getType()) + " origin=" + expression.getOrigin();
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitComposite, reason: merged with bridge method [inline-methods] */
    public String visitComposite2(IrComposite expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return "COMPOSITE type=" + render(expression.getType()) + " origin=" + expression.getOrigin();
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitReturn, reason: merged with bridge method [inline-methods] */
    public String visitReturn2(IrReturn expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return "RETURN type=" + render(expression.getType()) + " from='" + renderReference(expression.getReturnTargetSymbol()) + '\'';
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitCall, reason: merged with bridge method [inline-methods] */
    public String visitCall2(IrCall expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return "CALL '" + renderReference(expression.getSymbol()) + "' " + renderSuperQualifier(expression) + "type=" + render(expression.getType()) + " origin=" + expression.getOrigin();
    }

    private final String renderSuperQualifier(IrCall irCall) {
        String string;
        IrClassSymbol superQualifierSymbol = irCall.getSuperQualifierSymbol();
        return (superQualifierSymbol == null || (string = new StringBuilder("superQualifier='").append(renderReference(superQualifierSymbol)).append("' ").toString()) == null) ? "" : string;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstructorCall, reason: merged with bridge method [inline-methods] */
    public String visitConstructorCall2(IrConstructorCall expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return "CONSTRUCTOR_CALL '" + renderReference(expression.getSymbol()) + "' type=" + render(expression.getType()) + " origin=" + expression.getOrigin();
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDelegatingConstructorCall, reason: merged with bridge method [inline-methods] */
    public String visitDelegatingConstructorCall2(IrDelegatingConstructorCall expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return "DELEGATING_CONSTRUCTOR_CALL '" + renderReference(expression.getSymbol()) + '\'';
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitEnumConstructorCall, reason: merged with bridge method [inline-methods] */
    public String visitEnumConstructorCall2(IrEnumConstructorCall expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return "ENUM_CONSTRUCTOR_CALL '" + renderReference(expression.getSymbol()) + '\'';
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitInstanceInitializerCall, reason: merged with bridge method [inline-methods] */
    public String visitInstanceInitializerCall2(IrInstanceInitializerCall expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return "INSTANCE_INITIALIZER_CALL classDescriptor='" + renderReference(expression.getClassSymbol()) + '\'';
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetValue, reason: merged with bridge method [inline-methods] */
    public String visitGetValue2(IrGetValue expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return "GET_VAR '" + renderReference(expression.getSymbol()) + "' type=" + render(expression.getType()) + " origin=" + expression.getOrigin();
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSetValue, reason: merged with bridge method [inline-methods] */
    public String visitSetValue2(IrSetValue expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return "SET_VAR '" + renderReference(expression.getSymbol()) + "' type=" + render(expression.getType()) + " origin=" + expression.getOrigin();
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetField, reason: merged with bridge method [inline-methods] */
    public String visitGetField2(IrGetField expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return "GET_FIELD '" + renderReference(expression.getSymbol()) + "' type=" + render(expression.getType()) + " origin=" + expression.getOrigin();
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSetField, reason: merged with bridge method [inline-methods] */
    public String visitSetField2(IrSetField expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return "SET_FIELD '" + renderReference(expression.getSymbol()) + "' type=" + render(expression.getType()) + " origin=" + expression.getOrigin();
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetObjectValue, reason: merged with bridge method [inline-methods] */
    public String visitGetObjectValue2(IrGetObjectValue expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return "GET_OBJECT '" + renderReference(expression.getSymbol()) + "' type=" + render(expression.getType());
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetEnumValue, reason: merged with bridge method [inline-methods] */
    public String visitGetEnumValue2(IrGetEnumValue expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return "GET_ENUM '" + renderReference(expression.getSymbol()) + "' type=" + render(expression.getType());
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitStringConcatenation, reason: merged with bridge method [inline-methods] */
    public String visitStringConcatenation2(IrStringConcatenation expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return "STRING_CONCATENATION type=" + render(expression.getType());
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeOperator, reason: merged with bridge method [inline-methods] */
    public String visitTypeOperator2(IrTypeOperatorCall expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return "TYPE_OP type=" + render(expression.getType()) + " origin=" + expression.getOperator() + " typeOperand=" + render(expression.getTypeOperand());
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitWhen, reason: merged with bridge method [inline-methods] */
    public String visitWhen2(IrWhen expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return "WHEN type=" + render(expression.getType()) + " origin=" + expression.getOrigin();
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBranch, reason: merged with bridge method [inline-methods] */
    public String visitBranch2(IrBranch branch, Void data) {
        Intrinsics.checkNotNullParameter(branch, "branch");
        return "BRANCH";
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitWhileLoop, reason: merged with bridge method [inline-methods] */
    public String visitWhileLoop2(IrWhileLoop loop, Void data) {
        Intrinsics.checkNotNullParameter(loop, "loop");
        return "WHILE label=" + loop.getLabel() + " origin=" + loop.getOrigin();
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDoWhileLoop, reason: merged with bridge method [inline-methods] */
    public String visitDoWhileLoop2(IrDoWhileLoop loop, Void data) {
        Intrinsics.checkNotNullParameter(loop, "loop");
        return "DO_WHILE label=" + loop.getLabel() + " origin=" + loop.getOrigin();
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBreak, reason: merged with bridge method [inline-methods] */
    public String visitBreak2(IrBreak jump, Void data) {
        Intrinsics.checkNotNullParameter(jump, "jump");
        return "BREAK label=" + jump.getLabel() + " loop.label=" + jump.getLoop().getLabel();
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitContinue, reason: merged with bridge method [inline-methods] */
    public String visitContinue2(IrContinue jump, Void data) {
        Intrinsics.checkNotNullParameter(jump, "jump");
        return "CONTINUE label=" + jump.getLabel() + " loop.label=" + jump.getLoop().getLabel();
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitThrow, reason: merged with bridge method [inline-methods] */
    public String visitThrow2(IrThrow expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return "THROW type=" + render(expression.getType());
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunctionReference, reason: merged with bridge method [inline-methods] */
    public String visitFunctionReference2(IrFunctionReference expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return "FUNCTION_REFERENCE '" + renderReference(expression.getSymbol()) + "' type=" + render(expression.getType()) + " origin=" + expression.getOrigin() + " reflectionTarget=" + renderReflectionTarget(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitRawFunctionReference, reason: merged with bridge method [inline-methods] */
    public String visitRawFunctionReference2(IrRawFunctionReference expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return "RAW_FUNCTION_REFERENCE '" + renderReference(expression.getSymbol()) + "' type=" + render(expression.getType());
    }

    private final String renderReflectionTarget(IrFunctionReference expression) {
        if (Intrinsics.areEqual(expression.getSymbol(), expression.getReflectionTarget())) {
            return "<same>";
        }
        IrFunctionSymbol reflectionTarget = expression.getReflectionTarget();
        if (reflectionTarget != null) {
            return renderReference(reflectionTarget);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitClassReference, reason: merged with bridge method [inline-methods] */
    public String visitClassReference2(IrClassReference expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return "CLASS_REFERENCE '" + renderReference(expression.getSymbol()) + "' type=" + render(expression.getType());
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetClass, reason: merged with bridge method [inline-methods] */
    public String visitGetClass2(IrGetClass expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return "GET_CLASS type=" + render(expression.getType());
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTry, reason: merged with bridge method [inline-methods] */
    public String visitTry2(IrTry aTry, Void data) {
        Intrinsics.checkNotNullParameter(aTry, "aTry");
        return "TRY type=" + render(aTry.getType());
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitCatch, reason: merged with bridge method [inline-methods] */
    public String visitCatch2(IrCatch aCatch, Void data) {
        Intrinsics.checkNotNullParameter(aCatch, "aCatch");
        return "CATCH parameter=" + renderReference(aCatch.getCatchParameter().getSymbol());
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDynamicOperatorExpression, reason: merged with bridge method [inline-methods] */
    public String visitDynamicOperatorExpression2(IrDynamicOperatorExpression expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return "DYN_OP operator=" + expression.getOperator() + " type=" + render(expression.getType());
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDynamicMemberExpression, reason: merged with bridge method [inline-methods] */
    public String visitDynamicMemberExpression2(IrDynamicMemberExpression expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return "DYN_MEMBER memberName='" + expression.getMemberName() + "' type=" + render(expression.getType());
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitErrorDeclaration, reason: merged with bridge method [inline-methods] */
    public String visitErrorDeclaration2(IrErrorDeclaration declaration, Void data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        StringBuilder sbAppend = new StringBuilder("ERROR_DECL ").append(declaration.getDescriptor().getClass().getSimpleName()).append(StringUtil.SPACE);
        DescriptorRenderer descriptorRenderer = this.descriptorRendererForErrorDeclarations;
        DeclarationDescriptor original = declaration.getDescriptor().getOriginal();
        Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
        return sbAppend.append(RenderIrElementKt.renderDescriptor(descriptorRenderer, original)).toString();
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitErrorExpression, reason: merged with bridge method [inline-methods] */
    public String visitErrorExpression2(IrErrorExpression expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return "ERROR_EXPR '" + expression.getDescription() + "' type=" + render(expression.getType());
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitErrorCallExpression, reason: merged with bridge method [inline-methods] */
    public String visitErrorCallExpression2(IrErrorCallExpression expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return "ERROR_CALL '" + expression.getDescription() + "' type=" + render(expression.getType());
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantArray, reason: merged with bridge method [inline-methods] */
    public String visitConstantArray2(IrConstantArray expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return "CONSTANT_ARRAY type=" + render(expression.getType());
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantObject, reason: merged with bridge method [inline-methods] */
    public String visitConstantObject2(IrConstantObject expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return "CONSTANT_OBJECT type=" + render(expression.getType()) + " constructor=" + renderReference(expression.getConstructor());
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantPrimitive, reason: merged with bridge method [inline-methods] */
    public String visitConstantPrimitive2(IrConstantPrimitive expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return "CONSTANT_PRIMITIVE type=" + render(expression.getType());
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitPropertyReference, reason: merged with bridge method [inline-methods] */
    public String visitPropertyReference2(IrPropertyReference expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        StringBuilder sb = new StringBuilder("PROPERTY_REFERENCE ");
        sb.append(FormatHelper.QUOTE + renderReference(expression.getSymbol()) + "' ");
        IrFieldSymbol field = expression.getField();
        sb.append("field=");
        if (field != null) {
            sb.append(FormatHelper.QUOTE + renderReference(field) + '\'');
        } else {
            sb.append("null");
        }
        sb.append(" getter=");
        IrSimpleFunctionSymbol getter = expression.getGetter();
        if (getter != null) {
            sb.append(FormatHelper.QUOTE + renderReference(getter) + '\'');
        } else {
            sb.append("null");
        }
        sb.append(" setter=");
        IrSimpleFunctionSymbol setter = expression.getSetter();
        if (setter != null) {
            sb.append(FormatHelper.QUOTE + renderReference(setter) + '\'');
        } else {
            sb.append("null");
        }
        sb.append(" ");
        sb.append("type=" + render(expression.getType()) + StringUtil.SPACE);
        sb.append("origin=" + expression.getOrigin());
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return StringsKt.trimEnd((CharSequence) string).toString();
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitLocalDelegatedPropertyReference, reason: merged with bridge method [inline-methods] */
    public String visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        StringBuilder sb = new StringBuilder("LOCAL_DELEGATED_PROPERTY_REFERENCE ");
        sb.append(FormatHelper.QUOTE + renderReference(expression.getSymbol()) + "' ");
        sb.append("delegate='" + renderReference(expression.getDelegate()) + "' ");
        sb.append("getter='" + renderReference(expression.getGetter()) + "' ");
        IrSimpleFunctionSymbol setter = expression.getSetter();
        sb.append("setter=");
        if (setter != null) {
            sb.append(FormatHelper.QUOTE + renderReference(setter) + '\'');
        } else {
            sb.append("null");
        }
        sb.append(" ");
        sb.append("type=" + render(expression.getType()) + StringUtil.SPACE);
        sb.append("origin=" + expression.getOrigin());
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return StringsKt.trimEnd((CharSequence) string).toString();
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunctionExpression, reason: merged with bridge method [inline-methods] */
    public String visitFunctionExpression2(IrFunctionExpression expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        StringBuilder sb = new StringBuilder();
        sb.append("FUN_EXPR type=" + render(expression.getType()) + " origin=" + expression.getOrigin());
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return StringsKt.trimEnd((CharSequence) string).toString();
    }
}
