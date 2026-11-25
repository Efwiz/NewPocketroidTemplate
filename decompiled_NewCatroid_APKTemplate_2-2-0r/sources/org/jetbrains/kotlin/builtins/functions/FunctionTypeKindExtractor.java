package org.jetbrains.kotlin.builtins.functions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: FunctionTypeKindExtractor.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\u0018\u0000 \u00182\u00020\u0001:\u0002\u0018\u0019B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\nJ\u0018\u0010\u000b\u001a\u0004\u0018\u00010\u00042\u0006\u0010\f\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u000eJ\u0018\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\f\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u000eJ\u0006\u0010\u0011\u001a\u00020\u0012J\u000e\u0010\u0013\u001a\u00020\u00122\u0006\u0010\f\u001a\u00020\bJ\u0017\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0016\u001a\u00020\u000eH\u0002¢\u0006\u0002\u0010\u0017R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R \u0010\u0006\u001a\u0014\u0012\u0004\u0012\u00020\b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/builtins/functions/FunctionTypeKindExtractor;", "", "kinds", "", "Lorg/jetbrains/kotlin/builtins/functions/FunctionTypeKind;", "(Ljava/util/List;)V", "knownKindsByPackageFqName", "", "Lorg/jetbrains/kotlin/name/FqName;", "getFunctionKindPackageNames", "", "getFunctionalClassKind", "packageFqName", "className", "", "getFunctionalClassKindWithArity", "Lorg/jetbrains/kotlin/builtins/functions/FunctionTypeKindExtractor$KindWithArity;", "hasExtensionKinds", "", "hasKindWithSpecificPackage", "toInt", "", "s", "(Ljava/lang/String;)Ljava/lang/Integer;", "Companion", "KindWithArity", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FunctionTypeKindExtractor {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final FunctionTypeKindExtractor Default = new FunctionTypeKindExtractor(CollectionsKt.listOf((Object[]) new FunctionTypeKind[]{FunctionTypeKind.Function.INSTANCE, FunctionTypeKind.SuspendFunction.INSTANCE, FunctionTypeKind.KFunction.INSTANCE, FunctionTypeKind.KSuspendFunction.INSTANCE}));
    private final List<FunctionTypeKind> kinds;
    private final Map<FqName, List<FunctionTypeKind>> knownKindsByPackageFqName;

    public static final FunctionTypeKindExtractor getDefault() {
        return INSTANCE.getDefault();
    }

    /* JADX INFO: compiled from: FunctionTypeKindExtractor.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001c\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u0005\u0010\u0002\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/builtins/functions/FunctionTypeKindExtractor$Companion;", "", "()V", "Default", "Lorg/jetbrains/kotlin/builtins/functions/FunctionTypeKindExtractor;", "getDefault$annotations", "getDefault", "()Lorg/jetbrains/kotlin/builtins/functions/FunctionTypeKindExtractor;", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        @AllowedToUsedOnlyInK1
        public static /* synthetic */ void getDefault$annotations() {
        }

        private Companion() {
        }

        public final FunctionTypeKindExtractor getDefault() {
            return FunctionTypeKindExtractor.Default;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public FunctionTypeKindExtractor(List<? extends FunctionTypeKind> kinds) {
        Intrinsics.checkNotNullParameter(kinds, "kinds");
        this.kinds = kinds;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Object obj : kinds) {
            FqName packageFqName = ((FunctionTypeKind) obj).getPackageFqName();
            Object obj2 = linkedHashMap.get(packageFqName);
            if (obj2 == null) {
                obj2 = (List) new ArrayList();
                linkedHashMap.put(packageFqName, obj2);
            }
            ((List) obj2).add(obj);
        }
        this.knownKindsByPackageFqName = linkedHashMap;
    }

    public final FunctionTypeKind getFunctionalClassKind(FqName packageFqName, String className) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(className, "className");
        KindWithArity functionalClassKindWithArity = getFunctionalClassKindWithArity(packageFqName, className);
        if (functionalClassKindWithArity != null) {
            return functionalClassKindWithArity.getKind();
        }
        return null;
    }

    public final KindWithArity getFunctionalClassKindWithArity(FqName packageFqName, String className) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(className, "className");
        List<FunctionTypeKind> list = this.knownKindsByPackageFqName.get(packageFqName);
        if (list == null) {
            return null;
        }
        for (FunctionTypeKind functionTypeKind : list) {
            if (StringsKt.startsWith$default(className, functionTypeKind.getClassNamePrefix(), false, 2, (Object) null)) {
                String strSubstring = className.substring(functionTypeKind.getClassNamePrefix().length());
                Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
                Integer num = toInt(strSubstring);
                if (num != null) {
                    return new KindWithArity(functionTypeKind, num.intValue());
                }
            }
        }
        return null;
    }

    public final boolean hasKindWithSpecificPackage(FqName packageFqName) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        return this.knownKindsByPackageFqName.containsKey(packageFqName);
    }

    public final Set<FqName> getFunctionKindPackageNames() {
        return this.knownKindsByPackageFqName.keySet();
    }

    public final boolean hasExtensionKinds() {
        List<FunctionTypeKind> list = this.kinds;
        if ((list instanceof Collection) && list.isEmpty()) {
            return false;
        }
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            if (!FunctionTypeKindKt.isBuiltin((FunctionTypeKind) it.next())) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: compiled from: FunctionTypeKindExtractor.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0005HÖ\u0001J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/builtins/functions/FunctionTypeKindExtractor$KindWithArity;", "", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/builtins/functions/FunctionTypeKind;", "arity", "", "(Lorg/jetbrains/kotlin/builtins/functions/FunctionTypeKind;I)V", "getArity", "()I", "getKind", "()Lorg/jetbrains/kotlin/builtins/functions/FunctionTypeKind;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "toString", "", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class KindWithArity {
        private final int arity;
        private final FunctionTypeKind kind;

        public static /* synthetic */ KindWithArity copy$default(KindWithArity kindWithArity, FunctionTypeKind functionTypeKind, int i, int i2, Object obj) {
            if ((i2 & 1) != 0) {
                functionTypeKind = kindWithArity.kind;
            }
            if ((i2 & 2) != 0) {
                i = kindWithArity.arity;
            }
            return kindWithArity.copy(functionTypeKind, i);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final FunctionTypeKind getKind() {
            return this.kind;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final int getArity() {
            return this.arity;
        }

        public final KindWithArity copy(FunctionTypeKind kind, int arity) {
            Intrinsics.checkNotNullParameter(kind, "kind");
            return new KindWithArity(kind, arity);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof KindWithArity)) {
                return false;
            }
            KindWithArity kindWithArity = (KindWithArity) other;
            return Intrinsics.areEqual(this.kind, kindWithArity.kind) && this.arity == kindWithArity.arity;
        }

        public int hashCode() {
            return (this.kind.hashCode() * 31) + Integer.hashCode(this.arity);
        }

        public String toString() {
            return "KindWithArity(kind=" + this.kind + ", arity=" + this.arity + ')';
        }

        public KindWithArity(FunctionTypeKind kind, int i) {
            Intrinsics.checkNotNullParameter(kind, "kind");
            this.kind = kind;
            this.arity = i;
        }

        public final int getArity() {
            return this.arity;
        }

        public final FunctionTypeKind getKind() {
            return this.kind;
        }
    }

    private final Integer toInt(String s) {
        if (s.length() == 0) {
            return null;
        }
        int length = s.length();
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            int iCharAt = s.charAt(i2) - '0';
            if (iCharAt < 0 || iCharAt >= 10) {
                return null;
            }
            i = (i * 10) + iCharAt;
        }
        return Integer.valueOf(i);
    }
}
