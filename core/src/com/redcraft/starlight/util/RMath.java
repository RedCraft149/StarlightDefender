package com.redcraft.starlight.util;

import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class RMath {


    private static final Random random;
    static {
        random = new Random(0);
    }

    public static float PI = (float) Math.PI;
    public static float EPSILON = 0.000001f;

    public static float random() {
        return random.nextFloat();
    }
    public static float random(float start, float end) {
        return random()*(end-start)+start;
    }
    public static float random(float precision) {
        return floor(random()/precision)*precision;
    }

    //Modular Arithmetic
    /**
     * Modifies vector x to be the closest vector to p equivalent to the original vector {@code x mod d}.
     * @param x Vector to be modified.
     * @param p Vector to calculate distance to.
     * @param d Dimensions of the modular space.
     */
    public static void nearestModularVector(Vector2 x, Vector2 p, Vector2 d) {
        //noinspection SuspiciousNameCombination
        x.set(nearestModularValue(x.x,p.x,d.x),nearestModularValue(x.y,p.y,d.y));
    }

    /**
     * @param a Start of the direction vector.
     * @param b End of the direction vector
     * @param d Dimensions of the modular space.
     * @return A vector pointing from a to b mod d, so that the returned vector has the smallest length possible.
     */
    public static Vector2 nearestDirectionVector(Vector2 a, Vector2 b, Vector2 d) {
        Vector2 mb = new Vector2(b);
        nearestModularVector(mb,a,d);
        return mb.sub(a);
    }

    /**
     * 1-Dimensional version of {@link RMath#nearestModularVector(Vector2, Vector2, Vector2)}.
     */
    public static float nearestModularValue(float x, float p, float d) {
        x = mod(x,d);
        float s = x-p;
        float greatestDistance = d / 2f;
        if(abs(s)<=greatestDistance) return x;
        if(s<0) return x+d;
        else return x-d;
    }
    public static float modularDistance(float a, float b, float d) {
        a = nearestModularValue(a,b,d);
        return abs(a-b);
    }
    public static float modularDistance(Vector2 a, Vector2 b, Vector2 d) {
        float dx = modularDistance(a.x,b.x,d.x);
        float dy = modularDistance(a.y,b.y,d.y);
        return (float)Math.sqrt(dx*dx+dy*dy);
    }

    /**
     * Returns the sign of the number that, when added to x, has the smallest absolute value to equal dst.
     * @param x Current value
     * @param dst Destination value
     * @param b base
     * @return sign(a) : minimize(|a|) and x+a=dst mod b
     */
    public static int nearestModularDirection(float x, float dst, float b) {
        float b2 = b * 0.5f;
        float da = (dst-x + b2) % b - b2;
        if(da<0) return -1;
        if(da>0) return  1;
        return 0;
    }

    /**
     * @return {@code x mod b}, guaranteed to be positive for all {@code b > 0}.
     */
    public static float mod(float a, float b) {
        if(abs(b)<EPSILON) throw new ArithmeticException("Invalid argument: base cannot be 0.");
        if(a<0) a += -floor(a/b)*b;
        return a % b;
    }

    /**
     * See {@link RMath#mod(float, float)}.
     */
    public static int mod(int a, int b) {
        if(b==0) throw new ArithmeticException("Invalid argument: base cannot be 0.");
        while (a<0) a += b;
        return a % b;
    }

    public static void mod(Vector2 x, Vector2 b) {
        x.set(mod(x.x,b.x),mod(x.y,b.y));
    }

    public static Vector2 modLerp(Vector2 a, Vector2 b, float progress, Vector2 dimensions, Vector2 state) {
        state.set(modLerp(a.x,b.x,progress,dimensions.x),modLerp(a.y,b.y,progress,dimensions.y));
        return state;
    }

    //ceil, floor, abs
    public static int ceil(float a) {
        return (int) Math.ceil(a);
    }
    public static int floor(float a) {
        return (int) Math.floor(a);
    }
    public static float abs(float a) {
        return Math.abs(a);
    }

    //matrix combination

    /**
     * Combines Matrix A and B so that (Vector2 V) * A * B = (Vector2 V) * combineTransformScaleMatrix(A,B).
     * Note that A's rotation component will be ignored.
     * @param A Matrix to be edited.
     * @param B Additional transform.
     */
    public static void combineTransformScaleMatrix(Matrix3 A, Matrix3 B) {
        Vector2 trn = new Vector2();
        A.getTranslation(trn);

        Vector2 scl = new Vector2();
        A.getScale(scl);

        A.set(B).trn(trn).scl(scl);
    }


    public static int round(float a) {
        return Math.round(a);
    }
    public static float round(float a, float precision) {
        return round(a/precision)*precision;
    }
    public static float sin(float a) {
        return (float) Math.sin(a);
    }
    public static float cos(float a) {
        return (float) Math.cos(a);
    }

    /**
     * <p>Rounds {@code a} to the closest number b = 2\u03C0\u00D7precision\u00D7n; n \u2208 \u2115.
     */
    public static float roundRadians(float a, float precision) {
        float factor = 2f * PI * precision;
        int n = round(a / factor);
        return n*factor;
    }

    /**
     * Linearly interpolates between a and b on the parameter t within the modular space d.
     * @param a the starting value
     * @param b the ending value
     * @param t the amount to linearly interpolate
     * @param d The modular space. a and b have to be within [0,d).
     * @return The interpolated value on the shortest path from a to b in the modular space at the parameter t.
     */
    public static float modLerp(float a, float b, float t, float d) {
        float delta = b-a;
        if(abs(delta) > d*0.5f) {
            if(delta>0) delta -= d;
            else delta += d;
        }
        float result = a + delta*t;
        result = (result % d + d) % d;
        return result;
    }
}
