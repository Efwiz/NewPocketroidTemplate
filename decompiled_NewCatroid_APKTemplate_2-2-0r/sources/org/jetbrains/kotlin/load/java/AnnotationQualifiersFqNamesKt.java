package org.jetbrains.kotlin.load.java;

import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import org.jetbrains.kotlin.load.java.typeEnhancement.NullabilityQualifier;
import org.jetbrains.kotlin.load.java.typeEnhancement.NullabilityQualifierWithMigrationStatus;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: AnnotationQualifiersFqNames.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000&\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u000e\"\u001d\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0005\"\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t\"\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u001d\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0005\"\u0011\u0010\u0011\u001a\u00020\u0002¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0011\u0010\u0014\u001a\u00020\u0002¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0013\"\u0011\u0010\u0016\u001a\u00020\u0002¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0013\"\u0011\u0010\u0018\u001a\u00020\u0002¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0013¨\u0006\u001a"}, d2 = {"BUILT_IN_TYPE_QUALIFIER_DEFAULT_ANNOTATIONS", "", "Lorg/jetbrains/kotlin/name/FqName;", "Lorg/jetbrains/kotlin/load/java/JavaDefaultQualifiers;", "getBUILT_IN_TYPE_QUALIFIER_DEFAULT_ANNOTATIONS", "()Ljava/util/Map;", "BUILT_IN_TYPE_QUALIFIER_FQ_NAMES", "", "getBUILT_IN_TYPE_QUALIFIER_FQ_NAMES", "()Ljava/util/Set;", "DEFAULT_JSPECIFY_APPLICABILITY", "", "Lorg/jetbrains/kotlin/load/java/AnnotationQualifierApplicabilityType;", "getDEFAULT_JSPECIFY_APPLICABILITY", "()Ljava/util/List;", "JSPECIFY_DEFAULT_ANNOTATIONS", "getJSPECIFY_DEFAULT_ANNOTATIONS", "MIGRATION_ANNOTATION_FQNAME", "getMIGRATION_ANNOTATION_FQNAME", "()Lorg/jetbrains/kotlin/name/FqName;", "TYPE_QUALIFIER_DEFAULT_FQNAME", "getTYPE_QUALIFIER_DEFAULT_FQNAME", "TYPE_QUALIFIER_FQNAME", "getTYPE_QUALIFIER_FQNAME", "TYPE_QUALIFIER_NICKNAME_FQNAME", "getTYPE_QUALIFIER_NICKNAME_FQNAME", "compiler.common.jvm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class AnnotationQualifiersFqNamesKt {
    private static final Map<FqName, JavaDefaultQualifiers> BUILT_IN_TYPE_QUALIFIER_DEFAULT_ANNOTATIONS;
    private static final Set<FqName> BUILT_IN_TYPE_QUALIFIER_FQ_NAMES;
    private static final List<AnnotationQualifierApplicabilityType> DEFAULT_JSPECIFY_APPLICABILITY;
    private static final Map<FqName, JavaDefaultQualifiers> JSPECIFY_DEFAULT_ANNOTATIONS;
    private static final FqName TYPE_QUALIFIER_NICKNAME_FQNAME = new FqName("javax.annotation.meta.TypeQualifierNickname");
    private static final FqName TYPE_QUALIFIER_FQNAME = new FqName("javax.annotation.meta.TypeQualifier");
    private static final FqName TYPE_QUALIFIER_DEFAULT_FQNAME = new FqName("javax.annotation.meta.TypeQualifierDefault");
    private static final FqName MIGRATION_ANNOTATION_FQNAME = new FqName("kotlin.annotations.jvm.UnderMigration");

    static {
        List<AnnotationQualifierApplicabilityType> listListOf = CollectionsKt.listOf((Object[]) new AnnotationQualifierApplicabilityType[]{AnnotationQualifierApplicabilityType.FIELD, AnnotationQualifierApplicabilityType.METHOD_RETURN_TYPE, AnnotationQualifierApplicabilityType.VALUE_PARAMETER, AnnotationQualifierApplicabilityType.TYPE_PARAMETER_BOUNDS, AnnotationQualifierApplicabilityType.TYPE_USE});
        DEFAULT_JSPECIFY_APPLICABILITY = listListOf;
        Map<FqName, JavaDefaultQualifiers> mapMapOf = MapsKt.mapOf(TuplesKt.to(JvmAnnotationNamesKt.getJSPECIFY_OLD_NULL_MARKED(), new JavaDefaultQualifiers(new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NOT_NULL, false, 2, null), listListOf, false)), TuplesKt.to(JvmAnnotationNamesKt.getJSPECIFY_NULL_MARKED(), new JavaDefaultQualifiers(new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NOT_NULL, false, 2, null), listListOf, false)));
        JSPECIFY_DEFAULT_ANNOTATIONS = mapMapOf;
        BUILT_IN_TYPE_QUALIFIER_DEFAULT_ANNOTATIONS = MapsKt.plus(MapsKt.mapOf(TuplesKt.to(new FqName("javax.annotation.ParametersAreNullableByDefault"), new JavaDefaultQualifiers(new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NULLABLE, false, 2, null), CollectionsKt.listOf(AnnotationQualifierApplicabilityType.VALUE_PARAMETER), false, 4, null)), TuplesKt.to(new FqName("javax.annotation.ParametersAreNonnullByDefault"), new JavaDefaultQualifiers(new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NOT_NULL, false, 2, null), CollectionsKt.listOf(AnnotationQualifierApplicabilityType.VALUE_PARAMETER), false, 4, null))), mapMapOf);
        BUILT_IN_TYPE_QUALIFIER_FQ_NAMES = SetsKt.setOf((Object[]) new FqName[]{JvmAnnotationNamesKt.getJAVAX_NONNULL_ANNOTATION(), JvmAnnotationNamesKt.getJAVAX_CHECKFORNULL_ANNOTATION()});
    }

    public static final FqName getTYPE_QUALIFIER_NICKNAME_FQNAME() {
        return TYPE_QUALIFIER_NICKNAME_FQNAME;
    }

    public static final FqName getTYPE_QUALIFIER_FQNAME() {
        return TYPE_QUALIFIER_FQNAME;
    }

    public static final FqName getTYPE_QUALIFIER_DEFAULT_FQNAME() {
        return TYPE_QUALIFIER_DEFAULT_FQNAME;
    }

    public static final FqName getMIGRATION_ANNOTATION_FQNAME() {
        return MIGRATION_ANNOTATION_FQNAME;
    }

    public static final List<AnnotationQualifierApplicabilityType> getDEFAULT_JSPECIFY_APPLICABILITY() {
        return DEFAULT_JSPECIFY_APPLICABILITY;
    }

    public static final Map<FqName, JavaDefaultQualifiers> getJSPECIFY_DEFAULT_ANNOTATIONS() {
        return JSPECIFY_DEFAULT_ANNOTATIONS;
    }

    public static final Map<FqName, JavaDefaultQualifiers> getBUILT_IN_TYPE_QUALIFIER_DEFAULT_ANNOTATIONS() {
        return BUILT_IN_TYPE_QUALIFIER_DEFAULT_ANNOTATIONS;
    }

    public static final Set<FqName> getBUILT_IN_TYPE_QUALIFIER_FQ_NAMES() {
        return BUILT_IN_TYPE_QUALIFIER_FQ_NAMES;
    }
}
