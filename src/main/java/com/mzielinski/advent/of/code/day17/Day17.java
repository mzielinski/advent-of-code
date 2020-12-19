package com.mzielinski.advent.of.code.day17;

import com.mzielinski.advent.of.code.day17.geometry.NeighboursGenerator;
import com.mzielinski.advent.of.code.day17.geometry.Point;
import com.mzielinski.advent.of.code.day17.geometry.PointWithNeighbours;
import com.mzielinski.advent.of.code.day17.geometry.d3.Point3D;
import com.mzielinski.advent.of.code.day17.geometry.d3.Point3DNeighboursGenerator;
import com.mzielinski.advent.of.code.day17.geometry.d4.Point4D;
import com.mzielinski.advent.of.code.day17.geometry.d4.Point4DNeighboursGenerator;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toSet;

public class Day17<T extends Point<T>> {

    // part 1
    public static CubeReader<Point3D> point3DCubeReader = new CubeReader<>((z, y, x, w) -> new Point3D(z, y, x));
    public static NeighboursGenerator<Point3D> point3DNeighboursGenerator = new Point3DNeighboursGenerator();

    // part 2
    public static CubeReader<Point4D> point4DCubeReader = new CubeReader<>(Point4D::new);
    public static NeighboursGenerator<Point4D> point4DNeighboursGenerator = new Point4DNeighboursGenerator();

    private final List<T> combinations;
    private final CubeReader<T> cubeReader;

    public Day17(NeighboursGenerator<T> neighboursGenerator, CubeReader<T> cubeReader) {
        this.combinations = neighboursGenerator.combinations();
        this.cubeReader = cubeReader;
    }

    public long countActivePieces(String filePath, int cycle) {
        Grid<T> grid = Grid.initializeCube(filePath, cubeReader);
        return preformRound(grid, cycle).calculateActive();
    }

    public Grid<T> preformRound(Grid<T> cube, int cycle) {
        Grid<T> round = performSingleRound(cube);
        return cycle > 1 ? preformRound(round, cycle - 1) : round;
    }

    public Grid<T> performSingleRound(Grid<T> grid) {
        Set<Point<T>> points = grid.activePoints();

        // all active which have at 2 or 3 active neighbours
        Set<Point<T>> fromActiveToActive = findAllActivePoints(
                points,
                points,
                activeNeighbours -> activeNeighbours.size() == 2 || activeNeighbours.size() == 3);

        // all neighbours for active points which have exactly 3 active neighbours
        Set<Point<T>> fromInactiveToActive = points.stream()
                .map(point -> calculateInactiveNeighbours(point, points))
                .map(neighbour -> findAllActivePoints(
                        neighbour.neighbours(),
                        points,
                        activeNeighbours -> activeNeighbours.size() == 3))
                .flatMap(Collection::stream)
                .collect(toSet());

        fromActiveToActive.addAll(fromInactiveToActive);

        return new Grid<>(fromActiveToActive);
    }

    private Set<Point<T>> findAllActivePoints(Set<Point<T>> points, Set<Point<T>> activePoints, Predicate<Set<Point<T>>> predicate) {
        return points.stream()
                .map(point -> calculateActiveNeighbours(point, activePoints))
                .filter(activeNeighbour -> predicate.test(activeNeighbour.neighbours()))
                .map(PointWithNeighbours::point)
                .collect(toSet());
    }

    private PointWithNeighbours<T> calculateActiveNeighbours(Point<T> point, Set<Point<T>> points) {
        Set<Point<T>> activeNeighbours = combinations.stream()
                .map(point::next)
                .collect(toSet());
        activeNeighbours.remove(point);
        activeNeighbours.retainAll(points);
        return new PointWithNeighbours<>(point, activeNeighbours);
    }

    private PointWithNeighbours<T> calculateInactiveNeighbours(Point<T> point, Set<Point<T>> activePoints) {
        Set<Point<T>> neighbours = combinations.stream()
                .map(point::next)
                .filter(neighbour -> !activePoints.contains(neighbour))
                .filter(neighbour -> !point.equals(neighbour))
                .collect(toSet());
        return new PointWithNeighbours<>(point, neighbours);
    }
}