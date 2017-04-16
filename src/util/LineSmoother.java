package util;


import javafx.scene.shape.Line;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class LineSmoother {

    public static List<Line> smoothLine(List<Line> lineSegments) {
        if (lineSegments.size() < 4) {
            return lineSegments;
        }
        List<Line> smoothedLine = new ArrayList();
        List<Point> points = getPoints(lineSegments);
        smoothedLine.add(lineSegments.get(0));

        Point newPoint = (Point) points.get(1);
        for (int i = 2; i < points.size() - 2; i++) {
            Point lastPoint = newPoint;
            newPoint = smoothPoint(points.subList(i - 2, i + 3));
            smoothedLine.add(new Line(lastPoint.getX(), lastPoint.getY(), newPoint.getX(), newPoint.getY()));
        }
        Line lastSegment = (Line) lineSegments.get(lineSegments.size() - 1);
        Point lastPoint = new Point((int) lastSegment.getStartX(), (int) lastSegment.getStartY());
        smoothedLine.add(new Line(newPoint.getX(), newPoint.getY(), lastPoint.getX(), lastPoint.getY()));
        smoothedLine.add(lastSegment);

        return smoothedLine;
    }

    public static List<Point> getPoints(List<Line> lineSegments) {
        List<Point> points = new ArrayList();
        for (Line segment : lineSegments) {
            points.add(new Point((int) segment.getStartX(), (int) segment.getStartY()));
        }
        points.add(new Point((int) ((Line) lineSegments.get(lineSegments.size() - 1)).getEndX(), (int) ((Line) lineSegments.get(lineSegments.size() - 1)).getEndY()));

        return points;
    }

    public static Point smoothPoint(List<Point> points) {
        int avgX = 0;
        int avgY = 0;
        for (Point point : points) {
            avgX = (int) (avgX + point.getX());
            avgY = (int) (avgY + point.getY());
        }
        avgX /= points.size();
        avgY /= points.size();
        Point newPoint = new Point(avgX, avgY);
        Point oldPoint = (Point) points.get(points.size() / 2);
        int newX = (int)(newPoint.getX() + oldPoint.getX()) / 2;
        int newY = (int)(newPoint.getY() + oldPoint.getY()) / 2;

        return new Point(newX, newY);
    }
}

