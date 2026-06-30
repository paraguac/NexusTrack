import express from 'express';
import rateLimit from 'express-rate-limit';
import { handleAttachment, upload } from './handlers/attachmentHandler';
import { signToken, verifyToken } from './handlers/authHandler';

const app = express();
const PORT = process.env.PORT || 3001;

app.use(express.json());

const authLimiter = rateLimit({
  windowMs: 15 * 60 * 1000, // 15 minutes
  max: 100, // max 100 requests per windowMs
});

app.post('/api/attachments', upload.single('file'), handleAttachment);

app.post('/api/auth/token', authLimiter, (req, res) => {
  const { userId, email } = req.body;
  const token = signToken({ userId, email });
  res.json({ token });
});

app.get('/api/auth/verify', authLimiter, (req, res) => {
  const authHeader = req.headers.authorization;
  if (!authHeader || !authHeader.startsWith('Bearer ')) {
    return res.status(401).json({ error: 'Missing token' });
  }
  try {
    const payload = verifyToken(authHeader.substring(7));
    res.json({ valid: true, payload });
  } catch {
    res.status(401).json({ error: 'Invalid token' });
  }
});

if (require.main === module) {
  app.listen(PORT, () => {
    console.log(`Notification service running on port ${PORT}`);
  });
}

export default app;
