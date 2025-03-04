package nullengine.math;

import org.joml.Vector3f;
import org.joml.Vector3fc;

public final class Math2 {
    private Math2() {
    }

    public static int ceilPowerOfTwo(int value) {
        if (value <= 0)
            throw new IllegalArgumentException();

        int result = 1;
        while (value > result) {
            result <<= 1;
        }
        return result;
    }

    public static int getUsingBits(int value) {
        int bits = 0;
        while (value != 0) {
            value >>>= 1;
            bits++;
        }
        return bits;
    }

    public static int ceil(int value, int interval) {
        if (value == 0)
            return 0;
        if (interval == 0)
            return 0;
        if (value < 0)
            interval *= -1;
        int mod = value % interval;
        return mod == 0 ? value : value + interval - mod;
    }

    public static float loop(float value, float interval) {
        if (value < 0) {
            value = loop(-value, interval);
            return value == 0 ? 0 : interval - value;
        } else {
            int i = (int) (value / interval);
            return i == 0 ? value : value - i * interval;
        }
    }

    public static float clamp(float val, float min, float max) {
        return Math.min(max, Math.max(val, min));
    }

    public static int clamp(int val, int min, int max) {
        return Math.min(max, Math.max(val, min));
    }

    public static long clamp(long val, long min, long max) {
        return Math.min(max, Math.max(val, min));
    }

    public static double clamp(double val, double min, double max) {
        return Math.min(max, Math.max(val, min));
    }

    /**
     * Calculate normal of a triangle by vertices.
     *
     * @param vertices
     * @return
     */
    public static Vector3fc calcNormalByVertices(float[] vertices) {
        assert vertices.length == 9;
        float nx, ny, nz = 0;
        var u = new Vector3f(vertices[3], vertices[4], vertices[5]).sub(vertices[0], vertices[1], vertices[2]);
        var v = new Vector3f(vertices[6], vertices[7], vertices[8]).sub(vertices[0], vertices[1], vertices[2]);
        nx = u.y * v.z - u.z * v.y;
        ny = u.z * v.x - u.x * v.z;
        nz = u.x * v.y - u.y * v.x;
        return new Vector3f(nx, ny, nz).normalize();
    }

    /**
     * Calculate normal of a triangle by vertices.
     *
     * @return
     */
    public static Vector3fc calcNormalByVertices(Vector3fc v1, Vector3fc v2, Vector3fc v3) {
        var u = new Vector3f();
        var v = new Vector3f();
        v2.sub(v1, u);
        v3.sub(v1, v);
        float nx = u.y * v.z - u.z * v.y;
        float ny = u.z * v.x - u.x * v.z;
        float nz = u.x * v.y - u.y * v.x;
        return new Vector3f(nx, ny, nz).normalize();
    }
}
