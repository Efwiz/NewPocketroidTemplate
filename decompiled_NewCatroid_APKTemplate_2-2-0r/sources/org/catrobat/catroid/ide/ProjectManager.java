package org.catrobat.catroid.ide;

import android.content.Context;
import com.android.apksig.internal.apk.v4.V4Signature;
import com.badlogic.gdx.graphics.GL20;
import com.reandroid.apk.PathMap;
import com.reandroid.arsc.value.ResConfigBase;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.catrobat.catroid.BuildConfig;
import org.catrobat.catroid.common.Constants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ide/ProjectManager.class
  classes14.dex
 */
/* JADX INFO: compiled from: ProjectManager.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J:\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u00042\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u000e0\rJ\u0016\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0004J\u0016\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u0004J\u0014\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u00142\u0006\u0010\u000f\u001a\u00020\u0010J\u000e\u0010\u0016\u001a\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u0010J\u000e\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u0004J\u0016\u0010\u001a\u001a\u00020\u000e2\u0006\u0010\u0019\u001a\u00020\u00042\u0006\u0010\u001b\u001a\u00020\u0018R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u001c"}, d2 = {"Lorg/catrobat/catroid/ide/ProjectManager;", "", "()V", "PROJECTS_DIR_NAME", "", "createProject", "", "context", "Landroid/content/Context;", "name", "packageName", ModuleXmlParser.TYPE, "onProgress", "Lkotlin/Function1;", "", "filesDir", "Ljava/io/File;", "deleteProject", "projectName", "getAllProjects", "", "Lorg/catrobat/catroid/ide/ProjectMetadata;", "getProjectsFolder", "loadConfig", "Lorg/catrobat/catroid/ide/ProjectConfig;", Constants.EXTRA_PROJECT_PATH, "saveConfig", "config", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ProjectManager {
    public static final ProjectManager INSTANCE = new ProjectManager();
    private static final String PROJECTS_DIR_NAME = "NecatProjects";

    private ProjectManager() {
    }

    public final File getProjectsFolder(File filesDir) {
        Intrinsics.checkNotNullParameter(filesDir, "filesDir");
        File file = new File(filesDir, PROJECTS_DIR_NAME);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    public final List<ProjectMetadata> getAllProjects(File filesDir) {
        Intrinsics.checkNotNullParameter(filesDir, "filesDir");
        File[] fileArrListFiles = getProjectsFolder(filesDir).listFiles();
        if (fileArrListFiles != null) {
            ArrayList arrayList = new ArrayList();
            for (File file : fileArrListFiles) {
                if (file.isDirectory()) {
                    arrayList.add(file);
                }
            }
            ArrayList<File> arrayList2 = arrayList;
            ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
            for (File file2 : arrayList2) {
                String name2 = file2.getName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                String absolutePath = file2.getAbsolutePath();
                Intrinsics.checkNotNullExpressionValue(absolutePath, "getAbsolutePath(...)");
                arrayList3.add(new ProjectMetadata(name2, absolutePath, file2.lastModified()));
            }
            List<ProjectMetadata> listSortedWith = CollectionsKt.sortedWith(arrayList3, new Comparator() { // from class: org.catrobat.catroid.ide.ProjectManager$getAllProjects$$inlined$sortedByDescending$1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // java.util.Comparator
                public final int compare(T t, T t2) {
                    return ComparisonsKt.compareValues(Long.valueOf(((ProjectMetadata) t2).getLastModified()), Long.valueOf(((ProjectMetadata) t).getLastModified()));
                }
            });
            if (listSortedWith != null) {
                return listSortedWith;
            }
        }
        return CollectionsKt.emptyList();
    }

    public final boolean createProject(File filesDir, String name2) throws JSONException {
        Intrinsics.checkNotNullParameter(filesDir, "filesDir");
        Intrinsics.checkNotNullParameter(name2, "name");
        File file = new File(getProjectsFolder(filesDir), name2);
        if (!file.exists() && file.mkdirs()) {
            new File(file, "src").mkdirs();
            new File(file, "assets").mkdirs();
            ProjectConfig projectConfig = new ProjectConfig(name2, "com.example." + name2, "1.0.0", 1, "sensor", CollectionsKt.emptyList(), CollectionsKt.emptyList(), true, null, false, false, 0, 0, GL20.GL_VENDOR, null);
            String absolutePath = file.getAbsolutePath();
            Intrinsics.checkNotNullExpressionValue(absolutePath, "getAbsolutePath(...)");
            saveConfig(absolutePath, projectConfig);
            return true;
        }
        return false;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Multi-variable type inference failed */
    public final boolean createProject(Context context, String name2, String packageName, String type, Function1<? super String, Unit> onProgress) throws JSONException {
        int i;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(onProgress, "onProgress");
        onProgress.invoke("Создание структуры проекта...");
        File filesDir = context.getFilesDir();
        Intrinsics.checkNotNullExpressionValue(filesDir, "getFilesDir(...)");
        File file = new File(getProjectsFolder(filesDir), name2);
        if (file.exists()) {
            onProgress.invoke("Ошибка: Проект уже существует");
            return false;
        }
        if (!file.mkdirs()) {
            return false;
        }
        File file2 = new File(file, "src/game");
        file2.mkdirs();
        new File(file, "assets").mkdirs();
        ProjectConfig projectConfig = new ProjectConfig(name2, packageName, "1.0.0", 1, "portrait", CollectionsKt.listOf("android.permission.INTERNET"), CollectionsKt.emptyList(), Intrinsics.areEqual(type, "console") || Intrinsics.areEqual(type, "jsoup") || Intrinsics.areEqual(type, "empty") || Intrinsics.areEqual(type, "background_task") || Intrinsics.areEqual(type, "file_io") || Intrinsics.areEqual(type, "gson") || Intrinsics.areEqual(type, "zxing"), null, false, false, 0, 0, V4Signature.MAX_SIGNING_INFOS_SIZE, null);
        String absolutePath = file.getAbsolutePath();
        Intrinsics.checkNotNullExpressionValue(absolutePath, "getAbsolutePath(...)");
        saveConfig(absolutePath, projectConfig);
        File file3 = new File(file2, "Main.java");
        switch (type.hashCode()) {
            case -1873009031:
                if (type.equals("canvas_game")) {
                    FilesKt.writeText$default(file3, "package game;\n\nimport android.content.Context;\nimport android.view.View;\nimport android.view.ViewGroup;\nimport android.graphics.Canvas;\nimport android.graphics.Paint;\nimport android.graphics.Color;\n\npublic class Main {\n\n    public void onStart(Context context, ViewGroup layout) {\n        GameView view = new GameView(context);\n        layout.addView(view);\n    }\n\n    static class GameView extends View {\n        Paint paint = new Paint();\n        float x = 100;\n        float dx = 10;\n\n        public GameView(Context c) {\n            super(c);\n            paint.setColor(Color.RED);\n        }\n\n        @Override\n        protected void onDraw(Canvas canvas) {\n            canvas.drawColor(Color.BLACK);\n            canvas.drawCircle(x, 300, 50, paint);\n            x += dx;\n            if (x > getWidth() || x < 0) dx = -dx;\n            invalidate();\n        }\n    }\n}", null, 2, null);
                }
                return true;
            case -1103027562:
                File file4 = file;
                File file5 = file2;
                i = 1;
                Function1<? super String, Unit> function1 = onProgress;
                if (type.equals("libgdx")) {
                    for (String str : CollectionsKt.listOf((Object[]) new String[]{"com.badlogicgames.gdx:gdx:1.12.1", "com.badlogicgames.gdx:gdx-jnigen-loader:2.3.1", "com.badlogicgames.gdx:gdx-backend-android:1.12.1", "com.badlogicgames.gdx:gdx-platform:1.12.1:natives-arm64-v8a", "androidx.fragment:fragment:1.3.6", "androidx.activity:activity:1.2.3", "androidx.loader:loader:1.0.0", "androidx.viewpager:viewpager:1.0.0", "androidx.customview:customview:1.0.0", "androidx.collection:collection:1.1.0", "androidx.core:core:1.6.0", "androidx.versionedparcelable:versionedparcelable:1.1.1", "androidx.lifecycle:lifecycle-common:2.3.1", "androidx.lifecycle:lifecycle-viewmodel:2.3.1", "androidx.lifecycle:lifecycle-livedata-core:2.3.1", "androidx.lifecycle:lifecycle-viewmodel-savedstate:2.3.1", "androidx.savedstate:savedstate:1.1.0", "androidx.annotation:annotation:1.2.0"})) {
                        char[] cArr = new char[i];
                        cArr[0] = ':';
                        function1.invoke("Скачивание: " + StringsKt.split$default((CharSequence) str, cArr, false, 0, 6, (Object) null).get(i) + "...");
                        DependencyManager dependencyManager = DependencyManager.INSTANCE;
                        String absolutePath2 = file4.getAbsolutePath();
                        Intrinsics.checkNotNullExpressionValue(absolutePath2, "getAbsolutePath(...)");
                        File file6 = file4;
                        File file7 = file5;
                        Function1<? super String, Unit> function2 = function1;
                        if (!DependencyManager.downloadLibraryRecursive$default(dependencyManager, context, absolutePath2, str, null, false, new Function2<String, DownloadStatus, Unit>() { // from class: org.catrobat.catroid.ide.ProjectManager$createProject$success$1
                            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                            public final void invoke2(String str2, DownloadStatus downloadStatus) {
                                Intrinsics.checkNotNullParameter(str2, "<anonymous parameter 0>");
                                Intrinsics.checkNotNullParameter(downloadStatus, "<anonymous parameter 1>");
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public /* bridge */ /* synthetic */ Unit invoke(String str2, DownloadStatus downloadStatus) {
                                invoke2(str2, downloadStatus);
                                return Unit.INSTANCE;
                            }
                        }, 8, null)) {
                            function2.invoke("Ошибка загрузки: " + str);
                            return true;
                        }
                        function1 = function2;
                        file4 = file6;
                        file5 = file7;
                        i = 1;
                    }
                    File file8 = file5;
                    function1.invoke("Генерация кода...");
                    FilesKt.writeText$default(file3, "package game;\nimport android.content.Context;\nimport android.view.ViewGroup;\nimport android.view.View;\nimport androidx.fragment.app.FragmentActivity;\nimport androidx.fragment.app.FragmentManager;\nimport androidx.fragment.app.Fragment;\n\npublic class Main {\n    public void onStart(Context context, ViewGroup layout) {\n        FragmentActivity activity = (FragmentActivity) context;\n        FragmentManager fm = activity.getSupportFragmentManager();\n        String TAG = \"libgdx_container_tag\";\n\n        Fragment old = fm.findFragmentByTag(TAG);\n        if (old != null) {\n            fm.beginTransaction().remove(old).commitNow();\n        }\n\n        int containerId = View.generateViewId();\n        layout.setId(containerId);\n\n        ContainerFragment containerFrag = new ContainerFragment();\n        \n        fm.beginTransaction()\n          .add(containerId, containerFrag, TAG)\n          .commitNow();\n    }\n}", null, 2, null);
                    FilesKt.writeText$default(new File(file8, "ContainerFragment.java"), "package game;\nimport android.os.Bundle;\nimport android.view.LayoutInflater;\nimport android.view.View;\nimport android.view.ViewGroup;\nimport android.widget.FrameLayout;\nimport androidx.fragment.app.Fragment;\nimport com.badlogic.gdx.backends.android.AndroidFragmentApplication;\n\npublic class ContainerFragment extends Fragment implements AndroidFragmentApplication.Callbacks {\n    \n    @Override\n    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {\n        FrameLayout layout = new FrameLayout(getContext());\n        layout.setId(View.generateViewId());\n        \n        GameFragment gameFragment = new GameFragment();\n        \n        getChildFragmentManager().beginTransaction()\n            .add(layout.getId(), gameFragment)\n            .commitNow();\n            \n        return layout;\n    }\n\n    @Override\n    public void exit() {\n    }\n}", null, 2, null);
                    FilesKt.writeText$default(new File(file8, "GameFragment.java"), "package game;\nimport android.os.Bundle;\nimport android.view.LayoutInflater;\nimport android.view.View;\nimport android.view.ViewGroup;\nimport com.badlogic.gdx.Gdx;\nimport com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;\nimport com.badlogic.gdx.backends.android.AndroidFragmentApplication;\n\npublic class GameFragment extends AndroidFragmentApplication {\n    \n    @Override\n    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {\n        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();\n        config.useAccelerometer = false;\n        config.useCompass = false;\n        config.r = 8; config.g = 8; config.b = 8; config.a = 8;\n        return initializeForView(new MyGame(), config);\n    }\n\n    @Override\n    public void onPause() {\n        if (Gdx.graphics != null) {\n            Gdx.graphics.setContinuousRendering(false);\n            Gdx.graphics.requestRendering();\n        }\n        super.onPause();\n    }\n\n    @Override\n    public void onDestroy() {\n        try {\n            if (Gdx.app != null) Gdx.app.exit();\n        } catch (Exception e) {}\n        \n        Gdx.app = null;\n        Gdx.graphics = null;\n        super.onDestroy();\n    }\n}", null, 2, null);
                    FilesKt.writeText$default(new File(file8, "MyGame.java"), "package game;\nimport com.badlogic.gdx.ApplicationAdapter;\nimport com.badlogic.gdx.Gdx;\nimport com.badlogic.gdx.graphics.GL20;\npublic class MyGame extends ApplicationAdapter {\n    float r = 0;\n    @Override public void create() { Gdx.app.log(\"MyGame\", \"Game Created!\"); }\n    @Override public void render() {\n        r += 0.01f;\n        Gdx.gl.glClearColor((float)Math.sin(r), 0, (float)Math.cos(r), 1);\n        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);\n    }\n}", null, 2, null);
                    return true;
                }
                return i;
            case -855000375:
                i = 1;
                if (type.equals("file_io")) {
                    FilesKt.writeText$default(file3, "package game;\n\nimport android.content.Context;\nimport java.io.File;\nimport java.io.FileWriter;\nimport java.util.Scanner;\n\npublic class Main {\n\n    public void onStart(Context context) {\n        try {\n            File f = new File(context.getFilesDir(), \"test.txt\");\n            FileWriter w = new FileWriter(f);\n            w.write(\"Hello Necat!\");\n            w.close();\n\n            Scanner s = new Scanner(f);\n            System.out.println(\"File content: \" + s.nextLine());\n            s.close();\n        } catch (Exception e) {\n            System.out.println(\"Error: \" + e.getMessage());\n        }\n    }\n}", null, 2, null);
                }
                return i;
            case 3182539:
                i = 1;
                if (type.equals("gson")) {
                    DependencyManager dependencyManager2 = DependencyManager.INSTANCE;
                    String absolutePath3 = file.getAbsolutePath();
                    Intrinsics.checkNotNullExpressionValue(absolutePath3, "getAbsolutePath(...)");
                    DependencyManager.downloadLibraryRecursive$default(dependencyManager2, context, absolutePath3, "com.google.code.gson:gson:2.10.1", null, false, new Function2<String, DownloadStatus, Unit>() { // from class: org.catrobat.catroid.ide.ProjectManager.createProject.1
                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(String str2, DownloadStatus downloadStatus) {
                            Intrinsics.checkNotNullParameter(str2, "<anonymous parameter 0>");
                            Intrinsics.checkNotNullParameter(downloadStatus, "<anonymous parameter 1>");
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public /* bridge */ /* synthetic */ Unit invoke(String str2, DownloadStatus downloadStatus) {
                            invoke2(str2, downloadStatus);
                            return Unit.INSTANCE;
                        }
                    }, 24, null);
                    FilesKt.writeText$default(file3, "package game;\n\nimport com.google.gson.Gson;\n\nclass User {\n    String name;\n    int age;\n}\n\npublic class Main {\n    public void onStart() {\n        Gson gson = new Gson();\n        User u = new User();\n        u.name = \"Necat\";\n        u.age = 1;\n\n        String json = gson.toJson(u);\n        System.out.println(json);\n\n        User u2 = gson.fromJson(json, User.class);\n        System.out.println(u2.name + \" \" + u2.age);\n    }\n}", null, 2, null);
                }
                return i;
            case 96634189:
                i = 1;
                if (type.equals("empty")) {
                    FilesKt.writeText$default(file3, "package game;\n\npublic class Main {\n    public void onStart() {\n        \n    }\n}", null, 2, null);
                }
                return i;
            case 101429601:
                if (type.equals("jsoup")) {
                    onProgress.invoke("Скачивание: " + StringsKt.split$default((CharSequence) "org.jsoup:jsoup:1.13.1", new char[]{':'}, false, 0, 6, (Object) null).get(1) + "...");
                    DependencyManager dependencyManager3 = DependencyManager.INSTANCE;
                    String absolutePath4 = file.getAbsolutePath();
                    Intrinsics.checkNotNullExpressionValue(absolutePath4, "getAbsolutePath(...)");
                    i = 1;
                    if (!DependencyManager.downloadLibraryRecursive$default(dependencyManager3, context, absolutePath4, "org.jsoup:jsoup:1.13.1", null, false, new Function2<String, DownloadStatus, Unit>() { // from class: org.catrobat.catroid.ide.ProjectManager$createProject$success$2
                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(String str2, DownloadStatus downloadStatus) {
                            Intrinsics.checkNotNullParameter(str2, "<anonymous parameter 0>");
                            Intrinsics.checkNotNullParameter(downloadStatus, "<anonymous parameter 1>");
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public /* bridge */ /* synthetic */ Unit invoke(String str2, DownloadStatus downloadStatus) {
                            invoke2(str2, downloadStatus);
                            return Unit.INSTANCE;
                        }
                    }, 24, null)) {
                        onProgress.invoke("Ошибка загрузки: org.jsoup:jsoup:1.13.1");
                        return true;
                    }
                    onProgress.invoke("Генерация кода...");
                    FilesKt.writeText$default(file3, "package game;\nimport android.content.Context;\nimport org.jsoup.Jsoup;\nimport org.jsoup.nodes.Document;\n\npublic class Main {\n    public void onStart() {\n        System.out.println(\"Fetching Google title...\");\n        new Thread(new Runnable() {\n            @Override\n            public void run() {\n                try {\n                    Document doc = Jsoup.connect(\"https://www.google.com\").get();\n                    System.out.println(\"Title: \" + doc.title());\n                } catch (Exception e) {\n                    System.out.println(\"Error: \" + e.getMessage());\n                }\n            }\n        }).start();\n    }\n}", null, 2, null);
                    return i;
                }
                return true;
            case 116348900:
                if (type.equals("zxing")) {
                    DependencyManager dependencyManager4 = DependencyManager.INSTANCE;
                    String absolutePath5 = file.getAbsolutePath();
                    Intrinsics.checkNotNullExpressionValue(absolutePath5, "getAbsolutePath(...)");
                    DependencyManager.downloadLibraryRecursive$default(dependencyManager4, context, absolutePath5, "com.google.zxing:core:3.5.2", null, false, new Function2<String, DownloadStatus, Unit>() { // from class: org.catrobat.catroid.ide.ProjectManager.createProject.2
                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(String str2, DownloadStatus downloadStatus) {
                            Intrinsics.checkNotNullParameter(str2, "<anonymous parameter 0>");
                            Intrinsics.checkNotNullParameter(downloadStatus, "<anonymous parameter 1>");
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public /* bridge */ /* synthetic */ Unit invoke(String str2, DownloadStatus downloadStatus) {
                            invoke2(str2, downloadStatus);
                            return Unit.INSTANCE;
                        }
                    }, 24, null);
                    FilesKt.writeText$default(file3, "package game;\n\nimport com.google.zxing.qrcode.QRCodeWriter;\nimport com.google.zxing.BarcodeFormat;\nimport com.google.zxing.common.BitMatrix;\n\npublic class Main {\n    public void onStart() {\n        try {\n            QRCodeWriter writer = new QRCodeWriter();\n            BitMatrix matrix = writer.encode(\"Hello Necat!\", BarcodeFormat.QR_CODE, 10, 10);\n            System.out.println(\"QR generated: \" + matrix.getWidth());\n        } catch (Exception e) {\n            System.out.println(e.getMessage());\n        }\n    }\n}", null, 2, null);
                }
                return true;
            case 722989668:
                if (type.equals("android_ui")) {
                    FilesKt.writeText$default(file3, "package game;\nimport android.content.Context;\nimport android.view.ViewGroup;\nimport android.widget.Button;\nimport android.widget.TextView;\nimport android.widget.LinearLayout;\nimport android.graphics.Color;\nimport android.view.View;\nimport android.view.Gravity;\n\npublic class Main {\n    public void onStart(final Context context, ViewGroup layout) {\n        layout.setBackgroundColor(Color.parseColor(\"#202020\"));\n        \n        LinearLayout panel = new LinearLayout(context);\n        panel.setOrientation(LinearLayout.VERTICAL);\n        panel.setGravity(Gravity.CENTER);\n        \n        final TextView text = new TextView(context);\n        text.setText(\"Hello Android UI!\");\n        text.setTextSize(32);\n        text.setTextColor(Color.CYAN);\n        \n        Button btn = new Button(context);\n        btn.setText(\"Tap Me\");\n        btn.setOnClickListener(new View.OnClickListener() {\n            @Override\n            public void onClick(View v) {\n                text.setText(\"Tapped!\");\n                text.setTextColor(Color.GREEN);\n            }\n        });\n        \n        panel.addView(text);\n        panel.addView(btn);\n        layout.addView(panel);\n    }\n}", null, 2, null);
                }
                return true;
            case 951510359:
                if (type.equals("console")) {
                    FilesKt.writeText$default(file3, "package game;\npublic class Main {\n    public void onStart() {\n        System.out.println(\"Hello from Console!\");\n    }\n}", null, 2, null);
                }
                return true;
            case 981385993:
                if (type.equals("cpp_jni")) {
                    FilesKt.writeText$default(file3, "package game;\nimport android.content.Context;\nimport android.widget.TextView;\nimport android.view.ViewGroup;\nimport android.graphics.Color;\nimport android.view.Gravity;\n\npublic class Main {\n    static {\n        System.loadLibrary(\"native-lib\");\n    }\n\n    public native String stringFromJNI();\n\n    public void onStart(Context context, ViewGroup layout) {\n        TextView tv = new TextView(context);\n        tv.setText(stringFromJNI());\n        tv.setTextSize(30);\n        tv.setTextColor(Color.GREEN);\n        tv.setGravity(Gravity.CENTER);\n        layout.addView(tv);\n    }\n}", null, 2, null);
                    File file9 = new File(file, "src/main/cpp");
                    file9.mkdirs();
                    FilesKt.writeText$default(new File(file9, "native-lib.cpp"), "#include <jni.h>\n#include <string>\n\nextern \"C\" JNIEXPORT jstring JNICALL\nJava_game_Main_stringFromJNI(JNIEnv* env, jobject /* this */) {\n    std::string hello = \"Hello from C++!\";\n    return env->NewStringUTF(hello.c_str());\n}", null, 2, null);
                    FilesKt.writeText$default(new File(file9, "CMakeLists.txt"), "cmake_minimum_required(VERSION 3.10.2)\nproject(\"necat_cpp\")\n\nadd_library( \n     native-lib\n     SHARED\n     native-lib.cpp\n)\n\nfind_library( \n     log-lib\n     log\n)\n\ntarget_link_libraries( \n     native-lib\n     $\\{log-lib}\n)", null, 2, null);
                }
                return true;
            case 1313121782:
                if (type.equals("background_task")) {
                    FilesKt.writeText$default(file3, "package game;\n\npublic class Main {\n    private boolean running = true;\n\n    public void onStart() {\n        System.out.println(\"Background task started\");\n\n        new Thread(new Runnable() {\n            @Override\n            public void run() {\n                int i = 0;\n                while (running) {\n                    try {\n                        Thread.sleep(1000);\n                    } catch (InterruptedException e) {}\n\n                    System.out.println(\"Tick \" + i++);\n                }\n            }\n        }).start();\n    }\n\n    public void onStop() {\n        running = false;\n    }\n}", null, 2, null);
                }
                return true;
            default:
                return true;
        }
    }

    public final boolean deleteProject(File filesDir, String projectName) {
        Intrinsics.checkNotNullParameter(filesDir, "filesDir");
        Intrinsics.checkNotNullParameter(projectName, "projectName");
        File file = new File(getProjectsFolder(filesDir), projectName);
        if (file.exists()) {
            return FilesKt.deleteRecursively(file);
        }
        return false;
    }

    public final ProjectConfig loadConfig(String projectPath) {
        Intrinsics.checkNotNullParameter(projectPath, "projectPath");
        File file = new File(projectPath, "project.json");
        String name2 = new File(projectPath).getName();
        if (!file.exists()) {
            Intrinsics.checkNotNull(name2);
            String lowerCase = name2.toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
            return new ProjectConfig(name2, "com.example." + StringsKt.replace$default(lowerCase, " ", "", false, 4, (Object) null), "1.0.0", 1, "landscape", CollectionsKt.listOf("android.permission.INTERNET"), CollectionsKt.emptyList(), true, null, false, false, 0, 0, GL20.GL_KEEP, null);
        }
        try {
            SigningConfig signingConfig = null;
            JSONObject jSONObject = new JSONObject(FilesKt.readText$default(file, null, 1, null));
            ArrayList arrayList = new ArrayList();
            JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("repositories");
            if (jSONArrayOptJSONArray != null) {
                int length = jSONArrayOptJSONArray.length();
                for (int i = 0; i < length; i++) {
                    arrayList.add(jSONArrayOptJSONArray.getString(i));
                }
            }
            ArrayList arrayList2 = new ArrayList();
            JSONArray jSONArrayOptJSONArray2 = jSONObject.optJSONArray("permissions");
            if (jSONArrayOptJSONArray2 != null) {
                int length2 = jSONArrayOptJSONArray2.length();
                for (int i2 = 0; i2 < length2; i2++) {
                    arrayList2.add(jSONArrayOptJSONArray2.getString(i2));
                }
            }
            if (arrayList2.isEmpty()) {
                arrayList2.add("android.permission.INTERNET");
            }
            JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("signing");
            if (jSONObjectOptJSONObject != null) {
                String strOptString = jSONObjectOptJSONObject.optString("path", "release.jks");
                Intrinsics.checkNotNullExpressionValue(strOptString, "optString(...)");
                String strOptString2 = jSONObjectOptJSONObject.optString("pass", "");
                Intrinsics.checkNotNullExpressionValue(strOptString2, "optString(...)");
                String strOptString3 = jSONObjectOptJSONObject.optString(PathMap.NAME_alias, "key0");
                Intrinsics.checkNotNullExpressionValue(strOptString3, "optString(...)");
                String strOptString4 = jSONObjectOptJSONObject.optString("keyPass", "");
                Intrinsics.checkNotNullExpressionValue(strOptString4, "optString(...)");
                signingConfig = new SigningConfig(strOptString, strOptString2, strOptString3, strOptString4);
            }
            SigningConfig signingConfig2 = signingConfig;
            String strOptString5 = jSONObject.optString("name", name2);
            Intrinsics.checkNotNullExpressionValue(strOptString5, "optString(...)");
            Intrinsics.checkNotNull(name2);
            String lowerCase2 = name2.toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(lowerCase2, "toLowerCase(...)");
            String strOptString6 = jSONObject.optString("packageName", "com.danvex." + lowerCase2);
            Intrinsics.checkNotNullExpressionValue(strOptString6, "optString(...)");
            String strOptString7 = jSONObject.optString("version", "1.0.0");
            Intrinsics.checkNotNullExpressionValue(strOptString7, "optString(...)");
            int iOptInt = jSONObject.optInt("versionCode", 1);
            String strOptString8 = jSONObject.optString(ResConfigBase.NAME_orientation, "landscape");
            Intrinsics.checkNotNullExpressionValue(strOptString8, "optString(...)");
            return new ProjectConfig(strOptString5, strOptString6, strOptString7, iOptInt, strOptString8, arrayList2, arrayList, jSONObject.optBoolean("runConsole", true), signingConfig2, jSONObject.optBoolean("isProtected", false), jSONObject.optBoolean("isObfuscated", false), jSONObject.optInt("minSdk", 21), jSONObject.optInt("targetSdk", 34));
        } catch (Exception e) {
            e.printStackTrace();
            Intrinsics.checkNotNull(name2);
            return new ProjectConfig(name2, "com.example.game", BuildConfig.VERSION_NAME, 1, "landscape", CollectionsKt.listOf("android.permission.INTERNET"), CollectionsKt.emptyList(), true, null, false, false, 0, 0, GL20.GL_KEEP, null);
        }
    }

    public final void saveConfig(String projectPath, ProjectConfig config) throws JSONException {
        Intrinsics.checkNotNullParameter(projectPath, "projectPath");
        Intrinsics.checkNotNullParameter(config, "config");
        File file = new File(projectPath, "project.json");
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("name", config.getName());
        jSONObject.put("packageName", config.getPackageName());
        jSONObject.put("version", config.getVersion());
        jSONObject.put("versionCode", config.getVersionCode());
        jSONObject.put(ResConfigBase.NAME_orientation, config.getOrientation());
        jSONObject.put("runConsole", config.getRunConsole());
        jSONObject.put("isProtected", config.isProtected());
        jSONObject.put("isObfuscated", config.isObfuscated());
        jSONObject.put("minSdk", config.getMinSdk());
        jSONObject.put("targetSdk", config.getTargetSdk());
        JSONArray jSONArray = new JSONArray();
        Iterator<T> it = config.getRepositories().iterator();
        while (it.hasNext()) {
            jSONArray.put((String) it.next());
        }
        jSONObject.put("repositories", jSONArray);
        JSONArray jSONArray2 = new JSONArray();
        Iterator<T> it2 = config.getPermissions().iterator();
        while (it2.hasNext()) {
            jSONArray2.put((String) it2.next());
        }
        jSONObject.put("permissions", jSONArray2);
        if (config.getSigning() != null) {
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("path", config.getSigning().getKeystorePath());
            jSONObject2.put("pass", config.getSigning().getKeystorePass());
            jSONObject2.put(PathMap.NAME_alias, config.getSigning().getKeyAlias());
            jSONObject2.put("keyPass", config.getSigning().getKeyPass());
            jSONObject.put("signing", jSONObject2);
        }
        String string = jSONObject.toString(4);
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        FilesKt.writeText$default(file, string, null, 2, null);
    }
}
