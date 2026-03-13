import Image from "next/image";
import {Pokemon} from "@/app/lib/types";
import {TYPE_COLORS} from "@/app/lib/pokemonTypes";
export default function PokemonCard({pokemon}: { pokemon: Pokemon }) {
    return (
        <div className="rounded-2xl border border-zinc-200 bg-white p-4 shadow-sm">
            <div className="flex items-center gap-4">
                <Image
                    src={pokemon.image}
                    alt={pokemon.name}
                    className="rounded-xl bg-zinc-50 object-contain p-2"
                    width={100}
                    height={100}
                />

                <div className="min-w-0 flex-1">
                    <div className="text-xs text-zinc-500">
                        #{String(pokemon.id).padStart(3, "0")}
                    </div>

                    <div className="truncate text-lg font-semibold text-zinc-900">
                        {pokemon.name}
                    </div>

                    <div className="mt-1 text-sm text-zinc-600">
                        {pokemon.species}
                    </div>

                    <div className="mt-2 flex flex-wrap gap-2">
                        {pokemon.types.map((type) => {
                            const colors = TYPE_COLORS[type] || TYPE_COLORS.Normal;
                            return <span
                                key={type}
                                className={`rounded-full ${colors.bg} px-2 py-1 text-xs font-medium ${colors.text} border border-${colors.border}`}
                            >
                                {type}
                            </span>
                        })}
                    </div>
                </div>
            </div>
        </div>
    );
}