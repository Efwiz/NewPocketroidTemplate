package org.jetbrains.kotlin.builtins.functions;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FunctionTypeKind.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0010\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\b&\u0018\u00002\u00020\u0001:\u0004\u001f !\"B'\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nB)\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\b\u001a\u00020\t\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\u000bJ\b\u0010\u0017\u001a\u00020\u0000H\u0016J\u000e\u0010\u0018\u001a\u00020\u00072\u0006\u0010\u0019\u001a\u00020\u001aJ\u000e\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0019\u001a\u00020\u001aJ\b\u0010\u001d\u001a\u00020\u0000H\u0016J\b\u0010\u001e\u001a\u00020\u0005H\u0016R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0010R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0016\u0010\u0013\u001a\u0004\u0018\u00010\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u000fR\u0016\u0010\u0015\u001a\u0004\u0018\u00010\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u000f¨\u0006#"}, d2 = {"Lorg/jetbrains/kotlin/builtins/functions/FunctionTypeKind;", "", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "classNamePrefix", "", "annotationOnInvokeClassId", "Lorg/jetbrains/kotlin/name/ClassId;", "isReflectType", "", "(Lorg/jetbrains/kotlin/name/FqName;Ljava/lang/String;Lorg/jetbrains/kotlin/name/ClassId;Z)V", "(Lorg/jetbrains/kotlin/name/FqName;Ljava/lang/String;ZLorg/jetbrains/kotlin/name/ClassId;)V", "getAnnotationOnInvokeClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", "getClassNamePrefix", "()Ljava/lang/String;", "()Z", "getPackageFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "prefixForTypeRender", "getPrefixForTypeRender", "serializeAsFunctionWithAnnotationUntil", "getSerializeAsFunctionWithAnnotationUntil", "nonReflectKind", "numberedClassId", "arity", "", "numberedClassName", "Lorg/jetbrains/kotlin/name/Name;", "reflectKind", "toString", "Function", StandardNames.K_FUNCTION_PREFIX, StandardNames.K_SUSPEND_FUNCTION_PREFIX, "SuspendFunction", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FunctionTypeKind {
    private final ClassId annotationOnInvokeClassId;
    private final String classNamePrefix;
    private final boolean isReflectType;
    private final FqName packageFqName;

    public String getPrefixForTypeRender() {
        return null;
    }

    public String getSerializeAsFunctionWithAnnotationUntil() {
        return null;
    }

    public FunctionTypeKind nonReflectKind() {
        if (this.isReflectType) {
            throw new IllegalStateException("Should be overridden explicitly".toString());
        }
        return this;
    }

    public FunctionTypeKind reflectKind() {
        if (this.isReflectType) {
            return this;
        }
        throw new IllegalStateException("Should be overridden explicitly".toString());
    }

    public FunctionTypeKind(FqName packageFqName, String classNamePrefix, boolean z, ClassId classId) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(classNamePrefix, "classNamePrefix");
        this.packageFqName = packageFqName;
        this.classNamePrefix = classNamePrefix;
        this.isReflectType = z;
        this.annotationOnInvokeClassId = classId;
    }

    public final FqName getPackageFqName() {
        return this.packageFqName;
    }

    public final String getClassNamePrefix() {
        return this.classNamePrefix;
    }

    /* JADX INFO: renamed from: isReflectType, reason: from getter */
    public final boolean getIsReflectType() {
        return this.isReflectType;
    }

    public final ClassId getAnnotationOnInvokeClassId() {
        return this.annotationOnInvokeClassId;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public FunctionTypeKind(FqName packageFqName, String classNamePrefix, ClassId annotationOnInvokeClassId, boolean z) {
        this(packageFqName, classNamePrefix, z, annotationOnInvokeClassId);
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(classNamePrefix, "classNamePrefix");
        Intrinsics.checkNotNullParameter(annotationOnInvokeClassId, "annotationOnInvokeClassId");
    }

    public final Name numberedClassName(int arity) {
        Name nameIdentifier = Name.identifier(this.classNamePrefix + arity);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        return nameIdentifier;
    }

    public final ClassId numberedClassId(int arity) {
        return new ClassId(this.packageFqName, numberedClassName(arity));
    }

    public String toString() {
        return this.packageFqName + '.' + this.classNamePrefix + 'N';
    }

    /* JADX INFO: compiled from: FunctionTypeKind.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0001H\u0016¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/builtins/functions/FunctionTypeKind$Function;", "Lorg/jetbrains/kotlin/builtins/functions/FunctionTypeKind;", "()V", "reflectKind", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Function extends FunctionTypeKind {
        public static final Function INSTANCE = new Function();

        private Function() {
            super(StandardNames.BUILT_INS_PACKAGE_FQ_NAME, "Function", false, (ClassId) null);
        }

        @Override // org.jetbrains.kotlin.builtins.functions.FunctionTypeKind
        public FunctionTypeKind reflectKind() {
            return KFunction.INSTANCE;
        }
    }

    /* JADX INFO: compiled from: FunctionTypeKind.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0007\u001a\u00020\u0001H\u0016R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/builtins/functions/FunctionTypeKind$SuspendFunction;", "Lorg/jetbrains/kotlin/builtins/functions/FunctionTypeKind;", "()V", "prefixForTypeRender", "", "getPrefixForTypeRender", "()Ljava/lang/String;", "reflectKind", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class SuspendFunction extends FunctionTypeKind {
        public static final SuspendFunction INSTANCE = new SuspendFunction();

        private SuspendFunction() {
            super(StandardNames.COROUTINES_PACKAGE_FQ_NAME, "SuspendFunction", false, (ClassId) null);
        }

        @Override // org.jetbrains.kotlin.builtins.functions.FunctionTypeKind
        public String getPrefixForTypeRender() {
            return "suspend";
        }

        @Override // org.jetbrains.kotlin.builtins.functions.FunctionTypeKind
        public FunctionTypeKind reflectKind() {
            return KSuspendFunction.INSTANCE;
        }
    }

    /* JADX INFO: compiled from: FunctionTypeKind.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0001H\u0016¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/builtins/functions/FunctionTypeKind$KFunction;", "Lorg/jetbrains/kotlin/builtins/functions/FunctionTypeKind;", "()V", "nonReflectKind", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class KFunction extends FunctionTypeKind {
        public static final KFunction INSTANCE = new KFunction();

        private KFunction() {
            super(StandardNames.KOTLIN_REFLECT_FQ_NAME, StandardNames.K_FUNCTION_PREFIX, true, (ClassId) null);
        }

        @Override // org.jetbrains.kotlin.builtins.functions.FunctionTypeKind
        public FunctionTypeKind nonReflectKind() {
            return Function.INSTANCE;
        }
    }

    /* JADX INFO: compiled from: FunctionTypeKind.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0001H\u0016¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/builtins/functions/FunctionTypeKind$KSuspendFunction;", "Lorg/jetbrains/kotlin/builtins/functions/FunctionTypeKind;", "()V", "nonReflectKind", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class KSuspendFunction extends FunctionTypeKind {
        public static final KSuspendFunction INSTANCE = new KSuspendFunction();

        private KSuspendFunction() {
            super(StandardNames.KOTLIN_REFLECT_FQ_NAME, StandardNames.K_SUSPEND_FUNCTION_PREFIX, true, (ClassId) null);
        }

        @Override // org.jetbrains.kotlin.builtins.functions.FunctionTypeKind
        public FunctionTypeKind nonReflectKind() {
            return SuspendFunction.INSTANCE;
        }
    }
}
