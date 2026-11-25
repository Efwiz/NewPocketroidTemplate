package org.catrobat.catroid.sensing;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.Log;
import androidx.core.util.Pair;
import ar.com.hjg.pngj.PngjInputException;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ShortArray;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.utils.ImageEditing;
import org.jetbrains.kotlin.cli.common.arguments.PreprocessCommandLineArgumentsKt;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/sensing/CollisionInformation.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class CollisionInformation {
    private static final String TAG = "CollisionInformation";
    private static final String TAG_COLLISION_POLYGON = "CollisionPolygon";
    private static final Map<String, Polygon[]> ramCache = new ConcurrentHashMap();
    public Thread collisionPolygonCalculationThread;
    public volatile Polygon[] collisionPolygons;
    private volatile boolean isCalculationThreadCancelled = true;
    private long lastLoadedTime = 0;
    private Pair<Integer, Integer> leftBubblePos;
    private LookData lookData;
    private Pair<Integer, Integer> rightBubblePos;

    public static void clearCache() {
        ramCache.clear();
    }

    public CollisionInformation(LookData lookData) {
        this.lookData = lookData;
    }

    public Pair<Integer, Integer> getLeftBubblePos() {
        return this.leftBubblePos;
    }

    public Pair<Integer, Integer> getRightBubblePos() {
        return this.rightBubblePos;
    }

    public void calculate() {
        this.isCalculationThreadCancelled = false;
        Thread thread = new Thread(new CollisionPolygonCreationTask(this.lookData));
        this.collisionPolygonCalculationThread = thread;
        thread.start();
    }

    public int getNumberOfVertices() {
        if (this.collisionPolygons == null) {
            return 0;
        }
        int length = 0;
        for (Polygon polygon : this.collisionPolygons) {
            length += polygon.getVertices().length / 2;
        }
        return length;
    }

    public void calculateBubblePositions() {
        calculateBubblePositions(BitmapFactory.decodeFile(this.lookData.getFile().getAbsolutePath()));
    }

    public void calculateBubblePositions(Bitmap bitmap) {
        int[] iArr = new int[bitmap.getWidth() * bitmap.getHeight()];
        bitmap.getPixels(iArr, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        int width = bitmap.getWidth() / 2;
        int height = bitmap.getHeight() / 2;
        for (int i = 0; i < bitmap.getHeight() && this.rightBubblePos == null; i++) {
            for (int width2 = bitmap.getWidth() - 1; width2 > 0; width2--) {
                if (!ImageEditing.isPixelTransparent(iArr, bitmap.getWidth(), width2, i)) {
                    int i2 = width2 - width;
                    int i3 = height - i;
                    if (this.rightBubblePos == null) {
                        this.rightBubblePos = new Pair<>(Integer.valueOf(i2), Integer.valueOf(i3));
                    }
                    this.leftBubblePos = new Pair<>(Integer.valueOf(i2), Integer.valueOf(i3));
                }
            }
        }
    }

    public synchronized void loadCollisionPolygon() {
        long jLastModified = this.lookData.getFile().lastModified();
        if (this.collisionPolygons == null || this.lastLoadedTime != jLastModified) {
            this.isCalculationThreadCancelled = false;
            String absolutePath = this.lookData.getFile().getAbsolutePath();
            String str = absolutePath + PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT + jLastModified;
            Map<String, Polygon[]> map = ramCache;
            if (map.containsKey(str)) {
                this.collisionPolygons = map.get(str);
                this.lastLoadedTime = jLastModified;
                Log.i(TAG, "Successfully loaded collision polygons for " + this.lookData.getName() + " from RAM cache.");
                return;
            }
            Polygon[] collisionPolygonFromPNGMeta = getCollisionPolygonFromPNGMeta(absolutePath);
            if (collisionPolygonFromPNGMeta.length > 0) {
                this.collisionPolygons = collisionPolygonFromPNGMeta;
                this.lastLoadedTime = jLastModified;
                map.put(str, collisionPolygonFromPNGMeta);
                Log.i(TAG, "Successfully loaded " + this.collisionPolygons.length + " polygons from PNG cache.");
                return;
            }
            createCollisionPolygon(absolutePath, jLastModified);
            if (this.isCalculationThreadCancelled) {
                this.collisionPolygons = null;
            } else if (this.collisionPolygons != null) {
                this.lastLoadedTime = jLastModified;
                map.put(str, this.collisionPolygons);
            }
        }
    }

    public synchronized boolean forceRecalculateAndSave() {
        boolean z;
        z = false;
        this.isCalculationThreadCancelled = false;
        createCollisionPolygon(this.lookData.getFile().getAbsolutePath(), this.lookData.getFile().lastModified());
        if (this.collisionPolygons != null && this.collisionPolygons.length > 0) {
            z = true;
        }
        return z;
    }

    private synchronized void createCollisionPolygon(String path) {
        createCollisionPolygon(path, this.lookData.getFile().lastModified());
    }

    private synchronized void createCollisionPolygon(String path, long currentModified) {
        short s;
        try {
            Log.i(TAG, "No Collision information from PNG file, creating new one.");
            if (this.isCalculationThreadCancelled) {
                return;
            }
            String str = path + PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT + currentModified;
            Map<String, Polygon[]> map = ramCache;
            if (map.containsKey(str)) {
                this.collisionPolygons = map.get(str);
                this.lastLoadedTime = currentModified;
                return;
            }
            Bitmap bitmapDecodeFile = BitmapFactory.decodeFile(path);
            if (bitmapDecodeFile == null) {
                return;
            }
            ArrayList<ArrayList<CollisionPolygonVertex>> arrayListCreateBoundingPolygonVertices = createBoundingPolygonVertices(path, this.lookData);
            if (arrayListCreateBoundingPolygonVertices.size() == 0) {
                if (bitmapDecodeFile != null) {
                    this.collisionPolygons = createCollisionPolygonByHitbox(bitmapDecodeFile);
                }
                return;
            }
            Iterator<ArrayList<CollisionPolygonVertex>> it = arrayListCreateBoundingPolygonVertices.iterator();
            int i = 0;
            int size = 0;
            while (it.hasNext()) {
                size += it.next().size();
            }
            Log.d("CollisionDebug", "Look: " + this.lookData.getName() + " - Initial vertex count: " + size + " / Limit: 2048");
            Rect rectFindVisibleBounds = ImageEditing.findVisibleBounds(bitmapDecodeFile);
            if (rectFindVisibleBounds == null) {
                this.collisionPolygons = createCollisionPolygonByHitbox(bitmapDecodeFile);
                if (!this.isCalculationThreadCancelled && this.lookData.isValid()) {
                    writeCollisionVerticesToPNGMeta(this.collisionPolygons, path);
                }
                return;
            }
            float fMax = Math.max(1.5f, Math.min(rectFindVisibleBounds.width(), rectFindVisibleBounds.height()) * 0.05f);
            this.collisionPolygons = new Polygon[0];
            while (true) {
                Log.d("CollisionDebug", "Look: " + this.lookData.getName() + " - Simplification loop. Epsilon: " + fMax + ", Vertices: " + getNumberOfVertices());
                if (this.isCalculationThreadCancelled) {
                    return;
                }
                ArrayList arrayList = new ArrayList();
                int i2 = i;
                while (true) {
                    s = 2;
                    if (i2 >= arrayListCreateBoundingPolygonVertices.size()) {
                        break;
                    }
                    ArrayList<PointF> pointsFromPolygonVertices = getPointsFromPolygonVertices(arrayListCreateBoundingPolygonVertices.get(i2));
                    if (pointsFromPolygonVertices.size() >= 3) {
                        ArrayList<PointF> arrayListSimplifyPolygon = simplifyPolygon(pointsFromPolygonVertices, i, pointsFromPolygonVertices.size() - 1, fMax);
                        if (arrayListSimplifyPolygon.size() >= 2 && Math.hypot(arrayListSimplifyPolygon.get(arrayListSimplifyPolygon.size() - 1).x - arrayListSimplifyPolygon.get(i).x, arrayListSimplifyPolygon.get(arrayListSimplifyPolygon.size() - 1).y - arrayListSimplifyPolygon.get(i).y) < fMax) {
                            arrayListSimplifyPolygon.remove(arrayListSimplifyPolygon.size() - 1);
                        }
                        if (arrayListSimplifyPolygon.size() >= 3) {
                            arrayList.add(createPolygonFromPoints(arrayListSimplifyPolygon));
                        }
                    }
                    i2++;
                    i = 0;
                }
                EarClippingTriangulator earClippingTriangulator = new EarClippingTriangulator();
                Array array = new Array();
                Iterator it2 = arrayList.iterator();
                while (it2.hasNext()) {
                    float[] vertices = ((Polygon) it2.next()).getVertices();
                    if (vertices.length >= 6) {
                        ShortArray shortArrayComputeTriangles = earClippingTriangulator.computeTriangles(vertices);
                        int i3 = 0;
                        while (i3 < shortArrayComputeTriangles.size) {
                            int i4 = shortArrayComputeTriangles.get(i3) * s;
                            int i5 = shortArrayComputeTriangles.get(i3 + 1) * s;
                            int i6 = shortArrayComputeTriangles.get(i3 + 2) * s;
                            array.add(new Polygon(new float[]{vertices[i4], vertices[i4 + 1], vertices[i5], vertices[i5 + 1], vertices[i6], vertices[i6 + 1]}));
                            i3 += 3;
                            earClippingTriangulator = earClippingTriangulator;
                            s = 2;
                        }
                    }
                }
                this.collisionPolygons = (Polygon[]) array.toArray(Polygon.class);
                fMax *= 1.2f;
                if (getNumberOfVertices() <= 2048) {
                    Log.d("CollisionDebug", "Look: " + this.lookData.getName() + " - Final vertex count: " + getNumberOfVertices() + " in " + this.collisionPolygons.length + " polygons.");
                    if ((this.collisionPolygons == null || this.collisionPolygons.length == 0) && bitmapDecodeFile != null) {
                        this.collisionPolygons = createCollisionPolygonByHitbox(bitmapDecodeFile);
                    }
                    if (!this.isCalculationThreadCancelled && this.lookData.isValid()) {
                        if (this.collisionPolygons != null) {
                            this.lastLoadedTime = currentModified;
                            ramCache.put(str, this.collisionPolygons);
                        }
                        try {
                            writeCollisionVerticesToPNGMeta(this.collisionPolygons, path);
                        } catch (Exception e) {
                            Log.e(TAG, "Failed to write collision metadata to PNG on disk: " + e.getMessage());
                        }
                        Log.i(TAG_COLLISION_POLYGON, "Polygon size of look " + this.lookData.getName() + ": " + getNumberOfVertices() + " vertices in " + this.collisionPolygons.length + " convex shapes (triangles).");
                        return;
                    }
                    return;
                }
                i = 0;
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    /* JADX WARN: Code duplicated, block: B:51:0x0142 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:53:? A[LOOP:0: B:20:0x00a8->B:53:?, LOOP_END, SYNTHETIC] */
    public static ArrayList<ArrayList<CollisionPolygonVertex>> createBoundingPolygonVertices(String absolutePath, LookData lookData) {
        boolean z;
        Bitmap bitmapDecodeFile = BitmapFactory.decodeFile(absolutePath);
        if (bitmapDecodeFile == null) {
            Log.e(TAG_COLLISION_POLYGON, "bitmap " + absolutePath + " is null. Cannot create Collision polygon");
            return new ArrayList<>();
        }
        Matrix matrix = new Matrix();
        matrix.preScale(1.0f, -1.0f);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmapDecodeFile, 0, 0, bitmapDecodeFile.getWidth(), bitmapDecodeFile.getHeight(), matrix, true);
        boolean[][] zArrCreateCollisionGrid = createCollisionGrid(bitmapCreateBitmap);
        if (lookData.getCollisionInformation().isCalculationThreadCancelled) {
            return new ArrayList<>();
        }
        ArrayList<CollisionPolygonVertex> arrayListCreateVerticalVertices = createVerticalVertices(zArrCreateCollisionGrid, bitmapCreateBitmap.getWidth(), bitmapCreateBitmap.getHeight());
        ArrayList<CollisionPolygonVertex> arrayListCreateHorizontalVertices = createHorizontalVertices(zArrCreateCollisionGrid, bitmapCreateBitmap.getWidth(), bitmapCreateBitmap.getHeight());
        if (arrayListCreateVerticalVertices.size() == 0 || arrayListCreateHorizontalVertices.size() == 0) {
            return new ArrayList<>();
        }
        if (lookData.getCollisionInformation().isCalculationThreadCancelled) {
            return new ArrayList<>();
        }
        ArrayList<ArrayList<CollisionPolygonVertex>> arrayList = new ArrayList<>();
        arrayList.add(new ArrayList<>());
        arrayList.get(0).add(arrayListCreateVerticalVertices.get(0));
        arrayListCreateVerticalVertices.remove(0);
        int i = 0;
        while (!lookData.getCollisionInformation().isCalculationThreadCancelled) {
            ArrayList<CollisionPolygonVertex> arrayList2 = arrayList.get(i);
            boolean z2 = true;
            CollisionPolygonVertex collisionPolygonVertex = arrayList2.get(arrayList2.size() - 1);
            int i2 = 0;
            while (true) {
                if (i2 >= arrayListCreateHorizontalVertices.size()) {
                    z = false;
                    break;
                }
                if (collisionPolygonVertex.isConnected(arrayListCreateHorizontalVertices.get(i2))) {
                    arrayList2.add(arrayListCreateHorizontalVertices.get(i2));
                    collisionPolygonVertex = arrayListCreateHorizontalVertices.get(i2);
                    arrayListCreateHorizontalVertices.remove(i2);
                    z = true;
                    break;
                }
                i2++;
            }
            int i3 = 0;
            while (true) {
                if (i3 >= arrayListCreateVerticalVertices.size()) {
                    z2 = z;
                    break;
                }
                if (collisionPolygonVertex.isConnected(arrayListCreateVerticalVertices.get(i3))) {
                    arrayList2.add(arrayListCreateVerticalVertices.get(i3));
                    arrayListCreateVerticalVertices.remove(i3);
                    break;
                }
                i3++;
            }
            if (!z2) {
                i++;
                arrayList.add(new ArrayList<>());
                if (!arrayListCreateVerticalVertices.isEmpty()) {
                    arrayList.get(i).add(arrayListCreateVerticalVertices.get(0));
                    arrayListCreateVerticalVertices.remove(0);
                    if (arrayListCreateHorizontalVertices.size() <= 0) {
                    }
                }
            } else if (arrayListCreateHorizontalVertices.size() <= 0) {
            }
            return arrayList;
        }
        return new ArrayList<>();
    }

    public static ArrayList<CollisionPolygonVertex> createHorizontalVertices(boolean[][] grid, int gridWidth, int gridHeight) {
        ArrayList<CollisionPolygonVertex> arrayList = new ArrayList<>();
        for (int i = 0; i < gridHeight; i++) {
            for (int i2 = 0; i2 < gridWidth; i2++) {
                boolean[] zArr = grid[i2];
                if (zArr[i]) {
                    if (i == 0 || !zArr[i - 1]) {
                        boolean z = arrayList.size() > 0 && arrayList.get(arrayList.size() - 1).endX == ((float) i2) && arrayList.get(arrayList.size() - 1).endY == ((float) i);
                        boolean z2 = arrayList.size() > 1 && arrayList.get(arrayList.size() + (-2)).endX == ((float) i2) && arrayList.get(arrayList.size() + (-2)).endY == ((float) i);
                        if (z) {
                            arrayList.get(arrayList.size() - 1).extend(i2 + 1, i);
                        } else if (z2) {
                            arrayList.get(arrayList.size() - 2).extend(i2 + 1, arrayList.get(arrayList.size() - 2).endY);
                        } else {
                            float f = i;
                            arrayList.add(new CollisionPolygonVertex(i2, f, i2 + 1, f));
                        }
                    }
                    if (i == gridHeight - 1 || !grid[i2][i + 1]) {
                        boolean z3 = arrayList.size() > 0 && arrayList.get(arrayList.size() - 1).endX == ((float) i2) && arrayList.get(arrayList.size() - 1).endY == ((float) (i + 1));
                        boolean z4 = arrayList.size() > 1 && arrayList.get(arrayList.size() + (-2)).endX == ((float) i2) && arrayList.get(arrayList.size() + (-2)).endY == ((float) (i + 1));
                        if (z3) {
                            arrayList.get(arrayList.size() - 1).extend(i2 + 1, i + 1);
                        } else if (z4) {
                            arrayList.get(arrayList.size() - 2).extend(i2 + 1, arrayList.get(arrayList.size() - 2).endY);
                        } else {
                            float f2 = i + 1;
                            arrayList.add(new CollisionPolygonVertex(i2, f2, i2 + 1, f2));
                        }
                    }
                }
            }
        }
        return arrayList;
    }

    public static ArrayList<CollisionPolygonVertex> createVerticalVertices(boolean[][] grid, int gridWidth, int gridHeight) {
        ArrayList<CollisionPolygonVertex> arrayList = new ArrayList<>();
        for (int i = 0; i < gridWidth; i++) {
            for (int i2 = 0; i2 < gridHeight; i2++) {
                if (grid[i][i2]) {
                    if (i == 0 || !grid[i - 1][i2]) {
                        boolean z = arrayList.size() > 0 && arrayList.get(arrayList.size() - 1).endX == ((float) i) && arrayList.get(arrayList.size() - 1).endY == ((float) i2);
                        boolean z2 = arrayList.size() > 1 && arrayList.get(arrayList.size() + (-2)).endX == ((float) i) && arrayList.get(arrayList.size() + (-2)).endY == ((float) i2);
                        if (z) {
                            arrayList.get(arrayList.size() - 1).extend(arrayList.get(arrayList.size() - 1).endX, i2 + 1);
                        } else if (z2) {
                            arrayList.get(arrayList.size() - 2).extend(arrayList.get(arrayList.size() - 2).endX, i2 + 1);
                        } else {
                            float f = i;
                            arrayList.add(new CollisionPolygonVertex(f, i2, f, i2 + 1));
                        }
                    }
                    if (i == gridWidth - 1 || !grid[i + 1][i2]) {
                        boolean z3 = arrayList.size() > 0 && arrayList.get(arrayList.size() - 1).endX == ((float) (i + 1)) && arrayList.get(arrayList.size() - 1).endY == ((float) i2);
                        boolean z4 = arrayList.size() > 1 && arrayList.get(arrayList.size() + (-2)).endX == ((float) (i + 1)) && arrayList.get(arrayList.size() + (-2)).endY == ((float) i2);
                        if (z3) {
                            arrayList.get(arrayList.size() - 1).extend(arrayList.get(arrayList.size() - 1).endX, i2 + 1);
                        } else if (z4) {
                            arrayList.get(arrayList.size() - 2).extend(arrayList.get(arrayList.size() - 2).endX, i2 + 1);
                        } else {
                            float f2 = i + 1;
                            arrayList.add(new CollisionPolygonVertex(f2, i2, f2, i2 + 1));
                        }
                    }
                }
            }
        }
        return arrayList;
    }

    private static float pointToLineDistance(PointF lineStart, PointF lineEnd, PointF point) {
        return Math.abs(((point.x - lineStart.x) * (lineEnd.y - lineStart.y)) - ((point.y - lineStart.y) * (lineEnd.x - lineStart.x))) / ((float) Math.sqrt(((lineEnd.x - lineStart.x) * (lineEnd.x - lineStart.x)) + ((lineEnd.y - lineStart.y) * (lineEnd.y - lineStart.y))));
    }

    public static ArrayList<PointF> simplifyPolygon(ArrayList<PointF> points, int start, int end, float epsilon) {
        float f = 0.0f;
        int i = start;
        for (int i2 = start + 1; i2 < end; i2++) {
            float fPointToLineDistance = pointToLineDistance(points.get(start), points.get(end), points.get(i2));
            if (fPointToLineDistance > f) {
                i = i2;
                f = fPointToLineDistance;
            }
        }
        ArrayList<PointF> arrayList = new ArrayList<>();
        if (f > epsilon) {
            ArrayList<PointF> arrayListSimplifyPolygon = simplifyPolygon(points, start, i, epsilon);
            ArrayList<PointF> arrayListSimplifyPolygon2 = simplifyPolygon(points, i, end, epsilon);
            for (int i3 = 0; i3 < arrayListSimplifyPolygon.size() - 1; i3++) {
                arrayList.add(arrayListSimplifyPolygon.get(i3));
            }
            for (int i4 = 0; i4 < arrayListSimplifyPolygon2.size(); i4++) {
                arrayList.add(arrayListSimplifyPolygon2.get(i4));
            }
        } else {
            arrayList.add(points.get(start));
            arrayList.add(points.get(end));
        }
        return arrayList;
    }

    public static ArrayList<PointF> getPointsFromPolygonVertices(ArrayList<CollisionPolygonVertex> polygon) {
        ArrayList<PointF> arrayList = new ArrayList<>();
        Iterator<CollisionPolygonVertex> it = polygon.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getStartPoint());
        }
        return arrayList;
    }

    public static Polygon createPolygonFromPoints(ArrayList<PointF> points) {
        float[] fArr = new float[points.size() * 2];
        for (int i = 0; i < points.size(); i++) {
            int i2 = i * 2;
            fArr[i2] = points.get(i).x;
            fArr[i2 + 1] = points.get(i).y;
        }
        return new Polygon(fArr);
    }

    public static boolean[][] createCollisionGrid(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] iArr = new int[width * height];
        bitmap.getPixels(iArr, 0, width, 0, 0, width, height);
        boolean[][] zArr = (boolean[][]) java.lang.reflect.Array.newInstance((Class<?>) Boolean.TYPE, width, height);
        for (int i = 0; i < width; i++) {
            for (int i2 = 0; i2 < height; i2++) {
                if (!ImageEditing.isPixelTransparent(iArr, width, i, i2)) {
                    zArr[i][i2] = true;
                }
            }
        }
        return zArr;
    }

    public static void writeCollisionVerticesToPNGMeta(Polygon[] collisionPolygon, String absolutePath) {
        StringBuilder sb = new StringBuilder();
        int length = collisionPolygon.length;
        int i = 0;
        while (i < length) {
            Polygon polygon = collisionPolygon[i];
            for (int i2 = 0; i2 < polygon.getVertices().length; i2++) {
                sb.append(polygon.getVertices()[i2]).append(';');
            }
            StringBuilder sb2 = new StringBuilder(sb.substring(0, sb.length() - 1));
            sb2.append('|');
            i++;
            sb = sb2;
        }
        if (sb.length() > 0) {
            ImageEditing.writeMetaDataStringToPNG(absolutePath, Constants.COLLISION_PNG_META_TAG_KEY, new StringBuilder(sb.substring(0, sb.length() - 1)).toString());
            ImageEditing.writeMetaDataStringToPNG(absolutePath, Constants.COLLISION_PNG_META_VERSION_KEY, Constants.LATEST_COLLISION_VERSION);
        }
    }

    public static Polygon[] getCollisionPolygonFromPNGMeta(String absolutePath) {
        try {
            if (!Constants.LATEST_COLLISION_VERSION.equals(ImageEditing.readMetaDataStringFromPNG(absolutePath, Constants.COLLISION_PNG_META_VERSION_KEY))) {
                Log.i(TAG, "Old or invalid collision cache version detected. Forcing regeneration.");
                return new Polygon[0];
            }
            String metaDataStringFromPNG = ImageEditing.readMetaDataStringFromPNG(absolutePath, Constants.COLLISION_PNG_META_TAG_KEY);
            if (!checkMetaDataString(metaDataStringFromPNG)) {
                return new Polygon[0];
            }
            String[] strArrSplit = metaDataStringFromPNG.split("\\|");
            Polygon[] polygonArr = new Polygon[strArrSplit.length];
            for (int i = 0; i < strArrSplit.length; i++) {
                String[] strArrSplit2 = strArrSplit[i].split(";");
                float[] fArr = new float[strArrSplit2.length];
                for (int i2 = 0; i2 < strArrSplit2.length; i2++) {
                    fArr[i2] = Float.valueOf(strArrSplit2[i2]).floatValue();
                }
                polygonArr[i] = new Polygon(fArr);
            }
            Log.i(TAG, "Loaded CollisionPolygon from " + absolutePath + " successfully!");
            return polygonArr;
        } catch (PngjInputException unused) {
            Log.e(TAG, "Error reading metadata from png!");
            return new Polygon[0];
        }
    }

    public static boolean checkMetaDataString(String metadata) {
        if (metadata != null && !metadata.equals("")) {
            Matcher matcher = Pattern.compile(Constants.COLLISION_POLYGON_METADATA_PATTERN).matcher(metadata);
            if (matcher.find() && matcher.group().equals(metadata)) {
                return true;
            }
            Log.e(TAG_COLLISION_POLYGON, "Invalid Metadata, creating new Polygon");
        }
        return false;
    }

    public static Polygon[] createCollisionPolygonByHitbox(Bitmap bitmap) {
        float width = bitmap.getWidth();
        float height = bitmap.getHeight();
        new Polygon(new float[]{0.0f, 0.0f, width, 0.0f, width, height, 0.0f, height});
        return new Polygon[]{new Polygon(new float[]{0.0f, 0.0f, width, 0.0f, width, height, 0.0f, height})};
    }

    public void printDebugCollisionPolygons() {
        int i = 0;
        for (Polygon polygon : this.collisionPolygons) {
            i++;
            Log.i(TAG, "Collision Polygon " + i + " :\n" + Arrays.toString(polygon.getTransformedVertices()));
        }
    }
}
