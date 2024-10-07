import { lusitana } from '@/app/ui/fonts';
import AllPokemon from '../ui/dashboard/pokedex-list';
import { Pokemon, PokemonPage } from '../lib/definitions';
import Search from '../ui/search';
import { fetchPokemons } from '../api/search';

export default async function Page() {
    const pokemonPage: PokemonPage = await fetchPokemons();

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
                    <AllPokemon pokemons={ pokemonPage.content } />
                </div>
            </div>
        </main>
    )
}
