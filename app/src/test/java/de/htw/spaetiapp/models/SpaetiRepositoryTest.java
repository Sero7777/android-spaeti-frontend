package de.htw.spaetiapp.models;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

public class SpaetiRepositoryTest {

    @Before
    public void resetSingleton() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field instance = SpaetiRepository.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(null, null);
    }

    @Test
    public void getInstanceUseCase() {
        assertNotNull(SpaetiRepository.getInstance());
    }

    @Test
    public void getSpaetiListUseCase() {
        assertNotNull(SpaetiRepository.getInstance().getSpaetiList());
    }

    @Test
    public void getSpaetiListIsNotEmptyWhenSpaetiIsAdded() {
        SpaetiRepository.getInstance().addSpaeti(new Spaeti());
        assertEquals(1, SpaetiRepository.getInstance().getSpaetiList().size());
    }

    @Test
    public void testSpaetiRepositorySingletonBehavior() {
        SpaetiRepository spaetiRepository1 = SpaetiRepository.getInstance();
        SpaetiRepository spaetiRepository2 = SpaetiRepository.getInstance();

        assertEquals(spaetiRepository1, spaetiRepository2);
    }

    @Test
    public void testSpaetiRepositorySingletonBehaviorSameSpaetiList() {
        SpaetiRepository spaetiRepository1 = SpaetiRepository.getInstance();
        spaetiRepository1.addSpaeti(new Spaeti());
        SpaetiRepository spaetiRepository2 = SpaetiRepository.getInstance();

        assertEquals(spaetiRepository1.getSpaetiList(), spaetiRepository2.getSpaetiList());
    }

    @Test
    public void testSpaetiRepositorySingletonBehaviorSameSpaetiListSize() {
        SpaetiRepository spaetiRepository1 = SpaetiRepository.getInstance();
        spaetiRepository1.addSpaeti(new Spaeti());
        SpaetiRepository spaetiRepository2 = SpaetiRepository.getInstance();

        assertEquals(spaetiRepository1.getSpaetiList().size(), spaetiRepository2.getSpaetiList().size());
    }

    @Test
    public void getSpaetiListIsEmptyWhenInitialized() {
        assertEquals(0, SpaetiRepository.getInstance().getSpaetiList().size());
    }

    @Test
    public void addSpaetiUseCaseReturnsTrue() {
        assertTrue(SpaetiRepository.getInstance().addSpaeti(new Spaeti()));
    }

    @Test
    public void addSpaetiNullTestReturnsFalse() {
        assertFalse(SpaetiRepository.getInstance().addSpaeti(null));
    }

    @Test
    public void updateSpaetiUseCaseReturnsTrue() {
        Spaeti spaeti = new Spaeti();
        spaeti.set_id("1");
        spaeti.setCity("Berlin");
        Spaeti newSpaeti = new Spaeti();
        newSpaeti.set_id("1");
        newSpaeti.setCity("Spandau");
        SpaetiRepository.getInstance().addSpaeti(spaeti);
        assertTrue(SpaetiRepository.getInstance().updateSpaeti(newSpaeti));
    }

    @Test
    public void updateSpaetiNotInRepoReturnsFalse() {
        Spaeti spaeti = new Spaeti();
        spaeti.set_id("1");
        spaeti.setCity("Berlin");
        Spaeti newSpaeti = new Spaeti();
        newSpaeti.set_id("2");
        newSpaeti.setCity("Spandau");
        SpaetiRepository.getInstance().addSpaeti(spaeti);
        assertFalse(SpaetiRepository.getInstance().updateSpaeti(newSpaeti));
    }

    @Test
    public void updateSpaetiNullParameterReturnsFalse() {
        Spaeti spaeti = new Spaeti();
        spaeti.set_id("1");
        spaeti.setCity("Berlin");
        SpaetiRepository.getInstance().addSpaeti(spaeti);
        assertFalse(SpaetiRepository.getInstance().updateSpaeti(null));
    }

    @Test
    public void updateSpaetiEmptyListReturnsFalse() {
        Spaeti spaeti = new Spaeti();
        spaeti.set_id("1");
        spaeti.setCity("Berlin");
        assertFalse(SpaetiRepository.getInstance().updateSpaeti(spaeti));
    }

    @Test
    public void deleteSpaetiUseCaseReturnsTrue() {
        Spaeti spaeti = new Spaeti();
        spaeti.set_id("1");
        spaeti.setCity("Berlin");
        SpaetiRepository.getInstance().addSpaeti(spaeti);
        assertTrue(SpaetiRepository.getInstance().deleteSpaeti("1"));
    }

    @Test
    public void deleteSpaetiEmptyListReturnsFalse() {
        Spaeti spaeti = new Spaeti();
        spaeti.set_id("1");
        spaeti.setCity("Berlin");
        assertFalse(SpaetiRepository.getInstance().deleteSpaeti("1"));
    }

    @Test
    public void deleteSpaetiWrongIdReturnsFalse() {
        Spaeti spaeti = new Spaeti();
        spaeti.set_id("1");
        spaeti.setCity("Berlin");
        SpaetiRepository.getInstance().addSpaeti(spaeti);
        assertFalse(SpaetiRepository.getInstance().deleteSpaeti("2"));
    }

    @Test
    public void deleteSpaetiNullAsParameterReturnsFalse() {
        Spaeti spaeti = new Spaeti();
        spaeti.set_id("1");
        spaeti.setCity("Berlin");
        SpaetiRepository.getInstance().addSpaeti(spaeti);
        assertFalse(SpaetiRepository.getInstance().deleteSpaeti(null));
    }

}