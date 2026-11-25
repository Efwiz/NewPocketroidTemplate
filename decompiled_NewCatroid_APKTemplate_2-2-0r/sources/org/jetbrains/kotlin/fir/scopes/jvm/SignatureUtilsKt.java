package org.jetbrains.kotlin.fir.scopes.jvm;

import androidx.exifinterface.media.ExifInterface;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import kotlin.text.StringsKt;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.builtins.jvm.JavaToKotlinClassMap;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeDefinitelyNotNullType;
import org.jetbrains.kotlin.fir.types.ConeErrorType;
import org.jetbrains.kotlin.fir.types.ConeFlexibleType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeStarProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeParameterType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitAnyTypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitNullableAnyTypeRef;
import org.jetbrains.kotlin.fir.types.jvm.FirJavaTypeRef;
import org.jetbrains.kotlin.fir.utils.exceptions.FirExceptionUtilsKt;
import org.jetbrains.kotlin.incremental.IncrementalJvmCacheKt;
import org.jetbrains.kotlin.load.java.structure.JavaPrimitiveType;
import org.jetbrains.kotlin.load.java.structure.JavaType;
import org.jetbrains.kotlin.load.kotlin.SignatureBuildingComponents;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNameUnsafe;
import org.jetbrains.kotlin.name.StandardClassIds;
import org.jetbrains.kotlin.utils.exceptions.ExceptionAttachmentBuilder;
import org.jetbrains.kotlin.utils.exceptions.KotlinIllegalArgumentExceptionWithAttachments;

/* JADX INFO: compiled from: SignatureUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000L\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\u001a<\u0010\u0007\u001a\u00020\b*\u00060\tj\u0002`\n2\u0006\u0010\u000b\u001a\u00020\f2\u0014\u0010\r\u001a\u0010\u0012\u0004\u0012\u00020\u000f\u0012\u0006\u0012\u0004\u0018\u00010\f0\u000e2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011H\u0002\u001a8\u0010\u0013\u001a\u00020\u0002*\u00020\u00142\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u00022\b\b\u0002\u0010\u0016\u001a\u00020\u00172\u0016\b\u0002\u0010\r\u001a\u0010\u0012\u0004\u0012\u00020\u000f\u0012\u0006\u0012\u0004\u0018\u00010\f0\u000e\u001a\"\u0010\u0018\u001a\u00020\u0002*\u00020\f2\u0016\b\u0002\u0010\r\u001a\u0010\u0012\u0004\u0012\u00020\u000f\u0012\u0006\u0012\u0004\u0018\u00010\f0\u000e\u001a$\u0010\u0019\u001a\u0004\u0018\u00010\u0002*\u00020\u00142\u0016\b\u0002\u0010\r\u001a\u0010\u0012\u0004\u0012\u00020\u000f\u0012\u0006\u0012\u0004\u0018\u00010\f0\u000e\u001a\f\u0010\u001a\u001a\u00020\u0017*\u00020\u000fH\u0002\"\u001a\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u001a\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001b"}, d2 = {"PRIMITIVE_TYPE_ARRAYS_SIGNATURE", "", "", "PRIMITIVE_TYPE_OR_ARRAY_SIGNATURE", "PRIMITIVE_TYPE_SIGNATURE", "unitClassId", "Lorg/jetbrains/kotlin/name/ClassId;", "appendConeType", "", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "coneType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "typeConversion", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "visitedTypeParameters", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeParameterSymbol;", "computeJvmDescriptor", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "customName", "includeReturnType", "", "computeJvmDescriptorRepresentation", "computeJvmSignature", "isVoid", SuffixConstants.EXTENSION_java}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class SignatureUtilsKt {
    private static final Map<String, String> PRIMITIVE_TYPE_ARRAYS_SIGNATURE;
    private static final Map<String, String> PRIMITIVE_TYPE_OR_ARRAY_SIGNATURE;
    private static final Map<String, String> PRIMITIVE_TYPE_SIGNATURE;
    private static final ClassId unitClassId;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.scopes.jvm.SignatureUtilsKt$computeJvmSignature$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: SignatureUtils.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class C46211 extends FunctionReference implements Function1<FirTypeRef, ConeKotlinType> {
        public static final C46211 INSTANCE = new C46211();

        C46211() {
            super(1);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "coneTypeSafe(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;)Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "coneTypeSafe";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinPackage(FirTypeUtilsKt.class, SuffixConstants.EXTENSION_java);
        }

        @Override // kotlin.jvm.functions.Function1
        public final ConeKotlinType invoke(FirTypeRef p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            FirResolvedTypeRef firResolvedTypeRef = p0 instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) p0 : null;
            ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
            if (type instanceof ConeKotlinType) {
                return type;
            }
            return null;
        }
    }

    public static /* synthetic */ String computeJvmSignature$default(FirFunction firFunction, Function1 function1, int i, Object obj) {
        if ((i & 1) != 0) {
            function1 = C46211.INSTANCE;
        }
        return computeJvmSignature(firFunction, function1);
    }

    public static final String computeJvmSignature(FirFunction firFunction, Function1<? super FirTypeRef, ? extends ConeKotlinType> typeConversion) {
        Intrinsics.checkNotNullParameter(firFunction, "<this>");
        Intrinsics.checkNotNullParameter(typeConversion, "typeConversion");
        ConeClassLikeLookupTag coneClassLikeLookupTagContainingClassLookupTag = ClassMembersKt.containingClassLookupTag(firFunction);
        if (coneClassLikeLookupTagContainingClassLookupTag == null) {
            return null;
        }
        return SignatureBuildingComponents.INSTANCE.signature(coneClassLikeLookupTagContainingClassLookupTag.getClassId(), computeJvmDescriptor$default(firFunction, null, false, typeConversion, 3, null));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.scopes.jvm.SignatureUtilsKt$computeJvmDescriptor$1, reason: invalid class name */
    /* JADX INFO: compiled from: SignatureUtils.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class AnonymousClass1 extends FunctionReference implements Function1<FirTypeRef, ConeKotlinType> {
        public static final AnonymousClass1 INSTANCE = new AnonymousClass1();

        AnonymousClass1() {
            super(1);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "coneTypeSafe(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;)Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "coneTypeSafe";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinPackage(FirTypeUtilsKt.class, SuffixConstants.EXTENSION_java);
        }

        @Override // kotlin.jvm.functions.Function1
        public final ConeKotlinType invoke(FirTypeRef p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            FirResolvedTypeRef firResolvedTypeRef = p0 instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) p0 : null;
            ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
            if (type instanceof ConeKotlinType) {
                return type;
            }
            return null;
        }
    }

    public static /* synthetic */ String computeJvmDescriptor$default(FirFunction firFunction, String str, boolean z, Function1 function1, int i, Object obj) {
        if ((i & 1) != 0) {
            str = null;
        }
        if ((i & 2) != 0) {
            z = true;
        }
        if ((i & 4) != 0) {
            function1 = AnonymousClass1.INSTANCE;
        }
        return computeJvmDescriptor(firFunction, str, z, function1);
    }

    public static final String computeJvmDescriptor(FirFunction firFunction, String str, boolean z, Function1<? super FirTypeRef, ? extends ConeKotlinType> typeConversion) {
        Intrinsics.checkNotNullParameter(firFunction, "<this>");
        Intrinsics.checkNotNullParameter(typeConversion, "typeConversion");
        StringBuilder sb = new StringBuilder();
        if (str != null) {
            sb.append(str);
        } else if (firFunction instanceof FirSimpleFunction) {
            sb.append(((FirSimpleFunction) firFunction).getName().asString());
        } else {
            sb.append("<init>");
        }
        sb.append("(");
        for (FirValueParameter firValueParameter : firFunction.getValueParameters()) {
            ConeKotlinType coneKotlinTypeInvoke = typeConversion.invoke(firValueParameter.getReturnTypeRef());
            if (coneKotlinTypeInvoke != null) {
                try {
                    appendConeType(sb, coneKotlinTypeInvoke, typeConversion, new LinkedHashSet());
                } catch (ConcurrentModificationException e) {
                    KotlinIllegalArgumentExceptionWithAttachments kotlinIllegalArgumentExceptionWithAttachments = new KotlinIllegalArgumentExceptionWithAttachments("CME from appendConeType", e);
                    ExceptionAttachmentBuilder exceptionAttachmentBuilder = new ExceptionAttachmentBuilder();
                    exceptionAttachmentBuilder.withEntry("typeClass", Reflection.getOrCreateKotlinClass(coneKotlinTypeInvoke.getClass()).getSimpleName());
                    exceptionAttachmentBuilder.withEntry(ModuleXmlParser.TYPE, coneKotlinTypeInvoke, new Function1<ConeKotlinType, String>() { // from class: org.jetbrains.kotlin.fir.scopes.jvm.SignatureUtilsKt$computeJvmDescriptor$2$1$1$1
                        @Override // kotlin.jvm.functions.Function1
                        public final String invoke(ConeKotlinType it) {
                            Intrinsics.checkNotNullParameter(it, "it");
                            try {
                                return ConeTypeUtilsKt.renderForDebugging(it);
                            } catch (Throwable th) {
                                return "Render is failed due to " + Reflection.getOrCreateKotlinClass(th.getClass());
                            }
                        }
                    });
                    FirExceptionUtilsKt.withFirEntry(exceptionAttachmentBuilder, "parameter", firValueParameter);
                    FirExceptionUtilsKt.withFirEntry(exceptionAttachmentBuilder, "function", firFunction);
                    kotlinIllegalArgumentExceptionWithAttachments.withAttachment("info.txt", exceptionAttachmentBuilder.buildString());
                    throw kotlinIllegalArgumentExceptionWithAttachments;
                }
            }
        }
        sb.append(Constant.AFTER_QUTO);
        if (z) {
            if (!(firFunction instanceof FirSimpleFunction) || isVoid(firFunction.getReturnTypeRef())) {
                sb.append(ExifInterface.GPS_MEASUREMENT_INTERRUPTED);
            } else {
                ConeKotlinType coneKotlinTypeInvoke2 = typeConversion.invoke(firFunction.getReturnTypeRef());
                if (coneKotlinTypeInvoke2 != null) {
                    appendConeType(sb, coneKotlinTypeInvoke2, typeConversion, new LinkedHashSet());
                }
            }
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    static {
        Map<String, String> mapMapOf = MapsKt.mapOf(TuplesKt.to("Boolean", "Z"), TuplesKt.to("Byte", "B"), TuplesKt.to("Char", "C"), TuplesKt.to("Short", ExifInterface.LATITUDE_SOUTH), TuplesKt.to("Int", "I"), TuplesKt.to("Long", "J"), TuplesKt.to("Float", "F"), TuplesKt.to("Double", "D"));
        PRIMITIVE_TYPE_SIGNATURE = mapMapOf;
        ArrayList arrayList = new ArrayList(mapMapOf.size());
        for (Map.Entry<String, String> entry : mapMapOf.entrySet()) {
            arrayList.add(TuplesKt.to(entry.getKey() + "Array", "[" + entry.getValue()));
        }
        Map<String, String> map = MapsKt.toMap(arrayList);
        PRIMITIVE_TYPE_ARRAYS_SIGNATURE = map;
        PRIMITIVE_TYPE_OR_ARRAY_SIGNATURE = MapsKt.plus(PRIMITIVE_TYPE_SIGNATURE, map);
        ClassId classId = ClassId.topLevel(new FqName("kotlin.Unit"));
        Intrinsics.checkNotNullExpressionValue(classId, "topLevel(...)");
        unitClassId = classId;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.scopes.jvm.SignatureUtilsKt$computeJvmDescriptorRepresentation$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: SignatureUtils.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class C46201 extends FunctionReference implements Function1<FirTypeRef, ConeKotlinType> {
        public static final C46201 INSTANCE = new C46201();

        C46201() {
            super(1);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "coneTypeSafe(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;)Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "coneTypeSafe";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinPackage(FirTypeUtilsKt.class, SuffixConstants.EXTENSION_java);
        }

        @Override // kotlin.jvm.functions.Function1
        public final ConeKotlinType invoke(FirTypeRef p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            FirResolvedTypeRef firResolvedTypeRef = p0 instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) p0 : null;
            ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
            if (type instanceof ConeKotlinType) {
                return type;
            }
            return null;
        }
    }

    public static /* synthetic */ String computeJvmDescriptorRepresentation$default(ConeKotlinType coneKotlinType, Function1 function1, int i, Object obj) {
        if ((i & 1) != 0) {
            function1 = C46201.INSTANCE;
        }
        return computeJvmDescriptorRepresentation(coneKotlinType, function1);
    }

    public static final String computeJvmDescriptorRepresentation(ConeKotlinType coneKotlinType, Function1<? super FirTypeRef, ? extends ConeKotlinType> typeConversion) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        Intrinsics.checkNotNullParameter(typeConversion, "typeConversion");
        StringBuilder sb = new StringBuilder();
        appendConeType(sb, coneKotlinType, typeConversion, new LinkedHashSet());
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static final void appendConeType(StringBuilder sb, ConeKotlinType coneKotlinType, Function1<? super FirTypeRef, ? extends ConeKotlinType> function1, Set<FirTypeParameterSymbol> set) {
        String str;
        boolean z = coneKotlinType instanceof ConeClassLikeType;
        Pair pair = null;
        ConeClassLikeType coneClassLikeType = z ? (ConeClassLikeType) coneKotlinType : null;
        if (coneClassLikeType != null) {
            ClassId classId = coneClassLikeType.getLookupTag().getClassId();
            if (Intrinsics.areEqual(classId.getPackageFqName().toString(), IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME) && (str = PRIMITIVE_TYPE_OR_ARRAY_SIGNATURE.get(classId.getShortClassName().getIdentifier())) != null) {
                sb.append(str);
                return;
            }
        }
        if (coneKotlinType instanceof ConeErrorType) {
            return;
        }
        if (z) {
            appendConeType$appendClassLikeType(sb, function1, set, (ConeClassLikeType) coneKotlinType);
            return;
        }
        if (coneKotlinType instanceof ConeTypeParameterType) {
            ConeTypeParameterType coneTypeParameterType = (ConeTypeParameterType) coneKotlinType;
            if (set.add(coneTypeParameterType.getLookupTag().getTypeParameterSymbol())) {
                for (FirTypeRef firTypeRef : ((FirTypeParameter) coneTypeParameterType.getLookupTag().getTypeParameterSymbol().getFir()).getBounds()) {
                    ConeKotlinType coneKotlinTypeInvoke = function1.invoke(firTypeRef);
                    Pair pair2 = coneKotlinTypeInvoke != null ? TuplesKt.to(firTypeRef, coneKotlinTypeInvoke) : null;
                    if (pair2 != null) {
                        pair = pair2;
                        break;
                    }
                }
                if (pair != null) {
                    FirTypeRef firTypeRef2 = (FirTypeRef) pair.component1();
                    ConeKotlinType coneKotlinType2 = (ConeKotlinType) pair.component2();
                    if (!(firTypeRef2 instanceof FirImplicitNullableAnyTypeRef) && !(firTypeRef2 instanceof FirImplicitAnyTypeRef)) {
                        appendConeType(sb, coneKotlinType2, function1, set);
                        return;
                    }
                }
            }
            sb.append(TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR);
            return;
        }
        if (coneKotlinType instanceof ConeDefinitelyNotNullType) {
            appendConeType(sb, ((ConeDefinitelyNotNullType) coneKotlinType).getOriginal(), function1, set);
        } else if (coneKotlinType instanceof ConeFlexibleType) {
            appendConeType(sb, ((ConeFlexibleType) coneKotlinType).getLowerBound(), function1, set);
        }
    }

    private static final void appendConeType$appendClassLikeType(StringBuilder sb, Function1<? super FirTypeRef, ? extends ConeKotlinType> function1, Set<FirTypeParameterSymbol> set, ConeClassLikeType coneClassLikeType) {
        ClassId classId = coneClassLikeType.getLookupTag().getClassId();
        JavaToKotlinClassMap javaToKotlinClassMap = JavaToKotlinClassMap.INSTANCE;
        FqNameUnsafe unsafe = classId.asSingleFqName().toUnsafe();
        Intrinsics.checkNotNullExpressionValue(unsafe, "toUnsafe(...)");
        ClassId classIdMapKotlinToJava = javaToKotlinClassMap.mapKotlinToJava(unsafe);
        if (classIdMapKotlinToJava != null) {
            classId = classIdMapKotlinToJava;
        }
        if (Intrinsics.areEqual(classId, StandardClassIds.INSTANCE.getArray())) {
            sb.append("[");
            for (ConeTypeProjection coneTypeProjection : coneClassLikeType.getTypeArguments()) {
                if (Intrinsics.areEqual(coneTypeProjection, ConeStarProjection.INSTANCE)) {
                    sb.append("*");
                } else if (coneTypeProjection instanceof ConeKotlinTypeProjection) {
                    appendConeType(sb, ((ConeKotlinTypeProjection) coneTypeProjection).getType(), function1, set);
                }
            }
            return;
        }
        sb.append("L");
        String strAsString = classId.getPackageFqName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        sb.append(StringsKt.replace$default(strAsString, ".", "/", false, 4, (Object) null));
        sb.append("/");
        sb.append(classId.getRelativeClassName());
        sb.append(";");
    }

    private static final boolean isVoid(FirTypeRef firTypeRef) {
        if (firTypeRef instanceof FirJavaTypeRef) {
            JavaType type = ((FirJavaTypeRef) firTypeRef).getType();
            if ((type instanceof JavaPrimitiveType) && ((JavaPrimitiveType) type).getType() == null) {
                return true;
            }
        } else if (firTypeRef instanceof FirResolvedTypeRef) {
            ConeKotlinType type2 = ((FirResolvedTypeRef) firTypeRef).getType();
            if ((type2 instanceof ConeClassLikeType) && Intrinsics.areEqual(((ConeClassLikeType) type2).getLookupTag().getClassId(), unitClassId)) {
                return true;
            }
        }
        return false;
    }
}
