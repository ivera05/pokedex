export interface Trainer {
    id: string;
    email: string;
    displayName: string;
    class: string;
    region: string;
    bio: string;
    badges: number;
    avatar: string;
    pokemons: Pokemon[];
}

export interface Evolution {
    id: string;
    name: string;
    trigger: string;
}

export interface Pokemon {
    id: number;
    name: string;
    types: string[];
    baseHP: number;
    baseAttack: number;
    baseDefense: number;
    baseSpecialAttack: number;
    baseSpecialDefense: number;
    baseSpeed: number;
    description: string;
    species: string;
    height: number;
    weight: number;
    abilities: string[];
    image: string;
    evolutions: Evolution[];
    previousEvolutions: Evolution[];
}

export interface PokemonPageResponse {
    content: Pokemon[];
    page: number;
    size: number;
    totalElements: number;
    totalPages: number;
    sort: string[];
}

export interface League {
    league: string;
    region: string;
    gyms: Gym[];
}

export interface Gym {
    id: number,
    leader: Trainer,
    league: string,
    region: string,
    city: string,
    badge: string,
    image: string,
}

export type LoginInput = { username: string; password: string };

export type LoginResponse = { token: string };
