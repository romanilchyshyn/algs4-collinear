/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {

    private ArrayList<LineSegment> segments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        this.segments = new ArrayList<>();

        if (points == null) {
            throw new IllegalArgumentException();
        }

        // copy with check
        for (int i = 0; i < points.length; i++) {
            Point p = points[i];
            if (p == null) {
                throw new IllegalArgumentException();
            }

            Point[] sortedPoints = new Point[points.length];
            for (int j = 0; j < sortedPoints.length; j++) {
                Point q = points[j];

                if ((q == null) || (p.compareTo(q) == 0 && i != j)) {
                    throw new IllegalArgumentException();
                }

                sortedPoints[j] = q;
            }

            // sort
            Arrays.sort(sortedPoints, p.slopeOrder());

            System.out.print("--- ");
            System.out.println(p);

            // clusterize
            double activeSlope = Double.NaN;
            Point smallestActiveSlopePoint = null;
            Point largestActiveSlopePoint = null;
            int numberOfActiveSlopePoints = 0;
            for (int k = 0; k < sortedPoints.length; k++) {
                Point curr = sortedPoints[k];
                double slope = p.slopeTo(curr);

                System.out.println(curr);

                if (activeSlope != slope || k == sortedPoints.length - 1) {
                    if (4 <= numberOfActiveSlopePoints) {
                        segments.add(new LineSegment(smallestActiveSlopePoint, largestActiveSlopePoint));
                    }

                    activeSlope = slope;
                    numberOfActiveSlopePoints = 2;
                    smallestActiveSlopePoint = FastCollinearPoints.min(p, curr);
                    largestActiveSlopePoint = FastCollinearPoints.max(p, curr);
                } else {
                    numberOfActiveSlopePoints++;
                    smallestActiveSlopePoint = FastCollinearPoints.min(smallestActiveSlopePoint, curr);
                    largestActiveSlopePoint = FastCollinearPoints.max(largestActiveSlopePoint, curr);
                }

            }
        }
    }

    private static <T extends Comparable<T>> T max(T a, T b) {
        return a.compareTo(b) >= 0 ? a : b;
    }

    private static <T extends Comparable<T>> T min(T a, T b) {
        return a.compareTo(b) < 0 ? a : b;
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[0]);
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
