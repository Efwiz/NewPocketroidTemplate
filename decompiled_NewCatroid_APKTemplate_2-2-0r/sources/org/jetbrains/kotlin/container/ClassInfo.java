package org.jetbrains.kotlin.container;

import java.lang.reflect.Type;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: Cache.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B9\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0005\u0012\f\u0010\t\u001a\b\u0012\u0002\b\u0003\u0018\u00010\n¢\u0006\u0002\u0010\u000bJ\u000b\u0010\u0013\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0003J\u000f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\b0\u0005HÆ\u0003J\u000f\u0010\u0016\u001a\b\u0012\u0002\b\u0003\u0018\u00010\nHÆ\u0003JE\u0010\u0017\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u00052\u000e\b\u0002\u0010\t\u001a\b\u0012\u0002\b\u0003\u0018\u00010\nHÆ\u0001J\u0013\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001b\u001a\u00020\u001cHÖ\u0001J\t\u0010\u001d\u001a\u00020\u001eHÖ\u0001R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0017\u0010\t\u001a\b\u0012\u0002\b\u0003\u0018\u00010\n¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0011¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/container/ClassInfo;", "", "constructorInfo", "Lorg/jetbrains/kotlin/container/ConstructorInfo;", "setterInfos", "", "Lorg/jetbrains/kotlin/container/SetterInfo;", "registrations", "Ljava/lang/reflect/Type;", "defaultImplementation", "Ljava/lang/Class;", "(Lorg/jetbrains/kotlin/container/ConstructorInfo;Ljava/util/List;Ljava/util/List;Ljava/lang/Class;)V", "getConstructorInfo", "()Lorg/jetbrains/kotlin/container/ConstructorInfo;", "getDefaultImplementation", "()Ljava/lang/Class;", "getRegistrations", "()Ljava/util/List;", "getSetterInfos", "component1", "component2", "component3", "component4", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "container"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ClassInfo {
    private final ConstructorInfo constructorInfo;
    private final Class<?> defaultImplementation;
    private final List<Type> registrations;
    private final List<SetterInfo> setterInfos;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ ClassInfo copy$default(ClassInfo classInfo, ConstructorInfo constructorInfo, List list, List list2, Class cls, int i, Object obj) {
        if ((i & 1) != 0) {
            constructorInfo = classInfo.constructorInfo;
        }
        if ((i & 2) != 0) {
            list = classInfo.setterInfos;
        }
        if ((i & 4) != 0) {
            list2 = classInfo.registrations;
        }
        if ((i & 8) != 0) {
            cls = classInfo.defaultImplementation;
        }
        return classInfo.copy(constructorInfo, list, list2, cls);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final ConstructorInfo getConstructorInfo() {
        return this.constructorInfo;
    }

    public final List<SetterInfo> component2() {
        return this.setterInfos;
    }

    public final List<Type> component3() {
        return this.registrations;
    }

    public final Class<?> component4() {
        return this.defaultImplementation;
    }

    public final ClassInfo copy(ConstructorInfo constructorInfo, List<SetterInfo> setterInfos, List<? extends Type> registrations, Class<?> defaultImplementation) {
        Intrinsics.checkNotNullParameter(setterInfos, "setterInfos");
        Intrinsics.checkNotNullParameter(registrations, "registrations");
        return new ClassInfo(constructorInfo, setterInfos, registrations, defaultImplementation);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ClassInfo)) {
            return false;
        }
        ClassInfo classInfo = (ClassInfo) other;
        return Intrinsics.areEqual(this.constructorInfo, classInfo.constructorInfo) && Intrinsics.areEqual(this.setterInfos, classInfo.setterInfos) && Intrinsics.areEqual(this.registrations, classInfo.registrations) && Intrinsics.areEqual(this.defaultImplementation, classInfo.defaultImplementation);
    }

    public int hashCode() {
        ConstructorInfo constructorInfo = this.constructorInfo;
        int iHashCode = (((((constructorInfo == null ? 0 : constructorInfo.hashCode()) * 31) + this.setterInfos.hashCode()) * 31) + this.registrations.hashCode()) * 31;
        Class<?> cls = this.defaultImplementation;
        return iHashCode + (cls != null ? cls.hashCode() : 0);
    }

    public String toString() {
        return "ClassInfo(constructorInfo=" + this.constructorInfo + ", setterInfos=" + this.setterInfos + ", registrations=" + this.registrations + ", defaultImplementation=" + this.defaultImplementation + ')';
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ClassInfo(ConstructorInfo constructorInfo, List<SetterInfo> setterInfos, List<? extends Type> registrations, Class<?> cls) {
        Intrinsics.checkNotNullParameter(setterInfos, "setterInfos");
        Intrinsics.checkNotNullParameter(registrations, "registrations");
        this.constructorInfo = constructorInfo;
        this.setterInfos = setterInfos;
        this.registrations = registrations;
        this.defaultImplementation = cls;
    }

    public final ConstructorInfo getConstructorInfo() {
        return this.constructorInfo;
    }

    public final List<SetterInfo> getSetterInfos() {
        return this.setterInfos;
    }

    public final List<Type> getRegistrations() {
        return this.registrations;
    }

    public final Class<?> getDefaultImplementation() {
        return this.defaultImplementation;
    }
}
