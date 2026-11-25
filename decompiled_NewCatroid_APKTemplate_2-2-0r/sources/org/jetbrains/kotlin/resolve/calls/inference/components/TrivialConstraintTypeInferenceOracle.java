package org.jetbrains.kotlin.resolve.calls.inference.components;

import androidx.core.app.NotificationCompat;
import com.reandroid.arsc.container.SpecTypePair;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.builtins.functions.FunctionTypeKind;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.resolve.calls.inference.model.Constraint;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintKind;
import org.jetbrains.kotlin.resolve.checkers.EmptyIntersectionTypeInfo;
import org.jetbrains.kotlin.types.TypeCheckerState;
import org.jetbrains.kotlin.types.model.AnnotationMarker;
import org.jetbrains.kotlin.types.model.CaptureStatus;
import org.jetbrains.kotlin.types.model.CapturedTypeConstructorMarker;
import org.jetbrains.kotlin.types.model.CapturedTypeMarker;
import org.jetbrains.kotlin.types.model.DefinitelyNotNullTypeMarker;
import org.jetbrains.kotlin.types.model.DynamicTypeMarker;
import org.jetbrains.kotlin.types.model.FlexibleTypeMarker;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.ObsoleteTypeKind;
import org.jetbrains.kotlin.types.model.SimpleTypeMarker;
import org.jetbrains.kotlin.types.model.StubTypeMarker;
import org.jetbrains.kotlin.types.model.TypeArgumentListMarker;
import org.jetbrains.kotlin.types.model.TypeArgumentMarker;
import org.jetbrains.kotlin.types.model.TypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeParameterMarker;
import org.jetbrains.kotlin.types.model.TypeSubstitutorMarker;
import org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext;
import org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContextDelegate;
import org.jetbrains.kotlin.types.model.TypeVariableMarker;
import org.jetbrains.kotlin.types.model.TypeVariableTypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeVariance;

/* JADX INFO: compiled from: TrivialConstraintTypeInferenceOracle.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000ø\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\"\n\u0002\bR\n\u0002\u0010(\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0019\u0018\u0000 õ\u00012\u00020\u0001:\u0002õ\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0002\u0010\u0005J\t\u0010\u000e\u001a\u00020\u000fH\u0096\u0001J\u0019\u0010\u0010\u001a\u00020\u00072\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0012H\u0096\u0001J\u001b\u0010\u0014\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0015\u001a\u00020\u000f2\u0006\u0010\u0016\u001a\u00020\u0017H\u0096\u0001J\u0013\u0010\u0018\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u0015\u001a\u00020\u0019H\u0096\u0001J\u0019\u0010\u001a\u001a\u0004\u0018\u00010\u001b2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00190\u001dH\u0096\u0001J!\u0010\u001e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u001f\u001a\u00020\u000b2\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00190!H\u0096\u0001J1\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020%2\f\u0010&\u001a\b\u0012\u0004\u0012\u00020\u00190!2\b\u0010'\u001a\u0004\u0018\u00010\u00192\u0006\u0010(\u001a\u00020\u0017H\u0096\u0001J\t\u0010)\u001a\u00020*H\u0096\u0001J\u0011\u0010+\u001a\u00020\u000f2\u0006\u0010,\u001a\u00020-H\u0096\u0001J\u0019\u0010.\u001a\u00020\u00192\u0006\u0010/\u001a\u00020\u000f2\u0006\u00100\u001a\u00020\u000fH\u0096\u0001J?\u00101\u001a\u00020\u000f2\u0006\u00102\u001a\u00020\u00122\f\u00103\u001a\b\u0012\u0004\u0012\u00020%0!2\u0006\u00104\u001a\u00020\u00072\u0006\u00105\u001a\u00020\u00072\u000e\u00106\u001a\n\u0012\u0004\u0012\u000207\u0018\u00010!H\u0096\u0001J\u0011\u00108\u001a\u00020%2\u0006\u0010\t\u001a\u00020\nH\u0096\u0001J\u0011\u00109\u001a\u00020:2\u0006\u0010\u001f\u001a\u00020;H\u0096\u0001J\u0011\u0010<\u001a\u00020:2\u0006\u0010\u001f\u001a\u00020;H\u0096\u0001J\u0013\u0010=\u001a\u0004\u0018\u00010*2\u0006\u0010>\u001a\u00020\u0019H\u0096\u0001J\u0019\u0010?\u001a\u00020%2\u0006\u0010\u0015\u001a\u00020\u00192\u0006\u0010@\u001a\u00020AH\u0096\u0001J\u0019\u0010B\u001a\u00020\u00192\u0006\u0010C\u001a\u00020\u00192\u0006\u0010D\u001a\u00020\u0019H\u0096\u0001J\u0011\u0010E\u001a\u00020\u00192\u0006\u00102\u001a\u00020\u0012H\u0096\u0001J\u0019\u0010F\u001a\u0004\u0018\u00010\u000f2\f\u0010G\u001a\b\u0012\u0004\u0012\u00020\u000f0!H\u0096\u0001J\u0019\u0010H\u001a\u00020\u00122\u0006\u0010I\u001a\u00020J2\u0006\u0010K\u001a\u00020LH\u0096\u0001J\u0019\u0010M\u001a\u00020\u00122\u0006\u0010I\u001a\u00020J2\u0006\u0010K\u001a\u00020LH\u0096\u0001J\u0019\u0010N\u001a\u00020\u00072\u0006\u0010O\u001a\u00020\u000f2\u0006\u0010P\u001a\u00020\u000fH\u0096\u0001J\u0017\u0010Q\u001a\u00020\u00192\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00190!H\u0096\u0001J\u0017\u0010Q\u001a\u00020\u000f2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u000f0!H\u0096\u0001J&\u0010R\u001a\u00020\u00072\u0006\u0010S\u001a\u00020T2\u0006\u0010U\u001a\u00020T2\u0006\u0010V\u001a\u00020\u00192\u0006\u0010W\u001a\u00020\u0007J\u000e\u0010X\u001a\u00020\u00072\u0006\u0010Y\u001a\u00020TJ\u000e\u0010Z\u001a\u00020\u00072\u0006\u0010[\u001a\u00020\u0019J\u0019\u0010\\\u001a\u00020]2\u0006\u0010^\u001a\u00020\u00072\u0006\u0010_\u001a\u00020\u0007H\u0096\u0001J\t\u0010`\u001a\u00020\u000fH\u0096\u0001J\t\u0010a\u001a\u00020\u000fH\u0096\u0001J\t\u0010b\u001a\u00020\u000fH\u0096\u0001J\u0011\u0010c\u001a\u00020d2\u0006\u0010\u0015\u001a\u00020\u000fH\u0096\u0001J\u001d\u0010e\u001a\u00020*2\u0012\u0010f\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00190gH\u0096\u0001J\u001d\u0010h\u001a\b\u0012\u0004\u0012\u0002070!2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00190!H\u0096\u0001J\t\u0010i\u001a\u00020\u0007H\u0096\u0001J!\u0010j\u001a\u00020\u0007*\u00020\u00192\u0012\u0010k\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00070lH\u0096\u0001J\r\u0010m\u001a\u00020J*\u00020\u0019H\u0096\u0001J\r\u0010n\u001a\u00020o*\u00020\u000fH\u0096\u0001J\u000f\u0010p\u001a\u0004\u0018\u00010#*\u00020\u000fH\u0096\u0001J\u000f\u0010q\u001a\u0004\u0018\u00010r*\u00020\u000fH\u0096\u0001J\u000f\u0010s\u001a\u0004\u0018\u00010t*\u00020uH\u0096\u0001J\u000f\u0010v\u001a\u0004\u0018\u00010u*\u00020\u0019H\u0096\u0001J\u000f\u0010w\u001a\u0004\u0018\u00010\u000f*\u00020\u0019H\u0096\u0001J\r\u0010x\u001a\u00020%*\u00020\u0019H\u0096\u0001J\r\u0010y\u001a\u00020\u0007*\u00020\u0019H\u0096\u0001J\r\u0010(\u001a\u00020\u0017*\u00020#H\u0096\u0001J!\u0010z\u001a\u00020\u0007*\u00020\u00192\u0012\u0010k\u001a\u000e\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\u00070lH\u0096\u0001J\f\u0010{\u001a\u00020\u0007*\u00020\u0019H\u0002J\r\u0010|\u001a\u00020\u0019*\u00020\u0019H\u0096\u0001J\r\u0010}\u001a\u00020\u000f*\u00020;H\u0096\u0001J\r\u0010~\u001a\u00020\u0019*\u00020\u0019H\u0096\u0001J\u0013\u0010\u007f\u001a\b\u0012\u0004\u0012\u00020\u00190!*\u00020\u0019H\u0096\u0001J\u0015\u0010\u0080\u0001\u001a\t\u0012\u0004\u0012\u00020\n0\u0081\u0001*\u00020\u0019H\u0096\u0001J\u0015\u0010\u0082\u0001\u001a\t\u0012\u0004\u0012\u00020\u000b0\u0081\u0001*\u00020\u0019H\u0096\u0001J\u001e\u0010\u0083\u0001\u001a\n\u0012\u0004\u0012\u00020\u000f\u0018\u00010!*\u00020\u000f2\u0006\u00102\u001a\u00020\u0012H\u0096\u0001J\u000e\u0010\u0084\u0001\u001a\u00020\u0012*\u00020;H\u0096\u0001J\u0010\u0010\u0085\u0001\u001a\u0004\u0018\u00010L*\u00020\u0019H\u0096\u0001J\u0017\u0010\u0086\u0001\u001a\u00020%*\u00020o2\u0007\u0010\u0087\u0001\u001a\u00020JH\u0096\u0003J\u000e\u0010\u0088\u0001\u001a\u00020\u0019*\u00020\u0012H\u0096\u0001J\u0017\u0010\u0089\u0001\u001a\u00020%*\u00020\u00192\u0007\u0010\u0087\u0001\u001a\u00020JH\u0096\u0001J\u0019\u0010\u008a\u0001\u001a\u0004\u0018\u00010%*\u00020\u000f2\u0007\u0010\u0087\u0001\u001a\u00020JH\u0096\u0001J\u0014\u0010\u008b\u0001\u001a\b\u0012\u0004\u0012\u00020%0!*\u00020\u0019H\u0096\u0001J\u0014\u0010\u008c\u0001\u001a\b\u0012\u0004\u0012\u0002070!*\u00020\u0019H\u0096\u0001J\u0014\u0010\u008d\u0001\u001a\b\u0012\u0004\u0012\u0002070!*\u00020\u0019H\u0096\u0001J\u000e\u0010\u008e\u0001\u001a\u00020\u0019*\u00020\u0019H\u0096\u0001J\u000e\u0010\u008f\u0001\u001a\u00020\u000b*\u00020:H\u0096\u0001J\u0017\u0010\u0090\u0001\u001a\u00020\n*\u00020\u00122\u0007\u0010\u0087\u0001\u001a\u00020JH\u0096\u0001J\u0014\u0010\u0091\u0001\u001a\b\u0012\u0004\u0012\u00020\n0!*\u00020\u0012H\u0096\u0001J\u000e\u0010\u0092\u0001\u001a\u00020\u0019*\u00020%H\u0096\u0001J\u000e\u0010\u0093\u0001\u001a\u00020\u0012*\u00020\nH\u0096\u0001J\u0010\u0010\u0094\u0001\u001a\u0004\u0018\u00010\n*\u00020\u0012H\u0096\u0001J\u0017\u0010\u0095\u0001\u001a\u00020\u0019*\u00020\n2\u0007\u0010\u0087\u0001\u001a\u00020JH\u0096\u0001J\u0014\u0010\u0096\u0001\u001a\b\u0012\u0004\u0012\u00020\u00190!*\u00020\nH\u0096\u0001J\u000e\u0010\u0097\u0001\u001a\u00020A*\u00020%H\u0096\u0001J\u000e\u0010\u0097\u0001\u001a\u00020A*\u00020\nH\u0096\u0001J\u000e\u0010\u0098\u0001\u001a\u00020\u0007*\u00020\u0019H\u0096\u0001J\u000e\u0010\u0099\u0001\u001a\u00020\u0007*\u00020\u0019H\u0096\u0001J\u000e\u0010\u009a\u0001\u001a\u00020\u0007*\u00020\u0019H\u0096\u0001J\u000e\u0010\u009b\u0001\u001a\u00020\u0007*\u00020\u0019H\u0096\u0001J\u0019\u0010\u009c\u0001\u001a\u00020\u0007*\u00020\n2\t\u0010\u009d\u0001\u001a\u0004\u0018\u00010\u0012H\u0096\u0001J\u000e\u0010\u009e\u0001\u001a\u00020\u0007*\u00020\u0012H\u0096\u0001J\u000e\u0010\u009f\u0001\u001a\u00020\u0007*\u00020\u0012H\u0096\u0001J\u000e\u0010 \u0001\u001a\u00020\u0007*\u00020\u0019H\u0096\u0001J\u000e\u0010¡\u0001\u001a\u00020\u0007*\u00020\u0019H\u0096\u0001J\u000e\u0010¢\u0001\u001a\u00020\u0007*\u00020\u0019H\u0096\u0001J\u000e\u0010£\u0001\u001a\u00020\u0007*\u00020\u0012H\u0096\u0001J\u000e\u0010¤\u0001\u001a\u00020\u0007*\u00020\u000fH\u0096\u0001J\u000e\u0010¥\u0001\u001a\u00020\u0007*\u00020\u0012H\u0096\u0001J\u000e\u0010¦\u0001\u001a\u00020\u0007*\u00020\u0012H\u0096\u0001J\u000e\u0010§\u0001\u001a\u00020\u0007*\u00020\u000bH\u0096\u0001J\u000e\u0010¨\u0001\u001a\u00020\u0007*\u00020\u0012H\u0096\u0001J\u000e\u0010©\u0001\u001a\u00020\u0007*\u00020\u0019H\u0096\u0001J\u000e\u0010ª\u0001\u001a\u00020\u0007*\u00020\u0012H\u0096\u0001J\u000e\u0010«\u0001\u001a\u00020\u0007*\u00020\u0019H\u0096\u0001J\u000e\u0010¬\u0001\u001a\u00020\u0007*\u00020\u0019H\u0096\u0001J\u000e\u0010¬\u0001\u001a\u00020\u0007*\u00020\u0012H\u0096\u0001J\r\u00105\u001a\u00020\u0007*\u00020\u000fH\u0096\u0001J\u000e\u0010\u00ad\u0001\u001a\u00020\u0007*\u00020\u0019H\u0096\u0001J\u000e\u0010®\u0001\u001a\u00020\u0007*\u00020\u0012H\u0096\u0001J\u000e\u0010¯\u0001\u001a\u00020\u0007*\u00020\u0019H\u0096\u0001J\u000e\u0010°\u0001\u001a\u00020\u0007*\u00020\u0019H\u0096\u0001J\u000e\u0010±\u0001\u001a\u00020\u0007*\u00020\u0019H\u0096\u0001J\u000e\u0010²\u0001\u001a\u00020\u0007*\u00020\u0012H\u0096\u0001J\u000e\u0010³\u0001\u001a\u00020\u0007*\u00020\u0012H\u0096\u0001J\u000e\u0010´\u0001\u001a\u00020\u0007*\u00020\u000fH\u0096\u0001J\u000e\u0010µ\u0001\u001a\u00020\u0007*\u00020\u0012H\u0096\u0001J\u000e\u0010¶\u0001\u001a\u00020\u0007*\u00020\u0012H\u0096\u0001J\u000e\u0010·\u0001\u001a\u00020\u0007*\u00020\u0012H\u0096\u0001J\u000e\u0010¸\u0001\u001a\u00020\u0007*\u00020\u0012H\u0096\u0001J\u000e\u0010¹\u0001\u001a\u00020\u0007*\u00020\u0019H\u0096\u0001J\u000e\u0010¹\u0001\u001a\u00020\u0007*\u00020\u000fH\u0096\u0001J\u000e\u0010º\u0001\u001a\u00020\u0007*\u00020\u0019H\u0097\u0001J\u000e\u0010»\u0001\u001a\u00020\u0007*\u00020\u0019H\u0096\u0001J\u000e\u0010¼\u0001\u001a\u00020\u0007*\u00020\u0012H\u0096\u0001J\r\u0010½\u0001\u001a\u00020\u0007*\u00020\u0019H\u0002J\u000e\u0010¾\u0001\u001a\u00020\u0007*\u00020\u0019H\u0096\u0001J\u000e\u0010¿\u0001\u001a\u00020\u0007*\u00020\u0019H\u0096\u0001J\u000e\u0010À\u0001\u001a\u00020\u0007*\u00020\u0019H\u0096\u0001J\u000e\u0010Á\u0001\u001a\u00020\u0007*\u00020#H\u0096\u0001J\u000e\u0010Â\u0001\u001a\u00020\u0007*\u00020\u000fH\u0096\u0001J\u000e\u0010Ã\u0001\u001a\u00020\u0007*\u00020#H\u0096\u0001J\u000e\u0010Ä\u0001\u001a\u00020\u0007*\u00020\u0019H\u0096\u0001J\u000e\u0010Å\u0001\u001a\u00020\u0007*\u00020\u0019H\u0096\u0001J\u000e\u0010Æ\u0001\u001a\u00020\u0007*\u00020\u0019H\u0096\u0001J\u000e\u0010Ç\u0001\u001a\u00020\u0007*\u00020\u000fH\u0096\u0001J\u000e\u0010È\u0001\u001a\u00020\u0007*\u00020\u0019H\u0096\u0001J\u000e\u0010É\u0001\u001a\u00020\u0007*\u00020%H\u0096\u0001J\u000e\u0010Ê\u0001\u001a\u00020\u0007*\u00020\u000fH\u0096\u0001J\u000e\u0010Ë\u0001\u001a\u00020\u0007*\u00020\u000fH\u0096\u0001J\u000e\u0010Ì\u0001\u001a\u00020\u0007*\u00020\u000fH\u0096\u0001J\u000e\u0010Í\u0001\u001a\u00020\u0007*\u00020\u0012H\u0096\u0001J\u000e\u0010Î\u0001\u001a\u00020\u0007*\u00020\u0012H\u0096\u0001J\u000e\u0010Ï\u0001\u001a\u00020\u0007*\u00020\u0019H\u0096\u0001J\u000e\u0010Ð\u0001\u001a\u00020\u0007*\u00020\u0019H\u0096\u0001J\u000e\u0010Ñ\u0001\u001a\u00020\u0007*\u00020\u0019H\u0096\u0001J\u000e\u0010Ò\u0001\u001a\u00020\u0007*\u00020\u0012H\u0096\u0001J\u0015\u0010Ó\u0001\u001a\t\u0012\u0004\u0012\u00020%0Ô\u0001*\u00020oH\u0096\u0003J\r\u0010/\u001a\u00020\u000f*\u00020uH\u0096\u0001J\u000e\u0010Õ\u0001\u001a\u00020\u000f*\u00020\u0019H\u0096\u0001J\u000f\u0010'\u001a\u0004\u0018\u00010\u0019*\u00020#H\u0096\u0001J\u000e\u0010Ö\u0001\u001a\u00020\u0019*\u00020\u0019H\u0096\u0001J\u000e\u0010×\u0001\u001a\u00020\u000f*\u00020\u000fH\u0096\u0001J\u000e\u0010Ø\u0001\u001a\u00020\u000f*\u00020rH\u0096\u0001J\u000e\u0010Ù\u0001\u001a\u00020\u000f*\u00020\u000fH\u0096\u0001J\u000e\u0010Ú\u0001\u001a\u00020J*\u00020\u0012H\u0096\u0001J\u0014\u0010Û\u0001\u001a\b\u0012\u0004\u0012\u00020\u00190\u001d*\u00020\u000fH\u0096\u0001J\u000f\u0010Ü\u0001\u001a\u00020%*\u00030Ý\u0001H\u0096\u0001J\u000e\u0010Þ\u0001\u001a\u00020\u0019*\u00020\u0019H\u0096\u0001J\u000e\u0010ß\u0001\u001a\u00020\u0019*\u00020\u0019H\u0096\u0001J#\u0010à\u0001\u001a\u00020\u0019*\u00020\u00192\u0013\u0010á\u0001\u001a\u000e\u0012\u0004\u0012\u00020%\u0012\u0004\u0012\u00020%0lH\u0096\u0001J#\u0010à\u0001\u001a\u00020\u000f*\u00020\u000f2\u0013\u0010á\u0001\u001a\u000e\u0012\u0004\u0012\u00020%\u0012\u0004\u0012\u00020%0lH\u0096\u0001J\u001d\u0010à\u0001\u001a\u00020\u000f*\u00020\u000f2\r\u0010â\u0001\u001a\b\u0012\u0004\u0012\u00020%0!H\u0096\u0001J#\u0010ã\u0001\u001a\u00020\u0019*\u00020\u00192\u0013\u0010á\u0001\u001a\u000e\u0012\u0004\u0012\u00020%\u0012\u0004\u0012\u00020%0lH\u0096\u0001J#\u0010ã\u0001\u001a\u00020\u000f*\u00020\u000f2\u0013\u0010á\u0001\u001a\u000e\u0012\u0004\u0012\u00020%\u0012\u0004\u0012\u00020%0lH\u0096\u0001J\u001d\u0010ä\u0001\u001a\u00020\u0019*\u00020\u00192\r\u0010å\u0001\u001a\b\u0012\u0004\u0012\u0002070!H\u0096\u0001J\u0017\u0010æ\u0001\u001a\u00020%*\u00020%2\u0007\u0010ç\u0001\u001a\u00020\u0019H\u0096\u0001J\u0016\u0010è\u0001\u001a\u00020\u0019*\u00020*2\u0006\u0010\u0015\u001a\u00020\u0019H\u0096\u0001J\u0016\u0010é\u0001\u001a\u0004\u0018\u00010\u0019*\b\u0012\u0004\u0012\u00020\u00190\u001dH\u0096\u0001J\u000e\u0010ê\u0001\u001a\u00020J*\u00020oH\u0096\u0001J\u0014\u0010ë\u0001\u001a\b\u0012\u0004\u0012\u00020\u00190\u001d*\u00020\u0012H\u0096\u0001J\u000e\u0010ì\u0001\u001a\u00020\u000f*\u00020\u0012H\u0096\u0001J\u000f\u0010í\u0001\u001a\u00030Ý\u0001*\u00020#H\u0096\u0001J\u000e\u0010í\u0001\u001a\u00020\u0012*\u00020\u0019H\u0096\u0001J\u000e\u0010í\u0001\u001a\u00020\u0012*\u00020\u000fH\u0096\u0001J\u000e\u0010î\u0001\u001a\u00020%*\u00020#H\u0096\u0001J\u000e\u0010ï\u0001\u001a\u00020J*\u00020\u0019H\u0096\u0001J\u000e\u0010ï\u0001\u001a\u00020J*\u00020\u000fH\u0096\u0001J\u000f\u0010\t\u001a\u0004\u0018\u00010\n*\u00020#H\u0096\u0001J\u000e\u0010ð\u0001\u001a\u00020\u0012*\u00020\u0012H\u0096\u0001J\r\u00100\u001a\u00020\u000f*\u00020uH\u0096\u0001J\u000e\u0010ñ\u0001\u001a\u00020J*\u00020\nH\u0096\u0001J\u000e\u0010ò\u0001\u001a\u00020\u000f*\u00020\u0019H\u0096\u0001J\u000e\u0010ó\u0001\u001a\u00020\u0019*\u00020#H\u0096\u0001J\u0016\u0010ô\u0001\u001a\u00020\u0019*\u00020\u00192\u0006\u00104\u001a\u00020\u0007H\u0096\u0001J\u0016\u0010ô\u0001\u001a\u00020\u000f*\u00020\u000f2\u0006\u00104\u001a\u00020\u0007H\u0096\u0001R\u0012\u0010\u0006\u001a\u00020\u0007X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0006\u0010\bR\u0018\u0010\t\u001a\u0004\u0018\u00010\n*\u00020\u000bX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\f\u0010\r¨\u0006ö\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/components/TrivialConstraintTypeInferenceOracle;", "Lorg/jetbrains/kotlin/types/model/TypeSystemInferenceExtensionContext;", "context", "Lorg/jetbrains/kotlin/types/model/TypeSystemInferenceExtensionContextDelegate;", "(Lorg/jetbrains/kotlin/types/model/TypeSystemInferenceExtensionContextDelegate;)V", "(Lorg/jetbrains/kotlin/types/model/TypeSystemInferenceExtensionContext;)V", "isK2", "", "()Z", "typeParameter", "Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;", "Lorg/jetbrains/kotlin/types/model/TypeVariableTypeConstructorMarker;", "getTypeParameter", "(Lorg/jetbrains/kotlin/types/model/TypeVariableTypeConstructorMarker;)Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;", SchemaSymbols.ATTVAL_ANYTYPE, "Lorg/jetbrains/kotlin/types/model/SimpleTypeMarker;", "areEqualTypeConstructors", "c1", "Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;", "c2", "captureFromArguments", ModuleXmlParser.TYPE, NotificationCompat.CATEGORY_STATUS, "Lorg/jetbrains/kotlin/types/model/CaptureStatus;", "captureFromExpression", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "computeEmptyIntersectionTypeKind", "Lorg/jetbrains/kotlin/resolve/checkers/EmptyIntersectionTypeInfo;", SpecTypePair.NAME_types, "", "createCapturedStarProjectionForSelfType", "typeVariable", "typesForRecursiveTypeParameters", "", "createCapturedType", "Lorg/jetbrains/kotlin/types/model/CapturedTypeMarker;", "constructorProjection", "Lorg/jetbrains/kotlin/types/model/TypeArgumentMarker;", "constructorSupertypes", "lowerType", "captureStatus", "createEmptySubstitutor", "Lorg/jetbrains/kotlin/types/model/TypeSubstitutorMarker;", "createErrorType", "debugName", "", "createFlexibleType", "lowerBound", "upperBound", "createSimpleType", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "arguments", "nullable", "isExtensionFunction", "attributes", "Lorg/jetbrains/kotlin/types/model/AnnotationMarker;", "createStarProjection", "createStubTypeForBuilderInference", "Lorg/jetbrains/kotlin/types/model/StubTypeMarker;", "Lorg/jetbrains/kotlin/types/model/TypeVariableMarker;", "createStubTypeForTypeVariablesInSubtyping", "createSubstitutorForSuperTypes", "baseType", "createTypeArgument", "variance", "Lorg/jetbrains/kotlin/types/model/TypeVariance;", "createTypeWithAlternativeForIntersectionResult", "firstCandidate", "secondCandidate", "createUninferredType", "findCommonIntegerLiteralTypesSuperType", "explicitSupertypes", "getNonReflectFunctionTypeConstructor", "parametersNumber", "", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/builtins/functions/FunctionTypeKind;", "getReflectFunctionTypeConstructor", "identicalArguments", "a", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "intersectTypes", "isGeneratedConstraintTrivial", "baseConstraint", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/Constraint;", "otherConstraint", "generatedConstraintType", "isSubtype", "isNotInterestingConstraint", "constraint", "isSuitableResultedType", "resultType", "newTypeCheckerState", "Lorg/jetbrains/kotlin/types/TypeCheckerState;", "errorTypesEqualToAnything", "stubTypesEqualToAnything", "nothingType", "nullableAnyType", "nullableNothingType", "substitutionSupertypePolicy", "Lorg/jetbrains/kotlin/types/TypeCheckerState$SupertypesPolicy;", "typeSubstitutorByTypeConstructor", "map", "", "unionTypeAttributes", "useRefinedBoundsForTypeVariableInFlexiblePosition", "anySuperTypeConstructor", "predicate", "Lkotlin/Function1;", "argumentsCount", "asArgumentList", "Lorg/jetbrains/kotlin/types/model/TypeArgumentListMarker;", "asCapturedType", "asDefinitelyNotNullType", "Lorg/jetbrains/kotlin/types/model/DefinitelyNotNullTypeMarker;", "asDynamicType", "Lorg/jetbrains/kotlin/types/model/DynamicTypeMarker;", "Lorg/jetbrains/kotlin/types/model/FlexibleTypeMarker;", "asFlexibleType", "asSimpleType", "asTypeArgument", "canHaveUndefinedNullability", "contains", "containsOnlyNonNullableNothing", "convertToNonRaw", "defaultType", "eraseContainingTypeParameters", "extractArgumentsForFunctionTypeOrSubtype", "extractTypeParameters", "", "extractTypeVariables", "fastCorrespondingSupertypes", "freshTypeConstructor", "functionTypeKind", "get", "index", "getApproximatedIntegerLiteralType", "getArgument", "getArgumentOrNull", "getArguments", "getAttributes", "getCustomAttributes", "getFunctionTypeFromSupertypes", "getOriginalTypeVariable", "getParameter", "getParameters", "getType", "getTypeConstructor", "getTypeParameterClassifier", "getUpperBound", "getUpperBounds", "getVariance", "hasCustomAttributes", "hasExactAnnotation", "hasFlexibleNullability", "hasNoInferAnnotation", "hasRecursiveBounds", "selfConstructor", "isAnonymous", "isAnyConstructor", "isBuiltinFunctionTypeOrSubtype", "isCapturedDynamic", "isCapturedType", "isCapturedTypeConstructor", "isClassType", "isClassTypeConstructor", "isCommonFinalClassConstructor", "isContainedInInvariantOrContravariantPositions", "isDefinitelyClassTypeConstructor", "isDefinitelyNotNullType", "isDenotable", "isDynamic", "isError", "isExtensionFunctionType", "isFinalClassConstructor", "isFlexible", "isFlexibleNothing", "isFunctionOrKFunctionWithAnySuspendability", "isIntegerConstantOperatorTypeConstructor", "isIntegerLiteralConstantTypeConstructor", "isIntegerLiteralType", "isIntegerLiteralTypeConstructor", "isInterface", "isIntersection", "isLocalType", "isMarkedNullable", "isNotNullTypeParameter", "isNothing", "isNothingConstructor", "isNothingOrNullableNothing", "isNullableAny", "isNullableNothing", "isNullableType", "isOldCapturedType", "isPrimitiveType", "isProjectionNotNull", "isRawType", "isSignedOrUnsignedNumberType", "isSimpleType", "isSingleClassifierType", "isSpecial", "isStarProjection", "isStubType", "isStubTypeForBuilderInference", "isStubTypeForVariableInSubtyping", "isTypeParameterTypeConstructor", "isTypeVariable", "isTypeVariableType", "isUninferredParameter", "isUnit", "isUnitTypeConstructor", "iterator", "", "lowerBoundIfFlexible", "makeDefinitelyNotNullOrNotNull", "makeSimpleTypeDefinitelyNotNullOrNotNull", "original", "originalIfDefinitelyNotNullable", "parametersCount", "possibleIntegerTypes", "projection", "Lorg/jetbrains/kotlin/types/model/CapturedTypeConstructorMarker;", "removeAnnotations", "removeExactAnnotation", "replaceArguments", "replacement", "newArguments", "replaceArgumentsDeeply", "replaceCustomAttributes", "newAttributes", "replaceType", "newType", "safeSubstitute", "singleBestRepresentative", "size", "supertypes", "toErrorType", "typeConstructor", "typeConstructorProjection", "typeDepth", "unwrapStubTypeVariableConstructor", "upperBoundCount", "upperBoundIfFlexible", "withNotNullProjection", "withNullability", "Companion", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TrivialConstraintTypeInferenceOracle implements TypeSystemInferenceExtensionContext {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final /* synthetic */ TypeSystemInferenceExtensionContext $$delegate_0;

    public /* synthetic */ TrivialConstraintTypeInferenceOracle(TypeSystemInferenceExtensionContext typeSystemInferenceExtensionContext, DefaultConstructorMarker defaultConstructorMarker) {
        this(typeSystemInferenceExtensionContext);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
    public boolean anySuperTypeConstructor(KotlinTypeMarker kotlinTypeMarker, Function1<? super SimpleTypeMarker, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        return this.$$delegate_0.anySuperTypeConstructor(kotlinTypeMarker, predicate);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemBuiltInsContext
    public SimpleTypeMarker anyType() {
        return this.$$delegate_0.anyType();
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean areEqualTypeConstructors(TypeConstructorMarker c1, TypeConstructorMarker c2) {
        Intrinsics.checkNotNullParameter(c1, "c1");
        Intrinsics.checkNotNullParameter(c2, "c2");
        return this.$$delegate_0.areEqualTypeConstructors(c1, c2);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public int argumentsCount(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.argumentsCount(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeArgumentListMarker asArgumentList(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.asArgumentList(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public CapturedTypeMarker asCapturedType(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.asCapturedType(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public DefinitelyNotNullTypeMarker asDefinitelyNotNullType(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.asDefinitelyNotNullType(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public DynamicTypeMarker asDynamicType(FlexibleTypeMarker flexibleTypeMarker) {
        Intrinsics.checkNotNullParameter(flexibleTypeMarker, "<this>");
        return this.$$delegate_0.asDynamicType(flexibleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public FlexibleTypeMarker asFlexibleType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.asFlexibleType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker asSimpleType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.asSimpleType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeArgumentMarker asTypeArgument(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.asTypeArgument(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
    public boolean canHaveUndefinedNullability(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.canHaveUndefinedNullability(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker captureFromArguments(SimpleTypeMarker type, CaptureStatus status) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(status, "status");
        return this.$$delegate_0.captureFromArguments(type, status);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker captureFromExpression(KotlinTypeMarker type) {
        Intrinsics.checkNotNullParameter(type, "type");
        return this.$$delegate_0.captureFromExpression(type);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public CaptureStatus captureStatus(CapturedTypeMarker capturedTypeMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
        return this.$$delegate_0.captureStatus(capturedTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public EmptyIntersectionTypeInfo computeEmptyIntersectionTypeKind(Collection<? extends KotlinTypeMarker> types) {
        Intrinsics.checkNotNullParameter(types, "types");
        return this.$$delegate_0.computeEmptyIntersectionTypeKind(types);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public boolean contains(KotlinTypeMarker kotlinTypeMarker, Function1<? super KotlinTypeMarker, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        return this.$$delegate_0.contains(kotlinTypeMarker, predicate);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public KotlinTypeMarker convertToNonRaw(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.convertToNonRaw(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public SimpleTypeMarker createCapturedStarProjectionForSelfType(TypeVariableTypeConstructorMarker typeVariable, List<? extends KotlinTypeMarker> typesForRecursiveTypeParameters) {
        Intrinsics.checkNotNullParameter(typeVariable, "typeVariable");
        Intrinsics.checkNotNullParameter(typesForRecursiveTypeParameters, "typesForRecursiveTypeParameters");
        return this.$$delegate_0.createCapturedStarProjectionForSelfType(typeVariable, typesForRecursiveTypeParameters);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public CapturedTypeMarker createCapturedType(TypeArgumentMarker constructorProjection, List<? extends KotlinTypeMarker> constructorSupertypes, KotlinTypeMarker lowerType, CaptureStatus captureStatus) {
        Intrinsics.checkNotNullParameter(constructorProjection, "constructorProjection");
        Intrinsics.checkNotNullParameter(constructorSupertypes, "constructorSupertypes");
        Intrinsics.checkNotNullParameter(captureStatus, "captureStatus");
        return this.$$delegate_0.createCapturedType(constructorProjection, constructorSupertypes, lowerType, captureStatus);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeSubstitutorMarker createEmptySubstitutor() {
        return this.$$delegate_0.createEmptySubstitutor();
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemTypeFactoryContext
    public SimpleTypeMarker createErrorType(String debugName) {
        Intrinsics.checkNotNullParameter(debugName, "debugName");
        return this.$$delegate_0.createErrorType(debugName);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemTypeFactoryContext
    public KotlinTypeMarker createFlexibleType(SimpleTypeMarker lowerBound, SimpleTypeMarker upperBound) {
        Intrinsics.checkNotNullParameter(lowerBound, "lowerBound");
        Intrinsics.checkNotNullParameter(upperBound, "upperBound");
        return this.$$delegate_0.createFlexibleType(lowerBound, upperBound);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemTypeFactoryContext
    public SimpleTypeMarker createSimpleType(TypeConstructorMarker constructor, List<? extends TypeArgumentMarker> arguments, boolean nullable, boolean isExtensionFunction, List<? extends AnnotationMarker> attributes) {
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        return this.$$delegate_0.createSimpleType(constructor, arguments, nullable, isExtensionFunction, attributes);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemTypeFactoryContext
    public TypeArgumentMarker createStarProjection(TypeParameterMarker typeParameter) {
        Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
        return this.$$delegate_0.createStarProjection(typeParameter);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public StubTypeMarker createStubTypeForBuilderInference(TypeVariableMarker typeVariable) {
        Intrinsics.checkNotNullParameter(typeVariable, "typeVariable");
        return this.$$delegate_0.createStubTypeForBuilderInference(typeVariable);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public StubTypeMarker createStubTypeForTypeVariablesInSubtyping(TypeVariableMarker typeVariable) {
        Intrinsics.checkNotNullParameter(typeVariable, "typeVariable");
        return this.$$delegate_0.createStubTypeForTypeVariablesInSubtyping(typeVariable);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public TypeSubstitutorMarker createSubstitutorForSuperTypes(KotlinTypeMarker baseType) {
        Intrinsics.checkNotNullParameter(baseType, "baseType");
        return this.$$delegate_0.createSubstitutorForSuperTypes(baseType);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemTypeFactoryContext
    public TypeArgumentMarker createTypeArgument(KotlinTypeMarker type, TypeVariance variance) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(variance, "variance");
        return this.$$delegate_0.createTypeArgument(type, variance);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public KotlinTypeMarker createTypeWithAlternativeForIntersectionResult(KotlinTypeMarker firstCandidate, KotlinTypeMarker secondCandidate) {
        Intrinsics.checkNotNullParameter(firstCandidate, "firstCandidate");
        Intrinsics.checkNotNullParameter(secondCandidate, "secondCandidate");
        return this.$$delegate_0.createTypeWithAlternativeForIntersectionResult(firstCandidate, secondCandidate);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemTypeFactoryContext
    public KotlinTypeMarker createUninferredType(TypeConstructorMarker constructor) {
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        return this.$$delegate_0.createUninferredType(constructor);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public SimpleTypeMarker defaultType(TypeVariableMarker typeVariableMarker) {
        Intrinsics.checkNotNullParameter(typeVariableMarker, "<this>");
        return this.$$delegate_0.defaultType(typeVariableMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public KotlinTypeMarker eraseContainingTypeParameters(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.eraseContainingTypeParameters(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public List<KotlinTypeMarker> extractArgumentsForFunctionTypeOrSubtype(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.extractArgumentsForFunctionTypeOrSubtype(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public Set<TypeParameterMarker> extractTypeParameters(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.extractTypeParameters(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public Set<TypeVariableTypeConstructorMarker> extractTypeVariables(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.extractTypeVariables(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public List<SimpleTypeMarker> fastCorrespondingSupertypes(SimpleTypeMarker simpleTypeMarker, TypeConstructorMarker constructor) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        return this.$$delegate_0.fastCorrespondingSupertypes(simpleTypeMarker, constructor);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
    public SimpleTypeMarker findCommonIntegerLiteralTypesSuperType(List<? extends SimpleTypeMarker> explicitSupertypes) {
        Intrinsics.checkNotNullParameter(explicitSupertypes, "explicitSupertypes");
        return this.$$delegate_0.findCommonIntegerLiteralTypesSuperType(explicitSupertypes);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public TypeConstructorMarker freshTypeConstructor(TypeVariableMarker typeVariableMarker) {
        Intrinsics.checkNotNullParameter(typeVariableMarker, "<this>");
        return this.$$delegate_0.freshTypeConstructor(typeVariableMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public FunctionTypeKind functionTypeKind(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.functionTypeKind(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeArgumentMarker get(TypeArgumentListMarker typeArgumentListMarker, int i) {
        Intrinsics.checkNotNullParameter(typeArgumentListMarker, "<this>");
        return this.$$delegate_0.get(typeArgumentListMarker, i);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public KotlinTypeMarker getApproximatedIntegerLiteralType(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.getApproximatedIntegerLiteralType(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeArgumentMarker getArgument(KotlinTypeMarker kotlinTypeMarker, int i) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.getArgument(kotlinTypeMarker, i);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeArgumentMarker getArgumentOrNull(SimpleTypeMarker simpleTypeMarker, int i) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.getArgumentOrNull(simpleTypeMarker, i);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public List<TypeArgumentMarker> getArguments(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.getArguments(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public List<AnnotationMarker> getAttributes(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.getAttributes(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public List<AnnotationMarker> getCustomAttributes(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.getCustomAttributes(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public KotlinTypeMarker getFunctionTypeFromSupertypes(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.getFunctionTypeFromSupertypes(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public TypeConstructorMarker getNonReflectFunctionTypeConstructor(int parametersNumber, FunctionTypeKind kind) {
        Intrinsics.checkNotNullParameter(kind, "kind");
        return this.$$delegate_0.getNonReflectFunctionTypeConstructor(parametersNumber, kind);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public TypeVariableTypeConstructorMarker getOriginalTypeVariable(StubTypeMarker stubTypeMarker) {
        Intrinsics.checkNotNullParameter(stubTypeMarker, "<this>");
        return this.$$delegate_0.getOriginalTypeVariable(stubTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeParameterMarker getParameter(TypeConstructorMarker typeConstructorMarker, int i) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.getParameter(typeConstructorMarker, i);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public List<TypeParameterMarker> getParameters(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.getParameters(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public TypeConstructorMarker getReflectFunctionTypeConstructor(int parametersNumber, FunctionTypeKind kind) {
        Intrinsics.checkNotNullParameter(kind, "kind");
        return this.$$delegate_0.getReflectFunctionTypeConstructor(parametersNumber, kind);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker getType(TypeArgumentMarker typeArgumentMarker) {
        Intrinsics.checkNotNullParameter(typeArgumentMarker, "<this>");
        return this.$$delegate_0.getType(typeArgumentMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeConstructorMarker getTypeConstructor(TypeParameterMarker typeParameterMarker) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        return this.$$delegate_0.getTypeConstructor(typeParameterMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeParameterMarker getTypeParameter(TypeVariableTypeConstructorMarker typeVariableTypeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeVariableTypeConstructorMarker, "<this>");
        return this.$$delegate_0.getTypeParameter(typeVariableTypeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeParameterMarker getTypeParameterClassifier(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.getTypeParameterClassifier(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker getUpperBound(TypeParameterMarker typeParameterMarker, int i) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        return this.$$delegate_0.getUpperBound(typeParameterMarker, i);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public List<KotlinTypeMarker> getUpperBounds(TypeParameterMarker typeParameterMarker) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        return this.$$delegate_0.getUpperBounds(typeParameterMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeVariance getVariance(TypeArgumentMarker typeArgumentMarker) {
        Intrinsics.checkNotNullParameter(typeArgumentMarker, "<this>");
        return this.$$delegate_0.getVariance(typeArgumentMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeVariance getVariance(TypeParameterMarker typeParameterMarker) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        return this.$$delegate_0.getVariance(typeParameterMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean hasCustomAttributes(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.hasCustomAttributes(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public boolean hasExactAnnotation(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.hasExactAnnotation(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean hasFlexibleNullability(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.hasFlexibleNullability(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public boolean hasNoInferAnnotation(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.hasNoInferAnnotation(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
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

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker intersectTypes(List<? extends KotlinTypeMarker> types) {
        Intrinsics.checkNotNullParameter(types, "types");
        return this.$$delegate_0.intersectTypes(types);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    /* JADX INFO: renamed from: intersectTypes */
    public SimpleTypeMarker mo6107intersectTypes(List<? extends SimpleTypeMarker> types) {
        Intrinsics.checkNotNullParameter(types, "types");
        return this.$$delegate_0.intersectTypes((List<? extends KotlinTypeMarker>) types);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isAnonymous(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.isAnonymous(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isAnyConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.isAnyConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public boolean isBuiltinFunctionTypeOrSubtype(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.isBuiltinFunctionTypeOrSubtype(kotlinTypeMarker);
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

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public boolean isCapturedTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.isCapturedTypeConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isClassType(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.isClassType(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isClassTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.isClassTypeConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isCommonFinalClassConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.isCommonFinalClassConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public boolean isContainedInInvariantOrContravariantPositions(TypeVariableTypeConstructorMarker typeVariableTypeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeVariableTypeConstructorMarker, "<this>");
        return this.$$delegate_0.isContainedInInvariantOrContravariantPositions(typeVariableTypeConstructorMarker);
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

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isDenotable(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.isDenotable(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isDynamic(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.isDynamic(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isError(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.isError(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isError(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.isError(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
    public boolean isExtensionFunction(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.isExtensionFunction(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public boolean isExtensionFunctionType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.isExtensionFunctionType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public boolean isFinalClassConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.isFinalClassConstructor(typeConstructorMarker);
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

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public boolean isFunctionOrKFunctionWithAnySuspendability(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.isFunctionOrKFunctionWithAnySuspendability(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isIntegerConstantOperatorTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.isIntegerConstantOperatorTypeConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isIntegerLiteralConstantTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.isIntegerLiteralConstantTypeConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isIntegerLiteralType(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.isIntegerLiteralType(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isIntegerLiteralTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.isIntegerLiteralTypeConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isInterface(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.isInterface(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isIntersection(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.isIntersection(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public boolean isK2() {
        return this.$$delegate_0.isK2();
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isLocalType(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.isLocalType(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isMarkedNullable(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.isMarkedNullable(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isMarkedNullable(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.isMarkedNullable(simpleTypeMarker);
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

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
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

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isNullableType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.isNullableType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isOldCapturedType(CapturedTypeMarker capturedTypeMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
        return this.$$delegate_0.isOldCapturedType(capturedTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isPrimitiveType(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.isPrimitiveType(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isProjectionNotNull(CapturedTypeMarker capturedTypeMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
        return this.$$delegate_0.isProjectionNotNull(capturedTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isRawType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.isRawType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public boolean isSignedOrUnsignedNumberType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.isSignedOrUnsignedNumberType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isSimpleType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.isSimpleType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isSingleClassifierType(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.isSingleClassifierType(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public boolean isSpecial(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.isSpecial(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isStarProjection(TypeArgumentMarker typeArgumentMarker) {
        Intrinsics.checkNotNullParameter(typeArgumentMarker, "<this>");
        return this.$$delegate_0.isStarProjection(typeArgumentMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isStubType(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.isStubType(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isStubTypeForBuilderInference(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.isStubTypeForBuilderInference(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isStubTypeForVariableInSubtyping(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.isStubTypeForVariableInSubtyping(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isTypeParameterTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.isTypeParameterTypeConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public boolean isTypeVariable(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.isTypeVariable(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isTypeVariableType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.isTypeVariableType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isUninferredParameter(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.isUninferredParameter(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public boolean isUnit(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.isUnit(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public boolean isUnitTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.isUnitTypeConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public Iterator<TypeArgumentMarker> iterator(TypeArgumentListMarker typeArgumentListMarker) {
        Intrinsics.checkNotNullParameter(typeArgumentListMarker, "<this>");
        return this.$$delegate_0.iterator(typeArgumentListMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker lowerBound(FlexibleTypeMarker flexibleTypeMarker) {
        Intrinsics.checkNotNullParameter(flexibleTypeMarker, "<this>");
        return this.$$delegate_0.lowerBound(flexibleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker lowerBoundIfFlexible(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.lowerBoundIfFlexible(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker lowerType(CapturedTypeMarker capturedTypeMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
        return this.$$delegate_0.lowerType(capturedTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker makeDefinitelyNotNullOrNotNull(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.makeDefinitelyNotNullOrNotNull(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker makeSimpleTypeDefinitelyNotNullOrNotNull(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.makeSimpleTypeDefinitelyNotNullOrNotNull(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeCheckerProviderContext
    public TypeCheckerState newTypeCheckerState(boolean errorTypesEqualToAnything, boolean stubTypesEqualToAnything) {
        return this.$$delegate_0.newTypeCheckerState(errorTypesEqualToAnything, stubTypesEqualToAnything);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemBuiltInsContext
    public SimpleTypeMarker nothingType() {
        return this.$$delegate_0.nothingType();
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemBuiltInsContext
    public SimpleTypeMarker nullableAnyType() {
        return this.$$delegate_0.nullableAnyType();
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemBuiltInsContext
    public SimpleTypeMarker nullableNothingType() {
        return this.$$delegate_0.nullableNothingType();
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker original(DefinitelyNotNullTypeMarker definitelyNotNullTypeMarker) {
        Intrinsics.checkNotNullParameter(definitelyNotNullTypeMarker, "<this>");
        return this.$$delegate_0.original(definitelyNotNullTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker originalIfDefinitelyNotNullable(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.originalIfDefinitelyNotNullable(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public int parametersCount(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.parametersCount(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public Collection<KotlinTypeMarker> possibleIntegerTypes(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.possibleIntegerTypes(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeArgumentMarker projection(CapturedTypeConstructorMarker capturedTypeConstructorMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeConstructorMarker, "<this>");
        return this.$$delegate_0.projection(capturedTypeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public KotlinTypeMarker removeAnnotations(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.removeAnnotations(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public KotlinTypeMarker removeExactAnnotation(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.removeExactAnnotation(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public KotlinTypeMarker replaceArguments(KotlinTypeMarker kotlinTypeMarker, Function1<? super TypeArgumentMarker, ? extends TypeArgumentMarker> replacement) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        Intrinsics.checkNotNullParameter(replacement, "replacement");
        return this.$$delegate_0.replaceArguments(kotlinTypeMarker, replacement);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public SimpleTypeMarker replaceArguments(SimpleTypeMarker simpleTypeMarker, List<? extends TypeArgumentMarker> newArguments) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        Intrinsics.checkNotNullParameter(newArguments, "newArguments");
        return this.$$delegate_0.replaceArguments(simpleTypeMarker, newArguments);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public SimpleTypeMarker replaceArguments(SimpleTypeMarker simpleTypeMarker, Function1<? super TypeArgumentMarker, ? extends TypeArgumentMarker> replacement) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        Intrinsics.checkNotNullParameter(replacement, "replacement");
        return this.$$delegate_0.replaceArguments(simpleTypeMarker, replacement);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public KotlinTypeMarker replaceArgumentsDeeply(KotlinTypeMarker kotlinTypeMarker, Function1<? super TypeArgumentMarker, ? extends TypeArgumentMarker> replacement) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        Intrinsics.checkNotNullParameter(replacement, "replacement");
        return this.$$delegate_0.replaceArgumentsDeeply(kotlinTypeMarker, replacement);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public SimpleTypeMarker replaceArgumentsDeeply(SimpleTypeMarker simpleTypeMarker, Function1<? super TypeArgumentMarker, ? extends TypeArgumentMarker> replacement) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        Intrinsics.checkNotNullParameter(replacement, "replacement");
        return this.$$delegate_0.replaceArgumentsDeeply(simpleTypeMarker, replacement);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
    public KotlinTypeMarker replaceCustomAttributes(KotlinTypeMarker kotlinTypeMarker, List<? extends AnnotationMarker> newAttributes) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        Intrinsics.checkNotNullParameter(newAttributes, "newAttributes");
        return this.$$delegate_0.replaceCustomAttributes(kotlinTypeMarker, newAttributes);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeArgumentMarker replaceType(TypeArgumentMarker typeArgumentMarker, KotlinTypeMarker newType) {
        Intrinsics.checkNotNullParameter(typeArgumentMarker, "<this>");
        Intrinsics.checkNotNullParameter(newType, "newType");
        return this.$$delegate_0.replaceType(typeArgumentMarker, newType);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker safeSubstitute(TypeSubstitutorMarker typeSubstitutorMarker, KotlinTypeMarker type) {
        Intrinsics.checkNotNullParameter(typeSubstitutorMarker, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        return this.$$delegate_0.safeSubstitute(typeSubstitutorMarker, type);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public KotlinTypeMarker singleBestRepresentative(Collection<? extends KotlinTypeMarker> collection) {
        Intrinsics.checkNotNullParameter(collection, "<this>");
        return this.$$delegate_0.singleBestRepresentative(collection);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public int size(TypeArgumentListMarker typeArgumentListMarker) {
        Intrinsics.checkNotNullParameter(typeArgumentListMarker, "<this>");
        return this.$$delegate_0.size(typeArgumentListMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeCheckerState.SupertypesPolicy substitutionSupertypePolicy(SimpleTypeMarker type) {
        Intrinsics.checkNotNullParameter(type, "type");
        return this.$$delegate_0.substitutionSupertypePolicy(type);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public Collection<KotlinTypeMarker> supertypes(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.supertypes(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
    public SimpleTypeMarker toErrorType(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.toErrorType(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public CapturedTypeConstructorMarker typeConstructor(CapturedTypeMarker capturedTypeMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
        return this.$$delegate_0.typeConstructor(capturedTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeConstructorMarker typeConstructor(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.typeConstructor(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeConstructorMarker typeConstructor(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.typeConstructor(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public TypeArgumentMarker typeConstructorProjection(CapturedTypeMarker capturedTypeMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
        return this.$$delegate_0.typeConstructorProjection(capturedTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
    public int typeDepth(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.typeDepth(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
    public int typeDepth(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.typeDepth(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public TypeParameterMarker typeParameter(CapturedTypeMarker capturedTypeMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
        return this.$$delegate_0.typeParameter(capturedTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeSubstitutorMarker typeSubstitutorByTypeConstructor(Map<TypeConstructorMarker, ? extends KotlinTypeMarker> map) {
        Intrinsics.checkNotNullParameter(map, "map");
        return this.$$delegate_0.typeSubstitutorByTypeConstructor(map);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
    public List<AnnotationMarker> unionTypeAttributes(List<? extends KotlinTypeMarker> types) {
        Intrinsics.checkNotNullParameter(types, "types");
        return this.$$delegate_0.unionTypeAttributes(types);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeConstructorMarker unwrapStubTypeVariableConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.unwrapStubTypeVariableConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker upperBound(FlexibleTypeMarker flexibleTypeMarker) {
        Intrinsics.checkNotNullParameter(flexibleTypeMarker, "<this>");
        return this.$$delegate_0.upperBound(flexibleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public int upperBoundCount(TypeParameterMarker typeParameterMarker) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        return this.$$delegate_0.upperBoundCount(typeParameterMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker upperBoundIfFlexible(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.upperBoundIfFlexible(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public boolean useRefinedBoundsForTypeVariableInFlexiblePosition() {
        return this.$$delegate_0.useRefinedBoundsForTypeVariableInFlexiblePosition();
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public KotlinTypeMarker withNotNullProjection(CapturedTypeMarker capturedTypeMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
        return this.$$delegate_0.withNotNullProjection(capturedTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker withNullability(KotlinTypeMarker kotlinTypeMarker, boolean z) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.withNullability(kotlinTypeMarker, z);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker withNullability(SimpleTypeMarker simpleTypeMarker, boolean z) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.withNullability(simpleTypeMarker, z);
    }

    private TrivialConstraintTypeInferenceOracle(TypeSystemInferenceExtensionContext typeSystemInferenceExtensionContext) {
        this.$$delegate_0 = typeSystemInferenceExtensionContext;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public TrivialConstraintTypeInferenceOracle(TypeSystemInferenceExtensionContextDelegate context) {
        this((TypeSystemInferenceExtensionContext) context);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    public final boolean isNotInterestingConstraint(Constraint constraint) {
        Intrinsics.checkNotNullParameter(constraint, "constraint");
        return constraint.getKind() == ConstraintKind.LOWER && isNothingConstructor(typeConstructor(constraint.getType()));
    }

    public final boolean isSuitableResultedType(KotlinTypeMarker resultType) {
        Intrinsics.checkNotNullParameter(resultType, "resultType");
        return !isNothingConstructor(typeConstructor(resultType)) || (isK2() && isDynamic(resultType));
    }

    public final boolean isGeneratedConstraintTrivial(Constraint baseConstraint, Constraint otherConstraint, KotlinTypeMarker generatedConstraintType, boolean isSubtype) {
        Intrinsics.checkNotNullParameter(baseConstraint, "baseConstraint");
        Intrinsics.checkNotNullParameter(otherConstraint, "otherConstraint");
        Intrinsics.checkNotNullParameter(generatedConstraintType, "generatedConstraintType");
        if (isSubtype && (isNothing(generatedConstraintType) || isFlexibleNothing(generatedConstraintType))) {
            return true;
        }
        if (isSubtype || !isNullableAny(generatedConstraintType)) {
            return (contains(baseConstraint.getType(), new Function1<KotlinTypeMarker, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.inference.components.TrivialConstraintTypeInferenceOracle.isGeneratedConstraintTrivial.1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(KotlinTypeMarker it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return Boolean.valueOf(TrivialConstraintTypeInferenceOracle.this.isNothingOrNullableNothing(it));
                }
            }) || contains(otherConstraint.getType(), new Function1<KotlinTypeMarker, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.inference.components.TrivialConstraintTypeInferenceOracle.isGeneratedConstraintTrivial.2
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(KotlinTypeMarker it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return Boolean.valueOf(TrivialConstraintTypeInferenceOracle.this.isNothingOrNullableNothing(it));
                }
            }) || !containsOnlyNonNullableNothing(generatedConstraintType)) ? false : true;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isNothingOrNullableNothing(KotlinTypeMarker kotlinTypeMarker) {
        return isNothingConstructor(typeConstructor(kotlinTypeMarker));
    }

    private final boolean containsOnlyNonNullableNothing(KotlinTypeMarker kotlinTypeMarker) {
        return contains(kotlinTypeMarker, new Function1<KotlinTypeMarker, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.inference.components.TrivialConstraintTypeInferenceOracle.containsOnlyNonNullableNothing.1
            {
                super(1);
            }

            /* JADX WARN: Code duplicated, block: B:13:0x0030  */
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(KotlinTypeMarker it) {
                boolean z;
                Intrinsics.checkNotNullParameter(it, "it");
                if (TrivialConstraintTypeInferenceOracle.this.isNothing(it) || TrivialConstraintTypeInferenceOracle.this.isFlexibleNothing(it)) {
                    if (it instanceof SimpleTypeMarker) {
                        TrivialConstraintTypeInferenceOracle trivialConstraintTypeInferenceOracle = TrivialConstraintTypeInferenceOracle.this;
                        SimpleTypeMarker simpleTypeMarker = (SimpleTypeMarker) it;
                        if (trivialConstraintTypeInferenceOracle.isNothingConstructor(trivialConstraintTypeInferenceOracle.typeConstructor(simpleTypeMarker)) && TrivialConstraintTypeInferenceOracle.this.isMarkedNullable(simpleTypeMarker)) {
                            z = false;
                        }
                    }
                    z = true;
                } else {
                    z = false;
                }
                return Boolean.valueOf(z);
            }
        });
    }

    /* JADX INFO: compiled from: TrivialConstraintTypeInferenceOracle.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/components/TrivialConstraintTypeInferenceOracle$Companion;", "", "()V", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/resolve/calls/inference/components/TrivialConstraintTypeInferenceOracle;", "context", "Lorg/jetbrains/kotlin/types/model/TypeSystemInferenceExtensionContext;", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final TrivialConstraintTypeInferenceOracle create(TypeSystemInferenceExtensionContext context) {
            Intrinsics.checkNotNullParameter(context, "context");
            return new TrivialConstraintTypeInferenceOracle(context, null);
        }
    }
}
