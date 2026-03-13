"use client";

import {useEffect, useState} from "react";
import {AnimatePresence} from "framer-motion";
import {Pokemon, PokemonPageResponse,} from "@/app/lib/types";
import {apiGetTrainerPokemons} from "@/app/lib/api";
import {PokemonModal} from "@/app/components/pokemonModal";
import PokemonCard from "@/app/components/pokemonCard";

export default function PokedexPage() {
    const [data, setData] = useState<PokemonPageResponse | null>(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);
    const [page, setPage] = useState(0);
    const [selectedPokemon, setSelectedPokemon] = useState<Pokemon | null>(null);

    const pageSize = 30;

    useEffect(() => {
        async function load() {
            setLoading(true);
            setError(null);

            try {
                const res = await apiGetTrainerPokemons(pageSize, page);
                setData(res);
                // eslint-disable-next-line @typescript-eslint/no-unused-vars
            } catch (e) {
                setError("Failed to load Pokémon.");
            } finally {
                setLoading(false);
            }
        }

        load();
    }, [page]);

    return (
        <div className="space-y-6">
            <header className="rounded-2xl border border-zinc-200 bg-white p-6 shadow-sm">
                <div className="flex flex-col gap-3 md:flex-row md:items-end md:justify-between">
                    <div>
                        <h1 className="text-2xl font-semibold tracking-tight">Trainer&apos; Pokemon</h1>
                    </div>

                    <div className="text-sm text-zinc-600">
                        {data ? (
                            <div className="text-right">
                                <div>Total Pokémon: <span className="font-semibold">{data.totalElements}</span></div>
                                <div>Page {data.page + 1} of {data.totalPages}</div>
                            </div>
                        ) : null}
                    </div>
                </div>
            </header>

            {error ? (
                <div className="rounded-2xl border border-red-200 bg-red-50 p-4 text-sm text-red-700">
                    {error}
                </div>
            ) : null}

            <section className="grid grid-cols-1 gap-4 md:grid-cols-2 xl:grid-cols-3">
                {loading ? (
                    Array.from({length: 6}).map((_, index) => (
                        <div
                            key={index}
                            className="h-32 rounded-2xl bg-zinc-50 ring-1 ring-zinc-100"
                        />
                    ))
                ) : data?.size?? 0 > 0 ? (
                    data?.content.map((pokemon) => (
                        <div
                            key={pokemon.id}
                            onClick={() => setSelectedPokemon(pokemon)}
                            className="cursor-pointer transition-transform hover:scale-[1.02]"
                        >
                            <PokemonCard pokemon={pokemon}/>
                        </div>
                    ))
                ) : (
                    <div className="col-span-full rounded-2xl border border-zinc-200 bg-white p-6 text-sm text-zinc-600 shadow-sm">
                        Trainer does not have any pokemons.
                    </div>
                )}
            </section>

            <AnimatePresence>
                {selectedPokemon && (
                    <PokemonModal
                        pokemon={selectedPokemon}
                        onClose={() => setSelectedPokemon(null)}
                    />
                )}
            </AnimatePresence>

            {!loading && data ? (
                <footer
                    className="flex flex-col gap-3 rounded-2xl border border-zinc-200 bg-white p-4 shadow-sm sm:flex-row sm:items-center sm:justify-between">
                    <div className="text-sm text-zinc-600">
                        Showing page {data.page + 1} of {data.totalPages}
                    </div>

                    <div className="flex gap-2">
                        <button
                            className="rounded-xl border border-zinc-200 px-4 py-2 text-sm font-medium disabled:cursor-not-allowed disabled:opacity-50 cursor-pointer"
                            disabled={data.page === 0}
                            onClick={() => setPage((prev) => Math.max(prev - 1, 0))}
                        >
                            Previous
                        </button>

                        <button
                            className="rounded-xl bg-purple-600 px-4 py-2 text-sm font-semibold text-white disabled:cursor-not-allowed disabled:opacity-50 cursor-pointer"
                            disabled={data.page + 1 >= data.totalPages}
                            onClick={() => setPage((prev) => prev + 1)}
                        >
                            Next
                        </button>
                    </div>
                </footer>
            ) : null}
        </div>
    );
}
