package org.jetbrains.kotlin.load.java;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.load.java.typeEnhancement.MutabilityQualifier;
import org.jetbrains.kotlin.load.java.typeEnhancement.NullabilityQualifierWithMigrationStatus;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: AbstractAnnotationTypeQualifierResolver.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000z\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u001c\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\b\b&\u0018\u0000 >*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0002:\u0001>B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J \u0010\u0013\u001a\u0004\u0018\u00010\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u00142\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00028\u00000\u0010J\u0017\u0010\u0017\u001a\u0004\u0018\u00010\u00182\u0006\u0010\u0019\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010\u001aJ\u0016\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00028\u00000\u0010J0\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\u0006\u0010\u0019\u001a\u00028\u00002\u0017\u0010\u001f\u001a\u0013\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020!0 ¢\u0006\u0002\b\"H\u0002¢\u0006\u0002\u0010#J1\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00028\u00000\u00102\u0019\b\u0002\u0010\u001f\u001a\u0013\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020!0 ¢\u0006\u0002\b\"J\u0013\u0010$\u001a\u00020!2\u0006\u0010\u0019\u001a\u00028\u0000¢\u0006\u0002\u0010%J\u001f\u0010&\u001a\u0004\u0018\u00010\u001e2\u0006\u0010\u0019\u001a\u00028\u00002\u0006\u0010\u001f\u001a\u00020!H\u0002¢\u0006\u0002\u0010'J\u0015\u0010(\u001a\u00020)2\u0006\u0010\u0019\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010*J\u0015\u0010+\u001a\u00020)2\u0006\u0010\u0019\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010*J\u0017\u0010,\u001a\u0004\u0018\u00010)2\u0006\u0010\u0019\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010*J\u0017\u0010-\u001a\u0004\u0018\u00010\u00182\u0006\u0010\u0019\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010\u001aJ\u0015\u0010.\u001a\u0004\u0018\u00018\u00002\u0006\u0010\u0019\u001a\u00028\u0000¢\u0006\u0002\u0010\u000eJ5\u0010/\u001a\"\u0012\u0004\u0012\u00028\u0000\u0012\n\u0012\b\u0012\u0004\u0012\u00020201\u0018\u000100j\n\u0012\u0004\u0012\u00028\u0000\u0018\u0001`32\u0006\u0010\u0019\u001a\u00028\u0000H\u0002¢\u0006\u0002\u00104J\u0018\u00105\u001a\b\u0012\u0004\u0012\u00020201*\b\u0012\u0004\u0012\u00020201H\u0002J\u001f\u00106\u001a\b\u0012\u0004\u0012\u0002070\u0010*\u00028\u00002\u0006\u00108\u001a\u00020!H$¢\u0006\u0002\u00109J\u001b\u0010:\u001a\u0004\u0018\u00018\u0000*\u00028\u00002\u0006\u0010\b\u001a\u00020\tH\u0002¢\u0006\u0002\u0010;J\u0019\u0010<\u001a\u00020!*\u00028\u00002\u0006\u0010\b\u001a\u00020\tH\u0002¢\u0006\u0002\u0010=R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00028\u00000\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\b\u001a\u0004\u0018\u00010\t*\u00028\u0000X¤\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0016\u0010\f\u001a\u00020\u0002*\u00028\u0000X¤\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00000\u0010*\u00028\u0000X¤\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012¨\u0006?"}, d2 = {"Lorg/jetbrains/kotlin/load/java/AbstractAnnotationTypeQualifierResolver;", "TAnnotation", "", "javaTypeEnhancementState", "Lorg/jetbrains/kotlin/load/java/JavaTypeEnhancementState;", "(Lorg/jetbrains/kotlin/load/java/JavaTypeEnhancementState;)V", "resolvedNicknames", "Ljava/util/concurrent/ConcurrentHashMap;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "getFqName", "(Ljava/lang/Object;)Lorg/jetbrains/kotlin/name/FqName;", ASTExpr.DEFAULT_MAP_KEY_NAME, "getKey", "(Ljava/lang/Object;)Ljava/lang/Object;", "metaAnnotations", "", "getMetaAnnotations", "(Ljava/lang/Object;)Ljava/lang/Iterable;", "extractAndMergeDefaultQualifiers", "Lorg/jetbrains/kotlin/load/java/JavaTypeQualifiersByElementType;", "oldQualifiers", "annotations", "extractDefaultQualifiers", "Lorg/jetbrains/kotlin/load/java/JavaDefaultQualifiers;", "annotation", "(Ljava/lang/Object;)Lorg/jetbrains/kotlin/load/java/JavaDefaultQualifiers;", "extractMutability", "Lorg/jetbrains/kotlin/load/java/typeEnhancement/MutabilityQualifier;", "extractNullability", "Lorg/jetbrains/kotlin/load/java/typeEnhancement/NullabilityQualifierWithMigrationStatus;", "forceWarning", "Lkotlin/Function1;", "", "Lkotlin/ExtensionFunctionType;", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Lorg/jetbrains/kotlin/load/java/typeEnhancement/NullabilityQualifierWithMigrationStatus;", "isTypeUseAnnotation", "(Ljava/lang/Object;)Z", "knownNullability", "(Ljava/lang/Object;Z)Lorg/jetbrains/kotlin/load/java/typeEnhancement/NullabilityQualifierWithMigrationStatus;", "resolveDefaultAnnotationState", "Lorg/jetbrains/kotlin/load/java/ReportLevel;", "(Ljava/lang/Object;)Lorg/jetbrains/kotlin/load/java/ReportLevel;", "resolveJsr305AnnotationState", "resolveJsr305CustomState", "resolveQualifierBuiltInDefaultAnnotation", "resolveTypeQualifierAnnotation", "resolveTypeQualifierDefaultAnnotation", "Lkotlin/Pair;", "", "Lorg/jetbrains/kotlin/load/java/AnnotationQualifierApplicabilityType;", "Lorg/jetbrains/kotlin/load/java/TypeQualifierWithApplicability;", "(Ljava/lang/Object;)Lkotlin/Pair;", "allIfTypeUse", "enumArguments", "", "onlyValue", "(Ljava/lang/Object;Z)Ljava/lang/Iterable;", "findAnnotation", "(Ljava/lang/Object;Lorg/jetbrains/kotlin/name/FqName;)Ljava/lang/Object;", "hasAnnotation", "(Ljava/lang/Object;Lorg/jetbrains/kotlin/name/FqName;)Z", "Companion", "compiler.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractAnnotationTypeQualifierResolver<TAnnotation> {
    private static final Companion Companion = new Companion(null);
    private static final Map<String, AnnotationQualifierApplicabilityType> JAVA_APPLICABILITY_TYPES;
    private final JavaTypeEnhancementState javaTypeEnhancementState;
    private final ConcurrentHashMap<Object, TAnnotation> resolvedNicknames;

    protected abstract Iterable<String> enumArguments(TAnnotation tannotation, boolean z);

    protected abstract FqName getFqName(TAnnotation tannotation);

    protected abstract Object getKey(TAnnotation tannotation);

    protected abstract Iterable<TAnnotation> getMetaAnnotations(TAnnotation tannotation);

    public AbstractAnnotationTypeQualifierResolver(JavaTypeEnhancementState javaTypeEnhancementState) {
        Intrinsics.checkNotNullParameter(javaTypeEnhancementState, "javaTypeEnhancementState");
        this.javaTypeEnhancementState = javaTypeEnhancementState;
        this.resolvedNicknames = new ConcurrentHashMap<>();
    }

    private final TAnnotation findAnnotation(TAnnotation tannotation, FqName fqName) {
        for (TAnnotation tannotation2 : getMetaAnnotations(tannotation)) {
            if (Intrinsics.areEqual(getFqName(tannotation2), fqName)) {
                return tannotation2;
            }
        }
        return null;
    }

    private final boolean hasAnnotation(TAnnotation tannotation, FqName fqName) {
        Iterable<TAnnotation> metaAnnotations = getMetaAnnotations(tannotation);
        if ((metaAnnotations instanceof Collection) && ((Collection) metaAnnotations).isEmpty()) {
            return false;
        }
        Iterator<TAnnotation> it = metaAnnotations.iterator();
        while (it.hasNext()) {
            if (Intrinsics.areEqual(getFqName(it.next()), fqName)) {
                return true;
            }
        }
        return false;
    }

    public final TAnnotation resolveTypeQualifierAnnotation(TAnnotation annotation) {
        TAnnotation tannotationResolveTypeQualifierAnnotation;
        Intrinsics.checkNotNullParameter(annotation, "annotation");
        if (this.javaTypeEnhancementState.getJsr305().getIsDisabled()) {
            return null;
        }
        if (CollectionsKt.contains(AnnotationQualifiersFqNamesKt.getBUILT_IN_TYPE_QUALIFIER_FQ_NAMES(), getFqName(annotation)) || hasAnnotation(annotation, AnnotationQualifiersFqNamesKt.getTYPE_QUALIFIER_FQNAME())) {
            return annotation;
        }
        if (!hasAnnotation(annotation, AnnotationQualifiersFqNamesKt.getTYPE_QUALIFIER_NICKNAME_FQNAME())) {
            return null;
        }
        ConcurrentHashMap<Object, TAnnotation> concurrentHashMap = this.resolvedNicknames;
        Object key = getKey(annotation);
        TAnnotation tannotation = concurrentHashMap.get(key);
        if (tannotation != null) {
            return tannotation;
        }
        Iterator<TAnnotation> it = getMetaAnnotations(annotation).iterator();
        do {
            if (!it.hasNext()) {
                tannotationResolveTypeQualifierAnnotation = null;
                break;
            }
            tannotationResolveTypeQualifierAnnotation = resolveTypeQualifierAnnotation(it.next());
        } while (tannotationResolveTypeQualifierAnnotation == null);
        if (tannotationResolveTypeQualifierAnnotation == null) {
            return null;
        }
        TAnnotation tannotationPutIfAbsent = concurrentHashMap.putIfAbsent(key, tannotationResolveTypeQualifierAnnotation);
        return tannotationPutIfAbsent == null ? tannotationResolveTypeQualifierAnnotation : tannotationPutIfAbsent;
    }

    private final JavaDefaultQualifiers resolveQualifierBuiltInDefaultAnnotation(TAnnotation annotation) {
        JavaDefaultQualifiers javaDefaultQualifiers;
        if (this.javaTypeEnhancementState.getDisabledDefaultAnnotations() || (javaDefaultQualifiers = AnnotationQualifiersFqNamesKt.getBUILT_IN_TYPE_QUALIFIER_DEFAULT_ANNOTATIONS().get(getFqName(annotation))) == null) {
            return null;
        }
        ReportLevel reportLevelResolveDefaultAnnotationState = resolveDefaultAnnotationState(annotation);
        if (reportLevelResolveDefaultAnnotationState == ReportLevel.IGNORE) {
            reportLevelResolveDefaultAnnotationState = null;
        }
        if (reportLevelResolveDefaultAnnotationState == null) {
            return null;
        }
        return JavaDefaultQualifiers.copy$default(javaDefaultQualifiers, NullabilityQualifierWithMigrationStatus.copy$default(javaDefaultQualifiers.getNullabilityQualifier(), null, reportLevelResolveDefaultAnnotationState.isWarning(), 1, null), null, false, 6, null);
    }

    private final ReportLevel resolveDefaultAnnotationState(TAnnotation annotation) {
        FqName fqName = getFqName(annotation);
        if (fqName != null && AnnotationQualifiersFqNamesKt.getJSPECIFY_DEFAULT_ANNOTATIONS().containsKey(fqName)) {
            return this.javaTypeEnhancementState.getGetReportLevelForAnnotation().invoke(fqName);
        }
        return resolveJsr305AnnotationState(annotation);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final Set<AnnotationQualifierApplicabilityType> allIfTypeUse(Set<? extends AnnotationQualifierApplicabilityType> set) {
        return set.contains(AnnotationQualifierApplicabilityType.TYPE_USE) ? SetsKt.plus(SetsKt.minus((Set<? extends AnnotationQualifierApplicabilityType>) ArraysKt.toSet(AnnotationQualifierApplicabilityType.values()), AnnotationQualifierApplicabilityType.TYPE_PARAMETER_BOUNDS), (Iterable) set) : set;
    }

    private final Pair<TAnnotation, Set<AnnotationQualifierApplicabilityType>> resolveTypeQualifierDefaultAnnotation(TAnnotation annotation) {
        TAnnotation tannotationFindAnnotation;
        TAnnotation next;
        if (this.javaTypeEnhancementState.getJsr305().getIsDisabled() || (tannotationFindAnnotation = findAnnotation(annotation, AnnotationQualifiersFqNamesKt.getTYPE_QUALIFIER_DEFAULT_FQNAME())) == null) {
            return null;
        }
        Iterator<TAnnotation> it = getMetaAnnotations(annotation).iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (resolveTypeQualifierAnnotation(next) == null);
        if (next == null) {
            return null;
        }
        Iterable<String> iterableEnumArguments = enumArguments(tannotationFindAnnotation, true);
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator<String> it2 = iterableEnumArguments.iterator();
        while (it2.hasNext()) {
            AnnotationQualifierApplicabilityType annotationQualifierApplicabilityType = JAVA_APPLICABILITY_TYPES.get(it2.next());
            if (annotationQualifierApplicabilityType != null) {
                linkedHashSet.add(annotationQualifierApplicabilityType);
            }
        }
        return new Pair<>(next, allIfTypeUse(linkedHashSet));
    }

    public final boolean isTypeUseAnnotation(TAnnotation annotation) {
        Intrinsics.checkNotNullParameter(annotation, "annotation");
        TAnnotation tannotationFindAnnotation = findAnnotation(annotation, StandardNames.FqNames.target);
        if (tannotationFindAnnotation == null) {
            return false;
        }
        Iterable<String> iterableEnumArguments = enumArguments(tannotationFindAnnotation, false);
        if ((iterableEnumArguments instanceof Collection) && ((Collection) iterableEnumArguments).isEmpty()) {
            return false;
        }
        Iterator<String> it = iterableEnumArguments.iterator();
        while (it.hasNext()) {
            if (Intrinsics.areEqual(it.next(), "TYPE")) {
                return true;
            }
        }
        return false;
    }

    private final ReportLevel resolveJsr305AnnotationState(TAnnotation annotation) {
        ReportLevel reportLevelResolveJsr305CustomState = resolveJsr305CustomState(annotation);
        return reportLevelResolveJsr305CustomState != null ? reportLevelResolveJsr305CustomState : this.javaTypeEnhancementState.getJsr305().getGlobalLevel();
    }

    private final ReportLevel resolveJsr305CustomState(TAnnotation annotation) {
        Iterable<String> iterableEnumArguments;
        String str;
        ReportLevel reportLevel = this.javaTypeEnhancementState.getJsr305().getUserDefinedLevelForSpecificAnnotation().get(getFqName(annotation));
        if (reportLevel != null) {
            return reportLevel;
        }
        TAnnotation tannotationFindAnnotation = findAnnotation(annotation, AnnotationQualifiersFqNamesKt.getMIGRATION_ANNOTATION_FQNAME());
        if (tannotationFindAnnotation == null || (iterableEnumArguments = enumArguments(tannotationFindAnnotation, false)) == null || (str = (String) CollectionsKt.firstOrNull(iterableEnumArguments)) == null) {
            return null;
        }
        ReportLevel migrationLevel = this.javaTypeEnhancementState.getJsr305().getMigrationLevel();
        if (migrationLevel != null) {
            return migrationLevel;
        }
        int iHashCode = str.hashCode();
        if (iHashCode == -2137067054) {
            if (str.equals("IGNORE")) {
                return ReportLevel.IGNORE;
            }
            return null;
        }
        if (iHashCode == -1838656823) {
            if (str.equals("STRICT")) {
                return ReportLevel.STRICT;
            }
            return null;
        }
        if (iHashCode == 2656902 && str.equals("WARN")) {
            return ReportLevel.WARN;
        }
        return null;
    }

    private final NullabilityQualifierWithMigrationStatus extractNullability(TAnnotation annotation, Function1<? super TAnnotation, Boolean> forceWarning) {
        NullabilityQualifierWithMigrationStatus nullabilityQualifierWithMigrationStatusKnownNullability;
        NullabilityQualifierWithMigrationStatus nullabilityQualifierWithMigrationStatusKnownNullability2 = knownNullability(annotation, forceWarning.invoke(annotation).booleanValue());
        if (nullabilityQualifierWithMigrationStatusKnownNullability2 != null) {
            return nullabilityQualifierWithMigrationStatusKnownNullability2;
        }
        TAnnotation tannotationResolveTypeQualifierAnnotation = resolveTypeQualifierAnnotation(annotation);
        if (tannotationResolveTypeQualifierAnnotation == null) {
            return null;
        }
        ReportLevel reportLevelResolveJsr305AnnotationState = resolveJsr305AnnotationState(annotation);
        if (reportLevelResolveJsr305AnnotationState.isIgnore() || (nullabilityQualifierWithMigrationStatusKnownNullability = knownNullability(tannotationResolveTypeQualifierAnnotation, forceWarning.invoke(tannotationResolveTypeQualifierAnnotation).booleanValue())) == null) {
            return null;
        }
        return NullabilityQualifierWithMigrationStatus.copy$default(nullabilityQualifierWithMigrationStatusKnownNullability, null, reportLevelResolveJsr305AnnotationState.isWarning(), 1, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ NullabilityQualifierWithMigrationStatus extractNullability$default(AbstractAnnotationTypeQualifierResolver abstractAnnotationTypeQualifierResolver, Iterable iterable, Function1 function1, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: extractNullability");
        }
        if ((i & 2) != 0) {
            function1 = new Function1<TAnnotation, Boolean>() { // from class: org.jetbrains.kotlin.load.java.AbstractAnnotationTypeQualifierResolver.extractNullability.2
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(TAnnotation tannotation) {
                    Intrinsics.checkNotNullParameter(tannotation, "$this$null");
                    return false;
                }
            };
        }
        return abstractAnnotationTypeQualifierResolver.extractNullability(iterable, function1);
    }

    private final JavaDefaultQualifiers extractDefaultQualifiers(TAnnotation annotation) {
        NullabilityQualifierWithMigrationStatus nullabilityQualifierWithMigrationStatusExtractNullability;
        JavaDefaultQualifiers javaDefaultQualifiersResolveQualifierBuiltInDefaultAnnotation = resolveQualifierBuiltInDefaultAnnotation(annotation);
        if (javaDefaultQualifiersResolveQualifierBuiltInDefaultAnnotation != null) {
            return javaDefaultQualifiersResolveQualifierBuiltInDefaultAnnotation;
        }
        Pair<TAnnotation, Set<AnnotationQualifierApplicabilityType>> pairResolveTypeQualifierDefaultAnnotation = resolveTypeQualifierDefaultAnnotation(annotation);
        if (pairResolveTypeQualifierDefaultAnnotation == null) {
            return null;
        }
        TAnnotation tannotationComponent1 = pairResolveTypeQualifierDefaultAnnotation.component1();
        Set<AnnotationQualifierApplicabilityType> setComponent2 = pairResolveTypeQualifierDefaultAnnotation.component2();
        ReportLevel reportLevelResolveJsr305CustomState = resolveJsr305CustomState(annotation);
        if (reportLevelResolveJsr305CustomState == null) {
            reportLevelResolveJsr305CustomState = resolveJsr305AnnotationState(tannotationComponent1);
        }
        if (reportLevelResolveJsr305CustomState.isIgnore() || (nullabilityQualifierWithMigrationStatusExtractNullability = extractNullability(tannotationComponent1, new Function1<TAnnotation, Boolean>() { // from class: org.jetbrains.kotlin.load.java.AbstractAnnotationTypeQualifierResolver$extractDefaultQualifiers$nullabilityQualifier$1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(TAnnotation extractNullability) {
                Intrinsics.checkNotNullParameter(extractNullability, "$this$extractNullability");
                return false;
            }
        })) == null) {
            return null;
        }
        return new JavaDefaultQualifiers(NullabilityQualifierWithMigrationStatus.copy$default(nullabilityQualifierWithMigrationStatusExtractNullability, null, reportLevelResolveJsr305CustomState.isWarning(), 1, null), setComponent2, false, 4, null);
    }

    public final JavaTypeQualifiersByElementType extractAndMergeDefaultQualifiers(JavaTypeQualifiersByElementType oldQualifiers, Iterable<? extends TAnnotation> annotations) {
        EnumMap<AnnotationQualifierApplicabilityType, JavaDefaultQualifiers> defaultQualifiers;
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        if (this.javaTypeEnhancementState.getDisabledDefaultAnnotations()) {
            return oldQualifiers;
        }
        ArrayList arrayList = new ArrayList();
        Iterator<? extends TAnnotation> it = annotations.iterator();
        while (it.hasNext()) {
            JavaDefaultQualifiers javaDefaultQualifiersExtractDefaultQualifiers = extractDefaultQualifiers(it.next());
            if (javaDefaultQualifiersExtractDefaultQualifiers != null) {
                arrayList.add(javaDefaultQualifiersExtractDefaultQualifiers);
            }
        }
        ArrayList<JavaDefaultQualifiers> arrayList2 = arrayList;
        if (arrayList2.isEmpty()) {
            return oldQualifiers;
        }
        EnumMap enumMap = (oldQualifiers == null || (defaultQualifiers = oldQualifiers.getDefaultQualifiers()) == null) ? new EnumMap(AnnotationQualifierApplicabilityType.class) : new EnumMap((EnumMap) defaultQualifiers);
        boolean z = false;
        for (JavaDefaultQualifiers javaDefaultQualifiers : arrayList2) {
            Iterator<AnnotationQualifierApplicabilityType> it2 = javaDefaultQualifiers.getQualifierApplicabilityTypes().iterator();
            while (it2.hasNext()) {
                enumMap.put(it2.next(), javaDefaultQualifiers);
                z = true;
            }
        }
        return !z ? oldQualifiers : new JavaTypeQualifiersByElementType(enumMap);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0091, code lost:
    
        if (r7.equals("ALWAYS") != false) goto L48;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00a6, code lost:
    
        if (r7.equals("NEVER") == false) goto L47;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00af, code lost:
    
        if (r7.equals("MAYBE") == false) goto L47;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00b2, code lost:
    
        r7 = org.jetbrains.kotlin.load.java.typeEnhancement.NullabilityQualifier.NULLABLE;
     */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final org.jetbrains.kotlin.load.java.typeEnhancement.NullabilityQualifierWithMigrationStatus knownNullability(TAnnotation r7, boolean r8) {
        /*
            Method dump skipped, instruction units count: 270
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jetbrains.kotlin.load.java.AbstractAnnotationTypeQualifierResolver.knownNullability(java.lang.Object, boolean):org.jetbrains.kotlin.load.java.typeEnhancement.NullabilityQualifierWithMigrationStatus");
    }

    /* JADX INFO: compiled from: AbstractAnnotationTypeQualifierResolver.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0082\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001d\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/load/java/AbstractAnnotationTypeQualifierResolver$Companion;", "", "()V", "JAVA_APPLICABILITY_TYPES", "", "", "Lorg/jetbrains/kotlin/load/java/AnnotationQualifierApplicabilityType;", "getJAVA_APPLICABILITY_TYPES", "()Ljava/util/Map;", "compiler.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Map<String, AnnotationQualifierApplicabilityType> getJAVA_APPLICABILITY_TYPES() {
            return AbstractAnnotationTypeQualifierResolver.JAVA_APPLICABILITY_TYPES;
        }
    }

    static {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (AnnotationQualifierApplicabilityType annotationQualifierApplicabilityType : AnnotationQualifierApplicabilityType.values()) {
            String javaTarget = annotationQualifierApplicabilityType.getJavaTarget();
            if (linkedHashMap.get(javaTarget) == null) {
                linkedHashMap.put(javaTarget, annotationQualifierApplicabilityType);
            }
        }
        JAVA_APPLICABILITY_TYPES = linkedHashMap;
    }

    public final NullabilityQualifierWithMigrationStatus extractNullability(Iterable<? extends TAnnotation> annotations, Function1<? super TAnnotation, Boolean> forceWarning) {
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        Intrinsics.checkNotNullParameter(forceWarning, "forceWarning");
        Iterator<? extends TAnnotation> it = annotations.iterator();
        NullabilityQualifierWithMigrationStatus nullabilityQualifierWithMigrationStatus = null;
        while (it.hasNext()) {
            NullabilityQualifierWithMigrationStatus nullabilityQualifierWithMigrationStatusExtractNullability = extractNullability(it.next(), forceWarning);
            if (nullabilityQualifierWithMigrationStatus != null) {
                if (nullabilityQualifierWithMigrationStatusExtractNullability != null && !Intrinsics.areEqual(nullabilityQualifierWithMigrationStatusExtractNullability, nullabilityQualifierWithMigrationStatus) && (!nullabilityQualifierWithMigrationStatusExtractNullability.isForWarningOnly() || nullabilityQualifierWithMigrationStatus.isForWarningOnly())) {
                    if (nullabilityQualifierWithMigrationStatusExtractNullability.isForWarningOnly() || !nullabilityQualifierWithMigrationStatus.isForWarningOnly()) {
                        return null;
                    }
                }
            }
            nullabilityQualifierWithMigrationStatus = nullabilityQualifierWithMigrationStatusExtractNullability;
        }
        return nullabilityQualifierWithMigrationStatus;
    }

    public final MutabilityQualifier extractMutability(Iterable<? extends TAnnotation> annotations) {
        MutabilityQualifier mutabilityQualifier;
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        Iterator<? extends TAnnotation> it = annotations.iterator();
        MutabilityQualifier mutabilityQualifier2 = null;
        while (it.hasNext()) {
            FqName fqName = getFqName(it.next());
            if (JvmAnnotationNamesKt.getREAD_ONLY_ANNOTATIONS().contains(fqName)) {
                mutabilityQualifier = MutabilityQualifier.READ_ONLY;
            } else if (JvmAnnotationNamesKt.getMUTABLE_ANNOTATIONS().contains(fqName)) {
                mutabilityQualifier = MutabilityQualifier.MUTABLE;
            } else {
                continue;
            }
            if (mutabilityQualifier2 != null && mutabilityQualifier2 != mutabilityQualifier) {
                return null;
            }
            mutabilityQualifier2 = mutabilityQualifier;
        }
        return mutabilityQualifier2;
    }
}
