package org.jetbrains.kotlin.cli.jvm.compiler.builder;

import java.util.List;
import org.jetbrains.kotlin.asJava.builder.ClsWrapperStubPsiFactory;
import org.jetbrains.kotlin.asJava.builder.LightElementOrigin;
import org.jetbrains.kotlin.asJava.builder.LightElementOriginKt;
import org.jetbrains.kotlin.codegen.AbstractClassBuilder;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.compiled.InnerClassSourceStrategy;
import org.jetbrains.kotlin.com.intellij.psi.impl.compiled.StubBuildingVisitor;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiClassStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiJavaFileStub;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubBase;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.util.containers.Stack;
import org.jetbrains.kotlin.fileClasses.OldPackageFacadeClassUtils;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOrigin;
import org.jetbrains.org.objectweb.asm.ClassVisitor;
import org.jetbrains.org.objectweb.asm.FieldVisitor;
import org.jetbrains.org.objectweb.asm.MethodVisitor;

/* JADX INFO: loaded from: classes3.dex */
public class StubClassBuilder extends AbstractClassBuilder {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final InnerClassSourceStrategy<Object> EMPTY_STRATEGY = new InnerClassSourceStrategy<Object>() { // from class: org.jetbrains.kotlin.cli.jvm.compiler.builder.StubClassBuilder.1
        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.InnerClassSourceStrategy
        public Object findInnerClass(String str, Object obj) {
            return null;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.InnerClassSourceStrategy
        public void accept(Object obj, StubBuildingVisitor<Object> stubBuildingVisitor) {
            throw new UnsupportedOperationException("Shall not be called!");
        }
    };
    private final PsiJavaFileStub fileStub;
    private boolean isPackageClass;
    private final StubElement parent;
    private final Stack<StubElement> parentStack;
    private StubBuildingVisitor v;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 8 || i == 12 || i == 16) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 8 || i == 12 || i == 16) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "fileStub";
                break;
            case 2:
            case 8:
            case 12:
            case 16:
                objArr[0] = "org/jetbrains/kotlin/cli/jvm/compiler/builder/StubClassBuilder";
                break;
            case 3:
            case 10:
            case 14:
                objArr[0] = "name";
                break;
            case 4:
                objArr[0] = "superName";
                break;
            case 5:
                objArr[0] = Namer.METADATA_SUPERTYPES;
                break;
            case 6:
                objArr[0] = "internalName";
                break;
            case 7:
                objArr[0] = "classStub";
                break;
            case 9:
            case 13:
            case 17:
                objArr[0] = "origin";
                break;
            case 11:
            case 15:
                objArr[0] = "desc";
                break;
            default:
                objArr[0] = "parentStack";
                break;
        }
        if (i == 2) {
            objArr[1] = "getVisitor";
        } else if (i == 8) {
            objArr[1] = "getPackageInternalNamePrefix";
        } else if (i == 12) {
            objArr[1] = "newMethod";
        } else if (i != 16) {
            objArr[1] = "org/jetbrains/kotlin/cli/jvm/compiler/builder/StubClassBuilder";
        } else {
            objArr[1] = "newField";
        }
        switch (i) {
            case 2:
            case 8:
            case 12:
            case 16:
                break;
            case 3:
            case 4:
            case 5:
                objArr[2] = "defineClass";
                break;
            case 6:
                objArr[2] = "calculateShortName";
                break;
            case 7:
                objArr[2] = "getClassInternalNamePrefix";
                break;
            case 9:
            case 10:
            case 11:
                objArr[2] = "newMethod";
                break;
            case 13:
            case 14:
            case 15:
                objArr[2] = "newField";
                break;
            case 17:
                objArr[2] = "markLastChild";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 8 && i != 12 && i != 16) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public StubClassBuilder(Stack<StubElement> stack, PsiJavaFileStub psiJavaFileStub) {
        if (stack == null) {
            $$$reportNull$$$0(0);
        }
        if (psiJavaFileStub == null) {
            $$$reportNull$$$0(1);
        }
        this.isPackageClass = false;
        this.parentStack = stack;
        this.parent = stack.peek();
        this.fileStub = psiJavaFileStub;
    }

    @Override // org.jetbrains.kotlin.codegen.ClassBuilder
    public ClassVisitor getVisitor() {
        StubBuildingVisitor stubBuildingVisitor = this.v;
        if (stubBuildingVisitor == null) {
            $$$reportNull$$$0(2);
        }
        return stubBuildingVisitor;
    }

    @Override // org.jetbrains.kotlin.codegen.AbstractClassBuilder, org.jetbrains.kotlin.codegen.ClassBuilder
    public void defineClass(PsiElement psiElement, int i, int i2, String str, String str2, String str3, String[] strArr) {
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        if (str3 == null) {
            $$$reportNull$$$0(4);
        }
        if (strArr == null) {
            $$$reportNull$$$0(5);
        }
        this.v = new StubBuildingVisitor(null, EMPTY_STRATEGY, this.parent, i2, calculateShortName(str));
        super.defineClass(psiElement, i, i2, str, str2, str3, strArr);
        if (psiElement instanceof KtFile) {
            String packageClassName = OldPackageFacadeClassUtils.getPackageClassName(((KtFile) psiElement).getPackageFqName());
            if (str.equals(packageClassName) || str.endsWith("/" + packageClassName)) {
                this.isPackageClass = true;
            }
        }
        if (!this.isPackageClass) {
            this.parentStack.push(this.v.getResult());
        }
        ((StubBase) this.v.getResult()).putUserData(ClsWrapperStubPsiFactory.ORIGIN, LightElementOriginKt.toLightClassOrigin(psiElement));
    }

    private String calculateShortName(String str) {
        String classInternalNamePrefix;
        if (str == null) {
            $$$reportNull$$$0(6);
        }
        StubElement stubElement = this.parent;
        if (stubElement instanceof PsiJavaFileStub) {
            return str.substring(getPackageInternalNamePrefix().length());
        }
        if (!(stubElement instanceof PsiClassStub) || (classInternalNamePrefix = getClassInternalNamePrefix((PsiClassStub) stubElement)) == null) {
            return null;
        }
        return str.substring(classInternalNamePrefix.length());
    }

    private String getClassInternalNamePrefix(PsiClassStub psiClassStub) {
        if (psiClassStub == null) {
            $$$reportNull$$$0(7);
        }
        String packageName = this.fileStub.getPackageName();
        String qualifiedName = psiClassStub.getQualifiedName();
        if (qualifiedName == null) {
            return null;
        }
        if (packageName.isEmpty()) {
            return qualifiedName.replace('.', '$') + "$";
        }
        return packageName.replace('.', '/') + "/" + qualifiedName.substring(packageName.length() + 1).replace('.', '$') + "$";
    }

    private String getPackageInternalNamePrefix() {
        String packageName = this.fileStub.getPackageName();
        if (packageName.isEmpty()) {
            return "";
        }
        String str = packageName.replace('.', '/') + "/";
        if (str == null) {
            $$$reportNull$$$0(8);
        }
        return str;
    }

    @Override // org.jetbrains.kotlin.codegen.AbstractClassBuilder, org.jetbrains.kotlin.codegen.ClassBuilder
    public MethodVisitor newMethod(JvmDeclarationOrigin jvmDeclarationOrigin, int i, String str, String str2, String str3, String[] strArr) {
        if (jvmDeclarationOrigin == null) {
            $$$reportNull$$$0(9);
        }
        if (str == null) {
            $$$reportNull$$$0(10);
        }
        if (str2 == null) {
            $$$reportNull$$$0(11);
        }
        MethodVisitor methodVisitorNewMethod = super.newMethod(jvmDeclarationOrigin, i, str, str2, str3, strArr);
        if (methodVisitorNewMethod != EMPTY_METHOD_VISITOR) {
            markLastChild(jvmDeclarationOrigin);
        }
        if (methodVisitorNewMethod == null) {
            $$$reportNull$$$0(12);
        }
        return methodVisitorNewMethod;
    }

    @Override // org.jetbrains.kotlin.codegen.AbstractClassBuilder, org.jetbrains.kotlin.codegen.ClassBuilder
    public FieldVisitor newField(JvmDeclarationOrigin jvmDeclarationOrigin, int i, String str, String str2, String str3, Object obj) {
        if (jvmDeclarationOrigin == null) {
            $$$reportNull$$$0(13);
        }
        if (str == null) {
            $$$reportNull$$$0(14);
        }
        if (str2 == null) {
            $$$reportNull$$$0(15);
        }
        FieldVisitor fieldVisitorNewField = super.newField(jvmDeclarationOrigin, i, str, str2, str3, obj);
        if (fieldVisitorNewField != EMPTY_FIELD_VISITOR) {
            markLastChild(jvmDeclarationOrigin);
        }
        if (fieldVisitorNewField == null) {
            $$$reportNull$$$0(16);
        }
        return fieldVisitorNewField;
    }

    private void markLastChild(JvmDeclarationOrigin jvmDeclarationOrigin) {
        if (jvmDeclarationOrigin == null) {
            $$$reportNull$$$0(17);
        }
        List<StubElement> childrenStubs = this.v.getResult().getChildrenStubs();
        StubBase stubBase = (StubBase) childrenStubs.get(childrenStubs.size() - 1);
        LightElementOrigin lightElementOrigin = (LightElementOrigin) stubBase.getUserData(ClsWrapperStubPsiFactory.ORIGIN);
        if (lightElementOrigin != null) {
            PsiElement originalElement = lightElementOrigin.getOriginalElement();
            throw new IllegalStateException("Rewriting origin element: " + (originalElement != null ? originalElement.getText() : null) + " for stub " + stubBase.toString());
        }
        stubBase.putUserData(ClsWrapperStubPsiFactory.ORIGIN, LightElementOriginKt.toLightMemberOrigin(jvmDeclarationOrigin));
    }

    @Override // org.jetbrains.kotlin.codegen.AbstractClassBuilder, org.jetbrains.kotlin.codegen.ClassBuilder
    public void done(boolean z) {
        if (!this.isPackageClass) {
            this.parentStack.pop();
        }
        super.done(z);
    }
}
