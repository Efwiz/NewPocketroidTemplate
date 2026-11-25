package org.jetbrains.kotlin.resolve.deprecation;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;

/* JADX INFO: compiled from: Deprecation.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0080\b\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003¢\u0006\u0002\u0010\u0004J\r\u0010\u0015\u001a\u00020\u000eH\u0000¢\u0006\u0002\b\u0016J\u000f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003HÂ\u0003J\u0019\u0010\u0018\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003HÆ\u0001J\u0013\u0010\u0019\u001a\u00020\n2\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bHÖ\u0003J\t\u0010\u001c\u001a\u00020\u001dHÖ\u0001J\t\u0010\u001e\u001a\u00020\u000eHÖ\u0001R\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\u00020\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0011\u001a\u00020\u00128VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/deprecation/DeprecatedByOverridden;", "Lorg/jetbrains/kotlin/resolve/deprecation/DescriptorBasedDeprecationInfo;", "deprecations", "", "(Ljava/util/Collection;)V", "deprecationLevel", "Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationLevelValue;", "getDeprecationLevel", "()Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationLevelValue;", "forcePropagationToOverrides", "", "getForcePropagationToOverrides", "()Z", CrashHianalyticsData.MESSAGE, "", "getMessage", "()Ljava/lang/String;", "target", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "getTarget", "()Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "additionalMessage", "additionalMessage$frontend", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "", "hashCode", "", "toString", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class DeprecatedByOverridden extends DescriptorBasedDeprecationInfo {
    private final DeprecationLevelValue deprecationLevel;
    private final Collection<DescriptorBasedDeprecationInfo> deprecations;
    private final boolean forcePropagationToOverrides;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ DeprecatedByOverridden copy$default(DeprecatedByOverridden deprecatedByOverridden, Collection collection, int i, Object obj) {
        if ((i & 1) != 0) {
            collection = deprecatedByOverridden.deprecations;
        }
        return deprecatedByOverridden.copy(collection);
    }

    public final DeprecatedByOverridden copy(Collection<? extends DescriptorBasedDeprecationInfo> deprecations) {
        Intrinsics.checkNotNullParameter(deprecations, "deprecations");
        return new DeprecatedByOverridden(deprecations);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof DeprecatedByOverridden) && Intrinsics.areEqual(this.deprecations, ((DeprecatedByOverridden) other).deprecations);
    }

    public int hashCode() {
        return this.deprecations.hashCode();
    }

    public String toString() {
        return "DeprecatedByOverridden(deprecations=" + this.deprecations + ')';
    }

    /* JADX WARN: Multi-variable type inference failed */
    public DeprecatedByOverridden(Collection<? extends DescriptorBasedDeprecationInfo> deprecations) {
        Intrinsics.checkNotNullParameter(deprecations, "deprecations");
        this.deprecations = deprecations;
        deprecations.isEmpty();
        Collection<? extends DescriptorBasedDeprecationInfo> collection = deprecations;
        if (!(collection instanceof Collection) || !collection.isEmpty()) {
            Iterator it = collection.iterator();
            while (it.hasNext() && !(((DescriptorBasedDeprecationInfo) it.next()) instanceof DeprecatedByOverridden)) {
            }
        }
        Collection<DescriptorBasedDeprecationInfo> collection2 = this.deprecations;
        boolean z = false;
        if (!(collection2 instanceof Collection) || !collection2.isEmpty()) {
            Iterator<T> it2 = collection2.iterator();
            while (it2.hasNext()) {
                if (((DescriptorBasedDeprecationInfo) it2.next()).getForcePropagationToOverrides()) {
                    z = true;
                    break;
                }
            }
        }
        this.forcePropagationToOverrides = z;
        Collection<DescriptorBasedDeprecationInfo> collection3 = this.deprecations;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection3, 10));
        Iterator<T> it3 = collection3.iterator();
        while (it3.hasNext()) {
            arrayList.add(((DescriptorBasedDeprecationInfo) it3.next()).getDeprecationLevel());
        }
        Comparable comparableMinOrNull = CollectionsKt.minOrNull((Iterable<? extends Comparable>) arrayList);
        Intrinsics.checkNotNull(comparableMinOrNull);
        this.deprecationLevel = (DeprecationLevelValue) comparableMinOrNull;
    }

    @Override // org.jetbrains.kotlin.resolve.deprecation.DescriptorBasedDeprecationInfo
    public boolean getForcePropagationToOverrides() {
        return this.forcePropagationToOverrides;
    }

    @Override // org.jetbrains.kotlin.resolve.deprecation.DeprecationInfo
    public DeprecationLevelValue getDeprecationLevel() {
        return this.deprecationLevel;
    }

    @Override // org.jetbrains.kotlin.resolve.deprecation.DescriptorBasedDeprecationInfo
    public DeclarationDescriptor getTarget() {
        return ((DescriptorBasedDeprecationInfo) CollectionsKt.first(this.deprecations)).getTarget();
    }

    @Override // org.jetbrains.kotlin.resolve.deprecation.DeprecationInfo
    public String getMessage() {
        Collection<DescriptorBasedDeprecationInfo> collection = this.deprecations;
        ArrayList arrayList = new ArrayList();
        for (Object obj : collection) {
            if (((DescriptorBasedDeprecationInfo) obj).getDeprecationLevel() == getDeprecationLevel()) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            arrayList3.add(((DescriptorBasedDeprecationInfo) it.next()).getMessage());
        }
        return additionalMessage$frontend() + ". " + CollectionsKt.joinToString$default(CollectionsKt.toSet(arrayList3), ". ", null, null, 0, null, null, 62, null);
    }

    public final String additionalMessage$frontend() {
        StringBuilder sb = new StringBuilder("Overrides deprecated member in '");
        ClassDescriptor containingClass = DescriptorUtils.getContainingClass(getTarget());
        Intrinsics.checkNotNull(containingClass);
        return sb.append(DescriptorUtilsKt.getFqNameSafe(containingClass).asString()).append('\'').toString();
    }
}
