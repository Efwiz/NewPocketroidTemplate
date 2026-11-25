package org.catrobat.catroid.utils.git;

import android.util.Log;
import com.reandroid.apk.ApkUtil;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import name.antonsmirnov.firmata.FormatHelper;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.common.SoundInfo;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.XmlHeader;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.NoteBrick;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.UserList;
import org.catrobat.catroid.formulaeditor.UserVariable;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/git/ProjectMerger.class
  classes14.dex
 */
/* JADX INFO: compiled from: ProjectMerger.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\f\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\r\u0018\u0000 42\u00020\u0001:\u00014B\u0005¢\u0006\u0002\u0010\u0002J&\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\f2\u0006\u0010\r\u001a\u00020\u000bH\u0002J6\u0010\b\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\u000b2\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\f2\u0006\u0010\u000f\u001a\u00020\u000b2\u0006\u0010\u0010\u001a\u00020\u000b2\u0006\u0010\u0011\u001a\u00020\u000bH\u0002J\u0018\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00012\u0006\u0010\u0015\u001a\u00020\u0001H\u0002J \u0010\u0016\u001a\u00020\u00132\n\u0010\u0017\u001a\u0006\u0012\u0002\b\u00030\f2\n\u0010\u0018\u001a\u0006\u0012\u0002\b\u00030\fH\u0002J\u001c\u0010\u0019\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u00012\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001H\u0002J\u0018\u0010\u001a\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00012\u0006\u0010\u0015\u001a\u00020\u0001H\u0002J\u0012\u0010\u001b\u001a\u00020\u00012\b\u0010\u001c\u001a\u0004\u0018\u00010\u0001H\u0002J\u001a\u0010\u001d\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u001e\u001a\u00020\u00012\u0006\u0010\u001f\u001a\u00020 H\u0002J\u001a\u0010!\u001a\b\u0012\u0004\u0012\u00020\"0\f2\n\u0010#\u001a\u0006\u0012\u0002\b\u00030$H\u0002J&\u0010%\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u00012\b\u0010\u0015\u001a\u0004\u0018\u00010\u00012\b\u0010&\u001a\u0004\u0018\u00010\u0001H\u0002J\u001e\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000b2\u0006\u0010*\u001a\u00020\u000bJ@\u0010+\u001a\u00020\t2\u0006\u0010\u001f\u001a\u00020 2\u000e\u0010,\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u00042\u000e\u0010-\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\f2\u000e\u0010.\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\fH\u0002J(\u0010/\u001a\u00020\t2\u0006\u0010\u001f\u001a\u00020 2\u0006\u00100\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u00012\u0006\u0010\u0011\u001a\u00020\u0001H\u0002J \u00101\u001a\u00020\u00132\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000b2\u0006\u00102\u001a\u00020\u0005H\u0002J\u0018\u00101\u001a\u00020\u00132\u0006\u0010\u000e\u001a\u00020\u000b2\u0006\u00102\u001a\u00020\u0005H\u0002J \u00103\u001a\u00020\u00132\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000b2\u0006\u00102\u001a\u00020\u0005H\u0002J\u0018\u00103\u001a\u00020\u00132\u0006\u0010\u000e\u001a\u00020\u000b2\u0006\u00102\u001a\u00020\u0005H\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00065"}, d2 = {"Lorg/catrobat/catroid/utils/git/ProjectMerger;", "", "()V", "conflicts", "", "Lorg/catrobat/catroid/utils/git/Conflict;", "pathPattern", "Ljava/util/regex/Pattern;", "applyConflictResolutions", "", "mergedProject", "Lorg/catrobat/catroid/content/Project;", "", "localProject", "project", ApkUtil.DEF_MODULE_NAME, "local", "remote", "areFieldsEqual", "", "o1", "o2", "areListsSemanticallyEqual", "l1", "l2", "areObjectsEqual", "areSemanticallyEqual", "findId", "item", "findObjectByPath", "root", "path", "", "getAllFields", "Ljava/lang/reflect/Field;", "startClass", "Ljava/lang/Class;", "isMergableObject", "o3", "merge", "Lorg/catrobat/catroid/utils/git/MergeResultData;", "baseProject", "remoteProject", "mergeList", "mergedList", "baseList", "remoteList", "mergeObject", "merged", "tryResolveBrickConflict", "conflict", "tryResolveScriptConflict", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ProjectMerger {
    private static final Object NULL_ID = new Object();
    private static final String TAG = "ProjectMerger";
    private final List<Conflict> conflicts = new ArrayList();
    private final Pattern pathPattern;

    public ProjectMerger() {
        Pattern patternCompile = Pattern.compile("(.*)\\[(.*)]");
        Intrinsics.checkNotNullExpressionValue(patternCompile, "compile(...)");
        this.pathPattern = patternCompile;
    }

    public final MergeResultData merge(Project baseProject, Project localProject, Project remoteProject) {
        Intrinsics.checkNotNullParameter(baseProject, "baseProject");
        Intrinsics.checkNotNullParameter(localProject, "localProject");
        Intrinsics.checkNotNullParameter(remoteProject, "remoteProject");
        this.conflicts.clear();
        Log.d(TAG, "Starting V8 (conflict resolution merge)...");
        Project project = (Project) XStreamUtilGit.INSTANCE.deepCopy(localProject);
        mergeObject("project", project, baseProject, remoteProject);
        Log.d(TAG, "Semantic merge finished. Found " + this.conflicts.size() + " conflicts to resolve.");
        if (!this.conflicts.isEmpty()) {
            applyConflictResolutions(project, this.conflicts, localProject);
        }
        return new MergeResultData(project, this.conflicts);
    }

    private final void applyConflictResolutions(Project mergedProject, List<Conflict> conflicts, Project localProject) {
        for (Conflict conflict : conflicts) {
            try {
                Log.d(TAG, "Attempting to resolve conflict at: " + conflict.getPath());
                if (!tryResolveBrickConflict(mergedProject, localProject, conflict)) {
                    tryResolveScriptConflict(mergedProject, localProject, conflict);
                }
            } catch (Exception e) {
                Log.e(TAG, "Failed to programmatically resolve conflict at " + conflict.getPath(), e);
            }
        }
    }

    private final boolean tryResolveBrickConflict(Project mergedProject, Project localProject, Conflict conflict) throws IllegalAccessException {
        Object next;
        if ((conflict.getLocalValue() instanceof Brick) && (conflict.getRemoteValue() instanceof Brick)) {
            Object objFindObjectByPath = findObjectByPath(mergedProject, StringsKt.substringBeforeLast$default(conflict.getPath(), ".", (String) null, 2, (Object) null));
            Script script = objFindObjectByPath instanceof Script ? (Script) objFindObjectByPath : null;
            if (script == null) {
                return false;
            }
            List<Brick> brickList = script.getBrickList();
            Intrinsics.checkNotNull(brickList);
            Iterator<T> it = brickList.iterator();
            do {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
            } while (!Intrinsics.areEqual(findId((Brick) next), findId(conflict.getRemoteValue())));
            Brick brick = (Brick) next;
            if (brick == null) {
                return false;
            }
            int iIndexOf = brickList.indexOf(brick);
            Object objFindObjectByPath2 = findObjectByPath(localProject, conflict.getPath());
            Brick brick2 = objFindObjectByPath2 instanceof Brick ? (Brick) objFindObjectByPath2 : null;
            if (brick2 != null && iIndexOf != -1) {
                Log.i(TAG, "Resolving BRICK conflict for brick " + findId(brick2) + ". Duplicating local version.");
                Brick brick3 = (Brick) XStreamUtilGit.INSTANCE.deepCopy(brick2);
                brick3.setCommentedOut(true);
                NoteBrick noteBrick = new NoteBrick("--- КОНФЛИКТ: ВАША ВЕРСИЯ НИЖЕ ---");
                NoteBrick noteBrick2 = new NoteBrick("--- КОНЕЦ КОНФЛИКТА ---");
                brickList.add(iIndexOf + 1, noteBrick);
                brickList.add(iIndexOf + 2, brick3);
                brickList.add(iIndexOf + 3, noteBrick2);
                return true;
            }
        }
        return false;
    }

    private final boolean tryResolveScriptConflict(Project mergedProject, Project localProject, Conflict conflict) throws IllegalAccessException {
        Object next;
        if ((conflict.getLocalValue() instanceof Script) && (conflict.getRemoteValue() instanceof Script)) {
            Object objFindObjectByPath = findObjectByPath(mergedProject, StringsKt.substringBeforeLast$default(conflict.getPath(), ".", (String) null, 2, (Object) null));
            Sprite sprite = objFindObjectByPath instanceof Sprite ? (Sprite) objFindObjectByPath : null;
            if (sprite == null) {
                return false;
            }
            List<Script> scriptList = sprite.getScriptList();
            Intrinsics.checkNotNull(scriptList);
            Iterator<T> it = scriptList.iterator();
            do {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
            } while (!Intrinsics.areEqual(findId((Script) next), findId(conflict.getRemoteValue())));
            Script script = (Script) next;
            if (script == null) {
                return false;
            }
            int iIndexOf = scriptList.indexOf(script);
            Object objFindObjectByPath2 = findObjectByPath(localProject, conflict.getPath());
            Script script2 = objFindObjectByPath2 instanceof Script ? (Script) objFindObjectByPath2 : null;
            if (script2 != null && iIndexOf != -1) {
                Log.i(TAG, "Resolving SCRIPT conflict for script " + findId(script2) + ". Duplicating local version.");
                Script script3 = (Script) XStreamUtilGit.INSTANCE.deepCopy(script2);
                script3.setCommentedOut(true);
                scriptList.add(iIndexOf + 1, script3);
                return true;
            }
        }
        return false;
    }

    private final Object findObjectByPath(Object root, String path) throws IllegalAccessException {
        Object next;
        Object next2;
        if (Intrinsics.areEqual(path, "project")) {
            return root;
        }
        for (String str : StringsKt.split$default((CharSequence) StringsKt.substringAfter$default(path, ".", (String) null, 2, (Object) null), new char[]{'.'}, false, 0, 6, (Object) null)) {
            if (root == null) {
                return null;
            }
            Matcher matcher = this.pathPattern.matcher(str);
            if (matcher.matches()) {
                String strGroup = matcher.group(1);
                String strGroup2 = matcher.group(2);
                Iterator<T> it = getAllFields(root.getClass()).iterator();
                do {
                    if (!it.hasNext()) {
                        next = null;
                        break;
                    }
                    next = it.next();
                } while (!Intrinsics.areEqual(((Field) next).getName(), strGroup));
                Field field = (Field) next;
                if (field == null) {
                    return null;
                }
                field.setAccessible(true);
                Object obj = field.get(root);
                List list = obj instanceof List ? (List) obj : null;
                if (list == null) {
                    return null;
                }
                Iterator it2 = list.iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        root = null;
                        break;
                    }
                    Object next3 = it2.next();
                    if (Intrinsics.areEqual(findId(next3).toString(), strGroup2)) {
                        root = next3;
                        break;
                    }
                }
            } else {
                Iterator<T> it3 = getAllFields(root.getClass()).iterator();
                do {
                    if (!it3.hasNext()) {
                        next2 = null;
                        break;
                    }
                    next2 = it3.next();
                } while (!Intrinsics.areEqual(((Field) next2).getName(), str));
                Field field2 = (Field) next2;
                if (field2 == null) {
                    return null;
                }
                field2.setAccessible(true);
                root = field2.get(root);
            }
        }
        return root;
    }

    private final void mergeObject(String path, Object merged, Object base, Object remote) {
        Iterator<Field> it;
        if (Intrinsics.areEqual(merged.getClass(), base.getClass()) && Intrinsics.areEqual(base.getClass(), remote.getClass())) {
            Iterator<Field> it2 = getAllFields(merged.getClass()).iterator();
            while (it2.hasNext()) {
                Field next = it2.next();
                try {
                    if (!Modifier.isStatic(next.getModifiers()) && !Modifier.isTransient(next.getModifiers())) {
                        next.setAccessible(true);
                        Object obj = next.get(merged);
                        Object obj2 = next.get(base);
                        try {
                            Object obj3 = next.get(remote);
                            if (!areObjectsEqual(obj, obj2) || !areObjectsEqual(obj2, obj3)) {
                                String str = path + "." + next.getName();
                                Log.d(TAG, "Difference found in field '" + str + FormatHelper.QUOTE);
                                if (TypeIntrinsics.isMutableList(obj) && (obj2 instanceof List) && (obj3 instanceof List)) {
                                    Log.d(TAG, "-> Entering list merge for '" + str + FormatHelper.QUOTE);
                                    Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.collections.MutableList<kotlin.Any?>");
                                    mergeList(str, TypeIntrinsics.asMutableList(obj), (List) obj2, (List) obj3);
                                } else if (isMergableObject(obj, obj2, obj3)) {
                                    Log.d(TAG, "-> Entering recursive merge for '" + str + FormatHelper.QUOTE);
                                    Intrinsics.checkNotNull(obj);
                                    Intrinsics.checkNotNull(obj2);
                                    Intrinsics.checkNotNull(obj3);
                                    mergeObject(str, obj, obj2, obj3);
                                } else {
                                    boolean zAreObjectsEqual = areObjectsEqual(obj2, obj3);
                                    boolean z = !zAreObjectsEqual;
                                    boolean zAreObjectsEqual2 = areObjectsEqual(obj2, obj);
                                    it = it2;
                                    try {
                                        Log.d(TAG, "  -> Path '" + str + "': remoteChanged[" + z + "], localChanged[" + (!zAreObjectsEqual2) + "]");
                                        if (!zAreObjectsEqual && zAreObjectsEqual2) {
                                            Log.i(TAG, "  -> APPLY: Applying remote value to '" + str + FormatHelper.QUOTE);
                                            next.set(merged, obj3);
                                        } else if (zAreObjectsEqual && !zAreObjectsEqual2) {
                                            Log.i(TAG, "  -> KEEP: Keeping local change for '" + str + FormatHelper.QUOTE);
                                        } else if (!zAreObjectsEqual && !zAreObjectsEqual2 && !areObjectsEqual(obj, obj3)) {
                                            Log.w(TAG, "  -> CONFLICT: Value changed on both sides at '" + str + "'.");
                                            List<Conflict> list = this.conflicts;
                                            String name2 = next.getName();
                                            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                                            list.add(new Conflict(str, name2, obj2, obj, obj3));
                                            next.set(merged, obj3);
                                        }
                                    } catch (Exception e) {
                                        e = e;
                                        Log.e(TAG, "Error merging field " + next.getName() + " at path " + path, e);
                                    }
                                    it2 = it;
                                }
                            }
                        } catch (Exception e2) {
                            e = e2;
                            it = it2;
                            Log.e(TAG, "Error merging field " + next.getName() + " at path " + path, e);
                            it2 = it;
                        }
                    }
                } catch (Exception e3) {
                    e = e3;
                }
            }
        }
    }

    private final void mergeList(String path, List<Object> mergedList, List<? extends Object> baseList, List<? extends Object> remoteList) {
        final ProjectMerger projectMerger = this;
        Function1<Object, Object> function1 = new Function1<Object, Object>() { // from class: org.catrobat.catroid.utils.git.ProjectMerger$mergeList$idSelector$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return this.this$0.findId(obj);
            }
        };
        List<? extends Object> list = baseList;
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(list, 10)), 16));
        for (Object obj : list) {
            linkedHashMap.put(function1.invoke(obj), obj);
        }
        List<Object> list2 = mergedList;
        LinkedHashMap linkedHashMap2 = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(list2, 10)), 16));
        for (Object obj2 : list2) {
            linkedHashMap2.put(function1.invoke(obj2), obj2);
        }
        List<? extends Object> list3 = remoteList;
        LinkedHashMap linkedHashMap3 = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(list3, 10)), 16));
        for (Object obj3 : list3) {
            linkedHashMap3.put(function1.invoke(obj3), obj3);
        }
        List listDistinct = CollectionsKt.distinct(SetsKt.plus(SetsKt.plus(linkedHashMap.keySet(), (Iterable) linkedHashMap2.keySet()), (Iterable) linkedHashMap3.keySet()));
        ArrayList arrayList = new ArrayList();
        for (Object obj4 : listDistinct) {
            Object obj5 = linkedHashMap.get(obj4);
            Object obj6 = linkedHashMap2.get(obj4);
            Object obj7 = linkedHashMap3.get(obj4);
            boolean z = obj5 != null;
            boolean z2 = obj6 != null;
            boolean z3 = obj7 != null;
            if (z && z2 && z3) {
                boolean zAreObjectsEqual = projectMerger.areObjectsEqual(obj5, obj7);
                boolean zAreObjectsEqual2 = projectMerger.areObjectsEqual(obj5, obj6);
                if (!zAreObjectsEqual2 && zAreObjectsEqual) {
                    arrayList.add(obj6);
                } else if (zAreObjectsEqual2 && !zAreObjectsEqual) {
                    arrayList.add(obj7);
                } else if (!zAreObjectsEqual2 && !zAreObjectsEqual) {
                    if (projectMerger.areObjectsEqual(obj6, obj7)) {
                        arrayList.add(obj6);
                    } else {
                        XStreamUtilGit xStreamUtilGit = XStreamUtilGit.INSTANCE;
                        Intrinsics.checkNotNull(obj6);
                        Object objDeepCopy = xStreamUtilGit.deepCopy(obj6);
                        Intrinsics.checkNotNull(obj5);
                        Intrinsics.checkNotNull(obj7);
                        projectMerger.mergeObject(path + "[" + obj4 + "]", objDeepCopy, obj5, obj7);
                        arrayList.add(objDeepCopy);
                    }
                } else {
                    arrayList.add(obj6);
                }
            } else if (!z && z2 && !z3) {
                arrayList.add(obj6);
            } else if (!z && !z3 && z2) {
                arrayList.add(obj7);
            } else if (!z && z2 && z3) {
                projectMerger.conflicts.add(new Conflict(path, "add/add on item " + obj4, null, obj6, obj7));
                arrayList.add(obj6);
                if (!projectMerger.areObjectsEqual(obj6, obj7)) {
                    arrayList.add(obj7);
                }
            } else if (z && !z2 && z3) {
                if (!projectMerger.areObjectsEqual(obj5, obj7)) {
                    projectMerger.conflicts.add(new Conflict(path, "delete/modify on item " + obj4, obj5, obj6, null));
                }
            } else {
                if (z && z2 && !z3 && !projectMerger.areObjectsEqual(obj5, obj6)) {
                    projectMerger.conflicts.add(new Conflict(path, "modify/delete on item " + obj4, obj5, obj6, null));
                    arrayList.add(obj6);
                }
                projectMerger = this;
            }
        }
        mergedList.clear();
        mergedList.addAll(arrayList);
    }

    private final void applyConflictResolutions(Project project, List<Conflict> conflicts, Project base, Project local, Project remote) {
        for (Conflict conflict : conflicts) {
            try {
                Log.d(TAG, "Attempting to resolve conflict at: " + conflict.getPath());
                if (!tryResolveBrickConflict(project, conflict)) {
                    tryResolveScriptConflict(project, conflict);
                }
            } catch (Exception e) {
                Log.e(TAG, "Failed to resolve conflict at " + conflict.getPath(), e);
            }
        }
    }

    private final boolean tryResolveScriptConflict(Project project, Conflict conflict) throws IllegalAccessException, CloneNotSupportedException {
        int iIndexOf;
        Object localValue = conflict.getLocalValue();
        Object obj = null;
        Script script = localValue instanceof Script ? (Script) localValue : null;
        if (script == null) {
            return false;
        }
        Object objFindObjectByPath = findObjectByPath(project, StringsKt.substringBeforeLast$default(conflict.getPath(), ".", (String) null, 2, (Object) null));
        Sprite sprite = objFindObjectByPath instanceof Sprite ? (Sprite) objFindObjectByPath : null;
        if (sprite == null) {
            return false;
        }
        List<Script> scriptList = sprite.getScriptList();
        Intrinsics.checkNotNull(scriptList);
        for (Object obj2 : scriptList) {
            if (Intrinsics.areEqual(findId((Script) obj2), findId(script))) {
                obj = obj2;
                break;
            }
        }
        Script script2 = (Script) obj;
        if (script2 == null || (iIndexOf = scriptList.indexOf(script2)) == -1) {
            return false;
        }
        Log.d(TAG, "Resolving SCRIPT conflict for script " + findId(script));
        Script scriptClone = script.clone();
        Intrinsics.checkNotNull(scriptClone, "null cannot be cast to non-null type org.catrobat.catroid.content.Script");
        scriptClone.setCommentedOut(true);
        scriptList.add(iIndexOf + 1, scriptClone);
        return true;
    }

    private final boolean tryResolveBrickConflict(Project project, Conflict conflict) throws IllegalAccessException, CloneNotSupportedException {
        int iIndexOf;
        Object localValue = conflict.getLocalValue();
        Object obj = null;
        Brick brick = localValue instanceof Brick ? (Brick) localValue : null;
        if (brick == null) {
            return false;
        }
        Object remoteValue = conflict.getRemoteValue();
        if ((remoteValue instanceof Brick ? (Brick) remoteValue : null) == null) {
            return false;
        }
        Object objFindObjectByPath = findObjectByPath(project, StringsKt.substringBeforeLast$default(conflict.getPath(), ".", (String) null, 2, (Object) null));
        Script script = objFindObjectByPath instanceof Script ? (Script) objFindObjectByPath : null;
        if (script == null) {
            return false;
        }
        List<Brick> brickList = script.getBrickList();
        Intrinsics.checkNotNull(brickList);
        for (Object obj2 : brickList) {
            if (Intrinsics.areEqual(findId((Brick) obj2), findId(brick))) {
                obj = obj2;
                break;
            }
        }
        Brick brick2 = (Brick) obj;
        if (brick2 == null || (iIndexOf = brickList.indexOf(brick2)) == -1) {
            return false;
        }
        Log.d(TAG, "Resolving BRICK conflict for brick " + findId(brick));
        Brick brickClone = brick.clone();
        Intrinsics.checkNotNull(brickClone, "null cannot be cast to non-null type org.catrobat.catroid.content.bricks.Brick");
        brickClone.setCommentedOut(true);
        NoteBrick noteBrick = new NoteBrick("--- КОНФЛИКТ --- (Ниже версия с сервера)");
        NoteBrick noteBrick2 = new NoteBrick("--- ВАША ВЕРСИЯ ---");
        NoteBrick noteBrick3 = new NoteBrick("--- КОНЕЦ КОНФЛИКТА ---");
        brickList.add(iIndexOf, noteBrick);
        brickList.add(iIndexOf + 2, noteBrick2);
        brickList.add(iIndexOf + 3, brickClone);
        brickList.add(iIndexOf + 4, noteBrick3);
        return true;
    }

    private final boolean areObjectsEqual(Object o1, Object o2) {
        if (o1 == o2) {
            return true;
        }
        if (o1 == null || o2 == null || !Intrinsics.areEqual(o1.getClass(), o2.getClass())) {
            return false;
        }
        return areSemanticallyEqual(o1, o2);
    }

    private final boolean areSemanticallyEqual(Object o1, Object o2) {
        if (o1 instanceof List) {
            Intrinsics.checkNotNull(o2, "null cannot be cast to non-null type kotlin.collections.List<*>");
            return areListsSemanticallyEqual((List) o1, (List) o2);
        }
        if (!(o1 instanceof Project) && !(o1 instanceof Scene) && !(o1 instanceof Sprite) && !(o1 instanceof Script)) {
            if (o1 instanceof LookData ? true : o1 instanceof SoundInfo ? true : o1 instanceof UserVariable ? true : o1 instanceof UserList) {
                return o1.equals(o2);
            }
            return o1 instanceof XmlHeader ? true : o1 instanceof Brick ? true : o1 instanceof Formula ? Intrinsics.areEqual(XStreamUtilGit.INSTANCE.toXMLAny(o1), XStreamUtilGit.INSTANCE.toXMLAny(o2)) : Objects.equals(o1, o2);
        }
        return areFieldsEqual(o1, o2);
    }

    private final boolean areListsSemanticallyEqual(List<?> l1, List<?> l2) {
        if (l1.size() != l2.size()) {
            return false;
        }
        List listSortedWith = CollectionsKt.sortedWith(l1, new Comparator() { // from class: org.catrobat.catroid.utils.git.ProjectMerger$areListsSemanticallyEqual$$inlined$sortedBy$1
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                return ComparisonsKt.compareValues(Integer.valueOf(this.this$0.findId(t).hashCode()), Integer.valueOf(this.this$0.findId(t2).hashCode()));
            }
        });
        List listSortedWith2 = CollectionsKt.sortedWith(l2, new Comparator() { // from class: org.catrobat.catroid.utils.git.ProjectMerger$areListsSemanticallyEqual$$inlined$sortedBy$2
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                return ComparisonsKt.compareValues(Integer.valueOf(this.this$0.findId(t).hashCode()), Integer.valueOf(this.this$0.findId(t2).hashCode()));
            }
        });
        int size = listSortedWith.size();
        for (int i = 0; i < size; i++) {
            Object obj = listSortedWith.get(i);
            Intrinsics.checkNotNull(obj);
            Object obj2 = listSortedWith2.get(i);
            Intrinsics.checkNotNull(obj2);
            if (!areSemanticallyEqual(obj, obj2)) {
                return false;
            }
        }
        return true;
    }

    private final boolean areFieldsEqual(Object o1, Object o2) throws IllegalAccessException {
        for (Field field : getAllFields(o1.getClass())) {
            if (!Modifier.isStatic(field.getModifiers()) && !Modifier.isTransient(field.getModifiers())) {
                field.setAccessible(true);
                Object obj = field.get(o1);
                Object obj2 = field.get(o2);
                String name2 = field.getName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                if (!StringsKt.endsWith$default(name2, "Id", false, 2, (Object) null) && !areObjectsEqual(obj, obj2)) {
                    return false;
                }
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object findId(Object item) {
        Object name2;
        if (item == null) {
            return NULL_ID;
        }
        if (item instanceof Sprite) {
            name2 = ((Sprite) item).getSpriteId();
        } else if (item instanceof Scene) {
            name2 = ((Scene) item).getSceneId();
        } else if (item instanceof Script) {
            name2 = ((Script) item).getScriptId();
        } else if (item instanceof Brick) {
            name2 = ((Brick) item).getBrickID();
        } else if (item instanceof LookData) {
            name2 = ((LookData) item).getLookId();
        } else if (item instanceof SoundInfo) {
            name2 = ((SoundInfo) item).soundId;
        } else if (item instanceof UserVariable) {
            name2 = ((UserVariable) item).getName();
        } else {
            name2 = item instanceof UserList ? ((UserList) item).getName() : Integer.valueOf(item.hashCode());
        }
        Intrinsics.checkNotNull(name2);
        return name2;
    }

    private final boolean isMergableObject(Object o1, Object o2, Object o3) {
        if (o1 == null || o2 == null || o3 == null) {
            return false;
        }
        boolean z = ((o1 instanceof String) || (o1 instanceof Number) || (o1 instanceof Boolean) || (o1 instanceof Enum) || (o1 instanceof Class)) ? false : true;
        Package r4 = o1.getClass().getPackage();
        String name2 = r4 != null ? r4.getName() : null;
        if (name2 == null) {
            name2 = "";
        }
        return z && StringsKt.startsWith$default(name2, "org.catrobat", false, 2, (Object) null);
    }

    private final List<Field> getAllFields(Class<?> startClass) {
        ArrayList arrayList = new ArrayList();
        while (startClass != null && !Intrinsics.areEqual(startClass, Object.class)) {
            Field[] declaredFields = startClass.getDeclaredFields();
            Intrinsics.checkNotNullExpressionValue(declaredFields, "getDeclaredFields(...)");
            CollectionsKt.addAll(arrayList, declaredFields);
            startClass = startClass.getSuperclass();
        }
        return arrayList;
    }
}
