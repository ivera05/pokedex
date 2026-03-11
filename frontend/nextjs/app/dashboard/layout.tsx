import Sidebar from "@/app/dashboard/sidebar";
import React from "react";

export default function DashboardLayout({children}: { children: React.ReactNode }) {
    return (<div className="min-h-screen bg-zinc-50 text-zinc-900">
        <div className="mx-auto flex min-h-screen max-w-7xl gap-6 p-6">
            <aside className="hidden w-64 shrink-0 md:block">
                <Sidebar />
            </aside>

            <main className="min-w-0 flex-1">{children}</main>
        </div>
    </div>);
}