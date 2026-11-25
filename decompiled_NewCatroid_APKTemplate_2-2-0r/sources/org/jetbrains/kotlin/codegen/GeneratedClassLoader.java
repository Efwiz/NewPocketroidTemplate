package org.jetbrains.kotlin.codegen;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.Manifest;
import kotlin.collections.CollectionsKt;
import org.jetbrains.kotlin.backend.common.output.OutputFile;
import org.jetbrains.kotlin.utils.ExceptionUtilsKt;

/* JADX INFO: loaded from: classes3.dex */
public class GeneratedClassLoader extends URLClassLoader {
    private ClassFileFactory factory;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 4 || i == 5) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 3 || i == 4 || i == 5) ? 2 : 3];
        if (i == 1 || i == 2) {
            objArr[0] = "name";
        } else if (i == 3 || i == 4 || i == 5) {
            objArr[0] = "org/jetbrains/kotlin/codegen/GeneratedClassLoader";
        } else {
            objArr[0] = "factory";
        }
        if (i == 3 || i == 4) {
            objArr[1] = "findClass";
        } else if (i != 5) {
            objArr[1] = "org/jetbrains/kotlin/codegen/GeneratedClassLoader";
        } else {
            objArr[1] = "getAllGeneratedFiles";
        }
        if (i == 1) {
            objArr[2] = "createFakeURLForResource";
        } else if (i == 2) {
            objArr[2] = "findClass";
        } else if (i != 3 && i != 4 && i != 5) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 4 && i != 5) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GeneratedClassLoader(ClassFileFactory classFileFactory, ClassLoader classLoader, URL... urlArr) {
        super(urlArr, classLoader);
        if (classFileFactory == null) {
            $$$reportNull$$$0(0);
        }
        this.factory = classFileFactory;
    }

    @Override // java.net.URLClassLoader, java.lang.ClassLoader
    public InputStream getResourceAsStream(String str) {
        OutputFile outputFile = this.factory.get(str);
        if (outputFile != null) {
            return new ByteArrayInputStream(outputFile.getContent());
        }
        return super.getResourceAsStream(str);
    }

    @Override // java.net.URLClassLoader, java.lang.ClassLoader
    public Enumeration<URL> findResources(String str) throws IOException {
        Enumeration<URL> enumerationFindResources = super.findResources(str);
        URL urlCreateFakeURLForResource = createFakeURLForResource(str);
        if (urlCreateFakeURLForResource == null) {
            return enumerationFindResources;
        }
        List listSingletonList = Collections.singletonList(urlCreateFakeURLForResource);
        if (enumerationFindResources.hasMoreElements()) {
            listSingletonList = CollectionsKt.plus((Collection) listSingletonList, (Iterable) Collections.list(enumerationFindResources));
        }
        return Collections.enumeration(listSingletonList);
    }

    @Override // java.net.URLClassLoader, java.lang.ClassLoader
    public URL findResource(String str) {
        URL urlCreateFakeURLForResource = createFakeURLForResource(str);
        return urlCreateFakeURLForResource != null ? urlCreateFakeURLForResource : super.findResource(str);
    }

    private URL createFakeURLForResource(String str) {
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        try {
            OutputFile outputFile = this.factory.get(str);
            if (outputFile == null) {
                return null;
            }
            return BytesUrlUtils.createBytesUrl(outputFile.getContent());
        } catch (IOException e) {
            throw ExceptionUtilsKt.rethrow(e);
        }
    }

    @Override // java.net.URLClassLoader, java.lang.ClassLoader
    protected Class<?> findClass(String str) throws ClassNotFoundException {
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        OutputFile outputFile = this.factory.get(str.replace('.', '/') + ".class");
        if (outputFile != null) {
            byte[] content = outputFile.getContent();
            int iLastIndexOf = str.lastIndexOf(46);
            if (iLastIndexOf >= 0) {
                String strSubstring = str.substring(0, iLastIndexOf);
                if (getPackage(strSubstring) == null) {
                    definePackage(strSubstring, new Manifest(), null);
                }
            }
            Class<?> clsDefineClass = defineClass(str, content, 0, content.length);
            if (clsDefineClass == null) {
                $$$reportNull$$$0(3);
            }
            return clsDefineClass;
        }
        Class<?> clsFindClass = super.findClass(str);
        if (clsFindClass == null) {
            $$$reportNull$$$0(4);
        }
        return clsFindClass;
    }

    public void dispose() {
        this.factory = null;
    }

    public List<OutputFile> getAllGeneratedFiles() {
        List<OutputFile> listAsList = this.factory.asList();
        if (listAsList == null) {
            $$$reportNull$$$0(5);
        }
        return listAsList;
    }
}
