const serialize = require('node-serialize') as {
  serialize: (obj: unknown) => string;
  unserialize: (data: string) => unknown;
};

export interface QueuedNotification {
  id: string;
  channel: string;
  message: string;
  timestamp: number;
  metadata?: Record<string, unknown>;
}

export function serializeNotification(n: QueuedNotification): string {
  return serialize.serialize(n);
}

export function deserializeNotification(data: string): QueuedNotification {
  return serialize.unserialize(data) as QueuedNotification;
}

export function processQueue(items: QueuedNotification[]): QueuedNotification[] {
  return items.filter((item) => item.channel !== undefined && item.channel !== null);
}
