'use client';

import {useEffect, useState} from 'react';
import Image from 'next/image';
import {ChevronDownIcon, ChevronUpIcon} from '@heroicons/react/24/outline';
import {Gym, League} from "@/app/lib/types";
import {apiGetLeagues} from "@/app/lib/api";
import GymModal from "@/app/components/gymModal";
import {getImageUrl} from "@/app/lib/utils";

export default function GymsPage() {
    const [leagues, setLeagues] = useState<League[]>([]);

    useEffect(() => {
        async function load() {
            try {
                const res = await apiGetLeagues();
                setLeagues(res);
            } catch (e) {
                console.error('Failed to load leagues:', e);
            } finally {
            }
        }

        load();
    }, []);

    return (
        <div className="space-y-6">
            <header className="rounded-2xl border border-zinc-200 bg-white p-6 shadow-sm">
                <div className="flex flex-col gap-2 sm:flex-row sm:items-center sm:justify-between">
                    <h1 className="text-3xl font-bold">Leagues</h1>
                </div>
            </header>
            <section className="grid grid-rows-1 gap-4">
                {leagues.map((league) => (
                    <LeagueAccordion
                        key={league.league}
                        league={league.league}
                        gyms={league.gyms}
                    />
                ))}
            </section>
        </div>
    );
}

function LeagueAccordion({league, gyms}: { league: string; gyms: Gym[] }) {
    const [isOpen, setIsOpen] = useState(false);
    const [selectedGym, setSelectedGym] = useState<Gym | null>(null);

    return (
        <div className="rounded-2xl border border-zinc-200 bg-white overflow-hidden shadow-lg">
            {/* Accordion Header */}
            <button
                onClick={() => setIsOpen(!isOpen)}
                className="w-full flex items-center justify-between p-5 cursor-pointer transition-transform hover:scale-[1.02]"
            >
                <div className="text-left">
                    <h2 className="text-xl font-bold text-zinc-600">{league} League</h2>
                    <p className="mt-1 text-sm text-slate-400">{gyms[0]?.region} Region</p>
                </div>
                {isOpen ? <ChevronUpIcon className="w-6 h-6"/> : <ChevronDownIcon className="w-6 h-6"/>}
            </button>

            {/* Collapsable Content */}
            {isOpen && (
                <div className="p-6 grid grid-cols-1 sm:grid-cols-2 md:grid-cols-4 gap-4 bg-zinc-200 border-t border-zinc-200">
                    {gyms.map((gym) => (
                        <div
                            key={gym.id}
                            onClick={() => setSelectedGym(gym)}
                            className="p-4 bg-white rounded-2xl border border-zinc-100 cursor-pointer hover:scale-105 transition-transform flex flex-col items-center text-center"
                        >
                            <div className="relative w-16 h-16 mb-3">
                                <Image src={getImageUrl(gym.image)} alt={gym.badge} fill className="object-contain" unoptimized/>
                            </div>
                            <h3 className="font-bold text-sm">{gym.badge}</h3>
                            <p className="text-xs text-slate-400">{gym.leader.displayName}</p>
                            <p className="text-[10px] uppercase tracking-widest text-blue-400 mt-1">{gym.city}</p>
                        </div>
                    ))}
                </div>
            )}

            {/* Gym Modal */}
            {selectedGym && (
                <GymModal gym={selectedGym} onClose={() => setSelectedGym(null)}/>
            )}
        </div>
    );
}