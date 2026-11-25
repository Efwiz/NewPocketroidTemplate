package net.bytebuddy.jar.asm;

import io.netty.util.internal.StringUtil;
import java.util.Arrays;

/* JADX INFO: loaded from: classes12.dex */
public final class ConstantDynamic {
    private final Handle bootstrapMethod;
    private final Object[] bootstrapMethodArguments;
    private final String descriptor;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private final String f375name;

    public ConstantDynamic(String str, String str2, Handle handle, Object... objArr) {
        this.f375name = str;
        this.descriptor = str2;
        this.bootstrapMethod = handle;
        this.bootstrapMethodArguments = objArr;
    }

    public String getName() {
        return this.f375name;
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

    public int getSize() {
        char cCharAt = this.descriptor.charAt(0);
        return (cCharAt == 'J' || cCharAt == 'D') ? 2 : 1;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ConstantDynamic)) {
            return false;
        }
        ConstantDynamic constantDynamic = (ConstantDynamic) obj;
        return this.f375name.equals(constantDynamic.f375name) && this.descriptor.equals(constantDynamic.descriptor) && this.bootstrapMethod.equals(constantDynamic.bootstrapMethod) && Arrays.equals(this.bootstrapMethodArguments, constantDynamic.bootstrapMethodArguments);
    }

    public int hashCode() {
        return ((this.f375name.hashCode() ^ Integer.rotateLeft(this.descriptor.hashCode(), 8)) ^ Integer.rotateLeft(this.bootstrapMethod.hashCode(), 16)) ^ Integer.rotateLeft(Arrays.hashCode(this.bootstrapMethodArguments), 24);
    }

    public String toString() {
        return this.f375name + " : " + this.descriptor + StringUtil.SPACE + this.bootstrapMethod + StringUtil.SPACE + Arrays.toString(this.bootstrapMethodArguments);
    }
}
