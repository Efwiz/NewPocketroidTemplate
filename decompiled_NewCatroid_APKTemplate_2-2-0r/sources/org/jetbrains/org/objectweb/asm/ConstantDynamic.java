package org.jetbrains.org.objectweb.asm;

import io.netty.util.internal.StringUtil;
import java.util.Arrays;

/* JADX INFO: loaded from: classes6.dex */
public final class ConstantDynamic {
    private final Handle bootstrapMethod;
    private final Object[] bootstrapMethodArguments;
    private final String descriptor;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private final String f1004name;

    public ConstantDynamic(String str, String str2, Handle handle, Object... objArr) {
        this.f1004name = str;
        this.descriptor = str2;
        this.bootstrapMethod = handle;
        this.bootstrapMethodArguments = objArr;
    }

    public String getName() {
        return this.f1004name;
    }

    public String getDescriptor() {
        return this.descriptor;
    }

    public Handle getBootstrapMethod() {
        return this.bootstrapMethod;
    }

    public int getBootstrapMethodArgumentCount() {
        return this.bootstrapMethodArguments.length;
    }

    public Object getBootstrapMethodArgument(int i) {
        return this.bootstrapMethodArguments[i];
    }

    Object[] getBootstrapMethodArgumentsUnsafe() {
        return this.bootstrapMethodArguments;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ConstantDynamic)) {
            return false;
        }
        ConstantDynamic constantDynamic = (ConstantDynamic) obj;
        return this.f1004name.equals(constantDynamic.f1004name) && this.descriptor.equals(constantDynamic.descriptor) && this.bootstrapMethod.equals(constantDynamic.bootstrapMethod) && Arrays.equals(this.bootstrapMethodArguments, constantDynamic.bootstrapMethodArguments);
    }

    public int hashCode() {
        return ((this.f1004name.hashCode() ^ Integer.rotateLeft(this.descriptor.hashCode(), 8)) ^ Integer.rotateLeft(this.bootstrapMethod.hashCode(), 16)) ^ Integer.rotateLeft(Arrays.hashCode(this.bootstrapMethodArguments), 24);
    }

    public String toString() {
        return this.f1004name + " : " + this.descriptor + StringUtil.SPACE + this.bootstrapMethod + StringUtil.SPACE + Arrays.toString(this.bootstrapMethodArguments);
    }
}
