export type Trainer = {
    id: string;
    email: string;
    displayName: string;
    class: string;
    region: string;
    badges: number;
    avatar: string;
};

export type EvolutionDto = {
    id: string;
    name: string;
    trigger: string;
};

export type Pokemon = {
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
    evolutions: EvolutionDto[];
    previousEvolutions: EvolutionDto[];
};

export type PokemonPageResponse = {
    content: Pokemon[];
    page: number;
    size: number;
    totalElements: number;
    totalPages: number;
    sort: string[];
};

export type LoginInput = { username: string; password: string };

export type LoginResponse = { token: string };
