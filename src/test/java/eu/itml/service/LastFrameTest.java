package eu.itml.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class LastFrameTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void shouldGetDoubleMiss() {
        LastFrame frame = new LastFrame();
        frame.setPoints(0);
        frame.setPoints(0);

        assertTrue(frame.isCompleted());
        assertTrue(frame.isFirstMiss());
        assertTrue(frame.isSecondMiss());
    }

    @Test
    public void shouldGetFirstMiss() {
        LastFrame frame = new LastFrame();
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

        LastFrame frame = new LastFrame();
        frame.setPoints(11);

        assertFalse(frame.isCompleted());
    }

    @Test
    public void shouldHitStrikeInFirstShot() {
        LastFrame frame = new LastFrame();
        frame.setPoints(10);

        frame.setPoints(1);
        assertFalse(frame.isCompleted());

        frame.setPoints(4);
        assertTrue(frame.isCompleted());

        assertEquals(15, frame.getPoints());
        assertTrue(frame.isStrike());
    }

    @Test
    public void shouldHitMorePointsAfterStrike() {
        LastFrame frame = new LastFrame();
        frame.setPoints(10);

        frame.setPoints(1);
        assertFalse(frame.isCompleted());

        frame.setPoints(4);
        assertTrue(frame.isCompleted());

        assertEquals(15, frame.getPoints());
        assertTrue(frame.isStrike());
    }

    @Test
    public void shouldHitSpare() {
        LastFrame frame = new LastFrame();
        frame.setPoints(3);
        assertFalse(frame.isCompleted());

        frame.setPoints(7);
        assertFalse(frame.isCompleted());

        frame.setPoints(8);
        assertTrue(frame.isCompleted());
        assertEquals(18, frame.getPoints());
        assertTrue(frame.getSpares().get(1));
    }

    @Test
    public void shouldStoreTwoNumbers() {
        LastFrame frame = new LastFrame();
        frame.setPoints(1);
        assertFalse(frame.isCompleted());

        frame.setPoints(5);
        assertTrue(frame.isCompleted());
        assertEquals(6, frame.getPoints());
    }

    @Test
    public void shouldHitTripleStrike() {
        LastFrame frame = new LastFrame();
        frame.setPoints(10);
        assertFalse(frame.isCompleted());

        frame.setPoints(10);
        assertFalse(frame.isCompleted());

        frame.setPoints(10);
        assertTrue(frame.isCompleted());
        assertEquals(30, frame.getPoints());
        assertTrue(frame.getStrikes().stream().allMatch(s -> s));
    }

    @Test
    public void shouldHitDoubleStrikeAndMiss() {
        LastFrame frame = new LastFrame();
        frame.setPoints(10);
        assertFalse(frame.isCompleted());

        frame.setPoints(10);
        assertFalse(frame.isCompleted());

        frame.setPoints(0);
        assertTrue(frame.isCompleted());
        assertEquals(20, frame.getPoints());
        assertTrue(frame.getStrikes().get(0));
        assertTrue(frame.getStrikes().get(1));
        assertTrue(frame.getMisses().get(2));
    }

    @Test
    public void shouldHitSpareAndLastStrike() {
        LastFrame frame = new LastFrame();
        frame.setPoints(3);
        assertFalse(frame.isCompleted());

        frame.setPoints(7);
        assertFalse(frame.isCompleted());

        frame.setPoints(10);
        assertTrue(frame.isCompleted());
        assertEquals(20, frame.getPoints());

        assertTrue(frame.getStrikes().get(2));
    }

    @Test
    public void shouldNotHitSecondStrikeWhenFirstWasNot() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Max 10 pins!");

        LastFrame frame = new LastFrame();
        frame.setPoints(3);
        assertFalse(frame.isCompleted());

        frame.setPoints(10);
        assertFalse(frame.isCompleted());
    }
}