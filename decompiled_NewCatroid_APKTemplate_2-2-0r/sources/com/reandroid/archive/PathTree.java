package com.reandroid.archive;

import com.reandroid.json.JSONArray;
import com.reandroid.json.JSONConvert;
import com.reandroid.json.JSONObject;
import com.reandroid.utils.CompareUtil;
import com.reandroid.utils.StringsUtil;
import com.reandroid.utils.collection.ArrayCollection;
import com.reandroid.utils.collection.ComputeIterator;
import com.reandroid.utils.collection.MergingIterator;
import com.reandroid.utils.collection.SingleIterator;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/* JADX INFO: loaded from: classes10.dex */
public class PathTree<T> implements Comparable<PathTree<?>>, Iterable<PathTree<T>>, JSONConvert<JSONObject> {
    public static final String NAME_elements = "elements";
    public static final String NAME_path = "path";
    private final LinkedHashMap<String, PathTree<T>> elementsMap;
    private T item;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private String f221name;
    private PathTree<T> parent;

    public PathTree(T t, String str) {
        this.item = t;
        this.f221name = str;
        this.elementsMap = new LinkedHashMap<>();
    }

    public PathTree(String str) {
        this(null, str);
    }

    public PathTree<T> copy() {
        return copy(getName());
    }

    public PathTree<T> copy(String str) {
        PathTree<T> pathTree = new PathTree<>(getItem(), str);
        pathTree.setItem(getItem());
        Iterator<PathTree<T>> it = iterator();
        while (it.hasNext()) {
            pathTree.add(it.next().copy());
        }
        return pathTree;
    }

    public void setName(String str) {
        String str2 = this.f221name;
        if (str2.equals(str)) {
            return;
        }
        this.f221name = str;
        PathTree<T> pathTree = this.parent;
        if (pathTree != null) {
            pathTree.elementsMap.remove(str2);
            pathTree.elementsMap.put(str, this);
        }
    }

    public void sort() {
        sort(true);
    }

    public void sort(boolean z) {
        sort(CompareUtil.getComparableComparator(), z);
    }

    public void sort(Comparator<? super PathTree<?>> comparator, boolean z) {
        LinkedHashMap<String, PathTree<T>> linkedHashMap = this.elementsMap;
        if (linkedHashMap.size() == 0) {
            return;
        }
        PathTree<T>[] pathTreeArr = (PathTree[]) linkedHashMap.values().toArray(new PathTree[0]);
        Arrays.sort(pathTreeArr, comparator);
        linkedHashMap.clear();
        for (PathTree<T> pathTree : pathTreeArr) {
            linkedHashMap.put(pathTree.getName(), pathTree);
        }
        if (z) {
            for (PathTree<T> pathTree2 : pathTreeArr) {
                pathTree2.sort(comparator, true);
            }
        }
    }

    public List<PathTree<T>> toList() {
        return ArrayCollection.of((Iterable) this.elementsMap.values());
    }

    @Override // java.lang.Iterable
    public Iterator<PathTree<T>> iterator() {
        return this.elementsMap.values().iterator();
    }

    public Iterator<PathTree<T>> getFiles() {
        return new MergingIterator(ComputeIterator.of(iterator(), new Function() { // from class: com.reandroid.archive.PathTree$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return PathTree.lambda$getFiles$0((PathTree) obj);
            }
        }));
    }

    static /* synthetic */ Iterator lambda$getFiles$0(PathTree pathTree) {
        if (pathTree.isFile()) {
            return SingleIterator.of(pathTree);
        }
        return pathTree.getFiles();
    }

    public Iterator<T> getFileItems() {
        return ComputeIterator.of(getFiles(), new Function() { // from class: com.reandroid.archive.PathTree$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((PathTree) obj).getItem();
            }
        });
    }

    public int size() {
        return this.elementsMap.size();
    }

    public boolean contains(String str) {
        return this.elementsMap.containsKey(str);
    }

    public PathTree<T> remove(String str) {
        return this.elementsMap.remove(str);
    }

    public PathTree<T> getOrCreate(String str) {
        PathTree<T> pathTree = get(str);
        if (pathTree != null) {
            return pathTree;
        }
        PathTree<T> pathTree2 = new PathTree<>(str);
        add(pathTree2);
        return pathTree2;
    }

    public PathTree<T> get(String str) {
        return this.elementsMap.get(str);
    }

    public PathTree<T> find(String str) {
        if (str.equals(getName())) {
            return this;
        }
        int iIndexOf = str.indexOf(47);
        if (iIndexOf >= 0) {
            int i = iIndexOf + 1;
            String strSubstring = str.substring(0, i);
            String strSubstring2 = str.substring(i);
            PathTree<T> pathTree = get(strSubstring);
            return (pathTree == null || strSubstring2.length() == 0) ? pathTree : pathTree.find(strSubstring2);
        }
        if (str.length() > 0) {
            return get(str);
        }
        return null;
    }

    public PathTree<T> add(String str, T t) {
        int iIndexOf = str.indexOf(47);
        if (iIndexOf >= 0) {
            int i = iIndexOf + 1;
            String strSubstring = str.substring(0, i);
            String strSubstring2 = str.substring(i);
            PathTree<T> orCreate = getOrCreate(strSubstring);
            if (strSubstring2.length() == 0) {
                orCreate.setItemNonNull(t);
                return orCreate;
            }
            return orCreate.add(strSubstring2, t);
        }
        if (str.length() <= 0) {
            return this;
        }
        PathTree<T> orCreate2 = getOrCreate(str);
        orCreate2.setItem(t);
        return orCreate2;
    }

    public boolean add(PathTree<T> pathTree) {
        if (pathTree == null) {
            return false;
        }
        pathTree.setParent(this);
        this.elementsMap.put(pathTree.getName(), pathTree);
        return true;
    }

    public T getItem() {
        return this.item;
    }

    public void setItem(T t) {
        this.item = t;
    }

    private void setItemNonNull(T t) {
        if (t != null) {
            setItem(t);
        }
    }

    public boolean isDirectory() {
        return getName().endsWith("/");
    }

    public boolean isFile() {
        return !isDirectory();
    }

    public boolean isRoot() {
        return isDirectory() && getParent() == null;
    }

    public String getName() {
        return this.f221name;
    }

    public PathTree<T> getParent() {
        return this.parent;
    }

    public void setParent(PathTree<T> pathTree) {
        if (pathTree != this) {
            this.parent = pathTree;
        }
    }

    public int getDepth() {
        int i = 0;
        for (PathTree<T> parent = this; parent != null && !parent.isRoot(); parent = parent.getParent()) {
            i++;
        }
        return i;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private PathTree<?>[] getParentElements() {
        int depth = getDepth();
        PathTree<?>[] pathTreeArr = new PathTree[depth];
        int i = depth - 1;
        for (PathTree<T> parent = this; parent != null && !parent.isRoot(); parent = parent.getParent()) {
            pathTreeArr[i] = parent;
            i--;
        }
        return pathTreeArr;
    }

    public String getPath() {
        StringBuilder sb = new StringBuilder();
        for (PathTree<?> pathTree : getParentElements()) {
            sb.append(pathTree.getName());
        }
        return sb.toString();
    }

    @Override // java.lang.Comparable
    public int compareTo(PathTree<?> pathTree) {
        boolean zIsDirectory = isDirectory();
        if (zIsDirectory != pathTree.isDirectory()) {
            return zIsDirectory ? -1 : 1;
        }
        String name2 = getName();
        String name3 = pathTree.getName();
        if (zIsDirectory) {
            name2 = name2.substring(0, name2.length() - 1);
            name3 = name3.substring(0, name3.length() - 1);
        }
        return StringsUtil.toLowercase(name2).compareTo(StringsUtil.toLowercase(name3));
    }

    @Override // com.reandroid.json.JSONConvert
    public void fromJson(JSONObject jSONObject) {
        JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray(NAME_elements);
        if (jSONArrayOptJSONArray == null) {
            return;
        }
        int length = jSONArrayOptJSONArray.length();
        for (int i = 0; i < length; i++) {
            JSONObject jSONObject2 = jSONArrayOptJSONArray.getJSONObject(i);
            getOrCreate(getName(jSONObject2.optString("path", ""))).fromJson(jSONObject2);
        }
    }

    @Override // com.reandroid.json.JSONConvert
    public JSONObject toJson() {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("path", getPath());
        JSONArray jSONArray = new JSONArray();
        Iterator<PathTree<T>> it = iterator();
        while (it.hasNext()) {
            jSONArray.put(it.next().toJson());
        }
        if (!jSONArray.isEmpty()) {
            jSONObject.put(NAME_elements, jSONArray);
        }
        return jSONObject;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof PathTree) {
            return Objects.equals(getName(), ((PathTree) obj).getName());
        }
        return false;
    }

    public int hashCode() {
        return getName().hashCode();
    }

    public String toString() {
        return getPath();
    }

    public static <T1> PathTree<T1> newRoot() {
        return new PathTree<>("/");
    }

    public static Iterator<String> sortPaths(Iterator<String> it) {
        PathTree pathTreeNewRoot = newRoot();
        while (it.hasNext()) {
            String next = it.next();
            pathTreeNewRoot.add(next, next);
        }
        pathTreeNewRoot.sort();
        return pathTreeNewRoot.getFileItems();
    }

    private static String getName(String str) {
        int length = str.length();
        if (length < 2) {
            return str;
        }
        int i = length - 1;
        boolean z = false;
        if (str.charAt(i) == '/') {
            str = str.substring(0, i);
            z = true;
        }
        int iLastIndexOf = str.lastIndexOf(47);
        if (iLastIndexOf >= 0) {
            str = str.substring(iLastIndexOf + 1);
        }
        return z ? str + '/' : str;
    }
}
