package net.bytebuddy.utility.privilege;

import java.lang.reflect.Method;
import java.security.PrivilegedAction;
import java.util.Arrays;
import net.bytebuddy.build.HashCodeAndEqualsPlugin;
import net.bytebuddy.utility.nullability.MaybeNull;

/* JADX INFO: loaded from: classes12.dex */
@HashCodeAndEqualsPlugin.Enhance
public class GetMethodAction implements PrivilegedAction<Method> {

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private final String f407name;
    private final Class<?>[] parameter;
    private final String type;

    public boolean equals(@MaybeNull Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        GetMethodAction getMethodAction = (GetMethodAction) obj;
        return this.type.equals(getMethodAction.type) && this.f407name.equals(getMethodAction.f407name) && Arrays.equals(this.parameter, getMethodAction.parameter);
    }

    public int hashCode() {
        return (((((getClass().hashCode() * 31) + this.type.hashCode()) * 31) + this.f407name.hashCode()) * 31) + Arrays.hashCode(this.parameter);
    }

    public GetMethodAction(String str, String str2, Class<?>... clsArr) {
        this.type = str;
        this.f407name = str2;
        this.parameter = clsArr;
    }

    @Override // java.security.PrivilegedAction
    @MaybeNull
    public Method run() {
        try {
            return Class.forName(this.type).getMethod(this.f407name, this.parameter);
        } catch (Exception unused) {
            return null;
        }
    }
}
