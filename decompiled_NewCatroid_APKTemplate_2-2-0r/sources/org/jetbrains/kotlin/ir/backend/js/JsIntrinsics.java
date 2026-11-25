package org.jetbrains.kotlin.ir.backend.js;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.builtins.PrimitiveType;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.incremental.IncrementalJvmCacheKt;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.util.DescriptorSymbolTableExtension;
import org.jetbrains.kotlin.ir.util.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi2ir.KotlinUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.util.capitalizeDecapitalize.CapitalizeDecapitalizeKt;

/* JADX INFO: compiled from: JsIntrinsics.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000y\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\u0019\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0003\bæ\u0001\n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b)\u0018\u00002\u00020\u0001:\u0002ü\u0002B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0013\u0010ó\u0002\u001a\u00020\t2\b\u0010ô\u0002\u001a\u00030Æ\u0002H\u0002J\u0013\u0010õ\u0002\u001a\u00020\t2\b\u0010ô\u0002\u001a\u00030Æ\u0002H\u0002J\u0013\u0010ö\u0002\u001a\u00020\u000b2\b\u0010÷\u0002\u001a\u00030Æ\u0002H\u0002J\u0013\u0010ø\u0002\u001a\u00020\t2\b\u0010ô\u0002\u001a\u00030Æ\u0002H\u0002J\u0013\u0010ù\u0002\u001a\u00020:2\b\u0010ô\u0002\u001a\u00030Æ\u0002H\u0002J\u0013\u0010ú\u0002\u001a\u00020\t2\b\u0010ô\u0002\u001a\u00030Æ\u0002H\u0002J\u0015\u0010û\u0002\u001a\u0004\u0018\u00010\t2\b\u0010ô\u0002\u001a\u00030Æ\u0002H\u0002R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u000e\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0012\u001a\u00020\u000b8F¢\u0006\u0006\u001a\u0004\b\u0013\u0010\rR\u0011\u0010\u0014\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0017\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0016R\u0011\u0010\u0019\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0016R\u0011\u0010\u001b\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\rR\u0011\u0010\u001d\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\rR\u001b\u0010\u001f\u001a\u00020 8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b#\u0010$\u001a\u0004\b!\u0010\"R\u001b\u0010%\u001a\u00020 8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b'\u0010$\u001a\u0004\b&\u0010\"R\u001b\u0010(\u001a\u00020 8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b*\u0010$\u001a\u0004\b)\u0010\"R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b+\u0010,R\u0011\u0010-\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b.\u0010\u0016R\u0011\u0010/\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b0\u0010\rR\u0011\u00101\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b2\u0010\u0016R\u0011\u00103\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b4\u0010\u0016R\u0011\u00105\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b6\u0010\u0016R\u0011\u00107\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b8\u0010\u0016R\u0011\u00109\u001a\u00020:¢\u0006\b\n\u0000\u001a\u0004\b;\u0010<R\u0011\u0010=\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b>\u0010\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010?\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b?\u0010\u0016R\u0011\u0010@\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b@\u0010\u0016R\u0011\u0010A\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\bA\u0010\u0016R\u0011\u0010B\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\bB\u0010\u0016R\u0011\u0010C\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\bC\u0010\u0016R\u0011\u0010D\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\bD\u0010\u0016R\u001d\u0010E\u001a\u000e\u0012\u0004\u0012\u00020G\u0012\u0004\u0012\u00020\t0F¢\u0006\b\n\u0000\u001a\u0004\bE\u0010HR\u0011\u0010I\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\bI\u0010\u0016R\u0011\u0010J\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\bK\u0010\u0016R\u0011\u0010L\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\bM\u0010\u0016R\u0011\u0010N\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\bO\u0010\u0016R\u0011\u0010P\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\bQ\u0010\u0016R\u0011\u0010R\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\bS\u0010\u0016R\u0011\u0010T\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\bU\u0010\u0016R\u0011\u0010V\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\bW\u0010\u0016R\u0011\u0010X\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\bY\u0010\u0016R\u0011\u0010Z\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b[\u0010\u0016R\u0011\u0010\\\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b]\u0010\u0016R\u0011\u0010^\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b_\u0010\u0016R\u0011\u0010`\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\ba\u0010\u0016R\u0011\u0010b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\bc\u0010\u0016R\u0011\u0010d\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\be\u0010\u0016R\u0011\u0010f\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\bg\u0010\u0016R\u0011\u0010h\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\bi\u0010\u0016R\u0011\u0010j\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\bk\u0010\u0016R\u0011\u0010l\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\bm\u0010\u0016R\u0011\u0010n\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\bo\u0010\u0016R\u0011\u0010p\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\bq\u0010\u0016R\u0011\u0010r\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\bs\u0010\u0016R\u0011\u0010t\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\bu\u0010\u0016R\u0011\u0010v\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\bw\u0010\u0016R\u0011\u0010x\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\by\u0010\u0016R\u0011\u0010z\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b{\u0010\u0016R\u0011\u0010|\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b}\u0010\u0016R\u0011\u0010~\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u007f\u0010\u0016R\u0013\u0010\u0080\u0001\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\b\u0081\u0001\u0010\u0016R\u0013\u0010\u0082\u0001\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\b\u0083\u0001\u0010\u0016R\u0013\u0010\u0084\u0001\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\b\u0085\u0001\u0010\u0016R\u0013\u0010\u0086\u0001\u001a\u00020\t8F¢\u0006\u0007\u001a\u0005\b\u0087\u0001\u0010\u0016R\u0013\u0010\u0088\u0001\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\b\u0089\u0001\u0010\u0016R\u0013\u0010\u008a\u0001\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\b\u008b\u0001\u0010\u0016R\u0013\u0010\u008c\u0001\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\b\u008d\u0001\u0010\u0016R\u0013\u0010\u008e\u0001\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\b\u008f\u0001\u0010\u0016R\u0013\u0010\u0090\u0001\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\b\u0091\u0001\u0010\u0016R\u0013\u0010\u0092\u0001\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\b\u0093\u0001\u0010\u0016R\u0013\u0010\u0094\u0001\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\b\u0095\u0001\u0010\u0016R\u0013\u0010\u0096\u0001\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\b\u0097\u0001\u0010\u0016R\u0013\u0010\u0098\u0001\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\b\u0099\u0001\u0010\u0016R\u0013\u0010\u009a\u0001\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\b\u009b\u0001\u0010\u0016R\u0013\u0010\u009c\u0001\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\b\u009d\u0001\u0010\u0016R\u001e\u0010\u009e\u0001\u001a\u00020\u000b8FX\u0086\u0084\u0002¢\u0006\u000e\n\u0005\b \u0001\u0010$\u001a\u0005\b\u009f\u0001\u0010\rR\u001e\u0010¡\u0001\u001a\u00020\u000b8FX\u0086\u0084\u0002¢\u0006\u000e\n\u0005\b£\u0001\u0010$\u001a\u0005\b¢\u0001\u0010\rR\u0013\u0010¤\u0001\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\b¥\u0001\u0010\u0016R\u0013\u0010¦\u0001\u001a\u00020\u000b¢\u0006\t\n\u0000\u001a\u0005\b§\u0001\u0010\rR\u0013\u0010¨\u0001\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\b©\u0001\u0010\u0016R\u0013\u0010ª\u0001\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\b«\u0001\u0010\u0016R\u0013\u0010¬\u0001\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\b\u00ad\u0001\u0010\u0016R\u0013\u0010®\u0001\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\b¯\u0001\u0010\u0016R\u0013\u0010°\u0001\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\b±\u0001\u0010\u0016R\u0013\u0010²\u0001\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\b³\u0001\u0010\u0016R\u0013\u0010´\u0001\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\bµ\u0001\u0010\u0016R\u0013\u0010¶\u0001\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\b·\u0001\u0010\u0016R\u0013\u0010¸\u0001\u001a\u00020\u000b¢\u0006\t\n\u0000\u001a\u0005\b¹\u0001\u0010\rR\u0013\u0010º\u0001\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\b»\u0001\u0010\u0016R\u0013\u0010¼\u0001\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\b½\u0001\u0010\u0016R\u0013\u0010¾\u0001\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\b¿\u0001\u0010\u0016R\u0013\u0010À\u0001\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\bÁ\u0001\u0010\u0016R\u0013\u0010Â\u0001\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\bÃ\u0001\u0010\u0016R\u0013\u0010Ä\u0001\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\bÅ\u0001\u0010\u0016R\u0013\u0010Æ\u0001\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\bÇ\u0001\u0010\u0016R\u0013\u0010È\u0001\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\bÉ\u0001\u0010\u0016R\u0013\u0010Ê\u0001\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\bË\u0001\u0010\u0016R\u0013\u0010Ì\u0001\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\bÍ\u0001\u0010\u0016R\u0013\u0010Î\u0001\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\bÏ\u0001\u0010\u0016R\u0013\u0010Ð\u0001\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\bÑ\u0001\u0010\u0016R\u0013\u0010Ò\u0001\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\bÓ\u0001\u0010\u0016R\u0013\u0010Ô\u0001\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\bÕ\u0001\u0010\u0016R\u0013\u0010Ö\u0001\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\b×\u0001\u0010\u0016R\u0013\u0010Ø\u0001\u001a\u00020\u000b¢\u0006\t\n\u0000\u001a\u0005\bÙ\u0001\u0010\rR\u0013\u0010Ú\u0001\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\bÛ\u0001\u0010\u0016R\u0013\u0010Ü\u0001\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\bÝ\u0001\u0010\u0016R\u0013\u0010Þ\u0001\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\bß\u0001\u0010\u0016R\u0013\u0010à\u0001\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\bá\u0001\u0010\u0016R\u0013\u0010â\u0001\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\bã\u0001\u0010\u0016R\u0013\u0010ä\u0001\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\bå\u0001\u0010\u0016R\u0013\u0010æ\u0001\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\bç\u0001\u0010\u0016R\u0013\u0010è\u0001\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\bé\u0001\u0010\u0016R\u0013\u0010ê\u0001\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\bë\u0001\u0010\u0016R\u0013\u0010ì\u0001\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\bí\u0001\u0010\u0016R\u0013\u0010î\u0001\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\bï\u0001\u0010\u0016R\u0013\u0010ð\u0001\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\bñ\u0001\u0010\u0016R\u0013\u0010ò\u0001\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\bó\u0001\u0010\u0016R\u0013\u0010ô\u0001\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\bõ\u0001\u0010\u0016R\u0013\u0010ö\u0001\u001a\u00020\u000b¢\u0006\t\n\u0000\u001a\u0005\b÷\u0001\u0010\rR\u001e\u0010ø\u0001\u001a\u00020\u000f8FX\u0086\u0084\u0002¢\u0006\u000e\n\u0005\bú\u0001\u0010$\u001a\u0005\bù\u0001\u0010\u0011R\u0013\u0010û\u0001\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\bü\u0001\u0010\u0016R\u0013\u0010ý\u0001\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\bþ\u0001\u0010\u0016R\u0013\u0010ÿ\u0001\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\b\u0080\u0002\u0010\u0016R\u0013\u0010\u0081\u0002\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\b\u0082\u0002\u0010\u0016R\u0013\u0010\u0083\u0002\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\b\u0084\u0002\u0010\u0016R\u0013\u0010\u0085\u0002\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\b\u0086\u0002\u0010\u0016R\u0013\u0010\u0087\u0002\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\b\u0088\u0002\u0010\u0016R\u0013\u0010\u0089\u0002\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\b\u008a\u0002\u0010\u0016R\u0013\u0010\u008b\u0002\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\b\u008c\u0002\u0010\u0016R\u001f\u0010\u008d\u0002\u001a\u000e\u0012\u0004\u0012\u00020G\u0012\u0004\u0012\u00020\t0F¢\u0006\t\n\u0000\u001a\u0005\b\u008e\u0002\u0010HR\u0013\u0010\u008f\u0002\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\b\u0090\u0002\u0010\u0016R\u0013\u0010\u0091\u0002\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\b\u0092\u0002\u0010\u0016R\u0013\u0010\u0093\u0002\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\b\u0094\u0002\u0010\u0016R\u0013\u0010\u0095\u0002\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\b\u0096\u0002\u0010\u0016R\u0013\u0010\u0097\u0002\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\b\u0098\u0002\u0010\u0016R\u0013\u0010\u0099\u0002\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\b\u009a\u0002\u0010\u0016R\u0013\u0010\u009b\u0002\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\b\u009c\u0002\u0010\u0016R\u0013\u0010\u009d\u0002\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\b\u009e\u0002\u0010\u0016R\u0013\u0010\u009f\u0002\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\b \u0002\u0010\u0016R\u0013\u0010¡\u0002\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\b¢\u0002\u0010\u0016R\u0013\u0010£\u0002\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\b¤\u0002\u0010\u0016R\u0013\u0010¥\u0002\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\b¦\u0002\u0010\u0016R\u0013\u0010§\u0002\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\b¨\u0002\u0010\u0016R\u0013\u0010©\u0002\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\bª\u0002\u0010\u0016R\u0013\u0010«\u0002\u001a\u00020\u000b¢\u0006\t\n\u0000\u001a\u0005\b¬\u0002\u0010\rR\u0015\u0010\u00ad\u0002\u001a\u00030®\u0002¢\u0006\n\n\u0000\u001a\u0006\b¯\u0002\u0010°\u0002R\u0013\u0010±\u0002\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\b²\u0002\u0010\u0016R\u0013\u0010³\u0002\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\b´\u0002\u0010\u0016R\u0013\u0010µ\u0002\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\b¶\u0002\u0010\u0016R\u0013\u0010·\u0002\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\b¸\u0002\u0010\u0016R\u0013\u0010¹\u0002\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\bº\u0002\u0010\u0016R\u0013\u0010»\u0002\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\b¼\u0002\u0010\u0016R\u001f\u0010½\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020G0F8F¢\u0006\u0007\u001a\u0005\b¾\u0002\u0010HR\u001f\u0010¿\u0002\u001a\u000e\u0012\u0004\u0012\u00020G\u0012\u0004\u0012\u00020\t0F¢\u0006\t\n\u0000\u001a\u0005\bÀ\u0002\u0010HR\u001f\u0010Á\u0002\u001a\u000e\u0012\u0004\u0012\u00020G\u0012\u0004\u0012\u00020\t0F¢\u0006\t\n\u0000\u001a\u0005\bÂ\u0002\u0010HR5\u0010Ã\u0002\u001a#\u0012\r\u0012\u000b Å\u0002*\u0004\u0018\u00010G0G\u0012\u000f\u0012\r Å\u0002*\u0005\u0018\u00010Æ\u00020Æ\u00020Ä\u0002¢\u0006\n\n\u0000\u001a\u0006\bÇ\u0002\u0010È\u0002R\u001e\u0010É\u0002\u001a\u00020\u000b8FX\u0086\u0084\u0002¢\u0006\u000e\n\u0005\bË\u0002\u0010$\u001a\u0005\bÊ\u0002\u0010\rR9\u0010Ì\u0002\u001a\u001d\u0012\u0013\u0012\u0011\u0012\u0005\u0012\u00030Î\u0002\u0012\u0005\u0012\u00030Î\u00020Í\u0002\u0012\u0004\u0012\u00020\t0F8FX\u0086\u0084\u0002¢\u0006\u000e\n\u0005\bÐ\u0002\u0010$\u001a\u0005\bÏ\u0002\u0010HR\u0013\u0010Ñ\u0002\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\bÒ\u0002\u0010\u0016R\u001c\u0010Ó\u0002\u001a\u00070Ô\u0002R\u00020\u0000X\u0080\u0004¢\u0006\n\n\u0000\u001a\u0006\bÕ\u0002\u0010Ö\u0002R\u0013\u0010×\u0002\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\bØ\u0002\u0010\u0016R\u0013\u0010Ù\u0002\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\bÚ\u0002\u0010\u0016R\u0013\u0010Û\u0002\u001a\u00020\u000b¢\u0006\t\n\u0000\u001a\u0005\bÜ\u0002\u0010\rR\u0013\u0010Ý\u0002\u001a\u00020\u000f¢\u0006\t\n\u0000\u001a\u0005\bÞ\u0002\u0010\u0011R\u0013\u0010ß\u0002\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\bà\u0002\u0010\u0016R\u001e\u0010á\u0002\u001a\u00020\u000b8FX\u0086\u0084\u0002¢\u0006\u000e\n\u0005\bã\u0002\u0010$\u001a\u0005\bâ\u0002\u0010\rR\u001e\u0010ä\u0002\u001a\u00020\u000b8FX\u0086\u0084\u0002¢\u0006\u000e\n\u0005\bæ\u0002\u0010$\u001a\u0005\bå\u0002\u0010\rR\u001e\u0010ç\u0002\u001a\u00020\u000b8FX\u0086\u0084\u0002¢\u0006\u000e\n\u0005\bé\u0002\u0010$\u001a\u0005\bè\u0002\u0010\rR\u001e\u0010ê\u0002\u001a\u00020\u000b8FX\u0086\u0084\u0002¢\u0006\u000e\n\u0005\bì\u0002\u0010$\u001a\u0005\bë\u0002\u0010\rR\u0013\u0010í\u0002\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\bî\u0002\u0010\u0016R\u0013\u0010ï\u0002\u001a\u00020:¢\u0006\t\n\u0000\u001a\u0005\bð\u0002\u0010<R\u0013\u0010ñ\u0002\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\bò\u0002\u0010\u0016¨\u0006ý\u0002"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/JsIntrinsics;", "", "irBuiltIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "(Lorg/jetbrains/kotlin/ir/IrBuiltIns;Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;)V", "_rangeUntilFunctions", "", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "anyClassSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "getAnyClassSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "anyConstructorSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "getAnyConstructorSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "array", "getArray", "arrayConcat", "getArrayConcat", "()Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "arrayLiteral", "getArrayLiteral", "captureStack", "getCaptureStack", "charClassSymbol", "getCharClassSymbol", "charSequenceClassSymbol", "getCharSequenceClassSymbol", "charSequenceGetFunctionSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "getCharSequenceGetFunctionSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "charSequenceGetFunctionSymbol$delegate", "Lkotlin/Lazy;", "charSequenceLengthPropertyGetterSymbol", "getCharSequenceLengthPropertyGetterSymbol", "charSequenceLengthPropertyGetterSymbol$delegate", "charSequenceSubSequenceFunctionSymbol", "getCharSequenceSubSequenceFunctionSymbol", "charSequenceSubSequenceFunctionSymbol$delegate", "getContext", "()Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "createSharedBox", "getCreateSharedBox", "doNotIntrinsifyAnnotationSymbol", "getDoNotIntrinsifyAnnotationSymbol", "enumEntriesIntrinsic", "getEnumEntriesIntrinsic", "enumValueOfIntrinsic", "getEnumValueOfIntrinsic", "enumValuesIntrinsic", "getEnumValuesIntrinsic", "getContinuation", "getGetContinuation", "globalThis", "Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "getGlobalThis", "()Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "implementSymbol", "getImplementSymbol", "isArraySymbol", "isCharSequenceSymbol", "isComparableSymbol", "isExternalObject", "isInterfaceSymbol", "isNumberSymbol", "isPrimitiveArray", "", "Lorg/jetbrains/kotlin/builtins/PrimitiveType;", "()Ljava/util/Map;", "isSuspendFunctionSymbol", "jsAnd", "getJsAnd", "jsAnyToString", "getJsAnyToString", "jsArguments", "getJsArguments", "jsArray", "getJsArray", "jsArrayGet", "getJsArrayGet", "jsArrayIteratorFunction", "getJsArrayIteratorFunction", "jsArrayLength", "getJsArrayLength", "jsArrayLike2Array", "getJsArrayLike2Array", "jsArraySet", "getJsArraySet", "jsArraySlice", "getJsArraySlice", "jsBind", "getJsBind", "jsBitAnd", "getJsBitAnd", "jsBitNot", "getJsBitNot", "jsBitOr", "getJsBitOr", "jsBitShiftL", "getJsBitShiftL", "jsBitShiftR", "getJsBitShiftR", "jsBitShiftRU", "getJsBitShiftRU", "jsBitXor", "getJsBitXor", "jsBooleanInExternalException", "getJsBooleanInExternalException", "jsBooleanInExternalLog", "getJsBooleanInExternalLog", "jsBoxApplySymbol", "getJsBoxApplySymbol", "jsBoxIntrinsic", "getJsBoxIntrinsic", "jsCall", "getJsCall", "jsCharSequenceGet", "getJsCharSequenceGet", "jsCharSequenceLength", "getJsCharSequenceLength", "jsCharSequenceSubSequence", "getJsCharSequenceSubSequence", "jsClass", "getJsClass", "jsCode", "getJsCode", "jsCompareTo", "getJsCompareTo", "jsContexfulRef", "getJsContexfulRef", "jsCoroutineContext", "getJsCoroutineContext", "jsCreateExternalThisSymbol", "getJsCreateExternalThisSymbol", "jsCreateThisSymbol", "getJsCreateThisSymbol", "jsDefinePropertySymbol", "getJsDefinePropertySymbol", "jsDelete", "getJsDelete", "jsDiv", "getJsDiv", "jsDivAssign", "getJsDivAssign", "jsEmptyObject", "getJsEmptyObject", "jsEnsureNonNull", "getJsEnsureNonNull", "jsEqeq", "getJsEqeq", "jsEqeqeq", "getJsEqeqeq", "jsEquals", "getJsEquals", "jsExportAnnotationSymbol", "getJsExportAnnotationSymbol", "jsExportAnnotationSymbol$delegate", "jsExportIgnoreAnnotationSymbol", "getJsExportIgnoreAnnotationSymbol", "jsExportIgnoreAnnotationSymbol$delegate", "jsFillArray", "getJsFillArray", "jsFunAnnotationSymbol", "getJsFunAnnotationSymbol", "jsGetBooleanHashCode", "getJsGetBooleanHashCode", "jsGetContinuation", "getJsGetContinuation", "jsGetNumberHashCode", "getJsGetNumberHashCode", "jsGetObjectHashCode", "getJsGetObjectHashCode", "jsGetStringHashCode", "getJsGetStringHashCode", "jsGt", "getJsGt", "jsGtEq", "getJsGtEq", "jsHashCode", "getJsHashCode", "jsImplicitExportAnnotationSymbol", "getJsImplicitExportAnnotationSymbol", "jsImul", "getJsImul", "jsIn", "getJsIn", "jsInstanceOf", "getJsInstanceOf", "jsInvokeSuspendSuperType", "getJsInvokeSuspendSuperType", "jsInvokeSuspendSuperTypeWithReceiver", "getJsInvokeSuspendSuperTypeWithReceiver", "jsInvokeSuspendSuperTypeWithReceiverAndParam", "getJsInvokeSuspendSuperTypeWithReceiverAndParam", "jsIsEs6", "getJsIsEs6", "jsLt", "getJsLt", "jsLtEq", "getJsLtEq", "jsMinus", "getJsMinus", "jsMinusAssign", "getJsMinusAssign", "jsMod", "getJsMod", "jsModAssign", "getJsModAssign", "jsMult", "getJsMult", "jsMultAssign", "getJsMultAssign", "jsNameAnnotationSymbol", "getJsNameAnnotationSymbol", "jsNativeBoolean", "getJsNativeBoolean", "jsNewAnonymousClass", "getJsNewAnonymousClass", "jsNewTarget", "getJsNewTarget", "jsNot", "getJsNot", "jsNotEq", "getJsNotEq", "jsNotEqeq", "getJsNotEqeq", "jsNumberRangeToLong", "getJsNumberRangeToLong", "jsNumberRangeToNumber", "getJsNumberRangeToNumber", "jsNumberToByte", "getJsNumberToByte", "jsNumberToChar", "getJsNumberToChar", "jsNumberToDouble", "getJsNumberToDouble", "jsNumberToInt", "getJsNumberToInt", "jsNumberToLong", "getJsNumberToLong", "jsNumberToShort", "getJsNumberToShort", "jsObjectClassSymbol", "getJsObjectClassSymbol", "jsObjectConstructorSymbol", "getJsObjectConstructorSymbol", "jsObjectConstructorSymbol$delegate", "jsObjectCreateSymbol", "getJsObjectCreateSymbol", "jsOpenInitializerBox", "getJsOpenInitializerBox", "jsOr", "getJsOr", "jsPlus", "getJsPlus", "jsPlusAssign", "getJsPlusAssign", "jsPostfixDec", "getJsPostfixDec", "jsPostfixInc", "getJsPostfixInc", "jsPrefixDec", "getJsPrefixDec", "jsPrefixInc", "getJsPrefixInc", "jsPrimitiveArrayIteratorFunctions", "getJsPrimitiveArrayIteratorFunctions", "jsPrototypeOfSymbol", "getJsPrototypeOfSymbol", "jsSliceArrayLikeFromIndex", "getJsSliceArrayLikeFromIndex", "jsSliceArrayLikeFromIndexToIndex", "getJsSliceArrayLikeFromIndexToIndex", "jsToByte", "getJsToByte", "jsToLong", "getJsToLong", "jsToShort", "getJsToShort", "jsToString", "getJsToString", "jsTypeOf", "getJsTypeOf", "jsUnaryMinus", "getJsUnaryMinus", "jsUnaryPlus", "getJsUnaryPlus", "jsUnboxIntrinsic", "getJsUnboxIntrinsic", "jsUnreachableDeclarationException", "getJsUnreachableDeclarationException", "jsUnreachableDeclarationLog", "getJsUnreachableDeclarationLog", "linkageErrorSymbol", "getLinkageErrorSymbol", "longClassSymbol", "getLongClassSymbol", "longCompareToLong", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "getLongCompareToLong", "()Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "longToDouble", "getLongToDouble", "longToFloat", "getLongToFloat", "metadataClassConstructorSymbol", "getMetadataClassConstructorSymbol", "metadataInterfaceConstructorSymbol", "getMetadataInterfaceConstructorSymbol", "metadataObjectConstructorSymbol", "getMetadataObjectConstructorSymbol", "primitiveArrayConcat", "getPrimitiveArrayConcat", "primitiveArrays", "getPrimitiveArrays", "primitiveToLiteralConstructor", "getPrimitiveToLiteralConstructor", "primitiveToSizeConstructor", "getPrimitiveToSizeConstructor", "primitiveToTypedArrayMap", "Ljava/util/EnumMap;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "", "getPrimitiveToTypedArrayMap", "()Ljava/util/EnumMap;", "promiseClassSymbol", "getPromiseClassSymbol", "promiseClassSymbol$delegate", "rangeUntilFunctions", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/ir/types/IrType;", "getRangeUntilFunctions", "rangeUntilFunctions$delegate", "readSharedBox", "getReadSharedBox", "reflectionSymbols", "Lorg/jetbrains/kotlin/ir/backend/js/JsIntrinsics$JsReflectionSymbols;", "getReflectionSymbols$backend_js", "()Lorg/jetbrains/kotlin/ir/backend/js/JsIntrinsics$JsReflectionSymbols;", "returnIfSuspended", "getReturnIfSuspended", "setMetadataForSymbol", "getSetMetadataForSymbol", "stringClassSymbol", "getStringClassSymbol", "stringConstructorSymbol", "getStringConstructorSymbol", "taggedArrayCopy", "getTaggedArrayCopy", "uByteClassSymbol", "getUByteClassSymbol", "uByteClassSymbol$delegate", "uIntClassSymbol", "getUIntClassSymbol", "uIntClassSymbol$delegate", "uLongClassSymbol", "getULongClassSymbol", "uLongClassSymbol$delegate", "uShortClassSymbol", "getUShortClassSymbol", "uShortClassSymbol$delegate", "unreachable", "getUnreachable", PsiKeyword.VOID, "getVoid", "writeSharedBox", "getWriteSharedBox", "getFunctionInEnumPackage", "name", "getFunctionInKotlinPackage", "getInternalClassWithoutPackage", "fqName", "getInternalFunction", "getInternalProperty", "getInternalWithoutPackage", "getInternalWithoutPackageOrNull", "JsReflectionSymbols", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsIntrinsics {
    private final Iterable<IrSimpleFunctionSymbol> _rangeUntilFunctions;
    private final IrClassSymbol anyClassSymbol;
    private final IrConstructorSymbol anyConstructorSymbol;
    private final IrSimpleFunctionSymbol arrayConcat;
    private final IrSimpleFunctionSymbol arrayLiteral;
    private final IrSimpleFunctionSymbol captureStack;
    private final IrClassSymbol charClassSymbol;
    private final IrClassSymbol charSequenceClassSymbol;

    /* JADX INFO: renamed from: charSequenceGetFunctionSymbol$delegate, reason: from kotlin metadata */
    private final Lazy charSequenceGetFunctionSymbol;

    /* JADX INFO: renamed from: charSequenceLengthPropertyGetterSymbol$delegate, reason: from kotlin metadata */
    private final Lazy charSequenceLengthPropertyGetterSymbol;

    /* JADX INFO: renamed from: charSequenceSubSequenceFunctionSymbol$delegate, reason: from kotlin metadata */
    private final Lazy charSequenceSubSequenceFunctionSymbol;
    private final JsIrBackendContext context;
    private final IrSimpleFunctionSymbol createSharedBox;
    private final IrClassSymbol doNotIntrinsifyAnnotationSymbol;
    private final IrSimpleFunctionSymbol enumEntriesIntrinsic;
    private final IrSimpleFunctionSymbol enumValueOfIntrinsic;
    private final IrSimpleFunctionSymbol enumValuesIntrinsic;
    private final IrSimpleFunctionSymbol getContinuation;
    private final IrPropertySymbol globalThis;
    private final IrSimpleFunctionSymbol implementSymbol;
    private final IrBuiltIns irBuiltIns;
    private final IrSimpleFunctionSymbol isArraySymbol;
    private final IrSimpleFunctionSymbol isCharSequenceSymbol;
    private final IrSimpleFunctionSymbol isComparableSymbol;
    private final IrSimpleFunctionSymbol isExternalObject;
    private final IrSimpleFunctionSymbol isInterfaceSymbol;
    private final IrSimpleFunctionSymbol isNumberSymbol;
    private final Map<PrimitiveType, IrSimpleFunctionSymbol> isPrimitiveArray;
    private final IrSimpleFunctionSymbol isSuspendFunctionSymbol;
    private final IrSimpleFunctionSymbol jsAnd;
    private final IrSimpleFunctionSymbol jsAnyToString;
    private final IrSimpleFunctionSymbol jsArguments;
    private final IrSimpleFunctionSymbol jsArray;
    private final IrSimpleFunctionSymbol jsArrayGet;
    private final IrSimpleFunctionSymbol jsArrayIteratorFunction;
    private final IrSimpleFunctionSymbol jsArrayLength;
    private final IrSimpleFunctionSymbol jsArrayLike2Array;
    private final IrSimpleFunctionSymbol jsArraySet;
    private final IrSimpleFunctionSymbol jsArraySlice;
    private final IrSimpleFunctionSymbol jsBind;
    private final IrSimpleFunctionSymbol jsBitAnd;
    private final IrSimpleFunctionSymbol jsBitNot;
    private final IrSimpleFunctionSymbol jsBitOr;
    private final IrSimpleFunctionSymbol jsBitShiftL;
    private final IrSimpleFunctionSymbol jsBitShiftR;
    private final IrSimpleFunctionSymbol jsBitShiftRU;
    private final IrSimpleFunctionSymbol jsBitXor;
    private final IrSimpleFunctionSymbol jsBooleanInExternalException;
    private final IrSimpleFunctionSymbol jsBooleanInExternalLog;
    private final IrSimpleFunctionSymbol jsBoxApplySymbol;
    private final IrSimpleFunctionSymbol jsBoxIntrinsic;
    private final IrSimpleFunctionSymbol jsCall;
    private final IrSimpleFunctionSymbol jsCharSequenceGet;
    private final IrSimpleFunctionSymbol jsCharSequenceLength;
    private final IrSimpleFunctionSymbol jsCharSequenceSubSequence;
    private final IrSimpleFunctionSymbol jsClass;
    private final IrSimpleFunctionSymbol jsCode;
    private final IrSimpleFunctionSymbol jsCompareTo;
    private final IrSimpleFunctionSymbol jsContexfulRef;
    private final IrSimpleFunctionSymbol jsCreateExternalThisSymbol;
    private final IrSimpleFunctionSymbol jsCreateThisSymbol;
    private final IrSimpleFunctionSymbol jsDefinePropertySymbol;
    private final IrSimpleFunctionSymbol jsDelete;
    private final IrSimpleFunctionSymbol jsDiv;
    private final IrSimpleFunctionSymbol jsDivAssign;
    private final IrSimpleFunctionSymbol jsEmptyObject;
    private final IrSimpleFunctionSymbol jsEnsureNonNull;
    private final IrSimpleFunctionSymbol jsEqeq;
    private final IrSimpleFunctionSymbol jsEqeqeq;
    private final IrSimpleFunctionSymbol jsEquals;

    /* JADX INFO: renamed from: jsExportAnnotationSymbol$delegate, reason: from kotlin metadata */
    private final Lazy jsExportAnnotationSymbol;

    /* JADX INFO: renamed from: jsExportIgnoreAnnotationSymbol$delegate, reason: from kotlin metadata */
    private final Lazy jsExportIgnoreAnnotationSymbol;
    private final IrSimpleFunctionSymbol jsFillArray;
    private final IrClassSymbol jsFunAnnotationSymbol;
    private final IrSimpleFunctionSymbol jsGetBooleanHashCode;
    private final IrSimpleFunctionSymbol jsGetContinuation;
    private final IrSimpleFunctionSymbol jsGetNumberHashCode;
    private final IrSimpleFunctionSymbol jsGetObjectHashCode;
    private final IrSimpleFunctionSymbol jsGetStringHashCode;
    private final IrSimpleFunctionSymbol jsGt;
    private final IrSimpleFunctionSymbol jsGtEq;
    private final IrSimpleFunctionSymbol jsHashCode;
    private final IrClassSymbol jsImplicitExportAnnotationSymbol;
    private final IrSimpleFunctionSymbol jsImul;
    private final IrSimpleFunctionSymbol jsIn;
    private final IrSimpleFunctionSymbol jsInstanceOf;
    private final IrSimpleFunctionSymbol jsInvokeSuspendSuperType;
    private final IrSimpleFunctionSymbol jsInvokeSuspendSuperTypeWithReceiver;
    private final IrSimpleFunctionSymbol jsInvokeSuspendSuperTypeWithReceiverAndParam;
    private final IrSimpleFunctionSymbol jsIsEs6;
    private final IrSimpleFunctionSymbol jsLt;
    private final IrSimpleFunctionSymbol jsLtEq;
    private final IrSimpleFunctionSymbol jsMinus;
    private final IrSimpleFunctionSymbol jsMinusAssign;
    private final IrSimpleFunctionSymbol jsMod;
    private final IrSimpleFunctionSymbol jsModAssign;
    private final IrSimpleFunctionSymbol jsMult;
    private final IrSimpleFunctionSymbol jsMultAssign;
    private final IrClassSymbol jsNameAnnotationSymbol;
    private final IrSimpleFunctionSymbol jsNativeBoolean;
    private final IrSimpleFunctionSymbol jsNewAnonymousClass;
    private final IrSimpleFunctionSymbol jsNewTarget;
    private final IrSimpleFunctionSymbol jsNot;
    private final IrSimpleFunctionSymbol jsNotEq;
    private final IrSimpleFunctionSymbol jsNotEqeq;
    private final IrSimpleFunctionSymbol jsNumberRangeToLong;
    private final IrSimpleFunctionSymbol jsNumberRangeToNumber;
    private final IrSimpleFunctionSymbol jsNumberToByte;
    private final IrSimpleFunctionSymbol jsNumberToChar;
    private final IrSimpleFunctionSymbol jsNumberToDouble;
    private final IrSimpleFunctionSymbol jsNumberToInt;
    private final IrSimpleFunctionSymbol jsNumberToLong;
    private final IrSimpleFunctionSymbol jsNumberToShort;
    private final IrClassSymbol jsObjectClassSymbol;

    /* JADX INFO: renamed from: jsObjectConstructorSymbol$delegate, reason: from kotlin metadata */
    private final Lazy jsObjectConstructorSymbol;
    private final IrSimpleFunctionSymbol jsObjectCreateSymbol;
    private final IrSimpleFunctionSymbol jsOpenInitializerBox;
    private final IrSimpleFunctionSymbol jsOr;
    private final IrSimpleFunctionSymbol jsPlus;
    private final IrSimpleFunctionSymbol jsPlusAssign;
    private final IrSimpleFunctionSymbol jsPostfixDec;
    private final IrSimpleFunctionSymbol jsPostfixInc;
    private final IrSimpleFunctionSymbol jsPrefixDec;
    private final IrSimpleFunctionSymbol jsPrefixInc;
    private final Map<PrimitiveType, IrSimpleFunctionSymbol> jsPrimitiveArrayIteratorFunctions;
    private final IrSimpleFunctionSymbol jsPrototypeOfSymbol;
    private final IrSimpleFunctionSymbol jsSliceArrayLikeFromIndex;
    private final IrSimpleFunctionSymbol jsSliceArrayLikeFromIndexToIndex;
    private final IrSimpleFunctionSymbol jsToByte;
    private final IrSimpleFunctionSymbol jsToLong;
    private final IrSimpleFunctionSymbol jsToShort;
    private final IrSimpleFunctionSymbol jsToString;
    private final IrSimpleFunctionSymbol jsTypeOf;
    private final IrSimpleFunctionSymbol jsUnaryMinus;
    private final IrSimpleFunctionSymbol jsUnaryPlus;
    private final IrSimpleFunctionSymbol jsUnboxIntrinsic;
    private final IrSimpleFunctionSymbol jsUnreachableDeclarationException;
    private final IrSimpleFunctionSymbol jsUnreachableDeclarationLog;
    private final IrSimpleFunctionSymbol linkageErrorSymbol;
    private final IrClassSymbol longClassSymbol;
    private final IrSimpleFunction longCompareToLong;
    private final IrSimpleFunctionSymbol longToDouble;
    private final IrSimpleFunctionSymbol longToFloat;
    private final IrSimpleFunctionSymbol metadataClassConstructorSymbol;
    private final IrSimpleFunctionSymbol metadataInterfaceConstructorSymbol;
    private final IrSimpleFunctionSymbol metadataObjectConstructorSymbol;
    private final IrSimpleFunctionSymbol primitiveArrayConcat;
    private final Map<PrimitiveType, IrSimpleFunctionSymbol> primitiveToLiteralConstructor;
    private final Map<PrimitiveType, IrSimpleFunctionSymbol> primitiveToSizeConstructor;
    private final EnumMap<PrimitiveType, String> primitiveToTypedArrayMap;

    /* JADX INFO: renamed from: promiseClassSymbol$delegate, reason: from kotlin metadata */
    private final Lazy promiseClassSymbol;

    /* JADX INFO: renamed from: rangeUntilFunctions$delegate, reason: from kotlin metadata */
    private final Lazy rangeUntilFunctions;
    private final IrSimpleFunctionSymbol readSharedBox;
    private final JsReflectionSymbols reflectionSymbols;
    private final IrSimpleFunctionSymbol returnIfSuspended;
    private final IrSimpleFunctionSymbol setMetadataForSymbol;
    private final IrClassSymbol stringClassSymbol;
    private final IrConstructorSymbol stringConstructorSymbol;
    private final IrSimpleFunctionSymbol taggedArrayCopy;

    /* JADX INFO: renamed from: uByteClassSymbol$delegate, reason: from kotlin metadata */
    private final Lazy uByteClassSymbol;

    /* JADX INFO: renamed from: uIntClassSymbol$delegate, reason: from kotlin metadata */
    private final Lazy uIntClassSymbol;

    /* JADX INFO: renamed from: uLongClassSymbol$delegate, reason: from kotlin metadata */
    private final Lazy uLongClassSymbol;

    /* JADX INFO: renamed from: uShortClassSymbol$delegate, reason: from kotlin metadata */
    private final Lazy uShortClassSymbol;
    private final IrSimpleFunctionSymbol unreachable;
    private final IrPropertySymbol void;
    private final IrSimpleFunctionSymbol writeSharedBox;

    /* JADX WARN: Code duplicated, block: B:27:0x0652  */
    /* JADX WARN: Code duplicated, block: B:35:0x06cd  */
    public JsIntrinsics(IrBuiltIns irBuiltIns, JsIrBackendContext context) {
        Object next;
        IrSimpleFunctionSymbol internalFunction;
        IrSimpleFunctionSymbol internalFunction2;
        Intrinsics.checkNotNullParameter(irBuiltIns, "irBuiltIns");
        Intrinsics.checkNotNullParameter(context, "context");
        this.irBuiltIns = irBuiltIns;
        this.context = context;
        this.jsIsEs6 = getInternalFunction("jsIsEs6");
        this.void = getInternalProperty("VOID");
        this.globalThis = getInternalProperty("globalThis");
        this.jsEqeq = getInternalFunction("jsEqeq");
        this.jsNotEq = getInternalFunction("jsNotEq");
        this.jsEqeqeq = getInternalFunction("jsEqeqeq");
        this.jsNotEqeq = getInternalFunction("jsNotEqeq");
        this.jsGt = getInternalFunction("jsGt");
        this.jsGtEq = getInternalFunction("jsGtEq");
        this.jsLt = getInternalFunction("jsLt");
        this.jsLtEq = getInternalFunction("jsLtEq");
        this.jsNot = getInternalFunction("jsNot");
        this.jsUnaryPlus = getInternalFunction("jsUnaryPlus");
        this.jsUnaryMinus = getInternalFunction("jsUnaryMinus");
        this.jsPrefixInc = getInternalFunction("jsPrefixInc");
        this.jsPostfixInc = getInternalFunction("jsPostfixInc");
        this.jsPrefixDec = getInternalFunction("jsPrefixDec");
        this.jsPostfixDec = getInternalFunction("jsPostfixDec");
        this.jsDelete = getInternalFunction("jsDelete");
        this.jsPlus = getInternalFunction("jsPlus");
        this.jsMinus = getInternalFunction("jsMinus");
        this.jsMult = getInternalFunction("jsMult");
        this.jsDiv = getInternalFunction("jsDiv");
        this.jsMod = getInternalFunction("jsMod");
        this.jsPlusAssign = getInternalFunction("jsPlusAssign");
        this.jsMinusAssign = getInternalFunction("jsMinusAssign");
        this.jsMultAssign = getInternalFunction("jsMultAssign");
        this.jsDivAssign = getInternalFunction("jsDivAssign");
        this.jsModAssign = getInternalFunction("jsModAssign");
        this.jsAnd = getInternalFunction("jsAnd");
        this.jsOr = getInternalFunction("jsOr");
        this.jsIn = getInternalFunction("jsInIntrinsic");
        this.jsBitAnd = getInternalFunction("jsBitAnd");
        this.jsBitOr = getInternalFunction("jsBitOr");
        this.jsBitXor = getInternalFunction("jsBitXor");
        this.jsBitNot = getInternalFunction("jsBitNot");
        this.jsBitShiftR = getInternalFunction("jsBitShiftR");
        this.jsBitShiftRU = getInternalFunction("jsBitShiftRU");
        this.jsBitShiftL = getInternalFunction("jsBitShiftL");
        this.jsInstanceOf = getInternalFunction("jsInstanceOfIntrinsic");
        this.jsTypeOf = getInternalFunction("jsTypeOf");
        this.isExternalObject = getInternalFunction("isExternalObject");
        this.jsNumberToByte = getInternalFunction("numberToByte");
        this.jsNumberToDouble = getInternalFunction("numberToDouble");
        this.jsNumberToInt = getInternalFunction("numberToInt");
        this.jsNumberToShort = getInternalFunction("numberToShort");
        this.jsNumberToLong = getInternalFunction("numberToLong");
        this.jsNumberToChar = getInternalFunction("numberToChar");
        this.jsToByte = getInternalFunction("toByte");
        this.jsToShort = getInternalFunction("toShort");
        this.jsToLong = getInternalFunction("toLong");
        this.implementSymbol = getInternalFunction("implement");
        this.setMetadataForSymbol = getInternalFunction("setMetadataFor");
        this.isInterfaceSymbol = getInternalFunction("isInterface");
        this.isArraySymbol = getInternalFunction("isArray");
        this.isSuspendFunctionSymbol = getInternalFunction("isSuspendFunction");
        this.isNumberSymbol = getInternalFunction(Namer.IS_NUMBER);
        this.isComparableSymbol = getInternalFunction("isComparable");
        this.isCharSequenceSymbol = getInternalFunction("isCharSequence");
        this.isPrimitiveArray = MapsKt.mapOf(TuplesKt.to(PrimitiveType.BOOLEAN, getInternalFunction("isBooleanArray")), TuplesKt.to(PrimitiveType.BYTE, getInternalFunction("isByteArray")), TuplesKt.to(PrimitiveType.SHORT, getInternalFunction("isShortArray")), TuplesKt.to(PrimitiveType.CHAR, getInternalFunction("isCharArray")), TuplesKt.to(PrimitiveType.INT, getInternalFunction("isIntArray")), TuplesKt.to(PrimitiveType.FLOAT, getInternalFunction("isFloatArray")), TuplesKt.to(PrimitiveType.LONG, getInternalFunction("isLongArray")), TuplesKt.to(PrimitiveType.DOUBLE, getInternalFunction("isDoubleArray")));
        this.enumValueOfIntrinsic = getInternalFunction("enumValueOfIntrinsic");
        this.enumValuesIntrinsic = getInternalFunction("enumValuesIntrinsic");
        this.enumEntriesIntrinsic = getFunctionInEnumPackage("enumEntriesIntrinsic");
        this.jsCode = getInternalFunction("js");
        this.jsHashCode = getInternalFunction("hashCode");
        this.jsGetBooleanHashCode = getInternalFunction("getBooleanHashCode");
        this.jsGetNumberHashCode = getInternalFunction("getNumberHashCode");
        this.jsGetObjectHashCode = getInternalFunction("getObjectHashCode");
        this.jsGetStringHashCode = getInternalFunction("getStringHashCode");
        this.jsToString = getInternalFunction("toString");
        this.jsAnyToString = getInternalFunction("anyToString");
        this.jsCompareTo = getInternalFunction("compareTo");
        this.jsEquals = getInternalFunction(Namer.EQUALS_METHOD_NAME);
        this.jsNewTarget = getInternalFunction("jsNewTarget");
        this.jsEmptyObject = getInternalFunction("emptyObject");
        this.jsOpenInitializerBox = getInternalFunction("openInitializerBox");
        this.jsImul = getInternalFunction("imul");
        this.jsUnreachableDeclarationLog = getInternalFunction("unreachableDeclarationLog");
        this.jsUnreachableDeclarationException = getInternalFunction("unreachableDeclarationException");
        this.jsNativeBoolean = getInternalFunction("nativeBoolean");
        this.jsBooleanInExternalLog = getInternalFunction("booleanInExternalLog");
        this.jsBooleanInExternalException = getInternalFunction("booleanInExternalException");
        this.jsNewAnonymousClass = getInternalFunction("jsNewAnonymousClass");
        this.jsGetContinuation = getInternalFunction("getContinuation");
        this.jsInvokeSuspendSuperType = getInternalWithoutPackage("kotlin.coroutines.intrinsics.invokeSuspendSuperType");
        this.jsInvokeSuspendSuperTypeWithReceiver = getInternalWithoutPackage("kotlin.coroutines.intrinsics.invokeSuspendSuperTypeWithReceiver");
        this.jsInvokeSuspendSuperTypeWithReceiverAndParam = getInternalWithoutPackage("kotlin.coroutines.intrinsics.invokeSuspendSuperTypeWithReceiverAndParam");
        this.jsNumberRangeToNumber = getInternalFunction("numberRangeToNumber");
        this.jsNumberRangeToLong = getInternalFunction("numberRangeToLong");
        Name nameIdentifier = Name.identifier("until");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        this._rangeUntilFunctions = irBuiltIns.findFunctions(nameIdentifier, IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME, "ranges");
        this.rangeUntilFunctions = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<Map<Pair<? extends IrType, ? extends IrType>, ? extends IrSimpleFunctionSymbol>>() { // from class: org.jetbrains.kotlin.ir.backend.js.JsIntrinsics$rangeUntilFunctions$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Map<Pair<? extends IrType, ? extends IrType>, ? extends IrSimpleFunctionSymbol> invoke() {
                Iterable iterable = this.this$0._rangeUntilFunctions;
                ArrayList arrayList = new ArrayList();
                for (Object obj : iterable) {
                    IrSimpleFunctionSymbol irSimpleFunctionSymbol = (IrSimpleFunctionSymbol) obj;
                    if (irSimpleFunctionSymbol.getOwner().getExtensionReceiverParameter() != null && irSimpleFunctionSymbol.getOwner().getValueParameters().size() == 1) {
                        arrayList.add(obj);
                    }
                }
                ArrayList arrayList2 = arrayList;
                LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(arrayList2, 10)), 16));
                for (Object obj2 : arrayList2) {
                    IrSimpleFunctionSymbol irSimpleFunctionSymbol2 = (IrSimpleFunctionSymbol) obj2;
                    IrValueParameter extensionReceiverParameter = irSimpleFunctionSymbol2.getOwner().getExtensionReceiverParameter();
                    Intrinsics.checkNotNull(extensionReceiverParameter);
                    linkedHashMap.put(TuplesKt.to(extensionReceiverParameter.getType(), irSimpleFunctionSymbol2.getOwner().getValueParameters().get(0).getType()), obj2);
                }
                return linkedHashMap;
            }
        });
        IrClassSymbol internalClassWithoutPackage = getInternalClassWithoutPackage("kotlin.Long");
        this.longClassSymbol = internalClassWithoutPackage;
        this.promiseClassSymbol = JsCommonBackendContextKt.lazy2(context, new Function0<IrClassSymbol>() { // from class: org.jetbrains.kotlin.ir.backend.js.JsIntrinsics$promiseClassSymbol$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrClassSymbol invoke() {
                return this.this$0.getInternalClassWithoutPackage("kotlin.js.Promise");
            }
        });
        this.metadataInterfaceConstructorSymbol = getInternalFunction("interfaceMeta");
        this.metadataObjectConstructorSymbol = getInternalFunction("objectMeta");
        this.metadataClassConstructorSymbol = getInternalFunction("classMeta");
        DescriptorSymbolTableExtension descriptorExtension = context.getSymbolTable().getDescriptorExtension();
        MemberScope unsubstitutedMemberScope = context.getClass$backend_js(new FqName("kotlin.Long")).getUnsubstitutedMemberScope();
        Intrinsics.checkNotNullExpressionValue(unsubstitutedMemberScope, "getUnsubstitutedMemberScope(...)");
        Name nameIdentifier2 = Name.identifier("toDouble");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
        this.longToDouble = descriptorExtension.referenceSimpleFunction(KotlinUtilsKt.findSingleFunction(unsubstitutedMemberScope, nameIdentifier2));
        DescriptorSymbolTableExtension descriptorExtension2 = context.getSymbolTable().getDescriptorExtension();
        MemberScope unsubstitutedMemberScope2 = context.getClass$backend_js(new FqName("kotlin.Long")).getUnsubstitutedMemberScope();
        Intrinsics.checkNotNullExpressionValue(unsubstitutedMemberScope2, "getUnsubstitutedMemberScope(...)");
        Name nameIdentifier3 = Name.identifier("toFloat");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier3, "identifier(...)");
        this.longToFloat = descriptorExtension2.referenceSimpleFunction(KotlinUtilsKt.findSingleFunction(unsubstitutedMemberScope2, nameIdentifier3));
        Iterator<T> it = internalClassWithoutPackage.getOwner().getDeclarations().iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            IrDeclaration irDeclaration = (IrDeclaration) next;
            if (irDeclaration instanceof IrSimpleFunction) {
                IrSimpleFunction irSimpleFunction = (IrSimpleFunction) irDeclaration;
                if (Intrinsics.areEqual(irSimpleFunction.getName(), Name.identifier("compareTo")) && IrTypePredicatesKt.isLong(irSimpleFunction.getValueParameters().get(0).getType())) {
                    break;
                }
            }
        }
        IrSimpleFunction irSimpleFunction2 = (IrSimpleFunction) (next instanceof IrSimpleFunction ? next : null);
        Intrinsics.checkNotNull(irSimpleFunction2);
        this.longCompareToLong = irSimpleFunction2;
        this.charClassSymbol = getInternalClassWithoutPackage("kotlin.Char");
        IrClassSymbol internalClassWithoutPackage2 = getInternalClassWithoutPackage("kotlin.String");
        this.stringClassSymbol = internalClassWithoutPackage2;
        this.stringConstructorSymbol = (IrConstructorSymbol) SequencesKt.single(IrUtilsKt.getConstructors(internalClassWithoutPackage2));
        IrClassSymbol internalClassWithoutPackage3 = getInternalClassWithoutPackage("kotlin.Any");
        this.anyClassSymbol = internalClassWithoutPackage3;
        this.anyConstructorSymbol = (IrConstructorSymbol) SequencesKt.single(IrUtilsKt.getConstructors(internalClassWithoutPackage3));
        this.jsObjectClassSymbol = getInternalClassWithoutPackage("kotlin.js.JsObject");
        this.jsObjectConstructorSymbol = JsCommonBackendContextKt.lazy2(this.context, new Function0<IrConstructorSymbol>() { // from class: org.jetbrains.kotlin.ir.backend.js.JsIntrinsics$jsObjectConstructorSymbol$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrConstructorSymbol invoke() {
                return (IrConstructorSymbol) SequencesKt.single(IrUtilsKt.getConstructors(this.this$0.getJsObjectClassSymbol()));
            }
        });
        this.uByteClassSymbol = JsCommonBackendContextKt.lazy2(this.context, new Function0<IrClassSymbol>() { // from class: org.jetbrains.kotlin.ir.backend.js.JsIntrinsics$uByteClassSymbol$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrClassSymbol invoke() {
                return this.this$0.getInternalClassWithoutPackage("kotlin.UByte");
            }
        });
        this.uShortClassSymbol = JsCommonBackendContextKt.lazy2(this.context, new Function0<IrClassSymbol>() { // from class: org.jetbrains.kotlin.ir.backend.js.JsIntrinsics$uShortClassSymbol$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrClassSymbol invoke() {
                return this.this$0.getInternalClassWithoutPackage("kotlin.UShort");
            }
        });
        this.uIntClassSymbol = JsCommonBackendContextKt.lazy2(this.context, new Function0<IrClassSymbol>() { // from class: org.jetbrains.kotlin.ir.backend.js.JsIntrinsics$uIntClassSymbol$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrClassSymbol invoke() {
                return this.this$0.getInternalClassWithoutPackage("kotlin.UInt");
            }
        });
        this.uLongClassSymbol = JsCommonBackendContextKt.lazy2(this.context, new Function0<IrClassSymbol>() { // from class: org.jetbrains.kotlin.ir.backend.js.JsIntrinsics$uLongClassSymbol$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrClassSymbol invoke() {
                return this.this$0.getInternalClassWithoutPackage("kotlin.ULong");
            }
        });
        this.unreachable = getInternalFunction("unreachable");
        this.jsArguments = getInternalFunction("jsArguments");
        this.returnIfSuspended = getInternalFunction("returnIfSuspended");
        this.getContinuation = getInternalFunction("getContinuation");
        this.jsEnsureNonNull = getFunctionInKotlinPackage(Namer.NULL_CHECK_INTRINSIC_NAME);
        this.jsArray = getInternalFunction("arrayWithFun");
        this.jsFillArray = getInternalFunction("fillArrayFun");
        this.jsArrayLength = getInternalFunction("jsArrayLength");
        this.jsArrayGet = getInternalFunction("jsArrayGet");
        this.jsArraySet = getInternalFunction("jsArraySet");
        this.jsArrayIteratorFunction = getInternalFunction("arrayIterator");
        PrimitiveType[] primitiveTypeArrValues = PrimitiveType.values();
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(primitiveTypeArrValues.length), 16));
        for (PrimitiveType primitiveType : primitiveTypeArrValues) {
            StringBuilder sb = new StringBuilder();
            String strAsString = primitiveType.getTypeName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            Pair pair = TuplesKt.to(primitiveType, getInternalFunction(sb.append(CapitalizeDecapitalizeKt.toLowerCaseAsciiOnly(strAsString)).append("ArrayIterator").toString()));
            linkedHashMap.put(pair.getFirst(), pair.getSecond());
        }
        this.jsPrimitiveArrayIteratorFunctions = linkedHashMap;
        this.jsClass = getInternalFunction("jsClassIntrinsic");
        this.arrayLiteral = getInternalFunction("arrayLiteral");
        this.jsArrayLike2Array = getInternalFunction("jsArrayLike2Array");
        this.jsSliceArrayLikeFromIndex = getInternalFunction("jsSliceArrayLikeFromIndex");
        this.jsSliceArrayLikeFromIndexToIndex = getInternalFunction("jsSliceArrayLikeFromIndexToIndex");
        this.reflectionSymbols = new JsReflectionSymbols();
        this.primitiveToTypedArrayMap = new EnumMap<>(MapsKt.mapOf(TuplesKt.to(PrimitiveType.BYTE, "Int8"), TuplesKt.to(PrimitiveType.SHORT, "Int16"), TuplesKt.to(PrimitiveType.INT, "Int32"), TuplesKt.to(PrimitiveType.FLOAT, "Float32"), TuplesKt.to(PrimitiveType.DOUBLE, "Float64")));
        PrimitiveType[] primitiveTypeArrValues2 = PrimitiveType.values();
        LinkedHashMap linkedHashMap2 = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(primitiveTypeArrValues2.length), 16));
        for (PrimitiveType primitiveType2 : primitiveTypeArrValues2) {
            String str = this.primitiveToTypedArrayMap.get(primitiveType2);
            if (str != null) {
                StringBuilder sb2 = new StringBuilder();
                Intrinsics.checkNotNull(str);
                internalFunction2 = getInternalFunction(sb2.append(CapitalizeDecapitalizeKt.toLowerCaseAsciiOnly(str)).append("Array").toString());
                if (internalFunction2 == null) {
                    StringBuilder sb3 = new StringBuilder();
                    String strAsString2 = primitiveType2.getTypeName().asString();
                    Intrinsics.checkNotNullExpressionValue(strAsString2, "asString(...)");
                    internalFunction2 = getInternalFunction(sb3.append(CapitalizeDecapitalizeKt.toLowerCaseAsciiOnly(strAsString2)).append("Array").toString());
                }
            } else {
                StringBuilder sb4 = new StringBuilder();
                String strAsString3 = primitiveType2.getTypeName().asString();
                Intrinsics.checkNotNullExpressionValue(strAsString3, "asString(...)");
                internalFunction2 = getInternalFunction(sb4.append(CapitalizeDecapitalizeKt.toLowerCaseAsciiOnly(strAsString3)).append("Array").toString());
            }
            Pair pair2 = TuplesKt.to(primitiveType2, internalFunction2);
            linkedHashMap2.put(pair2.getFirst(), pair2.getSecond());
        }
        this.primitiveToSizeConstructor = linkedHashMap2;
        PrimitiveType[] primitiveTypeArrValues3 = PrimitiveType.values();
        LinkedHashMap linkedHashMap3 = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(primitiveTypeArrValues3.length), 16));
        for (PrimitiveType primitiveType3 : primitiveTypeArrValues3) {
            String str2 = this.primitiveToTypedArrayMap.get(primitiveType3);
            if (str2 != null) {
                StringBuilder sb5 = new StringBuilder();
                Intrinsics.checkNotNull(str2);
                internalFunction = getInternalFunction(sb5.append(CapitalizeDecapitalizeKt.toLowerCaseAsciiOnly(str2)).append("ArrayOf").toString());
                if (internalFunction == null) {
                    StringBuilder sb6 = new StringBuilder();
                    String strAsString4 = primitiveType3.getTypeName().asString();
                    Intrinsics.checkNotNullExpressionValue(strAsString4, "asString(...)");
                    internalFunction = getInternalFunction(sb6.append(CapitalizeDecapitalizeKt.toLowerCaseAsciiOnly(strAsString4)).append("ArrayOf").toString());
                }
            } else {
                StringBuilder sb7 = new StringBuilder();
                String strAsString5 = primitiveType3.getTypeName().asString();
                Intrinsics.checkNotNullExpressionValue(strAsString5, "asString(...)");
                internalFunction = getInternalFunction(sb7.append(CapitalizeDecapitalizeKt.toLowerCaseAsciiOnly(strAsString5)).append("ArrayOf").toString());
            }
            Pair pair3 = TuplesKt.to(primitiveType3, internalFunction);
            linkedHashMap3.put(pair3.getFirst(), pair3.getSecond());
        }
        this.primitiveToLiteralConstructor = linkedHashMap3;
        this.arrayConcat = getInternalWithoutPackage("arrayConcat");
        this.primitiveArrayConcat = getInternalWithoutPackage("primitiveArrayConcat");
        this.taggedArrayCopy = getInternalWithoutPackage("taggedArrayCopy");
        this.jsArraySlice = getInternalFunction("slice");
        this.jsCall = getInternalFunction("jsCall");
        this.jsBind = getInternalFunction("jsBind");
        this.doNotIntrinsifyAnnotationSymbol = this.context.getSymbolTable().getDescriptorExtension().referenceClass(this.context.getJsInternalClass$backend_js("DoNotIntrinsify"));
        this.jsFunAnnotationSymbol = this.context.getSymbolTable().getDescriptorExtension().referenceClass(this.context.getJsInternalClass$backend_js("JsFun"));
        this.jsNameAnnotationSymbol = this.context.getSymbolTable().getDescriptorExtension().referenceClass(this.context.getJsInternalClass$backend_js("JsName"));
        this.jsExportAnnotationSymbol = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<IrClassSymbol>() { // from class: org.jetbrains.kotlin.ir.backend.js.JsIntrinsics$jsExportAnnotationSymbol$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrClassSymbol invoke() {
                return this.this$0.getContext().getSymbolTable().getDescriptorExtension().referenceClass(this.this$0.getContext().getJsInternalClass$backend_js("JsExport"));
            }
        });
        this.jsExportIgnoreAnnotationSymbol = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<IrClassSymbol>() { // from class: org.jetbrains.kotlin.ir.backend.js.JsIntrinsics$jsExportIgnoreAnnotationSymbol$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrClassSymbol invoke() {
                Object next2;
                IrClassSymbol symbol;
                Iterator<T> it2 = this.this$0.getJsExportAnnotationSymbol().getOwner().getDeclarations().iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        next2 = null;
                        break;
                    }
                    next2 = it2.next();
                    IrDeclaration irDeclaration2 = (IrDeclaration) next2;
                    if ((irDeclaration2 instanceof IrClass) && Intrinsics.areEqual(IrUtilsKt.getFqNameWhenAvailable((IrClass) irDeclaration2), new FqName("kotlin.js.JsExport.Ignore"))) {
                        break;
                    }
                }
                IrClass irClass = (IrClass) (next2 instanceof IrClass ? next2 : null);
                if (irClass == null || (symbol = irClass.getSymbol()) == null) {
                    throw new IllegalStateException("can't find kotlin.js.JsExport.Ignore annotation".toString());
                }
                return symbol;
            }
        });
        this.jsImplicitExportAnnotationSymbol = this.context.getSymbolTable().getDescriptorExtension().referenceClass(this.context.getJsInternalClass$backend_js("JsImplicitExport"));
        this.charSequenceClassSymbol = this.context.getSymbolTable().getDescriptorExtension().referenceClass(this.context.getClass$backend_js(new FqName("kotlin.CharSequence")));
        this.charSequenceLengthPropertyGetterSymbol = JsCommonBackendContextKt.lazy2(this.context, new Function0<IrFunctionSymbol>() { // from class: org.jetbrains.kotlin.ir.backend.js.JsIntrinsics$charSequenceLengthPropertyGetterSymbol$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrFunctionSymbol invoke() {
                Object next2;
                IrSimpleFunction getter;
                List<IrDeclaration> declarations = this.this$0.getCharSequenceClassSymbol().getOwner().getDeclarations();
                ArrayList arrayList = new ArrayList();
                for (Object obj : declarations) {
                    if (obj instanceof IrProperty) {
                        arrayList.add(obj);
                    }
                }
                Iterator it2 = arrayList.iterator();
                do {
                    if (!it2.hasNext()) {
                        next2 = null;
                        break;
                    }
                    next2 = it2.next();
                } while (!Intrinsics.areEqual(((IrProperty) next2).getName().asString(), "length"));
                IrProperty irProperty = (IrProperty) next2;
                if (irProperty == null || (getter = irProperty.getGetter()) == null) {
                    ArrayList<IrFunction> arrayList2 = new ArrayList();
                    for (Object obj2 : declarations) {
                        if (obj2 instanceof IrFunction) {
                            arrayList2.add(obj2);
                        }
                    }
                    for (IrSimpleFunction irSimpleFunction3 : arrayList2) {
                        if (Intrinsics.areEqual(irSimpleFunction3.getName().asString(), "<get-length>")) {
                        }
                    }
                    throw new NoSuchElementException("Collection contains no element matching the predicate.");
                }
                irSimpleFunction3 = getter;
                return irSimpleFunction3.getSymbol();
            }
        });
        this.charSequenceGetFunctionSymbol = JsCommonBackendContextKt.lazy2(this.context, new Function0<IrFunctionSymbol>() { // from class: org.jetbrains.kotlin.ir.backend.js.JsIntrinsics$charSequenceGetFunctionSymbol$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrFunctionSymbol invoke() {
                List<IrDeclaration> declarations = this.this$0.getCharSequenceClassSymbol().getOwner().getDeclarations();
                ArrayList arrayList = new ArrayList();
                for (Object obj : declarations) {
                    if (obj instanceof IrFunction) {
                        arrayList.add(obj);
                    }
                }
                Object obj2 = null;
                boolean z = false;
                for (Object obj3 : arrayList) {
                    if (Intrinsics.areEqual(((IrFunction) obj3).getName().asString(), "get")) {
                        if (z) {
                            throw new IllegalArgumentException("Collection contains more than one matching element.");
                        }
                        z = true;
                        obj2 = obj3;
                    }
                }
                if (z) {
                    return ((IrFunction) obj2).getSymbol();
                }
                throw new NoSuchElementException("Collection contains no element matching the predicate.");
            }
        });
        this.charSequenceSubSequenceFunctionSymbol = JsCommonBackendContextKt.lazy2(this.context, new Function0<IrFunctionSymbol>() { // from class: org.jetbrains.kotlin.ir.backend.js.JsIntrinsics$charSequenceSubSequenceFunctionSymbol$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrFunctionSymbol invoke() {
                List<IrDeclaration> declarations = this.this$0.getCharSequenceClassSymbol().getOwner().getDeclarations();
                ArrayList arrayList = new ArrayList();
                for (Object obj : declarations) {
                    if (obj instanceof IrFunction) {
                        arrayList.add(obj);
                    }
                }
                Object obj2 = null;
                boolean z = false;
                for (Object obj3 : arrayList) {
                    if (Intrinsics.areEqual(((IrFunction) obj3).getName().asString(), "subSequence")) {
                        if (z) {
                            throw new IllegalArgumentException("Collection contains more than one matching element.");
                        }
                        z = true;
                        obj2 = obj3;
                    }
                }
                if (z) {
                    return ((IrFunction) obj2).getSymbol();
                }
                throw new NoSuchElementException("Collection contains no element matching the predicate.");
            }
        });
        this.jsCharSequenceGet = getInternalFunction("charSequenceGet");
        this.jsCharSequenceLength = getInternalFunction("charSequenceLength");
        this.jsCharSequenceSubSequence = getInternalFunction("charSequenceSubSequence");
        this.jsContexfulRef = getInternalFunction("jsContextfulRef");
        this.jsBoxIntrinsic = getInternalFunction("boxIntrinsic");
        this.jsUnboxIntrinsic = getInternalFunction("unboxIntrinsic");
        this.captureStack = getInternalFunction("captureStack");
        this.createSharedBox = getInternalFunction("sharedBoxCreate");
        this.readSharedBox = getInternalFunction("sharedBoxRead");
        this.writeSharedBox = getInternalFunction("sharedBoxWrite");
        this.linkageErrorSymbol = getInternalFunction("throwLinkageError");
        this.jsPrototypeOfSymbol = getInternalFunction("protoOf");
        this.jsDefinePropertySymbol = getInternalFunction("defineProp");
        this.jsObjectCreateSymbol = getInternalFunction("objectCreate");
        this.jsCreateThisSymbol = getInternalFunction("createThis");
        this.jsBoxApplySymbol = getInternalFunction("boxApply");
        this.jsCreateExternalThisSymbol = getInternalFunction("createExternalThis");
    }

    public final JsIrBackendContext getContext() {
        return this.context;
    }

    public final IrSimpleFunctionSymbol getJsIsEs6() {
        return this.jsIsEs6;
    }

    public final IrPropertySymbol getVoid() {
        return this.void;
    }

    public final IrPropertySymbol getGlobalThis() {
        return this.globalThis;
    }

    public final IrSimpleFunctionSymbol getJsEqeq() {
        return this.jsEqeq;
    }

    public final IrSimpleFunctionSymbol getJsNotEq() {
        return this.jsNotEq;
    }

    public final IrSimpleFunctionSymbol getJsEqeqeq() {
        return this.jsEqeqeq;
    }

    public final IrSimpleFunctionSymbol getJsNotEqeq() {
        return this.jsNotEqeq;
    }

    public final IrSimpleFunctionSymbol getJsGt() {
        return this.jsGt;
    }

    public final IrSimpleFunctionSymbol getJsGtEq() {
        return this.jsGtEq;
    }

    public final IrSimpleFunctionSymbol getJsLt() {
        return this.jsLt;
    }

    public final IrSimpleFunctionSymbol getJsLtEq() {
        return this.jsLtEq;
    }

    public final IrSimpleFunctionSymbol getJsNot() {
        return this.jsNot;
    }

    public final IrSimpleFunctionSymbol getJsUnaryPlus() {
        return this.jsUnaryPlus;
    }

    public final IrSimpleFunctionSymbol getJsUnaryMinus() {
        return this.jsUnaryMinus;
    }

    public final IrSimpleFunctionSymbol getJsPrefixInc() {
        return this.jsPrefixInc;
    }

    public final IrSimpleFunctionSymbol getJsPostfixInc() {
        return this.jsPostfixInc;
    }

    public final IrSimpleFunctionSymbol getJsPrefixDec() {
        return this.jsPrefixDec;
    }

    public final IrSimpleFunctionSymbol getJsPostfixDec() {
        return this.jsPostfixDec;
    }

    public final IrSimpleFunctionSymbol getJsDelete() {
        return this.jsDelete;
    }

    public final IrSimpleFunctionSymbol getJsPlus() {
        return this.jsPlus;
    }

    public final IrSimpleFunctionSymbol getJsMinus() {
        return this.jsMinus;
    }

    public final IrSimpleFunctionSymbol getJsMult() {
        return this.jsMult;
    }

    public final IrSimpleFunctionSymbol getJsDiv() {
        return this.jsDiv;
    }

    public final IrSimpleFunctionSymbol getJsMod() {
        return this.jsMod;
    }

    public final IrSimpleFunctionSymbol getJsPlusAssign() {
        return this.jsPlusAssign;
    }

    public final IrSimpleFunctionSymbol getJsMinusAssign() {
        return this.jsMinusAssign;
    }

    public final IrSimpleFunctionSymbol getJsMultAssign() {
        return this.jsMultAssign;
    }

    public final IrSimpleFunctionSymbol getJsDivAssign() {
        return this.jsDivAssign;
    }

    public final IrSimpleFunctionSymbol getJsModAssign() {
        return this.jsModAssign;
    }

    public final IrSimpleFunctionSymbol getJsAnd() {
        return this.jsAnd;
    }

    public final IrSimpleFunctionSymbol getJsOr() {
        return this.jsOr;
    }

    public final IrSimpleFunctionSymbol getJsIn() {
        return this.jsIn;
    }

    public final IrSimpleFunctionSymbol getJsBitAnd() {
        return this.jsBitAnd;
    }

    public final IrSimpleFunctionSymbol getJsBitOr() {
        return this.jsBitOr;
    }

    public final IrSimpleFunctionSymbol getJsBitXor() {
        return this.jsBitXor;
    }

    public final IrSimpleFunctionSymbol getJsBitNot() {
        return this.jsBitNot;
    }

    public final IrSimpleFunctionSymbol getJsBitShiftR() {
        return this.jsBitShiftR;
    }

    public final IrSimpleFunctionSymbol getJsBitShiftRU() {
        return this.jsBitShiftRU;
    }

    public final IrSimpleFunctionSymbol getJsBitShiftL() {
        return this.jsBitShiftL;
    }

    public final IrSimpleFunctionSymbol getJsInstanceOf() {
        return this.jsInstanceOf;
    }

    public final IrSimpleFunctionSymbol getJsTypeOf() {
        return this.jsTypeOf;
    }

    /* JADX INFO: renamed from: isExternalObject, reason: from getter */
    public final IrSimpleFunctionSymbol getIsExternalObject() {
        return this.isExternalObject;
    }

    public final IrSimpleFunctionSymbol getJsNumberToByte() {
        return this.jsNumberToByte;
    }

    public final IrSimpleFunctionSymbol getJsNumberToDouble() {
        return this.jsNumberToDouble;
    }

    public final IrSimpleFunctionSymbol getJsNumberToInt() {
        return this.jsNumberToInt;
    }

    public final IrSimpleFunctionSymbol getJsNumberToShort() {
        return this.jsNumberToShort;
    }

    public final IrSimpleFunctionSymbol getJsNumberToLong() {
        return this.jsNumberToLong;
    }

    public final IrSimpleFunctionSymbol getJsNumberToChar() {
        return this.jsNumberToChar;
    }

    public final IrSimpleFunctionSymbol getJsToByte() {
        return this.jsToByte;
    }

    public final IrSimpleFunctionSymbol getJsToShort() {
        return this.jsToShort;
    }

    public final IrSimpleFunctionSymbol getJsToLong() {
        return this.jsToLong;
    }

    public final IrSimpleFunctionSymbol getImplementSymbol() {
        return this.implementSymbol;
    }

    public final IrSimpleFunctionSymbol getSetMetadataForSymbol() {
        return this.setMetadataForSymbol;
    }

    /* JADX INFO: renamed from: isInterfaceSymbol, reason: from getter */
    public final IrSimpleFunctionSymbol getIsInterfaceSymbol() {
        return this.isInterfaceSymbol;
    }

    /* JADX INFO: renamed from: isArraySymbol, reason: from getter */
    public final IrSimpleFunctionSymbol getIsArraySymbol() {
        return this.isArraySymbol;
    }

    /* JADX INFO: renamed from: isSuspendFunctionSymbol, reason: from getter */
    public final IrSimpleFunctionSymbol getIsSuspendFunctionSymbol() {
        return this.isSuspendFunctionSymbol;
    }

    /* JADX INFO: renamed from: isNumberSymbol, reason: from getter */
    public final IrSimpleFunctionSymbol getIsNumberSymbol() {
        return this.isNumberSymbol;
    }

    /* JADX INFO: renamed from: isComparableSymbol, reason: from getter */
    public final IrSimpleFunctionSymbol getIsComparableSymbol() {
        return this.isComparableSymbol;
    }

    /* JADX INFO: renamed from: isCharSequenceSymbol, reason: from getter */
    public final IrSimpleFunctionSymbol getIsCharSequenceSymbol() {
        return this.isCharSequenceSymbol;
    }

    public final Map<PrimitiveType, IrSimpleFunctionSymbol> isPrimitiveArray() {
        return this.isPrimitiveArray;
    }

    public final IrSimpleFunctionSymbol getEnumValueOfIntrinsic() {
        return this.enumValueOfIntrinsic;
    }

    public final IrSimpleFunctionSymbol getEnumValuesIntrinsic() {
        return this.enumValuesIntrinsic;
    }

    public final IrSimpleFunctionSymbol getEnumEntriesIntrinsic() {
        return this.enumEntriesIntrinsic;
    }

    public final IrSimpleFunctionSymbol getJsCode() {
        return this.jsCode;
    }

    public final IrSimpleFunctionSymbol getJsHashCode() {
        return this.jsHashCode;
    }

    public final IrSimpleFunctionSymbol getJsGetBooleanHashCode() {
        return this.jsGetBooleanHashCode;
    }

    public final IrSimpleFunctionSymbol getJsGetNumberHashCode() {
        return this.jsGetNumberHashCode;
    }

    public final IrSimpleFunctionSymbol getJsGetObjectHashCode() {
        return this.jsGetObjectHashCode;
    }

    public final IrSimpleFunctionSymbol getJsGetStringHashCode() {
        return this.jsGetStringHashCode;
    }

    public final IrSimpleFunctionSymbol getJsToString() {
        return this.jsToString;
    }

    public final IrSimpleFunctionSymbol getJsAnyToString() {
        return this.jsAnyToString;
    }

    public final IrSimpleFunctionSymbol getJsCompareTo() {
        return this.jsCompareTo;
    }

    public final IrSimpleFunctionSymbol getJsEquals() {
        return this.jsEquals;
    }

    public final IrSimpleFunctionSymbol getJsNewTarget() {
        return this.jsNewTarget;
    }

    public final IrSimpleFunctionSymbol getJsEmptyObject() {
        return this.jsEmptyObject;
    }

    public final IrSimpleFunctionSymbol getJsOpenInitializerBox() {
        return this.jsOpenInitializerBox;
    }

    public final IrSimpleFunctionSymbol getJsImul() {
        return this.jsImul;
    }

    public final IrSimpleFunctionSymbol getJsUnreachableDeclarationLog() {
        return this.jsUnreachableDeclarationLog;
    }

    public final IrSimpleFunctionSymbol getJsUnreachableDeclarationException() {
        return this.jsUnreachableDeclarationException;
    }

    public final IrSimpleFunctionSymbol getJsNativeBoolean() {
        return this.jsNativeBoolean;
    }

    public final IrSimpleFunctionSymbol getJsBooleanInExternalLog() {
        return this.jsBooleanInExternalLog;
    }

    public final IrSimpleFunctionSymbol getJsBooleanInExternalException() {
        return this.jsBooleanInExternalException;
    }

    public final IrSimpleFunctionSymbol getJsNewAnonymousClass() {
        return this.jsNewAnonymousClass;
    }

    public final IrSimpleFunctionSymbol getJsCoroutineContext() {
        return this.context.getIr().getSymbols().getCoroutineContextGetter();
    }

    public final IrSimpleFunctionSymbol getJsGetContinuation() {
        return this.jsGetContinuation;
    }

    public final IrSimpleFunctionSymbol getJsInvokeSuspendSuperType() {
        return this.jsInvokeSuspendSuperType;
    }

    public final IrSimpleFunctionSymbol getJsInvokeSuspendSuperTypeWithReceiver() {
        return this.jsInvokeSuspendSuperTypeWithReceiver;
    }

    public final IrSimpleFunctionSymbol getJsInvokeSuspendSuperTypeWithReceiverAndParam() {
        return this.jsInvokeSuspendSuperTypeWithReceiverAndParam;
    }

    public final IrSimpleFunctionSymbol getJsNumberRangeToNumber() {
        return this.jsNumberRangeToNumber;
    }

    public final IrSimpleFunctionSymbol getJsNumberRangeToLong() {
        return this.jsNumberRangeToLong;
    }

    public final Map<Pair<IrType, IrType>, IrSimpleFunctionSymbol> getRangeUntilFunctions() {
        return (Map) this.rangeUntilFunctions.getValue();
    }

    public final IrClassSymbol getLongClassSymbol() {
        return this.longClassSymbol;
    }

    public final IrClassSymbol getPromiseClassSymbol() {
        return (IrClassSymbol) this.promiseClassSymbol.getValue();
    }

    public final IrSimpleFunctionSymbol getMetadataInterfaceConstructorSymbol() {
        return this.metadataInterfaceConstructorSymbol;
    }

    public final IrSimpleFunctionSymbol getMetadataObjectConstructorSymbol() {
        return this.metadataObjectConstructorSymbol;
    }

    public final IrSimpleFunctionSymbol getMetadataClassConstructorSymbol() {
        return this.metadataClassConstructorSymbol;
    }

    public final IrSimpleFunctionSymbol getLongToDouble() {
        return this.longToDouble;
    }

    public final IrSimpleFunctionSymbol getLongToFloat() {
        return this.longToFloat;
    }

    public final IrSimpleFunction getLongCompareToLong() {
        return this.longCompareToLong;
    }

    public final IrClassSymbol getCharClassSymbol() {
        return this.charClassSymbol;
    }

    public final IrClassSymbol getStringClassSymbol() {
        return this.stringClassSymbol;
    }

    public final IrConstructorSymbol getStringConstructorSymbol() {
        return this.stringConstructorSymbol;
    }

    public final IrClassSymbol getAnyClassSymbol() {
        return this.anyClassSymbol;
    }

    public final IrConstructorSymbol getAnyConstructorSymbol() {
        return this.anyConstructorSymbol;
    }

    public final IrClassSymbol getJsObjectClassSymbol() {
        return this.jsObjectClassSymbol;
    }

    public final IrConstructorSymbol getJsObjectConstructorSymbol() {
        return (IrConstructorSymbol) this.jsObjectConstructorSymbol.getValue();
    }

    public final IrClassSymbol getUByteClassSymbol() {
        return (IrClassSymbol) this.uByteClassSymbol.getValue();
    }

    public final IrClassSymbol getUShortClassSymbol() {
        return (IrClassSymbol) this.uShortClassSymbol.getValue();
    }

    public final IrClassSymbol getUIntClassSymbol() {
        return (IrClassSymbol) this.uIntClassSymbol.getValue();
    }

    public final IrClassSymbol getULongClassSymbol() {
        return (IrClassSymbol) this.uLongClassSymbol.getValue();
    }

    public final IrSimpleFunctionSymbol getUnreachable() {
        return this.unreachable;
    }

    public final IrSimpleFunctionSymbol getJsArguments() {
        return this.jsArguments;
    }

    public final IrSimpleFunctionSymbol getReturnIfSuspended() {
        return this.returnIfSuspended;
    }

    public final IrSimpleFunctionSymbol getGetContinuation() {
        return this.getContinuation;
    }

    public final IrSimpleFunctionSymbol getJsEnsureNonNull() {
        return this.jsEnsureNonNull;
    }

    public final IrClassSymbol getArray() {
        return this.irBuiltIns.getArrayClass();
    }

    public final Map<IrClassSymbol, PrimitiveType> getPrimitiveArrays() {
        return this.irBuiltIns.getPrimitiveArraysToPrimitiveTypes();
    }

    public final IrSimpleFunctionSymbol getJsArray() {
        return this.jsArray;
    }

    public final IrSimpleFunctionSymbol getJsFillArray() {
        return this.jsFillArray;
    }

    public final IrSimpleFunctionSymbol getJsArrayLength() {
        return this.jsArrayLength;
    }

    public final IrSimpleFunctionSymbol getJsArrayGet() {
        return this.jsArrayGet;
    }

    public final IrSimpleFunctionSymbol getJsArraySet() {
        return this.jsArraySet;
    }

    public final IrSimpleFunctionSymbol getJsArrayIteratorFunction() {
        return this.jsArrayIteratorFunction;
    }

    public final Map<PrimitiveType, IrSimpleFunctionSymbol> getJsPrimitiveArrayIteratorFunctions() {
        return this.jsPrimitiveArrayIteratorFunctions;
    }

    public final IrSimpleFunctionSymbol getJsClass() {
        return this.jsClass;
    }

    public final IrSimpleFunctionSymbol getArrayLiteral() {
        return this.arrayLiteral;
    }

    public final IrSimpleFunctionSymbol getJsArrayLike2Array() {
        return this.jsArrayLike2Array;
    }

    public final IrSimpleFunctionSymbol getJsSliceArrayLikeFromIndex() {
        return this.jsSliceArrayLikeFromIndex;
    }

    public final IrSimpleFunctionSymbol getJsSliceArrayLikeFromIndexToIndex() {
        return this.jsSliceArrayLikeFromIndexToIndex;
    }

    /* JADX INFO: compiled from: JsIntrinsics.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0080\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u0016\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0016\u0010\u0007\u001a\u0004\u0018\u00010\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006R\u0016\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0006R\u0016\u0010\u000b\u001a\u0004\u0018\u00010\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0006R\u0016\u0010\r\u001a\u0004\u0018\u00010\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0006R\u0016\u0010\u000f\u001a\u0004\u0018\u00010\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0006R\u0014\u0010\u0011\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0006R\u0014\u0010\u0013\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0006R\u0014\u0010\u0015\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0006R\u0016\u0010\u0017\u001a\u0004\u0018\u00010\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0006R\u0014\u0010\u0019\u001a\u00020\u001aX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0014\u0010\u001d\u001a\u00020\u001aX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001c¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/JsIntrinsics$JsReflectionSymbols;", "Lorg/jetbrains/kotlin/ir/backend/js/ReflectionSymbols;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsIntrinsics;)V", Namer.CREATE_CONTRAVARIANT_KTYPE_PROJECTION, "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "getCreateContravariantKTypeProjection", "()Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", Namer.CREATE_COVARIANT_KTYPE_PROJECTION, "getCreateCovariantKTypeProjection", Namer.CREATE_DYNAMIC_KTYPE, "getCreateDynamicKType", Namer.CREATE_INVARIANT_KTYPE_PROJECTION, "getCreateInvariantKTypeProjection", Namer.CREATE_KTYPE, "getCreateKType", Namer.CREATE_KTYPE_PARAMETER, "getCreateKTypeParameter", "getClassData", "getGetClassData", Namer.GET_KCLASS, "getGetKClass", Namer.GET_KCLASS_FROM_EXPRESSION, "getGetKClassFromExpression", Namer.GET_START_KTYPE_PROJECTION, "getGetStarKTypeProjection", "kTypeClass", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "getKTypeClass", "()Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "primitiveClassesObject", "getPrimitiveClassesObject", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public final class JsReflectionSymbols implements ReflectionSymbols {
        private final IrSimpleFunctionSymbol createContravariantKTypeProjection;
        private final IrSimpleFunctionSymbol createCovariantKTypeProjection;
        private final IrSimpleFunctionSymbol createDynamicKType;
        private final IrSimpleFunctionSymbol createInvariantKTypeProjection;
        private final IrSimpleFunctionSymbol createKType;
        private final IrSimpleFunctionSymbol createKTypeParameter;
        private final IrSimpleFunctionSymbol getKClass;
        private final IrSimpleFunctionSymbol getKClassFromExpression;
        private final IrSimpleFunctionSymbol getStarKTypeProjection;
        private final IrClassSymbol kTypeClass;
        private final IrClassSymbol primitiveClassesObject;

        public JsReflectionSymbols() {
            this.createKType = JsIntrinsics.this.getInternalWithoutPackageOrNull(Namer.CREATE_KTYPE);
            this.createDynamicKType = JsIntrinsics.this.getInternalWithoutPackageOrNull(Namer.CREATE_DYNAMIC_KTYPE);
            this.createKTypeParameter = JsIntrinsics.this.getInternalWithoutPackageOrNull(Namer.CREATE_KTYPE_PARAMETER);
            this.getStarKTypeProjection = JsIntrinsics.this.getInternalWithoutPackageOrNull(Namer.GET_START_KTYPE_PROJECTION);
            this.createCovariantKTypeProjection = JsIntrinsics.this.getInternalWithoutPackageOrNull(Namer.CREATE_COVARIANT_KTYPE_PROJECTION);
            this.createInvariantKTypeProjection = JsIntrinsics.this.getInternalWithoutPackageOrNull(Namer.CREATE_INVARIANT_KTYPE_PROJECTION);
            this.createContravariantKTypeProjection = JsIntrinsics.this.getInternalWithoutPackageOrNull(Namer.CREATE_CONTRAVARIANT_KTYPE_PROJECTION);
            this.getKClass = JsIntrinsics.this.getInternalWithoutPackage(Namer.GET_KCLASS);
            this.getKClassFromExpression = JsIntrinsics.this.getInternalWithoutPackage(Namer.GET_KCLASS_FROM_EXPRESSION);
            this.primitiveClassesObject = JsIntrinsics.this.getContext().getIrClass$backend_js(new FqName("kotlin.reflect.js.internal.PrimitiveClasses"));
            this.kTypeClass = JsIntrinsics.this.getContext().getIrClass$backend_js(new FqName("kotlin.reflect.KType"));
        }

        @Override // org.jetbrains.kotlin.ir.backend.js.ReflectionSymbols
        public IrSimpleFunctionSymbol getCreateKType() {
            return this.createKType;
        }

        @Override // org.jetbrains.kotlin.ir.backend.js.ReflectionSymbols
        public IrSimpleFunctionSymbol getCreateDynamicKType() {
            return this.createDynamicKType;
        }

        @Override // org.jetbrains.kotlin.ir.backend.js.ReflectionSymbols
        public IrSimpleFunctionSymbol getCreateKTypeParameter() {
            return this.createKTypeParameter;
        }

        @Override // org.jetbrains.kotlin.ir.backend.js.ReflectionSymbols
        public IrSimpleFunctionSymbol getGetStarKTypeProjection() {
            return this.getStarKTypeProjection;
        }

        @Override // org.jetbrains.kotlin.ir.backend.js.ReflectionSymbols
        public IrSimpleFunctionSymbol getCreateCovariantKTypeProjection() {
            return this.createCovariantKTypeProjection;
        }

        @Override // org.jetbrains.kotlin.ir.backend.js.ReflectionSymbols
        public IrSimpleFunctionSymbol getCreateInvariantKTypeProjection() {
            return this.createInvariantKTypeProjection;
        }

        @Override // org.jetbrains.kotlin.ir.backend.js.ReflectionSymbols
        public IrSimpleFunctionSymbol getCreateContravariantKTypeProjection() {
            return this.createContravariantKTypeProjection;
        }

        @Override // org.jetbrains.kotlin.ir.backend.js.ReflectionSymbols
        public IrSimpleFunctionSymbol getGetKClass() {
            return this.getKClass;
        }

        @Override // org.jetbrains.kotlin.ir.backend.js.ReflectionSymbols
        public IrSimpleFunctionSymbol getGetKClassFromExpression() {
            return this.getKClassFromExpression;
        }

        @Override // org.jetbrains.kotlin.ir.backend.js.ReflectionSymbols
        public IrClassSymbol getPrimitiveClassesObject() {
            return this.primitiveClassesObject;
        }

        @Override // org.jetbrains.kotlin.ir.backend.js.ReflectionSymbols
        public IrClassSymbol getKTypeClass() {
            return this.kTypeClass;
        }

        @Override // org.jetbrains.kotlin.ir.backend.js.ReflectionSymbols
        public IrSimpleFunctionSymbol getGetClassData() {
            return JsIntrinsics.this.getJsClass();
        }
    }

    /* JADX INFO: renamed from: getReflectionSymbols$backend_js, reason: from getter */
    public final JsReflectionSymbols getReflectionSymbols() {
        return this.reflectionSymbols;
    }

    public final EnumMap<PrimitiveType, String> getPrimitiveToTypedArrayMap() {
        return this.primitiveToTypedArrayMap;
    }

    public final Map<PrimitiveType, IrSimpleFunctionSymbol> getPrimitiveToSizeConstructor() {
        return this.primitiveToSizeConstructor;
    }

    public final Map<PrimitiveType, IrSimpleFunctionSymbol> getPrimitiveToLiteralConstructor() {
        return this.primitiveToLiteralConstructor;
    }

    public final IrSimpleFunctionSymbol getArrayConcat() {
        return this.arrayConcat;
    }

    public final IrSimpleFunctionSymbol getPrimitiveArrayConcat() {
        return this.primitiveArrayConcat;
    }

    public final IrSimpleFunctionSymbol getTaggedArrayCopy() {
        return this.taggedArrayCopy;
    }

    public final IrSimpleFunctionSymbol getJsArraySlice() {
        return this.jsArraySlice;
    }

    public final IrSimpleFunctionSymbol getJsCall() {
        return this.jsCall;
    }

    public final IrSimpleFunctionSymbol getJsBind() {
        return this.jsBind;
    }

    public final IrClassSymbol getDoNotIntrinsifyAnnotationSymbol() {
        return this.doNotIntrinsifyAnnotationSymbol;
    }

    public final IrClassSymbol getJsFunAnnotationSymbol() {
        return this.jsFunAnnotationSymbol;
    }

    public final IrClassSymbol getJsNameAnnotationSymbol() {
        return this.jsNameAnnotationSymbol;
    }

    public final IrClassSymbol getJsExportAnnotationSymbol() {
        return (IrClassSymbol) this.jsExportAnnotationSymbol.getValue();
    }

    public final IrClassSymbol getJsExportIgnoreAnnotationSymbol() {
        return (IrClassSymbol) this.jsExportIgnoreAnnotationSymbol.getValue();
    }

    public final IrClassSymbol getJsImplicitExportAnnotationSymbol() {
        return this.jsImplicitExportAnnotationSymbol;
    }

    public final IrClassSymbol getCharSequenceClassSymbol() {
        return this.charSequenceClassSymbol;
    }

    public final IrFunctionSymbol getCharSequenceLengthPropertyGetterSymbol() {
        return (IrFunctionSymbol) this.charSequenceLengthPropertyGetterSymbol.getValue();
    }

    public final IrFunctionSymbol getCharSequenceGetFunctionSymbol() {
        return (IrFunctionSymbol) this.charSequenceGetFunctionSymbol.getValue();
    }

    public final IrFunctionSymbol getCharSequenceSubSequenceFunctionSymbol() {
        return (IrFunctionSymbol) this.charSequenceSubSequenceFunctionSymbol.getValue();
    }

    public final IrSimpleFunctionSymbol getJsCharSequenceGet() {
        return this.jsCharSequenceGet;
    }

    public final IrSimpleFunctionSymbol getJsCharSequenceLength() {
        return this.jsCharSequenceLength;
    }

    public final IrSimpleFunctionSymbol getJsCharSequenceSubSequence() {
        return this.jsCharSequenceSubSequence;
    }

    public final IrSimpleFunctionSymbol getJsContexfulRef() {
        return this.jsContexfulRef;
    }

    public final IrSimpleFunctionSymbol getJsBoxIntrinsic() {
        return this.jsBoxIntrinsic;
    }

    public final IrSimpleFunctionSymbol getJsUnboxIntrinsic() {
        return this.jsUnboxIntrinsic;
    }

    public final IrSimpleFunctionSymbol getCaptureStack() {
        return this.captureStack;
    }

    public final IrSimpleFunctionSymbol getCreateSharedBox() {
        return this.createSharedBox;
    }

    public final IrSimpleFunctionSymbol getReadSharedBox() {
        return this.readSharedBox;
    }

    public final IrSimpleFunctionSymbol getWriteSharedBox() {
        return this.writeSharedBox;
    }

    public final IrSimpleFunctionSymbol getLinkageErrorSymbol() {
        return this.linkageErrorSymbol;
    }

    public final IrSimpleFunctionSymbol getJsPrototypeOfSymbol() {
        return this.jsPrototypeOfSymbol;
    }

    public final IrSimpleFunctionSymbol getJsDefinePropertySymbol() {
        return this.jsDefinePropertySymbol;
    }

    public final IrSimpleFunctionSymbol getJsObjectCreateSymbol() {
        return this.jsObjectCreateSymbol;
    }

    public final IrSimpleFunctionSymbol getJsCreateThisSymbol() {
        return this.jsCreateThisSymbol;
    }

    public final IrSimpleFunctionSymbol getJsBoxApplySymbol() {
        return this.jsBoxApplySymbol;
    }

    public final IrSimpleFunctionSymbol getJsCreateExternalThisSymbol() {
        return this.jsCreateExternalThisSymbol;
    }

    private final IrSimpleFunctionSymbol getInternalFunction(String name2) {
        return this.context.getSymbolTable().getDescriptorExtension().referenceSimpleFunction(this.context.getJsInternalFunction$backend_js(name2));
    }

    private final IrPropertySymbol getInternalProperty(String name2) {
        return this.context.getSymbolTable().getDescriptorExtension().referenceProperty(this.context.getJsInternalProperty$backend_js(name2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrSimpleFunctionSymbol getInternalWithoutPackage(String name2) {
        return this.context.getSymbolTable().getDescriptorExtension().referenceSimpleFunction(CollectionsKt.single((List) this.context.getFunctions(new FqName(name2))));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrSimpleFunctionSymbol getInternalWithoutPackageOrNull(String name2) {
        SimpleFunctionDescriptor simpleFunctionDescriptor = (SimpleFunctionDescriptor) CollectionsKt.singleOrNull((List) this.context.getFunctions(new FqName(name2)));
        if (simpleFunctionDescriptor == null) {
            return null;
        }
        return this.context.getSymbolTable().getDescriptorExtension().referenceSimpleFunction(simpleFunctionDescriptor);
    }

    private final IrSimpleFunctionSymbol getFunctionInEnumPackage(String name2) {
        DescriptorSymbolTableExtension descriptorExtension = this.context.getSymbolTable().getDescriptorExtension();
        JsIrBackendContext jsIrBackendContext = this.context;
        FqName fqNameChild = IrTypeUtilsKt.getKotlinEnumsPackageFqn().child(Name.identifier(name2));
        Intrinsics.checkNotNullExpressionValue(fqNameChild, "child(...)");
        return descriptorExtension.referenceSimpleFunction(CollectionsKt.single((List) jsIrBackendContext.getFunctions(fqNameChild)));
    }

    private final IrSimpleFunctionSymbol getFunctionInKotlinPackage(String name2) {
        DescriptorSymbolTableExtension descriptorExtension = this.context.getSymbolTable().getDescriptorExtension();
        JsIrBackendContext jsIrBackendContext = this.context;
        FqName fqNameChild = IrTypeUtilsKt.getKotlinPackageFqn().child(Name.identifier(name2));
        Intrinsics.checkNotNullExpressionValue(fqNameChild, "child(...)");
        return descriptorExtension.referenceSimpleFunction(CollectionsKt.single((List) jsIrBackendContext.getFunctions(fqNameChild)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrClassSymbol getInternalClassWithoutPackage(String fqName) {
        return this.context.getSymbolTable().getDescriptorExtension().referenceClass(this.context.getClass$backend_js(new FqName(fqName)));
    }
}
