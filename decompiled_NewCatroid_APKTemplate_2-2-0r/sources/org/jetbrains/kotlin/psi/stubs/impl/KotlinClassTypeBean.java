package org.jetbrains.kotlin.psi.stubs.impl;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.types.model.SimpleTypeMarker;

/* JADX INFO: compiled from: KotlinUserTypeStubImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\r\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u00012\u00020\u0002B#\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\t\u0010\u0011\u001a\u00020\u0004HÆ\u0003J\u000f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006HÆ\u0003J\t\u0010\u0013\u001a\u00020\tHÆ\u0003J-\u0010\u0014\u001a\u00020\u00002\b\b\u0002\u0010\u0003\u001a\u00020\u00042\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\b\b\u0002\u0010\b\u001a\u00020\tHÆ\u0001J\u0013\u0010\u0015\u001a\u00020\t2\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017HÖ\u0003J\t\u0010\u0018\u001a\u00020\u0019HÖ\u0001J\t\u0010\u001a\u001a\u00020\u001bHÖ\u0001R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\b\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinClassTypeBean;", "Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinTypeBean;", "Lorg/jetbrains/kotlin/types/model/SimpleTypeMarker;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "arguments", "", "Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinTypeArgumentBean;", "nullable", "", "(Lorg/jetbrains/kotlin/name/ClassId;Ljava/util/List;Z)V", "getArguments", "()Ljava/util/List;", "getClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", "getNullable", "()Z", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "", "hashCode", "", "toString", "", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class KotlinClassTypeBean implements KotlinTypeBean, SimpleTypeMarker {
    private final List<KotlinTypeArgumentBean> arguments;
    private final ClassId classId;
    private final boolean nullable;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ KotlinClassTypeBean copy$default(KotlinClassTypeBean kotlinClassTypeBean, ClassId classId, List list, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            classId = kotlinClassTypeBean.classId;
        }
        if ((i & 2) != 0) {
            list = kotlinClassTypeBean.arguments;
        }
        if ((i & 4) != 0) {
            z = kotlinClassTypeBean.nullable;
        }
        return kotlinClassTypeBean.copy(classId, list, z);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final ClassId getClassId() {
        return this.classId;
    }

    public final List<KotlinTypeArgumentBean> component2() {
        return this.arguments;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final boolean getNullable() {
        return this.nullable;
    }

    public final KotlinClassTypeBean copy(ClassId classId, List<KotlinTypeArgumentBean> arguments, boolean nullable) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        return new KotlinClassTypeBean(classId, arguments, nullable);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof KotlinClassTypeBean)) {
            return false;
        }
        KotlinClassTypeBean kotlinClassTypeBean = (KotlinClassTypeBean) other;
        return Intrinsics.areEqual(this.classId, kotlinClassTypeBean.classId) && Intrinsics.areEqual(this.arguments, kotlinClassTypeBean.arguments) && this.nullable == kotlinClassTypeBean.nullable;
    }

    public int hashCode() {
        return (((this.classId.hashCode() * 31) + this.arguments.hashCode()) * 31) + Boolean.hashCode(this.nullable);
    }

    public String toString() {
        return "KotlinClassTypeBean(classId=" + this.classId + ", arguments=" + this.arguments + ", nullable=" + this.nullable + ')';
    }

    public KotlinClassTypeBean(ClassId classId, List<KotlinTypeArgumentBean> arguments, boolean z) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        this.classId = classId;
        this.arguments = arguments;
        this.nullable = z;
    }

    public final ClassId getClassId() {
        return this.classId;
    }

    public final List<KotlinTypeArgumentBean> getArguments() {
        return this.arguments;
    }

    @Override // org.jetbrains.kotlin.psi.stubs.impl.KotlinTypeBean
    public boolean getNullable() {
        return this.nullable;
    }
}
