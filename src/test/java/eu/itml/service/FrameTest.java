package eu.itml.service;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FrameTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void shouldGetDoubleMiss() {
        Frame frame = new Frame();
        frame.setPoints(0);
        frame.setPoints(0);

        assertTrue(frame.isCompleted());
        assertTrue(frame.isFirstMiss());
        assertTrue(frame.isSecondMiss());
    }

    @Test
    public void shouldGetFirstMiss() {
        Frame frame = new Frame();
        frame.setPoints(0);
        frame.setPoints(1);

        assertTrue(frame.isCompleted());
        assertTrue(frame.isFirstMiss());
        assertFalse(frame.isSecondMiss());
    }

    @Test
    public void shouldGetExceptionWhenMoreThan10Pins() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Max pins ist 10");

        Frame frame = new Frame();
        frame.setPoints(11);

        assertFalse(frame.isCompleted());
    }

    @Test
    public void shouldHitStrike() {
        Frame frame = new Frame();
        frame.setPoints(10);

        assertTrue(frame.isCompleted());
        assertEquals(10, frame.getPoints());
        assertTrue(frame.isStrike());
    }

    @Test
    public void shouldNotHitMorePointsAfterStrike() {
        Frame frame = new Frame();
        frame.setPoints(10);
        frame.setPoints(1);

        assertTrue(frame.isCompleted());
        assertEquals(10, frame.getPoints());
        assertTrue(frame.isStrike());
    }

    @Test
    public void shouldHitSpare() {
        Frame frame = new Frame();
        frame.setPoints(3);
        frame.setPoints(7);

        assertTrue(frame.isCompleted());
        assertEquals(10, frame.getPoints());
        assertTrue(frame.isSpare());
    }


    @Test
    public void shouldStoreTwoNumbers() {
        Frame frame = new Frame();
        frame.setPoints(1);
        frame.setPoints(5);

        assertTrue(frame.isCompleted());
        assertEquals(6, frame.getPoints());
    }


}