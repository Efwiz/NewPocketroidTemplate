package org.jetbrains.kotlin.javac;

import com.reandroid.archive.PathTree;
import com.reandroid.arsc.container.SpecTypePair;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.Tree;
import com.sun.tools.javac.code.Scope;
import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.code.Symtab;
import com.sun.tools.javac.file.JavacFileManager;
import com.sun.tools.javac.jvm.ClassReader;
import com.sun.tools.javac.main.JavaCompiler;
import com.sun.tools.javac.model.JavacElements;
import com.sun.tools.javac.model.JavacTypes;
import com.sun.tools.javac.parser.Tokens;
import com.sun.tools.javac.tree.DCTree;
import com.sun.tools.javac.tree.DocCommentTable;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.ListBuffer;
import com.sun.tools.javac.util.Log;
import com.sun.tools.javac.util.Names;
import com.sun.tools.javac.util.Options;
import java.io.Closeable;
import java.io.File;
import java.io.PrintWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.StandardLocation;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileManager;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileSystem;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.search.EverythingGlobalScope;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.javac.resolve.ClassifierResolver;
import org.jetbrains.kotlin.javac.resolve.ClassifierResolverKt;
import org.jetbrains.kotlin.javac.resolve.IdentifierResolver;
import org.jetbrains.kotlin.javac.resolve.KotlinClassifiersCache;
import org.jetbrains.kotlin.javac.wrappers.symbols.MappedSymbolBasedPackage;
import org.jetbrains.kotlin.javac.wrappers.symbols.SimpleSymbolBasedPackage;
import org.jetbrains.kotlin.javac.wrappers.symbols.SymbolBasedClass;
import org.jetbrains.kotlin.javac.wrappers.symbols.SymbolBasedClassifierType;
import org.jetbrains.kotlin.javac.wrappers.symbols.SymbolBasedPackage;
import org.jetbrains.kotlin.javac.wrappers.trees.TreeBasedClass;
import org.jetbrains.kotlin.javac.wrappers.trees.TreeBasedPackage;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.JavaClassifier;
import org.jetbrains.kotlin.load.java.structure.JavaElement;
import org.jetbrains.kotlin.load.java.structure.JavaField;
import org.jetbrains.kotlin.load.java.structure.JavaPackage;
import org.jetbrains.kotlin.load.kotlin.PackagePartProvider;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNamesUtilKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtFile;

/* JADX INFO: compiled from: JavacWrapper.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000µ\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002*\u00019\u0018\u0000 \u0083\u00012\u00020\u0001:\u0002\u0083\u0001B\u008f\u0001\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003\u0012\u000e\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\t\u0018\u00010\b\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00040\u000b\u0012\u000e\u0010\f\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u000b\u0012\u000e\u0010\r\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u000b\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u000b\u0012\u0006\u0010\u0012\u001a\u00020\u0013\u0012\b\u0010\u0014\u001a\u0004\u0018\u00010\u0004\u0012\u0006\u0010\u0015\u001a\u00020\u0016¢\u0006\u0002\u0010\u0017J\b\u0010W\u001a\u00020XH\u0016J\u0012\u0010Y\u001a\u00020\u00132\n\b\u0002\u0010Z\u001a\u0004\u0018\u00010\u0004J \u0010[\u001a\u0012\u0012\f\u0012\n \u001b*\u0004\u0018\u00010\u001a0\u001a\u0018\u00010\u00192\u0006\u0010\\\u001a\u00020KH\u0002J\u001a\u0010]\u001a\u0004\u0018\u00010^2\u0006\u0010\\\u001a\u00020K2\b\b\u0002\u0010_\u001a\u00020`J\u0012\u0010a\u001a\u0004\u0018\u00010!2\u0006\u0010\\\u001a\u00020KH\u0002J\u0014\u0010b\u001a\b\u0012\u0004\u0012\u00020^0\u000b2\u0006\u0010c\u001a\u00020GJ\u001a\u0010d\u001a\u0004\u0018\u00010e2\u0006\u0010c\u001a\u00020G2\b\b\u0002\u0010_\u001a\u00020`J\u0012\u0010f\u001a\u0004\u0018\u00010N2\u0006\u0010c\u001a\u00020\tH\u0002J\u0014\u0010g\u001a\b\u0012\u0004\u0012\u00020e0\u000b2\u0006\u0010c\u001a\u00020GJ\u0010\u0010h\u001a\u0004\u0018\u00010^2\u0006\u0010\\\u001a\u00020KJ\u0014\u0010i\u001a\b\u0012\u0004\u0012\u00020H0\u000b2\u0006\u0010c\u001a\u00020GJ\u0010\u0010j\u001a\u0004\u0018\u00010G2\u0006\u0010c\u001a\u00020GJ\u000e\u0010k\u001a\u00020\u00132\u0006\u0010l\u001a\u00020mJ\u000e\u0010k\u001a\u00020\u00132\u0006\u0010n\u001a\u00020\u001aJ\u0016\u0010o\u001a\u00020\u00132\u0006\u0010p\u001a\u00020q2\u0006\u0010r\u001a\u00020sJ\u0014\u0010t\u001a\b\u0012\u0004\u0012\u00020\t0u2\u0006\u0010c\u001a\u00020GJ\b\u0010v\u001a\u00020XH\u0002J \u0010w\u001a\u0004\u0018\u00010x2\u0006\u0010p\u001a\u00020q2\u0006\u0010r\u001a\u00020s2\u0006\u0010y\u001a\u00020zJ \u0010{\u001a\u0004\u0018\u00010|2\u0006\u0010p\u001a\u00020q2\u0006\u0010r\u001a\u00020s2\u0006\u0010}\u001a\u00020^J\u0012\u0010~\u001a\u00020\u00002\b\u0010Z\u001a\u0004\u0018\u00010\u0004H\u0002J\u0012\u0010\u007f\u001a\u0005\u0018\u00010\u0080\u00012\u0007\u0010\u0081\u0001\u001a\u000203J\u0017\u0010]\u001a\u0004\u0018\u00010!*\u00030\u0082\u00012\u0006\u0010\\\u001a\u00020KH\u0002R+\u0010\u0018\u001a\u0012\u0012\f\u0012\n \u001b*\u0004\u0018\u00010\u001a0\u001a\u0018\u00010\u00198FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u001e\u0010\u001f\u001a\u0004\b\u001c\u0010\u001dR\u001d\u0010 \u001a\u0004\u0018\u00010!8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b$\u0010\u001f\u001a\u0004\b\"\u0010#R+\u0010%\u001a\u0012\u0012\f\u0012\n \u001b*\u0004\u0018\u00010\u001a0\u001a\u0018\u00010\u00198FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b'\u0010\u001f\u001a\u0004\b&\u0010\u001dR\u000e\u0010(\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020*X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010+\u001a\b\u0012\u0004\u0012\u00020-0,X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010.\u001a\n \u001b*\u0004\u0018\u00010/0/X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00100\u001a\u000201X\u0082\u0004¢\u0006\u0002\n\u0000R2\u00102\u001a&\u0012\f\u0012\n \u001b*\u0004\u0018\u00010303 \u001b*\u0012\u0012\f\u0012\n \u001b*\u0004\u0018\u00010303\u0018\u00010,0,X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00104\u001a\u000205X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00106\u001a\u000207X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u00108\u001a\u000209X\u0082\u0004¢\u0006\u0004\n\u0002\u0010:R\u001b\u0010;\u001a\u00020<8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b?\u0010\u001f\u001a\u0004\b=\u0010>R\u0011\u0010\u000e\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b@\u0010AR\u000e\u0010B\u001a\u000207X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010C\u001a\n \u001b*\u0004\u0018\u00010D0DX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R>\u0010E\u001a2\u0012\u0004\u0012\u00020G\u0012(\u0012&\u0012\f\u0012\n \u001b*\u0004\u0018\u00010H0H \u001b*\u0012\u0012\f\u0012\n \u001b*\u0004\u0018\u00010H0H\u0018\u00010,0,0FX\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010I\u001a\u001e\u0012\u0004\u0012\u00020K\u0012\u0004\u0012\u00020!0Jj\u000e\u0012\u0004\u0012\u00020K\u0012\u0004\u0012\u00020!`LX\u0082\u0004¢\u0006\u0002\n\u0000R.\u0010M\u001a\"\u0012\u0004\u0012\u00020\t\u0012\u0006\u0012\u0004\u0018\u00010N0Jj\u0010\u0012\u0004\u0012\u00020\t\u0012\u0006\u0012\u0004\u0018\u00010N`LX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010O\u001a\n \u001b*\u0004\u0018\u00010P0PX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010Q\u001a\u000e\u0012\u0004\u0012\u00020K\u0012\u0004\u0012\u00020R0FX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010S\u001a\u000e\u0012\u0004\u0012\u00020G\u0012\u0004\u0012\u00020T0FX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010U\u001a\n \u001b*\u0004\u0018\u00010V0VX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0084\u0001"}, d2 = {"Lorg/jetbrains/kotlin/javac/JavacWrapper;", "Ljava/io/Closeable;", "javaFiles", "", "Ljava/io/File;", "kotlinFiles", "Lorg/jetbrains/kotlin/psi/KtFile;", "arguments", "", "", "jvmClasspathRoots", "", "bootClasspath", "sourcePath", "kotlinResolver", "Lorg/jetbrains/kotlin/javac/JavacWrapperKotlinResolver;", "packagePartsProviders", "Lorg/jetbrains/kotlin/load/kotlin/PackagePartProvider;", "compileJava", "", "outputDirectory", "context", "Lcom/sun/tools/javac/util/Context;", "(Ljava/util/Collection;Ljava/util/Collection;[Ljava/lang/String;Ljava/util/List;Ljava/util/List;Ljava/util/List;Lorg/jetbrains/kotlin/javac/JavacWrapperKotlinResolver;Ljava/util/List;ZLjava/io/File;Lcom/sun/tools/javac/util/Context;)V", "JAVA_LANG_ANNOTATION_ANNOTATION", "Lorg/jetbrains/kotlin/javac/wrappers/symbols/SymbolBasedClassifierType;", "Ljavax/lang/model/type/TypeMirror;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "getJAVA_LANG_ANNOTATION_ANNOTATION", "()Lorg/jetbrains/kotlin/javac/wrappers/symbols/SymbolBasedClassifierType;", "JAVA_LANG_ANNOTATION_ANNOTATION$delegate", "Lkotlin/Lazy;", "JAVA_LANG_ENUM", "Lorg/jetbrains/kotlin/javac/wrappers/symbols/SymbolBasedClass;", "getJAVA_LANG_ENUM", "()Lorg/jetbrains/kotlin/javac/wrappers/symbols/SymbolBasedClass;", "JAVA_LANG_ENUM$delegate", "JAVA_LANG_OBJECT", "getJAVA_LANG_OBJECT", "JAVA_LANG_OBJECT$delegate", "aptOn", "classifierResolver", "Lorg/jetbrains/kotlin/javac/resolve/ClassifierResolver;", "compilationUnits", "Lcom/sun/tools/javac/util/List;", "Lcom/sun/tools/javac/tree/JCTree$JCCompilationUnit;", PathTree.NAME_elements, "Lcom/sun/tools/javac/model/JavacElements;", "fileManager", "Lcom/sun/tools/javac/file/JavacFileManager;", "fileObjects", "Ljavax/tools/JavaFileObject;", "identifierResolver", "Lorg/jetbrains/kotlin/javac/resolve/IdentifierResolver;", "jarFileSystem", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFileSystem;", "javac", "org/jetbrains/kotlin/javac/JavacWrapper$javac$1", "Lorg/jetbrains/kotlin/javac/JavacWrapper$javac$1;", "kotlinClassifiersCache", "Lorg/jetbrains/kotlin/javac/resolve/KotlinClassifiersCache;", "getKotlinClassifiersCache", "()Lorg/jetbrains/kotlin/javac/resolve/KotlinClassifiersCache;", "kotlinClassifiersCache$delegate", "getKotlinResolver", "()Lorg/jetbrains/kotlin/javac/JavacWrapperKotlinResolver;", "localFileSystem", "names", "Lcom/sun/tools/javac/util/Names;", "packageSourceAnnotations", "", "Lorg/jetbrains/kotlin/name/FqName;", "Lcom/sun/tools/javac/tree/JCTree$JCAnnotation;", "symbolBasedClassesCache", "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/name/ClassId;", "Lkotlin/collections/HashMap;", "symbolBasedPackagesCache", "Lorg/jetbrains/kotlin/javac/wrappers/symbols/SymbolBasedPackage;", "symbolTable", "Lcom/sun/tools/javac/code/Symtab;", "treeBasedJavaClasses", "Lorg/jetbrains/kotlin/javac/wrappers/trees/TreeBasedClass;", "treeBasedJavaPackages", "Lorg/jetbrains/kotlin/javac/wrappers/trees/TreeBasedPackage;", SpecTypePair.NAME_types, "Lcom/sun/tools/javac/model/JavacTypes;", "close", "", "compile", "outDir", "createCommonClassifierType", "classId", "findClass", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "scope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope;", "findClassInSymbols", "findClassesFromPackage", "fqName", "findPackage", "Lorg/jetbrains/kotlin/load/java/structure/JavaPackage;", "findPackageInSymbols", "findSubPackages", "getKotlinClassifier", "getPackageAnnotationsFromSources", "hasKotlinPackage", "isDeprecated", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Ljavax/lang/model/element/Element;", "typeMirror", "isDeprecatedInJavaDoc", Constants.TYPE_TREE, "Lcom/sun/tools/javac/tree/JCTree;", "compilationUnit", "Lcom/sun/source/tree/CompilationUnitTree;", "knownClassNamesInPackage", "", "makeOutputDirectoryClassesVisible", "resolve", "Lorg/jetbrains/kotlin/load/java/structure/JavaClassifier;", "containingElement", "Lorg/jetbrains/kotlin/load/java/structure/JavaElement;", "resolveField", "Lorg/jetbrains/kotlin/load/java/structure/JavaField;", "containingClass", "setClassPathForCompilation", "toVirtualFile", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "javaFileObject", "Lcom/sun/tools/javac/code/Symbol$PackageSymbol;", "Companion", "javac-wrapper"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavacWrapper implements Closeable {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    /* JADX INFO: renamed from: JAVA_LANG_ANNOTATION_ANNOTATION$delegate, reason: from kotlin metadata */
    private final Lazy JAVA_LANG_ANNOTATION_ANNOTATION;

    /* JADX INFO: renamed from: JAVA_LANG_ENUM$delegate, reason: from kotlin metadata */
    private final Lazy JAVA_LANG_ENUM;

    /* JADX INFO: renamed from: JAVA_LANG_OBJECT$delegate, reason: from kotlin metadata */
    private final Lazy JAVA_LANG_OBJECT;
    private final boolean aptOn;
    private final ClassifierResolver classifierResolver;
    private final List<JCTree.JCCompilationUnit> compilationUnits;
    private final boolean compileJava;
    private final Context context;
    private final JavacElements elements;
    private final JavacFileManager fileManager;
    private final List<JavaFileObject> fileObjects;
    private final IdentifierResolver identifierResolver;
    private final VirtualFileSystem jarFileSystem;
    private final JavacWrapper$javac$1 javac;

    /* JADX INFO: renamed from: kotlinClassifiersCache$delegate, reason: from kotlin metadata */
    private final Lazy kotlinClassifiersCache;
    private final JavacWrapperKotlinResolver kotlinResolver;
    private final VirtualFileSystem localFileSystem;
    private final Names names;
    private final File outputDirectory;
    private final java.util.List<PackagePartProvider> packagePartsProviders;
    private final Map<FqName, List<JCTree.JCAnnotation>> packageSourceAnnotations;
    private final HashMap<ClassId, SymbolBasedClass> symbolBasedClassesCache;
    private final HashMap<String, SymbolBasedPackage> symbolBasedPackagesCache;
    private final Symtab symbolTable;
    private final Map<ClassId, TreeBasedClass> treeBasedJavaClasses;
    private final Map<FqName, TreeBasedPackage> treeBasedJavaPackages;
    private final JavacTypes types;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v17, types: [org.jetbrains.kotlin.javac.JavacWrapper$javac$1] */
    public JavacWrapper(final Collection<? extends File> javaFiles, final Collection<? extends KtFile> kotlinFiles, String[] strArr, java.util.List<? extends File> jvmClasspathRoots, java.util.List<? extends File> list, java.util.List<? extends File> list2, JavacWrapperKotlinResolver kotlinResolver, java.util.List<? extends PackagePartProvider> packagePartsProviders, boolean z, File file, final Context context) {
        Unit unit;
        java.util.List<String> list3;
        Intrinsics.checkNotNullParameter(javaFiles, "javaFiles");
        Intrinsics.checkNotNullParameter(kotlinFiles, "kotlinFiles");
        Intrinsics.checkNotNullParameter(jvmClasspathRoots, "jvmClasspathRoots");
        Intrinsics.checkNotNullParameter(kotlinResolver, "kotlinResolver");
        Intrinsics.checkNotNullParameter(packagePartsProviders, "packagePartsProviders");
        Intrinsics.checkNotNullParameter(context, "context");
        this.kotlinResolver = kotlinResolver;
        this.packagePartsProviders = packagePartsProviders;
        this.compileJava = z;
        this.outputDirectory = file;
        this.context = context;
        VirtualFileSystem fileSystem = VirtualFileManager.getInstance().getFileSystem("file");
        Intrinsics.checkNotNull(fileSystem);
        this.localFileSystem = fileSystem;
        VirtualFileSystem fileSystem2 = VirtualFileManager.getInstance().getFileSystem("jar");
        Intrinsics.checkNotNull(fileSystem2);
        this.jarFileSystem = fileSystem2;
        this.JAVA_LANG_OBJECT = LazyKt.lazy(new Function0<SymbolBasedClassifierType<? extends TypeMirror>>() { // from class: org.jetbrains.kotlin.javac.JavacWrapper$JAVA_LANG_OBJECT$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final SymbolBasedClassifierType<? extends TypeMirror> invoke() {
                return this.this$0.createCommonClassifierType(ClassifierResolverKt.classId(CommonClassNames.DEFAULT_PACKAGE, CommonClassNames.JAVA_LANG_OBJECT_SHORT));
            }
        });
        this.JAVA_LANG_ENUM = LazyKt.lazy(new Function0<SymbolBasedClass>() { // from class: org.jetbrains.kotlin.javac.JavacWrapper$JAVA_LANG_ENUM$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final SymbolBasedClass invoke() {
                return this.this$0.findClassInSymbols(ClassifierResolverKt.classId(CommonClassNames.DEFAULT_PACKAGE, "Enum"));
            }
        });
        this.JAVA_LANG_ANNOTATION_ANNOTATION = LazyKt.lazy(new Function0<SymbolBasedClassifierType<? extends TypeMirror>>() { // from class: org.jetbrains.kotlin.javac.JavacWrapper$JAVA_LANG_ANNOTATION_ANNOTATION$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final SymbolBasedClassifierType<? extends TypeMirror> invoke() {
                return this.this$0.createCommonClassifierType(ClassifierResolverKt.classId("java.lang.annotation", "Annotation"));
            }
        });
        Options optionsInstance = Options.instance(context);
        JavacOptionsMapper javacOptionsMapper = JavacOptionsMapper.INSTANCE;
        Intrinsics.checkNotNull(optionsInstance);
        javacOptionsMapper.setUTF8Encoding(optionsInstance);
        if (strArr != null && (list3 = ArraysKt.toList(strArr)) != null) {
            JavacOptionsMapper.INSTANCE.map(optionsInstance, list3);
        }
        ?? r5 = new JavaCompiler(context) { // from class: org.jetbrains.kotlin.javac.JavacWrapper$javac$1
            public List<JCTree.JCCompilationUnit> parseFiles(Iterable<? extends JavaFileObject> files) {
                return this.this$0.compilationUnits;
            }
        };
        this.javac = r5;
        this.aptOn = strArr == null || !ArraysKt.contains(strArr, "-proc:none");
        Object obj = context.get(JavaFileManager.class);
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type com.sun.tools.javac.file.JavacFileManager");
        JavacFileManager javacFileManager = (JavacFileManager) obj;
        this.fileManager = javacFileManager;
        ((JavacWrapper$javac$1) r5).keepComments = true;
        javacFileManager.setSymbolFileEnabled(false);
        if (list != null) {
            Iterable location = javacFileManager.getLocation(StandardLocation.PLATFORM_CLASS_PATH);
            Intrinsics.checkNotNullExpressionValue(location, "getLocation(...)");
            java.util.List listPlus = CollectionsKt.plus(location, (Iterable) jvmClasspathRoots);
            javacFileManager.setLocation(StandardLocation.PLATFORM_CLASS_PATH, list);
            javacFileManager.setLocation(StandardLocation.CLASS_PATH, listPlus);
            unit = Unit.INSTANCE;
        } else {
            unit = null;
        }
        if (unit == null) {
            javacFileManager.setLocation(StandardLocation.CLASS_PATH, jvmClasspathRoots);
        }
        if (list2 != null) {
            javacFileManager.setLocation(StandardLocation.SOURCE_PATH, list2);
        }
        this.names = Names.instance(context);
        this.symbolTable = Symtab.instance(context);
        this.elements = JavacElements.instance(context);
        this.types = JavacTypes.instance(context);
        ListBuffer listBuffer = (Collection) new ListBuffer();
        Iterator<T> it = javaFiles.iterator();
        while (it.hasNext()) {
            listBuffer.add(this.fileManager.getRegularFile((File) it.next()));
        }
        Iterable fileObjects = listBuffer.toList();
        this.fileObjects = fileObjects;
        Intrinsics.checkNotNullExpressionValue(fileObjects, "fileObjects");
        ListBuffer listBuffer2 = (Collection) new ListBuffer();
        JavacWrapper$javac$1 javacWrapper$javac$1 = this.javac;
        Iterator it2 = fileObjects.iterator();
        while (it2.hasNext()) {
            listBuffer2.add(javacWrapper$javac$1.parse((JavaFileObject) it2.next()));
        }
        List<JCTree.JCCompilationUnit> list4 = listBuffer2.toList();
        Intrinsics.checkNotNullExpressionValue(list4, "toList(...)");
        this.compilationUnits = list4;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        Iterator it3 = list4.iterator();
        while (it3.hasNext()) {
            CompilationUnitTree compilationUnitTree = (JCTree.JCCompilationUnit) it3.next();
            JCTree.JCExpression packageName = compilationUnitTree.getPackageName();
            String string = packageName != null ? packageName.toString() : null;
            String str = string == null ? "<root>" : string;
            Intrinsics.checkNotNull(compilationUnitTree);
            TreeBasedPackage treeBasedPackage = new TreeBasedPackage(str, this, compilationUnitTree);
            linkedHashMap2.put(treeBasedPackage.getOriginalFqName(), treeBasedPackage);
            for (JCTree.JCClassDecl jCClassDecl : compilationUnitTree.getTypeDecls()) {
                Intrinsics.checkNotNull(jCClassDecl, "null cannot be cast to non-null type com.sun.tools.javac.tree.JCTree.JCClassDecl");
                JCTree.JCClassDecl jCClassDecl2 = jCClassDecl;
                String string2 = jCClassDecl2.getSimpleName().toString();
                Intrinsics.checkNotNullExpressionValue(string2, "toString(...)");
                ClassId classId = ClassifierResolverKt.classId(string == null ? "" : string, string2);
                linkedHashMap.put(classId, new TreeBasedClass(jCClassDecl2, compilationUnitTree, this, classId, null));
            }
        }
        this.treeBasedJavaClasses = linkedHashMap;
        this.treeBasedJavaPackages = linkedHashMap2;
        Iterable iterable = this.compilationUnits;
        ArrayList arrayList = new ArrayList();
        for (Object obj2 : iterable) {
            JCTree.JCCompilationUnit jCCompilationUnit = (JCTree.JCCompilationUnit) obj2;
            if (jCCompilationUnit.getSourceFile().isNameCompatible("package-info", JavaFileObject.Kind.SOURCE) && jCCompilationUnit.getPackageName() != null) {
                arrayList.add(obj2);
            }
        }
        ArrayList<JCTree.JCCompilationUnit> arrayList2 = arrayList;
        LinkedHashMap linkedHashMap3 = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(arrayList2, 10)), 16));
        for (JCTree.JCCompilationUnit jCCompilationUnit2 : arrayList2) {
            JCTree.JCExpression packageName2 = jCCompilationUnit2.getPackageName();
            Intrinsics.checkNotNull(packageName2);
            linkedHashMap3.put(new FqName(packageName2.toString()), jCCompilationUnit2.packageAnnotations);
        }
        this.packageSourceAnnotations = linkedHashMap3;
        this.classifierResolver = new ClassifierResolver(this);
        this.identifierResolver = new IdentifierResolver(this);
        this.kotlinClassifiersCache = LazyKt.lazy(new Function0<KotlinClassifiersCache>() { // from class: org.jetbrains.kotlin.javac.JavacWrapper$kotlinClassifiersCache$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final KotlinClassifiersCache invoke() {
                return new KotlinClassifiersCache(!javaFiles.isEmpty() ? kotlinFiles : CollectionsKt.emptyList(), this);
            }
        });
        this.symbolBasedPackagesCache = new HashMap<>();
        this.symbolBasedClassesCache = new HashMap<>();
    }

    public final JavacWrapperKotlinResolver getKotlinResolver() {
        return this.kotlinResolver;
    }

    /* JADX INFO: compiled from: JavacWrapper.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/javac/JavacWrapper$Companion;", "", "()V", "getInstance", "Lorg/jetbrains/kotlin/javac/JavacWrapper;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "javac-wrapper"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final JavacWrapper getInstance(Project project) {
            Intrinsics.checkNotNullParameter(project, "project");
            Object service = project.getService(JavacWrapper.class);
            Intrinsics.checkNotNullExpressionValue(service, "getService(...)");
            return (JavacWrapper) service;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final SymbolBasedClassifierType<TypeMirror> createCommonClassifierType(ClassId classId) {
        SymbolBasedClass symbolBasedClassFindClassInSymbols = findClassInSymbols(classId);
        if (symbolBasedClassFindClassInSymbols != null) {
            return new SymbolBasedClassifierType<>(((TypeElement) symbolBasedClassFindClassInSymbols.getElement()).asType(), this);
        }
        return null;
    }

    public final SymbolBasedClassifierType<TypeMirror> getJAVA_LANG_OBJECT() {
        return (SymbolBasedClassifierType) this.JAVA_LANG_OBJECT.getValue();
    }

    public final SymbolBasedClass getJAVA_LANG_ENUM() {
        return (SymbolBasedClass) this.JAVA_LANG_ENUM.getValue();
    }

    public final SymbolBasedClassifierType<TypeMirror> getJAVA_LANG_ANNOTATION_ANNOTATION() {
        return (SymbolBasedClassifierType) this.JAVA_LANG_ANNOTATION_ANNOTATION.getValue();
    }

    private final KotlinClassifiersCache getKotlinClassifiersCache() {
        return (KotlinClassifiersCache) this.kotlinClassifiersCache.getValue();
    }

    public static /* synthetic */ boolean compile$default(JavacWrapper javacWrapper, File file, int i, Object obj) {
        if ((i & 1) != 0) {
            file = null;
        }
        return javacWrapper.compile(file);
    }

    /* JADX WARN: Code duplicated, block: B:25:0x0056  */
    public final boolean compile(File outDir) {
        String path;
        JavacWrapper$javac$1 javacWrapper$javac$1 = this.javac;
        if (!this.compileJava) {
            return true;
        }
        if (javacWrapper$javac$1.errorCount() > 0) {
            return false;
        }
        int length = this.fileObjects.length();
        if (length == 0) {
            return true;
        }
        setClassPathForCompilation(outDir);
        if (!this.aptOn) {
            makeOutputDirectoryClassesVisible();
        }
        Iterable location = this.fileManager.getLocation(StandardLocation.CLASS_OUTPUT);
        if (location != null) {
            Intrinsics.checkNotNull(location);
            File file = (File) CollectionsKt.firstOrNull(location);
            if (file == null || (path = file.getPath()) == null) {
                path = "test directory";
            } else {
                Intrinsics.checkNotNull(path);
                if (StringsKt.contains$default((CharSequence) path, (CharSequence) "compilerProject_test", false, 2, (Object) null)) {
                    path = null;
                }
                if (path == null) {
                    path = "test directory";
                }
            }
        } else {
            path = "test directory";
        }
        PrintWriter printWriter = (PrintWriter) this.context.get(Log.outKey);
        if (printWriter != null) {
            printWriter.print("Compiling " + length + " Java source files to [" + path + ']');
        }
        javacWrapper$javac$1.compile(this.fileObjects);
        return javacWrapper$javac$1.errorCount() == 0;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        this.fileManager.close();
        close();
    }

    public static /* synthetic */ JavaClass findClass$default(JavacWrapper javacWrapper, ClassId classId, GlobalSearchScope globalSearchScope, int i, Object obj) {
        if ((i & 2) != 0) {
            globalSearchScope = new EverythingGlobalScope();
        }
        return javacWrapper.findClass(classId, globalSearchScope);
    }

    public final JavaClass findClass(ClassId classId, GlobalSearchScope scope) {
        VirtualFile virtualFile;
        VirtualFile virtualFile2;
        VirtualFile virtualFile3;
        Intrinsics.checkNotNullParameter(classId, "classId");
        Intrinsics.checkNotNullParameter(scope, "scope");
        if (classId.isNestedClass()) {
            java.util.List<Name> listPathSegments = classId.getRelativeClassName().pathSegments();
            Intrinsics.checkNotNullExpressionValue(listPathSegments, "pathSegments(...)");
            java.util.List<Name> list = listPathSegments;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(((Name) it.next()).asString());
            }
            ArrayList arrayList2 = arrayList;
            JavaClass javaClassFindClass = findClass(new ClassId(classId.getPackageFqName(), Name.identifier((String) CollectionsKt.first((java.util.List) arrayList2))), scope);
            if (javaClassFindClass == null) {
                return null;
            }
            Iterator it2 = CollectionsKt.drop(arrayList2, 1).iterator();
            while (it2.hasNext()) {
                Name nameIdentifier = Name.identifier((String) it2.next());
                Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
                javaClassFindClass = javaClassFindClass.findInnerClass(nameIdentifier);
                if (javaClassFindClass == null) {
                    return null;
                }
            }
            return javaClassFindClass;
        }
        TreeBasedClass treeBasedClass = this.treeBasedJavaClasses.get(classId);
        if (treeBasedClass != null && (virtualFile3 = treeBasedClass.getVirtualFile()) != null && scope.contains(virtualFile3)) {
            return treeBasedClass;
        }
        SymbolBasedClass symbolBasedClass = this.symbolBasedClassesCache.get(classId);
        if (symbolBasedClass != null && (virtualFile2 = symbolBasedClass.getVirtualFile()) != null && scope.contains(virtualFile2)) {
            return symbolBasedClass;
        }
        String strAsString = classId.getPackageFqName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        SymbolBasedPackage symbolBasedPackageFindPackageInSymbols = findPackageInSymbols(strAsString);
        if (symbolBasedPackageFindPackageInSymbols != null) {
            PackageElement element = symbolBasedPackageFindPackageInSymbols.getElement();
            Intrinsics.checkNotNull(element, "null cannot be cast to non-null type com.sun.tools.javac.code.Symbol.PackageSymbol");
            SymbolBasedClass symbolBasedClassFindClass = findClass((Symbol.PackageSymbol) element, classId);
            if (symbolBasedClassFindClass != null && (virtualFile = symbolBasedClassFindClass.getVirtualFile()) != null && scope.contains(virtualFile)) {
                return symbolBasedClassFindClass;
            }
        }
        return null;
    }

    public static /* synthetic */ JavaPackage findPackage$default(JavacWrapper javacWrapper, FqName fqName, GlobalSearchScope globalSearchScope, int i, Object obj) {
        if ((i & 2) != 0) {
            globalSearchScope = new EverythingGlobalScope();
        }
        return javacWrapper.findPackage(fqName, globalSearchScope);
    }

    public final JavaPackage findPackage(FqName fqName, GlobalSearchScope scope) {
        VirtualFile virtualFile;
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(scope, "scope");
        TreeBasedPackage treeBasedPackage = this.treeBasedJavaPackages.get(fqName);
        if (treeBasedPackage != null && (virtualFile = treeBasedPackage.getVirtualFile()) != null && scope.contains(virtualFile)) {
            return treeBasedPackage;
        }
        String strAsString = fqName.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return findPackageInSymbols(strAsString);
    }

    public final java.util.List<JavaPackage> findSubPackages(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Map packages = this.symbolTable.packages;
        Intrinsics.checkNotNullExpressionValue(packages, "packages");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry entry : packages.entrySet()) {
            String string = ((com.sun.tools.javac.util.Name) entry.getKey()).toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            if (StringsKt.startsWith$default(string, new StringBuilder().append(fqName).append('.').toString(), false, 2, (Object) null)) {
                linkedHashMap.put(entry.getKey(), entry.getValue());
            }
        }
        LinkedHashMap linkedHashMap2 = linkedHashMap;
        ArrayList arrayList = new ArrayList(linkedHashMap2.size());
        Iterator it = linkedHashMap2.entrySet().iterator();
        while (it.hasNext()) {
            Object value = ((Map.Entry) it.next()).getValue();
            Intrinsics.checkNotNullExpressionValue(value, "<get-value>(...)");
            arrayList.add(new SimpleSymbolBasedPackage((PackageElement) value, this));
        }
        ArrayList arrayList2 = arrayList;
        Map<FqName, TreeBasedPackage> map = this.treeBasedJavaPackages;
        LinkedHashMap linkedHashMap3 = new LinkedHashMap();
        for (Map.Entry<FqName, TreeBasedPackage> entry2 : map.entrySet()) {
            FqName key = entry2.getKey();
            if (FqNamesUtilKt.isSubpackageOf(key, fqName) && !Intrinsics.areEqual(key, fqName)) {
                linkedHashMap3.put(entry2.getKey(), entry2.getValue());
            }
        }
        LinkedHashMap linkedHashMap4 = linkedHashMap3;
        ArrayList arrayList3 = new ArrayList(linkedHashMap4.size());
        Iterator it2 = linkedHashMap4.entrySet().iterator();
        while (it2.hasNext()) {
            arrayList3.add((TreeBasedPackage) ((Map.Entry) it2.next()).getValue());
        }
        return CollectionsKt.plus((Collection) arrayList2, (Iterable) arrayList3);
    }

    public final java.util.List<JCTree.JCAnnotation> getPackageAnnotationsFromSources(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        java.util.List<JCTree.JCAnnotation> list = (List) this.packageSourceAnnotations.get(fqName);
        return list != null ? list : CollectionsKt.emptyList();
    }

    public final java.util.List<JavaClass> findClassesFromPackage(FqName fqName) {
        Scope scopeMembers;
        Iterable elements;
        java.util.List listFilterIsInstance;
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Map<ClassId, TreeBasedClass> map = this.treeBasedJavaClasses;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry<ClassId, TreeBasedClass> entry : map.entrySet()) {
            if (Intrinsics.areEqual(entry.getKey().getPackageFqName(), fqName)) {
                linkedHashMap.put(entry.getKey(), entry.getValue());
            }
        }
        LinkedHashMap linkedHashMap2 = linkedHashMap;
        ArrayList arrayList = new ArrayList(linkedHashMap2.size());
        Iterator it = linkedHashMap2.entrySet().iterator();
        while (it.hasNext()) {
            TreeBasedClass treeBasedClass = this.treeBasedJavaClasses.get(((Map.Entry) it.next()).getKey());
            Intrinsics.checkNotNull(treeBasedClass);
            arrayList.add(treeBasedClass);
        }
        ArrayList arrayList2 = arrayList;
        Symbol.PackageSymbol packageElement = this.elements.getPackageElement(fqName.asString());
        ArrayList arrayListEmptyList = null;
        if (packageElement != null && (scopeMembers = packageElement.members()) != null && (elements = scopeMembers.getElements()) != null && (listFilterIsInstance = CollectionsKt.filterIsInstance(elements, Symbol.ClassSymbol.class)) != null) {
            java.util.List<TypeElement> list = listFilterIsInstance;
            ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            for (TypeElement typeElement : list) {
                arrayList3.add(new SymbolBasedClass(typeElement, this, null, ((Symbol.ClassSymbol) typeElement).classfile));
            }
            arrayListEmptyList = arrayList3;
        }
        if (arrayListEmptyList == null) {
            arrayListEmptyList = CollectionsKt.emptyList();
        }
        return CollectionsKt.plus((Collection) arrayList2, (Iterable) arrayListEmptyList);
    }

    public final Set<String> knownClassNamesInPackage(FqName fqName) {
        ArrayList arrayListEmptyList;
        Scope scope;
        Iterable elements;
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Map<ClassId, TreeBasedClass> map = this.treeBasedJavaClasses;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry<ClassId, TreeBasedClass> entry : map.entrySet()) {
            if (Intrinsics.areEqual(entry.getKey().getPackageFqName(), fqName)) {
                linkedHashMap.put(entry.getKey(), entry.getValue());
            }
        }
        HashSet hashSet = new HashSet();
        Iterator it = linkedHashMap.entrySet().iterator();
        while (it.hasNext()) {
            hashSet.add(((TreeBasedClass) ((Map.Entry) it.next()).getValue()).getName().asString());
        }
        HashSet hashSet2 = hashSet;
        Symbol.PackageSymbol packageElement = this.elements.getPackageElement(fqName.asString());
        if (packageElement == null || (scope = packageElement.members_field) == null || (elements = scope.getElements()) == null) {
            arrayListEmptyList = null;
        } else {
            ArrayList arrayList = new ArrayList();
            for (Object obj : elements) {
                if (obj instanceof Symbol.ClassSymbol) {
                    arrayList.add(obj);
                }
            }
            ArrayList arrayList2 = arrayList;
            ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
            Iterator it2 = arrayList2.iterator();
            while (it2.hasNext()) {
                arrayList3.add(((Symbol.ClassSymbol) it2.next()).name.toString());
            }
            arrayListEmptyList = arrayList3;
        }
        if (arrayListEmptyList == null) {
            arrayListEmptyList = CollectionsKt.emptyList();
        }
        return SetsKt.plus((Set) hashSet2, (Iterable) arrayListEmptyList);
    }

    public final JavaClass getKotlinClassifier(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        return getKotlinClassifiersCache().getKotlinClassifier(classId);
    }

    public final boolean isDeprecated(Element element) {
        Intrinsics.checkNotNullParameter(element, "element");
        return this.elements.isDeprecated(element);
    }

    public final boolean isDeprecated(TypeMirror typeMirror) {
        Intrinsics.checkNotNullParameter(typeMirror, "typeMirror");
        Element elementAsElement = this.types.asElement(typeMirror);
        if (elementAsElement == null) {
            return false;
        }
        return isDeprecated(elementAsElement);
    }

    public final JavaClassifier resolve(JCTree tree, CompilationUnitTree compilationUnit, JavaElement containingElement) {
        Intrinsics.checkNotNullParameter(tree, "tree");
        Intrinsics.checkNotNullParameter(compilationUnit, "compilationUnit");
        Intrinsics.checkNotNullParameter(containingElement, "containingElement");
        return this.classifierResolver.resolve((Tree) tree, compilationUnit, containingElement);
    }

    public final JavaField resolveField(JCTree tree, CompilationUnitTree compilationUnit, JavaClass containingClass) {
        Intrinsics.checkNotNullParameter(tree, "tree");
        Intrinsics.checkNotNullParameter(compilationUnit, "compilationUnit");
        Intrinsics.checkNotNullParameter(containingClass, "containingClass");
        return this.identifierResolver.resolve((Tree) tree, compilationUnit, containingClass);
    }

    public final VirtualFile toVirtualFile(JavaFileObject javaFileObject) {
        Intrinsics.checkNotNullParameter(javaFileObject, "javaFileObject");
        URI uri = javaFileObject.toUri();
        if (Intrinsics.areEqual(uri.getScheme(), "jar")) {
            VirtualFileSystem virtualFileSystem = this.jarFileSystem;
            String schemeSpecificPart = uri.getSchemeSpecificPart();
            Intrinsics.checkNotNullExpressionValue(schemeSpecificPart, "getSchemeSpecificPart(...)");
            String strSubstring = schemeSpecificPart.substring(5);
            Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
            return virtualFileSystem.findFileByPath(strSubstring);
        }
        return this.localFileSystem.findFileByPath(uri.getSchemeSpecificPart());
    }

    public final FqName hasKotlinPackage(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        if (getKotlinClassifiersCache().hasPackage(fqName)) {
            return fqName;
        }
        return null;
    }

    public final boolean isDeprecatedInJavaDoc(JCTree tree, CompilationUnitTree compilationUnit) {
        DCTree.DCDocComment commentTree;
        Tokens.Comment comment;
        Intrinsics.checkNotNullParameter(tree, "tree");
        Intrinsics.checkNotNullParameter(compilationUnit, "compilationUnit");
        DocCommentTable docCommentTable = ((JCTree.JCCompilationUnit) compilationUnit).docComments;
        return (docCommentTable == null || (commentTree = docCommentTable.getCommentTree(tree)) == null || (comment = commentTree.comment) == null || !comment.isDeprecated()) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final SymbolBasedClass findClassInSymbols(ClassId classId) {
        TypeElement typeElement = this.elements.getTypeElement(classId.asSingleFqName().asString());
        if (typeElement != null) {
            return new SymbolBasedClass(typeElement, this, classId, ((Symbol.ClassSymbol) typeElement).classfile);
        }
        return null;
    }

    private final SymbolBasedPackage findPackageInSymbols(String fqName) {
        SymbolBasedPackage symbolBasedPackage = this.symbolBasedPackagesCache.get(fqName);
        if (symbolBasedPackage != null) {
            return symbolBasedPackage;
        }
        ArrayList arrayList = new ArrayList();
        Iterator<PackagePartProvider> it = this.packagePartsProviders.iterator();
        while (it.hasNext()) {
            java.util.List<String> listFindPackageParts = it.next().findPackageParts(fqName);
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(listFindPackageParts, 10));
            Iterator<T> it2 = listFindPackageParts.iterator();
            while (it2.hasNext()) {
                arrayList2.add(StringsKt.replace$default(StringsKt.substringBeforeLast$default((String) it2.next(), "/", (String) null, 2, (Object) null), '/', '.', false, 4, (Object) null));
            }
            ArrayList arrayList3 = new ArrayList();
            for (Object obj : arrayList2) {
                if (!Intrinsics.areEqual((String) obj, fqName)) {
                    arrayList3.add(obj);
                }
            }
            ArrayList arrayList4 = arrayList;
            java.util.List listDistinct = CollectionsKt.distinct(arrayList3);
            ArrayList arrayList5 = new ArrayList();
            Iterator it3 = listDistinct.iterator();
            while (it3.hasNext()) {
                SimpleSymbolBasedPackage simpleSymbolBasedPackageFindPackageInSymbols$findSimplePackageInSymbols = findPackageInSymbols$findSimplePackageInSymbols(this, (String) it3.next());
                if (simpleSymbolBasedPackageFindPackageInSymbols$findSimplePackageInSymbols != null) {
                    arrayList5.add(simpleSymbolBasedPackageFindPackageInSymbols$findSimplePackageInSymbols);
                }
            }
            CollectionsKt.addAll(arrayList4, arrayList5);
        }
        if (!arrayList.isEmpty()) {
            MappedSymbolBasedPackage mappedSymbolBasedPackage = new MappedSymbolBasedPackage(new FqName(fqName), arrayList, this);
            this.symbolBasedPackagesCache.put(fqName, mappedSymbolBasedPackage);
            return mappedSymbolBasedPackage;
        }
        return findPackageInSymbols$findSimplePackageInSymbols(this, fqName);
    }

    private static final SimpleSymbolBasedPackage findPackageInSymbols$findSimplePackageInSymbols(JavacWrapper javacWrapper, String str) {
        PackageElement packageElement = javacWrapper.elements.getPackageElement(str);
        SimpleSymbolBasedPackage simpleSymbolBasedPackage = packageElement != null ? new SimpleSymbolBasedPackage(packageElement, javacWrapper) : null;
        javacWrapper.symbolBasedPackagesCache.put(str, simpleSymbolBasedPackage);
        return simpleSymbolBasedPackage;
    }

    private final void makeOutputDirectoryClassesVisible() {
        File file;
        ClassReader classReaderInstance = ClassReader.instance(this.context);
        Names namesInstance = Names.instance(this.context);
        Iterable location = this.fileManager.getLocation(StandardLocation.CLASS_OUTPUT);
        String path = (location == null || (file = (File) CollectionsKt.firstOrNull(location)) == null) ? null : file.getPath();
        if (path == null) {
            path = "";
        }
        Iterable<JavaFileObject> list = this.fileManager.list(StandardLocation.CLASS_OUTPUT, "", SetsKt.setOf(JavaFileObject.Kind.CLASS), true);
        Intrinsics.checkNotNullExpressionValue(list, "list(...)");
        for (JavaFileObject javaFileObject : list) {
            String name2 = javaFileObject.getName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            String strSubstringBefore$default = StringsKt.substringBefore$default(StringsKt.substringAfter$default(name2, path, (String) null, 2, (Object) null), ".class", (String) null, 2, (Object) null);
            String separator = File.separator;
            Intrinsics.checkNotNullExpressionValue(separator, "separator");
            String strReplace$default = StringsKt.replace$default(strSubstringBefore$default, separator, ".", false, 4, (Object) null);
            if (StringsKt.startsWith$default(strReplace$default, ".", false, 2, (Object) null)) {
                strReplace$default = strReplace$default.substring(1);
                Intrinsics.checkNotNullExpressionValue(strReplace$default, "substring(...)");
            }
            Intrinsics.checkNotNull(namesInstance);
            com.sun.tools.javac.util.Name nameFromString = namesInstance.fromString(strReplace$default);
            if (((Symbol.ClassSymbol) this.symbolTable.classes.get(nameFromString)) != null) {
                Map classes = this.symbolTable.classes;
                Intrinsics.checkNotNullExpressionValue(classes, "classes");
                classes.put(nameFromString, null);
            }
            Symbol symbolEnterClass = classReaderInstance.enterClass(nameFromString, javaFileObject);
            Symbol.PackageSymbol packageOf = this.elements.getPackageOf((Element) symbolEnterClass);
            Symbol.PackageSymbol packageSymbol = packageOf instanceof Symbol.PackageSymbol ? packageOf : null;
            if (packageSymbol != null) {
                Scope scope = packageSymbol.members_field;
                if (scope != null) {
                    scope.enter(symbolEnterClass);
                }
                packageSymbol.flags_field |= 8388608;
            }
        }
    }

    private final JavacWrapper setClassPathForCompilation(File outDir) {
        if (outDir == null) {
            outDir = this.outputDirectory;
        }
        if (outDir != null) {
            if (outDir.exists() && this.aptOn) {
                JavacFileManager javacFileManager = this.fileManager;
                JavaFileManager.Location location = StandardLocation.CLASS_PATH;
                Iterable location2 = this.fileManager.getLocation(StandardLocation.CLASS_PATH);
                Intrinsics.checkNotNullExpressionValue(location2, "getLocation(...)");
                javacFileManager.setLocation(location, CollectionsKt.plus((Iterable<? extends File>) location2, outDir));
            }
            outDir.mkdirs();
            this.fileManager.setLocation(StandardLocation.CLASS_OUTPUT, CollectionsKt.listOf(outDir));
        }
        return this;
    }

    private final SymbolBasedClass findClass(Symbol.PackageSymbol packageSymbol, ClassId classId) {
        Iterable elementsByName;
        Iterable elementsByName2;
        String strAsString = classId.getRelativeClassName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        java.util.List listSplit$default = StringsKt.split$default((CharSequence) StringsKt.replace$default(strAsString, "$", ".", false, 4, (Object) null), new String[]{"."}, false, 0, 6, (Object) null);
        Scope scope = packageSymbol.members_field;
        Symbol symbol = (scope == null || (elementsByName2 = scope.getElementsByName(this.names.fromString((String) CollectionsKt.first(listSplit$default)))) == null) ? null : (Symbol) CollectionsKt.firstOrNull(elementsByName2);
        Symbol.ClassSymbol classSymbol = symbol instanceof Symbol.ClassSymbol ? (Symbol.ClassSymbol) symbol : null;
        if (classSymbol == null) {
            return null;
        }
        if (listSplit$default.size() > 1) {
            classSymbol.complete();
            for (String str : CollectionsKt.drop(listSplit$default, 1)) {
                Scope scope2 = classSymbol.members_field;
                Symbol symbol2 = (scope2 == null || (elementsByName = scope2.getElementsByName(this.names.fromString(str))) == null) ? null : (Symbol) CollectionsKt.firstOrNull(elementsByName);
                classSymbol = symbol2 instanceof Symbol.ClassSymbol ? (Symbol.ClassSymbol) symbol2 : null;
                if (classSymbol == null) {
                    return null;
                }
                classSymbol.complete();
            }
        }
        SymbolBasedClass symbolBasedClass = new SymbolBasedClass((TypeElement) classSymbol, this, classId, classSymbol.classfile);
        this.symbolBasedClassesCache.put(classId, symbolBasedClass);
        return symbolBasedClass;
    }
}
