package org.jetbrains.kotlin.com.intellij.psi.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubInputStream;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubOutputStream;

/* JADX INFO: loaded from: classes3.dex */
public final class QualifiedName implements Comparable<QualifiedName> {
    private final List<String> myComponents;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
            default:
                str = "@NotNull method %s.%s must not return null";
                break;
        }
        switch (i) {
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                i2 = 3;
                break;
            default:
                i2 = 2;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 5:
                objArr[0] = "prefix";
                break;
            case 6:
                objArr[0] = "suffix";
                break;
            case 7:
                objArr[0] = "refName";
                break;
            case 8:
                objArr[0] = "other";
                break;
            case 9:
                objArr[0] = "component";
                break;
            default:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/util/QualifiedName";
                break;
        }
        switch (i) {
            case 1:
                objArr[1] = "removeLastComponent";
                break;
            case 2:
                objArr[1] = "removeTail";
                break;
            case 3:
                objArr[1] = "removeHead";
                break;
            case 4:
                objArr[1] = "getComponents";
                break;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/util/QualifiedName";
                break;
            default:
                objArr[1] = "fromComponents";
                break;
        }
        switch (i) {
            case 5:
                objArr[2] = "matchesPrefix";
                break;
            case 6:
                objArr[2] = "endsWith";
                break;
            case 7:
                objArr[2] = "fromDottedString";
                break;
            case 8:
                objArr[2] = "compareTo";
                break;
            case 9:
                objArr[2] = "assertNoDots";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                throw new IllegalArgumentException(str2);
            default:
                throw new IllegalStateException(str2);
        }
    }

    private QualifiedName(int i) {
        this.myComponents = new ArrayList(i);
    }

    public static QualifiedName fromComponents(Collection<String> collection) {
        Iterator<String> it = collection.iterator();
        while (it.hasNext()) {
            assertNoDots(it.next());
        }
        QualifiedName qualifiedName = new QualifiedName(collection.size());
        qualifiedName.myComponents.addAll(collection);
        return qualifiedName;
    }

    public static QualifiedName fromComponents(String... strArr) {
        for (String str : strArr) {
            assertNoDots(str);
        }
        QualifiedName qualifiedName = new QualifiedName(strArr.length);
        Collections.addAll(qualifiedName.myComponents, strArr);
        return qualifiedName;
    }

    public QualifiedName append(String str) {
        QualifiedName qualifiedName = new QualifiedName(this.myComponents.size() + 1);
        qualifiedName.myComponents.addAll(this.myComponents);
        qualifiedName.myComponents.add(str);
        return qualifiedName;
    }

    public QualifiedName append(QualifiedName qualifiedName) {
        QualifiedName qualifiedName2 = new QualifiedName(this.myComponents.size() + qualifiedName.getComponentCount());
        qualifiedName2.myComponents.addAll(this.myComponents);
        qualifiedName2.myComponents.addAll(qualifiedName.getComponents());
        return qualifiedName2;
    }

    public QualifiedName removeLastComponent() {
        QualifiedName qualifiedNameRemoveTail = removeTail(1);
        if (qualifiedNameRemoveTail == null) {
            $$$reportNull$$$0(1);
        }
        return qualifiedNameRemoveTail;
    }

    public QualifiedName removeTail(int i) {
        QualifiedName qualifiedName = new QualifiedName(this.myComponents.size());
        qualifiedName.myComponents.addAll(this.myComponents);
        for (int i2 = 0; i2 < i && !qualifiedName.myComponents.isEmpty(); i2++) {
            List<String> list = qualifiedName.myComponents;
            list.remove(list.size() - 1);
        }
        return qualifiedName;
    }

    public QualifiedName removeHead(int i) {
        QualifiedName qualifiedName = new QualifiedName(this.myComponents.size());
        qualifiedName.myComponents.addAll(this.myComponents);
        for (int i2 = 0; i2 < i && !qualifiedName.myComponents.isEmpty(); i2++) {
            qualifiedName.myComponents.remove(0);
        }
        return qualifiedName;
    }

    public List<String> getComponents() {
        List<String> listUnmodifiableList = Collections.unmodifiableList(this.myComponents);
        if (listUnmodifiableList == null) {
            $$$reportNull$$$0(4);
        }
        return listUnmodifiableList;
    }

    public int getComponentCount() {
        return this.myComponents.size();
    }

    public boolean matches(String... strArr) {
        if (this.myComponents.size() != strArr.length) {
            return false;
        }
        for (int i = 0; i < this.myComponents.size(); i++) {
            if (!this.myComponents.get(i).equals(strArr[i])) {
                return false;
            }
        }
        return true;
    }

    public boolean matchesPrefix(QualifiedName qualifiedName) {
        if (qualifiedName == null) {
            $$$reportNull$$$0(5);
        }
        if (getComponentCount() < qualifiedName.getComponentCount()) {
            return false;
        }
        for (int i = 0; i < qualifiedName.getComponentCount(); i++) {
            String str = getComponents().get(i);
            if (str == null || !str.equals(qualifiedName.getComponents().get(i))) {
                return false;
            }
        }
        return true;
    }

    public boolean endsWith(String str) {
        if (str == null) {
            $$$reportNull$$$0(6);
        }
        return str.equals(getLastComponent());
    }

    public static void serialize(QualifiedName qualifiedName, StubOutputStream stubOutputStream) throws IOException {
        if (qualifiedName == null) {
            stubOutputStream.writeVarInt(0);
            return;
        }
        stubOutputStream.writeVarInt(qualifiedName.getComponentCount());
        Iterator<String> it = qualifiedName.myComponents.iterator();
        while (it.hasNext()) {
            stubOutputStream.writeName(it.next());
        }
    }

    public static QualifiedName deserialize(StubInputStream stubInputStream) throws IOException {
        int varInt = stubInputStream.readVarInt();
        if (varInt == 0) {
            return null;
        }
        QualifiedName qualifiedName = new QualifiedName(varInt);
        for (int i = 0; i < varInt; i++) {
            qualifiedName.myComponents.add(stubInputStream.readNameString());
        }
        return qualifiedName;
    }

    public String getFirstComponent() {
        if (this.myComponents.isEmpty()) {
            return null;
        }
        return this.myComponents.get(0);
    }

    public String getLastComponent() {
        if (this.myComponents.isEmpty()) {
            return null;
        }
        List<String> list = this.myComponents;
        return list.get(list.size() - 1);
    }

    public String toString() {
        return join(".");
    }

    public String join(String str) {
        return StringUtil.join((Collection<String>) this.myComponents, str);
    }

    public static QualifiedName fromDottedString(String str) {
        if (str == null) {
            $$$reportNull$$$0(7);
        }
        return fromComponents(str.split("\\."));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.myComponents.equals(((QualifiedName) obj).myComponents);
    }

    public int hashCode() {
        return this.myComponents.hashCode();
    }

    public QualifiedName subQualifiedName(int i, int i2) {
        return fromComponents(this.myComponents.subList(i, i2));
    }

    @Override // java.lang.Comparable
    public int compareTo(QualifiedName qualifiedName) {
        if (qualifiedName == null) {
            $$$reportNull$$$0(8);
        }
        return toString().compareTo(qualifiedName.toString());
    }

    private static void assertNoDots(String str) {
        if (str == null) {
            $$$reportNull$$$0(9);
        }
        if (str.contains(".")) {
            throw new IllegalArgumentException("Components of QualifiedName cannot contain dots inside them, but got: " + str);
        }
    }
}
