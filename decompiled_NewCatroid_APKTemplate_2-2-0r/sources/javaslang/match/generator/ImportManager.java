package javaslang.match.generator;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javaslang.match.model.ClassModel;

/* JADX INFO: loaded from: classes11.dex */
class ImportManager {
    private List<String> directImports;
    private Map<FQN, Import> imports = new HashMap();
    private final String packageNameOfClass;

    private ImportManager(String str, List<String> list) {
        this.packageNameOfClass = str;
        this.directImports = list;
    }

    public static ImportManager forClass(ClassModel classModel, String... strArr) {
        return new ImportManager(classModel.getPackageName(), reverseSort(strArr));
    }

    public String getType(ClassModel classModel) {
        return getType(new FQN(classModel.getPackageName(), classModel.getClassName()), this.imports, this.packageNameOfClass, this.directImports);
    }

    public String getType(String str, String str2) {
        return getType(new FQN(str, str2), this.imports, this.packageNameOfClass, this.directImports);
    }

    public String getImports() {
        return optimizeImports(this.imports.values());
    }

    private static String getType(FQN fqn, Map<FQN, Import> map, String str, List<String> list) {
        if (fqn.packageName.isEmpty() && !str.isEmpty()) {
            throw new IllegalStateException("Can't import class '" + fqn.className + "' located in default package");
        }
        if (fqn.packageName.equals(str)) {
            Import importCreateImport = createImport(fqn, list);
            if (!importCreateImport.type.equals(fqn.className)) {
                map.put(fqn, importCreateImport);
                return importCreateImport.type;
            }
            return fqn.className;
        }
        if (map.containsKey(fqn)) {
            return map.get(fqn).type;
        }
        Import importCreateImport2 = createImport(fqn, list);
        map.put(fqn, importCreateImport2);
        return importCreateImport2.type;
    }

    private static Import createImport(FQN fqn, List<String> list) {
        final String strQualifiedName = fqn.qualifiedName();
        Optional<String> optionalFindFirst = list.stream().filter(new Predicate() { // from class: javaslang.match.generator.ImportManager$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ImportManager.lambda$createImport$0(strQualifiedName, (String) obj);
            }
        }).findFirst();
        if (optionalFindFirst.isPresent()) {
            String str = optionalFindFirst.get();
            if (strQualifiedName.equals(str)) {
                return new Import(str, str.substring(str.lastIndexOf(46) + 1));
            }
            String strSubstring = strQualifiedName.substring(str.length() + 1);
            int iIndexOf = strSubstring.indexOf(".");
            return new Import(str + "." + (iIndexOf < 0 ? strSubstring : strSubstring.substring(0, iIndexOf)), strSubstring);
        }
        int iIndexOf2 = fqn.className.indexOf(".");
        return new Import(fqn.packageName + "." + (iIndexOf2 < 0 ? fqn.className : fqn.className.substring(0, iIndexOf2)), fqn.className);
    }

    static /* synthetic */ boolean lambda$createImport$0(String str, String str2) {
        return str.equals(str2) || str.startsWith(new StringBuilder().append(str2).append(".").toString());
    }

    private static String optimizeImports(final Collection<Import> collection) {
        return (String) collection.stream().filter(new Predicate() { // from class: javaslang.match.generator.ImportManager$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ImportManager.lambda$optimizeImports$4(collection, (ImportManager.Import) obj);
            }
        }).map(new Function() { // from class: javaslang.match.generator.ImportManager$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((ImportManager.Import) obj).f292name;
            }
        }).distinct().sorted().map(new Function() { // from class: javaslang.match.generator.ImportManager$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ImportManager.lambda$optimizeImports$6((String) obj);
            }
        }).collect(Collectors.joining("\n"));
    }

    static /* synthetic */ boolean lambda$optimizeImports$4(Collection collection, final Import r3) {
        return !r3.f292name.startsWith("java.lang.") || collection.stream().filter(new Predicate() { // from class: javaslang.match.generator.ImportManager$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ImportManager.lambda$null$1(r3, (ImportManager.Import) obj);
            }
        }).map(new Function() { // from class: javaslang.match.generator.ImportManager$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((ImportManager.Import) obj).type;
            }
        }).filter(new Predicate() { // from class: javaslang.match.generator.ImportManager$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((String) obj).equals(r3.type);
            }
        }).findFirst().isPresent();
    }

    static /* synthetic */ boolean lambda$null$1(Import r0, Import r1) {
        return !r1.equals(r0);
    }

    static /* synthetic */ String lambda$optimizeImports$6(String str) {
        return "import " + str + ";";
    }

    private static List<String> reverseSort(String[] strArr) {
        String[] strArr2 = new String[strArr.length];
        System.arraycopy(strArr, 0, strArr2, 0, strArr.length);
        Arrays.sort(strArr2, new Comparator() { // from class: javaslang.match.generator.ImportManager$$ExternalSyntheticLambda1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return ((String) obj2).compareTo((String) obj);
            }
        });
        return Arrays.asList(strArr2);
    }

    private static class FQN {
        private final String className;
        private final String packageName;

        FQN(String str, String str2) {
            this.packageName = str;
            this.className = str2;
        }

        String qualifiedName() {
            return this.packageName.isEmpty() ? this.className : this.packageName + "." + this.className;
        }

        public boolean equals(Object obj) {
            return obj == this || ((obj instanceof FQN) && qualifiedName().equals(((FQN) obj).qualifiedName()));
        }

        public int hashCode() {
            return qualifiedName().hashCode();
        }

        public String toString() {
            return "FQN(" + this.packageName + ", " + this.className + Constant.AFTER_QUTO;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class Import {

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        final String f292name;
        final String type;

        Import(String str, String str2) {
            this.f292name = str;
            this.type = str2;
        }

        public boolean equals(Object obj) {
            return obj == this || ((obj instanceof Import) && toString().equals(obj.toString()));
        }

        public int hashCode() {
            return toString().hashCode();
        }

        public String toString() {
            return "Import(" + this.f292name + ", " + this.type + Constant.AFTER_QUTO;
        }
    }
}
