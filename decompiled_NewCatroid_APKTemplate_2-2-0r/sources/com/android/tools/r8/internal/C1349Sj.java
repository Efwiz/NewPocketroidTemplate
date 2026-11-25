package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Sj, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C1349Sj {
    private static C1349Sj b = new C1349Sj(a.Unknown);
    static final /* synthetic */ boolean c = true;
    private final a a;

    /* JADX INFO: renamed from: com.android.tools.r8.internal.Sj$a */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public enum a {
        KeepRule,
        CompatibilityRule,
        ConditionalKeepRule,
        KeepRulePrecondition,
        InstantiatedIn,
        InvokedViaSuper,
        TargetedBySuper,
        InvokedFrom,
        InvokedFromLambdaCreatedIn,
        AnnotatedOn,
        ReferencedFrom,
        ReflectiveUseFrom,
        ReachableFromLiveType,
        ReferencedInAnnotation,
        IsLibraryMethod,
        OverridingMethod,
        MethodHandleUseFrom,
        /* JADX INFO: Fake field, exist only in values array */
        CompanionMethod,
        /* JADX INFO: Fake field, exist only in values array */
        CompanionMethod,
        Unknown
    }

    public C1349Sj(a aVar) {
        this.a = aVar;
    }

    public static C1349Sj c() {
        return b;
    }

    public a a() {
        return this.a;
    }

    public String b() {
        switch (AbstractC1323Rj.a[a().ordinal()]) {
            case 1:
            case 2:
            case 3:
                return "referenced in keep rule";
            case 4:
                return "satisfied with precondition";
            case 5:
                return "instantiated in";
            case 6:
                return "invoked via super from";
            case 7:
                return "targeted by super from";
            case 8:
                return "invoked from";
            case 9:
                return "invoked from lambda created in";
            case 10:
                return "annotated on";
            case 11:
                return "referenced from";
            case 12:
                return "reflected from";
            case 13:
                return "reachable from";
            case 14:
                return "referenced in annotation";
            case 15:
                return "overriding method";
            case 16:
                return "defined in library method overridden by";
            case 17:
                return "referenced by method handle";
            case 18:
                return "companion class for";
            case 19:
                return "companion method for";
            case 20:
                return "kept for unknown reasons";
            default:
                if (c) {
                    return "kept for unknown reasons";
                }
                throw new AssertionError("Unknown edge kind: " + a());
        }
    }

    public final boolean equals(Object obj) {
        return this == obj || ((obj instanceof C1349Sj) && ((C1349Sj) obj).a == this.a);
    }

    public final int hashCode() {
        return this.a.hashCode();
    }

    public final String toString() {
        return "{edge-type:" + this.a.toString() + "}";
    }
}
