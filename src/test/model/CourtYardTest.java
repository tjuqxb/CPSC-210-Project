package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CourtYardTest {
    private CourtYard cy;

    @BeforeEach
    void setUp() {
        cy = new CourtYard("test", 10, 20, "2");
    }

    @Test
    void setName() {
        cy.setName("test0");
        assertEquals("test0", cy.getName());
        cy.setName("test1");
        assertEquals("test1", cy.getName());
    }

    @Test
    void setWidth() {
        cy.setWidth(200);
        assertEquals(200, cy.getWidth());
        cy.setWidth(300);
        assertEquals(300, cy.getWidth());
    }

    @Test
    void setHeight() {
        cy.setHeight(100);
        assertEquals(100, cy.getHeight());
        cy.setHeight(800);
        assertEquals(800, cy.getHeight());
    }

    @Test
    void getAttribute() {
        assertEquals("COURT_YARD", cy.getAttribute());
    }

    @Test
    void getWidth() {
        assertEquals(10, cy.getWidth());
        cy.setWidth(50);
        assertEquals(50, cy.getWidth());
    }

    @Test
    void getHeight() {
        assertEquals(20, cy.getHeight());
        cy.setHeight(55);
        assertEquals(55, cy.getHeight());
    }

    @Test
    void getArea() {
        assertEquals(200, cy.getArea());
        cy.setWidth(30);
        cy.setHeight(40);
        assertEquals(1200, cy.getArea());
    }

    @Test
    void getBuildingArea() {
        assertEquals(0, cy.getBuildingArea());
        cy.setWidth(60);
        cy.setHeight(70);
        assertEquals(0, cy.getBuildingArea());
    }

    @Test
    void getName() {
        assertEquals("test", cy.getName());
        cy.setName("test100");
        assertEquals("test100", cy.getName());
    }
}