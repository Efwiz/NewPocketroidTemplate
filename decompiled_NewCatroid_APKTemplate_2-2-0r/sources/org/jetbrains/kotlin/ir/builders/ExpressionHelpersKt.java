package org.jetbrains.kotlin.ir.builders;

import androidx.exifinterface.media.ExifInterface;
import com.android.apksig.internal.apk.AndroidBinXmlParser;
import com.reandroid.archive.PathTree;
import com.reandroid.arsc.value.CompoundEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrBranch;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrCatch;
import org.jetbrains.kotlin.ir.expressions.IrConst;
import org.jetbrains.kotlin.ir.expressions.IrConstKind;
import org.jetbrains.kotlin.ir.expressions.IrConstantValue;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrContainerExpression;
import org.jetbrains.kotlin.ir.expressions.IrDelegatingConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.expressions.IrMemberAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrStatementContainer;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperator;
import org.jetbrains.kotlin.ir.expressions.impl.IrBlockImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrBranchImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstantArrayImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstantObjectImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstantPrimitiveImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstructorCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrDelegatingConstructorCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrElseBranchImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrFunctionReferenceImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetFieldImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetObjectValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrIfThenElseImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrRawFunctionReferenceImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrReturnImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrSetFieldImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrSetValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrStringConcatenationImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrTryImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrTypeOperatorCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrVarargImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrWhenImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrReturnTargetSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueSymbol;
import org.jetbrains.kotlin.ir.symbols.IrVariableSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: ExpressionHelpers.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0094\u0003\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\f\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u001a\u0010\u0005\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n\u001aU\u0010\u000b\u001a\u00020\f*\u00020\u00022\b\b\u0002\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u000e2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00112\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\n2\u0017\u0010\u0013\u001a\u0013\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00160\u0014¢\u0006\u0002\b\u0017H\u0086\bø\u0001\u0000\u001a=\u0010\u0018\u001a\u00020\u0019*\u00020\u00022\b\b\u0002\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u000e2\u0017\u0010\u0013\u001a\u0013\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020\u00160\u0014¢\u0006\u0002\b\u0017H\u0086\bø\u0001\u0000\u001a\u0018\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001c*\u00020\u00022\u0006\u0010\u001e\u001a\u00020\u001d\u001a\u001a\u0010\u001f\u001a\u00020 *\u00020\u00022\u0006\u0010!\u001a\u00020\b2\u0006\u0010\"\u001a\u00020\b\u001a\u0012\u0010#\u001a\u00020$*\u00020\u00022\u0006\u0010%\u001a\u00020&\u001a*\u0010#\u001a\u00020'*\u00020\u00022\u0006\u0010%\u001a\u00020&2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00112\n\b\u0002\u0010(\u001a\u0004\u0018\u00010)\u001a\u0012\u0010#\u001a\u00020**\u00020\u00022\u0006\u0010%\u001a\u00020+\u001a$\u0010#\u001a\u00020**\u00020\u00022\u0006\u0010%\u001a\u00020+2\u0006\u0010\t\u001a\u00020\n2\b\b\u0002\u0010,\u001a\u00020-\u001a\u0012\u0010#\u001a\u00020$*\u00020\u00022\u0006\u0010%\u001a\u00020.\u001a\u001a\u0010#\u001a\u00020$*\u00020\u00022\u0006\u0010%\u001a\u00020.2\u0006\u0010\t\u001a\u00020\n\u001a,\u0010#\u001a\u0006\u0012\u0002\b\u00030/*\u00020\u00022\u0006\u0010%\u001a\u00020.2\u0006\u0010\t\u001a\u00020\n2\f\u00100\u001a\b\u0012\u0004\u0012\u00020\n01\u001a\u0012\u0010#\u001a\u00020'*\u00020\u00022\u0006\u0010%\u001a\u000202\u001a:\u0010#\u001a\u00020'*\u00020\u00022\u0006\u0010%\u001a\u0002022\u0006\u0010\t\u001a\u00020\n2\b\b\u0002\u00103\u001a\u00020\u000e2\b\b\u0002\u00104\u001a\u00020\u000e2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0011\u001a \u00105\u001a\u00020**\u00020\u00022\u0006\u0010%\u001a\u00020+2\f\u00100\u001a\b\u0012\u0004\u0012\u00020\n01\u001a>\u00106\u001a\u0006\u0012\u0002\b\u00030/*\u00020\u00022\u0006\u0010%\u001a\u0002022\u0006\u0010\t\u001a\u00020\n2\u0006\u00107\u001a\u00020\b2\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0011\u001a\u0018\u00108\u001a\b\u0012\u0004\u0012\u0002090\u001c*\u00020\u00022\u0006\u0010\u001e\u001a\u000209\u001aU\u0010:\u001a\u00020\b*\u00020\u00022\b\b\u0002\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u000e2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00112\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\n2\u0017\u0010\u0013\u001a\u0013\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00160\u0014¢\u0006\u0002\b\u0017H\u0086\bø\u0001\u0000\u001a\n\u0010;\u001a\u00020<*\u00020\u0002\u001a \u0010=\u001a\u00020>*\u00020\u00022\u0006\u0010\t\u001a\u00020\n2\f\u0010?\u001a\b\u0012\u0004\u0012\u00020@01\u001a0\u0010A\u001a\u00020@*\u00020\u00022\u0006\u0010B\u001a\u00020-2\f\u0010C\u001a\b\u0012\u0004\u0012\u00020@012\u000e\b\u0002\u00100\u001a\b\u0012\u0004\u0012\u00020\n01\u001a6\u0010A\u001a\u00020@*\u00020\u00022\u0006\u0010B\u001a\u00020-2\u0012\u0010?\u001a\u000e\u0012\u0004\u0012\u00020E\u0012\u0004\u0012\u00020@0D2\u000e\b\u0002\u00100\u001a\b\u0012\u0004\u0012\u00020\n01\u001a0\u0010A\u001a\u00020@*\u00020\u00022\u0006\u0010F\u001a\u00020+2\f\u0010C\u001a\b\u0012\u0004\u0012\u00020@012\u000e\b\u0002\u00100\u001a\b\u0012\u0004\u0012\u00020\n01\u001a\u0016\u0010G\u001a\u00020H*\u00020\u00022\n\u0010\u001e\u001a\u0006\u0012\u0002\b\u00030I\u001a\u0012\u0010J\u001a\u00020K*\u00020\u00022\u0006\u0010%\u001a\u00020L\u001a\u0012\u0010M\u001a\u00020N*\u00020\u00022\u0006\u0010O\u001a\u00020\b\u001a\u001a\u0010P\u001a\u00020\b*\u00020\u00022\u0006\u0010Q\u001a\u00020\b2\u0006\u0010R\u001a\u00020\b\u001a\u001a\u0010S\u001a\u00020\b*\u00020\u00022\u0006\u0010Q\u001a\u00020\b2\u0006\u0010R\u001a\u00020\b\u001a$\u0010T\u001a\u00020\b*\u00020\u00022\u0006\u0010Q\u001a\u00020\b2\u0006\u0010R\u001a\u00020\b2\b\b\u0002\u0010\u0010\u001a\u00020\u0011\u001a\u0012\u0010U\u001a\u00020\b*\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b\u001a\u0012\u0010V\u001a\u00020W*\u00020\u00022\u0006\u0010\u001e\u001a\u00020\b\u001a\u0010\u0010X\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001c*\u00020\u0002\u001a\u001a\u0010Y\u001a\u00020Z*\u00020\u00022\u0006\u0010\t\u001a\u00020\n2\u0006\u0010[\u001a\u00020.\u001a\u0012\u0010\\\u001a\u00020]*\u00020\u00022\u0006\u0010^\u001a\u00020_\u001a\u001a\u0010\\\u001a\u00020]*\u00020\u00022\u0006\u0010^\u001a\u00020_2\u0006\u0010\t\u001a\u00020\n\u001a$\u0010\\\u001a\u00020'*\u00020\u00022\u0006\u0010\t\u001a\u00020\n2\b\u0010`\u001a\u0004\u0018\u00010\b2\u0006\u0010a\u001a\u00020.\u001a\u001a\u0010\\\u001a\u00020]*\u00020\u00022\u0006\u0010\t\u001a\u00020\n2\u0006\u0010^\u001a\u00020b\u001a&\u0010c\u001a\u00020d*\u00020\u00022\b\u0010`\u001a\u0004\u0018\u00010\b2\u0006\u0010e\u001a\u00020f2\b\b\u0002\u0010\t\u001a\u00020\n\u001a\u001a\u0010g\u001a\u00020h*\u00020\u00022\u0006\u0010\t\u001a\u00020\n2\u0006\u0010i\u001a\u00020)\u001a*\u0010j\u001a\u00020k*\u00020\u00022\u0006\u0010\t\u001a\u00020\n2\u0006\u0010l\u001a\u00020\b2\u0006\u0010m\u001a\u00020\b2\u0006\u0010n\u001a\u00020\b\u001a.\u0010o\u001a\u00020k*\u00020\u00022\u0006\u0010\t\u001a\u00020\n2\u0006\u0010!\u001a\u00020\b2\u0006\u0010m\u001a\u00020\b2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0011\u001a6\u0010p\u001a\u00020k*\u00020\u00022\u0006\u0010\t\u001a\u00020\n2\u0006\u0010!\u001a\u00020\b2\u0006\u0010m\u001a\u00020\b2\u0006\u0010n\u001a\u00020\b2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0011\u001a8\u0010q\u001a\u00020k*\u00020\u00022\u0006\u0010\t\u001a\u00020\n2\u0006\u0010!\u001a\u00020\b2\u0006\u0010m\u001a\u00020\b2\b\u0010n\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0011\u001a\u0012\u0010r\u001a\u00020k*\u00020\u00022\u0006\u0010!\u001a\u00020\b\u001a\u0012\u0010s\u001a\u00020k*\u00020\u00022\u0006\u0010!\u001a\u00020\b\u001a\u001a\u0010t\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n\u001a\"\u0010u\u001a\b\u0012\u0004\u0012\u00020\u000e0\u001c*\u00020\u00022\u0006\u0010\u001e\u001a\u00020\u000e2\b\b\u0002\u0010\t\u001a\u00020\n\u001a\u001a\u0010v\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n\u001aP\u0010w\u001a\u00020\b*\u00020\u00022\u0006\u0010\u001e\u001a\u00020\b2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00112\n\b\u0002\u0010x\u001a\u0004\u0018\u00010E2\n\b\u0002\u0010y\u001a\u0004\u0018\u00010\n2\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020b\u0012\u0004\u0012\u00020\b0\u0014H\u0086\bø\u0001\u0000\u001a\"\u0010z\u001a\b\u0012\u0004\u0012\u00020{0\u001c*\u00020\u00022\u0006\u0010\u001e\u001a\u00020{2\b\b\u0002\u0010\t\u001a\u00020\n\u001a\u001a\u0010|\u001a\u00020\b*\u00020\u00022\u0006\u0010Q\u001a\u00020\b2\u0006\u0010R\u001a\u00020\b\u001a\u001a\u0010}\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n\u001a\u0012\u0010~\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u007f0\u001c*\u00020\u0002\u001a\u001a\u0010~\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u007f0\u001c*\u00020\u00022\u0006\u0010y\u001a\u00020\n\u001a\u001c\u0010\u0080\u0001\u001a\u00030\u0081\u0001*\u00020\u00022\u0006\u0010\t\u001a\u00020\n2\u0006\u0010[\u001a\u00020.\u001a\u001b\u0010\u0082\u0001\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n\u001a\u0014\u0010\u0083\u0001\u001a\u00030\u0084\u0001*\u00020\u00022\u0006\u0010\u001e\u001a\u00020\b\u001a\f\u0010\u0085\u0001\u001a\u00030\u0084\u0001*\u00020\u0002\u001a\f\u0010\u0086\u0001\u001a\u00030\u0084\u0001*\u00020\u0002\u001a\f\u0010\u0087\u0001\u001a\u00030\u0084\u0001*\u00020\u0002\u001a\u001b\u0010\u0088\u0001\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n\u001a&\u0010\u0089\u0001\u001a\u00030\u008a\u0001*\u00020\u00022\u0006\u0010^\u001a\u00020_2\u0006\u0010\u001e\u001a\u00020\b2\b\b\u0002\u0010\u0010\u001a\u00020\u0011\u001a&\u0010\u0089\u0001\u001a\u00030\u008a\u0001*\u00020\u00022\u0006\u0010^\u001a\u00020b2\u0006\u0010\u001e\u001a\u00020\b2\b\b\u0002\u0010\u0010\u001a\u00020\u0011\u001a.\u0010\u0089\u0001\u001a\u00020'*\u00020\u00022\u0006\u0010\t\u001a\u00020\n2\b\u0010`\u001a\u0004\u0018\u00010\b2\u0007\u0010\u008b\u0001\u001a\u00020.2\u0006\u0010\u001e\u001a\u00020\b\u001a2\u0010\u008c\u0001\u001a\u00030\u008d\u0001*\u00020\u00022\b\u0010`\u001a\u0004\u0018\u00010\b2\u0006\u0010e\u001a\u00020f2\u0006\u0010\u001e\u001a\u00020\b2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0011\u001a\u0019\u0010\u008e\u0001\u001a\b\u0012\u0004\u0012\u00020E0\u001c*\u00020\u00022\u0006\u0010\u001e\u001a\u00020E\u001aX\u0010\u008f\u0001\u001a\u00030\u0090\u0001\"\n\b\u0000\u0010\u0091\u0001*\u00030\u0092\u0001*\n\u0012\u0005\u0012\u0003H\u0091\u00010\u0093\u00012\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010x\u001a\u0004\u0018\u00010E2\b\b\u0002\u0010y\u001a\u00020\n2\t\b\u0002\u0010\u0094\u0001\u001a\u00020\u001d2\t\b\u0002\u0010\u0010\u001a\u00030\u0095\u0001\u001a\u0011\u0010\u0096\u0001\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001c*\u00020\u0002\u001a8\u0010\u0097\u0001\u001a\u00030\u0098\u0001*\u00020\u00022\u0006\u0010\t\u001a\u00020\n2\u0007\u0010\u0099\u0001\u001a\u00020\b2\u000e\u0010\u009a\u0001\u001a\t\u0012\u0005\u0012\u00030\u009b\u0001012\t\u0010\u009c\u0001\u001a\u0004\u0018\u00010\b\u001a\u000b\u0010\u009d\u0001\u001a\u00020h*\u00020\u0002\u001a$\u0010\u009e\u0001\u001a\u00030\u009f\u0001*\u00020\u00022\u0007\u0010 \u0001\u001a\u00020\n2\r\u0010¡\u0001\u001a\b\u0012\u0004\u0012\u00020\b01\u001a$\u0010¢\u0001\u001a\u00030£\u0001*\u00020\u00022\u0006\u0010\t\u001a\u00020\n2\u000e\u0010¤\u0001\u001a\t\u0012\u0005\u0012\u00030¥\u000101\u001a.\u0010¦\u0001\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0012\u001a\u00020\n2\u0006\u0010\u0007\u001a\u00020\b2\b\u0010¦\u0001\u001a\u00030§\u00012\u0007\u0010¨\u0001\u001a\u00020\n\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006©\u0001"}, d2 = {"parent", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "Lorg/jetbrains/kotlin/ir/builders/IrBuilderWithScope;", "getParent", "(Lorg/jetbrains/kotlin/ir/builders/IrBuilderWithScope;)Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "irAs", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrTypeOperatorCallImpl;", "argument", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "irBlock", "Lorg/jetbrains/kotlin/ir/expressions/IrContainerExpression;", "startOffset", "", "endOffset", "origin", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "resultType", "body", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/ir/builders/IrBlockBuilder;", "", "Lkotlin/ExtensionFunctionType;", "irBlockBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBlockBody;", "Lorg/jetbrains/kotlin/ir/builders/IrBlockBodyBuilder;", "irBoolean", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrConstImpl;", "", "value", "irBranch", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrBranchImpl;", "condition", "result", "irCall", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "callee", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "superQualifierSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "constructedClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "Lorg/jetbrains/kotlin/ir/expressions/IrMemberAccessExpression;", "typeArguments", "", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "valueArgumentsCount", "typeArgumentsCount", "irCallConstructor", "irCallOp", "dispatchReceiver", "irChar", "", "irComposite", "irConcat", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrStringConcatenationImpl;", "irConstantArray", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrConstantArrayImpl;", PathTree.NAME_elements, "Lorg/jetbrains/kotlin/ir/expressions/IrConstantValue;", "irConstantObject", "clazz", "arguments", "", "", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "irConstantPrimitive", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrConstantPrimitiveImpl;", "Lorg/jetbrains/kotlin/ir/expressions/IrConst;", "irDelegatingConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrDelegatingConstructorCall;", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "irElseBranch", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrElseBranchImpl;", "expression", "irEqeqeq", "arg1", "arg2", "irEqeqeqWithoutBox", "irEquals", "irEqualsNull", "irExprBody", "Lorg/jetbrains/kotlin/ir/expressions/IrExpressionBody;", "irFalse", "irFunctionReference", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrFunctionReferenceImpl;", NativeSymbol.TYPE_NAME, "irGet", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrGetValueImpl;", "variable", "Lorg/jetbrains/kotlin/ir/declarations/IrValueDeclaration;", "receiver", "getterSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrValueSymbol;", "irGetField", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrGetFieldImpl;", "field", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "irGetObjectValue", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrGetObjectValueImpl;", "classSymbol", "irIfNull", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrIfThenElseImpl;", "subject", "thenPart", "elsePart", "irIfThen", "irIfThenElse", "irIfThenMaybeElse", "irIfThenReturnFalse", "irIfThenReturnTrue", "irImplicitCast", "irInt", "irIs", "irLetS", "nameHint", "irType", "irLong", "", "irNotEquals", "irNotIs", "irNull", "", "irRawFunctionReference", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrRawFunctionReferenceImpl;", "irReinterpretCast", "irReturn", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrReturnImpl;", "irReturnFalse", "irReturnTrue", "irReturnUnit", "irSamConversion", "irSet", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrSetValueImpl;", "setterSymbol", "irSetField", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrSetFieldImpl;", "irString", "irTemporary", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/ir/IrElement;", "Lorg/jetbrains/kotlin/ir/builders/IrStatementsBuilder;", "isMutable", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "irTrue", "irTry", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrTryImpl;", "tryResult", "catches", "Lorg/jetbrains/kotlin/ir/expressions/IrCatch;", "finallyExpression", "irUnit", "irVararg", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrVarargImpl;", "elementType", CompoundEntry.NAME_values, "irWhen", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrWhenImpl;", "branches", "Lorg/jetbrains/kotlin/ir/expressions/IrBranch;", "typeOperator", "Lorg/jetbrains/kotlin/ir/expressions/IrTypeOperator;", "typeOperand", "ir.tree"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ExpressionHelpersKt {
    public static final IrDeclarationParent getParent(IrBuilderWithScope irBuilderWithScope) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        return irBuilderWithScope.getScope().getLocalDeclarationParent();
    }

    /* JADX WARN: Code duplicated, block: B:19:0x0046  */
    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ IrExpression irLetS$default(IrBuilderWithScope irBuilderWithScope, IrExpression value, IrStatementOrigin irStatementOrigin, String str, IrType irType, Function1 body, int i, Object obj) {
        IrVariableSymbol symbol;
        IrVariable irVariableCreateTemporaryVariable = null;
        IrStatementOrigin irStatementOrigin2 = (i & 2) != 0 ? null : irStatementOrigin;
        String str2 = (i & 4) != 0 ? null : str;
        IrType irType2 = (i & 8) != 0 ? null : irType;
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(body, "body");
        if (value instanceof IrGetValue) {
            IrGetValue irGetValue = (IrGetValue) value;
            if (IrUtilsKt.isImmutable(irGetValue.getSymbol().getOwner())) {
                symbol = irGetValue.getSymbol();
            } else {
                irVariableCreateTemporaryVariable = irBuilderWithScope.getScope().createTemporaryVariable(value, (108 & 2) != 0 ? null : str2, (108 & 4) != 0 ? false : false, (108 & 8) != 0 ? IrDeclarationOrigin.IR_TEMPORARY_VARIABLE.INSTANCE : null, (108 & 16) == 0 ? irType2 : null, (108 & 32) != 0 ? value.getStartOffset() : 0, (108 & 64) != 0 ? value.getEndOffset() : 0);
                symbol = irVariableCreateTemporaryVariable.getSymbol();
            }
        } else {
            irVariableCreateTemporaryVariable = irBuilderWithScope.getScope().createTemporaryVariable(value, (108 & 2) != 0 ? null : str2, (108 & 4) != 0 ? false : false, (108 & 8) != 0 ? IrDeclarationOrigin.IR_TEMPORARY_VARIABLE.INSTANCE : null, (108 & 16) == 0 ? irType2 : null, (108 & 32) != 0 ? value.getStartOffset() : 0, (108 & 64) != 0 ? value.getEndOffset() : 0);
            symbol = irVariableCreateTemporaryVariable.getSymbol();
        }
        IrExpression irExpression = (IrExpression) body.invoke(symbol);
        if (irVariableCreateTemporaryVariable == null) {
            return irExpression;
        }
        IrBlockImpl irBlockImpl = new IrBlockImpl(irBuilderWithScope.getStartOffset(), irBuilderWithScope.getEndOffset(), irExpression.getType(), irStatementOrigin2);
        irBlockImpl.getStatements().add(irVariableCreateTemporaryVariable);
        if (irExpression instanceof IrStatementContainer) {
            irBlockImpl.getStatements().addAll(((IrStatementContainer) irExpression).getStatements());
        } else {
            irBlockImpl.getStatements().add(irExpression);
        }
        return irBlockImpl;
    }

    /* JADX WARN: Code duplicated, block: B:7:0x002a  */
    /* JADX WARN: Multi-variable type inference failed */
    public static final IrExpression irLetS(IrBuilderWithScope irBuilderWithScope, IrExpression value, IrStatementOrigin irStatementOrigin, String str, IrType irType, Function1<? super IrValueSymbol, ? extends IrExpression> body) {
        IrVariable irVariableCreateTemporaryVariable;
        IrVariableSymbol symbol;
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(body, "body");
        if (value instanceof IrGetValue) {
            IrGetValue irGetValue = (IrGetValue) value;
            if (IrUtilsKt.isImmutable(irGetValue.getSymbol().getOwner())) {
                symbol = irGetValue.getSymbol();
                irVariableCreateTemporaryVariable = null;
            } else {
                irVariableCreateTemporaryVariable = irBuilderWithScope.getScope().createTemporaryVariable(value, (108 & 2) != 0 ? null : str, (108 & 4) != 0 ? false : false, (108 & 8) != 0 ? IrDeclarationOrigin.IR_TEMPORARY_VARIABLE.INSTANCE : null, (108 & 16) == 0 ? irType : null, (108 & 32) != 0 ? value.getStartOffset() : 0, (108 & 64) != 0 ? value.getEndOffset() : 0);
                symbol = irVariableCreateTemporaryVariable.getSymbol();
            }
        } else {
            irVariableCreateTemporaryVariable = irBuilderWithScope.getScope().createTemporaryVariable(value, (108 & 2) != 0 ? null : str, (108 & 4) != 0 ? false : false, (108 & 8) != 0 ? IrDeclarationOrigin.IR_TEMPORARY_VARIABLE.INSTANCE : null, (108 & 16) == 0 ? irType : null, (108 & 32) != 0 ? value.getStartOffset() : 0, (108 & 64) != 0 ? value.getEndOffset() : 0);
            symbol = irVariableCreateTemporaryVariable.getSymbol();
        }
        IrExpression irExpressionInvoke = body.invoke(symbol);
        if (irVariableCreateTemporaryVariable == null) {
            return irExpressionInvoke;
        }
        IrBlockImpl irBlockImpl = new IrBlockImpl(irBuilderWithScope.getStartOffset(), irBuilderWithScope.getEndOffset(), irExpressionInvoke.getType(), irStatementOrigin);
        irBlockImpl.getStatements().add(irVariableCreateTemporaryVariable);
        if (irExpressionInvoke instanceof IrStatementContainer) {
            irBlockImpl.getStatements().addAll(((IrStatementContainer) irExpressionInvoke).getStatements());
        } else {
            irBlockImpl.getStatements().add(irExpressionInvoke);
        }
        return irBlockImpl;
    }

    public static /* synthetic */ IrVariable irTemporary$default(IrStatementsBuilder irStatementsBuilder, IrExpression irExpression, String str, IrType irType, boolean z, IrDeclarationOrigin irDeclarationOrigin, int i, Object obj) {
        if ((i & 1) != 0) {
            irExpression = null;
        }
        if ((i & 2) != 0) {
            str = null;
        }
        if ((i & 4) != 0) {
            irType = irExpression != null ? irExpression.getType() : null;
            Intrinsics.checkNotNull(irType);
        }
        if ((i & 8) != 0) {
            z = false;
        }
        if ((i & 16) != 0) {
            irDeclarationOrigin = IrDeclarationOrigin.IR_TEMPORARY_VARIABLE.INSTANCE;
        }
        return irTemporary(irStatementsBuilder, irExpression, str, irType, z, irDeclarationOrigin);
    }

    public static final <T extends IrElement> IrVariable irTemporary(IrStatementsBuilder<? extends T> irStatementsBuilder, IrExpression irExpression, String str, IrType irType, boolean z, IrDeclarationOrigin origin) {
        Intrinsics.checkNotNullParameter(irStatementsBuilder, "<this>");
        Intrinsics.checkNotNullParameter(irType, "irType");
        Intrinsics.checkNotNullParameter(origin, "origin");
        IrVariable irVariableCreateTemporaryVariableDeclaration = irStatementsBuilder.getScope().createTemporaryVariableDeclaration(irType, str, z, origin, irStatementsBuilder.getStartOffset(), irStatementsBuilder.getEndOffset());
        if (irExpression != null) {
            irVariableCreateTemporaryVariableDeclaration.setInitializer(irExpression);
        }
        irStatementsBuilder.unaryPlus(irVariableCreateTemporaryVariableDeclaration);
        return irVariableCreateTemporaryVariableDeclaration;
    }

    public static final IrExpressionBody irExprBody(IrBuilderWithScope irBuilderWithScope, IrExpression value) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(value, "value");
        return irBuilderWithScope.getContext().getIrFactory().createExpressionBody(irBuilderWithScope.getStartOffset(), irBuilderWithScope.getEndOffset(), value);
    }

    public static final IrWhenImpl irWhen(IrBuilderWithScope irBuilderWithScope, IrType type, List<? extends IrBranch> branches) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(branches, "branches");
        return new IrWhenImpl(irBuilderWithScope.getStartOffset(), irBuilderWithScope.getEndOffset(), type, null, branches);
    }

    public static final IrReturnImpl irReturn(IrBuilderWithScope irBuilderWithScope, IrExpression value) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(value, "value");
        int startOffset = irBuilderWithScope.getStartOffset();
        int endOffset = irBuilderWithScope.getEndOffset();
        IrType nothingType = irBuilderWithScope.getContext().getIrBuiltIns().getNothingType();
        IrSymbol scopeOwnerSymbol = irBuilderWithScope.getScope().getScopeOwnerSymbol();
        IrReturnTargetSymbol irReturnTargetSymbol = scopeOwnerSymbol instanceof IrReturnTargetSymbol ? (IrReturnTargetSymbol) scopeOwnerSymbol : null;
        if (irReturnTargetSymbol != null) {
            return new IrReturnImpl(startOffset, endOffset, nothingType, irReturnTargetSymbol, value);
        }
        throw new AssertionError("Function scope expected: " + RenderIrElementKt.render$default(irBuilderWithScope.getScope().getScopeOwnerSymbol().getOwner(), (DumpIrTreeOptions) null, 1, (Object) null));
    }

    public static final IrConstImpl<Boolean> irBoolean(IrBuilderWithScope irBuilderWithScope, boolean z) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        return new IrConstImpl<>(irBuilderWithScope.getStartOffset(), irBuilderWithScope.getEndOffset(), irBuilderWithScope.getContext().getIrBuiltIns().getBooleanType(), IrConstKind.Boolean.INSTANCE, Boolean.valueOf(z));
    }

    public static final IrGetObjectValueImpl irUnit(IrBuilderWithScope irBuilderWithScope) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        return irGetObjectValue(irBuilderWithScope, irBuilderWithScope.getContext().getIrBuiltIns().getUnitType(), irBuilderWithScope.getContext().getIrBuiltIns().getUnitClass());
    }

    public static final IrConstImpl<Boolean> irTrue(IrBuilderWithScope irBuilderWithScope) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        return irBoolean(irBuilderWithScope, true);
    }

    public static final IrConstImpl<Boolean> irFalse(IrBuilderWithScope irBuilderWithScope) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        return irBoolean(irBuilderWithScope, false);
    }

    public static final IrReturnImpl irReturnTrue(IrBuilderWithScope irBuilderWithScope) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        return irReturn(irBuilderWithScope, irTrue(irBuilderWithScope));
    }

    public static final IrReturnImpl irReturnFalse(IrBuilderWithScope irBuilderWithScope) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        return irReturn(irBuilderWithScope, irFalse(irBuilderWithScope));
    }

    public static final IrReturnImpl irReturnUnit(IrBuilderWithScope irBuilderWithScope) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        return irReturn(irBuilderWithScope, irUnit(irBuilderWithScope));
    }

    public static final IrBranchImpl irBranch(IrBuilderWithScope irBuilderWithScope, IrExpression condition, IrExpression result) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(condition, "condition");
        Intrinsics.checkNotNullParameter(result, "result");
        return new IrBranchImpl(irBuilderWithScope.getStartOffset(), irBuilderWithScope.getEndOffset(), condition, result);
    }

    public static final IrElseBranchImpl irElseBranch(IrBuilderWithScope irBuilderWithScope, IrExpression expression) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(expression, "expression");
        return new IrElseBranchImpl(irBuilderWithScope.getStartOffset(), irBuilderWithScope.getEndOffset(), irTrue(irBuilderWithScope), expression);
    }

    public static /* synthetic */ IrIfThenElseImpl irIfThen$default(IrBuilderWithScope irBuilderWithScope, IrType irType, IrExpression irExpression, IrExpression irExpression2, IrStatementOrigin irStatementOrigin, int i, Object obj) {
        if ((i & 8) != 0) {
            irStatementOrigin = null;
        }
        return irIfThen(irBuilderWithScope, irType, irExpression, irExpression2, irStatementOrigin);
    }

    public static final IrIfThenElseImpl irIfThen(IrBuilderWithScope irBuilderWithScope, IrType type, IrExpression condition, IrExpression thenPart, IrStatementOrigin irStatementOrigin) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(condition, "condition");
        Intrinsics.checkNotNullParameter(thenPart, "thenPart");
        IrIfThenElseImpl irIfThenElseImpl = new IrIfThenElseImpl(irBuilderWithScope.getStartOffset(), irBuilderWithScope.getEndOffset(), type, irStatementOrigin);
        irIfThenElseImpl.getBranches().add(new IrBranchImpl(irIfThenElseImpl.getStartOffset(), irIfThenElseImpl.getEndOffset(), condition, thenPart));
        return irIfThenElseImpl;
    }

    public static /* synthetic */ IrIfThenElseImpl irIfThenElse$default(IrBuilderWithScope irBuilderWithScope, IrType irType, IrExpression irExpression, IrExpression irExpression2, IrExpression irExpression3, IrStatementOrigin irStatementOrigin, int i, Object obj) {
        if ((i & 16) != 0) {
            irStatementOrigin = null;
        }
        return irIfThenElse(irBuilderWithScope, irType, irExpression, irExpression2, irExpression3, irStatementOrigin);
    }

    public static final IrIfThenElseImpl irIfThenElse(IrBuilderWithScope irBuilderWithScope, IrType type, IrExpression condition, IrExpression thenPart, IrExpression elsePart, IrStatementOrigin irStatementOrigin) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(condition, "condition");
        Intrinsics.checkNotNullParameter(thenPart, "thenPart");
        Intrinsics.checkNotNullParameter(elsePart, "elsePart");
        IrIfThenElseImpl irIfThenElseImpl = new IrIfThenElseImpl(irBuilderWithScope.getStartOffset(), irBuilderWithScope.getEndOffset(), type, irStatementOrigin);
        irIfThenElseImpl.getBranches().add(new IrBranchImpl(irIfThenElseImpl.getStartOffset(), irIfThenElseImpl.getEndOffset(), condition, thenPart));
        irIfThenElseImpl.getBranches().add(irElseBranch(irBuilderWithScope, elsePart));
        return irIfThenElseImpl;
    }

    public static /* synthetic */ IrIfThenElseImpl irIfThenMaybeElse$default(IrBuilderWithScope irBuilderWithScope, IrType irType, IrExpression irExpression, IrExpression irExpression2, IrExpression irExpression3, IrStatementOrigin irStatementOrigin, int i, Object obj) {
        if ((i & 16) != 0) {
            irStatementOrigin = null;
        }
        return irIfThenMaybeElse(irBuilderWithScope, irType, irExpression, irExpression2, irExpression3, irStatementOrigin);
    }

    public static final IrIfThenElseImpl irIfThenMaybeElse(IrBuilderWithScope irBuilderWithScope, IrType type, IrExpression condition, IrExpression thenPart, IrExpression irExpression, IrStatementOrigin irStatementOrigin) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(condition, "condition");
        Intrinsics.checkNotNullParameter(thenPart, "thenPart");
        if (irExpression != null) {
            return irIfThenElse(irBuilderWithScope, type, condition, thenPart, irExpression, irStatementOrigin);
        }
        return irIfThen(irBuilderWithScope, type, condition, thenPart, irStatementOrigin);
    }

    public static final IrIfThenElseImpl irIfNull(IrBuilderWithScope irBuilderWithScope, IrType type, IrExpression subject, IrExpression thenPart, IrExpression elsePart) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(subject, "subject");
        Intrinsics.checkNotNullParameter(thenPart, "thenPart");
        Intrinsics.checkNotNullParameter(elsePart, "elsePart");
        return irIfThenElse$default(irBuilderWithScope, type, irEqualsNull(irBuilderWithScope, subject), thenPart, elsePart, null, 16, null);
    }

    public static final IrIfThenElseImpl irIfThenReturnTrue(IrBuilderWithScope irBuilderWithScope, IrExpression condition) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(condition, "condition");
        return irIfThen$default(irBuilderWithScope, irBuilderWithScope.getContext().getIrBuiltIns().getUnitType(), condition, irReturnTrue(irBuilderWithScope), null, 8, null);
    }

    public static final IrIfThenElseImpl irIfThenReturnFalse(IrBuilderWithScope irBuilderWithScope, IrExpression condition) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(condition, "condition");
        return irIfThen$default(irBuilderWithScope, irBuilderWithScope.getContext().getIrBuiltIns().getUnitType(), condition, irReturnFalse(irBuilderWithScope), null, 8, null);
    }

    public static final IrGetValueImpl irGet(IrBuilderWithScope irBuilderWithScope, IrType type, IrValueSymbol variable) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(variable, "variable");
        return new IrGetValueImpl(irBuilderWithScope.getStartOffset(), irBuilderWithScope.getEndOffset(), type, variable, null, 16, null);
    }

    public static final IrGetValueImpl irGet(IrBuilderWithScope irBuilderWithScope, IrValueDeclaration variable) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(variable, "variable");
        return irGet(irBuilderWithScope, variable.getType(), variable.getSymbol());
    }

    public static final IrGetValueImpl irGet(IrBuilderWithScope irBuilderWithScope, IrValueDeclaration variable, IrType type) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(variable, "variable");
        Intrinsics.checkNotNullParameter(type, "type");
        return irGet(irBuilderWithScope, type, variable.getSymbol());
    }

    public static /* synthetic */ IrSetValueImpl irSet$default(IrBuilderWithScope irBuilderWithScope, IrValueSymbol irValueSymbol, IrExpression irExpression, IrStatementOrigin irStatementOrigin, int i, Object obj) {
        if ((i & 4) != 0) {
            irStatementOrigin = IrStatementOrigin.EQ.INSTANCE;
        }
        return irSet(irBuilderWithScope, irValueSymbol, irExpression, irStatementOrigin);
    }

    public static final IrSetValueImpl irSet(IrBuilderWithScope irBuilderWithScope, IrValueSymbol variable, IrExpression value, IrStatementOrigin origin) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(variable, "variable");
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(origin, "origin");
        return new IrSetValueImpl(irBuilderWithScope.getStartOffset(), irBuilderWithScope.getEndOffset(), irBuilderWithScope.getContext().getIrBuiltIns().getUnitType(), variable, value, origin);
    }

    public static /* synthetic */ IrSetValueImpl irSet$default(IrBuilderWithScope irBuilderWithScope, IrValueDeclaration irValueDeclaration, IrExpression irExpression, IrStatementOrigin irStatementOrigin, int i, Object obj) {
        if ((i & 4) != 0) {
            irStatementOrigin = IrStatementOrigin.EQ.INSTANCE;
        }
        return irSet(irBuilderWithScope, irValueDeclaration, irExpression, irStatementOrigin);
    }

    public static final IrSetValueImpl irSet(IrBuilderWithScope irBuilderWithScope, IrValueDeclaration variable, IrExpression value, IrStatementOrigin origin) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(variable, "variable");
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(origin, "origin");
        return irSet(irBuilderWithScope, variable.getSymbol(), value, origin);
    }

    public static /* synthetic */ IrGetFieldImpl irGetField$default(IrBuilderWithScope irBuilderWithScope, IrExpression irExpression, IrField irField, IrType irType, int i, Object obj) {
        if ((i & 4) != 0) {
            irType = irField.getType();
        }
        return irGetField(irBuilderWithScope, irExpression, irField, irType);
    }

    public static final IrGetFieldImpl irGetField(IrBuilderWithScope irBuilderWithScope, IrExpression irExpression, IrField field, IrType type) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(field, "field");
        Intrinsics.checkNotNullParameter(type, "type");
        return new IrGetFieldImpl(irBuilderWithScope.getStartOffset(), irBuilderWithScope.getEndOffset(), field.getSymbol(), type, irExpression, null, null, 96, null);
    }

    public static /* synthetic */ IrSetFieldImpl irSetField$default(IrBuilderWithScope irBuilderWithScope, IrExpression irExpression, IrField irField, IrExpression irExpression2, IrStatementOrigin irStatementOrigin, int i, Object obj) {
        if ((i & 8) != 0) {
            irStatementOrigin = null;
        }
        return irSetField(irBuilderWithScope, irExpression, irField, irExpression2, irStatementOrigin);
    }

    public static final IrSetFieldImpl irSetField(IrBuilderWithScope irBuilderWithScope, IrExpression irExpression, IrField field, IrExpression value, IrStatementOrigin irStatementOrigin) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(field, "field");
        Intrinsics.checkNotNullParameter(value, "value");
        return new IrSetFieldImpl(irBuilderWithScope.getStartOffset(), irBuilderWithScope.getEndOffset(), field.getSymbol(), irExpression, value, irBuilderWithScope.getContext().getIrBuiltIns().getUnitType(), irStatementOrigin, null, 128, null);
    }

    public static final IrGetObjectValueImpl irGetObjectValue(IrBuilderWithScope irBuilderWithScope, IrType type, IrClassSymbol classSymbol) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(classSymbol, "classSymbol");
        return new IrGetObjectValueImpl(irBuilderWithScope.getStartOffset(), irBuilderWithScope.getEndOffset(), type, classSymbol);
    }

    public static final IrExpression irEqeqeq(IrBuilderWithScope irBuilderWithScope, IrExpression arg1, IrExpression arg2) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(arg1, "arg1");
        Intrinsics.checkNotNullParameter(arg2, "arg2");
        return PrimitivesKt.eqeqeq(irBuilderWithScope.getContext(), irBuilderWithScope.getStartOffset(), irBuilderWithScope.getEndOffset(), arg1, arg2);
    }

    public static final IrExpression irEqeqeqWithoutBox(IrBuilderWithScope irBuilderWithScope, IrExpression arg1, IrExpression arg2) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(arg1, "arg1");
        Intrinsics.checkNotNullParameter(arg2, "arg2");
        return PrimitivesKt.primitiveOp2(irBuilderWithScope.getStartOffset(), irBuilderWithScope.getEndOffset(), irBuilderWithScope.getContext().getIrBuiltIns().getEqeqeqSymbol(), irBuilderWithScope.getContext().getIrBuiltIns().getBooleanType(), IrStatementOrigin.SYNTHETIC_NOT_AUTOBOXED_CHECK.INSTANCE, arg1, arg2);
    }

    public static final IrConstImpl irNull(IrBuilderWithScope irBuilderWithScope) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        return irNull(irBuilderWithScope, irBuilderWithScope.getContext().getIrBuiltIns().getNothingNType());
    }

    public static final IrConstImpl irNull(IrBuilderWithScope irBuilderWithScope, IrType irType) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(irType, "irType");
        return IrConstImpl.INSTANCE.constNull(irBuilderWithScope.getStartOffset(), irBuilderWithScope.getEndOffset(), irType);
    }

    public static final IrExpression irEqualsNull(IrBuilderWithScope irBuilderWithScope, IrExpression argument) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(argument, "argument");
        return irEquals$default(irBuilderWithScope, argument, irNull(irBuilderWithScope), null, 4, null);
    }

    public static /* synthetic */ IrExpression irEquals$default(IrBuilderWithScope irBuilderWithScope, IrExpression irExpression, IrExpression irExpression2, IrStatementOrigin irStatementOrigin, int i, Object obj) {
        if ((i & 4) != 0) {
            irStatementOrigin = IrStatementOrigin.EQEQ.INSTANCE;
        }
        return irEquals(irBuilderWithScope, irExpression, irExpression2, irStatementOrigin);
    }

    public static final IrExpression irEquals(IrBuilderWithScope irBuilderWithScope, IrExpression arg1, IrExpression arg2, IrStatementOrigin origin) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(arg1, "arg1");
        Intrinsics.checkNotNullParameter(arg2, "arg2");
        Intrinsics.checkNotNullParameter(origin, "origin");
        return PrimitivesKt.primitiveOp2(irBuilderWithScope.getStartOffset(), irBuilderWithScope.getEndOffset(), irBuilderWithScope.getContext().getIrBuiltIns().getEqeqSymbol(), irBuilderWithScope.getContext().getIrBuiltIns().getBooleanType(), origin, arg1, arg2);
    }

    public static final IrExpression irNotEquals(IrBuilderWithScope irBuilderWithScope, IrExpression arg1, IrExpression arg2) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(arg1, "arg1");
        Intrinsics.checkNotNullParameter(arg2, "arg2");
        return PrimitivesKt.primitiveOp1(irBuilderWithScope.getStartOffset(), irBuilderWithScope.getEndOffset(), irBuilderWithScope.getContext().getIrBuiltIns().getBooleanNotSymbol(), irBuilderWithScope.getContext().getIrBuiltIns().getBooleanType(), IrStatementOrigin.EXCLEQ.INSTANCE, irEquals(irBuilderWithScope, arg1, arg2, IrStatementOrigin.EXCLEQ.INSTANCE));
    }

    public static final IrCall irGet(IrBuilderWithScope irBuilderWithScope, IrType type, IrExpression irExpression, IrFunctionSymbol getterSymbol) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(getterSymbol, "getterSymbol");
        IrSimpleFunctionSymbol irSimpleFunctionSymbol = (IrSimpleFunctionSymbol) getterSymbol;
        IrCallImpl irCallImpl = new IrCallImpl(irBuilderWithScope.getStartOffset(), irBuilderWithScope.getEndOffset(), type, irSimpleFunctionSymbol, irSimpleFunctionSymbol.getOwner().getTypeParameters().size(), 0, IrStatementOrigin.GET_PROPERTY.INSTANCE, null, 128, null);
        irCallImpl.setDispatchReceiver(irExpression);
        return irCallImpl;
    }

    public static final IrCall irSet(IrBuilderWithScope irBuilderWithScope, IrType type, IrExpression irExpression, IrFunctionSymbol setterSymbol, IrExpression value) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(setterSymbol, "setterSymbol");
        Intrinsics.checkNotNullParameter(value, "value");
        IrSimpleFunctionSymbol irSimpleFunctionSymbol = (IrSimpleFunctionSymbol) setterSymbol;
        IrCallImpl irCallImpl = new IrCallImpl(irBuilderWithScope.getStartOffset(), irBuilderWithScope.getEndOffset(), type, irSimpleFunctionSymbol, irSimpleFunctionSymbol.getOwner().getTypeParameters().size(), 1, IrStatementOrigin.EQ.INSTANCE, null, 128, null);
        irCallImpl.setDispatchReceiver(irExpression);
        irCallImpl.putValueArgument(0, value);
        return irCallImpl;
    }

    public static final IrMemberAccessExpression<?> irCall(IrBuilderWithScope irBuilderWithScope, IrFunctionSymbol callee, IrType type, List<? extends IrType> typeArguments) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(callee, "callee");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(typeArguments, "typeArguments");
        IrFunctionAccessExpression irFunctionAccessExpressionIrCall = irCall(irBuilderWithScope, callee, type);
        int i = 0;
        for (Object obj : typeArguments) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            irFunctionAccessExpressionIrCall.putTypeArgument(i, (IrType) obj);
            i = i2;
        }
        return irFunctionAccessExpressionIrCall;
    }

    public static final IrConstructorCall irCallConstructor(IrBuilderWithScope irBuilderWithScope, IrConstructorSymbol callee, List<? extends IrType> typeArguments) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(callee, "callee");
        Intrinsics.checkNotNullParameter(typeArguments, "typeArguments");
        IrConstructorCallImpl irConstructorCallImplFromSymbolOwner$default = IrConstructorCallImpl.Companion.fromSymbolOwner$default(IrConstructorCallImpl.INSTANCE, irBuilderWithScope.getStartOffset(), irBuilderWithScope.getEndOffset(), callee.getOwner().getReturnType(), callee, typeArguments.size() - callee.getOwner().getTypeParameters().size(), null, 32, null);
        int i = 0;
        for (Object obj : typeArguments) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            irConstructorCallImplFromSymbolOwner$default.putTypeArgument(i, (IrType) obj);
            i = i2;
        }
        return irConstructorCallImplFromSymbolOwner$default;
    }

    public static /* synthetic */ IrCall irCall$default(IrBuilderWithScope irBuilderWithScope, IrSimpleFunctionSymbol irSimpleFunctionSymbol, IrType irType, int i, int i2, IrStatementOrigin irStatementOrigin, int i3, Object obj) {
        if ((i3 & 4) != 0) {
            i = irSimpleFunctionSymbol.getOwner().getValueParameters().size();
        }
        int i4 = i;
        if ((i3 & 8) != 0) {
            i2 = irSimpleFunctionSymbol.getOwner().getTypeParameters().size();
        }
        int i5 = i2;
        if ((i3 & 16) != 0) {
            irStatementOrigin = null;
        }
        return irCall(irBuilderWithScope, irSimpleFunctionSymbol, irType, i4, i5, irStatementOrigin);
    }

    public static final IrCall irCall(IrBuilderWithScope irBuilderWithScope, IrSimpleFunctionSymbol callee, IrType type, int i, int i2, IrStatementOrigin irStatementOrigin) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(callee, "callee");
        Intrinsics.checkNotNullParameter(type, "type");
        return new IrCallImpl(irBuilderWithScope.getStartOffset(), irBuilderWithScope.getEndOffset(), type, callee, i2, i, irStatementOrigin, null, 128, null);
    }

    public static /* synthetic */ IrConstructorCall irCall$default(IrBuilderWithScope irBuilderWithScope, IrConstructorSymbol irConstructorSymbol, IrType irType, IrClass irClass, int i, Object obj) {
        if ((i & 4) != 0) {
            irClass = IrUtilsKt.getParentAsClass(irConstructorSymbol.getOwner());
        }
        return irCall(irBuilderWithScope, irConstructorSymbol, irType, irClass);
    }

    public static final IrConstructorCall irCall(IrBuilderWithScope irBuilderWithScope, IrConstructorSymbol callee, IrType type, IrClass constructedClass) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(callee, "callee");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(constructedClass, "constructedClass");
        return new IrConstructorCallImpl(irBuilderWithScope.getStartOffset(), irBuilderWithScope.getEndOffset(), type, callee, callee.getOwner().getTypeParameters().size() + constructedClass.getTypeParameters().size(), callee.getOwner().getTypeParameters().size(), callee.getOwner().getValueParameters().size(), null, null, AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP, null);
    }

    public static final IrFunctionAccessExpression irCall(IrBuilderWithScope irBuilderWithScope, IrFunctionSymbol callee, IrType type) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(callee, "callee");
        Intrinsics.checkNotNullParameter(type, "type");
        if (callee instanceof IrConstructorSymbol) {
            return irCall$default(irBuilderWithScope, (IrConstructorSymbol) callee, type, (IrClass) null, 4, (Object) null);
        }
        if (callee instanceof IrSimpleFunctionSymbol) {
            return irCall$default(irBuilderWithScope, (IrSimpleFunctionSymbol) callee, type, 0, 0, null, 28, null);
        }
        throw new NoWhenBranchMatchedException();
    }

    public static final IrCall irCall(IrBuilderWithScope irBuilderWithScope, IrSimpleFunctionSymbol callee) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(callee, "callee");
        return irCall$default(irBuilderWithScope, callee, callee.getOwner().getReturnType(), 0, 0, null, 28, null);
    }

    public static final IrConstructorCall irCall(IrBuilderWithScope irBuilderWithScope, IrConstructorSymbol callee) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(callee, "callee");
        return irCall$default(irBuilderWithScope, callee, callee.getOwner().getReturnType(), (IrClass) null, 4, (Object) null);
    }

    public static final IrFunctionAccessExpression irCall(IrBuilderWithScope irBuilderWithScope, IrFunctionSymbol callee) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(callee, "callee");
        return irCall(irBuilderWithScope, callee, callee.getOwner().getReturnType());
    }

    public static final IrFunctionAccessExpression irCall(IrBuilderWithScope irBuilderWithScope, IrFunction callee) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(callee, "callee");
        return irCall(irBuilderWithScope, callee.getSymbol());
    }

    public static /* synthetic */ IrCall irCall$default(IrBuilderWithScope irBuilderWithScope, IrFunction irFunction, IrStatementOrigin irStatementOrigin, IrClassSymbol irClassSymbol, int i, Object obj) {
        if ((i & 2) != 0) {
            irStatementOrigin = null;
        }
        if ((i & 4) != 0) {
            irClassSymbol = null;
        }
        return irCall(irBuilderWithScope, irFunction, irStatementOrigin, irClassSymbol);
    }

    public static final IrCall irCall(IrBuilderWithScope irBuilderWithScope, IrFunction callee, IrStatementOrigin irStatementOrigin, IrClassSymbol irClassSymbol) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(callee, "callee");
        int startOffset = irBuilderWithScope.getStartOffset();
        int endOffset = irBuilderWithScope.getEndOffset();
        IrType returnType = callee.getReturnType();
        IrFunctionSymbol symbol = callee.getSymbol();
        Intrinsics.checkNotNull(symbol, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol");
        return new IrCallImpl(startOffset, endOffset, returnType, (IrSimpleFunctionSymbol) symbol, callee.getTypeParameters().size(), callee.getValueParameters().size(), irStatementOrigin, irClassSymbol);
    }

    public static final IrDelegatingConstructorCall irDelegatingConstructorCall(IrBuilderWithScope irBuilderWithScope, IrConstructor callee) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(callee, "callee");
        return new IrDelegatingConstructorCallImpl(irBuilderWithScope.getStartOffset(), irBuilderWithScope.getEndOffset(), irBuilderWithScope.getContext().getIrBuiltIns().getUnitType(), callee.getSymbol(), IrUtilsKt.getParentAsClass(callee).getTypeParameters().size(), callee.getValueParameters().size());
    }

    public static final IrMemberAccessExpression<?> irCallOp(IrBuilderWithScope irBuilderWithScope, IrSimpleFunctionSymbol callee, IrType type, IrExpression dispatchReceiver, IrExpression irExpression, IrStatementOrigin irStatementOrigin) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(callee, "callee");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(dispatchReceiver, "dispatchReceiver");
        IrCall irCall = irCall(irBuilderWithScope, callee, type, irExpression != null ? 1 : 0, 0, irStatementOrigin);
        irCall.setDispatchReceiver(dispatchReceiver);
        if (irExpression != null) {
            irCall.putValueArgument(0, irExpression);
        }
        return irCall;
    }

    public static final IrTypeOperatorCallImpl typeOperator(IrBuilderWithScope irBuilderWithScope, IrType resultType, IrExpression argument, IrTypeOperator typeOperator, IrType typeOperand) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(resultType, "resultType");
        Intrinsics.checkNotNullParameter(argument, "argument");
        Intrinsics.checkNotNullParameter(typeOperator, "typeOperator");
        Intrinsics.checkNotNullParameter(typeOperand, "typeOperand");
        return new IrTypeOperatorCallImpl(irBuilderWithScope.getStartOffset(), irBuilderWithScope.getEndOffset(), resultType, typeOperator, typeOperand, argument);
    }

    public static final IrTypeOperatorCallImpl irIs(IrBuilderWithScope irBuilderWithScope, IrExpression argument, IrType type) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(argument, "argument");
        Intrinsics.checkNotNullParameter(type, "type");
        return typeOperator(irBuilderWithScope, irBuilderWithScope.getContext().getIrBuiltIns().getBooleanType(), argument, IrTypeOperator.INSTANCEOF, type);
    }

    public static final IrTypeOperatorCallImpl irNotIs(IrBuilderWithScope irBuilderWithScope, IrExpression argument, IrType type) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(argument, "argument");
        Intrinsics.checkNotNullParameter(type, "type");
        return typeOperator(irBuilderWithScope, irBuilderWithScope.getContext().getIrBuiltIns().getBooleanType(), argument, IrTypeOperator.NOT_INSTANCEOF, type);
    }

    public static final IrTypeOperatorCallImpl irAs(IrBuilderWithScope irBuilderWithScope, IrExpression argument, IrType type) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(argument, "argument");
        Intrinsics.checkNotNullParameter(type, "type");
        return new IrTypeOperatorCallImpl(irBuilderWithScope.getStartOffset(), irBuilderWithScope.getEndOffset(), type, IrTypeOperator.CAST, type, argument);
    }

    public static final IrTypeOperatorCallImpl irImplicitCast(IrBuilderWithScope irBuilderWithScope, IrExpression argument, IrType type) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(argument, "argument");
        Intrinsics.checkNotNullParameter(type, "type");
        return new IrTypeOperatorCallImpl(irBuilderWithScope.getStartOffset(), irBuilderWithScope.getEndOffset(), type, IrTypeOperator.IMPLICIT_CAST, type, argument);
    }

    public static final IrTypeOperatorCallImpl irReinterpretCast(IrBuilderWithScope irBuilderWithScope, IrExpression argument, IrType type) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(argument, "argument");
        Intrinsics.checkNotNullParameter(type, "type");
        return new IrTypeOperatorCallImpl(irBuilderWithScope.getStartOffset(), irBuilderWithScope.getEndOffset(), type, IrTypeOperator.REINTERPRET_CAST, type, argument);
    }

    public static final IrTypeOperatorCallImpl irSamConversion(IrBuilderWithScope irBuilderWithScope, IrExpression argument, IrType type) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(argument, "argument");
        Intrinsics.checkNotNullParameter(type, "type");
        return typeOperator(irBuilderWithScope, type, argument, IrTypeOperator.SAM_CONVERSION, type);
    }

    public static /* synthetic */ IrConstImpl irInt$default(IrBuilderWithScope irBuilderWithScope, int i, IrType irType, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            irType = irBuilderWithScope.getContext().getIrBuiltIns().getIntType();
        }
        return irInt(irBuilderWithScope, i, irType);
    }

    public static final IrConstImpl<Integer> irInt(IrBuilderWithScope irBuilderWithScope, int i, IrType type) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        return IrConstImpl.INSTANCE.m7211int(irBuilderWithScope.getStartOffset(), irBuilderWithScope.getEndOffset(), type, i);
    }

    public static /* synthetic */ IrConstImpl irLong$default(IrBuilderWithScope irBuilderWithScope, long j, IrType irType, int i, Object obj) {
        if ((i & 2) != 0) {
            irType = irBuilderWithScope.getContext().getIrBuiltIns().getLongType();
        }
        return irLong(irBuilderWithScope, j, irType);
    }

    public static final IrConstImpl<Long> irLong(IrBuilderWithScope irBuilderWithScope, long j, IrType type) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        return IrConstImpl.INSTANCE.m7212long(irBuilderWithScope.getStartOffset(), irBuilderWithScope.getEndOffset(), type, j);
    }

    public static final IrConstImpl<Character> irChar(IrBuilderWithScope irBuilderWithScope, char c) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        return IrConstImpl.INSTANCE.m7208char(irBuilderWithScope.getStartOffset(), irBuilderWithScope.getEndOffset(), irBuilderWithScope.getContext().getIrBuiltIns().getCharType(), c);
    }

    public static final IrConstImpl<String> irString(IrBuilderWithScope irBuilderWithScope, String value) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(value, "value");
        return IrConstImpl.INSTANCE.string(irBuilderWithScope.getStartOffset(), irBuilderWithScope.getEndOffset(), irBuilderWithScope.getContext().getIrBuiltIns().getStringType(), value);
    }

    public static final IrStringConcatenationImpl irConcat(IrBuilderWithScope irBuilderWithScope) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        return new IrStringConcatenationImpl(irBuilderWithScope.getStartOffset(), irBuilderWithScope.getEndOffset(), irBuilderWithScope.getContext().getIrBuiltIns().getStringType());
    }

    public static final IrVarargImpl irVararg(IrBuilderWithScope irBuilderWithScope, IrType elementType, List<? extends IrExpression> values) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(elementType, "elementType");
        Intrinsics.checkNotNullParameter(values, "values");
        return new IrVarargImpl(irBuilderWithScope.getStartOffset(), irBuilderWithScope.getEndOffset(), IrTypesKt.typeWith(irBuilderWithScope.getContext().getIrBuiltIns().getArrayClass(), elementType), elementType, values);
    }

    public static final IrRawFunctionReferenceImpl irRawFunctionReference(IrBuilderWithScope irBuilderWithScope, IrType type, IrFunctionSymbol symbol) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return new IrRawFunctionReferenceImpl(irBuilderWithScope.getStartOffset(), irBuilderWithScope.getEndOffset(), type, symbol);
    }

    public static final IrFunctionReferenceImpl irFunctionReference(IrBuilderWithScope irBuilderWithScope, IrType type, IrFunctionSymbol symbol) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return new IrFunctionReferenceImpl(irBuilderWithScope.getStartOffset(), irBuilderWithScope.getEndOffset(), type, symbol, symbol.getOwner().getTypeParameters().size(), symbol.getOwner().getValueParameters().size(), null, null, 192, null);
    }

    public static final IrTryImpl irTry(IrBuilderWithScope irBuilderWithScope, IrType type, IrExpression tryResult, List<? extends IrCatch> catches, IrExpression irExpression) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(tryResult, "tryResult");
        Intrinsics.checkNotNullParameter(catches, "catches");
        return new IrTryImpl(irBuilderWithScope.getStartOffset(), irBuilderWithScope.getEndOffset(), type, tryResult, catches, irExpression);
    }

    public static /* synthetic */ IrContainerExpression irBlock$default(IrBuilderWithScope irBuilderWithScope, int i, int i2, IrStatementOrigin irStatementOrigin, IrType irType, Function1 body, int i3, Object obj) {
        int startOffset = (i3 & 1) != 0 ? irBuilderWithScope.getStartOffset() : i;
        int endOffset = (i3 & 2) != 0 ? irBuilderWithScope.getEndOffset() : i2;
        IrStatementOrigin irStatementOrigin2 = (i3 & 4) != 0 ? null : irStatementOrigin;
        IrType irType2 = (i3 & 8) != 0 ? null : irType;
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(body, "body");
        IrBlockBuilder irBlockBuilder = new IrBlockBuilder(irBuilderWithScope.getContext(), irBuilderWithScope.getScope(), startOffset, endOffset, irStatementOrigin2, irType2, false, 64, null);
        body.invoke(irBlockBuilder);
        return irBlockBuilder.getIrBlockBody();
    }

    public static final IrContainerExpression irBlock(IrBuilderWithScope irBuilderWithScope, int i, int i2, IrStatementOrigin irStatementOrigin, IrType irType, Function1<? super IrBlockBuilder, Unit> body) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(body, "body");
        IrBlockBuilder irBlockBuilder = new IrBlockBuilder(irBuilderWithScope.getContext(), irBuilderWithScope.getScope(), i, i2, irStatementOrigin, irType, false, 64, null);
        body.invoke(irBlockBuilder);
        return irBlockBuilder.getIrBlockBody();
    }

    public static /* synthetic */ IrExpression irComposite$default(IrBuilderWithScope irBuilderWithScope, int i, int i2, IrStatementOrigin irStatementOrigin, IrType irType, Function1 body, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = irBuilderWithScope.getStartOffset();
        }
        int i4 = i;
        if ((i3 & 2) != 0) {
            i2 = irBuilderWithScope.getEndOffset();
        }
        int i5 = i2;
        IrStatementOrigin irStatementOrigin2 = (i3 & 4) != 0 ? null : irStatementOrigin;
        IrType irType2 = (i3 & 8) != 0 ? null : irType;
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(body, "body");
        IrBlockBuilder irBlockBuilder = new IrBlockBuilder(irBuilderWithScope.getContext(), irBuilderWithScope.getScope(), i4, i5, irStatementOrigin2, irType2, true);
        body.invoke(irBlockBuilder);
        return irBlockBuilder.getIrBlockBody();
    }

    public static final IrExpression irComposite(IrBuilderWithScope irBuilderWithScope, int i, int i2, IrStatementOrigin irStatementOrigin, IrType irType, Function1<? super IrBlockBuilder, Unit> body) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(body, "body");
        IrBlockBuilder irBlockBuilder = new IrBlockBuilder(irBuilderWithScope.getContext(), irBuilderWithScope.getScope(), i, i2, irStatementOrigin, irType, true);
        body.invoke(irBlockBuilder);
        return irBlockBuilder.getIrBlockBody();
    }

    public static /* synthetic */ IrBlockBody irBlockBody$default(IrBuilderWithScope irBuilderWithScope, int i, int i2, Function1 body, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = irBuilderWithScope.getStartOffset();
        }
        if ((i3 & 2) != 0) {
            i2 = irBuilderWithScope.getEndOffset();
        }
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(body, "body");
        IrBlockBodyBuilder irBlockBodyBuilder = new IrBlockBodyBuilder(irBuilderWithScope.getContext(), irBuilderWithScope.getScope(), i, i2);
        body.invoke(irBlockBodyBuilder);
        return irBlockBodyBuilder.getIrBlockBody();
    }

    public static final IrBlockBody irBlockBody(IrBuilderWithScope irBuilderWithScope, int i, int i2, Function1<? super IrBlockBodyBuilder, Unit> body) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(body, "body");
        IrBlockBodyBuilder irBlockBodyBuilder = new IrBlockBodyBuilder(irBuilderWithScope.getContext(), irBuilderWithScope.getScope(), i, i2);
        body.invoke(irBlockBodyBuilder);
        return irBlockBodyBuilder.getIrBlockBody();
    }

    public static final IrConstantPrimitiveImpl irConstantPrimitive(IrBuilderWithScope irBuilderWithScope, IrConst<?> value) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(value, "value");
        return new IrConstantPrimitiveImpl(irBuilderWithScope.getStartOffset(), irBuilderWithScope.getEndOffset(), value);
    }

    public static final IrConstantArrayImpl irConstantArray(IrBuilderWithScope irBuilderWithScope, IrType type, List<? extends IrConstantValue> elements) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(elements, "elements");
        return new IrConstantArrayImpl(irBuilderWithScope.getStartOffset(), irBuilderWithScope.getEndOffset(), type, elements);
    }

    public static /* synthetic */ IrConstantValue irConstantObject$default(IrBuilderWithScope irBuilderWithScope, IrConstructorSymbol irConstructorSymbol, List list, List list2, int i, Object obj) {
        if ((i & 4) != 0) {
            list2 = CollectionsKt.emptyList();
        }
        return irConstantObject(irBuilderWithScope, irConstructorSymbol, (List<? extends IrConstantValue>) list, (List<? extends IrType>) list2);
    }

    public static final IrConstantValue irConstantObject(IrBuilderWithScope irBuilderWithScope, IrConstructorSymbol constructor, List<? extends IrConstantValue> arguments, List<? extends IrType> typeArguments) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        Intrinsics.checkNotNullParameter(typeArguments, "typeArguments");
        return new IrConstantObjectImpl(irBuilderWithScope.getStartOffset(), irBuilderWithScope.getEndOffset(), constructor, arguments, typeArguments, null, 32, null);
    }

    public static /* synthetic */ IrConstantValue irConstantObject$default(IrBuilderWithScope irBuilderWithScope, IrClass irClass, List list, List list2, int i, Object obj) {
        if ((i & 4) != 0) {
            list2 = CollectionsKt.emptyList();
        }
        return irConstantObject(irBuilderWithScope, irClass, (List<? extends IrConstantValue>) list, (List<? extends IrType>) list2);
    }

    public static final IrConstantValue irConstantObject(IrBuilderWithScope irBuilderWithScope, IrClass clazz, List<? extends IrConstantValue> arguments, List<? extends IrType> typeArguments) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        Intrinsics.checkNotNullParameter(typeArguments, "typeArguments");
        IrConstructor primaryConstructor = IrUtilsKt.getPrimaryConstructor(clazz);
        IrConstructorSymbol symbol = primaryConstructor != null ? primaryConstructor.getSymbol() : null;
        Intrinsics.checkNotNull(symbol);
        return irConstantObject(irBuilderWithScope, symbol, arguments, typeArguments);
    }

    public static /* synthetic */ IrConstantValue irConstantObject$default(IrBuilderWithScope irBuilderWithScope, IrClass irClass, Map map, List list, int i, Object obj) {
        if ((i & 4) != 0) {
            list = CollectionsKt.emptyList();
        }
        return irConstantObject(irBuilderWithScope, irClass, (Map<String, ? extends IrConstantValue>) map, (List<? extends IrType>) list);
    }

    public static final IrConstantValue irConstantObject(IrBuilderWithScope irBuilderWithScope, IrClass clazz, Map<String, ? extends IrConstantValue> elements, List<? extends IrType> typeArguments) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        Intrinsics.checkNotNullParameter(elements, "elements");
        Intrinsics.checkNotNullParameter(typeArguments, "typeArguments");
        IrConstructor primaryConstructor = IrUtilsKt.getPrimaryConstructor(clazz);
        Intrinsics.checkNotNull(primaryConstructor);
        List<IrValueParameter> valueParameters = primaryConstructor.getSymbol().getOwner().getValueParameters();
        if (valueParameters.size() != elements.size()) {
            throw new IllegalArgumentException(("Wrong number of values provided for " + clazz.getName() + " construction: " + elements.size() + " instead of " + valueParameters.size()).toString());
        }
        Unit unit = Unit.INSTANCE;
        List<IrValueParameter> list = valueParameters;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (IrValueParameter irValueParameter : list) {
            IrConstantValue irConstantValue = elements.get(irValueParameter.getName().asString());
            if (irConstantValue == null) {
                throw new IllegalStateException(("No value for field named " + irValueParameter.getName() + " provided").toString());
            }
            arrayList.add(irConstantValue);
        }
        return irConstantObject(irBuilderWithScope, clazz, arrayList, typeArguments);
    }
}
