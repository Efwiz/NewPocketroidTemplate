package org.jetbrains.kotlin.com.intellij.psi.impl.light;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.jar.Manifest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import name.antonsmirnov.firmata.FormatHelper;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.com.intellij.lang.java.JavaLanguage;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentation;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentationProviders;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.util.NotNullLazyValue;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VfsUtilCore;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileVisitor;
import org.jetbrains.kotlin.com.intellij.psi.JavaDirectoryService;
import org.jetbrains.kotlin.com.intellij.psi.PsiDirectory;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaModule;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaModuleReference;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaModuleReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiPackage;
import org.jetbrains.kotlin.com.intellij.psi.PsiPackageAccessibilityStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiProvidesStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiRequiresStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiUsesStatement;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocComment;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValuesManager;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiModificationTracker;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.com.intellij.util.ObjectUtils;
import org.jetbrains.kotlin.library.KotlinLibraryLayoutKt;

/* JADX INFO: loaded from: classes3.dex */
public final class LightJavaModule extends LightElement implements PsiJavaModule {
    private final NotNullLazyValue<List<PsiPackageAccessibilityStatement>> myExports;
    private final LightJavaModuleReferenceElement myRefElement;
    private final VirtualFile myRoot;

    /* JADX WARN: Code duplicated, block: B:8:0x0012  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 13 && i != 17 && i != 23) {
            switch (i) {
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 13 && i != 17 && i != 23) {
            switch (i) {
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
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
            case 15:
            case 20:
                objArr[0] = "root";
                break;
            case 2:
            case 11:
            case 12:
            case 21:
            case 22:
                objArr[0] = "name";
                break;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 13:
            case 17:
            case 23:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightJavaModule";
                break;
            case 14:
            case 19:
            default:
                objArr[0] = "manager";
                break;
            case 16:
                objArr[0] = "jarRoot";
                break;
            case 18:
                objArr[0] = KotlinLibraryLayoutKt.KLIB_MANIFEST_FILE_NAME;
                break;
        }
        if (i == 13) {
            objArr[1] = "getNavigationElement";
        } else if (i != 17 && i != 23) {
            switch (i) {
                case 3:
                    objArr[1] = "getRootVirtualFile";
                    break;
                case 4:
                    objArr[1] = "getRequires";
                    break;
                case 5:
                    objArr[1] = "getExports";
                    break;
                case 6:
                    objArr[1] = "getOpens";
                    break;
                case 7:
                    objArr[1] = "getUses";
                    break;
                case 8:
                    objArr[1] = "getProvides";
                    break;
                case 9:
                    objArr[1] = "getNameIdentifier";
                    break;
                case 10:
                    objArr[1] = "getName";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightJavaModule";
                    break;
            }
        } else {
            objArr[1] = "moduleName";
        }
        switch (i) {
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 13:
            case 17:
            case 23:
                break;
            case 11:
                objArr[2] = "setName";
                break;
            case 12:
                objArr[2] = "hasModifierProperty";
                break;
            case 14:
            case 15:
                objArr[2] = "findModule";
                break;
            case 16:
            case 22:
                objArr[2] = "moduleName";
                break;
            case 18:
                objArr[2] = "claimedModuleName";
                break;
            case 19:
            case 20:
            case 21:
                objArr[2] = CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME;
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 13 && i != 17 && i != 23) {
            switch (i) {
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaDocumentedElement
    /* JADX INFO: renamed from: getDocComment */
    public PsiDocComment mo6091getDocComment() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    /* JADX INFO: renamed from: getModifierList */
    public PsiModifierList mo6095getModifierList() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    public boolean hasModifierProperty(String str) {
        if (str != null) {
            return false;
        }
        $$$reportNull$$$0(12);
        return false;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private LightJavaModule(PsiManager psiManager, VirtualFile virtualFile, String str) {
        super(psiManager, JavaLanguage.INSTANCE);
        if (psiManager == null) {
            $$$reportNull$$$0(0);
        }
        if (virtualFile == null) {
            $$$reportNull$$$0(1);
        }
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        this.myExports = NotNullLazyValue.atomicLazy(new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.light.LightJavaModule$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.findExports();
            }
        });
        this.myRoot = virtualFile;
        this.myRefElement = new LightJavaModuleReferenceElement(psiManager, str);
    }

    public VirtualFile getRootVirtualFile() {
        VirtualFile virtualFile = this.myRoot;
        if (virtualFile == null) {
            $$$reportNull$$$0(3);
        }
        return virtualFile;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaModule
    public Iterable<PsiRequiresStatement> getRequires() {
        List listEmptyList = Collections.emptyList();
        if (listEmptyList == null) {
            $$$reportNull$$$0(4);
        }
        return listEmptyList;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaModule
    public Iterable<PsiPackageAccessibilityStatement> getExports() {
        List<PsiPackageAccessibilityStatement> value = this.myExports.getValue();
        if (value == null) {
            $$$reportNull$$$0(5);
        }
        return value;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<PsiPackageAccessibilityStatement> findExports() throws Throwable {
        final ArrayList arrayList = new ArrayList();
        VfsUtilCore.visitChildrenRecursively(this.myRoot, new VirtualFileVisitor<Void>(new VirtualFileVisitor.Option[0]) { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.light.LightJavaModule.1
            private final JavaDirectoryService service = JavaDirectoryService.getInstance();

            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "file", "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightJavaModule$1", "visitFile"));
            }

            @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileVisitor
            public boolean visitFile(VirtualFile virtualFile) {
                PsiDirectory psiDirectoryFindDirectory;
                PsiPackage psiPackage;
                if (virtualFile == null) {
                    $$$reportNull$$$0(0);
                }
                if (virtualFile.isDirectory() && !LightJavaModule.this.myRoot.equals(virtualFile) && (psiDirectoryFindDirectory = LightJavaModule.this.getManager().findDirectory(virtualFile)) != null && (psiPackage = this.service.getPackage(psiDirectoryFindDirectory)) != null) {
                    String qualifiedName = psiPackage.getQualifiedName();
                    if (!qualifiedName.isEmpty() && !PsiUtil.isPackageEmpty(new PsiDirectory[]{psiDirectoryFindDirectory}, qualifiedName)) {
                        arrayList.add(new LightPackageAccessibilityStatement(LightJavaModule.this.getManager(), qualifiedName));
                    }
                }
                return true;
            }
        });
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaModule
    public Iterable<PsiPackageAccessibilityStatement> getOpens() {
        List listEmptyList = Collections.emptyList();
        if (listEmptyList == null) {
            $$$reportNull$$$0(6);
        }
        return listEmptyList;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaModule
    public Iterable<PsiUsesStatement> getUses() {
        List listEmptyList = Collections.emptyList();
        if (listEmptyList == null) {
            $$$reportNull$$$0(7);
        }
        return listEmptyList;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaModule
    public Iterable<PsiProvidesStatement> getProvides() {
        List listEmptyList = Collections.emptyList();
        if (listEmptyList == null) {
            $$$reportNull$$$0(8);
        }
        return listEmptyList;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaModule, org.jetbrains.kotlin.com.intellij.psi.PsiNameIdentifierOwner
    /* JADX INFO: renamed from: getNameIdentifier */
    public PsiJavaModuleReferenceElement mo7392getNameIdentifier() {
        LightJavaModuleReferenceElement lightJavaModuleReferenceElement = this.myRefElement;
        if (lightJavaModuleReferenceElement == null) {
            $$$reportNull$$$0(9);
        }
        return lightJavaModuleReferenceElement;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    public String getName() {
        String referenceText = this.myRefElement.getReferenceText();
        if (referenceText == null) {
            $$$reportNull$$$0(10);
        }
        return referenceText;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
    public PsiElement setName(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(11);
        }
        throw new IncorrectOperationException("Cannot modify an automatic module '" + getName() + FormatHelper.QUOTE);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem
    public ItemPresentation getPresentation() {
        return ItemPresentationProviders.getItemPresentation(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getNavigationElement() {
        PsiElement psiElement = (PsiElement) ObjectUtils.notNull(this.myManager.findDirectory(this.myRoot), super.getNavigationElement());
        if (psiElement == null) {
            $$$reportNull$$$0(13);
        }
        return psiElement;
    }

    public boolean equals(Object obj) {
        if (obj instanceof LightJavaModule) {
            LightJavaModule lightJavaModule = (LightJavaModule) obj;
            if (this.myRoot.equals(lightJavaModule.myRoot) && getManager() == lightJavaModule.getManager()) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return (getName().hashCode() * 31) + getManager().hashCode();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiJavaModule:" + getName();
    }

    private static final class LightJavaModuleReferenceElement extends LightElement implements PsiJavaModuleReferenceElement {
        private final String myText;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 2 ? 3 : 2];
            if (i == 1) {
                objArr[0] = "text";
            } else if (i != 2) {
                objArr[0] = "manager";
            } else {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightJavaModule$LightJavaModuleReferenceElement";
            }
            if (i != 2) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightJavaModule$LightJavaModuleReferenceElement";
            } else {
                objArr[1] = "getReferenceText";
            }
            if (i != 2) {
                objArr[2] = "<init>";
            }
            String str2 = String.format(str, objArr);
            if (i == 2) {
                throw new IllegalStateException(str2);
            }
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiJavaModuleReference getReference() {
            return null;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        private LightJavaModuleReferenceElement(PsiManager psiManager, String str) {
            super(psiManager, JavaLanguage.INSTANCE);
            if (psiManager == null) {
                $$$reportNull$$$0(0);
            }
            if (str == null) {
                $$$reportNull$$$0(1);
            }
            this.myText = str;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaModuleReferenceElement
        public String getReferenceText() {
            String str = this.myText;
            if (str == null) {
                $$$reportNull$$$0(2);
            }
            return str;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public String toString() {
            return "PsiJavaModuleReference";
        }
    }

    private static class LightPackageAccessibilityStatement extends LightElement implements PsiPackageAccessibilityStatement {
        private final String myPackageName;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = (i == 2 || i == 3 || i == 4) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
            Object[] objArr = new Object[(i == 2 || i == 3 || i == 4) ? 2 : 3];
            if (i == 1) {
                objArr[0] = "packageName";
            } else if (i == 2 || i == 3 || i == 4) {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightJavaModule$LightPackageAccessibilityStatement";
            } else {
                objArr[0] = "manager";
            }
            if (i == 2) {
                objArr[1] = "getRole";
            } else if (i == 3) {
                objArr[1] = "getModuleReferences";
            } else if (i != 4) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightJavaModule$LightPackageAccessibilityStatement";
            } else {
                objArr[1] = "getModuleNames";
            }
            if (i != 2 && i != 3 && i != 4) {
                objArr[2] = "<init>";
            }
            String str2 = String.format(str, objArr);
            if (i != 2 && i != 3 && i != 4) {
                throw new IllegalArgumentException(str2);
            }
            throw new IllegalStateException(str2);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiPackageAccessibilityStatement
        public PsiJavaCodeReferenceElement getPackageReference() {
            return null;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        LightPackageAccessibilityStatement(PsiManager psiManager, String str) {
            super(psiManager, JavaLanguage.INSTANCE);
            if (psiManager == null) {
                $$$reportNull$$$0(0);
            }
            if (str == null) {
                $$$reportNull$$$0(1);
            }
            this.myPackageName = str;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiPackageAccessibilityStatement
        public PsiPackageAccessibilityStatement.Role getRole() {
            PsiPackageAccessibilityStatement.Role role = PsiPackageAccessibilityStatement.Role.EXPORTS;
            if (role == null) {
                $$$reportNull$$$0(2);
            }
            return role;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiPackageAccessibilityStatement
        public String getPackageName() {
            return this.myPackageName;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiPackageAccessibilityStatement
        public Iterable<PsiJavaModuleReferenceElement> getModuleReferences() {
            List listEmptyList = Collections.emptyList();
            if (listEmptyList == null) {
                $$$reportNull$$$0(3);
            }
            return listEmptyList;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiPackageAccessibilityStatement
        public List<String> getModuleNames() {
            List<String> listEmptyList = Collections.emptyList();
            if (listEmptyList == null) {
                $$$reportNull$$$0(4);
            }
            return listEmptyList;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public String toString() {
            return "PsiPackageAccessibilityStatement";
        }
    }

    @Deprecated
    public static LightJavaModule findModule(final PsiManager psiManager, final VirtualFile virtualFile) {
        if (psiManager == null) {
            $$$reportNull$$$0(14);
        }
        if (virtualFile == null) {
            $$$reportNull$$$0(15);
        }
        final PsiDirectory psiDirectoryFindDirectory = psiManager.findDirectory(virtualFile);
        if (psiDirectoryFindDirectory == null) {
            return null;
        }
        if (virtualFile.isInLocalFileSystem()) {
            return (LightJavaModule) CachedValuesManager.getCachedValue((PsiElement) psiDirectoryFindDirectory, new CachedValueProvider() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.light.LightJavaModule$$ExternalSyntheticLambda1
                @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider
                public final CachedValueProvider.Result compute() {
                    return LightJavaModule.lambda$findModule$0(virtualFile, psiManager);
                }
            });
        }
        return (LightJavaModule) CachedValuesManager.getCachedValue((PsiElement) psiDirectoryFindDirectory, new CachedValueProvider() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.light.LightJavaModule$$ExternalSyntheticLambda2
            @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider
            public final CachedValueProvider.Result compute() {
                PsiManager psiManager2 = psiManager;
                VirtualFile virtualFile2 = virtualFile;
                return CachedValueProvider.Result.create(new LightJavaModule(psiManager2, virtualFile2, LightJavaModule.moduleName(virtualFile2)), psiDirectoryFindDirectory);
            }
        });
    }

    static /* synthetic */ CachedValueProvider.Result lambda$findModule$0(VirtualFile virtualFile, PsiManager psiManager) {
        PsiFile psiFileFindFile;
        VirtualFile virtualFileFindFileByRelativePath = virtualFile.findFileByRelativePath("META-INF/MANIFEST.MF");
        if (virtualFileFindFileByRelativePath != null && (psiFileFindFile = psiManager.findFile(virtualFileFindFileByRelativePath)) != null) {
            String strClaimedModuleName = claimedModuleName(virtualFileFindFileByRelativePath);
            return CachedValueProvider.Result.create(strClaimedModuleName != null ? new LightJavaModule(psiManager, virtualFile, strClaimedModuleName) : null, psiFileFindFile);
        }
        return CachedValueProvider.Result.create((Object) null, PsiModificationTracker.MODIFICATION_COUNT);
    }

    public static String moduleName(VirtualFile virtualFile) {
        String strClaimedModuleName;
        if (virtualFile == null) {
            $$$reportNull$$$0(16);
        }
        VirtualFile virtualFileFindFileByRelativePath = virtualFile.findFileByRelativePath("META-INF/MANIFEST.MF");
        if (virtualFileFindFileByRelativePath == null || (strClaimedModuleName = claimedModuleName(virtualFileFindFileByRelativePath)) == null) {
            return moduleName(virtualFile.getNameWithoutExtension());
        }
        if (strClaimedModuleName == null) {
            $$$reportNull$$$0(17);
        }
        return strClaimedModuleName;
    }

    public static String claimedModuleName(VirtualFile virtualFile) {
        if (virtualFile == null) {
            $$$reportNull$$$0(18);
        }
        try {
            InputStream inputStream = virtualFile.getInputStream();
            try {
                String value = new Manifest(inputStream).getMainAttributes().getValue("Automatic-Module-Name");
                if (inputStream != null) {
                    inputStream.close();
                }
                return value;
            } catch (Throwable th) {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (IOException e) {
            Logger.getInstance((Class<?>) LightJavaModule.class).warn(virtualFile.getPath(), e);
            return null;
        }
    }

    public static LightJavaModule create(PsiManager psiManager, VirtualFile virtualFile, String str) {
        if (psiManager == null) {
            $$$reportNull$$$0(19);
        }
        if (virtualFile == null) {
            $$$reportNull$$$0(20);
        }
        if (str == null) {
            $$$reportNull$$$0(21);
        }
        return new LightJavaModule(psiManager, virtualFile, str);
    }

    public static String moduleName(String str) {
        if (str == null) {
            $$$reportNull$$$0(22);
        }
        Matcher matcher = Patterns.VERSION.matcher(str);
        if (matcher.find()) {
            str = str.substring(0, matcher.start());
        }
        String strTrimLeading = StringUtil.trimLeading(StringUtil.trimTrailing(Patterns.DOT_SEQUENCE.matcher(Patterns.NON_NAME.matcher(str).replaceAll(".")).replaceAll("."), '.'), '.');
        if (strTrimLeading == null) {
            $$$reportNull$$$0(23);
        }
        return strTrimLeading;
    }

    private static class Patterns {
        private static final Pattern VERSION = Pattern.compile("-(\\d+(\\.|$))");
        private static final Pattern NON_NAME = Pattern.compile("[^A-Za-z0-9]");
        private static final Pattern DOT_SEQUENCE = Pattern.compile("\\.{2,}");

        private Patterns() {
        }
    }
}
