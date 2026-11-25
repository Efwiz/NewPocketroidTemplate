package com.reandroid.dex.common;

import com.reandroid.dex.smali.SmaliReader;
import com.reandroid.utils.collection.ArrayCollection;
import com.reandroid.utils.collection.ArrayIterator;
import io.netty.util.internal.StringUtil;
import java.util.HashMap;
import java.util.Iterator;
import java.util.function.Predicate;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.load.java.JvmAbi;

/* JADX INFO: loaded from: classes7.dex */
public class AccessFlag extends Modifier {
    public static final AccessFlag ABSTRACT;
    public static final AccessFlag ANNOTATION;
    public static final AccessFlag BRIDGE;
    public static final AccessFlag CONSTRUCTOR;
    public static final AccessFlag DECLARED_SYNCHRONIZED;
    public static final AccessFlag ENUM;
    public static final AccessFlag FINAL;
    public static final AccessFlag INTERFACE;
    public static final AccessFlag NATIVE;
    public static final AccessFlag PRIVATE;
    public static final AccessFlag PROTECTED;
    public static final AccessFlag PUBLIC;
    public static final AccessFlag STATIC;
    public static final AccessFlag STRICTFP;
    public static final AccessFlag SYNCHRONIZED;
    public static final AccessFlag SYNTHETIC;
    public static final AccessFlag TRANSIENT;
    private static final AccessFlag[] VALUES;
    public static final AccessFlag VARARGS;
    public static final AccessFlag VOLATILE;
    private static final HashMap<String, AccessFlag> accessFlagsByName;
    private final boolean validForClass;
    private final boolean validForField;
    private final boolean validForMethod;

    static {
        AccessFlag accessFlag = new AccessFlag(1, "public", true, true, true);
        PUBLIC = accessFlag;
        AccessFlag accessFlag2 = new AccessFlag(2, "private", true, true, true);
        PRIVATE = accessFlag2;
        AccessFlag accessFlag3 = new AccessFlag(4, "protected", true, true, true);
        PROTECTED = accessFlag3;
        AccessFlag accessFlag4 = new AccessFlag(8, "static", true, true, true);
        STATIC = accessFlag4;
        AccessFlag accessFlag5 = new AccessFlag(16, "final", true, true, true);
        FINAL = accessFlag5;
        AccessFlag accessFlag6 = new AccessFlag(32, "synchronized", false, true, false);
        SYNCHRONIZED = accessFlag6;
        AccessFlag accessFlag7 = new AccessFlag(64, "volatile", false, false, true);
        VOLATILE = accessFlag7;
        AccessFlag accessFlag8 = new AccessFlag(64, "bridge", false, true, false);
        BRIDGE = accessFlag8;
        AccessFlag accessFlag9 = new AccessFlag(128, "transient", false, false, true);
        TRANSIENT = accessFlag9;
        AccessFlag accessFlag10 = new AccessFlag(128, "varargs", false, true, false);
        VARARGS = accessFlag10;
        AccessFlag accessFlag11 = new AccessFlag(256, "native", false, true, false);
        NATIVE = accessFlag11;
        AccessFlag accessFlag12 = new AccessFlag(512, PsiKeyword.INTERFACE, true, false, false);
        INTERFACE = accessFlag12;
        AccessFlag accessFlag13 = new AccessFlag(1024, "abstract", true, true, false);
        ABSTRACT = accessFlag13;
        AccessFlag accessFlag14 = new AccessFlag(2048, "strictfp", false, true, false);
        STRICTFP = accessFlag14;
        AccessFlag accessFlag15 = new AccessFlag(4096, "synthetic", true, true, true);
        SYNTHETIC = accessFlag15;
        AccessFlag accessFlag16 = new AccessFlag(8192, "annotation", true, false, false);
        ANNOTATION = accessFlag16;
        AccessFlag accessFlag17 = new AccessFlag(16384, PsiKeyword.ENUM, true, false, true);
        ENUM = accessFlag17;
        AccessFlag accessFlag18 = new AccessFlag(65536, JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, false, true, false);
        CONSTRUCTOR = accessFlag18;
        AccessFlag accessFlag19 = new AccessFlag(131072, "declared-synchronized", false, true, false);
        DECLARED_SYNCHRONIZED = accessFlag19;
        AccessFlag[] accessFlagArr = {accessFlag, accessFlag2, accessFlag3, accessFlag4, accessFlag5, accessFlag6, accessFlag7, accessFlag8, accessFlag9, accessFlag10, accessFlag11, accessFlag12, accessFlag13, accessFlag14, accessFlag15, accessFlag16, accessFlag17, accessFlag18, accessFlag19};
        VALUES = accessFlagArr;
        accessFlagsByName = new HashMap<>();
        for (AccessFlag accessFlag20 : accessFlagArr) {
            accessFlagsByName.put(accessFlag20.getName(), accessFlag20);
        }
    }

    private AccessFlag(int i, String str, boolean z, boolean z2, boolean z3) {
        super(i, str);
        this.validForClass = z;
        this.validForMethod = z2;
        this.validForField = z3;
    }

    @Override // com.reandroid.dex.common.Modifier
    public boolean isSet(int i) {
        return (i & getValue()) != 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isSetForField(int i) {
        return this.validForField && (i & getValue()) != 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isSetForMethod(int i) {
        return this.validForMethod && (i & getValue()) != 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isSetForClass(int i) {
        return this.validForClass && (i & getValue()) != 0;
    }

    public static Iterator<AccessFlag> valuesOfClass(final int i) {
        return getValues(new Predicate() { // from class: com.reandroid.dex.common.AccessFlag$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((AccessFlag) obj).isSetForClass(i);
            }
        });
    }

    public static Iterator<AccessFlag> valuesOfMethod(final int i) {
        return getValues(new Predicate() { // from class: com.reandroid.dex.common.AccessFlag$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((AccessFlag) obj).isSetForMethod(i);
            }
        });
    }

    public static Iterator<AccessFlag> valuesOfField(final int i) {
        return getValues(new Predicate() { // from class: com.reandroid.dex.common.AccessFlag$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((AccessFlag) obj).isSetForField(i);
            }
        });
    }

    public static AccessFlag valueOf(String str) {
        return accessFlagsByName.get(str);
    }

    public static Iterator<AccessFlag> getValues() {
        return getValues(null);
    }

    public static Iterator<AccessFlag> getValues(Predicate<AccessFlag> predicate) {
        return new ArrayIterator(VALUES, predicate);
    }

    public static AccessFlag[] parse(SmaliReader smaliReader) {
        ArrayCollection arrayCollection = new ArrayCollection();
        while (true) {
            AccessFlag next = parseNext(smaliReader);
            if (next == null) {
                break;
            }
            arrayCollection.add(next);
        }
        int size = arrayCollection.size();
        if (size == 0) {
            return null;
        }
        smaliReader.skipWhitespaces();
        return (AccessFlag[]) arrayCollection.toArray(new AccessFlag[size]);
    }

    private static AccessFlag parseNext(SmaliReader smaliReader) {
        smaliReader.skipWhitespaces();
        int iIndexOf = smaliReader.indexOf(StringUtil.SPACE);
        if (iIndexOf < 0) {
            return null;
        }
        int iPosition = smaliReader.position();
        AccessFlag accessFlagValueOf = valueOf(smaliReader.readString(iIndexOf - smaliReader.position()));
        if (accessFlagValueOf == null) {
            smaliReader.position(iPosition);
        }
        return accessFlagValueOf;
    }
}
