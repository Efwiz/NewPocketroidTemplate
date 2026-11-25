package org.jetbrains.kotlin.name;

import androidx.exifinterface.media.ExifInterface;
import com.reandroid.arsc.value.CompoundEntry;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: FqNamesUtil.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000*\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\b\b\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003H\u0002\u001a\u0010\u0010\u0005\u001a\u00020\u00012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u001a\n\u0010\u0007\u001a\u00020\b*\u00020\t\u001a+\u0010\n\u001a\u0004\u0018\u0001H\u000b\"\u0004\b\u0000\u0010\u000b*\u00020\f2\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u0002H\u000b0\u000e¢\u0006\u0002\u0010\u000f\u001a\u0012\u0010\u0010\u001a\u00020\u0001*\u00020\f2\u0006\u0010\u0011\u001a\u00020\f\u001a\n\u0010\u0012\u001a\u00020\u0001*\u00020\f\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\f2\u0006\u0010\u0011\u001a\u00020\f\u001a\f\u0010\u0013\u001a\u0004\u0018\u00010\f*\u00020\f\u001a\u0012\u0010\u0014\u001a\u00020\f*\u00020\f2\u0006\u0010\u0015\u001a\u00020\f¨\u0006\u0016"}, d2 = {"isSubpackageOf", "", "subpackageNameStr", "", "packageNameStr", "isValidJavaFqName", "qualifiedName", "callableIdForConstructor", "Lorg/jetbrains/kotlin/name/CallableId;", "Lorg/jetbrains/kotlin/name/ClassId;", "findValueForMostSpecificFqname", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "Lorg/jetbrains/kotlin/name/FqName;", CompoundEntry.NAME_values, "", "(Lorg/jetbrains/kotlin/name/FqName;Ljava/util/Map;)Ljava/lang/Object;", "isChildOf", "packageName", "isOneSegmentFQN", "parentOrNull", "tail", "prefix", "compiler.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FqNamesUtilKt {

    /* JADX INFO: compiled from: FqNamesUtil.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[State.values().length];
            try {
                iArr[State.BEGINNING.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[State.AFTER_DOT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[State.MIDDLE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static final boolean isSubpackageOf(FqName fqName, FqName packageName) {
        Intrinsics.checkNotNullParameter(fqName, "<this>");
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        if (Intrinsics.areEqual(fqName, packageName) || packageName.isRoot()) {
            return true;
        }
        String strAsString = fqName.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        String strAsString2 = packageName.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString2, "asString(...)");
        return isSubpackageOf(strAsString, strAsString2);
    }

    public static final boolean isChildOf(FqName fqName, FqName packageName) {
        Intrinsics.checkNotNullParameter(fqName, "<this>");
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        return Intrinsics.areEqual(parentOrNull(fqName), packageName);
    }

    private static final boolean isSubpackageOf(String str, String str2) {
        return StringsKt.startsWith$default(str, str2, false, 2, (Object) null) && str.charAt(str2.length()) == '.';
    }

    public static final boolean isOneSegmentFQN(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "<this>");
        return !fqName.isRoot() && fqName.parent().isRoot();
    }

    public static final FqName tail(FqName fqName, FqName prefix) {
        Intrinsics.checkNotNullParameter(fqName, "<this>");
        Intrinsics.checkNotNullParameter(prefix, "prefix");
        if (!isSubpackageOf(fqName, prefix) || prefix.isRoot()) {
            return fqName;
        }
        if (Intrinsics.areEqual(fqName, prefix)) {
            FqName ROOT = FqName.ROOT;
            Intrinsics.checkNotNullExpressionValue(ROOT, "ROOT");
            return ROOT;
        }
        String strAsString = fqName.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        String strSubstring = strAsString.substring(prefix.asString().length() + 1);
        Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
        return new FqName(strSubstring);
    }

    public static final FqName parentOrNull(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "<this>");
        if (fqName.isRoot()) {
            return null;
        }
        return fqName.parent();
    }

    public static final boolean isValidJavaFqName(String str) {
        if (str == null) {
            return false;
        }
        State state = State.BEGINNING;
        for (int i = 0; i < str.length(); i++) {
            char cCharAt = str.charAt(i);
            int i2 = WhenMappings.$EnumSwitchMapping$0[state.ordinal()];
            if (i2 == 1 || i2 == 2) {
                if (!Character.isJavaIdentifierStart(cCharAt)) {
                    return false;
                }
                state = State.MIDDLE;
            } else if (i2 != 3) {
                continue;
            } else if (cCharAt == '.') {
                state = State.AFTER_DOT;
            } else if (!Character.isJavaIdentifierPart(cCharAt)) {
                return false;
            }
        }
        return state != State.AFTER_DOT;
    }

    public static final CallableId callableIdForConstructor(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "<this>");
        if (classId.isNestedClass()) {
            ClassId outerClassId = classId.getOuterClassId();
            Intrinsics.checkNotNull(outerClassId);
            Name shortClassName = classId.getShortClassName();
            Intrinsics.checkNotNullExpressionValue(shortClassName, "getShortClassName(...)");
            return new CallableId(outerClassId, shortClassName);
        }
        FqName packageFqName = classId.getPackageFqName();
        Intrinsics.checkNotNullExpressionValue(packageFqName, "getPackageFqName(...)");
        Name shortClassName2 = classId.getShortClassName();
        Intrinsics.checkNotNullExpressionValue(shortClassName2, "getShortClassName(...)");
        return new CallableId(packageFqName, shortClassName2);
    }

    public static final <V> V findValueForMostSpecificFqname(FqName fqName, Map<FqName, ? extends V> values) {
        Object next;
        Intrinsics.checkNotNullParameter(fqName, "<this>");
        Intrinsics.checkNotNullParameter(values, "values");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry<FqName, ? extends V> entry : values.entrySet()) {
            FqName key = entry.getKey();
            if (Intrinsics.areEqual(fqName, key) || isChildOf(fqName, key)) {
                linkedHashMap.put(entry.getKey(), entry.getValue());
            }
        }
        if (linkedHashMap.isEmpty()) {
            linkedHashMap = null;
        }
        if (linkedHashMap == null) {
            return null;
        }
        Iterator it = linkedHashMap.entrySet().iterator();
        if (it.hasNext()) {
            next = it.next();
            if (it.hasNext()) {
                int length = tail((FqName) ((Map.Entry) next).getKey(), fqName).asString().length();
                do {
                    Object next2 = it.next();
                    int length2 = tail((FqName) ((Map.Entry) next2).getKey(), fqName).asString().length();
                    if (length > length2) {
                        next = next2;
                        length = length2;
                    }
                } while (it.hasNext());
            }
        } else {
            next = null;
        }
        Map.Entry entry2 = (Map.Entry) next;
        if (entry2 != null) {
            return (V) entry2.getValue();
        }
        return null;
    }
}
