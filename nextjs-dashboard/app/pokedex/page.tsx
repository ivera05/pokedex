import { lusitana } from '@/app/ui/fonts';
import AllPokemon from '../ui/pokedex/pokedex-list';
import { Pokemon } from '../lib/definitions';
import Search from '../ui/search';

const apiBaseUrl = process.env.API_BASE_URL;

async function getPokemons() {
    let apiUrl = `${apiBaseUrl}pokemon`;
    let pokemons = [];
    console.log('Pulled pokemons from :', apiUrl);

    try {
        const res = await fetch(`${apiUrl}?cursor=0&limit=20`);
        console.log('Gor response:', res);
        if (res.ok) {
            pokemons = await res.json();
        }
    } catch (error) {
        console.error('Failed to fetch Pokémon data:', error);
    }

    return pokemons;
};

export default async function Page() {
    const pokemons: Pokemon[] = await getPokemons()

    return (
        <main>
            <h1 className={`${lusitana.className} mb-4 text-xl md:text-2xl`}>
            Pokedex
            </h1>
            <div className="mt-6 grid grid-cols-1 gap-6 md:grid-cols-4 lg:grid-cols-8">
                <div className='mt-5 w-full justify-center'>
                    <Search placeholder='Search Pokemons...' />
                </div>
                <div className="mt-5 flex w-full justify-center">
                    <AllPokemon pokemons={ pokemons } />
                </div>
            </div>
        </main>
    )
}
