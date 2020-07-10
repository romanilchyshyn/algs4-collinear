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

public class BruteCollinearPoints {

    private ArrayList<LineSegment> segments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        this.segments = new ArrayList<>();

        if (points == null) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < points.length; i++) {
            Point p = points[i];
            if (p == null) {
                throw new IllegalArgumentException();
            }

            for (int j = i + 1; j < points.length; j++) {
                Point q = points[j];
                if (q == null || q.compareTo(p) == 0) {
                    throw new IllegalArgumentException();
                }

                for (int m = j + 1; m < points.length; m++) {
                    Point r = points[m];
                    if (r == null || r.compareTo(q) == 0) {
                        throw new IllegalArgumentException();
                    }

                    for (int n = m + 1; n < points.length; n++) {
                        Point s = points[n];
                        if (s == null || s.compareTo(r) == 0) {
                            throw new IllegalArgumentException();
                        }

                        double pqSlope = p.slopeTo(q);
                        double prSlope = p.slopeTo(r);
                        double psSlope = p.slopeTo(s);

                        if (pqSlope == prSlope && prSlope == psSlope) {
                            Point[] pqrs = {p, q, r, s};
                            Arrays.sort(pqrs);

                            segments.add(new LineSegment(pqrs[0], pqrs[3]));
                        }
                    }
                }
            }
        }
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
