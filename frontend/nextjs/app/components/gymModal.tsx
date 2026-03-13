import Image from "next/image";
import {Gym, Pokemon} from "@/app/lib/types";
import {motion} from "framer-motion";
import {getImageUrl} from "@/app/lib/utils";

export default function GymModal({gym, onClose}: { gym: Gym; onClose: () => void }) {
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
                layoutId={`pokemon-${gym.id}`}
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
                    <div className="flex flex-col items-center border-b border-zinc-100 pb-6">
                        <p className="text-blue-400 font-medium">{gym.city} Gym</p>
                        <div className="relative w-24 h-24 mb-1">
                            <Image src={getImageUrl(gym.leader.avatar)} alt={gym.leader.displayName + "avatar"} fill className="object-contain" unoptimized/>
                        </div>
                        <h2 className="text-2xl font-black">{gym.leader.displayName}</h2>
                        <p className="text-zinc-500 font-light text-sm mt-1">{gym.leader.bio}</p>
                    </div>

                    <div className="mt-6">
                        <p className="text-zinc-500 font-light text-sm text-left pb-2">Current Team:</p>
                        <div className="grid grid-cols-3 gap-3">
                            {gym.leader.pokemons.map((pokemon: Pokemon) => (
                                <div key={pokemon.id}
                                     className="flex flex-col items-center p-2 rounded-xl bg-zinc-50">
                                    <div className="relative w-12 h-12">
                                        <Image src={pokemon.image} alt={pokemon.name} fill className="object-contain"
                                               unoptimized/>
                                    </div>
                                    <span className="text-[10px] mt-1 font-semibold">{pokemon.name}</span>
                                </div>
                            ))}
                        </div>
                    </div>
                </div>
            </motion.div>
        </div>
    );
}