'use client';

import Link from "next/link";
import {usePathname, useRouter} from "next/navigation";
import {apiLogout} from "@/app/lib/api";
import {clearCookie} from "@/app/lib/cookies";
import {BuildingLibraryIcon, HomeIcon, MapIcon, UserCircleIcon} from '@heroicons/react/24/outline';
import {ElementType} from "react";

function NavItem({
                     href,
                     label,
                     active,
    icon: Icon
                 }: {
    href: string,
    label: string,
    active: boolean,
    icon: ElementType,
}) {
    return (
        <Link href={href}
              className={`flex items-center gap-3 rounded-xl px-4 py-3 text-sm font-medium transition 
              ${active ?
                  'bg-purple-50 text-purple-700 ring-1 ring-purple-100'
                  : 'text-zinc-700 hover:bg-zinc-100'}`}>
            <Icon className="w-5 h-5" />
            {label}
        </Link>
    );
}

export default function Sidebar() {
    const pathname = usePathname();
    const router = useRouter();

    function logout() {
        if (typeof window === 'undefined') return;

        apiLogout()
            .then(() => alert("Logged out successfully"));

        clearCookie('username');
        clearCookie('jwt_token');
        router.push('/login');
    }

    return (
        <div className="h-full rounded-2xl border border-zinc-200 bg-white p-4 shadow-sm">
            <div className="mb-6 px-2">
                <div className="text-xs font-semibold uppercase tracking-wide text-zinc-500">
                    Pokedex Dashboard
                </div>
                <div className="mt-1 text-lg font-semibold tracking-tight">
                    Trainer Panel
                </div>
            </div>

            <nav className="space-y-1">
                <NavItem href="/dashboard" label="Home" active={pathname === '/dashboard'} icon={HomeIcon}/>
                <NavItem href="/dashboard/trainer-pokemons" label="Trainer Pokemons"
                         active={pathname === '/dashboard/trainer-pokemons'} icon={UserCircleIcon}/>
                <NavItem href="/dashboard/pokedex" label="Pokedex"
                         active={pathname === '/dashboard/pokedex'} icon={BuildingLibraryIcon}/>
                <NavItem href="/dashboard/gyms" label="Leagues"
                         active={pathname === '/dashboard/gyms'} icon={MapIcon}/>
            </nav>

            <div className="mt-6 border-t border-zinc-200 pt-4">
                <button
                    onClick={logout}
                    className="w-full rounded-xl border border-zinc-200 bg-white px-4 py-3 text-sm font-semibold text-zinc-700 transition hover:bg-zinc-100 cursor-pointer">
                    Log out
                </button>
            </div>
        </div>
    );
};