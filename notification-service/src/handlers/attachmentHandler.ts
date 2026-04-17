import multer from 'multer';
import { Request, Response } from 'express';
import path from 'path';

const ALLOWED_EXTENSIONS = ['.pdf', '.png', '.jpg', '.csv'];
const MAX_FILE_SIZE = 10 * 1024 * 1024; // 10 MB

export const upload = multer({
  storage: multer.memoryStorage(),
  limits: {
    fileSize: MAX_FILE_SIZE,
  },
  fileFilter: (_req, file, cb) => {
    const ext = path.extname(file.originalname).toLowerCase();
    if (ALLOWED_EXTENSIONS.includes(ext)) {
      cb(null, true);
    } else {
      cb(new Error(`File type ${ext} is not allowed`));
    }
  },
});

export function handleAttachment(req: Request, res: Response): void {
  if (!req.file) {
    res.status(400).json({ error: 'No file uploaded' });
    return;
  }

  res.status(200).json({
    message: 'File uploaded successfully',
    filename: req.file.originalname,
    size: req.file.size,
    mimetype: req.file.mimetype,
  });
}
