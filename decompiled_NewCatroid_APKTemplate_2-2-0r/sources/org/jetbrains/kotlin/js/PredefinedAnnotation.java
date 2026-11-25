package org.jetbrains.kotlin.js;

import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.common.Constants;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.JsStandardClassIds;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r0v0 org.jetbrains.kotlin.js.PredefinedAnnotation, still in use, count: 1, list:
  (r0v0 org.jetbrains.kotlin.js.PredefinedAnnotation) from 0x0052: FILLED_NEW_ARRAY (r0v0 org.jetbrains.kotlin.js.PredefinedAnnotation), (r1v1 org.jetbrains.kotlin.js.PredefinedAnnotation) A[WRAPPED] (LINE:33) elemType: org.jetbrains.kotlin.js.PredefinedAnnotation
	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:164)
	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:129)
	at jadx.core.utils.InsnRemover.lambda$unbindInsns$1(InsnRemover.java:101)
	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
	at jadx.core.utils.InsnRemover.unbindInsns(InsnRemover.java:100)
	at jadx.core.utils.InsnRemover.removeAllAndUnbind(InsnRemover.java:257)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:187)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:102)
 */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX INFO: compiled from: PredefinedAnnotation.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\b\u0086\u0081\u0002\u0018\u0000 \u00102\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u0010B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000f¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/js/PredefinedAnnotation;", "", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "(Ljava/lang/String;ILorg/jetbrains/kotlin/name/ClassId;)V", "getClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "getFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "LIBRARY", Constants.NO_OAUTH_PROVIDER, "NATIVE_INVOKE", "NATIVE_GETTER", "NATIVE_SETTER", "Companion", "compiler.common.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PredefinedAnnotation {
    LIBRARY(JsStandardClassIds.Annotations.JsLibrary),
    NATIVE(JsStandardClassIds.Annotations.JsNative),
    NATIVE_INVOKE(JsStandardClassIds.Annotations.JsNativeInvoke),
    NATIVE_GETTER(JsStandardClassIds.Annotations.JsNativeGetter),
    NATIVE_SETTER(JsStandardClassIds.Annotations.JsNativeSetter);

    private final ClassId classId;
    private final FqName fqName;
    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final Set<PredefinedAnnotation> WITH_CUSTOM_NAME = SetsKt.setOf((Object[]) new PredefinedAnnotation[]{new PredefinedAnnotation(JsStandardClassIds.Annotations.JsLibrary), new PredefinedAnnotation(JsStandardClassIds.Annotations.JsNative)});

    public static EnumEntries<PredefinedAnnotation> getEntries() {
        return $ENTRIES;
    }

    public static PredefinedAnnotation valueOf(String str) {
        return (PredefinedAnnotation) Enum.valueOf(PredefinedAnnotation.class, str);
    }

    public static PredefinedAnnotation[] values() {
        return (PredefinedAnnotation[]) $VALUES.clone();
    }

    private PredefinedAnnotation(ClassId classId) {
        super(str, i);
        this.classId = classId;
        FqName fqNameAsSingleFqName = classId.asSingleFqName();
        Intrinsics.checkNotNullExpressionValue(fqNameAsSingleFqName, "asSingleFqName(...)");
        this.fqName = fqNameAsSingleFqName;
    }

    public final ClassId getClassId() {
        return this.classId;
    }

    static {
    }

    public final FqName getFqName() {
        return this.fqName;
    }

    /* JADX INFO: compiled from: PredefinedAnnotation.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/js/PredefinedAnnotation$Companion;", "", "()V", "WITH_CUSTOM_NAME", "", "Lorg/jetbrains/kotlin/js/PredefinedAnnotation;", "getWITH_CUSTOM_NAME", "()Ljava/util/Set;", "compiler.common.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Set<PredefinedAnnotation> getWITH_CUSTOM_NAME() {
            return PredefinedAnnotation.WITH_CUSTOM_NAME;
        }
    }
}
