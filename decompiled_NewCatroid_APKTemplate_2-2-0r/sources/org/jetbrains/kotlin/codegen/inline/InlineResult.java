package org.jetbrains.kotlin.codegen.inline;

import com.reandroid.dex.sections.Marker;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;

/* JADX INFO: compiled from: InlineResult.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\"\n\u0000\n\u0002\u0010$\n\u0002\b\u0005\u0018\u0000 \u001c2\u00020\u0001:\u0001\u001cB\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00052\u0006\u0010\u0011\u001a\u00020\u0005J\u000e\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u0005J\u000e\u0010\u0014\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u0005J\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00050\u0016J\u0012\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0018J\u000e\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u0000J\u000e\u0010\u001b\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u0000R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\b\u001a\u0012\u0012\u0004\u0012\u00020\u00050\u0007j\b\u0012\u0004\u0012\u00020\u0005`\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/InlineResult;", "", "()V", "changedTypes", "Ljava/util/HashMap;", "", "classesToRemove", "Ljava/util/HashSet;", "notChangedTypes", "Lkotlin/collections/HashSet;", "reifiedTypeParametersUsages", "Lorg/jetbrains/kotlin/codegen/inline/ReifiedTypeParametersUsages;", "getReifiedTypeParametersUsages", "()Lorg/jetbrains/kotlin/codegen/inline/ReifiedTypeParametersUsages;", "addChangedType", "", "oldClassInternalName", "newClassInternalName", "addClassToRemove", "classInternalName", "addNotChangedClass", "calcClassesToRemove", "", "getChangedTypes", "", "merge", "child", "mergeWithNotChangeInfo", "Companion", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class InlineResult {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final HashMap<String, String> changedTypes;
    private final HashSet<String> classesToRemove;
    private final HashSet<String> notChangedTypes;
    private final ReifiedTypeParametersUsages reifiedTypeParametersUsages;

    public /* synthetic */ InlineResult(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    @JvmStatic
    public static final InlineResult create() {
        return INSTANCE.create();
    }

    private InlineResult() {
        this.notChangedTypes = new HashSet<>();
        this.classesToRemove = new HashSet<>();
        this.changedTypes = new HashMap<>();
        this.reifiedTypeParametersUsages = new ReifiedTypeParametersUsages();
    }

    public final ReifiedTypeParametersUsages getReifiedTypeParametersUsages() {
        return this.reifiedTypeParametersUsages;
    }

    public final void merge(InlineResult child) {
        Intrinsics.checkNotNullParameter(child, "child");
        this.classesToRemove.addAll(child.calcClassesToRemove());
    }

    public final void mergeWithNotChangeInfo(InlineResult child) {
        Intrinsics.checkNotNullParameter(child, "child");
        this.notChangedTypes.addAll(child.notChangedTypes);
        merge(child);
    }

    public final void addClassToRemove(String classInternalName) {
        Intrinsics.checkNotNullParameter(classInternalName, "classInternalName");
        this.classesToRemove.add(classInternalName);
    }

    public final void addNotChangedClass(String classInternalName) {
        Intrinsics.checkNotNullParameter(classInternalName, "classInternalName");
        this.notChangedTypes.add(classInternalName);
    }

    public final void addChangedType(String oldClassInternalName, String newClassInternalName) {
        Intrinsics.checkNotNullParameter(oldClassInternalName, "oldClassInternalName");
        Intrinsics.checkNotNullParameter(newClassInternalName, "newClassInternalName");
        this.changedTypes.put(oldClassInternalName, newClassInternalName);
    }

    public final Set<String> calcClassesToRemove() {
        return SetsKt.minus((Set) this.classesToRemove, (Iterable) this.notChangedTypes);
    }

    public final Map<String, String> getChangedTypes() {
        return this.changedTypes;
    }

    /* JADX INFO: compiled from: InlineResult.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0007¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/InlineResult$Companion;", "", "()V", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/codegen/inline/InlineResult;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final InlineResult create() {
            return new InlineResult(null);
        }
    }
}
