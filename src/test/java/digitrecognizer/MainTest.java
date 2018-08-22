package digitrecognizer;

import org.junit.Test;

import static org.junit.Assert.*;

public class MainTest {
    @Test
    public void distanceBetweenEqual2Points2D(){
        Integer[] firstArgument = {2, 1};
        Integer[] secondArgument = {2, 1};
        assertEquals(0, Main.distance(firstArgument, secondArgument));
    }

    @Test
    public void distanceBetweenDifferent2Points2D(){
        Integer[] firstArgument = {2, 2};
        Integer[] secondArgument = {2, 1};
        assertEquals(1, Main.distance(firstArgument, secondArgument));
    }

    @Test
    public void distanceBetweenDifferent2Points3D(){
        Integer[] firstArgument = {2, 2, 4};
        Integer[] secondArgument = {2, 2, 2};
        assertEquals(2, Main.distance(firstArgument, secondArgument));
    }

    @Test
    public void distanceBetweenDifferent2PointsnD(){
        Integer[] firstArgument = {2, 2, 4, 5, 8};
        Integer[] secondArgument = {2, 1, 1, 6, 8};
        assertEquals(3, Main.distance(firstArgument, secondArgument));
    }

}