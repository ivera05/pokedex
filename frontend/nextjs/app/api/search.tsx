
'use server';

import { PokemonPage, Pokemon } from 'app/lib/definitions'

const apiBaseUrl = process.env.API_BASE_URL;
const ITEMS_PER_PAGE = 15;

export async function fetchPokemons(
    currentPage: number = 1
): Promise<PokemonPage | void> {
    const offset = (currentPage - 1);
    const apiUrl = `${apiBaseUrl}/pokemon`;

    try {
        const res = await fetch(`${apiUrl}?page=${offset}&size=${ITEMS_PER_PAGE}`);

        if (res.ok) {
            return await res.json();
        }
    } catch (error) {
        console.error('Failed to fetch Pokémon data:', error);
    }
};

export async function fetchFilteredPokemons(
    query: string,
    currentPage: number
): Promise<PokemonPage | void> {
    const offset = (currentPage - 1) * ITEMS_PER_PAGE
    const apiUrl = `${apiBaseUrl}/pokemon/search`;

    try {
        const res = await fetch(`${apiUrl}?query=${encodeURIComponent(query)}&page=${offset}&size=${ITEMS_PER_PAGE}`);

        if (res.ok) {
            return await res.json();
        }
    } catch (error) {
        console.error('Failed to fetch Pokémon data:', error);
    }
};
