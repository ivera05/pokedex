import {League, LoginInput, LoginResponse, PokemonPageResponse, Trainer} from "@/app/lib/types";
import {clearCookie, getCookie} from "@/app/lib/cookies";

const API_BASE =
    process.env.NEXT_PUBLIC_API_BASE_URL?.replace(/\/+$/, "");

function handleUnauthorized() {
    if (typeof window !== "undefined") {
        clearCookie("jwt_token");
        clearCookie("username");
        window.location.href = "/login";
    }
}

async function http<T>(path: string, init?: RequestInit): Promise<T> {
    const token = getCookie("jwt_token");

    const res = await fetch(`${API_BASE}${path}`, {
        ...init,
        headers: {
            "Content-Type": "application/json",
            ...(token ? {Authorization: `Bearer ${token}`} : {}),
            ...(init?.headers || {}),
        },
    });

    if (res.status === 401 || res.status === 403) {
        handleUnauthorized();
        throw new Error("Unauthorized");
    }

    if (!res.ok) {
        const text = await res.text().catch(() => "");
        throw new Error(text || `Request failed (${res.status})`);
    }

    return (await res.json()) as T;
}

export async function apiLogin(input: LoginInput): Promise<LoginResponse> {
    return await http<LoginResponse>("/auth/login", {
        method: "POST",
        body: JSON.stringify(input),
    });
}

export async function apiLogout(): Promise<void> {
    await http("/auth/logout", {
        method: "POST",
    });
}

export async function apiGetTrainer(): Promise<Trainer> {
    return await http<Trainer>("/trainer/", {
        method: "GET",
    });
}

export async function apiGetTrainerPokemons(
    size: number,
    page: number = 0
): Promise<PokemonPageResponse> {
    const params = new URLSearchParams();
    params.set("size", String(size));
    params.set("page", String(page));

    return await http<PokemonPageResponse>(`/trainer/pokemons?${params.toString()}`, {
        method: "GET",
    });
}

export async function apiGetPokemonList(
    name: string | null = null,
    type: string | null = null,
    size: number = 30,
    page: number = 0
): Promise<PokemonPageResponse> {
    const params = new URLSearchParams();
    // eslint-disable-next-line @typescript-eslint/no-unused-expressions
    type?.length && type !== 'all' && params.set("type", type);
    // eslint-disable-next-line @typescript-eslint/no-unused-expressions
    name?.length && params.set("name", name);
    params.set("size", String(size));
    params.set("page", String(page));

    return await http<PokemonPageResponse>(`/pokedex/?${params.toString()}`, {
        method: "GET",
    });
}

// Leagues
export async function apiGetLeagues(): Promise<League[]> {
    return await http<League[]>("/league/", {
        method: "GET",
    });
}

