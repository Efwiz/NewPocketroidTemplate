package com.reandroid.dex.smali.formatters;

import com.reandroid.dex.key.MethodKey;
import com.reandroid.dex.model.DexClassRepository;
import com.reandroid.dex.model.DexMethod;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.utils.ObjectsUtil;
import com.reandroid.utils.collection.CollectionUtil;
import java.io.IOException;
import java.util.Iterator;

/* JADX INFO: loaded from: classes11.dex */
public interface MethodComment extends SmaliComment {
    void writeComment(SmaliWriter smaliWriter, MethodKey methodKey) throws IOException;

    public static class MethodOverrideComment implements MethodComment {
        private final DexClassRepository classRepository;

        public MethodOverrideComment(DexClassRepository dexClassRepository) {
            this.classRepository = dexClassRepository;
        }

        @Override // com.reandroid.dex.smali.formatters.MethodComment
        public void writeComment(SmaliWriter smaliWriter, MethodKey methodKey) throws IOException {
            DexMethod dexMethod;
            DexMethod declaredMethod = this.classRepository.getDeclaredMethod(methodKey);
            if (declaredMethod == null || declaredMethod.isDirect() || (dexMethod = (DexMethod) CollectionUtil.getFirst(declaredMethod.getSuperMethods())) == null) {
                return;
            }
            smaliWriter.newLine();
            smaliWriter.appendComment("overrides: ");
            smaliWriter.appendComment(dexMethod.getKey().getDeclaring().getTypeName());
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof MethodOverrideComment) && this.classRepository == ((MethodOverrideComment) obj).classRepository;
        }

        public int hashCode() {
            return ObjectsUtil.hash(this.classRepository, getClass());
        }
    }

    public static class MethodImplementComment implements MethodComment {
        private final DexClassRepository classRepository;

        public MethodImplementComment(DexClassRepository dexClassRepository) {
            this.classRepository = dexClassRepository;
        }

        @Override // com.reandroid.dex.smali.formatters.MethodComment
        public void writeComment(SmaliWriter smaliWriter, MethodKey methodKey) throws IOException {
            DexMethod declaredMethod = this.classRepository.getDeclaredMethod(methodKey);
            if (declaredMethod == null || declaredMethod.isDirect() || declaredMethod.getDexClass().isFinal()) {
                return;
            }
            Iterator<DexMethod> overriding = declaredMethod.getOverriding();
            while (overriding.hasNext()) {
                DexMethod next = overriding.next();
                smaliWriter.newLine();
                smaliWriter.appendComment("implemented-by: ");
                smaliWriter.appendComment(next.getKey().getDeclaring().getTypeName());
            }
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof MethodImplementComment) && this.classRepository == ((MethodImplementComment) obj).classRepository;
        }

        public int hashCode() {
            return ObjectsUtil.hash(this.classRepository, getClass());
        }
    }
}
