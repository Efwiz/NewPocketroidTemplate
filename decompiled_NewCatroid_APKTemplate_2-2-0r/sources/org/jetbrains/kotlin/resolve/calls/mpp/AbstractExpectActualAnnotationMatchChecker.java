package org.jetbrains.kotlin.resolve.calls.mpp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.mpp.DeclarationSymbolMarker;
import org.jetbrains.kotlin.mpp.RegularClassSymbolMarker;
import org.jetbrains.kotlin.mpp.TypeAliasSymbolMarker;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.StandardClassIds;
import org.jetbrains.kotlin.resolve.checkers.OptInNames;
import org.jetbrains.kotlin.resolve.multiplatform.ExpectActualAnnotationsIncompatibilityType;

/* JADX INFO: compiled from: AbstractExpectActualAnnotationMatchChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001\u0011B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J'\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0002R\u0006\u0012\u0002\b\u00030\b¢\u0006\u0002\u0010\fJ$\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\n\u0010\r\u001a\u0006\u0012\u0002\b\u00030\bJ\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0005H\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/mpp/AbstractExpectActualAnnotationMatchChecker;", "", "()V", "SKIPPED_CLASS_IDS", "", "Lorg/jetbrains/kotlin/name/ClassId;", "areAnnotationsCompatible", "Lorg/jetbrains/kotlin/resolve/calls/mpp/AbstractExpectActualAnnotationMatchChecker$Incompatibility;", "Lorg/jetbrains/kotlin/resolve/calls/mpp/ExpectActualMatchingContext;", "expectSymbol", "Lorg/jetbrains/kotlin/mpp/DeclarationSymbolMarker;", "actualSymbol", "(Lorg/jetbrains/kotlin/resolve/calls/mpp/ExpectActualMatchingContext;Lorg/jetbrains/kotlin/mpp/DeclarationSymbolMarker;Lorg/jetbrains/kotlin/mpp/DeclarationSymbolMarker;)Lorg/jetbrains/kotlin/resolve/calls/mpp/AbstractExpectActualAnnotationMatchChecker$Incompatibility;", "context", "getAnnotationCollectionArgumentsCompatibilityChecker", "Lorg/jetbrains/kotlin/resolve/calls/mpp/ExpectActualCollectionArgumentsCompatibilityCheckStrategy;", "annotationClassId", "Incompatibility", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AbstractExpectActualAnnotationMatchChecker {
    public static final AbstractExpectActualAnnotationMatchChecker INSTANCE = new AbstractExpectActualAnnotationMatchChecker();
    private static final Set<ClassId> SKIPPED_CLASS_IDS = SetsKt.setOf((Object[]) new ClassId[]{StandardClassIds.Annotations.INSTANCE.getDeprecated(), StandardClassIds.Annotations.INSTANCE.getDeprecatedSinceKotlin(), StandardClassIds.Annotations.INSTANCE.getImplicitlyActualizedByJvmDeclaration(), StandardClassIds.Annotations.INSTANCE.getOptionalExpectation(), StandardClassIds.Annotations.INSTANCE.getRequireKotlin(), StandardClassIds.Annotations.INSTANCE.getSinceKotlin(), StandardClassIds.Annotations.INSTANCE.getSuppress(), StandardClassIds.Annotations.INSTANCE.getWasExperimental(), OptInNames.INSTANCE.getOPT_IN_CLASS_ID()});

    private AbstractExpectActualAnnotationMatchChecker() {
    }

    /* JADX INFO: compiled from: AbstractExpectActualAnnotationMatchChecker.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\u0002\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\nR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/mpp/AbstractExpectActualAnnotationMatchChecker$Incompatibility;", "", "expectSymbol", "Lorg/jetbrains/kotlin/mpp/DeclarationSymbolMarker;", "actualSymbol", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualAnnotationsIncompatibilityType;", "Lorg/jetbrains/kotlin/resolve/calls/mpp/ExpectActualMatchingContext$AnnotationCallInfo;", "(Lorg/jetbrains/kotlin/mpp/DeclarationSymbolMarker;Lorg/jetbrains/kotlin/mpp/DeclarationSymbolMarker;Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualAnnotationsIncompatibilityType;)V", "getActualSymbol", "()Lorg/jetbrains/kotlin/mpp/DeclarationSymbolMarker;", "getExpectSymbol", "getType", "()Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualAnnotationsIncompatibilityType;", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Incompatibility {
        private final DeclarationSymbolMarker actualSymbol;
        private final DeclarationSymbolMarker expectSymbol;
        private final ExpectActualAnnotationsIncompatibilityType<ExpectActualMatchingContext.AnnotationCallInfo> type;

        /* JADX WARN: Multi-variable type inference failed */
        public Incompatibility(DeclarationSymbolMarker expectSymbol, DeclarationSymbolMarker actualSymbol, ExpectActualAnnotationsIncompatibilityType<? extends ExpectActualMatchingContext.AnnotationCallInfo> type) {
            Intrinsics.checkNotNullParameter(expectSymbol, "expectSymbol");
            Intrinsics.checkNotNullParameter(actualSymbol, "actualSymbol");
            Intrinsics.checkNotNullParameter(type, "type");
            this.expectSymbol = expectSymbol;
            this.actualSymbol = actualSymbol;
            this.type = type;
        }

        public final DeclarationSymbolMarker getExpectSymbol() {
            return this.expectSymbol;
        }

        public final DeclarationSymbolMarker getActualSymbol() {
            return this.actualSymbol;
        }

        public final ExpectActualAnnotationsIncompatibilityType<ExpectActualMatchingContext.AnnotationCallInfo> getType() {
            return this.type;
        }
    }

    public final Incompatibility areAnnotationsCompatible(DeclarationSymbolMarker expectSymbol, DeclarationSymbolMarker actualSymbol, ExpectActualMatchingContext<?> context) {
        Intrinsics.checkNotNullParameter(expectSymbol, "expectSymbol");
        Intrinsics.checkNotNullParameter(actualSymbol, "actualSymbol");
        Intrinsics.checkNotNullParameter(context, "context");
        return INSTANCE.areAnnotationsCompatible(context, expectSymbol, actualSymbol);
    }

    private final Incompatibility areAnnotationsCompatible(ExpectActualMatchingContext<?> expectActualMatchingContext, DeclarationSymbolMarker declarationSymbolMarker, DeclarationSymbolMarker declarationSymbolMarker2) {
        ExpectActualAnnotationsIncompatibilityType.MissingOnActual missingOnActual;
        if (declarationSymbolMarker2 instanceof TypeAliasSymbolMarker) {
            RegularClassSymbolMarker regularClassSymbolMarkerExpandToRegularClass = expectActualMatchingContext.expandToRegularClass((TypeAliasSymbolMarker) declarationSymbolMarker2);
            if (regularClassSymbolMarkerExpandToRegularClass == null) {
                return null;
            }
            return areAnnotationsCompatible(expectActualMatchingContext, declarationSymbolMarker, regularClassSymbolMarkerExpandToRegularClass);
        }
        boolean hasSourceAnnotationsErased = expectActualMatchingContext.getHasSourceAnnotationsErased(declarationSymbolMarker2);
        List<ExpectActualMatchingContext.AnnotationCallInfo> annotations = expectActualMatchingContext.getAnnotations(declarationSymbolMarker2);
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Object obj : annotations) {
            ClassId classId = ((ExpectActualMatchingContext.AnnotationCallInfo) obj).getClassId();
            Object obj2 = linkedHashMap.get(classId);
            if (obj2 == null) {
                obj2 = (List) new ArrayList();
                linkedHashMap.put(classId, obj2);
            }
            ((List) obj2).add(obj);
        }
        for (ExpectActualMatchingContext.AnnotationCallInfo annotationCallInfo : expectActualMatchingContext.getAnnotations(declarationSymbolMarker)) {
            ClassId classId2 = annotationCallInfo.getClassId();
            if (classId2 != null && !SKIPPED_CLASS_IDS.contains(classId2) && !annotationCallInfo.isOptIn() && (!annotationCallInfo.isRetentionSource() || !hasSourceAnnotationsErased)) {
                List listEmptyList = (List) linkedHashMap.get(classId2);
                if (listEmptyList == null) {
                    listEmptyList = CollectionsKt.emptyList();
                }
                if (listEmptyList.isEmpty()) {
                    return new Incompatibility(declarationSymbolMarker, declarationSymbolMarker2, new ExpectActualAnnotationsIncompatibilityType.MissingOnActual(annotationCallInfo));
                }
                ExpectActualCollectionArgumentsCompatibilityCheckStrategy annotationCollectionArgumentsCompatibilityChecker = getAnnotationCollectionArgumentsCompatibilityChecker(classId2);
                List list = listEmptyList;
                if (!(list instanceof Collection) || !list.isEmpty()) {
                    Iterator it = list.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            if (expectActualMatchingContext.areAnnotationArgumentsEqual(annotationCallInfo, (ExpectActualMatchingContext.AnnotationCallInfo) it.next(), annotationCollectionArgumentsCompatibilityChecker)) {
                            }
                        }
                    }
                }
                if (listEmptyList.size() == 1) {
                    missingOnActual = new ExpectActualAnnotationsIncompatibilityType.DifferentOnActual(annotationCallInfo, CollectionsKt.single(listEmptyList));
                } else {
                    missingOnActual = new ExpectActualAnnotationsIncompatibilityType.MissingOnActual(annotationCallInfo);
                }
                return new Incompatibility(declarationSymbolMarker, declarationSymbolMarker2, missingOnActual);
            }
        }
        return null;
    }

    private final ExpectActualCollectionArgumentsCompatibilityCheckStrategy getAnnotationCollectionArgumentsCompatibilityChecker(ClassId annotationClassId) {
        if (Intrinsics.areEqual(annotationClassId, StandardClassIds.Annotations.INSTANCE.getTarget())) {
            return ExpectActualCollectionArgumentsCompatibilityCheckStrategy.ExpectIsSubsetOfActual.INSTANCE;
        }
        return ExpectActualCollectionArgumentsCompatibilityCheckStrategy.Default.INSTANCE;
    }
}
