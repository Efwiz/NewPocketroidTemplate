package org.jetbrains.kotlin.fir.types;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.ranges.RangesKt;
import kotlin.reflect.KClass;
import org.antlr.stringtemplate.language.ASTExpr;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.cli.common.arguments.PreprocessCommandLineArgumentsKt;
import org.jetbrains.kotlin.incremental.IncrementalJvmCacheKt;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.StandardClassIds;

/* JADX INFO: compiled from: CompilerConeAttributes.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\bÆ\u0002\u0018\u00002\u00020\u0001:\u0007\u000f\u0010\u0011\u0012\u0013\u0014\u0015B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R-\u0010\u0003\u001a\u001e\u0012\u0014\u0012\u0012\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030\u00060\u0005j\u0002`\u0007\u0012\u0004\u0012\u00020\b0\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR*\u0010\u000b\u001a\u001e\u0012\u0004\u0012\u00020\b\u0012\u0014\u0012\u0012\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030\u00060\u0005j\u0002`\u00070\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R-\u0010\f\u001a\u001e\u0012\u0004\u0012\u00020\r\u0012\u0014\u0012\u0012\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030\u00060\u0005j\u0002`\u00070\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\n¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/CompilerConeAttributes;", "", "()V", "classIdByCompilerAttributeKey", "", "Lkotlin/reflect/KClass;", "Lorg/jetbrains/kotlin/fir/types/ConeAttribute;", "Lorg/jetbrains/kotlin/fir/types/ConeAttributeKey;", "Lorg/jetbrains/kotlin/name/ClassId;", "getClassIdByCompilerAttributeKey", "()Ljava/util/Map;", "compilerAttributeByClassId", "compilerAttributeKeyByFqName", "Lorg/jetbrains/kotlin/name/FqName;", "getCompilerAttributeKeyByFqName", "ContextFunctionTypeParams", "EnhancedNullability", "Exact", "ExtensionFunctionType", "NoInfer", "RawType", "UnsafeVariance", "cones"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CompilerConeAttributes {
    public static final CompilerConeAttributes INSTANCE = new CompilerConeAttributes();
    private static final Map<KClass<? extends ConeAttribute<?>>, ClassId> classIdByCompilerAttributeKey;
    private static final Map<ClassId, KClass<? extends ConeAttribute<?>>> compilerAttributeByClassId;
    private static final Map<FqName, KClass<? extends ConeAttribute<?>>> compilerAttributeKeyByFqName;

    /* JADX INFO: compiled from: CompilerConeAttributes.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u000b\u001a\u00020\u00002\b\u0010\f\u001a\u0004\u0018\u00010\u0000H\u0016J\u0014\u0010\r\u001a\u0004\u0018\u00010\u00002\b\u0010\f\u001a\u0004\u0018\u00010\u0000H\u0016J\u0012\u0010\u000e\u001a\u00020\u000f2\b\u0010\f\u001a\u0004\u0018\u00010\u0000H\u0016J\b\u0010\u0010\u001a\u00020\u0011H\u0016J\u0014\u0010\u0012\u001a\u0004\u0018\u00010\u00002\b\u0010\f\u001a\u0004\u0018\u00010\u0000H\u0016R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u001c\u0010\u0007\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00000\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/CompilerConeAttributes$Exact;", "Lorg/jetbrains/kotlin/fir/types/ConeAttribute;", "()V", "ANNOTATION_CLASS_ID", "Lorg/jetbrains/kotlin/name/ClassId;", "getANNOTATION_CLASS_ID", "()Lorg/jetbrains/kotlin/name/ClassId;", ASTExpr.DEFAULT_MAP_KEY_NAME, "Lkotlin/reflect/KClass;", "getKey", "()Lkotlin/reflect/KClass;", ProductAction.ACTION_ADD, "other", "intersect", "isSubtypeOf", "", "toString", "", SchemaSymbols.ATTVAL_UNION, "cones"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Exact extends ConeAttribute<Exact> {
        public static final Exact INSTANCE = new Exact();
        private static final ClassId ANNOTATION_CLASS_ID = new ClassId(new FqName("kotlin.internal"), Name.identifier("Exact"));
        private static final KClass<? extends Exact> key = Reflection.getOrCreateKotlinClass(Exact.class);

        @Override // org.jetbrains.kotlin.fir.types.ConeAttribute
        public Exact add(Exact other) {
            return this;
        }

        @Override // org.jetbrains.kotlin.fir.types.ConeAttribute
        public Exact intersect(Exact other) {
            return null;
        }

        @Override // org.jetbrains.kotlin.fir.types.ConeAttribute
        public boolean isSubtypeOf(Exact other) {
            return true;
        }

        @Override // org.jetbrains.kotlin.fir.types.ConeAttribute
        public Exact union(Exact other) {
            return null;
        }

        private Exact() {
        }

        public final ClassId getANNOTATION_CLASS_ID() {
            return ANNOTATION_CLASS_ID;
        }

        @Override // org.jetbrains.kotlin.fir.types.ConeAttribute
        public KClass<? extends Exact> getKey() {
            return key;
        }

        @Override // org.jetbrains.kotlin.fir.types.ConeAttribute
        public String toString() {
            return "@Exact";
        }
    }

    private CompilerConeAttributes() {
    }

    /* JADX INFO: compiled from: CompilerConeAttributes.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u000b\u001a\u00020\u00002\b\u0010\f\u001a\u0004\u0018\u00010\u0000H\u0016J\u0014\u0010\r\u001a\u0004\u0018\u00010\u00002\b\u0010\f\u001a\u0004\u0018\u00010\u0000H\u0016J\u0012\u0010\u000e\u001a\u00020\u000f2\b\u0010\f\u001a\u0004\u0018\u00010\u0000H\u0016J\b\u0010\u0010\u001a\u00020\u0011H\u0016J\u0014\u0010\u0012\u001a\u0004\u0018\u00010\u00002\b\u0010\f\u001a\u0004\u0018\u00010\u0000H\u0016R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u001c\u0010\u0007\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00000\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/CompilerConeAttributes$NoInfer;", "Lorg/jetbrains/kotlin/fir/types/ConeAttribute;", "()V", "ANNOTATION_CLASS_ID", "Lorg/jetbrains/kotlin/name/ClassId;", "getANNOTATION_CLASS_ID", "()Lorg/jetbrains/kotlin/name/ClassId;", ASTExpr.DEFAULT_MAP_KEY_NAME, "Lkotlin/reflect/KClass;", "getKey", "()Lkotlin/reflect/KClass;", ProductAction.ACTION_ADD, "other", "intersect", "isSubtypeOf", "", "toString", "", SchemaSymbols.ATTVAL_UNION, "cones"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class NoInfer extends ConeAttribute<NoInfer> {
        public static final NoInfer INSTANCE = new NoInfer();
        private static final ClassId ANNOTATION_CLASS_ID = new ClassId(new FqName("kotlin.internal"), Name.identifier("NoInfer"));
        private static final KClass<? extends NoInfer> key = Reflection.getOrCreateKotlinClass(NoInfer.class);

        @Override // org.jetbrains.kotlin.fir.types.ConeAttribute
        public NoInfer add(NoInfer other) {
            return this;
        }

        @Override // org.jetbrains.kotlin.fir.types.ConeAttribute
        public NoInfer intersect(NoInfer other) {
            return null;
        }

        @Override // org.jetbrains.kotlin.fir.types.ConeAttribute
        public boolean isSubtypeOf(NoInfer other) {
            return true;
        }

        @Override // org.jetbrains.kotlin.fir.types.ConeAttribute
        public NoInfer union(NoInfer other) {
            return null;
        }

        private NoInfer() {
        }

        public final ClassId getANNOTATION_CLASS_ID() {
            return ANNOTATION_CLASS_ID;
        }

        @Override // org.jetbrains.kotlin.fir.types.ConeAttribute
        public KClass<? extends NoInfer> getKey() {
            return key;
        }

        @Override // org.jetbrains.kotlin.fir.types.ConeAttribute
        public String toString() {
            return "@NoInfer";
        }
    }

    /* JADX INFO: compiled from: CompilerConeAttributes.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u000b\u001a\u00020\u00002\b\u0010\f\u001a\u0004\u0018\u00010\u0000H\u0016J\u0012\u0010\r\u001a\u00020\u00002\b\u0010\f\u001a\u0004\u0018\u00010\u0000H\u0016J\u0012\u0010\u000e\u001a\u00020\u000f2\b\u0010\f\u001a\u0004\u0018\u00010\u0000H\u0016J\b\u0010\u0010\u001a\u00020\u0011H\u0016J\u0014\u0010\u0012\u001a\u0004\u0018\u00010\u00002\b\u0010\f\u001a\u0004\u0018\u00010\u0000H\u0016R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u001c\u0010\u0007\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00000\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/CompilerConeAttributes$EnhancedNullability;", "Lorg/jetbrains/kotlin/fir/types/ConeAttribute;", "()V", "ANNOTATION_CLASS_ID", "Lorg/jetbrains/kotlin/name/ClassId;", "getANNOTATION_CLASS_ID", "()Lorg/jetbrains/kotlin/name/ClassId;", ASTExpr.DEFAULT_MAP_KEY_NAME, "Lkotlin/reflect/KClass;", "getKey", "()Lkotlin/reflect/KClass;", ProductAction.ACTION_ADD, "other", "intersect", "isSubtypeOf", "", "toString", "", SchemaSymbols.ATTVAL_UNION, "cones"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class EnhancedNullability extends ConeAttribute<EnhancedNullability> {
        public static final EnhancedNullability INSTANCE = new EnhancedNullability();
        private static final ClassId ANNOTATION_CLASS_ID = StandardClassIds.Annotations.INSTANCE.getEnhancedNullability();
        private static final KClass<? extends EnhancedNullability> key = Reflection.getOrCreateKotlinClass(EnhancedNullability.class);

        @Override // org.jetbrains.kotlin.fir.types.ConeAttribute
        public EnhancedNullability add(EnhancedNullability other) {
            return this;
        }

        @Override // org.jetbrains.kotlin.fir.types.ConeAttribute
        public EnhancedNullability intersect(EnhancedNullability other) {
            return this;
        }

        @Override // org.jetbrains.kotlin.fir.types.ConeAttribute
        public boolean isSubtypeOf(EnhancedNullability other) {
            return true;
        }

        @Override // org.jetbrains.kotlin.fir.types.ConeAttribute
        public EnhancedNullability union(EnhancedNullability other) {
            return other;
        }

        private EnhancedNullability() {
        }

        public final ClassId getANNOTATION_CLASS_ID() {
            return ANNOTATION_CLASS_ID;
        }

        @Override // org.jetbrains.kotlin.fir.types.ConeAttribute
        public KClass<? extends EnhancedNullability> getKey() {
            return key;
        }

        @Override // org.jetbrains.kotlin.fir.types.ConeAttribute
        public String toString() {
            return "@EnhancedNullability";
        }
    }

    /* JADX INFO: compiled from: CompilerConeAttributes.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u000b\u001a\u00020\u00002\b\u0010\f\u001a\u0004\u0018\u00010\u0000H\u0016J\u0012\u0010\r\u001a\u00020\u00002\b\u0010\f\u001a\u0004\u0018\u00010\u0000H\u0016J\u0012\u0010\u000e\u001a\u00020\u000f2\b\u0010\f\u001a\u0004\u0018\u00010\u0000H\u0016J\b\u0010\u0010\u001a\u00020\u0011H\u0016J\u0014\u0010\u0012\u001a\u0004\u0018\u00010\u00002\b\u0010\f\u001a\u0004\u0018\u00010\u0000H\u0016R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u001c\u0010\u0007\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00000\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/CompilerConeAttributes$ExtensionFunctionType;", "Lorg/jetbrains/kotlin/fir/types/ConeAttribute;", "()V", "ANNOTATION_CLASS_ID", "Lorg/jetbrains/kotlin/name/ClassId;", "getANNOTATION_CLASS_ID", "()Lorg/jetbrains/kotlin/name/ClassId;", ASTExpr.DEFAULT_MAP_KEY_NAME, "Lkotlin/reflect/KClass;", "getKey", "()Lkotlin/reflect/KClass;", ProductAction.ACTION_ADD, "other", "intersect", "isSubtypeOf", "", "toString", "", SchemaSymbols.ATTVAL_UNION, "cones"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ExtensionFunctionType extends ConeAttribute<ExtensionFunctionType> {
        public static final ExtensionFunctionType INSTANCE = new ExtensionFunctionType();
        private static final ClassId ANNOTATION_CLASS_ID = new ClassId(new FqName(IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME), Name.identifier("ExtensionFunctionType"));
        private static final KClass<? extends ExtensionFunctionType> key = Reflection.getOrCreateKotlinClass(ExtensionFunctionType.class);

        @Override // org.jetbrains.kotlin.fir.types.ConeAttribute
        public ExtensionFunctionType add(ExtensionFunctionType other) {
            return this;
        }

        @Override // org.jetbrains.kotlin.fir.types.ConeAttribute
        public ExtensionFunctionType intersect(ExtensionFunctionType other) {
            return this;
        }

        @Override // org.jetbrains.kotlin.fir.types.ConeAttribute
        public boolean isSubtypeOf(ExtensionFunctionType other) {
            return true;
        }

        @Override // org.jetbrains.kotlin.fir.types.ConeAttribute
        public ExtensionFunctionType union(ExtensionFunctionType other) {
            return other;
        }

        private ExtensionFunctionType() {
        }

        public final ClassId getANNOTATION_CLASS_ID() {
            return ANNOTATION_CLASS_ID;
        }

        @Override // org.jetbrains.kotlin.fir.types.ConeAttribute
        public KClass<? extends ExtensionFunctionType> getKey() {
            return key;
        }

        @Override // org.jetbrains.kotlin.fir.types.ConeAttribute
        public String toString() {
            return "@ExtensionFunctionType";
        }
    }

    /* JADX INFO: compiled from: CompilerConeAttributes.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0007\u001a\u00020\u00002\b\u0010\b\u001a\u0004\u0018\u00010\u0000H\u0016J\u0014\u0010\t\u001a\u0004\u0018\u00010\u00002\b\u0010\b\u001a\u0004\u0018\u00010\u0000H\u0016J\u0012\u0010\n\u001a\u00020\u000b2\b\u0010\b\u001a\u0004\u0018\u00010\u0000H\u0016J\b\u0010\f\u001a\u00020\rH\u0016J\u0014\u0010\u000e\u001a\u0004\u0018\u00010\u00002\b\u0010\b\u001a\u0004\u0018\u00010\u0000H\u0016R\u001c\u0010\u0003\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00000\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/CompilerConeAttributes$RawType;", "Lorg/jetbrains/kotlin/fir/types/ConeAttribute;", "()V", ASTExpr.DEFAULT_MAP_KEY_NAME, "Lkotlin/reflect/KClass;", "getKey", "()Lkotlin/reflect/KClass;", ProductAction.ACTION_ADD, "other", "intersect", "isSubtypeOf", "", "toString", "", SchemaSymbols.ATTVAL_UNION, "cones"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class RawType extends ConeAttribute<RawType> {
        public static final RawType INSTANCE = new RawType();
        private static final KClass<? extends RawType> key = Reflection.getOrCreateKotlinClass(RawType.class);

        @Override // org.jetbrains.kotlin.fir.types.ConeAttribute
        public RawType add(RawType other) {
            return this;
        }

        @Override // org.jetbrains.kotlin.fir.types.ConeAttribute
        public RawType intersect(RawType other) {
            return other;
        }

        @Override // org.jetbrains.kotlin.fir.types.ConeAttribute
        public boolean isSubtypeOf(RawType other) {
            return true;
        }

        @Override // org.jetbrains.kotlin.fir.types.ConeAttribute
        public RawType union(RawType other) {
            return other;
        }

        private RawType() {
        }

        @Override // org.jetbrains.kotlin.fir.types.ConeAttribute
        public KClass<? extends RawType> getKey() {
            return key;
        }

        @Override // org.jetbrains.kotlin.fir.types.ConeAttribute
        public String toString() {
            return "Raw type";
        }
    }

    /* JADX INFO: compiled from: CompilerConeAttributes.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u0000 \u00132\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u0013B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0012\u0010\u000b\u001a\u00020\u00002\b\u0010\f\u001a\u0004\u0018\u00010\u0000H\u0016J\u0012\u0010\r\u001a\u00020\u00002\b\u0010\f\u001a\u0004\u0018\u00010\u0000H\u0016J\u0012\u0010\u000e\u001a\u00020\u000f2\b\u0010\f\u001a\u0004\u0018\u00010\u0000H\u0016J\b\u0010\u0010\u001a\u00020\u0011H\u0016J\u0014\u0010\u0012\u001a\u0004\u0018\u00010\u00002\b\u0010\f\u001a\u0004\u0018\u00010\u0000H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u001c\u0010\u0007\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00000\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/CompilerConeAttributes$ContextFunctionTypeParams;", "Lorg/jetbrains/kotlin/fir/types/ConeAttribute;", "contextReceiverNumber", "", "(I)V", "getContextReceiverNumber", "()I", ASTExpr.DEFAULT_MAP_KEY_NAME, "Lkotlin/reflect/KClass;", "getKey", "()Lkotlin/reflect/KClass;", ProductAction.ACTION_ADD, "other", "intersect", "isSubtypeOf", "", "toString", "", SchemaSymbols.ATTVAL_UNION, "Companion", "cones"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ContextFunctionTypeParams extends ConeAttribute<ContextFunctionTypeParams> {
        private static final ClassId ANNOTATION_CLASS_ID;

        /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
        public static final Companion INSTANCE = new Companion(null);
        private final int contextReceiverNumber;
        private final KClass<? extends ContextFunctionTypeParams> key = Reflection.getOrCreateKotlinClass(ContextFunctionTypeParams.class);

        @Override // org.jetbrains.kotlin.fir.types.ConeAttribute
        public ContextFunctionTypeParams add(ContextFunctionTypeParams other) {
            return this;
        }

        @Override // org.jetbrains.kotlin.fir.types.ConeAttribute
        public ContextFunctionTypeParams intersect(ContextFunctionTypeParams other) {
            return this;
        }

        @Override // org.jetbrains.kotlin.fir.types.ConeAttribute
        public boolean isSubtypeOf(ContextFunctionTypeParams other) {
            return true;
        }

        @Override // org.jetbrains.kotlin.fir.types.ConeAttribute
        public ContextFunctionTypeParams union(ContextFunctionTypeParams other) {
            return other;
        }

        public ContextFunctionTypeParams(int i) {
            this.contextReceiverNumber = i;
        }

        public final int getContextReceiverNumber() {
            return this.contextReceiverNumber;
        }

        @Override // org.jetbrains.kotlin.fir.types.ConeAttribute
        public KClass<? extends ContextFunctionTypeParams> getKey() {
            return this.key;
        }

        @Override // org.jetbrains.kotlin.fir.types.ConeAttribute
        public String toString() {
            return PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT + StandardNames.FqNames.contextFunctionTypeParams.shortName().asString();
        }

        /* JADX INFO: compiled from: CompilerConeAttributes.kt */
        @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/CompilerConeAttributes$ContextFunctionTypeParams$Companion;", "", "()V", "ANNOTATION_CLASS_ID", "Lorg/jetbrains/kotlin/name/ClassId;", "getANNOTATION_CLASS_ID", "()Lorg/jetbrains/kotlin/name/ClassId;", "cones"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }

            public final ClassId getANNOTATION_CLASS_ID() {
                return ContextFunctionTypeParams.ANNOTATION_CLASS_ID;
            }
        }

        static {
            ClassId classId = ClassId.topLevel(StandardNames.FqNames.contextFunctionTypeParams);
            Intrinsics.checkNotNullExpressionValue(classId, "topLevel(...)");
            ANNOTATION_CLASS_ID = classId;
        }
    }

    /* JADX INFO: compiled from: CompilerConeAttributes.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u000b\u001a\u00020\u00002\b\u0010\f\u001a\u0004\u0018\u00010\u0000H\u0016J\u0014\u0010\r\u001a\u0004\u0018\u00010\u00002\b\u0010\f\u001a\u0004\u0018\u00010\u0000H\u0016J\u0012\u0010\u000e\u001a\u00020\u000f2\b\u0010\f\u001a\u0004\u0018\u00010\u0000H\u0016J\b\u0010\u0010\u001a\u00020\u0011H\u0016J\u0014\u0010\u0012\u001a\u0004\u0018\u00010\u00002\b\u0010\f\u001a\u0004\u0018\u00010\u0000H\u0016R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u001c\u0010\u0007\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00000\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/CompilerConeAttributes$UnsafeVariance;", "Lorg/jetbrains/kotlin/fir/types/ConeAttribute;", "()V", "ANNOTATION_CLASS_ID", "Lorg/jetbrains/kotlin/name/ClassId;", "getANNOTATION_CLASS_ID", "()Lorg/jetbrains/kotlin/name/ClassId;", ASTExpr.DEFAULT_MAP_KEY_NAME, "Lkotlin/reflect/KClass;", "getKey", "()Lkotlin/reflect/KClass;", ProductAction.ACTION_ADD, "other", "intersect", "isSubtypeOf", "", "toString", "", SchemaSymbols.ATTVAL_UNION, "cones"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class UnsafeVariance extends ConeAttribute<UnsafeVariance> {
        public static final UnsafeVariance INSTANCE = new UnsafeVariance();
        private static final ClassId ANNOTATION_CLASS_ID = new ClassId(new FqName(IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME), Name.identifier("UnsafeVariance"));
        private static final KClass<? extends UnsafeVariance> key = Reflection.getOrCreateKotlinClass(UnsafeVariance.class);

        @Override // org.jetbrains.kotlin.fir.types.ConeAttribute
        public UnsafeVariance add(UnsafeVariance other) {
            return this;
        }

        @Override // org.jetbrains.kotlin.fir.types.ConeAttribute
        public UnsafeVariance intersect(UnsafeVariance other) {
            return null;
        }

        @Override // org.jetbrains.kotlin.fir.types.ConeAttribute
        public boolean isSubtypeOf(UnsafeVariance other) {
            return true;
        }

        @Override // org.jetbrains.kotlin.fir.types.ConeAttribute
        public UnsafeVariance union(UnsafeVariance other) {
            return null;
        }

        private UnsafeVariance() {
        }

        public final ClassId getANNOTATION_CLASS_ID() {
            return ANNOTATION_CLASS_ID;
        }

        @Override // org.jetbrains.kotlin.fir.types.ConeAttribute
        public KClass<? extends UnsafeVariance> getKey() {
            return key;
        }

        @Override // org.jetbrains.kotlin.fir.types.ConeAttribute
        public String toString() {
            return "@UnsafeVariance";
        }
    }

    static {
        Map<ClassId, KClass<? extends ConeAttribute<?>>> mapMapOf = MapsKt.mapOf(TuplesKt.to(Exact.INSTANCE.getANNOTATION_CLASS_ID(), Exact.INSTANCE.getKey()), TuplesKt.to(NoInfer.INSTANCE.getANNOTATION_CLASS_ID(), NoInfer.INSTANCE.getKey()), TuplesKt.to(EnhancedNullability.INSTANCE.getANNOTATION_CLASS_ID(), EnhancedNullability.INSTANCE.getKey()), TuplesKt.to(ExtensionFunctionType.INSTANCE.getANNOTATION_CLASS_ID(), ExtensionFunctionType.INSTANCE.getKey()), TuplesKt.to(UnsafeVariance.INSTANCE.getANNOTATION_CLASS_ID(), UnsafeVariance.INSTANCE.getKey()));
        compilerAttributeByClassId = mapMapOf;
        Set<Map.Entry<ClassId, KClass<? extends ConeAttribute<?>>>> setEntrySet = mapMapOf.entrySet();
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(setEntrySet, 10)), 16));
        Iterator<T> it = setEntrySet.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            linkedHashMap.put((KClass) entry.getValue(), (ClassId) entry.getKey());
        }
        classIdByCompilerAttributeKey = linkedHashMap;
        Map<ClassId, KClass<? extends ConeAttribute<?>>> map = compilerAttributeByClassId;
        LinkedHashMap linkedHashMap2 = new LinkedHashMap(MapsKt.mapCapacity(map.size()));
        Iterator<T> it2 = map.entrySet().iterator();
        while (it2.hasNext()) {
            Map.Entry entry2 = (Map.Entry) it2.next();
            FqName fqNameAsSingleFqName = ((ClassId) entry2.getKey()).asSingleFqName();
            Intrinsics.checkNotNullExpressionValue(fqNameAsSingleFqName, "asSingleFqName(...)");
            linkedHashMap2.put(fqNameAsSingleFqName, entry2.getValue());
        }
        compilerAttributeKeyByFqName = linkedHashMap2;
    }

    public final Map<KClass<? extends ConeAttribute<?>>, ClassId> getClassIdByCompilerAttributeKey() {
        return classIdByCompilerAttributeKey;
    }

    public final Map<FqName, KClass<? extends ConeAttribute<?>>> getCompilerAttributeKeyByFqName() {
        return compilerAttributeKeyByFqName;
    }
}
