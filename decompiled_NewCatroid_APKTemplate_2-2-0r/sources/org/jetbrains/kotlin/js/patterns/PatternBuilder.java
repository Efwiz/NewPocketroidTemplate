package org.jetbrains.kotlin.js.patterns;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.jetbrains.kotlin.com.google.common.collect.Lists;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.incremental.IncrementalJvmCacheKt;
import org.jetbrains.kotlin.js.descriptorUtils.DescriptorUtilsKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.OverridingUtil;

/* JADX INFO: loaded from: classes5.dex */
public final class PatternBuilder {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final NamePredicate KOTLIN_NAME_PREDICATE = new NamePredicate(IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME);
    private static final Name KOTLIN_NAME = Name.identifier("Kotlin".toLowerCase());

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 6 || i == 9 || i == 10) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 6 || i == 9 || i == 10) ? 2 : 3];
        switch (i) {
            case 1:
            case 2:
            case 4:
            case 5:
            case 7:
            case 8:
            case 11:
                objArr[0] = "stringWithPattern";
                break;
            case 3:
            default:
                objArr[0] = "checker";
                break;
            case 6:
            case 9:
            case 10:
                objArr[0] = "org/jetbrains/kotlin/js/patterns/PatternBuilder";
                break;
            case 12:
            case 13:
            case 14:
                objArr[0] = "checkers";
                break;
            case 15:
                objArr[0] = "names";
                break;
        }
        if (i == 6) {
            objArr[1] = "parseFqNamesFromString";
        } else if (i == 9 || i == 10) {
            objArr[1] = "getNamePatternFromString";
        } else {
            objArr[1] = "org/jetbrains/kotlin/js/patterns/PatternBuilder";
        }
        switch (i) {
            case 5:
                objArr[2] = "parseFqNamesFromString";
                break;
            case 6:
            case 9:
            case 10:
                break;
            case 7:
                objArr[2] = "parseArgumentsFromString";
                break;
            case 8:
                objArr[2] = "getNamePatternFromString";
                break;
            case 11:
                objArr[2] = "getArgumentsPatternFromString";
                break;
            default:
                objArr[2] = "pattern";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 6 && i != 9 && i != 10) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    private PatternBuilder() {
    }

    public static DescriptorPredicate pattern(NamePredicate namePredicate, String str) {
        if (namePredicate == null) {
            $$$reportNull$$$0(0);
        }
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        ArrayList arrayListNewArrayList = Lists.newArrayList(namePredicate);
        arrayListNewArrayList.addAll(parseFqNamesFromString(str));
        return pattern(arrayListNewArrayList, parseArgumentsFromString(str));
    }

    public static DescriptorPredicate pattern(String str, NamePredicate namePredicate) {
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        if (namePredicate == null) {
            $$$reportNull$$$0(3);
        }
        ArrayList arrayListNewArrayList = Lists.newArrayList(parseFqNamesFromString(str));
        arrayListNewArrayList.add(namePredicate);
        return pattern(arrayListNewArrayList);
    }

    public static DescriptorPredicate pattern(String str) {
        if (str == null) {
            $$$reportNull$$$0(4);
        }
        return pattern(parseFqNamesFromString(str), parseArgumentsFromString(str));
    }

    private static List<NamePredicate> parseFqNamesFromString(String str) {
        if (str == null) {
            $$$reportNull$$$0(5);
        }
        String[] strArrSplit = getNamePatternFromString(str).split("\\.");
        ArrayList arrayListNewArrayList = Lists.newArrayList();
        for (String str2 : strArrSplit) {
            arrayListNewArrayList.add(new NamePredicate(str2.split("\\|")));
        }
        if (arrayListNewArrayList == null) {
            $$$reportNull$$$0(6);
        }
        return arrayListNewArrayList;
    }

    private static List<NamePredicate> parseArgumentsFromString(String str) {
        if (str == null) {
            $$$reportNull$$$0(7);
        }
        String argumentsPatternFromString = getArgumentsPatternFromString(str);
        if (argumentsPatternFromString == null) {
            return null;
        }
        ArrayList arrayListNewArrayList = Lists.newArrayList();
        if (argumentsPatternFromString.isEmpty()) {
            return arrayListNewArrayList;
        }
        for (String str2 : argumentsPatternFromString.split("\\,")) {
            arrayListNewArrayList.add(new NamePredicate(str2.split("\\|")));
        }
        return arrayListNewArrayList;
    }

    private static String getNamePatternFromString(String str) {
        if (str == null) {
            $$$reportNull$$$0(8);
        }
        int iIndexOf = str.indexOf("(");
        if (iIndexOf < 0) {
            if (str == null) {
                $$$reportNull$$$0(9);
            }
            return str;
        }
        String strSubstring = str.substring(0, iIndexOf);
        if (strSubstring == null) {
            $$$reportNull$$$0(10);
        }
        return strSubstring;
    }

    private static String getArgumentsPatternFromString(String str) {
        if (str == null) {
            $$$reportNull$$$0(11);
        }
        int iIndexOf = str.indexOf("(");
        if (iIndexOf < 0) {
            return null;
        }
        return str.substring(iIndexOf + 1, str.indexOf(Constant.AFTER_QUTO));
    }

    private static DescriptorPredicate pattern(List<NamePredicate> list) {
        if (list == null) {
            $$$reportNull$$$0(12);
        }
        return pattern(list, (List<NamePredicate>) null);
    }

    private static DescriptorPredicate pattern(List<NamePredicate> list, List<NamePredicate> list2) {
        if (list == null) {
            $$$reportNull$$$0(13);
        }
        final ArrayList arrayListNewArrayList = Lists.newArrayList();
        if (!list.get(0).test(KOTLIN_NAME)) {
            arrayListNewArrayList.add(KOTLIN_NAME_PREDICATE);
        }
        arrayListNewArrayList.addAll(list);
        final ArrayList arrayListNewArrayList2 = list2 != null ? Lists.newArrayList(list2) : null;
        return new DescriptorPredicate() { // from class: org.jetbrains.kotlin.js.patterns.PatternBuilder.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[3];
                if (i != 2) {
                    objArr[0] = "descriptor";
                } else {
                    objArr[0] = "nameParts";
                }
                objArr[1] = "org/jetbrains/kotlin/js/patterns/PatternBuilder$1";
                if (i == 1) {
                    objArr[2] = "checkAllArgumentsValidIfNeeded";
                } else if (i != 2) {
                    objArr[2] = "doApply";
                } else {
                    objArr[2] = "allNamePartsValid";
                }
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
            }

            @Override // java.util.function.Predicate
            public boolean test(FunctionDescriptor functionDescriptor) {
                try {
                    return doApply(functionDescriptor);
                } catch (IllegalArgumentException unused) {
                    return false;
                }
            }

            private boolean doApply(FunctionDescriptor functionDescriptor) {
                if (functionDescriptor == null) {
                    $$$reportNull$$$0(0);
                }
                List<Name> listPathSegments = DescriptorUtils.getFqName(functionDescriptor).pathSegments();
                return listPathSegments.size() == arrayListNewArrayList.size() && allNamePartsValid(listPathSegments) && checkAllArgumentsValidIfNeeded(functionDescriptor);
            }

            private boolean checkAllArgumentsValidIfNeeded(FunctionDescriptor functionDescriptor) {
                if (functionDescriptor == null) {
                    $$$reportNull$$$0(1);
                }
                if (arrayListNewArrayList2 != null) {
                    List<ValueParameterDescriptor> valueParameters = functionDescriptor.getValueParameters();
                    if (valueParameters.size() != arrayListNewArrayList2.size()) {
                        return false;
                    }
                    for (int i = 0; i < valueParameters.size(); i++) {
                        if (!((NamePredicate) arrayListNewArrayList2.get(i)).test(DescriptorUtilsKt.getNameIfStandardType(valueParameters.get(i).getType()))) {
                            return false;
                        }
                    }
                }
                return true;
            }

            private boolean allNamePartsValid(List<Name> list3) {
                if (list3 == null) {
                    $$$reportNull$$$0(2);
                }
                for (int i = 0; i < list3.size(); i++) {
                    if (!((NamePredicate) arrayListNewArrayList.get(i)).test(list3.get(i))) {
                        return false;
                    }
                }
                return true;
            }
        };
    }

    public static DescriptorPredicate pattern(NamePredicate... namePredicateArr) {
        if (namePredicateArr == null) {
            $$$reportNull$$$0(14);
        }
        return pattern((List<NamePredicate>) Arrays.asList(namePredicateArr));
    }

    public static DescriptorPredicateImpl pattern(String... strArr) {
        if (strArr == null) {
            $$$reportNull$$$0(15);
        }
        return new DescriptorPredicateImpl(strArr);
    }

    public static class DescriptorPredicateImpl implements DescriptorPredicate {
        private boolean checkOverridden;
        private final String[] names;
        private String receiverFqName;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "callable", "org/jetbrains/kotlin/js/patterns/PatternBuilder$DescriptorPredicateImpl", "matches"));
        }

        public DescriptorPredicateImpl(String... strArr) {
            this.names = strArr;
        }

        public DescriptorPredicateImpl isExtensionOf(String str) {
            this.receiverFqName = str;
            return this;
        }

        public DescriptorPredicateImpl checkOverridden() {
            this.checkOverridden = true;
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r6v0, types: [org.jetbrains.kotlin.descriptors.CallableDescriptor] */
        /* JADX WARN: Type inference failed for: r6v1, types: [org.jetbrains.kotlin.descriptors.DeclarationDescriptor] */
        /* JADX WARN: Type inference failed for: r6v2, types: [org.jetbrains.kotlin.descriptors.DeclarationDescriptor] */
        private boolean matches(CallableDescriptor callableDescriptor) {
            if (callableDescriptor == 0) {
                $$$reportNull$$$0(0);
            }
            int length = this.names.length - 1;
            while (length != -1 && callableDescriptor.getShortName().asString().equals(this.names[length])) {
                length--;
                callableDescriptor = callableDescriptor.getContainingDeclaration();
                if (callableDescriptor instanceof PackageFragmentDescriptor) {
                    return length == 0 && this.names[0].equals(((PackageFragmentDescriptor) callableDescriptor).getFqName().asString());
                }
            }
            return false;
        }

        @Override // java.util.function.Predicate
        public boolean test(FunctionDescriptor functionDescriptor) {
            ReceiverParameterDescriptor extensionReceiverParameter = functionDescriptor.getExtensionReceiverParameter();
            if (extensionReceiverParameter != null && (this.receiverFqName == null || !DescriptorUtilsKt.getKotlinTypeFqName(extensionReceiverParameter.getType(), false).equals(this.receiverFqName))) {
                return false;
            }
            if (!(functionDescriptor.getContainingDeclaration() instanceof ClassDescriptor)) {
                return matches(functionDescriptor);
            }
            Iterator<CallableMemberDescriptor> it = OverridingUtil.getOverriddenDeclarations(functionDescriptor).iterator();
            while (it.hasNext()) {
                if (matches(it.next())) {
                    return true;
                }
            }
            if (this.checkOverridden) {
                Iterator it2 = DescriptorUtils.getAllOverriddenDescriptors(functionDescriptor).iterator();
                while (it2.hasNext()) {
                    if (matches((CallableDescriptor) it2.next())) {
                        return true;
                    }
                }
            }
            return false;
        }
    }
}
