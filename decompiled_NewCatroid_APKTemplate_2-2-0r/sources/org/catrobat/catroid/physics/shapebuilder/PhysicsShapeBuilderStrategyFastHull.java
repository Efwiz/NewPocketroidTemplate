package org.catrobat.catroid.physics.shapebuilder;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import java.util.ArrayList;
import java.util.Stack;
import org.catrobat.catroid.physics.PhysicsWorldConverter;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/physics/shapebuilder/PhysicsShapeBuilderStrategyFastHull.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class PhysicsShapeBuilderStrategyFastHull implements PhysicsShapeBuilderStrategy {
    private static final int MINIMUM_PIXEL_ALPHA_VALUE = 1;

    @Override // org.catrobat.catroid.physics.shapebuilder.PhysicsShapeBuilderStrategy
    public Shape[] build(Pixmap pixmap, float scale) {
        if (pixmap == null) {
            return null;
        }
        int width = pixmap.getWidth();
        int height = pixmap.getHeight();
        Stack<Vector2> stack = new Stack<>();
        Vector2 vector2 = new Vector2(width, height);
        for (int i = 0; i < height; i++) {
            int i2 = 0;
            while (true) {
                float f = i2;
                if (f >= vector2.x) {
                    break;
                }
                if ((pixmap.getPixel(i2, i) & 255) >= 1) {
                    vector2 = new Vector2(f, i);
                    addPoint(stack, vector2);
                    break;
                }
                i2++;
            }
        }
        if (stack.isEmpty()) {
            return null;
        }
        for (int i3 = (int) vector2.x; i3 < width; i3++) {
            int i4 = height - 1;
            while (true) {
                float f2 = i4;
                if (f2 <= vector2.y) {
                    break;
                }
                if ((pixmap.getPixel(i3, i4) & 255) >= 1) {
                    float f3 = i3;
                    vector2 = new Vector2(f3, f2);
                    addPoint(stack, new Vector2(f3, f2 + 1.0f));
                    break;
                }
                i4--;
            }
        }
        Vector2 vector2FirstElement = stack.firstElement();
        int i5 = (int) vector2.y;
        while (true) {
            float f4 = i5;
            if (f4 < vector2FirstElement.y) {
                break;
            }
            int i6 = width - 1;
            while (true) {
                float f5 = i6;
                if (f5 <= vector2.x) {
                    break;
                }
                if ((pixmap.getPixel(i6, i5) & 255) >= 1) {
                    vector2 = new Vector2(f5, f4);
                    addPoint(stack, new Vector2(f5 + 1.0f, f4 + 1.0f));
                    break;
                }
                i6--;
            }
            i5--;
        }
        int i7 = (int) vector2.x;
        while (true) {
            float f6 = i7;
            if (f6 <= vector2FirstElement.x) {
                break;
            }
            int i8 = (int) vector2FirstElement.y;
            while (true) {
                float f7 = i8;
                if (f7 >= vector2.y) {
                    break;
                }
                if ((pixmap.getPixel(i7, i8) & 255) >= 1) {
                    vector2 = new Vector2(f6, f7);
                    addPoint(stack, new Vector2(f6 + 1.0f, f7));
                    break;
                }
                i8++;
            }
            i7--;
        }
        if (stack.size() > 2) {
            removeNonConvexPoints(stack, vector2FirstElement);
        }
        return devideShape((Vector2[]) stack.toArray(new Vector2[stack.size()]), width, height);
    }

    private void addPoint(Stack<Vector2> convexHull, Vector2 point) {
        removeNonConvexPoints(convexHull, point);
        convexHull.add(point);
    }

    private void removeNonConvexPoints(Stack<Vector2> convexHull, Vector2 newTop) {
        while (convexHull.size() > 1) {
            Vector2 vector2Peek = convexHull.peek();
            Vector2 vector2 = convexHull.get(convexHull.size() - 2);
            if (leftTurn(vector2, vector2Peek, newTop)) {
                return;
            }
            if (vector2Peek.y > newTop.y && vector2Peek.y > vector2.y) {
                return;
            } else {
                convexHull.pop();
            }
        }
    }

    private boolean leftTurn(Vector2 a, Vector2 b, Vector2 c) {
        return ((b.x - a.x) * (c.y - a.y)) - ((b.y - a.y) * (c.x - a.x)) < 0.0f;
    }

    private Shape[] devideShape(Vector2[] convexpoints, int width, int height) {
        for (int i = 0; i < convexpoints.length; i++) {
            Vector2 vector2 = convexpoints[i];
            vector2.x -= width / 2.0f;
            vector2.y = (height / 2.0f) - vector2.y;
            convexpoints[i] = PhysicsWorldConverter.convertCatroidToBox2dVector(vector2);
        }
        if (convexpoints.length < 9) {
            PolygonShape polygonShape = new PolygonShape();
            polygonShape.set(convexpoints);
            return new Shape[]{polygonShape};
        }
        ArrayList arrayList = new ArrayList((convexpoints.length / 6) + 1);
        ArrayList arrayList2 = new ArrayList(8);
        Vector2 vector3 = convexpoints[0];
        int i2 = 1;
        while (i2 < convexpoints.length - 1) {
            int i3 = i2 + 7;
            int length = convexpoints.length - i2;
            if (length > 7 && length < 9) {
                i3 = i2 + 4;
            }
            arrayList2.add(vector3);
            while (i2 < i3 && i2 < convexpoints.length) {
                arrayList2.add(convexpoints[i2]);
                i2++;
            }
            PolygonShape polygonShape2 = new PolygonShape();
            polygonShape2.set((Vector2[]) arrayList2.toArray(new Vector2[arrayList2.size()]));
            arrayList.add(polygonShape2);
            arrayList2.clear();
            i2--;
        }
        return (Shape[]) arrayList.toArray(new Shape[arrayList.size()]);
    }
}
