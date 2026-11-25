package org.jetbrains.kotlin.types;

import androidx.core.app.NotificationCompat;
import com.reandroid.arsc.container.SpecTypePair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.Implementation;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.builtins.functions.FunctionTypeKind;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.resolve.calls.NewCommonSuperTypeCalculator;
import org.jetbrains.kotlin.resolve.checkers.EmptyIntersectionTypeInfo;
import org.jetbrains.kotlin.types.model.AnnotationMarker;
import org.jetbrains.kotlin.types.model.CaptureStatus;
import org.jetbrains.kotlin.types.model.CapturedTypeConstructorMarker;
import org.jetbrains.kotlin.types.model.CapturedTypeMarker;
import org.jetbrains.kotlin.types.model.DefinitelyNotNullTypeMarker;
import org.jetbrains.kotlin.types.model.DynamicTypeMarker;
import org.jetbrains.kotlin.types.model.FlexibleTypeMarker;
import org.jetbrains.kotlin.types.model.IntersectionTypeConstructorMarker;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.ObsoleteTypeKind;
import org.jetbrains.kotlin.types.model.SimpleTypeMarker;
import org.jetbrains.kotlin.types.model.StubTypeMarker;
import org.jetbrains.kotlin.types.model.TypeArgumentListMarker;
import org.jetbrains.kotlin.types.model.TypeArgumentMarker;
import org.jetbrains.kotlin.types.model.TypeCheckerProviderContext;
import org.jetbrains.kotlin.types.model.TypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeParameterMarker;
import org.jetbrains.kotlin.types.model.TypeSubstitutorMarker;
import org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext;
import org.jetbrains.kotlin.types.model.TypeVariableMarker;
import org.jetbrains.kotlin.types.model.TypeVariableTypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeVariance;

/* JADX INFO: compiled from: AbstractTypeApproximator.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000¦\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\"\n\u0002\bS\n\u0002\u0010(\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u001b\b&\u0018\u0000 \u009b\u00022\u00020\u0001:\u0004\u009a\u0002\u009b\u0002B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\t\u0010 \u001a\u00020\u0014H\u0096\u0001J*\u0010!\u001a\u0004\u0018\u00010\b2\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020\u00152\u0006\u0010%\u001a\u00020\u000e2\u0006\u0010&\u001a\u00020\u0016H\u0002J*\u0010'\u001a\u0004\u0018\u00010\b2\u0006\u0010\"\u001a\u00020(2\u0006\u0010$\u001a\u00020\u00152\u0006\u0010%\u001a\u00020\u000e2\u0006\u0010&\u001a\u00020\u0016H\u0002J*\u0010)\u001a\u0004\u0018\u00010\b2\u0006\u0010\"\u001a\u00020\u00142\u0006\u0010$\u001a\u00020\u00152\u0006\u0010%\u001a\u00020\u000e2\u0006\u0010&\u001a\u00020\u0016H\u0002J*\u0010*\u001a\u0004\u0018\u00010\u00142\u0006\u0010\"\u001a\u00020\u00142\u0006\u0010$\u001a\u00020\u00152\u0006\u0010%\u001a\u00020\u000e2\u0006\u0010&\u001a\u00020\u0016H\u0002J*\u0010+\u001a\u0004\u0018\u00010\u00142\u0006\u0010\"\u001a\u00020\u00142\u0006\u0010$\u001a\u00020\u00152\u0006\u0010%\u001a\u00020\u000e2\u0006\u0010&\u001a\u00020\u0016H\u0002J\"\u0010,\u001a\u0004\u0018\u00010\b2\u0006\u0010\"\u001a\u00020\u00142\u0006\u0010$\u001a\u00020\u00152\u0006\u0010&\u001a\u00020\u0016H\u0002J\"\u0010-\u001a\u0004\u0018\u00010\b2\u0006\u0010\"\u001a\u00020\u00142\u0006\u0010$\u001a\u00020\u00152\u0006\u0010&\u001a\u00020\u0016H\u0002Jl\u0010.\u001a\u0004\u0018\u00010\b2\u0006\u0010\"\u001a\u00020\b2\u0006\u0010$\u001a\u00020\u00152\u0017\u0010/\u001a\u0013\u0012\u0004\u0012\u000201\u0012\u0004\u0012\u00020\u001400¢\u0006\u0002\b22/\u0010.\u001a+\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u0015\u0012\u0013\u0012\u00110\u0016¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(&\u0012\u0006\u0012\u0004\u0018\u00010\b0\u00132\u0006\u0010&\u001a\u00020\u0016H\u0002J*\u0010.\u001a\u0004\u0018\u00010\b2\u0006\u0010\"\u001a\u00020\u00142\u0006\u0010$\u001a\u00020\u00152\u0006\u0010%\u001a\u00020\u000e2\u0006\u0010&\u001a\u00020\u0016H\u0002J\u0018\u00105\u001a\u0004\u0018\u00010\b2\u0006\u0010\"\u001a\u00020\b2\u0006\u0010$\u001a\u00020\u0015J\"\u00105\u001a\u0004\u0018\u00010\b2\u0006\u0010\"\u001a\u00020\b2\u0006\u0010$\u001a\u00020\u00152\u0006\u0010&\u001a\u00020\u0016H\u0002J\u0018\u00106\u001a\u0004\u0018\u00010\b2\u0006\u0010\"\u001a\u00020\b2\u0006\u0010$\u001a\u00020\u0015J\"\u00106\u001a\u0004\u0018\u00010\b2\u0006\u0010\"\u001a\u00020\b2\u0006\u0010$\u001a\u00020\u00152\u0006\u0010&\u001a\u00020\u0016H\u0002J\u0019\u00107\u001a\u00020\u000e2\u0006\u00108\u001a\u0002092\u0006\u0010:\u001a\u000209H\u0096\u0001J3\u0010;\u001a\u0004\u0018\u00010\b2\u0006\u0010\"\u001a\u00020\b2\u0006\u0010$\u001a\u00020\u00152\u0006\u0010%\u001a\u00020\u000e2\u000e\u0010<\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\b0=H\u0082\bJ\u001b\u0010>\u001a\u0004\u0018\u00010\u00142\u0006\u0010\"\u001a\u00020\u00142\u0006\u0010?\u001a\u00020@H\u0096\u0001J\u0013\u0010A\u001a\u0004\u0018\u00010\b2\u0006\u0010\"\u001a\u00020\bH\u0096\u0001J*\u0010B\u001a\u0004\u0018\u00010\t2\u0006\u0010\"\u001a\u00020\b2\u0006\u0010&\u001a\u00020\u00162\u0006\u0010$\u001a\u00020\u00152\u0006\u0010%\u001a\u00020\u000eH\u0002J\u0006\u0010C\u001a\u00020DJ\u0019\u0010E\u001a\u0004\u0018\u00010F2\f\u0010G\u001a\b\u0012\u0004\u0012\u00020\b0HH\u0096\u0001J!\u0010I\u001a\u0004\u0018\u00010\u00142\u0006\u0010J\u001a\u00020\u001d2\f\u0010K\u001a\b\u0012\u0004\u0012\u00020\b0LH\u0096\u0001J1\u0010M\u001a\u00020#2\u0006\u0010N\u001a\u00020O2\f\u0010P\u001a\b\u0012\u0004\u0012\u00020\b0L2\b\u0010Q\u001a\u0004\u0018\u00010\b2\u0006\u0010R\u001a\u00020@H\u0096\u0001J\t\u0010S\u001a\u00020TH\u0096\u0001J\u0011\u0010U\u001a\u00020\u00142\u0006\u0010V\u001a\u00020WH\u0096\u0001J\u0019\u0010X\u001a\u00020\b2\u0006\u0010Y\u001a\u00020\u00142\u0006\u0010Z\u001a\u00020\u0014H\u0096\u0001J?\u0010[\u001a\u00020\u00142\u0006\u0010\\\u001a\u0002092\f\u0010]\u001a\b\u0012\u0004\u0012\u00020O0L2\u0006\u0010^\u001a\u00020\u000e2\u0006\u0010_\u001a\u00020\u000e2\u000e\u0010`\u001a\n\u0012\u0004\u0012\u00020a\u0018\u00010LH\u0096\u0001J\u0011\u0010b\u001a\u00020O2\u0006\u0010\u001b\u001a\u00020\u001cH\u0096\u0001J\u0011\u0010c\u001a\u00020d2\u0006\u0010J\u001a\u00020eH\u0096\u0001J\u0011\u0010f\u001a\u00020d2\u0006\u0010J\u001a\u00020eH\u0096\u0001J\u0013\u0010g\u001a\u0004\u0018\u00010T2\u0006\u0010h\u001a\u00020\bH\u0096\u0001J\u0019\u0010i\u001a\u00020O2\u0006\u0010\"\u001a\u00020\b2\u0006\u0010j\u001a\u00020kH\u0096\u0001J\u0019\u0010l\u001a\u00020\b2\u0006\u0010m\u001a\u00020\b2\u0006\u0010n\u001a\u00020\bH\u0096\u0001J\u0011\u0010o\u001a\u00020\b2\u0006\u0010\\\u001a\u000209H\u0096\u0001J\u0019\u0010p\u001a\u0004\u0018\u00010\u00142\f\u0010q\u001a\b\u0012\u0004\u0012\u00020\u00140LH\u0096\u0001J\u0019\u0010r\u001a\u0002092\u0006\u0010s\u001a\u00020\u00162\u0006\u0010t\u001a\u00020uH\u0096\u0001J\u0019\u0010v\u001a\u0002092\u0006\u0010s\u001a\u00020\u00162\u0006\u0010t\u001a\u00020uH\u0096\u0001J\u0019\u0010w\u001a\u00020\u000e2\u0006\u0010x\u001a\u00020\u00142\u0006\u0010y\u001a\u00020\u0014H\u0096\u0001J\u0017\u0010z\u001a\u00020\b2\f\u0010G\u001a\b\u0012\u0004\u0012\u00020\b0LH\u0096\u0001J\u0017\u0010z\u001a\u00020\u00142\f\u0010G\u001a\b\u0012\u0004\u0012\u00020\u00140LH\u0096\u0001J\u0018\u0010{\u001a\u00020\u000e2\u0006\u0010|\u001a\u00020k2\u0006\u0010%\u001a\u00020\u000eH\u0002J\u0010\u0010}\u001a\u00020\u000e2\u0006\u0010\\\u001a\u00020~H\u0002J\u001c\u0010\u007f\u001a\u00030\u0080\u00012\u0007\u0010\u0081\u0001\u001a\u00020\u000e2\u0007\u0010\u0082\u0001\u001a\u00020\u000eH\u0096\u0001J\n\u0010\u0083\u0001\u001a\u00020\u0014H\u0096\u0001J\n\u0010\u0084\u0001\u001a\u00020\u0014H\u0096\u0001J\n\u0010\u0085\u0001\u001a\u00020\u0014H\u0096\u0001J+\u0010\u0086\u0001\u001a\u00020\u000e2\u0007\u0010\u0087\u0001\u001a\u00020\b2\u0007\u0010\u0088\u0001\u001a\u00020\b2\u0006\u0010$\u001a\u00020\u00152\u0006\u0010&\u001a\u00020\u0016H\u0002J\u0013\u0010\u0089\u0001\u001a\u00030\u008a\u00012\u0006\u0010\"\u001a\u00020\u0014H\u0096\u0001J \u0010\u008b\u0001\u001a\u00020T2\u0014\u0010\u008c\u0001\u001a\u000f\u0012\u0004\u0012\u000209\u0012\u0004\u0012\u00020\b0\u008d\u0001H\u0096\u0001J\u001e\u0010\u008e\u0001\u001a\b\u0012\u0004\u0012\u00020a0L2\f\u0010G\u001a\b\u0012\u0004\u0012\u00020\b0LH\u0096\u0001J\n\u0010\u008f\u0001\u001a\u00020\u000eH\u0096\u0001J#\u0010\u0090\u0001\u001a\u00020\u000e*\u00020\b2\u0013\u0010\u0091\u0001\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u000e00H\u0096\u0001J\u000e\u0010\u0092\u0001\u001a\u00020\u0016*\u00020\bH\u0096\u0001J\u000f\u0010\u0093\u0001\u001a\u00030\u0094\u0001*\u00020\u0014H\u0096\u0001J\u0010\u0010\u0095\u0001\u001a\u0004\u0018\u00010#*\u00020\u0014H\u0096\u0001J\u0010\u0010\u0096\u0001\u001a\u0004\u0018\u00010(*\u00020\u0014H\u0096\u0001J\u0011\u0010\u0097\u0001\u001a\u0005\u0018\u00010\u0098\u0001*\u000201H\u0096\u0001J\u0010\u0010\u0099\u0001\u001a\u0004\u0018\u000101*\u00020\bH\u0096\u0001J\u0010\u0010\u009a\u0001\u001a\u0004\u0018\u00010\u0014*\u00020\bH\u0096\u0001J\u000e\u0010\u009b\u0001\u001a\u00020O*\u00020\bH\u0096\u0001J\u000e\u0010\u009c\u0001\u001a\u00020\u000e*\u00020\bH\u0096\u0001J\r\u0010R\u001a\u00020@*\u00020#H\u0096\u0001J#\u0010\u009d\u0001\u001a\u00020\u000e*\u00020\b2\u0013\u0010\u0091\u0001\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u000e00H\u0096\u0001J\u000e\u0010\u009e\u0001\u001a\u00020\b*\u00020\bH\u0096\u0001J\u0015\u0010\u009f\u0001\u001a\u00020\u0014*\u00020\b2\u0006\u0010%\u001a\u00020\u000eH\u0002J\u000e\u0010 \u0001\u001a\u00020\u0014*\u00020eH\u0096\u0001J\u000e\u0010¡\u0001\u001a\u00020\b*\u00020\bH\u0096\u0001J\u0014\u0010¢\u0001\u001a\b\u0012\u0004\u0012\u00020\b0L*\u00020\bH\u0096\u0001J\u0015\u0010£\u0001\u001a\t\u0012\u0004\u0012\u00020\u001c0¤\u0001*\u00020\bH\u0096\u0001J\u0015\u0010¥\u0001\u001a\t\u0012\u0004\u0012\u00020\u001d0¤\u0001*\u00020\bH\u0096\u0001J\u001e\u0010¦\u0001\u001a\n\u0012\u0004\u0012\u00020\u0014\u0018\u00010L*\u00020\u00142\u0006\u0010\\\u001a\u000209H\u0096\u0001J\u000e\u0010§\u0001\u001a\u000209*\u00020eH\u0096\u0001J\u0010\u0010¨\u0001\u001a\u0004\u0018\u00010u*\u00020\bH\u0096\u0001J\u0018\u0010©\u0001\u001a\u00020O*\u00030\u0094\u00012\u0007\u0010ª\u0001\u001a\u00020\u0016H\u0096\u0003J\u000e\u0010«\u0001\u001a\u00020\b*\u000209H\u0096\u0001J\u0017\u0010¬\u0001\u001a\u00020O*\u00020\b2\u0007\u0010ª\u0001\u001a\u00020\u0016H\u0096\u0001J\u0019\u0010\u00ad\u0001\u001a\u0004\u0018\u00010O*\u00020\u00142\u0007\u0010ª\u0001\u001a\u00020\u0016H\u0096\u0001J\u0014\u0010®\u0001\u001a\b\u0012\u0004\u0012\u00020O0L*\u00020\bH\u0096\u0001J\u0014\u0010¯\u0001\u001a\b\u0012\u0004\u0012\u00020a0L*\u00020\bH\u0096\u0001J\u0014\u0010°\u0001\u001a\b\u0012\u0004\u0012\u00020a0L*\u00020\bH\u0096\u0001J\u000e\u0010±\u0001\u001a\u00020\b*\u00020\bH\u0096\u0001J\u000e\u0010²\u0001\u001a\u00020\u001d*\u00020dH\u0096\u0001J\u0017\u0010³\u0001\u001a\u00020\u001c*\u0002092\u0007\u0010ª\u0001\u001a\u00020\u0016H\u0096\u0001J\u0014\u0010´\u0001\u001a\b\u0012\u0004\u0012\u00020\u001c0L*\u000209H\u0096\u0001J\u000e\u0010µ\u0001\u001a\u00020\b*\u00020OH\u0096\u0001J\u000e\u0010¶\u0001\u001a\u000209*\u00020\u001cH\u0096\u0001J\u0010\u0010·\u0001\u001a\u0004\u0018\u00010\u001c*\u000209H\u0096\u0001J\u0017\u0010¸\u0001\u001a\u00020\b*\u00020\u001c2\u0007\u0010ª\u0001\u001a\u00020\u0016H\u0096\u0001J\u0014\u0010¹\u0001\u001a\b\u0012\u0004\u0012\u00020\b0L*\u00020\u001cH\u0096\u0001J\u000e\u0010º\u0001\u001a\u00020k*\u00020OH\u0096\u0001J\u000e\u0010º\u0001\u001a\u00020k*\u00020\u001cH\u0096\u0001J\u000e\u0010»\u0001\u001a\u00020\u000e*\u00020\bH\u0096\u0001J\u000e\u0010¼\u0001\u001a\u00020\u000e*\u00020\bH\u0096\u0001J\u000e\u0010½\u0001\u001a\u00020\u000e*\u00020\bH\u0096\u0001J\u000e\u0010¾\u0001\u001a\u00020\u000e*\u00020\bH\u0096\u0001J\u0019\u0010¿\u0001\u001a\u00020\u000e*\u00020\u001c2\t\u0010À\u0001\u001a\u0004\u0018\u000109H\u0096\u0001J\u000e\u0010Á\u0001\u001a\u00020\u000e*\u000209H\u0096\u0001J\u000e\u0010Â\u0001\u001a\u00020\u000e*\u000209H\u0096\u0001J\u000e\u0010Ã\u0001\u001a\u00020\u000e*\u00020\bH\u0096\u0001J\u000e\u0010Ä\u0001\u001a\u00020\u000e*\u00020\bH\u0096\u0001J\u000e\u0010Å\u0001\u001a\u00020\u000e*\u00020\bH\u0096\u0001J\u000e\u0010Æ\u0001\u001a\u00020\u000e*\u000209H\u0096\u0001J\u000e\u0010Ç\u0001\u001a\u00020\u000e*\u00020\u0014H\u0096\u0001J\u000e\u0010È\u0001\u001a\u00020\u000e*\u000209H\u0096\u0001J\u000e\u0010É\u0001\u001a\u00020\u000e*\u000209H\u0096\u0001J\u000e\u0010Ê\u0001\u001a\u00020\u000e*\u00020\u001dH\u0096\u0001J\u000e\u0010Ë\u0001\u001a\u00020\u000e*\u000209H\u0096\u0001J\u000e\u0010Ì\u0001\u001a\u00020\u000e*\u00020\bH\u0096\u0001J\u000e\u0010Í\u0001\u001a\u00020\u000e*\u000209H\u0096\u0001J\u000e\u0010Î\u0001\u001a\u00020\u000e*\u00020\bH\u0096\u0001J\u000e\u0010Ï\u0001\u001a\u00020\u000e*\u00020\bH\u0096\u0001J\u000e\u0010Ï\u0001\u001a\u00020\u000e*\u000209H\u0096\u0001J\r\u0010_\u001a\u00020\u000e*\u00020\u0014H\u0096\u0001J\u000e\u0010Ð\u0001\u001a\u00020\u000e*\u00020\bH\u0096\u0001J\u000e\u0010Ñ\u0001\u001a\u00020\u000e*\u000209H\u0096\u0001J\u000e\u0010Ò\u0001\u001a\u00020\u000e*\u00020\bH\u0096\u0001J\u000e\u0010Ó\u0001\u001a\u00020\u000e*\u00020\bH\u0096\u0001J\u000e\u0010Ô\u0001\u001a\u00020\u000e*\u00020\bH\u0096\u0001J\u000e\u0010Õ\u0001\u001a\u00020\u000e*\u000209H\u0096\u0001J\u000e\u0010Ö\u0001\u001a\u00020\u000e*\u000209H\u0096\u0001J\u000e\u0010×\u0001\u001a\u00020\u000e*\u00020\u0014H\u0096\u0001J\u000e\u0010Ø\u0001\u001a\u00020\u000e*\u000209H\u0096\u0001J\u000e\u0010Ù\u0001\u001a\u00020\u000e*\u000209H\u0096\u0001J\u000e\u0010Ú\u0001\u001a\u00020\u000e*\u000209H\u0096\u0001J\u000e\u0010Û\u0001\u001a\u00020\u000e*\u000209H\u0096\u0001J\u000e\u0010Ü\u0001\u001a\u00020\u000e*\u00020\bH\u0096\u0001J\u000e\u0010Ü\u0001\u001a\u00020\u000e*\u00020\u0014H\u0096\u0001J\u000e\u0010Ý\u0001\u001a\u00020\u000e*\u00020\bH\u0097\u0001J\u000e\u0010Þ\u0001\u001a\u00020\u000e*\u00020\bH\u0096\u0001J\u000e\u0010ß\u0001\u001a\u00020\u000e*\u000209H\u0096\u0001J\u000e\u0010à\u0001\u001a\u00020\u000e*\u00020\bH\u0096\u0001J\u000e\u0010á\u0001\u001a\u00020\u000e*\u00020\bH\u0096\u0001J\u000e\u0010â\u0001\u001a\u00020\u000e*\u00020\bH\u0096\u0001J\u000e\u0010ã\u0001\u001a\u00020\u000e*\u00020#H\u0096\u0001J\u000e\u0010ä\u0001\u001a\u00020\u000e*\u00020\u0014H\u0096\u0001J\u000e\u0010å\u0001\u001a\u00020\u000e*\u00020#H\u0096\u0001J\u000e\u0010æ\u0001\u001a\u00020\u000e*\u00020\bH\u0096\u0001J\u000e\u0010ç\u0001\u001a\u00020\u000e*\u00020\bH\u0096\u0001J\u000e\u0010è\u0001\u001a\u00020\u000e*\u00020\bH\u0096\u0001J\u000e\u0010é\u0001\u001a\u00020\u000e*\u00020\u0014H\u0096\u0001J\u000e\u0010ê\u0001\u001a\u00020\u000e*\u00020\bH\u0096\u0001J\u000e\u0010ë\u0001\u001a\u00020\u000e*\u00020OH\u0096\u0001J\u000e\u0010ì\u0001\u001a\u00020\u000e*\u00020\u0014H\u0096\u0001J\u000e\u0010í\u0001\u001a\u00020\u000e*\u00020\u0014H\u0096\u0001J\u000e\u0010î\u0001\u001a\u00020\u000e*\u00020\u0014H\u0096\u0001J\r\u0010ï\u0001\u001a\u00020\u000e*\u00020\bH\u0002J\r\u0010ð\u0001\u001a\u00020\u000e*\u00020\bH\u0002J\u000e\u0010ñ\u0001\u001a\u00020\u000e*\u000209H\u0096\u0001J\u000e\u0010ò\u0001\u001a\u00020\u000e*\u000209H\u0096\u0001J\u000e\u0010ó\u0001\u001a\u00020\u000e*\u00020\bH\u0096\u0001J\u000e\u0010ô\u0001\u001a\u00020\u000e*\u00020\bH\u0096\u0001J\u000e\u0010õ\u0001\u001a\u00020\u000e*\u00020\bH\u0096\u0001J\u000e\u0010ö\u0001\u001a\u00020\u000e*\u000209H\u0096\u0001J\u0016\u0010÷\u0001\u001a\t\u0012\u0004\u0012\u00020O0ø\u0001*\u00030\u0094\u0001H\u0096\u0003J\r\u0010Y\u001a\u00020\u0014*\u000201H\u0096\u0001J\u000e\u0010ù\u0001\u001a\u00020\u0014*\u00020\bH\u0096\u0001J\u000f\u0010Q\u001a\u0004\u0018\u00010\b*\u00020#H\u0096\u0001J\u000e\u0010ú\u0001\u001a\u00020\b*\u00020\bH\u0096\u0001J\u000e\u0010û\u0001\u001a\u00020\u0014*\u00020\u0014H\u0096\u0001J\u000e\u0010ü\u0001\u001a\u00020\u0014*\u00020(H\u0096\u0001J\u000e\u0010ý\u0001\u001a\u00020\u0014*\u00020\u0014H\u0096\u0001J\u000e\u0010þ\u0001\u001a\u00020\u0016*\u000209H\u0096\u0001J\u0014\u0010ÿ\u0001\u001a\b\u0012\u0004\u0012\u00020\b0H*\u00020\u0014H\u0096\u0001J\u000f\u0010\u0080\u0002\u001a\u00020O*\u00030\u0081\u0002H\u0096\u0001J\u000e\u0010\u0082\u0002\u001a\u00020\b*\u00020\bH\u0096\u0001J\u000e\u0010\u0083\u0002\u001a\u00020\b*\u00020\bH\u0096\u0001J#\u0010\u0084\u0002\u001a\u00020\b*\u00020\b2\u0013\u0010\u0085\u0002\u001a\u000e\u0012\u0004\u0012\u00020O\u0012\u0004\u0012\u00020O00H\u0096\u0001J#\u0010\u0084\u0002\u001a\u00020\u0014*\u00020\u00142\u0013\u0010\u0085\u0002\u001a\u000e\u0012\u0004\u0012\u00020O\u0012\u0004\u0012\u00020O00H\u0096\u0001J\u001d\u0010\u0084\u0002\u001a\u00020\u0014*\u00020\u00142\r\u0010\u0086\u0002\u001a\b\u0012\u0004\u0012\u00020O0LH\u0096\u0001J#\u0010\u0087\u0002\u001a\u00020\b*\u00020\b2\u0013\u0010\u0085\u0002\u001a\u000e\u0012\u0004\u0012\u00020O\u0012\u0004\u0012\u00020O00H\u0096\u0001J#\u0010\u0087\u0002\u001a\u00020\u0014*\u00020\u00142\u0013\u0010\u0085\u0002\u001a\u000e\u0012\u0004\u0012\u00020O\u0012\u0004\u0012\u00020O00H\u0096\u0001J\u001d\u0010\u0088\u0002\u001a\u00020\b*\u00020\b2\r\u0010\u0089\u0002\u001a\b\u0012\u0004\u0012\u00020a0LH\u0096\u0001J\u0017\u0010\u008a\u0002\u001a\u00020O*\u00020O2\u0007\u0010\u008b\u0002\u001a\u00020\bH\u0096\u0001J\u0016\u0010\u008c\u0002\u001a\u00020\b*\u00020T2\u0006\u0010\"\u001a\u00020\bH\u0096\u0001J\u0016\u0010\u008d\u0002\u001a\u0004\u0018\u00010\b*\b\u0012\u0004\u0012\u00020\b0HH\u0096\u0001J\u000f\u0010\u008e\u0002\u001a\u00020\u0016*\u00030\u0094\u0001H\u0096\u0001J\u0014\u0010\u008f\u0002\u001a\b\u0012\u0004\u0012\u00020\b0H*\u000209H\u0096\u0001J\u000f\u0010\u0090\u0002\u001a\u00020\t*\u0004\u0018\u00010\bH\u0002J\u000e\u0010\u0091\u0002\u001a\u00020\u0014*\u000209H\u0096\u0001J\u000f\u0010\u0092\u0002\u001a\u00030\u0081\u0002*\u00020#H\u0096\u0001J\u000e\u0010\u0092\u0002\u001a\u000209*\u00020\bH\u0096\u0001J\u000e\u0010\u0092\u0002\u001a\u000209*\u00020\u0014H\u0096\u0001J\u000e\u0010\u0093\u0002\u001a\u00020O*\u00020#H\u0096\u0001J\u000e\u0010\u0094\u0002\u001a\u00020\u0016*\u00020\bH\u0096\u0001J\u000e\u0010\u0094\u0002\u001a\u00020\u0016*\u00020\u0014H\u0096\u0001J\u000e\u0010\u001b\u001a\u0004\u0018\u00010\u001c*\u00020#H\u0016J\u000e\u0010\u0095\u0002\u001a\u000209*\u000209H\u0096\u0001J\r\u0010Z\u001a\u00020\u0014*\u000201H\u0096\u0001J\u000e\u0010\u0096\u0002\u001a\u00020\u0016*\u00020\u001cH\u0096\u0001J\u000e\u0010\u0097\u0002\u001a\u00020\u0014*\u00020\bH\u0096\u0001J\u000e\u0010\u0098\u0002\u001a\u00020\b*\u00020#H\u0096\u0001J\u0016\u0010\u0099\u0002\u001a\u00020\b*\u00020\b2\u0006\u0010^\u001a\u00020\u000eH\u0096\u0001J\u0016\u0010\u0099\u0002\u001a\u00020\u0014*\u00020\u00142\u0006\u0010^\u001a\u00020\u000eH\u0096\u0001R\u001a\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0012\u0010\r\u001a\u00020\u000eX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\r\u0010\u000fR\u0014\u0010\u0003\u001a\u00020\u0004X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R.\u0010\u0012\u001a\u001c\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u0016\u0012\u0006\u0012\u0004\u0018\u00010\b0\u00138BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R.\u0010\u0019\u001a\u001c\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u0016\u0012\u0006\u0012\u0004\u0018\u00010\b0\u00138BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u0018R\u0018\u0010\u001b\u001a\u0004\u0018\u00010\u001c*\u00020\u001dX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u001f¨\u0006\u009c\u0002"}, d2 = {"Lorg/jetbrains/kotlin/types/AbstractTypeApproximator;", "Lorg/jetbrains/kotlin/types/model/TypeSystemInferenceExtensionContext;", "ctx", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "(Lorg/jetbrains/kotlin/types/model/TypeSystemInferenceExtensionContext;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;)V", "cacheForIncorporationConfigToSubtypeDirection", "Ljava/util/concurrent/ConcurrentHashMap;", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "Lorg/jetbrains/kotlin/types/AbstractTypeApproximator$ApproximationResult;", "cacheForIncorporationConfigToSuperDirection", "getCtx", "()Lorg/jetbrains/kotlin/types/model/TypeSystemInferenceExtensionContext;", "isK2", "", "()Z", "getLanguageVersionSettings", "()Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "referenceApproximateToSubType", "Lkotlin/Function3;", "Lorg/jetbrains/kotlin/types/model/SimpleTypeMarker;", "Lorg/jetbrains/kotlin/types/TypeApproximatorConfiguration;", "", "getReferenceApproximateToSubType", "()Lkotlin/jvm/functions/Function3;", "referenceApproximateToSuperType", "getReferenceApproximateToSuperType", "typeParameter", "Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;", "Lorg/jetbrains/kotlin/types/model/TypeVariableTypeConstructorMarker;", "getTypeParameter", "(Lorg/jetbrains/kotlin/types/model/TypeVariableTypeConstructorMarker;)Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;", SchemaSymbols.ATTVAL_ANYTYPE, "approximateCapturedType", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/model/CapturedTypeMarker;", "conf", "toSuper", ConfigConstants.CONFIG_KEY_DEPTH, "approximateDefinitelyNotNullType", "Lorg/jetbrains/kotlin/types/model/DefinitelyNotNullTypeMarker;", "approximateIntersectionType", "approximateLocalTypes", "approximateParametrizedType", "approximateSimpleToSubType", "approximateSimpleToSuperType", "approximateTo", "bound", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/types/model/FlexibleTypeMarker;", "Lkotlin/ExtensionFunctionType;", "Lkotlin/ParameterName;", "name", "approximateToSubType", "approximateToSuperType", "areEqualTypeConstructors", "c1", "Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;", "c2", Implementation.Context.Default.FIELD_CACHE_PREFIX, "approximate", "Lkotlin/Function0;", "captureFromArguments", NotificationCompat.CATEGORY_STATUS, "Lorg/jetbrains/kotlin/types/model/CaptureStatus;", "captureFromExpression", "checkExceptionalCases", "clearCache", "", "computeEmptyIntersectionTypeKind", "Lorg/jetbrains/kotlin/resolve/checkers/EmptyIntersectionTypeInfo;", SpecTypePair.NAME_types, "", "createCapturedStarProjectionForSelfType", "typeVariable", "typesForRecursiveTypeParameters", "", "createCapturedType", "constructorProjection", "Lorg/jetbrains/kotlin/types/model/TypeArgumentMarker;", "constructorSupertypes", "lowerType", "captureStatus", "createEmptySubstitutor", "Lorg/jetbrains/kotlin/types/model/TypeSubstitutorMarker;", "createErrorType", "debugName", "", "createFlexibleType", "lowerBound", "upperBound", "createSimpleType", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "arguments", "nullable", "isExtensionFunction", "attributes", "Lorg/jetbrains/kotlin/types/model/AnnotationMarker;", "createStarProjection", "createStubTypeForBuilderInference", "Lorg/jetbrains/kotlin/types/model/StubTypeMarker;", "Lorg/jetbrains/kotlin/types/model/TypeVariableMarker;", "createStubTypeForTypeVariablesInSubtyping", "createSubstitutorForSuperTypes", "baseType", "createTypeArgument", "variance", "Lorg/jetbrains/kotlin/types/model/TypeVariance;", "createTypeWithAlternativeForIntersectionResult", "firstCandidate", "secondCandidate", "createUninferredType", "findCommonIntegerLiteralTypesSuperType", "explicitSupertypes", "getNonReflectFunctionTypeConstructor", "parametersNumber", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/builtins/functions/FunctionTypeKind;", "getReflectFunctionTypeConstructor", "identicalArguments", "a", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "intersectTypes", "isApproximateDirectionToSuper", "effectiveVariance", "isIntersectionTypeEffectivelyNothing", "Lorg/jetbrains/kotlin/types/model/IntersectionTypeConstructorMarker;", "newTypeCheckerState", "Lorg/jetbrains/kotlin/types/TypeCheckerState;", "errorTypesEqualToAnything", "stubTypesEqualToAnything", "nothingType", "nullableAnyType", "nullableNothingType", "shouldUseSubTypeForCapturedArgument", "subType", "capturedArgumentType", "substitutionSupertypePolicy", "Lorg/jetbrains/kotlin/types/TypeCheckerState$SupertypesPolicy;", "typeSubstitutorByTypeConstructor", "map", "", "unionTypeAttributes", "useRefinedBoundsForTypeVariableInFlexiblePosition", "anySuperTypeConstructor", "predicate", "argumentsCount", "asArgumentList", "Lorg/jetbrains/kotlin/types/model/TypeArgumentListMarker;", "asCapturedType", "asDefinitelyNotNullType", "asDynamicType", "Lorg/jetbrains/kotlin/types/model/DynamicTypeMarker;", "asFlexibleType", "asSimpleType", "asTypeArgument", "canHaveUndefinedNullability", "contains", "convertToNonRaw", "defaultResult", "defaultType", "eraseContainingTypeParameters", "extractArgumentsForFunctionTypeOrSubtype", "extractTypeParameters", "", "extractTypeVariables", "fastCorrespondingSupertypes", "freshTypeConstructor", "functionTypeKind", "get", "index", "getApproximatedIntegerLiteralType", "getArgument", "getArgumentOrNull", "getArguments", "getAttributes", "getCustomAttributes", "getFunctionTypeFromSupertypes", "getOriginalTypeVariable", "getParameter", "getParameters", "getType", "getTypeConstructor", "getTypeParameterClassifier", "getUpperBound", "getUpperBounds", "getVariance", "hasCustomAttributes", "hasExactAnnotation", "hasFlexibleNullability", "hasNoInferAnnotation", "hasRecursiveBounds", "selfConstructor", "isAnonymous", "isAnyConstructor", "isBuiltinFunctionTypeOrSubtype", "isCapturedDynamic", "isCapturedType", "isCapturedTypeConstructor", "isClassType", "isClassTypeConstructor", "isCommonFinalClassConstructor", "isContainedInInvariantOrContravariantPositions", "isDefinitelyClassTypeConstructor", "isDefinitelyNotNullType", "isDenotable", "isDynamic", "isError", "isExtensionFunctionType", "isFinalClassConstructor", "isFlexible", "isFlexibleNothing", "isFunctionOrKFunctionWithAnySuspendability", "isIntegerConstantOperatorTypeConstructor", "isIntegerLiteralConstantTypeConstructor", "isIntegerLiteralType", "isIntegerLiteralTypeConstructor", "isInterface", "isIntersection", "isLocalType", "isMarkedNullable", "isNotNullTypeParameter", "isNothing", "isNothingConstructor", "isNullableAny", "isNullableNothing", "isNullableType", "isOldCapturedType", "isPrimitiveType", "isProjectionNotNull", "isRawType", "isSignedOrUnsignedNumberType", "isSimpleType", "isSingleClassifierType", "isSpecial", "isStarProjection", "isStubType", "isStubTypeForBuilderInference", "isStubTypeForVariableInSubtyping", "isTrivialSub", "isTrivialSuper", "isTypeParameterTypeConstructor", "isTypeVariable", "isTypeVariableType", "isUninferredParameter", "isUnit", "isUnitTypeConstructor", "iterator", "", "lowerBoundIfFlexible", "makeDefinitelyNotNullOrNotNull", "makeSimpleTypeDefinitelyNotNullOrNotNull", "original", "originalIfDefinitelyNotNullable", "parametersCount", "possibleIntegerTypes", "projection", "Lorg/jetbrains/kotlin/types/model/CapturedTypeConstructorMarker;", "removeAnnotations", "removeExactAnnotation", "replaceArguments", "replacement", "newArguments", "replaceArgumentsDeeply", "replaceCustomAttributes", "newAttributes", "replaceType", "newType", "safeSubstitute", "singleBestRepresentative", "size", "supertypes", "toApproximationResult", "toErrorType", "typeConstructor", "typeConstructorProjection", "typeDepth", "unwrapStubTypeVariableConstructor", "upperBoundCount", "upperBoundIfFlexible", "withNotNullProjection", "withNullability", "ApproximationResult", "Companion", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractTypeApproximator implements TypeSystemInferenceExtensionContext {
    public static final int CACHE_FOR_INCORPORATION_MAX_SIZE = 500;
    private final ConcurrentHashMap<KotlinTypeMarker, ApproximationResult> cacheForIncorporationConfigToSubtypeDirection;
    private final ConcurrentHashMap<KotlinTypeMarker, ApproximationResult> cacheForIncorporationConfigToSuperDirection;
    private final TypeSystemInferenceExtensionContext ctx;
    private final LanguageVersionSettings languageVersionSettings;

    /* JADX INFO: compiled from: AbstractTypeApproximator.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[TypeApproximatorConfiguration.IntersectionStrategy.values().length];
            try {
                iArr[TypeApproximatorConfiguration.IntersectionStrategy.ALLOWED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[TypeApproximatorConfiguration.IntersectionStrategy.TO_FIRST.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[TypeApproximatorConfiguration.IntersectionStrategy.TO_COMMON_SUPERTYPE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[TypeApproximatorConfiguration.IntersectionStrategy.TO_UPPER_BOUND_IF_SUPERTYPE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[TypeVariance.values().length];
            try {
                iArr2[TypeVariance.OUT.ordinal()] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr2[TypeVariance.IN.ordinal()] = 2;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr2[TypeVariance.INV.ordinal()] = 3;
            } catch (NoSuchFieldError unused7) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
    public boolean anySuperTypeConstructor(KotlinTypeMarker kotlinTypeMarker, Function1<? super SimpleTypeMarker, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        return this.ctx.anySuperTypeConstructor(kotlinTypeMarker, predicate);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemBuiltInsContext
    public SimpleTypeMarker anyType() {
        return this.ctx.anyType();
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean areEqualTypeConstructors(TypeConstructorMarker c1, TypeConstructorMarker c2) {
        Intrinsics.checkNotNullParameter(c1, "c1");
        Intrinsics.checkNotNullParameter(c2, "c2");
        return this.ctx.areEqualTypeConstructors(c1, c2);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public int argumentsCount(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.ctx.argumentsCount(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeArgumentListMarker asArgumentList(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.ctx.asArgumentList(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public CapturedTypeMarker asCapturedType(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.ctx.asCapturedType(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public DefinitelyNotNullTypeMarker asDefinitelyNotNullType(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.ctx.asDefinitelyNotNullType(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public DynamicTypeMarker asDynamicType(FlexibleTypeMarker flexibleTypeMarker) {
        Intrinsics.checkNotNullParameter(flexibleTypeMarker, "<this>");
        return this.ctx.asDynamicType(flexibleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public FlexibleTypeMarker asFlexibleType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.ctx.asFlexibleType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker asSimpleType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.ctx.asSimpleType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeArgumentMarker asTypeArgument(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.ctx.asTypeArgument(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
    public boolean canHaveUndefinedNullability(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.ctx.canHaveUndefinedNullability(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker captureFromArguments(SimpleTypeMarker type, CaptureStatus status) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(status, "status");
        return this.ctx.captureFromArguments(type, status);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker captureFromExpression(KotlinTypeMarker type) {
        Intrinsics.checkNotNullParameter(type, "type");
        return this.ctx.captureFromExpression(type);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public CaptureStatus captureStatus(CapturedTypeMarker capturedTypeMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
        return this.ctx.captureStatus(capturedTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public EmptyIntersectionTypeInfo computeEmptyIntersectionTypeKind(Collection<? extends KotlinTypeMarker> types) {
        Intrinsics.checkNotNullParameter(types, "types");
        return this.ctx.computeEmptyIntersectionTypeKind(types);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public boolean contains(KotlinTypeMarker kotlinTypeMarker, Function1<? super KotlinTypeMarker, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        return this.ctx.contains(kotlinTypeMarker, predicate);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public KotlinTypeMarker convertToNonRaw(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.ctx.convertToNonRaw(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public SimpleTypeMarker createCapturedStarProjectionForSelfType(TypeVariableTypeConstructorMarker typeVariable, List<? extends KotlinTypeMarker> typesForRecursiveTypeParameters) {
        Intrinsics.checkNotNullParameter(typeVariable, "typeVariable");
        Intrinsics.checkNotNullParameter(typesForRecursiveTypeParameters, "typesForRecursiveTypeParameters");
        return this.ctx.createCapturedStarProjectionForSelfType(typeVariable, typesForRecursiveTypeParameters);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public CapturedTypeMarker createCapturedType(TypeArgumentMarker constructorProjection, List<? extends KotlinTypeMarker> constructorSupertypes, KotlinTypeMarker lowerType, CaptureStatus captureStatus) {
        Intrinsics.checkNotNullParameter(constructorProjection, "constructorProjection");
        Intrinsics.checkNotNullParameter(constructorSupertypes, "constructorSupertypes");
        Intrinsics.checkNotNullParameter(captureStatus, "captureStatus");
        return this.ctx.createCapturedType(constructorProjection, constructorSupertypes, lowerType, captureStatus);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeSubstitutorMarker createEmptySubstitutor() {
        return this.ctx.createEmptySubstitutor();
    }

    public SimpleTypeMarker createErrorType(String debugName) {
        Intrinsics.checkNotNullParameter(debugName, "debugName");
        return this.ctx.createErrorType(debugName);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemTypeFactoryContext
    public KotlinTypeMarker createFlexibleType(SimpleTypeMarker lowerBound, SimpleTypeMarker upperBound) {
        Intrinsics.checkNotNullParameter(lowerBound, "lowerBound");
        Intrinsics.checkNotNullParameter(upperBound, "upperBound");
        return this.ctx.createFlexibleType(lowerBound, upperBound);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemTypeFactoryContext
    public SimpleTypeMarker createSimpleType(TypeConstructorMarker constructor, List<? extends TypeArgumentMarker> arguments, boolean nullable, boolean isExtensionFunction, List<? extends AnnotationMarker> attributes) {
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        return this.ctx.createSimpleType(constructor, arguments, nullable, isExtensionFunction, attributes);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemTypeFactoryContext
    public TypeArgumentMarker createStarProjection(TypeParameterMarker typeParameter) {
        Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
        return this.ctx.createStarProjection(typeParameter);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public StubTypeMarker createStubTypeForBuilderInference(TypeVariableMarker typeVariable) {
        Intrinsics.checkNotNullParameter(typeVariable, "typeVariable");
        return this.ctx.createStubTypeForBuilderInference(typeVariable);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public StubTypeMarker createStubTypeForTypeVariablesInSubtyping(TypeVariableMarker typeVariable) {
        Intrinsics.checkNotNullParameter(typeVariable, "typeVariable");
        return this.ctx.createStubTypeForTypeVariablesInSubtyping(typeVariable);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public TypeSubstitutorMarker createSubstitutorForSuperTypes(KotlinTypeMarker baseType) {
        Intrinsics.checkNotNullParameter(baseType, "baseType");
        return this.ctx.createSubstitutorForSuperTypes(baseType);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemTypeFactoryContext
    public TypeArgumentMarker createTypeArgument(KotlinTypeMarker type, TypeVariance variance) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(variance, "variance");
        return this.ctx.createTypeArgument(type, variance);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public KotlinTypeMarker createTypeWithAlternativeForIntersectionResult(KotlinTypeMarker firstCandidate, KotlinTypeMarker secondCandidate) {
        Intrinsics.checkNotNullParameter(firstCandidate, "firstCandidate");
        Intrinsics.checkNotNullParameter(secondCandidate, "secondCandidate");
        return this.ctx.createTypeWithAlternativeForIntersectionResult(firstCandidate, secondCandidate);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemTypeFactoryContext
    public KotlinTypeMarker createUninferredType(TypeConstructorMarker constructor) {
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        return this.ctx.createUninferredType(constructor);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public SimpleTypeMarker defaultType(TypeVariableMarker typeVariableMarker) {
        Intrinsics.checkNotNullParameter(typeVariableMarker, "<this>");
        return this.ctx.defaultType(typeVariableMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public KotlinTypeMarker eraseContainingTypeParameters(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.ctx.eraseContainingTypeParameters(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public List<KotlinTypeMarker> extractArgumentsForFunctionTypeOrSubtype(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.ctx.extractArgumentsForFunctionTypeOrSubtype(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public Set<TypeParameterMarker> extractTypeParameters(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.ctx.extractTypeParameters(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public Set<TypeVariableTypeConstructorMarker> extractTypeVariables(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.ctx.extractTypeVariables(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public List<SimpleTypeMarker> fastCorrespondingSupertypes(SimpleTypeMarker simpleTypeMarker, TypeConstructorMarker constructor) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        return this.ctx.fastCorrespondingSupertypes(simpleTypeMarker, constructor);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
    public SimpleTypeMarker findCommonIntegerLiteralTypesSuperType(List<? extends SimpleTypeMarker> explicitSupertypes) {
        Intrinsics.checkNotNullParameter(explicitSupertypes, "explicitSupertypes");
        return this.ctx.findCommonIntegerLiteralTypesSuperType(explicitSupertypes);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public TypeConstructorMarker freshTypeConstructor(TypeVariableMarker typeVariableMarker) {
        Intrinsics.checkNotNullParameter(typeVariableMarker, "<this>");
        return this.ctx.freshTypeConstructor(typeVariableMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public FunctionTypeKind functionTypeKind(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.ctx.functionTypeKind(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeArgumentMarker get(TypeArgumentListMarker typeArgumentListMarker, int i) {
        Intrinsics.checkNotNullParameter(typeArgumentListMarker, "<this>");
        return this.ctx.get(typeArgumentListMarker, i);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public KotlinTypeMarker getApproximatedIntegerLiteralType(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.ctx.getApproximatedIntegerLiteralType(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeArgumentMarker getArgument(KotlinTypeMarker kotlinTypeMarker, int i) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.ctx.getArgument(kotlinTypeMarker, i);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeArgumentMarker getArgumentOrNull(SimpleTypeMarker simpleTypeMarker, int i) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.ctx.getArgumentOrNull(simpleTypeMarker, i);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public List<TypeArgumentMarker> getArguments(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.ctx.getArguments(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public List<AnnotationMarker> getAttributes(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.ctx.getAttributes(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public List<AnnotationMarker> getCustomAttributes(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.ctx.getCustomAttributes(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public KotlinTypeMarker getFunctionTypeFromSupertypes(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.ctx.getFunctionTypeFromSupertypes(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public TypeConstructorMarker getNonReflectFunctionTypeConstructor(int parametersNumber, FunctionTypeKind kind) {
        Intrinsics.checkNotNullParameter(kind, "kind");
        return this.ctx.getNonReflectFunctionTypeConstructor(parametersNumber, kind);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public TypeVariableTypeConstructorMarker getOriginalTypeVariable(StubTypeMarker stubTypeMarker) {
        Intrinsics.checkNotNullParameter(stubTypeMarker, "<this>");
        return this.ctx.getOriginalTypeVariable(stubTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeParameterMarker getParameter(TypeConstructorMarker typeConstructorMarker, int i) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.ctx.getParameter(typeConstructorMarker, i);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public List<TypeParameterMarker> getParameters(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.ctx.getParameters(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public TypeConstructorMarker getReflectFunctionTypeConstructor(int parametersNumber, FunctionTypeKind kind) {
        Intrinsics.checkNotNullParameter(kind, "kind");
        return this.ctx.getReflectFunctionTypeConstructor(parametersNumber, kind);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker getType(TypeArgumentMarker typeArgumentMarker) {
        Intrinsics.checkNotNullParameter(typeArgumentMarker, "<this>");
        return this.ctx.getType(typeArgumentMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeConstructorMarker getTypeConstructor(TypeParameterMarker typeParameterMarker) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        return this.ctx.getTypeConstructor(typeParameterMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeParameterMarker getTypeParameter(TypeVariableTypeConstructorMarker typeVariableTypeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeVariableTypeConstructorMarker, "<this>");
        return this.ctx.getTypeParameter(typeVariableTypeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeParameterMarker getTypeParameterClassifier(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.ctx.getTypeParameterClassifier(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker getUpperBound(TypeParameterMarker typeParameterMarker, int i) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        return this.ctx.getUpperBound(typeParameterMarker, i);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public List<KotlinTypeMarker> getUpperBounds(TypeParameterMarker typeParameterMarker) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        return this.ctx.getUpperBounds(typeParameterMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeVariance getVariance(TypeArgumentMarker typeArgumentMarker) {
        Intrinsics.checkNotNullParameter(typeArgumentMarker, "<this>");
        return this.ctx.getVariance(typeArgumentMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeVariance getVariance(TypeParameterMarker typeParameterMarker) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        return this.ctx.getVariance(typeParameterMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean hasCustomAttributes(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.ctx.hasCustomAttributes(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public boolean hasExactAnnotation(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.ctx.hasExactAnnotation(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean hasFlexibleNullability(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.ctx.hasFlexibleNullability(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public boolean hasNoInferAnnotation(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.ctx.hasNoInferAnnotation(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean hasRecursiveBounds(TypeParameterMarker typeParameterMarker, TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        return this.ctx.hasRecursiveBounds(typeParameterMarker, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemOptimizationContext
    public boolean identicalArguments(SimpleTypeMarker a, SimpleTypeMarker b) {
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        return this.ctx.identicalArguments(a, b);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker intersectTypes(List<? extends KotlinTypeMarker> types) {
        Intrinsics.checkNotNullParameter(types, "types");
        return this.ctx.intersectTypes(types);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    /* JADX INFO: renamed from: intersectTypes */
    public SimpleTypeMarker mo6107intersectTypes(List<? extends SimpleTypeMarker> types) {
        Intrinsics.checkNotNullParameter(types, "types");
        return this.ctx.intersectTypes((List<? extends KotlinTypeMarker>) types);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isAnonymous(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.ctx.isAnonymous(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isAnyConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.ctx.isAnyConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public boolean isBuiltinFunctionTypeOrSubtype(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.ctx.isBuiltinFunctionTypeOrSubtype(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isCapturedDynamic(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.ctx.isCapturedDynamic(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isCapturedType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.ctx.isCapturedType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public boolean isCapturedTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.ctx.isCapturedTypeConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isClassType(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.ctx.isClassType(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isClassTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.ctx.isClassTypeConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isCommonFinalClassConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.ctx.isCommonFinalClassConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public boolean isContainedInInvariantOrContravariantPositions(TypeVariableTypeConstructorMarker typeVariableTypeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeVariableTypeConstructorMarker, "<this>");
        return this.ctx.isContainedInInvariantOrContravariantPositions(typeVariableTypeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isDefinitelyClassTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.ctx.isDefinitelyClassTypeConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isDefinitelyNotNullType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.ctx.isDefinitelyNotNullType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isDenotable(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.ctx.isDenotable(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isDynamic(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.ctx.isDynamic(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isError(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.ctx.isError(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isError(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.ctx.isError(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
    public boolean isExtensionFunction(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.ctx.isExtensionFunction(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public boolean isExtensionFunctionType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.ctx.isExtensionFunctionType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public boolean isFinalClassConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.ctx.isFinalClassConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isFlexible(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.ctx.isFlexible(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isFlexibleNothing(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.ctx.isFlexibleNothing(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public boolean isFunctionOrKFunctionWithAnySuspendability(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.ctx.isFunctionOrKFunctionWithAnySuspendability(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isIntegerConstantOperatorTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.ctx.isIntegerConstantOperatorTypeConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isIntegerLiteralConstantTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.ctx.isIntegerLiteralConstantTypeConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isIntegerLiteralType(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.ctx.isIntegerLiteralType(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isIntegerLiteralTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.ctx.isIntegerLiteralTypeConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isInterface(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.ctx.isInterface(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isIntersection(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.ctx.isIntersection(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public boolean isK2() {
        return this.ctx.isK2();
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isLocalType(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.ctx.isLocalType(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isMarkedNullable(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.ctx.isMarkedNullable(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isMarkedNullable(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.ctx.isMarkedNullable(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    @ObsoleteTypeKind
    public boolean isNotNullTypeParameter(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.ctx.isNotNullTypeParameter(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isNothing(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.ctx.isNothing(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isNothingConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.ctx.isNothingConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isNullableAny(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.ctx.isNullableAny(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isNullableNothing(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.ctx.isNullableNothing(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isNullableType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.ctx.isNullableType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isOldCapturedType(CapturedTypeMarker capturedTypeMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
        return this.ctx.isOldCapturedType(capturedTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isPrimitiveType(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.ctx.isPrimitiveType(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isProjectionNotNull(CapturedTypeMarker capturedTypeMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
        return this.ctx.isProjectionNotNull(capturedTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isRawType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.ctx.isRawType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public boolean isSignedOrUnsignedNumberType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.ctx.isSignedOrUnsignedNumberType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isSimpleType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.ctx.isSimpleType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isSingleClassifierType(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.ctx.isSingleClassifierType(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public boolean isSpecial(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.ctx.isSpecial(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isStarProjection(TypeArgumentMarker typeArgumentMarker) {
        Intrinsics.checkNotNullParameter(typeArgumentMarker, "<this>");
        return this.ctx.isStarProjection(typeArgumentMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isStubType(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.ctx.isStubType(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isStubTypeForBuilderInference(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.ctx.isStubTypeForBuilderInference(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isStubTypeForVariableInSubtyping(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.ctx.isStubTypeForVariableInSubtyping(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isTypeParameterTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.ctx.isTypeParameterTypeConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public boolean isTypeVariable(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.ctx.isTypeVariable(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isTypeVariableType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.ctx.isTypeVariableType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isUninferredParameter(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.ctx.isUninferredParameter(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public boolean isUnit(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.ctx.isUnit(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public boolean isUnitTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.ctx.isUnitTypeConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public Iterator<TypeArgumentMarker> iterator(TypeArgumentListMarker typeArgumentListMarker) {
        Intrinsics.checkNotNullParameter(typeArgumentListMarker, "<this>");
        return this.ctx.iterator(typeArgumentListMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker lowerBound(FlexibleTypeMarker flexibleTypeMarker) {
        Intrinsics.checkNotNullParameter(flexibleTypeMarker, "<this>");
        return this.ctx.lowerBound(flexibleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker lowerBoundIfFlexible(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.ctx.lowerBoundIfFlexible(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker lowerType(CapturedTypeMarker capturedTypeMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
        return this.ctx.lowerType(capturedTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker makeDefinitelyNotNullOrNotNull(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.ctx.makeDefinitelyNotNullOrNotNull(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker makeSimpleTypeDefinitelyNotNullOrNotNull(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.ctx.makeSimpleTypeDefinitelyNotNullOrNotNull(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeCheckerProviderContext
    public TypeCheckerState newTypeCheckerState(boolean errorTypesEqualToAnything, boolean stubTypesEqualToAnything) {
        return this.ctx.newTypeCheckerState(errorTypesEqualToAnything, stubTypesEqualToAnything);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemBuiltInsContext
    public SimpleTypeMarker nothingType() {
        return this.ctx.nothingType();
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemBuiltInsContext
    public SimpleTypeMarker nullableAnyType() {
        return this.ctx.nullableAnyType();
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemBuiltInsContext
    public SimpleTypeMarker nullableNothingType() {
        return this.ctx.nullableNothingType();
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker original(DefinitelyNotNullTypeMarker definitelyNotNullTypeMarker) {
        Intrinsics.checkNotNullParameter(definitelyNotNullTypeMarker, "<this>");
        return this.ctx.original(definitelyNotNullTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker originalIfDefinitelyNotNullable(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.ctx.originalIfDefinitelyNotNullable(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public int parametersCount(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.ctx.parametersCount(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public Collection<KotlinTypeMarker> possibleIntegerTypes(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.ctx.possibleIntegerTypes(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeArgumentMarker projection(CapturedTypeConstructorMarker capturedTypeConstructorMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeConstructorMarker, "<this>");
        return this.ctx.projection(capturedTypeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public KotlinTypeMarker removeAnnotations(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.ctx.removeAnnotations(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public KotlinTypeMarker removeExactAnnotation(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.ctx.removeExactAnnotation(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public KotlinTypeMarker replaceArguments(KotlinTypeMarker kotlinTypeMarker, Function1<? super TypeArgumentMarker, ? extends TypeArgumentMarker> replacement) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        Intrinsics.checkNotNullParameter(replacement, "replacement");
        return this.ctx.replaceArguments(kotlinTypeMarker, replacement);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public SimpleTypeMarker replaceArguments(SimpleTypeMarker simpleTypeMarker, List<? extends TypeArgumentMarker> newArguments) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        Intrinsics.checkNotNullParameter(newArguments, "newArguments");
        return this.ctx.replaceArguments(simpleTypeMarker, newArguments);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public SimpleTypeMarker replaceArguments(SimpleTypeMarker simpleTypeMarker, Function1<? super TypeArgumentMarker, ? extends TypeArgumentMarker> replacement) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        Intrinsics.checkNotNullParameter(replacement, "replacement");
        return this.ctx.replaceArguments(simpleTypeMarker, replacement);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public KotlinTypeMarker replaceArgumentsDeeply(KotlinTypeMarker kotlinTypeMarker, Function1<? super TypeArgumentMarker, ? extends TypeArgumentMarker> replacement) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        Intrinsics.checkNotNullParameter(replacement, "replacement");
        return this.ctx.replaceArgumentsDeeply(kotlinTypeMarker, replacement);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public SimpleTypeMarker replaceArgumentsDeeply(SimpleTypeMarker simpleTypeMarker, Function1<? super TypeArgumentMarker, ? extends TypeArgumentMarker> replacement) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        Intrinsics.checkNotNullParameter(replacement, "replacement");
        return this.ctx.replaceArgumentsDeeply(simpleTypeMarker, replacement);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
    public KotlinTypeMarker replaceCustomAttributes(KotlinTypeMarker kotlinTypeMarker, List<? extends AnnotationMarker> newAttributes) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        Intrinsics.checkNotNullParameter(newAttributes, "newAttributes");
        return this.ctx.replaceCustomAttributes(kotlinTypeMarker, newAttributes);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeArgumentMarker replaceType(TypeArgumentMarker typeArgumentMarker, KotlinTypeMarker newType) {
        Intrinsics.checkNotNullParameter(typeArgumentMarker, "<this>");
        Intrinsics.checkNotNullParameter(newType, "newType");
        return this.ctx.replaceType(typeArgumentMarker, newType);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker safeSubstitute(TypeSubstitutorMarker typeSubstitutorMarker, KotlinTypeMarker type) {
        Intrinsics.checkNotNullParameter(typeSubstitutorMarker, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        return this.ctx.safeSubstitute(typeSubstitutorMarker, type);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public KotlinTypeMarker singleBestRepresentative(Collection<? extends KotlinTypeMarker> collection) {
        Intrinsics.checkNotNullParameter(collection, "<this>");
        return this.ctx.singleBestRepresentative(collection);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public int size(TypeArgumentListMarker typeArgumentListMarker) {
        Intrinsics.checkNotNullParameter(typeArgumentListMarker, "<this>");
        return this.ctx.size(typeArgumentListMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeCheckerState.SupertypesPolicy substitutionSupertypePolicy(SimpleTypeMarker type) {
        Intrinsics.checkNotNullParameter(type, "type");
        return this.ctx.substitutionSupertypePolicy(type);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public Collection<KotlinTypeMarker> supertypes(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.ctx.supertypes(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
    public SimpleTypeMarker toErrorType(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.ctx.toErrorType(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public CapturedTypeConstructorMarker typeConstructor(CapturedTypeMarker capturedTypeMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
        return this.ctx.typeConstructor(capturedTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeConstructorMarker typeConstructor(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.ctx.typeConstructor(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeConstructorMarker typeConstructor(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.ctx.typeConstructor(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public TypeArgumentMarker typeConstructorProjection(CapturedTypeMarker capturedTypeMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
        return this.ctx.typeConstructorProjection(capturedTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
    public int typeDepth(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.ctx.typeDepth(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
    public int typeDepth(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.ctx.typeDepth(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeSubstitutorMarker typeSubstitutorByTypeConstructor(Map<TypeConstructorMarker, ? extends KotlinTypeMarker> map) {
        Intrinsics.checkNotNullParameter(map, "map");
        return this.ctx.typeSubstitutorByTypeConstructor(map);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
    public List<AnnotationMarker> unionTypeAttributes(List<? extends KotlinTypeMarker> types) {
        Intrinsics.checkNotNullParameter(types, "types");
        return this.ctx.unionTypeAttributes(types);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeConstructorMarker unwrapStubTypeVariableConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.ctx.unwrapStubTypeVariableConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker upperBound(FlexibleTypeMarker flexibleTypeMarker) {
        Intrinsics.checkNotNullParameter(flexibleTypeMarker, "<this>");
        return this.ctx.upperBound(flexibleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public int upperBoundCount(TypeParameterMarker typeParameterMarker) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        return this.ctx.upperBoundCount(typeParameterMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker upperBoundIfFlexible(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.ctx.upperBoundIfFlexible(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public boolean useRefinedBoundsForTypeVariableInFlexiblePosition() {
        return this.ctx.useRefinedBoundsForTypeVariableInFlexiblePosition();
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public KotlinTypeMarker withNotNullProjection(CapturedTypeMarker capturedTypeMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
        return this.ctx.withNotNullProjection(capturedTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker withNullability(KotlinTypeMarker kotlinTypeMarker, boolean z) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.ctx.withNullability(kotlinTypeMarker, z);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker withNullability(SimpleTypeMarker simpleTypeMarker, boolean z) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.ctx.withNullability(simpleTypeMarker, z);
    }

    public AbstractTypeApproximator(TypeSystemInferenceExtensionContext ctx, LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        this.ctx = ctx;
        this.languageVersionSettings = languageVersionSettings;
        this.cacheForIncorporationConfigToSuperDirection = new ConcurrentHashMap<>();
        this.cacheForIncorporationConfigToSubtypeDirection = new ConcurrentHashMap<>();
    }

    public final TypeSystemInferenceExtensionContext getCtx() {
        return this.ctx;
    }

    protected final LanguageVersionSettings getLanguageVersionSettings() {
        return this.languageVersionSettings;
    }

    /* JADX INFO: compiled from: AbstractTypeApproximator.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0002\u0018\u00002\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/types/AbstractTypeApproximator$ApproximationResult;", "", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "(Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;)V", "getType", "()Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class ApproximationResult {
        private final KotlinTypeMarker type;

        public ApproximationResult(KotlinTypeMarker kotlinTypeMarker) {
            this.type = kotlinTypeMarker;
        }

        public final KotlinTypeMarker getType() {
            return this.type;
        }
    }

    private final Function3<SimpleTypeMarker, TypeApproximatorConfiguration, Integer, KotlinTypeMarker> getReferenceApproximateToSuperType() {
        return new AbstractTypeApproximator$referenceApproximateToSuperType$1(this);
    }

    private final Function3<SimpleTypeMarker, TypeApproximatorConfiguration, Integer, KotlinTypeMarker> getReferenceApproximateToSubType() {
        return new AbstractTypeApproximator$referenceApproximateToSubType$1(this);
    }

    public final KotlinTypeMarker approximateToSuperType(KotlinTypeMarker type, TypeApproximatorConfiguration conf) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(conf, "conf");
        return approximateToSuperType(type, conf, -typeDepth(type));
    }

    public final KotlinTypeMarker approximateToSubType(KotlinTypeMarker type, TypeApproximatorConfiguration conf) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(conf, "conf");
        return approximateToSubType(type, conf, -typeDepth(type));
    }

    public final void clearCache() {
        this.cacheForIncorporationConfigToSubtypeDirection.clear();
        this.cacheForIncorporationConfigToSuperDirection.clear();
    }

    private final ApproximationResult checkExceptionalCases(KotlinTypeMarker type, int depth, TypeApproximatorConfiguration conf, boolean toSuper) {
        if (isSpecial(type)) {
            return toApproximationResult(null);
        }
        if (isError(type)) {
            return toApproximationResult(conf.getErrorType() ? null : defaultResult(type, toSuper));
        }
        if (depth > 3) {
            return toApproximationResult(defaultResult(type, toSuper));
        }
        return null;
    }

    private final ApproximationResult toApproximationResult(KotlinTypeMarker kotlinTypeMarker) {
        return new ApproximationResult(kotlinTypeMarker);
    }

    private final KotlinTypeMarker approximateToSuperType(KotlinTypeMarker type, TypeApproximatorConfiguration conf, int depth) {
        ApproximationResult approximationResultPutIfAbsent;
        KotlinTypeMarker kotlinTypeMarkerPrepareType$default;
        Function1<FlexibleTypeMarker, SimpleTypeMarker> function1;
        ApproximationResult approximationResultCheckExceptionalCases = checkExceptionalCases(type, depth, conf, true);
        if (approximationResultCheckExceptionalCases != null) {
            return approximationResultCheckExceptionalCases.getType();
        }
        if (conf instanceof TypeApproximatorConfiguration.IncorporationConfiguration) {
            ConcurrentHashMap<KotlinTypeMarker, ApproximationResult> concurrentHashMap = this.cacheForIncorporationConfigToSuperDirection;
            if (concurrentHashMap.size() <= 500) {
                ConcurrentHashMap<KotlinTypeMarker, ApproximationResult> concurrentHashMap2 = concurrentHashMap;
                ApproximationResult approximationResult = concurrentHashMap2.get(type);
                if (approximationResult == null && (approximationResultPutIfAbsent = concurrentHashMap2.putIfAbsent(type, (approximationResult = toApproximationResult(approximateTo(AbstractTypeChecker.prepareType$default(AbstractTypeChecker.INSTANCE, this.ctx, type, false, 4, null), conf, new Function1<FlexibleTypeMarker, SimpleTypeMarker>() { // from class: org.jetbrains.kotlin.types.AbstractTypeApproximator$approximateToSuperType$2$1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final SimpleTypeMarker invoke(FlexibleTypeMarker approximateTo) {
                        Intrinsics.checkNotNullParameter(approximateTo, "$this$approximateTo");
                        return this.this$0.upperBound(approximateTo);
                    }
                }, getReferenceApproximateToSuperType(), depth))))) != null) {
                    approximationResult = approximationResultPutIfAbsent;
                }
                return approximationResult.getType();
            }
            kotlinTypeMarkerPrepareType$default = AbstractTypeChecker.prepareType$default(AbstractTypeChecker.INSTANCE, this.ctx, type, false, 4, null);
            function1 = new Function1<FlexibleTypeMarker, SimpleTypeMarker>() { // from class: org.jetbrains.kotlin.types.AbstractTypeApproximator$approximateToSuperType$2$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final SimpleTypeMarker invoke(FlexibleTypeMarker approximateTo) {
                    Intrinsics.checkNotNullParameter(approximateTo, "$this$approximateTo");
                    return this.this$0.upperBound(approximateTo);
                }
            };
        } else {
            kotlinTypeMarkerPrepareType$default = AbstractTypeChecker.prepareType$default(AbstractTypeChecker.INSTANCE, this.ctx, type, false, 4, null);
            function1 = new Function1<FlexibleTypeMarker, SimpleTypeMarker>() { // from class: org.jetbrains.kotlin.types.AbstractTypeApproximator$approximateToSuperType$2$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final SimpleTypeMarker invoke(FlexibleTypeMarker approximateTo) {
                    Intrinsics.checkNotNullParameter(approximateTo, "$this$approximateTo");
                    return this.this$0.upperBound(approximateTo);
                }
            };
        }
        return approximateTo(kotlinTypeMarkerPrepareType$default, conf, function1, getReferenceApproximateToSuperType(), depth);
    }

    private final KotlinTypeMarker approximateToSubType(KotlinTypeMarker type, TypeApproximatorConfiguration conf, int depth) {
        ApproximationResult approximationResultPutIfAbsent;
        KotlinTypeMarker kotlinTypeMarkerPrepareType$default;
        Function1<FlexibleTypeMarker, SimpleTypeMarker> function1;
        ApproximationResult approximationResultCheckExceptionalCases = checkExceptionalCases(type, depth, conf, false);
        if (approximationResultCheckExceptionalCases != null) {
            return approximationResultCheckExceptionalCases.getType();
        }
        if (conf instanceof TypeApproximatorConfiguration.IncorporationConfiguration) {
            ConcurrentHashMap<KotlinTypeMarker, ApproximationResult> concurrentHashMap = this.cacheForIncorporationConfigToSubtypeDirection;
            if (concurrentHashMap.size() <= 500) {
                ConcurrentHashMap<KotlinTypeMarker, ApproximationResult> concurrentHashMap2 = concurrentHashMap;
                ApproximationResult approximationResult = concurrentHashMap2.get(type);
                if (approximationResult == null && (approximationResultPutIfAbsent = concurrentHashMap2.putIfAbsent(type, (approximationResult = toApproximationResult(approximateTo(AbstractTypeChecker.prepareType$default(AbstractTypeChecker.INSTANCE, this.ctx, type, false, 4, null), conf, new Function1<FlexibleTypeMarker, SimpleTypeMarker>() { // from class: org.jetbrains.kotlin.types.AbstractTypeApproximator$approximateToSubType$2$1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final SimpleTypeMarker invoke(FlexibleTypeMarker approximateTo) {
                        Intrinsics.checkNotNullParameter(approximateTo, "$this$approximateTo");
                        return this.this$0.lowerBound(approximateTo);
                    }
                }, getReferenceApproximateToSubType(), depth))))) != null) {
                    approximationResult = approximationResultPutIfAbsent;
                }
                return approximationResult.getType();
            }
            kotlinTypeMarkerPrepareType$default = AbstractTypeChecker.prepareType$default(AbstractTypeChecker.INSTANCE, this.ctx, type, false, 4, null);
            function1 = new Function1<FlexibleTypeMarker, SimpleTypeMarker>() { // from class: org.jetbrains.kotlin.types.AbstractTypeApproximator$approximateToSubType$2$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final SimpleTypeMarker invoke(FlexibleTypeMarker approximateTo) {
                    Intrinsics.checkNotNullParameter(approximateTo, "$this$approximateTo");
                    return this.this$0.lowerBound(approximateTo);
                }
            };
        } else {
            kotlinTypeMarkerPrepareType$default = AbstractTypeChecker.prepareType$default(AbstractTypeChecker.INSTANCE, this.ctx, type, false, 4, null);
            function1 = new Function1<FlexibleTypeMarker, SimpleTypeMarker>() { // from class: org.jetbrains.kotlin.types.AbstractTypeApproximator$approximateToSubType$2$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final SimpleTypeMarker invoke(FlexibleTypeMarker approximateTo) {
                    Intrinsics.checkNotNullParameter(approximateTo, "$this$approximateTo");
                    return this.this$0.lowerBound(approximateTo);
                }
            };
        }
        return approximateTo(kotlinTypeMarkerPrepareType$default, conf, function1, getReferenceApproximateToSubType(), depth);
    }

    private final KotlinTypeMarker approximateTo(KotlinTypeMarker type, TypeApproximatorConfiguration conf, Function1<? super FlexibleTypeMarker, ? extends SimpleTypeMarker> bound, Function3<? super SimpleTypeMarker, ? super TypeApproximatorConfiguration, ? super Integer, ? extends KotlinTypeMarker> approximateTo, int depth) {
        KotlinTypeMarker kotlinTypeMarkerInvoke;
        SimpleTypeMarker simpleTypeMarkerUpperBoundIfFlexible;
        SimpleTypeMarker simpleTypeMarkerLowerBoundIfFlexible;
        if (type instanceof SimpleTypeMarker) {
            return approximateTo.invoke(type, conf, Integer.valueOf(depth));
        }
        if (type instanceof FlexibleTypeMarker) {
            if (isDynamic(type)) {
                if (conf.getDynamic()) {
                    return null;
                }
                return bound.invoke(type);
            }
            if (isRawType(type)) {
                if (conf.getRawType()) {
                    return null;
                }
                return bound.invoke(type);
            }
            if (conf.getFlexible()) {
                FlexibleTypeMarker flexibleTypeMarker = (FlexibleTypeMarker) type;
                SimpleTypeMarker simpleTypeMarkerLowerBound = lowerBound(flexibleTypeMarker);
                SimpleTypeMarker simpleTypeMarkerUpperBound = upperBound(flexibleTypeMarker);
                KotlinTypeMarker kotlinTypeMarkerInvoke2 = approximateTo.invoke(simpleTypeMarkerLowerBound, conf, Integer.valueOf(depth));
                if (!isRawType(type) && Intrinsics.areEqual(typeConstructor(simpleTypeMarkerLowerBound), typeConstructor(simpleTypeMarkerUpperBound))) {
                    kotlinTypeMarkerInvoke = kotlinTypeMarkerInvoke2 != null ? withNullability(kotlinTypeMarkerInvoke2, isMarkedNullable(simpleTypeMarkerUpperBound)) : null;
                } else {
                    kotlinTypeMarkerInvoke = approximateTo.invoke(simpleTypeMarkerUpperBound, conf, Integer.valueOf(depth));
                }
                if (kotlinTypeMarkerInvoke2 == null && kotlinTypeMarkerInvoke == null) {
                    return null;
                }
                if (kotlinTypeMarkerInvoke2 != null && (simpleTypeMarkerLowerBoundIfFlexible = lowerBoundIfFlexible(kotlinTypeMarkerInvoke2)) != null) {
                    simpleTypeMarkerLowerBound = simpleTypeMarkerLowerBoundIfFlexible;
                }
                if (kotlinTypeMarkerInvoke != null && (simpleTypeMarkerUpperBoundIfFlexible = upperBoundIfFlexible(kotlinTypeMarkerInvoke)) != null) {
                    simpleTypeMarkerUpperBound = simpleTypeMarkerUpperBoundIfFlexible;
                }
                return createFlexibleType(simpleTypeMarkerLowerBound, simpleTypeMarkerUpperBound);
            }
            SimpleTypeMarker simpleTypeMarkerInvoke = bound.invoke(type);
            KotlinTypeMarker kotlinTypeMarkerInvoke3 = approximateTo.invoke(simpleTypeMarkerInvoke, conf, Integer.valueOf(depth));
            return kotlinTypeMarkerInvoke3 == null ? simpleTypeMarkerInvoke : kotlinTypeMarkerInvoke3;
        }
        throw new IllegalStateException("sealed".toString());
    }

    private final SimpleTypeMarker approximateLocalTypes(SimpleTypeMarker type, TypeApproximatorConfiguration conf, boolean toSuper, int depth) {
        SimpleTypeMarker simpleTypeMarkerWithNullability;
        if (!toSuper) {
            return null;
        }
        if (!conf.getLocalTypes() && !conf.getAnonymous()) {
            return null;
        }
        TypeConstructorMarker typeConstructorMarkerTypeConstructor = typeConstructor(type);
        if ((conf.getLocalTypes() && isLocalType(typeConstructorMarkerTypeConstructor)) || (conf.getAnonymous() && isAnonymous(typeConstructorMarkerTypeConstructor))) {
            SimpleTypeMarker simpleTypeMarker = (SimpleTypeMarker) CollectionsKt.firstOrNull((List) AbstractTypeChecker.INSTANCE.findCorrespondingSupertypes(newTypeCheckerState(false, false), type, typeConstructor((KotlinTypeMarker) CollectionsKt.first(supertypes(typeConstructorMarkerTypeConstructor)))));
            if (simpleTypeMarker != null && (simpleTypeMarkerWithNullability = withNullability(simpleTypeMarker, isMarkedNullable(type))) != null) {
                if (!this.ctx.isK2()) {
                    return simpleTypeMarkerWithNullability;
                }
                SimpleTypeMarker simpleTypeMarkerApproximateTo = approximateTo(simpleTypeMarkerWithNullability, TypeApproximatorConfiguration.SubtypeCapturedTypesApproximation.INSTANCE, toSuper, depth);
                if (simpleTypeMarkerApproximateTo == null) {
                    simpleTypeMarkerApproximateTo = simpleTypeMarkerWithNullability;
                }
                return (SimpleTypeMarker) simpleTypeMarkerApproximateTo;
            }
        }
        return null;
    }

    private final boolean isIntersectionTypeEffectivelyNothing(IntersectionTypeConstructorMarker constructor) {
        Collection<KotlinTypeMarker> collectionSupertypes = supertypes(constructor);
        if ((collectionSupertypes instanceof Collection) && collectionSupertypes.isEmpty()) {
            return false;
        }
        for (KotlinTypeMarker kotlinTypeMarker : collectionSupertypes) {
            if (!isMarkedNullable(kotlinTypeMarker) && isSignedOrUnsignedNumberType(kotlinTypeMarker)) {
                return true;
            }
        }
        return false;
    }

    private final KotlinTypeMarker approximateIntersectionType(SimpleTypeMarker type, TypeApproximatorConfiguration conf, boolean toSuper, int depth) {
        KotlinTypeMarker kotlinTypeMarkerIntersectTypes;
        TypeConstructorMarker typeConstructorMarkerTypeConstructor = typeConstructor(type);
        isIntersection(typeConstructorMarkerTypeConstructor);
        supertypes(typeConstructorMarkerTypeConstructor).isEmpty();
        Collection<KotlinTypeMarker> collectionSupertypes = supertypes(typeConstructorMarkerTypeConstructor);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collectionSupertypes, 10));
        boolean z = false;
        for (KotlinTypeMarker kotlinTypeMarker : collectionSupertypes) {
            KotlinTypeMarker kotlinTypeMarkerApproximateToSuperType = toSuper ? approximateToSuperType(kotlinTypeMarker, conf, depth) : approximateToSubType(kotlinTypeMarker, conf, depth);
            if (kotlinTypeMarkerApproximateToSuperType != null) {
                z = true;
                kotlinTypeMarker = kotlinTypeMarkerApproximateToSuperType;
            }
            arrayList.add(kotlinTypeMarker);
        }
        ArrayList arrayList2 = arrayList;
        int i = WhenMappings.$EnumSwitchMapping$0[conf.getIntersection().ordinal()];
        if (i == 1) {
            if (!z) {
                return null;
            }
            kotlinTypeMarkerIntersectTypes = intersectTypes((List<? extends KotlinTypeMarker>) arrayList2);
        } else if (i == 2) {
            if (!toSuper) {
                return defaultResult(type, false);
            }
            kotlinTypeMarkerIntersectTypes = (KotlinTypeMarker) CollectionsKt.first((List) arrayList2);
        } else {
            if (i != 3 && i != 4) {
                throw new NoWhenBranchMatchedException();
            }
            if (!toSuper) {
                return defaultResult(type, false);
            }
            kotlinTypeMarkerIntersectTypes = NewCommonSuperTypeCalculator.INSTANCE.commonSuperType(this, arrayList2);
            KotlinTypeMarker kotlinTypeMarkerApproximateToSuperType2 = approximateToSuperType(kotlinTypeMarkerIntersectTypes, conf);
            if (kotlinTypeMarkerApproximateToSuperType2 != null) {
                kotlinTypeMarkerIntersectTypes = kotlinTypeMarkerApproximateToSuperType2;
            }
        }
        return isMarkedNullable(type) ? withNullability(kotlinTypeMarkerIntersectTypes, true) : kotlinTypeMarkerIntersectTypes;
    }

    private final KotlinTypeMarker approximateCapturedType(CapturedTypeMarker type, TypeApproximatorConfiguration conf, boolean toSuper, int depth) {
        SimpleTypeMarker simpleTypeMarkerNullableAnyType;
        Collection<KotlinTypeMarker> collectionSupertypes = supertypes(typeConstructor(type));
        int size = collectionSupertypes.size();
        if (size == 0) {
            simpleTypeMarkerNullableAnyType = nullableAnyType();
        } else if (size == 1) {
            simpleTypeMarkerNullableAnyType = (KotlinTypeMarker) CollectionsKt.single(collectionSupertypes);
        } else {
            TypeArgumentMarker typeArgumentMarkerTypeConstructorProjection = typeConstructorProjection(type);
            simpleTypeMarkerNullableAnyType = isStarProjection(typeArgumentMarkerTypeConstructorProjection) ? intersectTypes(CollectionsKt.toList(collectionSupertypes)) : getType(typeArgumentMarkerTypeConstructorProjection);
        }
        SimpleTypeMarker simpleTypeMarkerLowerType = lowerType(type);
        if (simpleTypeMarkerLowerType == null) {
            simpleTypeMarkerLowerType = nothingType();
        }
        if (!conf.capturedType(this.ctx, type) && approximateToSuperType(simpleTypeMarkerNullableAnyType, conf, depth) == null && approximateToSubType(simpleTypeMarkerLowerType, conf, depth) == null) {
            return null;
        }
        if (toSuper) {
            KotlinTypeMarker kotlinTypeMarkerApproximateToSuperType = approximateToSuperType(simpleTypeMarkerNullableAnyType, conf, depth);
            if (kotlinTypeMarkerApproximateToSuperType != null) {
                simpleTypeMarkerNullableAnyType = kotlinTypeMarkerApproximateToSuperType;
            }
        } else {
            simpleTypeMarkerNullableAnyType = approximateToSubType(simpleTypeMarkerLowerType, conf, depth);
            if (simpleTypeMarkerNullableAnyType == null) {
                simpleTypeMarkerNullableAnyType = simpleTypeMarkerLowerType;
            }
        }
        if (isMarkedNullable((SimpleTypeMarker) type)) {
            simpleTypeMarkerNullableAnyType = withNullability(simpleTypeMarkerNullableAnyType, true);
        } else if (isProjectionNotNull(type)) {
            simpleTypeMarkerNullableAnyType = withNullability(simpleTypeMarkerNullableAnyType, false);
        }
        return (isK2() && conf.getConvertToNonRawVersionAfterApproximationInK2() && isRawType(simpleTypeMarkerNullableAnyType)) ? convertToNonRaw(simpleTypeMarkerNullableAnyType) : simpleTypeMarkerNullableAnyType;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final KotlinTypeMarker approximateSimpleToSuperType(SimpleTypeMarker type, TypeApproximatorConfiguration conf, int depth) {
        return approximateTo(type, conf, true, depth);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final KotlinTypeMarker approximateSimpleToSubType(SimpleTypeMarker type, TypeApproximatorConfiguration conf, int depth) {
        return approximateTo(type, conf, false, depth);
    }

    private final KotlinTypeMarker approximateTo(SimpleTypeMarker type, TypeApproximatorConfiguration conf, boolean toSuper, int depth) {
        SimpleTypeMarker simpleTypeMarker = type;
        if (argumentsCount(simpleTypeMarker) != 0) {
            return approximateParametrizedType(type, conf, toSuper, depth + 1);
        }
        DefinitelyNotNullTypeMarker definitelyNotNullTypeMarkerAsDefinitelyNotNullType = asDefinitelyNotNullType(type);
        if (definitelyNotNullTypeMarkerAsDefinitelyNotNullType != null) {
            return approximateDefinitelyNotNullType(definitelyNotNullTypeMarkerAsDefinitelyNotNullType, conf, toSuper, depth);
        }
        TypeConstructorMarker typeConstructorMarkerTypeConstructor = typeConstructor(type);
        if (isCapturedTypeConstructor(typeConstructorMarkerTypeConstructor)) {
            CapturedTypeMarker capturedTypeMarkerAsCapturedType = asCapturedType(type);
            if (capturedTypeMarkerAsCapturedType == null) {
                throw new IllegalArgumentException(("Type is inconsistent -- somewhere we create type with typeConstructor = " + typeConstructorMarkerTypeConstructor + " and class: " + type.getClass().getCanonicalName() + ". type.toString() = " + type).toString());
            }
            return approximateCapturedType(capturedTypeMarkerAsCapturedType, conf, toSuper, depth);
        }
        if (isIntersection(typeConstructorMarkerTypeConstructor)) {
            return approximateIntersectionType(type, conf, toSuper, depth);
        }
        if (typeConstructorMarkerTypeConstructor instanceof TypeVariableTypeConstructorMarker) {
            if (conf.shouldKeepTypeVariableBasedType$resolution_common((TypeVariableTypeConstructorMarker) typeConstructorMarkerTypeConstructor, isK2())) {
                return null;
            }
            return defaultResult(simpleTypeMarker, toSuper);
        }
        if (isIntegerLiteralConstantTypeConstructor(typeConstructorMarkerTypeConstructor)) {
            if (conf.getIntegerLiteralConstantType()) {
                return withNullability(getApproximatedIntegerLiteralType(typeConstructorMarkerTypeConstructor), isMarkedNullable(type));
            }
            return null;
        }
        if (isIntegerConstantOperatorTypeConstructor(typeConstructorMarkerTypeConstructor)) {
            if (conf.getIntegerConstantOperatorType()) {
                return withNullability(getApproximatedIntegerLiteralType(typeConstructorMarkerTypeConstructor), isMarkedNullable(type));
            }
            return null;
        }
        return approximateLocalTypes(type, conf, toSuper, depth);
    }

    private final KotlinTypeMarker approximateDefinitelyNotNullType(DefinitelyNotNullTypeMarker type, TypeApproximatorConfiguration conf, boolean toSuper, int depth) {
        SimpleTypeMarker simpleTypeMarkerOriginal = original(type);
        SimpleTypeMarker simpleTypeMarker = simpleTypeMarkerOriginal;
        SimpleTypeMarker simpleTypeMarkerApproximateToSuperType = toSuper ? approximateToSuperType(simpleTypeMarker, conf, depth) : approximateToSubType(simpleTypeMarker, conf, depth);
        SimpleTypeMarker simpleTypeMarkerWithNullability = withNullability(simpleTypeMarkerOriginal, false);
        if (isTypeParameterTypeConstructor(typeConstructor(simpleTypeMarkerOriginal))) {
            SimpleTypeMarker simpleTypeMarker2 = simpleTypeMarkerWithNullability;
            if (!isNullableType(simpleTypeMarker2)) {
                return simpleTypeMarker2;
            }
        }
        if (conf.getDefinitelyNotNullType() || this.languageVersionSettings.supportsFeature(LanguageFeature.DefinitelyNonNullableTypes)) {
            if (simpleTypeMarkerApproximateToSuperType != null) {
                return makeDefinitelyNotNullOrNotNull(simpleTypeMarkerApproximateToSuperType);
            }
            return null;
        }
        if (!toSuper) {
            return defaultResult(type, toSuper);
        }
        if (simpleTypeMarkerApproximateToSuperType == null) {
            simpleTypeMarkerApproximateToSuperType = simpleTypeMarkerOriginal;
        }
        return withNullability(simpleTypeMarkerApproximateToSuperType, false);
    }

    private final boolean isApproximateDirectionToSuper(TypeVariance effectiveVariance, boolean toSuper) {
        int i = WhenMappings.$EnumSwitchMapping$1[effectiveVariance.ordinal()];
        if (i == 1) {
            return toSuper;
        }
        if (i == 2) {
            return !toSuper;
        }
        if (i != 3) {
            throw new NoWhenBranchMatchedException();
        }
        throw new AssertionError("Incorrect variance " + effectiveVariance);
    }

    /* JADX WARN: Code duplicated, block: B:111:0x0230 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:113:0x0234  */
    /* JADX WARN: Code duplicated, block: B:116:0x023c A[PHI: r11
      0x023c: PHI (r11v8 org.jetbrains.kotlin.types.model.KotlinTypeMarker) = 
      (r11v7 org.jetbrains.kotlin.types.model.KotlinTypeMarker)
      (r11v9 org.jetbrains.kotlin.types.model.KotlinTypeMarker)
      (r11v10 org.jetbrains.kotlin.types.model.KotlinTypeMarker)
      (r11v9 org.jetbrains.kotlin.types.model.KotlinTypeMarker)
     binds: [B:114:0x0238, B:111:0x0230, B:110:0x022e, B:109:0x022c] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:118:0x0244  */
    /* JADX WARN: Code duplicated, block: B:128:0x027e  */
    /* JADX WARN: Code duplicated, block: B:130:0x0286  */
    /* JADX WARN: Code duplicated, block: B:131:0x028e  */
    /* JADX WARN: Code duplicated, block: B:63:0x0135  */
    /* JADX WARN: Code duplicated, block: B:66:0x013c  */
    /* JADX WARN: Code duplicated, block: B:68:0x0142  */
    /* JADX WARN: Code duplicated, block: B:71:0x0149  */
    /* JADX WARN: Code duplicated, block: B:73:0x014f  */
    /* JADX WARN: Code duplicated, block: B:74:0x0158  */
    /* JADX WARN: Code duplicated, block: B:76:0x016e  */
    /* JADX WARN: Code duplicated, block: B:77:0x0176  */
    /* JADX WARN: Code duplicated, block: B:89:0x01b3  */
    /* JADX WARN: Code duplicated, block: B:91:0x01b9  */
    /* JADX WARN: Code duplicated, block: B:93:0x01c5  */
    private final SimpleTypeMarker approximateParametrizedType(SimpleTypeMarker type, TypeApproximatorConfiguration conf, boolean toSuper, int depth) {
        char c;
        KotlinTypeMarker type2;
        KotlinTypeMarker kotlinTypeMarkerApproximateToSubType;
        KotlinTypeMarker kotlinTypeMarkerApproximateToSuperType;
        KotlinTypeMarker kotlinTypeMarkerApproximateToSubType2;
        TypeArgumentMarker typeArgumentMarkerTypeConstructorProjection;
        TypeConstructorMarker typeConstructorMarkerTypeConstructor = typeConstructor(type);
        SimpleTypeMarker simpleTypeMarker = type;
        char c2 = ')';
        if (parametersCount(typeConstructorMarkerTypeConstructor) != argumentsCount(simpleTypeMarker)) {
            if (conf.getErrorType()) {
                return createErrorType("Inconsistent type: " + type + " (parameters.size = " + parametersCount(typeConstructorMarkerTypeConstructor) + ", arguments.size = " + argumentsCount(simpleTypeMarker) + ')');
            }
            return defaultResult(simpleTypeMarker, toSuper);
        }
        int iArgumentsCount = argumentsCount(simpleTypeMarker);
        TypeArgumentMarker[] typeArgumentMarkerArr = new TypeArgumentMarker[iArgumentsCount];
        int i = 0;
        for (int iArgumentsCount2 = argumentsCount(simpleTypeMarker); i < iArgumentsCount2; iArgumentsCount2 = iArgumentsCount2) {
            TypeParameterMarker parameter = getParameter(typeConstructorMarkerTypeConstructor, i);
            TypeArgumentMarker argument = getArgument(simpleTypeMarker, i);
            if (isStarProjection(argument)) {
                c = c2;
            } else {
                TypeVariance typeVarianceEffectiveVariance = AbstractTypeChecker.INSTANCE.effectiveVariance(getVariance(parameter), getVariance(argument));
                TypeArgumentMarker typeArgumentMarker = typeArgumentMarkerArr[i];
                if (typeArgumentMarker == null || (type2 = getType(typeArgumentMarker)) == null) {
                    type2 = getType(argument);
                }
                CapturedTypeMarker capturedTypeMarkerAsCapturedType = asCapturedType(originalIfDefinitelyNotNullable(lowerBoundIfFlexible(type2)));
                TypeArgumentMarker typeArgumentMarker2 = null;
                if (capturedTypeMarkerAsCapturedType != null && (typeArgumentMarkerTypeConstructorProjection = typeConstructorProjection(capturedTypeMarkerAsCapturedType)) != null && isStarProjection(typeArgumentMarkerTypeConstructorProjection)) {
                    typeArgumentMarker2 = typeArgumentMarkerTypeConstructorProjection;
                }
                if (typeArgumentMarker2 != null && ((typeVarianceEffectiveVariance == TypeVariance.OUT || typeVarianceEffectiveVariance == TypeVariance.INV) && toSuper && Intrinsics.areEqual(typeParameter(capturedTypeMarkerAsCapturedType), parameter))) {
                    typeArgumentMarkerArr[i] = typeArgumentMarker2;
                } else {
                    int i2 = typeVarianceEffectiveVariance == null ? -1 : WhenMappings.$EnumSwitchMapping$1[typeVarianceEffectiveVariance.ordinal()];
                    if (i2 == -1) {
                        if (conf.getErrorType()) {
                            return createErrorType("Inconsistent type: " + type + " (" + i + " parameter has declared variance: " + getVariance(parameter) + ", but argument variance is " + getVariance(argument) + ')');
                        }
                        return defaultResult(simpleTypeMarker, toSuper);
                    }
                    if (i2 == 1 || i2 == 2) {
                        if (conf.getIntersectionTypesInContravariantPositions() && typeVarianceEffectiveVariance == TypeVariance.IN && isIntersection(typeConstructor(type2))) {
                            TypeConstructorMarker typeConstructorMarkerTypeConstructor2 = typeConstructor(type2);
                            if (isIntersection(typeConstructorMarkerTypeConstructor2)) {
                                Intrinsics.checkNotNull(typeConstructorMarkerTypeConstructor2, "null cannot be cast to non-null type org.jetbrains.kotlin.types.model.IntersectionTypeConstructorMarker");
                                if (isIntersectionTypeEffectivelyNothing((IntersectionTypeConstructorMarker) typeConstructorMarkerTypeConstructor2)) {
                                    typeArgumentMarkerArr[i] = createStarProjection(parameter);
                                } else if (isApproximateDirectionToSuper(typeVarianceEffectiveVariance, toSuper)) {
                                    kotlinTypeMarkerApproximateToSubType = approximateToSuperType(type2, conf, depth);
                                    if (conf.getIntersection() != TypeApproximatorConfiguration.IntersectionStrategy.TO_UPPER_BOUND_IF_SUPERTYPE) {
                                        if (kotlinTypeMarkerApproximateToSubType != null) {
                                            if (conf.getIntersection() == TypeApproximatorConfiguration.IntersectionStrategy.ALLOWED) {
                                                if (getVariance(parameter) == TypeVariance.INV) {
                                                    typeArgumentMarkerArr[i] = createTypeArgument(kotlinTypeMarkerApproximateToSubType, typeVarianceEffectiveVariance);
                                                } else {
                                                    typeArgumentMarkerArr[i] = asTypeArgument(kotlinTypeMarkerApproximateToSubType);
                                                }
                                            } else if (getVariance(parameter) == TypeVariance.INV) {
                                                typeArgumentMarkerArr[i] = createTypeArgument(kotlinTypeMarkerApproximateToSubType, typeVarianceEffectiveVariance);
                                            } else {
                                                typeArgumentMarkerArr[i] = asTypeArgument(kotlinTypeMarkerApproximateToSubType);
                                            }
                                        }
                                    } else if (kotlinTypeMarkerApproximateToSubType != null) {
                                        if (conf.getIntersection() == TypeApproximatorConfiguration.IntersectionStrategy.ALLOWED) {
                                            if (getVariance(parameter) == TypeVariance.INV) {
                                                typeArgumentMarkerArr[i] = createTypeArgument(kotlinTypeMarkerApproximateToSubType, typeVarianceEffectiveVariance);
                                            } else {
                                                typeArgumentMarkerArr[i] = asTypeArgument(kotlinTypeMarkerApproximateToSubType);
                                            }
                                        } else if (getVariance(parameter) == TypeVariance.INV) {
                                            typeArgumentMarkerArr[i] = createTypeArgument(kotlinTypeMarkerApproximateToSubType, typeVarianceEffectiveVariance);
                                        } else {
                                            typeArgumentMarkerArr[i] = asTypeArgument(kotlinTypeMarkerApproximateToSubType);
                                        }
                                    }
                                } else {
                                    kotlinTypeMarkerApproximateToSubType = approximateToSubType(type2, conf, depth);
                                    if (kotlinTypeMarkerApproximateToSubType != null) {
                                        if (conf.getIntersection() == TypeApproximatorConfiguration.IntersectionStrategy.ALLOWED) {
                                            if (getVariance(parameter) == TypeVariance.INV) {
                                                typeArgumentMarkerArr[i] = createTypeArgument(kotlinTypeMarkerApproximateToSubType, typeVarianceEffectiveVariance);
                                            } else {
                                                typeArgumentMarkerArr[i] = asTypeArgument(kotlinTypeMarkerApproximateToSubType);
                                            }
                                        } else if (getVariance(parameter) == TypeVariance.INV) {
                                            typeArgumentMarkerArr[i] = createTypeArgument(kotlinTypeMarkerApproximateToSubType, typeVarianceEffectiveVariance);
                                        } else {
                                            typeArgumentMarkerArr[i] = asTypeArgument(kotlinTypeMarkerApproximateToSubType);
                                        }
                                    }
                                }
                            } else if (isApproximateDirectionToSuper(typeVarianceEffectiveVariance, toSuper)) {
                                kotlinTypeMarkerApproximateToSubType = approximateToSuperType(type2, conf, depth);
                                if (conf.getIntersection() != TypeApproximatorConfiguration.IntersectionStrategy.TO_UPPER_BOUND_IF_SUPERTYPE) {
                                    if (kotlinTypeMarkerApproximateToSubType != null) {
                                        if (conf.getIntersection() == TypeApproximatorConfiguration.IntersectionStrategy.ALLOWED) {
                                            if (getVariance(parameter) == TypeVariance.INV) {
                                                typeArgumentMarkerArr[i] = createTypeArgument(kotlinTypeMarkerApproximateToSubType, typeVarianceEffectiveVariance);
                                            } else {
                                                typeArgumentMarkerArr[i] = asTypeArgument(kotlinTypeMarkerApproximateToSubType);
                                            }
                                        } else if (getVariance(parameter) == TypeVariance.INV) {
                                            typeArgumentMarkerArr[i] = createTypeArgument(kotlinTypeMarkerApproximateToSubType, typeVarianceEffectiveVariance);
                                        } else {
                                            typeArgumentMarkerArr[i] = asTypeArgument(kotlinTypeMarkerApproximateToSubType);
                                        }
                                    }
                                } else if (kotlinTypeMarkerApproximateToSubType != null) {
                                    if (conf.getIntersection() == TypeApproximatorConfiguration.IntersectionStrategy.ALLOWED) {
                                        if (getVariance(parameter) == TypeVariance.INV) {
                                            typeArgumentMarkerArr[i] = createTypeArgument(kotlinTypeMarkerApproximateToSubType, typeVarianceEffectiveVariance);
                                        } else {
                                            typeArgumentMarkerArr[i] = asTypeArgument(kotlinTypeMarkerApproximateToSubType);
                                        }
                                    } else if (getVariance(parameter) == TypeVariance.INV) {
                                        typeArgumentMarkerArr[i] = createTypeArgument(kotlinTypeMarkerApproximateToSubType, typeVarianceEffectiveVariance);
                                    } else {
                                        typeArgumentMarkerArr[i] = asTypeArgument(kotlinTypeMarkerApproximateToSubType);
                                    }
                                }
                            } else {
                                kotlinTypeMarkerApproximateToSubType = approximateToSubType(type2, conf, depth);
                                if (kotlinTypeMarkerApproximateToSubType != null) {
                                    if (conf.getIntersection() == TypeApproximatorConfiguration.IntersectionStrategy.ALLOWED) {
                                        if (getVariance(parameter) == TypeVariance.INV) {
                                            typeArgumentMarkerArr[i] = createTypeArgument(kotlinTypeMarkerApproximateToSubType, typeVarianceEffectiveVariance);
                                        } else {
                                            typeArgumentMarkerArr[i] = asTypeArgument(kotlinTypeMarkerApproximateToSubType);
                                        }
                                    } else if (getVariance(parameter) == TypeVariance.INV) {
                                        typeArgumentMarkerArr[i] = createTypeArgument(kotlinTypeMarkerApproximateToSubType, typeVarianceEffectiveVariance);
                                    } else {
                                        typeArgumentMarkerArr[i] = asTypeArgument(kotlinTypeMarkerApproximateToSubType);
                                    }
                                }
                            }
                        } else if (isApproximateDirectionToSuper(typeVarianceEffectiveVariance, toSuper)) {
                            kotlinTypeMarkerApproximateToSubType = approximateToSuperType(type2, conf, depth);
                            if (conf.getIntersection() != TypeApproximatorConfiguration.IntersectionStrategy.TO_UPPER_BOUND_IF_SUPERTYPE && isIntersection(typeConstructor(type2))) {
                                List<KotlinTypeMarker> upperBounds = getUpperBounds(parameter);
                                if (!(upperBounds instanceof Collection) || !upperBounds.isEmpty()) {
                                    Iterator<T> it = upperBounds.iterator();
                                    while (true) {
                                        if (it.hasNext()) {
                                            if (!AbstractTypeChecker.isSubtypeOf$default(AbstractTypeChecker.INSTANCE, (TypeCheckerProviderContext) this.ctx, type2, (KotlinTypeMarker) it.next(), false, 8, (Object) null)) {
                                                if (kotlinTypeMarkerApproximateToSubType != null) {
                                                    if (conf.getIntersection() == TypeApproximatorConfiguration.IntersectionStrategy.ALLOWED) {
                                                        if (getVariance(parameter) == TypeVariance.INV) {
                                                            typeArgumentMarkerArr[i] = createTypeArgument(kotlinTypeMarkerApproximateToSubType, typeVarianceEffectiveVariance);
                                                        } else {
                                                            typeArgumentMarkerArr[i] = asTypeArgument(kotlinTypeMarkerApproximateToSubType);
                                                        }
                                                    } else if (getVariance(parameter) == TypeVariance.INV) {
                                                        typeArgumentMarkerArr[i] = createTypeArgument(kotlinTypeMarkerApproximateToSubType, typeVarianceEffectiveVariance);
                                                    } else {
                                                        typeArgumentMarkerArr[i] = asTypeArgument(kotlinTypeMarkerApproximateToSubType);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                KotlinTypeMarker kotlinTypeMarkerIntersectTypes = intersectTypes((List<? extends KotlinTypeMarker>) getUpperBounds(parameter));
                                if (kotlinTypeMarkerApproximateToSubType == null || !AbstractTypeChecker.isSubtypeOf$default(AbstractTypeChecker.INSTANCE, (TypeCheckerProviderContext) this.ctx, kotlinTypeMarkerApproximateToSubType, kotlinTypeMarkerIntersectTypes, false, 8, (Object) null)) {
                                    kotlinTypeMarkerApproximateToSubType = kotlinTypeMarkerIntersectTypes;
                                }
                                if (conf.getIntersection() == TypeApproximatorConfiguration.IntersectionStrategy.ALLOWED) {
                                    if (getVariance(parameter) == TypeVariance.INV) {
                                        typeArgumentMarkerArr[i] = createTypeArgument(kotlinTypeMarkerApproximateToSubType, typeVarianceEffectiveVariance);
                                    } else {
                                        typeArgumentMarkerArr[i] = asTypeArgument(kotlinTypeMarkerApproximateToSubType);
                                    }
                                } else if (getVariance(parameter) == TypeVariance.INV) {
                                    typeArgumentMarkerArr[i] = createTypeArgument(kotlinTypeMarkerApproximateToSubType, typeVarianceEffectiveVariance);
                                } else {
                                    typeArgumentMarkerArr[i] = asTypeArgument(kotlinTypeMarkerApproximateToSubType);
                                }
                            } else if (kotlinTypeMarkerApproximateToSubType != null) {
                                if (conf.getIntersection() == TypeApproximatorConfiguration.IntersectionStrategy.ALLOWED && typeVarianceEffectiveVariance == TypeVariance.OUT && isIntersection(typeConstructor(type2))) {
                                    int iUpperBoundCount = upperBoundCount(parameter);
                                    int i3 = 0;
                                    while (true) {
                                        if (i3 < iUpperBoundCount) {
                                            if (AbstractTypeChecker.isSubtypeOf$default(AbstractTypeChecker.INSTANCE, (TypeCheckerProviderContext) this.ctx, kotlinTypeMarkerApproximateToSubType, getUpperBound(parameter, i3), false, 8, (Object) null)) {
                                                i3++;
                                            } else {
                                                typeArgumentMarkerArr[i] = createStarProjection(parameter);
                                            }
                                        } else if (getVariance(parameter) == TypeVariance.INV) {
                                            typeArgumentMarkerArr[i] = createTypeArgument(kotlinTypeMarkerApproximateToSubType, typeVarianceEffectiveVariance);
                                        } else {
                                            typeArgumentMarkerArr[i] = asTypeArgument(kotlinTypeMarkerApproximateToSubType);
                                        }
                                    }
                                } else if (getVariance(parameter) == TypeVariance.INV) {
                                    typeArgumentMarkerArr[i] = createTypeArgument(kotlinTypeMarkerApproximateToSubType, typeVarianceEffectiveVariance);
                                } else {
                                    typeArgumentMarkerArr[i] = asTypeArgument(kotlinTypeMarkerApproximateToSubType);
                                }
                            }
                        } else {
                            kotlinTypeMarkerApproximateToSubType = approximateToSubType(type2, conf, depth);
                            if (kotlinTypeMarkerApproximateToSubType != null) {
                                if (conf.getIntersection() == TypeApproximatorConfiguration.IntersectionStrategy.ALLOWED) {
                                    if (getVariance(parameter) == TypeVariance.INV) {
                                        typeArgumentMarkerArr[i] = createTypeArgument(kotlinTypeMarkerApproximateToSubType, typeVarianceEffectiveVariance);
                                    } else {
                                        typeArgumentMarkerArr[i] = asTypeArgument(kotlinTypeMarkerApproximateToSubType);
                                    }
                                } else if (getVariance(parameter) == TypeVariance.INV) {
                                    typeArgumentMarkerArr[i] = createTypeArgument(kotlinTypeMarkerApproximateToSubType, typeVarianceEffectiveVariance);
                                } else {
                                    typeArgumentMarkerArr[i] = asTypeArgument(kotlinTypeMarkerApproximateToSubType);
                                }
                            }
                        }
                    } else if (i2 == 3) {
                        if (!toSuper) {
                            KotlinTypeMarker kotlinTypeMarkerApproximateToSubType3 = approximateToSubType(type2, conf, depth);
                            if (kotlinTypeMarkerApproximateToSubType3 != null) {
                                if (!AbstractTypeChecker.equalTypes$default(AbstractTypeChecker.INSTANCE, this, type2, kotlinTypeMarkerApproximateToSubType3, false, 8, null)) {
                                    return defaultResult(simpleTypeMarker, toSuper);
                                }
                                typeArgumentMarkerArr[i] = asTypeArgument(kotlinTypeMarkerApproximateToSubType3);
                            }
                        } else if (isCapturedTypeConstructor(typeConstructor(type2))) {
                            KotlinTypeMarker kotlinTypeMarkerApproximateToSubType4 = approximateToSubType(type2, conf, depth);
                            if (kotlinTypeMarkerApproximateToSubType4 != null) {
                                if (shouldUseSubTypeForCapturedArgument(kotlinTypeMarkerApproximateToSubType4, type2, conf, depth)) {
                                    typeArgumentMarkerArr[i] = createTypeArgument(kotlinTypeMarkerApproximateToSubType4, TypeVariance.IN);
                                } else {
                                    kotlinTypeMarkerApproximateToSuperType = approximateToSuperType(type2, conf, depth);
                                    if (kotlinTypeMarkerApproximateToSuperType != null) {
                                        if (!isTrivialSuper(kotlinTypeMarkerApproximateToSuperType)) {
                                            kotlinTypeMarkerApproximateToSubType2 = approximateToSubType(type2, conf, depth);
                                            if (kotlinTypeMarkerApproximateToSubType2 != null) {
                                                if (!isTrivialSub(kotlinTypeMarkerApproximateToSubType2)) {
                                                    typeArgumentMarkerArr[i] = createTypeArgument(kotlinTypeMarkerApproximateToSubType2, TypeVariance.IN);
                                                } else if (AbstractTypeChecker.equalTypes$default(AbstractTypeChecker.INSTANCE, this, type2, kotlinTypeMarkerApproximateToSuperType, false, 8, null)) {
                                                    typeArgumentMarkerArr[i] = asTypeArgument(kotlinTypeMarkerApproximateToSuperType);
                                                } else {
                                                    typeArgumentMarkerArr[i] = createTypeArgument(kotlinTypeMarkerApproximateToSuperType, TypeVariance.OUT);
                                                }
                                            }
                                        } else if (AbstractTypeChecker.equalTypes$default(AbstractTypeChecker.INSTANCE, this, type2, kotlinTypeMarkerApproximateToSuperType, false, 8, null)) {
                                            typeArgumentMarkerArr[i] = asTypeArgument(kotlinTypeMarkerApproximateToSuperType);
                                        } else {
                                            typeArgumentMarkerArr[i] = createTypeArgument(kotlinTypeMarkerApproximateToSuperType, TypeVariance.OUT);
                                        }
                                    }
                                }
                            }
                        } else {
                            kotlinTypeMarkerApproximateToSuperType = approximateToSuperType(type2, conf, depth);
                            if (kotlinTypeMarkerApproximateToSuperType != null) {
                                if (!isTrivialSuper(kotlinTypeMarkerApproximateToSuperType)) {
                                    kotlinTypeMarkerApproximateToSubType2 = approximateToSubType(type2, conf, depth);
                                    if (kotlinTypeMarkerApproximateToSubType2 != null) {
                                        if (!isTrivialSub(kotlinTypeMarkerApproximateToSubType2)) {
                                            typeArgumentMarkerArr[i] = createTypeArgument(kotlinTypeMarkerApproximateToSubType2, TypeVariance.IN);
                                        } else if (AbstractTypeChecker.equalTypes$default(AbstractTypeChecker.INSTANCE, this, type2, kotlinTypeMarkerApproximateToSuperType, false, 8, null)) {
                                            typeArgumentMarkerArr[i] = asTypeArgument(kotlinTypeMarkerApproximateToSuperType);
                                        } else {
                                            typeArgumentMarkerArr[i] = createTypeArgument(kotlinTypeMarkerApproximateToSuperType, TypeVariance.OUT);
                                        }
                                    }
                                } else if (AbstractTypeChecker.equalTypes$default(AbstractTypeChecker.INSTANCE, this, type2, kotlinTypeMarkerApproximateToSuperType, false, 8, null)) {
                                    typeArgumentMarkerArr[i] = asTypeArgument(kotlinTypeMarkerApproximateToSuperType);
                                } else {
                                    typeArgumentMarkerArr[i] = createTypeArgument(kotlinTypeMarkerApproximateToSuperType, TypeVariance.OUT);
                                }
                            }
                        }
                    }
                }
                c = ')';
            }
            i++;
            c2 = c;
            typeConstructorMarkerTypeConstructor = typeConstructorMarkerTypeConstructor;
        }
        for (int i4 = 0; i4 < iArgumentsCount; i4++) {
            if (typeArgumentMarkerArr[i4] != null) {
                int iArgumentsCount3 = argumentsCount(simpleTypeMarker);
                ArrayList arrayList = new ArrayList(iArgumentsCount3);
                for (int i5 = 0; i5 < iArgumentsCount3; i5++) {
                    TypeArgumentMarker argument2 = typeArgumentMarkerArr[i5];
                    if (argument2 == null) {
                        argument2 = getArgument(simpleTypeMarker, i5);
                    }
                    arrayList.add(argument2);
                }
                SimpleTypeMarker simpleTypeMarkerReplaceArguments = replaceArguments(type, arrayList);
                SimpleTypeMarker simpleTypeMarkerApproximateLocalTypes = approximateLocalTypes(simpleTypeMarkerReplaceArguments, conf, toSuper, depth);
                return simpleTypeMarkerApproximateLocalTypes == null ? simpleTypeMarkerReplaceArguments : simpleTypeMarkerApproximateLocalTypes;
            }
        }
        return approximateLocalTypes(type, conf, toSuper, depth);
    }

    private final boolean shouldUseSubTypeForCapturedArgument(KotlinTypeMarker subType, KotlinTypeMarker capturedArgumentType, TypeApproximatorConfiguration conf, int depth) {
        if (isTrivialSub(subType)) {
            return false;
        }
        if (!isK2() || !isNullableNothing(lowerBoundIfFlexible(subType)) || !isMarkedNullable(capturedArgumentType)) {
            return true;
        }
        KotlinTypeMarker kotlinTypeMarkerApproximateToSubType = approximateToSubType(withNullability(capturedArgumentType, false), conf, depth);
        if (kotlinTypeMarkerApproximateToSubType == null) {
            throw new IllegalStateException("Not-marked-nullable version of captured type approximation should also return not-null".toString());
        }
        return !isTrivialSub(kotlinTypeMarkerApproximateToSubType);
    }

    private final SimpleTypeMarker defaultResult(KotlinTypeMarker kotlinTypeMarker, boolean z) {
        if (z) {
            return nullableAnyType();
        }
        return ((kotlinTypeMarker instanceof SimpleTypeMarker) && isMarkedNullable((SimpleTypeMarker) kotlinTypeMarker)) ? nullableNothingType() : nothingType();
    }

    private final boolean isTrivialSuper(KotlinTypeMarker kotlinTypeMarker) {
        return isNullableAny(upperBoundIfFlexible(kotlinTypeMarker));
    }

    private final boolean isTrivialSub(KotlinTypeMarker kotlinTypeMarker) {
        return isNothing(lowerBoundIfFlexible(kotlinTypeMarker));
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public TypeParameterMarker typeParameter(CapturedTypeMarker capturedTypeMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
        return this.ctx.typeParameter(capturedTypeMarker);
    }
}
