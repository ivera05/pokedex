import NextAuth from 'next-auth';
import { authConfig } from './auth.config';
import Credentials from 'next-auth/providers/credentials';
import { z } from 'zod';
import { User } from './app/lib/definitions';

const apiBaseUrl = process.env.API_BASE_URL;

async function getAuthUser(username:string, password: string): Promise<User | null> {
    const apiUrl = `${apiBaseUrl}/auth/login`;
    try {
        const res = await fetch(apiUrl, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ username: username, password: password})
        });
        console.log('Gor response:', res);
        if (res.ok) {
            const user: User = await res.json();
            return user
        } else {
            // Handle errors from the external API, such as invalid credentials
            const errorData = await res.json();
            console.error('Failed to login:', errorData);
            throw new Error(errorData.message || 'Invalid credentials', errorData);
        }
    } catch (error) {
        console.error('Failed to authenticate:', error);
    }
    return null;
};

export const { auth, signIn, signOut } = NextAuth({
    ...authConfig,
  providers: [
    Credentials({
      async authorize(credentials) {
        const parsedCredentials = z
          .object({ email: z.string().email(), password: z.string().min(6) })
          .safeParse(credentials);

          if (parsedCredentials.success) {
            const { email, password } = parsedCredentials.data;
            const user = await getAuthUser(email, password);
            if (!user) return null;

            return user
          }
          console.log('Invalid credentials');
          return null
      },
    }),
  ],
});