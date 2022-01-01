package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Unit test on Neighbour service
 */
@RunWith(JUnit4.class)
public class NeighbourServiceTest {

    private NeighbourApiService service;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    @Test
    public void getNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }

    @Test
    public void deleteNeighbourWithSuccess() {
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        service.deleteNeighbour(neighbourToDelete);
        assertFalse(service.getNeighbours().contains(neighbourToDelete));
    }

    /**
     * Vérifie l'ajout d'un voisin
     */
    @Test
    public void createNeighbourWithSuccess() {
        // Déclare un voisin à créer qu'on récupère de la liste existante
        Neighbour neighbourToCreate = service.getNeighbours().get(0);
        // Récupère la taille de la liste des voisins
        int sizeBefore = service.getNeighbours().size();
        // Ajoute à la liste le voisin précédemment initialisé
        service.createNeighbour(neighbourToCreate);
        // Vérifie qu'il y a bien un voisin de plus dans la liste
        assertEquals(sizeBefore + 1, service.getNeighbours().size());
        // Vérifie que le dernier voisin de la liste est bien celui créé
        assertEquals(neighbourToCreate, service.getNeighbours().get(service.getNeighbours().size() - 1));
    }

    /**
     * Vérifie la récupération de la liste des favoris
     */
    @Test
    public void getFavoriteNeighboursWithSuccess() {
        // Initialise un voisin au premier voisin de la liste
        Neighbour favoriteNeighbour = service.getNeighbours().get(0);
        // Ajoute le voisin aux favoris
        service.addFavoriteNeighbour(favoriteNeighbour);
        // Vérifie que le voisin est bien dans les favoris quand on appelle la liste des favoris
        assertTrue(service.getFavoriteNeighbours().contains(favoriteNeighbour));
    }

    /**
     * Vérifie l'ajout d'un voisin aux favoris
     */
    @Test
    public void addNeighbourToFavoritesWithSuccess() {
        // Initialise un voisin à ajouter aux favoris
        Neighbour neighbourToAddToFavorites = service.getNeighbours().get(0);
        // Vérifie que ce voisin n'est pas dans la liste des favoris
        assertFalse(service.getFavoriteNeighbours().contains(neighbourToAddToFavorites));
        // Ajoute le voisin aux favoris
        service.addFavoriteNeighbour(neighbourToAddToFavorites);
        // Vérifie que le voisin est maintenant bien dans la liste des favoris
        assertTrue(service.getFavoriteNeighbours().contains(neighbourToAddToFavorites));
    }

    /**
     * Vérifie la suppression d'un voisin des favoris
     */
    @Test
    public void deleteNeighbourFromFavoritesWithSuccess() {
        // Initialise un voisin à retirer des favoris
        Neighbour neighbourToRemoveFromFavorites = service.getNeighbours().get(0);
        // Ajoute d'abord le voisin aux favoris
        service.addFavoriteNeighbour(neighbourToRemoveFromFavorites);
        // Vérifie que le voisin est bien dans la liste des favoris
        assertTrue(service.getFavoriteNeighbours().contains(neighbourToRemoveFromFavorites));
        // Retire le voisin des favoris
        service.deleteFavoriteNeighbour(neighbourToRemoveFromFavorites);
        // Vérifie que le voisin n'est plus dans la liste des favoris
        assertFalse(service.getFavoriteNeighbours().contains(neighbourToRemoveFromFavorites));
    }
}
