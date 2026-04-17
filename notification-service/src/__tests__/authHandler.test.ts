import { signToken, verifyToken, decodeToken } from '../handlers/authHandler';

describe('authHandler', () => {
  const testPayload = { userId: 'user-123', email: 'test@nexustrack.com' };

  test('signToken produces a 3-part JWT string', () => {
    const token = signToken(testPayload);
    const parts = token.split('.');
    expect(parts).toHaveLength(3);
  });

  test('verifyToken returns correct payload', () => {
    const token = signToken(testPayload);
    const decoded = verifyToken(token);

    expect(decoded.userId).toBe('user-123');
    expect(decoded.email).toBe('test@nexustrack.com');
  });

  test('verifyToken throws on tampered token', () => {
    const token = signToken(testPayload);
    const tampered = token.slice(0, -5) + 'XXXXX';

    expect(() => verifyToken(tampered)).toThrow();
  });

  test('decodeToken returns null for non-JWT string', () => {
    const result = decodeToken('not-a-jwt');
    expect(result).toBeNull();
  });

  test('decodeToken returns payload for valid token', () => {
    const token = signToken(testPayload);
    const decoded = decodeToken(token);

    expect(decoded).not.toBeNull();
    expect(decoded?.userId).toBe('user-123');
  });
});
