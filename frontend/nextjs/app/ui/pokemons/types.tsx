import { CheckIcon, ClockIcon } from '@heroicons/react/24/outline';
import clsx from 'clsx';

export default function PokemonTypes({ 
    types,
}: {
    types: string[]
}) {
  return (
    <>
      {types.map(type => (
        <span 
          key={type} // Always use a unique key when rendering lists in React
          className={clsx(
            "inline-block rounded-full px-3 py-1 text-sm font-medium",
            `bg-${type.toLowerCase()}-100`,
            `text-${type.toLowerCase()}-700`
          )}
        >
          {type}
        </span>
      ))}
    </>
  )
}
