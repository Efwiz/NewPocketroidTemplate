package org.jetbrains.kotlin.backend.jvm.lower;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.backend.common.FileLoweringPass;
import org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext;
import org.jetbrains.kotlin.backend.common.UtilsKt;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.backend.jvm.InlineClassAbi;
import org.jetbrains.kotlin.backend.jvm.InlineClassAbiKt;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.MemoizedValueClassAbstractReplacements;
import org.jetbrains.kotlin.ir.IrElementsKt;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationBase;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrErrorDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrExternalPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrLocalDelegatedProperty;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.declarations.IrPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrReturnTarget;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.declarations.IrTypeAlias;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrBlock;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrBranch;
import org.jetbrains.kotlin.ir.expressions.IrBreak;
import org.jetbrains.kotlin.ir.expressions.IrBreakContinue;
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
import org.jetbrains.kotlin.ir.expressions.IrFunctionExpression;
import org.jetbrains.kotlin.ir.expressions.IrGetClass;
import org.jetbrains.kotlin.ir.expressions.IrGetEnumValue;
import org.jetbrains.kotlin.ir.expressions.IrGetObjectValue;
import org.jetbrains.kotlin.ir.expressions.IrGetSingletonValue;
import org.jetbrains.kotlin.ir.expressions.IrInstanceInitializerCall;
import org.jetbrains.kotlin.ir.expressions.IrLocalDelegatedPropertyReference;
import org.jetbrains.kotlin.ir.expressions.IrLoop;
import org.jetbrains.kotlin.ir.expressions.IrMemberAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrPropertyReference;
import org.jetbrains.kotlin.ir.expressions.IrRawFunctionReference;
import org.jetbrains.kotlin.ir.expressions.IrReturn;
import org.jetbrains.kotlin.ir.expressions.IrSpreadElement;
import org.jetbrains.kotlin.ir.expressions.IrStatementContainer;
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
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.PatchDeclarationParentsKt;
import org.jetbrains.kotlin.ir.util.TransformKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: JvmValueClassAbstractLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000 \u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\b \u0018\u00002\u00020\u00012\u00020\u0002:\u0002Í\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0018\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0013H$J(\u0010\u0015\u001a\u00020\u00112\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0019\u001a\u00020\u001aH$J \u0010\u001b\u001a\u00020\u00172\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0014\u001a\u00020\u00172\u0006\u0010\u001c\u001a\u00020\u001dH&J\u0018\u0010\u001e\u001a\u00020\u00172\u0006\u0010\u001f\u001a\u00020\u00172\u0006\u0010\u0014\u001a\u00020\u0017H\u0002J\u0010\u0010 \u001a\u00020\u00112\u0006\u0010!\u001a\u00020\"H&J\u000e\u0010#\u001a\u00020\u00112\u0006\u0010$\u001a\u00020%J\u0010\u0010&\u001a\u00020\u00112\u0006\u0010'\u001a\u00020(H\u0014J\u001e\u0010)\u001a\b\u0012\u0004\u0012\u00020(0*2\u0006\u0010\u001f\u001a\u00020+2\u0006\u0010\u0014\u001a\u00020+H\u0002J\u0018\u0010,\u001a\n\u0012\u0004\u0012\u00020(\u0018\u00010*2\u0006\u0010\u001f\u001a\u00020\u0013H\u0004J\u001e\u0010-\u001a\b\u0012\u0004\u0012\u00020(0*2\u0006\u0010.\u001a\u00020+2\u0006\u0010\u0014\u001a\u00020\u0017H$J\u001e\u0010/\u001a\b\u0012\u0004\u0012\u00020(0*2\u0006\u0010\u001f\u001a\u00020\u00172\u0006\u0010\u0014\u001a\u00020\u0017H\u0002J0\u00100\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u000202\u0012\u0006\u0012\u0004\u0018\u000103010*2\u0006\u0010\u001f\u001a\u00020\u00132\n\u00104\u001a\u0006\u0012\u0002\b\u000305H\u0004J\u000e\u00106\u001a\u0002072\u0006\u0010!\u001a\u000208J\u000e\u00109\u001a\u0002032\u0006\u00104\u001a\u00020:J\u000e\u0010;\u001a\u00020<2\u0006\u0010=\u001a\u00020>J\u000e\u0010?\u001a\u00020<2\u0006\u0010=\u001a\u00020<J\u000e\u0010@\u001a\u00020A2\u0006\u0010B\u001a\u00020AJ\u000e\u0010C\u001a\u0002032\u0006\u0010D\u001a\u00020EJ\u000e\u0010F\u001a\u0002032\u0006\u0010D\u001a\u00020GJ\u0012\u0010H\u001a\u0002032\n\u00104\u001a\u0006\u0012\u0002\b\u00030IJ\u000e\u0010J\u001a\u00020K2\u0006\u0010L\u001a\u00020KJ\u0015\u0010M\u001a\u00020\u00112\u0006\u0010!\u001a\u00020(H ¢\u0006\u0002\bNJ\u000e\u0010O\u001a\u0002032\u0006\u00104\u001a\u00020PJ\u000e\u0010Q\u001a\u0002032\u0006\u00104\u001a\u00020RJ\u0012\u0010S\u001a\u0002032\n\u00104\u001a\u0006\u0012\u0002\b\u00030TJ\u000e\u0010U\u001a\u00020V2\u0006\u00104\u001a\u00020WJ\u000e\u0010X\u001a\u00020V2\u0006\u00104\u001a\u00020YJ\u000e\u0010Z\u001a\u00020V2\u0006\u00104\u001a\u00020[J\u000e\u0010\\\u001a\u00020V2\u0006\u00104\u001a\u00020VJ\u000e\u0010]\u001a\u0002072\u0006\u0010!\u001a\u00020+J\u000e\u0010^\u001a\u0002032\u0006\u00104\u001a\u00020_J\u0010\u0010`\u001a\u0002032\u0006\u00104\u001a\u00020aH\u0016J\u000e\u0010b\u001a\u0002032\u0006\u0010D\u001a\u00020cJ\u000e\u0010d\u001a\u0002072\u0006\u0010!\u001a\u00020eJ\u000e\u0010f\u001a\u0002032\u0006\u00104\u001a\u00020gJ\u000e\u0010h\u001a\u0002032\u0006\u00104\u001a\u00020iJ\u000e\u0010j\u001a\u0002032\u0006\u0010k\u001a\u00020lJ\u000e\u0010m\u001a\u0002032\u0006\u00104\u001a\u00020nJ\u000e\u0010o\u001a\u0002032\u0006\u00104\u001a\u00020pJ\u000e\u0010q\u001a\u0002032\u0006\u00104\u001a\u00020rJ\u000e\u0010s\u001a\u00020t2\u0006\u0010B\u001a\u00020tJ\u000e\u0010u\u001a\u0002032\u0006\u00104\u001a\u00020vJ\u000e\u0010w\u001a\u0002072\u0006\u0010!\u001a\u00020xJ\u000e\u0010y\u001a\u0002032\u0006\u00104\u001a\u00020zJ\u000e\u0010{\u001a\u0002072\u0006\u0010!\u001a\u00020|J\u000e\u0010}\u001a\u0002032\u0006\u00104\u001a\u00020~J\u000e\u0010\u007f\u001a\u0002032\u0006\u00104\u001a\u000203J\u0010\u0010\u0080\u0001\u001a\u00020<2\u0007\u0010=\u001a\u00030\u0081\u0001J\u0011\u0010\u0082\u0001\u001a\u00030\u0083\u00012\u0007\u0010!\u001a\u00030\u0083\u0001J\u0010\u0010\u0084\u0001\u001a\u0002032\u0007\u00104\u001a\u00030\u0085\u0001J\u0010\u0010\u0086\u0001\u001a\u0002032\u0007\u00104\u001a\u00030\u0087\u0001J\u0010\u0010\u0088\u0001\u001a\u0002032\u0007\u00104\u001a\u00030\u0089\u0001J\u0010\u0010\u008a\u0001\u001a\u0002032\u0007\u00104\u001a\u00030\u008b\u0001J\u0010\u0010\u008c\u0001\u001a\u0002032\u0007\u00104\u001a\u00030\u008d\u0001J\u0010\u0010\u008e\u0001\u001a\u0002032\u0007\u00104\u001a\u00030\u008f\u0001J\u0010\u0010\u0090\u0001\u001a\u0002072\u0007\u0010!\u001a\u00030\u0091\u0001J\u0010\u0010\u0092\u0001\u001a\u0002032\u0007\u00104\u001a\u00030\u0093\u0001J\u0010\u0010\u0094\u0001\u001a\u0002032\u0007\u0010k\u001a\u00030\u0095\u0001J\u0013\u0010\u0096\u0001\u001a\u0002032\n\u00104\u001a\u0006\u0012\u0002\b\u000305J\u0011\u0010\u0097\u0001\u001a\u00030\u0098\u00012\u0007\u0010!\u001a\u00030\u0098\u0001J\u0011\u0010\u0099\u0001\u001a\u00030\u009a\u00012\u0007\u0010!\u001a\u00030\u009a\u0001J\u0012\u0010\u009b\u0001\u001a\u00020\u00112\u0007\u0010\u009c\u0001\u001a\u000202H\u0016J\u0010\u0010\u009d\u0001\u001a\u0002032\u0007\u00104\u001a\u00030\u009e\u0001J\u0010\u0010\u009f\u0001\u001a\u0002032\u0007\u00104\u001a\u00030 \u0001J\u0010\u0010¡\u0001\u001a\u0002032\u0007\u00104\u001a\u00030¢\u0001J\u000f\u0010£\u0001\u001a\u0002072\u0006\u0010!\u001a\u00020\u0017J\u0010\u0010¤\u0001\u001a\u0002032\u0007\u00104\u001a\u00030¥\u0001J\u0012\u0010¦\u0001\u001a\u00030§\u00012\b\u0010¨\u0001\u001a\u00030§\u0001J\u0019\u0010©\u0001\u001a\u00020\u00112\b\u0010ª\u0001\u001a\u00030«\u0001H\u0000¢\u0006\u0003\b¬\u0001J\u0010\u0010\u00ad\u0001\u001a\u0002032\u0007\u00104\u001a\u00030®\u0001J\u0010\u0010¯\u0001\u001a\u0002032\u0007\u00104\u001a\u00030°\u0001J\u0010\u0010±\u0001\u001a\u00020<2\u0007\u0010=\u001a\u00030²\u0001J\u0010\u0010³\u0001\u001a\u0002032\u0007\u00104\u001a\u00030´\u0001J\u0011\u0010µ\u0001\u001a\u0002032\b\u0010¶\u0001\u001a\u00030·\u0001J\u0010\u0010¸\u0001\u001a\u0002072\u0007\u0010!\u001a\u00030¹\u0001J\u0010\u0010º\u0001\u001a\u0002032\u0007\u00104\u001a\u00030»\u0001J\u0010\u0010¼\u0001\u001a\u0002072\u0007\u0010!\u001a\u00030½\u0001J\u0010\u0010¾\u0001\u001a\u0002032\u0007\u00104\u001a\u00030¿\u0001J\u000f\u0010À\u0001\u001a\u0002072\u0006\u0010!\u001a\u000202J\u0010\u0010Á\u0001\u001a\u0002032\u0007\u00104\u001a\u00030Â\u0001J\u0010\u0010Ã\u0001\u001a\u0002032\u0007\u00104\u001a\u00030Ä\u0001J\u0010\u0010Å\u0001\u001a\u0002032\u0007\u0010k\u001a\u00030Æ\u0001J\u0010\u0010Ç\u0001\u001a\u0005\u0018\u00010È\u0001*\u00020\u0013H\u0002J\r\u0010É\u0001\u001a\u00020\u001a*\u00020\"H&J#\u0010Ê\u0001\u001a\u00020\u001a*\u00020\u00172\t\b\u0002\u0010Ë\u0001\u001a\u00020\u001a2\t\b\u0002\u0010Ì\u0001\u001a\u00020\u001aH\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0012\u0010\b\u001a\u00020\tX¦\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0012\u0010\f\u001a\u00020\rX¤\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000f¨\u0006Î\u0001"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/JvmValueClassAbstractLowering;", "Lorg/jetbrains/kotlin/backend/common/FileLoweringPass;", "Lorg/jetbrains/kotlin/backend/common/IrElementTransformerVoidWithContext;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "replacements", "Lorg/jetbrains/kotlin/backend/jvm/MemoizedValueClassAbstractReplacements;", "getReplacements", "()Lorg/jetbrains/kotlin/backend/jvm/MemoizedValueClassAbstractReplacements;", "specificMangle", "Lorg/jetbrains/kotlin/backend/jvm/lower/JvmValueClassAbstractLowering$SpecificMangle;", "getSpecificMangle", "()Lorg/jetbrains/kotlin/backend/jvm/lower/JvmValueClassAbstractLowering$SpecificMangle;", "addBindingsFor", "", "original", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "replacement", "createBridgeBody", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "target", "inverted", "", "createBridgeDeclaration", "mangledName", "Lorg/jetbrains/kotlin/name/Name;", "createBridgeFunction", "function", "handleSpecificNewClass", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "lower", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "postActionAfterTransformingClassDeclaration", "replacingDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "transformFlattenedConstructor", "", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "transformFunctionFlat", "transformSecondaryConstructorFlat", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "transformSimpleFunctionFlat", "typedArgumentList", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrMemberAccessExpression;", "visitAnonymousInitializerNew", "Lorg/jetbrains/kotlin/ir/IrStatement;", "Lorg/jetbrains/kotlin/ir/declarations/IrAnonymousInitializer;", "visitBlock", "Lorg/jetbrains/kotlin/ir/expressions/IrBlock;", "visitBlockBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "body", "Lorg/jetbrains/kotlin/ir/expressions/IrBlockBody;", "visitBody", "visitBranch", "Lorg/jetbrains/kotlin/ir/expressions/IrBranch;", ConfigConstants.CONFIG_BRANCH_SECTION, "visitBreak", "jump", "Lorg/jetbrains/kotlin/ir/expressions/IrBreak;", "visitBreakContinue", "Lorg/jetbrains/kotlin/ir/expressions/IrBreakContinue;", "visitCallableReference", "Lorg/jetbrains/kotlin/ir/expressions/IrCallableReference;", "visitCatch", "Lorg/jetbrains/kotlin/ir/expressions/IrCatch;", "aCatch", "visitClassNewDeclarationsWhenParallel", "visitClassNewDeclarationsWhenParallel$backend_jvm_lower", "visitClassReference", "Lorg/jetbrains/kotlin/ir/expressions/IrClassReference;", "visitComposite", "Lorg/jetbrains/kotlin/ir/expressions/IrComposite;", "visitConst", "Lorg/jetbrains/kotlin/ir/expressions/IrConst;", "visitConstantArray", "Lorg/jetbrains/kotlin/ir/expressions/IrConstantValue;", "Lorg/jetbrains/kotlin/ir/expressions/IrConstantArray;", "visitConstantObject", "Lorg/jetbrains/kotlin/ir/expressions/IrConstantObject;", "visitConstantPrimitive", "Lorg/jetbrains/kotlin/ir/expressions/IrConstantPrimitive;", "visitConstantValue", "visitConstructor", "visitConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "visitContainerExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrContainerExpression;", "visitContinue", "Lorg/jetbrains/kotlin/ir/expressions/IrContinue;", "visitDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationBase;", "visitDeclarationReference", "Lorg/jetbrains/kotlin/ir/expressions/IrDeclarationReference;", "visitDelegatingConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrDelegatingConstructorCall;", "visitDoWhileLoop", "loop", "Lorg/jetbrains/kotlin/ir/expressions/IrDoWhileLoop;", "visitDynamicExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrDynamicExpression;", "visitDynamicMemberExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrDynamicMemberExpression;", "visitDynamicOperatorExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrDynamicOperatorExpression;", "visitElseBranch", "Lorg/jetbrains/kotlin/ir/expressions/IrElseBranch;", "visitEnumConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrEnumConstructorCall;", "visitEnumEntry", "Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "visitErrorCallExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrErrorCallExpression;", "visitErrorDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrErrorDeclaration;", "visitErrorExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrErrorExpression;", "visitExpression", "visitExpressionBody", "Lorg/jetbrains/kotlin/ir/expressions/IrExpressionBody;", "visitExternalPackageFragment", "Lorg/jetbrains/kotlin/ir/declarations/IrExternalPackageFragment;", "visitFieldAccess", "Lorg/jetbrains/kotlin/ir/expressions/IrFieldAccessExpression;", "visitFunctionExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionExpression;", "visitGetClass", "Lorg/jetbrains/kotlin/ir/expressions/IrGetClass;", "visitGetEnumValue", "Lorg/jetbrains/kotlin/ir/expressions/IrGetEnumValue;", "visitGetObjectValue", "Lorg/jetbrains/kotlin/ir/expressions/IrGetObjectValue;", "visitInstanceInitializerCall", "Lorg/jetbrains/kotlin/ir/expressions/IrInstanceInitializerCall;", "visitLocalDelegatedProperty", "Lorg/jetbrains/kotlin/ir/declarations/IrLocalDelegatedProperty;", "visitLocalDelegatedPropertyReference", "Lorg/jetbrains/kotlin/ir/expressions/IrLocalDelegatedPropertyReference;", "visitLoop", "Lorg/jetbrains/kotlin/ir/expressions/IrLoop;", "visitMemberAccess", "visitModuleFragment", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "visitPackageFragment", "Lorg/jetbrains/kotlin/ir/declarations/IrPackageFragment;", "visitParameter", "parameter", "visitPropertyReference", "Lorg/jetbrains/kotlin/ir/expressions/IrPropertyReference;", "visitRawFunctionReference", "Lorg/jetbrains/kotlin/ir/expressions/IrRawFunctionReference;", "visitReturn", "Lorg/jetbrains/kotlin/ir/expressions/IrReturn;", "visitSimpleFunction", "visitSingletonReference", "Lorg/jetbrains/kotlin/ir/expressions/IrGetSingletonValue;", "visitSpreadElement", "Lorg/jetbrains/kotlin/ir/expressions/IrSpreadElement;", "spread", "visitStatementContainer", "container", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementContainer;", "visitStatementContainer$backend_jvm_lower", "visitSuspendableExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrSuspendableExpression;", "visitSuspensionPoint", "Lorg/jetbrains/kotlin/ir/expressions/IrSuspensionPoint;", "visitSyntheticBody", "Lorg/jetbrains/kotlin/ir/expressions/IrSyntheticBody;", "visitThrow", "Lorg/jetbrains/kotlin/ir/expressions/IrThrow;", "visitTry", "aTry", "Lorg/jetbrains/kotlin/ir/expressions/IrTry;", "visitTypeAlias", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeAlias;", "visitTypeOperator", "Lorg/jetbrains/kotlin/ir/expressions/IrTypeOperatorCall;", "visitTypeParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "visitValueAccess", "Lorg/jetbrains/kotlin/ir/expressions/IrValueAccessExpression;", "visitValueParameterNew", "visitVararg", "Lorg/jetbrains/kotlin/ir/expressions/IrVararg;", "visitWhen", "Lorg/jetbrains/kotlin/ir/expressions/IrWhen;", "visitWhileLoop", "Lorg/jetbrains/kotlin/ir/expressions/IrWhileLoop;", "hashSuffix", "", "isSpecificLoweringLogicApplicable", "signatureRequiresMangling", "includeInline", "includeMFVC", "SpecificMangle", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class JvmValueClassAbstractLowering extends IrElementTransformerVoidWithContext implements FileLoweringPass {
    private final JvmBackendContext context;

    /* JADX INFO: compiled from: JvmValueClassAbstractLowering.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0084\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/JvmValueClassAbstractLowering$SpecificMangle;", "", "(Ljava/lang/String;I)V", "Inline", "MultiField", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
    protected enum SpecificMangle {
        Inline,
        MultiField;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<SpecificMangle> getEntries() {
            return $ENTRIES;
        }
    }

    /* JADX INFO: compiled from: JvmValueClassAbstractLowering.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[SpecificMangle.values().length];
            try {
                iArr[SpecificMangle.Inline.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[SpecificMangle.MultiField.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    protected abstract void addBindingsFor(IrFunction original, IrFunction replacement);

    protected abstract void createBridgeBody(IrSimpleFunction source, IrSimpleFunction target, IrFunction original, boolean inverted);

    public abstract IrSimpleFunction createBridgeDeclaration(IrSimpleFunction source, IrSimpleFunction replacement, Name mangledName);

    public abstract MemoizedValueClassAbstractReplacements getReplacements();

    protected abstract SpecificMangle getSpecificMangle();

    public abstract void handleSpecificNewClass(IrClass declaration);

    public abstract boolean isSpecificLoweringLogicApplicable(IrClass irClass);

    protected void postActionAfterTransformingClassDeclaration(IrDeclaration replacingDeclaration) {
        Intrinsics.checkNotNullParameter(replacingDeclaration, "replacingDeclaration");
    }

    protected abstract List<IrDeclaration> transformSecondaryConstructorFlat(IrConstructor constructor, IrSimpleFunction replacement);

    public abstract void visitClassNewDeclarationsWhenParallel$backend_jvm_lower(IrDeclaration declaration);

    public final JvmBackendContext getContext() {
        return this.context;
    }

    public JvmValueClassAbstractLowering(JvmBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    @Override // org.jetbrains.kotlin.backend.common.FileLoweringPass
    public final void lower(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        JvmValueClassAbstractLowering jvmValueClassAbstractLowering = this;
        jvmValueClassAbstractLowering.unsafeEnterScope(irFile);
        transformChildrenVoid(irFile);
        Unit unit = Unit.INSTANCE;
        jvmValueClassAbstractLowering.unsafeLeaveScope();
    }

    protected final List<IrDeclaration> transformFunctionFlat(IrFunction function) {
        IrConstructor irConstructor;
        IrConstructor replacementForRegularClassConstructor;
        Intrinsics.checkNotNullParameter(function, "function");
        boolean z = function instanceof IrConstructor;
        if (z) {
            IrConstructor irConstructor2 = (IrConstructor) function;
            if (irConstructor2.getIsPrimary() && isSpecificLoweringLogicApplicable(AdditionalIrUtilsKt.getConstructedClass(irConstructor2))) {
                return null;
            }
        }
        IrSimpleFunction replacementFunction = getReplacements().getReplacementFunction(function);
        if (replacementFunction == null) {
            if (z && (replacementForRegularClassConstructor = getReplacements().getReplacementForRegularClassConstructor((irConstructor = (IrConstructor) function))) != null) {
                addBindingsFor(function, replacementForRegularClassConstructor);
                return transformFlattenedConstructor(irConstructor, replacementForRegularClassConstructor);
            }
            transformChildrenVoid(function);
            if (function instanceof IrSimpleFunction) {
                IrSimpleFunction irSimpleFunction = (IrSimpleFunction) function;
                irSimpleFunction.setOverriddenSymbols(getReplacements().replaceOverriddenSymbols(irSimpleFunction));
            }
            return null;
        }
        boolean z2 = function instanceof IrSimpleFunction;
        if (z2) {
            List<IrSimpleFunctionSymbol> overriddenSymbols = ((IrSimpleFunction) function).getOverriddenSymbols();
            if (!(overriddenSymbols instanceof Collection) || !overriddenSymbols.isEmpty()) {
                Iterator<T> it = overriddenSymbols.iterator();
                while (it.hasNext()) {
                    if (IrUtilsKt.getParentAsClass(((IrSimpleFunctionSymbol) it.next()).getOwner()).getIsFun()) {
                        String strHashSuffix = hashSuffix(function);
                        if (strHashSuffix == null) {
                            break;
                        }
                        String strAsString = function.getName().asString();
                        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                        if (!StringsKt.endsWith$default(strAsString, strHashSuffix, false, 2, (Object) null)) {
                            break;
                        }
                        transformChildrenVoid(function);
                        return null;
                    }
                }
            }
        }
        addBindingsFor(function, replacementFunction);
        if (z2) {
            return transformSimpleFunctionFlat((IrSimpleFunction) function, replacementFunction);
        }
        if (z) {
            return transformSecondaryConstructorFlat((IrConstructor) function, replacementFunction);
        }
        throw new IllegalStateException();
    }

    private final List<IrDeclaration> transformFlattenedConstructor(IrConstructor function, IrConstructor replacement) {
        IrBody irBodyTransform;
        for (IrValueParameter irValueParameter : replacement.getValueParameters()) {
            IrExpressionBody defaultValue = irValueParameter.getDefaultValue();
            if (defaultValue != null) {
            }
            visitParameter(irValueParameter);
        }
        UtilsKt.push(getAllScopes(), createScope(replacement));
        IrBody body = function.getBody();
        IrBody irBody = null;
        if (body != null && (irBodyTransform = body.transform((IrElementTransformer<? super Object>) this, (Object) null)) != null) {
            irBody = (IrBody) PatchDeclarationParentsKt.patchDeclarationParents(irBodyTransform, replacement);
        }
        replacement.setBody(irBody);
        UtilsKt.pop(getAllScopes());
        return CollectionsKt.listOf(replacement);
    }

    private final String hashSuffix(IrFunction irFunction) {
        return InlineClassAbi.INSTANCE.hashSuffix(irFunction, this.context.getConfig().getFunctionsWithInlineClassReturnTypesMangled(), this.context.getConfig().getUseOldManglingSchemeForFunctionsWithInlineClassesInSignatures());
    }

    public void visitParameter(IrValueParameter parameter) {
        Intrinsics.checkNotNullParameter(parameter, "parameter");
        transformChildrenVoid(parameter);
    }

    @Override // org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext
    public final IrStatement visitValueParameterNew(IrValueParameter declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        visitParameter(declaration);
        return declaration;
    }

    private final List<IrDeclaration> transformSimpleFunctionFlat(IrSimpleFunction function, IrSimpleFunction replacement) {
        IrBody irBodyTransform;
        for (IrValueParameter irValueParameter : replacement.getValueParameters()) {
            IrExpressionBody defaultValue = irValueParameter.getDefaultValue();
            if (defaultValue != null) {
            }
            visitParameter(irValueParameter);
        }
        UtilsKt.push(getAllScopes(), createScope(replacement));
        IrBody body = function.getBody();
        IrBody irBody = null;
        if (body != null && (irBodyTransform = body.transform((IrElementTransformer<? super Object>) this, (Object) null)) != null) {
            irBody = (IrBody) PatchDeclarationParentsKt.patchDeclarationParents(irBodyTransform, replacement);
        }
        replacement.setBody(irBody);
        UtilsKt.pop(getAllScopes());
        IrDeclarationsKt.copyAttributes(replacement, function);
        return (function.getOverriddenSymbols().isEmpty() || replacement.getDispatchReceiverParameter() != null) ? CollectionsKt.listOf(replacement) : CollectionsKt.listOf((Object[]) new IrSimpleFunction[]{replacement, createBridgeFunction(function, replacement)});
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrExpression visitReturn(IrReturn expression) {
        IrConstructor replacementForRegularClassConstructor;
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrReturnTarget owner = expression.getReturnTargetSymbol().getOwner();
        IrFunction irFunction = owner instanceof IrFunction ? (IrFunction) owner : null;
        if (irFunction != null) {
            String strHashSuffix = hashSuffix(irFunction);
            if (strHashSuffix != null) {
                String strAsString = irFunction.getName().asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                if (StringsKt.endsWith$default(strAsString, strHashSuffix, false, 2, (Object) null)) {
                    return super.visitReturn(expression);
                }
            }
            MemoizedValueClassAbstractReplacements replacements = getReplacements();
            IrSimpleFunction replacementFunction = replacements.getReplacementFunction(irFunction);
            if (replacementFunction != null) {
                replacementForRegularClassConstructor = replacementFunction;
            } else {
                replacementForRegularClassConstructor = irFunction instanceof IrConstructor ? replacements.getReplacementForRegularClassConstructor((IrConstructor) irFunction) : null;
            }
            if (replacementForRegularClassConstructor != null) {
                return ExpressionHelpersKt.irReturn(LowerUtilsKt.createIrBuilder(this.context, replacementForRegularClassConstructor.getSymbol(), expression.getStartOffset(), expression.getEndOffset()), expression.getValue().transform((IrElementTransformer<? super Object>) this, (Object) null));
            }
        }
        return super.visitReturn(expression);
    }

    public final void visitStatementContainer$backend_jvm_lower(IrStatementContainer container) {
        List<IrDeclaration> listListOf;
        Intrinsics.checkNotNullParameter(container, "container");
        List<IrStatement> statements = container.getStatements();
        int iReplaceInPlace = 0;
        while (iReplaceInPlace < statements.size()) {
            IrStatement irStatement = statements.get(iReplaceInPlace);
            if (irStatement instanceof IrFunction) {
                JvmValueClassAbstractLowering jvmValueClassAbstractLowering = this;
                jvmValueClassAbstractLowering.unsafeEnterScope((IrSymbolOwner) irStatement);
                listListOf = transformFunctionFlat((IrFunction) irStatement);
                jvmValueClassAbstractLowering.unsafeLeaveScope();
            } else {
                listListOf = CollectionsKt.listOf(IrElementsKt.transformStatement(irStatement, this));
            }
            List<IrDeclaration> listListOf2 = listListOf == null ? CollectionsKt.listOf(irStatement) : listListOf;
            ArrayList arrayList = new ArrayList();
            for (Object obj : listListOf2) {
                if (obj instanceof IrDeclaration) {
                    arrayList.add(obj);
                }
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                postActionAfterTransformingClassDeclaration((IrDeclaration) it.next());
            }
            iReplaceInPlace = TransformKt.replaceInPlace(statements, listListOf, iReplaceInPlace);
        }
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitContainerExpression(IrContainerExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        visitStatementContainer$backend_jvm_lower(expression);
        return expression;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrBody visitBlockBody(IrBlockBody body) {
        Intrinsics.checkNotNullParameter(body, "body");
        visitStatementContainer$backend_jvm_lower(body);
        return body;
    }

    @Override // org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext
    public final IrStatement visitAnonymousInitializerNew(IrAnonymousInitializer declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrDeclarationParent parent = declaration.getParent();
        IrClass irClass = parent instanceof IrClass ? (IrClass) parent : null;
        if (irClass != null && isSpecificLoweringLogicApplicable(irClass) && !declaration.getIsStatic()) {
            return declaration;
        }
        return super.visitAnonymousInitializerNew(declaration);
    }

    /* JADX WARN: Code duplicated, block: B:19:0x0052  */
    /* JADX WARN: Code duplicated, block: B:21:0x005f  */
    /* JADX WARN: Code duplicated, block: B:22:0x0083  */
    /* JADX WARN: Code duplicated, block: B:24:0x0089  */
    /* JADX WARN: Code duplicated, block: B:25:0x00ad  */
    private final IrSimpleFunction createBridgeFunction(IrSimpleFunction function, IrSimpleFunction replacement) {
        boolean zSignatureRequiresMangling;
        Name name2;
        IrSimpleFunction irSimpleFunction;
        IrSimpleFunction irSimpleFunction2 = function;
        if (IrUtilsKt.isValueClassTypedEquals(irSimpleFunction2)) {
            name2 = InlineClassAbi.INSTANCE.mangledNameFor(this.context, irSimpleFunction2, false, false);
        } else if (function.getIsFakeOverride() && InheritedDefaultMethodsOnClassesLoweringKt.findInterfaceImplementation(function, this.context.getConfig().getJvmDefaultMode()) == null) {
            irSimpleFunction = replacement;
            if (AdditionalIrUtilsKt.isGetter(irSimpleFunction)) {
                IrPropertySymbol correspondingPropertySymbol = replacement.getCorrespondingPropertySymbol();
                Intrinsics.checkNotNull(correspondingPropertySymbol);
                String strAsString = correspondingPropertySymbol.getOwner().getName().asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                name2 = Name.identifier(JvmAbi.getterName(strAsString));
                Intrinsics.checkNotNullExpressionValue(name2, "identifier(...)");
            } else if (AdditionalIrUtilsKt.isSetter(irSimpleFunction)) {
                IrPropertySymbol correspondingPropertySymbol2 = replacement.getCorrespondingPropertySymbol();
                Intrinsics.checkNotNull(correspondingPropertySymbol2);
                String strAsString2 = correspondingPropertySymbol2.getOwner().getName().asString();
                Intrinsics.checkNotNullExpressionValue(strAsString2, "asString(...)");
                name2 = Name.identifier(JvmAbi.setterName(strAsString2));
                Intrinsics.checkNotNullExpressionValue(name2, "identifier(...)");
            } else {
                name2 = function.getName();
            }
        } else {
            int i = WhenMappings.$EnumSwitchMapping$0[getSpecificMangle().ordinal()];
            if (i == 1) {
                zSignatureRequiresMangling = signatureRequiresMangling(function, true, false);
            } else {
                if (i != 2) {
                    throw new NoWhenBranchMatchedException();
                }
                zSignatureRequiresMangling = signatureRequiresMangling(function, false, true);
            }
            if (zSignatureRequiresMangling) {
                name2 = replacement.getName();
            } else {
                irSimpleFunction = replacement;
                if (AdditionalIrUtilsKt.isGetter(irSimpleFunction)) {
                    IrPropertySymbol correspondingPropertySymbol3 = replacement.getCorrespondingPropertySymbol();
                    Intrinsics.checkNotNull(correspondingPropertySymbol3);
                    String strAsString3 = correspondingPropertySymbol3.getOwner().getName().asString();
                    Intrinsics.checkNotNullExpressionValue(strAsString3, "asString(...)");
                    name2 = Name.identifier(JvmAbi.getterName(strAsString3));
                    Intrinsics.checkNotNullExpressionValue(name2, "identifier(...)");
                } else if (AdditionalIrUtilsKt.isSetter(irSimpleFunction)) {
                    IrPropertySymbol correspondingPropertySymbol4 = replacement.getCorrespondingPropertySymbol();
                    Intrinsics.checkNotNull(correspondingPropertySymbol4);
                    String strAsString4 = correspondingPropertySymbol4.getOwner().getName().asString();
                    Intrinsics.checkNotNullExpressionValue(strAsString4, "asString(...)");
                    name2 = Name.identifier(JvmAbi.setterName(strAsString4));
                    Intrinsics.checkNotNullExpressionValue(name2, "identifier(...)");
                } else {
                    name2 = function.getName();
                }
            }
        }
        IrSimpleFunction irSimpleFunctionCreateBridgeDeclaration = createBridgeDeclaration(function, replacement, name2);
        irSimpleFunctionCreateBridgeDeclaration.setOverriddenSymbols(replacement.getOverriddenSymbols());
        if (irSimpleFunctionCreateBridgeDeclaration.getIsFakeOverride() && isSpecificLoweringLogicApplicable(IrUtilsKt.getParentAsClass(irSimpleFunctionCreateBridgeDeclaration))) {
            createBridgeBody(replacement, irSimpleFunctionCreateBridgeDeclaration, irSimpleFunction2, true);
        } else {
            createBridgeBody(irSimpleFunctionCreateBridgeDeclaration, replacement, irSimpleFunction2, false);
        }
        return irSimpleFunctionCreateBridgeDeclaration;
    }

    private final boolean signatureRequiresMangling(IrSimpleFunction irSimpleFunction, boolean z, boolean z2) {
        List<IrValueParameter> fullValueParameterList = InlineClassAbiKt.getFullValueParameterList(irSimpleFunction);
        if ((fullValueParameterList instanceof Collection) && fullValueParameterList.isEmpty()) {
            return this.context.getConfig().getFunctionsWithInlineClassReturnTypesMangled() ? false : false;
        }
        Iterator<T> it = fullValueParameterList.iterator();
        while (it.hasNext()) {
            if (InlineClassAbiKt.getRequiresMangling(((IrValueParameter) it.next()).getType(), z, z2)) {
            }
        }
        if (this.context.getConfig().getFunctionsWithInlineClassReturnTypesMangled() || !InlineClassAbiKt.getRequiresMangling(irSimpleFunction.getReturnType(), z, false)) {
        }
        return true;
    }

    protected final List<Pair<IrValueParameter, IrExpression>> typedArgumentList(IrFunction function, IrMemberAccessExpression<?> expression) {
        Intrinsics.checkNotNullParameter(function, "function");
        Intrinsics.checkNotNullParameter(expression, "expression");
        Pair[] pairArr = new Pair[2];
        IrValueParameter dispatchReceiverParameter = function.getDispatchReceiverParameter();
        pairArr[0] = dispatchReceiverParameter != null ? TuplesKt.to(dispatchReceiverParameter, expression.getDispatchReceiver()) : null;
        IrValueParameter extensionReceiverParameter = function.getExtensionReceiverParameter();
        pairArr[1] = extensionReceiverParameter != null ? TuplesKt.to(extensionReceiverParameter, expression.getExtensionReceiver()) : null;
        List listListOfNotNull = CollectionsKt.listOfNotNull((Object[]) pairArr);
        List<IrValueParameter> valueParameters = function.getValueParameters();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameters, 10));
        for (IrValueParameter irValueParameter : valueParameters) {
            arrayList.add(TuplesKt.to(irValueParameter, expression.getValueArgument(irValueParameter.getIndex())));
        }
        return CollectionsKt.plus((Collection) listListOfNotNull, (Iterable) arrayList);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrModuleFragment visitModuleFragment(IrModuleFragment declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return super.visitModuleFragment(declaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrPackageFragment visitPackageFragment(IrPackageFragment declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return super.visitPackageFragment(declaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrExternalPackageFragment visitExternalPackageFragment(IrExternalPackageFragment declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return super.visitExternalPackageFragment(declaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrStatement visitDeclaration(IrDeclarationBase declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return super.visitDeclaration(declaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrStatement visitSimpleFunction(IrSimpleFunction declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return super.visitSimpleFunction(declaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrStatement visitConstructor(IrConstructor declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return super.visitConstructor(declaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrStatement visitLocalDelegatedProperty(IrLocalDelegatedProperty declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return super.visitLocalDelegatedProperty(declaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrStatement visitEnumEntry(IrEnumEntry declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return super.visitEnumEntry(declaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrStatement visitTypeParameter(IrTypeParameter declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return super.visitTypeParameter(declaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrStatement visitTypeAlias(IrTypeAlias declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return super.visitTypeAlias(declaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrBody visitBody(IrBody body) {
        Intrinsics.checkNotNullParameter(body, "body");
        return super.visitBody(body);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrBody visitExpressionBody(IrExpressionBody body) {
        Intrinsics.checkNotNullParameter(body, "body");
        return super.visitExpressionBody(body);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrBody visitSyntheticBody(IrSyntheticBody body) {
        Intrinsics.checkNotNullParameter(body, "body");
        return super.visitSyntheticBody(body);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrExpression visitSuspendableExpression(IrSuspendableExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return super.visitSuspendableExpression(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrExpression visitSuspensionPoint(IrSuspensionPoint expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return super.visitSuspensionPoint(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrExpression visitExpression(IrExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return super.visitExpression(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrExpression visitConst(IrConst<?> expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return super.visitConst(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrConstantValue visitConstantValue(IrConstantValue expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return super.visitConstantValue(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrConstantValue visitConstantObject(IrConstantObject expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return super.visitConstantObject(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrConstantValue visitConstantPrimitive(IrConstantPrimitive expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return super.visitConstantPrimitive(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrConstantValue visitConstantArray(IrConstantArray expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return super.visitConstantArray(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrExpression visitVararg(IrVararg expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return super.visitVararg(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrSpreadElement visitSpreadElement(IrSpreadElement spread) {
        Intrinsics.checkNotNullParameter(spread, "spread");
        return super.visitSpreadElement(spread);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrExpression visitBlock(IrBlock expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return super.visitBlock(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrExpression visitComposite(IrComposite expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return super.visitComposite(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrExpression visitDeclarationReference(IrDeclarationReference expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return super.visitDeclarationReference(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrExpression visitSingletonReference(IrGetSingletonValue expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return super.visitSingletonReference(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrExpression visitGetObjectValue(IrGetObjectValue expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return super.visitGetObjectValue(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrExpression visitGetEnumValue(IrGetEnumValue expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return super.visitGetEnumValue(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrExpression visitValueAccess(IrValueAccessExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return super.visitValueAccess(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrExpression visitFieldAccess(IrFieldAccessExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return super.visitFieldAccess(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrExpression visitMemberAccess(IrMemberAccessExpression<?> expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return super.visitMemberAccess(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrExpression visitConstructorCall(IrConstructorCall expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return super.visitConstructorCall(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrExpression visitDelegatingConstructorCall(IrDelegatingConstructorCall expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return super.visitDelegatingConstructorCall(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrExpression visitEnumConstructorCall(IrEnumConstructorCall expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return super.visitEnumConstructorCall(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrExpression visitGetClass(IrGetClass expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return super.visitGetClass(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrExpression visitCallableReference(IrCallableReference<?> expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return super.visitCallableReference(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrExpression visitPropertyReference(IrPropertyReference expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return super.visitPropertyReference(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrExpression visitLocalDelegatedPropertyReference(IrLocalDelegatedPropertyReference expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return super.visitLocalDelegatedPropertyReference(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrExpression visitRawFunctionReference(IrRawFunctionReference expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return super.visitRawFunctionReference(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrExpression visitFunctionExpression(IrFunctionExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return super.visitFunctionExpression(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrExpression visitClassReference(IrClassReference expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return super.visitClassReference(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrExpression visitInstanceInitializerCall(IrInstanceInitializerCall expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return super.visitInstanceInitializerCall(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrExpression visitTypeOperator(IrTypeOperatorCall expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return super.visitTypeOperator(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrExpression visitWhen(IrWhen expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return super.visitWhen(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrBranch visitBranch(IrBranch branch) {
        Intrinsics.checkNotNullParameter(branch, "branch");
        return super.visitBranch(branch);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrElseBranch visitElseBranch(IrElseBranch branch) {
        Intrinsics.checkNotNullParameter(branch, "branch");
        return super.visitElseBranch(branch);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrExpression visitLoop(IrLoop loop) {
        Intrinsics.checkNotNullParameter(loop, "loop");
        return super.visitLoop(loop);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrExpression visitWhileLoop(IrWhileLoop loop) {
        Intrinsics.checkNotNullParameter(loop, "loop");
        return super.visitWhileLoop(loop);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrExpression visitDoWhileLoop(IrDoWhileLoop loop) {
        Intrinsics.checkNotNullParameter(loop, "loop");
        return super.visitDoWhileLoop(loop);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrExpression visitTry(IrTry aTry) {
        Intrinsics.checkNotNullParameter(aTry, "aTry");
        return super.visitTry(aTry);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrCatch visitCatch(IrCatch aCatch) {
        Intrinsics.checkNotNullParameter(aCatch, "aCatch");
        return super.visitCatch(aCatch);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrExpression visitBreakContinue(IrBreakContinue jump) {
        Intrinsics.checkNotNullParameter(jump, "jump");
        return super.visitBreakContinue(jump);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrExpression visitBreak(IrBreak jump) {
        Intrinsics.checkNotNullParameter(jump, "jump");
        return super.visitBreak(jump);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrExpression visitContinue(IrContinue jump) {
        Intrinsics.checkNotNullParameter(jump, "jump");
        return super.visitContinue(jump);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrExpression visitThrow(IrThrow expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return super.visitThrow(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrExpression visitDynamicExpression(IrDynamicExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return super.visitDynamicExpression(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrExpression visitDynamicOperatorExpression(IrDynamicOperatorExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return super.visitDynamicOperatorExpression(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrExpression visitDynamicMemberExpression(IrDynamicMemberExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return super.visitDynamicMemberExpression(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrStatement visitErrorDeclaration(IrErrorDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return super.visitErrorDeclaration(declaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrExpression visitErrorExpression(IrErrorExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return super.visitErrorExpression(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrExpression visitErrorCallExpression(IrErrorCallExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return super.visitErrorCallExpression(expression);
    }
}
