import axios, { AxiosInstance } from 'axios';

const apiClient: AxiosInstance = axios.create({
  baseURL: process.env.REACT_APP_API_URL || 'http://localhost:8080',
  timeout: 10000,
  maxRedirects: 5,
});

apiClient.interceptors.request.use((config) => {
  const token = localStorage.getItem('authToken');
  if (token) {
    config.headers = config.headers || {};
    config.headers['Authorization'] = `Bearer ${token}`;
  }
  return config;
});

apiClient.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response && error.response.status === 401) {
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

export interface Project {
  id: number;
  name: string;
  description: string;
  status: string;
  createdAt: string;
}

export interface Task {
  id: number;
  projectId: number;
  title: string;
  assignee: string;
  priority: string;
  dueDate: string;
}

export const projectsApi = {
  getAll: () => apiClient.get<Project[]>('/api/projects'),
  getById: (id: number) => apiClient.get<Project>(`/api/projects/${id}`),
  create: (project: Omit<Project, 'id' | 'createdAt'>) =>
    apiClient.post<Project>('/api/projects', project),
  update: (id: number, project: Partial<Project>) =>
    apiClient.put<Project>(`/api/projects/${id}`, project),
  delete: (id: number) => apiClient.delete(`/api/projects/${id}`),
};

export const tasksApi = {
  getAll: () => apiClient.get<Task[]>('/api/tasks'),
  getById: (id: number) => apiClient.get<Task>(`/api/tasks/${id}`),
  create: (task: Omit<Task, 'id'>) => apiClient.post<Task>('/api/tasks', task),
  update: (id: number, task: Partial<Task>) =>
    apiClient.put<Task>(`/api/tasks/${id}`, task),
  delete: (id: number) => apiClient.delete(`/api/tasks/${id}`),
};

export default apiClient;
