package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class TorpedoStoreTest {

    // Ezek konstansok a tesztekhez, hogy sokkal könnyebben átlátható legyen
    // Hajnal 2:09van ezért ezt a kódot magyarul kommenteltem :')
    static final double NEVER_FAILS = 0.0;
    static final double ALWAYS_FAILS = 1.0;

    @Test
    void fire_Success() {
        // Arrange
        TorpedoStore store = new TorpedoStore(1);

        // Act
        boolean result = store.fire(1);

        // Assert
        assertEquals(true, result);
    }

    //! new TorpedoStrore(x) után TÉNYLEG x torpedo van benne
    @Test
    void constructor_SetsTorpedoCount() {
        // Arrange & Act
        TorpedoStore store = new TorpedoStore(5, NEVER_FAILS);

        // Assert
        assertEquals(5, store.getTorpedoCount());
    }

    //! 0 torpedoval ÜRES
    @Test
    void isEmpty_NoTorpedoes_True() {
        // Arrange & Act
        TorpedoStore store = new TorpedoStore(0, NEVER_FAILS);

        // Assert
        assertTrue(store.isEmpty());
    }

    //! van pld 5, lő 1-et, marad 4
    @Test
    void fire_NeverFails_DecreasesCountByOne() {
        // Arrange
        TorpedoStore store = new TorpedoStore(5, NEVER_FAILS);

        // Act
        boolean result = store.fire(1);

        // Assert
        assertTrue(result);
        assertEquals(4, store.getTorpedoCount());
    }

    //! van pld 5, lő 3-at, marad 2
    @Test
    void fire_MoreTorpedoes_DecreasesCountByNumberFired() {
        // Arrange
        TorpedoStore store = new TorpedoStore(5, NEVER_FAILS);

        // Act
        boolean result = store.fire(3);

        // Assert
        assertTrue(result);
        assertEquals(2, store.getTorpedoCount());
    }

    //! utolsót is kilőttük, üres lesz
    @Test
    void fire_LastTorpedo_StoreBecomesEmpty() {
        // Arrange
        TorpedoStore store = new TorpedoStore(1, NEVER_FAILS);

        // Act
        store.fire(1);

        // Assert
        assertTrue(store.isEmpty());
    }

    //! ha elromlik, false, és NEM fogy torpedo
    @Test
    void fire_AlwaysFails_ReturnsFalseAndKeepsCount() {
        // Arrange
        TorpedoStore store = new TorpedoStore(5, ALWAYS_FAILS);

        // Act
        boolean result = store.fire(1);

        // Assert
        assertFalse(result);
        assertEquals(5, store.getTorpedoCount());
    }

    //! rossz számnák exception-t dob esetekre bontva:
    @Test
    void fire_MoreThanAvailable_ThrowsException() {
        // Arrange
        TorpedoStore store = new TorpedoStore(2, NEVER_FAILS);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> store.fire(3));
        //? Ez a sor magyarul: Ha store.fire(3) meghívásakor NEM dob IllegalArgumentException-t, akkor a teszt MEGBUKIK
    }

    @Test
    void fire_Zero_ThrowsException() {
        // Arrange
        TorpedoStore store = new TorpedoStore(2, NEVER_FAILS);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> store.fire(0));
    }

    @Test
    void fire_Negative_ThrowsException() {
        // Arrange
        TorpedoStore store = new TorpedoStore(2, NEVER_FAILS);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> store.fire(-1));
    }

    @Test
    void fire_EmptyStore_ThrowsException() {
        // Arrange
        TorpedoStore store = new TorpedoStore(0, NEVER_FAILS);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> store.fire(1));
    }
}
