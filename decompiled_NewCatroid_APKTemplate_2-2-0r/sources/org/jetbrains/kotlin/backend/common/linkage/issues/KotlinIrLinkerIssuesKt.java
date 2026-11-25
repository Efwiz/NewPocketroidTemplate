package org.jetbrains.kotlin.backend.common.linkage.issues;

import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.text.StringsKt;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.utils.ResolvedDependency;
import org.jetbrains.kotlin.utils.ResolvedDependencyId;
import org.jetbrains.kotlin.utils.ResolvedDependencyVersion;

/* JADX INFO: compiled from: KotlinIrLinkerIssues.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000h\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a<\u0010\u0002\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\b\u0012\u00060\u0001j\u0002`\u00050\u00032\u0006\u0010\u0006\u001a\u00020\u00042\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\b0\u00032\u0006\u0010\t\u001a\u00020\u0004H\u0002\u001a4\u0010\n\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\b\u0012\u00060\u0001j\u0002`\u00050\u00032\u0006\u0010\u0006\u001a\u00020\u00042\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\b0\u0003H\u0002\u001a\\\u0010\u000b\u001a\u00020\f*\u00060\rj\u0002`\u000e2\u0006\u0010\u000f\u001a\u00020\u00012\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\b0\u00032\u0016\u0010\u0010\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\b\u0012\u00060\u0001j\u0002`\u00050\u00032\u0016\u0010\u0011\u001a\u0012\u0012\u0004\u0012\u00020\u00040\u0012j\b\u0012\u0004\u0012\u00020\u0004`\u0013H\u0002\u001aZ\u0010\u0014\u001a\u00020\f*\u00060\rj\u0002`\u000e2\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\b0\u00032\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00040\u00162\u0006\u0010\u0017\u001a\u00020\u00012\u0006\u0010\t\u001a\u00020\u00042\u0016\u0010\u0011\u001a\u0012\u0012\u0004\u0012\u00020\u00040\u0012j\b\u0012\u0004\u0012\u00020\u0004`\u0013H\u0002\u001aF\u0010\u0018\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\b\u0012\u00060\u0001j\u0002`\u00050\u0003*\u0014\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001a0\u00190\u00032\u0016\u0010\u001b\u001a\u0012\u0012\u0004\u0012\u00020\u001d\u0012\b\u0012\u00060\u0001j\u0002`\u00050\u001cH\u0002\u001a \u0010\u001e\u001a\u00020\b*\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\b0\u00032\u0006\u0010\u001f\u001a\u00020\u0004H\u0002\u001a(\u0010 \u001a\u00020\u0004*\u00020!2\u0006\u0010\"\u001a\u00020#2\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\b0\u0003H\u0002\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000*\f\b\u0002\u0010$\"\u00020\u00012\u00020\u0001¨\u0006%"}, d2 = {"UNKNOWN_VERSION", "", "findPotentiallyConflictingIncomingDependencies", "", "Lorg/jetbrains/kotlin/utils/ResolvedDependencyId;", "Lorg/jetbrains/kotlin/backend/common/linkage/issues/PotentialConflictDescription;", "problemModuleId", "allModules", "Lorg/jetbrains/kotlin/utils/ResolvedDependency;", "sourceCodeModuleId", "findPotentiallyConflictingOutgoingDependencies", "appendPotentiallyConflictingDependencies", "", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "header", "potentiallyConflictingDependencies", "moduleIdComparator", "Ljava/util/Comparator;", "Lkotlin/Comparator;", "appendProjectDependencies", "problemModuleIds", "", "problemCause", "describeDependencyStates", "", "Lorg/jetbrains/kotlin/backend/common/linkage/issues/DependencyState;", "getDescription", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/backend/common/linkage/issues/PotentialConflictReason;", "findMatchingModule", "moduleId", "getProblemModuleId", "Lorg/jetbrains/kotlin/backend/common/linkage/issues/UserVisibleIrModulesSupport;", "problemModuleDeserializer", "Lorg/jetbrains/kotlin/backend/common/serialization/IrModuleDeserializer;", "PotentialConflictDescription", "ir.serialization.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class KotlinIrLinkerIssuesKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final ResolvedDependencyId getProblemModuleId(UserVisibleIrModulesSupport userVisibleIrModulesSupport, IrModuleDeserializer irModuleDeserializer, Map<ResolvedDependencyId, ResolvedDependency> map) {
        return findMatchingModule(map, userVisibleIrModulesSupport.getUserVisibleModuleId(irModuleDeserializer)).getId();
    }

    private static final ResolvedDependency findMatchingModule(Map<ResolvedDependencyId, ResolvedDependency> map, ResolvedDependencyId resolvedDependencyId) {
        ResolvedDependency resolvedDependency = map.get(resolvedDependencyId);
        if (resolvedDependency != null) {
            return resolvedDependency;
        }
        for (ResolvedDependency resolvedDependency2 : map.values()) {
            if (resolvedDependency2.getId().contains(resolvedDependencyId)) {
                return resolvedDependency2;
            }
        }
        throw new NoSuchElementException("Collection contains no element matching the predicate.");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void appendProjectDependencies(StringBuilder sb, Map<ResolvedDependencyId, ResolvedDependency> map, Set<ResolvedDependencyId> set, String str, ResolvedDependencyId resolvedDependencyId, Comparator<ResolvedDependencyId> comparator) {
        sb.append("\n\nProject dependencies:");
        if (map.isEmpty()) {
            sb.append(" <empty>");
            return;
        }
        HashMap map2 = new HashMap();
        for (ResolvedDependency resolvedDependency : map.values()) {
            for (ResolvedDependencyId resolvedDependencyId2 : resolvedDependency.getRequestedVersionsByIncomingDependencies().keySet()) {
                Object obj = map2.get(resolvedDependencyId2);
                if (obj == null) {
                    obj = (Collection) ((List) new ArrayList());
                    map2.put(resolvedDependencyId2, obj);
                }
                ((Collection) obj).add(resolvedDependency);
            }
        }
        HashSet hashSet = new HashSet();
        Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        appendProjectDependencies$renderModules(comparator, sb, resolvedDependencyId, hashSet, map2, booleanRef, set, str, (Collection) MapsKt.getValue(map2, resolvedDependencyId), null);
        if (booleanRef.element) {
            sb.append("\n\n(*) - dependencies omitted (listed previously)");
        }
    }

    private static final void appendProjectDependencies$renderModules(final Comparator<ResolvedDependencyId> comparator, StringBuilder sb, ResolvedDependencyId resolvedDependencyId, Set<ResolvedDependencyId> set, Map<ResolvedDependencyId, Collection<ResolvedDependency>> map, Ref.BooleanRef booleanRef, Set<ResolvedDependencyId> set2, String str, Collection<ResolvedDependency> collection, Data data) {
        ArrayList arrayList;
        ResolvedDependencyId incomingDependencyId;
        if (data == null) {
            ArrayList arrayList2 = new ArrayList();
            for (Object obj : collection) {
                if (((ResolvedDependency) obj).getVisibleAsFirstLevelDependency()) {
                    arrayList2.add(obj);
                }
            }
            arrayList = arrayList2;
        } else {
            arrayList = collection;
        }
        final Function2<ResolvedDependency, ResolvedDependency, Integer> function2 = new Function2<ResolvedDependency, ResolvedDependency, Integer>() { // from class: org.jetbrains.kotlin.backend.common.linkage.issues.KotlinIrLinkerIssuesKt$appendProjectDependencies$renderModules$sortedModules$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Integer invoke(ResolvedDependency resolvedDependency, ResolvedDependency resolvedDependency2) {
                return Integer.valueOf(comparator.compare(resolvedDependency.getId(), resolvedDependency2.getId()));
            }
        };
        List listSortedWith = CollectionsKt.sortedWith(arrayList, new Comparator() { // from class: org.jetbrains.kotlin.backend.common.linkage.issues.KotlinIrLinkerIssuesKt$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(Object obj2, Object obj3) {
                return KotlinIrLinkerIssuesKt.appendProjectDependencies$renderModules$lambda$6(function2, obj2, obj3);
            }
        });
        int i = 0;
        for (Object obj2 : listSortedWith) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            ResolvedDependency resolvedDependency = (ResolvedDependency) obj2;
            boolean z = true;
            Data data2 = new Data(data, resolvedDependency.getId(), i2 == listSortedWith.size());
            sb.append('\n').append(data2.getRegularLinePrefix());
            sb.append(resolvedDependency.getId());
            if (data == null || (incomingDependencyId = data.getIncomingDependencyId()) == null) {
                incomingDependencyId = resolvedDependencyId;
            }
            ResolvedDependencyVersion resolvedDependencyVersion = (ResolvedDependencyVersion) MapsKt.getValue(resolvedDependency.getRequestedVersionsByIncomingDependencies(), incomingDependencyId);
            if (!resolvedDependencyVersion.isEmpty() || !resolvedDependency.getSelectedVersion().isEmpty()) {
                sb.append(": ");
                String version = resolvedDependencyVersion.getVersion();
                if (version.length() == 0) {
                    version = "unknown";
                }
                sb.append(version);
                if (!Intrinsics.areEqual(resolvedDependencyVersion, resolvedDependency.getSelectedVersion())) {
                    sb.append(" -> ");
                    String version2 = resolvedDependency.getSelectedVersion().getVersion();
                    sb.append(version2.length() != 0 ? version2 : "unknown");
                }
            }
            boolean zAdd = set.add(resolvedDependency.getId());
            Collection<ResolvedDependency> collection2 = map.get(resolvedDependency.getId());
            if (!zAdd) {
                if (collection2 != null && !collection2.isEmpty()) {
                    booleanRef.element = true;
                    sb.append(" (*)");
                }
                z = false;
            }
            if (set2.contains(resolvedDependency.getId())) {
                sb.append('\n').append(data2.getErrorLinePrefix());
                sb.append("^^^ " + str + '.');
            }
            if (z && collection2 != null && !collection2.isEmpty()) {
                appendProjectDependencies$renderModules(comparator, sb, resolvedDependencyId, set, map, booleanRef, set2, str, collection2, data2);
            }
            i = i2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int appendProjectDependencies$renderModules$lambda$6(Function2 tmp0, Object obj, Object obj2) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return ((Number) tmp0.invoke(obj, obj2)).intValue();
    }

    /* JADX INFO: compiled from: KotlinIrLinkerIssues.kt */
    @Metadata(d1 = {"\u0000-\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000*\u0001\u0000\b\u008a\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000e\u001a\u00020\u0005HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0005HÆ\u0003J,\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0005HÆ\u0001¢\u0006\u0002\u0010\u0011J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000b¨\u0006\u0019"}, d2 = {"org/jetbrains/kotlin/backend/common/linkage/issues/KotlinIrLinkerIssuesKt$findPotentiallyConflictingOutgoingDependencies$OutgoingDependency", "", "id", "Lorg/jetbrains/kotlin/utils/ResolvedDependencyId;", "requestedVersion", "Lorg/jetbrains/kotlin/utils/ResolvedDependencyVersion;", "selectedVersion", "(Lorg/jetbrains/kotlin/utils/ResolvedDependencyId;Lorg/jetbrains/kotlin/utils/ResolvedDependencyVersion;Lorg/jetbrains/kotlin/utils/ResolvedDependencyVersion;)V", "getId", "()Lorg/jetbrains/kotlin/utils/ResolvedDependencyId;", "getRequestedVersion", "()Lorg/jetbrains/kotlin/utils/ResolvedDependencyVersion;", "getSelectedVersion", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "(Lorg/jetbrains/kotlin/utils/ResolvedDependencyId;Lorg/jetbrains/kotlin/utils/ResolvedDependencyVersion;Lorg/jetbrains/kotlin/utils/ResolvedDependencyVersion;)Lorg/jetbrains/kotlin/backend/common/linkage/issues/KotlinIrLinkerIssuesKt$findPotentiallyConflictingOutgoingDependencies$OutgoingDependency;", Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class OutgoingDependency {
        private final ResolvedDependencyId id;
        private final ResolvedDependencyVersion requestedVersion;
        private final ResolvedDependencyVersion selectedVersion;

        public static /* synthetic */ OutgoingDependency copy$default(OutgoingDependency outgoingDependency, ResolvedDependencyId resolvedDependencyId, ResolvedDependencyVersion resolvedDependencyVersion, ResolvedDependencyVersion resolvedDependencyVersion2, int i, Object obj) {
            if ((i & 1) != 0) {
                resolvedDependencyId = outgoingDependency.id;
            }
            if ((i & 2) != 0) {
                resolvedDependencyVersion = outgoingDependency.requestedVersion;
            }
            if ((i & 4) != 0) {
                resolvedDependencyVersion2 = outgoingDependency.selectedVersion;
            }
            return outgoingDependency.copy(resolvedDependencyId, resolvedDependencyVersion, resolvedDependencyVersion2);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final ResolvedDependencyId getId() {
            return this.id;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final ResolvedDependencyVersion getRequestedVersion() {
            return this.requestedVersion;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final ResolvedDependencyVersion getSelectedVersion() {
            return this.selectedVersion;
        }

        public final OutgoingDependency copy(ResolvedDependencyId id2, ResolvedDependencyVersion requestedVersion, ResolvedDependencyVersion selectedVersion) {
            Intrinsics.checkNotNullParameter(id2, "id");
            Intrinsics.checkNotNullParameter(requestedVersion, "requestedVersion");
            Intrinsics.checkNotNullParameter(selectedVersion, "selectedVersion");
            return new OutgoingDependency(id2, requestedVersion, selectedVersion);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof OutgoingDependency)) {
                return false;
            }
            OutgoingDependency outgoingDependency = (OutgoingDependency) other;
            return Intrinsics.areEqual(this.id, outgoingDependency.id) && Intrinsics.areEqual(this.requestedVersion, outgoingDependency.requestedVersion) && Intrinsics.areEqual(this.selectedVersion, outgoingDependency.selectedVersion);
        }

        public int hashCode() {
            return (((this.id.hashCode() * 31) + this.requestedVersion.hashCode()) * 31) + this.selectedVersion.hashCode();
        }

        public String toString() {
            return "OutgoingDependency(id=" + this.id + ", requestedVersion=" + this.requestedVersion + ", selectedVersion=" + this.selectedVersion + ')';
        }

        public OutgoingDependency(ResolvedDependencyId id2, ResolvedDependencyVersion requestedVersion, ResolvedDependencyVersion selectedVersion) {
            Intrinsics.checkNotNullParameter(id2, "id");
            Intrinsics.checkNotNullParameter(requestedVersion, "requestedVersion");
            Intrinsics.checkNotNullParameter(selectedVersion, "selectedVersion");
            this.id = id2;
            this.requestedVersion = requestedVersion;
            this.selectedVersion = selectedVersion;
        }

        public final ResolvedDependencyId getId() {
            return this.id;
        }

        public final ResolvedDependencyVersion getRequestedVersion() {
            return this.requestedVersion;
        }

        public final ResolvedDependencyVersion getSelectedVersion() {
            return this.selectedVersion;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Map<ResolvedDependencyId, String> findPotentiallyConflictingOutgoingDependencies(ResolvedDependencyId resolvedDependencyId, Map<ResolvedDependencyId, ResolvedDependency> map) {
        HashMap map2 = new HashMap();
        for (ResolvedDependency resolvedDependency : map.values()) {
            for (Map.Entry<ResolvedDependencyId, ResolvedDependencyVersion> entry : resolvedDependency.getRequestedVersionsByIncomingDependencies().entrySet()) {
                ResolvedDependencyId key = entry.getKey();
                ResolvedDependencyVersion value = entry.getValue();
                Object obj = map2.get(key);
                if (obj == null) {
                    obj = (List) new ArrayList();
                    map2.put(key, obj);
                }
                ((Collection) obj).add(new OutgoingDependency(resolvedDependency.getId(), value, resolvedDependency.getSelectedVersion()));
            }
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        findPotentiallyConflictingOutgoingDependencies$recurse(map2, linkedHashMap, resolvedDependencyId, false);
        return describeDependencyStates(linkedHashMap, new Function1<PotentialConflictReason, String>() { // from class: org.jetbrains.kotlin.backend.common.linkage.issues.KotlinIrLinkerIssuesKt.findPotentiallyConflictingOutgoingDependencies.2

            /* JADX INFO: renamed from: org.jetbrains.kotlin.backend.common.linkage.issues.KotlinIrLinkerIssuesKt$findPotentiallyConflictingOutgoingDependencies$2$WhenMappings */
            /* JADX INFO: compiled from: KotlinIrLinkerIssues.kt */
            @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
            public /* synthetic */ class WhenMappings {
                public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                static {
                    int[] iArr = new int[PotentialConflictKind.values().length];
                    try {
                        iArr[PotentialConflictKind.UNKNOWN_SELECTED_VERSION.ordinal()] = 1;
                    } catch (NoSuchFieldError unused) {
                    }
                    try {
                        iArr[PotentialConflictKind.REQUESTED_SELECTED_VERSIONS_MISMATCH.ordinal()] = 2;
                    } catch (NoSuchFieldError unused2) {
                    }
                    try {
                        iArr[PotentialConflictKind.BEHIND_CONFLICTING_DEPENDENCY.ordinal()] = 3;
                    } catch (NoSuchFieldError unused3) {
                    }
                    $EnumSwitchMapping$0 = iArr;
                }
            }

            @Override // kotlin.jvm.functions.Function1
            public final String invoke(PotentialConflictReason potentialConflictReason) {
                Intrinsics.checkNotNullParameter(potentialConflictReason, "potentialConflictReason");
                int i = WhenMappings.$EnumSwitchMapping$0[potentialConflictReason.getKind().ordinal()];
                if (i == 1) {
                    return "a library with unknown version";
                }
                if (i == 2) {
                    return "was initially compiled with \"" + potentialConflictReason.getConflictingModuleId().withVersion(potentialConflictReason.getRequestedVersion()) + '\"';
                }
                if (i == 3) {
                    return "a dependency of the library with unknown version or versions mismatch: \"" + potentialConflictReason.getConflictingModuleId() + '\"';
                }
                throw new NoWhenBranchMatchedException();
            }
        });
    }

    private static final void findPotentiallyConflictingOutgoingDependencies$recurse(Map<ResolvedDependencyId, List<OutgoingDependency>> map, Map<ResolvedDependencyId, Set<DependencyState>> map2, ResolvedDependencyId resolvedDependencyId, boolean z) {
        DependencyState success;
        List<OutgoingDependency> listEmptyList = map.get(resolvedDependencyId);
        if (listEmptyList == null) {
            listEmptyList = CollectionsKt.emptyList();
        }
        for (OutgoingDependency outgoingDependency : listEmptyList) {
            if (z) {
                success = new DependencyState(new PotentialConflictReason(PotentialConflictKind.BEHIND_CONFLICTING_DEPENDENCY, outgoingDependency.getId(), null, null, 12, null));
            } else if (outgoingDependency.getSelectedVersion().isEmpty()) {
                success = new DependencyState(new PotentialConflictReason(PotentialConflictKind.UNKNOWN_SELECTED_VERSION, outgoingDependency.getId(), outgoingDependency.getRequestedVersion(), null, 8, null));
            } else if (!Intrinsics.areEqual(outgoingDependency.getRequestedVersion(), outgoingDependency.getSelectedVersion())) {
                success = new DependencyState(new PotentialConflictReason(PotentialConflictKind.REQUESTED_SELECTED_VERSIONS_MISMATCH, outgoingDependency.getId(), outgoingDependency.getRequestedVersion(), outgoingDependency.getSelectedVersion()));
            } else {
                success = DependencyState.INSTANCE.getSUCCESS();
            }
            ResolvedDependencyId id2 = outgoingDependency.getId();
            LinkedHashSet linkedHashSet = map2.get(id2);
            if (linkedHashSet == null) {
                linkedHashSet = new LinkedHashSet();
                map2.put(id2, linkedHashSet);
            }
            if (linkedHashSet.add(success)) {
                findPotentiallyConflictingOutgoingDependencies$recurse(map, map2, outgoingDependency.getId(), success.getConflictReason() != null);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Map<ResolvedDependencyId, String> findPotentiallyConflictingIncomingDependencies(ResolvedDependencyId resolvedDependencyId, Map<ResolvedDependencyId, ResolvedDependency> map, ResolvedDependencyId resolvedDependencyId2) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        findPotentiallyConflictingIncomingDependencies$recurse$17(map, resolvedDependencyId2, linkedHashMap, resolvedDependencyId, false);
        return describeDependencyStates(linkedHashMap, new Function1<PotentialConflictReason, String>() { // from class: org.jetbrains.kotlin.backend.common.linkage.issues.KotlinIrLinkerIssuesKt.findPotentiallyConflictingIncomingDependencies.1

            /* JADX INFO: renamed from: org.jetbrains.kotlin.backend.common.linkage.issues.KotlinIrLinkerIssuesKt$findPotentiallyConflictingIncomingDependencies$1$WhenMappings */
            /* JADX INFO: compiled from: KotlinIrLinkerIssues.kt */
            @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
            public /* synthetic */ class WhenMappings {
                public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                static {
                    int[] iArr = new int[PotentialConflictKind.values().length];
                    try {
                        iArr[PotentialConflictKind.UNKNOWN_SELECTED_VERSION.ordinal()] = 1;
                    } catch (NoSuchFieldError unused) {
                    }
                    try {
                        iArr[PotentialConflictKind.REQUESTED_SELECTED_VERSIONS_MISMATCH.ordinal()] = 2;
                    } catch (NoSuchFieldError unused2) {
                    }
                    try {
                        iArr[PotentialConflictKind.BEHIND_CONFLICTING_DEPENDENCY.ordinal()] = 3;
                    } catch (NoSuchFieldError unused3) {
                    }
                    $EnumSwitchMapping$0 = iArr;
                }
            }

            @Override // kotlin.jvm.functions.Function1
            public final String invoke(PotentialConflictReason potentialConflictReason) {
                Intrinsics.checkNotNullParameter(potentialConflictReason, "potentialConflictReason");
                int i = WhenMappings.$EnumSwitchMapping$0[potentialConflictReason.getKind().ordinal()];
                if (i == 1) {
                    return "depends on the library with unknown version: \"" + potentialConflictReason.getConflictingModuleId() + '\"';
                }
                if (i != 2) {
                    if (i == 3) {
                        return "depends on the library with unknown version or versions mismatch: \"" + potentialConflictReason.getConflictingModuleId() + '\"';
                    }
                    throw new NoWhenBranchMatchedException();
                }
                return "was compiled against \"" + potentialConflictReason.getConflictingModuleId().withVersion(potentialConflictReason.getRequestedVersion()) + "\" but \"" + potentialConflictReason.getConflictingModuleId().withVersion(potentialConflictReason.getSelectedVersion()) + "\" is used in the project";
            }
        });
    }

    private static final void findPotentiallyConflictingIncomingDependencies$recurse$17(Map<ResolvedDependencyId, ResolvedDependency> map, ResolvedDependencyId resolvedDependencyId, Map<ResolvedDependencyId, Set<DependencyState>> map2, ResolvedDependencyId resolvedDependencyId2, boolean z) {
        DependencyState success;
        ResolvedDependency resolvedDependencyFindMatchingModule = findMatchingModule(map, resolvedDependencyId2);
        for (Map.Entry<ResolvedDependencyId, ResolvedDependencyVersion> entry : resolvedDependencyFindMatchingModule.getRequestedVersionsByIncomingDependencies().entrySet()) {
            ResolvedDependencyId key = entry.getKey();
            ResolvedDependencyVersion value = entry.getValue();
            if (!Intrinsics.areEqual(key, resolvedDependencyId)) {
                if (z) {
                    success = new DependencyState(new PotentialConflictReason(PotentialConflictKind.BEHIND_CONFLICTING_DEPENDENCY, resolvedDependencyFindMatchingModule.getId(), null, null, 12, null));
                } else if (resolvedDependencyFindMatchingModule.getSelectedVersion().isEmpty()) {
                    success = new DependencyState(new PotentialConflictReason(PotentialConflictKind.UNKNOWN_SELECTED_VERSION, resolvedDependencyFindMatchingModule.getId(), value, null, 8, null));
                } else if (!Intrinsics.areEqual(value, resolvedDependencyFindMatchingModule.getSelectedVersion())) {
                    success = new DependencyState(new PotentialConflictReason(PotentialConflictKind.REQUESTED_SELECTED_VERSIONS_MISMATCH, resolvedDependencyFindMatchingModule.getId(), value, resolvedDependencyFindMatchingModule.getSelectedVersion()));
                } else {
                    success = DependencyState.INSTANCE.getSUCCESS();
                }
                LinkedHashSet linkedHashSet = map2.get(key);
                if (linkedHashSet == null) {
                    linkedHashSet = new LinkedHashSet();
                    map2.put(key, linkedHashSet);
                }
                if (linkedHashSet.add(success)) {
                    findPotentiallyConflictingIncomingDependencies$recurse$17(map, resolvedDependencyId, map2, key, success.isConflicting());
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void appendPotentiallyConflictingDependencies(StringBuilder sb, String str, Map<ResolvedDependencyId, ResolvedDependency> map, Map<ResolvedDependencyId, String> map2, Comparator<ResolvedDependencyId> comparator) {
        if (map2.isEmpty()) {
            return;
        }
        sb.append("\n\n" + str);
        int length = String.valueOf(map2.size() + 1).length();
        Set setEntrySet = MapsKt.toSortedMap(map2, comparator).entrySet();
        Intrinsics.checkNotNullExpressionValue(setEntrySet, "<get-entries>(...)");
        int i = 0;
        for (Object obj : setEntrySet) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            Map.Entry entry = (Map.Entry) obj;
            Intrinsics.checkNotNull(entry);
            ResolvedDependencyId resolvedDependencyId = (ResolvedDependencyId) entry.getKey();
            String str2 = (String) entry.getValue();
            String strPadStart = StringsKt.padStart(String.valueOf(i2), length, StringUtil.SPACE);
            Intrinsics.checkNotNull(resolvedDependencyId);
            sb.append("\n" + strPadStart + ". \"" + ((ResolvedDependency) MapsKt.getValue(map, resolvedDependencyId)).getModuleIdWithVersion() + "\" (" + str2 + ')');
            i = i2;
        }
    }

    private static final Map<ResolvedDependencyId, String> describeDependencyStates(Map<ResolvedDependencyId, ? extends Set<DependencyState>> map, final Function1<? super PotentialConflictReason, String> function1) {
        ArrayList arrayList = new ArrayList();
        for (Map.Entry<ResolvedDependencyId, ? extends Set<DependencyState>> entry : map.entrySet()) {
            ResolvedDependencyId key = entry.getKey();
            Set<DependencyState> value = entry.getValue();
            PotentialConflictReason.Companion companion = PotentialConflictReason.INSTANCE;
            Set<DependencyState> set = value;
            ArrayList arrayList2 = new ArrayList();
            Iterator<T> it = set.iterator();
            while (it.hasNext()) {
                PotentialConflictReason conflictReason = ((DependencyState) it.next()).getConflictReason();
                if (conflictReason != null) {
                    arrayList2.add(conflictReason);
                }
            }
            Collection<PotentialConflictReason> mostSignificantConflictReasons = companion.getMostSignificantConflictReasons(arrayList2);
            Pair pair = null;
            if (!mostSignificantConflictReasons.isEmpty()) {
                Collection<PotentialConflictReason> collection = mostSignificantConflictReasons;
                if (((PotentialConflictReason) CollectionsKt.first(collection)).getKind() == PotentialConflictKind.BEHIND_CONFLICTING_DEPENDENCY && (!(set instanceof Collection) || !set.isEmpty())) {
                    Iterator<T> it2 = set.iterator();
                    while (true) {
                        if (it2.hasNext()) {
                            if (!((DependencyState) it2.next()).isConflicting()) {
                            }
                        }
                    }
                }
                pair = TuplesKt.to(key, CollectionsKt.joinToString$default(collection, "; ", null, null, 0, null, new Function1<PotentialConflictReason, CharSequence>() { // from class: org.jetbrains.kotlin.backend.common.linkage.issues.KotlinIrLinkerIssuesKt$describeDependencyStates$1$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    /* JADX WARN: Multi-variable type inference failed */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final CharSequence invoke(PotentialConflictReason it3) {
                        Intrinsics.checkNotNullParameter(it3, "it");
                        return function1.invoke(it3);
                    }
                }, 30, null));
            }
            if (pair != null) {
                arrayList.add(pair);
            }
        }
        return MapsKt.toMap(arrayList);
    }
}
