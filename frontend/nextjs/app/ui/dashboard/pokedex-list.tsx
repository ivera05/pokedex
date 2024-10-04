import { ArrowPathIcon } from '@heroicons/react/24/outline';
import clsx from 'clsx';
import Image from 'next/image';
import { lusitana } from '@/app/ui/fonts';
import { Pokemon } from '@/app/lib/definitions';

export default async function AllPokemon({
  pokemons,
}: {
  pokemons: Pokemon[];
}) {
  const fallbackImage = '/images/default-sprite.png';
  return (
      <div className="rounded-lg bg-gray-50 p-2 md:pt-0">
        <div className="md:hidden">
          {pokemons?.map((pokemon) => (
            <div
              key={pokemon.id}
              className="mb-2 w-full rounded-md bg-white p-4"
            >
              <div className="flex w-full items-center justify-between pt-4 pb-2">
                <div>
                  <p>#{ pokemon.id.toLocaleString('en-US', { minimumIntegerDigits: 3, useGrouping: false }) }</p>
                </div>
              </div>
              <div className="flex items-center justify-between border-t border-b pb-4 pt-4">
                <div>
                  <div className="mb-2 flex items-center">
                    <Image
                      src={ pokemon.images.thumbnail ? pokemon.images.thumbnail : fallbackImage }
                      className="mr-2 rounded-full"
                      width={28}
                      height={28}
                      alt={`${ pokemon.name }'s profile picture`}
                    />
                    <p>{pokemon.name}</p>
                  </div>
                </div>
              </div>
              <div className="flex w-full items-center justify-between pt-4">
                <div>
                  <p className="text-xl font-medium">
                    {pokemon.type}
                  </p>                    
                </div>
              </div>
            </div>
          ))}
        </div>
        <table className="hidden min-w-full text-gray-900 md:table">
          <thead className="rounded-lg text-left text-sm font-normal">
            <tr>
              <th scope="col" className="px-4 py-5 font-medium">
                Id
              </th>
              <th scope="col" className="px-4 py-5 font-medium sm:pl-6">
                Pokemon
              </th>
              <th scope="col" className="px-4 py-5 font-medium sm:pl-6">
                Type
              </th>
            </tr>
          </thead>
          <tbody className="bg-white">
            {pokemons?.map((pokemon) => (
              <tr
                key={pokemon.id}
                className="w-full border-b py-3 text-sm last-of-type:border-none [&:first-child>td:first-child]:rounded-tl-lg [&:first-child>td:last-child]:rounded-tr-lg [&:last-child>td:first-child]:rounded-bl-lg [&:last-child>td:last-child]:rounded-br-lg"
              >
                <td className="whitespace-nowrap px-3 py-3">
                  <p>#{ pokemon.id.toLocaleString('en-US', { minimumIntegerDigits: 3, useGrouping: false }) }</p>
                </td>
                <td className="whitespace-nowrap py-3 pl-6 pr-3">
                  <div className="flex items-center gap-3">
                    <Image
                      src={ pokemon.images.thumbnail ? pokemon.images.thumbnail : fallbackImage }
                      className="rounded-full"
                      width={50}
                      height={50}
                      alt={`${pokemon.name}'s profile picture`}
                    />
                    <p>{ pokemon.name }</p>
                  </div>
                </td>
                <td className="whitespace-nowrap px-3 py-3 pl-6 pr-6">
                  {pokemon.type}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
  );
}
