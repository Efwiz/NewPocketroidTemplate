package org.jetbrains.kotlin.resolve;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.annotations.KotlinTarget;
import org.jetbrains.kotlin.lexer.KtKeywordToken;
import org.jetbrains.kotlin.lexer.KtModifierKeywordToken;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX INFO: compiled from: ModifierCheckerHelpers.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000V\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\"\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0011\n\u0002\b\u0015\u001a'\u0010&\u001a\u00020 2\u0006\u0010'\u001a\u00020\n2\u0012\u0010(\u001a\n\u0012\u0006\b\u0001\u0012\u00020\n0)\"\u00020\n¢\u0006\u0002\u0010*\u001a \u0010+\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u0002\u0012\u0004\u0012\u00020\u00040\u000eH\u0002\u001a\u0016\u0010,\u001a\u00020\u00042\u0006\u0010-\u001a\u00020\u00032\u0006\u0010.\u001a\u00020\u0003\u001a9\u0010/\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u0002\u0012\u0004\u0012\u00020\u00040\u000e2\u0012\u00100\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00030)\"\u00020\u0003H\u0002¢\u0006\u0002\u00101\u001aA\u00102\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u0002\u0012\u0004\u0012\u00020\u00040\u000e2\u0006\u0010,\u001a\u00020\u00042\u0012\u00100\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00030)\"\u00020\u0003H\u0002¢\u0006\u0002\u00103\u001a/\u00104\u001a\u00020 2\u0006\u00105\u001a\u00020\u001a2\u0006\u0010'\u001a\u00020\n2\u0012\u0010(\u001a\n\u0012\u0006\b\u0001\u0012\u00020\n0)\"\u00020\n¢\u0006\u0002\u00106\u001a9\u00107\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u0002\u0012\u0004\u0012\u00020\u00040\u000e2\u0012\u00100\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00030)\"\u00020\u0003H\u0002¢\u0006\u0002\u00101\u001a\u0016\u00108\u001a\u00020 2\u0006\u00109\u001a\u00020 2\u0006\u0010:\u001a\u00020 \u001a0\u0010;\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u0002\u0012\u0004\u0012\u00020\u00040\u000e2\u0006\u0010<\u001a\u00020\u00032\u0006\u0010=\u001a\u00020\u0003H\u0002\"E\u0010\u0000\u001a6\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u0002\u0012\u0004\u0012\u00020\u00040\u0001j\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u0002\u0012\u0004\u0012\u00020\u0004`\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f\"-\u0010\r\u001a\u001e\u0012\f\u0012\n \u0010*\u0004\u0018\u00010\u000f0\u000f\u0012\f\u0012\n \u0010*\u0004\u0018\u00010\u000f0\u000f0\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"#\u0010\u0013\u001a\u0014\u0012\u0004\u0012\u00020\u0003\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u00140\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0012\"#\u0010\u0016\u001a\u0014\u0012\u0004\u0012\u00020\u0003\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u00140\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0012\"+\u0010\u0018\u001a\u001c\u0012\f\u0012\n \u0010*\u0004\u0018\u00010\u000f0\u000f\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001a0\u00190\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0012\"#\u0010\u001c\u001a\u0014\u0012\u0004\u0012\u00020\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u00140\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0012\"&\u0010\u001e\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u0002\u0012\u0004\u0012\u00020\u00040\u000eX\u0082\u0004¢\u0006\u0002\n\u0000\"%\u0010\u001f\u001a\u0016\u0012\f\u0012\n \u0010*\u0004\u0018\u00010\u000f0\u000f\u0012\u0004\u0012\u00020 0\u000e¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0012\"I\u0010\"\u001a:\u0012\f\u0012\n \u0010*\u0004\u0018\u00010\u000f0\u000f\u0012(\u0012&\u0012\f\u0012\n \u0010*\u0004\u0018\u00010\n0\n \u0010*\u0012\u0012\f\u0012\n \u0010*\u0004\u0018\u00010\n0\n\u0018\u00010\u00140\u00140\u000e¢\u0006\b\n\u0000\u001a\u0004\b#\u0010\u0012\"#\u0010$\u001a\u0014\u0012\u0004\u0012\u00020\u0003\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u00140\u000e¢\u0006\b\n\u0000\u001a\u0004\b%\u0010\u0012¨\u0006>"}, d2 = {"compatibilityTypeMap", "Ljava/util/HashMap;", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/lexer/KtKeywordToken;", "Lorg/jetbrains/kotlin/resolve/Compatibility;", "Lkotlin/collections/HashMap;", "getCompatibilityTypeMap", "()Ljava/util/HashMap;", "defaultVisibilityTargets", "Ljava/util/EnumSet;", "Lorg/jetbrains/kotlin/descriptors/annotations/KotlinTarget;", "getDefaultVisibilityTargets", "()Ljava/util/EnumSet;", "deprecatedModifierMap", "", "Lorg/jetbrains/kotlin/lexer/KtModifierKeywordToken;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "getDeprecatedModifierMap", "()Ljava/util/Map;", "deprecatedParentTargetMap", "", "getDeprecatedParentTargetMap", "deprecatedTargetMap", "getDeprecatedTargetMap", "featureDependencies", "", "Lorg/jetbrains/kotlin/config/LanguageFeature;", "getFeatureDependencies", "featureDependenciesTargets", "getFeatureDependenciesTargets", "mutualCompatibility", "possibleParentTargetPredicateMap", "Lorg/jetbrains/kotlin/resolve/TargetAllowedPredicate;", "getPossibleParentTargetPredicateMap", "possibleTargetMap", "getPossibleTargetMap", "redundantTargetMap", "getRedundantTargetMap", "always", "target", "targets", "", "(Lorg/jetbrains/kotlin/descriptors/annotations/KotlinTarget;[Lorg/jetbrains/kotlin/descriptors/annotations/KotlinTarget;)Lorg/jetbrains/kotlin/resolve/TargetAllowedPredicate;", "buildCompatibilityMap", "compatibility", "first", "second", "compatibilityForClassesRegister", SchemaSymbols.ATTVAL_LIST, "([Lorg/jetbrains/kotlin/lexer/KtKeywordToken;)Ljava/util/Map;", "compatibilityRegister", "(Lorg/jetbrains/kotlin/resolve/Compatibility;[Lorg/jetbrains/kotlin/lexer/KtKeywordToken;)Ljava/util/Map;", "ifSupported", "languageFeature", "(Lorg/jetbrains/kotlin/config/LanguageFeature;Lorg/jetbrains/kotlin/descriptors/annotations/KotlinTarget;[Lorg/jetbrains/kotlin/descriptors/annotations/KotlinTarget;)Lorg/jetbrains/kotlin/resolve/TargetAllowedPredicate;", "incompatibilityRegister", "or", "p1", "p2", "redundantRegister", "sufficient", "redundant", "psi"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ModifierCheckerHelpersKt {
    private static final EnumSet<KotlinTarget> defaultVisibilityTargets;
    private static final Map<KtModifierKeywordToken, KtModifierKeywordToken> deprecatedModifierMap;
    private static final Map<KtKeywordToken, Set<KotlinTarget>> deprecatedParentTargetMap;
    private static final Map<KtKeywordToken, Set<KotlinTarget>> deprecatedTargetMap;
    private static final Map<KtModifierKeywordToken, TargetAllowedPredicate> possibleParentTargetPredicateMap;
    private static final Map<KtModifierKeywordToken, Set<KotlinTarget>> possibleTargetMap;
    private static final Map<KtKeywordToken, Set<KotlinTarget>> redundantTargetMap;
    private static final HashMap<Pair<KtKeywordToken, KtKeywordToken>, Compatibility> compatibilityTypeMap = new HashMap<>();
    private static final Map<Pair<KtKeywordToken, KtKeywordToken>, Compatibility> mutualCompatibility = buildCompatibilityMap();
    private static final Map<KtModifierKeywordToken, List<LanguageFeature>> featureDependencies = MapsKt.mapOf(TuplesKt.to(KtTokens.SUSPEND_KEYWORD, CollectionsKt.listOf(LanguageFeature.Coroutines)), TuplesKt.to(KtTokens.INLINE_KEYWORD, CollectionsKt.listOf((Object[]) new LanguageFeature[]{LanguageFeature.InlineProperties, LanguageFeature.InlineClasses})), TuplesKt.to(KtTokens.HEADER_KEYWORD, CollectionsKt.listOf(LanguageFeature.MultiPlatformProjects)), TuplesKt.to(KtTokens.IMPL_KEYWORD, CollectionsKt.listOf(LanguageFeature.MultiPlatformProjects)), TuplesKt.to(KtTokens.EXPECT_KEYWORD, CollectionsKt.listOf(LanguageFeature.MultiPlatformProjects)), TuplesKt.to(KtTokens.ACTUAL_KEYWORD, CollectionsKt.listOf(LanguageFeature.MultiPlatformProjects)), TuplesKt.to(KtTokens.LATEINIT_KEYWORD, CollectionsKt.listOf((Object[]) new LanguageFeature[]{LanguageFeature.LateinitTopLevelProperties, LanguageFeature.LateinitLocalVariables})), TuplesKt.to(KtTokens.FUN_KEYWORD, CollectionsKt.listOf(LanguageFeature.FunctionalInterfaceConversion)), TuplesKt.to(KtTokens.DATA_KEYWORD, CollectionsKt.listOf(LanguageFeature.DataObjects)));
    private static final Map<LanguageFeature, Set<KotlinTarget>> featureDependenciesTargets = MapsKt.mapOf(TuplesKt.to(LanguageFeature.InlineProperties, SetsKt.setOf((Object[]) new KotlinTarget[]{KotlinTarget.PROPERTY, KotlinTarget.PROPERTY_GETTER, KotlinTarget.PROPERTY_SETTER})), TuplesKt.to(LanguageFeature.LateinitLocalVariables, SetsKt.setOf(KotlinTarget.LOCAL_VARIABLE)), TuplesKt.to(LanguageFeature.LateinitTopLevelProperties, SetsKt.setOf(KotlinTarget.TOP_LEVEL_PROPERTY)), TuplesKt.to(LanguageFeature.InlineClasses, SetsKt.setOf(KotlinTarget.CLASS_ONLY)), TuplesKt.to(LanguageFeature.JvmInlineValueClasses, SetsKt.setOf(KotlinTarget.CLASS_ONLY)), TuplesKt.to(LanguageFeature.FunctionalInterfaceConversion, SetsKt.setOf(KotlinTarget.INTERFACE)), TuplesKt.to(LanguageFeature.DataObjects, SetsKt.setOf(KotlinTarget.STANDALONE_OBJECT)));

    static {
        EnumSet<KotlinTarget> enumSetOf = EnumSet.of(KotlinTarget.CLASS_ONLY, KotlinTarget.OBJECT, KotlinTarget.INTERFACE, KotlinTarget.ENUM_CLASS, KotlinTarget.ANNOTATION_CLASS, KotlinTarget.MEMBER_FUNCTION, KotlinTarget.TOP_LEVEL_FUNCTION, KotlinTarget.PROPERTY_GETTER, KotlinTarget.PROPERTY_SETTER, KotlinTarget.MEMBER_PROPERTY, KotlinTarget.TOP_LEVEL_PROPERTY, KotlinTarget.CONSTRUCTOR, KotlinTarget.TYPEALIAS);
        Intrinsics.checkNotNullExpressionValue(enumSetOf, "of(...)");
        defaultVisibilityTargets = enumSetOf;
        possibleTargetMap = MapsKt.mapOf(TuplesKt.to(KtTokens.ENUM_KEYWORD, EnumSet.of(KotlinTarget.ENUM_CLASS)), TuplesKt.to(KtTokens.ABSTRACT_KEYWORD, EnumSet.of(KotlinTarget.CLASS_ONLY, KotlinTarget.LOCAL_CLASS, KotlinTarget.INTERFACE, KotlinTarget.MEMBER_PROPERTY, KotlinTarget.MEMBER_FUNCTION)), TuplesKt.to(KtTokens.OPEN_KEYWORD, EnumSet.of(KotlinTarget.CLASS_ONLY, KotlinTarget.LOCAL_CLASS, KotlinTarget.INTERFACE, KotlinTarget.MEMBER_PROPERTY, KotlinTarget.MEMBER_FUNCTION)), TuplesKt.to(KtTokens.FINAL_KEYWORD, EnumSet.of(KotlinTarget.CLASS_ONLY, KotlinTarget.LOCAL_CLASS, KotlinTarget.ENUM_CLASS, KotlinTarget.OBJECT, KotlinTarget.MEMBER_PROPERTY, KotlinTarget.MEMBER_FUNCTION)), TuplesKt.to(KtTokens.SEALED_KEYWORD, EnumSet.of(KotlinTarget.CLASS_ONLY, KotlinTarget.INTERFACE)), TuplesKt.to(KtTokens.INNER_KEYWORD, EnumSet.of(KotlinTarget.CLASS_ONLY)), TuplesKt.to(KtTokens.OVERRIDE_KEYWORD, EnumSet.of(KotlinTarget.MEMBER_PROPERTY, KotlinTarget.MEMBER_FUNCTION)), TuplesKt.to(KtTokens.PRIVATE_KEYWORD, SetsKt.plus(enumSetOf, KotlinTarget.BACKING_FIELD)), TuplesKt.to(KtTokens.PUBLIC_KEYWORD, enumSetOf), TuplesKt.to(KtTokens.INTERNAL_KEYWORD, SetsKt.plus(enumSetOf, KotlinTarget.BACKING_FIELD)), TuplesKt.to(KtTokens.PROTECTED_KEYWORD, EnumSet.of(KotlinTarget.CLASS_ONLY, KotlinTarget.OBJECT, KotlinTarget.INTERFACE, KotlinTarget.ENUM_CLASS, KotlinTarget.ANNOTATION_CLASS, KotlinTarget.MEMBER_FUNCTION, KotlinTarget.PROPERTY_GETTER, KotlinTarget.PROPERTY_SETTER, KotlinTarget.MEMBER_PROPERTY, KotlinTarget.CONSTRUCTOR, KotlinTarget.TYPEALIAS)), TuplesKt.to(KtTokens.IN_KEYWORD, EnumSet.of(KotlinTarget.TYPE_PARAMETER, KotlinTarget.TYPE_PROJECTION)), TuplesKt.to(KtTokens.OUT_KEYWORD, EnumSet.of(KotlinTarget.TYPE_PARAMETER, KotlinTarget.TYPE_PROJECTION)), TuplesKt.to(KtTokens.REIFIED_KEYWORD, EnumSet.of(KotlinTarget.TYPE_PARAMETER)), TuplesKt.to(KtTokens.VARARG_KEYWORD, EnumSet.of(KotlinTarget.VALUE_PARAMETER, KotlinTarget.PROPERTY_PARAMETER)), TuplesKt.to(KtTokens.COMPANION_KEYWORD, EnumSet.of(KotlinTarget.OBJECT)), TuplesKt.to(KtTokens.LATEINIT_KEYWORD, EnumSet.of(KotlinTarget.MEMBER_PROPERTY, KotlinTarget.TOP_LEVEL_PROPERTY, KotlinTarget.LOCAL_VARIABLE, KotlinTarget.BACKING_FIELD)), TuplesKt.to(KtTokens.DATA_KEYWORD, EnumSet.of(KotlinTarget.CLASS_ONLY, KotlinTarget.LOCAL_CLASS, KotlinTarget.STANDALONE_OBJECT)), TuplesKt.to(KtTokens.INLINE_KEYWORD, EnumSet.of(KotlinTarget.FUNCTION, KotlinTarget.PROPERTY, KotlinTarget.PROPERTY_GETTER, KotlinTarget.PROPERTY_SETTER, KotlinTarget.CLASS_ONLY)), TuplesKt.to(KtTokens.NOINLINE_KEYWORD, EnumSet.of(KotlinTarget.VALUE_PARAMETER)), TuplesKt.to(KtTokens.TAILREC_KEYWORD, EnumSet.of(KotlinTarget.FUNCTION)), TuplesKt.to(KtTokens.SUSPEND_KEYWORD, EnumSet.of(KotlinTarget.MEMBER_FUNCTION, KotlinTarget.TOP_LEVEL_FUNCTION, KotlinTarget.LOCAL_FUNCTION, KotlinTarget.ANONYMOUS_FUNCTION)), TuplesKt.to(KtTokens.EXTERNAL_KEYWORD, EnumSet.of(KotlinTarget.FUNCTION, KotlinTarget.PROPERTY, KotlinTarget.PROPERTY_GETTER, KotlinTarget.PROPERTY_SETTER, KotlinTarget.CLASS)), TuplesKt.to(KtTokens.ANNOTATION_KEYWORD, EnumSet.of(KotlinTarget.ANNOTATION_CLASS)), TuplesKt.to(KtTokens.CROSSINLINE_KEYWORD, EnumSet.of(KotlinTarget.VALUE_PARAMETER)), TuplesKt.to(KtTokens.CONST_KEYWORD, EnumSet.of(KotlinTarget.MEMBER_PROPERTY, KotlinTarget.TOP_LEVEL_PROPERTY)), TuplesKt.to(KtTokens.OPERATOR_KEYWORD, EnumSet.of(KotlinTarget.FUNCTION)), TuplesKt.to(KtTokens.INFIX_KEYWORD, EnumSet.of(KotlinTarget.FUNCTION)), TuplesKt.to(KtTokens.HEADER_KEYWORD, EnumSet.of(KotlinTarget.TOP_LEVEL_FUNCTION, KotlinTarget.TOP_LEVEL_PROPERTY, KotlinTarget.CLASS_ONLY, KotlinTarget.OBJECT, KotlinTarget.INTERFACE, KotlinTarget.ENUM_CLASS, KotlinTarget.ANNOTATION_CLASS)), TuplesKt.to(KtTokens.IMPL_KEYWORD, EnumSet.of(KotlinTarget.TOP_LEVEL_FUNCTION, KotlinTarget.MEMBER_FUNCTION, KotlinTarget.TOP_LEVEL_PROPERTY, KotlinTarget.MEMBER_PROPERTY, KotlinTarget.CONSTRUCTOR, KotlinTarget.CLASS_ONLY, KotlinTarget.OBJECT, KotlinTarget.INTERFACE, KotlinTarget.ENUM_CLASS, KotlinTarget.ANNOTATION_CLASS, KotlinTarget.TYPEALIAS)), TuplesKt.to(KtTokens.EXPECT_KEYWORD, EnumSet.of(KotlinTarget.TOP_LEVEL_FUNCTION, KotlinTarget.TOP_LEVEL_PROPERTY, KotlinTarget.CLASS_ONLY, KotlinTarget.OBJECT, KotlinTarget.INTERFACE, KotlinTarget.ENUM_CLASS, KotlinTarget.ANNOTATION_CLASS)), TuplesKt.to(KtTokens.ACTUAL_KEYWORD, EnumSet.of(KotlinTarget.TOP_LEVEL_FUNCTION, KotlinTarget.MEMBER_FUNCTION, KotlinTarget.TOP_LEVEL_PROPERTY, KotlinTarget.MEMBER_PROPERTY, KotlinTarget.CONSTRUCTOR, KotlinTarget.CLASS_ONLY, KotlinTarget.OBJECT, KotlinTarget.INTERFACE, KotlinTarget.ENUM_CLASS, KotlinTarget.ANNOTATION_CLASS, KotlinTarget.TYPEALIAS)), TuplesKt.to(KtTokens.FUN_KEYWORD, EnumSet.of(KotlinTarget.INTERFACE)), TuplesKt.to(KtTokens.VALUE_KEYWORD, EnumSet.of(KotlinTarget.CLASS_ONLY)));
        deprecatedTargetMap = MapsKt.emptyMap();
        deprecatedParentTargetMap = MapsKt.emptyMap();
        deprecatedModifierMap = MapsKt.mapOf(TuplesKt.to(KtTokens.HEADER_KEYWORD, KtTokens.EXPECT_KEYWORD), TuplesKt.to(KtTokens.IMPL_KEYWORD, KtTokens.ACTUAL_KEYWORD));
        redundantTargetMap = MapsKt.mapOf(TuplesKt.to(KtTokens.OPEN_KEYWORD, EnumSet.of(KotlinTarget.INTERFACE)));
        possibleParentTargetPredicateMap = MapsKt.mapOf(TuplesKt.to(KtTokens.INNER_KEYWORD, or(always(KotlinTarget.CLASS_ONLY, KotlinTarget.LOCAL_CLASS, KotlinTarget.ENUM_CLASS), ifSupported(LanguageFeature.InnerClassInEnumEntryClass, KotlinTarget.ENUM_ENTRY, new KotlinTarget[0]))), TuplesKt.to(KtTokens.OVERRIDE_KEYWORD, always(KotlinTarget.CLASS_ONLY, KotlinTarget.LOCAL_CLASS, KotlinTarget.OBJECT, KotlinTarget.OBJECT_LITERAL, KotlinTarget.INTERFACE, KotlinTarget.ENUM_CLASS, KotlinTarget.ENUM_ENTRY)), TuplesKt.to(KtTokens.PROTECTED_KEYWORD, always(KotlinTarget.CLASS_ONLY, KotlinTarget.LOCAL_CLASS, KotlinTarget.ENUM_CLASS, KotlinTarget.COMPANION_OBJECT)), TuplesKt.to(KtTokens.INTERNAL_KEYWORD, always(KotlinTarget.CLASS_ONLY, KotlinTarget.LOCAL_CLASS, KotlinTarget.OBJECT, KotlinTarget.OBJECT_LITERAL, KotlinTarget.ENUM_CLASS, KotlinTarget.ENUM_ENTRY, KotlinTarget.FILE)), TuplesKt.to(KtTokens.PRIVATE_KEYWORD, always(KotlinTarget.CLASS_ONLY, KotlinTarget.LOCAL_CLASS, KotlinTarget.OBJECT, KotlinTarget.OBJECT_LITERAL, KotlinTarget.INTERFACE, KotlinTarget.ENUM_CLASS, KotlinTarget.ENUM_ENTRY, KotlinTarget.FILE)), TuplesKt.to(KtTokens.COMPANION_KEYWORD, always(KotlinTarget.CLASS_ONLY, KotlinTarget.INTERFACE, KotlinTarget.ENUM_CLASS, KotlinTarget.ANNOTATION_CLASS)), TuplesKt.to(KtTokens.FINAL_KEYWORD, always(KotlinTarget.CLASS_ONLY, KotlinTarget.LOCAL_CLASS, KotlinTarget.OBJECT, KotlinTarget.OBJECT_LITERAL, KotlinTarget.ENUM_CLASS, KotlinTarget.ENUM_ENTRY, KotlinTarget.ANNOTATION_CLASS, KotlinTarget.FILE)), TuplesKt.to(KtTokens.VARARG_KEYWORD, always(KotlinTarget.CONSTRUCTOR, KotlinTarget.FUNCTION, KotlinTarget.CLASS)));
    }

    public static final HashMap<Pair<KtKeywordToken, KtKeywordToken>, Compatibility> getCompatibilityTypeMap() {
        return compatibilityTypeMap;
    }

    public static final Compatibility compatibility(KtKeywordToken first, KtKeywordToken second) {
        Intrinsics.checkNotNullParameter(first, "first");
        Intrinsics.checkNotNullParameter(second, "second");
        if (Intrinsics.areEqual(first, second)) {
            return Compatibility.REPEATED;
        }
        Compatibility compatibility = mutualCompatibility.get(new Pair(first, second));
        return compatibility == null ? Compatibility.COMPATIBLE : compatibility;
    }

    private static final Map<Pair<KtKeywordToken, KtKeywordToken>, Compatibility> buildCompatibilityMap() {
        HashMap map = new HashMap();
        KtModifierKeywordToken IN_KEYWORD = KtTokens.IN_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(IN_KEYWORD, "IN_KEYWORD");
        KtModifierKeywordToken OUT_KEYWORD = KtTokens.OUT_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(OUT_KEYWORD, "OUT_KEYWORD");
        map.putAll(incompatibilityRegister(IN_KEYWORD, OUT_KEYWORD));
        KtModifierKeywordToken PRIVATE_KEYWORD = KtTokens.PRIVATE_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(PRIVATE_KEYWORD, "PRIVATE_KEYWORD");
        KtModifierKeywordToken PROTECTED_KEYWORD = KtTokens.PROTECTED_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(PROTECTED_KEYWORD, "PROTECTED_KEYWORD");
        KtModifierKeywordToken PUBLIC_KEYWORD = KtTokens.PUBLIC_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(PUBLIC_KEYWORD, "PUBLIC_KEYWORD");
        KtModifierKeywordToken INTERNAL_KEYWORD = KtTokens.INTERNAL_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(INTERNAL_KEYWORD, "INTERNAL_KEYWORD");
        map.putAll(incompatibilityRegister(PRIVATE_KEYWORD, PROTECTED_KEYWORD, PUBLIC_KEYWORD, INTERNAL_KEYWORD));
        KtModifierKeywordToken ABSTRACT_KEYWORD = KtTokens.ABSTRACT_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(ABSTRACT_KEYWORD, "ABSTRACT_KEYWORD");
        KtModifierKeywordToken OPEN_KEYWORD = KtTokens.OPEN_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(OPEN_KEYWORD, "OPEN_KEYWORD");
        KtModifierKeywordToken FINAL_KEYWORD = KtTokens.FINAL_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(FINAL_KEYWORD, "FINAL_KEYWORD");
        KtModifierKeywordToken SEALED_KEYWORD = KtTokens.SEALED_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(SEALED_KEYWORD, "SEALED_KEYWORD");
        map.putAll(incompatibilityRegister(ABSTRACT_KEYWORD, OPEN_KEYWORD, FINAL_KEYWORD, SEALED_KEYWORD));
        KtModifierKeywordToken DATA_KEYWORD = KtTokens.DATA_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(DATA_KEYWORD, "DATA_KEYWORD");
        KtModifierKeywordToken OPEN_KEYWORD2 = KtTokens.OPEN_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(OPEN_KEYWORD2, "OPEN_KEYWORD");
        map.putAll(incompatibilityRegister(DATA_KEYWORD, OPEN_KEYWORD2));
        KtModifierKeywordToken DATA_KEYWORD2 = KtTokens.DATA_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(DATA_KEYWORD2, "DATA_KEYWORD");
        KtModifierKeywordToken INNER_KEYWORD = KtTokens.INNER_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(INNER_KEYWORD, "INNER_KEYWORD");
        map.putAll(incompatibilityRegister(DATA_KEYWORD2, INNER_KEYWORD));
        KtModifierKeywordToken DATA_KEYWORD3 = KtTokens.DATA_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(DATA_KEYWORD3, "DATA_KEYWORD");
        KtModifierKeywordToken ABSTRACT_KEYWORD2 = KtTokens.ABSTRACT_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(ABSTRACT_KEYWORD2, "ABSTRACT_KEYWORD");
        map.putAll(incompatibilityRegister(DATA_KEYWORD3, ABSTRACT_KEYWORD2));
        KtModifierKeywordToken DATA_KEYWORD4 = KtTokens.DATA_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(DATA_KEYWORD4, "DATA_KEYWORD");
        KtModifierKeywordToken SEALED_KEYWORD2 = KtTokens.SEALED_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(SEALED_KEYWORD2, "SEALED_KEYWORD");
        map.putAll(incompatibilityRegister(DATA_KEYWORD4, SEALED_KEYWORD2));
        KtModifierKeywordToken DATA_KEYWORD5 = KtTokens.DATA_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(DATA_KEYWORD5, "DATA_KEYWORD");
        KtModifierKeywordToken INLINE_KEYWORD = KtTokens.INLINE_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(INLINE_KEYWORD, "INLINE_KEYWORD");
        map.putAll(incompatibilityRegister(DATA_KEYWORD5, INLINE_KEYWORD));
        KtModifierKeywordToken DATA_KEYWORD6 = KtTokens.DATA_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(DATA_KEYWORD6, "DATA_KEYWORD");
        KtModifierKeywordToken VALUE_KEYWORD = KtTokens.VALUE_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(VALUE_KEYWORD, "VALUE_KEYWORD");
        map.putAll(incompatibilityRegister(DATA_KEYWORD6, VALUE_KEYWORD));
        KtModifierKeywordToken DATA_KEYWORD7 = KtTokens.DATA_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(DATA_KEYWORD7, "DATA_KEYWORD");
        KtKeywordToken OBJECT_KEYWORD = KtTokens.OBJECT_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(OBJECT_KEYWORD, "OBJECT_KEYWORD");
        KtModifierKeywordToken EXPECT_KEYWORD = KtTokens.EXPECT_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(EXPECT_KEYWORD, "EXPECT_KEYWORD");
        map.putAll(incompatibilityRegister(DATA_KEYWORD7, OBJECT_KEYWORD, EXPECT_KEYWORD));
        KtModifierKeywordToken ABSTRACT_KEYWORD3 = KtTokens.ABSTRACT_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(ABSTRACT_KEYWORD3, "ABSTRACT_KEYWORD");
        KtModifierKeywordToken OPEN_KEYWORD3 = KtTokens.OPEN_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(OPEN_KEYWORD3, "OPEN_KEYWORD");
        map.putAll(redundantRegister(ABSTRACT_KEYWORD3, OPEN_KEYWORD3));
        KtModifierKeywordToken SEALED_KEYWORD3 = KtTokens.SEALED_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(SEALED_KEYWORD3, "SEALED_KEYWORD");
        KtModifierKeywordToken ABSTRACT_KEYWORD4 = KtTokens.ABSTRACT_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(ABSTRACT_KEYWORD4, "ABSTRACT_KEYWORD");
        map.putAll(redundantRegister(SEALED_KEYWORD3, ABSTRACT_KEYWORD4));
        KtModifierKeywordToken CONST_KEYWORD = KtTokens.CONST_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(CONST_KEYWORD, "CONST_KEYWORD");
        KtModifierKeywordToken ABSTRACT_KEYWORD5 = KtTokens.ABSTRACT_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(ABSTRACT_KEYWORD5, "ABSTRACT_KEYWORD");
        map.putAll(incompatibilityRegister(CONST_KEYWORD, ABSTRACT_KEYWORD5));
        KtModifierKeywordToken CONST_KEYWORD2 = KtTokens.CONST_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(CONST_KEYWORD2, "CONST_KEYWORD");
        KtModifierKeywordToken OPEN_KEYWORD4 = KtTokens.OPEN_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(OPEN_KEYWORD4, "OPEN_KEYWORD");
        map.putAll(incompatibilityRegister(CONST_KEYWORD2, OPEN_KEYWORD4));
        KtModifierKeywordToken CONST_KEYWORD3 = KtTokens.CONST_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(CONST_KEYWORD3, "CONST_KEYWORD");
        KtModifierKeywordToken OVERRIDE_KEYWORD = KtTokens.OVERRIDE_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(OVERRIDE_KEYWORD, "OVERRIDE_KEYWORD");
        map.putAll(incompatibilityRegister(CONST_KEYWORD3, OVERRIDE_KEYWORD));
        KtModifierKeywordToken PRIVATE_KEYWORD2 = KtTokens.PRIVATE_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(PRIVATE_KEYWORD2, "PRIVATE_KEYWORD");
        KtModifierKeywordToken OVERRIDE_KEYWORD2 = KtTokens.OVERRIDE_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(OVERRIDE_KEYWORD2, "OVERRIDE_KEYWORD");
        map.putAll(incompatibilityRegister(PRIVATE_KEYWORD2, OVERRIDE_KEYWORD2));
        KtModifierKeywordToken PRIVATE_KEYWORD3 = KtTokens.PRIVATE_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(PRIVATE_KEYWORD3, "PRIVATE_KEYWORD");
        KtModifierKeywordToken OPEN_KEYWORD5 = KtTokens.OPEN_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(OPEN_KEYWORD5, "OPEN_KEYWORD");
        map.putAll(compatibilityForClassesRegister(PRIVATE_KEYWORD3, OPEN_KEYWORD5));
        KtModifierKeywordToken PRIVATE_KEYWORD4 = KtTokens.PRIVATE_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(PRIVATE_KEYWORD4, "PRIVATE_KEYWORD");
        KtModifierKeywordToken ABSTRACT_KEYWORD6 = KtTokens.ABSTRACT_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(ABSTRACT_KEYWORD6, "ABSTRACT_KEYWORD");
        map.putAll(compatibilityForClassesRegister(PRIVATE_KEYWORD4, ABSTRACT_KEYWORD6));
        KtModifierKeywordToken CROSSINLINE_KEYWORD = KtTokens.CROSSINLINE_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(CROSSINLINE_KEYWORD, "CROSSINLINE_KEYWORD");
        KtModifierKeywordToken NOINLINE_KEYWORD = KtTokens.NOINLINE_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(NOINLINE_KEYWORD, "NOINLINE_KEYWORD");
        map.putAll(incompatibilityRegister(CROSSINLINE_KEYWORD, NOINLINE_KEYWORD));
        KtModifierKeywordToken SEALED_KEYWORD4 = KtTokens.SEALED_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(SEALED_KEYWORD4, "SEALED_KEYWORD");
        KtModifierKeywordToken INNER_KEYWORD2 = KtTokens.INNER_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(INNER_KEYWORD2, "INNER_KEYWORD");
        map.putAll(incompatibilityRegister(SEALED_KEYWORD4, INNER_KEYWORD2));
        KtModifierKeywordToken HEADER_KEYWORD = KtTokens.HEADER_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(HEADER_KEYWORD, "HEADER_KEYWORD");
        KtModifierKeywordToken EXPECT_KEYWORD2 = KtTokens.EXPECT_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(EXPECT_KEYWORD2, "EXPECT_KEYWORD");
        KtModifierKeywordToken IMPL_KEYWORD = KtTokens.IMPL_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(IMPL_KEYWORD, "IMPL_KEYWORD");
        KtModifierKeywordToken ACTUAL_KEYWORD = KtTokens.ACTUAL_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(ACTUAL_KEYWORD, "ACTUAL_KEYWORD");
        map.putAll(incompatibilityRegister(HEADER_KEYWORD, EXPECT_KEYWORD2, IMPL_KEYWORD, ACTUAL_KEYWORD));
        return map;
    }

    private static final Map<Pair<KtKeywordToken, KtKeywordToken>, Compatibility> incompatibilityRegister(KtKeywordToken... ktKeywordTokenArr) {
        return compatibilityRegister(Compatibility.INCOMPATIBLE, (KtKeywordToken[]) Arrays.copyOf(ktKeywordTokenArr, ktKeywordTokenArr.length));
    }

    private static final Map<Pair<KtKeywordToken, KtKeywordToken>, Compatibility> redundantRegister(KtKeywordToken ktKeywordToken, KtKeywordToken ktKeywordToken2) {
        return MapsKt.mapOf(TuplesKt.to(new Pair(ktKeywordToken, ktKeywordToken2), Compatibility.REDUNDANT), TuplesKt.to(new Pair(ktKeywordToken2, ktKeywordToken), Compatibility.REVERSE_REDUNDANT));
    }

    private static final Map<Pair<KtKeywordToken, KtKeywordToken>, Compatibility> compatibilityForClassesRegister(KtKeywordToken... ktKeywordTokenArr) {
        return compatibilityRegister(Compatibility.COMPATIBLE_FOR_CLASSES_ONLY, (KtKeywordToken[]) Arrays.copyOf(ktKeywordTokenArr, ktKeywordTokenArr.length));
    }

    private static final Map<Pair<KtKeywordToken, KtKeywordToken>, Compatibility> compatibilityRegister(Compatibility compatibility, KtKeywordToken... ktKeywordTokenArr) {
        HashMap map = new HashMap();
        for (KtKeywordToken ktKeywordToken : ktKeywordTokenArr) {
            for (KtKeywordToken ktKeywordToken2 : ktKeywordTokenArr) {
                if (!Intrinsics.areEqual(ktKeywordToken, ktKeywordToken2)) {
                    map.put(new Pair(ktKeywordToken, ktKeywordToken2), compatibility);
                }
            }
        }
        return map;
    }

    public static final Map<KtModifierKeywordToken, List<LanguageFeature>> getFeatureDependencies() {
        return featureDependencies;
    }

    public static final Map<LanguageFeature, Set<KotlinTarget>> getFeatureDependenciesTargets() {
        return featureDependenciesTargets;
    }

    public static final EnumSet<KotlinTarget> getDefaultVisibilityTargets() {
        return defaultVisibilityTargets;
    }

    public static final Map<KtModifierKeywordToken, Set<KotlinTarget>> getPossibleTargetMap() {
        return possibleTargetMap;
    }

    public static final Map<KtKeywordToken, Set<KotlinTarget>> getDeprecatedTargetMap() {
        return deprecatedTargetMap;
    }

    public static final Map<KtKeywordToken, Set<KotlinTarget>> getDeprecatedParentTargetMap() {
        return deprecatedParentTargetMap;
    }

    public static final Map<KtModifierKeywordToken, KtModifierKeywordToken> getDeprecatedModifierMap() {
        return deprecatedModifierMap;
    }

    public static final Map<KtKeywordToken, Set<KotlinTarget>> getRedundantTargetMap() {
        return redundantTargetMap;
    }

    public static final TargetAllowedPredicate always(KotlinTarget target, KotlinTarget... targets) {
        Intrinsics.checkNotNullParameter(target, "target");
        Intrinsics.checkNotNullParameter(targets, "targets");
        return new TargetAllowedPredicate(target, targets) { // from class: org.jetbrains.kotlin.resolve.ModifierCheckerHelpersKt.always.1
            private final EnumSet<KotlinTarget> targetSet;

            {
                this.targetSet = EnumSet.of(target, (KotlinTarget[]) Arrays.copyOf(targets, targets.length));
            }

            @Override // org.jetbrains.kotlin.resolve.TargetAllowedPredicate
            public boolean isAllowed(KotlinTarget target2, LanguageVersionSettings languageVersionSettings) {
                Intrinsics.checkNotNullParameter(target2, "target");
                Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
                return this.targetSet.contains(target2);
            }
        };
    }

    public static final TargetAllowedPredicate ifSupported(LanguageFeature languageFeature, KotlinTarget target, KotlinTarget... targets) {
        Intrinsics.checkNotNullParameter(languageFeature, "languageFeature");
        Intrinsics.checkNotNullParameter(target, "target");
        Intrinsics.checkNotNullParameter(targets, "targets");
        return new TargetAllowedPredicate(target, targets, languageFeature) { // from class: org.jetbrains.kotlin.resolve.ModifierCheckerHelpersKt.ifSupported.1
            final /* synthetic */ LanguageFeature $languageFeature;
            private final EnumSet<KotlinTarget> targetSet;

            {
                this.$languageFeature = languageFeature;
                this.targetSet = EnumSet.of(target, (KotlinTarget[]) Arrays.copyOf(targets, targets.length));
            }

            @Override // org.jetbrains.kotlin.resolve.TargetAllowedPredicate
            public boolean isAllowed(KotlinTarget target2, LanguageVersionSettings languageVersionSettings) {
                Intrinsics.checkNotNullParameter(target2, "target");
                Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
                return languageVersionSettings.supportsFeature(this.$languageFeature) && this.targetSet.contains(target2);
            }
        };
    }

    public static final TargetAllowedPredicate or(final TargetAllowedPredicate p1, final TargetAllowedPredicate p2) {
        Intrinsics.checkNotNullParameter(p1, "p1");
        Intrinsics.checkNotNullParameter(p2, "p2");
        return new TargetAllowedPredicate() { // from class: org.jetbrains.kotlin.resolve.ModifierCheckerHelpersKt.or.1
            @Override // org.jetbrains.kotlin.resolve.TargetAllowedPredicate
            public boolean isAllowed(KotlinTarget target, LanguageVersionSettings languageVersionSettings) {
                Intrinsics.checkNotNullParameter(target, "target");
                Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
                return p1.isAllowed(target, languageVersionSettings) || p2.isAllowed(target, languageVersionSettings);
            }
        };
    }

    public static final Map<KtModifierKeywordToken, TargetAllowedPredicate> getPossibleParentTargetPredicateMap() {
        return possibleParentTargetPredicateMap;
    }
}
