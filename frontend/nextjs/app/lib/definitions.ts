// This file contains type definitions for your data.
// It describes the shape of the data, and what data type each property should accept.
// For simplicity of teaching, we're manually defining these types.
// However, these types are generated automatically if you're using an ORM such as Prisma.
export type User = {
  id: string;
  email: string;
  token: string;
};

export type Customer = {
  id: string;
  name: string;
  email: string;
  image_url: string;
};

export type Invoice = {
  id: string;
  customer_id: string;
  amount: number;
  date: string;
  // In TypeScript, this is called a string union type.
  // It means that the "status" property can only be one of the two strings: 'pending' or 'paid'.
  status: 'pending' | 'paid';
};

export type Revenue = {
  month: string;
  revenue: number;
};

export type LatestInvoice = {
  id: string;
  name: string;
  image_url: string;
  email: string;
  amount: string;
};

// The database returns a number for amount, but we later format it to a string with the formatCurrency function
export type LatestInvoiceRaw = Omit<LatestInvoice, 'amount'> & {
  amount: number;
};

export type InvoicesTable = {
  id: string;
  customer_id: string;
  name: string;
  email: string;
  image_url: string;
  date: string;
  amount: number;
  status: 'pending' | 'paid';
};

export type CustomersTableType = {
  id: string;
  name: string;
  email: string;
  image_url: string;
  total_invoices: number;
  total_pending: number;
  total_paid: number;
};

export type FormattedCustomersTable = {
  id: string;
  name: string;
  email: string;
  image_url: string;
  total_invoices: number;
  total_pending: string;
  total_paid: string;
};

export type CustomerField = {
  id: string;
  name: string;
};

export type InvoiceForm = {
  id: string;
  customer_id: string;
  amount: number;
  status: 'pending' | 'paid';
};

export type Base = {
  hp: number;
  attack: number;
  defense: number;
  special_attack: number;
  special_defense: number;
  speed: number;
}

export type Evolutions = {
  prev: Evolution| null;
  next: Evolution[]| null;
}

export type Evolution = {
  id: number;
  condition: string;
}

export type Profile = {
  height: string;
  weight: string;
  ability: Ability[];
}

export type Ability = {
  ability: string;
  isHidden: boolean;
}

export type Images = {
  sprite: string| null;
  thumbnail: string| null,
  hires: string| null;
}

export type Pokemon = {
  id: number,
  name: string,
  type: string[],
  description: string,
  species: string,
  base: Base| null,
  evolutions: Evolutions,
  profile: Profile,
  images: Images
}

export type PokemonPage = {
  content: Pokemon[],
  pageable: Pageable,
  totalElements: number,
  totalPages: number,
  last: Boolean
  size: number,
  number: number,
  sort: Sort,
  numberOfElements: number,
  first: Boolean,
  empty: Boolean
}

export type Pageable = {
  pageNumber: number,
  pageSize: number,
  sort: Sort,
  offset: number,
  paged: Boolean,
  unpaged: Boolean,
}

export type Sort = {
  empty: Boolean,
  sorted: Boolean,
  unsorted: Boolean
}