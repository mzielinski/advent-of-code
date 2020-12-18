package com.mzielinski.advent.of.code.day17;

import com.mzielinski.advent.of.code.day17.Point3D.Point3DWithNeighbours;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toSet;

public class Day17 {

    private final List<Point3D> combinations = GridMoveStrategy.combinations();

    public long countActivePieces(String filePath, int cycle) {
        Grid grid = Grid.initializeCube(filePath);
        return preformRound(grid, cycle).calculateActive();
    }

    public Grid preformRound(Grid cube, int cycle) {
        Grid round = performSingleRound(cube);
        return cycle > 1 ? preformRound(round, cycle - 1) : round;
    }

    public Grid performSingleRound(Grid grid) {

        // all active which have at 2 or 3 active neighbours
        Set<Point3D> fromActiveToActive = findAllActivePoints(
                grid.activePoints(),
                grid.activePoints(),
                activeNeighbours -> activeNeighbours.size() == 2 || activeNeighbours.size() == 3);

        // all neighbours for active points which have exactly 3 active neighbours
        Set<Point3D> fromInactiveToActive = grid.activePoints().stream()
                .map(point -> calculateAllNeighbours(point, grid.activePoints()))
                .map(neighbour -> findAllActivePoints(
                        neighbour.neighbours(),
                        grid.activePoints(),
                        activeNeighbours -> activeNeighbours.size() == 3))
                .flatMap(Collection::stream)
                .collect(toSet());

        fromActiveToActive.addAll(fromInactiveToActive);
        return new Grid(fromActiveToActive);
    }

    private Set<Point3D> findAllActivePoints(Set<Point3D> points, Set<Point3D> activePoints, Predicate<Set<Point3D>> predicate) {
        return points.stream()
                .map(point -> calculateActiveNeighbours(point, activePoints))
                .filter(activeNeighbour -> predicate.test(activeNeighbour.neighbours()))
                .map(Point3DWithNeighbours::point)
                .collect(toSet());
    }

    private Point3DWithNeighbours calculateActiveNeighbours(Point3D point, Set<Point3D> points) {
        Set<Point3D> activeNeighbours = combinations.stream()
                .map(point::next)
                .collect(toSet());
        activeNeighbours.remove(point);
        activeNeighbours.retainAll(points);
        return new Point3DWithNeighbours(point, activeNeighbours);
    }

    private Point3DWithNeighbours calculateAllNeighbours(Point3D point, Set<Point3D> activePoints) {
        Set<Point3D> neighbours = combinations.stream()
                .map(point::next)
                .filter(neighbour -> !activePoints.contains(neighbour))
                .filter(neighbour -> !point.equals(neighbour))
                .collect(toSet());
        return new Point3DWithNeighbours(point, neighbours);
    }
}