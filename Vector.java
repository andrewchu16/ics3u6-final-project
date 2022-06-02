/**
 * This class represents a 2-dimensional vector with double precision.
 */
public class Vector {
    public static final Vector VECTOR_ZERO = new Vector(0, 0);

    private double x;
    private double y;

    /**
     * This constructs a new Vector object at (0, 0).
     */
    public Vector() {
        this.x = 0.0;
        this.y = 0.0;
    }

    /**
     * This constructs a new Vector object at the specified coordinate.
     * @param x The x component of the vector.
     * @param y The y component of the vector.
     */
    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * This method adds the x and y parts of a vector to the vector. If the
     * first vector is at (a, b) and the second vector is at (c, d), This method
     * will set the first vector to (a+c, b+d).
     * @param other The vector to add.
     */
    public void add(Vector other) {
        this.x += other.getX();
        this.y += other.getY();
    }

    /**
     * This method subtracts the x and y parts of a vector to the vector. If the
     * first vector is at (a, b) and the second vector is at (c, d), This method
     * will set the first vector to (a-c, b-d).
     * @param other The vector to add.
     */
    public void sub(Vector other) {
        this.x -= other.getX();
        this.y -= other.getY();
    }

    /**
     * This method calculates the length of the vector. It will always return a
     * positive value for all valid vectors.
     * @return The length of the vector.
     */
    public double getLength() {
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }
    
    /**
     * This method gets the dot product of two vectors.
     * @param other The other vector.
     * @return The dot product of the vectors, a scalar.
     */
    public double dot(Vector other) {
        return this.x * other.getX() + this.y * other.getY();
    }
    
    /**
     * This method scales the length of the vector to 1.
     */
    public void normalize() {
        this.scale(1 / this.getLength());
    }

    /**
     * This method scales the length of the vector.
     * @param scalar The length to scale the vector by.
     */
    public void scale(double scalar) {
        this.x *= scalar;
        this.y *= scalar;
    }

    /**
     * This method rotates the vector in user coordinates.
     * @param angleDegrees The degree to rotate the vector in user coordinates.
     */
    public void rotate(double angleDegrees) {
        double angleRadians = Math.toRadians(angleDegrees);

        double cosAngle = Math.cos(angleRadians);
        double sinAngle = Math.sin(angleRadians);

        double x2 = cosAngle * this.x - sinAngle * this.y;
        double y2 = sinAngle * this.x + cosAngle * this.y;

        this.x = x2;
        this.y = y2;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    /**
     * This method sets the vector to the specified length.
     * @param length The new length of the vector.
     */
    public void setLength(double length) {
        this.scale(length / this.getLength());
    }

    /**
     * This method returns a copy of the vector.
     * @return The copied vector.
     */
    @Override
    public Vector clone() {
        return new Vector(this.getX(), this.getY());
    }
    /**
     * This method returns a string representation of the vector in the form, Vec(x, y).
     */
    @Override
    public String toString() {
        String info = "Vec(" + this.getX() + ", " + this.getY() + ")";
        return info;
    }
}
