package org.jetbrains.kotlin.backend.jvm.mapping;

import androidx.core.app.NotificationCompat;
import com.reandroid.arsc.container.SpecTypePair;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrTypeUtilsKt;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrUtilsKt;
import org.jetbrains.kotlin.builtins.PrimitiveType;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrScriptSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.types.IrDynamicType;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeArgument;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypeSystemContext;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.types.impl.IrStarProjectionImpl;
import org.jetbrains.kotlin.ir.util.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNameUnsafe;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.TypeCheckerState;
import org.jetbrains.kotlin.types.TypeSystemCommonBackendContextForTypeMapping;
import org.jetbrains.kotlin.types.model.AnnotationMarker;
import org.jetbrains.kotlin.types.model.CaptureStatus;
import org.jetbrains.kotlin.types.model.CapturedTypeConstructorMarker;
import org.jetbrains.kotlin.types.model.CapturedTypeMarker;
import org.jetbrains.kotlin.types.model.DefinitelyNotNullTypeMarker;
import org.jetbrains.kotlin.types.model.FlexibleTypeMarker;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.ObsoleteTypeKind;
import org.jetbrains.kotlin.types.model.SimpleTypeMarker;
import org.jetbrains.kotlin.types.model.TypeArgumentListMarker;
import org.jetbrains.kotlin.types.model.TypeArgumentMarker;
import org.jetbrains.kotlin.types.model.TypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeParameterMarker;
import org.jetbrains.kotlin.types.model.TypeSubstitutorMarker;
import org.jetbrains.kotlin.types.model.TypeVariableTypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeVariance;

/* JADX INFO: compiled from: IrTypeMapper.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000º\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b<\n\u0002\u0010(\n\u0002\b\t\n\u0002\u0010\"\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u001e\n\u0002\b\t\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0001\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u0010\u001a\u00020\u0011H\u0096\u0001J\u0019\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0015H\u0096\u0001J\u0011\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\u0096\u0001J\u001b\u0010\u001b\u001a\u0004\u0018\u00010\u00182\u0006\u0010\u001c\u001a\u00020\u00182\u0006\u0010\u001d\u001a\u00020\u001eH\u0096\u0001J\u0013\u0010\u001f\u001a\u0004\u0018\u00010\u001a2\u0006\u0010\u001c\u001a\u00020\u001aH\u0096\u0001J\b\u0010 \u001a\u00020!H\u0016J\t\u0010\"\u001a\u00020#H\u0096\u0001J\u0011\u0010$\u001a\u00020\u00182\u0006\u0010%\u001a\u00020&H\u0096\u0001J\u0019\u0010'\u001a\u00020\u001a2\u0006\u0010(\u001a\u00020\u00182\u0006\u0010)\u001a\u00020\u0018H\u0096\u0001J?\u0010*\u001a\u00020\u00182\u0006\u0010+\u001a\u00020\u00152\f\u0010,\u001a\b\u0012\u0004\u0012\u00020.0-2\u0006\u0010/\u001a\u00020\u00132\u0006\u00100\u001a\u00020\u00132\u000e\u00101\u001a\n\u0012\u0004\u0012\u000202\u0018\u00010-H\u0096\u0001J\u0011\u00103\u001a\u0002042\u0006\u0010\u000b\u001a\u00020\fH\u0096\u0001J\u0019\u00105\u001a\u00020.2\u0006\u0010\u001c\u001a\u00020\u001a2\u0006\u00106\u001a\u000207H\u0096\u0001J\u0011\u00108\u001a\u00020\u001a2\u0006\u0010+\u001a\u00020\u0015H\u0096\u0001J\u0019\u00109\u001a\u0004\u0018\u00010\u00182\f\u0010:\u001a\b\u0012\u0004\u0012\u00020\u00180-H\u0096\u0001J\u0010\u0010;\u001a\u00020!2\u0006\u0010<\u001a\u00020=H\u0016J\u0019\u0010>\u001a\u00020\u00132\u0006\u0010?\u001a\u00020\u00182\u0006\u0010@\u001a\u00020\u0018H\u0096\u0001J\u0017\u0010A\u001a\u00020\u001a2\f\u0010B\u001a\b\u0012\u0004\u0012\u00020\u001a0-H\u0096\u0001J\u0017\u0010A\u001a\u00020\u00182\f\u0010B\u001a\b\u0012\u0004\u0012\u00020\u00180-H\u0096\u0001J\u0019\u0010C\u001a\u00020D2\u0006\u0010E\u001a\u00020\u00132\u0006\u0010F\u001a\u00020\u0013H\u0096\u0001J\t\u0010G\u001a\u00020\u0011H\u0096\u0001J\t\u0010H\u001a\u00020\u0011H\u0096\u0001J\t\u0010I\u001a\u00020\u0011H\u0096\u0001J\u0011\u0010J\u001a\u00020K2\u0006\u0010\u001c\u001a\u00020\u0018H\u0096\u0001J\u001d\u0010L\u001a\u00020#2\u0012\u0010M\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u001a0NH\u0096\u0001J\u001d\u0010O\u001a\b\u0012\u0004\u0012\u0002020-2\f\u0010B\u001a\b\u0012\u0004\u0012\u00020\u001a0-H\u0096\u0001J!\u0010P\u001a\u00020\u0013*\u00020\u001a2\u0012\u0010Q\u001a\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00130RH\u0096\u0001J\r\u0010S\u001a\u00020=*\u00020\u001aH\u0096\u0001J\r\u0010T\u001a\u00020\u0011*\u00020\u0018H\u0096\u0001J\u000f\u0010U\u001a\u0004\u0018\u00010V*\u00020\u0018H\u0096\u0001J\u000f\u0010W\u001a\u0004\u0018\u00010X*\u00020\u0018H\u0096\u0001J\u000f\u0010Y\u001a\u0004\u0018\u00010Z*\u00020[H\u0096\u0001J\u000f\u0010\\\u001a\u0004\u0018\u00010[*\u00020\u001aH\u0096\u0001J\u000f\u0010]\u001a\u0004\u0018\u00010\u0018*\u00020\u001aH\u0096\u0001J\r\u0010^\u001a\u00020_*\u00020\u001aH\u0096\u0001J\f\u0010`\u001a\u00020\f*\u00020\u0015H\u0016J\r\u0010a\u001a\u00020\u0013*\u00020\u001aH\u0096\u0001J\r\u0010b\u001a\u00020\u001e*\u00020VH\u0096\u0001J\f\u0010c\u001a\u00020d*\u00020\u0015H\u0016J\u001d\u0010e\u001a\n\u0012\u0004\u0012\u00020\u0018\u0018\u00010-*\u00020\u00182\u0006\u0010+\u001a\u00020\u0015H\u0096\u0001J\u0015\u0010f\u001a\u00020.*\u00020g2\u0006\u0010h\u001a\u00020=H\u0096\u0003J\u0017\u0010i\u001a\u0004\u0018\u00010j*\u00020\u001a2\u0006\u0010k\u001a\u00020lH\u0096\u0001J\u0015\u0010m\u001a\u00020.*\u00020\u001a2\u0006\u0010h\u001a\u00020=H\u0096\u0001J\u0017\u0010n\u001a\u0004\u0018\u00010.*\u00020\u00182\u0006\u0010h\u001a\u00020=H\u0096\u0001J\u0013\u0010o\u001a\b\u0012\u0004\u0012\u00020.0-*\u00020\u001aH\u0096\u0001J\u0013\u0010p\u001a\b\u0012\u0004\u0012\u0002020-*\u00020\u001aH\u0096\u0001J\u000f\u0010q\u001a\u0004\u0018\u00010r*\u00020\u0015H\u0096\u0001J\u0013\u0010s\u001a\b\u0012\u0004\u0012\u0002020-*\u00020\u001aH\u0096\u0001J\r\u0010t\u001a\u00020u*\u00020\fH\u0096\u0001J\u000e\u0010v\u001a\u0004\u0018\u00010&*\u00020\u001aH\u0016J\u0015\u0010w\u001a\u00020x*\u00020\u00152\u0006\u0010h\u001a\u00020=H\u0096\u0001J\u0013\u0010y\u001a\b\u0012\u0004\u0012\u00020x0-*\u00020\u0015H\u0096\u0001J\u000f\u0010z\u001a\u0004\u0018\u00010{*\u00020\u0015H\u0096\u0001J\u000f\u0010|\u001a\u0004\u0018\u00010{*\u00020\u0015H\u0096\u0001J\r\u0010}\u001a\u00020\u001a*\u00020\fH\u0096\u0001J\u000f\u0010~\u001a\u0004\u0018\u00010\u001a*\u00020\u001aH\u0096\u0001J\r\u0010\u007f\u001a\u00020d*\u00020.H\u0096\u0001J\u000e\u0010\u0080\u0001\u001a\u00020x*\u00020\fH\u0096\u0001J\u0010\u0010\u0081\u0001\u001a\u0004\u0018\u00010\f*\u00020\u0015H\u0096\u0001J\u0010\u0010\u0082\u0001\u001a\u0004\u0018\u00010\u001a*\u00020\u001aH\u0096\u0001J\u0016\u0010\u0083\u0001\u001a\u00020\u001a*\u00020\f2\u0006\u0010h\u001a\u00020=H\u0096\u0001J\u0014\u0010\u0084\u0001\u001a\b\u0012\u0004\u0012\u00020\u001a0-*\u00020\fH\u0096\u0001J#\u0010\u0085\u0001\u001a\u0017\u0012\u0011\u0012\u000f\u0012\u0004\u0012\u00020u\u0012\u0004\u0012\u00020\u00180\u0086\u0001\u0018\u00010-*\u00020\u0015H\u0096\u0001J\u000e\u0010\u0087\u0001\u001a\u000207*\u00020.H\u0096\u0001J\u000e\u0010\u0087\u0001\u001a\u000207*\u00020\fH\u0096\u0001J\u0016\u0010\u0088\u0001\u001a\u00020\u0013*\u00020\u001a2\u0006\u0010k\u001a\u00020lH\u0096\u0001J\u000e\u0010\u0089\u0001\u001a\u00020\u0013*\u00020\u001aH\u0096\u0001J\u000e\u0010\u008a\u0001\u001a\u00020\u0013*\u00020\u001aH\u0096\u0001J\u0019\u0010\u008b\u0001\u001a\u00020\u0013*\u00020\f2\t\u0010\u008c\u0001\u001a\u0004\u0018\u00010\u0015H\u0096\u0001J\u000e\u0010\u008d\u0001\u001a\u00020\u0013*\u00020\u0015H\u0096\u0001J\u000e\u0010\u008e\u0001\u001a\u00020\u0013*\u00020\u0015H\u0096\u0001J\u000e\u0010\u008f\u0001\u001a\u00020\u0013*\u00020\u001aH\u0096\u0001J\u000e\u0010\u0090\u0001\u001a\u00020\u0013*\u00020\u001aH\u0096\u0001J\u000e\u0010\u0091\u0001\u001a\u00020\u0013*\u00020\u001aH\u0096\u0001J\u000e\u0010\u0092\u0001\u001a\u00020\u0013*\u00020\u0018H\u0096\u0001J\u000e\u0010\u0093\u0001\u001a\u00020\u0013*\u00020\u0015H\u0096\u0001J\u000e\u0010\u0094\u0001\u001a\u00020\u0013*\u00020\u0015H\u0096\u0001J\u000e\u0010\u0095\u0001\u001a\u00020\u0013*\u00020\u0015H\u0096\u0001J\u000e\u0010\u0096\u0001\u001a\u00020\u0013*\u00020\u001aH\u0096\u0001J\u000e\u0010\u0097\u0001\u001a\u00020\u0013*\u00020\u0015H\u0096\u0001J\u000e\u0010\u0098\u0001\u001a\u00020\u0013*\u00020\u001aH\u0096\u0001J\u000e\u0010\u0099\u0001\u001a\u00020\u0013*\u00020\u001aH\u0096\u0001J\u000e\u0010\u0099\u0001\u001a\u00020\u0013*\u00020\u0015H\u0096\u0001J\r\u00100\u001a\u00020\u0013*\u00020\u0018H\u0096\u0001J\u000e\u0010\u009a\u0001\u001a\u00020\u0013*\u00020\u0015H\u0096\u0001J\u000e\u0010\u009b\u0001\u001a\u00020\u0013*\u00020\u001aH\u0096\u0001J\u000e\u0010\u009c\u0001\u001a\u00020\u0013*\u00020\u001aH\u0096\u0001J\u000e\u0010\u009d\u0001\u001a\u00020\u0013*\u00020\u0015H\u0096\u0001J\u000e\u0010\u009e\u0001\u001a\u00020\u0013*\u00020\u0015H\u0096\u0001J\u000e\u0010\u009f\u0001\u001a\u00020\u0013*\u00020\u0015H\u0096\u0001J\u000e\u0010 \u0001\u001a\u00020\u0013*\u00020\u0015H\u0096\u0001J\u000e\u0010¡\u0001\u001a\u00020\u0013*\u00020\u0018H\u0096\u0001J\u000e\u0010¢\u0001\u001a\u00020\u0013*\u00020\u0015H\u0096\u0001J\u000e\u0010£\u0001\u001a\u00020\u0013*\u00020\u0015H\u0096\u0001J\u000e\u0010¤\u0001\u001a\u00020\u0013*\u00020\u001aH\u0096\u0001J\u000e\u0010¥\u0001\u001a\u00020\u0013*\u00020\u0015H\u0096\u0001J\r\u0010¦\u0001\u001a\u00020\u0013*\u00020\u0018H\u0016J\u000e\u0010§\u0001\u001a\u00020\u0013*\u00020\u0015H\u0096\u0001J\u000e\u0010¨\u0001\u001a\u00020\u0013*\u00020\u001aH\u0096\u0001J\u000e\u0010¨\u0001\u001a\u00020\u0013*\u00020\u0018H\u0096\u0001J\u000e\u0010©\u0001\u001a\u00020\u0013*\u00020\u0015H\u0096\u0001J\u000e\u0010ª\u0001\u001a\u00020\u0013*\u00020\u001aH\u0097\u0001J\u000e\u0010«\u0001\u001a\u00020\u0013*\u00020\u001aH\u0096\u0001J\u000e\u0010¬\u0001\u001a\u00020\u0013*\u00020\u0015H\u0096\u0001J\u000e\u0010\u00ad\u0001\u001a\u00020\u0013*\u00020\u001aH\u0096\u0001J\u000e\u0010®\u0001\u001a\u00020\u0013*\u00020\u001aH\u0096\u0001J\u000e\u0010¯\u0001\u001a\u00020\u0013*\u00020\u001aH\u0096\u0001J\u000e\u0010°\u0001\u001a\u00020\u0013*\u00020VH\u0096\u0001J\u000e\u0010±\u0001\u001a\u00020\u0013*\u00020\u0018H\u0096\u0001J\u000e\u0010²\u0001\u001a\u00020\u0013*\u00020VH\u0096\u0001J\r\u0010³\u0001\u001a\u00020\u0013*\u00020\u001aH\u0016J\u000e\u0010´\u0001\u001a\u00020\u0013*\u00020\fH\u0096\u0001J\r\u0010µ\u0001\u001a\u00020\u0013*\u00020\u0015H\u0016J\u000e\u0010¶\u0001\u001a\u00020\u0013*\u00020\u001aH\u0096\u0001J\u000e\u0010·\u0001\u001a\u00020\u0013*\u00020\u0018H\u0096\u0001J\u000e\u0010¸\u0001\u001a\u00020\u0013*\u00020.H\u0096\u0001J\u000e\u0010¹\u0001\u001a\u00020\u0013*\u00020\u0018H\u0096\u0001J\u000e\u0010º\u0001\u001a\u00020\u0013*\u00020\u0018H\u0096\u0001J\u000e\u0010»\u0001\u001a\u00020\u0013*\u00020\u0018H\u0096\u0001J\r\u0010¼\u0001\u001a\u00020\u0013*\u00020\u0018H\u0016J\r\u0010½\u0001\u001a\u00020\u0013*\u00020\u0015H\u0016J\u000e\u0010¾\u0001\u001a\u00020\u0013*\u00020\u0015H\u0096\u0001J\u000e\u0010¿\u0001\u001a\u00020\u0013*\u00020\u001aH\u0096\u0001J\u000e\u0010À\u0001\u001a\u00020\u0013*\u00020\u0015H\u0096\u0001J\u000e\u0010Á\u0001\u001a\u00020\u0013*\u00020\u001aH\u0096\u0001J\u0015\u0010Â\u0001\u001a\t\u0012\u0004\u0012\u00020.0Ã\u0001*\u00020gH\u0096\u0003J\r\u0010(\u001a\u00020\u0018*\u00020[H\u0096\u0001J\u000e\u0010Ä\u0001\u001a\u00020\u0018*\u00020\u001aH\u0096\u0001J\u0010\u0010Å\u0001\u001a\u0004\u0018\u00010\u001a*\u00020VH\u0096\u0001J\u000e\u0010Æ\u0001\u001a\u00020\u001a*\u00020\u001aH\u0096\u0001J\u000e\u0010Ç\u0001\u001a\u00020\u001a*\u00020\u001aH\u0096\u0001J\u000e\u0010È\u0001\u001a\u00020\u0018*\u00020\u0018H\u0096\u0001J\u000e\u0010É\u0001\u001a\u00020\u0018*\u00020XH\u0096\u0001J\u000e\u0010Ê\u0001\u001a\u00020\u0018*\u00020\u0018H\u0096\u0001J\u000e\u0010Ë\u0001\u001a\u00020=*\u00020\u0015H\u0096\u0001J\u0015\u0010Ì\u0001\u001a\t\u0012\u0004\u0012\u00020d0Í\u0001*\u00020\u0018H\u0096\u0001J\u000f\u0010Î\u0001\u001a\u00020.*\u00030Ï\u0001H\u0096\u0001J\u001d\u0010Ð\u0001\u001a\u00020\u001a*\u00020\u001a2\r\u0010Ñ\u0001\u001a\b\u0012\u0004\u0012\u0002020-H\u0096\u0001J\u0017\u0010Ò\u0001\u001a\u00020.*\u00020.2\u0007\u0010Ó\u0001\u001a\u00020\u001aH\u0096\u0001J\r\u0010Ô\u0001\u001a\u00020d*\u00020\fH\u0016J\u0016\u0010Õ\u0001\u001a\u00020\u001a*\u00020#2\u0006\u0010\u001c\u001a\u00020\u001aH\u0096\u0001J\u000e\u0010Ö\u0001\u001a\u00020=*\u00020gH\u0096\u0001J\u0015\u0010×\u0001\u001a\t\u0012\u0004\u0012\u00020\u001a0Ø\u0001*\u00020\u0015H\u0096\u0001J\u000e\u0010Ù\u0001\u001a\u00020\u0018*\u00020\u0015H\u0096\u0001J\u000f\u0010Ú\u0001\u001a\u00030Ï\u0001*\u00020VH\u0096\u0001J\u000e\u0010Ú\u0001\u001a\u00020\u0015*\u00020\u001aH\u0096\u0001J\u000e\u0010Ú\u0001\u001a\u00020\u0015*\u00020\u0018H\u0096\u0001J\u000e\u0010Û\u0001\u001a\u00020=*\u00020\u001aH\u0096\u0001J\u000e\u0010Û\u0001\u001a\u00020=*\u00020\u0018H\u0096\u0001J\u001b\u0010Ü\u0001\u001a\u00020\u0011*\u00020\u00152\f\u0010,\u001a\b\u0012\u0004\u0012\u00020\u001a0-H\u0016J\u000e\u0010Ý\u0001\u001a\u00020\u0015*\u00020\u0015H\u0096\u0001J\r\u0010)\u001a\u00020\u0018*\u00020[H\u0096\u0001J\u000e\u0010Þ\u0001\u001a\u00020=*\u00020\fH\u0096\u0001J\u000e\u0010ß\u0001\u001a\u00020\u0018*\u00020\u001aH\u0096\u0001J\u0016\u0010à\u0001\u001a\u00020\u001a*\u00020\u001a2\u0006\u0010/\u001a\u00020\u0013H\u0096\u0001J\u0016\u0010à\u0001\u001a\u00020\u0018*\u00020\u00182\u0006\u0010/\u001a\u00020\u0013H\u0096\u0001R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0007\u001a\u00020\bX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u001a\u0010\u000b\u001a\u0004\u0018\u00010\f*\u00020\r8VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000f¨\u0006á\u0001"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/mapping/IrTypeCheckerContextForTypeMapping;", "Lorg/jetbrains/kotlin/ir/types/IrTypeSystemContext;", "Lorg/jetbrains/kotlin/types/TypeSystemCommonBackendContextForTypeMapping;", "baseContext", "backendContext", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/ir/types/IrTypeSystemContext;Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "irBuiltIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "getIrBuiltIns", "()Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "typeParameter", "Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;", "Lorg/jetbrains/kotlin/types/model/TypeVariableTypeConstructorMarker;", "getTypeParameter", "(Lorg/jetbrains/kotlin/types/model/TypeVariableTypeConstructorMarker;)Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;", SchemaSymbols.ATTVAL_ANYTYPE, "Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "areEqualTypeConstructors", "", "c1", "Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;", "c2", "arrayType", "Lorg/jetbrains/kotlin/types/model/SimpleTypeMarker;", "componentType", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "captureFromArguments", ModuleXmlParser.TYPE, NotificationCompat.CATEGORY_STATUS, "Lorg/jetbrains/kotlin/types/model/CaptureStatus;", "captureFromExpression", "continuationTypeConstructor", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "createEmptySubstitutor", "Lorg/jetbrains/kotlin/types/model/TypeSubstitutorMarker;", "createErrorType", "debugName", "", "createFlexibleType", "lowerBound", "upperBound", "createSimpleType", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "arguments", "", "Lorg/jetbrains/kotlin/types/model/TypeArgumentMarker;", "nullable", "isExtensionFunction", "attributes", "Lorg/jetbrains/kotlin/types/model/AnnotationMarker;", "createStarProjection", "Lorg/jetbrains/kotlin/ir/types/impl/IrStarProjectionImpl;", "createTypeArgument", "variance", "Lorg/jetbrains/kotlin/types/model/TypeVariance;", "createUninferredType", "findCommonIntegerLiteralTypesSuperType", "explicitSupertypes", "functionNTypeConstructor", "n", "", "identicalArguments", "a", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "intersectTypes", SpecTypePair.NAME_types, "newTypeCheckerState", "Lorg/jetbrains/kotlin/types/TypeCheckerState;", "errorTypesEqualToAnything", "stubTypesEqualToAnything", "nothingType", "nullableAnyType", "nullableNothingType", "substitutionSupertypePolicy", "Lorg/jetbrains/kotlin/types/TypeCheckerState$SupertypesPolicy;", "typeSubstitutorByTypeConstructor", "map", "", "unionTypeAttributes", "anySuperTypeConstructor", "predicate", "Lkotlin/Function1;", "argumentsCount", "asArgumentList", "asCapturedType", "Lorg/jetbrains/kotlin/types/model/CapturedTypeMarker;", "asDefinitelyNotNullType", "Lorg/jetbrains/kotlin/types/model/DefinitelyNotNullTypeMarker;", "asDynamicType", "Lorg/jetbrains/kotlin/ir/types/IrDynamicType;", "Lorg/jetbrains/kotlin/types/model/FlexibleTypeMarker;", "asFlexibleType", "asSimpleType", "asTypeArgument", "Lorg/jetbrains/kotlin/ir/types/IrTypeArgument;", "asTypeParameter", "canHaveUndefinedNullability", "captureStatus", "defaultType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "fastCorrespondingSupertypes", "get", "Lorg/jetbrains/kotlin/types/model/TypeArgumentListMarker;", "index", "getAnnotationFirstArgumentValue", "", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "getArgument", "getArgumentOrNull", "getArguments", "getAttributes", "getClassFqNameUnsafe", "Lorg/jetbrains/kotlin/name/FqNameUnsafe;", "getCustomAttributes", "getName", "Lorg/jetbrains/kotlin/name/Name;", "getNameForErrorType", "getParameter", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeParameterSymbol;", "getParameters", "getPrimitiveArrayType", "Lorg/jetbrains/kotlin/builtins/PrimitiveType;", "getPrimitiveType", "getRepresentativeUpperBound", "getSubstitutedUnderlyingType", "getType", "getTypeConstructor", "getTypeParameterClassifier", "getUnsubstitutedUnderlyingType", "getUpperBound", "getUpperBounds", "getValueClassProperties", "Lkotlin/Pair;", "getVariance", "hasAnnotation", "hasCustomAttributes", "hasFlexibleNullability", "hasRecursiveBounds", "selfConstructor", "isAnonymous", "isAnyConstructor", "isArrayOrNullableArray", "isCapturedDynamic", "isCapturedType", "isClassType", "isClassTypeConstructor", "isCommonFinalClassConstructor", "isDefinitelyClassTypeConstructor", "isDefinitelyNotNullType", "isDenotable", "isDynamic", "isError", "isFinalClassOrEnumEntryOrAnnotationClassConstructor", "isFlexible", "isFlexibleNothing", "isInlineClass", "isInnerClass", "isIntegerConstantOperatorTypeConstructor", "isIntegerLiteralConstantTypeConstructor", "isIntegerLiteralType", "isIntegerLiteralTypeConstructor", "isInterface", "isInterfaceOrAnnotationClass", "isIntersection", "isKClass", "isLocalType", "isMarkedNullable", "isMultiFieldValueClass", "isNotNullTypeParameter", "isNothing", "isNothingConstructor", "isNullableAny", "isNullableNothing", "isNullableType", "isOldCapturedType", "isPrimitiveType", "isProjectionNotNull", "isRawType", "isReified", "isScript", "isSimpleType", "isSingleClassifierType", "isStarProjection", "isStubType", "isStubTypeForBuilderInference", "isStubTypeForVariableInSubtyping", "isSuspendFunction", "isTypeParameter", "isTypeParameterTypeConstructor", "isTypeVariableType", "isUnderKotlinPackage", "isUninferredParameter", "iterator", "", "lowerBoundIfFlexible", "lowerType", "makeDefinitelyNotNullOrNotNull", "makeNullable", "makeSimpleTypeDefinitelyNotNullOrNotNull", "original", "originalIfDefinitelyNotNullable", "parametersCount", "possibleIntegerTypes", "", "projection", "Lorg/jetbrains/kotlin/types/model/CapturedTypeConstructorMarker;", "replaceCustomAttributes", "newAttributes", "replaceType", "newType", "representativeUpperBound", "safeSubstitute", "size", "supertypes", "", "toErrorType", "typeConstructor", "typeDepth", "typeWithArguments", "unwrapStubTypeVariableConstructor", "upperBoundCount", "upperBoundIfFlexible", "withNullability", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class IrTypeCheckerContextForTypeMapping implements IrTypeSystemContext, TypeSystemCommonBackendContextForTypeMapping {
    private final JvmBackendContext backendContext;
    private final IrTypeSystemContext baseContext;

    @Override // org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
    public boolean anySuperTypeConstructor(KotlinTypeMarker kotlinTypeMarker, Function1<? super SimpleTypeMarker, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        return this.baseContext.anySuperTypeConstructor(kotlinTypeMarker, predicate);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemBuiltInsContext
    public IrSimpleType anyType() {
        return this.baseContext.anyType();
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean areEqualTypeConstructors(TypeConstructorMarker c1, TypeConstructorMarker c2) {
        Intrinsics.checkNotNullParameter(c1, "c1");
        Intrinsics.checkNotNullParameter(c2, "c2");
        return this.baseContext.areEqualTypeConstructors(c1, c2);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public int argumentsCount(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.baseContext.argumentsCount(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public SimpleTypeMarker arrayType(KotlinTypeMarker componentType) {
        Intrinsics.checkNotNullParameter(componentType, "componentType");
        return this.baseContext.arrayType(componentType);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public IrSimpleType asArgumentList(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.baseContext.asArgumentList(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public CapturedTypeMarker asCapturedType(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.baseContext.asCapturedType(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public DefinitelyNotNullTypeMarker asDefinitelyNotNullType(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.baseContext.asDefinitelyNotNullType(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public IrDynamicType asDynamicType(FlexibleTypeMarker flexibleTypeMarker) {
        Intrinsics.checkNotNullParameter(flexibleTypeMarker, "<this>");
        return this.baseContext.asDynamicType(flexibleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public FlexibleTypeMarker asFlexibleType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.baseContext.asFlexibleType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker asSimpleType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.baseContext.asSimpleType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public IrTypeArgument asTypeArgument(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.baseContext.asTypeArgument(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
    public boolean canHaveUndefinedNullability(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.baseContext.canHaveUndefinedNullability(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker captureFromArguments(SimpleTypeMarker type, CaptureStatus status) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(status, "status");
        return this.baseContext.captureFromArguments(type, status);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker captureFromExpression(KotlinTypeMarker type) {
        Intrinsics.checkNotNullParameter(type, "type");
        return this.baseContext.captureFromExpression(type);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public CaptureStatus captureStatus(CapturedTypeMarker capturedTypeMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
        return this.baseContext.captureStatus(capturedTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeSubstitutorMarker createEmptySubstitutor() {
        return this.baseContext.createEmptySubstitutor();
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemTypeFactoryContext
    public SimpleTypeMarker createErrorType(String debugName) {
        Intrinsics.checkNotNullParameter(debugName, "debugName");
        return this.baseContext.createErrorType(debugName);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemTypeFactoryContext
    public KotlinTypeMarker createFlexibleType(SimpleTypeMarker lowerBound, SimpleTypeMarker upperBound) {
        Intrinsics.checkNotNullParameter(lowerBound, "lowerBound");
        Intrinsics.checkNotNullParameter(upperBound, "upperBound");
        return this.baseContext.createFlexibleType(lowerBound, upperBound);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemTypeFactoryContext
    public SimpleTypeMarker createSimpleType(TypeConstructorMarker constructor, List<? extends TypeArgumentMarker> arguments, boolean nullable, boolean isExtensionFunction, List<? extends AnnotationMarker> attributes) {
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        return this.baseContext.createSimpleType(constructor, arguments, nullable, isExtensionFunction, attributes);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemTypeFactoryContext
    public IrStarProjectionImpl createStarProjection(TypeParameterMarker typeParameter) {
        Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
        return this.baseContext.createStarProjection(typeParameter);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemTypeFactoryContext
    public TypeArgumentMarker createTypeArgument(KotlinTypeMarker type, TypeVariance variance) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(variance, "variance");
        return this.baseContext.createTypeArgument(type, variance);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemTypeFactoryContext
    public KotlinTypeMarker createUninferredType(TypeConstructorMarker constructor) {
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        return this.baseContext.createUninferredType(constructor);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public List<SimpleTypeMarker> fastCorrespondingSupertypes(SimpleTypeMarker simpleTypeMarker, TypeConstructorMarker constructor) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        return this.baseContext.fastCorrespondingSupertypes(simpleTypeMarker, constructor);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
    public SimpleTypeMarker findCommonIntegerLiteralTypesSuperType(List<? extends SimpleTypeMarker> explicitSupertypes) {
        Intrinsics.checkNotNullParameter(explicitSupertypes, "explicitSupertypes");
        return this.baseContext.findCommonIntegerLiteralTypesSuperType(explicitSupertypes);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeArgumentMarker get(TypeArgumentListMarker typeArgumentListMarker, int i) {
        Intrinsics.checkNotNullParameter(typeArgumentListMarker, "<this>");
        return this.baseContext.get(typeArgumentListMarker, i);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public Object getAnnotationFirstArgumentValue(KotlinTypeMarker kotlinTypeMarker, FqName fqName) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        return this.baseContext.getAnnotationFirstArgumentValue(kotlinTypeMarker, fqName);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeArgumentMarker getArgument(KotlinTypeMarker kotlinTypeMarker, int i) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.baseContext.getArgument(kotlinTypeMarker, i);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeArgumentMarker getArgumentOrNull(SimpleTypeMarker simpleTypeMarker, int i) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.baseContext.getArgumentOrNull(simpleTypeMarker, i);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public List<TypeArgumentMarker> getArguments(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.baseContext.getArguments(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public List<AnnotationMarker> getAttributes(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.baseContext.getAttributes(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public FqNameUnsafe getClassFqNameUnsafe(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.baseContext.getClassFqNameUnsafe(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public List<AnnotationMarker> getCustomAttributes(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.baseContext.getCustomAttributes(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext
    public IrBuiltIns getIrBuiltIns() {
        return this.baseContext.getIrBuiltIns();
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public Name getName(TypeParameterMarker typeParameterMarker) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        return this.baseContext.getName(typeParameterMarker);
    }

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContextForTypeMapping
    public String getNameForErrorType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return null;
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public IrTypeParameterSymbol getParameter(TypeConstructorMarker typeConstructorMarker, int i) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.baseContext.getParameter(typeConstructorMarker, i);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public List<IrTypeParameterSymbol> getParameters(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.baseContext.getParameters(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public PrimitiveType getPrimitiveArrayType(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.baseContext.getPrimitiveArrayType(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public PrimitiveType getPrimitiveType(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.baseContext.getPrimitiveType(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public KotlinTypeMarker getRepresentativeUpperBound(TypeParameterMarker typeParameterMarker) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        return this.baseContext.getRepresentativeUpperBound(typeParameterMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public KotlinTypeMarker getSubstitutedUnderlyingType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.baseContext.getSubstitutedUnderlyingType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public IrType getType(TypeArgumentMarker typeArgumentMarker) {
        Intrinsics.checkNotNullParameter(typeArgumentMarker, "<this>");
        return this.baseContext.getType(typeArgumentMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public IrTypeParameterSymbol getTypeConstructor(TypeParameterMarker typeParameterMarker) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        return this.baseContext.getTypeConstructor(typeParameterMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeParameterMarker getTypeParameter(TypeVariableTypeConstructorMarker typeVariableTypeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeVariableTypeConstructorMarker, "<this>");
        return this.baseContext.getTypeParameter(typeVariableTypeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeParameterMarker getTypeParameterClassifier(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.baseContext.getTypeParameterClassifier(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public KotlinTypeMarker getUnsubstitutedUnderlyingType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.baseContext.getUnsubstitutedUnderlyingType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker getUpperBound(TypeParameterMarker typeParameterMarker, int i) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        return this.baseContext.getUpperBound(typeParameterMarker, i);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public List<KotlinTypeMarker> getUpperBounds(TypeParameterMarker typeParameterMarker) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        return this.baseContext.getUpperBounds(typeParameterMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public List<Pair<Name, SimpleTypeMarker>> getValueClassProperties(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.baseContext.getValueClassProperties(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeVariance getVariance(TypeArgumentMarker typeArgumentMarker) {
        Intrinsics.checkNotNullParameter(typeArgumentMarker, "<this>");
        return this.baseContext.getVariance(typeArgumentMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeVariance getVariance(TypeParameterMarker typeParameterMarker) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        return this.baseContext.getVariance(typeParameterMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public boolean hasAnnotation(KotlinTypeMarker kotlinTypeMarker, FqName fqName) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        return this.baseContext.hasAnnotation(kotlinTypeMarker, fqName);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean hasCustomAttributes(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.baseContext.hasCustomAttributes(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean hasFlexibleNullability(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.baseContext.hasFlexibleNullability(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean hasRecursiveBounds(TypeParameterMarker typeParameterMarker, TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        return this.baseContext.hasRecursiveBounds(typeParameterMarker, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemOptimizationContext
    public boolean identicalArguments(SimpleTypeMarker a, SimpleTypeMarker b) {
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        return this.baseContext.identicalArguments(a, b);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker intersectTypes(List<? extends KotlinTypeMarker> types) {
        Intrinsics.checkNotNullParameter(types, "types");
        return this.baseContext.intersectTypes(types);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    /* JADX INFO: renamed from: intersectTypes */
    public SimpleTypeMarker mo6107intersectTypes(List<? extends SimpleTypeMarker> types) {
        Intrinsics.checkNotNullParameter(types, "types");
        return this.baseContext.mo6107intersectTypes(types);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isAnonymous(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.baseContext.isAnonymous(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isAnyConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.baseContext.isAnyConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public boolean isArrayOrNullableArray(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.baseContext.isArrayOrNullableArray(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isCapturedDynamic(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.baseContext.isCapturedDynamic(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isCapturedType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.baseContext.isCapturedType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isClassType(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.baseContext.isClassType(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isClassTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.baseContext.isClassTypeConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isCommonFinalClassConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.baseContext.isCommonFinalClassConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isDefinitelyClassTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.baseContext.isDefinitelyClassTypeConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isDefinitelyNotNullType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.baseContext.isDefinitelyNotNullType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isDenotable(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.baseContext.isDenotable(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isDynamic(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.baseContext.isDynamic(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isError(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.baseContext.isError(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isError(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.baseContext.isError(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
    public boolean isExtensionFunction(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.baseContext.isExtensionFunction(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public boolean isFinalClassOrEnumEntryOrAnnotationClassConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.baseContext.isFinalClassOrEnumEntryOrAnnotationClassConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isFlexible(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.baseContext.isFlexible(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isFlexibleNothing(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.baseContext.isFlexibleNothing(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public boolean isInlineClass(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.baseContext.isInlineClass(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public boolean isInnerClass(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.baseContext.isInnerClass(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isIntegerConstantOperatorTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.baseContext.isIntegerConstantOperatorTypeConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isIntegerLiteralConstantTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.baseContext.isIntegerLiteralConstantTypeConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isIntegerLiteralType(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.baseContext.isIntegerLiteralType(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isIntegerLiteralTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.baseContext.isIntegerLiteralTypeConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isInterface(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.baseContext.isInterface(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public boolean isInterfaceOrAnnotationClass(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.baseContext.isInterfaceOrAnnotationClass(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isIntersection(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.baseContext.isIntersection(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isLocalType(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.baseContext.isLocalType(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isMarkedNullable(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.baseContext.isMarkedNullable(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isMarkedNullable(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.baseContext.isMarkedNullable(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public boolean isMultiFieldValueClass(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.baseContext.isMultiFieldValueClass(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    @ObsoleteTypeKind
    public boolean isNotNullTypeParameter(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.baseContext.isNotNullTypeParameter(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isNothing(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.baseContext.isNothing(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isNothingConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.baseContext.isNothingConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isNullableAny(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.baseContext.isNullableAny(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isNullableNothing(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.baseContext.isNullableNothing(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isNullableType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.baseContext.isNullableType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isOldCapturedType(CapturedTypeMarker capturedTypeMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
        return this.baseContext.isOldCapturedType(capturedTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isPrimitiveType(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.baseContext.isPrimitiveType(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isProjectionNotNull(CapturedTypeMarker capturedTypeMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
        return this.baseContext.isProjectionNotNull(capturedTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public boolean isReified(TypeParameterMarker typeParameterMarker) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        return this.baseContext.isReified(typeParameterMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isSimpleType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.baseContext.isSimpleType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isSingleClassifierType(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.baseContext.isSingleClassifierType(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isStarProjection(TypeArgumentMarker typeArgumentMarker) {
        Intrinsics.checkNotNullParameter(typeArgumentMarker, "<this>");
        return this.baseContext.isStarProjection(typeArgumentMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isStubType(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.baseContext.isStubType(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isStubTypeForBuilderInference(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.baseContext.isStubTypeForBuilderInference(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isStubTypeForVariableInSubtyping(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.baseContext.isStubTypeForVariableInSubtyping(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isTypeParameterTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.baseContext.isTypeParameterTypeConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isTypeVariableType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.baseContext.isTypeVariableType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public boolean isUnderKotlinPackage(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.baseContext.isUnderKotlinPackage(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isUninferredParameter(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.baseContext.isUninferredParameter(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public Iterator<TypeArgumentMarker> iterator(TypeArgumentListMarker typeArgumentListMarker) {
        Intrinsics.checkNotNullParameter(typeArgumentListMarker, "<this>");
        return this.baseContext.iterator(typeArgumentListMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker lowerBound(FlexibleTypeMarker flexibleTypeMarker) {
        Intrinsics.checkNotNullParameter(flexibleTypeMarker, "<this>");
        return this.baseContext.lowerBound(flexibleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker lowerBoundIfFlexible(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.baseContext.lowerBoundIfFlexible(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker lowerType(CapturedTypeMarker capturedTypeMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
        return this.baseContext.lowerType(capturedTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker makeDefinitelyNotNullOrNotNull(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.baseContext.makeDefinitelyNotNullOrNotNull(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public KotlinTypeMarker makeNullable(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.baseContext.makeNullable(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker makeSimpleTypeDefinitelyNotNullOrNotNull(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.baseContext.makeSimpleTypeDefinitelyNotNullOrNotNull(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeCheckerProviderContext
    public TypeCheckerState newTypeCheckerState(boolean errorTypesEqualToAnything, boolean stubTypesEqualToAnything) {
        return this.baseContext.newTypeCheckerState(errorTypesEqualToAnything, stubTypesEqualToAnything);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemBuiltInsContext
    public IrSimpleType nothingType() {
        return this.baseContext.nothingType();
    }

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext, org.jetbrains.kotlin.types.model.TypeSystemBuiltInsContext
    public IrSimpleType nullableAnyType() {
        return this.baseContext.nullableAnyType();
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemBuiltInsContext
    public IrSimpleType nullableNothingType() {
        return this.baseContext.nullableNothingType();
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker original(DefinitelyNotNullTypeMarker definitelyNotNullTypeMarker) {
        Intrinsics.checkNotNullParameter(definitelyNotNullTypeMarker, "<this>");
        return this.baseContext.original(definitelyNotNullTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker originalIfDefinitelyNotNullable(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.baseContext.originalIfDefinitelyNotNullable(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public int parametersCount(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.baseContext.parametersCount(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public Set<IrType> possibleIntegerTypes(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.baseContext.possibleIntegerTypes(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeArgumentMarker projection(CapturedTypeConstructorMarker capturedTypeConstructorMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeConstructorMarker, "<this>");
        return this.baseContext.projection(capturedTypeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
    public KotlinTypeMarker replaceCustomAttributes(KotlinTypeMarker kotlinTypeMarker, List<? extends AnnotationMarker> newAttributes) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        Intrinsics.checkNotNullParameter(newAttributes, "newAttributes");
        return this.baseContext.replaceCustomAttributes(kotlinTypeMarker, newAttributes);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeArgumentMarker replaceType(TypeArgumentMarker typeArgumentMarker, KotlinTypeMarker newType) {
        Intrinsics.checkNotNullParameter(typeArgumentMarker, "<this>");
        Intrinsics.checkNotNullParameter(newType, "newType");
        return this.baseContext.replaceType(typeArgumentMarker, newType);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker safeSubstitute(TypeSubstitutorMarker typeSubstitutorMarker, KotlinTypeMarker type) {
        Intrinsics.checkNotNullParameter(typeSubstitutorMarker, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        return this.baseContext.safeSubstitute(typeSubstitutorMarker, type);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public int size(TypeArgumentListMarker typeArgumentListMarker) {
        Intrinsics.checkNotNullParameter(typeArgumentListMarker, "<this>");
        return this.baseContext.size(typeArgumentListMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeCheckerState.SupertypesPolicy substitutionSupertypePolicy(SimpleTypeMarker type) {
        Intrinsics.checkNotNullParameter(type, "type");
        return this.baseContext.substitutionSupertypePolicy(type);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public Collection<KotlinTypeMarker> supertypes(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.baseContext.supertypes(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
    public SimpleTypeMarker toErrorType(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.baseContext.toErrorType(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public CapturedTypeConstructorMarker typeConstructor(CapturedTypeMarker capturedTypeMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
        return this.baseContext.typeConstructor(capturedTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeConstructorMarker typeConstructor(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.baseContext.typeConstructor(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeConstructorMarker typeConstructor(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.baseContext.typeConstructor(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
    public int typeDepth(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.baseContext.typeDepth(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
    public int typeDepth(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.baseContext.typeDepth(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeSubstitutorMarker typeSubstitutorByTypeConstructor(Map<TypeConstructorMarker, ? extends KotlinTypeMarker> map) {
        Intrinsics.checkNotNullParameter(map, "map");
        return this.baseContext.typeSubstitutorByTypeConstructor(map);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
    public List<AnnotationMarker> unionTypeAttributes(List<? extends KotlinTypeMarker> types) {
        Intrinsics.checkNotNullParameter(types, "types");
        return this.baseContext.unionTypeAttributes(types);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeConstructorMarker unwrapStubTypeVariableConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.baseContext.unwrapStubTypeVariableConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker upperBound(FlexibleTypeMarker flexibleTypeMarker) {
        Intrinsics.checkNotNullParameter(flexibleTypeMarker, "<this>");
        return this.baseContext.upperBound(flexibleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public int upperBoundCount(TypeParameterMarker typeParameterMarker) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        return this.baseContext.upperBoundCount(typeParameterMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker upperBoundIfFlexible(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.baseContext.upperBoundIfFlexible(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker withNullability(KotlinTypeMarker kotlinTypeMarker, boolean z) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.baseContext.withNullability(kotlinTypeMarker, z);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker withNullability(SimpleTypeMarker simpleTypeMarker, boolean z) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.baseContext.withNullability(simpleTypeMarker, z);
    }

    public IrTypeCheckerContextForTypeMapping(IrTypeSystemContext baseContext, JvmBackendContext backendContext) {
        Intrinsics.checkNotNullParameter(baseContext, "baseContext");
        Intrinsics.checkNotNullParameter(backendContext, "backendContext");
        this.baseContext = baseContext;
        this.backendContext = backendContext;
    }

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContextForTypeMapping
    public /* bridge */ /* synthetic */ SimpleTypeMarker typeWithArguments(TypeConstructorMarker typeConstructorMarker, List list) {
        return typeWithArguments(typeConstructorMarker, (List<? extends KotlinTypeMarker>) list);
    }

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContextForTypeMapping
    public boolean isTypeParameter(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return typeConstructorMarker instanceof IrTypeParameterSymbol;
    }

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContextForTypeMapping
    public TypeParameterMarker asTypeParameter(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        if (!isTypeParameter(typeConstructorMarker)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return (IrTypeParameterSymbol) typeConstructorMarker;
    }

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContextForTypeMapping
    public IrType defaultType(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        if (typeConstructorMarker instanceof IrClassSymbol) {
            return IrUtilsKt.getDefaultType(((IrClassSymbol) typeConstructorMarker).getOwner());
        }
        if (typeConstructorMarker instanceof IrTypeParameterSymbol) {
            return IrTypesKt.getDefaultType(((IrTypeParameterSymbol) typeConstructorMarker).getOwner());
        }
        throw new IllegalStateException(("Unsupported type constructor: " + typeConstructorMarker).toString());
    }

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContextForTypeMapping
    public boolean isScript(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return typeConstructorMarker instanceof IrScriptSymbol;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContextForTypeMapping
    public boolean isSuspendFunction(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        if (simpleTypeMarker instanceof IrSimpleType) {
            return IrTypeUtilsKt.isSuspendFunction((IrType) simpleTypeMarker);
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContextForTypeMapping
    public boolean isKClass(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        if (!(simpleTypeMarker instanceof IrSimpleType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return IrTypePredicatesKt.isKClass((IrType) simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isRawType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        if (!(kotlinTypeMarker instanceof IrType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        if (kotlinTypeMarker instanceof IrSimpleType) {
            return JvmIrUtilsKt.isRawType((IrSimpleType) kotlinTypeMarker);
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContextForTypeMapping
    public IrSimpleType typeWithArguments(TypeConstructorMarker typeConstructorMarker, List<? extends KotlinTypeMarker> arguments) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        if (!(typeConstructorMarker instanceof IrClassSymbol)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        Iterator<T> it = arguments.iterator();
        while (it.hasNext()) {
            if (!(((KotlinTypeMarker) it.next()) instanceof IrType)) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
        }
        return IrTypesKt.typeWith((IrClassifierSymbol) typeConstructorMarker, (List<? extends IrType>) arguments);
    }

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContextForTypeMapping
    public IrType representativeUpperBound(TypeParameterMarker typeParameterMarker) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        if (!(typeParameterMarker instanceof IrTypeParameterSymbol)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return JvmIrTypeUtilsKt.getRepresentativeUpperBound(((IrTypeParameterSymbol) typeParameterMarker).getOwner());
    }

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContextForTypeMapping
    public IrClassSymbol continuationTypeConstructor() {
        return this.backendContext.getIr().getSymbols().getContinuationClass();
    }

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContextForTypeMapping
    public IrClassSymbol functionNTypeConstructor(int n) {
        return this.backendContext.getIrBuiltIns().functionN(n).getSymbol();
    }
}
