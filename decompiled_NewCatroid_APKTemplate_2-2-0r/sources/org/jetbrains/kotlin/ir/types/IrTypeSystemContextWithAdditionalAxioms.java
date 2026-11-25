package org.jetbrains.kotlin.ir.types;

import androidx.core.app.NotificationCompat;
import com.reandroid.arsc.container.SpecTypePair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.builtins.PrimitiveType;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.types.impl.IrStarProjectionImpl;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNameUnsafe;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.TypeCheckerState;
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

/* JADX INFO: compiled from: IrTypeCheckerUtils.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000´\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b8\n\u0002\u0010(\n\u0002\b\t\n\u0002\u0010\"\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u001e\n\u0002\b\b\u0018\u00002\u00020\u0001B)\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\u0002\u0010\u0007J\t\u0010\u0016\u001a\u00020\u0017H\u0096\u0001J\u0018\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001bH\u0016J\u0011\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 H\u0096\u0001J\u001b\u0010!\u001a\u0004\u0018\u00010\u001e2\u0006\u0010\"\u001a\u00020\u001e2\u0006\u0010#\u001a\u00020$H\u0096\u0001J\u0013\u0010%\u001a\u0004\u0018\u00010 2\u0006\u0010\"\u001a\u00020 H\u0096\u0001J\t\u0010&\u001a\u00020'H\u0096\u0001J\u0011\u0010(\u001a\u00020\u001e2\u0006\u0010)\u001a\u00020*H\u0096\u0001J\u0019\u0010+\u001a\u00020 2\u0006\u0010,\u001a\u00020\u001e2\u0006\u0010-\u001a\u00020\u001eH\u0096\u0001J?\u0010.\u001a\u00020\u001e2\u0006\u0010/\u001a\u00020\u001b2\f\u00100\u001a\b\u0012\u0004\u0012\u0002010\u00042\u0006\u00102\u001a\u00020\u00192\u0006\u00103\u001a\u00020\u00192\u000e\u00104\u001a\n\u0012\u0004\u0012\u000205\u0018\u00010\u0004H\u0096\u0001J\u0011\u00106\u001a\u0002072\u0006\u0010\u0011\u001a\u00020\u0012H\u0096\u0001J\u0019\u00108\u001a\u0002012\u0006\u0010\"\u001a\u00020 2\u0006\u00109\u001a\u00020:H\u0096\u0001J\u0011\u0010;\u001a\u00020 2\u0006\u0010/\u001a\u00020\u001bH\u0096\u0001J\u0019\u0010<\u001a\u0004\u0018\u00010\u001e2\f\u0010=\u001a\b\u0012\u0004\u0012\u00020\u001e0\u0004H\u0096\u0001J\u0019\u0010>\u001a\u00020\u00192\u0006\u0010?\u001a\u00020\u001e2\u0006\u0010@\u001a\u00020\u001eH\u0096\u0001J\u0017\u0010A\u001a\u00020 2\f\u0010B\u001a\b\u0012\u0004\u0012\u00020 0\u0004H\u0096\u0001J\u0017\u0010A\u001a\u00020\u001e2\f\u0010B\u001a\b\u0012\u0004\u0012\u00020\u001e0\u0004H\u0096\u0001J\u0019\u0010C\u001a\u00020D2\u0006\u0010E\u001a\u00020\u00192\u0006\u0010F\u001a\u00020\u0019H\u0096\u0001J\t\u0010G\u001a\u00020\u0017H\u0096\u0001J\t\u0010H\u001a\u00020\u0017H\u0096\u0001J\t\u0010I\u001a\u00020\u0017H\u0096\u0001J\u0011\u0010J\u001a\u00020K2\u0006\u0010\"\u001a\u00020\u001eH\u0096\u0001J\u001d\u0010L\u001a\u00020'2\u0012\u0010M\u001a\u000e\u0012\u0004\u0012\u00020\u001b\u0012\u0004\u0012\u00020 0NH\u0096\u0001J\u001d\u0010O\u001a\b\u0012\u0004\u0012\u0002050\u00042\f\u0010B\u001a\b\u0012\u0004\u0012\u00020 0\u0004H\u0096\u0001J!\u0010P\u001a\u00020\u0019*\u00020 2\u0012\u0010Q\u001a\u000e\u0012\u0004\u0012\u00020\u001e\u0012\u0004\u0012\u00020\u00190RH\u0096\u0001J\r\u0010S\u001a\u00020T*\u00020 H\u0096\u0001J\r\u0010U\u001a\u00020\u0017*\u00020\u001eH\u0096\u0001J\u000f\u0010V\u001a\u0004\u0018\u00010W*\u00020\u001eH\u0096\u0001J\u000f\u0010X\u001a\u0004\u0018\u00010Y*\u00020\u001eH\u0096\u0001J\u000f\u0010Z\u001a\u0004\u0018\u00010[*\u00020\\H\u0096\u0001J\u000f\u0010]\u001a\u0004\u0018\u00010\\*\u00020 H\u0096\u0001J\u000f\u0010^\u001a\u0004\u0018\u00010\u001e*\u00020 H\u0096\u0001J\r\u0010_\u001a\u00020`*\u00020 H\u0096\u0001J\r\u0010a\u001a\u00020\u0019*\u00020 H\u0096\u0001J\r\u0010b\u001a\u00020$*\u00020WH\u0096\u0001J\u001d\u0010c\u001a\n\u0012\u0004\u0012\u00020\u001e\u0018\u00010\u0004*\u00020\u001e2\u0006\u0010/\u001a\u00020\u001bH\u0096\u0001J\u0015\u0010d\u001a\u000201*\u00020e2\u0006\u0010f\u001a\u00020TH\u0096\u0003J\u0017\u0010g\u001a\u0004\u0018\u00010h*\u00020 2\u0006\u0010i\u001a\u00020jH\u0096\u0001J\u0015\u0010k\u001a\u000201*\u00020 2\u0006\u0010f\u001a\u00020TH\u0096\u0001J\u0017\u0010l\u001a\u0004\u0018\u000101*\u00020\u001e2\u0006\u0010f\u001a\u00020TH\u0096\u0001J\u0013\u0010m\u001a\b\u0012\u0004\u0012\u0002010\u0004*\u00020 H\u0096\u0001J\u0013\u0010n\u001a\b\u0012\u0004\u0012\u0002050\u0004*\u00020 H\u0096\u0001J\u000f\u0010o\u001a\u0004\u0018\u00010p*\u00020\u001bH\u0096\u0001J\u0013\u0010q\u001a\b\u0012\u0004\u0012\u0002050\u0004*\u00020 H\u0096\u0001J\r\u0010r\u001a\u00020s*\u00020\u0012H\u0096\u0001J\u0015\u0010t\u001a\u00020\t*\u00020\u001b2\u0006\u0010f\u001a\u00020TH\u0096\u0001J\u0013\u0010u\u001a\b\u0012\u0004\u0012\u00020\t0\u0004*\u00020\u001bH\u0096\u0001J\u000f\u0010v\u001a\u0004\u0018\u00010w*\u00020\u001bH\u0096\u0001J\u000f\u0010x\u001a\u0004\u0018\u00010w*\u00020\u001bH\u0096\u0001J\r\u0010y\u001a\u00020 *\u00020\u0012H\u0096\u0001J\u000f\u0010z\u001a\u0004\u0018\u00010 *\u00020 H\u0096\u0001J\r\u0010{\u001a\u00020|*\u000201H\u0096\u0001J\r\u0010}\u001a\u00020\t*\u00020\u0012H\u0096\u0001J\u000f\u0010~\u001a\u0004\u0018\u00010\u0012*\u00020\u001bH\u0096\u0001J\u000f\u0010\u007f\u001a\u0004\u0018\u00010 *\u00020 H\u0096\u0001J\u0016\u0010\u0080\u0001\u001a\u00020 *\u00020\u00122\u0006\u0010f\u001a\u00020TH\u0096\u0001J\u0014\u0010\u0081\u0001\u001a\b\u0012\u0004\u0012\u00020 0\u0004*\u00020\u0012H\u0096\u0001J#\u0010\u0082\u0001\u001a\u0017\u0012\u0011\u0012\u000f\u0012\u0004\u0012\u00020s\u0012\u0004\u0012\u00020\u001e0\u0083\u0001\u0018\u00010\u0004*\u00020\u001bH\u0096\u0001J\u000e\u0010\u0084\u0001\u001a\u00020:*\u000201H\u0096\u0001J\u000e\u0010\u0084\u0001\u001a\u00020:*\u00020\u0012H\u0096\u0001J\u0016\u0010\u0085\u0001\u001a\u00020\u0019*\u00020 2\u0006\u0010i\u001a\u00020jH\u0096\u0001J\u000e\u0010\u0086\u0001\u001a\u00020\u0019*\u00020 H\u0096\u0001J\u000e\u0010\u0087\u0001\u001a\u00020\u0019*\u00020 H\u0096\u0001J\u0019\u0010\u0088\u0001\u001a\u00020\u0019*\u00020\u00122\t\u0010\u0089\u0001\u001a\u0004\u0018\u00010\u001bH\u0096\u0001J\u000e\u0010\u008a\u0001\u001a\u00020\u0019*\u00020\u001bH\u0096\u0001J\u000e\u0010\u008b\u0001\u001a\u00020\u0019*\u00020\u001bH\u0096\u0001J\u000e\u0010\u008c\u0001\u001a\u00020\u0019*\u00020 H\u0096\u0001J\u000e\u0010\u008d\u0001\u001a\u00020\u0019*\u00020 H\u0096\u0001J\u000e\u0010\u008e\u0001\u001a\u00020\u0019*\u00020 H\u0096\u0001J\u000e\u0010\u008f\u0001\u001a\u00020\u0019*\u00020\u001eH\u0096\u0001J\u000e\u0010\u0090\u0001\u001a\u00020\u0019*\u00020\u001bH\u0096\u0001J\u000e\u0010\u0091\u0001\u001a\u00020\u0019*\u00020\u001bH\u0096\u0001J\u000e\u0010\u0092\u0001\u001a\u00020\u0019*\u00020\u001bH\u0096\u0001J\u000e\u0010\u0093\u0001\u001a\u00020\u0019*\u00020 H\u0096\u0001J\u000e\u0010\u0094\u0001\u001a\u00020\u0019*\u00020\u001bH\u0096\u0001J\u000e\u0010\u0095\u0001\u001a\u00020\u0019*\u00020 H\u0096\u0001J\u000e\u0010\u0096\u0001\u001a\u00020\u0019*\u00020 H\u0096\u0001J\u000e\u0010\u0096\u0001\u001a\u00020\u0019*\u00020\u001bH\u0096\u0001J\r\u00103\u001a\u00020\u0019*\u00020\u001eH\u0096\u0001J\u000e\u0010\u0097\u0001\u001a\u00020\u0019*\u00020\u001bH\u0096\u0001J\u000e\u0010\u0098\u0001\u001a\u00020\u0019*\u00020 H\u0096\u0001J\u000e\u0010\u0099\u0001\u001a\u00020\u0019*\u00020 H\u0096\u0001J\u000e\u0010\u009a\u0001\u001a\u00020\u0019*\u00020\u001bH\u0096\u0001J\u000e\u0010\u009b\u0001\u001a\u00020\u0019*\u00020\u001bH\u0096\u0001J\u000e\u0010\u009c\u0001\u001a\u00020\u0019*\u00020\u001bH\u0096\u0001J\u000e\u0010\u009d\u0001\u001a\u00020\u0019*\u00020\u001bH\u0096\u0001J\u000e\u0010\u009e\u0001\u001a\u00020\u0019*\u00020\u001eH\u0096\u0001J\u000e\u0010\u009f\u0001\u001a\u00020\u0019*\u00020\u001bH\u0096\u0001J\u000e\u0010 \u0001\u001a\u00020\u0019*\u00020\u001bH\u0096\u0001J\u000e\u0010¡\u0001\u001a\u00020\u0019*\u00020 H\u0096\u0001J\u000e\u0010¢\u0001\u001a\u00020\u0019*\u00020\u001bH\u0096\u0001J\u000e\u0010£\u0001\u001a\u00020\u0019*\u00020\u001bH\u0096\u0001J\u000e\u0010¤\u0001\u001a\u00020\u0019*\u00020 H\u0096\u0001J\u000e\u0010¤\u0001\u001a\u00020\u0019*\u00020\u001eH\u0096\u0001J\u000e\u0010¥\u0001\u001a\u00020\u0019*\u00020\u001bH\u0096\u0001J\u000e\u0010¦\u0001\u001a\u00020\u0019*\u00020 H\u0097\u0001J\u000e\u0010§\u0001\u001a\u00020\u0019*\u00020 H\u0096\u0001J\u000e\u0010¨\u0001\u001a\u00020\u0019*\u00020\u001bH\u0096\u0001J\u000e\u0010©\u0001\u001a\u00020\u0019*\u00020 H\u0096\u0001J\u000e\u0010ª\u0001\u001a\u00020\u0019*\u00020 H\u0096\u0001J\u000e\u0010«\u0001\u001a\u00020\u0019*\u00020 H\u0096\u0001J\u000e\u0010¬\u0001\u001a\u00020\u0019*\u00020WH\u0096\u0001J\u000e\u0010\u00ad\u0001\u001a\u00020\u0019*\u00020\u001eH\u0096\u0001J\u000e\u0010®\u0001\u001a\u00020\u0019*\u00020WH\u0096\u0001J\u000e\u0010¯\u0001\u001a\u00020\u0019*\u00020 H\u0096\u0001J\u000e\u0010°\u0001\u001a\u00020\u0019*\u00020\u0012H\u0096\u0001J\u000e\u0010±\u0001\u001a\u00020\u0019*\u00020 H\u0096\u0001J\u000e\u0010²\u0001\u001a\u00020\u0019*\u00020\u001eH\u0096\u0001J\u000e\u0010³\u0001\u001a\u00020\u0019*\u000201H\u0096\u0001J\u000e\u0010´\u0001\u001a\u00020\u0019*\u00020\u001eH\u0096\u0001J\u000e\u0010µ\u0001\u001a\u00020\u0019*\u00020\u001eH\u0096\u0001J\u000e\u0010¶\u0001\u001a\u00020\u0019*\u00020\u001eH\u0096\u0001J\u000e\u0010·\u0001\u001a\u00020\u0019*\u00020\u001bH\u0096\u0001J\u000e\u0010¸\u0001\u001a\u00020\u0019*\u00020 H\u0096\u0001J\u000e\u0010¹\u0001\u001a\u00020\u0019*\u00020\u001bH\u0096\u0001J\u000e\u0010º\u0001\u001a\u00020\u0019*\u00020 H\u0096\u0001J\u0015\u0010»\u0001\u001a\t\u0012\u0004\u0012\u0002010¼\u0001*\u00020eH\u0096\u0003J\r\u0010,\u001a\u00020\u001e*\u00020\\H\u0096\u0001J\u000e\u0010½\u0001\u001a\u00020\u001e*\u00020 H\u0096\u0001J\u0010\u0010¾\u0001\u001a\u0004\u0018\u00010 *\u00020WH\u0096\u0001J\u000e\u0010¿\u0001\u001a\u00020 *\u00020 H\u0096\u0001J\u000e\u0010À\u0001\u001a\u00020 *\u00020 H\u0096\u0001J\u000e\u0010Á\u0001\u001a\u00020\u001e*\u00020\u001eH\u0096\u0001J\u000e\u0010Â\u0001\u001a\u00020\u001e*\u00020YH\u0096\u0001J\u000e\u0010Ã\u0001\u001a\u00020\u001e*\u00020\u001eH\u0096\u0001J\u000e\u0010Ä\u0001\u001a\u00020T*\u00020\u001bH\u0096\u0001J\u0015\u0010Å\u0001\u001a\t\u0012\u0004\u0012\u00020|0Æ\u0001*\u00020\u001eH\u0096\u0001J\u000f\u0010Ç\u0001\u001a\u000201*\u00030È\u0001H\u0096\u0001J\u001d\u0010É\u0001\u001a\u00020 *\u00020 2\r\u0010Ê\u0001\u001a\b\u0012\u0004\u0012\u0002050\u0004H\u0096\u0001J\u0017\u0010Ë\u0001\u001a\u000201*\u0002012\u0007\u0010Ì\u0001\u001a\u00020 H\u0096\u0001J\u0016\u0010Í\u0001\u001a\u00020 *\u00020'2\u0006\u0010\"\u001a\u00020 H\u0096\u0001J\u000e\u0010Î\u0001\u001a\u00020T*\u00020eH\u0096\u0001J\u0015\u0010Ï\u0001\u001a\t\u0012\u0004\u0012\u00020 0Ð\u0001*\u00020\u001bH\u0096\u0001J\u000e\u0010Ñ\u0001\u001a\u00020\u001e*\u00020\u001bH\u0096\u0001J\u000f\u0010Ò\u0001\u001a\u00030È\u0001*\u00020WH\u0096\u0001J\u000e\u0010Ò\u0001\u001a\u00020\u001b*\u00020 H\u0096\u0001J\u000e\u0010Ò\u0001\u001a\u00020\u001b*\u00020\u001eH\u0096\u0001J\u000e\u0010Ó\u0001\u001a\u00020T*\u00020 H\u0096\u0001J\u000e\u0010Ó\u0001\u001a\u00020T*\u00020\u001eH\u0096\u0001J\u000e\u0010Ô\u0001\u001a\u00020\u001b*\u00020\u001bH\u0096\u0001J\r\u0010-\u001a\u00020\u001e*\u00020\\H\u0096\u0001J\u000e\u0010Õ\u0001\u001a\u00020T*\u00020\u0012H\u0096\u0001J\u000e\u0010Ö\u0001\u001a\u00020\u001e*\u00020 H\u0096\u0001J\u0016\u0010×\u0001\u001a\u00020 *\u00020 2\u0006\u00102\u001a\u00020\u0019H\u0096\u0001J\u0016\u0010×\u0001\u001a\u00020\u001e*\u00020\u001e2\u0006\u00102\u001a\u00020\u0019H\u0096\u0001R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\n\u001a\u00020\u000bX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\t0\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\t0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0011\u001a\u0004\u0018\u00010\u0012*\u00020\u00138VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015¨\u0006Ø\u0001"}, d2 = {"Lorg/jetbrains/kotlin/ir/types/IrTypeSystemContextWithAdditionalAxioms;", "Lorg/jetbrains/kotlin/ir/types/IrTypeSystemContext;", "typeSystem", "firstParameters", "", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "secondParameters", "(Lorg/jetbrains/kotlin/ir/types/IrTypeSystemContext;Ljava/util/List;Ljava/util/List;)V", "firstTypeParameterConstructors", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeParameterSymbol;", "irBuiltIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "getIrBuiltIns", "()Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "matchingTypeConstructors", "Ljava/util/HashMap;", "secondTypeParameterConstructors", "typeParameter", "Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;", "Lorg/jetbrains/kotlin/types/model/TypeVariableTypeConstructorMarker;", "getTypeParameter", "(Lorg/jetbrains/kotlin/types/model/TypeVariableTypeConstructorMarker;)Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;", SchemaSymbols.ATTVAL_ANYTYPE, "Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "areEqualTypeConstructors", "", "c1", "Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;", "c2", "arrayType", "Lorg/jetbrains/kotlin/types/model/SimpleTypeMarker;", "componentType", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "captureFromArguments", ModuleXmlParser.TYPE, NotificationCompat.CATEGORY_STATUS, "Lorg/jetbrains/kotlin/types/model/CaptureStatus;", "captureFromExpression", "createEmptySubstitutor", "Lorg/jetbrains/kotlin/types/model/TypeSubstitutorMarker;", "createErrorType", "debugName", "", "createFlexibleType", "lowerBound", "upperBound", "createSimpleType", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "arguments", "Lorg/jetbrains/kotlin/types/model/TypeArgumentMarker;", "nullable", "isExtensionFunction", "attributes", "Lorg/jetbrains/kotlin/types/model/AnnotationMarker;", "createStarProjection", "Lorg/jetbrains/kotlin/ir/types/impl/IrStarProjectionImpl;", "createTypeArgument", "variance", "Lorg/jetbrains/kotlin/types/model/TypeVariance;", "createUninferredType", "findCommonIntegerLiteralTypesSuperType", "explicitSupertypes", "identicalArguments", "a", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "intersectTypes", SpecTypePair.NAME_types, "newTypeCheckerState", "Lorg/jetbrains/kotlin/types/TypeCheckerState;", "errorTypesEqualToAnything", "stubTypesEqualToAnything", "nothingType", "nullableAnyType", "nullableNothingType", "substitutionSupertypePolicy", "Lorg/jetbrains/kotlin/types/TypeCheckerState$SupertypesPolicy;", "typeSubstitutorByTypeConstructor", "map", "", "unionTypeAttributes", "anySuperTypeConstructor", "predicate", "Lkotlin/Function1;", "argumentsCount", "", "asArgumentList", "asCapturedType", "Lorg/jetbrains/kotlin/types/model/CapturedTypeMarker;", "asDefinitelyNotNullType", "Lorg/jetbrains/kotlin/types/model/DefinitelyNotNullTypeMarker;", "asDynamicType", "Lorg/jetbrains/kotlin/ir/types/IrDynamicType;", "Lorg/jetbrains/kotlin/types/model/FlexibleTypeMarker;", "asFlexibleType", "asSimpleType", "asTypeArgument", "Lorg/jetbrains/kotlin/ir/types/IrTypeArgument;", "canHaveUndefinedNullability", "captureStatus", "fastCorrespondingSupertypes", "get", "Lorg/jetbrains/kotlin/types/model/TypeArgumentListMarker;", "index", "getAnnotationFirstArgumentValue", "", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "getArgument", "getArgumentOrNull", "getArguments", "getAttributes", "getClassFqNameUnsafe", "Lorg/jetbrains/kotlin/name/FqNameUnsafe;", "getCustomAttributes", "getName", "Lorg/jetbrains/kotlin/name/Name;", "getParameter", "getParameters", "getPrimitiveArrayType", "Lorg/jetbrains/kotlin/builtins/PrimitiveType;", "getPrimitiveType", "getRepresentativeUpperBound", "getSubstitutedUnderlyingType", "getType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "getTypeConstructor", "getTypeParameterClassifier", "getUnsubstitutedUnderlyingType", "getUpperBound", "getUpperBounds", "getValueClassProperties", "Lkotlin/Pair;", "getVariance", "hasAnnotation", "hasCustomAttributes", "hasFlexibleNullability", "hasRecursiveBounds", "selfConstructor", "isAnonymous", "isAnyConstructor", "isArrayOrNullableArray", "isCapturedDynamic", "isCapturedType", "isClassType", "isClassTypeConstructor", "isCommonFinalClassConstructor", "isDefinitelyClassTypeConstructor", "isDefinitelyNotNullType", "isDenotable", "isDynamic", "isError", "isFinalClassOrEnumEntryOrAnnotationClassConstructor", "isFlexible", "isFlexibleNothing", "isInlineClass", "isInnerClass", "isIntegerConstantOperatorTypeConstructor", "isIntegerLiteralConstantTypeConstructor", "isIntegerLiteralType", "isIntegerLiteralTypeConstructor", "isInterface", "isInterfaceOrAnnotationClass", "isIntersection", "isLocalType", "isMarkedNullable", "isMultiFieldValueClass", "isNotNullTypeParameter", "isNothing", "isNothingConstructor", "isNullableAny", "isNullableNothing", "isNullableType", "isOldCapturedType", "isPrimitiveType", "isProjectionNotNull", "isRawType", "isReified", "isSimpleType", "isSingleClassifierType", "isStarProjection", "isStubType", "isStubTypeForBuilderInference", "isStubTypeForVariableInSubtyping", "isTypeParameterTypeConstructor", "isTypeVariableType", "isUnderKotlinPackage", "isUninferredParameter", "iterator", "", "lowerBoundIfFlexible", "lowerType", "makeDefinitelyNotNullOrNotNull", "makeNullable", "makeSimpleTypeDefinitelyNotNullOrNotNull", "original", "originalIfDefinitelyNotNullable", "parametersCount", "possibleIntegerTypes", "", "projection", "Lorg/jetbrains/kotlin/types/model/CapturedTypeConstructorMarker;", "replaceCustomAttributes", "newAttributes", "replaceType", "newType", "safeSubstitute", "size", "supertypes", "", "toErrorType", "typeConstructor", "typeDepth", "unwrapStubTypeVariableConstructor", "upperBoundCount", "upperBoundIfFlexible", "withNullability", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrTypeSystemContextWithAdditionalAxioms implements IrTypeSystemContext {
    private final /* synthetic */ IrTypeSystemContext $$delegate_0;
    private final List<IrTypeParameterSymbol> firstTypeParameterConstructors;
    private final HashMap<IrTypeParameterSymbol, IrTypeParameterSymbol> matchingTypeConstructors;
    private final List<IrTypeParameterSymbol> secondTypeParameterConstructors;

    @Override // org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
    public boolean anySuperTypeConstructor(KotlinTypeMarker kotlinTypeMarker, Function1<? super SimpleTypeMarker, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        return this.$$delegate_0.anySuperTypeConstructor(kotlinTypeMarker, predicate);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemBuiltInsContext
    public IrSimpleType anyType() {
        return this.$$delegate_0.anyType();
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public int argumentsCount(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.argumentsCount(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public SimpleTypeMarker arrayType(KotlinTypeMarker componentType) {
        Intrinsics.checkNotNullParameter(componentType, "componentType");
        return this.$$delegate_0.arrayType(componentType);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public IrSimpleType asArgumentList(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.asArgumentList(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public CapturedTypeMarker asCapturedType(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.asCapturedType(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public DefinitelyNotNullTypeMarker asDefinitelyNotNullType(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.asDefinitelyNotNullType(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public IrDynamicType asDynamicType(FlexibleTypeMarker flexibleTypeMarker) {
        Intrinsics.checkNotNullParameter(flexibleTypeMarker, "<this>");
        return this.$$delegate_0.asDynamicType(flexibleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public FlexibleTypeMarker asFlexibleType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.asFlexibleType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker asSimpleType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.asSimpleType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public IrTypeArgument asTypeArgument(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.asTypeArgument(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
    public boolean canHaveUndefinedNullability(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.canHaveUndefinedNullability(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker captureFromArguments(SimpleTypeMarker type, CaptureStatus status) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(status, "status");
        return this.$$delegate_0.captureFromArguments(type, status);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker captureFromExpression(KotlinTypeMarker type) {
        Intrinsics.checkNotNullParameter(type, "type");
        return this.$$delegate_0.captureFromExpression(type);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public CaptureStatus captureStatus(CapturedTypeMarker capturedTypeMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
        return this.$$delegate_0.captureStatus(capturedTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeSubstitutorMarker createEmptySubstitutor() {
        return this.$$delegate_0.createEmptySubstitutor();
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemTypeFactoryContext
    public SimpleTypeMarker createErrorType(String debugName) {
        Intrinsics.checkNotNullParameter(debugName, "debugName");
        return this.$$delegate_0.createErrorType(debugName);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemTypeFactoryContext
    public KotlinTypeMarker createFlexibleType(SimpleTypeMarker lowerBound, SimpleTypeMarker upperBound) {
        Intrinsics.checkNotNullParameter(lowerBound, "lowerBound");
        Intrinsics.checkNotNullParameter(upperBound, "upperBound");
        return this.$$delegate_0.createFlexibleType(lowerBound, upperBound);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemTypeFactoryContext
    public SimpleTypeMarker createSimpleType(TypeConstructorMarker constructor, List<? extends TypeArgumentMarker> arguments, boolean nullable, boolean isExtensionFunction, List<? extends AnnotationMarker> attributes) {
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        return this.$$delegate_0.createSimpleType(constructor, arguments, nullable, isExtensionFunction, attributes);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemTypeFactoryContext
    public IrStarProjectionImpl createStarProjection(TypeParameterMarker typeParameter) {
        Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
        return this.$$delegate_0.createStarProjection(typeParameter);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemTypeFactoryContext
    public TypeArgumentMarker createTypeArgument(KotlinTypeMarker type, TypeVariance variance) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(variance, "variance");
        return this.$$delegate_0.createTypeArgument(type, variance);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemTypeFactoryContext
    public KotlinTypeMarker createUninferredType(TypeConstructorMarker constructor) {
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        return this.$$delegate_0.createUninferredType(constructor);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public List<SimpleTypeMarker> fastCorrespondingSupertypes(SimpleTypeMarker simpleTypeMarker, TypeConstructorMarker constructor) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        return this.$$delegate_0.fastCorrespondingSupertypes(simpleTypeMarker, constructor);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
    public SimpleTypeMarker findCommonIntegerLiteralTypesSuperType(List<? extends SimpleTypeMarker> explicitSupertypes) {
        Intrinsics.checkNotNullParameter(explicitSupertypes, "explicitSupertypes");
        return this.$$delegate_0.findCommonIntegerLiteralTypesSuperType(explicitSupertypes);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeArgumentMarker get(TypeArgumentListMarker typeArgumentListMarker, int i) {
        Intrinsics.checkNotNullParameter(typeArgumentListMarker, "<this>");
        return this.$$delegate_0.get(typeArgumentListMarker, i);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public Object getAnnotationFirstArgumentValue(KotlinTypeMarker kotlinTypeMarker, FqName fqName) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        return this.$$delegate_0.getAnnotationFirstArgumentValue(kotlinTypeMarker, fqName);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeArgumentMarker getArgument(KotlinTypeMarker kotlinTypeMarker, int i) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.getArgument(kotlinTypeMarker, i);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeArgumentMarker getArgumentOrNull(SimpleTypeMarker simpleTypeMarker, int i) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.getArgumentOrNull(simpleTypeMarker, i);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public List<TypeArgumentMarker> getArguments(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.getArguments(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public List<AnnotationMarker> getAttributes(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.getAttributes(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public FqNameUnsafe getClassFqNameUnsafe(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.getClassFqNameUnsafe(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public List<AnnotationMarker> getCustomAttributes(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.getCustomAttributes(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext
    public IrBuiltIns getIrBuiltIns() {
        return this.$$delegate_0.getIrBuiltIns();
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public Name getName(TypeParameterMarker typeParameterMarker) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        return this.$$delegate_0.getName(typeParameterMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public IrTypeParameterSymbol getParameter(TypeConstructorMarker typeConstructorMarker, int i) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.getParameter(typeConstructorMarker, i);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public List<IrTypeParameterSymbol> getParameters(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.getParameters(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public PrimitiveType getPrimitiveArrayType(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.getPrimitiveArrayType(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public PrimitiveType getPrimitiveType(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.getPrimitiveType(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public KotlinTypeMarker getRepresentativeUpperBound(TypeParameterMarker typeParameterMarker) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        return this.$$delegate_0.getRepresentativeUpperBound(typeParameterMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public KotlinTypeMarker getSubstitutedUnderlyingType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.getSubstitutedUnderlyingType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public IrType getType(TypeArgumentMarker typeArgumentMarker) {
        Intrinsics.checkNotNullParameter(typeArgumentMarker, "<this>");
        return this.$$delegate_0.getType(typeArgumentMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public IrTypeParameterSymbol getTypeConstructor(TypeParameterMarker typeParameterMarker) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        return this.$$delegate_0.getTypeConstructor(typeParameterMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeParameterMarker getTypeParameter(TypeVariableTypeConstructorMarker typeVariableTypeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeVariableTypeConstructorMarker, "<this>");
        return this.$$delegate_0.getTypeParameter(typeVariableTypeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeParameterMarker getTypeParameterClassifier(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.getTypeParameterClassifier(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public KotlinTypeMarker getUnsubstitutedUnderlyingType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.getUnsubstitutedUnderlyingType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker getUpperBound(TypeParameterMarker typeParameterMarker, int i) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        return this.$$delegate_0.getUpperBound(typeParameterMarker, i);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public List<KotlinTypeMarker> getUpperBounds(TypeParameterMarker typeParameterMarker) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        return this.$$delegate_0.getUpperBounds(typeParameterMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public List<Pair<Name, SimpleTypeMarker>> getValueClassProperties(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.getValueClassProperties(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeVariance getVariance(TypeArgumentMarker typeArgumentMarker) {
        Intrinsics.checkNotNullParameter(typeArgumentMarker, "<this>");
        return this.$$delegate_0.getVariance(typeArgumentMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeVariance getVariance(TypeParameterMarker typeParameterMarker) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        return this.$$delegate_0.getVariance(typeParameterMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public boolean hasAnnotation(KotlinTypeMarker kotlinTypeMarker, FqName fqName) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        return this.$$delegate_0.hasAnnotation(kotlinTypeMarker, fqName);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean hasCustomAttributes(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.hasCustomAttributes(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean hasFlexibleNullability(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.hasFlexibleNullability(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean hasRecursiveBounds(TypeParameterMarker typeParameterMarker, TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        return this.$$delegate_0.hasRecursiveBounds(typeParameterMarker, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemOptimizationContext
    public boolean identicalArguments(SimpleTypeMarker a, SimpleTypeMarker b) {
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        return this.$$delegate_0.identicalArguments(a, b);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker intersectTypes(List<? extends KotlinTypeMarker> types) {
        Intrinsics.checkNotNullParameter(types, "types");
        return this.$$delegate_0.intersectTypes(types);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    /* JADX INFO: renamed from: intersectTypes */
    public SimpleTypeMarker mo6107intersectTypes(List<? extends SimpleTypeMarker> types) {
        Intrinsics.checkNotNullParameter(types, "types");
        return this.$$delegate_0.mo6107intersectTypes(types);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isAnonymous(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.isAnonymous(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isAnyConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.isAnyConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public boolean isArrayOrNullableArray(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.isArrayOrNullableArray(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isCapturedDynamic(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.isCapturedDynamic(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isCapturedType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.isCapturedType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isClassType(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.isClassType(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isClassTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.isClassTypeConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isCommonFinalClassConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.isCommonFinalClassConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isDefinitelyClassTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.isDefinitelyClassTypeConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isDefinitelyNotNullType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.isDefinitelyNotNullType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isDenotable(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.isDenotable(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isDynamic(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.isDynamic(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isError(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.isError(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isError(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.isError(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
    public boolean isExtensionFunction(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.isExtensionFunction(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public boolean isFinalClassOrEnumEntryOrAnnotationClassConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.isFinalClassOrEnumEntryOrAnnotationClassConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isFlexible(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.isFlexible(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isFlexibleNothing(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.isFlexibleNothing(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public boolean isInlineClass(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.isInlineClass(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public boolean isInnerClass(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.isInnerClass(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isIntegerConstantOperatorTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.isIntegerConstantOperatorTypeConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isIntegerLiteralConstantTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.isIntegerLiteralConstantTypeConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isIntegerLiteralType(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.isIntegerLiteralType(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isIntegerLiteralTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.isIntegerLiteralTypeConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isInterface(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.isInterface(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public boolean isInterfaceOrAnnotationClass(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.isInterfaceOrAnnotationClass(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isIntersection(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.isIntersection(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isLocalType(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.isLocalType(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isMarkedNullable(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.isMarkedNullable(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isMarkedNullable(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.isMarkedNullable(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public boolean isMultiFieldValueClass(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.isMultiFieldValueClass(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    @ObsoleteTypeKind
    public boolean isNotNullTypeParameter(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.isNotNullTypeParameter(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isNothing(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.isNothing(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isNothingConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.isNothingConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isNullableAny(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.isNullableAny(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isNullableNothing(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.isNullableNothing(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isNullableType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.isNullableType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isOldCapturedType(CapturedTypeMarker capturedTypeMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
        return this.$$delegate_0.isOldCapturedType(capturedTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isPrimitiveType(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.isPrimitiveType(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isProjectionNotNull(CapturedTypeMarker capturedTypeMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
        return this.$$delegate_0.isProjectionNotNull(capturedTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isRawType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.isRawType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public boolean isReified(TypeParameterMarker typeParameterMarker) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        return this.$$delegate_0.isReified(typeParameterMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isSimpleType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.isSimpleType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isSingleClassifierType(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.isSingleClassifierType(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isStarProjection(TypeArgumentMarker typeArgumentMarker) {
        Intrinsics.checkNotNullParameter(typeArgumentMarker, "<this>");
        return this.$$delegate_0.isStarProjection(typeArgumentMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isStubType(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.isStubType(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isStubTypeForBuilderInference(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.isStubTypeForBuilderInference(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isStubTypeForVariableInSubtyping(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.isStubTypeForVariableInSubtyping(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isTypeParameterTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.isTypeParameterTypeConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isTypeVariableType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.isTypeVariableType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public boolean isUnderKotlinPackage(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.isUnderKotlinPackage(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isUninferredParameter(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.isUninferredParameter(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public Iterator<TypeArgumentMarker> iterator(TypeArgumentListMarker typeArgumentListMarker) {
        Intrinsics.checkNotNullParameter(typeArgumentListMarker, "<this>");
        return this.$$delegate_0.iterator(typeArgumentListMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker lowerBound(FlexibleTypeMarker flexibleTypeMarker) {
        Intrinsics.checkNotNullParameter(flexibleTypeMarker, "<this>");
        return this.$$delegate_0.lowerBound(flexibleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker lowerBoundIfFlexible(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.lowerBoundIfFlexible(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker lowerType(CapturedTypeMarker capturedTypeMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
        return this.$$delegate_0.lowerType(capturedTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker makeDefinitelyNotNullOrNotNull(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.makeDefinitelyNotNullOrNotNull(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public KotlinTypeMarker makeNullable(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.makeNullable(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker makeSimpleTypeDefinitelyNotNullOrNotNull(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.makeSimpleTypeDefinitelyNotNullOrNotNull(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeCheckerProviderContext
    public TypeCheckerState newTypeCheckerState(boolean errorTypesEqualToAnything, boolean stubTypesEqualToAnything) {
        return this.$$delegate_0.newTypeCheckerState(errorTypesEqualToAnything, stubTypesEqualToAnything);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemBuiltInsContext
    public IrSimpleType nothingType() {
        return this.$$delegate_0.nothingType();
    }

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext, org.jetbrains.kotlin.types.model.TypeSystemBuiltInsContext
    public IrSimpleType nullableAnyType() {
        return this.$$delegate_0.nullableAnyType();
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemBuiltInsContext
    public IrSimpleType nullableNothingType() {
        return this.$$delegate_0.nullableNothingType();
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker original(DefinitelyNotNullTypeMarker definitelyNotNullTypeMarker) {
        Intrinsics.checkNotNullParameter(definitelyNotNullTypeMarker, "<this>");
        return this.$$delegate_0.original(definitelyNotNullTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker originalIfDefinitelyNotNullable(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.originalIfDefinitelyNotNullable(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public int parametersCount(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.parametersCount(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public Set<IrType> possibleIntegerTypes(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.possibleIntegerTypes(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeArgumentMarker projection(CapturedTypeConstructorMarker capturedTypeConstructorMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeConstructorMarker, "<this>");
        return this.$$delegate_0.projection(capturedTypeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
    public KotlinTypeMarker replaceCustomAttributes(KotlinTypeMarker kotlinTypeMarker, List<? extends AnnotationMarker> newAttributes) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        Intrinsics.checkNotNullParameter(newAttributes, "newAttributes");
        return this.$$delegate_0.replaceCustomAttributes(kotlinTypeMarker, newAttributes);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeArgumentMarker replaceType(TypeArgumentMarker typeArgumentMarker, KotlinTypeMarker newType) {
        Intrinsics.checkNotNullParameter(typeArgumentMarker, "<this>");
        Intrinsics.checkNotNullParameter(newType, "newType");
        return this.$$delegate_0.replaceType(typeArgumentMarker, newType);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker safeSubstitute(TypeSubstitutorMarker typeSubstitutorMarker, KotlinTypeMarker type) {
        Intrinsics.checkNotNullParameter(typeSubstitutorMarker, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        return this.$$delegate_0.safeSubstitute(typeSubstitutorMarker, type);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public int size(TypeArgumentListMarker typeArgumentListMarker) {
        Intrinsics.checkNotNullParameter(typeArgumentListMarker, "<this>");
        return this.$$delegate_0.size(typeArgumentListMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeCheckerState.SupertypesPolicy substitutionSupertypePolicy(SimpleTypeMarker type) {
        Intrinsics.checkNotNullParameter(type, "type");
        return this.$$delegate_0.substitutionSupertypePolicy(type);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public Collection<KotlinTypeMarker> supertypes(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.supertypes(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
    public SimpleTypeMarker toErrorType(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.toErrorType(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public CapturedTypeConstructorMarker typeConstructor(CapturedTypeMarker capturedTypeMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
        return this.$$delegate_0.typeConstructor(capturedTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeConstructorMarker typeConstructor(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.typeConstructor(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeConstructorMarker typeConstructor(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.typeConstructor(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
    public int typeDepth(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.typeDepth(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
    public int typeDepth(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.typeDepth(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeSubstitutorMarker typeSubstitutorByTypeConstructor(Map<TypeConstructorMarker, ? extends KotlinTypeMarker> map) {
        Intrinsics.checkNotNullParameter(map, "map");
        return this.$$delegate_0.typeSubstitutorByTypeConstructor(map);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
    public List<AnnotationMarker> unionTypeAttributes(List<? extends KotlinTypeMarker> types) {
        Intrinsics.checkNotNullParameter(types, "types");
        return this.$$delegate_0.unionTypeAttributes(types);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeConstructorMarker unwrapStubTypeVariableConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.unwrapStubTypeVariableConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker upperBound(FlexibleTypeMarker flexibleTypeMarker) {
        Intrinsics.checkNotNullParameter(flexibleTypeMarker, "<this>");
        return this.$$delegate_0.upperBound(flexibleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public int upperBoundCount(TypeParameterMarker typeParameterMarker) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        return this.$$delegate_0.upperBoundCount(typeParameterMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker upperBoundIfFlexible(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.upperBoundIfFlexible(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker withNullability(KotlinTypeMarker kotlinTypeMarker, boolean z) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.withNullability(kotlinTypeMarker, z);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker withNullability(SimpleTypeMarker simpleTypeMarker, boolean z) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.withNullability(simpleTypeMarker, z);
    }

    public IrTypeSystemContextWithAdditionalAxioms(IrTypeSystemContext typeSystem, List<? extends IrTypeParameter> firstParameters, List<? extends IrTypeParameter> secondParameters) {
        Intrinsics.checkNotNullParameter(typeSystem, "typeSystem");
        Intrinsics.checkNotNullParameter(firstParameters, "firstParameters");
        Intrinsics.checkNotNullParameter(secondParameters, "secondParameters");
        this.$$delegate_0 = typeSystem;
        firstParameters.size();
        secondParameters.size();
        List<? extends IrTypeParameter> list = firstParameters;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((IrTypeParameter) it.next()).getSymbol());
        }
        this.firstTypeParameterConstructors = arrayList;
        List<? extends IrTypeParameter> list2 = secondParameters;
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        Iterator<T> it2 = list2.iterator();
        while (it2.hasNext()) {
            arrayList2.add(((IrTypeParameter) it2.next()).getSymbol());
        }
        ArrayList arrayList3 = arrayList2;
        this.secondTypeParameterConstructors = arrayList3;
        this.matchingTypeConstructors = (HashMap) MapsKt.toMap(CollectionsKt.zip(this.firstTypeParameterConstructors, arrayList3), org.jetbrains.kotlin.utils.CollectionsKt.newHashMapWithExpectedSize(this.firstTypeParameterConstructors.size()));
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean areEqualTypeConstructors(TypeConstructorMarker c1, TypeConstructorMarker c2) {
        Intrinsics.checkNotNullParameter(c1, "c1");
        Intrinsics.checkNotNullParameter(c2, "c2");
        return IrTypeSystemContext.DefaultImpls.areEqualTypeConstructors(this, c1, c2) || Intrinsics.areEqual(this.matchingTypeConstructors.get(c1), c2) || Intrinsics.areEqual(this.matchingTypeConstructors.get(c2), c1);
    }
}
