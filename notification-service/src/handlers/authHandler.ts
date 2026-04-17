import jwt from 'jsonwebtoken';

const SECRET = process.env.JWT_SECRET || 'nexustrack-dev-secret';

export function signToken(payload: Record<string, unknown>): string {
  return jwt.sign(payload, SECRET, { expiresIn: '24h' });
}

export function verifyToken(token: string): Record<string, unknown> {
  return jwt.verify(token, SECRET) as Record<string, unknown>;
}

export function decodeToken(token: string): Record<string, unknown> | null {
  try {
    const decoded = jwt.decode(token);
    if (decoded && typeof decoded === 'object') {
      return decoded as Record<string, unknown>;
    }
    return null;
  } catch {
    return null;
  }
}
