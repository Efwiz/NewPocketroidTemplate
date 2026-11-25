package kotlin.script.experimental.api;

import java.io.Serializable;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;
import kotlin.reflect.KClassifier;
import kotlin.reflect.KType;
import kotlin.text.StringsKt;
import net.bytebuddy.description.type.TypeDescription;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: kotlinType.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\u0018\u0000 \u001b2\u00020\u0001:\u0001\u001bB\u0019\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006B\u001d\b\u0017\u0012\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\b\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\tB\u000f\b\u0016\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fB%\b\u0002\u0012\u0006\u0010\r\u001a\u00020\u0003\u0012\f\u0010\u000e\u001a\b\u0012\u0002\b\u0003\u0018\u00010\b\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u000fJ\u0013\u0010\u0015\u001a\u00020\u00052\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0096\u0002J\b\u0010\u0018\u001a\u00020\u0019H\u0016J\u000e\u0010\u001a\u001a\u00020\u00002\u0006\u0010\u0004\u001a\u00020\u0005R\u0017\u0010\u000e\u001a\b\u0012\u0002\b\u0003\u0018\u00010\b¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0012R\u0011\u0010\r\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014¨\u0006\u001c"}, d2 = {"Lkotlin/script/experimental/api/KotlinType;", "Ljava/io/Serializable;", "qualifiedTypeName", "", "isNullable", "", "(Ljava/lang/String;Z)V", MangleConstant.CLASS_PREFIX, "Lkotlin/reflect/KClass;", "(Lkotlin/reflect/KClass;Z)V", ModuleXmlParser.TYPE, "Lkotlin/reflect/KType;", "(Lkotlin/reflect/KType;)V", "typeName", "fromClass", "(Ljava/lang/String;Lkotlin/reflect/KClass;Z)V", "getFromClass", "()Lkotlin/reflect/KClass;", "()Z", "getTypeName", "()Ljava/lang/String;", Namer.EQUALS_METHOD_NAME, "other", "", "hashCode", "", "withNullability", "Companion", PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinType implements Serializable {
    private static final long serialVersionUID = 2;
    private final transient KClass<?> fromClass;
    private final boolean isNullable;
    private final String typeName;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public KotlinType(String qualifiedTypeName) {
        this(qualifiedTypeName, false, 2, (DefaultConstructorMarker) null);
        Intrinsics.checkNotNullParameter(qualifiedTypeName, "qualifiedTypeName");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public KotlinType(KClass<?> kclass) {
        this((KClass) kclass, false, 2, (DefaultConstructorMarker) null);
        Intrinsics.checkNotNullParameter(kclass, "kclass");
    }

    private KotlinType(String str, KClass<?> kClass, boolean z) {
        this.typeName = str;
        this.fromClass = kClass;
        this.isNullable = z;
    }

    public final String getTypeName() {
        return this.typeName;
    }

    public final KClass<?> getFromClass() {
        return this.fromClass;
    }

    /* JADX INFO: renamed from: isNullable, reason: from getter */
    public final boolean getIsNullable() {
        return this.isNullable;
    }

    public /* synthetic */ KotlinType(String str, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? false : z);
    }

    /* JADX WARN: Code duplicated, block: B:6:0x001c  */
    /* JADX WARN: Illegal instructions before constructor call */
    public KotlinType(String qualifiedTypeName, boolean z) {
        boolean z2;
        Intrinsics.checkNotNullParameter(qualifiedTypeName, "qualifiedTypeName");
        String strRemoveSuffix = StringsKt.removeSuffix(qualifiedTypeName, (CharSequence) TypeDescription.Generic.OfWildcardType.SYMBOL);
        if (!z) {
            z2 = StringsKt.endsWith$default((CharSequence) qualifiedTypeName, MangleConstant.Q_MARK, false, 2, (Object) null);
        }
        this(strRemoveSuffix, null, z2);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public KotlinType(KClass<?> kclass, boolean z) {
        Intrinsics.checkNotNullParameter(kclass, "kclass");
        String qualifiedName = kclass.getQualifiedName();
        if (qualifiedName == null) {
            throw new IllegalStateException(("Cannot use class " + kclass + " as a KotlinType").toString());
        }
        this(qualifiedName, kclass, z);
    }

    public /* synthetic */ KotlinType(KClass kClass, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((KClass<?>) kClass, (i & 2) != 0 ? false : z);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public KotlinType(KType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        KClassifier classifier = type.getClassifier();
        Intrinsics.checkNotNull(classifier, "null cannot be cast to non-null type kotlin.reflect.KClass<*>");
        this((KClass<?>) classifier, type.isMarkedNullable());
    }

    public boolean equals(Object other) {
        KotlinType kotlinType = other instanceof KotlinType ? (KotlinType) other : null;
        return kotlinType != null && Intrinsics.areEqual(this.typeName, kotlinType.typeName) && this.isNullable == kotlinType.isNullable;
    }

    public int hashCode() {
        return this.typeName.hashCode() + (Boolean.hashCode(this.isNullable) * 31);
    }

    public final KotlinType withNullability(boolean isNullable) {
        return new KotlinType(this.typeName, this.fromClass, isNullable);
    }
}
