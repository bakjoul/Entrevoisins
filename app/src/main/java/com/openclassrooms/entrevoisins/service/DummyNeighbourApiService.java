package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.ArrayList;
import java.util.List;

/**
 * Dummy mock for the Api
 */
public class DummyNeighbourApiService implements NeighbourApiService {

    private List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();


    /**
     * {@inheritDoc}
     */
    @Override
    public List<Neighbour> getNeighbours() {
        return neighbours;
    }

    @Override
    // Retourne la liste des voisins favoris
    public List<Neighbour> getFavoriteNeighbours() {
        List<Neighbour> favorites = new ArrayList<>();
        for (Neighbour neighbour : neighbours) {
            if (neighbour.isFavorite())
                favorites.add(neighbour);
        }
        return favorites;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteNeighbour(Neighbour neighbour) {
        neighbours.remove(neighbour);
    }

    /**
     * {@inheritDoc}
     *
     * @param neighbour
     */
    @Override
    public void createNeighbour(Neighbour neighbour) {
        neighbours.add(neighbour);
    }

    @Override
    // Ajoute le voisin aux favoris
    public void addFavoriteNeighbour(Neighbour neighbour) {
        neighbour.setFavorite(true);
    }

    @Override
    // Supprime le voisin des favoris
    public void deleteFavoriteNeighbour(Neighbour neighbour) {
        neighbour.setFavorite(false);
    }
}