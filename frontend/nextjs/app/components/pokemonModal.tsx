import {motion} from "framer-motion";
import {Pokemon} from "@/app/lib/types";
import Image from "next/image";
import { TYPE_COLORS } from "@/app/lib/pokemonTypes";

export function PokemonModal({pokemon, onClose}: { pokemon: Pokemon; onClose: () => void }) {
    return (
        <div className="fixed inset-0 z-50 flex items-center justify-center p-4">
            {/* 1. Backdrop Animation */}
            <motion.div
                initial={{opacity: 0}}
                animate={{opacity: 1}}
                exit={{opacity: 0}}
                onClick={onClose}
                className="absolute inset-0 bg-zinc-900/60 backdrop-blur-sm"
            />

            {/* 2. Modal Content Animation */}
            <motion.div
                layoutId={`pokemon-${pokemon.id}`}
                initial={{opacity: 0, scale: 0.9, y: 20}}
                animate={{opacity: 1, scale: 1, y: 0}}
                exit={{opacity: 0, scale: 0.9, y: 20}}
                transition={{type: "spring", duration: 0.5, bounce: 0.3}}
                className="relative z-10 w-full max-w-3xl overflow-hidden rounded-3xl bg-white shadow-2xl"
            >
                <button
                    onClick={onClose}
                    className="absolute right-4 top-4 z-20 w-10 h-10 rounded-full bg-zinc-100 p-2 text-zinc-500 hover:bg-zinc-200 transition-colors cursor-pointer"
                >
                    X
                </button>

                <div className="p-8">
                    <div className="flex flex-col items-center">
                        <motion.div
                            initial={{y: 10, opacity: 0}}
                            animate={{y: 0, opacity: 1}}
                            transition={{delay: 0.2}}
                        >
                            <Image
                                src={pokemon.image}
                                alt={pokemon.name}
                                className="h-40 w-40 object-contain"
                                width={200}
                                height={200}
                            />
                        </motion.div>

                        <h2 className="mt-4 text-3xl font-bold capitalize text-zinc-900">{pokemon.name}</h2>
                        <p className="text-zinc-500 font-medium">{pokemon.species}</p>
                        <p className="text-zinc-400 font-normal text-sm">{pokemon.description}</p>

                        <div className="w-full mt-4 border-t border-zinc-100 pt-4">
                            <p className="text-zinc-500 font-light text-sm text-center pb-2">Types:</p>
                            <div className="flex gap-2 justify-center">
                                {pokemon.types.map((type, i) => {
                                    const colors = TYPE_COLORS[type] || TYPE_COLORS.Normal;
                                    return <motion.span
                                        key={type}
                                        initial={{opacity: 0, x: -10}}
                                        animate={{opacity: 1, x: 0}}
                                        transition={{delay: 0.3 + (i * 0.1)}}
                                        className={`rounded-full ${colors.bg} px-2 py-1 text-xs font-medium ${colors.text} border border-${colors.border}`}
                                    >
                                        {type}
                                    </motion.span>
                                })}
                            </div>
                        </div>
                        <div className="w-full mt-4 border-t border-zinc-100 pt-4">
                            <p className="text-zinc-500 font-light text-sm text-center pb-2">Abilities:</p>
                            <div className="flex gap-2 justify-center">
                                {pokemon.abilities.map((ability, i) => (
                                    <motion.span
                                        key={ability}
                                        initial={{opacity: 0, x: -10}}
                                        animate={{opacity: 1, x: 0}}
                                        transition={{delay: 0.3 + (i * 0.1)}}
                                        className="rounded-full bg-blue-100 px-3 py-1 text-xs font-medium text-blue-700"
                                    >
                                        {ability}
                                    </motion.span>
                                ))}
                            </div>
                        </div>
                    </div>

                    {/* Stats Grid */}
                    <motion.div
                        className="mt-4 grid grid-cols-4 gap-4 border-t border-zinc-100 pt-6"
                        initial={{opacity: 0}}
                        animate={{opacity: 1}}
                        transition={{delay: 0.4}}
                    >
                        <Stat label="Height" value={`${pokemon.height} m`} />
                        <Stat label="Weight" value={`${pokemon.weight} kg`} />
                        <Stat label="HP" value={pokemon.baseHP}/>
                        <Stat label="Attack" value={pokemon.baseAttack}/>
                        <Stat label="Sp. Attack" value={pokemon.baseSpecialAttack}/>
                        <Stat label="Defense" value={pokemon.baseDefense}/>
                        <Stat label="Sp. Defense" value={pokemon.baseSpecialDefense}/>
                        <Stat label="Speed" value={pokemon.baseSpeed}/>
                    </motion.div>
                </div>
            </motion.div>
        </div>
    );
}

function Stat({label, value}: { label: string, value?: string | number }) {
    return (
        <div className="flex justify-between rounded-xl bg-zinc-50 p-3">
            <span className="text-sm text-zinc-500 font-medium">{label}</span>
            <span className="text-sm text-zinc-900 font-bold">{value ?? "???"}</span>
        </div>
    );
}