import '@/app/ui/dashboard/pokemon-types.css';
import Image from 'next/image';
import { formatPokemonId } from '@/app/lib/utils';
import { UpdateInvoice, DeleteInvoice, ViewPokemon } from './buttons';
import InvoiceStatus from './status';
import { fetchFilteredPokemons, fetchPokemons } from '@/app/api/search';
import { Pokemon } from '@/app/lib/definitions';
import PokemonTypes from './types';

export default async function InvoicesTable({
  query,
  currentPage,
  pokemons,
}: {
  query: string;
  currentPage: number;
  pokemons: Pokemon[]
}) {

  return (
    <div className="mt-6 flow-root">
      <div className="inline-block min-w-full align-middle">
        <div className="rounded-lg bg-gray-50 p-2 md:pt-0">
          <div className="md:hidden">
            {pokemons?.map((pokemon) => (
              <div
                key={pokemon.id}
                className="mb-2 w-full rounded-md bg-white p-4"
              >
                <div className="flex items-center justify-between border-b pb-4">
                  <div>
                    <div className="mb-2 flex items-center">
                      <Image
                        src={pokemon.images.sprite ? pokemon.images.sprite: 'no_image.jpg'}
                        className="mr-2 rounded-full"
                        width={28}
                        height={28}
                        alt={`${pokemon.name}'s profile picture`}
                      />
                      <p>{pokemon.name}</p>
                    </div>
                    <p className="text-sm text-gray-500">{pokemon.name}</p>
                  </div>
                  <InvoiceStatus status='pending' />
                </div>
                <div className="flex w-full items-center justify-between pt-4">
                  <div>
                    <p className="text-xl font-medium">
                      {/* {formatCurrency(pokemon.amount)} */}
                    </p>
                    {/* <p>{formatDateToLocal(pokemon.date)}</p> */}
                  </div>
                  <div className="flex justify-end gap-2">
                    <UpdateInvoice id={pokemon.id.toString()} />
                    <DeleteInvoice id={pokemon.id.toString()} />
                  </div>
                </div>
              </div>
            ))}
          </div>
          <table className="hidden min-w-full text-gray-900 md:table">
            <thead className="rounded-lg text-left text-sm font-normal">
              <tr>
                <th scope="col" className="px-4 py-5 font-medium sm:pl-6">
                  Id
                </th>
                <th scope="col" className="px-3 py-5 font-medium">
                  Pokemon
                </th>
                <th scope="col" className="px-3 py-5 font-medium">
                  Type
                </th>
                <th scope="col" className="px-3 py-5 font-medium">
                  Species
                </th>
                <th scope="col" className="relative py-3 pl-6 pr-3">
                  <span className="sr-only">Edit</span>
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
                    { formatPokemonId(pokemon.id)}
                  </td>
                  <td className="whitespace-nowrap py-3 pl-6 pr-3">
                    <div className="flex items-center gap-3">
                      <Image
                        src={pokemon.images.sprite ? pokemon.images.thumbnail : 'no_image.jpg'}
                        className="rounded-full"
                        width={28}
                        height={28}
                        alt={`${pokemon.name}'s profile picture`}
                      />
                      <p>{pokemon.name}</p>
                    </div>
                  </td>
                  <td className="whitespace-nowrap px-3 py-3">
                    <PokemonTypes types={pokemon.type} />
                  </td>
                  <td className="whitespace-nowrap px-3 py-3">
                    {pokemon.species}
                  </td>
                  <td className="whitespace-nowrap px-3 py-3">
                    {/* <InvoiceStatus status={pokemon.status} /> */}
                  </td>
                  <td className="whitespace-nowrap py-3 pl-6 pr-3">
                    <div className="flex justify-end gap-3">
                      <ViewPokemon id={pokemon.id.toString()} />
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}
