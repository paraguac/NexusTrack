const requestInterceptors: any[] = [];
const responseInterceptors: any[] = [];

const mockInstance = {
  interceptors: {
    request: {
      use: (fn: any) => requestInterceptors.push(fn),
    },
    response: {
      use: (fn: any, errFn: any) => responseInterceptors.push({ success: fn, error: errFn }),
    },
  },
  get: jest.fn(),
  post: jest.fn(),
  put: jest.fn(),
  delete: jest.fn(),
};

jest.mock('axios', () => ({
  __esModule: true,
  default: {
    create: jest.fn(() => mockInstance),
  },
}));

// Force client module to load and register interceptors
require('../api/client');

describe('API Client', () => {
  beforeEach(() => {
    localStorage.clear();
  });

  test('request interceptor attaches Authorization header when token present', () => {
    localStorage.setItem('authToken', 'test-jwt-token');

    const interceptor = requestInterceptors[0];
    const config = { headers: {} as Record<string, string> };
    const result = interceptor(config);

    expect(result.headers['Authorization']).toBe('Bearer test-jwt-token');
  });

  test('does not attach auth header when no token', () => {
    localStorage.removeItem('authToken');

    const interceptor = requestInterceptors[0];
    const config = { headers: {} as Record<string, string> };
    const result = interceptor(config);

    expect(result.headers['Authorization']).toBeUndefined();
  });

  test('response interceptor redirects on 401', async () => {
    const originalLocation = window.location.href;
    delete (window as any).location;
    (window as any).location = { href: '' };

    const errorInterceptor = responseInterceptors[0].error;
    const error = { response: { status: 401 } };

    try {
      await errorInterceptor(error);
    } catch (e) {
      // expected rejection
    }

    expect(window.location.href).toBe('/login');

    (window as any).location = { href: originalLocation };
  });
});
