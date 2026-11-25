package org.jetbrains.kotlin.com.intellij.psi.stubs;

/* JADX INFO: loaded from: classes3.dex */
final class StubSerializationUtil {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 2 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "root";
        } else if (i != 2) {
            objArr[0] = "rootStub";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/stubs/StubSerializationUtil";
        }
        if (i != 2) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/stubs/StubSerializationUtil";
        } else {
            objArr[1] = "brokenStubFormat";
        }
        if (i == 1) {
            objArr[2] = "brokenStubFormat";
        } else if (i != 2) {
            objArr[2] = "getSerializer";
        }
        String str2 = String.format(str, objArr);
        if (i == 2) {
            throw new IllegalStateException(str2);
        }
    }

    private StubSerializationUtil() {
    }

    static ObjectStubSerializer<Stub, Stub> getSerializer(Stub stub) {
        if (stub == null) {
            $$$reportNull$$$0(0);
        }
        if (stub instanceof PsiFileStub) {
            return ((PsiFileStub) stub).getType();
        }
        return stub.getStubType();
    }
}
