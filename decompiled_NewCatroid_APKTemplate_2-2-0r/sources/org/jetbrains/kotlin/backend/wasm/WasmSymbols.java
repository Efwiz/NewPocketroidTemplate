package org.jetbrains.kotlin.backend.wasm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.sequences.SequencesKt;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.backend.common.ir.Symbols;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PackageViewDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.incremental.IncrementalJvmCacheKt;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.ir.BuiltInOperatorNames;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.backend.js.ReflectionSymbols;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.DescriptorSymbolTableExtension;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.ir.util.SymbolTable;
import org.jetbrains.kotlin.js.config.JSConfigurationKeys;
import org.jetbrains.kotlin.js.config.WasmTarget;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: WasmSymbols.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000¬\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u001c\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b1\n\u0002\u0018\u0002\n\u0002\b>\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0010\u0018\u00002\u00020\u0001:\u0006û\u0001ü\u0001ý\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u001e\u0010Ø\u0001\u001a\u00030Ù\u00012\b\u0010Ú\u0001\u001a\u00030Û\u00012\b\u0010Ü\u0001\u001a\u00030Ý\u0001H\u0002J\u0010\u0010Þ\u0001\u001a\u00020\b2\u0007\u0010ß\u0001\u001a\u00020 J\u0010\u0010à\u0001\u001a\u00020\b2\u0007\u0010ß\u0001\u001a\u00020 J$\u0010á\u0001\u001a\t\u0012\u0005\u0012\u00030â\u00010\u000e2\b\u0010Ú\u0001\u001a\u00030Û\u00012\b\u0010Ü\u0001\u001a\u00030Ý\u0001H\u0002J!\u0010ã\u0001\u001a\u00020\b2\u0007\u0010ß\u0001\u001a\u00020 2\r\u0010ä\u0001\u001a\b\u0012\u0004\u0012\u00020\b0\u000eH\u0002J$\u0010å\u0001\u001a\t\u0012\u0005\u0012\u00030æ\u00010\u000e2\b\u0010Ú\u0001\u001a\u00030Û\u00012\b\u0010Ü\u0001\u001a\u00030Ý\u0001H\u0002J\u0010\u0010ç\u0001\u001a\u00020\b2\u0007\u0010è\u0001\u001a\u00020 J\u001a\u0010é\u0001\u001a\u00030Ù\u00012\b\u0010ê\u0001\u001a\u00030ë\u0001H\u0000¢\u0006\u0003\bì\u0001J\u0013\u0010í\u0001\u001a\u00020\b2\b\u0010Ü\u0001\u001a\u00030î\u0001H\u0002J\u001c\u0010ï\u0001\u001a\u00020\b2\b\u0010Ü\u0001\u001a\u00030î\u00012\u0007\u0010ð\u0001\u001a\u00020\u0018H\u0002J\u0013\u0010ñ\u0001\u001a\u00020$2\b\u0010Ü\u0001\u001a\u00030î\u0001H\u0002J\u0013\u0010ò\u0001\u001a\u00020\b2\b\u0010Ü\u0001\u001a\u00030î\u0001H\u0002J\u0013\u0010ó\u0001\u001a\u00020$2\b\u0010ê\u0001\u001a\u00030ë\u0001H\u0002J\u001f\u0010ô\u0001\u001a\u00020 2\u0007\u0010è\u0001\u001a\u00020 2\r\u0010õ\u0001\u001a\b\u0012\u0004\u0012\u00020 0\u000eJ\u001a\u0010ö\u0001\u001a\u00030æ\u00012\b\u0010ê\u0001\u001a\u00030ë\u0001H\u0000¢\u0006\u0003\b÷\u0001J\u001e\u0010ø\u0001\u001a\u0004\u0018\u00010\b2\b\u0010Ü\u0001\u001a\u00030î\u00012\u0007\u0010ð\u0001\u001a\u00020\u0018H\u0002J\u0013\u0010ù\u0001\u001a\u00030î\u00012\u0007\u0010ú\u0001\u001a\u00020KH\u0002R\u0014\u0010\u0007\u001a\u00020\bX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\nR\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\b0\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\b0\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0010R\u0011\u0010\u0013\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\nR\u0011\u0010\u0015\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\nR\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u001d\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b0\u001b¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u000e\u0010\u001e\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u00020 \u0012\u0004\u0012\u00020\b0\u001bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010!\u001a\b\u0012\u0004\u0012\u00020\b0\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\"\u001a\b\u0012\u0004\u0012\u00020\b0\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010#\u001a\u00020$X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b%\u0010&R\u0014\u0010'\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b(\u0010\nR\u0011\u0010)\u001a\u00020*¢\u0006\b\n\u0000\u001a\u0004\b+\u0010,R\u0014\u0010-\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b.\u0010\nR\u0014\u0010/\u001a\u00020$X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b0\u0010&R\u0014\u00101\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b2\u0010\nR\u0011\u00103\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b4\u0010\nR\u0014\u00105\u001a\u00020$X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b6\u0010&R\u0014\u00107\u001a\u00020$X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b8\u0010&R\u0011\u00109\u001a\u00020$¢\u0006\b\n\u0000\u001a\u0004\b:\u0010&R\u0011\u0010;\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b<\u0010\nR\u0011\u0010=\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b>\u0010\nR\u0011\u0010?\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b@\u0010\nR\u000e\u0010A\u001a\u00020\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u001d\u0010B\u001a\u000e\u0012\u0004\u0012\u00020 \u0012\u0004\u0012\u00020\b0\u001b¢\u0006\b\n\u0000\u001a\u0004\bC\u0010\u001dR\u001d\u0010D\u001a\u000e\u0012\u0004\u0012\u00020 \u0012\u0004\u0012\u00020\b0\u001b¢\u0006\b\n\u0000\u001a\u0004\bE\u0010\u001dR\u0014\u0010F\u001a\u00020$8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bG\u0010&R\u0014\u0010H\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bI\u0010\nR'\u0010J\u001a\u000e\u0012\u0004\u0012\u00020K\u0012\u0004\u0012\u00020\b0\u001b8VX\u0096\u0084\u0002¢\u0006\f\n\u0004\bM\u0010N\u001a\u0004\bL\u0010\u001dR\u0014\u0010O\u001a\b\u0012\u0004\u0012\u00020\b0PX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010Q\u001a\u00020\bX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\bR\u0010\nR\u0011\u0010S\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\bT\u0010\nR\u0014\u0010U\u001a\u00020*X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\bV\u0010,R\u0015\u0010W\u001a\u00060XR\u00020\u00008F¢\u0006\u0006\u001a\u0004\bY\u0010ZR\u0014\u0010[\u001a\b\u0018\u00010XR\u00020\u0000X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\\\u001a\u00020$¢\u0006\b\n\u0000\u001a\u0004\b]\u0010&R\u0011\u0010^\u001a\u00020$¢\u0006\b\n\u0000\u001a\u0004\b_\u0010&R\u0011\u0010`\u001a\u00020$¢\u0006\b\n\u0000\u001a\u0004\ba\u0010&R\u0011\u0010b\u001a\u00020$¢\u0006\b\n\u0000\u001a\u0004\bc\u0010&R\u0011\u0010d\u001a\u00020$¢\u0006\b\n\u0000\u001a\u0004\be\u0010&R\u0011\u0010f\u001a\u00020$¢\u0006\b\n\u0000\u001a\u0004\bg\u0010&R\u0011\u0010h\u001a\u00020$¢\u0006\b\n\u0000\u001a\u0004\bi\u0010&R\u0011\u0010j\u001a\u00020$¢\u0006\b\n\u0000\u001a\u0004\bk\u0010&R\u0011\u0010l\u001a\u00020$¢\u0006\b\n\u0000\u001a\u0004\bm\u0010&R\u0011\u0010n\u001a\u00020$¢\u0006\b\n\u0000\u001a\u0004\bo\u0010&R\u0011\u0010p\u001a\u00020$¢\u0006\b\n\u0000\u001a\u0004\bq\u0010&R\u0011\u0010r\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\bs\u0010\nR\u000e\u0010t\u001a\u00020\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010u\u001a\u00020\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010v\u001a\u00020\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010w\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\bx\u0010\nR\u0011\u0010y\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\bz\u0010\nR\u0011\u0010{\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b|\u0010\nR\u001d\u0010}\u001a\u000e\u0012\u0004\u0012\u00020$\u0012\u0004\u0012\u00020\b0\u001b¢\u0006\b\n\u0000\u001a\u0004\b~\u0010\u001dR\u0012\u0010\u007f\u001a\u00020\b¢\u0006\t\n\u0000\u001a\u0005\b\u0080\u0001\u0010\nR\u0013\u0010\u0081\u0001\u001a\u00020\b¢\u0006\t\n\u0000\u001a\u0005\b\u0082\u0001\u0010\nR\u0013\u0010\u0083\u0001\u001a\u00020\b¢\u0006\t\n\u0000\u001a\u0005\b\u0084\u0001\u0010\nR\u0013\u0010\u0085\u0001\u001a\u00020\b¢\u0006\t\n\u0000\u001a\u0005\b\u0086\u0001\u0010\nR\u0013\u0010\u0087\u0001\u001a\u00020\b¢\u0006\t\n\u0000\u001a\u0005\b\u0088\u0001\u0010\nR\u001c\u0010\u0089\u0001\u001a\u00070\u008a\u0001R\u00020\u0000X\u0080\u0004¢\u0006\n\n\u0000\u001a\u0006\b\u008b\u0001\u0010\u008c\u0001R\u0013\u0010\u008d\u0001\u001a\u00020\b¢\u0006\t\n\u0000\u001a\u0005\b\u008e\u0001\u0010\nR\u0016\u0010\u008f\u0001\u001a\u00020\bX\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0090\u0001\u0010\nR\u0019\u0010\u0091\u0001\u001a\b\u0012\u0004\u0012\u00020\b0\u000e¢\u0006\t\n\u0000\u001a\u0005\b\u0092\u0001\u0010\u0010R\u0015\u0010\u0093\u0001\u001a\u0004\u0018\u00010\b¢\u0006\t\n\u0000\u001a\u0005\b\u0094\u0001\u0010\nR\u0016\u0010\u0095\u0001\u001a\u00020$X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0096\u0001\u0010&R\u0013\u0010\u0097\u0001\u001a\u00020\b¢\u0006\t\n\u0000\u001a\u0005\b\u0098\u0001\u0010\nR\u0013\u0010\u0099\u0001\u001a\u00020\b¢\u0006\t\n\u0000\u001a\u0005\b\u009a\u0001\u0010\nR\u0015\u0010\u009b\u0001\u001a\u0004\u0018\u00010\b¢\u0006\t\n\u0000\u001a\u0005\b\u009c\u0001\u0010\nR\u0016\u0010\u009d\u0001\u001a\u00020\bX\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u009e\u0001\u0010\nR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0015\u0010\u009f\u0001\u001a\u0004\u0018\u00010\b¢\u0006\t\n\u0000\u001a\u0005\b \u0001\u0010\nR\u0016\u0010¡\u0001\u001a\u00020\bX\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b¢\u0001\u0010\nR\u0016\u0010£\u0001\u001a\u00020\bX\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b¤\u0001\u0010\nR\u0016\u0010¥\u0001\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0007\u001a\u0005\b¦\u0001\u0010\nR\u0013\u0010§\u0001\u001a\u00020\b¢\u0006\t\n\u0000\u001a\u0005\b¨\u0001\u0010\nR\u0016\u0010©\u0001\u001a\u00020\bX\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\bª\u0001\u0010\nR\u0016\u0010«\u0001\u001a\u00020\bX\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b¬\u0001\u0010\nR\u0016\u0010\u00ad\u0001\u001a\u00020\bX\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b®\u0001\u0010\nR*\u0010¯\u0001\u001a\u000e\u0012\u0004\u0012\u00020K\u0012\u0004\u0012\u00020\b0\u001b8VX\u0096\u0084\u0002¢\u0006\u000e\n\u0005\b±\u0001\u0010N\u001a\u0005\b°\u0001\u0010\u001dR\u0015\u0010²\u0001\u001a\b\u0012\u0004\u0012\u00020\b0PX\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010³\u0001\u001a\u000e\u0012\u0004\u0012\u00020K\u0012\u0004\u0012\u00020\b0\u001b8VX\u0096\u0084\u0002¢\u0006\u000e\n\u0005\bµ\u0001\u0010N\u001a\u0005\b´\u0001\u0010\u001dR\u0015\u0010¶\u0001\u001a\b\u0012\u0004\u0012\u00020\b0PX\u0082\u0004¢\u0006\u0002\n\u0000R\u0013\u0010·\u0001\u001a\u00020\b¢\u0006\t\n\u0000\u001a\u0005\b¸\u0001\u0010\nR\u0013\u0010¹\u0001\u001a\u00020\b¢\u0006\t\n\u0000\u001a\u0005\bº\u0001\u0010\nR\u0013\u0010»\u0001\u001a\u00020$¢\u0006\t\n\u0000\u001a\u0005\b¼\u0001\u0010&R\u001f\u0010½\u0001\u001a\u00020 8FX\u0086\u0084\u0002¢\u0006\u000f\n\u0005\bÀ\u0001\u0010N\u001a\u0006\b¾\u0001\u0010¿\u0001R\u0013\u0010Á\u0001\u001a\u00020$¢\u0006\t\n\u0000\u001a\u0005\bÂ\u0001\u0010&R\u0013\u0010Ã\u0001\u001a\u00020\b¢\u0006\t\n\u0000\u001a\u0005\bÄ\u0001\u0010\nR\u0013\u0010Å\u0001\u001a\u00020\b¢\u0006\t\n\u0000\u001a\u0005\bÆ\u0001\u0010\nR\u000f\u0010Ç\u0001\u001a\u00020$X\u0082\u0004¢\u0006\u0002\n\u0000R \u0010È\u0001\u001a\u00030É\u00018FX\u0086\u0084\u0002¢\u0006\u000f\n\u0005\bÌ\u0001\u0010N\u001a\u0006\bÊ\u0001\u0010Ë\u0001R\u000f\u0010Í\u0001\u001a\u00020\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u0013\u0010Î\u0001\u001a\u00020\b¢\u0006\t\n\u0000\u001a\u0005\bÏ\u0001\u0010\nR\u000f\u0010Ð\u0001\u001a\u00020$X\u0082\u0004¢\u0006\u0002\n\u0000R\u001f\u0010Ñ\u0001\u001a\u00020 8FX\u0086\u0084\u0002¢\u0006\u000f\n\u0005\bÓ\u0001\u0010N\u001a\u0006\bÒ\u0001\u0010¿\u0001R\u0013\u0010Ô\u0001\u001a\u00020\b¢\u0006\t\n\u0000\u001a\u0005\bÕ\u0001\u0010\nR\u0013\u0010Ö\u0001\u001a\u00020\b¢\u0006\t\n\u0000\u001a\u0005\b×\u0001\u0010\n¨\u0006þ\u0001"}, d2 = {"Lorg/jetbrains/kotlin/backend/wasm/WasmSymbols;", "Lorg/jetbrains/kotlin/backend/common/ir/Symbols;", "context", "Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "(Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;Lorg/jetbrains/kotlin/ir/util/SymbolTable;)V", "addAssociatedObject", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "getAddAssociatedObject$backend_wasm", "()Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "anyNtoString", "getAnyNtoString", "arraysCopyInto", "", "getArraysCopyInto", "()Ljava/util/List;", "assertFuncs", "getAssertFuncs", "booleanAnd", "getBooleanAnd", "boxIntrinsic", "getBoxIntrinsic", "builtInsPackage", "Lorg/jetbrains/kotlin/descriptors/PackageViewDescriptor;", "collectionsPackage", "comparisonBuiltInsToWasmIntrinsics", "", "getComparisonBuiltInsToWasmIntrinsics", "()Ljava/util/Map;", "consumeAnyIntoVoid", "consumePrimitiveIntoVoid", "Lorg/jetbrains/kotlin/ir/types/IrType;", "contentHashCode", "contentToString", "continuationClass", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "getContinuationClass", "()Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "coroutineContextGetter", "getCoroutineContextGetter", "coroutineEmptyContinuation", "Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "getCoroutineEmptyContinuation", "()Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "coroutineGetContext", "getCoroutineGetContext", "coroutineImpl", "getCoroutineImpl", "coroutineSuspendedGetter", "getCoroutineSuspendedGetter", "createEnumEntries", "getCreateEnumEntries", "defaultConstructorMarker", "getDefaultConstructorMarker", "eagerInitialization", "getEagerInitialization$backend_wasm", "enumEntries", "getEnumEntries", "enumEntriesIntrinsic", "getEnumEntriesIntrinsic", "enumValueOfIntrinsic", "getEnumValueOfIntrinsic", "enumValuesIntrinsic", "getEnumValuesIntrinsic", "enumsInternalPackage", "equalityFunctions", "getEqualityFunctions", "floatEqualityFunctions", "getFloatEqualityFunctions", "functionAdapter", "getFunctionAdapter", "getContinuation", "getGetContinuation", "getProgressionLastElementByReturnType", "Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;", "getGetProgressionLastElementByReturnType", "getProgressionLastElementByReturnType$delegate", "Lkotlin/Lazy;", "getProgressionLastElementSymbols", "", "initAssociatedObjects", "getInitAssociatedObjects$backend_wasm", "intToLong", "getIntToLong", "isNotFirstWasmExportCall", "isNotFirstWasmExportCall$backend_wasm", "jsRelatedSymbols", "Lorg/jetbrains/kotlin/backend/wasm/WasmSymbols$JsRelatedSymbols;", "getJsRelatedSymbols", "()Lorg/jetbrains/kotlin/backend/wasm/WasmSymbols$JsRelatedSymbols;", "jsRelatedSymbolsIfNonWasi", "kLocalDelegatedMutablePropertyImpl", "getKLocalDelegatedMutablePropertyImpl", "kLocalDelegatedPropertyImpl", "getKLocalDelegatedPropertyImpl", "kMutableProperty0", "getKMutableProperty0", "kMutableProperty0Impl", "getKMutableProperty0Impl", "kMutableProperty1", "getKMutableProperty1", "kMutableProperty1Impl", "getKMutableProperty1Impl", "kMutableProperty2", "getKMutableProperty2", "kMutableProperty2Impl", "getKMutableProperty2Impl", "kProperty0Impl", "getKProperty0Impl", "kProperty1Impl", "getKProperty1Impl", "kProperty2Impl", "getKProperty2Impl", "kTypeStub", "getKTypeStub", "kotlinJsPackage", "kotlinTestPackage", "kotlinTopLevelPackage", "nullableDoubleIeee754Equals", "getNullableDoubleIeee754Equals", "nullableEquals", "getNullableEquals", "nullableFloatIeee754Equals", "getNullableFloatIeee754Equals", "primitiveTypeToCreateTypedArray", "getPrimitiveTypeToCreateTypedArray", "rangeCheck", "getRangeCheck", "refCastNull", "getRefCastNull", "refEq", "getRefEq", "refIsNull", "getRefIsNull", "refTest", "getRefTest", "reflectionSymbols", "Lorg/jetbrains/kotlin/backend/wasm/WasmSymbols$WasmReflectionSymbols;", "getReflectionSymbols$backend_wasm", "()Lorg/jetbrains/kotlin/backend/wasm/WasmSymbols$WasmReflectionSymbols;", "returnArgumentIfItIsKotlinAny", "getReturnArgumentIfItIsKotlinAny", "returnIfSuspended", "getReturnIfSuspended", "startCoroutineUninterceptedOrReturnIntrinsics", "getStartCoroutineUninterceptedOrReturnIntrinsics", "startUnitTests", "getStartUnitTests", "stringBuilder", "getStringBuilder", "stringGetLiteral", "getStringGetLiteral", "stringGetPoolSize", "getStringGetPoolSize", "suiteFun", "getSuiteFun", "suspendCoroutineUninterceptedOrReturn", "getSuspendCoroutineUninterceptedOrReturn", "testFun", "getTestFun", Namer.THROW_ILLEGAL_ARGUMENT_EXCEPTION_FUN_NAME, "getThrowIAE", "throwISE", "getThrowISE", "throwKotlinNothingValueException", "getThrowKotlinNothingValueException", "throwNoBranchMatchedException", "getThrowNoBranchMatchedException", "throwNullPointerException", "getThrowNullPointerException", "throwTypeCastException", "getThrowTypeCastException", "throwUninitializedPropertyAccessException", "getThrowUninitializedPropertyAccessException", "toUIntByExtensionReceiver", "getToUIntByExtensionReceiver", "toUIntByExtensionReceiver$delegate", "toUIntSymbols", "toULongByExtensionReceiver", "getToULongByExtensionReceiver", "toULongByExtensionReceiver$delegate", "toULongSymbols", "unboxIntrinsic", "getUnboxIntrinsic", "unsafeGetScratchRawMemory", "getUnsafeGetScratchRawMemory", "voidClass", "getVoidClass", "voidType", "getVoidType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "voidType$delegate", "wasmAnyRefClass", "getWasmAnyRefClass", "wasmArrayCopy", "getWasmArrayCopy", "wasmArrayNewData0", "getWasmArrayNewData0", "wasmExportClass", "wasmExportConstructor", "Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "getWasmExportConstructor", "()Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "wasmExportConstructor$delegate", "wasmInternalPackage", "wasmIsInterface", "getWasmIsInterface", "wasmStructRefClass", "wasmStructRefType", "getWasmStructRefType", "wasmStructRefType$delegate", "wasmTypeId", "getWasmTypeId", "wasmUnreachable", "getWasmUnreachable", "findClass", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "memberScope", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "name", "Lorg/jetbrains/kotlin/name/Name;", "findContentHashCodeOverload", "arrayType", "findContentToStringOverload", "findFunctions", "Lorg/jetbrains/kotlin/descriptors/SimpleFunctionDescriptor;", "findOverloadForReceiver", "overloadsList", "findProperty", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "findVoidConsumer", ModuleXmlParser.TYPE, "getClass", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "getClass$backend_wasm", "getEnumsFunction", "", "getFunction", "ownerPackage", "getInternalClass", "getInternalFunction", "getIrClass", "getKFunctionType", SchemaSymbols.ATTVAL_LIST, "getProperty", "getProperty$backend_wasm", "maybeGetFunction", "wasmPrimitiveTypeName", "classifier", "JsInteropAdapters", "JsRelatedSymbols", "WasmReflectionSymbols", "backend.wasm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WasmSymbols extends Symbols {
    private final IrSimpleFunctionSymbol addAssociatedObject;
    private final IrSimpleFunctionSymbol anyNtoString;
    private final List<IrSimpleFunctionSymbol> arraysCopyInto;
    private final List<IrSimpleFunctionSymbol> assertFuncs;
    private final IrSimpleFunctionSymbol booleanAnd;
    private final IrSimpleFunctionSymbol boxIntrinsic;
    private final PackageViewDescriptor builtInsPackage;
    private final PackageViewDescriptor collectionsPackage;
    private final Map<IrSimpleFunctionSymbol, IrSimpleFunctionSymbol> comparisonBuiltInsToWasmIntrinsics;
    private final IrSimpleFunctionSymbol consumeAnyIntoVoid;
    private final Map<IrType, IrSimpleFunctionSymbol> consumePrimitiveIntoVoid;
    private final List<IrSimpleFunctionSymbol> contentHashCode;
    private final List<IrSimpleFunctionSymbol> contentToString;
    private final WasmBackendContext context;
    private final IrClassSymbol continuationClass;
    private final IrSimpleFunctionSymbol coroutineContextGetter;
    private final IrPropertySymbol coroutineEmptyContinuation;
    private final IrSimpleFunctionSymbol coroutineGetContext;
    private final IrClassSymbol coroutineImpl;
    private final IrSimpleFunctionSymbol coroutineSuspendedGetter;
    private final IrSimpleFunctionSymbol createEnumEntries;
    private final IrClassSymbol defaultConstructorMarker;
    private final IrClassSymbol eagerInitialization;
    private final IrClassSymbol enumEntries;
    private final IrSimpleFunctionSymbol enumEntriesIntrinsic;
    private final IrSimpleFunctionSymbol enumValueOfIntrinsic;
    private final IrSimpleFunctionSymbol enumValuesIntrinsic;
    private final PackageViewDescriptor enumsInternalPackage;
    private final Map<IrType, IrSimpleFunctionSymbol> equalityFunctions;
    private final Map<IrType, IrSimpleFunctionSymbol> floatEqualityFunctions;
    private final IrSimpleFunctionSymbol getContinuation;

    /* JADX INFO: renamed from: getProgressionLastElementByReturnType$delegate, reason: from kotlin metadata */
    private final Lazy getProgressionLastElementByReturnType;
    private final Iterable<IrSimpleFunctionSymbol> getProgressionLastElementSymbols;
    private final IrSimpleFunctionSymbol initAssociatedObjects;
    private final IrSimpleFunctionSymbol intToLong;
    private final IrPropertySymbol isNotFirstWasmExportCall;
    private final JsRelatedSymbols jsRelatedSymbolsIfNonWasi;
    private final IrClassSymbol kLocalDelegatedMutablePropertyImpl;
    private final IrClassSymbol kLocalDelegatedPropertyImpl;
    private final IrClassSymbol kMutableProperty0;
    private final IrClassSymbol kMutableProperty0Impl;
    private final IrClassSymbol kMutableProperty1;
    private final IrClassSymbol kMutableProperty1Impl;
    private final IrClassSymbol kMutableProperty2;
    private final IrClassSymbol kMutableProperty2Impl;
    private final IrClassSymbol kProperty0Impl;
    private final IrClassSymbol kProperty1Impl;
    private final IrClassSymbol kProperty2Impl;
    private final IrSimpleFunctionSymbol kTypeStub;
    private final PackageViewDescriptor kotlinJsPackage;
    private final PackageViewDescriptor kotlinTestPackage;
    private final PackageViewDescriptor kotlinTopLevelPackage;
    private final IrSimpleFunctionSymbol nullableDoubleIeee754Equals;
    private final IrSimpleFunctionSymbol nullableEquals;
    private final IrSimpleFunctionSymbol nullableFloatIeee754Equals;
    private final Map<IrClassSymbol, IrSimpleFunctionSymbol> primitiveTypeToCreateTypedArray;
    private final IrSimpleFunctionSymbol rangeCheck;
    private final IrSimpleFunctionSymbol refCastNull;
    private final IrSimpleFunctionSymbol refEq;
    private final IrSimpleFunctionSymbol refIsNull;
    private final IrSimpleFunctionSymbol refTest;
    private final WasmReflectionSymbols reflectionSymbols;
    private final IrSimpleFunctionSymbol returnArgumentIfItIsKotlinAny;
    private final IrSimpleFunctionSymbol returnIfSuspended;
    private final List<IrSimpleFunctionSymbol> startCoroutineUninterceptedOrReturnIntrinsics;
    private final IrSimpleFunctionSymbol startUnitTests;
    private final IrClassSymbol stringBuilder;
    private final IrSimpleFunctionSymbol stringGetLiteral;
    private final IrSimpleFunctionSymbol stringGetPoolSize;
    private final IrSimpleFunctionSymbol suiteFun;
    private final IrSimpleFunctionSymbol suspendCoroutineUninterceptedOrReturn;
    private final SymbolTable symbolTable;
    private final IrSimpleFunctionSymbol testFun;
    private final IrSimpleFunctionSymbol throwIAE;
    private final IrSimpleFunctionSymbol throwISE;
    private final IrSimpleFunctionSymbol throwNoBranchMatchedException;
    private final IrSimpleFunctionSymbol throwNullPointerException;
    private final IrSimpleFunctionSymbol throwTypeCastException;
    private final IrSimpleFunctionSymbol throwUninitializedPropertyAccessException;

    /* JADX INFO: renamed from: toUIntByExtensionReceiver$delegate, reason: from kotlin metadata */
    private final Lazy toUIntByExtensionReceiver;
    private final Iterable<IrSimpleFunctionSymbol> toUIntSymbols;

    /* JADX INFO: renamed from: toULongByExtensionReceiver$delegate, reason: from kotlin metadata */
    private final Lazy toULongByExtensionReceiver;
    private final Iterable<IrSimpleFunctionSymbol> toULongSymbols;
    private final IrSimpleFunctionSymbol unboxIntrinsic;
    private final IrSimpleFunctionSymbol unsafeGetScratchRawMemory;
    private final IrClassSymbol voidClass;

    /* JADX INFO: renamed from: voidType$delegate, reason: from kotlin metadata */
    private final Lazy voidType;
    private final IrClassSymbol wasmAnyRefClass;
    private final IrSimpleFunctionSymbol wasmArrayCopy;
    private final IrSimpleFunctionSymbol wasmArrayNewData0;
    private final IrClassSymbol wasmExportClass;

    /* JADX INFO: renamed from: wasmExportConstructor$delegate, reason: from kotlin metadata */
    private final Lazy wasmExportConstructor;
    private final PackageViewDescriptor wasmInternalPackage;
    private final IrSimpleFunctionSymbol wasmIsInterface;
    private final IrClassSymbol wasmStructRefClass;

    /* JADX INFO: renamed from: wasmStructRefType$delegate, reason: from kotlin metadata */
    private final Lazy wasmStructRefType;
    private final IrSimpleFunctionSymbol wasmTypeId;
    private final IrSimpleFunctionSymbol wasmUnreachable;

    public final JsRelatedSymbols getJsRelatedSymbols() {
        JsRelatedSymbols jsRelatedSymbols = this.jsRelatedSymbolsIfNonWasi;
        if (jsRelatedSymbols != null) {
            return jsRelatedSymbols;
        }
        throw new IllegalStateException("Cannot access to js related std in wasi mode".toString());
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WasmSymbols(WasmBackendContext context, SymbolTable symbolTable) {
        Object next;
        KotlinType type;
        super(context.getIrBuiltIns(), symbolTable);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(symbolTable, "symbolTable");
        this.context = context;
        this.symbolTable = symbolTable;
        this.kotlinTopLevelPackage = context.getModule().getPackage(new FqName(IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME));
        PackageViewDescriptor packageViewDescriptor = context.getModule().getPackage(new FqName("kotlin.enums"));
        this.enumsInternalPackage = packageViewDescriptor;
        this.wasmInternalPackage = context.getModule().getPackage(new FqName("kotlin.wasm.internal"));
        this.kotlinJsPackage = context.getModule().getPackage(new FqName("kotlin.js"));
        this.collectionsPackage = context.getModule().getPackage(StandardNames.COLLECTIONS_PACKAGE_FQ_NAME);
        this.builtInsPackage = context.getModule().getPackage(StandardNames.BUILT_INS_PACKAGE_FQ_NAME);
        this.kotlinTestPackage = context.getModule().getPackage(new FqName("kotlin.test"));
        this.reflectionSymbols = new WasmReflectionSymbols();
        this.eagerInitialization = getIrClass(new FqName("kotlin.EagerInitialization"));
        DescriptorSymbolTableExtension descriptorExtension = symbolTable.getDescriptorExtension();
        FqName fqNameFromSegments = FqName.fromSegments(CollectionsKt.listOf((Object[]) new String[]{IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME, "wasm", "internal", "isNotFirstWasmExportCall"}));
        Intrinsics.checkNotNullExpressionValue(fqNameFromSegments, "fromSegments(...)");
        this.isNotFirstWasmExportCall = descriptorExtension.referenceProperty(getProperty$backend_wasm(fqNameFromSegments));
        this.initAssociatedObjects = getInternalFunction("initAssociatedObjects");
        this.addAssociatedObject = getInternalFunction("addAssociatedObject");
        this.throwNullPointerException = getInternalFunction("THROW_NPE");
        this.throwISE = getInternalFunction(BuiltInOperatorNames.THROW_ISE);
        this.throwTypeCastException = getInternalFunction(BuiltInOperatorNames.THROW_CCE);
        this.throwIAE = getInternalFunction("THROW_IAE");
        this.throwNoBranchMatchedException = getInternalFunction("throwNoBranchMatchedException");
        this.throwUninitializedPropertyAccessException = getInternalFunction("throwUninitializedPropertyAccessException");
        this.defaultConstructorMarker = getIrClass(new FqName("kotlin.wasm.internal.DefaultConstructorMarker"));
        this.stringBuilder = getIrClass(new FqName("kotlin.text.StringBuilder"));
        this.coroutineImpl = context.getCoroutineSymbols().getCoroutineImpl();
        this.coroutineSuspendedGetter = context.getCoroutineSymbols().getCoroutineSuspendedGetter();
        this.getContinuation = getInternalFunction("getContinuation");
        this.continuationClass = context.getCoroutineSymbols().getContinuationClass();
        DescriptorSymbolTableExtension descriptorExtension2 = symbolTable.getDescriptorExtension();
        PropertyGetterDescriptor getter = context.getCoroutineSymbols().getCoroutineContextProperty().getGetter();
        Intrinsics.checkNotNull(getter);
        this.coroutineContextGetter = descriptorExtension2.referenceSimpleFunction(getter);
        this.suspendCoroutineUninterceptedOrReturn = getInternalFunction("suspendCoroutineUninterceptedOrReturn");
        this.coroutineGetContext = getInternalFunction("getCoroutineContext");
        this.returnIfSuspended = getInternalFunction("returnIfSuspended");
        FqName fqNameFromSegments2 = FqName.fromSegments(CollectionsKt.listOf((Object[]) new String[]{IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME, "enums", "EnumEntries"}));
        Intrinsics.checkNotNullExpressionValue(fqNameFromSegments2, "fromSegments(...)");
        this.enumEntries = getIrClass(fqNameFromSegments2);
        MemberScope memberScope = packageViewDescriptor.getMemberScope();
        Name nameIdentifier = Name.identifier("enumEntries");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        Iterator<T> it = findFunctions(memberScope, nameIdentifier).iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            List<ValueParameterDescriptor> valueParameters = ((SimpleFunctionDescriptor) next).getValueParameters();
            Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
            ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) CollectionsKt.firstOrNull((List) valueParameters);
            if (valueParameterDescriptor != null && (type = valueParameterDescriptor.getType()) != null) {
                Intrinsics.checkNotNull(type);
                if (!FunctionTypesKt.isFunctionType(type)) {
                    break;
                }
            }
        }
        SimpleFunctionDescriptor simpleFunctionDescriptor = (SimpleFunctionDescriptor) next;
        DescriptorSymbolTableExtension descriptorExtension3 = this.symbolTable.getDescriptorExtension();
        Intrinsics.checkNotNull(simpleFunctionDescriptor);
        this.createEnumEntries = descriptorExtension3.referenceSimpleFunction(simpleFunctionDescriptor);
        this.enumValueOfIntrinsic = getInternalFunction("enumValueOfIntrinsic");
        this.enumValuesIntrinsic = getInternalFunction("enumValuesIntrinsic");
        this.enumEntriesIntrinsic = getEnumsFunction("enumEntriesIntrinsic");
        DescriptorSymbolTableExtension descriptorExtension4 = this.symbolTable.getDescriptorExtension();
        FqName fqNameFromSegments3 = FqName.fromSegments(CollectionsKt.listOf((Object[]) new String[]{IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME, "wasm", "internal", "EmptyContinuation"}));
        Intrinsics.checkNotNullExpressionValue(fqNameFromSegments3, "fromSegments(...)");
        this.coroutineEmptyContinuation = descriptorExtension4.referenceProperty(getProperty$backend_wasm(fqNameFromSegments3));
        this.wasmUnreachable = getInternalFunction("wasm_unreachable");
        this.voidClass = getIrClass(new FqName("kotlin.wasm.internal.Void"));
        this.voidType = LazyKt.lazy(new Function0<IrType>() { // from class: org.jetbrains.kotlin.backend.wasm.WasmSymbols$voidType$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrType invoke() {
                return IrTypesKt.getDefaultType(this.this$0.getVoidClass());
            }
        });
        this.consumeAnyIntoVoid = getInternalFunction("consumeAnyIntoVoid");
        this.consumePrimitiveIntoVoid = MapsKt.mapOf(TuplesKt.to(this.context.getIrBuiltIns().getBooleanType(), getInternalFunction("consumeBooleanIntoVoid")), TuplesKt.to(this.context.getIrBuiltIns().getByteType(), getInternalFunction("consumeByteIntoVoid")), TuplesKt.to(this.context.getIrBuiltIns().getShortType(), getInternalFunction("consumeShortIntoVoid")), TuplesKt.to(this.context.getIrBuiltIns().getCharType(), getInternalFunction("consumeCharIntoVoid")), TuplesKt.to(this.context.getIrBuiltIns().getIntType(), getInternalFunction("consumeIntIntoVoid")), TuplesKt.to(this.context.getIrBuiltIns().getLongType(), getInternalFunction("consumeLongIntoVoid")), TuplesKt.to(this.context.getIrBuiltIns().getFloatType(), getInternalFunction("consumeFloatIntoVoid")), TuplesKt.to(this.context.getIrBuiltIns().getDoubleType(), getInternalFunction("consumeDoubleIntoVoid")));
        this.equalityFunctions = MapsKt.mapOf(TuplesKt.to(this.context.getIrBuiltIns().getBooleanType(), getInternalFunction("wasm_i32_eq")), TuplesKt.to(this.context.getIrBuiltIns().getByteType(), getInternalFunction("wasm_i32_eq")), TuplesKt.to(this.context.getIrBuiltIns().getShortType(), getInternalFunction("wasm_i32_eq")), TuplesKt.to(this.context.getIrBuiltIns().getCharType(), getInternalFunction("wasm_i32_eq")), TuplesKt.to(this.context.getIrBuiltIns().getIntType(), getInternalFunction("wasm_i32_eq")), TuplesKt.to(this.context.getIrBuiltIns().getLongType(), getInternalFunction("wasm_i64_eq")));
        this.floatEqualityFunctions = MapsKt.mapOf(TuplesKt.to(this.context.getIrBuiltIns().getFloatType(), getInternalFunction("wasm_f32_eq")), TuplesKt.to(this.context.getIrBuiltIns().getDoubleType(), getInternalFunction("wasm_f64_eq")));
        IrBuiltIns irBuiltIns = this.context.getIrBuiltIns();
        List<Pair> listListOf = CollectionsKt.listOf((Object[]) new Pair[]{TuplesKt.to(irBuiltIns.getLessFunByOperandType(), "lt"), TuplesKt.to(irBuiltIns.getLessOrEqualFunByOperandType(), "le"), TuplesKt.to(irBuiltIns.getGreaterOrEqualFunByOperandType(), "ge"), TuplesKt.to(irBuiltIns.getGreaterFunByOperandType(), "gt")});
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listListOf, 10));
        for (Pair pair : listListOf) {
            Map map = (Map) pair.component1();
            String str = (String) pair.component2();
            ArrayList arrayList2 = new ArrayList(map.size());
            for (Map.Entry entry : map.entrySet()) {
                IrClassifierSymbol irClassifierSymbol = (IrClassifierSymbol) entry.getKey();
                IrSimpleFunctionSymbol irSimpleFunctionSymbol = (IrSimpleFunctionSymbol) entry.getValue();
                String strWasmPrimitiveTypeName = wasmPrimitiveTypeName(irClassifierSymbol);
                arrayList2.add(TuplesKt.to(irSimpleFunctionSymbol, getInternalFunction("wasm_" + strWasmPrimitiveTypeName + '_' + str + ((Intrinsics.areEqual(strWasmPrimitiveTypeName, "i32") || Intrinsics.areEqual(strWasmPrimitiveTypeName, "i64")) ? "_s" : ""))));
            }
            arrayList.add(arrayList2);
        }
        this.comparisonBuiltInsToWasmIntrinsics = MapsKt.toMap(CollectionsKt.flatten(arrayList));
        this.booleanAnd = getInternalFunction("wasm_i32_and");
        this.refEq = getInternalFunction("wasm_ref_eq");
        this.refIsNull = getInternalFunction("wasm_ref_is_null");
        this.refTest = getInternalFunction("wasm_ref_test");
        this.refCastNull = getInternalFunction("wasm_ref_cast_null");
        this.wasmArrayCopy = getInternalFunction("wasm_array_copy");
        this.wasmArrayNewData0 = getInternalFunction("array_new_data0");
        this.primitiveTypeToCreateTypedArray = MapsKt.mapOf(TuplesKt.to(this.context.getIrBuiltIns().getArrayClass(), getFunction("createAnyArray", this.kotlinTopLevelPackage)), TuplesKt.to(this.context.getIrBuiltIns().getBooleanArray(), getFunction("createBooleanArray", this.kotlinTopLevelPackage)), TuplesKt.to(this.context.getIrBuiltIns().getByteArray(), getFunction("createByteArray", this.kotlinTopLevelPackage)), TuplesKt.to(this.context.getIrBuiltIns().getShortArray(), getFunction("createShortArray", this.kotlinTopLevelPackage)), TuplesKt.to(this.context.getIrBuiltIns().getCharArray(), getFunction("createCharArray", this.kotlinTopLevelPackage)), TuplesKt.to(this.context.getIrBuiltIns().getIntArray(), getFunction("createIntArray", this.kotlinTopLevelPackage)), TuplesKt.to(this.context.getIrBuiltIns().getLongArray(), getFunction("createLongArray", this.kotlinTopLevelPackage)), TuplesKt.to(this.context.getIrBuiltIns().getFloatArray(), getFunction("createFloatArray", this.kotlinTopLevelPackage)), TuplesKt.to(this.context.getIrBuiltIns().getDoubleArray(), getFunction("createDoubleArray", this.kotlinTopLevelPackage)));
        this.intToLong = getInternalFunction("wasm_i64_extend_i32_s");
        this.rangeCheck = getInternalFunction("rangeCheck");
        MemberScope memberScope2 = this.kotlinTopLevelPackage.getMemberScope();
        Name nameIdentifier2 = Name.identifier(PsiKeyword.ASSERT);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
        List<SimpleFunctionDescriptor> listFindFunctions = findFunctions(memberScope2, nameIdentifier2);
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(listFindFunctions, 10));
        Iterator<T> it2 = listFindFunctions.iterator();
        while (it2.hasNext()) {
            arrayList3.add(this.symbolTable.getDescriptorExtension().referenceSimpleFunction((SimpleFunctionDescriptor) it2.next()));
        }
        this.assertFuncs = arrayList3;
        this.boxIntrinsic = getInternalFunction("boxIntrinsic");
        this.unboxIntrinsic = getInternalFunction("unboxIntrinsic");
        this.stringGetLiteral = getFunction("stringLiteral", this.builtInsPackage);
        this.stringGetPoolSize = getInternalFunction("stringGetPoolSize");
        this.testFun = maybeGetFunction("test", this.kotlinTestPackage);
        this.suiteFun = maybeGetFunction("suite", this.kotlinTestPackage);
        this.startUnitTests = maybeGetFunction("startUnitTests", this.kotlinTestPackage);
        this.wasmTypeId = getInternalFunction("wasmTypeId");
        this.wasmIsInterface = getInternalFunction("wasmIsInterface");
        this.nullableEquals = getInternalFunction("nullableEquals");
        this.anyNtoString = getInternalFunction("anyNtoString");
        this.nullableFloatIeee754Equals = getInternalFunction("nullableFloatIeee754Equals");
        this.nullableDoubleIeee754Equals = getInternalFunction("nullableDoubleIeee754Equals");
        this.unsafeGetScratchRawMemory = getInternalFunction("unsafeGetScratchRawMemory");
        this.returnArgumentIfItIsKotlinAny = getInternalFunction("returnArgumentIfItIsKotlinAny");
        IntRange intRange = new IntRange(0, 2);
        ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(intRange, 10));
        Iterator<Integer> it3 = intRange.iterator();
        while (it3.hasNext()) {
            arrayList4.add(getInternalFunction("startCoroutineUninterceptedOrReturnIntrinsic" + ((IntIterator) it3).nextInt()));
        }
        this.startCoroutineUninterceptedOrReturnIntrinsics = arrayList4;
        this.kLocalDelegatedPropertyImpl = getInternalClass("KLocalDelegatedPropertyImpl");
        this.kLocalDelegatedMutablePropertyImpl = getInternalClass("KLocalDelegatedMutablePropertyImpl");
        this.kProperty0Impl = getInternalClass("KProperty0Impl");
        this.kProperty1Impl = getInternalClass("KProperty1Impl");
        this.kProperty2Impl = getInternalClass("KProperty2Impl");
        this.kMutableProperty0Impl = getInternalClass("KMutableProperty0Impl");
        this.kMutableProperty1Impl = getInternalClass("KMutableProperty1Impl");
        this.kMutableProperty2Impl = getInternalClass("KMutableProperty2Impl");
        this.kMutableProperty0 = getIrClass(new FqName("kotlin.reflect.KMutableProperty0"));
        this.kMutableProperty1 = getIrClass(new FqName("kotlin.reflect.KMutableProperty1"));
        this.kMutableProperty2 = getIrClass(new FqName("kotlin.reflect.KMutableProperty2"));
        this.kTypeStub = getInternalFunction("kTypeStub");
        MemberScope memberScope3 = this.collectionsPackage.getMemberScope();
        Name nameIdentifier3 = Name.identifier("copyInto");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier3, "identifier(...)");
        List<SimpleFunctionDescriptor> listFindFunctions2 = findFunctions(memberScope3, nameIdentifier3);
        ArrayList arrayList5 = new ArrayList(CollectionsKt.collectionSizeOrDefault(listFindFunctions2, 10));
        Iterator<T> it4 = listFindFunctions2.iterator();
        while (it4.hasNext()) {
            arrayList5.add(this.symbolTable.getDescriptorExtension().referenceSimpleFunction((SimpleFunctionDescriptor) it4.next()));
        }
        this.arraysCopyInto = arrayList5;
        MemberScope memberScope4 = this.collectionsPackage.getMemberScope();
        Name nameIdentifier4 = Name.identifier("contentToString");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier4, "identifier(...)");
        List<SimpleFunctionDescriptor> listFindFunctions3 = findFunctions(memberScope4, nameIdentifier4);
        ArrayList arrayList6 = new ArrayList(CollectionsKt.collectionSizeOrDefault(listFindFunctions3, 10));
        Iterator<T> it5 = listFindFunctions3.iterator();
        while (it5.hasNext()) {
            arrayList6.add(this.symbolTable.getDescriptorExtension().referenceSimpleFunction((SimpleFunctionDescriptor) it5.next()));
        }
        this.contentToString = arrayList6;
        MemberScope memberScope5 = this.collectionsPackage.getMemberScope();
        Name nameIdentifier5 = Name.identifier("contentHashCode");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier5, "identifier(...)");
        List<SimpleFunctionDescriptor> listFindFunctions4 = findFunctions(memberScope5, nameIdentifier5);
        ArrayList arrayList7 = new ArrayList(CollectionsKt.collectionSizeOrDefault(listFindFunctions4, 10));
        Iterator<T> it6 = listFindFunctions4.iterator();
        while (it6.hasNext()) {
            arrayList7.add(this.symbolTable.getDescriptorExtension().referenceSimpleFunction((SimpleFunctionDescriptor) it6.next()));
        }
        this.contentHashCode = arrayList7;
        IrBuiltIns irBuiltIns2 = getIrBuiltIns();
        Name nameIdentifier6 = Name.identifier("getProgressionLastElement");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier6, "identifier(...)");
        this.getProgressionLastElementSymbols = irBuiltIns2.findFunctions(nameIdentifier6, IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME, "internal");
        this.getProgressionLastElementByReturnType = LazyKt.lazy(new Function0<Map<IrClassifierSymbol, ? extends IrSimpleFunctionSymbol>>() { // from class: org.jetbrains.kotlin.backend.wasm.WasmSymbols$getProgressionLastElementByReturnType$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Map<IrClassifierSymbol, ? extends IrSimpleFunctionSymbol> invoke() {
                Iterable iterable = this.this$0.getProgressionLastElementSymbols;
                LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(iterable, 10)), 16));
                for (Object obj : iterable) {
                    linkedHashMap.put(IrTypesKt.getClassifierOrFail(((IrSimpleFunctionSymbol) obj).getOwner().getReturnType()), obj);
                }
                return linkedHashMap;
            }
        });
        IrBuiltIns irBuiltIns3 = getIrBuiltIns();
        Name nameIdentifier7 = Name.identifier(Namer.UINT_FROM_INT);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier7, "identifier(...)");
        this.toUIntSymbols = irBuiltIns3.findFunctions(nameIdentifier7, IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME);
        this.toUIntByExtensionReceiver = LazyKt.lazy(new Function0<Map<IrClassifierSymbol, ? extends IrSimpleFunctionSymbol>>() { // from class: org.jetbrains.kotlin.backend.wasm.WasmSymbols$toUIntByExtensionReceiver$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Map<IrClassifierSymbol, ? extends IrSimpleFunctionSymbol> invoke() {
                IrType type2;
                IrClassifierSymbol classifierOrFail;
                Iterable iterable = this.this$0.toUIntSymbols;
                LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(iterable, 10)), 16));
                for (Object obj : iterable) {
                    IrSimpleFunctionSymbol irSimpleFunctionSymbol2 = (IrSimpleFunctionSymbol) obj;
                    IrValueParameter extensionReceiverParameter = irSimpleFunctionSymbol2.getOwner().getExtensionReceiverParameter();
                    if (extensionReceiverParameter == null || (type2 = extensionReceiverParameter.getType()) == null || (classifierOrFail = IrTypesKt.getClassifierOrFail(type2)) == null) {
                        throw new IllegalStateException(("Expected extension receiver for " + RenderIrElementKt.render$default(irSimpleFunctionSymbol2.getOwner(), (DumpIrTreeOptions) null, 1, (Object) null)).toString());
                    }
                    linkedHashMap.put(classifierOrFail, obj);
                }
                return linkedHashMap;
            }
        });
        IrBuiltIns irBuiltIns4 = getIrBuiltIns();
        Name nameIdentifier8 = Name.identifier("toULong");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier8, "identifier(...)");
        this.toULongSymbols = irBuiltIns4.findFunctions(nameIdentifier8, IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME);
        this.toULongByExtensionReceiver = LazyKt.lazy(new Function0<Map<IrClassifierSymbol, ? extends IrSimpleFunctionSymbol>>() { // from class: org.jetbrains.kotlin.backend.wasm.WasmSymbols$toULongByExtensionReceiver$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Map<IrClassifierSymbol, ? extends IrSimpleFunctionSymbol> invoke() {
                IrType type2;
                IrClassifierSymbol classifierOrFail;
                Iterable iterable = this.this$0.toULongSymbols;
                LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(iterable, 10)), 16));
                for (Object obj : iterable) {
                    IrSimpleFunctionSymbol irSimpleFunctionSymbol2 = (IrSimpleFunctionSymbol) obj;
                    IrValueParameter extensionReceiverParameter = irSimpleFunctionSymbol2.getOwner().getExtensionReceiverParameter();
                    if (extensionReceiverParameter == null || (type2 = extensionReceiverParameter.getType()) == null || (classifierOrFail = IrTypesKt.getClassifierOrFail(type2)) == null) {
                        throw new IllegalStateException(("Expected extension receiver for " + RenderIrElementKt.render$default(irSimpleFunctionSymbol2.getOwner(), (DumpIrTreeOptions) null, 1, (Object) null)).toString());
                    }
                    linkedHashMap.put(classifierOrFail, obj);
                }
                return linkedHashMap;
            }
        });
        this.wasmStructRefClass = getIrClass(new FqName("kotlin.wasm.internal.reftypes.structref"));
        this.wasmStructRefType = LazyKt.lazy(new Function0<IrType>() { // from class: org.jetbrains.kotlin.backend.wasm.WasmSymbols$wasmStructRefType$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrType invoke() {
                return IrTypesKt.getDefaultType(this.this$0.wasmStructRefClass);
            }
        });
        this.wasmAnyRefClass = getIrClass(new FqName("kotlin.wasm.internal.reftypes.anyref"));
        this.wasmExportClass = getIrClass(new FqName("kotlin.wasm.WasmExport"));
        this.wasmExportConstructor = LazyKt.lazy(new Function0<IrConstructorSymbol>() { // from class: org.jetbrains.kotlin.backend.wasm.WasmSymbols$wasmExportConstructor$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrConstructorSymbol invoke() {
                return (IrConstructorSymbol) SequencesKt.single(IrUtilsKt.getConstructors(this.this$0.wasmExportClass));
            }
        });
        this.jsRelatedSymbolsIfNonWasi = this.context.getConfiguration().get(JSConfigurationKeys.WASM_TARGET, WasmTarget.JS) == WasmTarget.JS ? new JsRelatedSymbols() : null;
    }

    /* JADX INFO: compiled from: WasmSymbols.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0017\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0080\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006R\u0014\u0010\t\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u0006R\u0014\u0010\u000b\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0006R\u0014\u0010\r\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0006R\u0014\u0010\u000f\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0006R\u0014\u0010\u0011\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0006R\u0014\u0010\u0013\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0006R\u0014\u0010\u0015\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0006R\u0014\u0010\u0017\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0006R\u0011\u0010\u0019\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0006R\u0014\u0010\u001b\u001a\u00020\u001cX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0014\u0010\u001f\u001a\u00020\u001cX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b \u0010\u001eR\u0011\u0010!\u001a\u00020\u001c¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u001e¨\u0006#"}, d2 = {"Lorg/jetbrains/kotlin/backend/wasm/WasmSymbols$WasmReflectionSymbols;", "Lorg/jetbrains/kotlin/ir/backend/js/ReflectionSymbols;", "(Lorg/jetbrains/kotlin/backend/wasm/WasmSymbols;)V", Namer.CREATE_CONTRAVARIANT_KTYPE_PROJECTION, "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "getCreateContravariantKTypeProjection", "()Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", Namer.CREATE_COVARIANT_KTYPE_PROJECTION, "getCreateCovariantKTypeProjection", Namer.CREATE_DYNAMIC_KTYPE, "getCreateDynamicKType", Namer.CREATE_INVARIANT_KTYPE_PROJECTION, "getCreateInvariantKTypeProjection", Namer.CREATE_KTYPE, "getCreateKType", Namer.CREATE_KTYPE_PARAMETER, "getCreateKTypeParameter", "getClassData", "getGetClassData", Namer.GET_KCLASS, "getGetKClass", Namer.GET_KCLASS_FROM_EXPRESSION, "getGetKClassFromExpression", Namer.GET_START_KTYPE_PROJECTION, "getGetStarKTypeProjection", "getTypeInfoTypeDataByPtr", "getGetTypeInfoTypeDataByPtr", "kTypeClass", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "getKTypeClass", "()Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "primitiveClassesObject", "getPrimitiveClassesObject", "wasmTypeInfoData", "getWasmTypeInfoData", "backend.wasm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public final class WasmReflectionSymbols implements ReflectionSymbols {
        private final IrSimpleFunctionSymbol createContravariantKTypeProjection;
        private final IrSimpleFunctionSymbol createCovariantKTypeProjection;
        private final IrSimpleFunctionSymbol createInvariantKTypeProjection;
        private final IrSimpleFunctionSymbol createKType;
        private final IrSimpleFunctionSymbol createKTypeParameter;
        private final IrSimpleFunctionSymbol getClassData;
        private final IrSimpleFunctionSymbol getKClass;
        private final IrSimpleFunctionSymbol getKClassFromExpression;
        private final IrSimpleFunctionSymbol getStarKTypeProjection;
        private final IrSimpleFunctionSymbol getTypeInfoTypeDataByPtr;
        private final IrClassSymbol kTypeClass;
        private final IrClassSymbol primitiveClassesObject;
        private final IrClassSymbol wasmTypeInfoData;

        public WasmReflectionSymbols() {
            this.createKType = WasmSymbols.this.getInternalFunction(Namer.CREATE_KTYPE);
            this.getClassData = WasmSymbols.this.getInternalFunction("wasmGetTypeInfoData");
            this.getKClass = WasmSymbols.this.getInternalFunction(Namer.GET_KCLASS);
            this.getKClassFromExpression = WasmSymbols.this.getInternalFunction(Namer.GET_KCLASS_FROM_EXPRESSION);
            this.createKTypeParameter = WasmSymbols.this.getInternalFunction(Namer.CREATE_KTYPE_PARAMETER);
            this.getStarKTypeProjection = WasmSymbols.this.getInternalFunction(Namer.GET_START_KTYPE_PROJECTION);
            this.createCovariantKTypeProjection = WasmSymbols.this.getInternalFunction(Namer.CREATE_COVARIANT_KTYPE_PROJECTION);
            this.createInvariantKTypeProjection = WasmSymbols.this.getInternalFunction(Namer.CREATE_INVARIANT_KTYPE_PROJECTION);
            this.createContravariantKTypeProjection = WasmSymbols.this.getInternalFunction(Namer.CREATE_CONTRAVARIANT_KTYPE_PROJECTION);
            this.primitiveClassesObject = WasmSymbols.this.getInternalClass("PrimitiveClasses");
            this.kTypeClass = WasmSymbols.this.getIrClass(new FqName("kotlin.reflect.KClass"));
            this.getTypeInfoTypeDataByPtr = WasmSymbols.this.getInternalFunction("getTypeInfoTypeDataByPtr");
            this.wasmTypeInfoData = WasmSymbols.this.getInternalClass("TypeInfoData");
        }

        @Override // org.jetbrains.kotlin.ir.backend.js.ReflectionSymbols
        public IrSimpleFunctionSymbol getCreateKType() {
            return this.createKType;
        }

        @Override // org.jetbrains.kotlin.ir.backend.js.ReflectionSymbols
        public IrSimpleFunctionSymbol getGetClassData() {
            return this.getClassData;
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
        public IrSimpleFunctionSymbol getCreateDynamicKType() {
            throw new IllegalStateException("Dynamic type is not supported by WASM".toString());
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
        public IrClassSymbol getPrimitiveClassesObject() {
            return this.primitiveClassesObject;
        }

        @Override // org.jetbrains.kotlin.ir.backend.js.ReflectionSymbols
        public IrClassSymbol getKTypeClass() {
            return this.kTypeClass;
        }

        public final IrSimpleFunctionSymbol getGetTypeInfoTypeDataByPtr() {
            return this.getTypeInfoTypeDataByPtr;
        }

        public final IrClassSymbol getWasmTypeInfoData() {
            return this.wasmTypeInfoData;
        }
    }

    /* JADX INFO: renamed from: getReflectionSymbols$backend_wasm, reason: from getter */
    public final WasmReflectionSymbols getReflectionSymbols() {
        return this.reflectionSymbols;
    }

    /* JADX INFO: renamed from: getEagerInitialization$backend_wasm, reason: from getter */
    public final IrClassSymbol getEagerInitialization() {
        return this.eagerInitialization;
    }

    /* JADX INFO: renamed from: isNotFirstWasmExportCall$backend_wasm, reason: from getter */
    public final IrPropertySymbol getIsNotFirstWasmExportCall() {
        return this.isNotFirstWasmExportCall;
    }

    /* JADX INFO: renamed from: getInitAssociatedObjects$backend_wasm, reason: from getter */
    public final IrSimpleFunctionSymbol getInitAssociatedObjects() {
        return this.initAssociatedObjects;
    }

    /* JADX INFO: renamed from: getAddAssociatedObject$backend_wasm, reason: from getter */
    public final IrSimpleFunctionSymbol getAddAssociatedObject() {
        return this.addAssociatedObject;
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.Symbols
    public IrSimpleFunctionSymbol getThrowNullPointerException() {
        return this.throwNullPointerException;
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.Symbols
    public IrSimpleFunctionSymbol getThrowISE() {
        return this.throwISE;
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.Symbols
    public IrSimpleFunctionSymbol getThrowTypeCastException() {
        return this.throwTypeCastException;
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.Symbols
    public IrSimpleFunctionSymbol getThrowIAE() {
        return this.throwIAE;
    }

    public final IrSimpleFunctionSymbol getThrowNoBranchMatchedException() {
        return this.throwNoBranchMatchedException;
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.Symbols
    public IrSimpleFunctionSymbol getThrowUninitializedPropertyAccessException() {
        return this.throwUninitializedPropertyAccessException;
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.Symbols
    public IrClassSymbol getDefaultConstructorMarker() {
        return this.defaultConstructorMarker;
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.Symbols
    public IrSimpleFunctionSymbol getThrowKotlinNothingValueException() {
        throw new NotImplementedError(null, 1, null);
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.Symbols
    public IrClassSymbol getStringBuilder() {
        return this.stringBuilder;
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.Symbols
    public IrClassSymbol getCoroutineImpl() {
        return this.coroutineImpl;
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.Symbols
    public IrSimpleFunctionSymbol getCoroutineSuspendedGetter() {
        return this.coroutineSuspendedGetter;
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.Symbols
    public IrSimpleFunctionSymbol getGetContinuation() {
        return this.getContinuation;
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.Symbols
    public IrClassSymbol getContinuationClass() {
        return this.continuationClass;
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.Symbols
    public IrSimpleFunctionSymbol getCoroutineContextGetter() {
        return this.coroutineContextGetter;
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.Symbols
    public IrSimpleFunctionSymbol getSuspendCoroutineUninterceptedOrReturn() {
        return this.suspendCoroutineUninterceptedOrReturn;
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.Symbols
    public IrSimpleFunctionSymbol getCoroutineGetContext() {
        return this.coroutineGetContext;
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.Symbols
    public IrSimpleFunctionSymbol getReturnIfSuspended() {
        return this.returnIfSuspended;
    }

    public final IrClassSymbol getEnumEntries() {
        return this.enumEntries;
    }

    public final IrSimpleFunctionSymbol getCreateEnumEntries() {
        return this.createEnumEntries;
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

    public final IrPropertySymbol getCoroutineEmptyContinuation() {
        return this.coroutineEmptyContinuation;
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.Symbols
    public IrClassSymbol getFunctionAdapter() {
        throw new NotImplementedError(null, 1, null);
    }

    public final IrSimpleFunctionSymbol getWasmUnreachable() {
        return this.wasmUnreachable;
    }

    public final IrClassSymbol getVoidClass() {
        return this.voidClass;
    }

    public final IrType getVoidType() {
        return (IrType) this.voidType.getValue();
    }

    public final IrSimpleFunctionSymbol findVoidConsumer(IrType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        IrSimpleFunctionSymbol irSimpleFunctionSymbol = this.consumePrimitiveIntoVoid.get(type);
        return irSimpleFunctionSymbol == null ? this.consumeAnyIntoVoid : irSimpleFunctionSymbol;
    }

    public final Map<IrType, IrSimpleFunctionSymbol> getEqualityFunctions() {
        return this.equalityFunctions;
    }

    public final Map<IrType, IrSimpleFunctionSymbol> getFloatEqualityFunctions() {
        return this.floatEqualityFunctions;
    }

    private final String wasmPrimitiveTypeName(IrClassifierSymbol classifier) {
        IrBuiltIns irBuiltIns = this.context.getIrBuiltIns();
        if (Intrinsics.areEqual(classifier, irBuiltIns.getBooleanClass()) ? true : Intrinsics.areEqual(classifier, irBuiltIns.getByteClass()) ? true : Intrinsics.areEqual(classifier, irBuiltIns.getShortClass()) ? true : Intrinsics.areEqual(classifier, irBuiltIns.getCharClass()) ? true : Intrinsics.areEqual(classifier, irBuiltIns.getIntClass())) {
            return "i32";
        }
        if (Intrinsics.areEqual(classifier, irBuiltIns.getFloatClass())) {
            return "f32";
        }
        if (Intrinsics.areEqual(classifier, irBuiltIns.getDoubleClass())) {
            return "f64";
        }
        if (Intrinsics.areEqual(classifier, irBuiltIns.getLongClass())) {
            return "i64";
        }
        throw new IllegalStateException("Unknown primitive type".toString());
    }

    public final Map<IrSimpleFunctionSymbol, IrSimpleFunctionSymbol> getComparisonBuiltInsToWasmIntrinsics() {
        return this.comparisonBuiltInsToWasmIntrinsics;
    }

    public final IrSimpleFunctionSymbol getBooleanAnd() {
        return this.booleanAnd;
    }

    public final IrSimpleFunctionSymbol getRefEq() {
        return this.refEq;
    }

    public final IrSimpleFunctionSymbol getRefIsNull() {
        return this.refIsNull;
    }

    public final IrSimpleFunctionSymbol getRefTest() {
        return this.refTest;
    }

    public final IrSimpleFunctionSymbol getRefCastNull() {
        return this.refCastNull;
    }

    public final IrSimpleFunctionSymbol getWasmArrayCopy() {
        return this.wasmArrayCopy;
    }

    public final IrSimpleFunctionSymbol getWasmArrayNewData0() {
        return this.wasmArrayNewData0;
    }

    public final Map<IrClassSymbol, IrSimpleFunctionSymbol> getPrimitiveTypeToCreateTypedArray() {
        return this.primitiveTypeToCreateTypedArray;
    }

    public final IrSimpleFunctionSymbol getIntToLong() {
        return this.intToLong;
    }

    public final IrSimpleFunctionSymbol getRangeCheck() {
        return this.rangeCheck;
    }

    public final List<IrSimpleFunctionSymbol> getAssertFuncs() {
        return this.assertFuncs;
    }

    public final IrSimpleFunctionSymbol getBoxIntrinsic() {
        return this.boxIntrinsic;
    }

    public final IrSimpleFunctionSymbol getUnboxIntrinsic() {
        return this.unboxIntrinsic;
    }

    public final IrSimpleFunctionSymbol getStringGetLiteral() {
        return this.stringGetLiteral;
    }

    public final IrSimpleFunctionSymbol getStringGetPoolSize() {
        return this.stringGetPoolSize;
    }

    public final IrSimpleFunctionSymbol getTestFun() {
        return this.testFun;
    }

    public final IrSimpleFunctionSymbol getSuiteFun() {
        return this.suiteFun;
    }

    public final IrSimpleFunctionSymbol getStartUnitTests() {
        return this.startUnitTests;
    }

    public final IrSimpleFunctionSymbol getWasmTypeId() {
        return this.wasmTypeId;
    }

    public final IrSimpleFunctionSymbol getWasmIsInterface() {
        return this.wasmIsInterface;
    }

    public final IrSimpleFunctionSymbol getNullableEquals() {
        return this.nullableEquals;
    }

    public final IrSimpleFunctionSymbol getAnyNtoString() {
        return this.anyNtoString;
    }

    public final IrSimpleFunctionSymbol getNullableFloatIeee754Equals() {
        return this.nullableFloatIeee754Equals;
    }

    public final IrSimpleFunctionSymbol getNullableDoubleIeee754Equals() {
        return this.nullableDoubleIeee754Equals;
    }

    public final IrSimpleFunctionSymbol getUnsafeGetScratchRawMemory() {
        return this.unsafeGetScratchRawMemory;
    }

    public final IrSimpleFunctionSymbol getReturnArgumentIfItIsKotlinAny() {
        return this.returnArgumentIfItIsKotlinAny;
    }

    public final List<IrSimpleFunctionSymbol> getStartCoroutineUninterceptedOrReturnIntrinsics() {
        return this.startCoroutineUninterceptedOrReturnIntrinsics;
    }

    public final IrClassSymbol getKLocalDelegatedPropertyImpl() {
        return this.kLocalDelegatedPropertyImpl;
    }

    public final IrClassSymbol getKLocalDelegatedMutablePropertyImpl() {
        return this.kLocalDelegatedMutablePropertyImpl;
    }

    public final IrClassSymbol getKProperty0Impl() {
        return this.kProperty0Impl;
    }

    public final IrClassSymbol getKProperty1Impl() {
        return this.kProperty1Impl;
    }

    public final IrClassSymbol getKProperty2Impl() {
        return this.kProperty2Impl;
    }

    public final IrClassSymbol getKMutableProperty0Impl() {
        return this.kMutableProperty0Impl;
    }

    public final IrClassSymbol getKMutableProperty1Impl() {
        return this.kMutableProperty1Impl;
    }

    public final IrClassSymbol getKMutableProperty2Impl() {
        return this.kMutableProperty2Impl;
    }

    public final IrClassSymbol getKMutableProperty0() {
        return this.kMutableProperty0;
    }

    public final IrClassSymbol getKMutableProperty1() {
        return this.kMutableProperty1;
    }

    public final IrClassSymbol getKMutableProperty2() {
        return this.kMutableProperty2;
    }

    public final IrSimpleFunctionSymbol getKTypeStub() {
        return this.kTypeStub;
    }

    public final List<IrSimpleFunctionSymbol> getArraysCopyInto() {
        return this.arraysCopyInto;
    }

    private final IrSimpleFunctionSymbol findOverloadForReceiver(IrType arrayType, List<? extends IrSimpleFunctionSymbol> overloadsList) {
        for (IrSimpleFunctionSymbol irSimpleFunctionSymbol : overloadsList) {
            IrValueParameter extensionReceiverParameter = irSimpleFunctionSymbol.getOwner().getExtensionReceiverParameter();
            IrType type = extensionReceiverParameter != null ? extensionReceiverParameter.getType() : null;
            if (type != null && IrTypeUtilsKt.isNullable(arrayType) == IrTypeUtilsKt.isNullable(type) && Intrinsics.areEqual(IrTypesKt.getClassOrNull(arrayType), IrTypesKt.getClassOrNull(type))) {
                return irSimpleFunctionSymbol;
            }
        }
        throw new NoSuchElementException("Collection contains no element matching the predicate.");
    }

    public final IrSimpleFunctionSymbol findContentToStringOverload(IrType arrayType) {
        Intrinsics.checkNotNullParameter(arrayType, "arrayType");
        return findOverloadForReceiver(arrayType, this.contentToString);
    }

    public final IrSimpleFunctionSymbol findContentHashCodeOverload(IrType arrayType) {
        Intrinsics.checkNotNullParameter(arrayType, "arrayType");
        return findOverloadForReceiver(arrayType, this.contentHashCode);
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.BuiltinSymbolsBase
    public Map<IrClassifierSymbol, IrSimpleFunctionSymbol> getGetProgressionLastElementByReturnType() {
        return (Map) this.getProgressionLastElementByReturnType.getValue();
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.BuiltinSymbolsBase
    public Map<IrClassifierSymbol, IrSimpleFunctionSymbol> getToUIntByExtensionReceiver() {
        return (Map) this.toUIntByExtensionReceiver.getValue();
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.BuiltinSymbolsBase
    public Map<IrClassifierSymbol, IrSimpleFunctionSymbol> getToULongByExtensionReceiver() {
        return (Map) this.toULongByExtensionReceiver.getValue();
    }

    public final IrType getWasmStructRefType() {
        return (IrType) this.wasmStructRefType.getValue();
    }

    public final IrClassSymbol getWasmAnyRefClass() {
        return this.wasmAnyRefClass;
    }

    /* JADX INFO: compiled from: WasmSymbols.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b-\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006R\u0011\u0010\t\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0006R\u0011\u0010\u000b\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0006R\u0011\u0010\r\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0006R\u0011\u0010\u000f\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0006R\u0011\u0010\u0011\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0006R\u0011\u0010\u0013\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0006R\u0011\u0010\u0015\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0006R\u0011\u0010\u0017\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0006R\u0011\u0010\u0019\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0006R\u0011\u0010\u001b\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0006R\u0011\u0010\u001d\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0006R\u0011\u0010\u001f\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b \u0010\u0006R\u0011\u0010!\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0006R\u0011\u0010#\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b$\u0010\u0006R\u0011\u0010%\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b&\u0010\u0006R\u0011\u0010'\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b(\u0010\u0006R\u0011\u0010)\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b*\u0010\u0006R\u0011\u0010+\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b,\u0010\u0006R\u0011\u0010-\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b.\u0010\u0006R\u0011\u0010/\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b0\u0010\u0006¨\u00061"}, d2 = {"Lorg/jetbrains/kotlin/backend/wasm/WasmSymbols$JsInteropAdapters;", "", "(Lorg/jetbrains/kotlin/backend/wasm/WasmSymbols;)V", "externRefToKotlinBooleanAdapter", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "getExternRefToKotlinBooleanAdapter", "()Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "externRefToKotlinDoubleAdapter", "getExternRefToKotlinDoubleAdapter", "externRefToKotlinFloatAdapter", "getExternRefToKotlinFloatAdapter", "externRefToKotlinIntAdapter", "getExternRefToKotlinIntAdapter", "externRefToKotlinLongAdapter", "getExternRefToKotlinLongAdapter", "jsCheckIsNullOrUndefinedAdapter", "getJsCheckIsNullOrUndefinedAdapter", "jsToKotlinAnyAdapter", "getJsToKotlinAnyAdapter", "jsToKotlinByteAdapter", "getJsToKotlinByteAdapter", "jsToKotlinCharAdapter", "getJsToKotlinCharAdapter", "jsToKotlinShortAdapter", "getJsToKotlinShortAdapter", "jsToKotlinStringAdapter", "getJsToKotlinStringAdapter", "kotlinBooleanToExternRefAdapter", "getKotlinBooleanToExternRefAdapter", "kotlinByteToExternRefAdapter", "getKotlinByteToExternRefAdapter", "kotlinCharToExternRefAdapter", "getKotlinCharToExternRefAdapter", "kotlinDoubleToExternRefAdapter", "getKotlinDoubleToExternRefAdapter", "kotlinFloatToExternRefAdapter", "getKotlinFloatToExternRefAdapter", "kotlinIntToExternRefAdapter", "getKotlinIntToExternRefAdapter", "kotlinLongToExternRefAdapter", "getKotlinLongToExternRefAdapter", "kotlinShortToExternRefAdapter", "getKotlinShortToExternRefAdapter", "kotlinToJsAnyAdapter", "getKotlinToJsAnyAdapter", "kotlinToJsStringAdapter", "getKotlinToJsStringAdapter", "numberToDoubleAdapter", "getNumberToDoubleAdapter", "backend.wasm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public final class JsInteropAdapters {
        private final IrSimpleFunctionSymbol externRefToKotlinBooleanAdapter;
        private final IrSimpleFunctionSymbol externRefToKotlinDoubleAdapter;
        private final IrSimpleFunctionSymbol externRefToKotlinFloatAdapter;
        private final IrSimpleFunctionSymbol externRefToKotlinIntAdapter;
        private final IrSimpleFunctionSymbol externRefToKotlinLongAdapter;
        private final IrSimpleFunctionSymbol jsCheckIsNullOrUndefinedAdapter;
        private final IrSimpleFunctionSymbol jsToKotlinAnyAdapter;
        private final IrSimpleFunctionSymbol jsToKotlinByteAdapter;
        private final IrSimpleFunctionSymbol jsToKotlinCharAdapter;
        private final IrSimpleFunctionSymbol jsToKotlinShortAdapter;
        private final IrSimpleFunctionSymbol jsToKotlinStringAdapter;
        private final IrSimpleFunctionSymbol kotlinBooleanToExternRefAdapter;
        private final IrSimpleFunctionSymbol kotlinByteToExternRefAdapter;
        private final IrSimpleFunctionSymbol kotlinCharToExternRefAdapter;
        private final IrSimpleFunctionSymbol kotlinDoubleToExternRefAdapter;
        private final IrSimpleFunctionSymbol kotlinFloatToExternRefAdapter;
        private final IrSimpleFunctionSymbol kotlinIntToExternRefAdapter;
        private final IrSimpleFunctionSymbol kotlinLongToExternRefAdapter;
        private final IrSimpleFunctionSymbol kotlinShortToExternRefAdapter;
        private final IrSimpleFunctionSymbol kotlinToJsAnyAdapter;
        private final IrSimpleFunctionSymbol kotlinToJsStringAdapter;
        private final IrSimpleFunctionSymbol numberToDoubleAdapter;

        public JsInteropAdapters() {
            this.kotlinToJsStringAdapter = WasmSymbols.this.getInternalFunction("kotlinToJsStringAdapter");
            this.kotlinToJsAnyAdapter = WasmSymbols.this.getInternalFunction("kotlinToJsAnyAdapter");
            this.numberToDoubleAdapter = WasmSymbols.this.getInternalFunction("numberToDoubleAdapter");
            this.jsCheckIsNullOrUndefinedAdapter = WasmSymbols.this.getInternalFunction("jsCheckIsNullOrUndefinedAdapter");
            this.jsToKotlinStringAdapter = WasmSymbols.this.getInternalFunction("jsToKotlinStringAdapter");
            this.jsToKotlinAnyAdapter = WasmSymbols.this.getInternalFunction("jsToKotlinAnyAdapter");
            this.jsToKotlinByteAdapter = WasmSymbols.this.getInternalFunction("jsToKotlinByteAdapter");
            this.jsToKotlinShortAdapter = WasmSymbols.this.getInternalFunction("jsToKotlinShortAdapter");
            this.jsToKotlinCharAdapter = WasmSymbols.this.getInternalFunction("jsToKotlinCharAdapter");
            this.externRefToKotlinIntAdapter = WasmSymbols.this.getInternalFunction("externRefToKotlinIntAdapter");
            this.externRefToKotlinBooleanAdapter = WasmSymbols.this.getInternalFunction("externRefToKotlinBooleanAdapter");
            this.externRefToKotlinLongAdapter = WasmSymbols.this.getInternalFunction("externRefToKotlinLongAdapter");
            this.externRefToKotlinFloatAdapter = WasmSymbols.this.getInternalFunction("externRefToKotlinFloatAdapter");
            this.externRefToKotlinDoubleAdapter = WasmSymbols.this.getInternalFunction("externRefToKotlinDoubleAdapter");
            this.kotlinIntToExternRefAdapter = WasmSymbols.this.getInternalFunction("kotlinIntToExternRefAdapter");
            this.kotlinBooleanToExternRefAdapter = WasmSymbols.this.getInternalFunction("kotlinBooleanToExternRefAdapter");
            this.kotlinLongToExternRefAdapter = WasmSymbols.this.getInternalFunction("kotlinLongToExternRefAdapter");
            this.kotlinFloatToExternRefAdapter = WasmSymbols.this.getInternalFunction("kotlinFloatToExternRefAdapter");
            this.kotlinDoubleToExternRefAdapter = WasmSymbols.this.getInternalFunction("kotlinDoubleToExternRefAdapter");
            this.kotlinByteToExternRefAdapter = WasmSymbols.this.getInternalFunction("kotlinByteToExternRefAdapter");
            this.kotlinShortToExternRefAdapter = WasmSymbols.this.getInternalFunction("kotlinShortToExternRefAdapter");
            this.kotlinCharToExternRefAdapter = WasmSymbols.this.getInternalFunction("kotlinCharToExternRefAdapter");
        }

        public final IrSimpleFunctionSymbol getKotlinToJsStringAdapter() {
            return this.kotlinToJsStringAdapter;
        }

        public final IrSimpleFunctionSymbol getKotlinToJsAnyAdapter() {
            return this.kotlinToJsAnyAdapter;
        }

        public final IrSimpleFunctionSymbol getNumberToDoubleAdapter() {
            return this.numberToDoubleAdapter;
        }

        public final IrSimpleFunctionSymbol getJsCheckIsNullOrUndefinedAdapter() {
            return this.jsCheckIsNullOrUndefinedAdapter;
        }

        public final IrSimpleFunctionSymbol getJsToKotlinStringAdapter() {
            return this.jsToKotlinStringAdapter;
        }

        public final IrSimpleFunctionSymbol getJsToKotlinAnyAdapter() {
            return this.jsToKotlinAnyAdapter;
        }

        public final IrSimpleFunctionSymbol getJsToKotlinByteAdapter() {
            return this.jsToKotlinByteAdapter;
        }

        public final IrSimpleFunctionSymbol getJsToKotlinShortAdapter() {
            return this.jsToKotlinShortAdapter;
        }

        public final IrSimpleFunctionSymbol getJsToKotlinCharAdapter() {
            return this.jsToKotlinCharAdapter;
        }

        public final IrSimpleFunctionSymbol getExternRefToKotlinIntAdapter() {
            return this.externRefToKotlinIntAdapter;
        }

        public final IrSimpleFunctionSymbol getExternRefToKotlinBooleanAdapter() {
            return this.externRefToKotlinBooleanAdapter;
        }

        public final IrSimpleFunctionSymbol getExternRefToKotlinLongAdapter() {
            return this.externRefToKotlinLongAdapter;
        }

        public final IrSimpleFunctionSymbol getExternRefToKotlinFloatAdapter() {
            return this.externRefToKotlinFloatAdapter;
        }

        public final IrSimpleFunctionSymbol getExternRefToKotlinDoubleAdapter() {
            return this.externRefToKotlinDoubleAdapter;
        }

        public final IrSimpleFunctionSymbol getKotlinIntToExternRefAdapter() {
            return this.kotlinIntToExternRefAdapter;
        }

        public final IrSimpleFunctionSymbol getKotlinBooleanToExternRefAdapter() {
            return this.kotlinBooleanToExternRefAdapter;
        }

        public final IrSimpleFunctionSymbol getKotlinLongToExternRefAdapter() {
            return this.kotlinLongToExternRefAdapter;
        }

        public final IrSimpleFunctionSymbol getKotlinFloatToExternRefAdapter() {
            return this.kotlinFloatToExternRefAdapter;
        }

        public final IrSimpleFunctionSymbol getKotlinDoubleToExternRefAdapter() {
            return this.kotlinDoubleToExternRefAdapter;
        }

        public final IrSimpleFunctionSymbol getKotlinByteToExternRefAdapter() {
            return this.kotlinByteToExternRefAdapter;
        }

        public final IrSimpleFunctionSymbol getKotlinShortToExternRefAdapter() {
            return this.kotlinShortToExternRefAdapter;
        }

        public final IrSimpleFunctionSymbol getKotlinCharToExternRefAdapter() {
            return this.kotlinCharToExternRefAdapter;
        }
    }

    /* JADX INFO: compiled from: WasmSymbols.kt */
    @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u001b\u0010\u0007\u001a\u00020\b8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\t\u0010\nR\u0011\u0010\r\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0006R\u0011\u0010\u000f\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0006R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\u0013\u001a\u00020\u00148FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0017\u0010\f\u001a\u0004\b\u0015\u0010\u0016R\u000e\u0010\u0018\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\u0019\u001a\u00020\u00148FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u001b\u0010\f\u001a\u0004\b\u001a\u0010\u0016R\u0015\u0010\u001c\u001a\u00060\u001dR\u00020\u001e¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u000e\u0010!\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\"\u001a\u00020\u00148FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b$\u0010\f\u001a\u0004\b#\u0010\u0016R\u0011\u0010%\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b&\u0010\u0006R\u0014\u0010'\u001a\u00020\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b(\u0010\u0006¨\u0006)"}, d2 = {"Lorg/jetbrains/kotlin/backend/wasm/WasmSymbols$JsRelatedSymbols;", "", "(Lorg/jetbrains/kotlin/backend/wasm/WasmSymbols;)V", "externRefIsNull", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "getExternRefIsNull", "()Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "jsAnyType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "getJsAnyType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "jsAnyType$delegate", "Lkotlin/Lazy;", "jsArrayPush", "getJsArrayPush", "jsCode", "getJsCode", "jsExportClass", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "jsExportConstructor", "Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "getJsExportConstructor", "()Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "jsExportConstructor$delegate", "jsFunClass", "jsFunConstructor", "getJsFunConstructor", "jsFunConstructor$delegate", "jsInteropAdapters", "Lorg/jetbrains/kotlin/backend/wasm/WasmSymbols$JsInteropAdapters;", "Lorg/jetbrains/kotlin/backend/wasm/WasmSymbols;", "getJsInteropAdapters", "()Lorg/jetbrains/kotlin/backend/wasm/WasmSymbols$JsInteropAdapters;", "jsNameClass", "jsNameConstructor", "getJsNameConstructor", "jsNameConstructor$delegate", "newJsArray", "getNewJsArray", "throwAsJsException", "getThrowAsJsException$backend_wasm", "backend.wasm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public final class JsRelatedSymbols {
        private final IrSimpleFunctionSymbol externRefIsNull;

        /* JADX INFO: renamed from: jsAnyType$delegate, reason: from kotlin metadata */
        private final Lazy jsAnyType;
        private final IrSimpleFunctionSymbol jsArrayPush;
        private final IrSimpleFunctionSymbol jsCode;
        private final IrClassSymbol jsExportClass;
        private final IrClassSymbol jsFunClass;
        private final JsInteropAdapters jsInteropAdapters;
        private final IrClassSymbol jsNameClass;
        private final IrSimpleFunctionSymbol newJsArray;
        private final IrSimpleFunctionSymbol throwAsJsException;

        /* JADX INFO: renamed from: jsExportConstructor$delegate, reason: from kotlin metadata */
        private final Lazy jsExportConstructor = LazyKt.lazy(new Function0<IrConstructorSymbol>() { // from class: org.jetbrains.kotlin.backend.wasm.WasmSymbols$JsRelatedSymbols$jsExportConstructor$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrConstructorSymbol invoke() {
                return (IrConstructorSymbol) SequencesKt.single(IrUtilsKt.getConstructors(this.this$0.jsExportClass));
            }
        });

        /* JADX INFO: renamed from: jsNameConstructor$delegate, reason: from kotlin metadata */
        private final Lazy jsNameConstructor = LazyKt.lazy(new Function0<IrConstructorSymbol>() { // from class: org.jetbrains.kotlin.backend.wasm.WasmSymbols$JsRelatedSymbols$jsNameConstructor$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrConstructorSymbol invoke() {
                return (IrConstructorSymbol) SequencesKt.single(IrUtilsKt.getConstructors(this.this$0.jsNameClass));
            }
        });

        /* JADX INFO: renamed from: jsFunConstructor$delegate, reason: from kotlin metadata */
        private final Lazy jsFunConstructor = LazyKt.lazy(new Function0<IrConstructorSymbol>() { // from class: org.jetbrains.kotlin.backend.wasm.WasmSymbols$JsRelatedSymbols$jsFunConstructor$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrConstructorSymbol invoke() {
                return (IrConstructorSymbol) SequencesKt.single(IrUtilsKt.getConstructors(this.this$0.jsFunClass));
            }
        });

        public JsRelatedSymbols() {
            this.jsInteropAdapters = WasmSymbols.this.new JsInteropAdapters();
            this.jsExportClass = WasmSymbols.this.getIrClass(new FqName("kotlin.js.JsExport"));
            this.jsNameClass = WasmSymbols.this.getIrClass(new FqName("kotlin.js.JsName"));
            this.jsFunClass = WasmSymbols.this.getIrClass(new FqName("kotlin.JsFun"));
            this.jsCode = WasmSymbols.this.getFunction("js", WasmSymbols.this.kotlinJsPackage);
            this.jsAnyType = LazyKt.lazy(new Function0<IrType>() { // from class: org.jetbrains.kotlin.backend.wasm.WasmSymbols$JsRelatedSymbols$jsAnyType$2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrType invoke() {
                    return IrTypesKt.getDefaultType(wasmSymbols.getIrClass(new FqName("kotlin.js.JsAny")));
                }
            });
            this.newJsArray = WasmSymbols.this.getInternalFunction("newJsArray");
            this.jsArrayPush = WasmSymbols.this.getInternalFunction("jsArrayPush");
            this.externRefIsNull = WasmSymbols.this.getInternalFunction("wasm_externref_is_null");
            this.throwAsJsException = WasmSymbols.this.getInternalFunction("throwAsJsException");
        }

        public final JsInteropAdapters getJsInteropAdapters() {
            return this.jsInteropAdapters;
        }

        public final IrConstructorSymbol getJsExportConstructor() {
            return (IrConstructorSymbol) this.jsExportConstructor.getValue();
        }

        public final IrConstructorSymbol getJsNameConstructor() {
            return (IrConstructorSymbol) this.jsNameConstructor.getValue();
        }

        public final IrConstructorSymbol getJsFunConstructor() {
            return (IrConstructorSymbol) this.jsFunConstructor.getValue();
        }

        public final IrSimpleFunctionSymbol getJsCode() {
            return this.jsCode;
        }

        public final IrType getJsAnyType() {
            return (IrType) this.jsAnyType.getValue();
        }

        public final IrSimpleFunctionSymbol getNewJsArray() {
            return this.newJsArray;
        }

        public final IrSimpleFunctionSymbol getJsArrayPush() {
            return this.jsArrayPush;
        }

        public final IrSimpleFunctionSymbol getExternRefIsNull() {
            return this.externRefIsNull;
        }

        /* JADX INFO: renamed from: getThrowAsJsException$backend_wasm, reason: from getter */
        public final IrSimpleFunctionSymbol getThrowAsJsException() {
            return this.throwAsJsException;
        }
    }

    public final IrConstructorSymbol getWasmExportConstructor() {
        return (IrConstructorSymbol) this.wasmExportConstructor.getValue();
    }

    private final ClassDescriptor findClass(MemberScope memberScope, Name name2) {
        ClassifierDescriptor contributedClassifier = memberScope.mo7465getContributedClassifier(name2, NoLookupLocation.FROM_BACKEND);
        Intrinsics.checkNotNull(contributedClassifier, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
        return (ClassDescriptor) contributedClassifier;
    }

    private final List<SimpleFunctionDescriptor> findFunctions(MemberScope memberScope, Name name2) {
        return CollectionsKt.toList(memberScope.getContributedFunctions(name2, NoLookupLocation.FROM_BACKEND));
    }

    private final List<PropertyDescriptor> findProperty(MemberScope memberScope, Name name2) {
        return CollectionsKt.toList(memberScope.getContributedVariables(name2, NoLookupLocation.FROM_BACKEND));
    }

    public final ClassDescriptor getClass$backend_wasm(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        ModuleDescriptor module = this.context.getModule();
        FqName fqNameParent = fqName.parent();
        Intrinsics.checkNotNullExpressionValue(fqNameParent, "parent(...)");
        MemberScope memberScope = module.getPackage(fqNameParent).getMemberScope();
        Name nameShortName = fqName.shortName();
        Intrinsics.checkNotNullExpressionValue(nameShortName, "shortName(...)");
        return findClass(memberScope, nameShortName);
    }

    public final PropertyDescriptor getProperty$backend_wasm(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        ModuleDescriptor module = this.context.getModule();
        FqName fqNameParent = fqName.parent();
        Intrinsics.checkNotNullExpressionValue(fqNameParent, "parent(...)");
        MemberScope memberScope = module.getPackage(fqNameParent).getMemberScope();
        Name nameShortName = fqName.shortName();
        Intrinsics.checkNotNullExpressionValue(nameShortName, "shortName(...)");
        return (PropertyDescriptor) CollectionsKt.single((List) findProperty(memberScope, nameShortName));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrSimpleFunctionSymbol getFunction(String name2, PackageViewDescriptor ownerPackage) {
        IrSimpleFunctionSymbol irSimpleFunctionSymbolMaybeGetFunction = maybeGetFunction(name2, ownerPackage);
        if (irSimpleFunctionSymbolMaybeGetFunction != null) {
            return irSimpleFunctionSymbolMaybeGetFunction;
        }
        throw new IllegalArgumentException("Function " + name2 + " not found");
    }

    private final IrSimpleFunctionSymbol maybeGetFunction(String name2, PackageViewDescriptor ownerPackage) {
        MemberScope memberScope = ownerPackage.getMemberScope();
        Name nameIdentifier = Name.identifier(name2);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        List<SimpleFunctionDescriptor> listFindFunctions = findFunctions(memberScope, nameIdentifier);
        if (listFindFunctions.isEmpty()) {
            return null;
        }
        return this.symbolTable.getDescriptorExtension().referenceSimpleFunction(CollectionsKt.single((List) listFindFunctions));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrSimpleFunctionSymbol getInternalFunction(String name2) {
        return getFunction(name2, this.wasmInternalPackage);
    }

    private final IrSimpleFunctionSymbol getEnumsFunction(String name2) {
        return getFunction(name2, this.enumsInternalPackage);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrClassSymbol getIrClass(FqName fqName) {
        return this.symbolTable.getDescriptorExtension().referenceClass(getClass$backend_wasm(fqName));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrClassSymbol getInternalClass(String name2) {
        return getIrClass(new FqName("kotlin.wasm.internal." + name2));
    }

    public final IrType getKFunctionType(IrType type, List<? extends IrType> list) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(list, "list");
        return IrTypesKt.typeWith(getIrBuiltIns().functionN(list.size()), (List<? extends IrType>) CollectionsKt.plus((Collection<? extends IrType>) list, type));
    }
}
