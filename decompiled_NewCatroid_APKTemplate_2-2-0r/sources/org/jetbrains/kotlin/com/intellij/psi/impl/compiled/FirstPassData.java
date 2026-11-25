package org.jetbrains.kotlin.com.intellij.psi.impl.compiled;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.io.IOException;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.impl.cache.TypeInfo;
import org.jetbrains.kotlin.com.intellij.util.BitUtil;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.one.util.streamex.EntryStream;
import org.jetbrains.org.objectweb.asm.ClassReader;
import org.jetbrains.org.objectweb.asm.ClassVisitor;
import org.jetbrains.org.objectweb.asm.Handle;
import org.jetbrains.org.objectweb.asm.MethodVisitor;
import org.jetbrains.org.objectweb.asm.RecordComponentVisitor;

/* JADX INFO: loaded from: classes3.dex */
class FirstPassData implements Function<String, String> {
    private final Map<String, InnerClassEntry> myMap;
    private final Set<String> myNonStatic;
    private final Set<ObjectMethod> mySyntheticMethods;
    private final boolean myTrustInnerClasses;
    private final String myVarArgRecordComponent;
    private static final Logger LOG = Logger.getInstance((Class<?>) FirstPassData.class);
    private static final FirstPassData NO_DATA = new FirstPassData(Collections.emptyMap(), null, Collections.emptySet(), true);
    private static final FirstPassData EMPTY = new FirstPassData(Collections.emptyMap(), null, Collections.emptySet(), true);

    /* JADX WARN: Code duplicated, block: B:31:0x0060  */
    /* JADX WARN: Code duplicated, block: B:7:0x000d  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 3 && i != 9) {
            switch (i) {
                case 11:
                case 12:
                case 13:
                case 14:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 3 && i != 9) {
            switch (i) {
                case 11:
                case 12:
                case 13:
                case 14:
                    i2 = 2;
                    break;
                default:
                    i2 = 3;
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
                objArr[0] = "syntheticMethods";
                break;
            case 2:
            case 8:
            case 10:
                objArr[0] = "jvmName";
                break;
            case 3:
            case 9:
            case 11:
            case 12:
            case 13:
            case 14:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/FirstPassData";
                break;
            case 4:
                objArr[0] = "javaName";
                break;
            case 5:
                objArr[0] = "componentName";
                break;
            case 6:
                objArr[0] = "methodName";
                break;
            case 7:
                objArr[0] = "methodDesc";
                break;
            default:
                objArr[0] = "map";
                break;
        }
        if (i == 3) {
            objArr[1] = "fun";
        } else if (i != 9) {
            switch (i) {
                case 11:
                case 12:
                    objArr[1] = "mapJvmClassNameToJava";
                    break;
                case 13:
                    objArr[1] = CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME;
                    break;
                case 14:
                    objArr[1] = "fromClassBytes";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/FirstPassData";
                    break;
            }
        } else {
            objArr[1] = "mapJvmClassNameToJava";
        }
        switch (i) {
            case 2:
                objArr[2] = "fun";
                break;
            case 3:
            case 9:
            case 11:
            case 12:
            case 13:
            case 14:
                break;
            case 4:
                objArr[2] = "getInnerDepth";
                break;
            case 5:
                objArr[2] = "isVarArgComponent";
                break;
            case 6:
            case 7:
                objArr[2] = "isSyntheticRecordMethod";
                break;
            case 8:
            case 10:
                objArr[2] = "mapJvmClassNameToJava";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 9) {
            switch (i) {
                case 11:
                case 12:
                case 13:
                case 14:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class InnerClassEntry {
        final String myInnerName;
        final String myOuterName;
        final boolean myStatic;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "outerName", "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/FirstPassData$InnerClassEntry", "<init>"));
        }

        private InnerClassEntry(String str, String str2, boolean z) {
            if (str == null) {
                $$$reportNull$$$0(0);
            }
            this.myOuterName = str;
            this.myInnerName = str2;
            this.myStatic = z;
        }
    }

    private FirstPassData(Map<String, InnerClassEntry> map, String str, Set<ObjectMethod> set, boolean z) {
        if (map == null) {
            $$$reportNull$$$0(0);
        }
        if (set == null) {
            $$$reportNull$$$0(1);
        }
        this.myMap = map;
        this.myVarArgRecordComponent = str;
        this.mySyntheticMethods = set;
        this.myTrustInnerClasses = z;
        if (!map.isEmpty()) {
            this.myNonStatic = ContainerUtil.map2Set(EntryStream.of((Map) map).filterValues(new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.compiled.FirstPassData$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return FirstPassData.lambda$new$0((FirstPassData.InnerClassEntry) obj);
                }
            }).keys().toList(), new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.compiled.FirstPassData$$ExternalSyntheticLambda2
                @Override // org.jetbrains.kotlin.com.intellij.util.Function
                public final Object fun(Object obj) {
                    return this.f$0.mapJvmClassNameToJava((String) obj);
                }
            });
        } else {
            this.myNonStatic = Collections.emptySet();
        }
    }

    static /* synthetic */ boolean lambda$new$0(InnerClassEntry innerClassEntry) {
        return !innerClassEntry.myStatic;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.Function
    public String fun(String str) {
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        String strMapJvmClassNameToJava = mapJvmClassNameToJava(str);
        if (strMapJvmClassNameToJava == null) {
            $$$reportNull$$$0(3);
        }
        return strMapJvmClassNameToJava;
    }

    int getInnerDepth(String str) {
        if (str == null) {
            $$$reportNull$$$0(4);
        }
        int i = 0;
        while (!str.isEmpty() && this.myNonStatic.contains(str)) {
            i++;
            str = StringUtil.getPackageName(str);
        }
        return i;
    }

    boolean isVarArgComponent(String str) {
        if (str == null) {
            $$$reportNull$$$0(5);
        }
        return str.equals(this.myVarArgRecordComponent);
    }

    boolean isSyntheticRecordMethod(String str, String str2) {
        if (str == null) {
            $$$reportNull$$$0(6);
        }
        if (str2 == null) {
            $$$reportNull$$$0(7);
        }
        return !this.mySyntheticMethods.isEmpty() && this.mySyntheticMethods.contains(ObjectMethod.from(str, str2));
    }

    List<TypeInfo> createTypes(String[] strArr) {
        if (strArr == null) {
            return null;
        }
        return ContainerUtil.map(strArr, new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.compiled.FirstPassData$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return this.f$0.m6603xa097a5a0((String) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$createTypes$1$org-jetbrains-kotlin-com-intellij-psi-impl-compiled-FirstPassData, reason: not valid java name */
    /* synthetic */ TypeInfo m6603xa097a5a0(String str) {
        return new TypeInfo(mapJvmClassNameToJava(str, false));
    }

    String mapJvmClassNameToJava(String str) {
        if (str == null) {
            $$$reportNull$$$0(8);
        }
        String strMapJvmClassNameToJava = mapJvmClassNameToJava(str, true);
        if (strMapJvmClassNameToJava == null) {
            $$$reportNull$$$0(9);
        }
        return strMapJvmClassNameToJava;
    }

    /* JADX WARN: Multi-variable type inference failed */
    String mapJvmClassNameToJava(String str, boolean z) {
        if (str == null) {
            $$$reportNull$$$0(10);
        }
        if (str.indexOf(36) >= 0) {
            InnerClassEntry innerClassEntry = this.myMap.get(str);
            if (innerClassEntry != null) {
                str = innerClassEntry.myOuterName;
                if (innerClassEntry.myInnerName != null) {
                    str = mapJvmClassNameToJava(innerClassEntry.myOuterName) + '.' + innerClassEntry.myInnerName;
                    this.myMap.put(str, new InnerClassEntry(str, null, true));
                }
            } else if (z || !this.myTrustInnerClasses) {
                String strFun = StubBuildingVisitor.GUESSING_MAPPER.fun(str);
                if (strFun == null) {
                    $$$reportNull$$$0(11);
                }
                return strFun;
            }
        }
        String strReplace = str.replace('/', '.');
        if (strReplace == null) {
            $$$reportNull$$$0(12);
        }
        return strReplace;
    }

    static FirstPassData create(Object obj) {
        byte[] bArrContentsToByteArray;
        if (obj instanceof ClsFileImpl.FileContentPair) {
            bArrContentsToByteArray = ((ClsFileImpl.FileContentPair) obj).getContent();
        } else if (obj instanceof VirtualFile) {
            try {
                bArrContentsToByteArray = ((VirtualFile) obj).contentsToByteArray(false);
            } catch (IOException unused) {
                bArrContentsToByteArray = null;
            }
        } else {
            bArrContentsToByteArray = null;
        }
        if (bArrContentsToByteArray != null) {
            return fromClassBytes(bArrContentsToByteArray);
        }
        FirstPassData firstPassData = NO_DATA;
        if (firstPassData == null) {
            $$$reportNull$$$0(13);
        }
        return firstPassData;
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.com.intellij.psi.impl.compiled.FirstPassData$1FirstPassVisitor, reason: invalid class name */
    class C1FirstPassVisitor extends ClassVisitor {
        StringBuilder canonicalSignature;
        String lastComponent;
        final Map<String, InnerClassEntry> mapping;
        Set<ObjectMethod> syntheticSignatures;
        boolean trustInnerClasses;
        Set<String> varArgConstructors;

        C1FirstPassVisitor() {
            super(589824);
            this.mapping = new HashMap();
            this.trustInnerClasses = true;
        }

        @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
        public void visit(int i, int i2, String str, String str2, String str3, String[] strArr) {
            if (BitUtil.isSet(i2, 65536)) {
                this.varArgConstructors = new HashSet();
                this.canonicalSignature = new StringBuilder("(");
                this.syntheticSignatures = EnumSet.noneOf(ObjectMethod.class);
            }
        }

        @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
        public void visitSource(String str, String str2) {
            if (str != null && StringUtil.endsWithIgnoreCase(str, ".groovy")) {
                this.trustInnerClasses = false;
            }
            super.visitSource(str, str2);
        }

        @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
        public RecordComponentVisitor visitRecordComponent(String str, String str2, String str3) {
            if (!isRecord()) {
                return null;
            }
            this.canonicalSignature.append(str2);
            this.lastComponent = str;
            return null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isRecord() {
            return this.varArgConstructors != null;
        }

        @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
        public MethodVisitor visitMethod(int i, final String str, String str2, String str3, String[] strArr) {
            if (!isRecord()) {
                return null;
            }
            if (str.equals("<init>") && BitUtil.isSet(i, 128)) {
                this.varArgConstructors.add(str2);
            }
            final ObjectMethod objectMethodFrom = ObjectMethod.from(str, str2);
            if (objectMethodFrom != null) {
                return new MethodVisitor(589824) { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.compiled.FirstPassData.1FirstPassVisitor.1
                    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
                    public void visitInvokeDynamicInsn(String str4, String str5, Handle handle, Object... objArr) {
                        if (str4.equals(str) && handle.getName().equals("bootstrap") && handle.getOwner().equals("java/lang/runtime/ObjectMethods")) {
                            C1FirstPassVisitor.this.syntheticSignatures.add(objectMethodFrom);
                        }
                    }
                };
            }
            return null;
        }

        @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
        public void visitInnerClass(String str, String str2, String str3, int i) {
            if (str2 == null || str3 == null) {
                return;
            }
            this.mapping.put(str, new InnerClassEntry(str2, str3, BitUtil.isSet(i, 8)));
        }
    }

    /* JADX WARN: Code duplicated, block: B:12:0x0035  */
    private static FirstPassData fromClassBytes(byte[] bArr) {
        String str;
        C1FirstPassVisitor c1FirstPassVisitor = new C1FirstPassVisitor();
        try {
            new ClassReader(bArr).accept(c1FirstPassVisitor, ClsFileImpl.EMPTY_ATTRIBUTES, 4);
        } catch (Exception e) {
            LOG.debug(e);
        }
        if (c1FirstPassVisitor.isRecord()) {
            c1FirstPassVisitor.canonicalSignature.append(")V");
            if (c1FirstPassVisitor.varArgConstructors.contains(c1FirstPassVisitor.canonicalSignature.toString())) {
                str = c1FirstPassVisitor.lastComponent;
            } else {
                str = null;
            }
        } else {
            str = null;
        }
        Set<ObjectMethod> setEmptySet = c1FirstPassVisitor.syntheticSignatures == null ? Collections.emptySet() : c1FirstPassVisitor.syntheticSignatures;
        if (str == null && c1FirstPassVisitor.mapping.isEmpty() && setEmptySet.isEmpty() && c1FirstPassVisitor.trustInnerClasses) {
            FirstPassData firstPassData = EMPTY;
            if (firstPassData == null) {
                $$$reportNull$$$0(14);
            }
            return firstPassData;
        }
        return new FirstPassData(c1FirstPassVisitor.mapping, str, setEmptySet, c1FirstPassVisitor.trustInnerClasses);
    }

    private enum ObjectMethod {
        EQUALS(Namer.EQUALS_METHOD_NAME, "(Ljava/lang/Object;)Z"),
        HASH_CODE("hashCode", "()I"),
        TO_STRING("toString", "()Ljava/lang/String;");

        private final String myDesc;
        private final String myName;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i == 1 || i == 3) {
                objArr[0] = "desc";
            } else {
                objArr[0] = "name";
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/FirstPassData$ObjectMethod";
            if (i == 2 || i == 3) {
                objArr[2] = TypedValues.TransitionType.S_FROM;
            } else {
                objArr[2] = "<init>";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        ObjectMethod(String str, String str2) {
            if (str == null) {
                $$$reportNull$$$0(0);
            }
            if (str2 == null) {
                $$$reportNull$$$0(1);
            }
            this.myName = str;
            this.myDesc = str2;
        }

        static ObjectMethod from(String str, String str2) {
            if (str == null) {
                $$$reportNull$$$0(2);
            }
            if (str2 == null) {
                $$$reportNull$$$0(3);
            }
            for (ObjectMethod objectMethod : values()) {
                if (objectMethod.myName.equals(str) && objectMethod.myDesc.equals(str2)) {
                    return objectMethod;
                }
            }
            return null;
        }
    }
}
