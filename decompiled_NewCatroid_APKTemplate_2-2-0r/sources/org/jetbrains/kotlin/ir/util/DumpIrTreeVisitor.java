package org.jetbrains.kotlin.ir.util;

import androidx.exifinterface.media.ExifInterface;
import com.google.android.gms.cast.MediaTrack;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.declarations.IrAnnotationContainer;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationBase;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
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
import org.jetbrains.kotlin.ir.expressions.IrConstantArray;
import org.jetbrains.kotlin.ir.expressions.IrConstantObject;
import org.jetbrains.kotlin.ir.expressions.IrConstantPrimitive;
import org.jetbrains.kotlin.ir.expressions.IrConstantValue;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCallTypeArgumentsKt;
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
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.utils.Printer;

/* JADX INFO: compiled from: DumpIrTree.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000¨\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u001b\u0012\n\u0010\u0004\u001a\u00060\u0005j\u0002`\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u0010\u0010\u000e\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\u0010\u0010\u0011\u001a\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J\u0010\u0010\u0014\u001a\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J\u0014\u0010\u0014\u001a\u00020\u00022\n\u0010\u0012\u001a\u0006\u0012\u0002\b\u00030\u0015H\u0002J\u0017\u0010\u0016\u001a\u00020\u00022\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00020\u0018H\u0082\bJ\u001f\u0010\u0016\u001a\u00020\u00022\u0006\u0010\u0019\u001a\u00020\u00032\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00020\u0018H\u0082\bJ\u0018\u0010\u001a\u001a\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u0003H\u0016J\u0018\u0010\u001d\u001a\u00020\u00022\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u001c\u001a\u00020\u0003H\u0016J\u0018\u0010 \u001a\u00020\u00022\u0006\u0010!\u001a\u00020\"2\u0006\u0010\u001c\u001a\u00020\u0003H\u0016J\u0018\u0010#\u001a\u00020\u00022\u0006\u0010\u0012\u001a\u00020$2\u0006\u0010\u001c\u001a\u00020\u0003H\u0016J\u0018\u0010%\u001a\u00020\u00022\u0006\u0010\u0012\u001a\u00020&2\u0006\u0010\u001c\u001a\u00020\u0003H\u0016J\u0018\u0010'\u001a\u00020\u00022\u0006\u0010!\u001a\u00020(2\u0006\u0010\u001c\u001a\u00020\u0003H\u0016J\u0018\u0010)\u001a\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u001c\u001a\u00020\u0003H\u0016J\u0018\u0010*\u001a\u00020\u00022\u0006\u0010+\u001a\u00020,2\u0006\u0010\u001c\u001a\u00020\u0003H\u0016J\u0018\u0010-\u001a\u00020\u00022\u0006\u0010\u0012\u001a\u00020.2\u0006\u0010\u001c\u001a\u00020\u0003H\u0016J\u0018\u0010/\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u0002002\u0006\u0010\u001c\u001a\u00020\u0003H\u0016J\u0018\u00101\u001a\u00020\u00022\u0006\u0010!\u001a\u0002022\u0006\u0010\u001c\u001a\u00020\u0003H\u0016J\u0018\u00103\u001a\u00020\u00022\u0006\u0010\u0012\u001a\u0002042\u0006\u0010\u001c\u001a\u00020\u0003H\u0016J\u0018\u00105\u001a\u00020\u00022\u0006\u0010!\u001a\u0002062\u0006\u0010\u001c\u001a\u00020\u0003H\u0016J\u0018\u00107\u001a\u00020\u00022\u0006\u0010!\u001a\u0002082\u0006\u0010\u001c\u001a\u00020\u0003H\u0016J\u0018\u00109\u001a\u00020\u00022\u0006\u0010!\u001a\u00020:2\u0006\u0010\u001c\u001a\u00020\u0003H\u0016J\u0018\u0010;\u001a\u00020\u00022\u0006\u0010\u0012\u001a\u00020<2\u0006\u0010\u001c\u001a\u00020\u0003H\u0016J\u001c\u0010=\u001a\u00020\u00022\n\u0010\u0012\u001a\u0006\u0012\u0002\b\u00030\u00152\u0006\u0010\u001c\u001a\u00020\u0003H\u0016J\u0018\u0010>\u001a\u00020\u00022\u0006\u0010!\u001a\u00020?2\u0006\u0010\u001c\u001a\u00020\u0003H\u0016J\u0018\u0010@\u001a\u00020\u00022\u0006\u0010!\u001a\u00020A2\u0006\u0010\u001c\u001a\u00020\u0003H\u0016J\u0018\u0010B\u001a\u00020\u00022\u0006\u0010\u0012\u001a\u00020C2\u0006\u0010\u001c\u001a\u00020\u0003H\u0016J\u0018\u0010D\u001a\u00020\u00022\u0006\u0010!\u001a\u00020E2\u0006\u0010\u001c\u001a\u00020\u0003H\u0016J\u0018\u0010F\u001a\u00020\u00022\u0006\u0010G\u001a\u00020H2\u0006\u0010\u001c\u001a\u00020\u0003H\u0016J\u0018\u0010I\u001a\u00020\u00022\u0006\u0010!\u001a\u00020J2\u0006\u0010\u001c\u001a\u00020\u0003H\u0016J\u0018\u0010K\u001a\u00020\u00022\u0006\u0010\u0012\u001a\u00020L2\u0006\u0010\u001c\u001a\u00020\u0003H\u0016J\u0018\u0010M\u001a\u00020\u00022\u0006\u0010!\u001a\u00020N2\u0006\u0010\u001c\u001a\u00020\u0003H\u0016J\u0018\u0010O\u001a\u00020\u00022\u0006\u0010\u0012\u001a\u00020P2\u0006\u0010\u001c\u001a\u00020\u0003H\u0016J\u0018\u0010Q\u001a\u00020\u00022\u0006\u0010+\u001a\u00020R2\u0006\u0010\u001c\u001a\u00020\u0003H\u0016J\u0018\u0010S\u001a\u00020\r*\u00020T2\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u0003H\u0002J\u0012\u0010U\u001a\u00020\u0002*\b\u0012\u0004\u0012\u0002000VH\u0002J\u0018\u0010W\u001a\u00020\u0002*\u0002002\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u0003H\u0002J\u0018\u0010W\u001a\u00020\u0002*\u00020T2\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u0003H\u0002J5\u0010X\u001a\u00020\u0002\"\u0004\b\u0000\u0010Y*\b\u0012\u0004\u0012\u0002HY0Z2\u0006\u0010[\u001a\u00020\u00032\u0012\u0010\\\u001a\u000e\u0012\u0004\u0012\u0002HY\u0012\u0004\u0012\u00020\u00020]H\u0082\bJ#\u0010^\u001a\u00020\u0002*\u0002002\u0006\u0010\u0019\u001a\u00020\u00032\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00020\u0018H\u0082\bJ\u0012\u0010_\u001a\b\u0012\u0004\u0012\u00020N0V*\u00020(H\u0002J\u001a\u0010`\u001a\b\u0012\u0004\u0012\u00020\u00030V*\u00020a2\u0006\u0010b\u001a\u00020cH\u0002J\u001e\u0010`\u001a\b\u0012\u0004\u0012\u00020\u00030V*\u0006\u0012\u0002\b\u00030\u00152\u0006\u0010b\u001a\u00020cH\u0002J\u0018\u0010d\u001a\b\u0012\u0004\u0012\u00020e0V*\b\u0012\u0004\u0012\u00020e0VH\u0002J\f\u0010f\u001a\u00020\u0003*\u00020gH\u0002J\u0018\u0010h\u001a\u00020\u0003*\u0006\u0012\u0002\b\u00030\u00152\u0006\u0010i\u001a\u00020cH\u0002J\u0014\u0010j\u001a\u00020\u0003*\u00020\u00032\u0006\u0010\u0019\u001a\u00020\u0003H\u0002R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006k"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/DumpIrTreeVisitor;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;", "", "", "out", "Ljava/lang/Appendable;", "Lkotlin/text/Appendable;", "options", "Lorg/jetbrains/kotlin/ir/util/DumpIrTreeOptions;", "(Ljava/lang/Appendable;Lorg/jetbrains/kotlin/ir/util/DumpIrTreeOptions;)V", "elementRenderer", "Lorg/jetbrains/kotlin/ir/util/RenderIrElementVisitor;", "printer", "Lorg/jetbrains/kotlin/utils/Printer;", "dumpAnnotations", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/ir/declarations/IrAnnotationContainer;", "dumpConstructorValueArguments", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "dumpTypeArguments", "Lorg/jetbrains/kotlin/ir/expressions/IrMemberAccessExpression;", "indented", "body", "Lkotlin/Function0;", CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME, "visitBlock", "Lorg/jetbrains/kotlin/ir/expressions/IrBlock;", "data", "visitBranch", ConfigConstants.CONFIG_BRANCH_SECTION, "Lorg/jetbrains/kotlin/ir/expressions/IrBranch;", "visitClass", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "visitConstantArray", "Lorg/jetbrains/kotlin/ir/expressions/IrConstantArray;", "visitConstantObject", "Lorg/jetbrains/kotlin/ir/expressions/IrConstantObject;", "visitConstructor", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "visitConstructorCall", "visitDoWhileLoop", "loop", "Lorg/jetbrains/kotlin/ir/expressions/IrDoWhileLoop;", "visitDynamicOperatorExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrDynamicOperatorExpression;", "visitElement", "Lorg/jetbrains/kotlin/ir/IrElement;", "visitEnumEntry", "Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "visitErrorCallExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrErrorCallExpression;", "visitExternalPackageFragment", "Lorg/jetbrains/kotlin/ir/declarations/IrExternalPackageFragment;", "visitField", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "visitFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "visitGetField", "Lorg/jetbrains/kotlin/ir/expressions/IrGetField;", "visitMemberAccess", "visitModuleFragment", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "visitProperty", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "visitSetField", "Lorg/jetbrains/kotlin/ir/expressions/IrSetField;", "visitSimpleFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "visitTry", "aTry", "Lorg/jetbrains/kotlin/ir/expressions/IrTry;", "visitTypeAlias", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeAlias;", "visitTypeOperator", "Lorg/jetbrains/kotlin/ir/expressions/IrTypeOperatorCall;", "visitTypeParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "visitWhen", "Lorg/jetbrains/kotlin/ir/expressions/IrWhen;", "visitWhileLoop", "Lorg/jetbrains/kotlin/ir/expressions/IrWhileLoop;", ArchiveStreamFactory.DUMP, "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "dumpElements", "", "dumpInternal", "dumpItems", ExifInterface.GPS_DIRECTION_TRUE, "", MediaTrack.ROLE_CAPTION, "renderElement", "Lkotlin/Function1;", "dumpLabeledElementWith", "getFullTypeParametersList", "getTypeParameterNames", "Lorg/jetbrains/kotlin/ir/declarations/IrSymbolOwner;", "expectedCount", "", "ordered", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "render", "Lorg/jetbrains/kotlin/ir/types/IrType;", "renderTypeArgument", "index", "withLabel", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DumpIrTreeVisitor implements IrElementVisitor<Unit, String> {
    private final RenderIrElementVisitor elementRenderer;
    private final DumpIrTreeOptions options;
    private final Printer printer;

    public DumpIrTreeVisitor(Appendable out, DumpIrTreeOptions options) {
        Intrinsics.checkNotNullParameter(out, "out");
        Intrinsics.checkNotNullParameter(options, "options");
        this.options = options;
        this.printer = new Printer(out, "  ");
        this.elementRenderer = new RenderIrElementVisitor(options);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitAnonymousInitializer */
    public /* bridge */ /* synthetic */ Unit visitAnonymousInitializer2(IrAnonymousInitializer irAnonymousInitializer, String str) {
        visitAnonymousInitializer2(irAnonymousInitializer, str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitAnonymousInitializer, reason: avoid collision after fix types in other method */
    public void visitAnonymousInitializer2(IrAnonymousInitializer irAnonymousInitializer, String str) {
        IrElementVisitor.DefaultImpls.visitAnonymousInitializer(this, irAnonymousInitializer, str);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBlock */
    public /* bridge */ /* synthetic */ Unit visitBlock2(IrBlock irBlock, String str) {
        visitBlock2(irBlock, str);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBlockBody */
    public /* bridge */ /* synthetic */ Unit visitBlockBody2(IrBlockBody irBlockBody, String str) {
        visitBlockBody2(irBlockBody, str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitBlockBody, reason: avoid collision after fix types in other method */
    public void visitBlockBody2(IrBlockBody irBlockBody, String str) {
        IrElementVisitor.DefaultImpls.visitBlockBody(this, irBlockBody, str);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBody */
    public /* bridge */ /* synthetic */ Unit visitBody2(IrBody irBody, String str) {
        visitBody2(irBody, str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitBody, reason: avoid collision after fix types in other method */
    public void visitBody2(IrBody irBody, String str) {
        IrElementVisitor.DefaultImpls.visitBody(this, irBody, str);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBranch */
    public /* bridge */ /* synthetic */ Unit visitBranch2(IrBranch irBranch, String str) {
        visitBranch2(irBranch, str);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBreak */
    public /* bridge */ /* synthetic */ Unit visitBreak2(IrBreak irBreak, String str) {
        visitBreak2(irBreak, str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitBreak, reason: avoid collision after fix types in other method */
    public void visitBreak2(IrBreak irBreak, String str) {
        IrElementVisitor.DefaultImpls.visitBreak(this, irBreak, str);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBreakContinue */
    public /* bridge */ /* synthetic */ Unit visitBreakContinue2(IrBreakContinue irBreakContinue, String str) {
        visitBreakContinue2(irBreakContinue, str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitBreakContinue, reason: avoid collision after fix types in other method */
    public void visitBreakContinue2(IrBreakContinue irBreakContinue, String str) {
        IrElementVisitor.DefaultImpls.visitBreakContinue(this, irBreakContinue, str);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitCall */
    public /* bridge */ /* synthetic */ Unit visitCall2(IrCall irCall, String str) {
        visitCall2(irCall, str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitCall, reason: avoid collision after fix types in other method */
    public void visitCall2(IrCall irCall, String str) {
        IrElementVisitor.DefaultImpls.visitCall(this, irCall, str);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public /* bridge */ /* synthetic */ Unit visitCallableReference(IrCallableReference irCallableReference, String str) {
        visitCallableReference2((IrCallableReference<?>) irCallableReference, str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitCallableReference, reason: avoid collision after fix types in other method */
    public void visitCallableReference2(IrCallableReference<?> irCallableReference, String str) {
        IrElementVisitor.DefaultImpls.visitCallableReference(this, irCallableReference, str);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitCatch */
    public /* bridge */ /* synthetic */ Unit visitCatch2(IrCatch irCatch, String str) {
        visitCatch2(irCatch, str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitCatch, reason: avoid collision after fix types in other method */
    public void visitCatch2(IrCatch irCatch, String str) {
        IrElementVisitor.DefaultImpls.visitCatch(this, irCatch, str);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitClass */
    public /* bridge */ /* synthetic */ Unit visitClass2(IrClass irClass, String str) {
        visitClass2(irClass, str);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitClassReference */
    public /* bridge */ /* synthetic */ Unit visitClassReference2(IrClassReference irClassReference, String str) {
        visitClassReference2(irClassReference, str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitClassReference, reason: avoid collision after fix types in other method */
    public void visitClassReference2(IrClassReference irClassReference, String str) {
        IrElementVisitor.DefaultImpls.visitClassReference(this, irClassReference, str);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitComposite */
    public /* bridge */ /* synthetic */ Unit visitComposite2(IrComposite irComposite, String str) {
        visitComposite2(irComposite, str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitComposite, reason: avoid collision after fix types in other method */
    public void visitComposite2(IrComposite irComposite, String str) {
        IrElementVisitor.DefaultImpls.visitComposite(this, irComposite, str);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public /* bridge */ /* synthetic */ Unit visitConst(IrConst irConst, String str) {
        visitConst2((IrConst<?>) irConst, str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitConst, reason: avoid collision after fix types in other method */
    public void visitConst2(IrConst<?> irConst, String str) {
        IrElementVisitor.DefaultImpls.visitConst(this, irConst, str);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantArray */
    public /* bridge */ /* synthetic */ Unit visitConstantArray2(IrConstantArray irConstantArray, String str) {
        visitConstantArray2(irConstantArray, str);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantObject */
    public /* bridge */ /* synthetic */ Unit visitConstantObject2(IrConstantObject irConstantObject, String str) {
        visitConstantObject2(irConstantObject, str);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantPrimitive */
    public /* bridge */ /* synthetic */ Unit visitConstantPrimitive2(IrConstantPrimitive irConstantPrimitive, String str) {
        visitConstantPrimitive2(irConstantPrimitive, str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitConstantPrimitive, reason: avoid collision after fix types in other method */
    public void visitConstantPrimitive2(IrConstantPrimitive irConstantPrimitive, String str) {
        IrElementVisitor.DefaultImpls.visitConstantPrimitive(this, irConstantPrimitive, str);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantValue */
    public /* bridge */ /* synthetic */ Unit visitConstantValue2(IrConstantValue irConstantValue, String str) {
        visitConstantValue2(irConstantValue, str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitConstantValue, reason: avoid collision after fix types in other method */
    public void visitConstantValue2(IrConstantValue irConstantValue, String str) {
        IrElementVisitor.DefaultImpls.visitConstantValue(this, irConstantValue, str);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstructor */
    public /* bridge */ /* synthetic */ Unit visitConstructor2(IrConstructor irConstructor, String str) {
        visitConstructor2(irConstructor, str);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstructorCall */
    public /* bridge */ /* synthetic */ Unit visitConstructorCall2(IrConstructorCall irConstructorCall, String str) {
        visitConstructorCall2(irConstructorCall, str);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitContainerExpression */
    public /* bridge */ /* synthetic */ Unit visitContainerExpression2(IrContainerExpression irContainerExpression, String str) {
        visitContainerExpression2(irContainerExpression, str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitContainerExpression, reason: avoid collision after fix types in other method */
    public void visitContainerExpression2(IrContainerExpression irContainerExpression, String str) {
        IrElementVisitor.DefaultImpls.visitContainerExpression(this, irContainerExpression, str);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitContinue */
    public /* bridge */ /* synthetic */ Unit visitContinue2(IrContinue irContinue, String str) {
        visitContinue2(irContinue, str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitContinue, reason: avoid collision after fix types in other method */
    public void visitContinue2(IrContinue irContinue, String str) {
        IrElementVisitor.DefaultImpls.visitContinue(this, irContinue, str);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDeclaration */
    public /* bridge */ /* synthetic */ Unit visitDeclaration2(IrDeclarationBase irDeclarationBase, String str) {
        visitDeclaration2(irDeclarationBase, str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitDeclaration, reason: avoid collision after fix types in other method */
    public void visitDeclaration2(IrDeclarationBase irDeclarationBase, String str) {
        IrElementVisitor.DefaultImpls.visitDeclaration(this, irDeclarationBase, str);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDeclarationReference */
    public /* bridge */ /* synthetic */ Unit visitDeclarationReference2(IrDeclarationReference irDeclarationReference, String str) {
        visitDeclarationReference2(irDeclarationReference, str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitDeclarationReference, reason: avoid collision after fix types in other method */
    public void visitDeclarationReference2(IrDeclarationReference irDeclarationReference, String str) {
        IrElementVisitor.DefaultImpls.visitDeclarationReference(this, irDeclarationReference, str);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDelegatingConstructorCall */
    public /* bridge */ /* synthetic */ Unit visitDelegatingConstructorCall2(IrDelegatingConstructorCall irDelegatingConstructorCall, String str) {
        visitDelegatingConstructorCall2(irDelegatingConstructorCall, str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitDelegatingConstructorCall, reason: avoid collision after fix types in other method */
    public void visitDelegatingConstructorCall2(IrDelegatingConstructorCall irDelegatingConstructorCall, String str) {
        IrElementVisitor.DefaultImpls.visitDelegatingConstructorCall(this, irDelegatingConstructorCall, str);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDoWhileLoop */
    public /* bridge */ /* synthetic */ Unit visitDoWhileLoop2(IrDoWhileLoop irDoWhileLoop, String str) {
        visitDoWhileLoop2(irDoWhileLoop, str);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDynamicExpression */
    public /* bridge */ /* synthetic */ Unit visitDynamicExpression2(IrDynamicExpression irDynamicExpression, String str) {
        visitDynamicExpression2(irDynamicExpression, str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitDynamicExpression, reason: avoid collision after fix types in other method */
    public void visitDynamicExpression2(IrDynamicExpression irDynamicExpression, String str) {
        IrElementVisitor.DefaultImpls.visitDynamicExpression(this, irDynamicExpression, str);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDynamicMemberExpression */
    public /* bridge */ /* synthetic */ Unit visitDynamicMemberExpression2(IrDynamicMemberExpression irDynamicMemberExpression, String str) {
        visitDynamicMemberExpression2(irDynamicMemberExpression, str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitDynamicMemberExpression, reason: avoid collision after fix types in other method */
    public void visitDynamicMemberExpression2(IrDynamicMemberExpression irDynamicMemberExpression, String str) {
        IrElementVisitor.DefaultImpls.visitDynamicMemberExpression(this, irDynamicMemberExpression, str);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDynamicOperatorExpression */
    public /* bridge */ /* synthetic */ Unit visitDynamicOperatorExpression2(IrDynamicOperatorExpression irDynamicOperatorExpression, String str) {
        visitDynamicOperatorExpression2(irDynamicOperatorExpression, str);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitElement */
    public /* bridge */ /* synthetic */ Unit visitElement2(IrElement irElement, String str) {
        visitElement2(irElement, str);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitElseBranch */
    public /* bridge */ /* synthetic */ Unit visitElseBranch2(IrElseBranch irElseBranch, String str) {
        visitElseBranch2(irElseBranch, str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitElseBranch, reason: avoid collision after fix types in other method */
    public void visitElseBranch2(IrElseBranch irElseBranch, String str) {
        IrElementVisitor.DefaultImpls.visitElseBranch(this, irElseBranch, str);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitEnumConstructorCall */
    public /* bridge */ /* synthetic */ Unit visitEnumConstructorCall2(IrEnumConstructorCall irEnumConstructorCall, String str) {
        visitEnumConstructorCall2(irEnumConstructorCall, str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitEnumConstructorCall, reason: avoid collision after fix types in other method */
    public void visitEnumConstructorCall2(IrEnumConstructorCall irEnumConstructorCall, String str) {
        IrElementVisitor.DefaultImpls.visitEnumConstructorCall(this, irEnumConstructorCall, str);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitEnumEntry */
    public /* bridge */ /* synthetic */ Unit visitEnumEntry2(IrEnumEntry irEnumEntry, String str) {
        visitEnumEntry2(irEnumEntry, str);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitErrorCallExpression */
    public /* bridge */ /* synthetic */ Unit visitErrorCallExpression2(IrErrorCallExpression irErrorCallExpression, String str) {
        visitErrorCallExpression2(irErrorCallExpression, str);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitErrorDeclaration */
    public /* bridge */ /* synthetic */ Unit visitErrorDeclaration2(IrErrorDeclaration irErrorDeclaration, String str) {
        visitErrorDeclaration2(irErrorDeclaration, str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitErrorDeclaration, reason: avoid collision after fix types in other method */
    public void visitErrorDeclaration2(IrErrorDeclaration irErrorDeclaration, String str) {
        IrElementVisitor.DefaultImpls.visitErrorDeclaration(this, irErrorDeclaration, str);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitErrorExpression */
    public /* bridge */ /* synthetic */ Unit visitErrorExpression2(IrErrorExpression irErrorExpression, String str) {
        visitErrorExpression2(irErrorExpression, str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitErrorExpression, reason: avoid collision after fix types in other method */
    public void visitErrorExpression2(IrErrorExpression irErrorExpression, String str) {
        IrElementVisitor.DefaultImpls.visitErrorExpression(this, irErrorExpression, str);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExpression */
    public /* bridge */ /* synthetic */ Unit visitExpression2(IrExpression irExpression, String str) {
        visitExpression2(irExpression, str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitExpression, reason: avoid collision after fix types in other method */
    public void visitExpression2(IrExpression irExpression, String str) {
        IrElementVisitor.DefaultImpls.visitExpression(this, irExpression, str);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExpressionBody */
    public /* bridge */ /* synthetic */ Unit visitExpressionBody2(IrExpressionBody irExpressionBody, String str) {
        visitExpressionBody2(irExpressionBody, str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitExpressionBody, reason: avoid collision after fix types in other method */
    public void visitExpressionBody2(IrExpressionBody irExpressionBody, String str) {
        IrElementVisitor.DefaultImpls.visitExpressionBody(this, irExpressionBody, str);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExternalPackageFragment */
    public /* bridge */ /* synthetic */ Unit visitExternalPackageFragment2(IrExternalPackageFragment irExternalPackageFragment, String str) {
        visitExternalPackageFragment2(irExternalPackageFragment, str);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitField */
    public /* bridge */ /* synthetic */ Unit visitField2(IrField irField, String str) {
        visitField2(irField, str);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFieldAccess */
    public /* bridge */ /* synthetic */ Unit visitFieldAccess2(IrFieldAccessExpression irFieldAccessExpression, String str) {
        visitFieldAccess2(irFieldAccessExpression, str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitFieldAccess, reason: avoid collision after fix types in other method */
    public void visitFieldAccess2(IrFieldAccessExpression irFieldAccessExpression, String str) {
        IrElementVisitor.DefaultImpls.visitFieldAccess(this, irFieldAccessExpression, str);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFile */
    public /* bridge */ /* synthetic */ Unit visitFile2(IrFile irFile, String str) {
        visitFile2(irFile, str);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunction */
    public /* bridge */ /* synthetic */ Unit visitFunction2(IrFunction irFunction, String str) {
        visitFunction2(irFunction, str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitFunction, reason: avoid collision after fix types in other method */
    public void visitFunction2(IrFunction irFunction, String str) {
        IrElementVisitor.DefaultImpls.visitFunction(this, irFunction, str);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunctionAccess */
    public /* bridge */ /* synthetic */ Unit visitFunctionAccess2(IrFunctionAccessExpression irFunctionAccessExpression, String str) {
        visitFunctionAccess2(irFunctionAccessExpression, str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitFunctionAccess, reason: avoid collision after fix types in other method */
    public void visitFunctionAccess2(IrFunctionAccessExpression irFunctionAccessExpression, String str) {
        IrElementVisitor.DefaultImpls.visitFunctionAccess(this, irFunctionAccessExpression, str);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunctionExpression */
    public /* bridge */ /* synthetic */ Unit visitFunctionExpression2(IrFunctionExpression irFunctionExpression, String str) {
        visitFunctionExpression2(irFunctionExpression, str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitFunctionExpression, reason: avoid collision after fix types in other method */
    public void visitFunctionExpression2(IrFunctionExpression irFunctionExpression, String str) {
        IrElementVisitor.DefaultImpls.visitFunctionExpression(this, irFunctionExpression, str);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunctionReference */
    public /* bridge */ /* synthetic */ Unit visitFunctionReference2(IrFunctionReference irFunctionReference, String str) {
        visitFunctionReference2(irFunctionReference, str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitFunctionReference, reason: avoid collision after fix types in other method */
    public void visitFunctionReference2(IrFunctionReference irFunctionReference, String str) {
        IrElementVisitor.DefaultImpls.visitFunctionReference(this, irFunctionReference, str);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetClass */
    public /* bridge */ /* synthetic */ Unit visitGetClass2(IrGetClass irGetClass, String str) {
        visitGetClass2(irGetClass, str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitGetClass, reason: avoid collision after fix types in other method */
    public void visitGetClass2(IrGetClass irGetClass, String str) {
        IrElementVisitor.DefaultImpls.visitGetClass(this, irGetClass, str);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetEnumValue */
    public /* bridge */ /* synthetic */ Unit visitGetEnumValue2(IrGetEnumValue irGetEnumValue, String str) {
        visitGetEnumValue2(irGetEnumValue, str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitGetEnumValue, reason: avoid collision after fix types in other method */
    public void visitGetEnumValue2(IrGetEnumValue irGetEnumValue, String str) {
        IrElementVisitor.DefaultImpls.visitGetEnumValue(this, irGetEnumValue, str);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetField */
    public /* bridge */ /* synthetic */ Unit visitGetField2(IrGetField irGetField, String str) {
        visitGetField2(irGetField, str);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetObjectValue */
    public /* bridge */ /* synthetic */ Unit visitGetObjectValue2(IrGetObjectValue irGetObjectValue, String str) {
        visitGetObjectValue2(irGetObjectValue, str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitGetObjectValue, reason: avoid collision after fix types in other method */
    public void visitGetObjectValue2(IrGetObjectValue irGetObjectValue, String str) {
        IrElementVisitor.DefaultImpls.visitGetObjectValue(this, irGetObjectValue, str);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetValue */
    public /* bridge */ /* synthetic */ Unit visitGetValue2(IrGetValue irGetValue, String str) {
        visitGetValue2(irGetValue, str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitGetValue, reason: avoid collision after fix types in other method */
    public void visitGetValue2(IrGetValue irGetValue, String str) {
        IrElementVisitor.DefaultImpls.visitGetValue(this, irGetValue, str);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitInstanceInitializerCall */
    public /* bridge */ /* synthetic */ Unit visitInstanceInitializerCall2(IrInstanceInitializerCall irInstanceInitializerCall, String str) {
        visitInstanceInitializerCall2(irInstanceInitializerCall, str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitInstanceInitializerCall, reason: avoid collision after fix types in other method */
    public void visitInstanceInitializerCall2(IrInstanceInitializerCall irInstanceInitializerCall, String str) {
        IrElementVisitor.DefaultImpls.visitInstanceInitializerCall(this, irInstanceInitializerCall, str);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitLocalDelegatedProperty */
    public /* bridge */ /* synthetic */ Unit visitLocalDelegatedProperty2(IrLocalDelegatedProperty irLocalDelegatedProperty, String str) {
        visitLocalDelegatedProperty2(irLocalDelegatedProperty, str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitLocalDelegatedProperty, reason: avoid collision after fix types in other method */
    public void visitLocalDelegatedProperty2(IrLocalDelegatedProperty irLocalDelegatedProperty, String str) {
        IrElementVisitor.DefaultImpls.visitLocalDelegatedProperty(this, irLocalDelegatedProperty, str);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitLocalDelegatedPropertyReference */
    public /* bridge */ /* synthetic */ Unit visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, String str) {
        visitLocalDelegatedPropertyReference2(irLocalDelegatedPropertyReference, str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitLocalDelegatedPropertyReference, reason: avoid collision after fix types in other method */
    public void visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, String str) {
        IrElementVisitor.DefaultImpls.visitLocalDelegatedPropertyReference(this, irLocalDelegatedPropertyReference, str);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitLoop */
    public /* bridge */ /* synthetic */ Unit visitLoop2(IrLoop irLoop, String str) {
        visitLoop2(irLoop, str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitLoop, reason: avoid collision after fix types in other method */
    public void visitLoop2(IrLoop irLoop, String str) {
        IrElementVisitor.DefaultImpls.visitLoop(this, irLoop, str);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public /* bridge */ /* synthetic */ Unit visitMemberAccess(IrMemberAccessExpression irMemberAccessExpression, String str) {
        visitMemberAccess2((IrMemberAccessExpression<?>) irMemberAccessExpression, str);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitModuleFragment */
    public /* bridge */ /* synthetic */ Unit visitModuleFragment2(IrModuleFragment irModuleFragment, String str) {
        visitModuleFragment2(irModuleFragment, str);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitPackageFragment */
    public /* bridge */ /* synthetic */ Unit visitPackageFragment2(IrPackageFragment irPackageFragment, String str) {
        visitPackageFragment2(irPackageFragment, str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitPackageFragment, reason: avoid collision after fix types in other method */
    public void visitPackageFragment2(IrPackageFragment irPackageFragment, String str) {
        IrElementVisitor.DefaultImpls.visitPackageFragment(this, irPackageFragment, str);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitProperty */
    public /* bridge */ /* synthetic */ Unit visitProperty2(IrProperty irProperty, String str) {
        visitProperty2(irProperty, str);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitPropertyReference */
    public /* bridge */ /* synthetic */ Unit visitPropertyReference2(IrPropertyReference irPropertyReference, String str) {
        visitPropertyReference2(irPropertyReference, str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitPropertyReference, reason: avoid collision after fix types in other method */
    public void visitPropertyReference2(IrPropertyReference irPropertyReference, String str) {
        IrElementVisitor.DefaultImpls.visitPropertyReference(this, irPropertyReference, str);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitRawFunctionReference */
    public /* bridge */ /* synthetic */ Unit visitRawFunctionReference2(IrRawFunctionReference irRawFunctionReference, String str) {
        visitRawFunctionReference2(irRawFunctionReference, str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitRawFunctionReference, reason: avoid collision after fix types in other method */
    public void visitRawFunctionReference2(IrRawFunctionReference irRawFunctionReference, String str) {
        IrElementVisitor.DefaultImpls.visitRawFunctionReference(this, irRawFunctionReference, str);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitReturn */
    public /* bridge */ /* synthetic */ Unit visitReturn2(IrReturn irReturn, String str) {
        visitReturn2(irReturn, str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitReturn, reason: avoid collision after fix types in other method */
    public void visitReturn2(IrReturn irReturn, String str) {
        IrElementVisitor.DefaultImpls.visitReturn(this, irReturn, str);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitScript */
    public /* bridge */ /* synthetic */ Unit visitScript2(IrScript irScript, String str) {
        visitScript2(irScript, str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitScript, reason: avoid collision after fix types in other method */
    public void visitScript2(IrScript irScript, String str) {
        IrElementVisitor.DefaultImpls.visitScript(this, irScript, str);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSetField */
    public /* bridge */ /* synthetic */ Unit visitSetField2(IrSetField irSetField, String str) {
        visitSetField2(irSetField, str);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSetValue */
    public /* bridge */ /* synthetic */ Unit visitSetValue2(IrSetValue irSetValue, String str) {
        visitSetValue2(irSetValue, str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitSetValue, reason: avoid collision after fix types in other method */
    public void visitSetValue2(IrSetValue irSetValue, String str) {
        IrElementVisitor.DefaultImpls.visitSetValue(this, irSetValue, str);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSimpleFunction */
    public /* bridge */ /* synthetic */ Unit visitSimpleFunction2(IrSimpleFunction irSimpleFunction, String str) {
        visitSimpleFunction2(irSimpleFunction, str);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSingletonReference */
    public /* bridge */ /* synthetic */ Unit visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, String str) {
        visitSingletonReference2(irGetSingletonValue, str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitSingletonReference, reason: avoid collision after fix types in other method */
    public void visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, String str) {
        IrElementVisitor.DefaultImpls.visitSingletonReference(this, irGetSingletonValue, str);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSpreadElement */
    public /* bridge */ /* synthetic */ Unit visitSpreadElement2(IrSpreadElement irSpreadElement, String str) {
        visitSpreadElement2(irSpreadElement, str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitSpreadElement, reason: avoid collision after fix types in other method */
    public void visitSpreadElement2(IrSpreadElement irSpreadElement, String str) {
        IrElementVisitor.DefaultImpls.visitSpreadElement(this, irSpreadElement, str);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitStringConcatenation */
    public /* bridge */ /* synthetic */ Unit visitStringConcatenation2(IrStringConcatenation irStringConcatenation, String str) {
        visitStringConcatenation2(irStringConcatenation, str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitStringConcatenation, reason: avoid collision after fix types in other method */
    public void visitStringConcatenation2(IrStringConcatenation irStringConcatenation, String str) {
        IrElementVisitor.DefaultImpls.visitStringConcatenation(this, irStringConcatenation, str);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSuspendableExpression */
    public /* bridge */ /* synthetic */ Unit visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, String str) {
        visitSuspendableExpression2(irSuspendableExpression, str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitSuspendableExpression, reason: avoid collision after fix types in other method */
    public void visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, String str) {
        IrElementVisitor.DefaultImpls.visitSuspendableExpression(this, irSuspendableExpression, str);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSuspensionPoint */
    public /* bridge */ /* synthetic */ Unit visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, String str) {
        visitSuspensionPoint2(irSuspensionPoint, str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitSuspensionPoint, reason: avoid collision after fix types in other method */
    public void visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, String str) {
        IrElementVisitor.DefaultImpls.visitSuspensionPoint(this, irSuspensionPoint, str);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSyntheticBody */
    public /* bridge */ /* synthetic */ Unit visitSyntheticBody2(IrSyntheticBody irSyntheticBody, String str) {
        visitSyntheticBody2(irSyntheticBody, str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitSyntheticBody, reason: avoid collision after fix types in other method */
    public void visitSyntheticBody2(IrSyntheticBody irSyntheticBody, String str) {
        IrElementVisitor.DefaultImpls.visitSyntheticBody(this, irSyntheticBody, str);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitThrow */
    public /* bridge */ /* synthetic */ Unit visitThrow2(IrThrow irThrow, String str) {
        visitThrow2(irThrow, str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitThrow, reason: avoid collision after fix types in other method */
    public void visitThrow2(IrThrow irThrow, String str) {
        IrElementVisitor.DefaultImpls.visitThrow(this, irThrow, str);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTry */
    public /* bridge */ /* synthetic */ Unit visitTry2(IrTry irTry, String str) {
        visitTry2(irTry, str);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeAlias */
    public /* bridge */ /* synthetic */ Unit visitTypeAlias2(IrTypeAlias irTypeAlias, String str) {
        visitTypeAlias2(irTypeAlias, str);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeOperator */
    public /* bridge */ /* synthetic */ Unit visitTypeOperator2(IrTypeOperatorCall irTypeOperatorCall, String str) {
        visitTypeOperator2(irTypeOperatorCall, str);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeParameter */
    public /* bridge */ /* synthetic */ Unit visitTypeParameter2(IrTypeParameter irTypeParameter, String str) {
        visitTypeParameter2(irTypeParameter, str);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitValueAccess */
    public /* bridge */ /* synthetic */ Unit visitValueAccess2(IrValueAccessExpression irValueAccessExpression, String str) {
        visitValueAccess2(irValueAccessExpression, str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitValueAccess, reason: avoid collision after fix types in other method */
    public void visitValueAccess2(IrValueAccessExpression irValueAccessExpression, String str) {
        IrElementVisitor.DefaultImpls.visitValueAccess(this, irValueAccessExpression, str);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitValueParameter */
    public /* bridge */ /* synthetic */ Unit visitValueParameter2(IrValueParameter irValueParameter, String str) {
        visitValueParameter2(irValueParameter, str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitValueParameter, reason: avoid collision after fix types in other method */
    public void visitValueParameter2(IrValueParameter irValueParameter, String str) {
        IrElementVisitor.DefaultImpls.visitValueParameter(this, irValueParameter, str);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitVararg */
    public /* bridge */ /* synthetic */ Unit visitVararg2(IrVararg irVararg, String str) {
        visitVararg2(irVararg, str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitVararg, reason: avoid collision after fix types in other method */
    public void visitVararg2(IrVararg irVararg, String str) {
        IrElementVisitor.DefaultImpls.visitVararg(this, irVararg, str);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitVariable */
    public /* bridge */ /* synthetic */ Unit visitVariable2(IrVariable irVariable, String str) {
        visitVariable2(irVariable, str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitVariable, reason: avoid collision after fix types in other method */
    public void visitVariable2(IrVariable irVariable, String str) {
        IrElementVisitor.DefaultImpls.visitVariable(this, irVariable, str);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitWhen */
    public /* bridge */ /* synthetic */ Unit visitWhen2(IrWhen irWhen, String str) {
        visitWhen2(irWhen, str);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitWhileLoop */
    public /* bridge */ /* synthetic */ Unit visitWhileLoop2(IrWhileLoop irWhileLoop, String str) {
        visitWhileLoop2(irWhileLoop, str);
        return Unit.INSTANCE;
    }

    public /* synthetic */ DumpIrTreeVisitor(Appendable appendable, DumpIrTreeOptions dumpIrTreeOptions, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(appendable, (i & 2) != 0 ? new DumpIrTreeOptions(false, false, false, false, false, false, 63, null) : dumpIrTreeOptions);
    }

    private final String render(IrType irType) {
        return this.elementRenderer.renderType(irType);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final List<IrDeclaration> ordered(List<? extends IrDeclaration> list) {
        return this.options.getStableOrder() ? DumpIrTreeKt.stableOrdered(list) : list;
    }

    /* JADX INFO: renamed from: visitModuleFragment, reason: avoid collision after fix types in other method */
    public void visitModuleFragment2(IrModuleFragment declaration, String data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(data, "data");
        this.printer.println(withLabel((String) declaration.accept(this.elementRenderer, null), data));
        this.printer.pushIndent();
        dumpElements(declaration.getFiles());
        this.printer.popIndent();
    }

    /* JADX INFO: renamed from: visitExternalPackageFragment, reason: avoid collision after fix types in other method */
    public void visitExternalPackageFragment2(IrExternalPackageFragment declaration, String data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(data, "data");
        this.printer.println(withLabel((String) declaration.accept(this.elementRenderer, null), data));
        this.printer.pushIndent();
        dumpElements(ordered(declaration.getDeclarations()));
        this.printer.popIndent();
    }

    /* JADX INFO: renamed from: visitFile, reason: avoid collision after fix types in other method */
    public void visitFile2(IrFile declaration, String data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(data, "data");
        this.printer.println(withLabel((String) declaration.accept(this.elementRenderer, null), data));
        this.printer.pushIndent();
        dumpAnnotations(declaration);
        dumpElements(ordered(declaration.getDeclarations()));
        this.printer.popIndent();
    }

    /* JADX INFO: renamed from: visitClass, reason: avoid collision after fix types in other method */
    public void visitClass2(IrClass declaration, String data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(data, "data");
        this.printer.println(withLabel((String) declaration.accept(this.elementRenderer, null), data));
        this.printer.pushIndent();
        dumpAnnotations(declaration);
        List<IrClassSymbol> sealedSubclasses = declaration.getSealedSubclasses();
        if (!sealedSubclasses.isEmpty()) {
            this.printer.println("sealedSubclasses:");
            this.printer.pushIndent();
            Iterator<T> it = sealedSubclasses.iterator();
            while (it.hasNext()) {
                dump$default(this, (IrClassSymbol) it.next(), null, 1, null);
            }
            this.printer.popIndent();
        }
        IrValueParameter thisReceiver = declaration.getThisReceiver();
        if (thisReceiver != null) {
            thisReceiver.accept(this, "$this");
        }
        dumpElements(declaration.getTypeParameters());
        dumpElements(ordered(declaration.getDeclarations()));
        this.printer.popIndent();
    }

    /* JADX INFO: renamed from: visitTypeAlias, reason: avoid collision after fix types in other method */
    public void visitTypeAlias2(IrTypeAlias declaration, String data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(data, "data");
        this.printer.println(withLabel((String) declaration.accept(this.elementRenderer, null), data));
        this.printer.pushIndent();
        dumpAnnotations(declaration);
        dumpElements(declaration.getTypeParameters());
        this.printer.popIndent();
    }

    /* JADX INFO: renamed from: visitTypeParameter, reason: avoid collision after fix types in other method */
    public void visitTypeParameter2(IrTypeParameter declaration, String data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(data, "data");
        this.printer.println(withLabel((String) declaration.accept(this.elementRenderer, null), data));
        this.printer.pushIndent();
        dumpAnnotations(declaration);
        this.printer.popIndent();
    }

    /* JADX INFO: renamed from: visitSimpleFunction, reason: avoid collision after fix types in other method */
    public void visitSimpleFunction2(IrSimpleFunction declaration, String data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(data, "data");
        this.printer.println(withLabel((String) declaration.accept(this.elementRenderer, null), data));
        this.printer.pushIndent();
        dumpAnnotations(declaration);
        IrPropertySymbol correspondingPropertySymbol = declaration.getCorrespondingPropertySymbol();
        if (correspondingPropertySymbol != null) {
            dumpInternal(correspondingPropertySymbol, "correspondingProperty");
        }
        List<IrSimpleFunctionSymbol> overriddenSymbols = declaration.getOverriddenSymbols();
        if (!overriddenSymbols.isEmpty()) {
            this.printer.println("overridden:");
            this.printer.pushIndent();
            Iterator<T> it = overriddenSymbols.iterator();
            while (it.hasNext()) {
                dump$default(this, (IrSimpleFunctionSymbol) it.next(), null, 1, null);
            }
            this.printer.popIndent();
        }
        dumpElements(declaration.getTypeParameters());
        IrValueParameter dispatchReceiverParameter = declaration.getDispatchReceiverParameter();
        if (dispatchReceiverParameter != null) {
            dispatchReceiverParameter.accept(this, "$this");
        }
        int contextReceiverParametersCount = declaration.getContextReceiverParametersCount();
        if (contextReceiverParametersCount > 0) {
            this.printer.println("contextReceiverParametersCount: " + contextReceiverParametersCount);
        }
        IrValueParameter extensionReceiverParameter = declaration.getExtensionReceiverParameter();
        if (extensionReceiverParameter != null) {
            extensionReceiverParameter.accept(this, AsmUtil.RECEIVER_PARAMETER_NAME);
        }
        dumpElements(declaration.getValueParameters());
        IrBody body = declaration.getBody();
        if (body != null) {
            body.accept(this, "");
        }
        this.printer.popIndent();
    }

    private final void dumpAnnotations(IrAnnotationContainer element) {
        List<IrConstructorCall> annotations = element.getAnnotations();
        if (annotations.isEmpty()) {
            return;
        }
        this.printer.println("annotations:");
        this.printer.pushIndent();
        Iterator<T> it = annotations.iterator();
        while (it.hasNext()) {
            this.printer.println(this.elementRenderer.renderAsAnnotation((IrConstructorCall) it.next()));
        }
        this.printer.popIndent();
    }

    static /* synthetic */ Printer dump$default(DumpIrTreeVisitor dumpIrTreeVisitor, IrSymbol irSymbol, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            str = null;
        }
        return dumpIrTreeVisitor.dump(irSymbol, str);
    }

    private final Printer dump(IrSymbol irSymbol, String str) {
        Printer printer = this.printer;
        String strRenderSymbolReference = this.elementRenderer.renderSymbolReference(irSymbol);
        if (str != null) {
            strRenderSymbolReference = str + ": " + strRenderSymbolReference;
        }
        Printer printerPrintln = printer.println(strRenderSymbolReference);
        Intrinsics.checkNotNullExpressionValue(printerPrintln, "println(...)");
        return printerPrintln;
    }

    /* JADX INFO: renamed from: visitConstructor, reason: avoid collision after fix types in other method */
    public void visitConstructor2(IrConstructor declaration, String data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(data, "data");
        this.printer.println(withLabel((String) declaration.accept(this.elementRenderer, null), data));
        this.printer.pushIndent();
        dumpAnnotations(declaration);
        dumpElements(declaration.getTypeParameters());
        IrValueParameter dispatchReceiverParameter = declaration.getDispatchReceiverParameter();
        if (dispatchReceiverParameter != null) {
            dispatchReceiverParameter.accept(this, Namer.OUTER_FIELD_NAME);
        }
        dumpElements(declaration.getValueParameters());
        IrBody body = declaration.getBody();
        if (body != null) {
            body.accept(this, "");
        }
        this.printer.popIndent();
    }

    /* JADX INFO: renamed from: visitProperty, reason: avoid collision after fix types in other method */
    public void visitProperty2(IrProperty declaration, String data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(data, "data");
        this.printer.println(withLabel((String) declaration.accept(this.elementRenderer, null), data));
        this.printer.pushIndent();
        dumpAnnotations(declaration);
        List<IrPropertySymbol> overriddenSymbols = declaration.getOverriddenSymbols();
        if (!overriddenSymbols.isEmpty()) {
            this.printer.println("overridden:");
            this.printer.pushIndent();
            Iterator<T> it = overriddenSymbols.iterator();
            while (it.hasNext()) {
                dump$default(this, (IrPropertySymbol) it.next(), null, 1, null);
            }
            this.printer.popIndent();
        }
        IrField backingField = declaration.getBackingField();
        if (backingField != null) {
            backingField.accept(this, "");
        }
        IrSimpleFunction getter = declaration.getGetter();
        if (getter != null) {
            getter.accept(this, "");
        }
        IrSimpleFunction setter = declaration.getSetter();
        if (setter != null) {
            setter.accept(this, "");
        }
        this.printer.popIndent();
    }

    /* JADX INFO: renamed from: visitField, reason: avoid collision after fix types in other method */
    public void visitField2(IrField declaration, String data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(data, "data");
        this.printer.println(withLabel((String) declaration.accept(this.elementRenderer, null), data));
        this.printer.pushIndent();
        dumpAnnotations(declaration);
        IrExpressionBody initializer = declaration.getInitializer();
        if (initializer != null) {
            initializer.accept(this, "");
        }
        this.printer.popIndent();
    }

    private final void dumpElements(List<? extends IrElement> list) {
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            ((IrElement) it.next()).accept(this, "");
        }
    }

    /* JADX INFO: renamed from: visitErrorCallExpression, reason: avoid collision after fix types in other method */
    public void visitErrorCallExpression2(IrErrorCallExpression expression, String data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        this.printer.println(withLabel((String) expression.accept(this.elementRenderer, null), data));
        this.printer.pushIndent();
        IrExpression explicitReceiver = expression.getExplicitReceiver();
        if (explicitReceiver != null) {
            explicitReceiver.accept(this, "receiver");
        }
        dumpElements(expression.getArguments());
        this.printer.popIndent();
    }

    /* JADX INFO: renamed from: visitEnumEntry, reason: avoid collision after fix types in other method */
    public void visitEnumEntry2(IrEnumEntry declaration, String data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(data, "data");
        this.printer.println(withLabel((String) declaration.accept(this.elementRenderer, null), data));
        this.printer.pushIndent();
        dumpAnnotations(declaration);
        IrExpressionBody initializerExpression = declaration.getInitializerExpression();
        if (initializerExpression != null) {
            initializerExpression.accept(this, ConfigConstants.CONFIG_INIT_SECTION);
        }
        IrClass correspondingClass = declaration.getCorrespondingClass();
        if (correspondingClass != null) {
            correspondingClass.accept(this, "class");
        }
        this.printer.popIndent();
    }

    /* JADX INFO: renamed from: visitMemberAccess, reason: avoid collision after fix types in other method */
    public void visitMemberAccess2(IrMemberAccessExpression<?> expression, String data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        this.printer.println(withLabel((String) expression.accept(this.elementRenderer, null), data));
        this.printer.pushIndent();
        dumpTypeArguments(expression);
        IrExpression dispatchReceiver = expression.getDispatchReceiver();
        if (dispatchReceiver != null) {
            dispatchReceiver.accept(this, "$this");
        }
        IrExpression extensionReceiver = expression.getExtensionReceiver();
        if (extensionReceiver != null) {
            extensionReceiver.accept(this, AsmUtil.RECEIVER_PARAMETER_NAME);
        }
        List<String> valueParameterNamesForDebug = DumpIrTreeKt.getValueParameterNamesForDebug(expression);
        int valueArgumentsCount = expression.getValueArgumentsCount();
        for (int i = 0; i < valueArgumentsCount; i++) {
            IrExpression valueArgument = expression.getValueArgument(i);
            if (valueArgument != null) {
                valueArgument.accept(this, valueParameterNamesForDebug.get(i));
            }
        }
        this.printer.popIndent();
    }

    /* JADX INFO: renamed from: visitConstructorCall, reason: avoid collision after fix types in other method */
    public void visitConstructorCall2(IrConstructorCall expression, String data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        this.printer.println(withLabel((String) expression.accept(this.elementRenderer, null), data));
        this.printer.pushIndent();
        dumpTypeArguments(expression);
        IrExpression outerClassReceiver = IrConstructorCallTypeArgumentsKt.getOuterClassReceiver(expression);
        if (outerClassReceiver != null) {
            outerClassReceiver.accept(this, Namer.OUTER_FIELD_NAME);
        }
        dumpConstructorValueArguments(expression);
        this.printer.popIndent();
    }

    private final void dumpConstructorValueArguments(IrConstructorCall expression) {
        List<String> valueParameterNamesForDebug = DumpIrTreeKt.getValueParameterNamesForDebug(expression);
        int valueArgumentsCount = expression.getValueArgumentsCount();
        for (int i = 0; i < valueArgumentsCount; i++) {
            IrExpression valueArgument = expression.getValueArgument(i);
            if (valueArgument != null) {
                valueArgument.accept(this, valueParameterNamesForDebug.get(i));
            }
        }
    }

    private final void dumpTypeArguments(IrMemberAccessExpression<?> expression) {
        List<String> typeParameterNames = getTypeParameterNames(expression, expression.getTypeArgumentsCount());
        int typeArgumentsCount = expression.getTypeArgumentsCount();
        for (int i = 0; i < typeArgumentsCount; i++) {
            this.printer.println("<" + typeParameterNames.get(i) + ">: " + renderTypeArgument(expression, i));
        }
    }

    private final void dumpTypeArguments(IrConstructorCall expression) {
        IrConstructorCall irConstructorCall = expression;
        List<String> typeParameterNames = getTypeParameterNames(irConstructorCall, expression.getTypeArgumentsCount());
        int typeArgumentsCount = expression.getTypeArgumentsCount();
        for (int i = 0; i < typeArgumentsCount; i++) {
            String str = typeParameterNames.get(i);
            if (i < IrConstructorCallTypeArgumentsKt.getClassTypeArgumentsCount(expression)) {
                str = "class: " + str;
            }
            this.printer.println("<" + str + ">: " + renderTypeArgument(irConstructorCall, i));
        }
    }

    private final List<String> getTypeParameterNames(IrMemberAccessExpression<?> irMemberAccessExpression, int i) {
        if (irMemberAccessExpression.getSymbol().isBound()) {
            return getTypeParameterNames(irMemberAccessExpression.getSymbol().getOwner(), i);
        }
        return DumpIrTreeKt.getPlaceholderParameterNames(i);
    }

    private final List<String> getTypeParameterNames(IrSymbolOwner irSymbolOwner, int i) {
        String strValueOf;
        if (irSymbolOwner instanceof IrTypeParametersContainer) {
            List<IrTypeParameter> fullTypeParametersList = irSymbolOwner instanceof IrConstructor ? getFullTypeParametersList((IrConstructor) irSymbolOwner) : ((IrTypeParametersContainer) irSymbolOwner).getTypeParameters();
            IntRange intRangeUntil = RangesKt.until(0, i);
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(intRangeUntil, 10));
            Iterator<Integer> it = intRangeUntil.iterator();
            while (it.hasNext()) {
                int iNextInt = ((IntIterator) it).nextInt();
                if (iNextInt < fullTypeParametersList.size()) {
                    strValueOf = fullTypeParametersList.get(iNextInt).getName().asString();
                } else {
                    strValueOf = String.valueOf(iNextInt + 1);
                }
                Intrinsics.checkNotNull(strValueOf);
                arrayList.add(strValueOf);
            }
            return arrayList;
        }
        return DumpIrTreeKt.getPlaceholderParameterNames(i);
    }

    private final List<IrTypeParameter> getFullTypeParametersList(IrConstructor irConstructor) {
        try {
            IrDeclarationParent parent = irConstructor.getParent();
            IrClass irClass = parent instanceof IrClass ? (IrClass) parent : null;
            return irClass == null ? irConstructor.getTypeParameters() : CollectionsKt.plus((Collection) irClass.getTypeParameters(), (Iterable) irConstructor.getTypeParameters());
        } catch (Exception unused) {
            return irConstructor.getTypeParameters();
        }
    }

    private final String renderTypeArgument(IrMemberAccessExpression<?> irMemberAccessExpression, int i) {
        String strRender;
        IrType typeArgument = irMemberAccessExpression.getTypeArgument(i);
        return (typeArgument == null || (strRender = render(typeArgument)) == null) ? "<none>" : strRender;
    }

    /* JADX INFO: renamed from: visitBlock, reason: avoid collision after fix types in other method */
    public void visitBlock2(IrBlock expression, String data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        if (!(expression instanceof IrInlinedFunctionBlock)) {
            IrElementVisitor.DefaultImpls.visitBlock(this, expression, data);
            return;
        }
        this.printer.println(withLabel((String) expression.accept(this.elementRenderer, null), data));
        this.printer.pushIndent();
        dumpInternal(((IrInlinedFunctionBlock) expression).getInlinedElement(), "inlinedElement");
        expression.acceptChildren(this, "");
        this.printer.popIndent();
    }

    /* JADX INFO: renamed from: visitGetField, reason: avoid collision after fix types in other method */
    public void visitGetField2(IrGetField expression, String data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        this.printer.println(withLabel((String) expression.accept(this.elementRenderer, null), data));
        this.printer.pushIndent();
        IrExpression receiver = expression.getReceiver();
        if (receiver != null) {
            receiver.accept(this, "receiver");
        }
        this.printer.popIndent();
    }

    /* JADX INFO: renamed from: visitSetField, reason: avoid collision after fix types in other method */
    public void visitSetField2(IrSetField expression, String data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        this.printer.println(withLabel((String) expression.accept(this.elementRenderer, null), data));
        this.printer.pushIndent();
        IrExpression receiver = expression.getReceiver();
        if (receiver != null) {
            receiver.accept(this, "receiver");
        }
        expression.getValue().accept(this, "value");
        this.printer.popIndent();
    }

    /* JADX INFO: renamed from: visitWhen, reason: avoid collision after fix types in other method */
    public void visitWhen2(IrWhen expression, String data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        this.printer.println(withLabel((String) expression.accept(this.elementRenderer, null), data));
        this.printer.pushIndent();
        dumpElements(expression.getBranches());
        this.printer.popIndent();
    }

    /* JADX INFO: renamed from: visitBranch, reason: avoid collision after fix types in other method */
    public void visitBranch2(IrBranch branch, String data) {
        Intrinsics.checkNotNullParameter(branch, "branch");
        Intrinsics.checkNotNullParameter(data, "data");
        this.printer.println(withLabel((String) branch.accept(this.elementRenderer, null), data));
        this.printer.pushIndent();
        DumpIrTreeVisitor dumpIrTreeVisitor = this;
        branch.getCondition().accept(dumpIrTreeVisitor, PsiKeyword.IF);
        branch.getResult().accept(dumpIrTreeVisitor, "then");
        this.printer.popIndent();
    }

    /* JADX INFO: renamed from: visitWhileLoop, reason: avoid collision after fix types in other method */
    public void visitWhileLoop2(IrWhileLoop loop, String data) {
        Intrinsics.checkNotNullParameter(loop, "loop");
        Intrinsics.checkNotNullParameter(data, "data");
        this.printer.println(withLabel((String) loop.accept(this.elementRenderer, null), data));
        this.printer.pushIndent();
        DumpIrTreeVisitor dumpIrTreeVisitor = this;
        loop.getCondition().accept(dumpIrTreeVisitor, "condition");
        IrExpression body = loop.getBody();
        if (body != null) {
            body.accept(dumpIrTreeVisitor, "body");
        }
        this.printer.popIndent();
    }

    /* JADX INFO: renamed from: visitDoWhileLoop, reason: avoid collision after fix types in other method */
    public void visitDoWhileLoop2(IrDoWhileLoop loop, String data) {
        Intrinsics.checkNotNullParameter(loop, "loop");
        Intrinsics.checkNotNullParameter(data, "data");
        this.printer.println(withLabel((String) loop.accept(this.elementRenderer, null), data));
        this.printer.pushIndent();
        IrExpression body = loop.getBody();
        if (body != null) {
            body.accept(this, "body");
        }
        loop.getCondition().accept(this, "condition");
        this.printer.popIndent();
    }

    /* JADX INFO: renamed from: visitTry, reason: avoid collision after fix types in other method */
    public void visitTry2(IrTry aTry, String data) {
        Intrinsics.checkNotNullParameter(aTry, "aTry");
        Intrinsics.checkNotNullParameter(data, "data");
        this.printer.println(withLabel((String) aTry.accept(this.elementRenderer, null), data));
        this.printer.pushIndent();
        DumpIrTreeVisitor dumpIrTreeVisitor = this;
        aTry.getTryResult().accept(dumpIrTreeVisitor, PsiKeyword.TRY);
        dumpElements(aTry.getCatches());
        IrExpression finallyExpression = aTry.getFinallyExpression();
        if (finallyExpression != null) {
            finallyExpression.accept(dumpIrTreeVisitor, PsiKeyword.FINALLY);
        }
        this.printer.popIndent();
    }

    /* JADX INFO: renamed from: visitTypeOperator, reason: avoid collision after fix types in other method */
    public void visitTypeOperator2(IrTypeOperatorCall expression, String data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        this.printer.println(withLabel((String) expression.accept(this.elementRenderer, null), data));
        this.printer.pushIndent();
        expression.acceptChildren(this, "");
        this.printer.popIndent();
    }

    /* JADX INFO: renamed from: visitDynamicOperatorExpression, reason: avoid collision after fix types in other method */
    public void visitDynamicOperatorExpression2(IrDynamicOperatorExpression expression, String data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        this.printer.println(withLabel((String) expression.accept(this.elementRenderer, null), data));
        this.printer.pushIndent();
        DumpIrTreeVisitor dumpIrTreeVisitor = this;
        expression.getReceiver().accept(dumpIrTreeVisitor, "receiver");
        Iterator<IrExpression> it = expression.getArguments().iterator();
        int i = 0;
        while (it.hasNext()) {
            it.next().accept(dumpIrTreeVisitor, String.valueOf(i));
            i++;
        }
        this.printer.popIndent();
    }

    /* JADX INFO: renamed from: visitConstantArray, reason: avoid collision after fix types in other method */
    public void visitConstantArray2(IrConstantArray expression, String data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        this.printer.println(withLabel((String) expression.accept(this.elementRenderer, null), data));
        this.printer.pushIndent();
        Iterator<IrConstantValue> it = expression.getElements().iterator();
        int i = 0;
        while (it.hasNext()) {
            it.next().accept(this, String.valueOf(i));
            i++;
        }
        this.printer.popIndent();
    }

    /* JADX INFO: renamed from: visitConstantObject, reason: avoid collision after fix types in other method */
    public void visitConstantObject2(IrConstantObject expression, String data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        this.printer.println(withLabel((String) expression.accept(this.elementRenderer, null), data));
        this.printer.pushIndent();
        Iterator<IrConstantValue> it = expression.getValueArguments().iterator();
        int i = 0;
        while (it.hasNext()) {
            it.next().accept(this, expression.getConstructor().getOwner().getValueParameters().get(i).getName().toString());
            i++;
        }
        this.printer.popIndent();
    }

    private final void dumpInternal(IrSymbol irSymbol, String str) {
        if (irSymbol.isBound()) {
            dumpInternal(irSymbol.getOwner(), str);
        } else {
            this.printer.println(str + ": UNBOUND " + irSymbol.getClass().getSimpleName());
        }
    }

    private final void dumpInternal(IrElement irElement, String str) {
        if (str != null) {
            this.printer.println(str + ": ", irElement.accept(this.elementRenderer, null));
        } else {
            this.printer.println(irElement.accept(this.elementRenderer, null));
        }
    }

    private final String withLabel(String str, String str2) {
        return str2.length() == 0 ? str : str2 + ": " + str;
    }

    /* JADX INFO: renamed from: visitElement, reason: avoid collision after fix types in other method */
    public void visitElement2(IrElement element, String data) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(data, "data");
        this.printer.println(withLabel((String) element.accept(this.elementRenderer, null), data));
        this.printer.pushIndent();
        if (element instanceof IrAnnotationContainer) {
            dumpAnnotations((IrAnnotationContainer) element);
        }
        element.acceptChildren(this, "");
        this.printer.popIndent();
    }
}
