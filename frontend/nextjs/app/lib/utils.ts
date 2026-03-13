export const getImageUrl = (path: string) => {
    const baseUrl = process.env.NEXT_PUBLIC_API_URL || 'http://localhost:8080';
    return `${baseUrl}${path}`;
};