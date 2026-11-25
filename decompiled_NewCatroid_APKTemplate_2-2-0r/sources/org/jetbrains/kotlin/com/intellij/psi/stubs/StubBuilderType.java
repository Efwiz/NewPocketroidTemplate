package org.jetbrains.kotlin.com.intellij.psi.stubs;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.tree.IStubFileElementType;

/* JADX INFO: loaded from: classes3.dex */
class StubBuilderType {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Logger LOG = Logger.getInstance((Class<?>) StubBuilderType.class);
    private final BinaryFileStubBuilder myBinaryFileStubBuilder;
    private final Object myBinarySubBuilder;
    private final IStubFileElementType myElementType;
    private final List<String> myProperties;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 4 || i == 5 || i == 6) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 4 || i == 5 || i == 6) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "properties";
                break;
            case 2:
            case 3:
                objArr[0] = "binaryFileStubBuilder";
                break;
            case 4:
            case 5:
            case 6:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/stubs/StubBuilderType";
                break;
            default:
                objArr[0] = "elementType";
                break;
        }
        if (i == 4 || i == 5 || i == 6) {
            objArr[1] = "getClassToBlameInCaseOfException";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/stubs/StubBuilderType";
        }
        if (i != 4 && i != 5 && i != 6) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i != 4 && i != 5 && i != 6) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    StubBuilderType(IStubFileElementType iStubFileElementType, List<String> list) {
        if (iStubFileElementType == null) {
            $$$reportNull$$$0(0);
        }
        if (list == null) {
            $$$reportNull$$$0(1);
        }
        this.myElementType = iStubFileElementType;
        this.myProperties = list;
        this.myBinaryFileStubBuilder = null;
        this.myBinarySubBuilder = null;
    }

    StubBuilderType(BinaryFileStubBuilder binaryFileStubBuilder) {
        if (binaryFileStubBuilder == null) {
            $$$reportNull$$$0(2);
        }
        this.myElementType = null;
        this.myBinaryFileStubBuilder = binaryFileStubBuilder;
        this.myBinarySubBuilder = null;
        this.myProperties = Collections.emptyList();
    }

    StubBuilderType(BinaryFileStubBuilder.CompositeBinaryFileStubBuilder compositeBinaryFileStubBuilder, Object obj) {
        if (compositeBinaryFileStubBuilder == null) {
            $$$reportNull$$$0(3);
        }
        this.myElementType = null;
        this.myBinaryFileStubBuilder = compositeBinaryFileStubBuilder;
        this.myBinarySubBuilder = obj;
        this.myProperties = Collections.emptyList();
    }

    Class<?> getClassToBlameInCaseOfException() {
        IStubFileElementType iStubFileElementType = this.myElementType;
        if (iStubFileElementType != null) {
            Class<?> cls = iStubFileElementType.getClass();
            if (cls == null) {
                $$$reportNull$$$0(4);
            }
            return cls;
        }
        Object obj = this.myBinarySubBuilder;
        if (obj == null) {
            Class<?> cls2 = this.myBinaryFileStubBuilder.getClass();
            if (cls2 == null) {
                $$$reportNull$$$0(5);
            }
            return cls2;
        }
        Class<?> cls3 = obj.getClass();
        if (cls3 == null) {
            $$$reportNull$$$0(6);
        }
        return cls3;
    }

    BinaryFileStubBuilder getBinaryFileStubBuilder() {
        return this.myBinaryFileStubBuilder;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        StubBuilderType stubBuilderType = (StubBuilderType) obj;
        return Objects.equals(this.myElementType, stubBuilderType.myElementType) && Objects.equals(this.myBinaryFileStubBuilder, stubBuilderType.myBinaryFileStubBuilder) && Objects.equals(this.myProperties, stubBuilderType.myProperties) && Objects.equals(this.myBinarySubBuilder, stubBuilderType.myBinarySubBuilder);
    }

    public int hashCode() {
        return Objects.hash(this.myElementType, this.myBinaryFileStubBuilder, this.myBinarySubBuilder, this.myProperties);
    }
}
