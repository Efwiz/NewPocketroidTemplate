package com.reandroid.dex.model;

import com.reandroid.dex.key.TypeKey;
import java.util.Iterator;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes11.dex */
public class DalvikUtil {
    public static int cleanMissingMembers(DexClassRepository dexClassRepository) {
        Iterator<DexClass> dexClasses = dexClassRepository.getDexClasses();
        int iCleanMissingMembers = 0;
        while (dexClasses.hasNext()) {
            iCleanMissingMembers += cleanMissingMembers(dexClasses.next());
        }
        return iCleanMissingMembers;
    }

    public static int cleanMissingMembers(DexClass dexClass) {
        Iterator<DexAnnotation> annotations = dexClass.getAnnotations(TypeKey.DALVIK_MemberClass);
        int i = 0;
        while (annotations.hasNext()) {
            DexAnnotation next = annotations.next();
            DexAnnotationElement dexAnnotationElement = next.get("value");
            if (dexAnnotationElement != null) {
                DexValue value = dexAnnotationElement.getValue();
                if (value instanceof DexValueArray) {
                    DexValueArray dexValueArray = (DexValueArray) value;
                    if (dexValueArray.removeIf(new Predicate() { // from class: com.reandroid.dex.model.DalvikUtil$$ExternalSyntheticLambda0
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            return DalvikUtil.lambda$cleanMissingMembers$0((DexValue) obj);
                        }
                    })) {
                        i++;
                    }
                    if (dexValueArray.isEmpty()) {
                        dexAnnotationElement.removeSelf();
                    }
                }
                if (next.size() == 0) {
                    next.removeSelf();
                }
            }
        }
        return i;
    }

    static /* synthetic */ boolean lambda$cleanMissingMembers$0(DexValue dexValue) {
        return !dexValue.getClassRepository().containsClass(dexValue.getTypeKey());
    }
}
