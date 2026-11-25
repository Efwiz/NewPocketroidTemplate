package org.jetbrains.kotlin.fir.backend.jvm;

import androidx.core.app.NotificationCompat;
import com.reandroid.arsc.container.SpecTypePair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.PrimitiveType;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.ScopeUtilsKt;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeUnresolvedSymbolError;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeUnresolvedTypeQualifierError;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProviderKt;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.ConeClassifierLookupTag;
import org.jetbrains.kotlin.fir.symbols.ConeTypeParameterLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeErrorLookupTag;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeErrorType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeRawType;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeContext;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FunctionalTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.InferenceUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.fir.types.impl.ConeTypeParameterTypeImpl;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNameUnsafe;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.StandardClassIds;
import org.jetbrains.kotlin.types.TypeCheckerState;
import org.jetbrains.kotlin.types.TypeSystemCommonBackendContext;
import org.jetbrains.kotlin.types.TypeSystemCommonBackendContextForTypeMapping;
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
import org.jetbrains.kotlin.types.model.TypeArgumentListMarker;
import org.jetbrains.kotlin.types.model.TypeArgumentMarker;
import org.jetbrains.kotlin.types.model.TypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeParameterMarker;
import org.jetbrains.kotlin.types.model.TypeSubstitutorMarker;
import org.jetbrains.kotlin.types.model.TypeVariableTypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeVariance;

/* JADX INFO: compiled from: FirJvmTypeMapper.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0098\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b;\n\u0002\u0010(\n\u0002\b\n\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\r\u0018\u00002\u00020\u00012\u00020\u0002B#\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0014\u0010\u0005\u001a\u0010\u0012\u0004\u0012\u00020\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006¢\u0006\u0002\u0010\bJ\u0019\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0019H\u0096\u0001J\u0011\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eH\u0096\u0001J\u001b\u0010\u001f\u001a\u0004\u0018\u00010\u001c2\u0006\u0010 \u001a\u00020\u001c2\u0006\u0010!\u001a\u00020\"H\u0096\u0001J\u0013\u0010#\u001a\u0004\u0018\u00010\u001e2\u0006\u0010 \u001a\u00020\u001eH\u0096\u0001J\b\u0010$\u001a\u00020%H\u0016J\t\u0010&\u001a\u00020'H\u0096\u0001J\u0010\u0010(\u001a\u00020\u00192\u0006\u0010)\u001a\u00020*H\u0016J\u0019\u0010+\u001a\u00020\u00172\u0006\u0010,\u001a\u00020\u001c2\u0006\u0010-\u001a\u00020\u001cH\u0096\u0001J\u0017\u0010.\u001a\u00020\u001e2\f\u0010/\u001a\b\u0012\u0004\u0012\u00020\u001e00H\u0096\u0001J\u0017\u0010.\u001a\u00020\u001c2\f\u0010/\u001a\b\u0012\u0004\u0012\u00020\u001c00H\u0096\u0001J\t\u00101\u001a\u00020\u001cH\u0096\u0001J\u0010\u00102\u001a\u00020%2\u0006\u00103\u001a\u000204H\u0002J\u0011\u00105\u001a\u0002062\u0006\u0010 \u001a\u00020\u001cH\u0096\u0001J\u001d\u00107\u001a\u00020'2\u0012\u00108\u001a\u000e\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\u001e09H\u0096\u0001J\r\u0010:\u001a\u00020**\u00020\u001eH\u0096\u0001J\r\u0010;\u001a\u00020<*\u00020\u001cH\u0096\u0001J\u000f\u0010=\u001a\u0004\u0018\u00010>*\u00020\u001cH\u0096\u0001J\u000f\u0010?\u001a\u0004\u0018\u00010@*\u00020\u001cH\u0096\u0001J\u000f\u0010A\u001a\u0004\u0018\u00010B*\u00020CH\u0096\u0001J\u000f\u0010D\u001a\u0004\u0018\u00010C*\u00020\u001eH\u0096\u0001J\u000f\u0010E\u001a\u0004\u0018\u00010\u001c*\u00020\u001eH\u0096\u0001J\r\u0010F\u001a\u00020G*\u00020\u001eH\u0096\u0001J\f\u0010H\u001a\u00020\u0012*\u00020\u0019H\u0016J\r\u0010I\u001a\u00020\"*\u00020>H\u0096\u0001J\f\u0010J\u001a\u00020K*\u00020\u0019H\u0016J\u001d\u0010L\u001a\n\u0012\u0004\u0012\u00020\u001c\u0018\u000100*\u00020\u001c2\u0006\u0010M\u001a\u00020\u0019H\u0096\u0001J\u0015\u0010N\u001a\u00020G*\u00020<2\u0006\u0010O\u001a\u00020*H\u0096\u0003J\u0017\u0010P\u001a\u0004\u0018\u00010Q*\u00020\u001e2\u0006\u0010R\u001a\u00020SH\u0096\u0001J\u0015\u0010T\u001a\u00020G*\u00020\u001e2\u0006\u0010O\u001a\u00020*H\u0096\u0001J\u0017\u0010U\u001a\u0004\u0018\u00010G*\u00020\u001c2\u0006\u0010O\u001a\u00020*H\u0096\u0001J\u0013\u0010V\u001a\b\u0012\u0004\u0012\u00020G00*\u00020\u001eH\u0096\u0001J\u0013\u0010W\u001a\b\u0012\u0004\u0012\u00020X00*\u00020\u001eH\u0096\u0001J\u000f\u0010Y\u001a\u0004\u0018\u00010Z*\u00020\u0019H\u0096\u0001J\u0013\u0010[\u001a\b\u0012\u0004\u0012\u00020X00*\u00020\u001eH\u0096\u0001J\r\u0010\\\u001a\u00020]*\u00020\u0012H\u0096\u0001J\u000e\u0010^\u001a\u0004\u0018\u00010\u0007*\u00020\u001eH\u0016J\u0015\u0010_\u001a\u00020\u0012*\u00020\u00192\u0006\u0010O\u001a\u00020*H\u0096\u0001J\u0013\u0010`\u001a\b\u0012\u0004\u0012\u00020\u001200*\u00020\u0019H\u0096\u0001J\u000f\u0010a\u001a\u0004\u0018\u00010b*\u00020\u0019H\u0096\u0001J\u000f\u0010c\u001a\u0004\u0018\u00010b*\u00020\u0019H\u0096\u0001J\r\u0010d\u001a\u00020\u001e*\u00020\u0012H\u0096\u0001J\u000f\u0010e\u001a\u0004\u0018\u00010\u001e*\u00020\u001eH\u0096\u0001J\r\u0010f\u001a\u00020\u001e*\u00020GH\u0096\u0001J\r\u0010g\u001a\u00020\u0019*\u00020\u0012H\u0096\u0001J\u000f\u0010h\u001a\u0004\u0018\u00010\u0012*\u00020\u0019H\u0096\u0001J\u000f\u0010i\u001a\u0004\u0018\u00010\u001e*\u00020\u001eH\u0096\u0001J\u0015\u0010j\u001a\u00020\u001e*\u00020\u00122\u0006\u0010O\u001a\u00020*H\u0096\u0001J\u0013\u0010k\u001a\b\u0012\u0004\u0012\u00020\u001e00*\u00020\u0012H\u0096\u0001J!\u0010l\u001a\u0016\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020]\u0012\u0004\u0012\u00020\u001c0m\u0018\u000100*\u00020\u0019H\u0096\u0001J\r\u0010n\u001a\u00020o*\u00020GH\u0096\u0001J\r\u0010n\u001a\u00020o*\u00020\u0012H\u0096\u0001J\u0015\u0010p\u001a\u00020\u0017*\u00020\u001e2\u0006\u0010R\u001a\u00020SH\u0096\u0001J\r\u0010q\u001a\u00020\u0017*\u00020\u001eH\u0096\u0001J\r\u0010r\u001a\u00020\u0017*\u00020\u001eH\u0096\u0001J\u0017\u0010s\u001a\u00020\u0017*\u00020\u00122\b\u0010t\u001a\u0004\u0018\u00010\u0019H\u0096\u0001J\r\u0010u\u001a\u00020\u0017*\u00020\u0019H\u0096\u0001J\r\u0010v\u001a\u00020\u0017*\u00020\u0019H\u0096\u0001J\r\u0010w\u001a\u00020\u0017*\u00020\u001eH\u0096\u0001J\r\u0010x\u001a\u00020\u0017*\u00020\u001eH\u0096\u0001J\r\u0010y\u001a\u00020\u0017*\u00020\u001eH\u0096\u0001J\r\u0010z\u001a\u00020\u0017*\u00020\u001cH\u0096\u0001J\r\u0010{\u001a\u00020\u0017*\u00020\u0019H\u0096\u0001J\r\u0010|\u001a\u00020\u0017*\u00020\u0019H\u0096\u0001J\r\u0010}\u001a\u00020\u0017*\u00020\u0019H\u0096\u0001J\r\u0010~\u001a\u00020\u0017*\u00020\u001eH\u0096\u0001J\r\u0010\u007f\u001a\u00020\u0017*\u00020\u0019H\u0096\u0001J\u000e\u0010\u0080\u0001\u001a\u00020\u0017*\u00020\u001eH\u0096\u0001J\u000e\u0010\u0081\u0001\u001a\u00020\u0017*\u00020\u001eH\u0096\u0001J\u000e\u0010\u0081\u0001\u001a\u00020\u0017*\u00020\u0019H\u0096\u0001J\u000e\u0010\u0082\u0001\u001a\u00020\u0017*\u00020\u0019H\u0096\u0001J\u000e\u0010\u0083\u0001\u001a\u00020\u0017*\u00020\u001eH\u0096\u0001J\u000e\u0010\u0084\u0001\u001a\u00020\u0017*\u00020\u001eH\u0096\u0001J\u000e\u0010\u0085\u0001\u001a\u00020\u0017*\u00020\u0019H\u0096\u0001J\u000e\u0010\u0086\u0001\u001a\u00020\u0017*\u00020\u0019H\u0096\u0001J\u000e\u0010\u0087\u0001\u001a\u00020\u0017*\u00020\u0019H\u0096\u0001J\u000e\u0010\u0088\u0001\u001a\u00020\u0017*\u00020\u0019H\u0096\u0001J\u000e\u0010\u0089\u0001\u001a\u00020\u0017*\u00020\u001cH\u0096\u0001J\u000e\u0010\u008a\u0001\u001a\u00020\u0017*\u00020\u0019H\u0096\u0001J\u000e\u0010\u008b\u0001\u001a\u00020\u0017*\u00020\u0019H\u0096\u0001J\u000e\u0010\u008c\u0001\u001a\u00020\u0017*\u00020\u001eH\u0096\u0001J\u000e\u0010\u008d\u0001\u001a\u00020\u0017*\u00020\u0019H\u0096\u0001J\r\u0010\u008e\u0001\u001a\u00020\u0017*\u00020\u001cH\u0016J\u000e\u0010\u008f\u0001\u001a\u00020\u0017*\u00020\u0019H\u0096\u0001J\u000e\u0010\u0090\u0001\u001a\u00020\u0017*\u00020\u001eH\u0096\u0001J\u000e\u0010\u0090\u0001\u001a\u00020\u0017*\u00020\u001cH\u0096\u0001J\u000e\u0010\u0091\u0001\u001a\u00020\u0017*\u00020\u0019H\u0096\u0001J\u000e\u0010\u0092\u0001\u001a\u00020\u0017*\u00020\u001eH\u0097\u0001J\u000e\u0010\u0093\u0001\u001a\u00020\u0017*\u00020\u001eH\u0096\u0001J\u000e\u0010\u0094\u0001\u001a\u00020\u0017*\u00020\u0019H\u0096\u0001J\u000e\u0010\u0095\u0001\u001a\u00020\u0017*\u00020\u001eH\u0096\u0001J\u000e\u0010\u0096\u0001\u001a\u00020\u0017*\u00020\u001eH\u0096\u0001J\u000e\u0010\u0097\u0001\u001a\u00020\u0017*\u00020\u001eH\u0096\u0001J\u000e\u0010\u0098\u0001\u001a\u00020\u0017*\u00020>H\u0096\u0001J\u000e\u0010\u0099\u0001\u001a\u00020\u0017*\u00020\u001cH\u0096\u0001J\u000e\u0010\u009a\u0001\u001a\u00020\u0017*\u00020>H\u0096\u0001J\r\u0010\u009b\u0001\u001a\u00020\u0017*\u00020\u001eH\u0016J\u000e\u0010\u009c\u0001\u001a\u00020\u0017*\u00020\u0012H\u0096\u0001J\r\u0010\u009d\u0001\u001a\u00020\u0017*\u00020\u0019H\u0016J\u000e\u0010\u009e\u0001\u001a\u00020\u0017*\u00020\u001eH\u0096\u0001J\u000e\u0010\u009f\u0001\u001a\u00020\u0017*\u00020\u001cH\u0096\u0001J\u000e\u0010 \u0001\u001a\u00020\u0017*\u00020GH\u0096\u0001J\u000e\u0010¡\u0001\u001a\u00020\u0017*\u00020\u001cH\u0096\u0001J\u000e\u0010¢\u0001\u001a\u00020\u0017*\u00020\u001cH\u0096\u0001J\u000e\u0010£\u0001\u001a\u00020\u0017*\u00020\u001cH\u0096\u0001J\r\u0010¤\u0001\u001a\u00020\u0017*\u00020\u001cH\u0016J\r\u0010¥\u0001\u001a\u00020\u0017*\u00020\u0019H\u0016J\u000e\u0010¦\u0001\u001a\u00020\u0017*\u00020\u0019H\u0096\u0001J\u000e\u0010§\u0001\u001a\u00020\u0017*\u00020\u001eH\u0096\u0001J\u000e\u0010¨\u0001\u001a\u00020\u0017*\u00020\u0019H\u0096\u0001J\u000e\u0010©\u0001\u001a\u00020\u0017*\u00020\u001eH\u0096\u0001J\u0015\u0010ª\u0001\u001a\t\u0012\u0004\u0012\u00020G0«\u0001*\u00020<H\u0096\u0003J\u000e\u0010¬\u0001\u001a\u00020\u001c*\u00020CH\u0096\u0001J\u000e\u0010\u00ad\u0001\u001a\u00020\u001c*\u00020\u001eH\u0096\u0001J\u0010\u0010®\u0001\u001a\u0004\u0018\u00010\u001e*\u00020>H\u0096\u0001J\u000e\u0010¯\u0001\u001a\u00020\u001e*\u00020\u001eH\u0096\u0001J\u000e\u0010°\u0001\u001a\u00020\u001e*\u00020\u001eH\u0096\u0001J\u000e\u0010±\u0001\u001a\u00020\u001c*\u00020\u001cH\u0096\u0001J\u000e\u0010²\u0001\u001a\u00020\u001c*\u00020@H\u0096\u0001J\u000e\u0010³\u0001\u001a\u00020\u001c*\u00020\u001cH\u0096\u0001J\u000e\u0010´\u0001\u001a\u00020**\u00020\u0019H\u0096\u0001J\u0015\u0010µ\u0001\u001a\t\u0012\u0004\u0012\u00020\u001e0¶\u0001*\u00020\u001cH\u0096\u0001J\u000f\u0010·\u0001\u001a\u00020G*\u00030¸\u0001H\u0096\u0001J\u0017\u0010¹\u0001\u001a\u00020G*\u00020G2\u0007\u0010º\u0001\u001a\u00020\u001eH\u0096\u0001J\u000e\u0010»\u0001\u001a\u00030¼\u0001*\u00020\u0012H\u0016J\u0016\u0010½\u0001\u001a\u00020\u001e*\u00020'2\u0006\u0010 \u001a\u00020\u001eH\u0096\u0001J\u000e\u0010¾\u0001\u001a\u00020**\u00020<H\u0096\u0001J\u0015\u0010¿\u0001\u001a\t\u0012\u0004\u0012\u00020\u001e0¶\u0001*\u00020\u0019H\u0096\u0001J\u000f\u0010À\u0001\u001a\u00030¸\u0001*\u00020>H\u0096\u0001J\u000e\u0010À\u0001\u001a\u00020\u0019*\u00020\u001eH\u0096\u0001J\u000e\u0010À\u0001\u001a\u00020\u0019*\u00020\u001cH\u0096\u0001J\u001c\u0010Á\u0001\u001a\u00020K*\u00020\u00192\r\u0010Â\u0001\u001a\b\u0012\u0004\u0012\u00020\u001e00H\u0016J\u000e\u0010Ã\u0001\u001a\u00020\u0019*\u00020\u0019H\u0096\u0001J\u000e\u0010Ä\u0001\u001a\u00020\u001c*\u00020CH\u0096\u0001J\u000e\u0010Å\u0001\u001a\u00020**\u00020\u0012H\u0096\u0001J\u000e\u0010Æ\u0001\u001a\u00020\u001c*\u00020\u001eH\u0096\u0001J\u0017\u0010Ç\u0001\u001a\u00020\u001e*\u00020\u001e2\u0007\u0010È\u0001\u001a\u00020\u0017H\u0096\u0001J\u0017\u0010Ç\u0001\u001a\u00020\u001c*\u00020\u001c2\u0007\u0010È\u0001\u001a\u00020\u0017H\u0096\u0001R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u001f\u0010\u0005\u001a\u0010\u0012\u0004\u0012\u00020\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0018\u0010\u0011\u001a\u0004\u0018\u00010\u0012*\u00020\u0013X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015¨\u0006É\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/jvm/ConeTypeSystemCommonBackendContextForTypeMapping;", "Lorg/jetbrains/kotlin/types/TypeSystemCommonBackendContext;", "Lorg/jetbrains/kotlin/types/TypeSystemCommonBackendContextForTypeMapping;", "context", "Lorg/jetbrains/kotlin/fir/types/ConeTypeContext;", "unresolvedQualifierRemapper", "Lkotlin/Function1;", "", "(Lorg/jetbrains/kotlin/fir/types/ConeTypeContext;Lkotlin/jvm/functions/Function1;)V", "getContext", "()Lorg/jetbrains/kotlin/fir/types/ConeTypeContext;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "symbolProvider", "Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolProvider;", "getUnresolvedQualifierRemapper", "()Lkotlin/jvm/functions/Function1;", "typeParameter", "Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;", "Lorg/jetbrains/kotlin/types/model/TypeVariableTypeConstructorMarker;", "getTypeParameter", "(Lorg/jetbrains/kotlin/types/model/TypeVariableTypeConstructorMarker;)Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;", "areEqualTypeConstructors", "", "c1", "Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;", "c2", "arrayType", "Lorg/jetbrains/kotlin/types/model/SimpleTypeMarker;", "componentType", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "captureFromArguments", ModuleXmlParser.TYPE, NotificationCompat.CATEGORY_STATUS, "Lorg/jetbrains/kotlin/types/model/CaptureStatus;", "captureFromExpression", "continuationTypeConstructor", "Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;", "createEmptySubstitutor", "Lorg/jetbrains/kotlin/types/model/TypeSubstitutorMarker;", "functionNTypeConstructor", "n", "", "identicalArguments", "a", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "intersectTypes", SpecTypePair.NAME_types, "", "nullableAnyType", "possiblyErrorTypeConstructorByClassId", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "substitutionSupertypePolicy", "Lorg/jetbrains/kotlin/types/TypeCheckerState$SupertypesPolicy;", "typeSubstitutorByTypeConstructor", "map", "", "argumentsCount", "asArgumentList", "Lorg/jetbrains/kotlin/types/model/TypeArgumentListMarker;", "asCapturedType", "Lorg/jetbrains/kotlin/types/model/CapturedTypeMarker;", "asDefinitelyNotNullType", "Lorg/jetbrains/kotlin/types/model/DefinitelyNotNullTypeMarker;", "asDynamicType", "Lorg/jetbrains/kotlin/types/model/DynamicTypeMarker;", "Lorg/jetbrains/kotlin/types/model/FlexibleTypeMarker;", "asFlexibleType", "asSimpleType", "asTypeArgument", "Lorg/jetbrains/kotlin/types/model/TypeArgumentMarker;", "asTypeParameter", "captureStatus", "defaultType", "Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "fastCorrespondingSupertypes", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "get", "index", "getAnnotationFirstArgumentValue", "", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "getArgument", "getArgumentOrNull", "getArguments", "getAttributes", "Lorg/jetbrains/kotlin/types/model/AnnotationMarker;", "getClassFqNameUnsafe", "Lorg/jetbrains/kotlin/name/FqNameUnsafe;", "getCustomAttributes", "getName", "Lorg/jetbrains/kotlin/name/Name;", "getNameForErrorType", "getParameter", "getParameters", "getPrimitiveArrayType", "Lorg/jetbrains/kotlin/builtins/PrimitiveType;", "getPrimitiveType", "getRepresentativeUpperBound", "getSubstitutedUnderlyingType", "getType", "getTypeConstructor", "getTypeParameterClassifier", "getUnsubstitutedUnderlyingType", "getUpperBound", "getUpperBounds", "getValueClassProperties", "Lkotlin/Pair;", "getVariance", "Lorg/jetbrains/kotlin/types/model/TypeVariance;", "hasAnnotation", "hasCustomAttributes", "hasFlexibleNullability", "hasRecursiveBounds", "selfConstructor", "isAnonymous", "isAnyConstructor", "isArrayOrNullableArray", "isCapturedDynamic", "isCapturedType", "isClassType", "isClassTypeConstructor", "isCommonFinalClassConstructor", "isDefinitelyClassTypeConstructor", "isDefinitelyNotNullType", "isDenotable", "isDynamic", "isError", "isFinalClassOrEnumEntryOrAnnotationClassConstructor", "isFlexible", "isFlexibleNothing", "isInlineClass", "isInnerClass", "isIntegerConstantOperatorTypeConstructor", "isIntegerLiteralConstantTypeConstructor", "isIntegerLiteralType", "isIntegerLiteralTypeConstructor", "isInterface", "isInterfaceOrAnnotationClass", "isIntersection", "isKClass", "isLocalType", "isMarkedNullable", "isMultiFieldValueClass", "isNotNullTypeParameter", "isNothing", "isNothingConstructor", "isNullableAny", "isNullableNothing", "isNullableType", "isOldCapturedType", "isPrimitiveType", "isProjectionNotNull", "isRawType", "isReified", "isScript", "isSimpleType", "isSingleClassifierType", "isStarProjection", "isStubType", "isStubTypeForBuilderInference", "isStubTypeForVariableInSubtyping", "isSuspendFunction", "isTypeParameter", "isTypeParameterTypeConstructor", "isTypeVariableType", "isUnderKotlinPackage", "isUninferredParameter", "iterator", "", "lowerBound", "lowerBoundIfFlexible", "lowerType", "makeDefinitelyNotNullOrNotNull", "makeNullable", "makeSimpleTypeDefinitelyNotNullOrNotNull", "original", "originalIfDefinitelyNotNullable", "parametersCount", "possibleIntegerTypes", "", "projection", "Lorg/jetbrains/kotlin/types/model/CapturedTypeConstructorMarker;", "replaceType", "newType", "representativeUpperBound", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "safeSubstitute", "size", "supertypes", "typeConstructor", "typeWithArguments", "arguments", "unwrapStubTypeVariableConstructor", "upperBound", "upperBoundCount", "upperBoundIfFlexible", "withNullability", "nullable", "jvm-backend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeTypeSystemCommonBackendContextForTypeMapping implements TypeSystemCommonBackendContext, TypeSystemCommonBackendContextForTypeMapping {
    private final ConeTypeContext context;
    private final FirSession session;
    private final FirSymbolProvider symbolProvider;
    private final Function1<String, String> unresolvedQualifierRemapper;

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean areEqualTypeConstructors(TypeConstructorMarker c1, TypeConstructorMarker c2) {
        Intrinsics.checkNotNullParameter(c1, "c1");
        Intrinsics.checkNotNullParameter(c2, "c2");
        return this.context.areEqualTypeConstructors(c1, c2);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public int argumentsCount(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.context.argumentsCount(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public SimpleTypeMarker arrayType(KotlinTypeMarker componentType) {
        Intrinsics.checkNotNullParameter(componentType, "componentType");
        return this.context.arrayType(componentType);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeArgumentListMarker asArgumentList(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.context.asArgumentList(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public CapturedTypeMarker asCapturedType(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.context.asCapturedType(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public DefinitelyNotNullTypeMarker asDefinitelyNotNullType(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.context.asDefinitelyNotNullType(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public DynamicTypeMarker asDynamicType(FlexibleTypeMarker flexibleTypeMarker) {
        Intrinsics.checkNotNullParameter(flexibleTypeMarker, "<this>");
        return this.context.asDynamicType(flexibleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public FlexibleTypeMarker asFlexibleType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.context.asFlexibleType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker asSimpleType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.context.asSimpleType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeArgumentMarker asTypeArgument(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.context.asTypeArgument(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker captureFromArguments(SimpleTypeMarker type, CaptureStatus status) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(status, "status");
        return this.context.captureFromArguments(type, status);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker captureFromExpression(KotlinTypeMarker type) {
        Intrinsics.checkNotNullParameter(type, "type");
        return this.context.captureFromExpression(type);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public CaptureStatus captureStatus(CapturedTypeMarker capturedTypeMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
        return this.context.captureStatus(capturedTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeSubstitutorMarker createEmptySubstitutor() {
        return this.context.createEmptySubstitutor();
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public List<SimpleTypeMarker> fastCorrespondingSupertypes(SimpleTypeMarker simpleTypeMarker, TypeConstructorMarker constructor) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        return this.context.fastCorrespondingSupertypes(simpleTypeMarker, constructor);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeArgumentMarker get(TypeArgumentListMarker typeArgumentListMarker, int i) {
        Intrinsics.checkNotNullParameter(typeArgumentListMarker, "<this>");
        return this.context.get(typeArgumentListMarker, i);
    }

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public Object getAnnotationFirstArgumentValue(KotlinTypeMarker kotlinTypeMarker, FqName fqName) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        return this.context.getAnnotationFirstArgumentValue(kotlinTypeMarker, fqName);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeArgumentMarker getArgument(KotlinTypeMarker kotlinTypeMarker, int i) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.context.getArgument(kotlinTypeMarker, i);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeArgumentMarker getArgumentOrNull(SimpleTypeMarker simpleTypeMarker, int i) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.context.getArgumentOrNull(simpleTypeMarker, i);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public List<TypeArgumentMarker> getArguments(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.context.getArguments(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public List<AnnotationMarker> getAttributes(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.context.getAttributes(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public FqNameUnsafe getClassFqNameUnsafe(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.context.getClassFqNameUnsafe(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public List<AnnotationMarker> getCustomAttributes(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.context.getCustomAttributes(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public Name getName(TypeParameterMarker typeParameterMarker) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        return this.context.getName(typeParameterMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeParameterMarker getParameter(TypeConstructorMarker typeConstructorMarker, int i) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.context.getParameter(typeConstructorMarker, i);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public List<TypeParameterMarker> getParameters(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.context.getParameters(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public PrimitiveType getPrimitiveArrayType(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.context.getPrimitiveArrayType(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public PrimitiveType getPrimitiveType(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.context.getPrimitiveType(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public KotlinTypeMarker getRepresentativeUpperBound(TypeParameterMarker typeParameterMarker) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        return this.context.getRepresentativeUpperBound(typeParameterMarker);
    }

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public KotlinTypeMarker getSubstitutedUnderlyingType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.context.getSubstitutedUnderlyingType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker getType(TypeArgumentMarker typeArgumentMarker) {
        Intrinsics.checkNotNullParameter(typeArgumentMarker, "<this>");
        return this.context.getType(typeArgumentMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeConstructorMarker getTypeConstructor(TypeParameterMarker typeParameterMarker) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        return this.context.getTypeConstructor(typeParameterMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeParameterMarker getTypeParameter(TypeVariableTypeConstructorMarker typeVariableTypeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeVariableTypeConstructorMarker, "<this>");
        return this.context.getTypeParameter(typeVariableTypeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeParameterMarker getTypeParameterClassifier(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.context.getTypeParameterClassifier(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public KotlinTypeMarker getUnsubstitutedUnderlyingType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.context.getUnsubstitutedUnderlyingType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker getUpperBound(TypeParameterMarker typeParameterMarker, int i) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        return this.context.getUpperBound(typeParameterMarker, i);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public List<KotlinTypeMarker> getUpperBounds(TypeParameterMarker typeParameterMarker) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        return this.context.getUpperBounds(typeParameterMarker);
    }

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public List<Pair<Name, SimpleTypeMarker>> getValueClassProperties(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.context.getValueClassProperties(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeVariance getVariance(TypeArgumentMarker typeArgumentMarker) {
        Intrinsics.checkNotNullParameter(typeArgumentMarker, "<this>");
        return this.context.getVariance(typeArgumentMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeVariance getVariance(TypeParameterMarker typeParameterMarker) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        return this.context.getVariance(typeParameterMarker);
    }

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public boolean hasAnnotation(KotlinTypeMarker kotlinTypeMarker, FqName fqName) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        return this.context.hasAnnotation(kotlinTypeMarker, fqName);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean hasCustomAttributes(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.context.hasCustomAttributes(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean hasFlexibleNullability(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.context.hasFlexibleNullability(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean hasRecursiveBounds(TypeParameterMarker typeParameterMarker, TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        return this.context.hasRecursiveBounds(typeParameterMarker, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemOptimizationContext
    public boolean identicalArguments(SimpleTypeMarker a, SimpleTypeMarker b) {
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        return this.context.identicalArguments(a, b);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker intersectTypes(List<? extends KotlinTypeMarker> types) {
        Intrinsics.checkNotNullParameter(types, "types");
        return this.context.intersectTypes(types);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    /* JADX INFO: renamed from: intersectTypes */
    public SimpleTypeMarker mo6107intersectTypes(List<? extends SimpleTypeMarker> types) {
        Intrinsics.checkNotNullParameter(types, "types");
        return this.context.mo6107intersectTypes(types);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isAnonymous(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.context.isAnonymous(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isAnyConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.context.isAnyConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public boolean isArrayOrNullableArray(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.context.isArrayOrNullableArray(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isCapturedDynamic(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.context.isCapturedDynamic(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isCapturedType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.context.isCapturedType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isClassType(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.context.isClassType(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isClassTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.context.isClassTypeConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isCommonFinalClassConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.context.isCommonFinalClassConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isDefinitelyClassTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.context.isDefinitelyClassTypeConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isDefinitelyNotNullType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.context.isDefinitelyNotNullType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isDenotable(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.context.isDenotable(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isDynamic(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.context.isDynamic(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isError(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.context.isError(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isError(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.context.isError(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public boolean isFinalClassOrEnumEntryOrAnnotationClassConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.context.isFinalClassOrEnumEntryOrAnnotationClassConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isFlexible(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.context.isFlexible(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isFlexibleNothing(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.context.isFlexibleNothing(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public boolean isInlineClass(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.context.isInlineClass(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public boolean isInnerClass(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.context.isInnerClass(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isIntegerConstantOperatorTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.context.isIntegerConstantOperatorTypeConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isIntegerLiteralConstantTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.context.isIntegerLiteralConstantTypeConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isIntegerLiteralType(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.context.isIntegerLiteralType(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isIntegerLiteralTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.context.isIntegerLiteralTypeConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isInterface(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.context.isInterface(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public boolean isInterfaceOrAnnotationClass(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.context.isInterfaceOrAnnotationClass(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isIntersection(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.context.isIntersection(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isLocalType(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.context.isLocalType(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isMarkedNullable(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.context.isMarkedNullable(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isMarkedNullable(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.context.isMarkedNullable(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public boolean isMultiFieldValueClass(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.context.isMultiFieldValueClass(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    @ObsoleteTypeKind
    public boolean isNotNullTypeParameter(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.context.isNotNullTypeParameter(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isNothing(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.context.isNothing(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isNothingConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.context.isNothingConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isNullableAny(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.context.isNullableAny(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isNullableNothing(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.context.isNullableNothing(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isNullableType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.context.isNullableType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isOldCapturedType(CapturedTypeMarker capturedTypeMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
        return this.context.isOldCapturedType(capturedTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isPrimitiveType(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.context.isPrimitiveType(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isProjectionNotNull(CapturedTypeMarker capturedTypeMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
        return this.context.isProjectionNotNull(capturedTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public boolean isReified(TypeParameterMarker typeParameterMarker) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        return this.context.isReified(typeParameterMarker);
    }

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContextForTypeMapping
    public boolean isScript(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return false;
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isSimpleType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.context.isSimpleType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isSingleClassifierType(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.context.isSingleClassifierType(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isStarProjection(TypeArgumentMarker typeArgumentMarker) {
        Intrinsics.checkNotNullParameter(typeArgumentMarker, "<this>");
        return this.context.isStarProjection(typeArgumentMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isStubType(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.context.isStubType(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isStubTypeForBuilderInference(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.context.isStubTypeForBuilderInference(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isStubTypeForVariableInSubtyping(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.context.isStubTypeForVariableInSubtyping(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isTypeParameterTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.context.isTypeParameterTypeConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isTypeVariableType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.context.isTypeVariableType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public boolean isUnderKotlinPackage(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.context.isUnderKotlinPackage(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isUninferredParameter(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.context.isUninferredParameter(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public Iterator<TypeArgumentMarker> iterator(TypeArgumentListMarker typeArgumentListMarker) {
        Intrinsics.checkNotNullParameter(typeArgumentListMarker, "<this>");
        return this.context.iterator(typeArgumentListMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker lowerBound(FlexibleTypeMarker flexibleTypeMarker) {
        Intrinsics.checkNotNullParameter(flexibleTypeMarker, "<this>");
        return this.context.lowerBound(flexibleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker lowerBoundIfFlexible(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.context.lowerBoundIfFlexible(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker lowerType(CapturedTypeMarker capturedTypeMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
        return this.context.lowerType(capturedTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker makeDefinitelyNotNullOrNotNull(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.context.makeDefinitelyNotNullOrNotNull(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public KotlinTypeMarker makeNullable(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.context.makeNullable(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker makeSimpleTypeDefinitelyNotNullOrNotNull(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.context.makeSimpleTypeDefinitelyNotNullOrNotNull(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext, org.jetbrains.kotlin.types.model.TypeSystemBuiltInsContext
    public SimpleTypeMarker nullableAnyType() {
        return this.context.nullableAnyType();
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker original(DefinitelyNotNullTypeMarker definitelyNotNullTypeMarker) {
        Intrinsics.checkNotNullParameter(definitelyNotNullTypeMarker, "<this>");
        return this.context.original(definitelyNotNullTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker originalIfDefinitelyNotNullable(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.context.originalIfDefinitelyNotNullable(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public int parametersCount(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.context.parametersCount(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public Collection<KotlinTypeMarker> possibleIntegerTypes(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.context.possibleIntegerTypes(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeArgumentMarker projection(CapturedTypeConstructorMarker capturedTypeConstructorMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeConstructorMarker, "<this>");
        return this.context.projection(capturedTypeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeArgumentMarker replaceType(TypeArgumentMarker typeArgumentMarker, KotlinTypeMarker newType) {
        Intrinsics.checkNotNullParameter(typeArgumentMarker, "<this>");
        Intrinsics.checkNotNullParameter(newType, "newType");
        return this.context.replaceType(typeArgumentMarker, newType);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker safeSubstitute(TypeSubstitutorMarker typeSubstitutorMarker, KotlinTypeMarker type) {
        Intrinsics.checkNotNullParameter(typeSubstitutorMarker, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        return this.context.safeSubstitute(typeSubstitutorMarker, type);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public int size(TypeArgumentListMarker typeArgumentListMarker) {
        Intrinsics.checkNotNullParameter(typeArgumentListMarker, "<this>");
        return this.context.size(typeArgumentListMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeCheckerState.SupertypesPolicy substitutionSupertypePolicy(SimpleTypeMarker type) {
        Intrinsics.checkNotNullParameter(type, "type");
        return this.context.substitutionSupertypePolicy(type);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public Collection<KotlinTypeMarker> supertypes(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.context.supertypes(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public CapturedTypeConstructorMarker typeConstructor(CapturedTypeMarker capturedTypeMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
        return this.context.typeConstructor(capturedTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeConstructorMarker typeConstructor(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.context.typeConstructor(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeConstructorMarker typeConstructor(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.context.typeConstructor(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeSubstitutorMarker typeSubstitutorByTypeConstructor(Map<TypeConstructorMarker, ? extends KotlinTypeMarker> map) {
        Intrinsics.checkNotNullParameter(map, "map");
        return this.context.typeSubstitutorByTypeConstructor(map);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeConstructorMarker unwrapStubTypeVariableConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.context.unwrapStubTypeVariableConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker upperBound(FlexibleTypeMarker flexibleTypeMarker) {
        Intrinsics.checkNotNullParameter(flexibleTypeMarker, "<this>");
        return this.context.upperBound(flexibleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public int upperBoundCount(TypeParameterMarker typeParameterMarker) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        return this.context.upperBoundCount(typeParameterMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker upperBoundIfFlexible(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.context.upperBoundIfFlexible(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker withNullability(KotlinTypeMarker kotlinTypeMarker, boolean z) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.context.withNullability(kotlinTypeMarker, z);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker withNullability(SimpleTypeMarker simpleTypeMarker, boolean z) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.context.withNullability(simpleTypeMarker, z);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ConeTypeSystemCommonBackendContextForTypeMapping(ConeTypeContext context, Function1<? super String, String> unresolvedQualifierRemapper) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(unresolvedQualifierRemapper, "unresolvedQualifierRemapper");
        this.context = context;
        this.unresolvedQualifierRemapper = unresolvedQualifierRemapper;
        FirSession firSession = context.get$session();
        this.session = firSession;
        this.symbolProvider = FirSymbolProviderKt.getSymbolProvider(firSession);
    }

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContextForTypeMapping
    public /* bridge */ /* synthetic */ SimpleTypeMarker typeWithArguments(TypeConstructorMarker typeConstructorMarker, List list) {
        return typeWithArguments(typeConstructorMarker, (List<? extends KotlinTypeMarker>) list);
    }

    public final ConeTypeContext getContext() {
        return this.context;
    }

    public final Function1<String, String> getUnresolvedQualifierRemapper() {
        return this.unresolvedQualifierRemapper;
    }

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContextForTypeMapping
    public boolean isTypeParameter(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return typeConstructorMarker instanceof ConeTypeParameterLookupTag;
    }

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContextForTypeMapping
    public TypeParameterMarker asTypeParameter(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        if (!isTypeParameter(typeConstructorMarker)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return (ConeTypeParameterLookupTag) typeConstructorMarker;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContextForTypeMapping
    public ConeSimpleKotlinType defaultType(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        if (!(typeConstructorMarker instanceof ConeClassifierLookupTag)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        if (typeConstructorMarker instanceof ConeTypeParameterLookupTag) {
            return new ConeTypeParameterTypeImpl((ConeTypeParameterLookupTag) typeConstructorMarker, false, null, 4, null);
        }
        if (typeConstructorMarker instanceof ConeClassLikeLookupTag) {
            ConeClassLikeLookupTag coneClassLikeLookupTag = (ConeClassLikeLookupTag) typeConstructorMarker;
            FirClassLikeSymbol<?> symbol = LookupTagUtilsKt.toSymbol(coneClassLikeLookupTag, this.session);
            FirRegularClassSymbol firRegularClassSymbol = symbol instanceof FirRegularClassSymbol ? (FirRegularClassSymbol) symbol : null;
            if (firRegularClassSymbol == null) {
                return new ConeErrorType(new ConeUnresolvedSymbolError(coneClassLikeLookupTag.getClassId()), false, null, null, 14, null);
            }
            return ScopeUtilsKt.defaultType((FirClass) firRegularClassSymbol.getFir());
        }
        throw new IllegalStateException(("Unsupported type constructor: " + typeConstructorMarker).toString());
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContextForTypeMapping
    public boolean isSuspendFunction(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        if (!(simpleTypeMarker instanceof ConeSimpleKotlinType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return FunctionalTypeUtilsKt.isSuspendOrKSuspendFunctionType((ConeKotlinType) simpleTypeMarker, this.session);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContextForTypeMapping
    public boolean isKClass(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        if (!(simpleTypeMarker instanceof ConeSimpleKotlinType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return InferenceUtilsKt.isKClassType((ConeKotlinType) simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isRawType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return kotlinTypeMarker instanceof ConeRawType;
    }

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContextForTypeMapping
    public ConeSimpleKotlinType typeWithArguments(TypeConstructorMarker typeConstructorMarker, List<? extends KotlinTypeMarker> arguments) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        Iterator<T> it = arguments.iterator();
        while (it.hasNext()) {
            if (!(((KotlinTypeMarker) it.next()) instanceof ConeKotlinType)) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
        }
        return (ConeSimpleKotlinType) TypeUtilsKt.withArguments(defaultType(typeConstructorMarker), (ConeTypeProjection[]) arguments.toArray(new ConeKotlinType[0]));
    }

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContextForTypeMapping
    public ConeKotlinType representativeUpperBound(TypeParameterMarker typeParameterMarker) {
        Object obj;
        ClassKind classKind;
        ConeClassLikeType coneClassLikeTypeFullyExpandedType$default;
        ConeClassLikeLookupTag lookupTag;
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        if (!(typeParameterMarker instanceof ConeTypeParameterLookupTag)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        List<FirResolvedTypeRef> resolvedBounds = ((ConeTypeParameterLookupTag) typeParameterMarker).getTypeParameterSymbol().getResolvedBounds();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(resolvedBounds, 10));
        Iterator<T> it = resolvedBounds.iterator();
        while (it.hasNext()) {
            arrayList.add(FirTypeUtilsKt.getConeType((FirResolvedTypeRef) it.next()));
        }
        ArrayList arrayList2 = arrayList;
        Iterator it2 = arrayList2.iterator();
        while (true) {
            obj = null;
            if (!it2.hasNext()) {
                break;
            }
            Object next = it2.next();
            ConeKotlinType coneKotlinType = (ConeKotlinType) next;
            ConeClassLikeType coneClassLikeType = coneKotlinType instanceof ConeClassLikeType ? (ConeClassLikeType) coneKotlinType : null;
            FirClassLikeSymbol<?> symbol = (coneClassLikeType == null || (coneClassLikeTypeFullyExpandedType$default = TypeExpansionUtilsKt.fullyExpandedType$default(coneClassLikeType, this.session, null, 2, null)) == null || (lookupTag = coneClassLikeTypeFullyExpandedType$default.getLookupTag()) == null) ? null : LookupTagUtilsKt.toSymbol(lookupTag, this.session);
            FirRegularClassSymbol firRegularClassSymbol = symbol instanceof FirRegularClassSymbol ? (FirRegularClassSymbol) symbol : null;
            if (firRegularClassSymbol != null && (classKind = ((FirRegularClass) firRegularClassSymbol.getFir()).getClassKind()) != ClassKind.INTERFACE && classKind != ClassKind.ANNOTATION_CLASS) {
                obj = next;
                break;
            }
        }
        ConeKotlinType coneKotlinType2 = (ConeKotlinType) obj;
        return coneKotlinType2 == null ? (ConeKotlinType) CollectionsKt.first((List) arrayList2) : coneKotlinType2;
    }

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContextForTypeMapping
    public ConeClassLikeLookupTag continuationTypeConstructor() {
        return possiblyErrorTypeConstructorByClassId(StandardClassIds.INSTANCE.getContinuation());
    }

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContextForTypeMapping
    public TypeConstructorMarker functionNTypeConstructor(int n) {
        ConeClassLikeLookupTag lookupTag;
        FirClassLikeSymbol<?> classLikeSymbolByClassId = this.symbolProvider.getClassLikeSymbolByClassId(StandardClassIds.INSTANCE.FunctionN(n));
        if (classLikeSymbolByClassId == null || (lookupTag = classLikeSymbolByClassId.getLookupTag()) == null) {
            throw new IllegalStateException(("Function" + n + " class not found").toString());
        }
        return lookupTag;
    }

    private final ConeClassLikeLookupTag possiblyErrorTypeConstructorByClassId(ClassId classId) {
        ConeClassLikeLookupTag lookupTag;
        FirClassLikeSymbol<?> classLikeSymbolByClassId = this.symbolProvider.getClassLikeSymbolByClassId(classId);
        return (classLikeSymbolByClassId == null || (lookupTag = classLikeSymbolByClassId.getLookupTag()) == null) ? new ConeClassLikeErrorLookupTag(classId) : lookupTag;
    }

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContextForTypeMapping
    public String getNameForErrorType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        if (!(kotlinTypeMarker instanceof ConeErrorType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        ConeDiagnostic diagnostic = ((ConeErrorType) kotlinTypeMarker).getDiagnostic();
        String qualifier = diagnostic instanceof ConeUnresolvedTypeQualifierError ? ((ConeUnresolvedTypeQualifierError) diagnostic).getQualifier() : null;
        if (qualifier == null) {
            return null;
        }
        String strInvoke = this.unresolvedQualifierRemapper.invoke(qualifier);
        return strInvoke == null ? qualifier : strInvoke;
    }
}
