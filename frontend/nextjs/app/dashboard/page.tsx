"use client";

import {useRouter} from "next/navigation";
import {useEffect, useState} from "react";
import {apiGetTrainer, apiGetTrainerPokemons} from "@/app/lib/api";
import {Pokemon, Trainer} from "@/app/lib/types";
import PokemonCard from "@/app/components/pokemonCard";
import Image from "next/image";
import {getImageUrl} from "@/app/lib/utils";

function Card({
                  title,
                  value
              }: {
    title: string,
    value: string,
}) {
    return (
        <div className="rounded-2xl border border-zinc-200 bg-white p-5 shadow-sm">
            <div className="text-xs font-semibold uppercase tracking-wide text-zinc-500">{title}</div>
            <div className="mt-2 text-2xl font-semibold tracking-tight">{value}</div>
        </div>
    )
}

export default function DashboardPage() {
    const router = useRouter();
    const [loading, setLoading] = useState(true);
    const [trainer, setTrainer] = useState<Trainer | null>(null);
    const [caught, setCaught] = useState<Pokemon[]>([]);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        (async () => {
            setLoading(true);
            setError(null);
            try {
                const [t, c] = await Promise.all([
                    apiGetTrainer(),
                    apiGetTrainerPokemons(6),
                ]);
                setTrainer(t);
                setCaught(c.content);
                // eslint-disable-next-line @typescript-eslint/no-unused-vars
            } catch (e) {
                setError('Failed to load data');
            } finally {
                setLoading(false);
            }
        })();
    }, [router]);
    return (
        <div className="space-y-6">
            <header className="rounded-2xl border border-zinc-200 bg-white p-6 shadow-sm">
                <div className="flex flex-col gap-2 sm:flex-row sm:items-center sm:justify-between">
                    <div>
                        <h1 className="text-2xl font-semibold tracking-tight">Dashboard</h1>
                        <p className="mt-1 text-sm text-zinc-600">
                            {trainer
                                ? `Welcome back, ${trainer.displayName}.`
                                : "Welcome back."}
                        </p>
                    </div>

                    <div className="text-sm text-zinc-600">
                        {trainer ? (
                            <div className="text-right">
                                <div className="font-medium text-zinc-900">
                                    Trainer ID: <span className="font-mono">{trainer.id}</span>
                                </div>
                                <div className="text-xs text-zinc-500">{trainer.email}</div>
                            </div>
                        ) : null}
                    </div>
                </div>
            </header>

            {error ? (
                <div className="rounded-2xl border border-red-200 bg-red-50 p-5 text-sm text-red-700">
                    {error}
                </div>
            ) : null}

            <section className="grid grid-cols-1 gap-4 md:grid-cols-4">
                <div className="rounded-2xl border border-zinc-200 bg-white p-6 shadow-sm">
                    <div className="text-sm font-semibold">Trainer</div>
                    <div className="mt-4 space-y-3 text-sm">
                        <div className="flex justify-between">
                            <Image
                                src={getImageUrl(trainer?.avatar ?? "")}
                                alt={trainer?.displayName ?? "Trainer avatar"}
                                width={48}
                                height={48}
                                className="rounded-full"
                                unoptimized={process.env.NODE_ENV === 'development'}
                            />
                        </div>
                        <div className="flex justify-between">
                            <span className="text-zinc-500">Name</span>
                            <span className="font-medium">{trainer?.displayName ?? (loading ? "…" : "—")}</span>
                        </div>
                        <div className="flex justify-between">
                            <span className="text-zinc-500">Region</span>
                            <span className="font-medium">{trainer?.region ?? (loading ? "…" : "—")}</span>
                        </div>
                    </div>
                </div>
                <Card title="Badges" value={loading ? "…" : String(trainer?.badges ?? 0)}/>
            </section>

            <section className="rounded-2xl border border-zinc-200 bg-white p-6 shadow-sm">
                <div className="flex items-center justify-between">
                    <div>
                        <div className="text-sm font-semibold">Recently caught</div>
                        <div className="text-xs text-zinc-500">
                            Showing up to {caught.length} Pokémon
                        </div>
                    </div>
                </div>

                <div className="mt-5 grid grid-cols-1 gap-3 sm:grid-cols-2 lg:grid-cols-3">
                    {loading ? (
                        Array.from({length: 6}).map((_, i) => (
                            <div
                                key={i}
                                className="h-16 rounded-2xl bg-zinc-50 ring-1 ring-zinc-100"
                            />
                        ))
                    ) : caught.length === 0 ? (
                        <div className="text-sm text-zinc-600">
                            No caught Pokémon yet. Add your first one!
                        </div>
                    ) : (
                        caught.map((pokemon) => (
                            <div
                                key={pokemon.id}
                                className="cursor-pointer transition-transform hover:scale-[1.02]"
                            >
                                <PokemonCard pokemon={pokemon}/>
                            </div>
                        ))
                    )}
                </div>
            </section>
        </div>
    );
}
