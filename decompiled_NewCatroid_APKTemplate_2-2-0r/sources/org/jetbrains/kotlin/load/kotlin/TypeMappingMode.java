package org.jetbrains.kotlin.load.kotlin;

import com.gaurav.avnc.vnc.XKeySym;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.apache.commons.compress.archivers.tar.TarConstants;
import org.jetbrains.kotlin.types.Variance;

/* JADX INFO: compiled from: TypeMappingMode.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0014\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 \u001b2\u00020\u0001:\u0001\u001bBq\b\u0007\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0000\u0012\b\b\u0002\u0010\t\u001a\u00020\u0003\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0000\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0000\u0012\b\b\u0002\u0010\f\u001a\u00020\u0003¢\u0006\u0002\u0010\rJ\u0006\u0010\u0015\u001a\u00020\u0000J\u0012\u0010\f\u001a\u00020\u00002\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0000J\u0018\u0010\u0016\u001a\u00020\u00002\u0006\u0010\u0017\u001a\u00020\u00182\b\b\u0002\u0010\u0019\u001a\u00020\u0003J\u0006\u0010\u001a\u001a\u00020\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\u0000X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u0000X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\u0000X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u000eR\u0011\u0010\t\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000eR\u0011\u0010\f\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000eR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000eR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000eR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000eR\u0011\u0010\u0007\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u000e¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/load/kotlin/TypeMappingMode;", "", "needPrimitiveBoxing", "", "needInlineClassWrapping", "isForAnnotationParameter", "skipDeclarationSiteWildcards", "skipDeclarationSiteWildcardsIfPossible", "genericArgumentMode", "kotlinCollectionsToJavaCollections", "genericContravariantArgumentMode", "genericInvariantArgumentMode", "mapTypeAliases", "(ZZZZZLorg/jetbrains/kotlin/load/kotlin/TypeMappingMode;ZLorg/jetbrains/kotlin/load/kotlin/TypeMappingMode;Lorg/jetbrains/kotlin/load/kotlin/TypeMappingMode;Z)V", "()Z", "getKotlinCollectionsToJavaCollections", "getMapTypeAliases", "getNeedInlineClassWrapping", "getNeedPrimitiveBoxing", "getSkipDeclarationSiteWildcards", "getSkipDeclarationSiteWildcardsIfPossible", "dontWrapInlineClassesMode", "toGenericArgumentMode", "effectiveVariance", "Lorg/jetbrains/kotlin/types/Variance;", "ofArray", "wrapInlineClassesMode", "Companion", "compiler.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TypeMappingMode {
    public static final TypeMappingMode CLASS_DECLARATION;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final TypeMappingMode DEFAULT;
    public static final TypeMappingMode DEFAULT_UAST;
    public static final TypeMappingMode GENERIC_ARGUMENT;
    public static final TypeMappingMode GENERIC_ARGUMENT_UAST;
    public static final TypeMappingMode RETURN_TYPE_BOXED;
    public static final TypeMappingMode SUPER_TYPE;
    public static final TypeMappingMode SUPER_TYPE_KOTLIN_COLLECTIONS_AS_IS;
    public static final TypeMappingMode VALUE_FOR_ANNOTATION;
    private final TypeMappingMode genericArgumentMode;
    private final TypeMappingMode genericContravariantArgumentMode;
    private final TypeMappingMode genericInvariantArgumentMode;
    private final boolean isForAnnotationParameter;
    private final boolean kotlinCollectionsToJavaCollections;
    private final boolean mapTypeAliases;
    private final boolean needInlineClassWrapping;
    private final boolean needPrimitiveBoxing;
    private final boolean skipDeclarationSiteWildcards;
    private final boolean skipDeclarationSiteWildcardsIfPossible;

    /* JADX INFO: compiled from: TypeMappingMode.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Variance.values().length];
            try {
                iArr[Variance.IN_VARIANCE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Variance.INVARIANT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    @TypeMappingModeInternals
    public TypeMappingMode() {
        this(false, false, false, false, false, null, false, null, null, false, 1023, null);
    }

    @JvmStatic
    public static final TypeMappingMode createWithConstantDeclarationSiteWildcardsMode(boolean z, boolean z2, boolean z3, boolean z4, TypeMappingMode typeMappingMode) {
        return INSTANCE.createWithConstantDeclarationSiteWildcardsMode(z, z2, z3, z4, typeMappingMode);
    }

    @JvmStatic
    public static final TypeMappingMode getModeForReturnTypeNoGeneric(boolean z) {
        return INSTANCE.getModeForReturnTypeNoGeneric(z);
    }

    @TypeMappingModeInternals
    public TypeMappingMode(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, TypeMappingMode typeMappingMode, boolean z6, TypeMappingMode typeMappingMode2, TypeMappingMode typeMappingMode3, boolean z7) {
        this.needPrimitiveBoxing = z;
        this.needInlineClassWrapping = z2;
        this.isForAnnotationParameter = z3;
        this.skipDeclarationSiteWildcards = z4;
        this.skipDeclarationSiteWildcardsIfPossible = z5;
        this.genericArgumentMode = typeMappingMode;
        this.kotlinCollectionsToJavaCollections = z6;
        this.genericContravariantArgumentMode = typeMappingMode2;
        this.genericInvariantArgumentMode = typeMappingMode3;
        this.mapTypeAliases = z7;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ TypeMappingMode(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, TypeMappingMode typeMappingMode, boolean z6, TypeMappingMode typeMappingMode2, TypeMappingMode typeMappingMode3, boolean z7, int i, DefaultConstructorMarker defaultConstructorMarker) {
        boolean z8 = (i & 1) != 0 ? true : z;
        boolean z9 = (i & 2) != 0 ? true : z2;
        boolean z10 = (i & 4) != 0 ? false : z3;
        boolean z11 = (i & 8) != 0 ? false : z4;
        boolean z12 = (i & 16) != 0 ? false : z5;
        TypeMappingMode typeMappingMode4 = (i & 32) != 0 ? null : typeMappingMode;
        this(z8, z9, z10, z11, z12, typeMappingMode4, (i & 64) == 0 ? z6 : true, (i & 128) != 0 ? typeMappingMode4 : typeMappingMode2, (i & 256) != 0 ? typeMappingMode4 : typeMappingMode3, (i & 512) == 0 ? z7 : false);
    }

    public final boolean getNeedPrimitiveBoxing() {
        return this.needPrimitiveBoxing;
    }

    public final boolean getNeedInlineClassWrapping() {
        return this.needInlineClassWrapping;
    }

    /* JADX INFO: renamed from: isForAnnotationParameter, reason: from getter */
    public final boolean getIsForAnnotationParameter() {
        return this.isForAnnotationParameter;
    }

    public final boolean getSkipDeclarationSiteWildcards() {
        return this.skipDeclarationSiteWildcards;
    }

    public final boolean getSkipDeclarationSiteWildcardsIfPossible() {
        return this.skipDeclarationSiteWildcardsIfPossible;
    }

    public final boolean getKotlinCollectionsToJavaCollections() {
        return this.kotlinCollectionsToJavaCollections;
    }

    public final boolean getMapTypeAliases() {
        return this.mapTypeAliases;
    }

    /* JADX INFO: compiled from: TypeMappingMode.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J4\u0010\r\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u000f2\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u0004H\u0007J\u0010\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00020\u000fH\u0007R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/load/kotlin/TypeMappingMode$Companion;", "", "()V", "CLASS_DECLARATION", "Lorg/jetbrains/kotlin/load/kotlin/TypeMappingMode;", "DEFAULT", "DEFAULT_UAST", "GENERIC_ARGUMENT", "GENERIC_ARGUMENT_UAST", "RETURN_TYPE_BOXED", "SUPER_TYPE", "SUPER_TYPE_KOTLIN_COLLECTIONS_AS_IS", "VALUE_FOR_ANNOTATION", "createWithConstantDeclarationSiteWildcardsMode", "skipDeclarationSiteWildcards", "", "isForAnnotationParameter", "needInlineClassWrapping", "mapTypeAliases", "fallbackMode", "getModeForReturnTypeNoGeneric", "isAnnotationMethod", "compiler.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final TypeMappingMode getModeForReturnTypeNoGeneric(boolean isAnnotationMethod) {
            return isAnnotationMethod ? TypeMappingMode.VALUE_FOR_ANNOTATION : TypeMappingMode.DEFAULT;
        }

        public static /* synthetic */ TypeMappingMode createWithConstantDeclarationSiteWildcardsMode$default(Companion companion, boolean z, boolean z2, boolean z3, boolean z4, TypeMappingMode typeMappingMode, int i, Object obj) {
            if ((i & 16) != 0) {
                typeMappingMode = null;
            }
            return companion.createWithConstantDeclarationSiteWildcardsMode(z, z2, z3, z4, typeMappingMode);
        }

        @JvmStatic
        public final TypeMappingMode createWithConstantDeclarationSiteWildcardsMode(boolean skipDeclarationSiteWildcards, boolean isForAnnotationParameter, boolean needInlineClassWrapping, boolean mapTypeAliases, TypeMappingMode fallbackMode) {
            return new TypeMappingMode(false, needInlineClassWrapping, isForAnnotationParameter, skipDeclarationSiteWildcards, false, fallbackMode, false, null, null, mapTypeAliases, XKeySym.XK_Nacute, null);
        }
    }

    static {
        DefaultConstructorMarker defaultConstructorMarker = null;
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        boolean z4 = false;
        TypeMappingMode typeMappingMode = null;
        boolean z5 = false;
        TypeMappingMode typeMappingMode2 = null;
        TypeMappingMode typeMappingMode3 = null;
        boolean z6 = false;
        TypeMappingMode typeMappingMode4 = new TypeMappingMode(z, false, z2, z3, z4, typeMappingMode, z5, typeMappingMode2, typeMappingMode3, z6, 1023, defaultConstructorMarker);
        GENERIC_ARGUMENT = typeMappingMode4;
        DefaultConstructorMarker defaultConstructorMarker2 = null;
        boolean z7 = false;
        boolean z8 = false;
        boolean z9 = false;
        boolean z10 = false;
        boolean z11 = false;
        boolean z12 = false;
        TypeMappingMode typeMappingMode5 = null;
        TypeMappingMode typeMappingMode6 = null;
        boolean z13 = true;
        TypeMappingMode typeMappingMode7 = new TypeMappingMode(z7, z8, z9, z10, z11, null, z12, typeMappingMode5, typeMappingMode6, z13, 511, defaultConstructorMarker2);
        GENERIC_ARGUMENT_UAST = typeMappingMode7;
        RETURN_TYPE_BOXED = new TypeMappingMode(z, true, z2, z3, z4, typeMappingMode, z5, typeMappingMode2, typeMappingMode3, z6, 1021, defaultConstructorMarker);
        int i = 988;
        DEFAULT = new TypeMappingMode(z, false, z2, z3, z4, typeMappingMode4, z5, typeMappingMode2, typeMappingMode3, z6, i, defaultConstructorMarker);
        DEFAULT_UAST = new TypeMappingMode(z7, z8, z9, z10, z11, typeMappingMode7, z12, typeMappingMode5, typeMappingMode6, z13, TarConstants.XSTAR_ATIME_OFFSET, defaultConstructorMarker2);
        CLASS_DECLARATION = new TypeMappingMode(z, true, z2, z3, z4, typeMappingMode4, z5, typeMappingMode2, typeMappingMode3, z6, i, defaultConstructorMarker);
        boolean z14 = false;
        boolean z15 = true;
        SUPER_TYPE = new TypeMappingMode(z, z14, z2, z15, z4, typeMappingMode4, z5, typeMappingMode2, typeMappingMode3, z6, 983, defaultConstructorMarker);
        SUPER_TYPE_KOTLIN_COLLECTIONS_AS_IS = new TypeMappingMode(z, z14, z2, z15, z4, typeMappingMode4, z5, typeMappingMode2, typeMappingMode3, z6, 919, defaultConstructorMarker);
        VALUE_FOR_ANNOTATION = new TypeMappingMode(z, z14, true, false, z4, typeMappingMode4, z5, typeMappingMode2, typeMappingMode3, z6, 984, defaultConstructorMarker);
    }

    public static /* synthetic */ TypeMappingMode toGenericArgumentMode$default(TypeMappingMode typeMappingMode, Variance variance, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return typeMappingMode.toGenericArgumentMode(variance, z);
    }

    public final TypeMappingMode toGenericArgumentMode(Variance effectiveVariance, boolean ofArray) {
        Intrinsics.checkNotNullParameter(effectiveVariance, "effectiveVariance");
        if (!ofArray || !this.isForAnnotationParameter) {
            int i = WhenMappings.$EnumSwitchMapping$0[effectiveVariance.ordinal()];
            if (i == 1) {
                TypeMappingMode typeMappingMode = this.genericContravariantArgumentMode;
                if (typeMappingMode != null) {
                    return typeMappingMode;
                }
            } else if (i == 2) {
                TypeMappingMode typeMappingMode2 = this.genericInvariantArgumentMode;
                if (typeMappingMode2 != null) {
                    return typeMappingMode2;
                }
            } else {
                TypeMappingMode typeMappingMode3 = this.genericArgumentMode;
                if (typeMappingMode3 != null) {
                    return typeMappingMode3;
                }
            }
        }
        return this;
    }

    public final TypeMappingMode wrapInlineClassesMode() {
        return new TypeMappingMode(this.needPrimitiveBoxing, true, this.isForAnnotationParameter, this.skipDeclarationSiteWildcards, this.skipDeclarationSiteWildcardsIfPossible, this.genericArgumentMode, this.kotlinCollectionsToJavaCollections, this.genericContravariantArgumentMode, this.genericInvariantArgumentMode, false, 512, null);
    }

    public final TypeMappingMode dontWrapInlineClassesMode() {
        return new TypeMappingMode(this.needPrimitiveBoxing, false, this.isForAnnotationParameter, this.skipDeclarationSiteWildcards, this.skipDeclarationSiteWildcardsIfPossible, this.genericArgumentMode, this.kotlinCollectionsToJavaCollections, this.genericContravariantArgumentMode, this.genericInvariantArgumentMode, false, 512, null);
    }

    public static /* synthetic */ TypeMappingMode mapTypeAliases$default(TypeMappingMode typeMappingMode, TypeMappingMode typeMappingMode2, int i, Object obj) {
        if ((i & 1) != 0) {
            typeMappingMode2 = null;
        }
        return typeMappingMode.mapTypeAliases(typeMappingMode2);
    }

    public final TypeMappingMode mapTypeAliases(TypeMappingMode genericArgumentMode) {
        boolean z = this.needPrimitiveBoxing;
        boolean z2 = this.needInlineClassWrapping;
        boolean z3 = this.isForAnnotationParameter;
        boolean z4 = this.skipDeclarationSiteWildcards;
        boolean z5 = this.skipDeclarationSiteWildcardsIfPossible;
        if (genericArgumentMode == null) {
            genericArgumentMode = this.genericArgumentMode;
        }
        return new TypeMappingMode(z, z2, z3, z4, z5, genericArgumentMode, this.kotlinCollectionsToJavaCollections, this.genericContravariantArgumentMode, this.genericInvariantArgumentMode, true);
    }
}
