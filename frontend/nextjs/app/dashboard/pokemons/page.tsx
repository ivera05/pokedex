import { fetchFilteredPokemons, fetchPokemons } from "@/app/api/search"
import { PokemonPage } from "@/app/lib/definitions"
import { lusitana } from "@/app/ui/fonts"
import Pagination from "@/app/ui/pokemons/pagination"
import Table from "@/app/ui/pokemons/table"
import Search from "@/app/ui/search"
import { InvoicesTableSkeleton } from "@/app/ui/skeletons"
import { Metadata } from "next"
import { Suspense } from "react"

export const metadata: Metadata = {
    title: "Pokedex"
}

export default async function Page({
    searchParams,
}: {
    searchParams?: {
        query?: string,
        page?: string
    }
}) {
    const query = searchParams?.query || '';
    console.log('Search Params:', searchParams?.page);
    const currentPage = Number(searchParams?.page) || 1;
    console.log('Current Page:',currentPage);
    let response: PokemonPage;
    if (query) {
        response = await fetchFilteredPokemons(query, currentPage);
    } else {
        response = await fetchPokemons( currentPage);
    }

    const totalPages = response.totalPages

    return (
        <div className="w-full">
            <div className="flex w-full items-center justify-between">
                <h1 className={`${lusitana.className } text-2x1`}>Pokedex</h1>
            </div>
            <div className="mt-4 flex items-center justify-between gap-2 md:mt-8">
                <Search placeholder="Search pokemons..." />
            </div>
            <Suspense key={query + currentPage} fallback={<InvoicesTableSkeleton />}>
                <Table query={query} currentPage={currentPage} pokemons={response.content} />
            </Suspense>
            <div className="mt-5 flex w-full justify-center">
                <Pagination totalPages={totalPages} />
            </div>
        </div>
    )
}