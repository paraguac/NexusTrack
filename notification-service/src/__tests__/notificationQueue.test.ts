import {
  serializeNotification,
  deserializeNotification,
  processQueue,
  QueuedNotification,
} from '../handlers/notificationQueue';

describe('notificationQueue', () => {
  const sampleNotification: QueuedNotification = {
    id: 'notif-001',
    channel: 'slack',
    message: 'New task assigned to you',
    timestamp: 1700000000,
    metadata: { projectId: 'proj-42' },
  };

  test('serializeNotification returns a string', () => {
    const result = serializeNotification(sampleNotification);
    expect(typeof result).toBe('string');
    expect(result.length).toBeGreaterThan(0);
  });

  test('deserialize roundtrip returns original object', () => {
    const serialized = serializeNotification(sampleNotification);
    const deserialized = deserializeNotification(serialized);

    expect(deserialized.id).toBe(sampleNotification.id);
    expect(deserialized.channel).toBe(sampleNotification.channel);
    expect(deserialized.message).toBe(sampleNotification.message);
    expect(deserialized.timestamp).toBe(sampleNotification.timestamp);
  });

  test('processQueue filters objects missing channel', () => {
    const items: QueuedNotification[] = [
      sampleNotification,
      { id: 'notif-002', channel: undefined as any, message: 'Orphaned', timestamp: 1700000001 },
      { id: 'notif-003', channel: 'email', message: 'Welcome', timestamp: 1700000002 },
    ];

    const result = processQueue(items);

    expect(result).toHaveLength(2);
    expect(result[0].id).toBe('notif-001');
    expect(result[1].id).toBe('notif-003');
  });
});
