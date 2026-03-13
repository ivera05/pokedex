import type { NextConfig } from "next";

const nextConfig: NextConfig = {
    output: "standalone",
    images: {
        remotePatterns: [
            {
                protocol: "https",
                hostname: "raw.githubusercontent.com",
            },
            {
                protocol: 'http',
                hostname: 'localhost',
                port: '8080',
                pathname: '/images/**',
            },
            {
                protocol: 'http',
                hostname: '127.0.0.1',
                port: '8080',
                pathname: '/images/**',
            },
        ],
        dangerouslyAllowSVG: true,
    },
};

export default nextConfig;
