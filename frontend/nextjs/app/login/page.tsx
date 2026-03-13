"use client"

import {useState} from "react";
import {useRouter} from "next/navigation";
import {apiLogin} from "@/app/lib/api";
import {setCookie} from "@/app/lib/cookies";

export default function LoginPage() {
    const router = useRouter();
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [submitting, setSubmitting] = useState(false);
    const [error, setError] = useState<string | null>('');

    async function handleSubmit(e: React.FormEvent) {
        e.preventDefault();
        setError(null);
        setSubmitting(true);
        try {
            const response = await apiLogin({"username": username, "password": password});
            setCookie('username', username);
            setCookie('jwt_token', response.token);

            router.push('/dashboard');
            // eslint-disable-next-line @typescript-eslint/no-unused-vars
        } catch (error) {
            setError('Failed to login');
        } finally {
            setSubmitting(false);
        }
    }

    return (
        <div className="min-h-screen bg-zinc-50 text-zinc-900">
            <div className="mx-auto flex min-h-screen max-w-6xl items-center justify-center px-6 py-12">
                <div className="w-full max-w-md rounded-2xl border border-zinc-200 bg-white p-8 shadow-sm">
                    <div className="mb-6">
                        <h1 className="mt-1 text-2xl font-semibold tracking-tight">
                            Trainer Login
                        </h1>
                        <p className="mt-2 text-sm text-zinc-600">
                            Sign in to view your caught Pokémon and other data.
                        </p>
                    </div>

                    <form onSubmit={handleSubmit} className="space-y-4">
                        <label className="block">
                          <span className="text-sm font-medium text-zinc-700">
                            Username
                          </span>
                            <input
                                value={username}
                                onChange={(e) => setUsername(e.target.value)}
                                className="mt-1 w-full rounded-xl border border-zinc-200 bg-white px-4 py-3 text-sm outline-none ring-purple-500/20 placeholder:text-zinc-400 focus:border-purple-400 focus:ring-4"
                                placeholder="trainer@domain.com"
                                autoComplete="username"
                                required
                            />
                        </label>

                        <label className="block">
              <span className="text-sm font-medium text-zinc-700">
                Password
              </span>
                            <input
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                                type="password"
                                className="mt-1 w-full rounded-xl border border-zinc-200 bg-white px-4 py-3 text-sm outline-none ring-purple-500/20 placeholder:text-zinc-400 focus:border-purple-400 focus:ring-4"
                                placeholder="••••••••"
                                autoComplete="current-password"
                                required
                            />
                        </label>

                        {error ? (
                            <div className="rounded-xl border border-red-200 bg-red-50 px-4 py-3 text-sm text-red-700">
                                {error}
                            </div>
                        ) : null}

                        <button
                            type="submit"
                            disabled={submitting}
                            className="w-full rounded-xl bg-purple-600 px-4 py-3 text-sm font-semibold text-white shadow-sm transition hover:bg-purple-700 disabled:cursor-not-allowed disabled:opacity-60"
                        >
                            {submitting ? "Signing in..." : "Sign in"}
                        </button>
                    </form>
                </div>
            </div>
        </div>
    );
}