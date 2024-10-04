import type { NextApiRequest, NextApiResponse } from 'next';

type Data = {
  token?: string;
  message?: string;
};

export default async function handler(req: NextApiRequest, res: NextApiResponse<Data>) {
  if (req.method === 'POST') {
    const { email, password } = req.body;

    try {
      // Call to external authentication API
      const externalResponse = await fetch('https://example.com/api/auth/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ email, password }),
      });

      // Handle the response from the external API
      if (externalResponse.ok) {
        const externalData = await externalResponse.json();
        const token = externalData.token; // Assuming the external API returns a JWT token

        // Send the token to the client
        res.status(200).json({ token });
      } else {
        // Handle errors from the external API, such as invalid credentials
        const errorData = await externalResponse.json();
        res.status(externalResponse.status).json({ message: errorData.message || 'Invalid credentials' });
      }
    } catch (error) {
      // Handle any other errors, such as network issues or server errors
      res.status(500).json({ message: 'Internal server error' });
    }
  } else {
    res.status(405).json({ message: 'Method not allowed' });
  }
}
