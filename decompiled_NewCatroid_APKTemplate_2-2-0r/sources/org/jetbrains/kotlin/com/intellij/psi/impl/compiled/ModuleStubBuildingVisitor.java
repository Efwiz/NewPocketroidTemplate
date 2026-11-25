package org.jetbrains.kotlin.com.intellij.psi.impl.compiled;

import java.util.Arrays;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaClassReferenceListElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiJavaFileStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiJavaModuleStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl.PsiAnnotationStubImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl.PsiClassReferenceListStubImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl.PsiJavaModuleStubImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl.PsiModifierListStubImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl.PsiPackageAccessibilityStatementStubImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl.PsiProvidesStatementStubImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl.PsiRequiresStatementStubImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl.PsiUsesStatementStubImpl;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtilRt;
import org.jetbrains.kotlin.com.intellij.util.BitUtil;
import org.jetbrains.kotlin.com.intellij.util.Consumer;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.org.objectweb.asm.AnnotationVisitor;
import org.jetbrains.org.objectweb.asm.ClassVisitor;
import org.jetbrains.org.objectweb.asm.ModuleVisitor;

/* JADX INFO: loaded from: classes3.dex */
public class ModuleStubBuildingVisitor extends ClassVisitor {
    private static final Function<String, String> NAME_MAPPER = new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ModuleStubBuildingVisitor$$ExternalSyntheticLambda1
        @Override // org.jetbrains.kotlin.com.intellij.util.Function
        public final Object fun(Object obj) {
            return ((String) obj).replace('/', '.');
        }
    };
    private PsiModifierListStubImpl myModList;
    private final PsiJavaFileStub myParent;
    private PsiJavaModuleStub myResult;

    public ModuleStubBuildingVisitor(PsiJavaFileStub psiJavaFileStub) {
        super(589824);
        this.myParent = psiJavaFileStub;
    }

    public PsiJavaModuleStub getResult() {
        return this.myResult;
    }

    @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
    public ModuleVisitor visitModule(String str, int i, String str2) {
        this.myResult = new PsiJavaModuleStubImpl(this.myParent, str);
        this.myModList = new PsiModifierListStubImpl(this.myResult, moduleFlags(i));
        return new ModuleVisitor(589824) { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ModuleStubBuildingVisitor.1
            @Override // org.jetbrains.org.objectweb.asm.ModuleVisitor
            public void visitRequire(String str3, int i2, String str4) {
                if (ModuleStubBuildingVisitor.isGenerated(i2)) {
                    return;
                }
                new PsiModifierListStubImpl(new PsiRequiresStatementStubImpl(ModuleStubBuildingVisitor.this.myResult, str3), ModuleStubBuildingVisitor.requiresFlags(i2));
            }

            @Override // org.jetbrains.org.objectweb.asm.ModuleVisitor
            public void visitExport(String str3, int i2, String... strArr) {
                if (ModuleStubBuildingVisitor.isGenerated(i2)) {
                    return;
                }
                new PsiPackageAccessibilityStatementStubImpl(ModuleStubBuildingVisitor.this.myResult, JavaStubElementTypes.EXPORTS_STATEMENT, (String) ModuleStubBuildingVisitor.NAME_MAPPER.fun(str3), strArr == null ? null : Arrays.asList(strArr));
            }

            @Override // org.jetbrains.org.objectweb.asm.ModuleVisitor
            public void visitOpen(String str3, int i2, String... strArr) {
                if (ModuleStubBuildingVisitor.isGenerated(i2)) {
                    return;
                }
                new PsiPackageAccessibilityStatementStubImpl(ModuleStubBuildingVisitor.this.myResult, JavaStubElementTypes.OPENS_STATEMENT, (String) ModuleStubBuildingVisitor.NAME_MAPPER.fun(str3), strArr == null ? null : Arrays.asList(strArr));
            }

            @Override // org.jetbrains.org.objectweb.asm.ModuleVisitor
            public void visitUse(String str3) {
                new PsiUsesStatementStubImpl(ModuleStubBuildingVisitor.this.myResult, (String) ModuleStubBuildingVisitor.NAME_MAPPER.fun(str3));
            }

            @Override // org.jetbrains.org.objectweb.asm.ModuleVisitor
            public void visitProvide(String str3, String... strArr) {
                PsiProvidesStatementStubImpl psiProvidesStatementStubImpl = new PsiProvidesStatementStubImpl(ModuleStubBuildingVisitor.this.myResult, (String) ModuleStubBuildingVisitor.NAME_MAPPER.fun(str3));
                String[] strArr2 = (String[]) ContainerUtil.map2Array(strArr, String.class, ModuleStubBuildingVisitor.NAME_MAPPER);
                JavaClassReferenceListElementType javaClassReferenceListElementType = JavaStubElementTypes.PROVIDES_WITH_LIST;
                if (strArr2.length == 0) {
                    strArr2 = ArrayUtilRt.EMPTY_STRING_ARRAY;
                }
                new PsiClassReferenceListStubImpl(javaClassReferenceListElementType, psiProvidesStatementStubImpl, strArr2);
            }
        };
    }

    /* JADX INFO: renamed from: lambda$visitAnnotation$1$org-jetbrains-kotlin-com-intellij-psi-impl-compiled-ModuleStubBuildingVisitor, reason: not valid java name */
    /* synthetic */ void m6604x96e7b8f2(String str) {
        new PsiAnnotationStubImpl(this.myModList, str);
    }

    @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
    public AnnotationVisitor visitAnnotation(String str, boolean z) {
        return StubBuildingVisitor.getAnnotationTextCollector(str, new Consumer() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ModuleStubBuildingVisitor$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.util.Consumer
            public final void consume(Object obj) {
                this.f$0.m6604x96e7b8f2((String) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isGenerated(int i) {
        return BitUtil.isSet(i, 4096) || BitUtil.isSet(i, 32768);
    }

    private static int moduleFlags(int i) {
        return BitUtil.isSet(i, 32) ? 8192 : 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int requiresFlags(int i) {
        int i2 = BitUtil.isSet(i, 32) ? 16384 : 0;
        return BitUtil.isSet(i, 64) ? i2 | 8 : i2;
    }
}
