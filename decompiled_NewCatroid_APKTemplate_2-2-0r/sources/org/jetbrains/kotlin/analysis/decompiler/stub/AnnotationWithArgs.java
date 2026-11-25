package org.jetbrains.kotlin.analysis.decompiler.stub;

import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.constant.ConstantValue;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: AnnotationWithArgs.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0016\u0010\u0004\u001a\u0012\u0012\u0004\u0012\u00020\u0006\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00070\u0005¢\u0006\u0002\u0010\bJ\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\u0019\u0010\u000e\u001a\u0012\u0012\u0004\u0012\u00020\u0006\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00070\u0005HÆ\u0003J-\u0010\u000f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u0018\b\u0002\u0010\u0004\u001a\u0012\u0012\u0004\u0012\u00020\u0006\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00070\u0005HÆ\u0001J\u0013\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001R!\u0010\u0004\u001a\u0012\u0012\u0004\u0012\u00020\u0006\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00070\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/analysis/decompiler/stub/AnnotationWithArgs;", "", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "args", "", "Lorg/jetbrains/kotlin/name/Name;", "Lorg/jetbrains/kotlin/constant/ConstantValue;", "(Lorg/jetbrains/kotlin/name/ClassId;Ljava/util/Map;)V", "getArgs", "()Ljava/util/Map;", "getClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "decompiler-to-stubs"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class AnnotationWithArgs {
    private final Map<Name, ConstantValue<?>> args;
    private final ClassId classId;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ AnnotationWithArgs copy$default(AnnotationWithArgs annotationWithArgs, ClassId classId, Map map, int i, Object obj) {
        if ((i & 1) != 0) {
            classId = annotationWithArgs.classId;
        }
        if ((i & 2) != 0) {
            map = annotationWithArgs.args;
        }
        return annotationWithArgs.copy(classId, map);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final ClassId getClassId() {
        return this.classId;
    }

    public final Map<Name, ConstantValue<?>> component2() {
        return this.args;
    }

    public final AnnotationWithArgs copy(ClassId classId, Map<Name, ? extends ConstantValue<?>> args) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        Intrinsics.checkNotNullParameter(args, "args");
        return new AnnotationWithArgs(classId, args);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AnnotationWithArgs)) {
            return false;
        }
        AnnotationWithArgs annotationWithArgs = (AnnotationWithArgs) other;
        return Intrinsics.areEqual(this.classId, annotationWithArgs.classId) && Intrinsics.areEqual(this.args, annotationWithArgs.args);
    }

    public int hashCode() {
        return (this.classId.hashCode() * 31) + this.args.hashCode();
    }

    public String toString() {
        return "AnnotationWithArgs(classId=" + this.classId + ", args=" + this.args + ')';
    }

    /* JADX WARN: Multi-variable type inference failed */
    public AnnotationWithArgs(ClassId classId, Map<Name, ? extends ConstantValue<?>> args) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        Intrinsics.checkNotNullParameter(args, "args");
        this.classId = classId;
        this.args = args;
    }

    public final Map<Name, ConstantValue<?>> getArgs() {
        return this.args;
    }

    public final ClassId getClassId() {
        return this.classId;
    }
}
